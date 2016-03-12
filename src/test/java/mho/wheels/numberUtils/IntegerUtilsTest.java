package mho.wheels.numberUtils;

import mho.wheels.io.Readers;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.numberUtils.IntegerUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static org.junit.Assert.*;

public class IntegerUtilsTest {
    @Test
    public void testConstants() {
        aeq(NEGATIVE_ONE, "-1");
        aeq(TWO, "2");
    }

    private static void isPowerOfTwo_int_helper(int input, boolean output) {
        aeq(isPowerOfTwo(input), output);
    }

    private static void isPowerOfTwo_int_fail_helper(int input) {
        try {
            isPowerOfTwo(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testIsPowerOfTwo_int() {
        isPowerOfTwo_int_helper(1, true);
        isPowerOfTwo_int_helper(2, true);
        isPowerOfTwo_int_helper(4, true);
        isPowerOfTwo_int_helper(8, true);
        isPowerOfTwo_int_helper(16, true);
        isPowerOfTwo_int_helper(1 << 30, true);
        isPowerOfTwo_int_helper(3, false);
        isPowerOfTwo_int_helper(13, false);
        isPowerOfTwo_int_helper(100, false);
        isPowerOfTwo_int_fail_helper(0);
        isPowerOfTwo_int_fail_helper(-5);
    }

    private static void isPowerOfTwo_long_helper(long input, boolean output) {
        aeq(isPowerOfTwo(input), output);
    }

    private static void isPowerOfTwo_long_fail_helper(long input) {
        try {
            isPowerOfTwo(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testIsPowerOfTwo_long() {
        isPowerOfTwo_long_helper(1L, true);
        isPowerOfTwo_long_helper(2L, true);
        isPowerOfTwo_long_helper(4L, true);
        isPowerOfTwo_long_helper(8L, true);
        isPowerOfTwo_long_helper(16L, true);
        isPowerOfTwo_long_helper(1L << 62, true);
        isPowerOfTwo_long_helper(3L, false);
        isPowerOfTwo_long_helper(13L, false);
        isPowerOfTwo_long_helper(100L, false);
        isPowerOfTwo_long_fail_helper(0);
        isPowerOfTwo_long_fail_helper(-5);
    }

    private static void isPowerOfTwo_BigInteger_helper(@NotNull String input, boolean output) {
        aeq(isPowerOfTwo(Readers.readBigInteger(input).get()), output);
    }

    private static void isPowerOfTwo_BigInteger_fail_helper(@NotNull String input) {
        try {
            isPowerOfTwo(Readers.readBigInteger(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testIsPowerOfTwo_BigInteger() {
        isPowerOfTwo_BigInteger_helper("1", true);
        isPowerOfTwo_BigInteger_helper("2", true);
        isPowerOfTwo_BigInteger_helper("4", true);
        isPowerOfTwo_BigInteger_helper("8", true);
        isPowerOfTwo_BigInteger_helper("16", true);
        isPowerOfTwo_BigInteger_helper("3", false);
        isPowerOfTwo_BigInteger_helper("13", false);
        isPowerOfTwo_BigInteger_helper("100", false);
        isPowerOfTwo_BigInteger_fail_helper("0");
        isPowerOfTwo_BigInteger_fail_helper("-5");
    }

    private static void ceilingLog2_int_helper(int input, int output) {
        aeq(ceilingLog2(input), output);
    }

    private static void ceilingLog2_int_fail_helper(int n) {
        try {
            ceilingLog2(n);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testCeilingLog2_int() {
        ceilingLog2_int_helper(1, 0);
        ceilingLog2_int_helper(2, 1);
        ceilingLog2_int_helper(3, 2);
        ceilingLog2_int_helper(4, 2);
        ceilingLog2_int_helper(5, 3);
        ceilingLog2_int_helper(6, 3);
        ceilingLog2_int_helper(7, 3);
        ceilingLog2_int_helper(8, 3);
        ceilingLog2_int_helper(9, 4);
        ceilingLog2_int_helper(1000, 10);
        ceilingLog2_int_helper(Integer.MAX_VALUE, 31);
        ceilingLog2_int_fail_helper(0);
        ceilingLog2_int_fail_helper(-5);
    }

    private static void ceilingLog2_long_helper(long input, int output) {
        aeq(ceilingLog2(input), output);
    }

    private static void ceilingLog2_long_fail_helper(long n) {
        try {
            ceilingLog2(n);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testCeilingLog2_long() {
        ceilingLog2_long_helper(1L, 0);
        ceilingLog2_long_helper(2L, 1);
        ceilingLog2_long_helper(3L, 2);
        ceilingLog2_long_helper(4L, 2);
        ceilingLog2_long_helper(5L, 3);
        ceilingLog2_long_helper(6L, 3);
        ceilingLog2_long_helper(7L, 3);
        ceilingLog2_long_helper(8L, 3);
        ceilingLog2_long_helper(9L, 4);
        ceilingLog2_long_helper(1000L, 10);
        ceilingLog2_long_helper(Long.MAX_VALUE, 63);
        ceilingLog2_long_fail_helper(0L);
        ceilingLog2_long_fail_helper(-5L);
    }

    private static void ceilingLog2_BigInteger_helper(@NotNull String input, int output) {
        aeq(ceilingLog2(Readers.readBigInteger(input).get()), output);
    }

    private static void ceilingLog2_BigInteger_fail_helper(@NotNull String input) {
        try {
            ceilingLog2(Readers.readBigInteger(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testCeilingLog2_BigInteger() {
        ceilingLog2_BigInteger_helper("1", 0);
        ceilingLog2_BigInteger_helper("2", 1);
        ceilingLog2_BigInteger_helper("3", 2);
        ceilingLog2_BigInteger_helper("4", 2);
        ceilingLog2_BigInteger_helper("5", 3);
        ceilingLog2_BigInteger_helper("6", 3);
        ceilingLog2_BigInteger_helper("7", 3);
        ceilingLog2_BigInteger_helper("8", 3);
        ceilingLog2_BigInteger_helper("9", 4);
        ceilingLog2_BigInteger_helper("1000", 10);
        ceilingLog2_BigInteger_fail_helper("0");
        ceilingLog2_BigInteger_fail_helper("-5");
    }

    private static void bits_int_helper(int input, @NotNull String output) {
        aeq(bits(input), output);
    }

    private static void bits_int_fail_helper(int input) {
        try {
            bits(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBits_int() {
        bits_int_helper(0, "[]");
        bits_int_helper(1, "[true]");
        bits_int_helper(6, "[false, true, true]");
        bits_int_helper(105, "[true, false, false, true, false, true, true]");
        bits_int_fail_helper(-1);
    }

    private static void bits_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(bits(Readers.readBigInteger(input).get()), output);
    }

    private static void bits_BigInteger_fail_helper(@NotNull String input) {
        try {
            bits(Readers.readBigInteger(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBits_BigInteger() {
        bits_BigInteger_helper("0", "[]");
        bits_BigInteger_helper("1", "[true]");
        bits_BigInteger_helper("6", "[false, true, true]");
        bits_BigInteger_helper("105", "[true, false, false, true, false, true, true]");
        bits_BigInteger_fail_helper("-1");
    }

    private static void bitsPadded_int_int_helper(int length, int n, @NotNull String output) {
        aeq(bitsPadded(length, n), output);
    }

    private static void bitsPadded_int_int_fail_helper(int length, int n) {
        try {
            bitsPadded(length, n);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testBitsPadded_int_int() {
        bitsPadded_int_int_helper(8, 0, "[false, false, false, false, false, false, false, false]");
        bitsPadded_int_int_helper(8, 1, "[true, false, false, false, false, false, false, false]");
        bitsPadded_int_int_helper(8, 6, "[false, true, true, false, false, false, false, false]");
        bitsPadded_int_int_helper(8, 105, "[true, false, false, true, false, true, true, false]");
        bitsPadded_int_int_helper(8, 1000, "[false, false, false, true, false, true, true, true]");
        bitsPadded_int_int_helper(2, 104, "[false, false]");
        bitsPadded_int_int_helper(2, 105, "[true, false]");
        bitsPadded_int_int_helper(1, 104, "[false]");
        bitsPadded_int_int_helper(1, 105, "[true]");
        bitsPadded_int_int_helper(0, 104, "[]");
        bitsPadded_int_int_fail_helper(8, -1);
        bitsPadded_int_int_fail_helper(-1, 8);
    }

    private static void bitsPadded_int_BigInteger_helper(int length, @NotNull String n, @NotNull String output) {
        aeq(bitsPadded(length, Readers.readBigInteger(n).get()), output);
    }

    private static void bitsPadded_int_BigInteger_fail_helper(int length, @NotNull String n) {
        try {
            bitsPadded(length, Readers.readBigInteger(n).get());
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testBitsPadded_int_BigInteger() {
        bitsPadded_int_BigInteger_helper(8, "0", "[false, false, false, false, false, false, false, false]");
        bitsPadded_int_BigInteger_helper(8, "1", "[true, false, false, false, false, false, false, false]");
        bitsPadded_int_BigInteger_helper(8, "6", "[false, true, true, false, false, false, false, false]");
        bitsPadded_int_BigInteger_helper(8, "105", "[true, false, false, true, false, true, true, false]");
        bitsPadded_int_BigInteger_helper(8, "1000", "[false, false, false, true, false, true, true, true]");
        bitsPadded_int_BigInteger_helper(2, "104", "[false, false]");
        bitsPadded_int_BigInteger_helper(2, "105", "[true, false]");
        bitsPadded_int_BigInteger_helper(1, "104", "[false]");
        bitsPadded_int_BigInteger_helper(1, "105", "[true]");
        bitsPadded_int_BigInteger_helper(0, "104", "[]");
        bitsPadded_int_BigInteger_fail_helper(8, "-1");
        bitsPadded_int_BigInteger_fail_helper(-1, "8");
    }

    private static void bigEndianBits_int_helper(int input, @NotNull String output) {
        aeq(bigEndianBits(input), output);
    }

    private static void bigEndianBits_int_fail_helper(int input) {
        try {
            bigEndianBits(input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBits_int() {
        bigEndianBits_int_helper(0, "[]");
        bigEndianBits_int_helper(1, "[true]");
        bigEndianBits_int_helper(6, "[true, true, false]");
        bigEndianBits_int_helper(105, "[true, true, false, true, false, false, true]");
        bigEndianBits_int_fail_helper(-1);
    }

    private static void bigEndianBits_BigInteger_helper(@NotNull String input, @NotNull String output) {
        aeq(bigEndianBits(Readers.readBigInteger(input).get()), output);
    }

    private static void bigEndianBits_BigInteger_fail_helper(@NotNull String input) {
        try {
            bigEndianBits(Readers.readBigInteger(input).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianBits_BigInteger() {
        bigEndianBits_BigInteger_helper("0", "[]");
        bigEndianBits_BigInteger_helper("1", "[true]");
        bigEndianBits_BigInteger_helper("6", "[true, true, false]");
        bigEndianBits_BigInteger_helper("105", "[true, true, false, true, false, false, true]");
        bigEndianBits_BigInteger_fail_helper("-1");
    }

    private static void bigEndianBitsPadded_int_int_helper(int length, int n, @NotNull String output) {
        aeq(bigEndianBitsPadded(length, n), output);
    }

    private static void bigEndianBitsPadded_int_int_fail_helper(int length, int n) {
        try {
            bigEndianBitsPadded(length, n);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianBitsPadded_int_int() {
        bigEndianBitsPadded_int_int_helper(8, 0, "[false, false, false, false, false, false, false, false]");
        bigEndianBitsPadded_int_int_helper(8, 1, "[false, false, false, false, false, false, false, true]");
        bigEndianBitsPadded_int_int_helper(8, 6, "[false, false, false, false, false, true, true, false]");
        bigEndianBitsPadded_int_int_helper(8, 105, "[false, true, true, false, true, false, false, true]");
        bigEndianBitsPadded_int_int_helper(8, 1000, "[true, true, true, false, true, false, false, false]");
        bigEndianBitsPadded_int_int_helper(2, 104, "[false, false]");
        bigEndianBitsPadded_int_int_helper(2, 105, "[false, true]");
        bigEndianBitsPadded_int_int_helper(1, 104, "[false]");
        bigEndianBitsPadded_int_int_helper(1, 105, "[true]");
        bigEndianBitsPadded_int_int_helper(0, 104, "[]");
        bigEndianBitsPadded_int_int_fail_helper(8, -1);
        bigEndianBitsPadded_int_int_fail_helper(-1, 8);
    }

    private static void bigEndianBitsPadded_int_BigInteger_helper(
            int length,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(bigEndianBitsPadded(length, Readers.readBigInteger(n).get()), output);
    }

    private static void bigEndianBitsPadded_int_BigInteger_fail_helper(int length, @NotNull String n) {
        try {
            bigEndianBitsPadded(length, Readers.readBigInteger(n).get());
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianBitsPadded_BigInteger_BigInteger() {
        bigEndianBitsPadded_int_BigInteger_helper(8, "0", "[false, false, false, false, false, false, false, false]");
        bigEndianBitsPadded_int_BigInteger_helper(8, "1", "[false, false, false, false, false, false, false, true]");
        bigEndianBitsPadded_int_BigInteger_helper(8, "6", "[false, false, false, false, false, true, true, false]");
        bigEndianBitsPadded_int_BigInteger_helper(8, "105", "[false, true, true, false, true, false, false, true]");
        bigEndianBitsPadded_int_BigInteger_helper(8, "1000", "[true, true, true, false, true, false, false, false]");
        bigEndianBitsPadded_int_BigInteger_helper(2, "104", "[false, false]");
        bigEndianBitsPadded_int_BigInteger_helper(2, "105", "[false, true]");
        bigEndianBitsPadded_int_BigInteger_helper(1, "104", "[false]");
        bigEndianBitsPadded_int_BigInteger_helper(1, "105", "[true]");
        bigEndianBitsPadded_int_BigInteger_helper(0, "104", "[]");
        bigEndianBitsPadded_int_BigInteger_fail_helper(8, "-1");
        bigEndianBitsPadded_int_BigInteger_fail_helper(-1, "8");
    }

    private static void fromBits_helper(@NotNull String input, @NotNull String output) {
        aeq(fromBits(readBooleanList(input)), output);
    }

    private static void fromBits_fail_helper(@NotNull String input) {
        try {
            fromBits(readBooleanListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testFromBits() {
        fromBits_helper("[]", "0");
        fromBits_helper("[false, false]", "0");
        fromBits_helper("[true, false]", "1");
        fromBits_helper("[false, true, true, false, false, false, false, false]", "6");
        fromBits_helper("[true, false, false, true, false, true, true]", "105");
        fromBits_fail_helper("[true, null, true]");
    }

    private static void fromBigEndianBits_helper(@NotNull String input, @NotNull String output) {
        aeq(fromBigEndianBits(readBooleanList(input)), output);
    }

    private static void fromBigEndianBits_fail_helper(@NotNull String input) {
        try {
            fromBigEndianBits(readBooleanListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testFromBigEndianBits() {
        fromBigEndianBits_helper("[]", "0");
        fromBigEndianBits_helper("[false, false]", "0");
        fromBigEndianBits_helper("[false, true]", "1");
        fromBigEndianBits_helper("[false, false, false, false, false, true, true, false]", "6");
        fromBigEndianBits_helper("[true, true, false, true, false, false, true]", "105");
        fromBigEndianBits_fail_helper("[true, null, true]");
    }

    private static void digits_int_int_helper(int base, int n, @NotNull String output) {
        aeq(digits(base, n), output);
    }

    private static void digits_int_int_fail_helper(int base, int n) {
        try {
            digits(base, n);
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testDigits_int_int() {
        digits_int_int_helper(2, 0, "[]");
        digits_int_int_helper(3, 0, "[]");
        digits_int_int_helper(8, 0, "[]");
        digits_int_int_helper(10, 0, "[]");
        digits_int_int_helper(12, 0, "[]");
        digits_int_int_helper(57, 0, "[]");
        digits_int_int_helper(2, 1, "[1]");
        digits_int_int_helper(3, 1, "[1]");
        digits_int_int_helper(8, 1, "[1]");
        digits_int_int_helper(10, 1, "[1]");
        digits_int_int_helper(12, 1, "[1]");
        digits_int_int_helper(57, 1, "[1]");
        digits_int_int_helper(2, 10, "[0, 1, 0, 1]");
        digits_int_int_helper(3, 10, "[1, 0, 1]");
        digits_int_int_helper(8, 10, "[2, 1]");
        digits_int_int_helper(10, 10, "[0, 1]");
        digits_int_int_helper(12, 10, "[10]");
        digits_int_int_helper(57, 10, "[10]");
        digits_int_int_helper(2, 187, "[1, 1, 0, 1, 1, 1, 0, 1]");
        digits_int_int_helper(3, 187, "[1, 2, 2, 0, 2]");
        digits_int_int_helper(8, 187, "[3, 7, 2]");
        digits_int_int_helper(10, 187, "[7, 8, 1]");
        digits_int_int_helper(12, 187, "[7, 3, 1]");
        digits_int_int_helper(57, 187, "[16, 3]");
        digits_int_int_fail_helper(1, 10);
        digits_int_int_fail_helper(0, 10);
        digits_int_int_fail_helper(2, -1);
        digits_int_int_fail_helper(0, -1);
    }

    private static void digits_BigInteger_BigInteger_helper(
            @NotNull String base,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(digits(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get()), output);
    }

    private static void digits_BigInteger_BigInteger_fail_helper(@NotNull String base, @NotNull String n) {
        try {
            digits(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testDigits_BigInteger_BigInteger() {
        digits_BigInteger_BigInteger_helper("2", "0", "[]");
        digits_BigInteger_BigInteger_helper("3", "0", "[]");
        digits_BigInteger_BigInteger_helper("8", "0", "[]");
        digits_BigInteger_BigInteger_helper("10", "0", "[]");
        digits_BigInteger_BigInteger_helper("12", "0", "[]");
        digits_BigInteger_BigInteger_helper("57", "0", "[]");
        digits_BigInteger_BigInteger_helper("2", "1", "[1]");
        digits_BigInteger_BigInteger_helper("3", "1", "[1]");
        digits_BigInteger_BigInteger_helper("8", "1", "[1]");
        digits_BigInteger_BigInteger_helper("10", "1", "[1]");
        digits_BigInteger_BigInteger_helper("12", "1", "[1]");
        digits_BigInteger_BigInteger_helper("57", "1", "[1]");
        digits_BigInteger_BigInteger_helper("2", "10", "[0, 1, 0, 1]");
        digits_BigInteger_BigInteger_helper("3", "10", "[1, 0, 1]");
        digits_BigInteger_BigInteger_helper("8", "10", "[2, 1]");
        digits_BigInteger_BigInteger_helper("10", "10", "[0, 1]");
        digits_BigInteger_BigInteger_helper("12", "10", "[10]");
        digits_BigInteger_BigInteger_helper("57", "10", "[10]");
        digits_BigInteger_BigInteger_helper("2", "187", "[1, 1, 0, 1, 1, 1, 0, 1]");
        digits_BigInteger_BigInteger_helper("3", "187", "[1, 2, 2, 0, 2]");
        digits_BigInteger_BigInteger_helper("8", "187", "[3, 7, 2]");
        digits_BigInteger_BigInteger_helper("10", "187", "[7, 8, 1]");
        digits_BigInteger_BigInteger_helper("12", "187", "[7, 3, 1]");
        digits_BigInteger_BigInteger_helper("57", "187", "[16, 3]");
        digits_BigInteger_BigInteger_fail_helper("1", "10");
        digits_BigInteger_BigInteger_fail_helper("0", "10");
        digits_BigInteger_BigInteger_fail_helper("2", "-1");
        digits_BigInteger_BigInteger_fail_helper("0", "-1");
    }

    private static void digitsPadded_int_int_int_helper(int length, int base, int n, @NotNull String output) {
        aeq(digitsPadded(length, base, n), output);
    }

    private static void digitsPadded_int_int_int_fail_helper(int length, int base, int n) {
        try {
            digitsPadded(length, base, n);
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testDigitsPadded_int_int_int() {
        digitsPadded_int_int_int_helper(0, 2, 0, "[]");
        digitsPadded_int_int_int_helper(0, 3, 0, "[]");
        digitsPadded_int_int_int_helper(0, 57, 0, "[]");
        digitsPadded_int_int_int_helper(0, 2, 1, "[]");
        digitsPadded_int_int_int_helper(0, 3, 1, "[]");
        digitsPadded_int_int_int_helper(0, 57, 1, "[]");
        digitsPadded_int_int_int_helper(0, 2, 10, "[]");
        digitsPadded_int_int_int_helper(0, 3, 10, "[]");
        digitsPadded_int_int_int_helper(0, 57, 10, "[]");
        digitsPadded_int_int_int_helper(0, 2, 187, "[]");
        digitsPadded_int_int_int_helper(0, 3, 187, "[]");
        digitsPadded_int_int_int_helper(0, 57, 187, "[]");
        digitsPadded_int_int_int_helper(1, 2, 0, "[0]");
        digitsPadded_int_int_int_helper(1, 3, 0, "[0]");
        digitsPadded_int_int_int_helper(1, 57, 0, "[0]");
        digitsPadded_int_int_int_helper(1, 2, 1, "[1]");
        digitsPadded_int_int_int_helper(1, 3, 1, "[1]");
        digitsPadded_int_int_int_helper(1, 57, 1, "[1]");
        digitsPadded_int_int_int_helper(1, 2, 10, "[0]");
        digitsPadded_int_int_int_helper(1, 3, 10, "[1]");
        digitsPadded_int_int_int_helper(1, 57, 10, "[10]");
        digitsPadded_int_int_int_helper(1, 2, 187, "[1]");
        digitsPadded_int_int_int_helper(1, 3, 187, "[1]");
        digitsPadded_int_int_int_helper(1, 57, 187, "[16]");
        digitsPadded_int_int_int_helper(2, 2, 0, "[0, 0]");
        digitsPadded_int_int_int_helper(2, 3, 0, "[0, 0]");
        digitsPadded_int_int_int_helper(2, 57, 0, "[0, 0]");
        digitsPadded_int_int_int_helper(2, 2, 1, "[1, 0]");
        digitsPadded_int_int_int_helper(2, 3, 1, "[1, 0]");
        digitsPadded_int_int_int_helper(2, 57, 1, "[1, 0]");
        digitsPadded_int_int_int_helper(2, 2, 10, "[0, 1]");
        digitsPadded_int_int_int_helper(2, 3, 10, "[1, 0]");
        digitsPadded_int_int_int_helper(2, 57, 10, "[10, 0]");
        digitsPadded_int_int_int_helper(2, 2, 187, "[1, 1]");
        digitsPadded_int_int_int_helper(2, 3, 187, "[1, 2]");
        digitsPadded_int_int_int_helper(2, 57, 187, "[16, 3]");
        digitsPadded_int_int_int_helper(8, 2, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 3, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 57, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 2, 1, "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 3, 1, "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 57, 1, "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 2, 10, "[0, 1, 0, 1, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 3, 10, "[1, 0, 1, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 57, 10, "[10, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 2, 187, "[1, 1, 0, 1, 1, 1, 0, 1]");
        digitsPadded_int_int_int_helper(8, 3, 187, "[1, 2, 2, 0, 2, 0, 0, 0]");
        digitsPadded_int_int_int_helper(8, 57, 187, "[16, 3, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_int_int_fail_helper(3, 1, 10);
        digitsPadded_int_int_int_fail_helper(3, 0, 10);
        digitsPadded_int_int_int_fail_helper(3, 2, -1);
        digitsPadded_int_int_int_fail_helper(3, 0, -1);
        digitsPadded_int_int_int_fail_helper(-1, 2, 3);
        digitsPadded_int_int_int_fail_helper(-1, 0, -1);
    }

    private static void digitsPadded_int_BigInteger_BigInteger_helper(
            int length,
            @NotNull String base,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(digitsPadded(length, Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get()), output);
    }

    private static void digitsPadded_int_BigInteger_BigInteger_fail_helper(
            int length,
            @NotNull String base,
            @NotNull String n
    ) {
        try {
            digitsPadded(length, Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testDigitsPadded_int_BigInteger_BigInteger() {
        digitsPadded_int_BigInteger_BigInteger_helper(0, "2", "0", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "3", "0", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "57", "0", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "2", "1", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "3", "1", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "57", "1", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "2", "10", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "3", "10", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "57", "10", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "2", "187", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "3", "187", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(0, "57", "187", "[]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "2", "0", "[0]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "3", "0", "[0]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "57", "0", "[0]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "2", "1", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "3", "1", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "57", "1", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "2", "10", "[0]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "3", "10", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "57", "10", "[10]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "2", "187", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "3", "187", "[1]");
        digitsPadded_int_BigInteger_BigInteger_helper(1, "57", "187", "[16]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "2", "0", "[0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "3", "0", "[0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "57", "0", "[0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "2", "1", "[1, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "3", "1", "[1, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "57", "1", "[1, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "2", "10", "[0, 1]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "3", "10", "[1, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "57", "10", "[10, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "2", "187", "[1, 1]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "3", "187", "[1, 2]");
        digitsPadded_int_BigInteger_BigInteger_helper(2, "57", "187", "[16, 3]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "2", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "3", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "57", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "2", "1", "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "3", "1", "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "57", "1", "[1, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "2", "10", "[0, 1, 0, 1, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "3", "10", "[1, 0, 1, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "57", "10", "[10, 0, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "2", "187", "[1, 1, 0, 1, 1, 1, 0, 1]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "3", "187", "[1, 2, 2, 0, 2, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_helper(8, "57", "187", "[16, 3, 0, 0, 0, 0, 0, 0]");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(3, "1", "10");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(3, "0", "10");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(3, "2", "-1");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(3, "0", "-1");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(-1, "2", "3");
        digitsPadded_int_BigInteger_BigInteger_fail_helper(-1, "0", "-1");
    }

    private static void bigEndianDigits_int_int_helper(int base, int n, @NotNull String output) {
        aeq(bigEndianDigits(base, n), output);
    }

    private static void bigEndianDigits_int_int_fail_helper(int base, int n) {
        try {
            bigEndianDigits(base, n);
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianDigits_int_int() {
        bigEndianDigits_int_int_helper(2, 0, "[]");
        bigEndianDigits_int_int_helper(3, 0, "[]");
        bigEndianDigits_int_int_helper(8, 0, "[]");
        bigEndianDigits_int_int_helper(10, 0, "[]");
        bigEndianDigits_int_int_helper(12, 0, "[]");
        bigEndianDigits_int_int_helper(57, 0, "[]");
        bigEndianDigits_int_int_helper(2, 1, "[1]");
        bigEndianDigits_int_int_helper(3, 1, "[1]");
        bigEndianDigits_int_int_helper(8, 1, "[1]");
        bigEndianDigits_int_int_helper(10, 1, "[1]");
        bigEndianDigits_int_int_helper(12, 1, "[1]");
        bigEndianDigits_int_int_helper(57, 1, "[1]");
        bigEndianDigits_int_int_helper(2, 10, "[1, 0, 1, 0]");
        bigEndianDigits_int_int_helper(3, 10, "[1, 0, 1]");
        bigEndianDigits_int_int_helper(8, 10, "[1, 2]");
        bigEndianDigits_int_int_helper(10, 10, "[1, 0]");
        bigEndianDigits_int_int_helper(12, 10, "[10]");
        bigEndianDigits_int_int_helper(57, 10, "[10]");
        bigEndianDigits_int_int_helper(2, 187, "[1, 0, 1, 1, 1, 0, 1, 1]");
        bigEndianDigits_int_int_helper(3, 187, "[2, 0, 2, 2, 1]");
        bigEndianDigits_int_int_helper(8, 187, "[2, 7, 3]");
        bigEndianDigits_int_int_helper(10, 187, "[1, 8, 7]");
        bigEndianDigits_int_int_helper(12, 187, "[1, 3, 7]");
        bigEndianDigits_int_int_helper(57, 187, "[3, 16]");
        bigEndianDigits_int_int_fail_helper(1, 10);
        bigEndianDigits_int_int_fail_helper(0, 10);
        bigEndianDigits_int_int_fail_helper(2, -1);
        bigEndianDigits_int_int_fail_helper(0, -1);
    }

    private static void bigEndianDigits_BigInteger_BigInteger_helper(
            @NotNull String base,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(bigEndianDigits(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get()), output);
    }

    private static void bigEndianDigits_BigInteger_BigInteger_fail_helper(@NotNull String base, @NotNull String n) {
        try {
            bigEndianDigits(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianDigits_BigInteger_BigInteger() {
        bigEndianDigits_BigInteger_BigInteger_helper("2", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("3", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("8", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("10", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("12", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("57", "0", "[]");
        bigEndianDigits_BigInteger_BigInteger_helper("2", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("3", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("8", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("10", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("12", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("57", "1", "[1]");
        bigEndianDigits_BigInteger_BigInteger_helper("2", "10", "[1, 0, 1, 0]");
        bigEndianDigits_BigInteger_BigInteger_helper("3", "10", "[1, 0, 1]");
        bigEndianDigits_BigInteger_BigInteger_helper("8", "10", "[1, 2]");
        bigEndianDigits_BigInteger_BigInteger_helper("10", "10", "[1, 0]");
        bigEndianDigits_BigInteger_BigInteger_helper("12", "10", "[10]");
        bigEndianDigits_BigInteger_BigInteger_helper("57", "10", "[10]");
        bigEndianDigits_BigInteger_BigInteger_helper("2", "187", "[1, 0, 1, 1, 1, 0, 1, 1]");
        bigEndianDigits_BigInteger_BigInteger_helper("3", "187", "[2, 0, 2, 2, 1]");
        bigEndianDigits_BigInteger_BigInteger_helper("8", "187", "[2, 7, 3]");
        bigEndianDigits_BigInteger_BigInteger_helper("10", "187", "[1, 8, 7]");
        bigEndianDigits_BigInteger_BigInteger_helper("12", "187", "[1, 3, 7]");
        bigEndianDigits_BigInteger_BigInteger_helper("57", "187", "[3, 16]");
        bigEndianDigits_BigInteger_BigInteger_fail_helper("1", "10");
        bigEndianDigits_BigInteger_BigInteger_fail_helper("0", "10");
        bigEndianDigits_BigInteger_BigInteger_fail_helper("2", "-1");
        bigEndianDigits_BigInteger_BigInteger_fail_helper("0", "-1");
    }

    private static void bigEndianDigitsPadded_int_int_int_helper(int length, int base, int n, @NotNull String output) {
        aeq(bigEndianDigitsPadded(length, base, n), output);
    }

    private static void bigEndianDigitsPadded_int_int_int_fail_helper(int length, int base, int n) {
        try {
            bigEndianDigitsPadded(length, base, n);
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_int_int_int() {
        bigEndianDigitsPadded_int_int_int_helper(0, 2, 0, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 3, 0, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 57, 0, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 2, 1, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 3, 1, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 57, 1, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 2, 10, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 3, 10, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 57, 10, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 2, 187, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 3, 187, "[]");
        bigEndianDigitsPadded_int_int_int_helper(0, 57, 187, "[]");
        bigEndianDigitsPadded_int_int_int_helper(1, 2, 0, "[0]");
        bigEndianDigitsPadded_int_int_int_helper(1, 3, 0, "[0]");
        bigEndianDigitsPadded_int_int_int_helper(1, 57, 0, "[0]");
        bigEndianDigitsPadded_int_int_int_helper(1, 2, 1, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 3, 1, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 57, 1, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 2, 10, "[0]");
        bigEndianDigitsPadded_int_int_int_helper(1, 3, 10, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 57, 10, "[10]");
        bigEndianDigitsPadded_int_int_int_helper(1, 2, 187, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 3, 187, "[1]");
        bigEndianDigitsPadded_int_int_int_helper(1, 57, 187, "[16]");
        bigEndianDigitsPadded_int_int_int_helper(2, 2, 0, "[0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(2, 3, 0, "[0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(2, 57, 0, "[0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(2, 2, 1, "[0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 3, 1, "[0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 57, 1, "[0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 2, 10, "[1, 0]");
        bigEndianDigitsPadded_int_int_int_helper(2, 3, 10, "[0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 57, 10, "[0, 10]");
        bigEndianDigitsPadded_int_int_int_helper(2, 2, 187, "[1, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 3, 187, "[2, 1]");
        bigEndianDigitsPadded_int_int_int_helper(2, 57, 187, "[3, 16]");
        bigEndianDigitsPadded_int_int_int_helper(8, 2, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(8, 3, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(8, 57, 0, "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_int_int_helper(8, 2, 1, "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 3, 1, "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 57, 1, "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 2, 10, "[0, 0, 0, 0, 1, 0, 1, 0]");
        bigEndianDigitsPadded_int_int_int_helper(8, 3, 10, "[0, 0, 0, 0, 0, 1, 0, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 57, 10, "[0, 0, 0, 0, 0, 0, 0, 10]");
        bigEndianDigitsPadded_int_int_int_helper(8, 2, 187, "[1, 0, 1, 1, 1, 0, 1, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 3, 187, "[0, 0, 0, 2, 0, 2, 2, 1]");
        bigEndianDigitsPadded_int_int_int_helper(8, 57, 187, "[0, 0, 0, 0, 0, 0, 3, 16]");
        bigEndianDigitsPadded_int_int_int_fail_helper(3, 1, 10);
        bigEndianDigitsPadded_int_int_int_fail_helper(3, 0, 10);
        bigEndianDigitsPadded_int_int_int_fail_helper(3, 2, -1);
        bigEndianDigitsPadded_int_int_int_fail_helper(3, 0, -1);
        bigEndianDigitsPadded_int_int_int_fail_helper(-1, 2, 3);
        bigEndianDigitsPadded_int_int_int_fail_helper(-1, 0, -1);
    }

    private static void bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(
            int length,
            @NotNull String base,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(
                bigEndianDigitsPadded(length, Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get()),
                output
        );
    }

    private static void bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(
            int length,
            @NotNull String base,
            @NotNull String n
    ) {
        try {
            bigEndianDigitsPadded(length, Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException | ArithmeticException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_BigInteger_BigInteger_BigInteger() {
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "2", "0", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "3", "0", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "57", "0", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "2", "1", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "3", "1", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "57", "1", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "2", "10", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "3", "10", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "57", "10", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "2", "187", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "3", "187", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(0, "57", "187", "[]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "2", "0", "[0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "3", "0", "[0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "57", "0", "[0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "2", "1", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "3", "1", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "57", "1", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "2", "10", "[0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "3", "10", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "57", "10", "[10]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "2", "187", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "3", "187", "[1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(1, "57", "187", "[16]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "2", "0", "[0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "3", "0", "[0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "57", "0", "[0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "2", "1", "[0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "3", "1", "[0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "57", "1", "[0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "2", "10", "[1, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "3", "10", "[0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "57", "10", "[0, 10]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "2", "187", "[1, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "3", "187", "[2, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(2, "57", "187", "[3, 16]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "2", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "3", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "57", "0", "[0, 0, 0, 0, 0, 0, 0, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "2", "1", "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "3", "1", "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "57", "1", "[0, 0, 0, 0, 0, 0, 0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "2", "10", "[0, 0, 0, 0, 1, 0, 1, 0]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "3", "10", "[0, 0, 0, 0, 0, 1, 0, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "57", "10", "[0, 0, 0, 0, 0, 0, 0, 10]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "2", "187", "[1, 0, 1, 1, 1, 0, 1, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "3", "187", "[0, 0, 0, 2, 0, 2, 2, 1]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_helper(8, "57", "187", "[0, 0, 0, 0, 0, 0, 3, 16]");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(3, "1", "10");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(3, "0", "10");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(3, "2", "-1");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(3, "0", "-1");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(-1, "2", "3");
        bigEndianDigitsPadded_int_BigInteger_BigInteger_fail_helper(-1, "0", "-1");
    }

    private static void fromDigits_int_Iterable_Integer_helper(
            int base,
            @NotNull String digits,
            @NotNull String output
    ) {
        aeq(fromDigits(base, readIntegerList(digits)), output);
    }

    private static void fromDigits_int_Iterable_Integer_fail_helper(int base, @NotNull String digits) {
        try {
            fromDigits(base, readIntegerListWithNulls(digits));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testFromDigits_int_Iterable_Integer() {
        fromDigits_int_Iterable_Integer_helper(2, "[0, 0]", "0");
        fromDigits_int_Iterable_Integer_helper(2, "[1, 0]", "1");
        fromDigits_int_Iterable_Integer_helper(2, "[1, 0, 1, 1, 1, 0]", "29");
        fromDigits_int_Iterable_Integer_helper(10, "[9, 5, 1, 4, 1, 3]", "314159");
        fromDigits_int_Iterable_Integer_helper(70, "[8, 0, 20, 5, 43]", "1034243008");
        fromDigits_int_Iterable_Integer_helper(70, "[]", "0");
        fromDigits_int_Iterable_Integer_fail_helper(2, "[0, null]");
        fromDigits_int_Iterable_Integer_fail_helper(1, "[1, 2, 3]");
        fromDigits_int_Iterable_Integer_fail_helper(0, "[1, 2, 3]");
        fromDigits_int_Iterable_Integer_fail_helper(-1, "[1, 2, 3]");
        fromDigits_int_Iterable_Integer_fail_helper(10, "[-1, 2, 3]");
        fromDigits_int_Iterable_Integer_fail_helper(10, "[1, 2, 10]");
    }

    private static void fromDigits_BigInteger_Iterable_BigInteger_helper(
            @NotNull String base,
            @NotNull String digits,
            @NotNull String output
    ) {
        aeq(fromDigits(Readers.readBigInteger(base).get(), readBigIntegerList(digits)), output);
    }

    private static void fromDigits_BigInteger_Iterable_BigInteger_fail_helper(
            @NotNull String base,
            @NotNull String digits
    ) {
        try {
            fromDigits(Readers.readBigInteger(base).get(), readBigIntegerListWithNulls(digits));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testFromDigits_BigInteger_Iterable_BigInteger() {
        fromDigits_BigInteger_Iterable_BigInteger_helper("2", "[0, 0]", "0");
        fromDigits_BigInteger_Iterable_BigInteger_helper("2", "[1, 0]", "1");
        fromDigits_BigInteger_Iterable_BigInteger_helper("2", "[1, 0, 1, 1, 1, 0]", "29");
        fromDigits_BigInteger_Iterable_BigInteger_helper("10", "[9, 5, 1, 4, 1, 3]", "314159");
        fromDigits_BigInteger_Iterable_BigInteger_helper("70", "[8, 0, 20, 5, 43]", "1034243008");
        fromDigits_BigInteger_Iterable_BigInteger_helper("70", "[]", "0");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("2", "[0, null]");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("1", "[1, 2, 3]");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("0", "[1, 2, 3]");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("-1", "[1, 2, 3]");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("10", "[-1, 2, 3]");
        fromDigits_BigInteger_Iterable_BigInteger_fail_helper("10", "[1, 2, 10]");
    }

    private static void fromBigEndianDigits_int_Iterable_Integer_helper(
            int base,
            @NotNull String digits,
            @NotNull String output
    ) {
        aeq(fromBigEndianDigits(base, readIntegerList(digits)), output);
    }

    private static void fromBigEndianDigits_int_Iterable_Integer_fail_helper(int base, @NotNull String digits) {
        try {
            fromBigEndianDigits(base, readIntegerListWithNulls(digits));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_int_Iterable_Integer() {
        fromBigEndianDigits_int_Iterable_Integer_helper(2, "[0, 0]", "0");
        fromBigEndianDigits_int_Iterable_Integer_helper(2, "[0, 1]", "1");
        fromBigEndianDigits_int_Iterable_Integer_helper(2, "[0, 1, 1, 1, 0, 1]", "29");
        fromBigEndianDigits_int_Iterable_Integer_helper(10, "[3, 1, 4, 1, 5, 9]", "314159");
        fromBigEndianDigits_int_Iterable_Integer_helper(70, "[43, 5, 20, 0, 8]", "1034243008");
        fromBigEndianDigits_int_Iterable_Integer_helper(70, "[]", "0");
        fromBigEndianDigits_int_Iterable_Integer_fail_helper(1, "[1, 2, 3]");
        fromBigEndianDigits_int_Iterable_Integer_fail_helper(0, "[1, 2, 3]");
        fromBigEndianDigits_int_Iterable_Integer_fail_helper(-1, "[1, 2, 3]");
        fromBigEndianDigits_int_Iterable_Integer_fail_helper(10, "[-1, 2, 3]");
        fromBigEndianDigits_int_Iterable_Integer_fail_helper(10, "[1, 2, 10]");
    }

    private static void fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper(
            @NotNull String base,
            @NotNull String digits,
            @NotNull String output
    ) {
        aeq(fromBigEndianDigits(Readers.readBigInteger(base).get(), readBigIntegerList(digits)), output);
    }

    private static void fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper(
            @NotNull String base,
            @NotNull String digits
    ) {
        try {
            fromBigEndianDigits(Readers.readBigInteger(base).get(), readBigIntegerListWithNulls(digits));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_BigInteger_Iterable_BigInteger() {
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("2", "[0, 0]", "0");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("2", "[0, 1]", "1");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("2", "[0, 1, 1, 1, 0, 1]", "29");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("10", "[3, 1, 4, 1, 5, 9]", "314159");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("70", "[43, 5, 20, 0, 8]", "1034243008");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_helper("70", "[]", "0");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper("1", "[1, 2, 3]");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper("0", "[1, 2, 3]");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper("-1", "[1, 2, 3]");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper("10", "[-1, 2, 3]");
        fromBigEndianDigits_BigInteger_Iterable_BigInteger_fail_helper("10", "[1, 2, 10]");
    }

    private static void toDigit_helper(int i, char c) {
        aeq(toDigit(i), c);
    }

    private static void toDigit_fail_helper(int i) {
        try {
            toDigit(i);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToDigit() {
        toDigit_helper(0, '0');
        toDigit_helper(6, '6');
        toDigit_helper(10, 'A');
        toDigit_helper(20, 'K');
        toDigit_helper(35, 'Z');
        toDigit_fail_helper(-1);
        toDigit_fail_helper(36);
    }

    private static void fromDigit_helper(char c, int i) {
        aeq(fromDigit(c), i);
    }

    private static void fromDigit_fail_helper(char c) {
        try {
            fromDigit(c);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigit() {
        fromDigit_helper('0', 0);
        fromDigit_helper('6', 6);
        fromDigit_helper('A', 10);
        fromDigit_helper('K', 20);
        fromDigit_helper('Z', 35);
        fromDigit_fail_helper(' ');
        fromDigit_fail_helper('a');
    }

    private static void toStringBase_int_int_helper(int base, int n, @NotNull String output) {
        aeq(toStringBase(base, n), output);
    }

    private static void toStringBase_int_int_fail_helper(int base, int n) {
        try {
            toStringBase(base, n);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_int_int() {
        toStringBase_int_int_helper(2, 0, "0");
        toStringBase_int_int_helper(3, 0, "0");
        toStringBase_int_int_helper(4, 0, "0");
        toStringBase_int_int_helper(10, 0, "0");
        toStringBase_int_int_helper(12, 0, "0");
        toStringBase_int_int_helper(16, 0, "0");
        toStringBase_int_int_helper(36, 0, "0");
        toStringBase_int_int_helper(88, 0, "(0)");
        toStringBase_int_int_helper(100, 0, "(0)");
        toStringBase_int_int_helper(2, 524393454, "11111010000011001101111101110");
        toStringBase_int_int_helper(3, 524393454, "1100112201221120210");
        toStringBase_int_int_helper(4, 524393454, "133100121233232");
        toStringBase_int_int_helper(10, 524393454, "524393454");
        toStringBase_int_int_helper(12, 524393454, "127750526");
        toStringBase_int_int_helper(16, 524393454, "1F419BEE");
        toStringBase_int_int_helper(36, 524393454, "8O7KKU");
        toStringBase_int_int_helper(88, 524393454, "(8)(65)(44)(8)(46)");
        toStringBase_int_int_helper(100, 524393454, "(5)(24)(39)(34)(54)");
        toStringBase_int_int_helper(2, -524393454, "-11111010000011001101111101110");
        toStringBase_int_int_helper(3, -524393454, "-1100112201221120210");
        toStringBase_int_int_helper(4, -524393454, "-133100121233232");
        toStringBase_int_int_helper(10, -524393454, "-524393454");
        toStringBase_int_int_helper(12, -524393454, "-127750526");
        toStringBase_int_int_helper(16, -524393454, "-1F419BEE");
        toStringBase_int_int_helper(36, -524393454, "-8O7KKU");
        toStringBase_int_int_helper(88, -524393454, "-(8)(65)(44)(8)(46)");
        toStringBase_int_int_helper(100, -524393454, "-(5)(24)(39)(34)(54)");
        toStringBase_int_int_fail_helper(1, 524393454);
        toStringBase_int_int_fail_helper(0, 524393454);
    }

    private static void toStringBase_BigInteger_BigInteger_helper(
            @NotNull String base,
            @NotNull String n,
            @NotNull String output
    ) {
        aeq(toStringBase(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get()), output);
    }

    private static void toStringBase_BigInteger_BigInteger_fail_helper(@NotNull String base, @NotNull String n) {
        try {
            toStringBase(Readers.readBigInteger(base).get(), Readers.readBigInteger(n).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_BigInteger_BigInteger() {
        toStringBase_BigInteger_BigInteger_helper("2", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("3", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("4", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("10", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("12", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("16", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("36", "0", "0");
        toStringBase_BigInteger_BigInteger_helper("88", "0", "(0)");
        toStringBase_BigInteger_BigInteger_helper("100", "0", "(0)");
        toStringBase_BigInteger_BigInteger_helper("2", "524393454", "11111010000011001101111101110");
        toStringBase_BigInteger_BigInteger_helper("3", "524393454", "1100112201221120210");
        toStringBase_BigInteger_BigInteger_helper("4", "524393454", "133100121233232");
        toStringBase_BigInteger_BigInteger_helper("10", "524393454", "524393454");
        toStringBase_BigInteger_BigInteger_helper("12", "524393454", "127750526");
        toStringBase_BigInteger_BigInteger_helper("16", "524393454", "1F419BEE");
        toStringBase_BigInteger_BigInteger_helper("36", "524393454", "8O7KKU");
        toStringBase_BigInteger_BigInteger_helper("88", "524393454", "(8)(65)(44)(8)(46)");
        toStringBase_BigInteger_BigInteger_helper("100", "524393454", "(5)(24)(39)(34)(54)");
        toStringBase_BigInteger_BigInteger_helper("2", "-524393454", "-11111010000011001101111101110");
        toStringBase_BigInteger_BigInteger_helper("3", "-524393454", "-1100112201221120210");
        toStringBase_BigInteger_BigInteger_helper("4", "-524393454", "-133100121233232");
        toStringBase_BigInteger_BigInteger_helper("10", "-524393454", "-524393454");
        toStringBase_BigInteger_BigInteger_helper("12", "-524393454", "-127750526");
        toStringBase_BigInteger_BigInteger_helper("16", "-524393454", "-1F419BEE");
        toStringBase_BigInteger_BigInteger_helper("36", "-524393454", "-8O7KKU");
        toStringBase_BigInteger_BigInteger_helper("88", "-524393454", "-(8)(65)(44)(8)(46)");
        toStringBase_BigInteger_BigInteger_helper("100", "-524393454", "-(5)(24)(39)(34)(54)");
        toStringBase_BigInteger_BigInteger_fail_helper("1", "524393454");
        toStringBase_BigInteger_BigInteger_fail_helper("0", "524393454");
    }

    private static void fromStringBase_int_String_helper(int base, @NotNull String s, @NotNull String output) {
        aeq(fromStringBase(base, s), output);
    }

    private static void fromStringBase_int_String_fail_helper(int base, @NotNull String s) {
        try {
            fromStringBase(base, s);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_int_String() {
        fromStringBase_int_String_helper(2, "", "0");
        fromStringBase_int_String_helper(3, "", "0");
        fromStringBase_int_String_helper(4, "", "0");
        fromStringBase_int_String_helper(10, "", "0");
        fromStringBase_int_String_helper(12, "", "0");
        fromStringBase_int_String_helper(16, "", "0");
        fromStringBase_int_String_helper(36, "", "0");
        fromStringBase_int_String_helper(88, "", "0");
        fromStringBase_int_String_helper(100, "", "0");

        fromStringBase_int_String_helper(2, "0", "0");
        fromStringBase_int_String_helper(3, "0", "0");
        fromStringBase_int_String_helper(4, "0", "0");
        fromStringBase_int_String_helper(10, "0", "0");
        fromStringBase_int_String_helper(12, "0", "0");
        fromStringBase_int_String_helper(16, "0", "0");
        fromStringBase_int_String_helper(36, "0", "0");
        fromStringBase_int_String_helper(88, "(0)", "0");
        fromStringBase_int_String_helper(100, "(0)", "0");

        fromStringBase_int_String_helper(2, "00", "0");
        fromStringBase_int_String_helper(3, "00", "0");
        fromStringBase_int_String_helper(4, "00", "0");
        fromStringBase_int_String_helper(10, "00", "0");
        fromStringBase_int_String_helper(12, "00", "0");
        fromStringBase_int_String_helper(16, "00", "0");
        fromStringBase_int_String_helper(36, "00", "0");
        fromStringBase_int_String_helper(88, "(0)(0)", "0");
        fromStringBase_int_String_helper(100, "(0)(0)", "0");

        fromStringBase_int_String_helper(2, "-0", "0");
        fromStringBase_int_String_helper(3, "-0", "0");
        fromStringBase_int_String_helper(4, "-0", "0");
        fromStringBase_int_String_helper(10, "-0", "0");
        fromStringBase_int_String_helper(12, "-0", "0");
        fromStringBase_int_String_helper(16, "-0", "0");
        fromStringBase_int_String_helper(36, "-0", "0");
        fromStringBase_int_String_helper(88, "-(0)", "0");
        fromStringBase_int_String_helper(100, "-(0)", "0");

        fromStringBase_int_String_helper(2, "-00", "0");
        fromStringBase_int_String_helper(3, "-00", "0");
        fromStringBase_int_String_helper(4, "-00", "0");
        fromStringBase_int_String_helper(10, "-00", "0");
        fromStringBase_int_String_helper(12, "-00", "0");
        fromStringBase_int_String_helper(16, "-00", "0");
        fromStringBase_int_String_helper(36, "-00", "0");
        fromStringBase_int_String_helper(88, "-(0)(0)", "0");
        fromStringBase_int_String_helper(100, "-(0)(0)", "0");

        fromStringBase_int_String_helper(2, "11111010000011001101111101110", "524393454");
        fromStringBase_int_String_helper(3, "1100112201221120210", "524393454");
        fromStringBase_int_String_helper(4, "133100121233232", "524393454");
        fromStringBase_int_String_helper(10, "524393454", "524393454");
        fromStringBase_int_String_helper(12, "127750526", "524393454");
        fromStringBase_int_String_helper(16, "1F419BEE", "524393454");
        fromStringBase_int_String_helper(36, "8O7KKU", "524393454");
        fromStringBase_int_String_helper(88, "(8)(65)(44)(8)(46)", "524393454");
        fromStringBase_int_String_helper(100, "(5)(24)(39)(34)(54)", "524393454");

        fromStringBase_int_String_helper(2, "00011111010000011001101111101110", "524393454");
        fromStringBase_int_String_helper(3, "0001100112201221120210", "524393454");
        fromStringBase_int_String_helper(4, "000133100121233232", "524393454");
        fromStringBase_int_String_helper(10, "000524393454", "524393454");
        fromStringBase_int_String_helper(12, "000127750526", "524393454");
        fromStringBase_int_String_helper(16, "0001F419BEE", "524393454");
        fromStringBase_int_String_helper(36, "0008O7KKU", "524393454");
        fromStringBase_int_String_helper(88, "(0)(0)(0)(8)(65)(44)(8)(46)", "524393454");
        fromStringBase_int_String_helper(100, "(0)(0)(0)(5)(24)(39)(34)(54)", "524393454");

        fromStringBase_int_String_helper(2, "-11111010000011001101111101110", "-524393454");
        fromStringBase_int_String_helper(3, "-1100112201221120210", "-524393454");
        fromStringBase_int_String_helper(4, "-133100121233232", "-524393454");
        fromStringBase_int_String_helper(10, "-524393454", "-524393454");
        fromStringBase_int_String_helper(12, "-127750526", "-524393454");
        fromStringBase_int_String_helper(16, "-1F419BEE", "-524393454");
        fromStringBase_int_String_helper(36, "-8O7KKU", "-524393454");
        fromStringBase_int_String_helper(88, "-(8)(65)(44)(8)(46)", "-524393454");
        fromStringBase_int_String_helper(100, "-(5)(24)(39)(34)(54)", "-524393454");

        fromStringBase_int_String_helper(2, "-00011111010000011001101111101110", "-524393454");
        fromStringBase_int_String_helper(3, "-0001100112201221120210", "-524393454");
        fromStringBase_int_String_helper(4, "-000133100121233232", "-524393454");
        fromStringBase_int_String_helper(10, "-000524393454", "-524393454");
        fromStringBase_int_String_helper(12, "-000127750526", "-524393454");
        fromStringBase_int_String_helper(16, "-0001F419BEE", "-524393454");
        fromStringBase_int_String_helper(36, "-0008O7KKU", "-524393454");
        fromStringBase_int_String_helper(88, "-(0)(0)(0)(8)(65)(44)(8)(46)", "-524393454");
        fromStringBase_int_String_helper(100, "-(0)(0)(0)(5)(24)(39)(34)(54)", "-524393454");

        fromStringBase_int_String_fail_helper(1, "");
        fromStringBase_int_String_fail_helper(0, "");
        fromStringBase_int_String_fail_helper(2, "-");
        fromStringBase_int_String_fail_helper(2, "3");
        fromStringBase_int_String_fail_helper(2, "*");
        fromStringBase_int_String_fail_helper(100, "12");
        fromStringBase_int_String_fail_helper(100, "(-12)");
        fromStringBase_int_String_fail_helper(100, "(3F)");
        fromStringBase_int_String_fail_helper(100, "-");
        fromStringBase_int_String_fail_helper(100, "()");
        fromStringBase_int_String_fail_helper(100, "()()");
        fromStringBase_int_String_fail_helper(100, "(00)");
        fromStringBase_int_String_fail_helper(100, "(02)");
        fromStringBase_int_String_fail_helper(100, "(2)()");
    }

    private static void fromStringBase_BigInteger_String_helper(
            @NotNull String base,
            @NotNull String s,
            @NotNull String output
    ) {
        aeq(fromStringBase(Readers.readBigInteger(base).get(), s), output);
    }

    private static void fromStringBase_BigInteger_String_fail_helper(@NotNull String base, @NotNull String s) {
        try {
            fromStringBase(Readers.readBigInteger(base).get(), s);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_BigInteger_String() {
        fromStringBase_BigInteger_String_helper("2", "", "0");
        fromStringBase_BigInteger_String_helper("3", "", "0");
        fromStringBase_BigInteger_String_helper("4", "", "0");
        fromStringBase_BigInteger_String_helper("10", "", "0");
        fromStringBase_BigInteger_String_helper("12", "", "0");
        fromStringBase_BigInteger_String_helper("16", "", "0");
        fromStringBase_BigInteger_String_helper("36", "", "0");
        fromStringBase_BigInteger_String_helper("88", "", "0");
        fromStringBase_BigInteger_String_helper("100", "", "0");

        fromStringBase_BigInteger_String_helper("2", "0", "0");
        fromStringBase_BigInteger_String_helper("3", "0", "0");
        fromStringBase_BigInteger_String_helper("4", "0", "0");
        fromStringBase_BigInteger_String_helper("10", "0", "0");
        fromStringBase_BigInteger_String_helper("12", "0", "0");
        fromStringBase_BigInteger_String_helper("16", "0", "0");
        fromStringBase_BigInteger_String_helper("36", "0", "0");
        fromStringBase_BigInteger_String_helper("88", "(0)", "0");
        fromStringBase_BigInteger_String_helper("100", "(0)", "0");

        fromStringBase_BigInteger_String_helper("2", "00", "0");
        fromStringBase_BigInteger_String_helper("3", "00", "0");
        fromStringBase_BigInteger_String_helper("4", "00", "0");
        fromStringBase_BigInteger_String_helper("10", "00", "0");
        fromStringBase_BigInteger_String_helper("12", "00", "0");
        fromStringBase_BigInteger_String_helper("16", "00", "0");
        fromStringBase_BigInteger_String_helper("36", "00", "0");
        fromStringBase_BigInteger_String_helper("88", "(0)(0)", "0");
        fromStringBase_BigInteger_String_helper("100", "(0)(0)", "0");

        fromStringBase_BigInteger_String_helper("2", "-0", "0");
        fromStringBase_BigInteger_String_helper("3", "-0", "0");
        fromStringBase_BigInteger_String_helper("4", "-0", "0");
        fromStringBase_BigInteger_String_helper("10", "-0", "0");
        fromStringBase_BigInteger_String_helper("12", "-0", "0");
        fromStringBase_BigInteger_String_helper("16", "-0", "0");
        fromStringBase_BigInteger_String_helper("36", "-0", "0");
        fromStringBase_BigInteger_String_helper("88", "-(0)", "0");
        fromStringBase_BigInteger_String_helper("100", "-(0)", "0");

        fromStringBase_BigInteger_String_helper("2", "-00", "0");
        fromStringBase_BigInteger_String_helper("3", "-00", "0");
        fromStringBase_BigInteger_String_helper("4", "-00", "0");
        fromStringBase_BigInteger_String_helper("10", "-00", "0");
        fromStringBase_BigInteger_String_helper("12", "-00", "0");
        fromStringBase_BigInteger_String_helper("16", "-00", "0");
        fromStringBase_BigInteger_String_helper("36", "-00", "0");
        fromStringBase_BigInteger_String_helper("88", "-(0)(0)", "0");
        fromStringBase_BigInteger_String_helper("100", "-(0)(0)", "0");

        fromStringBase_BigInteger_String_helper("2", "11111010000011001101111101110", "524393454");
        fromStringBase_BigInteger_String_helper("3", "1100112201221120210", "524393454");
        fromStringBase_BigInteger_String_helper("4", "133100121233232", "524393454");
        fromStringBase_BigInteger_String_helper("10", "524393454", "524393454");
        fromStringBase_BigInteger_String_helper("12", "127750526", "524393454");
        fromStringBase_BigInteger_String_helper("16", "1F419BEE", "524393454");
        fromStringBase_BigInteger_String_helper("36", "8O7KKU", "524393454");
        fromStringBase_BigInteger_String_helper("88", "(8)(65)(44)(8)(46)", "524393454");
        fromStringBase_BigInteger_String_helper("100", "(5)(24)(39)(34)(54)", "524393454");

        fromStringBase_BigInteger_String_helper("2", "00011111010000011001101111101110", "524393454");
        fromStringBase_BigInteger_String_helper("3", "0001100112201221120210", "524393454");
        fromStringBase_BigInteger_String_helper("4", "000133100121233232", "524393454");
        fromStringBase_BigInteger_String_helper("10", "000524393454", "524393454");
        fromStringBase_BigInteger_String_helper("12", "000127750526", "524393454");
        fromStringBase_BigInteger_String_helper("16", "0001F419BEE", "524393454");
        fromStringBase_BigInteger_String_helper("36", "0008O7KKU", "524393454");
        fromStringBase_BigInteger_String_helper("88", "(0)(0)(0)(8)(65)(44)(8)(46)", "524393454");
        fromStringBase_BigInteger_String_helper("100", "(0)(0)(0)(5)(24)(39)(34)(54)", "524393454");

        fromStringBase_BigInteger_String_helper("2", "-11111010000011001101111101110", "-524393454");
        fromStringBase_BigInteger_String_helper("3", "-1100112201221120210", "-524393454");
        fromStringBase_BigInteger_String_helper("4", "-133100121233232", "-524393454");
        fromStringBase_BigInteger_String_helper("10", "-524393454", "-524393454");
        fromStringBase_BigInteger_String_helper("12", "-127750526", "-524393454");
        fromStringBase_BigInteger_String_helper("16", "-1F419BEE", "-524393454");
        fromStringBase_BigInteger_String_helper("36", "-8O7KKU", "-524393454");
        fromStringBase_BigInteger_String_helper("88", "-(8)(65)(44)(8)(46)", "-524393454");
        fromStringBase_BigInteger_String_helper("100", "-(5)(24)(39)(34)(54)", "-524393454");

        fromStringBase_BigInteger_String_helper("2", "-00011111010000011001101111101110", "-524393454");
        fromStringBase_BigInteger_String_helper("3", "-0001100112201221120210", "-524393454");
        fromStringBase_BigInteger_String_helper("4", "-000133100121233232", "-524393454");
        fromStringBase_BigInteger_String_helper("10", "-000524393454", "-524393454");
        fromStringBase_BigInteger_String_helper("12", "-000127750526", "-524393454");
        fromStringBase_BigInteger_String_helper("16", "-0001F419BEE", "-524393454");
        fromStringBase_BigInteger_String_helper("36", "-0008O7KKU", "-524393454");
        fromStringBase_BigInteger_String_helper("88", "-(0)(0)(0)(8)(65)(44)(8)(46)", "-524393454");
        fromStringBase_BigInteger_String_helper("100", "-(0)(0)(0)(5)(24)(39)(34)(54)", "-524393454");

        fromStringBase_BigInteger_String_fail_helper("1", "");
        fromStringBase_BigInteger_String_fail_helper("0", "");
        fromStringBase_BigInteger_String_fail_helper("2", "-");
        fromStringBase_BigInteger_String_fail_helper("2", "3");
        fromStringBase_BigInteger_String_fail_helper("2", "*");
        fromStringBase_BigInteger_String_fail_helper("100", "12");
        fromStringBase_BigInteger_String_fail_helper("100", "(-12)");
        fromStringBase_BigInteger_String_fail_helper("100", "(3F)");
        fromStringBase_BigInteger_String_fail_helper("100", "-");
        fromStringBase_BigInteger_String_fail_helper("100", "()");
        fromStringBase_BigInteger_String_fail_helper("100", "()()");
        fromStringBase_BigInteger_String_fail_helper("100", "(00)");
        fromStringBase_BigInteger_String_fail_helper("100", "(02)");
        fromStringBase_BigInteger_String_fail_helper("100", "(2)()");
    }

    private static void logarithmicMux_helper(@NotNull String x, @NotNull String y, @NotNull String output) {
        aeq(logarithmicMux(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get()), output);
    }

    private static void logarithmicMux_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            logarithmicMux(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testLogarithmicMux() {
        logarithmicMux_helper("0", "0", "0");
        logarithmicMux_helper("0", "1", "1");
        logarithmicMux_helper("1", "0", "2");
        logarithmicMux_helper("5", "10", "11263");
        logarithmicMux_helper("10", "5", "671");
        logarithmicMux_helper("500000", "0", "1000000");

        logarithmicMux_fail_helper("-5", "5");
        logarithmicMux_fail_helper("5", "-5");
        logarithmicMux_fail_helper("-5", "-5");
    }

    private static void logarithmicDemux_helper(@NotNull String n, @NotNull String x, @NotNull String y) {
        Pair<BigInteger, BigInteger> p = logarithmicDemux(Readers.readBigInteger(n).get());
        aeq(p.a, x);
        aeq(p.b, y);
    }

    private static void logarithmicDemux_fail_helper(@NotNull String n) {
        try {
            logarithmicDemux(Readers.readBigInteger(n).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testLogarithmicDemux() {
        logarithmicDemux_helper("0", "0", "0");
        logarithmicDemux_helper("1", "0", "1");
        logarithmicDemux_helper("2", "1", "0");
        logarithmicDemux_helper("11263", "5", "10");
        logarithmicDemux_helper("671", "10", "5");
        logarithmicDemux_helper("1000000", "500000", "0");

        logarithmicDemux_fail_helper("-5");
    }

    private static void squareRootMux_helper(@NotNull String x, @NotNull String y, @NotNull String output) {
        aeq(squareRootMux(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get()), output);
    }

    private static void squareRootMux_fail_helper(@NotNull String x, @NotNull String y) {
        try {
            squareRootMux(Readers.readBigInteger(x).get(), Readers.readBigInteger(y).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootMux() {
        squareRootMux_helper("0", "0", "0");
        squareRootMux_helper("0", "1", "1");
        squareRootMux_helper("1", "0", "2");
        squareRootMux_helper("5", "10", "538");
        squareRootMux_helper("10", "5", "101");
        squareRootMux_helper("7680", "76", "1000000");
        squareRootMux_fail_helper("-5", "5");
        squareRootMux_fail_helper("5", "-5");
        squareRootMux_fail_helper("-5", "-5");
    }

    private static void squareRootDemux_helper(@NotNull String n, @NotNull String x, @NotNull String y) {
        Pair<BigInteger, BigInteger> p = squareRootDemux(Readers.readBigInteger(n).get());
        aeq(p.a, x);
        aeq(p.b, y);
    }

    private static void squareRootDemux_fail_helper(@NotNull String n) {
        try {
            squareRootDemux(Readers.readBigInteger(n).get());
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootDemux() {
        squareRootDemux_helper("0", "0", "0");
        squareRootDemux_helper("1", "0", "1");
        squareRootDemux_helper("2", "1", "0");
        squareRootDemux_helper("538", "5", "10");
        squareRootDemux_helper("101", "10", "5");
        squareRootDemux_helper("1000000", "7680", "76");
        squareRootDemux_fail_helper("-5");
    }

    @Test
    public void testMux() {
        aeq(mux(readBigIntegerList("[]")), 0);
        aeq(mux(readBigIntegerList("[0]")), 0);
        aeq(mux(readBigIntegerList("[1]")), 1);
        aeq(mux(readBigIntegerList("[2]")), 2);
        aeq(mux(readBigIntegerList("[0, 0]")), 0);
        aeq(mux(readBigIntegerList("[0, 1]")), 1);
        aeq(mux(readBigIntegerList("[1, 0]")), 2);
        aeq(mux(readBigIntegerList("[5, 10]")), 102);
        aeq(mux(readBigIntegerList("[10, 5]")), 153);
        aeq(mux(readBigIntegerList("[784, 904]")), 1000000);
        aeq(mux(readBigIntegerList("[0, 0, 0]")), 0);
        aeq(mux(readBigIntegerList("[10, 10, 10]")), 3640);
        aeq(mux(readBigIntegerList("[48, 96, 76]")), 1000000);
        aeq(mux(readBigIntegerList("[1, 2, 3, 4]")), 362);
        aeq(mux(readBigIntegerList("[3, 2, 2, 3, 0, 2, 0, 0, 0, 0]")), 1000000);
        try {
            mux(readBigIntegerList("[1, 2, -3]"));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            mux(readBigIntegerListWithNulls("[1, null, 2]"));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testDemux() {
        aeq(demux(0, BigInteger.ZERO), "[]");
        aeq(demux(1, BigInteger.ZERO), "[0]");
        aeq(demux(1, BigInteger.ONE), "[1]");
        aeq(demux(1, TWO), "[2]");
        aeq(demux(2, BigInteger.ZERO), "[0, 0]");
        aeq(demux(2, BigInteger.ONE), "[0, 1]");
        aeq(demux(2, TWO), "[1, 0]");
        aeq(demux(2, BigInteger.valueOf(1000000)), "[784, 904]");
        aeq(demux(3, BigInteger.ZERO), "[0, 0, 0]");
        aeq(demux(3, BigInteger.valueOf(3640)), "[10, 10, 10]");
        aeq(demux(3, BigInteger.valueOf(1000000)), "[48, 96, 76]");
        aeq(demux(4, BigInteger.valueOf(362)), "[1, 2, 3, 4]");
        aeq(demux(10, BigInteger.valueOf(1000000)), "[3, 2, 2, 3, 0, 2, 0, 0, 0, 0]");
        try {
            demux(0, BigInteger.ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(-2, BigInteger.ZERO);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(-2, BigInteger.ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            demux(2, BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static @NotNull List<Boolean> readBooleanList(@NotNull String s) {
        return Readers.readList(Readers::readBoolean).apply(s).get();
    }

    private static @NotNull List<Boolean> readBooleanListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBoolean).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readBigInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBigInteger).apply(s).get();
    }
}
