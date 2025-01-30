import java.util.Scanner;

/**
 * Demonstrates a menu-driven Hangman game with three different word lists.
 *
 * @author Smit Patel
 * Date: 1/29/2025
 * @version 3.0
 */
public class Hangman {
    // Three word lists (3 different "versions" or "topics")
    private static final String[] ANIMALS   = {"cat", "dog", "elephant", "tiger", "lion"};
    private static final String[] FOODS     = {"pizza", "burger", "pasta", "taco", "sushi"};
    private static final String[] COUNTRIES = {"france", "brazil", "japan", "egypt", "canada"};

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
            input.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- HANGMAN: ANIMALS ---");
                    playHangmanFromFile("animals.txt", input);
                    break;
                case 2:
                    System.out.println("\n--- HANGMAN: FOODS ---");
                    playHangmanFromFile("foods.txt", input);
                    break;
                case 3:
                    System.out.println("\n--- HANGMAN: COUNTRIES ---");
                    playHangmanFromFile("countries.txt", input);
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
     * Plays one round of Hangman using the given array of words.
     */
    public static void playHangman(String[] words, Scanner input) {
        // Randomly pick a word from the chosen topic array
        String secretWord = words[(int) (Math.random() * words.length)];
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
                // If the letter is not in the word or already guessed,
                // check if it's truly not in the word or already revealed
                if (!alreadyGuessed(maskedWord, guess)) {
                    missedCount++;
                    System.out.println(guess + " is not in the word.");
                } else {
                    System.out.println(guess + " is already in the word.");
                }
            }
        }

        // Word is fully guessed
        System.out.println("The word is " + secretWord
                + ". You missed " + missedCount
                + (missedCount == 1 ? " time." : " times."));
    }

    /**
     * Reveal `guess` in `maskedWord` if it appears in `secretWord`.
     * @return true if we revealed at least one new character, false otherwise
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
     * Check if the word has been fully guessed (no '*' left).
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
     * Determine if the `guess` character is already revealed in `maskedWord`.
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
