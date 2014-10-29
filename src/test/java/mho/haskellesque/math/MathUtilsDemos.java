package mho.haskellesque.math;

import mho.haskellesque.iterables.ExhaustiveProvider;
import mho.haskellesque.iterables.IterableProvider;
import mho.haskellesque.iterables.RandomProvider;
import mho.haskellesque.structures.Pair;

import java.math.BigInteger;
import java.util.Random;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.math.MathUtils.*;

public class MathUtilsDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;

    private static IterableProvider P;

    public static void main(String[] args) {
        demoBitsPadded_int_int();
    }

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(new Random(0x6af477d9a7e54fcaL));
            LIMIT = 1000;
        } else {
            P = new ExhaustiveProvider();
            LIMIT = 10000;
        }
    }

    private static void demoGcd() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private static void demoBits_int() {
        initialize();
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("bits(" + i + ") = " + toList(bits(i)));
        }
    }

    private static void demoBits_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("bits(" + i + ") = " + toList(bits(i)));
        }
    }

    private static void demoBitsPadded_int_int() {
        initialize();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers()))) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bitsPadded(" + p.a + ", " + p.b + ") = " + toList(bitsPadded(p.a, p.b)));
        }
    }
}
