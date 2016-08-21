package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.util.Collections;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;

@SuppressWarnings("UnusedDeclaration")
public class LexComparatorDemos extends Demos {
    public LexComparatorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        LexComparator<Integer> comparator = new LexComparator<>();
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(P.integersGeometric())))) {
            System.out.println("new LexComparator<Integer>(): " + p.a + " " +
                    Ordering.fromInt(comparator.compare(p.a, p.b)) + " " + p.b);
        }
    }

    private void demoConstructor_Comparator() {
        Iterable<Pair<List<Integer>, Pair<List<Integer>, List<Integer>>>> ps = P.withElement(
                new Pair<>(Collections.emptyList(), new Pair<>(Collections.emptyList(), Collections.emptyList())),
                P.dependentPairsInfinite(
                        P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())),
                        is -> P.pairs(P.withScale(4).lists(P.uniformSample(toList(nub(is)))))
                )
        );
        for (Pair<List<Integer>, Pair<List<Integer>, List<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            LexComparator<Integer> comparator = new LexComparator<>(new ListBasedComparator<>(p.a));
            System.out.println("new LexComparator<Integer>(new ListBasedComparator<Integer>(" +
                    middle(p.a.toString()) + ")): " + p.b.a + " " +
                    Ordering.fromInt(comparator.compare(p.b.a, p.b.b)) + " " + p.b.b);
        }
    }
}
