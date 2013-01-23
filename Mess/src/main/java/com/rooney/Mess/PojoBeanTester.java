package com.rooney.Mess;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PojoBeanTester{

    public static class PersonTester extends AbstractPojoTester {
        @Test
        public void testGettersAndSetters() {
            testPojo(Person.class);
        }
    }    
        
    public static class Person {
        private String name;
        private int age;
        private double salary;
    
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public double getSalary() {
            return salary;
        }
        public void setSalary(double salary) {
            this.salary = salary;
        }
    }    
        
    public static abstract class AbstractPojoTester {
    
        private Map testValues = new HashMap();
    
        protected void addTestValue(Class propertyType, Object testValue) {
            testValues.put(propertyType, testValue);
        }
    
        @Before
        public void setUpTestValues() throws Exception {
            // add in further test values here.
            addTestValue(String.class, "foo");
            addTestValue(int.class, 123);
            addTestValue(Integer.class, 123);
            addTestValue(double.class, 123.0);
            addTestValue(Double.class, 123.0);
            addTestValue(boolean.class, true);
            addTestValue(Boolean.class, true);
            addTestValue(java.util.Date.class, new java.util.Date(100, 3, 4, 11, 45));
        }
    
        /**
         * Call from subclass
         */
        protected void testPojo(Class pojoClass) {
            try {
                Object pojo = pojoClass.newInstance();
                BeanInfo pojoInfo = Introspector.getBeanInfo(pojoClass);
                for (PropertyDescriptor propertyDescriptor : pojoInfo.getPropertyDescriptors()) {
                    testProperty(pojo, propertyDescriptor);
                }
            } catch (Exception e) {
                // ignore
            }
        }
    
        private void testProperty(Object pojo, PropertyDescriptor propertyDescriptor) {
            try {
                Class propertyType = propertyDescriptor.getPropertyType();
                Object testValue = testValues.get(propertyType);
                if (testValue == null) {
                    return;
                }
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null && writeMethod != null) {
                    writeMethod.invoke(pojo, testValue);
                    Assert.assertEquals(readMethod.invoke(pojo), testValue);
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }    
}
