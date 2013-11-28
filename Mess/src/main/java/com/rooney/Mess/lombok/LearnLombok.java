package com.rooney.Mess.lombok;

import lombok.Data;


public @Data class LearnLombok {
	private final String name;
	private final double latitude, longitude;
	private String country;

	public static void main(String[] args) {
		LearnLombok learnLombok = new LearnLombok("paul", 1.1, 1.2);
		System.out.println(learnLombok);
	}
	
}
