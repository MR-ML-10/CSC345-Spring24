/**
 * This class provides methods to read integers from a file and calculate the 
 * frequency of each remainder when these integers are divided by a specified modulus.
 * It includes functionality for running tests and comparing the computed results 
 * with expected outputs. The main method orchestrates the execution of these tests
 * based on input and output file specifications.
 *
 *
 * @author Doom Al Rajhi
 * @file: Part1.java
 * @assignment: Programming Assignment #1
 * @course: CSC 345 Spring 2024
 * @date: 02/02/2024
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Part1 {
	public static void main(String[] args) {
		int m = 96;
		int p = 97;
		int testNum = 1;
		String input = null; // Change to NULL
		double score = 0.0;

		while (testNum <= 3) {
			input = "test_input" + testNum + ".txt";
			score += runTest(input, m, p, testNum);
			testNum++;
		}
		System.out.println("Expected Score: " + score + "/9.0");
	}

	/**
	 * Reads integers from a file and calculates the occurrence of each remainder
	 * when divided by a specified modulus.
	 *
	 * @param fn The file name containing integers, each on a separate line.
	 * @param m  The modulus used for calculating the remainder.
	 * @return An array where each index represents a remainder (0 to m-1) and the
	 *         value at each index is the count of occurrences of that remainder.
	 */
	public static int[] getCounts(String fn, int m) {
		int[] counts = new int[m]; // m - 1

		// Try-with-resources to ensure the BufferedReader is closed after use
		BufferedReader br;
		try {
			// read content per line
			br = new BufferedReader(new FileReader(fn));
			String line = br.readLine(); // 148

			// Read the file line by line
			while (line != null) {
				// Parse the line as an integer
				int curNum = Integer.parseInt(line);
				// Calculate the remainder and increment the corresponding count
				int reminder = curNum % m; // r = x % m
				counts[reminder]++;
				line = br.readLine(); // next line
			}
			printCounts(counts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counts;
	}

	/****
	 * The following methods are used in the main method for testing purposes, so I
	 * recommend you do not change them. However, the grading is done with a
	 * different class (with similar methods), so if you do change them, it
	 * shouldn't be a problem unless they do not compile.
	 ****/

	// This is the method for running each test.
	private static double runTest(String input, int m, int p, int testNum) {
		double score = 0.0;
		System.out.println("\nRunning on " + input + " with modulus " + m + "...");
		System.out.println("***********************************************\n");
		int[] counts = Part1.getCounts(input, m);
		// Part1.printCounts(counts);
		String output = "test_output" + testNum + "a.txt";
		score += checkCounts(counts, output);

		System.out.println("\nRunning on " + input + " with modulus " + p + "...");
		System.out.println("***********************************************\n");
		counts = Part1.getCounts(input, p);
		// Part1.printCounts(counts);
		output = "test_output" + testNum + "b.txt";
		score += checkCounts(counts, output);
		return score;
	}

	// This method compares your results to the expected results, which are read
	// from a text file.
	private static double checkCounts(int[] counts, String output) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(output));
			String line = br.readLine();
			int i = 0;
			while (line != null) {
				String[] split = line.split(":\t");
				if (Integer.parseInt(split[1]) != counts[i]) {
					System.out.println("\nCounts for " + i + " do not match.");
					return 0.0;
				}
				line = br.readLine();
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
		System.out.println("\nAll the counts match!");
		return 1.5;
	}

	private static void printCounts(int[] counts) {
		for (int i = 0; i < counts.length; i++)
			System.out.println(i + ":\t" + counts[i]);
	}
}
