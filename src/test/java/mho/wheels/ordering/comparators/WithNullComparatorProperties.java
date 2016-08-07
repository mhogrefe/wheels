package mho.wheels.ordering.comparators;

import mho.wheels.testing.TestProperties;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public class WithNullComparatorProperties extends TestProperties {
    public WithNullComparatorProperties() {
        super("WithNullComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesConstructor_Comparator();
    }

    private void propertiesConstructor() {
        initialize("new WithNullComparator<T>()");
        //noinspection Convert2Diamond
        propertiesCompareToHelper(LIMIT, P, new WithNullComparator<Integer>(), p -> p.withNull(p.integers()));
    }

    private void propertiesConstructor_Comparator() {
        initialize("new WithNullComparator<T>(Comparator<T>)");
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            WithNullComparator<Integer> comparator = new WithNullComparator<>(new ListBasedComparator<>(is));
            propertiesCompareToHelper(TINY_LIMIT, P, comparator, p -> p.uniformSample(toList(nub(cons(null, is)))));
        }
    }
}
