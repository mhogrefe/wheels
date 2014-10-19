package mho.haskellesque.numbers;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Optional;

public final class Numbers {
    /**
     * Disallow instantiation
     */
    private Numbers() {}

    public static @NotNull Optional<Integer> readInteger(@NotNull String s) {
        if (s.startsWith("0x") || s.startsWith("-0") || s.length() > 1 && s.charAt(0) == '0') return Optional.empty();
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static @NotNull Optional<BigInteger> readBigInteger(@NotNull String s) {
        if (s.startsWith("0x") || s.startsWith("-0") || s.length() > 1 && s.charAt(0) == '0') return Optional.empty();
        try {
            return Optional.of(new BigInteger(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

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

    public static @NotNull Optional<Float> floatFromPair(int mantissa, int exponent) {
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
        bits |= rawMantissa.intValue();
        float f = Float.intBitsToFloat(bits);
        return Optional.of(sign ? f : -f);
    }

    public static @NotNull Optional<Double> doubleFromPair(long mantissa, int exponent) {
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
        bits |= rawMantissa.longValue();
        double d = Double.longBitsToDouble(bits);
        return Optional.of(sign ? d : -d);
    }
}
