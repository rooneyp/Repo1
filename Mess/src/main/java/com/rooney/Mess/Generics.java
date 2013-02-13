package com.rooney.Mess;


public class Generics {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String foo = Generics.<String>any();

    }

    private void callFooWithType() {
        this.<String>foo(); //needs the 'this.' to work
    }
    
    private <T> void foo(){          
    }

    public static <T> T any() {
        return (T) null;
    }

}


