
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	public void run() {
		fillColumn();
		while (frontIsClear()) {
			moveToNextColumn();
			fillColumn();
		}
	}

	// pre:karel stands at the bottom of the column which is already filled
	// post:karel has moved to next column's bottom
	private void moveToNextColumn() {
		for (int i = 0; i < 4; i++) {
			move();
		}
	}

	// pre: stand at starting point of column, facing to east
	// post: column is filled and karel stands at bottom of column, facing to east
	private void fillColumn() {
		turnLeft();
		if (noBeepersPresent()) {
			putBeeper();
		}
		while (frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
		moveToBottom();
	}

	// pre: stands at the top of the columns, facing to north
	// post: stands at the bottom of column, facing to east
	private void moveToBottom() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}

}
