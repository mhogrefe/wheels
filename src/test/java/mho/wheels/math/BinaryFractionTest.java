package mho.wheels.math;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.aeq;

public class BinaryFractionTest {
    @Test
    public void testConstants() {
        aeq(ZERO, "0");
        aeq(ONE, "1");
        aeq(SMALLEST_FLOAT, "1 >> 149");
        aeq(LARGEST_SUBNORMAL_FLOAT, "8388607 >> 149");
        aeq(SMALLEST_NORMAL_FLOAT, "1 >> 126");
        aeq(LARGEST_FLOAT, "16777215 << 104");
        aeq(SMALLEST_DOUBLE, "1 >> 1074");
        aeq(LARGEST_SUBNORMAL_DOUBLE, "4503599627370495 >> 1074");
        aeq(SMALLEST_NORMAL_DOUBLE, "1 >> 1022");
        aeq(LARGEST_DOUBLE, "9007199254740991 << 971");
    }

    private static void getMantissa_helper(@NotNull String x, int output) {
        aeq(read(x).get().getMantissa(), output);
    }

    @Test
    public void testGetMantissa() {
        getMantissa_helper("0", 0);
        getMantissa_helper("1", 1);
        getMantissa_helper("11", 11);
        getMantissa_helper("5 << 20", 5);
        getMantissa_helper("5 >> 20", 5);
        getMantissa_helper("-1", -1);
        getMantissa_helper("-11", -11);
        getMantissa_helper("-5 << 20", -5);
        getMantissa_helper("-5 >> 20", -5);
    }

    private static void getExponent_helper(@NotNull String x, int output) {
        aeq(read(x).get().getExponent(), output);
    }

    @Test
    public void testGetExponent() {
        getExponent_helper("0", 0);
        getExponent_helper("1", 0);
        getExponent_helper("11", 0);
        getExponent_helper("5 << 20", 20);
        getExponent_helper("5 >> 20", -20);
        getExponent_helper("-1", 0);
        getExponent_helper("-11", 0);
        getExponent_helper("-5 << 20", 20);
        getExponent_helper("-5 >> 20", -20);
    }
}
