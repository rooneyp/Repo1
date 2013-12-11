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
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("A", "1");
		map1.put("B", "2");
		map1.put("C", "3");
		
		//test single field
		assertTrue(fixture.filter(map1, "A:1"));
		assertTrue(fixture.filter(map1, "B:2"));
		assertTrue(fixture.filter(map1, "C:3"));
		
		assertFalse(fixture.filter(map1, "A:2"));
		assertFalse(fixture.filter(map1, "B:3"));
		assertFalse(fixture.filter(map1, "D:1"));
		
		
		assertFalse(fixture.filter(map1, "A:"));
		assertFalse(fixture.filter(map1, ":1"));
		assertFalse(fixture.filter(map1, "1"));
		assertFalse(fixture.filter(map1, ""));
		
		
		//test NOT single field
		assertFalse(fixture.filter(map1, "A:!1"));
		assertFalse(fixture.filter(map1, "B:!2"));
		assertFalse(fixture.filter(map1, "C:!3"));
		
		assertTrue(fixture.filter(map1, "A:!2"));
		assertTrue(fixture.filter(map1, "B:!3"));
		assertTrue(fixture.filter(map1, "D:!1")); // should this filter if D is not present?
		
		
		assertFalse(fixture.filter(map1, "A:"));
		assertFalse(fixture.filter(map1, ":!1"));
		assertFalse(fixture.filter(map1, "!1"));
		assertFalse(fixture.filter(map1, ""));
				
		
		
		//test single field OR
		
		//test single field OR with NOT
		
		//test single field REGEX
		
		//test multiple fields as AND
		
		//test multiple fields as OR
		
	}

}
