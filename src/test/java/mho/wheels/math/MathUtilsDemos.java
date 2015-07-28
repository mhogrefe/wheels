package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;

import java.math.BigInteger;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.math.MathUtils.gcd;
import static mho.wheels.math.MathUtils.lcm;

@SuppressWarnings("UnusedDeclaration")
public class MathUtilsDemos {
    private static final boolean USE_RANDOM = false;
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

    private static void demoGcd_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private static void demoGcd_long_long() {
        initialize();
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private static void demoLcm() {
        initialize();
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            System.out.println("lcm(" + p.a + ", " + p.b + ") = " + lcm(p.a, p.b));
        }
    }
}
