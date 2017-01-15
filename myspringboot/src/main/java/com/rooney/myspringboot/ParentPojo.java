package com.rooney.myspringboot;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentPojo {
	String aString;
	int anInt;
	
//	List<ChildPojo> children = new ArrayList<>();
	
	
	
	public ParentPojo(String aString, int anInt) {
		super();
		this.aString = aString;
		this.anInt = anInt;
	}

//	public ParentPojo(String aString, int anInt, List<ChildPojo> children) {
//		super();
//		this.aString = aString;
//		this.anInt = anInt;
//		this.children = children;
//	}

	public String getaString() {
		return aString;
	}

	public void setaString(String aString) {
		this.aString = aString;
	}

	public int getAnInt() {
		return anInt;
	}

	public void setAnInt(int anInt) {
		this.anInt = anInt;
	}

//	public List<ChildPojo> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<ChildPojo> children) {
//		this.children = children;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aString == null) ? 0 : aString.hashCode());
		result = prime * result + anInt;
//		result = prime * result + ((children == null) ? 0 : children.hashCode());
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
		ParentPojo other = (ParentPojo) obj;
		if (aString == null) {
			if (other.aString != null)
				return false;
		} else if (!aString.equals(other.aString))
			return false;
		if (anInt != other.anInt)
			return false;
//		if (children == null) {
//			if (other.children != null)
//				return false;
//		} else if (!children.equals(other.children))
//			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParentPojo [aString=" + aString + ", anInt=" + anInt + ", children=" + "]"; //children + "]";
	}

}
