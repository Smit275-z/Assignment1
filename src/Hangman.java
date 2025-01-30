/**
 * Hangman game that reads word lists from files (one file per topic).
 *
 * Assignment 3: Demonstrates reading from text files instead of
 * hard-coded arrays.
 *
 * @author Smit Patel
 * Date: 30/01/2025
 * @version 3.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            // Display menu
            System.out.println("\n=== HANGMAN MENU ===");
            System.out.println("1) Animals");
            System.out.println("2) Foods");
            System.out.println("3) Countries");
            System.out.println("4) Exit");
            System.out.print("Choose a topic (1-4): ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- HANGMAN: ANIMALS ---");
                    playHangmanFromFile("src/animals.txt", input);
                    break;
                case 2:
                    System.out.println("\n--- HANGMAN: FOODS ---");
                    playHangmanFromFile("src/foods.txt", input);
                    break;
                case 3:
                    System.out.println("\n--- HANGMAN: COUNTRIES ---");
                    playHangmanFromFile("src/countries.txt", input);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        input.close();
    }

    /**
     * Reads words from the specified file and plays Hangman with them.
     */
    public static void playHangmanFromFile(String fileName, Scanner input) {
        ArrayList<String> wordList = readWordsFromFile(fileName);
        if (wordList.isEmpty()) {
            System.out.println("No words found in " + fileName + ". Exiting this topic...");
            return;
        }

        String secretWord = wordList.get((int)(Math.random() * wordList.size()));
        char[] maskedWord = new char[secretWord.length()];
        for (int i = 0; i < maskedWord.length; i++) {
            maskedWord[i] = '*';
        }

        int missedCount = 0;
        while (!isWordGuessed(maskedWord)) {
            System.out.print("(Guess) Enter a letter in word "
                    + String.valueOf(maskedWord) + " > ");
            char guess = input.next().toLowerCase().charAt(0);

            boolean result = revealLetter(secretWord, maskedWord, guess);
            if (!result) {
                // If the letter isn't revealed, check if it's not in the word
                if (!alreadyGuessed(maskedWord, guess)) {
                    missedCount++;
                    System.out.println(guess + " is not in the word.");
                } else {
                    System.out.println(guess + " is already in the word.");
                }
            }
        }
        System.out.println("The word is " + secretWord
                + ". You missed " + missedCount
                + (missedCount == 1 ? " time." : " times."));
    }

    /**
     * Reads all words (lines) from a file into an ArrayList.
     *
     * @param fileName the file path (e.g. "animals.txt")
     * @return an ArrayList of words
     */
    public static ArrayList<String> readWordsFromFile(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                // If each word is on a separate line:
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    words.add(line.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find " + fileName);
        }
        return words;
    }

    /**
     * Reveal `guess` in `maskedWord` if it appears in `secretWord`.
     * @return true if at least one new letter was revealed
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
     * Check if the word is fully guessed (no '*' left).
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
