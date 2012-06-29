package com.rooney.Mess;

import java.util.HashMap;
import java.util.Map;

public class GAPHashMap {
    public static void main(String[] args) {
        new GAPHashMap().go();
    }

    private void go() {
        Map<Key, Value> map = new HashMap<Key, Value>();
        
        System.out.println("put one");
        map.put(new Key(new String("one")), new Value(1));
        
        System.out.println("get one");
        System.out.println(map.get(new Key(new String("one"))));
    }

    public class Value {
        int val;

        public Value(int val) {
            this.val = val;
        }

        @Override
        public int hashCode() {
            System.out.println("Value hashcode: " + val);
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + val;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("Value equals: " + val);
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Value other = (Value) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (val != other.val)
                return false;
            return true;
        }

        private GAPHashMap getOuterType() {
            return GAPHashMap.this;
        }
        
    }
    
    public class Key {
        String name;

        public Key(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            System.out.println("Key hashcode: " + name);
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("Key equals: " + name);
            
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Key other = (Key) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        private GAPHashMap getOuterType() {
            return GAPHashMap.this;
        }
        
        
    }
}
