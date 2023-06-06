
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		drawPyramid();
	}

	// drawing piramid lines one at a time and moving up until the piramid is
	// finished
	private void drawPyramid() {
		double startingPointY = getHeight() - BRICK_HEIGHT;
		int brickNumber = BRICKS_IN_BASE;
		double startingPointX = (getWidth() - BRICK_WIDTH * brickNumber) / 2;
		while (brickNumber > 0) {
			drawBrickLine(startingPointX, startingPointY, brickNumber);
			brickNumber--;
			startingPointX = startingPointX + BRICK_WIDTH / 2;
			startingPointY = startingPointY - BRICK_HEIGHT;
		}
	}

	// drawing one brick line using the starting point coordinants and amount of the
	// bricks in the line
	private void drawBrickLine(double startingPointX, double startingPontY, int brickNumber) {
		while (brickNumber > 0) {
			drawBrick(startingPointX, startingPontY);
			brickNumber--;
			startingPointX = startingPointX + BRICK_WIDTH;
		}
	}

	// drawing one brick using just starting point
	private void drawBrick(double startingPointX, double startingPontY) {
		GRect brick = new GRect(startingPointX, startingPontY, BRICK_WIDTH, BRICK_HEIGHT);
		add(brick);
	}
}
