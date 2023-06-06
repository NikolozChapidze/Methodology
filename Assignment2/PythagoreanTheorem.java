
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Phytagorean theorem.");
		pythagor();
	}

	// this method counts length of hypotenuse with sides' length, that are entered
	// by user
	private void pythagor() {
		double a = readDouble("a:");
		while (a <= 0) {
			println("a can't be negative or 0");
			a = readDouble("a:");
		}
		double b = readDouble("b:");
		while (b <= 0) {
			println("b can't be negative or 0");
			b = readDouble("b:");
		}
		double c = Math.sqrt(a * a + b * b);
		println("c = " + c);
	}
}
