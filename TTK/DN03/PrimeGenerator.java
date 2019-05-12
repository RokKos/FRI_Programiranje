import java.util.*;
import java.math.BigInteger;

public class PrimeGenerator {

    public static void main(String[] args) {
        BigInteger prime_Q = BigInteger.probablePrime(160, new Random());
        System.out.println(prime_Q.toString());

        final BigInteger kOne = new BigInteger("1");
        final BigInteger kZero = new BigInteger("0");

        while (true) {
            BigInteger prime_P = BigInteger.probablePrime(1024, new Random());
            prime_P.subtract(kOne);
            BigInteger mod = prime_P.mod(prime_Q);
            if (mod.compareTo(kZero) == 0) {
                System.out.println(prime_P.toString());
            }
        }
    }

}