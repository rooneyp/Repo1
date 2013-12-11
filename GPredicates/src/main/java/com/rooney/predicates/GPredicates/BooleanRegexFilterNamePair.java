package com.rooney.predicates.GPredicates;

import static com.google.common.base.Predicates.alwaysFalse;
import static com.google.common.base.Predicates.and;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class BooleanRegexFilterNamePair {

	/**
	 * 
	 * @param map
	 * @param filterPattern
	 * @return true if matched
	 */
	public boolean filter(Map<String, String> map, String filterPattern) {
		Predicate<Map.Entry<String, String>> filterPredicate = buildFilterPredicate(filterPattern);
		
		Map<String, String> filteredEntries = Maps.filterEntries(map, filterPredicate);
		
		
		return ! filteredEntries.isEmpty();
	}

	private Predicate<Map.Entry<String, String>> buildFilterPredicate(String filterPattern) {
		if(Strings.isNullOrEmpty(filterPattern)) {
			System.err.println("invalid filter pattern: " + filterPattern);
			return alwaysFalse();
		}
		
		List<Predicate<Map.Entry<String, String>>> allPredicates = new ArrayList<Predicate<Map.Entry<String,String>>>();
		
		for (String aFilter : Splitter.on(',').split(filterPattern)) {
			final List<String> splitFilter = Splitter.on(':').splitToList(aFilter);
			if(splitFilter.size() != 2) {
				System.err.println("invalid filter " + splitFilter);
				return alwaysFalse();
			}
			
			final String fieldName = splitFilter.get(0);
			final String fieldFilter = splitFilter.get(1);
			
			
			allPredicates.add(
				new Predicate<Map.Entry<String, String>>() { //todo use factory method
					public boolean apply(Entry<String, String> dataToFilter) {
						if(fieldName.equals(dataToFilter.getKey())
								&& buildIndividualFilterPredicate(fieldFilter).apply(dataToFilter.getValue())
								) {
							return true;
						} else {
							return false;
						}
					}

					private Predicate<String> buildIndividualFilterPredicate(String fieldFilter) {
						List<Predicate<String>> orPredicates = new ArrayList<Predicate<String>>();
						
						Iterable<String> splitByOR = Splitter.on("||").split(fieldFilter);
						
						
						for (String orFilter : splitByOR) {
							if(orFilter.startsWith("!")) {
								
							}
						}
						
						return null;
					};
				}
			);
		}
		
		
		
		return and(allPredicates);
	}
	
}
