package com.rooney.Mess;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerator {
    public static void main(String[] args) {
        long l = 0;
        System.out.println();
    }

    private Map testValues = new HashMap() {
        @Override
        public Object get(Object key) {
            Object oldValue = super.get(key);

            if (oldValue != null) {
                super.put(key, incrementors.get(key).increment(oldValue));
            }
            return oldValue;
        }
    };
    
    private Map<Object, Incrementor> incrementors = new HashMap();

    public DataGenerator() {
        setUpTestValues();
    }

    public <T> List<T> generate(Class<T> clazz, long count) {
        List<T> result = new ArrayList<T>();

        try {
            for (int i = 0; i < count; i++) {
                T pojo = clazz.newInstance();
                BeanInfo pojoInfo = Introspector.getBeanInfo(clazz);
                for (PropertyDescriptor propertyDescriptor : pojoInfo.getPropertyDescriptors()) {
                    setProperty(pojo, propertyDescriptor);
                }
                result.add(pojo);
            }
        } catch (Exception e) {
            // ignore
        }
        return result;
    }
    
    private <T> void setProperty(T pojo, PropertyDescriptor propertyDescriptor) {
        try {
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            Object testValue = testValues.get(propertyType);
            if (testValue == null) {
                return;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                writeMethod.invoke(pojo, testValue);
            }
        } catch (Exception e) {
            System.out.println(pojo + ", " + propertyDescriptor);
            e.printStackTrace();
            // ignore
        }
    }
    
    protected void addTestValue(Class propertyType, Object testValue, Incrementor inc) {
        testValues.put(propertyType, testValue);
        incrementors.put(propertyType, inc);
    }

    public void setUpTestValues() {
        // add in further test values here.
        addTestValue(String.class, "foo", new Incrementor() {
            private int seed = 1;
            public Object increment(Object o) {
                return ((String) o) + seed++;
            }
        });
        addTestValue(int.class, 123, new Incrementor() {
            private int seed = 1;
            public Object increment(Object o) {
                return ((int) o) + seed++;
            }
        });
        addTestValue(Integer.class, 123, new Incrementor() {
            private int seed = 1;
            public Object increment(Object o) {
                return ((Integer) o) + seed++;
            }
        });
        addTestValue(long.class, 123L, new Incrementor() {
            private long seed = 1L;
            public Object increment(Object o) {
                return ((Long) o) + seed++;
            }
        });
        addTestValue(Long.class, 123L, new Incrementor() {
            private long seed = 1L;
            public Object increment(Object o) {
                return ((Long) o) + seed++;
            }
        });
        addTestValue(double.class, 123.0, new Incrementor() {
            private double seed = 1;
            public Object increment(Object o) {
                return ((double) o) + seed++;
            }
        });
        addTestValue(Double.class, 123.0, new Incrementor() {
            private double seed = 1;
            public Object increment(Object o) {
                return ((Double) o) + seed++;
            }
        });
        addTestValue(boolean.class, true, new Incrementor() {
            public Object increment(Object o) {
                return !((Boolean) o);
            }
        });
        addTestValue(Boolean.class, true, new Incrementor() {
            public Object increment(Object o) {
                return !((Boolean) o);
            }
        });
        addTestValue(java.util.Date.class, new java.util.Date(100, 3, 4, 11, 45), new Incrementor() {
            private int seed = 1;
            public Object increment(Object o) {
                return new Date(((Date) o).getTime() + seed++);
            }
        });
    }

    
    public interface Incrementor {
        Object increment(Object o);
    }
}
