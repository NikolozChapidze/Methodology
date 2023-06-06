import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import acmx.export.javax.swing.ImageIcon;
import jdk.nashorn.internal.objects.Global;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;

import com.sun.org.apache.xml.internal.utils.URI;

public class Extension extends GraphicsProgram {

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

	private GImage background = new GImage("BreakoutBackground.png");

	private GImage heart = null;

	private int HEART_WIDTH = 40;

	private int HEART_HEIGHT = 32;

	private final int INFORMATION_OFFSET = HEART_HEIGHT;

	private GLabel points = new GLabel("SCORE: 0", 0, INFORMATION_OFFSET);

	private int score = 0;

	private int brick_score = 10;

	private int loose_score = 30;

	ArrayList<GImage> hearts = new ArrayList<>();

	ArrayList<Color> colors = new ArrayList<>();

	ArrayList<Color> ballColors = new ArrayList<>();

	ArrayList<GImage> powerUps = new ArrayList<>();

	ArrayList<activePowerUps> everyPower = new ArrayList<>();

	private AudioClip bounceClip = MediaTools.loadAudioClip("hit.wav");

	private AudioClip winClip = MediaTools.loadAudioClip("win-sound.wav");

	private AudioClip loseClip = MediaTools.loadAudioClip("you-lose-evil.wav");

	private AudioClip mineClip = MediaTools.loadAudioClip("bomb.wav");

	private AudioClip startClip = MediaTools.loadAudioClip("gong.wav");

	private double gameSpeed = 8;

	private double gameAccelerationRate = 1;

	private int brickAmount = NBRICK_ROWS * NBRICKS_PER_ROW;

	private int lives = NTURNS;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private double vx = 0;

	private double vy = +3.0;

	private GRect paddle = null;

	private GOval ball = null;

	private int hitCounter = 0;

	private boolean brickFreezer = false;

	private boolean miner = false;

	private GLabel gameName = new GLabel("BREAKOUT", 0, INFORMATION_OFFSET);

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		initialization();
		addMouseListeners();
		waitForClick();
		startClip.play();
		process();
		result();
	}

	private void result() {
		removeAll();
		add(new GImage("background1.jpg"));
		printResult();
	}

	// drawing the whole game panel
	private void initialization() {
		initializingArrays();
		drawWindow();
		drawBricks();
		drawPaddle();
		drawBall();
		drawInformation();

	}

	// initializing arraylists
	private void initializingArrays() {
		colors = new ArrayList<Color>() {
			{
				add(Color.CYAN);
				add(Color.GREEN);
				add(Color.YELLOW);
				add(Color.ORANGE);
				add(Color.RED);
			}
		};

		ballColors = new ArrayList<Color>() {
			{
				add(new Color(69, 204, 163));
				add(new Color(16, 126, 153));
				add(new Color(173, 255, 0));
				add(new Color(255, 190, 12));
				add(new Color(204, 86, 69));
				add(new Color(86, 48, 140));
			}
		};

		GImage brickFreeze = new GImage("BrickFreeze.png");
		brickFreeze.setSize(30, 20);
		GImage increasePaddle = new GImage("IncreasePaddle.png");
		increasePaddle.setSize(30, 20);
		GImage life = new GImage("Life.png");
		life.setSize(30, 20);
		GImage looseLife = new GImage("LooseLife.png");
		looseLife.setSize(30, 20);
		GImage mine = new GImage("Mine.png");
		mine.setSize(30, 20);
		GImage shock = new GImage("Shock.png");
		shock.setSize(30, 20);
		GImage acceleration = new GImage("Acceleration.png");
		acceleration.setSize(30, 20);

		powerUps = new ArrayList<GImage>() {
			{
				add(brickFreeze);
				add(increasePaddle);
				add(life);
				add(looseLife);
				add(mine);
				add(shock);
				add(acceleration);
			}
		};

	}

	// draw information(scors and lives) on the top of the panel
	private void drawInformation() {
		for (int i = 0; i < NTURNS; i++) {
			heart = new GImage("heart.png", getWidth() - HEART_WIDTH - hearts.size() * HEART_WIDTH, 0);
			heart.setSize(HEART_WIDTH, HEART_HEIGHT);
			hearts.add(heart);
		}
		for (int i = 0; i < hearts.size(); i++) {
			add(hearts.get(i));
		}
		points.setFont(new Font("Serif", Font.PLAIN, HEART_HEIGHT));
		Color pointColor = new Color(235, 4, 80);
		points.setColor(pointColor);
		points.setLocation(0, INFORMATION_OFFSET - points.getDescent());
		add(points);
	}

	// draw/set window size
	private void drawWindow() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT + INFORMATION_OFFSET);
		add(background);
	}

	// draw bricks
	private void drawBricks() {
		double startingY = BRICK_Y_OFFSET + INFORMATION_OFFSET;
		for (int i = 0; i < NBRICK_ROWS; i++) {
			drawBrickLine(startingY, colorDetermination(i));
			startingY += BRICK_HEIGHT + BRICK_SEP;
		}
	}

	// determine color for brick line
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

	// draw single line of bricks
	private void drawBrickLine(double startingY, Color color) {
		double startingX = (getWidth() - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			drawBrick(startingX, startingY, color);
			startingX += BRICK_WIDTH + BRICK_SEP;
		}
	}

	// draw one single brick
	private void drawBrick(double startingX, double startingY, Color color) {
		GRect brick = new GRect(startingX, startingY, BRICK_WIDTH, BRICK_HEIGHT);
		brick.setColor(color);
		brick.setFilled(true);
		add(brick);
	}

	// draw/add paddle
	private void drawPaddle() {
		paddle = new GRect((getWidth() - PADDLE_WIDTH) / 2, getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH,
				PADDLE_HEIGHT);
		Color paddleColor = new Color(0, 41, 250);
		paddle.setColor(paddleColor);
		paddle.setFilled(true);
		add(paddle);
	}

	// draw ball
	private void drawBall() {
		ball = new GOval(getWidth() / 2 - BALL_RADIUS, (getHeight() - INFORMATION_OFFSET) / 2 - BALL_RADIUS,
				BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setColor(ballColors.get(rgen.nextInt(ballColors.size())));
		ball.setFilled(true);
		add(ball);
	}

	// whole process of game, starting from centre , moving ball , checking
	// colliding object , printing result
	private void process() {
		beginningFromCenter();
		while (isGameNotOver()) {
			moveBall();
			checkCollider();
			movePowerUps();
			checkPowerUp();
		}
	}

	// moving power ups
	private void movePowerUps() {
		for (int i = 0; i < everyPower.size(); i++) {
			everyPower.get(i).getPowerUp().move(0, 2);
			if (everyPower.get(i).isActive() == false) {
				checkPowerColliding(i);
			}
		}
	}

	// checking if power up is active
	private void checkPowerUp() {
		for (int i = 0; i < everyPower.size(); i++) {
			if (everyPower.get(i).isActive() == true) {
				powerUpAction(i);
			}
		}
	}

	// checking if power up is active and acting according to it
	private void powerUpAction(int i) {
		activePowerUps powerUp = everyPower.get(i);
		if (powerUp.getPowerUp().getImage() == new GImage("Acceleration.png").getImage()) {
			acceleration(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("BrickFreeze.png").getImage()) {
			brickFreeze(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("IncreasePaddle.png").getImage()) {
			increasePaddle(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("Life.png").getImage() && lives < 5) {
			life(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("LooseLife.png").getImage()) {
			looseLife(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("Mine.png").getImage()) {
			mine(powerUp, i);
		} else if (powerUp.getPowerUp().getImage() == new GImage("Shock.png").getImage()) {
			shock(powerUp, i);
		}
	}

	// power up: activating acceleration / speeding up game speed
	private void acceleration(activePowerUps powerUp, int i) {
		if (gameSpeed > 4) {
			gameSpeed -= gameAccelerationRate;
			everyPower.remove(i);
		}
	}

	// power up:activating brick freezer/ increasing hitted brick color
	private void brickFreeze(activePowerUps powerUp, int i) {
		if (hitCounter - powerUp.getHitsOfCtreation() < 5) {
			brickFreezer = true;
		} else {
			brickFreezer = false;
			everyPower.remove(i);
		}

	}

	// power up: activating mine
	private void mine(activePowerUps powerUp, int i) {
		if (hitCounter - powerUp.getHitsOfCtreation() < 1) {
			miner = true;
		} else {
			miner = false;
			everyPower.remove(i);
		}
	}

	// power up: activating shock / that changes paddle length
	private void shock(activePowerUps powerUp, int i) {
		if (hitCounter - powerUp.getHitsOfCtreation() < 10) {
			if (paddle.getWidth() == PADDLE_WIDTH) {
				paddle.setSize(PADDLE_WIDTH / 2, PADDLE_HEIGHT);

			}
			for (int j = 0; j < everyPower.size(); j++) {
				if (everyPower.get(j).getPowerUp().getImage() == new GImage("IncreasePaddle.png").getImage()
						&& everyPower.get(j).isActive() == true && j > i) {
					everyPower.remove(i);
					paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);

				}
			}
		} else {
			paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
			everyPower.remove(i);

		}

	}

	// power up: activating loose life/ decreases amount of turns
	private void looseLife(activePowerUps powerUp, int i) {
		lives--;
		remove(hearts.get(hearts.size() - 1));
		hearts.remove(hearts.get(hearts.size() - 1));
		everyPower.remove(i);
	}

	// power up: activating life / increases amount of turns
	private void life(activePowerUps powerUp, int i) {
		heart = new GImage("heart.png", getWidth() - HEART_WIDTH - hearts.size() * HEART_WIDTH, 0);
		heart.setSize(HEART_WIDTH, HEART_HEIGHT);
		hearts.add(heart);
		lives++;
		add(hearts.get(hearts.size() - 1));
		everyPower.remove(i);
	}

	// power up: increase paddle/ increase paddle length
	private void increasePaddle(activePowerUps powerUp, int i) {
		if (hitCounter - powerUp.getHitsOfCtreation() < 10) {
			if (paddle.getWidth() == PADDLE_WIDTH) {
				paddle.setSize(PADDLE_WIDTH * 2, PADDLE_HEIGHT);

			}
			for (int j = 0; j < everyPower.size(); j++) {
				if (everyPower.get(j).getPowerUp().getImage() == new GImage("Shock.png").getImage()
						&& everyPower.get(j).isActive() == true && j > i) {
					everyPower.remove(i);
					paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
				}
			}
		} else {
			paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
			everyPower.remove(i);
		}

	}

	// checking if power up hits paddle and activating power up
	private void checkPowerColliding(int i) {
		GImage powerUp = everyPower.get(i).getPowerUp();
		if ((powerUp.getY() >= paddle.getY() && powerUp.getY() <= paddle.getY() + paddle.getHeight())
				|| (powerUp.getY() + powerUp.getHeight() >= paddle.getY()
						&& powerUp.getY() + powerUp.getHeight() <= paddle.getY() + paddle.getHeight())) {
			if ((powerUp.getX() >= paddle.getX() && powerUp.getX() <= paddle.getX() + paddle.getWidth())
					|| (powerUp.getX() + powerUp.getWidth() >= paddle.getX()
							&& powerUp.getX() + powerUp.getWidth() <= paddle.getX() + paddle.getWidth())) {
				everyPower.get(i).setActive(true);
				everyPower.get(i).setHitsOfCtreation(hitCounter);
				remove(powerUp);
			}
		}
		if (powerUp.getY() > getHeight()) {
			everyPower.remove(i);
		}
	}

	// printing result whether user won or not
	private void printResult() {
		GLabel result = new GLabel("YOU WON");
		result.setColor(Color.GREEN);
		if (lives == 0) {
			loseClip.play();
			result = new GLabel("YOU LOST");
			result.setColor(Color.RED);
		} else {
			winClip.play();
		}
		result.setFont(new Font("Serif", Font.PLAIN, 50));
		result.setLocation((getWidth() - result.getWidth()) / 2,
				getHeight() / 2 + (result.getAscent() - result.getDescent()) / 2);
		points.setFont(new Font("Serif", Font.PLAIN, 40));
		points.setLocation((getWidth() - points.getWidth()) / 2, result.getY() + result.getHeight());
		points.setColor(Color.MAGENTA);
		add(result);
		add(points);
	}

	// beginning from centre from the start and after loosing turn
	private void beginningFromCenter() {
		ball.setLocation(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}

	// checking colliding object and acting according to it
	private void checkCollider() {
		GObject collider = getCollidingObject(ball);
		if (collider == paddle) {
			ball.setColor(ballColors.get(rgen.nextInt(ballColors.size())));
			ballDirectionChange();
			vy = -vy;
			paddleCheck();
		} else if (collider != null && collider != points && ball.getY() > INFORMATION_OFFSET
				&& !GImage.class.isInstance(collider)) {
			hitBrick(collider);
		}
	}

	// ball direction change
	private void ballDirectionChange() {
		if (ball.getX() + BALL_RADIUS < paddle.getX() + (paddle.getWidth() / 3)) {
			vx = -Math.abs(vx);
		} else if (ball.getX() + BALL_RADIUS > paddle.getX() + ((2 * paddle.getWidth()) / 3)) {
			vx = Math.abs(vx);
		} else if (ball.getX() + BALL_RADIUS < paddle.getX() + ((2 * paddle.getWidth()) / 3)
				&& ball.getX() + BALL_RADIUS > paddle.getX() + (paddle.getWidth() / 3)) {
			vx = -vx;
		}
	}

	// hitting brick and deleting it, or changing its color, if specific power up is
	// active acting differently
	private void hitBrick(GObject collider) {
		hitCounter++;
		if (miner == true) {
			score += brick_score;
			mineClip.play();

			ArrayList<GObject> mineBricks = new ArrayList<GObject>() {
				{
					add(getElementAt(collider.getX(), collider.getY() - BRICK_SEP));
					add(getElementAt(collider.getX(), collider.getY() + BRICK_SEP + BRICK_HEIGHT));
					add(getElementAt(collider.getX() - BRICK_SEP, collider.getY()));
					add(getElementAt(collider.getX() - BRICK_SEP, collider.getY() - BRICK_SEP));
					add(getElementAt(collider.getX() - BRICK_SEP, collider.getY() + BRICK_SEP + BRICK_HEIGHT));
					add(getElementAt(collider.getX() + BRICK_SEP + BRICK_WIDTH, collider.getY()));
					add(getElementAt(collider.getX() + BRICK_SEP + BRICK_WIDTH, collider.getY() - BRICK_SEP));
					add(getElementAt(collider.getX() + BRICK_SEP + BRICK_WIDTH,
							collider.getY() + BRICK_SEP + BRICK_HEIGHT));

				}
			};
			remove(collider);
			for (int i = 0; i < mineBricks.size(); i++) {
				if (mineBricks.get(i) != null && mineBricks.get(i) != ball && mineBricks.get(i) != points
						&& ball.getY() > INFORMATION_OFFSET && !GImage.class.isInstance(mineBricks.get(i))) {
					remove(mineBricks.get(i));
					score += brick_score;
				}
			}

		} else if (brickFreezer == true) {
			score = score - brick_score;
			bounceClip.play();
			points.setLabel("SCORE: " + score);
			if (collider.getColor() != colors.get(colors.size() - 1)) {
				collider.setColor(colors.get(colors.indexOf(collider.getColor()) + 1));
			}

		} else {
			score += brick_score;
			points.setLabel("SCORE: " + score);
			bounceClip.play();

			if (collider.getColor() == colors.get(0)) {
				brickAmount--;
				remove(collider);
				powersAfterBrickHit(collider);

			} else {
				collider.setColor(colors.get(colors.indexOf(collider.getColor()) - 1));
			}
		}
		vy = -vy;
	}

	// after brick is deleted generating power up randomly
	private void powersAfterBrickHit(GObject collider) {
		if (rgen.nextInt(100) < 40) {
			int powerType = rgen.nextInt(powerUps.size());
			GImage image = new GImage(powerUps.get(powerType).getImage());
			image.setSize(powerUps.get(powerType).getSize());
			image.setLocation(collider.getX() + (collider.getWidth() - image.getWidth()) / 2,
					collider.getY() + collider.getHeight());
			activePowerUps power = new activePowerUps(image, 0, false);
			everyPower.add(power);
			add(power.getPowerUp());
		}
	}

	// checking ball to not get stuck in paddle
	private void paddleCheck() {
		if (paddle.getY() - vy <= ball.getY() + BALL_RADIUS * 2) {
			if ((paddle.getX() < ball.getX() + BALL_RADIUS * 2 || paddle.getX() + paddle.getWidth() > ball.getX())
					&& paddle.getY() < ball.getY() + BALL_RADIUS * 2) {
				ball.move(0, -((ball.getY() + BALL_RADIUS * 2) - paddle.getY()));
			}
			if (((paddle.getX() < ball.getX() + BALL_RADIUS * 2 && paddle.getX() > ball.getX())
					|| (paddle.getX() + paddle.getWidth() > ball.getX()
							&& paddle.getX() + paddle.getWidth() < ball.getX() + BALL_RADIUS * 2))
					&& paddle.getY() < ball.getY() + BALL_RADIUS) {
				vy = -vy;
				vx = -vx;
			}
		}
	}

	// moving ball animation
	private void moveBall() {
		checkSides();
		ball.move(vx, vy);
		pause(gameSpeed);
	}

	// checking sides and changing directions if ball hits the sides
	private void checkSides() {
		if (ball.getX() <= 0 || ball.getX() + BALL_RADIUS * 2 >= getWidth()) {
			vx = -vx;
		}
		if (ball.getY() <= INFORMATION_OFFSET) {
			vy = -vy;
		}
		if (ball.getY() + BALL_RADIUS * 2 >= getHeight()) {
			score -= loose_score;
			points.setLabel("SCORE: " + score);
			loosingTurn();
		}

	}

	// loosing turn and beginning from the centre
	private void loosingTurn() {
		remove(hearts.get(hearts.size() - 1));
		hearts.remove(hearts.size() - 1);
		lives--;
		if (lives != 0) {
			beginningFromCenter();
			waitForClick();
		}
	}

	// checking if game is over
	private boolean isGameNotOver() {
		if (lives == 0 || brickAmount == 0) {
			return false;
		} else {
			return true;
		}
	}

	// determining the colliding object of the specific object
	private GObject getCollidingObject(GObject object) {
		GObject collider = getElementAt(object.getX(), object.getY());
		if (collider == null || collider == background) {
			collider = getElementAt(object.getX() + object.getWidth(), object.getY());
		}
		if (collider == null || collider == background) {
			collider = getElementAt(object.getX() + object.getWidth(), object.getY() + object.getHeight());
		}
		if (collider == null || collider == background) {
			collider = getElementAt(object.getX(), object.getY() + object.getHeight());
		}
		if (collider == background) {
			return null;
		}
		return collider;
	}

	// mouse moving listener, moving the paddle according to mouse location
	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= getWidth() - paddle.getWidth() / 2) {
			paddle.setLocation(getWidth() - paddle.getWidth(), paddle.getY());
		} else if (e.getX() <= paddle.getWidth() / 2) {
			paddle.setLocation(0, paddle.getY());
		} else {
			paddle.move(e.getX() - paddle.getX() - paddle.getWidth() / 2, 0);
		}
	}

}
