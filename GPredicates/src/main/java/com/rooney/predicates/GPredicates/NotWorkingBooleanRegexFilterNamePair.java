package com.rooney.predicates.GPredicates;

import static com.google.common.base.Predicates.*;
import static com.google.common.base.Strings.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

/*
 * This solution doesn't work as 
 * it just checks that *each* data element is matched by at least one filter Predicate
 * 
 * given data: A:1,B:2, C:3
 * cannot support
 * 	"A:!1,A:!RUBBISH"  //one pred matches A1, so it returns TRUE, !1 should fail it
 *  "A:1, B:2, Z:9"    //one pred matches A1, so it returns TRUE, !1 should fail as Z:9 doesn't match
 * 
 * 
 * We need 
 *  
 */
public class NotWorkingBooleanRegexFilterNamePair {

	/**
	 * , is AND
	 * single field supports equals, not-equals and OR
	 * 
	 * xxx
	 * TODO: optimise by just running predicates on matched keys and their values
	 * @param map
	 * @param filterPattern
	 * @return true if matched
	 */
	public boolean match(Map<String, String> map, String filterPattern) {
		//TODO move to construction/injection once complete
		Predicate<Map.Entry<String, String>> filterEntryPredicate = buildFilterPredicate(filterPattern);
		Predicate<String> filterKeyPredicate = buildFilterKeyPredicate(filterPattern);
		
		//remove data fields that are not filtered on
		//TODO: has this removed the data from the original map?
		Map<String, String> dataFilteredByFilteredKeys = Maps.filterKeys(map, filterKeyPredicate);
		
		if(dataFilteredByFilteredKeys.isEmpty()) {
			return false; //The filter does not contain any keys that are present in the data, so we do not match
		}
		
		//we 'AND' the results of matching against each key-filtered entry, 'success' is where each entry has a match 		
		for (Entry<String, String> entry : dataFilteredByFilteredKeys.entrySet()) {
			if(!filterEntryPredicate.apply(entry)) {
				return false;
			}
		}
		
		return true;
	}

	private Predicate<String> buildFilterKeyPredicate(String filterPattern) {
		final List<String> keysToFilterOn = new ArrayList<String>();
		
		for (String aFilter : Splitter.on(',').split(filterPattern)) {
			List<String> splitFilter = Splitter.on(':').splitToList(aFilter.trim());
			keysToFilterOn.add(splitFilter.get(0).trim());
		}

		return new Predicate<String>() {
			public boolean apply(String key) {
				return keysToFilterOn.contains(key);
			}
		};
	}

	private Predicate<Map.Entry<String, String>> buildFilterPredicate(String filterPattern) {
		if(isNullOrEmpty(filterPattern)) {
			System.err.println("No matches will occur as filter is empty");
			return alwaysFalse();
		}
		
		List<Predicate<Map.Entry<String, String>>> allPredicates = new ArrayList<Predicate<Map.Entry<String,String>>>();
		
		for (String aFilter : Splitter.on(',').split(filterPattern)) {
			List<String> splitFilter = Splitter.on(':').splitToList(aFilter.trim());

			if(splitFilter.size() != 2) {
				System.err.println("invalid filter " + aFilter);
				return alwaysFalse();
			}
			
			String fieldName = splitFilter.get(0).trim();
			String fieldFilter = splitFilter.get(1).trim();
			
			allPredicates.add(buildMapEntryPredicate(fieldName, fieldFilter));
		}
		
		//as our incoming data set has been filtered to only contain keys we are filtering on
		//on each entry, for a successful match against the entry, at least one predicate should match 
		return or(allPredicates); //TODO can we call 'or' in the loop
	}

	private Predicate<Entry<String, String>> buildMapEntryPredicate(final String fieldName, final String fieldFilter) {
		return new Predicate<Map.Entry<String, String>>() { 
			Predicate<String> individualFilterPredicate = buildIndividualFilterPredicate(fieldFilter);  
			
			public boolean apply(Map.Entry<String, String> dataToFilter) {
				return fieldName.equals(dataToFilter.getKey()) 
						&& individualFilterPredicate.apply(dataToFilter.getValue());
			}
		};
	}
	
	private Predicate<String> buildIndividualFilterPredicate(String fieldFilter) {
		List<Predicate<String>> orPredicates = new ArrayList<Predicate<String>>();
		
		Iterable<String> splitByOR = Splitter.on("||").split(fieldFilter); //just support OR for now
		
		for (String orFilterPattern : splitByOR) {
			orFilterPattern = orFilterPattern.trim(); //TODO do we need these?
			
			if(orFilterPattern.startsWith("!")) {
				if(orFilterPattern.length() == 1) {
					System.err.println("invalid filter " + fieldFilter);
					return alwaysFalse();
				}
				
				orPredicates.add(not(equalTo(orFilterPattern.substring(1))));
			} else {
				if(isNullOrEmpty(orFilterPattern)) {
					System.err.println("invalid filter " + fieldFilter);
					return alwaysFalse();
				}
				
				orPredicates.add(equalTo(orFilterPattern)); //TODO ignore OR predicate if just one filter
			}
		}
		
		return or(orPredicates); //TODO can we call OR in the loop
	};	
	
}
