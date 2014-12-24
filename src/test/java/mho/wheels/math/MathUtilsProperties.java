package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
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
            propertiesGcd();
            compareImplementationsGcd();
        }
        System.out.println("Done");
    }

    private static int gcd_simplest(int x, int y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).intValue();
    }

    private static int gcd_explicit(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(x), factors(y)));
    }

    private static void propertiesGcd() {
        initialize();
        System.out.println("\t\ttesting gcd(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            int gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_simplest(p.a, p.b));
            assertEquals(p.toString(), gcd, gcd_explicit(p.a, p.b));
            assertTrue(p.toString(), gcd >= 0);
        }
    }

    private static void compareImplementationsGcd() {
        initialize();
        System.out.println("\t\tcomparing gcd(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_explicit(p.a, p.b);
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
}
