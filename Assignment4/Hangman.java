
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;

public class Hangman extends ConsoleProgram {

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private String word;

	private ArrayList<String> usedWords = new ArrayList<String>();

//	private String clientWord = new String();

	private String clientWord = "";

	private int turns = 8;

	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		setSize(800, 700);
		HangmanLexicon lexicon = new HangmanLexicon();
		canvas.reset();
		inicialization(lexicon);

		process();
	}

	// whole process of game
	private void process() {
		println("WELCOME TO HAMGMAN!!!");
		while (isGameNotOver()) {
			printSituations();
			String input = readLine("Your guess: ");
			checkInput(input);
		}
		printResults();
	}

	// checking input if it is correct or not and if it is appropriate type of
	// variable
	private void checkInput(String input) {
		boolean contains = false;
		input = checkInputType(input);
		for (int i = 0; i < word.length(); i++) {
			if ((word.toLowerCase().charAt(i) == input.charAt(0) || word.toUpperCase().charAt(i) == input.charAt(0))
					&& clientWord.charAt(i) == '-' && input.length() == 1) {
				contains = true;
				clientWord = clientWord.substring(0, i) + "" + word.charAt(i) + clientWord.substring(i + 1);
				// clientWord.replace(i, i + 1, "" + word.charAt(i));
			}
		}
		usedWords.add(input);
		printInputResult(contains,input);
	}

	//printing if input was correct
	private void printInputResult(boolean contains, String input) {
		if (contains == false) {
			if (usedWords.contains(input.toUpperCase()) || usedWords.contains(input.toLowerCase())) {
				println("you have already tried  " + input + " that is incorrect");
			}else {
				println("there are no " + input + " in the word");
			}
			turns--;
			canvas.noteIncorrectGuess(input.charAt(0));
		} else {
			println("your guess is correct.");
			canvas.displayWord(clientWord.toString());
		}

	}

	//check if user inputed correct type of variable, if not asking again to input appropriate one until it will be one letter
	private String checkInputType(String input) {
		String unchangedInput="";
		while (true) {
			unchangedInput=input;
			if((input=checkForEnter(input))!=unchangedInput) continue;			
			if((input=checkUsed(input))!=unchangedInput)continue;			
			if((input=checkIfInputIsLetter(input))!=unchangedInput) continue;			
			if(!usedWords.contains(input.toUpperCase()) || !usedWords.contains(input.toLowerCase()) || Character.isLetter(input.charAt(0)) || input.length() == 1) {
				break;
			}
		}
		return input;
	}

	//check if input is enter
	private String checkForEnter(String input) {
		if(input.isEmpty()) {
			println("you haven't entered anything");
			input = new String(readLine("new guess: "));
		}
		return input;
	}

	//check if inout is letter
	private String checkIfInputIsLetter(String input) {
		if (!Character.isLetter(input.charAt(0)) || input.length() != 1) {
			println("you should use just one letter");
			input = new String(readLine("new guess: "));
		}
		return input;
	}

	//check if user entered already used letter
	private String checkUsed(String input) {
		if (usedWords.contains(input.toUpperCase()) || usedWords.contains(input.toLowerCase())) {
			if (word.toLowerCase().contains("" + input.charAt(0))
					|| word.toUpperCase().contains("" + input.charAt(0))) {
				println("you have already used " + input);
				input = new String(readLine("new guess: "));
			} 
//			else {
//				turns--;
//				println("you have already tried  " + input + " that is incorrect");
//				printSituations();
//				input = new String(readLine("new guess: "));
//
//			}

		}
		return input;
	}

	//printing situation of game (user word and amount of guesses left)
	private void printSituations() {
		println("The word looks like this " + clientWord.toString());
		println("You have " + turns + " guesses left.");
	}

	//printing game results
	private void printResults() {
		if (turns == 0) {
			println("you lost!!!");
		} else {
			println("you won.  the word was " + word);
		}
	}

	//checking if game is over or not
	private boolean isGameNotOver() {
		if (clientWord.toString().equals(word) || turns == 0) {
			return false;
		}
		return true;
	}

	//inicialization of user words and getting word that should be guessed 
	private void inicialization(HangmanLexicon lexicon) {
		word = lexicon.getWord(rgen.nextInt(lexicon.getWordCount() - 1));
		for (int i = 0; i < word.length(); i++) {
//			clientWord.append("-");
			clientWord = clientWord + "-";
		}
		
		canvas.displayWord(clientWord.toString());
		System.out.println(word);
	}

}
