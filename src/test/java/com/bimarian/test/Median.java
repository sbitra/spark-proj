package com.bimarian.test;

// Median.java
// David Kosbie, 15-111/AB, Spring 2007, hw3

/*

Note:  These programs are not always written with ideal style, and as such
are not always complete solutions.  They are meant to be complete enough for
you to understand everything needed for your solutions.

2.	Write a program, Median.java, that reads in a list of integers using
scanner.nextInt(), ending when the sentinel -1 is entered, and prints out the
median of the data (for a definition of median, see Wikipedia's entry on
"median").  Note that even for integral data, the median may be a float -
for example, the median of { 2, 3 } is 2.5.  Note that we discussed a static
method in the Arrays class that is especially helpful here.

*/

import java.util.*;

public class Median {
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static int sentinel = -1;

	// Reads and returns an int, handling the case of non-int input.
	public static int readInt() {
		System.out.printf("Enter an integer (%d to quit): ",sentinel);
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.print("That was not an integer.  Please try again: ");  ;;
		}
		return scanner.nextInt();
	}
	
	// read in an array of integers, terminated by the sentinel
	public static int[] readIntArray() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		// load the ArrayList with integers up to the sentinel
		while (true) {
			int i = readInt();
			if (i == sentinel) break;
			list.add(i);
		}

		// convert the ArrayList to an array
		
		// Note1: actually, this is not necessary, since we could
		// return the ArrayList rather than an Array, and then call
		// Collections.sort(list), but we've not covered collections
		// yet, so without penalty you can convert to an array:
		
		// Note2: Once you are committed to converting to an array, 
		// here is the most efficient way to do this:
		// Integer[] array = (Integer[])list.toArray();
		// In this case, the return type of this method would
		// be Integer[] rather than int[].

		// Note3:  if you did not discover ArrayList.toArray, you
		// could instead (without penalty) do the following (with
		// the array being either an int[] or Integer[]):

		int[] array = new int[list.size()];
		for (int i=0; i<array.length; i++) array[i] = list.get(i);
		
		return array;
	}

	// Finds the median of the list.  If the list has no elements, returns 0.
	// The median of an odd-length list is the middle element, and the median
	// of an even-length list is the average of the middle two elements.
	// Note:  as a side-effect this sorts the list!
	public static double findMedian(int[] a) {
		Arrays.sort(a);
		if (a.length == 0)
			// no elements, just return 0
			return 0;
		else if (a.length % 2 == 1)
			// odd-length, return middle element
			return a[a.length/2];
		else
			// even-length, return average of middle two elements
			// be sure to divide by 2.0 and not by 2!
			return (a[a.length/2] + a[(a.length/2)-1])/2.0;
	}

	// Remember: your main method should be mostly a "traffic cop".
	// very little, if any, computation should occur here.
	public static void main(String[] args) {
		System.out.println("This program finds the median of a list of integers.\n");
		int[] a = readIntArray();
		if (a.length == 0)
			System.out.println("An empty list has no median!");			
		else {
			double median = findMedian(a);
			System.out.printf("The median of this list is: %1.1f\n",median);
		}
	}
}