package mho.haskellesque.math;

import org.junit.Test;

import static mho.haskellesque.math.BasicMath.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicMathTest {
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

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
