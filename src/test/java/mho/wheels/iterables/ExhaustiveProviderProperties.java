package mho.wheels.iterables;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Testing;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.testNoRemove;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class ExhaustiveProviderProperties {
    private static final ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final int TINY_LIMIT = 20;
    private static int LIMIT;
    private static IterableProvider P;

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(new RandomProvider(0x6af477d9a7e54fcaL), 1000, "randomly"));
        System.out.println("RandomProvider properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesRangeUp_byte();
            propertiesRangeUp_short();
            propertiesRangeUp_int();
            propertiesRangeUp_long();
            propertiesRangeUp_BigInteger();
            propertiesRangeUp_char();
            propertiesRangeDown_byte();
            propertiesRangeDown_short();
            propertiesRangeDown_int();
            propertiesRangeDown_long();
            propertiesRangeDown_BigInteger();
            propertiesRangeDown_char();
            propertiesRange_byte_byte();
            propertiesRange_short_short();
            propertiesRange_int_int();
            propertiesRange_long_long();
            propertiesRange_BigInteger_BigInteger();
            propertiesRange_char_char();
        }
        System.out.println("Done");
    }

    private static void propertiesRangeUp_byte() {
        System.out.println("\t\ttesting rangeUp(byte) properties...");

        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeUp(b);
            assertTrue(Byte.toString(b), all(c -> c != null, bs));
            testNoRemove(bs);
            assertEquals(Byte.toString(b), length(bs), (1 << 7) - b);
            assertTrue(Byte.toString(b), unique(bs));
            assertTrue(Byte.toString(b), all(c -> c >= b, bs));
            assertTrue(Byte.toString(b), weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
        }
    }

    private static void propertiesRangeUp_short() {
        System.out.println("\t\ttesting rangeUp(short) properties...");

        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = take(TINY_LIMIT, EP.rangeUp(s));
            assertTrue(Short.toString(s), all(t -> t != null, ss));
            testNoRemove(ss, TINY_LIMIT);
            assertTrue(Short.toString(s), unique(ss));
            assertTrue(Short.toString(s), all(t -> t >= s, ss));
            assertTrue(Short.toString(s), weaklyIncreasing((Iterable<Integer>) map(Math::abs, ss)));
        }
    }

    private static void propertiesRangeUp_int() {
        System.out.println("\t\ttesting rangeUp(int) properties...");

        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = take(TINY_LIMIT, EP.rangeUp(i));
            assertTrue(Integer.toString(i), all(j -> j != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(Integer.toString(i), unique(is));
            assertTrue(Integer.toString(i), all(j -> j >= i, is));
            assertTrue(Integer.toString(i), weaklyIncreasing((Iterable<Integer>) map(Math::abs, is)));
        }
    }

    private static void propertiesRangeUp_long() {
        System.out.println("\t\ttesting rangeUp(long) properties...");

        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = take(TINY_LIMIT, EP.rangeUp(l));
            assertTrue(Long.toString(l), all(m -> m != null, ls));
            testNoRemove(ls, TINY_LIMIT);
            assertTrue(Long.toString(l), unique(ls));
            assertTrue(Long.toString(l), all(m -> m >= l, ls));
            assertTrue(Long.toString(l), weaklyIncreasing((Iterable<Long>) map(Math::abs, ls)));
        }
    }

    private static void propertiesRangeUp_BigInteger() {
        System.out.println("\t\ttesting rangeUp(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = take(TINY_LIMIT, EP.rangeUp(i));
            assertTrue(i.toString(), all(j -> j != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(i.toString(), unique(is));
            assertTrue(i.toString(), all(j -> ge(j, i), is));
            assertTrue(i.toString(), weaklyIncreasing(map(BigInteger::abs, is)));
        }
    }

    private static void propertiesRangeUp_char() {
        System.out.println("\t\ttesting rangeUp(char) properties...");

        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = take(TINY_LIMIT, EP.rangeUp(c));
            assertTrue(Character.toString(c), all(d -> d != null, cs));
            testNoRemove(cs, TINY_LIMIT);
            unique(cs);
            assertTrue(nicePrint(c), all(d -> d >= c, cs));
            assertTrue(nicePrint(c), increasing(cs));
        }
    }

    private static void propertiesRangeDown_byte() {
        System.out.println("\t\ttesting rangeDown(byte) properties...");

        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeDown(b);
            assertTrue(Byte.toString(b), all(c -> c != null, bs));
            testNoRemove(bs);
            assertEquals(Byte.toString(b), length(bs), b + (1 << 7) + 1);
            assertTrue(Byte.toString(b), unique(bs));
            assertTrue(Byte.toString(b), all(c -> c <= b, bs));
            assertTrue(Byte.toString(b), weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
        }
    }

    private static void propertiesRangeDown_short() {
        System.out.println("\t\ttesting rangeDown(short) properties...");

        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = take(TINY_LIMIT, EP.rangeDown(s));
            assertTrue(Short.toString(s), all(t -> t != null, ss));
            testNoRemove(ss, TINY_LIMIT);
            assertTrue(Short.toString(s), unique(ss));
            assertTrue(Short.toString(s), all(t -> t <= s, ss));
            assertTrue(Short.toString(s), weaklyIncreasing((Iterable<Integer>) map(Math::abs, ss)));
        }
    }

    private static void propertiesRangeDown_int() {
        System.out.println("\t\ttesting rangeDown(int) properties...");

        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = take(TINY_LIMIT, EP.rangeDown(i));
            assertTrue(Integer.toString(i), all(j -> j != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(Integer.toString(i), unique(is));
            assertTrue(Integer.toString(i), all(j -> j <= i, is));
            assertTrue(Integer.toString(i), weaklyIncreasing((Iterable<Integer>) map(Math::abs, is)));
        }
    }

    private static void propertiesRangeDown_long() {
        System.out.println("\t\ttesting rangeDown(long) properties...");

        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = take(TINY_LIMIT, EP.rangeDown(l));
            assertTrue(Long.toString(l), all(m -> m != null, ls));
            testNoRemove(ls, TINY_LIMIT);
            assertTrue(Long.toString(l), unique(ls));
            assertTrue(Long.toString(l), all(m -> m <= l, ls));
            assertTrue(Long.toString(l), weaklyIncreasing((Iterable<Long>) map(Math::abs, ls)));
        }
    }

    private static void propertiesRangeDown_BigInteger() {
        System.out.println("\t\ttesting rangeDown(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = take(TINY_LIMIT, EP.rangeDown(i));
            assertTrue(i.toString(), all(j -> j != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(i.toString(), unique(is));
            assertTrue(i.toString(), all(j -> le(j, i), is));
            assertTrue(i.toString(), weaklyIncreasing(map(BigInteger::abs, is)));
        }
    }

    private static void propertiesRangeDown_char() {
        System.out.println("\t\ttesting rangeDown(char) properties...");

        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = take(TINY_LIMIT, EP.rangeDown(c));
            assertTrue(Character.toString(c), all(d -> d != null, cs));
            testNoRemove(cs, TINY_LIMIT);
            assertTrue(nicePrint(c), unique(cs));
            assertTrue(nicePrint(c), all(d -> d <= c, cs));
            assertTrue(nicePrint(c), increasing(cs));
        }
    }

    private static void propertiesRange_byte_byte() {
        System.out.println("\t\ttesting range(byte, byte) properties...");

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            Iterable<Byte> bs = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(b -> b != null, bs));
            testNoRemove(bs, TINY_LIMIT);
            assertTrue(p.toString(), unique(bs));
            assertTrue(p.toString(), all(b -> b >= p.a && b <= p.b, bs));
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(Byte.toString(b), EP.range(b, b), Arrays.asList(b));
            aeqit(Byte.toString(b), TINY_LIMIT, EP.range(b, Byte.MAX_VALUE), EP.rangeUp(b));
            aeqit(Byte.toString(b), TINY_LIMIT, EP.range(Byte.MIN_VALUE, b), EP.rangeDown(b));
        }
    }

    private static void propertiesRange_short_short() {
        System.out.println("\t\ttesting range(short, short) properties...");

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            Iterable<Short> ss = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(s -> s != null, ss));
            testNoRemove(ss, TINY_LIMIT);
            assertTrue(p.toString(), unique(ss));
            assertTrue(p.toString(), all(s -> s >= p.a && s <= p.b, ss));
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, ss)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(ss));
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(Short.toString(s), EP.range(s, s), Arrays.asList(s));
            aeqit(Short.toString(s), TINY_LIMIT, EP.range(s, Short.MAX_VALUE), EP.rangeUp(s));
            aeqit(Short.toString(s), TINY_LIMIT, EP.range(Short.MIN_VALUE, s), EP.rangeDown(s));
        }
    }

    private static void propertiesRange_int_int() {
        System.out.println("\t\ttesting range(int, int) properties...");

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            Iterable<Integer> is = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(i -> i != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(p.toString(), unique(is));
            assertTrue(p.toString(), all(i -> i >= p.a && i <= p.b, is));
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, is)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(is));
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(Integer.toString(i), EP.range(i, i), Arrays.asList(i));
            aeqit(Integer.toString(i), TINY_LIMIT, EP.range(i, Integer.MAX_VALUE), EP.rangeUp(i));
            aeqit(Integer.toString(i), TINY_LIMIT, EP.range(Integer.MIN_VALUE, i), EP.rangeDown(i));
        }
    }

    private static void propertiesRange_long_long() {
        System.out.println("\t\ttesting range(long, long) properties...");

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            Iterable<Long> ls = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(l -> l != null, ls));
            testNoRemove(ls, TINY_LIMIT);
            assertTrue(p.toString(), unique(ls));
            assertTrue(p.toString(), all(l -> l >= p.a && l <= p.b, ls));
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Long>) map(Math::abs, ls)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(ls));
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(Long.toString(l), EP.range(l, l), Arrays.asList(l));
            aeqit(Long.toString(l), TINY_LIMIT, EP.range(l, Long.MAX_VALUE), EP.rangeUp(l));
            aeqit(Long.toString(l), TINY_LIMIT, EP.range(Long.MIN_VALUE, l), EP.rangeDown(l));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        System.out.println("\t\ttesting range(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(i -> i != null, is));
            testNoRemove(is, TINY_LIMIT);
            assertTrue(p.toString(), unique(is));
            assertTrue(p.toString(), all(i -> ge(i, p.a) && le(i, p.b), is));
            assertTrue(p.toString(), weaklyIncreasing(map(BigInteger::abs, is)));
            assertEquals(p.toString(), gt(p.a, p.b), isEmpty(is));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i.toString(), EP.range(i, i), Arrays.asList(i));
        }
    }

    private static void propertiesRange_char_char() {
        System.out.println("\t\ttesting range(char, char) properties...");

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            Iterable<Character> cs = take(TINY_LIMIT, EP.range(p.a, p.b));
            assertTrue(p.toString(), all(c -> c != null, cs));
            testNoRemove(cs, TINY_LIMIT);
            assertTrue(p.toString(), unique(cs));
            assertTrue(p.toString(), all(c -> c >= p.a && c <= p.b, cs));
            assertTrue(p.toString(), increasing(cs));
            assertEquals(p.toString(), p.a > p.b, isEmpty(cs));
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.range(c, c), Arrays.asList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range(c, Character.MAX_VALUE), EP.rangeUp(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range('\0', c), EP.rangeDown(c));
        }
    }
}
