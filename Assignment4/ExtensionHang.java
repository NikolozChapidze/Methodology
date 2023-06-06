import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JPanel;
import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.program.Program;
import acm.util.MediaTools;
import acm.util.RandomGenerator;
import javafx.scene.input.KeyCode;
import jdk.nashorn.internal.objects.Global;

public class ExtensionHang extends GraphicsProgram {

	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private int HEART_WIDTH = 35;

	private int HEART_HEIGHT = 32;

	private GImage heart = null;

	private GLabel points = new GLabel("SCORE: 0", 0, HEART_HEIGHT);

	ArrayList<GImage> hearts = new ArrayList<>();

	GLabel description = null;
	GLabel removeLetters = null;
	GLabel revealLetter = null;

	private GImage head = new GImage("Tavi.png");

	private GImage body = new GImage("Tani.png");

	private GImage lArm = new GImage("MarcxenaXeli.png");

	private GImage rArm = new GImage("MarjvenaXeli.png");

	private GImage lFoot = new GImage("MarcxenaTerfi.png");

	private GImage rFoot = new GImage("MarjvenaTerfi.png");

	private GImage lLeg = new GImage("MarcxenaFexi.png");

	private GImage rLeg = new GImage("MarjvenaFexi.png");

	private GImage background = new GImage("background.png");

	private AudioClip winClip = MediaTools.loadAudioClip("win-sound.wav");

	private AudioClip loseClip = MediaTools.loadAudioClip("you-lose-evil.wav");

	private AudioClip welcome = MediaTools.loadAudioClip("welcome.wav");

	private GRect buttonRevealLetter = new GRect(0, 0);

	private GRect buttonDescription = new GRect(0, 0);

	private GRect buttonRemoveLetters = new GRect(0, 0);

	// private GImage scaffold=new GImage("scaffold.png");

	private double letterOffset = 10;

	private double buttonOffset = 0;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private String word;

	private ArrayList<String> usedWords = new ArrayList<String>();

//	private String clientWord = new String();

	private GLabel userWord = new GLabel("");

	private String clientWord = "";

	private int turns = 8;

	private GLabel nameLabel = new GLabel("");

	private String userName = "";

	private boolean isNameInputed = false;

	private GLabel descriptionText = new GLabel("");

	private int revealHint = 3;

	private int removeHint = 4;

	private double userWordHeight = 0;

	private int score = 0;

	private double hintPoints = -10;

	private static final int leftfootOffset = 22;
	private static final int rightfootOffset = 26;

	private static final int ARM_OFFSET_FROM_HEAD = 11;

	private double offset = 60;

	private static final int ROPE_LENGTH = 18;

	private int correctGuess = 15;

//	private ArrayList<String> users = new ArrayList<String>();
//
//	private ArrayList<String> usersScores = new ArrayList<String>();

	private ArrayList<Users> users = new ArrayList<Users>();

	public void run() {
		inicialization();
		addKeyListeners();
		addMouseListeners();
		process();
	}

	private void process() {
		firstPage();
		while (!isNameInputed) {
		}
		secondPage();
		while (gameStatus()) {
		}
		printResults();
	}

	private void printResults() {
		pause(300);
		removeAll();
		add(background);
		GLabel result = new GLabel("YOU WON");
		result.setColor(Color.GREEN);
		if (turns == 0) {
			loseClip.play();
			result = new GLabel("YOU LOST");
			result.setColor(Color.RED);

		} else {
			winClip.play();
		}
		result.setFont(new Font("Serif", Font.PLAIN, 50));
		result.setLocation((getWidth() - result.getWidth()) / 2, 100 + (result.getAscent() - result.getDescent()) / 2);
		points.setFont(new Font("Serif", Font.PLAIN, 40));
		points.setLocation((getWidth() - points.getWidth()) / 2, result.getY() + result.getHeight());
		points.setColor(Color.MAGENTA);
		add(result);
		add(points);
		writingLeaderBoard();
		displayLeaderBoard();

	}

	private void displayLeaderBoard() {
		int max = 10;
		if (users.size() < 10) {
			max = users.size();
		}
		GLabel leaderboard = new GLabel("", getWidth() / 6, points.getY() + points.getHeight());
		for (int i = 0; i < max; i++) {
			// users.get(i) + " : " + usersScores.get(i)
			leaderboard = new GLabel(i + 1 + ") " + users.get(i).toString(), leaderboard.getX(),
					leaderboard.getY() + letterOffset);
			leaderboard.setFont(new Font("Serif", Font.PLAIN, 17));
			add(leaderboard);
		}
	}

	private void writingLeaderBoard() {
		int defscore = score;
		String defName = userName;
		System.out.println(users.size());
		int size = users.size();
		if (defscore <= users.get(users.size() - 1).getUsersScores()) {
			users.add(new Users(defName, defscore));
		} else if (defscore > users.get(0).getUsersScores()) {
			users.add(0, new Users(defName, defscore));
		} else if (defscore > users.get(users.size() - 1).getUsersScores()
				|| defscore <= users.get(0).getUsersScores()) {
			int i = 0;
			while (i < size - 1) {
				if (defscore <= users.get(i).getUsersScores() && defscore >= users.get(i + 1).getUsersScores()) {

					Users u = new Users(defName, defscore);
					users.add(i + 1, u);
					break;
				}
				i++;
			}
		}
		try {
			FileWriter myWriter = new FileWriter("Users.txt");
			BufferedWriter writer = new BufferedWriter(myWriter);
			for (int i = 0; i < users.size(); i++) {
				// TODO
				writer.write(users.get(i).getUsers() + " " + users.get(i).getUsersScores());
				if (i != users.size() - 1) {
					writer.newLine();
				}

			}

			writer.close();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean gameStatus() {
		if (clientWord.toString().equals(word) || turns == 0) {
			return false;
		}
		return true;
	}

	private void secondPage() {
		removeAll();
		add(background);
		drawEverything();
	}

	private void drawEverything() {
		add(background);
		// add(scaffold);
		drawScaffold();
		drawLetters();
		drawInformation();
		addButtons();
	}

	private void drawScaffold() {
		GLine scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, offset + SCAFFOLD_HEIGHT, getWidth() / 2 - BEAM_LENGTH,
				offset);
		GLine beam = new GLine(scaffold.getEndPoint().getX(), scaffold.getEndPoint().getY(),
				scaffold.getEndPoint().getX() + BEAM_LENGTH, scaffold.getEndPoint().getY());
		GLine rope = new GLine(beam.getEndPoint().getX(), beam.getEndPoint().getY(), beam.getEndPoint().getX(),
				beam.getEndPoint().getY() + ROPE_LENGTH);
		scaffold.setColor(java.awt.Color.ORANGE);
		beam.setColor(java.awt.Color.ORANGE);
		rope.setColor(java.awt.Color.ORANGE);
		add(scaffold);
		add(beam);
		add(rope);
	}

	private void addButtons() {
		add(buttonDescription);
		add(description);
		add(buttonRemoveLetters);
		add(removeLetters);
		add(buttonRevealLetter);
		add(revealLetter);

	}

	private void drawInformation() {
		for (int i = 0; i < hearts.size(); i++) {
			add(hearts.get(i));
		}
		add(points);
	}

	private void drawLetters() {
		GLabel alphabet = new GLabel("A", letterOffset, 3 * getHeight() / 4 + userWord.getHeight() / 2 + letterOffset);
		alphabet.setColor(new Color(235, 4, 80));
		char letter = 'A';
		for (int i = 0; i < 26; i++) {
			add(alphabet);
			letter++;
			alphabet = new GLabel("" + letter, alphabet.getX() + letterOffset, alphabet.getY());
			alphabet.setColor(new Color(235, 4, 80));
			if (i == 12) {
				alphabet.setLocation(letterOffset, alphabet.getY() + letterOffset);
			}
		}
		userWordHeight = userWord.getHeight();
		add(userWord, (getWidth() - userWord.getWidth()) / 2, 3 * getHeight() / 4);
	}

	private void firstPage() {
		add(background);
		welcome.play();
		GLabel welcome = new GLabel("WELCOME TO HANGMAN");
		welcome.setFont(new Font("Serif", Font.PLAIN, 35));
		welcome.setLocation((getWidth() - welcome.getWidth()) / 2, 120);
		welcome.setColor(new Color(235, 4, 80));
		GLabel enterName = new GLabel("please enter your name:");
		enterName.setFont(new Font("Serif", Font.PLAIN, 30));
		add(nameLabel);
		add(enterName, getWidth() / 2 - enterName.getWidth() / 2, getHeight() / 2);
		add(welcome);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (gameStatus()) {

			char key = e.getKeyChar();
			if (!isNameInputed) {
				nameModify(key);
			} else {
				checkInput(key);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (gameStatus()) {
			if (e.getX() >= buttonDescription.getX()
					&& e.getX() <= buttonDescription.getX() + buttonDescription.getWidth()
					&& e.getY() >= buttonDescription.getY()
					&& e.getY() <= buttonDescription.getY() + buttonDescription.getHeight()) {
				descriptionClicked();
			}
			if (e.getX() >= buttonRemoveLetters.getX()
					&& e.getX() <= buttonRemoveLetters.getX() + buttonRemoveLetters.getWidth()
					&& e.getY() >= buttonRemoveLetters.getY()
					&& e.getY() <= buttonRemoveLetters.getY() + buttonRemoveLetters.getHeight()) {
				if (removeHint != 0)
					removeLettersClicked();
			}
			if (e.getX() >= buttonRevealLetter.getX()
					&& e.getX() <= buttonRevealLetter.getX() + buttonRevealLetter.getWidth()
					&& e.getY() >= buttonRevealLetter.getY()
					&& e.getY() <= buttonRevealLetter.getY() + buttonRevealLetter.getHeight()) {
				if (revealHint != 0)
					revealLetterClicked();
			}
		}
	}

	private void revealLetterClicked() {
		int index = rgen.nextInt(word.length());
		if (clientWord.charAt(index) == '-') {
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == word.charAt(index) && clientWord.charAt(i) == '-') {
					updateCorrectInput(word.charAt(index), i);
				}
			}
			score += hintPoints;
			points.setLabel("SCORE: " + score);
			usedWords.add(word.charAt(index) + "");
			revealHint--;
			if (revealHint == 0) {
				remove(buttonRevealLetter);
				remove(revealLetter);
			}
		} else {
			revealLetterClicked();
		}

	}

	private void removeLettersClicked() {
		int index = rgen.nextInt(26);
		char letter = (char) ('A' + index);
		double x = 0;
		double y = 0;

		if (!usedWords.contains(letter + "") && !word.contains(letter + "")) {

			if (index < 13) {
				x = (index + 1) * letterOffset;
				y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset;
			} else {
				x = (index - 12) * letterOffset;
				y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset * 2;
			}

			if (getElementAt(x, y) != null && getElementAt(x, y) != background) {
				remove(getElementAt(x, y));
			}

			score += hintPoints;
			points.setLabel("SCORE: " + score);
			usedWords.add(letter + "");
			removeHint--;

			if (removeHint == 0) {
				remove(removeLetters);
				remove(buttonRemoveLetters);
			}
		} else {
			removeLettersClicked();
		}
		// System.out.println("rem");
	}

	private void descriptionClicked() {
		// TODO background
		if (getElementAt(description.getLocation()) != null && getElementAt(description.getLocation()) != background) {
			descriptionText.setFont(new Font("Serif", Font.PLAIN, 15));
			descriptionText.setLocation((getWidth() - descriptionText.getWidth()) / 2,
					userWord.getY() + userWord.getDescent() + 20);

			add(descriptionText);
			remove(description);
			remove(buttonDescription);
			score += hintPoints;
			points.setLabel("SCORE: " + score);
			// System.out.println("desc");
		}

	}

	private void checkInput(char input) {
		boolean contains = false;
		if (Character.isLetter(input)) {
			input = (input + "").toUpperCase().charAt(0);
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == input) {
					contains = true;
					if (clientWord.charAt(i) == '-') {
						updateCorrectInput(input, i);
						score += correctGuess;
						points.setLabel("SCORE: " + score);
					}
				}
			}
			if (!usedWords.contains(input + "")) {
				usedWords.add(input + "");
			}
			if (!contains) {
				updateIncorrectInput(input);
			}

		}

//		boolean contains = false;
//		// input = checkInputType(input);
//		for (int i = 0; i < word.length(); i++) {
//			if ((word.toLowerCase().charAt(i) == input.charAt(0) || word.toUpperCase().charAt(i) == input.charAt(0))
//					&& clientWord.charAt(i) == '-' && input.length() == 1) {
//				contains = true;
//				clientWord = clientWord.substring(0, i) + "" + word.charAt(i) + clientWord.substring(i + 1);
//				// clientWord.replace(i, i + 1, "" + word.charAt(i));
//			}
//		}
//		usedWords.add(input);
		// printInputResult(contains,input);
	}

	private void updateIncorrectInput(char input) {
		score -= correctGuess;
		points.setLabel("SCORE: " + score);
		turns--;
		remove(hearts.get(hearts.size() - 1));
		hearts.remove(hearts.get(hearts.size() - 1));
		int placeOfInput = input - 'A';
		double x = 0;
		double y = 0;
		if (placeOfInput < 13) {
			x = (placeOfInput + 1) * letterOffset;
			y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset;
		} else {
			x = (placeOfInput - 12) * letterOffset;
			y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset * 2;
		}
		if (getElementAt(x, y) != null && getElementAt(x, y) != background) {
			remove(getElementAt(x, y));
		}
		addBodyPart();

	}

	private void addBodyPart() {
		switch (turns) {
		case 7:
			drawHead();
			break;
		case 6:
			drawBody();
			break;
		case 5:
			drawLeftArm();
			break;
		case 4:
			drawRightArm();
			break;
		case 3:
			drawLeftLeg();
			break;
		case 2:
			drawRightLeg();
			break;
		case 1:
			drawLeftFoot();
			break;
		case 0:
			drawRightFoot();
			break;
		default:
			break;
		}

	}

	private void drawRightArm() {
		add(rArm, getWidth() / 2 + body.getWidth() / 2, body.getY() + ARM_OFFSET_FROM_HEAD);

	}

	private void drawLeftLeg() {
		add(lLeg, getWidth() / 2 - lLeg.getWidth(), body.getY() + body.getHeight());

	}

	private void drawRightLeg() {
		add(rLeg, getWidth() / 2, body.getY() + body.getHeight());
	}

	private void drawLeftFoot() {
		add(lFoot, getWidth() / 2 - leftfootOffset - lFoot.getWidth(), lLeg.getY() + lLeg.getHeight());

	}

	private void drawRightFoot() {
		add(rFoot, getWidth() / 2 + rightfootOffset, rLeg.getY() + rLeg.getHeight());
	}

	private void drawLeftArm() {
		add(lArm, getWidth() / 2 - body.getWidth() / 2 - lArm.getWidth(), body.getY() + ARM_OFFSET_FROM_HEAD);

	}

	private void drawBody() {
		body.setLocation(getWidth() / 2 - body.getWidth() / 2, head.getY() + head.getHeight());
		add(body);
	}

	private void drawHead() {
		head.setLocation(getWidth() / 2 - head.getWidth() / 2, offset + ROPE_LENGTH);
		add(head);

	}

	private void drawHang() {
	}

	private void updateCorrectInput(char input, int i) {
		int placeOfInput = input - 'A';
		double x = 0;
		double y = 0;
		clientWord = clientWord.substring(0, i) + "" + word.charAt(i) + clientWord.substring(i + 1);
		userWord.setLabel(clientWord);
		userWord.setLocation((getWidth() - userWord.getWidth()) / 2, 3 * getHeight() / 4);

		if (placeOfInput < 13) {
			x = (placeOfInput + 1) * letterOffset;
			y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset;
		} else {
			x = (placeOfInput - 12) * letterOffset;
			y = 3 * getHeight() / 4 + userWordHeight / 2 + letterOffset * 2;
		}
		// TODO bakcground
		if (getElementAt(x, y) != null && getElementAt(x, y) != background) {
			remove(getElementAt(x, y));
		}

	}

//	private String checkInputType(String input) {
//		while (usedWords.contains(input.toUpperCase()) || usedWords.contains(input.toLowerCase())
//				|| !Character.isLetter(input.charAt(0)) || input.length() != 1) {
//			if (usedWords.contains(input.toUpperCase()) || usedWords.contains(input.toLowerCase())) {
//				if (word.toLowerCase().contains("" + input.charAt(0))
//						|| word.toUpperCase().contains("" + input.charAt(0))) {
//					println("you have already used " + input);
//					input = new String(readLine("new guess: "));
//				} else {
//					turns--;
//					println("you have already tried  " + input + " that is incorrect");
//					printSituations();
//					input = new String(readLine("new guess: "));
//
//				}
//
//			}
//			if (!Character.isLetter(input.charAt(0)) || input.length() != 1) {
//				println("you should use just one letter");
//				input = new String(readLine("new guess: "));
//			}
//		}
//		return input;
//	}

	private void nameModify(char key) {
		if (key != 10) {
			if (key == 8) {
				if (userName.length() != 0)
					userName = userName.substring(0, userName.length() - 1);
			} else {
				if (Character.isLetter(key) || Character.isDigit(key))
					userName += key;
			}
			nameLabel.setLabel(userName);
			nameLabel.setLocation(getWidth() / 2 - nameLabel.getWidth() / 2, getHeight() / 2 + letterOffset);
		} else {
			isNameInputed = true;
		}
	}

	private void inicialization() {
		HangmanLexiconExtension lexicon = new HangmanLexiconExtension();
		setSize(500, 750);
		setFont(new Font("Serif", Font.PLAIN, HEART_HEIGHT));
		StringTokenizer line = new StringTokenizer(
				lexicon.getWord(rgen.nextInt(lexicon.getWordCount() - 1)).toUpperCase(), " ");

		word = line.nextToken();
		descriptionText.setLabel(line.nextToken());
		for (int i = 0; i < word.length(); i++) {
			clientWord = clientWord + "-";
		}

		readUsers();
		for (int i = 0; i < turns; i++) {
			heart = new GImage("heart.png", getWidth() - HEART_WIDTH - hearts.size() * HEART_WIDTH, 0);
			heart.setSize(HEART_WIDTH, HEART_HEIGHT);
			hearts.add(heart);
		}

		points.setFont(new Font("Serif", Font.PLAIN, HEART_HEIGHT));
		points.setColor(new Color(235, 4, 80));
		points.setLocation(0, HEART_HEIGHT - points.getDescent());

		letterOffset = getWidth() / 14;
		userWord = new GLabel(clientWord);
		userWord.setFont(new Font("Serif", Font.PLAIN, 45));
		nameLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		// displayWord(clientWord.toString());
		userWordHeight = userWord.getHeight();
		inicializeButtons();
		System.out.println(word);
	}

	private void readUsers() {
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			StringTokenizer l = null;
			String name = "";
			String sc = "";
			while (line != null) {
				l = new StringTokenizer(line);
				name = l.nextToken().toString();
				sc = l.nextToken().toString();
				Users user = new Users();
				user.setUsers(name);
				user.setUsersScores(Integer.parseInt(sc));
//				users.add(name);
//				usersScores.add(sc);
				line = br.readLine();
				users.add(user);
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inicializeButtons() {

		description = new GLabel("description");
		removeLetters = new GLabel("remove letters");
		revealLetter = new GLabel("show Letter");
		description.setColor(new Color(235, 4, 80));
		removeLetters.setColor(new Color(235, 4, 80));
		revealLetter.setColor(new Color(235, 4, 80));
		description.setFont(new Font("Serif", Font.PLAIN, 18));
		removeLetters.setFont(new Font("Serif", Font.PLAIN, 18));
		revealLetter.setFont(new Font("Serif", Font.PLAIN, 18));
		buttonOffset = (getWidth() - (description.getWidth() + removeLetters.getWidth() + revealLetter.getWidth())) / 7;

		description.setLocation(buttonOffset * 1.5, getHeight() - 22);
		removeLetters.setLocation(description.getX() + description.getWidth() + buttonOffset * 2, getHeight() - 22);
		revealLetter.setLocation(removeLetters.getX() + removeLetters.getWidth() + buttonOffset * 2, getHeight() - 22);

//		double x=description.getX()-buttonOffset/2;
//		double y=description.getY()-description.getAscent()-buttonOffset/2;
//		double xx=buttonOffset+description.getWidth();
//		double yy=buttonOffset+description.getHeight();
//		buttonDescription.setLocation( buttonOffset+description.getWidth(), buttonOffset+description.getHeight());
//		buttonDescription.setSize(description.getX()-buttonOffset/2, description.getY()-description.getAscent()-buttonOffset/2);
//		buttonRemoveLetters.setSize(removeLetters.getX()-buttonOffset/2, removeLetters.getY()-removeLetters.getAscent()-buttonOffset/2);
//		buttonRemoveLetters.setLocation(buttonOffset+removeLetters.getWidth(), buttonOffset+removeLetters.getHeight());
		buttonDescription.setBounds(description.getX() - buttonOffset / 2,
				description.getY() - description.getAscent() - 5, buttonOffset + description.getWidth(),
				10 + description.getHeight());
		buttonRevealLetter.setBounds(revealLetter.getX() - buttonOffset / 2,
				revealLetter.getY() - revealLetter.getAscent() - 5, buttonOffset + revealLetter.getWidth(),
				10 + revealLetter.getHeight());
		buttonRemoveLetters.setBounds(removeLetters.getX() - buttonOffset / 2,
				removeLetters.getY() - removeLetters.getAscent() - 5, buttonOffset + removeLetters.getWidth(),
				10 + removeLetters.getHeight());

	}
}
