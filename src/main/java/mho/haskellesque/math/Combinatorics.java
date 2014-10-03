package mho.haskellesque.math;

import java.math.BigInteger;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

public class Combinatorics {
    public static BigInteger factorial(int n) {
        return productBigInteger(range(BigInteger.ONE, BigInteger.valueOf(n).add(BigInteger.ONE)));
    }

    public static BigInteger factorial(BigInteger n) {
        return productBigInteger(range(BigInteger.ONE, n.add(BigInteger.ONE)));
    }

    public static BigInteger subfactorial(int n) {
        if (n == 0) return BigInteger.ONE;
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        BigInteger c = b;
        for (int i = 1; i < n; i++) {
            c = BigInteger.valueOf(i).multiply(a.add(b));
            a = b;
            b = c;
        }
        return c;
    }

    public static BigInteger subfactorial(BigInteger n) {
        if (n.equals(BigInteger.ZERO)) return BigInteger.ONE;
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        BigInteger c = b;
        for (BigInteger i = BigInteger.ONE; lt(i, n); i = i.add(BigInteger.ONE)) {
            c = i.multiply(a.add(b));
            a = b;
            b = c;
        }
        return c;
    }
}
