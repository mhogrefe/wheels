package mho.haskellesque.numbers;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Optional;

public class Numbers {
    public static float successor(float f) {
        if (Float.isNaN(f) || f > 0 && Float.isInfinite(f))
            throw new ArithmeticException(f + " does not have a successor");
        if (f == 0.0f) return Float.MIN_VALUE;
        int floatBits = Float.floatToIntBits(f);
        return Float.intBitsToFloat(f > 0 ? floatBits + 1 : floatBits - 1);
    }

    public static float predecessor(float f) {
        if (Float.isNaN(f) || f < 0 && Float.isInfinite(f))
            throw new ArithmeticException(f + " does not have a predecessor");
        if (f == 0.0f) return -Float.MIN_VALUE;
        int floatBits = Float.floatToIntBits(f);
        return Float.intBitsToFloat(f > 0 ? floatBits - 1 : floatBits + 1);
    }

    public static double successor(double d) {
        if (Double.isNaN(d) || d > 0 && Double.isInfinite(d))
            throw new ArithmeticException(d + " does not have a successor");
        if (d == 0.0) return Double.MIN_VALUE;
        long doubleBits = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(d > 0 ? doubleBits + 1 : doubleBits - 1);
    }

    public static double predecessor(double d) {
        if (Double.isNaN(d) || d < 0 && Double.isInfinite(d))
            throw new ArithmeticException(d + " does not have a predecessor");
        if (d == 0.0) return -Double.MIN_VALUE;
        long doubleBits = Double.doubleToLongBits(d);
        return Double.longBitsToDouble(d > 0 ? doubleBits - 1 : doubleBits + 1);
    }

    public static @NotNull Optional<Integer> readInteger(@NotNull String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static @NotNull Optional<BigInteger> readBigInteger(@NotNull String s) {
        try {
            return Optional.of(new BigInteger(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
