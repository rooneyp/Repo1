package com.rooney.kyro;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;

public class TestSerDe {

	private LoadBatchRequestVOKyroFriendly batchRequest;

	@Before
	public void setup() {
		// guave immutable map not liked by vanilla kyro

		String batchNo = "1";

		List<Map<String, Object>> recordsHeaderValues = Lists.newArrayList();
		Map<String, Object> record1 = new HashMap<String, Object>();
		record1.put("key1", "val1");
		record1.put("key2", "val2");
		Map<String, Object> record2 = new HashMap<String, Object>();
		record2.put("key11", "val11");
		record2.put("key12", "val12");

		recordsHeaderValues.add(record1);
		recordsHeaderValues.add(record2);

		batchRequest = LoadBatchRequestVOKyroFriendly.create("1", "Batch [" + batchNo + "]", LoaderAction.INSERT,
				recordsHeaderValues);
	}

	@Test
	public void testComplexObjectByHand() throws Exception {

		Kryo kryo = new Kryo();
		Output output = new Output(new FileOutputStream("file.bin"));
		kryo.writeObject(output, batchRequest);
		output.close();

		Input input = new Input(new FileInputStream("file.bin"));
		LoadBatchRequestVOKyroFriendly someObject = kryo.readObject(input, LoadBatchRequestVOKyroFriendly.class);
		input.close();

		assertEquals(batchRequest, someObject);
	}

	@Test
	public void testPersonByHand() throws Exception {
		Person dave = new Person();
		dave.setName("dave");
		dave.setAge(100);

		Kryo kryo = new Kryo();
		Output output = new Output(new FileOutputStream("file.bin"));
		kryo.writeObject(output, dave);
		output.close();

		Input input = new Input(new FileInputStream("file.bin"));
		Person someObject = kryo.readObject(input, Person.class);
		input.close();

		assertEquals(dave, someObject);
	}

	public static class Person {
		String name;
		int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}
}
