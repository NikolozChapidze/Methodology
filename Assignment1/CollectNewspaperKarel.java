
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		roadToReward();
		pickBeeper();
		roadToStartingPoint();
	}

	// pre: 6x3 beeper is already picked, facing to east
	// post: arrived at starting point, facing to east
	private void roadToStartingPoint() {
		turnAround();
		moveTillEnd();
		turnRight();
		move();
		turnRight();
	}

	// pre:3x4 starting position, facing to east
	// post: 6x3 where the beeper is located, facing to east
	private void roadToReward() {
		moveTillEnd();
		turnRight();
		move();
		turnLeft();
		move();
	}

	private void moveTillEnd() {
		while (frontIsClear()) {
			move();
		}
	}

}
