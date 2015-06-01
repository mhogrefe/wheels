package mho.wheels.misc;

import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Methods for manipulating and analyzing {@link float}s and {@link double}s.
 */
public final strictfp class FloatingPointUtils {
    /**
     * Disallow instantiation
     */
    private FloatingPointUtils() {}

    /**
     * Determines whether a {@code float} is negative zero.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return whether {@code f} is -0.0f
     */
    public static boolean isNegativeZero(float f) {
        return Float.floatToIntBits(f) == Integer.MIN_VALUE;
    }

    /**
     * Determines whether a {@code double} is negative zero.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return whether {@code d} is -0.0
     */
    public static boolean isNegativeZero(double d) {
        return Double.doubleToLongBits(d) == Long.MIN_VALUE;
    }

    /**
     * Determines whether a {@code float} is positive zero.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return whether {@code f} is 0.0f
     */
    public static boolean isPositiveZero(float f) {
        return Float.floatToIntBits(f) == 0;
    }

    /**
     * Determines whether a {@code double} is positive zero.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return whether {@code d} is 0.0
     */
    public static boolean isPositiveZero(double d) {
        return Double.doubleToLongBits(d) == 0;
    }

    /**
     * Returns the next-largest {@code float} after {@code f}. The successor of {@code -Float.MIN_VALUE} is negative
     * zero. The successor of negative infinity is {@code -Float.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code f} may not be {@code NaN} or {@code +Infinity}.</li>
     *  <li>The result may be any {@code float} other than 0.0, {@code NaN}, or {@code -Infinity}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return min{g|g{@literal >}{@code f}}
     */
    public static float successor(float f) {
        if (Float.isNaN(f) || f > 0 && Float.isInfinite(f))
            throw new ArithmeticException(f + " does not have a successor");
        if (f == 0.0f) return Float.MIN_VALUE;
        int floatBits = Float.floatToIntBits(f);
        return Float.intBitsToFloat(f > 0 ? floatBits + 1 : floatBits - 1);
    }

    /**
     * Returns the next-smallest {@code float} before {@code f}. The predecessor of positive infinity is
     * {@code Float.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code f} may not be {@code NaN} or {@code -Infinity}.</li>
     *  <li>The result may be any {@code float} other than negative zero, {@code NaN}, or {@code +Infinity}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return max{g|g{@literal <}{@code f}}
     */
    public static float predecessor(float f) {
        if (Float.isNaN(f) || f < 0 && Float.isInfinite(f))
            throw new ArithmeticException(f + " does not have a predecessor");
        if (f == 0.0f) return -Float.MIN_VALUE;
        int floatBits = Float.floatToIntBits(f);
        return Float.intBitsToFloat(f > 0 ? floatBits - 1 : floatBits + 1);
    }

    /**
     * Returns the next-largest {@code double} after {@code d}. The successor of {@code -Double.MIN_VALUE} is negative
     * zero. The successor of negative infinity is {@code -Double.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code d} may not be {@code NaN} or {@code +Infinity}.</li>
     *  <li>The result may be any {@code double} other than 0.0, {@code NaN} or {@code -Infinity}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return min{e|e{@literal >}{@code d}}
     */
    public static double successor(double d) {
        if (Double.isNaN(d) || d > 0 && Double.isInfinite(d))
            throw new ArithmeticException(d + " does not have a successor");
        if (d == 0.0) return Double.MIN_VALUE;
        long doubleBits = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(d > 0 ? doubleBits + 1 : doubleBits - 1);
    }

    /**
     * Returns the next-smallest {@code double} before {@code d}. The predecessor of positive infinity is
     * {@code Double.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code d} may not be {@code NaN} or {@code -Infinity}.</li>
     *  <li>The result may be any {@code double} other than negative zero, {@code NaN}, or {@code +Infinity}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return max{e|e{@literal <}{@code d}}
     */
    public static double predecessor(double d) {
        if (Double.isNaN(d) || d < 0 && Double.isInfinite(d))
            throw new ArithmeticException(d + " does not have a predecessor");
        if (d == 0.0) return -Double.MIN_VALUE;
        long doubleBits = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(d > 0 ? doubleBits - 1 : doubleBits + 1);
    }

    private static @NotNull Pair<BigInteger, Integer> shift(@NotNull BigInteger mantissa, int exponent, int bits) {
        if (mantissa.equals(BigInteger.ZERO)) {
            return new Pair<>(BigInteger.ZERO, 0);
        }
        int trailingZeroes = mantissa.getLowestSetBit();
        if (trailingZeroes != 0) {
            mantissa = mantissa.shiftRight(trailingZeroes);
            exponent += trailingZeroes;
        }
        return new Pair<>(mantissa, exponent + bits);
    }

    private static @NotNull Pair<BigInteger, Integer> subOne(@NotNull BigInteger mantissa, int exponent) {
        if (exponent <= 0) {
            BigInteger newMantissa = mantissa.subtract(BigInteger.ONE.shiftLeft(-exponent));
            return newMantissa.equals(BigInteger.ZERO) ?
                    new Pair<>(BigInteger.ZERO, 0) :
                    new Pair<>(newMantissa, exponent);
        } else {
            return new Pair<>(mantissa.shiftLeft(exponent).subtract(BigInteger.ONE), 0);
        }
    }

    /**
     * Constructs a {@code float} from its mantissa and exponent. The {@code float} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code float}, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code mantissa} may be any {@code int}.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>The result is not {@code NaN}, negative zero, or infinite.</li>
     * </ul>
     *
     * @param mantissa a {@code float}'s mantissa
     * @param exponent a {@code float}'s exponent
     * @return The {@code float} with the given expoent and mantissa
     */
    public static @NotNull Optional<Float> floatFromME(int mantissa, int exponent) {
        if ((mantissa & 1) == 0) {
            return Optional.empty();
        }
        boolean sign = mantissa > 0;
        BigInteger bigMantissa = BigInteger.valueOf(mantissa);
        int bitLength = bigMantissa.bitLength();
        int rawExp = bitLength + exponent - 1;
        if (rawExp < -149) {
            return Optional.empty();
        }
        BigInteger rawMantissa;
        if (rawExp < -126) {
            int padding = exponent + 149;
            if (padding < 0) return Optional.empty();
            rawMantissa = bigMantissa.shiftLeft(padding);
            rawExp = 0;
        } else {
            int padding = 24 - bitLength;
            if (padding < 0) return Optional.empty();
            rawMantissa = bigMantissa.clearBit(bitLength - 1).shiftLeft(padding);
            if (rawExp < -126 || rawExp > 127) return Optional.empty();
            rawExp += 127;
        }
        int bits = rawExp;
        bits <<= 23;
        bits |= rawMantissa.intValueExact();
        float f = Float.intBitsToFloat(bits);
        return Optional.of(sign ? f : -f);
    }

    /**
     * Constructs a {@code double} from its mantissa and exponent. The {@code double} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code double}, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code mantissa} may be any {@code long}.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>The result is not {@code NaN}, negative zero, or infinite.</li>
     * </ul>
     *
     * @param mantissa a {@code double}'s mantissa
     * @param exponent a {@code double}'s exponent
     * @return The {@code float} with the given expoent and mantissa
     */
    public static @NotNull Optional<Double> doubleFromME(long mantissa, int exponent) {
        if ((mantissa & 1) == 0) {
            return Optional.empty();
        }
        boolean sign = mantissa > 0;
        BigInteger bigMantissa = BigInteger.valueOf(mantissa);
        int bitLength = bigMantissa.bitLength();
        int rawExp = bitLength + exponent - 1;
        if (rawExp < -1074) {
            return Optional.empty();
        }
        BigInteger rawMantissa;
        if (rawExp < -1022) {
            int padding = exponent + 1074;
            if (padding < 0) return Optional.empty();
            rawMantissa = bigMantissa.shiftLeft(padding);
            rawExp = 0;
        } else {
            int padding = 53 - bitLength;
            if (padding < 0) return Optional.empty();
            rawMantissa = bigMantissa.clearBit(bitLength - 1).shiftLeft(padding);
            if (rawExp < -1022 || rawExp > 1023) return Optional.empty();
            rawExp += 1023;
        }
        long bits = rawExp;
        bits <<= 52;
        bits |= rawMantissa.longValueExact();
        double d = Double.longBitsToDouble(bits);
        return Optional.of(sign ? d : -d);
    }

    /**
     * If {@code f} is -0.0f, return 0.0f; otherwise, return {@code f}.
     *
     * @param f a {@code float}
     * @return a {@code float} equal to {@code f} and not -0.0f
     */
    public static float absNegativeZeros(float f) {
        return f == 0.0f ? 0.0f : f;
    }

    /**
     * If {@code d} is -0.0, return 0.0; otherwise, return {@code d}.
     *
     * @param d a {@code double}
     * @return a {@code double} equal to {@code d} and not -0.0
     */
    public static double absNegativeZeros(double d) {
        return d == 0.0 ? 0.0 : d;
    }
}
