package mho.wheels.iterables;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
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
            assertTrue(Character.toString(c), all(d -> d >= c, cs));
            assertTrue(Character.toString(c), weaklyIncreasing(cs));
        }
    }
}
