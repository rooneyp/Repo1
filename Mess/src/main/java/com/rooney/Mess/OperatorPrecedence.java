package com.rooney.Mess;

public class OperatorPrecedence {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        precedence();
        numberTypeDivisionAndMultiplication();
//        stringFormatting();
        
    }
    //TODO
    public static void numberTypeDivisionAndMultiplication() {
        float a = 3; //how to 
        int b = 6;
        
        // invalid int result = (a / b);
//        float floatResult
//        System.out.println("result = " + floatResult);
        
        System.out.println("(1.0 / 0.0) + 2.0 =" +  ( (1.0 / 0.0) + 2.0) );
    }

    private static void stringFormatting() {
        String stringVar = "aString";
        float floatVar = 1.2F;
        int intVar = 99;
        System.out.printf("The value of the float " +
                "variable is %2.2f, while " +
                "the value of the " + 
                "integer variable is %d, " +
                "and the string is %s", 
                floatVar, intVar, stringVar);
        
    }


    public static void precedence() {
        System.out.println("20 + 6 / 3 = " + (20 + 6 / 3));
        System.out.println("(20 + 6) / 3 = " + ((20 + 6) / 3));
    }

}
