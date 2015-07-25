package mho.wheels.numberUtils;

import mho.wheels.io.Readers;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.NoRemoveIterator;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

public class IntegerUtils {
    /**
     * –1
     */
    public static final @NotNull BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);

    /**
     * 2
     */
    public static final @NotNull BigInteger TWO = BigInteger.valueOf(2);

    /**
     * The default maximum number of elements to print when printing an {@code Iterable}.
     */
    private static final int ITERABLE_PRINT_LIMIT = 20;

    /**
     * Determines whether {@code this} is a power of 2.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result may be either boolean.</li>
     * </ul>
     *
     * @param n a positive number
     * @return whether {@code n} is a power of two
     */
    public static boolean isPowerOfTwo(int n) {
        if (n < 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return (n & (n - 1)) == 0;
    }

    /**
     * Determines whether {@code this} is a power of 2.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result may be either boolean.</li>
     * </ul>
     *
     * @param n a positive number
     * @return whether {@code n} is a power of two
     */
    public static boolean isPowerOfTwo(long n) {
        if (n < 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return (n & (n - 1)) == 0L;
    }

    /**
     * Determines whether {@code this} is a power of 2.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result may be either boolean.</li>
     * </ul>
     *
     * @param n a positive number
     * @return whether {@code n} is a power of two
     */
    public static boolean isPowerOfTwo(@NotNull BigInteger n) {
        if (n.signum() != 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return n.getLowestSetBit() == n.bitLength() - 1;
    }

    /**
     * Finds the largest integer p such that {@code n}≤2<sup>p</sup>.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is between 0 and 31, inclusive.</li>
     * </ul>
     *
     * @param n a positive number
     * @return ⌈log<sub>2</sub>({@code n})⌉
     */
    public static int ceilingLog2(int n) {
        if (n < 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        int bitLength = 32 - Integer.numberOfLeadingZeros(n);
        return (n & (n - 1)) == 0 ? bitLength - 1 : bitLength;
    }

    /**
     * Finds the largest integer p such that {@code n}≤2<sup>p</sup>.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is between 0 and 63, inclusive.</li>
     * </ul>
     *
     * @param n a positive number
     * @return ⌈log<sub>2</sub>({@code n})⌉
     */
    public static int ceilingLog2(long n) {
        if (n < 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        int bitLength = 64 - Long.numberOfLeadingZeros(n);
        return (n & (n - 1)) == 0 ? bitLength - 1 : bitLength;
    }

    /**
     * Finds the largest integer p such that {@code n}≤2<sup>p</sup>.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param n a positive number
     * @return ⌈log<sub>2</sub>({@code n})⌉
     */
    public static int ceilingLog2(@NotNull BigInteger n) {
        if (n.signum() != 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        int bitLength = n.bitLength();
        return n.getLowestSetBit() == bitLength - 1 ? bitLength - 1 : bitLength;
    }

    /**
     * Returns the bits of a non-negative {@code int}. The {@link Iterable} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code Iterable}. There are no trailing unset bits. Does
     * not support removal.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
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
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return () -> new NoRemoveIterator<Boolean>() {
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

    /**
     * Returns the bits of a non-negative {@link BigInteger}. The {@code Iterable} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code Iterable}. There are no trailing unset bits.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
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
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return take(n.bitLength(), map(n::testBit, rangeUp(0)));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code Iterable} returned is little-endian;
     * the least-significant bits come first. It is exactly {@code n} bits long, and right-padded with zeroes (falses)
     * if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
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
        if (length < 0) {
            throw new ArithmeticException("length cannot be negative. Invalid length: " + length);
        }
        return pad(false, length, bits(n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code BigInteger}. The {@code Iterable} returned is little-
     * endian; the least-significant bits come first. It is exactly {@code n} bits long, and right-padded with zeroes
     * (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
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
        if (length < 0) {
            throw new ArithmeticException("length cannot be negative. Invalid length: " + length);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return take(length, map(n::testBit, rangeUp(0)));
    }

    /**
     * Returns the bits of a non-negative {@code int}. The {@code List} returned is big-endian; the most-significant
     * bits come first. Zero gives an empty {@code Iterable}. There are no leading unset bits.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is contains no nulls. If it is non-empty, the first element is {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull List<Boolean> bigEndianBits(int n) {
        return reverse(bits(n));
    }

    /**
     * Returns the bits of a non-negative {@code BigInteger}. The {@code List} returned is big-endian; the
     * most-significant bits come first. Zero gives an empty {@code List}. There are no leading unset bits.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls. If it is non-empty, the first element is {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull List<Boolean> bigEndianBits(@NotNull BigInteger n) {
        return reverse(bits(n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code List} returned is big-endian; the
     * most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeroes (falses) if
     * necessary.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull List<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code BigInteger}. The {@code List} returned is big-endian;
     * the most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeroes (falses) if
     * necessary.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull List<Boolean> bigEndianBitsPadded(int length, BigInteger n) {
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
        for (int i : select(bits, rangeUp(0))) {
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
     *  <li>{@code n} cannot be negative.</li>
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
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return () -> new NoRemoveIterator<Integer>() {
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
     * Returns the digits of a non-negative {@code BigInteger}. The {@code Iterable} returned is little-endian; the
     * least-significant digits come first. Zero gives an empty {@code Iterable}. There are no trailing zero digits.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
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
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
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
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
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
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
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
     * Returns the digits of a non-negative {@code int}. The {@code List} returned is big-endian; the most-significant
     * digits come first. Zero gives an empty {@code Iterable}. There are no leading zero digits.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a {@code List} whose elements are non-negative and whose first element (if it exists)
     *  is non-zero.</li>
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
     * Returns the digits of a non-negative {@code BigInteger}. The {@code List} returned is big-endian; the
     * most-significant digits come first. Zero gives an empty {@code List}. There are no leading zero digits.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a {@code List} whose elements are non-negative and whose first element (if it exists) is
     *  non-zero.</li>
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
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code List} returned is big-endian; the
     * least-significant digits come first. It is exactly {@code n} digits long, and right-padded with zeroes if
     * necessary.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a {@code List} whose elements are non-negative and less than 2<sup>31</sup>–1.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull List<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code BigInteger}. The {@code List} returned is
     * big-endian; the least-significant digits come first. It is exactly {@code n} digits long, and right-padded with
     * zeroes if necessary.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a {@code List} whose elements are non-negative and less than 2<sup>31</sup>–1.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param base the base of the output digits
     * @param n a number
     * @return {@code n}'s digits in big-endian order
     */
    public static @NotNull List<BigInteger> bigEndianDigitsPadded(
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
     *  <li>{@code digits} must be finite, and each element cannot be negative.</li>
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
     *  <li>{@code digits} must be finite, and each element cannot be negative.</li>
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
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            if (digit < 0) {
                String digitsString = IterableUtils.toString(ITERABLE_PRINT_LIMIT, digits);
                throw new IllegalArgumentException("Each element of digits must be non-negative. Invalid digit: " +
                        digit + " in " + digitsString);
            } else if (digit >= base) {
                String digitsString = IterableUtils.toString(ITERABLE_PRINT_LIMIT, digits);
                throw new IllegalArgumentException("Each element of digits must be less than base, which is " + base +
                        ". Invalid digit: " + digit + " in " + digitsString);
            }
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
     *  <li>{@code digits} must be finite, and each element cannot be negative.</li>
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
        if (lt(base, TWO))
            throw new IllegalArgumentException("base must be at least 2");
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            if (digit.signum() == -1 || ge(digit, base))
                throw new IllegalArgumentException("every digit must be at least zero and less than the base");
            if (digit.signum() == -1) {
                String digitsString = IterableUtils.toString(ITERABLE_PRINT_LIMIT, digits);
                throw new IllegalArgumentException("Each element of digits must be non-negative. Invalid digit: " +
                        digit + " in " + digitsString);
            } else if (ge(digit, base)) {
                String digitsString = IterableUtils.toString(ITERABLE_PRINT_LIMIT, digits);
                throw new IllegalArgumentException("Each element of digits must be less than base, which is " + base +
                        ". Invalid digit: " + digit + " in " + digitsString);
            }
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    /**
     * Converts a digit to its {@code char} representation. The digits 0 through 9 and converted to '0' through '9',
     * and the digits 10 through 35 are converted to 'A' through 'Z'.
     *
     * <ul>
     *  <li>{@code i} must be at least 0 and no greater than 35.</li>
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
            throw new IllegalArgumentException("i must be at least 0 and no greater than 35. Invalid i: " + i);
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
            throw new IllegalArgumentException(
                    "c must be between '0' and '9', inclusive, or between 'A' and 'Z', inclusive. Invalid c: " + c
            );
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
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
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
            absString = charsToString(map(IntegerUtils::toDigit, digits));
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
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
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
     * each digit is surrounded by parentheses (in this case, the {@code String} representing the digit cannot be empty
     * and no leading zeroes are allowed unless the digit is 0). The empty {@code String} represents 0. Leading zeroes
     * are permitted. If the {@code String} is invalid, an exception is thrown.
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
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (s.isEmpty())
            return BigInteger.ZERO;
        boolean negative = false;
        if (head(s) == '-') {
            s = tail(s);
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            negative = true;
        }
        List<Integer> digits;
        if (base <= 36) {
            digits = toList(map(IntegerUtils::fromDigit, fromString(s)));
        } else {
            if (head(s) != '(' || last(s) != ')' || s.contains("()")) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            s = tail(init(s));
            digits = toList(
                    map(
                            digit -> {
                                Optional<Integer> oi = Readers.readInteger(digit);
                                if (!oi.isPresent()) {
                                    throw new IllegalArgumentException("Improperly-formatted digit: " + digit);
                                }
                                return oi.get();
                            },
                            Arrays.asList(s.split("\\)\\("))
                    )
            );
        }
        BigInteger result = fromBigEndianDigits(base, digits);
        return negative ? result.negate() : result;
    }

    /**
     * Converts a {@code String} written in some base to a number. If the base is 36 or less, the digits are '0'
     * through '9' followed by 'A' through 'Z'. If the base is greater than 36, the digits are written in decimal and
     * each digit is surrounded by parentheses (in this case, the {@code String} representing the digit cannot be empty
     * and no leading zeroes are allowed unless the digit is 0). The empty {@code String} represents 0. Leading zeroes
     * are permitted. If the {@code String} is invalid, an exception is thrown.
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
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (s.isEmpty())
            return BigInteger.ZERO;
        boolean negative = false;
        if (head(s) == '-') {
            s = tail(s);
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            negative = true;
        }
        List<BigInteger> digits;
        if (le(base, BigInteger.valueOf(36))) {
            digits = toList(map(c -> BigInteger.valueOf(fromDigit(c)), fromString(s)));
        } else {
            if (head(s) != '(' || last(s) != ')' || s.contains("()")) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            s = tail(init(s));
            digits = toList(
                    map(
                            digit -> {
                                Optional<BigInteger> oi = Readers.readBigInteger(digit);
                                if (!oi.isPresent()) {
                                    throw new IllegalArgumentException("Improperly-formatted digit: " + digit);
                                }
                                return oi.get();
                            },
                            Arrays.asList(s.split("\\)\\("))
                    )
            );
        }
        BigInteger result = fromBigEndianDigits(base, digits);
        return negative ? result.negate() : result;
    }

    /**
     * Bijectively maps two natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the result
     * is O({@code x}2<sup>{@code y}</sup>). In other words, the contribution of {@code x} is approximately the base-2
     * log of the contribution of {@code y}. The inverse of this method is
     * {@link mho.wheels.numberUtils.IntegerUtils#logarithmicDemux}.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first {@code BigInteger}
     * @param y the second {@code BigInteger}
     * @return a {@code BigInteger} generated bijectively from {@code x} and {@code y}
     */
    public static @NotNull BigInteger logarithmicMux(@NotNull BigInteger x, @NotNull BigInteger y) {
        if (x.signum() == -1) {
            throw new ArithmeticException("x cannot be negative. Invalid x: " + x);
        }
        if (y.signum() == -1) {
            throw new ArithmeticException("y cannot be negative. Invalid y: " + y);
        }
        return x.shiftLeft(1).add(BigInteger.ONE).shiftLeft(y.intValueExact()).subtract(BigInteger.ONE);
    }

    /**
     * Bijectively maps one natural {@code BigInteger} to two natural {@code BigInteger}s in such a way that the second
     * is "typically" about the base-2 log of the first. More precisely, this method is the inverse of
     * {@link mho.wheels.numberUtils.IntegerUtils#logarithmicMux}.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is non-null and both of its elements are non-negative.</li>
     * </ul>
     *
     * @param n a {@code BigInteger}
     * @return a pair of {@code BigInteger}s generated bijectively from {@code n}
     */
    public static @NotNull
    Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    /**
     * Bijectively maps two natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the result
     * is O({@code x}{@code y}<sup>2</sup>). In other words, the contribution of {@code x} is approximately the square
     * root of the contribution of {@code y}. The inverse of this method is
     * {@link mho.wheels.numberUtils.IntegerUtils#squareRootDemux}.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
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
     * {@link mho.wheels.numberUtils.IntegerUtils#squareRootMux}.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
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
     * all lists. The empty list maps to 0. The inverse of this method is
     * {@link mho.wheels.numberUtils.IntegerUtils#demux}.
     *
     * <ul>
     *  <li>Every element of {@code xs} cannot be negative.</li>
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
     * {@link mho.wheels.numberUtils.IntegerUtils#mux}. The bijection is between the naturals and lists of a fixed size, not
     * between naturals and all lists. If {@code size} is 0, the only acceptable {@code n} is 0, which maps to the
     * empty list. The inverse of this method is {@link mho.wheels.numberUtils.IntegerUtils#mux}.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
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
        return reverse(IterableUtils.map(IntegerUtils::fromBits, IterableUtils.demux(size, bits(n))));
    }
}
