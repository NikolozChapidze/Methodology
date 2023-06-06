
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int n = readInt("enter a number: ");
		hailStone(n);
	}

	// this method counts how many moves does it took to reach 1, every calculation
	// step is printed
	private void hailStone(int n) {
		int counter = 0;
		while (n != 1) {
			if (n % 2 == 0) {
				print(n + " is even so I take half: ");
				n = n / 2;
				println(n);
			} else {
				print(n + " is odd, so I make 3n+1: ");
				n = 3 * n + 1;
				println(n);
			}
			counter++;
		}
		println("This process took " + counter + " to reach 1");
	}
}
