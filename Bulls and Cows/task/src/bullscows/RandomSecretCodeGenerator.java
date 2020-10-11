package bullscows;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomSecretCodeGenerator implements SecretCodeGenerator {

    private int numDigits;
    private String secretCode;
    private Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public RandomSecretCodeGenerator() throws BullsAndCowsException {
        this.numDigits = scanner.nextInt();
        if(numDigits > 10)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", numDigits));
        generateSecretCode();
    }

    public RandomSecretCodeGenerator(int numDigits) throws BullsAndCowsException {
        this.numDigits = numDigits;
        if(numDigits > 10)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", numDigits));
        generateSecretCode();
    }


    private void generateSecretCode() {
        final List<Integer> list = IntStream.rangeClosed(0, 9)
                .mapToObj(num->num)
                .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        int randomNumber = random.nextInt(list.size()-1)+1;
        buildNumber(randomNumber, list, builder);
        IntStream.rangeClosed(1, numDigits-1)
                .forEach(num-> {
                    int listNumber = random.nextInt(list.size());
                    buildNumber(listNumber, list, builder);
                });
        this.secretCode = builder.toString();
    }

    @Override
    public String getSecretCode() {
        return secretCode;
    }

    private void buildNumber(int randomNumber, List<Integer> list, StringBuilder builder) {
        Integer value = list.get(randomNumber);
        builder.append(value);
        list.remove(value);
    }


    @Override
    public int getNumberOfDigits() {
        return numDigits;
    }
}
