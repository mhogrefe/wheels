package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.FloatingPointUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class FloatingPointUtilsDemos {
    private static final boolean USE_RANDOM = false;
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

    private static void demoSuccessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && (!Float.isInfinite(f) || f < 0), P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("successor(" + f + ") = " + successor(f));
        }
    }

    private static void demoPredecessor_float() {
        initialize();
        Iterable<Float> fs = filter(f -> !Float.isNaN(f) && (!Float.isInfinite(f) || f > 0), P.floats());
        for (float f : take(LIMIT, fs)) {
            System.out.println("predecessor(" + f + ") = " + predecessor(f));
        }
    }

    private static void demoSuccessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && (!Double.isInfinite(d) || d < 0), P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("successor(" + d + ") = " + successor(d));
        }
    }

    private static void demoPredecessor_double() {
        initialize();
        Iterable<Double> ds = filter(d -> !Double.isNaN(d) && (!Double.isInfinite(d) || d > 0), P.doubles());
        for (double d : take(LIMIT, ds)) {
            System.out.println("predecessor(" + d + ") = " + predecessor(d));
        }
    }
}
