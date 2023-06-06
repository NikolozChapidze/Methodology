
/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		fillCheckerboard();
	}

	// karel fill the checkerboard with beepers
	private void fillCheckerboard() {
		fillLine();
		goBack();
		while (leftIsClear()) {
			moveToNextLine();
			fillNextLine();
		}
	}

	// pre:karel is at the starting point of new line
	// post:karel filled the line and returned to starting point of line
	private void fillNextLine() {
		if (lastIsBeeper()) {
			if (frontIsClear()) {
				move();
				fillLine();
				goBack();
			}
		} else {
			fillLine();
			goBack();
		}
	}

	// pre: karel is at the one end of line, facing to east
	// post: kares arrived at another end of line, facing to east
	private void goBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}

	// pre:karel is at the starting point of new line
	// post: karel has checked if beeper is present at the down spot
	private boolean lastIsBeeper() {
		turnRight();
		move();
		if (beepersPresent()) {
			goToLastPosition();
			return true;
		}
		goToLastPosition();
		return false;
	}

	// pre: stand at the start of line, facing to south
	// post:moved up by one step, facing to east
	private void goToLastPosition() {
		turnAround();
		move();
		turnRight();
	}

	// moves up by 1 line
	private void moveToNextLine() {
		turnLeft();
		move();
		turnRight();
	}

	// pre: karel stand at the one end of the line, facing to east
	// post: line is filled with beepers using 1 move interval, karel stands at the
	// another end of line, facing to east
	private void fillLine() {
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}

		}
	}

}
