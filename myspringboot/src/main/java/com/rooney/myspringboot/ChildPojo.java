package com.rooney.myspringboot;

public class ChildPojo {
	String aString;
	int anInt;
	
	
	public ChildPojo(String aString, int anInt) {
		super();
		this.aString = aString;
		this.anInt = anInt;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aString == null) ? 0 : aString.hashCode());
		result = prime * result + anInt;
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
		ChildPojo other = (ChildPojo) obj;
		if (aString == null) {
			if (other.aString != null)
				return false;
		} else if (!aString.equals(other.aString))
			return false;
		if (anInt != other.anInt)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChildPojo [aString=" + aString + ", anInt=" + anInt + "]";
	}
	
	
}
