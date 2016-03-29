package mho.wheels.math;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.aeq;
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

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readBigInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBigInteger).apply(s).get();
    }
}
