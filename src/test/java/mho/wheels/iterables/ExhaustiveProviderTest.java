package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
                " 1.25, 18.0, 19.0]");
        rangeUp_float_helper(1.0E20f,
                "[Infinity, 1.0E20, 1.0000004E20, 1.0000007E20, 1.0000011E20, 1.0000002E20, 1.0000018E20," +
                " 1.0000021E20, 1.0000025E20, 1.0000014E20, 1.0000032E20, 1.0000035E20, 1.0000039E20, 1.00000055E20," +
                " 1.0000046E20, 1.00000495E20, 1.0000053E20, 1.0000001E20, 1.000006E20, 1.00000635E20]");
        rangeUp_float_helper(-1.0f,
                "[Infinity, -1.0, 0.0, -0.0, 1.0, 2.0, -0.5, 4.0, 5.0, 6.0, 3.0, 8.0, 9.0, 10.0, 0.5, 12.0, 13.0," +
                " 14.0, -0.75, 16.0]");
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
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0]");
        rangeUp_float_helper(-0.0f,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0]");
        rangeUp_float_helper(Float.MIN_VALUE,
                "[Infinity, 1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 8.4E-45, 9.8E-45, 1.1E-44, 7.0E-45, 1.4E-44," +
                " 1.5E-44, 1.7E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.5E-44, 2.7E-44, 2.8E-44, 3.1E-44, 3.2E-44]");
        rangeUp_float_helper(Float.MIN_NORMAL,
                "[Infinity, 1.17549435E-38, 2.3509887E-38, 3.526483E-38, 4.7019774E-38, 1.7632415E-38, 7.052966E-38," +
                " 8.2284605E-38, 9.403955E-38, 5.877472E-38, 1.1754944E-37, 1.2930438E-37, 1.4105932E-37," +
                " 2.938736E-38, 1.6456921E-37, 1.7632415E-37, 1.880791E-37, 1.469368E-38, 2.1158898E-37," +
                " 2.2334393E-37]");
        rangeUp_float_helper(-Float.MIN_VALUE,
                "[Infinity, -1.4E-45, 0.0, -0.0, 1.4E-45, 2.8E-45, 5.6E-45, 7.0E-45, 8.4E-45, 4.2E-45, 1.1E-44," +
                " 1.3E-44, 1.4E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.8E-44]");
        rangeUp_float_helper(-Float.MIN_NORMAL,
                "[Infinity, -1.17549435E-38, 0.0, -0.0, 1.17549435E-38, 2.3509887E-38, -5.877472E-39, 4.7019774E-38," +
                " 5.877472E-38, 7.052966E-38, 3.526483E-38, 9.403955E-38, 1.0579449E-37, 1.1754944E-37, 5.877472E-39," +
                " 1.4105932E-37, 1.5281427E-37, 1.6456921E-37, -8.816208E-39, 1.880791E-37]");
        rangeUp_float_helper(Float.MAX_VALUE, "[Infinity, 3.4028235E38]");
        rangeUp_float_helper(-Float.MAX_VALUE,
                "[Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028225E38, -3.4028222E38," +
                " -3.402822E38, -3.4028227E38, -3.4028216E38, -3.4028214E38, -3.4028212E38, -3.4028208E38," +
                " -3.4028206E38, -3.4028204E38, -3.40282E38, -3.4028198E38, -3.4028196E38, -3.4028192E38," +
                " -3.402819E38]");
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "[Infinity]");
        rangeUp_float_helper(Float.NEGATIVE_INFINITY,
                "[Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0," +
                " 1.5, -1.5, 12.0, -12.0]");
        rangeUp_float_fail_helper(Float.NaN);
    }

    private static void rangeDown_float_helper(float a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
                " -12.0, -13.0, -14.0, 0.75, -16.0]");
        rangeDown_float_helper(1.0E20f,
                "[-Infinity, 1.0E20, 9.999997E19, 9.999993E19, 9.99999E19, 9.9999984E19, 9.999983E19, 9.999979E19," +
                " 9.999976E19, 9.999986E19, 9.9999685E19, 9.999965E19, 9.9999615E19, 9.999995E19, 9.9999545E19," +
                " 9.999951E19, 9.999947E19, 9.999999E19, 9.99994E19, 9.999937E19]");
        rangeDown_float_helper(-1.0f,
                "[-Infinity, -1.0, -2.0, -3.0, -4.0, -1.5, -6.0, -7.0, -8.0, -5.0, -10.0, -11.0, -12.0, -2.5, -14.0," +
                " -15.0, -16.0, -1.25, -18.0, -19.0]");
        rangeDown_float_helper(-1.0E20f,
                "[-Infinity, -1.0E20, -1.0000004E20, -1.0000007E20, -1.0000011E20, -1.0000002E20, -1.0000018E20," +
                " -1.0000021E20, -1.0000025E20, -1.0000014E20, -1.0000032E20, -1.0000035E20, -1.0000039E20," +
                " -1.00000055E20, -1.0000046E20, -1.00000495E20, -1.0000053E20, -1.0000001E20, -1.000006E20," +
                " -1.00000635E20]");
        rangeDown_float_helper((float) Math.PI,
                "[-Infinity, 3.1415927, 3.1415925, 3.1415923, 3.141592, 3.1415915, 3.1415913, 3.141591, 3.1415918," +
                " 3.1415906, 3.1415904, 3.14159, 3.1415896, 3.1415894, 3.1415892, 3.1415887, 3.1415884, 3.1415882," +
                " 3.1415877, 3.1415875]");
        rangeDown_float_helper((float) Math.sqrt(2),
                "[-Infinity, 1.4142135, 1.4142134, 1.4142133, 1.4142132, 1.414213, 1.4142128, 1.4142127, 1.4142131," +
                " 1.4142125, 1.4142123, 1.4142122, 1.414212, 1.4142119, 1.4142118, 1.4142115, 1.4142114, 1.4142113," +
                " 1.414211, 1.4142109]");
        rangeDown_float_helper((float) -Math.PI,
                "[-Infinity, -3.1415927, -3.141593, -3.1415932, -3.1415935, -3.141594, -3.1415942, -3.1415944," +
                " -3.1415937, -3.141595, -3.1415951, -3.1415954, -3.1415958, -3.141596, -3.1415963, -3.1415968," +
                " -3.141597, -3.1415973, -3.1415977, -3.141598]");
        rangeDown_float_helper((float) -Math.sqrt(2),
                "[-Infinity, -1.4142135, -1.4142137, -1.4142138, -1.4142139, -1.4142141, -1.4142143, -1.4142144," +
                " -1.414214, -1.4142146, -1.4142147, -1.4142148, -1.4142151, -1.4142152, -1.4142153, -1.4142156," +
                " -1.4142157, -1.4142158, -1.414216, -1.4142162]");
        rangeDown_float_helper(0.0f,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0]");
        rangeDown_float_helper(-0.0f,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0]");
        rangeDown_float_helper(Float.MIN_VALUE,
                "[-Infinity, 1.4E-45, -0.0, 0.0, -1.4E-45, -2.8E-45, -5.6E-45, -7.0E-45, -8.4E-45, -4.2E-45," +
                " -1.1E-44, -1.3E-44, -1.4E-44, -1.7E-44, -1.8E-44, -2.0E-44, -2.24E-44, -2.4E-44, -2.5E-44," +
                " -2.8E-44]");
        rangeDown_float_helper(Float.MIN_NORMAL,
                "[-Infinity, 1.17549435E-38, -0.0, 0.0, -1.17549435E-38, -2.3509887E-38, 5.877472E-39," +
                " -4.7019774E-38, -5.877472E-38, -7.052966E-38, -3.526483E-38, -9.403955E-38, -1.0579449E-37," +
                " -1.1754944E-37, -5.877472E-39, -1.4105932E-37, -1.5281427E-37, -1.6456921E-37, 8.816208E-39," +
                " -1.880791E-37]");
        rangeDown_float_helper(-Float.MIN_VALUE,
                "[-Infinity, -1.4E-45, -2.8E-45, -4.2E-45, -5.6E-45, -8.4E-45, -9.8E-45, -1.1E-44, -7.0E-45," +
                " -1.4E-44, -1.5E-44, -1.7E-44, -2.0E-44, -2.1E-44, -2.24E-44, -2.5E-44, -2.7E-44, -2.8E-44," +
                " -3.1E-44, -3.2E-44]");
        rangeDown_float_helper(-Float.MIN_NORMAL,
                "[-Infinity, -1.17549435E-38, -2.3509887E-38, -3.526483E-38, -4.7019774E-38, -1.7632415E-38," +
                " -7.052966E-38, -8.2284605E-38, -9.403955E-38, -5.877472E-38, -1.1754944E-37, -1.2930438E-37," +
                " -1.4105932E-37, -2.938736E-38, -1.6456921E-37, -1.7632415E-37, -1.880791E-37, -1.469368E-38," +
                " -2.1158898E-37, -2.2334393E-37]");
        rangeDown_float_helper(Float.MAX_VALUE,
                "[-Infinity, 3.4028235E38, 3.4028233E38, 3.402823E38, 3.4028229E38, 3.4028225E38, 3.4028222E38," +
                " 3.402822E38, 3.4028227E38, 3.4028216E38, 3.4028214E38, 3.4028212E38, 3.4028208E38, 3.4028206E38," +
                " 3.4028204E38, 3.40282E38, 3.4028198E38, 3.4028196E38, 3.4028192E38, 3.402819E38]");
        rangeDown_float_helper(-Float.MAX_VALUE, "[-Infinity, -3.4028235E38]");
        rangeDown_float_helper(Float.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0]");
        rangeDown_float_helper(Float.NEGATIVE_INFINITY, "[-Infinity]");
        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void range_float_float_helper(float a, float b, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.range(a, b)), output);
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
                " 1.6875, 1.8125, 1.9375, 1.03125, 1.09375, 1.15625]");
        range_float_float_helper(1.0f, 3.0f,
                "[1.0, 3.0, 2.0, 1.5, 2.5, 1.25, 1.75, 2.25, 2.75, 1.125, 1.375, 1.625, 1.875, 2.125, 2.375, 2.625," +
                " 2.875, 1.0625, 1.1875, 1.3125]");
        range_float_float_helper(1.0f, 4.0f,
                "[1.0, 2.0, 3.0, 4.0, 1.5, 2.5, 3.5, 1.25, 2.25, 3.25, 1.75, 2.75, 3.75, 1.125, 2.125, 3.125, 1.375," +
                " 2.375, 3.375, 1.625]");
        range_float_float_helper(1.0f, 257.0f,
                "[1.0, 257.0, 129.0, 65.0, 193.0, 33.0, 97.0, 161.0, 225.0, 17.0, 49.0, 81.0, 113.0, 145.0, 177.0," +
                " 209.0, 241.0, 9.0, 25.0, 41.0]");
        range_float_float_helper(-257.0f, -1.0f,
                "[-257.0, -1.0, -129.0, -193.0, -65.0, -225.0, -161.0, -97.0, -33.0, -241.0, -209.0, -177.0, -145.0," +
                " -113.0, -81.0, -49.0, -17.0, -249.0, -233.0, -217.0]");
        range_float_float_helper(1.0f, 1.0E20f,
                "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0," +
                " 19.0, 20.0]");
        range_float_float_helper(-1.0E20f, -1.0f,
                "[-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0, -9.0, -10.0, -11.0, -12.0, -13.0, -14.0, -15.0," +
                " -16.0, -17.0, -18.0, -19.0, -20.0]");
        range_float_float_helper((float) Math.sqrt(2), (float) Math.PI,
                "[1.4142135, 1.4142137, 1.4142138, 1.4142139, 1.414214, 1.4142141, 1.4142143, 1.4142144, 1.4142145," +
                " 1.4142146, 1.4142147, 1.4142148, 1.414215, 1.4142151, 1.4142152, 1.4142153, 1.4142154, 1.4142156," +
                " 1.4142157, 1.4142158]");
        range_float_float_helper((float) Math.PI, FloatingPointUtils.successor((float) Math.PI),
                "[3.1415927, 3.141593]");
        range_float_float_helper(0.0f, 1.0f,
                "[0.0, -0.0, 1.0, 0.5, 0.25, 0.75, 0.125, 0.375, 0.625, 0.875, 0.0625, 0.1875, 0.3125, 0.4375," +
                " 0.5625, 0.6875, 0.8125, 0.9375, 0.03125, 0.09375]");
        range_float_float_helper(-1.0f, 1.0f,
                "[-1.0, 1.0, 0.0, -0.0, -0.5, 0.5, -0.75, -0.25, 0.25, 0.75, -0.875, -0.625, -0.375, -0.125, 0.125," +
                " 0.375, 0.625, 0.875, -0.9375, -0.8125]");
        range_float_float_helper(1.0f, 1.0f, "[1.0]");
        range_float_float_helper((float) Math.PI, (float) Math.PI, "[3.1415927]");
        range_float_float_helper((float) -Math.PI, (float) Math.PI,
                "[-3.1415927, -3.1415923, -3.1415918, -3.1415913, -3.1415908, -3.1415904, -3.1415899, -3.1415894," +
                " -3.141589, -3.1415884, -3.141588, -3.1415875, -3.141587, -3.1415865, -3.141586, -3.1415856," +
                " -3.141585, -3.1415846, -3.1415842, -3.1415837]");
        range_float_float_helper(1.0f, Float.POSITIVE_INFINITY,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, 1.0f,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0]");
        range_float_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY, "[Infinity, 3.4028235E38]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE, "[-Infinity, -3.4028235E38]");
        range_float_float_helper(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0]");
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
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
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
                " 1.25, 18.0, 19.0]");
        rangeUp_double_helper(1.0E20,
                "[Infinity, 1.0E20, 1.0000000000000105E20, 1.000000000000021E20, 1.0000000000000315E20," +
                " 1.0000000000000052E20, 1.0000000000000524E20, 1.0000000000000629E20, 1.0000000000000734E20," +
                " 1.000000000000042E20, 1.0000000000000944E20, 1.0000000000001049E20, 1.0000000000001153E20," +
                " 1.0000000000000157E20, 1.0000000000001363E20, 1.0000000000001468E20, 1.0000000000001573E20," +
                " 1.0000000000000026E20, 1.0000000000001783E20, 1.0000000000001887E20]");
        rangeUp_double_helper(-1.0,
                "[Infinity, -1.0, 0.0, -0.0, 1.0, 2.0, -0.5, 4.0, 5.0, 6.0, 3.0, 8.0, 9.0, 10.0, 0.5, 12.0, 13.0," +
                " 14.0, -0.75, 16.0]");
        rangeUp_double_helper(-1.0E20,
                "[Infinity, -1.0E20, -9.999999999999895E19, -9.99999999999979E19, -9.999999999999685E19," +
                " -9.999999999999948E19, -9.999999999999476E19, -9.999999999999371E19, -9.999999999999266E19," +
                " -9.99999999999958E19, -9.999999999999056E19, -9.999999999998951E19, -9.999999999998847E19," +
                " -9.999999999999843E19, -9.999999999998637E19, -9.999999999998532E19, -9.999999999998427E19," +
                " -9.999999999999974E19, -9.999999999998217E19, -9.999999999998113E19]");
        rangeUp_double_helper(Math.PI,
                "[Infinity, 3.141592653589793, 3.1415926535897967, 3.1415926535898, 3.1415926535898038," +
                " 3.141592653589795, 3.141592653589811, 3.1415926535898144, 3.141592653589818, 3.1415926535898073," +
                " 3.141592653589825, 3.1415926535898286, 3.141592653589832, 3.1415926535897984, 3.1415926535898393," +
                " 3.141592653589843, 3.1415926535898464, 3.141592653589794, 3.1415926535898535, 3.141592653589857]");
        rangeUp_double_helper(Math.sqrt(2),
                "[Infinity, 1.4142135623730951, 1.4142135623730954, 1.4142135623730956, 1.4142135623730958," +
                " 1.4142135623730963, 1.4142135623730965, 1.4142135623730967, 1.414213562373096, 1.4142135623730971," +
                " 1.4142135623730974, 1.4142135623730976, 1.414213562373098, 1.4142135623730983, 1.4142135623730985," +
                " 1.414213562373099, 1.4142135623730991, 1.4142135623730994, 1.4142135623730998, 1.4142135623731]");
        rangeUp_double_helper(-Math.PI,
                "[Infinity, -3.141592653589793, -3.1415926535897896, -3.141592653589786, -3.1415926535897825," +
                " -3.1415926535897913, -3.1415926535897754, -3.141592653589772, -3.1415926535897682," +
                " -3.141592653589779, -3.141592653589761, -3.1415926535897576, -3.141592653589754," +
                " -3.141592653589788, -3.141592653589747, -3.1415926535897434, -3.14159265358974," +
                " -3.1415926535897922, -3.1415926535897327, -3.141592653589729]");
        rangeUp_double_helper(-Math.sqrt(2),
                "[Infinity, -1.4142135623730951, -1.414213562373095, -1.4142135623730947, -1.4142135623730945," +
                " -1.414213562373094, -1.4142135623730938, -1.4142135623730936, -1.4142135623730943," +
                " -1.4142135623730931, -1.414213562373093, -1.4142135623730927, -1.4142135623730923," +
                " -1.414213562373092, -1.4142135623730918, -1.4142135623730914, -1.4142135623730911," +
                " -1.414213562373091, -1.4142135623730905, -1.4142135623730903]");
        rangeUp_double_helper(0.0,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0]");
        rangeUp_double_helper(-0.0,
                "[Infinity, 0.0, -0.0, 1.0, 2.0, 3.0, 0.5, 5.0, 6.0, 7.0, 4.0, 9.0, 10.0, 11.0, 1.5, 13.0, 14.0," +
                " 15.0, 0.25, 17.0]");
        rangeUp_double_helper(Double.MIN_VALUE,
                "[Infinity, 4.9E-324, 1.0E-323, 1.5E-323, 2.0E-323, 3.0E-323, 3.5E-323, 4.0E-323, 2.5E-323," +
                " 4.9E-323, 5.4E-323, 5.9E-323, 6.9E-323, 7.4E-323, 7.9E-323, 8.9E-323, 9.4E-323, 1.0E-322," +
                " 1.1E-322, 1.14E-322]");
        rangeUp_double_helper(Double.MIN_NORMAL,
                "[Infinity, 2.2250738585072014E-308, 4.450147717014403E-308, 6.675221575521604E-308," +
                " 8.900295434028806E-308, 3.337610787760802E-308, 1.3350443151043208E-307, 1.557551700955041E-307," +
                " 1.7800590868057611E-307, 1.1125369292536007E-307, 2.2250738585072014E-307," +
                " 2.4475812443579215E-307, 2.6700886302086417E-307, 5.562684646268003E-308, 3.115103401910082E-307," +
                " 3.337610787760802E-307, 3.5601181736115222E-307, 2.7813423231340017E-308, 4.0051329453129625E-307," +
                " 4.227640331163683E-307]");
        rangeUp_double_helper(-Double.MIN_VALUE,
                "[Infinity, -4.9E-324, 0.0, -0.0, 4.9E-324, 1.0E-323, 2.0E-323, 2.5E-323, 3.0E-323, 1.5E-323," +
                " 4.0E-323, 4.4E-323, 4.9E-323, 5.9E-323, 6.4E-323, 6.9E-323, 7.9E-323, 8.4E-323, 8.9E-323," +
                " 1.0E-322]");
        rangeUp_double_helper(-Double.MIN_NORMAL,
                "[Infinity, -2.2250738585072014E-308, 0.0, -0.0, 2.2250738585072014E-308, 4.450147717014403E-308," +
                " -1.1125369292536007E-308, 8.900295434028806E-308, 1.1125369292536007E-307," +
                " 1.3350443151043208E-307, 6.675221575521604E-308, 1.7800590868057611E-307, 2.0025664726564812E-307," +
                " 2.2250738585072014E-307, 1.1125369292536007E-308, 2.6700886302086417E-307," +
                " 2.8925960160593618E-307, 3.115103401910082E-307, -1.668805393880401E-308, 3.5601181736115222E-307]");
        rangeUp_double_helper(-Double.MAX_VALUE,
                "[Infinity, -1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                " -1.7976931348623151E308, -1.7976931348623147E308, -1.7976931348623145E308," +
                " -1.7976931348623143E308, -1.797693134862315E308, -1.797693134862314E308, -1.7976931348623137E308," +
                " -1.7976931348623135E308, -1.7976931348623131E308, -1.797693134862313E308, -1.7976931348623127E308," +
                " -1.7976931348623123E308, -1.7976931348623121E308, -1.797693134862312E308, -1.7976931348623115E308," +
                " -1.7976931348623113E308]");
        rangeUp_double_helper(Double.POSITIVE_INFINITY, "[Infinity]");
        rangeUp_double_helper(Double.NEGATIVE_INFINITY,
                "[Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0," +
                " 1.5, -1.5, 12.0, -12.0]");
        rangeUp_double_fail_helper(Double.NaN);
    }

    private static void rangeDown_double_helper(double a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
                " -12.0, -13.0, -14.0, 0.75, -16.0]");
        rangeDown_double_helper(1.0E20,
                "[-Infinity, 1.0E20, 9.999999999999895E19, 9.99999999999979E19, 9.999999999999685E19," +
                " 9.999999999999948E19, 9.999999999999476E19, 9.999999999999371E19, 9.999999999999266E19," +
                " 9.99999999999958E19, 9.999999999999056E19, 9.999999999998951E19, 9.999999999998847E19," +
                " 9.999999999999843E19, 9.999999999998637E19, 9.999999999998532E19, 9.999999999998427E19," +
                " 9.999999999999974E19, 9.999999999998217E19, 9.999999999998113E19]");
        rangeDown_double_helper(-1.0,
                "[-Infinity, -1.0, -2.0, -3.0, -4.0, -1.5, -6.0, -7.0, -8.0, -5.0, -10.0, -11.0, -12.0, -2.5, -14.0," +
                " -15.0, -16.0, -1.25, -18.0, -19.0]");
        rangeDown_double_helper(-1.0E20,
                "[-Infinity, -1.0E20, -1.0000000000000105E20, -1.000000000000021E20, -1.0000000000000315E20," +
                " -1.0000000000000052E20, -1.0000000000000524E20, -1.0000000000000629E20, -1.0000000000000734E20," +
                " -1.000000000000042E20, -1.0000000000000944E20, -1.0000000000001049E20, -1.0000000000001153E20," +
                " -1.0000000000000157E20, -1.0000000000001363E20, -1.0000000000001468E20, -1.0000000000001573E20," +
                " -1.0000000000000026E20, -1.0000000000001783E20, -1.0000000000001887E20]");
        rangeDown_double_helper(Math.PI,
                "[-Infinity, 3.141592653589793, 3.1415926535897896, 3.141592653589786, 3.1415926535897825," +
                " 3.1415926535897913, 3.1415926535897754, 3.141592653589772, 3.1415926535897682, 3.141592653589779," +
                " 3.141592653589761, 3.1415926535897576, 3.141592653589754, 3.141592653589788, 3.141592653589747," +
                " 3.1415926535897434, 3.14159265358974, 3.1415926535897922, 3.1415926535897327, 3.141592653589729]");
        rangeDown_double_helper(Math.sqrt(2),
                "[-Infinity, 1.4142135623730951, 1.414213562373095, 1.4142135623730947, 1.4142135623730945," +
                " 1.414213562373094, 1.4142135623730938, 1.4142135623730936, 1.4142135623730943, 1.4142135623730931," +
                " 1.414213562373093, 1.4142135623730927, 1.4142135623730923, 1.414213562373092, 1.4142135623730918," +
                " 1.4142135623730914, 1.4142135623730911, 1.414213562373091, 1.4142135623730905, 1.4142135623730903]");
        rangeDown_double_helper(-Math.PI,
                "[-Infinity, -3.141592653589793, -3.1415926535897967, -3.1415926535898, -3.1415926535898038," +
                " -3.141592653589795, -3.141592653589811, -3.1415926535898144, -3.141592653589818," +
                " -3.1415926535898073, -3.141592653589825, -3.1415926535898286, -3.141592653589832," +
                " -3.1415926535897984, -3.1415926535898393, -3.141592653589843, -3.1415926535898464," +
                " -3.141592653589794, -3.1415926535898535, -3.141592653589857]");
        rangeDown_double_helper(-Math.sqrt(2),
                "[-Infinity, -1.4142135623730951, -1.4142135623730954, -1.4142135623730956, -1.4142135623730958," +
                " -1.4142135623730963, -1.4142135623730965, -1.4142135623730967, -1.414213562373096," +
                " -1.4142135623730971, -1.4142135623730974, -1.4142135623730976, -1.414213562373098," +
                " -1.4142135623730983, -1.4142135623730985, -1.414213562373099, -1.4142135623730991," +
                " -1.4142135623730994, -1.4142135623730998, -1.4142135623731]");
        rangeDown_double_helper(0.0,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0]");
        rangeDown_double_helper(-0.0,
                "[-Infinity, -0.0, 0.0, -1.0, -2.0, -3.0, -0.5, -5.0, -6.0, -7.0, -4.0, -9.0, -10.0, -11.0, -1.5," +
                " -13.0, -14.0, -15.0, -0.25, -17.0]");
        rangeDown_double_helper(Double.MIN_VALUE,
                "[-Infinity, 4.9E-324, -0.0, 0.0, -4.9E-324, -1.0E-323, -2.0E-323, -2.5E-323, -3.0E-323, -1.5E-323," +
                " -4.0E-323, -4.4E-323, -4.9E-323, -5.9E-323, -6.4E-323, -6.9E-323, -7.9E-323, -8.4E-323, -8.9E-323," +
                " -1.0E-322]");
        rangeDown_double_helper(Double.MIN_NORMAL,
                "[-Infinity, 2.2250738585072014E-308, -0.0, 0.0, -2.2250738585072014E-308, -4.450147717014403E-308," +
                " 1.1125369292536007E-308, -8.900295434028806E-308, -1.1125369292536007E-307," +
                " -1.3350443151043208E-307, -6.675221575521604E-308, -1.7800590868057611E-307," +
                " -2.0025664726564812E-307, -2.2250738585072014E-307, -1.1125369292536007E-308," +
                " -2.6700886302086417E-307, -2.8925960160593618E-307, -3.115103401910082E-307," +
                " 1.668805393880401E-308, -3.5601181736115222E-307]");
        rangeDown_double_helper(-Double.MIN_VALUE,
                "[-Infinity, -4.9E-324, -1.0E-323, -1.5E-323, -2.0E-323, -3.0E-323, -3.5E-323, -4.0E-323, -2.5E-323," +
                " -4.9E-323, -5.4E-323, -5.9E-323, -6.9E-323, -7.4E-323, -7.9E-323, -8.9E-323, -9.4E-323, -1.0E-322," +
                " -1.1E-322, -1.14E-322]");
        rangeDown_double_helper(-Double.MIN_NORMAL,
                "[-Infinity, -2.2250738585072014E-308, -4.450147717014403E-308, -6.675221575521604E-308," +
                " -8.900295434028806E-308, -3.337610787760802E-308, -1.3350443151043208E-307," +
                " -1.557551700955041E-307, -1.7800590868057611E-307, -1.1125369292536007E-307," +
                " -2.2250738585072014E-307, -2.4475812443579215E-307, -2.6700886302086417E-307," +
                " -5.562684646268003E-308, -3.115103401910082E-307, -3.337610787760802E-307," +
                " -3.5601181736115222E-307, -2.7813423231340017E-308, -4.0051329453129625E-307," +
                " -4.227640331163683E-307]");
        rangeDown_double_helper(Double.MAX_VALUE,
                "[-Infinity, 1.7976931348623157E308, 1.7976931348623155E308, 1.7976931348623153E308," +
                " 1.7976931348623151E308, 1.7976931348623147E308, 1.7976931348623145E308, 1.7976931348623143E308," +
                " 1.797693134862315E308, 1.797693134862314E308, 1.7976931348623137E308, 1.7976931348623135E308," +
                " 1.7976931348623131E308, 1.797693134862313E308, 1.7976931348623127E308, 1.7976931348623123E308," +
                " 1.7976931348623121E308, 1.797693134862312E308, 1.7976931348623115E308, 1.7976931348623113E308]");
        rangeDown_double_helper(-Double.MAX_VALUE, "[-Infinity, -1.7976931348623157E308]");
        rangeDown_double_helper(Double.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0]");
        rangeDown_double_helper(Double.NEGATIVE_INFINITY, "[-Infinity]");
        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void range_double_double_helper(double a, double b, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.range(a, b)), output);
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
                " 1.6875, 1.8125, 1.9375, 1.03125, 1.09375, 1.15625]");
        range_double_double_helper(1.0, 3.0,
                "[1.0, 3.0, 2.0, 1.5, 2.5, 1.25, 1.75, 2.25, 2.75, 1.125, 1.375, 1.625, 1.875, 2.125, 2.375, 2.625," +
                " 2.875, 1.0625, 1.1875, 1.3125]");
        range_double_double_helper(1.0, 4.0,
                "[1.0, 2.0, 3.0, 4.0, 1.5, 2.5, 3.5, 1.25, 2.25, 3.25, 1.75, 2.75, 3.75, 1.125, 2.125, 3.125, 1.375," +
                " 2.375, 3.375, 1.625]");
        range_double_double_helper(1.0, 257.0,
                "[1.0, 257.0, 129.0, 65.0, 193.0, 33.0, 97.0, 161.0, 225.0, 17.0, 49.0, 81.0, 113.0, 145.0, 177.0," +
                " 209.0, 241.0, 9.0, 25.0, 41.0]");
        range_double_double_helper(-257.0, -1.0,
                "[-257.0, -1.0, -129.0, -193.0, -65.0, -225.0, -161.0, -97.0, -33.0, -241.0, -209.0, -177.0, -145.0," +
                " -113.0, -81.0, -49.0, -17.0, -249.0, -233.0, -217.0]");
        range_double_double_helper(1.0, 1.0E20,
                "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0," +
                " 19.0, 20.0]");
        range_double_double_helper(-1.0E20, -1.0,
                "[-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0, -9.0, -10.0, -11.0, -12.0, -13.0, -14.0, -15.0," +
                " -16.0, -17.0, -18.0, -19.0, -20.0]");
        range_double_double_helper(Math.sqrt(2), Math.PI,
                "[1.4142135623730951, 1.4142135623730954, 1.4142135623730956, 1.4142135623730958, 1.414213562373096," +
                " 1.4142135623730963, 1.4142135623730965, 1.4142135623730967, 1.414213562373097, 1.4142135623730971," +
                " 1.4142135623730974, 1.4142135623730976, 1.4142135623730978, 1.414213562373098, 1.4142135623730983," +
                " 1.4142135623730985, 1.4142135623730987, 1.414213562373099, 1.4142135623730991, 1.4142135623730994]");
        range_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI),
                "[3.1415926535897936, 3.141592653589793]");
        range_double_double_helper(0.0, 1.0,
                "[0.0, -0.0, 1.0, 0.5, 0.25, 0.75, 0.125, 0.375, 0.625, 0.875, 0.0625, 0.1875, 0.3125, 0.4375," +
                " 0.5625, 0.6875, 0.8125, 0.9375, 0.03125, 0.09375]");
        range_double_double_helper(-1.0, 1.0,
                "[-1.0, 1.0, 0.0, -0.0, -0.5, 0.5, -0.75, -0.25, 0.25, 0.75, -0.875, -0.625, -0.375, -0.125, 0.125," +
                " 0.375, 0.625, 0.875, -0.9375, -0.8125]");
        range_double_double_helper(1.0, 1.0, "[1.0]");
        range_double_double_helper(Math.PI, Math.PI, "[3.141592653589793]");
        range_double_double_helper(-Math.PI, Math.PI,
                "[-3.141592653589793, -3.141592653589786, -3.141592653589779, -3.141592653589772," +
                " -3.1415926535897647, -3.1415926535897576, -3.1415926535897505, -3.1415926535897434," +
                " -3.1415926535897363, -3.141592653589729, -3.141592653589722, -3.141592653589715," +
                " -3.141592653589708, -3.1415926535897007, -3.1415926535896936, -3.1415926535896865," +
                " -3.1415926535896794, -3.1415926535896723, -3.141592653589665, -3.141592653589658]");
        range_double_double_helper(1.0, Double.POSITIVE_INFINITY,
                "[Infinity, 1.0, 2.0, 3.0, 4.0, 1.5, 6.0, 7.0, 8.0, 5.0, 10.0, 11.0, 12.0, 2.5, 14.0, 15.0, 16.0," +
                " 1.25, 18.0, 19.0]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, 1.0,
                "[-Infinity, 1.0, -0.0, 0.0, -1.0, -2.0, 0.5, -4.0, -5.0, -6.0, -3.0, -8.0, -9.0, -10.0, -0.5," +
                " -12.0, -13.0, -14.0, 0.75, -16.0]");
        range_double_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY, "[Infinity, 1.7976931348623157E308]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE,
                "[-Infinity, -1.7976931348623157E308]");
        range_double_double_helper(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                "[-Infinity, Infinity, -0.0, 0.0, -1.0, 1.0, -2.0, 2.0, -3.0, 3.0, -6.0, 6.0, -0.5, 0.5, -4.0, 4.0," +
                " -1.5, 1.5, -12.0, 12.0]");
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
    public void testNonzeroBigDecimals() {
        aeqit(take(50, P.nonzeroBigDecimals()),
                "[1, 0.1, -1, 1E+1, 2, -0.1, -2, 0.01, 3, 0.2, -3, -1E+1, 4, -0.2, -4, 1E+2, 5, 0.3, -5, 2E+1, 6," +
                " -0.3, -6, -0.01, 7, 0.4, -7, -2E+1, 8, -0.4, -8, 0.001, 9, 0.5, -9, 3E+1, 10, -0.5, -10, 0.02, 11," +
                " 0.6, -11, -3E+1, 12, -0.6, -12, -1E+2, 13, 0.7]");
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
