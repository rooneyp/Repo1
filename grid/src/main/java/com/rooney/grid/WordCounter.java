package com.rooney.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gridgain.grid.gridify.Gridify;

public class WordCounter {

    @Gridify
    public Map<String, Integer> count(Set<String> fileNames, Set<String> words) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        // ..
        return result;
    }
}
