package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.*;

public class BasicMath {
    public static int gcd(int x, int y) {
        return positiveGcd(x < 0 ? -x : x, y < 0 ? -y : y);
    }

    private static int positiveGcd(int x, int y) {
        return y == 0 ? x : positiveGcd(y, x % y);
    }

    public static BigInteger mod(BigInteger x, BigInteger y) {
        if (x.signum() < 0) {
            return y.subtract(x.negate()).mod(y);
        } else {
            return x.mod(y);
        }
    }

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

    public static Iterable<BigInteger> demux(int lines, BigInteger n) {
        return IterableUtils.map(BasicMath::fromBits, IterableUtils.demuxPadded(false, lines, bits(n)));
    }
}
