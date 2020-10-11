package bullscows;

import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        stageSeven();
    }

    public static void stageOne() {
        GameController controller = new GameController(4, "9305");
        System.out.println("The secret code is prepared: ****.\n");
        controller.addTurn(new GameTurn[] {new GameTurn(1, "1234", "1 cow"),
                new GameTurn(2, "5678", "1 cow"),
                new GameTurn(3, "9012", "1 bull and 1 cow"),
                new GameTurn(4, "9087", "1 bull and 1 cow"),
                new GameTurn(5, "1087", "1 cow"),
                new GameTurn(6, "9205", "3 bulls"),
                new GameTurn(7, "9305", "4 bulls")
        });
        controller.getTurns().forEach(turn->{
            System.out.printf("Turn %d. Answer:%n", turn.getIndex());
            System.out.println(turn.getGuess());
            System.out.printf("Grade: %s%n", turn.getGradeString());
            System.out.println();
        });
        System.out.printf("Congrats! The secret code is %s.%n", controller.getSecretCode());
    }

    public static void stageTwo() {
        GameController controller = new GameController(4, "9305");
        GameTurn turn = new GameTurn(0);
        controller.addTurn(turn);
        controller.evaluateTurn(turn);
        if(turn.getBulls() == 0 && turn.getCows() == 0)
            turn.setGradeString("None");
        else {
            if(turn.getBulls() == 0)
                turn.setGradeString(turn.getCows()+" cow(s)");
            else if(turn.getCows() == 0)
                turn.setGradeString(turn.getBulls()+" bull(s)");
            else
                turn.setGradeString(turn.getBulls()+" bull(s) and "+turn.getCows()+" cow(s)");
        }
        System.out.printf("Grade: %s. The secret code is %s.%n", turn.getGradeString(),
                controller.getSecretCode());
    }

    public static void stageThree() {
        try {
            SecretCodeGenerator generator = new PseudoRandomSecretCodeGenerator();
            System.out.printf("The random secret number is %s.%n", generator.getSecretCode());
        } catch(BullsAndCowsException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void stageFour() {
        try {
            System.out.println("Please, enter the secret code's length:");
            SecretCodeGenerator generator = new PseudoRandomSecretCodeGenerator();
            stageFourAndFiveCommon(generator);
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void stageFive() {
        try {
            System.out.println("Please, enter the secret code's length:");
            SecretCodeGenerator generator = new RandomSecretCodeGenerator();
            stageFourAndFiveCommon(generator);
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void stageFourAndFiveCommon(SecretCodeGenerator generator) {

            GameController controller = new GameController(generator.getNumberOfDigits(), generator.getSecretCode());
            System.out.println("Okay, let's start a game!");
            int turnIndex = 0;
            while(!controller.getGameStatus()) {
                System.out.printf("Turn %d:%n", ++turnIndex);
                GameTurn turn = new GameTurn(turnIndex);
                controller.addTurn(turn);
                controller.evaluateTurn(turn);
                if(turn.getBulls() == 0 && turn.getCows() == 0)
                    turn.setGradeString("None");
                else {
                    if(turn.getBulls() == 0)
                        turn.setGradeString(turn.getCows() == 1 ? turn.getCows()+" cow" : turn.getCows() + " cows");
                    else if(turn.getCows() == 0)
                        turn.setGradeString(turn.getBulls() == 1 ? turn.getBulls()+" bull" : turn.getBulls() + " bulls");
                    else {
                        turn.setGradeString((turn.getBulls() == 1 ? turn.getBulls()+" bull" : turn.getBulls() + " bulls and ") +
                                (turn.getCows() == 1 ? turn.getCows()+" cow" : turn.getCows() + " cows"));
                    }
                }
                System.out.printf("Grade: %s. %n", turn.getGradeString());
            }
            if(controller.getGameStatus())
                System.out.println("Congratulations! You guessed the secret code.");

    }

    public static void stageSix() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int numDigits = scanner.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int rangeOfPossibleChars = scanner.nextInt();
        try {
            SecretCodeGenerator generator = new AlphaNumericSecretCodeGenerator(numDigits, rangeOfPossibleChars);
            stageFourAndFiveCommon(generator);
        } catch (BullsAndCowsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void stageSeven() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Input the length of the secret code:");
            int numDigits = handleNumberFormatException(scanner.next());
            System.out.println("Input the number of possible symbols in the code:");
            int rangeOfPossibleChars = handleNumberFormatException(scanner.next());
            SecretCodeGenerator generator = new AlphaNumericSecretCodeGenerator(numDigits, rangeOfPossibleChars);
            stageFourAndFiveCommon(generator);
        } catch(BullsAndCowsException e) {
            System.out.println(e.getMessage());
        }

    }

    private static int handleNumberFormatException(String intValue) throws BullsAndCowsException {
        try {
            return Integer.parseInt(intValue);
        } catch (NumberFormatException ne) {
            throw new BullsAndCowsException(String.format("Error: \"%s\" isn't a valid number.", intValue));
        }
    }
}