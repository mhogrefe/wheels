package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;
import org.junit.Test;

import java.math.BigInteger;
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
        aeq(toList(bitsPadded(BigInteger.valueOf(8), BigInteger.ZERO)),
                "[false, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(BigInteger.valueOf(8), BigInteger.ONE)),
                "[true, false, false, false, false, false, false, false]");
        aeq(toList(bitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(6))),
                "[false, true, true, false, false, false, false, false]");
        aeq(toList(bitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(105))),
                "[true, false, false, true, false, true, true, false]");
        aeq(toList(bitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(1000))),
                "[false, false, false, true, false, true, true, true]");
        aeq(toList(bitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(104))), "[false, false]");
        aeq(toList(bitsPadded(BigInteger.valueOf(2), BigInteger.valueOf(105))), "[true, false]");
        aeq(toList(bitsPadded(BigInteger.ONE, BigInteger.valueOf(104))), "[false]");
        aeq(toList(bitsPadded(BigInteger.ONE, BigInteger.valueOf(105))), "[true]");
        aeq(toList(bitsPadded(BigInteger.ZERO, BigInteger.valueOf(104))), "[]");
        try {
            bitsPadded(BigInteger.valueOf(8), BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bitsPadded(BigInteger.valueOf(-1), BigInteger.valueOf(8));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
