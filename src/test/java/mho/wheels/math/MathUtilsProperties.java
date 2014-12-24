package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static org.junit.Assert.*;

public class MathUtilsProperties {
    private static boolean USE_RANDOM;
    private static int LIMIT;

    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(new Random(0x6af477d9a7e54fcaL));
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    @Test
    public void testAllProperties() {
        System.out.println("MathUtils properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
            propertiesGcd_int_int();
            compareImplementationsGcd_int_int();
            propertiesGcd_long_long();
            compareImplementationsGcd_long_long();
            propertiesBits_int();
            compareImplementationsBits_int();
            propertiesBits_BigInteger();
        }
        System.out.println("Done");
    }

    private static int gcd_int_int_simplest(int x, int y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).intValue();
    }

    private static int gcd_int_int_explicit(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(x), factors(y)));
    }

    private static void propertiesGcd_int_int() {
        initialize();
        System.out.println("\t\ttesting gcd(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            int gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p.toString(), gcd, gcd_int_int_explicit(p.a, p.b));
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
        }
    }

    private static void compareImplementationsGcd_int_int() {
        initialize();
        System.out.println("\t\tcomparing gcd(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_int_int_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_int_int_explicit(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static long gcd_long_long_simplest(long x, long y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).longValue();
    }

    private static long gcd_long_long_explicit(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(BigInteger.valueOf(x)), factors(BigInteger.valueOf(y)))).longValue();
    }

    private static void propertiesGcd_long_long() {
        initialize();
        System.out.println("\t\ttesting gcd(long, long) properties...");

        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p.toString(), gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
        }
    }

    private static void compareImplementationsGcd_long_long() {
        initialize();
        System.out.println("\t\tcomparing gcd(long, long) implementations...");

        long totalTime = 0;
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_long_long_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        if (P instanceof ExhaustiveProvider) {
            for (Pair<Long, Long> p : take(LIMIT, ps)) {
                long time = System.nanoTime();
                assert p.a != null;
                assert p.b != null;
                gcd_long_long_explicit(p.a, p.b);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");
        } else {
            System.out.println("\t\t\texplicit: too long");
        }

        totalTime = 0;
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull Iterable<Boolean> bits_int_simplest(int n) {
        return bits(BigInteger.valueOf(n));
    }

    private static void propertiesBits_int() {
        initialize();
        System.out.println("\t\ttesting bits(int) properties...");

        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Boolean> bits = toList(bits(i));
            aeq(Integer.toString(i), bits, bits_int_simplest(i));
            assertTrue(Integer.toString(i), all(b -> b != null, bits));
            assertEquals(Integer.toString(i), fromBits(bits).intValueExact(), i);
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(Integer.toString(i), bits.isEmpty());
            assertEquals(Integer.toString(i), last(bits), true);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                bits(i);
                fail(Integer.toString(i));
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBits_int() {
        initialize();
        System.out.println("\t\tcomparing bits(int) implementations...");

        long totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bits_int_simplest(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bits(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBits_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bits(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertTrue(i.toString(), all(b -> b != null, bits));
            assertEquals(i.toString(), fromBits(bits), i);
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(i.toString(), bits.isEmpty());
            assertEquals(i.toString(), last(bits), true);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                bits(i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static <T> void aeq(String message, Iterable<T> xs, Iterable<T> ys) {
        assertTrue(message, equal(xs, ys));
    }
}
