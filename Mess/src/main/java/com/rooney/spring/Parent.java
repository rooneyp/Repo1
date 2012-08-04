package com.rooney.spring;

import java.util.Date;


public class Parent {
    private Child child;

    public Parent() {
        System.out.println("Parent created " + new Date());
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void scheduled() {
        System.out.println(this + " was scheduled " + new Date());
    }

}