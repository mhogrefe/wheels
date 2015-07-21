package mho.wheels.numberUtils;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Some utilities for manipulating and analyzing {@code BigDecimal}s. In {@code BigDecimal} terminology, a
 * {@code BigDecimal}'s <i>scale</i> is the number of digits after the decimal point. It may be negative: for example,
 * 1E+1 has a scale of –1. A {@code BigDecimal}'s <i>precision</i> is the number of significant figures. It is always
 * positive, and a zero, regardless of its scale, always has a precision of 1.
 */
public class BigDecimalUtils {
    /**
     * –1
     */
    public static final @NotNull BigDecimal NEGATIVE_ONE = BigDecimal.valueOf(-1);

    /**
     * 2
     */
    public static final @NotNull BigDecimal TWO = BigDecimal.valueOf(2);

    /**
     * Disallow instantiation
     */
    private BigDecimalUtils() {}

    /**
     * Rounds a {@code BigDecimal} to a specified precision (number of significant figures). If the precision is
     * greater than the {@code BigDecimal}'s, trailing zeroes are appended; otherwise, the {@code BigDecimal} is
     * rounded according to {@code RoundingMode.HALF_EVEN}. Zero is treated specially: setting the precision of any
     * zero {@code BigDecimal} to 1 yields 0, setting it to 2 yields 0.0, setting it to 3 yields 0.00, etc.
     *
     * <ul>
     *  <li>{@code bd} must be non-null.</li>
     *  <li>{@code precision} must be positive.</li>
     *  <li>The result is not a zero {@code BigDecimal} with a negative scale.</li>
     * </ul>
     *
     * @param bd a {@code BigDecimal}
     * @param precision the precision that {@code bd} should be rounded to
     * @return {@code bd} rounded to {@code precision}
     */
    public static @NotNull BigDecimal setPrecision(@NotNull BigDecimal bd, int precision) {
        if (precision <= 0)
            throw new ArithmeticException("precision must be positive");
        if (Ordering.eq(bd, BigDecimal.ZERO)) {
            return BigDecimal.ZERO.setScale(precision - 1, RoundingMode.UNNECESSARY);
        }
        while (bd.precision() != precision) {
            bd = bd.setScale(bd.scale() + precision - bd.precision(), RoundingMode.HALF_EVEN);
        }
        return bd;
    }

    /**
     * Increments a {@code BigDecimal}'s unscaled value by 1.
     *
     * <ul>
     *  <li>{@code bd} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param bd a {@code BigDecimal}
     * @return The predecessor of {@code bd} at {@code bd}'s precision
     */
    public static @NotNull BigDecimal successor(@NotNull BigDecimal bd) {
        return new BigDecimal(bd.unscaledValue().add(BigInteger.ONE), bd.scale());
    }

    /**
     * Decrements a {@code BigDecimal}'s unscaled value by 1.
     *
     * <ul>
     *  <li>{@code bd} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param bd a {@code BigDecimal}
     * @return The predecessor of {@code bd} at {@code bd}'s precision
     */
    public static @NotNull BigDecimal predecessor(@NotNull BigDecimal bd) {
        return new BigDecimal(bd.unscaledValue().subtract(BigInteger.ONE), bd.scale());
    }

    /**
     * Returns the left shift of {@code this} by {@code bits}; {@code this}×2<sup>{@code bits}</sup>. Negative
     * {@code bits} corresponds to a right shift.
     *
     * <ul>
     *  <li>{@code this} can be any {@code BigDecimal}.</li>
     *  <li>{@code bits} may be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param bits the number of bits to left-shift by
     * @return {@code this}≪{@code bits}
     */
    public static @NotNull BigDecimal shiftLeft(@NotNull BigDecimal bd, int bits) {
        switch (Integer.signum(bits)) {
            case 0:
                return bd;
            case 1:
                return bd.multiply(TWO.pow(bits));
            case -1:
                //noinspection BigDecimalMethodWithoutRoundingCalled
                return bd.divide(TWO.pow(-bits));
            default:
                throw new IllegalStateException("unreachable");
        }
    }

    /**
     * Returns the right shift of {@code this} by {@code bits}; {@code this}×2<sup>–{@code bits}</sup>. Negative
     * {@code bits} corresponds to a left shift.
     *
     * <ul>
     *  <li>{@code this} can be any {@code BigDecimal}.</li>
     *  <li>{@code bits} may be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param bits the number of bits to right-shift by
     * @return {@code this}≫{@code bits}
     */
    public static @NotNull BigDecimal shiftRight(@NotNull BigDecimal bd, int bits) {
        switch (Integer.signum(bits)) {
            case 0:
                return bd;
            case 1:
                //noinspection BigDecimalMethodWithoutRoundingCalled
                return bd.divide(TWO.pow(bits));
            case -1:
                return bd.multiply(TWO.pow(-bits));
            default:
                throw new IllegalStateException("unreachable");
        }
    }
}
