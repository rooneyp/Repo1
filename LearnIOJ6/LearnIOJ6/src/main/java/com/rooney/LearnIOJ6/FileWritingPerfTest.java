package com.rooney.LearnIOJ6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import sun.nio.cs.StreamEncoder;


/**
 * <pre>
WINDOWS SSD
Writing using memory-mapped file... 0.125 seconds
Writing raw... 1.15 seconds
Writing buffered (buffer size: 8192)... 0.444 seconds
Writing buffered (buffer size: 1048576)... 0.428 seconds
Writing buffered (buffer size: 4194304)... 0.434 seconds
Writing buffered (fileChannelAndStreamEncoder) (buffer size: 8192)... 0.641 seconds
Writing buffered (fileChannelAndStreamEncoder) (buffer size: 1048576)... 0.595 seconds
Writing buffered (fileChannelAndStreamEncoder) (buffer size: 4194304)... 0.579 seconds

CENTOS server disks
Writing using memory-mapped file... 0.184 seconds
Writing raw... 0.618 seconds
Writing buffered (buffer size: 8192)... 0.37 seconds
Writing buffered (buffer size: 1048576)... 0.362 seconds
Writing buffered (buffer size: 4194304)... 0.363 seconds

 * @author prooney
 *
 */
public class FileWritingPerfTest {

	private static final int ITERATIONS = 5;
	private static final double MEG = (Math.pow(1024, 2));
	private static final int RECORD_COUNT = 4000000;
	private static final String RECORD = "Help I am trapped in a fortune cookie factory\n";
	private static final int RECSIZE = RECORD.getBytes().length;
	private static String OUTPUT_DIR = "/tmp"; ///netqos/tmp";

	public static void main(String[] args) throws Exception {
		if(args.length != 0) {
			OUTPUT_DIR = args[0];
		}
		
		List<String> records = new ArrayList<String>(RECORD_COUNT);
		int size = 0;
		for (int i = 0; i < RECORD_COUNT; i++) {
			records.add(RECORD);
			size += RECSIZE;
		}
		System.out.println(records.size() + " 'records' going to " + OUTPUT_DIR);
		System.out.printf("%.2f MB\n", (size / MEG));

		for (int i = 0; i < ITERATIONS; i++) {
			System.out.println("\nIteration " + i);

			writeWithMemoryMappedFile();
			writeRawWithFileWriter(records);
			writeBufferedWithFileWriterAndBufferedWriter(records, 8192);
			writeBufferedWithFileWriterAndBufferedWriter(records, (int) MEG);
			writeBufferedWithFileWriterAndBufferedWriter(records, 4 * (int) MEG);
			writeBufferedWithFileWriterAndBufferedWriter(records, 8 * (int) MEG);
			writeBufferedWithFileWriterAndBufferedWriter(records, 16 * (int) MEG);
			
			writeBufferedWithFileChannelAndStreamEncoder(records, 8192);
			writeBufferedWithFileChannelAndStreamEncoder(records, (int) MEG);
			writeBufferedWithFileChannelAndStreamEncoder(records, 4 * (int) MEG);
			writeBufferedWithFileChannelAndStreamEncoder(records, 8 * (int) MEG);
			writeBufferedWithFileChannelAndStreamEncoder(records, 16 * (int) MEG);
			
			
			writeBufferedFileChannel(records, 8192);
			writeBufferedFileChannel(records, (int) MEG);
			writeBufferedFileChannel(records, 4 * (int) MEG);
			writeBufferedFileChannel(records, 8 * (int) MEG);
			writeBufferedFileChannel(records, 16 * (int) MEG);
		}
	}
	
	private static void writeBufferedFileChannel(List<String> records, int bufSize) throws IOException {
		File file = createFile();
		FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
		ByteBuffer outputBuffer = ByteBuffer.allocate(bufSize);
		
		long start = System.currentTimeMillis();
		for (String string : records) {
			byte[] encodedLine = string.getBytes("utf8");
			
			if(outputBuffer.remaining() >= encodedLine.length) {
				outputBuffer.put(encodedLine);
			}else {
				outputBuffer.flip();
				channel.write(outputBuffer);
				outputBuffer.clear();
			}
		}
		
		channel.close();	
		long end = System.currentTimeMillis();
		System.out.println("Writing BufferedFileChannel... Buffer is " + bufSize +", " + (end - start) / 1000f + " seconds");
		file.delete();
	}
	
	

	private static void writeWithMemoryMappedFile() throws IOException {
		if(! ( System.getProperty("java.vendor").matches("[Oracle|Sun].*") ) ) {
			System.out.println("Oracle jvm required for closing Direct Buffer. Found " + System.getProperty("java.vendor"));
			return;
		}
		
		File file = createFile();
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		FileChannel rwChannel = randomAccessFile.getChannel();

		byte[] buffer = RECORD.getBytes();

		ByteBuffer wrBuf = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length * RECORD_COUNT);
		
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < RECORD_COUNT; i++) {
			wrBuf.put(buffer);
		}
		rwChannel.close();
		randomAccessFile.close();
		
		long end = System.currentTimeMillis();
		System.out.println("Writing using memory-mapped file... " + (end - start) / 1000f + " seconds");
		closeDirectBuffer(wrBuf);
		file.delete();
	}

	// Mem mapped files remain open until GC process closes it.
	// 1 hack is to request GC (system.gc) or another hack is to directly call Oracle code 
	// http://stackoverflow.com/questions/2972986/how-to-unmap-a-file-from-memory-mapped-using-filechannel-in-java
	private static void closeDirectBuffer(ByteBuffer cb) {
	    if (!cb.isDirect()) return;

	    // we could use this type cast and call functions without reflection code,
	    // but static import from sun.* package is risky for non-SUN virtual machine.
	    //try { ((sun.nio.ch.DirectBuffer)cb).cleaner().clean(); } catch (Exception ex) { }
	    try {
	        Method cleaner = cb.getClass().getMethod("cleaner");
	        cleaner.setAccessible(true);
	        Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
	        clean.setAccessible(true);
	        clean.invoke(cleaner.invoke(cb));
	    } catch(Exception ex) { }
	    cb = null;
	}	
	
	private static void writeRawWithFileWriter(List<String> records) throws IOException {
		File file = createFile();
		try {
			FileWriter writer = new FileWriter(file);
			System.out.print("Writing raw... ");
			write(records, writer);
		} finally {
			// comment this out if you want to inspect the files afterward
			file.delete();
		}
	}

	private static File createFile() throws IOException {
		return new File(OUTPUT_DIR + "/FileWritingPerfTest.txt"); // File.createTempFile("foo",
																	// ".txt");
	}
	
	

	private static void writeBufferedWithFileWriterAndBufferedWriter(List<String> records, int bufSize) throws IOException {
		File file = createFile();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			
			Writer fileWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufSize);

			//alternative but slower on java6
			//Writer bufferedWriter = StreamEncoder.forEncoder(fileOutputStream.getChannel(), Charset.forName("UTF-8").newEncoder(), bufSize);			
			
			
			System.out.print("Writing buffered (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			file.delete();
		}
	}
	
	//Seems to be slower that BufferedWriter(OutputStreamWriter(FileOutputStream ~0.5 vs ~0.4
	//JAVA 7+ only
	private static void writeBufferedWithFileChannelAndStreamEncoder(List<String> records, int bufSize) throws IOException {
		if(System.getProperty("java.version").substring(0, 3).compareTo("1.7") < 0) {
			System.out.println("needs 1.7 or greater");
			return;
		}
		
		File file = createFile();
		try {
			 SeekableByteChannel channel = Files.newByteChannel(
					 	createFile().toPath(),
	                    StandardOpenOption.CREATE,
	                    StandardOpenOption.TRUNCATE_EXISTING,
	                    StandardOpenOption.WRITE);

			 Writer bufferedWriter = StreamEncoder.forEncoder(channel, StandardCharsets.UTF_8.newEncoder(), bufSize);			
			
			
			System.out.print("Writing buffered (fileChannelAndStreamEncoder) (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			file.delete();
		}
	}

	private static void write(List<String> records, Writer writer) throws IOException {
		long start = System.currentTimeMillis();
		for (String record : records) {
			writer.write(record);
		}
		// writer.flush();
		writer.close();
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000f + " seconds");
	}
}
