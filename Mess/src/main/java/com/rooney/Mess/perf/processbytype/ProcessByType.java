package com.rooney.Mess.perf.processbytype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessByType {

	private static final int EXE_ITERATIONS = 100000;
	private static final int WARMUP_ITERATIONS = 10;
	private static final int NUM_INSTANCES_OF_EACH_TYPE = 10;
	private static final int NUM_TYPES = 10;

	/**
	 * @param args
	 * @param logResults TODO
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		codegen();
//		Thread.sleep(3000);
		
		List<Type> generated = genData();
		Collections.shuffle(generated);
		System.out.println(""+ generated.size() + generated);
		
		Type[] generatedArray = generated.toArray(new Type[]{});
		
		runTestInstanceof(generatedArray);
		
		runTestGetTypeAsString(generatedArray);
		
		runTestGetTypeAsClass(generatedArray);
		
//		runTestMethodOverriding(generatedArray);
		
		runTestMap(generatedArray);
	}

	private static void runTestMap(Type[] generatedArray) {
		Map<Class<? extends Type>, Type> typeMap = new HashMap<Class<? extends Type>, ProcessByType.Type>();
		Type discard =null;
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type0.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type1.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type2.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type3.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type4.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type5.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type6.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type7.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type8.class, discard);
		typeMap.put(com.rooney.Mess.perf.processbytype.ProcessByType.Type9.class, discard);
		
		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
			testMapClassAsKey(generatedArray, false, typeMap);
		}
		System.out.println("Warmed up. iterations = " + WARMUP_ITERATIONS);
		testMapClassAsKey(generatedArray, true, typeMap);
	}	
			
	
//	private static void runTestMethodOverriding(Type[] generatedArray) {
//		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
//			testMethodOverriding(generatedArray, false);
//		}
//		System.out.println("Warmed up. iterations = " + WARMUP_ITERATIONS);
//		testMethodOverriding(generatedArray, true);
//	}	
	
	private static void runTestGetTypeAsClass(Type[] generatedArray) {
		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
			testGetTypeAsClass(generatedArray, false);
		}
		System.out.println("Warmed up. iterations = " + WARMUP_ITERATIONS);
		testGetTypeAsClass(generatedArray, true);
	}	
	
	private static void runTestGetTypeAsString(Type[] generatedArray) {
		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
			testGetTypeAsString(generatedArray, false);
		}
		System.out.println("Warmed up. iterations = " + WARMUP_ITERATIONS);
		testGetTypeAsString(generatedArray, true);
	}	
	

	private static void runTestInstanceof(Type[] generatedArray) {
		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
			testInstanceof(generatedArray, false);
		}
		System.out.println("Warmed up. iterations = " + WARMUP_ITERATIONS);
		testInstanceof(generatedArray, true);
	}


	//Class key
	//simple code, the map can be initialised by the framework and avoid more codegen
	private static void testMapClassAsKey(Type[] generatedArray, boolean logResults, Map<Class<? extends Type>, Type> typeMap) {
		Type discard =null;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < EXE_ITERATIONS; i++) { 
			for (Type type: generatedArray) {
				discard = typeMap.get(type.getClass());
				discard = type;
			}
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		if(logResults) {
			System.out.println("testMapClassAsKey called " + EXE_ITERATIONS + " ran in " + duration + "ms");
		}
	}			
	
	
	private static void testMethodOverriding(Type[] generatedArray, boolean logResults) {
		Type discard =null;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < EXE_ITERATIONS; i++) { 
			for (Type type: generatedArray) {
//				chunk(type, discard); NOT WORKING
			}
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		if(logResults) {
			System.out.println("testGetTypeAsClass called " + EXE_ITERATIONS + " ran in " + duration + "ms");
		}
	}		
	
	
	
	private static void testGetTypeAsClass(Type[] generatedArray, boolean logResults) {
		Type discard;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < EXE_ITERATIONS; i++) { 
			for (Type type: generatedArray) {
				if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type0.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type1.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type2.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type3.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type4.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type5.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type6.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type7.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type8.class)) {
					discard = type;
				}
				else if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type9.class)) {
					discard = type;
				}
				
			}
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		if(logResults) {
			System.out.println("testGetTypeAsClass called " + EXE_ITERATIONS + " ran in " + duration + "ms");
		}
	}		
	
	private static void testGetTypeAsString(Type[] generatedArray, boolean logResults) {
		Type discard;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < EXE_ITERATIONS; i++) { 
			for (Type type: generatedArray) {
				if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type0")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type1")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type2")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type3")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type4")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type5")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type6")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type7")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type8")) {
					discard = type;
				}
				else if(type.getType().equals("com.rooney.Mess.perf.processbytype.ProcessByType.Type9")) {
					discard = type;
				}
			}
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		if(logResults) {
			System.out.println("testGetTypeAsString called " + EXE_ITERATIONS + " ran in " + duration + "ms");
		}
	}	
	
	
	//this would be codegen'd as a light subclass of DecoderContainer.decoder
	//we just need the binRecordType attribute in the decode model xml
	private static void testInstanceof(Type[] generatedArray, boolean logResults) {
		Type discard;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < EXE_ITERATIONS; i++) { 
			for (Type type: generatedArray) {
				if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type0) { 
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type1) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type2) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type3) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type4) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type5) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type6) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type7) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type8) {
					discard = type;
				}
				else if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type9) {
					discard = type;
				}
			}
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		if(logResults) {
			System.out.println("testInstanceof called " + EXE_ITERATIONS + " ran in " + duration + "ms");
		}
	}

	private static List<Type> genData() throws Exception {
		List<Type> types = new ArrayList<Type>();
		
		for (int i = 0; i < NUM_INSTANCES_OF_EACH_TYPE; i++) {
			for (int j = 0; j < NUM_TYPES; j++) {
				types.add((Type) Class.forName("com.rooney.Mess.perf.processbytype.ProcessByType$Type"+j).newInstance() );
			}
			
		}
		
		return types;
	}

	private static void codegen() {
		for (int i = 0; i < NUM_TYPES; i++) {
//			System.out.println(
//					"\t\tpublic static class Type"+i+" implements Type {\n" +
//					"\t\t\tpublic String getType() {\n" +
//					"\t\t\t\treturn \"com.rooney.Mess.perf.processbytype.ProcessByType.Type"+i+"\";\n" +
//					"\t\t\t}\n" +
//					"\t\t}\n");
//			System.out.println(
//					"\t\t\telse if(type instanceof com.rooney.Mess.perf.processbytype.ProcessByType.Type"+i+") {\n" +
//				    "\t\t\t\tdiscard = type;\n" +
//				    "\t\t\t}");
//			System.out.println(
//					"\t\t\telse if(type.getType().equals(\"com.rooney.Mess.perf.processbytype.ProcessByType.Type"+i+"\") {\n" +
//				    "\t\t\t\tdiscard = type;\n" +
//				    "\t\t\t}");
//			System.out.println(
//					"\t\t\telse if(type.getClass().equals(com.rooney.Mess.perf.processbytype.ProcessByType.Type"+i+".class)) {\n" +
//							"\t\t\t\tdiscard = type;\n" +
//					"\t\t\t}");
//			
			
			System.out.println(
					"\t\tpublic static void chunk(Type"+i+" type, Type discard) {\n" +
					"\t\t\tdiscard = type;\n" +
					"\t\t}\n");
			
			
		}
	}

	public static interface Type {
		String getType();
	}
	
	public static class Type0 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type0";
		}
	}

	public static class Type1 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type1";
		}
	}

	public static class Type2 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type2";
		}
	}

	public static class Type3 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type3";
		}
	}

	public static class Type4 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type4";
		}
	}

	public static class Type5 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type5";
		}
	}

	public static class Type6 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type6";
		}
	}

	public static class Type7 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type7";
		}
	}

	public static class Type8 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type8";
		}
	}

	public static class Type9 implements Type {
		public String getType() {
			return "com.rooney.Mess.perf.processbytype.ProcessByType.Type9";
		}
	}

//	public static void chunk(Object type, Type discard) {
//		System.out.println("chunk(Object type, Type discard) {");
//	}
	
	public static void chunk(Type0 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type1 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type2 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type3 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type4 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type5 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type6 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type7 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type8 type, Type discard) {
		discard = type;
	}

	public static void chunk(Type9 type, Type discard) {
		discard = type;
	}
	
	
}
