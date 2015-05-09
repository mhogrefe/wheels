package mho.wheels.misc;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.misc.FloatingPointUtils.predecessor;
import static mho.wheels.misc.FloatingPointUtils.successor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public strictfp class FloatingPointUtilsTest {
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

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}