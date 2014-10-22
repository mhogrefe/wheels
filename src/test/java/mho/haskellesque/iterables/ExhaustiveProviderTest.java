package mho.haskellesque.iterables;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;

public class ExhaustiveProviderTest {
    private static final ExhaustiveProvider P = new ExhaustiveProvider();

    @Test
    public void testBooleans() {
        aeq(P.booleans(), "[false, true]");
    }

    @Test
    public void testOrderingsAscending() {
        aeq(P.orderingsAscending(), "[LT, EQ, GT]");
    }

    @Test
    public void testOrderings() {
        aeq(P.orderings(), "[EQ, LT, GT]");
    }

    @Test
    public void testRoundingModes() {
        aeq(P.roundingModes(), "[UNNECESSARY, UP, DOWN, CEILING, FLOOR, HALF_UP, HALF_DOWN, HALF_EVEN]");
    }

    @Test
    public void testBytesAscending() {
        assertEquals(length(P.bytesAscending()), 256);
        aeq(take(20, P.bytesAscending()),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119," +
                " -118, -117, -116, -115, -114, -113, -112, -111, -110, -109]");
    }

    @Test
    public void testShortsAscending() {
        assertEquals(length(P.shortsAscending()), 65536);
        aeq(take(20, P.shortsAscending()),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759," +
                " -32758, -32757, -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749]");
    }

    @Test
    public void testIntegersAscending() {
        aeq(take(20, P.integersAscending()),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644," +
                " -2147483643, -2147483642, -2147483641, -2147483640, -2147483639," +
                " -2147483638, -2147483637, -2147483636, -2147483635, -2147483634," +
                " -2147483633, -2147483632, -2147483631, -2147483630, -2147483629]");
    }

    @Test
    public void testLongsAscending() {
        aeq(take(20, P.longsAscending()),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789]");
    }

    @Test
    public void testPositiveBytes() {
        assertEquals(length(P.positiveBytes()), 127);
        aeq(take(20, P.positiveBytes()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveShorts() {
        assertEquals(length(P.positiveShorts()), 32767);
        aeq(take(20, P.positiveShorts()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveIntegers() {
        aeq(take(20, P.positiveIntegers()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveLongs() {
        aeq(take(20, P.positiveLongs()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveBigIntegers() {
        aeq(take(20, P.positiveBigIntegers()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testNegativeBytes() {
        assertEquals(length(P.negativeBytes()), 128);
        aeq(take(20, P.negativeBytes()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeShorts() {
        assertEquals(length(P.negativeShorts()), 32768);
        aeq(take(20, P.negativeShorts()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeIntegers() {
        aeq(take(20, P.negativeIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeLongs() {
        aeq(take(20, P.negativeLongs()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeBigIntegers() {
        aeq(take(20, P.negativeBigIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNaturalBytes() {
        assertEquals(length(P.naturalBytes()), 128);
        aeq(take(20, P.naturalBytes()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalShorts() {
        assertEquals(length(P.naturalShorts()), 32768);
        aeq(take(20, P.naturalShorts()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalIntegers() {
        aeq(take(20, P.naturalIntegers()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalLongs() {
        aeq(take(20, P.naturalLongs()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalBigIntegers() {
        aeq(take(20, P.naturalBigIntegers()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testBytes() {
        assertEquals(length(P.bytes()), 256);
        aeq(take(5, (List<Byte>) reverse(P.bytes())), "[-128, -127, 127, -126, 126]");
        aeq(take(20, P.bytes()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testShorts() {
        assertEquals(length(P.shorts()), 65536);
        aeq(take(5, (List<Short>) reverse(P.shorts())), "[-32768, -32767, 32767, -32766, 32766]");
        aeq(take(20, P.shorts()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testIntegers() {
        aeq(take(20, P.integers()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testLongs() {
        aeq(take(20, P.longs()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testBigIntegers() {
        aeq(take(20, P.bigIntegers()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testAsciiCharactersAscending() {
        aeq(length(P.asciiCharactersAscending()), 128);
        aeq(charsToString(P.asciiCharactersAscending()),
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37" +
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~" +
                "\177"
        );
    }

    @Test
    public void testAsciiCharacters() {
        aeq(length(P.asciiCharacters()), 128);
        aeq(charsToString(P.asciiCharacters()),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37 \177"
        );
    }

    @Test
    public void testCharactersAscending() {
        aeq(length(P.charactersAscending()), 65536);
        aeq(charsToString(take(256, P.charactersAscending())),
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
        aeq(length(P.characters()), 65536);
        aeq(charsToString(take(256, P.characters())),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37 \177" +
                "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F" +
                "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F" +
                " ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ"
        );
    }

    @Test
    public void testPositiveOrdinaryFloatsAscending() {
        aeq(take(20, P.positiveOrdinaryFloatsAscending()),
                "[1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 7.0E-45, 8.4E-45, 9.8E-45, 1.1E-44, 1.3E-44, 1.4E-44," +
                " 1.5E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.7E-44, 2.8E-44]");
    }

    @Test
    public void testNegativeOrdinaryFloatsAscending() {
        aeq(take(20, P.negativeOrdinaryFloatsAscending()),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testOrdinaryFloatsAscending() {
        aeq(take(20, P.ordinaryFloatsAscending()),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testFloatsAscending() {
        aeq(take(20, P.floatsAscending()),
                "[-Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38," +
                " -3.4028225E38, -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38," +
                " -3.4028212E38, -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38," +
                " -3.40282E38, -3.4028198E38]");
    }

    @Test
    public void testPositiveOrdinaryFloats() {
        aeq(take(50, P.positiveOrdinaryFloats()),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryFloats() {
        aeq(take(50, P.negativeOrdinaryFloats()),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryFloats() {
        aeq(take(50, P.ordinaryFloats()),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                " -48.0, 1.25]");
    }

    @Test
    public void testFloats() {
        aeq(take(50, P.floats()),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    @Test
    public void testPositiveOrdinaryDoublesAscending() {
        aeq(take(20, P.positiveOrdinaryDoublesAscending()),
                "[4.9E-324, 1.0E-323, 1.5E-323, 2.0E-323, 2.5E-323, 3.0E-323, 3.5E-323, 4.0E-323, 4.4E-323," +
                " 4.9E-323, 5.4E-323, 5.9E-323, 6.4E-323, 6.9E-323, 7.4E-323, 7.9E-323, 8.4E-323, 8.9E-323," +
                " 9.4E-323, 1.0E-322]");
    }

    @Test
    public void testNegativeOrdinaryDoublesAscending() {
        aeq(take(20, P.negativeOrdinaryDoublesAscending()),
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
        aeq(take(20, P.ordinaryDoublesAscending()),
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
        aeq(take(20, P.doublesAscending()),
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
        aeq(take(50, P.positiveOrdinaryDoubles()),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryDoubles() {
        aeq(take(50, P.negativeOrdinaryDoubles()),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryDoubles() {
        aeq(take(50, P.ordinaryDoubles()),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                " -48.0, 1.25]");
    }

    @Test
    public void testDoubles() {
        aeq(take(50, P.doubles()),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                " 16.0, -16.0, 0.375]");
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        aeq(P.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 3), (3, 1), (2, 2), (4, 1), (1, 4)," +
                        " (3, 2), (2, 3), (4, 2), (3, 3), (2, 4), (4, 3), (3, 4), (4, 4)]");
        aeq(P.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, null), (null, 1), (2, 2), (4, 1), (1, 4)," +
                        " (null, 2), (2, null), (4, 2), (null, null), (2, 4), (4, null), (null, 4), (4, 4)]");
        aeq(P.pairsLogarithmicOrder(new ArrayList<Integer>()), "[]");
        aeq(take(20, P.pairsLogarithmicOrder(P.naturalBigIntegers())),
                "[(0, 0), (0, 1), (1, 0), (0, 2), (2, 0), (1, 1), (3, 0), (0, 3), (4, 0), (2, 1)," +
                " (5, 0), (1, 2), (6, 0), (3, 1), (7, 0), (0, 4), (8, 0), (4, 1), (9, 0), (2, 2)]");
        aeq(take(20, P.pairsLogarithmicOrder((Iterable<BigInteger>) cons(null, P.naturalBigIntegers()))),
                "[(null, null), (null, 0), (0, null), (null, 1), (1, null), (0, 0), (2, null), (null, 2), (3, null)," +
                " (1, 0), (4, null), (0, 1), (5, null), (2, 0), (6, null), (null, 3), (7, null), (3, 0), (8, null)," +
                " (1, 1)]");
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable_Iterable() {
        aeq(P.pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (3, a), (2, b), (4, a), (1, d)," +
                " (3, b), (2, c), (4, b), (3, c), (2, d), (4, c), (3, d), (4, d)]");
        aeq(P.pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (null, a), (2, b), (4, a), (1, d)," +
                " (null, b), (2, c), (4, b), (null, c), (2, d), (4, c), (null, d), (4, d)]");
        aeq(P.pairsLogarithmicOrder(new ArrayList<Integer>(), fromString("abcd")), "[]");
        aeq(P.pairsLogarithmicOrder(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
        aeq(take(20, P.pairsLogarithmicOrder(P.naturalBigIntegers(), fromString("abcd"))),
                "[(0, a), (0, b), (1, a), (0, c), (2, a), (1, b), (3, a), (0, d), (4, a), (2, b)," +
                " (5, a), (1, c), (6, a), (3, b), (7, a), (8, a), (4, b), (9, a), (2, c), (10, a)]");
        aeq(take(20, P.pairsLogarithmicOrder(fromString("abcd"), P.naturalBigIntegers())),
                "[(a, 0), (a, 1), (b, 0), (a, 2), (c, 0), (b, 1), (d, 0), (a, 3), (c, 1), (b, 2)," +
                " (d, 1), (a, 4), (c, 2), (b, 3), (d, 2), (a, 5), (c, 3), (b, 4), (d, 3), (a, 6)]");
        aeq(take(20, P.pairsLogarithmicOrder(P.positiveBigIntegers(), P.negativeBigIntegers())),
                "[(1, -1), (1, -2), (2, -1), (1, -3), (3, -1), (2, -2), (4, -1), (1, -4), (5, -1), (3, -2)," +
                " (6, -1), (2, -3), (7, -1), (4, -2), (8, -1), (1, -5), (9, -1), (5, -2), (10, -1), (3, -3)]");
    }

    @Test
    public void testPairs() {
        aeq(P.pairs(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d)," +
                " (3, a), (3, b), (4, a), (4, b), (3, c), (3, d), (4, c), (4, d)]");
        aeq(P.pairs(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d)," +
                " (null, a), (null, b), (4, a), (4, b), (null, c), (null, d), (4, c), (4, d)]");
        aeq(P.pairs(new ArrayList<Integer>(), fromString("abcd")), "[]");
        aeq(P.pairs(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
        aeq(take(20, P.pairs(P.naturalBigIntegers(), fromString("abcd"))),
                "[(0, a), (0, b), (1, a), (1, b), (0, c), (0, d), (1, c), (1, d), (2, a), (2, b)," +
                " (3, a), (3, b), (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b)]");
        aeq(take(20, P.pairs(fromString("abcd"), P.naturalBigIntegers())),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (a, 2), (a, 3), (b, 2), (b, 3), (c, 0), (c, 1)," +
                " (d, 0), (d, 1), (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5)]");
        aeq(take(20, P.pairs(P.positiveBigIntegers(), P.negativeBigIntegers())),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (1, -3), (1, -4), (2, -3), (2, -4), (3, -1), (3, -2)," +
                 " (4, -1), (4, -2), (3, -3), (3, -4), (4, -3), (4, -4), (1, -5), (1, -6), (2, -5), (2, -6)]");
    }

    @Test
    public void testTriples() {
        aeq(P.triples(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        aeq(P.triples(Arrays.asList(1, 2, null, 4), fromString("abcd"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (1, d, false), (1, d, true)," +
                " (2, c, false), (2, c, true), (2, d, false), (2, d, true), (null, a, false), (null, a, true)," +
                " (null, b, false), (null, b, true), (4, a, false), (4, a, true), (4, b, false), (4, b, true)," +
                " (null, c, false), (null, c, true), (null, d, false), (null, d, true), (4, c, false)," +
                " (4, c, true), (4, d, false), (4, d, true)]");
        aeq(P.triples(new ArrayList<Integer>(), fromString("abcd"), P.booleans()), "[]");
        aeq(P.triples(new ArrayList<Integer>(), new ArrayList<Character>(), new ArrayList<Boolean>()), "[]");
        aeq(take(20, P.triples(P.naturalBigIntegers(), fromString("abcd"), P.booleans())),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (1, a, false), (1, a, true)," +
                " (1, b, false), (1, b, true), (0, c, false), (0, c, true), (0, d, false), (0, d, true)," +
                " (1, c, false), (1, c, true), (1, d, false), (1, d, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true)]");
        aeq(take(20, P.triples(fromString("abcd"), P.booleans(), P.naturalBigIntegers())),
                "[(a, false, 0), (a, false, 1), (a, true, 0), (a, true, 1), (b, false, 0), (b, false, 1)," +
                " (b, true, 0), (b, true, 1), (a, false, 2), (a, false, 3), (a, true, 2), (a, true, 3)," +
                " (b, false, 2), (b, false, 3), (b, true, 2), (b, true, 3), (c, false, 0), (c, false, 1)," +
                " (c, true, 0), (c, true, 1)]");
        aeq(take(20, P.triples(P.positiveBigIntegers(), P.negativeBigIntegers(), P.characters())),
                "[(1, -1, a), (1, -1, b), (1, -2, a), (1, -2, b), (2, -1, a), (2, -1, b), (2, -2, a), (2, -2, b)," +
                " (1, -1, c), (1, -1, d), (1, -2, c), (1, -2, d), (2, -1, c), (2, -1, d), (2, -2, c), (2, -2, d)," +
                " (1, -3, a), (1, -3, b), (1, -4, a), (1, -4, b)]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
