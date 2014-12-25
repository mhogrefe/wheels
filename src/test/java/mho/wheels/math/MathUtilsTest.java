package mho.wheels.math;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.misc.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.toList;
import static mho.wheels.math.MathUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MathUtilsTest {
    @Test
    public void testGcd_int_int() {
        aeq(gcd(12, 15), 3);
        aeq(gcd(35, 210), 35);
        aeq(gcd(17, 20), 1);
        aeq(gcd(1, 5), 1);
        aeq(gcd(-12, 15), 3);
        aeq(gcd(12, -15), 3);
        aeq(gcd(-12, -15), 3);
        aeq(gcd(6, 0), 6);
        aeq(gcd(-6, 0), 6);
        aeq(gcd(0, 6), 6);
        aeq(gcd(0, -6), 6);
        try {
            gcd(0, 0);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testGcd_long_long() {
        aeq(gcd(12L, 15L), 3);
        aeq(gcd(35L, 210L), 35);
        aeq(gcd(17L, 20L), 1);
        aeq(gcd(1L, 5L), 1);
        aeq(gcd(-12L, 15L), 3);
        aeq(gcd(12L, -15L), 3);
        aeq(gcd(-12L, -15L), 3);
        aeq(gcd(6L, 0L), 6);
        aeq(gcd(-6L, 0L), 6);
        aeq(gcd(0L, 6L), 6);
        aeq(gcd(0L, -6L), 6);
        try {
            gcd(0L, 0L);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBits_int() {
        aeq(toList(bits(0)), "[]");
        aeq(toList(bits(1)), "[true]");
        aeq(toList(bits(6)), "[false, true, true]");
        aeq(toList(bits(105)), "[true, false, false, true, false, true, true]");
        try {
            bits(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBits_BigInteger() {
        aeq(toList(bits(BigInteger.ZERO)), "[]");
        aeq(toList(bits(BigInteger.ONE)), "[true]");
        aeq(toList(bits(BigInteger.valueOf(6))), "[false, true, true]");
        aeq(toList(bits(BigInteger.valueOf(105))), "[true, false, false, true, false, true, true]");
        try {
            bits(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBitsPadded_int_int() {
        aeq(toList(bitsPadded(8, 0)), "[false, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, 1)), "[true, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, 6)), "[false, true, true, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, 105)), "[true, false, false, true, false, true, true, false]");
        aeq(toList(bitsPadded(8, 1000)), "[false, false, false, true, false, true, true, true]");
        aeq(toList(bitsPadded(2, 104)), "[false, false]");
        aeq(toList(bitsPadded(2, 105)), "[true, false]");
        aeq(toList(bitsPadded(1, 104)), "[false]");
        aeq(toList(bitsPadded(1, 105)), "[true]");
        aeq(toList(bitsPadded(0, 104)), "[]");
        try {
            bitsPadded(8, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bitsPadded(-1, 8);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBitsPadded_BigInteger_BigInteger() {
        aeq(toList(bitsPadded(8, BigInteger.ZERO)), "[false, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, BigInteger.ONE)), "[true, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, BigInteger.valueOf(6))), "[false, true, true, false, false, false, false, false]");
        aeq(toList(bitsPadded(8, BigInteger.valueOf(105))), "[true, false, false, true, false, true, true, false]");
        aeq(toList(bitsPadded(8, BigInteger.valueOf(1000))), "[false, false, false, true, false, true, true, true]");
        aeq(toList(bitsPadded(2, BigInteger.valueOf(104))), "[false, false]");
        aeq(toList(bitsPadded(2, BigInteger.valueOf(105))), "[true, false]");
        aeq(toList(bitsPadded(1, BigInteger.valueOf(104))), "[false]");
        aeq(toList(bitsPadded(1, BigInteger.valueOf(105))), "[true]");
        aeq(toList(bitsPadded(0, BigInteger.valueOf(104))), "[]");
        try {
            bitsPadded(8, BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bitsPadded(-1, BigInteger.valueOf(8));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBits_int() {
        aeq(toList(bigEndianBits(0)), "[]");
        aeq(toList(bigEndianBits(1)), "[true]");
        aeq(toList(bigEndianBits(6)), "[true, true, false]");
        aeq(toList(bigEndianBits(105)), "[true, true, false, true, false, false, true]");
        try {
            bigEndianBits(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBits_BigInteger() {
        aeq(toList(bigEndianBits(BigInteger.ZERO)), "[]");
        aeq(toList(bigEndianBits(BigInteger.ONE)), "[true]");
        aeq(toList(bigEndianBits(BigInteger.valueOf(6))), "[true, true, false]");
        aeq(toList(bigEndianBits(BigInteger.valueOf(105))), "[true, true, false, true, false, false, true]");
        try {
            bigEndianBits(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBitsPadded_int_int() {
        aeq(toList(bigEndianBitsPadded(8, 0)), "[false, false, false, false, false, false, false, false]");
        aeq(toList(bigEndianBitsPadded(8, 1)), "[false, false, false, false, false, false, false, true]");
        aeq(toList(bigEndianBitsPadded(8, 6)), "[false, false, false, false, false, true, true, false]");
        aeq(toList(bigEndianBitsPadded(8, 105)), "[false, true, true, false, true, false, false, true]");
        aeq(toList(bigEndianBitsPadded(8, 1000)), "[true, true, true, false, true, false, false, false]");
        aeq(toList(bigEndianBitsPadded(2, 104)), "[false, false]");
        aeq(toList(bigEndianBitsPadded(2, 105)), "[false, true]");
        aeq(toList(bigEndianBitsPadded(1, 104)), "[false]");
        aeq(toList(bigEndianBitsPadded(1, 105)), "[true]");
        aeq(toList(bigEndianBitsPadded(0, 104)), "[]");
        try {
            bigEndianBitsPadded(8, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianBitsPadded(-1, 8);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBitsPadded_BigInteger_BigInteger() {
        aeq(toList(bigEndianBitsPadded(8, BigInteger.ZERO)),
                "[false, false, false, false, false, false, false, false]");
        aeq(toList(bigEndianBitsPadded(8, BigInteger.ONE)),
                "[false, false, false, false, false, false, false, true]");
        aeq(toList(bigEndianBitsPadded(8, BigInteger.valueOf(6))),
                "[false, false, false, false, false, true, true, false]");
        aeq(toList(bigEndianBitsPadded(8, BigInteger.valueOf(105))),
                "[false, true, true, false, true, false, false, true]");
        aeq(toList(bigEndianBitsPadded(8, BigInteger.valueOf(1000))),
                "[true, true, true, false, true, false, false, false]");
        aeq(toList(bigEndianBitsPadded(2, BigInteger.valueOf(104))), "[false, false]");
        aeq(toList(bigEndianBitsPadded(2, BigInteger.valueOf(105))), "[false, true]");
        aeq(toList(bigEndianBitsPadded(1, BigInteger.valueOf(104))), "[false]");
        aeq(toList(bigEndianBitsPadded(1, BigInteger.valueOf(105))), "[true]");
        aeq(toList(bigEndianBitsPadded(0, BigInteger.valueOf(104))), "[]");
        try {
            bigEndianBitsPadded(8, BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianBitsPadded(-1, BigInteger.valueOf(8));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFromBits() {
        aeq(fromBits(new ArrayList<>()), 0);
        aeq(fromBits(Arrays.asList(false, false)), 0);
        aeq(fromBits(Arrays.asList(true, false)), 1);
        aeq(fromBits(Arrays.asList(false, true, true, false, false, false, false, false)), 6);
        aeq(fromBits(Arrays.asList(true, false, false, true, false, true, true)), 105);
        try {
            fromBits(Arrays.asList(true, null, true));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testFromBigEndianBits() {
        aeq(fromBigEndianBits(new ArrayList<>()), 0);
        aeq(fromBigEndianBits(Arrays.asList(false, false)), 0);
        aeq(fromBigEndianBits(Arrays.asList(false, true)), 1);
        aeq(fromBigEndianBits(Arrays.asList(false, false, false, false, false, true, true, false)), 6);
        aeq(fromBigEndianBits(Arrays.asList(true, true, false, true, false, false, true)), 105);
        try {
            fromBigEndianBits(Arrays.asList(true, null, true));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testDigits_int_int() {
        aeq(digits(2, 0), "[]");
        aeq(digits(3, 0), "[]");
        aeq(digits(8, 0), "[]");
        aeq(digits(10, 0), "[]");
        aeq(digits(12, 0), "[]");
        aeq(digits(57, 0), "[]");
        aeq(digits(2, 1), "[1]");
        aeq(digits(3, 1), "[1]");
        aeq(digits(8, 1), "[1]");
        aeq(digits(10, 1), "[1]");
        aeq(digits(12, 1), "[1]");
        aeq(digits(57, 1), "[1]");
        aeq(digits(2, 10), "[0, 1, 0, 1]");
        aeq(digits(3, 10), "[1, 0, 1]");
        aeq(digits(8, 10), "[2, 1]");
        aeq(digits(10, 10), "[0, 1]");
        aeq(digits(12, 10), "[10]");
        aeq(digits(57, 10), "[10]");
        aeq(digits(2, 187), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeq(digits(3, 187), "[1, 2, 2, 0, 2]");
        aeq(digits(8, 187), "[3, 7, 2]");
        aeq(digits(10, 187), "[7, 8, 1]");
        aeq(digits(12, 187), "[7, 3, 1]");
        aeq(digits(57, 187), "[16, 3]");
        try {
            digits(1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digits(0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigits_BigInteger_BigInteger() {
        aeq(digits(BigInteger.valueOf(2), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(8), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(10), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(12), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(digits(BigInteger.valueOf(2), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(8), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(10), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(12), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(digits(BigInteger.valueOf(2), BigInteger.valueOf(10)), "[0, 1, 0, 1]");
        aeq(digits(BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0, 1]");
        aeq(digits(BigInteger.valueOf(8), BigInteger.valueOf(10)), "[2, 1]");
        aeq(digits(BigInteger.valueOf(10), BigInteger.valueOf(10)), "[0, 1]");
        aeq(digits(BigInteger.valueOf(12), BigInteger.valueOf(10)), "[10]");
        aeq(digits(BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(digits(BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeq(digits(BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1, 2, 2, 0, 2]");
        aeq(digits(BigInteger.valueOf(8), BigInteger.valueOf(187)), "[3, 7, 2]");
        aeq(digits(BigInteger.valueOf(10), BigInteger.valueOf(187)), "[7, 8, 1]");
        aeq(digits(BigInteger.valueOf(12), BigInteger.valueOf(187)), "[7, 3, 1]");
        aeq(digits(BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16, 3]");
        try {
            digits(BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(BigInteger.valueOf(2), BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digits(BigInteger.ZERO, BigInteger.valueOf(-1));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigitsPadded_int_int_int() {
        aeq(digitsPadded(0, 2, 0), "[]");
        aeq(digitsPadded(0, 3, 0), "[]");
        aeq(digitsPadded(0, 57, 0), "[]");
        aeq(digitsPadded(0, 2, 1), "[]");
        aeq(digitsPadded(0, 3, 1), "[]");
        aeq(digitsPadded(0, 57, 1), "[]");
        aeq(digitsPadded(0, 2, 10), "[]");
        aeq(digitsPadded(0, 3, 10), "[]");
        aeq(digitsPadded(0, 57, 10), "[]");
        aeq(digitsPadded(0, 2, 187), "[]");
        aeq(digitsPadded(0, 3, 187), "[]");
        aeq(digitsPadded(0, 57, 187), "[]");
        aeq(digitsPadded(1, 2, 0), "[0]");
        aeq(digitsPadded(1, 3, 0), "[0]");
        aeq(digitsPadded(1, 57, 0), "[0]");
        aeq(digitsPadded(1, 2, 1), "[1]");
        aeq(digitsPadded(1, 3, 1), "[1]");
        aeq(digitsPadded(1, 57, 1), "[1]");
        aeq(digitsPadded(1, 2, 10), "[0]");
        aeq(digitsPadded(1, 3, 10), "[1]");
        aeq(digitsPadded(1, 57, 10), "[10]");
        aeq(digitsPadded(1, 2, 187), "[1]");
        aeq(digitsPadded(1, 3, 187), "[1]");
        aeq(digitsPadded(1, 57, 187), "[16]");
        aeq(digitsPadded(2, 2, 0), "[0, 0]");
        aeq(digitsPadded(2, 3, 0), "[0, 0]");
        aeq(digitsPadded(2, 57, 0), "[0, 0]");
        aeq(digitsPadded(2, 2, 1), "[1, 0]");
        aeq(digitsPadded(2, 3, 1), "[1, 0]");
        aeq(digitsPadded(2, 57, 1), "[1, 0]");
        aeq(digitsPadded(2, 2, 10), "[0, 1]");
        aeq(digitsPadded(2, 3, 10), "[1, 0]");
        aeq(digitsPadded(2, 57, 10), "[10, 0]");
        aeq(digitsPadded(2, 2, 187), "[1, 1]");
        aeq(digitsPadded(2, 3, 187), "[1, 2]");
        aeq(digitsPadded(2, 57, 187), "[16, 3]");
        aeq(digitsPadded(8, 2, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 3, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 57, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 2, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 3, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 57, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 2, 10), "[0, 1, 0, 1, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 3, 10), "[1, 0, 1, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 57, 10), "[10, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(8, 2, 187), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeq(digitsPadded(8, 3, 187), "[1, 2, 2, 0, 2, 0, 0, 0]");
        aeq(digitsPadded(8, 57, 187), "[16, 3, 0, 0, 0, 0, 0, 0]");
        try {
            digitsPadded(3, 1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, 0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, 2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digitsPadded(3, 0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, 2, 3);
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, 0, -1);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigitsPadded_BigInteger_BigInteger_BigInteger() {
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.ZERO), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.ONE), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.ONE), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.ONE), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.valueOf(10)), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.valueOf(187)), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[]");
        aeq(digitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.ZERO), "[0]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.ZERO), "[0]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.ZERO), "[0]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.ONE), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(10)), "[0]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1]");
        aeq(digitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.ZERO), "[0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.ONE), "[1, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.ONE), "[1, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.ONE), "[1, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(10)), "[0, 1]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10, 0]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1, 1]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1, 2]");
        aeq(digitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16, 3]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.valueOf(10)),
                "[0, 1, 0, 1, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.valueOf(10)),
                "[1, 0, 1, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.valueOf(10)),
                "[10, 0, 0, 0, 0, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.valueOf(187)),
                "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.valueOf(187)),
                "[1, 2, 2, 0, 2, 0, 0, 0]");
        aeq(digitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.valueOf(187)),
                "[16, 3, 0, 0, 0, 0, 0, 0]");
        try {
            digitsPadded(BigInteger.valueOf(3), BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(BigInteger.valueOf(3), BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(BigInteger.valueOf(3), BigInteger.valueOf(2), BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digitsPadded(BigInteger.valueOf(3), BigInteger.ZERO, BigInteger.valueOf(-1));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(BigInteger.valueOf(-1), BigInteger.valueOf(2), BigInteger.valueOf(3));
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(BigInteger.valueOf(-1), BigInteger.ZERO, BigInteger.valueOf(-1));
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigits_int_int() {
        aeq(bigEndianDigits(2, 0), "[]");
        aeq(bigEndianDigits(3, 0), "[]");
        aeq(bigEndianDigits(8, 0), "[]");
        aeq(bigEndianDigits(10, 0), "[]");
        aeq(bigEndianDigits(12, 0), "[]");
        aeq(bigEndianDigits(57, 0), "[]");
        aeq(bigEndianDigits(2, 1), "[1]");
        aeq(bigEndianDigits(3, 1), "[1]");
        aeq(bigEndianDigits(8, 1), "[1]");
        aeq(bigEndianDigits(10, 1), "[1]");
        aeq(bigEndianDigits(12, 1), "[1]");
        aeq(bigEndianDigits(57, 1), "[1]");
        aeq(bigEndianDigits(2, 10), "[1, 0, 1, 0]");
        aeq(bigEndianDigits(3, 10), "[1, 0, 1]");
        aeq(bigEndianDigits(8, 10), "[1, 2]");
        aeq(bigEndianDigits(10, 10), "[1, 0]");
        aeq(bigEndianDigits(12, 10), "[10]");
        aeq(bigEndianDigits(57, 10), "[10]");
        aeq(bigEndianDigits(2, 187), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigits(3, 187), "[2, 0, 2, 2, 1]");
        aeq(bigEndianDigits(8, 187), "[2, 7, 3]");
        aeq(bigEndianDigits(10, 187), "[1, 8, 7]");
        aeq(bigEndianDigits(12, 187), "[1, 3, 7]");
        aeq(bigEndianDigits(57, 187), "[3, 16]");
        try {
            bigEndianDigits(1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigits(0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigits_BigInteger_BigInteger() {
        aeq(bigEndianDigits(BigInteger.valueOf(2), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(2), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(2), BigInteger.valueOf(10)), "[1, 0, 1, 0]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.valueOf(10)), "[1, 2]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.valueOf(10)), "[1, 0]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigits(BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.valueOf(187)), "[2, 0, 2, 2, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.valueOf(187)), "[2, 7, 3]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.valueOf(187)), "[1, 8, 7]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.valueOf(187)), "[1, 3, 7]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.valueOf(187)), "[3, 16]");
        try {
            bigEndianDigits(BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(BigInteger.valueOf(2), BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigits(BigInteger.ZERO, BigInteger.valueOf(-1));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_int_int_int() {
        aeq(bigEndianDigitsPadded(0, 2, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 187), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 187), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 187), "[]");
        aeq(bigEndianDigitsPadded(1, 2, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 3, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 57, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 2, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 3, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 2, 10), "[0]");
        aeq(bigEndianDigitsPadded(1, 3, 10), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 10), "[10]");
        aeq(bigEndianDigitsPadded(1, 2, 187), "[1]");
        aeq(bigEndianDigitsPadded(1, 3, 187), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 187), "[16]");
        aeq(bigEndianDigitsPadded(2, 2, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 3, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 57, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 2, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 3, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 2, 10), "[1, 0]");
        aeq(bigEndianDigitsPadded(2, 3, 10), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 10), "[0, 10]");
        aeq(bigEndianDigitsPadded(2, 2, 187), "[1, 1]");
        aeq(bigEndianDigitsPadded(2, 3, 187), "[2, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 187), "[3, 16]");
        aeq(bigEndianDigitsPadded(8, 2, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 3, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 57, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 2, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 3, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 2, 10), "[0, 0, 0, 0, 1, 0, 1, 0]");
        aeq(bigEndianDigitsPadded(8, 3, 10), "[0, 0, 0, 0, 0, 1, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 10), "[0, 0, 0, 0, 0, 0, 0, 10]");
        aeq(bigEndianDigitsPadded(8, 2, 187), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigitsPadded(8, 3, 187), "[0, 0, 0, 2, 0, 2, 2, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 187), "[0, 0, 0, 0, 0, 0, 3, 16]");
        try {
            bigEndianDigitsPadded(3, 1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, 0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, 2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigitsPadded(3, 0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, 2, 3);
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, 0, -1);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_BigInteger_BigInteger_BigInteger() {
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(2), BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ZERO, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(10)), "[0]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1]");
        aeq(bigEndianDigitsPadded(BigInteger.ONE, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(10)), "[1, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(10)), "[0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.valueOf(10)), "[0, 10]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(187)), "[1, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(187)), "[2, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(57), BigInteger.valueOf(187)), "[3, 16]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.ZERO),
                "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.ZERO),
                "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.ZERO),
                "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.ONE),
                "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.ONE),
                "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.ONE),
                "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.valueOf(10)),
                         "[0, 0, 0, 0, 1, 0, 1, 0]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.valueOf(10)),
                         "[0, 0, 0, 0, 0, 1, 0, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.valueOf(10)),
                         "[0, 0, 0, 0, 0, 0, 0, 10]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(2), BigInteger.valueOf(187)),
                         "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(3), BigInteger.valueOf(187)),
                         "[0, 0, 0, 2, 0, 2, 2, 1]");
        aeq(bigEndianDigitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(57), BigInteger.valueOf(187)),
                         "[0, 0, 0, 0, 0, 0, 3, 16]");
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(3), BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(3), BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(3), BigInteger.valueOf(2), BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(3), BigInteger.ZERO, BigInteger.valueOf(-1));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(-1), BigInteger.valueOf(2), BigInteger.valueOf(3));
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(BigInteger.valueOf(-1), BigInteger.ZERO, BigInteger.valueOf(-1));
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_int_Iterable_Integer() {
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 0)), 0);
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 1)), 1);
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 1, 1, 1, 0, 1)), 29);
        aeq(fromBigEndianDigits(10, Arrays.asList(3, 1, 4, 1, 5, 9)), 314159);
        aeq(fromBigEndianDigits(70, Arrays.asList(43, 5, 20, 0, 8)), 1034243008);
        aeq(fromBigEndianDigits(70, new ArrayList<Integer>()), 0);
        try {
            fromBigEndianDigits(1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(0, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(10, Arrays.asList(-1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(10, Arrays.asList(1, 2, 10));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_int_Iterable_BigInteger() {
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), readBigIntegerList("[0, 0]").get()), 0);
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), readBigIntegerList("[0, 1]").get()), 1);
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), readBigIntegerList("[0, 1, 1, 1, 0, 1]").get()), 29);
        aeq(fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[3, 1, 4, 1, 5, 9]").get()), 314159);
        aeq(fromBigEndianDigits(BigInteger.valueOf(70), readBigIntegerList("[43, 5, 20, 0, 8]").get()), 1034243008);
        aeq(fromBigEndianDigits(BigInteger.valueOf(70), new ArrayList<BigInteger>()), 0);
        try {
            fromBigEndianDigits(BigInteger.ONE, readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.ZERO, readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(-1), readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[-1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[1, 2, 10]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigits_int_Iterable_Integer() {
        aeq(fromDigits(2, Arrays.asList(0, 0)), 0);
        aeq(fromDigits(2, Arrays.asList(1, 0)), 1);
        aeq(fromDigits(2, Arrays.asList(1, 0, 1, 1, 1, 0)), 29);
        aeq(fromDigits(10, Arrays.asList(9, 5, 1, 4, 1, 3)), 314159);
        aeq(fromDigits(70, Arrays.asList(8, 0, 20, 5, 43)), 1034243008);
        aeq(fromDigits(70, new ArrayList<Integer>()), 0);
        try {
            fromDigits(1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(0, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(10, Arrays.asList(-1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(10, Arrays.asList(1, 2, 10));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigits_BigInteger_Iterable_BigInteger() {
        aeq(fromDigits(BigInteger.valueOf(2), readBigIntegerList("[0, 0]").get()), 0);
        aeq(fromDigits(BigInteger.valueOf(2), readBigIntegerList("[1, 0]").get()), 1);
        aeq(fromDigits(BigInteger.valueOf(2), readBigIntegerList("[1, 0, 1, 1, 1, 0]").get()), 29);
        aeq(fromDigits(BigInteger.valueOf(10), readBigIntegerList("[9, 5, 1, 4, 1, 3]").get()), 314159);
        aeq(fromDigits(BigInteger.valueOf(70), readBigIntegerList("[8, 0, 20, 5, 43]").get()), 1034243008);
        aeq(fromDigits(BigInteger.valueOf(70), new ArrayList<BigInteger>()), 0);
        try {
            fromDigits(BigInteger.ONE, readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.ZERO, readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(-1), readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), readBigIntegerList("[-1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), readBigIntegerList("[1, 2, 10]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToDigit() {
        aeq(toDigit(0), '0');
        aeq(toDigit(6), '6');
        aeq(toDigit(10), 'A');
        aeq(toDigit(20), 'K');
        aeq(toDigit(35), 'Z');
        try {
            toDigit(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toDigit(36);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigit() {
        aeq(fromDigit('0'), 0);
        aeq(fromDigit('6'), 6);
        aeq(fromDigit('A'), 10);
        aeq(fromDigit('K'), 20);
        aeq(fromDigit('Z'), 35);
        try {
            fromDigit(' ');
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigit('a');
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_int_int() {
        aeq(toStringBase(2, 0), "0");
        aeq(toStringBase(3, 0), "0");
        aeq(toStringBase(4, 0), "0");
        aeq(toStringBase(10, 0), "0");
        aeq(toStringBase(12, 0), "0");
        aeq(toStringBase(16, 0), "0");
        aeq(toStringBase(36, 0), "0");
        aeq(toStringBase(88, 0), "(0)");
        aeq(toStringBase(100, 0), "(0)");
        aeq(toStringBase(2, 524393454), "11111010000011001101111101110");
        aeq(toStringBase(3, 524393454), "1100112201221120210");
        aeq(toStringBase(4, 524393454), "133100121233232");
        aeq(toStringBase(10, 524393454), "524393454");
        aeq(toStringBase(12, 524393454), "127750526");
        aeq(toStringBase(16, 524393454), "1F419BEE");
        aeq(toStringBase(36, 524393454), "8O7KKU");
        aeq(toStringBase(88, 524393454), "(8)(65)(44)(8)(46)");
        aeq(toStringBase(100, 524393454), "(5)(24)(39)(34)(54)");
        aeq(toStringBase(2, -524393454), "-11111010000011001101111101110");
        aeq(toStringBase(3, -524393454), "-1100112201221120210");
        aeq(toStringBase(4, -524393454), "-133100121233232");
        aeq(toStringBase(10, -524393454), "-524393454");
        aeq(toStringBase(12, -524393454), "-127750526");
        aeq(toStringBase(16, -524393454), "-1F419BEE");
        aeq(toStringBase(36, -524393454), "-8O7KKU");
        aeq(toStringBase(88, -524393454), "-(8)(65)(44)(8)(46)");
        aeq(toStringBase(100, -524393454), "-(5)(24)(39)(34)(54)");
        try {
            toStringBase(1, 524393454);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toStringBase(0, 524393454);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_BigInteger_BigInteger() {
        aeq(toStringBase(BigInteger.valueOf(2), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.ZERO), "(0)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.ZERO), "(0)");
        aeq(toStringBase(BigInteger.valueOf(2), BigInteger.valueOf(524393454)), "11111010000011001101111101110");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.valueOf(524393454)), "1100112201221120210");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.valueOf(524393454)), "133100121233232");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.valueOf(524393454)), "524393454");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.valueOf(524393454)), "127750526");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.valueOf(524393454)), "1F419BEE");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.valueOf(524393454)), "8O7KKU");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.valueOf(524393454)), "(8)(65)(44)(8)(46)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.valueOf(524393454)), "(5)(24)(39)(34)(54)");
        aeq(toStringBase(BigInteger.valueOf(2), BigInteger.valueOf(-524393454)), "-11111010000011001101111101110");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.valueOf(-524393454)), "-1100112201221120210");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.valueOf(-524393454)), "-133100121233232");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.valueOf(-524393454)), "-524393454");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.valueOf(-524393454)), "-127750526");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.valueOf(-524393454)), "-1F419BEE");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.valueOf(-524393454)), "-8O7KKU");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.valueOf(-524393454)), "-(8)(65)(44)(8)(46)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.valueOf(-524393454)), "-(5)(24)(39)(34)(54)");
        try {
            toStringBase(BigInteger.ONE, BigInteger.valueOf(524393454));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toStringBase(BigInteger.ZERO, BigInteger.valueOf(524393454));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_int_String() {
        aeq(fromStringBase(2, ""), 0);
        aeq(fromStringBase(3, ""), 0);
        aeq(fromStringBase(4, ""), 0);
        aeq(fromStringBase(10, ""), 0);
        aeq(fromStringBase(12, ""), 0);
        aeq(fromStringBase(16, ""), 0);
        aeq(fromStringBase(36, ""), 0);
        aeq(fromStringBase(88, ""), 0);
        aeq(fromStringBase(100, ""), 0);
        aeq(fromStringBase(2, "0"), 0);
        aeq(fromStringBase(3, "0"), 0);
        aeq(fromStringBase(4, "0"), 0);
        aeq(fromStringBase(10, "0"), 0);
        aeq(fromStringBase(12, "0"), 0);
        aeq(fromStringBase(16, "0"), 0);
        aeq(fromStringBase(36, "0"), 0);
        aeq(fromStringBase(88, "(0)"), 0);
        aeq(fromStringBase(100, "(0)"), 0);
        aeq(fromStringBase(2, "00"), 0);
        aeq(fromStringBase(3, "00"), 0);
        aeq(fromStringBase(4, "00"), 0);
        aeq(fromStringBase(10, "00"), 0);
        aeq(fromStringBase(12, "00"), 0);
        aeq(fromStringBase(16, "00"), 0);
        aeq(fromStringBase(36, "00"), 0);
        aeq(fromStringBase(88, "(0)(0)"), 0);
        aeq(fromStringBase(100, "(0)(0)"), 0);
        aeq(fromStringBase(2, "-0"), 0);
        aeq(fromStringBase(3, "-0"), 0);
        aeq(fromStringBase(4, "-0"), 0);
        aeq(fromStringBase(10, "-0"), 0);
        aeq(fromStringBase(12, "-0"), 0);
        aeq(fromStringBase(16, "-0"), 0);
        aeq(fromStringBase(36, "-0"), 0);
        aeq(fromStringBase(88, "-(0)"), 0);
        aeq(fromStringBase(100, "-(0)"), 0);
        aeq(fromStringBase(2, "-00"), 0);
        aeq(fromStringBase(3, "-00"), 0);
        aeq(fromStringBase(4, "-00"), 0);
        aeq(fromStringBase(10, "-00"), 0);
        aeq(fromStringBase(12, "-00"), 0);
        aeq(fromStringBase(16, "-00"), 0);
        aeq(fromStringBase(36, "-00"), 0);
        aeq(fromStringBase(88, "-(0)(0)"), 0);
        aeq(fromStringBase(100, "-(0)(0)"), 0);
        aeq(fromStringBase(2, "11111010000011001101111101110"), 524393454);
        aeq(fromStringBase(3, "1100112201221120210"), 524393454);
        aeq(fromStringBase(4, "133100121233232"), 524393454);
        aeq(fromStringBase(10, "524393454"), 524393454);
        aeq(fromStringBase(12, "127750526"), 524393454);
        aeq(fromStringBase(16, "1F419BEE"), 524393454);
        aeq(fromStringBase(36, "8O7KKU"), 524393454);
        aeq(fromStringBase(88, "(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(100, "(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(2, "00011111010000011001101111101110"), 524393454);
        aeq(fromStringBase(3, "0001100112201221120210"), 524393454);
        aeq(fromStringBase(4, "000133100121233232"), 524393454);
        aeq(fromStringBase(10, "000524393454"), 524393454);
        aeq(fromStringBase(12, "000127750526"), 524393454);
        aeq(fromStringBase(16, "0001F419BEE"), 524393454);
        aeq(fromStringBase(36, "0008O7KKU"), 524393454);
        aeq(fromStringBase(88, "(0)(0)(0)(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(100, "(0)(0)(0)(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(2, "-11111010000011001101111101110"), -524393454);
        aeq(fromStringBase(3, "-1100112201221120210"), -524393454);
        aeq(fromStringBase(4, "-133100121233232"), -524393454);
        aeq(fromStringBase(10, "-524393454"), -524393454);
        aeq(fromStringBase(12, "-127750526"), -524393454);
        aeq(fromStringBase(16, "-1F419BEE"), -524393454);
        aeq(fromStringBase(36, "-8O7KKU"), -524393454);
        aeq(fromStringBase(88, "-(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(100, "-(5)(24)(39)(34)(54)"), -524393454);
        aeq(fromStringBase(2, "-00011111010000011001101111101110"), -524393454);
        aeq(fromStringBase(3, "-0001100112201221120210"), -524393454);
        aeq(fromStringBase(4, "-000133100121233232"), -524393454);
        aeq(fromStringBase(10, "-000524393454"), -524393454);
        aeq(fromStringBase(12, "-000127750526"), -524393454);
        aeq(fromStringBase(16, "-0001F419BEE"), -524393454);
        aeq(fromStringBase(36, "-0008O7KKU"), -524393454);
        aeq(fromStringBase(88, "-(0)(0)(0)(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(100, "-(0)(0)(0)(5)(24)(39)(34)(54)"), -524393454);
        try {
            fromStringBase(1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(0, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "3");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "*");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "12");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(-12)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(3F)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_BigInteger_String() {
        aeq(fromStringBase(BigInteger.valueOf(2), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(2), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(2), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(2), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(2), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(2), "11111010000011001101111101110"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "1100112201221120210"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "133100121233232"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "524393454"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "127750526"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "1F419BEE"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "8O7KKU"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(2), "00011111010000011001101111101110"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "0001100112201221120210"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "000133100121233232"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "000524393454"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "000127750526"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "0001F419BEE"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "0008O7KKU"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)(0)(0)(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)(0)(0)(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(2), "-11111010000011001101111101110"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "-1100112201221120210"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "-133100121233232"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "-524393454"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "-127750526"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "-1F419BEE"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "-8O7KKU"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(5)(24)(39)(34)(54)"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(2), "-00011111010000011001101111101110"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "-0001100112201221120210"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "-000133100121233232"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "-000524393454"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "-000127750526"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "-0001F419BEE"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "-0008O7KKU"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)(0)(0)(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)(0)(0)(5)(24)(39)(34)(54)"), -524393454);
        try {
            fromStringBase(BigInteger.ONE, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.ZERO, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(2), "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(2), "3");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(2), "*");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "12");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(-12)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(3F)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLogarithmicMux() {
        aeq(logarithmicMux(BigInteger.valueOf(0), BigInteger.valueOf(0)), 0);
        aeq(logarithmicMux(BigInteger.valueOf(0), BigInteger.valueOf(1)), 1);
        aeq(logarithmicMux(BigInteger.valueOf(1), BigInteger.valueOf(0)), 2);
        aeq(logarithmicMux(BigInteger.valueOf(5), BigInteger.valueOf(10)), 11263);
        aeq(logarithmicMux(BigInteger.valueOf(10), BigInteger.valueOf(5)), 671);
        aeq(logarithmicMux(BigInteger.valueOf(500000), BigInteger.ZERO), 1000000);
        try {
            logarithmicMux(BigInteger.valueOf(-5), BigInteger.valueOf(5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            logarithmicMux(BigInteger.valueOf(5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            logarithmicMux(BigInteger.valueOf(-5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testLogarithmicDemux() {
        aeq(logarithmicDemux(BigInteger.ZERO), "(0, 0)");
        aeq(logarithmicDemux(BigInteger.ONE), "(0, 1)");
        aeq(logarithmicDemux(BigInteger.valueOf(2)), "(1, 0)");
        aeq(logarithmicDemux(BigInteger.valueOf(11263)), "(5, 10)");
        aeq(logarithmicDemux(BigInteger.valueOf(671)), "(10, 5)");
        aeq(logarithmicDemux(BigInteger.valueOf(1000000)), "(500000, 0)");
        try {
            logarithmicDemux(BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootMux() {
        aeq(squareRootMux(BigInteger.valueOf(0), BigInteger.valueOf(0)), 0);
        aeq(squareRootMux(BigInteger.valueOf(0), BigInteger.valueOf(1)), 1);
        aeq(squareRootMux(BigInteger.valueOf(1), BigInteger.valueOf(0)), 2);
        aeq(squareRootMux(BigInteger.valueOf(5), BigInteger.valueOf(10)), 538);
        aeq(squareRootMux(BigInteger.valueOf(10), BigInteger.valueOf(5)), 101);
        aeq(squareRootMux(BigInteger.valueOf(7680), BigInteger.valueOf(76)), 1000000);
        try {
            squareRootMux(BigInteger.valueOf(-5), BigInteger.valueOf(5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            squareRootMux(BigInteger.valueOf(5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            squareRootMux(BigInteger.valueOf(-5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootDemux() {
        aeq(squareRootDemux(BigInteger.ZERO), "(0, 0)");
        aeq(squareRootDemux(BigInteger.ONE), "(0, 1)");
        aeq(squareRootDemux(BigInteger.valueOf(2)), "(1, 0)");
        aeq(squareRootDemux(BigInteger.valueOf(538)), "(5, 10)");
        aeq(squareRootDemux(BigInteger.valueOf(101)), "(10, 5)");
        aeq(squareRootDemux(BigInteger.valueOf(1000000)), "(7680, 76)");
        try {
            squareRootDemux(BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testMux() {
        aeq(mux(readBigIntegerList("[]").get()), 0);
        aeq(mux(readBigIntegerList("[0]").get()), 0);
        aeq(mux(readBigIntegerList("[1]").get()), 1);
        aeq(mux(readBigIntegerList("[2]").get()), 2);
        aeq(mux(readBigIntegerList("[0, 0]").get()), 0);
        aeq(mux(readBigIntegerList("[0, 1]").get()), 1);
        aeq(mux(readBigIntegerList("[1, 0]").get()), 2);
        aeq(mux(readBigIntegerList("[5, 10]").get()), 102);
        aeq(mux(readBigIntegerList("[10, 5]").get()), 153);
        aeq(mux(readBigIntegerList("[784, 904]").get()), 1000000);
        aeq(mux(readBigIntegerList("[0, 0, 0]").get()), 0);
        aeq(mux(readBigIntegerList("[10, 10, 10]").get()), 3640);
        aeq(mux(readBigIntegerList("[48, 96, 76]").get()), 1000000);
        aeq(mux(readBigIntegerList("[1, 2, 3, 4]").get()), 362);
        aeq(mux(readBigIntegerList("[3, 2, 2, 3, 0, 2, 0, 0, 0, 0]").get()), 1000000);
        try {
            mux(readBigIntegerList("[1, 2, -3]").get());
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            mux(readBigIntegerListWithNulls("[1, null, 2]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDemux() {
        aeq(demux(0, BigInteger.ZERO), "[]");
        aeq(demux(1, BigInteger.ZERO), "[0]");
        aeq(demux(1, BigInteger.ONE), "[1]");
        aeq(demux(1, BigInteger.valueOf(2)), "[2]");
        aeq(demux(2, BigInteger.ZERO), "[0, 0]");
        aeq(demux(2, BigInteger.ONE), "[0, 1]");
        aeq(demux(2, BigInteger.valueOf(2)), "[1, 0]");
        aeq(demux(2, BigInteger.valueOf(1000000)), "[784, 904]");
        aeq(demux(3, BigInteger.ZERO), "[0, 0, 0]");
        aeq(demux(3, BigInteger.valueOf(3640)), "[10, 10, 10]");
        aeq(demux(3, BigInteger.valueOf(1000000)), "[48, 96, 76]");
        aeq(demux(4, BigInteger.valueOf(362)), "[1, 2, 3, 4]");
        aeq(demux(10, BigInteger.valueOf(1000000)), "[3, 2, 2, 3, 0, 2, 0, 0, 0, 0]");
        try {
            demux(0, BigInteger.ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(-2, BigInteger.ZERO);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(-2, BigInteger.ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(2, BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }

    private static @NotNull Optional<List<BigInteger>> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::findBigIntegerIn, s);
    }

    private static @NotNull Optional<List<BigInteger>> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readList(t -> Readers.findInWithNulls(Readers::findBigIntegerIn, t), s);
    }
}
