package mho.wheels.math;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

/**
 * Some mathematical utilities
 */
public final class MathUtils {
    /**
     * The size of {@link mho.wheels.math.MathUtils#PRIME_SIEVE}. Must be greater than or equal to
     * ⌈sqrt({@code Integer.MAX_VALUE})⌉ = 46,341
     */
    private static final int PRIME_SIEVE_SIZE = 1 << 16;

    /**
     * A cache of small primes, generated using the Sieve of Eratosthenes algorithm.
     */
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
     * The greatest common divisor of two {@link int}s. If both {@code x} and {@code y} are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code int}.</li>
     *  <li>{@code y} may be any {@code int}.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
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
     * The greatest common divisor of two non-negative {@code int}s.
     *
     * <ul>
     *  <li>{@code x} must be non-negative.</li>
     *  <li>{@code y} must be non-negative.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    private static int positiveGcd(int x, int y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : positiveGcd(y, x % y);
    }

    /**
     * The greatest common divisor of two {@link long}s. If both {@code x} and {@code y} are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code long}.</li>
     *  <li>{@code y} may be any {@code long}.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    public static long gcd(long x, long y) {
        if (x == 0 && y == 0)
            throw new ArithmeticException("cannot take gcd of 0 and 0");
        return positiveGcd(Math.abs(x), Math.abs(y));
    }

    /**
     * The greatest common divisor of two non-negative {@code long}s.
     *
     * <ul>
     *  <li>{@code x} must be non-negative.</li>
     *  <li>{@code y} must be non-negative.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    private static long positiveGcd(long x, long y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : positiveGcd(y, x % y);
    }

    /**
     * Returns the bits of a non-negative {@code int}. The {@link Iterable} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code Iterable}. There are no trailing unset bits. Does
     * not support removal.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable}, containing no nulls. If it is non-empty, the last element is
     *  {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in little-endian order
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
     * Returns the bits of a non-negative {@link BigInteger}. The {@code Iterable} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code Iterable}. There are no trailing unset bits.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable}, containing no nulls. If it is non-empty, the last element is
     *  {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bits(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot get bits of a negative number");
        return take(n.bitLength(), map(n::testBit, range(0)));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code Iterable} returned is little-endian;
     * the least-significant bits come first. It is exactly {@code n} bits long, and right-padded with zeroes (falses)
     * if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} containing no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of bits returned
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(int length, int n) {
        if (length < 0)
            throw new ArithmeticException("cannot pad with a negative length");
        return pad(false, length, bits(n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code BigInteger}. The {@code Iterable} returned is little-
     * endian; the least-significant bits come first. It is exactly {@code n} bits long, and right-padded with zeroes
     * (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} containing no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of bits returned
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(int length, @NotNull BigInteger n) {
        if (length < 0)
            throw new ArithmeticException("cannot pad with a negative length");
        if (n.signum() == -1)
            throw new ArithmeticException("cannot get bits of a negative number");
        return take(length, map(n::testBit, range(0)));
    }

    /**
     * Returns the bits of a non-negative {@code int}. The {@code Iterable} returned is big-endian; the most-
     * significant bits come first. Zero gives an empty {@code Iterable}. There are no leading unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable}, containing no nullsIf it is non-empty, the first element is
     *  {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBits(int n) {
        return reverse(bits(n));
    }

    /**
     * Returns the bits of a non-negative {@code BigInteger}. The {@code Iterable} returned is big-endian; the most-
     * significant bits come first. Zero gives an empty {@code Iterable}. There are no leading unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable}, containing no nullsIf it is non-empty, the first element is
     *  {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBits(@NotNull BigInteger n) {
        return reverse(bits(n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code Iterable} returned is big-endian;
     * the most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeroes (falses) if
     * necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} containing no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code BigInteger}. The {@code Iterable} returned is big-
     * endian; the most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeroes
     * (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} containing no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, BigInteger n) {
        return reverse(bitsPadded(length, n));
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of bits in little-endian order (least significant bits
     * first). Trailing zero (false) bits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code bits} must be finite and every element must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits an {@code Iterable} of bits in little-endian order
     * @return The {@code BigInteger} represented by {@code bits}
     */
    public static @NotNull BigInteger fromBits(@NotNull Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (int i : select(bits, range(0))) {
            n = n.setBit(i);
        }
        return n;
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of bits in big-endian order (most significant bits first).
     * Leading zero (false) bits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code bits} must be finite and every element must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits an {@code Iterable} of bits in big-endian order
     * @return The {@code BigInteger} represented by {@code bits}
     */
    public static @NotNull BigInteger fromBigEndianBits(@NotNull Iterable<Boolean> bits) {
        return fromBits(reverse(bits));
    }

    /**
     * Returns the digits of a non-negative {@code int}. The {@code Iterable} returned is little-endian; the least-
     * significant digits come first. Zero gives an empty {@code Iterable}. There are no trailing zero digits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and whose last element (if it
     *  exists) is non-zero.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>{@code base}</sub>{@code n}⌋+1 otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in little-endian order
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the digits of a non-negative {@code BigInteger}. The {@code Iterable} returned is little-endian; the
     * least-significant digits come first. Zero gives an empty {@code Iterable}. There are no trailing zero digits.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and whose last element (if it
     *  exists) is non-zero.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>{@code base}</sub>{@code n}⌋+1 otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in little-endian order
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code Iterable} returned is little-
     * endian; the least-significant digits come first. It is exactly {@code n} digits long, and left-padded with
     * zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in little-endian order
     */
    public static @NotNull Iterable<Integer> digitsPadded(int length, int base, int n) {
        return pad(0, length, digits(base, n));
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code Iterable} returned is little-
     * endian; the least-significant digits come first. It is exactly {@code n} digits long, and left-padded with
     * zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in little-endian order
     */
    public static @NotNull Iterable<BigInteger> digitsPadded(
            int length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return pad(BigInteger.ZERO, length, digits(base, n));
    }

    /**
     * Returns the digits of a non-negative {@code int}. The {@code Iterable} returned is big-endian; the most-
     * significant digits come first. Zero gives an empty {@code Iterable}. There are no leading zero digits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and whose first element (if it
     *  exists) is non-zero.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>{@code base}</sub>{@code n}⌋ otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull List<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    /**
     * Returns the digits of a non-negative {@code BigInteger}. The {@code Iterable} returned is big-endian; the most-
     * significant digits come first. Zero gives an empty {@code Iterable}. There are no leading zero digits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and whose first element (if it
     *  exists) is non-zero.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>{@code base}</sub>{@code n}⌋ otherwise
     *
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull List<BigInteger> bigEndianDigits(@NotNull BigInteger base, final @NotNull BigInteger n) {
        return reverse(digits(base, n));
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code Iterable} returned is big-endian;
     * the least-significant digits come first. It is exactly {@code n} digits long, and right-padded with zeroes if
     * necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and less than
     *  2<sup>31</sup>–1.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull Iterable<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code BigInteger}. The {@code Iterable} returned is big-
     * endian; the least-significant digits come first. It is exactly {@code n} digits long, and right-padded with
     * zeroes if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} must be non-negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is a finite {@code Iterable} whose elements are non-negative and less than
     *  2<sup>31</sup>–1.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull Iterable<BigInteger> bigEndianDigitsPadded(
            int length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return reverse(digitsPadded(length, base, n));
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of digits in little-endian order (least-significant digits
     * first). Trailing zero digits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code digits} must be finite, and each element must be non-negative.</li>
     *  <li>Every element of {@code digits} must be less than {@code base}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an {@code Iterable} of digits in little-endian order
     * @return The {@code BigInteger} represented by {@code digits}
     */
    public static @NotNull BigInteger fromDigits(int base, @NotNull Iterable<Integer> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of digits in little-endian order (least-significant digits
     * first). Trailing zero digits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code digits} must be finite, and each element must be non-negative.</li>
     *  <li>Every element of {@code digits} must be less than {@code base}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an {@code Iterable} of digits in little-endian order
     * @return The {@code BigInteger} represented by {@code digits}
     */
    public static @NotNull BigInteger fromDigits(@NotNull BigInteger base, @NotNull Iterable<BigInteger> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of digits in big-endian order (most-significant digits
     * first). Leading zero digits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code digits} must be finite, and each element must be non-negative.</li>
     *  <li>Every element of {@code digits} must be less than {@code base}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an {@code Iterable} of digits in big-endian order
     * @return The {@code BigInteger} represented by {@code digits}
     */
    public static @NotNull BigInteger fromBigEndianDigits(int base, @NotNull Iterable<Integer> digits) {
        if (base < 2)
            throw new IllegalArgumentException("base must be at least 2");
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            if (digit < 0 || digit >= base)
                throw new IllegalArgumentException("every digit must be at least zero and less than the base");
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    /**
     * Builds a {@code BigInteger} from an {@code Iterable} of digits in big-endian order (most-significant digits
     * first). Leading zero digits are permitted. Zero may be represented by an empty {@code Iterable}.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code digits} must be finite, and each element must be non-negative.</li>
     *  <li>Every element of {@code digits} must be less than {@code base}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param base the base of the input digits
     * @param digits an {@code Iterable} of digits in big-endian order
     * @return The {@code BigInteger} represented by {@code digits}
     */
    public static @NotNull BigInteger fromBigEndianDigits(
            @NotNull BigInteger base,
            @NotNull Iterable<BigInteger> digits
    ) {
        if (lt(base, BigInteger.valueOf(2)))
            throw new IllegalArgumentException("base must be at least 2");
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            if (digit.signum() == -1 || ge(digit, base))
                throw new IllegalArgumentException("every digit must be at least zero and less than the base");
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    /**
     * Converts a digit to its {@code char} representation. The digits 0 through 9 and converted to '0' through '9',
     * and the digits 10 through 35 are converted to 'A' through 'Z'.
     *
     * <ul>
     *  <li>{@code i} must be non-negative and less than 36.</li>
     *  <li>The result is between '0' and '9', inclusive, or between 'A' and 'Z', inclusive.</li>
     * </ul>
     *
     * @param i the digit to be converted
     * @return the {@code char} representation of {@code i}
     */
    public static char toDigit(int i) {
        if (i >= 0 && i <= 9) {
            return (char) ('0' + i);
        } else if (i >= 10 && i < 36) {
            return (char) ('A' + i - 10);
        } else {
            throw new IllegalArgumentException("digit value must be between 0 and 35, inclusive");
        }
    }

    /**
     * Returns the digit corresponding to a {@code char}. The {@code char}s '0' through '9' are mapped to 0 through 9,
     * and the {@code char}s 'A' through 'Z' are mapped to 10 through 35.
     *
     * <ul>
     *  <li>{@code c} must be between '0' and '9', inclusive, or between 'A' and 'Z', inclusive.</li>
     *  <li>The result is non-negative and less than 36.</li>
     * </ul>
     *
     * @param c a {@code char} corresponding to a digit
     * @return the digit that {@code c} corresponds to
     */
    public static int fromDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else {
            throw new IllegalArgumentException("char must be between '0' and '9' or between 'A' and 'Z'");
        }
    }

    /**
     * Converts an {@code int} to a {@code String} in any base greater than 1. If the base is 36 or less, the digits
     * are '0' through '9' followed by 'A' through 'Z'. If the base is greater than 36, the digits are written in
     * decimal and each digit is surrounded by parentheses. Zero is represented by "0" if the base is 36 or less, or
     * "(0)" otherwise. In every other case there are no leading zeroes.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-null.</li>
     *  <li>The result is a {@code String} which is nonempty and either composed of the characters '0'–'9' and 'A'–'Z'
     *  (not starting with '0', unless that is the only character), or is the concatenation of some numbers from 0 to
     *  2<sup>31</sup>–1 surrounded by parentheses (not starting with "(0)", unless that is the only number). In either
     *  case there may be an optional leading '-', except that "-0" and "-(0)" are not allowed.</li>
     * </ul>
     *
     * @param base the base of the output digits
     * @param n a number
     * @return a {@code String} representation of {@code n} in base {@code base}
     */
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

    /**
     * Converts a {@code BigInteger} to a {@code String} in any base greater than 1. If the base is 36 or less, the
     * digits are '0' through '9' followed by 'A' through 'Z'. If the base is greater than 36, the digits are written
     * in decimal and each digit is surrounded by parentheses. Zero is represented by "0" if the base is 36 or less, or
     * "(0)" otherwise. In every other case there are no leading zeroes.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} must be non-null.</li>
     *  <li>The result is a {@code String} which is nonempty and either composed of the characters '0'–'9' and 'A'–'Z'
     *  (not starting with '0', unless that is the only character), or is the concatenation of some non-negative
     *  numbers surrounded by parentheses (not starting with "(0)", unless that is the only number). In either case
     *  there may be an optional leading '-', except that "-0" and "-(0)" are not allowed.</li>
     * </ul>
     *
     * @param base the base of the output digits
     * @param n a number
     * @return a {@code String} representation of {@code n} in base {@code base}
     */
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

    /**
     * Converts a {@code String} written in some base to a number. If the base is 36 or less, the digits are '0'
     * through '9' followed by 'A' through 'Z'. If the base is greater than 36, the digits are written in decimal and
     * each digit is surrounded by parentheses. The empty {@code String} represents 0. Leading zeroes are permitted. If
     * the {@code String} is invalid, an exception is thrown.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code s} must either be composed of the digits '0' through '9' and 'A' through 'Z', or a sequence of
     *  non-negative decimal integers, each surrounded by parentheses. In either case there may be an optional leading
     *  '-'. {@code s} may also be empty, but "-" is not permitted.</li>
     *  <li>If {@code base} is between 2 and 36, {@code s} may only include the corresponding characters, and the
     *  optional leading '-'. If {@code base} is greater than 36, {@code s} must be composed of decimal integers
     *  surrounded by parentheses (with the optional leading '-'), each integer being non-negative and less than
     *  {@code base}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param base the base that the {@code s} is written in
     * @param s the input {@code String}
     * @return the number represented by {@code s}
     */
    public static @NotNull BigInteger fromStringBase(int base, @NotNull String s) {
        if (base < 2)
            throw new IllegalArgumentException("base must be at least 2");
        if (s.isEmpty())
            return BigInteger.ZERO;
        boolean negative = false;
        if (head(s) == '-') {
            s = tail(s);
            if (s.isEmpty())
                throw new IllegalArgumentException("improperly-formatted String");
            negative = true;
        }
        List<Integer> digits;
        if (base <= 36) {
            digits = toList(map(MathUtils::fromDigit, fromString(s)));
        } else {
            if (head(s) != '(' || last(s) != ')')
                throw new IllegalArgumentException("improperly-formatted String");
            s = tail(init(s));
            digits = toList(map(Integer::parseInt, Arrays.asList(s.split("\\)\\("))));
        }
        BigInteger result = fromBigEndianDigits(base, digits);
        return negative ? result.negate() : result;
    }

    /**
     * Converts a {@code String} written in some base to a number. If the base is 36 or less, the digits are '0'
     * through '9' followed by 'A' through 'Z'. If the base is greater than 36, the digits are written in decimal and
     * each digit is surrounded by parentheses. The empty {@code String} represents 0. Leading zeroes are permitted. If
     * the {@code String} is invalid, an exception is thrown.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code s} must either be composed of the digits '0' through '9' and 'A' through 'Z', or a sequence of
     *  non-negative decimal integers, each surrounded by parentheses. In either case there may be an optional leading
     *  '-'. {@code s} may also be empty, but "-" is not permitted.</li>
     *  <li>If {@code base} is between 2 and 36, {@code s} may only include the corresponding characters, and the
     *  optional leading '-'. If {@code base} is greater than 36, {@code s} must be composed of decimal integers
     *  surrounded by parentheses (with the optional leading '-'), each integer being non-negative and less than
     *  {@code base}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param base the base that the {@code s} is written in
     * @param s the input {@code String}
     * @return the number represented by {@code s}
     */
    public static @NotNull BigInteger fromStringBase(@NotNull BigInteger base, @NotNull String s) {
        if (lt(base, BigInteger.valueOf(2)))
            throw new IllegalArgumentException("base must be at least 2");
        if (s.isEmpty())
            return BigInteger.ZERO;
        boolean negative = false;
        if (head(s) == '-') {
            s = tail(s);
            if (s.isEmpty())
                throw new IllegalArgumentException("improperly-formatted String");
            negative = true;
        }
        List<BigInteger> digits;
        if (le(base, BigInteger.valueOf(36))) {
            digits = toList(map(c -> BigInteger.valueOf(fromDigit(c)), fromString(s)));
        } else {
            if (head(s) != '(' || last(s) != ')')
                throw new IllegalArgumentException("improperly-formatted String");
            s = tail(init(s));
            digits = toList(map(BigInteger::new, Arrays.asList(s.split("\\)\\("))));
        }
        BigInteger result = fromBigEndianDigits(base, digits);
        return negative ? result.negate() : result;
    }

    /**
     * Bijectively maps two natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the result
     * is O({@code x}2<sup>{@code y}</sup>). In other words, the contribution of {@code x} is approximately the base-2
     * log of the contribution of {@code y}. The inverse of this method is
     * {@link mho.wheels.math.MathUtils#logarithmicDemux}.
     *
     * <ul>
     *  <li>{@code x} must be non-negative.</li>
     *  <li>{@code y} must be non-negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first {@code BigInteger}
     * @param y the second {@code BigInteger}
     * @return a {@code BigInteger} generated bijectively from {@code x} and {@code y}
     */
    public static @NotNull BigInteger logarithmicMux(@NotNull BigInteger x, @NotNull BigInteger y) {
        if (x.signum() == -1 || y.signum() == -1)
            throw new ArithmeticException("neither integer can be negative");
        return x.shiftLeft(1).add(BigInteger.ONE).shiftLeft(y.intValueExact()).subtract(BigInteger.ONE);
    }

    /**
     * Bijectively maps one natural {@code BigInteger} to two natural {@code BigInteger}s in such a way that the second
     * is "typically" about the base-2 log of the first. More precisely, this method is the inverse of
     * {@link mho.wheels.math.MathUtils#logarithmicMux}.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is non-null and both of its elements are non-negative.</li>
     * </ul>
     *
     * @param n a {@code BigInteger}
     * @return a pair of {@code BigInteger}s generated bijectively from {@code n}
     */
    public static @NotNull Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("input cannot be negative");
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    /**
     * Bijectively maps two natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the result
     * is O({@code x}{@code y}<sup>2</sup>). In other words, the contribution of {@code x} is approximately the square
     * root of the contribution of {@code y}. The inverse of this method is
     * {@link mho.wheels.math.MathUtils#squareRootDemux}.
     *
     * <ul>
     *  <li>{@code x} must be non-negative.</li>
     *  <li>{@code y} must be non-negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first {@code BigInteger}
     * @param y the second {@code BigInteger}
     * @return a {@code BigInteger} generated bijectively from {@code x} and {@code y}
     */
    public static @NotNull BigInteger squareRootMux(@NotNull BigInteger x, @NotNull BigInteger y) {
        List<Boolean> xBits = toList(bits(x));
        List<Boolean> yBits = toList(bits(y));
        int outputSize = max(xBits.size(), yBits.size()) * 3;
        Iterable<Iterable<Boolean>> xChunks = map(w -> w, chunk(2, concat(xBits, repeat(false))));
        Iterable<Iterable<Boolean>> yChunks = map(Arrays::asList, concat(yBits, repeat(false)));
        return fromBits(take(outputSize, concat(IterableUtils.mux(Arrays.asList(yChunks, xChunks)))));
    }

    /**
     * Bijectively maps one natural {@code BigInteger} to two natural {@code BigInteger}s in such a way that the second
     * is "typically" about the square root of the first. More precisely, this method is the inverse of
     * {@link mho.wheels.math.MathUtils#squareRootMux}.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result is non-null and both of its elements are non-negative.</li>
     * </ul>
     *
     * @param n a {@code BigInteger}
     * @return a pair of {@code BigInteger}s generated bijectively from {@code n}
     */
    public static @NotNull Pair<BigInteger, BigInteger> squareRootDemux(@NotNull BigInteger n) {
        List<Boolean> bits = toList(bits(n));
        Iterable<Boolean> xMask = cycle(Arrays.asList(false, true, true));
        Iterable<Boolean> yMask = cycle(Arrays.asList(true, false, false));
        return new Pair<>(fromBits(select(xMask, bits)), fromBits(select(yMask, bits)));
    }

    /**
     * Bijectively maps a list of natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the
     * result is O(max({@code xs})<sup>|{@code xs}|</sup>), so the contribution of each element of {@code xs} is
     * approximately equal. The bijection is between the naturals and lists of a fixed size, not between naturals and
     * all lists. The empty list maps to 0. The inverse of this method is {@link mho.wheels.math.MathUtils#demux}.
     *
     * <ul>
     *  <li>Every element of {@code xs} must be non-negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs the list of {@code BigInteger}s
     * @return a {@code BigInteger} generated bijectively from {@code xs}
     */
    public static @NotNull BigInteger mux(@NotNull List<BigInteger> xs) {
        if (xs.isEmpty()) return BigInteger.ZERO;
        Iterable<Boolean> muxedBits = IterableUtils.mux(toList(map(x -> concat(bits(x), repeat(false)), reverse(xs))));
        int outputSize = maximum(map(BigInteger::bitLength, xs)) * xs.size();
        return fromBits(take(outputSize, muxedBits));
    }

    /**
     * Bijectively maps one natural {@code BigInteger} to a list of natural {@code BigInteger}s in such a way that
     * every element of the list is "typically" about the same size. More precisely, this method is the inverse of
     * {@link mho.wheels.math.MathUtils#mux}. The bijection is between the naturals and lists of a fixed size, not
     * between naturals and all lists. If {@code size} is 0, the only acceptable {@code n} is 0, which maps to the
     * empty list. The inverse of this method is {@link mho.wheels.math.MathUtils#mux}.
     *
     * <ul>
     *  <li>{@code size} must be non-negative.</li>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>If {@code size} is 0, {@code n} must also be 0.</li>
     *  <li>The result is non-null and all of its elements are non-negative.</li>
     * </ul>
     *
     * @param size the number of {@code BigIntegers} to map {@code n} to
     * @param n a {@code BigInteger}
     * @return a list of {@code BigInteger}s generated bijectively from {@code n}
     */
    public static @NotNull List<BigInteger> demux(int size, @NotNull BigInteger n) {
        if (size == 0 && !n.equals(BigInteger.ZERO))
            throw new ArithmeticException("if muxing into 0 numbers, n must also be 0");
        if (size < 0)
            throw new ArithmeticException("cannot demux into a negative size");
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(size, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(MathUtils::fromBits, IterableUtils.demux(size, bits(n))));
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
        if (lt(base, BigInteger.valueOf(2)))
            throw new ArithmeticException("Base must be at least 2. Invalid base: " + base);
        if (x.signum() != 1)
            throw new ArithmeticException("Argument must be positive. Invalid argument: " + x);
        //noinspection SuspiciousNameCombination
        return fastGrowingCeilingInverse(
                i -> base.pow(i.intValueExact()),
                x,
                BigInteger.ZERO,
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
        //noinspection SuspiciousNameCombination
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
        for (int i = 3; ; i += 2) {
            int square = i * i;
            if (square > n || square < 0) break;
            if (PRIME_SIEVE.get(i) && n % i == 0) return i;
        }
        return n;
    }

    public static @NotNull BigInteger smallestPrimeFactor(@NotNull BigInteger n) {
        if (le(n, BigInteger.valueOf(Integer.MAX_VALUE))) {
            return BigInteger.valueOf(smallestPrimeFactor(n.intValueExact()));
        }
        if (!n.testBit(0)) return BigInteger.valueOf(2);
        for (int i = 3; i < PRIME_SIEVE_SIZE; i += 2) {
            BigInteger bi = BigInteger.valueOf(i);
            if (gt(bi.pow(2), n)) return n;
            if (PRIME_SIEVE.get(i) && n.mod(bi).equals(BigInteger.ZERO)) return bi;
        }
        BigInteger limit = ceilingRoot(BigInteger.valueOf(2), n);
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
        Iterable<List<Integer>> possibleExponents = Combinatorics.controlledListsIncreasing(
                toList(map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, Integer> f = exponents -> productInteger(
                zipWith(p -> BigInteger.valueOf(p.a).pow(p.b).intValueExact(), map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static @NotNull List<BigInteger> factors(@NotNull BigInteger n) {
        List<Pair<BigInteger, Integer>> cpf = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = Combinatorics.controlledListsIncreasing(
                toList(map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, BigInteger> f = exponents -> productBigInteger(
                zipWith(p -> {
                    assert p.a != null;
                    assert p.b != null;
                    return p.a.pow(p.b);
                }, map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static @NotNull Iterable<Integer> intPrimes() {
        int start = (PRIME_SIEVE_SIZE & 1) == 0 ? PRIME_SIEVE_SIZE + 1 : PRIME_SIEVE_SIZE;
        return concat(
                filter(PRIME_SIEVE::get, range(2, PRIME_SIEVE_SIZE - 1)),
                filter(MathUtils::isPrime, rangeBy(start, 2))
        );
    }

    public static @NotNull Iterable<BigInteger> primes() {
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                range(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        return concat(map(i -> BigInteger.valueOf(i), intPrimes()), filter(MathUtils::isPrime, candidates));
    }
}
