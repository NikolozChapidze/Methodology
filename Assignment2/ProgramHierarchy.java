
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	/** width of each box */
	private static final double BOX_WIDTH = 200;
	/** height of each box */
	private static final double BOX_HEIGHT = 60;
	/** gap between second row boxes */
	private static final double BOX_GAP = 20;
	/** vertical gap between top and lower boxes */
	private static final double LINE = 40;

	public void run() {
		drawBoxes();
		drawLines();
		writeLabels();
	}

	// pre:everything is drawn only labels are left
	// post:lables are printed
	private void writeLabels() {
		firstLable();
		restOfTheLables();
	}

	// drawing first box's label
	private void firstLable() {
		GLabel firstLable = new GLabel("Program");
		add(firstLable, getWidth() / 2 - firstLable.getWidth() / 2,
				getHeight() / 2 - LINE / 2 - BOX_HEIGHT / 2 + (firstLable.getAscent() - firstLable.getDescent()) / 2);
	}

	// drawing rest of the labels
	private void restOfTheLables() {
		GLabel lable = new GLabel("GraphicsProgram");
		double y = getHeight() / 2 + LINE / 2 + BOX_HEIGHT / 2 + (lable.getAscent() - lable.getDescent()) / 2;
		add(lable, getWidth() / 2 - BOX_WIDTH - BOX_GAP - lable.getWidth() / 2, y);
		lable = new GLabel("ConsoleProgram");
		add(lable, getWidth() / 2 - lable.getWidth() / 2, y);
		lable = new GLabel("DialogePRogram");
		add(lable, getWidth() / 2 + BOX_WIDTH + BOX_GAP - lable.getWidth() / 2, y);
	}

	// drawing lines which connects boxes
	private void drawLines() {
		double startingY = getHeight() / 2 - LINE / 2;
		double startingX = getWidth() / 2;
		double finishx = getWidth() / 2 - BOX_GAP - BOX_WIDTH;
		double finishy = getHeight() / 2 + LINE / 2;
		GLine line = new GLine(startingX, startingY, finishx, finishy);
		for (int i = 0; i < 3; i++) {
			add(line);
			finishx = finishx + BOX_WIDTH + BOX_GAP;
			line = new GLine(startingX, startingY, finishx, finishy);
		}
	}

	// drawing boxes where the labels will be written
	private void drawBoxes() {
		drawFirstBox();
		drawRestOfTheBoxes();
	}

	// drawing the box that is located at the first line
	private void drawFirstBox() {
		GRect box = new GRect((getWidth() - BOX_WIDTH) / 2, getHeight() / 2 - LINE / 2 - BOX_HEIGHT, BOX_WIDTH,
				BOX_HEIGHT);
		add(box);
	}

	// drawing boxes which are located at the second line
	private void drawRestOfTheBoxes() {
		double y = getHeight() / 2 + LINE / 2;
		double x = (getWidth() - BOX_WIDTH * 3 - BOX_GAP * 2) / 2;
		GRect box = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		for (int i = 0; i < 3; i++) {
			add(box);
			x = x + BOX_WIDTH + BOX_GAP;
			box = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		}
	}
}
