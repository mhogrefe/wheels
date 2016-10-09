package mho.wheels.ordering;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class OrderingProperties extends TestProperties {
    private static final @NotNull String ORDERING_CHARS = "<=>";

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
        propertiesEq_Comparator_T_T();
        propertiesNe_Comparator_T_T();
        propertiesLt_Comparator_T_T();
        propertiesGt_Comparator_T_T();
        propertiesLe_Comparator_T_T();
        propertiesGe_Comparator_T_T();
        propertiesMin_T_T();
        propertiesMax_T_T();
        propertiesMinMax_T_T();
        propertiesMin_Ordering_T_T();
        propertiesMax_Ordering_T_T();
        propertiesMinMax_Ordering_T_T();
        propertiesReadStrict();
        propertiesToString();
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
            antiCommutative(Ordering::compare, Ordering::invert, p);
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
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            compare(comparator, t.a, t.b);

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
            assertEquals(p, b, !lt(p.a, p.b) && !gt(p.a, p.b));
            //noinspection Convert2MethodRef
            commutative((x, y) -> eq(x, y), p);
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, eq(i, i));
        }
    }

    private void propertiesNe_T_T() {
        initialize("ne(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = ne(p.a, p.b);
            assertEquals(p, b, lt(p.a, p.b) || gt(p.a, p.b));
            //noinspection Convert2MethodRef
            commutative((x, y) -> ne(x, y), p);
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertFalse(i, ne(i, i));
        }
    }

    private void propertiesLt_T_T() {
        initialize("lt(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = lt(p.a, p.b);
            assertEquals(p, b, !eq(p.a, p.b) && !gt(p.a, p.b));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertFalse(i, lt(i, i));
        }
    }

    private void propertiesGt_T_T() {
        initialize("gt(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = gt(p.a, p.b);
            assertEquals(p, b, !eq(p.a, p.b) && !lt(p.a, p.b));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertFalse(i, gt(i, i));
        }
    }

    private void propertiesLe_T_T() {
        initialize("le(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = le(p.a, p.b);
            assertEquals(p, b, eq(p.a, p.b) || lt(p.a, p.b));
            assertEquals(p, b, ge(p.b, p.a));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, le(i, i));
        }
    }

    private void propertiesGe_T_T() {
        initialize("ge(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            boolean b = ge(p.a, p.b);
            assertEquals(p, b, eq(p.a, p.b) || gt(p.a, p.b));
            assertEquals(p, b, le(p.b, p.a));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, ge(i, i));
        }
    }

    private void propertiesEq_Comparator_T_T() {
        initialize("eq(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            eq(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    eq(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    eq(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, eq(naturalComparator, p.a, p.b), eq(p.a, p.b));
        }
    }

    private void propertiesNe_Comparator_T_T() {
        initialize("ne(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            ne(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    ne(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    ne(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, ne(naturalComparator, p.a, p.b), ne(p.a, p.b));
        }
    }

    private void propertiesLt_Comparator_T_T() {
        initialize("lt(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            lt(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    lt(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    lt(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, lt(naturalComparator, p.a, p.b), lt(p.a, p.b));
        }
    }

    private void propertiesGt_Comparator_T_T() {
        initialize("gt(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            gt(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    gt(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    gt(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, gt(naturalComparator, p.a, p.b), gt(p.a, p.b));
        }
    }

    private void propertiesLe_Comparator_T_T() {
        initialize("le(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            le(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    le(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    le(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, le(naturalComparator, p.a, p.b), le(p.a, p.b));
        }
    }

    private void propertiesGe_Comparator_T_T() {
        initialize("ge(Comparator, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            ge(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    ge(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    ge(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, eq(naturalComparator, p.a, p.b), eq(p.a, p.b));
        }
    }

    private void propertiesMin_T_T() {
        initialize("min(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            int min = min(p.a, p.b);
            assertTrue(p, p.a.equals(min) || p.b.equals(min));
            assertTrue(p, le(min, p.a));
            assertTrue(p, le(min, p.b));
            commutative(Ordering::min, p);
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            fixedPoint(j -> min(j, j), i);
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.integersGeometric()))) {
            associative(Ordering::min, t);
        }
    }

    private void propertiesMax_T_T() {
        initialize("max(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            int max = max(p.a, p.b);
            assertTrue(p, p.a.equals(max) || p.b.equals(max));
            assertTrue(p, ge(max, p.a));
            assertTrue(p, ge(max, p.b));
            commutative(Ordering::max, p);
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            fixedPoint(j -> max(j, j), i);
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.integersGeometric()))) {
            associative(Ordering::max, t);
        }
    }

    private void propertiesMinMax_T_T() {
        initialize("minMax(T, T)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            Pair<Integer, Integer> minMax = minMax(p.a, p.b);
            assertEquals(p, minMax.a, min(p.a, p.b));
            assertEquals(p, minMax.b, max(p.a, p.b));
            assertTrue(p, le(minMax.a, minMax.b));
            commutative(Ordering::minMax, p);
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertEquals(i, minMax(i, i), new Pair<>(i, i));
        }
    }

    private void propertiesMin_Ordering_T_T() {
        initialize("min(Ordering, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            min(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    min(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    min(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, min(naturalComparator, p.a, p.b), min(p.a, p.b));
        }
    }

    private void propertiesMax_Ordering_T_T() {
        initialize("max(Ordering, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            max(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    max(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    max(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, max(naturalComparator, p.a, p.b), max(p.a, p.b));
        }
    }

    private void propertiesMinMax_Ordering_T_T() {
        initialize("minMax(Ordering, T, T)");
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(P.withNull(P.integersGeometric()), P.withNull(P.integersGeometric()), P.integersGeometric())
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
            minMax(comparator, t.a, t.b);

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.a), P.integersGeometric()))) {
                try {
                    minMax(comparator, i, t.b);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            for (int i : take(TINY_LIMIT, filterInfinite(j -> !j.equals(t.b), P.integersGeometric()))) {
                try {
                    minMax(comparator, t.a, i);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, minMax(naturalComparator, p.a, p.b), minMax(p.a, p.b));
        }
    }

    private void propertiesReadStrict() {
        initialize("readStrict(String)");
        propertiesReadHelper(LIMIT, P, ORDERING_CHARS, P.orderings(), Ordering::readStrict, o -> {}, false, true);
    }

    private void propertiesToString() {
        initialize("toString()");
        propertiesToStringHelper(LIMIT, "<=>", P.orderings(), Ordering::readStrict);
    }
}
