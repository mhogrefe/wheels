package mho.wheels.misc;

import org.junit.Test;

import java.math.BigInteger;

import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersTest {
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
                findLongIn("abcd" + BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.ONE) + "xyz").get(),
                "(2147483648, 4)"
        );
        aeq(
                findLongIn("abcd" + BigInteger.valueOf(Integer.MIN_VALUE).subtract(BigInteger.ONE) + "xyz").get(),
                "(-2147483649, 4)"
        );
        assertFalse(findLongIn("").isPresent());
        assertFalse(findLongIn("hello").isPresent());
        assertFalse(findLongIn("vdfsvfbf").isPresent());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
