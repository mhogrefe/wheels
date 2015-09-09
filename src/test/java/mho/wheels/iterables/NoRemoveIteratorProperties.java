package mho.wheels.iterables;

import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.assertEquals;

public class NoRemoveIteratorProperties {
    private static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static int LIMIT;
    private static final int TINY_LIMIT = 20;
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
        System.out.println("NoRemoveIterator properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesConstructor();
            propertiesRemove();
        }
        System.out.println("Done");
    }

    private static void propertiesConstructor() {
        initialize("NoRemoveIterator()");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < xs.size();
                }

                @Override
                public Integer next() {
                    return xs.get(i++);
                }
            };
            assertEquals(xs, toList(() -> it), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.integers())))) {
            NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
                private final @NotNull Iterator<Integer> iterator = xs.iterator();

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    return iterator.next();
                }
            };
            aeqit(xs, TINY_LIMIT, xs, () -> it);
        }
    }

    private static void propertiesRemove() {
        initialize("remove()");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < xs.size();
                }

                @Override
                public Integer next() {
                    return xs.get(i++);
                }
            };
            testNoRemove(TINY_LIMIT, () -> it);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.integers())))) {
            NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
                private final @NotNull Iterator<Integer> iterator = xs.iterator();

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    return iterator.next();
                }
            };
            testNoRemove(TINY_LIMIT, () -> it);
        }
    }
}
