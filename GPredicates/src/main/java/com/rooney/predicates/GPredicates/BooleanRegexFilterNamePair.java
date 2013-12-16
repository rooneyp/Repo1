package com.rooney.predicates.GPredicates;

import static com.google.common.base.Predicates.*;
import static com.google.common.base.Strings.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class BooleanRegexFilterNamePair {
	final String filterPattern;
	private final Predicate<String> dataKeysFilter;
	private final Map<String, Predicate<CharSequence>> filtersByKey;
	
	public BooleanRegexFilterNamePair(String filterPattern) {
	    this.filterPattern = filterPattern;
		dataKeysFilter = buildFilterForKeys(filterPattern);
		filtersByKey = buildFilterPredicate(filterPattern);
    }

	/**
	 * , is treated as AND
	 * , can be for the same or different fields
	 * 
	 * single field supports either (equals, not-equals and OR) or Regex
	 * '#' prefix is treated as a single regex (equals, not-equals and OR syntax is not parsed) e.g "fieldFoo:#[^A-M]in"
	 * a value present is treated as an equals check . e.g "fieldFoo:100" means match where "fieldFoo" has a value of 100
	 * '!' is a not equals check. e.g "fieldFoo:!100" means match where "fieldFoo" does not have a value of 100
	 * '||' is a logical OR which can pair equals and non equals. e.g.
	 * 
	 * Implementation is:
	 * <LI>create Predicates for each filter Key, and group by key
	 * <LI>if filter key is not present in data, then FAIL
	 * <LI>for each filter key, match all predicates, if any do not mach then FAIL
	 * <LI>if reach, end we have a MATCH
	 * 
	 * TODO: log 
	 * @param dataToMatch
	 * @param filterPattern
	 * @return true if matched
	 */
	public boolean match(Map<String, String> dataToMatch) {
		
		//remove data fields that are not filtered on
		Map<String, String> dataByFilterKeysOnly = Maps.filterKeys(dataToMatch, dataKeysFilter);
		
		if(dataByFilterKeysOnly.isEmpty()) {
			return false; //The filter does not contain any keys that are present in the data, so we do not match
		}

		//loop on each filter by key, and apply to matching data by key
		for (Entry<String, Predicate<CharSequence>> filterForKey : filtersByKey.entrySet()) {
			String dataForKey = dataByFilterKeysOnly.get(filterForKey.getKey());
			if(isNullOrEmpty(dataForKey)) {
				return false; // a Filter was present for a key that wasn't present in the data
			}
			
			if(! filterForKey.getValue().apply(dataForKey)) {
				return false;
			}
        }
		
		return true;
	}

	/**
	 * Give a filterPattern, extract the keys present into a Predicate to validate a key is present in the filterPattern 
	 * @param filterPattern
	 * @return
	 */
	private Predicate<String> buildFilterForKeys(String filterPattern) {
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

	/**
	 * Given a filter pattern, extract the filter(s) for each key, construct a Predicate for the filter and store by key. 
	 * A malformed filter will result in a Predicate which will match noting.
	 *  
	 * @param filterPattern
	 * @return
	 */
	private Map<String, Predicate<CharSequence>> buildFilterPredicate(String filterPattern) {
		Multimap<String, Predicate<CharSequence>> predicatesByKey = LinkedHashMultimap.create();
		
		if(isNullOrEmpty(filterPattern)) {
			System.err.println("No matches will occur as filter is empty");
			return Collections.emptyMap();
		}
		
		for (String aFilter : Splitter.on(',').split(filterPattern)) {
			List<String> splitFilter = Splitter.on(':').splitToList(aFilter.trim());

			if(splitFilter.size() != 2) {
				System.err.println("No matches will occur as filter portion is invalid: " + aFilter + " in filter " + filterPattern);
				return Collections.emptyMap();
			}
			
			String fieldName = splitFilter.get(0).trim();
			String fieldFilter = splitFilter.get(1).trim();
			
			predicatesByKey.put(fieldName, buildIndividualFilterPredicate(fieldFilter));
		}

		Map<String, Predicate<CharSequence>> predicatesByKeyANDed = new LinkedHashMap<String, Predicate<CharSequence>>();
		
		for (String key : predicatesByKey.keys()) {
			Collection<Predicate<CharSequence>> predicatesForAKey = predicatesByKey.get(key);
			Predicate<CharSequence> andPredicate = and(predicatesForAKey); //java 6 needs this broken out
			predicatesByKeyANDed.put(key, andPredicate);
        }
		
		return predicatesByKeyANDed; 
	}	
	
	/**
	 * Builds a filter Predicate for a keys' value from syntax:
	 * !  Not
	 * || OR
	 * #  regex
	 * @param fieldFilterPattern
	 * @return
	 */
	private Predicate<CharSequence> buildIndividualFilterPredicate(String fieldFilterPattern) {
		if(isNullOrEmpty(fieldFilterPattern) || fieldFilterPattern.equals("#")) {
			System.err.println("invalid filter " + fieldFilterPattern);
			return alwaysFalse();
		}
		
		if(fieldFilterPattern.startsWith("#")) { //handle regex
			return containsPattern(fieldFilterPattern.substring(1));
		}
		
		List<Predicate<CharSequence>> orPredicates = new ArrayList<Predicate<CharSequence>>();
		
		Iterable<String> splitByOR = Splitter.on("||").split(fieldFilterPattern); //just support OR for now
		
		for (String orFilterPattern : splitByOR) {
			orFilterPattern = orFilterPattern.trim(); //TODO do we need these?
			
			if(orFilterPattern.startsWith("!")) {
				if(orFilterPattern.length() == 1) {
					System.err.println("invalid filter " + fieldFilterPattern);
					return alwaysFalse();
				}
				
				orPredicates.add(not(equalTo((CharSequence)orFilterPattern.substring(1))));
			} else {
				if(isNullOrEmpty(orFilterPattern)) {
					System.err.println("invalid filter " + fieldFilterPattern);
					return alwaysFalse();
				}
				
				orPredicates.add(equalTo((CharSequence)orFilterPattern)); //TODO ignore OR predicate if just one filter
			}
		}
		
		return or(orPredicates); //TODO can we call OR in the loop
	};	
	
}
