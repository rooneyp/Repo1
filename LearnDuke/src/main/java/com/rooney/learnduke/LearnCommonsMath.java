package com.rooney.learnduke;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class LearnCommonsMath {
	public static void main(String[] args) {
//		stats();
		correlation();
	}

	private static void correlation() {
		// peasons has -1 to +1 as a result
		// -1 is that the points are moving apart all the time
		// +1 is that the points are both increasing all the time
		// 0 is no correlation
		
		
		double[] lhs = {100, 110, 120 };
		double[] rhs;
		
		correlation(lhs, 90, 80, 70 ); //-1 -100
		correlation(lhs, 3, 2, 1 ); //-1 -10
		
		correlation(lhs, 110, 120, 130 ); //1 100
		correlation(lhs, 1000, 2000, 3000 ); //1 10000
		correlation(lhs, 100, 2000, 3000 ); //xs
		
		//
		correlation(lhs, 101, 99, 102 ); //0.3
		correlation(lhs, 101, 1, 1000 ); //0.8
		
		//1 up 2 down 
		correlation(lhs, 101, 99, 44 ); //-0.8
		
	}

	private static void correlation(double[] lhs, double... rhs) {
		System.out.println("PearsonsCorrelation: " + Arrays.toString(lhs) + ", " + Arrays.toString(rhs)  + " result = "+ new PearsonsCorrelation().correlation(lhs, rhs));
		System.out.println("unbiased covariance: " + Arrays.toString(lhs) + ", " + Arrays.toString(rhs)  + " result = "+ new Covariance().covariance(lhs, rhs));
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
		correlation(lhs, rhs);
	}
}
