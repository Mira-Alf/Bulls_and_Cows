package bullscows;

import java.security.cert.CollectionCertStoreParameters;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PseudoRandomSecretCodeGenerator implements SecretCodeGenerator {
    private int numDigits;
    private long pseudoRandomNumber = System.nanoTime();
    private String secretCode;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public PseudoRandomSecretCodeGenerator() throws BullsAndCowsException {
        this.numDigits = scanner.nextInt();
        if(numDigits > 10)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", numDigits));
        generateSecretCode();
    }

    public PseudoRandomSecretCodeGenerator(int numDigits) throws BullsAndCowsException {
        this.numDigits = numDigits;
        if(numDigits > 10)
            throw new BullsAndCowsException(String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", numDigits));
        generateSecretCode();
    }

    private void generateSecretCode() {
        boolean proceed = true;
        StringBuilder builder = new StringBuilder();

        while(proceed == true) {
            List<Character> charSet = String.valueOf(pseudoRandomNumber).chars()
                    .mapToObj(num -> (char)num).distinct().collect(Collectors.toList());
            if(charSet.size() >= numDigits) {
                proceed = false;
                int count = 0;
                while(count < numDigits) {
                    int randomNumber = random.nextInt(charSet.size()-1);
                    char value = charSet.get(randomNumber);
                    if(count == 0 && value == '0')
                        continue;
                    charSet.remove((Character) value);
                    builder.append(value);
                    count++;
                }
            }
            pseudoRandomNumber = System.nanoTime();
        }
        this.secretCode = builder.toString();
    }

    @Override
    public String getSecretCode() {
        return secretCode;
    }

    @Override
    public int getNumberOfDigits() {
        return numDigits;
    }
}