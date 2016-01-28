package mho.wheels.numberUtils;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mho.wheels.numberUtils.IntegerUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.aeqit;
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
        aeqit(bits(input), output);
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
        } catch (ArithmeticException ignored) {}
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
        } catch (ArithmeticException ignored) {}
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
        } catch (ArithmeticException ignored) {}
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
        } catch (ArithmeticException ignored) {}
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

    @Test
    public void testDigits_int_int() {
        aeqit(digits(2, 0), "[]");
        aeqit(digits(3, 0), "[]");
        aeqit(digits(8, 0), "[]");
        aeqit(digits(10, 0), "[]");
        aeqit(digits(12, 0), "[]");
        aeqit(digits(57, 0), "[]");
        aeqit(digits(2, 1), "[1]");
        aeqit(digits(3, 1), "[1]");
        aeqit(digits(8, 1), "[1]");
        aeqit(digits(10, 1), "[1]");
        aeqit(digits(12, 1), "[1]");
        aeqit(digits(57, 1), "[1]");
        aeqit(digits(2, 10), "[0, 1, 0, 1]");
        aeqit(digits(3, 10), "[1, 0, 1]");
        aeqit(digits(8, 10), "[2, 1]");
        aeqit(digits(10, 10), "[0, 1]");
        aeqit(digits(12, 10), "[10]");
        aeqit(digits(57, 10), "[10]");
        aeqit(digits(2, 187), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeqit(digits(3, 187), "[1, 2, 2, 0, 2]");
        aeqit(digits(8, 187), "[3, 7, 2]");
        aeqit(digits(10, 187), "[7, 8, 1]");
        aeqit(digits(12, 187), "[7, 3, 1]");
        aeqit(digits(57, 187), "[16, 3]");
        try {
            digits(1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digits(0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigits_BigInteger_BigInteger() {
        aeqit(digits(TWO, BigInteger.ZERO), "[]");
        aeqit(digits(BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeqit(digits(BigInteger.valueOf(8), BigInteger.ZERO), "[]");
        aeqit(digits(BigInteger.valueOf(10), BigInteger.ZERO), "[]");
        aeqit(digits(BigInteger.valueOf(12), BigInteger.ZERO), "[]");
        aeqit(digits(BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeqit(digits(TWO, BigInteger.ONE), "[1]");
        aeqit(digits(BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeqit(digits(BigInteger.valueOf(8), BigInteger.ONE), "[1]");
        aeqit(digits(BigInteger.valueOf(10), BigInteger.ONE), "[1]");
        aeqit(digits(BigInteger.valueOf(12), BigInteger.ONE), "[1]");
        aeqit(digits(BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeqit(digits(TWO, BigInteger.valueOf(10)), "[0, 1, 0, 1]");
        aeqit(digits(BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0, 1]");
        aeqit(digits(BigInteger.valueOf(8), BigInteger.valueOf(10)), "[2, 1]");
        aeqit(digits(BigInteger.valueOf(10), BigInteger.valueOf(10)), "[0, 1]");
        aeqit(digits(BigInteger.valueOf(12), BigInteger.valueOf(10)), "[10]");
        aeqit(digits(BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeqit(digits(TWO, BigInteger.valueOf(187)), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeqit(digits(BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1, 2, 2, 0, 2]");
        aeqit(digits(BigInteger.valueOf(8), BigInteger.valueOf(187)), "[3, 7, 2]");
        aeqit(digits(BigInteger.valueOf(10), BigInteger.valueOf(187)), "[7, 8, 1]");
        aeqit(digits(BigInteger.valueOf(12), BigInteger.valueOf(187)), "[7, 3, 1]");
        aeqit(digits(BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16, 3]");
        try {
            digits(BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digits(TWO, NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digits(BigInteger.ZERO, NEGATIVE_ONE);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigitsPadded_int_int_int() {
        aeqit(digitsPadded(0, 2, 0), "[]");
        aeqit(digitsPadded(0, 3, 0), "[]");
        aeqit(digitsPadded(0, 57, 0), "[]");
        aeqit(digitsPadded(0, 2, 1), "[]");
        aeqit(digitsPadded(0, 3, 1), "[]");
        aeqit(digitsPadded(0, 57, 1), "[]");
        aeqit(digitsPadded(0, 2, 10), "[]");
        aeqit(digitsPadded(0, 3, 10), "[]");
        aeqit(digitsPadded(0, 57, 10), "[]");
        aeqit(digitsPadded(0, 2, 187), "[]");
        aeqit(digitsPadded(0, 3, 187), "[]");
        aeqit(digitsPadded(0, 57, 187), "[]");
        aeqit(digitsPadded(1, 2, 0), "[0]");
        aeqit(digitsPadded(1, 3, 0), "[0]");
        aeqit(digitsPadded(1, 57, 0), "[0]");
        aeqit(digitsPadded(1, 2, 1), "[1]");
        aeqit(digitsPadded(1, 3, 1), "[1]");
        aeqit(digitsPadded(1, 57, 1), "[1]");
        aeqit(digitsPadded(1, 2, 10), "[0]");
        aeqit(digitsPadded(1, 3, 10), "[1]");
        aeqit(digitsPadded(1, 57, 10), "[10]");
        aeqit(digitsPadded(1, 2, 187), "[1]");
        aeqit(digitsPadded(1, 3, 187), "[1]");
        aeqit(digitsPadded(1, 57, 187), "[16]");
        aeqit(digitsPadded(2, 2, 0), "[0, 0]");
        aeqit(digitsPadded(2, 3, 0), "[0, 0]");
        aeqit(digitsPadded(2, 57, 0), "[0, 0]");
        aeqit(digitsPadded(2, 2, 1), "[1, 0]");
        aeqit(digitsPadded(2, 3, 1), "[1, 0]");
        aeqit(digitsPadded(2, 57, 1), "[1, 0]");
        aeqit(digitsPadded(2, 2, 10), "[0, 1]");
        aeqit(digitsPadded(2, 3, 10), "[1, 0]");
        aeqit(digitsPadded(2, 57, 10), "[10, 0]");
        aeqit(digitsPadded(2, 2, 187), "[1, 1]");
        aeqit(digitsPadded(2, 3, 187), "[1, 2]");
        aeqit(digitsPadded(2, 57, 187), "[16, 3]");
        aeqit(digitsPadded(8, 2, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 3, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 57, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 2, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 3, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 57, 1), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 2, 10), "[0, 1, 0, 1, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 3, 10), "[1, 0, 1, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 57, 10), "[10, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, 2, 187), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeqit(digitsPadded(8, 3, 187), "[1, 2, 2, 0, 2, 0, 0, 0]");
        aeqit(digitsPadded(8, 57, 187), "[16, 3, 0, 0, 0, 0, 0, 0]");
        try {
            digitsPadded(3, 1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, 0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, 2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digitsPadded(3, 0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, 2, 3);
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, 0, -1);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDigitsPadded_BigInteger_BigInteger_BigInteger() {
        aeqit(digitsPadded(0, TWO, BigInteger.ZERO), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeqit(digitsPadded(0, TWO, BigInteger.ONE), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(3), BigInteger.ONE), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(57), BigInteger.ONE), "[]");
        aeqit(digitsPadded(0, TWO, BigInteger.valueOf(10)), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[]");
        aeqit(digitsPadded(0, TWO, BigInteger.valueOf(187)), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[]");
        aeqit(digitsPadded(0, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[]");
        aeqit(digitsPadded(1, TWO, BigInteger.ZERO), "[0]");
        aeqit(digitsPadded(1, BigInteger.valueOf(3), BigInteger.ZERO), "[0]");
        aeqit(digitsPadded(1, BigInteger.valueOf(57), BigInteger.ZERO), "[0]");
        aeqit(digitsPadded(1, TWO, BigInteger.ONE), "[1]");
        aeqit(digitsPadded(1, BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeqit(digitsPadded(1, BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeqit(digitsPadded(1, TWO, BigInteger.valueOf(10)), "[0]");
        aeqit(digitsPadded(1, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1]");
        aeqit(digitsPadded(1, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeqit(digitsPadded(1, TWO, BigInteger.valueOf(187)), "[1]");
        aeqit(digitsPadded(1, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1]");
        aeqit(digitsPadded(1, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16]");
        aeqit(digitsPadded(2, TWO, BigInteger.ZERO), "[0, 0]");
        aeqit(digitsPadded(2, BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0]");
        aeqit(digitsPadded(2, BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0]");
        aeqit(digitsPadded(2, TWO, BigInteger.ONE), "[1, 0]");
        aeqit(digitsPadded(2, BigInteger.valueOf(3), BigInteger.ONE), "[1, 0]");
        aeqit(digitsPadded(2, BigInteger.valueOf(57), BigInteger.ONE), "[1, 0]");
        aeqit(digitsPadded(2, TWO, BigInteger.valueOf(10)), "[0, 1]");
        aeqit(digitsPadded(2, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0]");
        aeqit(digitsPadded(2, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10, 0]");
        aeqit(digitsPadded(2, TWO, BigInteger.valueOf(187)), "[1, 1]");
        aeqit(digitsPadded(2, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1, 2]");
        aeqit(digitsPadded(2, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16, 3]");
        aeqit(digitsPadded(8, TWO, BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, TWO, BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(3), BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(57), BigInteger.ONE), "[1, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, TWO, BigInteger.valueOf(10)), "[0, 1, 0, 1, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0, 1, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10, 0, 0, 0, 0, 0, 0, 0]");
        aeqit(digitsPadded(8, TWO, BigInteger.valueOf(187)), "[1, 1, 0, 1, 1, 1, 0, 1]");
        aeqit(digitsPadded(8, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1, 2, 2, 0, 2, 0, 0, 0]");
        aeqit(digitsPadded(8, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16, 3, 0, 0, 0, 0, 0, 0]");
        try {
            digitsPadded(3, BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(3, TWO, NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            digitsPadded(3, BigInteger.ZERO, NEGATIVE_ONE);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, TWO, BigInteger.valueOf(3));
        } catch (IllegalArgumentException ignored) {}
        try {
            digitsPadded(-1, BigInteger.ZERO, NEGATIVE_ONE);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigits_int_int() {
        aeq(bigEndianDigits(2, 0), "[]");
        aeq(bigEndianDigits(3, 0), "[]");
        aeq(bigEndianDigits(8, 0), "[]");
        aeq(bigEndianDigits(10, 0), "[]");
        aeq(bigEndianDigits(12, 0), "[]");
        aeq(bigEndianDigits(57, 0), "[]");
        aeq(bigEndianDigits(2, 1), "[1]");
        aeq(bigEndianDigits(3, 1), "[1]");
        aeq(bigEndianDigits(8, 1), "[1]");
        aeq(bigEndianDigits(10, 1), "[1]");
        aeq(bigEndianDigits(12, 1), "[1]");
        aeq(bigEndianDigits(57, 1), "[1]");
        aeq(bigEndianDigits(2, 10), "[1, 0, 1, 0]");
        aeq(bigEndianDigits(3, 10), "[1, 0, 1]");
        aeq(bigEndianDigits(8, 10), "[1, 2]");
        aeq(bigEndianDigits(10, 10), "[1, 0]");
        aeq(bigEndianDigits(12, 10), "[10]");
        aeq(bigEndianDigits(57, 10), "[10]");
        aeq(bigEndianDigits(2, 187), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigits(3, 187), "[2, 0, 2, 2, 1]");
        aeq(bigEndianDigits(8, 187), "[2, 7, 3]");
        aeq(bigEndianDigits(10, 187), "[1, 8, 7]");
        aeq(bigEndianDigits(12, 187), "[1, 3, 7]");
        aeq(bigEndianDigits(57, 187), "[3, 16]");
        try {
            bigEndianDigits(1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigits(0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigits_BigInteger_BigInteger() {
        aeq(bigEndianDigits(TWO, BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(bigEndianDigits(TWO, BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(bigEndianDigits(TWO, BigInteger.valueOf(10)), "[1, 0, 1, 0]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1, 0, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.valueOf(10)), "[1, 2]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.valueOf(10)), "[1, 0]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigits(TWO, BigInteger.valueOf(187)), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(3), BigInteger.valueOf(187)), "[2, 0, 2, 2, 1]");
        aeq(bigEndianDigits(BigInteger.valueOf(8), BigInteger.valueOf(187)), "[2, 7, 3]");
        aeq(bigEndianDigits(BigInteger.valueOf(10), BigInteger.valueOf(187)), "[1, 8, 7]");
        aeq(bigEndianDigits(BigInteger.valueOf(12), BigInteger.valueOf(187)), "[1, 3, 7]");
        aeq(bigEndianDigits(BigInteger.valueOf(57), BigInteger.valueOf(187)), "[3, 16]");
        try {
            bigEndianDigits(BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigits(TWO, NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigits(BigInteger.ZERO, NEGATIVE_ONE);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_int_int_int() {
        aeq(bigEndianDigitsPadded(0, 2, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 0), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 1), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 10), "[]");
        aeq(bigEndianDigitsPadded(0, 2, 187), "[]");
        aeq(bigEndianDigitsPadded(0, 3, 187), "[]");
        aeq(bigEndianDigitsPadded(0, 57, 187), "[]");
        aeq(bigEndianDigitsPadded(1, 2, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 3, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 57, 0), "[0]");
        aeq(bigEndianDigitsPadded(1, 2, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 3, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 1), "[1]");
        aeq(bigEndianDigitsPadded(1, 2, 10), "[0]");
        aeq(bigEndianDigitsPadded(1, 3, 10), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 10), "[10]");
        aeq(bigEndianDigitsPadded(1, 2, 187), "[1]");
        aeq(bigEndianDigitsPadded(1, 3, 187), "[1]");
        aeq(bigEndianDigitsPadded(1, 57, 187), "[16]");
        aeq(bigEndianDigitsPadded(2, 2, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 3, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 57, 0), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, 2, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 3, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 1), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 2, 10), "[1, 0]");
        aeq(bigEndianDigitsPadded(2, 3, 10), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 10), "[0, 10]");
        aeq(bigEndianDigitsPadded(2, 2, 187), "[1, 1]");
        aeq(bigEndianDigitsPadded(2, 3, 187), "[2, 1]");
        aeq(bigEndianDigitsPadded(2, 57, 187), "[3, 16]");
        aeq(bigEndianDigitsPadded(8, 2, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 3, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 57, 0), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, 2, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 3, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 1), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 2, 10), "[0, 0, 0, 0, 1, 0, 1, 0]");
        aeq(bigEndianDigitsPadded(8, 3, 10), "[0, 0, 0, 0, 0, 1, 0, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 10), "[0, 0, 0, 0, 0, 0, 0, 10]");
        aeq(bigEndianDigitsPadded(8, 2, 187), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigitsPadded(8, 3, 187), "[0, 0, 0, 2, 0, 2, 2, 1]");
        aeq(bigEndianDigitsPadded(8, 57, 187), "[0, 0, 0, 0, 0, 0, 3, 16]");
        try {
            bigEndianDigitsPadded(3, 1, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, 0, 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, 2, -1);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigitsPadded(3, 0, -1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, 2, 3);
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, 0, -1);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigEndianDigitsPadded_BigInteger_BigInteger_BigInteger() {
        aeq(bigEndianDigitsPadded(0, TWO, BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(3), BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(57), BigInteger.ZERO), "[]");
        aeq(bigEndianDigitsPadded(0, TWO, BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(3), BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(57), BigInteger.ONE), "[]");
        aeq(bigEndianDigitsPadded(0, TWO, BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[]");
        aeq(bigEndianDigitsPadded(0, TWO, BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(0, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[]");
        aeq(bigEndianDigitsPadded(1, TWO, BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(3), BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(57), BigInteger.ZERO), "[0]");
        aeq(bigEndianDigitsPadded(1, TWO, BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(3), BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(57), BigInteger.ONE), "[1]");
        aeq(bigEndianDigitsPadded(1, TWO, BigInteger.valueOf(10)), "[0]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[1]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[10]");
        aeq(bigEndianDigitsPadded(1, TWO, BigInteger.valueOf(187)), "[1]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[1]");
        aeq(bigEndianDigitsPadded(1, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[16]");
        aeq(bigEndianDigitsPadded(2, TWO, BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0]");
        aeq(bigEndianDigitsPadded(2, TWO, BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(3), BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(57), BigInteger.ONE), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, TWO, BigInteger.valueOf(10)), "[1, 0]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[0, 1]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[0, 10]");
        aeq(bigEndianDigitsPadded(2, TWO, BigInteger.valueOf(187)), "[1, 1]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[2, 1]");
        aeq(bigEndianDigitsPadded(2, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[3, 16]");
        aeq(bigEndianDigitsPadded(8, TWO, BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(3), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(57), BigInteger.ZERO), "[0, 0, 0, 0, 0, 0, 0, 0]");
        aeq(bigEndianDigitsPadded(8, TWO, BigInteger.ONE), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(3), BigInteger.ONE), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(57), BigInteger.ONE), "[0, 0, 0, 0, 0, 0, 0, 1]");
        aeq(bigEndianDigitsPadded(8, TWO, BigInteger.valueOf(10)), "[0, 0, 0, 0, 1, 0, 1, 0]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(3), BigInteger.valueOf(10)), "[0, 0, 0, 0, 0, 1, 0, 1]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(57), BigInteger.valueOf(10)), "[0, 0, 0, 0, 0, 0, 0, 10]");
        aeq(bigEndianDigitsPadded(8, TWO, BigInteger.valueOf(187)), "[1, 0, 1, 1, 1, 0, 1, 1]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(3), BigInteger.valueOf(187)), "[0, 0, 0, 2, 0, 2, 2, 1]");
        aeq(bigEndianDigitsPadded(8, BigInteger.valueOf(57), BigInteger.valueOf(187)), "[0, 0, 0, 0, 0, 0, 3, 16]");
        try {
            bigEndianDigitsPadded(3, BigInteger.ONE, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, BigInteger.ZERO, BigInteger.valueOf(10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(3, TWO, NEGATIVE_ONE);
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            bigEndianDigitsPadded(3, BigInteger.ZERO, NEGATIVE_ONE);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, TWO, BigInteger.valueOf(3));
        } catch (IllegalArgumentException ignored) {}
        try {
            bigEndianDigitsPadded(-1, BigInteger.ZERO, NEGATIVE_ONE);
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigits_int_Iterable_Integer() {
        aeq(fromDigits(2, Arrays.asList(0, 0)), 0);
        aeq(fromDigits(2, Arrays.asList(1, 0)), 1);
        aeq(fromDigits(2, Arrays.asList(1, 0, 1, 1, 1, 0)), 29);
        aeq(fromDigits(10, Arrays.asList(9, 5, 1, 4, 1, 3)), 314159);
        aeq(fromDigits(70, Arrays.asList(8, 0, 20, 5, 43)), 1034243008);
        aeq(fromDigits(70, new ArrayList<Integer>()), 0);
        try {
            fromDigits(1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(0, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(10, Arrays.asList(-1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(10, Arrays.asList(1, 2, 10));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigits_BigInteger_Iterable_BigInteger() {
        aeq(fromDigits(TWO, readBigIntegerList("[0, 0]")), 0);
        aeq(fromDigits(TWO, readBigIntegerList("[1, 0]")), 1);
        aeq(fromDigits(TWO, readBigIntegerList("[1, 0, 1, 1, 1, 0]")), 29);
        aeq(fromDigits(BigInteger.valueOf(10), readBigIntegerList("[9, 5, 1, 4, 1, 3]")), 314159);
        aeq(fromDigits(BigInteger.valueOf(70), readBigIntegerList("[8, 0, 20, 5, 43]")), 1034243008);
        aeq(fromDigits(BigInteger.valueOf(70), readBigIntegerList("[]")), 0);
        try {
            fromDigits(BigInteger.ONE, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.ZERO, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(NEGATIVE_ONE, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), readBigIntegerList("[-1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigits(BigInteger.valueOf(10), readBigIntegerList("[1, 2, 10]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_int_Iterable_Integer() {
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 0)), 0);
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 1)), 1);
        aeq(fromBigEndianDigits(2, Arrays.asList(0, 1, 1, 1, 0, 1)), 29);
        aeq(fromBigEndianDigits(10, Arrays.asList(3, 1, 4, 1, 5, 9)), 314159);
        aeq(fromBigEndianDigits(70, Arrays.asList(43, 5, 20, 0, 8)), 1034243008);
        aeq(fromBigEndianDigits(70, new ArrayList<Integer>()), 0);
        try {
            fromBigEndianDigits(1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(0, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(10, Arrays.asList(-1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(10, Arrays.asList(1, 2, 10));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromBigEndianDigits_int_Iterable_BigInteger() {
        aeq(fromBigEndianDigits(TWO, readBigIntegerList("[0, 0]")), 0);
        aeq(fromBigEndianDigits(TWO, readBigIntegerList("[0, 1]")), 1);
        aeq(fromBigEndianDigits(TWO, readBigIntegerList("[0, 1, 1, 1, 0, 1]")), 29);
        aeq(fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[3, 1, 4, 1, 5, 9]")), 314159);
        aeq(fromBigEndianDigits(BigInteger.valueOf(70), readBigIntegerList("[43, 5, 20, 0, 8]")), 1034243008);
        aeq(fromBigEndianDigits(BigInteger.valueOf(70), readBigIntegerList("[]")), 0);
        try {
            fromBigEndianDigits(BigInteger.ONE, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.ZERO, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(NEGATIVE_ONE, readBigIntegerList("[1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[-1, 2, 3]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromBigEndianDigits(BigInteger.valueOf(10), readBigIntegerList("[1, 2, 10]"));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToDigit() {
        aeq(toDigit(0), '0');
        aeq(toDigit(6), '6');
        aeq(toDigit(10), 'A');
        aeq(toDigit(20), 'K');
        aeq(toDigit(35), 'Z');
        try {
            toDigit(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toDigit(36);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromDigit() {
        aeq(fromDigit('0'), 0);
        aeq(fromDigit('6'), 6);
        aeq(fromDigit('A'), 10);
        aeq(fromDigit('K'), 20);
        aeq(fromDigit('Z'), 35);
        try {
            fromDigit(' ');
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromDigit('a');
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_int_int() {
        aeq(toStringBase(2, 0), "0");
        aeq(toStringBase(3, 0), "0");
        aeq(toStringBase(4, 0), "0");
        aeq(toStringBase(10, 0), "0");
        aeq(toStringBase(12, 0), "0");
        aeq(toStringBase(16, 0), "0");
        aeq(toStringBase(36, 0), "0");
        aeq(toStringBase(88, 0), "(0)");
        aeq(toStringBase(100, 0), "(0)");
        aeq(toStringBase(2, 524393454), "11111010000011001101111101110");
        aeq(toStringBase(3, 524393454), "1100112201221120210");
        aeq(toStringBase(4, 524393454), "133100121233232");
        aeq(toStringBase(10, 524393454), "524393454");
        aeq(toStringBase(12, 524393454), "127750526");
        aeq(toStringBase(16, 524393454), "1F419BEE");
        aeq(toStringBase(36, 524393454), "8O7KKU");
        aeq(toStringBase(88, 524393454), "(8)(65)(44)(8)(46)");
        aeq(toStringBase(100, 524393454), "(5)(24)(39)(34)(54)");
        aeq(toStringBase(2, -524393454), "-11111010000011001101111101110");
        aeq(toStringBase(3, -524393454), "-1100112201221120210");
        aeq(toStringBase(4, -524393454), "-133100121233232");
        aeq(toStringBase(10, -524393454), "-524393454");
        aeq(toStringBase(12, -524393454), "-127750526");
        aeq(toStringBase(16, -524393454), "-1F419BEE");
        aeq(toStringBase(36, -524393454), "-8O7KKU");
        aeq(toStringBase(88, -524393454), "-(8)(65)(44)(8)(46)");
        aeq(toStringBase(100, -524393454), "-(5)(24)(39)(34)(54)");
        try {
            toStringBase(1, 524393454);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toStringBase(0, 524393454);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testToStringBase_BigInteger_BigInteger() {
        aeq(toStringBase(TWO, BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.ZERO), "0");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.ZERO), "(0)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.ZERO), "(0)");
        aeq(toStringBase(TWO, BigInteger.valueOf(524393454)), "11111010000011001101111101110");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.valueOf(524393454)), "1100112201221120210");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.valueOf(524393454)), "133100121233232");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.valueOf(524393454)), "524393454");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.valueOf(524393454)), "127750526");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.valueOf(524393454)), "1F419BEE");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.valueOf(524393454)), "8O7KKU");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.valueOf(524393454)), "(8)(65)(44)(8)(46)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.valueOf(524393454)), "(5)(24)(39)(34)(54)");
        aeq(toStringBase(TWO, BigInteger.valueOf(-524393454)), "-11111010000011001101111101110");
        aeq(toStringBase(BigInteger.valueOf(3), BigInteger.valueOf(-524393454)), "-1100112201221120210");
        aeq(toStringBase(BigInteger.valueOf(4), BigInteger.valueOf(-524393454)), "-133100121233232");
        aeq(toStringBase(BigInteger.valueOf(10), BigInteger.valueOf(-524393454)), "-524393454");
        aeq(toStringBase(BigInteger.valueOf(12), BigInteger.valueOf(-524393454)), "-127750526");
        aeq(toStringBase(BigInteger.valueOf(16), BigInteger.valueOf(-524393454)), "-1F419BEE");
        aeq(toStringBase(BigInteger.valueOf(36), BigInteger.valueOf(-524393454)), "-8O7KKU");
        aeq(toStringBase(BigInteger.valueOf(88), BigInteger.valueOf(-524393454)), "-(8)(65)(44)(8)(46)");
        aeq(toStringBase(BigInteger.valueOf(100), BigInteger.valueOf(-524393454)), "-(5)(24)(39)(34)(54)");
        try {
            toStringBase(BigInteger.ONE, BigInteger.valueOf(524393454));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toStringBase(BigInteger.ZERO, BigInteger.valueOf(524393454));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_int_String() {
        aeq(fromStringBase(2, ""), 0);
        aeq(fromStringBase(3, ""), 0);
        aeq(fromStringBase(4, ""), 0);
        aeq(fromStringBase(10, ""), 0);
        aeq(fromStringBase(12, ""), 0);
        aeq(fromStringBase(16, ""), 0);
        aeq(fromStringBase(36, ""), 0);
        aeq(fromStringBase(88, ""), 0);
        aeq(fromStringBase(100, ""), 0);
        aeq(fromStringBase(2, "0"), 0);
        aeq(fromStringBase(3, "0"), 0);
        aeq(fromStringBase(4, "0"), 0);
        aeq(fromStringBase(10, "0"), 0);
        aeq(fromStringBase(12, "0"), 0);
        aeq(fromStringBase(16, "0"), 0);
        aeq(fromStringBase(36, "0"), 0);
        aeq(fromStringBase(88, "(0)"), 0);
        aeq(fromStringBase(100, "(0)"), 0);
        aeq(fromStringBase(2, "00"), 0);
        aeq(fromStringBase(3, "00"), 0);
        aeq(fromStringBase(4, "00"), 0);
        aeq(fromStringBase(10, "00"), 0);
        aeq(fromStringBase(12, "00"), 0);
        aeq(fromStringBase(16, "00"), 0);
        aeq(fromStringBase(36, "00"), 0);
        aeq(fromStringBase(88, "(0)(0)"), 0);
        aeq(fromStringBase(100, "(0)(0)"), 0);
        aeq(fromStringBase(2, "-0"), 0);
        aeq(fromStringBase(3, "-0"), 0);
        aeq(fromStringBase(4, "-0"), 0);
        aeq(fromStringBase(10, "-0"), 0);
        aeq(fromStringBase(12, "-0"), 0);
        aeq(fromStringBase(16, "-0"), 0);
        aeq(fromStringBase(36, "-0"), 0);
        aeq(fromStringBase(88, "-(0)"), 0);
        aeq(fromStringBase(100, "-(0)"), 0);
        aeq(fromStringBase(2, "-00"), 0);
        aeq(fromStringBase(3, "-00"), 0);
        aeq(fromStringBase(4, "-00"), 0);
        aeq(fromStringBase(10, "-00"), 0);
        aeq(fromStringBase(12, "-00"), 0);
        aeq(fromStringBase(16, "-00"), 0);
        aeq(fromStringBase(36, "-00"), 0);
        aeq(fromStringBase(88, "-(0)(0)"), 0);
        aeq(fromStringBase(100, "-(0)(0)"), 0);
        aeq(fromStringBase(2, "11111010000011001101111101110"), 524393454);
        aeq(fromStringBase(3, "1100112201221120210"), 524393454);
        aeq(fromStringBase(4, "133100121233232"), 524393454);
        aeq(fromStringBase(10, "524393454"), 524393454);
        aeq(fromStringBase(12, "127750526"), 524393454);
        aeq(fromStringBase(16, "1F419BEE"), 524393454);
        aeq(fromStringBase(36, "8O7KKU"), 524393454);
        aeq(fromStringBase(88, "(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(100, "(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(2, "00011111010000011001101111101110"), 524393454);
        aeq(fromStringBase(3, "0001100112201221120210"), 524393454);
        aeq(fromStringBase(4, "000133100121233232"), 524393454);
        aeq(fromStringBase(10, "000524393454"), 524393454);
        aeq(fromStringBase(12, "000127750526"), 524393454);
        aeq(fromStringBase(16, "0001F419BEE"), 524393454);
        aeq(fromStringBase(36, "0008O7KKU"), 524393454);
        aeq(fromStringBase(88, "(0)(0)(0)(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(100, "(0)(0)(0)(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(2, "-11111010000011001101111101110"), -524393454);
        aeq(fromStringBase(3, "-1100112201221120210"), -524393454);
        aeq(fromStringBase(4, "-133100121233232"), -524393454);
        aeq(fromStringBase(10, "-524393454"), -524393454);
        aeq(fromStringBase(12, "-127750526"), -524393454);
        aeq(fromStringBase(16, "-1F419BEE"), -524393454);
        aeq(fromStringBase(36, "-8O7KKU"), -524393454);
        aeq(fromStringBase(88, "-(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(100, "-(5)(24)(39)(34)(54)"), -524393454);
        aeq(fromStringBase(2, "-00011111010000011001101111101110"), -524393454);
        aeq(fromStringBase(3, "-0001100112201221120210"), -524393454);
        aeq(fromStringBase(4, "-000133100121233232"), -524393454);
        aeq(fromStringBase(10, "-000524393454"), -524393454);
        aeq(fromStringBase(12, "-000127750526"), -524393454);
        aeq(fromStringBase(16, "-0001F419BEE"), -524393454);
        aeq(fromStringBase(36, "-0008O7KKU"), -524393454);
        aeq(fromStringBase(88, "-(0)(0)(0)(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(100, "-(0)(0)(0)(5)(24)(39)(34)(54)"), -524393454);
        try {
            fromStringBase(1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(0, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "3");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(2, "*");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "12");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(-12)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(3F)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "()()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(00)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(100, "(02)");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromStringBase_BigInteger_String() {
        aeq(fromStringBase(TWO, ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), ""), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), ""), 0);
        aeq(fromStringBase(TWO, "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)"), 0);
        aeq(fromStringBase(TWO, "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)(0)"), 0);
        aeq(fromStringBase(TWO, "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "-0"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)"), 0);
        aeq(fromStringBase(TWO, "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(3), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(4), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(10), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(12), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(16), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(36), "-00"), 0);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)(0)"), 0);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)(0)"), 0);
        aeq(fromStringBase(TWO, "11111010000011001101111101110"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "1100112201221120210"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "133100121233232"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "524393454"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "127750526"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "1F419BEE"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "8O7KKU"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(TWO, "00011111010000011001101111101110"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "0001100112201221120210"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "000133100121233232"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "000524393454"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "000127750526"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "0001F419BEE"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "0008O7KKU"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "(0)(0)(0)(8)(65)(44)(8)(46)"), 524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "(0)(0)(0)(5)(24)(39)(34)(54)"), 524393454);
        aeq(fromStringBase(TWO, "-11111010000011001101111101110"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "-1100112201221120210"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "-133100121233232"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "-524393454"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "-127750526"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "-1F419BEE"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "-8O7KKU"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(5)(24)(39)(34)(54)"), -524393454);
        aeq(fromStringBase(TWO, "-00011111010000011001101111101110"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(3), "-0001100112201221120210"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(4), "-000133100121233232"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(10), "-000524393454"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(12), "-000127750526"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(16), "-0001F419BEE"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(36), "-0008O7KKU"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(88), "-(0)(0)(0)(8)(65)(44)(8)(46)"), -524393454);
        aeq(fromStringBase(BigInteger.valueOf(100), "-(0)(0)(0)(5)(24)(39)(34)(54)"), -524393454);
        try {
            fromStringBase(BigInteger.ONE, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.ZERO, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(TWO, "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(TWO, "3");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(TWO, "*");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "12");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(-12)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "()()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(3F)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "-");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "()");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(00)");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            fromStringBase(BigInteger.valueOf(100), "(02)");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLogarithmicMux() {
        aeq(logarithmicMux(BigInteger.valueOf(0), BigInteger.valueOf(0)), 0);
        aeq(logarithmicMux(BigInteger.valueOf(0), BigInteger.valueOf(1)), 1);
        aeq(logarithmicMux(BigInteger.valueOf(1), BigInteger.valueOf(0)), 2);
        aeq(logarithmicMux(BigInteger.valueOf(5), BigInteger.valueOf(10)), 11263);
        aeq(logarithmicMux(BigInteger.valueOf(10), BigInteger.valueOf(5)), 671);
        aeq(logarithmicMux(BigInteger.valueOf(500000), BigInteger.ZERO), 1000000);
        try {
            logarithmicMux(BigInteger.valueOf(-5), BigInteger.valueOf(5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            logarithmicMux(BigInteger.valueOf(5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            logarithmicMux(BigInteger.valueOf(-5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testLogarithmicDemux() {
        aeq(logarithmicDemux(BigInteger.ZERO), "(0, 0)");
        aeq(logarithmicDemux(BigInteger.ONE), "(0, 1)");
        aeq(logarithmicDemux(TWO), "(1, 0)");
        aeq(logarithmicDemux(BigInteger.valueOf(11263)), "(5, 10)");
        aeq(logarithmicDemux(BigInteger.valueOf(671)), "(10, 5)");
        aeq(logarithmicDemux(BigInteger.valueOf(1000000)), "(500000, 0)");
        try {
            logarithmicDemux(BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootMux() {
        aeq(squareRootMux(BigInteger.valueOf(0), BigInteger.valueOf(0)), 0);
        aeq(squareRootMux(BigInteger.valueOf(0), BigInteger.valueOf(1)), 1);
        aeq(squareRootMux(BigInteger.valueOf(1), BigInteger.valueOf(0)), 2);
        aeq(squareRootMux(BigInteger.valueOf(5), BigInteger.valueOf(10)), 538);
        aeq(squareRootMux(BigInteger.valueOf(10), BigInteger.valueOf(5)), 101);
        aeq(squareRootMux(BigInteger.valueOf(7680), BigInteger.valueOf(76)), 1000000);
        try {
            squareRootMux(BigInteger.valueOf(-5), BigInteger.valueOf(5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            squareRootMux(BigInteger.valueOf(5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            squareRootMux(BigInteger.valueOf(-5), BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSquareRootDemux() {
        aeq(squareRootDemux(BigInteger.ZERO), "(0, 0)");
        aeq(squareRootDemux(BigInteger.ONE), "(0, 1)");
        aeq(squareRootDemux(TWO), "(1, 0)");
        aeq(squareRootDemux(BigInteger.valueOf(538)), "(5, 10)");
        aeq(squareRootDemux(BigInteger.valueOf(101)), "(10, 5)");
        aeq(squareRootDemux(BigInteger.valueOf(1000000)), "(7680, 76)");
        try {
            squareRootDemux(BigInteger.valueOf(-5));
            fail();
        } catch (ArithmeticException ignored) {}
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

    private static @NotNull List<BigInteger> readBigIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readBigInteger).apply(s).get();
    }

    private static @NotNull List<BigInteger> readBigIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readBigInteger).apply(s).get();
    }
}
