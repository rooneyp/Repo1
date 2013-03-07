package com.rooney.Mess.maps;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    /**
     * 
     */
    public static void main(String[] args) {
        Integer i = 1;
        System.out.write(i);
        System.out.flush();
        
//        hashmapCollisions();
    }

    /**
     * for each entry that has a hashcode collision, equals is called
     * On put
     *  Hashmap has a table of Entries.
     *  key.hashcode points to an entry
     *  if there already is an key present in the table
     *      then check if key objects are the same of if newKey.equals(currentKey)
     *      if true
     *          overwrite and return old value/
     *      else
     *          add new Key as next Entry on current Key (Key is a linked list)
     */
    public static void hashmapCollisions() {
        Foo f1 = new Foo("f1", 100, true);
        Foo f2 = new Foo("f2", 100, false); //equals called, returns false, so we get collision, and this gives to entries. 
        //                                    F2 seems to hold the main entry in the table and its linked list holds ref to F1
//        Foo f2 = new Foo("f2", 999, true); //equals not called as no hashcode collision occurs

        Map<Foo, String> map = new HashMap<Maps.Foo, String>();
        map.put(f1, "f1");
        map.put(f2, "f2");
        
        System.out.println("Stored size: " + map.size());
        
        map.get(f1); //hashcode and equals on f1 called
        map.get(f2); //hashcode on f2 only called. probably due to object instance matching
    }
    
    public static class Foo {
        String name;
        int hashcode;
        boolean equals;
        
        public Foo(String name, int hashcode, boolean equals) {
            super();
            this.name = name;
            this.hashcode = hashcode;
            this.equals = equals;
        }

        @Override
        public int hashCode() {
            System.out.println("hashcode called on " + name);
            return hashcode;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("equals called on " + name + " Obj pass in is " + obj);
            return equals;
        }

        @Override
        public String toString() {
            return name;
        }
        
    }

}
