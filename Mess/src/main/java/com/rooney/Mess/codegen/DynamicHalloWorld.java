package com.rooney.Mess.codegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.lang3.StringUtils;

public class DynamicHalloWorld {   
    private final static String HALLO_WORLD_PACKAGE = "com.rooney.Mess.codegen";
    private static final String OUTPUT_DIR = "D:/delme/" + HALLO_WORLD_PACKAGE.replace(".", "/") + "/";
    private final static String HALLO_WORLD_CLASS_NAME = "HalloWorldTest";   
    private final static String HALLO_WORLD_SOURCE =
        "package "+ HALLO_WORLD_PACKAGE +"; \n" +
        "public class "+HALLO_WORLD_CLASS_NAME+" implements Foo {\n" +           
        "    private int i = 100; \n" +
        "    public void foo() {\n" +           
        "        int x = 1000; \n" +
        "        x++; \n" +
        "        i++; \n" +
        "        System.out.println(\"Hallo world: x=\" + x + \" i=\" +i);\n" +           
//"        System.out.println(\"Hallo world: \";\n" +
        "    }\n" +           
        "}";

    public static void main(String[] args) throws Exception {
        writeSrcToDisk();
        tryBlogWay();   
    }

    public static void writeClass(byte[] byteArray) {
        try {
            FileOutputStream writer;
            writer = new FileOutputStream(new File(OUTPUT_DIR + HALLO_WORLD_CLASS_NAME + ".class"));
            writer.write(byteArray);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    private static void writeSrcToDisk() throws Exception {
        File file = new File(OUTPUT_DIR + HALLO_WORLD_CLASS_NAME + ".java");
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);
        writer.write(HALLO_WORLD_SOURCE);
        writer.close();
    }

    public static void tryBlogWay() {
        Class compiledClass = compileClass(HALLO_WORLD_SOURCE, HALLO_WORLD_PACKAGE + "." + HALLO_WORLD_CLASS_NAME);       
        if (compiledClass==null){           
            return;       
        }       
        try{           
            Foo generatedFoo = (Foo) compiledClass.newInstance();
            generatedFoo.foo();
        }
        catch (Exception e) {           
            e.printStackTrace();       
        }
        
        System.out.println("Finished Blog way");
    }

    private static Class compileClass(String halloWorldProgram, String className) {       
        try{           
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler(); //new EclipseCompiler(); // 

            StandardJavaFileManager sjfm = javac.getStandardFileManager(null, null, null);
            CustomClassLoader cl = new CustomClassLoader();           
            CustomJavaFileManager fileManager = new CustomJavaFileManager(sjfm, cl);           
            
            List<String> options = Arrays.asList("-g"); //List options = Collections.emptyList();
            List<InMemorySourceFileObjectX> compilationUnits = Arrays.asList(new InMemorySourceFileObjectX(className, halloWorldProgram));           
            Writer out = getWriter();           
            JavaCompiler.CompilationTask compile = javac.getTask(out, fileManager, null, options, null, compilationUnits);           

            boolean res = compile.call();           
            if (res){               
                return cl.findClass(className);           
            }       
        } catch (Exception e){           
            e.printStackTrace();       
        }       
        return null;   
    }

    private static Writer getWriter() {
        Writer logWriter = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                String msg = String.valueOf(cbuf, off, len);
                if(StringUtils.isNotBlank(msg)) {
                    System.err.println(msg); 
                }
            }
            
            @Override
            public void flush() throws IOException {
            }
            
            @Override
            public void close() throws IOException {
            }
        };
        return logWriter;
    }
}

class InMemorySourceFileObjectX extends SimpleJavaFileObject {   
    private String src;   
    public InMemorySourceFileObjectX(String name, String src) {       
        super(URI.create("file:///" + name.replace(".","/") + ".java"), Kind.SOURCE);       
        this.src = src;   
    }   
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {       
        return src;   
    }   
    public InputStream openInputStream() {       
        return new ByteArrayInputStream(src.getBytes());   
    }
    @Override
    public String toString() {
        return toUri().toString(); // This is used to name 'SourceFile' debug attribute in the generated class
    }
}

/**
 * Overrides: Getting a file object for output: allows us to return our InMemoryClassFileObjectX and add it to our customClassloader
 *            getting classloader: allow us to provide out custom one
 * 
 * All other ops are delegated to a StandardJavaFileManager
 * 
 * @author prooney
 */
class CustomJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {   
    private CustomClassLoader customClassloader;   
    public CustomJavaFileManager(StandardJavaFileManager sjfm, CustomClassLoader customClassloader) {       
        super(sjfm);       
        this.customClassloader = customClassloader;   
    }   
    public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {       
        InMemoryClassFileObjectX mbc = new InMemoryClassFileObjectX(name);       
        customClassloader.addClass(name, mbc);       
        return mbc;   
    }

    public ClassLoader getClassLoader(Location location) {       
        return customClassloader;   
    }
}

class InMemoryClassFileObjectX extends SimpleJavaFileObject {   
    private ByteArrayOutputStream baos;   
    public InMemoryClassFileObjectX(String name) {       
        super(URI.create("byte:///" + name.replace(".","/") + ".class"), Kind.CLASS);   
    }   
    public OutputStream openOutputStream() {       
        baos = new ByteArrayOutputStream();       
        return baos;   
    }   
    public byte[] getBytes() {       
        byte[] byteArray = baos.toByteArray();
//        DynamicHalloWorld.writeClass(byteArray);
        return byteArray;   
    }
}

/**
 * Checks internal Map first then delegates to parent
 * @author prooney
 *
 */
class CustomClassLoader extends ClassLoader {   
    private Map<String,InMemoryClassFileObjectX> m = new HashMap<String, InMemoryClassFileObjectX>();

    protected Class<?> findClass(String name) throws ClassNotFoundException {       
        InMemoryClassFileObjectX mbc = m.get(name);       
        if (mbc==null){           
            mbc = m.get(name.replace(".","/"));           
            if (mbc==null){               
                throw new ClassNotFoundException(name);       
            }       
        }       
        return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);   
    }

    public void addClass(String name, InMemoryClassFileObjectX mbc) {       
        m.put(name, mbc);   
    }
}
