/**
 * Provides utility methods for array manipulations, including the calculation
 * of the maximum product of consecutive elements in an array. This class
 * showcases efficient algorithms for processing array data, specifically
 * implementing a sliding window technique to find the maximum product.
 *
 * @author Doom Al Rajhi
 * @file: Part3.java
 * @assignment: Programming Assignment #1
 * @course: CSC 345 Spring 2024
 * @date: 02/02/2024
 * @version 1.0
 */

public class Part3 {

	/**
	 * Calculates the maximum product of any 'm' consecutive elements in a given
	 * array.
	 * 
	 * @param a The array to be processed. Assumes a custom structure with
	 *          'length()' and 'getVal(int index)' methods.
	 * @param m The number of consecutive elements for the product calculation.
	 * @return Maximum product of 'm' consecutive elements, or 0 if 'm' exceeds
	 *         array length.
	 */
	public static int maxProduct(Array a, int m) {
		// Check if the array is shorter than the number of elements to be multiplied.
		if (a.length() < m) {
			return 0;
		}

		int index = 1;
		int maxProduct = 0;
		int product = 1;

		// Calculate the product of the first m elements.
		for (int i = 0; i < m; i++) {
			product *= a.getVal(i);
		}
		maxProduct = product;

		// Calculate the maximum product in the sliding window.
		maxProduct = calculateMaxProductInSlidingWindow(a, m, index, maxProduct, product);
		return maxProduct;
	}

	/**
	 * Calculates the maximum product of m consecutive elements in a sliding window.
	 * 
	 * @param a          The array to be processed.
	 * @param m          The number of consecutive elements to be multiplied.
	 * @param index      The starting index for processing the sliding window.
	 * @param maxProduct The initial maximum product calculated from the first m
	 *                   elements.
	 * @param product    The current product of the elements in the window.
	 * @return The maximum product of m consecutive elements.
	 */
	private static int calculateMaxProductInSlidingWindow(Array a, int m, int index, int maxProduct, int product) {
		while (index <= a.length() - m) {
			int currentElement = a.getVal(index - 1);
			int windowLastElement = a.getVal(index + m - 1);

			if (currentElement == 0) {
				// Recalculate the product from scratch if the exiting element is 0.
				product = 1;
				for (int i = index; i < index + m; i++) {
					product *= a.getVal(i);
				}
			} else {
				// Update the product for the current window.
				product = (product / currentElement) * windowLastElement;
			}
			maxProduct = Math.max(maxProduct, product);
			index++;
		}
		return maxProduct;
	}
}
