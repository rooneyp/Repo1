package com.rooney.Mess.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.webguys.ponzu.api.map.ImmutableMap;
import com.webguys.ponzu.api.map.MutableMap;
import com.webguys.ponzu.impl.factory.Maps;

// http://stackoverflow.com/questions/507602/how-to-initialise-a-static-map-in-java
public class MapsLearning {

    @Test
    public void testGuava() {
        //immutable map guarantees order
        Map<Integer, String> MY_MAP = com.google.common.collect.ImmutableMap.of(
                1, "one",
                2, "two",
                3, "three",
                4, "four",
                5, "five"
        );        
        
        CollectionUtils.forAllDo(MY_MAP.entrySet(), new Closure() {
            public void execute(Object input) {
                System.out.println(input);
            }
        });
        
//        Collections2.transform(MY_MAP.entrySet(), new Function(){
//            public Object apply(Object input) {
//                System.out.println(input);
//                return input;
//            }});            
    }
    
    @Test
    public void testDoubleBraceInit() {
        Map<String, String> FLAVORS = new HashMap<String, String>() {{
                put("Up", "Down");
                put("Top", "Bottom");
            }};
            
        Map<String, String> orderedMap = new LinkedHashMap<String, String>() {{
                put("Up", "Down");
                put("Top", "Bottom");
            }};

    }
    
    @Test
    public void testGS() {
        Map<Integer, String> MAP = 
                Maps.mutable.of(1, "one", 2, "two");

        MutableMap<Integer, String> MUTABLE_MAP = 
                Maps.mutable.of(1, "one", 2, "two");

        MutableMap<Integer, String> UNMODIFIABLE_MAP = 
                Maps.mutable.of(1, "one", 2, "two").asUnmodifiable();

        MutableMap<Integer, String> SYNCHRONIZED_MAP = 
                Maps.mutable.of(1, "one", 2, "two").asSynchronized();

        ImmutableMap<Integer, String> IMMUTABLE_MAP = 
                Maps.mutable.of(1, "one", 2, "two").toImmutable();

        ImmutableMap<Integer, String> IMMUTABLE_MAP2 = 
                Maps.immutable.of(1, "one", 2, "two");
    }
}
