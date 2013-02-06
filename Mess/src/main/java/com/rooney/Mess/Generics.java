package com.rooney.Mess;


public class Generics {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    private void callFooWithType() {
        this.<String>foo(); //needs the 'this.' to work
    }
    
    private <T> void foo(){          
    }
}
