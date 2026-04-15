import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    // -------Game configuration
    static final int MIN_NUMBER = 1;
    static final int MAX_NUMBER = 100;
    static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        int roundNumber = 0;

        printBanner();
        // -------Main game loop
        while (true) {
            roundNumber++;
            int secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int attemptsLeft = MAX_ATTEMPTS;
            boolean won = false;

            System.out.println("\n╔══════════════════════════════════╗");
            System.out.printf("║      -----> ROUND %-2d<-----       ║%n", roundNumber);
            System.out.println("╚══════════════════════════════════╝");
            System.out.printf("I'm thinking of a number between %d and %d.%n", MIN_NUMBER, MAX_NUMBER);
            System.out.printf("You have %d attempts. Good luck!%n%n", MAX_ATTEMPTS);

            // -------Guess loop
            while (attemptsLeft > 0) {
                System.out.printf("Attempts left: %d  | Enter your guess: ", attemptsLeft);

                // Input validation
                if (!scanner.hasNextInt()) {
                    System.out.println(" Please enter a valid integer!");
                    scanner.next(); // discard bad token
                    continue;
                }
                int guess = scanner.nextInt();

                if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                    System.out.printf("Please guess between %d and %d.%n",
                            MIN_NUMBER, MAX_NUMBER);
                    continue;
                }

                attemptsLeft--;
                if (guess == secretNumber) {
                    won = true;
                    int roundScore = attemptsLeft * 10 + 10; // bonus for remaining attempts
                    totalScore += roundScore;
                    System.out.println();
                    System.out.println("  CORRECT! You nailed it!");
                    System.out.printf("  Round score  : +%d points%n", roundScore);
                    System.out.printf("  Total score  : %d points%n", totalScore);
                    break;

                } else if (guess < secretNumber) {
                    String hint = (secretNumber - guess > 20) ? "Much higher! " : "A bit higher!";
                    System.out.printf("Too low!  %s%n%n", hint);
                } else {
                    String hint = (guess - secretNumber > 20) ? "Much lower!  " : "A bit lower!";
                    System.out.printf("Too high! %s%n%n", hint);
                }
            }

            // -------Round result
            if (!won) {
                System.out.println();
                System.out.println(" Out of attempts! Better luck next time.");
                System.out.printf("  The secret number was: %d%n", secretNumber);
                System.out.printf("  Total score so far   : %d points%n", totalScore);
            }

            // -------Play again prompt
            System.out.print("\nPlay another round? (yes / no): ");
            String choice = scanner.next().trim().toLowerCase();

            if (!choice.equals("yes") && !choice.equals("y")) {
                break;
            }
        }

        // -------Final summary
        System.out.println();
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    xxx---> GAME  OVER <---xxx    ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.printf("  Rounds played : %d%n", roundNumber);
        System.out.printf("  Final score   : %d points%n", totalScore);
        printRank(totalScore, roundNumber);
        System.out.println("\nThanks for playing!  See you next time.");

        scanner.close();
    }

    // -------Helper: rank the player
    static void printRank(int score, int rounds) {
        if (rounds == 0)
            return;
        double avg = (double) score / rounds;
        String rank;
        if (avg >= 60)
            rank = " Grand Master";
        else if (avg >= 40)
            rank = " Expert";
        else if (avg >= 20)
            rank = " Intermediate";
        else
            rank = " Beginner";
        System.out.printf("  Your rank     : %s%n", rank);
    }

    // -------Helper: print opening banner
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   ~----> NUMBER  GUESSING  GAME <----~   ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Range    :%3d to %3d                    ║%n",
                MIN_NUMBER, MAX_NUMBER);
        System.out.printf("║  Attempts : %d per round                 ║%n", MAX_ATTEMPTS);
        System.out.println("║  Scoring  : 10 pts + 10 per spare try    ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
