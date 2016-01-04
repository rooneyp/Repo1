package no.priv.garshol.duke.comparators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LevenshteinTest {

	@Test
	public void testCompare() {
		int result = Levenshtein.distance("kitten", "sitting");
		assertEquals(3, result);
	}

}
