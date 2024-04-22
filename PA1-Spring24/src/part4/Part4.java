/**
 * This class provides methods for reading integers from a file and maintaining a list
 * of the top 'm' numbers. It includes functionality to process the data, run validation
 * tests, and compare the computed top numbers against expected results. The main method
 * orchestrates the execution of these tests with various parameters.
 *
 *
 * @author Doom Al Rajhi
 * @file: Part4.java
 * @assignment: Programming Assignment #1
 * @course: CSC 345 Spring 2024
 * @date: 02/02/2024
 * @version 1.0
 */

public class Part4 {
	/**
	 * This class is responsible for including a method in which it focuses on
	 * rearranging elements within an array. It includes the method 'pushZeroes',
	 * which moves all zeros in an array to the end while maintaining the order of
	 * non-zero elements.
	 * 
	 * @param array The array in which zeros are to be moved to the end.
	 */
	public static void pushZeroes(Array array) {
		// Position where the next non-zero element should be placed
		int curZeroIndex = 0;

		for (int i = 0; i < array.length(); i++) {
			int curElement = array.getVal(i);
			if (curElement != 0) {
				// Swap only if the current non-zero element is not at the correct position
				if (i != curZeroIndex) {
					array.swap(i, curZeroIndex);
				}
				curZeroIndex++;
			}
		}
	}
}