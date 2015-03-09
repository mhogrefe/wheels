package mho.wheels.misc;

import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    private static class WordyInteger {
        private final int i;

        public WordyInteger(int i) {
            this.i = i;
        }

        public @NotNull String toString() {
            switch (i) {
                case 1: return "one";
                case 2: return "two";
                case 3: return "three";
                default: return "many";
            }
        }
    }

    private static class WordyIntegerWithNullToString {
        private final int i;

        public WordyIntegerWithNullToString(int i) {
            this.i = i;
        }

        public @Nullable String toString() {
            switch (i) {
                case 1: return "one";
                case 2: return "two";
                case 3: return "three";
                default: return null;
            }
        }
    }

    @Test
    public void testGenericRead() {
        Function<String, Optional<WordyInteger>> f = genericRead(s -> {
            if (s.equals("one")) return new WordyInteger(1);
            if (s.equals("two")) return new WordyInteger(2);
            if (s.equals("three")) return new WordyInteger(3);
            throw new ArithmeticException();
        });
        aeq(f.apply("one").get(), "one");
        aeq(f.apply("two").get(), "two");
        aeq(f.apply("three").get(), "three");
        assertFalse(f.apply("four").isPresent());
        assertFalse(f.apply("").isPresent());
        assertFalse(f.apply(" ").isPresent());
        assertFalse(f.apply("null").isPresent());

        f = genericRead(s -> {
            if (s.equals("one")) return new WordyInteger(1);
            if (s.equals("two")) return new WordyInteger(2);
            if (s.equals("three")) return new WordyInteger(3);
            return new WordyInteger(10);
        });
        aeq(f.apply("one").get(), "one");
        aeq(f.apply("two").get(), "two");
        aeq(f.apply("three").get(), "three");
        aeq(f.apply("many").get(), "many");
        assertFalse(f.apply("four").isPresent());
        assertFalse(f.apply("").isPresent());
        assertFalse(f.apply(" ").isPresent());
        assertFalse(f.apply("null").isPresent());

        try {
            genericRead(s -> null).apply("four");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testGenericFindIn_Iterable_T_String() {
        aeq(genericFindIn(Arrays.asList(1, 12, 3)).apply("there are 3 numbers").get(), "(3, 10)");
        aeq(genericFindIn(Arrays.asList(1, 3, 3)).apply("there are 3 numbers").get(), "(3, 10)");
        aeq(genericFindIn(Arrays.asList(1, 12, 3)).apply("there are 12 numbers").get(), "(12, 10)");
        aeq(genericFindIn(Arrays.asList(1, 12, 3)).apply("there is 1 number").get(), "(1, 9)");
        assertFalse(genericFindIn(Arrays.asList(1, 12, 3)).apply("there are no numbers").isPresent());
        assertFalse(genericFindIn(Arrays.asList(1, 12, 3)).apply("").isPresent());
        try {
            genericFindIn(Arrays.asList(1, null, 3)).apply("there are 3 numbers");
            fail();
        } catch (NullPointerException ignored) {}
        genericFindIn(Arrays.asList(1, 1, 3)).apply("there are 3 numbers");

        List<WordyIntegerWithNullToString> xs = new ArrayList<>();
        xs.add(new WordyIntegerWithNullToString(1));
        xs.add(new WordyIntegerWithNullToString(2));
        xs.add(new WordyIntegerWithNullToString(3));
        xs.add(new WordyIntegerWithNullToString(4));
        try {
            genericFindIn(xs).apply("there are 3 numbers");
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testGenericFindIn_Function_T_Optional_T_String_String() {
        Function<String, Optional<Pair<WordyInteger, Integer>>> f = genericFindIn(s -> {
            if (s.equals("one")) return Optional.of(new WordyInteger(1));
            if (s.equals("two")) return Optional.of(new WordyInteger(2));
            if (s.equals("three")) return Optional.of(new WordyInteger(3));
            if (s.equals("one hundred")) return Optional.of(new WordyInteger(100));
            return Optional.empty();
        }, " dehnortuw");
        aeq(f.apply("vdfsmvlefqvehthreefdsz"), "Optional[(three, 13)]");
        aeq(f.apply("vdfsmvldfonevfdsz"), "Optional[(one, 9)]");
        aeq(f.apply("vdfsmvldfone hundredvfdsz"), "Optional[(many, 9)]");
        aeq(f.apply("vdfsmvldfvfdsz"), "Optional.empty");

        //exclude "one hundred" chars
        f = genericFindIn(s -> {
            if (s.equals("one")) return Optional.of(new WordyInteger(1));
            if (s.equals("two")) return Optional.of(new WordyInteger(2));
            if (s.equals("three")) return Optional.of(new WordyInteger(3));
            if (s.equals("one hundred")) return Optional.of(new WordyInteger(100));
            return Optional.empty();
        }, "ehnortw");
        aeq(f.apply("vdfsmvldfone hundredvfdsz"), "Optional[(one, 9)]");

        try {
            genericFindIn(s -> null, " dehnortuw").apply("vdfsmvlefqvehthreefdsz");
            fail();
        } catch (NullPointerException ignored) {}
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

    @Test
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

    @Test
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

    @Test
    public void testFindStringIn() {
        aeq(findStringIn("Hello"), "Optional[(Hello, 0)]");
        aeq(findStringIn("ø"), "Optional[(ø, 0)]");
        aeq(findStringIn(""), "Optional[(, 0)]");
    }

    @Test
    public void testReadWithNulls() {
        aeq(readWithNulls(Readers::readInteger, "23").get(), "23");
        aeq(readWithNulls(Readers::readInteger, "-500").get(), "-500");
        assertNull(readWithNulls(Readers::readInteger, "null").get());
        aeq(readWithNulls(Readers::readString, "hello").get(), "hello");
        aeq(readWithNulls(Readers::readString, "bye").get(), "bye");
        aeq(readWithNulls(Readers::readString, "nullification").get(), "nullification");
        aeq(readWithNulls(Readers::readString, "").get(), "");
        assertNull(readWithNulls(Readers::readString, "null").get());
        assertFalse(readWithNulls(Readers::readInteger, "annull").isPresent());
        assertFalse(readWithNulls(Readers::readInteger, "--").isPresent());
        assertFalse(readWithNulls(Readers::readInteger, "").isPresent());
        try {
            readWithNulls(s -> null, "hello");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFindInWithNulls() {
        aeq(findInWithNulls(Readers::findIntegerIn, "xyz123xyz").get(), "(123, 3)");
        aeq(findInWithNulls(Readers::findIntegerIn, "123null").get(), "(123, 0)");
        assertNull(findInWithNulls(Readers::findIntegerIn, "null123").get().a);
        aeq(findInWithNulls(Readers::findIntegerIn, "--500").get(), "(-500, 1)");
        assertNull(findInWithNulls(Readers::findIntegerIn, "thisisnull").get().a);
        aeq(findInWithNulls(Readers::findBooleanIn, "falsenull").get(), "(false, 0)");
        assertNull(findInWithNulls(Readers::findBooleanIn, "nullfalse").get().a);
        assertFalse(findInWithNulls(Readers::findIntegerIn, "xyz").isPresent());
        assertFalse(findInWithNulls(Readers::findIntegerIn, "--").isPresent());
        assertFalse(findInWithNulls(Readers::findIntegerIn, "").isPresent());
        try {
            findInWithNulls(s -> null, "hello");
        } catch (NullPointerException ignored) {}
        try {
            findInWithNulls(s -> Optional.of(new Pair<>('a', null)), "hello");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findInWithNulls(s -> Optional.of(new Pair<>(null, 3)), "hello");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findInWithNulls(s -> Optional.of(new Pair<>(null, null)), "hello");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findInWithNulls(s -> Optional.of(new Pair<>('a', -1)), "hello");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testReadOptional() {
        aeq(readOptional(Readers::readInteger, "Optional[23]").get(), "Optional[23]");
        aeq(readOptional(Readers::readInteger, "Optional[0]").get(), "Optional[0]");
        aeq(readOptional(Readers::readInteger, "Optional[-5]").get(), "Optional[-5]");
        aeq(readOptional(Readers::readInteger, "Optional.empty").get(), "Optional.empty");
        aeq(readOptional(Readers::readBoolean, "Optional[false]").get(), "Optional[false]");
        aeq(readOptional(Readers::readBoolean, "Optional[true]").get(), "Optional[true]");
        aeq(readOptional(Readers::readBoolean, "Optional.empty").get(), "Optional.empty");
        assertFalse(readOptional(Readers::readInteger, "Optional[10000000000000000000]").isPresent());
        assertFalse(readOptional(Readers::readInteger, "Optional[xyz]").isPresent());
        assertFalse(readOptional(Readers::readInteger, "Optional[null]").isPresent());
        assertFalse(readOptional(Readers::readInteger, "Optional[10").isPresent());
        assertFalse(readOptional(Readers::readInteger, "Optional").isPresent());
        assertFalse(readOptional(Readers::readInteger, "xyz").isPresent());
        assertFalse(readOptional(Readers::readInteger, "").isPresent());
        assertFalse(readOptional(Readers::readBoolean, "Optional[12]").isPresent());
        try {
            readOptional(s -> null, "Optional[hello]");
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testFindOptionalIn() {
        aeq(findOptionalIn(Readers::findIntegerIn, "xyzOptional[23]xyz"), "Optional[(Optional[23], 3)]");
        aeq(findOptionalIn(Readers::findIntegerIn, "xyzOptional[0]xyz"), "Optional[(Optional[0], 3)]");
        aeq(findOptionalIn(Readers::findIntegerIn, "xyzOptional[-5]xyz"), "Optional[(Optional[-5], 3)]");
        aeq(findOptionalIn(Readers::findIntegerIn, "Optional[]Optional[-5]xyz"), "Optional[(Optional[-5], 2)]");
        aeq(
                findOptionalIn(Readers::findIntegerIn, "vdsfvOptional[1000000000000000000]Optional[-5]xyz"),
                "Optional[(Optional[-5], 10)]"
        );
        aeq(findOptionalIn(Readers::findIntegerIn, "Optional[3]Optional[-5]xyz"), "Optional[(Optional[3], 0)]");
        aeq(findOptionalIn(Readers::findIntegerIn, "xyzOptional.emptyxyz"), "Optional[(Optional.empty, 3)]");
        assertFalse(findOptionalIn(Readers::findIntegerIn, "xyz").isPresent());
        assertFalse(findOptionalIn(Readers::findIntegerIn, "").isPresent());
        assertFalse(findOptionalIn(Readers::findIntegerIn, "vdsfvOptional[1000000000000000000]xyz").isPresent());
        assertFalse(findOptionalIn(Readers::findIntegerIn, "vdsfvOptional[null]xyz").isPresent());
        assertFalse(findOptionalIn(Readers::findIntegerIn, "vdsfvOptinal[3]xyz").isPresent());
        try {
            findOptionalIn(s -> null, "Optional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findOptionalIn(s -> Optional.of(new Pair<>('a', null)), "Optional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findOptionalIn(s -> Optional.of(new Pair<>(null, 3)), "Optional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findOptionalIn(s -> Optional.of(new Pair<>(null, null)), "Optional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findOptionalIn(s -> Optional.of(new Pair<>('a', -1)), "Optional[hello]");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testReadNullableOptional() {
        Function<String, NullableOptional<Integer>> fi = s -> readWithNulls(Readers::readInteger, s);
        Function<String, NullableOptional<Boolean>> fb = s -> readWithNulls(Readers::readBoolean, s);
        aeq(readNullableOptional(fi, "NullableOptional[23]").get(), "NullableOptional[23]");
        aeq(readNullableOptional(fi, "NullableOptional[0]").get(), "NullableOptional[0]");
        aeq(readNullableOptional(fi, "NullableOptional[-5]").get(), "NullableOptional[-5]");
        aeq(readNullableOptional(fi, "NullableOptional[null]").get(), "NullableOptional[null]");
        aeq(readNullableOptional(fi, "NullableOptional.empty").get(), "NullableOptional.empty");
        aeq(readNullableOptional(fb, "NullableOptional[false]").get(), "NullableOptional[false]");
        aeq(readNullableOptional(fb, "NullableOptional[true]").get(), "NullableOptional[true]");
        aeq(readNullableOptional(fb, "NullableOptional[null]").get(), "NullableOptional[null]");
        aeq(readNullableOptional(fb, "NullableOptional.empty").get(), "NullableOptional.empty");
        assertFalse(readNullableOptional(fi, "Optional[23]").isPresent());
        assertFalse(readNullableOptional(fi, "NullableOptional[10000000000000000000]").isPresent());
        assertFalse(readNullableOptional(fi, "NullableOptional[xyz]").isPresent());
        assertFalse(readNullableOptional(fi, "NullableOptional[10").isPresent());
        assertFalse(readNullableOptional(fi, "NullableOptional").isPresent());
        assertFalse(readNullableOptional(fi, "xyz").isPresent());
        assertFalse(readNullableOptional(fi, "").isPresent());
        assertFalse(readNullableOptional(fb, "NullableOptional[12]").isPresent());
        try {
            readNullableOptional(s -> null, "NullableOptional[hello]");
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testFindNullableOptionalIn() {
        Function<String, Optional<Pair<Integer, Integer>>> fi = s -> findInWithNulls(Readers::findIntegerIn, s);
        aeq(
                findNullableOptionalIn(fi, "xyzNullableOptional[23]xyz"),
                "Optional[(NullableOptional[23], 3)]"
        );
        aeq(findNullableOptionalIn(fi, "xyzNullableOptional[0]xyz"), "Optional[(NullableOptional[0], 3)]");
        aeq(findNullableOptionalIn(fi, "xyzNullableOptional[-5]xyz"), "Optional[(NullableOptional[-5], 3)]");
        aeq(findNullableOptionalIn(fi, "xyzNullableOptional[null]xyz"), "Optional[(NullableOptional[null], 3)]");
        aeq(
                findNullableOptionalIn(fi, "NullableOptional[]NullableOptional[-5]xyz"),
                "Optional[(NullableOptional[-5], 2)]"
        );
        aeq(
                findNullableOptionalIn(
                        Readers::findIntegerIn,
                        "vdsfvNullableOptional[1000000000000000000]NullableOptional[-5]xyz"
                ),
                "Optional[(NullableOptional[-5], 10)]"
        );
        aeq(
                findNullableOptionalIn(Readers::findIntegerIn, "NullableOptional[3]NullableOptional[-5]xyz"),
                "Optional[(NullableOptional[3], 0)]"
        );
        aeq(
                findNullableOptionalIn(Readers::findIntegerIn, "xyzNullableOptional.emptyxyz"),
                "Optional[(NullableOptional.empty, 3)]"
        );
        assertFalse(findNullableOptionalIn(Readers::findIntegerIn, "xyz").isPresent());
        assertFalse(findNullableOptionalIn(Readers::findIntegerIn, "").isPresent());
        assertFalse(findNullableOptionalIn(Readers::findIntegerIn, "vdsfvOptional[23]xyz").isPresent());
        assertFalse(
                findNullableOptionalIn(Readers::findIntegerIn, "vdsfvNullableOptional[1000000000000000000]xyz")
                        .isPresent()
        );
        assertFalse(findNullableOptionalIn(Readers::findIntegerIn, "vdsfvNullableOptional[null]xyz").isPresent());
        assertFalse(findNullableOptionalIn(Readers::findIntegerIn, "vdsfvNullableOptinal[3]xyz").isPresent());
        try {
            findNullableOptionalIn(s -> null, "NullableOptional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findNullableOptionalIn(s -> Optional.of(new Pair<>('a', null)), "NullableOptional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findNullableOptionalIn(s -> Optional.of(new Pair<>(null, null)), "NullableOptional[hello]");
            fail();
        } catch (NullPointerException ignored) {}
        try {
            findNullableOptionalIn(s -> Optional.of(new Pair<>('a', -1)), "NullableOptional[hello]");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testReadList() {
        aeq(readList(Readers::readInteger, "[]").get(), "[]");
        aeq(readList(Readers::readInteger, "[1]").get(), "[1]");
        aeq(readList(Readers::readInteger, "[1, 2, -3]").get(), "[1, 2, -3]");
        assertFalse(readList(Readers::readInteger, "[1000000000000000]").isPresent());
        assertFalse(readList(Readers::readInteger, "[null]").isPresent());
        assertFalse(readList(Readers::readInteger, "[1, 2").isPresent());
        assertFalse(readList(Readers::readInteger, "1, 2").isPresent());
        assertFalse(readList(Readers::readInteger, "[a]").isPresent());
        assertFalse(readList(Readers::readInteger, "[00]").isPresent());
        Optional<List<String>> ss;

        ss = readList(Readers::readString, "[hello]");
        aeq(ss.get(), "[hello]");
        aeq(ss.get().size(), 1);

        ss = readList(Readers::readString, "[hello, bye]");
        aeq(ss.get(), "[hello, bye]");
        aeq(ss.get().size(), 2);

        ss = readList(Readers::readString, "[a, b, c]");
        aeq(ss.get(), "[a, b, c]");
        aeq(ss.get().size(), 3);
    }

    @Test
    public void testFindListIn() {
        //todo
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
