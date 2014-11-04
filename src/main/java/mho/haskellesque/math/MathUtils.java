package mho.haskellesque.math;

import mho.haskellesque.iterables.ExhaustiveProvider;
import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * Some mathematical utilities
 */
public final class MathUtils {
    //must be >= ceiling(sqrt(Integer.MAX_VALUE)) = 46,341
    private static final int PRIME_SIEVE_SIZE = 1 << 16;

    private static final BitSet PRIME_SIEVE;
    static {
        PRIME_SIEVE = new BitSet(PRIME_SIEVE_SIZE);
        PRIME_SIEVE.set(2, PRIME_SIEVE_SIZE - 1);
        int multiple;
        for (multiple = 0; multiple < PRIME_SIEVE_SIZE; multiple++) {
            while (!PRIME_SIEVE.get(multiple) && multiple < PRIME_SIEVE_SIZE) multiple++;
            for (int i : rangeBy(multiple * 2, multiple, PRIME_SIEVE_SIZE - 1)) {
                PRIME_SIEVE.clear(i);
            }
        }
    }

    /**
     * Disallow instantiation
     */
    private MathUtils() {}

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

    /**
     * Returns the bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is little-endian; the least-
     * significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>, containing no nulls, ending with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bits(int n) {
        if (n < 0)
            throw new ArithmeticException("cannot get bits of a negative number");
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is little-endian; the
     * least-significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing unset bits.
     * Does not support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>, containing no nulls, ending with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bits(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot get bits of a negative number");
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant bits come first. It is exactly <tt>n</tt> bits long, and right-padded with
     * zeroes (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> containing no nulls.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of bits returned
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(int length, int n) {
        if (length < 0)
            throw new ArithmeticException("cannot pad with a negative length");
        return pad(false, length, bits(n));
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant bits come first. It is exactly <tt>n</tt> bits long, and right-padded with
     * zeroes (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> containing no nulls.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of bits returned
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(int length, @NotNull BigInteger n) {
        if (length < 0)
            throw new ArithmeticException("cannot pad with a negative length");
        return pad(false, length, bits(n));
    }

    /**
     * Returns the bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is big-endian; the most-
     * significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no leading unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>, containing no nulls, beginning with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBits(int n) {
        return reverse(bits(n));
    }

    /**
     * Returns the bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is big-endian; the
     * most-significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no leading unset bits.
     * Does not support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>, containing no nulls, beginning with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBits(@NotNull BigInteger n) {
        return reverse(bits(n));
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * big-endian; the most-significant bits come first. It is exactly <tt>n</tt> bits long, and left-padded with
     * zeroes (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> containing no nulls.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param n a number
     * @return <tt>n</tt>'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is
     * big-endian; the most-significant bits come first. It is exactly <tt>n</tt> bits long, and left-padded with
     * zeroes (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> containing no nulls.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param n a number
     * @return <tt>n</tt>'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, BigInteger n) {
        return reverse(bitsPadded(length, n));
    }

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of bits in big-endian order (most significant bits
     * first). Leading zero (false) bits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>bits</tt> must be finite and every element must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits an <tt>Iterable</tt> of bits in big-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>bits</tt>
     */
    public static @NotNull BigInteger fromBigEndianBits(@NotNull Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (boolean bit : bits) {
            n = n.shiftLeft(1);
            if (bit) n = n.add(BigInteger.ONE);
        }
        return n;
    }

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of bits in little-endian order (least significant bits
     * first). Trailing zero (false) bits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>bits</tt> must be finite and every element must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits an <tt>Iterable</tt> of bits in little-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>bits</tt>
     */
    public static @NotNull BigInteger fromBits(@NotNull Iterable<Boolean> bits) {
        return fromBigEndianBits(reverse(bits));
    }

    /**
     * Returns the digits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is little-endian; the least-
     * significant digits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing zero digits. Does
     * not support removal.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and whose last element is greater
     *  than zero.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub><tt>base</tt></sub><tt>n</tt>&#x230B; otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in little-endian order
     */
    public static @NotNull Iterable<Integer> digits(int base, int n) {
        if (base < 2)
            throw new IllegalArgumentException("base must be at least 2");
        if (n < 0)
            throw new ArithmeticException("cannot get digits of a negative number");
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

    /**
     * Returns the digits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is little-endian;
     * the least-significant digits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing zero
     * digits. Does not support removal.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and whose last element is non-
     *  zero.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub><tt>base</tt></sub><tt>n</tt>&#x230B; otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in little-endian order
     */
    public static @NotNull Iterable<BigInteger> digits(@NotNull BigInteger base, @NotNull final BigInteger n) {
        if (lt(base, BigInteger.valueOf(2)))
            throw new IllegalArgumentException("base must be at least 2");
        if (n.signum() == -1)
            throw new ArithmeticException("cannot get digits of a negative number");
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

    /**
     * Returns the lowest <tt>n</tt> digits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant digits come first. It is exactly <tt>n</tt> digits long, and left-padded
     * with zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and less than
     *  2<sup>31</sup>&#x2212;1.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in little-endian order
     */
    public static @NotNull Iterable<Integer> digitsPadded(int length, int base, int n) {
        return pad(0, length, digits(base, n));
    }

    /**
     * Returns the lowest <tt>n</tt> digits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant digits come first. It is exactly <tt>n</tt> digits long, and left-padded
     * with zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in little-endian order
     */
    public static @NotNull Iterable<BigInteger> digitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return pad(BigInteger.ZERO, length, digits(base, n));
    }

    /**
     * Returns the digits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is big-endian; the most-
     * significant digits come first. Zero gives an empty <tt>Iterable</tt>. There are no leading zero digits. Does not
     * support removal.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and whose first element is greater
     *  than zero.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub><tt>base</tt></sub><tt>n</tt>&#x230B; otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in big-endian order
     */
    public static @NotNull List<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    /**
     * Returns the digits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is big-endian; the
     * most-significant digits come first. Zero gives an empty <tt>Iterable</tt>. There are no leading zero digits.
     * Does not support removal.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and whose first element is greater
     *  than zero.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub><tt>base</tt></sub><tt>n</tt>&#x230B; otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in big-endian order
     */
    public static @NotNull List<BigInteger> bigEndianDigits(@NotNull BigInteger base, final @NotNull BigInteger n) {
        return reverse(digits(base, n));
    }

    /**
     * Returns the lowest <tt>n</tt> digits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * big-endian; the least-significant digits come first. It is exactly <tt>n</tt> digits long, and right-padded with
     * zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and less than
     *  2<sup>31</sup>&#x2212;1.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in big-endian order
     */
    public static @NotNull Iterable<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    /**
     * Returns the lowest <tt>n</tt> digits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is
     * big-endian; the least-significant digits come first. It is exactly <tt>n</tt> digits long, and right-padded with
     * zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> whose elements are non-negative and less than
     *  2<sup>31</sup>&#x2212;1.</li>
     * </ul>
     *
     * Result length is <tt>length</tt>
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return <tt>n</tt>'s digits in big-endian order
     */
    public static @NotNull Iterable<BigInteger> bigEndianDigitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return reverse(digitsPadded(length, base, n));
    }

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of digits in big-endian order (most-significant digits
     * first). Leading zero digits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>digits</tt> must be finite, and each element must be non-negative.</li>
     *  <li>Every element of <tt>digits</tt> must be less than <tt>base</tt>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an <tt>Iterable</tt> of digits in big-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>digits</tt>
     */
    public static @NotNull BigInteger fromBigEndianDigits(int base, @NotNull Iterable<Integer> digits) {
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of digits in big-endian order (most-significant digits
     * first). Leading zero digits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>digits</tt> must be finite, and each element must be non-negative.</li>
     *  <li>Every element of <tt>digits</tt> must be less than <tt>base</tt>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an <tt>Iterable</tt> of digits in big-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>digits</tt>
     */
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

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of digits in little-endian order (least-significant
     * digits first). Trailing zero digits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>digits</tt> must be finite, and each element must be non-negative.</li>
     *  <li>Every element of <tt>digits</tt> must be less than <tt>base</tt>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an <tt>Iterable</tt> of digits in little-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>digits</tt>
     */
    public static @NotNull BigInteger fromDigits(int base, @NotNull Iterable<Integer> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    /**
     * Builds a <tt>BigInteger</tt> from an <tt>Iterable</tt> of digits in little-endian order (least-significant
     * digits first). Trailing zero digits are permitted. Zero may be represented by an empty <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>base</tt> must be at least 2.</li>
     *  <li><tt>digits</tt> must be finite, and each element must be non-negative.</li>
     *  <li>Every element of <tt>digits</tt> must be less than <tt>base</tt>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an <tt>Iterable</tt> of digits in little-endian order
     * @return The <tt>BigInteger</tt> represented by <tt>digits</tt>
     */
    public static @NotNull BigInteger fromDigits(@NotNull BigInteger base, @NotNull Iterable<BigInteger> digits) {
        return fromBigEndianDigits(base, (Iterable<BigInteger>) reverse(digits));
    }

    private static char toDigit(int i) {
        return (char) (i < 10 ? '0' + i : 'A' + i - 10);
    }

    public static @NotNull String toStringBase(int base, int n) {
        if (base < 2)
            throw new IllegalArgumentException("base must be at least 2");
        boolean bigBase = base > 36;
        if (n == 0) {
            return bigBase ? "(0)" : "0";
        }
        boolean negative = n < 0;
        if (negative) n = -n;
        List<Integer> digits = bigEndianDigits(base, n);
        String absString;
        if (bigBase) {
            absString = concatStrings(map(d -> "(" + d + ")", digits));
        } else {
            absString = charsToString(map(MathUtils::toDigit, digits));
        }
        return negative ? cons('-', absString) : absString;
    }

    public static @NotNull String toStringBase(@NotNull BigInteger base, @NotNull BigInteger n) {
        if (lt(base, BigInteger.valueOf(2)))
            throw new IllegalArgumentException("base must be at least 2");
        boolean bigBase = gt(base, BigInteger.valueOf(36));
        if (n.equals(BigInteger.ZERO)) {
            return bigBase ? "(0)" : "0";
        }
        boolean negative = n.signum() == -1;
        if (negative) n = n.negate();
        List<BigInteger> digits = bigEndianDigits(base, n);
        String absString;
        if (bigBase) {
            absString = concatStrings(map(d -> "(" + d + ")", digits));
        } else {
            absString = charsToString(map(d -> toDigit(d.intValue()), digits));
        }
        return negative ? cons('-', absString) : absString;
    }

    public static @NotNull Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    public static @NotNull Pair<BigInteger, BigInteger> squareRootDemux(@NotNull BigInteger n) {
        List<Boolean> bits = toList(bits(n));
        Iterable<Boolean> aMask = cycle(Arrays.asList(true, false, false));
        Iterable<Boolean> bMask = cycle(Arrays.asList(false, true, true));
        return new Pair<>(fromBits(select(bMask, bits)), fromBits(select(aMask, bits)));
    }

    public static @NotNull List<BigInteger> demux(int lines, @NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(lines, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(MathUtils::fromBits, IterableUtils.demux(lines, bits(n))));
    }

    public static boolean isAPowerOfTwo(@NotNull BigInteger n) {
        return n.getLowestSetBit() == n.bitLength() - 1;
    }

    public static @NotNull BigInteger fastGrowingCeilingInverse(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull BigInteger y,
            @NotNull BigInteger min,
            @NotNull BigInteger max
    ) {
        for (BigInteger x : range(min, max)) {
            BigInteger j = f.apply(x);
            if (ge(j, y)) {
                return x;
            }
        }
        throw new IllegalArgumentException("inverse not found in range");
    }

    public static @NotNull BigInteger ceilingLog(@NotNull BigInteger base, @NotNull BigInteger x) {
        return fastGrowingCeilingInverse(
                i -> base.pow(i.intValueExact()),
                x,
                BigInteger.ONE,
                x //very loose bound
        );
    }

    public static @NotNull BigInteger ceilingInverse(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull BigInteger y,
            @NotNull BigInteger min,
            @NotNull BigInteger max
    ) {
        while (true) {
            if (min.equals(max)) return max;
            BigInteger mid = min.add(max).shiftRight(1);
            BigInteger fMid = f.apply(mid);
            switch (compare(fMid, y)) {
                case GT:
                    max = mid; break;
                case LT:
                    min = mid.add(BigInteger.ONE); break;
                default:
                    return mid;
            }
        }
    }

    public static @NotNull BigInteger ceilingRoot(@NotNull BigInteger r, @NotNull BigInteger x) {
        return ceilingInverse(
                i -> i.pow(r.intValueExact()),
                x,
                BigInteger.ZERO,
                x //very loose bound
        );
    }

    public static int smallestPrimeFactor(int n) {
        if (n < 2)
            throw new IllegalArgumentException("argument must be at least 2");
        if (n % 2 == 0) return 2;
        if (n < PRIME_SIEVE_SIZE && PRIME_SIEVE.get(n)) return n;
        int limit = ceilingRoot(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact();
        for (int i = 3; i <= limit; i++) {
            if (PRIME_SIEVE.get(i) && n % i == 0) return i;
        }
        return n;
    }

    public static @NotNull BigInteger smallestPrimeFactor(@NotNull BigInteger n) {
        if (le(n, BigInteger.valueOf(Integer.MAX_VALUE))) {
            return BigInteger.valueOf(smallestPrimeFactor(n.intValueExact()));
        }
        if (!n.testBit(0)) return BigInteger.valueOf(2);
        BigInteger limit = ceilingRoot(BigInteger.valueOf(2), n);
        int sieveLimit = min(limit, BigInteger.valueOf(PRIME_SIEVE_SIZE - 1)).intValueExact();
        for (int i = 3; i <= sieveLimit; i++) {
            BigInteger bi = BigInteger.valueOf(i);
            if (PRIME_SIEVE.get(i) && n.mod(bi).equals(BigInteger.ZERO)) return bi;
        }
        if (limit.equals(BigInteger.valueOf(sieveLimit))) return n;
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                range(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        for (BigInteger candidate : takeWhile(i -> le(i, limit), candidates)) {
            if (n.mod(candidate).equals(BigInteger.ZERO)) return candidate;
        }
        return n;
    }

    public static boolean isPrime(int n) {
        return smallestPrimeFactor(n) == n;
    }

    public static boolean isPrime(@NotNull BigInteger n) {
        return smallestPrimeFactor(n).equals(n);
    }

    public static @NotNull Iterable<Integer> primeFactors(int n) {
        return unfoldr(
                i -> {
                    if (i == 1) return Optional.empty();
                    int spf = smallestPrimeFactor(i);
                    return Optional.of(new Pair<>(spf, i / spf));
                },
                n
        );
    }

    public static @NotNull Iterable<BigInteger> primeFactors(@NotNull BigInteger n) {
        return unfoldr(
                i -> {
                    if (i.equals(BigInteger.ONE)) return Optional.empty();
                    BigInteger spf = smallestPrimeFactor(i);
                    return Optional.of(new Pair<>(spf, i.divide(spf)));
                },
                n
        );
    }

    public static @NotNull Iterable<Pair<Integer, Integer>> compactPrimeFactors(int n) {
        return countAdjacent(primeFactors(n));
    }

    public static @NotNull Iterable<Pair<BigInteger, Integer>> compactPrimeFactors(@NotNull BigInteger n) {
        return countAdjacent(primeFactors(n));
    }

    public static @NotNull List<Integer> factors(int n) {
        List<Pair<Integer, Integer>> cpf = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = Combinatorics.controlledListsAscending(
                toList((Iterable<Iterable<Integer>>) map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, Integer> f = exponents -> productInteger(
                zipWith(p -> BigInteger.valueOf(p.a).pow(p.b).intValueExact(), map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static @NotNull List<BigInteger> factors(@NotNull BigInteger n) {
        List<Pair<BigInteger, Integer>> cpf = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = Combinatorics.controlledListsAscending(
                toList((Iterable<Iterable<Integer>>) map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, BigInteger> f = exponents -> productBigInteger(
                zipWith(p -> p.a.pow(p.b), map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static void main(String[] args) {
        for (BigInteger i : range(new BigInteger("1000000000000000"))) {
            System.out.println(factors(i));
        }
    }
}
