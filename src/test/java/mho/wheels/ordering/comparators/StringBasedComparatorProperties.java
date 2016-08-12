package mho.wheels.ordering.comparators;

import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.TINY_LIMIT;
import static mho.wheels.testing.Testing.fail;
import static mho.wheels.testing.Testing.propertiesCompareToHelper;

public class StringBasedComparatorProperties extends TestProperties {
    public StringBasedComparatorProperties() {
        super("StringBasedComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
    }

    private void propertiesConstructor() {
        initialize("new StringBasedComparator(String)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            StringBasedComparator comparator = new StringBasedComparator(s);
            propertiesCompareToHelper(TINY_LIMIT, P, comparator, p -> p.uniformSample(toList(nub(s))));

            Iterable<Pair<Character, Character>> psFail = filterInfinite(
                    q -> !elem(q.a, s) || !elem(q.b, s),
                    P.pairs(P.characters())
            );
            for (Pair<Character, Character> p : take(TINY_LIMIT, psFail)) {
                try {
                    comparator.compare(p.a, p.b);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }
}
