import java.util.Scanner;

/**
 * Game where one user enters a secret word and another user guesses letters until the word is complete. Similar to 
 * hangman or "Wheel of Fortune"
 * 
 * @author Nicholas Bellman
 */
public class GuessingGame {

	/**
	 * Greets the user, plays the game, and then asks them if they want to play again. If they answer "yes", the game
	 * plays again. If they answer "no", the program thanks the user for playing, then terminates.
	 * 
	 * @param args Unknown.
	 */
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
		boolean canExitLoop = false;
		String prompt = "Play again?: ";
		
		System.out.println("Welcome to my guessing game!\n");
	
		play(input);
		
		while (canExitLoop == false) {
			
			String yesOrNo = getYesNoAnswer(prompt, input);
			
			if (yesOrNo.equals("yes")) {
				
				play(input);
				
			} else if (yesOrNo.equals("no")){
				
				input.close();
				canExitLoop = true;
				
			}
			
		}
		
		System.out.println("Thanks for playing!");
	}

	/**
	 * Prints a variable amount of blank lines to "clear" the console.
	 * 
	 * @param blankLines The number of blank lines desired.
	 */
	public static void clearConsole(int blankLines) {

		for (int counter = 0; counter < blankLines; counter++) {

			System.out.println();

		}

	}

	/**
	 * Reads in the first letter of a user's input to generate their guess.
	 * 
	 * @param input Scanner used to read user input.
	 * @return The first letter of a player's input.
	 */
	public static char getGuess(Scanner input) {

		System.out.print("Guess a letter: ");
		String guess = input.nextLine().toUpperCase();

		return guess.charAt(0);

	}

	/**
	 * Creates a string of underscores to represent an unguessed word.
	 * 
	 * @param length Length of the word to convert into underscroes.
	 * @return String of a number of underscores equal to the parameter 'length'.
	 */
	public static String createBlanksString(int length) {

		String blanksString = "";

		for (int counter = 0; counter < length; counter++) {

			blanksString = blanksString + "_";

		}

		return blanksString;

	}

	/**
	 * Replaces the blanks of a hidden secret word with any correct guesses.
	 * 
	 * @param secretWord The unhidden secret word. Used to check against the current guess.
	 * @param correctGuesses String containing correctly guessed characters of the secret word.
	 * @param currentGuess The user's current guess.
	 * @return The current hidden secret word with correct guesses, including the current guess, if valid.
	 */
	public static String replaceBlanks(String secretWord, String correctGuesses, char currentGuess) {

		for (int counter = 0; counter < secretWord.length(); counter++) {

			if (secretWord.charAt(counter) == currentGuess) {

				correctGuesses = correctGuesses.substring(0,counter) + currentGuess 
						+ correctGuesses.substring(counter + 1);

			}

		}

		return correctGuesses;

	}

	/**
	 * Checks to see if a word contains any non-alphabetic characters.
	 * 
	 * @param word The word to be checked.
	 * @return True if word contains a non-alphabetic character. False if word does not contain a non-alphabetic 
	 * character.
	 */
	public static boolean wordContainsNonLetter(String word) {

		boolean hasNonLetter = false;

		int index = 0;
		while(hasNonLetter == false && index < word.length()) {

			if (Character.isAlphabetic(word.charAt(index)) == false) {

				hasNonLetter = true;

			}

			index++;

		}

		return hasNonLetter;

	}

	/**
	 * Checks whether the input answer to a prompt is "yes" or "no". Re-prompts the user if neither is the case.
	 * 
	 * @param prompt The prompt the input is answering.
	 * @param input Scanner to read user input.
	 * @return String of value "yes" or "no".
	 */
	public static String getYesNoAnswer(String prompt, Scanner input) {

		String userResponse = "";
		boolean isYesOrNo = false;

		while (isYesOrNo == false) {

			System.out.print(prompt);
			String temp = input.nextLine();

			if (temp.equals("yes") || temp.equals("no")) {

				userResponse = temp;

				isYesOrNo = true;

			} else {

				System.out.println("Please enter 'yes' or 'no'.");

			}

		}

		return userResponse;

	}

	/**
	 * Gets a valid secret word to be guessed. If word is not valid, asks user to try again. 
	 * 
	 * @param input Scanner to read user input.
	 * @return String of the value of the chosen secret word.
	 */
	public static String getSecretWord(Scanner input) {

		String secretWord = "";

		System.out.print("Enter the secret word: ");
		secretWord = input.nextLine().toUpperCase();

		while (wordContainsNonLetter(secretWord) == true) {

			System.out.println("A secret word may only contain letters.");

			System.out.print("Enter the secret word: ");
			secretWord = input.nextLine().toUpperCase();

		}
		
		return secretWord;

	}

	/**
	 * Asks user for a secret word, prints 30 blank lines, then allows user to guess repeatedly until they are 
	 * successful. When successful, notifies the user how many attempts they made.
	 * 
	 * @param input Scanner used to read user input.
	 */
	public static void play(Scanner input) {

		String secretWord = getSecretWord(input);
		String correctGuesses = createBlanksString(secretWord.length());

		clearConsole(30);

		int numGuesses = 0;
		boolean hasLettersRemaining = true;
		
		while (hasLettersRemaining == true) {

			System.out.println("Your knowledge so far: " + correctGuesses);

			char currentGuess = getGuess(input);
			correctGuesses = replaceBlanks(secretWord, correctGuesses, currentGuess);

			numGuesses++;

			if (correctGuesses.equals(secretWord)) {

				hasLettersRemaining = false;

			} 

		}

		System.out.printf("Congratulations, you discovered the word \"%s\"!\n", secretWord);
		System.out.printf("It took you %d guesses.\n", numGuesses);

	}

}