package mho.wheels.iterables;

import mho.wheels.testing.Demos;

import java.util.Iterator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class NoRemoveIteratorDemos extends Demos {
    public NoRemoveIteratorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor_finite() {
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
            System.out.println("NoRemoveIterable from " + xs + ": " + its(() -> it));
        }
    }

    private void demoConstructor_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.integers())))) {
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
