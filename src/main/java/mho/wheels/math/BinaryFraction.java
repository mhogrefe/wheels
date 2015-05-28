package mho.wheels.math;

import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.misc.Readers;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.*;

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
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param mantissa the mantissa
     * @param exponent the exponent
     * @return the {@code BinaryFraction} corresponding to {@code mantissa}×2<sup>{@code exponent}</sup>
     */
    public static @NotNull BinaryFraction of(@NotNull BigInteger mantissa, int exponent) {
        if (mantissa.equals(BigInteger.ZERO)) {
            return new BinaryFraction(BigInteger.ZERO, 0);
        }
        int trailingZeroes = mantissa.getLowestSetBit();
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
     * @return the {@code BigInteger} equal to {@code n}
     */
    public @NotNull BinaryFraction of(@NotNull BigInteger n) {
        return of(n, 0);
    }

    /**
     * Creates a {@code BinaryFraction} from an {@code int}.
     *
     * <ul>
     *  <li>{@code n} cannot be null.</li>
     *  <li>The result is an integral {@code BinaryFraction} x satisfying
     *  –2<sup>63</sup>≤x{@literal <}2<sup>63</sup>.</li>
     * </ul>
     *
     * @param n the {@code int}
     * @return the {@code int} equal to {@code n}
     */
    public @NotNull BinaryFraction of(int n) {
        return of(BigInteger.valueOf(n), 0);
    }

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

    public @NotNull BigDecimal bigDecimalValue() {
        switch (Integer.signum(exponent)) {
            case 0:
                return new BigDecimal(mantissa);
            case 1:
                return new BigDecimal(mantissa).multiply(new BigDecimal(BigInteger.ONE.shiftLeft(exponent)));
            case -1:
                //noinspection BigDecimalMethodWithoutRoundingCalled
                return new BigDecimal(mantissa).divide(new BigDecimal(BigInteger.ONE.shiftLeft(-exponent)));
            default: throw new IllegalStateException("unreachable");
        }
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        BinaryFraction bf = (BinaryFraction) that;
        return exponent == bf.exponent && mantissa.equals(bf.mantissa);
    }

    @Override
    public int hashCode() {
        return 31 * mantissa.hashCode() + exponent;
    }

    @Override
    public int compareTo(@NotNull BinaryFraction that) {
        if (this == that) return 0;
        Ordering signumOrdering = Ordering.compare(mantissa.signum(), that.mantissa.signum());
        if (signumOrdering != Ordering.EQ) return signumOrdering.toInt();
        switch (Ordering.compare(exponent, that.exponent)) {
            case LT: return mantissa.shiftLeft(that.exponent - exponent).compareTo(that.mantissa);
            case GT: return mantissa.compareTo(that.mantissa.shiftLeft(exponent - that.exponent));
            case EQ: return mantissa.compareTo(that.mantissa);
        }
        throw new IllegalStateException("unreachable");
    }

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
