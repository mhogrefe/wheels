package mho.wheels.misc;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.misc.FloatingPointUtils.predecessor;
import static mho.wheels.misc.FloatingPointUtils.successor;
import static mho.wheels.misc.FloatingPointUtils.toMantissaAndExponent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public strictfp class FloatingPointUtilsTest {
    @Test
    public void testIsNegativeZero_float() {
        assertFalse(FloatingPointUtils.isNegativeZero(0.0f));
        assertTrue(FloatingPointUtils.isNegativeZero(-0.0f));
        assertFalse(FloatingPointUtils.isNegativeZero(1.0f));
        assertFalse(FloatingPointUtils.isNegativeZero(-1.0f));
        assertFalse(FloatingPointUtils.isNegativeZero(Float.NaN));
        assertFalse(FloatingPointUtils.isNegativeZero(Float.POSITIVE_INFINITY));
        assertFalse(FloatingPointUtils.isNegativeZero(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void testIsNegativeZero_double() {
        assertFalse(FloatingPointUtils.isNegativeZero(0.0));
        assertTrue(FloatingPointUtils.isNegativeZero(-0.0));
        assertFalse(FloatingPointUtils.isNegativeZero(1.0));
        assertFalse(FloatingPointUtils.isNegativeZero(-1.0));
        assertFalse(FloatingPointUtils.isNegativeZero(Double.NaN));
        assertFalse(FloatingPointUtils.isNegativeZero(Double.POSITIVE_INFINITY));
        assertFalse(FloatingPointUtils.isNegativeZero(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testIsPositiveZero_float() {
        assertTrue(FloatingPointUtils.isPositiveZero(0.0f));
        assertFalse(FloatingPointUtils.isPositiveZero(-0.0f));
        assertFalse(FloatingPointUtils.isPositiveZero(1.0f));
        assertFalse(FloatingPointUtils.isPositiveZero(-1.0f));
        assertFalse(FloatingPointUtils.isPositiveZero(Float.NaN));
        assertFalse(FloatingPointUtils.isPositiveZero(Float.POSITIVE_INFINITY));
        assertFalse(FloatingPointUtils.isPositiveZero(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void testIsPositiveZero_double() {
        assertTrue(FloatingPointUtils.isPositiveZero(0.0));
        assertFalse(FloatingPointUtils.isPositiveZero(-0.0));
        assertFalse(FloatingPointUtils.isPositiveZero(1.0));
        assertFalse(FloatingPointUtils.isPositiveZero(-1.0));
        assertFalse(FloatingPointUtils.isPositiveZero(Double.NaN));
        assertFalse(FloatingPointUtils.isPositiveZero(Double.POSITIVE_INFINITY));
        assertFalse(FloatingPointUtils.isPositiveZero(Double.NEGATIVE_INFINITY));
    }

    private static void successor_float_helper(float x, @NotNull String output) {
        aeq(successor(x), output);
    }

    @Test
    public void testSuccessor_float() {
        successor_float_helper(1.0f, "1.0000001");
        successor_float_helper(1e20f, "1.0000001E20");
        successor_float_helper(-1.0f, "-0.99999994");
        successor_float_helper(-1e20f, "-9.999999E19");
        successor_float_helper((float) Math.PI, "3.141593");
        successor_float_helper((float) Math.sqrt(2), "1.4142137");
        successor_float_helper((float) -Math.PI, "-3.1415925");
        successor_float_helper((float) -Math.sqrt(2), "-1.4142134");
        successor_float_helper(0.0f, "1.4E-45");
        successor_float_helper(-0.0f, "1.4E-45");
        successor_float_helper(Float.MIN_VALUE, "2.8E-45");
        successor_float_helper(Float.MIN_NORMAL, "1.1754945E-38");
        successor_float_helper(-Float.MIN_VALUE, "-0.0");
        successor_float_helper(-Float.MIN_NORMAL, "-1.1754942E-38");
        successor_float_helper(Float.MAX_VALUE, "Infinity");
        successor_float_helper(-Float.MAX_VALUE, "-3.4028233E38");
        successor_float_helper(Float.NEGATIVE_INFINITY, "-3.4028235E38");
        try {
            successor(Float.POSITIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            successor(Float.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void predecessor_float_helper(float x, @NotNull String output) {
        aeq(predecessor(x), output);
    }

    @Test
    public void testPredecessor_float() {
        predecessor_float_helper(1.0f, "0.99999994");
        predecessor_float_helper(1e20f, "9.999999E19");
        predecessor_float_helper(-1.0f, "-1.0000001");
        predecessor_float_helper(-1e20f, "-1.0000001E20");
        predecessor_float_helper((float) Math.PI, "3.1415925");
        predecessor_float_helper((float) Math.sqrt(2), "1.4142134");
        predecessor_float_helper((float) -Math.PI, "-3.141593");
        predecessor_float_helper((float) -Math.sqrt(2), "-1.4142137");
        predecessor_float_helper(0.0f, "-1.4E-45");
        predecessor_float_helper(-0.0f, "-1.4E-45");
        predecessor_float_helper(Float.MIN_VALUE, "0.0");
        predecessor_float_helper(Float.MIN_NORMAL, "1.1754942E-38");
        predecessor_float_helper(-Float.MIN_VALUE, "-2.8E-45");
        predecessor_float_helper(-Float.MIN_NORMAL, "-1.1754945E-38");
        predecessor_float_helper(Float.MAX_VALUE, "3.4028233E38");
        predecessor_float_helper(-Float.MAX_VALUE, "-Infinity");
        predecessor_float_helper(Float.POSITIVE_INFINITY, "3.4028235E38");
        try {
            predecessor(Float.NEGATIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            predecessor(Float.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void successor_double_helper(double x, @NotNull String output) {
        aeq(successor(x), output);
    }

    @Test
    public void testSuccessor_double() {
        successor_double_helper(1.0, "1.0000000000000002");
        successor_double_helper(1e20, "1.0000000000000002E20");
        successor_double_helper(-1.0, "-0.9999999999999999");
        successor_double_helper(-1e20, "-9.999999999999998E19");
        successor_double_helper(Math.PI, "3.1415926535897936");
        successor_double_helper(Math.sqrt(2), "1.4142135623730954");
        successor_double_helper(-Math.PI, "-3.1415926535897927");
        successor_double_helper(-Math.sqrt(2), "-1.414213562373095");
        successor_double_helper(0.0, "4.9E-324");
        successor_double_helper(-0.0, "4.9E-324");
        successor_double_helper(Double.MIN_VALUE, "1.0E-323");
        successor_double_helper(Double.MIN_NORMAL, "2.225073858507202E-308");
        successor_double_helper(-Double.MIN_VALUE, "-0.0");
        successor_double_helper(-Double.MIN_NORMAL, "-2.225073858507201E-308");
        successor_double_helper(Double.MAX_VALUE, "Infinity");
        successor_double_helper(-Double.MAX_VALUE, "-1.7976931348623155E308");
        successor_double_helper(Double.NEGATIVE_INFINITY, "-1.7976931348623157E308");
        try {
            successor(Double.POSITIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            successor(Double.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void predecessor_double_helper(double x, @NotNull String output) {
        aeq(predecessor(x), output);
    }

    @Test
    public void testPredecessor_double() {
        predecessor_double_helper(1.0, "0.9999999999999999");
        predecessor_double_helper(1e20, "9.999999999999998E19");
        predecessor_double_helper(-1.0, "-1.0000000000000002");
        predecessor_double_helper(-1e20, "-1.0000000000000002E20");
        predecessor_double_helper(Math.PI, "3.1415926535897927");
        predecessor_double_helper(Math.sqrt(2), "1.414213562373095");
        predecessor_double_helper(-Math.PI, "-3.1415926535897936");
        predecessor_double_helper(-Math.sqrt(2), "-1.4142135623730954");
        predecessor_double_helper(0.0, "-4.9E-324");
        predecessor_double_helper(-0.0, "-4.9E-324");
        predecessor_double_helper(Double.MIN_VALUE, "0.0");
        predecessor_double_helper(Double.MIN_NORMAL, "2.225073858507201E-308");
        predecessor_double_helper(-Double.MIN_VALUE, "-1.0E-323");
        predecessor_double_helper(-Double.MIN_NORMAL, "-2.225073858507202E-308");
        predecessor_double_helper(Double.MAX_VALUE, "1.7976931348623155E308");
        predecessor_double_helper(-Double.MAX_VALUE, "-Infinity");
        predecessor_double_helper(Double.POSITIVE_INFINITY, "1.7976931348623157E308");
        try {
            predecessor(Double.NEGATIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            predecessor(Double.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void toMantissaAndExponent_float_helper(float f, @NotNull String output) {
        aeq(toMantissaAndExponent(f).get(), output);
    }

    private static void toMantissaAndExponent_float_empty_helper(float f) {
        assertFalse(toMantissaAndExponent(f).isPresent());
    }

    @Test
    public void toMantissaAndExponent_float() {
        toMantissaAndExponent_float_helper(0.0f, "(0, 0)");
        toMantissaAndExponent_float_helper(1.0f, "(1, 0)");
        toMantissaAndExponent_float_helper(0.5f, "(1, -1)");
        toMantissaAndExponent_float_helper(0.25f, "(1, -2)");
        toMantissaAndExponent_float_helper(1.0f / 3.0f, "(11184811, -25)");
        toMantissaAndExponent_float_helper(2.0f, "(1, 1)");
        toMantissaAndExponent_float_helper(4.0f, "(1, 2)");
        toMantissaAndExponent_float_helper(3.0f, "(3, 0)");
        toMantissaAndExponent_float_helper(1.5f, "(3, -1)");
        toMantissaAndExponent_float_helper(1000.0f, "(125, 3)");
        toMantissaAndExponent_float_helper(0.001f, "(8589935, -33)");
        toMantissaAndExponent_float_helper((float) Math.PI, "(13176795, -22)");
        toMantissaAndExponent_float_helper((float) Math.E, "(2850325, -20)");
        toMantissaAndExponent_float_helper(Float.MIN_VALUE, "(1, -149)");
        toMantissaAndExponent_float_helper(Float.MIN_NORMAL, "(1, -126)");
        toMantissaAndExponent_float_helper(Float.MAX_VALUE, "(16777215, 104)");
        toMantissaAndExponent_float_helper(-0.0f, "(0, 0)");
        toMantissaAndExponent_float_helper(-1.0f, "(-1, 0)");
        toMantissaAndExponent_float_helper(-0.5f, "(-1, -1)");
        toMantissaAndExponent_float_helper(-0.25f, "(-1, -2)");
        toMantissaAndExponent_float_helper(-1.0f / 3.0f, "(-11184811, -25)");
        toMantissaAndExponent_float_helper(-2.0f, "(-1, 1)");
        toMantissaAndExponent_float_helper(-4.0f, "(-1, 2)");
        toMantissaAndExponent_float_helper(-3.0f, "(-3, 0)");
        toMantissaAndExponent_float_helper(-1.5f, "(-3, -1)");
        toMantissaAndExponent_float_helper(-1000.0f, "(-125, 3)");
        toMantissaAndExponent_float_helper(-0.001f, "(-8589935, -33)");
        toMantissaAndExponent_float_helper((float) -Math.PI, "(-13176795, -22)");
        toMantissaAndExponent_float_helper((float) -Math.E, "(-2850325, -20)");
        toMantissaAndExponent_float_helper(-Float.MIN_VALUE, "(-1, -149)");
        toMantissaAndExponent_float_helper(-Float.MIN_NORMAL, "(-1, -126)");
        toMantissaAndExponent_float_helper(-Float.MAX_VALUE, "(-16777215, 104)");
        toMantissaAndExponent_float_empty_helper(Float.POSITIVE_INFINITY);
        toMantissaAndExponent_float_empty_helper(Float.NEGATIVE_INFINITY);
        toMantissaAndExponent_float_empty_helper(Float.NaN);
    }

    private static void toMantissaAndExponent_double_helper(double d, @NotNull String output) {
        aeq(toMantissaAndExponent(d).get(), output);
    }

    private static void toMantissaAndExponent_double_empty_helper(double d) {
        assertFalse(toMantissaAndExponent(d).isPresent());
    }

    @Test
    public void toMantissaAndExponent_double() {
        toMantissaAndExponent_double_helper(0.0, "(0, 0)");
        toMantissaAndExponent_double_helper(1.0, "(1, 0)");
        toMantissaAndExponent_double_helper(0.5, "(1, -1)");
        toMantissaAndExponent_double_helper(0.25, "(1, -2)");
        toMantissaAndExponent_double_helper(1.0 / 3.0, "(6004799503160661, -54)");
        toMantissaAndExponent_double_helper(2.0, "(1, 1)");
        toMantissaAndExponent_double_helper(4.0, "(1, 2)");
        toMantissaAndExponent_double_helper(3.0, "(3, 0)");
        toMantissaAndExponent_double_helper(1.5, "(3, -1)");
        toMantissaAndExponent_double_helper(1000.0, "(125, 3)");
        toMantissaAndExponent_double_helper(0.001, "(1152921504606847, -60)");
        toMantissaAndExponent_double_helper(Math.PI, "(884279719003555, -48)");
        toMantissaAndExponent_double_helper(Math.E, "(6121026514868073, -51)");
        toMantissaAndExponent_double_helper(Double.MIN_VALUE, "(1, -1074)");
        toMantissaAndExponent_double_helper(Double.MIN_NORMAL, "(1, -1022)");
        toMantissaAndExponent_double_helper(Double.MAX_VALUE, "(9007199254740991, 971)");
        toMantissaAndExponent_double_helper(-0.0, "(0, 0)");
        toMantissaAndExponent_double_helper(-1.0, "(-1, 0)");
        toMantissaAndExponent_double_helper(-0.5, "(-1, -1)");
        toMantissaAndExponent_double_helper(-0.25, "(-1, -2)");
        toMantissaAndExponent_double_helper(-1.0 / 3.0, "(-6004799503160661, -54)");
        toMantissaAndExponent_double_helper(-2.0, "(-1, 1)");
        toMantissaAndExponent_double_helper(-4.0, "(-1, 2)");
        toMantissaAndExponent_double_helper(-3.0, "(-3, 0)");
        toMantissaAndExponent_double_helper(-1.5, "(-3, -1)");
        toMantissaAndExponent_double_helper(-1000.0, "(-125, 3)");
        toMantissaAndExponent_double_helper(-0.001, "(-1152921504606847, -60)");
        toMantissaAndExponent_double_helper(-Math.PI, "(-884279719003555, -48)");
        toMantissaAndExponent_double_helper(-Math.E, "(-6121026514868073, -51)");
        toMantissaAndExponent_double_helper(-Double.MIN_VALUE, "(-1, -1074)");
        toMantissaAndExponent_double_helper(-Double.MIN_NORMAL, "(-1, -1022)");
        toMantissaAndExponent_double_helper(-Double.MAX_VALUE, "(-9007199254740991, 971)");
        toMantissaAndExponent_double_empty_helper(Double.POSITIVE_INFINITY);
        toMantissaAndExponent_double_empty_helper(Double.NEGATIVE_INFINITY);
        toMantissaAndExponent_double_empty_helper(Double.NaN);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}