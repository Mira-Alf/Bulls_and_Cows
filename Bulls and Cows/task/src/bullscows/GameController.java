package bullscows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class GameController {

    private int numDigits;
    private String secretCode;
    private boolean gameWon = false;
    private List<GameTurn> turns = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public GameController(int numDigits, String secretCode) {
        this.numDigits = numDigits;
        this.secretCode = secretCode;
    }
    public GameController(int numDigits, SecretCodeGenerator generator) {
        this.numDigits = numDigits;
        this.secretCode = generator.getSecretCode();
        System.out.println(secretCode);
    }

    public void addTurn(GameTurn... rounds) {
        Arrays.stream(rounds).forEach(t->turns.add(t));
    }

    public List<GameTurn> getTurns() {
        return turns;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void evaluateTurn(GameTurn turn) {
            updateBullsAndCows(turn);
    }

    private void updateBullsAndCows(GameTurn turn) {
        final String guess = turn.getGuess();
        int bulls = (int)IntStream.rangeClosed(0, secretCode.length()-1)
                .filter(n->secretCode.charAt(n) == guess.charAt(n)).count();
        int cows = (int)guess.chars()
                .filter(num->secretCode.contains((CharSequence)String.valueOf((char)num)))
                .count();
        cows = bulls > 0 ? cows-bulls : cows;
        turn.setBullsAndCows(bulls, cows);
        if(turn.getBulls() == numDigits)
            gameWon = true;
    }

    public boolean getGameStatus() {
        return gameWon;
    }
}