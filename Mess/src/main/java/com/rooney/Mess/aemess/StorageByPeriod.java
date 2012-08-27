package com.rooney.Mess.aemess;

import java.util.HashMap;
import java.util.Map;

public class StorageByPeriod {

	private Map<String, Map<DimKey, AggData>> storage;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new StorageByPeriod().go();

	}

	private void go() {
		storage = new HashMap<String, Map<DimKey, AggData>>();

		// 12:01 key: 1,2,3
		aggregate("12:01", "1", "2", "3");
		aggregate("12:01", "1", "2", "3");
		
		aggregate("12:01", "1", "2", "4");
		aggregate("12:01", "1", "2", "4");
		
		aggregate("12:02", "1", "2", "3");
		aggregate("12:02", "1", "2", "3");
		
		dump(storage);

	}

	private void dump(Map<String, Map<DimKey, AggData>> storage2) {
		System.out.println(storage2);
		
	}

	private void aggregate(String period, String keyA, String keyB, String keyC) {
		DimKey dimKey = new DimKey(keyA, keyB, keyC);

		Map<DimKey, AggData> aggPeriod = storage.get(period);

		if (aggPeriod == null) {
			aggPeriod = new HashMap<StorageByPeriod.DimKey, StorageByPeriod.AggData>();
			storage.put(period, aggPeriod);
		} 
		aggDataAndStore(dimKey, aggPeriod);

	}

	private void aggDataAndStore(DimKey dimKey, Map<DimKey, AggData> aggPeriod) {
		AggData aggData = aggPeriod.get(dimKey);
		if (aggData == null) {
			aggData = new AggData();
			aggPeriod.put(dimKey, aggData);
		} 
		aggData.measure++;
	}

	public class DimKey {
		String a;
		String b;
		String c;

		@Override
		public String toString() {
			return "DimKey [a=" + a + ", b=" + b + ", c=" + c + "]";
		}

		public DimKey(String a, String b, String c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			result = prime * result + ((c == null) ? 0 : c.hashCode());
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
			DimKey other = (DimKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (b == null) {
				if (other.b != null)
					return false;
			} else if (!b.equals(other.b))
				return false;
			if (c == null) {
				if (other.c != null)
					return false;
			} else if (!c.equals(other.c))
				return false;
			return true;
		}

		private StorageByPeriod getOuterType() {
			return StorageByPeriod.this;
		}

	}

	public class AggData {
		int measure;

		@Override
		public String toString() {
			return "AggData [measure=" + measure + "]";
		}

	}
}
