package mho.wheels.math;

import org.junit.Test;

import java.math.BigInteger;

import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.aeqit;
import static org.junit.Assert.*;

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
    public void testLcm() {
        aeq(lcm(BigInteger.valueOf(12), BigInteger.valueOf(15)), 60);
        aeq(lcm(BigInteger.valueOf(35), BigInteger.valueOf(210)), 210);
        aeq(lcm(BigInteger.valueOf(17), BigInteger.valueOf(20)), 340);
        aeq(lcm(BigInteger.ONE, BigInteger.valueOf(5)), 5);
        try {
            lcm(BigInteger.valueOf(-12), BigInteger.valueOf(15));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            lcm(BigInteger.valueOf(12), BigInteger.valueOf(-15));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            lcm(BigInteger.valueOf(-12), BigInteger.valueOf(-15));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            lcm(BigInteger.valueOf(6), BigInteger.ZERO);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            lcm(BigInteger.ZERO, BigInteger.valueOf(6));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            lcm(BigInteger.ZERO, BigInteger.ZERO);
            fail();
        } catch (ArithmeticException ignored) {}
    }
}
