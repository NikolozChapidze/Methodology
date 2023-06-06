
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	public void run() {
		findRange();
	}

	// printing the task and checking if first inputed number equals or not 0, after
	// that we use the method that finds max and min
	private void findRange() {
		println("This program finds the largest and smallest numbers.");
		int x = readInt("? ");
		if (x == SENTINEL) {
			println("you should enter numbers");
		} else {
			findNumbers(x);
		}
	}

	// finding max and min numbers, after that using method that prints results
	private void findNumbers(int x) {
		int smallest = x;
		int largest = x;
		while (x != SENTINEL) {
			if (x < smallest) {
				smallest = x;
			}
			if (x > largest) {
				largest = x;
			}
			x = readInt("?");
		}
		printREsult(smallest, largest);
	}

	// printing already determined results- smallest and largest numbers
	private void printREsult(int smallest, int largest) {
		println("smallest: " + smallest);
		println("largest: " + largest);
	}
}
