package com.rooney.learnduke;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.primitives.Doubles;

import no.priv.garshol.duke.Comparator;


public class MyComparator implements Comparator {
	private char separator = ':';
	private char keyValueSeparator = '=';


	public boolean isTokenized() {
		// TODO how important is this????
		return false;
	}

	/**
	 * Measures the linear correlation between series data of two variables.
	 * 
	 * Given 2 Strings representing key-value data for two variables, these are parsed into two insertion order maps.
	 * The values from the maps are extracted where keys match. 
	 * The 2 collections of values are compared using <a href="https://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient">Pearsons-Correlation</a> approach.
	 *  
	 * @see no.priv.garshol.duke.Comparator#compare(java.lang.String, java.lang.String)
	 */
	public double compare(String v1, String v2) {
		Preconditions.checkNotNull(v1, "LHS value is null");
		Preconditions.checkNotNull(v2, "RHS value is null");
		
		Map<String, Double> lhs = parseData(v1);
		Map<String, Double> rhs = parseData(v2);
		
		retainCommonKeys(lhs, rhs);
		
		//A correlation greater than 0.8 is generally described as strong, whereas a correlation less than 0.5 is generally described as weak. 
		//TODO maps to 0 -> 1.0
		return Math.max(0.0, calculateCorrelation(lhs.values(), rhs.values()));
	}

	
	double calculateCorrelation(Collection<Double> lhs, Collection<Double> rhs) {
		return new PearsonsCorrelation().correlation(Doubles.toArray(lhs), Doubles.toArray(rhs));
	}

	
	void retainCommonKeys(Map<String, Double> lhs, Map<String, Double> rhs) {
		SetView<String> commonKeys = Sets.intersection(lhs.keySet(), rhs.keySet());
		
		lhs.keySet().retainAll(commonKeys);
		rhs.keySet().retainAll(commonKeys);
	}

	
	Map<String, Double> parseData(String data) {
		LinkedHashMap<String, Double> parsedMap = Maps.newLinkedHashMap();
		
		for(String entry : StringUtils.split(data, separator)) {
			String[] splitEntry = StringUtils.split(entry, keyValueSeparator);
			parsedMap.put(splitEntry[0], Double.valueOf(splitEntry[1]));
		}
		
		return parsedMap;
	}

	
}


