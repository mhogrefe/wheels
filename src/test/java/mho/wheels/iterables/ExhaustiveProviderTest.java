package mho.wheels.iterables;

import org.junit.Test;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.aeqit;

public class ExhaustiveProviderTest {
    private static final ExhaustiveProvider P = ExhaustiveProvider.INSTANCE;

    @Test
    public void testBooleans() {
        aeq(P.booleans(), "[false, true]");
    }

    @Test
    public void testOrderingsIncreasing() {
        aeq(P.orderingsIncreasing(), "[LT, EQ, GT]");
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
    public void testBytesIncreasing() {
        aeq(length(P.bytesIncreasing()), 256);
        aeqit(take(20, P.bytesIncreasing()),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119," +
                        " -118, -117, -116, -115, -114, -113, -112, -111, -110, -109]");
    }

    @Test
    public void testShortsIncreasing() {
        aeq(length(P.shortsIncreasing()), 65536);
        aeqit(take(20, P.shortsIncreasing()),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759," +
                        " -32758, -32757, -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749]");
    }

    @Test
    public void testIntegersIncreasing() {
        aeqit(take(20, P.integersIncreasing()),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644," +
                        " -2147483643, -2147483642, -2147483641, -2147483640, -2147483639," +
                        " -2147483638, -2147483637, -2147483636, -2147483635, -2147483634," +
                        " -2147483633, -2147483632, -2147483631, -2147483630, -2147483629]");
    }

    @Test
    public void testLongsIncreasing() {
        aeqit(take(20, P.longsIncreasing()),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                        " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                        " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                        " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                        " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789]");
    }

    @Test
    public void testPositiveBytes() {
        aeq(length(P.positiveBytes()), 127);
        aeqit(take(20, P.positiveBytes()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveShorts() {
        aeq(length(P.positiveShorts()), 32767);
        aeqit(take(20, P.positiveShorts()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(20, P.positiveIntegers()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(20, P.positiveLongs()), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveBigIntegers() {
        aeqit(take(20, P.positiveBigIntegers()),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testNegativeBytes() {
        aeq(length(P.negativeBytes()), 128);
        aeqit(take(20, P.negativeBytes()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeShorts() {
        aeq(length(P.negativeShorts()), 32768);
        aeqit(take(20, P.negativeShorts()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeIntegers() {
        aeqit(take(20, P.negativeIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeLongs() {
        aeqit(take(20, P.negativeLongs()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNegativeBigIntegers() {
        aeqit(take(20, P.negativeBigIntegers()),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    @Test
    public void testNaturalBytes() {
        aeq(length(P.naturalBytes()), 128);
        aeqit(take(20, P.naturalBytes()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalShorts() {
        aeq(length(P.naturalShorts()), 32768);
        aeqit(take(20, P.naturalShorts()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalIntegers() {
        aeqit(take(20, P.naturalIntegers()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalLongs() {
        aeqit(take(20, P.naturalLongs()), "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testNaturalBigIntegers() {
        aeqit(take(20, P.naturalBigIntegers()),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]");
    }

    @Test
    public void testBytes() {
        aeq(length(P.bytes()), 256);
        aeqit(take(5, reverse(P.bytes())), "[-128, -127, 127, -126, 126]");
        aeqit(take(20, P.bytes()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testShorts() {
        aeq(length(P.shorts()), 65536);
        aeqit(take(5, reverse(P.shorts())), "[-32768, -32767, 32767, -32766, 32766]");
        aeqit(take(20, P.shorts()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testIntegers() {
        aeqit(take(20, P.integers()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testLongs() {
        aeqit(take(20, P.longs()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
    }

    @Test
    public void testBigIntegers() {
        aeqit(take(20, P.bigIntegers()), "[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10]");
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
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" +
                "\0\1\2\3\4\5\6\7\b\t\n\13\f\r\16\17\20\21\22\23\24\25\26\27\30\31\32\33\34\35\36\37 \177"
        );
    }

    @Test
    public void testCharactersIncreasing() {
        aeq(length(P.charactersIncreasing()), 65536);
        aeq(charsToString(take(256, P.charactersIncreasing())),
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
    public void testPositiveOrdinaryFloatsIncreasing() {
        aeqit(take(20, P.positiveOrdinaryFloatsIncreasing()),
                "[1.4E-45, 2.8E-45, 4.2E-45, 5.6E-45, 7.0E-45, 8.4E-45, 9.8E-45, 1.1E-44, 1.3E-44, 1.4E-44," +
                        " 1.5E-44, 1.7E-44, 1.8E-44, 2.0E-44, 2.1E-44, 2.24E-44, 2.4E-44, 2.5E-44, 2.7E-44, 2.8E-44]");
    }

    @Test
    public void testNegativeOrdinaryFloatsIncreasing() {
        aeqit(take(20, P.negativeOrdinaryFloatsIncreasing()),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                        " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                        " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                        " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testOrdinaryFloatsIncreasing() {
        aeqit(take(20, P.ordinaryFloatsIncreasing()),
                "[-3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38, -3.4028225E38," +
                        " -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38, -3.4028212E38," +
                        " -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38, -3.40282E38," +
                        " -3.4028198E38, -3.4028196E38]");
    }

    @Test
    public void testFloatsIncreasing() {
        aeqit(take(20, P.floatsIncreasing()),
                "[-Infinity, -3.4028235E38, -3.4028233E38, -3.402823E38, -3.4028229E38, -3.4028227E38," +
                        " -3.4028225E38, -3.4028222E38, -3.402822E38, -3.4028218E38, -3.4028216E38, -3.4028214E38," +
                        " -3.4028212E38, -3.402821E38, -3.4028208E38, -3.4028206E38, -3.4028204E38, -3.4028202E38," +
                        " -3.40282E38, -3.4028198E38]");
    }

    @Test
    public void testPositiveOrdinaryFloats() {
        aeqit(take(50, P.positiveOrdinaryFloats()),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                        " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                        " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryFloats() {
        aeqit(take(50, P.negativeOrdinaryFloats()),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                        " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                        " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                        " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryFloats() {
        aeqit(take(50, P.ordinaryFloats()),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                        " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                        " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                        " -48.0, 1.25]");
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
    public void testPositiveOrdinaryDoublesIncreasing() {
        aeqit(take(20, P.positiveOrdinaryDoublesIncreasing()),
                "[4.9E-324, 1.0E-323, 1.5E-323, 2.0E-323, 2.5E-323, 3.0E-323, 3.5E-323, 4.0E-323, 4.4E-323," +
                        " 4.9E-323, 5.4E-323, 5.9E-323, 6.4E-323, 6.9E-323, 7.4E-323, 7.9E-323, 8.4E-323, 8.9E-323," +
                        " 9.4E-323, 1.0E-322]");
    }

    @Test
    public void testNegativeOrdinaryDoublesIncreasing() {
        aeqit(take(20, P.negativeOrdinaryDoublesIncreasing()),
                "[-1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                        " -1.7976931348623151E308, -1.797693134862315E308, -1.7976931348623147E308," +
                        " -1.7976931348623145E308, -1.7976931348623143E308, -1.7976931348623141E308," +
                        " -1.797693134862314E308, -1.7976931348623137E308, -1.7976931348623135E308," +
                        " -1.7976931348623133E308, -1.7976931348623131E308, -1.797693134862313E308," +
                        " -1.7976931348623127E308, -1.7976931348623125E308, -1.7976931348623123E308," +
                        " -1.7976931348623121E308, -1.797693134862312E308]");
    }

    @Test
    public void testOrdinaryDoublesIncreasing() {
        aeqit(take(20, P.ordinaryDoublesIncreasing()),
                "[-1.7976931348623157E308, -1.7976931348623155E308, -1.7976931348623153E308," +
                        " -1.7976931348623151E308, -1.797693134862315E308, -1.7976931348623147E308," +
                        " -1.7976931348623145E308, -1.7976931348623143E308, -1.7976931348623141E308," +
                        " -1.797693134862314E308, -1.7976931348623137E308, -1.7976931348623135E308," +
                        " -1.7976931348623133E308, -1.7976931348623131E308, -1.797693134862313E308," +
                        " -1.7976931348623127E308, -1.7976931348623125E308, -1.7976931348623123E308," +
                        " -1.7976931348623121E308, -1.797693134862312E308]");
    }

    @Test
    public void testDoublesIncreasing() {
        aeqit(take(20, P.doublesIncreasing()),
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
        aeqit(take(50, P.positiveOrdinaryDoubles()),
                "[1.0, 2.0, 3.0, 6.0, 0.5, 4.0, 1.5, 12.0, 5.0, 10.0, 7.0, 14.0, 2.5, 20.0, 3.5, 28.0, 0.25, 8.0," +
                        " 0.75, 24.0, 0.125, 16.0, 0.375, 48.0, 1.25, 40.0, 1.75, 56.0, 0.625, 80.0, 0.875, 112.0, 9.0," +
                        " 18.0, 11.0, 22.0, 4.5, 36.0, 5.5, 44.0, 13.0, 26.0, 15.0, 30.0, 6.5, 52.0, 7.5, 60.0, 2.25, 72.0]");
    }

    @Test
    public void testNegativeOrdinaryDoubles() {
        aeqit(take(50, P.negativeOrdinaryDoubles()),
                "[-1.0, -2.0, -3.0, -6.0, -0.5, -4.0, -1.5, -12.0, -5.0, -10.0, -7.0, -14.0, -2.5, -20.0, -3.5," +
                        " -28.0, -0.25, -8.0, -0.75, -24.0, -0.125, -16.0, -0.375, -48.0, -1.25, -40.0, -1.75, -56.0," +
                        " -0.625, -80.0, -0.875, -112.0, -9.0, -18.0, -11.0, -22.0, -4.5, -36.0, -5.5, -44.0, -13.0, -26.0," +
                        " -15.0, -30.0, -6.5, -52.0, -7.5, -60.0, -2.25, -72.0]");
    }

    @Test
    public void testOrdinaryDoubles() {
        aeqit(take(50, P.ordinaryDoubles()),
                "[0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0, -4.0, 1.5, -1.5, 12.0, -12.0," +
                        " 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0, -20.0, 3.5, -3.5, 28.0, -28.0," +
                        " 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125, 16.0, -16.0, 0.375, -0.375, 48.0," +
                        " -48.0, 1.25]");
    }

    @Test
    public void testDoubles() {
        aeqit(take(50, P.doubles()),
                "[NaN, Infinity, -Infinity, 0.0, -0.0, 1.0, -1.0, 2.0, -2.0, 3.0, -3.0, 6.0, -6.0, 0.5, -0.5, 4.0," +
                        " -4.0, 1.5, -1.5, 12.0, -12.0, 5.0, -5.0, 10.0, -10.0, 7.0, -7.0, 14.0, -14.0, 2.5, -2.5, 20.0," +
                        " -20.0, 3.5, -3.5, 28.0, -28.0, 0.25, -0.25, 8.0, -8.0, 0.75, -0.75, 24.0, -24.0, 0.125, -0.125," +
                        " 16.0, -16.0, 0.375]");
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
}
