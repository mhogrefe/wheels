package mho.wheels.misc;

import org.junit.Test;

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

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}