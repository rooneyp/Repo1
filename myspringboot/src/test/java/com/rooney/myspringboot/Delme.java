package com.rooney.myspringboot;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Delme {

    @Test
    public void test() {
        Widget widget = new Widget();
        System.out.println(widget);
        
        BeanWrapper wrappedWidget = new BeanWrapperImpl(widget);
       
        wrappedWidget.setPropertyValue("myInt", "1");
        wrappedWidget.setPropertyValue("myString", 1);
        
        System.out.println(widget);
    }
    
    
    public static class Widget {
        int myInt;
        String myString;
        
        public int getMyInt() {
            return myInt;
        }
        public void setMyInt(int myInt) {
            this.myInt = myInt;
        }
        public String getMyString() {
            return myString;
        }
        public void setMyString(String myString) {
            this.myString = myString;
        }
        @Override
        public String toString() {
            return "Widget [myInt=" + myInt + ", myString=" + myString + "]";
        }
        
        
    }

}
