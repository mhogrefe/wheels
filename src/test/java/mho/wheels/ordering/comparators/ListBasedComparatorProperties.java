package mho.wheels.ordering.comparators;

import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.TINY_LIMIT;
import static mho.wheels.testing.Testing.fail;
import static mho.wheels.testing.Testing.propertiesCompareToHelper;

public class ListBasedComparatorProperties extends TestProperties {
    public ListBasedComparatorProperties() {
        super("ListBasedComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
    }

    private void propertiesConstructor() {
        initialize("new ListBasedComparator<T>(List<T>)");
        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.withNull(P.integers())))) {
            ListBasedComparator<Integer> comparator = new ListBasedComparator<>(is);
            propertiesCompareToHelper(TINY_LIMIT, P, comparator, p -> p.uniformSample(toList(nub(is))));

            Iterable<Pair<Integer, Integer>> psFail = filterInfinite(
                    q -> !is.contains(q.a) || !is.contains(q.b),
                    P.pairs(P.withNull(P.integers()))
            );
            for (Pair<Integer, Integer> p : take(TINY_LIMIT, psFail)) {
                try {
                    comparator.compare(p.a, p.b);
                    fail(is);
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }
}
