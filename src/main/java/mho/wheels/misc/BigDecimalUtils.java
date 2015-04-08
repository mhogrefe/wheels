package mho.wheels.misc;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Some utilities for manipulating and analyzing {@code BigDecimal}s.
 */
public class BigDecimalUtils {
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
}
