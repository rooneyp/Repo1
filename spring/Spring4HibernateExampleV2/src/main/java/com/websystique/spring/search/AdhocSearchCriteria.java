package com.websystique.spring.search;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

//TODO enhance to support JQL like language
//See http://www.baeldung.com/hibernate-pagination
public class AdhocSearchCriteria {
    protected int limit;
    protected String sort;
    protected String dir;
    protected int page;
    protected int start;
    //[{"type":"string","value":"34","field":"APP_ID"},{"type":"numeric","comparison":"gt","value":4,"field":"itemId"}]
    //[{"operator":"like","value":"pictures","property":"messageText"},{"operator":"gt","value":4,"property":"priority"},{"operator":"lt","value":1152399600000,"property":"added"}]
    protected List<Map<String, Object>> filters = Lists.newArrayList();
    protected Set<String> dateFields = Sets.newHashSet();
    
    
    public AdhocSearchCriteria(Map<String, String> requestMap, List<Map<String, Object>> filters) {
        this.filters = filters;

    }

    
    

    public static void main(String[] args) throws IOException {
        String requestParams = "["
                + "{\"operator\":\"like\",\"value\":\"pictures\",\"property\":\"messageText\"}"
                + ",{\"operator\":\"gt\",\"value\":4,\"property\":\"priority\"}"
                + ",{\"operator\":\"lt\",\"value\":1152399600000,\"property\":\"added\"}"
                + "]";

        TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<Map<String, Object>> filters = new ObjectMapper().readValue(requestParams, typeRef);
        System.out.println("filters " + filters);

        Map<String, String> requestMap = new HashMap<String, String>();
        
        requestMap.put("sort", "mySortField");
        requestMap.put("filter", requestParams);
        
        AdhocSearchCriteria criteria = new AdhocSearchCriteria(requestMap, filters);
        
        //test: getquery
        //test: getTotalCount
    
        
        //TODO build as HQL or
        
    }
    
}
