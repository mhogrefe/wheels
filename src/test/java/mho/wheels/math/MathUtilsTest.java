package mho.wheels.math;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.testNoRemove;
import static org.junit.Assert.fail;

public class MathUtilsTest {
    private static void gcd_int_int_helper(int x, int y, int output) {
        aeq(gcd(x, y), output);
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
    }

    private static void gcd_long_long_helper(long x, long y, long output) {
        aeq(gcd(x, y), output);
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
    }

    private static void lcm_BigInteger_BigInteger_helper(
            @NotNull String x,
            @NotNull String y,
            @NotNull String output
    ) {
        aeq(lcm(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get()), output);
    }

    private static void lcm_BigInteger_BigInteger_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            lcm(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get());
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
        aeq(factorial(Readers.readBigInteger(input).get()), output);
    }

    private static void factorial_BigInteger_fail_helper(@NotNull String input) {
        try {
            factorial(Readers.readBigInteger(input).get());
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
        aeq(subfactorial(Readers.readBigInteger(input).get()), output);
    }

    private static void subfactorial_BigInteger_fail_helper(@NotNull String input) {
        try {
            subfactorial(Readers.readBigInteger(input).get());
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
        aeq(fallingFactorial(Readers.readBigInteger(x).get(), n), output);
    }

    private static void fallingFactorial_fail_helper(@NotNull String x, int n) {
        try {
            fallingFactorial(Readers.readBigInteger(x).get(), n);
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
        numberOfArrangementsOfASet_int_int_helper(20, 100,
                "253686955560127297415270748212280220445147578566298142232775185987449253908386446518940485425152049" +
                "793267407732328003493593216076399867112102621246975180800000");

        numberOfArrangementsOfASet_int_int_fail_helper(0, -1);
        numberOfArrangementsOfASet_int_int_fail_helper(-1, 0);
    }

    private static void binomialCoefficient_helper(@NotNull String n, int k, @NotNull String output) {
        aeq(binomialCoefficient(Readers.readBigInteger(n).get(), k), output);
    }

    private static void binomialCoefficient_fail_helper(@NotNull String n, int k) {
        try {
            binomialCoefficient(Readers.readBigInteger(n).get(), k);
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
        aeq(multisetCoefficient(Readers.readBigInteger(n).get(), k), output);
    }

    private static void multisetCoefficient_fail_helper(@NotNull String n, int k) {
        try {
            multisetCoefficient(Readers.readBigInteger(n).get(), k);
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
        aeq(fastGrowingCeilingInverse(f, min, max, Readers.readBigInteger(y).get()), output);
    }

    private static void fastGrowingCeilingInverse_fail_helper(
            @NotNull Function<Integer, BigInteger> f,
            int min,
            int max,
            @NotNull String y
    ) {
        try {
            fastGrowingCeilingInverse(f, min, max, Readers.readBigInteger(y).get());
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
        aeq(ceilingLog(Readers.readBigInteger(base).get(), Readers.readBigInteger(x).get()), output);
    }

    private static void ceilingLog_fail_helper(@NotNull String base, @NotNull String x) {
        try {
            ceilingLog(Readers.readBigInteger(base).get(), Readers.readBigInteger(x).get());
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
                        Readers.readBigInteger(min).get(),
                        Readers.readBigInteger(max).get(),
                        Readers.readBigInteger(y).get()
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
                    Readers.readBigInteger(min).get(),
                    Readers.readBigInteger(max).get(),
                    Readers.readBigInteger(y).get()
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
        aeq(ceilingRoot(r, Readers.readBigInteger(x).get()), output);
    }

    private static void ceilingRoot_fail_helper(int r, @NotNull String x) {
        try {
            ceilingRoot(r, Readers.readBigInteger(x).get());
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
        aeq(smallestPrimeFactor(Readers.readBigInteger(input).get()), output);
    }

    private static void smallestPrimeFactor_BigInteger_fail_helper(@NotNull String input) {
        try {
            smallestPrimeFactor(Readers.readBigInteger(input).get());
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
        aeq(isPrime(Readers.readBigInteger(input).get()), output);
    }

    private static void isPrime_BigInteger_fail_helper(@NotNull String input) {
        try {
            isPrime(Readers.readBigInteger(input).get());
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
        Testing.aeqit(primeFactors(Readers.readBigInteger(input).get()), output);
    }

    private static void primeFactors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(primeFactors(Readers.readBigInteger(input).get()));
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
        Testing.aeqit(compactPrimeFactors(Readers.readBigInteger(input).get()), output);
    }

    private static void compactPrimeFactors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(compactPrimeFactors(Readers.readBigInteger(input).get()));
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
        Testing.aeqit(factors(Readers.readBigInteger(input).get()), output);
    }

    private static void factors_BigInteger_fail_helper(@NotNull String input) {
        try {
            toList(factors(Readers.readBigInteger(input).get()));
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

    @Test
    public void testIntPrimes() {
        Iterable<Integer> ps = intPrimes();
        Testing.aeqitLimit(SMALL_LIMIT, ps,
                "[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97," +
                " 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193," +
                " 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307," +
                " 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421," +
                " 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, ...]");
        testNoRemove(SMALL_LIMIT, ps);
    }

    @Test
    public void testPrimes() {
        Iterable<BigInteger> ps = primes();
        Testing.aeqitLimit(SMALL_LIMIT, ps,
                "[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97," +
                " 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193," +
                " 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307," +
                " 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421," +
                " 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, ...]");
        testNoRemove(SMALL_LIMIT, ps);
    }

    private static void largestPerfectPowerFactor_helper(int p, @NotNull String n, @NotNull String output) {
        aeq(largestPerfectPowerFactor(p, Readers.readBigInteger(n).get()), output);
    }

    private static void largestPerfectPowerFactor_fail_helper(int p, @NotNull String n) {
        try {
            largestPerfectPowerFactor(p, Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLargestPerfectPowerFactor() {
        largestPerfectPowerFactor_helper(1, "1", "1");
        largestPerfectPowerFactor_helper(1, "2", "2");
        largestPerfectPowerFactor_helper(1, "3", "3");
        largestPerfectPowerFactor_helper(1, "4", "4");
        largestPerfectPowerFactor_helper(1, "100", "100");

        largestPerfectPowerFactor_helper(2, "1", "1");
        largestPerfectPowerFactor_helper(2, "2", "1");
        largestPerfectPowerFactor_helper(2, "3", "1");
        largestPerfectPowerFactor_helper(2, "4", "2");
        largestPerfectPowerFactor_helper(2, "12", "2");
        largestPerfectPowerFactor_helper(2, "75", "5");
        largestPerfectPowerFactor_helper(2, "100", "10");

        largestPerfectPowerFactor_helper(10, "1024", "2");
        largestPerfectPowerFactor_helper(10, "10240", "2");
        largestPerfectPowerFactor_helper(10, "10000000000", "10");

        largestPerfectPowerFactor_fail_helper(0, "1");
        largestPerfectPowerFactor_fail_helper(-1, "1");
        largestPerfectPowerFactor_fail_helper(1, "0");
        largestPerfectPowerFactor_fail_helper(1, "-1");
    }

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readBigInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBigInteger).apply(s).get();
    }
}
