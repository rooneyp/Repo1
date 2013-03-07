package com.rooney.spring.spel;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class Bootstrapper implements ApplicationContextAware, InitializingBean {
    private AbstractRefreshableConfigApplicationContext configSettableContext;
    private GenericApplicationContext genContext;
    private String[] configLocations;

    public void setConfigLocations(final String[] configLocations) {
        this.configLocations = configLocations;
    }

    public void afterPropertiesSet() throws Exception {
        if(configSettableContext != null) {
            configSettableContext.setConfigLocations(configLocations);
            configSettableContext.refresh();
        } else {
            genContext.refresh();
        }
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
	    if(applicationContext instanceof AbstractRefreshableConfigApplicationContext) {
	        configSettableContext =  (AbstractRefreshableConfigApplicationContext) applicationContext;		
	    } else {
	        genContext = (GenericApplicationContext) applicationContext;
	    }
	}
}
