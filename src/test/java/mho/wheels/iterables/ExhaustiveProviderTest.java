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
        aeqitLog(EP.uniformSample(readIntegerList(xs)), output);
    }

    private static void uniformSample_Iterable_helper_2(@NotNull String xs, @NotNull String output) {
        aeqitLog(EP.uniformSample(readIntegerListWithNulls(xs)), output);
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper_1("[3, 1, 4, 1]", "ExhaustiveProvider_uniformSample_Iterable_i");
        uniformSample_Iterable_helper_1("[]", "ExhaustiveProvider_uniformSample_Iterable_ii");
        uniformSample_Iterable_helper_2("[3, 1, null, 1]", "ExhaustiveProvider_uniformSample_Iterable_iii");
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

    private static void rangeUp_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeUp(BigInteger.valueOf(a)), output);
    }

    @Test
    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper(0, "ExhaustiveProvider_rangeUp_BigInteger_i");
        rangeUp_BigInteger_helper(5, "ExhaustiveProvider_rangeUp_BigInteger_ii");
        rangeUp_BigInteger_helper(-5, "ExhaustiveProvider_rangeUp_BigInteger_iii");
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, EP.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_char() {
        rangeUp_char_helper('\0', "ExhaustiveProvider_rangeUp_char_i");
        rangeUp_char_helper('a', "ExhaustiveProvider_rangeUp_char_ii");
        rangeUp_char_helper('Ш', "ExhaustiveProvider_rangeUp_char_iii");
        rangeUp_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeUp_char_iv");
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

    private static void rangeDown_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(EP.rangeDown(BigInteger.valueOf(a)), output);
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper(0, "ExhaustiveProvider_rangeDown_BigInteger_i");
        rangeDown_BigInteger_helper(5, "ExhaustiveProvider_rangeDown_BigInteger_ii");
        rangeDown_BigInteger_helper(-5, "ExhaustiveProvider_rangeDown_BigInteger_iii");
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, EP.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_char() {
        rangeDown_char_helper('\0', "ExhaustiveProvider_rangeDown_char_i");
        rangeDown_char_helper('a', "ExhaustiveProvider_rangeDown_char_ii");
        rangeDown_char_helper('Ш', "ExhaustiveProvider_rangeDown_char_iii");
        rangeDown_char_helper(Character.MAX_VALUE, "ExhaustiveProvider_rangeDown_char_iv");
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range((byte) a, (byte) b), output);
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(10, 20, "ExhaustiveProvider_range_byte_byte_i");
        range_byte_byte_helper(10, 10, "ExhaustiveProvider_range_byte_byte_ii");
        range_byte_byte_helper(10, 9, "ExhaustiveProvider_range_byte_byte_iii");
        range_byte_byte_helper(-20, -10, "ExhaustiveProvider_range_byte_byte_iv");
        range_byte_byte_helper(-20, -20, "ExhaustiveProvider_range_byte_byte_v");
        range_byte_byte_helper(-20, -21, "ExhaustiveProvider_range_byte_byte_vi");
        range_byte_byte_helper(0, 0, "ExhaustiveProvider_range_byte_byte_vii");
        range_byte_byte_helper(0, 10, "ExhaustiveProvider_range_byte_byte_viii");
        range_byte_byte_helper(-5, 0, "ExhaustiveProvider_range_byte_byte_ix");
        range_byte_byte_helper(-5, 10, "ExhaustiveProvider_range_byte_byte_x");
        range_byte_byte_helper(-10, 5, "ExhaustiveProvider_range_byte_byte_xi");
        range_byte_byte_helper(5, -10, "ExhaustiveProvider_range_byte_byte_xii");
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range((short) a, (short) b), output);
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(10, 20, "ExhaustiveProvider_range_short_short_i");
        range_short_short_helper(10, 10, "ExhaustiveProvider_range_short_short_ii");
        range_short_short_helper(10, 9, "ExhaustiveProvider_range_short_short_iii");
        range_short_short_helper(-20, -10, "ExhaustiveProvider_range_short_short_iv");
        range_short_short_helper(-20, -20, "ExhaustiveProvider_range_short_short_v");
        range_short_short_helper(-20, -21, "ExhaustiveProvider_range_short_short_vi");
        range_short_short_helper(0, 0, "ExhaustiveProvider_range_short_short_vii");
        range_short_short_helper(0, 10, "ExhaustiveProvider_range_short_short_viii");
        range_short_short_helper(-5, 0, "ExhaustiveProvider_range_short_short_ix");
        range_short_short_helper(-5, 10, "ExhaustiveProvider_range_short_short_x");
        range_short_short_helper(-10, 5, "ExhaustiveProvider_range_short_short_xi");
        range_short_short_helper(5, -10, "ExhaustiveProvider_range_short_short_xii");
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range(a, b), output);
    }

    @Test
    public void testRange_int_int() {
        range_int_int_helper(10, 20, "ExhaustiveProvider_range_int_int_i");
        range_int_int_helper(10, 10, "ExhaustiveProvider_range_int_int_ii");
        range_int_int_helper(10, 9, "ExhaustiveProvider_range_int_int_iii");
        range_int_int_helper(-20, -10, "ExhaustiveProvider_range_int_int_iv");
        range_int_int_helper(-20, -20, "ExhaustiveProvider_range_int_int_v");
        range_int_int_helper(-20, -21, "ExhaustiveProvider_range_int_int_vi");
        range_int_int_helper(0, 0, "ExhaustiveProvider_range_int_int_vii");
        range_int_int_helper(0, 10, "ExhaustiveProvider_range_int_int_viii");
        range_int_int_helper(-5, 0, "ExhaustiveProvider_range_int_int_ix");
        range_int_int_helper(-5, 10, "ExhaustiveProvider_range_int_int_x");
        range_int_int_helper(-10, 5, "ExhaustiveProvider_range_int_int_xi");
        range_int_int_helper(5, -10, "ExhaustiveProvider_range_int_int_xii");
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        aeqitLog(EP.range(a, b), output);
    }

    @Test
    public void testRange_long_long() {
        range_long_long_helper(10L, 20L, "ExhaustiveProvider_range_long_long_i");
        range_long_long_helper(10L, 10L, "ExhaustiveProvider_range_long_long_ii");
        range_long_long_helper(10L, 9L, "ExhaustiveProvider_range_long_long_iii");
        range_long_long_helper(-20L, -10L, "ExhaustiveProvider_range_long_long_iv");
        range_long_long_helper(-20L, -20L, "ExhaustiveProvider_range_long_long_v");
        range_long_long_helper(-20L, -21L, "ExhaustiveProvider_range_long_long_vi");
        range_long_long_helper(0L, 0L, "ExhaustiveProvider_range_long_long_vii");
        range_long_long_helper(0L, 10L, "ExhaustiveProvider_range_long_long_viii");
        range_long_long_helper(-5L, 0L, "ExhaustiveProvider_range_long_long_ix");
        range_long_long_helper(-5L, 10L, "ExhaustiveProvider_range_long_long_x");
        range_long_long_helper(-10L, 5L, "ExhaustiveProvider_range_long_long_xi");
        range_long_long_helper(5L, -10L, "ExhaustiveProvider_range_long_long_xii");
    }

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        aeqitLog(EP.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
    }

    @Test
    public void testRange_BigInteger_BigInteger() {
        range_BigInteger_BigInteger_helper(10, 20, "ExhaustiveProvider_range_BigInteger_BigInteger_i");
        range_BigInteger_BigInteger_helper(10, 10, "ExhaustiveProvider_range_BigInteger_BigInteger_ii");
        range_BigInteger_BigInteger_helper(10, 9, "ExhaustiveProvider_range_BigInteger_BigInteger_iii");
        range_BigInteger_BigInteger_helper(-20, -10, "ExhaustiveProvider_range_BigInteger_BigInteger_iv");
        range_BigInteger_BigInteger_helper(-20, -20, "ExhaustiveProvider_range_BigInteger_BigInteger_v");
        range_BigInteger_BigInteger_helper(-20, -21, "ExhaustiveProvider_range_BigInteger_BigInteger_vi");
        range_BigInteger_BigInteger_helper(0, 0, "ExhaustiveProvider_range_BigInteger_BigInteger_vii");
        range_BigInteger_BigInteger_helper(0, 10, "ExhaustiveProvider_range_BigInteger_BigInteger_viii");
        range_BigInteger_BigInteger_helper(-5, 0, "ExhaustiveProvider_range_BigInteger_BigInteger_ix");
        range_BigInteger_BigInteger_helper(-5, 10, "ExhaustiveProvider_range_BigInteger_BigInteger_x");
        range_BigInteger_BigInteger_helper(-10, 5, "ExhaustiveProvider_range_BigInteger_BigInteger_xi");
        range_BigInteger_BigInteger_helper(5, -10, "ExhaustiveProvider_range_BigInteger_BigInteger_xii");
    }

    private static void range_char_char_helper(char a, char b, @NotNull String output) {
        aeqitLog(EP.range(a, b), output);
    }

    @Test
    public void testRange_char_char() {
        range_char_char_helper('a', 'z', "ExhaustiveProvider_range_char_char_i");
        range_char_char_helper('a', 'a', "ExhaustiveProvider_range_char_char_ii");
        range_char_char_helper('a', 'A', "ExhaustiveProvider_range_char_char_iii");
        range_char_char_helper('!', '9', "ExhaustiveProvider_range_char_char_iv");
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
        simpleProviderHelper(EP.rangeDown(BinaryFraction.read(a).get()), output);
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
        simpleProviderHelper(EP.range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()), output);
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper("0", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_i");
        range_BinaryFraction_BinaryFraction_helper("1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_ii");
        range_BinaryFraction_BinaryFraction_helper("11", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_iii");
        range_BinaryFraction_BinaryFraction_helper("11", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_iv");
        range_BinaryFraction_BinaryFraction_helper("-11", "-1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_v");
        range_BinaryFraction_BinaryFraction_helper("-11", "-11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_vi");
        range_BinaryFraction_BinaryFraction_helper("-1", "-11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_vii");
        range_BinaryFraction_BinaryFraction_helper("0", "0",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_viii");
        range_BinaryFraction_BinaryFraction_helper("0", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_ix");
        range_BinaryFraction_BinaryFraction_helper("-1", "11",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_x");
        range_BinaryFraction_BinaryFraction_helper("5 >> 20", "1",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_xi");
        range_BinaryFraction_BinaryFraction_helper("1", "5 << 20",
                "ExhaustiveProvider_range_BinaryFraction_BinaryFraction_xii");
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
        } catch (ArithmeticException ignored) {}
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
        range_float_float_helper(1.0f, -1.0f, "ExhaustiveProvider_range_float_float_xxvi");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xxvii");
        range_float_float_helper(Float.POSITIVE_INFINITY, 1.0f,
                "ExhaustiveProvider_range_float_float_xxviii");
        range_float_float_helper(1.0f, Float.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_float_float_xxix");
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
        } catch (ArithmeticException ignored) {}
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
        range_double_double_helper(1.0f, -1.0f, "ExhaustiveProvider_range_double_double_xxvi");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xxvii");
        range_double_double_helper(Double.POSITIVE_INFINITY, 1.0f,
                "ExhaustiveProvider_range_double_double_xxviii");
        range_double_double_helper(1.0f, Double.NEGATIVE_INFINITY,
                "ExhaustiveProvider_range_double_double_xxix");
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

    @Test
    public void testNonEmptyOptionals() {
        simpleProviderHelper(EP.nonEmptyOptionals(EP.integers()), "ExhaustiveProvider_nonEmptyOptionals_i");
        simpleProviderHelper(EP.nonEmptyOptionals(EP.strings()), "ExhaustiveProvider_nonEmptyOptionals_ii");
        aeqitLog(EP.nonEmptyOptionals(Arrays.asList(1, 2, 3)), "ExhaustiveProvider_nonEmptyOptionals_iii");
        aeqitLog(EP.nonEmptyOptionals(Collections.emptyList()), "ExhaustiveProvider_nonEmptyOptionals_iv");
        try {
            toList(take(TINY_LIMIT, EP.nonEmptyOptionals(EP.withNull(EP.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testOptionals() {
        simpleProviderHelper(EP.optionals(EP.integers()), "ExhaustiveProvider_nonOptionals_i");
        simpleProviderHelper(EP.optionals(EP.strings()), "ExhaustiveProvider_nonOptionals_ii");
        aeqitLog(EP.optionals(Arrays.asList(1, 2, 3)), "ExhaustiveProvider_nonOptionals_iii");
        aeqitLog(EP.optionals(Collections.emptyList()), "ExhaustiveProvider_nonOptionals_iv");
        try {
            toList(take(TINY_LIMIT, EP.optionals(EP.withNull(EP.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testNonEmptyNullableOptionals() {
        simpleProviderHelper(EP.nonEmptyNullableOptionals(EP.withNull(EP.integers())),
                "ExhaustiveProvider_nonEmptyNullableOptionals_i");
        simpleProviderHelper(EP.nonEmptyNullableOptionals(EP.withNull(EP.strings())),
                "ExhaustiveProvider_nonEmptyNullableOptionals_ii");
        aeqitLog(EP.nonEmptyNullableOptionals(Arrays.asList(1, 2, 3)),
                "ExhaustiveProvider_nonEmptyNullableOptionals_iii");
        aeqitLog(EP.nonEmptyNullableOptionals(Collections.emptyList()),
                "ExhaustiveProvider_nonEmptyNullableOptionals_iv");
    }

    @Test
    public void testNullableOptionals() {
        simpleProviderHelper(EP.nullableOptionals(EP.withNull(EP.integers())),
                "ExhaustiveProvider_nullableOptionals_i");
        simpleProviderHelper(EP.nullableOptionals(EP.withNull(EP.strings())),
                "ExhaustiveProvider_nullableOptionals_ii");
        aeqitLog(EP.nullableOptionals(Arrays.asList(1, 2, 3)), "ExhaustiveProvider_nullableOptionals_iii");
        aeqitLog(EP.nullableOptionals(Collections.emptyList()), "ExhaustiveProvider_nullableOptionals_iv");
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
        simpleProviderHelper(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), f), "ExhaustiveProvider_dependentPairs_i");

        aeqitLog(EP.dependentPairs(Collections.emptyList(), f), "ExhaustiveProvider_dependentPairs_ii");

        simpleProviderHelper(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> Collections.emptyList()),
                "ExhaustiveProvider_dependentPairs_iii");

        try {
            toList(EP.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testDependentPairsInfinite() {
        simpleProviderHelper(EP.dependentPairsInfinite(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers()),
                "ExhaustiveProvider_dependentPairsInfinite_i");

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), f),
                "ExhaustiveProvider_dependentPairsInfinite_ii");

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
                EP.dependentPairsInfiniteLogarithmicOrder(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers()),
                "ExhaustiveProvider_dependentPairsInfiniteLogarithmicOrder_i"
        );

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfiniteLogarithmicOrder(cycle(Arrays.asList(1, 0)), f),
                "ExhaustiveProvider_dependentPairsInfiniteLogarithmicOrder_ii");

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
                EP.dependentPairsInfiniteSquareRootOrder(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers()),
                "ExhaustiveProvider_dependentPairsInfiniteSquareRootOrder_i"
        );

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(EP.dependentPairsInfiniteSquareRootOrder(cycle(Arrays.asList(1, 0)), f),
                "ExhaustiveProvider_dependentPairsInfiniteSquareRootOrder_ii");

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
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_i"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_ii"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_iii"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Collections.emptyList(), fromString("abcd")),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_iv"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Collections.emptyList(), Collections.emptyList()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_v"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_vi"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_vii"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_Iterable_viii"
        );
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4)),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_i"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4)),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_ii"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4)),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_iii"
        );
        aeqitLog(
                EP.pairsLogarithmicOrder(Collections.emptyList()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_iv"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_v"
        );
        simpleProviderHelper(
                EP.pairsLogarithmicOrder(cons(null, EP.naturalBigIntegers())),
                "ExhaustiveProvider_pairsLogarithmicOrder_Iterable_vi"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable_Iterable() {
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_i"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_ii"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_iii"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Collections.emptyList(), fromString("abcd")),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_iv"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Collections.emptyList(), Collections.emptyList()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_v"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_vi"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_vii"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_Iterable_viii"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable() {
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4)),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_i"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4)),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_ii"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4)),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_iii"
        );
        aeqitLog(
                EP.pairsSquareRootOrder(Collections.emptyList()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_iv"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_v"
        );
        simpleProviderHelper(
                EP.pairsSquareRootOrder(cons(null, EP.naturalBigIntegers())),
                "ExhaustiveProvider_pairsSquareRootOrder_Iterable_vi"
        );
    }

    private static void permutationsFiniteHelper(@NotNull String input, @NotNull String output) {
        aeqitLog(map(Testing::its, EP.permutationsFinite(readIntegerListWithNulls(input))), output);
    }

    private static void permutationsFiniteHelper(@NotNull List<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.permutationsFinite(input)), output);
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
        permutationsFiniteHelper(toList(IterableUtils.range(1, 10)), "ExhaustiveProvider_permutationsFinite_x");
    }

    @Test
    public void testStringPermutations() {
        aeqitLog(EP.stringPermutations(""), "ExhaustiveProvider_stringPermutations_i");
        aeqitLog(EP.stringPermutations("a"), "ExhaustiveProvider_stringPermutations_ii");
        aeqitLog(EP.stringPermutations("abc"), "ExhaustiveProvider_stringPermutations_iii");
        aeqitLog(EP.stringPermutations("foo"), "ExhaustiveProvider_stringPermutations_iv");
        aeqitLog(EP.stringPermutations("hello"), "ExhaustiveProvider_stringPermutations_v");
        simpleProviderHelper(EP.stringPermutations("Mississippi"), "ExhaustiveProvider_stringPermutations_vi");
    }

    private static void prefixPermutationsHelper(@NotNull String input, @NotNull String output) {
        aeqitLog(map(Testing::its, EP.prefixPermutations(readIntegerList(input))), output);
    }

    private static void prefixPermutationsHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        aeqitLog(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    private static void prefixPermutationsLimitHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, EP.prefixPermutations(input)), output);
    }

    @Test
    public void testPrefixPermutations() {
        prefixPermutationsHelper("[]", "ExhaustiveProvider_prefixPermutations_i");
        prefixPermutationsHelper("[5]", "ExhaustiveProvider_prefixPermutations_ii");
        prefixPermutationsHelper("[1, 2]", "ExhaustiveProvider_prefixPermutations_iii");
        prefixPermutationsHelper("[1, 2, 3]", "ExhaustiveProvider_prefixPermutations_iv");
        prefixPermutationsHelper("[1, 2, 3, 4]", "ExhaustiveProvider_prefixPermutations_v");
        prefixPermutationsHelper("[1, 2, 2, 4]", "ExhaustiveProvider_prefixPermutations_vi");
        prefixPermutationsHelper("[2, 2, 2, 2]", "ExhaustiveProvider_prefixPermutations_vii");
        prefixPermutationsHelper("[3, 1, 4, 1]", "ExhaustiveProvider_prefixPermutations_viii");
        prefixPermutationsHelper(Arrays.asList(3, 1, null, 1), "ExhaustiveProvider_prefixPermutations_ix");
        prefixPermutationsLimitHelper(IterableUtils.range(1, 10), "ExhaustiveProvider_prefixPermutations_x");
        prefixPermutationsLimitHelper(EP.positiveIntegers(), "ExhaustiveProvider_prefixPermutations_xi");
        prefixPermutationsLimitHelper(repeat(1), "ExhaustiveProvider_prefixPermutations_xii");
    }

    private static void listsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.listsLex(size, readIntegerListWithNulls(input)), output);
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
        aeqitLog(EP.pairsLex(as, toList(bs)), output);
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
        pairsLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), "ExhaustiveProvider_pairsLex_i");
        pairsLex_helper(Arrays.asList(1, null, 3), fromString("abc"), "ExhaustiveProvider_pairsLex_ii");
        pairsLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"), "ExhaustiveProvider_pairsLex_iii");
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
        triplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), EP.booleans(), "ExhaustiveProvider_triplesLex_i");
        triplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(),
                "ExhaustiveProvider_triplesLex_ii");
        triplesLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(),
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
                "ExhaustiveProvider_quadruplesLex_i");
        quadruplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), EP.booleans(), EP.orderings(),
                "ExhaustiveProvider_quadruplesLex_ii");
        quadruplesLex_helper_limit(EP.naturalBigIntegers(), fromString("abc"), EP.booleans(), EP.orderings(),
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
                "ExhaustiveProvider_quintuplesLex_i");
        quintuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                "ExhaustiveProvider_quintuplesLex_ii");
        quintuplesLex_helper_limit(
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
                "ExhaustiveProvider_sextuplesLex_i");
        sextuplesLex_helper(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                EP.booleans(),
                EP.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                "ExhaustiveProvider_sextuplesLex_ii");
        sextuplesLex_helper_limit(
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
        septuplesLex_helper_limit(
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

    private static void stringsLex_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.stringsLex(size, input), output);
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

        stringsLex_int_String_helper_limit(0, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xvii");
        stringsLex_int_String_helper_limit(1, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xviii");
        stringsLex_int_String_helper_limit(2, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xix");
        stringsLex_int_String_helper_limit(3, "Mississippi", "ExhaustiveProvider_stringsLex_int_String_xx");
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
        aeqitLog(EP.lists(size, readIntegerListWithNulls(input)), output);
    }

    private static void lists_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.lists(size, input), output);
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
        aeqitLog(ps, output);
        testNoRemove(ps);
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
        simpleProviderHelper(EP.pairs(EP.naturalBigIntegers(), fromString("abcd")),
                "ExhaustiveProvider_pairs_Iterable_Iterable_v");
        simpleProviderHelper(EP.pairs(fromString("abcd"), EP.naturalBigIntegers()),
                "ExhaustiveProvider_pairs_Iterable_Iterable_vi");
        simpleProviderHelper(EP.pairs(EP.positiveBigIntegers(), EP.negativeBigIntegers()),
                "ExhaustiveProvider_pairs_Iterable_Iterable_vii");
    }

    private static void pairs_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.pairs(readIntegerListWithNulls(input)), output);
    }

    private static void pairs_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.pairs(input), output);
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
        Iterable<Triple<A, B, C>> ts = EP.triples(as, bs, cs);
        aeqitLog(ts, output);
        testNoRemove(ts);
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
        simpleProviderHelper(EP.triples(EP.naturalBigIntegers(), fromString("abcd"), EP.booleans()),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_iv");
        simpleProviderHelper(EP.triples(fromString("abcd"), EP.booleans(), EP.naturalBigIntegers()),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_v");
        simpleProviderHelper(EP.triples(EP.positiveBigIntegers(), EP.negativeBigIntegers(), EP.characters()),
                "ExhaustiveProvider_triples_Iterable_Iterable_Iterable_vi");
    }

    private static void triples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.triples(readIntegerListWithNulls(input)), output);
    }

    private static void triples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.triples(input), output);
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
        Iterable<Quadruple<A, B, C, D>> qs = EP.quadruples(as, bs, cs, ds);
        aeqitLog(qs, output);
        testNoRemove(qs);
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
        simpleProviderHelper(EP.quadruples(EP.naturalBigIntegers(), fromString("abcd"), EP.booleans(), EP.orderings()),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_v");
        simpleProviderHelper(EP.quadruples(fromString("abcd"), EP.booleans(), EP.naturalBigIntegers(), EP.orderings()),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_vi");
        simpleProviderHelper(
                EP.quadruples(EP.positiveBigIntegers(), EP.negativeBigIntegers(), EP.characters(), EP.strings()),
                "ExhaustiveProvider_quadruples_Iterable_Iterable_Iterable_Iterable_vii");
    }

    private static void quadruples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(EP.quadruples(readIntegerListWithNulls(input)), output);
    }

    private static void quadruples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(EP.quadruples(input), output);
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
        Iterable<Quintuple<A, B, C, D, E>> qs = EP.quintuples(as, bs, cs, ds, es);
        aeqitLog(qs, output);
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
        simpleProviderHelper(
                EP.quintuples(
                        EP.naturalBigIntegers(),
                        fromString("abcd"),
                        EP.booleans(),
                        EP.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_v"
        );
        simpleProviderHelper(
                EP.quintuples(
                        fromString("abcd"),
                        EP.booleans(),
                        EP.naturalBigIntegers(),
                        EP.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
        );
        simpleProviderHelper(
                EP.quintuples(
                        EP.positiveBigIntegers(),
                        EP.negativeBigIntegers(),
                        EP.characters(),
                        EP.strings(),
                        EP.floats()
                ),
                "ExhaustiveProvider_quintuples_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
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
        Iterable<Sextuple<A, B, C, D, E, F>> ss = EP.sextuples(as, bs, cs, ds, es, fs);
        aeqitLog(ss, output);
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
        simpleProviderHelper(
                EP.sextuples(
                        EP.naturalBigIntegers(),
                        fromString("abcd"),
                        EP.booleans(),
                        EP.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_v"
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
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
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
                "ExhaustiveProvider_sextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
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
        Iterable<Septuple<A, B, C, D, E, F, G>> ss = EP.septuples(as, bs, cs, ds, es, fs, gs);
        aeqitLog(ss, output);
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
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_v"
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
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vi"
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
                "ExhaustiveProvider_septuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_vii"
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
        strings_int_helper(0, "ExhaustiveProvider_strings_int_i");
        strings_int_helper(1, "ExhaustiveProvider_strings_int_ii");
        strings_int_helper(2, "ExhaustiveProvider_strings_int_iii");
        strings_int_helper(3, "ExhaustiveProvider_strings_int_iv");
        try {
            EP.strings(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLists_Iterable() {
        aeqitLog(EP.lists(Collections.emptyList()), "ExhaustiveProvider_lists_Iterable_i");
        simpleProviderHelper(EP.lists(Collections.singletonList(5)), "ExhaustiveProvider_lists_Iterable_ii");
        simpleProviderHelper(EP.lists(Arrays.asList(1, 2, 3)), "ExhaustiveProvider_lists_Iterable_iii");
        simpleProviderHelper(EP.lists(Arrays.asList(1, 2, 2, 3)), "ExhaustiveProvider_lists_Iterable_iv");
        simpleProviderHelper(EP.lists(EP.naturalIntegers()), "ExhaustiveProvider_lists_Iterable_v");
        simpleProviderHelper(EP.lists(repeat(1)), "ExhaustiveProvider_lists_Iterable_vi");
    }

    @Test
    public void testStrings_String() {
        aeqitLog(EP.strings(""), "ExhaustiveProvider_strings_String_i");
        simpleProviderHelper(EP.strings("a"), "ExhaustiveProvider_strings_String_ii");
        simpleProviderHelper(EP.strings("abc"), "ExhaustiveProvider_strings_String_iii");
        simpleProviderHelper(EP.strings("abbc"), "ExhaustiveProvider_strings_String_iv");
        simpleProviderHelper(EP.strings("Mississippi"), "ExhaustiveProvider_strings_String_v");
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
        stringsAtLeast_String_helper(0, "", "ExhaustiveProvider_stringsAtLeast_String_i");
        stringsAtLeast_String_helper(1, "", "ExhaustiveProvider_stringsAtLeast_String_ii");
        stringsAtLeast_String_helper(2, "", "ExhaustiveProvider_stringsAtLeast_String_iii");
        stringsAtLeast_String_helper(3, "", "ExhaustiveProvider_stringsAtLeast_String_iv");

        stringsAtLeast_String_helper(0, "a", "ExhaustiveProvider_stringsAtLeast_String_v");
        stringsAtLeast_String_helper(1, "a", "ExhaustiveProvider_stringsAtLeast_String_vi");
        stringsAtLeast_String_helper(2, "a", "ExhaustiveProvider_stringsAtLeast_String_vii");
        stringsAtLeast_String_helper(3, "a", "ExhaustiveProvider_stringsAtLeast_String_viii");

        stringsAtLeast_String_helper(0, "abc", "ExhaustiveProvider_stringsAtLeast_String_ix");
        stringsAtLeast_String_helper(1, "abc", "ExhaustiveProvider_stringsAtLeast_String_x");
        stringsAtLeast_String_helper(2, "abc", "ExhaustiveProvider_stringsAtLeast_String_xi");
        stringsAtLeast_String_helper(3, "abc", "ExhaustiveProvider_stringsAtLeast_String_xii");

        stringsAtLeast_String_helper(0, "abbc", "ExhaustiveProvider_stringsAtLeast_String_xiii");
        stringsAtLeast_String_helper(1, "abbc", "ExhaustiveProvider_stringsAtLeast_String_xiv");
        stringsAtLeast_String_helper(2, "abbc", "ExhaustiveProvider_stringsAtLeast_String_xv");
        stringsAtLeast_String_helper(3, "abbc", "ExhaustiveProvider_stringsAtLeast_String_xvi");

        stringsAtLeast_String_helper(0, "Mississippi", "ExhaustiveProvider_stringsAtLeast_String_xvii");
        stringsAtLeast_String_helper(1, "Mississippi", "ExhaustiveProvider_stringsAtLeast_String_xviii");
        stringsAtLeast_String_helper(2, "Mississippi", "ExhaustiveProvider_stringsAtLeast_String_xix");
        stringsAtLeast_String_helper(3, "Mississippi", "ExhaustiveProvider_stringsAtLeast_String_xx");
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
        stringsAtLeast_helper(0, "ExhaustiveProvider_stringsAtLeast_i");
        stringsAtLeast_helper(1, "ExhaustiveProvider_stringsAtLeast_ii");
        stringsAtLeast_helper(2, "ExhaustiveProvider_stringsAtLeast_iii");
        stringsAtLeast_helper(3, "ExhaustiveProvider_stringsAtLeast_iv");
        try {
            EP.stringsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsLex(size, readIntegerListWithNulls(input)), output);
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

    private static void distinctStringsLex_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(EP.distinctStringsLex(size, input), output);
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

        distinctStringsLex_int_String_helper_limit(0, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xvii");
        distinctStringsLex_int_String_helper_limit(1, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xviii");
        distinctStringsLex_int_String_helper_limit(2, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xix");
        distinctStringsLex_int_String_helper_limit(3, "Mississippi",
                "ExhaustiveProvider_distinctStringsLex_int_String_xx");

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

    @Test
    public void testDistinctStringsLex_String() {
        aeqitLog(EP.distinctStringsLex(""), "ExhaustiveProvider_distinctStringsLex_String_i");
        aeqitLog(EP.distinctStringsLex("a"), "ExhaustiveProvider_distinctStringsLex_String_ii");
        aeqitLog(EP.distinctStringsLex("abc"), "ExhaustiveProvider_distinctStringsLex_String_iii");
        aeqitLog(EP.distinctStringsLex("abcd"), "ExhaustiveProvider_distinctStringsLex_String_iv");
        aeqitLog(EP.distinctStringsLex("abbc"), "ExhaustiveProvider_distinctStringsLex_String_v");
        simpleProviderHelper(EP.distinctStringsLex("Mississippi"), "ExhaustiveProvider_distinctStringsLex_String_vi");
    }

    private static void distinctListsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqitLog(EP.distinctListsLexAtLeast(minSize, readIntegerListWithNulls(input)), output);
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
        aeqitLog(EP.distinctStringsLexAtLeast(minSize, input), output);
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

        distinctStringsLexAtLeast_helper_limit(0, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xvii");
        distinctStringsLexAtLeast_helper_limit(1, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xviii");
        distinctStringsLexAtLeast_helper_limit(2, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xix");
        distinctStringsLexAtLeast_helper_limit(3, "Mississippi", "ExhaustiveProvider_distinctStringsLexAtLeast_xx");

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
