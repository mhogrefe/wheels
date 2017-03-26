package mho.wheels.numberUtils;

import mho.wheels.io.Readers;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

/**
 * Some utilities for dealing with integers.
 */
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
     * The number of alphanumeric characters, or 36.
     */
    private static final @NotNull BigInteger ALPHANUMERIC_COUNT = BigInteger.valueOf(36);

    /**
     * Disallow instantiation
     */
    private IntegerUtils() {}

    /**
     * Determines whether {@code n} is a power of 2.
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
     * Determines whether {@code n} is a power of 2.
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
     * Determines whether {@code n} is a power of 2.
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
        return (n & (n - 1)) == 0L ? bitLength - 1 : bitLength;
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
     * Returns the bits of a non-negative {@code int}. The {@link List} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code List}. There are no trailing unset bits.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls. If it is non-empty, the last element is {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull List<Boolean> bits(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        for (int remaining = n; remaining != 0; remaining >>= 1) {
            bits.add((remaining & 1) != 0);
        }
        return bits;
    }

    /**
     * Returns the bits of a non-negative {@link BigInteger}. The {@code List} returned is little-endian; the
     * least-significant bits come first. Zero gives an empty {@code List}. There are no trailing unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls. If it is non-empty, the last element is {@code true}.</li>
     * </ul>
     *
     * Result length is 0 if {@code n} is 0, or ⌊log<sub>2</sub>{@code n}⌋+1 otherwise
     *
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull List<Boolean> bits(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        int bitLength = n.bitLength();
        for (int i = 0; i < bitLength; i++) {
            bits.add(n.testBit(i));
        }
        return bits;
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code List} returned is little-endian; the
     * least-significant bits come first. It is exactly {@code n} bits long, and right-padded with zeros (falses) if
     * necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>All bits in the result apart from the first 31 are false.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of bits returned
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull List<Boolean> bitsPadded(int length, int n) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        int remaining = n;
        for (int i = 0; i < length; i++) {
            bits.add((remaining & 1) != 0);
            remaining >>= 1;
        }
        return bits;
    }

    /**
     * Returns the lowest {@code length} bits of a non-negative {@code BigInteger}. The {@code List} returned is
     * little-endian; the least-significant bits come first. It is exactly {@code n} bits long, and right-padded with
     * zeros (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result contains no nulls.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of bits returned
     * @param n a number
     * @return {@code n}'s bits in little-endian order
     */
    public static @NotNull List<Boolean> bitsPadded(int length, @NotNull BigInteger n) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            bits.add(n.testBit(i));
        }
        return bits;
    }

    /**
     * Returns the bits of a non-negative {@code int}. The {@code List} returned is big-endian; the most-significant
     * bits come first. Zero gives an empty {@code List}. There are no leading unset bits.
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
    public static @NotNull List<Boolean> bigEndianBits(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        for (int mask = Integer.highestOneBit(n); mask != 0; mask >>= 1) {
            bits.add((n & mask) != 0);
        }
        return bits;
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
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        for (int i = n.bitLength() - 1; i >= 0; i--) {
            bits.add(n.testBit(i));
        }
        return bits;
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code int}. The {@code List} returned is big-endian; the
     * most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeros (falses) if
     * necessary.
     *
     * <ul>
     *  <li>{@code length} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>All bits in the result apart from the last 31 are false.</li>
     * </ul>
     *
     * Result length is {@code length}
     *
     * @param length the number of digits returned
     * @param n a number
     * @return {@code n}'s bits in big-endian order
     */
    public static @NotNull List<Boolean> bigEndianBitsPadded(int length, int n) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        if (length == 0) return bits;
        for (; length > 31; length--) {
            bits.add(false);
        }
        for (int mask = 1 << (length - 1); mask != 0; mask >>= 1) {
            bits.add((n & mask) != 0);
        }
        return bits;
    }

    /**
     * Returns the lowest {@code n} bits of a non-negative {@code BigInteger}. The {@code List} returned is big-endian;
     * the most-significant bits come first. It is exactly {@code n} bits long, and left-padded with zeros (falses) if
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
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Boolean> bits = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            bits.add(n.testBit(i));
        }
        return bits;
    }

    /**
     * Builds a {@code BigInteger} from a {@code List} of bits in little-endian order (least significant bits first).
     * Trailing zero (false) bits are permitted. Zero may be represented by an empty {@code List}.
     *
     * <ul>
     *  <li>Every element in {@code bits} must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits a {@code List} of bits in little-endian order
     * @return The {@code BigInteger} represented by {@code bits}
     */
    public static @NotNull BigInteger fromBits(@NotNull List<Boolean> bits) {
        byte[] bytes = new byte[bits.size() >> 3 + 1]; // if bits.size() is a multiple of 8, we get an extra zero to
        int byteIndex = bytes.length;                  // the left which ensures a positive sign
        for (int i = 0; i < bits.size(); i++) {
            int j = i % 8;
            if (j == 0) byteIndex--;
            if (bits.get(i)) {
                bytes[byteIndex] |= 1 << j;
            }
        }
        return new BigInteger(bytes);
    }

    /**
     * Builds a {@code BigInteger} from a {@code List} of bits in big-endian order (most significant bits first).
     * Leading zero (false) bits are permitted. Zero may be represented by an empty {@code List}.
     *
     * <ul>
     *  <li>Every element in {@code bits} must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param bits a {@code List} of bits in big-endian order
     * @return The {@code BigInteger} represented by {@code bits}
     */
    public static @NotNull BigInteger fromBigEndianBits(@NotNull List<Boolean> bits) {
        byte[] bytes = new byte[bits.size() >> 3 + 1]; // if bits.size() is a multiple of 8, we get an extra zero to
        int byteIndex = bytes.length;                  // the left which ensures a positive sign
        int limit = bits.size() - 1;
        for (int i = 0; i < bits.size(); i++) {
            int j = i % 8;
            if (j == 0) byteIndex--;
            if (bits.get(limit - i)) {
                bytes[byteIndex] |= 1 << j;
            }
        }
        return new BigInteger(bytes);
    }

    /**
     * Returns the digits of a non-negative {@code int}. The {@code Iterable} returned is little-endian; the least-
     * significant digits come first. Zero gives an empty {@code Iterable}. There are no trailing zero digits.
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
    public static @NotNull List<Integer> digits(int base, int n) {
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Integer> digits = new ArrayList<>();
        int log = ceilingLog2(base);
        if (1 << log == base) {
            int mask = base - 1;
            while (n != 0) {
                digits.add(n & mask);
                n >>= log;
            }
        } else {
            int remaining = n;
            while (remaining != 0) {
                digits.add(remaining % base);
                remaining /= base;
            }
        }
        return digits;
    }

    /**
     * Returns the digits of a non-negative {@code BigInteger}. The {@code Iterable} returned is little-endian; the
     * least-significant digits come first. Zero gives an empty {@code Iterable}. There are no trailing zero digits.
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
    public static @NotNull List<BigInteger> digits(@NotNull BigInteger base, @NotNull BigInteger n) {
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<BigInteger> digits = new ArrayList<>();
        if (isPowerOfTwo(base)) {
            int log = ceilingLog2(base);
            BigInteger mask = base.subtract(BigInteger.ONE);
            while (!n.equals(BigInteger.ZERO)) {
                digits.add(n.and(mask));
                n = n.shiftRight(log);
            }
        } else {
            BigInteger remaining = n;
            while (!remaining.equals(BigInteger.ZERO)) {
                digits.add(remaining.mod(base));
                remaining = remaining.divide(base);
            }
        }
        return digits;
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code Iterable} returned is little-
     * endian; the least-significant digits come first. It is exactly {@code n} digits long, and left-padded with zeros
     * if necessary.
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
    public static @NotNull List<Integer> digitsPadded(int length, int base, int n) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Integer> digits = new ArrayList<>();
        int log = ceilingLog2(base);
        if (1 << log == base) {
            int mask = base - 1;
            for (int i = 0; i < length; i++) {
                digits.add(n & mask);
                n >>= log;
            }
        } else {
            for (int i = 0; i < length; i++) {
                digits.add(n % base);
                n /= base;
            }
        }
        return digits;
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code Iterable} returned is little-
     * endian; the least-significant digits come first. It is exactly {@code n} digits long, and left-padded with
     * zeros if necessary.
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
    public static @NotNull List<BigInteger> digitsPadded(int length, @NotNull BigInteger base, @NotNull BigInteger n) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<BigInteger> digits = new ArrayList<>();
        if (isPowerOfTwo(base)) {
            int log = ceilingLog2(base);
            BigInteger mask = base.subtract(BigInteger.ONE);
            for (int i = 0; i < length; i++) {
                digits.add(n.and(mask));
                n = n.shiftRight(log);
            }
        } else {
            BigInteger remaining = n;
            for (int i = 0; i < length; i++) {
                digits.add(remaining.mod(base));
                remaining = remaining.divide(base);
            }
        }
        return digits;
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
    public static @NotNull List<Integer> bigEndianDigits(int base, int n) {
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
    public static @NotNull List<BigInteger> bigEndianDigits(@NotNull BigInteger base, @NotNull BigInteger n) {
        return reverse(digits(base, n));
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code int}. The {@code List} returned is big-endian; the
     * least-significant digits come first. It is exactly {@code n} digits long, and right-padded with zeros if
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
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<Integer> digits = toList(replicate(length, 0));
        int log = ceilingLog2(base);
        if (1 << log == base) {
            int mask = base - 1;
            for (int i = length - 1; i >= 0; i--) {
                digits.set(i, n & mask);
                n >>= log;
            }
        } else {
            int remaining = n;
            for (int i = length - 1; i >= 0; i--) {
                digits.set(i, remaining % base);
                remaining /= base;
            }
        }
        return digits;
    }

    /**
     * Returns the lowest {@code n} digits of a non-negative {@code BigInteger}. The {@code List} returned is
     * big-endian; the least-significant digits come first. It is exactly {@code n} digits long, and right-padded with
     * zeros if necessary.
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
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative. Invalid length: " + length);
        }
        if (lt(base, TWO)) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        List<BigInteger> digits = toList(replicate(length, BigInteger.ZERO));
        if (isPowerOfTwo(base)) {
            int log = ceilingLog2(base);
            BigInteger mask = base.subtract(BigInteger.ONE);
            for (int i = length - 1; i >= 0; i--) {
                digits.set(i, n.and(mask));
                n = n.shiftRight(log);
            }
        } else {
            BigInteger remaining = n;
            for (int i = length - 1; i >= 0; i--) {
                digits.set(i, remaining.mod(base));
                remaining = remaining.divide(base);
            }
        }
        return digits;
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
                String digitsString = Testing.its(digits);
                throw new IllegalArgumentException("Each element of digits must be non-negative. Invalid digit: " +
                        digit + " in " + digitsString);
            } else if (digit >= base) {
                String digitsString = Testing.its(digits);
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
                String digitsString = Testing.its(digits);
                throw new IllegalArgumentException("Each element of digits must be non-negative. Invalid digit: " +
                        digit + " in " + digitsString);
            } else if (ge(digit, base)) {
                String digitsString = Testing.its(digits);
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
     * "(0)" otherwise. In every other case there are no leading zeros.
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
     * "(0)" otherwise. In every other case there are no leading zeros.
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
        boolean bigBase = gt(base, ALPHANUMERIC_COUNT);
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
     * and no leading zeros are allowed unless the digit is 0). The empty {@code String} represents 0. Leading zeros
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
        if (s.isEmpty()) {
            return BigInteger.ZERO;
        }
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
            s = middle(s);
            digits = toList(
                    map(
                            digit -> {
                                Optional<Integer> oi = Readers.readIntegerStrict(digit);
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
     * and no leading zeros are allowed unless the digit is 0). The empty {@code String} represents 0. Leading zeros
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
        if (s.isEmpty()) {
            return BigInteger.ZERO;
        }
        boolean negative = false;
        if (head(s) == '-') {
            s = tail(s);
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            negative = true;
        }
        List<BigInteger> digits;
        if (le(base, ALPHANUMERIC_COUNT)) {
            digits = toList(map(c -> BigInteger.valueOf(fromDigit(c)), fromString(s)));
        } else {
            if (head(s) != '(' || last(s) != ')' || s.contains("()")) {
                throw new IllegalArgumentException("Improperly-formatted String: " + s);
            }
            s = middle(s);
            digits = toList(
                    map(
                            digit -> {
                                Optional<BigInteger> oi = Readers.readBigIntegerStrict(digit);
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
     *  <li>{@code y} cannot be negative and must be less than 2<sup>31</sup>.</li>
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
     * is "typically" about the base-2 logarithm of the first. More precisely, this method is the inverse of
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
    public static @NotNull Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
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
        List<Boolean> xBits = bits(x);
        List<Boolean> yBits = bits(y);
        int xBitSize = xBits.size();
        int yBitSize = yBits.size();
        return fromBits(toList(() -> new Iterator<Boolean>() {
            private int ix = 0;
            private int iy = 0;
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return ix < xBitSize || iy < yBitSize;
            }

            @Override
            public Boolean next() {
                boolean bit;
                if (counter == 0) {
                    bit = iy < yBitSize ? yBits.get(iy) : false;
                    iy++;
                    counter = 2;
                } else {
                    bit = ix < xBitSize ? xBits.get(ix) : false;
                    ix++;
                    counter--;
                }
                return bit;
            }
        }));
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
        List<Boolean> xBits = new ArrayList<>();
        List<Boolean> yBits = new ArrayList<>();
        int counter = 0;
        for (boolean bit : bits(n)) {
            if (counter == 0) {
                yBits.add(bit);
                counter = 2;
            } else {
                xBits.add(bit);
                counter--;
            }
        }
        return new Pair<>(fromBits(xBits), fromBits(yBits));
    }

    /**
     * Bijectively maps a list of natural {@code BigInteger}s to one natural {@code BigInteger} in such a way that the
     * result is O(max({@code xs})<sup>|{@code xs}|</sup>), so the contribution of each element of {@code xs} is
     * approximately equal. The bijection is between the naturals and lists of a fixed size, not between naturals and
     * all lists. The empty list maps to 0. The inverse of this method is
     * {@link mho.wheels.numberUtils.IntegerUtils#demux}.
     *
     * <ul>
     *  <li>No element of {@code xs} can be negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs the list of {@code BigInteger}s
     * @return a {@code BigInteger} generated bijectively from {@code xs}
     */
    public static @NotNull BigInteger mux(@NotNull List<BigInteger> xs) {
        if (xs.isEmpty()) return BigInteger.ZERO;
        int maxBitLength = 0;
        for (BigInteger x : xs) {
            if (x.signum() == -1) {
                throw new ArithmeticException("No element of xs can be negative. Invalid xs: " + xs);
            }
            int bitLength = x.bitLength();
            if (bitLength > maxBitLength) maxBitLength = bitLength;
        }
        int outputBitLength = maxBitLength * xs.size() + 1;
        int byteLength = outputBitLength >> 3;
        if (byteLength << 3 != outputBitLength) {
            byteLength++;
        }
        byte[] bytes = new byte[byteLength];
        int k = xs.size() - 1;
        int j = 0;
        for (int i = byteLength - 1; i >= 0; i--) {
            int b = 0;
            for (int m = 1; m < 256; m <<= 1) {
                if (xs.get(k).testBit(j)) {
                    b |= m;
                }
                if (k == 0) {
                    k = xs.size();
                    j++;
                }
                k--;
            }
            bytes[i] = (byte) b;
        }
        return new BigInteger(bytes);
    }

    /**
     * Bijectively maps one natural {@code BigInteger} to a list of natural {@code BigInteger}s in such a way that
     * every element of the list is "typically" about the same size. More precisely, this method is the inverse of
     * {@link mho.wheels.numberUtils.IntegerUtils#mux}. The bijection is between the naturals and lists of a fixed
     * size, not between naturals and all lists. If {@code size} is 0, the only acceptable {@code n} is 0, which maps
     * to the empty list.
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
        if (size < 0) {
            throw new ArithmeticException("size cannot be negative. Invalid size: " + size);
        }
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(size, BigInteger.ZERO));
        }
        if (size == 0) {
            throw new ArithmeticException("If size is 0, n must also be 0. Invalid n: " + n);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        byte[] bytes = n.toByteArray();
        int length = bytes.length;
        int resultLength = length / size + 2;
        byte[][] demuxedBytes = new byte[size][resultLength];
        int ri = resultLength - 1;
        int rj = 1;
        int rk = size - 1;
        for (int i = length - 1; i >= 0; i--) {
            byte b = bytes[i];
            for (int j = 0; j < 8; j++) {
                if ((b & 1) != 0) {
                    demuxedBytes[rk][ri] |= rj;
                }
                b >>= 1;
                if (rk == 0) {
                    rk = size - 1;
                    rj <<= 1;
                    if (rj == 256) {
                        rj = 1;
                        ri--;
                    }
                } else {
                    rk--;
                }
            }
        }
        List<BigInteger> demuxed = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            demuxed.add(new BigInteger(demuxedBytes[i]));
        }
        return demuxed;
    }
}
