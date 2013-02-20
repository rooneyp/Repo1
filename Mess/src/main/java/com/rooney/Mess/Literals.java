package com.rooney.Mess;

import java.math.BigDecimal;

/**
* Output is:
* int 1
* int -2147483648
* double 1.1111111111111112
* float 1.1
* double 1.1
* int 1
* long 1
* http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10
*/
public class Literals {
    public static void main(String[] args) {
        foo((byte)1); //byte
        foo(1); //int
        foo(1); //int
        foo(2147483647);
        foo(2147483648L); //needs L 
        foo(1L); //long
        foo(Integer.MAX_VALUE + 1); //broken it needs 'L' added. so its illegal?
        foo(1.1111); //double
        foo(1.1111d); //double
        foo(1.1111f); //float
        foo("1"); //string. problem is when to interpret as String vs non String. 
        // option 1) assume as number and string expressions always use ""+. 
        //           This does not work for Strings, so do we always assume String, but how to parse to a number using a method, or 
        //           So we have to statically parse (no as we know numeric type)?? OR (opt 2)rely on what 'kind' of operation parent is, and fail at runtime if model doesn't make sense?
                        
        // option 2) if parent expr/cond is String based then surround in ""
        
        // option3) have attribute: String/Numeric. This works for inlining if Java literal rules used.
        
        //above works when using 
        //** what about byte, only applies to LHS assignment
        
        byte b = (byte) 128; //messed up value, can we validate this?
        foo(b);
        
        //or just parse numbers using BigDecimal 
        BigDecimal bd = new BigDecimal("1111111111111111111111111111111.12345");
        System.out.println(bd.toPlainString());
        System.out.println(bd.toString());
        System.out.println(bd.scale());
        
        //if no scale: treat as whole no. if > Int.MAX then append 'L'
        //else treat as double (no need to add 'D' as this is the default). Do not try and reduce to float (as this is hard/impossible with where the decimal place is)
        //for byte, treat as int
        
        System.out.println("int division: " + ( (double)(100/2) ));
        
        Character character = new Character('b');
        int i = character;
        Integer bigI = Integer.valueOf(character);
        
        float f = character;
        
//        byte b1 = character; illegal
//        byte b2 =   
        
        int i1 = (int) 100f;
        
    }

    
    
    static void foo(byte b) {
        System.out.println("byte " + b);
    }
    
    static void foo(int i) {
        System.out.println("int " + i);
    }

    static void foo(long l) {
        System.out.println("long " + l);
    }

    static void foo(double d) {
        System.out.println("double " + d);
    }

    static void foo(float f) {
        System.out.println("float " + f);
    }
    
    static void foo(String s) {
        System.out.println("String " + s);
    }
}

