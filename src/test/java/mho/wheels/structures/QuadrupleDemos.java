package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.ordering.Ordering;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class QuadrupleDemos extends Demos {
    private static final @NotNull String NULLABLE_INTEGER_QUADRUPLE_CHARS = " (),-0123456789lnu";

    public QuadrupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            System.out.println("new Quadruple<Integer, Integer, Integer, Integer>(" + q.a + ", " + q.b + ", " + q.c +
                    ", " + q.d + ") = " + new Quadruple<>(q.a, q.b, q.c, q.d));
        }
    }

    private void demoToList() {
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            System.out.println("toList" + q + " = " + Quadruple.toList(q));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(4, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Quadruple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<Quadruple<Integer, Integer, Integer, Integer>, Quadruple<Integer, Integer, Integer, Integer>>
        > ps = P.pairs(P.quadruples(P.integers()));
        for (Pair<Quadruple<Integer, Integer, Integer, Integer>, Quadruple<Integer, Integer, Integer, Integer>> p :
                take(LIMIT, ps)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Quadruple.compare(p.a, p.b));
        }
    }

    private void demoEquals_Quadruple() {
        Iterable<
                Pair<Quadruple<Integer, Integer, Integer, Integer>, Quadruple<Integer, Integer, Integer, Integer>>
        > ps = P.pairs(P.quadruples(P.withNull(P.integers())));
        for (Pair<Quadruple<Integer, Integer, Integer, Integer>, Quadruple<Integer, Integer, Integer, Integer>> p :
                take(LIMIT, ps)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(q + (q.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            System.out.println("hashCode" + q + " = " + q.hashCode());
        }
    }

    private void demoReadStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Quadruple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoReadStrict_targeted() {
        for (String s : take(LIMIT, P.strings(NULLABLE_INTEGER_QUADRUPLE_CHARS))) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Quadruple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoQuadrupleComparator_compare() {
        Iterable<
                Pair<
                        Pair<
                                Quadruple<Integer, Integer, Integer, Integer>,
                                Quadruple<Integer, Integer, Integer, Integer>
                        >,
                        Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>
                >
        > ps = P.dependentPairs(
                P.pairs(P.quadruples(P.withNull(P.integersGeometric()))),
                p -> P.quadruples(
                        EP.permutationsFinite(toList(nub(concat(Quadruple.toList(p.a), Quadruple.toList(p.b)))))
                )
        );
        for (Pair<
                Pair<Quadruple<Integer, Integer, Integer, Integer>, Quadruple<Integer, Integer, Integer, Integer>>,
                Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>
        > p : take(MEDIUM_LIMIT, ps)) {
            Comparator<Integer> aComparator = (x, y) -> {
                int xIndex = p.b.a.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.a.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            Comparator<Integer> bComparator = (x, y) -> {
                int xIndex = p.b.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            Comparator<Integer> cComparator = (x, y) -> {
                int xIndex = p.b.c.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.c.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            Comparator<Integer> dComparator = (x, y) -> {
                int xIndex = p.b.d.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.d.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println("new QuadrupleComparator(" +
                    intercalate(" < ", map(Objects::toString, p.b.a)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.b)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.c)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.d)) +
                    "): " + p.a.a + " " +
                    Ordering.fromInt(
                            new Quadruple.QuadrupleComparator<>(
                                    aComparator,
                                    bComparator,
                                    cComparator,
                                    dComparator
                            ).compare(p.a.a, p.a.b)
                    ) + " " + p.a.b);
        }
    }
}
