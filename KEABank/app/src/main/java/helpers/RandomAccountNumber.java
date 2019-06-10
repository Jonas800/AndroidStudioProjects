package helpers;

import java.math.BigInteger;
import java.util.UUID;

public class RandomAccountNumber {

    public static String getRandomAccountNumber() {
        return String.format("%020d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(0, 16);
    }
}
