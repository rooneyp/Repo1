package com.rooney.spring.spel;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

//http://bottlenet.blogspot.ie/2012/08/spring-dynamic-import-with-properties.html
public class ApplicationContextLoader implements ApplicationContextAware {
    public List<Resource> contextLocations;
    public GenericApplicationContext loadedAppContext;
    public ApplicationContext applicationContext;
    public PropertyPlaceholderConfigurer propertyPlaceholder;

    public void initialize() throws Exception {
        loadedAppContext = new GenericApplicationContext(applicationContext);

        XmlBeanDefinitionReader beanReader = null;
        beanReader = new XmlBeanDefinitionReader(loadedAppContext);

        /* only needed when those settings in spring jar are invisible, e.g. in OSGi platform 
        beanReader.setNamespaceHandlerResolver(new DefaultNamespaceHandlerResolver(
                applicationContext.getClassLoader(), YOUR_HANDLER_MAPPINGS_LOCATION));
        beanReader.setEntityResolver(new PluggableSchemaResolver(
                applicationContext.getClassLoader(), YOUR_SCHEMA_MAPPINGS_LOCATION));
        */

        for (Resource contextLocation : contextLocations) {
            beanReader.loadBeanDefinitions(contextLocation);
        }

        if (propertyPlaceholder != null) {
            propertyPlaceholder.postProcessBeanFactory(loadedAppContext.getBeanFactory());
        }

        loadedAppContext.refresh();
    }

    public void close() {
        if (loadedAppContext != null) {
            loadedAppContext.close();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<Resource> getContextLocations() {
        return contextLocations;
    }

    public void setContextLocations(List<Resource> contextLocations) {
        this.contextLocations = contextLocations;
    }

    public GenericApplicationContext getLoadedAppContext() {
        return loadedAppContext;
    }

    public void setLoadedAppContext(GenericApplicationContext loadedAppContext) {
        this.loadedAppContext = loadedAppContext;
    }

    public PropertyPlaceholderConfigurer getPropertyPlaceholder() {
        return propertyPlaceholder;
    }

    public void setPropertyPlaceholder(PropertyPlaceholderConfigurer propertyPlaceholder) {
        this.propertyPlaceholder = propertyPlaceholder;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
