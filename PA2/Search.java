//-----------------------------------------------------------------------------
//Name: Mohammad Mirabian
//CruzID: mmirabia
//Student ID# 1377020
//Date: 4/19/2015
//File Name: Search.java
//File Purpose: takes command line arguments to find a number of targets in a specified file
//---------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Search {

	public static void main(String[] args) throws IOException {

		int i;
		int targetIndex;
		// check number of command line arguments
		if (args.length < 2) {
			System.err.println("Usage: LineCount file");
			System.exit(1);
		}

		// find the number of lines in the file
		Scanner fileIn = new Scanner(new File(args[0]));
		int lineCount = 0;
		while (fileIn.hasNextLine()) {
			fileIn.nextLine();
			lineCount++;
		}

		// Allocate memory as much as the words-lines in the file
		String[] Array = new String[lineCount];
		int[] lineNumber = new int[lineCount];

		// adds number to the array	
		for (i = 1; i <= lineNumber.length; i++) {
			lineNumber[i - 1] = i;
		}

		fileIn = new Scanner(new File(args[0])); // rescan the file to go
													// through and put each word
													// in an index
		for (i = 0; fileIn.hasNextLine(); i++) {
			Array[i] = fileIn.nextLine();
		}

		// call merge sort to sort array Array
		mergeSort(Array, lineNumber, 0, lineCount - 1);

		// now go through the command line arguments and find the targets and
		// its indexes
		for (i = 1; i < args.length; i++) {

			targetIndex = binarySearch(Array, lineNumber, 0, Array.length - 1,
					args[i]);

			if (targetIndex >= 0) {
				System.out.println(args[i] + " was found on line "
						+ targetIndex);
			}

			else
				System.out.println("\n" + args[i]
						+ " does not exist in the file");

		}
	}

	// mergeSort
	static void mergeSort(String[] word, int[] lineNumber, int p, int r) {
		int q;
		if (p < r) {
			q = (p + r) / 2;
			mergeSort(word, lineNumber, p, q);
			mergeSort(word, lineNumber, q + 1, r);
			merge(word, lineNumber, p, q, r);
		}
	}

	// merge
	static void merge(String[] word, int[] lineNumber, int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		String[] left = new String[n1];
		String[] right = new String[n2];
		int[] leftNum = new int[n1];
		int[] rightNum = new int[n2];
		int i, j, k;

		for (i = 0; i < n1; i++) {
			left[i] = word[p + i];
			leftNum[i] = lineNumber[p + i];
		}
		for (j = 0; j < n2; j++) {
			right[j] = word[q + j + 1];
			rightNum[j] = lineNumber[q + j + 1];
		}
		i = 0;
		j = 0;
		for (k = p; k <= r; k++) {
			if (i < n1 && j < n2) {
				if (left[i].compareTo(right[j]) < 0) {
					word[k] = left[i];
					lineNumber[k] = leftNum[i];
					i++;
				} else {
					word[k] = right[j];
					lineNumber[k] = rightNum[j];
					j++;
				}
			} else if (i < n1) {
				word[k] = left[i];
				lineNumber[k] = leftNum[i];
				i++;
			} else { // j<n2
				word[k] = right[j];
				lineNumber[k] = rightNum[j];
				j++;
			}
		}
	}

	// binarySearch()
	// pre: Array A[p..r] is sorted
	public static int binarySearch(String[] A, int[] lineNumber, int p, int r,
			String target) {
		int q;
		if (p > r) {
			return -1;
		} else {
			q = (p + r) / 2;
			if (target.compareTo(A[q]) == 0) {
				return lineNumber[q];
			} else if (target.compareTo(A[q]) < 0) {
				return binarySearch(A, lineNumber, p, q - 1, target);
			} else { // target > A[q]
				return binarySearch(A, lineNumber, q + 1, r, target);
			}
		}
	}
}
