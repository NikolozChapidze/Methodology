
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import jdk.nashorn.internal.objects.Global;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private double gameSpeed = 70;

	private int brickAmount = NBRICK_ROWS * NBRICKS_PER_ROW;

	private int lives = NTURNS;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private double vx = 0;

	private double vy = +3.0;

	private GRect paddle;

	private GOval ball;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		initialization();
		addMouseListeners();
		waitForClick();
		process();
	}

	//drawing the whole game panel
	private void initialization() {
		drawWindow();
		drawBricks();
		drawPaddle();
		drawBall();
	}

	//draw/set window size
	private void drawWindow() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);

//		add(new GRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT));
	}

	//draw bricks 
	private void drawBricks() {
		double startingY = BRICK_Y_OFFSET;
		for (int i = 0; i < NBRICK_ROWS; i++) {
			drawBrickLine(startingY, colorDetermination(i));
			startingY += BRICK_HEIGHT + BRICK_SEP;
		}
	}

	//determine color for brick line
	private Color colorDetermination(int i) {
		if (i < 2) {
			return Color.RED;
		} else if (i < 4) {
			return Color.ORANGE;
		} else if (i < 6) {
			return Color.YELLOW;
		} else if (i < 8) {
			return Color.GREEN;
		} else if (i < 10) {
			return Color.CYAN;
		}
		return Color.BLACK;
	}

	//draw single line of bricks 
	private void drawBrickLine(double startingY, Color color) {
		double startingX = (getWidth() - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			drawBrick(startingX, startingY, color);
			startingX += BRICK_WIDTH + BRICK_SEP;
		}
	}

	//draw one single brick
	private void drawBrick(double startingX, double startingY, Color color) {
		GRect brick = new GRect(startingX, startingY, BRICK_WIDTH, BRICK_HEIGHT);
		brick.setColor(color);
		brick.setFillColor(color);
		brick.setFilled(true);
		add(brick);
	}

	//draw/add paddle 
	private void drawPaddle() {
		paddle = new GRect((getWidth() - PADDLE_WIDTH) / 2, getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH,
				PADDLE_HEIGHT);
		paddle.setFillColor(Color.BLACK);
		paddle.setFilled(true);
		add(paddle);
	}

	//draw ball
	private void drawBall() {
		ball = new GOval(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setFillColor(Color.BLACK);
		ball.setFilled(true);
		add(ball);
	}

	//whole process of game, starting from centre , moving ball , checking colliding object , printing result
	private void process() {
		beginningFromCentre();
		while (isGameNotOver()) {
			moveBall();
			checkCollider();
		}
		printResult();
	}

	//printing result whether user won or not 
	private void printResult() {
		removeAll();
		GLabel result = new GLabel("YOU WON");
		if (lives == 0) {
			result = new GLabel("YOU LOST");
		}
		result.setLocation((getWidth() - result.getWidth()) / 2,
				getHeight() / 2 + (result.getAscent() - result.getDescent()) / 2);
		add(result);
	}

	// beginning from centre from the start and after loosing turn
	private void beginningFromCentre() {
		ball.setLocation(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}

	//checking colliding object 
	private void checkCollider() {
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			vy = -vy;
			paddleCheck();

		} else if (collider != null) {
			brickAmount--;
			remove(collider);
			vy = -vy;
		}
	}

	//checking ball to not get stuck in paddle
	private void paddleCheck() {
		if (paddle.getY() - vy <= ball.getY() + BALL_RADIUS * 2) {
			System.out.println(vy);
			if ((paddle.getX() < ball.getX() + BALL_RADIUS * 2 || paddle.getX() + PADDLE_WIDTH > ball.getX())
					&& paddle.getY() < ball.getY() + BALL_RADIUS * 2) {
				ball.move(0, -((ball.getY() + BALL_RADIUS * 2) - paddle.getY()));
			}
			if (paddle.getY() < ball.getY() + BALL_RADIUS) {
				if ((paddle.getX() < ball.getX() + BALL_RADIUS * 2 && paddle.getX() > ball.getX())
						|| (paddle.getX() + PADDLE_WIDTH > ball.getX()
								&& paddle.getX() + PADDLE_WIDTH < ball.getX() + BALL_RADIUS * 2)) {
					vy = -vy;
					vx = -vx;
				}
			}
		}
	}

	//moving ball animation
	private void moveBall() {
		checkSides();
		ball.move(vx, vy);
		pause(gameSpeed);
	}

	//checking sides and changing directions if ball hits the sides
	private void checkSides() {
		if (ball.getX() <= 0 || ball.getX() + BALL_RADIUS * 2 >= getWidth()) {
			vx = -vx;
		}
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		if (ball.getY() + BALL_RADIUS * 2 >= getHeight()) {
			lives--;
			if (lives != 0) {
				beginningFromCentre();
				waitForClick();
			}

		}
	}

	//checking if game is over
	private boolean isGameNotOver() {
		if (lives == 0 || brickAmount == 0) {
			return false;
		} else {
			return true;
		}
	}

	//determining the colliding object of the ball
	private GObject getCollidingObject() {
		GObject collider = getElementAt(ball.getX(), ball.getY());
		if (collider == null) {
			collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		}
		if (collider == null) {
			collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}
		if (collider == null) {
			collider = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		}
		return collider;
	}

	//mouse moving listener, moving the paddle according to mouse location
	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= getWidth() - PADDLE_WIDTH / 2) {
			paddle.setLocation(getWidth() - PADDLE_WIDTH, paddle.getY());
		} else if (e.getX() <= PADDLE_WIDTH / 2) {
			paddle.setLocation(0, paddle.getY());
		} else {
			paddle.move(e.getX() - paddle.getX() - PADDLE_WIDTH / 2, 0);
		}
	}

}
