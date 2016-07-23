package com.rooney.sts.mit006

import org.junit.Test

import java.lang.reflect.Method

/**
 * Created by paul on 18/02/2016.
 */
class PeakFindingMultiplePeaksTest {
    def peakFinding = new PeakFindingMultiplePeaks();

    @Test
    void testSolutions() {
        testAllSolutions([1, 3, 2, 2, 4, 5, 2, 7, 8, 2], [1, 5, 8])

    }

    private Method[] testAllSolutions(int[] input, expected) {
        PeakFindingMultiplePeaks.methods.each {
            if (it.name.startsWith("soln")) {
                assert peakFinding."$it.name"(input) == expected
            }
        }
    }
}
