package com.rooney.Mess.freemarker;

import java.io.*;
import java.util.*;


import freemarker.cache.*;
import freemarker.ext.beans.*;
import freemarker.template.*;

public class FreeMarker {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		StringWriter out = new StringWriter();
//		System.setProperty("foo", "");
		new FreeMarker().codeGen("", "Scratch2.ftl", out);
		System.out.println(out);
	}

	
    public void codeGen(Object model, String templateName, StringWriter out) throws Exception {
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
//            LOG.error(msg, e);
            throw new Exception(msg, e);
        } 
        out.flush();
    }	
}
