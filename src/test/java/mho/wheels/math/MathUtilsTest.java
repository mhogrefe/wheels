package mho.wheels.math;

import mho.wheels.io.Readers;
import mho.wheels.numberUtils.IntegerUtils;
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

    @Test
    public void testFactorial_int() {
        aeq(factorial(0), 1);
        aeq(factorial(1), 1);
        aeq(factorial(2), 2);
        aeq(factorial(3), 6);
        aeq(factorial(4), 24);
        aeq(factorial(5), 120);
        aeq(factorial(6), 720);
        aeq(factorial(10), 3628800);
        aeq(factorial(100), "9332621544394415268169923885626670049071596826438162146859296389521759999322991" +
                            "5608941463976156518286253697920827223758251185210916864000000000000000000000000");
        try {
            factorial(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testFactorial_BigInteger() {
        aeq(factorial(BigInteger.ZERO), 1);
        aeq(factorial(BigInteger.ONE), 1);
        aeq(factorial(IntegerUtils.TWO), 2);
        aeq(factorial(BigInteger.valueOf(3)), 6);
        aeq(factorial(BigInteger.valueOf(4)), 24);
        aeq(factorial(BigInteger.valueOf(5)), 120);
        aeq(factorial(BigInteger.valueOf(6)), 720);
        aeq(factorial(BigInteger.TEN), 3628800);
        aeq(factorial(BigInteger.valueOf(100)),
                "9332621544394415268169923885626670049071596826438162146859296389521759999322991" +
                "5608941463976156518286253697920827223758251185210916864000000000000000000000000");
        try {
            factorial(IntegerUtils.NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubfactorial_int() {
        aeq(subfactorial(0), 1);
        aeq(subfactorial(1), 0);
        aeq(subfactorial(2), 1);
        aeq(subfactorial(3), 2);
        aeq(subfactorial(4), 9);
        aeq(subfactorial(5), 44);
        aeq(subfactorial(6), 265);
        aeq(subfactorial(10), 1334961);
        aeq(subfactorial(100), "3433279598416380476519597752677614203236578380537578498354340028268518079332763" +
                               "2432791396429850988990237345920155783984828001486412574060553756854137069878601"); ///
        try {
            subfactorial(-1);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubfactorial_BigInteger() {
        aeq(subfactorial(BigInteger.ZERO), 1);
        aeq(subfactorial(BigInteger.ONE), 0);
        aeq(subfactorial(IntegerUtils.TWO), 1);
        aeq(subfactorial(BigInteger.valueOf(3)), 2);
        aeq(subfactorial(BigInteger.valueOf(4)), 9);
        aeq(subfactorial(BigInteger.valueOf(5)), 44);
        aeq(subfactorial(BigInteger.valueOf(6)), 265);
        aeq(subfactorial(BigInteger.TEN), 1334961);
        aeq(subfactorial(BigInteger.valueOf(100)),
                "3433279598416380476519597752677614203236578380537578498354340028268518079332763" +
                "2432791396429850988990237345920155783984828001486412574060553756854137069878601");
        try {
            subfactorial(IntegerUtils.NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
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
