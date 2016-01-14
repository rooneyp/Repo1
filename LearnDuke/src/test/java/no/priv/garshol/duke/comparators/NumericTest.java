package no.priv.garshol.duke.comparators;

import org.junit.Test;

public class NumericTest {

	@Test
	public void testNumeric() throws Exception {
		
		double minratio = 0.25;
		runComparator(minratio, "2.0", "2.0");
		runComparator(minratio, "1.75", "2.0");
		runComparator(minratio, "1.5", "2.0");
		runComparator(minratio, "1.25", "2.0");
		runComparator(minratio, "1.0", "2.0");
		runComparator(minratio, "1.0", "2.0");
		runComparator(minratio, "1.0", "3.0");
		runComparator(minratio, "1.0", "4.0");
		runComparator(minratio, "1.0", "5.0");
		runComparator(minratio, "1.0", "6.0");
		
	}

	private void runComparator(double minratio, String v1, String v2) {
		NumericComparator nv = new NumericComparator();
		nv.setMinRatio(minratio);
		double compare = nv.compare(v1, v2);
		
		System.out.println("v1=" + v1 + ", v2=" + v2 + ", minratio=" + minratio + " result= " + compare );
	}
}
