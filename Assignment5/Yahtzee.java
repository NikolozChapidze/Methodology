
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;
import java.util.HashMap;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		int turn = 0;
		int[] dice = new int[N_DICE];
		HashMap<String, Integer> allScores = inicialization();

		while (turn != 13) {
			for (int i = 0; i < nPlayers; i++) {
				display.printMessage(playerNames[i] + "'s turn! Click roll dice button to roll the dice.");
				display.waitForPlayerToClickRoll(i + 1);

				dice = new int[N_DICE];

				drawDice(dice);

				diceSelections(dice);

				int category = checkCategory(allScores, i);

				int score = determineScore(dice, category, allScores, i);

				display.updateScorecard(category, i + 1, score);
				display.updateScorecard(TOTAL, i + 1, allScores.get("Lower" + i) + allScores.get("Upper" + i));
			}
			turn++;
		}
		printResult(allScores);
	}

	// checking if category is available
	private int checkCategory(HashMap<String, Integer> allScores, int i) {
		int category = 0;
		while (true) {
			category = display.waitForPlayerToSelectCategory();
			// System.out.println(""+category+i+" "+allScores.get(""+category+i));
			if (allScores.get("" + category + i) == null)
				break;
		}

		return category;
	}

	// checking winner updaiting results and printing it
	private void printResult(HashMap<String, Integer> allScores) {
		int[] finalScore = new int[nPlayers];
		int max = 0;
		int winner = 0;
		for (int i = 0; i < nPlayers; i++) {
			finalScore[i] = allScores.get("Lower" + i) + allScores.get("Upper" + i);
			if (allScores.get("Upper" + i) > 63) {
				display.updateScorecard(UPPER_BONUS, i + 1, 35);
				finalScore[i] += 35;
				display.updateScorecard(TOTAL, i + 1, finalScore[i]);
			} else {
				display.updateScorecard(UPPER_BONUS, i + 1, 0);
			}
			if (finalScore[i] >= max) {
				max = finalScore[i];
				winner = i;
			}
			display.updateScorecard(UPPER_SCORE, i + 1, allScores.get("Upper" + i));
			display.updateScorecard(LOWER_SCORE, i + 1, allScores.get("Lower" + i));
		}
		display.printMessage(
				"Congratulations, " + playerNames[winner] + ", you're winner with a total score of " + max + " !");
	}

	// initializing hashMap of allScors/every score od each player
	private HashMap<String, Integer> inicialization() {
		HashMap<String, Integer> allScores = new HashMap<>();
		for (int i = 0; i < nPlayers; i++) {
			allScores.put("Upper" + i, 0);
			allScores.put("Lower" + i, 0);
			allScores.put("" + ONES + i, null);
			allScores.put("" + TWOS + i, null);
			allScores.put("" + THREES + i, null);
			allScores.put("" + FOURS + i, null);
			allScores.put("" + FIVES + i, null);
			allScores.put("" + SIXES + i, null);
			allScores.put("" + THREE_OF_A_KIND + i, null);
			allScores.put("" + FOUR_OF_A_KIND + i, null);
			allScores.put("" + FULL_HOUSE + i, null);
			allScores.put("" + SMALL_STRAIGHT + i, null);
			allScores.put("" + LARGE_STRAIGHT + i, null);
			allScores.put("" + YAHTZEE + i, null);
			allScores.put("" + CHANCE + i, null);
		}
		return allScores;
	}

	// checking if any dice is selected and acting according to it(giving new value
	// and showing updates)
	private void diceSelections(int[] dice) {
		boolean isSelected = false;
		display.waitForPlayerToSelectDice();
		isSelected = checkDice(dice);
		if (isSelected) {
			display.displayDice(dice);
			display.waitForPlayerToSelectDice();
			isSelected = checkDice(dice);
			if (isSelected) {
				display.displayDice(dice);
			}

		}

	}

	// determining the score according to dices and chosen category
	private int determineScore(int[] dice, int category, HashMap<String, Integer> allSscores, int i) {
		int score = 0;
		Arrays.sort(dice);
		if (category >= ONES && category <= SIXES) {
			score = checkOneSix(dice, category);
			allSscores.put("Upper" + i, allSscores.get("Upper" + i) + score);
			allSscores.put("" + category + i, score);
//			System.out.println(allSscores.get("Upper" + i) + score);
		} else {
			if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND || category == YAHTZEE) {
				score = checkKinds(dice, category);
				allSscores.put("" + category + i, score);
			}
			if (category == FULL_HOUSE) {
				score = checkFullHouse(dice, category);
				allSscores.put("" + category + i, score);
			}
			if (category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) {
				score = checkStraights(dice, category);
				allSscores.put("" + category + i, score);
			}
			if (category == CHANCE) {
				score = chance(dice);
				allSscores.put("" + category + i, score);
			}
			allSscores.put("Lower" + i, allSscores.get("Lower" + i) + score);
		}
		return score;
	}

	// calculating score when chance is selected
	private int chance(int[] dice) {
		int score = 0;
		for (int i = 0; i < dice.length; i++) {
			score += dice[i];
		}
		return score;
	}

	// calculating score when any STRAIGHTS is selected
	private int checkStraights(int[] dice, int category) {
		if (dice[1] + 1 == dice[2] && dice[2] + 1 == dice[3]) {
			if (category == SMALL_STRAIGHT && (dice[0] + 1 == dice[1] || dice[3] + 1 == dice[4])) {
				return 30;
			}
			if (category == LARGE_STRAIGHT && dice[0] + 1 == dice[1] && dice[3] + 1 == dice[4]) {
				return 40;
			}
		}
		return 0;
	}

	// calculating score when fullHouse is selected
	private int checkFullHouse(int[] dice, int category) {
		if (dice[0] == dice[1] && dice[3] == dice[4] && (dice[2] == dice[1] || dice[2] == dice[3])) {
			return 25;
		}
		return 0;
	}

	// calculating score when KINDS and YAHTZEE is selected
	private int checkKinds(int[] dice, int category) {
		int score = 0;
		int counter = 0;
		int middleDice = dice[2];
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == middleDice) {
				counter++;
			}
			score += dice[i];
		}
		if (category == THREE_OF_A_KIND && counter >= 3) {
			return score;
		}
		if (category == FOUR_OF_A_KIND && counter >= 4) {
			return score;
		}
		if (category == YAHTZEE && counter == 5) {
			return 50;
		}
		return 0;
	}

	// calculating score when 1-6 is selected
	private int checkOneSix(int[] dice, int category) {
		int score = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == category) {
				score += category;
			}
		}
		return score;
	}

	// checking if any dice is selected and changing its value
	private boolean checkDice(int[] dice) {
		boolean isSelected = false;
		for (int i = 0; i < dice.length; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
				isSelected = true;
			}
		}
		return isSelected;

	}

	// updating dice values
	private void drawDice(int[] dice) {
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
