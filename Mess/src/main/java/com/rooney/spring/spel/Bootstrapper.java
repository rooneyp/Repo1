package com.rooney.spring.spel;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;

public class Bootstrapper implements ApplicationContextAware, InitializingBean {
    private AbstractRefreshableConfigApplicationContext context;
    private String[] configLocations;

    public void setConfigLocations(final String[] configLocations) {
        this.configLocations = configLocations;
    }

    public void afterPropertiesSet() throws Exception {
        context.setConfigLocations(configLocations);
        context.refresh();
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context =  (AbstractRefreshableConfigApplicationContext) applicationContext;		
	}
}
