package mho.haskellesque.iterables;

import org.junit.Test;

import java.util.List;

import static mho.haskellesque.iterables.Exhaustive.*;
import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;

public class ExhaustiveTest {
    @Test
    public void testBooleans() {
        aeq(BOOLEANS, "[false, true]");
    }

    @Test
    public void testOrderingsAscending() {
        aeq(ORDERINGS_ASCENDING, "[LT, EQ, GT]");
    }

    @Test
    public void testOrderings() {
        aeq(ORDERINGS, "[EQ, LT, GT]");
    }

    @Test
    public void testRoundingModes() {
        aeq(ROUNDING_MODES, "[UNNECESSARY, UP, DOWN, CEILING, FLOOR, HALF_UP, HALF_DOWN, HALF_EVEN]");
    }

    @Test
    public void testBytesAscending() {
        assertEquals(length(BYTES_ASCENDING), 256);
        aeq(take(20, BYTES_ASCENDING),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119," +
                " -118, -117, -116, -115, -114, -113, -112, -111, -110, -109]");
    }

    @Test
    public void testShortsAscending() {
        assertEquals(length(SHORTS_ASCENDING), 65536);
        aeq(take(20, SHORTS_ASCENDING),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759," +
                " -32758, -32757, -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749]");
    }

    @Test
    public void testIntegersAscending() {
        aeq(take(20, INTEGERS_ASCENDING),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644," +
                " -2147483643, -2147483642, -2147483641, -2147483640, -2147483639," +
                " -2147483638, -2147483637, -2147483636, -2147483635, -2147483634," +
                " -2147483633, -2147483632, -2147483631, -2147483630, -2147483629]");
    }

    @Test
    public void testLongsAscending() {
        aeq(take(20, LONGS_ASCENDING),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789]");
    }

    @Test
    public void testPositiveBytes() {
        assertEquals(length(POSITIVE_BYTES), 127);
        aeq(take(20, POSITIVE_BYTES), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveShorts() {
        assertEquals(length(POSITIVE_SHORTS), 32767);
        aeq(take(20, POSITIVE_SHORTS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveIntegers() {
        aeq(take(20, POSITIVE_INTEGERS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveLongs() {
        aeq(take(20, POSITIVE_LONGS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveBigIntegers() {
        aeq(take(20, POSITIVE_BIG_INTEGERS),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testNegativeBytes() {
        assertEquals(length(NEGATIVE_BYTES), 128);
        aeq(take(20, NEGATIVE_BYTES),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeShorts() {
        assertEquals(length(NEGATIVE_SHORTS), 32768);
        aeq(take(20, NEGATIVE_SHORTS),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeIntegers() {
        aeq(take(20, NEGATIVE_INTEGERS),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeLongs() {
        aeq(take(20, NEGATIVE_LONGS),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeBigIntegers() {
        aeq(take(20, NEGATIVE_BIG_INTEGERS),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNaturalBytes() {
        assertEquals(length(NATURAL_BYTES), 128);
        aeq(take(20, NATURAL_BYTES), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalShorts() {
        assertEquals(length(NATURAL_SHORTS), 32768);
        aeq(take(20, NATURAL_SHORTS), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalIntegers() {
        aeq(take(20, NATURAL_INTEGERS), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalLongs() {
        aeq(take(20, NATURAL_LONGS), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalBigIntegers() {
        aeq(take(20, NATURAL_BIG_INTEGERS), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testBytes() {
        assertEquals(length(BYTES), 256);
        aeq(take(5, (List<Byte>) reverse(BYTES)), "[-128, -127, 127, -126, 126]");
        aeq(take(20, BYTES), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testShorts() {
        assertEquals(length(SHORTS), 65536);
        aeq(take(5, (List<Short>) reverse(SHORTS)), "[-32768, -32767, 32767, -32766, 32766]");
        aeq(take(20, SHORTS), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testIntegers() {
        aeq(take(20, INTEGERS), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testLongs() {
        aeq(take(20, LONGS), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testBigIntegers() {
        aeq(take(20, BIG_INTEGERS), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testAsciiCharactersAscending() {
        aeq(length(ASCII_CHARACTERS_ASCENDING), 128);
        aeq(charsToString(ASCII_CHARACTERS_ASCENDING),
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177"
        );
    }

    @Test
    public void testAsciiCharacters() {
        aeq(length(ASCII_CHARACTERS), 128);
        aeq(charsToString(ASCII_CHARACTERS),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37 \177"
        );
    }

    @Test
    public void testCharactersAscending() {
        aeq(length(CHARACTERS_ASCENDING), 65536);
        aeq(charsToString(take(256, CHARACTERS_ASCENDING)),
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F" +
                " ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }

    @Test
    public void testCharacters() {
        aeq(length(CHARACTERS), 65536);
        aeq(charsToString(take(256, CHARACTERS)),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37 \177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F" +
                " ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }

    @Test
    public void testPositiveOrdinaryFloatsAscending() {
        aeq(take(20, POSITIVE_ORDINARY_FLOATS_ASCENDING),
                "[1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 7.0E-45, 8.4E-45, 9.8E-45, 1.1E-44, 1.3E-44, 1.4E-44," +
                " 1.5E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.7E-44, 2.8E-44]");
    }

    @Test
    public void testNegativeOrdinaryFloatsAscending() {
        aeq(take(20, NEGATIVE_ORDINARY_FLOATS_ASCENDING),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testOrdinaryFloatsAscending() {
        aeq(take(20, ORDINARY_FLOATS_ASCENDING),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testFloatsAscending() {
        aeq(take(20, FLOATS_ASCENDING),
                "[-Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38," +
                " -3.4028225E38, -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38," +
                " -3.4028212E38, -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38," +
                " -3.40282E38, -3.4028198E38]");
    }

    @Test
    public void testPositiveOrdinaryFloats() {
        aeq(take(50, POSITIVE_ORDINARY_FLOATS),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryFloats() {
        aeq(take(50, NEGATIVE_ORDINARY_FLOATS),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryFloats() {
        aeq(take(50, ORDINARY_FLOATS),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                " -48.0, 1.25]");
    }

    @Test
    public void testFloats() {
        aeq(take(50, FLOATS),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    @Test
    public void testPositiveOrdinaryDoublesAscending() {
        aeq(take(20, POSITIVE_ORDINARY_DOUBLES_ASCENDING),
                "[4.9E-324, 1.0E-323, 1.5E-323, 2.0E-323, 2.5E-323, 3.0E-323, 3.5E-323, 4.0E-323, 4.4E-323," +
                " 4.9E-323, 5.4E-323, 5.9E-323, 6.4E-323, 6.9E-323, 7.4E-323, 7.9E-323, 8.4E-323, 8.9E-323," +
                " 9.4E-323, 1.0E-322]");
    }

    @Test
    public void testNegativeOrdinaryDoublesAscending() {
        aeq(take(20, NEGATIVE_ORDINARY_DOUBLES_ASCENDING),
                "[-1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                " -1.7976931348623151E308, -1.797693134862315E308, -1.7976931348623147E308," +
                " -1.7976931348623145E308, -1.7976931348623143E308, -1.7976931348623141E308," +
                " -1.797693134862314E308, -1.7976931348623137E308, -1.7976931348623135E308," +
                " -1.7976931348623133E308, -1.7976931348623131E308, -1.797693134862313E308," +
                " -1.7976931348623127E308, -1.7976931348623125E308, -1.7976931348623123E308," +
                " -1.7976931348623121E308, -1.797693134862312E308]");
    }

    @Test
    public void testOrdinaryDoublesAscending() {
        aeq(take(20, ORDINARY_DOUBLES_ASCENDING),
                "[-1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                " -1.7976931348623151E308, -1.797693134862315E308, -1.7976931348623147E308," +
                " -1.7976931348623145E308, -1.7976931348623143E308, -1.7976931348623141E308," +
                " -1.797693134862314E308, -1.7976931348623137E308, -1.7976931348623135E308," +
                " -1.7976931348623133E308, -1.7976931348623131E308, -1.797693134862313E308," +
                " -1.7976931348623127E308, -1.7976931348623125E308, -1.7976931348623123E308," +
                " -1.7976931348623121E308, -1.797693134862312E308]");
    }

    @Test
    public void testDoublesAscending() {
        aeq(take(20, DOUBLES_ASCENDING),
                "[-Infinity, -1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                " -1.7976931348623151E308, -1.797693134862315E308, -1.7976931348623147E308," +
                " -1.7976931348623145E308, -1.7976931348623143E308, -1.7976931348623141E308," +
                " -1.797693134862314E308, -1.7976931348623137E308, -1.7976931348623135E308," +
                " -1.7976931348623133E308, -1.7976931348623131E308, -1.797693134862313E308," +
                " -1.7976931348623127E308, -1.7976931348623125E308, -1.7976931348623123E308," +
                " -1.7976931348623121E308]");
    }

    @Test
    public void testPositiveOrdinaryDoubles() {
        aeq(take(50, POSITIVE_ORDINARY_DOUBLES),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryDoubles() {
        aeq(take(50, NEGATIVE_ORDINARY_DOUBLES),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryDoubles() {
        aeq(take(50, ORDINARY_DOUBLES),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                " -48.0, 1.25]");
    }

    @Test
    public void testDoubles() {
        aeq(take(50, DOUBLES),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
