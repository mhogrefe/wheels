package mho.wheels.math;

import mho.wheels.misc.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.fail;
import static mho.wheels.testing.Testing.testEqualsHelper;

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

    private static void of_BigInteger_int_helper(int mantissa, int exponent, @NotNull String output) {
        aeq(of(BigInteger.valueOf(mantissa), exponent), output);
    }

    @Test
    public void testOf_BigInteger_int() {
        of_BigInteger_int_helper(0, 0, "0");
        of_BigInteger_int_helper(0, 1, "0");
        of_BigInteger_int_helper(0, -3, "0");
        of_BigInteger_int_helper(1, 0, "1");
        of_BigInteger_int_helper(2, 0, "1 << 1");
        of_BigInteger_int_helper(1, 1, "1 << 1");
        of_BigInteger_int_helper(5, 20, "5 << 20");
        of_BigInteger_int_helper(5, -20, "5 >> 20");
        of_BigInteger_int_helper(100, 0, "25 << 2");
        of_BigInteger_int_helper(-1, 0, "-1");
        of_BigInteger_int_helper(-2, 0, "-1 << 1");
        of_BigInteger_int_helper(-1, 1, "-1 << 1");
        of_BigInteger_int_helper(-5, 20, "-5 << 20");
        of_BigInteger_int_helper(-5, -20, "-5 >> 20");
        of_BigInteger_int_helper(-100, 0, "-25 << 2");
        try {
            of(BigInteger.valueOf(4), Integer.MAX_VALUE);
            Assert.fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void of_BigInteger_helper(int n, @NotNull String output) {
        aeq(of(BigInteger.valueOf(n)), output);
    }

    @Test
    public void testOf_BigInteger() {
        of_BigInteger_helper(0, "0");
        of_BigInteger_helper(1, "1");
        of_BigInteger_helper(5, "5");
        of_BigInteger_helper(100, "25 << 2");
        of_BigInteger_helper(-1, "-1");
        of_BigInteger_helper(-5, "-5");
        of_BigInteger_helper(-100, "-25 << 2");
    }

    private static void of_int_helper(int n, @NotNull String output) {
        aeq(of(n), output);
    }

    @Test
    public void testOf_int() {
        of_int_helper(0, "0");
        of_int_helper(1, "1");
        of_int_helper(5, "5");
        of_int_helper(100, "25 << 2");
        of_int_helper(-1, "-1");
        of_int_helper(-5, "-5");
        of_int_helper(-100, "-25 << 2");
    }

    private static void of_float_helper(float f, @NotNull String output) {
        aeq(of(f).get(), output);
    }

    private static void of_float_empty_helper(float f) {
        Assert.assertFalse(of(f).isPresent());
    }

    @Test
    public void testOf_float() {
        of_float_helper(0.0f, "0");
        of_float_helper(1.0f, "1");
        of_float_helper(0.5f, "1 >> 1");
        of_float_helper(0.25f, "1 >> 2");
        of_float_helper(1.0f / 3.0f, "11184811 >> 25");
        of_float_helper(2.0f, "1 << 1");
        of_float_helper(4.0f, "1 << 2");
        of_float_helper(3.0f, "3");
        of_float_helper(1.5f, "3 >> 1");
        of_float_helper(1000.0f, "125 << 3");
        of_float_helper(0.001f, "8589935 >> 33");
        of_float_helper((float) Math.PI, "13176795 >> 22");
        of_float_helper((float) Math.E, "2850325 >> 20");
        of_float_helper(Float.MIN_VALUE, "1 >> 149");
        of_float_helper(Float.MIN_NORMAL, "1 >> 126");
        of_float_helper(Float.MAX_VALUE, "16777215 << 104");
        of_float_helper(-0.0f, "0");
        of_float_helper(-1.0f, "-1");
        of_float_helper(-0.5f, "-1 >> 1");
        of_float_helper(-0.25f, "-1 >> 2");
        of_float_helper(-1.0f / 3.0f, "-11184811 >> 25");
        of_float_helper(-2.0f, "-1 << 1");
        of_float_helper(-4.0f, "-1 << 2");
        of_float_helper(-3.0f, "-3");
        of_float_helper(-1.5f, "-3 >> 1");
        of_float_helper(-1000.0f, "-125 << 3");
        of_float_helper(-0.001f, "-8589935 >> 33");
        of_float_helper((float) -Math.PI, "-13176795 >> 22");
        of_float_helper((float) -Math.E, "-2850325 >> 20");
        of_float_helper(-Float.MIN_VALUE, "-1 >> 149");
        of_float_helper(-Float.MIN_NORMAL, "-1 >> 126");
        of_float_helper(-Float.MAX_VALUE, "-16777215 << 104");
        of_float_empty_helper(Float.POSITIVE_INFINITY);
        of_float_empty_helper(Float.NEGATIVE_INFINITY);
        of_float_empty_helper(Float.NaN);
    }

    private static void of_double_helper(double d, @NotNull String output) {
        aeq(of(d).get(), output);
    }

    private static void of_double_empty_helper(double d) {
        Assert.assertFalse(of(d).isPresent());
    }

    @Test
    public void testOfMantissaAndExponent_double() {
        of_double_helper(0.0, "0");
        of_double_helper(1.0, "1");
        of_double_helper(0.5, "1 >> 1");
        of_double_helper(0.25, "1 >> 2");
        of_double_helper(1.0 / 3.0, "6004799503160661 >> 54");
        of_double_helper(2.0, "1 << 1");
        of_double_helper(4.0, "1 << 2");
        of_double_helper(3.0, "3");
        of_double_helper(1.5, "3 >> 1");
        of_double_helper(1000.0, "125 << 3");
        of_double_helper(0.001, "1152921504606847 >> 60");
        of_double_helper(Math.PI, "884279719003555 >> 48");
        of_double_helper(Math.E, "6121026514868073 >> 51");
        of_double_helper(Double.MIN_VALUE, "1 >> 1074");
        of_double_helper(Double.MIN_NORMAL, "1 >> 1022");
        of_double_helper(Double.MAX_VALUE, "9007199254740991 << 971");
        of_double_helper(-0.0, "0");
        of_double_helper(-1.0, "-1");
        of_double_helper(-0.5, "-1 >> 1");
        of_double_helper(-0.25, "-1 >> 2");
        of_double_helper(-1.0 / 3.0, "-6004799503160661 >> 54");
        of_double_helper(-2.0, "-1 << 1");
        of_double_helper(-4.0, "-1 << 2");
        of_double_helper(-3.0, "-3");
        of_double_helper(-1.5, "-3 >> 1");
        of_double_helper(-1000.0, "-125 << 3");
        of_double_helper(-0.001, "-1152921504606847 >> 60");
        of_double_helper(-Math.PI, "-884279719003555 >> 48");
        of_double_helper(-Math.E, "-6121026514868073 >> 51");
        of_double_helper(-Double.MIN_VALUE, "-1 >> 1074");
        of_double_helper(-Double.MIN_NORMAL, "-1 >> 1022");
        of_double_helper(-Double.MAX_VALUE, "-9007199254740991 << 971");
        of_double_empty_helper(Double.POSITIVE_INFINITY);
        of_double_empty_helper(Double.NEGATIVE_INFINITY);
        of_double_empty_helper(Double.NaN);
    }

    private static void bigDecimalValue_helper(@NotNull String input, @NotNull String output) {
        aeq(read(input).get().bigDecimalValue(), output);
    }

    @Test
    public void testBigDecimalValue() {
        bigDecimalValue_helper("0", "0");
        bigDecimalValue_helper("1", "1");
        bigDecimalValue_helper("11", "11");
        bigDecimalValue_helper("5 << 20", "5242880");
        bigDecimalValue_helper("5 >> 20", "0.00000476837158203125");
        bigDecimalValue_helper("-1", "-1");
        bigDecimalValue_helper("-11", "-11");
        bigDecimalValue_helper("-5 << 20", "-5242880");
        bigDecimalValue_helper("-5 >> 20", "-0.00000476837158203125");
        aeq(SMALLEST_FLOAT.bigDecimalValue(),
                "1.4012984643248170709237295832899161312802619418765157717570682838897910826858606014866381883621215" +
                "8203125E-45");
        aeq(LARGEST_FLOAT.bigDecimalValue(), "340282346638528859811704183484516925440");
        aeq(SMALLEST_DOUBLE.bigDecimalValue(),
                "4.9406564584124654417656879286822137236505980261432476442558568250067550727020875186529983636163599" +
                "237979656469544571773092665671035593979639877479601078187812630071319031140452784581716784898210368" +
                "871863605699873072305000638740915356498438731247339727316961514003171538539807412623856559117102665" +
                "855668676818703956031062493194527159149245532930545654440112748012970999954193198940908041656332452" +
                "475714786901472678015935523861155013480352649347201937902681071074917033322268447533357208324319360" +
                "923828934583680601060115061698097530783422773183292479049825247307763759272478746560847782037344696" +
                "995336470179726777175851256605511991315048911014510378627381672509558373897335989936648099411642057" +
                "02637090279242767544565229087538682506419718265533447265625E-324");
        aeq(LARGEST_DOUBLE.bigDecimalValue(),
                "179769313486231570814527423731704356798070567525844996598917476803157260780028538760589558632766878" +
                "171540458953514382464234321326889464182768467546703537516986049910576551282076245490090389328944075" +
                "868508455133942304583236903222948165808559332123348274797826204144723168738177180919299881250404026" +
                "184124858368");
    }

    private static void isInteger_helper(@NotNull String input, boolean output) {
        BinaryFraction bf = read(input).get();
        if (output) {
            Assert.assertTrue(bf.isInteger());
        } else {
            Assert.assertFalse(bf.isInteger());
        }
    }

    @Test
    public void testIsInteger() {
        isInteger_helper("0", true);
        isInteger_helper("1", true);
        isInteger_helper("11", true);
        isInteger_helper("5 << 20", true);
        isInteger_helper("5 >> 20", false);
        isInteger_helper("-1", true);
        isInteger_helper("-11", true);
        isInteger_helper("-5 << 20", true);
        isInteger_helper("-5 >> 20", false);
    }

    @Test
    public void testEquals() {
        testEqualsHelper(
                readBinaryFractionList("[0, 1, 11, 5 << 20, 5 >> 20, -1, -11, -5 << 20, -5 >> 20]"),
                readBinaryFractionList("[0, 1, 11, 5 << 20, 5 >> 20, -1, -11, -5 << 20, -5 >> 20]")
        );
    }

    private static void hashCode_helper(@NotNull String input, int hashCode) {
        aeq(read(input).get().hashCode(), hashCode);
    }

    @Test
    public void testHashCode() {
        hashCode_helper("0", 0);
        hashCode_helper("1", 31);
        hashCode_helper("11", 341);
        hashCode_helper("5 << 20", 175);
        hashCode_helper("5 >> 20", 135);
        hashCode_helper("-1", -31);
        hashCode_helper("-11", -341);
        hashCode_helper("-5 << 20", -135);
        hashCode_helper("-5 >> 20", -175);
    }

    private static @NotNull List<BinaryFraction> readBinaryFractionList(@NotNull String s) {
        return Readers.readList(BinaryFraction::read).apply(s).get();
    }
}
