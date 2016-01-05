package com.rooney.learnduke;

import java.util.LinkedHashMap;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class LearnCommonsMath {
	public static void main(String[] args) {
		stats();
//		timeSeriesIntersection();
	}

	private static void stats() {
		
		double[] lhs = {1.0, 2.0, 3.0 };
//		double[] rhs = {1.0, 2.0, 3.0 };  gives 1.0
		double[] rhs = {1.1, 2.0, 3.0 };  // all must be populated
		
		SummaryStatistics stats = new SummaryStatistics();
		stats.addValue(1.0);
		stats.addValue(2.0);
		stats.addValue(3.0);
		
		System.out.println("Mean: " + new Mean().evaluate(lhs));
		System.out.println("variance: " + stats.getVariance());
		
		
		
		System.out.println("covariance: " + new Covariance().covariance(lhs, rhs));
		System.out.println("PearsonsCorrelation: " + new PearsonsCorrelation().correlation(lhs, rhs));
	}
}
