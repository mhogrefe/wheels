package mho.wheels.misc;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.misc.FloatingPointUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static org.junit.Assert.*;

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

    private static void successor_float_helper(float f, float g) {
        aeq(successor(f), g);
    }

    private static void successor_float_fail_helper(float f) {
        try {
            successor(f);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSuccessor_float() {
        successor_float_helper(1.0f, 1.0000001f);
        successor_float_helper(1.0E20f, 1.0000001E20f);
        successor_float_helper(-1.0f, -0.99999994f);
        successor_float_helper(-1.0E20f, -9.999999E19f);
        successor_float_helper((float) Math.PI, 3.141593f);
        successor_float_helper((float) Math.sqrt(2), 1.4142137f);
        successor_float_helper((float) -Math.PI, -3.1415925f);
        successor_float_helper((float) -Math.sqrt(2), -1.4142134f);
        successor_float_helper(0.0f, 1.4E-45f);
        successor_float_helper(-0.0f, 1.4E-45f);
        successor_float_helper(Float.MIN_VALUE, 2.8E-45f);
        successor_float_helper(Float.MIN_NORMAL, 1.1754945E-38f);
        successor_float_helper(-Float.MIN_VALUE, -0.0f);
        successor_float_helper(-Float.MIN_NORMAL, -1.1754942E-38f);
        successor_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY);
        successor_float_helper(-Float.MAX_VALUE, -3.4028233E38f);
        successor_float_helper(Float.NEGATIVE_INFINITY, -3.4028235E38f);
        successor_float_fail_helper(Float.POSITIVE_INFINITY);
        successor_float_fail_helper(Float.NaN);
    }

    private static void predecessor_float_helper(float f, float g) {
        aeq(predecessor(f), g);
    }

    private static void predecessor_float_fail_helper(float f) {
        try {
            predecessor(f);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPredecessor_float() {
        predecessor_float_helper(1.0f, 0.99999994f);
        predecessor_float_helper(1.0E20f, 9.999999E19f);
        predecessor_float_helper(-1.0f, -1.0000001f);
        predecessor_float_helper(-1.0E20f, -1.0000001E20f);
        predecessor_float_helper((float) Math.PI, 3.1415925f);
        predecessor_float_helper((float) Math.sqrt(2), 1.4142134f);
        predecessor_float_helper((float) -Math.PI, -3.141593f);
        predecessor_float_helper((float) -Math.sqrt(2), -1.4142137f);
        predecessor_float_helper(0.0f, -1.4E-45f);
        predecessor_float_helper(-0.0f, -1.4E-45f);
        predecessor_float_helper(Float.MIN_VALUE, 0.0f);
        predecessor_float_helper(Float.MIN_NORMAL, 1.1754942E-38f);
        predecessor_float_helper(-Float.MIN_VALUE, -2.8E-45f);
        predecessor_float_helper(-Float.MIN_NORMAL, -1.1754945E-38f);
        predecessor_float_helper(Float.MAX_VALUE, 3.4028233E38f);
        predecessor_float_helper(-Float.MAX_VALUE, Float.NEGATIVE_INFINITY);
        predecessor_float_helper(Float.POSITIVE_INFINITY, 3.4028235E38f);
        predecessor_float_fail_helper(Float.NEGATIVE_INFINITY);
        predecessor_float_fail_helper(Float.NaN);
    }

    private static void successor_double_helper(double d, double e) {
        aeq(successor(d), e);
    }

    private static void successor_double_fail_helper(double d) {
        try {
            successor(d);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSuccessor_double() {
        successor_double_helper(1.0, 1.0000000000000002);
        successor_double_helper(1.0E20, 1.0000000000000002E20);
        successor_double_helper(-1.0, -0.9999999999999999);
        successor_double_helper(-1.0E20, -9.999999999999998E19);
        successor_double_helper(Math.PI, 3.1415926535897936);
        successor_double_helper(Math.sqrt(2), 1.4142135623730954);
        successor_double_helper(-Math.PI, -3.1415926535897927);
        successor_double_helper(-Math.sqrt(2), -1.414213562373095);
        successor_double_helper(0.0, 4.9E-324);
        successor_double_helper(-0.0, 4.9E-324);
        successor_double_helper(Double.MIN_VALUE, 1.0E-323);
        successor_double_helper(Double.MIN_NORMAL, 2.225073858507202E-308);
        successor_double_helper(-Double.MIN_VALUE, -0.0);
        successor_double_helper(-Double.MIN_NORMAL, -2.225073858507201E-308);
        successor_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY);
        successor_double_helper(-Double.MAX_VALUE, -1.7976931348623155E308);
        successor_double_helper(Double.NEGATIVE_INFINITY, -1.7976931348623157E308);
        successor_double_fail_helper(Double.POSITIVE_INFINITY);
        successor_double_fail_helper(Double.NaN);
    }

    private static void predecessor_double_helper(double d, double e) {
        aeq(predecessor(d), e);
    }

    private static void predecessor_double_fail_helper(double d) {
        try {
            predecessor(d);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPredecessor_double() {
        predecessor_double_helper(1.0, 0.9999999999999999);
        predecessor_double_helper(1.0E20, 9.999999999999998E19);
        predecessor_double_helper(-1.0, -1.0000000000000002);
        predecessor_double_helper(-1.0E20, -1.0000000000000002E20);
        predecessor_double_helper(Math.PI, 3.1415926535897927);
        predecessor_double_helper(Math.sqrt(2), 1.414213562373095);
        predecessor_double_helper(-Math.PI, -3.1415926535897936);
        predecessor_double_helper(-Math.sqrt(2), -1.4142135623730954);
        predecessor_double_helper(0.0, -4.9E-324);
        predecessor_double_helper(-0.0, -4.9E-324);
        predecessor_double_helper(Double.MIN_VALUE, 0.0);
        predecessor_double_helper(Double.MIN_NORMAL, 2.225073858507201E-308);
        predecessor_double_helper(-Double.MIN_VALUE, -1.0E-323);
        predecessor_double_helper(-Double.MIN_NORMAL, -2.225073858507202E-308);
        predecessor_double_helper(Double.MAX_VALUE, 1.7976931348623155E308);
        predecessor_double_helper(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY);
        predecessor_double_helper(Double.POSITIVE_INFINITY, 1.7976931348623157E308);
        predecessor_double_fail_helper(Double.NEGATIVE_INFINITY);
        predecessor_double_fail_helper(Double.NaN);
    }

    private static void floatFromMantissaAndExponent_helper(int m, int e, float f) {
        aeq(floatFromMantissaAndExponent(m, e).get(), f);
    }

    private static void floatFromMantissaAndExponent_empty_helper(int m, int e) {
        assertFalse(floatFromMantissaAndExponent(m, e).isPresent());
    }

    @Test
    public void testFloatFromMantissaAndExponent() {
        floatFromMantissaAndExponent_helper(1, 0, 1.0f);
        floatFromMantissaAndExponent_helper(2842171, 45, 1.0E20f);
        floatFromMantissaAndExponent_helper(-1, 0, -1.0f);
        floatFromMantissaAndExponent_helper(-2842171, 45, -1.0E20f);
        floatFromMantissaAndExponent_helper(13176795, -22, 3.1415927f);
        floatFromMantissaAndExponent_helper(11863283, -23, 1.4142135f);
        floatFromMantissaAndExponent_helper(-13176795, -22, -3.1415927f);
        floatFromMantissaAndExponent_helper(-11863283, -23, -1.4142135f);
        floatFromMantissaAndExponent_helper(0, 0, 0.0f);
        floatFromMantissaAndExponent_helper(1, -149, 1.4E-45f);
        floatFromMantissaAndExponent_helper(1, -126, 1.17549435E-38f);
        floatFromMantissaAndExponent_helper(-1, -149, -1.4E-45f);
        floatFromMantissaAndExponent_helper(-1, -126, -1.17549435E-38f);
        floatFromMantissaAndExponent_helper(16777215, 104, 3.4028235E38f);
        floatFromMantissaAndExponent_helper(-16777215, 104, -3.4028235E38f);
        floatFromMantissaAndExponent_helper(1, 100, 1.2676506E30f);
        floatFromMantissaAndExponent_helper(1, -100, 7.888609E-31f);
        floatFromMantissaAndExponent_helper(123, 45, 4.32767777E15f);
        floatFromMantissaAndExponent_helper(-1, 100, -1.2676506E30f);
        floatFromMantissaAndExponent_helper(-1, -100, -7.888609E-31f);
        floatFromMantissaAndExponent_helper(-123, 45, -4.32767777E15f);
        floatFromMantissaAndExponent_empty_helper(2, 10);
        floatFromMantissaAndExponent_empty_helper(1, 1000);
        floatFromMantissaAndExponent_empty_helper(1, -1000);
        floatFromMantissaAndExponent_empty_helper(1111111111, 0);
    }

    private static void doubleFromMantissaAndExponent_helper(long m, int e, double d) {
        aeq(doubleFromMantissaAndExponent(m, e).get(), d);
    }

    private static void doubleFromMantissaAndExponent_empty_helper(long m, int e) {
        assertFalse(doubleFromMantissaAndExponent(m, e).isPresent());
    }

    @Test
    public void testDoubleFromMantissaAndExponent() {
        doubleFromMantissaAndExponent_helper(1L, 0, 1.0);
        doubleFromMantissaAndExponent_helper(95367431640625L, 20, 1.0E20);
        doubleFromMantissaAndExponent_helper(-1L, 0, -1.0);
        doubleFromMantissaAndExponent_helper(-95367431640625L, 20, -1.0E20);
        doubleFromMantissaAndExponent_helper(884279719003555L, -48, 3.141592653589793);
        doubleFromMantissaAndExponent_helper(6369051672525773L, -52, 1.4142135623730951);
        doubleFromMantissaAndExponent_helper(-884279719003555L, -48, -3.141592653589793);
        doubleFromMantissaAndExponent_helper(-6369051672525773L, -52, -1.4142135623730951);
        doubleFromMantissaAndExponent_helper(0L, 0, 0.0);
        doubleFromMantissaAndExponent_helper(1L, -1074, 4.9E-324);
        doubleFromMantissaAndExponent_helper(1L, -1022, 2.2250738585072014E-308);
        doubleFromMantissaAndExponent_helper(-1L, -1074, -4.9E-324);
        doubleFromMantissaAndExponent_helper(-1L, -1022, -2.2250738585072014E-308);
        doubleFromMantissaAndExponent_helper(9007199254740991L, 971, 1.7976931348623157E308);
        doubleFromMantissaAndExponent_helper(-9007199254740991L, 971, -1.7976931348623157E308);
        doubleFromMantissaAndExponent_helper(1L, 1000, 1.0715086071862673E301);
        doubleFromMantissaAndExponent_helper(1L, -1000, 9.332636185032189E-302);
        doubleFromMantissaAndExponent_helper(123L, 456, 2.288669775062007E139);
        doubleFromMantissaAndExponent_helper(-1L, 1000, -1.0715086071862673E301);
        doubleFromMantissaAndExponent_helper(-1L, -1000, -9.332636185032189E-302);
        doubleFromMantissaAndExponent_helper(-123L, 456, -2.288669775062007E139);
        doubleFromMantissaAndExponent_empty_helper(2L, 10);
        doubleFromMantissaAndExponent_empty_helper(1L, 10000);
        doubleFromMantissaAndExponent_empty_helper(1L, -10000);
        doubleFromMantissaAndExponent_empty_helper(1111111111111111111L, 0);
    }

    private static void toMantissaAndExponent_float_helper(float x, @NotNull String output) {
        aeq(toMantissaAndExponent(x).get(), output);
    }

    private static void toMantissaAndExponent_float_empty_helper(float x) {
        assertFalse(toMantissaAndExponent(x).isPresent());
    }

    @Test
    public void testToMantissaAndExponent_float() {
        toMantissaAndExponent_float_helper(1.0f, "(1, 0)");
        toMantissaAndExponent_float_helper(1.0E20F, "(2842171, 45)");
        toMantissaAndExponent_float_helper(-1.0f, "(-1, 0)");
        toMantissaAndExponent_float_helper(-1.0E20f, "(-2842171, 45)");
        toMantissaAndExponent_float_helper((float) Math.PI, "(13176795, -22)");
        toMantissaAndExponent_float_helper((float) Math.sqrt(2), "(11863283, -23)");
        toMantissaAndExponent_float_helper((float) -Math.PI, "(-13176795, -22)");
        toMantissaAndExponent_float_helper((float) -Math.sqrt(2), "(-11863283, -23)");
        toMantissaAndExponent_float_helper(0.0f, "(0, 0)");
        toMantissaAndExponent_float_helper(-0.0f, "(0, 0)");
        toMantissaAndExponent_float_helper(Float.MIN_VALUE, "(1, -149)");
        toMantissaAndExponent_float_helper(Float.MIN_NORMAL, "(1, -126)");
        toMantissaAndExponent_float_helper(-Float.MIN_VALUE, "(-1, -149)");
        toMantissaAndExponent_float_helper(-Float.MIN_NORMAL, "(-1, -126)");
        toMantissaAndExponent_float_helper(Float.MAX_VALUE, "(16777215, 104)");
        toMantissaAndExponent_float_helper(-Float.MAX_VALUE, "(-16777215, 104)");
        toMantissaAndExponent_float_helper(1.2676506E30f, "(1, 100)");
        toMantissaAndExponent_float_helper(7.888609E-31f, "(1, -100)");
        toMantissaAndExponent_float_helper(4.32767777E15f, "(123, 45)");
        toMantissaAndExponent_float_helper(-1.2676506E30f, "(-1, 100)");
        toMantissaAndExponent_float_helper(-7.888609E-31f, "(-1, -100)");
        toMantissaAndExponent_float_helper(-4.32767777E15f, "(-123, 45)");
        toMantissaAndExponent_float_empty_helper(Float.POSITIVE_INFINITY);
        toMantissaAndExponent_float_empty_helper(Float.NEGATIVE_INFINITY);
        toMantissaAndExponent_float_empty_helper(Float.NaN);
    }

    private static void toMantissaAndExponent_double_helper(double x, @NotNull String output) {
        aeq(toMantissaAndExponent(x).get(), output);
    }

    private static void toMantissaAndExponent_double_empty_helper(double x) {
        assertFalse(toMantissaAndExponent(x).isPresent());
    }

    @Test
    public void testToMantissaAndExponent_double() {
        toMantissaAndExponent_double_helper(1.0, "(1, 0)");
        toMantissaAndExponent_double_helper(1.0E20, "(95367431640625, 20)");
        toMantissaAndExponent_double_helper(-1.0, "(-1, 0)");
        toMantissaAndExponent_double_helper(-1.0E20, "(-95367431640625, 20)");
        toMantissaAndExponent_double_helper(Math.PI, "(884279719003555, -48)");
        toMantissaAndExponent_double_helper(Math.sqrt(2), "(6369051672525773, -52)");
        toMantissaAndExponent_double_helper(-Math.PI, "(-884279719003555, -48)");
        toMantissaAndExponent_double_helper(-Math.sqrt(2), "(-6369051672525773, -52)");
        toMantissaAndExponent_double_helper(0.0, "(0, 0)");
        toMantissaAndExponent_double_helper(-0.0, "(0, 0)");
        toMantissaAndExponent_double_helper(Double.MIN_VALUE, "(1, -1074)");
        toMantissaAndExponent_double_helper(Double.MIN_NORMAL, "(1, -1022)");
        toMantissaAndExponent_double_helper(-Double.MIN_VALUE, "(-1, -1074)");
        toMantissaAndExponent_double_helper(-Double.MIN_NORMAL, "(-1, -1022)");
        toMantissaAndExponent_double_helper(Double.MAX_VALUE, "(9007199254740991, 971)");
        toMantissaAndExponent_double_helper(-Double.MAX_VALUE, "(-9007199254740991, 971)");
        toMantissaAndExponent_double_empty_helper(Double.POSITIVE_INFINITY);
        toMantissaAndExponent_double_empty_helper(Double.NEGATIVE_INFINITY);
        toMantissaAndExponent_double_empty_helper(Double.NaN);
    }

    private static void absNegativeZeros_helper(float x, float y) {
        aeq(absNegativeZeros(x), y);
    }

    @Test
    public void testAbsNegativeZeros_float_helper() {
        absNegativeZeros_helper(1.0f, 1.0f);
        absNegativeZeros_helper(-1.0f, -1.0f);
        absNegativeZeros_helper(0.0f, 0.0f);
        absNegativeZeros_helper(-0.0f, 0.0f);
        absNegativeZeros_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        absNegativeZeros_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        absNegativeZeros_helper(Float.NaN, Float.NaN);
    }

    private static void absNegativeZeros_helper(double x, double y) {
        aeq(absNegativeZeros(x), y);
    }

    @Test
    public void testAbsNegativeZeros_double_helper() {
        absNegativeZeros_helper(1.0, 1.0);
        absNegativeZeros_helper(-1.0, -1.0);
        absNegativeZeros_helper(0.0, 0.0);
        absNegativeZeros_helper(-0.0, 0.0);
        absNegativeZeros_helper(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        absNegativeZeros_helper(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        absNegativeZeros_helper(Double.NaN, Double.NaN);
    }
}
