package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.toList;
import static mho.haskellesque.math.MathUtils.*;
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

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
