package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class ListBasedComparatorDemos  extends Demos {
    public ListBasedComparatorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        Iterable<Pair<List<Integer>, Pair<Integer, Integer>>> ps = P.dependentPairs(
                P.withScale(4).listsAtLeast(1, P.withNull(P.integers())),
                is -> P.pairs(P.uniformSample(toList(nub(is))))
        );
        for (Pair<List<Integer>, Pair<Integer, Integer>> p : take(LIMIT, ps)) {
            ListBasedComparator<Integer> comparator = new ListBasedComparator<>(p.a);
            System.out.println("new ListBasedComparator<Integer>(" + middle(p.a.toString()) + "): " + p.b.a + " " +
                    Ordering.fromInt(comparator.compare(p.b.a, p.b.b)) + " " + p.b.b);
        }
    }
}
