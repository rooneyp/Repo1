package com.rooney.myspringboot;

//needs get/set and no arg ctor
public class MyResult {
    
    public MyResult() {
    }

    public String result;

    public MyResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}