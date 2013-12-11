package com.rooney.predicates.GPredicates;

import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class BooleanRegexFilterNamePairTest {
	
	@Test
	public void test() {
		BooleanRegexFilterNamePair fixture = new BooleanRegexFilterNamePair();
		
		Map<String, String> data1 = new LinkedHashMap<String, String>();
		data1.put("A", "1");
		data1.put("B", "2");
		data1.put("C", "3");
		
		Map<String, String> data2 = new LinkedHashMap<String, String>();
		data2.put("A", "1b");
		data2.put("B", "2b");
		data2.put("C", "3b");
		
		
		//test single field
		assertTrue(fixture.filter(data1, "A:1"));
		assertTrue(fixture.filter(data1, "B:2"));
		assertTrue(fixture.filter(data1, "C:3"));
		
		assertFalse(fixture.filter(data1, "A:2"));
		assertFalse(fixture.filter(data1, "B:3"));
		assertFalse(fixture.filter(data1, "D:1"));
		
		
		assertFalse(fixture.filter(data1, "A:"));
		assertFalse(fixture.filter(data1, ":1"));
		assertFalse(fixture.filter(data1, "1"));
		assertFalse(fixture.filter(data1, ""));
		
		
		//test NOT single field
		assertFalse(fixture.filter(data1, "A:!1"));
		assertFalse(fixture.filter(data1, "B:!2"));
		assertFalse(fixture.filter(data1, "C:!3"));
		
		assertTrue(fixture.filter(data1, "A:!RUBBISH"));
		assertTrue(fixture.filter(data1, "B:!RUBBISH"));
		assertFalse(fixture.filter(data1, "RUBBISH:!1")); // should this filter if 'RUBBISH' is not present?
		
		
		assertFalse(fixture.filter(data1, "A:"));
		assertFalse(fixture.filter(data1, "A:!"));
		assertFalse(fixture.filter(data1, ":!1"));
		assertFalse(fixture.filter(data1, "!1"));
		assertFalse(fixture.filter(data1, ""));
				
		
		
		//test single field OR
		assertTrue(fixture.filter(data1, "A:1||1b"));
		assertTrue(fixture.filter(data1, "A:1||1b||RUBBISH"));
		assertTrue(fixture.filter(data2, "A:1||1b"));
		
		assertTrue(fixture.filter(data1, "A:1||RUBBISH"));
		assertFalse(fixture.filter(data2, "A:1||RUBBISH"));
		
		assertFalse(fixture.filter(data1, "A:1||"));
		assertFalse(fixture.filter(data1, "A:||"));
		assertFalse(fixture.filter(data1, "||"));
		
		
		//test single field OR with equals OR NOT EQUALS
		assertTrue(fixture.filter(data1, "A:1||!RUBBISH"));
		
		//test single field OR with NOT EQUALS OR NOT EQUALS
		assertTrue(fixture.filter(data1, "A:!1||!RUBBISH")); //!1 fails, but !RUBBISH passes
		assertTrue(fixture.filter(data1, "A:!RUBBISH||!RUBBISH"));
		
		
		//test single field AND with NOT EQUALS AND NOT EQUALS
		//assertTrue(fixture.filter(data1, "A:!1&&!RUBBISH")); //NOT IMPLEMENTED, use 2 separate single fields  
		
		//test single field REGEX
		
		//test multiple same fields as AND
		assertTrue(fixture.filter(data1, "A:1,A:!RUBBISH")); //matches on A:1 and A:!RUBBISH
		assertTrue(fixture.filter(data1, "A:!GARBAGE,A:!RUBBISH")); //matches on A:!GARBAGE AND A:!RUBBISH
		assertFalse(fixture.filter(data1, "A:!1,A:!RUBBISH")); //fails on A:!1 
		assertFalse(fixture.filter(data1, "A:!1, A:!RUBBISH")); //fails on A:!1 and space trimmed
		assertFalse(fixture.filter(data1, "A: !1 , A:!RUBBISH ")); //fails on A:!1 and space trimmed
		assertFalse(fixture.filter(data1, " A: !1 , A:!RUBBISH ")); //fails on A:!1 and space trimmed
		
		//test multiple diff fields as AND
		
		
		//test multiple diff fields as OR
		
	}

}
