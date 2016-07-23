package com.rooney.sts.mit006;

import java.util.ArrayList;
import java.util.List;

/**
 *Find single peak in a 1 dimensional array.
 * A peak is defined as a value at position n where position  n >= n -1 and n >= n +1
 * At bounds of array, we only check one position inward
 * Created by paul on 17/02/2016.
 */
public class PeakFinding {

    public List<Integer> soln1(int[] data) {
        List<Integer> peaks = new ArrayList<Integer>();
        int length = data.length;

        if(length <= 1) {
            return peaks;
        }

        for(int i = 0; i< length; i++) {
            if ( (i !=0 ? data[i] >= data[i-1] : true) &&
                    (i != length ? data[i] >= data[i+1] : true)) {
                System.out.println("peak at " + i);
                peaks.add(i);
                i++; //skip rhs as it's not a peak
            }
        }
        return peaks;
    }

}
