package com.rooney.LearnNIO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.lang.System.out;
import static java.nio.channels.FileChannel.MapMode.*;
import static java.lang.Long.*;


/**
 * <pre>
 * WINDOWS SSD
 * Failed to delete test file=test.dat
Windows does not allow mapped files to be deleted.
ABOUT TO PREALLOCATE 1,000,000,000
ABOUT TO WRITE 1,000,000,000
AVG RandomAccessFile JDK1.0      	write=327,501,794	read=783,415,737 bytes/sec
AVG BufferedStreamFile JDK 1.0   	write=177,926,743	read=215,049,648 bytes/sec
AVG BufferedChannelFile NIO      	write=243,543,875	read=803,142,617 bytes/sec
AVG MemoryMappedFile NIO         	write=295,787,619	read=335,701,720 bytes/sec
AVG StreamFile1xPage JDK 1.0     	write=260,061,359	read=207,171,108 bytes/sec
AVG StreamFile2xPage JDK1.0      	write=273,494,571	read=962,433,230 bytes/sec
AVG StreamFile10xPage JDK1.0     	write=305,665,887	read=1,053,381,200 bytes/sec
AVG Files readAll/writeAll JDK1.7	write=398,411,818	read=746,879,685 bytes/sec
ABOUT TO DELETE 1,000,000,000Failed to delete test file=test.dat
Windows does not allow mapped files to be deleted.


CENTOS server disks - java 8 server jre
Failed to delete test file=test.dat
Windows does not allow mapped files to be deleted.
ABOUT TO PREALLOCATE 1,000,000,000
ABOUT TO WRITE 1,000,000,000
AVG RandomAccessFile JDK1.0             write=364,495,492       read=1,177,958,033 bytes/sec
AVG BufferedStreamFile JDK 1.0          write=140,103,415       read=263,976,553 bytes/sec
AVG BufferedChannelFile NIO             write=309,476,517       read=1,442,014,473 bytes/sec
AVG MemoryMappedFile NIO                write=304,064,462       read=352,506,219 bytes/sec
AVG StreamFile1xPage JDK 1.0            write=205,005,082       read=250,389,056 bytes/sec
AVG StreamFile2xPage JDK1.0             write=209,519,326       read=1,298,744,509 bytes/sec
AVG StreamFile10xPage JDK1.0            write=200,874,422       read=1,430,313,636 bytes/sec
AVG Files readAll/writeAll JDK1.7       write=226,768,335       read=629,216,820 bytes/sec
ABOUT TO DELETE 1,000,000,000
 * @author prooney
 *
 */
public final class TestSequentialIoPerf {
	public static final int FILE_SIZE = 1_000_000_000;
	public static final int BUF_SIZE = 5_000;
	public static final String FILE_NAME = "test.dat";
	public static final byte[] BLANK_PAGE = new byte[FILE_SIZE / 10];

	public static void main(final String... args) throws Exception {
		deleteFile(FILE_NAME);
		out.printf("ABOUT TO PREALLOCATE %,d\n", FILE_SIZE);
		preallocateTestFile(FILE_NAME);
		out.printf("ABOUT TO WRITE %,d\n", FILE_SIZE);
		for (final PerfTestCase testCase : testCases) {
			long bytesReadPerSecSum = 0;
			long bytesWrittenPerSecSum = 0;
			int numRuns = 5;
			for (int i = 0; i < numRuns; i++) {
				System.gc();
				long writeDurationMs = testCase.test(PerfTestCase.Type.WRITE, FILE_NAME);
				System.gc();
				long readDurationMs = testCase.test(PerfTestCase.Type.READ, FILE_NAME);
				long bytesReadPerSecond = (FILE_SIZE * 1000L) / readDurationMs;
				long bytesWrittenPerSecond = (FILE_SIZE * 1000L) / writeDurationMs;
				bytesWrittenPerSecSum += bytesWrittenPerSecond;
				bytesReadPerSecSum += bytesReadPerSecond;
			}
			out.format("AVG %s\twrite=%,d\tread=%,d bytes/sec\n", testCase.getName(), (bytesWrittenPerSecSum / numRuns), (bytesReadPerSecSum / numRuns));
		}
		out.printf("ABOUT TO DELETE %,d", FILE_SIZE);
		deleteFile(FILE_NAME);
	}

	private static void preallocateTestFile(final String fileName) throws Exception {
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		for (long i = 0; i < FILE_SIZE; i += BLANK_PAGE.length) {
			file.write(BLANK_PAGE, 0, BLANK_PAGE.length);
		}
		file.close();
	}

	private static void deleteFile(final String testFileName) throws Exception {
		File file = new File(testFileName);
		if (!file.delete()) {
			out.println("Failed to delete test file=" + testFileName);
			out.println("Windows does not allow mapped files to be deleted.");
		}
	}

	public abstract static class PerfTestCase {
		public enum Type {
			READ, WRITE
		}

		private final String name;
		private int checkSum;

		public PerfTestCase(final String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public long test(final Type type, final String fileName) {
			long start = System.currentTimeMillis();
			try {
				switch (type) {
				case WRITE: {
					checkSum = testWrite(fileName);
					break;
				}
				case READ: {
					final int checkSum = testRead(fileName);
					if (checkSum != this.checkSum) {
						final String msg = getName() + " expected=" + this.checkSum + " got=" + checkSum;
						throw new IllegalStateException(msg);
					}
					break;
				}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return System.currentTimeMillis() - start;
		}

		public abstract int testWrite(final String fileName) throws Exception;

		public abstract int testRead(final String fileName) throws Exception;
	}

	private static PerfTestCase[] testCases = { new PerfTestCase("RandomAccessFile JDK1.0      ") {
		public int testWrite(final String fileName) throws Exception {
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
			final byte[] buffer = new byte[BUF_SIZE];
			int pos = 0;
			int checkSum = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer[pos++] = b;
				if (BUF_SIZE == pos) {
					file.write(buffer, 0, BUF_SIZE);
					pos = 0;
				}
			}
			file.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			final byte[] buffer = new byte[BUF_SIZE];
			int checkSum = 0;
			int bytesRead;
			while (-1 != (bytesRead = file.read(buffer))) {
				for (int i = 0; i < bytesRead; i++) {
					checkSum += buffer[i];
				}
			}
			file.close();
			return checkSum;
		}
	}, new PerfTestCase("BufferedStreamFile JDK 1.0   ") {
		public int testWrite(final String fileName) throws Exception {
			int checkSum = 0;
			OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				out.write(b);
			}
			out.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			int checkSum = 0;
			InputStream in = new BufferedInputStream(new FileInputStream(fileName));
			int b;
			while (-1 != (b = in.read())) {
				checkSum += (byte) b;
			}
			in.close();
			return checkSum;
		}
	}, new PerfTestCase("BufferedChannelFile NIO      ") {
		public int testWrite(final String fileName) throws Exception {
			FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
			int checkSum = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer.put(b);
				if (!buffer.hasRemaining()) {
					buffer.flip();
					channel.write(buffer);
					buffer.clear();
				}
			}
			channel.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
			int checkSum = 0;
			while (-1 != (channel.read(buffer))) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					checkSum += buffer.get();
				}
				buffer.clear();
			}
			return checkSum;
		}
	}, new PerfTestCase("MemoryMappedFile NIO         ") {
		public int testWrite(final String fileName) throws Exception {
			FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
			MappedByteBuffer buffer = channel.map(READ_WRITE, 0, Math.min(channel.size(), MAX_VALUE));
			int checkSum = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				if (!buffer.hasRemaining()) {
					buffer = channel.map(READ_WRITE, i, Math.min(channel.size() - i, MAX_VALUE));
				}
				byte b = (byte) i;
				checkSum += b;
				buffer.put(b);
			}
			channel.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
			MappedByteBuffer buffer = channel.map(READ_ONLY, 0, Math.min(channel.size(), MAX_VALUE));
			int checkSum = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				if (!buffer.hasRemaining()) {
					buffer = channel.map(READ_WRITE, i, Math.min(channel.size() - i, MAX_VALUE));
				}
				checkSum += buffer.get();
			}
			channel.close();
			return checkSum;
		}
	}, new PerfTestCase("StreamFile1xPage JDK 1.0     ") {
		public final byte[] buffer = new byte[BUF_SIZE];

		public int testWrite(final String fileName) throws Exception {
			int checkSum = 0;
			OutputStream out = new FileOutputStream(fileName);
			int index = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer[index] = b;
				index++;
				if (index == buffer.length) {
					index = 0;
					out.write(buffer);
				}
			}
			out.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			int checkSum = 0;
			InputStream in = new BufferedInputStream(new FileInputStream(fileName));
			int b;
			while (-1 != (b = in.read())) {
				checkSum += (byte) b;
			}
			in.close();
			return checkSum;
		}
	}, new PerfTestCase("StreamFile2xPage JDK1.0      ") {
		public final byte[] buffer = new byte[BUF_SIZE * 2];

		public int testWrite(final String fileName) throws Exception {
			int checkSum = 0;
			OutputStream out = new FileOutputStream(fileName);
			int index = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer[index] = b;
				index++;
				if (index == buffer.length) {
					index = 0;
					out.write(buffer);
				}
			}
			out.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			int checkSum = 0;
			InputStream in = new FileInputStream(fileName);
			int count = buffer.length;
			while (count == buffer.length) {
				count = in.read(buffer);
				for (int index = 0; index < count; index++) {
					checkSum += buffer[index];
				}
			}
			in.close();
			return checkSum;
		}
	}, new PerfTestCase("StreamFile10xPage JDK1.0     ") {
		public final byte[] buffer = new byte[BUF_SIZE * 10];

		public int testWrite(final String fileName) throws Exception {
			int checkSum = 0;
			OutputStream out = new FileOutputStream(fileName);
			int index = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer[index] = b;
				index++;
				if (index == buffer.length) {
					index = 0;
					out.write(buffer);
				}
			}
			out.close();
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			int checkSum = 0;
			InputStream in = new FileInputStream(fileName);
			int count = buffer.length;
			while (count == buffer.length) {
				count = in.read(buffer);
				for (int index = 0; index < count; index++) {
					checkSum += buffer[index];
				}
			}
			in.close();
			return checkSum;
		}
	}, new PerfTestCase("Files readAll/writeAll JDK1.7") {
		public final byte[] buffer = new byte[FILE_SIZE];

		public int testWrite(final String fileName) throws Exception {
			final Path filePath = Paths.get(fileName);
			int checkSum = 0;
			for (long i = 0; i < FILE_SIZE; i++) {
				byte b = (byte) i;
				checkSum += b;
				buffer[(int) i] = b;
			}
			Files.write(filePath, buffer);
			return checkSum;
		}

		public int testRead(final String fileName) throws Exception {
			final Path filePath = Paths.get(fileName);
			final byte[] inBuffer = Files.readAllBytes(filePath);
			int checkSum = 0;
			for (int index = 0; index < inBuffer.length; index++) {
				checkSum += inBuffer[index];
			}
			return checkSum;
		}
	}, };
}