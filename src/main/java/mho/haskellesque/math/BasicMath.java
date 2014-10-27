package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.*;

/**
 * Some mathematical utilities
 */
public final class BasicMath {
    /**
     * Disallow instantiation
     */
    private BasicMath() {}

    /**
     * The greatest common divisor of two <tt>int</tt>s. If both <tt>x</tt> and <tt>y</tt> are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li><tt>x</tt> may be any <tt>int</tt>.</li>
     *  <li><tt>y</tt> may be any <tt>int</tt>.</li>
     *  <li><tt>x</tt> and <tt>y</tt> y may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    public static int gcd(int x, int y) {
        if (x == 0 && y == 0)
            throw new ArithmeticException("cannot take gcd of 0 and 0");
        return positiveGcd(Math.abs(x), Math.abs(y));
    }

    /**
     * The greatest common divisor of two non-negative <tt>int</tt>s.
     *
     * <ul>
     *  <li><tt>x</tt> must be non-negative.</li>
     *  <li><tt>y</tt> must be non-negative.</li>
     *  <li><tt>x</tt> and <tt>y</tt> y may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
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

    public static @NotNull Iterable<Boolean> bits(final int n) {
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

    public static @NotNull Iterable<Boolean> bits(final @NotNull BigInteger n) {
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

    public static @NotNull Iterable<Boolean> bitsPadded(int length, int n) {
        return pad(false, length, bits(n));
    }

    public static @NotNull Iterable<Boolean> bitsPadded(BigInteger length, BigInteger n) {
        return pad(false, length, bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBits(@NotNull final int n) {
        return reverse(bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBits(@NotNull final BigInteger n) {
        return reverse(bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(BigInteger length, BigInteger n) {
        return reverse(bitsPadded(length, n));
    }

    public static @NotNull BigInteger fromBigEndianBits(@NotNull Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (boolean bit : bits) {
            n = n.shiftLeft(1);
            if (bit) n = n.add(BigInteger.ONE);
        }
        return n;
    }

    public static @NotNull BigInteger fromBits(@NotNull Iterable<Boolean> bits) {
        return fromBigEndianBits(reverse(bits));
    }

    public static @NotNull Iterable<Integer> digits(int base, final int n) {
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

    public static @NotNull Iterable<BigInteger> digits(@NotNull BigInteger base, @NotNull final BigInteger n) {
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

    public static @NotNull Iterable<Integer> digitsPadded(int length, int base, int n) {
        return pad(0, length, digits(base, n));
    }

    public static @NotNull Iterable<BigInteger> digitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return pad(BigInteger.ZERO, length, digits(base, n));
    }

    public static @NotNull List<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    public static @NotNull List<BigInteger> bigEndianDigits(@NotNull BigInteger base, final @NotNull BigInteger n) {
        return reverse(digits(base, n));
    }

    public static @NotNull Iterable<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    public static @NotNull Iterable<BigInteger> bigEndianDigitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return reverse(digitsPadded(length, base, n));
    }

    public static @NotNull BigInteger fromBigEndianDigits(int base, @NotNull Iterable<Integer> digits) {
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    public static @NotNull BigInteger fromBigEndianDigits(
            @NotNull BigInteger base,
            @NotNull Iterable<BigInteger> digits
    ) {
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    public static @NotNull BigInteger fromDigits(int base, @NotNull Iterable<Integer> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    public static @NotNull BigInteger fromDigits(@NotNull BigInteger base, @NotNull Iterable<BigInteger> digits) {
        return fromBigEndianDigits(base, (Iterable<BigInteger>) reverse(digits));
    }

    public static @NotNull Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    public static @NotNull List<BigInteger> demux(int lines, @NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(lines, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(BasicMath::fromBits, IterableUtils.demux(lines, bits(n))));
    }

    public static boolean isAPowerOfTwo(@NotNull BigInteger n) {
        return n.getLowestSetBit() == n.bitLength() - 1;
    }
}
