package com.rooney.spring.spel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.util.StringUtils;

public class SpelContextLoader extends AbstractContextLoader{
    protected static final Log logger = LogFactory.getLog(SpelContextLoader.class);

    public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Loading ApplicationContext for merged context configuration [%s].", mergedConfig));
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.getEnvironment().setActiveProfiles(mergedConfig.getActiveProfiles());
        context.setConfigLocations(mergedConfig.getLocations());
        context.refresh();
        context.registerShutdownHook();
        
        AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(ap);
        ap.setParent(context);
        ap.refresh();
        ap.registerShutdownHook();
        return ap;
    }

    public ApplicationContext loadContext(String... locations) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Loading ApplicationContext for locations [%s].",
                    StringUtils.arrayToCommaDelimitedString(locations)));
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocations(locations);
        context.refresh();
        AnnotationConfigUtils.registerAnnotationConfigProcessors((DefaultListableBeanFactory) context.getAutowireCapableBeanFactory());
        context.registerShutdownHook();
        return context;
    }

    /**
     * Returns &quot;<code>-context.xml</code>&quot;.
     */
    @Override
    public String getResourceSuffix() {
        return "-context.xml";
    }
}
