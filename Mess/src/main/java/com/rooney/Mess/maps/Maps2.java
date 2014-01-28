package com.rooney.Mess.maps;

import java.util.HashMap;
import java.util.Map;


public class Maps2 {
	public static void main(String[] args) throws Exception {
		Map<Foo, String> m = new HashMap<Foo, String>();
		for (int i = 0; i < 10; i++) {
	        Foo key = new Foo("" + i);
	        System.out.println("adding " + key);
			m.put(key, "" + i); //hashcode then multiple equals is called on it, passing in existing keys when putting into and Entry chain, and there are already entries there
        }
		
		System.out.println("GETTING ***************************************************************************");
		for (int i = 0; i < 10; i++) {
	        Foo key = new Foo("" + i);
	        System.out.println("Getting " + key);
			m.get(key); //equals is called when putting into and Entry chain, and there are already entries there
        }
		
//		System.out.println(MapUtils.dumpMapKeyCollisions(m));
	}

	
	public static class Foo {
		String name;
		public Foo(String name) {
	        super();
	        this.name = name;
        }

		@Override
        public int hashCode() {
			System.out.println("hashCode " + this);
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        return 1;
        }

		@Override
        public boolean equals(Object obj) {
			Foo foo = (Foo) obj;
			
			System.out.println("equals " + this + " called with param " + obj);
	        if (this == obj)
		        return true;
	        if (obj == null)
		        return false;
	        if (getClass() != obj.getClass())
		        return false;
	        Foo other = (Foo) obj;
	        if (name == null) {
		        if (other.name != null)
			        return false;
	        } else if (!name.equals(other.name))
		        return false;
	        return true;
        }

		@Override
        public String toString() {
	        return  System.identityHashCode(this) + "-" + name;
        }
		
		
	}
	
}
