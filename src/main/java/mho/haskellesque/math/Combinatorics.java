package mho.haskellesque.math;

import java.math.BigInteger;
import java.util.Iterator;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

public class Combinatorics {
    public static Iterable<Boolean> bits(final int n) {
        return () -> new Iterator<Boolean>() {
            private int remaining = n;

            @Override
            public boolean hasNext() {
                return remaining != 0;
            }

            @Override
            public Boolean next() {
                boolean bit = (remaining & 1) == 1;
                remaining >>= 1;
                return bit;
            }
        };
    }

    public static Iterable<Boolean> bits(final BigInteger n) {
        return () -> new Iterator<Boolean>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public Boolean next() {
                boolean bit = remaining.testBit(0);
                remaining = remaining.shiftRight(1);
                return bit;
            }
        };
    }

    public static Iterable<Boolean> bigEndianBits(final int n) {
        return reverse(bits(n));
    }

    public static Iterable<Boolean> bigEndianBits(final BigInteger n) {
        return reverse(bits(n));
    }

    public static BigInteger fromBits(Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (boolean bit : bits) {
            n = n.shiftLeft(1);
            if (bit) n = n.add(BigInteger.ONE);
        }
        return n;
    }

    public static BigInteger fromBigEndianBits(Iterable<Boolean> bits) {
        return fromBits(reverse(bits));
    }

    public static Iterable<Integer> digits(int base, final int n) {
        return () -> new Iterator<Integer>() {
            private int remaining = n;

            @Override
            public boolean hasNext() {
                return remaining != 0;
            }

            @Override
            public Integer next() {
                int digit = remaining % base;
                remaining /= base;
                return digit;
            }
        };
    }

    public static Iterable<BigInteger> digits(BigInteger base, final BigInteger n) {
        return () -> new Iterator<BigInteger>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public BigInteger next() {
                BigInteger digit = remaining.mod(base);
                remaining = remaining.divide(base);
                return digit;
            }
        };
    }

    public static Iterable<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    public static Iterable<BigInteger> bigEndianDigits(BigInteger base, final BigInteger n) {
        return reverse(digits(base, n));
    }

    public static BigInteger fromDigits(int base, Iterable<Integer> digits) {
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    public static BigInteger fromDigits(BigInteger base, Iterable<BigInteger> digits) {
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    public static BigInteger fromBigEndianDigits(int base, Iterable<Integer> digits) {
        return fromDigits(base, reverse(digits));
    }

    public static BigInteger fromBigEndianDigits(BigInteger base, Iterable<BigInteger> digits) {
        return fromDigits(base, (Iterable<BigInteger>) reverse(digits));
    }

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
