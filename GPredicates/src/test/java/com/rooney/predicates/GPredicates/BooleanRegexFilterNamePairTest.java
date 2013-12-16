package com.rooney.predicates.GPredicates;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class BooleanRegexFilterNamePairTest {
	
	@Test
	public void test() {
		Map<String, String> data1 = new LinkedHashMap<String, String>();
		data1.put("A", "1");
		data1.put("B", "2");
		data1.put("C", "3");
		
		Map<String, String> data2 = new LinkedHashMap<String, String>();
		data2.put("A", "1b");
		data2.put("B", "2b");
		data2.put("C", "3b");
		
		Map<String, String> data3 = new LinkedHashMap<String, String>();
		data3.put("A", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		data3.put("B", "Mozilla/4.75 [en](X11;U;Linux2.2.16-22 i586)");
		
		
		//test single field
		assertTrue(executeMatch(data1, "A:1"));
		assertTrue(executeMatch(data1, "B:2"));
		assertTrue(executeMatch(data1, "C:3"));
		
		assertFalse(executeMatch(data1, "A:2"));
		assertFalse(executeMatch(data1, "B:3"));
		assertFalse(executeMatch(data1, "D:1")); // D not present so do not include it
		

		//test invalid filters
		assertFalse(executeMatch(data1, "A:"));
		assertFalse(executeMatch(data1, ":1"));
		assertFalse(executeMatch(data1, "1"));
		assertFalse(executeMatch(data1, "")); //no filter supplied, so no match occurs
		
		
		//test NOT single field
		assertFalse(executeMatch(data1, "A:!1"));
		assertFalse(executeMatch(data1, "B:!2"));
		assertFalse(executeMatch(data1, "C:!3"));
		
		assertTrue(executeMatch(data1, "A:!RUBBISH"));
		assertTrue(executeMatch(data1, "B:!RUBBISH"));
		assertFalse(executeMatch(data1, "RUBBISH:!1")); // should this filter if 'RUBBISH' is not present?
		
		//test invalid filters
		assertFalse(executeMatch(data1, "A:!"));
		assertFalse(executeMatch(data1, ":!1"));
		assertFalse(executeMatch(data1, "!1"));
		
		
		//test single field OR
		assertTrue(executeMatch(data1, "A:1||1b"));
		assertTrue(executeMatch(data1, "A:1||1b||RUBBISH"));
		assertTrue(executeMatch(data2, "A:1||1b"));
		
		assertTrue(executeMatch(data1, "A:1||RUBBISH"));
		assertFalse(executeMatch(data2, "A:1||RUBBISH"));
		
		assertFalse(executeMatch(data1, "A:1||"));
		assertFalse(executeMatch(data1, "A:||"));
		assertFalse(executeMatch(data1, "||"));
		
		
		//test single field OR with equals OR NOT EQUALS
		assertTrue(executeMatch(data1, "A:1||!RUBBISH"));
		
		//test single field OR with NOT EQUALS OR NOT EQUALS
		assertTrue(executeMatch(data1, "A:!1||!RUBBISH")); //!1 fail, but !RUBBISH passes
		assertTrue(executeMatch(data1, "A:!RUBBISH||!RUBBISH"));
		
		
		//test single field AND with NOT EQUALS AND NOT EQUALS
		//assertTrue(fixture.filter(data1, "A:!1&&!RUBBISH")); //NOT IMPLEMENTED, use same key in 2 separate filters
		
		
		//test multiple same fields as AND
		assertTrue(executeMatch(data1, "A:1,A:!RUBBISH")); //match on A:1 and A:!RUBBISH
		assertTrue(executeMatch(data1, "A:!GARBAGE,A:!RUBBISH")); //matches on A:!GARBAGE AND A:!RUBBISH
		assertFalse(executeMatch(data1, "A:!1,A:!RUBBISH")); //fail on A:!1 
		assertFalse(executeMatch(data1, "A:!1, A:!RUBBISH")); //fail on A:!1 and space trimmed
		assertFalse(executeMatch(data1, "A: !1 , A:!RUBBISH ")); //fail on A:!1 and space trimmed
		assertFalse(executeMatch(data1, " A: !1 , A:!RUBBISH ")); //fail on A:!1 and space trimmed
		
		//test multiple diff fields as AND
		assertTrue(executeMatch(data1, "A:1, B:2"));
		assertFalse(executeMatch(data1, "A:1, B:2, Z:9"));
		assertFalse(executeMatch(data1, "A:1, B:99"));
		
		//test multiple diff fields as OR
		assertTrue(executeMatch(data1, "A:1||99, B:2||88"));
		assertFalse(executeMatch(data1, "A:77||99, B:2||88"));
		assertFalse(executeMatch(data1, "A:1||99, B:77||88"));
		assertFalse(executeMatch(data1, "66:1||99, B:77||88"));
		
		
		//test single field REGEX
		assertTrue(executeMatch(data3, "A:#in[du]")); //finds ind in Windows
		assertTrue(executeMatch(data3, "B:#in[du]")); //finds inu in Linux
		
		assertFalse(executeMatch(data3, "A:#x[0-9A-Z]")); // fail due to case sensitive
		assertTrue(executeMatch(data3, "B:#x[0-9A-Z]")); //Finds x2 in Linux2
		
		
		assertTrue(executeMatch(data3, "A:#[^A-M]in")); //Finds Win in Windows
		assertFalse(executeMatch(data3, "B:#[^A-M]in")); //Fail as excluded the range A to M
		
	}

	private boolean executeMatch(Map<String, String> dataToMatch, String filterPattern) {
		return new BooleanRegexFilterNamePair(filterPattern).match(dataToMatch);
    }

}
