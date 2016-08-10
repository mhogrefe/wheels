package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class StringBasedComparatorDemos  extends Demos {
    public StringBasedComparatorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        Iterable<Pair<String, Pair<Character, Character>>> ps = P.dependentPairs(
                P.withScale(4).stringsAtLeast(1),
                s -> P.pairs(P.uniformSample(nub(s)))
        );
        for (Pair<String, Pair<Character, Character>> p : take(LIMIT, ps)) {
            StringBasedComparator comparator = new StringBasedComparator(p.a);
            System.out.println("new StringBasedComparator(" + nicePrint(p.a) + "): " + nicePrint(p.b.a) + " " +
                    Ordering.fromInt(comparator.compare(p.b.a, p.b.b)) + " " + nicePrint(p.b.b));
        }
    }
}
