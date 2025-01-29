/**
 * A simple console-based Hangman game.
 *
 * This program randomly selects a word from a predefined array
 * and allows the user to guess the word one letter at a time.
 *
 * When the user finishes guessing the word (or chooses to stop),
 * the program shows the total number of misses and offers to start
 * a new game.
 *
 * @author Smit Patel
 * @version 2.0
 */

import java.util.Scanner;

public class Hangman {

    // Three word lists
    private static final String[] ANIMALS   = {"cat", "dog", "elephant", "tiger", "lion"};
    private static final String[] FOODS     = {"pizza", "burger", "pasta", "taco", "sushi"};
    private static final String[] COUNTRIES = {"france", "brazil", "japan", "egypt", "canada"};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Word list
        String[] words = {"write", "that", "program", "java", "computer", "game"};


        // Game loop
        String playAgain;
        do {
            // Pick a random word from the array
            String secretWord = words[(int)(Math.random() * words.length)];

            // Create a masked char array (all asterisks)
            char[] maskedWord = new char[secretWord.length()];
            for (int i = 0; i < maskedWord.length; i++) {
                maskedWord[i] = '*';
            }

            int missedCount = 0; // track wrong guesses
            // Keep guessing until user completes the word
            while (!isWordGuessed(maskedWord)) {
                System.out.print("(Guess) Enter a letter in word " + String.valueOf(maskedWord) + " > ");
                char guess = input.next().toLowerCase().charAt(0);

                // Check if the guess is correct
                if (!revealLetter(secretWord, maskedWord, guess)) {

                    if (!alreadyGuessed(maskedWord, guess)) {
                        missedCount++;
                        System.out.println(guess + " is not in the word");
                    } else {
                        System.out.println(guess + " is already in the word");
                    }
                }
            }

            // Word fully guessed
            System.out.println("The word is " + secretWord + ". You missed " + missedCount +
                    (missedCount <= 1 ? " time" : " times"));

            // Ask if the user wants to play again
            System.out.print("Do you want to guess another word? Enter y or n> ");
            playAgain = input.next();
        } while (playAgain.equalsIgnoreCase("y"));

        input.close();
    }

    /**
     * Reveal `guess` in `maskedWord` if it appears in `secretWord`.
     * @return true if at least one new letter was revealed, false if guess is not in the word.
     */
    public static boolean revealLetter(String secretWord, char[] maskedWord, char guess) {
        boolean foundNewLetter = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess && maskedWord[i] == '*') {
                maskedWord[i] = guess;
                foundNewLetter = true;
            }
        }
        return foundNewLetter;
    }

    /**
     * Check if the word is fully guessed (no asterisks left).
     */
    public static boolean isWordGuessed(char[] maskedWord) {
        for (char c : maskedWord) {
            if (c == '*') {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if `guess` is already revealed in `maskedWord`.
     */
    public static boolean alreadyGuessed(char[] maskedWord, char guess) {
        for (char c : maskedWord) {
            if (c == guess) {
                return true;
            }
        }
        return false;
    }
}
