package mho.wheels.misc;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.misc.FloatingPointUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static org.junit.Assert.*;

public strictfp class FloatingPointUtilsTest {
    @Test
    public void testConstants() {
        aeq(FLOAT_EXPONENT_WIDTH, 8);
        aeq(DOUBLE_EXPONENT_WIDTH, 11);
        aeq(FLOAT_FRACTION_WIDTH, 23);
        aeq(DOUBLE_FRACTION_WIDTH, 52);
        aeq(MIN_SUBNORMAL_FLOAT_EXPONENT, -149);
        aeq(MIN_SUBNORMAL_DOUBLE_EXPONENT, -1074);
        aeq(SCALED_UP_MAX_FLOAT,
                "242833597054204979200408310406566737244312373222769356951406046285165034661509857280");
        aeq(SCALED_UP_MAX_DOUBLE,
                "363857141251215733008468006984567498428427744310602690309735631992518352027631318742205104461997525" +
                "781461689595255359755041236607412597305594915359190782200698392412987448013052928786408355279308639" +
                "946743576115889990206935944747628988479302915525946901702031872150456880949556607739225761379698303" +
                "426118602250199355821996011214692492231498724661213715671558623030843303146025660694164325513330061" +
                "947744775142603512019698593680602201312344881981489765361696383056969005048388307197608755142462165" +
                "089768038827285826773521770041292888478544630840063729813907563445195499310977439636039716323348918" +
                "36831978686870043355177324550146752512");
    }

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
        toMantissaAndExponent_double_helper(1.0715086071862673E301, "(1, 1000)");
        toMantissaAndExponent_double_helper(9.332636185032189E-302, "(1, -1000)");
        toMantissaAndExponent_double_helper(2.288669775062007E139, "(123, 456)");
        toMantissaAndExponent_double_helper(-1.0715086071862673E301, "(-1, 1000)");
        toMantissaAndExponent_double_helper(-9.332636185032189E-302, "(-1, -1000)");
        toMantissaAndExponent_double_helper(-2.288669775062007E139, "(-123, 456)");
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

    private static void scaleUp_float_helper(float f, @NotNull String output) {
        aeq(scaleUp(f).get(), output);
    }

    private static void scaleUp_float_empty_helper(float f) {
        assertFalse(scaleUp(f).isPresent());
    }

    @Test
    public void testScaleUp_float() {
        scaleUp_float_helper(1.0f, "713623846352979940529142984724747568191373312");
        scaleUp_float_helper(1.0E20F, "71362386065462791412927717842990225862080190703791157742208548864");
        scaleUp_float_helper(-1.0f, "-713623846352979940529142984724747568191373312");
        scaleUp_float_helper(-1.0E20f, "-71362386065462791412927717842990225862080190703791157742208548864");
        scaleUp_float_helper((float) Math.PI, "2241915495515993670335938605166943104936181760");
        scaleUp_float_helper((float) Math.sqrt(2), "1009216504673232904412793275744242370726592512");
        scaleUp_float_helper((float) -Math.PI, "-2241915495515993670335938605166943104936181760");
        scaleUp_float_helper((float) -Math.sqrt(2), "-1009216504673232904412793275744242370726592512");
        scaleUp_float_helper(0.0f, "0");
        scaleUp_float_helper(-0.0f, "0");
        scaleUp_float_helper(Float.MIN_VALUE, "1");
        scaleUp_float_helper(Float.MIN_NORMAL, "8388608");
        scaleUp_float_helper(-Float.MIN_VALUE, "-1");
        scaleUp_float_helper(-Float.MIN_NORMAL, "-8388608");
        scaleUp_float_helper(Float.MAX_VALUE,
                "242833597054204979200408310406566737244312373222769356951406046285165034661509857280");
        scaleUp_float_helper(-Float.MAX_VALUE,
                "-242833597054204979200408310406566737244312373222769356951406046285165034661509857280");
        scaleUp_float_empty_helper(Float.POSITIVE_INFINITY);
        scaleUp_float_empty_helper(Float.NEGATIVE_INFINITY);
        scaleUp_float_empty_helper(Float.NaN);
    }

    private static void scaleUp_double_helper(double d, @NotNull String output) {
        aeq(scaleUp(d).get(), output);
    }

    private static void scaleUp_double_empty_helper(double d) {
        assertFalse(scaleUp(d).isPresent());
    }

    @Test
    public void testScaleUp_double() {
        scaleUp_double_helper(1.0,
                "202402253307310618352495346718917307049556649764142118356901358027430339567995346891960383701437124" +
                "495187077864316811911389808737385793476867013399940738509921517424276566361364466907742093216341239" +
                "767678472745068562007483424692698618103355649159556340810056512358769552333414615230502532186327508" +
                "646006263307707741093494784");
        scaleUp_double_helper(1.0E20,
                "20240225330731061835249534671891730704955664976414211835690135802743033956799534689196038370143712" +
                "44951870778643168119113898087373857934768670133999407385099215174242765663613644669077420932163412" +
                "39767678472745068562007483424692698618103355649159556340810056512358769552333414615230502532186327" +
                "50864600626330770774109349478400000000000000000000");
        scaleUp_double_helper(-1.0,
                "-20240225330731061835249534671891730704955664976414211835690135802743033956799534689196038370143712" +
                "449518707786431681191138980873738579347686701339994073850992151742427656636136446690774209321634123" +
                "976767847274506856200748342469269861810335564915955634081005651235876955233341461523050253218632750" +
                "8646006263307707741093494784");
        scaleUp_double_helper(-1.0E20,
                "-20240225330731061835249534671891730704955664976414211835690135802743033956799534689196038370143712" +
                "449518707786431681191138980873738579347686701339994073850992151742427656636136446690774209321634123" +
                "976767847274506856200748342469269861810335564915955634081005651235876955233341461523050253218632750" +
                "864600626330770774109349478400000000000000000000");
        scaleUp_double_helper(Math.PI,
                "635865432060267445468214182091255143872403807686233472603792193275656356707688919982837471838052747" +
                "972478524629983029917167695672135057166860157826274832966513867892628499855176210243632893770193919" +
                "817964592300301104964149478683857093638587052393633657306350059152722017417798014182989122203290511" +
                "554679949608784156447211520");
        scaleUp_double_helper(Math.sqrt(2),
                "286240011682073328362787334263691923924391219706463276968985538734844037157151109548568235817006833" +
                "651987366513964422661132007770600072130077881251753294643304641697510507371000948958629701213985344" +
                "497086249726915278714709715136272639151964413490064246690116540701128871898413930195307402052395949" +
                "162786919083459855612116992");
        scaleUp_double_helper(-Math.PI,
                "-63586543206026744546821418209125514387240380768623347260379219327565635670768891998283747183805274" +
                "797247852462998302991716769567213505716686015782627483296651386789262849985517621024363289377019391" +
                "981796459230030110496414947868385709363858705239363365730635005915272201741779801418298912220329051" +
                "1554679949608784156447211520");
        scaleUp_double_helper(-Math.sqrt(2),
                "-28624001168207332836278733426369192392439121970646327696898553873484403715715110954856823581700683" +
                "365198736651396442266113200777060007213007788125175329464330464169751050737100094895862970121398534" +
                "449708624972691527871470971513627263915196441349006424669011654070112887189841393019530740205239594" +
                "9162786919083459855612116992");
        scaleUp_double_helper(0.0, "0");
        scaleUp_double_helper(-0.0, "0");
        scaleUp_double_helper(Double.MIN_VALUE, "1");
        scaleUp_double_helper(Double.MIN_NORMAL, "4503599627370496");
        scaleUp_double_helper(-Double.MIN_VALUE, "-1");
        scaleUp_double_helper(-Double.MIN_NORMAL, "-4503599627370496");
        scaleUp_double_helper(Double.MAX_VALUE,
                "363857141251215733008468006984567498428427744310602690309735631992518352027631318742205104461997525" +
                "781461689595255359755041236607412597305594915359190782200698392412987448013052928786408355279308639" +
                "946743576115889990206935944747628988479302915525946901702031872150456880949556607739225761379698303" +
                "426118602250199355821996011214692492231498724661213715671558623030843303146025660694164325513330061" +
                "947744775142603512019698593680602201312344881981489765361696383056969005048388307197608755142462165" +
                "089768038827285826773521770041292888478544630840063729813907563445195499310977439636039716323348918" +
                "36831978686870043355177324550146752512");
        scaleUp_double_helper(-Double.MAX_VALUE,
                "-36385714125121573300846800698456749842842774431060269030973563199251835202763131874220510446199752" +
                "578146168959525535975504123660741259730559491535919078220069839241298744801305292878640835527930863" +
                "994674357611588999020693594474762898847930291552594690170203187215045688094955660773922576137969830" +
                "342611860225019935582199601121469249223149872466121371567155862303084330314602566069416432551333006" +
                "194774477514260351201969859368060220131234488198148976536169638305696900504838830719760875514246216" +
                "508976803882728582677352177004129288847854463084006372981390756344519549931097743963603971632334891" +
                "836831978686870043355177324550146752512");
        scaleUp_double_empty_helper(Double.POSITIVE_INFINITY);
        scaleUp_double_empty_helper(Double.NEGATIVE_INFINITY);
        scaleUp_double_empty_helper(Double.NaN);
    }
}
