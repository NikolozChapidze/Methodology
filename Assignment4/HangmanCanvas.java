
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private double offset = 60;

	private StringBuilder usedWords = new StringBuilder();

	private double wordOffset = 90;
	
//	private GLine scaffold;
//
//	private GLine beam;
//
//	private GLine rope;

//	private GOval head;
//	
//	private GLine leftArm;
//	
//	private GLine rightArm;
//	
//	private GLine leftLeg;
//	
//	private GLine rightLeg;
//	
//	private GLine leftFoot;
//
//	private GLine rightFoot;
//	
//	private GLine heap;
//
//	private GLine upperLeftArm;
//	
//	//private 

	private GLabel userWord = new GLabel("", getWidth() / 2 - BEAM_LENGTH,
			offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH );
	private GLabel usedWordsLable= new GLabel("",getWidth() / 2 - BEAM_LENGTH,
			offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH + wordOffset+40);
	private int bodyCounter = 0;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		GLine scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, offset + SCAFFOLD_HEIGHT, getWidth() / 2 - BEAM_LENGTH,
				offset);
		GLine beam = new GLine(scaffold.getEndPoint().getX(), scaffold.getEndPoint().getY(),
				scaffold.getEndPoint().getX() + BEAM_LENGTH, scaffold.getEndPoint().getY());
		GLine rope = new GLine(beam.getEndPoint().getX(), beam.getEndPoint().getY(), beam.getEndPoint().getX(),
				beam.getEndPoint().getY() + ROPE_LENGTH);
		add(scaffold);
		add(beam);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		if (userWord.getLabel().equals("")) {
			userWord.setLabel(word);
			userWord.setFont(new Font("Serif", Font.PLAIN, 40));
			userWord.setFont(new Font("Serif", Font.PLAIN, 20));
//			Color pointColor = new Color(235, 4, 80);
//			userWord.setColor(pointColor);

			userWord.setLocation(getWidth() / 2 - BEAM_LENGTH,
					offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH + wordOffset);
			usedWordsLable.setLocation(getWidth() / 2 - BEAM_LENGTH,
					offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH + wordOffset+40);
			add(userWord);
			add(usedWordsLable);
		} else {
			userWord.setLabel(word);
		}
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		addBodyPart();
		bodyCounter++;
			usedWords.append(letter);
			usedWordsLable.setLabel(usedWords.toString());
	}

	private void addBodyPart() {
		switch (bodyCounter) {
		case 0:
			drawHead();
			break;
		case 1:
			drawBody();
			break;
		case 2:
			drawLeftArm();
			break;
		case 3:
			drawRightArm();
			break;
		case 4:
			drawLeftLeg();
			break;
		case 5:
			drawRightLeg();
			break;
		case 6:
			drawLeftFoot();
			break;
		case 7:
			drawRightFoot();
			break;
		default:
			break;
		}
	}

	private void drawRightFoot() {
		GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);
	}

	private void drawLeftFoot() {
		GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}

	private void drawRightLeg() {
		GLine hip = new GLine(getWidth() / 2 + HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		GLine rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 + HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(hip);
		add(rightLeg);
	}

	private void drawLeftLeg() {
		GLine hip = new GLine(getWidth() / 2 - HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 - HIP_WIDTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(hip);
		add(leftLeg);
	}

	private void drawRightArm() {
		GLine rightShoulder = new GLine(getWidth() / 2, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 + UPPER_ARM_LENGTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		GLine rightArm = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth() / 2 + UPPER_ARM_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(rightShoulder);
		add(rightArm);
	}

	private void drawHead() {
		GOval head = new GOval(getWidth() / 2 - HEAD_RADIUS, offset + ROPE_LENGTH, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	private void drawBody() {
		GLine body = new GLine(getWidth() / 2, offset + ROPE_LENGTH + 2 * HEAD_RADIUS, getWidth() / 2,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(body);
	}

	private void drawLeftArm() {
		GLine leftShoulder = new GLine(getWidth() / 2, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 - UPPER_ARM_LENGTH, offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		GLine leftArm = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth() / 2 - UPPER_ARM_LENGTH,
				offset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(leftShoulder);
		add(leftArm);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
