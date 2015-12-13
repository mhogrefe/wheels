package mho.wheels.numberUtils;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.numberUtils.FloatingPointUtils.*;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;

@SuppressWarnings("UnusedDeclaration")
public strictfp class FloatingPointUtilsDemos extends Demos {
    private void demoIsNegativeZero_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println(f + " is " + (FloatingPointUtils.isNegativeZero(f) ? "" : "not ") + "negative zero");
        }
    }

    private void demoIsNegativeZero_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println(d + " is " + (FloatingPointUtils.isNegativeZero(d) ? "" : "not ") + "negative zero");
        }
    }

    private void demoIsPositiveZero_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println(f + " is " + (FloatingPointUtils.isPositiveZero(f) ? "" : "not ") + "positive zero");
        }
    }

    private void demoIsPositiveZero_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println(d + " is " + (FloatingPointUtils.isPositiveZero(d) ? "" : "not ") + "positive zero");
        }
    }

    private void demoSuccessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && f != Float.POSITIVE_INFINITY, P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("successor(" + f + ") = " + successor(f));
        }
    }

    private void demoPredecessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && f != Float.NEGATIVE_INFINITY, P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("predecessor(" + f + ") = " + predecessor(f));
        }
    }

    private void demoSuccessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && d != Double.POSITIVE_INFINITY, P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("successor(" + d + ") = " + successor(d));
        }
    }

    private void demoPredecessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && d != Double.NEGATIVE_INFINITY, P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("predecessor(" + d + ") = " + predecessor(d));
        }
    }

    private void demoToOrderedRepresentation_float() {
        initialize();
        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("toOrderedRepresentation(" + f + ") = " + toOrderedRepresentation(f));
        }
    }

    private void demoToOrderedRepresentation_double() {
        initialize();
        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("toOrderedRepresentation(" + d + ") = " + toOrderedRepresentation(d));
        }
    }

    private void demoFloatFromOrderedRepresentation() {
        initialize();
        int maxAbs = FloatingPointUtils.POSITIVE_FINITE_FLOAT_COUNT + 1;
        for (int i : take(LIMIT, P.range(-maxAbs, maxAbs))) {
            System.out.println("floatFromOrderedRepresentation(" + i + ") = " + floatFromOrderedRepresentation(i));
        }
    }

    private void demoDoubleFromOrderedRepresentation() {
        initialize();
        long maxAbs = FloatingPointUtils.POSITIVE_FINITE_DOUBLE_COUNT + 1;
        for (long l : take(LIMIT, P.range(-maxAbs, maxAbs))) {
            System.out.println("doubleFromOrderedRepresentation(" + l + ") = " + doubleFromOrderedRepresentation(l));
        }
    }

    private void demoFloatFromMantissaAndExponent() {
        initialize();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("floatFromMantissaAndExponent(" + p.a + ", " + p.b + ") = " +
                    floatFromMantissaAndExponent(p.a, p.b));
        }
    }

    private void demoDoubleFromMantissaAndExponent() {
        initialize();
        for (Pair<Long, Integer> p : take(LIMIT, P.pairs(P.longs(), P.integers()))) {
            System.out.println("doubleFromMantissaAndExponent(" + p.a + ", " + p.b + ") = " +
                    doubleFromMantissaAndExponent(p.a, p.b));
        }
    }

    private void demoToMantissaAndExponent_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("toMantissaAndExponent(" + f + ") = " + toMantissaAndExponent(f));
        }
    }

    private void demoToMantissaAndExponent_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("toMantissaAndExponent(" + d + ") = " + toMantissaAndExponent(d));
        }
    }

    private void demoAbsNegativeZeros_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("absNegativeZeros(" + f + ") = " + absNegativeZeros(f));
        }
    }

    private void demoAbsNegativeZeros_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("absNegativeZeros(" + d + ") = " + absNegativeZeros(d));
        }
    }

    private void demoScaleUp_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("scaleUp(" + f + ") = " + scaleUp(f));
        }
    }

    private void demoScaleUp_double() {
        initialize();
        for (double d : take(MEDIUM_LIMIT, P.doubles())) {
            System.out.println("scaleUp(" + d + ") = " + scaleUp(d));
        }
    }

    private void demoToStringCompact_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("toStringCompact(" + f + ") = " + toStringCompact(f));
        }
    }

    private void demoToStringCompact_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("toStringCompact(" + d + ") = " + toStringCompact(d));
        }
    }
}
