package mho.wheels.testing;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static mho.wheels.testing.Testing.LARGE_LIMIT;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;

public abstract class TestProperties {
    protected int LIMIT;
    protected IterableProvider P;
    private @NotNull final String name;

    public TestProperties(@NotNull String name) {
        this.name = name;
    }

    protected void initializeConstant(@NotNull String methodName) {
        System.out.println("\ttesting " + methodName + " properties...");
    }

    protected void initialize(@NotNull String methodName) {
        P.reset();
        System.out.print('\t');
        initializeConstant(methodName);
    }

    protected void testConstant() {}

    protected abstract void testBothModes();

    @Test
    public void testAllProperties() {
        System.out.println(name + " properties");
        testConstant();
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, LARGE_LIMIT, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), MEDIUM_LIMIT, "randomly"));
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            testBothModes();
        }
        System.out.println("Done");
    }
}
