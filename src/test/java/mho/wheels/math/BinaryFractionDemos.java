package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import static mho.wheels.iterables.IterableUtils.take;

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

    private static void demoToString() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf);
        }
    }
}
