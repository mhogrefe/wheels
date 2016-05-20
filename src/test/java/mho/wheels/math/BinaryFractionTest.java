package mho.wheels.math;

import mho.wheels.io.Readers;
import mho.wheels.numberUtils.FloatingPointUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.iterate;
import static mho.wheels.iterables.IterableUtils.toList;
import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.math.BinaryFraction.sum;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

public strictfp class BinaryFractionTest {
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
        aeq(readStrict(x).get().getMantissa(), output);
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
        aeq(readStrict(x).get().getExponent(), output);
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

    private static void of_BigInteger_int_helper(@NotNull String mantissa, int exponent, @NotNull String output) {
        BinaryFraction bf = of(Readers.readBigIntegerStrict(mantissa).get(), exponent);
        bf.validate();
        aeq(bf, output);
    }

    private static void of_BigInteger_int_fail_helper(@NotNull String mantissa, int exponent) {
        try {
            of(Readers.readBigIntegerStrict(mantissa).get(), exponent);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testOf_BigInteger_int() {
        of_BigInteger_int_helper("0", 0, "0");
        of_BigInteger_int_helper("0", 1, "0");
        of_BigInteger_int_helper("0", -3, "0");
        of_BigInteger_int_helper("1", 0, "1");
        of_BigInteger_int_helper("2", 0, "1 << 1");
        of_BigInteger_int_helper("1", 1, "1 << 1");
        of_BigInteger_int_helper("5", 20, "5 << 20");
        of_BigInteger_int_helper("5", -20, "5 >> 20");
        of_BigInteger_int_helper("100", 0, "25 << 2");
        of_BigInteger_int_helper("-1", 0, "-1");
        of_BigInteger_int_helper("-2", 0, "-1 << 1");
        of_BigInteger_int_helper("-1", 1, "-1 << 1");
        of_BigInteger_int_helper("-5", 20, "-5 << 20");
        of_BigInteger_int_helper("-5", -20, "-5 >> 20");
        of_BigInteger_int_helper("-100", 0, "-25 << 2");
        of_BigInteger_int_helper("1", Integer.MAX_VALUE, "1 << 2147483647");
        of_BigInteger_int_helper("1", Integer.MIN_VALUE, "1 >> 2147483648");

        of_BigInteger_int_fail_helper("4", Integer.MAX_VALUE);
    }

    private static void of_BigInteger_helper(int n, @NotNull String output) {
        BinaryFraction bf = of(BigInteger.valueOf(n));
        bf.validate();
        aeq(bf, output);
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
        BinaryFraction bf = of(n);
        bf.validate();
        aeq(bf, output);
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
        Optional<BinaryFraction> bf = of(f);
        if (bf.isPresent()) {
            bf.get().validate();
        }
        aeq(bf, output);
    }

    @Test
    public void testOf_float() {
        of_float_helper(0.0f, "Optional[0]");
        of_float_helper(1.0f, "Optional[1]");
        of_float_helper(0.5f, "Optional[1 >> 1]");
        of_float_helper(0.25f, "Optional[1 >> 2]");
        of_float_helper(1.0f / 3.0f, "Optional[11184811 >> 25]");
        of_float_helper(2.0f, "Optional[1 << 1]");
        of_float_helper(4.0f, "Optional[1 << 2]");
        of_float_helper(3.0f, "Optional[3]");
        of_float_helper(1.5f, "Optional[3 >> 1]");
        of_float_helper(1000.0f, "Optional[125 << 3]");
        of_float_helper(0.001f, "Optional[8589935 >> 33]");
        of_float_helper((float) Math.PI, "Optional[13176795 >> 22]");
        of_float_helper((float) Math.E, "Optional[2850325 >> 20]");
        of_float_helper(Float.MIN_VALUE, "Optional[1 >> 149]");
        of_float_helper(Float.MIN_NORMAL, "Optional[1 >> 126]");
        of_float_helper(Float.MAX_VALUE, "Optional[16777215 << 104]");
        of_float_helper(-0.0f, "Optional[0]");
        of_float_helper(-1.0f, "Optional[-1]");
        of_float_helper(-0.5f, "Optional[-1 >> 1]");
        of_float_helper(-0.25f, "Optional[-1 >> 2]");
        of_float_helper(-1.0f / 3.0f, "Optional[-11184811 >> 25]");
        of_float_helper(-2.0f, "Optional[-1 << 1]");
        of_float_helper(-4.0f, "Optional[-1 << 2]");
        of_float_helper(-3.0f, "Optional[-3]");
        of_float_helper(-1.5f, "Optional[-3 >> 1]");
        of_float_helper(-1000.0f, "Optional[-125 << 3]");
        of_float_helper(-0.001f, "Optional[-8589935 >> 33]");
        of_float_helper((float) -Math.PI, "Optional[-13176795 >> 22]");
        of_float_helper((float) -Math.E, "Optional[-2850325 >> 20]");
        of_float_helper(-Float.MIN_VALUE, "Optional[-1 >> 149]");
        of_float_helper(-Float.MIN_NORMAL, "Optional[-1 >> 126]");
        of_float_helper(-Float.MAX_VALUE, "Optional[-16777215 << 104]");

        of_float_helper(Float.POSITIVE_INFINITY, "Optional.empty");
        of_float_helper(Float.NEGATIVE_INFINITY, "Optional.empty");
        of_float_helper(Float.NaN, "Optional.empty");
    }

    private static void of_double_helper(double d, @NotNull String output) {
        Optional<BinaryFraction> bf = of(d);
        if (bf.isPresent()) {
            bf.get().validate();
        }
        aeq(bf, output);
    }

    @Test
    public void testOfMantissaAndExponent_double() {
        of_double_helper(0.0, "Optional[0]");
        of_double_helper(1.0, "Optional[1]");
        of_double_helper(0.5, "Optional[1 >> 1]");
        of_double_helper(0.25, "Optional[1 >> 2]");
        of_double_helper(1.0 / 3.0, "Optional[6004799503160661 >> 54]");
        of_double_helper(2.0, "Optional[1 << 1]");
        of_double_helper(4.0, "Optional[1 << 2]");
        of_double_helper(3.0, "Optional[3]");
        of_double_helper(1.5, "Optional[3 >> 1]");
        of_double_helper(1000.0, "Optional[125 << 3]");
        of_double_helper(0.001, "Optional[1152921504606847 >> 60]");
        of_double_helper(Math.PI, "Optional[884279719003555 >> 48]");
        of_double_helper(Math.E, "Optional[6121026514868073 >> 51]");
        of_double_helper(Double.MIN_VALUE, "Optional[1 >> 1074]");
        of_double_helper(Double.MIN_NORMAL, "Optional[1 >> 1022]");
        of_double_helper(Double.MAX_VALUE, "Optional[9007199254740991 << 971]");
        of_double_helper(-0.0, "Optional[0]");
        of_double_helper(-1.0, "Optional[-1]");
        of_double_helper(-0.5, "Optional[-1 >> 1]");
        of_double_helper(-0.25, "Optional[-1 >> 2]");
        of_double_helper(-1.0 / 3.0, "Optional[-6004799503160661 >> 54]");
        of_double_helper(-2.0, "Optional[-1 << 1]");
        of_double_helper(-4.0, "Optional[-1 << 2]");
        of_double_helper(-3.0, "Optional[-3]");
        of_double_helper(-1.5, "Optional[-3 >> 1]");
        of_double_helper(-1000.0, "Optional[-125 << 3]");
        of_double_helper(-0.001, "Optional[-1152921504606847 >> 60]");
        of_double_helper(-Math.PI, "Optional[-884279719003555 >> 48]");
        of_double_helper(-Math.E, "Optional[-6121026514868073 >> 51]");
        of_double_helper(-Double.MIN_VALUE, "Optional[-1 >> 1074]");
        of_double_helper(-Double.MIN_NORMAL, "Optional[-1 >> 1022]");
        of_double_helper(-Double.MAX_VALUE, "Optional[-9007199254740991 << 971]");

        of_double_helper(Double.POSITIVE_INFINITY, "Optional.empty");
        of_double_helper(Double.NEGATIVE_INFINITY, "Optional.empty");
        of_double_helper(Double.NaN, "Optional.empty");
    }

    private static void bigIntegerValueExact_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get().bigIntegerValueExact(), output);
    }

    private static void bigIntegerValueExact_fail_helper(@NotNull String input) {
        try {
            readStrict(input).get().bigIntegerValueExact();
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigIntegerValueExact() {
        bigIntegerValueExact_helper("0", "0");
        bigIntegerValueExact_helper("1", "1");
        bigIntegerValueExact_helper("11", "11");
        bigIntegerValueExact_helper("5 << 20", "5242880");
        bigIntegerValueExact_helper("-1", "-1");
        bigIntegerValueExact_helper("-11", "-11");
        bigIntegerValueExact_helper("-5 << 20", "-5242880");

        bigIntegerValueExact_fail_helper("5 >> 20");
        bigIntegerValueExact_fail_helper("-5 >> 20");
    }

    private static void bigDecimalValue_helper(@NotNull BinaryFraction input, @NotNull String output) {
        aeq(input.bigDecimalValue(), output);
    }

    private static void bigDecimalValue_helper(@NotNull String input, @NotNull String output) {
        bigDecimalValue_helper(readStrict(input).get(), output);
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

    private static void floatRange_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get().floatRange(), output);
    }

    @Test
    public void testFloatRange() {
        BinaryFraction almostOne = ONE.subtract(ONE.shiftRight(1000));
        BinaryFraction trillion = of(new BigInteger("1000000000000"));
        BinaryFraction pi = of((float) Math.PI).get();
        BinaryFraction piSuccessor = of(FloatingPointUtils.successor((float) Math.PI)).get();
        BinaryFraction piPredecessor = of(FloatingPointUtils.predecessor((float) Math.PI)).get();
        BinaryFraction halfAbovePi = pi.add(piSuccessor).shiftRight(1);
        BinaryFraction halfBelowPi = pi.add(piPredecessor).shiftRight(1);
        float subnormalFloat = 1.0e-40f;
        BinaryFraction subnormal = of(subnormalFloat).get();
        BinaryFraction subnormalSuccessor = of(FloatingPointUtils.successor(subnormalFloat)).get();
        BinaryFraction subnormalPredecessor = of(FloatingPointUtils.predecessor(subnormalFloat)).get();
        BinaryFraction halfAboveSubnormal = subnormal.add(subnormalSuccessor).shiftRight(1);
        BinaryFraction halfBelowSubnormal = subnormal.add(subnormalPredecessor).shiftRight(1);
        BinaryFraction subnormalBoundary = LARGEST_SUBNORMAL_FLOAT.add(SMALLEST_NORMAL_FLOAT).shiftRight(1);
        floatRange_helper("0", "(0.0, 0.0)");
        floatRange_helper("1", "(1.0, 1.0)");
        floatRange_helper("11", "(11.0, 11.0)");
        floatRange_helper("5 << 20", "(5242880.0, 5242880.0)");
        floatRange_helper("5 >> 20", "(4.7683716E-6, 4.7683716E-6)");
        floatRange_helper("1 << 2147483647", "(3.4028235E38, Infinity)");
        floatRange_helper("1 >> 2147483648", "(0.0, 1.4E-45)");
        aeq(almostOne.floatRange(), "(0.99999994, 1.0)");
        aeq(trillion.floatRange(), "(1.0E12, 1.00000006E12)");
        aeq(pi.floatRange(), "(3.1415927, 3.1415927)");
        aeq(halfAbovePi.floatRange(), "(3.1415927, 3.141593)");
        aeq(halfBelowPi.floatRange(), "(3.1415925, 3.1415927)");
        aeq(halfAboveSubnormal.floatRange(), "(1.0E-40, 1.00001E-40)");
        aeq(halfBelowSubnormal.floatRange(), "(9.9998E-41, 1.0E-40)");
        aeq(subnormalBoundary.floatRange(), "(1.1754942E-38, 1.17549435E-38)");
        floatRange_helper("-1", "(-1.0, -1.0)");
        floatRange_helper("-11", "(-11.0, -11.0)");
        floatRange_helper("-5 << 20", "(-5242880.0, -5242880.0)");
        floatRange_helper("-5 >> 20", "(-4.7683716E-6, -4.7683716E-6)");
        floatRange_helper("-1 << 2147483647", "(-Infinity, -3.4028235E38)");
        floatRange_helper("-1 >> 2147483648", "(-1.4E-45, -0.0)");
        aeq(almostOne.negate().floatRange(), "(-1.0, -0.99999994)");
        aeq(trillion.negate().floatRange(), "(-1.00000006E12, -1.0E12)");
        aeq(pi.negate().floatRange(), "(-3.1415927, -3.1415927)");
        aeq(halfAbovePi.negate().floatRange(), "(-3.141593, -3.1415927)");
        aeq(halfBelowPi.negate().floatRange(), "(-3.1415927, -3.1415925)");
        aeq(halfAboveSubnormal.negate().floatRange(), "(-1.00001E-40, -1.0E-40)");
        aeq(halfBelowSubnormal.negate().floatRange(), "(-1.0E-40, -9.9998E-41)");
        aeq(subnormalBoundary.negate().floatRange(), "(-1.17549435E-38, -1.1754942E-38)");
        BinaryFraction aboveNegativeMax = LARGEST_FLOAT.negate().add(ONE);
        BinaryFraction belowNegativeMax = LARGEST_FLOAT.negate().subtract(ONE);
        BinaryFraction belowMax = LARGEST_FLOAT.subtract(ONE);
        BinaryFraction aboveMax = LARGEST_FLOAT.add(ONE);
        BinaryFraction justAboveZero = SMALLEST_FLOAT.shiftRight(1);
        BinaryFraction justBelowZero = SMALLEST_FLOAT.negate().shiftRight(1);
        aeq(aboveNegativeMax.floatRange(), "(-3.4028235E38, -3.4028233E38)");
        aeq(belowNegativeMax.floatRange(), "(-Infinity, -3.4028235E38)");
        aeq(belowMax.floatRange(), "(3.4028233E38, 3.4028235E38)");
        aeq(aboveMax.floatRange(), "(3.4028235E38, Infinity)");
        aeq(justAboveZero.floatRange(), "(0.0, 1.4E-45)");
        aeq(justBelowZero.floatRange(), "(-1.4E-45, -0.0)");
    }

    private static void doubleRange_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get().doubleRange(), output);
    }

    @Test
    public void testDoubleRange() {
        BinaryFraction almostOne = ONE.subtract(ONE.shiftRight(1000));
        BinaryFraction googol = of(
                new BigInteger(
                        "1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000"
                )
        );
        BinaryFraction pi = of(Math.PI).get();
        BinaryFraction piSuccessor = of(FloatingPointUtils.successor(Math.PI)).get();
        BinaryFraction piPredecessor = of(FloatingPointUtils.predecessor(Math.PI)).get();
        BinaryFraction halfAbovePi = pi.add(piSuccessor).shiftRight(1);
        BinaryFraction halfBelowPi = pi.add(piPredecessor).shiftRight(1);
        double subnormalDouble = 1.0e-310;
        BinaryFraction subnormal = of(subnormalDouble).get();
        BinaryFraction subnormalSuccessor = of(FloatingPointUtils.successor(subnormalDouble)).get();
        BinaryFraction subnormalPredecessor = of(FloatingPointUtils.predecessor(subnormalDouble)).get();
        BinaryFraction halfAboveSubnormal = subnormal.add(subnormalSuccessor).shiftRight(1);
        BinaryFraction halfBelowSubnormal = subnormal.add(subnormalPredecessor).shiftRight(1);
        BinaryFraction subnormalBoundary = LARGEST_SUBNORMAL_DOUBLE.add(SMALLEST_NORMAL_DOUBLE).shiftRight(1);
        doubleRange_helper("0", "(0.0, 0.0)");
        doubleRange_helper("1", "(1.0, 1.0)");
        doubleRange_helper("11", "(11.0, 11.0)");
        doubleRange_helper("5 << 20", "(5242880.0, 5242880.0)");
        doubleRange_helper("5 >> 20", "(4.76837158203125E-6, 4.76837158203125E-6)");
        doubleRange_helper("1 << 2147483647", "(1.7976931348623157E308, Infinity)");
        doubleRange_helper("1 >> 2147483648", "(0.0, 4.9E-324)");
        aeq(almostOne.doubleRange(), "(0.9999999999999999, 1.0)");
        aeq(googol.doubleRange(), "(9.999999999999998E99, 1.0E100)");
        aeq(pi.doubleRange(), "(3.141592653589793, 3.141592653589793)");
        aeq(halfAbovePi.doubleRange(), "(3.141592653589793, 3.1415926535897936)");
        aeq(halfBelowPi.doubleRange(), "(3.1415926535897927, 3.141592653589793)");
        aeq(halfAboveSubnormal.doubleRange(), "(1.0E-310, 1.00000000000005E-310)");
        aeq(halfBelowSubnormal.doubleRange(), "(9.9999999999995E-311, 1.0E-310)");
        aeq(subnormalBoundary.doubleRange(), "(2.225073858507201E-308, 2.2250738585072014E-308)");
        doubleRange_helper("-1", "(-1.0, -1.0)");
        doubleRange_helper("-11", "(-11.0, -11.0)");
        doubleRange_helper("-5 << 20", "(-5242880.0, -5242880.0)");
        doubleRange_helper("-5 >> 20", "(-4.76837158203125E-6, -4.76837158203125E-6)");
        doubleRange_helper("-1 << 2147483647", "(-Infinity, -1.7976931348623157E308)");
        doubleRange_helper("-1 >> 2147483648", "(-4.9E-324, -0.0)");
        aeq(almostOne.negate().doubleRange(), "(-1.0, -0.9999999999999999)");
        aeq(googol.negate().doubleRange(), "(-1.0E100, -9.999999999999998E99)");
        aeq(pi.negate().doubleRange(), "(-3.141592653589793, -3.141592653589793)");
        aeq(halfAbovePi.negate().doubleRange(), "(-3.1415926535897936, -3.141592653589793)");
        aeq(halfBelowPi.negate().doubleRange(), "(-3.141592653589793, -3.1415926535897927)");
        aeq(halfAboveSubnormal.negate().doubleRange(), "(-1.00000000000005E-310, -1.0E-310)");
        aeq(halfBelowSubnormal.negate().doubleRange(), "(-1.0E-310, -9.9999999999995E-311)");
        aeq(subnormalBoundary.negate().doubleRange(), "(-2.2250738585072014E-308, -2.225073858507201E-308)");
        BinaryFraction aboveNegativeMax = LARGEST_DOUBLE.negate().add(ONE);
        BinaryFraction belowNegativeMax = LARGEST_DOUBLE.negate().subtract(ONE);
        BinaryFraction belowMax = LARGEST_DOUBLE.subtract(ONE);
        BinaryFraction aboveMax = LARGEST_DOUBLE.add(ONE);
        BinaryFraction justAboveZero = SMALLEST_DOUBLE.shiftRight(1);
        BinaryFraction justBelowZero = SMALLEST_DOUBLE.negate().shiftRight(1);
        aeq(aboveNegativeMax.doubleRange(), "(-1.7976931348623157E308, -1.7976931348623155E308)");
        aeq(belowNegativeMax.doubleRange(), "(-Infinity, -1.7976931348623157E308)");
        aeq(belowMax.doubleRange(), "(1.7976931348623155E308, 1.7976931348623157E308)");
        aeq(aboveMax.doubleRange(), "(1.7976931348623157E308, Infinity)");
        aeq(justAboveZero.doubleRange(), "(0.0, 4.9E-324)");
        aeq(justBelowZero.doubleRange(), "(-4.9E-324, -0.0)");
    }

    private static void isInteger_helper(@NotNull String input, boolean output) {
        BinaryFraction bf = readStrict(input).get();
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
        aeq(readStrict(x).get().add(readStrict(y).get()), output);
    }

    private static void add_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            readStrict(x).get().add(readStrict(y).get());
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
        aeq(readStrict(input).get().negate(), output);
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
        aeq(readStrict(input).get().abs(), output);
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
        aeq(readStrict(input).get().signum(), signum);
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
        aeq(readStrict(x).get().subtract(readStrict(y).get()), output);
    }

    private static void subtract_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            readStrict(x).get().subtract(readStrict(y).get());
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
        aeq(readStrict(x).get().multiply(readStrict(y).get()), output);
    }

    private static void multiply_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            readStrict(x).get().multiply(readStrict(y).get());
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
        aeq(readStrict(input).get().shiftLeft(bits), output);
    }

    private static void shiftLeft_fail_helper(@NotNull String input, int bits) {
        try {
            readStrict(input).get().shiftLeft(bits);
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
        aeq(readStrict(input).get().shiftRight(bits), output);
    }

    private static void shiftRight_fail_helper(@NotNull String input, int bits) {
        try {
            readStrict(input).get().shiftRight(bits);
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

    private static void sum_helper(@NotNull String input, @NotNull String output) {
        aeq(BinaryFraction.sum(readBinaryFractionList(input)), output);
    }

    private static void sum_null_helper(@NotNull String input) {
        try {
            sum(readBinaryFractionListWithNulls(input));
            Assert.fail();
        } catch (NullPointerException ignored) {}
    }

    private static void sum_arithmetic_helper(@NotNull String input) {
        try {
            sum(readBinaryFractionList(input));
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSum() {
        sum_helper("[]", "0");
        sum_helper("[5 >> 20]", "5 >> 20");
        sum_helper("[1, 11, 5 << 20, 5 >> 20]", "5497570721797 >> 20");
        sum_helper("[1 << 2147483647, 1 << 2147483647, -1 << 2147483647]", "1 << 2147483647");
        sum_helper("[-1 << 2147483647, -1 << 2147483647, 1 << 2147483647]", "-1 << 2147483647");
        sum_null_helper("[1, 11, null, 5 >> 20]");
        sum_arithmetic_helper("[1 << 2147483647, 1 << 2147483647]");
        sum_arithmetic_helper("[-1 << 2147483647, -1 << 2147483647]");
    }

    private static void product_helper(@NotNull String input, @NotNull String output) {
        aeq(BinaryFraction.product(readBinaryFractionList(input)), output);
    }

    private static void product_null_helper(@NotNull String input) {
        try {
            product(readBinaryFractionListWithNulls(input));
            Assert.fail();
        } catch (NullPointerException ignored) {}
    }

    private static void product_arithmetic_helper(@NotNull String input) {
        try {
            product(readBinaryFractionList(input));
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testProduct() {
        product_helper("[]", "1");
        product_helper("[5 >> 20]", "5 >> 20");
        product_helper("[1, 11, 5 << 20, 5 >> 20]", "275");
        product_helper("[1 << 2147483647, 1 << 1, 1 >> 1]", "1 << 2147483647");
        product_helper("[1 >> 2147483648, 1 >> 1, 1 << 1]", "1 >> 2147483648");
        product_null_helper("[1, 11, null, 5 >> 20]");
        product_arithmetic_helper("[1 << 2147483647, 1 << 1]");
        product_arithmetic_helper("[1 >> 2147483648, 1 >> 1]");
    }

    private static void delta_helper(@NotNull String input, @NotNull String output) {
        aeqit(delta(readBinaryFractionList(input)), output);
    }

    private static void delta_null_helper(@NotNull String input) {
        try {
            toList(delta(readBinaryFractionListWithNulls(input)));
            Assert.fail();
        } catch (NullPointerException ignored) {}
    }

    private static void delta_arithmetic_helper(@NotNull String input) {
        try {
            toList(delta(readBinaryFractionList(input)));
            Assert.fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testDelta() {
        delta_helper("[5 >> 20]", "[]");
        delta_helper("[1, 11, 5 << 20, 5 >> 20]", "[5 << 1, 5242869, -5497558138875 >> 20]");
        aeqitLimit(TINY_LIMIT, delta(iterate(bf -> bf.shiftRight(1), ONE)),
                "[-1 >> 1, -1 >> 2, -1 >> 3, -1 >> 4, -1 >> 5, -1 >> 6, -1 >> 7, -1 >> 8, -1 >> 9, -1 >> 10," +
                " -1 >> 11, -1 >> 12, -1 >> 13, -1 >> 14, -1 >> 15, -1 >> 16, -1 >> 17, -1 >> 18, -1 >> 19," +
                " -1 >> 20, ...]");
        delta_null_helper("[1, 11, null, 5 >> 20]");
        delta_arithmetic_helper("[-1 << 2147483647, 1 << 2147483647]");
        delta_arithmetic_helper("[1 << 2147483647, -1 << 2147483647]");
    }

    private static void floor_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get().floor(), output);
    }

    @Test
    public void testFloor() {
        floor_helper("0", "0");
        floor_helper("1", "1");
        floor_helper("11", "11");
        floor_helper("5 << 20", "5242880");
        floor_helper("5 >> 20", "0");
        floor_helper("3 >> 1", "1");
        floor_helper("5 >> 1", "2");
        floor_helper("-1", "-1");
        floor_helper("-11", "-11");
        floor_helper("-5 << 20", "-5242880");
        floor_helper("-5 >> 20", "-1");
        floor_helper("-3 >> 1", "-2");
        floor_helper("-5 >> 1", "-3");
    }

    private static void ceiling_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get().ceiling(), output);
    }

    @Test
    public void testCeiling() {
        ceiling_helper("0", "0");
        ceiling_helper("1", "1");
        ceiling_helper("11", "11");
        ceiling_helper("5 << 20", "5242880");
        ceiling_helper("5 >> 20", "1");
        ceiling_helper("3 >> 1", "2");
        ceiling_helper("5 >> 1", "3");
        ceiling_helper("-1", "-1");
        ceiling_helper("-11", "-11");
        ceiling_helper("-5 << 20", "-5242880");
        ceiling_helper("-5 >> 20", "0");
        ceiling_helper("-3 >> 1", "-1");
        ceiling_helper("-5 >> 1", "-2");
    }

    @Test
    public void testEquals() {
        testEqualsHelper(
                readBinaryFractionList("[0, 1, 11, 5 << 20, 5 >> 20, -1, -11, -5 << 20, -5 >> 20]"),
                readBinaryFractionList("[0, 1, 11, 5 << 20, 5 >> 20, -1, -11, -5 << 20, -5 >> 20]")
        );
    }

    private static void hashCode_helper(@NotNull String input, int hashCode) {
        aeq(readStrict(input).get().hashCode(), hashCode);
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

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input).get(), output);
    }

    private static void readStrict_empty_helper(@NotNull String input) {
        Assert.assertFalse(readStrict(input).isPresent());
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("0", "0");
        readStrict_helper("1", "1");
        readStrict_helper("11", "11");
        readStrict_helper("5 << 20", "5 << 20");
        readStrict_helper("5 >> 20", "5 >> 20");
        readStrict_helper("-1", "-1");
        readStrict_helper("-11", "-11");
        readStrict_helper("-5 << 20", "-5 << 20");
        readStrict_helper("-5 >> 20", "-5 >> 20");
        readStrict_helper("1 << 1000000000", "1 << 1000000000");
        readStrict_helper("1 >> 1000000000", "1 >> 1000000000");
        readStrict_helper("1 << 2147483647", "1 << 2147483647");
        readStrict_helper("1 >> 2147483648", "1 >> 2147483648");
        readStrict_empty_helper("");
        readStrict_empty_helper("a");
        readStrict_empty_helper("0x10");
        readStrict_empty_helper("0.5");
        readStrict_empty_helper(" ");
        readStrict_empty_helper(" 1");
        readStrict_empty_helper("1 ");
        readStrict_empty_helper("1 < 2");
        readStrict_empty_helper("1<<5");
        readStrict_empty_helper("1 <<");
        readStrict_empty_helper("<< 1");
        readStrict_empty_helper("2");
        readStrict_empty_helper("-2");
        readStrict_empty_helper("0 << 5");
        readStrict_empty_helper("0 >> 5");
        readStrict_empty_helper("1 << -5");
        readStrict_empty_helper("1 >> -5");
        readStrict_empty_helper("1 << 0");
        readStrict_empty_helper("1 >> 0");
        readStrict_empty_helper("2 << 1");
        readStrict_empty_helper("2 >> 1");
        readStrict_empty_helper("1 << 10000000000");
    }

    private static @NotNull List<BinaryFraction> readBinaryFractionList(@NotNull String s) {
        return Readers.readListStrict(BinaryFraction::readStrict).apply(s).get();
    }

    private static @NotNull List<BinaryFraction> readBinaryFractionListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(BinaryFraction::readStrict).apply(s).get();
    }
}
