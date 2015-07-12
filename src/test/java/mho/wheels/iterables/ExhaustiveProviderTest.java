package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.misc.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// @formatter:off
public strictfp class ExhaustiveProviderTest {
    private static final ExhaustiveProvider P = ExhaustiveProvider.INSTANCE;
    private static final int TINY_LIMIT = 20;

    private static <T> void simpleProviderHelper(@NotNull Iterable<T> xs, @NotNull String output) {
        aeqit(xs, output);
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
        aeqit(take(TINY_LIMIT, P.bytesIncreasing()),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119," +
                " -118, -117, -116, -115, -114, -113, -112, -111, -110, -109]");
    }

    @Test
    public void testShortsIncreasing() {
        aeq(length(P.shortsIncreasing()), 65536);
        aeqit(take(TINY_LIMIT, P.shortsIncreasing()),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759," +
                " -32758, -32757, -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749]");
    }

    @Test
    public void testIntegersIncreasing() {
        aeqit(take(TINY_LIMIT, P.integersIncreasing()),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644," +
                " -2147483643, -2147483642, -2147483641, -2147483640, -2147483639," +
                " -2147483638, -2147483637, -2147483636, -2147483635, -2147483634," +
                " -2147483633, -2147483632, -2147483631, -2147483630, -2147483629]");
    }

    @Test
    public void testLongsIncreasing() {
        aeqit(take(TINY_LIMIT, P.longsIncreasing()),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789]");
    }

    @Test
    public void testPositiveBytes() {
        aeq(length(P.positiveBytes()), 127);
        aeqit(
                take(TINY_LIMIT, P.positiveBytes()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]"
        );
    }

    @Test
    public void testPositiveShorts() {
        aeq(length(P.positiveShorts()), 32767);
        aeqit(
                take(TINY_LIMIT, P.positiveShorts()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]"
        );
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(TINY_LIMIT, P.positiveIntegers()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(TINY_LIMIT, P.positiveLongs()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveBigIntegers() {
        aeqit(take(TINY_LIMIT, P.positiveBigIntegers()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testNegativeBytes() {
        aeq(length(P.negativeBytes()), 128);
        aeqit(take(TINY_LIMIT, P.negativeBytes()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeShorts() {
        aeq(length(P.negativeShorts()), 32768);
        aeqit(take(TINY_LIMIT, P.negativeShorts()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeIntegers() {
        aeqit(take(TINY_LIMIT, P.negativeIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeLongs() {
        aeqit(take(TINY_LIMIT, P.negativeLongs()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeBigIntegers() {
        aeqit(take(TINY_LIMIT, P.negativeBigIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNaturalBytes() {
        aeq(length(P.naturalBytes()), 128);
        aeqit(take(TINY_LIMIT, P.naturalBytes()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalShorts() {
        aeq(length(P.naturalShorts()), 32768);
        aeqit(take(TINY_LIMIT, P.naturalShorts()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalIntegers() {
        aeqit(take(TINY_LIMIT, P.naturalIntegers()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalLongs() {
        aeqit(take(TINY_LIMIT, P.naturalLongs()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalBigIntegers() {
        aeqit(take(TINY_LIMIT, P.naturalBigIntegers()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNonzeroBytes() {
        Iterable<Byte> bs = P.nonzeroBytes();
        aeq(length(bs), 255);
        aeqit(take(5, reverse(bs)), "[-128, -127, 127, -126, 126]");
        aeqit(take(TINY_LIMIT, bs), "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10]");
    }

    @Test
    public void testNonzeroShorts() {
        Iterable<Short> ss = P.nonzeroShorts();
        aeq(length(ss), 65535);
        aeqit(take(5, reverse(ss)), "[-32768, -32767, 32767, -32766, 32766]");
        aeqit(take(TINY_LIMIT, ss), "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10]");
    }

    @Test
    public void testNonzeroIntegers() {
        aeqit(
                take(TINY_LIMIT, P.nonzeroIntegers()),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10]"
        );
    }

    @Test
    public void testNonzeroLongs() {
        aeqit(take(TINY_LIMIT, P.nonzeroLongs()),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10]");
    }

    @Test
    public void testNonzeroBigIntegers() {
        aeqit(
                take(TINY_LIMIT, P.nonzeroBigIntegers()),
                "[1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10]"
        );
    }

    @Test
    public void testBytes() {
        Iterable<Byte> bs = P.bytes();
        aeq(length(bs), 256);
        aeqit(take(5, reverse(bs)), "[-128, -127, 127, -126, 126]");
        aeqit(take(TINY_LIMIT, bs), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testShorts() {
        Iterable<Short> ss = P.shorts();
        aeq(length(ss), 65536);
        aeqit(take(5, reverse(ss)), "[-32768, -32767, 32767, -32766, 32766]");
        aeqit(take(TINY_LIMIT, ss), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testIntegers() {
        aeqit(take(TINY_LIMIT, P.integers()),
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testLongs() {
        aeqit(take(TINY_LIMIT, P.longs()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testBigIntegers() {
        aeqit(take(TINY_LIMIT, P.bigIntegers()),
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
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
        aeqit(take(TINY_LIMIT, P.rangeUp((byte) a)), output);
    }

    @Test
    public void testRangeUp_byte() {
        rangeUp_byte_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        rangeUp_byte_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");
        rangeUp_byte_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14]");
        rangeUp_byte_helper(Byte.MAX_VALUE, "[127]");
        rangeUp_byte_helper(Byte.MIN_VALUE, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    private static void rangeUp_short_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp((short) a)), output);
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        rangeUp_short_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");
        rangeUp_short_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14]");
        rangeUp_short_helper(Short.MAX_VALUE, "[32767]");
        rangeUp_short_helper(
                Short.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        rangeUp_int_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");
        rangeUp_int_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14]");
        rangeUp_int_helper(Integer.MAX_VALUE, "[2147483647]");
        rangeUp_int_helper(
                Integer.MIN_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(0L, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        rangeUp_long_helper(5L, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");
        rangeUp_long_helper(-5L, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14]");
        rangeUp_long_helper(Long.MAX_VALUE, "[9223372036854775807]");
        rangeUp_long_helper(Long.MIN_VALUE, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    private static void rangeUp_BigInteger_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(BigInteger.valueOf(a))), output);
    }

    @Test

    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper(0, "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
        rangeUp_BigInteger_helper(5, "[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");
        rangeUp_BigInteger_helper(-5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, 7, 8, 9, 10, 11, 12, 13, 14]");
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
        aeqit(take(TINY_LIMIT, P.rangeDown((byte) a)), output);
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper(
                0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19]"
        );
        rangeDown_byte_helper(
                5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14]"
        );
        rangeDown_byte_helper(
                -5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24]"
        );
        rangeDown_byte_helper(
                Byte.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
        rangeDown_byte_helper(Byte.MIN_VALUE, "[-128]");
    }

    private static void rangeDown_short_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown((short) a)), output);
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper(
                0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19]"
        );
        rangeDown_short_helper(
                5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14]"
        );
        rangeDown_short_helper(
                -5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24]"
        );
        rangeDown_short_helper(
                Short.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
        rangeDown_short_helper(Short.MIN_VALUE, "[-32768]");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(
                0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19]"
        );
        rangeDown_int_helper(5, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14]");
        rangeDown_int_helper(
                -5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24]"
        );
        rangeDown_int_helper(
                Integer.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
        rangeDown_int_helper(Integer.MIN_VALUE, "[-2147483648]");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(
                0L,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19]"
        );
        rangeDown_long_helper(5L, "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14]");
        rangeDown_long_helper(
                -5L,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24]"
        );
        rangeDown_long_helper(
                Long.MAX_VALUE,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]"
        );
        rangeDown_int_helper(Integer.MIN_VALUE, "[-2147483648]");
    }

    private static void rangeDown_BigInteger_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(BigInteger.valueOf(a))), output);
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper(
                0,
                "[0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19]"
        );
        rangeDown_BigInteger_helper(
                5,
                "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14]"
        );
        rangeDown_BigInteger_helper(
                -5,
                "[-5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24]"
        );
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
        aeqit(take(TINY_LIMIT, P.positiveBinaryFractions()),
                "[1, 1 << 1, 3, 1 >> 1, 5, 3 << 1, 7, 1 << 2, 9, 5 << 1, 11, 3 >> 1, 13, 7 << 1, 15, 1 >> 2, 17," +
                " 9 << 1, 19, 5 >> 1]");
    }

    @Test
    public void testNegativeBinaryFractions() {
        aeqit(take(TINY_LIMIT, P.negativeBinaryFractions()),
                "[-1, -1 << 1, -3, -1 >> 1, -5, -3 << 1, -7, -1 << 2, -9, -5 << 1, -11, -3 >> 1, -13, -7 << 1, -15," +
                " -1 >> 2, -17, -9 << 1, -19, -5 >> 1]");
    }

    @Test
    public void testNonzeroBinaryFractions() {
        aeqit(take(TINY_LIMIT, P.nonzeroBinaryFractions()),
                "[1, -1, 1 << 1, -1 << 1, 3, -3, 1 >> 1, -1 >> 1, 5, -5, 3 << 1, -3 << 1, 7, -7, 1 << 2, -1 << 2, 9," +
                " -9, 5 << 1, -5 << 1]");
    }

    @Test
    public void testBinaryFractions() {
        aeqit(take(TINY_LIMIT, P.binaryFractions()),
                "[0, 1, -1, 1 << 1, -1 << 1, 3, -3, 1 >> 1, -1 >> 1, 5, -5, 3 << 1, -3 << 1, 7, -7, 1 << 2, -1 << 2," +
                " 9, -9, 5 << 1]");
    }

    private static void rangeUp_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(BinaryFraction.read(a).get())), output);
    }

    @Test
    public void testRangeUp_BinaryFraction() {
        rangeUp_BinaryFraction_helper("0",
                "[0, 1, 1 << 1, 3, 1 >> 1, 5, 3 << 1, 7, 1 << 2, 9, 5 << 1, 11, 3 >> 1, 13, 7 << 1, 15, 1 >> 2, 17," +
                " 9 << 1, 19]");
        rangeUp_BinaryFraction_helper("1",
                "[1, 1 << 1, 3, 1 << 2, 3 >> 1, 3 << 1, 7, 1 << 3, 5, 5 << 1, 11, 3 << 2, 5 >> 1, 7 << 1, 15," +
                " 1 << 4, 5 >> 2, 9 << 1, 19, 5 << 2]");
        rangeUp_BinaryFraction_helper("11",
                "[11, 3 << 2, 13, 7 << 1, 23 >> 1, 1 << 4, 17, 9 << 1, 15, 5 << 2, 21, 11 << 1, 25 >> 1, 3 << 3, 25," +
                " 13 << 1, 45 >> 2, 7 << 2, 29, 15 << 1]");
        rangeUp_BinaryFraction_helper("5 << 20",
                "[5 << 20, 3 << 21, 7 << 20, 1 << 23, 11 << 19, 5 << 21, 11 << 20, 3 << 22, 9 << 20, 7 << 21," +
                " 15 << 20, 1 << 24, 13 << 19, 9 << 21, 19 << 20, 5 << 22, 21 << 18, 11 << 21, 23 << 20, 3 << 23]");
        rangeUp_BinaryFraction_helper("5 >> 20",
                "[5 >> 20, 3 >> 19, 7 >> 20, 1 >> 17, 11 >> 21, 5 >> 19, 11 >> 20, 3 >> 18, 9 >> 20, 7 >> 19," +
                " 15 >> 20, 1 >> 16, 13 >> 21, 9 >> 19, 19 >> 20, 5 >> 18, 21 >> 22, 11 >> 19, 23 >> 20, 3 >> 17]");
        rangeUp_BinaryFraction_helper("-1",
                "[-1, 0, 1, 1 << 1, -1 >> 1, 1 << 2, 5, 3 << 1, 3, 1 << 3, 9, 5 << 1, 1 >> 1, 3 << 2, 13, 7 << 1," +
                " -3 >> 2, 1 << 4, 17, 9 << 1]");
        rangeUp_BinaryFraction_helper("-11",
                "[-11, -5 << 1, -9, -1 << 3, -21 >> 1, -3 << 1, -5, -1 << 2, -7, -1 << 1, -1, 0, -19 >> 1, 1 << 1," +
                " 3, 1 << 2, -43 >> 2, 3 << 1, 7, 1 << 3]");
        rangeUp_BinaryFraction_helper("-5 << 20",
                "[-5 << 20, -1 << 22, -3 << 20, -1 << 21, -9 << 19, 0, 1 << 20, 1 << 21, -1 << 20, 1 << 22, 5 << 20," +
                " 3 << 21, -7 << 19, 1 << 23, 9 << 20, 5 << 21, -19 << 18, 3 << 22, 13 << 20, 7 << 21]");
        rangeUp_BinaryFraction_helper("-5 >> 20",
                "[-5 >> 20, -1 >> 18, -3 >> 20, -1 >> 19, -9 >> 21, 0, 1 >> 20, 1 >> 19, -1 >> 20, 1 >> 18, 5 >> 20," +
                " 3 >> 19, -7 >> 21, 1 >> 17, 9 >> 20, 5 >> 19, -19 >> 22, 3 >> 18, 13 >> 20, 7 >> 19]");
    }

    private static void rangeDown_BinaryFraction_helper(@NotNull String a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(BinaryFraction.read(a).get())), output);
    }

    @Test
    public void testRangeDown_BinaryFraction() {
        rangeDown_BinaryFraction_helper("0",
                "[0, -1, -1 << 1, -3, -1 >> 1, -5, -3 << 1, -7, -1 << 2, -9, -5 << 1, -11, -3 >> 1, -13, -7 << 1," +
                " -15, -1 >> 2, -17, -9 << 1, -19]");
        rangeDown_BinaryFraction_helper("1",
                "[1, 0, -1, -1 << 1, 1 >> 1, -1 << 2, -5, -3 << 1, -3, -1 << 3, -9, -5 << 1, -1 >> 1, -3 << 2, -13," +
                " -7 << 1, 3 >> 2, -1 << 4, -17, -9 << 1]");
        rangeDown_BinaryFraction_helper("11",
                "[11, 5 << 1, 9, 1 << 3, 21 >> 1, 3 << 1, 5, 1 << 2, 7, 1 << 1, 1, 0, 19 >> 1, -1 << 1, -3, -1 << 2," +
                " 43 >> 2, -3 << 1, -7, -1 << 3]");
        rangeDown_BinaryFraction_helper("5 << 20",
                "[5 << 20, 1 << 22, 3 << 20, 1 << 21, 9 << 19, 0, -1 << 20, -1 << 21, 1 << 20, -1 << 22, -5 << 20," +
                " -3 << 21, 7 << 19, -1 << 23, -9 << 20, -5 << 21, 19 << 18, -3 << 22, -13 << 20, -7 << 21]");
        rangeDown_BinaryFraction_helper("5 >> 20",
                "[5 >> 20, 1 >> 18, 3 >> 20, 1 >> 19, 9 >> 21, 0, -1 >> 20, -1 >> 19, 1 >> 20, -1 >> 18, -5 >> 20," +
                " -3 >> 19, 7 >> 21, -1 >> 17, -9 >> 20, -5 >> 19, 19 >> 22, -3 >> 18, -13 >> 20, -7 >> 19]");
        rangeDown_BinaryFraction_helper("-1",
                "[-1, -1 << 1, -3, -1 << 2, -3 >> 1, -3 << 1, -7, -1 << 3, -5, -5 << 1, -11, -3 << 2, -5 >> 1," +
                " -7 << 1, -15, -1 << 4, -5 >> 2, -9 << 1, -19, -5 << 2]");
        rangeDown_BinaryFraction_helper("-11",
                "[-11, -3 << 2, -13, -7 << 1, -23 >> 1, -1 << 4, -17, -9 << 1, -15, -5 << 2, -21, -11 << 1," +
                " -25 >> 1, -3 << 3, -25, -13 << 1, -45 >> 2, -7 << 2, -29, -15 << 1]");
        rangeDown_BinaryFraction_helper("-5 << 20",
                "[-5 << 20, -3 << 21, -7 << 20, -1 << 23, -11 << 19, -5 << 21, -11 << 20, -3 << 22, -9 << 20," +
                " -7 << 21, -15 << 20, -1 << 24, -13 << 19, -9 << 21, -19 << 20, -5 << 22, -21 << 18, -11 << 21," +
                " -23 << 20, -3 << 23]");
        rangeDown_BinaryFraction_helper("-5 >> 20",
                "[-5 >> 20, -3 >> 19, -7 >> 20, -1 >> 17, -11 >> 21, -5 >> 19, -11 >> 20, -3 >> 18, -9 >> 20," +
                " -7 >> 19, -15 >> 20, -1 >> 16, -13 >> 21, -9 >> 19, -19 >> 20, -5 >> 18, -21 >> 22, -11 >> 19," +
                " -23 >> 20, -3 >> 17]");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeqit(take(TINY_LIMIT, P.range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get())), output);
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper("0", "1",
                "[0, 1, 1 >> 1, 1 >> 2, 3 >> 2, 1 >> 3, 3 >> 3, 5 >> 3, 7 >> 3, 1 >> 4, 3 >> 4, 5 >> 4, 7 >> 4," +
                " 9 >> 4, 11 >> 4, 13 >> 4, 15 >> 4, 1 >> 5, 3 >> 5, 5 >> 5]");
        range_BinaryFraction_BinaryFraction_helper("1", "11",
                "[1, 3, 5, 7, 9, 11, 1 << 1, 1 << 2, 3 << 1, 1 << 3, 5 << 1, 3 >> 1, 7 >> 1, 11 >> 1, 15 >> 1," +
                " 19 >> 1, 5 >> 1, 9 >> 1, 13 >> 1, 17 >> 1]");
        range_BinaryFraction_BinaryFraction_helper("11", "11", "[11]");
        range_BinaryFraction_BinaryFraction_helper("11", "1", "[]");
        range_BinaryFraction_BinaryFraction_helper("-11", "-1",
                "[-11, -9, -7, -5, -3, -1, -5 << 1, -1 << 3, -3 << 1, -1 << 2, -1 << 1, -21 >> 1, -17 >> 1," +
                " -13 >> 1, -9 >> 1, -5 >> 1, -19 >> 1, -15 >> 1, -11 >> 1, -7 >> 1]");
        range_BinaryFraction_BinaryFraction_helper("-11", "-11", "[-11]");
        range_BinaryFraction_BinaryFraction_helper("-1", "-11", "[]");
        range_BinaryFraction_BinaryFraction_helper("0", "0", "[0]");
        range_BinaryFraction_BinaryFraction_helper("0", "11",
                "[0, 1, 1 << 1, 3, 1 << 2, 5, 3 << 1, 7, 1 << 3, 9, 5 << 1, 11, 1 >> 1, 3 >> 1, 5 >> 1, 7 >> 1," +
                " 9 >> 1, 11 >> 1, 13 >> 1, 15 >> 1]");
        range_BinaryFraction_BinaryFraction_helper("-1", "11",
                "[-1, 3, 7, 11, 1, 5, 9, 0, 1 << 2, 1 << 3, 1 << 1, 3 << 1, 5 << 1, -1 >> 1, 7 >> 1, 15 >> 1," +
                " 1 >> 1, 9 >> 1, 17 >> 1, 3 >> 1]");
        range_BinaryFraction_BinaryFraction_helper("5 >> 20", "1",
                "[5 >> 20, 3 >> 19, 7 >> 20, 1 >> 17, 9 >> 20, 5 >> 19, 11 >> 20, 3 >> 18, 13 >> 20, 7 >> 19," +
                " 15 >> 20, 1 >> 16, 17 >> 20, 9 >> 19, 19 >> 20, 5 >> 18, 21 >> 20, 11 >> 19, 23 >> 20, 3 >> 17]");
        range_BinaryFraction_BinaryFraction_helper("1", "5 << 20",
                "[1, 1 << 1, 3, 1 << 2, 5, 3 << 1, 7, 1 << 3, 9, 5 << 1, 11, 3 << 2, 13, 7 << 1, 15, 1 << 4, 17," +
                " 9 << 1, 19, 5 << 2]");
    }

    @Test
    public void testPositiveFloats() {
        aeqit(take(50, P.positiveFloats()),
                "[Infinity, 1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0," +
                " 0.25, 8.0, 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875," +
                " 112.0, 9.0, 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0," +
                " 2.25]");
    }

    @Test
    public void testNegativeFloats() {
        aeqit(take(50, P.negativeFloats()),
                "[-Infinity, -1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0," +
                " -3.5, -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25]");
    }

    @Test
    public void testNonzeroFloats() {
        aeqit(take(50, P.nonzeroFloats()),
                "[NaN, Infinity, -Infinity, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5," +
                " -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5," +
                " -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0," +
                " 0.375, -0.375, 48.0]");
    }

    @Test
    public void testFloats() {
        aeqit(take(50, P.floats()),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    @Test
    public void testPositiveDoubles() {
        aeqit(take(50, P.positiveDoubles()),
                "[Infinity, 1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0," +
                " 0.25, 8.0, 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875," +
                " 112.0, 9.0, 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0," +
                " 2.25]");
    }

    @Test
    public void testNegativeDoubles() {
        aeqit(take(50, P.negativeDoubles()),
                "[-Infinity, -1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0," +
                " -3.5, -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25]");
    }

    @Test
    public void testNonzeroDoubles() {
        aeqit(take(50, P.nonzeroDoubles()),
                "[NaN, Infinity, -Infinity, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5," +
                " -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5," +
                " -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0," +
                " 0.375, -0.375, 48.0]");
    }

    @Test
    public void testDoubles() {
        aeqit(take(50, P.doubles()),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    private static void rangeUp_float_helper(float a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
    }

    @Test
    public void testRangeUp_float() {
        rangeUp_float_helper(1.0f,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0]");
        rangeUp_float_helper(1.0E20f,
                "[Infinity, 1.0E20, 1.0000004E20, 1.0000007E20, 1.0000011E20, 1.0000002E20, 1.0000018E20," +
                " 1.0000021E20, 1.0000025E20, 1.0000014E20, 1.0000032E20, 1.0000035E20, 1.0000039E20, 1.00000055E20," +
                " 1.0000046E20, 1.00000495E20, 1.0000053E20, 1.0000001E20, 1.000006E20, 1.00000635E20]");
        rangeUp_float_helper(-1.0f,
                "[Infinity, -1.0, 0.0, 1.0, 2.0, -0.5, 4.0, 5.0, 6.0, 3.0, 8.0, 9.0, 10.0, 0.5, 12.0, 13.0, 14.0," +
                " -0.75, 16.0, 17.0]");
        rangeUp_float_helper(-1.0E20f,
                "[Infinity, -1.0E20, -9.999997E19, -9.999993E19, -9.99999E19, -9.9999984E19, -9.999983E19," +
                " -9.999979E19, -9.999976E19, -9.999986E19, -9.9999685E19, -9.999965E19, -9.9999615E19," +
                " -9.999995E19, -9.9999545E19, -9.999951E19, -9.999947E19, -9.999999E19, -9.99994E19, -9.999937E19]");
        rangeUp_float_helper((float) Math.PI,
                "[Infinity, 3.1415927, 3.141593, 3.1415932, 3.1415935, 3.141594, 3.1415942, 3.1415944, 3.1415937," +
                " 3.141595, 3.1415951, 3.1415954, 3.1415958, 3.141596, 3.1415963, 3.1415968, 3.141597, 3.1415973," +
                " 3.1415977, 3.141598]");
        rangeUp_float_helper((float) Math.sqrt(2),
                "[Infinity, 1.4142135, 1.4142137, 1.4142138, 1.4142139, 1.4142141, 1.4142143, 1.4142144, 1.414214," +
                " 1.4142146, 1.4142147, 1.4142148, 1.4142151, 1.4142152, 1.4142153, 1.4142156, 1.4142157, 1.4142158," +
                " 1.414216, 1.4142162]");
        rangeUp_float_helper((float) -Math.PI,
                "[Infinity, -3.1415927, -3.1415925, -3.1415923, -3.141592, -3.1415915, -3.1415913, -3.141591," +
                " -3.1415918, -3.1415906, -3.1415904, -3.14159, -3.1415896, -3.1415894, -3.1415892, -3.1415887," +
                " -3.1415884, -3.1415882, -3.1415877, -3.1415875]");
        rangeUp_float_helper((float) -Math.sqrt(2),
                "[Infinity, -1.4142135, -1.4142134, -1.4142133, -1.4142132, -1.414213, -1.4142128, -1.4142127," +
                " -1.4142131, -1.4142125, -1.4142123, -1.4142122, -1.414212, -1.4142119, -1.4142118, -1.4142115," +
                " -1.4142114, -1.4142113, -1.414211, -1.4142109]");
        rangeUp_float_helper(0.0f,
                "[Infinity, 0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0, 15.0," +
                " 0.25, 17.0, 18.0]");
        rangeUp_float_helper(-0.0f,
                "[Infinity, 0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0, 15.0," +
                " 0.25, 17.0, 18.0]");
        rangeUp_float_helper(Float.MIN_VALUE,
                "[Infinity, 1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 8.4E-45, 9.8E-45, 1.1E-44, 7.0E-45, 1.4E-44," +
                " 1.5E-44, 1.7E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.5E-44, 2.7E-44, 2.8E-44, 3.1E-44, 3.2E-44]");
        rangeUp_float_helper(Float.MIN_NORMAL,
                "[Infinity, 1.17549435E-38, 2.3509887E-38, 3.526483E-38, 4.7019774E-38, 1.7632415E-38, 7.052966E-38," +
                " 8.2284605E-38, 9.403955E-38, 5.877472E-38, 1.1754944E-37, 1.2930438E-37, 1.4105932E-37," +
                " 2.938736E-38, 1.6456921E-37, 1.7632415E-37, 1.880791E-37, 1.469368E-38, 2.1158898E-37," +
                " 2.2334393E-37]");
        rangeUp_float_helper(-Float.MIN_VALUE,
                "[Infinity, -1.4E-45, 0.0, 1.4E-45, 2.8E-45, 5.6E-45, 7.0E-45, 8.4E-45, 4.2E-45, 1.1E-44, 1.3E-44," +
                " 1.4E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.8E-44, 2.9E-44]");
        rangeUp_float_helper(-Float.MIN_NORMAL,
                "[Infinity, -1.17549435E-38, 0.0, 1.17549435E-38, 2.3509887E-38, -5.877472E-39, 4.7019774E-38," +
                " 5.877472E-38, 7.052966E-38, 3.526483E-38, 9.403955E-38, 1.0579449E-37, 1.1754944E-37," +
                " 5.877472E-39, 1.4105932E-37, 1.5281427E-37, 1.6456921E-37, -8.816208E-39, 1.880791E-37," +
                " 1.9983404E-37]");
        rangeUp_float_helper(Float.MAX_VALUE, "[Infinity, 3.4028235E38]");
        rangeUp_float_helper(-Float.MAX_VALUE,
                "[Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028225E38, -3.4028222E38," +
                " -3.402822E38, -3.4028227E38, -3.4028216E38, -3.4028214E38, -3.4028212E38, -3.4028208E38," +
                " -3.4028206E38, -3.4028204E38, -3.40282E38, -3.4028198E38, -3.4028196E38, -3.4028192E38," +
                " -3.402819E38]");
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "[Infinity]");
        rangeUp_float_helper(Float.NEGATIVE_INFINITY, "[Infinity, -Infinity]");
    }

    @Test
    public void testPositiveBigDecimals() {
        aeqit(take(50, P.positiveBigDecimals()),
                "[1, 0.1, 2, 1E+1, 3, 0.2, 4, 0.01, 5, 0.3, 6, 2E+1, 7, 0.4, 8, 1E+2, 9, 0.5, 10, 3E+1, 11, 0.6, 12," +
                " 0.02, 13, 0.7, 14, 4E+1, 15, 0.8, 16, 0.001, 17, 0.9, 18, 5E+1, 19, 1.0, 20, 0.03, 21, 1.1, 22," +
                " 6E+1, 23, 1.2, 24, 2E+2, 25, 1.3]");
    }

    @Test
    public void testNegativeBigDecimals() {
        aeqit(take(50, P.negativeBigDecimals()),
                "[-1, -0.1, -2, -1E+1, -3, -0.2, -4, -0.01, -5, -0.3, -6, -2E+1, -7, -0.4, -8, -1E+2, -9, -0.5, -10," +
                " -3E+1, -11, -0.6, -12, -0.02, -13, -0.7, -14, -4E+1, -15, -0.8, -16, -0.001, -17, -0.9, -18," +
                " -5E+1, -19, -1.0, -20, -0.03, -21, -1.1, -22, -6E+1, -23, -1.2, -24, -2E+2, -25, -1.3]");
    }

    @Test
    public void testBigDecimals() {
        aeqit(take(50, P.bigDecimals()),
                "[0, 0.0, 1, 0E+1, -1, 0.1, 2, 0.00, -2, -0.1, 3, 1E+1, -3, 0.2, 4, 0E+2, -4, -0.2, 5, -1E+1, -5," +
                " 0.3, 6, 0.01, -6, -0.3, 7, 2E+1, -7, 0.4, 8, 0.000, -8, -0.4, 9, -2E+1, -9, 0.5, 10, -0.01, -10," +
                " -0.5, 11, 3E+1, -11, 0.6, 12, 1E+2, -12, -0.6]");
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
}
// @formatter:on
