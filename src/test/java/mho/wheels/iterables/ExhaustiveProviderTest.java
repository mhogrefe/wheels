package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class ExhaustiveProviderTest {
    private static <T> void simpleProviderHelper(@NotNull Iterable<T> xs, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, xs, output);
        testNoRemove(TINY_LIMIT, xs);
    }

    private static void rangeUpIncreasing_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing((byte) a), output);
    }

    @Test
    public void testRangeUpIncreasing_byte() {
        rangeUpIncreasing_byte_helper(0, "ExhaustiveProvider_rangeUpIncreasing_byte_i");
        rangeUpIncreasing_byte_helper(5, "ExhaustiveProvider_rangeUpIncreasing_byte_ii");
        rangeUpIncreasing_byte_helper(-5, "ExhaustiveProvider_rangeUpIncreasing_byte_iii");
        rangeUpIncreasing_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeUpIncreasing_byte_iv");
        rangeUpIncreasing_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeUpIncreasing_byte_v");
    }

    private static void rangeUpIncreasing_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing((short) a), output);
    }

    @Test
    public void testRangeUpIncreasing_short() {
        rangeUpIncreasing_short_helper(0, "ExhaustiveProvider_rangeUpIncreasing_short_i");
        rangeUpIncreasing_short_helper(5, "ExhaustiveProvider_rangeUpIncreasing_short_ii");
        rangeUpIncreasing_short_helper(-5, "ExhaustiveProvider_rangeUpIncreasing_short_iii");
        rangeUpIncreasing_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeUpIncreasing_short_iv");
        rangeUpIncreasing_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeUpIncreasing_short_v");
    }

    private static void rangeUpIncreasing_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing(a), output);
    }

    @Test
    public void testRangeUpIncreasing_int() {
        rangeUpIncreasing_int_helper(0, "ExhaustiveProvider_rangeUpIncreasing_int_i");
        rangeUpIncreasing_int_helper(5, "ExhaustiveProvider_rangeUpIncreasing_int_ii");
        rangeUpIncreasing_int_helper(-5, "ExhaustiveProvider_rangeUpIncreasing_int_iii");
        rangeUpIncreasing_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeUpIncreasing_int_iv");
        rangeUpIncreasing_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeUpIncreasing_int_v");
    }

    private static void rangeUpIncreasing_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing(a), output);
    }

    @Test
    public void testRangeUpIncreasing_long() {
        rangeUpIncreasing_long_helper(0L, "ExhaustiveProvider_rangeUpIncreasing_long_i");
        rangeUpIncreasing_long_helper(5L, "ExhaustiveProvider_rangeUpIncreasing_long_ii");
        rangeUpIncreasing_long_helper(-5L, "ExhaustiveProvider_rangeUpIncreasing_long_iii");
        rangeUpIncreasing_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeUpIncreasing_long_iv");
        rangeUpIncreasing_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeUpIncreasing_long_v");
    }

    private static void rangeUpIncreasing_BigInteger_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing(Readers.readBigIntegerStrict(a).get()), output);
    }

    @Test
    public void testRangeUpIncreasing_BigInteger() {
        rangeUpIncreasing_BigInteger_helper("0", "ExhaustiveProvider_rangeUpIncreasing_BigInteger_i");
        rangeUpIncreasing_BigInteger_helper("5", "ExhaustiveProvider_rangeUpIncreasing_BigInteger_ii");
        rangeUpIncreasing_BigInteger_helper("-5", "ExhaustiveProvider_rangeUpIncreasing_BigInteger_iii");
    }

    private static void rangeUpIncreasing_char_helper(char a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpIncreasing(a), output);
    }

    @Test
    public void testRangeUpIncreasing_char() {
        rangeUpIncreasing_char_helper('\0', "ExhaustiveProvider_rangeUpIncreasing_char_i");
        rangeUpIncreasing_char_helper('a', "ExhaustiveProvider_rangeUpIncreasing_char_ii");
        rangeUpIncreasing_char_helper('Ш', "ExhaustiveProvider_rangeUpIncreasing_char_iii");
        rangeUpIncreasing_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeUpIncreasing_char_iv");
    }

    private static void rangeUpDecreasing_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpDecreasing((byte) a), output);
    }

    @Test
    public void testRangeUpDecreasing_byte() {
        rangeUpDecreasing_byte_helper(0, "ExhaustiveProvider_rangeUpDecreasing_byte_i");
        rangeUpDecreasing_byte_helper(5, "ExhaustiveProvider_rangeUpDecreasing_byte_ii");
        rangeUpDecreasing_byte_helper(-5, "ExhaustiveProvider_rangeUpDecreasing_byte_iii");
        rangeUpDecreasing_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeUpDecreasing_byte_iv");
        rangeUpDecreasing_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeUpDecreasing_byte_v");
    }

    private static void rangeUpDecreasing_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpDecreasing((short) a), output);
    }

    @Test
    public void testRangeUpDecreasing_short() {
        rangeUpDecreasing_short_helper(0, "ExhaustiveProvider_rangeUpDecreasing_short_i");
        rangeUpDecreasing_short_helper(5, "ExhaustiveProvider_rangeUpDecreasing_short_ii");
        rangeUpDecreasing_short_helper(-5, "ExhaustiveProvider_rangeUpDecreasing_short_iii");
        rangeUpDecreasing_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeUpDecreasing_short_iv");
        rangeUpDecreasing_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeUpDecreasing_short_v");
    }

    private static void rangeUpDecreasing_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpDecreasing(a), output);
    }

    @Test
    public void testRangeUpDecreasing_int() {
        rangeUpDecreasing_int_helper(0, "ExhaustiveProvider_rangeUpDecreasing_int_i");
        rangeUpDecreasing_int_helper(5, "ExhaustiveProvider_rangeUpDecreasing_int_ii");
        rangeUpDecreasing_int_helper(-5, "ExhaustiveProvider_rangeUpDecreasing_int_iii");
        rangeUpDecreasing_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeUpDecreasing_int_iv");
        rangeUpDecreasing_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeUpDecreasing_int_v");
    }

    private static void rangeUpDecreasing_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpDecreasing(a), output);
    }

    @Test
    public void testRangeUpDecreasing_long() {
        rangeUpDecreasing_long_helper(0L, "ExhaustiveProvider_rangeUpDecreasing_long_i");
        rangeUpDecreasing_long_helper(5L, "ExhaustiveProvider_rangeUpDecreasing_long_ii");
        rangeUpDecreasing_long_helper(-5L, "ExhaustiveProvider_rangeUpDecreasing_long_iii");
        rangeUpDecreasing_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeUpDecreasing_long_iv");
        rangeUpDecreasing_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeUpDecreasing_long_v");
    }

    private static void rangeUpDecreasing_char_helper(char a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpDecreasing(a), output);
    }

    @Test
    public void testRangeUpDecreasing_char() {
        rangeUpDecreasing_char_helper('\0', "ExhaustiveProvider_rangeUpDecreasing_char_i");
        rangeUpDecreasing_char_helper('a', "ExhaustiveProvider_rangeUpDecreasing_char_ii");
        rangeUpDecreasing_char_helper('Ш', "ExhaustiveProvider_rangeUpDecreasing_char_iii");
        rangeUpDecreasing_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeUpDecreasing_char_iv");
    }

    private static void rangeDownIncreasing_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownIncreasing((byte) a), output);
    }

    @Test
    public void testRangeDownIncreasing_byte() {
        rangeDownIncreasing_byte_helper(0, "ExhaustiveProvider_rangeDownIncreasing_byte_i");
        rangeDownIncreasing_byte_helper(5, "ExhaustiveProvider_rangeDownIncreasing_byte_ii");
        rangeDownIncreasing_byte_helper(-5, "ExhaustiveProvider_rangeDownIncreasing_byte_iii");
        rangeDownIncreasing_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeDownIncreasing_byte_iv");
        rangeDownIncreasing_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeDownIncreasing_byte_v");
    }

    private static void rangeDownIncreasing_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownIncreasing((short) a), output);
    }

    @Test
    public void testRangeDownIncreasing_short() {
        rangeDownIncreasing_short_helper(0, "ExhaustiveProvider_rangeDownIncreasing_short_i");
        rangeDownIncreasing_short_helper(5, "ExhaustiveProvider_rangeDownIncreasing_short_ii");
        rangeDownIncreasing_short_helper(-5, "ExhaustiveProvider_rangeDownIncreasing_short_iii");
        rangeDownIncreasing_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeDownIncreasing_short_iv");
        rangeDownIncreasing_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeDownIncreasing_short_v");
    }

    private static void rangeDownIncreasing_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownIncreasing(a), output);
    }

    @Test
    public void testRangeDownIncreasing_int() {
        rangeDownIncreasing_int_helper(0, "ExhaustiveProvider_rangeDownIncreasing_int_i");
        rangeDownIncreasing_int_helper(5, "ExhaustiveProvider_rangeDownIncreasing_int_ii");
        rangeDownIncreasing_int_helper(-5, "ExhaustiveProvider_rangeDownIncreasing_int_iii");
        rangeDownIncreasing_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeDownIncreasing_int_iv");
        rangeDownIncreasing_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeDownIncreasing_int_v");
    }

    private static void rangeDownIncreasing_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownIncreasing(a), output);
    }

    @Test
    public void testRangeDownIncreasing_long() {
        rangeDownIncreasing_long_helper(0L, "ExhaustiveProvider_rangeDownIncreasing_long_i");
        rangeDownIncreasing_long_helper(5L, "ExhaustiveProvider_rangeDownIncreasing_long_ii");
        rangeDownIncreasing_long_helper(-5L, "ExhaustiveProvider_rangeDownIncreasing_long_iii");
        rangeDownIncreasing_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeDownIncreasing_long_iv");
        rangeDownIncreasing_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeDownIncreasing_long_v");
    }

    private static void rangeDownIncreasing_char_helper(char a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownIncreasing(a), output);
    }

    @Test
    public void testRangeDownIncreasing_char() {
        rangeDownIncreasing_char_helper('\0', "ExhaustiveProvider_rangeDownIncreasing_char_i");
        rangeDownIncreasing_char_helper('a', "ExhaustiveProvider_rangeDownIncreasing_char_ii");
        rangeDownIncreasing_char_helper('Ш', "ExhaustiveProvider_rangeDownIncreasing_char_iii");
        rangeDownIncreasing_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeDownIncreasing_char_iv");
    }

    private static void rangeDownDecreasing_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing((byte) a), output);
    }

    @Test
    public void testRangeDownDecreasing_byte() {
        rangeDownDecreasing_byte_helper(0, "ExhaustiveProvider_rangeDownDecreasing_byte_i");
        rangeDownDecreasing_byte_helper(5, "ExhaustiveProvider_rangeDownDecreasing_byte_ii");
        rangeDownDecreasing_byte_helper(-5, "ExhaustiveProvider_rangeDownDecreasing_byte_iii");
        rangeDownDecreasing_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeDownDecreasing_byte_iv");
        rangeDownDecreasing_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeDownDecreasing_byte_v");
    }

    private static void rangeDownDecreasing_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing((short) a), output);
    }

    @Test
    public void testRangeDownDecreasing_short() {
        rangeDownDecreasing_short_helper(0, "ExhaustiveProvider_rangeDownDecreasing_short_i");
        rangeDownDecreasing_short_helper(5, "ExhaustiveProvider_rangeDownDecreasing_short_ii");
        rangeDownDecreasing_short_helper(-5, "ExhaustiveProvider_rangeDownDecreasing_short_iii");
        rangeDownDecreasing_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeDownDecreasing_short_iv");
        rangeDownDecreasing_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeDownDecreasing_short_v");
    }

    private static void rangeDownDecreasing_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing(a), output);
    }

    @Test
    public void testRangeDownDecreasing_int() {
        rangeDownDecreasing_int_helper(0, "ExhaustiveProvider_rangeDownDecreasing_int_i");
        rangeDownDecreasing_int_helper(5, "ExhaustiveProvider_rangeDownDecreasing_int_ii");
        rangeDownDecreasing_int_helper(-5, "ExhaustiveProvider_rangeDownDecreasing_int_iii");
        rangeDownDecreasing_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeDownDecreasing_int_iv");
        rangeDownDecreasing_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeDownDecreasing_int_v");
    }

    private static void rangeDownDecreasing_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing(a), output);
    }

    @Test
    public void testRangeDownDecreasing_long() {
        rangeDownDecreasing_long_helper(0L, "ExhaustiveProvider_rangeDownDecreasing_long_i");
        rangeDownDecreasing_long_helper(5L, "ExhaustiveProvider_rangeDownDecreasing_long_ii");
        rangeDownDecreasing_long_helper(-5L, "ExhaustiveProvider_rangeDownDecreasing_long_iii");
        rangeDownDecreasing_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeDownDecreasing_long_iv");
        rangeDownDecreasing_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeDownDecreasing_long_v");
    }

    private static void rangeDownDecreasing_BigInteger_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing(Readers.readBigIntegerStrict(a).get()), output);
    }

    @Test
    public void testRangeDownDecreasing_BigInteger() {
        rangeDownDecreasing_BigInteger_helper("0", "ExhaustiveProvider_rangeDownDecreasing_BigInteger_i");
        rangeDownDecreasing_BigInteger_helper("5", "ExhaustiveProvider_rangeDownDecreasing_BigInteger_ii");
        rangeDownDecreasing_BigInteger_helper("-5", "ExhaustiveProvider_rangeDownDecreasing_BigInteger_iii");
    }

    private static void rangeDownDecreasing_char_helper(char a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownDecreasing(a), output);
    }

    @Test
    public void testRangeDownDecreasing_char() {
        rangeDownDecreasing_char_helper('\0', "ExhaustiveProvider_rangeDownDecreasing_char_i");
        rangeDownDecreasing_char_helper('a', "ExhaustiveProvider_rangeDownDecreasing_char_ii");
        rangeDownDecreasing_char_helper('Ш', "ExhaustiveProvider_rangeDownDecreasing_char_iii");
        rangeDownDecreasing_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeDownDecreasing_char_iv");
    }

    private static void rangeIncreasing_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeIncreasing((byte) a, (byte) b), output);
    }

    private static void rangeIncreasing_byte_byte_fail_helper(int a, int b) {
        try {
            EP.rangeIncreasing((byte) a, (byte) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_byte_byte() {
        rangeIncreasing_byte_byte_helper(10, 20, "ExhaustiveProvider_rangeIncreasing_byte_byte_i");
        rangeIncreasing_byte_byte_helper(10, 10, "ExhaustiveProvider_rangeIncreasing_byte_byte_ii");
        rangeIncreasing_byte_byte_helper(-20, -10, "ExhaustiveProvider_rangeIncreasing_byte_byte_iii");
        rangeIncreasing_byte_byte_helper(-20, -20, "ExhaustiveProvider_rangeIncreasing_byte_byte_iv");
        rangeIncreasing_byte_byte_helper(0, 0, "ExhaustiveProvider_rangeIncreasing_byte_byte_v");
        rangeIncreasing_byte_byte_helper(0, 10, "ExhaustiveProvider_rangeIncreasing_byte_byte_vi");
        rangeIncreasing_byte_byte_helper(-5, 0, "ExhaustiveProvider_rangeIncreasing_byte_byte_vii");
        rangeIncreasing_byte_byte_helper(-5, 10, "ExhaustiveProvider_rangeIncreasing_byte_byte_viii");
        rangeIncreasing_byte_byte_helper(-10, 5, "ExhaustiveProvider_rangeIncreasing_byte_byte_ix");

        rangeIncreasing_byte_byte_fail_helper(10, 9);
        rangeIncreasing_byte_byte_fail_helper(-20, -21);
        rangeIncreasing_byte_byte_fail_helper(5, -10);
    }

    private static void rangeIncreasing_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeIncreasing((short) a, (short) b), output);
    }

    private static void rangeIncreasing_short_short_fail_helper(int a, int b) {
        try {
            EP.rangeIncreasing((short) a, (short) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_short_short() {
        rangeIncreasing_short_short_helper(10, 20, "ExhaustiveProvider_rangeIncreasing_short_short_i");
        rangeIncreasing_short_short_helper(10, 10, "ExhaustiveProvider_rangeIncreasing_short_short_ii");
        rangeIncreasing_short_short_helper(-20, -10, "ExhaustiveProvider_rangeIncreasing_short_short_iii");
        rangeIncreasing_short_short_helper(-20, -20, "ExhaustiveProvider_rangeIncreasing_short_short_iv");
        rangeIncreasing_short_short_helper(0, 0, "ExhaustiveProvider_rangeIncreasing_short_short_v");
        rangeIncreasing_short_short_helper(0, 10, "ExhaustiveProvider_rangeIncreasing_short_short_vi");
        rangeIncreasing_short_short_helper(-5, 0, "ExhaustiveProvider_rangeIncreasing_short_short_vii");
        rangeIncreasing_short_short_helper(-5, 10, "ExhaustiveProvider_rangeIncreasing_short_short_viii");
        rangeIncreasing_short_short_helper(-10, 5, "ExhaustiveProvider_rangeIncreasing_short_short_ix");

        rangeIncreasing_short_short_fail_helper(10, 9);
        rangeIncreasing_short_short_fail_helper(-20, -21);
        rangeIncreasing_short_short_fail_helper(5, -10);
    }

    private static void rangeIncreasing_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeIncreasing(a, b), output);
    }

    private static void rangeIncreasing_int_int_fail_helper(int a, int b) {
        try {
            EP.rangeIncreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_int_int() {
        rangeIncreasing_int_int_helper(10, 20, "ExhaustiveProvider_rangeIncreasing_int_int_i");
        rangeIncreasing_int_int_helper(10, 10, "ExhaustiveProvider_rangeIncreasing_int_int_ii");
        rangeIncreasing_int_int_helper(-20, -10, "ExhaustiveProvider_rangeIncreasing_int_int_iii");
        rangeIncreasing_int_int_helper(-20, -20, "ExhaustiveProvider_rangeIncreasing_int_int_iv");
        rangeIncreasing_int_int_helper(0, 0, "ExhaustiveProvider_rangeIncreasing_int_int_v");
        rangeIncreasing_int_int_helper(0, 10, "ExhaustiveProvider_rangeIncreasing_int_int_vi");
        rangeIncreasing_int_int_helper(-5, 0, "ExhaustiveProvider_rangeIncreasing_int_int_vii");
        rangeIncreasing_int_int_helper(-5, 10, "ExhaustiveProvider_rangeIncreasing_int_int_viii");
        rangeIncreasing_int_int_helper(-10, 5, "ExhaustiveProvider_rangeIncreasing_int_int_ix");

        rangeIncreasing_int_int_fail_helper(10, 9);
        rangeIncreasing_int_int_fail_helper(-20, -21);
        rangeIncreasing_int_int_fail_helper(5, -10);
    }

    private static void rangeIncreasing_long_long_helper(long a, long b, @NotNull String output) {
        aeqitLog(EP.rangeIncreasing(a, b), output);
    }

    private static void rangeIncreasing_long_long_fail_helper(long a, long b) {
        try {
            EP.rangeIncreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_long_long() {
        rangeIncreasing_long_long_helper(10L, 20L, "ExhaustiveProvider_rangeIncreasing_long_long_i");
        rangeIncreasing_long_long_helper(10L, 10L, "ExhaustiveProvider_rangeIncreasing_long_long_ii");
        rangeIncreasing_long_long_helper(-20L, -10L, "ExhaustiveProvider_rangeIncreasing_long_long_iii");
        rangeIncreasing_long_long_helper(-20L, -20L, "ExhaustiveProvider_rangeIncreasing_long_long_iv");
        rangeIncreasing_long_long_helper(0L, 0L, "ExhaustiveProvider_rangeIncreasing_long_long_v");
        rangeIncreasing_long_long_helper(0L, 10L, "ExhaustiveProvider_rangeIncreasing_long_long_vi");
        rangeIncreasing_long_long_helper(-5L, 0L, "ExhaustiveProvider_rangeIncreasing_long_long_vii");
        rangeIncreasing_long_long_helper(-5L, 10L, "ExhaustiveProvider_rangeIncreasing_long_long_viii");
        rangeIncreasing_long_long_helper(-10L, 5L, "ExhaustiveProvider_rangeIncreasing_long_long_ix");

        rangeIncreasing_long_long_fail_helper(10L, 9L);
        rangeIncreasing_long_long_fail_helper(-20L, -21L);
        rangeIncreasing_long_long_fail_helper(5L, -10L);
    }

    private static void rangeIncreasing_BigInteger_BigInteger_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeqitLog(
                EP.rangeIncreasing(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get()),
                output
        );
    }

    private static void rangeIncreasing_BigInteger_BigInteger_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.rangeIncreasing(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_BigInteger_BigInteger() {
        rangeIncreasing_BigInteger_BigInteger_helper("10", "20",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_i");
        rangeIncreasing_BigInteger_BigInteger_helper("10", "10",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_ii");
        rangeIncreasing_BigInteger_BigInteger_helper("-20", "-10",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_iii");
        rangeIncreasing_BigInteger_BigInteger_helper("-20", "-20",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_iv");
        rangeIncreasing_BigInteger_BigInteger_helper("0", "0",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_v");
        rangeIncreasing_BigInteger_BigInteger_helper("0", "10",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_vi");
        rangeIncreasing_BigInteger_BigInteger_helper("-5", "0",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_vii");
        rangeIncreasing_BigInteger_BigInteger_helper("-5", "10",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_viii");
        rangeIncreasing_BigInteger_BigInteger_helper("-10", "5",
                "ExhaustiveProvider_rangeIncreasing_BigInteger_BigInteger_ix");

        rangeIncreasing_BigInteger_BigInteger_fail_helper("10", "9");
        rangeIncreasing_BigInteger_BigInteger_fail_helper("-20", "-21");
        rangeIncreasing_BigInteger_BigInteger_fail_helper("5", "-10");
    }

    private static void rangeIncreasing_char_char_helper(char a, char b, @NotNull String output) {
        aeqitLog(EP.rangeIncreasing(a, b), output);
    }

    private static void rangeIncreasing_char_char_fail_helper(char a, char b) {
        try {
            EP.rangeIncreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeIncreasing_char_char() {
        rangeIncreasing_char_char_helper('a', 'z', "ExhaustiveProvider_rangeIncreasing_char_char_i");
        rangeIncreasing_char_char_helper('a', 'a', "ExhaustiveProvider_rangeIncreasing_char_char_ii");
        rangeIncreasing_char_char_helper('!', '9', "ExhaustiveProvider_rangeIncreasing_char_char_iii");

        rangeIncreasing_char_char_fail_helper('a', 'A');
    }

    private static void rangeDecreasing_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeDecreasing((byte) a, (byte) b), output);
    }

    private static void rangeDecreasing_byte_byte_fail_helper(int a, int b) {
        try {
            EP.rangeDecreasing((byte) a, (byte) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_byte_byte() {
        rangeDecreasing_byte_byte_helper(10, 20, "ExhaustiveProvider_rangeDecreasing_byte_byte_i");
        rangeDecreasing_byte_byte_helper(10, 10, "ExhaustiveProvider_rangeDecreasing_byte_byte_ii");
        rangeDecreasing_byte_byte_helper(-20, -10, "ExhaustiveProvider_rangeDecreasing_byte_byte_iii");
        rangeDecreasing_byte_byte_helper(-20, -20, "ExhaustiveProvider_rangeDecreasing_byte_byte_iv");
        rangeDecreasing_byte_byte_helper(0, 0, "ExhaustiveProvider_rangeDecreasing_byte_byte_v");
        rangeDecreasing_byte_byte_helper(0, 10, "ExhaustiveProvider_rangeDecreasing_byte_byte_vi");
        rangeDecreasing_byte_byte_helper(-5, 0, "ExhaustiveProvider_rangeDecreasing_byte_byte_vii");
        rangeDecreasing_byte_byte_helper(-5, 10, "ExhaustiveProvider_rangeDecreasing_byte_byte_viii");
        rangeDecreasing_byte_byte_helper(-10, 5, "ExhaustiveProvider_rangeDecreasing_byte_byte_ix");

        rangeDecreasing_byte_byte_fail_helper(10, 9);
        rangeDecreasing_byte_byte_fail_helper(-20, -21);
        rangeDecreasing_byte_byte_fail_helper(5, -10);
    }

    private static void rangeDecreasing_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeDecreasing((short) a, (short) b), output);
    }

    private static void rangeDecreasing_short_short_fail_helper(int a, int b) {
        try {
            EP.rangeDecreasing((short) a, (short) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_short_short() {
        rangeDecreasing_short_short_helper(10, 20, "ExhaustiveProvider_rangeDecreasing_short_short_i");
        rangeDecreasing_short_short_helper(10, 10, "ExhaustiveProvider_rangeDecreasing_short_short_ii");
        rangeDecreasing_short_short_helper(-20, -10, "ExhaustiveProvider_rangeDecreasing_short_short_iii");
        rangeDecreasing_short_short_helper(-20, -20, "ExhaustiveProvider_rangeDecreasing_short_short_iv");
        rangeDecreasing_short_short_helper(0, 0, "ExhaustiveProvider_rangeDecreasing_short_short_v");
        rangeDecreasing_short_short_helper(0, 10, "ExhaustiveProvider_rangeDecreasing_short_short_vi");
        rangeDecreasing_short_short_helper(-5, 0, "ExhaustiveProvider_rangeDecreasing_short_short_vii");
        rangeDecreasing_short_short_helper(-5, 10, "ExhaustiveProvider_rangeDecreasing_short_short_viii");
        rangeDecreasing_short_short_helper(-10, 5, "ExhaustiveProvider_rangeDecreasing_short_short_ix");

        rangeDecreasing_short_short_fail_helper(10, 9);
        rangeDecreasing_short_short_fail_helper(-20, -21);
        rangeDecreasing_short_short_fail_helper(5, -10);
    }

    private static void rangeDecreasing_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.rangeDecreasing(a, b), output);
    }

    private static void rangeDecreasing_int_int_fail_helper(int a, int b) {
        try {
            EP.rangeDecreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_int_int() {
        rangeDecreasing_int_int_helper(10, 20, "ExhaustiveProvider_rangeDecreasing_int_int_i");
        rangeDecreasing_int_int_helper(10, 10, "ExhaustiveProvider_rangeDecreasing_int_int_ii");
        rangeDecreasing_int_int_helper(-20, -10, "ExhaustiveProvider_rangeDecreasing_int_int_iii");
        rangeDecreasing_int_int_helper(-20, -20, "ExhaustiveProvider_rangeDecreasing_int_int_iv");
        rangeDecreasing_int_int_helper(0, 0, "ExhaustiveProvider_rangeDecreasing_int_int_v");
        rangeDecreasing_int_int_helper(0, 10, "ExhaustiveProvider_rangeDecreasing_int_int_vi");
        rangeDecreasing_int_int_helper(-5, 0, "ExhaustiveProvider_rangeDecreasing_int_int_vii");
        rangeDecreasing_int_int_helper(-5, 10, "ExhaustiveProvider_rangeDecreasing_int_int_viii");
        rangeDecreasing_int_int_helper(-10, 5, "ExhaustiveProvider_rangeDecreasing_int_int_ix");

        rangeDecreasing_int_int_fail_helper(10, 9);
        rangeDecreasing_int_int_fail_helper(-20, -21);
        rangeDecreasing_int_int_fail_helper(5, -10);
    }

    private static void rangeDecreasing_long_long_helper(long a, long b, @NotNull String output) {
        aeqitLog(EP.rangeDecreasing(a, b), output);
    }

    private static void rangeDecreasing_long_long_fail_helper(long a, long b) {
        try {
            EP.rangeDecreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_long_long() {
        rangeDecreasing_long_long_helper(10L, 20L, "ExhaustiveProvider_rangeDecreasing_long_long_i");
        rangeDecreasing_long_long_helper(10L, 10L, "ExhaustiveProvider_rangeDecreasing_long_long_ii");
        rangeDecreasing_long_long_helper(-20L, -10L, "ExhaustiveProvider_rangeDecreasing_long_long_iii");
        rangeDecreasing_long_long_helper(-20L, -20L, "ExhaustiveProvider_rangeDecreasing_long_long_iv");
        rangeDecreasing_long_long_helper(0L, 0L, "ExhaustiveProvider_rangeDecreasing_long_long_v");
        rangeDecreasing_long_long_helper(0L, 10L, "ExhaustiveProvider_rangeDecreasing_long_long_vi");
        rangeDecreasing_long_long_helper(-5L, 0L, "ExhaustiveProvider_rangeDecreasing_long_long_vii");
        rangeDecreasing_long_long_helper(-5L, 10L, "ExhaustiveProvider_rangeDecreasing_long_long_viii");
        rangeDecreasing_long_long_helper(-10L, 5L, "ExhaustiveProvider_rangeDecreasing_long_long_ix");

        rangeDecreasing_long_long_fail_helper(10L, 9L);
        rangeDecreasing_long_long_fail_helper(-20L, -21L);
        rangeDecreasing_long_long_fail_helper(5L, -10L);
    }

    private static void rangeDecreasing_BigInteger_BigInteger_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeqitLog(
                EP.rangeDecreasing(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get()),
                output
        );
    }

    private static void rangeDecreasing_BigInteger_BigInteger_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.rangeDecreasing(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_BigInteger_BigInteger() {
        rangeDecreasing_BigInteger_BigInteger_helper("10", "20",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_i");
        rangeDecreasing_BigInteger_BigInteger_helper("10", "10",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_ii");
        rangeDecreasing_BigInteger_BigInteger_helper("-20", "-10",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_iii");
        rangeDecreasing_BigInteger_BigInteger_helper("-20", "-20",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_iv");
        rangeDecreasing_BigInteger_BigInteger_helper("0", "0",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_v");
        rangeDecreasing_BigInteger_BigInteger_helper("0", "10",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_vi");
        rangeDecreasing_BigInteger_BigInteger_helper("-5", "0",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_vii");
        rangeDecreasing_BigInteger_BigInteger_helper("-5", "10",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_viii");
        rangeDecreasing_BigInteger_BigInteger_helper("-10", "5",
                "ExhaustiveProvider_rangeDecreasing_BigInteger_BigInteger_ix");

        rangeDecreasing_BigInteger_BigInteger_fail_helper("10", "9");
        rangeDecreasing_BigInteger_BigInteger_fail_helper("-20", "-21");
        rangeDecreasing_BigInteger_BigInteger_fail_helper("5", "-10");
    }

    private static void rangeDecreasing_char_char_helper(char a, char b, @NotNull String output) {
        aeqitLog(EP.rangeDecreasing(a, b), output);
    }

    private static void rangeDecreasing_char_char_fail_helper(char a, char b) {
        try {
            EP.rangeDecreasing(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeDecreasing_char_char() {
        rangeDecreasing_char_char_helper('a', 'z', "ExhaustiveProvider_rangeDecreasing_char_char_i");
        rangeDecreasing_char_char_helper('a', 'a', "ExhaustiveProvider_rangeDecreasing_char_char_ii");
        rangeDecreasing_char_char_helper('!', '9', "ExhaustiveProvider_rangeDecreasing_char_char_iii");

        rangeDecreasing_char_char_fail_helper('a', 'A');
    }

    @Test
    public void testBooleans() {
        simpleProviderHelper(EP.booleans(), "ExhaustiveProvider_booleans");
    }

    @Test
    public void testOrderingsIncreasing() {
        simpleProviderHelper(EP.orderingsIncreasing(), "ExhaustiveProvider_orderingsIncreasing");
    }

    @Test
    public void testOrderings() {
        simpleProviderHelper(EP.orderings(), "ExhaustiveProvider_orderings");
    }

    private static void uniformSample_Iterable_helper(@NotNull String xs, @NotNull String output) {
        aeqitLog(EP.uniformSample(readIntegerListWithNulls(xs)), output);
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper("[3, 1, 4, 1]", "ExhaustiveProvider_uniformSample_Iterable_i");
        uniformSample_Iterable_helper("[]", "ExhaustiveProvider_uniformSample_Iterable_ii");
        uniformSample_Iterable_helper("[3, 1, null, 1]", "ExhaustiveProvider_uniformSample_Iterable_iii");
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        aeqitLog(EP.uniformSample(s), output);
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper("hello", "ExhaustiveProvider_uniformSample_String_i");
        uniformSample_String_helper("", "ExhaustiveProvider_uniformSample_String_ii");
    }

    @Test
    public void testBytesIncreasing() {
        aeq(length(EP.bytesIncreasing()), 256);
        simpleProviderHelper(EP.bytesIncreasing(), "ExhaustiveProvider_bytesIncreasing");
    }

    @Test
    public void testShortsIncreasing() {
        aeq(length(EP.shortsIncreasing()), 65536);
        simpleProviderHelper(EP.shortsIncreasing(), "ExhaustiveProvider_shortsIncreasing");
    }

    @Test
    public void testIntegersIncreasing() {
        simpleProviderHelper(EP.integersIncreasing(), "ExhaustiveProvider_integersIncreasing");}

    @Test
    public void testLongsIncreasing() {
        simpleProviderHelper(EP.longsIncreasing(), "ExhaustiveProvider_longsIncreasing");
    }

    @Test
    public void testPositiveBytes() {
        aeq(length(EP.positiveBytes()), 127);
        simpleProviderHelper(EP.positiveBytes(), "ExhaustiveProvider_positiveBytes");
    }

    @Test
    public void testPositiveShorts() {
        aeq(length(EP.positiveShorts()), 32767);
        simpleProviderHelper(EP.positiveShorts(), "ExhaustiveProvider_positiveShorts");
    }

    @Test
    public void testPositiveIntegers() {
        simpleProviderHelper(EP.positiveIntegers(), "ExhaustiveProvider_positiveIntegers");
    }

    @Test
    public void testPositiveLongs() {
        simpleProviderHelper(EP.positiveLongs(), "ExhaustiveProvider_positiveLongs");
    }

    @Test
    public void testPositiveBigIntegers() {
        simpleProviderHelper(EP.positiveBigIntegers(), "ExhaustiveProvider_positiveBigIntegers");
    }

    @Test
    public void testNegativeBytes() {
        aeq(length(EP.negativeBytes()), 128);
        simpleProviderHelper(EP.negativeBytes(), "ExhaustiveProvider_negativeBytes");
    }

    @Test
    public void testNegativeShorts() {
        aeq(length(EP.negativeShorts()), 32768);
        simpleProviderHelper(EP.negativeShorts(), "ExhaustiveProvider_negativeShorts");
    }

    @Test
    public void testNegativeIntegers() {
        simpleProviderHelper(EP.negativeIntegers(), "ExhaustiveProvider_negativeIntegers");
    }

    @Test
    public void testNegativeLongs() {
        simpleProviderHelper(EP.negativeLongs(), "ExhaustiveProvider_negativeLongs");
    }

    @Test
    public void testNegativeBigIntegers() {
        simpleProviderHelper(EP.negativeBigIntegers(), "ExhaustiveProvider_negativeBigIntegers");
    }

    @Test
    public void testNaturalBytes() {
        aeq(length(EP.naturalBytes()), 128);
        simpleProviderHelper(EP.naturalBytes(), "ExhaustiveProvider_naturalBytes");
    }

    @Test
    public void testNaturalShorts() {
        aeq(length(EP.naturalShorts()), 32768);
        simpleProviderHelper(EP.naturalShorts(), "ExhaustiveProvider_naturalShorts");
    }

    @Test
    public void testNaturalIntegers() {
        simpleProviderHelper(EP.naturalIntegers(), "ExhaustiveProvider_naturalIntegers");
    }

    @Test
    public void testNaturalLongs() {
        simpleProviderHelper(EP.naturalLongs(), "ExhaustiveProvider_naturalLongs");
    }

    @Test
    public void testNaturalBigIntegers() {
        simpleProviderHelper(EP.naturalBigIntegers(), "ExhaustiveProvider_naturalBigIntegers");
    }

    @Test
    public void testNonzeroBytes() {
        Iterable<Byte> bs = EP.nonzeroBytes();
        aeq(length(bs), 255);
        aeqitLimitLog(TINY_LIMIT, reverse(bs), "ExhaustiveProvider_nonzeroBytes_i");
        simpleProviderHelper(bs, "ExhaustiveProvider_nonzeroBytes_ii");
    }

    @Test
    public void testNonzeroShorts() {
        Iterable<Short> ss = EP.nonzeroShorts();
        aeq(length(ss), 65535);
        aeqitLimitLog(TINY_LIMIT, reverse(ss), "ExhaustiveProvider_nonzeroShorts_i");
        simpleProviderHelper(ss, "ExhaustiveProvider_nonzeroShorts_ii");
    }

    @Test
    public void testNonzeroIntegers() {
        simpleProviderHelper(EP.nonzeroIntegers(), "ExhaustiveProvider_nonzeroIntegers");
    }

    @Test
    public void testNonzeroLongs() {
        simpleProviderHelper(EP.nonzeroLongs(), "ExhaustiveProvider_nonzeroLongs");
    }

    @Test
    public void testNonzeroBigIntegers() {
        simpleProviderHelper(EP.nonzeroBigIntegers(), "ExhaustiveProvider_nonzeroBigIntegers");
    }

    @Test
    public void testBytes() {
        Iterable<Byte> bs = EP.bytes();
        aeq(length(bs), 256);
        aeqitLimitLog(TINY_LIMIT, reverse(bs), "ExhaustiveProvider_bytes_i");
        simpleProviderHelper(bs, "ExhaustiveProvider_bytes_ii");
    }

    @Test
    public void testShorts() {
        Iterable<Short> ss = EP.shorts();
        aeq(length(ss), 65536);
        aeqitLimitLog(TINY_LIMIT, reverse(ss), "ExhaustiveProvider_shorts_i");
        simpleProviderHelper(ss, "ExhaustiveProvider_shorts_ii");
    }

    @Test
    public void testIntegers() {
        simpleProviderHelper(EP.integers(), "ExhaustiveProvider_integers");
    }

    @Test
    public void testLongs() {
        simpleProviderHelper(EP.longs(), "ExhaustiveProvider_longs");
    }

    @Test
    public void testBigIntegers() {
        simpleProviderHelper(EP.bigIntegers(), "ExhaustiveProvider_bigIntegers");
    }

    @Test
    public void testAsciiCharactersIncreasing() {
        aeq(length(EP.asciiCharactersIncreasing()), 128);
        aeqitLog(EP.asciiCharactersIncreasing(),"ExhaustiveProvider_asciiCharactersIncreasing");
    }

    @Test
    public void testAsciiCharacters() {
        aeq(length(EP.asciiCharacters()), 128);
        aeqitLog(EP.asciiCharacters(), "ExhaustiveProvider_asciiCharacters");
    }

    @Test
    public void testCharactersIncreasing() {
        aeq(length(EP.charactersIncreasing()), 65536);
        aeqitLimitLog(256, EP.charactersIncreasing(), "ExhaustiveProvider_charactersIncreasing");
    }

    @Test
    public void testCharacters() {
        aeq(length(EP.characters()), 65536);
        aeqitLimitLog(256, EP.characters(), "ExhaustiveProvider_characters");
    }

    @Test
    public void testRoundingModes() {
        simpleProviderHelper(EP.roundingModes(), "ExhaustiveProvider_roundingModes");
    }

    private static void rangeUp_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp((byte) a), output);
    }

    @Test
    public void testRangeUp_byte() {
        rangeUp_byte_helper(0, "ExhaustiveProvider_rangeUp_byte_i");
        rangeUp_byte_helper(5, "ExhaustiveProvider_rangeUp_byte_ii");
        rangeUp_byte_helper(-5, "ExhaustiveProvider_rangeUp_byte_iii");
        rangeUp_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeUp_byte_iv");
        rangeUp_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeUp_byte_v");
    }

    private static void rangeUp_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp((short) a), output);
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper(0, "ExhaustiveProvider_rangeUp_short_i");
        rangeUp_short_helper(5, "ExhaustiveProvider_rangeUp_short_ii");
        rangeUp_short_helper(-5, "ExhaustiveProvider_rangeUp_short_iii");
        rangeUp_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeUp_short_iv");
        rangeUp_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeUp_short_v");
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(0, "ExhaustiveProvider_rangeUp_int_i");
        rangeUp_int_helper(5, "ExhaustiveProvider_rangeUp_int_ii");
        rangeUp_int_helper(-5, "ExhaustiveProvider_rangeUp_int_iii");
        rangeUp_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeUp_int_iv");
        rangeUp_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeUp_int_v");
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(0, "ExhaustiveProvider_rangeUp_long_i");
        rangeUp_long_helper(5, "ExhaustiveProvider_rangeUp_long_ii");
        rangeUp_long_helper(-5, "ExhaustiveProvider_rangeUp_long_iii");
        rangeUp_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeUp_long_iv");
        rangeUp_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeUp_long_v");
    }

    private static void rangeUp_BigInteger_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(Readers.readBigIntegerStrict(a).get()), output);
    }

    @Test
    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper("0", "ExhaustiveProvider_rangeUp_BigInteger_i");
        rangeUp_BigInteger_helper("5", "ExhaustiveProvider_rangeUp_BigInteger_ii");
        rangeUp_BigInteger_helper("-5", "ExhaustiveProvider_rangeUp_BigInteger_iii");
    }

    private static void rangeDown_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown((byte) a), output);
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper(0, "ExhaustiveProvider_rangeDown_byte_i");
        rangeDown_byte_helper(5, "ExhaustiveProvider_rangeDown_byte_ii");
        rangeDown_byte_helper(-5,"ExhaustiveProvider_rangeDown_byte_iii");
        rangeDown_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeDown_byte_iv");
        rangeDown_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeDown_byte_v");
    }

    private static void rangeDown_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown((short) a), output);
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper(0, "ExhaustiveProvider_rangeDown_short_i");
        rangeDown_short_helper(5, "ExhaustiveProvider_rangeDown_short_ii");
        rangeDown_short_helper(-5,"ExhaustiveProvider_rangeDown_short_iii");
        rangeDown_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeDown_short_iv");
        rangeDown_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeDown_short_v");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(0, "ExhaustiveProvider_rangeDown_int_i");
        rangeDown_int_helper(5, "ExhaustiveProvider_rangeDown_int_ii");
        rangeDown_int_helper(-5,"ExhaustiveProvider_rangeDown_int_iii");
        rangeDown_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeDown_int_iv");
        rangeDown_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeDown_int_v");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(0, "ExhaustiveProvider_rangeDown_long_i");
        rangeDown_long_helper(5, "ExhaustiveProvider_rangeDown_long_ii");
        rangeDown_long_helper(-5,"ExhaustiveProvider_rangeDown_long_iii");
        rangeDown_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeDown_long_iv");
        rangeDown_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeDown_long_v");
    }

    private static void rangeDown_BigInteger_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(Readers.readBigIntegerStrict(a).get()), output);
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper("0", "ExhaustiveProvider_rangeDown_BigInteger_i");
        rangeDown_BigInteger_helper("5", "ExhaustiveProvider_rangeDown_BigInteger_ii");
        rangeDown_BigInteger_helper("-5", "ExhaustiveProvider_rangeDown_BigInteger_iii");
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range((byte) a, (byte) b), output);
    }

    private static void range_byte_byte_fail_helper(int a, int b) {
        try {
            EP.range((byte) a, (byte) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(10, 20, "ExhaustiveProvider_range_byte_byte_i");
        range_byte_byte_helper(10, 10, "ExhaustiveProvider_range_byte_byte_ii");
        range_byte_byte_helper(-20, -10, "ExhaustiveProvider_range_byte_byte_iii");
        range_byte_byte_helper(-20, -20, "ExhaustiveProvider_range_byte_byte_iv");
        range_byte_byte_helper(0, 0, "ExhaustiveProvider_range_byte_byte_v");
        range_byte_byte_helper(0, 10, "ExhaustiveProvider_range_byte_byte_vi");
        range_byte_byte_helper(-5, 0, "ExhaustiveProvider_range_byte_byte_vii");
        range_byte_byte_helper(-5, 10, "ExhaustiveProvider_range_byte_byte_viii");
        range_byte_byte_helper(-10, 5, "ExhaustiveProvider_range_byte_byte_ix");

        range_byte_byte_fail_helper(10, 9);
        range_byte_byte_fail_helper(-20, -21);
        range_byte_byte_fail_helper(5, -10);
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range((short) a, (short) b), output);
    }

    private static void range_short_short_fail_helper(int a, int b) {
        try {
            EP.range((short) a, (short) b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(10, 20, "ExhaustiveProvider_range_short_short_i");
        range_short_short_helper(10, 10, "ExhaustiveProvider_range_short_short_ii");
        range_short_short_helper(-20, -10, "ExhaustiveProvider_range_short_short_iii");
        range_short_short_helper(-20, -20, "ExhaustiveProvider_range_short_short_iv");
        range_short_short_helper(0, 0, "ExhaustiveProvider_range_short_short_v");
        range_short_short_helper(0, 10, "ExhaustiveProvider_range_short_short_vi");
        range_short_short_helper(-5, 0, "ExhaustiveProvider_range_short_short_vii");
        range_short_short_helper(-5, 10, "ExhaustiveProvider_range_short_short_viii");
        range_short_short_helper(-10, 5, "ExhaustiveProvider_range_short_short_ix");

        range_short_short_fail_helper(10, 9);
        range_short_short_fail_helper(-20, -21);
        range_short_short_fail_helper(5, -10);
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range(a, b), output);
    }

    private static void range_int_int_fail_helper(int a, int b) {
        try {
            EP.range(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_int_int() {
        range_int_int_helper(10, 20, "ExhaustiveProvider_range_int_int_i");
        range_int_int_helper(10, 10, "ExhaustiveProvider_range_int_int_ii");
        range_int_int_helper(-20, -10, "ExhaustiveProvider_range_int_int_iii");
        range_int_int_helper(-20, -20, "ExhaustiveProvider_range_int_int_iv");
        range_int_int_helper(0, 0, "ExhaustiveProvider_range_int_int_v");
        range_int_int_helper(0, 10, "ExhaustiveProvider_range_int_int_vi");
        range_int_int_helper(-5, 0, "ExhaustiveProvider_range_int_int_vii");
        range_int_int_helper(-5, 10, "ExhaustiveProvider_range_int_int_viii");
        range_int_int_helper(-10, 5, "ExhaustiveProvider_range_int_int_ix");

        range_int_int_fail_helper(10, 9);
        range_int_int_fail_helper(-20, -21);
        range_int_int_fail_helper(5, -10);
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        aeqitLog(EP.range(a, b), output);
    }

    private static void range_long_long_fail_helper(long a, long b) {
        try {
            EP.range(a, b);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_long_long() {
        range_long_long_helper(10L, 20L, "ExhaustiveProvider_range_long_long_i");
        range_long_long_helper(10L, 10L, "ExhaustiveProvider_range_long_long_ii");
        range_long_long_helper(-20L, -10L, "ExhaustiveProvider_range_long_long_iii");
        range_long_long_helper(-20L, -20L, "ExhaustiveProvider_range_long_long_iv");
        range_long_long_helper(0L, 0L, "ExhaustiveProvider_range_long_long_v");
        range_long_long_helper(0L, 10L, "ExhaustiveProvider_range_long_long_vi");
        range_long_long_helper(-5L, 0L, "ExhaustiveProvider_range_long_long_vii");
        range_long_long_helper(-5L, 10L, "ExhaustiveProvider_range_long_long_viii");
        range_long_long_helper(-10L, 5L, "ExhaustiveProvider_range_long_long_ix");

        range_long_long_fail_helper(10L, 9L);
        range_long_long_fail_helper(-20L, -21L);
        range_long_long_fail_helper(5L, -10L);
    }

    private static void range_BigInteger_BigInteger_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeqitLog(EP.range(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get()), output);
    }

    private static void range_BigInteger_BigInteger_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.range(Readers.readBigIntegerStrict(a).get(), Readers.readBigIntegerStrict(b).get());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_BigInteger_BigInteger() {
        range_BigInteger_BigInteger_helper("10", "20", "ExhaustiveProvider_range_BigInteger_BigInteger_i");
        range_BigInteger_BigInteger_helper("10", "10", "ExhaustiveProvider_range_BigInteger_BigInteger_ii");
        range_BigInteger_BigInteger_helper("-20", "-10", "ExhaustiveProvider_range_BigInteger_BigInteger_iii");
        range_BigInteger_BigInteger_helper("-20", "-20", "ExhaustiveProvider_range_BigInteger_BigInteger_iv");
        range_BigInteger_BigInteger_helper("0", "0", "ExhaustiveProvider_range_BigInteger_BigInteger_v");
        range_BigInteger_BigInteger_helper("0", "10", "ExhaustiveProvider_range_BigInteger_BigInteger_vi");
        range_BigInteger_BigInteger_helper("-5", "0", "ExhaustiveProvider_range_BigInteger_BigInteger_vii");
        range_BigInteger_BigInteger_helper("-5", "10", "ExhaustiveProvider_range_BigInteger_BigInteger_viii");
        range_BigInteger_BigInteger_helper("-10", "5", "ExhaustiveProvider_range_BigInteger_BigInteger_ix");

        range_BigInteger_BigInteger_fail_helper("10", "9");
        range_BigInteger_BigInteger_fail_helper("-20", "-21");
        range_BigInteger_BigInteger_fail_helper("5", "-10");
    }

    @Test
    public void testPositiveBinaryFractions() {
        simpleProviderHelper(EP.positiveBinaryFractions(), "ExhaustiveProvider_positiveBinaryFractions");
    }

    @Test
    public void testNegativeBinaryFractions() {
        simpleProviderHelper(EP.negativeBinaryFractions(), "ExhaustiveProvider_negativeBinaryFractions");
    }

    @Test
    public void testNonzeroBinaryFractions() {
        simpleProviderHelper(EP.nonzeroBinaryFractions(), "ExhaustiveProvider_nonzeroBinaryFractions");
    }

    @Test
    public void testBinaryFractions() {
        simpleProviderHelper(EP.binaryFractions(), "ExhaustiveProvider_binaryFractions");
    }

    private static void rangeUp_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(BinaryFraction.readStrict(a).get()), output);
    }

    @Test
    public void testRangeUp_BinaryFraction() {
        rangeUp_BinaryFraction_helper("0", "ExhaustiveProvider_rangeUp_BinaryFraction_i");
        rangeUp_BinaryFraction_helper("1", "ExhaustiveProvider_rangeUp_BinaryFraction_ii");
        rangeUp_BinaryFraction_helper("11", "ExhaustiveProvider_rangeUp_BinaryFraction_iii");
        rangeUp_BinaryFraction_helper("5 << 20", "ExhaustiveProvider_rangeUp_BinaryFraction_iv");
        rangeUp_BinaryFraction_helper("5 >> 20", "ExhaustiveProvider_rangeUp_BinaryFraction_v");
        rangeUp_BinaryFraction_helper("-1", "ExhaustiveProvider_rangeUp_BinaryFraction_vi");
        rangeUp_BinaryFraction_helper("-11", "ExhaustiveProvider_rangeUp_BinaryFraction_vii");
        rangeUp_BinaryFraction_helper("-5 << 20", "ExhaustiveProvider_rangeUp_BinaryFraction_viii");
        rangeUp_BinaryFraction_helper("-5 >> 20", "ExhaustiveProvider_rangeUp_BinaryFraction_ix");
    }

    private static void rangeDown_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(BinaryFraction.readStrict(a).get()), output);
    }

    @Test
    public void testRangeDown_BinaryFraction() {
        rangeDown_BinaryFraction_helper("0", "ExhaustiveProvider_rangeDown_BinaryFraction_i");
        rangeDown_BinaryFraction_helper("1", "ExhaustiveProvider_rangeDown_BinaryFraction_ii");
        rangeDown_BinaryFraction_helper("11", "ExhaustiveProvider_rangeDown_BinaryFraction_iii");
        rangeDown_BinaryFraction_helper("5 << 20", "ExhaustiveProvider_rangeDown_BinaryFraction_iv");
        rangeDown_BinaryFraction_helper("5 >> 20", "ExhaustiveProvider_rangeDown_BinaryFraction_v");
        rangeDown_BinaryFraction_helper("-1", "ExhaustiveProvider_rangeDown_BinaryFraction_vi");
        rangeDown_BinaryFraction_helper("-11", "ExhaustiveProvider_rangeDown_BinaryFraction_vii");
        rangeDown_BinaryFraction_helper("-5 << 20", "ExhaustiveProvider_rangeDown_BinaryFraction_viii");
        rangeDown_BinaryFraction_helper("-5 >> 20", "ExhaustiveProvider_rangeDown_BinaryFraction_ix");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.range(BinaryFraction.readStrict(a).get(), BinaryFraction.readStrict(b).get()), output);
    }

    private static void range_BinaryFraction_BinaryFraction_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.range(BinaryFraction.readStrict(a).get(), BinaryFraction.readStrict(b).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper("0", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_i");
        range_BinaryFraction_BinaryFraction_helper("1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_ii");
        range_BinaryFraction_BinaryFraction_helper("11", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_iii");
        range_BinaryFraction_BinaryFraction_helper("-11", "-1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_iv");
        range_BinaryFraction_BinaryFraction_helper("-11", "-11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_v");
        range_BinaryFraction_BinaryFraction_helper("0", "0",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_vi");
        range_BinaryFraction_BinaryFraction_helper("0", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_vii");
        range_BinaryFraction_BinaryFraction_helper("-1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_viii");
        range_BinaryFraction_BinaryFraction_helper("5 >> 20", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_ix");
        range_BinaryFraction_BinaryFraction_helper("1", "5 << 20",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_x");

        range_BinaryFraction_BinaryFraction_fail_helper("11", "1");
        range_BinaryFraction_BinaryFraction_fail_helper("-1", "-11");
    }

    @Test
    public void testPositiveFloats() {
        aeqitLimitLog(SMALLER_LIMIT, EP.positiveFloats(), "ExhaustiveProvider_positiveFloats");
    }

    @Test
    public void testNegativeFloats() {
        aeqitLimitLog(SMALLER_LIMIT, EP.negativeFloats(), "ExhaustiveProvider_negativeFloats");
    }

    @Test
    public void testNonzeroFloats() {
        aeqitLimitLog(SMALLER_LIMIT, EP.nonzeroFloats(), "ExhaustiveProvider_nonzeroFloats");
    }

    @Test
    public void testFloats() {
        aeqitLimitLog(SMALLER_LIMIT, EP.floats(), "ExhaustiveProvider_floats");
    }

    @Test
    public void testPositiveDoubles() {
        aeqitLimitLog(SMALLER_LIMIT, EP.positiveDoubles(), "ExhaustiveProvider_positiveDoubles");
    }

    @Test
    public void testNegativeDoubles() {
        aeqitLimitLog(SMALLER_LIMIT, EP.negativeDoubles(), "ExhaustiveProvider_negativeDoubles");
    }

    @Test
    public void testNonzeroDoubles() {
        aeqitLimitLog(SMALLER_LIMIT, EP.nonzeroDoubles(), "ExhaustiveProvider_nonzeroDoubles");
    }

    @Test
    public void testDoubles() {
        aeqitLimitLog(SMALLER_LIMIT, EP.doubles(), "ExhaustiveProvider_doubles");
    }

    private static void rangeUp_float_helper(float a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    private static void rangeUp_float_fail_helper(float a) {
        try {
            EP.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_float() {
        rangeUp_float_helper(1.0f, "ExhaustiveProvider_rangeUp_float_i");
        rangeUp_float_helper(1.0E20f, "ExhaustiveProvider_rangeUp_float_ii");
        rangeUp_float_helper(-1.0f, "ExhaustiveProvider_rangeUp_float_iii");
        rangeUp_float_helper(-1.0E20f, "ExhaustiveProvider_rangeUp_float_iv");
        rangeUp_float_helper((float) Math.PI, "ExhaustiveProvider_rangeUp_float_v");
        rangeUp_float_helper((float) Math.sqrt(2), "ExhaustiveProvider_rangeUp_float_vi");
        rangeUp_float_helper((float) -Math.PI, "ExhaustiveProvider_rangeUp_float_vii");
        rangeUp_float_helper((float) -Math.sqrt(2), "ExhaustiveProvider_rangeUp_float_viii");
        rangeUp_float_helper(0.0f, "ExhaustiveProvider_rangeUp_float_ix");
        rangeUp_float_helper(-0.0f, "ExhaustiveProvider_rangeUp_float_x");
        rangeUp_float_helper(Float.MIN_VALUE, "ExhaustiveProvider_rangeUp_float_xi");
        rangeUp_float_helper(Float.MIN_NORMAL, "ExhaustiveProvider_rangeUp_float_xii");
        rangeUp_float_helper(-Float.MIN_VALUE, "ExhaustiveProvider_rangeUp_float_xiii");
        rangeUp_float_helper(-Float.MIN_NORMAL, "ExhaustiveProvider_rangeUp_float_xiv");
        rangeUp_float_helper(Float.MAX_VALUE, "ExhaustiveProvider_rangeUp_float_xv");
        rangeUp_float_helper(-Float.MAX_VALUE, "ExhaustiveProvider_rangeUp_float_xvi");
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "ExhaustiveProvider_rangeUp_float_xvii");
        rangeUp_float_helper(Float.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeUp_float_xviii");

        rangeUp_float_fail_helper(Float.NaN);
    }

    private static void rangeDown_float_helper(float a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    private static void rangeDown_float_fail_helper(float a) {
        try {
            EP.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_float() {
        rangeDown_float_helper(1.0f, "ExhaustiveProvider_rangeDown_float_i");
        rangeDown_float_helper(1.0E20f, "ExhaustiveProvider_rangeDown_float_ii");
        rangeDown_float_helper(-1.0f, "ExhaustiveProvider_rangeDown_float_iii");
        rangeDown_float_helper(-1.0E20f, "ExhaustiveProvider_rangeDown_float_iv");
        rangeDown_float_helper((float) Math.PI, "ExhaustiveProvider_rangeDown_float_v");
        rangeDown_float_helper((float) Math.sqrt(2), "ExhaustiveProvider_rangeDown_float_vi");
        rangeDown_float_helper((float) -Math.PI, "ExhaustiveProvider_rangeDown_float_vii");
        rangeDown_float_helper((float) -Math.sqrt(2), "ExhaustiveProvider_rangeDown_float_viii");
        rangeDown_float_helper(0.0f, "ExhaustiveProvider_rangeDown_float_ix");
        rangeDown_float_helper(-0.0f, "ExhaustiveProvider_rangeDown_float_x");
        rangeDown_float_helper(Float.MIN_VALUE, "ExhaustiveProvider_rangeDown_float_xi");
        rangeDown_float_helper(Float.MIN_NORMAL, "ExhaustiveProvider_rangeDown_float_xii");
        rangeDown_float_helper(-Float.MIN_VALUE, "ExhaustiveProvider_rangeDown_float_xiii");
        rangeDown_float_helper(-Float.MIN_NORMAL, "ExhaustiveProvider_rangeDown_float_xiv");
        rangeDown_float_helper(Float.MAX_VALUE, "ExhaustiveProvider_rangeDown_float_xv");
        rangeDown_float_helper(-Float.MAX_VALUE, "ExhaustiveProvider_rangeDown_float_xvi");
        rangeDown_float_helper(Float.POSITIVE_INFINITY, "ExhaustiveProvider_rangeDown_float_xvii");
        rangeDown_float_helper(Float.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeDown_float_xviii");

        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void range_float_float_helper(float a, float b, @NotNull String output) {
        simpleProviderHelper(EP.range(a, b), output);
    }

    private static void range_float_float_fail_helper(float a, float b) {
        try {
            EP.range(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_float_float() {
        range_float_float_helper(1.0f, 2.0f, "ExhaustiveProvider_range_float_float_i");
        range_float_float_helper(1.0f, 3.0f, "ExhaustiveProvider_range_float_float_ii");
        range_float_float_helper(1.0f, 4.0f,"ExhaustiveProvider_range_float_float_iii");
        range_float_float_helper(1.0f, 257.0f, "ExhaustiveProvider_range_float_float_iv");
        range_float_float_helper(-257.0f, -1.0f, "ExhaustiveProvider_range_float_float_v");
        range_float_float_helper(1.0f, 1.0E20f, "ExhaustiveProvider_range_float_float_vi");
        range_float_float_helper(-1.0E20f, -1.0f, "ExhaustiveProvider_range_float_float_vii");
        range_float_float_helper((float) Math.sqrt(2), (float) Math.PI, "ExhaustiveProvider_range_float_float_viii");
        range_float_float_helper((float) Math.PI, FloatingPointUtils.successor((float) Math.PI),
                "ExhaustiveProvider_range_float_float_ix");
        range_float_float_helper(0.0f, 1.0f, "ExhaustiveProvider_range_float_float_x");
        range_float_float_helper(-1.0f, 1.0f, "ExhaustiveProvider_range_float_float_xi");
        range_float_float_helper(1.0f, 1.0f, "ExhaustiveProvider_range_float_float_xii");
        range_float_float_helper((float) Math.PI, (float) Math.PI, "ExhaustiveProvider_range_float_float_xiii");
        range_float_float_helper((float) -Math.PI, (float) Math.PI, "ExhaustiveProvider_range_float_float_xiv");
        range_float_float_helper(1.0f, Float.POSITIVE_INFINITY, "ExhaustiveProvider_range_float_float_xv");
        range_float_float_helper(Float.NEGATIVE_INFINITY, 1.0f, "ExhaustiveProvider_range_float_float_xvi");
        range_float_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xvii");
        range_float_float_helper(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE,
                "ExhaustiveProvider_range_float_float_xviii");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xix");
        range_float_float_helper(0.0f, 0.0f, "ExhaustiveProvider_range_float_float_xx");
        range_float_float_helper(-0.0f, -0.0f, "ExhaustiveProvider_range_float_float_xxi");
        range_float_float_helper(-0.0f, 0.0f, "ExhaustiveProvider_range_float_float_xxii");
        range_float_float_helper(0.0f, -0.0f, "ExhaustiveProvider_range_float_float_xxiii");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xxiv");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xxv");

        range_float_float_fail_helper(1.0f, -1.0f);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, 1.0f);
        range_float_float_fail_helper(1.0f, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(Float.NaN, 1.0f);
        range_float_float_fail_helper(Float.NaN, Float.POSITIVE_INFINITY);
        range_float_float_fail_helper(Float.NaN, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(1.0f, Float.NaN);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NEGATIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NaN, Float.NaN);
    }

    private static void rangeUp_double_helper(double a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    private static void rangeUp_double_fail_helper(double a) {
        try {
            EP.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_double() {
        rangeUp_double_helper(1.0, "ExhaustiveProvider_rangeUp_double_i");
        rangeUp_double_helper(1.0E20, "ExhaustiveProvider_rangeUp_double_ii");
        rangeUp_double_helper(-1.0, "ExhaustiveProvider_rangeUp_double_iii");
        rangeUp_double_helper(-1.0E20, "ExhaustiveProvider_rangeUp_double_iv");
        rangeUp_double_helper(Math.PI, "ExhaustiveProvider_rangeUp_double_v");
        rangeUp_double_helper(Math.sqrt(2), "ExhaustiveProvider_rangeUp_double_vi");
        rangeUp_double_helper(-Math.PI, "ExhaustiveProvider_rangeUp_double_vii");
        rangeUp_double_helper(-Math.sqrt(2), "ExhaustiveProvider_rangeUp_double_viii");
        rangeUp_double_helper(0.0, "ExhaustiveProvider_rangeUp_double_ix");
        rangeUp_double_helper(-0.0, "ExhaustiveProvider_rangeUp_double_x");
        rangeUp_double_helper(Double.MIN_VALUE, "ExhaustiveProvider_rangeUp_double_xi");
        rangeUp_double_helper(Double.MIN_NORMAL, "ExhaustiveProvider_rangeUp_double_xii");
        rangeUp_double_helper(-Double.MIN_VALUE, "ExhaustiveProvider_rangeUp_double_xiii");
        rangeUp_double_helper(-Double.MIN_NORMAL, "ExhaustiveProvider_rangeUp_double_xiv");
        rangeUp_double_helper(Double.MAX_VALUE, "ExhaustiveProvider_rangeUp_double_xv");
        rangeUp_double_helper(-Double.MAX_VALUE, "ExhaustiveProvider_rangeUp_double_xvi");
        rangeUp_double_helper(Double.POSITIVE_INFINITY, "ExhaustiveProvider_rangeUp_double_xvii");
        rangeUp_double_helper(Double.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeUp_double_xviii");

        rangeUp_double_fail_helper(Double.NaN);
    }

    private static void rangeDown_double_helper(double a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    private static void rangeDown_double_fail_helper(double a) {
        try {
            EP.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_double() {
        rangeDown_double_helper(1.0, "ExhaustiveProvider_rangeDown_double_i");
        rangeDown_double_helper(1.0E20, "ExhaustiveProvider_rangeDown_double_ii");
        rangeDown_double_helper(-1.0, "ExhaustiveProvider_rangeDown_double_iii");
        rangeDown_double_helper(-1.0E20, "ExhaustiveProvider_rangeDown_double_iv");
        rangeDown_double_helper(Math.PI, "ExhaustiveProvider_rangeDown_double_v");
        rangeDown_double_helper(Math.sqrt(2), "ExhaustiveProvider_rangeDown_double_vi");
        rangeDown_double_helper(-Math.PI, "ExhaustiveProvider_rangeDown_double_vii");
        rangeDown_double_helper(-Math.sqrt(2), "ExhaustiveProvider_rangeDown_double_viii");
        rangeDown_double_helper(0.0, "ExhaustiveProvider_rangeDown_double_ix");
        rangeDown_double_helper(-0.0, "ExhaustiveProvider_rangeDown_double_x");
        rangeDown_double_helper(Double.MIN_VALUE, "ExhaustiveProvider_rangeDown_double_xi");
        rangeDown_double_helper(Double.MIN_NORMAL, "ExhaustiveProvider_rangeDown_double_xii");
        rangeDown_double_helper(-Double.MIN_VALUE, "ExhaustiveProvider_rangeDown_double_xiii");
        rangeDown_double_helper(-Double.MIN_NORMAL, "ExhaustiveProvider_rangeDown_double_xiv");
        rangeDown_double_helper(Double.MAX_VALUE, "ExhaustiveProvider_rangeDown_double_xv");
        rangeDown_double_helper(-Double.MAX_VALUE, "ExhaustiveProvider_rangeDown_double_xvi");
        rangeDown_double_helper(Double.POSITIVE_INFINITY, "ExhaustiveProvider_rangeDown_double_xvii");
        rangeDown_double_helper(Double.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeDown_double_xviii");

        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void range_double_double_helper(double a, double b, @NotNull String output) {
        simpleProviderHelper(EP.range(a, b), output);
    }

    private static void range_double_double_fail_helper(double a, double b) {
        try {
            EP.range(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_double_double() {
        range_double_double_helper(1.0, 2.0, "ExhaustiveProvider_range_double_double_i");
        range_double_double_helper(1.0, 3.0, "ExhaustiveProvider_range_double_double_ii");
        range_double_double_helper(1.0, 4.0,"ExhaustiveProvider_range_double_double_iii");
        range_double_double_helper(1.0, 257.0, "ExhaustiveProvider_range_double_double_iv");
        range_double_double_helper(-257.0, -1.0, "ExhaustiveProvider_range_double_double_v");
        range_double_double_helper(1.0, 1.0E20, "ExhaustiveProvider_range_double_double_vi");
        range_double_double_helper(-1.0E20, -1.0, "ExhaustiveProvider_range_double_double_vii");
        range_double_double_helper(Math.sqrt(2), Math.PI, "ExhaustiveProvider_range_double_double_viii");
        range_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI),
                "ExhaustiveProvider_range_double_double_ix");
        range_double_double_helper(0.0, 1.0, "ExhaustiveProvider_range_double_double_x");
        range_double_double_helper(-1.0, 1.0, "ExhaustiveProvider_range_double_double_xi");
        range_double_double_helper(1.0, 1.0, "ExhaustiveProvider_range_double_double_xii");
        range_double_double_helper(Math.PI, Math.PI, "ExhaustiveProvider_range_double_double_xiii");
        range_double_double_helper(-Math.PI, Math.PI, "ExhaustiveProvider_range_double_double_xiv");
        range_double_double_helper(1.0, Double.POSITIVE_INFINITY, "ExhaustiveProvider_range_double_double_xv");
        range_double_double_helper(Double.NEGATIVE_INFINITY, 1.0, "ExhaustiveProvider_range_double_double_xvi");
        range_double_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xvii");
        range_double_double_helper(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE,
                "ExhaustiveProvider_range_double_double_xviii");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xix");
        range_double_double_helper(0.0, 0.0, "ExhaustiveProvider_range_double_double_xx");
        range_double_double_helper(-0.0, -0.0, "ExhaustiveProvider_range_double_double_xxi");
        range_double_double_helper(-0.0, 0.0, "ExhaustiveProvider_range_double_double_xxii");
        range_double_double_helper(0.0, -0.0, "ExhaustiveProvider_range_double_double_xxiii");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xxiv");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xxv");

        range_double_double_fail_helper(1.0f, -1.0f);
        range_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        range_double_double_fail_helper(Double.POSITIVE_INFINITY, 1.0f);
        range_double_double_fail_helper(1.0f, Double.NEGATIVE_INFINITY);
        range_double_double_fail_helper(Double.NaN, 1.0);
        range_double_double_fail_helper(Double.NaN, Double.POSITIVE_INFINITY);
        range_double_double_fail_helper(Double.NaN, Double.NEGATIVE_INFINITY);
        range_double_double_fail_helper(1.0f, Float.NaN);
        range_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NEGATIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NaN, Double.NaN);
    }

    @Test
    public void testPositiveBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.positiveBigDecimals(), "ExhaustiveProvider_positiveBigDecimals");
    }

    @Test
    public void testNegativeBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.negativeBigDecimals(), "ExhaustiveProvider_negativeBigDecimals");
    }

    @Test
    public void testNonzeroBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.nonzeroBigDecimals(), "ExhaustiveProvider_nonzeroBigDecimals");
    }

    @Test
    public void testBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.bigDecimals(), "ExhaustiveProvider_bigDecimals");
    }

    @Test
    public void testPositiveCanonicalBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.positiveCanonicalBigDecimals(),
                "ExhaustiveProvider_positiveCanonicalBigDecimals");
    }

    @Test
    public void testNegativeCanonicalBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.negativeCanonicalBigDecimals(),
                "ExhaustiveProvider_negativeCanonicalBigDecimals");
    }

    @Test
    public void testNonzeroCanonicalBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.nonzeroCanonicalBigDecimals(),
                "ExhaustiveProvider_nonzeroCanonicalBigDecimals");
    }

    @Test
    public void testCanonicalBigDecimals() {
        aeqitLimitLog(SMALLER_LIMIT, EP.canonicalBigDecimals(), "ExhaustiveProvider_canonicalBigDecimals");
    }

    private static void rangeUp_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(Readers.readBigDecimalStrict(a).get()), output);
    }

    @Test
    public void testRangeUp_BigDecimal() {
        rangeUp_BigDecimal_helper("0", "ExhaustiveProvider_rangeUp_BigDecimal_i");
        rangeUp_BigDecimal_helper("0.0", "ExhaustiveProvider_rangeUp_BigDecimal_ii");
        rangeUp_BigDecimal_helper("1", "ExhaustiveProvider_rangeUp_BigDecimal_iii");
        rangeUp_BigDecimal_helper("1.0", "ExhaustiveProvider_rangeUp_BigDecimal_iv");
        rangeUp_BigDecimal_helper("-1", "ExhaustiveProvider_rangeUp_BigDecimal_v");
        rangeUp_BigDecimal_helper("-1.0", "ExhaustiveProvider_rangeUp_BigDecimal_vi");
        rangeUp_BigDecimal_helper("9", "ExhaustiveProvider_rangeUp_BigDecimal_vii");
        rangeUp_BigDecimal_helper("-9", "ExhaustiveProvider_rangeUp_BigDecimal_viii");
        rangeUp_BigDecimal_helper("10", "ExhaustiveProvider_rangeUp_BigDecimal_ix");
        rangeUp_BigDecimal_helper("-10", "ExhaustiveProvider_rangeUp_BigDecimal_x");
        rangeUp_BigDecimal_helper("101", "ExhaustiveProvider_rangeUp_BigDecimal_xi");
        rangeUp_BigDecimal_helper("-101", "ExhaustiveProvider_rangeUp_BigDecimal_xii");
        rangeUp_BigDecimal_helper("1234567", "ExhaustiveProvider_rangeUp_BigDecimal_xiii");
        rangeUp_BigDecimal_helper("-1234567", "ExhaustiveProvider_rangeUp_BigDecimal_xiv");
        rangeUp_BigDecimal_helper("0.09", "ExhaustiveProvider_rangeUp_BigDecimal_xv");
        rangeUp_BigDecimal_helper("-0.09", "ExhaustiveProvider_rangeUp_BigDecimal_xvi");
        rangeUp_BigDecimal_helper("1E-12", "ExhaustiveProvider_rangeUp_BigDecimal_xvii");
        rangeUp_BigDecimal_helper("-1E-12", "ExhaustiveProvider_rangeUp_BigDecimal_xviii");
        rangeUp_BigDecimal_helper("1E+12", "ExhaustiveProvider_rangeUp_BigDecimal_xix");
        rangeUp_BigDecimal_helper("-1E+12", "ExhaustiveProvider_rangeUp_BigDecimal_xx");
    }

    private static void rangeDown_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(Readers.readBigDecimalStrict(a).get()), output);
    }

    @Test
    public void testRangeDown_BigDecimal() {
        rangeDown_BigDecimal_helper("0", "ExhaustiveProvider_rangeDown_BigDecimal_i");
        rangeDown_BigDecimal_helper("0.0", "ExhaustiveProvider_rangeDown_BigDecimal_ii");
        rangeDown_BigDecimal_helper("1", "ExhaustiveProvider_rangeDown_BigDecimal_iii");
        rangeDown_BigDecimal_helper("1.0", "ExhaustiveProvider_rangeDown_BigDecimal_iv");
        rangeDown_BigDecimal_helper("-1", "ExhaustiveProvider_rangeDown_BigDecimal_v");
        rangeDown_BigDecimal_helper("-1.0", "ExhaustiveProvider_rangeDown_BigDecimal_vi");
        rangeDown_BigDecimal_helper("9", "ExhaustiveProvider_rangeDown_BigDecimal_vii");
        rangeDown_BigDecimal_helper("-9", "ExhaustiveProvider_rangeDown_BigDecimal_viii");
        rangeDown_BigDecimal_helper("10", "ExhaustiveProvider_rangeDown_BigDecimal_ix");
        rangeDown_BigDecimal_helper("-10", "ExhaustiveProvider_rangeDown_BigDecimal_x");
        rangeDown_BigDecimal_helper("101", "ExhaustiveProvider_rangeDown_BigDecimal_xi");
        rangeDown_BigDecimal_helper("-101", "ExhaustiveProvider_rangeDown_BigDecimal_xii");
        rangeDown_BigDecimal_helper("1234567", "ExhaustiveProvider_rangeDown_BigDecimal_xiii");
        rangeDown_BigDecimal_helper("-1234567", "ExhaustiveProvider_rangeDown_BigDecimal_xiv");
        rangeDown_BigDecimal_helper("0.09", "ExhaustiveProvider_rangeDown_BigDecimal_xv");
        rangeDown_BigDecimal_helper("-0.09", "ExhaustiveProvider_rangeDown_BigDecimal_xvi");
        rangeDown_BigDecimal_helper("1E-12", "ExhaustiveProvider_rangeDown_BigDecimal_xvii");
        rangeDown_BigDecimal_helper("-1E-12", "ExhaustiveProvider_rangeDown_BigDecimal_xviii");
        rangeDown_BigDecimal_helper("1E+12", "ExhaustiveProvider_rangeDown_BigDecimal_xix");
        rangeDown_BigDecimal_helper("-1E+12", "ExhaustiveProvider_rangeDown_BigDecimal_xx");
    }

    private static void range_BigDecimal_BigDecimal_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(
                EP.range(Readers.readBigDecimalStrict(a).get(), Readers.readBigDecimalStrict(b).get()),
                output
        );
    }

    private static void range_BigDecimal_BigDecimal_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.range(Readers.readBigDecimalStrict(a).get(), Readers.readBigDecimalStrict(b).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRange_BigDecimal_BigDecimal() {
        range_BigDecimal_BigDecimal_helper("0", "1", "ExhaustiveProvider_range_BigDecimal_BigDecimal_i");
        range_BigDecimal_BigDecimal_helper("0", "3", "ExhaustiveProvider_range_BigDecimal_BigDecimal_ii");
        range_BigDecimal_BigDecimal_helper("0", "1E+6", "ExhaustiveProvider_range_BigDecimal_BigDecimal_iii");
        range_BigDecimal_BigDecimal_helper("0", "0.000001", "ExhaustiveProvider_range_BigDecimal_BigDecimal_iv");
        range_BigDecimal_BigDecimal_helper("1", "3", "ExhaustiveProvider_range_BigDecimal_BigDecimal_v");
        range_BigDecimal_BigDecimal_helper("1", "1E+6", "ExhaustiveProvider_range_BigDecimal_BigDecimal_vi");
        range_BigDecimal_BigDecimal_helper("-1", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_vii");
        range_BigDecimal_BigDecimal_helper("-3", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_viii");
        range_BigDecimal_BigDecimal_helper("-1E+6", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_ix");
        range_BigDecimal_BigDecimal_helper("-0.000001", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_x");
        range_BigDecimal_BigDecimal_helper("-3", "-1", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xi");
        range_BigDecimal_BigDecimal_helper("-1E+6", "-1", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xii");
        range_BigDecimal_BigDecimal_helper("100", "101", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xiii");
        range_BigDecimal_BigDecimal_helper("2.7183", "3.1416", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xiv");
        range_BigDecimal_BigDecimal_helper("-3.1416", "2.7183", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xv");
        range_BigDecimal_BigDecimal_helper("0", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xvi");
        range_BigDecimal_BigDecimal_helper("0", "0.0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xvii");
        range_BigDecimal_BigDecimal_helper("0.0", "0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xviii");
        range_BigDecimal_BigDecimal_helper("0.0", "0.0", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xix");
        range_BigDecimal_BigDecimal_helper("1", "1", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xx");

        range_BigDecimal_BigDecimal_fail_helper("5", "3");
    }

    private static void rangeUpCanonical_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpCanonical(Readers.readBigDecimalStrict(a).get()), output);
    }

    @Test
    public void testRangeUpCanonical_BigDecimal() {
        rangeUpCanonical_BigDecimal_helper("0", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_i");
        rangeUpCanonical_BigDecimal_helper("0.0", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_ii");
        rangeUpCanonical_BigDecimal_helper("1", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_iii");
        rangeUpCanonical_BigDecimal_helper("1.0", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_iv");
        rangeUpCanonical_BigDecimal_helper("-1", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_v");
        rangeUpCanonical_BigDecimal_helper("-1.0", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_vi");
        rangeUpCanonical_BigDecimal_helper("9", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_vii");
        rangeUpCanonical_BigDecimal_helper("-9", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_viii");
        rangeUpCanonical_BigDecimal_helper("10", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_ix");
        rangeUpCanonical_BigDecimal_helper("-10", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_x");
        rangeUpCanonical_BigDecimal_helper("101", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xi");
        rangeUpCanonical_BigDecimal_helper("-101", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xii");
        rangeUpCanonical_BigDecimal_helper("1234567", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xiii");
        rangeUpCanonical_BigDecimal_helper("-1234567", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xiv");
        rangeUpCanonical_BigDecimal_helper("0.09", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xv");
        rangeUpCanonical_BigDecimal_helper("-0.09", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xvi");
        rangeUpCanonical_BigDecimal_helper("1E-12", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xvii");
        rangeUpCanonical_BigDecimal_helper("-1E-12", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xviii");
        rangeUpCanonical_BigDecimal_helper("1E+12", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xix");
        rangeUpCanonical_BigDecimal_helper("-1E+12", "ExhaustiveProvider_rangeUpCanonical_BigDecimal_xx");
    }

    private static void rangeDownCanonical_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDownCanonical(Readers.readBigDecimalStrict(a).get()), output);
    }

    @Test
    public void testRangeDownCanonical_BigDecimal() {
        rangeDownCanonical_BigDecimal_helper("0", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_i");
        rangeDownCanonical_BigDecimal_helper("0.0", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_ii");
        rangeDownCanonical_BigDecimal_helper("1", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_iii");
        rangeDownCanonical_BigDecimal_helper("1.0", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_iv");
        rangeDownCanonical_BigDecimal_helper("-1", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_v");
        rangeDownCanonical_BigDecimal_helper("-1.0", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_vi");
        rangeDownCanonical_BigDecimal_helper("9", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_vii");
        rangeDownCanonical_BigDecimal_helper("-9", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_viii");
        rangeDownCanonical_BigDecimal_helper("10", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_ix");
        rangeDownCanonical_BigDecimal_helper("-10", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_x");
        rangeDownCanonical_BigDecimal_helper("101", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xi");
        rangeDownCanonical_BigDecimal_helper("-101", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xii");
        rangeDownCanonical_BigDecimal_helper("1234567", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xiii");
        rangeDownCanonical_BigDecimal_helper("-1234567", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xiv");
        rangeDownCanonical_BigDecimal_helper("0.09", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xv");
        rangeDownCanonical_BigDecimal_helper("-0.09", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xvi");
        rangeDownCanonical_BigDecimal_helper("1E-12", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xvii");
        rangeDownCanonical_BigDecimal_helper("-1E-12", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xviii");
        rangeDownCanonical_BigDecimal_helper("1E+12", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xix");
        rangeDownCanonical_BigDecimal_helper("-1E+12", "ExhaustiveProvider_rangeDownCanonical_BigDecimal_xx");
    }

    private static void rangeCanonical_BigDecimal_BigDecimal_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(
                EP.rangeCanonical(Readers.readBigDecimalStrict(a).get(), Readers.readBigDecimalStrict(b).get()),
                output
        );
    }

    private static void rangeCanonical_BigDecimal_BigDecimal_fail_helper(@NotNull String a, @NotNull String b) {
        try {
            EP.rangeCanonical(Readers.readBigDecimalStrict(a).get(), Readers.readBigDecimalStrict(b).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRangeCanonical_BigDecimal_BigDecimal() {
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "1",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_i");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "3",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_ii");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "1E+6",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_iii");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0.000001",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_iv");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "3",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_v");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "1E+6",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_vi");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_vii");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_viii");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1E+6", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_ix");
        rangeCanonical_BigDecimal_BigDecimal_helper("-0.000001", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_x");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3", "-1",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xi");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1E+6", "-1",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xii");
        rangeCanonical_BigDecimal_BigDecimal_helper("100", "101",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xiii");
        rangeCanonical_BigDecimal_BigDecimal_helper("2.7183", "3.1416",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xiv");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3.1416", "2.7183",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xv");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xvi");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0.0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xvii");
        rangeCanonical_BigDecimal_BigDecimal_helper("0.0", "0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xviii");
        rangeCanonical_BigDecimal_BigDecimal_helper("0.0", "0.0",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xix");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "1",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xx");

        rangeCanonical_BigDecimal_BigDecimal_fail_helper("5", "3");
    }

    private static void withNull_finite_helper(@NotNull String x, @NotNull String output) {
        aeqitLog(EP.withNull(readIntegerListWithNulls(x)), output);
    }

    private static void withNull_cyclic_helper(@NotNull String x, @NotNull String output) {
        simpleProviderHelper(EP.withNull(cycle(readIntegerListWithNulls(x))), output);
    }

    @Test
    public void testWithNull() {
        withNull_finite_helper("[]", "ExhaustiveProvider_withNull_i");
        withNull_finite_helper("[1, 2, 3]", "ExhaustiveProvider_withNull_ii");
        withNull_finite_helper("[1, null, 3]", "ExhaustiveProvider_withNull_iii");
        withNull_cyclic_helper("[1, 2, 3]", "ExhaustiveProvider_withNull_iv");
        withNull_cyclic_helper("[1, null, 3]", "ExhaustiveProvider_withNull_v");
    }

    private static <T> void nonEmptyOptionals_helper(@NotNull Iterable<T> input, @NotNull String output) {
        simpleProviderHelper(EP.nonEmptyOptionals(input), output);
    }

    private static void nonEmptyOptionals_helper(@NotNull String input, @NotNull String output) {
        nonEmptyOptionals_helper(readIntegerList(input), output);
    }

    private static void nonEmptyOptionals_fail_helper(@NotNull String input) {
        try {
            toList(EP.nonEmptyOptionals(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testNonEmptyOptionals() {
        nonEmptyOptionals_helper(EP.integers(), "ExhaustiveProvider_nonEmptyOptionals_i");
        nonEmptyOptionals_helper(EP.strings(), "ExhaustiveProvider_nonEmptyOptionals_ii");
        nonEmptyOptionals_helper("[1, 2, 3]", "ExhaustiveProvider_nonEmptyOptionals_iii");
        nonEmptyOptionals_helper("[]", "ExhaustiveProvider_nonEmptyOptionals_iv");

        nonEmptyOptionals_fail_helper("[1, null, 3]");
    }

    private static <T> void optionals_helper(@NotNull Iterable<T> input, @NotNull String output) {
        simpleProviderHelper(EP.optionals(input), output);
    }

    private static void optionals_helper(@NotNull String input, @NotNull String output) {
        optionals_helper(readIntegerList(input), output);
    }

    private static void optionals_fail_helper(@NotNull String input) {
        try {
            toList(EP.optionals(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testOptionals() {
        optionals_helper(EP.integers(), "ExhaustiveProvider_optionals_i");
        optionals_helper(EP.strings(), "ExhaustiveProvider_optionals_ii");
        optionals_helper("[1, 2, 3]", "ExhaustiveProvider_optionals_iii");
        optionals_helper("[]", "ExhaustiveProvider_optionals_iv");

        optionals_fail_helper("[1, null, 3]");
    }

    private static <T> void nonEmptyNullableOptionals_helper(@NotNull Iterable<T> input, @NotNull String output) {
        simpleProviderHelper(EP.nonEmptyNullableOptionals(input), output);
    }

    private static void nonEmptyNullableOptionals_helper(@NotNull String input, @NotNull String output) {
        nonEmptyNullableOptionals_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testNonEmptyNullableOptionals() {
        nonEmptyNullableOptionals_helper(EP.withNull(EP.integers()), "ExhaustiveProvider_nonEmptyNullableOptionals_i");
        nonEmptyNullableOptionals_helper(EP.withNull(EP.strings()), "ExhaustiveProvider_nonEmptyNullableOptionals_ii");
        nonEmptyNullableOptionals_helper("[1, 2, 3]", "ExhaustiveProvider_nonEmptyNullableOptionals_iii");
        nonEmptyNullableOptionals_helper("[]", "ExhaustiveProvider_nonEmptyNullableOptionals_iv");
    }

    private static <T> void nullableOptionals_helper(@NotNull Iterable<T> input, @NotNull String output) {
        simpleProviderHelper(EP.nullableOptionals(input), output);
    }

    private static void nullableOptionals_helper(@NotNull String input, @NotNull String output) {
        nullableOptionals_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testNullableOptionals() {
        nullableOptionals_helper(EP.withNull(EP.integers()), "ExhaustiveProvider_nullableOptionals_i");
        nullableOptionals_helper(EP.withNull(EP.strings()), "ExhaustiveProvider_nullableOptionals_ii");
        nullableOptionals_helper("[1, 2, 3]", "ExhaustiveProvider_nullableOptionals_iii");
        nullableOptionals_helper("[]", "ExhaustiveProvider_nullableOptionals_iv");
    }

    private static <T> void dependentPairs_helper(
            @NotNull String xs,
            @NotNull Function<Integer, Iterable<T>> f,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.dependentPairs(readIntegerList(xs), f), output);
    }

    private static <T> void dependentPairs_fail_helper(@NotNull String xs, @NotNull Function<Integer, Iterable<T>> f) {
        try {
            toList(EP.dependentPairs(readIntegerList(xs), f));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testDependentPairs() {
        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return Collections.emptyList();
                case 2: return Arrays.asList("a", "b", "c");
                case 3: return Collections.singletonList("foo");
            }
            throw new IllegalArgumentException();
        };
        dependentPairs_helper("[3, 1, 2, 0]", f, "ExhaustiveProvider_dependentPairs_i");
        dependentPairs_helper("[]", f, "ExhaustiveProvider_dependentPairs_ii");
        dependentPairs_helper("[3, 1, 2, 0]", i -> Collections.emptyList(), "ExhaustiveProvider_dependentPairs_iii");

        dependentPairs_fail_helper("[3, 1, 2, 0]", i -> null);
    }

    private static <A, B> void dependentPairsInfinite_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.dependentPairsInfinite(xs, f), output);
    }

    private static <A, B> void dependentPairsInfinite_fail_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        try {
            toList(EP.dependentPairsInfinite(xs, f));
            fail();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfinite() {
        dependentPairsInfinite_helper(
                EP.naturalBigIntegers(),
                i -> EP.naturalBigIntegers(),
                "ExhaustiveProvider_dependentPairsInfinite_i"
        );
        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        dependentPairsInfinite_helper(cycle(Arrays.asList(1, 0)), f, "ExhaustiveProvider_dependentPairsInfinite_ii");

        dependentPairsInfinite_fail_helper(cycle(Arrays.asList(1, 0)), i -> null);
        dependentPairsInfinite_fail_helper(Arrays.asList(0, 1), f);
        dependentPairsInfinite_fail_helper(cycle(Arrays.asList(1, 0)), i -> Collections.singletonList("a"));
    }

    private static <A, B> void dependentPairsInfiniteIdentityHash_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.dependentPairsInfiniteIdentityHash(xs, f), output);
    }

    private static <A, B> void dependentPairsInfiniteIdentityHash_fail_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        try {
            toList(EP.dependentPairsInfiniteIdentityHash(xs, f));
            fail();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException ignored) {}
    }

    private static class IntNoHashCode {
        private int i;

        public IntNoHashCode(int i) {
            this.i = i;
        }

        public int intValue() {
            return i;
        }

        public int hashCode() {
            throw new UnsupportedOperationException();
        }

        public @NotNull String toString() {
            return Integer.toString(i);
        }
    }

    @Test
    public void testDependentPairsInfiniteIdentityHash() {
        dependentPairsInfiniteIdentityHash_helper(
                map(IntNoHashCode::new, EP.naturalIntegers()),
                i -> EP.naturalBigIntegers(),
                "ExhaustiveProvider_dependentPairsInfinite_i"
        );
        Function<IntNoHashCode, Iterable<String>> f = i -> {
            switch (i.intValue()) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };

        List<IntNoHashCode> is = Arrays.asList(new IntNoHashCode(1), new IntNoHashCode(0));
        dependentPairsInfiniteIdentityHash_helper(cycle(is), f, "ExhaustiveProvider_dependentPairsInfinite_ii");

        dependentPairsInfiniteIdentityHash_fail_helper(cycle(is), i -> null);
        dependentPairsInfiniteIdentityHash_fail_helper(is, f);
        dependentPairsInfiniteIdentityHash_fail_helper(cycle(is), i -> Collections.singletonList("a"));
    }

    private static <A, B> void dependentPairsInfiniteLogarithmicOrder_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.dependentPairsInfiniteLogarithmicOrder(xs, f), output);
    }

    private static <A, B> void dependentPairsInfiniteLogarithmicOrder_fail_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        try {
            toList(EP.dependentPairsInfiniteLogarithmicOrder(xs, f));
            fail();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfiniteLogarithmicOrder() {
        dependentPairsInfiniteLogarithmicOrder_helper(
                EP.naturalBigIntegers(),
                i -> EP.naturalBigIntegers(),
                "ExhaustiveProvider_dependentPairsInfiniteLogarithmicOrder_i"
        );
        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        dependentPairsInfiniteLogarithmicOrder_helper(
                cycle(Arrays.asList(1, 0)),
                f,
                "ExhaustiveProvider_dependentPairsInfiniteLogarithmicOrder_ii"
        );

        dependentPairsInfiniteLogarithmicOrder_fail_helper(cycle(Arrays.asList(1, 0)), i -> null);
        dependentPairsInfiniteLogarithmicOrder_fail_helper(Arrays.asList(0, 1), f);
        dependentPairsInfiniteLogarithmicOrder_fail_helper(
                cycle(Arrays.asList(1, 0)),
                i -> Collections.singletonList("a")
        );
    }

    private static <A, B> void dependentPairsInfiniteSquareRootOrder_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.dependentPairsInfiniteSquareRootOrder(xs, f), output);
    }

    private static <A, B> void dependentPairsInfiniteSquareRootOrder_fail_helper(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        try {
            toList(EP.dependentPairsInfiniteSquareRootOrder(xs, f));
            fail();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfiniteSquareRootOrder() {
        dependentPairsInfiniteSquareRootOrder_helper(
                EP.naturalBigIntegers(),
                i -> EP.naturalBigIntegers(),
                "ExhaustiveProvider_dependentPairsInfiniteSquareRootOrder_i"
        );
        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        dependentPairsInfiniteSquareRootOrder_helper(
                cycle(Arrays.asList(1, 0)),
                f,
                "ExhaustiveProvider_dependentPairsInfiniteSquareRootOrder_ii"
        );

        dependentPairsInfiniteSquareRootOrder_fail_helper(cycle(Arrays.asList(1, 0)), i -> null);
        dependentPairsInfiniteSquareRootOrder_fail_helper(Arrays.asList(0, 1), f);
        dependentPairsInfiniteSquareRootOrder_fail_helper(
                cycle(Arrays.asList(1, 0)),
                i -> Collections.singletonList("a")
        );
    }

    private static <A, B> void pairsLogarithmicOrder_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.pairsLogarithmicOrder(as, bs), output);
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable_Iterable() {
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_i"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 2, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_ii"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_iii"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_iv"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_v"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_vi"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                fromString("abcd"),
                EP.naturalBigIntegers(),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_vii"
        );
        pairsLogarithmicOrder_Iterable_Iterable_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_viii"
        );
    }

    private static <T> void pairsLogarithmicOrder_Iterable_helper(@NotNull Iterable<T> xs, @NotNull String output) {
        simpleProviderHelper(EP.pairsLogarithmicOrder(xs), output);
    }

    private static void pairsLogarithmicOrder_Iterable_helper(@NotNull String xs, @NotNull String output) {
        pairsLogarithmicOrder_Iterable_helper(readIntegerListWithNulls(xs), output);
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        pairsLogarithmicOrder_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_i");
        pairsLogarithmicOrder_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_ii");
        pairsLogarithmicOrder_Iterable_helper(
                "[1, 2, null, 4]",
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_iii"
        );
        pairsLogarithmicOrder_Iterable_helper("[]", "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_iv");
        pairsLogarithmicOrder_Iterable_helper(
                EP.naturalBigIntegers(),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_v"
        );
        pairsLogarithmicOrder_Iterable_helper(
                cons(null, EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_vi"
        );
    }

    private static <A, B> void pairsSquareRootOrder_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.pairsSquareRootOrder(as, bs), output);
    }

    @Test
    public void testPairsSquareRootOrder_Iterable_Iterable() {
        pairsSquareRootOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_i"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 2, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_ii"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_iii"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_iv"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_v"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_vi"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                fromString("abcd"),
                EP.naturalBigIntegers(),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_vii"
        );
        pairsSquareRootOrder_Iterable_Iterable_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_viii"
        );
    }

    private static <T> void pairsSquareRootOrder_Iterable_helper(@NotNull Iterable<T> xs, @NotNull String output) {
        simpleProviderHelper(EP.pairsSquareRootOrder(xs), output);
    }

    private static void pairsSquareRootOrder_Iterable_helper(@NotNull String xs, @NotNull String output) {
        pairsSquareRootOrder_Iterable_helper(readIntegerListWithNulls(xs), output);
    }

    @Test
    public void testPairsSquareRootOrder_Iterable() {
        pairsSquareRootOrder_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_pairsSquareRootOrder_Iterable_i");
        pairsSquareRootOrder_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_pairsSquareRootOrder_Iterable_ii");
        pairsSquareRootOrder_Iterable_helper(
                "[1, 2, null, 4]",
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_iii"
        );
        pairsSquareRootOrder_Iterable_helper("[]", "ExhaustiveProvider_pairsSquareRootOrder_Iterable_iv");
        pairsSquareRootOrder_Iterable_helper(
                EP.naturalBigIntegers(),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_v"
        );
        pairsSquareRootOrder_Iterable_helper(
                cons(null, EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_vi"
        );
    }

    private static void permutationsFiniteHelper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.permutationsFinite(readIntegerListWithNulls(input))), output);
    }

    @Test
    public void testPermutationsFinite() {
        permutationsFiniteHelper("[]", "ExhaustiveProvider_permutationsFinite_i");
        permutationsFiniteHelper("[5]", "ExhaustiveProvider_permutationsFinite_ii");
        permutationsFiniteHelper("[1, 2]", "ExhaustiveProvider_permutationsFinite_iii");
        permutationsFiniteHelper("[1, 2, 3]", "ExhaustiveProvider_permutationsFinite_iv");
        permutationsFiniteHelper("[1, 2, 3, 4]", "ExhaustiveProvider_permutationsFinite_v");
        permutationsFiniteHelper("[1, 2, 2, 4]", "ExhaustiveProvider_permutationsFinite_vi");
        permutationsFiniteHelper("[2, 2, 2, 2]", "ExhaustiveProvider_permutationsFinite_vii");
        permutationsFiniteHelper("[3, 1, 4, 1]", "ExhaustiveProvider_permutationsFinite_viii");
        permutationsFiniteHelper("[3, 1, null, 1]", "ExhaustiveProvider_permutationsFinite_ix");
        permutationsFiniteHelper("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", "ExhaustiveProvider_permutationsFinite_x");
    }

    private static void stringPermutations_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringPermutations(input), output);
    }

    private static void stringPermutations_limit_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringPermutations(input), output);
    }

    @Test
    public void testStringPermutations() {
        stringPermutations_helper("", "ExhaustiveProvider_stringPermutations_i");
        stringPermutations_helper("a", "ExhaustiveProvider_stringPermutations_ii");
        stringPermutations_helper("abc", "ExhaustiveProvider_stringPermutations_iii");
        stringPermutations_helper("foo", "ExhaustiveProvider_stringPermutations_iv");
        stringPermutations_helper("hello", "ExhaustiveProvider_stringPermutations_v");
        stringPermutations_limit_helper("Mississippi", "ExhaustiveProvider_stringPermutations_vi");
    }

    private static void prefixPermutations_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        aeqitLog(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    private static void prefixPermutations_helper(@NotNull String input, @NotNull String output) {
        prefixPermutations_helper(readIntegerListWithNulls(input), output);
    }

    private static void prefixPermutations_limit_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    @Test
    public void testPrefixPermutations() {
        prefixPermutations_helper("[]", "ExhaustiveProvider_prefixPermutations_i");
        prefixPermutations_helper("[5]", "ExhaustiveProvider_prefixPermutations_ii");
        prefixPermutations_helper("[1, 2]", "ExhaustiveProvider_prefixPermutations_iii");
        prefixPermutations_helper("[1, 2, 3]", "ExhaustiveProvider_prefixPermutations_iv");
        prefixPermutations_helper("[1, 2, 3, 4]", "ExhaustiveProvider_prefixPermutations_v");
        prefixPermutations_helper("[1, 2, 2, 4]", "ExhaustiveProvider_prefixPermutations_vi");
        prefixPermutations_helper("[2, 2, 2, 2]", "ExhaustiveProvider_prefixPermutations_vii");
        prefixPermutations_helper("[3, 1, 4, 1]", "ExhaustiveProvider_prefixPermutations_viii");
        prefixPermutations_helper("[3, 1, null, 1]", "ExhaustiveProvider_prefixPermutations_ix");
        prefixPermutations_limit_helper(EP.rangeIncreasing(1, 10), "ExhaustiveProvider_prefixPermutations_x");
        prefixPermutations_limit_helper(EP.positiveIntegers(), "ExhaustiveProvider_prefixPermutations_xi");
        prefixPermutations_limit_helper(repeat(1), "ExhaustiveProvider_prefixPermutations_xii");
    }

    private static void listsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.listsLex(size, readIntegerListWithNulls(input)), output);
    }

    private static void listsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.listsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsLex_int_List() {
        listsLex_int_List_helper(0, "[]", "ExhaustiveProvider_listsLex_int_List_i");
        listsLex_int_List_helper(0, "[5]", "ExhaustiveProvider_listsLex_int_List_ii");
        listsLex_int_List_helper(1, "[5]", "ExhaustiveProvider_listsLex_int_List_iii");
        listsLex_int_List_helper(2, "[5]", "ExhaustiveProvider_listsLex_int_List_iv");
        listsLex_int_List_helper(3, "[5]", "ExhaustiveProvider_listsLex_int_List_v");
        listsLex_int_List_helper(0, "[1, 2, 3]", "ExhaustiveProvider_listsLex_int_List_vi");
        listsLex_int_List_helper(1, "[1, 2, 3]", "ExhaustiveProvider_listsLex_int_List_vii");
        listsLex_int_List_helper(2, "[1, 2, 3]", "ExhaustiveProvider_listsLex_int_List_viii");
        listsLex_int_List_helper(3, "[1, 2, 3]", "ExhaustiveProvider_listsLex_int_List_ix");
        listsLex_int_List_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_listsLex_int_List_x");
        listsLex_int_List_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_listsLex_int_List_xi");
        listsLex_int_List_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_listsLex_int_List_xii");
        listsLex_int_List_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_listsLex_int_List_xiii");
        listsLex_int_List_helper(0, "[1, null, 3]", "ExhaustiveProvider_listsLex_int_List_xiv");
        listsLex_int_List_helper(1, "[1, null, 3]", "ExhaustiveProvider_listsLex_int_List_xv");
        listsLex_int_List_helper(2, "[1, null, 3]", "ExhaustiveProvider_listsLex_int_List_xvi");
        listsLex_int_List_helper(3, "[1, null, 3]", "ExhaustiveProvider_listsLex_int_List_xvii");
        listsLex_int_List_helper(0, "[]", "ExhaustiveProvider_listsLex_int_List_xviii");
        listsLex_int_List_helper(1, "[]", "ExhaustiveProvider_listsLex_int_List_xix");
        listsLex_int_List_helper(2, "[]", "ExhaustiveProvider_listsLex_int_List_xx");
        listsLex_int_List_helper(3, "[]", "ExhaustiveProvider_listsLex_int_List_xxi");

        listsLex_int_List_fail_helper(-1, "[]");
        listsLex_int_List_fail_helper(-1, "[1, 2, 3]");
    }

    private static <A, B> void pairsLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.pairsLex(as, toList(bs)), output);
    }

    private static <A, B> void pairsLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.pairsLex(as, toList(bs)), output);
    }

    @Test
    public void testPairsLex() {
        pairsLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), "ExhaustiveProvider_pairsLex_i");
        pairsLex_helper(Arrays.asList(1, null, 3), fromString("abc"), "ExhaustiveProvider_pairsLex_ii");
        pairsLex_limit_helper(EP.naturalBigIntegers(), fromString("abc"), "ExhaustiveProvider_pairsLex_iii");
        pairsLex_helper(EP.naturalBigIntegers(), Collections.emptyList(), "ExhaustiveProvider_pairsLex_iv");
        pairsLex_helper(Collections.emptyList(), fromString("abc"), "ExhaustiveProvider_pairsLex_v");
        pairsLex_helper(Collections.emptyList(), Collections.emptyList(), "ExhaustiveProvider_pairsLex_vi");
    }

    private static <A, B, C> void triplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        aeqitLog(EP.triplesLex(as, toList(bs), toList(cs)), output);
    }

    private static <A, B, C> void triplesLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.triplesLex(as, toList(bs), toList(cs)), output);
    }

    @Test
    public void testTriplesLex() {
        triplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(), "ExhaustiveProvider_triplesLex_i");
        triplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(),
                "ExhaustiveProvider_triplesLex_ii");
        triplesLex_limit_helper(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(),
                "ExhaustiveProvider_triplesLex_iii");
        triplesLex_helper(EP.naturalBigIntegers(), fromString("abc"), Collections.emptyList(),
                "ExhaustiveProvider_triplesLex_iv");
        triplesLex_helper(Collections.emptyList(), fromString("abc"), EP.booleans(),
                "ExhaustiveProvider_triplesLex_v");
        triplesLex_helper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                "ExhaustiveProvider_triplesLex_vi");
    }

    private static <A, B, C, D> void quadruplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        aeqitLog(EP.quadruplesLex(as, toList(bs), toList(cs), toList(ds)), output);
    }

    private static <A, B, C, D> void quadruplesLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.quadruplesLex(as, toList(bs), toList(cs), toList(ds)), output);
    }

    @Test
    public void testQuadruplesLex() {
        quadruplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(), EP.orderings(),
                "ExhaustiveProvider_quadruplesLex_i");
        quadruplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(), EP.orderings(),
                "ExhaustiveProvider_quadruplesLex_ii");
        quadruplesLex_limit_helper(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(), EP.orderings(),
                "ExhaustiveProvider_quadruplesLex_iii");
        quadruplesLex_helper(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(), Collections.emptyList(),
                "ExhaustiveProvider_quadruplesLex_iv");
        quadruplesLex_helper(Collections.emptyList(), fromString("abc"), EP.booleans(), EP.orderings(),
                "ExhaustiveProvider_quadruplesLex_v");
        quadruplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_quadruplesLex_vi"
        );
    }

    private static <A, B, C, D, E> void quintuplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        aeqitLog(EP.quintuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es)), output);
    }

    private static <A, B, C, D, E> void quintuplesLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.quintuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es)), output);
    }

    @Test
    public void testQuintuplesLex() {
        quintuplesLex_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuplesLex_i");
        quintuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuplesLex_ii");
        quintuplesLex_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuplesLex_iii");
        quintuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Collections.emptyList(),
                "ExhaustiveProvider_quintuplesLex_iv"
        );
        quintuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuplesLex_v"
        );
        quintuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_quintuplesLex_vi"
        );
    }

    private static <A, B, C, D, E, F> void sextuplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull String output
    ) {
        aeqitLog(EP.sextuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs)), output);
    }

    private static <A, B, C, D, E, F> void sextuplesLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.sextuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs)), output);
    }

    @Test
    public void testSextuplesLex() {
        sextuplesLex_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuplesLex_i");
        sextuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuplesLex_ii");
        sextuplesLex_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuplesLex_iii");
        sextuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Collections.emptyList(),
                "ExhaustiveProvider_sextuplesLex_iv"
        );
        sextuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuplesLex_v"
        );
        sextuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_sextuplesLex_vi"
        );
    }

    private static <A, B, C, D, E, F, G> void septuplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs,
            @NotNull String output
    ) {
        aeqitLog(EP.septuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs), toList(gs)), output);
    }

    private static <A, B, C, D, E, F, G> void septuplesLex_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs,
            @NotNull String output
    ) {
        simpleProviderHelper(
                EP.septuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs), toList(gs)),
                output
        );
    }

    @Test
    public void testSeptuplesLex() {
        List<Integer> x = Arrays.asList(1, 0);
        List<Integer> y = Arrays.asList(0, 1);
        septuplesLex_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuplesLex_i");
        septuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuplesLex_ii");
        septuplesLex_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuplesLex_iii");
        septuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Collections.emptyList(),
                "ExhaustiveProvider_septuplesLex_iv"
        );
        septuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuplesLex_v"
        );
        septuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_septuplesLex_vi"
        );
    }

    private static void stringsLex_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringsLex(size, input), output);
    }

    private static void stringsLex_int_String_limit_helper(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsLex(size, input), output);
    }

    private static void stringLex_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.stringsLex(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsLex_int_String() {
        stringsLex_int_String_helper(0, "", "ExhaustiveProvider_stringsLex_int_String_i");
        stringsLex_int_String_helper(1, "", "ExhaustiveProvider_stringsLex_int_String_ii");
        stringsLex_int_String_helper(2, "", "ExhaustiveProvider_stringsLex_int_String_iii");
        stringsLex_int_String_helper(3, "", "ExhaustiveProvider_stringsLex_int_String_iv");

        stringsLex_int_String_helper(0, "a", "ExhaustiveProvider_stringsLex_int_String_v");
        stringsLex_int_String_helper(1, "a", "ExhaustiveProvider_stringsLex_int_String_vi");
        stringsLex_int_String_helper(2, "a", "ExhaustiveProvider_stringsLex_int_String_vii");
        stringsLex_int_String_helper(3, "a", "ExhaustiveProvider_stringsLex_int_String_viii");

        stringsLex_int_String_helper(0, "abc", "ExhaustiveProvider_stringsLex_int_String_ix");
        stringsLex_int_String_helper(1, "abc", "ExhaustiveProvider_stringsLex_int_String_x");
        stringsLex_int_String_helper(2, "abc", "ExhaustiveProvider_stringsLex_int_String_xi");
        stringsLex_int_String_helper(3, "abc", "ExhaustiveProvider_stringsLex_int_String_xii");

        stringsLex_int_String_helper(0, "abbc", "ExhaustiveProvider_stringsLex_int_String_xiii");
        stringsLex_int_String_helper(1, "abbc", "ExhaustiveProvider_stringsLex_int_String_xiv");
        stringsLex_int_String_helper(2, "abbc", "ExhaustiveProvider_stringsLex_int_String_xv");
        stringsLex_int_String_helper(3, "abbc", "ExhaustiveProvider_stringsLex_int_String_xvi");

        stringsLex_int_String_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xvii");
        stringsLex_int_String_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xviii");
        stringsLex_int_String_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xix");
        stringsLex_int_String_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xx");

        stringLex_int_String_fail_helper(-1, "");
        stringLex_int_String_fail_helper(-1, "abc");
    }

    private static void listsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.listsShortlex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testListsShortlex() {
        listsShortlex_helper("[]", "ExhaustiveProvider_listsShortlex_i");
        listsShortlex_helper("[5]", "ExhaustiveProvider_listsShortlex_ii");
        listsShortlex_helper("[1, 2, 3]", "ExhaustiveProvider_listsShortlex_iii");
        listsShortlex_helper("[1, 2, 2, 3]", "ExhaustiveProvider_listsShortlex_iv");
    }

    private static void stringsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsShortlex(input), output);
    }

    @Test
    public void testStringsShortlex() {
        stringsShortlex_helper("", "ExhaustiveProvider_stringsShortlex_i");
        stringsShortlex_helper("a", "ExhaustiveProvider_stringsShortlex_ii");
        stringsShortlex_helper("abc", "ExhaustiveProvider_stringsShortlex_iii");
        stringsShortlex_helper("abbc", "ExhaustiveProvider_stringsShortlex_iv");
        stringsShortlex_helper("Mississippi", "ExhaustiveProvider_stringsShortlex_v");
    }

    private static void listsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.listsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    private static void listsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.listsShortlexAtLeast(minSize, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsShortlexAtLeast() {
        listsShortlexAtLeast_helper(0, "[]", "ExhaustiveProvider_listsShortlexAtLeast_i");
        listsShortlexAtLeast_helper(1, "[]", "ExhaustiveProvider_listsShortlexAtLeast_ii");
        listsShortlexAtLeast_helper(2, "[]", "ExhaustiveProvider_listsShortlexAtLeast_iii");
        listsShortlexAtLeast_helper(3, "[]", "ExhaustiveProvider_listsShortlexAtLeast_iv");

        listsShortlexAtLeast_helper(0, "[5]", "ExhaustiveProvider_listsShortlexAtLeast_v");
        listsShortlexAtLeast_helper(1, "[5]", "ExhaustiveProvider_listsShortlexAtLeast_vi");
        listsShortlexAtLeast_helper(2, "[5]", "ExhaustiveProvider_listsShortlexAtLeast_vii");
        listsShortlexAtLeast_helper(3, "[5]", "ExhaustiveProvider_listsShortlexAtLeast_viii");

        listsShortlexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_listsShortlexAtLeast_ix");
        listsShortlexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_listsShortlexAtLeast_x");
        listsShortlexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xi");
        listsShortlexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xii");

        listsShortlexAtLeast_helper(0, "[1, null, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xiii");
        listsShortlexAtLeast_helper(1, "[1, null, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xiv");
        listsShortlexAtLeast_helper(2, "[1, null, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xv");
        listsShortlexAtLeast_helper(3, "[1, null, 3]", "ExhaustiveProvider_listsShortlexAtLeast_xvi");

        listsShortlexAtLeast_fail_helper(-1, "[]");
        listsShortlexAtLeast_fail_helper(-1, "[1, 2, 3]");
    }

    private static void stringsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsShortlexAtLeast(minSize, input), output);
    }

    private static void stringsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringsShortlexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsShortlexAtLeast() {
        stringsShortlexAtLeast_helper(0, "", "ExhaustiveProvider_stringsShortlexAtLeast_i");
        stringsShortlexAtLeast_helper(1, "", "ExhaustiveProvider_stringsShortlexAtLeast_ii");
        stringsShortlexAtLeast_helper(2, "", "ExhaustiveProvider_stringsShortlexAtLeast_iii");
        stringsShortlexAtLeast_helper(3, "", "ExhaustiveProvider_stringsShortlexAtLeast_iv");

        stringsShortlexAtLeast_helper(0, "a", "ExhaustiveProvider_stringsShortlexAtLeast_v");
        stringsShortlexAtLeast_helper(1, "a", "ExhaustiveProvider_stringsShortlexAtLeast_vi");
        stringsShortlexAtLeast_helper(2, "a", "ExhaustiveProvider_stringsShortlexAtLeast_vii");
        stringsShortlexAtLeast_helper(3, "a", "ExhaustiveProvider_stringsShortlexAtLeast_viii");

        stringsShortlexAtLeast_helper(0, "abc", "ExhaustiveProvider_stringsShortlexAtLeast_ix");
        stringsShortlexAtLeast_helper(1, "abc", "ExhaustiveProvider_stringsShortlexAtLeast_x");
        stringsShortlexAtLeast_helper(2, "abc", "ExhaustiveProvider_stringsShortlexAtLeast_xi");
        stringsShortlexAtLeast_helper(3, "abc", "ExhaustiveProvider_stringsShortlexAtLeast_xii");

        stringsShortlexAtLeast_helper(0, "abbc", "ExhaustiveProvider_stringsShortlexAtLeast_xiii");
        stringsShortlexAtLeast_helper(1, "abbc", "ExhaustiveProvider_stringsShortlexAtLeast_xiv");
        stringsShortlexAtLeast_helper(2, "abbc", "ExhaustiveProvider_stringsShortlexAtLeast_xv");
        stringsShortlexAtLeast_helper(3, "abbc", "ExhaustiveProvider_stringsShortlexAtLeast_xvi");

        stringsShortlexAtLeast_helper(0, "Mississippi", "ExhaustiveProvider_stringsShortlexAtLeast_xvii");
        stringsShortlexAtLeast_helper(1, "Mississippi", "ExhaustiveProvider_stringsShortlexAtLeast_xviii");
        stringsShortlexAtLeast_helper(2, "Mississippi", "ExhaustiveProvider_stringsShortlexAtLeast_xix");
        stringsShortlexAtLeast_helper(3, "Mississippi", "ExhaustiveProvider_stringsShortlexAtLeast_xx");

        stringsShortlexAtLeast_fail_helper(-1, "");
        stringsShortlexAtLeast_fail_helper(-1, "abc");
    }

    private static void lists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.lists(size, readIntegerListWithNulls(input)), output);
    }

    private static void lists_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.lists(size, input), output);
    }

    private static void lists_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            EP.lists(size, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLists_int_Iterable() {
        lists_int_Iterable_helper(0, "[]", "ExhaustiveProvider_lists_int_Iterable_i");
        lists_int_Iterable_helper(1, "[]", "ExhaustiveProvider_lists_int_Iterable_ii");
        lists_int_Iterable_helper(2, "[]", "ExhaustiveProvider_lists_int_Iterable_iii");
        lists_int_Iterable_helper(3, "[]", "ExhaustiveProvider_lists_int_Iterable_iv");

        lists_int_Iterable_helper(0, "[5]", "ExhaustiveProvider_lists_int_Iterable_v");
        lists_int_Iterable_helper(1, "[5]", "ExhaustiveProvider_lists_int_Iterable_vi");
        lists_int_Iterable_helper(2, "[5]", "ExhaustiveProvider_lists_int_Iterable_vii");
        lists_int_Iterable_helper(3, "[5]", "ExhaustiveProvider_lists_int_Iterable_viii");

        lists_int_Iterable_helper(0, "[1, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_ix");
        lists_int_Iterable_helper(1, "[1, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_x");
        lists_int_Iterable_helper(2, "[1, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xi");
        lists_int_Iterable_helper(3, "[1, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xii");

        lists_int_Iterable_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xiii");
        lists_int_Iterable_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xiv");
        lists_int_Iterable_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xv");
        lists_int_Iterable_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_lists_int_Iterable_xvi");

        lists_int_Iterable_helper(0, "[1, null, 3]", "ExhaustiveProvider_lists_int_Iterable_xvii");
        lists_int_Iterable_helper(1, "[1, null, 3]", "ExhaustiveProvider_lists_int_Iterable_xviii");
        lists_int_Iterable_helper(2, "[1, null, 3]", "ExhaustiveProvider_lists_int_Iterable_xix");
        lists_int_Iterable_helper(3, "[1, null, 3]", "ExhaustiveProvider_lists_int_Iterable_xx");

        lists_int_Iterable_helper(0, EP.positiveIntegers(), "ExhaustiveProvider_lists_int_Iterable_xxi");
        lists_int_Iterable_helper(1, EP.positiveIntegers(), "ExhaustiveProvider_lists_int_Iterable_xxii");
        lists_int_Iterable_helper(2, EP.positiveIntegers(), "ExhaustiveProvider_lists_int_Iterable_xxiii");
        lists_int_Iterable_helper(3, EP.positiveIntegers(), "ExhaustiveProvider_lists_int_Iterable_xxiv");

        lists_int_Iterable_helper(0, repeat(1), "ExhaustiveProvider_lists_int_Iterable_xxv");
        lists_int_Iterable_helper(1, repeat(1), "ExhaustiveProvider_lists_int_Iterable_xxvi");
        lists_int_Iterable_helper(2, repeat(1), "ExhaustiveProvider_lists_int_Iterable_xxvii");
        lists_int_Iterable_helper(3, repeat(1), "ExhaustiveProvider_lists_int_Iterable_xxviii");

        lists_int_Iterable_fail_helper(-1, "[]");
        lists_int_Iterable_fail_helper(-1, "[1, 2, 3]");
    }

    private static <A, B> void pairs_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.pairs(as, bs), output);
    }

    private static <A, B> void pairs_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.pairs(as, bs), output);
    }

    @Test
    public void testPairs_Iterable_Iterable() {
        pairs_Iterable_Iterable_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "ExhaustiveProvider_pairs_Iterable_Iterable_i");
        pairs_Iterable_Iterable_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "ExhaustiveProvider_pairs_Iterable_Iterable_ii");
        pairs_Iterable_Iterable_helper(Collections.emptyList(), fromString("abcd"),
                "ExhaustiveProvider_pairs_Iterable_Iterable_iii");
        pairs_Iterable_Iterable_helper(Collections.emptyList(), Collections.emptyList(),
                "ExhaustiveProvider_pairs_Iterable_Iterable_iv");

        pairs_Iterable_Iterable_limit_helper(EP.naturalBigIntegers(), fromString("abcd"),
                "ExhaustiveProvider_pairs_Iterable_Iterable_v");
        pairs_Iterable_Iterable_limit_helper(fromString("abcd"), EP.naturalBigIntegers(),
                "ExhaustiveProvider_pairs_Iterable_Iterable_vi");
        pairs_Iterable_Iterable_limit_helper(EP.positiveBigIntegers(), EP.negativeBigIntegers(),
                "ExhaustiveProvider_pairs_Iterable_Iterable_vii");
    }

    private static void pairs_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.pairs(input), output);
    }

    private static void pairs_Iterable_helper(@NotNull String input, @NotNull String output) {
        pairs_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testPairs_Iterable() {
        pairs_Iterable_helper("[]", "ExhaustiveProvider_pairs_Iterable_i");
        pairs_Iterable_helper("[5]", "ExhaustiveProvider_pairs_Iterable_ii");
        pairs_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_pairs_Iterable_iii");
        pairs_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_pairs_Iterable_iv");
        pairs_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_pairs_Iterable_v");
        pairs_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_pairs_Iterable_vi");
        pairs_Iterable_helper(repeat(1), "ExhaustiveProvider_pairs_Iterable_vii");
    }

    private static <A, B, C> void triples_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        aeqitLog(EP.triples(as, bs, cs), output);
    }

    private static <A, B, C> void triples_Iterable_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.triples(as, bs, cs), output);
    }

    @Test
    public void testTriples_Iterable_Iterable_Iterable() {
        triples_Iterable_Iterable_Iterable_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_i");
        triples_Iterable_Iterable_Iterable_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"), EP.booleans(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_ii");
        triples_Iterable_Iterable_Iterable_helper(Collections.emptyList(), fromString("abcd"), EP.booleans(), "[]");
        triples_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_iii"
        );

        triples_Iterable_Iterable_Iterable_limit_helper(EP.naturalBigIntegers(), fromString("abcd"), EP.booleans(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_iv");
        triples_Iterable_Iterable_Iterable_limit_helper(fromString("abcd"), EP.booleans(), EP.naturalBigIntegers(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_v");
        triples_Iterable_Iterable_Iterable_limit_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                EP.characters(),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_vi"
        );
    }

    private static void triples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.triples(input), output);
    }

    private static void triples_Iterable_helper(@NotNull String input, @NotNull String output) {
        triples_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testTriples_Iterable() {
        triples_Iterable_helper("[]", "ExhaustiveProvider_triples_Iterable_i");
        triples_Iterable_helper("[5]", "ExhaustiveProvider_triples_Iterable_ii");
        triples_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_triples_Iterable_iii");
        triples_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_triples_Iterable_iv");
        triples_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_triples_Iterable_v");
        triples_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_triples_Iterable_vi");
        triples_Iterable_helper(repeat(1), "ExhaustiveProvider_triples_Iterable_vii");
    }

    private static <A, B, C, D> void quadruples_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        aeqitLog(EP.quadruples(as, bs, cs, ds), output);
    }

    private static <A, B, C, D> void quadruples_Iterable_Iterable_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.quadruples(as, bs, cs, ds), output);
    }

    @Test
    public void testQuadruples_Iterable_Iterable_Iterable_Iterable() {
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_i"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_ii"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_iii"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_iv"
        );

        quadruples_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_v"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_limit_helper(
                fromString("abcd"),
                EP.booleans(),
                EP.naturalBigIntegers(),
                EP.orderings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_vi"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                EP.characters(),
                EP.strings(),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_vii"
        );
    }

    private static void quadruples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.quadruples(input), output);
    }

    private static void quadruples_Iterable_helper(@NotNull String input, @NotNull String output) {
        quadruples_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testQuadruples_Iterable() {
        quadruples_Iterable_helper("[]", "ExhaustiveProvider_quadruples_Iterable_i");
        quadruples_Iterable_helper("[5]", "ExhaustiveProvider_quadruples_Iterable_ii");
        quadruples_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_quadruples_Iterable_iii");
        quadruples_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_quadruples_Iterable_iv");
        quadruples_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_quadruples_Iterable_v");
        quadruples_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_quadruples_Iterable_vi");
        quadruples_Iterable_helper(repeat(1), "ExhaustiveProvider_quadruples_Iterable_vii");
    }

    private static <A, B, C, D, E> void quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        aeqitLog(EP.quintuples(as, bs, cs, ds, es), output);
    }

    private static <A, B, C, D, E> void quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.quintuples(as, bs, cs, ds, es), output);
    }

    @Test
    public void testQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable() {
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_i"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_ii"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_iii"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_iv"
        );

        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_v"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                fromString("abcd"),
                EP.booleans(),
                EP.naturalBigIntegers(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                EP.characters(),
                EP.strings(),
                EP.floats(),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
        );
    }

    private static void quintuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.quintuples(input), output);
    }

    private static void quintuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        quintuples_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testQuintuples_Iterable() {
        quintuples_Iterable_helper("[]", "ExhaustiveProvider_quintuples_Iterable_i");
        quintuples_Iterable_helper("[5]", "ExhaustiveProvider_quintuples_Iterable_ii");
        quintuples_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_quintuples_Iterable_iii");
        quintuples_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_quintuples_Iterable_iv");
        quintuples_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_quintuples_Iterable_v");
        quintuples_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_quintuples_Iterable_vi");
        quintuples_Iterable_helper(repeat(1), "ExhaustiveProvider_quintuples_Iterable_vii");
    }

    private static <A, B, C, D, E, F> void sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull String output
    ) {
        aeqitLog(EP.sextuples(as, bs, cs, ds, es, fs), output);
    }

    private static <A, B, C, D, E, F> void
            sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.sextuples(as, bs, cs, ds, es, fs), output);
    }

    @Test
    public void testSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_i"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_ii"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_iii"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_iv"
        );

        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_v"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                fromString("abcd"),
                EP.booleans(),
                EP.naturalBigIntegers(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                EP.characters(),
                EP.strings(),
                EP.floats(),
                EP.lists(EP.integers()),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
        );
    }

    private static void sextuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.sextuples(input), output);
    }

    private static void sextuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        sextuples_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testSextuples_Iterable() {
        sextuples_Iterable_helper("[]", "ExhaustiveProvider_sextuples_Iterable_i");
        sextuples_Iterable_helper("[5]", "ExhaustiveProvider_sextuples_Iterable_ii");
        sextuples_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_sextuples_Iterable_iii");
        sextuples_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_sextuples_Iterable_iv");
        sextuples_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_sextuples_Iterable_v");
        sextuples_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_sextuples_Iterable_vi");
        sextuples_Iterable_helper(repeat(1), "ExhaustiveProvider_sextuples_Iterable_vii");
    }

    private static <A, B, C, D, E, F, G> void
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs,
            @NotNull String output
    ) {
        aeqitLog(EP.septuples(as, bs, cs, ds, es, fs, gs), output);
    }

    private static <A, B, C, D, E, F, G> void
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.septuples(as, bs, cs, ds, es, fs, gs), output);
    }

    @Test
    public void testSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        List<Integer> x = Arrays.asList(1, 0);
        List<Integer> y = Arrays.asList(0, 1);
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_i"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_ii"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_iii"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_iv"
        );

        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.naturalBigIntegers(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_v"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                fromString("abcd"),
                EP.booleans(),
                EP.naturalBigIntegers(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_limit_helper(
                EP.positiveBigIntegers(),
                EP.negativeBigIntegers(),
                EP.characters(),
                EP.strings(),
                EP.floats(),
                EP.lists(EP.integers()),
                EP.bigDecimals(),
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
        );
    }

    private static void septuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.septuples(input), output);
    }

    private static void septuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        septuples_Iterable_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testSeptuples_Iterable() {
        septuples_Iterable_helper("[]", "ExhaustiveProvider_septuples_Iterable_i");
        septuples_Iterable_helper("[5]", "ExhaustiveProvider_septuples_Iterable_ii");
        septuples_Iterable_helper("[1, 2, 3, 4]", "ExhaustiveProvider_septuples_Iterable_iii");
        septuples_Iterable_helper("[1, 2, 2, 4]", "ExhaustiveProvider_septuples_Iterable_iv");
        septuples_Iterable_helper("[1, 2, null, 4]", "ExhaustiveProvider_septuples_Iterable_v");
        septuples_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_septuples_Iterable_vi");
        septuples_Iterable_helper(repeat(1), "ExhaustiveProvider_septuples_Iterable_vii");
    }

    private static void strings_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.strings(size, input), output);
    }

    private static void strings_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.strings(size, input), output);
    }

    private static void strings_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.strings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStrings_int_String() {
        strings_int_String_helper(0, "", "ExhaustiveProvider_strings_int_String_i");
        strings_int_String_helper(1, "", "ExhaustiveProvider_strings_int_String_ii");
        strings_int_String_helper(2, "", "ExhaustiveProvider_strings_int_String_iii");
        strings_int_String_helper(3, "", "ExhaustiveProvider_strings_int_String_iv");

        strings_int_String_helper(0, "a", "ExhaustiveProvider_strings_int_String_v");
        strings_int_String_helper(1, "a", "ExhaustiveProvider_strings_int_String_vi");
        strings_int_String_helper(2, "a", "ExhaustiveProvider_strings_int_String_vii");
        strings_int_String_helper(3, "a", "ExhaustiveProvider_strings_int_String_viii");

        strings_int_String_helper(0, "abc", "ExhaustiveProvider_strings_int_String_ix");
        strings_int_String_helper(1, "abc", "ExhaustiveProvider_strings_int_String_x");
        strings_int_String_helper(2, "abc", "ExhaustiveProvider_strings_int_String_xi");
        strings_int_String_helper(3, "abc", "ExhaustiveProvider_strings_int_String_xii");

        strings_int_String_helper(0, "abbc", "ExhaustiveProvider_strings_int_String_xiii");
        strings_int_String_helper(1, "abbc", "ExhaustiveProvider_strings_int_String_xiv");
        strings_int_String_helper(2, "abbc", "ExhaustiveProvider_strings_int_String_xv");
        strings_int_String_helper(3, "abbc", "ExhaustiveProvider_strings_int_String_xvi");

        strings_int_String_helper(0, "Mississippi", "ExhaustiveProvider_strings_int_String_xvii");
        strings_int_String_helper_limit(1, "Mississippi", "ExhaustiveProvider_strings_int_String_xviii");
        strings_int_String_helper_limit(2, "Mississippi", "ExhaustiveProvider_strings_int_String_xix");
        strings_int_String_helper_limit(3, "Mississippi", "ExhaustiveProvider_strings_int_String_xx");

        strings_int_String_fail_helper(-1, "");
        strings_int_String_fail_helper(-1, "abc");
    }

    private static void strings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.strings(size), output);
    }

    private static void strings_int_fail_helper(int size) {
        try {
            EP.strings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStrings_int() {
        strings_int_helper(0, "ExhaustiveProvider_strings_int_i");
        strings_int_helper(1, "ExhaustiveProvider_strings_int_ii");
        strings_int_helper(2, "ExhaustiveProvider_strings_int_iii");
        strings_int_helper(3, "ExhaustiveProvider_strings_int_iv");

        strings_int_fail_helper(-1);
    }

    private static void lists_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.lists(input), output);
    }

    private static void lists_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.lists(readIntegerList(input)), output);
    }

    @Test
    public void testLists_Iterable() {
        lists_Iterable_helper("[]", "ExhaustiveProvider_lists_Iterable_i");
        lists_Iterable_helper("[5]", "ExhaustiveProvider_lists_Iterable_ii");
        lists_Iterable_helper("[1, 2, 3]", "ExhaustiveProvider_lists_Iterable_iii");
        lists_Iterable_helper("[1, 2, 2, 3]", "ExhaustiveProvider_lists_Iterable_iv");
        lists_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_lists_Iterable_v");
        lists_Iterable_helper(repeat(1), "ExhaustiveProvider_lists_Iterable_vi");
    }

    private static void strings_String_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.strings(input), output);
    }

    @Test
    public void testStrings_String() {
        strings_String_helper("", "ExhaustiveProvider_strings_String_i");
        strings_String_helper("a", "ExhaustiveProvider_strings_String_ii");
        strings_String_helper("abc", "ExhaustiveProvider_strings_String_iii");
        strings_String_helper("abbc", "ExhaustiveProvider_strings_String_iv");
        strings_String_helper("Mississippi", "ExhaustiveProvider_strings_String_v");
    }

    @Test
    public void testStrings() {
        simpleProviderHelper(EP.strings(), "ExhaustiveProvider_strings");
    }

    private static void listsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.listsAtLeast(minSize, input), output);
    }

    private static void listsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        listsAtLeast_helper(minSize, readIntegerListWithNulls(input), output);
    }

    private static void listsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.listsAtLeast(minSize, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsAtLeast() {
        listsAtLeast_helper(0, "[]", "ExhaustiveProvider_listsAtLeast_i");
        listsAtLeast_helper(1, "[]", "ExhaustiveProvider_listsAtLeast_ii");
        listsAtLeast_helper(2, "[]", "ExhaustiveProvider_listsAtLeast_iii");
        listsAtLeast_helper(3, "[]", "ExhaustiveProvider_listsAtLeast_iv");

        listsAtLeast_helper(0, "[5]", "ExhaustiveProvider_listsAtLeast_v");
        listsAtLeast_helper(1, "[5]", "ExhaustiveProvider_listsAtLeast_vi");
        listsAtLeast_helper(2, "[5]", "ExhaustiveProvider_listsAtLeast_vii");
        listsAtLeast_helper(3, "[5]", "ExhaustiveProvider_listsAtLeast_viii");

        listsAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_listsAtLeast_ix");
        listsAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_listsAtLeast_x");
        listsAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_listsAtLeast_xi");
        listsAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_listsAtLeast_xii");

        listsAtLeast_helper(0, "[1, null, 3]", "ExhaustiveProvider_listsAtLeast_xiii");
        listsAtLeast_helper(1, "[1, null, 3]", "ExhaustiveProvider_listsAtLeast_xiv");
        listsAtLeast_helper(2, "[1, null, 3]", "ExhaustiveProvider_listsAtLeast_xv");
        listsAtLeast_helper(3, "[1, null, 3]", "ExhaustiveProvider_listsAtLeast_xvi");

        listsAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_listsAtLeast_xvii");
        listsAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_listsAtLeast_xviii");
        listsAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_listsAtLeast_xix");
        listsAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_listsAtLeast_xx");

        listsAtLeast_helper(0, EP.naturalIntegers(), "ExhaustiveProvider_listsAtLeast_xxi");
        listsAtLeast_helper(1, EP.naturalIntegers(), "ExhaustiveProvider_listsAtLeast_xxii");
        listsAtLeast_helper(2, EP.naturalIntegers(), "ExhaustiveProvider_listsAtLeast_xxiii");
        listsAtLeast_helper(3, EP.naturalIntegers(), "ExhaustiveProvider_listsAtLeast_xxiv");

        listsAtLeast_helper(0, repeat(1), "ExhaustiveProvider_listsAtLeast_xxv");
        listsAtLeast_helper(1, repeat(1), "ExhaustiveProvider_listsAtLeast_xxvi");
        listsAtLeast_helper(2, repeat(1), "ExhaustiveProvider_listsAtLeast_xxvii");
        listsAtLeast_helper(3, repeat(1), "ExhaustiveProvider_listsAtLeast_xxviii");

        listsAtLeast_fail_helper(-1, "[]");
        listsAtLeast_fail_helper(-1, "[1, 2, 3]");
    }

    private static void stringsAtLeast_int_String_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsAtLeast(minSize, input), output);
    }

    private static void stringsAtLeast_int_String_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringsAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsAtLeast_int_String() {
        stringsAtLeast_int_String_helper(0, "", "ExhaustiveProvider_stringsAtLeast_int_String_i");
        stringsAtLeast_int_String_helper(1, "", "ExhaustiveProvider_stringsAtLeast_int_String_ii");
        stringsAtLeast_int_String_helper(2, "", "ExhaustiveProvider_stringsAtLeast_int_String_iii");
        stringsAtLeast_int_String_helper(3, "", "ExhaustiveProvider_stringsAtLeast_int_String_iv");

        stringsAtLeast_int_String_helper(0, "a", "ExhaustiveProvider_stringsAtLeast_int_String_v");
        stringsAtLeast_int_String_helper(1, "a", "ExhaustiveProvider_stringsAtLeast_int_String_vi");
        stringsAtLeast_int_String_helper(2, "a", "ExhaustiveProvider_stringsAtLeast_int_String_vii");
        stringsAtLeast_int_String_helper(3, "a", "ExhaustiveProvider_stringsAtLeast_int_String_viii");

        stringsAtLeast_int_String_helper(0, "abc", "ExhaustiveProvider_stringsAtLeast_int_String_ix");
        stringsAtLeast_int_String_helper(1, "abc", "ExhaustiveProvider_stringsAtLeast_int_String_x");
        stringsAtLeast_int_String_helper(2, "abc", "ExhaustiveProvider_stringsAtLeast_int_String_xi");
        stringsAtLeast_int_String_helper(3, "abc", "ExhaustiveProvider_stringsAtLeast_int_String_xii");

        stringsAtLeast_int_String_helper(0, "abbc", "ExhaustiveProvider_stringsAtLeast_int_String_xiii");
        stringsAtLeast_int_String_helper(1, "abbc", "ExhaustiveProvider_stringsAtLeast_int_String_xiv");
        stringsAtLeast_int_String_helper(2, "abbc", "ExhaustiveProvider_stringsAtLeast_int_String_xv");
        stringsAtLeast_int_String_helper(3, "abbc", "ExhaustiveProvider_stringsAtLeast_int_String_xvi");

        stringsAtLeast_int_String_helper(0, "Mississippi", "ExhaustiveProvider_stringsAtLeast_int_String_xvii");
        stringsAtLeast_int_String_helper(1, "Mississippi", "ExhaustiveProvider_stringsAtLeast_int_String_xviii");
        stringsAtLeast_int_String_helper(2, "Mississippi", "ExhaustiveProvider_stringsAtLeast_int_String_xix");
        stringsAtLeast_int_String_helper(3, "Mississippi", "ExhaustiveProvider_stringsAtLeast_int_String_xx");

        stringsAtLeast_int_String_fail_helper(-1, "");
        stringsAtLeast_int_String_fail_helper(-1, "abc");
    }

    private static void stringsAtLeast_int_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringsAtLeast(minSize), output);
    }

    private static void stringsAtLeast_int_fail_helper(int minSize) {
        try {
            EP.stringsAtLeast(minSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsAtLeast_int() {
        stringsAtLeast_int_helper(0, "ExhaustiveProvider_stringsAtLeast_int_i");
        stringsAtLeast_int_helper(1, "ExhaustiveProvider_stringsAtLeast_int_ii");
        stringsAtLeast_int_helper(2, "ExhaustiveProvider_stringsAtLeast_int_iii");
        stringsAtLeast_int_helper(3, "ExhaustiveProvider_stringsAtLeast_int_iv");

        stringsAtLeast_int_fail_helper(-1);
    }

    private static void distinctListsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsLex(size, readIntegerListWithNulls(input)), output);
    }

    private static void distinctListsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.distinctListsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctListsLex_int_List() {
        distinctListsLex_int_List_helper(0, "[]", "ExhaustiveProvider_distinctListsLex_int_List_i");
        distinctListsLex_int_List_helper(1, "[]", "ExhaustiveProvider_distinctListsLex_int_List_ii");
        distinctListsLex_int_List_helper(2, "[]", "ExhaustiveProvider_distinctListsLex_int_List_iii");
        distinctListsLex_int_List_helper(3, "[]", "ExhaustiveProvider_distinctListsLex_int_List_iv");

        distinctListsLex_int_List_helper(0, "[5]", "ExhaustiveProvider_distinctListsLex_int_List_v");
        distinctListsLex_int_List_helper(1, "[5]", "ExhaustiveProvider_distinctListsLex_int_List_vi");
        distinctListsLex_int_List_helper(2, "[5]", "ExhaustiveProvider_distinctListsLex_int_List_vii");
        distinctListsLex_int_List_helper(3, "[5]", "ExhaustiveProvider_distinctListsLex_int_List_viii");

        distinctListsLex_int_List_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_ix");
        distinctListsLex_int_List_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_x");
        distinctListsLex_int_List_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xi");
        distinctListsLex_int_List_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xii");

        distinctListsLex_int_List_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xiii");
        distinctListsLex_int_List_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xiv");
        distinctListsLex_int_List_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xv");
        distinctListsLex_int_List_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xvi");

        distinctListsLex_int_List_helper(0, "[1, null, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xvii");
        distinctListsLex_int_List_helper(1, "[1, null, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xix");
        distinctListsLex_int_List_helper(2, "[1, null, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xx");
        distinctListsLex_int_List_helper(3, "[1, null, 3]", "ExhaustiveProvider_distinctListsLex_int_List_xxi");

        distinctListsLex_int_List_fail_helper(-1, "[]");
        distinctListsLex_int_List_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctPairsLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctPairsLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctPairsLex() {
        distinctPairsLex_helper("[]", "ExhaustiveProvider_distinctPairsLex_i");
        distinctPairsLex_helper("[5]", "ExhaustiveProvider_distinctPairsLex_ii");
        distinctPairsLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctPairsLex_iii");
        distinctPairsLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctPairsLex_iv");
        distinctPairsLex_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctPairsLex_v");
    }

    private static void distinctTriplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctTriplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctTriplesLex() {
        distinctTriplesLex_helper("[]", "ExhaustiveProvider_distinctTriplesLex_i");
        distinctTriplesLex_helper("[5]", "ExhaustiveProvider_distinctTriplesLex_ii");
        distinctTriplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctTriplesLex_iii");
        distinctTriplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctTriplesLex_iv");
        distinctTriplesLex_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctTriplesLex_v");
    }

    private static void distinctQuadruplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctQuadruplesLex() {
        distinctQuadruplesLex_helper("[]", "ExhaustiveProvider_distinctQuadruplesLex_i");
        distinctQuadruplesLex_helper("[5]", "ExhaustiveProvider_distinctQuadruplesLex_ii");
        distinctQuadruplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctQuadruplesLex_iii");
        distinctQuadruplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctQuadruplesLex_iv");
        distinctQuadruplesLex_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctQuadruplesLex_v");
    }

    private static void distinctQuintuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuintuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctQuintuplesLex() {
        distinctQuintuplesLex_helper("[]", "ExhaustiveProvider_distinctQuintuplesLex_i");
        distinctQuintuplesLex_helper("[5]", "ExhaustiveProvider_distinctQuintuplesLex_ii");
        distinctQuintuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctQuintuplesLex_iii");
        distinctQuintuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuplesLex_iv");
        distinctQuintuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuplesLex_v");
        distinctQuintuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuplesLex_vi");
    }

    private static void distinctSextuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSextuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctSextuplesLex() {
        distinctSextuplesLex_helper("[]", "ExhaustiveProvider_distinctSextuplesLex_i");
        distinctSextuplesLex_helper("[5]", "ExhaustiveProvider_distinctSextuplesLex_ii");
        distinctSextuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctSextuplesLex_iii");
        distinctSextuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuplesLex_iv");
        distinctSextuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuplesLex_v");
        distinctSextuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuplesLex_vi");
    }

    private static void distinctSeptuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSeptuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctSeptuplesLex() {
        distinctSeptuplesLex_helper("[]", "ExhaustiveProvider_distinctSeptuplesLex_i");
        distinctSeptuplesLex_helper("[5]", "ExhaustiveProvider_distinctSeptuplesLex_ii");
        distinctSeptuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctSeptuplesLex_iii");
        distinctSeptuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuplesLex_iv");
        distinctSeptuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuplesLex_v");
        distinctSeptuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuplesLex_vi");
    }

    private static void distinctStringsLex_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctStringsLex(size, input), output);
    }

    private static void distinctStringsLex_int_String_limit_helper(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsLex(size, input), output);
    }

    private static void distinctStringsLex_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.distinctStringsLex(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStringsLex_int_String() {
        distinctStringsLex_int_String_helper(0, "", "ExhaustiveProvider_distinctStringsLex_int_String_i");
        distinctStringsLex_int_String_helper(1, "", "ExhaustiveProvider_distinctStringsLex_int_String_ii");
        distinctStringsLex_int_String_helper(2, "", "ExhaustiveProvider_distinctStringsLex_int_String_iii");
        distinctStringsLex_int_String_helper(3, "", "ExhaustiveProvider_distinctStringsLex_int_String_iv");

        distinctStringsLex_int_String_helper(0, "a", "ExhaustiveProvider_distinctStringsLex_int_String_v");
        distinctStringsLex_int_String_helper(1, "a", "ExhaustiveProvider_distinctStringsLex_int_String_vi");
        distinctStringsLex_int_String_helper(2, "a", "ExhaustiveProvider_distinctStringsLex_int_String_vii");
        distinctStringsLex_int_String_helper(3, "a", "ExhaustiveProvider_distinctStringsLex_int_String_viii");

        distinctStringsLex_int_String_helper(0, "abc", "ExhaustiveProvider_distinctStringsLex_int_String_ix");
        distinctStringsLex_int_String_helper(1, "abc", "ExhaustiveProvider_distinctStringsLex_int_String_x");
        distinctStringsLex_int_String_helper(2, "abc", "ExhaustiveProvider_distinctStringsLex_int_String_xi");
        distinctStringsLex_int_String_helper(3, "abc", "ExhaustiveProvider_distinctStringsLex_int_String_xii");

        distinctStringsLex_int_String_helper(0, "abbc", "ExhaustiveProvider_distinctStringsLex_int_String_xiii");
        distinctStringsLex_int_String_helper(1, "abbc", "ExhaustiveProvider_distinctStringsLex_int_String_xiv");
        distinctStringsLex_int_String_helper(2, "abbc", "ExhaustiveProvider_distinctStringsLex_int_String_xv");
        distinctStringsLex_int_String_helper(3, "abbc", "ExhaustiveProvider_distinctStringsLex_int_String_xvi");

        distinctStringsLex_int_String_limit_helper(0, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xvii");
        distinctStringsLex_int_String_limit_helper(1, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xviii");
        distinctStringsLex_int_String_limit_helper(2, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xix");
        distinctStringsLex_int_String_limit_helper(3, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xx");

        distinctStringsLex_int_String_fail_helper(-1, "");
        distinctStringsLex_int_String_fail_helper(-1, "abc");
    }

    private static void distinctListsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsLex_List() {
        distinctListsLex_List_helper("[]", "ExhaustiveProvider_distinctListsLex_List_i");
        distinctListsLex_List_helper("[5]", "ExhaustiveProvider_distinctListsLex_List_ii");
        distinctListsLex_List_helper("[1, 2, 3]", "ExhaustiveProvider_distinctListsLex_List_iii");
        distinctListsLex_List_helper("[1, null, 3]", "ExhaustiveProvider_distinctListsLex_List_iv");
        distinctListsLex_List_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsLex_List_v");
        distinctListsLex_List_helper("[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLex_List_vi");
    }

    private static void distinctStringsLex_String_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctStringsLex(input), output);
    }

    private static void distinctStringsLex_String_limit_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctStringsLex(input), output);
    }

    @Test
    public void testDistinctStringsLex_String() {
        distinctStringsLex_String_helper("", "ExhaustiveProvider_distinctStringsLex_String_i");
        distinctStringsLex_String_helper("a", "ExhaustiveProvider_distinctStringsLex_String_ii");
        distinctStringsLex_String_helper("abc", "ExhaustiveProvider_distinctStringsLex_String_iii");
        distinctStringsLex_String_helper("abcd", "ExhaustiveProvider_distinctStringsLex_String_iv");
        distinctStringsLex_String_helper("abbc", "ExhaustiveProvider_distinctStringsLex_String_v");
        distinctStringsLex_String_limit_helper("Mississippi", "ExhaustiveProvider_distinctStringsLex_String_vi");
    }

    private static void distinctListsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsLexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    private static void distinctListsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctListsLexAtLeast(minSize, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctListsLexAtLeast() {
        distinctListsLexAtLeast_helper(0, "[]", "ExhaustiveProvider_distinctListsLexAtLeast_i");
        distinctListsLexAtLeast_helper(1, "[]", "ExhaustiveProvider_distinctListsLexAtLeast_ii");
        distinctListsLexAtLeast_helper(2, "[]", "ExhaustiveProvider_distinctListsLexAtLeast_iii");
        distinctListsLexAtLeast_helper(3, "[]", ",ExhaustiveProvider_distinctListsLexAtLeast_iv");

        distinctListsLexAtLeast_helper(0, "[5]", "ExhaustiveProvider_distinctListsLexAtLeast_v");
        distinctListsLexAtLeast_helper(1, "[5]", "ExhaustiveProvider_distinctListsLexAtLeast_vi");
        distinctListsLexAtLeast_helper(2, "[5]", "ExhaustiveProvider_distinctListsLexAtLeast_vii");
        distinctListsLexAtLeast_helper(3, "[5]", "ExhaustiveProvider_distinctListsLexAtLeast_viii");

        distinctListsLexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_ix");
        distinctListsLexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_x");
        distinctListsLexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xi");
        distinctListsLexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xii");

        distinctListsLexAtLeast_helper(0, "[1, null, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xiii");
        distinctListsLexAtLeast_helper(1, "[1, null, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xiv");
        distinctListsLexAtLeast_helper(2, "[1, null, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xv");
        distinctListsLexAtLeast_helper(3, "[1, null, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xvi");

        distinctListsLexAtLeast_helper(0, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsLexAtLeast_xvii");
        distinctListsLexAtLeast_helper(1, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsLexAtLeast_xviii");
        distinctListsLexAtLeast_helper(2, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsLexAtLeast_xix");
        distinctListsLexAtLeast_helper(3, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsLexAtLeast_xx");

        distinctListsLexAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xxi");
        distinctListsLexAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xxii");
        distinctListsLexAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xxiii");
        distinctListsLexAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsLexAtLeast_xxiv");

        distinctListsLexAtLeast_fail_helper(-1, "[]");
        distinctListsLexAtLeast_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctStringsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctStringsLexAtLeast(minSize, input), output);
    }

    private static void distinctStringsLexAtLeast_limit_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsLexAtLeast(minSize, input), output);
    }

    private static void distinctStringsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctStringsLexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStringsLexAtLeast() {
        distinctStringsLexAtLeast_helper(0, "", "ExhaustiveProvider_distinctStringsLexAtLeast_i");
        distinctStringsLexAtLeast_helper(1, "", "ExhaustiveProvider_distinctStringsLexAtLeast_ii");
        distinctStringsLexAtLeast_helper(2, "", "ExhaustiveProvider_distinctStringsLexAtLeast_iii");
        distinctStringsLexAtLeast_helper(3, "", "ExhaustiveProvider_distinctStringsLexAtLeast_iv");

        distinctStringsLexAtLeast_helper(0, "a", "ExhaustiveProvider_distinctStringsLexAtLeast_v");
        distinctStringsLexAtLeast_helper(1, "a", "ExhaustiveProvider_distinctStringsLexAtLeast_vi");
        distinctStringsLexAtLeast_helper(2, "a", "ExhaustiveProvider_distinctStringsLexAtLeast_vii");
        distinctStringsLexAtLeast_helper(3, "a", "ExhaustiveProvider_distinctStringsLexAtLeast_viii");

        distinctStringsLexAtLeast_helper(0, "abc", "ExhaustiveProvider_distinctStringsLexAtLeast_ix");
        distinctStringsLexAtLeast_helper(1, "abc", "ExhaustiveProvider_distinctStringsLexAtLeast_x");
        distinctStringsLexAtLeast_helper(2, "abc", "ExhaustiveProvider_distinctStringsLexAtLeast_xi");
        distinctStringsLexAtLeast_helper(3, "abc", "ExhaustiveProvider_distinctStringsLexAtLeast_xii");

        distinctStringsLexAtLeast_helper(0, "abbc", "ExhaustiveProvider_distinctStringsLexAtLeast_xiii");
        distinctStringsLexAtLeast_helper(1, "abbc", "ExhaustiveProvider_distinctStringsLexAtLeast_xiv");
        distinctStringsLexAtLeast_helper(2, "abbc", "ExhaustiveProvider_distinctStringsLexAtLeast_xv");
        distinctStringsLexAtLeast_helper(3, "abbc", "ExhaustiveProvider_distinctStringsLexAtLeast_xvi");

        distinctStringsLexAtLeast_limit_helper(0, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xvii");
        distinctStringsLexAtLeast_limit_helper(1, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xviii");
        distinctStringsLexAtLeast_limit_helper(2, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xix");
        distinctStringsLexAtLeast_limit_helper(3, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xx");

        distinctStringsLexAtLeast_fail_helper(-1, "");
        distinctStringsLexAtLeast_fail_helper(-1, "abc");
    }

    private static void distinctListsShortlex_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsShortlex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsShortlex() {
        distinctListsShortlex_helper("[]", "ExhaustiveProvider_distinctListsShortlex_i");
        distinctListsShortlex_helper("[5]", "ExhaustiveProvider_distinctListsShortlex_ii");
        distinctListsShortlex_helper("[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlex_iii");
        distinctListsShortlex_helper("[1, null, 3]", "ExhaustiveProvider_distinctListsShortlex_iv");
        distinctListsShortlex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsShortlex_v");
        distinctListsShortlex_helper("[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsShortlex_vi");
    }

    private static void distinctStringsShortlex_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctStringsShortlex(input), output);
    }

    private static void distinctStringsShortlex_limit_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctStringsShortlex(input), output);
    }

    @Test
    public void testDistinctStringsShortlex() {
        distinctStringsShortlex_helper("", "ExhaustiveProvider_distinctStringsShortlex_i");
        distinctStringsShortlex_helper("a", "ExhaustiveProvider_distinctStringsShortlex_ii");
        distinctStringsShortlex_helper("abc", "ExhaustiveProvider_distinctStringsShortlex_iii");
        distinctStringsShortlex_helper("abcd", "ExhaustiveProvider_distinctStringsShortlex_iv");
        distinctStringsShortlex_helper("abbc", "ExhaustiveProvider_distinctStringsShortlex_v");
        distinctStringsShortlex_limit_helper("Mississippi", "ExhaustiveProvider_distinctStringsShortlex_vi");
    }

    private static void distinctListsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqitLog(EP.distinctListsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    private static void distinctListsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctListsShortlexAtLeast(minSize, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctListsShortlexAtLeast() {
        distinctListsShortlexAtLeast_helper(0, "[]", "ExhaustiveProvider_distinctListsShortlexAtLeast_i");
        distinctListsShortlexAtLeast_helper(1, "[]", "ExhaustiveProvider_distinctListsShortlexAtLeast_ii");
        distinctListsShortlexAtLeast_helper(2, "[]", "ExhaustiveProvider_distinctListsShortlexAtLeast_iii");
        distinctListsShortlexAtLeast_helper(3, "[]", "ExhaustiveProvider_distinctListsShortlexAtLeast_iv");

        distinctListsShortlexAtLeast_helper(0, "[5]", "ExhaustiveProvider_distinctListsShortlexAtLeast_v");
        distinctListsShortlexAtLeast_helper(1, "[5]", "ExhaustiveProvider_distinctListsShortlexAtLeast_vi");
        distinctListsShortlexAtLeast_helper(2, "[5]", "ExhaustiveProvider_distinctListsShortlexAtLeast_vii");
        distinctListsShortlexAtLeast_helper(3, "[5]", "ExhaustiveProvider_distinctListsShortlexAtLeast_viii");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_ix");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_x");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xi");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xii");

        distinctListsShortlexAtLeast_helper(0, "[1, null, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xiii");
        distinctListsShortlexAtLeast_helper(1, "[1, null, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xiv");
        distinctListsShortlexAtLeast_helper(2, "[1, null, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xv");
        distinctListsShortlexAtLeast_helper(3, "[1, null, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xvi");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xvii");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xviii");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xix");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xx");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xxi");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xxii");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3, 4]",
                "ExhaustiveProvider_distinctListsShortlexAtLeast_xxiii");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3, 4]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xxiv");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xxv");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsShortlexAtLeast_xxvi");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 2, 3]",
                "ExhaustiveProvider_distinctListsShortlexAtLeast_xxvii");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 2, 3]",
                "ExhaustiveProvider_distinctListsShortlexAtLeast_xxviii");

        distinctListsShortlexAtLeast_fail_helper(-1, "[]");
        distinctListsShortlexAtLeast_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctStringsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqitLog(EP.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    private static void distinctStringsShortlexAtLeast_limit_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    private static void distinctStringsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctStringsShortlexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStringsShortlexAtLeast() {
        distinctStringsShortlexAtLeast_helper(0, "", "ExhaustiveProvider_distinctStringsShortlexAtLeast_i");
        distinctStringsShortlexAtLeast_helper(1, "", "ExhaustiveProvider_distinctStringsShortlexAtLeast_ii");
        distinctStringsShortlexAtLeast_helper(2, "", "ExhaustiveProvider_distinctStringsShortlexAtLeast_iii");
        distinctStringsShortlexAtLeast_helper(3, "", "ExhaustiveProvider_distinctStringsShortlexAtLeast_iv");

        distinctStringsShortlexAtLeast_helper(0, "a", "ExhaustiveProvider_distinctStringsShortlexAtLeast_v");
        distinctStringsShortlexAtLeast_helper(1, "a", "ExhaustiveProvider_distinctStringsShortlexAtLeast_vi");
        distinctStringsShortlexAtLeast_helper(2, "a", "ExhaustiveProvider_distinctStringsShortlexAtLeast_vii");
        distinctStringsShortlexAtLeast_helper(3, "a", "ExhaustiveProvider_distinctStringsShortlexAtLeast_viii");

        distinctStringsShortlexAtLeast_helper(0, "abc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_ix");
        distinctStringsShortlexAtLeast_helper(1, "abc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_x");
        distinctStringsShortlexAtLeast_helper(2, "abc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xi");
        distinctStringsShortlexAtLeast_helper(3, "abc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xii");

        distinctStringsShortlexAtLeast_helper(0, "abbc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xiii");
        distinctStringsShortlexAtLeast_helper(1, "abbc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xiv");
        distinctStringsShortlexAtLeast_helper(2, "abbc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xv");
        distinctStringsShortlexAtLeast_helper(3, "abbc", "ExhaustiveProvider_distinctStringsShortlexAtLeast_xvi");

        distinctStringsShortlexAtLeast_limit_helper(0, "Mississippi",
                "ExhaustiveProvider_distinctStringsShortlexAtLeast_xvii");
        distinctStringsShortlexAtLeast_limit_helper(1, "Mississippi",
                "ExhaustiveProvider_distinctStringsShortlexAtLeast_xviii");
        distinctStringsShortlexAtLeast_limit_helper(2, "Mississippi",
                "ExhaustiveProvider_distinctStringsShortlexAtLeast_xix");
        distinctStringsShortlexAtLeast_limit_helper(3, "Mississippi",
                "ExhaustiveProvider_distinctStringsShortlexAtLeast_xx");

        distinctStringsShortlexAtLeast_fail_helper(-1, "");
        distinctStringsShortlexAtLeast_fail_helper(-1, "abc");
    }

    private static void distinctLists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctLists(size, readIntegerListWithNulls(input)), output);
    }

    private static void distinctLists_int_Iterable_helper(
            int size,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctLists(size, input), output);
    }

    private static void distinctLists_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            EP.distinctLists(size, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctLists_int_Iterable() {
        distinctLists_int_Iterable_helper(0, "[]", "ExhaustiveProvider_distinctLists_int_Iterable_i");
        distinctLists_int_Iterable_helper(1, "[]", "ExhaustiveProvider_distinctLists_int_Iterable_ii");
        distinctLists_int_Iterable_helper(2, "[]", "ExhaustiveProvider_distinctLists_int_Iterable_iii");
        distinctLists_int_Iterable_helper(3, "[]", "ExhaustiveProvider_distinctLists_int_Iterable_iv");

        distinctLists_int_Iterable_helper(0, "[5]", "ExhaustiveProvider_distinctLists_int_Iterable_v");
        distinctLists_int_Iterable_helper(1, "[5]", "ExhaustiveProvider_distinctLists_int_Iterable_vi");
        distinctLists_int_Iterable_helper(2, "[5]", "ExhaustiveProvider_distinctLists_int_Iterable_vii");
        distinctLists_int_Iterable_helper(3, "[5]", "ExhaustiveProvider_distinctLists_int_Iterable_viii");

        distinctLists_int_Iterable_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_ix");
        distinctLists_int_Iterable_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_x");
        distinctLists_int_Iterable_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xi");
        distinctLists_int_Iterable_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xii");

        distinctLists_int_Iterable_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xiii");
        distinctLists_int_Iterable_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xiv");
        distinctLists_int_Iterable_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xv");
        distinctLists_int_Iterable_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xvi");

        distinctLists_int_Iterable_helper(0, "[1, null, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xvii");
        distinctLists_int_Iterable_helper(1, "[1, null, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xviii");
        distinctLists_int_Iterable_helper(2, "[1, null, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xix");
        distinctLists_int_Iterable_helper(3, "[1, null, 3]", "ExhaustiveProvider_distinctLists_int_Iterable_xx");

        distinctLists_int_Iterable_helper(0, EP.positiveIntegers(),
                "ExhaustiveProvider_distinctLists_int_Iterable_xxi");
        distinctLists_int_Iterable_helper(1, EP.positiveIntegers(),
                "ExhaustiveProvider_distinctLists_int_Iterable_xxii");
        distinctLists_int_Iterable_helper(2, EP.positiveIntegers(),
                "ExhaustiveProvider_distinctLists_int_Iterable_xxiii");
        distinctLists_int_Iterable_helper(3, EP.positiveIntegers(),
                "ExhaustiveProvider_distinctLists_int_Iterable_xxiv");

        distinctLists_int_Iterable_helper(0, repeat(1), "ExhaustiveProvider_distinctLists_int_Iterable_xxv");
        distinctLists_int_Iterable_helper(1, repeat(1), "ExhaustiveProvider_distinctLists_int_Iterable_xxvi");
        distinctLists_int_Iterable_helper(2, repeat(1), "ExhaustiveProvider_distinctLists_int_Iterable_xxvii");
        distinctLists_int_Iterable_helper(3, repeat(1), "ExhaustiveProvider_distinctLists_int_Iterable_xxviii");

        distinctLists_int_Iterable_fail_helper(-1, "[]");
        distinctLists_int_Iterable_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctPairs(input), output);
    }

    private static void distinctPairs_helper(@NotNull String input, @NotNull String output) {
        distinctPairs_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctPairs() {
        distinctPairs_helper("[]", "ExhaustiveProvider_distinctPairs_i");
        distinctPairs_helper("[5]", "ExhaustiveProvider_distinctPairs_ii");
        distinctPairs_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctPairs_iii");
        distinctPairs_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctPairs_iv");
        distinctPairs_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctPairs_v");
        distinctPairs_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctPairs_vi");
        distinctPairs_helper(repeat(1), "ExhaustiveProvider_distinctPairs_vii");
    }

    private static void distinctTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctTriples(input), output);
    }

    private static void distinctTriples_helper(@NotNull String input, @NotNull String output) {
        distinctTriples_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctTriples() {
        distinctTriples_helper("[]", "ExhaustiveProvider_distinctTriples_i");
        distinctTriples_helper("[5]", "ExhaustiveProvider_distinctTriples_ii");
        distinctTriples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctTriples_iii");
        distinctTriples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctTriples_iv");
        distinctTriples_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctTriples_v");
        distinctTriples_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctTriples_vi");
        distinctTriples_helper(repeat(1), "ExhaustiveProvider_distinctTriples_vii");
    }

    private static void distinctQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuadruples(input), output);
    }

    private static void distinctQuadruples_helper(@NotNull String input, @NotNull String output) {
        distinctQuadruples_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctQuadruples() {
        distinctQuadruples_helper("[]", "ExhaustiveProvider_distinctQuadruples_i");
        distinctQuadruples_helper("[5]", "ExhaustiveProvider_distinctQuadruples_ii");
        distinctQuadruples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctQuadruples_iii");
        distinctQuadruples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_distinctQuadruples_iv");
        distinctQuadruples_helper("[1, 2, null, 4]", "ExhaustiveProvider_distinctQuadruples_v");
        distinctQuadruples_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctQuadruples_vi");
        distinctQuadruples_helper(repeat(1), "ExhaustiveProvider_distinctQuadruples_vii");
    }

    private static void distinctQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuintuples(input), output);
    }

    private static void distinctQuintuples_helper(@NotNull String input, @NotNull String output) {
        distinctQuintuples_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctQuintuples() {
        distinctQuintuples_helper("[]", "ExhaustiveProvider_distinctQuintuples_i");
        distinctQuintuples_helper("[5]", "ExhaustiveProvider_distinctQuintuples_ii");
        distinctQuintuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctQuintuples_iii");
        distinctQuintuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuples_iv");
        distinctQuintuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuples_v");
        distinctQuintuples_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctQuintuples_vi");
        distinctQuintuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctQuintuples_vii");
        distinctQuintuples_helper(repeat(1), "ExhaustiveProvider_distinctQuintuples_viii");
    }

    private static void distinctSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSextuples(input), output);
    }

    private static void distinctSextuples_helper(@NotNull String input, @NotNull String output) {
        distinctSextuples_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctSextuples() {
        distinctSextuples_helper("[]", "ExhaustiveProvider_distinctSextuples_i");
        distinctSextuples_helper("[5]", "ExhaustiveProvider_distinctSextuples_ii");
        distinctSextuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctSextuples_iii");
        distinctSextuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuples_iv");
        distinctSextuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuples_v");
        distinctSextuples_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSextuples_vi");
        distinctSextuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctSextuples_vii");
        distinctSextuples_helper(repeat(1), "ExhaustiveProvider_distinctSextuples_viii");
    }

    private static void distinctSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSeptuples(input), output);
    }

    private static void distinctSeptuples_helper(@NotNull String input, @NotNull String output) {
        distinctSeptuples_helper(readIntegerListWithNulls(input), output);
    }

    @Test
    public void testDistinctSeptuples() {
        distinctSeptuples_helper("[]", "ExhaustiveProvider_distinctSeptuples_i");
        distinctSeptuples_helper("[5]", "ExhaustiveProvider_distinctSeptuples_ii");
        distinctSeptuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_distinctSeptuples_iii");
        distinctSeptuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuples_iv");
        distinctSeptuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuples_v");
        distinctSeptuples_helper("[1, 2, null, 4, 5, 6, 7, 8]", "ExhaustiveProvider_distinctSeptuples_vi");
        distinctSeptuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctSeptuples_vii");
        distinctSeptuples_helper(repeat(1), "ExhaustiveProvider_distinctSeptuples_viii");
    }

    private static void distinctStrings_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctStrings(size, input), output);
    }

    private static void distinctStrings_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStrings(size, input), output);
    }

    private static void distinctStrings_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.distinctStrings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStrings_int_String() {
        distinctStrings_int_String_helper(0, "", "ExhaustiveProvider_distinctStrings_int_String_i");
        distinctStrings_int_String_helper(1, "", "ExhaustiveProvider_distinctStrings_int_String_ii");
        distinctStrings_int_String_helper(2, "", "ExhaustiveProvider_distinctStrings_int_String_iii");
        distinctStrings_int_String_helper(3, "", "ExhaustiveProvider_distinctStrings_int_String_iv");

        distinctStrings_int_String_helper(0, "a", "ExhaustiveProvider_distinctStrings_int_String_v");
        distinctStrings_int_String_helper(1, "a", "ExhaustiveProvider_distinctStrings_int_String_vi");
        distinctStrings_int_String_helper(2, "a", "ExhaustiveProvider_distinctStrings_int_String_vii");
        distinctStrings_int_String_helper(3, "a", "ExhaustiveProvider_distinctStrings_int_String_viii");

        distinctStrings_int_String_helper(0, "abc", "ExhaustiveProvider_distinctStrings_int_String_ix");
        distinctStrings_int_String_helper(1, "abc", "ExhaustiveProvider_distinctStrings_int_String_x");
        distinctStrings_int_String_helper(2, "abc", "ExhaustiveProvider_distinctStrings_int_String_xi");
        distinctStrings_int_String_helper(3, "abc", "ExhaustiveProvider_distinctStrings_int_String_xii");

        distinctStrings_int_String_helper(0, "abbc", "ExhaustiveProvider_distinctStrings_int_String_xiii");
        distinctStrings_int_String_helper(1, "abbc", "ExhaustiveProvider_distinctStrings_int_String_xiv");
        distinctStrings_int_String_helper(2, "abbc", "ExhaustiveProvider_distinctStrings_int_String_xv");
        distinctStrings_int_String_helper(3, "abbc", "ExhaustiveProvider_distinctStrings_int_String_xvi");

        distinctStrings_int_String_helper_limit(0, "Mississippi",
                "ExhaustiveProvider_distinctStrings_int_String_xvii");
        distinctStrings_int_String_helper_limit(1, "Mississippi",
                "ExhaustiveProvider_distinctStrings_int_String_xviii");
        distinctStrings_int_String_helper_limit(2, "Mississippi", "ExhaustiveProvider_distinctStrings_int_String_xix");
        distinctStrings_int_String_helper_limit(3, "Mississippi", "ExhaustiveProvider_distinctStrings_int_String_xx");

        distinctStrings_int_String_fail_helper(-1, "");
        distinctStrings_int_String_fail_helper(-1, "abc");
    }

    private static void distinctStrings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.distinctStrings(size), output);
    }

    private static void distinctStrings_int_fail_helper(int size) {
        try {
            EP.distinctStrings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStrings_int() {
        distinctStrings_int_helper(0, "ExhaustiveProvider_distinctStrings_int_i");
        distinctStrings_int_helper(1, "ExhaustiveProvider_distinctStrings_int_ii");
        distinctStrings_int_helper(2, "ExhaustiveProvider_distinctStrings_int_iii");
        distinctStrings_int_helper(3, "ExhaustiveProvider_distinctStrings_int_iv");
        distinctStrings_int_helper(65537, "ExhaustiveProvider_distinctStrings_int_v");

        distinctStrings_int_fail_helper(-1);
    }

    private static void distinctLists_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctLists(input), output);
    }

    private static void distinctLists_Iterable_helper(@NotNull String input, @NotNull String output) {
        distinctLists_Iterable_helper(readIntegerList(input), output);
    }

    @Test
    public void testDistinctLists_Iterable() {
        distinctLists_Iterable_helper("[]", "ExhaustiveProvider_distinctLists_Iterable_i");
        distinctLists_Iterable_helper("[5]", "ExhaustiveProvider_distinctLists_Iterable_ii");
        distinctLists_Iterable_helper("[1, 2, 3]", "ExhaustiveProvider_distinctLists_Iterable_iii");
        distinctLists_Iterable_helper("[1, 2, 2, 3]", "ExhaustiveProvider_distinctLists_Iterable_iv");
        distinctLists_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_distinctLists_Iterable_v");
        distinctLists_Iterable_helper(repeat(1), "ExhaustiveProvider_distinctLists_Iterable_vi");
    }

    private static void distinctStrings_String_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctStrings(input), output);
    }

    @Test
    public void testDistinctStrings_String() {
        distinctStrings_String_helper("", "ExhaustiveProvider_distinctStrings_String_i");
        distinctStrings_String_helper("a", "ExhaustiveProvider_distinctStrings_String_ii");
        distinctStrings_String_helper("abc", "ExhaustiveProvider_distinctStrings_String_iii");
        distinctStrings_String_helper("abbc", "ExhaustiveProvider_distinctStrings_String_iv");
        distinctStrings_String_helper("Mississippi", "ExhaustiveProvider_distinctStrings_String_v");
    }

    @Test
    public void testDistinctStrings() {
        simpleProviderHelper(EP.distinctStrings(), "ExhaustiveProvider_distinctStrings");
    }

    private static void distinctListsAtLeast_helper(
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctListsAtLeast(minSize, input), output);
    }

    private static void distinctListsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        distinctListsAtLeast_helper(minSize, readIntegerListWithNulls(input), output);
    }

    private static void distinctListsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctListsAtLeast(minSize, readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctListsAtLeast() {
        distinctListsAtLeast_helper(0, "[]", "ExhaustiveProvider_distinctListsAtLeast_i");
        distinctListsAtLeast_helper(1, "[]", "ExhaustiveProvider_distinctListsAtLeast_ii");
        distinctListsAtLeast_helper(2, "[]", "ExhaustiveProvider_distinctListsAtLeast_iii");
        distinctListsAtLeast_helper(3, "[]", "ExhaustiveProvider_distinctListsAtLeast_iv");

        distinctListsAtLeast_helper(0, "[5]", "ExhaustiveProvider_distinctListsAtLeast_v");
        distinctListsAtLeast_helper(1, "[5]", "ExhaustiveProvider_distinctListsAtLeast_vi");
        distinctListsAtLeast_helper(2, "[5]", "ExhaustiveProvider_distinctListsAtLeast_vii");
        distinctListsAtLeast_helper(3, "[5]", "ExhaustiveProvider_distinctListsAtLeast_viii");

        distinctListsAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_ix");
        distinctListsAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_x");
        distinctListsAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xi");
        distinctListsAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xii");

        distinctListsAtLeast_helper(0, "[1, null, 3]", "ExhaustiveProvider_distinctListsAtLeast_xiii");
        distinctListsAtLeast_helper(1, "[1, null, 3]", "ExhaustiveProvider_distinctListsAtLeast_xiv");
        distinctListsAtLeast_helper(2, "[1, null, 3]", "ExhaustiveProvider_distinctListsAtLeast_xv");
        distinctListsAtLeast_helper(3, "[1, null, 3]", "ExhaustiveProvider_distinctListsAtLeast_xvi");

        distinctListsAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xvii");
        distinctListsAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xviii");
        distinctListsAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xix");
        distinctListsAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_distinctListsAtLeast_xx");

        distinctListsAtLeast_helper(0, EP.naturalIntegers(), "ExhaustiveProvider_distinctListsAtLeast_xxi");
        distinctListsAtLeast_helper(1, EP.naturalIntegers(), "ExhaustiveProvider_distinctListsAtLeast_xxii");
        distinctListsAtLeast_helper(2, EP.naturalIntegers(), "ExhaustiveProvider_distinctListsAtLeast_xxiii");
        distinctListsAtLeast_helper(3, EP.naturalIntegers(), "ExhaustiveProvider_distinctListsAtLeast_xxiv");

        distinctListsAtLeast_helper(0, repeat(1), "ExhaustiveProvider_distinctListsAtLeast_xxv");
        distinctListsAtLeast_helper(1, repeat(1), "ExhaustiveProvider_distinctListsAtLeast_xxvi");
        distinctListsAtLeast_helper(2, repeat(1), "ExhaustiveProvider_distinctListsAtLeast_xxvii");
        distinctListsAtLeast_helper(3, repeat(1), "ExhaustiveProvider_distinctListsAtLeast_xxviii");

        distinctListsAtLeast_fail_helper(-1, "[]");
        distinctListsAtLeast_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctStringsAtLeast_int_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsAtLeast(minSize, input), output);
    }

    private static void distinctStringsAtLeast_int_String_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.distinctStringsAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStringsAtLeast_int_String() {
        distinctStringsAtLeast_int_String_helper(0, "", "ExhaustiveProvider_distinctStringsAtLeast_int_String_i");
        distinctStringsAtLeast_int_String_helper(1, "", "ExhaustiveProvider_distinctStringsAtLeast_int_String_ii");
        distinctStringsAtLeast_int_String_helper(2, "", "ExhaustiveProvider_distinctStringsAtLeast_int_String_iii");
        distinctStringsAtLeast_int_String_helper(3, "", "ExhaustiveProvider_distinctStringsAtLeast_int_String_iv");

        distinctStringsAtLeast_int_String_helper(0, "a", "ExhaustiveProvider_distinctStringsAtLeast_int_String_v");
        distinctStringsAtLeast_int_String_helper(1, "a", "ExhaustiveProvider_distinctStringsAtLeast_int_String_vi");
        distinctStringsAtLeast_int_String_helper(2, "a", "ExhaustiveProvider_distinctStringsAtLeast_int_String_vii");
        distinctStringsAtLeast_int_String_helper(3, "a", "ExhaustiveProvider_distinctStringsAtLeast_int_String_viii");

        distinctStringsAtLeast_int_String_helper(0, "abc", "ExhaustiveProvider_distinctStringsAtLeast_int_String_ix");
        distinctStringsAtLeast_int_String_helper(1, "abc", "ExhaustiveProvider_distinctStringsAtLeast_int_String_x");
        distinctStringsAtLeast_int_String_helper(2, "abc", "ExhaustiveProvider_distinctStringsAtLeast_int_String_xi");
        distinctStringsAtLeast_int_String_helper(3, "abc", "ExhaustiveProvider_distinctStringsAtLeast_int_String_xii");

        distinctStringsAtLeast_int_String_helper(
                0,
                "abbc",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xiii"
        );
        distinctStringsAtLeast_int_String_helper(
                1,
                "abbc",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xiv"
        );
        distinctStringsAtLeast_int_String_helper(2, "abbc", "ExhaustiveProvider_distinctStringsAtLeast_int_String_xv");
        distinctStringsAtLeast_int_String_helper(
                3,
                "abbc",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xvi"
        );

        distinctStringsAtLeast_int_String_helper(0, "Mississippi",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xvii");
        distinctStringsAtLeast_int_String_helper(1, "Mississippi",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xviii");
        distinctStringsAtLeast_int_String_helper(2, "Mississippi",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xix");
        distinctStringsAtLeast_int_String_helper(3, "Mississippi",
                "ExhaustiveProvider_distinctStringsAtLeast_int_String_xx");

        distinctStringsAtLeast_int_String_fail_helper(-1, "");
        distinctStringsAtLeast_int_String_fail_helper(-1, "abc");
    }

    private static void distinctStringsAtLeast_int_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.distinctStringsAtLeast(minSize), output);
    }

    private static void distinctStringsAtLeast_int_fail_helper(int minSize) {
        try {
            EP.distinctStringsAtLeast(minSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctStringsAtLeast_int() {
        distinctStringsAtLeast_int_helper(0, "ExhaustiveProvider_distinctStringsAtLeast_int_i");
        distinctStringsAtLeast_int_helper(1, "ExhaustiveProvider_distinctStringsAtLeast_int_ii");
        distinctStringsAtLeast_int_helper(2, "ExhaustiveProvider_distinctStringsAtLeast_int_iii");
        distinctStringsAtLeast_int_helper(3, "ExhaustiveProvider_distinctStringsAtLeast_int_iv");
        distinctStringsAtLeast_int_helper(65537, "ExhaustiveProvider_distinctStringsAtLeast_int_v");

        distinctStringsAtLeast_int_fail_helper(-1);
    }

    private static void bagsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.bagsLex(size, readIntegerListWithNulls(input)), output);
    }

    private static void bagsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.bagsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (ArithmeticException | NullPointerException ignored) {}
    }

    @Test
    public void testBagsLex_int_List() {
        bagsLex_int_List_helper(0, "[]", "ExhaustiveProvider_bagsLex_int_List_i");
        bagsLex_int_List_helper(1, "[]", "ExhaustiveProvider_bagsLex_int_List_ii");
        bagsLex_int_List_helper(2, "[]", "ExhaustiveProvider_bagsLex_int_List_iii");
        bagsLex_int_List_helper(3, "[]", "ExhaustiveProvider_bagsLex_int_List_iv");

        bagsLex_int_List_helper(0, "[5]", "ExhaustiveProvider_bagsLex_int_List_v");
        bagsLex_int_List_helper(1, "[5]", "ExhaustiveProvider_bagsLex_int_List_vi");
        bagsLex_int_List_helper(2, "[5]", "ExhaustiveProvider_bagsLex_int_List_vii");
        bagsLex_int_List_helper(3, "[5]", "ExhaustiveProvider_bagsLex_int_List_viii");

        bagsLex_int_List_helper(0, "[1, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_ix");
        bagsLex_int_List_helper(1, "[1, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_x");
        bagsLex_int_List_helper(2, "[1, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xi");
        bagsLex_int_List_helper(3, "[1, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xii");

        bagsLex_int_List_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xiii");
        bagsLex_int_List_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xiv");
        bagsLex_int_List_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xv");
        bagsLex_int_List_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsLex_int_List_xvi");

        bagsLex_int_List_fail_helper(-1, "[]");
        bagsLex_int_List_fail_helper(-1, "[1, 2, 3]");
        bagsLex_int_List_fail_helper(1, "[1, null, 3]");
        bagsLex_int_List_fail_helper(1, "[null]");
        bagsLex_int_List_fail_helper(0, "[1, null, 3]");
        bagsLex_int_List_fail_helper(0, "[null]");
    }

    private static void bagPairsLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagPairsLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagPairsLex_fail_helper(@NotNull String input) {
        try {
            EP.bagPairsLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagPairsLex() {
        bagPairsLex_helper("[]", "ExhaustiveProvider_bagPairsLex_i");
        bagPairsLex_helper("[5]", "ExhaustiveProvider_bagPairsLex_ii");
        bagPairsLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagPairsLex_iii");
        bagPairsLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagPairsLex_iv");

        bagPairsLex_fail_helper("[1, 2, null, 4]");
        bagPairsLex_fail_helper("[null]");
    }

    private static void bagTriplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagTriplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagTriplesLex_fail_helper(@NotNull String input) {
        try {
            EP.bagTriplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagTriplesLex() {
        bagTriplesLex_helper("[]", "ExhaustiveProvider_bagTriplesLex_i");
        bagTriplesLex_helper("[5]", "ExhaustiveProvider_bagTriplesLex_ii");
        bagTriplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagTriplesLex_iii");
        bagTriplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagTriplesLex_iv");

        bagTriplesLex_fail_helper("[1, 2, null, 4]");
        bagTriplesLex_fail_helper("[null]");
    }

    private static void bagQuadruplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagQuadruplesLex_fail_helper(@NotNull String input) {
        try {
            EP.bagQuadruplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuadruplesLex() {
        bagQuadruplesLex_helper("[]", "ExhaustiveProvider_bagQuadruplesLex_i");
        bagQuadruplesLex_helper("[5]", "ExhaustiveProvider_bagQuadruplesLex_ii");
        bagQuadruplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagQuadruplesLex_iii");
        bagQuadruplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagQuadruplesLex_iv");

        bagQuadruplesLex_fail_helper("[1, 2, null, 4]");
        bagQuadruplesLex_fail_helper("[null]");
    }

    private static void bagQuintuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuintuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagQuintuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.bagQuintuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuintuplesLex() {
        bagQuintuplesLex_helper("[]", "ExhaustiveProvider_bagQuintuplesLex_i");
        bagQuintuplesLex_helper("[5]", "ExhaustiveProvider_bagQuintuplesLex_ii");
        bagQuintuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagQuintuplesLex_iii");
        bagQuintuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagQuintuplesLex_iv");

        bagQuintuplesLex_fail_helper("[1, 2, null, 4]");
        bagQuintuplesLex_fail_helper("[null]");
    }

    private static void bagSextuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagSextuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagSextuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.bagSextuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSextuplesLex() {
        bagSextuplesLex_helper("[]", "ExhaustiveProvider_bagSextuplesLex_i");
        bagSextuplesLex_helper("[5]", "ExhaustiveProvider_bagSextuplesLex_ii");
        bagSextuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagSextuplesLex_iii");
        bagSextuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagSextuplesLex_iv");

        bagSextuplesLex_fail_helper("[1, 2, null, 4]");
        bagSextuplesLex_fail_helper("[null]");
    }

    private static void bagSeptuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagSeptuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagSeptuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.bagSeptuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSeptuplesLex() {
        bagSeptuplesLex_helper("[]", "ExhaustiveProvider_bagSeptuplesLex_i");
        bagSeptuplesLex_helper("[5]", "ExhaustiveProvider_bagSeptuplesLex_ii");
        bagSeptuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagSeptuplesLex_iii");
        bagSeptuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagSeptuplesLex_iv");

        bagSeptuplesLex_fail_helper("[1, 2, null, 4]");
        bagSeptuplesLex_fail_helper("[null]");
    }

    private static void stringBagsLex_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringBagsLex(size, input), output);
    }

    private static void stringBagLex_limit_helper(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBagsLex(size, input), output);
    }

    private static void stringBagsLex_fail_helper(int size, @NotNull String input) {
        try {
            EP.stringBagsLex(size, input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testStringBagsLex() {
        stringBagsLex_helper(0, "", "ExhaustiveProvider_stringBagsLex_i");
        stringBagsLex_helper(1, "", "ExhaustiveProvider_stringBagsLex_ii");
        stringBagsLex_helper(2, "", "ExhaustiveProvider_stringBagsLex_iii");
        stringBagsLex_helper(3, "", "ExhaustiveProvider_stringBagsLex_iv");

        stringBagsLex_helper(0, "a", "ExhaustiveProvider_stringBagsLex_v");
        stringBagsLex_helper(1, "a", "ExhaustiveProvider_stringBagsLex_vi");
        stringBagsLex_helper(2, "a", "ExhaustiveProvider_stringBagsLex_vii");
        stringBagsLex_helper(3, "a", "ExhaustiveProvider_stringBagsLex_viii");

        stringBagsLex_helper(0, "abc", "ExhaustiveProvider_stringBagsLex_ix");
        stringBagsLex_helper(1, "abc", "ExhaustiveProvider_stringBagsLex_x");
        stringBagsLex_helper(2, "abc", "ExhaustiveProvider_stringBagsLex_xi");
        stringBagsLex_helper(3, "abc", "ExhaustiveProvider_stringBagsLex_xii");

        stringBagsLex_helper(0, "abbc", "ExhaustiveProvider_stringBagsLex_xiii");
        stringBagsLex_helper(1, "abbc", "ExhaustiveProvider_stringBagsLex_xiv");
        stringBagsLex_helper(2, "abbc", "ExhaustiveProvider_stringBagsLex_xv");
        stringBagsLex_helper(3, "abbc", "ExhaustiveProvider_stringBagsLex_xvi");

        stringBagLex_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringBagsLex_xvii");
        stringBagLex_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringBagsLex_xviii");
        stringBagLex_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringBagsLex_xix");
        stringBagLex_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringBagsLex_xx");

        stringBagsLex_fail_helper(-1, "");
        stringBagsLex_fail_helper(-1, "abc");
    }

    private static void bagsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagsShortlex(readIntegerList(input)), output);
    }

    private static void bagsShortlex_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagsShortlex(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagsShortlex() {
        bagsShortlex_helper("[]", "ExhaustiveProvider_bagsShortlex_i");
        bagsShortlex_helper("[5]", "ExhaustiveProvider_bagsShortlex_ii");
        bagsShortlex_helper("[1, 2, 3]", "ExhaustiveProvider_bagsShortlex_iii");
        bagsShortlex_helper("[1, 2, 2, 3]", "ExhaustiveProvider_bagsShortlex_iv");

        bagsShortlex_fail_helper("[1, null, 3]");
        bagsShortlex_fail_helper("[null]");
    }

    private static void stringBagsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsShortlex(input), output);
    }

    @Test
    public void testStringBagsShortlex() {
        stringBagsShortlex_helper("", "ExhaustiveProvider_stringBagsShortlex_i");
        stringBagsShortlex_helper("a", "ExhaustiveProvider_stringBagsShortlex_ii");
        stringBagsShortlex_helper("abc", "ExhaustiveProvider_stringBagsShortlex_iii");
        stringBagsShortlex_helper("abbc", "ExhaustiveProvider_stringBagsShortlex_iv");
        stringBagsShortlex_helper("Mississippi", "ExhaustiveProvider_stringBagsShortlex_v");
    }

    private static void bagsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagsShortlexAtLeast(minSize, readIntegerList(input)), output);
    }

    private static void bagsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.bagsShortlexAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testBagsShortlexAtLeast() {
        bagsShortlexAtLeast_helper(0, "[]", "ExhaustiveProvider_bagsShortlexAtLeast_i");
        bagsShortlexAtLeast_helper(1, "[]", "ExhaustiveProvider_bagsShortlexAtLeast_ii");
        bagsShortlexAtLeast_helper(2, "[]", "ExhaustiveProvider_bagsShortlexAtLeast_iii");
        bagsShortlexAtLeast_helper(3, "[]", "ExhaustiveProvider_bagsShortlexAtLeast_iv");

        bagsShortlexAtLeast_helper(0, "[5]", "ExhaustiveProvider_bagsShortlexAtLeast_v");
        bagsShortlexAtLeast_helper(1, "[5]", "ExhaustiveProvider_bagsShortlexAtLeast_vi");
        bagsShortlexAtLeast_helper(2, "[5]", "ExhaustiveProvider_bagsShortlexAtLeast_vii");
        bagsShortlexAtLeast_helper(3, "[5]", "ExhaustiveProvider_bagsShortlexAtLeast_viii");

        bagsShortlexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_bagsShortlexAtLeast_ix");
        bagsShortlexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_bagsShortlexAtLeast_x");
        bagsShortlexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_bagsShortlexAtLeast_xi");
        bagsShortlexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_bagsShortlexAtLeast_xii");

        bagsShortlexAtLeast_fail_helper(-1, "[]");
        bagsShortlexAtLeast_fail_helper(-1, "[1, 2, 3]");
        bagsShortlexAtLeast_fail_helper(1, "[1, null, 3]");
        bagsShortlexAtLeast_fail_helper(1, "[null]");
    }

    private static void stringBagsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsShortlexAtLeast(minSize, input), output);
    }

    private static void stringsBagsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringBagsShortlexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringBagsShortlexAtLeast() {
        stringBagsShortlexAtLeast_helper(0, "", "ExhaustiveProvider_stringBagsShortlexAtLeast_i");
        stringBagsShortlexAtLeast_helper(1, "", "ExhaustiveProvider_stringBagsShortlexAtLeast_ii");
        stringBagsShortlexAtLeast_helper(2, "", "ExhaustiveProvider_stringBagsShortlexAtLeast_iii");
        stringBagsShortlexAtLeast_helper(3, "", "ExhaustiveProvider_stringBagsShortlexAtLeast_iv");

        stringBagsShortlexAtLeast_helper(0, "a", "ExhaustiveProvider_stringBagsShortlexAtLeast_v");
        stringBagsShortlexAtLeast_helper(1, "a", "ExhaustiveProvider_stringBagsShortlexAtLeast_vi");
        stringBagsShortlexAtLeast_helper(2, "a", "ExhaustiveProvider_stringBagsShortlexAtLeast_vii");
        stringBagsShortlexAtLeast_helper(3, "a", "ExhaustiveProvider_stringBagsShortlexAtLeast_viii");

        stringBagsShortlexAtLeast_helper(0, "abc", "ExhaustiveProvider_stringBagsShortlexAtLeast_ix");
        stringBagsShortlexAtLeast_helper(1, "abc", "ExhaustiveProvider_stringBagsShortlexAtLeast_x");
        stringBagsShortlexAtLeast_helper(2, "abc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xi");
        stringBagsShortlexAtLeast_helper(3, "abc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xii");

        stringBagsShortlexAtLeast_helper(0, "abbc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xiii");
        stringBagsShortlexAtLeast_helper(1, "abbc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xiv");
        stringBagsShortlexAtLeast_helper(2, "abbc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xv");
        stringBagsShortlexAtLeast_helper(3, "abbc", "ExhaustiveProvider_stringBagsShortlexAtLeast_xvi");

        stringBagsShortlexAtLeast_helper(0, "Mississippi", "ExhaustiveProvider_stringBagsShortlexAtLeast_xvii");
        stringBagsShortlexAtLeast_helper(1, "Mississippi", "ExhaustiveProvider_stringBagsShortlexAtLeast_xviii");
        stringBagsShortlexAtLeast_helper(2, "Mississippi", "ExhaustiveProvider_stringBagsShortlexAtLeast_xix");
        stringBagsShortlexAtLeast_helper(3, "Mississippi", "ExhaustiveProvider_stringBagsShortlexAtLeast_xx");

        stringsBagsShortlexAtLeast_fail_helper(-1, "");
        stringsBagsShortlexAtLeast_fail_helper(-1, "abc");
    }

    private static void bags_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bags(size, input), output);
    }

    private static void bags_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        bags_int_Iterable_helper(size, readIntegerList(input), output);
    }

    private static void bags_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            toList(EP.bags(size, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testBags_int_Iterable() {
        bags_int_Iterable_helper(0, "[]", "ExhaustiveProvider_bags_int_Iterable_i");
        bags_int_Iterable_helper(1, "[]", "ExhaustiveProvider_bags_int_Iterable_ii");
        bags_int_Iterable_helper(2, "[]", "ExhaustiveProvider_bags_int_Iterable_iii");
        bags_int_Iterable_helper(3, "[]", "ExhaustiveProvider_bags_int_Iterable_iv");

        bags_int_Iterable_helper(0, "[5]", "ExhaustiveProvider_bags_int_Iterable_v");
        bags_int_Iterable_helper(1, "[5]", "ExhaustiveProvider_bags_int_Iterable_vi");
        bags_int_Iterable_helper(2, "[5]", "ExhaustiveProvider_bags_int_Iterable_vii");
        bags_int_Iterable_helper(3, "[5]", "ExhaustiveProvider_bags_int_Iterable_viii");

        bags_int_Iterable_helper(0, "[1, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_ix");
        bags_int_Iterable_helper(1, "[1, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_x");
        bags_int_Iterable_helper(2, "[1, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xi");
        bags_int_Iterable_helper(3, "[1, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xii");

        bags_int_Iterable_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xiii");
        bags_int_Iterable_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xiv");
        bags_int_Iterable_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xv");
        bags_int_Iterable_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_bags_int_Iterable_xvi");

        bags_int_Iterable_helper(0, EP.positiveIntegers(), "ExhaustiveProvider_bags_int_Iterable_xvii");
        bags_int_Iterable_helper(1, EP.positiveIntegers(), "ExhaustiveProvider_bags_int_Iterable_xviii");
        bags_int_Iterable_helper(2, EP.positiveIntegers(), "ExhaustiveProvider_bags_int_Iterable_xix");
        bags_int_Iterable_helper(3, EP.positiveIntegers(), "ExhaustiveProvider_bags_int_Iterable_xx");

        bags_int_Iterable_helper(0, repeat(1), "ExhaustiveProvider_bags_int_Iterable_xxi");
        bags_int_Iterable_helper(1, repeat(1), "ExhaustiveProvider_bags_int_Iterable_xxii");
        bags_int_Iterable_helper(2, repeat(1), "ExhaustiveProvider_bags_int_Iterable_xxiii");
        bags_int_Iterable_helper(3, repeat(1), "ExhaustiveProvider_bags_int_Iterable_xxiv");

        bags_int_Iterable_fail_helper(-1, "[]");
        bags_int_Iterable_fail_helper(-1, "[1, 2, 3]");
        bags_int_Iterable_fail_helper(1, "[1, null, 3]");
        bags_int_Iterable_fail_helper(1, "[null]");
    }

    private static void bagPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagPairs(input), output);
    }

    private static void bagPairs_helper(@NotNull String input, @NotNull String output) {
        bagPairs_helper(readIntegerList(input), output);
    }

    private static void bagPairs_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagPairs(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagPairs() {
        bagPairs_helper("[]", "ExhaustiveProvider_bagPairs_i");
        bagPairs_helper("[5]", "ExhaustiveProvider_bagPairs_ii");
        bagPairs_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagPairs_iii");
        bagPairs_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagPairs_iv");
        bagPairs_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagPairs_v");
        bagPairs_helper(repeat(1), "ExhaustiveProvider_bagPairs_vi");

        bagPairs_fail_helper("[1, null, 3]");
    }

    private static void bagTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagTriples(input), output);
    }

    private static void bagTriples_helper(@NotNull String input, @NotNull String output) {
        bagTriples_helper(readIntegerList(input), output);
    }

    private static void bagTriples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagTriples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagTriples() {
        bagTriples_helper("[]", "ExhaustiveProvider_bagTriples_i");
        bagTriples_helper("[5]", "ExhaustiveProvider_bagTriples_ii");
        bagTriples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagTriples_iii");
        bagTriples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagTriples_iv");
        bagTriples_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagTriples_v");
        bagTriples_helper(repeat(1), "ExhaustiveProvider_bagTriples_vi");

        bagTriples_fail_helper("[1, null, 3]");
    }

    private static void bagQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuadruples(input), output);
    }

    private static void bagQuadruples_helper(@NotNull String input, @NotNull String output) {
        bagQuadruples_helper(readIntegerList(input), output);
    }

    private static void bagQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagQuadruples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuadruples() {
        bagQuadruples_helper("[]", "ExhaustiveProvider_bagQuadruples_i");
        bagQuadruples_helper("[5]", "ExhaustiveProvider_bagQuadruples_ii");
        bagQuadruples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagQuadruples_iii");
        bagQuadruples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagQuadruples_iv");
        bagQuadruples_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagQuadruples_v");
        bagQuadruples_helper(repeat(1), "ExhaustiveProvider_bagQuadruples_vi");

        bagQuadruples_fail_helper("[1, null, 3]");
    }

    private static void bagQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuintuples(input), output);
    }

    private static void bagQuintuples_helper(@NotNull String input, @NotNull String output) {
        bagQuintuples_helper(readIntegerList(input), output);
    }

    private static void bagQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagQuintuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuintuples() {
        bagQuintuples_helper("[]", "ExhaustiveProvider_bagQuintuples_i");
        bagQuintuples_helper("[5]", "ExhaustiveProvider_bagQuintuples_ii");
        bagQuintuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagQuintuples_iii");
        bagQuintuples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagQuintuples_iv");
        bagQuintuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagQuintuples_v");
        bagQuintuples_helper(repeat(1), "ExhaustiveProvider_bagQuintuples_vi");

        bagQuintuples_fail_helper("[1, null, 3]");
    }

    private static void bagSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagSextuples(input), output);
    }

    private static void bagSextuples_helper(@NotNull String input, @NotNull String output) {
        bagSextuples_helper(readIntegerList(input), output);
    }

    private static void bagSextuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagSextuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSextuples() {
        bagSextuples_helper("[]", "ExhaustiveProvider_bagSextuples_i");
        bagSextuples_helper("[5]", "ExhaustiveProvider_bagSextuples_ii");
        bagSextuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagSextuples_iii");
        bagSextuples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagSextuples_iv");
        bagSextuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagSextuples_v");
        bagSextuples_helper(repeat(1), "ExhaustiveProvider_bagSextuples_vi");

        bagSextuples_fail_helper("[1, null, 3]");
    }

    private static void bagSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagSeptuples(input), output);
    }

    private static void bagSeptuples_helper(@NotNull String input, @NotNull String output) {
        bagSeptuples_helper(readIntegerList(input), output);
    }

    private static void bagSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagSeptuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSeptuples() {
        bagSeptuples_helper("[]", "ExhaustiveProvider_bagSeptuples_i");
        bagSeptuples_helper("[5]", "ExhaustiveProvider_bagSeptuples_ii");
        bagSeptuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_bagSeptuples_iii");
        bagSeptuples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_bagSeptuples_iv");
        bagSeptuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_bagSeptuples_v");
        bagSeptuples_helper(repeat(1), "ExhaustiveProvider_bagSeptuples_vi");

        bagSeptuples_fail_helper("[1, null, 3]");
    }

    private static void stringBags_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringBags(size, input), output);
    }

    private static void stringBags_int_String_limit_helper(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBags(size, input), output);
    }

    private static void stringBags_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.stringBags(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringBags_int_String() {
        stringBags_int_String_helper(0, "", "ExhaustiveProvider_stringBags_int_String_i");
        stringBags_int_String_helper(1, "", "ExhaustiveProvider_stringBags_int_String_ii");
        stringBags_int_String_helper(2, "", "ExhaustiveProvider_stringBags_int_String_iii");
        stringBags_int_String_helper(3, "", "ExhaustiveProvider_stringBags_int_String_iv");

        stringBags_int_String_helper(0, "a", "ExhaustiveProvider_stringBags_int_String_v");
        stringBags_int_String_helper(1, "a", "ExhaustiveProvider_stringBags_int_String_vi");
        stringBags_int_String_helper(2, "a", "ExhaustiveProvider_stringBags_int_String_vii");
        stringBags_int_String_helper(3, "a", "ExhaustiveProvider_stringBags_int_String_viii");

        stringBags_int_String_helper(0, "abc", "ExhaustiveProvider_stringBags_int_String_ix");
        stringBags_int_String_helper(1, "abc", "ExhaustiveProvider_stringBags_int_String_x");
        stringBags_int_String_helper(2, "abc", "ExhaustiveProvider_stringBags_int_String_xi");
        stringBags_int_String_helper(3, "abc", "ExhaustiveProvider_stringBags_int_String_xii");

        stringBags_int_String_helper(0, "abbc", "ExhaustiveProvider_stringBags_int_String_xiii");
        stringBags_int_String_helper(1, "abbc", "ExhaustiveProvider_stringBags_int_String_xiv");
        stringBags_int_String_helper(2, "abbc", "ExhaustiveProvider_stringBags_int_String_xv");
        stringBags_int_String_helper(3, "abbc", "ExhaustiveProvider_stringBags_int_String_xvi");

        stringBags_int_String_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringBags_int_String_xvii");
        stringBags_int_String_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringBags_int_String_xviii");
        stringBags_int_String_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringBags_int_String_xix");
        stringBags_int_String_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringBags_int_String_xx");

        stringBags_int_String_fail_helper(-1, "");
        stringBags_int_String_fail_helper(-1, "abc");
    }

    private static void stringBags_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.stringBags(size), output);
    }

    private static void stringBags_int_fail_helper(int size) {
        try {
            EP.stringBags(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringBags_int() {
        stringBags_int_helper(0, "ExhaustiveProvider_stringBags_int_i");
        stringBags_int_helper(1, "ExhaustiveProvider_stringBags_int_ii");
        stringBags_int_helper(2, "ExhaustiveProvider_stringBags_int_iii");
        stringBags_int_helper(3, "ExhaustiveProvider_stringBags_int_iv");

        stringBags_int_fail_helper(-1);
    }

    private static void bags_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bags(input), output);
    }

    private static void bags_Iterable_helper(@NotNull String input, @NotNull String output) {
        bags_Iterable_helper(readIntegerList(input), output);
    }

    private static void bags_Iterable_fail_helper(@NotNull String input) {
        try {
            toList(EP.bags(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBags_Iterable() {
        bags_Iterable_helper("[]", "ExhaustiveProvider_bags_Iterable_i");
        bags_Iterable_helper("[5]", "ExhaustiveProvider_bags_Iterable_ii");
        bags_Iterable_helper("[1, 2, 3]", "ExhaustiveProvider_bags_Iterable_iii");
        bags_Iterable_helper("[1, 2, 2, 3]", "ExhaustiveProvider_bags_Iterable_iv");
        bags_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_bags_Iterable_v");
        bags_Iterable_helper(repeat(1), "ExhaustiveProvider_bags_Iterable_vi");

        bags_Iterable_fail_helper("[null]");
        bags_Iterable_fail_helper("[1, null, 3]");
    }

    private static void stringBags_String_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringBags(input), output);
    }

    @Test
    public void testStringBags_String() {
        stringBags_String_helper("", "ExhaustiveProvider_stringBags_String_i");
        stringBags_String_helper("a", "ExhaustiveProvider_stringBags_String_ii");
        stringBags_String_helper("abc", "ExhaustiveProvider_stringBags_String_iii");
        stringBags_String_helper("abbc", "ExhaustiveProvider_stringBags_String_iv");
        stringBags_String_helper("Mississippi", "ExhaustiveProvider_stringBags_String_v");
    }

    @Test
    public void testStringBags() {
        simpleProviderHelper(EP.stringBags(), "ExhaustiveProvider_stringBags");
    }

    private static void bagsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagsAtLeast(minSize, input), output);
    }

    private static void bagsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        bagsAtLeast_helper(minSize, readIntegerList(input), output);
    }

    private static void bagsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.bagsAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testBagsAtLeast() {
        bagsAtLeast_helper(0, "[]", "ExhaustiveProvider_bagsAtLeast_i");
        bagsAtLeast_helper(1, "[]", "ExhaustiveProvider_bagsAtLeast_ii");
        bagsAtLeast_helper(2, "[]", "ExhaustiveProvider_bagsAtLeast_iii");
        bagsAtLeast_helper(3, "[]", "ExhaustiveProvider_bagsAtLeast_iv");

        bagsAtLeast_helper(0, "[5]", "ExhaustiveProvider_bagsAtLeast_v");
        bagsAtLeast_helper(1, "[5]", "ExhaustiveProvider_bagsAtLeast_vi");
        bagsAtLeast_helper(2, "[5]", "ExhaustiveProvider_bagsAtLeast_vii");
        bagsAtLeast_helper(3, "[5]", "ExhaustiveProvider_bagsAtLeast_viii");

        bagsAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_bagsAtLeast_ix");
        bagsAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_bagsAtLeast_x");
        bagsAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xi");
        bagsAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xii");

        bagsAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xiii");
        bagsAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xiv");
        bagsAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xv");
        bagsAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_bagsAtLeast_xvi");

        bagsAtLeast_helper(0, EP.naturalIntegers(), "ExhaustiveProvider_bagsAtLeast_xvii");
        bagsAtLeast_helper(1, EP.naturalIntegers(), "ExhaustiveProvider_bagsAtLeast_xviii");
        bagsAtLeast_helper(2, EP.naturalIntegers(), "ExhaustiveProvider_bagsAtLeast_xix");
        bagsAtLeast_helper(3, EP.naturalIntegers(), "ExhaustiveProvider_bagsAtLeast_xx");

        bagsAtLeast_helper(0, repeat(1), "ExhaustiveProvider_bagsAtLeast_xxi");
        bagsAtLeast_helper(1, repeat(1), "ExhaustiveProvider_bagsAtLeast_xxii");
        bagsAtLeast_helper(2, repeat(1), "ExhaustiveProvider_bagsAtLeast_xxiii");
        bagsAtLeast_helper(3, repeat(1), "ExhaustiveProvider_bagsAtLeast_xxiv");

        bagsAtLeast_fail_helper(-1, "[]");
        bagsAtLeast_fail_helper(-1, "[1, 2, 3]");
        bagsAtLeast_fail_helper(1, "[null]");
        bagsAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringBagsAtLeast_int_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBagsAtLeast(minSize, input), output);
    }

    private static void stringBagsAtLeast_int_String_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringBagsAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringBagsAtLeast_int_String() {
        stringBagsAtLeast_int_String_helper(0, "", "ExhaustiveProvider_stringBagsAtLeast_int_String_i");
        stringBagsAtLeast_int_String_helper(1, "", "ExhaustiveProvider_stringBagsAtLeast_int_String_ii");
        stringBagsAtLeast_int_String_helper(2, "", "ExhaustiveProvider_stringBagsAtLeast_int_String_iii");
        stringBagsAtLeast_int_String_helper(3, "", "ExhaustiveProvider_stringBagsAtLeast_int_String_iv");

        stringBagsAtLeast_int_String_helper(0, "a", "ExhaustiveProvider_stringBagsAtLeast_int_String_v");
        stringBagsAtLeast_int_String_helper(1, "a", "ExhaustiveProvider_stringBagsAtLeast_int_String_vi");
        stringBagsAtLeast_int_String_helper(2, "a", "ExhaustiveProvider_stringBagsAtLeast_int_String_vii");
        stringBagsAtLeast_int_String_helper(3, "a", "ExhaustiveProvider_stringBagsAtLeast_int_String_viii");

        stringBagsAtLeast_int_String_helper(0, "abc", "ExhaustiveProvider_stringBagsAtLeast_int_String_ix");
        stringBagsAtLeast_int_String_helper(1, "abc", "ExhaustiveProvider_stringBagsAtLeast_int_String_x");
        stringBagsAtLeast_int_String_helper(2, "abc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xi");
        stringBagsAtLeast_int_String_helper(3, "abc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xii");

        stringBagsAtLeast_int_String_helper(0, "abbc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xiii");
        stringBagsAtLeast_int_String_helper(1, "abbc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xiv");
        stringBagsAtLeast_int_String_helper(2, "abbc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xv");
        stringBagsAtLeast_int_String_helper(3, "abbc", "ExhaustiveProvider_stringBagsAtLeast_int_String_xvi");

        stringBagsAtLeast_int_String_helper(0, "Mississippi", "ExhaustiveProvider_stringBagsAtLeast_int_String_xvii");
        stringBagsAtLeast_int_String_helper(1, "Mississippi", "ExhaustiveProvider_stringBagsAtLeast_int_String_xviii");
        stringBagsAtLeast_int_String_helper(2, "Mississippi", "ExhaustiveProvider_stringBagsAtLeast_int_String_xix");
        stringBagsAtLeast_int_String_helper(3, "Mississippi", "ExhaustiveProvider_stringBagsAtLeast_int_String_xx");

        stringBagsAtLeast_int_String_fail_helper(-1, "");
        stringBagsAtLeast_int_String_fail_helper(-1, "abc");
    }

    private static void stringBagsAtLeast_int_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsAtLeast(minSize), output);
    }

    private static void stringBagsAtLeast_int_fail_helper(int minSize) {
        try {
            EP.stringBagsAtLeast(minSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringBagsAtLeast_int() {
        stringBagsAtLeast_int_helper(0, "ExhaustiveProvider_stringBagsAtLeast_int_i");
        stringBagsAtLeast_int_helper(1, "ExhaustiveProvider_stringBagsAtLeast_int_ii");
        stringBagsAtLeast_int_helper(2, "ExhaustiveProvider_stringBagsAtLeast_int_iii");
        stringBagsAtLeast_int_helper(3, "ExhaustiveProvider_stringBagsAtLeast_int_iv");

        stringBagsAtLeast_int_fail_helper(-1);
    }

    private static void subsetsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.subsetsLex(size, readIntegerList(input)), output);
    }

    private static void subsetsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.subsetsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (ArithmeticException | NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsLex_int_List() {
        subsetsLex_int_List_helper(0, "[]", "ExhaustiveProvider_subsetsLex_int_List_i");
        subsetsLex_int_List_helper(1, "[]", "ExhaustiveProvider_subsetsLex_int_List_ii");
        subsetsLex_int_List_helper(2, "[]", "ExhaustiveProvider_subsetsLex_int_List_iii");
        subsetsLex_int_List_helper(3, "[]", "ExhaustiveProvider_subsetsLex_int_List_iv");

        subsetsLex_int_List_helper(0, "[5]", "ExhaustiveProvider_subsetsLex_int_List_v");
        subsetsLex_int_List_helper(1, "[5]", "ExhaustiveProvider_subsetsLex_int_List_vi");
        subsetsLex_int_List_helper(2, "[5]", "ExhaustiveProvider_subsetsLex_int_List_vii");
        subsetsLex_int_List_helper(3, "[5]", "ExhaustiveProvider_subsetsLex_int_List_viii");

        subsetsLex_int_List_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_ix");
        subsetsLex_int_List_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_x");
        subsetsLex_int_List_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xi");
        subsetsLex_int_List_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xii");

        subsetsLex_int_List_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xiii");
        subsetsLex_int_List_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xiv");
        subsetsLex_int_List_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xv");
        subsetsLex_int_List_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLex_int_List_xvi");

        subsetsLex_int_List_fail_helper(-1, "[]");
        subsetsLex_int_List_fail_helper(-1, "[1, 2, 3]");
        subsetsLex_int_List_fail_helper(1, "[1, null, 3]");
        subsetsLex_int_List_fail_helper(1, "[null]");
        subsetsLex_int_List_fail_helper(0, "[1, null, 3]");
        subsetsLex_int_List_fail_helper(0, "[null]");
    }

    private static void subsetPairsLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetPairsLex(readIntegerList(input)), output);
    }

    private static void subsetPairsLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetPairsLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetPairsLex() {
        subsetPairsLex_helper("[]", "ExhaustiveProvider_subsetPairsLex_i");
        subsetPairsLex_helper("[5]", "ExhaustiveProvider_subsetPairsLex_ii");
        subsetPairsLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetPairsLex_iii");
        subsetPairsLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetPairsLex_iv");

        subsetPairsLex_fail_helper("[1, 2, null, 4]");
        subsetPairsLex_fail_helper("[null]");
    }

    private static void subsetTriplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetTriplesLex(readIntegerList(input)), output);
    }

    private static void subsetTriplesLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetTriplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetTriplesLex() {
        subsetTriplesLex_helper("[]", "ExhaustiveProvider_subsetTriplesLex_i");
        subsetTriplesLex_helper("[5]", "ExhaustiveProvider_subsetTriplesLex_ii");
        subsetTriplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetTriplesLex_iii");
        subsetTriplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetTriplesLex_iv");

        subsetTriplesLex_fail_helper("[1, 2, null, 4]");
        subsetTriplesLex_fail_helper("[null]");
    }

    private static void subsetQuadruplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void subsetQuadruplesLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetQuadruplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuadruplesLex() {
        subsetQuadruplesLex_helper("[]", "ExhaustiveProvider_subsetQuadruplesLex_i");
        subsetQuadruplesLex_helper("[5]", "ExhaustiveProvider_subsetQuadruplesLex_ii");
        subsetQuadruplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetQuadruplesLex_iii");
        subsetQuadruplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetQuadruplesLex_iv");

        subsetQuadruplesLex_fail_helper("[1, 2, null, 4]");
        subsetQuadruplesLex_fail_helper("[null]");
    }

    private static void subsetQuintuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuintuplesLex(readIntegerList(input)), output);
    }

    private static void subsetQuintuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetQuintuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuintuplesLex() {
        subsetQuintuplesLex_helper("[]", "ExhaustiveProvider_subsetQuintuplesLex_i");
        subsetQuintuplesLex_helper("[5]", "ExhaustiveProvider_subsetQuintuplesLex_ii");
        subsetQuintuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetQuintuplesLex_iii");
        subsetQuintuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetQuintuplesLex_iv");
        subsetQuintuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetQuintuplesLex_v");
        subsetQuintuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetQuintuplesLex_vi");

        subsetQuintuplesLex_fail_helper("[1, 2, null, 4]");
        subsetQuintuplesLex_fail_helper("[null]");
    }

    private static void subsetSextuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSextuplesLex(readIntegerList(input)), output);
    }

    private static void subsetSextuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetSextuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSextuplesLex() {
        subsetSextuplesLex_helper("[]", "ExhaustiveProvider_subsetSextuplesLex_i");
        subsetSextuplesLex_helper("[5]", "ExhaustiveProvider_subsetSextuplesLex_ii");
        subsetSextuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetSextuplesLex_iii");
        subsetSextuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetSextuplesLex_iv");
        subsetSextuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSextuplesLex_v");
        subsetSextuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSextuplesLex_vi");

        subsetSextuplesLex_fail_helper("[1, 2, null, 4]");
        subsetSextuplesLex_fail_helper("[null]");
    }

    private static void subsetSeptuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSeptuplesLex(readIntegerList(input)), output);
    }

    private static void subsetSeptuplesLex_fail_helper(@NotNull String input) {
        try {
            EP.subsetSeptuplesLex(readIntegerListWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSeptuplesLex() {
        subsetSeptuplesLex_helper("[]", "ExhaustiveProvider_subsetSeptuplesLex_i");
        subsetSeptuplesLex_helper("[5]", "ExhaustiveProvider_subsetSeptuplesLex_ii");
        subsetSeptuplesLex_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetSeptuplesLex_iii");
        subsetSeptuplesLex_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetSeptuplesLex_iv");
        subsetSeptuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSeptuplesLex_v");
        subsetSeptuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSeptuplesLex_vi");

        subsetSeptuplesLex_fail_helper("[1, 2, null, 4]");
        subsetSeptuplesLex_fail_helper("[null]");
    }

    private static void stringSubsetsLex_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringSubsetsLex(size, input), output);
    }

    private static void stringSubsetsLex_limit_helper(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsLex(size, input), output);
    }

    private static void stringSubsetsLex_fail_helper(int size, @NotNull String input) {
        try {
            EP.stringSubsetsLex(size, input);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testStringSubsetsLex_int_String() {
        stringSubsetsLex_helper(0, "", "ExhaustiveProvider_stringSubsetsLex_i");
        stringSubsetsLex_helper(1, "", "ExhaustiveProvider_stringSubsetsLex_ii");
        stringSubsetsLex_helper(2, "", "ExhaustiveProvider_stringSubsetsLex_iii");
        stringSubsetsLex_helper(3, "", "ExhaustiveProvider_stringSubsetsLex_iv");

        stringSubsetsLex_helper(0, "a", "ExhaustiveProvider_stringSubsetsLex_v");
        stringSubsetsLex_helper(1, "a", "ExhaustiveProvider_stringSubsetsLex_vi");
        stringSubsetsLex_helper(2, "a", "ExhaustiveProvider_stringSubsetsLex_vii");
        stringSubsetsLex_helper(3, "a", "ExhaustiveProvider_stringSubsetsLex_viii");

        stringSubsetsLex_helper(0, "abc", "ExhaustiveProvider_stringSubsetsLex_ix");
        stringSubsetsLex_helper(1, "abc", "ExhaustiveProvider_stringSubsetsLex_x");
        stringSubsetsLex_helper(2, "abc", "ExhaustiveProvider_stringSubsetsLex_xi");
        stringSubsetsLex_helper(3, "abc", "ExhaustiveProvider_stringSubsetsLex_xii");

        stringSubsetsLex_helper(0, "abbc", "ExhaustiveProvider_stringSubsetsLex_xiii");
        stringSubsetsLex_helper(1, "abbc", "ExhaustiveProvider_stringSubsetsLex_xiv");
        stringSubsetsLex_helper(2, "abbc", "ExhaustiveProvider_stringSubsetsLex_xv");
        stringSubsetsLex_helper(3, "abbc", "ExhaustiveProvider_stringSubsetsLex_xvi");

        stringSubsetsLex_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringSubsetsLex_xvii");
        stringSubsetsLex_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringSubsetsLex_xviii");
        stringSubsetsLex_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringSubsetsLex_xix");
        stringSubsetsLex_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringSubsetsLex_xx");

        stringSubsetsLex_fail_helper(-1, "");
        stringSubsetsLex_fail_helper(-1, "abc");
    }

    private static void subsetsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.subsetsLex(readIntegerList(input)), output);
    }

    private static void subsetsLex_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetsLex(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsLex_List() {
        subsetsLex_List_helper("[]", "ExhaustiveProvider_subsetsLex_List_i");
        subsetsLex_List_helper("[5]", "ExhaustiveProvider_subsetsLex_List_ii");
        subsetsLex_List_helper("[1, 2, 3]", "ExhaustiveProvider_subsetsLex_List_iii");
        subsetsLex_List_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetsLex_List_iv");
        subsetsLex_List_helper("[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLex_List_v");

        subsetsLex_fail_helper("[null]");
        subsetsLex_fail_helper("[1, null, 3]");
    }

    private static void stringSubsetsLex_String_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringSubsetsLex(input), output);
    }

    private static void stringSubsetsLex_String_limit_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsLex(input), output);
    }

    @Test
    public void testStringSubsetsLex_String() {
        stringSubsetsLex_String_helper("", "ExhaustiveProvider_stringSubsetsLex_String_i");
        stringSubsetsLex_String_helper("a", "ExhaustiveProvider_stringSubsetsLex_String_ii");
        stringSubsetsLex_String_helper("abc", "ExhaustiveProvider_stringSubsetsLex_String_iii");
        stringSubsetsLex_String_helper("abcd", "ExhaustiveProvider_stringSubsetsLex_String_iv");
        stringSubsetsLex_String_helper("abbc", "ExhaustiveProvider_stringSubsetsLex_String_v");

        stringSubsetsLex_String_limit_helper("Mississippi", "ExhaustiveProvider_stringSubsetsLex_String_vi");
    }

    private static void subsetsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.subsetsLexAtLeast(minSize, readIntegerList(input)), output);
    }

    private static void subsetsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.subsetsLexAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testSubsetsLexAtLeast() {
        subsetsLexAtLeast_helper(0, "[]", "ExhaustiveProvider_subsetsLexAtLeast_i");
        subsetsLexAtLeast_helper(1, "[]", "ExhaustiveProvider_subsetsLexAtLeast_ii");
        subsetsLexAtLeast_helper(2, "[]", "ExhaustiveProvider_subsetsLexAtLeast_iii");
        subsetsLexAtLeast_helper(3, "[]", "ExhaustiveProvider_subsetsLexAtLeast_iv");

        subsetsLexAtLeast_helper(0, "[5]", "ExhaustiveProvider_subsetsLexAtLeast_v");
        subsetsLexAtLeast_helper(1, "[5]", "ExhaustiveProvider_subsetsLexAtLeast_vi");
        subsetsLexAtLeast_helper(2, "[5]", "ExhaustiveProvider_subsetsLexAtLeast_vii");
        subsetsLexAtLeast_helper(3, "[5]", "ExhaustiveProvider_subsetsLexAtLeast_viii");

        subsetsLexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_ix");
        subsetsLexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_x");
        subsetsLexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xi");
        subsetsLexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xii");

        subsetsLexAtLeast_helper(0, "[1, 2, 3, 4]", "ExhaustiveProvider_subsetsLexAtLeast_xiii");
        subsetsLexAtLeast_helper(1, "[1, 2, 3, 4]", "ExhaustiveProvider_subsetsLexAtLeast_xiv");
        subsetsLexAtLeast_helper(2, "[1, 2, 3, 4]", "ExhaustiveProvider_subsetsLexAtLeast_xv");
        subsetsLexAtLeast_helper(3, "[1, 2, 3, 4]", "ExhaustiveProvider_subsetsLexAtLeast_xvi");

        subsetsLexAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xvii");
        subsetsLexAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xviii");
        subsetsLexAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xix");
        subsetsLexAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsLexAtLeast_xx");

        subsetsLexAtLeast_fail_helper(-1, "[]");
        subsetsLexAtLeast_fail_helper(-1, "[1, 2, 3]");
        subsetsLexAtLeast_fail_helper(1, "[null]");
        subsetsLexAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringSubsetsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringSubsetsLexAtLeast(minSize, input), output);
    }

    private static void stringSubsetsLexAtLeast_limit_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsLexAtLeast(minSize, input), output);
    }

    private static void stringSubsetsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringSubsetsLexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsetsLexAtLeast() {
        stringSubsetsLexAtLeast_helper(0, "", "ExhaustiveProvider_stringSubsetsLexAtLeast_i");
        stringSubsetsLexAtLeast_helper(1, "", "ExhaustiveProvider_stringSubsetsLexAtLeast_ii");
        stringSubsetsLexAtLeast_helper(2, "", "ExhaustiveProvider_stringSubsetsLexAtLeast_iii");
        stringSubsetsLexAtLeast_helper(3, "", "ExhaustiveProvider_stringSubsetsLexAtLeast_iv");

        stringSubsetsLexAtLeast_helper(0, "a", "ExhaustiveProvider_stringSubsetsLexAtLeast_v");
        stringSubsetsLexAtLeast_helper(1, "a", "ExhaustiveProvider_stringSubsetsLexAtLeast_vi");
        stringSubsetsLexAtLeast_helper(2, "a", "ExhaustiveProvider_stringSubsetsLexAtLeast_vii");
        stringSubsetsLexAtLeast_helper(3, "a", "ExhaustiveProvider_stringSubsetsLexAtLeast_viii");

        stringSubsetsLexAtLeast_helper(0, "abc", "ExhaustiveProvider_stringSubsetsLexAtLeast_ix");
        stringSubsetsLexAtLeast_helper(1, "abc", "ExhaustiveProvider_stringSubsetsLexAtLeast_x");
        stringSubsetsLexAtLeast_helper(2, "abc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xi");
        stringSubsetsLexAtLeast_helper(3, "abc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xii");

        stringSubsetsLexAtLeast_helper(0, "abbc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xiii");
        stringSubsetsLexAtLeast_helper(1, "abbc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xiv");
        stringSubsetsLexAtLeast_helper(2, "abbc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xv");
        stringSubsetsLexAtLeast_helper(3, "abbc", "ExhaustiveProvider_stringSubsetsLexAtLeast_xvi");

        stringSubsetsLexAtLeast_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringSubsetsLexAtLeast_xvii");
        stringSubsetsLexAtLeast_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringSubsetsLexAtLeast_xviii");
        stringSubsetsLexAtLeast_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringSubsetsLexAtLeast_xix");
        stringSubsetsLexAtLeast_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringSubsetsLexAtLeast_xx");

        stringSubsetsLexAtLeast_fail_helper(-1, "");
        stringSubsetsLexAtLeast_fail_helper(-1, "abc");
    }

    private static void subsetsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetsShortlex(readIntegerList(input)), output);
    }

    private static void subsetsShortlex_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetsShortlex(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsShortlex() {
        subsetsShortlex_helper("[]", "ExhaustiveProvider_subsetsShortlex_i");
        subsetsShortlex_helper("[5]", "ExhaustiveProvider_subsetsShortlex_ii");
        subsetsShortlex_helper("[1, 2, 3]", "ExhaustiveProvider_subsetsShortlex_iii");
        subsetsShortlex_helper("[1, 2, 2, 3]", "ExhaustiveProvider_subsetsShortlex_iv");

        subsetsShortlex_fail_helper("[1, null, 3]");
        subsetsShortlex_fail_helper("[null]");
    }

    private static void stringSubsetsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsShortlex(input), output);
    }

    @Test
    public void testStringSubsetsShortlex() {
        stringSubsetsShortlex_helper("", "ExhaustiveProvider_stringSubsetsShortlex_i");
        stringSubsetsShortlex_helper("a", "ExhaustiveProvider_stringSubsetsShortlex_ii");
        stringSubsetsShortlex_helper("abc", "ExhaustiveProvider_stringSubsetsShortlex_iii");
        stringSubsetsShortlex_helper("abbc", "ExhaustiveProvider_stringSubsetsShortlex_iv");
        stringSubsetsShortlex_helper("Mississippi", "ExhaustiveProvider_stringSubsetsShortlex_v");
    }

    private static void subsetsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetsShortlexAtLeast(minSize, readIntegerList(input)), output);
    }

    private static void subsetsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.subsetsShortlexAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsShortlexAtLeast() {
        subsetsShortlexAtLeast_helper(0, "[]", "ExhaustiveProvider_subsetsShortlexAtLeast_i");
        subsetsShortlexAtLeast_helper(1, "[]", "ExhaustiveProvider_subsetsShortlexAtLeast_ii");
        subsetsShortlexAtLeast_helper(2, "[]", "ExhaustiveProvider_subsetsShortlexAtLeast_iii");
        subsetsShortlexAtLeast_helper(3, "[]", "ExhaustiveProvider_subsetsShortlexAtLeast_iv");

        subsetsShortlexAtLeast_helper(0, "[5]", "ExhaustiveProvider_subsetsShortlexAtLeast_v");
        subsetsShortlexAtLeast_helper(1, "[5]", "ExhaustiveProvider_subsetsShortlexAtLeast_vi");
        subsetsShortlexAtLeast_helper(2, "[5]", "ExhaustiveProvider_subsetsShortlexAtLeast_vii");
        subsetsShortlexAtLeast_helper(3, "[5]", "ExhaustiveProvider_subsetsShortlexAtLeast_viii");

        subsetsShortlexAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsetsShortlexAtLeast_ix");
        subsetsShortlexAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsetsShortlexAtLeast_x");
        subsetsShortlexAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsetsShortlexAtLeast_xi");
        subsetsShortlexAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsetsShortlexAtLeast_xii");

        subsetsShortlexAtLeast_fail_helper(-1, "[]");
        subsetsShortlexAtLeast_fail_helper(-1, "[1, 2, 3]");
        subsetsShortlexAtLeast_fail_helper(1, "[1, null, 3]");
        subsetsShortlexAtLeast_fail_helper(1, "[null]");
    }

    private static void stringSubsetsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsShortlexAtLeast(minSize, input), output);
    }

    private static void stringSubsetsShortlexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringSubsetsShortlexAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsetsShortlexAtLeast() {
        stringSubsetsShortlexAtLeast_helper(0, "", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_i");
        stringSubsetsShortlexAtLeast_helper(1, "", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_ii");
        stringSubsetsShortlexAtLeast_helper(2, "", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_iii");
        stringSubsetsShortlexAtLeast_helper(3, "", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_iv");

        stringSubsetsShortlexAtLeast_helper(0, "a", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_v");
        stringSubsetsShortlexAtLeast_helper(1, "a", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_vi");
        stringSubsetsShortlexAtLeast_helper(2, "a", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_vii");
        stringSubsetsShortlexAtLeast_helper(3, "a", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_viii");

        stringSubsetsShortlexAtLeast_helper(0, "abc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_ix");
        stringSubsetsShortlexAtLeast_helper(1, "abc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_x");
        stringSubsetsShortlexAtLeast_helper(2, "abc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xi");
        stringSubsetsShortlexAtLeast_helper(3, "abc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xii");

        stringSubsetsShortlexAtLeast_helper(0, "abbc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xiii");
        stringSubsetsShortlexAtLeast_helper(1, "abbc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xiv");
        stringSubsetsShortlexAtLeast_helper(2, "abbc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xv");
        stringSubsetsShortlexAtLeast_helper(3, "abbc", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xvi");

        stringSubsetsShortlexAtLeast_helper(0, "Mississippi", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xvii");
        stringSubsetsShortlexAtLeast_helper(1, "Mississippi", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xviii");
        stringSubsetsShortlexAtLeast_helper(2, "Mississippi", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xix");
        stringSubsetsShortlexAtLeast_helper(3, "Mississippi", "ExhaustiveProvider_stringSubsetsShortlexAtLeast_xx");

        stringSubsetsShortlexAtLeast_fail_helper(-1, "");
        stringSubsetsShortlexAtLeast_fail_helper(-1, "abc");
    }

    private static void subsets_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.subsets(size, readIntegerList(input)), output);
    }

    private static void subsets_int_Iterable_limit_helper(
            int size,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.subsets(size, input), output);
    }

    private static void subsets_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            toList(EP.subsets(size, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testSubsets_int_Iterable() {
        subsets_int_Iterable_helper(0, "[]", "ExhaustiveProvider_subsets_int_Iterable_i");
        subsets_int_Iterable_helper(1, "[]", "ExhaustiveProvider_subsets_int_Iterable_ii");
        subsets_int_Iterable_helper(2, "[]", "ExhaustiveProvider_subsets_int_Iterable_iii");
        subsets_int_Iterable_helper(3, "[]", "ExhaustiveProvider_subsets_int_Iterable_iv");

        subsets_int_Iterable_helper(0, "[5]", "ExhaustiveProvider_subsets_int_Iterable_v");
        subsets_int_Iterable_helper(1, "[5]", "ExhaustiveProvider_subsets_int_Iterable_vi");
        subsets_int_Iterable_helper(2, "[5]", "ExhaustiveProvider_subsets_int_Iterable_vii");
        subsets_int_Iterable_helper(3, "[5]", "ExhaustiveProvider_subsets_int_Iterable_viii");

        subsets_int_Iterable_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_ix");
        subsets_int_Iterable_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_x");
        subsets_int_Iterable_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xi");
        subsets_int_Iterable_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xii");

        subsets_int_Iterable_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xiii");
        subsets_int_Iterable_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xiv");
        subsets_int_Iterable_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xv");
        subsets_int_Iterable_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_subsets_int_Iterable_xvi");

        subsets_int_Iterable_limit_helper(0, EP.positiveIntegers(), "ExhaustiveProvider_subsets_int_Iterable_xvii");
        subsets_int_Iterable_limit_helper(1, EP.positiveIntegers(), "ExhaustiveProvider_subsets_int_Iterable_xviii");
        subsets_int_Iterable_limit_helper(2, EP.positiveIntegers(), "ExhaustiveProvider_subsets_int_Iterable_xix");
        subsets_int_Iterable_limit_helper(3, EP.positiveIntegers(), "ExhaustiveProvider_subsets_int_Iterable_xx");

        subsets_int_Iterable_limit_helper(0, repeat(1), "ExhaustiveProvider_subsets_int_Iterable_xxi");
        subsets_int_Iterable_limit_helper(1, repeat(1), "ExhaustiveProvider_subsets_int_Iterable_xxii");
        subsets_int_Iterable_limit_helper(2, repeat(1), "ExhaustiveProvider_subsets_int_Iterable_xxiii");
        subsets_int_Iterable_limit_helper(3, repeat(1), "ExhaustiveProvider_subsets_int_Iterable_xxiv");

        subsets_int_Iterable_fail_helper(-1, "[]");
        subsets_int_Iterable_fail_helper(-1, "[1, 2, 3]");
        subsets_int_Iterable_fail_helper(1, "[1, null, 3]");
        subsets_int_Iterable_fail_helper(1, "[null]");
    }

    private static void subsetPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetPairs(input), output);
    }

    private static void subsetPairs_helper(@NotNull String input, @NotNull String output) {
        subsetPairs_helper(readIntegerList(input), output);
    }

    private static void subsetPairs_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetPairs(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetPairs() {
        subsetPairs_helper("[]", "ExhaustiveProvider_subsetPairs_i");
        subsetPairs_helper("[5]", "ExhaustiveProvider_subsetPairs_ii");
        subsetPairs_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetPairs_iii");
        subsetPairs_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetPairs_iv");
        subsetPairs_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetPairs_v");
        subsetPairs_helper(repeat(1), "ExhaustiveProvider_subsetPairs_vi");

        subsetPairs_fail_helper("[1, null, 3]");
    }

    private static void subsetTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetTriples(input), output);
    }

    private static void subsetTriples_helper(@NotNull String input, @NotNull String output) {
        subsetTriples_helper(readIntegerList(input), output);
    }

    private static void subsetTriples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetTriples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetTriples() {
        subsetTriples_helper("[]", "ExhaustiveProvider_subsetTriples_i");
        subsetTriples_helper("[5]", "ExhaustiveProvider_subsetTriples_ii");
        subsetTriples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetTriples_iii");
        subsetTriples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetTriples_iv");
        subsetTriples_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetTriples_v");
        subsetTriples_helper(repeat(1), "ExhaustiveProvider_subsetTriples_vi");

        subsetTriples_fail_helper("[1, null, 3]");
    }

    private static void subsetQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuadruples(input), output);
    }

    private static void subsetQuadruples_helper(@NotNull String input, @NotNull String output) {
        subsetQuadruples_helper(readIntegerList(input), output);
    }

    private static void subsetQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetQuadruples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuadruples() {
        subsetQuadruples_helper("[]", "ExhaustiveProvider_subsetQuadruples_i");
        subsetQuadruples_helper("[5]", "ExhaustiveProvider_subsetQuadruples_ii");
        subsetQuadruples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetQuadruples_iii");
        subsetQuadruples_helper("[1, 2, 2, 4]", "ExhaustiveProvider_subsetQuadruples_iv");
        subsetQuadruples_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetQuadruples_v");
        subsetQuadruples_helper(repeat(1), "ExhaustiveProvider_subsetQuadruples_vi");

        subsetQuadruples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuintuples(input), output);
    }

    private static void subsetQuintuples_helper(@NotNull String input, @NotNull String output) {
        subsetQuintuples_helper(readIntegerList(input), output);
    }

    private static void subsetQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetQuintuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuintuples() {
        subsetQuintuples_helper("[]", "ExhaustiveProvider_subsetQuintuples_i");
        subsetQuintuples_helper("[5]", "ExhaustiveProvider_subsetQuintuples_ii");
        subsetQuintuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetQuintuples_iii");
        subsetQuintuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetQuintuples_iv");
        subsetQuintuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetQuintuples_v");
        subsetQuintuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetQuintuples_vi");
        subsetQuintuples_helper(repeat(1), "ExhaustiveProvider_subsetQuintuples_vii");

        subsetQuintuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSextuples(input), output);
    }

    private static void subsetSextuples_helper(@NotNull String input, @NotNull String output) {
        subsetSextuples_helper(readIntegerList(input), output);
    }

    private static void subsetSextuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetSextuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSextuples() {
        subsetSextuples_helper("[]", "ExhaustiveProvider_subsetSextuples_i");
        subsetSextuples_helper("[5]", "ExhaustiveProvider_subsetSextuples_ii");
        subsetSextuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetSextuples_iii");
        subsetSextuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSextuples_iv");
        subsetSextuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSextuples_v");
        subsetSextuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetSextuples_vi");
        subsetSextuples_helper(repeat(1), "ExhaustiveProvider_subsetSextuples_vii");

        subsetSextuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSeptuples(input), output);
    }

    private static void subsetSeptuples_helper(@NotNull String input, @NotNull String output) {
        subsetSeptuples_helper(readIntegerList(input), output);
    }

    private static void subsetSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetSeptuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSeptuples() {
        subsetSeptuples_helper("[]", "ExhaustiveProvider_subsetSeptuples_i");
        subsetSeptuples_helper("[5]", "ExhaustiveProvider_subsetSeptuples_ii");
        subsetSeptuples_helper("[1, 2, 3, 4]", "ExhaustiveProvider_subsetSeptuples_iii");
        subsetSeptuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSeptuples_iv");
        subsetSeptuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]", "ExhaustiveProvider_subsetSeptuples_v");
        subsetSeptuples_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetSeptuples_vi");
        subsetSeptuples_helper(repeat(1), "ExhaustiveProvider_subsetSeptuples_vii");

        subsetSeptuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void stringSubsets_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.stringSubsets(size, input), output);
    }

    private static void stringSubsets_int_String_limit_helper(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsets(size, input), output);
    }

    private static void stringsSubsets_int_String_fail_helper(int size, @NotNull String input) {
        try {
            EP.stringSubsets(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsets_int_String() {
        stringSubsets_int_String_helper(0, "", "ExhaustiveProvider_stringSubsets_int_String_i");
        stringSubsets_int_String_helper(1, "", "ExhaustiveProvider_stringSubsets_int_String_ii");
        stringSubsets_int_String_helper(2, "", "ExhaustiveProvider_stringSubsets_int_String_iii");
        stringSubsets_int_String_helper(3, "", "ExhaustiveProvider_stringSubsets_int_String_iv");

        stringSubsets_int_String_helper(0, "a", "ExhaustiveProvider_stringSubsets_int_String_v");
        stringSubsets_int_String_helper(1, "a", "ExhaustiveProvider_stringSubsets_int_String_vi");
        stringSubsets_int_String_helper(2, "a", "ExhaustiveProvider_stringSubsets_int_String_vii");
        stringSubsets_int_String_helper(3, "a", "ExhaustiveProvider_stringSubsets_int_String_viii");

        stringSubsets_int_String_helper(0, "abc", "ExhaustiveProvider_stringSubsets_int_String_ix");
        stringSubsets_int_String_helper(1, "abc", "ExhaustiveProvider_stringSubsets_int_String_x");
        stringSubsets_int_String_helper(2, "abc", "ExhaustiveProvider_stringSubsets_int_String_xi");
        stringSubsets_int_String_helper(3, "abc", "ExhaustiveProvider_stringSubsets_int_String_xii");

        stringSubsets_int_String_helper(0, "abbc", "ExhaustiveProvider_stringSubsets_int_String_xiii");
        stringSubsets_int_String_helper(1, "abbc", "ExhaustiveProvider_stringSubsets_int_String_xiv");
        stringSubsets_int_String_helper(2, "abbc", "ExhaustiveProvider_stringSubsets_int_String_xv");
        stringSubsets_int_String_helper(3, "abbc", "ExhaustiveProvider_stringSubsets_int_String_xvi");

        stringSubsets_int_String_limit_helper(0, "Mississippi", "ExhaustiveProvider_stringSubsets_int_String_xvii");
        stringSubsets_int_String_limit_helper(1, "Mississippi", "ExhaustiveProvider_stringSubsets_int_String_xviii");
        stringSubsets_int_String_limit_helper(2, "Mississippi", "ExhaustiveProvider_stringSubsets_int_String_xix");
        stringSubsets_int_String_limit_helper(3, "Mississippi", "ExhaustiveProvider_stringSubsets_int_String_xx");

        stringsSubsets_int_String_fail_helper(-1, "");
        stringsSubsets_int_String_fail_helper(-1, "abc");
    }

    private static void stringSubsets_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsets(size), output);
    }

    private static void stringSubsets_int_fail_helper(int size) {
        try {
            EP.stringSubsets(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsets_int() {
        stringSubsets_int_helper(0, "ExhaustiveProvider_stringSubsets_int_i");
        stringSubsets_int_helper(1, "ExhaustiveProvider_stringSubsets_int_ii");
        stringSubsets_int_helper(2, "ExhaustiveProvider_stringSubsets_int_iii");
        stringSubsets_int_helper(3, "ExhaustiveProvider_stringSubsets_int_iv");

        stringSubsets_int_fail_helper(-1);
    }

    private static void subsets_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsets(input), output);
    }

    private static void subsets_Iterable_helper(@NotNull String input, @NotNull String output) {
        subsets_Iterable_helper(readIntegerList(input), output);
    }

    private static void subsets_Iterable_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsets(readIntegerListWithNulls(input)));
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsets_Iterable() {
        subsets_Iterable_helper("[]", "ExhaustiveProvider_subsetsIterable_i");
        subsets_Iterable_helper("[5]", "ExhaustiveProvider_subsetsIterable_ii");
        subsets_Iterable_helper("[1, 2, 3]", "ExhaustiveProvider_subsetsIterable_iii");
        subsets_Iterable_helper("[1, 2, 2, 3]", "ExhaustiveProvider_subsetsIterable_iv");
        subsets_Iterable_helper(EP.naturalIntegers(), "ExhaustiveProvider_subsetsIterable_v");
        subsets_Iterable_helper(repeat(1), "ExhaustiveProvider_subsetsIterable_vi");

        subsets_Iterable_fail_helper("[null]");
        subsets_Iterable_fail_helper("[1, null, 3]");
    }

    private static void stringSubsets_String_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsets(input), output);
    }

    @Test
    public void testStringSubsets_String() {
        stringSubsets_String_helper("", "ExhaustiveProvider_stringSubsets_String_i");
        stringSubsets_String_helper("a", "ExhaustiveProvider_stringSubsets_String_ii");
        stringSubsets_String_helper("abc", "ExhaustiveProvider_stringSubsets_String_iii");
        stringSubsets_String_helper("abbc", "ExhaustiveProvider_stringSubsets_String_iv");
        stringSubsets_String_helper("Mississippi", "ExhaustiveProvider_stringSubsets_String_v");
    }

    @Test
    public void testStringSubsets() {
        simpleProviderHelper(EP.stringSubsets(), "ExhaustiveProvider_stringSubsets");
    }

    private static void subsetsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetsAtLeast(minSize, input), output);
    }

    private static void subsetsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        subsetsAtLeast_helper(minSize, readIntegerList(input), output);
    }

    private static void subsetsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.subsetsAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsAtLeast() {
        subsetsAtLeast_helper(0, "[]", "ExhaustiveProvider_subsetsAtLeast_i");
        subsetsAtLeast_helper(1, "[]", "ExhaustiveProvider_subsetsAtLeast_ii");
        subsetsAtLeast_helper(2, "[]", "ExhaustiveProvider_subsetsAtLeast_iii");
        subsetsAtLeast_helper(3, "[]", "ExhaustiveProvider_subsetsAtLeast_iv");

        subsetsAtLeast_helper(0, "[5]", "ExhaustiveProvider_subsetsAtLeast_v");
        subsetsAtLeast_helper(1, "[5]", "ExhaustiveProvider_subsetsAtLeast_vi");
        subsetsAtLeast_helper(2, "[5]", "ExhaustiveProvider_subsetsAtLeast_vii");
        subsetsAtLeast_helper(3, "[5]", "ExhaustiveProvider_subsetsAtLeast_viii");

        subsetsAtLeast_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_ix");
        subsetsAtLeast_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_x");
        subsetsAtLeast_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xi");
        subsetsAtLeast_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xii");

        subsetsAtLeast_helper(0, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xiii");
        subsetsAtLeast_helper(1, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xiv");
        subsetsAtLeast_helper(2, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xv");
        subsetsAtLeast_helper(3, "[1, 2, 2, 3]", "ExhaustiveProvider_subsetsAtLeast_xvi");

        subsetsAtLeast_helper(0, EP.naturalIntegers(), "ExhaustiveProvider_subsetsAtLeast_xvii");
        subsetsAtLeast_helper(1, EP.naturalIntegers(), "ExhaustiveProvider_subsetsAtLeast_xviii");
        subsetsAtLeast_helper(2, EP.naturalIntegers(), "ExhaustiveProvider_subsetsAtLeast_xix");
        subsetsAtLeast_helper(3, EP.naturalIntegers(), "ExhaustiveProvider_subsetsAtLeast_xx");

        subsetsAtLeast_helper(0, repeat(1), "ExhaustiveProvider_subsetsAtLeast_xxi");
        subsetsAtLeast_helper(1, repeat(1), "ExhaustiveProvider_subsetsAtLeast_xxii");
        subsetsAtLeast_helper(2, repeat(1), "ExhaustiveProvider_subsetsAtLeast_xxiii");
        subsetsAtLeast_helper(3, repeat(1), "ExhaustiveProvider_subsetsAtLeast_xxiv");

        subsetsAtLeast_fail_helper(-1, "[]");
        subsetsAtLeast_fail_helper(-1, "[1, 2, 3]");
        subsetsAtLeast_fail_helper(1, "[null]");
        subsetsAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringSubsetsAtLeast_int_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsAtLeast(minSize, input), output);
    }

    private static void stringSubsetsAtLeast_int_String_fail_helper(int minSize, @NotNull String input) {
        try {
            EP.stringSubsetsAtLeast(minSize, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsetsAtLeast_int_String() {
        stringSubsetsAtLeast_int_String_helper(0, "", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_i");
        stringSubsetsAtLeast_int_String_helper(1, "", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_ii");
        stringSubsetsAtLeast_int_String_helper(2, "", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_iii");
        stringSubsetsAtLeast_int_String_helper(3, "", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_iv");

        stringSubsetsAtLeast_int_String_helper(0, "a", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_v");
        stringSubsetsAtLeast_int_String_helper(1, "a", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_vi");
        stringSubsetsAtLeast_int_String_helper(2, "a", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_vii");
        stringSubsetsAtLeast_int_String_helper(3, "a", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_viii");

        stringSubsetsAtLeast_int_String_helper(0, "abc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_ix");
        stringSubsetsAtLeast_int_String_helper(1, "abc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_x");
        stringSubsetsAtLeast_int_String_helper(2, "abc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xi");
        stringSubsetsAtLeast_int_String_helper(3, "abc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xii");

        stringSubsetsAtLeast_int_String_helper(0, "abbc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xiii");
        stringSubsetsAtLeast_int_String_helper(1, "abbc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xiv");
        stringSubsetsAtLeast_int_String_helper(2, "abbc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xv");
        stringSubsetsAtLeast_int_String_helper(3, "abbc", "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xvi");

        stringSubsetsAtLeast_int_String_helper(
                0,
                "Mississippi",
                "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xvii"
        );
        stringSubsetsAtLeast_int_String_helper(
                1,
                "Mississippi",
                "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xviii"
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                "Mississippi",
                "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xix"
        );
        stringSubsetsAtLeast_int_String_helper(
                3,
                "Mississippi",
                "ExhaustiveProvider_stringSubsetsAtLeast_int_String_xx"
        );

        stringSubsetsAtLeast_int_String_fail_helper(-1, "");
        stringSubsetsAtLeast_int_String_fail_helper(-1, "abc");
    }

    private static void stringSubsetsAtLeast_int_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsAtLeast(minSize), output);
    }

    private static void stringSubsetsAtLeast_int_fail_helper(int minSize) {
        try {
            EP.stringSubsetsAtLeast(minSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringSubsetsAtLeast_int() {
        stringSubsetsAtLeast_int_helper(0, "ExhaustiveProvider_stringSubsetsAtLeast_int_i");
        stringSubsetsAtLeast_int_helper(1, "ExhaustiveProvider_stringSubsetsAtLeast_int_ii");
        stringSubsetsAtLeast_int_helper(2, "ExhaustiveProvider_stringSubsetsAtLeast_int_iii");
        stringSubsetsAtLeast_int_helper(3, "ExhaustiveProvider_stringSubsetsAtLeast_int_iv");

        stringSubsetsAtLeast_int_fail_helper(-1);
    }

    private static <A, B> void eithersSuccessive_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.eithersSuccessive(as, bs), output);
    }

    @Test
    public void testEithersSuccessive() {
        eithersSuccessive_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersSuccessive_i");
        eithersSuccessive_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersSuccessive_ii");
        eithersSuccessive_helper(Arrays.asList(1, 2), fromString("abcd"),
                "ExhaustiveProvider_eithersSuccessive_iii");
        eithersSuccessive_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "ExhaustiveProvider_eithersSuccessive_iv");
        eithersSuccessive_helper(Collections.emptyList(), fromString("abcd"),
                "ExhaustiveProvider_eithersSuccessive_v");
        eithersSuccessive_helper(Collections.emptyList(), Collections.emptyList(),
                "ExhaustiveProvider_eithersSuccessive_vi");
        simpleProviderHelper(EP.eithersSuccessive(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_eithersSuccessive_vii");
        simpleProviderHelper(EP.eithersSuccessive(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_eithersSuccessive_viii");
        simpleProviderHelper(EP.eithersSuccessive(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_eithersSuccessive_ix");
    }

    private static <A, B> void eithersSquareRootOrder_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.eithersSquareRootOrder(as, bs), output);
    }

    @Test
    public void testEithersSquareRootOrder() {
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersSquareRootOrder_i");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersSquareRootOrder_ii");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2), fromString("abcd"),
                "ExhaustiveProvider_eithersSquareRootOrder_iii");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "ExhaustiveProvider_eithersSquareRootOrder_iv");
        eithersSquareRootOrder_helper(Collections.emptyList(), fromString("abcd"),
                "ExhaustiveProvider_eithersSquareRootOrder_v");
        eithersSquareRootOrder_helper(Collections.emptyList(), Collections.emptyList(),
                "ExhaustiveProvider_eithersSquareRootOrder_vi");
        simpleProviderHelper(EP.eithersSquareRootOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_eithersSquareRootOrder_vii");
        simpleProviderHelper(EP.eithersSquareRootOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_eithersSquareRootOrder_viii");
        simpleProviderHelper(EP.eithersSquareRootOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_eithersSquareRootOrder_ix");
    }

    private static <A, B> void eithersLogarithmicOrder_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.eithersLogarithmicOrder(as, bs), output);
    }

    @Test
    public void testEithersLogarithmicOrder() {
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersLogarithmicOrder_i");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "ExhaustiveProvider_eithersLogarithmicOrder_ii");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2), fromString("abcd"),
                "ExhaustiveProvider_eithersLogarithmicOrder_iii");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "ExhaustiveProvider_eithersLogarithmicOrder_iv");
        eithersLogarithmicOrder_helper(Collections.emptyList(), fromString("abcd"),
                "ExhaustiveProvider_eithersLogarithmicOrder_v");
        eithersLogarithmicOrder_helper(Collections.emptyList(), Collections.emptyList(),
                "ExhaustiveProvider_eithersLogarithmicOrder_vi");
        simpleProviderHelper(EP.eithersLogarithmicOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_eithersLogarithmicOrder_vii");
        simpleProviderHelper(EP.eithersLogarithmicOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_eithersLogarithmicOrder_viii");
        simpleProviderHelper(EP.eithersLogarithmicOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_eithersLogarithmicOrder_ix");
    }

    private static <A, B> void eithers_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqitLog(EP.eithers(as, bs), output);
    }

    @Test
    public void testEithers() {
        eithers_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"), "ExhaustiveProvider_eithers_i");
        eithers_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"), "ExhaustiveProvider_eithers_ii");
        eithers_helper(Arrays.asList(1, 2), fromString("abcd"), "ExhaustiveProvider_eithers_iii");
        eithers_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"), "ExhaustiveProvider_eithers_iv");
        eithers_helper(Collections.emptyList(), fromString("abcd"), "ExhaustiveProvider_eithers_v");
        eithers_helper(Collections.emptyList(), Collections.emptyList(), "ExhaustiveProvider_eithers_vi");
        simpleProviderHelper(EP.eithers(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_eithers_vii");
        simpleProviderHelper(EP.eithers(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_eithers_viii");
        simpleProviderHelper(EP.eithers(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_eithers_ix");
    }

    private static void chooseSquareRootOrder_helper(@NotNull String as, @NotNull String bs, @NotNull String output) {
        aeqitLog(EP.chooseSquareRootOrder(readIntegerListWithNulls(as), readIntegerListWithNulls(bs)), output);
    }

    @Test
    public void testChooseSquareRootOrder() {
        chooseSquareRootOrder_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]", "ExhaustiveProvider_chooseSquareRootOrder_i");
        chooseSquareRootOrder_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_chooseSquareRootOrder_ii");
        chooseSquareRootOrder_helper("[1, 2]", "[-1, -2, -3, -4]", "ExhaustiveProvider_chooseSquareRootOrder_iii");
        chooseSquareRootOrder_helper("[1, 2, 3, 4]", "[-1, -2]", "ExhaustiveProvider_chooseSquareRootOrder_iv");
        chooseSquareRootOrder_helper("[]", "[1, 2, 3, 4]", "ExhaustiveProvider_chooseSquareRootOrder_v");
        chooseSquareRootOrder_helper("[]", "[]", "ExhaustiveProvider_chooseSquareRootOrder_vi");
        simpleProviderHelper(EP.chooseSquareRootOrder(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "ExhaustiveProvider_chooseSquareRootOrder_vii");
        simpleProviderHelper(EP.chooseSquareRootOrder(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "ExhaustiveProvider_chooseSquareRootOrder_viii");
        simpleProviderHelper(EP.chooseSquareRootOrder(EP.positiveIntegers(), EP.negativeIntegers()),
                "ExhaustiveProvider_chooseSquareRootOrder_ix");
    }

    private static void chooseLogarithmicOrder_helper(@NotNull String as, @NotNull String bs, @NotNull String output) {
        aeqitLog(EP.chooseLogarithmicOrder(readIntegerListWithNulls(as), readIntegerListWithNulls(bs)), output);
    }

    @Test
    public void testChooseLogarithmicOrder() {
        chooseLogarithmicOrder_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_chooseLogarithmicOrder_i");
        chooseLogarithmicOrder_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_chooseLogarithmicOrder_ii");
        chooseLogarithmicOrder_helper("[1, 2]", "[-1, -2, -3, -4]", "ExhaustiveProvider_chooseLogarithmicOrder_iii");
        chooseLogarithmicOrder_helper("[1, 2, 3, 4]", "[-1, -2]", "ExhaustiveProvider_chooseLogarithmicOrder_iv");
        chooseLogarithmicOrder_helper("[]", "[1, 2, 3, 4]", "ExhaustiveProvider_chooseLogarithmicOrder_v");
        chooseLogarithmicOrder_helper("[]", "[]", "ExhaustiveProvider_chooseLogarithmicOrder_vi");
        simpleProviderHelper(EP.chooseLogarithmicOrder(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "ExhaustiveProvider_chooseLogarithmicOrder_vii");
        simpleProviderHelper(EP.chooseLogarithmicOrder(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "ExhaustiveProvider_chooseLogarithmicOrder_viii");
        simpleProviderHelper(EP.chooseLogarithmicOrder(EP.positiveIntegers(), EP.negativeIntegers()),
                "ExhaustiveProvider_chooseLogarithmicOrder_ix");
    }

    private static void choose_Iterable_Iterable_helper(
            @NotNull String as,
            @NotNull String bs,
            @NotNull String output
    ) {
        aeqitLog(EP.choose(readIntegerListWithNulls(as), readIntegerListWithNulls(bs)), output);
    }

    @Test
    public void testChoose_Iterable_Iterable() {
        choose_Iterable_Iterable_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_choose_Iterable_Iterable_i");
        choose_Iterable_Iterable_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_choose_Iterable_Iterable_ii");
        choose_Iterable_Iterable_helper("[1, 2]", "[-1, -2, -3, -4]",
                "ExhaustiveProvider_choose_Iterable_Iterable_iii");
        choose_Iterable_Iterable_helper("[1, 2, 3, 4]", "[-1, -2]", "ExhaustiveProvider_choose_Iterable_Iterable_iv");
        choose_Iterable_Iterable_helper("[]", "[1, 2, 3, 4]", "ExhaustiveProvider_choose_Iterable_Iterable_v");
        choose_Iterable_Iterable_helper("[]", "[]", "ExhaustiveProvider_choose_Iterable_Iterable_vi");
        simpleProviderHelper(EP.choose(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "ExhaustiveProvider_choose_Iterable_Iterable_vii");
        simpleProviderHelper(EP.choose(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "ExhaustiveProvider_choose_Iterable_Iterable_viii");
        simpleProviderHelper(EP.choose(EP.positiveIntegers(), EP.negativeIntegers()),
                "ExhaustiveProvider_choose_Iterable_Iterable_ix");
    }

    private static void choose_Iterable_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.choose(toList(map(is -> is, readIntegerListWithNullsLists(input)))), output);
    }

    private static void choose_Iterable_fail_helper(@NotNull String input) {
        try {
            toList(EP.choose(toList(map(is -> is, readIntegerListWithNullsListsWithNulls(input)))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void choose_Iterable() {
        choose_Iterable_helper("[]", "ExhaustiveProvider_choose_Iterable_i");
        choose_Iterable_helper("[[]]", "ExhaustiveProvider_choose_Iterable_ii");
        choose_Iterable_helper("[[1, 2, 3], [-1, -2, -3]]", "ExhaustiveProvider_choose_Iterable_iii");
        choose_Iterable_helper("[[1, 2, 3], [-1, -2, -3, -4]]", "ExhaustiveProvider_choose_Iterable_iv");
        choose_Iterable_helper("[[1, 2, 3, 4], [-1, -2, -3]]", "ExhaustiveProvider_choose_Iterable_v");
        choose_Iterable_helper("[[1, 2, 3], [10, 11], [100, null, 102, 103]]",
                "ExhaustiveProvider_choose_Iterable_vi");

        choose_Iterable_fail_helper("[[1, 2], null]");
    }

    private static void cartesianProduct_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.cartesianProduct(readIntegerListWithNullsLists(input)), output);
    }

    private static void cartesianProduct_fail_helper(@NotNull String input) {
        try {
            EP.cartesianProduct(readIntegerListWithNullsListsWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testCartesianProduct() {
        cartesianProduct_helper("[]", "ExhaustiveProvider_cartesianProduct_i");
        cartesianProduct_helper("[[]]", "ExhaustiveProvider_cartesianProduct_ii");
        cartesianProduct_helper("[[], []]", "ExhaustiveProvider_cartesianProduct_iii");
        cartesianProduct_helper("[[], [0]]", "ExhaustiveProvider_cartesianProduct_iv");
        cartesianProduct_helper("[[0], [0]]", "ExhaustiveProvider_cartesianProduct_v");
        cartesianProduct_helper("[[0, 0, 0]]", "ExhaustiveProvider_cartesianProduct_vi");
        cartesianProduct_helper("[[1, 2, 3]]", "ExhaustiveProvider_cartesianProduct_vii");
        cartesianProduct_helper("[[null]]", "ExhaustiveProvider_cartesianProduct_viii");
        cartesianProduct_helper("[[1], [1], [1], [1]]", "ExhaustiveProvider_cartesianProduct_ix");
        cartesianProduct_helper("[[0, 1, 2], [-3, -4], [null, 10]]", "ExhaustiveProvider_cartesianProduct_x");
        cartesianProduct_helper("[[0, 1], [0, 1], [0, 1]]", "ExhaustiveProvider_cartesianProduct_xi");

        cartesianProduct_fail_helper("[null]");
        cartesianProduct_fail_helper("[[1, 2, 3], null]");
    }

    private static void repeatingIterables_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.repeatingIterables(input)), output);
    }

    private static void repeatingIterables_helper(@NotNull String input, @NotNull String output) {
        repeatingIterables_helper(readIntegerListWithNulls(input), output);
    }

    private static void repeatingIterables_fail_helper(@NotNull String input) {
        try {
            EP.repeatingIterables(readIntegerListWithNulls(input));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRepeatingIterables() {
        repeatingIterables_helper("[0, 1]", "ExhaustiveProvider_repeatingIterables_i");
        repeatingIterables_helper("[1, 0]", "ExhaustiveProvider_repeatingIterables_ii");
        repeatingIterables_helper("[0, 1, 1]", "ExhaustiveProvider_repeatingIterables_iii");
        repeatingIterables_helper("[4, null, -5]", "ExhaustiveProvider_repeatingIterables_iv");
        repeatingIterables_helper(EP.naturalIntegers(), "ExhaustiveProvider_repeatingIterables_v");

        repeatingIterables_fail_helper("[]");
        repeatingIterables_fail_helper("[0]");
    }

    private static void repeatingIterablesDistinctAtLeast_helper(
            int minSize,
            @NotNull Iterable<Integer> xs,
            @NotNull String output
    ) {
        simpleProviderHelper(map(Testing::its, EP.repeatingIterablesDistinctAtLeast(minSize, xs)), output);
    }

    private static void repeatingIterablesDistinctAtLeast_helper(
            int minSize,
            @NotNull String xs,
            @NotNull String output
    ) {
        repeatingIterablesDistinctAtLeast_helper(minSize, readIntegerListWithNulls(xs), output);
    }

    private static void repeatingIterablesDistinctAtLeast_fail_helper(int minSize, @NotNull String xs) {
        try {
            EP.repeatingIterablesDistinctAtLeast(minSize, readIntegerListWithNulls(xs));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testRepeatingIterablesDistinctAtLeast() {
        repeatingIterablesDistinctAtLeast_helper(2, "[0, 1]",
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_i");
        repeatingIterablesDistinctAtLeast_helper(2, "[1, 0]",
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_ii");
        repeatingIterablesDistinctAtLeast_helper(2, "[0, 1, 1]",
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_iii");
        repeatingIterablesDistinctAtLeast_helper(2, "[4, null, -5]",
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_iv");
        repeatingIterablesDistinctAtLeast_helper(3, "[4, null, -5]",
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_v");
        repeatingIterablesDistinctAtLeast_helper(2, EP.naturalIntegers(),
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_vi");
        repeatingIterablesDistinctAtLeast_helper(5, EP.naturalIntegers(),
                "ExhaustiveProvider_repeatingIterablesDistinctAtLeast_vii");

        repeatingIterablesDistinctAtLeast_fail_helper(2, "[]");
        repeatingIterablesDistinctAtLeast_fail_helper(2, "[0]");
        repeatingIterablesDistinctAtLeast_fail_helper(-1, "[0, 1]");
    }

    private static void sublists_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.sublists(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testSublists() {
        sublists_helper("[]", "ExhaustiveProvider_sublists_i");
        sublists_helper("[1, 2, 3, 4]", "ExhaustiveProvider_sublists_ii");
        sublists_helper("[1, null, 3, 4]", "ExhaustiveProvider_sublists_iii");
        sublists_helper("[3, 1, 4, 1]", "ExhaustiveProvider_sublists_iv");
        sublists_helper("[1, 1, 1, 1]", "ExhaustiveProvider_sublists_v");
    }

    private static void substrings_helper(@NotNull String input, @NotNull String output) {
        aeqitLog(EP.substrings(input), output);
    }

    @Test
    public void testSubstrings() {
        substrings_helper("", "ExhaustiveProvider_substrings_i");
        substrings_helper("abcd", "ExhaustiveProvider_substrings_ii");
        substrings_helper("aaaa", "ExhaustiveProvider_substrings_iii");
        substrings_helper("Mississippi", "ExhaustiveProvider_substrings_iv");
    }

    private static void listsWithElement_helper(
            @Nullable Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.listsWithElement(x, input), output);
    }

    private static void listsWithElement_helper(@Nullable Integer x, @NotNull String input, @NotNull String output) {
        listsWithElement_helper(x, readIntegerListWithNulls(input), output);
    }

    @Test
    public void testListsWithElement() {
        listsWithElement_helper(0, "[]", "ExhaustiveProvider_listWithElement_i");
        listsWithElement_helper(0, "[1, 2, 3]", "ExhaustiveProvider_listWithElement_ii");
        listsWithElement_helper(null, "[1, 2, 3]", "ExhaustiveProvider_listWithElement_iii");
        listsWithElement_helper(0, "[0, 1, 2]", "ExhaustiveProvider_listWithElement_iv");
        listsWithElement_helper(null, EP.positiveIntegers(), "ExhaustiveProvider_listWithElement_v");
        listsWithElement_helper(3, EP.positiveIntegers(), "ExhaustiveProvider_listWithElement_vi");
        listsWithElement_helper(0, repeat(1), "ExhaustiveProvider_listWithElement_vii");
    }

    private static void stringsWithChar_char_String_helper(char c, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsWithChar(c, input), output);
    }

    @Test
    public void testStringsWithChar_char_String() {
        stringsWithChar_char_String_helper('a', "", "ExhaustiveProvider_stringsWithChar_char_String_i");
        stringsWithChar_char_String_helper('#', "abcd", "ExhaustiveProvider_stringsWithChar_char_String_ii");
        stringsWithChar_char_String_helper('a', "abcd", "ExhaustiveProvider_stringsWithChar_char_String_iii");
        stringsWithChar_char_String_helper('#', "aaaa", "ExhaustiveProvider_stringsWithChar_char_String_iv");
        stringsWithChar_char_String_helper('#', "Mississippi", "ExhaustiveProvider_stringsWithChar_char_String_v");
    }

    private static void stringsWithChar_char_helper(char c, @NotNull String output) {
        simpleProviderHelper(EP.stringsWithChar(c), output);
    }

    @Test
    public void testStringsWithChar_char() {
        stringsWithChar_char_helper('a', "ExhaustiveProvider_stringsWithChar_char_i");
        stringsWithChar_char_helper('#', "ExhaustiveProvider_stringsWithChar_char_ii");
    }

    private static void subsetsWithElement_helper(
            @NotNull Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.subsetsWithElement(x, input), output);
    }

    private static void subsetsWithElement_helper(@NotNull Integer x, @NotNull String input, @NotNull String output) {
        subsetsWithElement_helper(x, readIntegerListWithNulls(input), output);
    }

    @Test
    public void testSubsetsWithElement() {
        subsetsWithElement_helper(0, "[]", "ExhaustiveProvider_subsetsWithElement_i");
        subsetsWithElement_helper(0, "[1, 2, 3]", "ExhaustiveProvider_subsetsWithElement_ii");
        subsetsWithElement_helper(1, "[1, 2, 3]", "ExhaustiveProvider_subsetsWithElement_iii");
        subsetsWithElement_helper(2, "[1, 2, 3]", "ExhaustiveProvider_subsetsWithElement_iv");
        subsetsWithElement_helper(3, "[1, 2, 3]", "ExhaustiveProvider_subsetsWithElement_v");
        subsetsWithElement_helper(3, EP.positiveIntegers(), "ExhaustiveProvider_subsetsWithElement_vi");
        subsetsWithElement_helper(0, repeat(1), "ExhaustiveProvider_subsetsWithElement_vii");
    }

    private static void stringSubsetsWithChar_char_String_helper(
            char c,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsWithChar(c, input), output);
    }

    @Test
    public void testStringSubsetsWithChar_char_String() {
        stringSubsetsWithChar_char_String_helper('a', "", "ExhaustiveProvider_subsetsWithChar_char_String_i");
        stringSubsetsWithChar_char_String_helper('#', "abcd", "ExhaustiveProvider_subsetsWithChar_char_String_ii");
        stringSubsetsWithChar_char_String_helper('a', "aabcd", "ExhaustiveProvider_subsetsWithChar_char_String_iii");
        stringSubsetsWithChar_char_String_helper('a', "abcd", "ExhaustiveProvider_subsetsWithChar_char_String_iv");
        stringSubsetsWithChar_char_String_helper('a', "aabcd", "ExhaustiveProvider_subsetsWithChar_char_String_v");
        stringSubsetsWithChar_char_String_helper('#', "aaaa", "ExhaustiveProvider_subsetsWithChar_char_String_vi");
        stringSubsetsWithChar_char_String_helper('#', "Mississippi",
                "ExhaustiveProvider_subsetsWithChar_char_String_vii");
    }

    private static void stringSubsetsWithChar_char_helper(char c, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsWithChar(c), output);
    }

    @Test
    public void testStringSubsetsWithChar_char() {
        stringSubsetsWithChar_char_helper('a', "ExhaustiveProvider_subsetsWithChar_char_i");
        stringSubsetsWithChar_char_helper('#', "ExhaustiveProvider_subsetsWithChar_char_ii");
    }

    private static void listsWithSublistsHelper(
            @NotNull Iterable<List<Integer>> sublists,
            @NotNull Iterable<Integer> xs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.listsWithSublists(sublists, xs), output);
    }

    private static void listsWithSublistsHelper(@NotNull String sublists, @NotNull String xs, @NotNull String output) {
        simpleProviderHelper(
                EP.listsWithSublists(readIntegerListWithNullsLists(sublists), readIntegerList(xs)),
                output
        );
    }

    private static void listsWithSublists_fail_helper(@NotNull String sublists, @NotNull String xs) {
        try {
            EP.listsWithSublists(readIntegerListWithNullsListsWithNulls(sublists), readIntegerListWithNulls(xs));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testListsWithSublists() {
        listsWithSublistsHelper("[]", "[0]", "ExhaustiveProvider_listsWithSublists_i");
        listsWithSublistsHelper("[]", "[]", "ExhaustiveProvider_listsWithSublists_ii");
        listsWithSublistsHelper("[[]]", "[]", "ExhaustiveProvider_listsWithSublists_iii");
        listsWithSublistsHelper("[[]]", "[0]", "ExhaustiveProvider_listsWithSublists_iv");
        listsWithSublistsHelper("[[1, 0, 1]]", "[0]", "ExhaustiveProvider_listsWithSublists_v");
        listsWithSublistsHelper(Collections.singletonList(Arrays.asList(1, 0, 1)), EP.positiveIntegers(),
                "ExhaustiveProvider_listsWithSublists_vi");
        listsWithSublistsHelper(map(i -> Arrays.asList(i, i), EP.positiveIntegers()), Collections.singletonList(0),
                "ExhaustiveProvider_listsWithSublists_vii");
        listsWithSublistsHelper(map(i -> Arrays.asList(i, i), EP.positiveIntegers()), EP.positiveIntegers(),
                "ExhaustiveProvider_listsWithSublists_viii");

        listsWithSublists_fail_helper("[null]", "[0]");
    }

    private static void stringsWithSubstrings_Iterable_String_String_helper(
            @NotNull Iterable<String> substrings,
            @NotNull String s,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringsWithSubstrings(substrings, s), output);
    }

    private static void stringsWithSubstrings_Iterable_String_String_helper(
            @NotNull String substrings,
            @NotNull String s,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringsWithSubstrings(readStringList(substrings), s), output);
    }

    private static void stringsWithSubstrings_Iterable_String_String_fail_helper(
            @NotNull Iterable<String> substrings,
            @NotNull String s
    ) {
        try {
            EP.stringsWithSubstrings(substrings, s);
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String_String() {
        stringsWithSubstrings_Iterable_String_String_helper("[]", "",
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_i");
        stringsWithSubstrings_Iterable_String_String_helper("[]", charsToString(EP.rangeIncreasing('a', 'z')),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_ii");
        stringsWithSubstrings_Iterable_String_String_helper(Collections.singletonList(""), "",
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_iii");
        stringsWithSubstrings_Iterable_String_String_helper(
                Collections.singletonList(""),
                charsToString(EP.rangeIncreasing('a', 'z')),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_iv"
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                Collections.singletonList("cat"),
                charsToString(EP.rangeIncreasing('a', 'z')),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_v"
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                map(d -> Double.toString(d), EP.positiveDoubles()),
                charsToString(EP.rangeIncreasing('a', 'z')),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_String_vi"
        );

        stringsWithSubstrings_Iterable_String_String_fail_helper(
                Collections.singletonList(null),
                charsToString(EP.rangeIncreasing('a', 'z'))
        );
    }

    private static void stringsWithSubstrings_Iterable_String_helper(
            @NotNull Iterable<String> input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringsWithSubstrings(input), output);
    }

    private static void stringsWithSubstrings_Iterable_String_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsWithSubstrings(readStringList(input)), output);
    }

    private static void stringsWithSubstrings_Iterable_String_fail_helper(@NotNull String input) {
        try {
            toList(EP.stringsWithSubstrings(readStringListWithNulls(input)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String() {
        stringsWithSubstrings_Iterable_String_helper("[]",
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_i");
        stringsWithSubstrings_Iterable_String_helper(Collections.singletonList(""),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_ii");
        stringsWithSubstrings_Iterable_String_helper("[cat]",
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_iii");
        stringsWithSubstrings_Iterable_String_helper(map(d -> Double.toString(d), EP.positiveDoubles()),
                "ExhaustiveProvider_stringsWithSubstrings_Iterable_String_iv");

        stringsWithSubstrings_Iterable_String_fail_helper("[null]");
    }

    private static void maps_helper(@NotNull String keys, @NotNull Iterable<Integer> values, @NotNull String output) {
        simpleProviderHelper(EP.maps(readIntegerListWithNulls(keys), values), output);
    }

    private static void maps_helper(@NotNull String keys, @NotNull String values, @NotNull String output) {
        maps_helper(keys, readIntegerListWithNulls(values), output);
    }

    @Test
    public void testMaps() {
        maps_helper("[]", "[]", "ExhaustiveProvider_maps_i");
        maps_helper("[]", "[4]", "ExhaustiveProvider_maps_ii");
        maps_helper("[]", "[1, 2, 3]", "ExhaustiveProvider_maps_iii");
        maps_helper("[]", "[1, null, 3]", "ExhaustiveProvider_maps_iv");
        maps_helper("[]", EP.positiveIntegers(), "ExhaustiveProvider_maps_v");

        maps_helper("[4]", "[]", "ExhaustiveProvider_maps_vi");
        maps_helper("[4]", "[4]", "ExhaustiveProvider_maps_vii");
        maps_helper("[4]", "[1, 2, 3]", "ExhaustiveProvider_maps_viii");
        maps_helper("[4]", "[1, null, 3]", "ExhaustiveProvider_maps_ix");
        maps_helper("[4]", EP.positiveIntegers(), "ExhaustiveProvider_maps_x");

        maps_helper("[1, 2, 3]", "[]", "ExhaustiveProvider_maps_xi");
        maps_helper("[1, 2, 3]", "[4]", "ExhaustiveProvider_maps_xii");
        maps_helper("[1, 2, 3]", "[1, 2, 3]", "ExhaustiveProvider_maps_xiii");
        maps_helper("[1, 2, 3]", "[1, null, 3]", "ExhaustiveProvider_maps_xiv");
        maps_helper("[1, 2, 3]", EP.positiveIntegers(), "ExhaustiveProvider_maps_xv");

        maps_helper("[1, null, 3]", "[]", "ExhaustiveProvider_maps_xvi");
        maps_helper("[1, null, 3]", "[4]", "ExhaustiveProvider_maps_xvii");
        maps_helper("[1, null, 3]", "[1, 2, 3]", "ExhaustiveProvider_maps_xviii");
        maps_helper("[1, null, 3]", "[1, null, 3]", "ExhaustiveProvider_maps_xix");
        maps_helper("[1, null, 3]", EP.positiveIntegers(), "ExhaustiveProvider_maps_xx");
    }

    private static void randomProvidersFixedScales_helper(
            int scale,
            int secondaryScale,
            int tertiaryScale,
            @NotNull String output
    ) {
        Iterable<RandomProvider> rps = EP.randomProvidersFixedScales(scale, secondaryScale, tertiaryScale);
        take(TINY_LIMIT, rps).forEach(RandomProvider::validate);
        simpleProviderHelper(rps, output);
    }

    @Test
    public void testRandomProvidersFixedScales() {
        randomProvidersFixedScales_helper(8, 32, 2, "ExhaustiveProvider_randomProvidersFixedScales_i");
        randomProvidersFixedScales_helper(0, 0, 0, "ExhaustiveProvider_randomProvidersFixedScales_ii");
        randomProvidersFixedScales_helper(-5, -10, -1, "ExhaustiveProvider_randomProvidersFixedScales_iii");
    }

    @Test
    public void testRandomProvidersDefault() {
        Iterable<RandomProvider> rps = EP.randomProvidersDefault();
        take(TINY_LIMIT, rps).forEach(RandomProvider::validate);
        simpleProviderHelper(rps, "ExhaustiveProvider_randomProvidersDefault");
    }

    @Test
    public void testRandomProvidersDefaultSecondaryAndTertiaryScale() {
        Iterable<RandomProvider> rps = EP.randomProvidersDefaultSecondaryAndTertiaryScale();
        take(TINY_LIMIT, rps).forEach(RandomProvider::validate);
        simpleProviderHelper(rps, "ExhaustiveProvider_randomProviderDefaultSecondaryAndTertiaryScale");
    }

    @Test
    public void testRandomProvidersDefaultTertiaryScale() {
        Iterable<RandomProvider> rps = EP.randomProvidersDefaultTertiaryScale();
        take(TINY_LIMIT, rps).forEach(RandomProvider::validate);
        simpleProviderHelper(rps, "ExhaustiveProvider_randomProvidersDefaultTertiaryScale");
    }

    @Test
    public void testRandomProviders() {
        Iterable<RandomProvider> rps = EP.randomProviders();
        take(TINY_LIMIT, rps).forEach(RandomProvider::validate);
        simpleProviderHelper(rps, "ExhaustiveProvider_randomProviders");
    }

    @Test
    public void testEquals() {
        //noinspection EqualsWithItself
        assertTrue(EP.equals(EP));
        //noinspection ObjectEqualsNull
        assertFalse(EP.equals(null));
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(EP.equals("hello"));
    }

    @Test
    public void testHashCode() {
        aeq(EP.hashCode(), 0);
    }

    @Test
    public void testToString() {
        aeq(EP, "ExhaustiveProvider");
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readListStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsLists(@NotNull String s) {
        return Readers.readListStrict(Readers.readListWithNullsStrict(Readers::readIntegerStrict)).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsListsWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers.readListWithNullsStrict(Readers::readIntegerStrict))
                .apply(s).get();
    }

    private static @NotNull List<String> readStringList(@NotNull String s) {
        return Readers.readListStrict(Readers::readStringStrict).apply(s).get();
    }

    private static @NotNull List<String> readStringListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readStringStrict).apply(s).get();
    }
}
// @formatter:on
