package com.rooney.Mess;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;

public class ReadFileFromClasspath {
	public static void main(String[] args) {
		InputStream is = ReadFileFromClasspath.class.getResourceAsStream("/xytel/001-units/units_Units.xml");
		String xml = new java.util.Scanner(is).useDelimiter("\\A").next();
		System.out.println(xml);
		
		//if inside a jar
		//kpi-flex-bytel-models-1.0.22.jar/bytel/001-units/units_Units.xml
		
//		InputStream is = this.getClass().getResourceAsStream("/bytel/001-units/units_Units.xml");
//		String xml = new Scanner(is).useDelimiter("\\A").next();
//		Assert.assertNotNull(xml);
//		System.out.println(xml);
//		is.close();
		
		//String rootUrl = "classpath:/xytel";
//		URL resource = getClass().getClassLoader().getResource(rootUrl.substring("classpath:".length()));
//		InputStream is = this.getClass().getResourceAsStream("/bytel");
		
		 // Ant-style path matching
//	        for (Resource resource : new PathMatchingResourcePatternResolver().getResources("/bytel/**")) {
//	            System.out.println("resource = " + resource);
//	            InputStream is = resource.getInputStream();
//	        }
		
//	        File dir = new PathMatchingResourcePatternResolver().getResource(rootUrl).getURI().
	        
		//dir =new File(resource.getFile());
//		System.out.println(resource);
		
		
		//commons VFS
//		<dependency>
//        <groupId>org.apache.commons</groupId>
//        <artifactId>commons-vfs2</artifactId>
//        <version>2.0</version>
//    </dependency>
//		FileSystemManager fsManager;
//        fsManager = VFS.getManager();
//        FileObject jarFile = fsManager.resolveFile(url.toString());
		
//	    private void find(FileObject jarFile, List<File> results) throws FileSystemException {
//	        switch (jarFile.getType()) {
//	            case FOLDER:
//	                for (FileObject child = jarFile.getChildren()) {
//	                    findChildren(child, results);
//	                }
//	                break;
//
//	            case FILE:
//	                if (jarFile.getName().getBaseName().endsWith(".xxx")) {
//	                    final String uri = jarFile.getName().getURI();
//	                    results.add(new File(uri));
//	                }		
	}
	
	
}
