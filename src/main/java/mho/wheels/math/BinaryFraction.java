package mho.wheels.math;

import mho.wheels.misc.BigDecimalUtils;
import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.misc.Readers;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>The {@code BinaryFraction} class uniquely represents rational numbers whose denominator is a power of 2. Every
 * such number is either zero or an equal to an odd integer (the mantissa) times 2 raised to an integer (the exponent).
 * Zero is considered to have a mantissa of zero (this is the only case when the mantissa is even) and an exponent of
 * zero.
 *
 * <p>There is only one instance of {@code ZERO} and one instance of {@code ONE}, so these may be compared with other
 * {@code BigInteger}s using {@code ==}.
 *
 * <p>This class is immutable.
 */
public class BinaryFraction implements Comparable<BinaryFraction> {
    /**
     * 0
     */
    public static final @NotNull BinaryFraction ZERO = new BinaryFraction(BigInteger.ZERO, 0);

    /**
     * 1
     */
    public static final @NotNull BinaryFraction ONE = new BinaryFraction(BigInteger.ONE, 0);

    /**
     * The smallest positive float value, or 2<sup>–149</sup>
     */
    public static final @NotNull BinaryFraction SMALLEST_FLOAT = of(Float.MIN_VALUE).get();

    /**
     * The largest subnormal float value, or (2<sup>23</sup>–1)/2<sup>149</sup>
     */
    public static final @NotNull BinaryFraction LARGEST_SUBNORMAL_FLOAT =
            of(FloatingPointUtils.predecessor(Float.MIN_NORMAL)).get();

    /**
     * The smallest positive normal float value, or 2<sup>–126</sup>
     */
    public static final @NotNull BinaryFraction SMALLEST_NORMAL_FLOAT = of(Float.MIN_NORMAL).get();

    /**
     * The largest finite float value, or 2<sup>128</sup>–2<sup>104</sup>
     */
    public static final @NotNull BinaryFraction LARGEST_FLOAT = of(Float.MAX_VALUE).get();

    /**
     * The smallest positive double value, or 2<sup>–1074</sup>
     */
    public static final @NotNull BinaryFraction SMALLEST_DOUBLE = of(Double.MIN_VALUE).get();

    /**
     * The largest subnormal double value, or (2<sup>52</sup>–1)/2<sup>1074</sup>
     */
    public static final @NotNull BinaryFraction LARGEST_SUBNORMAL_DOUBLE =
            of(FloatingPointUtils.predecessor(Double.MIN_NORMAL)).get();

    /**
     * The smallest positive normal double value, or 2<sup>–1022</sup>
     */
    public static final @NotNull BinaryFraction SMALLEST_NORMAL_DOUBLE = of(Double.MIN_NORMAL).get();

    /**
     * The largest finite double value, or 2<sup>1024</sup>–2<sup>971</sup>
     */
    public static final @NotNull BinaryFraction LARGEST_DOUBLE = of(Double.MAX_VALUE).get();

    /**
     * If {@code this} is 0, then 0; otherwise, the unique odd integer equal to {@code this} times an integer power of
     * 2
     */
    private @NotNull BigInteger mantissa;

    /**
     * log<sub>2</sub>({@code this}/{@code mantissa})
     */
    private int exponent;

    /**
     * Private constructor; assumes arguments are valid.
     *
     * <ul>
     *  <li>{@code mantissa} is odd or zero.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>If {@code mantissa} is zero, {@code exponent} must also be zero.</li>
     *  <li>Any {@code BinaryFraction} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param mantissa the mantissa
     * @param exponent the exponent
     */
    private BinaryFraction(@NotNull BigInteger mantissa, int exponent) {
        this.mantissa = mantissa;
        this.exponent = exponent;
    }

    /**
     * Returns this {@code BinaryFraction}'s mantissa.
     *
     * <ul>
     *  <li>The result is odd or zero.</li>
     * </ul>
     *
     * @return the mantissa
     */
    public @NotNull BigInteger getMantissa() {
        return mantissa;
    }

    /**
     * Returns this {@code BinaryFraction}'s exponent.
     *
     * <ul>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @return the exponent
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * Creates a {@code BinaryFraction} from a mantissa and an exponent. Reduces arguments if necessary.
     *
     * <ul>
     *  <li>{@code mantissa} cannot be null.</li>
     *  <li>{@code exponent} may be any {@code int}.</li>
     *  <li>If {@code mantissa} is nonzero, the sum of {@code exponent} and the number of trailing zero bits of
     *  {@code mantissa} must be less than 2<pow>31</pow>.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param mantissa the mantissa
     * @param exponent the exponent
     * @return the {@code BinaryFraction} corresponding to {@code mantissa}×2<sup>{@code exponent}</sup>
     */
    public static @NotNull BinaryFraction of(@NotNull BigInteger mantissa, int exponent) {
        if (mantissa.equals(BigInteger.ZERO)) return ZERO;
        int trailingZeroes = mantissa.getLowestSetBit();
        if ((long) exponent + trailingZeroes >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The sum of exponent and the number of trailing zero bits of mantissa" +
                    " must be less than 2^31. exponent is " + exponent + " and mantissa is " + mantissa + ".");
        }
        if (trailingZeroes != 0) {
            mantissa = mantissa.shiftRight(trailingZeroes);
            exponent += trailingZeroes;
        }
        return mantissa.equals(BigInteger.ONE) && exponent == 0 ? ONE : new BinaryFraction(mantissa, exponent);
    }

    /**
     * Creates a {@code BinaryFraction} from a {@code BigInteger}.
     *
     * <ul>
     *  <li>{@code n} cannot be null.</li>
     *  <li>The result is an integral {@code BinaryFraction}.</li>
     * </ul>
     *
     * @param n the {@code BigInteger}
     * @return the {@code BinaryFraction} equal to {@code n}
     */
    public static @NotNull BinaryFraction of(@NotNull BigInteger n) {
        return of(n, 0);
    }

    /**
     * Creates a {@code BinaryFraction} from an {@code int}.
     *
     * <ul>
     *  <li>{@code n} cannot be null.</li>
     *  <li>The result is an integral {@code BinaryFraction} x satisfying
     *  –2<sup>31</sup>≤x{@literal <}2<sup>31</sup>.</li>
     * </ul>
     *
     * @param n the {@code int}
     * @return the {@code BinaryFraction} equal to {@code n}
     */
    public static @NotNull BinaryFraction of(int n) {
        return of(BigInteger.valueOf(n), 0);
    }

    /**
     * Creates a {@code BinaryFraction} from a {@code float}. No rounding occurs; the {@code Rational} has exactly the
     * same value as the {@code float}. For example, {@code of(1.0f/3.0f)} yields 11184811 >> 25. Returns empty if the
     * {@code float} is {@code Infinity}, {@code -Infinity}, or {@code NaN}.
     *
     * <ul>
     *  <li>{@code f} may be any {@code float}.</li>
     *  <li>
     *   The result is empty or a {@code BinaryFraction} that may be exactly represented as a {@code float}. Here are
     *   some, but not all, of the conditions on the result:
     *   <ul>
     *    <li>The absolute value of {@code exponent} less than or equal to 149.</li>
     *    <li>The absolute value of {@code mantissa} is less than to 2<sup>24</sup>.</li>
     *   </ul>
     *  </li>
     * </ul>
     *
     * @param f the {@code float}
     * @return the {@code BinaryFraction} equal to {@code f}
     */
    public static @NotNull Optional<BinaryFraction> of(float f) {
        if (f == 0.0f) return Optional.of(ZERO);
        if (f == 1.0f) return Optional.of(ONE);
        if (Float.isInfinite(f) || Float.isNaN(f)) return Optional.empty();
        boolean isPositive = f > 0.0f;
        if (!isPositive) f = -f;
        int bits = Float.floatToIntBits(f);
        int exponent = bits >> 23 & ((1 << 8) - 1);
        int mantissa = bits & ((1 << 23) - 1);
        if (exponent == 0) {
            exponent = -149;
        } else {
            mantissa += 1 << 23;
            exponent -= 150;
        }
        return Optional.of(of(BigInteger.valueOf(isPositive ? mantissa : -mantissa), exponent));
    }

    /**
     * Creates a {@code BinaryFraction} from a {@code double}. No rounding occurs; the {@code Rational} has exactly the
     * same value as the {@code double}. For example, {@code of(1.0/3.0)} yields 6004799503160661 >> 54. Returns empty
     * if the {@code double} is {@code Infinity}, {@code -Infinity}, or {@code NaN}.
     *
     * <ul>
     *  <li>{@code d} may be any {@code double}.</li>
     *  <li>
     *   The result is empty or a {@code BigInteger} that may be exactly represented as a {@code double}. Here are
     *   some, but not all, of the conditions on the result:
     *   <ul>
     *    <li>The absolute value of {@code exponent} less than or equal to 1074.</li>
     *    <li>The absolute value of {@code mantissa} is less than to 2<sup>53</sup>.</li>
     *   </ul>
     *  </li>
     * </ul>
     *
     * @param d the {@code double}
     * @return the {@code BinaryFraction} equal to {@code d}
     */
    public static @NotNull Optional<BinaryFraction> of(double d) {
        if (d == 0.0) return Optional.of(ZERO);
        if (d == 1.0) return Optional.of(ONE);
        if (Double.isInfinite(d) || Double.isNaN(d)) return Optional.empty();
        boolean isPositive = d > 0.0f;
        if (!isPositive) d = -d;
        long bits = Double.doubleToLongBits(d);
        int exponent = (int) (bits >> 52 & ((1 << 11) - 1));
        long mantissa = bits & ((1L << 52) - 1);
        if (exponent == 0) {
            exponent = -1074;
        } else {
            mantissa += 1L << 52;
            exponent -= 1075;
        }
        return Optional.of(of(BigInteger.valueOf(isPositive ? mantissa : -mantissa), exponent));
    }

    /**
     * Converts {@code this} to a {@code BigDecimal} with full precision.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>The result is a {@code BigDecimal} with minimal scale. That is, the scale is the smallest non-negative n
     *  such that {@code this}×10<sup>n</sup> is an integer.</li>
     * </ul>
     *
     * @return the {@code BigDecimal} equal to {@code this}
     */
    public @NotNull BigDecimal bigDecimalValue() {
        return BigDecimalUtils.shiftLeft(new BigDecimal(mantissa), exponent);
    }

    /**
     * Determines whether {@code this} is integral.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @return whether this is an integer
     */
    public boolean isInteger() {
        return exponent >= 0;
    }

    /**
     * Returns the sum of {@code this} and {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>{@code that} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param that the {@code BinaryFraction} added to {@code this}
     * @return {@code this}+{@code that}
     */
    public @NotNull BinaryFraction add(@NotNull BinaryFraction that) {
        if (this == ZERO) return that;
        if (that == ZERO) return this;
        BigInteger sumMantissa;
        int sumExponent;
        switch (Ordering.compare(exponent, that.exponent)) {
            case EQ:
                return of(mantissa.add(that.mantissa), exponent);
            case LT:
                sumMantissa = that.mantissa.shiftLeft(that.exponent - exponent).add(mantissa);
                sumExponent = exponent;
                break;
            case GT:
                sumMantissa = mantissa.shiftLeft(exponent - that.exponent).add(that.mantissa);
                sumExponent = that.exponent;
                break;
            default: throw new IllegalStateException("unreachable");
        }
        return sumMantissa.equals(BigInteger.ONE) && exponent == 0 ?
                ONE :
                new BinaryFraction(sumMantissa, sumExponent);
    }

    /**
     * Returns the negative of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @return –{@code this}
     */
    public @NotNull BinaryFraction negate() {
        if (this == ZERO) return ZERO;
        if (mantissa.equals(BigInteger.valueOf(-1)) && exponent == 0) return ONE;
        return new BinaryFraction(mantissa.negate(), exponent);
    }

    /**
     * Returns the absolute value of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>The result is a non-negative {@code BinaryFraction}.</li>
     * </ul>
     *
     * @return |{@code this}|
     */
    public @NotNull BinaryFraction abs() {
        return signum() == -1 ? negate() : this;
    }

    /**
     * Returns the sign of {@code this}: 1 if positive, –1 if negative, 0 if equal to 0.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>The result is –1, 0, or 1.</li>
     * </ul>
     *
     * @return sgn({@code this})
     */
    public int signum() {
        return mantissa.signum();
    }

    /**
     * Returns the difference of {@code this} and {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>{@code that} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param that the {@code BinaryFraction} subtracted from {@code this}
     * @return {@code this}–{@code that}
     */
    public @NotNull BinaryFraction subtract(@NotNull BinaryFraction that) {
        if (this == ZERO) return that.negate();
        if (that == ZERO) return this;
        BigInteger differenceMantissa;
        int differenceExponent;
        switch (Ordering.compare(exponent, that.exponent)) {
            case EQ:
                return of(mantissa.subtract(that.mantissa), exponent);
            case LT:
                differenceMantissa = that.mantissa.shiftLeft(that.exponent - exponent).subtract(mantissa);
                differenceExponent = exponent;
                break;
            case GT:
                differenceMantissa = mantissa.shiftLeft(exponent - that.exponent).subtract(that.mantissa);
                differenceExponent = that.exponent;
                break;
            default: throw new IllegalStateException("unreachable");
        }
        return differenceMantissa.equals(BigInteger.ONE) && exponent == 0 ?
                ONE :
                new BinaryFraction(differenceMantissa, differenceExponent);
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Object} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        BinaryFraction bf = (BinaryFraction) that;
        return exponent == bf.exponent && mantissa.equals(bf.mantissa);
    }

    /**
     * Calculates the hash code of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code.
     */
    @Override
    public int hashCode() {
        return 31 * mantissa.hashCode() + exponent;
    }

    /**
     * Compares {@code this} to {@code that}, returning 1, –1, or 0 if the answer is "greater than", "less than", or
     * "equal to", respectively.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>{@code that} cannot be null.</li>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param that The {@code BinaryFraction} to be compared with {@code this}
     * @return {@code this} compared to {@code that}
     */
    @Override
    public int compareTo(@NotNull BinaryFraction that) {
        if (this == that) return 0;
        Ordering signumOrdering = Ordering.compare(mantissa.signum(), that.mantissa.signum());
        if (signumOrdering != Ordering.EQ) return signumOrdering.toInt();
        switch (Ordering.compare(exponent, that.exponent)) {
            case LT:
                return mantissa.compareTo(that.mantissa.shiftLeft(that.exponent - exponent));
            case GT:
                return mantissa.shiftLeft(exponent - that.exponent).compareTo(that.mantissa);
            case EQ:
                return mantissa.compareTo(that.mantissa);
        }
        throw new IllegalStateException("unreachable");
    }

    /**
     * Creates an {@code BinaryFraction} from a {@code String}. Valid input takes the form of a {@code String} that
     * could have been returned by {@link BinaryFraction#toString()}. See that method's tests and demos for examples of
     * valid input.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result may be any {@code Optional<BinaryFraction>}.</li>
     * </ul>
     *
     * @param s a string representation of a {@code BinaryFraction}.
     * @return the wrapped {@code BinaryFraction} represented by {@code s}, or {@code empty} if {@code s} is invalid.
     */
    public static @NotNull Optional<BinaryFraction> read(@NotNull String s) {
        return Readers.genericRead(
            t -> {
                int leftShiftIndex = s.indexOf(" << ");
                if (leftShiftIndex != -1) {
                    Optional<BigInteger> oMantissa = Readers.readBigInteger(s.substring(0, leftShiftIndex));
                    if (!oMantissa.isPresent()) return null;
                    Optional<Integer> oExponent = Readers.readInteger(s.substring(leftShiftIndex + 4));
                    if (!oExponent.isPresent()) return null;
                    return of(oMantissa.get(), oExponent.get());
                }
                int rightShiftIndex = s.indexOf(" >> ");
                if (rightShiftIndex != -1) {
                    Optional<BigInteger> oMantissa = Readers.readBigInteger(s.substring(0, rightShiftIndex));
                    if (!oMantissa.isPresent()) return null;
                    Optional<Integer> oExponent = Readers.readInteger(s.substring(rightShiftIndex + 4));
                    if (!oExponent.isPresent()) return null;
                    return of(oMantissa.get(), -oExponent.get());
                }
                Optional<BigInteger> oMantissa = Readers.readBigInteger(s);
                return oMantissa.isPresent() ? new BinaryFraction(oMantissa.get(), 0) : null;
            }
        ).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code BinaryFraction} in a {@code String}. Returns the {@code BinaryFraction}
     * and the index at which it was found. Returns an empty {@code Optional} if no {@code BinaryFraction} is found.
     * Only {@code String}s which could have been emitted by {@link BinaryFraction#toString} are recognized. The
     * longest possible {@code BinaryFraction} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code BinaryFraction} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<BinaryFraction, Integer>> findIn(@NotNull String s) {
        return Readers.genericFindIn(BinaryFraction::read, " -0123456789<>").apply(s);
    }

    /**
     * Creates a {@code String} representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code BinaryFraction}.</li>
     *  <li>See tests and demos for example results.</li>
     * </ul>
     *
     * @return a {@code String} representation of {@code this}
     */
    public @NotNull String toString() {
        switch (Integer.signum(exponent)) {
            case 0:  return mantissa.toString();
            case 1:  return mantissa + " << " + exponent;
            case -1: return mantissa + " >> " + -exponent;
            default: throw new IllegalStateException("unreachable");
        }
    }

    /**
     * Ensures that {@code this} is valid. Must return true for any {@code Rational} used outside this class.
     */
    public void validate() {
        if (mantissa.equals(BigInteger.ZERO)) {
            assertEquals(toString(), exponent, 0);
        } else {
            assertTrue(toString(), mantissa.testBit(0));
        }
        if (equals(ZERO)) assertTrue(toString(), this == ZERO);
        if (equals(ONE)) assertTrue(toString(), this == ONE);
    }
}
