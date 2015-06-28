package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.filter;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;

public class FloatingPointUtilsProperties {
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
        System.out.println("FloatingPointUtils properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesIsNegativeZero_float();
            propertiesIsNegativeZero_double();
            propertiesIsPositiveZero_float();
            propertiesIsPositiveZero_double();
            propertiesSuccessor_float();
            propertiesPredecessor_float();
            propertiesSuccessor_double();
            propertiesPredecessor_double();
        }
        System.out.println("Done");
    }

    private static void propertiesIsNegativeZero_float() {
        initialize("isNegativeZero(float)");
        for (float f : take(LIMIT, P.floats())) {
            FloatingPointUtils.isNegativeZero(f);
        }

        //todo test nonzero floats
        for (float f : take(LIMIT, P.ordinaryFloats())) {
            assertFalse(f, FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesIsNegativeZero_double() {
        initialize("isNegativeZero(double)");
        for (double d : take(LIMIT, P.doubles())) {
            FloatingPointUtils.isNegativeZero(d);
        }

        //todo test nonzero doubles
        for (double d : take(LIMIT, P.ordinaryDoubles())) {
            assertFalse(d, FloatingPointUtils.isNegativeZero(d));
        }
    }

    private static void propertiesIsPositiveZero_float() {
        initialize("isPositiveZero(float)");
        for (float f : take(LIMIT, P.floats())) {
            FloatingPointUtils.isPositiveZero(f);
        }

        //todo test nonzero floats
    }

    private static void propertiesIsPositiveZero_double() {
        initialize("isPositiveZero(double)");
        for (double d : take(LIMIT, P.doubles())) {
            FloatingPointUtils.isPositiveZero(d);
        }

        //todo test nonzero doubles
    }

    private static void propertiesSuccessor_float() {
        initialize("successor(float)");
        Iterable<Float> fs = filter(
                f -> !Float.isNaN(f) && f != Float.POSITIVE_INFINITY && !FloatingPointUtils.isNegativeZero(f),
                P.floats()
        );
        for (float f : take(LIMIT, fs)) {
            //noinspection RedundantCast
            inverses((Function<Float, Float>) FloatingPointUtils::successor, FloatingPointUtils::predecessor, f);
        }
    }

    private static void propertiesPredecessor_float() {
        initialize("predecessor(float)");
        Iterable<Float> fs = filter(
                f -> !Float.isNaN(f) && f != Float.NEGATIVE_INFINITY && !FloatingPointUtils.isPositiveZero(f),
                P.floats()
        );
        for (float f : take(LIMIT, fs)) {
            //noinspection RedundantCast
            inverses((Function<Float, Float>) FloatingPointUtils::predecessor, FloatingPointUtils::successor, f);
        }
    }

    private static void propertiesSuccessor_double() {
        initialize("successor(double)");
        Iterable<Double> ds = filter(
                d -> !Double.isNaN(d) && d != Double.POSITIVE_INFINITY && !FloatingPointUtils.isNegativeZero(d),
                P.doubles()
        );
        for (double d : take(LIMIT, ds)) {
            //noinspection RedundantCast
            inverses((Function<Double, Double>) FloatingPointUtils::successor, FloatingPointUtils::predecessor, d);
        }
    }

    private static void propertiesPredecessor_double() {
        initialize("predecessor(double)");
        Iterable<Double> ds = filter(
                d -> !Double.isNaN(d) && d != Double.NEGATIVE_INFINITY && !FloatingPointUtils.isPositiveZero(d),
                P.doubles()
        );
        for (double d : take(LIMIT, ds)) {
            //noinspection RedundantCast
            inverses((Function<Double, Double>) FloatingPointUtils::predecessor, FloatingPointUtils::successor, d);
        }
    }
}
