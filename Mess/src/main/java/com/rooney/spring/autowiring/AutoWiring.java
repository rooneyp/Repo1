package com.rooney.spring.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.TaskScheduler;

public class AutoWiring {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:**/AutoWiring-context.xml");
//        System.out.println(appCtx.getBean("scheduler"));
//        System.out.println(appCtx.getBean("test"));
        
        MyBean bean = new MyBean();
        System.out.println(bean);
        
        AutowireCapableBeanFactory factory = appCtx.getAutowireCapableBeanFactory();
        factory.autowireBean( bean );
        factory.initializeBean( bean, "bean" );
        
        System.out.println(bean);
    }

    
    public static class MyBean {
        @Autowired TaskScheduler scheduler;
        @Autowired String test;
        @Override
        public String toString() {
            return super.toString() + " test: " + test + " sch: " +scheduler;
        }
        
        
    }
}
