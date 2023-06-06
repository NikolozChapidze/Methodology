
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	/** radius of first circle */
	private static final double FIRST_OVAL_RADIUS = 72;
	/** difference between circle's radiuses */
	private static final double DIFFERENCE_OF_OVAL_RADIUS = (FIRST_OVAL_RADIUS / 2.54) * (2.54 - 1.65);
	/** number of the circles' */
	private static final int NUMBER_OF_OVALS = 3;

	public void run() {
		double startingPointX = getWidth() / 2 - FIRST_OVAL_RADIUS;
		double startingPointY = getHeight() / 2 - FIRST_OVAL_RADIUS;
		Color ovalColor = Color.red;
		drawTarget(startingPointX, startingPointY, NUMBER_OF_OVALS, FIRST_OVAL_RADIUS, ovalColor);
	}

	// this method draws target, big oval is red, middle is white and small is red
	private void drawTarget(double  startingPointX, double startingPointY, int ovalsNumber, double radius,
			Color ovalColor) {
		while (ovalsNumber > 0) {
			drawOval(startingPointX, startingPointY, radius, ovalColor);
			ovalsNumber--;
			if (ovalColor == Color.red) {
				ovalColor = Color.white;
			} else {
				ovalColor = Color.red;
			}
			startingPointX = startingPointX + DIFFERENCE_OF_OVAL_RADIUS;
			startingPointY = startingPointY + DIFFERENCE_OF_OVAL_RADIUS;
			radius = radius - DIFFERENCE_OF_OVAL_RADIUS;
		}
	}

	// this method draws one filled oval
	private void drawOval(double startingPointX, double startingPointY, double radius, Color ovalColor) {
		GOval oval = new GOval(startingPointX, startingPointY, radius * 2, radius * 2);
		// oval.setFillColor(ovalColor);
		oval.setFilled(true);
		oval.setColor(ovalColor);
		add(oval);
	}
}
