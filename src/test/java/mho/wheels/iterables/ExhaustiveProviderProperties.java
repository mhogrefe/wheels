package mho.wheels.iterables;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

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
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        System.out.println("RandomProvider properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesUniformSample_Iterable();
            propertiesUniformSample_String();
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

    private static void propertiesUniformSample_Iterable() {
        System.out.println("\t\ttesting uniformSample(Iterable<T>) properties...");

        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            Iterable<Integer> js = EP.uniformSample(is);
            aeqit(is.toString(), is, js);
            testNoRemove(js);
        }
    }

    private static void propertiesUniformSample_String() {
        System.out.println("\t\ttesting uniformSample(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            Iterable<Character> cs = EP.uniformSample(s);
            assertEquals(s, s, charsToString(cs));
            testNoRemove(cs);
        }
    }

    private static <T> void simpleTest(
            @NotNull String message,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        Iterable<T> txs = take(TINY_LIMIT, xs);
        assertTrue(message, all(x -> x != null && predicate.test(x), txs));
        testNoRemove(TINY_LIMIT, txs);
        assertTrue(message, unique(txs));
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
            Iterable<Short> ss = EP.rangeUp(s);
            simpleTest(Short.toString(s), ss, t -> t >= s);
            assertTrue(Short.toString(s), weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
        }
    }

    private static void propertiesRangeUp_int() {
        System.out.println("\t\ttesting rangeUp(int) properties...");

        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeUp(i);
            simpleTest(Integer.toString(i), is, j -> j >= i);
            assertTrue(
                    Integer.toString(i),
                    weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, is)))
            );
        }
    }

    private static void propertiesRangeUp_long() {
        System.out.println("\t\ttesting rangeUp(long) properties...");

        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeUp(l);
            simpleTest(Long.toString(l), ls, m -> m >= l);
            assertTrue(Long.toString(l), weaklyIncreasing((Iterable<Long>) map(Math::abs, take(TINY_LIMIT, ls))));
        }
    }

    private static void propertiesRangeUp_BigInteger() {
        System.out.println("\t\ttesting rangeUp(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeUp(i);
            simpleTest(i.toString(), is, j -> ge(j, i));
            assertTrue(i.toString(), weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private static void propertiesRangeUp_char() {
        System.out.println("\t\ttesting rangeUp(char) properties...");

        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeUp(c);
            simpleTest(nicePrint(c), cs, d -> d >= c);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
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
            Iterable<Short> ss = EP.rangeDown(s);
            simpleTest(Short.toString(s), ss, t -> t <= s);
            assertTrue(Short.toString(s), weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
        }
    }

    private static void propertiesRangeDown_int() {
        System.out.println("\t\ttesting rangeDown(int) properties...");

        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeDown(i);
            simpleTest(Integer.toString(i), is, j -> j <= i);
            assertTrue(
                    Integer.toString(i),
                    weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, is)))
            );
        }
    }

    private static void propertiesRangeDown_long() {
        System.out.println("\t\ttesting rangeDown(long) properties...");

        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeDown(l);
            simpleTest(Long.toString(l), ls, m -> m <= l);
            assertTrue(Long.toString(l), weaklyIncreasing((Iterable<Long>) map(Math::abs, take(TINY_LIMIT, ls))));
        }
    }

    private static void propertiesRangeDown_BigInteger() {
        System.out.println("\t\ttesting rangeDown(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeDown(i);
            simpleTest(i.toString(), is, j -> le(j, i));
            assertTrue(i.toString(), weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private static void propertiesRangeDown_char() {
        System.out.println("\t\ttesting rangeDown(char) properties...");

        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeDown(c);
            simpleTest(Character.toString(c), cs, d -> d <= c);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
        }
    }

    private static void propertiesRange_byte_byte() {
        System.out.println("\t\ttesting range(byte, byte) properties...");

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            Iterable<Byte> bs = EP.range(p.a, p.b);
            assertTrue(p.toString(), all(b -> b != null, bs));
            testNoRemove(bs);
            assertEquals(p.toString(), length(bs), p.a > p.b ? 0 : p.b - p.a + 1);
            assertTrue(p.toString(), unique(bs));
            assertTrue(p.toString(), all(b -> b >= p.a && b <= p.b, bs));
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(Byte.toString(b), EP.range(b, b), Collections.singletonList(b));
            aeqit(Byte.toString(b), TINY_LIMIT, EP.range(b, Byte.MAX_VALUE), EP.rangeUp(b));
            aeqit(Byte.toString(b), TINY_LIMIT, EP.range(Byte.MIN_VALUE, b), EP.rangeDown(b));
        }
    }

    private static void propertiesRange_short_short() {
        System.out.println("\t\ttesting range(short, short) properties...");

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            Iterable<Short> ss = EP.range(p.a, p.b);
            simpleTest(p.toString(), ss, s -> s >= p.a && s <= p.b);
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
            assertEquals(p.toString(), p.a > p.b, isEmpty(ss));
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(Short.toString(s), EP.range(s, s), Collections.singletonList(s));
            aeqit(Short.toString(s), TINY_LIMIT, EP.range(s, Short.MAX_VALUE), EP.rangeUp(s));
            aeqit(Short.toString(s), TINY_LIMIT, EP.range(Short.MIN_VALUE, s), EP.rangeDown(s));
        }
    }

    private static void propertiesRange_int_int() {
        System.out.println("\t\ttesting range(int, int) properties...");

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            Iterable<Integer> is = EP.range(p.a, p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            simpleTest(p.toString(), is, i -> i >= p.a && i <= p.b);
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Integer>) map(Math::abs, tis)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(is));
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(Integer.toString(i), EP.range(i, i), Collections.singletonList(i));
            aeqit(Integer.toString(i), TINY_LIMIT, EP.range(i, Integer.MAX_VALUE), EP.rangeUp(i));
            aeqit(Integer.toString(i), TINY_LIMIT, EP.range(Integer.MIN_VALUE, i), EP.rangeDown(i));
        }
    }

    private static void propertiesRange_long_long() {
        System.out.println("\t\ttesting range(long, long) properties...");

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            Iterable<Long> ls = EP.range(p.a, p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            simpleTest(p.toString(), ls, l -> l >= p.a && l <= p.b);
            assertTrue(p.toString(), weaklyIncreasing((Iterable<Long>) map(Math::abs, tls)));
            assertEquals(p.toString(), p.a > p.b, isEmpty(ls));
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(Long.toString(l), EP.range(l, l), Collections.singletonList(l));
            aeqit(Long.toString(l), TINY_LIMIT, EP.range(l, Long.MAX_VALUE), EP.rangeUp(l));
            aeqit(Long.toString(l), TINY_LIMIT, EP.range(Long.MIN_VALUE, l), EP.rangeDown(l));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        System.out.println("\t\ttesting range(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = EP.range(p.a, p.b);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            simpleTest(p.toString(), is, i -> ge(i, p.a) && le(i, p.b));
            assertTrue(p.toString(), weaklyIncreasing(map(BigInteger::abs, tis)));
            assertEquals(p.toString(), gt(p.a, p.b), isEmpty(is));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i.toString(), EP.range(i, i), Collections.singletonList(i));
        }
    }

    private static void propertiesRange_char_char() {
        System.out.println("\t\ttesting range(char, char) properties...");

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            Iterable<Character> cs = EP.range(p.a, p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            simpleTest(p.toString(), cs, c -> c >= p.a && c <= p.b);
            assertTrue(p.toString(), increasing(tcs));
            assertEquals(p.toString(), p.a > p.b, isEmpty(cs));
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.range(c, c), Collections.singletonList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range(c, Character.MAX_VALUE), EP.rangeUp(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range('\0', c), EP.rangeDown(c));
        }
    }
}
