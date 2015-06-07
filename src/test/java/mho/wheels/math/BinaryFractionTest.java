package mho.wheels.math;

import mho.wheels.misc.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.*;

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
        of_BigInteger_int_helper(1, Integer.MAX_VALUE, "1 << 2147483647");
        of_BigInteger_int_helper(1, Integer.MIN_VALUE, "1 >> 2147483648");
        try {
            of(BigInteger.valueOf(4), Integer.MAX_VALUE);
            Assert.fail();
        } catch (ArithmeticException ignored) {}
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

    private static void add_helper(@NotNull String x, @NotNull String y, @NotNull String output) {
        aeq(read(x).get().add(read(y).get()), output);
    }

    private static void add_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            read(x).get().add(read(y).get());
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testAdd() {
        add_helper("0", "0", "0");
        add_helper("0", "1", "1");
        add_helper("0", "11", "11");
        add_helper("0", "5 << 20", "5 << 20");
        add_helper("0", "5 >> 20", "5 >> 20");
        add_helper("0", "-1", "-1");
        add_helper("0", "-11", "-11");
        add_helper("0", "-5 << 20", "-5 << 20");
        add_helper("0", "-5 >> 20", "-5 >> 20");
        add_helper("1", "0", "1");
        add_helper("1", "1", "1 << 1");
        add_helper("1", "11", "3 << 2");
        add_helper("1", "5 << 20", "5242881");
        add_helper("1", "5 >> 20", "1048581 >> 20");
        add_helper("1", "-1", "0");
        add_helper("1", "-11", "-5 << 1");
        add_helper("1", "-5 << 20", "-5242879");
        add_helper("1", "-5 >> 20", "1048571 >> 20");
        add_helper("11", "0", "11");
        add_helper("11", "1", "3 << 2");
        add_helper("11", "11", "11 << 1");
        add_helper("11", "5 << 20", "5242891");
        add_helper("11", "5 >> 20", "11534341 >> 20");
        add_helper("11", "-1", "5 << 1");
        add_helper("11", "-11", "0");
        add_helper("11", "-5 << 20", "-5242869");
        add_helper("11", "-5 >> 20", "11534331 >> 20");
        add_helper("5 << 20", "0", "5 << 20");
        add_helper("5 << 20", "1", "5242881");
        add_helper("5 << 20", "11", "5242891");
        add_helper("5 << 20", "5 << 20", "5 << 21");
        add_helper("5 << 20", "5 >> 20", "5497558138885 >> 20");
        add_helper("5 << 20", "-1", "5242879");
        add_helper("5 << 20", "-11", "5242869");
        add_helper("5 << 20", "-5 << 20", "0");
        add_helper("5 << 20", "-5 >> 20", "5497558138875 >> 20");
        add_helper("5 >> 20", "0", "5 >> 20");
        add_helper("5 >> 20", "1", "1048581 >> 20");
        add_helper("5 >> 20", "11", "11534341 >> 20");
        add_helper("5 >> 20", "5 << 20", "5497558138885 >> 20");
        add_helper("5 >> 20", "5 >> 20", "5 >> 19");
        add_helper("5 >> 20", "-1", "-1048571 >> 20");
        add_helper("5 >> 20", "-11", "-11534331 >> 20");
        add_helper("5 >> 20", "-5 << 20", "-5497558138875 >> 20");
        add_helper("5 >> 20", "-5 >> 20", "0");
        add_helper("-1", "0", "-1");
        add_helper("-1", "1", "0");
        add_helper("-1", "11", "5 << 1");
        add_helper("-1", "5 << 20", "5242879");
        add_helper("-1", "5 >> 20", "-1048571 >> 20");
        add_helper("-1", "-1", "-1 << 1");
        add_helper("-1", "-11", "-3 << 2");
        add_helper("-1", "-5 << 20", "-5242881");
        add_helper("-1", "-5 >> 20", "-1048581 >> 20");
        add_helper("-11", "0", "-11");
        add_helper("-11", "1", "-5 << 1");
        add_helper("-11", "11", "0");
        add_helper("-11", "5 << 20", "5242869");
        add_helper("-11", "5 >> 20", "-11534331 >> 20");
        add_helper("-11", "-1", "-3 << 2");
        add_helper("-11", "-11", "-11 << 1");
        add_helper("-11", "-5 << 20", "-5242891");
        add_helper("-11", "-5 >> 20", "-11534341 >> 20");
        add_helper("-5 << 20", "0", "-5 << 20");
        add_helper("-5 << 20", "1", "-5242879");
        add_helper("-5 << 20", "11", "-5242869");
        add_helper("-5 << 20", "5 << 20", "0");
        add_helper("-5 << 20", "5 >> 20", "-5497558138875 >> 20");
        add_helper("-5 << 20", "-1", "-5242881");
        add_helper("-5 << 20", "-11", "-5242891");
        add_helper("-5 << 20", "-5 << 20", "-5 << 21");
        add_helper("-5 << 20", "-5 >> 20", "-5497558138885 >> 20");
        add_helper("-5 >> 20", "0", "-5 >> 20");
        add_helper("-5 >> 20", "1", "1048571 >> 20");
        add_helper("-5 >> 20", "11", "11534331 >> 20");
        add_helper("-5 >> 20", "5 << 20", "5497558138875 >> 20");
        add_helper("-5 >> 20", "5 >> 20", "0");
        add_helper("-5 >> 20", "-1", "-1048581 >> 20");
        add_helper("-5 >> 20", "-11", "-11534341 >> 20");
        add_helper("-5 >> 20", "-5 << 20", "-5497558138885 >> 20");
        add_helper("-5 >> 20", "-5 >> 20", "-5 >> 19");
        add_fail_helper("1 << 2147483647", "1 << 2147483647");
    }

    private static void negate_helper(@NotNull String input, @NotNull String output) {
        aeq(read(input).get().negate(), output);
    }

    @Test
    public void testNegate() {
        negate_helper("0", "0");
        negate_helper("1", "-1");
        negate_helper("11", "-11");
        negate_helper("5 << 20", "-5 << 20");
        negate_helper("5 >> 20", "-5 >> 20");
        negate_helper("-1", "1");
        negate_helper("-11", "11");
        negate_helper("-5 << 20", "5 << 20");
        negate_helper("-5 >> 20", "5 >> 20");
    }

    private static void abs_helper(@NotNull String input, @NotNull String output) {
        aeq(read(input).get().abs(), output);
    }

    @Test
    public void testAbs() {
        abs_helper("0", "0");
        abs_helper("1", "1");
        abs_helper("11", "11");
        abs_helper("5 << 20", "5 << 20");
        abs_helper("5 >> 20", "5 >> 20");
        abs_helper("-1", "1");
        abs_helper("-11", "11");
        abs_helper("-5 << 20", "5 << 20");
        abs_helper("-5 >> 20", "5 >> 20");
    }

    private static void signum_helper(@NotNull String input, int signum) {
        aeq(read(input).get().signum(), signum);
    }

    @Test
    public void testSignum() {
        signum_helper("0", 0);
        signum_helper("1", 1);
        signum_helper("11", 1);
        signum_helper("5 << 20", 1);
        signum_helper("5 >> 20", 1);
        signum_helper("-1", -1);
        signum_helper("-11", -1);
        signum_helper("-5 << 20", -1);
        signum_helper("-5 >> 20", -1);
    }

    private static void subtract_helper(@NotNull String x, @NotNull String y, @NotNull String output) {
        aeq(read(x).get().subtract(read(y).get()), output);
    }

    private static void subtract_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            read(x).get().subtract(read(y).get());
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubtract() {
        subtract_helper("0", "0", "0");
        subtract_helper("0", "1", "-1");
        subtract_helper("0", "11", "-11");
        subtract_helper("0", "5 << 20", "-5 << 20");
        subtract_helper("0", "5 >> 20", "-5 >> 20");
        subtract_helper("0", "-1", "1");
        subtract_helper("0", "-11", "11");
        subtract_helper("0", "-5 << 20", "5 << 20");
        subtract_helper("0", "-5 >> 20", "5 >> 20");
        subtract_helper("1", "0", "1");
        subtract_helper("1", "1", "0");
        subtract_helper("1", "11", "-5 << 1");
        subtract_helper("1", "5 << 20", "-5242879");
        subtract_helper("1", "5 >> 20", "1048571 >> 20");
        subtract_helper("1", "-1", "1 << 1");
        subtract_helper("1", "-11", "3 << 2");
        subtract_helper("1", "-5 << 20", "5242881");
        subtract_helper("1", "-5 >> 20", "1048581 >> 20");
        subtract_helper("11", "0", "11");
        subtract_helper("11", "1", "5 << 1");
        subtract_helper("11", "11", "0");
        subtract_helper("11", "5 << 20", "-5242869");
        subtract_helper("11", "5 >> 20", "11534331 >> 20");
        subtract_helper("11", "-1", "3 << 2");
        subtract_helper("11", "-11", "11 << 1");
        subtract_helper("11", "-5 << 20", "5242891");
        subtract_helper("11", "-5 >> 20", "11534341 >> 20");
        subtract_helper("5 << 20", "0", "5 << 20");
        subtract_helper("5 << 20", "1", "5242879");
        subtract_helper("5 << 20", "11", "5242869");
        subtract_helper("5 << 20", "5 << 20", "0");
        subtract_helper("5 << 20", "5 >> 20", "5497558138875 >> 20");
        subtract_helper("5 << 20", "-1", "5242881");
        subtract_helper("5 << 20", "-11", "5242891");
        subtract_helper("5 << 20", "-5 << 20", "5 << 21");
        subtract_helper("5 << 20", "-5 >> 20", "5497558138885 >> 20");
        subtract_helper("5 >> 20", "0", "5 >> 20");
        subtract_helper("5 >> 20", "1", "-1048571 >> 20");
        subtract_helper("5 >> 20", "11", "-11534331 >> 20");
        subtract_helper("5 >> 20", "5 << 20", "-5497558138875 >> 20");
        subtract_helper("5 >> 20", "5 >> 20", "0");
        subtract_helper("5 >> 20", "-1", "1048581 >> 20");
        subtract_helper("5 >> 20", "-11", "11534341 >> 20");
        subtract_helper("5 >> 20", "-5 << 20", "5497558138885 >> 20");
        subtract_helper("5 >> 20", "-5 >> 20", "5 >> 19");
        subtract_helper("-1", "0", "-1");
        subtract_helper("-1", "1", "-1 << 1");
        subtract_helper("-1", "11", "-3 << 2");
        subtract_helper("-1", "5 << 20", "-5242881");
        subtract_helper("-1", "5 >> 20", "-1048581 >> 20");
        subtract_helper("-1", "-1", "0");
        subtract_helper("-1", "-11", "5 << 1");
        subtract_helper("-1", "-5 << 20", "5242879");
        subtract_helper("-1", "-5 >> 20", "-1048571 >> 20");
        subtract_helper("-11", "0", "-11");
        subtract_helper("-11", "1", "-3 << 2");
        subtract_helper("-11", "11", "-11 << 1");
        subtract_helper("-11", "5 << 20", "-5242891");
        subtract_helper("-11", "5 >> 20", "-11534341 >> 20");
        subtract_helper("-11", "-1", "-5 << 1");
        subtract_helper("-11", "-11", "0");
        subtract_helper("-11", "-5 << 20", "5242869");
        subtract_helper("-11", "-5 >> 20", "-11534331 >> 20");
        subtract_helper("-5 << 20", "0", "-5 << 20");
        subtract_helper("-5 << 20", "1", "-5242881");
        subtract_helper("-5 << 20", "11", "-5242891");
        subtract_helper("-5 << 20", "5 << 20", "-5 << 21");
        subtract_helper("-5 << 20", "5 >> 20", "-5497558138885 >> 20");
        subtract_helper("-5 << 20", "-1", "-5242879");
        subtract_helper("-5 << 20", "-11", "-5242869");
        subtract_helper("-5 << 20", "-5 << 20", "0");
        subtract_helper("-5 << 20", "-5 >> 20", "-5497558138875 >> 20");
        subtract_helper("-5 >> 20", "0", "-5 >> 20");
        subtract_helper("-5 >> 20", "1", "-1048581 >> 20");
        subtract_helper("-5 >> 20", "11", "-11534341 >> 20");
        subtract_helper("-5 >> 20", "5 << 20", "-5497558138885 >> 20");
        subtract_helper("-5 >> 20", "5 >> 20", "-5 >> 19");
        subtract_helper("-5 >> 20", "-1", "1048571 >> 20");
        subtract_helper("-5 >> 20", "-11", "11534331 >> 20");
        subtract_helper("-5 >> 20", "-5 << 20", "5497558138875 >> 20");
        subtract_helper("-5 >> 20", "-5 >> 20", "0");
        subtract_fail_helper("1 << 2147483647", "-1 << 2147483647");
    }

    private static void multiply_helper(@NotNull String x, @NotNull String y, @NotNull String output) {
        aeq(read(x).get().multiply(read(y).get()), output);
    }

    private static void multiply_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            read(x).get().multiply(read(y).get());
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testMultiply() {
        multiply_helper("0", "0", "0");
        multiply_helper("0", "1", "0");
        multiply_helper("0", "11", "0");
        multiply_helper("0", "5 << 20", "0");
        multiply_helper("0", "5 >> 20", "0");
        multiply_helper("0", "-1", "0");
        multiply_helper("0", "-11", "0");
        multiply_helper("0", "-5 << 20", "0");
        multiply_helper("0", "-5 >> 20", "0");
        multiply_helper("1", "0", "0");
        multiply_helper("1", "1", "1");
        multiply_helper("1", "11", "11");
        multiply_helper("1", "5 << 20", "5 << 20");
        multiply_helper("1", "5 >> 20", "5 >> 20");
        multiply_helper("1", "-1", "-1");
        multiply_helper("1", "-11", "-11");
        multiply_helper("1", "-5 << 20", "-5 << 20");
        multiply_helper("1", "-5 >> 20", "-5 >> 20");
        multiply_helper("11", "0", "0");
        multiply_helper("11", "1", "11");
        multiply_helper("11", "11", "121");
        multiply_helper("11", "5 << 20", "55 << 20");
        multiply_helper("11", "5 >> 20", "55 >> 20");
        multiply_helper("11", "-1", "-11");
        multiply_helper("11", "-11", "-121");
        multiply_helper("11", "-5 << 20", "-55 << 20");
        multiply_helper("11", "-5 >> 20", "-55 >> 20");
        multiply_helper("5 << 20", "0", "0");
        multiply_helper("5 << 20", "1", "5 << 20");
        multiply_helper("5 << 20", "11", "55 << 20");
        multiply_helper("5 << 20", "5 << 20", "25 << 40");
        multiply_helper("5 << 20", "5 >> 20", "25");
        multiply_helper("5 << 20", "-1", "-5 << 20");
        multiply_helper("5 << 20", "-11", "-55 << 20");
        multiply_helper("5 << 20", "-5 << 20", "-25 << 40");
        multiply_helper("5 << 20", "-5 >> 20", "-25");
        multiply_helper("5 >> 20", "0", "0");
        multiply_helper("5 >> 20", "1", "5 >> 20");
        multiply_helper("5 >> 20", "11", "55 >> 20");
        multiply_helper("5 >> 20", "5 << 20", "25");
        multiply_helper("5 >> 20", "5 >> 20", "25 >> 40");
        multiply_helper("5 >> 20", "-1", "-5 >> 20");
        multiply_helper("5 >> 20", "-11", "-55 >> 20");
        multiply_helper("5 >> 20", "-5 << 20", "-25");
        multiply_helper("5 >> 20", "-5 >> 20", "-25 >> 40");
        multiply_helper("-1", "0", "0");
        multiply_helper("-1", "1", "-1");
        multiply_helper("-1", "11", "-11");
        multiply_helper("-1", "5 << 20", "-5 << 20");
        multiply_helper("-1", "5 >> 20", "-5 >> 20");
        multiply_helper("-1", "-1", "1");
        multiply_helper("-1", "-11", "11");
        multiply_helper("-1", "-5 << 20", "5 << 20");
        multiply_helper("-1", "-5 >> 20", "5 >> 20");
        multiply_helper("-11", "0", "0");
        multiply_helper("-11", "1", "-11");
        multiply_helper("-11", "11", "-121");
        multiply_helper("-11", "5 << 20", "-55 << 20");
        multiply_helper("-11", "5 >> 20", "-55 >> 20");
        multiply_helper("-11", "-1", "11");
        multiply_helper("-11", "-11", "121");
        multiply_helper("-11", "-5 << 20", "55 << 20");
        multiply_helper("-11", "-5 >> 20", "55 >> 20");
        multiply_helper("-5 << 20", "0", "0");
        multiply_helper("-5 << 20", "1", "-5 << 20");
        multiply_helper("-5 << 20", "11", "-55 << 20");
        multiply_helper("-5 << 20", "5 << 20", "-25 << 40");
        multiply_helper("-5 << 20", "5 >> 20", "-25");
        multiply_helper("-5 << 20", "-1", "5 << 20");
        multiply_helper("-5 << 20", "-11", "55 << 20");
        multiply_helper("-5 << 20", "-5 << 20", "25 << 40");
        multiply_helper("-5 << 20", "-5 >> 20", "25");
        multiply_helper("-5 >> 20", "0", "0");
        multiply_helper("-5 >> 20", "1", "-5 >> 20");
        multiply_helper("-5 >> 20", "11", "-55 >> 20");
        multiply_helper("-5 >> 20", "5 << 20", "-25");
        multiply_helper("-5 >> 20", "5 >> 20", "-25 >> 40");
        multiply_helper("-5 >> 20", "-1", "5 >> 20");
        multiply_helper("-5 >> 20", "-11", "55 >> 20");
        multiply_helper("-5 >> 20", "-5 << 20", "25");
        multiply_helper("-5 >> 20", "-5 >> 20", "25 >> 40");
        multiply_fail_helper("1 << 2147483647", "1 << 1");
        multiply_fail_helper("1 >> 2147483648", "1 >> 1");
    }

    private static void shiftLeft_helper(@NotNull String input, int bits, @NotNull String output) {
        aeq(read(input).get().shiftLeft(bits), output);
    }

    private static void shiftLeft_fail_helper(@NotNull String input, int bits) {
        try {
            read(input).get().shiftLeft(bits);
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testShiftLeft() {
        shiftLeft_helper("0", 0, "0");
        shiftLeft_helper("0", 5, "0");
        shiftLeft_helper("0", -5, "0");
        shiftLeft_helper("1", 0, "1");
        shiftLeft_helper("1", 5, "1 << 5");
        shiftLeft_helper("1", -5, "1 >> 5");
        shiftLeft_helper("11", 0, "11");
        shiftLeft_helper("11", 5, "11 << 5");
        shiftLeft_helper("11", -5, "11 >> 5");
        shiftLeft_helper("5 << 20", 0, "5 << 20");
        shiftLeft_helper("5 << 20", 5, "5 << 25");
        shiftLeft_helper("5 << 20", -5, "5 << 15");
        shiftLeft_helper("5 >> 20", 0, "5 >> 20");
        shiftLeft_helper("5 >> 20", 5, "5 >> 15");
        shiftLeft_helper("5 >> 20", -5, "5 >> 25");
        shiftLeft_helper("-1", 0, "-1");
        shiftLeft_helper("-1", 5, "-1 << 5");
        shiftLeft_helper("-1", -5, "-1 >> 5");
        shiftLeft_helper("-11", 0, "-11");
        shiftLeft_helper("-11", 5, "-11 << 5");
        shiftLeft_helper("-11", -5, "-11 >> 5");
        shiftLeft_helper("-5 << 20", 0, "-5 << 20");
        shiftLeft_helper("-5 << 20", 5, "-5 << 25");
        shiftLeft_helper("-5 << 20", -5, "-5 << 15");
        shiftLeft_helper("-5 >> 20", 0, "-5 >> 20");
        shiftLeft_helper("-5 >> 20", 5, "-5 >> 15");
        shiftLeft_helper("-5 >> 20", -5, "-5 >> 25");
        shiftLeft_fail_helper("1 << 2147483647", 1);
        shiftLeft_fail_helper("1 >> 2147483648", -1);
    }

    private static void shiftRight_helper(@NotNull String input, int bits, @NotNull String output) {
        aeq(read(input).get().shiftRight(bits), output);
    }

    private static void shiftRight_fail_helper(@NotNull String input, int bits) {
        try {
            read(input).get().shiftRight(bits);
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testShiftRight() {
        shiftRight_helper("0", 0, "0");
        shiftRight_helper("0", 5, "0");
        shiftRight_helper("0", -5, "0");
        shiftRight_helper("1", 0, "1");
        shiftRight_helper("1", 5, "1 >> 5");
        shiftRight_helper("1", -5, "1 << 5");
        shiftRight_helper("11", 0, "11");
        shiftRight_helper("11", 5, "11 >> 5");
        shiftRight_helper("11", -5, "11 << 5");
        shiftRight_helper("5 << 20", 0, "5 << 20");
        shiftRight_helper("5 << 20", 5, "5 << 15");
        shiftRight_helper("5 << 20", -5, "5 << 25");
        shiftRight_helper("5 >> 20", 0, "5 >> 20");
        shiftRight_helper("5 >> 20", 5, "5 >> 25");
        shiftRight_helper("5 >> 20", -5, "5 >> 15");
        shiftRight_helper("-1", 0, "-1");
        shiftRight_helper("-1", 5, "-1 >> 5");
        shiftRight_helper("-1", -5, "-1 << 5");
        shiftRight_helper("-11", 0, "-11");
        shiftRight_helper("-11", 5, "-11 >> 5");
        shiftRight_helper("-11", -5, "-11 << 5");
        shiftRight_helper("-5 << 20", 0, "-5 << 20");
        shiftRight_helper("-5 << 20", 5, "-5 << 15");
        shiftRight_helper("-5 << 20", -5, "-5 << 25");
        shiftRight_helper("-5 >> 20", 0, "-5 >> 20");
        shiftRight_helper("-5 >> 20", 5, "-5 >> 25");
        shiftRight_helper("-5 >> 20", -5, "-5 >> 15");
        shiftRight_fail_helper("1 << 2147483647", -1);
        shiftRight_fail_helper("1 >> 2147483648", 1);
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

    @Test
    public void testCompareTo() {
        testCompareToHelper(readBinaryFractionList("[-5 << 20, -11, -1, -5 >> 20, 0, 5 >> 20, 1, 11, 5 << 20]"));
    }

    private static void read_helper(@NotNull String input, @NotNull String output) {
        aeq(read(input).get(), output);
    }

    private static void read_empty_helper(@NotNull String input) {
        Assert.assertFalse(read(input).isPresent());
    }

    @Test
    public void testRead() {
        read_helper("0", "0");
        read_helper("1", "1");
        read_helper("11", "11");
        read_helper("5 << 20", "5 << 20");
        read_helper("5 >> 20", "5 >> 20");
        read_helper("-1", "-1");
        read_helper("-11", "-11");
        read_helper("-5 << 20", "-5 << 20");
        read_helper("-5 >> 20", "-5 >> 20");
        read_helper("1 << 1000000000", "1 << 1000000000");
        read_helper("1 >> 1000000000", "1 >> 1000000000");
        read_helper("1 << 2147483647", "1 << 2147483647");
        read_helper("1 >> 2147483648", "1 >> 2147483648");
        read_empty_helper("");
        read_empty_helper("a");
        read_empty_helper("0x10");
        read_empty_helper("0.5");
        read_empty_helper(" ");
        read_empty_helper(" 1");
        read_empty_helper("1 ");
        read_empty_helper("1 < 2");
        read_empty_helper("1<<5");
        read_empty_helper("1 <<");
        read_empty_helper("<< 1");
        read_empty_helper("2");
        read_empty_helper("-2");
        read_empty_helper("0 << 5");
        read_empty_helper("0 >> 5");
        read_empty_helper("1 << -5");
        read_empty_helper("1 >> -5");
        read_empty_helper("1 << 0");
        read_empty_helper("1 >> 0");
        read_empty_helper("2 << 1");
        read_empty_helper("2 >> 1");
        read_empty_helper("1 << 10000000000");
    }

    private static void findIn_helper(@NotNull String input, @NotNull String output) {
        aeq(findIn(input).get(), output);
    }

    private static void findIn_empty_helper(@NotNull String input) {
        Assert.assertFalse(findIn(input).isPresent());
    }

    @Test
    public void testFindIn() {
        findIn_helper("abc1 << 2xyz", "(1 << 2, 3)");
        findIn_helper("abc1<<2xyz", "(1, 3)");
        findIn_helper("01 >> 5 ", "(0, 0)");
        findIn_helper("a1 >> 5 ", "(1 >> 5, 1)");
        findIn_helper("--3 << 25a", "(-3 << 25, 1)");
        findIn_helper("1 << 1111111111111", "(1 << 1111111111, 0)");
        findIn_helper("5 << 1 << 3", "(5 << 1, 0)");
        findIn_helper("6 << 1 << 3", "(1 << 3, 5)");
        findIn_helper("20", "(0, 1)");
        findIn_empty_helper("");
        findIn_empty_helper("abc");
    }

    private static @NotNull List<BinaryFraction> readBinaryFractionList(@NotNull String s) {
        return Readers.readList(BinaryFraction::read).apply(s).get();
    }
}
