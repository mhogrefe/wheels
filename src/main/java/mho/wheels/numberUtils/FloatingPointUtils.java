package mho.wheels.numberUtils;

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
     * The bit size of a {@code float}'s exponent
     */
    public static final int FLOAT_EXPONENT_WIDTH = 8;

    /**
     * The bit size of a {@code double}'s exponent
     */
    public static final int DOUBLE_EXPONENT_WIDTH = 11;

    /**
     * The bit size of a {@code float}'s fraction
     */
    public static final int FLOAT_FRACTION_WIDTH = 23;

    /**
     * The bit size of a {@code double}'s fraction
     */
    public static final int DOUBLE_FRACTION_WIDTH = 52;

    /**
     * The exponent of the smallest {@code float} value
     */
    public static final int MIN_SUBNORMAL_FLOAT_EXPONENT = Float.MIN_EXPONENT - FLOAT_FRACTION_WIDTH;

    /**
     * The exponent of the smallest {@code double} value
     */
    public static final int MIN_SUBNORMAL_DOUBLE_EXPONENT = Double.MIN_EXPONENT - DOUBLE_FRACTION_WIDTH;

    /**
     * The number of positive, finite {@code float}s, or 2<sup>31</sup>–2<sup>23</sup>–1
     */
    public static final int POSITIVE_FINITE_FLOAT_COUNT = Float.floatToIntBits(Float.MAX_VALUE);

    /**
     * The number of positive, finite {@code double}s, or 2<sup>63</sup>–2<sup>52</sup>–1
     */
    public static final long POSITIVE_FINITE_DOUBLE_COUNT = Double.doubleToRawLongBits(Double.MAX_VALUE);

    /**
     * {@link Float#MAX_VALUE} divided by {@link Float#MIN_VALUE}, or 2<sup>277</sup>–2<sup>253</sup>
     */
    public static final @NotNull BigInteger SCALED_UP_MAX_FLOAT = scaleUp(Float.MAX_VALUE).get();

    /**
     * {@link Double#MAX_VALUE} divided by {@link Double#MIN_VALUE}, or 2<sup>2098</sup>–2<sup>2045</sup>
     */
    public static final @NotNull BigInteger SCALED_UP_MAX_DOUBLE = scaleUp(Double.MAX_VALUE).get();

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
        return Double.doubleToLongBits(d) == 0L;
    }

    /**
     * Returns the next-largest {@code float} after {@code f}. The successor of {@code -Float.MIN_VALUE} is negative
     * zero. The successor of negative infinity is {@code -Float.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code f} may not be {@code NaN} or {@code Infinity}.</li>
     *  <li>The result may be any {@code float} other than 0.0, {@code NaN}, or {@code -Infinity}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return min{g|g{@literal >}{@code f}}
     */
    public static float successor(float f) {
        if (Float.isNaN(f) || f > 0 && Float.isInfinite(f)) {
            throw new ArithmeticException(f + " may not be NaN or Infinity.");
        }
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
        if (Float.isNaN(f) || f < 0 && Float.isInfinite(f)) {
            throw new ArithmeticException(f + " may not be NaN or -Infinity.");
        }
        if (f == 0.0f) return -Float.MIN_VALUE;
        int floatBits = Float.floatToIntBits(f);
        return Float.intBitsToFloat(f > 0 ? floatBits - 1 : floatBits + 1);
    }

    /**
     * Returns the next-largest {@code double} after {@code d}. The successor of {@code -Double.MIN_VALUE} is negative
     * zero. The successor of negative infinity is {@code -Double.MAX_VALUE}.
     *
     * <ul>
     *  <li>{@code d} may not be {@code NaN} or {@code Infinity}.</li>
     *  <li>The result may be any {@code double} other than 0.0, {@code NaN} or {@code -Infinity}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return min{e|e{@literal >}{@code d}}
     */
    public static double successor(double d) {
        if (Double.isNaN(d) || d > 0 && Double.isInfinite(d)) {
            throw new ArithmeticException(d + " may not be NaN or Infinity.");
        }
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
        if (Double.isNaN(d) || d < 0 && Double.isInfinite(d)) {
            throw new ArithmeticException(d + " may not be NaN or -Infinity.");
        }
        if (d == 0.0) return -Double.MIN_VALUE;
        long doubleBits = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(d > 0 ? doubleBits - 1 : doubleBits + 1);
    }

    /**
     * Maps a {@code float} to an {@code int} in a way that adjacent {@code float}s correspond to adjacent {@code int}s
     * and ordering is preserved. The {@code float} may be infinite, but may not be {@code NaN}. Positive and negative
     * zeros both map to 0.
     *
     * <ul>
     *  <li>{@code f} cannot be {@code NaN}.</li>
     *  <li>The result has an absolute value less than or equal to 2<sup>31</sup>–2<sup>23</sup>.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return an {@code int} value that may be used to represent {@code f}
     */
    public static int toOrderedRepresentation(float f) {
        if (Float.isNaN(f)) {
            throw new ArithmeticException("f cannot be NaN.");
        }
        if (f >= 0) {
            return Float.floatToIntBits(absNegativeZeros(f));
        } else {
            return -Float.floatToIntBits(-f);
        }
    }

    /**
     * The inverse of {@link FloatingPointUtils#toOrderedRepresentation(float)}.
     *
     * <ul>
     *  <li>{@code n} must have an absolute value less than or equal to 2<sup>31</sup>–2<sup>23</sup>.</li>
     *  <li>The result is not {@code NaN}.</li>
     * </ul>
     *
     * @param n an {@code int} being used to represent a {@code float}
     * @return the {@code float}
     */
    public static float floatFromOrderedRepresentation(int n) {
        float f = n >= 0 ? Float.intBitsToFloat(n) : -Float.intBitsToFloat(-n);
        if (Float.isNaN(f)) {
            throw new ArithmeticException("n must have an absolute value less than or equal to 2^31 - 2^23. Invalid" +
                    " n: " + n);
        }
        return f;
    }

    /**
     * Maps a {@code double} to an {@code long} in a way that adjacent {@code double}s correspond to adjacent
     * {@code long}s and ordering is preserved. The {@code double} may be infinite, but may not be {@code NaN}.
     * Positive and negative zeros both map to 0.
     *
     * <ul>
     *  <li>{@code d} cannot be {@code NaN}.</li>
     *  <li>The result has an absolute value less than or equal to 2<sup>63</sup>–2<sup>52</sup>.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return an {@code long} value that may be used to represent {@code f}
     */
    public static long toOrderedRepresentation(double d) {
        if (Double.isNaN(d)) {
            throw new ArithmeticException("d cannot be NaN.");
        }
        if (d >= 0) {
            return Double.doubleToLongBits(absNegativeZeros(d));
        } else {
            return -Double.doubleToLongBits(-d);
        }
    }

    /**
     * The inverse of {@link FloatingPointUtils#toOrderedRepresentation(double)}.
     *
     * <ul>
     *  <li>{@code n} must have an absolute value less than or equal to 2<sup>63</sup>–2<sup>52</sup>.</li>
     *  <li>The result is not {@code NaN}.</li>
     * </ul>
     *
     * @param n a {@code long} being used to represent a {@code double}
     * @return the {@code double}
     */
    public static double doubleFromOrderedRepresentation(long n) {
        double d = n >= 0 ? Double.longBitsToDouble(n) : -Double.longBitsToDouble(-n);
        if (Double.isNaN(d)) {
            throw new ArithmeticException("n must have an absolute value less than or equal to 2^63 - 2^52. Invalid" +
                    " n: " + n);
        }
        return d;
    }

    /**
     * Constructs a {@code Float} from its mantissa and exponent. The {@code Float} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code Float} (in particular, the mantissa must be zero or odd), an empty {@code Optional} is returned.
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
        if (mantissa == 0) {
            if (exponent != 0) {
                return Optional.empty();
            }
        } else if ((mantissa & 1) == 0) {
            return Optional.empty();
        }
        Pair<Float, Float> range = BinaryFraction.of(BigInteger.valueOf(mantissa), exponent).floatRange();
        return range.a.equals(range.b) ? Optional.of(range.a) : Optional.<Float>empty();
    }

    /**
     * Constructs a {@code Double} from its mantissa and exponent. The {@code Double} is equal to
     * {@code mantissa}×2<sup>{@code exponent}</sup>. If the given mantissa and exponent do not form a valid
     * {@code Double} (in particular, the mantissa must be zero or odd), an empty {@code Optional} is returned.
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
        if (mantissa == 0) {
            if (exponent != 0) {
                return Optional.empty();
            }
        } else if ((mantissa & 1) == 0) {
            return Optional.empty();
        }
        Pair<Double, Double> range = BinaryFraction.of(BigInteger.valueOf(mantissa), exponent).doubleRange();
        return range.a.equals(range.b) ? Optional.of(range.a) : Optional.<Double>empty();
    }

    /**
     * Extracts a {@code float}'s mantissa and exponent: an odd {@code Integer} <i>m</i> and an {@code Integer}
     * <i>e</i> such that the {@code float} is exactly equal to <i>m</i>×2<sup><i>e</i></sup>. Zero is treated
     * specially–its mantissa and exponent are both zero. Positive and negative zeros are treated identically;
     * {@code NaN} and infinities yield an empty result.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>The result is either empty or a pair of {@code Integer}s, neither null, that form the mantissa and exponent
     *  of some {@code float} according to the definition above.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return the {@code float}'s mantissa and exponent
     */
    public static @NotNull Optional<Pair<Integer, Integer>> toMantissaAndExponent(float f) {
        Optional<BinaryFraction> obf = BinaryFraction.of(f);
        if (!obf.isPresent()) return Optional.empty();
        BinaryFraction bf = obf.get();
        return Optional.of(new Pair<>(bf.getMantissa().intValueExact(), bf.getExponent()));
    }

    /**
     * Extracts a {@code double}'s mantissa and exponent: an odd {@code Long} <i>m</i> and an {@code Integer} <i>e</i>
     * such that the {@code double} is exactly equal to <i>m</i>×2<sup><i>e</i></sup>. Zero is treated specially–its
     * mantissa and exponent are both zero. Positive and negative zeros are treated identically; {@code NaN} and
     * infinities yield an empty result.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>The result is either empty or a pair of a {@code Long} and an {@code Integer}, neither null, that form the
     *  mantissa and exponent of some {@code double} according to the definition above.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return the {@code double}'s mantissa and exponent
     */
    public static @NotNull Optional<Pair<Long, Integer>> toMantissaAndExponent(double d) {
        Optional<BinaryFraction> obf = BinaryFraction.of(d);
        if (!obf.isPresent()) return Optional.empty();
        BinaryFraction bf = obf.get();
        return Optional.of(new Pair<>(bf.getMantissa().longValueExact(), bf.getExponent()));
    }

    /**
     * If {@code f} is -0.0f, return 0.0f; otherwise, return {@code f}.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>The result is not negative zero.</li>
     * </ul>
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
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>The result is not negative zero.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return a {@code double} equal to {@code d} and not -0.0
     */
    public static double absNegativeZeros(double d) {
        return d == 0.0 ? 0.0 : d;
    }

    /**
     * Return how many multiples of {@link Float#MIN_VALUE} a given {@code float} is. Returns an empty result if
     * {@code f} is infinite or {@code NaN}.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>The result is empty or a {@code BigInteger} whose absolute value is less than or equal to
     *  {@link FloatingPointUtils#SCALED_UP_MAX_FLOAT}.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return {@code f}/{@code Float.MIN_VALUE}
     */
    public static @NotNull Optional<BigInteger> scaleUp(float f) {
        return BinaryFraction.of(f).map(bf -> bf.shiftRight(MIN_SUBNORMAL_FLOAT_EXPONENT).bigIntegerValueExact());
    }

    /**
     * Return how many multiples of {@link Double#MIN_VALUE} a given {@code double} is. Returns an empty result if
     * {@code d} is infinite or {@code NaN}.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>The result is empty or a {@code BigInteger} whose absolute value is less than or equal to
     *  {@link FloatingPointUtils#SCALED_UP_MAX_DOUBLE}.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return {@code d}/{@code Double.MIN_VALUE}
     */
    public static @NotNull Optional<BigInteger> scaleUp(double d) {
        return BinaryFraction.of(d).map(bf -> bf.shiftRight(MIN_SUBNORMAL_DOUBLE_EXPONENT).bigIntegerValueExact());
    }

    /**
     * Converts a {@code float} to a {@code String} in the same way as {@link Float#toString()}, but with trailing
     * {@code ".0"}s removed, and any occurrence of {@code ".0E"} replaced by {@code "E"}.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>See tests and demos for example results.</li>
     * </ul>
     *
     * @param f a {@code float}
     * @return a compact {@code String} representation of {@code f}
     */
    public static @NotNull String toStringCompact(float f) {
        String s = Float.toString(f);
        int exponentIndex = s.indexOf('E');
        if (exponentIndex == -1) {
            return s.endsWith(".0") ? s.substring(0, s.length() - 2) : s;
        } else {
            String beforeExponent = s.substring(0, exponentIndex);
            if (beforeExponent.endsWith(".0")) {
                String afterExponent = s.substring(exponentIndex + 1);
                beforeExponent = beforeExponent.substring(0, beforeExponent.length() - 2);
                return beforeExponent + 'E' + afterExponent;
            } else {
                return s;
            }
        }
    }

    /**
     * Converts a {@code double} to a {@code String} in the same way as {@link Double#toString()}, but with trailing
     * {@code ".0"}s removed, and any occurrence of {@code ".0E"} replaced by {@code "E"}.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>See tests and demos for example results.</li>
     * </ul>
     *
     * @param d a {@code double}
     * @return a compact {@code String} representation of {@code d}
     */
    public static @NotNull String toStringCompact(double d) {
        String s = Double.toString(d);
        int exponentIndex = s.indexOf('E');
        if (exponentIndex == -1) {
            return s.endsWith(".0") ? s.substring(0, s.length() - 2) : s;
        } else {
            String beforeExponent = s.substring(0, exponentIndex);
            if (beforeExponent.endsWith(".0")) {
                String afterExponent = s.substring(exponentIndex + 1);
                beforeExponent = beforeExponent.substring(0, beforeExponent.length() - 2);
                return beforeExponent + 'E' + afterExponent;
            } else {
                return s;
            }
        }
    }
}
