package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.ordering.Ordering.le;
import static mho.wheels.testing.Testing.*;

public class MathUtilsProperties {
    private static boolean USE_RANDOM;
    private static final int TINY_LIMIT = 10;
    private static int LIMIT;

    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
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
            propertiesLcm();
            compareImplementationsLcm();
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
            int gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p, gcd, gcd_int_int_explicit(p.a, p.b));
            assertEquals(p, gcd, gcd(p.b, p.a));
            assertEquals(p, p.a % gcd, 0);
            assertEquals(p, p.b % gcd, 0);
            assertTrue(p, gcd >= 0);
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (int i : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p, p.a % i == 0 && p.b % i == 0);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(i, gcd(i, 1), 1);
        }             

        for (int i : take(LIMIT, filter(j -> j != 0, P.integers()))) {
            assertEquals(i, gcd(i, i), Math.abs(i));
            assertEquals(i, gcd(i, 0), Math.abs(i));
        }

        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                u -> (u.a != 0 || u.b != 0) && (u.b != 0 || u.c != 0),
                P.triples(P.integers())
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            int gcd1 = gcd(gcd(t.a, t.b), t.c);
            int gcd2 = gcd(t.a, gcd(t.b, t.c));
            assertEquals(t, gcd1, gcd2);
        }
    }

    private static void compareImplementationsGcd_int_int() {
        initialize();
        System.out.println("\t\tcomparing gcd(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_int_int_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_int_int_explicit(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            long gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p, gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertEquals(p, gcd, gcd(p.b, p.a));
            assertTrue(p, gcd >= 0);
            assertEquals(p, p.a % gcd, 0L);
            assertEquals(p, p.b % gcd, 0L);
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (long l : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p, p.a % l == 0 && p.b % l == 0);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(l, gcd(l, 1L), 1L);
        }

        for (long l : take(LIMIT, filter(m -> m != 0L, P.longs()))) {
            assertEquals(l, gcd(l, l), Math.abs(l));
            assertEquals(l, gcd(l, 0L), Math.abs(l));
        }

        Iterable<Triple<Long, Long, Long>> ts = filter(
                u -> (u.a != 0L || u.b != 0L) && (u.b != 0L || u.c != 0L),
                P.triples(P.longs())
        );
        for (Triple<Long, Long, Long> t : take(LIMIT, ts)) {
            long gcd1 = gcd(gcd(t.a, t.b), t.c);
            long gcd2 = gcd(t.a, gcd(t.b, t.c));
            assertEquals(t, gcd1, gcd2);
        }
    }

    private static void compareImplementationsGcd_long_long() {
        initialize();
        System.out.println("\t\tcomparing gcd(long, long) implementations...");

        long totalTime = 0;
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_long_long_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        if (P instanceof ExhaustiveProvider) {
            totalTime = 0;
            for (Pair<Long, Long> p : take(LIMIT, ps)) {
                long time = System.nanoTime();
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
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull BigInteger lcm_explicit(@NotNull BigInteger x, @NotNull BigInteger y) {
        return head(orderedIntersection(rangeBy(x, x), rangeBy(y, y)));
    }

    private static void propertiesLcm() {
        initialize();
        System.out.println("\t\ttesting lcm(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p, lcm, lcm(p.b, p.a));
            assertEquals(p, lcm.signum(), 1);
            assertEquals(p, lcm.mod(p.a), BigInteger.ZERO);
            assertEquals(p, lcm.mod(p.b), BigInteger.ZERO);
            for (BigInteger i : take(TINY_LIMIT, P.range(BigInteger.ONE, lcm.subtract(BigInteger.ONE)))) {
                assertFalse(p, i.mod(p.a).equals(BigInteger.ZERO) && i.mod(p.b).equals(BigInteger.ZERO));
            }
        }

        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(
                filter(i -> le(i, BigInteger.valueOf(1000000)), P.withScale(10).positiveBigIntegers())
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p, lcm, lcm_explicit(p.a, p.b));
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            assertEquals(i, lcm(i, BigInteger.ONE), i);
            assertEquals(i, lcm(i, i), i);
        }

        for (Triple<BigInteger, BigInteger, BigInteger> t : take(LIMIT, P.triples(P.positiveBigIntegers()))) {
            BigInteger lcm1 = lcm(lcm(t.a, t.b), t.c);
            BigInteger lcm2 = lcm(t.a, lcm(t.b, t.c));
            assertEquals(t, lcm1, lcm2);
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.rangeDown(BigInteger.ZERO), P.positiveBigIntegers());
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                lcm(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                lcm(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsLcm() {
        initialize();
        System.out.println("\t\tcomparing lcm(BigInteger, BigInteger) implementations...");

        long totalTime = 0;

        if (P instanceof ExhaustiveProvider) {
            for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
                long time = System.nanoTime();
                lcm_explicit(p.a, p.b);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");
        } else {
            System.out.println("\t\t\texplicit: too long");
        }

        totalTime = 0;
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            long time = System.nanoTime();
            lcm(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }
}
