package mho.wheels.misc;

import org.junit.Test;

import java.math.BigDecimal;

import static mho.wheels.misc.BigDecimalUtils.*;
import static org.junit.Assert.*;

public class BigDecimalUtilsTest {
    @Test
    public void testSetPrecision() {
        aeq(setPrecision(new BigDecimal("3.14159"), 1), "3");
        aeq(setPrecision(new BigDecimal("3.14159"), 2), "3.1");
        aeq(setPrecision(new BigDecimal("3.14159"), 3), "3.14");
        aeq(setPrecision(new BigDecimal("3.14159"), 4), "3.142");
        aeq(setPrecision(new BigDecimal("3.14159"), 10), "3.141590000");
        aeq(setPrecision(new BigDecimal("1200"), 1), "1E+3");
        aeq(setPrecision(new BigDecimal("1200"), 2), "1.2E+3");
        aeq(setPrecision(new BigDecimal("1200"), 3), "1.20E+3");
        aeq(setPrecision(new BigDecimal("1200"), 4), "1200");
        aeq(setPrecision(new BigDecimal("1200"), 5), "1200.0");
        aeq(setPrecision(new BigDecimal("-9"), 1), "-9");
        aeq(setPrecision(new BigDecimal("-9"), 2), "-9.0");
        aeq(setPrecision(new BigDecimal("-9"), 3), "-9.00");
        aeq(setPrecision(new BigDecimal("0.95"), 3), "0.950");
        aeq(setPrecision(new BigDecimal("0.95"), 2), "0.95");
        aeq(setPrecision(new BigDecimal("0.95"), 1), "1");
        aeq(setPrecision(new BigDecimal("0.995"), 4), "0.9950");
        aeq(setPrecision(new BigDecimal("0.995"), 3), "0.995");
        aeq(setPrecision(new BigDecimal("0.995"), 2), "1.0");
        aeq(setPrecision(new BigDecimal("0.995"), 1), "1");
        aeq(setPrecision(new BigDecimal("0"), 1), "0");
        aeq(setPrecision(new BigDecimal("0"), 2), "0.0");
        aeq(setPrecision(new BigDecimal("0"), 3), "0.00");
        aeq(setPrecision(new BigDecimal("0E+1"), 1), "0");
        aeq(setPrecision(new BigDecimal("0E+1"), 2), "0.0");
        aeq(setPrecision(new BigDecimal("0E+1"), 3), "0.00");
        aeq(setPrecision(new BigDecimal("0.0"), 1), "0");
        aeq(setPrecision(new BigDecimal("0.0"), 2), "0.0");
        aeq(setPrecision(new BigDecimal("0.0"), 3), "0.00");
        try {
            setPrecision(new BigDecimal("3.14159"), 0);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            setPrecision(new BigDecimal("3.14159"), -1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}