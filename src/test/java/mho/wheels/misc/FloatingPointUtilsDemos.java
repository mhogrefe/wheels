package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.misc.FloatingPointUtils.*;

@SuppressWarnings("UnusedDeclaration")
public strictfp class FloatingPointUtilsDemos {
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

    private static void demoIsNegativeZero_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println(f + " is " + (FloatingPointUtils.isNegativeZero(f) ? "" : "not ") + "negative zero");
        }
    }

    private static void demoIsNegativeZero_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println(d + " is " + (FloatingPointUtils.isNegativeZero(d) ? "" : "not ") + "negative zero");
        }
    }

    private static void demoIsPositiveZero_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println(f + " is " + (FloatingPointUtils.isPositiveZero(f) ? "" : "not ") + "positive zero");
        }
    }

    private static void demoIsPositiveZero_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println(d + " is " + (FloatingPointUtils.isPositiveZero(d) ? "" : "not ") + "positive zero");
        }
    }

    private static void demoSuccessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && f != Float.POSITIVE_INFINITY, P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("successor(" + f + ") = " + successor(f));
        }
    }

    private static void demoPredecessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && f != Float.NEGATIVE_INFINITY, P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("predecessor(" + f + ") = " + predecessor(f));
        }
    }

    private static void demoSuccessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && d != Double.POSITIVE_INFINITY, P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("successor(" + d + ") = " + successor(d));
        }
    }

    private static void demoPredecessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && d != Double.NEGATIVE_INFINITY, P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("predecessor(" + d + ") = " + predecessor(d));
        }
    }

    private static void demoFloatFromMantissaAndExponent() {
        initialize();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("floatFromMantissaAndExponent(" + p.a + ", " + p.b + ") = " +
                    floatFromMantissaAndExponent(p.a, p.b));
        }
    }

    private static void demoDoubleFromMantissaAndExponent() {
        initialize();
        for (Pair<Long, Integer> p : take(LIMIT, P.pairs(P.longs(), P.integers()))) {
            System.out.println("doubleFromMantissaAndExponent(" + p.a + ", " + p.b + ") = " +
                    doubleFromMantissaAndExponent(p.a, p.b));
        }
    }

    private static void demoToMantissaAndExponent_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("toMantissaAndExponent(" + f + ") = " + toMantissaAndExponent(f));
        }
    }

    private static void demoToMantissaAndExponent_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("toMantissaAndExponent(" + d + ") = " + toMantissaAndExponent(d));
        }
    }

    private static void demoAbsNegativeZeros_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("absNegativeZeros(" + f + ") = " + absNegativeZeros(f));
        }
    }

    private static void demoAbsNegativeZeros_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("absNegativeZeros(" + d + ") = " + absNegativeZeros(d));
        }
    }
}
