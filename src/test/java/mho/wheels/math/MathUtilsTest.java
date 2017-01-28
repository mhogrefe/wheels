package mho.wheels.math;

import mho.wheels.io.Readers;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

public class MathUtilsTest {
    @Test
    public void testConstants() {
        Testing.aeqitLimit(SMALL_LIMIT, INT_PRIMES,
                "[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97," +
                " 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193," +
                " 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307," +
                " 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421," +
                " 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, ...]");
        testNoRemove(SMALL_LIMIT, INT_PRIMES);
        Testing.aeqitLimit(SMALL_LIMIT, PRIMES,
                "[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97," +
                " 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193," +
                " 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307," +
                " 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421," +
                " 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, ...]");
        testNoRemove(SMALL_LIMIT, PRIMES);

        aeq(charsToString(map(b -> b ? '1' : '0', take(SMALL_LIMIT, THUE_MORSE))),
            "0110100110010110100101100110100110010110011010010110100110010110100101100110100101101001100101100110");
        testNoRemove(SMALL_LIMIT, THUE_MORSE);

        aeq(charsToString(map(IntegerUtils::toDigit, take(SMALL_LIMIT, KOLAKOSKI))),
            "1221121221221121122121121221121121221221121221211211221221121221221121121221211221221121221221121122");
        testNoRemove(SMALL_LIMIT, KOLAKOSKI);

        aeqitLimit(TINY_LIMIT / 2, map(is -> IntegerUtils.fromBigEndianDigits(10, is), LOOK_AND_SAY),
                "[1, 11, 21, 1211, 111221, 312211, 13112221, 1113213211, 31131211131221, 13211311123113112211, ...]");
        testNoRemove(TINY_LIMIT, LOOK_AND_SAY);

        aeqitLimit(TINY_LIMIT / 2, SYLVESTER,
                "[2, 3, 7, 43, 1807, 3263443, 10650056950807, 113423713055421844361000443," +
                " 12864938683278671740537145998360961546653259485195807," +
                " 16550664732451996419846819544443918001751315270637749784185138876653586863957240680891198813173764" +
                "5185443, ...]");
        testNoRemove(TINY_LIMIT, SYLVESTER);
    }

    private static void pow_helper(int n, int p, int output) {
        aeq(pow(n, p), output);
    }

    private static void pow_fail_helper(int n, int p) {
        try {
            pow(n, p);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPow() {
        pow_helper(0, 0, 1);
        pow_helper(0, 1, 0);
        pow_helper(0, 2, 0);
        pow_helper(0, 3, 0);

        pow_helper(1, 0, 1);
        pow_helper(1, 1, 1);
        pow_helper(1, 2, 1);
        pow_helper(1, 3, 1);

        pow_helper(-1, 0, 1);
        pow_helper(-1, 1, -1);
        pow_helper(-1, 2, 1);
        pow_helper(-1, 3, -1);

        pow_helper(2, 0, 1);
        pow_helper(2, 1, 2);
        pow_helper(2, 2, 4);
        pow_helper(2, 3, 8);
        pow_helper(2, 10, 1024);
        pow_helper(2, 30, 1073741824);

        pow_helper(-2, 0, 1);
        pow_helper(-2, 1, -2);
        pow_helper(-2, 2, 4);
        pow_helper(-2, 3, -8);
        pow_helper(-2, 10, 1024);
        pow_helper(-2, 30, 1073741824);
        pow_helper(-2, 31, -2147483648);

        pow_helper(3, 0, 1);
        pow_helper(3, 1, 3);
        pow_helper(3, 2, 9);
        pow_helper(3, 3, 27);

        pow_helper(-3, 0, 1);
        pow_helper(-3, 1, -3);
        pow_helper(-3, 2, 9);
        pow_helper(-3, 3, -27);

        pow_helper(10, 0, 1);
        pow_helper(10, 1, 10);
        pow_helper(10, 2, 100);
        pow_helper(10, 3, 1000);
        pow_helper(10, 9, 1000000000);

        pow_fail_helper(0, -1);
        pow_fail_helper(1, -1);
        pow_fail_helper(-1, -1);
        pow_fail_helper(2, -1);
        pow_fail_helper(-2, -1);
        pow_fail_helper(3, -1);
        pow_fail_helper(-3, -1);
        pow_fail_helper(2, 31);
        pow_fail_helper(-2, 32);
        pow_fail_helper(10, 10);
        pow_fail_helper(-10, 10);
    }

    private static void gcd_int_int_helper(int x, int y, int output) {
        aeq(gcd(x, y), output);
    }

    private static void gcd_int_int_fail_helper(int x, int y) {
        try {
            gcd(x, y);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testGcd_int_int() {
        gcd_int_int_helper(0, 0, 0);
        gcd_int_int_helper(6, 0, 6);
        gcd_int_int_helper(-6, 0, 6);
        gcd_int_int_helper(0, 6, 6);
        gcd_int_int_helper(0, -6, 6);
        gcd_int_int_helper(12, 15, 3);
        gcd_int_int_helper(35, 210, 35);
        gcd_int_int_helper(17, 20, 1);
        gcd_int_int_helper(1, 5, 1);
        gcd_int_int_helper(-12, 15, 3);
        gcd_int_int_helper(12, -15, 3);
        gcd_int_int_helper(-12, -15, 3);

        gcd_int_int_helper(Integer.MIN_VALUE, 1024, 1024);
        gcd_int_int_helper(1024, Integer.MIN_VALUE, 1024);
        gcd_int_int_helper(Integer.MIN_VALUE, 1023, 1);
        gcd_int_int_helper(1023, Integer.MIN_VALUE, 1);
        gcd_int_int_helper(Integer.MIN_VALUE, -1024, 1024);
        gcd_int_int_helper(-1024, Integer.MIN_VALUE, 1024);
        gcd_int_int_helper(Integer.MIN_VALUE, -1023, 1);
        gcd_int_int_helper(-1023, Integer.MIN_VALUE, 1);

        gcd_int_int_fail_helper(0, Integer.MIN_VALUE);
        gcd_int_int_fail_helper(Integer.MIN_VALUE, 0);
        gcd_int_int_fail_helper(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private static void gcd_long_long_helper(long x, long y, long output) {
        aeq(gcd(x, y), output);
    }

    private static void gcd_long_long_fail_helper(long x, long y) {
        try {
            gcd(x, y);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testGcd_long_long() {
        gcd_long_long_helper(0L, 0L, 0L);
        gcd_long_long_helper(6L, 0L, 6L);
        gcd_long_long_helper(-6L, 0L, 6L);
        gcd_long_long_helper(0L, 6L, 6L);
        gcd_long_long_helper(0L, -6L, 6L);
        gcd_long_long_helper(12L, 15L, 3L);
        gcd_long_long_helper(35L, 210L, 35L);
        gcd_long_long_helper(17L, 20L, 1L);
        gcd_long_long_helper(1L, 5L, 1L);
        gcd_long_long_helper(-12L, 15L, 3L);
        gcd_long_long_helper(12L, -15L, 3L);
        gcd_long_long_helper(-12L, -15L, 3L);

        gcd_long_long_helper(Long.MIN_VALUE, 1024L, 1024L);
        gcd_long_long_helper(1024L, Long.MIN_VALUE, 1024L);
        gcd_long_long_helper(Long.MIN_VALUE, 1023L, 1L);
        gcd_long_long_helper(1023L, Long.MIN_VALUE, 1L);
        gcd_long_long_helper(Long.MIN_VALUE, -1024L, 1024L);
        gcd_long_long_helper(-1024L, Long.MIN_VALUE, 1024L);
        gcd_long_long_helper(Long.MIN_VALUE, -1023L, 1L);
        gcd_long_long_helper(-1023L, Long.MIN_VALUE, 1L);

        gcd_long_long_fail_helper(0L, Long.MIN_VALUE);
        gcd_long_long_fail_helper(Long.MIN_VALUE, 0L);
        gcd_long_long_fail_helper(Long.MIN_VALUE, Long.MIN_VALUE);
    }

    private static void lcm_BigInteger_BigInteger_helper(
            @NotNull String x,
            @NotNull String y,
            @NotNull String output
    ) {
        aeq(lcm(Readers.readBigIntegerStrict(x).get(), Readers.readBigIntegerStrict(y).get()), output);
    }

    private static void lcm_BigInteger_BigInteger_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            lcm(Readers.readBigIntegerStrict(x).get(), Readers.readBigIntegerStrict(y).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testLcm_BigInteger_BigInteger() {
        lcm_BigInteger_BigInteger_helper("12", "15", "60");
        lcm_BigInteger_BigInteger_helper("35", "210", "210");
        lcm_BigInteger_BigInteger_helper("17", "20", "340");
        lcm_BigInteger_BigInteger_helper("1", "5", "5");

        lcm_BigInteger_BigInteger_fail_helper("-12", "15");
        lcm_BigInteger_BigInteger_fail_helper("12", "-15");
        lcm_BigInteger_BigInteger_fail_helper("-12", "-15");
        lcm_BigInteger_BigInteger_fail_helper("6", "0");
        lcm_BigInteger_BigInteger_fail_helper("0", "6");
        lcm_BigInteger_BigInteger_fail_helper("0", "0");
    }

    private static void gcd_List_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(gcd(readBigIntegerList(input)), output);
    }

    private static void gcd_List_BigInteger_fail_helper(@NotNull String input) {
        try {
            gcd(readBigIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testGcd_List_BigInteger() {
        gcd_List_BigInteger_helper("[]", "0");
        gcd_List_BigInteger_helper("[0]", "0");
        gcd_List_BigInteger_helper("[1]", "1");
        gcd_List_BigInteger_helper("[5]", "5");
        gcd_List_BigInteger_helper("[-5]", "5");
        gcd_List_BigInteger_helper("[35, 210]", "35");
        gcd_List_BigInteger_helper("[2, 4, 6]", "2");
        gcd_List_BigInteger_helper("[20, 40, 60]", "20");
        gcd_List_BigInteger_helper("[20, 40, -60]", "20");
        gcd_List_BigInteger_helper("[20, 40, 1]", "1");
        gcd_List_BigInteger_helper("[20, 40, 0]", "20");
        gcd_List_BigInteger_helper("[0, 0, 0]", "0");

        gcd_List_BigInteger_fail_helper("[1, 2, null]");
    }

    private static void lcm_List_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(lcm(readBigIntegerList(input)), output);
    }

    private static void lcm_List_BigInteger_fail_helper(@NotNull String input) {
        try {
            lcm(readBigIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException | ArithmeticException | NullPointerException ignored) {}
    }

    @Test
    public void testLcm_List_BigInteger() {
        lcm_List_BigInteger_helper("[1]", "1");
        lcm_List_BigInteger_helper("[5]", "5");
        lcm_List_BigInteger_helper("[35, 210]", "210");
        lcm_List_BigInteger_helper("[2, 4, 6]", "12");
        lcm_List_BigInteger_helper("[20, 40, 60]", "120");
        lcm_List_BigInteger_helper("[20, 40, 1]", "40");

        lcm_List_BigInteger_fail_helper("[]");
        lcm_List_BigInteger_fail_helper("[0]");
        lcm_List_BigInteger_fail_helper("[-5]");
        lcm_List_BigInteger_fail_helper("[0, 0, 0]");
        lcm_List_BigInteger_fail_helper("[1, 0, 2]");
        lcm_List_BigInteger_fail_helper("[1, -3, 2]");
        lcm_List_BigInteger_fail_helper("[1, 2, null]");
    }

    private static void factorial_int_helper(int input, @NotNull String output) {
        aeq(factorial(input), output);
    }

    private static void factorial_int_fail_helper(int input) {
        try {
            factorial(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFactorial_int() {
        factorial_int_helper(0, "1");
        factorial_int_helper(1, "1");
        factorial_int_helper(2, "2");
        factorial_int_helper(3, "6");
        factorial_int_helper(4, "24");
        factorial_int_helper(5, "120");
        factorial_int_helper(6, "720");
        factorial_int_helper(10, "3628800");
        factorial_int_helper(100,
                "933262154439441526816992388562667004907159682643816214685929638952175999932299156089414639761565182" +
                "86253697920827223758251185210916864000000000000000000000000");

        factorial_int_fail_helper(-1);
    }

    private static void factorial_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(factorial(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void factorial_BigInteger_fail_helper(@NotNull String input) {
        try {
            factorial(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFactorial_BigInteger() {
        factorial_BigInteger_helper("0", "1");
        factorial_BigInteger_helper("1", "1");
        factorial_BigInteger_helper("2", "2");
        factorial_BigInteger_helper("3", "6");
        factorial_BigInteger_helper("4", "24");
        factorial_BigInteger_helper("5", "120");
        factorial_BigInteger_helper("6", "720");
        factorial_BigInteger_helper("10", "3628800");
        factorial_BigInteger_helper("100",
                "933262154439441526816992388562667004907159682643816214685929638952175999932299156089414639761565182" +
                "86253697920827223758251185210916864000000000000000000000000");

        factorial_BigInteger_fail_helper("-1");
    }

    private static void subfactorial_int_helper(int input, @NotNull String output) {
        aeq(subfactorial(input), output);
    }

    private static void subfactorial_int_fail_helper(int input) {
        try {
            subfactorial(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubfactorial_int() {
        subfactorial_int_helper(0, "1");
        subfactorial_int_helper(1, "0");
        subfactorial_int_helper(2, "1");
        subfactorial_int_helper(3, "2");
        subfactorial_int_helper(4, "9");
        subfactorial_int_helper(5, "44");
        subfactorial_int_helper(6, "265");
        subfactorial_int_helper(10, "1334961");
        subfactorial_int_helper(100,
                "343327959841638047651959775267761420323657838053757849835434002826851807933276324327913964298509889" +
                "90237345920155783984828001486412574060553756854137069878601");

        subfactorial_int_fail_helper(-1);
    }

    private static void subfactorial_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(subfactorial(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void subfactorial_BigInteger_fail_helper(@NotNull String input) {
        try {
            subfactorial(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubfactorial_BigInteger() {
        subfactorial_BigInteger_helper("0", "1");
        subfactorial_BigInteger_helper("1", "0");
        subfactorial_BigInteger_helper("2", "1");
        subfactorial_BigInteger_helper("3", "2");
        subfactorial_BigInteger_helper("4", "9");
        subfactorial_BigInteger_helper("5", "44");
        subfactorial_BigInteger_helper("6", "265");
        subfactorial_BigInteger_helper("10", "1334961");
        subfactorial_BigInteger_helper("100",
                "343327959841638047651959775267761420323657838053757849835434002826851807933276324327913964298509889" +
                "90237345920155783984828001486412574060553756854137069878601");

        subfactorial_BigInteger_fail_helper("-1");
    }

    private static void fallingFactorial_helper(@NotNull String x, int n, @NotNull String output) {
        aeq(fallingFactorial(Readers.readBigIntegerStrict(x).get(), n), output);
    }

    private static void fallingFactorial_fail_helper(@NotNull String x, int n) {
        try {
            fallingFactorial(Readers.readBigIntegerStrict(x).get(), n);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFallingFactorial() {
        fallingFactorial_helper("0", 0, "1");
        fallingFactorial_helper("0", 1, "0");
        fallingFactorial_helper("0", 2, "0");
        fallingFactorial_helper("1", 0, "1");
        fallingFactorial_helper("1", 1, "1");
        fallingFactorial_helper("1", 2, "0");
        fallingFactorial_helper("10", 0, "1");
        fallingFactorial_helper("10", 1, "10");
        fallingFactorial_helper("10", 2, "90");
        fallingFactorial_helper("100", 20, "1303995018204712451095685346159820800000");

        fallingFactorial_fail_helper("-1", 0);
        fallingFactorial_fail_helper("0", -1);
    }

    private static void numberOfArrangementsOfASet_int_helper(int input, @NotNull String output) {
        aeq(numberOfArrangementsOfASet(input), output);
    }

    private static void numberOfArrangementsOfASet_int_fail_helper(int input) {
        try {
            numberOfArrangementsOfASet(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNumberOfArrangementsOfASet_int() {
        numberOfArrangementsOfASet_int_helper(0, "1");
        numberOfArrangementsOfASet_int_helper(1, "2");
        numberOfArrangementsOfASet_int_helper(2, "5");
        numberOfArrangementsOfASet_int_helper(3, "16");
        numberOfArrangementsOfASet_int_helper(4, "65");
        numberOfArrangementsOfASet_int_helper(5, "326");
        numberOfArrangementsOfASet_int_helper(6, "1957");
        numberOfArrangementsOfASet_int_helper(10, "9864101");
        numberOfArrangementsOfASet_int_helper(100,
                "253686955560127297415270748212280220445147578566298142232775185987449253908386446518940485425152049" +
                "793267407732328003493609513499849694176709764490323163992001");

        numberOfArrangementsOfASet_int_fail_helper(-1);
    }

    private static void numberOfArrangementsOfASet_int_int_helper(int minSize, int n, @NotNull String output) {
        aeq(numberOfArrangementsOfASet(minSize, n), output);
    }

    private static void numberOfArrangementsOfASet_int_int_fail_helper(int minSize, int n) {
        try {
            numberOfArrangementsOfASet(minSize, n);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNumberOfArrangementsOfASet_int_int() {
        numberOfArrangementsOfASet_int_int_helper(0, 0, "1");
        numberOfArrangementsOfASet_int_int_helper(1, 0, "0");
        numberOfArrangementsOfASet_int_int_helper(2, 0, "0");
        numberOfArrangementsOfASet_int_int_helper(0, 1, "2");
        numberOfArrangementsOfASet_int_int_helper(1, 1, "1");
        numberOfArrangementsOfASet_int_int_helper(2, 1, "0");
        numberOfArrangementsOfASet_int_int_helper(0, 10, "9864101");
        numberOfArrangementsOfASet_int_int_helper(1, 10, "9864100");
        numberOfArrangementsOfASet_int_int_helper(2, 10, "9864090");
        numberOfArrangementsOfASet_int_int_helper(10, 10, "3628800");
        numberOfArrangementsOfASet_int_int_helper(11, 10, "0");
        numberOfArrangementsOfASet_int_int_helper(20, 100,
                "253686955560127297415270748212280220445147578566298142232775185987449253908386446518940485425152049" +
                "793267407732328003493593216076399867112102621246975180800000");

        numberOfArrangementsOfASet_int_int_fail_helper(0, -1);
        numberOfArrangementsOfASet_int_int_fail_helper(-1, 0);
    }

    private static void binomialCoefficient_helper(@NotNull String n, int k, @NotNull String output) {
        aeq(binomialCoefficient(Readers.readBigIntegerStrict(n).get(), k), output);
    }

    private static void binomialCoefficient_fail_helper(@NotNull String n, int k) {
        try {
            binomialCoefficient(Readers.readBigIntegerStrict(n).get(), k);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBinomialCoefficient() {
        binomialCoefficient_helper("0", 0, "1");
        binomialCoefficient_helper("0", 1, "0");
        binomialCoefficient_helper("0", 2, "0");
        binomialCoefficient_helper("1", 0, "1");
        binomialCoefficient_helper("1", 1, "1");
        binomialCoefficient_helper("1", 2, "0");
        binomialCoefficient_helper("10", 0, "1");
        binomialCoefficient_helper("10", 1, "10");
        binomialCoefficient_helper("10", 2, "45");
        binomialCoefficient_helper("10", 3, "120");
        binomialCoefficient_helper("10", 4, "210");
        binomialCoefficient_helper("10", 5, "252");
        binomialCoefficient_helper("100", 20, "535983370403809682970");

        binomialCoefficient_fail_helper("-1", 0);
        binomialCoefficient_fail_helper("0", -1);
    }

    private static void multisetCoefficient_helper(@NotNull String n, int k, @NotNull String output) {
        aeq(multisetCoefficient(Readers.readBigIntegerStrict(n).get(), k), output);
    }

    private static void multisetCoefficient_fail_helper(@NotNull String n, int k) {
        try {
            multisetCoefficient(Readers.readBigIntegerStrict(n).get(), k);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testMultisetCoefficient() {
        multisetCoefficient_helper("0", 0, "1");
        multisetCoefficient_helper("0", 1, "0");
        multisetCoefficient_helper("0", 2, "0");
        multisetCoefficient_helper("1", 0, "1");
        multisetCoefficient_helper("1", 1, "1");
        multisetCoefficient_helper("1", 2, "1");
        multisetCoefficient_helper("10", 0, "1");
        multisetCoefficient_helper("10", 1, "10");
        multisetCoefficient_helper("10", 2, "55");
        multisetCoefficient_helper("10", 3, "220");
        multisetCoefficient_helper("10", 4, "715");
        multisetCoefficient_helper("10", 5, "2002");
        multisetCoefficient_helper("100", 20, "24551856075980529765105");

        multisetCoefficient_fail_helper("-1", 0);
        multisetCoefficient_fail_helper("0", -1);
    }

    private static void subsetCount_helper(int minSize, int n, @NotNull String output) {
        aeq(subsetCount(minSize, n), output);
    }

    private static void subsetCount_fail_helper(int minSize, int n) {
        try {
            subsetCount(minSize, n);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubsetCount() {
        subsetCount_helper(0, 0, "1");
        subsetCount_helper(1, 0, "0");
        subsetCount_helper(2, 0, "0");
        subsetCount_helper(0, 1, "2");
        subsetCount_helper(1, 1, "1");
        subsetCount_helper(2, 1, "0");
        subsetCount_helper(0, 10, "1024");
        subsetCount_helper(1, 10, "1023");
        subsetCount_helper(2, 10, "1013");
        subsetCount_helper(20, 100, "1267650600056921474829227703650");

        subsetCount_fail_helper(-1, 0);
        subsetCount_fail_helper(0, -1);
    }

    private static void permutationCount_helper(@NotNull String input, @NotNull String output) {
        aeq(permutationCount(readBigIntegerList(input)), output);
    }

    @Test
    public void testPermutationCount() {
        permutationCount_helper("[]", "1");
        permutationCount_helper("[1]", "1");
        permutationCount_helper("[1, 2, 3]", "6");
        permutationCount_helper("[1, 1, 1]", "1");
        permutationCount_helper("[1, 2, 3, 4, 5, 6, 7, 8, 9]", "362880");
        permutationCount_helper("[0, 1, 2, 2, 1, 2, 2, 1, 3, 3, 1]", "34650");
    }

    private static void reversePermutationsSign_helper(int i, boolean output) {
        aeq(reversePermutationSign(i), output);
    }

    private static void reversePermutationSign_fail_helper(int i) {
        try {
            reversePermutationSign(i);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testReversePermutationSign() {
        reversePermutationsSign_helper(0, true);
        reversePermutationsSign_helper(1, true);
        reversePermutationsSign_helper(2, false);
        reversePermutationsSign_helper(3, false);
        reversePermutationsSign_helper(4, true);
        reversePermutationsSign_helper(5, true);

        reversePermutationSign_fail_helper(-1);
    }

    private static void fastGrowingCeilingInverse_helper(
            @NotNull Function<Integer, BigInteger> f,
            int min,
            int max,
            @NotNull String y,
            int output
    ) {
        aeq(fastGrowingCeilingInverse(f, min, max, Readers.readBigIntegerStrict(y).get()), output);
    }

    private static void fastGrowingCeilingInverse_fail_helper(
            @NotNull Function<Integer, BigInteger> f,
            int min,
            int max,
            @NotNull String y
    ) {
        try {
            fastGrowingCeilingInverse(f, min, max, Readers.readBigIntegerStrict(y).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFastGrowingCeilingInverse() {
        //noinspection Convert2MethodRef
        fastGrowingCeilingInverse_helper(i -> BigInteger.valueOf(i), 0, 10, "3", 3);
        fastGrowingCeilingInverse_helper(MathUtils::factorial, 0, 20, "1000000", 10);
        fastGrowingCeilingInverse_helper(i -> BigInteger.valueOf(i).pow(2), 0, 10, "50", 8);

        fastGrowingCeilingInverse_fail_helper(i -> { throw new IllegalArgumentException(); }, 0, 1, "1");
        fastGrowingCeilingInverse_fail_helper(i -> null, 0, 1, "1");
        fastGrowingCeilingInverse_fail_helper(i -> BigInteger.valueOf(i).pow(2), -2, 0, "10");
        fastGrowingCeilingInverse_fail_helper(MathUtils::factorial, 0, 10, "1000000000");
    }

    private static void ceilingLog_helper(@NotNull String base, @NotNull String x, @NotNull String output) {
        aeq(ceilingLog(Readers.readBigIntegerStrict(base).get(), Readers.readBigIntegerStrict(x).get()), output);
    }

    private static void ceilingLog_fail_helper(@NotNull String base, @NotNull String x) {
        try {
            ceilingLog(Readers.readBigIntegerStrict(base).get(), Readers.readBigIntegerStrict(x).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testCeilingLog() {
        ceilingLog_helper("2", "1", "0");
        ceilingLog_helper("2", "2", "1");
        ceilingLog_helper("2", "3", "2");
        ceilingLog_helper("2", "4", "2");
        ceilingLog_helper("2", "5", "3");
        ceilingLog_helper("2", "6", "3");
        ceilingLog_helper("2", "7", "3");
        ceilingLog_helper("2", "8", "3");

        ceilingLog_helper("3", "1", "0");
        ceilingLog_helper("3", "2", "1");
        ceilingLog_helper("3", "3", "1");
        ceilingLog_helper("3", "4", "2");
        ceilingLog_helper("3", "8", "2");
        ceilingLog_helper("3", "9", "2");
        ceilingLog_helper("3", "10", "3");

        ceilingLog_helper("100", "1", "0");
        ceilingLog_helper("100", "9999", "2");
        ceilingLog_helper("100", "10000", "2");
        ceilingLog_helper("100", "10001", "3");

        ceilingLog_fail_helper("1", "1");
        ceilingLog_fail_helper("0", "1");
        ceilingLog_fail_helper("-1", "1");
        ceilingLog_fail_helper("2", "0");
        ceilingLog_fail_helper("2", "-1");
    }

    private static void ceilingInverse_helper(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull String min,
            @NotNull String max,
            @NotNull String y,
            int output
    ) {
        aeq(
                ceilingInverse(
                        f,
                        Readers.readBigIntegerStrict(min).get(),
                        Readers.readBigIntegerStrict(max).get(),
                        Readers.readBigIntegerStrict(y).get()
                ),
                output
        );
    }

    private static void ceilingInverse_fail_helper(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull String min,
            @NotNull String max,
            @NotNull String y
    ) {
        try {
            ceilingInverse(
                    f,
                    Readers.readBigIntegerStrict(min).get(),
                    Readers.readBigIntegerStrict(max).get(),
                    Readers.readBigIntegerStrict(y).get()
            );
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCeilingInverse() {
        //noinspection Convert2MethodRef
        ceilingInverse_helper(Function.identity(), "0", "10", "3", 3);
        ceilingInverse_helper(MathUtils::factorial, "0", "20", "1000000", 10);
        ceilingInverse_helper(i -> i.pow(2), "0", "10", "50", 8);

        ceilingInverse_fail_helper(i -> { throw new IllegalArgumentException(); }, "0", "1", "1");
        ceilingInverse_fail_helper(i -> null, "0", "1", "1");
        ceilingInverse_fail_helper(i -> i.pow(2), "-10", "0", "10");
        ceilingInverse_fail_helper(MathUtils::factorial, "0", "10", "1000000000");
    }

    private static void ceilingRoot_helper(int r, @NotNull String x, @NotNull String output) {
        aeq(ceilingRoot(r, Readers.readBigIntegerStrict(x).get()), output);
    }

    private static void ceilingRoot_fail_helper(int r, @NotNull String x) {
        try {
            ceilingRoot(r, Readers.readBigIntegerStrict(x).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testCeilingRoot() {
        ceilingRoot_helper(1, "0", "0");
        ceilingRoot_helper(1, "1", "1");
        ceilingRoot_helper(1, "2", "2");
        ceilingRoot_helper(1, "3", "3");

        ceilingRoot_helper(2, "0", "0");
        ceilingRoot_helper(2, "1", "1");
        ceilingRoot_helper(2, "2", "2");
        ceilingRoot_helper(2, "3", "2");
        ceilingRoot_helper(2, "4", "2");
        ceilingRoot_helper(2, "8", "3");
        ceilingRoot_helper(2, "9", "3");
        ceilingRoot_helper(2, "10", "4");
        ceilingRoot_helper(2, "99", "10");
        ceilingRoot_helper(2, "100", "10");
        ceilingRoot_helper(2, "101", "11");

        ceilingRoot_helper(3, "0", "0");
        ceilingRoot_helper(3, "1", "1");
        ceilingRoot_helper(3, "2", "2");
        ceilingRoot_helper(3, "3", "2");
        ceilingRoot_helper(3, "4", "2");
        ceilingRoot_helper(3, "7", "2");
        ceilingRoot_helper(3, "8", "2");
        ceilingRoot_helper(3, "9", "3");
        ceilingRoot_helper(3, "999", "10");
        ceilingRoot_helper(3, "1000", "10");
        ceilingRoot_helper(3, "1001", "11");

        ceilingRoot_helper(1000, "0", "0");
        ceilingRoot_helper(1000, "1", "1");
        ceilingRoot_helper(1000, "2", "2");
        ceilingRoot_helper(1000, "3", "2");
        ceilingRoot_helper(1000, "4", "2");
        ceilingRoot_helper(1000, "1000000000000000000", "2");

        ceilingRoot_fail_helper(0, "2");
        ceilingRoot_fail_helper(-1, "2");
        ceilingRoot_fail_helper(2, "-1");
    }

    private static void smallestPrimeFactor_int_helper(int input, int output) {
        aeq(smallestPrimeFactor(input), output);
    }

    private static void smallestPrimeFactor_int_fail_helper(int input) {
        try {
            smallestPrimeFactor(input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testSmallestPrimeFactor_int() {
        smallestPrimeFactor_int_helper(2, 2);
        smallestPrimeFactor_int_helper(3, 3);
        smallestPrimeFactor_int_helper(4, 2);
        smallestPrimeFactor_int_helper(5, 5);
        smallestPrimeFactor_int_helper(6, 2);
        smallestPrimeFactor_int_helper(7, 7);
        smallestPrimeFactor_int_helper(8, 2);
        smallestPrimeFactor_int_helper(9, 3);
        smallestPrimeFactor_int_helper(10, 2);
        smallestPrimeFactor_int_helper(1807, 13);
        smallestPrimeFactor_int_helper(6221671, 6221671);
        smallestPrimeFactor_int_helper(65533, 13);
        smallestPrimeFactor_int_helper(2147483647, 2147483647);
        smallestPrimeFactor_int_helper(2147483643, 3);
        smallestPrimeFactor_int_helper(2147483641, 2699);

        smallestPrimeFactor_int_fail_helper(1);
        smallestPrimeFactor_int_fail_helper(0);
        smallestPrimeFactor_int_fail_helper(-1);
    }

    private static void smallestPrimeFactor_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(smallestPrimeFactor(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void smallestPrimeFactor_BigInteger_fail_helper(@NotNull String input) {
        try {
            smallestPrimeFactor(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testSmallestPrimeFactor_BigInteger() {
        smallestPrimeFactor_BigInteger_helper("2", "2");
        smallestPrimeFactor_BigInteger_helper("3", "3");
        smallestPrimeFactor_BigInteger_helper("4", "2");
        smallestPrimeFactor_BigInteger_helper("5", "5");
        smallestPrimeFactor_BigInteger_helper("6", "2");
        smallestPrimeFactor_BigInteger_helper("7", "7");
        smallestPrimeFactor_BigInteger_helper("8", "2");
        smallestPrimeFactor_BigInteger_helper("9", "3");
        smallestPrimeFactor_BigInteger_helper("10", "2");
        smallestPrimeFactor_BigInteger_helper("1807", "13");
        smallestPrimeFactor_BigInteger_helper("6221671", "6221671");
        smallestPrimeFactor_BigInteger_helper("65533", "13");
        smallestPrimeFactor_BigInteger_helper("2147483647", "2147483647");
        smallestPrimeFactor_BigInteger_helper("2147483643", "3");
        smallestPrimeFactor_BigInteger_helper("2147483641", "2699");
        smallestPrimeFactor_BigInteger_helper("2147483649", "3");
        smallestPrimeFactor_BigInteger_helper("2147483659", "2147483659");
        smallestPrimeFactor_BigInteger_helper("1000000000039", "1000000000039");
        smallestPrimeFactor_BigInteger_helper("1000000000000037", "1000000000000037");
        smallestPrimeFactor_BigInteger_helper("1000000000000039", "17");

        smallestPrimeFactor_BigInteger_fail_helper("1");
        smallestPrimeFactor_BigInteger_fail_helper("0");
        smallestPrimeFactor_BigInteger_fail_helper("-1");
    }

    private static void isPrime_int_helper(int input, boolean output) {
        aeq(isPrime(input), output);
    }

    private static void isPrime_int_fail_helper(int input) {
        try {
            isPrime(input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testIsPrime_int() {
        isPrime_int_helper(1, false);
        isPrime_int_helper(2, true);
        isPrime_int_helper(3, true);
        isPrime_int_helper(4, false);
        isPrime_int_helper(5, true);
        isPrime_int_helper(6, false);
        isPrime_int_helper(7, true);
        isPrime_int_helper(8, false);
        isPrime_int_helper(9, false);
        isPrime_int_helper(10, false);
        isPrime_int_helper(1807, false);
        isPrime_int_helper(6221671, true);
        isPrime_int_helper(65533, false);
        isPrime_int_helper(2147483647, true);
        isPrime_int_helper(2147483643, false);
        isPrime_int_helper(2147483641, false);

        isPrime_int_fail_helper(0);
        isPrime_int_fail_helper(-1);
    }

    private static void isPrime_BigInteger_helper(@NotNull String input, boolean output) {
        aeq(isPrime(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void isPrime_BigInteger_fail_helper(@NotNull String input) {
        try {
            isPrime(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testIsPrime_BigInteger() {
        isPrime_BigInteger_helper("1", false);
        isPrime_BigInteger_helper("2", true);
        isPrime_BigInteger_helper("3", true);
        isPrime_BigInteger_helper("4", false);
        isPrime_BigInteger_helper("5", true);
        isPrime_BigInteger_helper("6", false);
        isPrime_BigInteger_helper("7", true);
        isPrime_BigInteger_helper("8", false);
        isPrime_BigInteger_helper("9", false);
        isPrime_BigInteger_helper("10", false);
        isPrime_BigInteger_helper("1807", false);
        isPrime_BigInteger_helper("6221671", true);
        isPrime_BigInteger_helper("65533", false);
        isPrime_BigInteger_helper("2147483647", true);
        isPrime_BigInteger_helper("2147483643", false);
        isPrime_BigInteger_helper("2147483641", false);
        isPrime_BigInteger_helper("2147483649", false);
        isPrime_BigInteger_helper("2147483659", true);
        isPrime_BigInteger_helper("1000000000039", true);
        isPrime_BigInteger_helper("1000000000000037", true);
        isPrime_BigInteger_helper("1000000000000039", false);

        isPrime_BigInteger_fail_helper("0");
        isPrime_BigInteger_fail_helper("-1");
    }

    private static void primeFactors_int_helper(int input, @NotNull String output) {
        Testing.aeqit(primeFactors(input), output);
    }

    private static void primeFactors_int_fail_helper(int input) {
        try {
            toList(primeFactors(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPrimeFactors_int() {
        primeFactors_int_helper(1, "[]");
        primeFactors_int_helper(2, "[2]");
        primeFactors_int_helper(3, "[3]");
        primeFactors_int_helper(4, "[2, 2]");
        primeFactors_int_helper(5, "[5]");
        primeFactors_int_helper(6, "[2, 3]");
        primeFactors_int_helper(7, "[7]");
        primeFactors_int_helper(8, "[2, 2, 2]");
        primeFactors_int_helper(9, "[3, 3]");
        primeFactors_int_helper(10, "[2, 5]");
        primeFactors_int_helper(1807, "[13, 139]");
        primeFactors_int_helper(6221671, "[6221671]");
        primeFactors_int_helper(65533, "[13, 71, 71]");
        primeFactors_int_helper(2147483647, "[2147483647]");
        primeFactors_int_helper(2147483643, "[3, 715827881]");
        primeFactors_int_helper(2147483641, "[2699, 795659]");

        primeFactors_int_fail_helper(0);
        primeFactors_int_fail_helper(-1);
    }

    private static void primeFactors_BigInteger_helper(@NotNull String input, @NotNull String output) {
        Testing.aeqit(primeFactors(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void primeFactors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(primeFactors(Readers.readBigIntegerStrict(input).get()));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPrimeFactors_BigInteger() {
        primeFactors_BigInteger_helper("1", "[]");
        primeFactors_BigInteger_helper("2", "[2]");
        primeFactors_BigInteger_helper("3", "[3]");
        primeFactors_BigInteger_helper("4", "[2, 2]");
        primeFactors_BigInteger_helper("5", "[5]");
        primeFactors_BigInteger_helper("6", "[2, 3]");
        primeFactors_BigInteger_helper("7", "[7]");
        primeFactors_BigInteger_helper("8", "[2, 2, 2]");
        primeFactors_BigInteger_helper("9", "[3, 3]");
        primeFactors_BigInteger_helper("10", "[2, 5]");
        primeFactors_BigInteger_helper("1807", "[13, 139]");
        primeFactors_BigInteger_helper("6221671", "[6221671]");
        primeFactors_BigInteger_helper("65533", "[13, 71, 71]");
        primeFactors_BigInteger_helper("2147483647", "[2147483647]");
        primeFactors_BigInteger_helper("2147483643", "[3, 715827881]");
        primeFactors_BigInteger_helper("2147483641", "[2699, 795659]");
        primeFactors_BigInteger_helper("2147483649", "[3, 715827883]");
        primeFactors_BigInteger_helper("2147483659", "[2147483659]");
        primeFactors_BigInteger_helper("1000000000039", "[1000000000039]");
        primeFactors_BigInteger_helper("1000000000000037", "[1000000000000037]");
        primeFactors_BigInteger_helper("1000000000000039", "[17, 29, 686669, 2953967]");

        primeFactors_BigInteger_fail_helper("0");
        primeFactors_BigInteger_fail_helper("-1");
    }

    private static void compactPrimeFactors_int_helper(int input, @NotNull String output) {
        Testing.aeqit(compactPrimeFactors(input), output);
    }

    private static void compactPrimeFactors_int_fail_helper(int input) {
        try {
            toList(compactPrimeFactors(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCompactPrimeFactors_int() {
        compactPrimeFactors_int_helper(1, "[]");
        compactPrimeFactors_int_helper(2, "[(2, 1)]");
        compactPrimeFactors_int_helper(3, "[(3, 1)]");
        compactPrimeFactors_int_helper(4, "[(2, 2)]");
        compactPrimeFactors_int_helper(5, "[(5, 1)]");
        compactPrimeFactors_int_helper(6, "[(2, 1), (3, 1)]");
        compactPrimeFactors_int_helper(7, "[(7, 1)]");
        compactPrimeFactors_int_helper(8, "[(2, 3)]");
        compactPrimeFactors_int_helper(9, "[(3, 2)]");
        compactPrimeFactors_int_helper(10, "[(2, 1), (5, 1)]");
        compactPrimeFactors_int_helper(1807, "[(13, 1), (139, 1)]");
        compactPrimeFactors_int_helper(6221671, "[(6221671, 1)]");
        compactPrimeFactors_int_helper(65533, "[(13, 1), (71, 2)]");
        compactPrimeFactors_int_helper(2147483647, "[(2147483647, 1)]");
        compactPrimeFactors_int_helper(2147483643, "[(3, 1), (715827881, 1)]");
        compactPrimeFactors_int_helper(2147483641, "[(2699, 1), (795659, 1)]");

        compactPrimeFactors_int_fail_helper(0);
        compactPrimeFactors_int_fail_helper(-1);
    }

    private static void compactPrimeFactors_BigInteger_helper(@NotNull String input, @NotNull String output) {
        Testing.aeqit(compactPrimeFactors(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void compactPrimeFactors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(compactPrimeFactors(Readers.readBigIntegerStrict(input).get()));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCompactPrimeFactors_BigInteger() {
        compactPrimeFactors_BigInteger_helper("1", "[]");
        compactPrimeFactors_BigInteger_helper("2", "[(2, 1)]");
        compactPrimeFactors_BigInteger_helper("3", "[(3, 1)]");
        compactPrimeFactors_BigInteger_helper("4", "[(2, 2)]");
        compactPrimeFactors_BigInteger_helper("5", "[(5, 1)]");
        compactPrimeFactors_BigInteger_helper("6", "[(2, 1), (3, 1)]");
        compactPrimeFactors_BigInteger_helper("7", "[(7, 1)]");
        compactPrimeFactors_BigInteger_helper("8", "[(2, 3)]");
        compactPrimeFactors_BigInteger_helper("9", "[(3, 2)]");
        compactPrimeFactors_BigInteger_helper("10", "[(2, 1), (5, 1)]");
        compactPrimeFactors_BigInteger_helper("1807", "[(13, 1), (139, 1)]");
        compactPrimeFactors_BigInteger_helper("6221671", "[(6221671, 1)]");
        compactPrimeFactors_BigInteger_helper("65533", "[(13, 1), (71, 2)]");
        compactPrimeFactors_BigInteger_helper("2147483647", "[(2147483647, 1)]");
        compactPrimeFactors_BigInteger_helper("2147483643", "[(3, 1), (715827881, 1)]");
        compactPrimeFactors_BigInteger_helper("2147483641", "[(2699, 1), (795659, 1)]");
        compactPrimeFactors_BigInteger_helper("2147483649", "[(3, 1), (715827883, 1)]");
        compactPrimeFactors_BigInteger_helper("2147483659", "[(2147483659, 1)]");
        compactPrimeFactors_BigInteger_helper("1000000000039", "[(1000000000039, 1)]");
        compactPrimeFactors_BigInteger_helper("1000000000000037", "[(1000000000000037, 1)]");
        compactPrimeFactors_BigInteger_helper("1000000000000039", "[(17, 1), (29, 1), (686669, 1), (2953967, 1)]");

        compactPrimeFactors_BigInteger_fail_helper("0");
        compactPrimeFactors_BigInteger_fail_helper("-1");
    }

    private static void factors_int_helper(int input, @NotNull String output) {
        Testing.aeqit(factors(input), output);
    }

    private static void factors_int_fail_helper(int input) {
        try {
            toList(factors(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFactors_int() {
        factors_int_helper(1, "[1]");
        factors_int_helper(2, "[1, 2]");
        factors_int_helper(3, "[1, 3]");
        factors_int_helper(4, "[1, 2, 4]");
        factors_int_helper(5, "[1, 5]");
        factors_int_helper(6, "[1, 2, 3, 6]");
        factors_int_helper(7, "[1, 7]");
        factors_int_helper(8, "[1, 2, 4, 8]");
        factors_int_helper(9, "[1, 3, 9]");
        factors_int_helper(10, "[1, 2, 5, 10]");
        factors_int_helper(1807, "[1, 13, 139, 1807]");
        factors_int_helper(6221671, "[1, 6221671]");
        factors_int_helper(65533, "[1, 13, 71, 923, 5041, 65533]");
        factors_int_helper(2147483647, "[1, 2147483647]");
        factors_int_helper(2147483643, "[1, 3, 715827881, 2147483643]");
        factors_int_helper(2147483641, "[1, 2699, 795659, 2147483641]");

        factors_int_fail_helper(0);
        factors_int_fail_helper(-1);
    }

    private static void factors_BigInteger_helper(@NotNull String input, @NotNull String output) {
        Testing.aeqit(factors(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void factors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(factors(Readers.readBigIntegerStrict(input).get()));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFactors_BigInteger() {
        factors_BigInteger_helper("1", "[1]");
        factors_BigInteger_helper("2", "[1, 2]");
        factors_BigInteger_helper("3", "[1, 3]");
        factors_BigInteger_helper("4", "[1, 2, 4]");
        factors_BigInteger_helper("5", "[1, 5]");
        factors_BigInteger_helper("6", "[1, 2, 3, 6]");
        factors_BigInteger_helper("7", "[1, 7]");
        factors_BigInteger_helper("8", "[1, 2, 4, 8]");
        factors_BigInteger_helper("9", "[1, 3, 9]");
        factors_BigInteger_helper("10", "[1, 2, 5, 10]");
        factors_BigInteger_helper("1807", "[1, 13, 139, 1807]");
        factors_BigInteger_helper("6221671", "[1, 6221671]");
        factors_BigInteger_helper("65533", "[1, 13, 71, 923, 5041, 65533]");
        factors_BigInteger_helper("2147483647", "[1, 2147483647]");
        factors_BigInteger_helper("2147483643", "[1, 3, 715827881, 2147483643]");
        factors_BigInteger_helper("2147483641", "[1, 2699, 795659, 2147483641]");
        factors_BigInteger_helper("2147483649", "[1, 3, 715827883, 2147483649]");
        factors_BigInteger_helper("2147483659", "[1, 2147483659]");
        factors_BigInteger_helper("1000000000039", "[1, 1000000000039]");
        factors_BigInteger_helper("1000000000000037", "[1, 1000000000000037]");
        factors_BigInteger_helper("1000000000000039",
                "[1, 17, 29, 493, 686669, 2953967, 11673373, 19913401, 50217439, 85665043, 338527817, 1456305731," +
                " 2028397565923, 34482758620691, 58823529411767, 1000000000000039]");

        factors_BigInteger_fail_helper("0");
        factors_BigInteger_fail_helper("-1");
    }

    private static void largestPerfectPowerFactor_int_int_helper(int p, int n, @NotNull String output) {
        aeq(largestPerfectPowerFactor(p, n), output);
    }

    private static void largestPerfectPowerFactor_int_int_fail_helper(int p, int n) {
        try {
            largestPerfectPowerFactor(p, n);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLargestPerfectPowerFactor_int_int() {
        largestPerfectPowerFactor_int_int_helper(1, 1, "1");
        largestPerfectPowerFactor_int_int_helper(1, 2, "2");
        largestPerfectPowerFactor_int_int_helper(1, 3, "3");
        largestPerfectPowerFactor_int_int_helper(1, 4, "4");
        largestPerfectPowerFactor_int_int_helper(1, 100, "100");

        largestPerfectPowerFactor_int_int_helper(2, 1, "1");
        largestPerfectPowerFactor_int_int_helper(2, 2, "1");
        largestPerfectPowerFactor_int_int_helper(2, 3, "1");
        largestPerfectPowerFactor_int_int_helper(2, 4, "2");
        largestPerfectPowerFactor_int_int_helper(2, 12, "2");
        largestPerfectPowerFactor_int_int_helper(2, 75, "5");
        largestPerfectPowerFactor_int_int_helper(2, 100, "10");

        largestPerfectPowerFactor_int_int_helper(10, 1024, "2");
        largestPerfectPowerFactor_int_int_helper(10, 10240, "2");

        largestPerfectPowerFactor_int_int_fail_helper(0, 1);
        largestPerfectPowerFactor_int_int_fail_helper(-1, 1);
        largestPerfectPowerFactor_int_int_fail_helper(1, 0);
        largestPerfectPowerFactor_int_int_fail_helper(1, -1);
    }

    private static void largestPerfectPowerFactor_int_BigInteger_helper(
            int p,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(largestPerfectPowerFactor(p, Readers.readBigIntegerStrict(n).get()), output);
    }

    private static void largestPerfectPowerFactor_int_BigInteger_fail_helper(int p, @NotNull String n) {
        try {
            largestPerfectPowerFactor(p, Readers.readBigIntegerStrict(n).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLargestPerfectPowerFactor_int_BigInteger() {
        largestPerfectPowerFactor_int_BigInteger_helper(1, "1", "1");
        largestPerfectPowerFactor_int_BigInteger_helper(1, "2", "2");
        largestPerfectPowerFactor_int_BigInteger_helper(1, "3", "3");
        largestPerfectPowerFactor_int_BigInteger_helper(1, "4", "4");
        largestPerfectPowerFactor_int_BigInteger_helper(1, "100", "100");

        largestPerfectPowerFactor_int_BigInteger_helper(2, "1", "1");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "2", "1");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "3", "1");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "4", "2");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "12", "2");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "75", "5");
        largestPerfectPowerFactor_int_BigInteger_helper(2, "100", "10");

        largestPerfectPowerFactor_int_BigInteger_helper(10, "1024", "2");
        largestPerfectPowerFactor_int_BigInteger_helper(10, "10240", "2");
        largestPerfectPowerFactor_int_BigInteger_helper(10, "10000000000", "10");

        largestPerfectPowerFactor_int_BigInteger_fail_helper(0, "1");
        largestPerfectPowerFactor_int_BigInteger_fail_helper(-1, "1");
        largestPerfectPowerFactor_int_BigInteger_fail_helper(1, "0");
        largestPerfectPowerFactor_int_BigInteger_fail_helper(1, "-1");
    }

    private static void expressAsPower_helper(@NotNull String input, @NotNull String output) {
        aeq(expressAsPower(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void expressAsPower_fail_helper(@NotNull String input) {
        try {
            expressAsPower(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testExpressAsPower() {
        expressAsPower_helper("2", "(2, 1)");
        expressAsPower_helper("3", "(3, 1)");
        expressAsPower_helper("4", "(2, 2)");
        expressAsPower_helper("5", "(5, 1)");
        expressAsPower_helper("6", "(6, 1)");
        expressAsPower_helper("7", "(7, 1)");
        expressAsPower_helper("8", "(2, 3)");
        expressAsPower_helper("9", "(3, 2)");
        expressAsPower_helper("10", "(10, 1)");
        expressAsPower_helper("64", "(2, 6)");
        expressAsPower_helper("1000000", "(10, 6)");
        expressAsPower_helper("1000000000039", "(1000000000039, 1)");
        expressAsPower_helper(
                "100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                "00",
                "(10, 100)"
        );

        expressAsPower_fail_helper("1");
        expressAsPower_fail_helper("0");
        expressAsPower_fail_helper("-1");
    }

    private static void root_helper(@NotNull String n, int r, @NotNull String output) {
        aeq(root(Readers.readBigIntegerStrict(n).get(), r), output);
    }

    private static void root_fail_helper(@NotNull String n, int r) {
        try {
            root(Readers.readBigIntegerStrict(n).get(), r);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRoot() {
        root_helper("0", 1, "Optional[0]");
        root_helper("0", 2, "Optional[0]");
        root_helper("0", 3, "Optional[0]");
        root_helper("0", 4, "Optional[0]");

        root_helper("1", 1, "Optional[1]");
        root_helper("1", 2, "Optional[1]");
        root_helper("1", 3, "Optional[1]");
        root_helper("1", 4, "Optional[1]");

        root_helper("2", 1, "Optional[2]");
        root_helper("2", 2, "Optional.empty");
        root_helper("2", 3, "Optional.empty");
        root_helper("2", 4, "Optional.empty");

        root_helper("-1", 1, "Optional[-1]");
        root_helper("-1", 3, "Optional[-1]");

        root_helper("-2", 1, "Optional[-2]");
        root_helper("-2", 3, "Optional.empty");

        root_helper("1000000", 6, "Optional[10]");
        root_helper("-125", 3, "Optional[-5]");

        root_fail_helper("-1", 2);
    }

    private static void sqrt_helper(@NotNull String n, @NotNull String output) {
        aeq(sqrt(Readers.readBigIntegerStrict(n).get()), output);
    }

    private static void sqrt_fail_helper(@NotNull String n) {
        try {
            sqrt(Readers.readBigIntegerStrict(n).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSqrt() {
        sqrt_helper("0", "Optional[0]");
        sqrt_helper("1", "Optional[1]");
        sqrt_helper("2", "Optional.empty");

        sqrt_helper("1000000", "Optional[1000]");

        sqrt_fail_helper("-1");
    }

    private static void cbrt_helper(@NotNull String n, @NotNull String output) {
        aeq(cbrt(Readers.readBigIntegerStrict(n).get()), output);
    }

    @Test
    public void testCbrt() {
        cbrt_helper("0", "Optional[0]");
        cbrt_helper("1", "Optional[1]");
        cbrt_helper("2", "Optional.empty");
        cbrt_helper("-1", "Optional[-1]");
        cbrt_helper("-2", "Optional.empty");

        cbrt_helper("1000000", "Optional[100]");
        cbrt_helper("-125", "Optional[-5]");
    }

    private static void totient_int_helper(int input, int output) {
        aeq(totient(input), output);
    }

    private static void totient_int_fail_helper(int input) {
        try {
            totient(input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testTotient_int() {
        totient_int_helper(1, 1);
        totient_int_helper(2, 1);
        totient_int_helper(3, 2);
        totient_int_helper(4, 2);
        totient_int_helper(5, 4);
        totient_int_helper(6, 2);
        totient_int_helper(7, 6);
        totient_int_helper(8, 4);
        totient_int_helper(9, 6);
        totient_int_helper(10, 4);
        totient_int_helper(1807, 1656);
        totient_int_helper(6221671, 6221670);
        totient_int_helper(65533, 59640);
        totient_int_helper(2147483647, 2147483646);
        totient_int_helper(2147483643, 1431655760);
        totient_int_helper(2147483641, 2146685284);

        totient_int_fail_helper(0);
        totient_int_fail_helper(-1);
    }

    private static void totient_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(totient(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void totient_BigInteger_fail_helper(@NotNull String input) {
        try {
            totient(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testTotient_BigInteger() {
        totient_BigInteger_helper("1", "1");
        totient_BigInteger_helper("2", "1");
        totient_BigInteger_helper("3", "2");
        totient_BigInteger_helper("4", "2");
        totient_BigInteger_helper("5", "4");
        totient_BigInteger_helper("6", "2");
        totient_BigInteger_helper("7", "6");
        totient_BigInteger_helper("8", "4");
        totient_BigInteger_helper("9", "6");
        totient_BigInteger_helper("10", "4");
        totient_BigInteger_helper("1807", "1656");
        totient_BigInteger_helper("6221671", "6221670");
        totient_BigInteger_helper("65533", "59640");
        totient_BigInteger_helper("2147483647", "2147483646");
        totient_BigInteger_helper("2147483643", "1431655760");
        totient_BigInteger_helper("2147483641", "2146685284");
        totient_BigInteger_helper("2147483649", "1431655764");
        totient_BigInteger_helper("2147483659", "2147483658");
        totient_BigInteger_helper("1000000000039", "1000000000038");
        totient_BigInteger_helper("1000000000000037", "1000000000000036");
        totient_BigInteger_helper("1000000000000039", "908720478529024");

        totient_BigInteger_fail_helper("0");
        totient_BigInteger_fail_helper("-1");
    }

    private static void inverseTotient_helper(@NotNull String input, @NotNull String output) {
        aeq(inverseTotient(Readers.readBigIntegerStrict(input).get()), output);
    }

    private static void inverseTotient_fail_helper(@NotNull String input) {
        try {
            inverseTotient(Readers.readBigIntegerStrict(input).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testInverseTotient() {
        inverseTotient_helper("1", "[1, 2]");
        inverseTotient_helper("2", "[3, 4, 6]");
        inverseTotient_helper("3", "[]");
        inverseTotient_helper("4", "[5, 8, 10, 12]");
        inverseTotient_helper("5", "[]");
        inverseTotient_helper("6", "[7, 9, 14, 18]");
        inverseTotient_helper("7", "[]");
        inverseTotient_helper("8", "[15, 16, 20, 24, 30]");
        inverseTotient_helper("9", "[]");
        inverseTotient_helper("10", "[11, 22]");
        inverseTotient_helper("1656",
                "[1657, 1739, 1807, 1939, 2487, 2493, 2679, 2919, 2961, 3314, 3316, 3478, 3572, 3614, 3878, 3892," +
                " 4974, 4986, 5004, 5076, 5358, 5838, 5922]");
        inverseTotient_helper("6221670", "[6221671, 12443342]");
        inverseTotient_helper("59640",
                "[60563, 61799, 65533, 69587, 89469, 105861, 121126, 123598, 131066, 139174, 141148, 178938, 181476," +
                " 211722]");
        inverseTotient_helper("2147483646", "[2147483647, 4294967294]");
        inverseTotient_helper("1431655760",
                "[1820425825, 2147483643, 2159969529, 2189665761, 2402962089, 2863311524, 2879959372, 2919554348," +
                " 3203949452, 3640851650, 4294967286, 4319939058, 4379331522, 4805924178]");
        inverseTotient_helper("2146685284", "[2147483641, 4294967282]");
        inverseTotient_helper("1431655764",
                "[1431772649, 1433755649, 1465742849, 1670265107, 2147483649, 2863311532, 2863545298, 2867511298," +
                " 2931485698, 3340530214, 4294967298]");
        inverseTotient_helper("2147483658", "[2147483659, 4294967318]");
        inverseTotient_helper("1000000000038", "[1000000000039, 2000000000078]");
        inverseTotient_helper("1000000000000036", "[1000000000000037, 2000000000000074]");

        inverseTotient_fail_helper("0");
        inverseTotient_fail_helper("-1");
    }

    private static void greedyNormalSequence_helper(int base, @NotNull String output) {
        aeqitLimit(SMALL_LIMIT, greedyNormalSequence(base), output);
    }

    private static void greedyNormalSequence_fail_helper(int base) {
        try {
            greedyNormalSequence(base);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testGreedyNormalSequence() {
        greedyNormalSequence_helper(2,
                "[0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0," +
                " 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0," +
                " 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0," +
                " 0, ...]");
        greedyNormalSequence_helper(3,
                "[0, 1, 2, 0, 2, 1, 0, 0, 1, 1, 2, 2, 0, 0, 2, 1, 1, 0, 2, 2, 1, 2, 0, 1, 0, 1, 1, 1, 2, 0, 0, 0, 2," +
                " 2, 2, 1, 0, 1, 2, 1, 0, 2, 0, 1, 1, 0, 0, 0, 1, 0, 2, 2, 0, 2, 2, 1, 1, 2, 1, 2, 2, 0, 1, 1, 1, 0," +
                " 0, 2, 0, 0, 1, 2, 2, 1, 2, 1, 0, 0, 2, 2, 0, 1, 2, 1, 1, 0, 0, 2, 1, 2, 0, 2, 0, 1, 1, 2, 0, 1, 2," +
                " 2, ...]");
        greedyNormalSequence_helper(4,
                "[0, 1, 2, 3, 0, 2, 1, 3, 1, 0, 3, 2, 0, 0, 1, 1, 2, 2, 3, 3, 0, 3, 1, 1, 0, 2, 2, 0, 3, 3, 2, 1, 0," +
                " 0, 2, 3, 1, 3, 2, 1, 1, 3, 0, 0, 0, 1, 2, 0, 2, 0, 1, 3, 3, 1, 2, 2, 1, 0, 3, 0, 1, 0, 1, 1, 1, 2," +
                " 3, 2, 2, 2, 3, 3, 3, 0, 2, 0, 3, 1, 3, 2, 3, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 0, 3, 1, 2, 3, 3, 1, 0," +
                " 0, ...]");
        greedyNormalSequence_helper(10,
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 2, 1, 3, 5, 4, 6, 8, 7, 9, 1, 0, 3, 2, 4, 7, 5, 8, 6, 9, 2, 0, 4," +
                " 1, 5, 3, 6, 0, 7, 0, 8, 0, 9, 3, 1, 4, 2, 5, 7, 6, 1, 8, 1, 9, 4, 3, 7, 2, 6, 5, 9, 8, 2, 7, 3, 8," +
                " 4, 9, 5, 0, 6, 2, 8, 3, 9, 6, 4, 0, 5, 1, 7, 1, 6, 3, 0, 0, 2, 9, 7, 4, 8, 5, 2, 1, 1, 3, 3, 4, 4," +
                " 5, ...]");
        greedyNormalSequence_helper(83,
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26," +
                " 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50," +
                " 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74," +
                " 75, 76, 77, 78, 79, 80, 81, 82, 0, 2, 1, 3, 5, 4, 6, 8, 7, 9, 11, 10, 12, 14, 13, 15, 17, ...]");
        greedyNormalSequence_helper(100,
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26," +
                " 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50," +
                " 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74," +
                " 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98," +
                " 99, ...]");

        greedyNormalSequence_fail_helper(1);
        greedyNormalSequence_fail_helper(0);
        greedyNormalSequence_fail_helper(-1);
    }

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readListStrict(Readers::readBigIntegerStrict).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readBigIntegerStrict).apply(s).get();
    }
}
