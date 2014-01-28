package com.rooney.Mess.maps;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    
    public static void main(String[] args) throws Exception {
	    Map realHM = new HashMap();
	    realHM.put(1, 2);
	    System.out.println(MapUtils.dumpMapKeyCollisions(realHM));    
	}

	public static String dumpMapKeyCollisions(Map<?,?> map) throws Exception {
		Field[] fs = map.getClass().getDeclaredFields();
		Object[] table = null;
		StringBuffer outputBuffer = new StringBuffer();
		
		for (java.lang.reflect.Field field : fs) {
			if (field.getName() == "table") {
				field.setAccessible(true);
				table = (Object[]) field.get(map);
				break;
			}
		}
		outputBuffer.append("HashMap.table count is ").append(table.length);
		
		Field entryField = null;
		
		for (Object object : table) { // loop on entry table to find one populated, and get a handle to the 'next' field on the Entry class
			if (object != null) {
				for (Field field : object.getClass().getDeclaredFields()) {
					if (field.getName() == "next") {
						field.setAccessible(true);
						entryField = field;
						break;
					}
				}
				if (entryField != null) {
					break;
				}
			}
		}
		
		if(entryField == null) {
			return "";
		}
		
		for (int i = 0; i < table.length; i++) {
			Object entry = table[i];
			if(entry == null) {
				continue;
			}
			
			int count = 0;
			
			while (entryField.get(entry) != null) { //access HashMap.Entry.next
				count++;
				entry = entryField.get(entry);
			}
	        outputBuffer.append(", Entry ").append(i).append(" collisions=").append(count);
		}		
		
		return outputBuffer.toString();
	}
}