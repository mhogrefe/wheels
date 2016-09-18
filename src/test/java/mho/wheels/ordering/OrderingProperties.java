package mho.wheels.ordering;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;

import java.util.Comparator;
import java.util.Objects;

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
        propertiesCompare_Comparator_T_T();
        propertiesEq_T_T();
        propertiesNe_T_T();
        propertiesLt_T_T();
        propertiesGt_T_T();
        propertiesLe_T_T();
        propertiesGe_T_T();
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

    private void propertiesCompare_Comparator_T_T() {
        initialize("compare(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(
                        P.withNull(P.integersGeometric()),
                        P.withNull(P.integersGeometric()),
                        P.withScale(4).integersGeometric()
                )
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            Comparator<Integer> comparator = (i, j) -> {
                if (!Objects.equals(i, t.a)) {
                    throw new IllegalArgumentException();
                }
                if (!Objects.equals(j, t.b)) {
                    throw new IllegalArgumentException();
                }
                return t.c;
            };
            Ordering o = compare(comparator, t.a, t.b);
            assertEquals(t, o, fromInt(comparator.compare(t.a, t.b)));

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    compare(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    compare(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, compare(naturalComparator, p.a, p.b), compare(p.a, p.b));
        }
    }

    private void propertiesEq_T_T() {
        initialize("eq(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = eq(p.a, p.b);
            assertEquals(p, b, eq(p.b, p.a));
            assertEquals(p, b, !lt(p.a, p.b) && !gt(p.a, p.b));
        }
    }

    private void propertiesNe_T_T() {
        initialize("ne(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = ne(p.a, p.b);
            assertEquals(p, b, ne(p.b, p.a));
            assertEquals(p, b, lt(p.a, p.b) || gt(p.a, p.b));
        }
    }

    private void propertiesLt_T_T() {
        initialize("lt(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = lt(p.a, p.b);
            assertEquals(p, b, !eq(p.a, p.b) && !gt(p.a, p.b));
        }
    }

    private void propertiesGt_T_T() {
        initialize("gt(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = gt(p.a, p.b);
            assertEquals(p, b, !eq(p.a, p.b) && !lt(p.a, p.b));
        }
    }

    private void propertiesLe_T_T() {
        initialize("le(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = le(p.a, p.b);
            assertEquals(p, b, eq(p.a, p.b) || le(p.a, p.b));
            assertEquals(p, b, ge(p.b, p.a));
        }
    }

    private void propertiesGe_T_T() {
        initialize("ge(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = ge(p.a, p.b);
            assertEquals(p, b, eq(p.a, p.b) || ge(p.a, p.b));
            assertEquals(p, b, le(p.b, p.a));
        }
    }
}
