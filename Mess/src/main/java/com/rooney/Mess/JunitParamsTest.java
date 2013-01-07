package com.rooney.Mess;

import static junitparams.JUnitParamsRunner.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class) public class JunitParamsTest {
/************************************************************************/    
    @Test
    @Parameters({ "17, false", "22, true" })
    public void personIsAdultV1(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult(), is(valid));
    }  


/************************************************************************/
    @Test
    @Parameters(method = "adultValues")
    public void personIsAdultV2(int age, boolean valid) throws Exception {
        assertEquals(valid, new Person(age).isAdult());
    }

    private Object[] adultValues() {
        return $(
                $(13, false), 
                $(17, false), 
                $(18, true), 
                $(22, true));
    }

    
/************************************************************************/    
    @Test
    @Parameters
    public void personIsAdultV3(int age, boolean valid) throws Exception {
        assertEquals(valid, new Person(age).isAdult());
    }

    private Object[] parametersForPersonIsAdultV3() {
        return $(
                     $(13, false),
                     $(17, false),
                     $(18, true),
                     $(22, true)
                );
    }
  
    
/************************************************************************/
    @Test
    @Parameters
    public void personIsAdultV4(Person person, boolean valid) throws Exception {
        assertThat(person.isAdult(), is(valid));
    }

    private Object[] parametersForPersonIsAdultV4() {
        return $(
                     $(new Person(13), false),
                     $(new Person(17), false),
                     $(new Person(18), true),
                     $(new Person(22), true)
                );
    }    
    

/************************************************************************/    
    @Test
    @Parameters(source = PersonProvider.class)
    public void personIsAdultV5(Person person, boolean valid) {
        assertThat(person.isAdult(), is(valid));
    }    
    
    
    public static class PersonProvider {
        public static Object[] provideAdults() {
            return $(
                        $(new Person(25), true),
                        $(new Person(32), true)
                   );
        }

        public static Object[] provideTeens() {
            return $(
                        $(new Person(12), false),
                        $(new Person(17), false)
                   );
        }
    }    
    
/************************************************************************/    
    public static class Person {
        private int age;

        public Person(int age) {
            this.age = age;
        }

        public boolean isAdult() {
            return age >= 18;
        }

        @Override
        public String toString() {
            return "Person of age: " + age;
        }
    }
}
