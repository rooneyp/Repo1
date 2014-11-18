package com.rooney.Mess.freemarker;

import java.io.*;
import java.util.*;

import com.google.common.collect.ImmutableMap;

import freemarker.cache.*;
import freemarker.ext.beans.*;
import freemarker.template.*;

public class FreeMarker {
	public static void main(String[] args) throws Exception {
		Map<String, Object> nestedMap = new HashMap<String, Object>();
		nestedMap.put("foo", "bar");
		
		Object dataModel = ImmutableMap.of("key1", "val1", "key2", "val2", "nestedMap", nestedMap);
		
		new FreeMarker().codeGen(dataModel, "Hash.ftl", new PrintWriter(System.out));
	}

    public void codeGen(Object model, String templateName, Writer out) throws Exception {
        try {
            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());

            Template template = cfg.getTemplate("templates/"+templateName);

            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("data", model);
            modelMap.put("statics", BeansWrapper.getDefaultInstance().getStaticModels());

            template.process(modelMap, out);
        } catch (Exception e) {
            String msg = "Error generating code for template: " + templateName;
            throw new Exception(msg, e);
        } 
        out.flush();
    }	
}
