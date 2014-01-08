package com.rooney.predicates.GPredicates;

import static com.google.common.base.Strings.*;
import static com.google.common.base.Predicates.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class BooleanRegexFilterNamePair {
	Predicate<Map.Entry<String, String>> filterPredicate;
	
	
	public BooleanRegexFilterNamePair(String filterPattern) {
		filterPredicate = buildFilterPredicate(filterPattern);
	}


	/**
	 * 
	 * @param map
	 * @param filterPattern
	 * @return true if matched
	 */
	public boolean filter(Map<String, String> map, String filterPattern) {
		
		Map<String, String> filteredEntries = Maps.filterEntries(map, filterPredicate);
		
		
		return ! filteredEntries.isEmpty();
	}

	private Predicate<Map.Entry<String, String>> buildFilterPredicate(String filterPattern) {
		if(isNullOrEmpty(filterPattern)) {
			System.err.println("invalid filter pattern: " + filterPattern);
			return alwaysFalse();
		}
		
		List<Predicate<Map.Entry<String, String>>> allPredicates = new ArrayList<Predicate<Map.Entry<String,String>>>();
		
		for (String aFilter : Splitter.on(',').split(filterPattern)) {
			final List<String> splitFilter = Splitter.on(':').splitToList(aFilter.trim());
			if(splitFilter.size() != 2) {
				System.err.println("invalid filter " + aFilter);
				return alwaysFalse();
			}
			
			final String fieldName = splitFilter.get(0).trim();
			final String fieldFilter = splitFilter.get(1).trim();
			
			
			allPredicates.add(
				buildMapEntryPredicate(fieldName, fieldFilter)
			);
		}
		
		
		
		return and(allPredicates);
	}

	private Predicate<Entry<String, String>> buildMapEntryPredicate(
			final String fieldName, final String fieldFilter) {
		
		
		return new Predicate<Map.Entry<String, String>>() { //todo use factory method
			{
				buildIndividualFilterPredicate(fieldFilter);
				
			}
			
			public boolean apply(Map.Entry<String, String> dataToFilter) {
				if(fieldName.equals(dataToFilter.getKey())
						&& .apply(dataToFilter.getValue())
						) {
					return true;
				} else {
					return false;
				}
			}
		};
	}
	
	private Predicate<String> buildIndividualFilterPredicate(String fieldFilter) {
		List<Predicate<String>> orPredicates = new ArrayList<Predicate<String>>();
		
		Iterable<String> splitByOR = Splitter.on("||").split(fieldFilter);
		
		
		for (String orFilter : splitByOR) {
			orFilter = orFilter.trim();
			
			if(orFilter.startsWith("!")) {
				if(orFilter.length() == 1) {
					System.err.println("invalid filter " + fieldFilter);
					return alwaysFalse();
				}
				
				orPredicates.add(not(equalTo(orFilter.substring(1))));
			} else {
				if(isNullOrEmpty(orFilter)) {
					System.err.println("invalid filter " + fieldFilter);
					return alwaysFalse();
				}
				
				orPredicates.add(equalTo(orFilter));
			}
		}
		
		return or(orPredicates);
	};	
	
}
