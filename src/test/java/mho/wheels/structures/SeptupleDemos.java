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
public class SeptupleDemos extends Demos {
    private static final @NotNull String NULLABLE_INTEGER_SEPTUPLE_CHARS = " (),-0123456789lnu";

    public SeptupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("new Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>(" + s.a +
                    ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    new Septuple<>(s.a, s.b, s.c, s.d, s.e, s.f, s.g));
        }
    }

    private void demoToList() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("toList" + s + " = " + Septuple.toList(s));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(7, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Septuple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                >
        > ss = P.pairs(P.septuples(P.integers()));
        for (Pair<
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, ss)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Septuple.compare(p.a, p.b));
        }
    }

    private void demoEquals_Septuple() {
        Iterable<
                Pair<
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                >
        > ps = P.pairs(P.septuples(P.withNull(P.integers())));
        for (Pair<
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, ps)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(s + (s.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("hashCode" + q + " = " + q.hashCode());
        }
    }

    private void demoReadStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Septuple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoReadStrict_targeted() {
        for (String s : take(LIMIT, P.strings(NULLABLE_INTEGER_SEPTUPLE_CHARS))) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Septuple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoSeptupleComparator_compare() {
        Iterable<
                Pair<
                        Pair<
                                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                                >,
                        Septuple<
                                List<Integer>,
                                List<Integer>,
                                List<Integer>,
                                List<Integer>,
                                List<Integer>,
                                List<Integer>,
                                List<Integer>
                                >
                        >
                > ps = P.dependentPairs(
                P.pairs(P.septuples(P.withNull(P.integersGeometric()))),
                p -> P.septuples(
                        EP.permutationsFinite(toList(nub(concat(Septuple.toList(p.a), Septuple.toList(p.b)))))
                )
        );
        for (Pair<
                Pair<
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                        >,
                Septuple<
                        List<Integer>,
                        List<Integer>,
                        List<Integer>,
                        List<Integer>,
                        List<Integer>,
                        List<Integer>,
                        List<Integer>
                        >
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
            Comparator<Integer> eComparator = (x, y) -> {
                int xIndex = p.b.e.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.e.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            Comparator<Integer> fComparator = (x, y) -> {
                int xIndex = p.b.f.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.f.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            Comparator<Integer> gComparator = (x, y) -> {
                int xIndex = p.b.g.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                int yIndex = p.b.g.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException("undefined");
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println("new SeptupleComparator(" +
                    intercalate(" < ", map(Objects::toString, p.b.a)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.b)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.c)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.d)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.e)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.f)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.g)) +
                    "): " + p.a.a + " " +
                    Ordering.fromInt(
                            new Septuple.SeptupleComparator<>(
                                    aComparator,
                                    bComparator,
                                    cComparator,
                                    dComparator,
                                    eComparator,
                                    fComparator,
                                    gComparator
                            ).compare(p.a.a, p.a.b)
                    ) + " " + p.a.b);
        }
    }
}
