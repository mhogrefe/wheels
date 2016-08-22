package mho.wheels.ordering.comparators;

import mho.wheels.testing.TestProperties;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.TINY_LIMIT;
import static mho.wheels.testing.Testing.propertiesCompareToHelper;

public class LexComparatorProperties extends TestProperties {
    public LexComparatorProperties() {
        super("LexComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesConstructor_Comparator();
    }

    private void propertiesConstructor() {
        initialize("new LexComparator<T>()");
        //noinspection Convert2Diamond
        propertiesCompareToHelper(LIMIT, P, new LexComparator<Integer>(), p -> map(is -> is, p.lists(p.integers())));
    }

    private void propertiesConstructor_Comparator() {
        initialize("new LexComparator<T>(Comparator<T>)");
        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.withNull(P.integers())))) {
            LexComparator<Integer> comparator = new LexComparator<>(new ListBasedComparator<>(is));
            propertiesCompareToHelper(
                    TINY_LIMIT,
                    P,
                    comparator,
                    p -> map(js -> js, p.lists(p.uniformSample(toList(nub(is)))))
            );
        }
    }
}
