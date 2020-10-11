package bullscows;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AlphaNumericSecretCodeGenerator implements SecretCodeGenerator {

    private int numDigits;
    private int rangeOfPossibleChars;
    private String secretCode;
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public AlphaNumericSecretCodeGenerator() throws BullsAndCowsException {
        this.numDigits = scanner.nextInt();
        if(numDigits > 36)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols(0-9 and a-z).", numDigits));
        if(numDigits == 0)
            throw new BullsAndCowsException("Error: Cannot produce secret code of zero length.");
        this.rangeOfPossibleChars = scanner.nextInt();
        if(rangeOfPossibleChars > 36)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols(0-9 and a-z).", rangeOfPossibleChars));
        if(numDigits > rangeOfPossibleChars)
            throw new BullsAndCowsException(String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", numDigits, rangeOfPossibleChars));
        generateSecretCode();
    }

    public AlphaNumericSecretCodeGenerator(int numDigits, int rangeOfPossibleChars) throws BullsAndCowsException {
        if(numDigits > 36)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols(0-9 and a-z).", numDigits));
        if(numDigits == 0)
            throw new BullsAndCowsException("Error: Cannot produce secret code of zero length.");

        this.numDigits = numDigits;
        if(rangeOfPossibleChars > 36)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols(0-9 and a-z).", rangeOfPossibleChars));
        this.rangeOfPossibleChars = rangeOfPossibleChars;
        if(numDigits > rangeOfPossibleChars)
            throw new BullsAndCowsException(String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", numDigits, rangeOfPossibleChars));
        generateSecretCode();
    }

    private void generateSecretCode() {
        List<Character> list = null;
        String rangeString = "";
        if(rangeOfPossibleChars < 10) {
            list = IntStream.rangeClosed('0', '0' + rangeOfPossibleChars - 1)
                    .mapToObj(num -> (Character) (char) num)
                    .collect(Collectors.toList());
            rangeString = list.size() == 0 ? "" : (list.size() == 1 ? "0" : "("+list.get(0)+"-"+list.get(list.size()-1)+")");
        }
        else if(rangeOfPossibleChars >= 10) {
            final List<Character> newList = IntStream.rangeClosed('0', '9')
                    .mapToObj(num -> (Character) (char) num)
                    .collect(Collectors.toList());
            rangeString = "(0-9";
            int alphabets = rangeOfPossibleChars - 10;
            if(alphabets == 0)
                rangeString += ")";
            else if(alphabets > 0) {
                char lastCharacter = 'z';
                IntStream.rangeClosed('a', 'a'+alphabets-1).forEach(num->newList.add((Character)(char)num));
                if(alphabets == 1) {
                    rangeString += " and a)";
                } else {
                    lastCharacter = newList.get(newList.size()-1);
                    rangeString += ", a-"+lastCharacter+")";
                }
            }
            list = newList;
        }
        rangeString += ".\n";
        final List<Character> finalList = list.stream().collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        IntStream.rangeClosed(0, numDigits-1)
                .forEach(num-> {
                    int listNumber = random.nextInt(finalList.size());
                    buildNumber(listNumber, finalList, builder);
                });
        this.secretCode = builder.toString();
        builder.delete(0, builder.length());
        //System.out.println(secretCode);
        builder.append("The secret is prepared: ");
        secretCode.chars().forEach(ch->builder.append("*"));
        builder.append(" "+rangeString);
        System.out.println(builder.toString());

    }

    private void buildNumber(int randomNumber, List<Character> list, StringBuilder builder) {
        Character value = list.get(randomNumber);
        builder.append(value);
        list.remove(value);
    }

    @Override
    public int getNumberOfDigits() {
        return numDigits;
    }

    @Override
    public String getSecretCode() {
        return secretCode;
    }


}
