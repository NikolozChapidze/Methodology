
/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	public void run() {
		putBeeperOnTheMidPoint();
	}

	// karel puts beeper on the middle point of the first line
	private void putBeeperOnTheMidPoint() {
		if (leftIsClear()) {
			findingLength();
			prepareMap();
			findingMidPoint();
		} else {
			putBeeper();
		}
	}

	// pre:karel stands at the starting position
	// post:karel filled the first line with beepers and than gathered all of them
	// at one point 1x2 and stands at the starting position
	private void findingLength() {
		fillingFirstLine();
		gatherEveryBeeperAtOnePoint();
	}

	// pre: stands at the end of first line, which is filled with beepers
	// post:karel picked every beeper and moved them at the point of 1x2, karel is
	// at the starting position 1x1
	private void gatherEveryBeeperAtOnePoint() {
		while (beepersPresent()) {
			pickBeeperAndMoveToOnePoint();
			turnAround();
			if (frontIsClear()) {
				move();
			}
		}
		turnAround();
	}

	// pre:karel stand at the last point of the first line where beeper is present,
	// fscing to west
	// post:karel picked beeper and moved at 1x2, karel moves till the point of the
	// first line where is no beeper present
	private void pickBeeperAndMoveToOnePoint() {
		pickBeeper();
		while (frontIsClear()) {
			move();
		}
		moveUpPutMoveDown();
		while (beepersPresent()) {
			move();
		}
	}

	// pre: stands on 1X1 spot, facing to west
	// post: stands on 1x1 spot and facing to east
	private void moveUpPutMoveDown() {
		turnAround();
		moveUp();
		putBeeper();
		moveDown();
	}

	// pre: stands at the start of the line, facing to east
	// post: stands at the end of the first line, facing to west
	private void fillingFirstLine() {
		while (frontIsClear()) {
			putBeeper();
			move();
		}
		putBeeper();
		turnAround();
	}

	// pre:karel stands at the 1x1 and first line till the middle is filled with 2
	// beepers on each spot
	// post:karel picked up every beeper and puted one at the middle, karel stands
	// at the middle of first line
	private void findingMidPoint() {
		while (beepersPresent()) {
			pickBeeper();
			pickBeeper();
			move();
		}
		turnAround();
		move();
		putBeeper();
		turnAround();
	}

	// pre:karel is at the 1x1 point, at the 1x2 is beepers which number is equal to
	// the length of first line
	// post:karel has puted 2 beepers till the middle of the first line and returned
	// to the starting position 1x1
	private void prepareMap() {
		moveUp();
		while (beepersPresent()) {
			pickBeeper();
			if (beepersPresent()) {
				pickBeeper();
			}
			moveDown();
			putTwoBeepersAndReturn();
			moveUp();
		}
		moveDown();
	}

	// pre:karel has piked one beeper from the stack 1x2
	// post:karel puts 2 beepers at next free spot and returns to starting position
	// 1x1
	private void putTwoBeepersAndReturn() {
		while (beepersPresent()) {
			move();
		}
		putBeeper();
		putBeeper();
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}

	// karel moves up with one step
	private void moveUp() {
		turnLeft();
		move();
		turnRight();
	}

	// karel moves down with one step
	private void moveDown() {
		turnRight();
		move();
		turnLeft();
	}

}
