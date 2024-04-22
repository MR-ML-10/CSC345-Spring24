
/**
 * This class provides methods for reading integers from a file and maintaining a list
 * of the top 'm' numbers. It includes functionality to process the data, run validation
 * tests, and compare the computed top numbers against expected results. The main method
 * orchestrates the execution of these tests with various parameters.
 *
 *
 * @author Doom Al Rajhi
 * @file: Part2.java
 * @assignment: Programming Assignment #1
 * @course: CSC 345 Spring 2024
 * @date: 02/02/2024
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Part2 {
	private static int next;
	private static int[][] vals = new int[][] { { 5, 10, 15 }, { 8, 16, 25 }, { 5, 10, 50 } };
	private static int[][] expCount = new int[][] { { 155, 183, 261 }, { 436, 714, 978 }, { 1250, 1716, 6071 } };

	public static void main(String[] args) {
		int testNum = 1;
		String input = null;
		double score = 0.0;

		while (testNum <= 3) {
			for (int i = 0; i < 3; i++)
				score += runTest(testNum, i);
			testNum++;
		}
		System.out.println("Expected score: " + score);
	}

	/***
	 * Should read the integers in from the file and maintain a list of the top m.
	 ***/
	public static Array getTop(String fn, int m) {
		Array counts = new Array(m);
		if (m == 0)
			return counts;

		BufferedReader br;
		try {
			// read content per line
			br = new BufferedReader(new FileReader(fn));
			String line = br.readLine();

			int index = 0;
			// Read the file line by line
			while (line != null) {
				// Parse the line as an integer
				int curNum = Integer.parseInt(line);

				// step 1: creating the array until it becomes full.
				if (index < m) {
					counts.setVal(index, curNum);
					index++;
				} else {
					// step 2: if it's full, compare the current value with each values in the
					// array.
					int smallestValue = Integer.MAX_VALUE;
					int smallestIndex = -1;

					// iterate through the array and compare the smallest value and update the index
					// it the smallest value
					findSmallestElement(m, counts, curNum, smallestValue, smallestIndex);
				}
				line = br.readLine(); // next line
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counts;
	}

	/**
	 * Iterates through the given array to find its smallest element and updates the
	 * array if the current number is larger.
	 * 
	 * @param m             The size of the array to iterate over.
	 * @param counts        The array in which to find the smallest element and
	 *                      potentially update an element.
	 * @param curNum        The current number to compare with the smallest element
	 * @param smallestValue The current smallest value found in the array. This
	 *                      parameter will be updated during the method execution.
	 * @param smallestIndex The index of the current smallest value in the array.
	 *                      This parameter will be updated if a replacement occurs.
	 */
	private static void findSmallestElement(int m, Array counts, int curNum, int smallestValue, int smallestIndex) {
		for (int i = 0; i < m; i++) {
			int currentElement = counts.getVal(i);
			if (currentElement < smallestValue) {
				smallestValue = currentElement;
				smallestIndex = i;
			}
		}
		if (curNum > smallestValue) {
			counts.setVal(smallestIndex, curNum);
		}
	}

	/***
	 * These methods are provided for testing purposes, so it is recommended that
	 * you do not change them. However, the grading is done in another class and
	 * will only call your getTop method from here, so if you do change the methods,
	 * that is fine (as long as they still compile).
	 ***/
	private static double runTest(int testNum, int i) {
		double score = 0.0;
		String input = "input" + testNum + ".txt";
		int m = vals[testNum - 1][i];
		System.out.println("\nRunning on " + input + " with m = " + m + "...");
		System.out.println("***********************************************\n");
		Array top = Part2.getTop(input, m);
		// Part2.printTop(top);
		String output = "output" + testNum + "_" + (i + 1) + ".txt";
		int[] exp = getExp(output, m);
		if (checkVals(top, exp)) {
			System.out.println("Accuracy check passed!");
			score += 1.0;
		}
		score += checkCount(top.getAccessCount(), expCount[testNum - 1][i]);
		return score;
	}

	private static double checkCount(int act, int exp) {
		if (act < exp / 2) {
			System.out.println("Something does not appear correct. The access count seems too low.");
			printCounts(act, exp);
			return 0.0;
		}
		if (act <= exp) {
			System.out.println("Access count looks good!");
			printCounts(act, exp);
			return 0.5;
		}
		if (act <= 2 * exp) {
			System.out.println("Access count looks okay but could be better.");
			printCounts(act, exp);
			return 0.25;
		}
		System.out.println("Access count looks too high.");
		printCounts(act, exp);
		return 0.0;
	}

	private static void printCounts(int act, int exp) {
		System.out.println("Your count: " + act);
		System.out.println("My count: " + exp);
	}

	private static int[] getExp(String output, int m) {
		int[] exp = new int[m];
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(output));
			String line = br.readLine();
			int i = 0;
			while (line != null) {
				int n = Integer.parseInt(line);
				exp[i] = n;
				i++;
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exp;
	}

	private static boolean checkVals(Array act, int[] exp) {
		return act.compare(exp);
	}

	private static void printTop(int[] top) {
		for (int i = 0; i < top.length; i++)
			System.out.println(top[i]);
	}

	private static void sort(int[] top) {
		int i = 1;
		while (i < top.length) {
			int j = i;
			int k = j - 1;
			while (j > 0 && top[j] < top[k]) {
				int temp = top[k];
				top[k] = top[j];
				top[j] = temp;
				j--;
				k--;
			}
			i++;
		}
	}
}
