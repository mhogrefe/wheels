package mho.wheels.iterables;

import java.util.Iterator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class NoRemoveIteratorDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;
    private static final int SMALL_LIMIT = 1000;
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

    private static void demoConstructor_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integers()))) {
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
            System.out.println("NoRemoveIterable from " + xs + ": " + its(() -> it));
        }
    }

    private static void demoConstructor_cyclic() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.repeatingIterables(P.integers()))) {
            NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
                private Iterator<Integer> iterator = xs.iterator();

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    return iterator.next();
                }
            };
            System.out.println("NoRemoveIterable from " + its(xs) + ": " + its(() -> it));
        }
    }
}
