// Sorting Algo Tester
// Follow the directions so it works properly
// Author: Nathan Oswald

import java.util.Arrays;
import java.util.Scanner;

public class testSorts_N1 {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String cont = "Y";
		while (cont.equals("Y")) {

			System.out.println("The sorting algos are labels 1-10 in order as the appear on the spec");
			System.out.print("Which sorting algorithm do you want test? Please enter a number 1-10: ");
			int sortThis = keyboard.nextInt();
			while (sortThis < 1 || sortThis > 10) {
				System.out.print("Follow Direction!!! Input Valid Number: ");
				sortThis = keyboard.nextInt();
				System.out.println();
			}
			System.out.println();
			System.out.println("The integer inputed will have the code test from 0-N length arrays");
			System.out.print("Max array size: ");
			int thisSize = keyboard.nextInt();

			System.out.println();
			System.out.println(
					"If this is a locality-aware array, provide the correct d-value, other wise, enter the same number as before");
			System.out.println("This will also be used as size parameter for insert merge.");
			System.out.print("d value: ");
			int d = keyboard.nextInt();

			System.out.println();
			System.out.println("My quicksort for linked list does NOT return the list.");
			System.out.println("If your's does, please open the test file and make it do so.");
			System.out.println("The other linked list ones, however, do.");
			System.out.print("Proceed (enter any integer): ");
			int y = keyboard.nextInt();

			System.out.println();

			LinkedList ll;
			Array arr;
			int[] a1;
			int flag = 0;

			for (int i = 0; i < thisSize; i++) {
				ll = new LinkedList();
				arr = new Array(i);
				a1 = new int[0];
				if (i != 0) {
					a1 = ArrayGen.getRand(i, d);
					for (int k = i - 1; k > -1; k--) {
						ll.add(a1[k]);
					}
					for (int k = 0; k < i; k++) {
						arr.setVal(k, a1[k]);
					}
				}

				Arrays.sort(a1);
				String theAlgo = "";

				if (sortThis == 1) {
					Sort.iterativeMerge(arr);
					theAlgo = "Iterative Merge: ";
				} else if (sortThis == 2) {
					Sort.insertMerge(arr, d);
					theAlgo = "Insert Merge: ";
				} else if (sortThis == 3) {
					Sort.threeWayMerge(arr);
					theAlgo = "Three Way Merge: ";
				} else if (sortThis == 4) {
					Sort.fiveWayQuick(arr);
					theAlgo = "Five Way Quick: ";
				} else if (sortThis == 5) {
					Sort.locSelect(arr, d);
					theAlgo = "Locality Selection: ";
				} else if (sortThis == 6) {
					Sort.locHeap(arr, d);
					theAlgo = "Locality Heap: ";
				}
//				} else if (sortThis == 7) {
//					ll = Sort.mergeSort(ll);
//					theAlgo = "Merge Sort LL:  ";
//				} else if (sortThis == 8) {
//					Sort.quickSort(ll);
//					theAlgo = "Quicksort LL: ";
//				} else if (sortThis == 9) {
//					ll = Sort.insertionSort(ll);
//					theAlgo = "Insertion Sort LL: ";
//				} else if (sortThis == 10) {
//					ll = Sort.bubbleSort(ll);
//					theAlgo = "Bubble Sort LL:   ";
//				}

				if (i == 0) {
					System.out.println(theAlgo);
				}

				if (sortThis < 7) {
					for (int k = 0; k < i; k++) {
						if (a1[k] != arr.getVal(k)) {
							flag = 1;
							break;
						}

					}
					if (flag == 1) {
						System.out.print("This failed with size: ");
						System.out.println(i);
					}
				}

				else {
					Node curr = ll.head();
					for (int k = 0; k < i; k++) {
						if (a1[k] != curr.val()) {
							flag = 1;
							break;
						}
						curr = curr.next();

					}
					if (flag == 1) {
						System.out.print("This failed with size: ");
						System.out.println(i);
					}
				}
			}
			if (flag == 0) {
				System.out.println("All sized arrays passed!");
			}
			System.out.println("Do another (Y/N)? : ");
			cont = keyboard.next();
		}
	}
}
