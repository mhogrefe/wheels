package mho.wheels.ordering;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.ordering.comparators.ListBasedComparator;
import mho.wheels.ordering.comparators.StringBasedComparator;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
        propertiesMin_Comparator_T_T();
        propertiesMax_Comparator_T_T();
        propertiesMinMax_Comparator_T_T();
        propertiesMinimum_Iterable_T();
        propertiesMaximum_Iterable_T();
        propertiesMinimumMaximum_Iterable_T();
        propertiesMinimum_Comparator_Iterable_T();
        propertiesMaximum_Comparator_Iterable_T();
        propertiesMinimumMaximum_Comparator_Iterable_T();
        propertiesMinimum_String();
        propertiesMaximum_String();
        propertiesMinimumMaximum_String();
        propertiesMinimum_Comparator_String();
        propertiesMaximum_Comparator_String();
        propertiesMinimumMaximum_Comparator_String();
        propertiesIncreasing_Iterable();
        propertiesDecreasing_Iterable();
        propertiesWeaklyIncreasing_Iterable();
        propertiesWeaklyDecreasing_Iterable();
        propertiesZigzagging_Iterable();
        propertiesIncreasing_Comparator_Iterable_T();
        propertiesDecreasing_Comparator_Iterable_T();
        propertiesWeaklyIncreasing_Comparator_Iterable_T();
        propertiesWeaklyDecreasing_Comparator_Iterable_T();
        propertiesZigzagging_Comparator_Iterable_T();
        propertiesIncreasing_String();
        propertiesDecreasing_String();
        propertiesWeaklyIncreasing_String();
        propertiesWeaklyDecreasing_String();
        propertiesZigzagging_String();
        propertiesIncreasing_Comparator_String();
        propertiesDecreasing_Comparator_String();
        propertiesWeaklyIncreasing_Comparator_String();
        propertiesWeaklyDecreasing_Comparator_String();
        propertiesZigzagging_Comparator_String();
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

    private void propertiesMin_Comparator_T_T() {
        initialize("min(Comparator, T, T)");
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

    private void propertiesMax_Comparator_T_T() {
        initialize("max(Comparator, T, T)");
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

    private void propertiesMinMax_Comparator_T_T() {
        initialize("minMax(Comparator, T, T)");
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

    private void propertiesMinimum_Iterable_T() {
        initialize("minimum(Iterable<T>)");
        propertiesFoldHelper(LIMIT, P, P.integersGeometric(), Ordering::min, Ordering::minimum, x -> {}, false, true);
    }

    private void propertiesMaximum_Iterable_T() {
        initialize("maximum(Iterable<T>)");
        propertiesFoldHelper(LIMIT, P, P.integersGeometric(), Ordering::max, Ordering::maximum, x -> {}, false, true);
    }

    private void propertiesMinimumMaximum_Iterable_T() {
        initialize("minimumMaximum(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            minimumMaximum(xs);
        }

        Iterable<Pair<List<Integer>, List<Integer>>> ps = filterInfinite(
                q -> !q.a.equals(q.b),
                P.dependentPairs(P.listsAtLeast(1, P.integersGeometric()), P::permutationsFinite)
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertEquals(p, minimumMaximum(p.a), minimumMaximum(p.b));
        }

        for (int x : take(LIMIT, P.integersGeometric())) {
            assertEquals(x, minimumMaximum(Collections.singletonList(x)), new Pair<>(x, x));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            assertEquals(p, minimumMaximum(Arrays.asList(p.a, p.b)), minMax(p.a, p.b));
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> !ys.isEmpty(),
                P.listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> lxs : take(LIMIT, xsFail)) {
            try {
                minimumMaximum(lxs);
                fail(lxs);
            } catch (NullPointerException | IllegalArgumentException ignored) {}
        }
    }

    private void propertiesMinimum_Comparator_Iterable_T() {
        initialize("mininum(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            minimum(comparator, p.a);

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    minimum(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            assertEquals(xs, minimum(naturalComparator, xs), minimum(xs));
        }
    }

    private void propertiesMaximum_Comparator_Iterable_T() {
        initialize("maximum(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            maximum(comparator, p.a);

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    maximum(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            assertEquals(xs, maximum(naturalComparator, xs), maximum(xs));
        }
    }

    private void propertiesMinimumMaximum_Comparator_Iterable_T() {
        initialize("minimumMaximum(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            minimumMaximum(comparator, p.a);

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    minimumMaximum(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            assertEquals(xs, minimumMaximum(naturalComparator, xs), minimumMaximum(xs));
        }
    }

    private void propertiesMinimum_String() {
        initialize("minimum(String)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            minimum(s);
        }

        Iterable<Pair<String, String>> ps = filterInfinite(
                q -> !q.a.equals(q.b),
                P.dependentPairs(P.stringsAtLeast(1), P::stringPermutations)
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            assertEquals(p, minimum(p.a), minimum(p.b));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertEquals(c, minimum(Character.toString(c)), c);
        }

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            assertEquals(p, minimum("" + p.a + p.b), min(p.a, p.b));
        }
    }

    private void propertiesMaximum_String() {
        initialize("maximum(String)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            maximum(s);
        }

        Iterable<Pair<String, String>> ps = filterInfinite(
                q -> !q.a.equals(q.b),
                P.dependentPairs(P.stringsAtLeast(1), P::stringPermutations)
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            assertEquals(p, maximum(p.a), maximum(p.b));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertEquals(c, maximum(Character.toString(c)), c);
        }

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            assertEquals(p, maximum("" + p.a + p.b), max(p.a, p.b));
        }
    }

    private void propertiesMinimumMaximum_String() {
        initialize("minimumMaximum(String)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            minimumMaximum(s);
        }

        Iterable<Pair<String, String>> ps = filterInfinite(
                q -> !q.a.equals(q.b),
                P.dependentPairs(P.stringsAtLeast(1), P::stringPermutations)
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            assertEquals(p, minimumMaximum(p.a), minimumMaximum(p.b));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertEquals(c, minimumMaximum(Character.toString(c)), new Pair<>(c, c));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            assertEquals(p, minimumMaximum("" + p.a + p.b), minMax(p.a, p.b));
        }
    }

    private void propertiesMinimum_Comparator_String() {
        initialize("minimum(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.stringsAtLeast(1), s -> P.stringPermutations(nub(s)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            minimum(comparator, p.a);

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    minimum(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            assertEquals(s, minimum(naturalComparator, s), minimum(s));
        }
    }

    private void propertiesMaximum_Comparator_String() {
        initialize("maximum(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.stringsAtLeast(1), s -> P.stringPermutations(nub(s)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            maximum(comparator, p.a);

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    maximum(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            assertEquals(s, maximum(naturalComparator, s), maximum(s));
        }
    }

    private void propertiesMinimumMaximum_Comparator_String() {
        initialize("minimumMaximum(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.stringsAtLeast(1), s -> P.stringPermutations(nub(s)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            minimumMaximum(comparator, p.a);

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    minimumMaximum(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            assertEquals(s, minimumMaximum(naturalComparator, s), minimumMaximum(s));
        }
    }

    private void propertiesIncreasing_Iterable() {
        initialize("increasing(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, increasing(xs), decreasing(reverse(xs)));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, increasing(Collections.singletonList(i)));
        }
    }

    private void propertiesDecreasing_Iterable() {
        initialize("decreasing(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, decreasing(xs), increasing(reverse(xs)));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, decreasing(Collections.singletonList(i)));
        }
    }

    private void propertiesWeaklyIncreasing_Iterable() {
        initialize("weaklyIncreasing(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, weaklyIncreasing(xs), weaklyDecreasing(reverse(xs)));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, weaklyIncreasing(Collections.singletonList(i)));
        }
    }

    private void propertiesWeaklyDecreasing_Iterable() {
        initialize("weaklyDecreasing(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, weaklyDecreasing(xs), weaklyIncreasing(reverse(xs)));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, weaklyDecreasing(Collections.singletonList(i)));
        }
    }

    private void propertiesZigzagging_Iterable() {
        initialize("zigzagging(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, zigzagging(xs), zigzagging(reverse(xs)));
        }

        for (int i : take(LIMIT, P.integersGeometric())) {
            assertTrue(i, zigzagging(Collections.singletonList(i)));
        }
    }

    private void propertiesIncreasing_Comparator_Iterable_T() {
        initialize("increasing(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.lists(P.integersGeometric()),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, increasing(comparator, p.a), decreasing(comparator, reverse(p.a)));

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    increasing(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, increasing(naturalComparator, xs), increasing(xs));
        }
    }

    private void propertiesDecreasing_Comparator_Iterable_T() {
        initialize("decreasing(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.lists(P.integersGeometric()),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, decreasing(comparator, p.a), increasing(comparator, reverse(p.a)));

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    decreasing(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, decreasing(naturalComparator, xs), decreasing(xs));
        }
    }

    private void propertiesWeaklyIncreasing_Comparator_Iterable_T() {
        initialize("weaklyIncreasing(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.lists(P.integersGeometric()),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, weaklyIncreasing(comparator, p.a), weaklyDecreasing(comparator, reverse(p.a)));

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    weaklyIncreasing(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, weaklyIncreasing(naturalComparator, xs), weaklyIncreasing(xs));
        }
    }

    private void propertiesWeaklyDecreasing_Comparator_Iterable_T() {
        initialize("weaklyDecreasing(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.lists(P.integersGeometric()),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, weaklyDecreasing(comparator, p.a), weaklyIncreasing(comparator, reverse(p.a)));

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    weaklyDecreasing(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, weaklyDecreasing(naturalComparator, xs), weaklyDecreasing(xs));
        }
    }

    private void propertiesZigzagging_Comparator_Iterable_T() {
        initialize("zigzagging(Comparator, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.lists(P.integersGeometric()),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, zigzagging(comparator, p.a), zigzagging(comparator, reverse(p.a)));

            Iterable<List<Integer>> xss = filterInfinite(
                    ys -> !any(ys::contains, p.a),
                    P.listsAtLeast(2, P.withNull(P.integersGeometric()))
            );
            for (List<Integer> xs : take(TINY_LIMIT, xss)) {
                try {
                    zigzagging(comparator, xs);
                    fail(xs);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Integer> naturalComparator = Comparator.naturalOrder();
        for (List<Integer> xs : take(LIMIT, P.lists(P.integersGeometric()))) {
            assertEquals(xs, zigzagging(naturalComparator, xs), zigzagging(xs));
        }
    }

    private void propertiesIncreasing_String() {
        initialize("increasing(String)");
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, increasing(s), decreasing(reverse(s)));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(c, increasing(Character.toString(c)));
        }
    }

    private void propertiesDecreasing_String() {
        initialize("decreasing(String)");
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, decreasing(s), increasing(reverse(s)));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(c, decreasing(Character.toString(c)));
        }
    }

    private void propertiesWeaklyIncreasing_String() {
        initialize("weaklyIncreasing(String)");
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, weaklyIncreasing(s), weaklyDecreasing(reverse(s)));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(c, weaklyIncreasing(Character.toString(c)));
        }
    }

    private void propertiesWeaklyDecreasing_String() {
        initialize("weaklyDecreasing(String)");
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, weaklyDecreasing(s), weaklyIncreasing(reverse(s)));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(c, weaklyDecreasing(Character.toString(c)));
        }
    }

    private void propertiesZigzagging_String() {
        initialize("zigzagging(String)");
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, zigzagging(s), zigzagging(reverse(s)));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(c, zigzagging(Character.toString(c)));
        }
    }

    private void propertiesIncreasing_Comparator_String() {
        initialize("increasing(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.strings(), is -> P.stringPermutations(nub(is)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            assertEquals(p, increasing(comparator, p.a), decreasing(comparator, reverse(p.a)));

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    increasing(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, increasing(naturalComparator, s), increasing(s));
        }
    }

    private void propertiesDecreasing_Comparator_String() {
        initialize("decreasing(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.strings(), is -> P.stringPermutations(nub(is)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            assertEquals(p, decreasing(comparator, p.a), increasing(comparator, reverse(p.a)));

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    decreasing(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, decreasing(naturalComparator, s), decreasing(s));
        }
    }

    private void propertiesWeaklyIncreasing_Comparator_String() {
        initialize("weaklyIncreasing(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.strings(), is -> P.stringPermutations(nub(is)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            assertEquals(p, weaklyIncreasing(comparator, p.a), weaklyDecreasing(comparator, reverse(p.a)));

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    weaklyIncreasing(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, weaklyIncreasing(naturalComparator, s), weaklyIncreasing(s));
        }
    }

    private void propertiesWeaklyDecreasing_Comparator_String() {
        initialize("weaklyDecreasing(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.strings(), is -> P.stringPermutations(nub(is)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            assertEquals(p, weaklyDecreasing(comparator, p.a), weaklyIncreasing(comparator, reverse(p.a)));

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    weaklyDecreasing(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, weaklyDecreasing(naturalComparator, s), weaklyDecreasing(s));
        }
    }

    private void propertiesZigzagging_Comparator_String() {
        initialize("zigzagging(Comparator, String)");
        Iterable<Pair<String, String>> ps = P.dependentPairs(P.strings(), is -> P.stringPermutations(nub(is)));
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new StringBasedComparator(p.b);
            assertEquals(p, zigzagging(comparator, p.a), zigzagging(comparator, reverse(p.a)));

            for (String s : take(TINY_LIMIT, filterInfinite(s -> !any(c -> elem(c, s), p.a), P.stringsAtLeast(2)))) {
                try {
                    zigzagging(comparator, s);
                    fail(s);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        Comparator<Character> naturalComparator = Comparator.naturalOrder();
        for (String s : take(LIMIT, P.strings())) {
            assertEquals(s, zigzagging(naturalComparator, s), zigzagging(s));
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
