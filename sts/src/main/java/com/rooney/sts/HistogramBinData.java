package com.rooney.sts;

import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

public class HistogramBinData {
	public static void main(String[] args) {
		double[] data1 = new double[] {-0.0017, 0.0025, 0.001, 0.0017, 0.0064, 0.006, -0.0075, 0.0012, -0.01, 0.0044, 0.00204, -0.00796, 0.0079, -0.0024, -0.0056, 0.0049, 0.0023, 0.0012, 0.01, -0.0069, 0.0094, -0.00576, 0.00874, 0.00964, 0.0017, 0, -0.0048, 0.01, -0.04, -0.0058, 0.01, -0.0047, 0.01, 0.0014, -0.0083, 0.04, 0.00375, 0.00655, 0.02, 0.01, -0.0037, -0.01, -0.0056, -0.01, 0.01, -0.01, 0.01, 0.01, -0.05, 0.00874, 0, 0.01, -0.04, -0.0063, 0.00494, -0.02, 0.02, 0.02, 0.00024, -0.01, 0.03, 0.01, 0.00974, 0.02, -0.01, 0.01, 0.01, -0.03, 0.01, 0.04, 0.01, 0.0048, 0.00764, 0.00234, -0.00666, -0.03, 0.02, 0.03, 0.00554, 0.01, 0.03, 0.02, 0.01, 0.01, 0.01, -0.04, 0.03, -0.04, 0.01, 0.0024, 0.02, 0.00634, 0.04, -0.01};
		double[] data2 = new double[] {-0.0017, 0.0025, 0.001, 0.0017, 0.0064, 0.006, -0.0075, 0.0012, -0.01, 0.0044, 0.00204, -0.00796, 0.0079, -0.0024, -0.0056, 0.0049, 0.0023, 0.0012, 0.01, -0.0069, 0.0094, -0.00576, 0.00874, 0.00964, 0.0017, 0, -0.0048, 0.01, -0.04, -0.0058, 0.01, -0.0047, 0.01, 0.0014, -0.0083, 0.04, 0.00375, 0.00655, 0.02, 0.01, -0.0037, -0.01, -0.0056, -0.01, 0.01, -0.01, 0.01, 0.01, -0.05, 0.00874, 0, 0.01, -0.04, -0.0063, 0.00494, -0.02, 0.02, 0.02, 0.00024, -0.01, 0.03, 0.01, 0.00974, 0.02, -0.01, 0.01, 0.01, -0.03, 0.01, 0.04, 0.01, 0.0048, 0.00764, 0.00234, -0.00666, -0.03, 0.02, 0.03, 0.00554, 0.01, 0.03, 0.02, 0.01, 0.01, 0.01, -0.04, 0.03, -0.04, 0.01, 0.0024, 0.02, 0.00634, 0.04, -0.01};
		
		double min = Math.min(Doubles.min(data1), Doubles.min(data1));
		double max = Math.min(Doubles.max(data2), Doubles.max(data2));
		
		System.out.println(Ints.asList(binData(data1, min, max, 10)));
		System.out.println("===============");
		long[] commonsHisto = binUsingCommons(data1, 10);
	}
	
	
	private static int[] binData(double[] data, double min, double max, int binCount) {
		double binSize = (max-min) / (Double.valueOf(binCount)).doubleValue();
		System.out.println("binSize=" + binSize);
		
		int[] bins = new int[binCount];
		for (double datum : data) {
			int binIndex =  Math.max((int) ((datum - min) / binSize), 0) ; //get value distance from min and divide by binSize to get bin Index (but keep above 0)
			bins[Math.min(binIndex, binCount-1)]++;	//index into bin array, but don't allow binIndex to index above last bin of bin array
			
		}
		return bins;
	}
	
	private static long[] binUsingCommons(double[] data, int binCount) {
		EmpiricalDistribution lhsDistribution = new EmpiricalDistribution(binCount);
		lhsDistribution.load(data);
		
		System.out.println("min=" + lhsDistribution.getSupportLowerBound() + ", max=" + lhsDistribution.getSupportUpperBound());
		
		
		long[] histogram = new long[binCount];
		int k = 0;
		for(SummaryStatistics stats: lhsDistribution.getBinStats())
		{
			System.out.println(stats.getMax() + " : " + stats.getN());
		    histogram[k++] = stats.getN();
		}
		return histogram;
	}	
}
