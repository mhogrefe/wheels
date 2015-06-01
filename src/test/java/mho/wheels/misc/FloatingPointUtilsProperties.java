package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.take;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("ConstantConditions")
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
            assertFalse(Float.toString(f), FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesIsNegativeZero_double() {
        initialize("isNegativeZero(double)");
        for (double d : take(LIMIT, P.doubles())) {
            FloatingPointUtils.isNegativeZero(d);
        }

        //todo test nonzero doubles
        for (double d : take(LIMIT, P.ordinaryDoubles())) {
            assertFalse(Double.toString(d), FloatingPointUtils.isNegativeZero(d));
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
}
