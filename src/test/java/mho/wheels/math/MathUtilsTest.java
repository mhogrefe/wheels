package mho.wheels.math;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

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

    private static void reversePermutationsSign_helper(int i, boolean output) {
        aeq(reversePermutationSign(i), output);
    }

    @Test
    public void testReversePermutationSign() {
        reversePermutationsSign_helper(0, true);
        reversePermutationsSign_helper(1, true);
        reversePermutationsSign_helper(2, false);
        reversePermutationsSign_helper(3, false);
        reversePermutationsSign_helper(4, true);
        reversePermutationsSign_helper(5, true);
        reversePermutationsSign_helper(-1, false);
        reversePermutationsSign_helper(-2, false);
        reversePermutationsSign_helper(-3, true);
        reversePermutationsSign_helper(-4, true);
        reversePermutationsSign_helper(-5, false);
    }

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readBigInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBigInteger).apply(s).get();
    }
}
