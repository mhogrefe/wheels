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
public class PairDemos extends Demos {
    private static final @NotNull String NULLABLE_INTEGER_PAIR_CHARS = " (),-0123456789lnu";

    public PairDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            System.out.println("new Pair<Integer, Integer>(" + p.a + ", " + p.b + ") = " + new Pair<>(p.a, p.b));
        }
    }

    private void demoToList() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            System.out.println("toList" + p + " = " + Pair.toList(p));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(2, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Pair.fromList(xs));
        }
    }

    private void demoCompare() {
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p : take(LIMIT, P.pairs(P.pairs(P.integers())))) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Pair.compare(p.a, p.b));
        }
    }

    private void demoEquals_Pair() {
        Iterable<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> ps = P.pairs(P.pairs(P.withNull(P.integers())));
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p : take(LIMIT, ps)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(p + (p.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            System.out.println("hashCode" + p + " = " + p.hashCode());
        }
    }

    private void demoReadStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Pair.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoReadStrict_targeted() {
        for (String s : take(LIMIT, P.strings(NULLABLE_INTEGER_PAIR_CHARS))) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Pair.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoPairComparator_compare() {
        Iterable<Pair<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<List<Integer>, List<Integer>>>> ps =
                P.dependentPairs(
                        P.pairs(P.pairs(P.withNull(P.integersGeometric()))),
                        p -> P.pairs(EP.permutationsFinite(toList(nub(concat(Pair.toList(p.a), Pair.toList(p.b))))))
                );
        for (Pair<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<List<Integer>, List<Integer>>> p :
                take(LIMIT, ps)) {
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
            System.out.println("new PairComparator(" +
                    intercalate(" < ", map(Objects::toString, p.b.a)) + ", " +
                    intercalate(" < ", map(Objects::toString, p.b.b)) +
                    "): " + p.a.a + " " +
                    Ordering.fromInt(new Pair.PairComparator<>(aComparator, bComparator).compare(p.a.a, p.a.b)) + " " +
                    p.a.b);
        }
    }
}
