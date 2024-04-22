/**
 * The DequeCompare class is used to compare the performance of two different
 * Deque implementations. It measures the time taken to add elements to the end
 * of each Deque implementation using the addLast method.
 *
 *
 * @author Doom Al Rajhi
 * @file: DequeCompare.java
 * @assignment: Programming Assignment #2
 * @course: CSC 345 Spring 2024
 * @date: 02/12/2024
 * @version 1.0
 */
public class DequeCompare {
	static String asterisks = "********************************";

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);

		Deque<Integer> q = new Deque<>();
		Deque1<Integer> q1 = new Deque1<>();

		long startTime;
		long endTime;
		System.out.print("*****	Running tests		*****\n");
		System.out.println("*****	Testing First Deque	*****\n");
		System.out.println("Testing...\n\n");

		// Timing for Deque
		startTime = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			q.addLast(1);
		}
		endTime = System.currentTimeMillis();
		System.err.println(asterisks);
		System.out.println("First Deque took: " + (endTime - startTime) + " milliseconds.");
		System.err.println(asterisks);

		System.out.println("\n*****Testing Second Deque*****\n");
		System.err.println("Testing...\n");
		// Timing for Deque1
		startTime = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			q1.addLast(1);
		}
		endTime = System.currentTimeMillis();
		System.err.println(asterisks);
		System.out.println("Second Deque took: " + (endTime - startTime) + " milliseconds.");
		System.err.println(asterisks);

		System.out.println("\n\nTesting Finished.");
	}
}
