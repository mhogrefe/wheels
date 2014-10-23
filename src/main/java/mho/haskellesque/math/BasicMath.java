package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.structures.Pair;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.*;

public final class BasicMath {
    /**
     * Disallow instantiation
     */
    private BasicMath() {}

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

    public static Iterable<Boolean> bitsPadded(int length, int n) {
        return pad(false, length, bits(n));
    }

    public static Iterable<Boolean> bitsPadded(BigInteger length, BigInteger n) {
        return pad(false, length, bits(n));
    }

    public static Iterable<Boolean> bigEndianBits(final int n) {
        return reverse(bits(n));
    }

    public static Iterable<Boolean> bigEndianBits(final BigInteger n) {
        return reverse(bits(n));
    }

    public static Iterable<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    public static Iterable<Boolean> bigEndianBitsPadded(BigInteger length, BigInteger n) {
        return reverse(bitsPadded(length, n));
    }

    public static BigInteger fromBigEndianBits(Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (boolean bit : bits) {
            n = n.shiftLeft(1);
            if (bit) n = n.add(BigInteger.ONE);
        }
        return n;
    }

    public static BigInteger fromBits(Iterable<Boolean> bits) {
        return fromBigEndianBits(reverse(bits));
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

    public static Iterable<Integer> digitsPadded(int length, int base, int n) {
        return pad(0, length, digits(base, n));
    }

    public static Iterable<BigInteger> digitsPadded(BigInteger length, BigInteger base, BigInteger n) {
        return pad(BigInteger.ZERO, length, digits(base, n));
    }

    public static List<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    public static List<BigInteger> bigEndianDigits(BigInteger base, final BigInteger n) {
        return reverse(digits(base, n));
    }

    public static Iterable<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    public static Iterable<BigInteger> bigEndianDigitsPadded(BigInteger length, BigInteger base, BigInteger n) {
        return reverse(digitsPadded(length, base, n));
    }

    public static BigInteger fromBigEndianDigits(int base, Iterable<Integer> digits) {
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    public static BigInteger fromBigEndianDigits(BigInteger base, Iterable<BigInteger> digits) {
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    public static BigInteger fromDigits(int base, Iterable<Integer> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    public static BigInteger fromDigits(BigInteger base, Iterable<BigInteger> digits) {
        return fromBigEndianDigits(base, (Iterable<BigInteger>) reverse(digits));
    }

    public static Pair<BigInteger, BigInteger> logarithmicDemux(BigInteger n) {
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    public static List<BigInteger> demux(int lines, BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(lines, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(BasicMath::fromBits, IterableUtils.demux(lines, bits(n))));
    }

    public static boolean isAPowerOfTwo(BigInteger n) {
        return n.getLowestSetBit() == n.bitLength() - 1;
    }
}
