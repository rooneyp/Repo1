package com.rooney.Mess.io;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnFiles {

	public static void main(String[] args) {
		pathAndResolve();

	}

	
	static void pathAndResolve() {
		Path csvDir = Paths.get(System.getProperty("user.dir"));
		System.out.println(csvDir.resolve("anyOldDir"));
		System.out.println(csvDir.resolve("../anyOldDir"));
	}
}
