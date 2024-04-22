/**
 * This class implements various sorting algorithms for arrays and linked lists.
 * It includes iterative and recursive versions of Merge Sort, a hybrid
 * Merge-Insertion Sort, a recursive 3-way Merge Sort, and sorting algorithms
 * tailored for specific scenarios like locality-aware Selection and Heap Sorts.
 * Additionally, it provides implementations for sorting linked lists using
 * Merge Sort, Insertion Sort, and Bubble Sort.
 *
 * @author Doom Al Rajhi
 * @file: Sort.java
 * @assignment: Programming Assignment #3
 * @course: CSC 345 Spring 2024
 * @date: 03/26/2024
 * @version 1.0
 */

public class Sort {
	/**
	 * Sorts the given array using an iterative Merge Sort algorithm. This method
	 * should only use a single extra array for merging purposes.
	 * 
	 * @param A the array to be sorted
	 */
	public static void iterativeMerge(Array A) {
		int size = A.length();
		Array tempArray = new Array(size); // temp array

		// iterating through the subarrays and starting from size 1 then 2, 4, ..., N/2
		for (int subArrSize = 1; subArrSize < size; subArrSize *= 2) {
			for (int leftStart = 0; leftStart < tempArray.length() - 1; leftStart += 2 * subArrSize) {
				int midIndices = Math.min(leftStart + subArrSize - 1, size - 1);
				int rightEnd = Math.min(leftStart + 2 * subArrSize - 1, size - 1);

				merge(A, tempArray, leftStart, midIndices, rightEnd);
			}
		}
	}

	/**
	 * Merges two sorted subarrays into one sorted array.
	 * 
	 * @param A          the array to be sorted
	 * @param tempArray  the temporary array for merging
	 * @param leftStart  the start index of the first subarray
	 * @param midIndices the end index of the first subarray and start index of the
	 *                   second subarray
	 * @param rightEnd   the end index of the second subarray
	 */
	private static void merge(Array A, Array tempArray, int leftStart, int midIndices, int rightEnd) {
		for (int i = leftStart; i <= rightEnd; i++) {
			tempArray.setVal(i, A.getVal(i));
		}

		// current location of the first index in the first array
		int i = leftStart;
		// current location of the first index in the second array
		int j = midIndices + 1;
		// current location of the original Array
		int k = leftStart;

		// checking while the first/second subArrays are in boundry
		while (i <= midIndices && j <= rightEnd) {
			int leftSubArrayVal = tempArray.getVal(i);
			int rightSubArrayVal = tempArray.getVal(j);

			if (leftSubArrayVal <= rightSubArrayVal) {
				A.setVal(k, leftSubArrayVal);
				i++;
			} else {
				A.setVal(k, rightSubArrayVal);
				j++;
			}
			k++;
		}

		// Copy any remaining elements from the first subarray
		while (i <= midIndices) {
			A.setVal(k, tempArray.getVal(i));
			i++;
			k++;
		}
		// Copy any remaining elements from the second subarray
		while (j <= rightEnd) {
			A.setVal(k, tempArray.getVal(j));
			j++;
			k++;
		}
	}

	/**
	 * Sorts the given array using a hybrid of Insertion Sort and Merge Sort. The
	 * array is first divided into sections of the specified size, each sorted with
	 * Insertion Sort, then merged together. Only one extra array is used for
	 * merging.
	 * 
	 * @param A    the array to be sorted
	 * @param size the size of sections to be initially sorted with Insertion Sort
	 */
	public static void insertMerge(Array A, int size) {
		for (int start = 0; start < A.length(); start += size) {
			inserSort(A, start, Math.min(start + size, A.length()));
		}

		Array tempArray = new Array(A.length()); // temp array

		// iterating through the subarrays and starting from size 1 then 2, 4, ..., N/2
		for (int subArrSize = size; subArrSize < A.length(); subArrSize *= 2) {

			for (int leftStart = 0; leftStart < tempArray.length() - 1; leftStart += 2 * subArrSize) {
				int midIndices = Math.min(leftStart + subArrSize - 1, A.length() - 1);
				int rightEnd = Math.min(leftStart + 2 * subArrSize - 1, A.length() - 1);

				merge(A, tempArray, leftStart, midIndices, rightEnd);
			}
		}

	}

	/**
	 * Performs an insertion sort on a sublist of the given array.
	 * 
	 * @param A     The array to be partially sorted.
	 * 
	 * @param start The starting index of the sublist within the array `A`
	 * 
	 * @param end   The ending index of the sublist within the array `A`
	 */
	private static void inserSort(Array A, int start, int end) {
		for (int i = start + 1; i < end; i++) {
			int j = i;
			while (j - 1 >= start) {
				if (A.getVal(j) < A.getVal(j - 1)) {
					A.swap(j, j - 1);
					j--;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * Sorts the given array using a recursive 3-way Merge Sort algorithm. This
	 * method divides the array into thirds, sorts each part, and then merges them.
	 * A single extra array is used for the merging process.
	 * 
	 * @param A the array to be sorted
	 */
	public static void threeWayMerge(Array A) {
		Array tempArray = new Array(A.length());
		mergeSortAux(A, tempArray, 0, A.length() - 1);
	}

	/**
	 * Recursively sorts the sublists of the array and then merges them.
	 * 
	 * @param A         the array to be sorted
	 * @param tempArray the temporary array for merging
	 * @param i         the start index of the sublist to be sorted
	 * @param j         the end index of the sublist to be sorted
	 */
	private static void mergeSortAux(Array A, Array tempArray, int i, int j) {
		if (i >= j)
			return;
		int third = (j - i) / 3; // by 3 sublists
		int mid = i + third;
		int mid1 = mid + third;

		mergeSortAux(A, tempArray, i, mid);
		mergeSortAux(A, tempArray, mid + 1, mid1);
		mergeSortAux(A, tempArray, mid1 + 1, j);

		merge(A, tempArray, i, mid, mid1 + 1, j);
	}

	/**
	 * Merges three sorted sublists of an array into a single sorted sublist. This
	 * method is handling the merging process. It first compares and merges elements
	 * from the three sublists into a temporary array in sorted order. After all
	 * sublists have been processed, it copies the sorted elements back into the
	 * original array.
	 *
	 * @param a         The original array containing the sublists to be merged.
	 * @param tempArray A temporary array used for merging the elements in sorted
	 *                  order.
	 * @param start     The starting index of the first sublist in the original
	 *                  array.
	 * @param mid       The ending index of the first sublist and one less than the
	 *                  starting index of the second sublist.
	 * @param mid2      The ending index of the second sublist and the starting
	 *                  index of the third sublist.
	 * @param end       The ending index of the third sublist in the original array.
	 */
	private static void merge(Array a, Array tempArray, int start, int mid, int mid2, int end) {
		int i = start; // Starting index for the first sublist
		int j = mid + 1; // Starting index for the second sublist
		int k = mid2; // Starting index for the third sublist
		int l = start; // Starting index for the tempArray

		// merge and compare elements from the three sorted sublists of the array into
		// the temporary array
		while (i <= mid && j <= mid2 - 1 && k <= end) {
			if (a.getVal(i) < a.getVal(j)) {
				if (a.getVal(i) < a.getVal(k)) {
					tempArray.setVal(l, a.getVal(i));
					l++;
					i++;
				} else {
					tempArray.setVal(l, a.getVal(k));
					l++;
					k++;
				}
			} else {
				if (a.getVal(j) < a.getVal(k)) {
					tempArray.setVal(l, a.getVal(j));
					l++;
					j++;
				} else {
					tempArray.setVal(l, a.getVal(k));
					l++;
					k++;
				}
			}
		}

		// Merge the remaining elements from the first and second sublists
		while (i <= mid && j <= mid2 - 1) {
			if (a.getVal(i) < a.getVal(j)) {
				tempArray.setVal(l, a.getVal(i));
				l++;
				i++;
			} else {
				tempArray.setVal(l, a.getVal(j));
				l++;
				j++;
			}
		}

		// Merge the remaining elements from the second and third sublists
		while (j <= mid2 - 1 && k <= end) {
			if (a.getVal(j) < a.getVal(k)) {
				tempArray.setVal(l, a.getVal(j));
				l++;
				j++;
			} else {
				tempArray.setVal(l, a.getVal(k));
				l++;
				k++;
			}
		}

		// Merge the remaining elements from the first and third sublists
		while (i <= mid && k <= end) {
			if (a.getVal(i) < a.getVal(k)) {
				tempArray.setVal(l, a.getVal(i));
				l++;
				i++;
			} else {
				tempArray.setVal(l, a.getVal(k));
				l++;
				k++;
			}
		}
		// handling leftovers from three sublists
		mergeElementsFromThreeSubLists(a, tempArray, mid, mid2, end, i, j, k, l);

		// Copy the merged elements back into the original array
		for (i = start; i <= end; i++) {
			a.setVal(i, tempArray.getVal(i));
		}
	}

	/**
	 * Handles copying any remaining elements from the three sorted sublists into
	 * the temporary array.
	 *
	 * @param a         The original array containing the sublists to be merged.
	 * @param tempArray The temporary array used for merging elements.
	 * @param mid       The ending index of the first sublist.
	 * @param mid2      The ending index of the second sublist.
	 * @param end       The ending index of the third sublist.
	 * @param i         The current index in the first sublist.
	 * @param j         The current index in the second sublist.
	 * @param k         The current index in the third sublist.
	 * @param l         The current index in the temporary array where elements are
	 *                  being merged.
	 */
	private static void mergeElementsFromThreeSubLists(Array a, Array tempArray, int mid, int mid2, int end, int i,
			int j, int k, int l) {
		// Copy any remaining elements from the first sublist
		while (i <= mid) {
			tempArray.setVal(l, a.getVal(i));
			l++;
			i++;
		}

		// Copy any remaining elements from the second sublist
		while (j <= mid2 - 1) {
			tempArray.setVal(l, a.getVal(j));
			l++;
			j++;
		}

		// Copy any remaining elements from the third sublist
		while (k <= end) {
			tempArray.setVal(l, a.getVal(k));
			l++;
			k++;
		}
	}

	/**
	 * Sorts the given array using a five-way Quicksort. This involves using two
	 * pivots to divide the array into five sections and sorting them. This should
	 * be done in place, without using extra data structures.
	 * 
	 * @param A the array to be sorted
	 */
	public static void fiveWayQuick(Array A) {
		fiveWayQuickSort(A, 0, A.length() - 1);
	}

	/**
	 * This method implements the five-way quicksort algorithm to sort an array. It
	 * recursively partitions the array into five sublists based on two pivot values
	 * and sorts each sublist separately.
	 * 
	 * @param A    The array to be sorted.
	 * @param low  The starting index of the sublist to be sorted.
	 * @param high The ending index of the sublist to be sorted.
	 */
	private static void fiveWayQuickSort(Array A, int low, int high) {
		if (low < high) {
			int p1 = A.getVal(low);
			int p2 = A.getVal(high);
			if (p1 > p2) {
				A.swap(low, high);
				p1 = A.getVal(low);
				p2 = A.getVal(high);
			}

			// Initialize pointers for the five sublists
			int less = low + 1; // Points right after pivot1
			int great = high - 1; // Points left before pivot2
			int i = less;

			// logic for five-way sorting
			while (i <= great) // if the index didn't reach p2
			{
				// case 1: element < p1:
				if (A.getVal(i) < p1) {
					A.swap(i, less);
					i++;
					less++;
				} // case 2: element > p2
				else if (A.getVal(i) > p2) {
					A.swap(i, great);
					great--;
				} else {
					i++;
				}
			}
			// Place the pivots in their correct positions
			less--;
			A.swap(low, less);
			great++;
			A.swap(high, great);

			fiveWayQuickSort(A, low, less - 1); // Elements less than pivot1
			fiveWayQuickSort(A, less + 1, great - 1); // Elements between pivot1 and pivot2
			fiveWayQuickSort(A, great + 1, high); // Elements greater than pivot2
		}
	}

	/**
	 * Sorts an array knowing that elements are no more than d positions away from
	 * their sorted position, using a locality-aware version of Selection Sort. This
	 * method should be in place with a worst-case runtime of O(dN).
	 * 
	 * @param A the array to be sorted
	 * @param d the maximum distance from the sorted position
	 */
	public static void locSelect(Array A, int d) {
		for (int i = 0; i < A.length(); i++) {
			// Initialize the index of the minimum value to the current position
			int minIndex = i;

			// Only go up to i+d but within the array bounds
			for (int j = i + 1; j <= Math.min(i + d, A.length() - 1); j++) {
				if (A.getVal(j) < A.getVal(minIndex)) {
					minIndex = j; // Update the index of the new minimum
				}
			}

			if (minIndex != i) {
				A.swap(i, minIndex);
			}
		}
	}

	/**
	 * Sorts an array with elements no more than d positions away from their sorted
	 * position, using a locality-aware version of Heapsort. This method should be
	 * in place with a worst-case runtime of O(Nlogd).
	 * 
	 * @param A the array to be sorted
	 * @param d the maximum distance from the sorted position
	 */
	public static void locHeap(Array A, int d) {
		for (int i = (int) Math.floor(A.length() / 2); i >= 0; i--) {
			sink(A, i, A.length());
		}

		// sort-down
		int hsize = A.length();
		while (hsize > 1) {
			A.swap(0, hsize - 1);
			hsize--;
			sink(A, 0, hsize);
		}
	}

	/**
	 * This method performs the sink operation in a binary heap. Starting from the
	 * given index i, it moves the element down the heap until it satisfies the heap
	 * property.
	 * 
	 * @param A    The array representing the binary heap.
	 * @param i    The index of the element to be sunk.
	 * @param size The size of the heap.
	 */
	private static void sink(Array A, int i, int size) {
		int maxNode = i;
		int leftChild = 2 * i + 1;
		int rightChild = 2 * i + 2;

		// if leftChild > maxNode
		if (leftChild < size && A.getVal(leftChild) > A.getVal(maxNode)) {
			maxNode = leftChild;
		}
		// if rightChild > maxNode
		if (rightChild < size && A.getVal(rightChild) > A.getVal(maxNode)) {
			maxNode = rightChild;
		}
		// if maxNode isn't the root
		if (maxNode != i) {
			A.swap(i, maxNode);
			sink(A, maxNode, size);
		}
	}

	/**
	 * Sorts a linked list using a recursive version of Merge Sort. The LinkedList
	 * class provided should be used as is.
	 * 
	 * @param list the linked list to be sorted
	 */
	public static LinkedList mergeSort(LinkedList list) {
		// list is empty or has only one element, no need to sort
		if (list.isEmpty()) {
			return list;
		}
		// if size is 1 retun list
		if ((list.head() != null) && (list.tail().tail() == null)) {
			return list;
		}

		// Halve the list
		LinkedList[] halves = halve(list);
		LinkedList L1 = halves[0];
		LinkedList L2 = halves[1];

		// Recursively sort the halves
		LinkedList S1 = mergeSort(L1);
		LinkedList S2 = mergeSort(L2);

		// Merge the sorted halves
		return merge(S1, S2);
	}

	/**
	 * Merges two sorted lists into one sorted list. This method iterates through
	 * both lists simultaneously, comparing their elements and appending the smaller
	 * one to the merged list until all elements are merged.
	 *
	 * @param list1 The first sorted linked list.
	 * @param list2 The second sorted linked list.
	 * @return The merged sorted linked list.
	 */
	private static LinkedList merge(LinkedList list1, LinkedList list2) {
		LinkedList mergedList = new LinkedList();
		Node current1 = list1.head();
		Node current2 = list2.head();

		// Traverse both lists simultaneously
		while (current1 != null && current2 != null) {
			// Compare values of current nodes in list1 and list2
			if (current1.val() <= current2.val()) {

				mergedList.add(current1.val()); // Add current node of list1 to mergedList
				current1 = current1.next(); // Move to next node in list1
			} else {
				mergedList.add(current2.val()); // Add current node of list2 to mergedList
				current2 = current2.next(); // Move to next node in list2
			}
		}

		// Add remaining nodes from list1
		while (current1 != null) {
			mergedList.add(current1.val());
			current1 = current1.next();
		}

		// Add remaining nodes from list2
		while (current2 != null) {
			mergedList.add(current2.val());
			current2 = current2.next();
		}

		// Copy the merged list to maintain consistency in list handling
		LinkedList l = new LinkedList();
		Node lNode = mergedList.head();
		while (lNode != null) {
			l.add(lNode.val());
			lNode = lNode.next();
		}

		return l;
	}

	/**
	 * This method divides the list into two by adding alternate nodes to each of
	 * the two new lists, halving the original list.
	 *
	 * @param list The linked list to be halved.
	 * @return An array of two LinkedLists representing the two halves.
	 */
	private static LinkedList[] halve(LinkedList list) {
		LinkedList firstHalf = new LinkedList();
		LinkedList secondHalf = new LinkedList();

		Node current = list.head();
		boolean addToFirst = true;

		// divide the list into two by adding nodes to each half
		while (current != null) {
			if (addToFirst) {
				firstHalf.add(current.val());
			} else {
				secondHalf.add(current.val());
			}

			addToFirst = !addToFirst;
			current = current.next();
		}

		LinkedList[] halves = { firstHalf, secondHalf };
		return halves;
	}

	/**
	 * Sorts a linked list using a recursive version of 2-way Quicksort. The
	 * LinkedList class provided should be used as is.
	 * 
	 * @param list the linked list to be sorted
	 */
	public static LinkedList quickSort(LinkedList list) {
		return list;
	}

	/**
	 * Sorts a linked list using a recursive version of Insertion Sort. The
	 * LinkedList class provided should be used as is.
	 * 
	 * @param list the linked list to be sorted
	 */
	public static LinkedList insertionSort(LinkedList list) {
		if (list.isEmpty()) {
			return list;
		}

		LinkedList sortedList = insertionSort(list.tail());
		sortedList = insert(list.head(), sortedList);
		return sortedList;
	}

	/**
	 * Inserts a node into a sorted linked list at the correct position to maintain
	 * the list's sorted order. This method iterates through the list to find the
	 * appropriate spot for the new node.
	 *
	 * @param target     The node to insert.
	 * @param sortedList The sorted linked list where the node will be inserted.
	 * @return The sorted linked list with the node inserted.
	 */
	private static LinkedList insert(Node target, LinkedList sortedList) {
		// base case:
		if (target == null) {
			return sortedList;
		}

		if (sortedList.isEmpty()) {
			sortedList.add(target.val());
			return sortedList;
		}
		// get reverse elements.
		Node cur = sortedList.head();

		// create a temporary list to store the reverse of the sorted list.
		LinkedList reverse = new LinkedList();

		// traverse the sorted list and add each element to the reverse list,
		// effectively reversing the sorted list.
		while (cur != null) {
			reverse.add(cur.val());
			cur = cur.next();
		}

		// Create a new list that will eventually contain the elements in
		// sorted order including the target node.
		LinkedList L = new LinkedList();
		Node reverseCur = reverse.head();

		// traverse the reversed list. Attempt to find the correct spot for
		// the target node by comparing its value with the current node's value.
		while (reverseCur != null) {
			Integer pop = reverseCur.val();
			if (target == null) {
				L.add(pop);
			} else if (pop > target.val()) {
				L.add(pop);
			} else {
				L.add(target.val());
				target = null;
				L.add(pop); // Add the current node's value to the new list.
			}
			reverseCur = reverseCur.next();
		}
		// If the target node has not been inserted (it's greater than all elements of
		// the list), add it to the end of the new list.
		if (target != null) {
			L.add(target.val());
		}
		return L;
	}

	/**
	 * Sorts a linked list using a recursive version of Bubble Sort. The LinkedList
	 * class provided should be used as is.
	 * 
	 * @param list the linked list to be sorted
	 */
	public static LinkedList bubbleSort(LinkedList list) {
		Node cur = list.head();
		while (cur != null) {
			list = bubble(list); // Updating the list everytime
			cur = cur.next();
		}
		return list;
	}

	private static LinkedList bubble(LinkedList list) {
		if (list.isEmpty()) {
			return list;
		}
//		 if L.size = 1: return L
		if ((list.head() != null) && (list.tail().tail() == null)) {
			return list;
		}

		Node a = list.head();
		Node b = list.tail().head();
		LinkedList T = list.tail().tail();

		if (a.val() <= b.val()) {
			T.add(b.val());
			T = bubble(T);
			T.add(a.val());
		} else {
			T.add(a.val());
			T = bubble(T);
			T.add(b.val());
		}
		return T;
	}
}