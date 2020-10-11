package bullscows;

import java.util.Scanner;

public class GameTurn {

    private int index;
    private String guess;
    private String gradeString;
    private final Scanner scanner = new Scanner(System.in);
    private int bulls;
    private int cows;

    public GameTurn(int index, String guess, String gradeString) {
        this.index = index;
        this.guess = guess;
        this.gradeString = gradeString;
    }

    public GameTurn(int index) {
        this.index = index;
        this.guess = scanner.next();
    }

    public String getGuess() {
        return guess;
    }

    public String getGradeString() {
        return gradeString;
    }

    public void setGradeString(String gradeString) {
        this.gradeString = gradeString;
    }

    public int getIndex() {
        return index;
    }

    public void setBullsAndCows(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }
}