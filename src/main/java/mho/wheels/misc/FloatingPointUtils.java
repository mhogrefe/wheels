package mho.wheels.misc;

import mho.wheels.math.BinaryFraction;
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

    /**
     * Constructs a {@code Float} from its mantissa and exponent. The {@code Float} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code Float}, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code mantissa} may be any {@code int}.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>The result is not {@code NaN}, negative zero, or infinite. May be empty.</li>
     * </ul>
     *
     * @param mantissa a {@code Float}'s mantissa
     * @param exponent a {@code Float}'s exponent
     * @return the {@code Float} with the given mantissa and exponent
     */
    public static @NotNull Optional<Float> floatFromMantissaAndExponent(int mantissa, int exponent) {
        BinaryFraction bf = BinaryFraction.of(BigInteger.valueOf(mantissa), exponent);
        if (!bf.getMantissa().equals(BigInteger.valueOf(mantissa)) || bf.getExponent() != exponent) {
            return Optional.empty();
        }
        Pair<Float, Float> range = bf.floatRange();
        return range.a.equals(range.b) ? Optional.of(range.a) : Optional.<Float>empty();
    }

    /**
     * Constructs a {@code Double} from its mantissa and exponent. The {@code Double} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code Double}, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code mantissa} may be any {@code long}.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>The result is not {@code NaN}, negative zero, or infinite. May be empty.</li>
     * </ul>
     *
     * @param mantissa a {@code Double}'s mantissa
     * @param exponent a {@code Double}'s exponent
     * @return the {@code Double} with the given mantissa and exponent
     */
    public static @NotNull Optional<Double> doubleFromMantissaAndExponent(long mantissa, int exponent) {
        BinaryFraction bf = BinaryFraction.of(BigInteger.valueOf(mantissa), exponent);
        if (!bf.getMantissa().equals(BigInteger.valueOf(mantissa)) || bf.getExponent() != exponent) {
            return Optional.empty();
        }
        Pair<Double, Double> range = bf.doubleRange();
        return range.a.equals(range.b) ? Optional.of(range.a) : Optional.<Double>empty();
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
