package mho.wheels.testing;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import static mho.wheels.testing.Testing.*;

public class Demos {
    protected final boolean USE_RANDOM = false;
    protected int LIMIT;
    protected IterableProvider P;

    protected void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = MEDIUM_LIMIT;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = LARGE_LIMIT;
        }
    }
}
