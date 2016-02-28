package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.structures.*;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.math.BigInteger;
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

    private static void uniformSample_Iterable_helper_1(@NotNull String xs, @NotNull String output) {
        aeqit(EP.uniformSample(readIntegerList(xs)), output);
    }

    private static void uniformSample_Iterable_helper_2(@NotNull String xs, @NotNull String output) {
        aeqit(EP.uniformSample(readIntegerListWithNulls(xs)), output);
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper_1("[3, 1, 4, 1]", "[3, 1, 4, 1]");
        uniformSample_Iterable_helper_1("[]", "[]");
        uniformSample_Iterable_helper_2("[3, 1, null, 1]", "[3, 1, null, 1]");
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        aeqcs(EP.uniformSample(s), output);
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper("hello", "hello");
        uniformSample_String_helper("", "");
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
        aeqitLimitLog(TINY_LIMIT, reverse(bs), "ExhaustiveProvider_nonzeroBytes_reverse");
        simpleProviderHelper(bs, "ExhaustiveProvider_nonzeroBytes");
    }

    @Test
    public void testNonzeroShorts() {
        Iterable<Short> ss = EP.nonzeroShorts();
        aeq(length(ss), 65535);
        aeqitLimitLog(TINY_LIMIT, reverse(ss), "ExhaustiveProvider_nonzeroShorts_reverse");
        simpleProviderHelper(ss, "ExhaustiveProvider_nonzeroShorts");
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
        aeqitLimitLog(TINY_LIMIT, reverse(bs), "ExhaustiveProvider_bytes_reverse");
        simpleProviderHelper(bs, "ExhaustiveProvider_bytes");
    }

    @Test
    public void testShorts() {
        Iterable<Short> ss = EP.shorts();
        aeq(length(ss), 65536);
        aeqitLimitLog(TINY_LIMIT, reverse(ss), "ExhaustiveProvider_shorts_reverse");
        simpleProviderHelper(ss, "ExhaustiveProvider_shorts");
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
        rangeUp_byte_helper(0, "ExhaustiveProvider_rangeUp_byte_0");
        rangeUp_byte_helper(5, "ExhaustiveProvider_rangeUp_byte_5");
        rangeUp_byte_helper(-5, "ExhaustiveProvider_rangeUp_byte_-5");
        rangeUp_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeUp_byte_MAX_VALUE");
        rangeUp_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeUp_byte_MIN_VALUE");
    }

    private static void rangeUp_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp((short) a), output);
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper(0, "ExhaustiveProvider_rangeUp_short_0");
        rangeUp_short_helper(5, "ExhaustiveProvider_rangeUp_short_5");
        rangeUp_short_helper(-5, "ExhaustiveProvider_rangeUp_short_-5");
        rangeUp_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeUp_short_MAX_VALUE");
        rangeUp_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeUp_short_MIN_VALUE");
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(0, "ExhaustiveProvider_rangeUp_int_0");
        rangeUp_int_helper(5, "ExhaustiveProvider_rangeUp_int_5");
        rangeUp_int_helper(-5, "ExhaustiveProvider_rangeUp_int_-5");
        rangeUp_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeUp_int_MAX_VALUE");
        rangeUp_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeUp_int_MIN_VALUE");
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(0, "ExhaustiveProvider_rangeUp_long_0");
        rangeUp_long_helper(5, "ExhaustiveProvider_rangeUp_long_5");
        rangeUp_long_helper(-5, "ExhaustiveProvider_rangeUp_long_-5");
        rangeUp_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeUp_long_MAX_VALUE");
        rangeUp_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeUp_long_MIN_VALUE");
    }

    private static void rangeUp_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(BigInteger.valueOf(a)), output);
    }

    @Test
    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper(0, "ExhaustiveProvider_rangeUp_BigInteger_0");
        rangeUp_BigInteger_helper(5, "ExhaustiveProvider_rangeUp_BigInteger_5");
        rangeUp_BigInteger_helper(-5, "ExhaustiveProvider_rangeUp_BigInteger_-5");
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_char() {
        rangeUp_char_helper('\0', "ExhaustiveProvider_rangeUp_char_\\u0000");
        rangeUp_char_helper('a', "ExhaustiveProvider_rangeUp_char_a");
        rangeUp_char_helper('Ш', "ExhaustiveProvider_rangeUp_char_\\u0428");
        rangeUp_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeUp_char_MAX_VALUE");
    }

    private static void rangeDown_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown((byte) a), output);
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper(0, "ExhaustiveProvider_rangeDown_byte_0");
        rangeDown_byte_helper(5, "ExhaustiveProvider_rangeDown_byte_5");
        rangeDown_byte_helper(-5,"ExhaustiveProvider_rangeDown_byte_-5");
        rangeDown_byte_helper(Byte.MAX_VALUE, "ExhaustiveProvider_rangeDown_byte_MAX_VALUE");
        rangeDown_byte_helper(Byte.MIN_VALUE, "ExhaustiveProvider_rangeDown_byte_MIN_VALUE");
    }

    private static void rangeDown_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown((short) a), output);
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper(0, "ExhaustiveProvider_rangeDown_short_0");
        rangeDown_short_helper(5, "ExhaustiveProvider_rangeDown_short_5");
        rangeDown_short_helper(-5,"ExhaustiveProvider_rangeDown_short_-5");
        rangeDown_short_helper(Short.MAX_VALUE, "ExhaustiveProvider_rangeDown_short_MAX_VALUE");
        rangeDown_short_helper(Short.MIN_VALUE, "ExhaustiveProvider_rangeDown_short_MIN_VALUE");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(0, "ExhaustiveProvider_rangeDown_int_0");
        rangeDown_int_helper(5, "ExhaustiveProvider_rangeDown_int_5");
        rangeDown_int_helper(-5,"ExhaustiveProvider_rangeDown_int_-5");
        rangeDown_int_helper(Integer.MAX_VALUE, "ExhaustiveProvider_rangeDown_int_MAX_VALUE");
        rangeDown_int_helper(Integer.MIN_VALUE, "ExhaustiveProvider_rangeDown_int_MIN_VALUE");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(0, "ExhaustiveProvider_rangeDown_long_0");
        rangeDown_long_helper(5, "ExhaustiveProvider_rangeDown_long_5");
        rangeDown_long_helper(-5,"ExhaustiveProvider_rangeDown_long_-5");
        rangeDown_long_helper(Long.MAX_VALUE, "ExhaustiveProvider_rangeDown_long_MAX_VALUE");
        rangeDown_long_helper(Long.MIN_VALUE, "ExhaustiveProvider_rangeDown_long_MIN_VALUE");
    }

    private static void rangeDown_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(BigInteger.valueOf(a)), output);
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper(0, "ExhaustiveProvider_rangeDown_BigInteger_0");
        rangeDown_BigInteger_helper(5, "ExhaustiveProvider_rangeDown_BigInteger_5");
        rangeDown_BigInteger_helper(-5, "ExhaustiveProvider_rangeDown_BigInteger_-5");
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_char() {
        rangeDown_char_helper('\0', "ExhaustiveProvider_rangeDown_char_\\u0000");
        rangeDown_char_helper('a', "ExhaustiveProvider_rangeDown_char_a");
        rangeDown_char_helper('Ш', "ExhaustiveProvider_rangeDown_char_\\u0428");
        rangeDown_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeDown_char_MAX_VALUE");
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqit(EP.range((byte) a, (byte) b), output);
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(10, 20, "[10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        range_byte_byte_helper(10, 10, "[10]");
        range_byte_byte_helper(10, 9, "[]");
        range_byte_byte_helper(-20, -10, "[-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
        range_byte_byte_helper(-20, -20, "[-20]");
        range_byte_byte_helper(-20, -21, "[]");
        range_byte_byte_helper(0, 0, "[0]");
        range_byte_byte_helper(0, 10, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        range_byte_byte_helper(-5, 0, "[0, -1, -2, -3, -4, -5]");
        range_byte_byte_helper(-5, 10, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10]");
        range_byte_byte_helper(-10, 5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10]");
        range_byte_byte_helper(5, -10, "[]");
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqit(EP.range((short) a, (short) b), output);
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(10, 20, "[10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        range_short_short_helper(10, 10, "[10]");
        range_short_short_helper(10, 9, "[]");
        range_short_short_helper(-20, -10, "[-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
        range_short_short_helper(-20, -20, "[-20]");
        range_short_short_helper(-20, -21, "[]");
        range_short_short_helper(0, 0, "[0]");
        range_short_short_helper(0, 10, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        range_short_short_helper(-5, 0, "[0, -1, -2, -3, -4, -5]");
        range_short_short_helper(-5, 10, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10]");
        range_short_short_helper(-10, 5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10]");
        range_short_short_helper(5, -10, "[]");
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqit(EP.range(a, b), output);
    }

    @Test
    public void testRange_int_int() {
        range_int_int_helper(10, 20, "[10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        range_int_int_helper(10, 10, "[10]");
        range_int_int_helper(10, 9, "[]");
        range_int_int_helper(-20, -10, "[-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
        range_int_int_helper(-20, -20, "[-20]");
        range_int_int_helper(-20, -21, "[]");
        range_int_int_helper(0, 0, "[0]");
        range_int_int_helper(0, 10, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        range_int_int_helper(-5, 0, "[0, -1, -2, -3, -4, -5]");
        range_int_int_helper(-5, 10, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10]");
        range_int_int_helper(-10, 5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10]");
        range_int_int_helper(5, -10, "[]");
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        aeqit(EP.range(a, b), output);
    }

    @Test
    public void testRange_long_long() {
        range_long_long_helper(10L, 20L, "[10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        range_long_long_helper(10L, 10L, "[10]");
        range_long_long_helper(10L, 9L, "[]");
        range_long_long_helper(-20L, -10L, "[-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
        range_long_long_helper(-20L, -20L, "[-20]");
        range_long_long_helper(-20L, -21L, "[]");
        range_long_long_helper(0L, 0L, "[0]");
        range_long_long_helper(0L, 10L, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        range_long_long_helper(-5L, 0L, "[0, -1, -2, -3, -4, -5]");
        range_long_long_helper(-5L, 10L, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10]");
        range_long_long_helper(-10L, 5L, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10]");
        range_long_long_helper(5L, -10L, "[]");
    }

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        aeqit(EP.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
    }

    @Test
    public void testRange_BigInteger_BigInteger() {
        range_BigInteger_BigInteger_helper(10, 20, "[10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        range_BigInteger_BigInteger_helper(10, 10, "[10]");
        range_BigInteger_BigInteger_helper(10, 9, "[]");
        range_BigInteger_BigInteger_helper(-20, -10, "[-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
        range_BigInteger_BigInteger_helper(-20, -20, "[-20]");
        range_BigInteger_BigInteger_helper(-20, -21, "[]");
        range_BigInteger_BigInteger_helper(0, 0, "[0]");
        range_BigInteger_BigInteger_helper(0, 10, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        range_BigInteger_BigInteger_helper(-5, 0, "[0, -1, -2, -3, -4, -5]");
        range_BigInteger_BigInteger_helper(-5, 10, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10]");
        range_BigInteger_BigInteger_helper(-10, 5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10]");
        range_BigInteger_BigInteger_helper(5, -10, "[]");
    }

    private static void range_char_char_helper(char a, char b, @NotNull String output) {
        aeqcs(EP.range(a, b), output);
    }

    @Test
    public void testRange_char_char() {
        range_char_char_helper('a', 'z', "abcdefghijklmnopqrstuvwxyz");
        range_char_char_helper('a', 'a', "a");
        range_char_char_helper('a', 'A', "");
        range_char_char_helper('!', '9', "!\"#$%&'()*+,-./0123456789");
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
        simpleProviderHelper(EP.rangeUp(BinaryFraction.read(a).get()), output);
    }

    @Test
    public void testRangeUp_BinaryFraction() {
        rangeUp_BinaryFraction_helper("0", "ExhaustiveProvider_rangeUp_BinaryFraction_0");
        rangeUp_BinaryFraction_helper("1", "ExhaustiveProvider_rangeUp_BinaryFraction_1");
        rangeUp_BinaryFraction_helper("11", "ExhaustiveProvider_rangeUp_BinaryFraction_11");
        rangeUp_BinaryFraction_helper("5 << 20", "ExhaustiveProvider_rangeUp_BinaryFraction_5_<<_20");
        rangeUp_BinaryFraction_helper("5 >> 20", "ExhaustiveProvider_rangeUp_BinaryFraction_5_>>_20");
        rangeUp_BinaryFraction_helper("-1", "ExhaustiveProvider_rangeUp_BinaryFraction_-1");
        rangeUp_BinaryFraction_helper("-11", "ExhaustiveProvider_rangeUp_BinaryFraction_-11");
        rangeUp_BinaryFraction_helper("-5 << 20", "ExhaustiveProvider_rangeUp_BinaryFraction_-5_<<_20");
        rangeUp_BinaryFraction_helper("-5 >> 20", "ExhaustiveProvider_rangeUp_BinaryFraction_-5_>>_20");
    }

    private static void rangeDown_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(BinaryFraction.read(a).get()), output);
    }

    @Test
    public void testRangeDown_BinaryFraction() {
        rangeDown_BinaryFraction_helper("0", "ExhaustiveProvider_rangeDown_BinaryFraction_0");
        rangeDown_BinaryFraction_helper("1", "ExhaustiveProvider_rangeDown_BinaryFraction_1");
        rangeDown_BinaryFraction_helper("11", "ExhaustiveProvider_rangeDown_BinaryFraction_11");
        rangeDown_BinaryFraction_helper("5 << 20", "ExhaustiveProvider_rangeDown_BinaryFraction_5_<<_20");
        rangeDown_BinaryFraction_helper("5 >> 20", "ExhaustiveProvider_rangeDown_BinaryFraction_5_>>_20");
        rangeDown_BinaryFraction_helper("-1", "ExhaustiveProvider_rangeDown_BinaryFraction_-1");
        rangeDown_BinaryFraction_helper("-11", "ExhaustiveProvider_rangeDown_BinaryFraction_-11");
        rangeDown_BinaryFraction_helper("-5 << 20", "ExhaustiveProvider_rangeDown_BinaryFraction_-5_<<_20");
        rangeDown_BinaryFraction_helper("-5 >> 20", "ExhaustiveProvider_rangeDown_BinaryFraction_-5_>>_20");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()), output);
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper("0", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_0_1");
        range_BinaryFraction_BinaryFraction_helper("1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_1_11");
        range_BinaryFraction_BinaryFraction_helper("11", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_11_11");
        range_BinaryFraction_BinaryFraction_helper("11", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_11_1");
        range_BinaryFraction_BinaryFraction_helper("-11", "-1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_-11_-1");
        range_BinaryFraction_BinaryFraction_helper("-11", "-11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_-11_-11");
        range_BinaryFraction_BinaryFraction_helper("-1", "-11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_-1_-11");
        range_BinaryFraction_BinaryFraction_helper("0", "0",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_0_0");
        range_BinaryFraction_BinaryFraction_helper("0", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_0_11");
        range_BinaryFraction_BinaryFraction_helper("-1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_-1_11");
        range_BinaryFraction_BinaryFraction_helper("5 >> 20", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_5_>>_20_1");
        range_BinaryFraction_BinaryFraction_helper("1", "5 << 20",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_1_5_<<_20");
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
        rangeUp_float_helper(1.0f, "ExhaustiveProvider_rangeUp_float_1.0f");
        rangeUp_float_helper(1.0E20f, "ExhaustiveProvider_rangeUp_float_1.0E20f");
        rangeUp_float_helper(-1.0f, "ExhaustiveProvider_rangeUp_float_-1.0f");
        rangeUp_float_helper(-1.0E20f, "ExhaustiveProvider_rangeUp_float_-1.0E20f");
        rangeUp_float_helper((float) Math.PI, "ExhaustiveProvider_rangeUp_float_(float)_Math.PI");
        rangeUp_float_helper((float) Math.sqrt(2), "ExhaustiveProvider_rangeUp_float_(float)_Math.sqrt(2)");
        rangeUp_float_helper((float) -Math.PI, "ExhaustiveProvider_rangeUp_float_(float)_-Math.PI");
        rangeUp_float_helper((float) -Math.sqrt(2), "ExhaustiveProvider_rangeUp_float_(float)_-Math.sqrt(2)");
        rangeUp_float_helper(0.0f, "ExhaustiveProvider_rangeUp_float_0.0f");
        rangeUp_float_helper(-0.0f, "ExhaustiveProvider_rangeUp_float_-0.0f");
        rangeUp_float_helper(Float.MIN_VALUE, "ExhaustiveProvider_rangeUp_float_MIN_VALUE");
        rangeUp_float_helper(Float.MIN_NORMAL, "ExhaustiveProvider_rangeUp_float_MIN_NORMAL");
        rangeUp_float_helper(-Float.MIN_VALUE, "ExhaustiveProvider_rangeUp_float_-MIN_VALUE");
        rangeUp_float_helper(-Float.MIN_NORMAL, "ExhaustiveProvider_rangeUp_float_-MIN_NORMAL");
        rangeUp_float_helper(Float.MAX_VALUE, "ExhaustiveProvider_rangeUp_float_MAX_VALUE");
        rangeUp_float_helper(-Float.MAX_VALUE, "ExhaustiveProvider_rangeUp_float_-MAX_VALUE");
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "ExhaustiveProvider_rangeUp_float_POSITIVE_INFINITY");
        rangeUp_float_helper(Float.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeUp_float_NEGATIVE_INFINITY");
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
        rangeDown_float_helper(1.0f, "ExhaustiveProvider_rangeDown_float_1.0f");
        rangeDown_float_helper(1.0E20f, "ExhaustiveProvider_rangeDown_float_1.0E20f");
        rangeDown_float_helper(-1.0f, "ExhaustiveProvider_rangeDown_float_-1.0f");
        rangeDown_float_helper(-1.0E20f, "ExhaustiveProvider_rangeDown_float_-1.0E20f");
        rangeDown_float_helper((float) Math.PI, "ExhaustiveProvider_rangeDown_float_(float)_Math.PI");
        rangeDown_float_helper((float) Math.sqrt(2), "ExhaustiveProvider_rangeDown_float_(float)_Math.sqrt(2)");
        rangeDown_float_helper((float) -Math.PI, "ExhaustiveProvider_rangeDown_float_(float)_-Math.PI");
        rangeDown_float_helper((float) -Math.sqrt(2), "ExhaustiveProvider_rangeDown_float_(float)_-Math.sqrt(2)");
        rangeDown_float_helper(0.0f, "ExhaustiveProvider_rangeDown_float_0.0f");
        rangeDown_float_helper(-0.0f, "ExhaustiveProvider_rangeDown_float_-0.0f");
        rangeDown_float_helper(Float.MIN_VALUE, "ExhaustiveProvider_rangeDown_float_MIN_VALUE");
        rangeDown_float_helper(Float.MIN_NORMAL, "ExhaustiveProvider_rangeDown_float_MIN_NORMAL");
        rangeDown_float_helper(-Float.MIN_VALUE, "ExhaustiveProvider_rangeDown_float_-MIN_VALUE");
        rangeDown_float_helper(-Float.MIN_NORMAL, "ExhaustiveProvider_rangeDown_float_-MIN_NORMAL");
        rangeDown_float_helper(Float.MAX_VALUE, "ExhaustiveProvider_rangeDown_float_MAX_VALUE");
        rangeDown_float_helper(-Float.MAX_VALUE, "ExhaustiveProvider_rangeDown_float_-MAX_VALUE");
        rangeDown_float_helper(Float.POSITIVE_INFINITY, "ExhaustiveProvider_rangeDown_float_POSITIVE_INFINITY");
        rangeDown_float_helper(Float.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeDown_float_NEGATIVE_INFINITY");
        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void range_float_float_helper(float a, float b, @NotNull String output) {
        simpleProviderHelper(EP.range(a, b), output);
    }

    private static void range_float_float_fail_helper(float a, float b) {
        try {
            EP.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_float_float() {
        range_float_float_helper(1.0f, 2.0f, "ExhaustiveProvider_range_float_float_1.0f_2.0f");
        range_float_float_helper(1.0f, 3.0f, "ExhaustiveProvider_range_float_float_1.0f_3.0f");
        range_float_float_helper(1.0f, 4.0f,"ExhaustiveProvider_range_float_float_1.0f_4.0f");
        range_float_float_helper(1.0f, 257.0f, "ExhaustiveProvider_range_float_float_1.0f_257.0f");
        range_float_float_helper(-257.0f, -1.0f, "ExhaustiveProvider_range_float_float_-257.0f_-1.0f");
        range_float_float_helper(1.0f, 1.0E20f, "ExhaustiveProvider_range_float_float_1.0f_1.0E20f");
        range_float_float_helper(-1.0E20f, -1.0f, "ExhaustiveProvider_range_float_float_-1.0E20f_-1.0f");
        range_float_float_helper((float) Math.sqrt(2), (float) Math.PI,
                "ExhaustiveProvider_range_float_float_(float)_Math.sqrt(2)_(float)_Math.PI");
        range_float_float_helper((float) Math.PI, FloatingPointUtils.successor((float) Math.PI),
                "ExhaustiveProvider_range_float_float_(float)_Math.sqrt(2)_successor((float)_Math.PI)");
        range_float_float_helper(0.0f, 1.0f, "ExhaustiveProvider_range_float_float_0.0f_1.0f");
        range_float_float_helper(-1.0f, 1.0f, "ExhaustiveProvider_range_float_float_-1.0f_1.0f");
        range_float_float_helper(1.0f, 1.0f, "ExhaustiveProvider_range_float_float_1.0f_1.0f");
        range_float_float_helper((float) Math.PI, (float) Math.PI,
                "ExhaustiveProvider_range_float_float_(float)_Math.PI_(float)_Math.PI");
        range_float_float_helper((float) -Math.PI, (float) Math.PI,
                "ExhaustiveProvider_range_float_float_(float)_-Math.PI_(float)_Math.PI");
        range_float_float_helper(1.0f, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_1.0f_POSITIVE_INFINITY");
        range_float_float_helper(Float.NEGATIVE_INFINITY, 1.0f,
                "ExhaustiveProvider_range_float_float_NEGATIVE_INFINITY_1.0f");
        range_float_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_MAX_VALUE_POSITIVE_INFINITY");
        range_float_float_helper(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE,
                "ExhaustiveProvider_range_float_float_NEGATIVE_INFINITY_-MAX_VALUE");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_NEGATIVE_INFINITY_POSITIVE_INFINITY");
        range_float_float_helper(0.0f, 0.0f, "ExhaustiveProvider_range_float_float_0.0f_0.0f");
        range_float_float_helper(-0.0f, -0.0f, "ExhaustiveProvider_range_float_float_-0.0f_-0.0f");
        range_float_float_helper(-0.0f, 0.0f, "ExhaustiveProvider_range_float_float_-0.0f_0.0f");
        range_float_float_helper(0.0f, -0.0f, "ExhaustiveProvider_range_float_float_0.0f_-0.0f");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_POSITIVE_INFINITY_POSITIVE_INFINITY");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_NEGATIVE_INFINITY_NEGATIVE_INFINITY");
        range_float_float_helper(1.0f, -1.0f, "ExhaustiveProvider_range_float_float_1.0f_-1.0f");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_POSITIVE_INFINITY_NEGATIVE_INFINITY");
        range_float_float_helper(Float.POSITIVE_INFINITY, 1.0f,
                "ExhaustiveProvider_range_float_float_POSITIVE_INFINITY_1.0f");
        range_float_float_helper(1.0f, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_1.0f_NEGATIVE_INFINITY");
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
        rangeUp_double_helper(1.0, "ExhaustiveProvider_rangeUp_double_1.0");
        rangeUp_double_helper(1.0E20, "ExhaustiveProvider_rangeUp_double_1.0E20");
        rangeUp_double_helper(-1.0, "ExhaustiveProvider_rangeUp_double_-1.0");
        rangeUp_double_helper(-1.0E20, "ExhaustiveProvider_rangeUp_double_-1.0E20");
        rangeUp_double_helper(Math.PI, "ExhaustiveProvider_rangeUp_double_Math.PI");
        rangeUp_double_helper(Math.sqrt(2), "ExhaustiveProvider_rangeUp_double_Math.sqrt(2)");
        rangeUp_double_helper(-Math.PI, "ExhaustiveProvider_rangeUp_double_-Math.PI");
        rangeUp_double_helper(-Math.sqrt(2), "ExhaustiveProvider_rangeUp_double_-Math.sqrt(2)");
        rangeUp_double_helper(0.0, "ExhaustiveProvider_rangeUp_double_0.0");
        rangeUp_double_helper(-0.0, "ExhaustiveProvider_rangeUp_double_-0.0");
        rangeUp_double_helper(Double.MIN_VALUE, "ExhaustiveProvider_rangeUp_double_MIN_VALUE");
        rangeUp_double_helper(Double.MIN_NORMAL, "ExhaustiveProvider_rangeUp_double_MIN_NORMAL");
        rangeUp_double_helper(-Double.MIN_VALUE, "ExhaustiveProvider_rangeUp_double_-MIN_VALUE");
        rangeUp_double_helper(-Double.MIN_NORMAL, "ExhaustiveProvider_rangeUp_double_-MIN_NORMAL");
        rangeUp_double_helper(Double.MAX_VALUE, "ExhaustiveProvider_rangeUp_double_MAX_VALUE");
        rangeUp_double_helper(-Double.MAX_VALUE, "ExhaustiveProvider_rangeUp_double_-MAX_VALUE");
        rangeUp_double_helper(Double.POSITIVE_INFINITY, "ExhaustiveProvider_rangeUp_double_POSITIVE_INFINITY");
        rangeUp_double_helper(Double.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeUp_double_NEGATIVE_INFINITY");
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
        rangeDown_double_helper(1.0, "ExhaustiveProvider_rangeDown_double_1.0");
        rangeDown_double_helper(1.0E20, "ExhaustiveProvider_rangeDown_double_1.0E20");
        rangeDown_double_helper(-1.0, "ExhaustiveProvider_rangeDown_double_-1.0");
        rangeDown_double_helper(-1.0E20, "ExhaustiveProvider_rangeDown_double_-1.0E20");
        rangeDown_double_helper(Math.PI, "ExhaustiveProvider_rangeDown_double_Math.PI");
        rangeDown_double_helper(Math.sqrt(2), "ExhaustiveProvider_rangeDown_double_Math.sqrt(2)");
        rangeDown_double_helper(-Math.PI, "ExhaustiveProvider_rangeDown_double_-Math.PI");
        rangeDown_double_helper(-Math.sqrt(2), "ExhaustiveProvider_rangeDown_double_-Math.sqrt(2)");
        rangeDown_double_helper(0.0, "ExhaustiveProvider_rangeDown_double_0.0");
        rangeDown_double_helper(-0.0, "ExhaustiveProvider_rangeDown_double_-0.0");
        rangeDown_double_helper(Double.MIN_VALUE, "ExhaustiveProvider_rangeDown_double_MIN_VALUE");
        rangeDown_double_helper(Double.MIN_NORMAL, "ExhaustiveProvider_rangeDown_double_MIN_NORMAL");
        rangeDown_double_helper(-Double.MIN_VALUE, "ExhaustiveProvider_rangeDown_double_-MIN_VALUE");
        rangeDown_double_helper(-Double.MIN_NORMAL, "ExhaustiveProvider_rangeDown_double_-MIN_NORMAL");
        rangeDown_double_helper(Double.MAX_VALUE, "ExhaustiveProvider_rangeDown_double_MAX_VALUE");
        rangeDown_double_helper(-Double.MAX_VALUE, "ExhaustiveProvider_rangeDown_double_-MAX_VALUE");
        rangeDown_double_helper(Double.POSITIVE_INFINITY, "ExhaustiveProvider_rangeDown_double_POSITIVE_INFINITY");
        rangeDown_double_helper(Double.NEGATIVE_INFINITY, "ExhaustiveProvider_rangeDown_double_NEGATIVE_INFINITY");
        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void range_double_double_helper(double a, double b, @NotNull String output) {
        simpleProviderHelper(EP.range(a, b), output);
    }

    private static void range_double_double_fail_helper(double a, double b) {
        try {
            EP.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_double_double() {
        range_double_double_helper(1.0, 2.0, "ExhaustiveProvider_range_double_double_1.0_2.0");
        range_double_double_helper(1.0, 3.0, "ExhaustiveProvider_range_double_double_1.0_3.0");
        range_double_double_helper(1.0, 4.0,"ExhaustiveProvider_range_double_double_1.0_4.0");
        range_double_double_helper(1.0, 257.0, "ExhaustiveProvider_range_double_double_1.0_257.0");
        range_double_double_helper(-257.0, -1.0, "ExhaustiveProvider_range_double_double_-257.0_-1.0");
        range_double_double_helper(1.0, 1.0E20, "ExhaustiveProvider_range_double_double_1.0_1.0E20");
        range_double_double_helper(-1.0E20, -1.0, "ExhaustiveProvider_range_double_double_-1.0E20_-1.0");
        range_double_double_helper(Math.sqrt(2), Math.PI,
                "ExhaustiveProvider_range_double_double_Math.sqrt(2)_Math.PI");
        range_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI),
                "ExhaustiveProvider_range_double_double_Math.sqrt(2)_successor(Math.PI)");
        range_double_double_helper(0.0, 1.0, "ExhaustiveProvider_range_double_double_0.0_1.0");
        range_double_double_helper(-1.0, 1.0, "ExhaustiveProvider_range_double_double_-1.0_1.0");
        range_double_double_helper(1.0, 1.0, "ExhaustiveProvider_range_double_double_1.0_1.0");
        range_double_double_helper(Math.PI, Math.PI, "ExhaustiveProvider_range_double_double_Math.PI_Math.PI");
        range_double_double_helper(-Math.PI, Math.PI, "ExhaustiveProvider_range_double_double_-Math.PI_Math.PI");
        range_double_double_helper(1.0, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_1.0_POSITIVE_INFINITY");
        range_double_double_helper(Double.NEGATIVE_INFINITY, 1.0,
                "ExhaustiveProvider_range_double_double_NEGATIVE_INFINITY_1.0f");
        range_double_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_MAX_VALUE_POSITIVE_INFINITY");
        range_double_double_helper(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE,
                "ExhaustiveProvider_range_double_double_NEGATIVE_INFINITY_-MAX_VALUE");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_NEGATIVE_INFINITY_POSITIVE_INFINITY");
        range_double_double_helper(0.0, 0.0, "ExhaustiveProvider_range_double_double_0.0_0.0");
        range_double_double_helper(-0.0, -0.0, "ExhaustiveProvider_range_double_double_-0.0_-0.0");
        range_double_double_helper(-0.0, 0.0, "ExhaustiveProvider_range_double_double_-0.0_0.0");
        range_double_double_helper(0.0, -0.0, "ExhaustiveProvider_range_double_double_0.0_-0.0");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_POSITIVE_INFINITY_POSITIVE_INFINITY");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_NEGATIVE_INFINITY_NEGATIVE_INFINITY");
        range_double_double_helper(1.0f, -1.0f, "ExhaustiveProvider_range_double_double_1.0_-1.0");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_POSITIVE_INFINITY_NEGATIVE_INFINITY");
        range_double_double_helper(Double.POSITIVE_INFINITY, 1.0f,
                "ExhaustiveProvider_range_double_double_POSITIVE_INFINITY_1.0");
        range_double_double_helper(1.0f, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_1.0_NEGATIVE_INFINITY");
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
        simpleProviderHelper(EP.rangeUp(Readers.readBigDecimal(a).get()), output);
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
        simpleProviderHelper(EP.rangeDown(Readers.readBigDecimal(a).get()), output);
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
        simpleProviderHelper(EP.range(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()), output);
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
        range_BigDecimal_BigDecimal_helper("5", "3", "ExhaustiveProvider_range_BigDecimal_BigDecimal_xxi");
    }

    private static void rangeUpCanonical_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUpCanonical(Readers.readBigDecimal(a).get()), output);
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
        simpleProviderHelper(EP.rangeDownCanonical(Readers.readBigDecimal(a).get()), output);
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
                EP.rangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output
        );
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
        rangeCanonical_BigDecimal_BigDecimal_helper("5", "3",
                "ExhaustiveProvider_rangeCanonical_BigDecimal_BigDecimal_xxi");
    }

    private static void withNull_finite_helper(@NotNull String x, @NotNull String output) {
        aeqit(EP.withNull(readIntegerListWithNulls(x)), output);
    }

    private static void withNull_cyclic_helper(@NotNull String x, @NotNull String output) {
        simpleProviderHelper(EP.withNull(cycle(readIntegerListWithNulls(x))), output);
    }

    @Test
    public void testWithNull() {
        withNull_finite_helper("[]", "[null]");
        withNull_finite_helper("[1, 2, 3]", "[null, 1, 2, 3]");
        withNull_finite_helper("[1, null, 3]", "[null, 1, null, 3]");
        withNull_cyclic_helper("[1, 2, 3]", "[null, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, ...]");
        withNull_cyclic_helper("[1, null, 3]",
                "[null, 1, null, 3, 1, null, 3, 1, null, 3, 1, null, 3, 1, null, 3, 1, null, 3, 1, ...]");
    }

    @Test
    public void testNonEmptyOptionals() {
        simpleProviderHelper(EP.nonEmptyOptionals(EP.integers()),
                "[Optional[0], Optional[1], Optional[-1], Optional[2], Optional[-2], Optional[3], Optional[-3]," +
                " Optional[4], Optional[-4], Optional[5], Optional[-5], Optional[6], Optional[-6], Optional[7]," +
                " Optional[-7], Optional[8], Optional[-8], Optional[9], Optional[-9], Optional[10], ...]");
        simpleProviderHelper(EP.nonEmptyOptionals(EP.strings()),
                "[Optional[], Optional[a], Optional[aa], Optional[b], Optional[aaa], Optional[c], Optional[ab]," +
                " Optional[d], Optional[aaaa], Optional[e], Optional[ba], Optional[f], Optional[aab], Optional[g]," +
                " Optional[bb], Optional[h], Optional[aaaaa], Optional[i], Optional[ac], Optional[j], ...]");
        aeqit(EP.nonEmptyOptionals(Arrays.asList(1, 2, 3)), "[Optional[1], Optional[2], Optional[3]]");
        aeqit(EP.nonEmptyOptionals(Collections.emptyList()), "[]");
        try {
            toList(take(TINY_LIMIT, EP.nonEmptyOptionals(EP.withNull(EP.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testOptionals() {
        simpleProviderHelper(EP.optionals(EP.integers()),
                "[Optional.empty, Optional[0], Optional[1], Optional[-1], Optional[2], Optional[-2], Optional[3]," +
                " Optional[-3], Optional[4], Optional[-4], Optional[5], Optional[-5], Optional[6], Optional[-6]," +
                " Optional[7], Optional[-7], Optional[8], Optional[-8], Optional[9], Optional[-9], ...]");
        simpleProviderHelper(EP.optionals(EP.strings()),
                "[Optional.empty, Optional[], Optional[a], Optional[aa], Optional[b], Optional[aaa], Optional[c]," +
                " Optional[ab], Optional[d], Optional[aaaa], Optional[e], Optional[ba], Optional[f], Optional[aab]," +
                " Optional[g], Optional[bb], Optional[h], Optional[aaaaa], Optional[i], Optional[ac], ...]");
        aeqit(EP.optionals(Arrays.asList(1, 2, 3)), "[Optional.empty, Optional[1], Optional[2], Optional[3]]");
        aeqit(EP.optionals(Collections.emptyList()), "[Optional.empty]");
        try {
            toList(take(TINY_LIMIT, EP.optionals(EP.withNull(EP.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testNonEmptyNullableOptionals() {
        simpleProviderHelper(EP.nonEmptyNullableOptionals(EP.withNull(EP.integers())),
                "[NullableOptional[null], NullableOptional[0], NullableOptional[1], NullableOptional[-1]," +
                " NullableOptional[2], NullableOptional[-2], NullableOptional[3], NullableOptional[-3]," +
                " NullableOptional[4], NullableOptional[-4], NullableOptional[5], NullableOptional[-5]," +
                " NullableOptional[6], NullableOptional[-6], NullableOptional[7], NullableOptional[-7]," +
                " NullableOptional[8], NullableOptional[-8], NullableOptional[9], NullableOptional[-9], ...]");
        simpleProviderHelper(EP.nonEmptyNullableOptionals(EP.withNull(EP.strings())),
                "[NullableOptional[null], NullableOptional[], NullableOptional[a], NullableOptional[aa]," +
                " NullableOptional[b], NullableOptional[aaa], NullableOptional[c], NullableOptional[ab]," +
                " NullableOptional[d], NullableOptional[aaaa], NullableOptional[e], NullableOptional[ba]," +
                " NullableOptional[f], NullableOptional[aab], NullableOptional[g], NullableOptional[bb]," +
                " NullableOptional[h], NullableOptional[aaaaa], NullableOptional[i], NullableOptional[ac], ...]");
        aeqit(EP.nonEmptyNullableOptionals(Arrays.asList(1, 2, 3)),
                "[NullableOptional[1], NullableOptional[2], NullableOptional[3]]");
        aeqit(EP.nonEmptyNullableOptionals(Collections.emptyList()), "[]");
    }

    @Test
    public void testNullableOptionals() {
        simpleProviderHelper(EP.nullableOptionals(EP.withNull(EP.integers())),
                "[NullableOptional.empty, NullableOptional[null], NullableOptional[0], NullableOptional[1]," +
                " NullableOptional[-1], NullableOptional[2], NullableOptional[-2], NullableOptional[3]," +
                " NullableOptional[-3], NullableOptional[4], NullableOptional[-4], NullableOptional[5]," +
                " NullableOptional[-5], NullableOptional[6], NullableOptional[-6], NullableOptional[7]," +
                " NullableOptional[-7], NullableOptional[8], NullableOptional[-8], NullableOptional[9], ...]");
        simpleProviderHelper(EP.nullableOptionals(EP.withNull(EP.strings())),
                "[NullableOptional.empty, NullableOptional[null], NullableOptional[], NullableOptional[a]," +
                " NullableOptional[aa], NullableOptional[b], NullableOptional[aaa], NullableOptional[c]," +
                " NullableOptional[ab], NullableOptional[d], NullableOptional[aaaa], NullableOptional[e]," +
                " NullableOptional[ba], NullableOptional[f], NullableOptional[aab], NullableOptional[g]," +
                " NullableOptional[bb], NullableOptional[h], NullableOptional[aaaaa], NullableOptional[i], ...]");
        aeqit(EP.nullableOptionals(Arrays.asList(1, 2, 3)),
                "[NullableOptional.empty, NullableOptional[1], NullableOptional[2], NullableOptional[3]]");
        aeqit(EP.nullableOptionals(Collections.emptyList()), "[NullableOptional.empty]");
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
        simpleProviderHelper(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), f),
                "[(3, foo), (2, a), (2, b), (2, c), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep)," +
                " (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep)," +
                " (0, beep), (0, beep), ...]");

        aeqit(EP.dependentPairs(Collections.emptyList(), f), "[]");

        simpleProviderHelper(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> Collections.emptyList()), "[]");

        try {
            toList(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testDependentPairsInfinite() {
        simpleProviderHelper(EP.dependentPairsInfinite(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (0, 4), (0, 5), (1, 4), (1, 5), ...]");

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b)," +
                " (0, beep), (0, beep), (1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b), (0, beep)," +
                " (0, beep), ...]");

        try {
            toList(EP.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(EP.dependentPairsInfinite(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(EP.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), i -> Collections.singletonList("a")));
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfiniteLogarithmicOrder() {
        simpleProviderHelper(
                EP.dependentPairsInfiniteLogarithmicOrder(
                        EP.naturalBigIntegers(),
                        i -> EP.naturalBigIntegers()
                ),
                "[(0, 0), (1, 0), (0, 1), (2, 0), (0, 2), (1, 1), (0, 3), (3, 0), (0, 4), (1, 2), (0, 5), (2, 1)," +
                " (0, 6), (1, 3), (0, 7), (4, 0), (0, 8), (1, 4), (0, 9), (2, 2), ...]"
        );

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfiniteLogarithmicOrder(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (0, beep), (1, b), (1, a), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep)," +
                " (1, a), (1, b), (1, a), (0, beep), (1, b), (1, a), (1, b), (0, beep), (1, a), (1, b), ...]");

        try {
            toList(EP.dependentPairsInfiniteLogarithmicOrder(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(EP.dependentPairsInfiniteLogarithmicOrder(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    EP.dependentPairsInfiniteLogarithmicOrder(
                            cycle(Arrays.asList(1, 0)),
                            i -> Collections.singletonList("a")
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfiniteSquareRootOrder() {
        simpleProviderHelper(
                EP.dependentPairsInfiniteSquareRootOrder(
                        EP.naturalBigIntegers(),
                        i -> EP.naturalBigIntegers()
                ),
                "[(0, 0), (1, 0), (0, 1), (1, 1), (0, 2), (1, 2), (0, 3), (1, 3), (2, 0), (3, 0), (2, 1), (3, 1)," +
                " (2, 2), (3, 2), (2, 3), (3, 3), (0, 4), (1, 4), (0, 5), (1, 5), ...]"
        );

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfiniteSquareRootOrder(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep)," +
                " (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep)," +
                " ...]");

        try {
            toList(EP.dependentPairsInfiniteSquareRootOrder(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(EP.dependentPairsInfiniteSquareRootOrder(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    EP.dependentPairsInfiniteSquareRootOrder(
                            cycle(Arrays.asList(1, 0)),
                            i -> Collections.singletonList("a")
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable_Iterable() {
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (3, a), (2, b), (4, a), (1, d), (3, b), (2, c), (4, b), (3, c)," +
                " (2, d), (4, c), (3, d), (4, d)]"
        );
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (2, a), (2, b), (4, a), (1, d), (2, b), (2, c), (4, b), (2, c)," +
                " (2, d), (4, c), (2, d), (4, d)]"
        );
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (null, a), (2, b), (4, a), (1, d), (null, b), (2, c), (4, b)," +
                " (null, c), (2, d), (4, c), (null, d), (4, d)]"
        );
        aeqit(EP.pairsLogarithmicOrder(Collections.emptyList(), fromString("abcd")), "[]");
        aeqit(EP.pairsLogarithmicOrder(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (0, c), (2, a), (1, b), (3, a), (0, d), (4, a), (2, b), (5, a), (1, c)," +
                " (6, a), (3, b), (7, a), (8, a), (4, b), (9, a), (2, c), (10, a), ...]"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (a, 2), (c, 0), (b, 1), (d, 0), (a, 3), (c, 1), (b, 2), (d, 1), (a, 4)," +
                " (c, 2), (b, 3), (d, 2), (a, 5), (c, 3), (b, 4), (d, 3), (a, 6), ...]"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (1, -3), (3, -1), (2, -2), (4, -1), (1, -4), (5, -1), (3, -2), (6, -1)," +
                " (2, -3), (7, -1), (4, -2), (8, -1), (1, -5), (9, -1), (5, -2), (10, -1), (3, -3), ...]"
        );
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 3), (3, 1), (2, 2), (4, 1), (1, 4), (3, 2), (2, 3), (4, 2), (3, 3)," +
                " (2, 4), (4, 3), (3, 4), (4, 4)]"
        );
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 2), (2, 1), (2, 2), (4, 1), (1, 4), (2, 2), (2, 2), (4, 2), (2, 2)," +
                " (2, 4), (4, 2), (2, 4), (4, 4)]"
        );
        aeqit(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, null), (null, 1), (2, 2), (4, 1), (1, 4), (null, 2), (2, null)," +
                " (4, 2), (null, null), (2, 4), (4, null), (null, 4), (4, 4)]"
        );
        aeqit(EP.pairsLogarithmicOrder(Collections.emptyList()), "[]");
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (0, 2), (2, 0), (1, 1), (3, 0), (0, 3), (4, 0), (2, 1), (5, 0), (1, 2)," +
                " (6, 0), (3, 1), (7, 0), (0, 4), (8, 0), (4, 1), (9, 0), (2, 2), ...]"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(cons(null, EP.naturalBigIntegers())),
                "[(null, null), (null, 0), (0, null), (null, 1), (1, null), (0, 0), (2, null), (null, 2), (3, null)," +
                " (1, 0), (4, null), (0, 1), (5, null), (2, 0), (6, null), (null, 3), (7, null), (3, 0), (8, null)," +
                " (1, 1), ...]"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable_Iterable() {
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (4, a), (4, b), (1, c), (1, d), (2, c), (2, d)," +
                " (3, c), (3, d), (4, c), (4, d)]"
        );
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (2, a), (2, b), (4, a), (4, b), (1, c), (1, d), (2, c), (2, d)," +
                " (2, c), (2, d), (4, c), (4, d)]"
        );
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (null, a), (null, b), (4, a), (4, b), (1, c), (1, d), (2, c)," +
                " (2, d), (null, c), (null, d), (4, c), (4, d)]"
        );
        aeqit(EP.pairsSquareRootOrder(Collections.emptyList(), fromString("abcd")), "[]");
        aeqit(EP.pairsSquareRootOrder(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (0, c), (0, d), (1, c), (1, d)," +
                " (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b), ...]"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (c, 0), (c, 1), (d, 0), (d, 1), (a, 2), (a, 3), (b, 2), (b, 3)," +
                " (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5), ...]"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (3, -1), (3, -2), (4, -1), (4, -2), (1, -3), (1, -4), (2, -3)," +
                " (2, -4), (3, -3), (3, -4), (4, -3), (4, -4), (5, -1), (5, -2), (6, -1), (6, -2), ...]"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable() {
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2), (4, 1), (4, 2), (1, 3), (1, 4), (2, 3), (2, 4)," +
                " (3, 3), (3, 4), (4, 3), (4, 4)]"
        );
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (2, 1), (2, 2), (4, 1), (4, 2), (1, 2), (1, 4), (2, 2), (2, 4)," +
                " (2, 2), (2, 4), (4, 2), (4, 4)]"
        );
        aeqit(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (null, 1), (null, 2), (4, 1), (4, 2), (1, null), (1, 4)," +
                " (2, null), (2, 4), (null, null), (null, 4), (4, null), (4, 4)]"
        );
        aeqit(EP.pairsSquareRootOrder(Collections.emptyList()), "[]");
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (2, 0), (2, 1), (3, 0), (3, 1), (0, 2), (0, 3), (1, 2), (1, 3)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (4, 0), (4, 1), (5, 0), (5, 1), ...]"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(cons(null, EP.naturalBigIntegers())),
                "[(null, null), (null, 0), (0, null), (0, 0), (1, null), (1, 0), (2, null), (2, 0), (null, 1)," +
                " (null, 2), (0, 1), (0, 2), (1, 1), (1, 2), (2, 1), (2, 2), (3, null), (3, 0), (4, null), (4, 0)," +
                " ...]"
        );
    }

    private static void permutationsFiniteHelper(@NotNull String input, @NotNull String output) {
        aeqit(map(Testing::its, EP.permutationsFinite(readIntegerListWithNulls(input))), output);
    }

    private static void permutationsFiniteHelper(@NotNull List<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.permutationsFinite(input)), output);
    }

    @Test
    public void testPermutationsFinite() {
        permutationsFiniteHelper("[]", "[[]]");
        permutationsFiniteHelper("[5]", "[[5]]");
        permutationsFiniteHelper("[1, 2]", "[[1, 2], [2, 1]]");
        permutationsFiniteHelper("[1, 2, 3]", "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        permutationsFiniteHelper("[1, 2, 3, 4]",
                "[[1, 2, 3, 4], [1, 2, 4, 3], [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4]," +
                " [2, 1, 4, 3], [2, 3, 1, 4], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2]," +
                " [3, 2, 1, 4], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3]," +
                " [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]");
        permutationsFiniteHelper("[1, 2, 2, 4]",
                "[[1, 2, 2, 4], [1, 2, 4, 2], [1, 4, 2, 2], [2, 1, 2, 4], [2, 1, 4, 2], [2, 2, 1, 4], [2, 2, 4, 1]," +
                " [2, 4, 1, 2], [2, 4, 2, 1], [4, 1, 2, 2], [4, 2, 1, 2], [4, 2, 2, 1]]");
        permutationsFiniteHelper("[2, 2, 2, 2]", "[[2, 2, 2, 2]]");
        permutationsFiniteHelper("[3, 1, 4, 1]",
                "[[3, 1, 1, 4], [3, 1, 4, 1], [3, 4, 1, 1], [1, 3, 1, 4], [1, 3, 4, 1], [1, 1, 3, 4], [1, 1, 4, 3]," +
                " [1, 4, 3, 1], [1, 4, 1, 3], [4, 3, 1, 1], [4, 1, 3, 1], [4, 1, 1, 3]]");
        permutationsFiniteHelper("[3, 1, null, 1]",
                "[[3, 1, 1, null], [3, 1, null, 1], [3, null, 1, 1], [1, 3, 1, null], [1, 3, null, 1]," +
                " [1, 1, 3, null], [1, 1, null, 3], [1, null, 3, 1], [1, null, 1, 3], [null, 3, 1, 1]," +
                " [null, 1, 3, 1], [null, 1, 1, 3]]");
        permutationsFiniteHelper(toList(IterableUtils.range(1, 10)),
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 10, 9], [1, 2, 3, 4, 5, 6, 7, 9, 8, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 9, 10, 8], [1, 2, 3, 4, 5, 6, 7, 10, 8, 9], [1, 2, 3, 4, 5, 6, 7, 10, 9, 8]," +
                " [1, 2, 3, 4, 5, 6, 8, 7, 9, 10], [1, 2, 3, 4, 5, 6, 8, 7, 10, 9], [1, 2, 3, 4, 5, 6, 8, 9, 7, 10]," +
                " [1, 2, 3, 4, 5, 6, 8, 9, 10, 7], [1, 2, 3, 4, 5, 6, 8, 10, 7, 9], [1, 2, 3, 4, 5, 6, 8, 10, 9, 7]," +
                " [1, 2, 3, 4, 5, 6, 9, 7, 8, 10], [1, 2, 3, 4, 5, 6, 9, 7, 10, 8], [1, 2, 3, 4, 5, 6, 9, 8, 7, 10]," +
                " [1, 2, 3, 4, 5, 6, 9, 8, 10, 7], [1, 2, 3, 4, 5, 6, 9, 10, 7, 8], [1, 2, 3, 4, 5, 6, 9, 10, 8, 7]," +
                " [1, 2, 3, 4, 5, 6, 10, 7, 8, 9], [1, 2, 3, 4, 5, 6, 10, 7, 9, 8], ...]");
    }

    @Test
    public void testStringPermutations() {
        aeqit(EP.stringPermutations(""), "[]");
        aeqit(EP.stringPermutations("a"), "[a]");
        aeqit(EP.stringPermutations("abc"), "[abc, acb, bac, bca, cab, cba]");
        aeqit(EP.stringPermutations("foo"), "[foo, ofo, oof]");
        aeqit(EP.stringPermutations("hello"),
                "[hello, helol, heoll, hlelo, hleol, hlleo, hlloe, hloel, hlole, hoell, holel, holle, ehllo, ehlol," +
                " eholl, elhlo, elhol, ellho, elloh, elohl, elolh, eohll, eolhl, eollh, lhelo, lheol, lhleo, lhloe," +
                " lhoel, lhole, lehlo, lehol, lelho, leloh, leohl, leolh, llheo, llhoe, lleho, lleoh, llohe, lloeh," +
                " lohel, lohle, loehl, loelh, lolhe, loleh, ohell, ohlel, ohlle, oehll, oelhl, oellh, olhel, olhle," +
                " olehl, olelh, ollhe, olleh]");
        simpleProviderHelper(EP.stringPermutations("Mississippi"),
                "[Miiiisssspp, Miiiissspsp, Miiiissspps, Miiiisspssp, Miiiisspsps, Miiiissppss, Miiiispsssp," +
                " Miiiispssps, Miiiispspss, Miiiisppsss, Miiiipssssp, Miiiipsssps, Miiiipsspss, Miiiipspsss," +
                " Miiiippssss, Miiisissspp, Miiisisspsp, Miiisisspps, Miiisispssp, Miiisispsps, ...]");
    }

    private static void prefixPermutationsHelper(@NotNull String input, @NotNull String output) {
        aeqit(map(Testing::its, EP.prefixPermutations(readIntegerList(input))), output);
    }

    private static void prefixPermutationsHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        aeqit(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    private static void prefixPermutationsLimitHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    @Test
    public void testPrefixPermutations() {
        prefixPermutationsHelper("[]", "[[]]");
        prefixPermutationsHelper("[5]", "[[5]]");
        prefixPermutationsHelper("[1, 2]", "[[1, 2], [2, 1]]");
        prefixPermutationsHelper("[1, 2, 3]", "[[1, 2, 3], [2, 1, 3], [1, 3, 2], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        prefixPermutationsHelper("[1, 2, 3, 4]",
                "[[1, 2, 3, 4], [2, 1, 3, 4], [1, 3, 2, 4], [2, 3, 1, 4], [3, 1, 2, 4], [3, 2, 1, 4], [1, 2, 4, 3]," +
                " [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 4, 3], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1]," +
                " [3, 1, 4, 2], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3]," +
                " [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]");
        prefixPermutationsHelper("[1, 2, 2, 4]",
                "[[1, 2, 2, 4], [2, 1, 2, 4], [1, 2, 2, 4], [2, 2, 1, 4], [2, 1, 2, 4], [2, 2, 1, 4], [1, 2, 4, 2]," +
                " [1, 2, 4, 2], [1, 4, 2, 2], [1, 4, 2, 2], [2, 1, 4, 2], [2, 2, 4, 1], [2, 4, 1, 2], [2, 4, 2, 1]," +
                " [2, 1, 4, 2], [2, 2, 4, 1], [2, 4, 1, 2], [2, 4, 2, 1], [4, 1, 2, 2], [4, 1, 2, 2], [4, 2, 1, 2]," +
                " [4, 2, 2, 1], [4, 2, 1, 2], [4, 2, 2, 1]]");
        prefixPermutationsHelper("[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]]");
        prefixPermutationsHelper("[3, 1, 4, 1]",
                "[[3, 1, 4, 1], [1, 3, 4, 1], [3, 4, 1, 1], [1, 4, 3, 1], [4, 3, 1, 1], [4, 1, 3, 1], [3, 1, 1, 4]," +
                " [3, 4, 1, 1], [3, 1, 1, 4], [3, 1, 4, 1], [1, 3, 1, 4], [1, 4, 1, 3], [1, 1, 3, 4], [1, 1, 4, 3]," +
                " [4, 3, 1, 1], [4, 1, 1, 3], [4, 1, 3, 1], [4, 1, 1, 3], [1, 3, 1, 4], [1, 3, 4, 1], [1, 1, 3, 4]," +
                " [1, 1, 4, 3], [1, 4, 3, 1], [1, 4, 1, 3]]");
        prefixPermutationsHelper(Arrays.asList(3, 1, null, 1),
                "[[3, 1, null, 1], [1, 3, null, 1], [3, null, 1, 1], [1, null, 3, 1], [null, 3, 1, 1]," +
                " [null, 1, 3, 1], [3, 1, 1, null], [3, null, 1, 1], [3, 1, 1, null], [3, 1, null, 1]," +
                " [1, 3, 1, null], [1, null, 1, 3], [1, 1, 3, null], [1, 1, null, 3], [null, 3, 1, 1]," +
                " [null, 1, 1, 3], [null, 1, 3, 1], [null, 1, 1, 3], [1, 3, 1, null], [1, 3, null, 1]," +
                " [1, 1, 3, null], [1, 1, null, 3], [1, null, 3, 1], [1, null, 1, 3]]");
        prefixPermutationsLimitHelper(IterableUtils.range(1, 10),
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [2, 1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 3, 2, 4, 5, 6, 7, 8, 9, 10]," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10], [3, 1, 2, 4, 5, 6, 7, 8, 9, 10], [3, 2, 1, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 4, 3, 5, 6, 7, 8, 9, 10], [1, 3, 4, 2, 5, 6, 7, 8, 9, 10], [1, 4, 2, 3, 5, 6, 7, 8, 9, 10]," +
                " [1, 4, 3, 2, 5, 6, 7, 8, 9, 10], [2, 1, 4, 3, 5, 6, 7, 8, 9, 10], [2, 3, 4, 1, 5, 6, 7, 8, 9, 10]," +
                " [2, 4, 1, 3, 5, 6, 7, 8, 9, 10], [2, 4, 3, 1, 5, 6, 7, 8, 9, 10], [3, 1, 4, 2, 5, 6, 7, 8, 9, 10]," +
                " [3, 2, 4, 1, 5, 6, 7, 8, 9, 10], [3, 4, 1, 2, 5, 6, 7, 8, 9, 10], [3, 4, 2, 1, 5, 6, 7, 8, 9, 10]," +
                " [4, 1, 2, 3, 5, 6, 7, 8, 9, 10], [4, 1, 3, 2, 5, 6, 7, 8, 9, 10], ...]");
        prefixPermutationsLimitHelper(EP.positiveIntegers(),
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 3, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 4, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 4, 3, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 3, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 4, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 4, 3, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 2, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 4, 2, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 1, 3, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]");
        prefixPermutationsLimitHelper(repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]");
    }

    private static void listsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.listsLex(size, readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testListsLex_int_List() {
        listsLex_int_List_helper(0, "[]", "[[]]");
        listsLex_int_List_helper(0, "[5]", "[[]]");
        listsLex_int_List_helper(1, "[5]", "[[5]]");
        listsLex_int_List_helper(2, "[5]", "[[5, 5]]");
        listsLex_int_List_helper(3, "[5]", "[[5, 5, 5]]");
        listsLex_int_List_helper(0, "[1, 2, 3]", "[[]]");
        listsLex_int_List_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        listsLex_int_List_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]");
        listsLex_int_List_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3]," +
                " [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 3]," +
                " [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 3]]");
        listsLex_int_List_helper(0, "[1, 2, 2, 3]", "[[]]");
        listsLex_int_List_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        listsLex_int_List_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 2], [2, 3], [2, 1], [2, 2], [2, 2], [2, 3]," +
                " [3, 1], [3, 2], [3, 2], [3, 3]]");
        listsLex_int_List_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 2], [1, 2, 3], [1, 2, 1]," +
                " [1, 2, 2], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 2], [1, 3, 3], [2, 1, 1], [2, 1, 2]," +
                " [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 2], [2, 2, 3], [2, 2, 1], [2, 2, 2], [2, 2, 2]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 2], [2, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 2], [2, 1, 3]," +
                " [2, 2, 1], [2, 2, 2], [2, 2, 2], [2, 2, 3], [2, 2, 1], [2, 2, 2], [2, 2, 2], [2, 2, 3], [2, 3, 1]," +
                " [2, 3, 2], [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2]," +
                " [3, 2, 2], [3, 2, 3], [3, 2, 1], [3, 2, 2], [3, 2, 2], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 2]," +
                " [3, 3, 3]]");
        listsLex_int_List_helper(0, "[1, null, 3]", "[[]]");
        listsLex_int_List_helper(1, "[1, null, 3]", "[[1], [null], [3]]");
        listsLex_int_List_helper(2, "[1, null, 3]",
                "[[1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1], [3, null], [3, 3]]");
        listsLex_int_List_helper(3, "[1, null, 3]",
                "[[1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null], [1, null, 3], [1, 3, 1]," +
                " [1, 3, null], [1, 3, 3], [null, 1, 1], [null, 1, null], [null, 1, 3], [null, null, 1]," +
                " [null, null, null], [null, null, 3], [null, 3, 1], [null, 3, null], [null, 3, 3], [3, 1, 1]," +
                " [3, 1, null], [3, 1, 3], [3, null, 1], [3, null, null], [3, null, 3], [3, 3, 1], [3, 3, null]," +
                " [3, 3, 3]]");
        listsLex_int_List_helper(0, "[]", "[[]]");
        listsLex_int_List_helper(1, "[]", "[]");
        listsLex_int_List_helper(2, "[]", "[]");
        listsLex_int_List_helper(3, "[]", "[]");
        try {
            EP.listsLex(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.listsLex(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static <A, B> void pairsLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqit(EP.pairsLex(as, toList(bs)), output);
    }

    private static <A, B> void pairsLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.pairsLex(as, toList(bs)), output);
    }

    @Test
    public void testPairsLex() {
        pairsLex_helper(Arrays.asList(1, 2, 3), fromString("abc"),
                "[(1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)]");
        pairsLex_helper(Arrays.asList(1, null, 3), fromString("abc"),
                "[(1, a), (1, b), (1, c), (null, a), (null, b), (null, c), (3, a), (3, b), (3, c)]");
        pairsLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"),
                "[(0, a), (0, b), (0, c), (1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)," +
                " (4, a), (4, b), (4, c), (5, a), (5, b), (5, c), (6, a), (6, b), ...]");
        pairsLex_helper(EP.naturalBigIntegers(), Collections.emptyList(), "[]");
        pairsLex_helper(Collections.emptyList(), fromString("abc"), "[]");
        pairsLex_helper(Collections.emptyList(), Collections.emptyList(), "[]");
    }

    private static <A, B, C> void triplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        aeqit(EP.triplesLex(as, toList(bs), toList(cs)), output);
    }

    private static <A, B, C> void triplesLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.triplesLex(as, toList(bs), toList(cs)), output);
    }

    @Test
    public void testTriplesLex() {
        triplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        triplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (null, a, false), (null, a, true), (null, b, false), (null, b, true), (null, c, false)," +
                " (null, c, true), (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false)," +
                " (3, c, true)]");
        triplesLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (0, c, false), (0, c, true)," +
                " (1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), ...]");
        triplesLex_helper(EP.naturalBigIntegers(), fromString("abc"), Collections.emptyList(), "[]");
        triplesLex_helper(Collections.emptyList(), fromString("abc"), EP.booleans(), "[]");
        triplesLex_helper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "[]");
    }

    private static <A, B, C, D> void quadruplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        aeqit(EP.quadruplesLex(as, toList(bs), toList(cs), toList(ds)), output);
    }

    private static <A, B, C, D> void quadruplesLex_helper_limit(
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
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT)," +
                " (1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ)," +
                " (1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT)," +
                " (1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, false, GT), (2, a, true, EQ), (2, a, true, LT), (2, a, true, GT), (2, b, false, EQ)," +
                " (2, b, false, LT), (2, b, false, GT), (2, b, true, EQ), (2, b, true, LT), (2, b, true, GT)," +
                " (2, c, false, EQ), (2, c, false, LT), (2, c, false, GT), (2, c, true, EQ), (2, c, true, LT)," +
                " (2, c, true, GT), (3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ)," +
                " (3, a, true, LT), (3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT)," +
                " (3, b, true, EQ), (3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT)," +
                " (3, c, false, GT), (3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        quadruplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(), EP.orderings(),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT)," +
                " (1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ)," +
                " (1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT)," +
                " (1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (null, a, false, EQ), (null, a, false, LT)," +
                " (null, a, false, GT), (null, a, true, EQ), (null, a, true, LT), (null, a, true, GT)," +
                " (null, b, false, EQ), (null, b, false, LT), (null, b, false, GT), (null, b, true, EQ)," +
                " (null, b, true, LT), (null, b, true, GT), (null, c, false, EQ), (null, c, false, LT)," +
                " (null, c, false, GT), (null, c, true, EQ), (null, c, true, LT), (null, c, true, GT)," +
                " (3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ), (3, a, true, LT)," +
                " (3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT), (3, b, true, EQ)," +
                " (3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT), (3, c, false, GT)," +
                " (3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        quadruplesLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(), EP.orderings(),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, false, GT), (0, a, true, EQ), (0, a, true, LT)," +
                " (0, a, true, GT), (0, b, false, EQ), (0, b, false, LT), (0, b, false, GT), (0, b, true, EQ)," +
                " (0, b, true, LT), (0, b, true, GT), (0, c, false, EQ), (0, c, false, LT), (0, c, false, GT)," +
                " (0, c, true, EQ), (0, c, true, LT), (0, c, true, GT), (1, a, false, EQ), (1, a, false, LT), ...]");
        quadruplesLex_helper(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(), Collections.emptyList(), "[]");
        quadruplesLex_helper(Collections.emptyList(), fromString("abc"), EP.booleans(), EP.orderings(), "[]");
        quadruplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
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
        aeqit(EP.quintuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es)), output);
    }

    private static <A, B, C, D, E> void quintuplesLex_helper_limit(
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
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, EQ, yes), (1, a, true, EQ, no)," +
                " (1, a, true, LT, yes), (1, a, true, LT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, EQ, yes), (1, b, true, EQ, no)," +
                " (1, b, true, LT, yes), (1, b, true, LT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, EQ, yes), (1, c, true, EQ, no)," +
                " (1, c, true, LT, yes), (1, c, true, LT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, EQ, yes), (2, a, true, EQ, no)," +
                " (2, a, true, LT, yes), (2, a, true, LT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, EQ, yes), (2, b, true, EQ, no)," +
                " (2, b, true, LT, yes), (2, b, true, LT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, EQ, yes), (2, c, true, EQ, no)," +
                " (2, c, true, LT, yes), (2, c, true, LT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, EQ, yes), (3, a, true, EQ, no)," +
                " (3, a, true, LT, yes), (3, a, true, LT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, EQ, yes), (3, b, true, EQ, no)," +
                " (3, b, true, LT, yes), (3, b, true, LT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, EQ, yes), (3, c, true, EQ, no)," +
                " (3, c, true, LT, yes), (3, c, true, LT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]");
        quintuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, EQ, yes), (1, a, true, EQ, no)," +
                " (1, a, true, LT, yes), (1, a, true, LT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, EQ, yes), (1, b, true, EQ, no)," +
                " (1, b, true, LT, yes), (1, b, true, LT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, EQ, yes), (1, c, true, EQ, no)," +
                " (1, c, true, LT, yes), (1, c, true, LT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (null, a, false, EQ, yes), (null, a, false, EQ, no), (null, a, false, LT, yes)," +
                " (null, a, false, LT, no), (null, a, false, GT, yes), (null, a, false, GT, no)," +
                " (null, a, true, EQ, yes), (null, a, true, EQ, no), (null, a, true, LT, yes)," +
                " (null, a, true, LT, no), (null, a, true, GT, yes), (null, a, true, GT, no)," +
                " (null, b, false, EQ, yes), (null, b, false, EQ, no), (null, b, false, LT, yes)," +
                " (null, b, false, LT, no), (null, b, false, GT, yes), (null, b, false, GT, no)," +
                " (null, b, true, EQ, yes), (null, b, true, EQ, no), (null, b, true, LT, yes)," +
                " (null, b, true, LT, no), (null, b, true, GT, yes), (null, b, true, GT, no)," +
                " (null, c, false, EQ, yes), (null, c, false, EQ, no), (null, c, false, LT, yes)," +
                " (null, c, false, LT, no), (null, c, false, GT, yes), (null, c, false, GT, no)," +
                " (null, c, true, EQ, yes), (null, c, true, EQ, no), (null, c, true, LT, yes)," +
                " (null, c, true, LT, no), (null, c, true, GT, yes), (null, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, EQ, yes), (3, a, true, EQ, no)," +
                " (3, a, true, LT, yes), (3, a, true, LT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, EQ, yes), (3, b, true, EQ, no)," +
                " (3, b, true, LT, yes), (3, b, true, LT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, EQ, yes), (3, c, true, EQ, no)," +
                " (3, c, true, LT, yes), (3, c, true, LT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]");
        quintuplesLex_helper_limit(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, false, GT, yes), (0, a, false, GT, no), (0, a, true, EQ, yes), (0, a, true, EQ, no)," +
                " (0, a, true, LT, yes), (0, a, true, LT, no), (0, a, true, GT, yes), (0, a, true, GT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, false, GT, yes), (0, b, false, GT, no), (0, b, true, EQ, yes), (0, b, true, EQ, no), ...]");
        quintuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Collections.emptyList(),
                "[]"
        );
        quintuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[]"
        );
        quintuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
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
        aeqit(EP.sextuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs)), output);
    }

    private static <A, B, C, D, E, F> void sextuplesLex_helper_limit(
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
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, false, GT, yes, Infinity)," +
                " (1, a, false, GT, yes, NaN), (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN)," +
                " (1, a, true, EQ, yes, Infinity), (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity)," +
                " (1, a, true, EQ, no, NaN), (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN)," +
                " (1, a, true, LT, no, Infinity), (1, a, true, LT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN), (1, b, false, EQ, no, Infinity)," +
                " (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity), (1, b, false, LT, yes, NaN)," +
                " (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN), (1, b, false, GT, yes, Infinity)," +
                " (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity), (1, b, false, GT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (1, b, true, GT, yes, Infinity)," +
                " (1, b, true, GT, yes, NaN), (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, EQ, yes, Infinity), (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity)," +
                " (1, c, true, EQ, no, NaN), (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN)," +
                " (1, c, true, LT, no, Infinity), (1, c, true, LT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (2, a, false, EQ, yes, Infinity), (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity)," +
                " (2, a, false, EQ, no, NaN), (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN)," +
                " (2, a, false, LT, no, Infinity), (2, a, false, LT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN), (2, a, true, EQ, no, Infinity)," +
                " (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity), (2, a, true, LT, yes, NaN)," +
                " (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN), (2, a, true, GT, yes, Infinity)," +
                " (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity), (2, a, true, GT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, false, GT, yes, Infinity)," +
                " (2, b, false, GT, yes, NaN), (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN)," +
                " (2, b, true, EQ, yes, Infinity), (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity)," +
                " (2, b, true, EQ, no, NaN), (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN)," +
                " (2, b, true, LT, no, Infinity), (2, b, true, LT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (2, c, false, EQ, yes, Infinity), (2, c, false, EQ, yes, NaN), (2, c, false, EQ, no, Infinity)," +
                " (2, c, false, EQ, no, NaN), (2, c, false, LT, yes, Infinity), (2, c, false, LT, yes, NaN)," +
                " (2, c, false, LT, no, Infinity), (2, c, false, LT, no, NaN), (2, c, false, GT, yes, Infinity)," +
                " (2, c, false, GT, yes, NaN), (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN)," +
                " (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN), (2, c, true, EQ, no, Infinity)," +
                " (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity), (2, c, true, LT, yes, NaN)," +
                " (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN), (2, c, true, GT, yes, Infinity)," +
                " (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity), (2, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, EQ, yes, Infinity), (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity)," +
                " (3, a, true, EQ, no, NaN), (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN)," +
                " (3, a, true, LT, no, Infinity), (3, a, true, LT, no, NaN), (3, a, true, GT, yes, Infinity)," +
                " (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity), (3, a, true, GT, no, NaN)," +
                " (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN), (3, b, false, EQ, no, Infinity)," +
                " (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity), (3, b, false, LT, yes, NaN)," +
                " (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN), (3, b, false, GT, yes, Infinity)," +
                " (3, b, false, GT, yes, NaN), (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, false, GT, yes, Infinity)," +
                " (3, c, false, GT, yes, NaN), (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN)," +
                " (3, c, true, EQ, yes, Infinity), (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity)," +
                " (3, c, true, EQ, no, NaN), (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN)," +
                " (3, c, true, LT, no, Infinity), (3, c, true, LT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]");
        sextuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, false, GT, yes, Infinity)," +
                " (1, a, false, GT, yes, NaN), (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN)," +
                " (1, a, true, EQ, yes, Infinity), (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity)," +
                " (1, a, true, EQ, no, NaN), (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN)," +
                " (1, a, true, LT, no, Infinity), (1, a, true, LT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN), (1, b, false, EQ, no, Infinity)," +
                " (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity), (1, b, false, LT, yes, NaN)," +
                " (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN), (1, b, false, GT, yes, Infinity)," +
                " (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity), (1, b, false, GT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (1, b, true, GT, yes, Infinity)," +
                " (1, b, true, GT, yes, NaN), (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, EQ, yes, Infinity), (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity)," +
                " (1, c, true, EQ, no, NaN), (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN)," +
                " (1, c, true, LT, no, Infinity), (1, c, true, LT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (null, a, false, EQ, yes, Infinity), (null, a, false, EQ, yes, NaN)," +
                " (null, a, false, EQ, no, Infinity), (null, a, false, EQ, no, NaN)," +
                " (null, a, false, LT, yes, Infinity), (null, a, false, LT, yes, NaN)," +
                " (null, a, false, LT, no, Infinity), (null, a, false, LT, no, NaN)," +
                " (null, a, false, GT, yes, Infinity), (null, a, false, GT, yes, NaN)," +
                " (null, a, false, GT, no, Infinity), (null, a, false, GT, no, NaN)," +
                " (null, a, true, EQ, yes, Infinity), (null, a, true, EQ, yes, NaN)," +
                " (null, a, true, EQ, no, Infinity), (null, a, true, EQ, no, NaN)," +
                " (null, a, true, LT, yes, Infinity), (null, a, true, LT, yes, NaN)," +
                " (null, a, true, LT, no, Infinity), (null, a, true, LT, no, NaN)," +
                " (null, a, true, GT, yes, Infinity), (null, a, true, GT, yes, NaN)," +
                " (null, a, true, GT, no, Infinity), (null, a, true, GT, no, NaN)," +
                " (null, b, false, EQ, yes, Infinity), (null, b, false, EQ, yes, NaN)," +
                " (null, b, false, EQ, no, Infinity), (null, b, false, EQ, no, NaN)," +
                " (null, b, false, LT, yes, Infinity), (null, b, false, LT, yes, NaN)," +
                " (null, b, false, LT, no, Infinity), (null, b, false, LT, no, NaN)," +
                " (null, b, false, GT, yes, Infinity), (null, b, false, GT, yes, NaN)," +
                " (null, b, false, GT, no, Infinity), (null, b, false, GT, no, NaN)," +
                " (null, b, true, EQ, yes, Infinity), (null, b, true, EQ, yes, NaN)," +
                " (null, b, true, EQ, no, Infinity), (null, b, true, EQ, no, NaN)," +
                " (null, b, true, LT, yes, Infinity), (null, b, true, LT, yes, NaN)," +
                " (null, b, true, LT, no, Infinity), (null, b, true, LT, no, NaN)," +
                " (null, b, true, GT, yes, Infinity), (null, b, true, GT, yes, NaN)," +
                " (null, b, true, GT, no, Infinity), (null, b, true, GT, no, NaN)," +
                " (null, c, false, EQ, yes, Infinity), (null, c, false, EQ, yes, NaN)," +
                " (null, c, false, EQ, no, Infinity), (null, c, false, EQ, no, NaN)," +
                " (null, c, false, LT, yes, Infinity), (null, c, false, LT, yes, NaN)," +
                " (null, c, false, LT, no, Infinity), (null, c, false, LT, no, NaN)," +
                " (null, c, false, GT, yes, Infinity), (null, c, false, GT, yes, NaN)," +
                " (null, c, false, GT, no, Infinity), (null, c, false, GT, no, NaN)," +
                " (null, c, true, EQ, yes, Infinity), (null, c, true, EQ, yes, NaN)," +
                " (null, c, true, EQ, no, Infinity), (null, c, true, EQ, no, NaN)," +
                " (null, c, true, LT, yes, Infinity), (null, c, true, LT, yes, NaN)," +
                " (null, c, true, LT, no, Infinity), (null, c, true, LT, no, NaN)," +
                " (null, c, true, GT, yes, Infinity), (null, c, true, GT, yes, NaN)," +
                " (null, c, true, GT, no, Infinity), (null, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, EQ, yes, Infinity), (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity)," +
                " (3, a, true, EQ, no, NaN), (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN)," +
                " (3, a, true, LT, no, Infinity), (3, a, true, LT, no, NaN), (3, a, true, GT, yes, Infinity)," +
                " (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity), (3, a, true, GT, no, NaN)," +
                " (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN), (3, b, false, EQ, no, Infinity)," +
                " (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity), (3, b, false, LT, yes, NaN)," +
                " (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN), (3, b, false, GT, yes, Infinity)," +
                " (3, b, false, GT, yes, NaN), (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, false, GT, yes, Infinity)," +
                " (3, c, false, GT, yes, NaN), (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN)," +
                " (3, c, true, EQ, yes, Infinity), (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity)," +
                " (3, c, true, EQ, no, NaN), (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN)," +
                " (3, c, true, LT, no, Infinity), (3, c, true, LT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]");
        sextuplesLex_helper_limit(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "[(0, a, false, EQ, yes, Infinity), (0, a, false, EQ, yes, NaN), (0, a, false, EQ, no, Infinity)," +
                " (0, a, false, EQ, no, NaN), (0, a, false, LT, yes, Infinity), (0, a, false, LT, yes, NaN)," +
                " (0, a, false, LT, no, Infinity), (0, a, false, LT, no, NaN), (0, a, false, GT, yes, Infinity)," +
                " (0, a, false, GT, yes, NaN), (0, a, false, GT, no, Infinity), (0, a, false, GT, no, NaN)," +
                " (0, a, true, EQ, yes, Infinity), (0, a, true, EQ, yes, NaN), (0, a, true, EQ, no, Infinity)," +
                " (0, a, true, EQ, no, NaN), (0, a, true, LT, yes, Infinity), (0, a, true, LT, yes, NaN)," +
                " (0, a, true, LT, no, Infinity), (0, a, true, LT, no, NaN), ...]");
        sextuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Collections.emptyList(),
                "[]"
        );
        sextuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "[]"
        );
        sextuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
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
        aeqit(EP.septuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs), toList(gs)), output);
    }

    private static <A, B, C, D, E, F, G> void septuplesLex_helper_limit(
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
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1])," +
                " (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1])," +
                " (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1])," +
                " (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1])," +
                " (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1])," +
                " (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1])," +
                " (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1])," +
                " (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1])," +
                " (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1])," +
                " (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1])," +
                " (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1])," +
                " (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1])," +
                " (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1])," +
                " (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1])," +
                " (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1])," +
                " (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1])," +
                " (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1])," +
                " (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1])," +
                " (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1])," +
                " (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1])," +
                " (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1])," +
                " (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1])," +
                " (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1])," +
                " (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1])," +
                " (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1])," +
                " (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1])," +
                " (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1])," +
                " (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1])," +
                " (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1])," +
                " (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1])," +
                " (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1])," +
                " (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1])," +
                " (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1])," +
                " (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1])," +
                " (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1])," +
                " (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1])," +
                " (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1])," +
                " (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1])," +
                " (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1])," +
                " (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1])," +
                " (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1])," +
                " (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1])," +
                " (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1])," +
                " (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1])," +
                " (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1])," +
                " (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1])," +
                " (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1])," +
                " (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1])," +
                " (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1])," +
                " (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1])," +
                " (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1])," +
                " (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1])," +
                " (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1])," +
                " (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1])," +
                " (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1])," +
                " (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1])," +
                " (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1])," +
                " (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1])," +
                " (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]");
        septuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (null, a, false, EQ, yes, Infinity, [1, 0]), (null, a, false, EQ, yes, Infinity, [0, 1])," +
                " (null, a, false, EQ, yes, NaN, [1, 0]), (null, a, false, EQ, yes, NaN, [0, 1])," +
                " (null, a, false, EQ, no, Infinity, [1, 0]), (null, a, false, EQ, no, Infinity, [0, 1])," +
                " (null, a, false, EQ, no, NaN, [1, 0]), (null, a, false, EQ, no, NaN, [0, 1])," +
                " (null, a, false, LT, yes, Infinity, [1, 0]), (null, a, false, LT, yes, Infinity, [0, 1])," +
                " (null, a, false, LT, yes, NaN, [1, 0]), (null, a, false, LT, yes, NaN, [0, 1])," +
                " (null, a, false, LT, no, Infinity, [1, 0]), (null, a, false, LT, no, Infinity, [0, 1])," +
                " (null, a, false, LT, no, NaN, [1, 0]), (null, a, false, LT, no, NaN, [0, 1])," +
                " (null, a, false, GT, yes, Infinity, [1, 0]), (null, a, false, GT, yes, Infinity, [0, 1])," +
                " (null, a, false, GT, yes, NaN, [1, 0]), (null, a, false, GT, yes, NaN, [0, 1])," +
                " (null, a, false, GT, no, Infinity, [1, 0]), (null, a, false, GT, no, Infinity, [0, 1])," +
                " (null, a, false, GT, no, NaN, [1, 0]), (null, a, false, GT, no, NaN, [0, 1])," +
                " (null, a, true, EQ, yes, Infinity, [1, 0]), (null, a, true, EQ, yes, Infinity, [0, 1])," +
                " (null, a, true, EQ, yes, NaN, [1, 0]), (null, a, true, EQ, yes, NaN, [0, 1])," +
                " (null, a, true, EQ, no, Infinity, [1, 0]), (null, a, true, EQ, no, Infinity, [0, 1])," +
                " (null, a, true, EQ, no, NaN, [1, 0]), (null, a, true, EQ, no, NaN, [0, 1])," +
                " (null, a, true, LT, yes, Infinity, [1, 0]), (null, a, true, LT, yes, Infinity, [0, 1])," +
                " (null, a, true, LT, yes, NaN, [1, 0]), (null, a, true, LT, yes, NaN, [0, 1])," +
                " (null, a, true, LT, no, Infinity, [1, 0]), (null, a, true, LT, no, Infinity, [0, 1])," +
                " (null, a, true, LT, no, NaN, [1, 0]), (null, a, true, LT, no, NaN, [0, 1])," +
                " (null, a, true, GT, yes, Infinity, [1, 0]), (null, a, true, GT, yes, Infinity, [0, 1])," +
                " (null, a, true, GT, yes, NaN, [1, 0]), (null, a, true, GT, yes, NaN, [0, 1])," +
                " (null, a, true, GT, no, Infinity, [1, 0]), (null, a, true, GT, no, Infinity, [0, 1])," +
                " (null, a, true, GT, no, NaN, [1, 0]), (null, a, true, GT, no, NaN, [0, 1])," +
                " (null, b, false, EQ, yes, Infinity, [1, 0]), (null, b, false, EQ, yes, Infinity, [0, 1])," +
                " (null, b, false, EQ, yes, NaN, [1, 0]), (null, b, false, EQ, yes, NaN, [0, 1])," +
                " (null, b, false, EQ, no, Infinity, [1, 0]), (null, b, false, EQ, no, Infinity, [0, 1])," +
                " (null, b, false, EQ, no, NaN, [1, 0]), (null, b, false, EQ, no, NaN, [0, 1])," +
                " (null, b, false, LT, yes, Infinity, [1, 0]), (null, b, false, LT, yes, Infinity, [0, 1])," +
                " (null, b, false, LT, yes, NaN, [1, 0]), (null, b, false, LT, yes, NaN, [0, 1])," +
                " (null, b, false, LT, no, Infinity, [1, 0]), (null, b, false, LT, no, Infinity, [0, 1])," +
                " (null, b, false, LT, no, NaN, [1, 0]), (null, b, false, LT, no, NaN, [0, 1])," +
                " (null, b, false, GT, yes, Infinity, [1, 0]), (null, b, false, GT, yes, Infinity, [0, 1])," +
                " (null, b, false, GT, yes, NaN, [1, 0]), (null, b, false, GT, yes, NaN, [0, 1])," +
                " (null, b, false, GT, no, Infinity, [1, 0]), (null, b, false, GT, no, Infinity, [0, 1])," +
                " (null, b, false, GT, no, NaN, [1, 0]), (null, b, false, GT, no, NaN, [0, 1])," +
                " (null, b, true, EQ, yes, Infinity, [1, 0]), (null, b, true, EQ, yes, Infinity, [0, 1])," +
                " (null, b, true, EQ, yes, NaN, [1, 0]), (null, b, true, EQ, yes, NaN, [0, 1])," +
                " (null, b, true, EQ, no, Infinity, [1, 0]), (null, b, true, EQ, no, Infinity, [0, 1])," +
                " (null, b, true, EQ, no, NaN, [1, 0]), (null, b, true, EQ, no, NaN, [0, 1])," +
                " (null, b, true, LT, yes, Infinity, [1, 0]), (null, b, true, LT, yes, Infinity, [0, 1])," +
                " (null, b, true, LT, yes, NaN, [1, 0]), (null, b, true, LT, yes, NaN, [0, 1])," +
                " (null, b, true, LT, no, Infinity, [1, 0]), (null, b, true, LT, no, Infinity, [0, 1])," +
                " (null, b, true, LT, no, NaN, [1, 0]), (null, b, true, LT, no, NaN, [0, 1])," +
                " (null, b, true, GT, yes, Infinity, [1, 0]), (null, b, true, GT, yes, Infinity, [0, 1])," +
                " (null, b, true, GT, yes, NaN, [1, 0]), (null, b, true, GT, yes, NaN, [0, 1])," +
                " (null, b, true, GT, no, Infinity, [1, 0]), (null, b, true, GT, no, Infinity, [0, 1])," +
                " (null, b, true, GT, no, NaN, [1, 0]), (null, b, true, GT, no, NaN, [0, 1])," +
                " (null, c, false, EQ, yes, Infinity, [1, 0]), (null, c, false, EQ, yes, Infinity, [0, 1])," +
                " (null, c, false, EQ, yes, NaN, [1, 0]), (null, c, false, EQ, yes, NaN, [0, 1])," +
                " (null, c, false, EQ, no, Infinity, [1, 0]), (null, c, false, EQ, no, Infinity, [0, 1])," +
                " (null, c, false, EQ, no, NaN, [1, 0]), (null, c, false, EQ, no, NaN, [0, 1])," +
                " (null, c, false, LT, yes, Infinity, [1, 0]), (null, c, false, LT, yes, Infinity, [0, 1])," +
                " (null, c, false, LT, yes, NaN, [1, 0]), (null, c, false, LT, yes, NaN, [0, 1])," +
                " (null, c, false, LT, no, Infinity, [1, 0]), (null, c, false, LT, no, Infinity, [0, 1])," +
                " (null, c, false, LT, no, NaN, [1, 0]), (null, c, false, LT, no, NaN, [0, 1])," +
                " (null, c, false, GT, yes, Infinity, [1, 0]), (null, c, false, GT, yes, Infinity, [0, 1])," +
                " (null, c, false, GT, yes, NaN, [1, 0]), (null, c, false, GT, yes, NaN, [0, 1])," +
                " (null, c, false, GT, no, Infinity, [1, 0]), (null, c, false, GT, no, Infinity, [0, 1])," +
                " (null, c, false, GT, no, NaN, [1, 0]), (null, c, false, GT, no, NaN, [0, 1])," +
                " (null, c, true, EQ, yes, Infinity, [1, 0]), (null, c, true, EQ, yes, Infinity, [0, 1])," +
                " (null, c, true, EQ, yes, NaN, [1, 0]), (null, c, true, EQ, yes, NaN, [0, 1])," +
                " (null, c, true, EQ, no, Infinity, [1, 0]), (null, c, true, EQ, no, Infinity, [0, 1])," +
                " (null, c, true, EQ, no, NaN, [1, 0]), (null, c, true, EQ, no, NaN, [0, 1])," +
                " (null, c, true, LT, yes, Infinity, [1, 0]), (null, c, true, LT, yes, Infinity, [0, 1])," +
                " (null, c, true, LT, yes, NaN, [1, 0]), (null, c, true, LT, yes, NaN, [0, 1])," +
                " (null, c, true, LT, no, Infinity, [1, 0]), (null, c, true, LT, no, Infinity, [0, 1])," +
                " (null, c, true, LT, no, NaN, [1, 0]), (null, c, true, LT, no, NaN, [0, 1])," +
                " (null, c, true, GT, yes, Infinity, [1, 0]), (null, c, true, GT, yes, Infinity, [0, 1])," +
                " (null, c, true, GT, yes, NaN, [1, 0]), (null, c, true, GT, yes, NaN, [0, 1])," +
                " (null, c, true, GT, no, Infinity, [1, 0]), (null, c, true, GT, no, Infinity, [0, 1])," +
                " (null, c, true, GT, no, NaN, [1, 0]), (null, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]");
        septuplesLex_helper_limit(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "[(0, a, false, EQ, yes, Infinity, [1, 0]), (0, a, false, EQ, yes, Infinity, [0, 1])," +
                " (0, a, false, EQ, yes, NaN, [1, 0]), (0, a, false, EQ, yes, NaN, [0, 1])," +
                " (0, a, false, EQ, no, Infinity, [1, 0]), (0, a, false, EQ, no, Infinity, [0, 1])," +
                " (0, a, false, EQ, no, NaN, [1, 0]), (0, a, false, EQ, no, NaN, [0, 1])," +
                " (0, a, false, LT, yes, Infinity, [1, 0]), (0, a, false, LT, yes, Infinity, [0, 1])," +
                " (0, a, false, LT, yes, NaN, [1, 0]), (0, a, false, LT, yes, NaN, [0, 1])," +
                " (0, a, false, LT, no, Infinity, [1, 0]), (0, a, false, LT, no, Infinity, [0, 1])," +
                " (0, a, false, LT, no, NaN, [1, 0]), (0, a, false, LT, no, NaN, [0, 1])," +
                " (0, a, false, GT, yes, Infinity, [1, 0]), (0, a, false, GT, yes, Infinity, [0, 1])," +
                " (0, a, false, GT, yes, NaN, [1, 0]), (0, a, false, GT, yes, NaN, [0, 1]), ...]");
        septuplesLex_helper(
                EP.naturalBigIntegers(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Collections.emptyList(),
                "[]"
        );
        septuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "[]"
        );
        septuplesLex_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
    }

    private static void stringsLex_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringsLex(size, input), output);
    }

    private static void stringsLex_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsLex(size, input), output);
    }

    @Test
    public void testStringsLex_int_String() {
        stringsLex_int_String_helper(0, "", "[]");
        aeq(length(EP.stringsLex(0, "")), 1);
        stringsLex_int_String_helper(1, "", "[]");
        aeq(length(EP.stringsLex(1, "")), 0);
        stringsLex_int_String_helper(2, "", "[]");
        aeq(length(EP.stringsLex(2, "")), 0);
        stringsLex_int_String_helper(3, "", "[]");
        aeq(length(EP.stringsLex(3, "")), 0);
        stringsLex_int_String_helper(0, "a", "[]");
        stringsLex_int_String_helper(1, "a", "[a]");
        stringsLex_int_String_helper(2, "a", "[aa]");
        stringsLex_int_String_helper(3, "a", "[aaa]");
        stringsLex_int_String_helper(0, "abc", "[]");
        aeq(length(EP.stringsLex(0, "abc")), 1);
        stringsLex_int_String_helper(1, "abc", "[a, b, c]");
        stringsLex_int_String_helper(2, "abc", "[aa, ab, ac, ba, bb, bc, ca, cb, cc]");
        stringsLex_int_String_helper(3, "abc",
                "[aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, bac, bba, bbb, bbc, bca, bcb, bcc, caa," +
                " cab, cac, cba, cbb, cbc, cca, ccb, ccc]");
        stringsLex_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.stringsLex(0, "abbc")), 1);
        stringsLex_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringsLex_int_String_helper(2, "abbc", "[aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, cc]");
        stringsLex_int_String_helper(3, "abbc",
                "[aaa, aab, aab, aac, aba, abb, abb, abc, aba, abb, abb, abc, aca, acb, acb, acc, baa, bab, bab," +
                " bac, bba, bbb, bbb, bbc, bba, bbb, bbb, bbc, bca, bcb, bcb, bcc, baa, bab, bab, bac, bba, bbb," +
                " bbb, bbc, bba, bbb, bbb, bbc, bca, bcb, bcb, bcc, caa, cab, cab, cac, cba, cbb, cbb, cbc, cba," +
                " cbb, cbb, cbc, cca, ccb, ccb, ccc]");
        stringsLex_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.stringsLex(0, "Mississippi")), 1);
        stringsLex_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringsLex_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, ii, is, is, ii, is, is, ii, ip, ...]");
        stringsLex_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, MMs, MMs, MMi, MMs, MMs, MMi, MMp, MMp, MMi, MiM, Mii, Mis, Mis, Mii, Mis, Mis, Mii," +
                " Mip, ...]");
        try {
            EP.stringsLex(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringsLex(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void listsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.listsShortlex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testListsShortlex() {
        listsShortlex_helper("[]", "[[]]");
        listsShortlex_helper("[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsShortlex_helper("[1, 2, 3]",
                "[[], [1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]," +
                " [1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], ...]");
        listsShortlex_helper("[1, 2, 2, 3]",
                "[[], [1], [2], [2], [3], [1, 1], [1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 2], [2, 3], [2, 1]," +
                " [2, 2], [2, 2], [2, 3], [3, 1], [3, 2], [3, 2], ...]");
    }

    private static void stringsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsShortlex(input), output);
    }

    @Test
    public void testStringsShortlex() {
        stringsShortlex_helper("", "[]");
        aeq(length(EP.stringsShortlex("")), 1);
        stringsShortlex_helper("a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringsShortlex_helper("abc",
                "[, a, b, c, aa, ab, ac, ba, bb, bc, ca, cb, cc, aaa, aab, aac, aba, abb, abc, aca, ...]");
        stringsShortlex_helper("abbc",
                "[, a, b, b, c, aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, ...]");
        stringsShortlex_helper("Mississippi",
                "[, M, i, s, s, i, s, s, i, p, p, i, MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, ...]");
    }

    private static void listsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.listsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testListsShortlexAtLeast() {
        listsShortlexAtLeast_helper(0, "[]", "[[]]");
        listsShortlexAtLeast_helper(1, "[]", "[]");
        listsShortlexAtLeast_helper(2, "[]", "[]");
        listsShortlexAtLeast_helper(3, "[]", "[]");

        listsShortlexAtLeast_helper(0, "[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsShortlexAtLeast_helper(1, "[5]",
                "[[5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsShortlexAtLeast_helper(2, "[5]",
                "[[5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsShortlexAtLeast_helper(3, "[5]",
                "[[5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");

        listsShortlexAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]," +
                " [1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], ...]");
        listsShortlexAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3], [1, 1, 1]," +
                " [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], ...]");
        listsShortlexAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3], [1, 1, 1], [1, 1, 2]," +
                " [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3], [2, 1, 1], [2, 1, 2]," +
                " ...]");
        listsShortlexAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3]," +
                " [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 3]," +
                " [3, 1, 1], [3, 1, 2], ...]");

        listsShortlexAtLeast_helper(0, "[1, null, 3]",
                "[[], [1], [null], [3], [1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1]," +
                " [3, null], [3, 3], [1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null]," +
                " [1, null, 3], [1, 3, 1], ...]");
        listsShortlexAtLeast_helper(1, "[1, null, 3]",
                "[[1], [null], [3], [1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1]," +
                " [3, null], [3, 3], [1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null]," +
                " [1, null, 3], [1, 3, 1], [1, 3, null], ...]");
        listsShortlexAtLeast_helper(2, "[1, null, 3]",
                "[[1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1], [3, null], [3, 3]," +
                " [1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null], [1, null, 3], [1, 3, 1]," +
                " [1, 3, null], [1, 3, 3], [null, 1, 1], [null, 1, null], ...]");
        listsShortlexAtLeast_helper(3, "[1, null, 3]",
                "[[1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null], [1, null, 3], [1, 3, 1]," +
                " [1, 3, null], [1, 3, 3], [null, 1, 1], [null, 1, null], [null, 1, 3], [null, null, 1]," +
                " [null, null, null], [null, null, 3], [null, 3, 1], [null, 3, null], [null, 3, 3], [3, 1, 1]," +
                " [3, 1, null], ...]");

        try {
            EP.listsShortlexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.listsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringsShortlexAtLeast() {
        stringsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(0, "")), 1);
        stringsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(1, "")), 0);
        stringsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(2, "")), 0);
        stringsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(3, "")), 0);
        stringsShortlexAtLeast_helper(0, "a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringsShortlexAtLeast_helper(1, "a",
                "[a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, ...]");
        stringsShortlexAtLeast_helper(2, "a",
                "[aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa, ...]");
        stringsShortlexAtLeast_helper(3, "a",
                "[aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaa, ...]");
        stringsShortlexAtLeast_helper(0, "abc",
                "[, a, b, c, aa, ab, ac, ba, bb, bc, ca, cb, cc, aaa, aab, aac, aba, abb, abc, aca, ...]");
        stringsShortlexAtLeast_helper(1, "abc",
                "[a, b, c, aa, ab, ac, ba, bb, bc, ca, cb, cc, aaa, aab, aac, aba, abb, abc, aca, acb, ...]");
        stringsShortlexAtLeast_helper(2, "abc",
                "[aa, ab, ac, ba, bb, bc, ca, cb, cc, aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, ...]");
        stringsShortlexAtLeast_helper(3, "abc",
                "[aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, bac, bba, bbb, bbc, bca, bcb, bcc, caa," +
                " cab, ...]");
        stringsShortlexAtLeast_helper(0, "abbc",
                "[, a, b, b, c, aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, ...]");
        stringsShortlexAtLeast_helper(1, "abbc",
                "[a, b, b, c, aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, cc, ...]");
        stringsShortlexAtLeast_helper(2, "abbc",
                "[aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, cc, aaa, aab, aab, aac, ...]");
        stringsShortlexAtLeast_helper(3, "abbc",
                "[aaa, aab, aab, aac, aba, abb, abb, abc, aba, abb, abb, abc, aca, acb, acb, acc, baa, bab, bab," +
                " bac, ...]");
        stringsShortlexAtLeast_helper(0, "Mississippi",
                "[, M, i, s, s, i, s, s, i, p, p, i, MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, ...]");
        stringsShortlexAtLeast_helper(1, "Mississippi",
                "[M, i, s, s, i, s, s, i, p, p, i, MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, ...]");
        stringsShortlexAtLeast_helper(2, "Mississippi",
                "[MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, ii, is, is, ii, is, is, ii, ip, ...]");
        stringsShortlexAtLeast_helper(3, "Mississippi",
                "[MMM, MMi, MMs, MMs, MMi, MMs, MMs, MMi, MMp, MMp, MMi, MiM, Mii, Mis, Mis, Mii, Mis, Mis, Mii," +
                " Mip, ...]");
        try {
            EP.stringsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void lists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.lists(size, readIntegerListWithNulls(input)), output);
    }

    private static void lists_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.lists(size, input), output);
    }

    @Test
    public void testLists_int_Iterable() {
        lists_int_Iterable_helper(0, "[]", "[[]]");
        lists_int_Iterable_helper(1, "[]", "[]");
        lists_int_Iterable_helper(2, "[]", "[]");
        lists_int_Iterable_helper(3, "[]", "[]");

        lists_int_Iterable_helper(0, "[5]", "[[]]");
        lists_int_Iterable_helper(1, "[5]", "[[5]]");
        lists_int_Iterable_helper(2, "[5]", "[[5, 5]]");
        lists_int_Iterable_helper(3, "[5]", "[[5, 5, 5]]");

        lists_int_Iterable_helper(0, "[1, 2, 3]", "[[]]");
        lists_int_Iterable_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        lists_int_Iterable_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 2], [2, 1], [2, 2], [1, 3], [2, 3], [3, 1], [3, 2], [3, 3]]");
        lists_int_Iterable_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 2, 1], [1, 2, 2], [2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2], [1, 1, 3]," +
                " [1, 2, 3], [2, 1, 3], [2, 2, 3], [1, 3, 1], [1, 3, 2], [2, 3, 1], [2, 3, 2], [1, 3, 3], [2, 3, 3]," +
                " [3, 1, 1], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 1, 3], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 3]]");

        lists_int_Iterable_helper(0, "[1, 2, 2, 3]", "[[]]");
        lists_int_Iterable_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        lists_int_Iterable_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 2], [2, 1], [2, 2], [1, 2], [1, 3], [2, 2], [2, 3], [2, 1], [2, 2], [3, 1], [3, 2]," +
                " [2, 2], [2, 3], [3, 2], [3, 3]]");
        lists_int_Iterable_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 2, 1], [1, 2, 2], [2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2], [1, 1, 2]," +
                " [1, 1, 3], [1, 2, 2], [1, 2, 3], [2, 1, 2], [2, 1, 3], [2, 2, 2], [2, 2, 3], [1, 2, 1], [1, 2, 2]," +
                " [1, 3, 1], [1, 3, 2], [2, 2, 1], [2, 2, 2], [2, 3, 1], [2, 3, 2], [1, 2, 2], [1, 2, 3], [1, 3, 2]," +
                " [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 2], [2, 3, 3], [2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2]," +
                " [3, 1, 1], [3, 1, 2], [3, 2, 1], [3, 2, 2], [2, 1, 2], [2, 1, 3], [2, 2, 2], [2, 2, 3], [3, 1, 2]," +
                " [3, 1, 3], [3, 2, 2], [3, 2, 3], [2, 2, 1], [2, 2, 2], [2, 3, 1], [2, 3, 2], [3, 2, 1], [3, 2, 2]," +
                " [3, 3, 1], [3, 3, 2], [2, 2, 2], [2, 2, 3], [2, 3, 2], [2, 3, 3], [3, 2, 2], [3, 2, 3], [3, 3, 2]," +
                " [3, 3, 3]]");

        lists_int_Iterable_helper(0, "[1, null, 3]", "[[]]");
        lists_int_Iterable_helper(1, "[1, null, 3]", "[[1], [null], [3]]");
        lists_int_Iterable_helper(2, "[1, null, 3]",
                "[[1, 1], [1, null], [null, 1], [null, null], [1, 3], [null, 3], [3, 1], [3, null], [3, 3]]");
        lists_int_Iterable_helper(3, "[1, null, 3]",
                "[[1, 1, 1], [1, 1, null], [1, null, 1], [1, null, null], [null, 1, 1], [null, 1, null]," +
                " [null, null, 1], [null, null, null], [1, 1, 3], [1, null, 3], [null, 1, 3], [null, null, 3]," +
                " [1, 3, 1], [1, 3, null], [null, 3, 1], [null, 3, null], [1, 3, 3], [null, 3, 3], [3, 1, 1]," +
                " [3, 1, null], [3, null, 1], [3, null, null], [3, 1, 3], [3, null, 3], [3, 3, 1], [3, 3, null]," +
                " [3, 3, 3]]");

        lists_int_Iterable_helper(0, EP.positiveIntegers(), "[[]]");
        lists_int_Iterable_helper(1, EP.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        lists_int_Iterable_helper(2, EP.positiveIntegers(),
                "[[1, 1], [1, 2], [2, 1], [2, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 1], [3, 2], [4, 1], [4, 2]," +
                " [3, 3], [3, 4], [4, 3], [4, 4], [1, 5], [1, 6], [2, 5], [2, 6], ...]");
        lists_int_Iterable_helper(3, EP.positiveIntegers(),
                "[[1, 1, 1], [1, 1, 2], [1, 2, 1], [1, 2, 2], [2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2], [1, 1, 3]," +
                " [1, 1, 4], [1, 2, 3], [1, 2, 4], [2, 1, 3], [2, 1, 4], [2, 2, 3], [2, 2, 4], [1, 3, 1], [1, 3, 2]," +
                " [1, 4, 1], [1, 4, 2], ...]");

        lists_int_Iterable_helper(0, repeat(1), "[[]]");
        lists_int_Iterable_helper(1, repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]");
        lists_int_Iterable_helper(2, repeat(1),
                "[[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1]," +
                " [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], ...]");
        lists_int_Iterable_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], ...]");

        try {
            EP.lists(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.lists(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static <A, B> void pairs_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        Iterable<Pair<A, B>> ps = EP.pairs(as, bs);
        aeqit(ps, output);
        testNoRemove(ps);
    }

    @Test
    public void testPairs_Iterable_Iterable() {
        pairs_Iterable_Iterable_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d), (3, a), (3, b), (4, a), (4, b)," +
                " (3, c), (3, d), (4, c), (4, d)]");
        pairs_Iterable_Iterable_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d), (null, a), (null, b), (4, a)," +
                " (4, b), (null, c), (null, d), (4, c), (4, d)]");
        pairs_Iterable_Iterable_helper(Collections.emptyList(), fromString("abcd"), "[]");
        pairs_Iterable_Iterable_helper(Collections.emptyList(), Collections.emptyList(), "[]");
        simpleProviderHelper(EP.pairs(EP.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (1, b), (0, c), (0, d), (1, c), (1, d), (2, a), (2, b), (3, a), (3, b)," +
                " (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b), ...]");
        simpleProviderHelper(EP.pairs(fromString("abcd"), EP.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (a, 2), (a, 3), (b, 2), (b, 3), (c, 0), (c, 1), (d, 0), (d, 1)," +
                " (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5), ...]");
        simpleProviderHelper(EP.pairs(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (1, -3), (1, -4), (2, -3), (2, -4), (3, -1), (3, -2), (4, -1)," +
                " (4, -2), (3, -3), (3, -4), (4, -3), (4, -4), (1, -5), (1, -6), (2, -5), (2, -6), ...]");
    }

    private static void pairs_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.pairs(readIntegerListWithNulls(input)), output);
    }

    private static void pairs_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.pairs(input), output);
    }

    @Test
    public void testPairs_Iterable() {
        pairs_Iterable_helper("[]", "[]");
        pairs_Iterable_helper("[5]", "[(5, 5)]");
        pairs_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1), (1, 2), (2, 1), (2, 2), (1, 3), (1, 4), (2, 3), (2, 4), (3, 1), (3, 2), (4, 1), (4, 2)," +
                " (3, 3), (3, 4), (4, 3), (4, 4)]");
        pairs_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1), (1, 2), (2, 1), (2, 2), (1, 2), (1, 4), (2, 2), (2, 4), (2, 1), (2, 2), (4, 1), (4, 2)," +
                " (2, 2), (2, 4), (4, 2), (4, 4)]");
        pairs_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1), (1, 2), (2, 1), (2, 2), (1, null), (1, 4), (2, null), (2, 4), (null, 1), (null, 2)," +
                " (4, 1), (4, 2), (null, null), (null, 4), (4, null), (4, 4)]");
        pairs_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (0, 4), (0, 5), (1, 4), (1, 5), ...]");
        pairs_Iterable_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
    }

    private static <A, B, C> void triples_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        Iterable<Triple<A, B, C>> ts = EP.triples(as, bs, cs);
        aeqit(ts, output);
        testNoRemove(ts);
    }

    @Test
    public void testTriples_Iterable_Iterable_Iterable() {
        triples_Iterable_Iterable_Iterable_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        triples_Iterable_Iterable_Iterable_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"), EP.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (1, d, false), (1, d, true)," +
                " (2, c, false), (2, c, true), (2, d, false), (2, d, true), (null, a, false), (null, a, true)," +
                " (null, b, false), (null, b, true), (4, a, false), (4, a, true), (4, b, false), (4, b, true)," +
                " (null, c, false), (null, c, true), (null, d, false), (null, d, true), (4, c, false)," +
                " (4, c, true), (4, d, false), (4, d, true)]");
        triples_Iterable_Iterable_Iterable_helper(Collections.emptyList(), fromString("abcd"), EP.booleans(), "[]");
        triples_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
        simpleProviderHelper(EP.triples(EP.naturalBigIntegers(), fromString("abcd"), EP.booleans()),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (1, a, false), (1, a, true)," +
                " (1, b, false), (1, b, true), (0, c, false), (0, c, true), (0, d, false), (0, d, true)," +
                " (1, c, false), (1, c, true), (1, d, false), (1, d, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), ...]");
        simpleProviderHelper(EP.triples(fromString("abcd"), EP.booleans(), EP.naturalBigIntegers()),
                "[(a, false, 0), (a, false, 1), (a, true, 0), (a, true, 1), (b, false, 0), (b, false, 1)," +
                " (b, true, 0), (b, true, 1), (a, false, 2), (a, false, 3), (a, true, 2), (a, true, 3)," +
                " (b, false, 2), (b, false, 3), (b, true, 2), (b, true, 3), (c, false, 0), (c, false, 1)," +
                " (c, true, 0), (c, true, 1), ...]");
        simpleProviderHelper(EP.triples(EP.positiveBigIntegers(), EP.negativeBigIntegers(), EP.characters()),
                "[(1, -1, a), (1, -1, b), (1, -2, a), (1, -2, b), (2, -1, a), (2, -1, b), (2, -2, a), (2, -2, b)," +
                " (1, -1, c), (1, -1, d), (1, -2, c), (1, -2, d), (2, -1, c), (2, -1, d), (2, -2, c), (2, -2, d)," +
                " (1, -3, a), (1, -3, b), (1, -4, a), (1, -4, b), ...]");
    }

    private static void triples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.triples(readIntegerListWithNulls(input)), output);
    }

    private static void triples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.triples(input), output);
    }

    @Test
    public void testTriples_Iterable() {
        triples_Iterable_helper("[]", "[]");
        triples_Iterable_helper("[5]", "[(5, 5, 5)]");
        triples_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 2, 1), (1, 2, 2), (2, 1, 1), (2, 1, 2), (2, 2, 1), (2, 2, 2), (1, 1, 3)," +
                " (1, 1, 4), (1, 2, 3), (1, 2, 4), (2, 1, 3), (2, 1, 4), (2, 2, 3), (2, 2, 4), (1, 3, 1), (1, 3, 2)," +
                " (1, 4, 1), (1, 4, 2), ...]");
        triples_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 2, 1), (1, 2, 2), (2, 1, 1), (2, 1, 2), (2, 2, 1), (2, 2, 2), (1, 1, 2)," +
                " (1, 1, 4), (1, 2, 2), (1, 2, 4), (2, 1, 2), (2, 1, 4), (2, 2, 2), (2, 2, 4), (1, 2, 1), (1, 2, 2)," +
                " (1, 4, 1), (1, 4, 2), ...]");
        triples_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 2, 1), (1, 2, 2), (2, 1, 1), (2, 1, 2), (2, 2, 1), (2, 2, 2)," +
                " (1, 1, null), (1, 1, 4), (1, 2, null), (1, 2, 4), (2, 1, null), (2, 1, 4), (2, 2, null)," +
                " (2, 2, 4), (1, null, 1), (1, null, 2), (1, 4, 1), (1, 4, 2), ...]");
        triples_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0, 0), (0, 0, 1), (0, 1, 0), (0, 1, 1), (1, 0, 0), (1, 0, 1), (1, 1, 0), (1, 1, 1), (0, 0, 2)," +
                " (0, 0, 3), (0, 1, 2), (0, 1, 3), (1, 0, 2), (1, 0, 3), (1, 1, 2), (1, 1, 3), (0, 2, 0), (0, 2, 1)," +
                " (0, 3, 0), (0, 3, 1), ...]");
        triples_Iterable_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
    }

    private static <A, B, C, D> void quadruples_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        Iterable<Quadruple<A, B, C, D>> qs = EP.quadruples(as, bs, cs, ds);
        aeqit(qs, output);
        testNoRemove(qs);
    }

    @Test
    public void testQuadruples_Iterable_Iterable_Iterable_Iterable() {
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ)," +
                " (1, b, false, LT), (1, b, true, EQ), (1, b, true, LT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, true, EQ), (2, a, true, LT), (2, b, false, EQ), (2, b, false, LT), (2, b, true, EQ)," +
                " (2, b, true, LT), (1, a, false, GT), (1, a, true, GT), (1, b, false, GT), (1, b, true, GT)," +
                " (2, a, false, GT), (2, a, true, GT), (2, b, false, GT), (2, b, true, GT), (1, c, false, EQ)," +
                " (1, c, false, LT), (1, c, true, EQ), (1, c, true, LT), (2, c, false, EQ), (2, c, false, LT)," +
                " (2, c, true, EQ), (2, c, true, LT), (1, c, false, GT), (1, c, true, GT), (2, c, false, GT)," +
                " (2, c, true, GT), (3, a, false, EQ), (3, a, false, LT), (3, a, true, EQ), (3, a, true, LT)," +
                " (3, b, false, EQ), (3, b, false, LT), (3, b, true, EQ), (3, b, true, LT), (3, a, false, GT)," +
                " (3, a, true, GT), (3, b, false, GT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT)," +
                " (3, c, true, EQ), (3, c, true, LT), (3, c, false, GT), (3, c, true, GT)]"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ)," +
                " (1, b, false, LT), (1, b, true, EQ), (1, b, true, LT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, true, EQ), (2, a, true, LT), (2, b, false, EQ), (2, b, false, LT), (2, b, true, EQ)," +
                " (2, b, true, LT), (1, a, false, GT), (1, a, true, GT), (1, b, false, GT), (1, b, true, GT)," +
                " (2, a, false, GT), (2, a, true, GT), (2, b, false, GT), (2, b, true, GT), (1, c, false, EQ)," +
                " (1, c, false, LT), (1, c, true, EQ), (1, c, true, LT), (1, d, false, EQ), (1, d, false, LT)," +
                " (1, d, true, EQ), (1, d, true, LT), (2, c, false, EQ), (2, c, false, LT), (2, c, true, EQ)," +
                " (2, c, true, LT), (2, d, false, EQ), (2, d, false, LT), (2, d, true, EQ), (2, d, true, LT)," +
                " (1, c, false, GT), (1, c, true, GT), (1, d, false, GT), (1, d, true, GT), (2, c, false, GT)," +
                " (2, c, true, GT), (2, d, false, GT), (2, d, true, GT), (null, a, false, EQ), (null, a, false, LT)," +
                " (null, a, true, EQ), (null, a, true, LT), (null, b, false, EQ), (null, b, false, LT)," +
                " (null, b, true, EQ), (null, b, true, LT), (4, a, false, EQ), (4, a, false, LT), (4, a, true, EQ)," +
                " (4, a, true, LT), (4, b, false, EQ), (4, b, false, LT), (4, b, true, EQ), (4, b, true, LT)," +
                " (null, a, false, GT), (null, a, true, GT), (null, b, false, GT), (null, b, true, GT)," +
                " (4, a, false, GT), (4, a, true, GT), (4, b, false, GT), (4, b, true, GT), (null, c, false, EQ)," +
                " (null, c, false, LT), (null, c, true, EQ), (null, c, true, LT), (null, d, false, EQ)," +
                " (null, d, false, LT), (null, d, true, EQ), (null, d, true, LT), (4, c, false, EQ)," +
                " (4, c, false, LT), (4, c, true, EQ), (4, c, true, LT), (4, d, false, EQ), (4, d, false, LT)," +
                " (4, d, true, EQ), (4, d, true, LT), (null, c, false, GT), (null, c, true, GT)," +
                " (null, d, false, GT), (null, d, true, GT), (4, c, false, GT), (4, c, true, GT), (4, d, false, GT)," +
                " (4, d, true, GT)]"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                "[]"
        );
        quadruples_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
        simpleProviderHelper(EP.quadruples(EP.naturalBigIntegers(), fromString("abcd"), EP.booleans(), EP.orderings()),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, true, EQ), (0, a, true, LT), (0, b, false, EQ)," +
                " (0, b, false, LT), (0, b, true, EQ), (0, b, true, LT), (1, a, false, EQ), (1, a, false, LT)," +
                " (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ), (1, b, false, LT), (1, b, true, EQ)," +
                " (1, b, true, LT), (0, a, false, GT), (0, a, true, GT), (0, b, false, GT), (0, b, true, GT), ...]");
        simpleProviderHelper(EP.quadruples(fromString("abcd"), EP.booleans(), EP.naturalBigIntegers(), EP.orderings()),
                "[(a, false, 0, EQ), (a, false, 0, LT), (a, false, 1, EQ), (a, false, 1, LT), (a, true, 0, EQ)," +
                " (a, true, 0, LT), (a, true, 1, EQ), (a, true, 1, LT), (b, false, 0, EQ), (b, false, 0, LT)," +
                " (b, false, 1, EQ), (b, false, 1, LT), (b, true, 0, EQ), (b, true, 0, LT), (b, true, 1, EQ)," +
                " (b, true, 1, LT), (a, false, 0, GT), (a, false, 1, GT), (a, true, 0, GT), (a, true, 1, GT), ...]");
        simpleProviderHelper(
                EP.quadruples(EP.positiveBigIntegers(), EP.negativeBigIntegers(), EP.characters(), EP.strings()),
                "[(1, -1, a, ), (1, -1, a, a), (1, -1, b, ), (1, -1, b, a), (1, -2, a, ), (1, -2, a, a)," +
                " (1, -2, b, ), (1, -2, b, a), (2, -1, a, ), (2, -1, a, a), (2, -1, b, ), (2, -1, b, a)," +
                " (2, -2, a, ), (2, -2, a, a), (2, -2, b, ), (2, -2, b, a), (1, -1, a, aa), (1, -1, a, b)," +
                " (1, -1, b, aa), (1, -1, b, b), ...]");
    }

    private static void quadruples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.quadruples(readIntegerListWithNulls(input)), output);
    }

    private static void quadruples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.quadruples(input), output);
    }

    @Test
    public void testQuadruples_Iterable() {
        quadruples_Iterable_helper("[]", "[]");
        quadruples_Iterable_helper("[5]", "[(5, 5, 5, 5)]");
        quadruples_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 1), (1, 1, 2, 2), (1, 2, 1, 1), (1, 2, 1, 2), (1, 2, 2, 1)," +
                " (1, 2, 2, 2), (2, 1, 1, 1), (2, 1, 1, 2), (2, 1, 2, 1), (2, 1, 2, 2), (2, 2, 1, 1), (2, 2, 1, 2)," +
                " (2, 2, 2, 1), (2, 2, 2, 2), (1, 1, 1, 3), (1, 1, 1, 4), (1, 1, 2, 3), (1, 1, 2, 4), ...]");
        quadruples_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 1), (1, 1, 2, 2), (1, 2, 1, 1), (1, 2, 1, 2), (1, 2, 2, 1)," +
                " (1, 2, 2, 2), (2, 1, 1, 1), (2, 1, 1, 2), (2, 1, 2, 1), (2, 1, 2, 2), (2, 2, 1, 1), (2, 2, 1, 2)," +
                " (2, 2, 2, 1), (2, 2, 2, 2), (1, 1, 1, 2), (1, 1, 1, 4), (1, 1, 2, 2), (1, 1, 2, 4), ...]");
        quadruples_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 1), (1, 1, 2, 2), (1, 2, 1, 1), (1, 2, 1, 2), (1, 2, 2, 1)," +
                " (1, 2, 2, 2), (2, 1, 1, 1), (2, 1, 1, 2), (2, 1, 2, 1), (2, 1, 2, 2), (2, 2, 1, 1), (2, 2, 1, 2)," +
                " (2, 2, 2, 1), (2, 2, 2, 2), (1, 1, 1, null), (1, 1, 1, 4), (1, 1, 2, null), (1, 1, 2, 4), ...]");
        quadruples_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0), (0, 0, 0, 1), (0, 0, 1, 0), (0, 0, 1, 1), (0, 1, 0, 0), (0, 1, 0, 1), (0, 1, 1, 0)," +
                " (0, 1, 1, 1), (1, 0, 0, 0), (1, 0, 0, 1), (1, 0, 1, 0), (1, 0, 1, 1), (1, 1, 0, 0), (1, 1, 0, 1)," +
                " (1, 1, 1, 0), (1, 1, 1, 1), (0, 0, 0, 2), (0, 0, 0, 3), (0, 0, 1, 2), (0, 0, 1, 3), ...]");
        quadruples_Iterable_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
    }

    private static <A, B, C, D, E> void quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        Iterable<Quintuple<A, B, C, D, E>> qs = EP.quintuples(as, bs, cs, ds, es);
        aeqit(qs, output);
        testNoRemove(qs);
    }

    @Test
    public void testQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable() {
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, true, EQ, yes), (1, a, true, EQ, no), (1, a, true, LT, yes), (1, a, true, LT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, true, EQ, yes), (1, b, true, EQ, no), (1, b, true, LT, yes), (1, b, true, LT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, true, EQ, yes), (2, a, true, EQ, no), (2, a, true, LT, yes), (2, a, true, LT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, true, EQ, yes), (2, b, true, EQ, no), (2, b, true, LT, yes), (2, b, true, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, true, EQ, yes), (1, c, true, EQ, no), (1, c, true, LT, yes), (1, c, true, LT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, true, EQ, yes), (2, c, true, EQ, no), (2, c, true, LT, yes), (2, c, true, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, true, EQ, yes), (3, a, true, EQ, no), (3, a, true, LT, yes), (3, a, true, LT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, true, EQ, yes), (3, b, true, EQ, no), (3, b, true, LT, yes), (3, b, true, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, true, EQ, yes), (3, c, true, EQ, no), (3, c, true, LT, yes), (3, c, true, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, true, EQ, yes), (1, a, true, EQ, no), (1, a, true, LT, yes), (1, a, true, LT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, true, EQ, yes), (1, b, true, EQ, no), (1, b, true, LT, yes), (1, b, true, LT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, true, EQ, yes), (2, a, true, EQ, no), (2, a, true, LT, yes), (2, a, true, LT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, true, EQ, yes), (2, b, true, EQ, no), (2, b, true, LT, yes), (2, b, true, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, true, EQ, yes), (1, c, true, EQ, no), (1, c, true, LT, yes), (1, c, true, LT, no)," +
                " (1, d, false, EQ, yes), (1, d, false, EQ, no), (1, d, false, LT, yes), (1, d, false, LT, no)," +
                " (1, d, true, EQ, yes), (1, d, true, EQ, no), (1, d, true, LT, yes), (1, d, true, LT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, true, EQ, yes), (2, c, true, EQ, no), (2, c, true, LT, yes), (2, c, true, LT, no)," +
                " (2, d, false, EQ, yes), (2, d, false, EQ, no), (2, d, false, LT, yes), (2, d, false, LT, no)," +
                " (2, d, true, EQ, yes), (2, d, true, EQ, no), (2, d, true, LT, yes), (2, d, true, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (1, d, false, GT, yes), (1, d, false, GT, no), (1, d, true, GT, yes), (1, d, true, GT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (2, d, false, GT, yes), (2, d, false, GT, no), (2, d, true, GT, yes), (2, d, true, GT, no)," +
                " (null, a, false, EQ, yes), (null, a, false, EQ, no), (null, a, false, LT, yes)," +
                " (null, a, false, LT, no), (null, a, true, EQ, yes), (null, a, true, EQ, no)," +
                " (null, a, true, LT, yes), (null, a, true, LT, no), (null, b, false, EQ, yes)," +
                " (null, b, false, EQ, no), (null, b, false, LT, yes), (null, b, false, LT, no)," +
                " (null, b, true, EQ, yes), (null, b, true, EQ, no), (null, b, true, LT, yes)," +
                " (null, b, true, LT, no), (4, a, false, EQ, yes), (4, a, false, EQ, no), (4, a, false, LT, yes)," +
                " (4, a, false, LT, no), (4, a, true, EQ, yes), (4, a, true, EQ, no), (4, a, true, LT, yes)," +
                " (4, a, true, LT, no), (4, b, false, EQ, yes), (4, b, false, EQ, no), (4, b, false, LT, yes)," +
                " (4, b, false, LT, no), (4, b, true, EQ, yes), (4, b, true, EQ, no), (4, b, true, LT, yes)," +
                " (4, b, true, LT, no), (null, a, false, GT, yes), (null, a, false, GT, no)," +
                " (null, a, true, GT, yes), (null, a, true, GT, no), (null, b, false, GT, yes)," +
                " (null, b, false, GT, no), (null, b, true, GT, yes), (null, b, true, GT, no)," +
                " (4, a, false, GT, yes), (4, a, false, GT, no), (4, a, true, GT, yes), (4, a, true, GT, no)," +
                " (4, b, false, GT, yes), (4, b, false, GT, no), (4, b, true, GT, yes), (4, b, true, GT, no)," +
                " (null, c, false, EQ, yes), (null, c, false, EQ, no), (null, c, false, LT, yes)," +
                " (null, c, false, LT, no), (null, c, true, EQ, yes), (null, c, true, EQ, no)," +
                " (null, c, true, LT, yes), (null, c, true, LT, no), (null, d, false, EQ, yes)," +
                " (null, d, false, EQ, no), (null, d, false, LT, yes), (null, d, false, LT, no)," +
                " (null, d, true, EQ, yes), (null, d, true, EQ, no), (null, d, true, LT, yes)," +
                " (null, d, true, LT, no), (4, c, false, EQ, yes), (4, c, false, EQ, no), (4, c, false, LT, yes)," +
                " (4, c, false, LT, no), (4, c, true, EQ, yes), (4, c, true, EQ, no), (4, c, true, LT, yes)," +
                " (4, c, true, LT, no), (4, d, false, EQ, yes), (4, d, false, EQ, no), (4, d, false, LT, yes)," +
                " (4, d, false, LT, no), (4, d, true, EQ, yes), (4, d, true, EQ, no), (4, d, true, LT, yes)," +
                " (4, d, true, LT, no), (null, c, false, GT, yes), (null, c, false, GT, no)," +
                " (null, c, true, GT, yes), (null, c, true, GT, no), (null, d, false, GT, yes)," +
                " (null, d, false, GT, no), (null, d, true, GT, yes), (null, d, true, GT, no)," +
                " (4, c, false, GT, yes), (4, c, false, GT, no), (4, c, true, GT, yes), (4, c, true, GT, no)," +
                " (4, d, false, GT, yes), (4, d, false, GT, no), (4, d, true, GT, yes), (4, d, true, GT, no)]"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "[]"
        );
        quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
        simpleProviderHelper(
                EP.quintuples(
                        EP.naturalBigIntegers(),
                        fromString("abcd"),
                        EP.booleans(),
                        EP.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, true, EQ, yes), (0, a, true, EQ, no), (0, a, true, LT, yes), (0, a, true, LT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, true, EQ, yes), (0, b, true, EQ, no), (0, b, true, LT, yes), (0, b, true, LT, no)," +
                " (1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no), ...]"
        );
        simpleProviderHelper(
                EP.quintuples(
                        fromString("abcd"),
                        EP.booleans(),
                        EP.naturalBigIntegers(),
                        EP.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "[(a, false, 0, EQ, yes), (a, false, 0, EQ, no), (a, false, 0, LT, yes), (a, false, 0, LT, no)," +
                " (a, false, 1, EQ, yes), (a, false, 1, EQ, no), (a, false, 1, LT, yes), (a, false, 1, LT, no)," +
                " (a, true, 0, EQ, yes), (a, true, 0, EQ, no), (a, true, 0, LT, yes), (a, true, 0, LT, no)," +
                " (a, true, 1, EQ, yes), (a, true, 1, EQ, no), (a, true, 1, LT, yes), (a, true, 1, LT, no)," +
                " (b, false, 0, EQ, yes), (b, false, 0, EQ, no), (b, false, 0, LT, yes), (b, false, 0, LT, no), ...]"
        );
        simpleProviderHelper(
                EP.quintuples(
                        EP.positiveBigIntegers(),
                        EP.negativeBigIntegers(),
                        EP.characters(),
                        EP.strings(),
                        EP.floats()
                ),
                "[(1, -1, a, , NaN), (1, -1, a, , Infinity), (1, -1, a, a, NaN), (1, -1, a, a, Infinity)," +
                " (1, -1, b, , NaN), (1, -1, b, , Infinity), (1, -1, b, a, NaN), (1, -1, b, a, Infinity)," +
                " (1, -2, a, , NaN), (1, -2, a, , Infinity), (1, -2, a, a, NaN), (1, -2, a, a, Infinity)," +
                " (1, -2, b, , NaN), (1, -2, b, , Infinity), (1, -2, b, a, NaN), (1, -2, b, a, Infinity)," +
                " (2, -1, a, , NaN), (2, -1, a, , Infinity), (2, -1, a, a, NaN), (2, -1, a, a, Infinity), ...]"
        );
    }

    private static void quintuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.quintuples(readIntegerListWithNulls(input)), output);
    }

    private static void quintuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.quintuples(input), output);
    }

    @Test
    public void testQuintuples_Iterable() {
        quintuples_Iterable_helper("[]", "[]");
        quintuples_Iterable_helper("[5]", "[(5, 5, 5, 5, 5)]");
        quintuples_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 1), (1, 1, 1, 2, 2), (1, 1, 2, 1, 1)," +
                " (1, 1, 2, 1, 2), (1, 1, 2, 2, 1), (1, 1, 2, 2, 2), (1, 2, 1, 1, 1), (1, 2, 1, 1, 2)," +
                " (1, 2, 1, 2, 1), (1, 2, 1, 2, 2), (1, 2, 2, 1, 1), (1, 2, 2, 1, 2), (1, 2, 2, 2, 1)," +
                " (1, 2, 2, 2, 2), (2, 1, 1, 1, 1), (2, 1, 1, 1, 2), (2, 1, 1, 2, 1), (2, 1, 1, 2, 2), ...]");
        quintuples_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 1), (1, 1, 1, 2, 2), (1, 1, 2, 1, 1)," +
                " (1, 1, 2, 1, 2), (1, 1, 2, 2, 1), (1, 1, 2, 2, 2), (1, 2, 1, 1, 1), (1, 2, 1, 1, 2)," +
                " (1, 2, 1, 2, 1), (1, 2, 1, 2, 2), (1, 2, 2, 1, 1), (1, 2, 2, 1, 2), (1, 2, 2, 2, 1)," +
                " (1, 2, 2, 2, 2), (2, 1, 1, 1, 1), (2, 1, 1, 1, 2), (2, 1, 1, 2, 1), (2, 1, 1, 2, 2), ...]");
        quintuples_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 1), (1, 1, 1, 2, 2), (1, 1, 2, 1, 1)," +
                " (1, 1, 2, 1, 2), (1, 1, 2, 2, 1), (1, 1, 2, 2, 2), (1, 2, 1, 1, 1), (1, 2, 1, 1, 2)," +
                " (1, 2, 1, 2, 1), (1, 2, 1, 2, 2), (1, 2, 2, 1, 1), (1, 2, 2, 1, 2), (1, 2, 2, 2, 1)," +
                " (1, 2, 2, 2, 2), (2, 1, 1, 1, 1), (2, 1, 1, 1, 2), (2, 1, 1, 2, 1), (2, 1, 1, 2, 2), ...]");
        quintuples_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0), (0, 0, 0, 0, 1), (0, 0, 0, 1, 0), (0, 0, 0, 1, 1), (0, 0, 1, 0, 0)," +
                " (0, 0, 1, 0, 1), (0, 0, 1, 1, 0), (0, 0, 1, 1, 1), (0, 1, 0, 0, 0), (0, 1, 0, 0, 1)," +
                " (0, 1, 0, 1, 0), (0, 1, 0, 1, 1), (0, 1, 1, 0, 0), (0, 1, 1, 0, 1), (0, 1, 1, 1, 0)," +
                " (0, 1, 1, 1, 1), (1, 0, 0, 0, 0), (1, 0, 0, 0, 1), (1, 0, 0, 1, 0), (1, 0, 0, 1, 1), ...]");
        quintuples_Iterable_helper(repeat(1),
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), ...]");
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
        Iterable<Sextuple<A, B, C, D, E, F>> ss = EP.sextuples(as, bs, cs, ds, es, fs);
        aeqit(ss, output);
        testNoRemove(ss);
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
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, true, EQ, yes, Infinity)," +
                " (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity), (1, a, true, EQ, no, NaN)," +
                " (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN), (1, a, true, LT, no, Infinity)," +
                " (1, a, true, LT, no, NaN), (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN)," +
                " (1, b, false, EQ, no, Infinity), (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity)," +
                " (1, b, false, LT, yes, NaN), (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (2, a, false, EQ, yes, Infinity)," +
                " (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity), (2, a, false, EQ, no, NaN)," +
                " (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN), (2, a, false, LT, no, Infinity)," +
                " (2, a, false, LT, no, NaN), (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN)," +
                " (2, a, true, EQ, no, Infinity), (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity)," +
                " (2, a, true, LT, yes, NaN), (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, true, EQ, yes, Infinity)," +
                " (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity), (2, b, true, EQ, no, NaN)," +
                " (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN), (2, b, true, LT, no, Infinity)," +
                " (2, b, true, LT, no, NaN), (1, a, false, GT, yes, Infinity), (1, a, false, GT, yes, NaN)," +
                " (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, GT, yes, Infinity), (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity)," +
                " (1, b, false, GT, no, NaN), (1, b, true, GT, yes, Infinity), (1, b, true, GT, yes, NaN)," +
                " (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, GT, yes, Infinity), (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity)," +
                " (2, a, true, GT, no, NaN), (2, b, false, GT, yes, Infinity), (2, b, false, GT, yes, NaN)," +
                " (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, true, EQ, yes, Infinity)," +
                " (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity), (1, c, true, EQ, no, NaN)," +
                " (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN), (1, c, true, LT, no, Infinity)," +
                " (1, c, true, LT, no, NaN), (2, c, false, EQ, yes, Infinity), (2, c, false, EQ, yes, NaN)," +
                " (2, c, false, EQ, no, Infinity), (2, c, false, EQ, no, NaN), (2, c, false, LT, yes, Infinity)," +
                " (2, c, false, LT, yes, NaN), (2, c, false, LT, no, Infinity), (2, c, false, LT, no, NaN)," +
                " (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN), (2, c, true, EQ, no, Infinity)," +
                " (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity), (2, c, true, LT, yes, NaN)," +
                " (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, GT, yes, Infinity), (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity)," +
                " (1, c, true, GT, no, NaN), (2, c, false, GT, yes, Infinity), (2, c, false, GT, yes, NaN)," +
                " (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN), (2, c, true, GT, yes, Infinity)," +
                " (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity), (2, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, true, EQ, yes, Infinity)," +
                " (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity), (3, a, true, EQ, no, NaN)," +
                " (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN), (3, a, true, LT, no, Infinity)," +
                " (3, a, true, LT, no, NaN), (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN)," +
                " (3, b, false, EQ, no, Infinity), (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity)," +
                " (3, b, false, LT, yes, NaN), (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, GT, yes, Infinity), (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity)," +
                " (3, a, true, GT, no, NaN), (3, b, false, GT, yes, Infinity), (3, b, false, GT, yes, NaN)," +
                " (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, true, EQ, yes, Infinity)," +
                " (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity), (3, c, true, EQ, no, NaN)," +
                " (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN), (3, c, true, LT, no, Infinity)," +
                " (3, c, true, LT, no, NaN), (3, c, false, GT, yes, Infinity), (3, c, false, GT, yes, NaN)," +
                " (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, true, EQ, yes, Infinity)," +
                " (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity), (1, a, true, EQ, no, NaN)," +
                " (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN), (1, a, true, LT, no, Infinity)," +
                " (1, a, true, LT, no, NaN), (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN)," +
                " (1, b, false, EQ, no, Infinity), (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity)," +
                " (1, b, false, LT, yes, NaN), (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (2, a, false, EQ, yes, Infinity)," +
                " (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity), (2, a, false, EQ, no, NaN)," +
                " (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN), (2, a, false, LT, no, Infinity)," +
                " (2, a, false, LT, no, NaN), (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN)," +
                " (2, a, true, EQ, no, Infinity), (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity)," +
                " (2, a, true, LT, yes, NaN), (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, true, EQ, yes, Infinity)," +
                " (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity), (2, b, true, EQ, no, NaN)," +
                " (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN), (2, b, true, LT, no, Infinity)," +
                " (2, b, true, LT, no, NaN), (1, a, false, GT, yes, Infinity), (1, a, false, GT, yes, NaN)," +
                " (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, GT, yes, Infinity), (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity)," +
                " (1, b, false, GT, no, NaN), (1, b, true, GT, yes, Infinity), (1, b, true, GT, yes, NaN)," +
                " (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, GT, yes, Infinity), (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity)," +
                " (2, a, true, GT, no, NaN), (2, b, false, GT, yes, Infinity), (2, b, false, GT, yes, NaN)," +
                " (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, true, EQ, yes, Infinity)," +
                " (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity), (1, c, true, EQ, no, NaN)," +
                " (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN), (1, c, true, LT, no, Infinity)," +
                " (1, c, true, LT, no, NaN), (1, d, false, EQ, yes, Infinity), (1, d, false, EQ, yes, NaN)," +
                " (1, d, false, EQ, no, Infinity), (1, d, false, EQ, no, NaN), (1, d, false, LT, yes, Infinity)," +
                " (1, d, false, LT, yes, NaN), (1, d, false, LT, no, Infinity), (1, d, false, LT, no, NaN)," +
                " (1, d, true, EQ, yes, Infinity), (1, d, true, EQ, yes, NaN), (1, d, true, EQ, no, Infinity)," +
                " (1, d, true, EQ, no, NaN), (1, d, true, LT, yes, Infinity), (1, d, true, LT, yes, NaN)," +
                " (1, d, true, LT, no, Infinity), (1, d, true, LT, no, NaN), (2, c, false, EQ, yes, Infinity)," +
                " (2, c, false, EQ, yes, NaN), (2, c, false, EQ, no, Infinity), (2, c, false, EQ, no, NaN)," +
                " (2, c, false, LT, yes, Infinity), (2, c, false, LT, yes, NaN), (2, c, false, LT, no, Infinity)," +
                " (2, c, false, LT, no, NaN), (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN)," +
                " (2, c, true, EQ, no, Infinity), (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity)," +
                " (2, c, true, LT, yes, NaN), (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN)," +
                " (2, d, false, EQ, yes, Infinity), (2, d, false, EQ, yes, NaN), (2, d, false, EQ, no, Infinity)," +
                " (2, d, false, EQ, no, NaN), (2, d, false, LT, yes, Infinity), (2, d, false, LT, yes, NaN)," +
                " (2, d, false, LT, no, Infinity), (2, d, false, LT, no, NaN), (2, d, true, EQ, yes, Infinity)," +
                " (2, d, true, EQ, yes, NaN), (2, d, true, EQ, no, Infinity), (2, d, true, EQ, no, NaN)," +
                " (2, d, true, LT, yes, Infinity), (2, d, true, LT, yes, NaN), (2, d, true, LT, no, Infinity)," +
                " (2, d, true, LT, no, NaN), (1, c, false, GT, yes, Infinity), (1, c, false, GT, yes, NaN)," +
                " (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (1, d, false, GT, yes, Infinity), (1, d, false, GT, yes, NaN), (1, d, false, GT, no, Infinity)," +
                " (1, d, false, GT, no, NaN), (1, d, true, GT, yes, Infinity), (1, d, true, GT, yes, NaN)," +
                " (1, d, true, GT, no, Infinity), (1, d, true, GT, no, NaN), (2, c, false, GT, yes, Infinity)," +
                " (2, c, false, GT, yes, NaN), (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN)," +
                " (2, c, true, GT, yes, Infinity), (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity)," +
                " (2, c, true, GT, no, NaN), (2, d, false, GT, yes, Infinity), (2, d, false, GT, yes, NaN)," +
                " (2, d, false, GT, no, Infinity), (2, d, false, GT, no, NaN), (2, d, true, GT, yes, Infinity)," +
                " (2, d, true, GT, yes, NaN), (2, d, true, GT, no, Infinity), (2, d, true, GT, no, NaN)," +
                " (null, a, false, EQ, yes, Infinity), (null, a, false, EQ, yes, NaN)," +
                " (null, a, false, EQ, no, Infinity), (null, a, false, EQ, no, NaN)," +
                " (null, a, false, LT, yes, Infinity), (null, a, false, LT, yes, NaN)," +
                " (null, a, false, LT, no, Infinity), (null, a, false, LT, no, NaN)," +
                " (null, a, true, EQ, yes, Infinity), (null, a, true, EQ, yes, NaN)," +
                " (null, a, true, EQ, no, Infinity), (null, a, true, EQ, no, NaN)," +
                " (null, a, true, LT, yes, Infinity), (null, a, true, LT, yes, NaN)," +
                " (null, a, true, LT, no, Infinity), (null, a, true, LT, no, NaN)," +
                " (null, b, false, EQ, yes, Infinity), (null, b, false, EQ, yes, NaN)," +
                " (null, b, false, EQ, no, Infinity), (null, b, false, EQ, no, NaN)," +
                " (null, b, false, LT, yes, Infinity), (null, b, false, LT, yes, NaN)," +
                " (null, b, false, LT, no, Infinity), (null, b, false, LT, no, NaN)," +
                " (null, b, true, EQ, yes, Infinity), (null, b, true, EQ, yes, NaN)," +
                " (null, b, true, EQ, no, Infinity), (null, b, true, EQ, no, NaN)," +
                " (null, b, true, LT, yes, Infinity), (null, b, true, LT, yes, NaN)," +
                " (null, b, true, LT, no, Infinity), (null, b, true, LT, no, NaN), (4, a, false, EQ, yes, Infinity)," +
                " (4, a, false, EQ, yes, NaN), (4, a, false, EQ, no, Infinity), (4, a, false, EQ, no, NaN)," +
                " (4, a, false, LT, yes, Infinity), (4, a, false, LT, yes, NaN), (4, a, false, LT, no, Infinity)," +
                " (4, a, false, LT, no, NaN), (4, a, true, EQ, yes, Infinity), (4, a, true, EQ, yes, NaN)," +
                " (4, a, true, EQ, no, Infinity), (4, a, true, EQ, no, NaN), (4, a, true, LT, yes, Infinity)," +
                " (4, a, true, LT, yes, NaN), (4, a, true, LT, no, Infinity), (4, a, true, LT, no, NaN)," +
                " (4, b, false, EQ, yes, Infinity), (4, b, false, EQ, yes, NaN), (4, b, false, EQ, no, Infinity)," +
                " (4, b, false, EQ, no, NaN), (4, b, false, LT, yes, Infinity), (4, b, false, LT, yes, NaN)," +
                " (4, b, false, LT, no, Infinity), (4, b, false, LT, no, NaN), (4, b, true, EQ, yes, Infinity)," +
                " (4, b, true, EQ, yes, NaN), (4, b, true, EQ, no, Infinity), (4, b, true, EQ, no, NaN)," +
                " (4, b, true, LT, yes, Infinity), (4, b, true, LT, yes, NaN), (4, b, true, LT, no, Infinity)," +
                " (4, b, true, LT, no, NaN), (null, a, false, GT, yes, Infinity), (null, a, false, GT, yes, NaN)," +
                " (null, a, false, GT, no, Infinity), (null, a, false, GT, no, NaN)," +
                " (null, a, true, GT, yes, Infinity), (null, a, true, GT, yes, NaN)," +
                " (null, a, true, GT, no, Infinity), (null, a, true, GT, no, NaN)," +
                " (null, b, false, GT, yes, Infinity), (null, b, false, GT, yes, NaN)," +
                " (null, b, false, GT, no, Infinity), (null, b, false, GT, no, NaN)," +
                " (null, b, true, GT, yes, Infinity), (null, b, true, GT, yes, NaN)," +
                " (null, b, true, GT, no, Infinity), (null, b, true, GT, no, NaN), (4, a, false, GT, yes, Infinity)," +
                " (4, a, false, GT, yes, NaN), (4, a, false, GT, no, Infinity), (4, a, false, GT, no, NaN)," +
                " (4, a, true, GT, yes, Infinity), (4, a, true, GT, yes, NaN), (4, a, true, GT, no, Infinity)," +
                " (4, a, true, GT, no, NaN), (4, b, false, GT, yes, Infinity), (4, b, false, GT, yes, NaN)," +
                " (4, b, false, GT, no, Infinity), (4, b, false, GT, no, NaN), (4, b, true, GT, yes, Infinity)," +
                " (4, b, true, GT, yes, NaN), (4, b, true, GT, no, Infinity), (4, b, true, GT, no, NaN)," +
                " (null, c, false, EQ, yes, Infinity), (null, c, false, EQ, yes, NaN)," +
                " (null, c, false, EQ, no, Infinity), (null, c, false, EQ, no, NaN)," +
                " (null, c, false, LT, yes, Infinity), (null, c, false, LT, yes, NaN)," +
                " (null, c, false, LT, no, Infinity), (null, c, false, LT, no, NaN)," +
                " (null, c, true, EQ, yes, Infinity), (null, c, true, EQ, yes, NaN)," +
                " (null, c, true, EQ, no, Infinity), (null, c, true, EQ, no, NaN)," +
                " (null, c, true, LT, yes, Infinity), (null, c, true, LT, yes, NaN)," +
                " (null, c, true, LT, no, Infinity), (null, c, true, LT, no, NaN)," +
                " (null, d, false, EQ, yes, Infinity), (null, d, false, EQ, yes, NaN)," +
                " (null, d, false, EQ, no, Infinity), (null, d, false, EQ, no, NaN)," +
                " (null, d, false, LT, yes, Infinity), (null, d, false, LT, yes, NaN)," +
                " (null, d, false, LT, no, Infinity), (null, d, false, LT, no, NaN)," +
                " (null, d, true, EQ, yes, Infinity), (null, d, true, EQ, yes, NaN)," +
                " (null, d, true, EQ, no, Infinity), (null, d, true, EQ, no, NaN)," +
                " (null, d, true, LT, yes, Infinity), (null, d, true, LT, yes, NaN)," +
                " (null, d, true, LT, no, Infinity), (null, d, true, LT, no, NaN), (4, c, false, EQ, yes, Infinity)," +
                " (4, c, false, EQ, yes, NaN), (4, c, false, EQ, no, Infinity), (4, c, false, EQ, no, NaN)," +
                " (4, c, false, LT, yes, Infinity), (4, c, false, LT, yes, NaN), (4, c, false, LT, no, Infinity)," +
                " (4, c, false, LT, no, NaN), (4, c, true, EQ, yes, Infinity), (4, c, true, EQ, yes, NaN)," +
                " (4, c, true, EQ, no, Infinity), (4, c, true, EQ, no, NaN), (4, c, true, LT, yes, Infinity)," +
                " (4, c, true, LT, yes, NaN), (4, c, true, LT, no, Infinity), (4, c, true, LT, no, NaN)," +
                " (4, d, false, EQ, yes, Infinity), (4, d, false, EQ, yes, NaN), (4, d, false, EQ, no, Infinity)," +
                " (4, d, false, EQ, no, NaN), (4, d, false, LT, yes, Infinity), (4, d, false, LT, yes, NaN)," +
                " (4, d, false, LT, no, Infinity), (4, d, false, LT, no, NaN), (4, d, true, EQ, yes, Infinity)," +
                " (4, d, true, EQ, yes, NaN), (4, d, true, EQ, no, Infinity), (4, d, true, EQ, no, NaN)," +
                " (4, d, true, LT, yes, Infinity), (4, d, true, LT, yes, NaN), (4, d, true, LT, no, Infinity)," +
                " (4, d, true, LT, no, NaN), (null, c, false, GT, yes, Infinity), (null, c, false, GT, yes, NaN)," +
                " (null, c, false, GT, no, Infinity), (null, c, false, GT, no, NaN)," +
                " (null, c, true, GT, yes, Infinity), (null, c, true, GT, yes, NaN)," +
                " (null, c, true, GT, no, Infinity), (null, c, true, GT, no, NaN)," +
                " (null, d, false, GT, yes, Infinity), (null, d, false, GT, yes, NaN)," +
                " (null, d, false, GT, no, Infinity), (null, d, false, GT, no, NaN)," +
                " (null, d, true, GT, yes, Infinity), (null, d, true, GT, yes, NaN)," +
                " (null, d, true, GT, no, Infinity), (null, d, true, GT, no, NaN), (4, c, false, GT, yes, Infinity)," +
                " (4, c, false, GT, yes, NaN), (4, c, false, GT, no, Infinity), (4, c, false, GT, no, NaN)," +
                " (4, c, true, GT, yes, Infinity), (4, c, true, GT, yes, NaN), (4, c, true, GT, no, Infinity)," +
                " (4, c, true, GT, no, NaN), (4, d, false, GT, yes, Infinity), (4, d, false, GT, yes, NaN)," +
                " (4, d, false, GT, no, Infinity), (4, d, false, GT, no, NaN), (4, d, true, GT, yes, Infinity)," +
                " (4, d, true, GT, yes, NaN), (4, d, true, GT, no, Infinity), (4, d, true, GT, no, NaN)]"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "[]"
        );
        sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
        simpleProviderHelper(
                EP.sextuples(
                        EP.naturalBigIntegers(),
                        fromString("abcd"),
                        EP.booleans(),
                        EP.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "[(0, a, false, EQ, yes, Infinity), (0, a, false, EQ, yes, NaN), (0, a, false, EQ, no, Infinity)," +
                " (0, a, false, EQ, no, NaN), (0, a, false, LT, yes, Infinity), (0, a, false, LT, yes, NaN)," +
                " (0, a, false, LT, no, Infinity), (0, a, false, LT, no, NaN), (0, a, true, EQ, yes, Infinity)," +
                " (0, a, true, EQ, yes, NaN), (0, a, true, EQ, no, Infinity), (0, a, true, EQ, no, NaN)," +
                " (0, a, true, LT, yes, Infinity), (0, a, true, LT, yes, NaN), (0, a, true, LT, no, Infinity)," +
                " (0, a, true, LT, no, NaN), (0, b, false, EQ, yes, Infinity), (0, b, false, EQ, yes, NaN)," +
                " (0, b, false, EQ, no, Infinity), (0, b, false, EQ, no, NaN), ...]"
        );
        simpleProviderHelper(
                EP.sextuples(
                        fromString("abcd"),
                        EP.booleans(),
                        EP.naturalBigIntegers(),
                        EP.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "[(a, false, 0, EQ, yes, Infinity), (a, false, 0, EQ, yes, NaN), (a, false, 0, EQ, no, Infinity)," +
                " (a, false, 0, EQ, no, NaN), (a, false, 0, LT, yes, Infinity), (a, false, 0, LT, yes, NaN)," +
                " (a, false, 0, LT, no, Infinity), (a, false, 0, LT, no, NaN), (a, false, 1, EQ, yes, Infinity)," +
                " (a, false, 1, EQ, yes, NaN), (a, false, 1, EQ, no, Infinity), (a, false, 1, EQ, no, NaN)," +
                " (a, false, 1, LT, yes, Infinity), (a, false, 1, LT, yes, NaN), (a, false, 1, LT, no, Infinity)," +
                " (a, false, 1, LT, no, NaN), (a, true, 0, EQ, yes, Infinity), (a, true, 0, EQ, yes, NaN)," +
                " (a, true, 0, EQ, no, Infinity), (a, true, 0, EQ, no, NaN), ...]"
        );
        simpleProviderHelper(
                EP.sextuples(
                        EP.positiveBigIntegers(),
                        EP.negativeBigIntegers(),
                        EP.characters(),
                        EP.strings(),
                        EP.floats(),
                        EP.lists(EP.integers())
                ),
                "[(1, -1, a, , NaN, []), (1, -1, a, , NaN, [0]), (1, -1, a, , Infinity, [])," +
                " (1, -1, a, , Infinity, [0]), (1, -1, a, a, NaN, []), (1, -1, a, a, NaN, [0])," +
                " (1, -1, a, a, Infinity, []), (1, -1, a, a, Infinity, [0]), (1, -1, b, , NaN, [])," +
                " (1, -1, b, , NaN, [0]), (1, -1, b, , Infinity, []), (1, -1, b, , Infinity, [0])," +
                " (1, -1, b, a, NaN, []), (1, -1, b, a, NaN, [0]), (1, -1, b, a, Infinity, [])," +
                " (1, -1, b, a, Infinity, [0]), (1, -2, a, , NaN, []), (1, -2, a, , NaN, [0])," +
                " (1, -2, a, , Infinity, []), (1, -2, a, , Infinity, [0]), ...]"
        );
    }

    private static void sextuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.sextuples(readIntegerListWithNulls(input)), output);
    }

    private static void sextuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.sextuples(input), output);
    }

    @Test
    public void testSextuples_Iterable() {
        sextuples_Iterable_helper("[]", "[]");
        sextuples_Iterable_helper("[5]", "[(5, 5, 5, 5, 5, 5)]");
        sextuples_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 1, 1), (1, 1, 1, 2, 1, 2), (1, 1, 1, 2, 2, 1), (1, 1, 1, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1), (1, 1, 2, 1, 1, 2), (1, 1, 2, 1, 2, 1), (1, 1, 2, 1, 2, 2)," +
                " (1, 1, 2, 2, 1, 1), (1, 1, 2, 2, 1, 2), (1, 1, 2, 2, 2, 1), (1, 1, 2, 2, 2, 2)," +
                " (1, 2, 1, 1, 1, 1), (1, 2, 1, 1, 1, 2), (1, 2, 1, 1, 2, 1), (1, 2, 1, 1, 2, 2), ...]");
        sextuples_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 1, 1), (1, 1, 1, 2, 1, 2), (1, 1, 1, 2, 2, 1), (1, 1, 1, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1), (1, 1, 2, 1, 1, 2), (1, 1, 2, 1, 2, 1), (1, 1, 2, 1, 2, 2)," +
                " (1, 1, 2, 2, 1, 1), (1, 1, 2, 2, 1, 2), (1, 1, 2, 2, 2, 1), (1, 1, 2, 2, 2, 2)," +
                " (1, 2, 1, 1, 1, 1), (1, 2, 1, 1, 1, 2), (1, 2, 1, 1, 2, 1), (1, 2, 1, 1, 2, 2), ...]");
        sextuples_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 1, 1), (1, 1, 1, 2, 1, 2), (1, 1, 1, 2, 2, 1), (1, 1, 1, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1), (1, 1, 2, 1, 1, 2), (1, 1, 2, 1, 2, 1), (1, 1, 2, 1, 2, 2)," +
                " (1, 1, 2, 2, 1, 1), (1, 1, 2, 2, 1, 2), (1, 1, 2, 2, 2, 1), (1, 1, 2, 2, 2, 2)," +
                " (1, 2, 1, 1, 1, 1), (1, 2, 1, 1, 1, 2), (1, 2, 1, 1, 2, 1), (1, 2, 1, 1, 2, 2), ...]");
        sextuples_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0, 0), (0, 0, 0, 0, 0, 1), (0, 0, 0, 0, 1, 0), (0, 0, 0, 0, 1, 1)," +
                " (0, 0, 0, 1, 0, 0), (0, 0, 0, 1, 0, 1), (0, 0, 0, 1, 1, 0), (0, 0, 0, 1, 1, 1)," +
                " (0, 0, 1, 0, 0, 0), (0, 0, 1, 0, 0, 1), (0, 0, 1, 0, 1, 0), (0, 0, 1, 0, 1, 1)," +
                " (0, 0, 1, 1, 0, 0), (0, 0, 1, 1, 0, 1), (0, 0, 1, 1, 1, 0), (0, 0, 1, 1, 1, 1)," +
                " (0, 1, 0, 0, 0, 0), (0, 1, 0, 0, 0, 1), (0, 1, 0, 0, 1, 0), (0, 1, 0, 0, 1, 1), ...]");
        sextuples_Iterable_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), ...]");
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
        Iterable<Septuple<A, B, C, D, E, F, G>> ss = EP.septuples(as, bs, cs, ds, es, fs, gs);
        aeqit(ss, output);
        testNoRemove(ss);
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
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1])," +
                " (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1])," +
                " (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1])," +
                " (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1])," +
                " (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1])," +
                " (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1])," +
                " (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1])," +
                " (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1])," +
                " (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1])," +
                " (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1])," +
                " (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1])," +
                " (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1])," +
                " (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1])," +
                " (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1])," +
                " (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1])," +
                " (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1])," +
                " (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1])," +
                " (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1])," +
                " (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1])," +
                " (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1])," +
                " (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1])," +
                " (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1])," +
                " (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1])," +
                " (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1])," +
                " (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1])," +
                " (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1])," +
                " (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1])," +
                " (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1])," +
                " (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1])," +
                " (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1])," +
                " (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1])," +
                " (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1])," +
                " (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1])," +
                " (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1])," +
                " (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1])," +
                " (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1])," +
                " (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1])," +
                " (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1])," +
                " (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1])," +
                " (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1])," +
                " (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1])," +
                " (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1])," +
                " (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1])," +
                " (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1])," +
                " (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1])," +
                " (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1])," +
                " (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1])," +
                " (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1])," +
                " (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1])," +
                " (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1])," +
                " (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1])," +
                " (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1])," +
                " (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1])," +
                " (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1])," +
                " (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1])," +
                " (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1])," +
                " (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Arrays.asList(1, 2, null, 4),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1])," +
                " (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1])," +
                " (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1])," +
                " (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1])," +
                " (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1])," +
                " (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1])," +
                " (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1])," +
                " (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1])," +
                " (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1])," +
                " (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1])," +
                " (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1])," +
                " (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1])," +
                " (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1])," +
                " (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1])," +
                " (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1])," +
                " (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1])," +
                " (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1])," +
                " (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1])," +
                " (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1])," +
                " (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1])," +
                " (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1])," +
                " (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1])," +
                " (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1])," +
                " (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1])," +
                " (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1])," +
                " (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1])," +
                " (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1])," +
                " (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1])," +
                " (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1])," +
                " (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1])," +
                " (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1])," +
                " (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1])," +
                " (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1])," +
                " (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1])," +
                " (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (1, d, false, EQ, yes, Infinity, [1, 0]), (1, d, false, EQ, yes, Infinity, [0, 1])," +
                " (1, d, false, EQ, yes, NaN, [1, 0]), (1, d, false, EQ, yes, NaN, [0, 1])," +
                " (1, d, false, EQ, no, Infinity, [1, 0]), (1, d, false, EQ, no, Infinity, [0, 1])," +
                " (1, d, false, EQ, no, NaN, [1, 0]), (1, d, false, EQ, no, NaN, [0, 1])," +
                " (1, d, false, LT, yes, Infinity, [1, 0]), (1, d, false, LT, yes, Infinity, [0, 1])," +
                " (1, d, false, LT, yes, NaN, [1, 0]), (1, d, false, LT, yes, NaN, [0, 1])," +
                " (1, d, false, LT, no, Infinity, [1, 0]), (1, d, false, LT, no, Infinity, [0, 1])," +
                " (1, d, false, LT, no, NaN, [1, 0]), (1, d, false, LT, no, NaN, [0, 1])," +
                " (1, d, true, EQ, yes, Infinity, [1, 0]), (1, d, true, EQ, yes, Infinity, [0, 1])," +
                " (1, d, true, EQ, yes, NaN, [1, 0]), (1, d, true, EQ, yes, NaN, [0, 1])," +
                " (1, d, true, EQ, no, Infinity, [1, 0]), (1, d, true, EQ, no, Infinity, [0, 1])," +
                " (1, d, true, EQ, no, NaN, [1, 0]), (1, d, true, EQ, no, NaN, [0, 1])," +
                " (1, d, true, LT, yes, Infinity, [1, 0]), (1, d, true, LT, yes, Infinity, [0, 1])," +
                " (1, d, true, LT, yes, NaN, [1, 0]), (1, d, true, LT, yes, NaN, [0, 1])," +
                " (1, d, true, LT, no, Infinity, [1, 0]), (1, d, true, LT, no, Infinity, [0, 1])," +
                " (1, d, true, LT, no, NaN, [1, 0]), (1, d, true, LT, no, NaN, [0, 1])," +
                " (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1])," +
                " (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1])," +
                " (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1])," +
                " (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1])," +
                " (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1])," +
                " (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1])," +
                " (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1])," +
                " (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1])," +
                " (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1])," +
                " (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1])," +
                " (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1])," +
                " (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1])," +
                " (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1])," +
                " (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1])," +
                " (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1])," +
                " (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1])," +
                " (2, d, false, EQ, yes, Infinity, [1, 0]), (2, d, false, EQ, yes, Infinity, [0, 1])," +
                " (2, d, false, EQ, yes, NaN, [1, 0]), (2, d, false, EQ, yes, NaN, [0, 1])," +
                " (2, d, false, EQ, no, Infinity, [1, 0]), (2, d, false, EQ, no, Infinity, [0, 1])," +
                " (2, d, false, EQ, no, NaN, [1, 0]), (2, d, false, EQ, no, NaN, [0, 1])," +
                " (2, d, false, LT, yes, Infinity, [1, 0]), (2, d, false, LT, yes, Infinity, [0, 1])," +
                " (2, d, false, LT, yes, NaN, [1, 0]), (2, d, false, LT, yes, NaN, [0, 1])," +
                " (2, d, false, LT, no, Infinity, [1, 0]), (2, d, false, LT, no, Infinity, [0, 1])," +
                " (2, d, false, LT, no, NaN, [1, 0]), (2, d, false, LT, no, NaN, [0, 1])," +
                " (2, d, true, EQ, yes, Infinity, [1, 0]), (2, d, true, EQ, yes, Infinity, [0, 1])," +
                " (2, d, true, EQ, yes, NaN, [1, 0]), (2, d, true, EQ, yes, NaN, [0, 1])," +
                " (2, d, true, EQ, no, Infinity, [1, 0]), (2, d, true, EQ, no, Infinity, [0, 1])," +
                " (2, d, true, EQ, no, NaN, [1, 0]), (2, d, true, EQ, no, NaN, [0, 1])," +
                " (2, d, true, LT, yes, Infinity, [1, 0]), (2, d, true, LT, yes, Infinity, [0, 1])," +
                " (2, d, true, LT, yes, NaN, [1, 0]), (2, d, true, LT, yes, NaN, [0, 1])," +
                " (2, d, true, LT, no, Infinity, [1, 0]), (2, d, true, LT, no, Infinity, [0, 1])," +
                " (2, d, true, LT, no, NaN, [1, 0]), (2, d, true, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (1, d, false, GT, yes, Infinity, [1, 0]), (1, d, false, GT, yes, Infinity, [0, 1])," +
                " (1, d, false, GT, yes, NaN, [1, 0]), (1, d, false, GT, yes, NaN, [0, 1])," +
                " (1, d, false, GT, no, Infinity, [1, 0]), (1, d, false, GT, no, Infinity, [0, 1])," +
                " (1, d, false, GT, no, NaN, [1, 0]), (1, d, false, GT, no, NaN, [0, 1])," +
                " (1, d, true, GT, yes, Infinity, [1, 0]), (1, d, true, GT, yes, Infinity, [0, 1])," +
                " (1, d, true, GT, yes, NaN, [1, 0]), (1, d, true, GT, yes, NaN, [0, 1])," +
                " (1, d, true, GT, no, Infinity, [1, 0]), (1, d, true, GT, no, Infinity, [0, 1])," +
                " (1, d, true, GT, no, NaN, [1, 0]), (1, d, true, GT, no, NaN, [0, 1])," +
                " (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1])," +
                " (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1])," +
                " (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1])," +
                " (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1])," +
                " (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1])," +
                " (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1])," +
                " (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1])," +
                " (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1])," +
                " (2, d, false, GT, yes, Infinity, [1, 0]), (2, d, false, GT, yes, Infinity, [0, 1])," +
                " (2, d, false, GT, yes, NaN, [1, 0]), (2, d, false, GT, yes, NaN, [0, 1])," +
                " (2, d, false, GT, no, Infinity, [1, 0]), (2, d, false, GT, no, Infinity, [0, 1])," +
                " (2, d, false, GT, no, NaN, [1, 0]), (2, d, false, GT, no, NaN, [0, 1])," +
                " (2, d, true, GT, yes, Infinity, [1, 0]), (2, d, true, GT, yes, Infinity, [0, 1])," +
                " (2, d, true, GT, yes, NaN, [1, 0]), (2, d, true, GT, yes, NaN, [0, 1])," +
                " (2, d, true, GT, no, Infinity, [1, 0]), (2, d, true, GT, no, Infinity, [0, 1])," +
                " (2, d, true, GT, no, NaN, [1, 0]), (2, d, true, GT, no, NaN, [0, 1])," +
                " (null, a, false, EQ, yes, Infinity, [1, 0]), (null, a, false, EQ, yes, Infinity, [0, 1])," +
                " (null, a, false, EQ, yes, NaN, [1, 0]), (null, a, false, EQ, yes, NaN, [0, 1])," +
                " (null, a, false, EQ, no, Infinity, [1, 0]), (null, a, false, EQ, no, Infinity, [0, 1])," +
                " (null, a, false, EQ, no, NaN, [1, 0]), (null, a, false, EQ, no, NaN, [0, 1])," +
                " (null, a, false, LT, yes, Infinity, [1, 0]), (null, a, false, LT, yes, Infinity, [0, 1])," +
                " (null, a, false, LT, yes, NaN, [1, 0]), (null, a, false, LT, yes, NaN, [0, 1])," +
                " (null, a, false, LT, no, Infinity, [1, 0]), (null, a, false, LT, no, Infinity, [0, 1])," +
                " (null, a, false, LT, no, NaN, [1, 0]), (null, a, false, LT, no, NaN, [0, 1])," +
                " (null, a, true, EQ, yes, Infinity, [1, 0]), (null, a, true, EQ, yes, Infinity, [0, 1])," +
                " (null, a, true, EQ, yes, NaN, [1, 0]), (null, a, true, EQ, yes, NaN, [0, 1])," +
                " (null, a, true, EQ, no, Infinity, [1, 0]), (null, a, true, EQ, no, Infinity, [0, 1])," +
                " (null, a, true, EQ, no, NaN, [1, 0]), (null, a, true, EQ, no, NaN, [0, 1])," +
                " (null, a, true, LT, yes, Infinity, [1, 0]), (null, a, true, LT, yes, Infinity, [0, 1])," +
                " (null, a, true, LT, yes, NaN, [1, 0]), (null, a, true, LT, yes, NaN, [0, 1])," +
                " (null, a, true, LT, no, Infinity, [1, 0]), (null, a, true, LT, no, Infinity, [0, 1])," +
                " (null, a, true, LT, no, NaN, [1, 0]), (null, a, true, LT, no, NaN, [0, 1])," +
                " (null, b, false, EQ, yes, Infinity, [1, 0]), (null, b, false, EQ, yes, Infinity, [0, 1])," +
                " (null, b, false, EQ, yes, NaN, [1, 0]), (null, b, false, EQ, yes, NaN, [0, 1])," +
                " (null, b, false, EQ, no, Infinity, [1, 0]), (null, b, false, EQ, no, Infinity, [0, 1])," +
                " (null, b, false, EQ, no, NaN, [1, 0]), (null, b, false, EQ, no, NaN, [0, 1])," +
                " (null, b, false, LT, yes, Infinity, [1, 0]), (null, b, false, LT, yes, Infinity, [0, 1])," +
                " (null, b, false, LT, yes, NaN, [1, 0]), (null, b, false, LT, yes, NaN, [0, 1])," +
                " (null, b, false, LT, no, Infinity, [1, 0]), (null, b, false, LT, no, Infinity, [0, 1])," +
                " (null, b, false, LT, no, NaN, [1, 0]), (null, b, false, LT, no, NaN, [0, 1])," +
                " (null, b, true, EQ, yes, Infinity, [1, 0]), (null, b, true, EQ, yes, Infinity, [0, 1])," +
                " (null, b, true, EQ, yes, NaN, [1, 0]), (null, b, true, EQ, yes, NaN, [0, 1])," +
                " (null, b, true, EQ, no, Infinity, [1, 0]), (null, b, true, EQ, no, Infinity, [0, 1])," +
                " (null, b, true, EQ, no, NaN, [1, 0]), (null, b, true, EQ, no, NaN, [0, 1])," +
                " (null, b, true, LT, yes, Infinity, [1, 0]), (null, b, true, LT, yes, Infinity, [0, 1])," +
                " (null, b, true, LT, yes, NaN, [1, 0]), (null, b, true, LT, yes, NaN, [0, 1])," +
                " (null, b, true, LT, no, Infinity, [1, 0]), (null, b, true, LT, no, Infinity, [0, 1])," +
                " (null, b, true, LT, no, NaN, [1, 0]), (null, b, true, LT, no, NaN, [0, 1])," +
                " (4, a, false, EQ, yes, Infinity, [1, 0]), (4, a, false, EQ, yes, Infinity, [0, 1])," +
                " (4, a, false, EQ, yes, NaN, [1, 0]), (4, a, false, EQ, yes, NaN, [0, 1])," +
                " (4, a, false, EQ, no, Infinity, [1, 0]), (4, a, false, EQ, no, Infinity, [0, 1])," +
                " (4, a, false, EQ, no, NaN, [1, 0]), (4, a, false, EQ, no, NaN, [0, 1])," +
                " (4, a, false, LT, yes, Infinity, [1, 0]), (4, a, false, LT, yes, Infinity, [0, 1])," +
                " (4, a, false, LT, yes, NaN, [1, 0]), (4, a, false, LT, yes, NaN, [0, 1])," +
                " (4, a, false, LT, no, Infinity, [1, 0]), (4, a, false, LT, no, Infinity, [0, 1])," +
                " (4, a, false, LT, no, NaN, [1, 0]), (4, a, false, LT, no, NaN, [0, 1])," +
                " (4, a, true, EQ, yes, Infinity, [1, 0]), (4, a, true, EQ, yes, Infinity, [0, 1])," +
                " (4, a, true, EQ, yes, NaN, [1, 0]), (4, a, true, EQ, yes, NaN, [0, 1])," +
                " (4, a, true, EQ, no, Infinity, [1, 0]), (4, a, true, EQ, no, Infinity, [0, 1])," +
                " (4, a, true, EQ, no, NaN, [1, 0]), (4, a, true, EQ, no, NaN, [0, 1])," +
                " (4, a, true, LT, yes, Infinity, [1, 0]), (4, a, true, LT, yes, Infinity, [0, 1])," +
                " (4, a, true, LT, yes, NaN, [1, 0]), (4, a, true, LT, yes, NaN, [0, 1])," +
                " (4, a, true, LT, no, Infinity, [1, 0]), (4, a, true, LT, no, Infinity, [0, 1])," +
                " (4, a, true, LT, no, NaN, [1, 0]), (4, a, true, LT, no, NaN, [0, 1])," +
                " (4, b, false, EQ, yes, Infinity, [1, 0]), (4, b, false, EQ, yes, Infinity, [0, 1])," +
                " (4, b, false, EQ, yes, NaN, [1, 0]), (4, b, false, EQ, yes, NaN, [0, 1])," +
                " (4, b, false, EQ, no, Infinity, [1, 0]), (4, b, false, EQ, no, Infinity, [0, 1])," +
                " (4, b, false, EQ, no, NaN, [1, 0]), (4, b, false, EQ, no, NaN, [0, 1])," +
                " (4, b, false, LT, yes, Infinity, [1, 0]), (4, b, false, LT, yes, Infinity, [0, 1])," +
                " (4, b, false, LT, yes, NaN, [1, 0]), (4, b, false, LT, yes, NaN, [0, 1])," +
                " (4, b, false, LT, no, Infinity, [1, 0]), (4, b, false, LT, no, Infinity, [0, 1])," +
                " (4, b, false, LT, no, NaN, [1, 0]), (4, b, false, LT, no, NaN, [0, 1])," +
                " (4, b, true, EQ, yes, Infinity, [1, 0]), (4, b, true, EQ, yes, Infinity, [0, 1])," +
                " (4, b, true, EQ, yes, NaN, [1, 0]), (4, b, true, EQ, yes, NaN, [0, 1])," +
                " (4, b, true, EQ, no, Infinity, [1, 0]), (4, b, true, EQ, no, Infinity, [0, 1])," +
                " (4, b, true, EQ, no, NaN, [1, 0]), (4, b, true, EQ, no, NaN, [0, 1])," +
                " (4, b, true, LT, yes, Infinity, [1, 0]), (4, b, true, LT, yes, Infinity, [0, 1])," +
                " (4, b, true, LT, yes, NaN, [1, 0]), (4, b, true, LT, yes, NaN, [0, 1])," +
                " (4, b, true, LT, no, Infinity, [1, 0]), (4, b, true, LT, no, Infinity, [0, 1])," +
                " (4, b, true, LT, no, NaN, [1, 0]), (4, b, true, LT, no, NaN, [0, 1])," +
                " (null, a, false, GT, yes, Infinity, [1, 0]), (null, a, false, GT, yes, Infinity, [0, 1])," +
                " (null, a, false, GT, yes, NaN, [1, 0]), (null, a, false, GT, yes, NaN, [0, 1])," +
                " (null, a, false, GT, no, Infinity, [1, 0]), (null, a, false, GT, no, Infinity, [0, 1])," +
                " (null, a, false, GT, no, NaN, [1, 0]), (null, a, false, GT, no, NaN, [0, 1])," +
                " (null, a, true, GT, yes, Infinity, [1, 0]), (null, a, true, GT, yes, Infinity, [0, 1])," +
                " (null, a, true, GT, yes, NaN, [1, 0]), (null, a, true, GT, yes, NaN, [0, 1])," +
                " (null, a, true, GT, no, Infinity, [1, 0]), (null, a, true, GT, no, Infinity, [0, 1])," +
                " (null, a, true, GT, no, NaN, [1, 0]), (null, a, true, GT, no, NaN, [0, 1])," +
                " (null, b, false, GT, yes, Infinity, [1, 0]), (null, b, false, GT, yes, Infinity, [0, 1])," +
                " (null, b, false, GT, yes, NaN, [1, 0]), (null, b, false, GT, yes, NaN, [0, 1])," +
                " (null, b, false, GT, no, Infinity, [1, 0]), (null, b, false, GT, no, Infinity, [0, 1])," +
                " (null, b, false, GT, no, NaN, [1, 0]), (null, b, false, GT, no, NaN, [0, 1])," +
                " (null, b, true, GT, yes, Infinity, [1, 0]), (null, b, true, GT, yes, Infinity, [0, 1])," +
                " (null, b, true, GT, yes, NaN, [1, 0]), (null, b, true, GT, yes, NaN, [0, 1])," +
                " (null, b, true, GT, no, Infinity, [1, 0]), (null, b, true, GT, no, Infinity, [0, 1])," +
                " (null, b, true, GT, no, NaN, [1, 0]), (null, b, true, GT, no, NaN, [0, 1])," +
                " (4, a, false, GT, yes, Infinity, [1, 0]), (4, a, false, GT, yes, Infinity, [0, 1])," +
                " (4, a, false, GT, yes, NaN, [1, 0]), (4, a, false, GT, yes, NaN, [0, 1])," +
                " (4, a, false, GT, no, Infinity, [1, 0]), (4, a, false, GT, no, Infinity, [0, 1])," +
                " (4, a, false, GT, no, NaN, [1, 0]), (4, a, false, GT, no, NaN, [0, 1])," +
                " (4, a, true, GT, yes, Infinity, [1, 0]), (4, a, true, GT, yes, Infinity, [0, 1])," +
                " (4, a, true, GT, yes, NaN, [1, 0]), (4, a, true, GT, yes, NaN, [0, 1])," +
                " (4, a, true, GT, no, Infinity, [1, 0]), (4, a, true, GT, no, Infinity, [0, 1])," +
                " (4, a, true, GT, no, NaN, [1, 0]), (4, a, true, GT, no, NaN, [0, 1])," +
                " (4, b, false, GT, yes, Infinity, [1, 0]), (4, b, false, GT, yes, Infinity, [0, 1])," +
                " (4, b, false, GT, yes, NaN, [1, 0]), (4, b, false, GT, yes, NaN, [0, 1])," +
                " (4, b, false, GT, no, Infinity, [1, 0]), (4, b, false, GT, no, Infinity, [0, 1])," +
                " (4, b, false, GT, no, NaN, [1, 0]), (4, b, false, GT, no, NaN, [0, 1])," +
                " (4, b, true, GT, yes, Infinity, [1, 0]), (4, b, true, GT, yes, Infinity, [0, 1])," +
                " (4, b, true, GT, yes, NaN, [1, 0]), (4, b, true, GT, yes, NaN, [0, 1])," +
                " (4, b, true, GT, no, Infinity, [1, 0]), (4, b, true, GT, no, Infinity, [0, 1])," +
                " (4, b, true, GT, no, NaN, [1, 0]), (4, b, true, GT, no, NaN, [0, 1])," +
                " (null, c, false, EQ, yes, Infinity, [1, 0]), (null, c, false, EQ, yes, Infinity, [0, 1])," +
                " (null, c, false, EQ, yes, NaN, [1, 0]), (null, c, false, EQ, yes, NaN, [0, 1])," +
                " (null, c, false, EQ, no, Infinity, [1, 0]), (null, c, false, EQ, no, Infinity, [0, 1])," +
                " (null, c, false, EQ, no, NaN, [1, 0]), (null, c, false, EQ, no, NaN, [0, 1])," +
                " (null, c, false, LT, yes, Infinity, [1, 0]), (null, c, false, LT, yes, Infinity, [0, 1])," +
                " (null, c, false, LT, yes, NaN, [1, 0]), (null, c, false, LT, yes, NaN, [0, 1])," +
                " (null, c, false, LT, no, Infinity, [1, 0]), (null, c, false, LT, no, Infinity, [0, 1])," +
                " (null, c, false, LT, no, NaN, [1, 0]), (null, c, false, LT, no, NaN, [0, 1])," +
                " (null, c, true, EQ, yes, Infinity, [1, 0]), (null, c, true, EQ, yes, Infinity, [0, 1])," +
                " (null, c, true, EQ, yes, NaN, [1, 0]), (null, c, true, EQ, yes, NaN, [0, 1])," +
                " (null, c, true, EQ, no, Infinity, [1, 0]), (null, c, true, EQ, no, Infinity, [0, 1])," +
                " (null, c, true, EQ, no, NaN, [1, 0]), (null, c, true, EQ, no, NaN, [0, 1])," +
                " (null, c, true, LT, yes, Infinity, [1, 0]), (null, c, true, LT, yes, Infinity, [0, 1])," +
                " (null, c, true, LT, yes, NaN, [1, 0]), (null, c, true, LT, yes, NaN, [0, 1])," +
                " (null, c, true, LT, no, Infinity, [1, 0]), (null, c, true, LT, no, Infinity, [0, 1])," +
                " (null, c, true, LT, no, NaN, [1, 0]), (null, c, true, LT, no, NaN, [0, 1])," +
                " (null, d, false, EQ, yes, Infinity, [1, 0]), (null, d, false, EQ, yes, Infinity, [0, 1])," +
                " (null, d, false, EQ, yes, NaN, [1, 0]), (null, d, false, EQ, yes, NaN, [0, 1])," +
                " (null, d, false, EQ, no, Infinity, [1, 0]), (null, d, false, EQ, no, Infinity, [0, 1])," +
                " (null, d, false, EQ, no, NaN, [1, 0]), (null, d, false, EQ, no, NaN, [0, 1])," +
                " (null, d, false, LT, yes, Infinity, [1, 0]), (null, d, false, LT, yes, Infinity, [0, 1])," +
                " (null, d, false, LT, yes, NaN, [1, 0]), (null, d, false, LT, yes, NaN, [0, 1])," +
                " (null, d, false, LT, no, Infinity, [1, 0]), (null, d, false, LT, no, Infinity, [0, 1])," +
                " (null, d, false, LT, no, NaN, [1, 0]), (null, d, false, LT, no, NaN, [0, 1])," +
                " (null, d, true, EQ, yes, Infinity, [1, 0]), (null, d, true, EQ, yes, Infinity, [0, 1])," +
                " (null, d, true, EQ, yes, NaN, [1, 0]), (null, d, true, EQ, yes, NaN, [0, 1])," +
                " (null, d, true, EQ, no, Infinity, [1, 0]), (null, d, true, EQ, no, Infinity, [0, 1])," +
                " (null, d, true, EQ, no, NaN, [1, 0]), (null, d, true, EQ, no, NaN, [0, 1])," +
                " (null, d, true, LT, yes, Infinity, [1, 0]), (null, d, true, LT, yes, Infinity, [0, 1])," +
                " (null, d, true, LT, yes, NaN, [1, 0]), (null, d, true, LT, yes, NaN, [0, 1])," +
                " (null, d, true, LT, no, Infinity, [1, 0]), (null, d, true, LT, no, Infinity, [0, 1])," +
                " (null, d, true, LT, no, NaN, [1, 0]), (null, d, true, LT, no, NaN, [0, 1])," +
                " (4, c, false, EQ, yes, Infinity, [1, 0]), (4, c, false, EQ, yes, Infinity, [0, 1])," +
                " (4, c, false, EQ, yes, NaN, [1, 0]), (4, c, false, EQ, yes, NaN, [0, 1])," +
                " (4, c, false, EQ, no, Infinity, [1, 0]), (4, c, false, EQ, no, Infinity, [0, 1])," +
                " (4, c, false, EQ, no, NaN, [1, 0]), (4, c, false, EQ, no, NaN, [0, 1])," +
                " (4, c, false, LT, yes, Infinity, [1, 0]), (4, c, false, LT, yes, Infinity, [0, 1])," +
                " (4, c, false, LT, yes, NaN, [1, 0]), (4, c, false, LT, yes, NaN, [0, 1])," +
                " (4, c, false, LT, no, Infinity, [1, 0]), (4, c, false, LT, no, Infinity, [0, 1])," +
                " (4, c, false, LT, no, NaN, [1, 0]), (4, c, false, LT, no, NaN, [0, 1])," +
                " (4, c, true, EQ, yes, Infinity, [1, 0]), (4, c, true, EQ, yes, Infinity, [0, 1])," +
                " (4, c, true, EQ, yes, NaN, [1, 0]), (4, c, true, EQ, yes, NaN, [0, 1])," +
                " (4, c, true, EQ, no, Infinity, [1, 0]), (4, c, true, EQ, no, Infinity, [0, 1])," +
                " (4, c, true, EQ, no, NaN, [1, 0]), (4, c, true, EQ, no, NaN, [0, 1])," +
                " (4, c, true, LT, yes, Infinity, [1, 0]), (4, c, true, LT, yes, Infinity, [0, 1])," +
                " (4, c, true, LT, yes, NaN, [1, 0]), (4, c, true, LT, yes, NaN, [0, 1])," +
                " (4, c, true, LT, no, Infinity, [1, 0]), (4, c, true, LT, no, Infinity, [0, 1])," +
                " (4, c, true, LT, no, NaN, [1, 0]), (4, c, true, LT, no, NaN, [0, 1])," +
                " (4, d, false, EQ, yes, Infinity, [1, 0]), (4, d, false, EQ, yes, Infinity, [0, 1])," +
                " (4, d, false, EQ, yes, NaN, [1, 0]), (4, d, false, EQ, yes, NaN, [0, 1])," +
                " (4, d, false, EQ, no, Infinity, [1, 0]), (4, d, false, EQ, no, Infinity, [0, 1])," +
                " (4, d, false, EQ, no, NaN, [1, 0]), (4, d, false, EQ, no, NaN, [0, 1])," +
                " (4, d, false, LT, yes, Infinity, [1, 0]), (4, d, false, LT, yes, Infinity, [0, 1])," +
                " (4, d, false, LT, yes, NaN, [1, 0]), (4, d, false, LT, yes, NaN, [0, 1])," +
                " (4, d, false, LT, no, Infinity, [1, 0]), (4, d, false, LT, no, Infinity, [0, 1])," +
                " (4, d, false, LT, no, NaN, [1, 0]), (4, d, false, LT, no, NaN, [0, 1])," +
                " (4, d, true, EQ, yes, Infinity, [1, 0]), (4, d, true, EQ, yes, Infinity, [0, 1])," +
                " (4, d, true, EQ, yes, NaN, [1, 0]), (4, d, true, EQ, yes, NaN, [0, 1])," +
                " (4, d, true, EQ, no, Infinity, [1, 0]), (4, d, true, EQ, no, Infinity, [0, 1])," +
                " (4, d, true, EQ, no, NaN, [1, 0]), (4, d, true, EQ, no, NaN, [0, 1])," +
                " (4, d, true, LT, yes, Infinity, [1, 0]), (4, d, true, LT, yes, Infinity, [0, 1])," +
                " (4, d, true, LT, yes, NaN, [1, 0]), (4, d, true, LT, yes, NaN, [0, 1])," +
                " (4, d, true, LT, no, Infinity, [1, 0]), (4, d, true, LT, no, Infinity, [0, 1])," +
                " (4, d, true, LT, no, NaN, [1, 0]), (4, d, true, LT, no, NaN, [0, 1])," +
                " (null, c, false, GT, yes, Infinity, [1, 0]), (null, c, false, GT, yes, Infinity, [0, 1])," +
                " (null, c, false, GT, yes, NaN, [1, 0]), (null, c, false, GT, yes, NaN, [0, 1])," +
                " (null, c, false, GT, no, Infinity, [1, 0]), (null, c, false, GT, no, Infinity, [0, 1])," +
                " (null, c, false, GT, no, NaN, [1, 0]), (null, c, false, GT, no, NaN, [0, 1])," +
                " (null, c, true, GT, yes, Infinity, [1, 0]), (null, c, true, GT, yes, Infinity, [0, 1])," +
                " (null, c, true, GT, yes, NaN, [1, 0]), (null, c, true, GT, yes, NaN, [0, 1])," +
                " (null, c, true, GT, no, Infinity, [1, 0]), (null, c, true, GT, no, Infinity, [0, 1])," +
                " (null, c, true, GT, no, NaN, [1, 0]), (null, c, true, GT, no, NaN, [0, 1])," +
                " (null, d, false, GT, yes, Infinity, [1, 0]), (null, d, false, GT, yes, Infinity, [0, 1])," +
                " (null, d, false, GT, yes, NaN, [1, 0]), (null, d, false, GT, yes, NaN, [0, 1])," +
                " (null, d, false, GT, no, Infinity, [1, 0]), (null, d, false, GT, no, Infinity, [0, 1])," +
                " (null, d, false, GT, no, NaN, [1, 0]), (null, d, false, GT, no, NaN, [0, 1])," +
                " (null, d, true, GT, yes, Infinity, [1, 0]), (null, d, true, GT, yes, Infinity, [0, 1])," +
                " (null, d, true, GT, yes, NaN, [1, 0]), (null, d, true, GT, yes, NaN, [0, 1])," +
                " (null, d, true, GT, no, Infinity, [1, 0]), (null, d, true, GT, no, Infinity, [0, 1])," +
                " (null, d, true, GT, no, NaN, [1, 0]), (null, d, true, GT, no, NaN, [0, 1])," +
                " (4, c, false, GT, yes, Infinity, [1, 0]), (4, c, false, GT, yes, Infinity, [0, 1])," +
                " (4, c, false, GT, yes, NaN, [1, 0]), (4, c, false, GT, yes, NaN, [0, 1])," +
                " (4, c, false, GT, no, Infinity, [1, 0]), (4, c, false, GT, no, Infinity, [0, 1])," +
                " (4, c, false, GT, no, NaN, [1, 0]), (4, c, false, GT, no, NaN, [0, 1])," +
                " (4, c, true, GT, yes, Infinity, [1, 0]), (4, c, true, GT, yes, Infinity, [0, 1])," +
                " (4, c, true, GT, yes, NaN, [1, 0]), (4, c, true, GT, yes, NaN, [0, 1])," +
                " (4, c, true, GT, no, Infinity, [1, 0]), (4, c, true, GT, no, Infinity, [0, 1])," +
                " (4, c, true, GT, no, NaN, [1, 0]), (4, c, true, GT, no, NaN, [0, 1])," +
                " (4, d, false, GT, yes, Infinity, [1, 0]), (4, d, false, GT, yes, Infinity, [0, 1])," +
                " (4, d, false, GT, yes, NaN, [1, 0]), (4, d, false, GT, yes, NaN, [0, 1])," +
                " (4, d, false, GT, no, Infinity, [1, 0]), (4, d, false, GT, no, Infinity, [0, 1])," +
                " (4, d, false, GT, no, NaN, [1, 0]), (4, d, false, GT, no, NaN, [0, 1])," +
                " (4, d, true, GT, yes, Infinity, [1, 0]), (4, d, true, GT, yes, Infinity, [0, 1])," +
                " (4, d, true, GT, yes, NaN, [1, 0]), (4, d, true, GT, yes, NaN, [0, 1])," +
                " (4, d, true, GT, no, Infinity, [1, 0]), (4, d, true, GT, no, Infinity, [0, 1])," +
                " (4, d, true, GT, no, NaN, [1, 0]), (4, d, true, GT, no, NaN, [0, 1])]"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                fromString("abcd"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y),
                "[]"
        );
        septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_helper(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "[]"
        );
        simpleProviderHelper(
                EP.septuples(
                        EP.naturalBigIntegers(),
                        fromString("abcd"),
                        EP.booleans(),
                        EP.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
                "[(0, a, false, EQ, yes, Infinity, [1, 0]), (0, a, false, EQ, yes, Infinity, [0, 1])," +
                " (0, a, false, EQ, yes, NaN, [1, 0]), (0, a, false, EQ, yes, NaN, [0, 1])," +
                " (0, a, false, EQ, no, Infinity, [1, 0]), (0, a, false, EQ, no, Infinity, [0, 1])," +
                " (0, a, false, EQ, no, NaN, [1, 0]), (0, a, false, EQ, no, NaN, [0, 1])," +
                " (0, a, false, LT, yes, Infinity, [1, 0]), (0, a, false, LT, yes, Infinity, [0, 1])," +
                " (0, a, false, LT, yes, NaN, [1, 0]), (0, a, false, LT, yes, NaN, [0, 1])," +
                " (0, a, false, LT, no, Infinity, [1, 0]), (0, a, false, LT, no, Infinity, [0, 1])," +
                " (0, a, false, LT, no, NaN, [1, 0]), (0, a, false, LT, no, NaN, [0, 1])," +
                " (0, a, true, EQ, yes, Infinity, [1, 0]), (0, a, true, EQ, yes, Infinity, [0, 1])," +
                " (0, a, true, EQ, yes, NaN, [1, 0]), (0, a, true, EQ, yes, NaN, [0, 1]), ...]"
        );
        simpleProviderHelper(
                EP.septuples(
                        fromString("abcd"),
                        EP.booleans(),
                        EP.naturalBigIntegers(),
                        EP.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
                "[(a, false, 0, EQ, yes, Infinity, [1, 0]), (a, false, 0, EQ, yes, Infinity, [0, 1])," +
                " (a, false, 0, EQ, yes, NaN, [1, 0]), (a, false, 0, EQ, yes, NaN, [0, 1])," +
                " (a, false, 0, EQ, no, Infinity, [1, 0]), (a, false, 0, EQ, no, Infinity, [0, 1])," +
                " (a, false, 0, EQ, no, NaN, [1, 0]), (a, false, 0, EQ, no, NaN, [0, 1])," +
                " (a, false, 0, LT, yes, Infinity, [1, 0]), (a, false, 0, LT, yes, Infinity, [0, 1])," +
                " (a, false, 0, LT, yes, NaN, [1, 0]), (a, false, 0, LT, yes, NaN, [0, 1])," +
                " (a, false, 0, LT, no, Infinity, [1, 0]), (a, false, 0, LT, no, Infinity, [0, 1])," +
                " (a, false, 0, LT, no, NaN, [1, 0]), (a, false, 0, LT, no, NaN, [0, 1])," +
                " (a, false, 1, EQ, yes, Infinity, [1, 0]), (a, false, 1, EQ, yes, Infinity, [0, 1])," +
                " (a, false, 1, EQ, yes, NaN, [1, 0]), (a, false, 1, EQ, yes, NaN, [0, 1]), ...]"
        );
        simpleProviderHelper(
                EP.septuples(
                        EP.positiveBigIntegers(),
                        EP.negativeBigIntegers(),
                        EP.characters(),
                        EP.strings(),
                        EP.floats(),
                        EP.lists(EP.integers()),
                        EP.bigDecimals()
                ),
                "[(1, -1, a, , NaN, [], 0), (1, -1, a, , NaN, [], 0.0), (1, -1, a, , NaN, [0], 0)," +
                " (1, -1, a, , NaN, [0], 0.0), (1, -1, a, , Infinity, [], 0), (1, -1, a, , Infinity, [], 0.0)," +
                " (1, -1, a, , Infinity, [0], 0), (1, -1, a, , Infinity, [0], 0.0), (1, -1, a, a, NaN, [], 0)," +
                " (1, -1, a, a, NaN, [], 0.0), (1, -1, a, a, NaN, [0], 0), (1, -1, a, a, NaN, [0], 0.0)," +
                " (1, -1, a, a, Infinity, [], 0), (1, -1, a, a, Infinity, [], 0.0), (1, -1, a, a, Infinity, [0], 0)," +
                " (1, -1, a, a, Infinity, [0], 0.0), (1, -1, b, , NaN, [], 0), (1, -1, b, , NaN, [], 0.0)," +
                " (1, -1, b, , NaN, [0], 0), (1, -1, b, , NaN, [0], 0.0), ...]"
        );
    }

    private static void septuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.septuples(readIntegerListWithNulls(input)), output);
    }

    private static void septuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.septuples(input), output);
    }

    @Test
    public void testSeptuples_Iterable() {
        septuples_Iterable_helper("[]", "[]");
        septuples_Iterable_helper("[5]", "[(5, 5, 5, 5, 5, 5, 5)]");
        septuples_Iterable_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 2, 1, 1), (1, 1, 1, 1, 2, 1, 2), (1, 1, 1, 1, 2, 2, 1), (1, 1, 1, 1, 2, 2, 2)," +
                " (1, 1, 1, 2, 1, 1, 1), (1, 1, 1, 2, 1, 1, 2), (1, 1, 1, 2, 1, 2, 1), (1, 1, 1, 2, 1, 2, 2)," +
                " (1, 1, 1, 2, 2, 1, 1), (1, 1, 1, 2, 2, 1, 2), (1, 1, 1, 2, 2, 2, 1), (1, 1, 1, 2, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1, 1), (1, 1, 2, 1, 1, 1, 2), (1, 1, 2, 1, 1, 2, 1), (1, 1, 2, 1, 1, 2, 2), ...]");
        septuples_Iterable_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 2, 1, 1), (1, 1, 1, 1, 2, 1, 2), (1, 1, 1, 1, 2, 2, 1), (1, 1, 1, 1, 2, 2, 2)," +
                " (1, 1, 1, 2, 1, 1, 1), (1, 1, 1, 2, 1, 1, 2), (1, 1, 1, 2, 1, 2, 1), (1, 1, 1, 2, 1, 2, 2)," +
                " (1, 1, 1, 2, 2, 1, 1), (1, 1, 1, 2, 2, 1, 2), (1, 1, 1, 2, 2, 2, 1), (1, 1, 1, 2, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1, 1), (1, 1, 2, 1, 1, 1, 2), (1, 1, 2, 1, 1, 2, 1), (1, 1, 2, 1, 1, 2, 2), ...]");
        septuples_Iterable_helper("[1, 2, null, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2, 1), (1, 1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 2, 1, 1), (1, 1, 1, 1, 2, 1, 2), (1, 1, 1, 1, 2, 2, 1), (1, 1, 1, 1, 2, 2, 2)," +
                " (1, 1, 1, 2, 1, 1, 1), (1, 1, 1, 2, 1, 1, 2), (1, 1, 1, 2, 1, 2, 1), (1, 1, 1, 2, 1, 2, 2)," +
                " (1, 1, 1, 2, 2, 1, 1), (1, 1, 1, 2, 2, 1, 2), (1, 1, 1, 2, 2, 2, 1), (1, 1, 1, 2, 2, 2, 2)," +
                " (1, 1, 2, 1, 1, 1, 1), (1, 1, 2, 1, 1, 1, 2), (1, 1, 2, 1, 1, 2, 1), (1, 1, 2, 1, 1, 2, 2), ...]");
        septuples_Iterable_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0, 0, 0), (0, 0, 0, 0, 0, 0, 1), (0, 0, 0, 0, 0, 1, 0), (0, 0, 0, 0, 0, 1, 1)," +
                " (0, 0, 0, 0, 1, 0, 0), (0, 0, 0, 0, 1, 0, 1), (0, 0, 0, 0, 1, 1, 0), (0, 0, 0, 0, 1, 1, 1)," +
                " (0, 0, 0, 1, 0, 0, 0), (0, 0, 0, 1, 0, 0, 1), (0, 0, 0, 1, 0, 1, 0), (0, 0, 0, 1, 0, 1, 1)," +
                " (0, 0, 0, 1, 1, 0, 0), (0, 0, 0, 1, 1, 0, 1), (0, 0, 0, 1, 1, 1, 0), (0, 0, 0, 1, 1, 1, 1)," +
                " (0, 0, 1, 0, 0, 0, 0), (0, 0, 1, 0, 0, 0, 1), (0, 0, 1, 0, 0, 1, 0), (0, 0, 1, 0, 0, 1, 1), ...]");
        septuples_Iterable_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), ...]");
    }

    private static void strings_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.strings(size, input), output);
    }

    private static void strings_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.strings(size, input), output);
    }

    @Test
    public void testStrings_int_String() {
        strings_int_String_helper(0, "", "[]");
        aeq(length(EP.strings(0, "")), 1);
        strings_int_String_helper(1, "", "[]");
        aeq(length(EP.strings(1, "")), 0);
        strings_int_String_helper(2, "", "[]");
        aeq(length(EP.strings(2, "")), 0);
        strings_int_String_helper(3, "", "[]");
        aeq(length(EP.strings(3, "")), 0);
        strings_int_String_helper(0, "abc", "[]");
        aeq(length(EP.strings(0, "abc")), 1);
        strings_int_String_helper(0, "a", "[]");
        strings_int_String_helper(1, "a", "[a]");
        strings_int_String_helper(2, "a", "[aa]");
        strings_int_String_helper(3, "a", "[aaa]");
        strings_int_String_helper(1, "abc", "[a, b, c]");
        strings_int_String_helper(2, "abc", "[aa, ab, ba, bb, ac, bc, ca, cb, cc]");
        strings_int_String_helper(3, "abc",
                "[aaa, aab, aba, abb, baa, bab, bba, bbb, aac, abc, bac, bbc, aca, acb, bca, bcb, acc, bcc, caa," +
                " cab, cba, cbb, cac, cbc, cca, ccb, ccc]");
        strings_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.strings(0, "abbc")), 1);
        strings_int_String_helper(1, "abbc", "[a, b, b, c]");
        strings_int_String_helper(2, "abbc", "[aa, ab, ba, bb, ab, ac, bb, bc, ba, bb, ca, cb, bb, bc, cb, cc]");
        strings_int_String_helper(3, "abbc",
                "[aaa, aab, aba, abb, baa, bab, bba, bbb, aab, aac, abb, abc, bab, bac, bbb, bbc, aba, abb, aca," +
                " acb, bba, bbb, bca, bcb, abb, abc, acb, acc, bbb, bbc, bcb, bcc, baa, bab, bba, bbb, caa, cab," +
                " cba, cbb, bab, bac, bbb, bbc, cab, cac, cbb, cbc, bba, bbb, bca, bcb, cba, cbb, cca, ccb, bbb," +
                " bbc, bcb, bcc, cbb, cbc, ccb, ccc]");
        strings_int_String_helper(0, "Mississippi", "[]");
        aeq(length(EP.strings(0, "Mississippi")), 1);
        strings_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        strings_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, iM, ii, Ms, Ms, is, is, sM, si, sM, si, ss, ss, ss, ss, Mi, Ms, ii, is, ...]");
        strings_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, MiM, Mii, iMM, iMi, iiM, iii, MMs, MMs, Mis, Mis, iMs, iMs, iis, iis, MsM, Msi, MsM," +
                " Msi, ...]");
        try {
            EP.strings(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.strings(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void strings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.strings(size), output);
    }

    @Test
    public void testStrings_int() {
        strings_int_helper(0, "[]");
        aeq(length(EP.strings(0)), 1);
        strings_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        strings_int_helper(2,
                "[aa, ab, ba, bb, ac, ad, bc, bd, ca, cb, da, db, cc, cd, dc, dd, ae, af, be, bf, ...]");
        strings_int_helper(3,
                "[aaa, aab, aba, abb, baa, bab, bba, bbb, aac, aad, abc, abd, bac, bad, bbc, bbd, aca, acb, ada," +
                " adb, ...]");
        try {
            EP.strings(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLists_Iterable() {
        aeqit(EP.lists(Collections.emptyList()), "[[]]");
        simpleProviderHelper(EP.lists(Collections.singletonList(5)),
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        simpleProviderHelper(EP.lists(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 1], [2, 3], [1, 1, 1, 2], [3, 1], [1, 2, 2], [3, 2]," +
                " [1, 1, 1, 1, 1, 1], ...]");
        simpleProviderHelper(EP.lists(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 1], [1, 3], [1, 1, 1, 2], [2, 2], [1, 2, 2], [2, 3], ...]");
        simpleProviderHelper(EP.lists(EP.naturalIntegers()),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        simpleProviderHelper(EP.lists(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
    }

    @Test
    public void testStrings_String() {
        aeqit(EP.strings(""), "[]");
        aeq(length(EP.strings("")), 1);
        simpleProviderHelper(EP.strings("a"),
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        simpleProviderHelper(EP.strings("abc"),
                "[, a, aa, b, aaa, c, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, bc, aaab, ca, abb, cb, aaaaaa, ...]");
        simpleProviderHelper(EP.strings("abbc"),
                "[, a, aa, b, aaa, b, ab, c, aaaa, ba, aab, bb, aaaaa, ab, aba, ac, aaab, bb, abb, bc, ...]");
        simpleProviderHelper(EP.strings("Mississippi"),
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, iM, s, MMi, s, ii, i, MMMMM, p, Ms, p, ...]");
    }

    @Test
    public void testStrings() {
        simpleProviderHelper(EP.strings(),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]");
    }

    private static void listsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.listsAtLeast(minSize, input), output);
    }

    private static void listsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        listsAtLeast_helper(minSize, readIntegerListWithNulls(input), output);
    }

    @Test
    public void testListsAtLeast() {
        listsAtLeast_helper(0, "[]", "[[]]");
        listsAtLeast_helper(1, "[]", "[]");
        listsAtLeast_helper(2, "[]", "[]");
        listsAtLeast_helper(3, "[]", "[]");

        listsAtLeast_helper(0, "[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsAtLeast_helper(1, "[5]",
                "[[5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsAtLeast_helper(2, "[5]",
                "[[5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        listsAtLeast_helper(3, "[5]",
                "[[5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");

        listsAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 1], [2, 3], [1, 1, 1, 2], [3, 1], [1, 2, 2], [3, 2]," +
                " [1, 1, 1, 1, 1, 1], ...]");
        listsAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 1], [2, 3], [1, 1, 1, 2], [3, 1], [1, 2, 2], [3, 2]," +
                " [1, 1, 1, 1, 1, 1], [3, 3], ...]");
        listsAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 1, 1], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2], [1, 1, 1, 1, 1], [1, 3]," +
                " [1, 2, 1], [2, 3], [1, 1, 1, 2], [3, 1], [1, 2, 2], [3, 2], [1, 1, 1, 1, 1, 1], [3, 3], [2, 1, 1]," +
                " [1, 1, 2, 1], [2, 1, 2], ...]");
        listsAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 2], [1, 1, 1, 1, 1], [1, 2, 1], [1, 1, 1, 2], [1, 2, 2]," +
                " [1, 1, 1, 1, 1, 1], [2, 1, 1], [1, 1, 2, 1], [2, 1, 2], [1, 1, 1, 1, 2], [2, 2, 1], [1, 1, 2, 2]," +
                " [2, 2, 2], [1, 1, 1, 1, 1, 1, 1], [1, 1, 3], [1, 2, 1, 1], [1, 2, 3], [1, 1, 1, 2, 1], ...]");

        listsAtLeast_helper(0, "[1, null, 3]",
                "[[], [1], [1, 1], [null], [1, 1, 1], [3], [1, null], [1, 1, 1, 1], [null, 1], [1, 1, null]," +
                " [null, null], [1, 1, 1, 1, 1], [1, 3], [1, null, 1], [null, 3], [1, 1, 1, null], [3, 1]," +
                " [1, null, null], [3, null], [1, 1, 1, 1, 1, 1], ...]");
        listsAtLeast_helper(1, "[1, null, 3]",
                "[[1], [1, 1], [null], [1, 1, 1], [3], [1, null], [1, 1, 1, 1], [null, 1], [1, 1, null]," +
                " [null, null], [1, 1, 1, 1, 1], [1, 3], [1, null, 1], [null, 3], [1, 1, 1, null], [3, 1]," +
                " [1, null, null], [3, null], [1, 1, 1, 1, 1, 1], [3, 3], ...]");
        listsAtLeast_helper(2, "[1, null, 3]",
                "[[1, 1], [1, 1, 1], [1, null], [1, 1, 1, 1], [null, 1], [1, 1, null], [null, null]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, null, 1], [null, 3], [1, 1, 1, null], [3, 1], [1, null, null]," +
                " [3, null], [1, 1, 1, 1, 1, 1], [3, 3], [null, 1, 1], [1, 1, null, 1], [null, 1, null], ...]");
        listsAtLeast_helper(3, "[1, null, 3]",
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, null], [1, 1, 1, 1, 1], [1, null, 1], [1, 1, 1, null]," +
                " [1, null, null], [1, 1, 1, 1, 1, 1], [null, 1, 1], [1, 1, null, 1], [null, 1, null]," +
                " [1, 1, 1, 1, null], [null, null, 1], [1, 1, null, null], [null, null, null]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1, 3], [1, null, 1, 1], [1, null, 3], [1, 1, 1, null, 1], ...]");

        listsAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 1], [1, 3], [1, 1, 1, 2], [2, 2], [1, 2, 2], [2, 3], ...]");
        listsAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 1], [1, 3], [1, 1, 1, 2], [2, 2], [1, 2, 2], [2, 3]," +
                " [1, 1, 1, 1, 1, 1], ...]");
        listsAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 1, 1], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2], [1, 1, 1, 1, 1], [1, 2]," +
                " [1, 2, 1], [1, 3], [1, 1, 1, 2], [2, 2], [1, 2, 2], [2, 3], [1, 1, 1, 1, 1, 1], [2, 1], [2, 1, 1]," +
                " [2, 2], [1, 1, 2, 1], ...]");
        listsAtLeast_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 2], [1, 1, 1, 1, 1], [1, 2, 1], [1, 1, 1, 2], [1, 2, 2]," +
                " [1, 1, 1, 1, 1, 1], [2, 1, 1], [1, 1, 2, 1], [2, 1, 2], [1, 1, 1, 1, 2], [2, 2, 1], [1, 1, 2, 2]," +
                " [2, 2, 2], [1, 1, 1, 1, 1, 1, 1], [1, 1, 2], [1, 2, 1, 1], [1, 1, 3], [1, 1, 1, 2, 1], ...]");

        listsAtLeast_helper(0, EP.naturalIntegers(),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        listsAtLeast_helper(1, EP.naturalIntegers(),
                "[[0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], [0, 1, 0], ...]");
        listsAtLeast_helper(2, EP.naturalIntegers(),
                "[[0, 0], [0, 0, 0], [0, 1], [0, 0, 0, 0], [1, 0], [0, 0, 1], [1, 1], [0, 0, 0, 0, 0], [0, 2]," +
                " [0, 1, 0], [0, 3], [0, 0, 0, 1], [1, 2], [0, 1, 1], [1, 3], [0, 0, 0, 0, 0, 0], [2, 0], [1, 0, 0]," +
                " [2, 1], [0, 0, 1, 0], ...]");
        listsAtLeast_helper(3, EP.naturalIntegers(),
                "[[0, 0, 0], [0, 0, 0, 0], [0, 0, 1], [0, 0, 0, 0, 0], [0, 1, 0], [0, 0, 0, 1], [0, 1, 1]," +
                " [0, 0, 0, 0, 0, 0], [1, 0, 0], [0, 0, 1, 0], [1, 0, 1], [0, 0, 0, 0, 1], [1, 1, 0], [0, 0, 1, 1]," +
                " [1, 1, 1], [0, 0, 0, 0, 0, 0, 0], [0, 0, 2], [0, 1, 0, 0], [0, 0, 3], [0, 0, 0, 1, 0], ...]");

        listsAtLeast_helper(0, repeat(1),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        listsAtLeast_helper(1, repeat(1),
                "[[1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], ...]");
        listsAtLeast_helper(2, repeat(1),
                "[[1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1], [1, 1], [1, 1, 1]," +
                " [1, 1], [1, 1, 1, 1], ...]");
        listsAtLeast_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], ...]");

        try {
            EP.listsAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.listsAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsAtLeast_String_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringsAtLeast_String() {
        stringsAtLeast_String_helper(0, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(0, "")), 1);
        stringsAtLeast_String_helper(1, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(1, "")), 0);
        stringsAtLeast_String_helper(2, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(2, "")), 0);
        stringsAtLeast_String_helper(3, "", "[]");
        aeq(length(EP.stringsShortlexAtLeast(3, "")), 0);
        stringsAtLeast_String_helper(0, "a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringsAtLeast_String_helper(1, "a",
                "[a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, ...]");
        stringsAtLeast_String_helper(2, "a",
                "[aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa, ...]");
        stringsAtLeast_String_helper(3, "a",
                "[aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaa, ...]");
        stringsAtLeast_String_helper(0, "abc",
                "[, a, aa, b, aaa, c, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, bc, aaab, ca, abb, cb, aaaaaa, ...]");
        stringsAtLeast_String_helper(1, "abc",
                "[a, aa, b, aaa, c, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, bc, aaab, ca, abb, cb, aaaaaa, cc, ...]");
        stringsAtLeast_String_helper(2, "abc",
                "[aa, aaa, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, bc, aaab, ca, abb, cb, aaaaaa, cc, baa, aaba, bab," +
                " ...]");
        stringsAtLeast_String_helper(3, "abc",
                "[aaa, aaaa, aab, aaaaa, aba, aaab, abb, aaaaaa, baa, aaba, bab, aaaab, bba, aabb, bbb, aaaaaaa," +
                " aac, abaa, abc, aaaba, ...]");
        stringsAtLeast_String_helper(0, "abbc",
                "[, a, aa, b, aaa, b, ab, c, aaaa, ba, aab, bb, aaaaa, ab, aba, ac, aaab, bb, abb, bc, ...]");
        stringsAtLeast_String_helper(1, "abbc",
                "[a, aa, b, aaa, b, ab, c, aaaa, ba, aab, bb, aaaaa, ab, aba, ac, aaab, bb, abb, bc, aaaaaa, ...]");
        stringsAtLeast_String_helper(2, "abbc",
                "[aa, aaa, ab, aaaa, ba, aab, bb, aaaaa, ab, aba, ac, aaab, bb, abb, bc, aaaaaa, ba, baa, bb, aaba," +
                " ...]");
        stringsAtLeast_String_helper(3, "abbc",
                "[aaa, aaaa, aab, aaaaa, aba, aaab, abb, aaaaaa, baa, aaba, bab, aaaab, bba, aabb, bbb, aaaaaaa," +
                " aab, abaa, aac, aaaba, ...]");
        stringsAtLeast_String_helper(0, "Mississippi",
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, iM, s, MMi, s, ii, i, MMMMM, p, Ms, p, ...]");
        stringsAtLeast_String_helper(1, "Mississippi",
                "[M, MM, i, MMM, s, Mi, s, MMMM, i, iM, s, MMi, s, ii, i, MMMMM, p, Ms, p, MiM, ...]");
        stringsAtLeast_String_helper(2, "Mississippi",
                "[MM, MMM, Mi, MMMM, iM, MMi, ii, MMMMM, Ms, MiM, Ms, MMMi, is, Mii, is, MMMMMM, sM, iMM, si, MMiM," +
                " ...]");
        stringsAtLeast_String_helper(3, "Mississippi",
                "[MMM, MMMM, MMi, MMMMM, MiM, MMMi, Mii, MMMMMM, iMM, MMiM, iMi, MMMMi, iiM, MMii, iii, MMMMMMM," +
                " MMs, MiMM, MMs, MMMiM, ...]");
        try {
            EP.stringsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringsAtLeast(minSize), output);
    }

    @Test
    public void testStringsAtLeast() {
        stringsAtLeast_helper(0, "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]");
        stringsAtLeast_helper(1,
                "[a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, aba, ...]");
        stringsAtLeast_helper(2,
                "[aa, aaa, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, ad, aaab, bc, abb, bd, aaaaaa, ca, baa, cb, aaba," +
                " ...]");
        stringsAtLeast_helper(3,
                "[aaa, aaaa, aab, aaaaa, aba, aaab, abb, aaaaaa, baa, aaba, bab, aaaab, bba, aabb, bbb, aaaaaaa," +
                " aac, abaa, aad, aaaba, ...]");
        try {
            EP.stringsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctListsLex(size, readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsLex_int_List() {
        distinctListsLex_int_List_helper(0, "[]", "[[]]");
        distinctListsLex_int_List_helper(1, "[]", "[]");
        distinctListsLex_int_List_helper(2, "[]", "[]");
        distinctListsLex_int_List_helper(3, "[]", "[]");
        distinctListsLex_int_List_helper(0, "[5]", "[[]]");
        distinctListsLex_int_List_helper(1, "[5]", "[[5]]");
        distinctListsLex_int_List_helper(2, "[5]", "[]");
        distinctListsLex_int_List_helper(3, "[5]", "[]");
        distinctListsLex_int_List_helper(0, "[1, 2, 3]", "[[]]");
        distinctListsLex_int_List_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        distinctListsLex_int_List_helper(2, "[1, 2, 3]", "[[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2]]");
        distinctListsLex_int_List_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsLex_int_List_helper(0, "[1, 2, 2, 3]", "[[]]");
        distinctListsLex_int_List_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        distinctListsLex_int_List_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 2]]");
        distinctListsLex_int_List_helper(3, "[1, 2, 2, 3]",
                "[[1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2]," +
                " [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2]]");
        distinctListsLex_int_List_helper(0, "[1, null, 3]", "[[]]");
        distinctListsLex_int_List_helper(1, "[1, null, 3]", "[[1], [null], [3]]");
        distinctListsLex_int_List_helper(2, "[1, null, 3]",
                "[[1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null]]");
        distinctListsLex_int_List_helper(3, "[1, null, 3]",
                "[[1, null, 3], [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        try {
            EP.distinctListsLex(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctListsLex(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctPairsLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctPairsLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctPairsLex() {
        distinctPairsLex_helper("[]", "[]");
        distinctPairsLex_helper("[5]", "[]");
        distinctPairsLex_helper("[1, 2, 3, 4]",
                "[(1, 2), (1, 3), (1, 4), (2, 1), (2, 3), (2, 4), (3, 1), (3, 2), (3, 4), (4, 1), (4, 2), (4, 3)]");
        distinctPairsLex_helper("[1, 2, 2, 4]",
                "[(1, 2), (1, 2), (1, 4), (2, 1), (2, 2), (2, 4), (2, 1), (2, 2), (2, 4), (4, 1), (4, 2), (4, 2)]");
        distinctPairsLex_helper("[1, 2, null, 4]",
                "[(1, 2), (1, null), (1, 4), (2, 1), (2, null), (2, 4), (null, 1), (null, 2), (null, 4), (4, 1)," +
                " (4, 2), (4, null)]");
    }

    private static void distinctTriplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctTriplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctTriplesLex() {
        distinctTriplesLex_helper("[]", "[]");
        distinctTriplesLex_helper("[5]", "[]");
        distinctTriplesLex_helper("[1, 2, 3, 4]",
                "[(1, 2, 3), (1, 2, 4), (1, 3, 2), (1, 3, 4), (1, 4, 2), (1, 4, 3), (2, 1, 3), (2, 1, 4), (2, 3, 1)," +
                " (2, 3, 4), (2, 4, 1), (2, 4, 3), (3, 1, 2), (3, 1, 4), (3, 2, 1), (3, 2, 4), (3, 4, 1), (3, 4, 2)," +
                " (4, 1, 2), (4, 1, 3), ...]");
        distinctTriplesLex_helper("[1, 2, 2, 4]",
                "[(1, 2, 2), (1, 2, 4), (1, 2, 2), (1, 2, 4), (1, 4, 2), (1, 4, 2), (2, 1, 2), (2, 1, 4), (2, 2, 1)," +
                " (2, 2, 4), (2, 4, 1), (2, 4, 2), (2, 1, 2), (2, 1, 4), (2, 2, 1), (2, 2, 4), (2, 4, 1), (2, 4, 2)," +
                " (4, 1, 2), (4, 1, 2), ...]");
        distinctTriplesLex_helper("[1, 2, null, 4]",
                "[(1, 2, null), (1, 2, 4), (1, null, 2), (1, null, 4), (1, 4, 2), (1, 4, null), (2, 1, null)," +
                " (2, 1, 4), (2, null, 1), (2, null, 4), (2, 4, 1), (2, 4, null), (null, 1, 2), (null, 1, 4)," +
                " (null, 2, 1), (null, 2, 4), (null, 4, 1), (null, 4, 2), (4, 1, 2), (4, 1, null), ...]");
    }

    private static void distinctQuadruplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctQuadruplesLex() {
        distinctQuadruplesLex_helper("[]", "[]");
        distinctQuadruplesLex_helper("[5]", "[]");
        distinctQuadruplesLex_helper("[1, 2, 3, 4]",
                "[(1, 2, 3, 4), (1, 2, 4, 3), (1, 3, 2, 4), (1, 3, 4, 2), (1, 4, 2, 3), (1, 4, 3, 2), (2, 1, 3, 4)," +
                " (2, 1, 4, 3), (2, 3, 1, 4), (2, 3, 4, 1), (2, 4, 1, 3), (2, 4, 3, 1), (3, 1, 2, 4), (3, 1, 4, 2)," +
                " (3, 2, 1, 4), (3, 2, 4, 1), (3, 4, 1, 2), (3, 4, 2, 1), (4, 1, 2, 3), (4, 1, 3, 2), ...]");
        distinctQuadruplesLex_helper("[1, 2, 2, 4]",
                "[(1, 2, 2, 4), (1, 2, 4, 2), (1, 2, 2, 4), (1, 2, 4, 2), (1, 4, 2, 2), (1, 4, 2, 2), (2, 1, 2, 4)," +
                " (2, 1, 4, 2), (2, 2, 1, 4), (2, 2, 4, 1), (2, 4, 1, 2), (2, 4, 2, 1), (2, 1, 2, 4), (2, 1, 4, 2)," +
                " (2, 2, 1, 4), (2, 2, 4, 1), (2, 4, 1, 2), (2, 4, 2, 1), (4, 1, 2, 2), (4, 1, 2, 2), ...]");
        distinctQuadruplesLex_helper("[1, 2, null, 4]",
                "[(1, 2, null, 4), (1, 2, 4, null), (1, null, 2, 4), (1, null, 4, 2), (1, 4, 2, null)," +
                " (1, 4, null, 2), (2, 1, null, 4), (2, 1, 4, null), (2, null, 1, 4), (2, null, 4, 1)," +
                " (2, 4, 1, null), (2, 4, null, 1), (null, 1, 2, 4), (null, 1, 4, 2), (null, 2, 1, 4)," +
                " (null, 2, 4, 1), (null, 4, 1, 2), (null, 4, 2, 1), (4, 1, 2, null), (4, 1, null, 2), ...]");
    }

    private static void distinctQuintuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuintuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctQuintuplesLex() {
        distinctQuintuplesLex_helper("[]", "[]");
        distinctQuintuplesLex_helper("[5]", "[]");
        distinctQuintuplesLex_helper("[1, 2, 3, 4]", "[]");
        distinctQuintuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5), (1, 2, 3, 4, 6), (1, 2, 3, 4, 7), (1, 2, 3, 4, 8), (1, 2, 3, 5, 4)," +
                " (1, 2, 3, 5, 6), (1, 2, 3, 5, 7), (1, 2, 3, 5, 8), (1, 2, 3, 6, 4), (1, 2, 3, 6, 5)," +
                " (1, 2, 3, 6, 7), (1, 2, 3, 6, 8), (1, 2, 3, 7, 4), (1, 2, 3, 7, 5), (1, 2, 3, 7, 6)," +
                " (1, 2, 3, 7, 8), (1, 2, 3, 8, 4), (1, 2, 3, 8, 5), (1, 2, 3, 8, 6), (1, 2, 3, 8, 7), ...]");
        distinctQuintuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5), (1, 2, 2, 4, 6), (1, 2, 2, 4, 7), (1, 2, 2, 4, 8), (1, 2, 2, 5, 4)," +
                " (1, 2, 2, 5, 6), (1, 2, 2, 5, 7), (1, 2, 2, 5, 8), (1, 2, 2, 6, 4), (1, 2, 2, 6, 5)," +
                " (1, 2, 2, 6, 7), (1, 2, 2, 6, 8), (1, 2, 2, 7, 4), (1, 2, 2, 7, 5), (1, 2, 2, 7, 6)," +
                " (1, 2, 2, 7, 8), (1, 2, 2, 8, 4), (1, 2, 2, 8, 5), (1, 2, 2, 8, 6), (1, 2, 2, 8, 7), ...]");
        distinctQuintuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5), (1, 2, null, 4, 6), (1, 2, null, 4, 7), (1, 2, null, 4, 8)," +
                " (1, 2, null, 5, 4), (1, 2, null, 5, 6), (1, 2, null, 5, 7), (1, 2, null, 5, 8)," +
                " (1, 2, null, 6, 4), (1, 2, null, 6, 5), (1, 2, null, 6, 7), (1, 2, null, 6, 8)," +
                " (1, 2, null, 7, 4), (1, 2, null, 7, 5), (1, 2, null, 7, 6), (1, 2, null, 7, 8)," +
                " (1, 2, null, 8, 4), (1, 2, null, 8, 5), (1, 2, null, 8, 6), (1, 2, null, 8, 7), ...]");
    }

    private static void distinctSextuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSextuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctSextuplesLex() {
        distinctSextuplesLex_helper("[]", "[]");
        distinctSextuplesLex_helper("[5]", "[]");
        distinctSextuplesLex_helper("[1, 2, 3, 4]", "[]");
        distinctSextuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6), (1, 2, 3, 4, 5, 7), (1, 2, 3, 4, 5, 8), (1, 2, 3, 4, 6, 5)," +
                " (1, 2, 3, 4, 6, 7), (1, 2, 3, 4, 6, 8), (1, 2, 3, 4, 7, 5), (1, 2, 3, 4, 7, 6)," +
                " (1, 2, 3, 4, 7, 8), (1, 2, 3, 4, 8, 5), (1, 2, 3, 4, 8, 6), (1, 2, 3, 4, 8, 7)," +
                " (1, 2, 3, 5, 4, 6), (1, 2, 3, 5, 4, 7), (1, 2, 3, 5, 4, 8), (1, 2, 3, 5, 6, 4)," +
                " (1, 2, 3, 5, 6, 7), (1, 2, 3, 5, 6, 8), (1, 2, 3, 5, 7, 4), (1, 2, 3, 5, 7, 6), ...]");
        distinctSextuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6), (1, 2, 2, 4, 5, 7), (1, 2, 2, 4, 5, 8), (1, 2, 2, 4, 6, 5)," +
                " (1, 2, 2, 4, 6, 7), (1, 2, 2, 4, 6, 8), (1, 2, 2, 4, 7, 5), (1, 2, 2, 4, 7, 6)," +
                " (1, 2, 2, 4, 7, 8), (1, 2, 2, 4, 8, 5), (1, 2, 2, 4, 8, 6), (1, 2, 2, 4, 8, 7)," +
                " (1, 2, 2, 5, 4, 6), (1, 2, 2, 5, 4, 7), (1, 2, 2, 5, 4, 8), (1, 2, 2, 5, 6, 4)," +
                " (1, 2, 2, 5, 6, 7), (1, 2, 2, 5, 6, 8), (1, 2, 2, 5, 7, 4), (1, 2, 2, 5, 7, 6), ...]");
        distinctSextuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5, 6), (1, 2, null, 4, 5, 7), (1, 2, null, 4, 5, 8), (1, 2, null, 4, 6, 5)," +
                " (1, 2, null, 4, 6, 7), (1, 2, null, 4, 6, 8), (1, 2, null, 4, 7, 5), (1, 2, null, 4, 7, 6)," +
                " (1, 2, null, 4, 7, 8), (1, 2, null, 4, 8, 5), (1, 2, null, 4, 8, 6), (1, 2, null, 4, 8, 7)," +
                " (1, 2, null, 5, 4, 6), (1, 2, null, 5, 4, 7), (1, 2, null, 5, 4, 8), (1, 2, null, 5, 6, 4)," +
                " (1, 2, null, 5, 6, 7), (1, 2, null, 5, 6, 8), (1, 2, null, 5, 7, 4), (1, 2, null, 5, 7, 6), ...]");
    }

    private static void distinctSeptuplesLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSeptuplesLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctSeptuplesLex() {
        distinctSeptuplesLex_helper("[]", "[]");
        distinctSeptuplesLex_helper("[5]", "[]");
        distinctSeptuplesLex_helper("[1, 2, 3, 4]", "[]");
        distinctSeptuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6, 7), (1, 2, 3, 4, 5, 6, 8), (1, 2, 3, 4, 5, 7, 6), (1, 2, 3, 4, 5, 7, 8)," +
                " (1, 2, 3, 4, 5, 8, 6), (1, 2, 3, 4, 5, 8, 7), (1, 2, 3, 4, 6, 5, 7), (1, 2, 3, 4, 6, 5, 8)," +
                " (1, 2, 3, 4, 6, 7, 5), (1, 2, 3, 4, 6, 7, 8), (1, 2, 3, 4, 6, 8, 5), (1, 2, 3, 4, 6, 8, 7)," +
                " (1, 2, 3, 4, 7, 5, 6), (1, 2, 3, 4, 7, 5, 8), (1, 2, 3, 4, 7, 6, 5), (1, 2, 3, 4, 7, 6, 8)," +
                " (1, 2, 3, 4, 7, 8, 5), (1, 2, 3, 4, 7, 8, 6), (1, 2, 3, 4, 8, 5, 6), (1, 2, 3, 4, 8, 5, 7), ...]");
        distinctSeptuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6, 7), (1, 2, 2, 4, 5, 6, 8), (1, 2, 2, 4, 5, 7, 6), (1, 2, 2, 4, 5, 7, 8)," +
                " (1, 2, 2, 4, 5, 8, 6), (1, 2, 2, 4, 5, 8, 7), (1, 2, 2, 4, 6, 5, 7), (1, 2, 2, 4, 6, 5, 8)," +
                " (1, 2, 2, 4, 6, 7, 5), (1, 2, 2, 4, 6, 7, 8), (1, 2, 2, 4, 6, 8, 5), (1, 2, 2, 4, 6, 8, 7)," +
                " (1, 2, 2, 4, 7, 5, 6), (1, 2, 2, 4, 7, 5, 8), (1, 2, 2, 4, 7, 6, 5), (1, 2, 2, 4, 7, 6, 8)," +
                " (1, 2, 2, 4, 7, 8, 5), (1, 2, 2, 4, 7, 8, 6), (1, 2, 2, 4, 8, 5, 6), (1, 2, 2, 4, 8, 5, 7), ...]");
        distinctSeptuplesLex_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5, 6, 7), (1, 2, null, 4, 5, 6, 8), (1, 2, null, 4, 5, 7, 6)," +
                " (1, 2, null, 4, 5, 7, 8), (1, 2, null, 4, 5, 8, 6), (1, 2, null, 4, 5, 8, 7)," +
                " (1, 2, null, 4, 6, 5, 7), (1, 2, null, 4, 6, 5, 8), (1, 2, null, 4, 6, 7, 5)," +
                " (1, 2, null, 4, 6, 7, 8), (1, 2, null, 4, 6, 8, 5), (1, 2, null, 4, 6, 8, 7)," +
                " (1, 2, null, 4, 7, 5, 6), (1, 2, null, 4, 7, 5, 8), (1, 2, null, 4, 7, 6, 5)," +
                " (1, 2, null, 4, 7, 6, 8), (1, 2, null, 4, 7, 8, 5), (1, 2, null, 4, 7, 8, 6)," +
                " (1, 2, null, 4, 8, 5, 6), (1, 2, null, 4, 8, 5, 7), ...]");
    }

    private static void distinctStringsLex_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctStringsLex(size, input), output);
    }

    private static void distinctStringsLex_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsLex(size, input), output);
    }

    @Test
    public void testDistinctStringsLex_int_String() {
        distinctStringsLex_int_String_helper(0, "", "[]");
        aeq(length(EP.distinctStringsLex(0, "")), 1);
        distinctStringsLex_int_String_helper(1, "", "[]");
        aeq(length(EP.distinctStringsLex(1, "")), 0);
        distinctStringsLex_int_String_helper(2, "", "[]");
        aeq(length(EP.distinctStringsLex(2, "")), 0);
        distinctStringsLex_int_String_helper(3, "", "[]");
        aeq(length(EP.distinctStringsLex(3, "")), 0);
        distinctStringsLex_int_String_helper(0, "a", "[]");
        aeq(length(EP.distinctStringsLex(0, "a")), 1);
        distinctStringsLex_int_String_helper(1, "a", "[a]");
        distinctStringsLex_int_String_helper(2, "a", "[]");
        aeq(length(EP.distinctStringsLex(2, "a")), 0);
        distinctStringsLex_int_String_helper(3, "a", "[]");
        aeq(length(EP.distinctStringsLex(3, "a")), 0);
        distinctStringsLex_int_String_helper(0, "abc", "[]");
        aeq(length(EP.distinctStringsLex(0, "abc")), 1);
        distinctStringsLex_int_String_helper(1, "abc", "[a, b, c]");
        distinctStringsLex_int_String_helper(2, "abc", "[ab, ac, ba, bc, ca, cb]");
        distinctStringsLex_int_String_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStringsLex_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.distinctStringsLex(0, "abbc")), 1);
        distinctStringsLex_int_String_helper(1, "abbc", "[a, b, b, c]");
        distinctStringsLex_int_String_helper(2, "abbc", "[ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb]");
        distinctStringsLex_int_String_helper(3, "abbc",
                "[abb, abc, abb, abc, acb, acb, bab, bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab," +
                " cab, cba, cbb, cba, cbb]");
        distinctStringsLex_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.distinctStringsLex(0, "Mississippi")), 1);
        distinctStringsLex_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        distinctStringsLex_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, is, is, ii, is, is, ii, ip, ip, ii, ...]");
        distinctStringsLex_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Mii, Mis, Mis, Mii, Mip, Mip, Mii, Msi, Mss, Msi, Mss, Mss, Msi, Msp, Msp, Msi, Msi," +
                " Mss, ...]");
        try {
            EP.distinctStringsLex(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctStringsLex(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.distinctListsLex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsLex_List() {
        distinctListsLex_List_helper("[]", "[[]]");
        distinctListsLex_List_helper("[5]", "[[], [5]]");
        distinctListsLex_List_helper("[1, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 3], [1, 3, 2], [2], [2, 1], [2, 1, 3], [2, 3], [2, 3, 1], [3]," +
                " [3, 1], [3, 1, 2], [3, 2], [3, 2, 1]]");
        distinctListsLex_List_helper("[1, null, 3]",
                "[[], [1], [1, null], [1, null, 3], [1, 3], [1, 3, null], [null], [null, 1], [null, 1, 3]," +
                " [null, 3], [null, 3, 1], [3], [3, 1], [3, 1, null], [3, null], [3, null, 1]]");
        distinctListsLex_List_helper("[1, 2, 3, 4]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 2, 4, 3], [1, 3], [1, 3, 2]," +
                " [1, 3, 2, 4], [1, 3, 4], [1, 3, 4, 2], [1, 4], [1, 4, 2], [1, 4, 2, 3], [1, 4, 3], [1, 4, 3, 2]," +
                " [2], [2, 1], [2, 1, 3], [2, 1, 3, 4], [2, 1, 4], [2, 1, 4, 3], [2, 3], [2, 3, 1], [2, 3, 1, 4]," +
                " [2, 3, 4], [2, 3, 4, 1], [2, 4], [2, 4, 1], [2, 4, 1, 3], [2, 4, 3], [2, 4, 3, 1], [3], [3, 1]," +
                " [3, 1, 2], [3, 1, 2, 4], [3, 1, 4], [3, 1, 4, 2], [3, 2], [3, 2, 1], [3, 2, 1, 4], [3, 2, 4]," +
                " [3, 2, 4, 1], [3, 4], [3, 4, 1], [3, 4, 1, 2], [3, 4, 2], [3, 4, 2, 1], [4], [4, 1], [4, 1, 2]," +
                " [4, 1, 2, 3], [4, 1, 3], [4, 1, 3, 2], [4, 2], [4, 2, 1], [4, 2, 1, 3], [4, 2, 3], [4, 2, 3, 1]," +
                " [4, 3], [4, 3, 1], [4, 3, 1, 2], [4, 3, 2], [4, 3, 2, 1]]");
        distinctListsLex_List_helper("[1, 2, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 2], [1, 2, 2]," +
                " [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 3], [1, 3, 2], [1, 3, 2, 2], [1, 3, 2], [1, 3, 2, 2]," +
                " [2], [2, 1], [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3]," +
                " [2, 2, 3], [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [2], [2, 1]," +
                " [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3]," +
                " [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [3], [3, 1], [3, 1, 2]," +
                " [3, 1, 2, 2], [3, 1, 2], [3, 1, 2, 2], [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]," +
                " [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]]");
    }

    @Test
    public void testDistinctStringsLex_String() {
        aeqit(EP.distinctStringsLex(""), "[]");
        aeq(length(EP.distinctStringsLex("")), 1);
        aeqit(EP.distinctStringsLex("a"), "[, a]");
        aeqit(EP.distinctStringsLex("abc"), "[, a, ab, abc, ac, acb, b, ba, bac, bc, bca, c, ca, cab, cb, cba]");
        aeqit(EP.distinctStringsLex("abcd"),
                "[, a, ab, abc, abcd, abd, abdc, ac, acb, acbd, acd, acdb, ad, adb, adbc, adc, adcb, b, ba, bac," +
                " bacd, bad, badc, bc, bca, bcad, bcd, bcda, bd, bda, bdac, bdc, bdca, c, ca, cab, cabd, cad, cadb," +
                " cb, cba, cbad, cbd, cbda, cd, cda, cdab, cdb, cdba, d, da, dab, dabc, dac, dacb, db, dba, dbac," +
                " dbc, dbca, dc, dca, dcab, dcb, dcba]");
        aeqit(EP.distinctStringsLex("abbc"),
                "[, a, ab, abb, abbc, abc, abcb, ab, abb, abbc, abc, abcb, ac, acb, acbb, acb, acbb, b, ba, bab," +
                " babc, bac, bacb, bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, b, ba, bab, babc, bac, bacb," +
                " bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, c, ca, cab, cabb, cab, cabb, cb, cba, cbab," +
                " cbb, cbba, cb, cba, cbab, cbb, cbba]");
        simpleProviderHelper(EP.distinctStringsLex("Mississippi"),
                "[, M, Mi, Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi," +
                " Mississipi, Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii," +
                " ...]");
    }

    private static void distinctListsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctListsLexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsLexAtLeast() {
        distinctListsLexAtLeast_helper(0, "[]", "[[]]");
        distinctListsLexAtLeast_helper(1, "[]", "[]");
        distinctListsLexAtLeast_helper(2, "[]", "[]");
        distinctListsLexAtLeast_helper(3, "[]", "[]");

        distinctListsLexAtLeast_helper(0, "[5]", "[[], [5]]");
        distinctListsLexAtLeast_helper(1, "[5]", "[[5]]");
        distinctListsLexAtLeast_helper(2, "[5]", "[]");
        distinctListsLexAtLeast_helper(3, "[5]", "[]");

        distinctListsLexAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 3], [1, 3, 2], [2], [2, 1], [2, 1, 3], [2, 3], [2, 3, 1], [3]," +
                " [3, 1], [3, 1, 2], [3, 2], [3, 2, 1]]");
        distinctListsLexAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [1, 2], [1, 2, 3], [1, 3], [1, 3, 2], [2], [2, 1], [2, 1, 3], [2, 3], [2, 3, 1], [3], [3, 1]," +
                " [3, 1, 2], [3, 2], [3, 2, 1]]");
        distinctListsLexAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 2], [1, 2, 3], [1, 3], [1, 3, 2], [2, 1], [2, 1, 3], [2, 3], [2, 3, 1], [3, 1], [3, 1, 2]," +
                " [3, 2], [3, 2, 1]]");
        distinctListsLexAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");

        distinctListsLexAtLeast_helper(0, "[1, null, 3]",
                "[[], [1], [1, null], [1, null, 3], [1, 3], [1, 3, null], [null], [null, 1], [null, 1, 3]," +
                " [null, 3], [null, 3, 1], [3], [3, 1], [3, 1, null], [3, null], [3, null, 1]]");
        distinctListsLexAtLeast_helper(1, "[1, null, 3]",
                "[[1], [1, null], [1, null, 3], [1, 3], [1, 3, null], [null], [null, 1], [null, 1, 3], [null, 3]," +
                " [null, 3, 1], [3], [3, 1], [3, 1, null], [3, null], [3, null, 1]]");
        distinctListsLexAtLeast_helper(2, "[1, null, 3]",
                "[[1, null], [1, null, 3], [1, 3], [1, 3, null], [null, 1], [null, 1, 3], [null, 3], [null, 3, 1]," +
                " [3, 1], [3, 1, null], [3, null], [3, null, 1]]");
        distinctListsLexAtLeast_helper(3, "[1, null, 3]",
                "[[1, null, 3], [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");

        distinctListsLexAtLeast_helper(0, "[1, 2, 3, 4]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 2, 4, 3], [1, 3], [1, 3, 2]," +
                " [1, 3, 2, 4], [1, 3, 4], [1, 3, 4, 2], [1, 4], [1, 4, 2], [1, 4, 2, 3], [1, 4, 3], [1, 4, 3, 2]," +
                " [2], [2, 1], [2, 1, 3], [2, 1, 3, 4], [2, 1, 4], [2, 1, 4, 3], [2, 3], [2, 3, 1], [2, 3, 1, 4]," +
                " [2, 3, 4], [2, 3, 4, 1], [2, 4], [2, 4, 1], [2, 4, 1, 3], [2, 4, 3], [2, 4, 3, 1], [3], [3, 1]," +
                " [3, 1, 2], [3, 1, 2, 4], [3, 1, 4], [3, 1, 4, 2], [3, 2], [3, 2, 1], [3, 2, 1, 4], [3, 2, 4]," +
                " [3, 2, 4, 1], [3, 4], [3, 4, 1], [3, 4, 1, 2], [3, 4, 2], [3, 4, 2, 1], [4], [4, 1], [4, 1, 2]," +
                " [4, 1, 2, 3], [4, 1, 3], [4, 1, 3, 2], [4, 2], [4, 2, 1], [4, 2, 1, 3], [4, 2, 3], [4, 2, 3, 1]," +
                " [4, 3], [4, 3, 1], [4, 3, 1, 2], [4, 3, 2], [4, 3, 2, 1]]");
        distinctListsLexAtLeast_helper(1, "[1, 2, 3, 4]",
                "[[1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 2, 4, 3], [1, 3], [1, 3, 2], [1, 3, 2, 4]," +
                " [1, 3, 4], [1, 3, 4, 2], [1, 4], [1, 4, 2], [1, 4, 2, 3], [1, 4, 3], [1, 4, 3, 2], [2], [2, 1]," +
                " [2, 1, 3], [2, 1, 3, 4], [2, 1, 4], [2, 1, 4, 3], [2, 3], [2, 3, 1], [2, 3, 1, 4], [2, 3, 4]," +
                " [2, 3, 4, 1], [2, 4], [2, 4, 1], [2, 4, 1, 3], [2, 4, 3], [2, 4, 3, 1], [3], [3, 1], [3, 1, 2]," +
                " [3, 1, 2, 4], [3, 1, 4], [3, 1, 4, 2], [3, 2], [3, 2, 1], [3, 2, 1, 4], [3, 2, 4], [3, 2, 4, 1]," +
                " [3, 4], [3, 4, 1], [3, 4, 1, 2], [3, 4, 2], [3, 4, 2, 1], [4], [4, 1], [4, 1, 2], [4, 1, 2, 3]," +
                " [4, 1, 3], [4, 1, 3, 2], [4, 2], [4, 2, 1], [4, 2, 1, 3], [4, 2, 3], [4, 2, 3, 1], [4, 3]," +
                " [4, 3, 1], [4, 3, 1, 2], [4, 3, 2], [4, 3, 2, 1]]");
        distinctListsLexAtLeast_helper(2, "[1, 2, 3, 4]",
                "[[1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 2, 4, 3], [1, 3], [1, 3, 2], [1, 3, 2, 4]," +
                " [1, 3, 4], [1, 3, 4, 2], [1, 4], [1, 4, 2], [1, 4, 2, 3], [1, 4, 3], [1, 4, 3, 2], [2, 1]," +
                " [2, 1, 3], [2, 1, 3, 4], [2, 1, 4], [2, 1, 4, 3], [2, 3], [2, 3, 1], [2, 3, 1, 4], [2, 3, 4]," +
                " [2, 3, 4, 1], [2, 4], [2, 4, 1], [2, 4, 1, 3], [2, 4, 3], [2, 4, 3, 1], [3, 1], [3, 1, 2]," +
                " [3, 1, 2, 4], [3, 1, 4], [3, 1, 4, 2], [3, 2], [3, 2, 1], [3, 2, 1, 4], [3, 2, 4], [3, 2, 4, 1]," +
                " [3, 4], [3, 4, 1], [3, 4, 1, 2], [3, 4, 2], [3, 4, 2, 1], [4, 1], [4, 1, 2], [4, 1, 2, 3]," +
                " [4, 1, 3], [4, 1, 3, 2], [4, 2], [4, 2, 1], [4, 2, 1, 3], [4, 2, 3], [4, 2, 3, 1], [4, 3]," +
                " [4, 3, 1], [4, 3, 1, 2], [4, 3, 2], [4, 3, 2, 1]]");
        distinctListsLexAtLeast_helper(3, "[1, 2, 3, 4]",
                "[[1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 2, 4, 3], [1, 3, 2], [1, 3, 2, 4], [1, 3, 4]," +
                " [1, 3, 4, 2], [1, 4, 2], [1, 4, 2, 3], [1, 4, 3], [1, 4, 3, 2], [2, 1, 3], [2, 1, 3, 4]," +
                " [2, 1, 4], [2, 1, 4, 3], [2, 3, 1], [2, 3, 1, 4], [2, 3, 4], [2, 3, 4, 1], [2, 4, 1]," +
                " [2, 4, 1, 3], [2, 4, 3], [2, 4, 3, 1], [3, 1, 2], [3, 1, 2, 4], [3, 1, 4], [3, 1, 4, 2]," +
                " [3, 2, 1], [3, 2, 1, 4], [3, 2, 4], [3, 2, 4, 1], [3, 4, 1], [3, 4, 1, 2], [3, 4, 2]," +
                " [3, 4, 2, 1], [4, 1, 2], [4, 1, 2, 3], [4, 1, 3], [4, 1, 3, 2], [4, 2, 1], [4, 2, 1, 3]," +
                " [4, 2, 3], [4, 2, 3, 1], [4, 3, 1], [4, 3, 1, 2], [4, 3, 2], [4, 3, 2, 1]]");

        distinctListsLexAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 2], [1, 2, 2]," +
                " [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 3], [1, 3, 2], [1, 3, 2, 2], [1, 3, 2], [1, 3, 2, 2]," +
                " [2], [2, 1], [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3]," +
                " [2, 2, 3], [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [2], [2, 1]," +
                " [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3]," +
                " [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [3], [3, 1], [3, 1, 2]," +
                " [3, 1, 2, 2], [3, 1, 2], [3, 1, 2, 2], [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]," +
                " [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]]");
        distinctListsLexAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 2], [1, 2, 2], [1, 2, 2, 3]," +
                " [1, 2, 3], [1, 2, 3, 2], [1, 3], [1, 3, 2], [1, 3, 2, 2], [1, 3, 2], [1, 3, 2, 2], [2], [2, 1]," +
                " [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3]," +
                " [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [2], [2, 1], [2, 1, 2]," +
                " [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3], [2, 2, 3, 1]," +
                " [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [3], [3, 1], [3, 1, 2], [3, 1, 2, 2]," +
                " [3, 1, 2], [3, 1, 2, 2], [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1], [3, 2]," +
                " [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]]");
        distinctListsLexAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 2], [1, 2, 2], [1, 2, 2, 3]," +
                " [1, 2, 3], [1, 2, 3, 2], [1, 3], [1, 3, 2], [1, 3, 2, 2], [1, 3, 2], [1, 3, 2, 2], [2, 1]," +
                " [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3]," +
                " [2, 2, 3, 1], [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [2, 1], [2, 1, 2]," +
                " [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2], [2, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3], [2, 2, 3, 1]," +
                " [2, 3], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [3, 1], [3, 1, 2], [3, 1, 2, 2]," +
                " [3, 1, 2], [3, 1, 2, 2], [3, 2], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1], [3, 2]," +
                " [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]]");
        distinctListsLexAtLeast_helper(3, "[1, 2, 2, 3]",
                "[[1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3]," +
                " [1, 2, 3, 2], [1, 3, 2], [1, 3, 2, 2], [1, 3, 2], [1, 3, 2, 2], [2, 1, 2], [2, 1, 2, 3]," +
                " [2, 1, 3], [2, 1, 3, 2], [2, 2, 1], [2, 2, 1, 3], [2, 2, 3], [2, 2, 3, 1], [2, 3, 1]," +
                " [2, 3, 1, 2], [2, 3, 2], [2, 3, 2, 1], [2, 1, 2], [2, 1, 2, 3], [2, 1, 3], [2, 1, 3, 2]," +
                " [2, 2, 1], [2, 2, 1, 3], [2, 2, 3], [2, 2, 3, 1], [2, 3, 1], [2, 3, 1, 2], [2, 3, 2]," +
                " [2, 3, 2, 1], [3, 1, 2], [3, 1, 2, 2], [3, 1, 2], [3, 1, 2, 2], [3, 2, 1], [3, 2, 1, 2]," +
                " [3, 2, 2], [3, 2, 2, 1], [3, 2, 1], [3, 2, 1, 2], [3, 2, 2], [3, 2, 2, 1]]");

        try {
            EP.distinctListsLexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctListsLexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctStringsLexAtLeast(minSize, input), output);
    }

    private static void distinctStringsLexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsLexAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsLexAtLeast_int_String() {
        distinctStringsLexAtLeast_helper(0, "", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(0, "")), 1);
        distinctStringsLexAtLeast_helper(1, "", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(1, "")), 0);
        distinctStringsLexAtLeast_helper(2, "", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(2, "")), 0);
        distinctStringsLexAtLeast_helper(3, "", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(3, "")), 0);
        distinctStringsLexAtLeast_helper(0, "a", "[, a]");
        distinctStringsLexAtLeast_helper(1, "a", "[a]");
        distinctStringsLexAtLeast_helper(2, "a", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(2, "a")), 0);
        distinctStringsLexAtLeast_helper(3, "a", "[]");
        aeq(length(EP.distinctStringsLexAtLeast(3, "a")), 0);
        distinctStringsLexAtLeast_helper(0, "abc",
                "[, a, ab, abc, ac, acb, b, ba, bac, bc, bca, c, ca, cab, cb, cba]");
        distinctStringsLexAtLeast_helper(1, "abc", "[a, ab, abc, ac, acb, b, ba, bac, bc, bca, c, ca, cab, cb, cba]");
        distinctStringsLexAtLeast_helper(2, "abc", "[ab, abc, ac, acb, ba, bac, bc, bca, ca, cab, cb, cba]");
        distinctStringsLexAtLeast_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStringsLexAtLeast_helper(0, "abbc",
                "[, a, ab, abb, abbc, abc, abcb, ab, abb, abbc, abc, abcb, ac, acb, acbb, acb, acbb, b, ba, bab," +
                " babc, bac, bacb, bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, b, ba, bab, babc, bac, bacb," +
                " bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, c, ca, cab, cabb, cab, cabb, cb, cba, cbab," +
                " cbb, cbba, cb, cba, cbab, cbb, cbba]");
        distinctStringsLexAtLeast_helper(1, "abbc",
                "[a, ab, abb, abbc, abc, abcb, ab, abb, abbc, abc, abcb, ac, acb, acbb, acb, acbb, b, ba, bab, babc," +
                " bac, bacb, bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, b, ba, bab, babc, bac, bacb, bb," +
                " bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, c, ca, cab, cabb, cab, cabb, cb, cba, cbab, cbb," +
                " cbba, cb, cba, cbab, cbb, cbba]");
        distinctStringsLexAtLeast_helper(2, "abbc",
                "[ab, abb, abbc, abc, abcb, ab, abb, abbc, abc, abcb, ac, acb, acbb, acb, acbb, ba, bab, babc, bac," +
                " bacb, bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, ba, bab, babc, bac, bacb, bb, bba, bbac," +
                " bbc, bbca, bc, bca, bcab, bcb, bcba, ca, cab, cabb, cab, cabb, cb, cba, cbab, cbb, cbba, cb, cba," +
                " cbab, cbb, cbba]");
        distinctStringsLexAtLeast_helper(3, "abbc",
                "[abb, abbc, abc, abcb, abb, abbc, abc, abcb, acb, acbb, acb, acbb, bab, babc, bac, bacb, bba, bbac," +
                " bbc, bbca, bca, bcab, bcb, bcba, bab, babc, bac, bacb, bba, bbac, bbc, bbca, bca, bcab, bcb, bcba," +
                " cab, cabb, cab, cabb, cba, cbab, cbb, cbba, cba, cbab, cbb, cbba]");
        distinctStringsLexAtLeast_helper_limit(0, "Mississippi",
                "[, M, Mi, Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi," +
                " Mississipi, Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii," +
                " ...]");
        distinctStringsLexAtLeast_helper_limit(1, "Mississippi",
                "[M, Mi, Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi," +
                " Mississipi, Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii," +
                " Mississiip, ...]");
        distinctStringsLexAtLeast_helper_limit(2, "Mississippi",
                "[Mi, Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi, Mississipi," +
                " Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii, Mississiip," +
                " Mississiipp, ...]");
        distinctStringsLexAtLeast_helper_limit(3, "Mississippi",
                "[Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi, Mississipi," +
                " Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii, Mississiip," +
                " Mississiipp, Mississiip, ...]");
        try {
            EP.distinctStringsLexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctStringsLexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsShortlex_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.distinctListsShortlex(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsShortlex() {
        distinctListsShortlex_helper("[]", "[[]]");
        distinctListsShortlex_helper("[5]", "[[], [5]]");
        distinctListsShortlex_helper("[1, 2, 3]",
                "[[], [1], [2], [3], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2]," +
                " [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlex_helper("[1, null, 3]",
                "[[], [1], [null], [3], [1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null], [1, null, 3]," +
                " [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsShortlex_helper("[1, 2, 3, 4]",
                "[[], [1], [2], [3], [4], [1, 2], [1, 3], [1, 4], [2, 1], [2, 3], [2, 4], [3, 1], [3, 2], [3, 4]," +
                " [4, 1], [4, 2], [4, 3], [1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [1, 4, 2], [1, 4, 3]," +
                " [2, 1, 3], [2, 1, 4], [2, 3, 1], [2, 3, 4], [2, 4, 1], [2, 4, 3], [3, 1, 2], [3, 1, 4], [3, 2, 1]," +
                " [3, 2, 4], [3, 4, 1], [3, 4, 2], [4, 1, 2], [4, 1, 3], [4, 2, 1], [4, 2, 3], [4, 3, 1], [4, 3, 2]," +
                " [1, 2, 3, 4], [1, 2, 4, 3], [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4]," +
                " [2, 1, 4, 3], [2, 3, 1, 4], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2]," +
                " [3, 2, 1, 4], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3]," +
                " [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]");
        distinctListsShortlex_helper("[1, 2, 2, 3]",
                "[[], [1], [2], [2], [3], [1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [2, 1], [2, 2], [2, 3]," +
                " [3, 1], [3, 2], [3, 2], [1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2]," +
                " [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2]," +
                " [1, 2, 2, 3], [1, 2, 3, 2], [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2, 2], [1, 3, 2, 2], [2, 1, 2, 3]," +
                " [2, 1, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [2, 1, 2, 3], [2, 1, 3, 2]," +
                " [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [3, 1, 2, 2], [3, 1, 2, 2], [3, 2, 1, 2]," +
                " [3, 2, 2, 1], [3, 2, 1, 2], [3, 2, 2, 1]]");
    }

    @Test
    public void testDistinctStringsShortlex() {
        aeqit(EP.distinctStringsShortlex(""), "[]");
        aeq(length(EP.distinctStringsShortlex("")), 1);
        aeqit(EP.distinctStringsShortlex("a"), "[, a]");
        aeqit(EP.distinctStringsShortlex("abc"), "[, a, b, c, ab, ac, ba, bc, ca, cb, abc, acb, bac, bca, cab, cba]");
        aeqit(EP.distinctStringsShortlex("abcd"),
                "[, a, b, c, d, ab, ac, ad, ba, bc, bd, ca, cb, cd, da, db, dc, abc, abd, acb, acd, adb, adc, bac," +
                " bad, bca, bcd, bda, bdc, cab, cad, cba, cbd, cda, cdb, dab, dac, dba, dbc, dca, dcb, abcd, abdc," +
                " acbd, acdb, adbc, adcb, bacd, badc, bcad, bcda, bdac, bdca, cabd, cadb, cbad, cbda, cdab, cdba," +
                " dabc, dacb, dbac, dbca, dcab, dcba]");
        aeqit(EP.distinctStringsShortlex("abbc"),
                "[, a, b, b, c, ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb, abb, abc, abb, abc, acb, acb, bab," +
                " bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab, cab, cba, cbb, cba, cbb, abbc, abcb," +
                " abbc, abcb, acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba, babc, bacb, bbac, bbca, bcab, bcba," +
                " cabb, cabb, cbab, cbba, cbab, cbba]");
        simpleProviderHelper(EP.distinctStringsShortlex("Mississippi"),
                "[, M, i, s, s, i, s, s, i, p, p, i, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, ...]");
    }

    private static void distinctListsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqit(EP.distinctListsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testDistinctListsShortlexAtLeast() {
        distinctListsShortlexAtLeast_helper(0, "[]", "[[]]");
        distinctListsShortlexAtLeast_helper(1, "[]", "[]");
        distinctListsShortlexAtLeast_helper(2, "[]", "[]");
        distinctListsShortlexAtLeast_helper(3, "[]", "[]");

        distinctListsShortlexAtLeast_helper(0, "[5]", "[[], [5]]");
        distinctListsShortlexAtLeast_helper(1, "[5]", "[[5]]");
        distinctListsShortlexAtLeast_helper(2, "[5]", "[]");
        distinctListsShortlexAtLeast_helper(3, "[5]", "[]");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [2], [3], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2]," +
                " [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [2], [3], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2], [2, 1, 3]," +
                " [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1]," +
                " [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");

        distinctListsShortlexAtLeast_helper(0, "[1, null, 3]",
                "[[], [1], [null], [3], [1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null], [1, null, 3]," +
                " [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsShortlexAtLeast_helper(1, "[1, null, 3]",
                "[[1], [null], [3], [1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null], [1, null, 3]," +
                " [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsShortlexAtLeast_helper(2, "[1, null, 3]",
                "[[1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null], [1, null, 3], [1, 3, null]," +
                " [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsShortlexAtLeast_helper(3, "[1, null, 3]",
                "[[1, null, 3], [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [2], [3], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2]," +
                " [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [2], [3], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2], [2, 1, 3]," +
                " [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1]," +
                " [3, 1, 2], [3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 3, 4]",
                "[[], [1], [2], [3], [4], [1, 2], [1, 3], [1, 4], [2, 1], [2, 3], [2, 4], [3, 1], [3, 2], [3, 4]," +
                " [4, 1], [4, 2], [4, 3], [1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [1, 4, 2], [1, 4, 3]," +
                " [2, 1, 3], [2, 1, 4], [2, 3, 1], [2, 3, 4], [2, 4, 1], [2, 4, 3], [3, 1, 2], [3, 1, 4], [3, 2, 1]," +
                " [3, 2, 4], [3, 4, 1], [3, 4, 2], [4, 1, 2], [4, 1, 3], [4, 2, 1], [4, 2, 3], [4, 3, 1], [4, 3, 2]," +
                " [1, 2, 3, 4], [1, 2, 4, 3], [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4]," +
                " [2, 1, 4, 3], [2, 3, 1, 4], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2]," +
                " [3, 2, 1, 4], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3]," +
                " [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 3, 4]",
                "[[1], [2], [3], [4], [1, 2], [1, 3], [1, 4], [2, 1], [2, 3], [2, 4], [3, 1], [3, 2], [3, 4]," +
                " [4, 1], [4, 2], [4, 3], [1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [1, 4, 2], [1, 4, 3]," +
                " [2, 1, 3], [2, 1, 4], [2, 3, 1], [2, 3, 4], [2, 4, 1], [2, 4, 3], [3, 1, 2], [3, 1, 4], [3, 2, 1]," +
                " [3, 2, 4], [3, 4, 1], [3, 4, 2], [4, 1, 2], [4, 1, 3], [4, 2, 1], [4, 2, 3], [4, 3, 1], [4, 3, 2]," +
                " [1, 2, 3, 4], [1, 2, 4, 3], [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4]," +
                " [2, 1, 4, 3], [2, 3, 1, 4], [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2]," +
                " [3, 2, 1, 4], [3, 2, 4, 1], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3]," +
                " [4, 2, 3, 1], [4, 3, 1, 2], [4, 3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 3, 4]",
                "[[1, 2], [1, 3], [1, 4], [2, 1], [2, 3], [2, 4], [3, 1], [3, 2], [3, 4], [4, 1], [4, 2], [4, 3]," +
                " [1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [1, 4, 2], [1, 4, 3], [2, 1, 3], [2, 1, 4], [2, 3, 1]," +
                " [2, 3, 4], [2, 4, 1], [2, 4, 3], [3, 1, 2], [3, 1, 4], [3, 2, 1], [3, 2, 4], [3, 4, 1], [3, 4, 2]," +
                " [4, 1, 2], [4, 1, 3], [4, 2, 1], [4, 2, 3], [4, 3, 1], [4, 3, 2], [1, 2, 3, 4], [1, 2, 4, 3]," +
                " [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4], [2, 1, 4, 3], [2, 3, 1, 4]," +
                " [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2], [3, 2, 1, 4], [3, 2, 4, 1]," +
                " [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3], [4, 2, 3, 1], [4, 3, 1, 2]," +
                " [4, 3, 2, 1]]");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 3, 4]",
                "[[1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [1, 4, 2], [1, 4, 3], [2, 1, 3], [2, 1, 4], [2, 3, 1]," +
                " [2, 3, 4], [2, 4, 1], [2, 4, 3], [3, 1, 2], [3, 1, 4], [3, 2, 1], [3, 2, 4], [3, 4, 1], [3, 4, 2]," +
                " [4, 1, 2], [4, 1, 3], [4, 2, 1], [4, 2, 3], [4, 3, 1], [4, 3, 2], [1, 2, 3, 4], [1, 2, 4, 3]," +
                " [1, 3, 2, 4], [1, 3, 4, 2], [1, 4, 2, 3], [1, 4, 3, 2], [2, 1, 3, 4], [2, 1, 4, 3], [2, 3, 1, 4]," +
                " [2, 3, 4, 1], [2, 4, 1, 3], [2, 4, 3, 1], [3, 1, 2, 4], [3, 1, 4, 2], [3, 2, 1, 4], [3, 2, 4, 1]," +
                " [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 1, 3, 2], [4, 2, 1, 3], [4, 2, 3, 1], [4, 3, 1, 2]," +
                " [4, 3, 2, 1]]");

        distinctListsShortlexAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [2], [2], [3], [1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [2, 1], [2, 2], [2, 3]," +
                " [3, 1], [3, 2], [3, 2], [1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2]," +
                " [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2]," +
                " [1, 2, 2, 3], [1, 2, 3, 2], [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2, 2], [1, 3, 2, 2], [2, 1, 2, 3]," +
                " [2, 1, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [2, 1, 2, 3], [2, 1, 3, 2]," +
                " [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [3, 1, 2, 2], [3, 1, 2, 2], [3, 2, 1, 2]," +
                " [3, 2, 2, 1], [3, 2, 1, 2], [3, 2, 2, 1]]");
        distinctListsShortlexAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [2], [2], [3], [1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [2, 1], [2, 2], [2, 3]," +
                " [3, 1], [3, 2], [3, 2], [1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2]," +
                " [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2]," +
                " [1, 2, 2, 3], [1, 2, 3, 2], [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2, 2], [1, 3, 2, 2], [2, 1, 2, 3]," +
                " [2, 1, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [2, 1, 2, 3], [2, 1, 3, 2]," +
                " [2, 2, 1, 3], [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [3, 1, 2, 2], [3, 1, 2, 2], [3, 2, 1, 2]," +
                " [3, 2, 2, 1], [3, 2, 1, 2], [3, 2, 2, 1]]");
        distinctListsShortlexAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 2]," +
                " [1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2]," +
                " [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2], [1, 2, 2, 3], [1, 2, 3, 2]," +
                " [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2, 2], [1, 3, 2, 2], [2, 1, 2, 3], [2, 1, 3, 2], [2, 2, 1, 3]," +
                " [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [2, 1, 2, 3], [2, 1, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1]," +
                " [2, 3, 1, 2], [2, 3, 2, 1], [3, 1, 2, 2], [3, 1, 2, 2], [3, 2, 1, 2], [3, 2, 2, 1], [3, 2, 1, 2]," +
                " [3, 2, 2, 1]]");
        distinctListsShortlexAtLeast_helper(3, "[1, 2, 2, 3]",
                "[[1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 2], [1, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1]," +
                " [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [2, 3, 1], [2, 3, 2]," +
                " [3, 1, 2], [3, 1, 2], [3, 2, 1], [3, 2, 2], [3, 2, 1], [3, 2, 2], [1, 2, 2, 3], [1, 2, 3, 2]," +
                " [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2, 2], [1, 3, 2, 2], [2, 1, 2, 3], [2, 1, 3, 2], [2, 2, 1, 3]," +
                " [2, 2, 3, 1], [2, 3, 1, 2], [2, 3, 2, 1], [2, 1, 2, 3], [2, 1, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1]," +
                " [2, 3, 1, 2], [2, 3, 2, 1], [3, 1, 2, 2], [3, 1, 2, 2], [3, 2, 1, 2], [3, 2, 2, 1], [3, 2, 1, 2]," +
                " [3, 2, 2, 1]]");

        try {
            EP.distinctListsShortlexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctListsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqit(EP.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    private static void distinctStringsShortlexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsShortlexAtLeast_int_String() {
        distinctStringsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(0, "")), 1);
        distinctStringsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(1, "")), 0);
        distinctStringsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(2, "")), 0);
        distinctStringsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(3, "")), 0);
        distinctStringsShortlexAtLeast_helper(0, "a", "[, a]");
        distinctStringsShortlexAtLeast_helper(1, "a", "[a]");
        distinctStringsShortlexAtLeast_helper(2, "a", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(2, "a")), 0);
        distinctStringsShortlexAtLeast_helper(3, "a", "[]");
        aeq(length(EP.distinctStringsShortlexAtLeast(3, "a")), 0);
        distinctStringsShortlexAtLeast_helper(0, "abc",
                "[, a, b, c, ab, ac, ba, bc, ca, cb, abc, acb, bac, bca, cab, cba]");
        distinctStringsShortlexAtLeast_helper(1, "abc",
                "[a, b, c, ab, ac, ba, bc, ca, cb, abc, acb, bac, bca, cab, cba]");
        distinctStringsShortlexAtLeast_helper(2, "abc", "[ab, ac, ba, bc, ca, cb, abc, acb, bac, bca, cab, cba]");
        distinctStringsShortlexAtLeast_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStringsShortlexAtLeast_helper(0, "abbc",
                "[, a, b, b, c, ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb, abb, abc, abb, abc, acb, acb, bab," +
                " bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab, cab, cba, cbb, cba, cbb, abbc, abcb," +
                " abbc, abcb, acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba, babc, bacb, bbac, bbca, bcab, bcba," +
                " cabb, cabb, cbab, cbba, cbab, cbba]");
        distinctStringsShortlexAtLeast_helper(1, "abbc",
                "[a, b, b, c, ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb, abb, abc, abb, abc, acb, acb, bab," +
                " bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab, cab, cba, cbb, cba, cbb, abbc, abcb," +
                " abbc, abcb, acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba, babc, bacb, bbac, bbca, bcab, bcba," +
                " cabb, cabb, cbab, cbba, cbab, cbba]");
        distinctStringsShortlexAtLeast_helper(2, "abbc",
                "[ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb, abb, abc, abb, abc, acb, acb, bab, bac, bba, bbc," +
                " bca, bcb, bab, bac, bba, bbc, bca, bcb, cab, cab, cba, cbb, cba, cbb, abbc, abcb, abbc, abcb," +
                " acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba, babc, bacb, bbac, bbca, bcab, bcba, cabb, cabb," +
                " cbab, cbba, cbab, cbba]");
        distinctStringsShortlexAtLeast_helper(3, "abbc",
                "[abb, abc, abb, abc, acb, acb, bab, bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab," +
                " cab, cba, cbb, cba, cbb, abbc, abcb, abbc, abcb, acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba," +
                " babc, bacb, bbac, bbca, bcab, bcba, cabb, cabb, cbab, cbba, cbab, cbba]");
        distinctStringsShortlexAtLeast_helper_limit(0, "Mississippi",
                "[, M, i, s, s, i, s, s, i, p, p, i, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, ...]");
        distinctStringsShortlexAtLeast_helper_limit(1, "Mississippi",
                "[M, i, s, s, i, s, s, i, p, p, i, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, ...]");
        distinctStringsShortlexAtLeast_helper_limit(2, "Mississippi",
                "[Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, is, is, ii, is, is, ii, ip, ip, ii, ...]");
        distinctStringsShortlexAtLeast_helper_limit(3, "Mississippi",
                "[Mis, Mis, Mii, Mis, Mis, Mii, Mip, Mip, Mii, Msi, Mss, Msi, Mss, Mss, Msi, Msp, Msp, Msi, Msi," +
                " Mss, ...]");
        try {
            EP.distinctStringsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctStringsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctLists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctLists(size, readIntegerListWithNulls(input)), output);
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
    public void testDistinctLists_int_List() {
        distinctLists_int_Iterable_helper(0, "[]", "[[]]");
        distinctLists_int_Iterable_helper(1, "[]", "[]");
        distinctLists_int_Iterable_helper(2, "[]", "[]");
        distinctLists_int_Iterable_helper(3, "[]", "[]");
        distinctLists_int_Iterable_helper(0, "[5]", "[[]]");
        distinctLists_int_Iterable_helper(1, "[5]", "[[5]]");
        distinctLists_int_Iterable_helper(2, "[5]", "[]");
        distinctLists_int_Iterable_helper(3, "[5]", "[]");
        distinctLists_int_Iterable_helper(0, "[1, 2, 3]", "[[]]");
        distinctLists_int_Iterable_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        distinctLists_int_Iterable_helper(2, "[1, 2, 3]", "[[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2]]");
        distinctLists_int_Iterable_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctLists_int_Iterable_helper(0, "[1, 2, 2, 3]", "[[]]");
        distinctLists_int_Iterable_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        distinctLists_int_Iterable_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2], [2, 1], [2, 2], [1, 3], [2, 3], [2, 1], [2, 2], [3, 1], [3, 2], [2, 3], [3, 2]]");
        distinctLists_int_Iterable_helper(3, "[1, 2, 2, 3]",
                "[[1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [1, 3, 2]," +
                " [1, 3, 2], [2, 3, 1], [2, 3, 2], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 3], [3, 1, 2], [3, 1, 2]," +
                " [3, 2, 1], [3, 2, 2], [2, 3, 1], [2, 3, 2], [3, 2, 1], [3, 2, 2]]");
        distinctLists_int_Iterable_helper(0, "[1, null, 3]", "[[]]");
        distinctLists_int_Iterable_helper(1, "[1, null, 3]", "[[1], [null], [3]]");
        distinctLists_int_Iterable_helper(2, "[1, null, 3]",
                "[[1, null], [1, 3], [null, 1], [null, 3], [3, 1], [3, null]]");
        distinctLists_int_Iterable_helper(3, "[1, null, 3]",
                "[[1, null, 3], [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctLists_int_Iterable_helper(0, EP.positiveIntegers(), "[[]]");
        distinctLists_int_Iterable_helper(1, EP.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        distinctLists_int_Iterable_helper(2, EP.positiveIntegers(),
                "[[1, 2], [1, 3], [2, 1], [2, 3], [1, 4], [1, 5], [2, 4], [2, 5], [3, 1], [3, 2], [4, 1], [4, 2]," +
                " [3, 4], [3, 5], [4, 3], [4, 5], [1, 6], [1, 7], [2, 6], [2, 7], ...]");
        distinctLists_int_Iterable_helper(3, EP.positiveIntegers(),
                "[[1, 2, 3], [1, 2, 4], [1, 3, 2], [1, 3, 4], [2, 1, 3], [2, 1, 4], [2, 3, 1], [2, 3, 4], [1, 2, 5]," +
                " [1, 2, 6], [1, 3, 5], [1, 3, 6], [2, 1, 5], [2, 1, 6], [2, 3, 5], [2, 3, 6], [1, 4, 2], [1, 4, 3]," +
                " [1, 5, 2], [1, 5, 3], ...]");
        distinctLists_int_Iterable_helper(0, repeat(1), "[[]]");
        distinctLists_int_Iterable_helper(1, repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]");
        distinctLists_int_Iterable_helper(2, repeat(1),
                "[[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1]," +
                " [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], ...]");
        distinctLists_int_Iterable_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], ...]");
        distinctLists_int_Iterable_fail_helper(-1, "[]");
        distinctLists_int_Iterable_fail_helper(-1, "[1, 2, 3]");
    }

    private static void distinctPairs_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctPairs(readIntegerListWithNulls(input)), output);
    }

    private static void distinctPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctPairs(input), output);
    }

    @Test
    public void testDistinctPairs() {
        distinctPairs_helper("[]", "[]");
        distinctPairs_helper("[5]", "[]");
        distinctPairs_helper("[1, 2, 3, 4]",
                "[(1, 2), (1, 3), (2, 1), (2, 3), (1, 4), (2, 4), (3, 1), (3, 2), (4, 1), (4, 2), (3, 4), (4, 3)]");
        distinctPairs_helper("[1, 2, 2, 4]",
                "[(1, 2), (1, 2), (2, 1), (2, 2), (1, 4), (2, 4), (2, 1), (2, 2), (4, 1), (4, 2), (2, 4), (4, 2)]");
        distinctPairs_helper("[1, 2, null, 4]",
                "[(1, 2), (1, null), (2, 1), (2, null), (1, 4), (2, 4), (null, 1), (null, 2), (4, 1), (4, 2)," +
                " (null, 4), (4, null)]");
        distinctPairs_helper(EP.naturalIntegers(),
                "[(0, 1), (0, 2), (1, 0), (1, 2), (0, 3), (0, 4), (1, 3), (1, 4), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 3), (2, 4), (3, 2), (3, 4), (0, 5), (0, 6), (1, 5), (1, 6), ...]");
        distinctPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
    }

    private static void distinctTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctTriples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctTriples(input), output);
    }

    @Test
    public void testDistinctTriples() {
        distinctTriples_helper("[]", "[]");
        distinctTriples_helper("[5]", "[]");
        distinctTriples_helper("[1, 2, 3, 4]",
                "[(1, 2, 3), (1, 2, 4), (1, 3, 2), (1, 3, 4), (2, 1, 3), (2, 1, 4), (2, 3, 1), (2, 3, 4), (1, 4, 2)," +
                " (1, 4, 3), (2, 4, 1), (2, 4, 3), (3, 1, 2), (3, 1, 4), (3, 2, 1), (3, 2, 4), (4, 1, 2), (4, 1, 3)," +
                " (4, 2, 1), (4, 2, 3), ...]");
        distinctTriples_helper("[1, 2, 2, 4]",
                "[(1, 2, 2), (1, 2, 4), (1, 2, 2), (1, 2, 4), (2, 1, 2), (2, 1, 4), (2, 2, 1), (2, 2, 4), (1, 4, 2)," +
                " (1, 4, 2), (2, 4, 1), (2, 4, 2), (2, 1, 2), (2, 1, 4), (2, 2, 1), (2, 2, 4), (4, 1, 2), (4, 1, 2)," +
                " (4, 2, 1), (4, 2, 2), ...]");
        distinctTriples_helper("[1, 2, null, 4]",
                "[(1, 2, null), (1, 2, 4), (1, null, 2), (1, null, 4), (2, 1, null), (2, 1, 4), (2, null, 1)," +
                " (2, null, 4), (1, 4, 2), (1, 4, null), (2, 4, 1), (2, 4, null), (null, 1, 2), (null, 1, 4)," +
                " (null, 2, 1), (null, 2, 4), (4, 1, 2), (4, 1, null), (4, 2, 1), (4, 2, null), ...]");
        distinctTriples_helper(EP.naturalIntegers(),
                "[(0, 1, 2), (0, 1, 3), (0, 2, 1), (0, 2, 3), (1, 0, 2), (1, 0, 3), (1, 2, 0), (1, 2, 3), (0, 1, 4)," +
                " (0, 1, 5), (0, 2, 4), (0, 2, 5), (1, 0, 4), (1, 0, 5), (1, 2, 4), (1, 2, 5), (0, 3, 1), (0, 3, 2)," +
                " (0, 4, 1), (0, 4, 2), ...]");
        distinctTriples_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
    }

    private static void distinctQuadruples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuadruples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuadruples(input), output);
    }

    @Test
    public void testDistinctQuadruples() {
        distinctQuadruples_helper("[]", "[]");
        distinctQuadruples_helper("[5]", "[]");
        distinctQuadruples_helper("[1, 2, 3, 4]",
                "[(1, 2, 3, 4), (1, 2, 4, 3), (1, 3, 2, 4), (1, 3, 4, 2), (2, 1, 3, 4), (2, 1, 4, 3), (2, 3, 1, 4)," +
                " (2, 3, 4, 1), (1, 4, 2, 3), (1, 4, 3, 2), (2, 4, 1, 3), (2, 4, 3, 1), (3, 1, 2, 4), (3, 1, 4, 2)," +
                " (3, 2, 1, 4), (3, 2, 4, 1), (4, 1, 2, 3), (4, 1, 3, 2), (4, 2, 1, 3), (4, 2, 3, 1), ...]");
        distinctQuadruples_helper("[1, 2, 2, 4]",
                "[(1, 2, 2, 4), (1, 2, 4, 2), (1, 2, 2, 4), (1, 2, 4, 2), (2, 1, 2, 4), (2, 1, 4, 2), (2, 2, 1, 4)," +
                " (2, 2, 4, 1), (1, 4, 2, 2), (1, 4, 2, 2), (2, 4, 1, 2), (2, 4, 2, 1), (2, 1, 2, 4), (2, 1, 4, 2)," +
                " (2, 2, 1, 4), (2, 2, 4, 1), (4, 1, 2, 2), (4, 1, 2, 2), (4, 2, 1, 2), (4, 2, 2, 1), ...]");
        distinctQuadruples_helper("[1, 2, null, 4]",
                "[(1, 2, null, 4), (1, 2, 4, null), (1, null, 2, 4), (1, null, 4, 2), (2, 1, null, 4)," +
                " (2, 1, 4, null), (2, null, 1, 4), (2, null, 4, 1), (1, 4, 2, null), (1, 4, null, 2)," +
                " (2, 4, 1, null), (2, 4, null, 1), (null, 1, 2, 4), (null, 1, 4, 2), (null, 2, 1, 4)," +
                " (null, 2, 4, 1), (4, 1, 2, null), (4, 1, null, 2), (4, 2, 1, null), (4, 2, null, 1), ...]");
        distinctQuadruples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3), (0, 1, 2, 4), (0, 1, 3, 2), (0, 1, 3, 4), (0, 2, 1, 3), (0, 2, 1, 4), (0, 2, 3, 1)," +
                " (0, 2, 3, 4), (1, 0, 2, 3), (1, 0, 2, 4), (1, 0, 3, 2), (1, 0, 3, 4), (1, 2, 0, 3), (1, 2, 0, 4)," +
                " (1, 2, 3, 0), (1, 2, 3, 4), (0, 1, 2, 5), (0, 1, 2, 6), (0, 1, 3, 5), (0, 1, 3, 6), ...]");
        distinctQuadruples_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
    }

    private static void distinctQuintuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuintuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctQuintuples(input), output);
    }

    @Test
    public void testDistinctQuintuples() {
        distinctQuintuples_helper("[]", "[]");
        distinctQuintuples_helper("[5]", "[]");
        distinctQuintuples_helper("[1, 2, 3, 4]", "[]");
        distinctQuintuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5), (1, 2, 3, 4, 6), (1, 2, 3, 5, 4), (1, 2, 3, 5, 6), (1, 2, 4, 3, 5)," +
                " (1, 2, 4, 3, 6), (1, 2, 4, 5, 3), (1, 2, 4, 5, 6), (1, 3, 2, 4, 5), (1, 3, 2, 4, 6)," +
                " (1, 3, 2, 5, 4), (1, 3, 2, 5, 6), (1, 3, 4, 2, 5), (1, 3, 4, 2, 6), (1, 3, 4, 5, 2)," +
                " (1, 3, 4, 5, 6), (2, 1, 3, 4, 5), (2, 1, 3, 4, 6), (2, 1, 3, 5, 4), (2, 1, 3, 5, 6), ...]");
        distinctQuintuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5), (1, 2, 2, 4, 6), (1, 2, 2, 5, 4), (1, 2, 2, 5, 6), (1, 2, 4, 2, 5)," +
                " (1, 2, 4, 2, 6), (1, 2, 4, 5, 2), (1, 2, 4, 5, 6), (1, 2, 2, 4, 5), (1, 2, 2, 4, 6)," +
                " (1, 2, 2, 5, 4), (1, 2, 2, 5, 6), (1, 2, 4, 2, 5), (1, 2, 4, 2, 6), (1, 2, 4, 5, 2)," +
                " (1, 2, 4, 5, 6), (2, 1, 2, 4, 5), (2, 1, 2, 4, 6), (2, 1, 2, 5, 4), (2, 1, 2, 5, 6), ...]");
        distinctQuintuples_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5), (1, 2, null, 4, 6), (1, 2, null, 5, 4), (1, 2, null, 5, 6)," +
                " (1, 2, 4, null, 5), (1, 2, 4, null, 6), (1, 2, 4, 5, null), (1, 2, 4, 5, 6), (1, null, 2, 4, 5)," +
                " (1, null, 2, 4, 6), (1, null, 2, 5, 4), (1, null, 2, 5, 6), (1, null, 4, 2, 5)," +
                " (1, null, 4, 2, 6), (1, null, 4, 5, 2), (1, null, 4, 5, 6), (2, 1, null, 4, 5)," +
                " (2, 1, null, 4, 6), (2, 1, null, 5, 4), (2, 1, null, 5, 6), ...]");
        distinctQuintuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4), (0, 1, 2, 3, 5), (0, 1, 2, 4, 3), (0, 1, 2, 4, 5), (0, 1, 3, 2, 4)," +
                " (0, 1, 3, 2, 5), (0, 1, 3, 4, 2), (0, 1, 3, 4, 5), (0, 2, 1, 3, 4), (0, 2, 1, 3, 5)," +
                " (0, 2, 1, 4, 3), (0, 2, 1, 4, 5), (0, 2, 3, 1, 4), (0, 2, 3, 1, 5), (0, 2, 3, 4, 1)," +
                " (0, 2, 3, 4, 5), (1, 0, 2, 3, 4), (1, 0, 2, 3, 5), (1, 0, 2, 4, 3), (1, 0, 2, 4, 5), ...]");
        distinctQuintuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), ...]");
    }

    private static void distinctSextuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSextuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSextuples(input), output);
    }

    @Test
    public void testDistinctSextuples() {
        distinctSextuples_helper("[]", "[]");
        distinctSextuples_helper("[5]", "[]");
        distinctSextuples_helper("[1, 2, 3, 4]", "[]");
        distinctSextuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6), (1, 2, 3, 4, 5, 7), (1, 2, 3, 4, 6, 5), (1, 2, 3, 4, 6, 7)," +
                " (1, 2, 3, 5, 4, 6), (1, 2, 3, 5, 4, 7), (1, 2, 3, 5, 6, 4), (1, 2, 3, 5, 6, 7)," +
                " (1, 2, 4, 3, 5, 6), (1, 2, 4, 3, 5, 7), (1, 2, 4, 3, 6, 5), (1, 2, 4, 3, 6, 7)," +
                " (1, 2, 4, 5, 3, 6), (1, 2, 4, 5, 3, 7), (1, 2, 4, 5, 6, 3), (1, 2, 4, 5, 6, 7)," +
                " (1, 3, 2, 4, 5, 6), (1, 3, 2, 4, 5, 7), (1, 3, 2, 4, 6, 5), (1, 3, 2, 4, 6, 7), ...]");
        distinctSextuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6), (1, 2, 2, 4, 5, 7), (1, 2, 2, 4, 6, 5), (1, 2, 2, 4, 6, 7)," +
                " (1, 2, 2, 5, 4, 6), (1, 2, 2, 5, 4, 7), (1, 2, 2, 5, 6, 4), (1, 2, 2, 5, 6, 7)," +
                " (1, 2, 4, 2, 5, 6), (1, 2, 4, 2, 5, 7), (1, 2, 4, 2, 6, 5), (1, 2, 4, 2, 6, 7)," +
                " (1, 2, 4, 5, 2, 6), (1, 2, 4, 5, 2, 7), (1, 2, 4, 5, 6, 2), (1, 2, 4, 5, 6, 7)," +
                " (1, 2, 2, 4, 5, 6), (1, 2, 2, 4, 5, 7), (1, 2, 2, 4, 6, 5), (1, 2, 2, 4, 6, 7), ...]");
        distinctSextuples_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5, 6), (1, 2, null, 4, 5, 7), (1, 2, null, 4, 6, 5), (1, 2, null, 4, 6, 7)," +
                " (1, 2, null, 5, 4, 6), (1, 2, null, 5, 4, 7), (1, 2, null, 5, 6, 4), (1, 2, null, 5, 6, 7)," +
                " (1, 2, 4, null, 5, 6), (1, 2, 4, null, 5, 7), (1, 2, 4, null, 6, 5), (1, 2, 4, null, 6, 7)," +
                " (1, 2, 4, 5, null, 6), (1, 2, 4, 5, null, 7), (1, 2, 4, 5, 6, null), (1, 2, 4, 5, 6, 7)," +
                " (1, null, 2, 4, 5, 6), (1, null, 2, 4, 5, 7), (1, null, 2, 4, 6, 5), (1, null, 2, 4, 6, 7), ...]");
        distinctSextuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4, 5), (0, 1, 2, 3, 4, 6), (0, 1, 2, 3, 5, 4), (0, 1, 2, 3, 5, 6)," +
                " (0, 1, 2, 4, 3, 5), (0, 1, 2, 4, 3, 6), (0, 1, 2, 4, 5, 3), (0, 1, 2, 4, 5, 6)," +
                " (0, 1, 3, 2, 4, 5), (0, 1, 3, 2, 4, 6), (0, 1, 3, 2, 5, 4), (0, 1, 3, 2, 5, 6)," +
                " (0, 1, 3, 4, 2, 5), (0, 1, 3, 4, 2, 6), (0, 1, 3, 4, 5, 2), (0, 1, 3, 4, 5, 6)," +
                " (0, 2, 1, 3, 4, 5), (0, 2, 1, 3, 4, 6), (0, 2, 1, 3, 5, 4), (0, 2, 1, 3, 5, 6), ...]");
        distinctSextuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), ...]");
    }

    private static void distinctSeptuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSeptuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.distinctSeptuples(input), output);
    }

    @Test
    public void testDistinctSeptuples() {
        distinctSeptuples_helper("[]", "[]");
        distinctSeptuples_helper("[5]", "[]");
        distinctSeptuples_helper("[1, 2, 3, 4]", "[]");
        distinctSeptuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6, 7), (1, 2, 3, 4, 5, 6, 8), (1, 2, 3, 4, 5, 7, 6), (1, 2, 3, 4, 5, 7, 8)," +
                " (1, 2, 3, 4, 6, 5, 7), (1, 2, 3, 4, 6, 5, 8), (1, 2, 3, 4, 6, 7, 5), (1, 2, 3, 4, 6, 7, 8)," +
                " (1, 2, 3, 5, 4, 6, 7), (1, 2, 3, 5, 4, 6, 8), (1, 2, 3, 5, 4, 7, 6), (1, 2, 3, 5, 4, 7, 8)," +
                " (1, 2, 3, 5, 6, 4, 7), (1, 2, 3, 5, 6, 4, 8), (1, 2, 3, 5, 6, 7, 4), (1, 2, 3, 5, 6, 7, 8)," +
                " (1, 2, 4, 3, 5, 6, 7), (1, 2, 4, 3, 5, 6, 8), (1, 2, 4, 3, 5, 7, 6), (1, 2, 4, 3, 5, 7, 8), ...]");
        distinctSeptuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6, 7), (1, 2, 2, 4, 5, 6, 8), (1, 2, 2, 4, 5, 7, 6), (1, 2, 2, 4, 5, 7, 8)," +
                " (1, 2, 2, 4, 6, 5, 7), (1, 2, 2, 4, 6, 5, 8), (1, 2, 2, 4, 6, 7, 5), (1, 2, 2, 4, 6, 7, 8)," +
                " (1, 2, 2, 5, 4, 6, 7), (1, 2, 2, 5, 4, 6, 8), (1, 2, 2, 5, 4, 7, 6), (1, 2, 2, 5, 4, 7, 8)," +
                " (1, 2, 2, 5, 6, 4, 7), (1, 2, 2, 5, 6, 4, 8), (1, 2, 2, 5, 6, 7, 4), (1, 2, 2, 5, 6, 7, 8)," +
                " (1, 2, 4, 2, 5, 6, 7), (1, 2, 4, 2, 5, 6, 8), (1, 2, 4, 2, 5, 7, 6), (1, 2, 4, 2, 5, 7, 8), ...]");
        distinctSeptuples_helper("[1, 2, null, 4, 5, 6, 7, 8]",
                "[(1, 2, null, 4, 5, 6, 7), (1, 2, null, 4, 5, 6, 8), (1, 2, null, 4, 5, 7, 6)," +
                " (1, 2, null, 4, 5, 7, 8), (1, 2, null, 4, 6, 5, 7), (1, 2, null, 4, 6, 5, 8)," +
                " (1, 2, null, 4, 6, 7, 5), (1, 2, null, 4, 6, 7, 8), (1, 2, null, 5, 4, 6, 7)," +
                " (1, 2, null, 5, 4, 6, 8), (1, 2, null, 5, 4, 7, 6), (1, 2, null, 5, 4, 7, 8)," +
                " (1, 2, null, 5, 6, 4, 7), (1, 2, null, 5, 6, 4, 8), (1, 2, null, 5, 6, 7, 4)," +
                " (1, 2, null, 5, 6, 7, 8), (1, 2, 4, null, 5, 6, 7), (1, 2, 4, null, 5, 6, 8)," +
                " (1, 2, 4, null, 5, 7, 6), (1, 2, 4, null, 5, 7, 8), ...]");
        distinctSeptuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4, 5, 6), (0, 1, 2, 3, 4, 5, 7), (0, 1, 2, 3, 4, 6, 5), (0, 1, 2, 3, 4, 6, 7)," +
                " (0, 1, 2, 3, 5, 4, 6), (0, 1, 2, 3, 5, 4, 7), (0, 1, 2, 3, 5, 6, 4), (0, 1, 2, 3, 5, 6, 7)," +
                " (0, 1, 2, 4, 3, 5, 6), (0, 1, 2, 4, 3, 5, 7), (0, 1, 2, 4, 3, 6, 5), (0, 1, 2, 4, 3, 6, 7)," +
                " (0, 1, 2, 4, 5, 3, 6), (0, 1, 2, 4, 5, 3, 7), (0, 1, 2, 4, 5, 6, 3), (0, 1, 2, 4, 5, 6, 7)," +
                " (0, 1, 3, 2, 4, 5, 6), (0, 1, 3, 2, 4, 5, 7), (0, 1, 3, 2, 4, 6, 5), (0, 1, 3, 2, 4, 6, 7), ...]");
        distinctSeptuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), ...]");
    }

    private static void distinctStrings_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.distinctStrings(size, input), output);
    }

    private static void distinctStrings_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStrings(size, input), output);
    }

    @Test
    public void testDistinctStrings_int_String() {
        distinctStrings_int_String_helper(0, "", "[]");
        aeq(length(EP.distinctStrings(0, "")), 1);
        distinctStrings_int_String_helper(1, "", "[]");
        aeq(length(EP.distinctStrings(1, "")), 0);
        distinctStrings_int_String_helper(2, "", "[]");
        aeq(length(EP.distinctStrings(2, "")), 0);
        distinctStrings_int_String_helper(3, "", "[]");
        aeq(length(EP.distinctStrings(3, "")), 0);
        distinctStrings_int_String_helper(0, "a", "[]");
        distinctStrings_int_String_helper(1, "a", "[a]");
        distinctStrings_int_String_helper(2, "a", "[]");
        aeq(length(EP.distinctStrings(2, "a")), 0);
        distinctStrings_int_String_helper(3, "a", "[]");
        aeq(length(EP.distinctStrings(3, "a")), 0);
        distinctStrings_int_String_helper(0, "abc", "[]");
        aeq(length(EP.distinctStrings(0, "abc")), 1);
        distinctStrings_int_String_helper(1, "abc", "[a, b, c]");
        distinctStrings_int_String_helper(2, "abc", "[ab, ac, ba, bc, ca, cb]");
        distinctStrings_int_String_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStrings_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.distinctStrings(0, "abbc")), 1);
        distinctStrings_int_String_helper(1, "abbc", "[a, b, b, c]");
        distinctStrings_int_String_helper(2, "abbc", "[ab, ab, ba, bb, ac, bc, ba, bb, ca, cb, bc, cb]");
        distinctStrings_int_String_helper(3, "abbc",
                "[abb, abc, abb, abc, bab, bac, bba, bbc, acb, acb, bca, bcb, bab, bac, bba, bbc, cab, cab, cba," +
                " cbb, bca, bcb, cba, cbb]");
        distinctStrings_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.distinctStrings(0, "Mississippi")), 1);
        distinctStrings_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        distinctStrings_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, iM, is, Ms, Mi, is, ii, sM, si, sM, si, ss, si, ss, si, Ms, Ms, is, is, ...]");
        distinctStrings_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Msi, Mss, iMs, iMs, isM, iss, Mii, Mis, Msi, Mss, iMi, iMs, isi, iss, Msi, Mss, Mii," +
                " Mis, ...]");
        try {
            EP.distinctStrings(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctStrings(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStrings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.distinctStrings(size), output);
    }

    @Test
    public void testDistinctStrings_int() {
        distinctStrings_int_helper(0, "[]");
        aeq(length(EP.distinctStrings(0)), 1);
        distinctStrings_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        distinctStrings_int_helper(2,
                "[ab, ac, ba, bc, ad, ae, bd, be, ca, cb, da, db, cd, ce, dc, de, af, ag, bf, bg, ...]");
        distinctStrings_int_helper(3,
                "[abc, abd, acb, acd, bac, bad, bca, bcd, abe, abf, ace, acf, bae, baf, bce, bcf, adb, adc, aeb," +
                " aec, ...]");
        distinctStrings_int_helper(65537, "[]");
        try {
            EP.distinctStrings(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctLists_Iterable() {
        aeqit(EP.distinctLists(Collections.emptyList()), "[[]]");
        simpleProviderHelper(EP.distinctLists(Collections.singletonList(5)), "[[], [5]]");
        simpleProviderHelper(EP.distinctLists(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 1], [2, 3], [1, 3, 2], [3, 1], [2, 1, 3]," +
                " [3, 2], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        simpleProviderHelper(EP.distinctLists(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 1], [1, 2, 3], [2, 2]," +
                " [1, 3], [1, 2, 2], [2, 3], [1, 2, 3], [2, 1], [2, 1, 2], [2, 2], [1, 2, 3, 2], ...]");
        simpleProviderHelper(EP.distinctLists(EP.naturalIntegers()),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        simpleProviderHelper(EP.distinctLists(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
    }

    @Test
    public void testDistinctStrings_String() {
        aeqit(EP.distinctStrings(""), "[]");
        aeq(length(EP.distinctStrings("")), 1);
        simpleProviderHelper(EP.distinctStrings("a"), "[, a]");
        simpleProviderHelper(EP.distinctStrings("abc"),
                "[, a, ab, b, abc, c, ac, ba, bc, acb, ca, bac, cb, bca, cab, cba]");
        simpleProviderHelper(EP.distinctStrings("abbc"),
                "[, a, ab, b, abb, b, ab, c, abbc, ba, abc, bb, ac, abb, bc, abc, ba, bab, bb, abcb, ...]");
        simpleProviderHelper(EP.distinctStrings("Mississippi"),
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, iM, s, Mis, s, is, i, Missi, p, Ms, p, ...]");
    }

    @Test
    public void testDistinctStrings() {
        simpleProviderHelper(EP.distinctStrings(),
                "[, a, ab, b, abc, c, ac, d, abcd, e, ba, f, abd, g, bc, h, abcde, i, ad, j, ...]");
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

    @Test
    public void testDistinctListsAtLeast() {
        distinctListsAtLeast_helper(0, "[]", "[[]]");
        distinctListsAtLeast_helper(1, "[]", "[]");
        distinctListsAtLeast_helper(2, "[]", "[]");
        distinctListsAtLeast_helper(3, "[]", "[]");

        distinctListsAtLeast_helper(0, "[5]", "[[], [5]]");
        distinctListsAtLeast_helper(1, "[5]", "[[5]]");
        distinctListsAtLeast_helper(2, "[5]", "[]");
        distinctListsAtLeast_helper(3, "[5]", "[]");

        distinctListsAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 1], [2, 3], [1, 3, 2], [3, 1], [2, 1, 3]," +
                " [3, 2], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 1], [2, 3], [1, 3, 2], [3, 1], [2, 1, 3], [3, 2]," +
                " [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        distinctListsAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 2], [1, 2, 3], [1, 3], [2, 1], [2, 3], [1, 3, 2], [3, 1], [2, 1, 3], [3, 2], [2, 3, 1]," +
                " [3, 1, 2], [3, 2, 1]]");
        distinctListsAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");

        distinctListsAtLeast_helper(0, "[1, null, 3]",
                "[[], [1], [1, null], [null], [1, null, 3], [3], [1, 3], [null, 1], [null, 3], [1, 3, null], [3, 1]," +
                " [null, 1, 3], [3, null], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsAtLeast_helper(1, "[1, null, 3]",
                "[[1], [1, null], [null], [1, null, 3], [3], [1, 3], [null, 1], [null, 3], [1, 3, null], [3, 1]," +
                " [null, 1, 3], [3, null], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsAtLeast_helper(2, "[1, null, 3]",
                "[[1, null], [1, null, 3], [1, 3], [null, 1], [null, 3], [1, 3, null], [3, 1], [null, 1, 3]," +
                " [3, null], [null, 3, 1], [3, 1, null], [3, null, 1]]");
        distinctListsAtLeast_helper(3, "[1, null, 3]",
                "[[1, null, 3], [1, 3, null], [null, 1, 3], [null, 3, 1], [3, 1, null], [3, null, 1]]");

        distinctListsAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 1], [1, 2, 3], [2, 2]," +
                " [1, 3], [1, 2, 2], [2, 3], [1, 2, 3], [2, 1], [2, 1, 2], [2, 2], [1, 2, 3, 2], ...]");
        distinctListsAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 1], [1, 2, 3], [2, 2], [1, 3]," +
                " [1, 2, 2], [2, 3], [1, 2, 3], [2, 1], [2, 1, 2], [2, 2], [1, 2, 3, 2], [3, 1], ...]");
        distinctListsAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2, 2], [1, 2], [1, 2, 2, 3], [2, 1], [1, 2, 3], [2, 2], [1, 3], [1, 2, 2], [2, 3]," +
                " [1, 2, 3], [2, 1], [2, 1, 2], [2, 2], [1, 2, 3, 2], [3, 1], [2, 1, 3], [3, 2], [2, 3], [2, 2, 1]," +
                " ...]");
        distinctListsAtLeast_helper(3, "[1, 2, 2, 3]",
                "[[1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 2], [1, 2, 3], [2, 1, 2], [1, 2, 3, 2], [2, 1, 3]," +
                " [2, 2, 1], [2, 2, 3], [1, 2, 2, 3], [1, 2, 3, 2], [1, 3, 2], [2, 1, 2, 3], [1, 3, 2], [2, 3, 1]," +
                " [2, 1, 3, 2], [2, 3, 2], [2, 2, 1, 3], [2, 2, 3, 1], ...]");

        distinctListsAtLeast_helper(0, EP.naturalIntegers(),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        distinctListsAtLeast_helper(1, EP.naturalIntegers(),
                "[[0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], [0, 2, 1], ...]");
        distinctListsAtLeast_helper(2, EP.naturalIntegers(),
                "[[0, 1], [0, 1, 2], [0, 2], [0, 1, 2, 3], [1, 0], [0, 1, 3], [1, 2], [0, 1, 2, 3, 4], [0, 3]," +
                " [0, 2, 1], [0, 4], [0, 1, 2, 4], [1, 3], [0, 2, 3], [1, 4], [0, 1, 2, 3, 4, 5], [2, 0], [1, 0, 2]," +
                " [2, 1], [0, 1, 3, 2], ...]");
        distinctListsAtLeast_helper(3, EP.naturalIntegers(),
                "[[0, 1, 2], [0, 1, 2, 3], [0, 1, 3], [0, 1, 2, 3, 4], [0, 2, 1], [0, 1, 2, 4], [0, 2, 3]," +
                " [0, 1, 2, 3, 4, 5], [1, 0, 2], [0, 1, 3, 2], [1, 0, 3], [0, 1, 2, 3, 5], [1, 2, 0], [0, 1, 3, 4]," +
                " [1, 2, 3], [0, 1, 2, 3, 4, 5, 6], [0, 1, 4], [0, 2, 1, 3], [0, 1, 5], [0, 1, 2, 4, 3], ...]");

        distinctListsAtLeast_helper(0, repeat(1),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        distinctListsAtLeast_helper(1, repeat(1),
                "[[1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], ...]");
        distinctListsAtLeast_helper(2, repeat(1),
                "[[1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1], [1, 1], [1, 1, 1]," +
                " [1, 1], [1, 1, 1, 1], ...]");
        distinctListsAtLeast_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], ...]");

        try {
            EP.distinctListsAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctListsAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsAtLeast_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsAtLeast_String() {
        distinctStringsAtLeast_String_helper(0, "", "[]");
        aeq(length(EP.distinctStringsAtLeast(0, "")), 1);
        distinctStringsAtLeast_String_helper(1, "", "[]");
        aeq(length(EP.distinctStringsAtLeast(1, "")), 0);
        distinctStringsAtLeast_String_helper(2, "", "[]");
        aeq(length(EP.distinctStringsAtLeast(2, "")), 0);
        distinctStringsAtLeast_String_helper(3, "", "[]");
        aeq(length(EP.distinctStringsAtLeast(3, "")), 0);
        distinctStringsAtLeast_String_helper(0, "a", "[, a]");
        distinctStringsAtLeast_String_helper(1, "a", "[a]");
        distinctStringsAtLeast_String_helper(2, "a", "[]");
        distinctStringsAtLeast_String_helper(3, "a", "[]");
        distinctStringsAtLeast_String_helper(0, "abc",
                "[, a, ab, b, abc, c, ac, ba, bc, acb, ca, bac, cb, bca, cab, cba]");
        distinctStringsAtLeast_String_helper(1, "abc",
                "[a, ab, b, abc, c, ac, ba, bc, acb, ca, bac, cb, bca, cab, cba]");
        distinctStringsAtLeast_String_helper(2, "abc", "[ab, abc, ac, ba, bc, acb, ca, bac, cb, bca, cab, cba]");
        distinctStringsAtLeast_String_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStringsAtLeast_String_helper(0, "abbc",
                "[, a, ab, b, abb, b, ab, c, abbc, ba, abc, bb, ac, abb, bc, abc, ba, bab, bb, abcb, ...]");
        distinctStringsAtLeast_String_helper(1, "abbc",
                "[a, ab, b, abb, b, ab, c, abbc, ba, abc, bb, ac, abb, bc, abc, ba, bab, bb, abcb, ca, ...]");
        distinctStringsAtLeast_String_helper(2, "abbc",
                "[ab, abb, ab, abbc, ba, abc, bb, ac, abb, bc, abc, ba, bab, bb, abcb, ca, bac, cb, bc, bba, ...]");
        distinctStringsAtLeast_String_helper(3, "abbc",
                "[abb, abbc, abc, abb, abc, bab, abcb, bac, bba, bbc, abbc, abcb, acb, babc, acb, bca, bacb, bcb," +
                " bbac, bbca, ...]");
        distinctStringsAtLeast_String_helper(0, "Mississippi",
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, iM, s, Mis, s, is, i, Missi, p, Ms, p, ...]");
        distinctStringsAtLeast_String_helper(1, "Mississippi",
                "[M, Mi, i, Mis, s, Ms, s, Miss, i, iM, s, Mis, s, is, i, Missi, p, Ms, p, Msi, ...]");
        distinctStringsAtLeast_String_helper(2, "Mississippi",
                "[Mi, Mis, Ms, Miss, iM, Mis, is, Missi, Ms, Msi, Mi, Misi, is, Mss, ii, Missis, sM, iMs, si, Miss," +
                " ...]");
        distinctStringsAtLeast_String_helper(3, "Mississippi",
                "[Mis, Miss, Mis, Missi, Msi, Misi, Mss, Missis, iMs, Miss, iMs, Misss, isM, Misi, iss, Mississ," +
                " Mii, Msis, Mis, Misis, ...]");
        try {
            EP.distinctStringsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.distinctStringsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.distinctStringsAtLeast(minSize), output);
    }

    @Test
    public void testDistinctStringsAtLeast() {
        distinctStringsAtLeast_helper(0,
                "[, a, ab, b, abc, c, ac, d, abcd, e, ba, f, abd, g, bc, h, abcde, i, ad, j, ...]");
        distinctStringsAtLeast_helper(1,
                "[a, ab, b, abc, c, ac, d, abcd, e, ba, f, abd, g, bc, h, abcde, i, ad, j, acb, ...]");
        distinctStringsAtLeast_helper(2,
                "[ab, abc, ac, abcd, ba, abd, bc, abcde, ad, acb, ae, abce, bd, acd, be, abcdef, ca, bac, cb, abdc," +
                " ...]");
        distinctStringsAtLeast_helper(3,
                "[abc, abcd, abd, abcde, acb, abce, acd, abcdef, bac, abdc, bad, abcdf, bca, abde, bcd, abcdefg," +
                " abe, acbd, abf, abced, ...]");
        distinctStringsAtLeast_helper(65537, "[]");
        try {
            EP.distinctStringsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void bagsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.bagsLex(size, readIntegerListWithNulls(input)), output);
    }

    private static void bagsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.bagsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (ArithmeticException | NullPointerException ignored) {}
    }

    @Test
    public void testBagsLex_int_List() {
        bagsLex_int_List_helper(0, "[]", "[[]]");
        bagsLex_int_List_helper(1, "[]", "[]");
        bagsLex_int_List_helper(2, "[]", "[]");
        bagsLex_int_List_helper(3, "[]", "[]");
        bagsLex_int_List_helper(0, "[5]", "[[]]");
        bagsLex_int_List_helper(1, "[5]", "[[5]]");
        bagsLex_int_List_helper(2, "[5]", "[[5, 5]]");
        bagsLex_int_List_helper(3, "[5]", "[[5, 5, 5]]");
        bagsLex_int_List_helper(0, "[1, 2, 3]", "[[]]");
        bagsLex_int_List_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        bagsLex_int_List_helper(2, "[1, 2, 3]", "[[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]");
        bagsLex_int_List_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3]," +
                " [3, 3, 3]]");
        bagsLex_int_List_helper(0, "[1, 2, 2, 3]", "[[]]");
        bagsLex_int_List_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        bagsLex_int_List_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 2], [1, 2], [1, 3], [2, 2], [2, 2], [2, 3], [2, 2], [2, 3], [3, 3]]");
        bagsLex_int_List_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 2], [1, 2, 3], [1, 2, 2], [1, 2, 3]," +
                " [1, 3, 3], [2, 2, 2], [2, 2, 2], [2, 2, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [2, 2, 2], [2, 2, 3]," +
                " [2, 3, 3], [3, 3, 3]]");
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
        bagPairsLex_helper("[]", "[]");
        bagPairsLex_helper("[5]", "[(5, 5)]");
        bagPairsLex_helper("[1, 2, 3, 4]",
                "[(1, 1), (1, 2), (1, 3), (1, 4), (2, 2), (2, 3), (2, 4), (3, 3), (3, 4), (4, 4)]");
        bagPairsLex_helper("[1, 2, 2, 4]",
                "[(1, 1), (1, 2), (1, 2), (1, 4), (2, 2), (2, 2), (2, 4), (2, 2), (2, 4), (4, 4)]");
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
        bagTriplesLex_helper("[]", "[]");
        bagTriplesLex_helper("[5]", "[(5, 5, 5)]");
        bagTriplesLex_helper("[1, 2, 3, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 1, 3), (1, 1, 4), (1, 2, 2), (1, 2, 3), (1, 2, 4), (1, 3, 3), (1, 3, 4)," +
                " (1, 4, 4), (2, 2, 2), (2, 2, 3), (2, 2, 4), (2, 3, 3), (2, 3, 4), (2, 4, 4), (3, 3, 3), (3, 3, 4)," +
                " (3, 4, 4), (4, 4, 4)]");
        bagTriplesLex_helper("[1, 2, 2, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 1, 2), (1, 1, 4), (1, 2, 2), (1, 2, 2), (1, 2, 4), (1, 2, 2), (1, 2, 4)," +
                " (1, 4, 4), (2, 2, 2), (2, 2, 2), (2, 2, 4), (2, 2, 2), (2, 2, 4), (2, 4, 4), (2, 2, 2), (2, 2, 4)," +
                " (2, 4, 4), (4, 4, 4)]");
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
        bagQuadruplesLex_helper("[]", "[]");
        bagQuadruplesLex_helper("[5]", "[(5, 5, 5, 5)]");
        bagQuadruplesLex_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 1, 3), (1, 1, 1, 4), (1, 1, 2, 2), (1, 1, 2, 3), (1, 1, 2, 4)," +
                " (1, 1, 3, 3), (1, 1, 3, 4), (1, 1, 4, 4), (1, 2, 2, 2), (1, 2, 2, 3), (1, 2, 2, 4), (1, 2, 3, 3)," +
                " (1, 2, 3, 4), (1, 2, 4, 4), (1, 3, 3, 3), (1, 3, 3, 4), (1, 3, 4, 4), (1, 4, 4, 4), ...]");
        bagQuadruplesLex_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 1, 2), (1, 1, 1, 4), (1, 1, 2, 2), (1, 1, 2, 2), (1, 1, 2, 4)," +
                " (1, 1, 2, 2), (1, 1, 2, 4), (1, 1, 4, 4), (1, 2, 2, 2), (1, 2, 2, 2), (1, 2, 2, 4), (1, 2, 2, 2)," +
                " (1, 2, 2, 4), (1, 2, 4, 4), (1, 2, 2, 2), (1, 2, 2, 4), (1, 2, 4, 4), (1, 4, 4, 4), ...]");
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
        bagQuintuplesLex_helper("[]", "[]");
        bagQuintuplesLex_helper("[5]", "[(5, 5, 5, 5, 5)]");
        bagQuintuplesLex_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 1, 3), (1, 1, 1, 1, 4), (1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 3), (1, 1, 1, 2, 4), (1, 1, 1, 3, 3), (1, 1, 1, 3, 4), (1, 1, 1, 4, 4)," +
                " (1, 1, 2, 2, 2), (1, 1, 2, 2, 3), (1, 1, 2, 2, 4), (1, 1, 2, 3, 3), (1, 1, 2, 3, 4)," +
                " (1, 1, 2, 4, 4), (1, 1, 3, 3, 3), (1, 1, 3, 3, 4), (1, 1, 3, 4, 4), (1, 1, 4, 4, 4), ...]");
        bagQuintuplesLex_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 1, 2), (1, 1, 1, 1, 4), (1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 2), (1, 1, 1, 2, 4), (1, 1, 1, 2, 2), (1, 1, 1, 2, 4), (1, 1, 1, 4, 4)," +
                " (1, 1, 2, 2, 2), (1, 1, 2, 2, 2), (1, 1, 2, 2, 4), (1, 1, 2, 2, 2), (1, 1, 2, 2, 4)," +
                " (1, 1, 2, 4, 4), (1, 1, 2, 2, 2), (1, 1, 2, 2, 4), (1, 1, 2, 4, 4), (1, 1, 4, 4, 4), ...]");
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
        bagSextuplesLex_helper("[]", "[]");
        bagSextuplesLex_helper("[5]", "[(5, 5, 5, 5, 5, 5)]");
        bagSextuplesLex_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 3), (1, 1, 1, 1, 1, 4)," +
                " (1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 2, 3), (1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 3, 3)," +
                " (1, 1, 1, 1, 3, 4), (1, 1, 1, 1, 4, 4), (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 3)," +
                " (1, 1, 1, 2, 2, 4), (1, 1, 1, 2, 3, 3), (1, 1, 1, 2, 3, 4), (1, 1, 1, 2, 4, 4)," +
                " (1, 1, 1, 3, 3, 3), (1, 1, 1, 3, 3, 4), (1, 1, 1, 3, 4, 4), (1, 1, 1, 4, 4, 4), ...]");
        bagSextuplesLex_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 4)," +
                " (1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 4, 4), (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 2)," +
                " (1, 1, 1, 2, 2, 4), (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 4), (1, 1, 1, 2, 4, 4)," +
                " (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 4), (1, 1, 1, 2, 4, 4), (1, 1, 1, 4, 4, 4), ...]");
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
        bagSeptuplesLex_helper("[]", "[]");
        bagSeptuplesLex_helper("[5]", "[(5, 5, 5, 5, 5, 5, 5)]");
        bagSeptuplesLex_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 1, 3), (1, 1, 1, 1, 1, 1, 4)," +
                " (1, 1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 1, 2, 3), (1, 1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 1, 3, 3)," +
                " (1, 1, 1, 1, 1, 3, 4), (1, 1, 1, 1, 1, 4, 4), (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 3)," +
                " (1, 1, 1, 1, 2, 2, 4), (1, 1, 1, 1, 2, 3, 3), (1, 1, 1, 1, 2, 3, 4), (1, 1, 1, 1, 2, 4, 4)," +
                " (1, 1, 1, 1, 3, 3, 3), (1, 1, 1, 1, 3, 3, 4), (1, 1, 1, 1, 3, 4, 4), (1, 1, 1, 1, 4, 4, 4), ...]");
        bagSeptuplesLex_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 1, 4)," +
                " (1, 1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 1, 2, 4), (1, 1, 1, 1, 1, 4, 4), (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 2)," +
                " (1, 1, 1, 1, 2, 2, 4), (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 4), (1, 1, 1, 1, 2, 4, 4)," +
                " (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 4), (1, 1, 1, 1, 2, 4, 4), (1, 1, 1, 1, 4, 4, 4), ...]");
        bagSeptuplesLex_fail_helper("[1, 2, null, 4]");
        bagSeptuplesLex_fail_helper("[null]");
    }

    private static void stringBagsLex_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringBagsLex(size, input), output);
    }

    private static void stringBagsLex_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBagsLex(size, input), output);
    }

    @Test
    public void testStringBagsLex() {
        stringBagsLex_helper(0, "", "[]");
        aeq(length(EP.stringBagsLex(0, "")), 1);
        stringBagsLex_helper(1, "", "[]");
        aeq(length(EP.stringBagsLex(1, "")), 0);
        stringBagsLex_helper(2, "", "[]");
        aeq(length(EP.stringBagsLex(2, "")), 0);
        stringBagsLex_helper(3, "", "[]");
        aeq(length(EP.stringBagsLex(3, "")), 0);
        stringBagsLex_helper(0, "a", "[]");
        aeq(length(EP.stringBagsLex(0, "a")), 1);
        stringBagsLex_helper(1, "a", "[a]");
        stringBagsLex_helper(2, "a", "[aa]");
        stringBagsLex_helper(3, "a", "[aaa]");
        stringBagsLex_helper(0, "abc", "[]");
        aeq(length(EP.stringBagsLex(0, "abc")), 1);
        stringBagsLex_helper(1, "abc", "[a, b, c]");
        stringBagsLex_helper(2, "abc", "[aa, ab, ac, bb, bc, cc]");
        stringBagsLex_helper(3, "abc", "[aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc]");
        stringBagsLex_helper(0, "abbc", "[]");
        aeq(length(EP.stringBagsLex(0, "abbc")), 1);
        stringBagsLex_helper(1, "abbc", "[a, b, b, c]");
        stringBagsLex_helper(2, "abbc", "[aa, ab, ab, ac, bb, bb, bc, bb, bc, cc]");
        stringBagsLex_helper(3, "abbc",
                "[aaa, aab, aab, aac, abb, abb, abc, abb, abc, acc, bbb, bbb, bbc, bbb, bbc, bcc, bbb, bbc, bcc," +
                " ccc]");
        stringBagsLex_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.stringBagsLex(0, "Mississippi")), 1);
        stringBagsLex_helper_limit(1, "Mississippi", "[M, i, i, i, i, p, p, s, s, s, s]");
        stringBagsLex_helper_limit(2, "Mississippi",
                "[MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ii, ip, ip, is, is, is, ...]");
        stringBagsLex_helper_limit(3, "Mississippi",
                "[MMM, MMi, MMi, MMi, MMi, MMp, MMp, MMs, MMs, MMs, MMs, Mii, Mii, Mii, Mii, Mip, Mip, Mis, Mis," +
                " Mis, ...]");
        try {
            EP.stringBagsLex(-1, "");
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            EP.stringBagsLex(-1, "abc");
            fail();
        } catch (ArithmeticException ignored) {}
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
        bagsShortlex_helper("[]", "[[]]");
        bagsShortlex_helper("[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsShortlex_helper("[1, 2, 3]",
                "[[], [1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3], [1, 1, 1], [1, 1, 2]," +
                " [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3], ...]");
        bagsShortlex_helper("[1, 2, 2, 3]",
                "[[], [1], [2], [2], [3], [1, 1], [1, 2], [1, 2], [1, 3], [2, 2], [2, 2], [2, 3], [2, 2], [2, 3]," +
                " [3, 3], [1, 1, 1], [1, 1, 2], [1, 1, 2], [1, 1, 3], [1, 2, 2], ...]");
        bagsShortlex_fail_helper("[1, null, 3]");
        bagsShortlex_fail_helper("[null]");
    }

    private static void stringBagsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsShortlex(input), output);
    }

    @Test
    public void testStringBagsShortlex() {
        stringBagsShortlex_helper("", "[]");
        aeq(length(EP.stringBagsShortlex("")), 1);
        stringBagsShortlex_helper("a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsShortlex_helper("abc",
                "[, a, b, c, aa, ab, ac, bb, bc, cc, aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc, ...]");
        stringBagsShortlex_helper("abbc",
                "[, a, b, b, c, aa, ab, ab, ac, bb, bb, bc, bb, bc, cc, aaa, aab, aab, aac, abb, ...]");
        stringBagsShortlex_helper("Mississippi",
                "[, M, i, i, i, i, p, p, s, s, s, s, MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, ...]");
    }

    private static void bagsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagsShortlexAtLeast(minSize, readIntegerList(input)), output);
    }

    @Test
    public void testBagsShortlexAtLeast() {
        bagsShortlexAtLeast_helper(0, "[]", "[[]]");
        bagsShortlexAtLeast_helper(1, "[]", "[]");
        bagsShortlexAtLeast_helper(2, "[]", "[]");
        bagsShortlexAtLeast_helper(3, "[]", "[]");

        bagsShortlexAtLeast_helper(0, "[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsShortlexAtLeast_helper(1, "[5]",
                "[[5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsShortlexAtLeast_helper(2, "[5]",
                "[[5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsShortlexAtLeast_helper(3, "[5]",
                "[[5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");

        bagsShortlexAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3], [1, 1, 1], [1, 1, 2]," +
                " [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3], ...]");
        bagsShortlexAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3], [1, 1, 1], [1, 1, 2], [1, 1, 3]," +
                " [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3], [1, 1, 1, 1], ...]");
        bagsShortlexAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3], [1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2]," +
                " [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3], [1, 1, 1, 1], [1, 1, 1, 2]," +
                " [1, 1, 1, 3], [1, 1, 2, 2], ...]");
        bagsShortlexAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3]," +
                " [3, 3, 3], [1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 1, 3], [1, 1, 2, 2], [1, 1, 2, 3], [1, 1, 3, 3]," +
                " [1, 2, 2, 2], [1, 2, 2, 3], [1, 2, 3, 3], [1, 3, 3, 3], ...]");

        try {
            EP.bagsShortlexAtLeast(-1, Collections.<Integer>emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.bagsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toList(EP.bagsShortlexAtLeast(1, Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(EP.bagsShortlexAtLeast(1, Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    private static void stringBagsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringBagsShortlexAtLeast() {
        stringBagsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(EP.stringBagsShortlexAtLeast(0, "")), 1);
        stringBagsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(EP.stringBagsShortlexAtLeast(1, "")), 0);
        stringBagsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(EP.stringBagsShortlexAtLeast(2, "")), 0);
        stringBagsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(EP.stringBagsShortlexAtLeast(3, "")), 0);
        stringBagsShortlexAtLeast_helper(0, "a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsShortlexAtLeast_helper(1, "a",
                "[a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsShortlexAtLeast_helper(2, "a",
                "[aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsShortlexAtLeast_helper(3, "a",
                "[aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsShortlexAtLeast_helper(0, "abc",
                "[, a, b, c, aa, ab, ac, bb, bc, cc, aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc, ...]");
        stringBagsShortlexAtLeast_helper(1, "abc",
                "[a, b, c, aa, ab, ac, bb, bc, cc, aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc, aaaa, ...]");
        stringBagsShortlexAtLeast_helper(2, "abc",
                "[aa, ab, ac, bb, bc, cc, aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc, aaaa, aaab, aaac, aabb," +
                " ...]");
        stringBagsShortlexAtLeast_helper(3, "abc",
                "[aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc, aaaa, aaab, aaac, aabb, aabc, aacc, abbb, abbc," +
                " abcc, accc, ...]");
        stringBagsShortlexAtLeast_helper(0, "abbc",
                "[, a, b, b, c, aa, ab, ab, ac, bb, bb, bc, bb, bc, cc, aaa, aab, aab, aac, abb, ...]");
        stringBagsShortlexAtLeast_helper(1, "abbc",
                "[a, b, b, c, aa, ab, ab, ac, bb, bb, bc, bb, bc, cc, aaa, aab, aab, aac, abb, abb, ...]");
        stringBagsShortlexAtLeast_helper(2, "abbc",
                "[aa, ab, ab, ac, bb, bb, bc, bb, bc, cc, aaa, aab, aab, aac, abb, abb, abc, abb, abc, acc, ...]");
        stringBagsShortlexAtLeast_helper(3, "abbc",
                "[aaa, aab, aab, aac, abb, abb, abc, abb, abc, acc, bbb, bbb, bbc, bbb, bbc, bcc, bbb, bbc, bcc," +
                " ccc, ...]");
        stringBagsShortlexAtLeast_helper(0, "Mississippi",
                "[, M, i, i, i, i, p, p, s, s, s, s, MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, ...]");
        stringBagsShortlexAtLeast_helper(1, "Mississippi",
                "[M, i, i, i, i, p, p, s, s, s, s, MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, ...]");
        stringBagsShortlexAtLeast_helper(2, "Mississippi",
                "[MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ii, ip, ip, is, is, is, ...]");
        stringBagsShortlexAtLeast_helper(3, "Mississippi",
                "[MMM, MMi, MMi, MMi, MMi, MMp, MMp, MMs, MMs, MMs, MMs, Mii, Mii, Mii, Mii, Mip, Mip, Mis, Mis," +
                " Mis, ...]");
        try {
            EP.stringBagsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringBagsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void bags_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.bags(size, readIntegerList(input)), output);
    }

    private static void bags_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bags(size, input), output);
    }

    private static void bags_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            toList(EP.bags(size, readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testBags_int_List() {
        bags_int_Iterable_helper(0, "[]", "[[]]");
        bags_int_Iterable_helper(1, "[]", "[]");
        bags_int_Iterable_helper(2, "[]", "[]");
        bags_int_Iterable_helper(3, "[]", "[]");
        bags_int_Iterable_helper(0, "[5]", "[[]]");
        bags_int_Iterable_helper(1, "[5]", "[[5]]");
        bags_int_Iterable_helper(2, "[5]", "[[5, 5]]");
        bags_int_Iterable_helper(3, "[5]", "[[5, 5, 5]]");
        bags_int_Iterable_helper(0, "[1, 2, 3]", "[[]]");
        bags_int_Iterable_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        bags_int_Iterable_helper(2, "[1, 2, 3]", "[[1, 1], [1, 2], [2, 2], [2, 3], [1, 3], [3, 3]]");
        bags_int_Iterable_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 2, 2], [1, 2, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [1, 1, 3], [1, 3, 3]," +
                " [3, 3, 3]]");
        bags_int_Iterable_helper(0, "[1, 2, 2, 3]", "[[]]");
        bags_int_Iterable_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        bags_int_Iterable_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 2], [2, 2], [2, 2], [1, 2], [1, 3], [2, 3], [2, 2], [2, 3], [3, 3]]");
        bags_int_Iterable_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 2], [1, 2, 2], [1, 2, 2], [2, 2, 2], [2, 2, 2], [2, 2, 2], [2, 2, 3], [1, 1, 2]," +
                " [1, 1, 3], [1, 2, 3], [2, 2, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 3, 3], [2, 2, 2], [2, 2, 3]," +
                " [2, 3, 3], [3, 3, 3]]");
        bags_int_Iterable_helper(0, EP.positiveIntegers(), "[[]]");
        bags_int_Iterable_helper(1, EP.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        bags_int_Iterable_helper(2, EP.positiveIntegers(),
                "[[1, 1], [1, 2], [2, 2], [2, 3], [1, 3], [1, 4], [2, 4], [2, 5], [3, 3], [3, 4], [4, 4], [4, 5]," +
                " [3, 5], [3, 6], [4, 6], [4, 7], [1, 5], [1, 6], [2, 6], [2, 7], ...]");
        bags_int_Iterable_helper(3, EP.positiveIntegers(),
                "[[1, 1, 1], [1, 1, 2], [1, 2, 2], [1, 2, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [2, 3, 4], [1, 1, 3]," +
                " [1, 1, 4], [1, 2, 4], [1, 2, 5], [2, 2, 4], [2, 2, 5], [2, 3, 5], [2, 3, 6], [1, 3, 3], [1, 3, 4]," +
                " [1, 4, 4], [1, 4, 5], ...]");
        bags_int_Iterable_helper(0, repeat(1), "[[]]");
        bags_int_Iterable_helper(1, repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]");
        bags_int_Iterable_helper(2, repeat(1),
                "[[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1]," +
                " [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], ...]");
        bags_int_Iterable_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], ...]");
        bags_int_Iterable_fail_helper(-1, "[]");
        bags_int_Iterable_fail_helper(-1, "[1, 2, 3]");
        bags_int_Iterable_fail_helper(1, "[1, null, 3]");
        bags_int_Iterable_fail_helper(1, "[null]");
    }

    private static void bagPairs_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagPairs(readIntegerList(input)), output);
    }

    private static void bagPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagPairs(input), output);
    }

    private static void bagPairs_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagPairs(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagPairs() {
        bagPairs_helper("[]", "[]");
        bagPairs_helper("[5]", "[(5, 5)]");
        bagPairs_helper("[1, 2, 3, 4]",
                "[(1, 1), (1, 2), (2, 2), (2, 3), (1, 3), (1, 4), (2, 4), (3, 3), (3, 4), (4, 4)]");
        bagPairs_helper("[1, 2, 2, 4]",
                "[(1, 1), (1, 2), (2, 2), (2, 2), (1, 2), (1, 4), (2, 4), (2, 2), (2, 4), (4, 4)]");
        bagPairs_helper(EP.naturalIntegers(),
                "[(0, 0), (0, 1), (1, 1), (1, 2), (0, 2), (0, 3), (1, 3), (1, 4), (2, 2), (2, 3), (3, 3), (3, 4)," +
                " (2, 4), (2, 5), (3, 5), (3, 6), (0, 4), (0, 5), (1, 5), (1, 6), ...]");
        bagPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
        bagPairs_fail_helper("[1, null, 3]");
    }

    private static void bagTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagTriples(readIntegerList(input)), output);
    }

    private static void bagTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagTriples(input), output);
    }

    private static void bagTriples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagTriples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagTriples() {
        bagTriples_helper("[]", "[]");
        bagTriples_helper("[5]", "[(5, 5, 5)]");
        bagTriples_helper("[1, 2, 3, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 2, 2), (1, 2, 3), (2, 2, 2), (2, 2, 3), (2, 3, 3), (2, 3, 4), (1, 1, 3)," +
                " (1, 1, 4), (1, 2, 4), (2, 2, 4), (1, 3, 3), (1, 3, 4), (1, 4, 4), (2, 4, 4), (3, 3, 3), (3, 3, 4)," +
                " (3, 4, 4), (4, 4, 4)]");
        bagTriples_helper("[1, 2, 2, 4]",
                "[(1, 1, 1), (1, 1, 2), (1, 2, 2), (1, 2, 2), (2, 2, 2), (2, 2, 2), (2, 2, 2), (2, 2, 4), (1, 1, 2)," +
                " (1, 1, 4), (1, 2, 4), (2, 2, 4), (1, 2, 2), (1, 2, 4), (1, 4, 4), (2, 4, 4), (2, 2, 2), (2, 2, 4)," +
                " (2, 4, 4), (4, 4, 4)]");
        bagTriples_helper(EP.naturalIntegers(),
                "[(0, 0, 0), (0, 0, 1), (0, 1, 1), (0, 1, 2), (1, 1, 1), (1, 1, 2), (1, 2, 2), (1, 2, 3), (0, 0, 2)," +
                " (0, 0, 3), (0, 1, 3), (0, 1, 4), (1, 1, 3), (1, 1, 4), (1, 2, 4), (1, 2, 5), (0, 2, 2), (0, 2, 3)," +
                " (0, 3, 3), (0, 3, 4), ...]");
        bagTriples_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
        bagTriples_fail_helper("[1, null, 3]");
    }

    private static void bagQuadruples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuadruples(readIntegerList(input)), output);
    }

    private static void bagQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuadruples(input), output);
    }

    private static void bagQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagQuadruples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuadruples() {
        bagQuadruples_helper("[]", "[]");
        bagQuadruples_helper("[5]", "[(5, 5, 5, 5)]");
        bagQuadruples_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 2), (1, 1, 2, 3), (1, 2, 2, 2), (1, 2, 2, 3), (1, 2, 3, 3)," +
                " (1, 2, 3, 4), (2, 2, 2, 2), (2, 2, 2, 3), (2, 2, 3, 3), (2, 2, 3, 4), (2, 3, 3, 3), (2, 3, 3, 4)," +
                " (2, 3, 4, 4), (1, 1, 1, 3), (1, 1, 1, 4), (1, 1, 2, 4), (1, 2, 2, 4), (2, 2, 2, 4), ...]");
        bagQuadruples_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 2), (1, 1, 2, 2), (1, 2, 2, 2), (1, 2, 2, 2), (1, 2, 2, 2)," +
                " (1, 2, 2, 4), (2, 2, 2, 2), (2, 2, 2, 2), (2, 2, 2, 2), (2, 2, 2, 4), (2, 2, 2, 2), (2, 2, 2, 4)," +
                " (2, 2, 4, 4), (1, 1, 1, 2), (1, 1, 1, 4), (1, 1, 2, 4), (1, 2, 2, 4), (2, 2, 2, 4), ...]");
        bagQuadruples_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0), (0, 0, 0, 1), (0, 0, 1, 1), (0, 0, 1, 2), (0, 1, 1, 1), (0, 1, 1, 2), (0, 1, 2, 2)," +
                " (0, 1, 2, 3), (1, 1, 1, 1), (1, 1, 1, 2), (1, 1, 2, 2), (1, 1, 2, 3), (1, 2, 2, 2), (1, 2, 2, 3)," +
                " (1, 2, 3, 3), (1, 2, 3, 4), (0, 0, 0, 2), (0, 0, 0, 3), (0, 0, 1, 3), (0, 0, 1, 4), ...]");
        bagQuadruples_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
        bagQuadruples_fail_helper("[1, null, 3]");
    }

    private static void bagQuintuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuintuples(readIntegerList(input)), output);
    }

    private static void bagQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagQuintuples(input), output);
    }

    private static void bagQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagQuintuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagQuintuples() {
        bagQuintuples_helper("[]", "[]");
        bagQuintuples_helper("[5]", "[(5, 5, 5, 5, 5)]");
        bagQuintuples_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 2), (1, 1, 1, 2, 3), (1, 1, 2, 2, 2)," +
                " (1, 1, 2, 2, 3), (1, 1, 2, 3, 3), (1, 1, 2, 3, 4), (1, 2, 2, 2, 2), (1, 2, 2, 2, 3)," +
                " (1, 2, 2, 3, 3), (1, 2, 2, 3, 4), (1, 2, 3, 3, 3), (1, 2, 3, 3, 4), (1, 2, 3, 4, 4)," +
                " (2, 2, 2, 2, 2), (2, 2, 2, 2, 3), (2, 2, 2, 3, 3), (2, 2, 2, 3, 4), (2, 2, 3, 3, 3), ...]");
        bagQuintuples_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 2), (1, 1, 1, 2, 2), (1, 1, 2, 2, 2)," +
                " (1, 1, 2, 2, 2), (1, 1, 2, 2, 2), (1, 1, 2, 2, 4), (1, 2, 2, 2, 2), (1, 2, 2, 2, 2)," +
                " (1, 2, 2, 2, 2), (1, 2, 2, 2, 4), (1, 2, 2, 2, 2), (1, 2, 2, 2, 4), (1, 2, 2, 4, 4)," +
                " (2, 2, 2, 2, 2), (2, 2, 2, 2, 2), (2, 2, 2, 2, 2), (2, 2, 2, 2, 4), (2, 2, 2, 2, 2), ...]");
        bagQuintuples_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0), (0, 0, 0, 0, 1), (0, 0, 0, 1, 1), (0, 0, 0, 1, 2), (0, 0, 1, 1, 1)," +
                " (0, 0, 1, 1, 2), (0, 0, 1, 2, 2), (0, 0, 1, 2, 3), (0, 1, 1, 1, 1), (0, 1, 1, 1, 2)," +
                " (0, 1, 1, 2, 2), (0, 1, 1, 2, 3), (0, 1, 2, 2, 2), (0, 1, 2, 2, 3), (0, 1, 2, 3, 3)," +
                " (0, 1, 2, 3, 4), (1, 1, 1, 1, 1), (1, 1, 1, 1, 2), (1, 1, 1, 2, 2), (1, 1, 1, 2, 3), ...]");
        bagQuintuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), ...]");
        bagQuintuples_fail_helper("[1, null, 3]");
    }

    private static void bagSextuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagSextuples(readIntegerList(input)), output);
    }

    private static void bagSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagSextuples(input), output);
    }

    private static void bagSextuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagSextuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSextuples() {
        bagSextuples_helper("[]", "[]");
        bagSextuples_helper("[5]", "[(5, 5, 5, 5, 5, 5)]");
        bagSextuples_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 2, 3)," +
                " (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 3), (1, 1, 1, 2, 3, 3), (1, 1, 1, 2, 3, 4)," +
                " (1, 1, 2, 2, 2, 2), (1, 1, 2, 2, 2, 3), (1, 1, 2, 2, 3, 3), (1, 1, 2, 2, 3, 4)," +
                " (1, 1, 2, 3, 3, 3), (1, 1, 2, 3, 3, 4), (1, 1, 2, 3, 4, 4), (1, 2, 2, 2, 2, 2)," +
                " (1, 2, 2, 2, 2, 3), (1, 2, 2, 2, 3, 3), (1, 2, 2, 2, 3, 4), (1, 2, 2, 3, 3, 3), ...]");
        bagSextuples_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 2), (1, 1, 1, 2, 2, 4)," +
                " (1, 1, 2, 2, 2, 2), (1, 1, 2, 2, 2, 2), (1, 1, 2, 2, 2, 2), (1, 1, 2, 2, 2, 4)," +
                " (1, 1, 2, 2, 2, 2), (1, 1, 2, 2, 2, 4), (1, 1, 2, 2, 4, 4), (1, 2, 2, 2, 2, 2)," +
                " (1, 2, 2, 2, 2, 2), (1, 2, 2, 2, 2, 2), (1, 2, 2, 2, 2, 4), (1, 2, 2, 2, 2, 2), ...]");
        bagSextuples_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0, 0), (0, 0, 0, 0, 0, 1), (0, 0, 0, 0, 1, 1), (0, 0, 0, 0, 1, 2)," +
                " (0, 0, 0, 1, 1, 1), (0, 0, 0, 1, 1, 2), (0, 0, 0, 1, 2, 2), (0, 0, 0, 1, 2, 3)," +
                " (0, 0, 1, 1, 1, 1), (0, 0, 1, 1, 1, 2), (0, 0, 1, 1, 2, 2), (0, 0, 1, 1, 2, 3)," +
                " (0, 0, 1, 2, 2, 2), (0, 0, 1, 2, 2, 3), (0, 0, 1, 2, 3, 3), (0, 0, 1, 2, 3, 4)," +
                " (0, 1, 1, 1, 1, 1), (0, 1, 1, 1, 1, 2), (0, 1, 1, 1, 2, 2), (0, 1, 1, 1, 2, 3), ...]");
        bagSextuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), ...]");
        bagSextuples_fail_helper("[1, null, 3]");
    }

    private static void bagSeptuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.bagSeptuples(readIntegerList(input)), output);
    }

    private static void bagSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.bagSeptuples(input), output);
    }

    private static void bagSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.bagSeptuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testBagSeptuples() {
        bagSeptuples_helper("[]", "[]");
        bagSeptuples_helper("[5]", "[(5, 5, 5, 5, 5, 5, 5)]");
        bagSeptuples_helper("[1, 2, 3, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 1, 2, 3)," +
                " (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 3), (1, 1, 1, 1, 2, 3, 3), (1, 1, 1, 1, 2, 3, 4)," +
                " (1, 1, 1, 2, 2, 2, 2), (1, 1, 1, 2, 2, 2, 3), (1, 1, 1, 2, 2, 3, 3), (1, 1, 1, 2, 2, 3, 4)," +
                " (1, 1, 1, 2, 3, 3, 3), (1, 1, 1, 2, 3, 3, 4), (1, 1, 1, 2, 3, 4, 4), (1, 1, 2, 2, 2, 2, 2)," +
                " (1, 1, 2, 2, 2, 2, 3), (1, 1, 2, 2, 2, 3, 3), (1, 1, 2, 2, 2, 3, 4), (1, 1, 2, 2, 3, 3, 3), ...]");
        bagSeptuples_helper("[1, 2, 2, 4]",
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 2), (1, 1, 1, 1, 1, 2, 2), (1, 1, 1, 1, 1, 2, 2)," +
                " (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 2), (1, 1, 1, 1, 2, 2, 4)," +
                " (1, 1, 1, 2, 2, 2, 2), (1, 1, 1, 2, 2, 2, 2), (1, 1, 1, 2, 2, 2, 2), (1, 1, 1, 2, 2, 2, 4)," +
                " (1, 1, 1, 2, 2, 2, 2), (1, 1, 1, 2, 2, 2, 4), (1, 1, 1, 2, 2, 4, 4), (1, 1, 2, 2, 2, 2, 2)," +
                " (1, 1, 2, 2, 2, 2, 2), (1, 1, 2, 2, 2, 2, 2), (1, 1, 2, 2, 2, 2, 4), (1, 1, 2, 2, 2, 2, 2), ...]");
        bagSeptuples_helper(EP.naturalIntegers(),
                "[(0, 0, 0, 0, 0, 0, 0), (0, 0, 0, 0, 0, 0, 1), (0, 0, 0, 0, 0, 1, 1), (0, 0, 0, 0, 0, 1, 2)," +
                " (0, 0, 0, 0, 1, 1, 1), (0, 0, 0, 0, 1, 1, 2), (0, 0, 0, 0, 1, 2, 2), (0, 0, 0, 0, 1, 2, 3)," +
                " (0, 0, 0, 1, 1, 1, 1), (0, 0, 0, 1, 1, 1, 2), (0, 0, 0, 1, 1, 2, 2), (0, 0, 0, 1, 1, 2, 3)," +
                " (0, 0, 0, 1, 2, 2, 2), (0, 0, 0, 1, 2, 2, 3), (0, 0, 0, 1, 2, 3, 3), (0, 0, 0, 1, 2, 3, 4)," +
                " (0, 0, 1, 1, 1, 1, 1), (0, 0, 1, 1, 1, 1, 2), (0, 0, 1, 1, 1, 2, 2), (0, 0, 1, 1, 1, 2, 3), ...]");
        bagSeptuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), ...]");
        bagSeptuples_fail_helper("[1, null, 3]");
    }

    private static void stringBags_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringBags(size, input), output);
    }

    private static void stringBags_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBags(size, input), output);
    }

    @Test
    public void testStringBags_int_String() {
        stringBags_int_String_helper(0, "", "[]");
        aeq(length(EP.stringBags(0, "")), 1);
        stringBags_int_String_helper(1, "", "[]");
        aeq(length(EP.stringBags(1, "")), 0);
        stringBags_int_String_helper(2, "", "[]");
        aeq(length(EP.stringBags(2, "")), 0);
        stringBags_int_String_helper(3, "", "[]");
        aeq(length(EP.stringBags(3, "")), 0);
        stringBags_int_String_helper(0, "a", "[]");
        stringBags_int_String_helper(1, "a", "[a]");
        stringBags_int_String_helper(2, "a", "[aa]");
        stringBags_int_String_helper(3, "a", "[aaa]");
        stringBags_int_String_helper(0, "abc", "[]");
        aeq(length(EP.stringBags(0, "abc")), 1);
        stringBags_int_String_helper(1, "abc", "[a, b, c]");
        stringBags_int_String_helper(2, "abc", "[aa, ab, bb, bc, ac, cc]");
        stringBags_int_String_helper(3, "abc", "[aaa, aab, abb, abc, bbb, bbc, bcc, aac, acc, ccc]");
        stringBags_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.stringBags(0, "abbc")), 1);
        stringBags_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringBags_int_String_helper(2, "abbc", "[aa, ab, bb, bb, ab, ac, bc, bb, bc, cc]");
        stringBags_int_String_helper(3, "abbc",
                "[aaa, aab, abb, abb, bbb, bbb, bbb, bbc, aab, aac, abc, bbc, abb, abc, acc, bcc, bbb, bbc, bcc," +
                " ccc]");
        stringBags_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.stringBags(0, "Mississippi")), 1);
        stringBags_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringBags_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, ii, is, Ms, Ms, is, ii, ss, ss, ss, is, is, ss, ss, ss, Mi, Ms, is, is, ...]");
        stringBags_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, Mii, Mis, iii, iis, iss, iss, MMs, MMs, Mis, Mii, iis, iii, iis, iss, Mss, Mss, Mss," +
                " Mis, ...]");
        try {
            EP.stringBags(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringBags(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringBags_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.stringBags(size), output);
    }

    @Test
    public void testStringBags_int() {
        stringBags_int_helper(0, "[]");
        aeq(length(EP.stringBags(0)), 1);
        stringBags_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        stringBags_int_helper(2,
                "[aa, ab, bb, bc, ac, ad, bd, be, cc, cd, dd, de, ce, cf, df, dg, ae, af, bf, bg, ...]");
        stringBags_int_helper(3,
                "[aaa, aab, abb, abc, bbb, bbc, bcc, bcd, aac, aad, abd, abe, bbd, bbe, bce, bcf, acc, acd, add," +
                " ade, ...]");
        try {
            EP.stringBags(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBags_Iterable() {
        aeqit(EP.bags(Collections.<Integer>emptyList()), "[[]]");
        simpleProviderHelper(EP.bags(Collections.singletonList(5)),
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        simpleProviderHelper(EP.bags(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 3]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 2], [1, 1, 1, 2], [1, 2, 3], [1, 1, 1, 1, 1, 1], [3, 3]," +
                " [2, 2, 2], [1, 1, 2, 2], ...]");
        simpleProviderHelper(EP.bags(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 2], [1, 3], [1, 1, 1, 2], [2, 3], [1, 2, 2], [1, 1, 1, 1, 1, 1]," +
                " ...]");
        simpleProviderHelper(EP.bags(EP.naturalIntegers()),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        simpleProviderHelper(EP.bags(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        try {
            toList(EP.bags(Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(EP.bags(Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testStringBags_String() {
        aeqit(EP.stringBags(""), "[]");
        aeq(length(EP.stringBags("")), 1);
        simpleProviderHelper(EP.stringBags("a"),
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        simpleProviderHelper(EP.stringBags("abc"),
                "[, a, aa, b, aaa, c, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, aaab, abc, aaaaaa, cc, bbb, aabb, ...]");
        simpleProviderHelper(EP.stringBags("abbc"),
                "[, a, aa, b, aaa, b, ab, c, aaaa, bb, aab, bb, aaaaa, ab, abb, ac, aaab, bc, abb, aaaaaa, ...]");
        simpleProviderHelper(EP.stringBags("Mississippi"),
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, ii, s, MMi, s, is, i, MMMMM, p, Ms, p, ...]");
    }

    @Test
    public void testStringBags() {
        simpleProviderHelper(EP.stringBags(),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, bb, f, aab, g, bc, h, aaaaa, i, ac, j, ...]");
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
        bagsAtLeast_helper(0, "[]", "[[]]");
        bagsAtLeast_helper(1, "[]", "[]");
        bagsAtLeast_helper(2, "[]", "[]");
        bagsAtLeast_helper(3, "[]", "[]");

        bagsAtLeast_helper(0, "[5]",
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsAtLeast_helper(1, "[5]",
                "[[5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsAtLeast_helper(2, "[5]",
                "[[5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        bagsAtLeast_helper(3, "[5]",
                "[[5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");

        bagsAtLeast_helper(0, "[1, 2, 3]",
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 3]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 2], [1, 1, 1, 2], [1, 2, 3], [1, 1, 1, 1, 1, 1], [3, 3]," +
                " [2, 2, 2], [1, 1, 2, 2], ...]");
        bagsAtLeast_helper(1, "[1, 2, 3]",
                "[[1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 3]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 2], [1, 1, 1, 2], [1, 2, 3], [1, 1, 1, 1, 1, 1], [3, 3]," +
                " [2, 2, 2], [1, 1, 2, 2], [2, 2, 3], ...]");
        bagsAtLeast_helper(2, "[1, 2, 3]",
                "[[1, 1], [1, 1, 1], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 3], [1, 1, 1, 1, 1], [1, 3]," +
                " [1, 2, 2], [1, 1, 1, 2], [1, 2, 3], [1, 1, 1, 1, 1, 1], [3, 3], [2, 2, 2], [1, 1, 2, 2]," +
                " [2, 2, 3], [1, 1, 1, 1, 2], [2, 3, 3], [1, 1, 2, 3], ...]");
        bagsAtLeast_helper(3, "[1, 2, 3]",
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 2], [1, 1, 1, 1, 1], [1, 2, 2], [1, 1, 1, 2], [1, 2, 3]," +
                " [1, 1, 1, 1, 1, 1], [2, 2, 2], [1, 1, 2, 2], [2, 2, 3], [1, 1, 1, 1, 2], [2, 3, 3], [1, 1, 2, 3]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1, 3], [1, 2, 2, 2], [1, 1, 1, 2, 2], [1, 2, 2, 3], [1, 1, 1, 1, 1, 2]," +
                " ...]");

        bagsAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 2], [1, 3], [1, 1, 1, 2], [2, 3], [1, 2, 2], [1, 1, 1, 1, 1, 1]," +
                " ...]");
        bagsAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 2], [1, 3], [1, 1, 1, 2], [2, 3], [1, 2, 2], [1, 1, 1, 1, 1, 1]," +
                " [2, 2], ...]");
        bagsAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 1], [1, 1, 1], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 2], [1, 1, 1, 1, 1], [1, 2]," +
                " [1, 2, 2], [1, 3], [1, 1, 1, 2], [2, 3], [1, 2, 2], [1, 1, 1, 1, 1, 1], [2, 2], [2, 2, 2], [2, 3]," +
                " [1, 1, 2, 2], [3, 3], ...]");
        bagsAtLeast_helper(3, "[1, 2, 2, 3]",
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 2], [1, 1, 1, 1, 1], [1, 2, 2], [1, 1, 1, 2], [1, 2, 2]," +
                " [1, 1, 1, 1, 1, 1], [2, 2, 2], [1, 1, 2, 2], [2, 2, 2], [1, 1, 1, 1, 2], [2, 2, 2], [1, 1, 2, 2]," +
                " [2, 2, 3], [1, 1, 1, 1, 1, 1, 1], [1, 1, 2], [1, 2, 2, 2], [1, 1, 3], [1, 1, 1, 2, 2], ...]");

        bagsAtLeast_helper(0, EP.naturalIntegers(),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        bagsAtLeast_helper(1, EP.naturalIntegers(),
                "[[0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], [0, 1, 1], ...]");
        bagsAtLeast_helper(2, EP.naturalIntegers(),
                "[[0, 0], [0, 0, 0], [0, 1], [0, 0, 0, 0], [1, 1], [0, 0, 1], [1, 2], [0, 0, 0, 0, 0], [0, 2]," +
                " [0, 1, 1], [0, 3], [0, 0, 0, 1], [1, 3], [0, 1, 2], [1, 4], [0, 0, 0, 0, 0, 0], [2, 2], [1, 1, 1]," +
                " [2, 3], [0, 0, 1, 1], ...]");
        bagsAtLeast_helper(3, EP.naturalIntegers(),
                "[[0, 0, 0], [0, 0, 0, 0], [0, 0, 1], [0, 0, 0, 0, 0], [0, 1, 1], [0, 0, 0, 1], [0, 1, 2]," +
                " [0, 0, 0, 0, 0, 0], [1, 1, 1], [0, 0, 1, 1], [1, 1, 2], [0, 0, 0, 0, 1], [1, 2, 2], [0, 0, 1, 2]," +
                " [1, 2, 3], [0, 0, 0, 0, 0, 0, 0], [0, 0, 2], [0, 1, 1, 1], [0, 0, 3], [0, 0, 0, 1, 1], ...]");

        bagsAtLeast_helper(0, repeat(1),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        bagsAtLeast_helper(1, repeat(1),
                "[[1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], ...]");
        bagsAtLeast_helper(2, repeat(1),
                "[[1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1], [1, 1], [1, 1, 1]," +
                " [1, 1], [1, 1, 1, 1], ...]");
        bagsAtLeast_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], ...]");

        bagsAtLeast_fail_helper(-1, "[]");
        bagsAtLeast_fail_helper(-1, "[1, 2, 3]");
        bagsAtLeast_fail_helper(1, "[null]");
        bagsAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringBagsAtLeast_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringBagsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringBagsAtLeast_String() {
        stringBagsAtLeast_String_helper(0, "", "[]");
        aeq(length(EP.stringBagsAtLeast(0, "")), 1);
        stringBagsAtLeast_String_helper(1, "", "[]");
        aeq(length(EP.stringBagsAtLeast(1, "")), 0);
        stringBagsAtLeast_String_helper(2, "", "[]");
        aeq(length(EP.stringBagsAtLeast(2, "")), 0);
        stringBagsAtLeast_String_helper(3, "", "[]");
        aeq(length(EP.stringBagsAtLeast(3, "")), 0);
        stringBagsAtLeast_String_helper(0, "a",
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsAtLeast_String_helper(1, "a",
                "[a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsAtLeast_String_helper(2, "a",
                "[aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsAtLeast_String_helper(3, "a",
                "[aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaa," +
                " aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaa, ...]");
        stringBagsAtLeast_String_helper(0, "abc",
                "[, a, aa, b, aaa, c, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, aaab, abc, aaaaaa, cc, bbb, aabb, ...]");
        stringBagsAtLeast_String_helper(1, "abc",
                "[a, aa, b, aaa, c, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, aaab, abc, aaaaaa, cc, bbb, aabb, bbc," +
                " ...]");
        stringBagsAtLeast_String_helper(2, "abc",
                "[aa, aaa, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, aaab, abc, aaaaaa, cc, bbb, aabb, bbc, aaaab, bcc," +
                " aabc, ...]");
        stringBagsAtLeast_String_helper(3, "abc",
                "[aaa, aaaa, aab, aaaaa, abb, aaab, abc, aaaaaa, bbb, aabb, bbc, aaaab, bcc, aabc, aaaaaaa, aac," +
                " abbb, aaabb, abbc, aaaaab, ...]");
        stringBagsAtLeast_String_helper(0, "abbc",
                "[, a, aa, b, aaa, b, ab, c, aaaa, bb, aab, bb, aaaaa, ab, abb, ac, aaab, bc, abb, aaaaaa, ...]");
        stringBagsAtLeast_String_helper(1, "abbc",
                "[a, aa, b, aaa, b, ab, c, aaaa, bb, aab, bb, aaaaa, ab, abb, ac, aaab, bc, abb, aaaaaa, bb, ...]");
        stringBagsAtLeast_String_helper(2, "abbc",
                "[aa, aaa, ab, aaaa, bb, aab, bb, aaaaa, ab, abb, ac, aaab, bc, abb, aaaaaa, bb, bbb, bc, aabb, cc," +
                " ...]");
        stringBagsAtLeast_String_helper(3, "abbc",
                "[aaa, aaaa, aab, aaaaa, abb, aaab, abb, aaaaaa, bbb, aabb, bbb, aaaab, bbb, aabb, bbc, aaaaaaa," +
                " aab, abbb, aac, aaabb, ...]");
        stringBagsAtLeast_String_helper(0, "Mississippi",
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, ii, s, MMi, s, is, i, MMMMM, p, Ms, p, ...]");
        stringBagsAtLeast_String_helper(1, "Mississippi",
                "[M, MM, i, MMM, s, Mi, s, MMMM, i, ii, s, MMi, s, is, i, MMMMM, p, Ms, p, Mii, ...]");
        stringBagsAtLeast_String_helper(2, "Mississippi",
                "[MM, MMM, Mi, MMMM, ii, MMi, is, MMMMM, Ms, Mii, Ms, MMMi, is, Mis, ii, MMMMMM, ss, iii, ss, MMii," +
                " ...]");
        stringBagsAtLeast_String_helper(3, "Mississippi",
                "[MMM, MMMM, MMi, MMMMM, Mii, MMMi, Mis, MMMMMM, iii, MMii, iis, MMMMi, iss, MMis, iss, MMMMMMM," +
                " MMs, Miii, MMs, MMMii, ...]");
        try {
            EP.stringBagsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringBagsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringBagsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringBagsAtLeast(minSize), output);
    }

    @Test
    public void testStringBagsAtLeast() {
        stringBagsAtLeast_helper(0,
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, bb, f, aab, g, bc, h, aaaaa, i, ac, j, ...]");
        stringBagsAtLeast_helper(1,
                "[a, aa, b, aaa, c, ab, d, aaaa, e, bb, f, aab, g, bc, h, aaaaa, i, ac, j, abb, ...]");
        stringBagsAtLeast_helper(2,
                "[aa, aaa, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, ad, aaab, bd, abc, be, aaaaaa, cc, bbb, cd, aabb," +
                " ...]");
        stringBagsAtLeast_helper(3,
                "[aaa, aaaa, aab, aaaaa, abb, aaab, abc, aaaaaa, bbb, aabb, bbc, aaaab, bcc, aabc, bcd, aaaaaaa," +
                " aac, abbb, aad, aaabb, ...]");
        try {
            EP.stringBagsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsetsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.subsetsLex(size, readIntegerList(input)), output);
    }

    private static void subsetsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            EP.subsetsLex(size, readIntegerListWithNulls(input));
            fail();
        } catch (ArithmeticException | NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsLex_int_List() {
        subsetsLex_int_List_helper(0, "[]", "[[]]");
        subsetsLex_int_List_helper(1, "[]", "[]");
        subsetsLex_int_List_helper(2, "[]", "[]");
        subsetsLex_int_List_helper(3, "[]", "[]");
        subsetsLex_int_List_helper(0, "[5]", "[[]]");
        subsetsLex_int_List_helper(1, "[5]", "[[5]]");
        subsetsLex_int_List_helper(2, "[5]", "[]");
        subsetsLex_int_List_helper(3, "[5]", "[]");
        subsetsLex_int_List_helper(0, "[1, 2, 3]", "[[]]");
        subsetsLex_int_List_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        subsetsLex_int_List_helper(2, "[1, 2, 3]", "[[1, 2], [1, 3], [2, 3]]");
        subsetsLex_int_List_helper(3, "[1, 2, 3]", "[[1, 2, 3]]");
        subsetsLex_int_List_helper(0, "[1, 2, 2, 3]", "[[]]");
        subsetsLex_int_List_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        subsetsLex_int_List_helper(2, "[1, 2, 2, 3]", "[[1, 2], [1, 2], [1, 3], [2, 2], [2, 3], [2, 3]]");
        subsetsLex_int_List_helper(3, "[1, 2, 2, 3]", "[[1, 2, 2], [1, 2, 3], [1, 2, 3], [2, 2, 3]]");
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
        subsetPairsLex_helper("[]", "[]");
        subsetPairsLex_helper("[5]", "[]");
        subsetPairsLex_helper("[1, 2, 3, 4]", "[(1, 2), (1, 3), (1, 4), (2, 3), (2, 4), (3, 4)]");
        subsetPairsLex_helper("[1, 2, 2, 4]", "[(1, 2), (1, 2), (1, 4), (2, 2), (2, 4), (2, 4)]");
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
        subsetTriplesLex_helper("[]", "[]");
        subsetTriplesLex_helper("[5]", "[]");
        subsetTriplesLex_helper("[1, 2, 3, 4]", "[(1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)]");
        subsetTriplesLex_helper("[1, 2, 2, 4]", "[(1, 2, 2), (1, 2, 4), (1, 2, 4), (2, 2, 4)]");
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
        subsetQuadruplesLex_helper("[]", "[]");
        subsetQuadruplesLex_helper("[5]", "[]");
        subsetQuadruplesLex_helper("[1, 2, 3, 4]", "[(1, 2, 3, 4)]");
        subsetQuadruplesLex_helper("[1, 2, 2, 4]", "[(1, 2, 2, 4)]");
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
        subsetQuintuplesLex_helper("[]", "[]");
        subsetQuintuplesLex_helper("[5]", "[]");
        subsetQuintuplesLex_helper("[1, 2, 3, 4]", "[]");
        subsetQuintuplesLex_helper("[1, 2, 2, 4]", "[]");
        subsetQuintuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5), (1, 2, 3, 4, 6), (1, 2, 3, 4, 7), (1, 2, 3, 4, 8), (1, 2, 3, 5, 6)," +
                " (1, 2, 3, 5, 7), (1, 2, 3, 5, 8), (1, 2, 3, 6, 7), (1, 2, 3, 6, 8), (1, 2, 3, 7, 8)," +
                " (1, 2, 4, 5, 6), (1, 2, 4, 5, 7), (1, 2, 4, 5, 8), (1, 2, 4, 6, 7), (1, 2, 4, 6, 8)," +
                " (1, 2, 4, 7, 8), (1, 2, 5, 6, 7), (1, 2, 5, 6, 8), (1, 2, 5, 7, 8), (1, 2, 6, 7, 8), ...]");
        subsetQuintuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5), (1, 2, 2, 4, 6), (1, 2, 2, 4, 7), (1, 2, 2, 4, 8), (1, 2, 2, 5, 6)," +
                " (1, 2, 2, 5, 7), (1, 2, 2, 5, 8), (1, 2, 2, 6, 7), (1, 2, 2, 6, 8), (1, 2, 2, 7, 8)," +
                " (1, 2, 4, 5, 6), (1, 2, 4, 5, 7), (1, 2, 4, 5, 8), (1, 2, 4, 6, 7), (1, 2, 4, 6, 8)," +
                " (1, 2, 4, 7, 8), (1, 2, 5, 6, 7), (1, 2, 5, 6, 8), (1, 2, 5, 7, 8), (1, 2, 6, 7, 8), ...]");
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
        subsetSextuplesLex_helper("[]", "[]");
        subsetSextuplesLex_helper("[5]", "[]");
        subsetSextuplesLex_helper("[1, 2, 3, 4]", "[]");
        subsetSextuplesLex_helper("[1, 2, 2, 4]", "[]");
        subsetSextuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6), (1, 2, 3, 4, 5, 7), (1, 2, 3, 4, 5, 8), (1, 2, 3, 4, 6, 7)," +
                " (1, 2, 3, 4, 6, 8), (1, 2, 3, 4, 7, 8), (1, 2, 3, 5, 6, 7), (1, 2, 3, 5, 6, 8)," +
                " (1, 2, 3, 5, 7, 8), (1, 2, 3, 6, 7, 8), (1, 2, 4, 5, 6, 7), (1, 2, 4, 5, 6, 8)," +
                " (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 2, 5, 6, 7, 8), (1, 3, 4, 5, 6, 7)," +
                " (1, 3, 4, 5, 6, 8), (1, 3, 4, 5, 7, 8), (1, 3, 4, 6, 7, 8), (1, 3, 5, 6, 7, 8), ...]");
        subsetSextuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6), (1, 2, 2, 4, 5, 7), (1, 2, 2, 4, 5, 8), (1, 2, 2, 4, 6, 7)," +
                " (1, 2, 2, 4, 6, 8), (1, 2, 2, 4, 7, 8), (1, 2, 2, 5, 6, 7), (1, 2, 2, 5, 6, 8)," +
                " (1, 2, 2, 5, 7, 8), (1, 2, 2, 6, 7, 8), (1, 2, 4, 5, 6, 7), (1, 2, 4, 5, 6, 8)," +
                " (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 2, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7)," +
                " (1, 2, 4, 5, 6, 8), (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 2, 5, 6, 7, 8), ...]");
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
        subsetSeptuplesLex_helper("[]", "[]");
        subsetSeptuplesLex_helper("[5]", "[]");
        subsetSeptuplesLex_helper("[1, 2, 3, 4]", "[]");
        subsetSeptuplesLex_helper("[1, 2, 2, 4]", "[]");
        subsetSeptuplesLex_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6, 7), (1, 2, 3, 4, 5, 6, 8), (1, 2, 3, 4, 5, 7, 8), (1, 2, 3, 4, 6, 7, 8)," +
                " (1, 2, 3, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (1, 3, 4, 5, 6, 7, 8), (2, 3, 4, 5, 6, 7, 8)]");
        subsetSeptuplesLex_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6, 7), (1, 2, 2, 4, 5, 6, 8), (1, 2, 2, 4, 5, 7, 8), (1, 2, 2, 4, 6, 7, 8)," +
                " (1, 2, 2, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (2, 2, 4, 5, 6, 7, 8)]");
        subsetSeptuplesLex_fail_helper("[1, 2, null, 4]");
        subsetSeptuplesLex_fail_helper("[null]");
    }

    private static void stringSubsetsLex_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringSubsetsLex(size, input), output);
    }

    private static void stringSubsetsLex_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsLex(size, input), output);
    }

    @Test
    public void testStringSubsetsLex_int_String() {
        stringSubsetsLex_helper(0, "", "[]");
        aeq(length(EP.stringSubsetsLex(0, "")), 1);
        stringSubsetsLex_helper(1, "", "[]");
        aeq(length(EP.stringSubsetsLex(1, "")), 0);
        stringSubsetsLex_helper(2, "", "[]");
        aeq(length(EP.stringSubsetsLex(2, "")), 0);
        stringSubsetsLex_helper(3, "", "[]");
        aeq(length(EP.stringSubsetsLex(3, "")), 0);
        stringSubsetsLex_helper(0, "a", "[]");
        aeq(length(EP.stringSubsetsLex(0, "a")), 1);
        stringSubsetsLex_helper(1, "a", "[a]");
        stringSubsetsLex_helper(2, "a", "[]");
        aeq(length(EP.stringSubsetsLex(2, "a")), 0);
        stringSubsetsLex_helper(3, "a", "[]");
        aeq(length(EP.stringSubsetsLex(3, "a")), 0);
        stringSubsetsLex_helper(0, "abc", "[]");
        aeq(length(EP.stringSubsetsLex(0, "abc")), 1);
        stringSubsetsLex_helper(1, "abc", "[a, b, c]");
        stringSubsetsLex_helper(2, "abc", "[ab, ac, bc]");
        stringSubsetsLex_helper(3, "abc", "[abc]");
        stringSubsetsLex_helper(0, "abbc", "[]");
        aeq(length(EP.stringSubsetsLex(0, "abbc")), 1);
        stringSubsetsLex_helper(1, "abbc", "[a, b, b, c]");
        stringSubsetsLex_helper(2, "abbc", "[ab, ab, ac, bb, bc, bc]");
        stringSubsetsLex_helper(3, "abbc", "[abb, abc, abc, bbc]");
        stringSubsetsLex_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.stringSubsetsLex(0, "Mississippi")), 1);
        stringSubsetsLex_helper_limit(1, "Mississippi", "[M, i, i, i, i, p, p, s, s, s, s]");
        stringSubsetsLex_helper_limit(2, "Mississippi",
                "[Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ip, ip, is, is, is, is, ii, ...]");
        stringSubsetsLex_helper_limit(3, "Mississippi",
                "[Mii, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mip," +
                " Mip, ...]");
        try {
            EP.stringSubsetsLex(-1, "");
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            EP.stringSubsetsLex(-1, "abc");
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void subsetsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.subsetsLex(readIntegerList(input)), output);
    }

    private static void subsetsLex_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetsLex(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetsLex_List() {
        subsetsLex_List_helper("[]", "[[]]");
        subsetsLex_List_helper("[5]", "[[], [5]]");
        subsetsLex_List_helper("[1, 2, 3]", "[[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]");
        subsetsLex_List_helper("[1, 2, 3, 4]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 3], [1, 3, 4], [1, 4], [2], [2, 3]," +
                " [2, 3, 4], [2, 4], [3], [3, 4], [4]]");
        subsetsLex_List_helper("[1, 2, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2], [1, 2, 3], [1, 3], [2], [2, 2]," +
                " [2, 2, 3], [2, 3], [2], [2, 3], [3]]");
        subsetsLex_fail_helper("[null]");
        subsetsLex_fail_helper("[1, null, 3]");
    }

    @Test
    public void testStringSubsetsLex_String() {
        aeqit(EP.stringSubsetsLex(""), "[]");
        aeq(length(EP.stringSubsetsLex("")), 1);
        aeqit(EP.stringSubsetsLex("a"), "[, a]");
        aeqit(EP.stringSubsetsLex("abc"), "[, a, ab, abc, ac, b, bc, c]");
        aeqit(EP.stringSubsetsLex("abcd"), "[, a, ab, abc, abcd, abd, ac, acd, ad, b, bc, bcd, bd, c, cd, d]");
        aeqit(EP.stringSubsetsLex("abbc"), "[, a, ab, abb, abbc, abc, ab, abc, ac, b, bb, bbc, bc, b, bc, c]");
        simpleProviderHelper(EP.stringSubsetsLex("Mississippi"),
                "[, M, Mi, Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss," +
                " Miiiippsss, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss, ...]");
    }

    private static void subsetsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(EP.subsetsLexAtLeast(minSize, readIntegerList(input)), output);
    }

    private static void subsetsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(EP.subsetsLexAtLeast(minSize, readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testSubsetsLexAtLeast() {
        subsetsLexAtLeast_helper(0, "[]", "[[]]");
        subsetsLexAtLeast_helper(1, "[]", "[]");
        subsetsLexAtLeast_helper(2, "[]", "[]");
        subsetsLexAtLeast_helper(3, "[]", "[]");

        subsetsLexAtLeast_helper(0, "[5]", "[[], [5]]");
        subsetsLexAtLeast_helper(1, "[5]", "[[5]]");
        subsetsLexAtLeast_helper(2, "[5]", "[]");
        subsetsLexAtLeast_helper(3, "[5]", "[]");

        subsetsLexAtLeast_helper(0, "[1, 2, 3]", "[[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]");
        subsetsLexAtLeast_helper(1, "[1, 2, 3]", "[[1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]");
        subsetsLexAtLeast_helper(2, "[1, 2, 3]", "[[1, 2], [1, 2, 3], [1, 3], [2, 3]]");
        subsetsLexAtLeast_helper(3, "[1, 2, 3]", "[[1, 2, 3]]");

        subsetsLexAtLeast_helper(0, "[1, 2, 3, 4]",
                "[[], [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 3], [1, 3, 4], [1, 4], [2], [2, 3]," +
                " [2, 3, 4], [2, 4], [3], [3, 4], [4]]");
        subsetsLexAtLeast_helper(1, "[1, 2, 3, 4]",
                "[[1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 3], [1, 3, 4], [1, 4], [2], [2, 3]," +
                " [2, 3, 4], [2, 4], [3], [3, 4], [4]]");
        subsetsLexAtLeast_helper(2, "[1, 2, 3, 4]",
                "[[1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 3], [1, 3, 4], [1, 4], [2, 3], [2, 3, 4], [2, 4]," +
                " [3, 4]]");
        subsetsLexAtLeast_helper(3, "[1, 2, 3, 4]", "[[1, 2, 3], [1, 2, 3, 4], [1, 2, 4], [1, 3, 4], [2, 3, 4]]");

        subsetsLexAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2], [1, 2, 3], [1, 3], [2], [2, 2]," +
                " [2, 2, 3], [2, 3], [2], [2, 3], [3]]");
        subsetsLexAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2], [1, 2, 3], [1, 3], [2], [2, 2]," +
                " [2, 2, 3], [2, 3], [2], [2, 3], [3]]");
        subsetsLexAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2], [1, 2, 3], [1, 3], [2, 2], [2, 2, 3], [2, 3]," +
                " [2, 3]]");
        subsetsLexAtLeast_helper(3, "[1, 2, 2, 3]", "[[1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3], [2, 2, 3]]");

        subsetsLexAtLeast_fail_helper(-1, "[]");
        subsetsLexAtLeast_fail_helper(-1, "[1, 2, 3]");
        subsetsLexAtLeast_fail_helper(1, "[null]");
        subsetsLexAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringSubsetsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringSubsetsLexAtLeast(minSize, input), output);
    }

    private static void stringSubsetsLexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsLexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsLexAtLeast_int_String() {
        stringSubsetsLexAtLeast_helper(0, "", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(0, "")), 1);
        stringSubsetsLexAtLeast_helper(1, "", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(1, "")), 0);
        stringSubsetsLexAtLeast_helper(2, "", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(2, "")), 0);
        stringSubsetsLexAtLeast_helper(3, "", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(3, "")), 0);
        stringSubsetsLexAtLeast_helper(0, "a", "[, a]");
        stringSubsetsLexAtLeast_helper(1, "a", "[a]");
        stringSubsetsLexAtLeast_helper(2, "a", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(2, "a")), 0);
        stringSubsetsLexAtLeast_helper(3, "a", "[]");
        aeq(length(EP.stringSubsetsLexAtLeast(3, "a")), 0);
        stringSubsetsLexAtLeast_helper(0, "abc", "[, a, ab, abc, ac, b, bc, c]");
        stringSubsetsLexAtLeast_helper(1, "abc", "[a, ab, abc, ac, b, bc, c]");
        stringSubsetsLexAtLeast_helper(2, "abc", "[ab, abc, ac, bc]");
        stringSubsetsLexAtLeast_helper(3, "abc", "[abc]");
        stringSubsetsLexAtLeast_helper(0, "abbc", "[, a, ab, abb, abbc, abc, ab, abc, ac, b, bb, bbc, bc, b, bc, c]");
        stringSubsetsLexAtLeast_helper(1, "abbc", "[a, ab, abb, abbc, abc, ab, abc, ac, b, bb, bbc, bc, b, bc, c]");
        stringSubsetsLexAtLeast_helper(2, "abbc", "[ab, abb, abbc, abc, ab, abc, ac, bb, bbc, bc, bc]");
        stringSubsetsLexAtLeast_helper(3, "abbc", "[abb, abbc, abc, abc, bbc]");
        stringSubsetsLexAtLeast_helper_limit(0, "Mississippi",
                "[, M, Mi, Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss," +
                " Miiiippsss, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss, ...]");
        stringSubsetsLexAtLeast_helper_limit(1, "Mississippi",
                "[M, Mi, Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss," +
                " Miiiippsss, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss," +
                " Miiiipps, ...]");
        stringSubsetsLexAtLeast_helper_limit(2, "Mississippi",
                "[Mi, Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss, Miiiippsss," +
                " Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss," +
                " ...]");
        stringSubsetsLexAtLeast_helper_limit(3, "Mississippi",
                "[Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss, Miiiippsss," +
                " Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss," +
                " Miiiipps, ...]");
        try {
            EP.stringSubsetsLexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringSubsetsLexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
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
        subsetsShortlex_helper("[]", "[[]]");
        subsetsShortlex_helper("[5]", "[[], [5]]");
        subsetsShortlex_helper("[1, 2, 3]", "[[], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]");
        subsetsShortlex_helper("[1, 2, 2, 3]",
                "[[], [1], [2], [2], [3], [1, 2], [1, 2], [1, 3], [2, 2], [2, 3], [2, 3], [1, 2, 2], [1, 2, 3]," +
                " [1, 2, 3], [2, 2, 3], [1, 2, 2, 3]]");
        subsetsShortlex_fail_helper("[1, null, 3]");
        subsetsShortlex_fail_helper("[null]");
    }

    private static void stringSubsetsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsShortlex(input), output);
    }

    @Test
    public void testStringSubsetsShortlex() {
        stringSubsetsShortlex_helper("", "[]");
        aeq(length(EP.stringSubsetsShortlex("")), 1);
        stringSubsetsShortlex_helper("a", "[, a]");
        stringSubsetsShortlex_helper("abc", "[, a, b, c, ab, ac, bc, abc]");
        stringSubsetsShortlex_helper("abbc","[, a, b, b, c, ab, ab, ac, bb, bc, bc, abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlex_helper("Mississippi",
                "[, M, i, i, i, i, p, p, s, s, s, s, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, ...]");
    }

    private static void subsetsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetsShortlexAtLeast(minSize, readIntegerList(input)), output);
    }

    @Test
    public void testSubsetsShortlexAtLeast() {
        subsetsShortlexAtLeast_helper(0, "[]", "[[]]");
        subsetsShortlexAtLeast_helper(1, "[]", "[]");
        subsetsShortlexAtLeast_helper(2, "[]", "[]");
        subsetsShortlexAtLeast_helper(3, "[]", "[]");

        subsetsShortlexAtLeast_helper(0, "[5]", "[[], [5]]");
        subsetsShortlexAtLeast_helper(1, "[5]", "[[5]]");
        subsetsShortlexAtLeast_helper(2, "[5]", "[]");
        subsetsShortlexAtLeast_helper(3, "[5]", "[]");

        subsetsShortlexAtLeast_helper(0, "[1, 2, 3]", "[[], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]");
        subsetsShortlexAtLeast_helper(1, "[1, 2, 3]", "[[1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]");
        subsetsShortlexAtLeast_helper(2, "[1, 2, 3]", "[[1, 2], [1, 3], [2, 3], [1, 2, 3]]");
        subsetsShortlexAtLeast_helper(3, "[1, 2, 3]", "[[1, 2, 3]]");

        try {
            EP.subsetsShortlexAtLeast(-1, Collections.<Integer>emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.subsetsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toList(EP.subsetsShortlexAtLeast(1, Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(EP.subsetsShortlexAtLeast(1, Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    private static void stringSubsetsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsShortlexAtLeast() {
        stringSubsetsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(EP.stringSubsetsShortlexAtLeast(0, "")), 1);
        stringSubsetsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(EP.stringSubsetsShortlexAtLeast(1, "")), 0);
        stringSubsetsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(EP.stringSubsetsShortlexAtLeast(2, "")), 0);
        stringSubsetsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(EP.stringSubsetsShortlexAtLeast(3, "")), 0);
        stringSubsetsShortlexAtLeast_helper(0, "a", "[, a]");
        stringSubsetsShortlexAtLeast_helper(1, "a", "[a]");
        stringSubsetsShortlexAtLeast_helper(2, "a", "[]");
        stringSubsetsShortlexAtLeast_helper(3, "a", "[]");
        stringSubsetsShortlexAtLeast_helper(0, "abc", "[, a, b, c, ab, ac, bc, abc]");
        stringSubsetsShortlexAtLeast_helper(1, "abc", "[a, b, c, ab, ac, bc, abc]");
        stringSubsetsShortlexAtLeast_helper(2, "abc", "[ab, ac, bc, abc]");
        stringSubsetsShortlexAtLeast_helper(3, "abc", "[abc]");
        stringSubsetsShortlexAtLeast_helper(0, "abbc",
                "[, a, b, b, c, ab, ab, ac, bb, bc, bc, abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlexAtLeast_helper(1, "abbc",
                "[a, b, b, c, ab, ab, ac, bb, bc, bc, abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlexAtLeast_helper(2, "abbc", "[ab, ab, ac, bb, bc, bc, abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlexAtLeast_helper(3, "abbc", "[abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlexAtLeast_helper(0, "Mississippi",
                "[, M, i, i, i, i, p, p, s, s, s, s, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, ...]");
        stringSubsetsShortlexAtLeast_helper(1, "Mississippi",
                "[M, i, i, i, i, p, p, s, s, s, s, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, ...]");
        stringSubsetsShortlexAtLeast_helper(2, "Mississippi",
                "[Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ip, ip, is, is, is, is, ii, ...]");
        stringSubsetsShortlexAtLeast_helper(3, "Mississippi",
                "[Mii, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mip," +
                " Mip, ...]");
        try {
            EP.stringSubsetsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringSubsetsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsets_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.subsets(size, readIntegerList(input)), output);
    }

    private static void subsets_int_Iterable_helper(
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
    public void testSubsets_int_List() {
        subsets_int_Iterable_helper(0, "[]", "[[]]");
        subsets_int_Iterable_helper(1, "[]", "[]");
        subsets_int_Iterable_helper(2, "[]", "[]");
        subsets_int_Iterable_helper(3, "[]", "[]");
        subsets_int_Iterable_helper(0, "[5]", "[[]]");
        subsets_int_Iterable_helper(1, "[5]", "[[5]]");
        subsets_int_Iterable_helper(2, "[5]", "[]");
        subsets_int_Iterable_helper(3, "[5]", "[]");
        subsets_int_Iterable_helper(0, "[1, 2, 3]", "[[]]");
        subsets_int_Iterable_helper(1, "[1, 2, 3]", "[[1], [2], [3]]");
        subsets_int_Iterable_helper(2, "[1, 2, 3]", "[[1, 2], [1, 3], [2, 3]]");
        subsets_int_Iterable_helper(3, "[1, 2, 3]", "[[1, 2, 3]]");
        subsets_int_Iterable_helper(0, "[1, 2, 2, 3]", "[[]]");
        subsets_int_Iterable_helper(1, "[1, 2, 2, 3]", "[[1], [2], [2], [3]]");
        subsets_int_Iterable_helper(2, "[1, 2, 2, 3]", "[[1, 2], [1, 2], [2, 2], [2, 3], [1, 3], [2, 3]]");
        subsets_int_Iterable_helper(3, "[1, 2, 2, 3]", "[[1, 2, 2], [1, 2, 3], [1, 2, 3], [2, 2, 3]]");
        subsets_int_Iterable_helper(0, EP.positiveIntegers(), "[[]]");
        subsets_int_Iterable_helper(1, EP.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        subsets_int_Iterable_helper(2, EP.positiveIntegers(),
                "[[1, 2], [1, 3], [2, 3], [2, 4], [1, 4], [1, 5], [2, 5], [2, 6], [3, 4], [3, 5], [4, 5], [4, 6]," +
                " [3, 6], [3, 7], [4, 7], [4, 8], [1, 6], [1, 7], [2, 7], [2, 8], ...]");
        subsets_int_Iterable_helper(3, EP.positiveIntegers(),
                "[[1, 2, 3], [1, 2, 4], [1, 3, 4], [1, 3, 5], [2, 3, 4], [2, 3, 5], [2, 4, 5], [2, 4, 6], [1, 2, 5]," +
                " [1, 2, 6], [1, 3, 6], [1, 3, 7], [2, 3, 6], [2, 3, 7], [2, 4, 7], [2, 4, 8], [1, 4, 5], [1, 4, 6]," +
                " [1, 5, 6], [1, 5, 7], ...]");
        subsets_int_Iterable_helper(0, repeat(1), "[[]]");
        subsets_int_Iterable_helper(1, repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]");
        subsets_int_Iterable_helper(2, repeat(1),
                "[[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1]," +
                " [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], ...]");
        subsets_int_Iterable_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], ...]");
        subsets_int_Iterable_fail_helper(-1, "[]");
        subsets_int_Iterable_fail_helper(-1, "[1, 2, 3]");
        subsets_int_Iterable_fail_helper(1, "[1, null, 3]");
        subsets_int_Iterable_fail_helper(1, "[null]");
    }

    private static void subsetPairs_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetPairs(readIntegerList(input)), output);
    }

    private static void subsetPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetPairs(input), output);
    }

    private static void subsetPairs_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetPairs(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetPairs() {
        subsetPairs_helper("[]", "[]");
        subsetPairs_helper("[5]", "[]");
        subsetPairs_helper("[1, 2, 3, 4]", "[(1, 2), (1, 3), (2, 3), (2, 4), (1, 4), (3, 4)]");
        subsetPairs_helper("[1, 2, 2, 4]", "[(1, 2), (1, 2), (2, 2), (2, 4), (1, 4), (2, 4)]");
        subsetPairs_helper(EP.naturalIntegers(),
                "[(0, 1), (0, 2), (1, 2), (1, 3), (0, 3), (0, 4), (1, 4), (1, 5), (2, 3), (2, 4), (3, 4), (3, 5)," +
                " (2, 5), (2, 6), (3, 6), (3, 7), (0, 5), (0, 6), (1, 6), (1, 7), ...]");
        subsetPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
        subsetPairs_fail_helper("[1, null, 3]");
    }

    private static void subsetTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetTriples(readIntegerList(input)), output);
    }

    private static void subsetTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetTriples(input), output);
    }

    private static void subsetTriples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetTriples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetTriples() {
        subsetTriples_helper("[]", "[]");
        subsetTriples_helper("[5]", "[]");
        subsetTriples_helper("[1, 2, 3, 4]", "[(1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)]");
        subsetTriples_helper("[1, 2, 2, 4]", "[(1, 2, 2), (1, 2, 4), (1, 2, 4), (2, 2, 4)]");
        subsetTriples_helper(EP.naturalIntegers(),
                "[(0, 1, 2), (0, 1, 3), (0, 2, 3), (0, 2, 4), (1, 2, 3), (1, 2, 4), (1, 3, 4), (1, 3, 5), (0, 1, 4)," +
                " (0, 1, 5), (0, 2, 5), (0, 2, 6), (1, 2, 5), (1, 2, 6), (1, 3, 6), (1, 3, 7), (0, 3, 4), (0, 3, 5)," +
                " (0, 4, 5), (0, 4, 6), ...]");
        subsetTriples_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
        subsetTriples_fail_helper("[1, null, 3]");
    }

    private static void subsetQuadruples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuadruples(readIntegerList(input)), output);
    }

    private static void subsetQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuadruples(input), output);
    }

    private static void subsetQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetQuadruples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuadruples() {
        subsetQuadruples_helper("[]", "[]");
        subsetQuadruples_helper("[5]", "[]");
        subsetQuadruples_helper("[1, 2, 3, 4]", "[(1, 2, 3, 4)]");
        subsetQuadruples_helper("[1, 2, 2, 4]", "[(1, 2, 2, 4)]");
        subsetQuadruples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3), (0, 1, 2, 4), (0, 1, 3, 4), (0, 1, 3, 5), (0, 2, 3, 4), (0, 2, 3, 5), (0, 2, 4, 5)," +
                " (0, 2, 4, 6), (1, 2, 3, 4), (1, 2, 3, 5), (1, 2, 4, 5), (1, 2, 4, 6), (1, 3, 4, 5), (1, 3, 4, 6)," +
                " (1, 3, 5, 6), (1, 3, 5, 7), (0, 1, 2, 5), (0, 1, 2, 6), (0, 1, 3, 6), (0, 1, 3, 7), ...]");
        subsetQuadruples_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
        subsetQuadruples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetQuintuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuintuples(readIntegerList(input)), output);
    }

    private static void subsetQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetQuintuples(input), output);
    }

    private static void subsetQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetQuintuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuintuples() {
        subsetQuintuples_helper("[]", "[]");
        subsetQuintuples_helper("[5]", "[]");
        subsetQuintuples_helper("[1, 2, 3, 4]", "[]");
        subsetQuintuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5), (1, 2, 3, 4, 6), (1, 2, 3, 5, 6), (1, 2, 3, 5, 7), (1, 2, 4, 5, 6)," +
                " (1, 2, 4, 5, 7), (1, 2, 4, 6, 7), (1, 2, 4, 6, 8), (1, 3, 4, 5, 6), (1, 3, 4, 5, 7)," +
                " (1, 3, 4, 6, 7), (1, 3, 4, 6, 8), (1, 3, 5, 6, 7), (1, 3, 5, 6, 8), (1, 3, 5, 7, 8)," +
                " (2, 3, 4, 5, 6), (2, 3, 4, 5, 7), (2, 3, 4, 6, 7), (2, 3, 4, 6, 8), (2, 3, 5, 6, 7), ...]");
        subsetQuintuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5), (1, 2, 2, 4, 6), (1, 2, 2, 5, 6), (1, 2, 2, 5, 7), (1, 2, 4, 5, 6)," +
                " (1, 2, 4, 5, 7), (1, 2, 4, 6, 7), (1, 2, 4, 6, 8), (1, 2, 4, 5, 6), (1, 2, 4, 5, 7)," +
                " (1, 2, 4, 6, 7), (1, 2, 4, 6, 8), (1, 2, 5, 6, 7), (1, 2, 5, 6, 8), (1, 2, 5, 7, 8)," +
                " (2, 2, 4, 5, 6), (2, 2, 4, 5, 7), (2, 2, 4, 6, 7), (2, 2, 4, 6, 8), (2, 2, 5, 6, 7), ...]");
        subsetQuintuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4), (0, 1, 2, 3, 5), (0, 1, 2, 4, 5), (0, 1, 2, 4, 6), (0, 1, 3, 4, 5)," +
                " (0, 1, 3, 4, 6), (0, 1, 3, 5, 6), (0, 1, 3, 5, 7), (0, 2, 3, 4, 5), (0, 2, 3, 4, 6)," +
                " (0, 2, 3, 5, 6), (0, 2, 3, 5, 7), (0, 2, 4, 5, 6), (0, 2, 4, 5, 7), (0, 2, 4, 6, 7)," +
                " (0, 2, 4, 6, 8), (1, 2, 3, 4, 5), (1, 2, 3, 4, 6), (1, 2, 3, 5, 6), (1, 2, 3, 5, 7), ...]");
        subsetQuintuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), (1, 1, 1, 1, 1), ...]");
        subsetQuintuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetSextuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSextuples(readIntegerList(input)), output);
    }

    private static void subsetSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSextuples(input), output);
    }

    private static void subsetSextuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetSextuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSextuples() {
        subsetSextuples_helper("[]", "[]");
        subsetSextuples_helper("[5]", "[]");
        subsetSextuples_helper("[1, 2, 3, 4]", "[]");
        subsetSextuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6), (1, 2, 3, 4, 5, 7), (1, 2, 3, 4, 6, 7), (1, 2, 3, 4, 6, 8)," +
                " (1, 2, 3, 5, 6, 7), (1, 2, 3, 5, 6, 8), (1, 2, 3, 5, 7, 8), (1, 2, 4, 5, 6, 7)," +
                " (1, 2, 4, 5, 6, 8), (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 3, 4, 5, 6, 7)," +
                " (1, 3, 4, 5, 6, 8), (1, 3, 4, 5, 7, 8), (1, 3, 4, 6, 7, 8), (1, 3, 5, 6, 7, 8)," +
                " (2, 3, 4, 5, 6, 7), (2, 3, 4, 5, 6, 8), (2, 3, 4, 5, 7, 8), (2, 3, 4, 6, 7, 8), ...]");
        subsetSextuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6), (1, 2, 2, 4, 5, 7), (1, 2, 2, 4, 6, 7), (1, 2, 2, 4, 6, 8)," +
                " (1, 2, 2, 5, 6, 7), (1, 2, 2, 5, 6, 8), (1, 2, 2, 5, 7, 8), (1, 2, 4, 5, 6, 7)," +
                " (1, 2, 4, 5, 6, 8), (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 2, 4, 5, 6, 7)," +
                " (1, 2, 4, 5, 6, 8), (1, 2, 4, 5, 7, 8), (1, 2, 4, 6, 7, 8), (1, 2, 5, 6, 7, 8)," +
                " (2, 2, 4, 5, 6, 7), (2, 2, 4, 5, 6, 8), (2, 2, 4, 5, 7, 8), (2, 2, 4, 6, 7, 8), ...]");
        subsetSextuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4, 5), (0, 1, 2, 3, 4, 6), (0, 1, 2, 3, 5, 6), (0, 1, 2, 3, 5, 7)," +
                " (0, 1, 2, 4, 5, 6), (0, 1, 2, 4, 5, 7), (0, 1, 2, 4, 6, 7), (0, 1, 2, 4, 6, 8)," +
                " (0, 1, 3, 4, 5, 6), (0, 1, 3, 4, 5, 7), (0, 1, 3, 4, 6, 7), (0, 1, 3, 4, 6, 8)," +
                " (0, 1, 3, 5, 6, 7), (0, 1, 3, 5, 6, 8), (0, 1, 3, 5, 7, 8), (0, 1, 3, 5, 7, 9)," +
                " (0, 2, 3, 4, 5, 6), (0, 2, 3, 4, 5, 7), (0, 2, 3, 4, 6, 7), (0, 2, 3, 4, 6, 8), ...]");
        subsetSextuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1), ...]");
        subsetSextuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void subsetSeptuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSeptuples(readIntegerList(input)), output);
    }

    private static void subsetSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.subsetSeptuples(input), output);
    }

    private static void subsetSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(EP.subsetSeptuples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetSeptuples() {
        subsetSeptuples_helper("[]", "[]");
        subsetSeptuples_helper("[5]", "[]");
        subsetSeptuples_helper("[1, 2, 3, 4]", "[]");
        subsetSeptuples_helper("[1, 2, 3, 4, 5, 6, 7, 8]",
                "[(1, 2, 3, 4, 5, 6, 7), (1, 2, 3, 4, 5, 6, 8), (1, 2, 3, 4, 5, 7, 8), (1, 2, 3, 4, 6, 7, 8)," +
                " (1, 2, 3, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (1, 3, 4, 5, 6, 7, 8), (2, 3, 4, 5, 6, 7, 8)]");
        subsetSeptuples_helper("[1, 2, 2, 4, 5, 6, 7, 8]",
                "[(1, 2, 2, 4, 5, 6, 7), (1, 2, 2, 4, 5, 6, 8), (1, 2, 2, 4, 5, 7, 8), (1, 2, 2, 4, 6, 7, 8)," +
                " (1, 2, 2, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (1, 2, 4, 5, 6, 7, 8), (2, 2, 4, 5, 6, 7, 8)]");
        subsetSeptuples_helper(EP.naturalIntegers(),
                "[(0, 1, 2, 3, 4, 5, 6), (0, 1, 2, 3, 4, 5, 7), (0, 1, 2, 3, 4, 6, 7), (0, 1, 2, 3, 4, 6, 8)," +
                " (0, 1, 2, 3, 5, 6, 7), (0, 1, 2, 3, 5, 6, 8), (0, 1, 2, 3, 5, 7, 8), (0, 1, 2, 3, 5, 7, 9)," +
                " (0, 1, 2, 4, 5, 6, 7), (0, 1, 2, 4, 5, 6, 8), (0, 1, 2, 4, 5, 7, 8), (0, 1, 2, 4, 5, 7, 9)," +
                " (0, 1, 2, 4, 6, 7, 8), (0, 1, 2, 4, 6, 7, 9), (0, 1, 2, 4, 6, 8, 9), (0, 1, 2, 4, 6, 8, 10)," +
                " (0, 1, 3, 4, 5, 6, 7), (0, 1, 3, 4, 5, 6, 8), (0, 1, 3, 4, 5, 7, 8), (0, 1, 3, 4, 5, 7, 9), ...]");
        subsetSeptuples_helper(repeat(1),
                "[(1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1)," +
                " (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), (1, 1, 1, 1, 1, 1, 1), ...]");
        subsetSeptuples_fail_helper("[1, null, 3, 4, 5, 6, 7, 8]");
    }

    private static void stringSubsets_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(EP.stringSubsets(size, input), output);
    }

    private static void stringSubsets_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsets(size, input), output);
    }

    @Test
    public void testStringSubsets_int_String() {
        stringSubsets_int_String_helper(0, "", "[]");
        aeq(length(EP.stringSubsets(0, "")), 1);
        stringSubsets_int_String_helper(1, "", "[]");
        aeq(length(EP.stringSubsets(1, "")), 0);
        stringSubsets_int_String_helper(2, "", "[]");
        aeq(length(EP.stringSubsets(2, "")), 0);
        stringSubsets_int_String_helper(3, "", "[]");
        aeq(length(EP.stringSubsets(3, "")), 0);
        stringSubsets_int_String_helper(0, "a", "[]");
        stringSubsets_int_String_helper(1, "a", "[a]");
        stringSubsets_int_String_helper(2, "a", "[]");
        aeq(length(EP.stringSubsets(2, "a")), 0);
        stringSubsets_int_String_helper(3, "a", "[]");
        aeq(length(EP.stringSubsets(3, "a")), 0);
        stringSubsets_int_String_helper(0, "abc", "[]");
        aeq(length(EP.stringSubsets(0, "abc")), 1);
        stringSubsets_int_String_helper(1, "abc", "[a, b, c]");
        stringSubsets_int_String_helper(2, "abc", "[ab, ac, bc]");
        stringSubsets_int_String_helper(3, "abc", "[abc]");
        stringSubsets_int_String_helper(0, "abbc", "[]");
        aeq(length(EP.stringSubsets(0, "abbc")), 1);
        stringSubsets_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringSubsets_int_String_helper(2, "abbc", "[ab, ab, bb, bc, ac, bc]");
        stringSubsets_int_String_helper(3, "abbc", "[abb, abc, abc, bbc]");
        stringSubsets_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(EP.stringSubsets(0, "Mississippi")), 1);
        stringSubsets_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringSubsets_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, is, is, Ms, Mi, ii, is, ss, is, is, ss, ss, ss, ss, is, Ms, Ms, is, ii, ...]");
        stringSubsets_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Mss, Mis, iss, iis, iis, iss, Mii, Mis, Mss, Mss, iss, iss, iss, iis, Mis, Mss, Mis," +
                " Mis, ...]");
        try {
            EP.stringSubsets(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringSubsets(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringSubsets_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsets(size), output);
    }

    @Test
    public void testStringSubsets_int() {
        stringSubsets_int_helper(0, "[]");
        aeq(length(EP.stringSubsets(0)), 1);
        stringSubsets_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        stringSubsets_int_helper(2,
                "[ab, ac, bc, bd, ad, ae, be, bf, cd, ce, de, df, cf, cg, dg, dh, af, ag, bg, bh, ...]");
        stringSubsets_int_helper(3,
                "[abc, abd, acd, ace, bcd, bce, bde, bdf, abe, abf, acf, acg, bcf, bcg, bdg, bdh, ade, adf, aef," +
                " aeg, ...]");
        try {
            EP.stringSubsets(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
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
        subsets_Iterable_helper("[]", "[[]]");
        subsets_Iterable_helper("[5]", "[[], [5]]");
        subsets_Iterable_helper("[1, 2, 3]", "[[], [1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 3]]");
        subsets_Iterable_helper("[1, 2, 2, 3]",
                "[[], [1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 2], [1, 2, 3], [2, 3]," +
                " [1, 3], [1, 2, 3], [2, 3], [2, 2, 3]]");
        subsets_Iterable_helper(EP.naturalIntegers(),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 2], [5], [0, 1, 3], [6]," +
                " [1, 3], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        subsets_Iterable_helper(repeat(1),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        subsets_Iterable_fail_helper("[null]");
        subsets_Iterable_fail_helper("[1, null, 3]");
    }

    @Test
    public void testStringSubsets_String() {
        aeqit(EP.stringSubsets(""), "[]");
        aeq(length(EP.stringSubsets("")), 1);
        simpleProviderHelper(EP.stringSubsets("a"), "[, a]");
        simpleProviderHelper(EP.stringSubsets("abc"), "[, a, ab, b, abc, c, ac, bc]");
        simpleProviderHelper(EP.stringSubsets("abbc"),
                "[, a, ab, b, abb, b, ab, c, abbc, bb, abc, bc, ac, abc, bc, bbc]");
        simpleProviderHelper(EP.stringSubsets("Mississippi"),
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, is, s, Mis, s, is, i, Miiss, p, Ms, p, ...]");
    }

    @Test
    public void testStringSubsets() {
        simpleProviderHelper(EP.stringSubsets(),
                "[, a, ab, b, abc, c, ac, d, abcd, e, bc, f, abd, g, bd, h, abcde, i, ad, j, ...]");
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
        subsetsAtLeast_helper(0, "[]", "[[]]");
        subsetsAtLeast_helper(1, "[]", "[]");
        subsetsAtLeast_helper(2, "[]", "[]");
        subsetsAtLeast_helper(3, "[]", "[]");

        subsetsAtLeast_helper(0, "[5]", "[[], [5]]");
        subsetsAtLeast_helper(1, "[5]", "[[5]]");
        subsetsAtLeast_helper(2, "[5]", "[]");
        subsetsAtLeast_helper(3, "[5]", "[]");

        subsetsAtLeast_helper(0, "[1, 2, 3]", "[[], [1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 3]]");
        subsetsAtLeast_helper(1, "[1, 2, 3]", "[[1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 3]]");
        subsetsAtLeast_helper(2, "[1, 2, 3]", "[[1, 2], [1, 2, 3], [1, 3], [2, 3]]");
        subsetsAtLeast_helper(3, "[1, 2, 3]", "[[1, 2, 3]]");

        subsetsAtLeast_helper(0, "[1, 2, 2, 3]",
                "[[], [1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 2], [1, 2, 3], [2, 3]," +
                " [1, 3], [1, 2, 3], [2, 3], [2, 2, 3]]");
        subsetsAtLeast_helper(1, "[1, 2, 2, 3]",
                "[[1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 2], [1, 2, 3], [2, 3], [1, 3]," +
                " [1, 2, 3], [2, 3], [2, 2, 3]]");
        subsetsAtLeast_helper(2, "[1, 2, 2, 3]",
                "[[1, 2], [1, 2, 2], [1, 2], [1, 2, 2, 3], [2, 2], [1, 2, 3], [2, 3], [1, 3], [1, 2, 3], [2, 3]," +
                " [2, 2, 3]]");
        subsetsAtLeast_helper(3, "[1, 2, 2, 3]", "[[1, 2, 2], [1, 2, 2, 3], [1, 2, 3], [1, 2, 3], [2, 2, 3]]");

        subsetsAtLeast_helper(0, EP.naturalIntegers(),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 2], [5], [0, 1, 3], [6]," +
                " [1, 3], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        subsetsAtLeast_helper(1, EP.naturalIntegers(),
                "[[0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 2], [5], [0, 1, 3], [6]," +
                " [1, 3], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], [0, 2, 3], ...]");
        subsetsAtLeast_helper(2, EP.naturalIntegers(),
                "[[0, 1], [0, 1, 2], [0, 2], [0, 1, 2, 3], [1, 2], [0, 1, 3], [1, 3], [0, 1, 2, 3, 4], [0, 3]," +
                " [0, 2, 3], [0, 4], [0, 1, 2, 4], [1, 4], [0, 2, 4], [1, 5], [0, 1, 2, 3, 4, 5], [2, 3], [1, 2, 3]," +
                " [2, 4], [0, 1, 3, 4], ...]");
        subsetsAtLeast_helper(3, EP.naturalIntegers(),
                "[[0, 1, 2], [0, 1, 2, 3], [0, 1, 3], [0, 1, 2, 3, 4], [0, 2, 3], [0, 1, 2, 4], [0, 2, 4]," +
                " [0, 1, 2, 3, 4, 5], [1, 2, 3], [0, 1, 3, 4], [1, 2, 4], [0, 1, 2, 3, 5], [1, 3, 4], [0, 1, 3, 5]," +
                " [1, 3, 5], [0, 1, 2, 3, 4, 5, 6], [0, 1, 4], [0, 2, 3, 4], [0, 1, 5], [0, 1, 2, 4, 5], ...]");

        subsetsAtLeast_helper(0, repeat(1),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        subsetsAtLeast_helper(1, repeat(1),
                "[[1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], ...]");
        subsetsAtLeast_helper(2, repeat(1),
                "[[1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1], [1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1], [1, 1], [1, 1, 1]," +
                " [1, 1], [1, 1, 1, 1], ...]");
        subsetsAtLeast_helper(3, repeat(1),
                "[[1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1], ...]");

        subsetsAtLeast_fail_helper(-1, "[]");
        subsetsAtLeast_fail_helper(-1, "[1, 2, 3]");
        subsetsAtLeast_fail_helper(1, "[null]");
        subsetsAtLeast_fail_helper(1, "[1, null, 3]");
    }

    private static void stringSubsetsAtLeast_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.stringSubsetsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsAtLeast_String() {
        stringSubsetsAtLeast_String_helper(0, "", "[]");
        aeq(length(EP.stringSubsetsAtLeast(0, "")), 1);
        stringSubsetsAtLeast_String_helper(1, "", "[]");
        aeq(length(EP.stringSubsetsAtLeast(1, "")), 0);
        stringSubsetsAtLeast_String_helper(2, "", "[]");
        aeq(length(EP.stringSubsetsAtLeast(2, "")), 0);
        stringSubsetsAtLeast_String_helper(3, "", "[]");
        aeq(length(EP.stringSubsetsAtLeast(3, "")), 0);
        stringSubsetsAtLeast_String_helper(0, "a", "[, a]");
        stringSubsetsAtLeast_String_helper(1, "a", "[a]");
        stringSubsetsAtLeast_String_helper(2, "a", "[]");
        stringSubsetsAtLeast_String_helper(3, "a", "[]");
        stringSubsetsAtLeast_String_helper(0, "abc", "[, a, ab, b, abc, c, ac, bc]");
        stringSubsetsAtLeast_String_helper(1, "abc", "[a, ab, b, abc, c, ac, bc]");
        stringSubsetsAtLeast_String_helper(2, "abc", "[ab, abc, ac, bc]");
        stringSubsetsAtLeast_String_helper(3, "abc", "[abc]");
        stringSubsetsAtLeast_String_helper(0, "abbc",
                "[, a, ab, b, abb, b, ab, c, abbc, bb, abc, bc, ac, abc, bc, bbc]");
        stringSubsetsAtLeast_String_helper(1, "abbc",
                "[a, ab, b, abb, b, ab, c, abbc, bb, abc, bc, ac, abc, bc, bbc]");
        stringSubsetsAtLeast_String_helper(2, "abbc", "[ab, abb, ab, abbc, bb, abc, bc, ac, abc, bc, bbc]");
        stringSubsetsAtLeast_String_helper(3, "abbc", "[abb, abbc, abc, abc, bbc]");
        stringSubsetsAtLeast_String_helper(0, "Mississippi",
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, is, s, Mis, s, is, i, Miiss, p, Ms, p, ...]");
        stringSubsetsAtLeast_String_helper(1, "Mississippi",
                "[M, Mi, i, Mis, s, Ms, s, Miss, i, is, s, Mis, s, is, i, Miiss, p, Ms, p, Mss, ...]");
        stringSubsetsAtLeast_String_helper(2, "Mississippi",
                "[Mi, Mis, Ms, Miss, is, Mis, is, Miiss, Ms, Mss, Mi, Miis, ii, Mis, is, Miisss, ss, iss, is, Miis," +
                " ...]");
        stringSubsetsAtLeast_String_helper(3, "Mississippi",
                "[Mis, Miss, Mis, Miiss, Mss, Miis, Mis, Miisss, iss, Miis, iis, Misss, iis, Miss, iss, Miissss," +
                " Mii, Miss, Mis, Miiss, ...]");
        try {
            EP.stringSubsetsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            EP.stringSubsetsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringSubsetsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsAtLeast(minSize), output);
    }

    @Test
    public void testStringSubsetsAtLeast() {
        stringSubsetsAtLeast_helper(0,
                "[, a, ab, b, abc, c, ac, d, abcd, e, bc, f, abd, g, bd, h, abcde, i, ad, j, ...]");
        stringSubsetsAtLeast_helper(1,
                "[a, ab, b, abc, c, ac, d, abcd, e, bc, f, abd, g, bd, h, abcde, i, ad, j, acd, ...]");
        stringSubsetsAtLeast_helper(2,
                "[ab, abc, ac, abcd, bc, abd, bd, abcde, ad, acd, ae, abce, be, ace, bf, abcdef, cd, bcd, ce, abde," +
                " ...]");
        stringSubsetsAtLeast_helper(3,
                "[abc, abcd, abd, abcde, acd, abce, ace, abcdef, bcd, abde, bce, abcdf, bde, abdf, bdf, abcdefg," +
                " abe, acde, abf, abcef, ...]");
        try {
            EP.stringSubsetsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static <A, B> void eithersSuccessive_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        Iterable<Either<A, B>> es = EP.eithersSuccessive(as, bs);
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testEithersSuccessive() {
        eithersSuccessive_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "[<1, >, <2, >, <3, >, <4, >, <, a>, <, b>, <, c>, <, d>]");
        eithersSuccessive_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "[<1, >, <2, >, <null, >, <4, >, <, a>, <, b>, <, c>, <, d>]");
        eithersSuccessive_helper(Arrays.asList(1, 2), fromString("abcd"),
                "[<1, >, <2, >, <, a>, <, b>, <, c>, <, d>]");
        eithersSuccessive_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "[<1, >, <2, >, <3, >, <4, >, <, a>, <, b>]");
        eithersSuccessive_helper(Collections.emptyList(), fromString("abcd"), "[<, a>, <, b>, <, c>, <, d>]");
        eithersSuccessive_helper(Collections.emptyList(), Collections.emptyList(), "[]");
        simpleProviderHelper(EP.eithersSuccessive(EP.naturalBigIntegers(), fromString("abcd")),
                "[<0, >, <1, >, <2, >, <3, >, <4, >, <5, >, <6, >, <7, >, <8, >, <9, >, <10, >, <11, >, <12, >," +
                " <13, >, <14, >, <15, >, <16, >, <17, >, <18, >, <19, >, ...]");
        simpleProviderHelper(EP.eithersSuccessive(fromString("abcd"), EP.naturalBigIntegers()),
                "[<a, >, <b, >, <c, >, <d, >, <, 0>, <, 1>, <, 2>, <, 3>, <, 4>, <, 5>, <, 6>, <, 7>, <, 8>, <, 9>," +
                " <, 10>, <, 11>, <, 12>, <, 13>, <, 14>, <, 15>, ...]");
        simpleProviderHelper(EP.eithersSuccessive(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[<1, >, <2, >, <3, >, <4, >, <5, >, <6, >, <7, >, <8, >, <9, >, <10, >, <11, >, <12, >, <13, >," +
                " <14, >, <15, >, <16, >, <17, >, <18, >, <19, >, <20, >, ...]");
    }

    private static <A, B> void eithersSquareRootOrder_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        Iterable<Either<A, B>> es = EP.eithersSquareRootOrder(as, bs);
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testEithersSquareRootOrder() {
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "[<, a>, <, b>, <1, >, <2, >, <, c>, <3, >, <4, >, <, d>]");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "[<, a>, <, b>, <1, >, <2, >, <, c>, <null, >, <4, >, <, d>]");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2), fromString("abcd"),
                "[<, a>, <, b>, <1, >, <2, >, <, c>, <, d>]");
        eithersSquareRootOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "[<, a>, <, b>, <1, >, <2, >, <3, >, <4, >]");
        eithersSquareRootOrder_helper(Collections.emptyList(), fromString("abcd"), "[<, a>, <, b>, <, c>, <, d>]");
        eithersSquareRootOrder_helper(Collections.emptyList(), Collections.emptyList(), "[]");
        simpleProviderHelper(EP.eithersSquareRootOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "[<, a>, <, b>, <0, >, <1, >, <, c>, <2, >, <3, >, <4, >, <5, >, <, d>, <6, >, <7, >, <8, >, <9, >," +
                " <10, >, <11, >, <12, >, <13, >, <14, >, <15, >, ...]");
        simpleProviderHelper(EP.eithersSquareRootOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "[<, 0>, <, 1>, <a, >, <b, >, <, 2>, <c, >, <d, >, <, 3>, <, 4>, <, 5>, <, 6>, <, 7>, <, 8>, <, 9>," +
                " <, 10>, <, 11>, <, 12>, <, 13>, <, 14>, <, 15>, ...]");
        simpleProviderHelper(EP.eithersSquareRootOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[<, -1>, <, -2>, <1, >, <2, >, <, -3>, <3, >, <4, >, <5, >, <6, >, <, -4>, <7, >, <8, >, <9, >," +
                " <10, >, <11, >, <12, >, <, -5>, <13, >, <14, >, <15, >, ...]");
    }

    private static <A, B> void eithersLogarithmicOrder_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        Iterable<Either<A, B>> es = EP.eithersLogarithmicOrder(as, bs);
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testEithersLogarithmicOrder() {
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "[<1, >, <, a>, <, b>, <2, >, <, c>, <3, >, <4, >, <, d>]");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "[<1, >, <, a>, <, b>, <2, >, <, c>, <null, >, <4, >, <, d>]");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2), fromString("abcd"),
                "[<1, >, <, a>, <, b>, <2, >, <, c>, <, d>]");
        eithersLogarithmicOrder_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"),
                "[<1, >, <, a>, <, b>, <2, >, <3, >, <4, >]");
        eithersLogarithmicOrder_helper(Collections.emptyList(), fromString("abcd"), "[<, a>, <, b>, <, c>, <, d>]");
        eithersLogarithmicOrder_helper(Collections.emptyList(), Collections.emptyList(), "[]");
        simpleProviderHelper(EP.eithersLogarithmicOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "[<0, >, <, a>, <, b>, <1, >, <, c>, <2, >, <3, >, <4, >, <, d>, <5, >, <6, >, <7, >, <8, >, <9, >," +
                " <10, >, <11, >, <12, >, <13, >, <14, >, <15, >, ...]");
        simpleProviderHelper(EP.eithersLogarithmicOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "[<a, >, <, 0>, <, 1>, <b, >, <, 2>, <c, >, <d, >, <, 3>, <, 4>, <, 5>, <, 6>, <, 7>, <, 8>, <, 9>," +
                " <, 10>, <, 11>, <, 12>, <, 13>, <, 14>, <, 15>, ...]");
        simpleProviderHelper(EP.eithersLogarithmicOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[<1, >, <, -1>, <, -2>, <2, >, <, -3>, <3, >, <4, >, <5, >, <, -4>, <6, >, <7, >, <8, >, <9, >," +
                " <10, >, <11, >, <12, >, <, -5>, <13, >, <14, >, <15, >, ...]");
    }

    private static <A, B> void eithers_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        Iterable<Either<A, B>> es = EP.eithers(as, bs);
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testEithers() {
        eithers_helper(Arrays.asList(1, 2, 3, 4), fromString("abcd"),
                "[<1, >, <, a>, <2, >, <, b>, <3, >, <, c>, <4, >, <, d>]");
        eithers_helper(Arrays.asList(1, 2, null, 4), fromString("abcd"),
                "[<1, >, <, a>, <2, >, <, b>, <null, >, <, c>, <4, >, <, d>]");
        eithers_helper(Arrays.asList(1, 2), fromString("abcd"), "[<1, >, <, a>, <2, >, <, b>, <, c>, <, d>]");
        eithers_helper(Arrays.asList(1, 2, 3, 4), fromString("ab"), "[<1, >, <, a>, <2, >, <, b>, <3, >, <4, >]");
        eithers_helper(Collections.emptyList(), fromString("abcd"), "[<, a>, <, b>, <, c>, <, d>]");
        eithers_helper(Collections.emptyList(), Collections.emptyList(), "[]");
        simpleProviderHelper(EP.eithers(EP.naturalBigIntegers(), fromString("abcd")),
                "[<0, >, <, a>, <1, >, <, b>, <2, >, <, c>, <3, >, <, d>, <4, >, <5, >, <6, >, <7, >, <8, >, <9, >," +
                " <10, >, <11, >, <12, >, <13, >, <14, >, <15, >, ...]");
        simpleProviderHelper(EP.eithers(fromString("abcd"), EP.naturalBigIntegers()),
                "[<a, >, <, 0>, <b, >, <, 1>, <c, >, <, 2>, <d, >, <, 3>, <, 4>, <, 5>, <, 6>, <, 7>, <, 8>, <, 9>," +
                " <, 10>, <, 11>, <, 12>, <, 13>, <, 14>, <, 15>, ...]");
        simpleProviderHelper(EP.eithers(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "[<1, >, <, -1>, <2, >, <, -2>, <3, >, <, -3>, <4, >, <, -4>, <5, >, <, -5>, <6, >, <, -6>, <7, >," +
                " <, -7>, <8, >, <, -8>, <9, >, <, -9>, <10, >, <, -10>, ...]");
    }

    private static void chooseSquareRootOrder_helper(@NotNull String as, @NotNull String bs, @NotNull String output) {
        Iterable<Integer> es = EP.chooseSquareRootOrder(readIntegerListWithNulls(as), readIntegerListWithNulls(bs));
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testChooseSquareRootOrder() {
        chooseSquareRootOrder_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]", "[-1, -2, 1, 2, -3, 3, 4, -4]");
        chooseSquareRootOrder_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]", "[-1, -2, 1, 2, -3, null, 4, -4]");
        chooseSquareRootOrder_helper("[1, 2]", "[-1, -2, -3, -4]", "[-1, -2, 1, 2, -3, -4]");
        chooseSquareRootOrder_helper("[1, 2, 3, 4]", "[-1, -2]", "[-1, -2, 1, 2, 3, 4]");
        chooseSquareRootOrder_helper("[]", "[1, 2, 3, 4]", "[1, 2, 3, 4]");
        chooseSquareRootOrder_helper("[]", "[]", "[]");
        simpleProviderHelper(EP.chooseSquareRootOrder(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "[-1, -2, 0, 1, -3, 2, 3, 4, 5, -4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.chooseSquareRootOrder(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "[0, 1, -1, -2, 2, -3, -4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.chooseSquareRootOrder(EP.positiveIntegers(), EP.negativeIntegers()),
                "[-1, -2, 1, 2, -3, 3, 4, 5, 6, -4, 7, 8, 9, 10, 11, 12, -5, 13, 14, 15, ...]");
    }

    private static void chooseLogarithmicOrder_helper(@NotNull String as, @NotNull String bs, @NotNull String output) {
        Iterable<Integer> es = EP.chooseLogarithmicOrder(readIntegerListWithNulls(as), readIntegerListWithNulls(bs));
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testChooseLogarithmicOrder() {
        chooseLogarithmicOrder_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]", "[1, -1, -2, 2, -3, 3, 4, -4]");
        chooseLogarithmicOrder_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]", "[1, -1, -2, 2, -3, null, 4, -4]");
        chooseLogarithmicOrder_helper("[1, 2]", "[-1, -2, -3, -4]", "[1, -1, -2, 2, -3, -4]");
        chooseLogarithmicOrder_helper("[1, 2, 3, 4]", "[-1, -2]", "[1, -1, -2, 2, 3, 4]");
        chooseLogarithmicOrder_helper("[]", "[1, 2, 3, 4]", "[1, 2, 3, 4]");
        chooseLogarithmicOrder_helper("[]", "[]", "[]");
        simpleProviderHelper(EP.chooseLogarithmicOrder(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "[0, -1, -2, 1, -3, 2, 3, 4, -4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.chooseLogarithmicOrder(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "[-1, 0, 1, -2, 2, -3, -4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.chooseLogarithmicOrder(EP.positiveIntegers(), EP.negativeIntegers()),
                "[1, -1, -2, 2, -3, 3, 4, 5, -4, 6, 7, 8, 9, 10, 11, 12, -5, 13, 14, 15, ...]");
    }

    private static void choose_helper(@NotNull String as, @NotNull String bs, @NotNull String output) {
        Iterable<Integer> es = EP.choose(readIntegerListWithNulls(as), readIntegerListWithNulls(bs));
        aeqit(es, output);
        testNoRemove(es);
    }

    @Test
    public void testChoose() {
        choose_helper("[1, 2, 3, 4]", "[-1, -2, -3, -4]", "[1, -1, 2, -2, 3, -3, 4, -4]");
        choose_helper("[1, 2, null, 4]", "[-1, -2, -3, -4]", "[1, -1, 2, -2, null, -3, 4, -4]");
        choose_helper("[1, 2]", "[-1, -2, -3, -4]", "[1, -1, 2, -2, -3, -4]");
        choose_helper("[1, 2, 3, 4]", "[-1, -2]", "[1, -1, 2, -2, 3, 4]");
        choose_helper("[]", "[1, 2, 3, 4]", "[1, 2, 3, 4]");
        choose_helper("[]", "[]", "[]");
        simpleProviderHelper(EP.choose(EP.naturalIntegers(), Arrays.asList(-1, -2, -3, -4)),
                "[0, -1, 1, -2, 2, -3, 3, -4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.choose(Arrays.asList(-1, -2, -3, -4), EP.naturalIntegers()),
                "[-1, 0, -2, 1, -3, 2, -4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, ...]");
        simpleProviderHelper(EP.choose(EP.positiveIntegers(), EP.negativeIntegers()),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    private static void cartesianProduct_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.cartesianProduct(readIntegerListWithNullsLists(input)), output);
    }

    private static void cartesianProduct_fail_helper(@NotNull String input) {
        try {
            EP.cartesianProduct(readIntegerListWithNullsListsWithNulls(input));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testCartesianProduct() {
        cartesianProduct_helper("[]", "[[]]");
        cartesianProduct_helper("[[]]", "[]");
        cartesianProduct_helper("[[], []]", "[]");
        cartesianProduct_helper("[[], [0]]", "[]");
        cartesianProduct_helper("[[0], [0]]", "[[0, 0]]");
        cartesianProduct_helper("[[0, 0, 0]]", "[[0], [0], [0]]");
        cartesianProduct_helper("[[1, 2, 3]]", "[[1], [2], [3]]");
        cartesianProduct_helper("[[null]]", "[[null]]");
        cartesianProduct_helper("[[1], [1], [1], [1]]", "[[1, 1, 1, 1]]");
        cartesianProduct_helper("[[0, 1, 2], [-3, -4], [null, 10]]",
                "[[0, -3, null], [0, -3, 10], [0, -4, null], [0, -4, 10], [1, -3, null], [1, -3, 10], [1, -4, null]," +
                " [1, -4, 10], [2, -3, null], [2, -3, 10], [2, -4, null], [2, -4, 10]]");
        cartesianProduct_helper("[[0, 1], [0, 1], [0, 1]]",
                "[[0, 0, 0], [0, 0, 1], [0, 1, 0], [0, 1, 1], [1, 0, 0], [1, 0, 1], [1, 1, 0], [1, 1, 1]]");
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
        repeatingIterables_helper("[0, 1]",
                "[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, ...]," +
                " [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, ...]," +
                " [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, ...]," +
                " [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, ...], ...]");
        repeatingIterables_helper("[1, 0]",
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, ...]," +
                " [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, ...]," +
                " [1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, ...]," +
                " [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, ...]," +
                " [1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, ...]," +
                " [1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, ...]," +
                " [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, ...], ...]");
        repeatingIterables_helper("[0, 1, 1]",
                "[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, ...]," +
                " [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, ...]," +
                " [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, ...]," +
                " [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, ...], ...]");
        repeatingIterables_helper("[4, null, -5]",
                "[[4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, ...]," +
                " [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null," +
                " null, null, null, null, ...]," +
                " [-5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, ...]," +
                " [4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, ...]," +
                " [null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, ...]," +
                " [4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, ...]," +
                " [4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, ...]," +
                " [4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, ...]," +
                " [null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null," +
                " -5, ...], [4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, ...]," +
                " [-5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, ...]," +
                " [4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4," +
                " null, ...]," +
                " [-5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5," +
                " null, ...]," +
                " [null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, ...]," +
                " [4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, ...]," +
                " [null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null," +
                " 4, ...], [4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, ...]," +
                " [null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null," +
                " null, ...]," +
                " [4, 4, null, null, 4, 4, null, null, 4, 4, null, null, 4, 4, null, null, 4, 4, null, null, ...]," +
                " [4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, ...], ...]");
        repeatingIterables_helper(
                EP.naturalIntegers(),
                "[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, ...]," +
                " [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, ...]," +
                " [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, ...]," +
                " [7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, ...]," +
                " [8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, ...]," +
                " [0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, ...]," +
                " [9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]," +
                " [0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, ...]," +
                " [11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, ...], ...]");
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
                "[[0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, ...]," +
                " [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, ...]," +
                " [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, ...]," +
                " [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, ...]," +
                " [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(2, "[1, 0]",
                "[[1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, ...]," +
                " [1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, ...]," +
                " [1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, ...]," +
                " [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, ...]," +
                " [1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, ...]," +
                " [1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, ...]," +
                " [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, ...]," +
                " [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, ...]," +
                " [1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(2, "[0, 1, 1]",
                "[[0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, ...]," +
                " [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, ...]," +
                " [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, ...]," +
                " [0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, ...]," +
                " [0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, ...]," +
                " [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, ...]," +
                " [1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, ...]," +
                " [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(2, "[4, null, -5]",
                "[[4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, ...]," +
                " [null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, null, 4, ...]," +
                " [4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, ...]," +
                " [4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, ...]," +
                " [4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, ...]," +
                " [null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null," +
                " -5, ...], [4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, ...]," +
                " [-5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, -5, 4, ...]," +
                " [4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4," +
                " null, ...]," +
                " [-5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5, null, -5," +
                " null, ...]," +
                " [null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, 4, null, 4, ...]," +
                " [4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, ...]," +
                " [null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null," +
                " 4, ...], [4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, ...]," +
                " [null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null, null, 4, null," +
                " null, ...]," +
                " [4, 4, null, null, 4, 4, null, null, 4, 4, null, null, 4, 4, null, null, 4, 4, null, null, ...]," +
                " [4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, -5, 4, 4, ...]," +
                " [4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, 4, null, 4, 4, ...]," +
                " [4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, ...]," +
                " [4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, 4, 4, 4, null, 4, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(3, "[4, null, -5]",
                "[[4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, ...]," +
                " [null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, ...]," +
                " [4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, ...]," +
                " [null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, ...]," +
                " [-5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, null, -5, 4, ...]," +
                " [-5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, 4, -5, null, ...]," +
                " [4, 4, null, -5, 4, 4, null, -5, 4, 4, null, -5, 4, 4, null, -5, 4, 4, null, -5, ...]," +
                " [4, null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, ...]," +
                " [4, null, null, -5, 4, null, null, -5, 4, null, null, -5, 4, null, null, -5, 4, null, null, -5," +
                " ...], [null, 4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, ...]," +
                " [null, 4, null, -5, null, 4, null, -5, null, 4, null, -5, null, 4, null, -5, null, 4, null, -5," +
                " ...]," +
                " [null, null, 4, -5, null, null, 4, -5, null, null, 4, -5, null, null, 4, -5, null, null, 4, -5," +
                " ...], [4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, null, 4, 4, -5, null, ...]," +
                " [4, null, -5, 4, 4, null, -5, 4, 4, null, -5, 4, 4, null, -5, 4, 4, null, -5, 4, ...]," +
                " [4, null, -5, null, 4, null, -5, null, 4, null, -5, null, 4, null, -5, null, 4, null, -5, null," +
                " ...], [null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, 4, null, 4, -5, 4, ...]," +
                " [null, 4, -5, null, null, 4, -5, null, null, 4, -5, null, null, 4, -5, null, null, 4, -5, null," +
                " ...]," +
                " [null, null, -5, 4, null, null, -5, 4, null, null, -5, 4, null, null, -5, 4, null, null, -5, 4," +
                " ...], [4, null, -5, -5, 4, null, -5, -5, 4, null, -5, -5, 4, null, -5, -5, 4, null, -5, -5, ...]," +
                " [null, 4, -5, -5, null, 4, -5, -5, null, 4, -5, -5, null, 4, -5, -5, null, 4, -5, -5, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(2, EP.naturalIntegers(),
                "[[0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, ...]," +
                " [1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, ...]," +
                " [0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, ...]," +
                " [0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, ...]," +
                " [0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, ...]," +
                " [0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, ...]," +
                " [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, ...]," +
                " [1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, ...]," +
                " [0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, ...]," +
                " [1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, ...]," +
                " [2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, ...]," +
                " [1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, ...]," +
                " [2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, ...]," +
                " [0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, ...]," +
                " [3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, ...]," +
                " [1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, ...]," +
                " [3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, ...]," +
                " [0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, ...]," +
                " [1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, ...]," +
                " [2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, ...], ...]");
        repeatingIterablesDistinctAtLeast_helper(5, EP.naturalIntegers(),
                "[[0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, ...]," +
                " [0, 1, 2, 3, 5, 0, 1, 2, 3, 5, 0, 1, 2, 3, 5, 0, 1, 2, 3, 5, ...]," +
                " [0, 1, 3, 2, 4, 0, 1, 3, 2, 4, 0, 1, 3, 2, 4, 0, 1, 3, 2, 4, ...]," +
                " [0, 1, 3, 2, 5, 0, 1, 3, 2, 5, 0, 1, 3, 2, 5, 0, 1, 3, 2, 5, ...]," +
                " [1, 0, 2, 3, 4, 1, 0, 2, 3, 4, 1, 0, 2, 3, 4, 1, 0, 2, 3, 4, ...]," +
                " [1, 0, 2, 3, 5, 1, 0, 2, 3, 5, 1, 0, 2, 3, 5, 1, 0, 2, 3, 5, ...]," +
                " [1, 0, 3, 2, 4, 1, 0, 3, 2, 4, 1, 0, 3, 2, 4, 1, 0, 3, 2, 4, ...]," +
                " [1, 0, 3, 2, 5, 1, 0, 3, 2, 5, 1, 0, 3, 2, 5, 1, 0, 3, 2, 5, ...]," +
                " [0, 1, 2, 3, 6, 0, 1, 2, 3, 6, 0, 1, 2, 3, 6, 0, 1, 2, 3, 6, ...]," +
                " [0, 1, 2, 3, 7, 0, 1, 2, 3, 7, 0, 1, 2, 3, 7, 0, 1, 2, 3, 7, ...]," +
                " [0, 1, 3, 2, 6, 0, 1, 3, 2, 6, 0, 1, 3, 2, 6, 0, 1, 3, 2, 6, ...]," +
                " [0, 1, 3, 2, 7, 0, 1, 3, 2, 7, 0, 1, 3, 2, 7, 0, 1, 3, 2, 7, ...]," +
                " [1, 0, 2, 3, 6, 1, 0, 2, 3, 6, 1, 0, 2, 3, 6, 1, 0, 2, 3, 6, ...]," +
                " [1, 0, 2, 3, 7, 1, 0, 2, 3, 7, 1, 0, 2, 3, 7, 1, 0, 2, 3, 7, ...]," +
                " [1, 0, 3, 2, 6, 1, 0, 3, 2, 6, 1, 0, 3, 2, 6, 1, 0, 3, 2, 6, ...]," +
                " [1, 0, 3, 2, 7, 1, 0, 3, 2, 7, 1, 0, 3, 2, 7, 1, 0, 3, 2, 7, ...]," +
                " [0, 2, 1, 3, 4, 0, 2, 1, 3, 4, 0, 2, 1, 3, 4, 0, 2, 1, 3, 4, ...]," +
                " [0, 2, 1, 3, 5, 0, 2, 1, 3, 5, 0, 2, 1, 3, 5, 0, 2, 1, 3, 5, ...]," +
                " [0, 3, 1, 2, 4, 0, 3, 1, 2, 4, 0, 3, 1, 2, 4, 0, 3, 1, 2, 4, ...]," +
                " [0, 3, 1, 2, 5, 0, 3, 1, 2, 5, 0, 3, 1, 2, 5, 0, 3, 1, 2, 5, ...], ...]");
        repeatingIterablesDistinctAtLeast_fail_helper(2, "[]");
        repeatingIterablesDistinctAtLeast_fail_helper(2, "[0]");
        repeatingIterablesDistinctAtLeast_fail_helper(-1, "[0, 1]");
    }

    private static void sublists_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.sublists(readIntegerListWithNulls(input)), output);
    }

    @Test
    public void testSublists() {
        sublists_helper("[]", "[[]]");
        sublists_helper("[1, 2, 3, 4]",
                "[[], [1], [1, 2], [1, 2, 3], [2], [2, 3], [3], [1, 2, 3, 4], [2, 3, 4], [3, 4], [4]]");
        sublists_helper("[1, null, 3, 4]",
                "[[], [1], [1, null], [1, null, 3], [null], [null, 3], [3], [1, null, 3, 4], [null, 3, 4], [3, 4]," +
                " [4]]");
        sublists_helper("[3, 1, 4, 1]",
                "[[], [3], [3, 1], [3, 1, 4], [1], [1, 4], [4], [3, 1, 4, 1], [1, 4, 1], [4, 1]]");
        sublists_helper("[1, 1, 1, 1]", "[[], [1], [1, 1], [1, 1, 1], [1, 1, 1, 1]]");
    }

    private static void substrings_helper(@NotNull String input, @NotNull String output) {
        aeqit(EP.substrings(input), output);
    }

    @Test
    public void testSubstrings() {
        substrings_helper("", "[]");
        substrings_helper("abcd", "[, a, ab, abc, b, bc, c, abcd, bcd, cd, d]");
        substrings_helper("aaaa", "[, a, aa, aaa, aaaa]");
        substrings_helper("Mississippi",
                "[, M, Mi, Mis, i, is, s, Miss, Missi, iss, issi, Missis, Mississ, issis, ississ, ss, ssi, si, ssis," +
                " ssiss, sis, siss, Mississi, Mississip, ississi, ississip, Mississipp, Mississippi, ississipp," +
                " ississippi, ssissi, ssissip, sissi, sissip, ssissipp, ssissippi, sissipp, sissippi, issip, ssip," +
                " issipp, issippi, ssipp, ssippi, sip, ip, sipp, sippi, ipp, ippi, p, pp, ppi, pi]");
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
        listsWithElement_helper(0, "[]", "[[0]]");
        listsWithElement_helper(0, "[1, 2, 3]",
                "[[0], [0, 1], [1, 0], [1, 0, 1], [0, 1, 1], [0, 2], [1, 0, 1, 1], [1, 0, 2], [1, 1, 0]," +
                " [1, 1, 0, 1], [2, 0], [2, 0, 1], [1, 1, 0, 1, 1], [1, 1, 0, 2], [2, 0, 1, 1], [2, 0, 2]," +
                " [0, 1, 1, 1], [0, 3], [1, 0, 1, 1, 1], [1, 0, 3], ...]");
        listsWithElement_helper(null, "[1, 2, 3]",
                "[[null], [null, 1], [1, null], [1, null, 1], [null, 1, 1], [null, 2], [1, null, 1, 1]," +
                " [1, null, 2], [1, 1, null], [1, 1, null, 1], [2, null], [2, null, 1], [1, 1, null, 1, 1]," +
                " [1, 1, null, 2], [2, null, 1, 1], [2, null, 2], [null, 1, 1, 1], [null, 3], [1, null, 1, 1, 1]," +
                " [1, null, 3], ...]");
        listsWithElement_helper(0, "[0, 1, 2]",
                "[[0], [0, 0], [1, 0], [1, 0, 0], [0, 0, 0], [0, 1], [1, 0, 0, 0], [1, 0, 1], [1, 1, 0]," +
                " [1, 1, 0, 0], [2, 0], [2, 0, 0], [1, 1, 0, 0, 0], [1, 1, 0, 1], [2, 0, 0, 0], [2, 0, 1]," +
                " [0, 0, 0, 0], [0, 2], [1, 0, 0, 0, 0], [1, 0, 2], ...]");
        listsWithElement_helper(null, EP.positiveIntegers(),
                "[[null], [null, 1], [1, null], [1, null, 1], [null, 1, 1], [null, 2], [1, null, 1, 1]," +
                " [1, null, 2], [1, 1, null], [1, 1, null, 1], [2, null], [2, null, 1], [1, 1, null, 1, 1]," +
                " [1, 1, null, 2], [2, null, 1, 1], [2, null, 2], [null, 1, 1, 1], [null, 3], [1, null, 1, 1, 1]," +
                " [1, null, 3], ...]");
        listsWithElement_helper(3, EP.positiveIntegers(),
                "[[3], [3, 1], [1, 3], [1, 3, 1], [3, 1, 1], [3, 2], [1, 3, 1, 1], [1, 3, 2], [1, 1, 3]," +
                " [1, 1, 3, 1], [2, 3], [2, 3, 1], [1, 1, 3, 1, 1], [1, 1, 3, 2], [2, 3, 1, 1], [2, 3, 2]," +
                " [3, 1, 1, 1], [3, 3], [1, 3, 1, 1, 1], [1, 3, 3], ...]");
        listsWithElement_helper(0, repeat(1),
                "[[0], [0, 1], [1, 0], [1, 0, 1], [0, 1, 1], [0, 1], [1, 0, 1, 1], [1, 0, 1], [1, 1, 0]," +
                " [1, 1, 0, 1], [1, 0], [1, 0, 1], [1, 1, 0, 1, 1], [1, 1, 0, 1], [1, 0, 1, 1], [1, 0, 1]," +
                " [0, 1, 1, 1], [0, 1], [1, 0, 1, 1, 1], [1, 0, 1], ...]");
    }

    private static void stringsWithChar_char_String_helper(char c, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsWithChar(c, input), output);
    }

    @Test
    public void testStringsWithChar_char_String() {
        stringsWithChar_char_String_helper('a', "", "[a]");
        stringsWithChar_char_String_helper('#', "abcd",
                "[#, #a, a#, a#a, #aa, #b, a#aa, a#b, aa#, aa#a, b#, b#a, aa#aa, aa#b, b#aa, b#b, #aaa, #c, a#aaa," +
                " a#c, ...]");
        stringsWithChar_char_String_helper('a', "abcd",
                "[a, aa, ba, baa, aaa, ab, baaa, bab, bba, bbaa, ca, caa, bbaaa, bbab, caaa, cab, aaaa, ac, baaaa," +
                " bac, ...]");
        stringsWithChar_char_String_helper('#', "aaaa",
                "[#, #a, a#, a#a, #aa, #a, a#aa, a#a, aa#, aa#a, a#, a#a, aa#aa, aa#a, a#aa, a#a, #aaa, #a, a#aaa," +
                " a#a, ...]");
        stringsWithChar_char_String_helper('#', "Mississippi",
                "[#, #M, M#, M#M, #MM, #i, M#MM, M#i, MM#, MM#M, i#, i#M, MM#MM, MM#i, i#MM, i#i, #MMM, #s, M#MMM," +
                " M#s, ...]");
    }

    private static void stringsWithChar_char_helper(char c, @NotNull String output) {
        simpleProviderHelper(EP.stringsWithChar(c), output);
    }

    @Test
    public void testStringsWithChar_char() {
        stringsWithChar_char_helper('a',
                "[a, aa, ba, baa, aaa, ab, baaa, bab, bba, bbaa, ca, caa, bbaaa, bbab, caaa, cab, aaaa, ac, baaaa," +
                " bac, ...]");
        stringsWithChar_char_helper('#',
                "[#, #a, a#, a#a, #aa, #b, a#aa, a#b, aa#, aa#a, b#, b#a, aa#aa, aa#b, b#aa, b#b, #aaa, #c, a#aaa," +
                " a#c, ...]");
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
        subsetsWithElement_helper(0, "[]", "[[0]]");
        subsetsWithElement_helper(0, "[1, 2, 3]",
                "[[0], [0, 1], [0, 1, 2], [0, 2], [0, 1, 2, 3], [0, 3], [0, 1, 3], [0, 2, 3]]");
        subsetsWithElement_helper(1, "[1, 2, 3]", "[[1], [1, 2], [1, 2, 3], [1, 3]]");
        subsetsWithElement_helper(2, "[1, 2, 3]", "[[2], [1, 2], [1, 2, 3], [2, 3]]");
        subsetsWithElement_helper(3, "[1, 2, 3]", "[[3], [1, 3], [1, 2, 3], [2, 3]]");
        subsetsWithElement_helper(3, EP.positiveIntegers(),
                "[[3], [1, 3], [1, 2, 3], [2, 3], [1, 2, 3, 4], [3, 4], [1, 3, 4], [3, 5], [1, 2, 3, 4, 5], [3, 6]," +
                " [2, 3, 4], [3, 7], [1, 2, 3, 5], [3, 8], [2, 3, 5], [3, 9], [1, 2, 3, 4, 5, 6], [3, 10]," +
                " [1, 3, 5], [3, 11], ...]");
        subsetsWithElement_helper(0, repeat(1),
                "[[0], [0, 1], [0, 1, 1], [0, 1], [0, 1, 1, 1], [0, 1], [0, 1, 1], [0, 1], [0, 1, 1, 1, 1], [0, 1]," +
                " [0, 1, 1], [0, 1], [0, 1, 1, 1], [0, 1], [0, 1, 1], [0, 1], [0, 1, 1, 1, 1, 1], [0, 1], [0, 1, 1]," +
                " [0, 1], ...]");
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
        stringSubsetsWithChar_char_String_helper('a', "", "[a]");
        stringSubsetsWithChar_char_String_helper('#', "abcd",
                "[#, #a, #ab, #b, #abc, #c, #ac, #d, #abcd, #bc, #abd, #bd, #ad, #acd, #cd, #bcd]");
        stringSubsetsWithChar_char_String_helper('a', "aabcd", "[a, ab, abc, ac, abcd, ad, abd, acd]");
        stringSubsetsWithChar_char_String_helper('a', "abcd", "[a, ab, abc, ac, abcd, ad, abd, acd]");
        stringSubsetsWithChar_char_String_helper('a', "aabcd", "[a, ab, abc, ac, abcd, ad, abd, acd]");
        stringSubsetsWithChar_char_String_helper('#', "aaaa",
                "[#, #a, #aa, #a, #aaa, #a, #aa, #a, #aaaa, #aa, #aaa, #aa, #aa, #aaa, #aa, #aaa]");
        stringSubsetsWithChar_char_String_helper('#', "Mississippi",
                "[#, #M, #Mi, #i, #Mis, #s, #Ms, #s, #Miss, #i, #is, #s, #Mis, #s, #is, #i, #Miiss, #p, #Ms, #p," +
                " ...]");
    }

    private static void stringSubsetsWithChar_char_helper(char c, @NotNull String output) {
        simpleProviderHelper(EP.stringSubsetsWithChar(c), output);
    }

    @Test
    public void testStringSubsetsWithChar_char() {
        stringSubsetsWithChar_char_helper('a',
                "[a, ab, abc, ac, abcd, ad, abd, ae, abcde, af, acd, ag, abce, ah, ace, ai, abcdef, aj, abe, ak," +
                " ...]");
        stringSubsetsWithChar_char_helper('#',
                "[#, #a, #ab, #b, #abc, #c, #ac, #d, #abcd, #e, #bc, #f, #abd, #g, #bd, #h, #abcde, #i, #ad, #j," +
                " ...]");
    }

    @Test
    public void testListsWithSublists() {
        simpleProviderHelper(EP.listsWithSublists(Collections.emptyList(), Collections.singletonList(0)), "[]");
        simpleProviderHelper(EP.listsWithSublists(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                EP.listsWithSublists(Collections.singletonList(Collections.emptyList()), Collections.emptyList()),
                "[[]]"
        );
        simpleProviderHelper(
                EP.listsWithSublists(Collections.singletonList(Collections.emptyList()), Collections.singletonList(0)),
                "[[], [0], [0, 0], [0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]," +
                " [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], ...]"
        );
        simpleProviderHelper(
                EP.listsWithSublists(Collections.singletonList(Arrays.asList(1, 0, 1)), Collections.singletonList(0)),
                "[[1, 0, 1], [1, 0, 1, 0], [0, 1, 0, 1], [0, 1, 0, 1, 0], [1, 0, 1, 0, 0], [1, 0, 1, 0, 0, 0]," +
                " [0, 1, 0, 1, 0, 0], [0, 1, 0, 1, 0, 0, 0], [0, 0, 1, 0, 1], [0, 0, 1, 0, 1, 0]," +
                " [0, 0, 0, 1, 0, 1], [0, 0, 0, 1, 0, 1, 0], [0, 0, 1, 0, 1, 0, 0], [0, 0, 1, 0, 1, 0, 0, 0]," +
                " [0, 0, 0, 1, 0, 1, 0, 0], [0, 0, 0, 1, 0, 1, 0, 0, 0], [1, 0, 1, 0, 0, 0, 0]," +
                " [1, 0, 1, 0, 0, 0, 0, 0], [0, 1, 0, 1, 0, 0, 0, 0], [0, 1, 0, 1, 0, 0, 0, 0, 0], ...]"
        );
        simpleProviderHelper(
                EP.listsWithSublists(Collections.singletonList(Arrays.asList(1, 0, 1)), EP.positiveIntegers()),
                "[[1, 0, 1], [1, 0, 1, 1], [1, 1, 0, 1], [1, 1, 0, 1, 1], [1, 0, 1, 1, 1], [1, 0, 1, 2]," +
                " [1, 1, 0, 1, 1, 1], [1, 1, 0, 1, 2], [1, 1, 1, 0, 1], [1, 1, 1, 0, 1, 1], [2, 1, 0, 1]," +
                " [2, 1, 0, 1, 1], [1, 1, 1, 0, 1, 1, 1], [1, 1, 1, 0, 1, 2], [2, 1, 0, 1, 1, 1], [2, 1, 0, 1, 2]," +
                " [1, 0, 1, 1, 1, 1], [1, 0, 1, 3], [1, 1, 0, 1, 1, 1, 1], [1, 1, 0, 1, 3], ...]"
        );
        simpleProviderHelper(
                EP.listsWithSublists(
                        map(i -> Arrays.asList(i, i), EP.positiveIntegers()),
                        Collections.singletonList(0)
                ),
                "[[1, 1], [1, 1, 0], [2, 2], [2, 2, 0], [0, 1, 1], [0, 1, 1, 0], [0, 2, 2], [0, 2, 2, 0]," +
                " [1, 1, 0, 0], [1, 1, 0, 0, 0], [2, 2, 0, 0], [2, 2, 0, 0, 0], [0, 1, 1, 0, 0], [0, 1, 1, 0, 0, 0]," +
                " [0, 2, 2, 0, 0], [0, 2, 2, 0, 0, 0], [3, 3], [3, 3, 0], [4, 4], [4, 4, 0], ...]"
        );
        simpleProviderHelper(
                EP.listsWithSublists(map(i -> Arrays.asList(i, i), EP.positiveIntegers()), EP.positiveIntegers()),
                "[[1, 1], [1, 1, 1], [2, 2], [2, 2, 1], [1, 1, 1, 1], [1, 2, 2], [1, 2, 2, 1], [1, 1, 2]," +
                " [2, 2, 1, 1], [2, 2, 2], [1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 2, 2, 1, 1], [1, 2, 2, 2], [3, 3]," +
                " [3, 3, 1], [4, 4], [4, 4, 1], [1, 3, 3], [1, 3, 3, 1], ...]"
        );
        try {
            toList(EP.listsWithSublists(Collections.singletonList(null), Collections.singletonList(0)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String_String() {
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.emptyList(), ""), "[]");
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.emptyList(), charsToString(range('a', 'z'))), "[]");
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.singletonList(""), ""), "[]");
        aeq(length(EP.stringsWithSubstrings(Collections.singletonList(""), "")), 1);
        simpleProviderHelper(
                EP.stringsWithSubstrings(Collections.singletonList(""), charsToString(range('a', 'z'))),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]"
        );
        simpleProviderHelper(
                EP.stringsWithSubstrings(Collections.singletonList("cat"), charsToString(range('a', 'z'))),
                "[cat, cata, acat, acata, cataa, catb, acataa, acatb, aacat, aacata, bcat, bcata, aacataa, aacatb," +
                " bcataa, bcatb, cataaa, catc, acataaa, acatc, ...]"
        );
        simpleProviderHelper(
                EP.stringsWithSubstrings(
                        map(d -> Double.toString(d), EP.positiveDoubles()),
                        charsToString(range('a', 'z'))
                ),
                "[Infinity, Infinitya, 1.0, 1.0a, aInfinity, aInfinitya, a1.0, a1.0a, Infinityaa, Infinityb, 1.0aa," +
                " 1.0b, aInfinityaa, aInfinityb, a1.0aa, a1.0b, 2.0, 2.0a, 3.0, 3.0a, ...]"
        );
        try {
            toList(EP.stringsWithSubstrings(Collections.singletonList(null), charsToString(range('a', 'z'))));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String() {
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.emptyList()), "[]");
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.singletonList("")),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]");
        simpleProviderHelper(EP.stringsWithSubstrings(Collections.singletonList("cat")),
                "[cat, cata, acat, acata, cataa, catb, acataa, acatb, aacat, aacata, bcat, bcata, aacataa, aacatb," +
                " bcataa, bcatb, cataaa, catc, acataaa, acatc, ...]");
        simpleProviderHelper(EP.stringsWithSubstrings(map(d -> Double.toString(d), EP.positiveDoubles())),
                "[Infinity, Infinitya, 1.0, 1.0a, aInfinity, aInfinitya, a1.0, a1.0a, Infinityaa, Infinityb, 1.0aa," +
                " 1.0b, aInfinityaa, aInfinityb, a1.0aa, a1.0b, 2.0, 2.0a, 3.0, 3.0a, ...]");
        try {
            toList(EP.stringsWithSubstrings(Collections.singletonList(null)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    private static void maps_helper(@NotNull String keys, @NotNull Iterable<Integer> values, @NotNull String output) {
        simpleProviderHelper(EP.maps(readIntegerListWithNulls(keys), values), output);
    }

    private static void maps_helper(@NotNull String keys, @NotNull String values, @NotNull String output) {
        maps_helper(keys, readIntegerListWithNulls(values), output);
    }

    @Test
    public void testMaps() {
        maps_helper("[]", "[]", "[{}]");
        maps_helper("[]", "[4]", "[{}]");
        maps_helper("[]", "[1, 2, 3]", "[{}]");
        maps_helper("[]", "[1, null, 3]", "[{}]");
        maps_helper("[]", EP.positiveIntegers(), "[{}]");
        maps_helper("[4]", "[]", "[]");
        maps_helper("[4]", "[4]", "[{4=4}]");
        maps_helper("[4]", "[1, 2, 3]", "[{4=1}, {4=2}, {4=3}]");
        maps_helper("[4]", "[1, null, 3]", "[{4=1}, {4=null}, {4=3}]");
        maps_helper("[4]", EP.positiveIntegers(),
                "[{4=1}, {4=2}, {4=3}, {4=4}, {4=5}, {4=6}, {4=7}, {4=8}, {4=9}, {4=10}, {4=11}, {4=12}, {4=13}," +
                " {4=14}, {4=15}, {4=16}, {4=17}, {4=18}, {4=19}, {4=20}, ...]");
        maps_helper("[1, 2, 3]", "[]", "[]");
        maps_helper("[1, 2, 3]", "[4]", "[{1=4, 2=4, 3=4}]");
        maps_helper("[1, 2, 3]", "[1, 2, 3]",
                "[{1=1, 2=1, 3=1}, {1=1, 2=1, 3=2}, {1=1, 2=2, 3=1}, {1=1, 2=2, 3=2}, {1=2, 2=1, 3=1}," +
                " {1=2, 2=1, 3=2}, {1=2, 2=2, 3=1}, {1=2, 2=2, 3=2}, {1=1, 2=1, 3=3}, {1=1, 2=2, 3=3}," +
                " {1=2, 2=1, 3=3}, {1=2, 2=2, 3=3}, {1=1, 2=3, 3=1}, {1=1, 2=3, 3=2}, {1=2, 2=3, 3=1}," +
                " {1=2, 2=3, 3=2}, {1=1, 2=3, 3=3}, {1=2, 2=3, 3=3}, {1=3, 2=1, 3=1}, {1=3, 2=1, 3=2}, ...]");
        maps_helper("[1, 2, 3]", "[1, null, 3]",
                "[{1=1, 2=1, 3=1}, {1=1, 2=1, 3=null}, {1=1, 2=null, 3=1}, {1=1, 2=null, 3=null}," +
                " {1=null, 2=1, 3=1}, {1=null, 2=1, 3=null}, {1=null, 2=null, 3=1}, {1=null, 2=null, 3=null}," +
                " {1=1, 2=1, 3=3}, {1=1, 2=null, 3=3}, {1=null, 2=1, 3=3}, {1=null, 2=null, 3=3}, {1=1, 2=3, 3=1}," +
                " {1=1, 2=3, 3=null}, {1=null, 2=3, 3=1}, {1=null, 2=3, 3=null}, {1=1, 2=3, 3=3}," +
                " {1=null, 2=3, 3=3}, {1=3, 2=1, 3=1}, {1=3, 2=1, 3=null}, ...]");
        maps_helper("[1, 2, 3]", EP.positiveIntegers(),
                "[{1=1, 2=1, 3=1}, {1=1, 2=1, 3=2}, {1=1, 2=2, 3=1}, {1=1, 2=2, 3=2}, {1=2, 2=1, 3=1}," +
                " {1=2, 2=1, 3=2}, {1=2, 2=2, 3=1}, {1=2, 2=2, 3=2}, {1=1, 2=1, 3=3}, {1=1, 2=1, 3=4}," +
                " {1=1, 2=2, 3=3}, {1=1, 2=2, 3=4}, {1=2, 2=1, 3=3}, {1=2, 2=1, 3=4}, {1=2, 2=2, 3=3}," +
                " {1=2, 2=2, 3=4}, {1=1, 2=3, 3=1}, {1=1, 2=3, 3=2}, {1=1, 2=4, 3=1}, {1=1, 2=4, 3=2}, ...]");
        maps_helper("[1, null, 3]", "[]", "[]");
        maps_helper("[1, null, 3]", "[4]", "[{null=4, 1=4, 3=4}]");
        maps_helper("[1, null, 3]", "[1, 2, 3]",
                "[{null=1, 1=1, 3=1}, {null=1, 1=1, 3=2}, {null=2, 1=1, 3=1}, {null=2, 1=1, 3=2}," +
                " {null=1, 1=2, 3=1}, {null=1, 1=2, 3=2}, {null=2, 1=2, 3=1}, {null=2, 1=2, 3=2}," +
                " {null=1, 1=1, 3=3}, {null=2, 1=1, 3=3}, {null=1, 1=2, 3=3}, {null=2, 1=2, 3=3}," +
                " {null=3, 1=1, 3=1}, {null=3, 1=1, 3=2}, {null=3, 1=2, 3=1}, {null=3, 1=2, 3=2}," +
                " {null=3, 1=1, 3=3}, {null=3, 1=2, 3=3}, {null=1, 1=3, 3=1}, {null=1, 1=3, 3=2}, ...]");
        maps_helper("[1, null, 3]", "[1, null, 3]",
                "[{null=1, 1=1, 3=1}, {null=1, 1=1, 3=null}, {null=null, 1=1, 3=1}, {null=null, 1=1, 3=null}," +
                " {null=1, 1=null, 3=1}, {null=1, 1=null, 3=null}, {null=null, 1=null, 3=1}," +
                " {null=null, 1=null, 3=null}, {null=1, 1=1, 3=3}, {null=null, 1=1, 3=3}, {null=1, 1=null, 3=3}," +
                " {null=null, 1=null, 3=3}, {null=3, 1=1, 3=1}, {null=3, 1=1, 3=null}, {null=3, 1=null, 3=1}," +
                " {null=3, 1=null, 3=null}, {null=3, 1=1, 3=3}, {null=3, 1=null, 3=3}, {null=1, 1=3, 3=1}," +
                " {null=1, 1=3, 3=null}, ...]");
        maps_helper("[1, null, 3]", EP.positiveIntegers(),
                "[{null=1, 1=1, 3=1}, {null=1, 1=1, 3=2}, {null=2, 1=1, 3=1}, {null=2, 1=1, 3=2}," +
                " {null=1, 1=2, 3=1}, {null=1, 1=2, 3=2}, {null=2, 1=2, 3=1}, {null=2, 1=2, 3=2}," +
                " {null=1, 1=1, 3=3}, {null=1, 1=1, 3=4}, {null=2, 1=1, 3=3}, {null=2, 1=1, 3=4}," +
                " {null=1, 1=2, 3=3}, {null=1, 1=2, 3=4}, {null=2, 1=2, 3=3}, {null=2, 1=2, 3=4}," +
                " {null=3, 1=1, 3=1}, {null=3, 1=1, 3=2}, {null=4, 1=1, 3=1}, {null=4, 1=1, 3=2}, ...]");
    }

    private static void randomProvidersFixedScales_helper(
            int scale,
            int secondaryScale,
            int tertiaryScale,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.randomProvidersFixedScales(scale, secondaryScale, tertiaryScale), output);
    }

    @Test
    public void testRandomProvidersFixedScales() {
        randomProvidersFixedScales_helper(8, 32, 2,
                "[RandomProvider[@-7948823947390831374, 8, 32, 2], RandomProvider[@7302477663894715351, 8, 32, 2]," +
                " RandomProvider[@5113382706114603938, 8, 32, 2], RandomProvider[@-1774083719213728003, 8, 32, 2]," +
                " RandomProvider[@8538952961045368838, 8, 32, 2], RandomProvider[@7023783968100629274, 8, 32, 2]," +
                " RandomProvider[@8397262361995169820, 8, 32, 2], RandomProvider[@7421997658690496630, 8, 32, 2]," +
                " RandomProvider[@-7898255109170938473, 8, 32, 2], RandomProvider[@3868661580815414054, 8, 32, 2]," +
                " RandomProvider[@453733778809658833, 8, 32, 2], RandomProvider[@-2636879068627078241, 8, 32, 2]," +
                " RandomProvider[@6142487595818599495, 8, 32, 2], RandomProvider[@4681726659604324256, 8, 32, 2]," +
                " RandomProvider[@1906427249022382973, 8, 32, 2], RandomProvider[@8356990522398738440, 8, 32, 2]," +
                " RandomProvider[@1947303986473887049, 8, 32, 2], RandomProvider[@-7907745475302030450, 8, 32, 2]," +
                " RandomProvider[@-9092377260523661242, 8, 32, 2], RandomProvider[@-1031230974728655171, 8, 32, 2]," +
                " ...]");
        randomProvidersFixedScales_helper(0, 0, 0,
                "[RandomProvider[@-7948823947390831374, 0, 0, 0], RandomProvider[@7302477663894715351, 0, 0, 0]," +
                " RandomProvider[@5113382706114603938, 0, 0, 0], RandomProvider[@-1774083719213728003, 0, 0, 0]," +
                " RandomProvider[@8538952961045368838, 0, 0, 0], RandomProvider[@7023783968100629274, 0, 0, 0]," +
                " RandomProvider[@8397262361995169820, 0, 0, 0], RandomProvider[@7421997658690496630, 0, 0, 0]," +
                " RandomProvider[@-7898255109170938473, 0, 0, 0], RandomProvider[@3868661580815414054, 0, 0, 0]," +
                " RandomProvider[@453733778809658833, 0, 0, 0], RandomProvider[@-2636879068627078241, 0, 0, 0]," +
                " RandomProvider[@6142487595818599495, 0, 0, 0], RandomProvider[@4681726659604324256, 0, 0, 0]," +
                " RandomProvider[@1906427249022382973, 0, 0, 0], RandomProvider[@8356990522398738440, 0, 0, 0]," +
                " RandomProvider[@1947303986473887049, 0, 0, 0], RandomProvider[@-7907745475302030450, 0, 0, 0]," +
                " RandomProvider[@-9092377260523661242, 0, 0, 0], RandomProvider[@-1031230974728655171, 0, 0, 0]," +
                " ...]");
        randomProvidersFixedScales_helper(-5, -10, -1,
                "[RandomProvider[@-7948823947390831374, -5, -10, -1]," +
                " RandomProvider[@7302477663894715351, -5, -10, -1]," +
                " RandomProvider[@5113382706114603938, -5, -10, -1]," +
                " RandomProvider[@-1774083719213728003, -5, -10, -1]," +
                " RandomProvider[@8538952961045368838, -5, -10, -1]," +
                " RandomProvider[@7023783968100629274, -5, -10, -1]," +
                " RandomProvider[@8397262361995169820, -5, -10, -1]," +
                " RandomProvider[@7421997658690496630, -5, -10, -1]," +
                " RandomProvider[@-7898255109170938473, -5, -10, -1]," +
                " RandomProvider[@3868661580815414054, -5, -10, -1]," +
                " RandomProvider[@453733778809658833, -5, -10, -1]," +
                " RandomProvider[@-2636879068627078241, -5, -10, -1]," +
                " RandomProvider[@6142487595818599495, -5, -10, -1]," +
                " RandomProvider[@4681726659604324256, -5, -10, -1]," +
                " RandomProvider[@1906427249022382973, -5, -10, -1]," +
                " RandomProvider[@8356990522398738440, -5, -10, -1]," +
                " RandomProvider[@1947303986473887049, -5, -10, -1]," +
                " RandomProvider[@-7907745475302030450, -5, -10, -1]," +
                " RandomProvider[@-9092377260523661242, -5, -10, -1]," +
                " RandomProvider[@-1031230974728655171, -5, -10, -1], ...]");
    }

    @Test
    public void testRandomProvidersDefault() {
        simpleProviderHelper(EP.randomProvidersDefault(),
                "[RandomProvider[@-7948823947390831374, 32, 8, 2], RandomProvider[@7302477663894715351, 32, 8, 2]," +
                " RandomProvider[@5113382706114603938, 32, 8, 2], RandomProvider[@-1774083719213728003, 32, 8, 2]," +
                " RandomProvider[@8538952961045368838, 32, 8, 2], RandomProvider[@7023783968100629274, 32, 8, 2]," +
                " RandomProvider[@8397262361995169820, 32, 8, 2], RandomProvider[@7421997658690496630, 32, 8, 2]," +
                " RandomProvider[@-7898255109170938473, 32, 8, 2], RandomProvider[@3868661580815414054, 32, 8, 2]," +
                " RandomProvider[@453733778809658833, 32, 8, 2], RandomProvider[@-2636879068627078241, 32, 8, 2]," +
                " RandomProvider[@6142487595818599495, 32, 8, 2], RandomProvider[@4681726659604324256, 32, 8, 2]," +
                " RandomProvider[@1906427249022382973, 32, 8, 2], RandomProvider[@8356990522398738440, 32, 8, 2]," +
                " RandomProvider[@1947303986473887049, 32, 8, 2], RandomProvider[@-7907745475302030450, 32, 8, 2]," +
                " RandomProvider[@-9092377260523661242, 32, 8, 2], RandomProvider[@-1031230974728655171, 32, 8, 2]," +
                " ...]");
    }

    @Test
    public void testRandomProvidersDefaultSecondaryAndTertiaryScale() {
        simpleProviderHelper(EP.randomProvidersDefaultSecondaryAndTertiaryScale(),
                "[RandomProvider[@-7948823947390831374, 0, 8, 2], RandomProvider[@-7948823947390831374, 1, 8, 2]," +
                " RandomProvider[@7302477663894715351, 0, 8, 2], RandomProvider[@7302477663894715351, 1, 8, 2]," +
                " RandomProvider[@-7948823947390831374, -1, 8, 2], RandomProvider[@-7948823947390831374, 2, 8, 2]," +
                " RandomProvider[@7302477663894715351, -1, 8, 2], RandomProvider[@7302477663894715351, 2, 8, 2]," +
                " RandomProvider[@5113382706114603938, 0, 8, 2], RandomProvider[@5113382706114603938, 1, 8, 2]," +
                " RandomProvider[@-1774083719213728003, 0, 8, 2], RandomProvider[@-1774083719213728003, 1, 8, 2]," +
                " RandomProvider[@5113382706114603938, -1, 8, 2], RandomProvider[@5113382706114603938, 2, 8, 2]," +
                " RandomProvider[@-1774083719213728003, -1, 8, 2], RandomProvider[@-1774083719213728003, 2, 8, 2]," +
                " RandomProvider[@-7948823947390831374, -2, 8, 2], RandomProvider[@-7948823947390831374, 3, 8, 2]," +
                " RandomProvider[@7302477663894715351, -2, 8, 2], RandomProvider[@7302477663894715351, 3, 8, 2]," +
                " ...]");
    }

    @Test
    public void testRandomProvidersDefaultTertiaryScale() {
        simpleProviderHelper(EP.randomProvidersDefaultTertiaryScale(),
                "[RandomProvider[@-7948823947390831374, 0, 0, 2], RandomProvider[@-7948823947390831374, 0, 1, 2]," +
                " RandomProvider[@7302477663894715351, 0, 0, 2], RandomProvider[@7302477663894715351, 0, 1, 2]," +
                " RandomProvider[@-7948823947390831374, 1, 0, 2], RandomProvider[@-7948823947390831374, 1, 1, 2]," +
                " RandomProvider[@7302477663894715351, 1, 0, 2], RandomProvider[@7302477663894715351, 1, 1, 2]," +
                " RandomProvider[@5113382706114603938, 0, 0, 2], RandomProvider[@5113382706114603938, 0, 1, 2]," +
                " RandomProvider[@-1774083719213728003, 0, 0, 2], RandomProvider[@-1774083719213728003, 0, 1, 2]," +
                " RandomProvider[@5113382706114603938, 1, 0, 2], RandomProvider[@5113382706114603938, 1, 1, 2]," +
                " RandomProvider[@-1774083719213728003, 1, 0, 2], RandomProvider[@-1774083719213728003, 1, 1, 2]," +
                " RandomProvider[@-7948823947390831374, 0, -1, 2], RandomProvider[@-7948823947390831374, 0, 2, 2]," +
                " RandomProvider[@7302477663894715351, 0, -1, 2]," +
                " RandomProvider[@7302477663894715351, 0, 2, 2], ...]");
    }

    @Test
    public void testRandomProviders() {
        simpleProviderHelper(EP.randomProviders(),
                "[RandomProvider[@-7948823947390831374, 0, 0, 0], RandomProvider[@-7948823947390831374, 0, 0, 1]," +
                " RandomProvider[@7302477663894715351, 0, 0, 0], RandomProvider[@7302477663894715351, 0, 0, 1]," +
                " RandomProvider[@-7948823947390831374, 0, 1, 0], RandomProvider[@-7948823947390831374, 0, 1, 1]," +
                " RandomProvider[@7302477663894715351, 0, 1, 0], RandomProvider[@7302477663894715351, 0, 1, 1]," +
                " RandomProvider[@5113382706114603938, 0, 0, 0], RandomProvider[@5113382706114603938, 0, 0, 1]," +
                " RandomProvider[@-1774083719213728003, 0, 0, 0], RandomProvider[@-1774083719213728003, 0, 0, 1]," +
                " RandomProvider[@5113382706114603938, 0, 1, 0], RandomProvider[@5113382706114603938, 0, 1, 1]," +
                " RandomProvider[@-1774083719213728003, 0, 1, 0], RandomProvider[@-1774083719213728003, 0, 1, 1]," +
                " RandomProvider[@-7948823947390831374, 1, 0, 0], RandomProvider[@-7948823947390831374, 1, 0, 1]," +
                " RandomProvider[@7302477663894715351, 1, 0, 0], RandomProvider[@7302477663894715351, 1, 0, 1], ...]");
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
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsLists(@NotNull String s) {
        return Readers.readList(Readers.readListWithNulls(Readers::readInteger)).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsListsWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers.readListWithNulls(Readers::readInteger)).apply(s).get();
    }
}
// @formatter:on
