package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
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
    private static final ExhaustiveProvider P = ExhaustiveProvider.INSTANCE;
    private static final int TINY_LIMIT = 20;
    private static final int SMALL_LIMIT = 50;

    private static <T> void simpleProviderHelper(@NotNull Iterable<T> xs, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, xs, output);
        testNoRemove(TINY_LIMIT, xs);
    }

    @Test
    public void testBooleans() {
        simpleProviderHelper(P.booleans(), "[false, true]");
    }

    @Test
    public void testOrderingsIncreasing() {
        simpleProviderHelper(P.orderingsIncreasing(), "[LT, EQ, GT]");
    }

    @Test
    public void testOrderings() {
        simpleProviderHelper(P.orderings(), "[EQ, LT, GT]");
    }

    private static void uniformSample_Iterable_helper_1(@NotNull String xs, @NotNull String output) {
        aeqit(P.uniformSample(readIntegerList(xs)), output);
    }

    private static void uniformSample_Iterable_helper_2(@NotNull String xs, @NotNull String output) {
        aeqit(P.uniformSample(readIntegerListWithNulls(xs)), output);
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper_1("[3, 1, 4, 1]", "[3, 1, 4, 1]");
        uniformSample_Iterable_helper_1("[]", "[]");
        uniformSample_Iterable_helper_2("[3, 1, null, 1]", "[3, 1, null, 1]");
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        aeqcs(P.uniformSample(s), output);
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper("hello", "hello");
        uniformSample_String_helper("", "");
    }

    @Test
    public void testBytesIncreasing() {
        aeq(length(P.bytesIncreasing()), 256);
        simpleProviderHelper(P.bytesIncreasing(),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -117, -116, -115, -114, -113," +
                " -112, -111, -110, -109, ...]");
    }

    @Test
    public void testShortsIncreasing() {
        aeq(length(P.shortsIncreasing()), 65536);
        simpleProviderHelper(P.shortsIncreasing(),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759, -32758, -32757," +
                " -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749, ...]");
    }

    @Test
    public void testIntegersIncreasing() {
        simpleProviderHelper(P.integersIncreasing(),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644, -2147483643, -2147483642," +
                " -2147483641, -2147483640, -2147483639, -2147483638, -2147483637, -2147483636, -2147483635," +
                " -2147483634, -2147483633, -2147483632, -2147483631, -2147483630, -2147483629, ...]");
    }

    @Test
    public void testLongsIncreasing() {
        simpleProviderHelper(P.longsIncreasing(),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789, ...]");
    }

    @Test
    public void testPositiveBytes() {
        aeq(length(P.positiveBytes()), 127);
        simpleProviderHelper(P.positiveBytes(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]");
    }

    @Test
    public void testPositiveShorts() {
        aeq(length(P.positiveShorts()), 32767);
        simpleProviderHelper(P.positiveShorts(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]");
    }

    @Test
    public void testPositiveIntegers() {
        simpleProviderHelper(P.positiveIntegers(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]");
    }

    @Test
    public void testPositiveLongs() {
        simpleProviderHelper(P.positiveLongs(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]");
    }

    @Test
    public void testPositiveBigIntegers() {
        simpleProviderHelper(P.positiveBigIntegers(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]");
    }

    @Test
    public void testNegativeBytes() {
        aeq(length(P.negativeBytes()), 128);
        simpleProviderHelper(P.negativeBytes(),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, ...]");
    }

    @Test
    public void testNegativeShorts() {
        aeq(length(P.negativeShorts()), 32768);
        simpleProviderHelper(P.negativeShorts(),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, ...]");
    }

    @Test
    public void testNegativeIntegers() {
        simpleProviderHelper(P.negativeIntegers(),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, ...]");
    }

    @Test
    public void testNegativeLongs() {
        simpleProviderHelper(P.negativeLongs(),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, ...]");
    }

    @Test
    public void testNegativeBigIntegers() {
        simpleProviderHelper(P.negativeBigIntegers(),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, ...]");
    }

    @Test
    public void testNaturalBytes() {
        aeq(length(P.naturalBytes()), 128);
        simpleProviderHelper(P.naturalBytes(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
    }

    @Test
    public void testNaturalShorts() {
        aeq(length(P.naturalShorts()), 32768);
        simpleProviderHelper(P.naturalShorts(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
    }

    @Test
    public void testNaturalIntegers() {
        simpleProviderHelper(P.naturalIntegers(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
    }

    @Test
    public void testNaturalLongs() {
        simpleProviderHelper(P.naturalLongs(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
    }

    @Test
    public void testNaturalBigIntegers() {
        simpleProviderHelper(P.naturalBigIntegers(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
    }

    @Test
    public void testNonzeroBytes() {
        Iterable<Byte> bs = P.nonzeroBytes();
        aeq(length(bs), 255);
        aeqitLimit(TINY_LIMIT, reverse(bs),
                "[-128, -127, 127, -126, 126, -125, 125, -124, 124, -123, 123, -122, 122, -121, 121, -120, 120," +
                " -119, 119, -118, ...]");
        simpleProviderHelper(bs, "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    @Test
    public void testNonzeroShorts() {
        Iterable<Short> ss = P.nonzeroShorts();
        aeq(length(ss), 65535);
        aeqitLimit(TINY_LIMIT, reverse(ss),
                "[-32768, -32767, 32767, -32766, 32766, -32765, 32765, -32764, 32764, -32763, 32763, -32762, 32762," +
                " -32761, 32761, -32760, 32760, -32759, 32759, -32758, ...]");
        simpleProviderHelper(ss, "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    @Test
    public void testNonzeroIntegers() {
        simpleProviderHelper(P.nonzeroIntegers(),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    @Test
    public void testNonzeroLongs() {
        simpleProviderHelper(P.nonzeroLongs(),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    @Test
    public void testNonzeroBigIntegers() {
        simpleProviderHelper(P.nonzeroBigIntegers(),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, ...]");
    }

    @Test
    public void testBytes() {
        Iterable<Byte> bs = P.bytes();
        aeq(length(bs), 256);
        aeqitLimit(TINY_LIMIT, reverse(bs),
                "[-128, -127, 127, -126, 126, -125, 125, -124, 124, -123, 123, -122, 122, -121, 121, -120, 120," +
                " -119, 119, -118, ...]");
        simpleProviderHelper(bs, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    @Test
    public void testShorts() {
        Iterable<Short> ss = P.shorts();
        aeq(length(ss), 65536);
        aeqitLimit(TINY_LIMIT, reverse(ss),
                "[-32768, -32767, 32767, -32766, 32766, -32765, 32765, -32764, 32764, -32763, 32763, -32762, 32762," +
                " -32761, 32761, -32760, 32760, -32759, 32759, -32758, ...]");
        simpleProviderHelper(ss, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    @Test
    public void testIntegers() {
        simpleProviderHelper(P.integers(),
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    @Test
    public void testLongs() {
        simpleProviderHelper(P.longs(), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    @Test
    public void testBigIntegers() {
        simpleProviderHelper(P.bigIntegers(),
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    @Test
    public void testAsciiCharactersIncreasing() {
        aeq(length(P.asciiCharactersIncreasing()), 128);
        aeq(charsToString(P.asciiCharactersIncreasing()),
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177"
        );
    }

    @Test
    public void testAsciiCharacters() {
        aeq(length(P.asciiCharacters()), 128);
        aeq(charsToString(P.asciiCharacters()),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ " +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37\177"
        );
    }

    @Test
    public void testCharactersIncreasing() {
        aeq(length(P.charactersIncreasing()), 65536);
        aeq(charsToString(take(256, P.charactersIncreasing())),
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\u009f" +
                "\u00a0" +
                "¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }

    @Test
    public void testCharacters() {
        aeq(length(P.characters()), 65536);
        aeq(charsToString(take(256, P.characters())),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ " +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37\177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\u009f" +
                "\u00a0" +
                "¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }

    @Test
    public void testRoundingModes() {
        simpleProviderHelper(
                P.roundingModes(),
                "[UNNECESSARY, UP, DOWN, CEILING, FLOOR, HALF_UP, HALF_DOWN, HALF_EVEN]"
        );
    }

    private static void rangeUp_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp((byte) a), output);
    }

    @Test
    public void testRangeUp_byte() {
        rangeUp_byte_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
        rangeUp_byte_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, ...]");
        rangeUp_byte_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14, ...]");
        rangeUp_byte_helper(Byte.MAX_VALUE, "[127]");
        rangeUp_byte_helper(Byte.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    private static void rangeUp_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp((short) a), output);
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
        rangeUp_short_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, ...]");
        rangeUp_short_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14, ...]");
        rangeUp_short_helper(Short.MAX_VALUE, "[32767]");
        rangeUp_short_helper(Short.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
        rangeUp_int_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, ...]");
        rangeUp_int_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14, ...]");
        rangeUp_int_helper(Integer.MAX_VALUE, "[2147483647]");
        rangeUp_int_helper(Integer.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(0L, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
        rangeUp_long_helper(5L, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, ...]");
        rangeUp_long_helper(-5L, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14, ...]");
        rangeUp_long_helper(Long.MAX_VALUE, "[9223372036854775807]");
        rangeUp_long_helper(Long.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
    }

    private static void rangeUp_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(BigInteger.valueOf(a)), output);
    }

    @Test

    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ...]");
        rangeUp_BigInteger_helper(5,
                "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, ...]");
        rangeUp_BigInteger_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14, ...]");
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        aeqcs(P.rangeUp(a), output);
    }

    @Test
    public void testRangeUp_char() {
        rangeUp_char_helper(
                '\0',
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\177"
        );
        rangeUp_char_helper(
                'a',
                "abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\u009f" +
                "\u00a0" +
                "¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßà"
        );
        rangeUp_char_helper(
                'Ш',
                "ШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяѐёђѓєѕіїјљњћќѝўџѠѡѢѣѤѥѦѧѨѩѪѫѬѭѮѯѰѱѲѳѴѵѶѷѸѹѺѻѼѽѾѿҀҁ" +
                "\u0482\u0483\u0484\u0485\u0486\u0487\u0488\u0489" +
                "ҊҋҌҍҎҏҐґҒғҔҕҖҗҘҙҚқҜҝҞҟҠҡҢңҤҥҦҧ"
        );
        rangeUp_char_helper(Character.MAX_VALUE, "\uffff");
    }

    private static void rangeDown_byte_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown((byte) a), output);
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper(0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, ...]");
        rangeDown_byte_helper(5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, ...]");
        rangeDown_byte_helper(-5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24," +
                " ...]");
        rangeDown_byte_helper(Byte.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
        rangeDown_byte_helper(Byte.MIN_VALUE, "[-128]");
    }

    private static void rangeDown_short_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown((short) a), output);
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper(0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, ...]");
        rangeDown_short_helper(5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, ...]");
        rangeDown_short_helper(-5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24," +
                " ...]");
        rangeDown_short_helper(Short.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
        rangeDown_short_helper(Short.MIN_VALUE, "[-32768]");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, ...]");
        rangeDown_int_helper(5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, ...]");
        rangeDown_int_helper(-5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24," +
                " ...]");
        rangeDown_int_helper(Integer.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
        rangeDown_int_helper(Integer.MIN_VALUE, "[-2147483648]");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(0L,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, ...]");
        rangeDown_long_helper(5L,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, ...]");
        rangeDown_long_helper(-5L,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24," +
                " ...]");
        rangeDown_long_helper(Long.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, ...]");
        rangeDown_int_helper(Integer.MIN_VALUE, "[-2147483648]");
    }

    private static void rangeDown_BigInteger_helper(int a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(BigInteger.valueOf(a)), output);
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper(0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, ...]");
        rangeDown_BigInteger_helper(5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, ...]");
        rangeDown_BigInteger_helper(-5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24," +
                " ...]");
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        aeqcs(P.rangeDown(a), output);
    }

    @Test
    public void testRangeDown_char() {
        rangeDown_char_helper('\0', "\0");
        rangeDown_char_helper(
                'a',
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`a"
        );
        rangeDown_char_helper(
                'Ш',
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177"
        );
        rangeDown_char_helper(
                Character.MAX_VALUE,
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177"
        );
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqit(P.range((byte) a, (byte) b), output);
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
        aeqit(P.range((short) a, (short) b), output);
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
        aeqit(P.range(a, b), output);
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
        aeqit(P.range(a, b), output);
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
        aeqit(P.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
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
        aeqcs(P.range(a, b), output);
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
        simpleProviderHelper(P.positiveBinaryFractions(),
                "[1, 1 << 1, 3, 1 >> 1, 5, 3 << 1, 7, 1 << 2, 9, 5 << 1, 11, 3 >> 1, 13, 7 << 1, 15, 1 >> 2, 17," +
                " 9 << 1, 19, 5 >> 1, ...]");
    }

    @Test
    public void testNegativeBinaryFractions() {
        simpleProviderHelper(P.negativeBinaryFractions(),
                "[-1, -1 << 1, -3, -1 >> 1, -5, -3 << 1, -7, -1 << 2, -9, -5 << 1, -11, -3 >> 1, -13, -7 << 1, -15," +
                " -1 >> 2, -17, -9 << 1, -19, -5 >> 1, ...]");
    }

    @Test
    public void testNonzeroBinaryFractions() {
        simpleProviderHelper(P.nonzeroBinaryFractions(),
                "[1, -1, 1 << 1, -1 << 1, 3, -3, 1 >> 1, -1 >> 1, 5, -5, 3 << 1, -3 << 1, 7, -7, 1 << 2, -1 << 2, 9," +
                " -9, 5 << 1, -5 << 1, ...]");
    }

    @Test
    public void testBinaryFractions() {
        simpleProviderHelper(P.binaryFractions(),
                "[0, 1, -1, 1 << 1, -1 << 1, 3, -3, 1 >> 1, -1 >> 1, 5, -5, 3 << 1, -3 << 1, 7, -7, 1 << 2, -1 << 2," +
                " 9, -9, 5 << 1, ...]");
    }

    private static void rangeUp_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(BinaryFraction.read(a).get()), output);
    }

    @Test
    public void testRangeUp_BinaryFraction() {
        rangeUp_BinaryFraction_helper("0",
                "[0, 1, 1 << 1, 3, 1 >> 1, 5, 3 << 1, 7, 1 << 2, 9, 5 << 1, 11, 3 >> 1, 13, 7 << 1, 15, 1 >> 2, 17," +
                " 9 << 1, 19, ...]");
        rangeUp_BinaryFraction_helper("1",
                "[1, 1 << 1, 3, 1 << 2, 3 >> 1, 3 << 1, 7, 1 << 3, 5, 5 << 1, 11, 3 << 2, 5 >> 1, 7 << 1, 15," +
                " 1 << 4, 5 >> 2, 9 << 1, 19, 5 << 2, ...]");
        rangeUp_BinaryFraction_helper("11",
                "[11, 3 << 2, 13, 7 << 1, 23 >> 1, 1 << 4, 17, 9 << 1, 15, 5 << 2, 21, 11 << 1, 25 >> 1, 3 << 3, 25," +
                " 13 << 1, 45 >> 2, 7 << 2, 29, 15 << 1, ...]");
        rangeUp_BinaryFraction_helper("5 << 20",
                "[5 << 20, 3 << 21, 7 << 20, 1 << 23, 11 << 19, 5 << 21, 11 << 20, 3 << 22, 9 << 20, 7 << 21," +
                " 15 << 20, 1 << 24, 13 << 19, 9 << 21, 19 << 20, 5 << 22, 21 << 18, 11 << 21, 23 << 20, 3 << 23," +
                " ...]");
        rangeUp_BinaryFraction_helper("5 >> 20",
                "[5 >> 20, 3 >> 19, 7 >> 20, 1 >> 17, 11 >> 21, 5 >> 19, 11 >> 20, 3 >> 18, 9 >> 20, 7 >> 19," +
                " 15 >> 20, 1 >> 16, 13 >> 21, 9 >> 19, 19 >> 20, 5 >> 18, 21 >> 22, 11 >> 19, 23 >> 20, 3 >> 17," +
                " ...]");
        rangeUp_BinaryFraction_helper("-1",
                "[-1, 0, 1, 1 << 1, -1 >> 1, 1 << 2, 5, 3 << 1, 3, 1 << 3, 9, 5 << 1, 1 >> 1, 3 << 2, 13, 7 << 1," +
                " -3 >> 2, 1 << 4, 17, 9 << 1, ...]");
        rangeUp_BinaryFraction_helper("-11",
                "[-11, -5 << 1, -9, -1 << 3, -21 >> 1, -3 << 1, -5, -1 << 2, -7, -1 << 1, -1, 0, -19 >> 1, 1 << 1," +
                " 3, 1 << 2, -43 >> 2, 3 << 1, 7, 1 << 3, ...]");
        rangeUp_BinaryFraction_helper("-5 << 20",
                "[-5 << 20, -1 << 22, -3 << 20, -1 << 21, -9 << 19, 0, 1 << 20, 1 << 21, -1 << 20, 1 << 22, 5 << 20," +
                " 3 << 21, -7 << 19, 1 << 23, 9 << 20, 5 << 21, -19 << 18, 3 << 22, 13 << 20, 7 << 21, ...]");
        rangeUp_BinaryFraction_helper("-5 >> 20",
                "[-5 >> 20, -1 >> 18, -3 >> 20, -1 >> 19, -9 >> 21, 0, 1 >> 20, 1 >> 19, -1 >> 20, 1 >> 18, 5 >> 20," +
                " 3 >> 19, -7 >> 21, 1 >> 17, 9 >> 20, 5 >> 19, -19 >> 22, 3 >> 18, 13 >> 20, 7 >> 19, ...]");
    }

    private static void rangeDown_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(BinaryFraction.read(a).get()), output);
    }

    @Test
    public void testRangeDown_BinaryFraction() {
        rangeDown_BinaryFraction_helper("0",
                "[0, -1, -1 << 1, -3, -1 >> 1, -5, -3 << 1, -7, -1 << 2, -9, -5 << 1, -11, -3 >> 1, -13, -7 << 1," +
                " -15, -1 >> 2, -17, -9 << 1, -19, ...]");
        rangeDown_BinaryFraction_helper("1",
                "[1, 0, -1, -1 << 1, 1 >> 1, -1 << 2, -5, -3 << 1, -3, -1 << 3, -9, -5 << 1, -1 >> 1, -3 << 2, -13," +
                " -7 << 1, 3 >> 2, -1 << 4, -17, -9 << 1, ...]");
        rangeDown_BinaryFraction_helper("11",
                "[11, 5 << 1, 9, 1 << 3, 21 >> 1, 3 << 1, 5, 1 << 2, 7, 1 << 1, 1, 0, 19 >> 1, -1 << 1, -3, -1 << 2," +
                " 43 >> 2, -3 << 1, -7, -1 << 3, ...]");
        rangeDown_BinaryFraction_helper("5 << 20",
                "[5 << 20, 1 << 22, 3 << 20, 1 << 21, 9 << 19, 0, -1 << 20, -1 << 21, 1 << 20, -1 << 22, -5 << 20," +
                " -3 << 21, 7 << 19, -1 << 23, -9 << 20, -5 << 21, 19 << 18, -3 << 22, -13 << 20, -7 << 21, ...]");
        rangeDown_BinaryFraction_helper("5 >> 20",
                "[5 >> 20, 1 >> 18, 3 >> 20, 1 >> 19, 9 >> 21, 0, -1 >> 20, -1 >> 19, 1 >> 20, -1 >> 18, -5 >> 20," +
                " -3 >> 19, 7 >> 21, -1 >> 17, -9 >> 20, -5 >> 19, 19 >> 22, -3 >> 18, -13 >> 20, -7 >> 19, ...]");
        rangeDown_BinaryFraction_helper("-1",
                "[-1, -1 << 1, -3, -1 << 2, -3 >> 1, -3 << 1, -7, -1 << 3, -5, -5 << 1, -11, -3 << 2, -5 >> 1," +
                " -7 << 1, -15, -1 << 4, -5 >> 2, -9 << 1, -19, -5 << 2, ...]");
        rangeDown_BinaryFraction_helper("-11",
                "[-11, -3 << 2, -13, -7 << 1, -23 >> 1, -1 << 4, -17, -9 << 1, -15, -5 << 2, -21, -11 << 1," +
                " -25 >> 1, -3 << 3, -25, -13 << 1, -45 >> 2, -7 << 2, -29, -15 << 1, ...]");
        rangeDown_BinaryFraction_helper("-5 << 20",
                "[-5 << 20, -3 << 21, -7 << 20, -1 << 23, -11 << 19, -5 << 21, -11 << 20, -3 << 22, -9 << 20," +
                " -7 << 21, -15 << 20, -1 << 24, -13 << 19, -9 << 21, -19 << 20, -5 << 22, -21 << 18, -11 << 21," +
                " -23 << 20, -3 << 23, ...]");
        rangeDown_BinaryFraction_helper("-5 >> 20",
                "[-5 >> 20, -3 >> 19, -7 >> 20, -1 >> 17, -11 >> 21, -5 >> 19, -11 >> 20, -3 >> 18, -9 >> 20," +
                " -7 >> 19, -15 >> 20, -1 >> 16, -13 >> 21, -9 >> 19, -19 >> 20, -5 >> 18, -21 >> 22, -11 >> 19," +
                " -23 >> 20, -3 >> 17, ...]");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(P.range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()), output);
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper("0", "1",
                "[0, 1, 1 >> 1, 1 >> 2, 3 >> 2, 1 >> 3, 3 >> 3, 5 >> 3, 7 >> 3, 1 >> 4, 3 >> 4, 5 >> 4, 7 >> 4," +
                " 9 >> 4, 11 >> 4, 13 >> 4, 15 >> 4, 1 >> 5, 3 >> 5, 5 >> 5, ...]");
        range_BinaryFraction_BinaryFraction_helper("1", "11",
                "[1, 3, 5, 7, 9, 11, 1 << 1, 1 << 2, 3 << 1, 1 << 3, 5 << 1, 3 >> 1, 7 >> 1, 11 >> 1, 15 >> 1," +
                " 19 >> 1, 5 >> 1, 9 >> 1, 13 >> 1, 17 >> 1, ...]");
        range_BinaryFraction_BinaryFraction_helper("11", "11", "[11]");
        range_BinaryFraction_BinaryFraction_helper("11", "1", "[]");
        range_BinaryFraction_BinaryFraction_helper("-11", "-1",
                "[-11, -9, -7, -5, -3, -1, -5 << 1, -1 << 3, -3 << 1, -1 << 2, -1 << 1, -21 >> 1, -17 >> 1," +
                " -13 >> 1, -9 >> 1, -5 >> 1, -19 >> 1, -15 >> 1, -11 >> 1, -7 >> 1, ...]");
        range_BinaryFraction_BinaryFraction_helper("-11", "-11", "[-11]");
        range_BinaryFraction_BinaryFraction_helper("-1", "-11", "[]");
        range_BinaryFraction_BinaryFraction_helper("0", "0", "[0]");
        range_BinaryFraction_BinaryFraction_helper("0", "11",
                "[0, 1, 1 << 1, 3, 1 << 2, 5, 3 << 1, 7, 1 << 3, 9, 5 << 1, 11, 1 >> 1, 3 >> 1, 5 >> 1, 7 >> 1," +
                " 9 >> 1, 11 >> 1, 13 >> 1, 15 >> 1, ...]");
        range_BinaryFraction_BinaryFraction_helper("-1", "11",
                "[-1, 3, 7, 11, 1, 5, 9, 0, 1 << 2, 1 << 3, 1 << 1, 3 << 1, 5 << 1, -1 >> 1, 7 >> 1, 15 >> 1," +
                " 1 >> 1, 9 >> 1, 17 >> 1, 3 >> 1, ...]");
        range_BinaryFraction_BinaryFraction_helper("5 >> 20", "1",
                "[5 >> 20, 3 >> 19, 7 >> 20, 1 >> 17, 9 >> 20, 5 >> 19, 11 >> 20, 3 >> 18, 13 >> 20, 7 >> 19," +
                " 15 >> 20, 1 >> 16, 17 >> 20, 9 >> 19, 19 >> 20, 5 >> 18, 21 >> 20, 11 >> 19, 23 >> 20, 3 >> 17," +
                " ...]");
        range_BinaryFraction_BinaryFraction_helper("1", "5 << 20",
                "[1, 1 << 1, 3, 1 << 2, 5, 3 << 1, 7, 1 << 3, 9, 5 << 1, 11, 3 << 2, 13, 7 << 1, 15, 1 << 4, 17," +
                " 9 << 1, 19, 5 << 2, ...]");
    }

    @Test
    public void testPositiveFloats() {
        aeqitLimit(SMALL_LIMIT, P.positiveFloats(),
                "[Infinity, 1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0," +
                " 0.25, 8.0, 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875," +
                " 112.0, 9.0, 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0," +
                " 2.25, ...]");
    }

    @Test
    public void testNegativeFloats() {
        aeqitLimit(SMALL_LIMIT, P.negativeFloats(),
                "[-Infinity, -1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0," +
                " -3.5, -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, ...]");
    }

    @Test
    public void testNonzeroFloats() {
        aeqitLimit(SMALL_LIMIT, P.nonzeroFloats(),
                "[NaN, Infinity, -Infinity, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5," +
                " -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5," +
                " -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0," +
                " 0.375, -0.375, 48.0, ...]");
    }

    @Test
    public void testFloats() {
        aeqitLimit(SMALL_LIMIT, P.floats(),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375, ...]");
    }

    @Test
    public void testPositiveDoubles() {
        aeqitLimit(SMALL_LIMIT, P.positiveDoubles(),
                "[Infinity, 1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0," +
                " 0.25, 8.0, 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875," +
                " 112.0, 9.0, 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0," +
                " 2.25, ...]");
    }

    @Test
    public void testNegativeDoubles() {
        aeqitLimit(SMALL_LIMIT, P.negativeDoubles(),
                "[-Infinity, -1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0," +
                " -3.5, -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, ...]");
    }

    @Test
    public void testNonzeroDoubles() {
        aeqitLimit(SMALL_LIMIT, P.nonzeroDoubles(),
                "[NaN, Infinity, -Infinity, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5," +
                " -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5," +
                " -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0," +
                " 0.375, -0.375, 48.0, ...]");
    }

    @Test
    public void testDoubles() {
        aeqitLimit(SMALL_LIMIT, P.doubles(),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375, ...]");
    }

    private static void rangeUp_float_helper(float a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(a), output);
    }

    private static void rangeUp_float_fail_helper(float a) {
        try {
            P.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_float() {
        rangeUp_float_helper(1.0f,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0, ...]");
        rangeUp_float_helper(1.0E20f,
                "[Infinity, 1.0E20, 1.0000004E20, 1.0000007E20, 1.0000011E20, 1.0000002E20, 1.0000018E20," +
                " 1.0000021E20, 1.0000025E20, 1.0000014E20, 1.0000032E20, 1.0000035E20, 1.0000039E20, 1.00000055E20," +
                " 1.0000046E20, 1.00000495E20, 1.0000053E20, 1.0000001E20, 1.000006E20, 1.00000635E20, ...]");
        rangeUp_float_helper(-1.0f,
                "[Infinity, -1.0, 0.0, -0.0, 1.0, 2.0, -0.5, 4.0, 5.0, 6.0, 3.0, 8.0, 9.0, 10.0, 0.5, 12.0, 13.0," +
                " 14.0, -0.75, 16.0, ...]");
        rangeUp_float_helper(-1.0E20f,
                "[Infinity, -1.0E20, -9.999997E19, -9.999993E19, -9.99999E19, -9.9999984E19, -9.999983E19," +
                " -9.999979E19, -9.999976E19, -9.999986E19, -9.9999685E19, -9.999965E19, -9.9999615E19," +
                " -9.999995E19, -9.9999545E19, -9.999951E19, -9.999947E19, -9.999999E19, -9.99994E19, -9.999937E19," +
                " ...]");
        rangeUp_float_helper((float) Math.PI,
                "[Infinity, 3.1415927, 3.141593, 3.1415932, 3.1415935, 3.141594, 3.1415942, 3.1415944, 3.1415937," +
                " 3.141595, 3.1415951, 3.1415954, 3.1415958, 3.141596, 3.1415963, 3.1415968, 3.141597, 3.1415973," +
                " 3.1415977, 3.141598, ...]");
        rangeUp_float_helper((float) Math.sqrt(2),
                "[Infinity, 1.4142135, 1.4142137, 1.4142138, 1.4142139, 1.4142141, 1.4142143, 1.4142144, 1.414214," +
                " 1.4142146, 1.4142147, 1.4142148, 1.4142151, 1.4142152, 1.4142153, 1.4142156, 1.4142157, 1.4142158," +
                " 1.414216, 1.4142162, ...]");
        rangeUp_float_helper((float) -Math.PI,
                "[Infinity, -3.1415927, -3.1415925, -3.1415923, -3.141592, -3.1415915, -3.1415913, -3.141591," +
                " -3.1415918, -3.1415906, -3.1415904, -3.14159, -3.1415896, -3.1415894, -3.1415892, -3.1415887," +
                " -3.1415884, -3.1415882, -3.1415877, -3.1415875, ...]");
        rangeUp_float_helper((float) -Math.sqrt(2),
                "[Infinity, -1.4142135, -1.4142134, -1.4142133, -1.4142132, -1.414213, -1.4142128, -1.4142127," +
                " -1.4142131, -1.4142125, -1.4142123, -1.4142122, -1.414212, -1.4142119, -1.4142118, -1.4142115," +
                " -1.4142114, -1.4142113, -1.414211, -1.4142109, ...]");
        rangeUp_float_helper(0.0f,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0, ...]");
        rangeUp_float_helper(-0.0f,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0, ...]");
        rangeUp_float_helper(Float.MIN_VALUE,
                "[Infinity, 1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 8.4E-45, 9.8E-45, 1.1E-44, 7.0E-45, 1.4E-44," +
                " 1.5E-44, 1.7E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.5E-44, 2.7E-44, 2.8E-44, 3.1E-44, 3.2E-44, ...]");
        rangeUp_float_helper(Float.MIN_NORMAL,
                "[Infinity, 1.17549435E-38, 2.3509887E-38, 3.526483E-38, 4.7019774E-38, 1.7632415E-38, 7.052966E-38," +
                " 8.2284605E-38, 9.403955E-38, 5.877472E-38, 1.1754944E-37, 1.2930438E-37, 1.4105932E-37," +
                " 2.938736E-38, 1.6456921E-37, 1.7632415E-37, 1.880791E-37, 1.469368E-38, 2.1158898E-37," +
                " 2.2334393E-37, ...]");
        rangeUp_float_helper(-Float.MIN_VALUE,
                "[Infinity, -1.4E-45, 0.0, -0.0, 1.4E-45, 2.8E-45, 5.6E-45, 7.0E-45, 8.4E-45, 4.2E-45, 1.1E-44," +
                " 1.3E-44, 1.4E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.8E-44, ...]");
        rangeUp_float_helper(-Float.MIN_NORMAL,
                "[Infinity, -1.17549435E-38, 0.0, -0.0, 1.17549435E-38, 2.3509887E-38, -5.877472E-39, 4.7019774E-38," +
                " 5.877472E-38, 7.052966E-38, 3.526483E-38, 9.403955E-38, 1.0579449E-37, 1.1754944E-37," +
                " 5.877472E-39, 1.4105932E-37, 1.5281427E-37, 1.6456921E-37, -8.816208E-39, 1.880791E-37, ...]");
        rangeUp_float_helper(Float.MAX_VALUE, "[Infinity, 3.4028235E38]");
        rangeUp_float_helper(-Float.MAX_VALUE,
                "[Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028225E38, -3.4028222E38," +
                " -3.402822E38, -3.4028227E38, -3.4028216E38, -3.4028214E38, -3.4028212E38, -3.4028208E38," +
                " -3.4028206E38, -3.4028204E38, -3.40282E38, -3.4028198E38, -3.4028196E38, -3.4028192E38," +
                " -3.402819E38, ...]");
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "[Infinity]");
        rangeUp_float_helper(Float.NEGATIVE_INFINITY,
                "[Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0," +
                " 1.5, -1.5, 12.0, -12.0, ...]");
        rangeUp_float_fail_helper(Float.NaN);
    }

    private static void rangeDown_float_helper(float a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(a), output);
    }

    private static void rangeDown_float_fail_helper(float a) {
        try {
            P.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_float() {
        rangeDown_float_helper(1.0f,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0, ...]");
        rangeDown_float_helper(1.0E20f,
                "[-Infinity, 1.0E20, 9.999997E19, 9.999993E19, 9.99999E19, 9.9999984E19, 9.999983E19, 9.999979E19," +
                " 9.999976E19, 9.999986E19, 9.9999685E19, 9.999965E19, 9.9999615E19, 9.999995E19, 9.9999545E19," +
                " 9.999951E19, 9.999947E19, 9.999999E19, 9.99994E19, 9.999937E19, ...]");
        rangeDown_float_helper(-1.0f,
                "[-Infinity, -1.0, -2.0, -3.0, -4.0, -1.5, -6.0, -7.0, -8.0, -5.0, -10.0, -11.0, -12.0, -2.5, -14.0," +
                " -15.0, -16.0, -1.25, -18.0, -19.0, ...]");
        rangeDown_float_helper(-1.0E20f,
                "[-Infinity, -1.0E20, -1.0000004E20, -1.0000007E20, -1.0000011E20, -1.0000002E20, -1.0000018E20," +
                " -1.0000021E20, -1.0000025E20, -1.0000014E20, -1.0000032E20, -1.0000035E20, -1.0000039E20," +
                " -1.00000055E20, -1.0000046E20, -1.00000495E20, -1.0000053E20, -1.0000001E20, -1.000006E20," +
                " -1.00000635E20, ...]");
        rangeDown_float_helper((float) Math.PI,
                "[-Infinity, 3.1415927, 3.1415925, 3.1415923, 3.141592, 3.1415915, 3.1415913, 3.141591, 3.1415918," +
                " 3.1415906, 3.1415904, 3.14159, 3.1415896, 3.1415894, 3.1415892, 3.1415887, 3.1415884, 3.1415882," +
                " 3.1415877, 3.1415875, ...]");
        rangeDown_float_helper((float) Math.sqrt(2),
                "[-Infinity, 1.4142135, 1.4142134, 1.4142133, 1.4142132, 1.414213, 1.4142128, 1.4142127, 1.4142131," +
                " 1.4142125, 1.4142123, 1.4142122, 1.414212, 1.4142119, 1.4142118, 1.4142115, 1.4142114, 1.4142113," +
                " 1.414211, 1.4142109, ...]");
        rangeDown_float_helper((float) -Math.PI,
                "[-Infinity, -3.1415927, -3.141593, -3.1415932, -3.1415935, -3.141594, -3.1415942, -3.1415944," +
                " -3.1415937, -3.141595, -3.1415951, -3.1415954, -3.1415958, -3.141596, -3.1415963, -3.1415968," +
                " -3.141597, -3.1415973, -3.1415977, -3.141598, ...]");
        rangeDown_float_helper((float) -Math.sqrt(2),
                "[-Infinity, -1.4142135, -1.4142137, -1.4142138, -1.4142139, -1.4142141, -1.4142143, -1.4142144," +
                " -1.414214, -1.4142146, -1.4142147, -1.4142148, -1.4142151, -1.4142152, -1.4142153, -1.4142156," +
                " -1.4142157, -1.4142158, -1.414216, -1.4142162, ...]");
        rangeDown_float_helper(0.0f,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0, ...]");
        rangeDown_float_helper(-0.0f,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0, ...]");
        rangeDown_float_helper(Float.MIN_VALUE,
                "[-Infinity, 1.4E-45, -0.0, 0.0, -1.4E-45, -2.8E-45, -5.6E-45, -7.0E-45, -8.4E-45, -4.2E-45," +
                " -1.1E-44, -1.3E-44, -1.4E-44, -1.7E-44, -1.8E-44, -2.0E-44, -2.24E-44, -2.4E-44, -2.5E-44," +
                " -2.8E-44, ...]");
        rangeDown_float_helper(Float.MIN_NORMAL,
                "[-Infinity, 1.17549435E-38, -0.0, 0.0, -1.17549435E-38, -2.3509887E-38, 5.877472E-39," +
                " -4.7019774E-38, -5.877472E-38, -7.052966E-38, -3.526483E-38, -9.403955E-38, -1.0579449E-37," +
                " -1.1754944E-37, -5.877472E-39, -1.4105932E-37, -1.5281427E-37, -1.6456921E-37, 8.816208E-39," +
                " -1.880791E-37, ...]");
        rangeDown_float_helper(-Float.MIN_VALUE,
                "[-Infinity, -1.4E-45, -2.8E-45, -4.2E-45, -5.6E-45, -8.4E-45, -9.8E-45, -1.1E-44, -7.0E-45," +
                " -1.4E-44, -1.5E-44, -1.7E-44, -2.0E-44, -2.1E-44, -2.24E-44, -2.5E-44, -2.7E-44, -2.8E-44," +
                " -3.1E-44, -3.2E-44, ...]");
        rangeDown_float_helper(-Float.MIN_NORMAL,
                "[-Infinity, -1.17549435E-38, -2.3509887E-38, -3.526483E-38, -4.7019774E-38, -1.7632415E-38," +
                " -7.052966E-38, -8.2284605E-38, -9.403955E-38, -5.877472E-38, -1.1754944E-37, -1.2930438E-37," +
                " -1.4105932E-37, -2.938736E-38, -1.6456921E-37, -1.7632415E-37, -1.880791E-37, -1.469368E-38," +
                " -2.1158898E-37, -2.2334393E-37, ...]");
        rangeDown_float_helper(Float.MAX_VALUE,
                "[-Infinity, 3.4028235E38, 3.4028233E38, 3.402823E38, 3.4028229E38, 3.4028225E38, 3.4028222E38," +
                " 3.402822E38, 3.4028227E38, 3.4028216E38, 3.4028214E38, 3.4028212E38, 3.4028208E38, 3.4028206E38," +
                " 3.4028204E38, 3.40282E38, 3.4028198E38, 3.4028196E38, 3.4028192E38, 3.402819E38, ...]");
        rangeDown_float_helper(-Float.MAX_VALUE, "[-Infinity, -3.4028235E38]");
        rangeDown_float_helper(Float.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0, ...]");
        rangeDown_float_helper(Float.NEGATIVE_INFINITY, "[-Infinity]");
        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void range_float_float_helper(float a, float b, @NotNull String output) {
        simpleProviderHelper(P.range(a, b), output);
    }

    private static void range_float_float_fail_helper(float a, float b) {
        try {
            P.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_float_float() {
        range_float_float_helper(1.0f, 2.0f,
                "[1.0, 2.0, 1.5, 1.25, 1.75, 1.125, 1.375, 1.625, 1.875, 1.0625, 1.1875, 1.3125, 1.4375, 1.5625," +
                " 1.6875, 1.8125, 1.9375, 1.03125, 1.09375, 1.15625, ...]");
        range_float_float_helper(1.0f, 3.0f,
                "[1.0, 3.0, 2.0, 1.5, 2.5, 1.25, 1.75, 2.25, 2.75, 1.125, 1.375, 1.625, 1.875, 2.125, 2.375, 2.625," +
                " 2.875, 1.0625, 1.1875, 1.3125, ...]");
        range_float_float_helper(1.0f, 4.0f,
                "[1.0, 2.0, 3.0, 4.0, 1.5, 2.5, 3.5, 1.25, 2.25, 3.25, 1.75, 2.75, 3.75, 1.125, 2.125, 3.125, 1.375," +
                " 2.375, 3.375, 1.625, ...]");
        range_float_float_helper(1.0f, 257.0f,
                "[1.0, 257.0, 129.0, 65.0, 193.0, 33.0, 97.0, 161.0, 225.0, 17.0, 49.0, 81.0, 113.0, 145.0, 177.0," +
                " 209.0, 241.0, 9.0, 25.0, 41.0, ...]");
        range_float_float_helper(-257.0f, -1.0f,
                "[-257.0, -1.0, -129.0, -193.0, -65.0, -225.0, -161.0, -97.0, -33.0, -241.0, -209.0, -177.0, -145.0," +
                " -113.0, -81.0, -49.0, -17.0, -249.0, -233.0, -217.0, ...]");
        range_float_float_helper(1.0f, 1.0E20f,
                "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0," +
                " 19.0, 20.0, ...]");
        range_float_float_helper(-1.0E20f, -1.0f,
                "[-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0, -9.0, -10.0, -11.0, -12.0, -13.0, -14.0, -15.0," +
                " -16.0, -17.0, -18.0, -19.0, -20.0, ...]");
        range_float_float_helper((float) Math.sqrt(2), (float) Math.PI,
                "[1.4142135, 1.4142137, 1.4142138, 1.4142139, 1.414214, 1.4142141, 1.4142143, 1.4142144, 1.4142145," +
                " 1.4142146, 1.4142147, 1.4142148, 1.414215, 1.4142151, 1.4142152, 1.4142153, 1.4142154, 1.4142156," +
                " 1.4142157, 1.4142158, ...]");
        range_float_float_helper((float) Math.PI, FloatingPointUtils.successor((float) Math.PI),
                "[3.1415927, 3.141593]");
        range_float_float_helper(0.0f, 1.0f,
                "[0.0, -0.0, 1.0, 0.5, 0.25, 0.75, 0.125, 0.375, 0.625, 0.875, 0.0625, 0.1875, 0.3125, 0.4375," +
                " 0.5625, 0.6875, 0.8125, 0.9375, 0.03125, 0.09375, ...]");
        range_float_float_helper(-1.0f, 1.0f,
                "[-1.0, 1.0, 0.0, -0.0, -0.5, 0.5, -0.75, -0.25, 0.25, 0.75, -0.875, -0.625, -0.375, -0.125, 0.125," +
                " 0.375, 0.625, 0.875, -0.9375, -0.8125, ...]");
        range_float_float_helper(1.0f, 1.0f, "[1.0]");
        range_float_float_helper((float) Math.PI, (float) Math.PI, "[3.1415927]");
        range_float_float_helper((float) -Math.PI, (float) Math.PI,
                "[-3.1415927, -3.1415923, -3.1415918, -3.1415913, -3.1415908, -3.1415904, -3.1415899, -3.1415894," +
                " -3.141589, -3.1415884, -3.141588, -3.1415875, -3.141587, -3.1415865, -3.141586, -3.1415856," +
                " -3.141585, -3.1415846, -3.1415842, -3.1415837, ...]");
        range_float_float_helper(1.0f, Float.POSITIVE_INFINITY,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0, ...]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, 1.0f,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0, ...]");
        range_float_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY, "[Infinity, 3.4028235E38]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE, "[-Infinity, -3.4028235E38]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0, ...]");
        range_float_float_helper(0.0f, 0.0f, "[0.0, -0.0]");
        range_float_float_helper(-0.0f, -0.0f, "[0.0, -0.0]");
        range_float_float_helper(-0.0f, 0.0f, "[0.0, -0.0]");
        range_float_float_helper(0.0f, -0.0f, "[0.0, -0.0]");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, "[Infinity]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, "[-Infinity]");
        range_float_float_helper(1.0f, -1.0f, "[]");
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, "[]");
        range_float_float_helper(Float.POSITIVE_INFINITY, 1.0f, "[]");
        range_float_float_helper(1.0f, Float.NEGATIVE_INFINITY, "[]");
        range_float_float_fail_helper(Float.NaN, 1.0f);
        range_float_float_fail_helper(Float.NaN, Float.POSITIVE_INFINITY);
        range_float_float_fail_helper(Float.NaN, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(1.0f, Float.NaN);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NEGATIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NaN, Float.NaN);
    }

    private static void rangeUp_double_helper(double a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(a), output);
    }

    private static void rangeUp_double_fail_helper(double a) {
        try {
            P.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_double() {
        rangeUp_double_helper(1.0,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0, ...]");
        rangeUp_double_helper(1.0E20,
                "[Infinity, 1.0E20, 1.0000000000000105E20, 1.000000000000021E20, 1.0000000000000315E20," +
                " 1.0000000000000052E20, 1.0000000000000524E20, 1.0000000000000629E20, 1.0000000000000734E20," +
                " 1.000000000000042E20, 1.0000000000000944E20, 1.0000000000001049E20, 1.0000000000001153E20," +
                " 1.0000000000000157E20, 1.0000000000001363E20, 1.0000000000001468E20, 1.0000000000001573E20," +
                " 1.0000000000000026E20, 1.0000000000001783E20, 1.0000000000001887E20, ...]");
        rangeUp_double_helper(-1.0,
                "[Infinity, -1.0, 0.0, -0.0, 1.0, 2.0, -0.5, 4.0, 5.0, 6.0, 3.0, 8.0, 9.0, 10.0, 0.5, 12.0, 13.0," +
                " 14.0, -0.75, 16.0, ...]");
        rangeUp_double_helper(-1.0E20,
                "[Infinity, -1.0E20, -9.999999999999895E19, -9.99999999999979E19, -9.999999999999685E19," +
                " -9.999999999999948E19, -9.999999999999476E19, -9.999999999999371E19, -9.999999999999266E19," +
                " -9.99999999999958E19, -9.999999999999056E19, -9.999999999998951E19, -9.999999999998847E19," +
                " -9.999999999999843E19, -9.999999999998637E19, -9.999999999998532E19, -9.999999999998427E19," +
                " -9.999999999999974E19, -9.999999999998217E19, -9.999999999998113E19, ...]");
        rangeUp_double_helper(Math.PI,
                "[Infinity, 3.141592653589793, 3.1415926535897967, 3.1415926535898, 3.1415926535898038," +
                " 3.141592653589795, 3.141592653589811, 3.1415926535898144, 3.141592653589818, 3.1415926535898073," +
                " 3.141592653589825, 3.1415926535898286, 3.141592653589832, 3.1415926535897984, 3.1415926535898393," +
                " 3.141592653589843, 3.1415926535898464, 3.141592653589794, 3.1415926535898535, 3.141592653589857," +
                " ...]");
        rangeUp_double_helper(Math.sqrt(2),
                "[Infinity, 1.4142135623730951, 1.4142135623730954, 1.4142135623730956, 1.4142135623730958," +
                " 1.4142135623730963, 1.4142135623730965, 1.4142135623730967, 1.414213562373096, 1.4142135623730971," +
                " 1.4142135623730974, 1.4142135623730976, 1.414213562373098, 1.4142135623730983, 1.4142135623730985," +
                " 1.414213562373099, 1.4142135623730991, 1.4142135623730994, 1.4142135623730998, 1.4142135623731," +
                " ...]");
        rangeUp_double_helper(-Math.PI,
                "[Infinity, -3.141592653589793, -3.1415926535897896, -3.141592653589786, -3.1415926535897825," +
                " -3.1415926535897913, -3.1415926535897754, -3.141592653589772, -3.1415926535897682," +
                " -3.141592653589779, -3.141592653589761, -3.1415926535897576, -3.141592653589754," +
                " -3.141592653589788, -3.141592653589747, -3.1415926535897434, -3.14159265358974," +
                " -3.1415926535897922, -3.1415926535897327, -3.141592653589729, ...]");
        rangeUp_double_helper(-Math.sqrt(2),
                "[Infinity, -1.4142135623730951, -1.414213562373095, -1.4142135623730947, -1.4142135623730945," +
                " -1.414213562373094, -1.4142135623730938, -1.4142135623730936, -1.4142135623730943," +
                " -1.4142135623730931, -1.414213562373093, -1.4142135623730927, -1.4142135623730923," +
                " -1.414213562373092, -1.4142135623730918, -1.4142135623730914, -1.4142135623730911," +
                " -1.414213562373091, -1.4142135623730905, -1.4142135623730903, ...]");
        rangeUp_double_helper(0.0,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0, ...]");
        rangeUp_double_helper(-0.0,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0, ...]");
        rangeUp_double_helper(Double.MIN_VALUE,
                "[Infinity, 4.9E-324, 1.0E-323, 1.5E-323, 2.0E-323, 3.0E-323, 3.5E-323, 4.0E-323, 2.5E-323," +
                " 4.9E-323, 5.4E-323, 5.9E-323, 6.9E-323, 7.4E-323, 7.9E-323, 8.9E-323, 9.4E-323, 1.0E-322," +
                " 1.1E-322, 1.14E-322, ...]");
        rangeUp_double_helper(Double.MIN_NORMAL,
                "[Infinity, 2.2250738585072014E-308, 4.450147717014403E-308, 6.675221575521604E-308," +
                " 8.900295434028806E-308, 3.337610787760802E-308, 1.3350443151043208E-307, 1.557551700955041E-307," +
                " 1.7800590868057611E-307, 1.1125369292536007E-307, 2.2250738585072014E-307," +
                " 2.4475812443579215E-307, 2.6700886302086417E-307, 5.562684646268003E-308, 3.115103401910082E-307," +
                " 3.337610787760802E-307, 3.5601181736115222E-307, 2.7813423231340017E-308, 4.0051329453129625E-307," +
                " 4.227640331163683E-307, ...]");
        rangeUp_double_helper(-Double.MIN_VALUE,
                "[Infinity, -4.9E-324, 0.0, -0.0, 4.9E-324, 1.0E-323, 2.0E-323, 2.5E-323, 3.0E-323, 1.5E-323," +
                " 4.0E-323, 4.4E-323, 4.9E-323, 5.9E-323, 6.4E-323, 6.9E-323, 7.9E-323, 8.4E-323, 8.9E-323," +
                " 1.0E-322, ...]");
        rangeUp_double_helper(-Double.MIN_NORMAL,
                "[Infinity, -2.2250738585072014E-308, 0.0, -0.0, 2.2250738585072014E-308, 4.450147717014403E-308," +
                " -1.1125369292536007E-308, 8.900295434028806E-308, 1.1125369292536007E-307," +
                " 1.3350443151043208E-307, 6.675221575521604E-308, 1.7800590868057611E-307, 2.0025664726564812E-307," +
                " 2.2250738585072014E-307, 1.1125369292536007E-308, 2.6700886302086417E-307," +
                " 2.8925960160593618E-307, 3.115103401910082E-307, -1.668805393880401E-308, 3.5601181736115222E-307," +
                " ...]");
        rangeUp_double_helper(-Double.MAX_VALUE,
                "[Infinity, -1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                " -1.7976931348623151E308, -1.7976931348623147E308, -1.7976931348623145E308," +
                " -1.7976931348623143E308, -1.797693134862315E308, -1.797693134862314E308, -1.7976931348623137E308," +
                " -1.7976931348623135E308, -1.7976931348623131E308, -1.797693134862313E308, -1.7976931348623127E308," +
                " -1.7976931348623123E308, -1.7976931348623121E308, -1.797693134862312E308, -1.7976931348623115E308," +
                " -1.7976931348623113E308, ...]");
        rangeUp_double_helper(Double.POSITIVE_INFINITY, "[Infinity]");
        rangeUp_double_helper(Double.NEGATIVE_INFINITY,
                "[Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0," +
                " 1.5, -1.5, 12.0, -12.0, ...]");
        rangeUp_double_fail_helper(Double.NaN);
    }

    private static void rangeDown_double_helper(double a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(a), output);
    }

    private static void rangeDown_double_fail_helper(double a) {
        try {
            P.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_double() {
        rangeDown_double_helper(1.0,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0, ...]");
        rangeDown_double_helper(1.0E20,
                "[-Infinity, 1.0E20, 9.999999999999895E19, 9.99999999999979E19, 9.999999999999685E19," +
                " 9.999999999999948E19, 9.999999999999476E19, 9.999999999999371E19, 9.999999999999266E19," +
                " 9.99999999999958E19, 9.999999999999056E19, 9.999999999998951E19, 9.999999999998847E19," +
                " 9.999999999999843E19, 9.999999999998637E19, 9.999999999998532E19, 9.999999999998427E19," +
                " 9.999999999999974E19, 9.999999999998217E19, 9.999999999998113E19, ...]");
        rangeDown_double_helper(-1.0,
                "[-Infinity, -1.0, -2.0, -3.0, -4.0, -1.5, -6.0, -7.0, -8.0, -5.0, -10.0, -11.0, -12.0, -2.5, -14.0," +
                " -15.0, -16.0, -1.25, -18.0, -19.0, ...]");
        rangeDown_double_helper(-1.0E20,
                "[-Infinity, -1.0E20, -1.0000000000000105E20, -1.000000000000021E20, -1.0000000000000315E20," +
                " -1.0000000000000052E20, -1.0000000000000524E20, -1.0000000000000629E20, -1.0000000000000734E20," +
                " -1.000000000000042E20, -1.0000000000000944E20, -1.0000000000001049E20, -1.0000000000001153E20," +
                " -1.0000000000000157E20, -1.0000000000001363E20, -1.0000000000001468E20, -1.0000000000001573E20," +
                " -1.0000000000000026E20, -1.0000000000001783E20, -1.0000000000001887E20, ...]");
        rangeDown_double_helper(Math.PI,
                "[-Infinity, 3.141592653589793, 3.1415926535897896, 3.141592653589786, 3.1415926535897825," +
                " 3.1415926535897913, 3.1415926535897754, 3.141592653589772, 3.1415926535897682, 3.141592653589779," +
                " 3.141592653589761, 3.1415926535897576, 3.141592653589754, 3.141592653589788, 3.141592653589747," +
                " 3.1415926535897434, 3.14159265358974, 3.1415926535897922, 3.1415926535897327, 3.141592653589729," +
                " ...]");
        rangeDown_double_helper(Math.sqrt(2),
                "[-Infinity, 1.4142135623730951, 1.414213562373095, 1.4142135623730947, 1.4142135623730945," +
                " 1.414213562373094, 1.4142135623730938, 1.4142135623730936, 1.4142135623730943, 1.4142135623730931," +
                " 1.414213562373093, 1.4142135623730927, 1.4142135623730923, 1.414213562373092, 1.4142135623730918," +
                " 1.4142135623730914, 1.4142135623730911, 1.414213562373091, 1.4142135623730905, 1.4142135623730903," +
                " ...]");
        rangeDown_double_helper(-Math.PI,
                "[-Infinity, -3.141592653589793, -3.1415926535897967, -3.1415926535898, -3.1415926535898038," +
                " -3.141592653589795, -3.141592653589811, -3.1415926535898144, -3.141592653589818," +
                " -3.1415926535898073, -3.141592653589825, -3.1415926535898286, -3.141592653589832," +
                " -3.1415926535897984, -3.1415926535898393, -3.141592653589843, -3.1415926535898464," +
                " -3.141592653589794, -3.1415926535898535, -3.141592653589857, ...]");
        rangeDown_double_helper(-Math.sqrt(2),
                "[-Infinity, -1.4142135623730951, -1.4142135623730954, -1.4142135623730956, -1.4142135623730958," +
                " -1.4142135623730963, -1.4142135623730965, -1.4142135623730967, -1.414213562373096," +
                " -1.4142135623730971, -1.4142135623730974, -1.4142135623730976, -1.414213562373098," +
                " -1.4142135623730983, -1.4142135623730985, -1.414213562373099, -1.4142135623730991," +
                " -1.4142135623730994, -1.4142135623730998, -1.4142135623731, ...]");
        rangeDown_double_helper(0.0,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0, ...]");
        rangeDown_double_helper(-0.0,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0, ...]");
        rangeDown_double_helper(Double.MIN_VALUE,
                "[-Infinity, 4.9E-324, -0.0, 0.0, -4.9E-324, -1.0E-323, -2.0E-323, -2.5E-323, -3.0E-323, -1.5E-323," +
                " -4.0E-323, -4.4E-323, -4.9E-323, -5.9E-323, -6.4E-323, -6.9E-323, -7.9E-323, -8.4E-323, -8.9E-323," +
                " -1.0E-322, ...]");
        rangeDown_double_helper(Double.MIN_NORMAL,
                "[-Infinity, 2.2250738585072014E-308, -0.0, 0.0, -2.2250738585072014E-308, -4.450147717014403E-308," +
                " 1.1125369292536007E-308, -8.900295434028806E-308, -1.1125369292536007E-307," +
                " -1.3350443151043208E-307, -6.675221575521604E-308, -1.7800590868057611E-307," +
                " -2.0025664726564812E-307, -2.2250738585072014E-307, -1.1125369292536007E-308," +
                " -2.6700886302086417E-307, -2.8925960160593618E-307, -3.115103401910082E-307," +
                " 1.668805393880401E-308, -3.5601181736115222E-307, ...]");
        rangeDown_double_helper(-Double.MIN_VALUE,
                "[-Infinity, -4.9E-324, -1.0E-323, -1.5E-323, -2.0E-323, -3.0E-323, -3.5E-323, -4.0E-323, -2.5E-323," +
                " -4.9E-323, -5.4E-323, -5.9E-323, -6.9E-323, -7.4E-323, -7.9E-323, -8.9E-323, -9.4E-323, -1.0E-322," +
                " -1.1E-322, -1.14E-322, ...]");
        rangeDown_double_helper(-Double.MIN_NORMAL,
                "[-Infinity, -2.2250738585072014E-308, -4.450147717014403E-308, -6.675221575521604E-308," +
                " -8.900295434028806E-308, -3.337610787760802E-308, -1.3350443151043208E-307," +
                " -1.557551700955041E-307, -1.7800590868057611E-307, -1.1125369292536007E-307," +
                " -2.2250738585072014E-307, -2.4475812443579215E-307, -2.6700886302086417E-307," +
                " -5.562684646268003E-308, -3.115103401910082E-307, -3.337610787760802E-307," +
                " -3.5601181736115222E-307, -2.7813423231340017E-308, -4.0051329453129625E-307," +
                " -4.227640331163683E-307, ...]");
        rangeDown_double_helper(Double.MAX_VALUE,
                "[-Infinity, 1.7976931348623157E308, 1.7976931348623155E308, 1.7976931348623153E308," +
                " 1.7976931348623151E308, 1.7976931348623147E308, 1.7976931348623145E308, 1.7976931348623143E308," +
                " 1.797693134862315E308, 1.797693134862314E308, 1.7976931348623137E308, 1.7976931348623135E308," +
                " 1.7976931348623131E308, 1.797693134862313E308, 1.7976931348623127E308, 1.7976931348623123E308," +
                " 1.7976931348623121E308, 1.797693134862312E308, 1.7976931348623115E308, 1.7976931348623113E308," +
                " ...]");
        rangeDown_double_helper(-Double.MAX_VALUE, "[-Infinity, -1.7976931348623157E308]");
        rangeDown_double_helper(Double.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0, ...]");
        rangeDown_double_helper(Double.NEGATIVE_INFINITY, "[-Infinity]");
        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void range_double_double_helper(double a, double b, @NotNull String output) {
        simpleProviderHelper(P.range(a, b), output);
    }

    private static void range_double_double_fail_helper(double a, double b) {
        try {
            P.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_double_double() {
        range_double_double_helper(1.0, 2.0,
                "[1.0, 2.0, 1.5, 1.25, 1.75, 1.125, 1.375, 1.625, 1.875, 1.0625, 1.1875, 1.3125, 1.4375, 1.5625," +
                " 1.6875, 1.8125, 1.9375, 1.03125, 1.09375, 1.15625, ...]");
        range_double_double_helper(1.0, 3.0,
                "[1.0, 3.0, 2.0, 1.5, 2.5, 1.25, 1.75, 2.25, 2.75, 1.125, 1.375, 1.625, 1.875, 2.125, 2.375, 2.625," +
                " 2.875, 1.0625, 1.1875, 1.3125, ...]");
        range_double_double_helper(1.0, 4.0,
                "[1.0, 2.0, 3.0, 4.0, 1.5, 2.5, 3.5, 1.25, 2.25, 3.25, 1.75, 2.75, 3.75, 1.125, 2.125, 3.125, 1.375," +
                " 2.375, 3.375, 1.625, ...]");
        range_double_double_helper(1.0, 257.0,
                "[1.0, 257.0, 129.0, 65.0, 193.0, 33.0, 97.0, 161.0, 225.0, 17.0, 49.0, 81.0, 113.0, 145.0, 177.0," +
                " 209.0, 241.0, 9.0, 25.0, 41.0, ...]");
        range_double_double_helper(-257.0, -1.0,
                "[-257.0, -1.0, -129.0, -193.0, -65.0, -225.0, -161.0, -97.0, -33.0, -241.0, -209.0, -177.0, -145.0," +
                " -113.0, -81.0, -49.0, -17.0, -249.0, -233.0, -217.0, ...]");
        range_double_double_helper(1.0, 1.0E20,
                "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0," +
                " 19.0, 20.0, ...]");
        range_double_double_helper(-1.0E20, -1.0,
                "[-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0, -9.0, -10.0, -11.0, -12.0, -13.0, -14.0, -15.0," +
                " -16.0, -17.0, -18.0, -19.0, -20.0, ...]");
        range_double_double_helper(Math.sqrt(2), Math.PI,
                "[1.4142135623730951, 1.4142135623730954, 1.4142135623730956, 1.4142135623730958, 1.414213562373096," +
                " 1.4142135623730963, 1.4142135623730965, 1.4142135623730967, 1.414213562373097, 1.4142135623730971," +
                " 1.4142135623730974, 1.4142135623730976, 1.4142135623730978, 1.414213562373098, 1.4142135623730983," +
                " 1.4142135623730985, 1.4142135623730987, 1.414213562373099, 1.4142135623730991, 1.4142135623730994," +
                " ...]");
        range_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI),
                "[3.1415926535897936, 3.141592653589793]");
        range_double_double_helper(0.0, 1.0,
                "[0.0, -0.0, 1.0, 0.5, 0.25, 0.75, 0.125, 0.375, 0.625, 0.875, 0.0625, 0.1875, 0.3125, 0.4375," +
                " 0.5625, 0.6875, 0.8125, 0.9375, 0.03125, 0.09375, ...]");
        range_double_double_helper(-1.0, 1.0,
                "[-1.0, 1.0, 0.0, -0.0, -0.5, 0.5, -0.75, -0.25, 0.25, 0.75, -0.875, -0.625, -0.375, -0.125, 0.125," +
                " 0.375, 0.625, 0.875, -0.9375, -0.8125, ...]");
        range_double_double_helper(1.0, 1.0, "[1.0]");
        range_double_double_helper(Math.PI, Math.PI, "[3.141592653589793]");
        range_double_double_helper(-Math.PI, Math.PI,
                "[-3.141592653589793, -3.141592653589786, -3.141592653589779, -3.141592653589772," +
                " -3.1415926535897647, -3.1415926535897576, -3.1415926535897505, -3.1415926535897434," +
                " -3.1415926535897363, -3.141592653589729, -3.141592653589722, -3.141592653589715," +
                " -3.141592653589708, -3.1415926535897007, -3.1415926535896936, -3.1415926535896865," +
                " -3.1415926535896794, -3.1415926535896723, -3.141592653589665, -3.141592653589658, ...]");
        range_double_double_helper(1.0, Double.POSITIVE_INFINITY,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0, ...]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, 1.0,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0, ...]");
        range_double_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY, "[Infinity, 1.7976931348623157E308]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE,
                "[-Infinity, -1.7976931348623157E308]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0, ...]");
        range_double_double_helper(0.0, 0.0, "[0.0, -0.0]");
        range_double_double_helper(-0.0, -0.0, "[0.0, -0.0]");
        range_double_double_helper(-0.0, 0.0, "[0.0, -0.0]");
        range_double_double_helper(0.0, -0.0, "[0.0, -0.0]");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, "[Infinity]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, "[-Infinity]");
        range_double_double_helper(1.0, -1.0, "[]");
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, "[]");
        range_double_double_helper(Double.POSITIVE_INFINITY, 1.0, "[]");
        range_double_double_helper(1.0, Double.NEGATIVE_INFINITY, "[]");
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
        aeqitLimit(SMALL_LIMIT, P.positiveBigDecimals(),
                "[1, 0.1, 2, 1E+1, 3, 0.2, 4, 0.01, 5, 0.3, 6, 2E+1, 7, 0.4, 8, 1E+2, 9, 0.5, 10, 3E+1, 11, 0.6, 12," +
                " 0.02, 13, 0.7, 14, 4E+1, 15, 0.8, 16, 0.001, 17, 0.9, 18, 5E+1, 19, 1.0, 20, 0.03, 21, 1.1, 22," +
                " 6E+1, 23, 1.2, 24, 2E+2, 25, 1.3, ...]");
    }

    @Test
    public void testNegativeBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.negativeBigDecimals(),
                "[-1, -0.1, -2, -1E+1, -3, -0.2, -4, -0.01, -5, -0.3, -6, -2E+1, -7, -0.4, -8, -1E+2, -9, -0.5, -10," +
                " -3E+1, -11, -0.6, -12, -0.02, -13, -0.7, -14, -4E+1, -15, -0.8, -16, -0.001, -17, -0.9, -18," +
                " -5E+1, -19, -1.0, -20, -0.03, -21, -1.1, -22, -6E+1, -23, -1.2, -24, -2E+2, -25, -1.3, ...]");
    }

    @Test
    public void testNonzeroBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.nonzeroBigDecimals(),
                "[1, 0.1, -1, 1E+1, 2, -0.1, -2, 0.01, 3, 0.2, -3, -1E+1, 4, -0.2, -4, 1E+2, 5, 0.3, -5, 2E+1, 6," +
                " -0.3, -6, -0.01, 7, 0.4, -7, -2E+1, 8, -0.4, -8, 0.001, 9, 0.5, -9, 3E+1, 10, -0.5, -10, 0.02, 11," +
                " 0.6, -11, -3E+1, 12, -0.6, -12, -1E+2, 13, 0.7, ...]");
    }

    @Test
    public void testBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.bigDecimals(),
                "[0, 0.0, 1, 0E+1, -1, 0.1, 2, 0.00, -2, -0.1, 3, 1E+1, -3, 0.2, 4, 0E+2, -4, -0.2, 5, -1E+1, -5," +
                " 0.3, 6, 0.01, -6, -0.3, 7, 2E+1, -7, 0.4, 8, 0.000, -8, -0.4, 9, -2E+1, -9, 0.5, 10, -0.01, -10," +
                " -0.5, 11, 3E+1, -11, 0.6, 12, 1E+2, -12, -0.6, ...]");
    }

    @Test
    public void testPositiveCanonicalBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.positiveCanonicalBigDecimals(),
                "[1, 0.1, 2, 3, 0.2, 4, 0.01, 5, 0.3, 6, 7, 0.4, 8, 9, 0.5, 10, 11, 0.6, 12, 0.02, 13, 0.7, 14, 15," +
                " 0.8, 16, 0.001, 17, 0.9, 18, 19, 20, 0.03, 21, 1.1, 22, 23, 1.2, 24, 25, 1.3, 26, 27, 1.4, 28," +
                " 0.04, 29, 1.5, 30, 31, ...]");
    }

    @Test
    public void testNegativeCanonicalBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.negativeCanonicalBigDecimals(),
                "[-1, -0.1, -2, -3, -0.2, -4, -0.01, -5, -0.3, -6, -7, -0.4, -8, -9, -0.5, -10, -11, -0.6, -12," +
                " -0.02, -13, -0.7, -14, -15, -0.8, -16, -0.001, -17, -0.9, -18, -19, -20, -0.03, -21, -1.1, -22," +
                " -23, -1.2, -24, -25, -1.3, -26, -27, -1.4, -28, -0.04, -29, -1.5, -30, -31, ...]");
    }

    @Test
    public void testNonzeroCanonicalBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.nonzeroCanonicalBigDecimals(),
                "[1, 0.1, -1, 2, -0.1, -2, 0.01, 3, 0.2, -3, 4, -0.2, -4, 5, 0.3, -5, 6, -0.3, -6, -0.01, 7, 0.4," +
                " -7, 8, -0.4, -8, 0.001, 9, 0.5, -9, 10, -0.5, -10, 0.02, 11, 0.6, -11, 12, -0.6, -12, 13, 0.7," +
                " -13, 14, -0.7, -14, -0.02, 15, 0.8, -15, ...]");
    }

    @Test
    public void testCanonicalBigDecimals() {
        aeqitLimit(SMALL_LIMIT, P.canonicalBigDecimals(),
                "[0, 1, -1, 0.1, 2, -2, -0.1, 3, -3, 0.2, 4, -4, -0.2, 5, -5, 0.3, 6, 0.01, -6, -0.3, 7, -7, 0.4, 8," +
                " -8, -0.4, 9, -9, 0.5, 10, -0.01, -10, -0.5, 11, -11, 0.6, 12, -12, -0.6, 13, -13, 0.7, 14, 0.02," +
                " -14, -0.7, 15, -15, 0.8, 16, ...]");
    }

    private static void rangeUp_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeUp(Readers.readBigDecimal(a).get()), output);
    }

    @Test
    public void testRangeUp_BigDecimal() {
        rangeUp_BigDecimal_helper("0",
                "[0, 0.0, 1, 0E+1, 0.1, 1.0, 2, 0.00, 3, 0.10, 0.2, 1.00, 4, 2.0, 0.01, 0E+2, 5, 3.0, 0.3, 0.100," +
                " ...]");
        rangeUp_BigDecimal_helper("0.0",
                "[0, 0.0, 1, 0E+1, 0.1, 1.0, 2, 0.00, 3, 0.10, 0.2, 1.00, 4, 2.0, 0.01, 0E+2, 5, 3.0, 0.3, 0.100," +
                " ...]");
        rangeUp_BigDecimal_helper("1",
                "[1, 1.0, 2, 1.00, 1.1, 2.0, 3, 1.000, 4, 1.10, 1.2, 2.00, 5, 3.0, 1.01, 1.0000, 6, 4.0, 1.3, 1.100," +
                " ...]");
        rangeUp_BigDecimal_helper("1.0",
                "[1, 1.0, 2, 1.00, 1.1, 2.0, 3, 1.000, 4, 1.10, 1.2, 2.00, 5, 3.0, 1.01, 1.0000, 6, 4.0, 1.3, 1.100," +
                " ...]");
        rangeUp_BigDecimal_helper("-1",
                "[-1, -1.0, 0, -1.00, -0.9, 0.0, 1, -1.000, 2, -0.90, -0.8, 0E+1, 3, 1.0, -0.99, -1.0000, 4, 2.0," +
                " -0.7, -0.900, ...]");
        rangeUp_BigDecimal_helper("-1.0",
                "[-1, -1.0, 0, -1.00, -0.9, 0.0, 1, -1.000, 2, -0.90, -0.8, 0E+1, 3, 1.0, -0.99, -1.0000, 4, 2.0," +
                " -0.7, -0.900, ...]");
        rangeUp_BigDecimal_helper("9",
                "[9, 9.0, 1E+1, 9.00, 9.1, 10, 11, 9.000, 12, 9.10, 9.2, 10.0, 13, 11.0, 9.01, 9.0000, 14, 12.0," +
                " 9.3, 9.100, ...]");
        rangeUp_BigDecimal_helper("-9",
                "[-9, -9.0, -8, -9.00, -8.9, -8.0, -7, -9.000, -6, -8.90, -8.8, -8.00, -5, -7.0, -8.99, -9.0000, -4," +
                " -6.0, -8.7, -8.900, ...]");
        rangeUp_BigDecimal_helper("10",
                "[1E+1, 10, 11, 10.0, 10.1, 11.0, 12, 10.00, 13, 10.10, 10.2, 11.00, 14, 12.0, 10.01, 10.000, 15," +
                " 13.0, 10.3, 10.100, ...]");
        rangeUp_BigDecimal_helper("-10",
                "[-1E+1, -10, -9, -10.0, -9.9, -9.0, -8, -10.00, -7, -9.90, -9.8, -9.00, -6, -8.0, -9.99, -10.000," +
                " -5, -7.0, -9.7, -9.900, ...]");
        rangeUp_BigDecimal_helper("101",
                "[101, 101.0, 102, 101.00, 101.1, 102.0, 103, 101.000, 104, 101.10, 101.2, 102.00, 105, 103.0," +
                " 101.01, 101.0000, 106, 104.0, 101.3, 101.100, ...]");
        rangeUp_BigDecimal_helper("-101",
                "[-101, -101.0, -1E+2, -101.00, -100.9, -1.0E+2, -99, -101.000, -98, -100.90, -100.8, -100, -97," +
                " -99.0, -100.99, -101.0000, -96, -98.0, -100.7, -100.900, ...]");
        rangeUp_BigDecimal_helper("1234567",
                "[1234567, 1234567.0, 1234568, 1234567.00, 1234567.1, 1234568.0, 1234569, 1234567.000, 1.23457E+6," +
                " 1234567.10, 1234567.2, 1234568.00, 1234571, 1234569.0, 1234567.01, 1234567.0000, 1234572, 1234570," +
                " 1234567.3, 1234567.100, ...]");
        rangeUp_BigDecimal_helper("-1234567",
                "[-1234567, -1234567.0, -1234566, -1234567.00, -1234566.9, -1234566.0, -1234565, -1234567.000," +
                " -1234564, -1234566.90, -1234566.8, -1234566.00, -1234563, -1234565.0, -1234566.99, -1234567.0000," +
                " -1234562, -1234564.0, -1234566.7, -1234566.900, ...]");
        rangeUp_BigDecimal_helper("0.09",
                "[0.09, 0.090, 1.09, 0.0900, 0.19, 1.090, 2.09, 0.09000, 3.09, 0.190, 0.29, 1.0900, 4.09, 2.090," +
                " 0.1, 0.090000, 5.09, 3.090, 0.39, 0.1900, ...]");
        rangeUp_BigDecimal_helper("-0.09",
                "[-0.09, -0.090, 0.91, -0.0900, 0.01, 0.910, 1.91, -0.09000, 2.91, 0.010, 0.11, 0.9100, 3.91, 1.910," +
                " -0.08, -0.090000, 4.91, 2.910, 0.21, 0.0100, ...]");
        rangeUp_BigDecimal_helper("1E-12",
                "[1E-12, 1.0E-12, 1.000000000001, 1.00E-12, 0.100000000001, 1.0000000000010, 2.000000000001," +
                " 1.000E-12, 3.000000000001, 0.1000000000010, 0.200000000001, 1.00000000000100, 4.000000000001," +
                " 2.0000000000010, 0.010000000001, 1.0000E-12, 5.000000000001, 3.0000000000010, 0.300000000001," +
                " 0.10000000000100, ...]");
        rangeUp_BigDecimal_helper("-1E-12",
                "[-1E-12, -1.0E-12, 0.999999999999, -1.00E-12, 0.099999999999, 0.9999999999990, 1.999999999999," +
                " -1.000E-12, 2.999999999999, 0.0999999999990, 0.199999999999, 0.99999999999900, 3.999999999999," +
                " 1.9999999999990, 0.009999999999, -1.0000E-12, 4.999999999999, 2.9999999999990, 0.299999999999," +
                " 0.09999999999900, ...]");
        rangeUp_BigDecimal_helper("1E+12",
                "[1E+12, 1.0E+12, 1000000000001, 1.00E+12, 1000000000000.1, 1000000000001.0, 1000000000002," +
                " 1.000E+12, 1000000000003, 1000000000000.10, 1000000000000.2, 1000000000001.00, 1000000000004," +
                " 1000000000002.0, 1000000000000.01, 1.0000E+12, 1000000000005, 1000000000003.0, 1000000000000.3," +
                " 1000000000000.100, ...]");
        rangeUp_BigDecimal_helper("-1E+12",
                "[-1E+12, -1.0E+12, -999999999999, -1.00E+12, -999999999999.9, -999999999999.0, -999999999998," +
                " -1.000E+12, -999999999997, -999999999999.90, -999999999999.8, -999999999999.00, -999999999996," +
                " -999999999998.0, -999999999999.99, -1.0000E+12, -999999999995, -999999999997.0, -999999999999.7," +
                " -999999999999.900, ...]");
    }

    private static void rangeDown_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeDown(Readers.readBigDecimal(a).get()), output);
    }

    @Test
    public void testRangeDown_BigDecimal() {
        rangeDown_BigDecimal_helper("0",
                "[0, 0.0, -1, 0E+1, -0.1, -1.0, -2, 0.00, -3, -0.10, -0.2, -1.00, -4, -2.0, -0.01, 0E+2, -5, -3.0," +
                " -0.3, -0.100, ...]");
        rangeDown_BigDecimal_helper("0.0",
                "[0, 0.0, -1, 0E+1, -0.1, -1.0, -2, 0.00, -3, -0.10, -0.2, -1.00, -4, -2.0, -0.01, 0E+2, -5, -3.0," +
                " -0.3, -0.100, ...]");
        rangeDown_BigDecimal_helper("1",
                "[1, 1.0, 0, 1.00, 0.9, 0.0, -1, 1.000, -2, 0.90, 0.8, 0E+1, -3, -1.0, 0.99, 1.0000, -4, -2.0, 0.7," +
                " 0.900, ...]");
        rangeDown_BigDecimal_helper("1.0",
                "[1, 1.0, 0, 1.00, 0.9, 0.0, -1, 1.000, -2, 0.90, 0.8, 0E+1, -3, -1.0, 0.99, 1.0000, -4, -2.0, 0.7," +
                " 0.900, ...]");
        rangeDown_BigDecimal_helper("-1",
                "[-1, -1.0, -2, -1.00, -1.1, -2.0, -3, -1.000, -4, -1.10, -1.2, -2.00, -5, -3.0, -1.01, -1.0000, -6," +
                " -4.0, -1.3, -1.100, ...]");
        rangeDown_BigDecimal_helper("-1.0",
                "[-1, -1.0, -2, -1.00, -1.1, -2.0, -3, -1.000, -4, -1.10, -1.2, -2.00, -5, -3.0, -1.01, -1.0000, -6," +
                " -4.0, -1.3, -1.100, ...]");
        rangeDown_BigDecimal_helper("9",
                "[9, 9.0, 8, 9.00, 8.9, 8.0, 7, 9.000, 6, 8.90, 8.8, 8.00, 5, 7.0, 8.99, 9.0000, 4, 6.0, 8.7, 8.900," +
                " ...]");
        rangeDown_BigDecimal_helper("-9",
                "[-9, -9.0, -1E+1, -9.00, -9.1, -10, -11, -9.000, -12, -9.10, -9.2, -10.0, -13, -11.0, -9.01," +
                " -9.0000, -14, -12.0, -9.3, -9.100, ...]");
        rangeDown_BigDecimal_helper("10",
                "[1E+1, 10, 9, 10.0, 9.9, 9.0, 8, 10.00, 7, 9.90, 9.8, 9.00, 6, 8.0, 9.99, 10.000, 5, 7.0, 9.7," +
                " 9.900, ...]");
        rangeDown_BigDecimal_helper("-10",
                "[-1E+1, -10, -11, -10.0, -10.1, -11.0, -12, -10.00, -13, -10.10, -10.2, -11.00, -14, -12.0, -10.01," +
                " -10.000, -15, -13.0, -10.3, -10.100, ...]");
        rangeDown_BigDecimal_helper("101",
                "[101, 101.0, 1E+2, 101.00, 100.9, 1.0E+2, 99, 101.000, 98, 100.90, 100.8, 100, 97, 99.0, 100.99," +
                " 101.0000, 96, 98.0, 100.7, 100.900, ...]");
        rangeDown_BigDecimal_helper("-101",
                "[-101, -101.0, -102, -101.00, -101.1, -102.0, -103, -101.000, -104, -101.10, -101.2, -102.00, -105," +
                " -103.0, -101.01, -101.0000, -106, -104.0, -101.3, -101.100, ...]");
        rangeDown_BigDecimal_helper("1234567",
                "[1234567, 1234567.0, 1234566, 1234567.00, 1234566.9, 1234566.0, 1234565, 1234567.000, 1234564," +
                " 1234566.90, 1234566.8, 1234566.00, 1234563, 1234565.0, 1234566.99, 1234567.0000, 1234562," +
                " 1234564.0, 1234566.7, 1234566.900, ...]");
        rangeDown_BigDecimal_helper("-1234567",
                "[-1234567, -1234567.0, -1234568, -1234567.00, -1234567.1, -1234568.0, -1234569, -1234567.000," +
                " -1.23457E+6, -1234567.10, -1234567.2, -1234568.00, -1234571, -1234569.0, -1234567.01," +
                " -1234567.0000, -1234572, -1234570, -1234567.3, -1234567.100, ...]");
        rangeDown_BigDecimal_helper("0.09",
                "[0.09, 0.090, -0.91, 0.0900, -0.01, -0.910, -1.91, 0.09000, -2.91, -0.010, -0.11, -0.9100, -3.91," +
                " -1.910, 0.08, 0.090000, -4.91, -2.910, -0.21, -0.0100, ...]");
        rangeDown_BigDecimal_helper("-0.09",
                "[-0.09, -0.090, -1.09, -0.0900, -0.19, -1.090, -2.09, -0.09000, -3.09, -0.190, -0.29, -1.0900, " +
                "-4.09, -2.090, -0.1, -0.090000, -5.09, -3.090, -0.39, -0.1900, ...]");
        rangeDown_BigDecimal_helper("1E-12",
                "[1E-12, 1.0E-12, -0.999999999999, 1.00E-12, -0.099999999999, -0.9999999999990, -1.999999999999," +
                " 1.000E-12, -2.999999999999, -0.0999999999990, -0.199999999999, -0.99999999999900, -3.999999999999," +
                " -1.9999999999990, -0.009999999999, 1.0000E-12, -4.999999999999, -2.9999999999990, -0.299999999999," +
                " -0.09999999999900, ...]");
        rangeDown_BigDecimal_helper("-1E-12",
                "[-1E-12, -1.0E-12, -1.000000000001, -1.00E-12, -0.100000000001, -1.0000000000010, -2.000000000001," +
                " -1.000E-12, -3.000000000001, -0.1000000000010, -0.200000000001, -1.00000000000100," +
                " -4.000000000001, -2.0000000000010, -0.010000000001, -1.0000E-12, -5.000000000001," +
                " -3.0000000000010, -0.300000000001, -0.10000000000100, ...]");
        rangeDown_BigDecimal_helper("1E+12",
                "[1E+12, 1.0E+12, 999999999999, 1.00E+12, 999999999999.9, 999999999999.0, 999999999998, 1.000E+12," +
                " 999999999997, 999999999999.90, 999999999999.8, 999999999999.00, 999999999996, 999999999998.0," +
                " 999999999999.99, 1.0000E+12, 999999999995, 999999999997.0, 999999999999.7, 999999999999.900, ...]");
        rangeDown_BigDecimal_helper("-1E+12",
                "[-1E+12, -1.0E+12, -1000000000001, -1.00E+12, -1000000000000.1, -1000000000001.0, -1000000000002," +
                " -1.000E+12, -1000000000003, -1000000000000.10, -1000000000000.2, -1000000000001.00," +
                " -1000000000004, -1000000000002.0, -1000000000000.01, -1.0000E+12, -1000000000005," +
                " -1000000000003.0, -1000000000000.3, -1000000000000.100, ...]");
    }

    private static void range_BigDecimal_BigDecimal_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(P.range(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()), output);
    }

    @Test
    public void testRange_BigDecimal_BigDecimal() {
        range_BigDecimal_BigDecimal_helper("0", "1",
                "[0, 0.0, 1, 0E+1, 0.1, 1.0, 0.2, 0.00, 0.01, 0.10, 0.3, 1.00, 0.02, 0.20, 0.4, 0E+2, 0.001, 0.010," +
                " 0.5, 0.100, ...]");
        range_BigDecimal_BigDecimal_helper("0", "3",
                "[0, 0.0, 1, 0E+1, 2, 1.0, 0.1, 0.00, 3, 2.0, 0.2, 1.00, 0.01, 0.10, 0.3, 0E+2, 0.02, 3.0, 0.4," +
                " 2.00, ...]");
        range_BigDecimal_BigDecimal_helper("0", "1E+6",
                "[0, 0.0, 1E+6, 0E+1, 1E+5, 1.0E+6, 2E+5, 0.00, 1E+4, 1.0E+5, 3E+5, 1.00E+6, 2E+4, 2.0E+5, 4E+5," +
                " 0E+2, 1E+3, 1.0E+4, 5E+5, 1.00E+5, ...]");
        range_BigDecimal_BigDecimal_helper("0", "0.000001",
                "[0, 0.0, 0.000001, 0E+1, 1E-7, 0.0000010, 2E-7, 0.00, 1E-8, 1.0E-7, 3E-7, 0.00000100, 2E-8, 2.0E-7," +
                " 4E-7, 0E+2, 1E-9, 1.0E-8, 5E-7, 1.00E-7, ...]");
        range_BigDecimal_BigDecimal_helper("1", "3",
                "[1, 1.0, 2, 1.00, 3, 2.0, 1.1, 1.000, 1.2, 3.0, 1.01, 2.00, 1.3, 1.10, 1.02, 1.0000, 1.4, 1.20," +
                " 1.001, 3.00, ...]");
        range_BigDecimal_BigDecimal_helper("1", "1E+6",
                "[1, 1.0, 100001, 1.00, 200001, 100001.0, 10001, 1.000, 300001, 200001.0, 20001, 100001.00, 400001," +
                " 10001.0, 1001, 1.0000, 500001, 300001.0, 30001, 200001.00, ...]");
        range_BigDecimal_BigDecimal_helper("-1", "0",
                "[-1, -1.0, 0, -1.00, -0.9, 0.0, -0.8, -1.000, -0.99, -0.90, -0.7, 0E+1, -0.98, -0.80, -0.6," +
                " -1.0000, -0.999, -0.990, -0.5, -0.900, ...]");
        range_BigDecimal_BigDecimal_helper("-3", "0",
                "[-3, -3.0, -2, -3.00, -1, -2.0, -2.9, -3.000, 0, -1.0, -2.8, -2.00, -2.99, -2.90, -2.7, -3.0000," +
                " -2.98, 0.0, -2.6, -1.00, ...]");
        range_BigDecimal_BigDecimal_helper("-1E+6", "0",
                "[-1E+6, -1.0E+6, 0, -1.00E+6, -9E+5, 0.0, -8E+5, -1.000E+6, -9.9E+5, -9.0E+5, -7E+5, 0E+1, -9.8E+5," +
                " -8.0E+5, -6E+5, -1.0000E+6, -9.99E+5, -9.90E+5, -5E+5, -9.00E+5, ...]");
        range_BigDecimal_BigDecimal_helper("-0.000001", "0",
                "[-0.000001, -0.0000010, 0, -0.00000100, -9E-7, 0.0, -8E-7, -0.000001000, -9.9E-7, -9.0E-7, -7E-7," +
                " 0E+1, -9.8E-7, -8.0E-7, -6E-7, -0.0000010000, -9.99E-7, -9.90E-7, -5E-7, -9.00E-7, ...]");
        range_BigDecimal_BigDecimal_helper("-3", "-1",
                "[-3, -3.0, -2, -3.00, -1, -2.0, -2.9, -3.000, -2.8, -1.0, -2.99, -2.00, -2.7, -2.90, -2.98," +
                " -3.0000, -2.6, -2.80, -2.999, -1.00, ...]");
        range_BigDecimal_BigDecimal_helper("-1E+6", "-1",
                "[-1E+6, -1.0E+6, -9E+5, -1.00E+6, -8E+5, -9.0E+5, -9.9E+5, -1.000E+6, -7E+5, -8.0E+5, -9.8E+5," +
                " -9.00E+5, -6E+5, -9.90E+5, -9.99E+5, -1.0000E+6, -5E+5, -7.0E+5, -9.7E+5, -8.00E+5, ...]");
        range_BigDecimal_BigDecimal_helper("100", "101",
                "[1E+2, 1.0E+2, 101, 100, 100.1, 101.0, 100.2, 100.0, 100.01, 100.10, 100.3, 101.00, 100.02, 100.20," +
                " 100.4, 100.00, 100.001, 100.010, 100.5, 100.100, ...]");
        range_BigDecimal_BigDecimal_helper("2.7183", "3.1416",
                "[2.7183, 2.71830, 2.8183, 2.718300, 2.9183, 2.81830, 2.7283, 2.7183000, 3.0183, 2.91830, 2.7383," +
                " 2.818300, 3.1183, 2.72830, 2.7193, 2.71830000, 2.7483, 3.01830, 2.7203, 2.918300, ...]");
        range_BigDecimal_BigDecimal_helper("-3.1416", "2.7183",
                "[-3.1416, -3.14160, -2.1416, -3.141600, -1.1416, -2.14160, -3.0416, -3.1416000, -0.1416, -1.14160," +
                " -2.9416, -2.141600, 0.8584, -3.04160, -3.1316, -3.14160000, 1.8584, -0.14160, -2.8416, -1.141600," +
                " ...]");
        range_BigDecimal_BigDecimal_helper("0", "0",
                "[0, 0.0, 0E+1, 0.00, 0E+2, 0.000, 0E+3, 0.0000, 0E+4, 0.00000, 0E+5, 0.000000, 0E+6, 0E-7, 0E+7," +
                " 0E-8, 0E+8, 0E-9, 0E+9, 0E-10, ...]");
        range_BigDecimal_BigDecimal_helper("0", "0.0",
                "[0, 0.0, 0E+1, 0.00, 0E+2, 0.000, 0E+3, 0.0000, 0E+4, 0.00000, 0E+5, 0.000000, 0E+6, 0E-7, 0E+7," +
                " 0E-8, 0E+8, 0E-9, 0E+9, 0E-10, ...]");
        range_BigDecimal_BigDecimal_helper("0.0", "0",
                "[0, 0.0, 0E+1, 0.00, 0E+2, 0.000, 0E+3, 0.0000, 0E+4, 0.00000, 0E+5, 0.000000, 0E+6, 0E-7, 0E+7," +
                " 0E-8, 0E+8, 0E-9, 0E+9, 0E-10, ...]");
        range_BigDecimal_BigDecimal_helper("0.0", "0.0",
                "[0, 0.0, 0E+1, 0.00, 0E+2, 0.000, 0E+3, 0.0000, 0E+4, 0.00000, 0E+5, 0.000000, 0E+6, 0E-7, 0E+7," +
                " 0E-8, 0E+8, 0E-9, 0E+9, 0E-10, ...]");
        range_BigDecimal_BigDecimal_helper("1", "1",
                "[1, 1.0, 1.00, 1.000, 1.0000, 1.00000, 1.000000, 1.0000000, 1.00000000, 1.000000000, 1.0000000000," +
                " 1.00000000000, 1.000000000000, 1.0000000000000, 1.00000000000000, 1.000000000000000," +
                " 1.0000000000000000, 1.00000000000000000, 1.000000000000000000, 1.0000000000000000000, ...]");
        range_BigDecimal_BigDecimal_helper("5", "3", "[]");
    }

    private static void rangeUpCanonical_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeUpCanonical(Readers.readBigDecimal(a).get()), output);
    }

    @Test
    public void testRangeUpCanonical_BigDecimal() {
        rangeUpCanonical_BigDecimal_helper("0",
                "[0, 1, 0.1, 2, 3, 0.2, 4, 0.01, 5, 0.3, 6, 7, 0.4, 8, 9, 0.5, 10, 11, 0.6, 12, ...]");
        rangeUpCanonical_BigDecimal_helper("0.0",
                "[0, 1, 0.1, 2, 3, 0.2, 4, 0.01, 5, 0.3, 6, 7, 0.4, 8, 9, 0.5, 10, 11, 0.6, 12, ...]");
        rangeUpCanonical_BigDecimal_helper("1",
                "[1, 2, 1.1, 3, 4, 1.2, 5, 1.01, 6, 1.3, 7, 8, 1.4, 9, 10, 1.5, 11, 12, 1.6, 13, ...]");
        rangeUpCanonical_BigDecimal_helper("1.0",
                "[1, 2, 1.1, 3, 4, 1.2, 5, 1.01, 6, 1.3, 7, 8, 1.4, 9, 10, 1.5, 11, 12, 1.6, 13, ...]");
        rangeUpCanonical_BigDecimal_helper("-1",
                "[-1, 0, -0.9, 1, 2, -0.8, 3, -0.99, 4, -0.7, 5, 6, -0.6, 7, 8, -0.5, 9, 10, -0.4, 11, ...]");
        rangeUpCanonical_BigDecimal_helper("-1.0",
                "[-1, 0, -0.9, 1, 2, -0.8, 3, -0.99, 4, -0.7, 5, 6, -0.6, 7, 8, -0.5, 9, 10, -0.4, 11, ...]");
        rangeUpCanonical_BigDecimal_helper("9",
                "[9, 10, 9.1, 11, 12, 9.2, 13, 9.01, 14, 9.3, 15, 16, 9.4, 17, 18, 9.5, 19, 20, 9.6, 21, ...]");
        rangeUpCanonical_BigDecimal_helper("-9",
                "[-9, -8, -8.9, -7, -6, -8.8, -5, -8.99, -4, -8.7, -3, -2, -8.6, -1, 0, -8.5, 1, 2, -8.4, 3, ...]");
        rangeUpCanonical_BigDecimal_helper("10",
                "[10, 11, 10.1, 12, 13, 10.2, 14, 10.01, 15, 10.3, 16, 17, 10.4, 18, 19, 10.5, 20, 21, 10.6, 22," +
                " ...]");
        rangeUpCanonical_BigDecimal_helper("-10",
                "[-10, -9, -9.9, -8, -7, -9.8, -6, -9.99, -5, -9.7, -4, -3, -9.6, -2, -1, -9.5, 0, 1, -9.4, 2, ...]");
        rangeUpCanonical_BigDecimal_helper("101",
                "[101, 102, 101.1, 103, 104, 101.2, 105, 101.01, 106, 101.3, 107, 108, 101.4, 109, 110, 101.5, 111," +
                " 112, 101.6, 113, ...]");
        rangeUpCanonical_BigDecimal_helper("-101",
                "[-101, -100, -100.9, -99, -98, -100.8, -97, -100.99, -96, -100.7, -95, -94, -100.6, -93, -92," +
                " -100.5, -91, -90, -100.4, -89, ...]");
        rangeUpCanonical_BigDecimal_helper("1234567",
                "[1234567, 1234568, 1234567.1, 1234569, 1234570, 1234567.2, 1234571, 1234567.01, 1234572, 1234567.3," +
                " 1234573, 1234574, 1234567.4, 1234575, 1234576, 1234567.5, 1234577, 1234578, 1234567.6, 1234579," +
                " ...]");
        rangeUpCanonical_BigDecimal_helper("-1234567",
                "[-1234567, -1234566, -1234566.9, -1234565, -1234564, -1234566.8, -1234563, -1234566.99, -1234562," +
                " -1234566.7, -1234561, -1234560, -1234566.6, -1234559, -1234558, -1234566.5, -1234557, -1234556," +
                " -1234566.4, -1234555, ...]");
        rangeUpCanonical_BigDecimal_helper("0.09",
                "[0.09, 1.09, 0.19, 2.09, 3.09, 0.29, 4.09, 0.1, 5.09, 0.39, 6.09, 7.09, 0.49, 8.09, 9.09, 0.59," +
                " 10.09, 11.09, 0.69, 12.09, ...]");
        rangeUpCanonical_BigDecimal_helper("-0.09",
                "[-0.09, 0.91, 0.01, 1.91, 2.91, 0.11, 3.91, -0.08, 4.91, 0.21, 5.91, 6.91, 0.31, 7.91, 8.91, 0.41," +
                " 9.91, 10.91, 0.51, 11.91, ...]");
        rangeUpCanonical_BigDecimal_helper("1E-12",
                "[1E-12, 1.000000000001, 0.100000000001, 2.000000000001, 3.000000000001, 0.200000000001," +
                " 4.000000000001, 0.010000000001, 5.000000000001, 0.300000000001, 6.000000000001, 7.000000000001," +
                " 0.400000000001, 8.000000000001, 9.000000000001, 0.500000000001, 10.000000000001, 11.000000000001," +
                " 0.600000000001, 12.000000000001, ...]");
        rangeUpCanonical_BigDecimal_helper("-1E-12",
                "[-1E-12, 0.999999999999, 0.099999999999, 1.999999999999, 2.999999999999, 0.199999999999," +
                " 3.999999999999, 0.009999999999, 4.999999999999, 0.299999999999, 5.999999999999, 6.999999999999," +
                " 0.399999999999, 7.999999999999, 8.999999999999, 0.499999999999, 9.999999999999, 10.999999999999," +
                " 0.599999999999, 11.999999999999, ...]");
        rangeUpCanonical_BigDecimal_helper("1E+12",
                "[1000000000000, 1000000000001, 1000000000000.1, 1000000000002, 1000000000003, 1000000000000.2," +
                " 1000000000004, 1000000000000.01, 1000000000005, 1000000000000.3, 1000000000006, 1000000000007," +
                " 1000000000000.4, 1000000000008, 1000000000009, 1000000000000.5, 1000000000010, 1000000000011," +
                " 1000000000000.6, 1000000000012, ...]");
        rangeUpCanonical_BigDecimal_helper("-1E+12",
                "[-1000000000000, -999999999999, -999999999999.9, -999999999998, -999999999997, -999999999999.8," +
                " -999999999996, -999999999999.99, -999999999995, -999999999999.7, -999999999994, -999999999993," +
                " -999999999999.6, -999999999992, -999999999991, -999999999999.5, -999999999990, -999999999989," +
                " -999999999999.4, -999999999988, ...]");
    }

    private static void rangeDownCanonical_BigDecimal_helper(@NotNull String a, @NotNull String output) {
        simpleProviderHelper(P.rangeDownCanonical(Readers.readBigDecimal(a).get()), output);
    }

    @Test
    public void testRangeDownCanonical_BigDecimal() {
        rangeDownCanonical_BigDecimal_helper("0",
                "[0, -1, -0.1, -2, -3, -0.2, -4, -0.01, -5, -0.3, -6, -7, -0.4, -8, -9, -0.5, -10, -11, -0.6, -12," +
                " ...]");
        rangeDownCanonical_BigDecimal_helper("0.0",
                "[0, -1, -0.1, -2, -3, -0.2, -4, -0.01, -5, -0.3, -6, -7, -0.4, -8, -9, -0.5, -10, -11, -0.6, -12," +
                " ...]");
        rangeDownCanonical_BigDecimal_helper("1",
                "[1, 0, 0.9, -1, -2, 0.8, -3, 0.99, -4, 0.7, -5, -6, 0.6, -7, -8, 0.5, -9, -10, 0.4, -11, ...]");
        rangeDownCanonical_BigDecimal_helper("1.0",
                "[1, 0, 0.9, -1, -2, 0.8, -3, 0.99, -4, 0.7, -5, -6, 0.6, -7, -8, 0.5, -9, -10, 0.4, -11, ...]");
        rangeDownCanonical_BigDecimal_helper("-1",
                "[-1, -2, -1.1, -3, -4, -1.2, -5, -1.01, -6, -1.3, -7, -8, -1.4, -9, -10, -1.5, -11, -12, -1.6, -13," +
                " ...]");
        rangeDownCanonical_BigDecimal_helper("-1.0",
                "[-1, -2, -1.1, -3, -4, -1.2, -5, -1.01, -6, -1.3, -7, -8, -1.4, -9, -10, -1.5, -11, -12, -1.6, -13," +
                " ...]");
        rangeDownCanonical_BigDecimal_helper("9",
                "[9, 8, 8.9, 7, 6, 8.8, 5, 8.99, 4, 8.7, 3, 2, 8.6, 1, 0, 8.5, -1, -2, 8.4, -3, ...]");
        rangeDownCanonical_BigDecimal_helper("-9",
                "[-9, -10, -9.1, -11, -12, -9.2, -13, -9.01, -14, -9.3, -15, -16, -9.4, -17, -18, -9.5, -19, -20," +
                " -9.6, -21, ...]");
        rangeDownCanonical_BigDecimal_helper("10",
                "[10, 9, 9.9, 8, 7, 9.8, 6, 9.99, 5, 9.7, 4, 3, 9.6, 2, 1, 9.5, 0, -1, 9.4, -2, ...]");
        rangeDownCanonical_BigDecimal_helper("-10",
                "[-10, -11, -10.1, -12, -13, -10.2, -14, -10.01, -15, -10.3, -16, -17, -10.4, -18, -19, -10.5, -20," +
                " -21, -10.6, -22, ...]");
        rangeDownCanonical_BigDecimal_helper("101",
                "[101, 100, 100.9, 99, 98, 100.8, 97, 100.99, 96, 100.7, 95, 94, 100.6, 93, 92, 100.5, 91, 90," +
                " 100.4, 89, ...]");
        rangeDownCanonical_BigDecimal_helper("-101",
                "[-101, -102, -101.1, -103, -104, -101.2, -105, -101.01, -106, -101.3, -107, -108, -101.4, -109," +
                " -110, -101.5, -111, -112, -101.6, -113, ...]");
        rangeDownCanonical_BigDecimal_helper("1234567",
                "[1234567, 1234566, 1234566.9, 1234565, 1234564, 1234566.8, 1234563, 1234566.99, 1234562, 1234566.7," +
                " 1234561, 1234560, 1234566.6, 1234559, 1234558, 1234566.5, 1234557, 1234556, 1234566.4, 1234555," +
                " ...]");
        rangeDownCanonical_BigDecimal_helper("-1234567",
                "[-1234567, -1234568, -1234567.1, -1234569, -1234570, -1234567.2, -1234571, -1234567.01, -1234572," +
                " -1234567.3, -1234573, -1234574, -1234567.4, -1234575, -1234576, -1234567.5, -1234577, -1234578," +
                " -1234567.6, -1234579, ...]");
        rangeDownCanonical_BigDecimal_helper("0.09",
                "[0.09, -0.91, -0.01, -1.91, -2.91, -0.11, -3.91, 0.08, -4.91, -0.21, -5.91, -6.91, -0.31, -7.91," +
                " -8.91, -0.41, -9.91, -10.91, -0.51, -11.91, ...]");
        rangeDownCanonical_BigDecimal_helper("-0.09",
                "[-0.09, -1.09, -0.19, -2.09, -3.09, -0.29, -4.09, -0.1, -5.09, -0.39, -6.09, -7.09, -0.49, -8.09," +
                " -9.09, -0.59, -10.09, -11.09, -0.69, -12.09, ...]");
        rangeDownCanonical_BigDecimal_helper("1E-12",
                "[1E-12, -0.999999999999, -0.099999999999, -1.999999999999, -2.999999999999, -0.199999999999," +
                " -3.999999999999, -0.009999999999, -4.999999999999, -0.299999999999, -5.999999999999," +
                " -6.999999999999, -0.399999999999, -7.999999999999, -8.999999999999, -0.499999999999," +
                " -9.999999999999, -10.999999999999, -0.599999999999, -11.999999999999, ...]");
        rangeDownCanonical_BigDecimal_helper("-1E-12",
                "[-1E-12, -1.000000000001, -0.100000000001, -2.000000000001, -3.000000000001, -0.200000000001," +
                " -4.000000000001, -0.010000000001, -5.000000000001, -0.300000000001, -6.000000000001," +
                " -7.000000000001, -0.400000000001, -8.000000000001, -9.000000000001, -0.500000000001," +
                " -10.000000000001, -11.000000000001, -0.600000000001, -12.000000000001, ...]");
        rangeDownCanonical_BigDecimal_helper("1E+12",
                "[1000000000000, 999999999999, 999999999999.9, 999999999998, 999999999997, 999999999999.8," +
                " 999999999996, 999999999999.99, 999999999995, 999999999999.7, 999999999994, 999999999993," +
                " 999999999999.6, 999999999992, 999999999991, 999999999999.5, 999999999990, 999999999989," +
                " 999999999999.4, 999999999988, ...]");
        rangeDownCanonical_BigDecimal_helper("-1E+12",
                "[-1000000000000, -1000000000001, -1000000000000.1, -1000000000002, -1000000000003," +
                " -1000000000000.2, -1000000000004, -1000000000000.01, -1000000000005, -1000000000000.3," +
                " -1000000000006, -1000000000007, -1000000000000.4, -1000000000008, -1000000000009," +
                " -1000000000000.5, -1000000000010, -1000000000011, -1000000000000.6, -1000000000012, ...]");
    }

    private static void rangeCanonical_BigDecimal_BigDecimal_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        simpleProviderHelper(
                P.rangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output
        );
    }

    @Test
    public void testRangeCanonical_BigDecimal_BigDecimal() {
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "1",
                "[0, 1, 0.1, 0.2, 0.01, 0.3, 0.02, 0.4, 0.001, 0.5, 0.03, 0.6, 0.002, 0.7, 0.04, 0.8, 0.0001, 0.9," +
                " 0.05, 0.003, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "3",
                "[0, 1, 2, 0.1, 3, 0.2, 0.01, 0.3, 0.02, 0.4, 0.001, 0.5, 0.03, 1.1, 0.6, 1.2, 0.002, 1.3, 0.7, 1.4," +
                " ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "1E+6",
                "[0, 1000000, 100000, 200000, 10000, 300000, 20000, 400000, 1000, 500000, 30000, 600000, 2000," +
                " 700000, 40000, 800000, 100, 900000, 50000, 3000, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0.000001",
                "[0, 0.000001, 1E-7, 2E-7, 1E-8, 3E-7, 2E-8, 4E-7, 1E-9, 5E-7, 3E-8, 6E-7, 2E-9, 7E-7, 4E-8, 8E-7," +
                " 1E-10, 9E-7, 5E-8, 3E-9, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "3",
                "[1, 2, 3, 1.1, 1.2, 1.01, 1.3, 1.02, 1.4, 1.001, 1.5, 1.03, 2.1, 1.6, 2.2, 1.002, 2.3, 1.7, 2.4," +
                " 1.04, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "1E+6",
                "[1, 100001, 200001, 10001, 300001, 20001, 400001, 1001, 500001, 30001, 600001, 2001, 700001, 40001," +
                " 800001, 101, 900001, 50001, 3001, 110001, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1", "0",
                "[-1, 0, -0.9, -0.8, -0.99, -0.7, -0.98, -0.6, -0.999, -0.5, -0.97, -0.4, -0.998, -0.3, -0.96, -0.2," +
                " -0.9999, -0.1, -0.95, -0.997, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3", "0",
                "[-3, -2, -1, -2.9, 0, -2.8, -2.99, -2.7, -2.98, -2.6, -2.999, -2.5, -2.97, -1.9, -2.4, -1.8," +
                " -2.998, -1.7, -2.3, -1.6, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1E+6", "0",
                "[-1000000, 0, -900000, -800000, -990000, -700000, -980000, -600000, -999000, -500000, -970000," +
                " -400000, -998000, -300000, -960000, -200000, -999900, -100000, -950000, -997000, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-0.000001", "0",
                "[-0.000001, 0, -9E-7, -8E-7, -9.9E-7, -7E-7, -9.8E-7, -6E-7, -9.99E-7, -5E-7, -9.7E-7, -4E-7," +
                " -9.98E-7, -3E-7, -9.6E-7, -2E-7, -9.999E-7, -1E-7, -9.5E-7, -9.97E-7, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3", "-1",
                "[-3, -2, -1, -2.9, -2.8, -2.99, -2.7, -2.98, -2.6, -2.999, -2.5, -2.97, -1.9, -2.4, -1.8, -2.998," +
                " -1.7, -2.3, -1.6, -2.96, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-1E+6", "-1",
                "[-1000000, -900000, -800000, -990000, -700000, -980000, -600000, -999000, -500000, -970000," +
                " -400000, -998000, -300000, -960000, -200000, -999900, -100000, -950000, -997000, -890000, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("100", "101",
                "[100, 101, 100.1, 100.2, 100.01, 100.3, 100.02, 100.4, 100.001, 100.5, 100.03, 100.6, 100.002," +
                " 100.7, 100.04, 100.8, 100.0001, 100.9, 100.05, 100.003, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("2.7183", "3.1416",
                "[2.7183, 2.8183, 2.9183, 2.7283, 3.0183, 2.7383, 3.1183, 2.7193, 2.7483, 2.7203, 2.7583, 2.7184," +
                " 2.7683, 2.7213, 2.8283, 2.7783, 2.8383, 2.7185, 2.8483, 2.7883, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("-3.1416", "2.7183",
                "[-3.1416, -2.1416, -1.1416, -3.0416, -0.1416, -2.9416, 0.8584, -3.1316, 1.8584, -2.8416, -3.1216," +
                " -2.7416, -3.1406, -2.6416, -3.1116, -2.0416, -2.5416, -1.9416, -3.1396, -1.8416, ...]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0", "[0]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0", "0.0", "[0]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0.0", "0", "[0]");
        rangeCanonical_BigDecimal_BigDecimal_helper("0.0", "0.0", "[0]");
        rangeCanonical_BigDecimal_BigDecimal_helper("1", "1", "[1]");
        rangeCanonical_BigDecimal_BigDecimal_helper("5", "3", "[]");
    }

    private static void withNull_finite_helper(@NotNull String x, @NotNull String output) {
        aeqit(P.withNull(readIntegerListWithNulls(x)), output);
    }

    private static void withNull_cyclic_helper(@NotNull String x, @NotNull String output) {
        simpleProviderHelper(P.withNull(cycle(readIntegerListWithNulls(x))), output);
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
        simpleProviderHelper(P.nonEmptyOptionals(P.integers()),
                "[Optional[0], Optional[1], Optional[-1], Optional[2], Optional[-2], Optional[3], Optional[-3]," +
                " Optional[4], Optional[-4], Optional[5], Optional[-5], Optional[6], Optional[-6], Optional[7]," +
                " Optional[-7], Optional[8], Optional[-8], Optional[9], Optional[-9], Optional[10], ...]");
        simpleProviderHelper(P.nonEmptyOptionals(P.strings()),
                "[Optional[], Optional[a], Optional[aa], Optional[b], Optional[aaa], Optional[c], Optional[ab]," +
                " Optional[d], Optional[aaaa], Optional[e], Optional[ba], Optional[f], Optional[aab], Optional[g]," +
                " Optional[bb], Optional[h], Optional[aaaaa], Optional[i], Optional[ac], Optional[j], ...]");
        aeqit(P.nonEmptyOptionals(Arrays.asList(1, 2, 3)), "[Optional[1], Optional[2], Optional[3]]");
        aeqit(P.nonEmptyOptionals(Collections.emptyList()), "[]");
        try {
            toList(take(TINY_LIMIT, P.nonEmptyOptionals(P.withNull(P.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testOptionals() {
        simpleProviderHelper(P.optionals(P.integers()),
                "[Optional.empty, Optional[0], Optional[1], Optional[-1], Optional[2], Optional[-2], Optional[3]," +
                " Optional[-3], Optional[4], Optional[-4], Optional[5], Optional[-5], Optional[6], Optional[-6]," +
                " Optional[7], Optional[-7], Optional[8], Optional[-8], Optional[9], Optional[-9], ...]");
        simpleProviderHelper(P.optionals(P.strings()),
                "[Optional.empty, Optional[], Optional[a], Optional[aa], Optional[b], Optional[aaa], Optional[c]," +
                " Optional[ab], Optional[d], Optional[aaaa], Optional[e], Optional[ba], Optional[f], Optional[aab]," +
                " Optional[g], Optional[bb], Optional[h], Optional[aaaaa], Optional[i], Optional[ac], ...]");
        aeqit(P.optionals(Arrays.asList(1, 2, 3)), "[Optional.empty, Optional[1], Optional[2], Optional[3]]");
        aeqit(P.optionals(Collections.emptyList()), "[Optional.empty]");
        try {
            toList(take(TINY_LIMIT, P.optionals(P.withNull(P.integers()))));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testNonEmptyNullableOptionals() {
        simpleProviderHelper(P.nonEmptyNullableOptionals(P.withNull(P.integers())),
                "[NullableOptional[null], NullableOptional[0], NullableOptional[1], NullableOptional[-1]," +
                " NullableOptional[2], NullableOptional[-2], NullableOptional[3], NullableOptional[-3]," +
                " NullableOptional[4], NullableOptional[-4], NullableOptional[5], NullableOptional[-5]," +
                " NullableOptional[6], NullableOptional[-6], NullableOptional[7], NullableOptional[-7]," +
                " NullableOptional[8], NullableOptional[-8], NullableOptional[9], NullableOptional[-9], ...]");
        simpleProviderHelper(P.nonEmptyNullableOptionals(P.withNull(P.strings())),
                "[NullableOptional[null], NullableOptional[], NullableOptional[a], NullableOptional[aa]," +
                " NullableOptional[b], NullableOptional[aaa], NullableOptional[c], NullableOptional[ab]," +
                " NullableOptional[d], NullableOptional[aaaa], NullableOptional[e], NullableOptional[ba]," +
                " NullableOptional[f], NullableOptional[aab], NullableOptional[g], NullableOptional[bb]," +
                " NullableOptional[h], NullableOptional[aaaaa], NullableOptional[i], NullableOptional[ac], ...]");
        aeqit(P.nonEmptyNullableOptionals(Arrays.asList(1, 2, 3)),
                "[NullableOptional[1], NullableOptional[2], NullableOptional[3]]");
        aeqit(P.nonEmptyNullableOptionals(Collections.emptyList()), "[]");
    }

    @Test
    public void testNullableOptionals() {
        simpleProviderHelper(P.nullableOptionals(P.withNull(P.integers())),
                "[NullableOptional.empty, NullableOptional[null], NullableOptional[0], NullableOptional[1]," +
                " NullableOptional[-1], NullableOptional[2], NullableOptional[-2], NullableOptional[3]," +
                " NullableOptional[-3], NullableOptional[4], NullableOptional[-4], NullableOptional[5]," +
                " NullableOptional[-5], NullableOptional[6], NullableOptional[-6], NullableOptional[7]," +
                " NullableOptional[-7], NullableOptional[8], NullableOptional[-8], NullableOptional[9], ...]");
        simpleProviderHelper(P.nullableOptionals(P.withNull(P.strings())),
                "[NullableOptional.empty, NullableOptional[null], NullableOptional[], NullableOptional[a]," +
                " NullableOptional[aa], NullableOptional[b], NullableOptional[aaa], NullableOptional[c]," +
                " NullableOptional[ab], NullableOptional[d], NullableOptional[aaaa], NullableOptional[e]," +
                " NullableOptional[ba], NullableOptional[f], NullableOptional[aab], NullableOptional[g]," +
                " NullableOptional[bb], NullableOptional[h], NullableOptional[aaaaa], NullableOptional[i], ...]");
        aeqit(P.nullableOptionals(Arrays.asList(1, 2, 3)),
                "[NullableOptional.empty, NullableOptional[1], NullableOptional[2], NullableOptional[3]]");
        aeqit(P.nullableOptionals(Collections.emptyList()), "[NullableOptional.empty]");
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
        simpleProviderHelper(P.dependentPairs(Arrays.asList(3, 1, 2, 0), f),
                "[(3, foo), (2, a), (2, b), (2, c), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep)," +
                " (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep), (0, beep)," +
                " (0, beep), (0, beep), ...]");

        aeqit(P.dependentPairs(Collections.emptyList(), f), "[]");

        simpleProviderHelper(P.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> Collections.emptyList()), "[]");

        try {
            toList(P.dependentPairs(Arrays.asList(3, 1, 2, 0), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testDependentPairsInfinite() {
        simpleProviderHelper(P.dependentPairsInfinite(P.naturalBigIntegers(), i -> P.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (0, 4), (0, 5), (1, 4), (1, 5), ...]");

        Function<Integer, Iterable<String>> f = i -> {
            switch (i) {
                case 0: return repeat("beep");
                case 1: return cycle(Arrays.asList("a", "b"));
            }
            throw new IllegalArgumentException();
        };
        simpleProviderHelper(P.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b)," +
                " (0, beep), (0, beep), (1, a), (1, b), (0, beep), (0, beep), (1, a), (1, b), (0, beep)," +
                " (0, beep), ...]");

        try {
            toList(P.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(P.dependentPairsInfinite(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(P.dependentPairsInfinite(cycle(Arrays.asList(1, 0)), i -> Collections.singletonList("a")));
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    @Test
    public void testDependentPairsInfiniteLogarithmicOrder() {
        simpleProviderHelper(
                P.dependentPairsInfiniteLogarithmicOrder(
                        P.naturalBigIntegers(),
                        i -> P.naturalBigIntegers()
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
        simpleProviderHelper(P.dependentPairsInfiniteLogarithmicOrder(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (0, beep), (1, b), (1, a), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep)," +
                " (1, a), (1, b), (1, a), (0, beep), (1, b), (1, a), (1, b), (0, beep), (1, a), (1, b), ...]");

        try {
            toList(P.dependentPairsInfiniteLogarithmicOrder(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(P.dependentPairsInfiniteLogarithmicOrder(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    P.dependentPairsInfiniteLogarithmicOrder(
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
                P.dependentPairsInfiniteSquareRootOrder(
                        P.naturalBigIntegers(),
                        i -> P.naturalBigIntegers()
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
        simpleProviderHelper(P.dependentPairsInfiniteSquareRootOrder(cycle(Arrays.asList(1, 0)), f),
                "[(1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep)," +
                " (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep), (1, a), (0, beep), (1, b), (0, beep)," +
                " ...]");

        try {
            toList(P.dependentPairsInfiniteSquareRootOrder(cycle(Arrays.asList(1, 0)), i -> null));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        try {
            toList(P.dependentPairsInfiniteSquareRootOrder(Arrays.asList(0, 1), f));
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    P.dependentPairsInfiniteSquareRootOrder(
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
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (3, a), (2, b), (4, a), (1, d), (3, b), (2, c), (4, b), (3, c)," +
                " (2, d), (4, c), (3, d), (4, d)]"
        );
        aeqit(
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (2, a), (2, b), (4, a), (1, d), (2, b), (2, c), (4, b), (2, c)," +
                " (2, d), (4, c), (2, d), (4, d)]"
        );
        aeqit(
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (null, a), (2, b), (4, a), (1, d), (null, b), (2, c), (4, b)," +
                " (null, c), (2, d), (4, c), (null, d), (4, d)]"
        );
        aeqit(P.pairsLogarithmicOrder(Collections.emptyList(), fromString("abcd")), "[]");
        aeqit(P.pairsLogarithmicOrder(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                P.pairsLogarithmicOrder(P.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (0, c), (2, a), (1, b), (3, a), (0, d), (4, a), (2, b), (5, a), (1, c)," +
                " (6, a), (3, b), (7, a), (8, a), (4, b), (9, a), (2, c), (10, a), ...]"
        );
        simpleProviderHelper(
                P.pairsLogarithmicOrder(fromString("abcd"), P.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (a, 2), (c, 0), (b, 1), (d, 0), (a, 3), (c, 1), (b, 2), (d, 1), (a, 4)," +
                " (c, 2), (b, 3), (d, 2), (a, 5), (c, 3), (b, 4), (d, 3), (a, 6), ...]"
        );
        simpleProviderHelper(
                P.pairsLogarithmicOrder(P.positiveBigIntegers(), P.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (1, -3), (3, -1), (2, -2), (4, -1), (1, -4), (5, -1), (3, -2), (6, -1)," +
                " (2, -3), (7, -1), (4, -2), (8, -1), (1, -5), (9, -1), (5, -2), (10, -1), (3, -3), ...]"
        );
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        aeqit(
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 3), (3, 1), (2, 2), (4, 1), (1, 4), (3, 2), (2, 3), (4, 2), (3, 3)," +
                " (2, 4), (4, 3), (3, 4), (4, 4)]"
        );
        aeqit(
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, 2, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 2), (2, 1), (2, 2), (4, 1), (1, 4), (2, 2), (2, 2), (4, 2), (2, 2)," +
                " (2, 4), (4, 2), (2, 4), (4, 4)]"
        );
        aeqit(
                P.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, null), (null, 1), (2, 2), (4, 1), (1, 4), (null, 2), (2, null)," +
                " (4, 2), (null, null), (2, 4), (4, null), (null, 4), (4, 4)]"
        );
        aeqit(P.pairsLogarithmicOrder(Collections.emptyList()), "[]");
        simpleProviderHelper(
                P.pairsLogarithmicOrder(P.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (0, 2), (2, 0), (1, 1), (3, 0), (0, 3), (4, 0), (2, 1), (5, 0), (1, 2)," +
                " (6, 0), (3, 1), (7, 0), (0, 4), (8, 0), (4, 1), (9, 0), (2, 2), ...]"
        );
        simpleProviderHelper(
                P.pairsLogarithmicOrder(cons(null, P.naturalBigIntegers())),
                "[(null, null), (null, 0), (0, null), (null, 1), (1, null), (0, 0), (2, null), (null, 2), (3, null)," +
                " (1, 0), (4, null), (0, 1), (5, null), (2, 0), (6, null), (null, 3), (7, null), (3, 0), (8, null)," +
                " (1, 1), ...]"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable_Iterable() {
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (4, a), (4, b), (1, c), (1, d), (2, c), (2, d)," +
                " (3, c), (3, d), (4, c), (4, d)]"
        );
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (2, a), (2, b), (4, a), (4, b), (1, c), (1, d), (2, c), (2, d)," +
                " (2, c), (2, d), (4, c), (4, d)]"
        );
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (null, a), (null, b), (4, a), (4, b), (1, c), (1, d), (2, c)," +
                " (2, d), (null, c), (null, d), (4, c), (4, d)]"
        );
        aeqit(P.pairsSquareRootOrder(Collections.emptyList(), fromString("abcd")), "[]");
        aeqit(P.pairsSquareRootOrder(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                P.pairsSquareRootOrder(P.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (0, c), (0, d), (1, c), (1, d)," +
                " (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b), ...]"
        );
        simpleProviderHelper(
                P.pairsSquareRootOrder(fromString("abcd"), P.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (c, 0), (c, 1), (d, 0), (d, 1), (a, 2), (a, 3), (b, 2), (b, 3)," +
                " (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5), ...]"
        );
        simpleProviderHelper(
                P.pairsSquareRootOrder(P.positiveBigIntegers(), P.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (3, -1), (3, -2), (4, -1), (4, -2), (1, -3), (1, -4), (2, -3)," +
                " (2, -4), (3, -3), (3, -4), (4, -3), (4, -4), (5, -1), (5, -2), (6, -1), (6, -2), ...]"
        );
    }

    @Test
    public void testPairsSquareRootOrder_Iterable() {
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2), (4, 1), (4, 2), (1, 3), (1, 4), (2, 3), (2, 4)," +
                " (3, 3), (3, 4), (4, 3), (4, 4)]"
        );
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, 2, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (2, 1), (2, 2), (4, 1), (4, 2), (1, 2), (1, 4), (2, 2), (2, 4)," +
                " (2, 2), (2, 4), (4, 2), (4, 4)]"
        );
        aeqit(
                P.pairsSquareRootOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (null, 1), (null, 2), (4, 1), (4, 2), (1, null), (1, 4)," +
                " (2, null), (2, 4), (null, null), (null, 4), (4, null), (4, 4)]"
        );
        aeqit(P.pairsSquareRootOrder(Collections.emptyList()), "[]");
        simpleProviderHelper(
                P.pairsSquareRootOrder(P.naturalBigIntegers()),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (2, 0), (2, 1), (3, 0), (3, 1), (0, 2), (0, 3), (1, 2), (1, 3)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (4, 0), (4, 1), (5, 0), (5, 1), ...]"
        );
        simpleProviderHelper(
                P.pairsSquareRootOrder(cons(null, P.naturalBigIntegers())),
                "[(null, null), (null, 0), (0, null), (0, 0), (1, null), (1, 0), (2, null), (2, 0), (null, 1)," +
                " (null, 2), (0, 1), (0, 2), (1, 1), (1, 2), (2, 1), (2, 2), (3, null), (3, 0), (4, null), (4, 0)," +
                " ...]"
        );
    }

    private static void permutationsFiniteHelper(@NotNull String input, @NotNull String output) {
        aeqit(map(Testing::its, P.permutationsFinite(readIntegerListWithNulls(input))), output);
    }

    private static void permutationsFiniteHelper(@NotNull List<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, P.permutationsFinite(input)), output);
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
        aeqit(P.stringPermutations(""), "[]");
        aeqit(P.stringPermutations("a"), "[a]");
        aeqit(P.stringPermutations("abc"), "[abc, acb, bac, bca, cab, cba]");
        aeqit(P.stringPermutations("foo"), "[foo, ofo, oof]");
        aeqit(P.stringPermutations("hello"),
                "[hello, helol, heoll, hlelo, hleol, hlleo, hlloe, hloel, hlole, hoell, holel, holle, ehllo, ehlol," +
                " eholl, elhlo, elhol, ellho, elloh, elohl, elolh, eohll, eolhl, eollh, lhelo, lheol, lhleo, lhloe," +
                " lhoel, lhole, lehlo, lehol, lelho, leloh, leohl, leolh, llheo, llhoe, lleho, lleoh, llohe, lloeh," +
                " lohel, lohle, loehl, loelh, lolhe, loleh, ohell, ohlel, ohlle, oehll, oelhl, oellh, olhel, olhle," +
                " olehl, olelh, ollhe, olleh]");
        simpleProviderHelper(P.stringPermutations("Mississippi"),
                "[Miiiisssspp, Miiiissspsp, Miiiissspps, Miiiisspssp, Miiiisspsps, Miiiissppss, Miiiispsssp," +
                " Miiiispssps, Miiiispspss, Miiiisppsss, Miiiipssssp, Miiiipsssps, Miiiipsspss, Miiiipspsss," +
                " Miiiippssss, Miiisissspp, Miiisisspsp, Miiisisspps, Miiisispssp, Miiisispsps, ...]");
    }

    private static void prefixPermutationsHelper(@NotNull String input, @NotNull String output) {
        aeqit(map(Testing::its, P.prefixPermutations(readIntegerList(input))), output);
    }

    private static void prefixPermutationsHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        aeqit(map(Testing::its, P.prefixPermutations(input)), output);
    }

    private static void prefixPermutationsLimitHelper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(map(Testing::its, P.prefixPermutations(input)), output);
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
        prefixPermutationsLimitHelper(P.positiveIntegers(),
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
        aeqit(P.listsLex(size, readIntegerListWithNulls(input)), output);
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
            P.listsLex(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.listsLex(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static <A, B> void pairsLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        aeqit(P.pairsLex(as, toList(bs)), output);
    }

    private static <A, B> void pairsLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull String output
    ) {
        simpleProviderHelper(P.pairsLex(as, toList(bs)), output);
    }

    @Test
    public void testPairsLex() {
        pairsLex_helper(Arrays.asList(1, 2, 3), fromString("abc"),
                "[(1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)]");
        pairsLex_helper(Arrays.asList(1, null, 3), fromString("abc"),
                "[(1, a), (1, b), (1, c), (null, a), (null, b), (null, c), (3, a), (3, b), (3, c)]");
        pairsLex_helper_limit(P.naturalBigIntegers(), fromString("abc"),
                "[(0, a), (0, b), (0, c), (1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)," +
                " (4, a), (4, b), (4, c), (5, a), (5, b), (5, c), (6, a), (6, b), ...]");
        pairsLex_helper(P.naturalBigIntegers(), Collections.emptyList(), "[]");
        pairsLex_helper(Collections.emptyList(), fromString("abc"), "[]");
        pairsLex_helper(Collections.emptyList(), Collections.emptyList(), "[]");
    }

    private static <A, B, C> void triplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        aeqit(P.triplesLex(as, toList(bs), toList(cs)), output);
    }

    private static <A, B, C> void triplesLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull String output
    ) {
        simpleProviderHelper(P.triplesLex(as, toList(bs), toList(cs)), output);
    }

    @Test
    public void testTriplesLex() {
        triplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        triplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), P.booleans(),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (null, a, false), (null, a, true), (null, b, false), (null, b, true), (null, c, false)," +
                " (null, c, true), (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false)," +
                " (3, c, true)]");
        triplesLex_helper_limit(P.naturalBigIntegers(), fromString("abc"), P.booleans(),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (0, c, false), (0, c, true)," +
                " (1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), ...]");
        triplesLex_helper(P.naturalBigIntegers(), fromString("abc"), Collections.emptyList(), "[]");
        triplesLex_helper(Collections.emptyList(), fromString("abc"), P.booleans(), "[]");
        triplesLex_helper(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "[]");
    }

    private static <A, B, C, D> void quadruplesLex_helper(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        aeqit(P.quadruplesLex(as, toList(bs), toList(cs), toList(ds)), output);
    }

    private static <A, B, C, D> void quadruplesLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull String output
    ) {
        simpleProviderHelper(P.quadruplesLex(as, toList(bs), toList(cs), toList(ds)), output);
    }

    @Test
    public void testQuadruplesLex() {
        quadruplesLex_helper(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans(), P.orderings(),
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
        quadruplesLex_helper(Arrays.asList(1, null, 3), fromString("abc"), P.booleans(), P.orderings(),
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
        quadruplesLex_helper_limit(P.naturalBigIntegers(), fromString("abc"), P.booleans(), P.orderings(),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, false, GT), (0, a, true, EQ), (0, a, true, LT)," +
                " (0, a, true, GT), (0, b, false, EQ), (0, b, false, LT), (0, b, false, GT), (0, b, true, EQ)," +
                " (0, b, true, LT), (0, b, true, GT), (0, c, false, EQ), (0, c, false, LT), (0, c, false, GT)," +
                " (0, c, true, EQ), (0, c, true, LT), (0, c, true, GT), (1, a, false, EQ), (1, a, false, LT), ...]");
        quadruplesLex_helper(P.naturalBigIntegers(), fromString("abc"), P.booleans(), Collections.emptyList(), "[]");
        quadruplesLex_helper(Collections.emptyList(), fromString("abc"), P.booleans(), P.orderings(), "[]");
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
        aeqit(P.quintuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es)), output);
    }

    private static <A, B, C, D, E> void quintuplesLex_helper_limit(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull String output
    ) {
        simpleProviderHelper(P.quintuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es)), output);
    }

    @Test
    public void testQuintuplesLex() {
        quintuplesLex_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
                P.booleans(),
                P.orderings(),
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
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, false, GT, yes), (0, a, false, GT, no), (0, a, true, EQ, yes), (0, a, true, EQ, no)," +
                " (0, a, true, LT, yes), (0, a, true, LT, no), (0, a, true, GT, yes), (0, a, true, GT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, false, GT, yes), (0, b, false, GT, no), (0, b, true, EQ, yes), (0, b, true, EQ, no), ...]");
        quintuplesLex_helper(
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Collections.emptyList(),
                "[]"
        );
        quintuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
        aeqit(P.sextuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs)), output);
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
        simpleProviderHelper(P.sextuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs)), output);
    }

    @Test
    public void testSextuplesLex() {
        sextuplesLex_helper(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
                P.booleans(),
                P.orderings(),
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
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Collections.emptyList(),
                "[]"
        );
        sextuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
        aeqit(P.septuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs), toList(gs)), output);
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
                P.septuplesLex(as, toList(bs), toList(cs), toList(ds), toList(es), toList(fs), toList(gs)),
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
                P.booleans(),
                P.orderings(),
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
                P.booleans(),
                P.orderings(),
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
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Collections.emptyList(),
                "[]"
        );
        septuplesLex_helper(
                Collections.emptyList(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
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
        aeqit(P.stringsLex(size, input), output);
    }

    private static void stringsLex_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.stringsLex(size, input), output);
    }

    @Test
    public void testStringsLex_int_String() {
        stringsLex_int_String_helper(0, "", "[]");
        aeq(length(P.stringsLex(0, "")), 1);
        stringsLex_int_String_helper(1, "", "[]");
        aeq(length(P.stringsLex(1, "")), 0);
        stringsLex_int_String_helper(2, "", "[]");
        aeq(length(P.stringsLex(2, "")), 0);
        stringsLex_int_String_helper(3, "", "[]");
        aeq(length(P.stringsLex(3, "")), 0);
        stringsLex_int_String_helper(0, "a", "[]");
        stringsLex_int_String_helper(1, "a", "[a]");
        stringsLex_int_String_helper(2, "a", "[aa]");
        stringsLex_int_String_helper(3, "a", "[aaa]");
        stringsLex_int_String_helper(0, "abc", "[]");
        aeq(length(P.stringsLex(0, "abc")), 1);
        stringsLex_int_String_helper(1, "abc", "[a, b, c]");
        stringsLex_int_String_helper(2, "abc", "[aa, ab, ac, ba, bb, bc, ca, cb, cc]");
        stringsLex_int_String_helper(3, "abc",
                "[aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, bac, bba, bbb, bbc, bca, bcb, bcc, caa," +
                " cab, cac, cba, cbb, cbc, cca, ccb, ccc]");
        stringsLex_int_String_helper(0, "abbc", "[]");
        aeq(length(P.stringsLex(0, "abbc")), 1);
        stringsLex_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringsLex_int_String_helper(2, "abbc", "[aa, ab, ab, ac, ba, bb, bb, bc, ba, bb, bb, bc, ca, cb, cb, cc]");
        stringsLex_int_String_helper(3, "abbc",
                "[aaa, aab, aab, aac, aba, abb, abb, abc, aba, abb, abb, abc, aca, acb, acb, acc, baa, bab, bab," +
                " bac, bba, bbb, bbb, bbc, bba, bbb, bbb, bbc, bca, bcb, bcb, bcc, baa, bab, bab, bac, bba, bbb," +
                " bbb, bbc, bba, bbb, bbb, bbc, bca, bcb, bcb, bcc, caa, cab, cab, cac, cba, cbb, cbb, cbc, cba," +
                " cbb, cbb, cbc, cca, ccb, ccb, ccc]");
        stringsLex_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.stringsLex(0, "Mississippi")), 1);
        stringsLex_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringsLex_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, ii, is, is, ii, is, is, ii, ip, ...]");
        stringsLex_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, MMs, MMs, MMi, MMs, MMs, MMi, MMp, MMp, MMi, MiM, Mii, Mis, Mis, Mii, Mis, Mis, Mii," +
                " Mip, ...]");
        try {
            P.stringsLex(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringsLex(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void listsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.listsShortlex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.stringsShortlex(input), output);
    }

    @Test
    public void testStringsShortlex() {
        stringsShortlex_helper("", "[]");
        aeq(length(P.stringsShortlex("")), 1);
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
        simpleProviderHelper(P.listsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
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
            P.listsShortlexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.listsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.stringsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringsShortlexAtLeast() {
        stringsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(0, "")), 1);
        stringsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(1, "")), 0);
        stringsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(2, "")), 0);
        stringsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(3, "")), 0);
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
            P.stringsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void lists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.lists(size, readIntegerListWithNulls(input)), output);
    }

    private static void lists_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.lists(size, input), output);
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

        lists_int_Iterable_helper(0, P.positiveIntegers(), "[[]]");
        lists_int_Iterable_helper(1, P.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        lists_int_Iterable_helper(2, P.positiveIntegers(),
                "[[1, 1], [1, 2], [2, 1], [2, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 1], [3, 2], [4, 1], [4, 2]," +
                " [3, 3], [3, 4], [4, 3], [4, 4], [1, 5], [1, 6], [2, 5], [2, 6], ...]");
        lists_int_Iterable_helper(3, P.positiveIntegers(),
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
            P.lists(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.lists(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPairs_Iterable_Iterable() {
        aeqit(P.pairs(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d), (3, a), (3, b), (4, a), (4, b)," +
                " (3, c), (3, d), (4, c), (4, d)]");
        aeqit(P.pairs(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d), (null, a), (null, b), (4, a)," +
                " (4, b), (null, c), (null, d), (4, c), (4, d)]");
        aeqit(P.pairs(Collections.emptyList(), fromString("abcd")), "[]");
        aeqit(P.pairs(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(P.pairs(P.naturalBigIntegers(), fromString("abcd")),
                "[(0, a), (0, b), (1, a), (1, b), (0, c), (0, d), (1, c), (1, d), (2, a), (2, b), (3, a), (3, b)," +
                " (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b), ...]");
        simpleProviderHelper(P.pairs(fromString("abcd"), P.naturalBigIntegers()),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (a, 2), (a, 3), (b, 2), (b, 3), (c, 0), (c, 1), (d, 0), (d, 1)," +
                " (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5), ...]");
        simpleProviderHelper(P.pairs(P.positiveBigIntegers(), P.negativeBigIntegers()),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (1, -3), (1, -4), (2, -3), (2, -4), (3, -1), (3, -2), (4, -1)," +
                " (4, -2), (3, -3), (3, -4), (4, -3), (4, -4), (1, -5), (1, -6), (2, -5), (2, -6), ...]");
    }

    private static void pairs_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.pairs(readIntegerListWithNulls(input)), output);
    }

    private static void pairs_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.pairs(input), output);
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
        pairs_Iterable_helper(P.naturalIntegers(),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 2), (2, 3), (3, 2), (3, 3), (0, 4), (0, 5), (1, 4), (1, 5), ...]");
        pairs_Iterable_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
    }

    @Test
    public void testTriples_Iterable_Iterable_Iterable() {
        aeqit(P.triples(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        aeqit(P.triples(Arrays.asList(1, 2, null, 4), fromString("abcd"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (1, d, false), (1, d, true)," +
                " (2, c, false), (2, c, true), (2, d, false), (2, d, true), (null, a, false), (null, a, true)," +
                " (null, b, false), (null, b, true), (4, a, false), (4, a, true), (4, b, false), (4, b, true)," +
                " (null, c, false), (null, c, true), (null, d, false), (null, d, true), (4, c, false)," +
                " (4, c, true), (4, d, false), (4, d, true)]");
        aeqit(P.triples(Collections.emptyList(), fromString("abcd"), P.booleans()), "[]");
        aeqit(P.triples(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(P.triples(P.naturalBigIntegers(), fromString("abcd"), P.booleans()),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (1, a, false), (1, a, true)," +
                " (1, b, false), (1, b, true), (0, c, false), (0, c, true), (0, d, false), (0, d, true)," +
                " (1, c, false), (1, c, true), (1, d, false), (1, d, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), ...]");
        simpleProviderHelper(P.triples(fromString("abcd"), P.booleans(), P.naturalBigIntegers()),
                "[(a, false, 0), (a, false, 1), (a, true, 0), (a, true, 1), (b, false, 0), (b, false, 1)," +
                " (b, true, 0), (b, true, 1), (a, false, 2), (a, false, 3), (a, true, 2), (a, true, 3)," +
                " (b, false, 2), (b, false, 3), (b, true, 2), (b, true, 3), (c, false, 0), (c, false, 1)," +
                " (c, true, 0), (c, true, 1), ...]");
        simpleProviderHelper(P.triples(P.positiveBigIntegers(), P.negativeBigIntegers(), P.characters()),
                "[(1, -1, a), (1, -1, b), (1, -2, a), (1, -2, b), (2, -1, a), (2, -1, b), (2, -2, a), (2, -2, b)," +
                " (1, -1, c), (1, -1, d), (1, -2, c), (1, -2, d), (2, -1, c), (2, -1, d), (2, -2, c), (2, -2, d)," +
                " (1, -3, a), (1, -3, b), (1, -4, a), (1, -4, b), ...]");
    }

    private static void triples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.triples(readIntegerListWithNulls(input)), output);
    }

    private static void triples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.triples(input), output);
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
        triples_Iterable_helper(P.naturalIntegers(),
                "[(0, 0, 0), (0, 0, 1), (0, 1, 0), (0, 1, 1), (1, 0, 0), (1, 0, 1), (1, 1, 0), (1, 1, 1), (0, 0, 2)," +
                " (0, 0, 3), (0, 1, 2), (0, 1, 3), (1, 0, 2), (1, 0, 3), (1, 1, 2), (1, 1, 3), (0, 2, 0), (0, 2, 1)," +
                " (0, 3, 0), (0, 3, 1), ...]");
        triples_Iterable_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
    }

    @Test
    public void testQuadruples_Iterable_Iterable_Iterable_Iterable() {
        aeqit(P.quadruples(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans(), P.orderings()),
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
                " (3, c, true, EQ), (3, c, true, LT), (3, c, false, GT), (3, c, true, GT)]");
        aeqit(P.quadruples(Arrays.asList(1, 2, null, 4), fromString("abcd"), P.booleans(), P.orderings()),
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
                " (4, d, true, GT)]");
        aeqit(P.quadruples(Collections.emptyList(), fromString("abcd"), P.booleans(), P.orderings()), "[]");
        aeqit(
                P.quadruples(
                    Collections.emptyList(),
                    Collections.emptyList(),
                    Collections.emptyList(),
                    Collections.emptyList()
                ),
                "[]"
        );
        simpleProviderHelper(P.quadruples(P.naturalBigIntegers(), fromString("abcd"), P.booleans(), P.orderings()),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, true, EQ), (0, a, true, LT), (0, b, false, EQ)," +
                " (0, b, false, LT), (0, b, true, EQ), (0, b, true, LT), (1, a, false, EQ), (1, a, false, LT)," +
                " (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ), (1, b, false, LT), (1, b, true, EQ)," +
                " (1, b, true, LT), (0, a, false, GT), (0, a, true, GT), (0, b, false, GT), (0, b, true, GT), ...]");
        simpleProviderHelper(P.quadruples(fromString("abcd"), P.booleans(), P.naturalBigIntegers(), P.orderings()),
                "[(a, false, 0, EQ), (a, false, 0, LT), (a, false, 1, EQ), (a, false, 1, LT), (a, true, 0, EQ)," +
                " (a, true, 0, LT), (a, true, 1, EQ), (a, true, 1, LT), (b, false, 0, EQ), (b, false, 0, LT)," +
                " (b, false, 1, EQ), (b, false, 1, LT), (b, true, 0, EQ), (b, true, 0, LT), (b, true, 1, EQ)," +
                " (b, true, 1, LT), (a, false, 0, GT), (a, false, 1, GT), (a, true, 0, GT), (a, true, 1, GT), ...]");
        simpleProviderHelper(
                P.quadruples(P.positiveBigIntegers(), P.negativeBigIntegers(), P.characters(), P.strings()),
                "[(1, -1, a, ), (1, -1, a, a), (1, -1, b, ), (1, -1, b, a), (1, -2, a, ), (1, -2, a, a)," +
                " (1, -2, b, ), (1, -2, b, a), (2, -1, a, ), (2, -1, a, a), (2, -1, b, ), (2, -1, b, a)," +
                " (2, -2, a, ), (2, -2, a, a), (2, -2, b, ), (2, -2, b, a), (1, -1, a, aa), (1, -1, a, b)," +
                " (1, -1, b, aa), (1, -1, b, b), ...]");
    }

    private static void quadruples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.quadruples(readIntegerListWithNulls(input)), output);
    }

    private static void quadruples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.quadruples(input), output);
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
        quadruples_Iterable_helper(P.naturalIntegers(),
                "[(0, 0, 0, 0), (0, 0, 0, 1), (0, 0, 1, 0), (0, 0, 1, 1), (0, 1, 0, 0), (0, 1, 0, 1), (0, 1, 1, 0)," +
                " (0, 1, 1, 1), (1, 0, 0, 0), (1, 0, 0, 1), (1, 0, 1, 0), (1, 0, 1, 1), (1, 1, 0, 0), (1, 1, 0, 1)," +
                " (1, 1, 1, 0), (1, 1, 1, 1), (0, 0, 0, 2), (0, 0, 0, 3), (0, 0, 1, 2), (0, 0, 1, 3), ...]");
        quadruples_Iterable_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
    }

    @Test
    public void testQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable() {
        aeqit(
                P.quintuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                ),
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
        aeqit(
                P.quintuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                ),
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
        aeqit(
                P.quintuples(
                Collections.emptyList(),
                fromString("abcd"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
                ),
                "[]"
        );
        aeqit(
                P.quintuples(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                "[]"
        );
        aeqit(
                take(
                        20,
                        P.quintuples(
                        P.naturalBigIntegers(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                        )
                ),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, true, EQ, yes), (0, a, true, EQ, no), (0, a, true, LT, yes), (0, a, true, LT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, true, EQ, yes), (0, b, true, EQ, no), (0, b, true, LT, yes), (0, b, true, LT, no)," +
                " (1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)]"
        );
        aeqit(
                take(
                        20,
                        P.quintuples(
                                fromString("abcd"),
                                P.booleans(),
                                P.naturalBigIntegers(),
                                P.orderings(),
                                Arrays.asList("yes", "no")
                        )
                ),
                "[(a, false, 0, EQ, yes), (a, false, 0, EQ, no), (a, false, 0, LT, yes), (a, false, 0, LT, no)," +
                " (a, false, 1, EQ, yes), (a, false, 1, EQ, no), (a, false, 1, LT, yes), (a, false, 1, LT, no)," +
                " (a, true, 0, EQ, yes), (a, true, 0, EQ, no), (a, true, 0, LT, yes), (a, true, 0, LT, no)," +
                " (a, true, 1, EQ, yes), (a, true, 1, EQ, no), (a, true, 1, LT, yes), (a, true, 1, LT, no)," +
                " (b, false, 0, EQ, yes), (b, false, 0, EQ, no), (b, false, 0, LT, yes), (b, false, 0, LT, no)]"
        );
        aeqit(
                take(
                        20,
                        P.quintuples(
                                P.positiveBigIntegers(),
                                P.negativeBigIntegers(),
                                P.characters(),
                                P.strings(),
                                P.floats()
                        )
                ),
                "[(1, -1, a, , NaN), (1, -1, a, , Infinity), (1, -1, a, a, NaN), (1, -1, a, a, Infinity)," +
                " (1, -1, b, , NaN), (1, -1, b, , Infinity), (1, -1, b, a, NaN), (1, -1, b, a, Infinity)," +
                " (1, -2, a, , NaN), (1, -2, a, , Infinity), (1, -2, a, a, NaN), (1, -2, a, a, Infinity)," +
                " (1, -2, b, , NaN), (1, -2, b, , Infinity), (1, -2, b, a, NaN), (1, -2, b, a, Infinity)," +
                " (2, -1, a, , NaN), (2, -1, a, , Infinity), (2, -1, a, a, NaN), (2, -1, a, a, Infinity)]"
        );
    }

    private static void quintuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.quintuples(readIntegerListWithNulls(input)), output);
    }

    private static void quintuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.quintuples(input), output);
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
        quintuples_Iterable_helper(P.naturalIntegers(),
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

    @Test
    public void testSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        aeqit(
                P.sextuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
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
        aeqit(
                P.sextuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
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
        aeqit(
                P.sextuples(
                        Collections.emptyList(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "[]"
        );
        aeqit(
                P.sextuples(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                "[]"
        );
        aeqit(
                take(
                        20,
                        P.sextuples(
                                P.naturalBigIntegers(),
                                fromString("abcd"),
                                P.booleans(),
                                P.orderings(),
                                Arrays.asList("yes", "no"),
                                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                        )
                ),
                "[(0, a, false, EQ, yes, Infinity), (0, a, false, EQ, yes, NaN), (0, a, false, EQ, no, Infinity)," +
                " (0, a, false, EQ, no, NaN), (0, a, false, LT, yes, Infinity), (0, a, false, LT, yes, NaN)," +
                " (0, a, false, LT, no, Infinity), (0, a, false, LT, no, NaN), (0, a, true, EQ, yes, Infinity)," +
                " (0, a, true, EQ, yes, NaN), (0, a, true, EQ, no, Infinity), (0, a, true, EQ, no, NaN)," +
                " (0, a, true, LT, yes, Infinity), (0, a, true, LT, yes, NaN), (0, a, true, LT, no, Infinity)," +
                " (0, a, true, LT, no, NaN), (0, b, false, EQ, yes, Infinity), (0, b, false, EQ, yes, NaN)," +
                " (0, b, false, EQ, no, Infinity), (0, b, false, EQ, no, NaN)]"
        );
        aeqit(
                take(
                        20,
                        P.sextuples(
                                fromString("abcd"),
                                P.booleans(),
                                P.naturalBigIntegers(),
                                P.orderings(),
                                Arrays.asList("yes", "no"),
                                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                        )
                ),
                "[(a, false, 0, EQ, yes, Infinity), (a, false, 0, EQ, yes, NaN), (a, false, 0, EQ, no, Infinity)," +
                " (a, false, 0, EQ, no, NaN), (a, false, 0, LT, yes, Infinity), (a, false, 0, LT, yes, NaN)," +
                " (a, false, 0, LT, no, Infinity), (a, false, 0, LT, no, NaN), (a, false, 1, EQ, yes, Infinity)," +
                " (a, false, 1, EQ, yes, NaN), (a, false, 1, EQ, no, Infinity), (a, false, 1, EQ, no, NaN)," +
                " (a, false, 1, LT, yes, Infinity), (a, false, 1, LT, yes, NaN), (a, false, 1, LT, no, Infinity)," +
                " (a, false, 1, LT, no, NaN), (a, true, 0, EQ, yes, Infinity), (a, true, 0, EQ, yes, NaN)," +
                " (a, true, 0, EQ, no, Infinity), (a, true, 0, EQ, no, NaN)]"
        );
        aeqit(
                take(
                        20,
                        P.sextuples(
                                P.positiveBigIntegers(),
                                P.negativeBigIntegers(),
                                P.characters(),
                                P.strings(),
                                P.floats(),
                                P.lists(P.integers())
                        )
                ),
                "[(1, -1, a, , NaN, []), (1, -1, a, , NaN, [0]), (1, -1, a, , Infinity, [])," +
                " (1, -1, a, , Infinity, [0]), (1, -1, a, a, NaN, []), (1, -1, a, a, NaN, [0])," +
                " (1, -1, a, a, Infinity, []), (1, -1, a, a, Infinity, [0]), (1, -1, b, , NaN, [])," +
                " (1, -1, b, , NaN, [0]), (1, -1, b, , Infinity, []), (1, -1, b, , Infinity, [0])," +
                " (1, -1, b, a, NaN, []), (1, -1, b, a, NaN, [0]), (1, -1, b, a, Infinity, [])," +
                " (1, -1, b, a, Infinity, [0]), (1, -2, a, , NaN, []), (1, -2, a, , NaN, [0])," +
                " (1, -2, a, , Infinity, []), (1, -2, a, , Infinity, [0])]"
        );
    }

    private static void sextuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.sextuples(readIntegerListWithNulls(input)), output);
    }

    private static void sextuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.sextuples(input), output);
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
        sextuples_Iterable_helper(P.naturalIntegers(),
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

    @Test
    public void testSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        List<Integer> x = Arrays.asList(1, 0);
        List<Integer> y = Arrays.asList(0, 1);
        aeqit(
                P.septuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
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
        aeqit(
                P.septuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
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
        aeqit(
                P.septuples(
                        Collections.emptyList(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
                "[]"
        );
        aeqit(
                P.septuples(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                "[]"
        );
        aeqit(
                take(
                        20,
                        P.septuples(
                                P.naturalBigIntegers(),
                                fromString("abcd"),
                                P.booleans(),
                                P.orderings(),
                                Arrays.asList("yes", "no"),
                                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                                Arrays.asList(x, y)
                        )
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
                " (0, a, true, EQ, yes, NaN, [1, 0]), (0, a, true, EQ, yes, NaN, [0, 1])]"
        );
        aeqit(
                take(
                        20,
                        P.septuples(
                                fromString("abcd"),
                                P.booleans(),
                                P.naturalBigIntegers(),
                                P.orderings(),
                                Arrays.asList("yes", "no"),
                                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                                Arrays.asList(x, y)
                        )
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
                " (a, false, 1, EQ, yes, NaN, [1, 0]), (a, false, 1, EQ, yes, NaN, [0, 1])]"
        );
        aeqit(
                take(
                        20,
                        P.septuples(
                                P.positiveBigIntegers(),
                                P.negativeBigIntegers(),
                                P.characters(),
                                P.strings(),
                                P.floats(),
                                P.lists(P.integers()),
                                P.bigDecimals()
                        )
                ),
                "[(1, -1, a, , NaN, [], 0), (1, -1, a, , NaN, [], 0.0), (1, -1, a, , NaN, [0], 0)," +
                " (1, -1, a, , NaN, [0], 0.0), (1, -1, a, , Infinity, [], 0), (1, -1, a, , Infinity, [], 0.0)," +
                " (1, -1, a, , Infinity, [0], 0), (1, -1, a, , Infinity, [0], 0.0), (1, -1, a, a, NaN, [], 0)," +
                " (1, -1, a, a, NaN, [], 0.0), (1, -1, a, a, NaN, [0], 0), (1, -1, a, a, NaN, [0], 0.0)," +
                " (1, -1, a, a, Infinity, [], 0), (1, -1, a, a, Infinity, [], 0.0), (1, -1, a, a, Infinity, [0], 0)," +
                " (1, -1, a, a, Infinity, [0], 0.0), (1, -1, b, , NaN, [], 0), (1, -1, b, , NaN, [], 0.0)," +
                " (1, -1, b, , NaN, [0], 0), (1, -1, b, , NaN, [0], 0.0)]"
        );
    }

    private static void septuples_Iterable_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.septuples(readIntegerListWithNulls(input)), output);
    }

    private static void septuples_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.septuples(input), output);
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
        septuples_Iterable_helper(P.naturalIntegers(),
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
        aeqit(P.strings(size, input), output);
    }

    private static void strings_int_String_helper_limit(int size, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.strings(size, input), output);
    }

    @Test
    public void testStrings_int_String() {
        strings_int_String_helper(0, "", "[]");
        aeq(length(P.strings(0, "")), 1);
        strings_int_String_helper(1, "", "[]");
        aeq(length(P.strings(1, "")), 0);
        strings_int_String_helper(2, "", "[]");
        aeq(length(P.strings(2, "")), 0);
        strings_int_String_helper(3, "", "[]");
        aeq(length(P.strings(3, "")), 0);
        strings_int_String_helper(0, "abc", "[]");
        aeq(length(P.strings(0, "abc")), 1);
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
        aeq(length(P.strings(0, "abbc")), 1);
        strings_int_String_helper(1, "abbc", "[a, b, b, c]");
        strings_int_String_helper(2, "abbc", "[aa, ab, ba, bb, ab, ac, bb, bc, ba, bb, ca, cb, bb, bc, cb, cc]");
        strings_int_String_helper(3, "abbc",
                "[aaa, aab, aba, abb, baa, bab, bba, bbb, aab, aac, abb, abc, bab, bac, bbb, bbc, aba, abb, aca," +
                " acb, bba, bbb, bca, bcb, abb, abc, acb, acc, bbb, bbc, bcb, bcc, baa, bab, bba, bbb, caa, cab," +
                " cba, cbb, bab, bac, bbb, bbc, cab, cac, cbb, cbc, bba, bbb, bca, bcb, cba, cbb, cca, ccb, bbb," +
                " bbc, bcb, bcc, cbb, cbc, ccb, ccc]");
        strings_int_String_helper(0, "Mississippi", "[]");
        aeq(length(P.strings(0, "Mississippi")), 1);
        strings_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        strings_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, iM, ii, Ms, Ms, is, is, sM, si, sM, si, ss, ss, ss, ss, Mi, Ms, ii, is, ...]");
        strings_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, MiM, Mii, iMM, iMi, iiM, iii, MMs, MMs, Mis, Mis, iMs, iMs, iis, iis, MsM, Msi, MsM," +
                " Msi, ...]");
        try {
            P.strings(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.strings(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void strings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(P.strings(size), output);
    }

    @Test
    public void testStrings_int() {
        strings_int_helper(0, "[]");
        aeq(length(P.strings(0)), 1);
        strings_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        strings_int_helper(2,
                "[aa, ab, ba, bb, ac, ad, bc, bd, ca, cb, da, db, cc, cd, dc, dd, ae, af, be, bf, ...]");
        strings_int_helper(3,
                "[aaa, aab, aba, abb, baa, bab, bba, bbb, aac, aad, abc, abd, bac, bad, bbc, bbd, aca, acb, ada," +
                " adb, ...]");
        try {
            P.strings(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testLists_Iterable() {
        aeqit(P.lists(Collections.emptyList()), "[[]]");
        simpleProviderHelper(P.lists(Collections.singletonList(5)),
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        simpleProviderHelper(P.lists(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 1], [2, 3], [1, 1, 1, 2], [3, 1], [1, 2, 2], [3, 2]," +
                " [1, 1, 1, 1, 1, 1], ...]");
        simpleProviderHelper(P.lists(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 1], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 1], [1, 3], [1, 1, 1, 2], [2, 2], [1, 2, 2], [2, 3], ...]");
        simpleProviderHelper(P.lists(P.naturalIntegers()),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        simpleProviderHelper(P.lists(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
    }

    @Test
    public void testStrings_String() {
        aeqit(P.strings(""), "[]");
        aeq(length(P.strings("")), 1);
        simpleProviderHelper(P.strings("a"),
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        simpleProviderHelper(P.strings("abc"),
                "[, a, aa, b, aaa, c, ab, aaaa, ba, aab, bb, aaaaa, ac, aba, bc, aaab, ca, abb, cb, aaaaaa, ...]");
        simpleProviderHelper(P.strings("abbc"),
                "[, a, aa, b, aaa, b, ab, c, aaaa, ba, aab, bb, aaaaa, ab, aba, ac, aaab, bb, abb, bc, ...]");
        simpleProviderHelper(P.strings("Mississippi"),
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, iM, s, MMi, s, ii, i, MMMMM, p, Ms, p, ...]");
    }

    @Test
    public void testStrings() {
        simpleProviderHelper(P.strings(),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]");
    }

    private static void listsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.listsAtLeast(minSize, input), output);
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

        listsAtLeast_helper(0, P.naturalIntegers(),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        listsAtLeast_helper(1, P.naturalIntegers(),
                "[[0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 0], [5], [0, 0, 1], [6]," +
                " [1, 1], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], [0, 1, 0], ...]");
        listsAtLeast_helper(2, P.naturalIntegers(),
                "[[0, 0], [0, 0, 0], [0, 1], [0, 0, 0, 0], [1, 0], [0, 0, 1], [1, 1], [0, 0, 0, 0, 0], [0, 2]," +
                " [0, 1, 0], [0, 3], [0, 0, 0, 1], [1, 2], [0, 1, 1], [1, 3], [0, 0, 0, 0, 0, 0], [2, 0], [1, 0, 0]," +
                " [2, 1], [0, 0, 1, 0], ...]");
        listsAtLeast_helper(3, P.naturalIntegers(),
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
            P.listsAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.listsAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsAtLeast_String_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.stringsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringsAtLeast_String() {
        stringsAtLeast_String_helper(0, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(0, "")), 1);
        stringsAtLeast_String_helper(1, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(1, "")), 0);
        stringsAtLeast_String_helper(2, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(2, "")), 0);
        stringsAtLeast_String_helper(3, "", "[]");
        aeq(length(P.stringsShortlexAtLeast(3, "")), 0);
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
            P.stringsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(P.stringsAtLeast(minSize), output);
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
            P.stringsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.distinctListsLex(size, readIntegerListWithNulls(input)), output);
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
            P.distinctListsLex(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctListsLex(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctPairsLex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.distinctPairsLex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.distinctTriplesLex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.distinctQuadruplesLex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.distinctQuintuplesLex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.distinctSextuplesLex(readIntegerListWithNulls(input)), output);
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
        simpleProviderHelper(P.distinctSeptuplesLex(readIntegerListWithNulls(input)), output);
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
        aeqit(P.distinctStringsLex(size, input), output);
    }

    private static void distinctStringsLex_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctStringsLex(size, input), output);
    }

    @Test
    public void testDistinctStringsLex_int_String() {
        distinctStringsLex_int_String_helper(0, "", "[]");
        aeq(length(P.distinctStringsLex(0, "")), 1);
        distinctStringsLex_int_String_helper(1, "", "[]");
        aeq(length(P.distinctStringsLex(1, "")), 0);
        distinctStringsLex_int_String_helper(2, "", "[]");
        aeq(length(P.distinctStringsLex(2, "")), 0);
        distinctStringsLex_int_String_helper(3, "", "[]");
        aeq(length(P.distinctStringsLex(3, "")), 0);
        distinctStringsLex_int_String_helper(0, "a", "[]");
        aeq(length(P.distinctStringsLex(0, "a")), 1);
        distinctStringsLex_int_String_helper(1, "a", "[a]");
        distinctStringsLex_int_String_helper(2, "a", "[]");
        aeq(length(P.distinctStringsLex(2, "a")), 0);
        distinctStringsLex_int_String_helper(3, "a", "[]");
        aeq(length(P.distinctStringsLex(3, "a")), 0);
        distinctStringsLex_int_String_helper(0, "abc", "[]");
        aeq(length(P.distinctStringsLex(0, "abc")), 1);
        distinctStringsLex_int_String_helper(1, "abc", "[a, b, c]");
        distinctStringsLex_int_String_helper(2, "abc", "[ab, ac, ba, bc, ca, cb]");
        distinctStringsLex_int_String_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStringsLex_int_String_helper(0, "abbc", "[]");
        aeq(length(P.distinctStringsLex(0, "abbc")), 1);
        distinctStringsLex_int_String_helper(1, "abbc", "[a, b, b, c]");
        distinctStringsLex_int_String_helper(2, "abbc", "[ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb]");
        distinctStringsLex_int_String_helper(3, "abbc",
                "[abb, abc, abb, abc, acb, acb, bab, bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab," +
                " cab, cba, cbb, cba, cbb]");
        distinctStringsLex_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.distinctStringsLex(0, "Mississippi")), 1);
        distinctStringsLex_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        distinctStringsLex_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, Mp, Mi, iM, is, is, ii, is, is, ii, ip, ip, ii, ...]");
        distinctStringsLex_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Mii, Mis, Mis, Mii, Mip, Mip, Mii, Msi, Mss, Msi, Mss, Mss, Msi, Msp, Msp, Msi, Msi," +
                " Mss, ...]");
        try {
            P.distinctStringsLex(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctStringsLex(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqit(P.distinctListsLex(readIntegerListWithNulls(input)), output);
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
        aeqit(P.distinctStringsLex(""), "[]");
        aeq(length(P.distinctStringsLex("")), 1);
        aeqit(P.distinctStringsLex("a"), "[, a]");
        aeqit(P.distinctStringsLex("abc"), "[, a, ab, abc, ac, acb, b, ba, bac, bc, bca, c, ca, cab, cb, cba]");
        aeqit(P.distinctStringsLex("abcd"),
                "[, a, ab, abc, abcd, abd, abdc, ac, acb, acbd, acd, acdb, ad, adb, adbc, adc, adcb, b, ba, bac," +
                " bacd, bad, badc, bc, bca, bcad, bcd, bcda, bd, bda, bdac, bdc, bdca, c, ca, cab, cabd, cad, cadb," +
                " cb, cba, cbad, cbd, cbda, cd, cda, cdab, cdb, cdba, d, da, dab, dabc, dac, dacb, db, dba, dbac," +
                " dbc, dbca, dc, dca, dcab, dcb, dcba]");
        aeqit(P.distinctStringsLex("abbc"),
                "[, a, ab, abb, abbc, abc, abcb, ab, abb, abbc, abc, abcb, ac, acb, acbb, acb, acbb, b, ba, bab," +
                " babc, bac, bacb, bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, b, ba, bab, babc, bac, bacb," +
                " bb, bba, bbac, bbc, bbca, bc, bca, bcab, bcb, bcba, c, ca, cab, cabb, cab, cabb, cb, cba, cbab," +
                " cbb, cbba, cb, cba, cbab, cbb, cbba]");
        simpleProviderHelper(P.distinctStringsLex("Mississippi"),
                "[, M, Mi, Mis, Miss, Missi, Missis, Mississ, Mississi, Mississip, Mississipp, Mississippi," +
                " Mississipi, Mississipip, Mississip, Mississipp, Mississippi, Mississipi, Mississipip, Mississii," +
                " ...]");
    }

    private static void distinctListsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(P.distinctListsLexAtLeast(minSize, readIntegerListWithNulls(input)), output);
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
            P.distinctListsLexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctListsLexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(P.distinctStringsLexAtLeast(minSize, input), output);
    }

    private static void distinctStringsLexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctStringsLexAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsLexAtLeast_int_String() {
        distinctStringsLexAtLeast_helper(0, "", "[]");
        aeq(length(P.distinctStringsLexAtLeast(0, "")), 1);
        distinctStringsLexAtLeast_helper(1, "", "[]");
        aeq(length(P.distinctStringsLexAtLeast(1, "")), 0);
        distinctStringsLexAtLeast_helper(2, "", "[]");
        aeq(length(P.distinctStringsLexAtLeast(2, "")), 0);
        distinctStringsLexAtLeast_helper(3, "", "[]");
        aeq(length(P.distinctStringsLexAtLeast(3, "")), 0);
        distinctStringsLexAtLeast_helper(0, "a", "[, a]");
        distinctStringsLexAtLeast_helper(1, "a", "[a]");
        distinctStringsLexAtLeast_helper(2, "a", "[]");
        aeq(length(P.distinctStringsLexAtLeast(2, "a")), 0);
        distinctStringsLexAtLeast_helper(3, "a", "[]");
        aeq(length(P.distinctStringsLexAtLeast(3, "a")), 0);
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
            P.distinctStringsLexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctStringsLexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctListsShortlex_helper(@NotNull String input, @NotNull String output) {
        aeqit(P.distinctListsShortlex(readIntegerListWithNulls(input)), output);
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
        aeqit(P.distinctStringsShortlex(""), "[]");
        aeq(length(P.distinctStringsShortlex("")), 1);
        aeqit(P.distinctStringsShortlex("a"), "[, a]");
        aeqit(P.distinctStringsShortlex("abc"), "[, a, b, c, ab, ac, ba, bc, ca, cb, abc, acb, bac, bca, cab, cba]");
        aeqit(P.distinctStringsShortlex("abcd"),
                "[, a, b, c, d, ab, ac, ad, ba, bc, bd, ca, cb, cd, da, db, dc, abc, abd, acb, acd, adb, adc, bac," +
                " bad, bca, bcd, bda, bdc, cab, cad, cba, cbd, cda, cdb, dab, dac, dba, dbc, dca, dcb, abcd, abdc," +
                " acbd, acdb, adbc, adcb, bacd, badc, bcad, bcda, bdac, bdca, cabd, cadb, cbad, cbda, cdab, cdba," +
                " dabc, dacb, dbac, dbca, dcab, dcba]");
        aeqit(P.distinctStringsShortlex("abbc"),
                "[, a, b, b, c, ab, ab, ac, ba, bb, bc, ba, bb, bc, ca, cb, cb, abb, abc, abb, abc, acb, acb, bab," +
                " bac, bba, bbc, bca, bcb, bab, bac, bba, bbc, bca, bcb, cab, cab, cba, cbb, cba, cbb, abbc, abcb," +
                " abbc, abcb, acbb, acbb, babc, bacb, bbac, bbca, bcab, bcba, babc, bacb, bbac, bbca, bcab, bcba," +
                " cabb, cabb, cbab, cbba, cbab, cbba]");
        simpleProviderHelper(P.distinctStringsShortlex("Mississippi"),
                "[, M, i, s, s, i, s, s, i, p, p, i, Mi, Ms, Ms, Mi, Ms, Ms, Mi, Mp, ...]");
    }

    private static void distinctListsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqit(P.distinctListsShortlexAtLeast(minSize, readIntegerListWithNulls(input)), output);
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
            P.distinctListsShortlexAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctListsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        aeqit(P.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    private static void distinctStringsShortlexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctStringsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsShortlexAtLeast_int_String() {
        distinctStringsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(0, "")), 1);
        distinctStringsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(1, "")), 0);
        distinctStringsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(2, "")), 0);
        distinctStringsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(3, "")), 0);
        distinctStringsShortlexAtLeast_helper(0, "a", "[, a]");
        distinctStringsShortlexAtLeast_helper(1, "a", "[a]");
        distinctStringsShortlexAtLeast_helper(2, "a", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(2, "a")), 0);
        distinctStringsShortlexAtLeast_helper(3, "a", "[]");
        aeq(length(P.distinctStringsShortlexAtLeast(3, "a")), 0);
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
            P.distinctStringsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctStringsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctLists_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.distinctLists(size, readIntegerListWithNulls(input)), output);
    }

    private static void distinctLists_int_Iterable_helper(
            int size,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctLists(size, input), output);
    }

    private static void distinctLists_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            P.distinctLists(size, readIntegerListWithNulls(input));
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
        distinctLists_int_Iterable_helper(0, P.positiveIntegers(), "[[]]");
        distinctLists_int_Iterable_helper(1, P.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        distinctLists_int_Iterable_helper(2, P.positiveIntegers(),
                "[[1, 2], [1, 3], [2, 1], [2, 3], [1, 4], [1, 5], [2, 4], [2, 5], [3, 1], [3, 2], [4, 1], [4, 2]," +
                " [3, 4], [3, 5], [4, 3], [4, 5], [1, 6], [1, 7], [2, 6], [2, 7], ...]");
        distinctLists_int_Iterable_helper(3, P.positiveIntegers(),
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
        simpleProviderHelper(P.distinctPairs(readIntegerListWithNulls(input)), output);
    }

    private static void distinctPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctPairs(input), output);
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
        distinctPairs_helper(P.naturalIntegers(),
                "[(0, 1), (0, 2), (1, 0), (1, 2), (0, 3), (0, 4), (1, 3), (1, 4), (2, 0), (2, 1), (3, 0), (3, 1)," +
                " (2, 3), (2, 4), (3, 2), (3, 4), (0, 5), (0, 6), (1, 5), (1, 6), ...]");
        distinctPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
    }

    private static void distinctTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.distinctTriples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctTriples(input), output);
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
        distinctTriples_helper(P.naturalIntegers(),
                "[(0, 1, 2), (0, 1, 3), (0, 2, 1), (0, 2, 3), (1, 0, 2), (1, 0, 3), (1, 2, 0), (1, 2, 3), (0, 1, 4)," +
                " (0, 1, 5), (0, 2, 4), (0, 2, 5), (1, 0, 4), (1, 0, 5), (1, 2, 4), (1, 2, 5), (0, 3, 1), (0, 3, 2)," +
                " (0, 4, 1), (0, 4, 2), ...]");
        distinctTriples_helper(repeat(1),
                "[(1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1), (1, 1, 1)," +
                " (1, 1, 1), (1, 1, 1), ...]");
    }

    private static void distinctQuadruples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.distinctQuadruples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctQuadruples(input), output);
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
        distinctQuadruples_helper(P.naturalIntegers(),
                "[(0, 1, 2, 3), (0, 1, 2, 4), (0, 1, 3, 2), (0, 1, 3, 4), (0, 2, 1, 3), (0, 2, 1, 4), (0, 2, 3, 1)," +
                " (0, 2, 3, 4), (1, 0, 2, 3), (1, 0, 2, 4), (1, 0, 3, 2), (1, 0, 3, 4), (1, 2, 0, 3), (1, 2, 0, 4)," +
                " (1, 2, 3, 0), (1, 2, 3, 4), (0, 1, 2, 5), (0, 1, 2, 6), (0, 1, 3, 5), (0, 1, 3, 6), ...]");
        distinctQuadruples_helper(repeat(1),
                "[(1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1)," +
                " (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), (1, 1, 1, 1), ...]");
    }

    private static void distinctQuintuples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.distinctQuintuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctQuintuples(input), output);
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
        distinctQuintuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.distinctSextuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctSextuples(input), output);
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
        distinctSextuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.distinctSeptuples(readIntegerListWithNulls(input)), output);
    }

    private static void distinctSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.distinctSeptuples(input), output);
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
        distinctSeptuples_helper(P.naturalIntegers(),
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
        aeqit(P.distinctStrings(size, input), output);
    }

    private static void distinctStrings_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctStrings(size, input), output);
    }

    @Test
    public void testDistinctStrings_int_String() {
        distinctStrings_int_String_helper(0, "", "[]");
        aeq(length(P.distinctStrings(0, "")), 1);
        distinctStrings_int_String_helper(1, "", "[]");
        aeq(length(P.distinctStrings(1, "")), 0);
        distinctStrings_int_String_helper(2, "", "[]");
        aeq(length(P.distinctStrings(2, "")), 0);
        distinctStrings_int_String_helper(3, "", "[]");
        aeq(length(P.distinctStrings(3, "")), 0);
        distinctStrings_int_String_helper(0, "a", "[]");
        distinctStrings_int_String_helper(1, "a", "[a]");
        distinctStrings_int_String_helper(2, "a", "[]");
        aeq(length(P.distinctStrings(2, "a")), 0);
        distinctStrings_int_String_helper(3, "a", "[]");
        aeq(length(P.distinctStrings(3, "a")), 0);
        distinctStrings_int_String_helper(0, "abc", "[]");
        aeq(length(P.distinctStrings(0, "abc")), 1);
        distinctStrings_int_String_helper(1, "abc", "[a, b, c]");
        distinctStrings_int_String_helper(2, "abc", "[ab, ac, ba, bc, ca, cb]");
        distinctStrings_int_String_helper(3, "abc", "[abc, acb, bac, bca, cab, cba]");
        distinctStrings_int_String_helper(0, "abbc", "[]");
        aeq(length(P.distinctStrings(0, "abbc")), 1);
        distinctStrings_int_String_helper(1, "abbc", "[a, b, b, c]");
        distinctStrings_int_String_helper(2, "abbc", "[ab, ab, ba, bb, ac, bc, ba, bb, ca, cb, bc, cb]");
        distinctStrings_int_String_helper(3, "abbc",
                "[abb, abc, abb, abc, bab, bac, bba, bbc, acb, acb, bca, bcb, bab, bac, bba, bbc, cab, cab, cba," +
                " cbb, bca, bcb, cba, cbb]");
        distinctStrings_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.distinctStrings(0, "Mississippi")), 1);
        distinctStrings_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        distinctStrings_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, iM, is, Ms, Mi, is, ii, sM, si, sM, si, ss, si, ss, si, Ms, Ms, is, is, ...]");
        distinctStrings_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Msi, Mss, iMs, iMs, isM, iss, Mii, Mis, Msi, Mss, iMi, iMs, isi, iss, Msi, Mss, Mii," +
                " Mis, ...]");
        try {
            P.distinctStrings(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctStrings(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStrings_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(P.distinctStrings(size), output);
    }

    @Test
    public void testDistinctStrings_int() {
        distinctStrings_int_helper(0, "[]");
        aeq(length(P.distinctStrings(0)), 1);
        distinctStrings_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        distinctStrings_int_helper(2,
                "[ab, ac, ba, bc, ad, ae, bd, be, ca, cb, da, db, cd, ce, dc, de, af, ag, bf, bg, ...]");
        distinctStrings_int_helper(3,
                "[abc, abd, acb, acd, bac, bad, bca, bcd, abe, abf, ace, acf, bae, baf, bce, bcf, adb, adc, aeb," +
                " aec, ...]");
        distinctStrings_int_helper(65537, "[]");
        try {
            P.distinctStrings(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testDistinctLists_Iterable() {
        aeqit(P.distinctLists(Collections.emptyList()), "[[]]");
        simpleProviderHelper(P.distinctLists(Collections.singletonList(5)), "[[], [5]]");
        simpleProviderHelper(P.distinctLists(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 2], [2], [1, 2, 3], [3], [1, 3], [2, 1], [2, 3], [1, 3, 2], [3, 1], [2, 1, 3]," +
                " [3, 2], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        simpleProviderHelper(P.distinctLists(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 2], [2], [1, 2, 2], [2], [1, 2], [3], [1, 2, 2, 3], [2, 1], [1, 2, 3], [2, 2]," +
                " [1, 3], [1, 2, 2], [2, 3], [1, 2, 3], [2, 1], [2, 1, 2], [2, 2], [1, 2, 3, 2], ...]");
        simpleProviderHelper(P.distinctLists(P.naturalIntegers()),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        simpleProviderHelper(P.distinctLists(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
    }

    @Test
    public void testDistinctStrings_String() {
        aeqit(P.distinctStrings(""), "[]");
        aeq(length(P.distinctStrings("")), 1);
        simpleProviderHelper(P.distinctStrings("a"), "[, a]");
        simpleProviderHelper(P.distinctStrings("abc"),
                "[, a, ab, b, abc, c, ac, ba, bc, acb, ca, bac, cb, bca, cab, cba]");
        simpleProviderHelper(P.distinctStrings("abbc"),
                "[, a, ab, b, abb, b, ab, c, abbc, ba, abc, bb, ac, abb, bc, abc, ba, bab, bb, abcb, ...]");
        simpleProviderHelper(P.distinctStrings("Mississippi"),
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, iM, s, Mis, s, is, i, Missi, p, Ms, p, ...]");
    }

    @Test
    public void testDistinctStrings() {
        simpleProviderHelper(P.distinctStrings(),
                "[, a, ab, b, abc, c, ac, d, abcd, e, ba, f, abd, g, bc, h, abcde, i, ad, j, ...]");
    }

    private static void distinctListsAtLeast_helper(
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctListsAtLeast(minSize, input), output);
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

        distinctListsAtLeast_helper(0, P.naturalIntegers(),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        distinctListsAtLeast_helper(1, P.naturalIntegers(),
                "[[0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 0], [5], [0, 1, 3], [6]," +
                " [1, 2], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], [0, 2, 1], ...]");
        distinctListsAtLeast_helper(2, P.naturalIntegers(),
                "[[0, 1], [0, 1, 2], [0, 2], [0, 1, 2, 3], [1, 0], [0, 1, 3], [1, 2], [0, 1, 2, 3, 4], [0, 3]," +
                " [0, 2, 1], [0, 4], [0, 1, 2, 4], [1, 3], [0, 2, 3], [1, 4], [0, 1, 2, 3, 4, 5], [2, 0], [1, 0, 2]," +
                " [2, 1], [0, 1, 3, 2], ...]");
        distinctListsAtLeast_helper(3, P.naturalIntegers(),
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
            P.distinctListsAtLeast(-1, Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctListsAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsAtLeast_String_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.distinctStringsAtLeast(minSize, input), output);
    }

    @Test
    public void testDistinctStringsAtLeast_String() {
        distinctStringsAtLeast_String_helper(0, "", "[]");
        aeq(length(P.distinctStringsAtLeast(0, "")), 1);
        distinctStringsAtLeast_String_helper(1, "", "[]");
        aeq(length(P.distinctStringsAtLeast(1, "")), 0);
        distinctStringsAtLeast_String_helper(2, "", "[]");
        aeq(length(P.distinctStringsAtLeast(2, "")), 0);
        distinctStringsAtLeast_String_helper(3, "", "[]");
        aeq(length(P.distinctStringsAtLeast(3, "")), 0);
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
            P.distinctStringsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.distinctStringsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void distinctStringsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(P.distinctStringsAtLeast(minSize), output);
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
            P.distinctStringsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void bagsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.bagsLex(size, readIntegerListWithNulls(input)), output);
    }

    private static void bagsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            P.bagsLex(size, readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagPairsLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagPairsLex_fail_helper(@NotNull String input) {
        try {
            P.bagPairsLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagTriplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagTriplesLex_fail_helper(@NotNull String input) {
        try {
            P.bagTriplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagQuadruplesLex_fail_helper(@NotNull String input) {
        try {
            P.bagQuadruplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagQuintuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagQuintuplesLex_fail_helper(@NotNull String input) {
        try {
            P.bagQuintuplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagSextuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagSextuplesLex_fail_helper(@NotNull String input) {
        try {
            P.bagSextuplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.bagSeptuplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void bagSeptuplesLex_fail_helper(@NotNull String input) {
        try {
            P.bagSeptuplesLex(readIntegerListWithNulls(input));
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
        aeqit(P.stringBagsLex(size, input), output);
    }

    private static void stringBagsLex_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringBagsLex(size, input), output);
    }

    @Test
    public void testStringBagsLex() {
        stringBagsLex_helper(0, "", "[]");
        aeq(length(P.stringBagsLex(0, "")), 1);
        stringBagsLex_helper(1, "", "[]");
        aeq(length(P.stringBagsLex(1, "")), 0);
        stringBagsLex_helper(2, "", "[]");
        aeq(length(P.stringBagsLex(2, "")), 0);
        stringBagsLex_helper(3, "", "[]");
        aeq(length(P.stringBagsLex(3, "")), 0);
        stringBagsLex_helper(0, "a", "[]");
        aeq(length(P.stringBagsLex(0, "a")), 1);
        stringBagsLex_helper(1, "a", "[a]");
        stringBagsLex_helper(2, "a", "[aa]");
        stringBagsLex_helper(3, "a", "[aaa]");
        stringBagsLex_helper(0, "abc", "[]");
        aeq(length(P.stringBagsLex(0, "abc")), 1);
        stringBagsLex_helper(1, "abc", "[a, b, c]");
        stringBagsLex_helper(2, "abc", "[aa, ab, ac, bb, bc, cc]");
        stringBagsLex_helper(3, "abc", "[aaa, aab, aac, abb, abc, acc, bbb, bbc, bcc, ccc]");
        stringBagsLex_helper(0, "abbc", "[]");
        aeq(length(P.stringBagsLex(0, "abbc")), 1);
        stringBagsLex_helper(1, "abbc", "[a, b, b, c]");
        stringBagsLex_helper(2, "abbc", "[aa, ab, ab, ac, bb, bb, bc, bb, bc, cc]");
        stringBagsLex_helper(3, "abbc",
                "[aaa, aab, aab, aac, abb, abb, abc, abb, abc, acc, bbb, bbb, bbc, bbb, bbc, bcc, bbb, bbc, bcc," +
                " ccc]");
        stringBagsLex_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.stringBagsLex(0, "Mississippi")), 1);
        stringBagsLex_helper_limit(1, "Mississippi", "[M, i, i, i, i, p, p, s, s, s, s]");
        stringBagsLex_helper_limit(2, "Mississippi",
                "[MM, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ii, ip, ip, is, is, is, ...]");
        stringBagsLex_helper_limit(3, "Mississippi",
                "[MMM, MMi, MMi, MMi, MMi, MMp, MMp, MMs, MMs, MMs, MMs, Mii, Mii, Mii, Mii, Mip, Mip, Mis, Mis," +
                " Mis, ...]");
        try {
            P.stringBagsLex(-1, "");
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            P.stringBagsLex(-1, "abc");
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void bagsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.bagsShortlex(readIntegerList(input)), output);
    }

    private static void bagsShortlex_fail_helper(@NotNull String input) {
        try {
            toList(P.bagsShortlex(readIntegerListWithNulls(input)));
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
        simpleProviderHelper(P.stringBagsShortlex(input), output);
    }

    @Test
    public void testStringBagsShortlex() {
        stringBagsShortlex_helper("", "[]");
        aeq(length(P.stringBagsShortlex("")), 1);
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
        simpleProviderHelper(P.bagsShortlexAtLeast(minSize, readIntegerList(input)), output);
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
            P.bagsShortlexAtLeast(-1, Collections.<Integer>emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.bagsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toList(P.bagsShortlexAtLeast(1, Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(P.bagsShortlexAtLeast(1, Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    private static void stringBagsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.stringBagsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringBagsShortlexAtLeast() {
        stringBagsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(P.stringBagsShortlexAtLeast(0, "")), 1);
        stringBagsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(P.stringBagsShortlexAtLeast(1, "")), 0);
        stringBagsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(P.stringBagsShortlexAtLeast(2, "")), 0);
        stringBagsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(P.stringBagsShortlexAtLeast(3, "")), 0);
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
            P.stringBagsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringBagsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void bags_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.bags(size, readIntegerList(input)), output);
    }

    private static void bags_int_Iterable_helper(int size, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bags(size, input), output);
    }

    private static void bags_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            toList(P.bags(size, readIntegerListWithNulls(input)));
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
        bags_int_Iterable_helper(0, P.positiveIntegers(), "[[]]");
        bags_int_Iterable_helper(1, P.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        bags_int_Iterable_helper(2, P.positiveIntegers(),
                "[[1, 1], [1, 2], [2, 2], [2, 3], [1, 3], [1, 4], [2, 4], [2, 5], [3, 3], [3, 4], [4, 4], [4, 5]," +
                " [3, 5], [3, 6], [4, 6], [4, 7], [1, 5], [1, 6], [2, 6], [2, 7], ...]");
        bags_int_Iterable_helper(3, P.positiveIntegers(),
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
        simpleProviderHelper(P.bagPairs(readIntegerList(input)), output);
    }

    private static void bagPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagPairs(input), output);
    }

    private static void bagPairs_fail_helper(@NotNull String input) {
        try {
            toList(P.bagPairs(readIntegerListWithNulls(input)));
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
        bagPairs_helper(P.naturalIntegers(),
                "[(0, 0), (0, 1), (1, 1), (1, 2), (0, 2), (0, 3), (1, 3), (1, 4), (2, 2), (2, 3), (3, 3), (3, 4)," +
                " (2, 4), (2, 5), (3, 5), (3, 6), (0, 4), (0, 5), (1, 5), (1, 6), ...]");
        bagPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
        bagPairs_fail_helper("[1, null, 3]");
    }

    private static void bagTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.bagTriples(readIntegerList(input)), output);
    }

    private static void bagTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagTriples(input), output);
    }

    private static void bagTriples_fail_helper(@NotNull String input) {
        try {
            toList(P.bagTriples(readIntegerListWithNulls(input)));
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
        bagTriples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.bagQuadruples(readIntegerList(input)), output);
    }

    private static void bagQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagQuadruples(input), output);
    }

    private static void bagQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(P.bagQuadruples(readIntegerListWithNulls(input)));
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
        bagQuadruples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.bagQuintuples(readIntegerList(input)), output);
    }

    private static void bagQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagQuintuples(input), output);
    }

    private static void bagQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(P.bagQuintuples(readIntegerListWithNulls(input)));
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
        bagQuintuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.bagSextuples(readIntegerList(input)), output);
    }

    private static void bagSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagSextuples(input), output);
    }

    private static void bagSextuples_fail_helper(@NotNull String input) {
        try {
            toList(P.bagSextuples(readIntegerListWithNulls(input)));
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
        bagSextuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.bagSeptuples(readIntegerList(input)), output);
    }

    private static void bagSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagSeptuples(input), output);
    }

    private static void bagSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(P.bagSeptuples(readIntegerListWithNulls(input)));
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
        bagSeptuples_helper(P.naturalIntegers(),
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
        aeqit(P.stringBags(size, input), output);
    }

    private static void stringBags_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringBags(size, input), output);
    }

    @Test
    public void testStringBags_int_String() {
        stringBags_int_String_helper(0, "", "[]");
        aeq(length(P.stringBags(0, "")), 1);
        stringBags_int_String_helper(1, "", "[]");
        aeq(length(P.stringBags(1, "")), 0);
        stringBags_int_String_helper(2, "", "[]");
        aeq(length(P.stringBags(2, "")), 0);
        stringBags_int_String_helper(3, "", "[]");
        aeq(length(P.stringBags(3, "")), 0);
        stringBags_int_String_helper(0, "a", "[]");
        stringBags_int_String_helper(1, "a", "[a]");
        stringBags_int_String_helper(2, "a", "[aa]");
        stringBags_int_String_helper(3, "a", "[aaa]");
        stringBags_int_String_helper(0, "abc", "[]");
        aeq(length(P.stringBags(0, "abc")), 1);
        stringBags_int_String_helper(1, "abc", "[a, b, c]");
        stringBags_int_String_helper(2, "abc", "[aa, ab, bb, bc, ac, cc]");
        stringBags_int_String_helper(3, "abc", "[aaa, aab, abb, abc, bbb, bbc, bcc, aac, acc, ccc]");
        stringBags_int_String_helper(0, "abbc", "[]");
        aeq(length(P.stringBags(0, "abbc")), 1);
        stringBags_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringBags_int_String_helper(2, "abbc", "[aa, ab, bb, bb, ab, ac, bc, bb, bc, cc]");
        stringBags_int_String_helper(3, "abbc",
                "[aaa, aab, abb, abb, bbb, bbb, bbb, bbc, aab, aac, abc, bbc, abb, abc, acc, bcc, bbb, bbc, bcc," +
                " ccc]");
        stringBags_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.stringBags(0, "Mississippi")), 1);
        stringBags_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringBags_int_String_helper_limit(2, "Mississippi",
                "[MM, Mi, ii, is, Ms, Ms, is, ii, ss, ss, ss, is, is, ss, ss, ss, Mi, Ms, is, is, ...]");
        stringBags_int_String_helper_limit(3, "Mississippi",
                "[MMM, MMi, Mii, Mis, iii, iis, iss, iss, MMs, MMs, Mis, Mii, iis, iii, iis, iss, Mss, Mss, Mss," +
                " Mis, ...]");
        try {
            P.stringBags(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringBags(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringBags_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(P.stringBags(size), output);
    }

    @Test
    public void testStringBags_int() {
        stringBags_int_helper(0, "[]");
        aeq(length(P.stringBags(0)), 1);
        stringBags_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        stringBags_int_helper(2,
                "[aa, ab, bb, bc, ac, ad, bd, be, cc, cd, dd, de, ce, cf, df, dg, ae, af, bf, bg, ...]");
        stringBags_int_helper(3,
                "[aaa, aab, abb, abc, bbb, bbc, bcc, bcd, aac, aad, abd, abe, bbd, bbe, bce, bcf, acc, acd, add," +
                " ade, ...]");
        try {
            P.stringBags(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBags_Iterable() {
        aeqit(P.bags(Collections.<Integer>emptyList()), "[[]]");
        simpleProviderHelper(P.bags(Collections.singletonList(5)),
                "[[], [5], [5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]");
        simpleProviderHelper(P.bags(Arrays.asList(1, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [3], [1, 2], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 3]," +
                " [1, 1, 1, 1, 1], [1, 3], [1, 2, 2], [1, 1, 1, 2], [1, 2, 3], [1, 1, 1, 1, 1, 1], [3, 3]," +
                " [2, 2, 2], [1, 1, 2, 2], ...]");
        simpleProviderHelper(P.bags(Arrays.asList(1, 2, 2, 3)),
                "[[], [1], [1, 1], [2], [1, 1, 1], [2], [1, 2], [3], [1, 1, 1, 1], [2, 2], [1, 1, 2], [2, 2]," +
                " [1, 1, 1, 1, 1], [1, 2], [1, 2, 2], [1, 3], [1, 1, 1, 2], [2, 3], [1, 2, 2], [1, 1, 1, 1, 1, 1]," +
                " ...]");
        simpleProviderHelper(P.bags(P.naturalIntegers()),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        simpleProviderHelper(P.bags(repeat(1)),
                "[[], [1], [1, 1], [1], [1, 1, 1], [1], [1, 1], [1], [1, 1, 1, 1], [1], [1, 1], [1], [1, 1, 1], [1]," +
                " [1, 1], [1], [1, 1, 1, 1, 1], [1], [1, 1], [1], ...]");
        try {
            toList(P.bags(Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(P.bags(Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testStringBags_String() {
        aeqit(P.stringBags(""), "[]");
        aeq(length(P.stringBags("")), 1);
        simpleProviderHelper(P.stringBags("a"),
                "[, a, aa, aaa, aaaa, aaaaa, aaaaaa, aaaaaaa, aaaaaaaa, aaaaaaaaa, aaaaaaaaaa, aaaaaaaaaaa," +
                " aaaaaaaaaaaa, aaaaaaaaaaaaa, aaaaaaaaaaaaaa, aaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa, ...]");
        simpleProviderHelper(P.stringBags("abc"),
                "[, a, aa, b, aaa, c, ab, aaaa, bb, aab, bc, aaaaa, ac, abb, aaab, abc, aaaaaa, cc, bbb, aabb, ...]");
        simpleProviderHelper(P.stringBags("abbc"),
                "[, a, aa, b, aaa, b, ab, c, aaaa, bb, aab, bb, aaaaa, ab, abb, ac, aaab, bc, abb, aaaaaa, ...]");
        simpleProviderHelper(P.stringBags("Mississippi"),
                "[, M, MM, i, MMM, s, Mi, s, MMMM, i, ii, s, MMi, s, is, i, MMMMM, p, Ms, p, ...]");
    }

    @Test
    public void testStringBags() {
        simpleProviderHelper(P.stringBags(),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, bb, f, aab, g, bc, h, aaaaa, i, ac, j, ...]");
    }

    private static void bagsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.bagsAtLeast(minSize, input), output);
    }

    private static void bagsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        bagsAtLeast_helper(minSize, readIntegerList(input), output);
    }

    private static void bagsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(P.bagsAtLeast(minSize, readIntegerListWithNulls(input)));
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

        bagsAtLeast_helper(0, P.naturalIntegers(),
                "[[], [0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], ...]");
        bagsAtLeast_helper(1, P.naturalIntegers(),
                "[[0], [0, 0], [1], [0, 0, 0], [2], [0, 1], [3], [0, 0, 0, 0], [4], [1, 1], [5], [0, 0, 1], [6]," +
                " [1, 2], [7], [0, 0, 0, 0, 0], [8], [0, 2], [9], [0, 1, 1], ...]");
        bagsAtLeast_helper(2, P.naturalIntegers(),
                "[[0, 0], [0, 0, 0], [0, 1], [0, 0, 0, 0], [1, 1], [0, 0, 1], [1, 2], [0, 0, 0, 0, 0], [0, 2]," +
                " [0, 1, 1], [0, 3], [0, 0, 0, 1], [1, 3], [0, 1, 2], [1, 4], [0, 0, 0, 0, 0, 0], [2, 2], [1, 1, 1]," +
                " [2, 3], [0, 0, 1, 1], ...]");
        bagsAtLeast_helper(3, P.naturalIntegers(),
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
        simpleProviderHelper(P.stringBagsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringBagsAtLeast_String() {
        stringBagsAtLeast_String_helper(0, "", "[]");
        aeq(length(P.stringBagsAtLeast(0, "")), 1);
        stringBagsAtLeast_String_helper(1, "", "[]");
        aeq(length(P.stringBagsAtLeast(1, "")), 0);
        stringBagsAtLeast_String_helper(2, "", "[]");
        aeq(length(P.stringBagsAtLeast(2, "")), 0);
        stringBagsAtLeast_String_helper(3, "", "[]");
        aeq(length(P.stringBagsAtLeast(3, "")), 0);
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
            P.stringBagsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringBagsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringBagsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(P.stringBagsAtLeast(minSize), output);
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
            P.stringBagsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsetsLex_int_List_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.subsetsLex(size, readIntegerList(input)), output);
    }

    private static void subsetsLex_int_List_fail_helper(int size, @NotNull String input) {
        try {
            P.subsetsLex(size, readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetPairsLex(readIntegerList(input)), output);
    }

    private static void subsetPairsLex_fail_helper(@NotNull String input) {
        try {
            P.subsetPairsLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetTriplesLex(readIntegerList(input)), output);
    }

    private static void subsetTriplesLex_fail_helper(@NotNull String input) {
        try {
            P.subsetTriplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetQuadruplesLex(readIntegerListWithNulls(input)), output);
    }

    private static void subsetQuadruplesLex_fail_helper(@NotNull String input) {
        try {
            P.subsetQuadruplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetQuintuplesLex(readIntegerList(input)), output);
    }

    private static void subsetQuintuplesLex_fail_helper(@NotNull String input) {
        try {
            P.subsetQuintuplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetSextuplesLex(readIntegerList(input)), output);
    }

    private static void subsetSextuplesLex_fail_helper(@NotNull String input) {
        try {
            P.subsetSextuplesLex(readIntegerListWithNulls(input));
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
        simpleProviderHelper(P.subsetSeptuplesLex(readIntegerList(input)), output);
    }

    private static void subsetSeptuplesLex_fail_helper(@NotNull String input) {
        try {
            P.subsetSeptuplesLex(readIntegerListWithNulls(input));
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
        aeqit(P.stringSubsetsLex(size, input), output);
    }

    private static void stringSubsetsLex_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringSubsetsLex(size, input), output);
    }

    @Test
    public void testStringSubsetsLex_int_String() {
        stringSubsetsLex_helper(0, "", "[]");
        aeq(length(P.stringSubsetsLex(0, "")), 1);
        stringSubsetsLex_helper(1, "", "[]");
        aeq(length(P.stringSubsetsLex(1, "")), 0);
        stringSubsetsLex_helper(2, "", "[]");
        aeq(length(P.stringSubsetsLex(2, "")), 0);
        stringSubsetsLex_helper(3, "", "[]");
        aeq(length(P.stringSubsetsLex(3, "")), 0);
        stringSubsetsLex_helper(0, "a", "[]");
        aeq(length(P.stringSubsetsLex(0, "a")), 1);
        stringSubsetsLex_helper(1, "a", "[a]");
        stringSubsetsLex_helper(2, "a", "[]");
        aeq(length(P.stringSubsetsLex(2, "a")), 0);
        stringSubsetsLex_helper(3, "a", "[]");
        aeq(length(P.stringSubsetsLex(3, "a")), 0);
        stringSubsetsLex_helper(0, "abc", "[]");
        aeq(length(P.stringSubsetsLex(0, "abc")), 1);
        stringSubsetsLex_helper(1, "abc", "[a, b, c]");
        stringSubsetsLex_helper(2, "abc", "[ab, ac, bc]");
        stringSubsetsLex_helper(3, "abc", "[abc]");
        stringSubsetsLex_helper(0, "abbc", "[]");
        aeq(length(P.stringSubsetsLex(0, "abbc")), 1);
        stringSubsetsLex_helper(1, "abbc", "[a, b, b, c]");
        stringSubsetsLex_helper(2, "abbc", "[ab, ab, ac, bb, bc, bc]");
        stringSubsetsLex_helper(3, "abbc", "[abb, abc, abc, bbc]");
        stringSubsetsLex_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.stringSubsetsLex(0, "Mississippi")), 1);
        stringSubsetsLex_helper_limit(1, "Mississippi", "[M, i, i, i, i, p, p, s, s, s, s]");
        stringSubsetsLex_helper_limit(2, "Mississippi",
                "[Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, Ms, Ms, ii, ii, ii, ip, ip, is, is, is, is, ii, ...]");
        stringSubsetsLex_helper_limit(3, "Mississippi",
                "[Mii, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mii, Mip, Mip, Mis, Mis, Mis, Mis, Mii, Mip," +
                " Mip, ...]");
        try {
            P.stringSubsetsLex(-1, "");
            fail();
        } catch (ArithmeticException ignored) {}
        try {
            P.stringSubsetsLex(-1, "abc");
            fail();
        } catch (ArithmeticException ignored) {}
    }

    private static void subsetsLex_List_helper(@NotNull String input, @NotNull String output) {
        aeqit(P.subsetsLex(readIntegerList(input)), output);
    }

    private static void subsetsLex_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetsLex(readIntegerListWithNulls(input)));
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
        aeqit(P.stringSubsetsLex(""), "[]");
        aeq(length(P.stringSubsetsLex("")), 1);
        aeqit(P.stringSubsetsLex("a"), "[, a]");
        aeqit(P.stringSubsetsLex("abc"), "[, a, ab, abc, ac, b, bc, c]");
        aeqit(P.stringSubsetsLex("abcd"), "[, a, ab, abc, abcd, abd, ac, acd, ad, b, bc, bcd, bd, c, cd, d]");
        aeqit(P.stringSubsetsLex("abbc"), "[, a, ab, abb, abbc, abc, ab, abc, ac, b, bb, bbc, bc, b, bc, c]");
        simpleProviderHelper(P.stringSubsetsLex("Mississippi"),
                "[, M, Mi, Mii, Miii, Miiii, Miiiip, Miiiipp, Miiiipps, Miiiippss, Miiiippsss, Miiiippssss," +
                " Miiiippsss, Miiiippss, Miiiippsss, Miiiippss, Miiiipps, Miiiippss, Miiiippsss, Miiiippss, ...]");
    }

    private static void subsetsLexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        aeqit(P.subsetsLexAtLeast(minSize, readIntegerList(input)), output);
    }

    private static void subsetsLexAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(P.subsetsLexAtLeast(minSize, readIntegerListWithNulls(input)));
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
        aeqit(P.stringSubsetsLexAtLeast(minSize, input), output);
    }

    private static void stringSubsetsLexAtLeast_helper_limit(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringSubsetsLexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsLexAtLeast_int_String() {
        stringSubsetsLexAtLeast_helper(0, "", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(0, "")), 1);
        stringSubsetsLexAtLeast_helper(1, "", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(1, "")), 0);
        stringSubsetsLexAtLeast_helper(2, "", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(2, "")), 0);
        stringSubsetsLexAtLeast_helper(3, "", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(3, "")), 0);
        stringSubsetsLexAtLeast_helper(0, "a", "[, a]");
        stringSubsetsLexAtLeast_helper(1, "a", "[a]");
        stringSubsetsLexAtLeast_helper(2, "a", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(2, "a")), 0);
        stringSubsetsLexAtLeast_helper(3, "a", "[]");
        aeq(length(P.stringSubsetsLexAtLeast(3, "a")), 0);
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
            P.stringSubsetsLexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringSubsetsLexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsetsShortlex_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.subsetsShortlex(readIntegerList(input)), output);
    }

    private static void subsetsShortlex_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetsShortlex(readIntegerListWithNulls(input)));
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
        simpleProviderHelper(P.stringSubsetsShortlex(input), output);
    }

    @Test
    public void testStringSubsetsShortlex() {
        stringSubsetsShortlex_helper("", "[]");
        aeq(length(P.stringSubsetsShortlex("")), 1);
        stringSubsetsShortlex_helper("a", "[, a]");
        stringSubsetsShortlex_helper("abc", "[, a, b, c, ab, ac, bc, abc]");
        stringSubsetsShortlex_helper("abbc","[, a, b, b, c, ab, ab, ac, bb, bc, bc, abb, abc, abc, bbc, abbc]");
        stringSubsetsShortlex_helper("Mississippi",
                "[, M, i, i, i, i, p, p, s, s, s, s, Mi, Mi, Mi, Mi, Mp, Mp, Ms, Ms, ...]");
    }

    private static void subsetsShortlexAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.subsetsShortlexAtLeast(minSize, readIntegerList(input)), output);
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
            P.subsetsShortlexAtLeast(-1, Collections.<Integer>emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.subsetsShortlexAtLeast(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            toList(P.subsetsShortlexAtLeast(1, Arrays.asList(1, null, 3)));
            fail();
        } catch (NullPointerException ignored) {}
        try {
            toList(P.subsetsShortlexAtLeast(1, Collections.<Integer>singletonList(null)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    private static void stringSubsetsShortlexAtLeast_helper(
            int minSize,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringSubsetsShortlexAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsShortlexAtLeast() {
        stringSubsetsShortlexAtLeast_helper(0, "", "[]");
        aeq(length(P.stringSubsetsShortlexAtLeast(0, "")), 1);
        stringSubsetsShortlexAtLeast_helper(1, "", "[]");
        aeq(length(P.stringSubsetsShortlexAtLeast(1, "")), 0);
        stringSubsetsShortlexAtLeast_helper(2, "", "[]");
        aeq(length(P.stringSubsetsShortlexAtLeast(2, "")), 0);
        stringSubsetsShortlexAtLeast_helper(3, "", "[]");
        aeq(length(P.stringSubsetsShortlexAtLeast(3, "")), 0);
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
            P.stringSubsetsShortlexAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringSubsetsShortlexAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsets_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        aeqit(P.subsets(size, readIntegerList(input)), output);
    }

    private static void subsets_int_Iterable_helper(
            int size,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.subsets(size, input), output);
    }

    private static void subsets_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            toList(P.subsets(size, readIntegerListWithNulls(input)));
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
        subsets_int_Iterable_helper(0, P.positiveIntegers(), "[[]]");
        subsets_int_Iterable_helper(1, P.positiveIntegers(),
                "[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18]," +
                " [19], [20], ...]");
        subsets_int_Iterable_helper(2, P.positiveIntegers(),
                "[[1, 2], [1, 3], [2, 3], [2, 4], [1, 4], [1, 5], [2, 5], [2, 6], [3, 4], [3, 5], [4, 5], [4, 6]," +
                " [3, 6], [3, 7], [4, 7], [4, 8], [1, 6], [1, 7], [2, 7], [2, 8], ...]");
        subsets_int_Iterable_helper(3, P.positiveIntegers(),
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
        simpleProviderHelper(P.subsetPairs(readIntegerList(input)), output);
    }

    private static void subsetPairs_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetPairs(input), output);
    }

    private static void subsetPairs_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetPairs(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetPairs() {
        subsetPairs_helper("[]", "[]");
        subsetPairs_helper("[5]", "[]");
        subsetPairs_helper("[1, 2, 3, 4]", "[(1, 2), (1, 3), (2, 3), (2, 4), (1, 4), (3, 4)]");
        subsetPairs_helper("[1, 2, 2, 4]", "[(1, 2), (1, 2), (2, 2), (2, 4), (1, 4), (2, 4)]");
        subsetPairs_helper(P.naturalIntegers(),
                "[(0, 1), (0, 2), (1, 2), (1, 3), (0, 3), (0, 4), (1, 4), (1, 5), (2, 3), (2, 4), (3, 4), (3, 5)," +
                " (2, 5), (2, 6), (3, 6), (3, 7), (0, 5), (0, 6), (1, 6), (1, 7), ...]");
        subsetPairs_helper(repeat(1),
                "[(1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1)," +
                " (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), ...]");
        subsetPairs_fail_helper("[1, null, 3]");
    }

    private static void subsetTriples_helper(@NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.subsetTriples(readIntegerList(input)), output);
    }

    private static void subsetTriples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetTriples(input), output);
    }

    private static void subsetTriples_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetTriples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetTriples() {
        subsetTriples_helper("[]", "[]");
        subsetTriples_helper("[5]", "[]");
        subsetTriples_helper("[1, 2, 3, 4]", "[(1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)]");
        subsetTriples_helper("[1, 2, 2, 4]", "[(1, 2, 2), (1, 2, 4), (1, 2, 4), (2, 2, 4)]");
        subsetTriples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.subsetQuadruples(readIntegerList(input)), output);
    }

    private static void subsetQuadruples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetQuadruples(input), output);
    }

    private static void subsetQuadruples_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetQuadruples(readIntegerListWithNulls(input)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testSubsetQuadruples() {
        subsetQuadruples_helper("[]", "[]");
        subsetQuadruples_helper("[5]", "[]");
        subsetQuadruples_helper("[1, 2, 3, 4]", "[(1, 2, 3, 4)]");
        subsetQuadruples_helper("[1, 2, 2, 4]", "[(1, 2, 2, 4)]");
        subsetQuadruples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.subsetQuintuples(readIntegerList(input)), output);
    }

    private static void subsetQuintuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetQuintuples(input), output);
    }

    private static void subsetQuintuples_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetQuintuples(readIntegerListWithNulls(input)));
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
        subsetQuintuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.subsetSextuples(readIntegerList(input)), output);
    }

    private static void subsetSextuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetSextuples(input), output);
    }

    private static void subsetSextuples_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetSextuples(readIntegerListWithNulls(input)));
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
        subsetSextuples_helper(P.naturalIntegers(),
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
        simpleProviderHelper(P.subsetSeptuples(readIntegerList(input)), output);
    }

    private static void subsetSeptuples_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetSeptuples(input), output);
    }

    private static void subsetSeptuples_fail_helper(@NotNull String input) {
        try {
            toList(P.subsetSeptuples(readIntegerListWithNulls(input)));
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
        subsetSeptuples_helper(P.naturalIntegers(),
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
        aeqit(P.stringSubsets(size, input), output);
    }

    private static void stringSubsets_int_String_helper_limit(
            int size,
            @NotNull String input,
            @NotNull String output
    ) {
        simpleProviderHelper(P.stringSubsets(size, input), output);
    }

    @Test
    public void testStringSubsets_int_String() {
        stringSubsets_int_String_helper(0, "", "[]");
        aeq(length(P.stringSubsets(0, "")), 1);
        stringSubsets_int_String_helper(1, "", "[]");
        aeq(length(P.stringSubsets(1, "")), 0);
        stringSubsets_int_String_helper(2, "", "[]");
        aeq(length(P.stringSubsets(2, "")), 0);
        stringSubsets_int_String_helper(3, "", "[]");
        aeq(length(P.stringSubsets(3, "")), 0);
        stringSubsets_int_String_helper(0, "a", "[]");
        stringSubsets_int_String_helper(1, "a", "[a]");
        stringSubsets_int_String_helper(2, "a", "[]");
        aeq(length(P.stringSubsets(2, "a")), 0);
        stringSubsets_int_String_helper(3, "a", "[]");
        aeq(length(P.stringSubsets(3, "a")), 0);
        stringSubsets_int_String_helper(0, "abc", "[]");
        aeq(length(P.stringSubsets(0, "abc")), 1);
        stringSubsets_int_String_helper(1, "abc", "[a, b, c]");
        stringSubsets_int_String_helper(2, "abc", "[ab, ac, bc]");
        stringSubsets_int_String_helper(3, "abc", "[abc]");
        stringSubsets_int_String_helper(0, "abbc", "[]");
        aeq(length(P.stringSubsets(0, "abbc")), 1);
        stringSubsets_int_String_helper(1, "abbc", "[a, b, b, c]");
        stringSubsets_int_String_helper(2, "abbc", "[ab, ab, bb, bc, ac, bc]");
        stringSubsets_int_String_helper(3, "abbc", "[abb, abc, abc, bbc]");
        stringSubsets_int_String_helper_limit(0, "Mississippi", "[]");
        aeq(length(P.stringSubsets(0, "Mississippi")), 1);
        stringSubsets_int_String_helper_limit(1, "Mississippi", "[M, i, s, s, i, s, s, i, p, p, i]");
        stringSubsets_int_String_helper_limit(2, "Mississippi",
                "[Mi, Ms, is, is, Ms, Mi, ii, is, ss, is, is, ss, ss, ss, ss, is, Ms, Ms, is, ii, ...]");
        stringSubsets_int_String_helper_limit(3, "Mississippi",
                "[Mis, Mis, Mss, Mis, iss, iis, iis, iss, Mii, Mis, Mss, Mss, iss, iss, iss, iis, Mis, Mss, Mis," +
                " Mis, ...]");
        try {
            P.stringSubsets(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringSubsets(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringSubsets_int_helper(int size, @NotNull String output) {
        simpleProviderHelper(P.stringSubsets(size), output);
    }

    @Test
    public void testStringSubsets_int() {
        stringSubsets_int_helper(0, "[]");
        aeq(length(P.stringSubsets(0)), 1);
        stringSubsets_int_helper(1, "[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, ...]");
        stringSubsets_int_helper(2,
                "[ab, ac, bc, bd, ad, ae, be, bf, cd, ce, de, df, cf, cg, dg, dh, af, ag, bg, bh, ...]");
        stringSubsets_int_helper(3,
                "[abc, abd, acd, ace, bcd, bce, bde, bdf, abe, abf, acf, acg, bcf, bcg, bdg, bdh, ade, adf, aef," +
                " aeg, ...]");
        try {
            P.stringSubsets(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void subsets_Iterable_helper(@NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsets(input), output);
    }

    private static void subsets_Iterable_helper(@NotNull String input, @NotNull String output) {
        subsets_Iterable_helper(readIntegerList(input), output);
    }

    private static void subsets_Iterable_fail_helper(@NotNull String input) {
        try {
            toList(P.subsets(readIntegerListWithNulls(input)));
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
        subsets_Iterable_helper(P.naturalIntegers(),
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
        aeqit(P.stringSubsets(""), "[]");
        aeq(length(P.stringSubsets("")), 1);
        simpleProviderHelper(P.stringSubsets("a"), "[, a]");
        simpleProviderHelper(P.stringSubsets("abc"), "[, a, ab, b, abc, c, ac, bc]");
        simpleProviderHelper(P.stringSubsets("abbc"),
                "[, a, ab, b, abb, b, ab, c, abbc, bb, abc, bc, ac, abc, bc, bbc]");
        simpleProviderHelper(P.stringSubsets("Mississippi"),
                "[, M, Mi, i, Mis, s, Ms, s, Miss, i, is, s, Mis, s, is, i, Miiss, p, Ms, p, ...]");
    }

    @Test
    public void testStringSubsets() {
        simpleProviderHelper(P.stringSubsets(),
                "[, a, ab, b, abc, c, ac, d, abcd, e, bc, f, abd, g, bd, h, abcde, i, ad, j, ...]");
    }

    private static void subsetsAtLeast_helper(int minSize, @NotNull Iterable<Integer> input, @NotNull String output) {
        simpleProviderHelper(P.subsetsAtLeast(minSize, input), output);
    }

    private static void subsetsAtLeast_helper(int minSize, @NotNull String input, @NotNull String output) {
        subsetsAtLeast_helper(minSize, readIntegerList(input), output);
    }

    private static void subsetsAtLeast_fail_helper(int minSize, @NotNull String input) {
        try {
            toList(P.subsetsAtLeast(minSize, readIntegerListWithNulls(input)));
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

        subsetsAtLeast_helper(0, P.naturalIntegers(),
                "[[], [0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 2], [5], [0, 1, 3], [6]," +
                " [1, 3], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], ...]");
        subsetsAtLeast_helper(1, P.naturalIntegers(),
                "[[0], [0, 1], [1], [0, 1, 2], [2], [0, 2], [3], [0, 1, 2, 3], [4], [1, 2], [5], [0, 1, 3], [6]," +
                " [1, 3], [7], [0, 1, 2, 3, 4], [8], [0, 3], [9], [0, 2, 3], ...]");
        subsetsAtLeast_helper(2, P.naturalIntegers(),
                "[[0, 1], [0, 1, 2], [0, 2], [0, 1, 2, 3], [1, 2], [0, 1, 3], [1, 3], [0, 1, 2, 3, 4], [0, 3]," +
                " [0, 2, 3], [0, 4], [0, 1, 2, 4], [1, 4], [0, 2, 4], [1, 5], [0, 1, 2, 3, 4, 5], [2, 3], [1, 2, 3]," +
                " [2, 4], [0, 1, 3, 4], ...]");
        subsetsAtLeast_helper(3, P.naturalIntegers(),
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
        simpleProviderHelper(P.stringSubsetsAtLeast(minSize, input), output);
    }

    @Test
    public void testStringSubsetsAtLeast_String() {
        stringSubsetsAtLeast_String_helper(0, "", "[]");
        aeq(length(P.stringSubsetsAtLeast(0, "")), 1);
        stringSubsetsAtLeast_String_helper(1, "", "[]");
        aeq(length(P.stringSubsetsAtLeast(1, "")), 0);
        stringSubsetsAtLeast_String_helper(2, "", "[]");
        aeq(length(P.stringSubsetsAtLeast(2, "")), 0);
        stringSubsetsAtLeast_String_helper(3, "", "[]");
        aeq(length(P.stringSubsetsAtLeast(3, "")), 0);
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
            P.stringSubsetsAtLeast(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.stringSubsetsAtLeast(-1, "abc");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void stringSubsetsAtLeast_helper(int minSize, @NotNull String output) {
        simpleProviderHelper(P.stringSubsetsAtLeast(minSize), output);
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
            P.stringSubsetsAtLeast(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void cartesianProduct_helper(@NotNull String input, @NotNull String output) {
        aeqit(P.cartesianProduct(readIntegerListWithNullsLists(input)), output);
    }

    private static void cartesianProduct_fail_helper(@NotNull String input) {
        try {
            P.cartesianProduct(readIntegerListWithNullsListsWithNulls(input));
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
        simpleProviderHelper(map(Testing::its, P.repeatingIterables(input)), output);
    }

    private static void repeatingIterables_helper(@NotNull String input, @NotNull String output) {
        repeatingIterables_helper(readIntegerListWithNulls(input), output);
    }

    private static void repeatingIterables_fail_helper(@NotNull String input) {
        try {
            P.repeatingIterables(readIntegerListWithNulls(input));
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
                P.naturalIntegers(),
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
        simpleProviderHelper(map(Testing::its, P.repeatingIterablesDistinctAtLeast(minSize, xs)), output);
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
            P.repeatingIterablesDistinctAtLeast(minSize, readIntegerListWithNulls(xs));
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
        repeatingIterablesDistinctAtLeast_helper(2, P.naturalIntegers(),
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
        repeatingIterablesDistinctAtLeast_helper(5, P.naturalIntegers(),
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
        aeqit(P.sublists(readIntegerListWithNulls(input)), output);
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
        aeqit(P.substrings(input), output);
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
        simpleProviderHelper(P.listsWithElement(x, input), output);
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
        listsWithElement_helper(null, P.positiveIntegers(),
                "[[null], [null, 1], [1, null], [1, null, 1], [null, 1, 1], [null, 2], [1, null, 1, 1]," +
                " [1, null, 2], [1, 1, null], [1, 1, null, 1], [2, null], [2, null, 1], [1, 1, null, 1, 1]," +
                " [1, 1, null, 2], [2, null, 1, 1], [2, null, 2], [null, 1, 1, 1], [null, 3], [1, null, 1, 1, 1]," +
                " [1, null, 3], ...]");
        listsWithElement_helper(3, P.positiveIntegers(),
                "[[3], [3, 1], [1, 3], [1, 3, 1], [3, 1, 1], [3, 2], [1, 3, 1, 1], [1, 3, 2], [1, 1, 3]," +
                " [1, 1, 3, 1], [2, 3], [2, 3, 1], [1, 1, 3, 1, 1], [1, 1, 3, 2], [2, 3, 1, 1], [2, 3, 2]," +
                " [3, 1, 1, 1], [3, 3], [1, 3, 1, 1, 1], [1, 3, 3], ...]");
        listsWithElement_helper(0, repeat(1),
                "[[0], [0, 1], [1, 0], [1, 0, 1], [0, 1, 1], [0, 1], [1, 0, 1, 1], [1, 0, 1], [1, 1, 0]," +
                " [1, 1, 0, 1], [1, 0], [1, 0, 1], [1, 1, 0, 1, 1], [1, 1, 0, 1], [1, 0, 1, 1], [1, 0, 1]," +
                " [0, 1, 1, 1], [0, 1], [1, 0, 1, 1, 1], [1, 0, 1], ...]");
    }

    private static void stringsWithChar_char_String_helper(char c, @NotNull String input, @NotNull String output) {
        simpleProviderHelper(P.stringsWithChar(c, input), output);
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
        simpleProviderHelper(P.stringsWithChar(c), output);
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
        simpleProviderHelper(P.subsetsWithElement(x, input), output);
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
        subsetsWithElement_helper(3, P.positiveIntegers(),
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
        simpleProviderHelper(P.stringSubsetsWithChar(c, input), output);
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
        simpleProviderHelper(P.stringSubsetsWithChar(c), output);
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
        simpleProviderHelper(P.listsWithSublists(Collections.emptyList(), Collections.singletonList(0)), "[]");
        simpleProviderHelper(P.listsWithSublists(Collections.emptyList(), Collections.emptyList()), "[]");
        simpleProviderHelper(
                P.listsWithSublists(Collections.singletonList(Collections.emptyList()), Collections.emptyList()),
                "[[]]"
        );
        simpleProviderHelper(
                P.listsWithSublists(Collections.singletonList(Collections.emptyList()), Collections.singletonList(0)),
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
                P.listsWithSublists(Collections.singletonList(Arrays.asList(1, 0, 1)), Collections.singletonList(0)),
                "[[1, 0, 1], [1, 0, 1, 0], [0, 1, 0, 1], [0, 1, 0, 1, 0], [1, 0, 1, 0, 0], [1, 0, 1, 0, 0, 0]," +
                " [0, 1, 0, 1, 0, 0], [0, 1, 0, 1, 0, 0, 0], [0, 0, 1, 0, 1], [0, 0, 1, 0, 1, 0]," +
                " [0, 0, 0, 1, 0, 1], [0, 0, 0, 1, 0, 1, 0], [0, 0, 1, 0, 1, 0, 0], [0, 0, 1, 0, 1, 0, 0, 0]," +
                " [0, 0, 0, 1, 0, 1, 0, 0], [0, 0, 0, 1, 0, 1, 0, 0, 0], [1, 0, 1, 0, 0, 0, 0]," +
                " [1, 0, 1, 0, 0, 0, 0, 0], [0, 1, 0, 1, 0, 0, 0, 0], [0, 1, 0, 1, 0, 0, 0, 0, 0], ...]"
        );
        simpleProviderHelper(
                P.listsWithSublists(Collections.singletonList(Arrays.asList(1, 0, 1)), P.positiveIntegers()),
                "[[1, 0, 1], [1, 0, 1, 1], [1, 1, 0, 1], [1, 1, 0, 1, 1], [1, 0, 1, 1, 1], [1, 0, 1, 2]," +
                " [1, 1, 0, 1, 1, 1], [1, 1, 0, 1, 2], [1, 1, 1, 0, 1], [1, 1, 1, 0, 1, 1], [2, 1, 0, 1]," +
                " [2, 1, 0, 1, 1], [1, 1, 1, 0, 1, 1, 1], [1, 1, 1, 0, 1, 2], [2, 1, 0, 1, 1, 1], [2, 1, 0, 1, 2]," +
                " [1, 0, 1, 1, 1, 1], [1, 0, 1, 3], [1, 1, 0, 1, 1, 1, 1], [1, 1, 0, 1, 3], ...]"
        );
        simpleProviderHelper(
                P.listsWithSublists(map(i -> Arrays.asList(i, i), P.positiveIntegers()), Collections.singletonList(0)),
                "[[1, 1], [1, 1, 0], [2, 2], [2, 2, 0], [0, 1, 1], [0, 1, 1, 0], [0, 2, 2], [0, 2, 2, 0]," +
                " [1, 1, 0, 0], [1, 1, 0, 0, 0], [2, 2, 0, 0], [2, 2, 0, 0, 0], [0, 1, 1, 0, 0], [0, 1, 1, 0, 0, 0]," +
                " [0, 2, 2, 0, 0], [0, 2, 2, 0, 0, 0], [3, 3], [3, 3, 0], [4, 4], [4, 4, 0], ...]"
        );
        simpleProviderHelper(
                P.listsWithSublists(map(i -> Arrays.asList(i, i), P.positiveIntegers()), P.positiveIntegers()),
                "[[1, 1], [1, 1, 1], [2, 2], [2, 2, 1], [1, 1, 1, 1], [1, 2, 2], [1, 2, 2, 1], [1, 1, 2]," +
                " [2, 2, 1, 1], [2, 2, 2], [1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 2, 2, 1, 1], [1, 2, 2, 2], [3, 3]," +
                " [3, 3, 1], [4, 4], [4, 4, 1], [1, 3, 3], [1, 3, 3, 1], ...]"
        );
        try {
            toList(P.listsWithSublists(Collections.singletonList(null), Collections.singletonList(0)));
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String_String() {
        simpleProviderHelper(P.stringsWithSubstrings(Collections.emptyList(), ""), "[]");
        simpleProviderHelper(P.stringsWithSubstrings(Collections.emptyList(), charsToString(range('a', 'z'))), "[]");
        simpleProviderHelper(P.stringsWithSubstrings(Collections.singletonList(""), ""), "[]");
        aeq(length(P.stringsWithSubstrings(Collections.singletonList(""), "")), 1);
        simpleProviderHelper(
                P.stringsWithSubstrings(Collections.singletonList(""), charsToString(range('a', 'z'))),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]"
        );
        simpleProviderHelper(
                P.stringsWithSubstrings(Collections.singletonList("cat"), charsToString(range('a', 'z'))),
                "[cat, cata, acat, acata, cataa, catb, acataa, acatb, aacat, aacata, bcat, bcata, aacataa, aacatb," +
                " bcataa, bcatb, cataaa, catc, acataaa, acatc, ...]"
        );
        simpleProviderHelper(
                P.stringsWithSubstrings(
                        map(d -> Double.toString(d), P.positiveDoubles()),
                        charsToString(range('a', 'z'))
                ),
                "[Infinity, Infinitya, 1.0, 1.0a, aInfinity, aInfinitya, a1.0, a1.0a, Infinityaa, Infinityb, 1.0aa," +
                " 1.0b, aInfinityaa, aInfinityb, a1.0aa, a1.0b, 2.0, 2.0a, 3.0, 3.0a, ...]"
        );
        try {
            toList(P.stringsWithSubstrings(Collections.singletonList(null), charsToString(range('a', 'z'))));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testStringsWithSubstrings_Iterable_String() {
        simpleProviderHelper(P.stringsWithSubstrings(Collections.emptyList()), "[]");
        simpleProviderHelper(P.stringsWithSubstrings(Collections.singletonList("")),
                "[, a, aa, b, aaa, c, ab, d, aaaa, e, ba, f, aab, g, bb, h, aaaaa, i, ac, j, ...]");
        simpleProviderHelper(P.stringsWithSubstrings(Collections.singletonList("cat")),
                "[cat, cata, acat, acata, cataa, catb, acataa, acatb, aacat, aacata, bcat, bcata, aacataa, aacatb," +
                " bcataa, bcatb, cataaa, catc, acataaa, acatc, ...]");
        simpleProviderHelper(P.stringsWithSubstrings(map(d -> Double.toString(d), P.positiveDoubles())),
                "[Infinity, Infinitya, 1.0, 1.0a, aInfinity, aInfinitya, a1.0, a1.0a, Infinityaa, Infinityb, 1.0aa," +
                " 1.0b, aInfinityaa, aInfinityb, a1.0aa, a1.0b, 2.0, 2.0a, 3.0, 3.0a, ...]");
        try {
            toList(P.stringsWithSubstrings(Collections.singletonList(null)));
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    private static void maps_helper(@NotNull String keys, @NotNull Iterable<Integer> values, @NotNull String output) {
        simpleProviderHelper(P.maps(readIntegerListWithNulls(keys), values), output);
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
        maps_helper("[]", P.positiveIntegers(), "[{}]");
        maps_helper("[4]", "[]", "[]");
        maps_helper("[4]", "[4]", "[{4=4}]");
        maps_helper("[4]", "[1, 2, 3]", "[{4=1}, {4=2}, {4=3}]");
        maps_helper("[4]", "[1, null, 3]", "[{4=1}, {4=null}, {4=3}]");
        maps_helper("[4]", P.positiveIntegers(),
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
        maps_helper("[1, 2, 3]", P.positiveIntegers(),
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
        maps_helper("[1, null, 3]", P.positiveIntegers(),
                "[{null=1, 1=1, 3=1}, {null=1, 1=1, 3=2}, {null=2, 1=1, 3=1}, {null=2, 1=1, 3=2}," +
                " {null=1, 1=2, 3=1}, {null=1, 1=2, 3=2}, {null=2, 1=2, 3=1}, {null=2, 1=2, 3=2}," +
                " {null=1, 1=1, 3=3}, {null=1, 1=1, 3=4}, {null=2, 1=1, 3=3}, {null=2, 1=1, 3=4}," +
                " {null=1, 1=2, 3=3}, {null=1, 1=2, 3=4}, {null=2, 1=2, 3=3}, {null=2, 1=2, 3=4}," +
                " {null=3, 1=1, 3=1}, {null=3, 1=1, 3=2}, {null=4, 1=1, 3=1}, {null=4, 1=1, 3=2}, ...]");
    }

    private static void randomProvidersFixedScales_helper(int scale, int secondaryScale, @NotNull String output) {
        simpleProviderHelper(P.randomProvidersFixedScales(scale, secondaryScale), output);
    }

    @Test
    public void testRandomProvidersFixedScales() {
        randomProvidersFixedScales_helper(8, 32,
                "[RandomProvider[@-7948823947390831374, 8, 32], RandomProvider[@7302477663894715351, 8, 32]," +
                " RandomProvider[@5113382706114603938, 8, 32], RandomProvider[@-1774083719213728003, 8, 32]," +
                " RandomProvider[@8538952961045368838, 8, 32], RandomProvider[@7023783968100629274, 8, 32]," +
                " RandomProvider[@8397262361995169820, 8, 32], RandomProvider[@7421997658690496630, 8, 32]," +
                " RandomProvider[@-7898255109170938473, 8, 32], RandomProvider[@3868661580815414054, 8, 32]," +
                " RandomProvider[@453733778809658833, 8, 32], RandomProvider[@-2636879068627078241, 8, 32]," +
                " RandomProvider[@6142487595818599495, 8, 32], RandomProvider[@4681726659604324256, 8, 32]," +
                " RandomProvider[@1906427249022382973, 8, 32], RandomProvider[@8356990522398738440, 8, 32]," +
                " RandomProvider[@1947303986473887049, 8, 32], RandomProvider[@-7907745475302030450, 8, 32]," +
                " RandomProvider[@-9092377260523661242, 8, 32], RandomProvider[@-1031230974728655171, 8, 32], ...]");
        randomProvidersFixedScales_helper(0, 0,
                "[RandomProvider[@-7948823947390831374, 0, 0], RandomProvider[@7302477663894715351, 0, 0]," +
                " RandomProvider[@5113382706114603938, 0, 0], RandomProvider[@-1774083719213728003, 0, 0]," +
                " RandomProvider[@8538952961045368838, 0, 0], RandomProvider[@7023783968100629274, 0, 0]," +
                " RandomProvider[@8397262361995169820, 0, 0], RandomProvider[@7421997658690496630, 0, 0]," +
                " RandomProvider[@-7898255109170938473, 0, 0], RandomProvider[@3868661580815414054, 0, 0]," +
                " RandomProvider[@453733778809658833, 0, 0], RandomProvider[@-2636879068627078241, 0, 0]," +
                " RandomProvider[@6142487595818599495, 0, 0], RandomProvider[@4681726659604324256, 0, 0]," +
                " RandomProvider[@1906427249022382973, 0, 0], RandomProvider[@8356990522398738440, 0, 0]," +
                " RandomProvider[@1947303986473887049, 0, 0], RandomProvider[@-7907745475302030450, 0, 0]," +
                " RandomProvider[@-9092377260523661242, 0, 0], RandomProvider[@-1031230974728655171, 0, 0], ...]");
        randomProvidersFixedScales_helper(-5, -10,
                "[RandomProvider[@-7948823947390831374, -5, -10], RandomProvider[@7302477663894715351, -5, -10]," +
                " RandomProvider[@5113382706114603938, -5, -10], RandomProvider[@-1774083719213728003, -5, -10]," +
                " RandomProvider[@8538952961045368838, -5, -10], RandomProvider[@7023783968100629274, -5, -10]," +
                " RandomProvider[@8397262361995169820, -5, -10], RandomProvider[@7421997658690496630, -5, -10]," +
                " RandomProvider[@-7898255109170938473, -5, -10], RandomProvider[@3868661580815414054, -5, -10]," +
                " RandomProvider[@453733778809658833, -5, -10], RandomProvider[@-2636879068627078241, -5, -10]," +
                " RandomProvider[@6142487595818599495, -5, -10], RandomProvider[@4681726659604324256, -5, -10]," +
                " RandomProvider[@1906427249022382973, -5, -10], RandomProvider[@8356990522398738440, -5, -10]," +
                " RandomProvider[@1947303986473887049, -5, -10], RandomProvider[@-7907745475302030450, -5, -10]," +
                " RandomProvider[@-9092377260523661242, -5, -10], RandomProvider[@-1031230974728655171, -5, -10]," +
                " ...]");
    }

    @Test
    public void testRandomProvidersDefault() {
        simpleProviderHelper(P.randomProvidersDefault(),
                "[RandomProvider[@-7948823947390831374, 32, 8], RandomProvider[@7302477663894715351, 32, 8]," +
                " RandomProvider[@5113382706114603938, 32, 8], RandomProvider[@-1774083719213728003, 32, 8]," +
                " RandomProvider[@8538952961045368838, 32, 8], RandomProvider[@7023783968100629274, 32, 8]," +
                " RandomProvider[@8397262361995169820, 32, 8], RandomProvider[@7421997658690496630, 32, 8]," +
                " RandomProvider[@-7898255109170938473, 32, 8], RandomProvider[@3868661580815414054, 32, 8]," +
                " RandomProvider[@453733778809658833, 32, 8], RandomProvider[@-2636879068627078241, 32, 8]," +
                " RandomProvider[@6142487595818599495, 32, 8], RandomProvider[@4681726659604324256, 32, 8]," +
                " RandomProvider[@1906427249022382973, 32, 8], RandomProvider[@8356990522398738440, 32, 8]," +
                " RandomProvider[@1947303986473887049, 32, 8], RandomProvider[@-7907745475302030450, 32, 8]," +
                " RandomProvider[@-9092377260523661242, 32, 8], RandomProvider[@-1031230974728655171, 32, 8], ...]");
    }

    @Test
    public void testRandomProvidersDefaultSecondaryScale() {
        simpleProviderHelper(P.randomProvidersDefaultSecondaryScale(),
                "[RandomProvider[@-7948823947390831374, 0, 8], RandomProvider[@-7948823947390831374, 1, 8]," +
                " RandomProvider[@7302477663894715351, 0, 8], RandomProvider[@7302477663894715351, 1, 8]," +
                " RandomProvider[@-7948823947390831374, -1, 8], RandomProvider[@-7948823947390831374, 2, 8]," +
                " RandomProvider[@7302477663894715351, -1, 8], RandomProvider[@7302477663894715351, 2, 8]," +
                " RandomProvider[@5113382706114603938, 0, 8], RandomProvider[@5113382706114603938, 1, 8]," +
                " RandomProvider[@-1774083719213728003, 0, 8], RandomProvider[@-1774083719213728003, 1, 8]," +
                " RandomProvider[@5113382706114603938, -1, 8], RandomProvider[@5113382706114603938, 2, 8]," +
                " RandomProvider[@-1774083719213728003, -1, 8], RandomProvider[@-1774083719213728003, 2, 8]," +
                " RandomProvider[@-7948823947390831374, -2, 8], RandomProvider[@-7948823947390831374, 3, 8]," +
                " RandomProvider[@7302477663894715351, -2, 8], RandomProvider[@7302477663894715351, 3, 8], ...]");
    }

    @Test
    public void testRandomProviders() {
        simpleProviderHelper(P.randomProviders(),
                "[RandomProvider[@-7948823947390831374, 0, 0], RandomProvider[@-7948823947390831374, 0, 1]," +
                " RandomProvider[@7302477663894715351, 0, 0], RandomProvider[@7302477663894715351, 0, 1]," +
                " RandomProvider[@-7948823947390831374, 1, 0], RandomProvider[@-7948823947390831374, 1, 1]," +
                " RandomProvider[@7302477663894715351, 1, 0], RandomProvider[@7302477663894715351, 1, 1]," +
                " RandomProvider[@5113382706114603938, 0, 0], RandomProvider[@5113382706114603938, 0, 1]," +
                " RandomProvider[@-1774083719213728003, 0, 0], RandomProvider[@-1774083719213728003, 0, 1]," +
                " RandomProvider[@5113382706114603938, 1, 0], RandomProvider[@5113382706114603938, 1, 1]," +
                " RandomProvider[@-1774083719213728003, 1, 0], RandomProvider[@-1774083719213728003, 1, 1]," +
                " RandomProvider[@-7948823947390831374, 0, -1], RandomProvider[@-7948823947390831374, 0, 2]," +
                " RandomProvider[@7302477663894715351, 0, -1], RandomProvider[@7302477663894715351, 0, 2], ...]");
    }

    @Test
    public void testEquals() {
        //noinspection EqualsWithItself
        assertTrue(P.equals(P));
        //noinspection ObjectEqualsNull
        assertFalse(P.equals(null));
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(P.equals("hello"));
    }

    @Test
    public void testHashCode() {
        aeq(P.hashCode(), 0);
    }

    @Test
    public void testToString() {
        aeq(P, "ExhaustiveProvider");
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
