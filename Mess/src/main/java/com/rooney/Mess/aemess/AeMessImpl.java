package com.rooney.Mess.aemess;

import com.rooney.Mess.aemess.AeKernalTypes.AEDataType;
import com.rooney.Mess.aemess.AeKernalTypes.Step;

public class AeMessImpl {
	public static void main(String[] args) {

	}
	
	class MyDecodeStep implements Step {
		public AEDataType process(AEDataType aEDataType) {
			return aEDataType;
		}
	}

	class MyMapStep implements Step {
		public AEDataType process(AEDataType aEDataType) {
			return aEDataType;
		}
	}	
	
	
}
