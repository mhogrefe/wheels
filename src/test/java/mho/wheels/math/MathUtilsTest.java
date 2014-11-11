package mho.wheels.math;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.numbers.Numbers;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static mho.wheels.iterables.IterableUtils.toList;
import static mho.wheels.math.MathUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MathUtilsTest {
    @Test
    public void testGcd() {
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
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[0, 0]").get()), 0);
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[0, 1]").get()), 1);
        aeq(fromBigEndianDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[0, 1, 1, 1, 0, 1]").get()), 29);
        aeq(
                fromBigEndianDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[3, 1, 4, 1, 5, 9]").get()),
                314159
        );
        aeq(
                fromBigEndianDigits(BigInteger.valueOf(70), Numbers.readBigIntegerList("[43, 5, 20, 0, 8]").get()),
                1034243008
        );
        aeq(fromBigEndianDigits(BigInteger.valueOf(70), new ArrayList<BigInteger>()), 0);
        try {
            fromBigEndianDigits(BigInteger.ONE, Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.ZERO, Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(-1), Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[-1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[1, 2, 10]").get());
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
        aeq(fromDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[0, 0]").get()), 0);
        aeq(fromDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[1, 0]").get()), 1);
        aeq(fromDigits(BigInteger.valueOf(2), Numbers.readBigIntegerList("[1, 0, 1, 1, 1, 0]").get()), 29);
        aeq(fromDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[9, 5, 1, 4, 1, 3]").get()), 314159);
        aeq(fromDigits(BigInteger.valueOf(70), Numbers.readBigIntegerList("[8, 0, 20, 5, 43]").get()), 1034243008);
        aeq(fromDigits(BigInteger.valueOf(70), new ArrayList<BigInteger>()), 0);
        try {
            fromDigits(BigInteger.ONE, Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.ZERO, Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(-1), Numbers.readBigIntegerList("[1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[-1, 2, 3]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), Numbers.readBigIntegerList("[1, 2, 10]").get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
