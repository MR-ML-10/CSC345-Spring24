import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * <bold>ADD to.String() in the LinkedList class to run the testcases!!!!!!!!!!</bold>
 */

import org.junit.jupiter.api.Test;

class SortTest {

	// Print header for each test
	private void printTestHeader(String testName) {
		System.out.println("\n==================== " + testName + " ====================");
	}

	// Print footer after each test
	private void printTestFooter(String testName, long duration) {
		System.out.println("==================== Finished " + testName + " ====================");
		System.out.println("Duration: " + duration + " ms");
	}

	@Test
	void testBubbleSort() {
		String testName = "bubbleSortLinkedList";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");

		for (int i = 0; i < 1000; i++) {
			int[] randomizedArray = ArrayGen.getRand(10, 5); // Assuming this generates random numbers
			LinkedList list = new LinkedList();
			for (int value : randomizedArray) {
				list.add(value);
			}
			LinkedList alistLinkedList = Sort.bubbleSort(list);

			assertTrue(isSorted(alistLinkedList));
		}

		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testMergeSort() {
		String testName = "mergeSort LinkedList";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");

		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(10, 5); // Assuming this generates random numbers
			LinkedList list = new LinkedList();
			for (int value : randomizedArray) {
				list.add(value);
			}
			LinkedList aList = Sort.mergeSort(list);
			assertTrue(isSorted(aList));
		}

		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testIterativeMerge() {
		String testName = "iterativeMerge";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);

			Sort.iterativeMerge(arr);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testThreeWayMergeSort() {
		String testName = "ThreeWayMergeSort";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);
			Sort.threeWayMerge(arr);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testInsertMerge() {
		String testName = "insertMerge";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);

			Sort.insertMerge(arr, 5);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testFiveWayQuick() {
		String testName = "fiveWayQuick";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);

			Sort.fiveWayQuick(arr);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testLocalityAwareSelectionSort() {
		String testName = "locSelect";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);

			Sort.locSelect(arr, 5);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	@Test
	void testLocHeap() {
		String testName = "locHeap";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");
		for (int i = 0; i < 1000000; i++) {
			int[] randomizedArray = ArrayGen.getRand(40, 5);
			Array arr = new Array(randomizedArray.length);
			settingValues(arr, randomizedArray);

			Sort.locHeap(arr, 5);

			assertTrue(isSorted(arr));
		}
		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

	// Helper method to populate the custom Array with values from an int[]
	static Array settingValues(Array arr, int[] randomArr) {
		for (int i = 0; i < randomArr.length; i++) {
			arr.setVal(i, randomArr[i]);
		}
		return arr;
	}

	// Helper method to check if an Array instance is sorted
	private boolean isSorted(Array array) {
		for (int i = 0; i < array.length() - 1; i++) {
			if (array.getVal(i) > array.getVal(i + 1)) {
				return false;
			}
		}
		return true;
	}

//	// Helper method to check if a LinkedList instance is sorted
	private boolean isSorted(LinkedList list) {
		if (list.isEmpty() || list.head().next() == null) {
			// The list is empty or has only one element, hence it's sorted.
			return true;
		}
		Node current = list.head();
		while (current.next() != null) {
			if (current.val() > current.next().val()) {
				// Found a pair of elements that are out of order
				return false;
			}
			current = current.next();
		}
		// Went through the list and found no out-of-order elements
		return true;
	}

	@Test
	void testIsertionSort() {
		String testName = "IsertionSort LinkedList";
		printTestHeader(testName);
		long startTime = System.currentTimeMillis();
		System.out.println("Testing...");

		for (int i = 0; i < 10000; i++) {
			int[] randomizedArray = ArrayGen.getRand(10, 5); // Assuming this generates random numbers
			LinkedList list = new LinkedList();
			for (int value : randomizedArray) {
				list.add(value);
			}
			LinkedList sortedList = Sort.insertionSort(list);
			assertTrue(isSorted(sortedList));
		}

		long duration = System.currentTimeMillis() - startTime;
		printTestFooter(testName, duration);
	}

//	@Test
//	void testTwoWayQuickSort() {
//		String testName = "TwoWayQuickSort LinkedList";
//		printTestHeader(testName);
//		long startTime = System.currentTimeMillis();
//		System.out.println("Testing...");
//
//		for (int i = 0; i < 1; i++) {
//			int[] randomizedArray = ArrayGen.getRand(10, 5); // Assuming this generates random numbers
//			LinkedList list = new LinkedList();
//			for (int value : randomizedArray) {
//				list.add(value);
//			}
//			System.out.println("before sorting: " + list);
//			LinkedList sortedList = Sort.quickSort(list);
//			System.out.println("After sorting: " + sortedList);
//			assertTrue(isSorted(sortedList));
//		}
//
//		long duration = System.currentTimeMillis() - startTime;
//		printTestFooter(testName, duration);
//	}
}
