package mho.wheels.testing;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import org.jetbrains.annotations.NotNull;

public class Demos {
    protected static final boolean USE_RANDOM = false;
    protected static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    protected static final int SMALL_LIMIT = 1000;
    protected static final int SMALLER_LIMIT = 100;
    protected static final int TINY_LIMIT = 20;
    protected static int LIMIT;
    protected static IterableProvider P;

    protected static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }
}
