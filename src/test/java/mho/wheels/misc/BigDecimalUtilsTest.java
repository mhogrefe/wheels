package mho.wheels.misc;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.misc.BigDecimalUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

public class BigDecimalUtilsTest {
    private static void setPrecision_helper(@NotNull String x, int scale, @NotNull String output) {
        aeq(setPrecision(Readers.readBigDecimal(x).get(), scale), output);
    }

    private static void setPrecision_fail(@NotNull String x, int scale) {
        try {
            setPrecision(Readers.readBigDecimal(x).get(), scale);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSetPrecision() {
        setPrecision_helper("3.14159", 1, "3");
        setPrecision_helper("3.14159", 2, "3.1");
        setPrecision_helper("3.14159", 3, "3.14");
        setPrecision_helper("3.14159", 4, "3.142");
        setPrecision_helper("3.14159", 10, "3.141590000");
        setPrecision_helper("1200", 1, "1E+3");
        setPrecision_helper("1200", 2, "1.2E+3");
        setPrecision_helper("1200", 3, "1.20E+3");
        setPrecision_helper("1200", 4, "1200");
        setPrecision_helper("1200", 5, "1200.0");
        setPrecision_helper("-9", 1, "-9");
        setPrecision_helper("-9", 2, "-9.0");
        setPrecision_helper("-9", 3, "-9.00");
        setPrecision_helper("0.95", 3, "0.950");
        setPrecision_helper("0.95", 2, "0.95");
        setPrecision_helper("0.95", 1, "1");
        setPrecision_helper("0.995", 4, "0.9950");
        setPrecision_helper("0.995", 3, "0.995");
        setPrecision_helper("0.995", 2, "1.0");
        setPrecision_helper("0.995", 1, "1");
        setPrecision_helper("0", 1, "0");
        setPrecision_helper("0", 2, "0.0");
        setPrecision_helper("0", 3, "0.00");
        setPrecision_helper("0E+1", 1, "0");
        setPrecision_helper("0E+1", 2, "0.0");
        setPrecision_helper("0E+1", 3, "0.00");
        setPrecision_helper("0.0", 1, "0");
        setPrecision_helper("0.0", 2, "0.0");
        setPrecision_helper("0.0", 3, "0.00");
        setPrecision_fail("3.14159", 0);
        setPrecision_fail("3.14159", -1);
    }

    private static void successor_helper(@NotNull String x, @NotNull String output) {
        aeq(successor(Readers.readBigDecimal(x).get()), output);
    }

    @Test
    public void testSuccessor() {
        successor_helper("3.14159", "3.14160");
        successor_helper("1200", "1201");
        successor_helper("1.2E+3", "1.3E+3");
        successor_helper("-9", "-8");
        successor_helper("0.99", "1.00");
        successor_helper("0.999", "1.000");
        successor_helper("-0.99", "-0.98");
        successor_helper("-0.999", "-0.998");
        successor_helper("0", "1");
        successor_helper("0E+1", "1E+1");
        successor_helper("0.0", "0.1");
    }

    private static void predecessor_helper(@NotNull String x, @NotNull String output) {
        aeq(predecessor(Readers.readBigDecimal(x).get()), output);
    }

    @Test
    public void testPredecessor() {
        predecessor_helper("3.14159", "3.14158");
        predecessor_helper("1200", "1199");
        predecessor_helper("1.2E+3", "1.1E+3");
        predecessor_helper("-9", "-10");
        predecessor_helper("0.99", "0.98");
        predecessor_helper("0.999", "0.998");
        predecessor_helper("-0.99", "-1.00");
        predecessor_helper("-0.999", "-1.000");
        predecessor_helper("0", "-1");
        predecessor_helper("0E+1", "-1E+1");
        predecessor_helper("0.0", "-0.1");
    }
}