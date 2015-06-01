package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;

import java.math.BigInteger;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.math.BinaryFraction.of;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class BinaryFractionDemos {
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

    private static void demoGetMantissa() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getMantissa(" + bf + ") = " + bf.getMantissa());
        }
    }

    private static void demoGetExponent() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getExponent(" + bf + ") = " + bf.getExponent());
        }
    }

    private static void demoOf_BigInteger_int() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps = filter(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("of(" + p.a + ", " + p.b + ") = " + of(p.a, p.b));
        }
    }

    private static void demoOf_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private static void demoOf_int() {
        initialize();
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private static void demoOf_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("of(" + f + ") = " + of(f));
        }
    }

    private static void demoOf_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("of(" + d + ") = " + of(d));
        }
    }

    private static void demoBigDecimalValue() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("bigDecimalValue(" + bf + ") = " + bf.bigDecimalValue());
        }
    }

    private static void demoIsInteger() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf + " is " + (bf.isInteger() ? "" : "not ") + "an integer");
        }
    }

    private static void demoCompareTo() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + " " + Ordering.compare(p.a, p.b).toChar() + " " + p.b);
        }
    }

    private static void demoEquals_BinaryFraction() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private static void demoEquals_null() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            //noinspection ObjectEqualsNull
            System.out.println(bf + (bf.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private static void demoHashCode() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("hashCode(" + bf + ") = " + bf.hashCode());
        }
    }

    private static void demoToString() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf);
        }
    }
}
