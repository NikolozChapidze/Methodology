
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {

	private ArrayList<String> lexicon = new ArrayList<String>();

	public HangmanLexicon() {
		try {
			 FileReader fr=new FileReader("HangmanLexicon.txt");
			//FileReader fr=new FileReader("Hangman.txt");
			BufferedReader br = new BufferedReader(fr);
			String word = br.readLine();
			while (word != null) {
				lexicon.add(word);
				word = br.readLine();
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lexicon.size();
		// return 10;
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon.get(index);
//		switch (index) {
//			case 0: return "BUOY";
//			case 1: return "COMPUTER";
//			case 2: return "CONNOISSEUR";
//			case 3: return "DEHYDRATE";
//			case 4: return "FUZZY";
//			case 5: return "HUBBUB";
//			case 6: return "KEYHOLE";
//			case 7: return "QUAGMIRE";
//			case 8: return "SLITHER";
//			case 9: return "ZIRCON";
//			default: throw new ErrorException("getWord: Illegal index");
//		}
	};
}
