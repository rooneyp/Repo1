package com.rooney.sts;

import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SolutionTest{

    @Test public void testSolution() {
        int missingTerm = Solution.findMissingTerm("5", "1 11 31 41 51");
        assertEquals(21, missingTerm);
    }
}
