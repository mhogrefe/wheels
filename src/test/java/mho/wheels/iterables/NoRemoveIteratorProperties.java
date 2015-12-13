package mho.wheels.iterables;

import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.iterables.IterableUtils.toList;
import static mho.wheels.testing.Testing.*;

public class NoRemoveIteratorProperties extends TestProperties {
    public NoRemoveIteratorProperties() {
        super("NoRemoveIterator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesRemove();
    }

    private void propertiesConstructor() {
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

    private void propertiesRemove() {
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
