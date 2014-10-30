package com.rooney.Mess.json;

import static com.rooney.Mess.json.JsonSimpleExample.Keywords.AND;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.EQUALS;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.EQUALS_IN;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.FIELD;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.HASVALUE;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.ISTRUE;
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.OR;
/*
 * http://www.mkyong.com/java/json-simple-example-read-and-write-json/
 * https://code.google.com/p/json-simple/wiki/EncodingExamples
 * 
 * TODO look at Jackson
 * TODO look at RestEasy and Jackson for Rest services
SAMPLE JSON to represent
{
	"OR": {
		"AND": {
			"AND": {
				"HASVALUE" : {
		 			"FIELD" : "CallAnsweredTimeInMills",
		 			"VALUES": []
		 		},
				"ISTRUE" : {
		 			"FIELD" : "CallAnsweredTimeInMills",
		 			"VALUES": []
		 		}		 				
			},

			"EQUALS" : {
		 		"FIELD" : "CauseFamily",
		 		"VALUES": ["5"]
		 	}
		},
		"OR": {
			"AND": {
				"HASVALUE" : {
		 			"FIELD" : "CallAnsweredTimeInMills",
		 			"VALUES": []
		 		},
				"ISTRUE" : {
		 			"FIELD" : "CallAnsweredTimeInMills",
		 			"VALUES": []
		 		}		 				
			},
			"EQUALS_IN" : {
		 		"FIELD" : "CauseValue",
		 		"VALUES": ["-1","16"]
		 	}
		}
	}
}


 */
import static com.rooney.Mess.json.JsonSimpleExample.Keywords.VALUES;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializerProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.datatype.guava.GuavaModule;


public class JsonSimpleExample {
//	private static ObjectWriter prettyObjectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter(); //is this threadsafe???

	//TODO just use Composite and convert to MAP and List, then use default Jackson serialisation
	public static void main(String[] args) throws Exception, IOException, Exception {
//		customSerializerToAdvoidJsonArrayFromJavaList();
//		jacksonNestedOperatorAndOperandsUsingMap();
		jacksonNestedOperatorAndOperandsUsingComposite();
	}
	
	public static void jacksonMultiMapToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
	}
	
	public static void jacksonNestedOperatorAndOperandsUsingComposite() throws Exception {
//		Node a1 = buildNodeManually();
		Node a1 = buildNodeManuallyWithSameChildren();
		
//		printNode(a1, "");
		
		Map map = nodeToMap(a1);
		System.out.println(serialiseToJson(map,true));
		
		//WORKS but with arrays for children
//		JSONObject nodeToJson = nodeToJson(a1);
//		System.out.println(nodeToJson.toJSONString());
	}

	public static enum Keywords {
		OR, AND,HASVALUE,FIELD,VALUES,EQUALS,ISTRUE,EQUALS_IN
	}
	
	
	// does this cause a problem when converting to MAP and then to String
	//could we use multimap?
	//OR
	//	EQUALS
	//		field  a
	//		values b
	//	EQUALS
	//		field  c
	//		values d
	private static Node buildNodeManuallyWithSameChildren() {
		Node a1 = Node.buildRootNode(OR);
		Node a1b1 = a1.addChild(EQUALS);
		a1b1.addLeaf(FIELD, "CallAnsweredTimeInMills").addLeaf(VALUES, "101");
		
		Node a1b2 = a1.addChild(EQUALS);
		a1b2.addLeaf(FIELD, "CallAnsweredTimeInMills").addLeaf(VALUES, "999");
		
		
		
		return a1;
	}	
	
	private static Node buildNodeManually() {
		Node a1 = Node.buildRootNode(OR);
		Node a1b1 = a1.addChild(AND);
		Node a1b1c1 = a1b1.addChild(AND);
		Node a1b1c1d1 = a1b1c1.addChild(HASVALUE).addLeaf(FIELD, "CallAnsweredTimeInMills").addLeaf(VALUES, "");
		
		a1b1.addChild(EQUALS).addLeaf(FIELD, "CauseFamily").addLeaf(VALUES, "5");
		
		
		Node a1b1c1d2 = a1b1c1.addChild(ISTRUE);
		a1b1c1d2.addLeaf(FIELD, "CallAnsweredTimeInMills");
		a1b1c1d2.addLeaf(VALUES, "");
		
		
		Node a1b2 = a1.addChild(OR);
		Node a1b2c1 = a1b2.addChild(AND);
		Node a1b2c1d1 = a1b2c1.addChild(HASVALUE).addLeaf(FIELD, "CallAnsweredTimeInMills").addLeaf(VALUES, "");
		Node a1b2c1d2 = a1b2c1.addChild(ISTRUE).addLeaf(FIELD, "CallAnsweredTimeInMills").addLeaf(VALUES, "");
		
		Node a1b2c2 = a1b2.addChild(EQUALS_IN).addLeaf(FIELD, "CauseValue").addLeaf(VALUES, "-1","16");
		return a1;
	}
	
	private static Node buildNodeManuallyStrings() {
		Node a1 = Node.buildRootNode("OR");
		Node a1b1 = a1.addChild("AND");
		Node a1b1c1 = a1b1.addChild("AND");
		Node a1b1c1d1 = a1b1c1.addChild("HASVALUE").addLeaf("FIELD", "CallAnsweredTimeInMills").addLeaf("VALUES", "");
		
		a1b1.addChild("EQUALS").addLeaf("FIELD", "CauseFamily").addLeaf("VALUES", "5");
		
		
		Node a1b1c1d2 = a1b1c1.addChild("ISTRUE");
		a1b1c1d2.addLeaf("FIELD", "CallAnsweredTimeInMills");
		a1b1c1d2.addLeaf("VALUES", "");
		
		
		Node a1b2 = a1.addChild("OR");
		Node a1b2c1 = a1b2.addChild("AND");
		Node a1b2c1d1 = a1b2c1.addChild("HASVALUE").addLeaf("FIELD", "CallAnsweredTimeInMills").addLeaf("VALUES", "");
		Node a1b2c1d2 = a1b2c1.addChild("ISTRUE").addLeaf("FIELD", "CallAnsweredTimeInMills").addLeaf("VALUES", "");
		
		Node a1b2c2 = a1b2.addChild("EQUALS_IN").addLeaf("FIELD", "CauseValue").addLeaf("VALUES", "-1","16");
		return a1;
	}
	
	private static JSONObject nodeToJsonManually(Node node) {

		if(node.isLeaf()) {
			JSONObject obj = new JSONObject();
			obj.put(node.id, Arrays.asList(node.leafValue).toString()); //TODO just concat
			return obj;
		}
		
		JSONArray childList = new JSONArray();
		for(Node child: node.children) {
			JSONObject obj = new JSONObject();
			childList.add(nodeToJsonManually(child));
		}
		
		JSONObject obj = new JSONObject();
		if(childList.size() == 1) {
			obj.put(node.id, childList.get(0));
		} else {
			obj.put(node.id, childList); //TODO maybe change from array to comma separated json objects?
		}
		
		return obj;		
	}

	public static Map nodeToMap(Node node) {
		if (node.isLeaf()) {
			Map map = new LinkedHashMap();
			map.put(node.id, node.leafValue);
			return map;
		}

		Map mergedChildrenMap = new LinkedHashMap();
		for (Node child : node.children) {
			mergedChildrenMap.putAll(nodeToMap(child));
		}

		Map thisMap = new LinkedHashMap();
		thisMap.put(node.id, mergedChildrenMap);
		return thisMap;
	}
	
	public static void printNode(Node node, String indent) {
		System.out.print(indent + node.id);
		
		if(node.isLeaf()) {
			System.out.print(indent + Arrays.asList(node.leafValue));
		}
		
		System.out.println();
		
		for(Node child: node.children) {
			printNode(child, indent + "  ");
		}
	}
	
	//seems we have to build from bottom up, which is a pain if we are visiting from top down
	public static void jacksonNestedOperatorAndOperandsUsingMap() throws Exception {
		Map a1 = new LinkedHashMap();
		
		
		Map a1b1c1d2e1 = new LinkedHashMap();
		a1b1c1d2e1.put("FIELD", "CallAnsweredTimeInMills");
		a1b1c1d2e1.put("VALUES", null);
		
		Map a1b1c1d2 = new LinkedHashMap();
		a1b1c1d2.put("ISTRUE", a1b1c1d2e1);
		//
		Map a1b1c1d1e1 = new LinkedHashMap();
		a1b1c1d1e1.put("FIELD", "CallAnsweredTimeInMills");
		a1b1c1d1e1.put("VALUES", null);
		
		Map a1b1c1d1 = new LinkedHashMap();
		a1b1c1d1.put("HASVALUE", a1b1c1d1e1);
		
		Map a1b1c1 = new LinkedHashMap();
		
		//FAIL: 	can't have 2 children if just use nested maps?
		//SOLN1: 	use List. But we default serialise is is output as list
		a1b1c1.put("AND", Arrays.asList(a1b1c1d1, a1b1c1d2)); 
		
		
		Map a1b1 = new LinkedHashMap();
		a1b1.put("AND", a1b1c1);
		
		a1.put("OR", a1b1);
		
		System.out.println(serialiseToJson(a1, true));	
	}

	//does add method returning new Child-Node make this easier to use in visitor
public static class Node {

	Object id;
	Node parent;
	Object[] leafValue;
	
	List<Node> children = new ArrayList<Node>();

	public static Node buildRootNode(Object id) {
		return new Node(id);
	}
	
	private Node(Object id) {
		this.id = id;
	}
	
	public Node(Object id, Node parent) {
		this.id = id;
		this.parent = parent; 
		parent.children.add(this);
	}
	
	public Node(Object id, Node parent, Object... leafValue) {
		this(id, parent);
		this.leafValue = leafValue;
	}

	/**
	 * Add child
	 * @param operator
	 * @return newly created child
	 */
	public Node addChild(Object id) {
		Node child = new Node(id, this);
		return child;
	}
	
	/**
	 * Add Leaf and retrurn current Node (not the leaf)
	 * @param id
	 * @param leafValue
	 * @return
	 */
	public Node addLeaf(Object id, Object... leafValue) {
		Node child = new Node(id, this, leafValue);
		return this;
	}
	
	public boolean isLeaf() {
		return leafValue != null;
	}

	@Override
	public String toString() {
		String childrenObject = "";
		if(children != null) {
			for (Node child : children) {
				childrenObject += child.id + ", "; 
			}
		}
		
		return "Node [id=" + id + ", parent=" + (parent==null? "": parent.id) + ", children=[" + childrenObject + "], leafValue=" + Arrays.toString(leafValue) + "]";
	}
	
	public static void printNode(Node node, String indent) {
		System.out.print(indent + node.id);
		
		if(node.isLeaf()) {
			System.out.print(indent + Arrays.asList(node.leafValue));
		}
		
		System.out.println();
		
		for(Node child: node.children) {
			printNode(child, indent + "  ");
		}
	}		
	
	//LinkedHashMap is a useful composite structure as Jackson easily serialises it, and children do not have to be an Array
	public static Map nodeToMap(Node node) {
		if (node.isLeaf()) {
			Map map = new LinkedHashMap();
			map.put(node.id, node.leafValue);
			return map;
		}

		Map mergedChildrenMap = new LinkedHashMap();
		for (Node child : node.children) {
			mergedChildrenMap.putAll(nodeToMap(child));
		}

		Map thisMap = new LinkedHashMap();
		thisMap.put(node.id, mergedChildrenMap);
		return thisMap;
	}	
	
}
	
	
	public static class NodeString {
		String id;
		NodeString parent;
		String[] leafValue;
		
		List<NodeString> children = new ArrayList<NodeString>();

		public static Node buildRootNode(String id) {
			return new Node(id);
		}
		
		private NodeString(String id) {
			this.id = id;
		}
		
		public NodeString(String id, NodeString parent) {
			this.id = id;
			this.parent = parent; 
			parent.children.add(this);
		}
		
		public NodeString(String id, NodeString parent, String... leafValue) {
			this(id, parent);
			this.leafValue = leafValue;
		}

		/**
		 * Add child
		 * @param operator
		 * @return newly created child
		 */
		public NodeString addChild(String id) {
			NodeString child = new NodeString(id, this);
			return child;
		}
		
		/**
		 * Add Leaf and retrurn current Node (not the leaf)
		 * @param id
		 * @param leafValue
		 * @return
		 */
		public NodeString addLeaf(String id, String... leafValue) {
			NodeString child = new NodeString(id, this, leafValue);
			return this;
		}
		
		public boolean isLeaf() {
			return leafValue != null;
		}

		@Override
		public String toString() {
			String childrenString = "";
			if(children != null) {
				for (NodeString child : children) {
					childrenString += child.id + ", "; 
				}
			}
			
			return "Node [id=" + id + ", parent=" + (parent==null? "": parent.id) + ", children=[" + childrenString + "], leafValue=" + Arrays.toString(leafValue) + "]";
		}
	}
	
	private static void jacksonOrderedCreationWithNestedData() throws Exception, JsonMappingException, IOException {
		Map obj = buildNestedLinkedHashMap();
		
		ObjectMapper mapper = new ObjectMapper();
//		System.out.println("Default output:"+mapper.writeValueAsString(obj));
		System.out.println("Pretty printing:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));		
//		System.out.println("Pretty printing deprecated:\n"+mapper.defaultPrettyPrintingWriter().writeValueAsString(obj));		
		
	}

	private static void jsonSimpleOrderedCreationWithNestedData() {
		Map obj = buildNestedLinkedHashMap();
		
		String jsonText = JSONValue.toJSONString(obj);
		System.out.print(jsonText);
	}

	private static Map buildNestedLinkedHashMap() {
		Map obj = new LinkedHashMap();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);

		Map nestedObj = new LinkedHashMap();
		nestedObj.put("foo1", "bar1");
		nestedObj.put("foo2", "bar2");
		obj.put("foo", nestedObj);
		return obj;
	}

	private static void jsonSimpleOrderedCreation() {
		Map obj = new LinkedHashMap();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);
		String jsonText = JSONValue.toJSONString(obj);
		System.out.print(jsonText);

	}

	private static void jsonSimpleUnorderedCreation() {
		// no order guaranteeded
		JSONObject obj = new JSONObject();
		obj.put("name", "mkyong.com");
		obj.put("age", new Integer(100));

		JSONArray list = new JSONArray();
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");

		obj.put("messages", list);

		// FileWriter file = new FileWriter("c:\\test.json");
		// file.write(obj.toJSONString());
		// file.flush();
		// file.close();
		System.out.print(obj);
	}

	private static String serialiseToJson(Object obj, boolean pretty) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
		
		//inject custom serializer for MyList class
//		SimpleModule module = new SimpleModule("MyList", Version.unknownVersion());
//		module.addSerializer(MyList.class, new ItemSerializer());
//		objectMapper.registerModule(module);
		
		if(pretty) {
			ObjectWriter writerWithDefaultPrettyPrinter = objectMapper.writerWithDefaultPrettyPrinter(); 
			return writerWithDefaultPrettyPrinter.writeValueAsString(obj);
		} else {
			return objectMapper.writeValueAsString(obj);
		}
	}	
	
	//TODO finish this off
	private static void customSerializerToAdvoidJsonArrayFromJavaList() throws Exception {
		Map a1b1c1d2 = new LinkedHashMap();
		a1b1c1d2.put("ISTRUE", "xxx");
		Map a1b1c1d1 = new LinkedHashMap();
		a1b1c1d1.put("HASVALUE", "yyy");
		
		Map root = new LinkedHashMap();
		MyList myList = new MyList();
		myList.addAll(Arrays.asList(a1b1c1d1, a1b1c1d2));
		
		root.put("AND", myList); //Arrays.asList(a1b1c1d1, a1b1c1d2) gives JSON Array
		
		System.out.println(serialiseToJson(root, true));
	}

	//new type to force needing customing serialisation  
	public static class MyList {
		List list = new ArrayList();
		
		public List getList() {
			return list;
		}

		public void add(Object o) {
			list.add(o);
		}
		
		public void addAll(List list) {
			for (Object object : list) {
				add(object);
			}
		}
	}
	
	//TODO finish this off. how to serialise one field, and then let Jackson parse its nested structure. It is nearly working and processing list of maps, but complains about cannot startObject cause name i
	public static class ItemSerializer extends JsonSerializer<MyList> {
		@Override
		public void serialize(MyList value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			
			List list = value.getList();
			
			for (Object map : list) { //I want to handle My List by looping on each entry have jackson serialise it automatically (they will be a map)
//				provider.findTypedValueSerializer(LinkedHashMap.class).serialize(map, jgen, provider);
				
				JsonSerializer<Object> findTypedValueSerializer = provider.findTypedValueSerializer(map.getClass(), true, null);
				findTypedValueSerializer.serialize(map, jgen, provider);
				
//				jgen.writeStartObject();
//				jgen.writeFieldName(map);
//			jgen.writeNumberField("id", value.id);
//			jgen.writeStringField("itemName", value.itemName);
//			jgen.writeNumberField("owner", value.owner.id);
//				jgen.writeEndObject();
				
			}
			
		}
	}	
}