package com.rooney.Mess.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnFiles {

	public static void main(String[] args) throws Exception {
//		pathAndResolve();
		loopOnFiles();
	}

	
	static void pathAndResolve() {
		Path csvDir = Paths.get(System.getProperty("user.dir"));
		System.out.println(csvDir.resolve("anyOldDir"));
		System.out.println(csvDir.resolve("../anyOldDir"));
	}
	
	static void loopOnFiles() throws Exception {
		Path csvDir = Paths.get(System.getProperty("user.dir"));
		 
		for (Path path : Files.newDirectoryStream(Paths.get(System.getProperty("user.dir")), "*.*")) {
             System.out.println(path.toUri().toURL());
         }
	}
}
