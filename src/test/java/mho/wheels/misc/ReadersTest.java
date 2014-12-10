package mho.wheels.misc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersTest {
    @Test
    public void testConstants() {
        aeq(MAX_POSITIVE_BYTE_LENGTH, 3);
        aeq(MAX_POSITIVE_SHORT_LENGTH, 5);
        aeq(MAX_POSITIVE_INTEGER_LENGTH, 10);
        aeq(MAX_POSITIVE_LONG_LENGTH, 19);
    }

    @Test
    public void testReadBoolean() {
        aeq(readBoolean("false").get(), "false");
        aeq(readBoolean("true").get(), "true");
        assertFalse(readBoolean(" true").isPresent());
        assertFalse(readBoolean("TRUE").isPresent());
        assertFalse(readBoolean("true ").isPresent());
        assertFalse(readBoolean("").isPresent());
        assertFalse(readBoolean("dsfsdfgd").isPresent());
    }

    @Test
    public void testFindBooleanIn() {
        aeq(findBooleanIn("true").get(), "(true, 0)");
        aeq(findBooleanIn("false").get(), "(false, 0)");
        aeq(findBooleanIn("xxtruefalsexx").get(), "(true, 2)");
        aeq(findBooleanIn("xxfalsetruexx").get(), "(false, 2)");
        assertFalse(findOrderingIn("hello").isPresent());
        assertFalse(findOrderingIn("").isPresent());
    }

    public void testReadOrdering() {
        aeq(readOrdering("LT").get(), "LT");
        aeq(readOrdering("EQ").get(), "EQ");
        aeq(readOrdering("GT").get(), "GT");
        assertFalse(readOrdering(" LT").isPresent());
        assertFalse(readOrdering("eq").isPresent());
        assertFalse(readOrdering("gt ").isPresent());
        assertFalse(readOrdering("").isPresent());
        assertFalse(readOrdering("dsfsdfgd").isPresent());
    }

    @Test
    public void testFindOrderingIn() {
        aeq(findOrderingIn("EQ").get(), "(EQ, 0)");
        aeq(findOrderingIn("LT").get(), "(LT, 0)");
        aeq(findOrderingIn("BELT").get(), "(LT, 2)");
        aeq(findOrderingIn("EGGTOWER").get(), "(GT, 2)");
        assertFalse(findOrderingIn("hello").isPresent());
        assertFalse(findOrderingIn("").isPresent());
    }

    public void testReadRoundingMode() {
        aeq(readRoundingMode("UP").get(), "UP");
        aeq(readRoundingMode("UNNECESSARY").get(), "UNNECESSARY");
        aeq(readRoundingMode("HALF_EVEN").get(), "HALF_EVEN");
        assertFalse(readRoundingMode(" DOWN").isPresent());
        assertFalse(readRoundingMode("HALF-EVEN").isPresent());
        assertFalse(readRoundingMode("FLOOR ").isPresent());
        assertFalse(readRoundingMode("").isPresent());
        assertFalse(readRoundingMode("dsfsdfgd").isPresent());
    }

    @Test
    public void testFindRoundingModeIn() {
        aeq(findRoundingModeIn("HALF_UP").get(), "(HALF_UP, 0)");
        aeq(findRoundingModeIn("CEILING").get(), "(CEILING, 0)");
        aeq(findRoundingModeIn("UPSIDE-DOWN").get(), "(UP, 0)");
        aeq(findRoundingModeIn("JLNUIDOWNJLNILN").get(), "(DOWN, 5)");
        assertFalse(findRoundingModeIn("hello").isPresent());
        assertFalse(findRoundingModeIn("").isPresent());
    }

    @Test
    public void testReadBigInteger() {
        aeq(readBigInteger("0").get(), "0");
        aeq(readBigInteger("5").get(), "5");
        aeq(readBigInteger("314159265358").get(), "314159265358");
        aeq(readBigInteger("-314159265358").get(), "-314159265358");
        assertFalse(readBigInteger(" 1").isPresent());
        assertFalse(readBigInteger("00").isPresent());
        assertFalse(readBigInteger("-0").isPresent());
        assertFalse(readBigInteger("0xff").isPresent());
        assertFalse(readBigInteger("0xff").isPresent());
        assertFalse(readBigInteger("2 ").isPresent());
        assertFalse(readBigInteger("--1").isPresent());
        assertFalse(readBigInteger("1-2").isPresent());
        assertFalse(readBigInteger("+4").isPresent());
    }

    @Test
    public void testFindBigIntegerIn() {
        aeq(findBigIntegerIn("abcd1234xyz").get(), "(1234, 4)");
        aeq(findBigIntegerIn("0123").get(), "(0, 0)");
        aeq(findBigIntegerIn("a-23").get(), "(-23, 1)");
        aeq(findBigIntegerIn("---34--4").get(), "(-34, 2)");
        aeq(findBigIntegerIn(" 20.1 ").get(), "(20, 1)");
        assertFalse(findBigIntegerIn("").isPresent());
        assertFalse(findBigIntegerIn("hello").isPresent());
        assertFalse(findBigIntegerIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadByte() {
        aeq(readByte("0").get(), "0");
        aeq(readByte("5").get(), "5");
        aeq(readByte("-100").get(), "-100");
        aeq(readByte(Integer.toString(Byte.MAX_VALUE)).get(), "127");
        aeq(readByte(Integer.toString(Byte.MIN_VALUE)).get(), "-128");
        assertFalse(readByte(Integer.toString(Byte.MAX_VALUE + 1)).isPresent());
        assertFalse(readByte(Integer.toString(Byte.MIN_VALUE - 1)).isPresent());
        assertFalse(readByte(" 1").isPresent());
        assertFalse(readByte("00").isPresent());
        assertFalse(readByte("-0").isPresent());
        assertFalse(readByte("0xff").isPresent());
        assertFalse(readByte("0xff").isPresent());
        assertFalse(readByte("2 ").isPresent());
        assertFalse(readByte("--1").isPresent());
        assertFalse(readByte("1-2").isPresent());
        assertFalse(readByte("+4").isPresent());
    }

    @Test
    public void testFindByteIn() {
        aeq(findByteIn("abcd1234xyz").get(), "(123, 4)");
        aeq(findByteIn("abcd8234xyz").get(), "(82, 4)");
        aeq(findByteIn("0123").get(), "(0, 0)");
        aeq(findByteIn("a-23").get(), "(-23, 1)");
        aeq(findByteIn("---34--4").get(), "(-34, 2)");
        aeq(findByteIn(" 20.1 ").get(), "(20, 1)");
        aeq(findByteIn("abcd" + Byte.MAX_VALUE + "xyz").get(), "(127, 4)");
        aeq(findByteIn("abcd" + Byte.MIN_VALUE + "xyz").get(), "(-128, 4)");
        aeq(findByteIn("abcd" + (Byte.MAX_VALUE + 1) + "xyz").get(), "(12, 4)");
        aeq(findByteIn("abcd" + (Byte.MIN_VALUE - 1) + "xyz").get(), "(-12, 4)");
        assertFalse(findByteIn("").isPresent());
        assertFalse(findByteIn("hello").isPresent());
        assertFalse(findByteIn("vdfsvfbf").isPresent());
    }

    @Test
     public void testReadShort() {
        aeq(readShort("0").get(), "0");
        aeq(readShort("5").get(), "5");
        aeq(readShort("-100").get(), "-100");
        aeq(readShort(Integer.toString(Short.MAX_VALUE)).get(), "32767");
        aeq(readShort(Integer.toString(Short.MIN_VALUE)).get(), "-32768");
        assertFalse(readShort(Integer.toString(Short.MAX_VALUE + 1)).isPresent());
        assertFalse(readShort(Integer.toString(Short.MIN_VALUE - 1)).isPresent());
        assertFalse(readShort(" 1").isPresent());
        assertFalse(readShort("00").isPresent());
        assertFalse(readShort("-0").isPresent());
        assertFalse(readShort("0xff").isPresent());
        assertFalse(readShort("0xff").isPresent());
        assertFalse(readShort("2 ").isPresent());
        assertFalse(readShort("--1").isPresent());
        assertFalse(readShort("1-2").isPresent());
        assertFalse(readShort("+4").isPresent());
    }

    @Test
    public void testFindShortIn() {
        aeq(findShortIn("abcd1234xyz").get(), "(1234, 4)");
        aeq(findShortIn("abcd8234xyz").get(), "(8234, 4)");
        aeq(findShortIn("0123").get(), "(0, 0)");
        aeq(findShortIn("a-23").get(), "(-23, 1)");
        aeq(findShortIn("---34--4").get(), "(-34, 2)");
        aeq(findShortIn(" 20.1 ").get(), "(20, 1)");
        aeq(findShortIn("abcd" + Short.MAX_VALUE + "xyz").get(), "(32767, 4)");
        aeq(findShortIn("abcd" + Short.MIN_VALUE + "xyz").get(), "(-32768, 4)");
        aeq(findShortIn("abcd" + (Short.MAX_VALUE + 1) + "xyz").get(), "(3276, 4)");
        aeq(findShortIn("abcd" + (Short.MIN_VALUE - 1) + "xyz").get(), "(-3276, 4)");
        assertFalse(findShortIn("").isPresent());
        assertFalse(findShortIn("hello").isPresent());
        assertFalse(findShortIn("vdfsvfbf").isPresent());
    }

    @Test
     public void testReadInteger() {
        aeq(readInteger("0").get(), "0");
        aeq(readInteger("5").get(), "5");
        aeq(readInteger("-100").get(), "-100");
        aeq(readInteger(Integer.toString(Integer.MAX_VALUE)).get(), "2147483647");
        aeq(readInteger(Integer.toString(Integer.MIN_VALUE)).get(), "-2147483648");
        assertFalse(readInteger(Long.toString((long) Integer.MAX_VALUE + 1)).isPresent());
        assertFalse(readInteger(Long.toString((long) Integer.MIN_VALUE - 1)).isPresent());
        assertFalse(readInteger(" 1").isPresent());
        assertFalse(readInteger("00").isPresent());
        assertFalse(readInteger("-0").isPresent());
        assertFalse(readInteger("0xff").isPresent());
        assertFalse(readInteger("0xff").isPresent());
        assertFalse(readInteger("2 ").isPresent());
        assertFalse(readInteger("--1").isPresent());
        assertFalse(readInteger("1-2").isPresent());
        assertFalse(readInteger("+4").isPresent());
    }

    @Test
    public void testFindIntegerIn() {
        aeq(findIntegerIn("abcd1234xyz").get(), "(1234, 4)");
        aeq(findIntegerIn("abcd8234xyz").get(), "(8234, 4)");
        aeq(findIntegerIn("0123").get(), "(0, 0)");
        aeq(findIntegerIn("a-23").get(), "(-23, 1)");
        aeq(findIntegerIn("---34--4").get(), "(-34, 2)");
        aeq(findIntegerIn(" 20.1 ").get(), "(20, 1)");
        aeq(findIntegerIn("abcd" + Integer.MAX_VALUE + "xyz").get(), "(2147483647, 4)");
        aeq(findIntegerIn("abcd" + Integer.MIN_VALUE + "xyz").get(), "(-2147483648, 4)");
        aeq(findIntegerIn("abcd" + ((long) Integer.MAX_VALUE + 1) + "xyz").get(), "(214748364, 4)");
        aeq(findIntegerIn("abcd" + ((long) Integer.MIN_VALUE - 1) + "xyz").get(), "(-214748364, 4)");
        assertFalse(findIntegerIn("").isPresent());
        assertFalse(findIntegerIn("hello").isPresent());
        assertFalse(findIntegerIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadLong() {
        aeq(readLong("0").get(), "0");
        aeq(readLong("5").get(), "5");
        aeq(readLong("-100").get(), "-100");
        aeq(readLong(Long.toString(Long.MAX_VALUE)).get(), "9223372036854775807");
        aeq(readLong(Long.toString(Long.MIN_VALUE)).get(), "-9223372036854775808");
        assertFalse(readLong(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE).toString()).isPresent());
        assertFalse(readLong(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE).toString()).isPresent());
        assertFalse(readLong(" 1").isPresent());
        assertFalse(readLong("00").isPresent());
        assertFalse(readLong("-0").isPresent());
        assertFalse(readLong("0xff").isPresent());
        assertFalse(readLong("0xff").isPresent());
        assertFalse(readLong("2 ").isPresent());
        assertFalse(readLong("--1").isPresent());
        assertFalse(readLong("1-2").isPresent());
        assertFalse(readLong("+4").isPresent());
    }

    @Test
    public void testFindLongIn() {
        aeq(findLongIn("abcd1234xyz").get(), "(1234, 4)");
        aeq(findLongIn("abcd8234xyz").get(), "(8234, 4)");
        aeq(findLongIn("0123").get(), "(0, 0)");
        aeq(findLongIn("a-23").get(), "(-23, 1)");
        aeq(findLongIn("---34--4").get(), "(-34, 2)");
        aeq(findLongIn(" 20.1 ").get(), "(20, 1)");
        aeq(findLongIn("abcd" + Long.MAX_VALUE + "xyz").get(), "(9223372036854775807, 4)");
        aeq(findLongIn("abcd" + Long.MIN_VALUE + "xyz").get(), "(-9223372036854775808, 4)");
        aeq(
                findLongIn("abcd" + BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE) + "xyz").get(),
                "(922337203685477580, 4)"
        );
        aeq(
                findLongIn("abcd" + BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE) + "xyz").get(),
                "(-922337203685477580, 4)"
        );
        assertFalse(findLongIn("").isPresent());
        assertFalse(findLongIn("hello").isPresent());
        assertFalse(findLongIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadFloat() {
        aeq(readFloat("0.0").get(), "0.0");
        aeq(readFloat("-0.0").get(), "-0.0");
        aeq(readFloat("5.0").get(), "5.0");
        aeq(readFloat("-100.0").get(), "-100.0");
        aeq(readFloat("1.0E10").get(), "1.0E10");
        aeq(readFloat("1.0E-10").get(), "1.0E-10");
        aeq(readFloat("1.234").get(), "1.234");
        aeq(readFloat("1.111111").get(), "1.111111");
        aeq(readFloat("NaN").get(), "NaN");
        aeq(readFloat("Infinity").get(), "Infinity");
        aeq(readFloat("-Infinity").get(), "-Infinity");
        assertFalse(readFloat("1.1111111").isPresent());
        assertFalse(readFloat("1.0e10").isPresent());
        assertFalse(readFloat("1.0e-10").isPresent());
        assertFalse(readFloat(".").isPresent());
        assertFalse(readFloat("0.").isPresent());
        assertFalse(readFloat(".0").isPresent());
        assertFalse(readFloat(" 1.0").isPresent());
        assertFalse(readFloat("--1.0").isPresent());
    }

    @Test
    public void testFindFloatIn() {
        aeq(findFloatIn("abcd1234.0xyz").get(), "(1234.0, 4)");
        aeq(findFloatIn("abcd823.4xyz").get(), "(823.4, 4)");
        aeq(findFloatIn("0.0.123").get(), "(0.0, 0)");
        aeq(findFloatIn("a-2.3E8z").get(), "(-2.3E8, 1)");
        aeq(findFloatIn("a-2.3E10z").get(), "(-2.3, 1)");
        aeq(findFloatIn("---34.4-").get(), "(-34.4, 2)");
        aeq(findFloatIn(" 20.1 ").get(), "(20.1, 1)");
        aeq(findFloatIn("AnAnANaNAN").get(), "(NaN, 5)");
        aeq(findFloatIn("1.1111111111111111111").get(), "(1.111111, 0)");
        assertFalse(findFloatIn("").isPresent());
        assertFalse(findFloatIn("3").isPresent());
        assertFalse(findFloatIn("hello").isPresent());
        assertFalse(findFloatIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadDouble() {
        aeq(readDouble("0.0").get(), "0.0");
        aeq(readDouble("-0.0").get(), "-0.0");
        aeq(readDouble("5.0").get(), "5.0");
        aeq(readDouble("-100.0").get(), "-100.0");
        aeq(readDouble("1.0E10").get(), "1.0E10");
        aeq(readDouble("1.0E-10").get(), "1.0E-10");
        aeq(readDouble("1.234").get(), "1.234");
        aeq(readDouble("1.111111111111111").get(), "1.111111111111111");
        aeq(readDouble("NaN").get(), "NaN");
        aeq(readDouble("Infinity").get(), "Infinity");
        aeq(readDouble("-Infinity").get(), "-Infinity");
        assertFalse(readDouble("1.1111111111111111").isPresent());
        assertFalse(readDouble("1.0e10").isPresent());
        assertFalse(readDouble("1.0e-10").isPresent());
        assertFalse(readDouble(".").isPresent());
        assertFalse(readDouble("0.").isPresent());
        assertFalse(readDouble(".0").isPresent());
        assertFalse(readDouble(" 1.0").isPresent());
        assertFalse(readDouble("--1.0").isPresent());
    }

    @Test
    public void testFindDoubleIn() {
        aeq(findDoubleIn("abcd1234.0xyz").get(), "(1234.0, 4)");
        aeq(findDoubleIn("abcd823.4xyz").get(), "(823.4, 4)");
        aeq(findDoubleIn("0.0.123").get(), "(0.0, 0)");
        aeq(findDoubleIn("a-2.3E8z").get(), "(-2.3E8, 1)");
        aeq(findDoubleIn("a-2.3E1000z").get(), "(-2.3E100, 1)");
        aeq(findDoubleIn("---34.4-").get(), "(-34.4, 2)");
        aeq(findDoubleIn(" 20.1 ").get(), "(20.1, 1)");
        aeq(findDoubleIn("AnAnANaNAN").get(), "(NaN, 5)");
        aeq(findDoubleIn("1.1111111111111111111").get(), "(1.111111111111111, 0)");
        assertFalse(findDoubleIn("").isPresent());
        assertFalse(findDoubleIn("3").isPresent());
        assertFalse(findDoubleIn("hello").isPresent());
        assertFalse(findDoubleIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadBigDecimal() {
        aeq(readBigDecimal("0.0").get(), "0.0");
        aeq(readBigDecimal("5.0").get(), "5.0");
        aeq(readBigDecimal("-100.0").get(), "-100.0");
        aeq(readBigDecimal("1.0E+10").get(), "1.0E+10");
        aeq(readBigDecimal("1.0E-10").get(), "1.0E-10");
        aeq(readBigDecimal("1.234").get(), "1.234");
        aeq(readBigDecimal("1.111111111111111").get(), "1.111111111111111");
        assertFalse(readBigDecimal("1.0e10").isPresent());
        assertFalse(readBigDecimal("1.0e-10").isPresent());
        assertFalse(readBigDecimal(".").isPresent());
        assertFalse(readBigDecimal("0.").isPresent());
        assertFalse(readBigDecimal(".0").isPresent());
        assertFalse(readBigDecimal(" 1.0").isPresent());
        assertFalse(readBigDecimal("--1.0").isPresent());
        assertFalse(readBigDecimal("-0.0").isPresent());
        assertFalse(readBigDecimal("NaN").isPresent());
        assertFalse(readBigDecimal("Infinity").isPresent());
    }

    @Test
    public void testFindBigDecimalIn() {
        aeq(findBigDecimalIn("abcd1234.0xyz").get(), "(1234.0, 4)");
        aeq(findBigDecimalIn("abcd823.4xyz").get(), "(823.4, 4)");
        aeq(findBigDecimalIn("3").get(), "(3, 0)");
        aeq(findBigDecimalIn("00").get(), "(0, 0)");
        aeq(findBigDecimalIn("0.0.123").get(), "(0.0, 0)");
        aeq(findBigDecimalIn("a-2.3E+8z").get(), "(-2.3E+8, 1)");
        aeq(findBigDecimalIn("a-2.3E+1000z").get(), "(-2.3E+1000, 1)");
        aeq(findBigDecimalIn("---34.4-").get(), "(-34.4, 2)");
        aeq(findBigDecimalIn(" 20.1 ").get(), "(20.1, 1)");
        aeq(findBigDecimalIn("1.1111111111111111111").get(), "(1.1111111111111111111, 0)");
        assertFalse(findBigDecimalIn("").isPresent());
        assertFalse(findBigDecimalIn("hello").isPresent());
        assertFalse(findBigDecimalIn("vdfsvfbf").isPresent());
    }

    @Test
    public void testReadCharacter() {
        aeq(readCharacter("a").get(), "a");
        aeq(readCharacter("ø").get(), "ø");
        assertFalse(readCharacter("hi").isPresent());
        assertFalse(readCharacter("").isPresent());
    }

    @Test
    public void testFindCharacterIn() {
        aeq(findCharacterIn("Hello").get(), "(H, 0)");
        aeq(findCharacterIn("ø").get(), "(ø, 0)");
        assertFalse(findCharacterIn("").isPresent());
    }

    @Test
    public void testReadString() {
        aeq(readString("Hello").get(), "Hello");
        aeq(readString("ø").get(), "ø");
        aeq(readString("").get(), "");
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
