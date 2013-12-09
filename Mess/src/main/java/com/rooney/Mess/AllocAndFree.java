package com.rooney.Mess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AllocAndFree {
	private static final Random RANDOM = new Random();
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int iterations = 1000;
		int elements = 100000;
		int releaseMod = 100;

		if (args.length > 0) {
			iterations = Integer.valueOf(args[0]);
		}
		
		if (args.length > 1) {
			elements = Integer.valueOf(args[1]);
		}
			
		if (args.length > 2) {
			releaseMod = Integer.valueOf(args[2]);
		}
			

		List holdData= new ArrayList();
		
		System.out.println("Running with iterations " + iterations + " elements " + elements + " releaseMod " + releaseMod);
		
		for (int i = 0; i < iterations; i++) {
			List<String> data = new ArrayList<String>(elements);
			alloc(data, elements);
			holdData.add(data);

			if (i % 30 == 0) {
				System.out.println("iteration: " + i);
			}
			
			if (i % releaseMod == 0) {
				System.out.println("releasing at: " + i);
				holdData.clear();
			}
		}

	}

	public static void alloc(List<String> data, int elements) throws Exception {
        for (int i = 0; i < elements; i++) {
            data.add(random(100, 32, 127, false, false, null, RANDOM));
        }
    }

	public static String random(int count, int start, int end, boolean letters,
			boolean numbers, char[] chars, Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException(
					"Requested random string length " + count
							+ " is less than 0.");
		}
		if (start == 0 && end == 0) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if (letters && Character.isLetter(ch) || numbers
					&& Character.isDigit(ch) || !letters && !numbers) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

}
