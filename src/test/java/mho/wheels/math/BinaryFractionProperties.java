package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("ConstantConditions")
public class BinaryFractionProperties {
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize(String name) {
        P.reset();
        System.out.println("\t\ttesting " + name + " properties...");
    }

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        System.out.println("BinaryFraction properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesGetMantissa();
            propertiesGetExponent();
            propertiesOf_BigInteger_int();
        }
        System.out.println("Done");
    }

    private static void propertiesGetMantissa() {
        initialize("getMantissa()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigInteger mantissa = bf.getMantissa();
            assertEquals(bf, of(mantissa, bf.getExponent()), bf);
            assertTrue(bf, mantissa.equals(BigInteger.ZERO) || mantissa.testBit(0));
        }
    }

    private static void propertiesGetExponent() {
        initialize("getExponent()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            bf.getExponent();
        }
    }

    private static void propertiesOf_BigInteger_int() {
        initialize("of(BigInteger, int)");
        Iterable<Pair<BigInteger, Integer>> ps = filter(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BinaryFraction bf = of(p.a, p.b);
            bf.validate();
        }

        Iterable<Pair<BigInteger, Integer>> psFail = P.dependentPairs(
                map(i -> i.shiftLeft(1), P.nonzeroBigIntegers()),
                m -> P.range(Integer.MIN_VALUE - m.getLowestSetBit(), Integer.MAX_VALUE)
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            try {
                of(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
