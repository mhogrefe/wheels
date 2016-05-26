package mho.wheels.numberUtils;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.numberUtils.FloatingPointUtils.*;
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
        aeq(POSITIVE_FINITE_FLOAT_COUNT, 2139095039);
        aeq(POSITIVE_FINITE_DOUBLE_COUNT, 9218868437227405311L);
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

    private static void isNegativeZero_float_helper(float input, boolean output) {
        aeq(isNegativeZero(input), output);
    }

    @Test
    public void testIsNegativeZero_float() {
        isNegativeZero_float_helper(0.0f, false);
        isNegativeZero_float_helper(-0.0f, true);
        isNegativeZero_float_helper(1.0f, false);
        isNegativeZero_float_helper(-1.0f, false);
        isNegativeZero_float_helper(Float.NaN, false);
        isNegativeZero_float_helper(Float.POSITIVE_INFINITY, false);
        isNegativeZero_float_helper(Float.NEGATIVE_INFINITY, false);
    }

    private static void isNegativeZero_double_helper(double input, boolean output) {
        aeq(isNegativeZero(input), output);
    }

    @Test
    public void testIsNegativeZero_double() {
        isNegativeZero_double_helper(0.0, false);
        isNegativeZero_double_helper(-0.0, true);
        isNegativeZero_double_helper(1.0, false);
        isNegativeZero_double_helper(-1.0, false);
        isNegativeZero_double_helper(Double.NaN, false);
        isNegativeZero_double_helper(Double.POSITIVE_INFINITY, false);
        isNegativeZero_double_helper(Double.NEGATIVE_INFINITY, false);
    }

    private static void isPositiveZero_float_helper(float input, boolean output) {
        aeq(isPositiveZero(input), output);
    }

    @Test
    public void testIsPositiveZero_float() {
        isPositiveZero_float_helper(0.0f, true);
        isPositiveZero_float_helper(-0.0f, false);
        isPositiveZero_float_helper(1.0f, false);
        isPositiveZero_float_helper(-1.0f, false);
        isPositiveZero_float_helper(Float.NaN, false);
        isPositiveZero_float_helper(Float.POSITIVE_INFINITY, false);
        isPositiveZero_float_helper(Float.NEGATIVE_INFINITY, false);
    }

    private static void isPositiveZero_double_helper(double input, boolean output) {
        aeq(isPositiveZero(input), output);
    }

    @Test
    public void testIsPositiveZero_double() {
        isPositiveZero_double_helper(0.0, true);
        isPositiveZero_double_helper(-0.0, false);
        isPositiveZero_double_helper(1.0, false);
        isPositiveZero_double_helper(-1.0, false);
        isPositiveZero_double_helper(Double.NaN, false);
        isPositiveZero_double_helper(Double.POSITIVE_INFINITY, false);
        isPositiveZero_double_helper(Double.NEGATIVE_INFINITY, false);
    }

    private static void successor_float_helper(float input, float output) {
        aeq(successor(input), output);
    }

    private static void successor_float_fail_helper(float input) {
        try {
            successor(input);
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

    private static void predecessor_float_helper(float input, float output) {
        aeq(predecessor(input), output);
    }

    private static void predecessor_float_fail_helper(float input) {
        try {
            predecessor(input);
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

    private static void successor_double_helper(double input, double output) {
        aeq(successor(input), output);
    }

    private static void successor_double_fail_helper(double input) {
        try {
            successor(input);
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

    private static void predecessor_double_helper(double input, double output) {
        aeq(predecessor(input), output);
    }

    private static void predecessor_double_fail_helper(double input) {
        try {
            predecessor(input);
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

    private static void toOrderedRepresentation_float_helper(float input, int output) {
        aeq(toOrderedRepresentation(input), output);
    }

    private static void toOrderedRepresentation_float_fail_helper(float input) {
        try {
            toOrderedRepresentation(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testToOrderedRepresentation_float() {
        toOrderedRepresentation_float_helper(1.0f, 1065353216);
        toOrderedRepresentation_float_helper(1.0E20f, 1621981420);
        toOrderedRepresentation_float_helper(-1.0f, -1065353216);
        toOrderedRepresentation_float_helper(-1.0E20f, -1621981420);
        toOrderedRepresentation_float_helper((float) Math.PI, 1078530011);
        toOrderedRepresentation_float_helper((float) Math.sqrt(2), 1068827891);
        toOrderedRepresentation_float_helper((float) -Math.PI, -1078530011);
        toOrderedRepresentation_float_helper((float) -Math.sqrt(2), -1068827891);
        toOrderedRepresentation_float_helper(0.0f, 0);
        toOrderedRepresentation_float_helper(-0.0f, 0);
        toOrderedRepresentation_float_helper(Float.MIN_VALUE, 1);
        toOrderedRepresentation_float_helper(Float.MIN_NORMAL, 8388608);
        toOrderedRepresentation_float_helper(-Float.MIN_VALUE, -1);
        toOrderedRepresentation_float_helper(-Float.MIN_NORMAL, -8388608);
        toOrderedRepresentation_float_helper(Float.MAX_VALUE, 2139095039);
        toOrderedRepresentation_float_helper(-Float.MAX_VALUE, -2139095039);
        toOrderedRepresentation_float_helper(Float.POSITIVE_INFINITY, 2139095040);
        toOrderedRepresentation_float_helper(Float.NEGATIVE_INFINITY, -2139095040);
        toOrderedRepresentation_float_helper(1.401E-42f, 1000);
        toOrderedRepresentation_float_helper(1.401298E-39f, 1000000);
        toOrderedRepresentation_float_helper(0.0047237873f, 1000000000);
        toOrderedRepresentation_float_helper(-1.401E-42f, -1000);
        toOrderedRepresentation_float_helper(-1.401298E-39f, -1000000);
        toOrderedRepresentation_float_helper(-0.0047237873f, -1000000000);

        toOrderedRepresentation_float_fail_helper(Float.NaN);
    }

    private static void floatFromOrderedRepresentation_helper(int input, float output) {
        aeq(floatFromOrderedRepresentation(input), output);
    }

    private static void floatFromOrderedRepresentation_fail_helper(int input) {
        try {
            floatFromOrderedRepresentation(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFloatFromOrderedRepresentation() {
        floatFromOrderedRepresentation_helper(1065353216, 1.0f);
        floatFromOrderedRepresentation_helper(1621981420, 1.0E20f);
        floatFromOrderedRepresentation_helper(-1065353216, -1.0f);
        floatFromOrderedRepresentation_helper(-1621981420, -1.0E20f);
        floatFromOrderedRepresentation_helper(1078530011, 3.1415927f);
        floatFromOrderedRepresentation_helper(1068827891, 1.4142135f);
        floatFromOrderedRepresentation_helper(-1078530011, -3.1415927f);
        floatFromOrderedRepresentation_helper(-1068827891, -1.4142135f);
        floatFromOrderedRepresentation_helper(0, 0.0f);
        floatFromOrderedRepresentation_helper(1, 1.4E-45f);
        floatFromOrderedRepresentation_helper(8388608, 1.17549435E-38f);
        floatFromOrderedRepresentation_helper(-1, -1.4E-45f);
        floatFromOrderedRepresentation_helper(-8388608, -1.17549435E-38f);
        floatFromOrderedRepresentation_helper(2139095039, 3.4028235E38f);
        floatFromOrderedRepresentation_helper(-2139095039, -3.4028235E38f);
        floatFromOrderedRepresentation_helper(2139095040, Float.POSITIVE_INFINITY);
        floatFromOrderedRepresentation_helper(-2139095040, Float.NEGATIVE_INFINITY);
        floatFromOrderedRepresentation_helper(1000, 1.401E-42f);
        floatFromOrderedRepresentation_helper(1000000, 1.401298E-39f);
        floatFromOrderedRepresentation_helper(1000000000, 0.0047237873f);
        floatFromOrderedRepresentation_helper(-1000, -1.401E-42f);
        floatFromOrderedRepresentation_helper(-1000000, -1.401298E-39f);
        floatFromOrderedRepresentation_helper(-1000000000, -0.0047237873f);

        floatFromOrderedRepresentation_fail_helper(2139095041);
        floatFromOrderedRepresentation_fail_helper(-2139095041);
    }

    private static void toOrderedRepresentation_double_helper(double input, long output) {
        aeq(toOrderedRepresentation(input), output);
    }

    private static void toOrderedRepresentation_double_fail_helper(double input) {
        try {
            toOrderedRepresentation(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testToOrderedRepresentation_double() {
        toOrderedRepresentation_double_helper(1.0, 4607182418800017408L);
        toOrderedRepresentation_double_helper(1.0E20, 4906019910204099648L);
        toOrderedRepresentation_double_helper(-1.0, -4607182418800017408L);
        toOrderedRepresentation_double_helper(-1.0E20, -4906019910204099648L);
        toOrderedRepresentation_double_helper(Math.PI, 4614256656552045848L);
        toOrderedRepresentation_double_helper(Math.sqrt(2), 4609047870845172685L);
        toOrderedRepresentation_double_helper(-Math.PI, -4614256656552045848L);
        toOrderedRepresentation_double_helper(-Math.sqrt(2), -4609047870845172685L);
        toOrderedRepresentation_double_helper(0.0, 0);
        toOrderedRepresentation_double_helper(-0.0, 0);
        toOrderedRepresentation_double_helper(Double.MIN_VALUE, 1);
        toOrderedRepresentation_double_helper(Double.MIN_NORMAL, 4503599627370496L);
        toOrderedRepresentation_double_helper(-Double.MIN_VALUE, -1);
        toOrderedRepresentation_double_helper(-Double.MIN_NORMAL, -4503599627370496L);
        toOrderedRepresentation_double_helper(Double.MAX_VALUE, 9218868437227405311L);
        toOrderedRepresentation_double_helper(-Double.MAX_VALUE, -9218868437227405311L);
        toOrderedRepresentation_double_helper(Double.POSITIVE_INFINITY, 9218868437227405312L);
        toOrderedRepresentation_double_helper(Double.NEGATIVE_INFINITY, -9218868437227405312L);
        toOrderedRepresentation_double_helper(4.940656E-318, 1000000L);
        toOrderedRepresentation_double_helper(4.94065645841E-312, 1000000000000L);
        toOrderedRepresentation_double_helper(7.832953389245686E-242, 1000000000000000000L);
        toOrderedRepresentation_double_helper(-4.940656E-318, -1000000L);
        toOrderedRepresentation_double_helper(-4.94065645841E-312, -1000000000000L);
        toOrderedRepresentation_double_helper(-7.832953389245686E-242, -1000000000000000000L);

        toOrderedRepresentation_double_fail_helper(Double.NaN);
    }

    private static void doubleFromOrderedRepresentation_helper(long input, double output) {
        aeq(doubleFromOrderedRepresentation(input), output);
    }

    private static void doubleFromOrderedRepresentation_fail_helper(long input) {
        try {
            doubleFromOrderedRepresentation(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testDoubleFromOrderedRepresentation() {
        doubleFromOrderedRepresentation_helper(4607182418800017408L, 1.0);
        doubleFromOrderedRepresentation_helper(4906019910204099648L, 1.0E20);
        doubleFromOrderedRepresentation_helper(-4607182418800017408L, -1.0);
        doubleFromOrderedRepresentation_helper(-4906019910204099648L, -1.0E20);
        doubleFromOrderedRepresentation_helper(4614256656552045848L, 3.141592653589793);
        doubleFromOrderedRepresentation_helper(4609047870845172685L, 1.4142135623730951);
        doubleFromOrderedRepresentation_helper(-4614256656552045848L, -3.141592653589793);
        doubleFromOrderedRepresentation_helper(-4609047870845172685L, -1.4142135623730951);
        doubleFromOrderedRepresentation_helper(0L, 0.0);
        doubleFromOrderedRepresentation_helper(1L, 4.9E-324);
        doubleFromOrderedRepresentation_helper(4503599627370496L, 2.2250738585072014E-308);
        doubleFromOrderedRepresentation_helper(-1L, -4.9E-324);
        doubleFromOrderedRepresentation_helper(-4503599627370496L, -2.2250738585072014E-308);
        doubleFromOrderedRepresentation_helper(9218868437227405311L, 1.7976931348623157E308);
        doubleFromOrderedRepresentation_helper(-9218868437227405311L, -1.7976931348623157E308);
        doubleFromOrderedRepresentation_helper(9218868437227405312L, Double.POSITIVE_INFINITY);
        doubleFromOrderedRepresentation_helper(-9218868437227405312L, Double.NEGATIVE_INFINITY);
        doubleFromOrderedRepresentation_helper(1000000L, 4.940656E-318);
        doubleFromOrderedRepresentation_helper(1000000000000L, 4.94065645841E-312);
        doubleFromOrderedRepresentation_helper(1000000000000000000L, 7.832953389245686E-242);
        doubleFromOrderedRepresentation_helper(-1000000L, -4.940656E-318);
        doubleFromOrderedRepresentation_helper(-1000000000000L, -4.94065645841E-312);
        doubleFromOrderedRepresentation_helper(-1000000000000000000L, -7.832953389245686E-242);

        doubleFromOrderedRepresentation_fail_helper(9218868437227405313L);
        doubleFromOrderedRepresentation_fail_helper(-9218868437227405313L);
    }

    private static void floatFromMantissaAndExponent_helper(int m, int e, @NotNull String output) {
        aeq(floatFromMantissaAndExponent(m, e), output);
    }

    @Test
    public void testFloatFromMantissaAndExponent() {
        floatFromMantissaAndExponent_helper(1, 0, "Optional[1.0]");
        floatFromMantissaAndExponent_helper(2842171, 45, "Optional[1.0E20]");
        floatFromMantissaAndExponent_helper(-1, 0, "Optional[-1.0]");
        floatFromMantissaAndExponent_helper(-2842171, 45, "Optional[-1.0E20]");
        floatFromMantissaAndExponent_helper(13176795, -22, "Optional[3.1415927]");
        floatFromMantissaAndExponent_helper(11863283, -23, "Optional[1.4142135]");
        floatFromMantissaAndExponent_helper(-13176795, -22, "Optional[-3.1415927]");
        floatFromMantissaAndExponent_helper(-11863283, -23, "Optional[-1.4142135]");
        floatFromMantissaAndExponent_helper(0, 0, "Optional[0.0]");
        floatFromMantissaAndExponent_helper(1, -149, "Optional[1.4E-45]");
        floatFromMantissaAndExponent_helper(1, -126, "Optional[1.17549435E-38]");
        floatFromMantissaAndExponent_helper(-1, -149, "Optional[-1.4E-45]");
        floatFromMantissaAndExponent_helper(-1, -126, "Optional[-1.17549435E-38]");
        floatFromMantissaAndExponent_helper(16777215, 104, "Optional[3.4028235E38]");
        floatFromMantissaAndExponent_helper(-16777215, 104, "Optional[-3.4028235E38]");
        floatFromMantissaAndExponent_helper(1, 100, "Optional[1.2676506E30]");
        floatFromMantissaAndExponent_helper(1, -100, "Optional[7.888609E-31]");
        floatFromMantissaAndExponent_helper(123, 45, "Optional[4.32767777E15]");
        floatFromMantissaAndExponent_helper(-1, 100, "Optional[-1.2676506E30]");
        floatFromMantissaAndExponent_helper(-1, -100, "Optional[-7.888609E-31]");
        floatFromMantissaAndExponent_helper(-123, 45, "Optional[-4.32767777E15]");

        floatFromMantissaAndExponent_helper(2, 10, "Optional.empty");
        floatFromMantissaAndExponent_helper(1, 1000, "Optional.empty");
        floatFromMantissaAndExponent_helper(1, -1000, "Optional.empty");
        floatFromMantissaAndExponent_helper(1111111111, 0, "Optional.empty");
    }

    private static void doubleFromMantissaAndExponent_helper(long m, int e, @NotNull String output) {
        aeq(doubleFromMantissaAndExponent(m, e), output);
    }

    @Test
    public void testDoubleFromMantissaAndExponent() {
        doubleFromMantissaAndExponent_helper(1L, 0, "Optional[1.0]");
        doubleFromMantissaAndExponent_helper(95367431640625L, 20, "Optional[1.0E20]");
        doubleFromMantissaAndExponent_helper(-1L, 0, "Optional[-1.0]");
        doubleFromMantissaAndExponent_helper(-95367431640625L, 20, "Optional[-1.0E20]");
        doubleFromMantissaAndExponent_helper(884279719003555L, -48, "Optional[3.141592653589793]");
        doubleFromMantissaAndExponent_helper(6369051672525773L, -52, "Optional[1.4142135623730951]");
        doubleFromMantissaAndExponent_helper(-884279719003555L, -48, "Optional[-3.141592653589793]");
        doubleFromMantissaAndExponent_helper(-6369051672525773L, -52, "Optional[-1.4142135623730951]");
        doubleFromMantissaAndExponent_helper(0L, 0, "Optional[0.0]");
        doubleFromMantissaAndExponent_helper(1L, -1074, "Optional[4.9E-324]");
        doubleFromMantissaAndExponent_helper(1L, -1022, "Optional[2.2250738585072014E-308]");
        doubleFromMantissaAndExponent_helper(-1L, -1074, "Optional[-4.9E-324]");
        doubleFromMantissaAndExponent_helper(-1L, -1022, "Optional[-2.2250738585072014E-308]");
        doubleFromMantissaAndExponent_helper(9007199254740991L, 971, "Optional[1.7976931348623157E308]");
        doubleFromMantissaAndExponent_helper(-9007199254740991L, 971, "Optional[-1.7976931348623157E308]");
        doubleFromMantissaAndExponent_helper(1L, 1000, "Optional[1.0715086071862673E301]");
        doubleFromMantissaAndExponent_helper(1L, -1000, "Optional[9.332636185032189E-302]");
        doubleFromMantissaAndExponent_helper(123L, 456, "Optional[2.288669775062007E139]");
        doubleFromMantissaAndExponent_helper(-1L, 1000, "Optional[-1.0715086071862673E301]");
        doubleFromMantissaAndExponent_helper(-1L, -1000, "Optional[-9.332636185032189E-302]");
        doubleFromMantissaAndExponent_helper(-123L, 456, "Optional[-2.288669775062007E139]");

        doubleFromMantissaAndExponent_helper(2L, 10, "Optional.empty");
        doubleFromMantissaAndExponent_helper(1L, 10000, "Optional.empty");
        doubleFromMantissaAndExponent_helper(1L, -10000, "Optional.empty");
        doubleFromMantissaAndExponent_helper(1111111111111111111L, 0, "Optional.empty");
    }

    private static void toMantissaAndExponent_float_helper(float input, @NotNull String output) {
        aeq(toMantissaAndExponent(input), output);
    }

    @Test
    public void testToMantissaAndExponent_float() {
        toMantissaAndExponent_float_helper(1.0f, "Optional[(1, 0)]");
        toMantissaAndExponent_float_helper(1.0E20F, "Optional[(2842171, 45)]");
        toMantissaAndExponent_float_helper(-1.0f, "Optional[(-1, 0)]");
        toMantissaAndExponent_float_helper(-1.0E20f, "Optional[(-2842171, 45)]");
        toMantissaAndExponent_float_helper((float) Math.PI, "Optional[(13176795, -22)]");
        toMantissaAndExponent_float_helper((float) Math.sqrt(2), "Optional[(11863283, -23)]");
        toMantissaAndExponent_float_helper((float) -Math.PI, "Optional[(-13176795, -22)]");
        toMantissaAndExponent_float_helper((float) -Math.sqrt(2), "Optional[(-11863283, -23)]");
        toMantissaAndExponent_float_helper(0.0f, "Optional[(0, 0)]");
        toMantissaAndExponent_float_helper(-0.0f, "Optional[(0, 0)]");
        toMantissaAndExponent_float_helper(Float.MIN_VALUE, "Optional[(1, -149)]");
        toMantissaAndExponent_float_helper(Float.MIN_NORMAL, "Optional[(1, -126)]");
        toMantissaAndExponent_float_helper(-Float.MIN_VALUE, "Optional[(-1, -149)]");
        toMantissaAndExponent_float_helper(-Float.MIN_NORMAL, "Optional[(-1, -126)]");
        toMantissaAndExponent_float_helper(Float.MAX_VALUE, "Optional[(16777215, 104)]");
        toMantissaAndExponent_float_helper(-Float.MAX_VALUE, "Optional[(-16777215, 104)]");
        toMantissaAndExponent_float_helper(1.2676506E30f, "Optional[(1, 100)]");
        toMantissaAndExponent_float_helper(7.888609E-31f, "Optional[(1, -100)]");
        toMantissaAndExponent_float_helper(4.32767777E15f, "Optional[(123, 45)]");
        toMantissaAndExponent_float_helper(-1.2676506E30f, "Optional[(-1, 100)]");
        toMantissaAndExponent_float_helper(-7.888609E-31f, "Optional[(-1, -100)]");
        toMantissaAndExponent_float_helper(-4.32767777E15f, "Optional[(-123, 45)]");

        toMantissaAndExponent_float_helper(Float.POSITIVE_INFINITY, "Optional.empty");
        toMantissaAndExponent_float_helper(Float.NEGATIVE_INFINITY, "Optional.empty");
        toMantissaAndExponent_float_helper(Float.NaN, "Optional.empty");
    }

    private static void toMantissaAndExponent_double_helper(double input, @NotNull String output) {
        aeq(toMantissaAndExponent(input), output);
    }

    @Test
    public void testToMantissaAndExponent_double() {
        toMantissaAndExponent_double_helper(1.0, "Optional[(1, 0)]");
        toMantissaAndExponent_double_helper(1.0E20, "Optional[(95367431640625, 20)]");
        toMantissaAndExponent_double_helper(-1.0, "Optional[(-1, 0)]");
        toMantissaAndExponent_double_helper(-1.0E20, "Optional[(-95367431640625, 20)]");
        toMantissaAndExponent_double_helper(Math.PI, "Optional[(884279719003555, -48)]");
        toMantissaAndExponent_double_helper(Math.sqrt(2), "Optional[(6369051672525773, -52)]");
        toMantissaAndExponent_double_helper(-Math.PI, "Optional[(-884279719003555, -48)]");
        toMantissaAndExponent_double_helper(-Math.sqrt(2), "Optional[(-6369051672525773, -52)]");
        toMantissaAndExponent_double_helper(0.0, "Optional[(0, 0)]");
        toMantissaAndExponent_double_helper(-0.0, "Optional[(0, 0)]");
        toMantissaAndExponent_double_helper(Double.MIN_VALUE, "Optional[(1, -1074)]");
        toMantissaAndExponent_double_helper(Double.MIN_NORMAL, "Optional[(1, -1022)]");
        toMantissaAndExponent_double_helper(-Double.MIN_VALUE, "Optional[(-1, -1074)]");
        toMantissaAndExponent_double_helper(-Double.MIN_NORMAL, "Optional[(-1, -1022)]");
        toMantissaAndExponent_double_helper(Double.MAX_VALUE, "Optional[(9007199254740991, 971)]");
        toMantissaAndExponent_double_helper(-Double.MAX_VALUE, "Optional[(-9007199254740991, 971)]");
        toMantissaAndExponent_double_helper(1.0715086071862673E301, "Optional[(1, 1000)]");
        toMantissaAndExponent_double_helper(9.332636185032189E-302, "Optional[(1, -1000)]");
        toMantissaAndExponent_double_helper(2.288669775062007E139, "Optional[(123, 456)]");
        toMantissaAndExponent_double_helper(-1.0715086071862673E301, "Optional[(-1, 1000)]");
        toMantissaAndExponent_double_helper(-9.332636185032189E-302, "Optional[(-1, -1000)]");
        toMantissaAndExponent_double_helper(-2.288669775062007E139, "Optional[(-123, 456)]");

        toMantissaAndExponent_double_helper(Double.POSITIVE_INFINITY, "Optional.empty");
        toMantissaAndExponent_double_helper(Double.NEGATIVE_INFINITY, "Optional.empty");
        toMantissaAndExponent_double_helper(Double.NaN, "Optional.empty");
    }

    private static void absNegativeZeros_helper(float input, float output) {
        aeq(absNegativeZeros(input), output);
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

    private static void absNegativeZeros_helper(double input, double output) {
        aeq(absNegativeZeros(input), output);
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

    private static void scaleUp_float_helper(float input, @NotNull String output) {
        aeq(scaleUp(input), output);
    }

    @Test
    public void testScaleUp_float() {
        scaleUp_float_helper(1.0f, "Optional[713623846352979940529142984724747568191373312]");
        scaleUp_float_helper(1.0E20F, "Optional[71362386065462791412927717842990225862080190703791157742208548864]");
        scaleUp_float_helper(-1.0f, "Optional[-713623846352979940529142984724747568191373312]");
        scaleUp_float_helper(-1.0E20f, "Optional[-71362386065462791412927717842990225862080190703791157742208548864]");
        scaleUp_float_helper((float) Math.PI, "Optional[2241915495515993670335938605166943104936181760]");
        scaleUp_float_helper((float) Math.sqrt(2), "Optional[1009216504673232904412793275744242370726592512]");
        scaleUp_float_helper((float) -Math.PI, "Optional[-2241915495515993670335938605166943104936181760]");
        scaleUp_float_helper((float) -Math.sqrt(2), "Optional[-1009216504673232904412793275744242370726592512]");
        scaleUp_float_helper(0.0f, "Optional[0]");
        scaleUp_float_helper(-0.0f, "Optional[0]");
        scaleUp_float_helper(Float.MIN_VALUE, "Optional[1]");
        scaleUp_float_helper(Float.MIN_NORMAL, "Optional[8388608]");
        scaleUp_float_helper(-Float.MIN_VALUE, "Optional[-1]");
        scaleUp_float_helper(-Float.MIN_NORMAL, "Optional[-8388608]");
        scaleUp_float_helper(Float.MAX_VALUE,
                "Optional[242833597054204979200408310406566737244312373222769356951406046285165034661509857280]");
        scaleUp_float_helper(-Float.MAX_VALUE,
                "Optional[-242833597054204979200408310406566737244312373222769356951406046285165034661509857280]");

        scaleUp_float_helper(Float.POSITIVE_INFINITY, "Optional.empty");
        scaleUp_float_helper(Float.NEGATIVE_INFINITY, "Optional.empty");
        scaleUp_float_helper(Float.NaN, "Optional.empty");
    }

    private static void scaleUp_double_helper(double input, @NotNull String output) {
        aeq(scaleUp(input), output);
    }

    @Test
    public void testScaleUp_double() {
        scaleUp_double_helper(1.0,
                "Optional[202402253307310618352495346718917307049556649764142118356901358027430339567995346891960383" +
                "701437124495187077864316811911389808737385793476867013399940738509921517424276566361364466907742093" +
                "216341239767678472745068562007483424692698618103355649159556340810056512358769552333414615230502532" +
                "186327508646006263307707741093494784]");
        scaleUp_double_helper(1.0E20,
                "Optional[202402253307310618352495346718917307049556649764142118356901358027430339567995346891960383" +
                "701437124495187077864316811911389808737385793476867013399940738509921517424276566361364466907742093" +
                "216341239767678472745068562007483424692698618103355649159556340810056512358769552333414615230502532" +
                "18632750864600626330770774109349478400000000000000000000]");
        scaleUp_double_helper(-1.0,
                "Optional[-20240225330731061835249534671891730704955664976414211835690135802743033956799534689196038" +
                "370143712449518707786431681191138980873738579347686701339994073850992151742427656636136446690774209" +
                "321634123976767847274506856200748342469269861810335564915955634081005651235876955233341461523050253" +
                "2186327508646006263307707741093494784]");
        scaleUp_double_helper(-1.0E20,
                "Optional[-20240225330731061835249534671891730704955664976414211835690135802743033956799534689196038" +
                "370143712449518707786431681191138980873738579347686701339994073850992151742427656636136446690774209" +
                "321634123976767847274506856200748342469269861810335564915955634081005651235876955233341461523050253" +
                "218632750864600626330770774109349478400000000000000000000]");
        scaleUp_double_helper(Math.PI,
                "Optional[635865432060267445468214182091255143872403807686233472603792193275656356707688919982837471" +
                "838052747972478524629983029917167695672135057166860157826274832966513867892628499855176210243632893" +
                "770193919817964592300301104964149478683857093638587052393633657306350059152722017417798014182989122" +
                "203290511554679949608784156447211520]");
        scaleUp_double_helper(Math.sqrt(2),
                "Optional[286240011682073328362787334263691923924391219706463276968985538734844037157151109548568235" +
                "817006833651987366513964422661132007770600072130077881251753294643304641697510507371000948958629701" +
                "213985344497086249726915278714709715136272639151964413490064246690116540701128871898413930195307402" +
                "052395949162786919083459855612116992]");
        scaleUp_double_helper(-Math.PI,
                "Optional[-63586543206026744546821418209125514387240380768623347260379219327565635670768891998283747" +
                "183805274797247852462998302991716769567213505716686015782627483296651386789262849985517621024363289" +
                "377019391981796459230030110496414947868385709363858705239363365730635005915272201741779801418298912" +
                "2203290511554679949608784156447211520]");
        scaleUp_double_helper(-Math.sqrt(2),
                "Optional[-28624001168207332836278733426369192392439121970646327696898553873484403715715110954856823" +
                "581700683365198736651396442266113200777060007213007788125175329464330464169751050737100094895862970" +
                "121398534449708624972691527871470971513627263915196441349006424669011654070112887189841393019530740" +
                "2052395949162786919083459855612116992]");
        scaleUp_double_helper(0.0, "Optional[0]");
        scaleUp_double_helper(-0.0, "Optional[0]");
        scaleUp_double_helper(Double.MIN_VALUE, "Optional[1]");
        scaleUp_double_helper(Double.MIN_NORMAL, "Optional[4503599627370496]");
        scaleUp_double_helper(-Double.MIN_VALUE, "Optional[-1]");
        scaleUp_double_helper(-Double.MIN_NORMAL, "Optional[-4503599627370496]");
        scaleUp_double_helper(Double.MAX_VALUE,
                "Optional[363857141251215733008468006984567498428427744310602690309735631992518352027631318742205104" +
                "461997525781461689595255359755041236607412597305594915359190782200698392412987448013052928786408355" +
                "279308639946743576115889990206935944747628988479302915525946901702031872150456880949556607739225761" +
                "379698303426118602250199355821996011214692492231498724661213715671558623030843303146025660694164325" +
                "513330061947744775142603512019698593680602201312344881981489765361696383056969005048388307197608755" +
                "142462165089768038827285826773521770041292888478544630840063729813907563445195499310977439636039716" +
                "32334891836831978686870043355177324550146752512]");
        scaleUp_double_helper(-Double.MAX_VALUE,
                "Optional[-36385714125121573300846800698456749842842774431060269030973563199251835202763131874220510" +
                "446199752578146168959525535975504123660741259730559491535919078220069839241298744801305292878640835" +
                "527930863994674357611588999020693594474762898847930291552594690170203187215045688094955660773922576" +
                "137969830342611860225019935582199601121469249223149872466121371567155862303084330314602566069416432" +
                "551333006194774477514260351201969859368060220131234488198148976536169638305696900504838830719760875" +
                "514246216508976803882728582677352177004129288847854463084006372981390756344519549931097743963603971" +
                "632334891836831978686870043355177324550146752512]");

        scaleUp_double_helper(Double.POSITIVE_INFINITY, "Optional.empty");
        scaleUp_double_helper(Double.NEGATIVE_INFINITY, "Optional.empty");
        scaleUp_double_helper(Double.NaN, "Optional.empty");
    }

    private static void toStringCompact_float_helper(float input, @NotNull String output) {
        aeq(toStringCompact(input), output);
    }

    @Test
    public void testToStringCompact_float() {
        toStringCompact_float_helper(1.0f, "1");
        toStringCompact_float_helper(1.0E20f, "1E20");
        toStringCompact_float_helper(1.0E-20f, "1E-20");
        toStringCompact_float_helper(-1.0f, "-1");
        toStringCompact_float_helper(-1.0E20f, "-1E20");
        toStringCompact_float_helper(-1.0E-20f, "-1E-20");
        toStringCompact_float_helper((float) Math.PI, "3.1415927");
        toStringCompact_float_helper((float) Math.sqrt(2), "1.4142135");
        toStringCompact_float_helper((float) -Math.PI, "-3.1415927");
        toStringCompact_float_helper((float) -Math.sqrt(2), "-1.4142135");
        toStringCompact_float_helper(0.0f, "0");
        toStringCompact_float_helper(-0.0f, "-0");
        toStringCompact_float_helper(Float.MIN_VALUE, "1.4E-45");
        toStringCompact_float_helper(Float.MIN_NORMAL, "1.17549435E-38");
        toStringCompact_float_helper(-Float.MIN_VALUE, "-1.4E-45");
        toStringCompact_float_helper(-Float.MIN_NORMAL, "-1.17549435E-38");
        toStringCompact_float_helper(Float.MAX_VALUE, "3.4028235E38");
        toStringCompact_float_helper(-Float.MAX_VALUE, "-3.4028235E38");
    }

    private static void toStringCompact_double_helper(double input, @NotNull String output) {
        aeq(toStringCompact(input), output);
    }

    @Test
    public void testToStringCompact_double() {
        toStringCompact_double_helper(1.0, "1");
        toStringCompact_double_helper(1.0E20, "1E20");
        toStringCompact_double_helper(1.0E-20, "1E-20");
        toStringCompact_double_helper(-1.0, "-1");
        toStringCompact_double_helper(-1.0E20, "-1E20");
        toStringCompact_double_helper(-1.0E-20, "-1E-20");
        toStringCompact_double_helper(Math.PI, "3.141592653589793");
        toStringCompact_double_helper(Math.sqrt(2), "1.4142135623730951");
        toStringCompact_double_helper(-Math.PI, "-3.141592653589793");
        toStringCompact_double_helper(-Math.sqrt(2), "-1.4142135623730951");
        toStringCompact_double_helper(0.0, "0");
        toStringCompact_double_helper(-0.0, "-0");
        toStringCompact_double_helper(Double.MIN_VALUE, "4.9E-324");
        toStringCompact_double_helper(Double.MIN_NORMAL, "2.2250738585072014E-308");
        toStringCompact_double_helper(-Double.MIN_VALUE, "-4.9E-324");
        toStringCompact_double_helper(-Double.MIN_NORMAL, "-2.2250738585072014E-308");
        toStringCompact_double_helper(Double.MAX_VALUE, "1.7976931348623157E308");
        toStringCompact_double_helper(-Double.MAX_VALUE, "-1.7976931348623157E308");
    }
}
