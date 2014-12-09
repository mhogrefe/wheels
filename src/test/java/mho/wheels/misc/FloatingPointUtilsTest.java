package mho.wheels.misc;

import org.junit.Test;

import static mho.wheels.misc.FloatingPointUtils.predecessor;
import static mho.wheels.misc.FloatingPointUtils.successor;
import static org.junit.Assert.*;

public class FloatingPointUtilsTest {
    @Test
    public void testSuccessor_float() {
        aeq(successor(1.0f), "1.0000001");
        aeq(successor(1e20f), "1.0000001E20");
        aeq(successor(-1.0f), "-0.99999994");
        aeq(successor(-1e20f), "-9.999999E19");
        aeq(successor((float) Math.PI), "3.141593");
        aeq(successor((float) Math.sqrt(2)), "1.4142137");
        aeq(successor((float) -Math.PI), "-3.1415925");
        aeq(successor((float) -Math.sqrt(2)), "-1.4142134");
        aeq(successor(0.0f), "1.4E-45");
        aeq(successor(-0.0f), "1.4E-45");
        aeq(successor(Float.MIN_VALUE), "2.8E-45");
        aeq(successor(Float.MIN_NORMAL), "1.1754945E-38");
        aeq(successor(-Float.MIN_VALUE), "-0.0");
        aeq(successor(-Float.MIN_NORMAL), "-1.1754942E-38");
        aeq(successor(Float.MAX_VALUE), "Infinity");
        aeq(successor(-Float.MAX_VALUE), "-3.4028233E38");
        aeq(successor(Float.NEGATIVE_INFINITY), "-3.4028235E38");
        try {
            successor(Float.POSITIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            successor(Float.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPredecessor_float() {
        aeq(predecessor(1.0f), "0.99999994");
        aeq(predecessor(1e20f), "9.999999E19");
        aeq(predecessor(-1.0f), "-1.0000001");
        aeq(predecessor(-1e20f), "-1.0000001E20");
        aeq(predecessor((float) Math.PI), "3.1415925");
        aeq(predecessor((float) Math.sqrt(2)), "1.4142134");
        aeq(predecessor((float) -Math.PI), "-3.141593");
        aeq(predecessor((float) -Math.sqrt(2)), "-1.4142137");
        aeq(predecessor(0.0f), "-1.4E-45");
        aeq(predecessor(-0.0f), "-1.4E-45");
        aeq(predecessor(Float.MIN_VALUE), "0.0");
        aeq(predecessor(Float.MIN_NORMAL), "1.1754942E-38");
        aeq(predecessor(-Float.MIN_VALUE), "-2.8E-45");
        aeq(predecessor(-Float.MIN_NORMAL), "-1.1754945E-38");
        aeq(predecessor(Float.MAX_VALUE), "3.4028233E38");
        aeq(predecessor(-Float.MAX_VALUE), "-Infinity");
        aeq(predecessor(Float.POSITIVE_INFINITY), "3.4028235E38");
        try {
            predecessor(Float.NEGATIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            predecessor(Float.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSuccessor_double() {
        aeq(successor(1.0), "1.0000000000000002");
        aeq(successor(1e20), "1.0000000000000002E20");
        aeq(successor(-1.0), "-0.9999999999999999");
        aeq(successor(-1e20), "-9.999999999999998E19");
        aeq(successor(Math.PI), "3.1415926535897936");
        aeq(successor(Math.sqrt(2)), "1.4142135623730954");
        aeq(successor(-Math.PI), "-3.1415926535897927");
        aeq(successor(-Math.sqrt(2)), "-1.414213562373095");
        aeq(successor(0.0), "4.9E-324");
        aeq(successor(-0.0), "4.9E-324");
        aeq(successor(Double.MIN_VALUE), "1.0E-323");
        aeq(successor(Double.MIN_NORMAL), "2.225073858507202E-308");
        aeq(successor(-Double.MIN_VALUE), "-0.0");
        aeq(successor(-Double.MIN_NORMAL), "-2.225073858507201E-308");
        aeq(successor(Double.MAX_VALUE), "Infinity");
        aeq(successor(-Double.MAX_VALUE), "-1.7976931348623155E308");
        aeq(successor(Double.NEGATIVE_INFINITY), "-1.7976931348623157E308");
        try {
            successor(Double.POSITIVE_INFINITY);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            successor(Double.NaN);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPredecessor_double() {
        aeq(predecessor(1.0), "0.9999999999999999");
        aeq(predecessor(1e20), "9.999999999999998E19");
        aeq(predecessor(-1.0), "-1.0000000000000002");
        aeq(predecessor(-1e20), "-1.0000000000000002E20");
        aeq(predecessor(Math.PI), "3.1415926535897927");
        aeq(predecessor(Math.sqrt(2)), "1.414213562373095");
        aeq(predecessor(-Math.PI), "-3.1415926535897936");
        aeq(predecessor(-Math.sqrt(2)), "-1.4142135623730954");
        aeq(predecessor(0.0), "-4.9E-324");
        aeq(predecessor(-0.0), "-4.9E-324");
        aeq(predecessor(Double.MIN_VALUE), "0.0");
        aeq(predecessor(Double.MIN_NORMAL), "2.225073858507201E-308");
        aeq(predecessor(-Double.MIN_VALUE), "-1.0E-323");
        aeq(predecessor(-Double.MIN_NORMAL), "-2.225073858507202E-308");
        aeq(predecessor(Double.MAX_VALUE), "1.7976931348623155E308");
        aeq(predecessor(-Double.MAX_VALUE), "-Infinity");
        aeq(predecessor(Double.POSITIVE_INFINITY), "1.7976931348623157E308");
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