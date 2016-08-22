package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class WithNullComparatorDemos extends Demos {
    public WithNullComparatorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        WithNullComparator<Integer> comparator = new WithNullComparator<>();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            System.out.println("new WithNullComparator<Integer>(): " + p.a + " " +
                    Ordering.fromInt(comparator.compare(p.a, p.b)) + " " + p.b);
        }
    }

    private void demoConstructor_Comparator() {
        Iterable<Pair<List<Integer>, Pair<Integer, Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.integers()),
                is -> P.pairs(P.uniformSample(toList(nub(cons(null, is)))))
        );
        for (Pair<List<Integer>, Pair<Integer, Integer>> p : take(LIMIT, ps)) {
            WithNullComparator<Integer> comparator = new WithNullComparator<>(new ListBasedComparator<>(p.a));
            System.out.println("new WithNullComparator<Integer>(new ListBasedComparator<Integer>(" +
                    middle(p.a.toString()) + ")): " + p.b.a + " " +
                    Ordering.fromInt(comparator.compare(p.b.a, p.b.b)) + " " + p.b.b);
        }
    }
}
