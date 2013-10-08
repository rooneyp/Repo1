package com.rooney.perf.accessors;

import java.util.ArrayList;
import java.util.Arrays;

public class TestAccessors {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		codegen();
		
		AJavaBean bean = new AJavaBean();
		TestAccessors test = new TestAccessors();
		
		long startTime = System.currentTimeMillis();
		int invokeCount = 10000000;
		
		for(int i=0; i<invokeCount; i++) {
			test.callMethods(bean);
//			test.callFields(bean);
		}
		
		System.out.println("invokeCount " + invokeCount + " took " + (System.currentTimeMillis() - startTime) + "ms");
		
	}

	private static void codegen() {
		for(int i=0; i<100; i++) {
			//System.out.println("\t\tprivate long field" + i + " = " + i + ";");
//			System.out.println("\t\ta = bean.getField" + i + "();");
			System.out.println("\t\ta = bean.field" + i + ";");
			
		}
	}
	
	public void callFields(AJavaBean bean) {
		long a;
		a = bean.field0;
		a = bean.field1;
		a = bean.field2;
		a = bean.field3;
		a = bean.field4;
		a = bean.field5;
		a = bean.field6;
		a = bean.field7;
		a = bean.field8;
		a = bean.field9;
		a = bean.field10;
		a = bean.field11;
		a = bean.field12;
		a = bean.field13;
		a = bean.field14;
		a = bean.field15;
		a = bean.field16;
		a = bean.field17;
		a = bean.field18;
		a = bean.field19;
		a = bean.field20;
		a = bean.field21;
		a = bean.field22;
		a = bean.field23;
		a = bean.field24;
		a = bean.field25;
		a = bean.field26;
		a = bean.field27;
		a = bean.field28;
		a = bean.field29;
		a = bean.field30;
		a = bean.field31;
		a = bean.field32;
		a = bean.field33;
		a = bean.field34;
		a = bean.field35;
		a = bean.field36;
		a = bean.field37;
		a = bean.field38;
		a = bean.field39;
		a = bean.field40;
		a = bean.field41;
		a = bean.field42;
		a = bean.field43;
		a = bean.field44;
		a = bean.field45;
		a = bean.field46;
		a = bean.field47;
		a = bean.field48;
		a = bean.field49;
		a = bean.field50;
		a = bean.field51;
		a = bean.field52;
		a = bean.field53;
		a = bean.field54;
		a = bean.field55;
		a = bean.field56;
		a = bean.field57;
		a = bean.field58;
		a = bean.field59;
		a = bean.field60;
		a = bean.field61;
		a = bean.field62;
		a = bean.field63;
		a = bean.field64;
		a = bean.field65;
		a = bean.field66;
		a = bean.field67;
		a = bean.field68;
		a = bean.field69;
		a = bean.field70;
		a = bean.field71;
		a = bean.field72;
		a = bean.field73;
		a = bean.field74;
		a = bean.field75;
		a = bean.field76;
		a = bean.field77;
		a = bean.field78;
		a = bean.field79;
		a = bean.field80;
		a = bean.field81;
		a = bean.field82;
		a = bean.field83;
		a = bean.field84;
		a = bean.field85;
		a = bean.field86;
		a = bean.field87;
		a = bean.field88;
		a = bean.field89;
		a = bean.field90;
		a = bean.field91;
		a = bean.field92;
		a = bean.field93;
		a = bean.field94;
		a = bean.field95;
		a = bean.field96;
		a = bean.field97;
		a = bean.field98;
		a = bean.field99;
	}
	
	public void callMethods(AJavaBean bean) {
		long a;
		a = bean.getField0();
		a = bean.getField1();
		a = bean.getField2();
		a = bean.getField3();
		a = bean.getField4();
		a = bean.getField5();
		a = bean.getField6();
		a = bean.getField7();
		a = bean.getField8();
		a = bean.getField9();
		a = bean.getField10();
		a = bean.getField11();
		a = bean.getField12();
		a = bean.getField13();
		a = bean.getField14();
		a = bean.getField15();
		a = bean.getField16();
		a = bean.getField17();
		a = bean.getField18();
		a = bean.getField19();
		a = bean.getField20();
		a = bean.getField21();
		a = bean.getField22();
		a = bean.getField23();
		a = bean.getField24();
		a = bean.getField25();
		a = bean.getField26();
		a = bean.getField27();
		a = bean.getField28();
		a = bean.getField29();
		a = bean.getField30();
		a = bean.getField31();
		a = bean.getField32();
		a = bean.getField33();
		a = bean.getField34();
		a = bean.getField35();
		a = bean.getField36();
		a = bean.getField37();
		a = bean.getField38();
		a = bean.getField39();
		a = bean.getField40();
		a = bean.getField41();
		a = bean.getField42();
		a = bean.getField43();
		a = bean.getField44();
		a = bean.getField45();
		a = bean.getField46();
		a = bean.getField47();
		a = bean.getField48();
		a = bean.getField49();
		a = bean.getField50();
		a = bean.getField51();
		a = bean.getField52();
		a = bean.getField53();
		a = bean.getField54();
		a = bean.getField55();
		a = bean.getField56();
		a = bean.getField57();
		a = bean.getField58();
		a = bean.getField59();
		a = bean.getField60();
		a = bean.getField61();
		a = bean.getField62();
		a = bean.getField63();
		a = bean.getField64();
		a = bean.getField65();
		a = bean.getField66();
		a = bean.getField67();
		a = bean.getField68();
		a = bean.getField69();
		a = bean.getField70();
		a = bean.getField71();
		a = bean.getField72();
		a = bean.getField73();
		a = bean.getField74();
		a = bean.getField75();
		a = bean.getField76();
		a = bean.getField77();
		a = bean.getField78();
		a = bean.getField79();
		a = bean.getField80();
		a = bean.getField81();
		a = bean.getField82();
		a = bean.getField83();
		a = bean.getField84();
		a = bean.getField85();
		a = bean.getField86();
		a = bean.getField87();
		a = bean.getField88();
		a = bean.getField89();
		a = bean.getField90();
		a = bean.getField91();
		a = bean.getField92();
		a = bean.getField93();
		a = bean.getField94();
		a = bean.getField95();
		a = bean.getField96();
		a = bean.getField97();
		a = bean.getField98();
		a = bean.getField99();
		
		
	}

	public static class AJavaBean {
		private long field0 = 0;
		private long field1 = 1;
		private long field2 = 2;
		private long field3 = 3;
		private long field4 = 4;
		private long field5 = 5;
		private long field6 = 6;
		private long field7 = 7;
		private long field8 = 8;
		private long field9 = 9;
		private long field10 = 10;
		private long field11 = 11;
		private long field12 = 12;
		private long field13 = 13;
		private long field14 = 14;
		private long field15 = 15;
		private long field16 = 16;
		private long field17 = 17;
		private long field18 = 18;
		private long field19 = 19;
		private long field20 = 20;
		private long field21 = 21;
		private long field22 = 22;
		private long field23 = 23;
		private long field24 = 24;
		private long field25 = 25;
		private long field26 = 26;
		private long field27 = 27;
		private long field28 = 28;
		private long field29 = 29;
		private long field30 = 30;
		private long field31 = 31;
		private long field32 = 32;
		private long field33 = 33;
		private long field34 = 34;
		private long field35 = 35;
		private long field36 = 36;
		private long field37 = 37;
		private long field38 = 38;
		private long field39 = 39;
		private long field40 = 40;
		private long field41 = 41;
		private long field42 = 42;
		private long field43 = 43;
		private long field44 = 44;
		private long field45 = 45;
		private long field46 = 46;
		private long field47 = 47;
		private long field48 = 48;
		private long field49 = 49;
		private long field50 = 50;
		private long field51 = 51;
		private long field52 = 52;
		private long field53 = 53;
		private long field54 = 54;
		private long field55 = 55;
		private long field56 = 56;
		private long field57 = 57;
		private long field58 = 58;
		private long field59 = 59;
		private long field60 = 60;
		private long field61 = 61;
		private long field62 = 62;
		private long field63 = 63;
		private long field64 = 64;
		private long field65 = 65;
		private long field66 = 66;
		private long field67 = 67;
		private long field68 = 68;
		private long field69 = 69;
		private long field70 = 70;
		private long field71 = 71;
		private long field72 = 72;
		private long field73 = 73;
		private long field74 = 74;
		private long field75 = 75;
		private long field76 = 76;
		private long field77 = 77;
		private long field78 = 78;
		private long field79 = 79;
		private long field80 = 80;
		private long field81 = 81;
		private long field82 = 82;
		private long field83 = 83;
		private long field84 = 84;
		private long field85 = 85;
		private long field86 = 86;
		private long field87 = 87;
		private long field88 = 88;
		private long field89 = 89;
		private long field90 = 90;
		private long field91 = 91;
		private long field92 = 92;
		private long field93 = 93;
		private long field94 = 94;
		private long field95 = 95;
		private long field96 = 96;
		private long field97 = 97;
		private long field98 = 98;
		private long field99 = 99;
		public long getField0() {
			return field0;
		}
		public void setField0(long field0) {
			this.field0 = field0;
		}
		public long getField1() {
			return field1;
		}
		public void setField1(long field1) {
			this.field1 = field1;
		}
		public long getField2() {
			return field2;
		}
		public void setField2(long field2) {
			this.field2 = field2;
		}
		public long getField3() {
			return field3;
		}
		public void setField3(long field3) {
			this.field3 = field3;
		}
		public long getField4() {
			return field4;
		}
		public void setField4(long field4) {
			this.field4 = field4;
		}
		public long getField5() {
			return field5;
		}
		public void setField5(long field5) {
			this.field5 = field5;
		}
		public long getField6() {
			return field6;
		}
		public void setField6(long field6) {
			this.field6 = field6;
		}
		public long getField7() {
			return field7;
		}
		public void setField7(long field7) {
			this.field7 = field7;
		}
		public long getField8() {
			return field8;
		}
		public void setField8(long field8) {
			this.field8 = field8;
		}
		public long getField9() {
			return field9;
		}
		public void setField9(long field9) {
			this.field9 = field9;
		}
		public long getField10() {
			return field10;
		}
		public void setField10(long field10) {
			this.field10 = field10;
		}
		public long getField11() {
			return field11;
		}
		public void setField11(long field11) {
			this.field11 = field11;
		}
		public long getField12() {
			return field12;
		}
		public void setField12(long field12) {
			this.field12 = field12;
		}
		public long getField13() {
			return field13;
		}
		public void setField13(long field13) {
			this.field13 = field13;
		}
		public long getField14() {
			return field14;
		}
		public void setField14(long field14) {
			this.field14 = field14;
		}
		public long getField15() {
			return field15;
		}
		public void setField15(long field15) {
			this.field15 = field15;
		}
		public long getField16() {
			return field16;
		}
		public void setField16(long field16) {
			this.field16 = field16;
		}
		public long getField17() {
			return field17;
		}
		public void setField17(long field17) {
			this.field17 = field17;
		}
		public long getField18() {
			return field18;
		}
		public void setField18(long field18) {
			this.field18 = field18;
		}
		public long getField19() {
			return field19;
		}
		public void setField19(long field19) {
			this.field19 = field19;
		}
		public long getField20() {
			return field20;
		}
		public void setField20(long field20) {
			this.field20 = field20;
		}
		public long getField21() {
			return field21;
		}
		public void setField21(long field21) {
			this.field21 = field21;
		}
		public long getField22() {
			return field22;
		}
		public void setField22(long field22) {
			this.field22 = field22;
		}
		public long getField23() {
			return field23;
		}
		public void setField23(long field23) {
			this.field23 = field23;
		}
		public long getField24() {
			return field24;
		}
		public void setField24(long field24) {
			this.field24 = field24;
		}
		public long getField25() {
			return field25;
		}
		public void setField25(long field25) {
			this.field25 = field25;
		}
		public long getField26() {
			return field26;
		}
		public void setField26(long field26) {
			this.field26 = field26;
		}
		public long getField27() {
			return field27;
		}
		public void setField27(long field27) {
			this.field27 = field27;
		}
		public long getField28() {
			return field28;
		}
		public void setField28(long field28) {
			this.field28 = field28;
		}
		public long getField29() {
			return field29;
		}
		public void setField29(long field29) {
			this.field29 = field29;
		}
		public long getField30() {
			return field30;
		}
		public void setField30(long field30) {
			this.field30 = field30;
		}
		public long getField31() {
			return field31;
		}
		public void setField31(long field31) {
			this.field31 = field31;
		}
		public long getField32() {
			return field32;
		}
		public void setField32(long field32) {
			this.field32 = field32;
		}
		public long getField33() {
			return field33;
		}
		public void setField33(long field33) {
			this.field33 = field33;
		}
		public long getField34() {
			return field34;
		}
		public void setField34(long field34) {
			this.field34 = field34;
		}
		public long getField35() {
			return field35;
		}
		public void setField35(long field35) {
			this.field35 = field35;
		}
		public long getField36() {
			return field36;
		}
		public void setField36(long field36) {
			this.field36 = field36;
		}
		public long getField37() {
			return field37;
		}
		public void setField37(long field37) {
			this.field37 = field37;
		}
		public long getField38() {
			return field38;
		}
		public void setField38(long field38) {
			this.field38 = field38;
		}
		public long getField39() {
			return field39;
		}
		public void setField39(long field39) {
			this.field39 = field39;
		}
		public long getField40() {
			return field40;
		}
		public void setField40(long field40) {
			this.field40 = field40;
		}
		public long getField41() {
			return field41;
		}
		public void setField41(long field41) {
			this.field41 = field41;
		}
		public long getField42() {
			return field42;
		}
		public void setField42(long field42) {
			this.field42 = field42;
		}
		public long getField43() {
			return field43;
		}
		public void setField43(long field43) {
			this.field43 = field43;
		}
		public long getField44() {
			return field44;
		}
		public void setField44(long field44) {
			this.field44 = field44;
		}
		public long getField45() {
			return field45;
		}
		public void setField45(long field45) {
			this.field45 = field45;
		}
		public long getField46() {
			return field46;
		}
		public void setField46(long field46) {
			this.field46 = field46;
		}
		public long getField47() {
			return field47;
		}
		public void setField47(long field47) {
			this.field47 = field47;
		}
		public long getField48() {
			return field48;
		}
		public void setField48(long field48) {
			this.field48 = field48;
		}
		public long getField49() {
			return field49;
		}
		public void setField49(long field49) {
			this.field49 = field49;
		}
		public long getField50() {
			return field50;
		}
		public void setField50(long field50) {
			this.field50 = field50;
		}
		public long getField51() {
			return field51;
		}
		public void setField51(long field51) {
			this.field51 = field51;
		}
		public long getField52() {
			return field52;
		}
		public void setField52(long field52) {
			this.field52 = field52;
		}
		public long getField53() {
			return field53;
		}
		public void setField53(long field53) {
			this.field53 = field53;
		}
		public long getField54() {
			return field54;
		}
		public void setField54(long field54) {
			this.field54 = field54;
		}
		public long getField55() {
			return field55;
		}
		public void setField55(long field55) {
			this.field55 = field55;
		}
		public long getField56() {
			return field56;
		}
		public void setField56(long field56) {
			this.field56 = field56;
		}
		public long getField57() {
			return field57;
		}
		public void setField57(long field57) {
			this.field57 = field57;
		}
		public long getField58() {
			return field58;
		}
		public void setField58(long field58) {
			this.field58 = field58;
		}
		public long getField59() {
			return field59;
		}
		public void setField59(long field59) {
			this.field59 = field59;
		}
		public long getField60() {
			return field60;
		}
		public void setField60(long field60) {
			this.field60 = field60;
		}
		public long getField61() {
			return field61;
		}
		public void setField61(long field61) {
			this.field61 = field61;
		}
		public long getField62() {
			return field62;
		}
		public void setField62(long field62) {
			this.field62 = field62;
		}
		public long getField63() {
			return field63;
		}
		public void setField63(long field63) {
			this.field63 = field63;
		}
		public long getField64() {
			return field64;
		}
		public void setField64(long field64) {
			this.field64 = field64;
		}
		public long getField65() {
			return field65;
		}
		public void setField65(long field65) {
			this.field65 = field65;
		}
		public long getField66() {
			return field66;
		}
		public void setField66(long field66) {
			this.field66 = field66;
		}
		public long getField67() {
			return field67;
		}
		public void setField67(long field67) {
			this.field67 = field67;
		}
		public long getField68() {
			return field68;
		}
		public void setField68(long field68) {
			this.field68 = field68;
		}
		public long getField69() {
			return field69;
		}
		public void setField69(long field69) {
			this.field69 = field69;
		}
		public long getField70() {
			return field70;
		}
		public void setField70(long field70) {
			this.field70 = field70;
		}
		public long getField71() {
			return field71;
		}
		public void setField71(long field71) {
			this.field71 = field71;
		}
		public long getField72() {
			return field72;
		}
		public void setField72(long field72) {
			this.field72 = field72;
		}
		public long getField73() {
			return field73;
		}
		public void setField73(long field73) {
			this.field73 = field73;
		}
		public long getField74() {
			return field74;
		}
		public void setField74(long field74) {
			this.field74 = field74;
		}
		public long getField75() {
			return field75;
		}
		public void setField75(long field75) {
			this.field75 = field75;
		}
		public long getField76() {
			return field76;
		}
		public void setField76(long field76) {
			this.field76 = field76;
		}
		public long getField77() {
			return field77;
		}
		public void setField77(long field77) {
			this.field77 = field77;
		}
		public long getField78() {
			return field78;
		}
		public void setField78(long field78) {
			this.field78 = field78;
		}
		public long getField79() {
			return field79;
		}
		public void setField79(long field79) {
			this.field79 = field79;
		}
		public long getField80() {
			return field80;
		}
		public void setField80(long field80) {
			this.field80 = field80;
		}
		public long getField81() {
			return field81;
		}
		public void setField81(long field81) {
			this.field81 = field81;
		}
		public long getField82() {
			return field82;
		}
		public void setField82(long field82) {
			this.field82 = field82;
		}
		public long getField83() {
			return field83;
		}
		public void setField83(long field83) {
			this.field83 = field83;
		}
		public long getField84() {
			return field84;
		}
		public void setField84(long field84) {
			this.field84 = field84;
		}
		public long getField85() {
			return field85;
		}
		public void setField85(long field85) {
			this.field85 = field85;
		}
		public long getField86() {
			return field86;
		}
		public void setField86(long field86) {
			this.field86 = field86;
		}
		public long getField87() {
			return field87;
		}
		public void setField87(long field87) {
			this.field87 = field87;
		}
		public long getField88() {
			return field88;
		}
		public void setField88(long field88) {
			this.field88 = field88;
		}
		public long getField89() {
			return field89;
		}
		public void setField89(long field89) {
			this.field89 = field89;
		}
		public long getField90() {
			return field90;
		}
		public void setField90(long field90) {
			this.field90 = field90;
		}
		public long getField91() {
			return field91;
		}
		public void setField91(long field91) {
			this.field91 = field91;
		}
		public long getField92() {
			return field92;
		}
		public void setField92(long field92) {
			this.field92 = field92;
		}
		public long getField93() {
			return field93;
		}
		public void setField93(long field93) {
			this.field93 = field93;
		}
		public long getField94() {
			return field94;
		}
		public void setField94(long field94) {
			this.field94 = field94;
		}
		public long getField95() {
			return field95;
		}
		public void setField95(long field95) {
			this.field95 = field95;
		}
		public long getField96() {
			return field96;
		}
		public void setField96(long field96) {
			this.field96 = field96;
		}
		public long getField97() {
			return field97;
		}
		public void setField97(long field97) {
			this.field97 = field97;
		}
		public long getField98() {
			return field98;
		}
		public void setField98(long field98) {
			this.field98 = field98;
		}
		public long getField99() {
			return field99;
		}
		public void setField99(long field99) {
			this.field99 = field99;
		}
		
	}
	
}
