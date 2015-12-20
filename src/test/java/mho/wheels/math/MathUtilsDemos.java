package mho.wheels.math;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.math.BigInteger;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.math.MathUtils.gcd;
import static mho.wheels.math.MathUtils.lcm;

@SuppressWarnings("UnusedDeclaration")
public class MathUtilsDemos extends Demos {
    public MathUtilsDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoGcd_int_int() {
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private void demoGcd_long_long() {
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private void demoLcm() {
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            System.out.println("lcm(" + p.a + ", " + p.b + ") = " + lcm(p.a, p.b));
        }
    }
}
