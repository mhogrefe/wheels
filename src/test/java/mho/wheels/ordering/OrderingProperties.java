package mho.wheels.ordering;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class OrderingProperties extends TestProperties {
    public OrderingProperties() {
        super("Ordering");
    }

    @Override
    protected void testBothModes() {
        propertiesFromInt();
        propertiesToInt();
        propertiesInvert();
        propertiesCompare_T_T();
    }

    private void propertiesFromInt() {
        initialize("fromInt(int)");
        for (int i : take(LIMIT, P.integers())) {
            Ordering o = fromInt(i);
            assertEquals(i, o, compare(i, 0));
        }
    }

    private void propertiesToInt() {
        initialize("toInt()");
        for (Ordering o : take(LIMIT, P.orderings())) {
            int i = o.toInt();
            assertTrue(o, i == -1 || i == 0 || i == 1);
            inverse(Ordering::toInt, Ordering::fromInt, o);
        }
    }

    private void propertiesInvert() {
        initialize("invert()");
        for (Ordering o : take(LIMIT, P.orderings())) {
            o.invert();
            involution(Ordering::invert, o);
        }
    }

    private void propertiesCompare_T_T() {
        initialize("compare(T, T)");
        IterableProvider Q = P.deepCopy();
        IterableProvider R = P.deepCopy();
        for (Pair<Integer, Integer> p : take(LIMIT, zip(P.integers(), Q.integers()))) {
            assertEquals(p, compare(p.a, p.b), EQ);
        }

        P.reset();
        Q.reset();
        for (Pair<Integer, Integer> p : take(LIMIT, EP.pairs(P.integers(), Q.integers()))) {
            Ordering o = compare(p.a, p.b);
            assertEquals(p, compare(p.b, p.a), o.invert());
        }

        P.reset();
        Q.reset();
        R.reset();
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, EP.triples(P.integers(), Q.integers(), R.integers()))) {
            if (compare(t.a, t.b) == LT && compare(t.b, t.c) == LT) {
                assertEquals(t, compare(t.a, t.c), LT);
            }
        }
    }
}
