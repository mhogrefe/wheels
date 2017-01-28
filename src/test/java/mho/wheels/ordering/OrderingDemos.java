package mho.wheels.ordering;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Demos;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class OrderingDemos extends Demos {
    private static final @NotNull String ORDERING_CHARS = "<=>";

    public OrderingDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoFromInt() {
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("fromInt(" + i + ") = " + fromInt(i));
        }
    }

    private void demoToInt() {
        for (Ordering o : take(LIMIT, P.orderings())) {
            System.out.println("toInt(" + o + ") = " + o.toInt());
        }
    }

    private void demoInvert() {
        for (Ordering o : take(LIMIT, P.orderings())) {
            System.out.println("invert(" + o + ") = " + o.invert());
        }
    }

    private void demoCompare_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + compare(p.a, p.b));
        }
    }

    private void demoCompare_Comparator_T_T() {
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
            System.out.println("compare(" + t.a + " " + fromInt(t.c) + " " + t.b + ", " + t.a + ", " + t.b + ") = " +
                    compare(comparator, t.a, t.b));
        }
    }

    private void demoEq_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (eq(p.a, p.b) ? "" : "not ") + "equal to " + p.b);
        }
    }

    private void demoNe_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (ne(p.a, p.b) ? "" : "not ") + "unequal to " + p.b);
        }
    }

    private void demoLt_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (lt(p.a, p.b) ? "" : "not ") + "less than " + p.b);
        }
    }

    private void demoGt_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (gt(p.a, p.b) ? "" : "not ") + "greater than " + p.b);
        }
    }

    private void demoLe_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (le(p.a, p.b) ? "" : "not ") + "less than or equal to " + p.b);
        }
    }

    private void demoGe_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println(p.a + " is " + (ge(p.a, p.b) ? "" : "not ") + "greater than or equal to " + p.b);
        }
    }

    private void demoEq_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (eq(comparator, t.a, t.b) ? "" : "not ") + "equal to " + t.b);
        }
    }

    private void demoNe_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (ne(comparator, t.a, t.b) ? "" : "not ") + "unequal to " + t.b);
        }
    }

    private void demoLt_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (lt(comparator, t.a, t.b) ? "" : "not ") + "less than " + t.b);
        }
    }

    private void demoGt_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (gt(comparator, t.a, t.b) ? "" : "not ") + "greater than " + t.b);
        }
    }

    private void demoLe_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (le(comparator, t.a, t.b) ? "" : "not ") + "less than or equal to " + t.b);
        }
    }

    private void demoGe_Comparator_T_T() {
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
            System.out.println(t.a + " " + fromInt(t.c) + " " + t.b + ": " + t.a + " is " +
                    (ge(comparator, t.a, t.b) ? "" : "not ") + "greater than or equal to " + t.b);
        }
    }

    private void demoMin_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println("min(" + p.a + ", " + p.b + ") = " + min(p.a, p.b));
        }
    }

    private void demoMax_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println("max(" + p.a + ", " + p.b + ") = " + max(p.a, p.b));
        }
    }

    private void demoMinMax_T_T() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integersGeometric()))) {
            System.out.println("minMax(" + p.a + ", " + p.b + ") = " + minMax(p.a, p.b));
        }
    }

    private void demoMin_Comparator_T_T() {
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
            System.out.println("min(" + t.a + " " + fromInt(t.c) + " " + t.b + ", " + t.a + ", " + t.b + ") = " +
                    min(comparator, t.a, t.b));
        }
    }

    private void demoMax_Comparator_T_T() {
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
            System.out.println("max(" + t.a + " " + fromInt(t.c) + " " + t.b + ", " + t.a + ", " + t.b + ") = " +
                    max(comparator, t.a, t.b));
        }
    }

    private void demoMinMax_Comparator_T_T() {
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
            System.out.println("minMax(" + t.a + " " + fromInt(t.c) + " " + t.b + ", " + t.a + ", " + t.b + ") = " +
                    minMax(comparator, t.a, t.b));
        }
    }

    private void demoMinimum_Iterable_T() {
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            System.out.println("minimum(" + middle(xs.toString()) + ") = " + minimum(xs));
        }
    }

    private void demoMaximum_Iterable_T() {
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            System.out.println("maximum(" + middle(xs.toString()) + ") = " + maximum(xs));
        }
    }

    private void demoMinimumMaximum_Iterable_T() {
        for (List<Integer> xs : take(LIMIT, P.listsAtLeast(1, P.integersGeometric()))) {
            System.out.println("minimumMaximum(" + middle(xs.toString()) + ") = " + minimumMaximum(xs));
        }
    }

    private void demoMinimum_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": minimum(" +
                    middle(p.a.toString()) + ") = " + minimum(comparator, p.a));
        }
    }

    private void demoMaximum_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": maximum(" +
                    middle(p.a.toString()) + ") = " + maximum(comparator, p.a));
        }
    }

    private void demoMinimumMaximum_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": minimumMaximum(" +
                    middle(p.a.toString()) + ") = " + minimumMaximum(comparator, p.a));
        }
    }

    private void demoMinimum_String() {
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            System.out.println("minimum(" + nicePrint(s) + ") = " + nicePrint(minimum(s)));
        }
    }

    private void demoMaximum_String() {
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            System.out.println("maximum(" + nicePrint(s) + ") = " + nicePrint(maximum(s)));
        }
    }

    private void demoMinimumMaximum_String() {
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            Pair<Character, Character> result = minimumMaximum(s);
            System.out.println("minimumMaximum(" + nicePrint(s) + ") = " +
                    new Pair<>(nicePrint(result.a), nicePrint(result.b)));
        }
    }

    private void demoMinimum_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).stringsAtLeast(1),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": minimum(" +
                    nicePrint(p.a) + ") = " + nicePrint(minimum(comparator, p.a)));
        }
    }

    private void demoMaximum_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).stringsAtLeast(1),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": minimum(" +
                    nicePrint(p.a) + ") = " + nicePrint(maximum(comparator, p.a)));
        }
    }

    private void demoMinimumMaximum_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).stringsAtLeast(1),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": minimum(" +
                    nicePrint(p.a) + ") = " + nicePrint(minimumMaximum(comparator, p.a).toString()));
        }
    }

    private void demoIncreasing_Iterable() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println(xs + " is " + (increasing(xs) ? "" : "not ") + "increasing");
        }
    }

    private void demoDecreasing_Iterable() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println(xs + " is " + (decreasing(xs) ? "" : "not ") + "decreasing");
        }
    }

    private void demoWeaklyIncreasing_Iterable() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println(xs + " is " + (weaklyIncreasing(xs) ? "" : "not ") + "weakly increasing");
        }
    }

    private void demoWeaklyDecreasing_Iterable() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println(xs + " is " + (weaklyDecreasing(xs) ? "" : "not ") + "weakly decreasing");
        }
    }

    private void demoZigzagging_Iterable() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println(xs + " is " + (zigzagging(xs) ? "" : "not ") + "zigzagging");
        }
    }

    private void demoIncreasing_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": " + p.a + " is " +
                    (increasing(comparator, p.a) ? "" : "not ") + "increasing");
        }
    }

    private void demoDecreasing_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": " + p.a + " is " +
                    (decreasing(comparator, p.a) ? "" : "not ") + "decreasing");
        }
    }

    private void demoWeaklyIncreasing_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": " + p.a + " is " +
                    (weaklyIncreasing(comparator, p.a) ? "" : "not ") + "weakly increasing");
        }
    }

    private void demoWeaklyDecreasing_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": " + p.a + " is " +
                    (weaklyDecreasing(comparator, p.a) ? "" : "not ") + "weakly decreasing");
        }
    }

    private void demoZigzagging_Comparator_Iterable_T() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.dependentPairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                is -> P.permutationsFinite(toList(nub(is)))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Objects::toString, p.b)) + ": " + p.a + " is " +
                    (zigzagging(comparator, p.a) ? "" : "not ") + "zigzagging");
        }
    }

    private void demoIncreasing_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(nicePrint(s) + " is " + (increasing(s) ? "" : "not ") + "increasing");
        }
    }

    private void demoDecreasing_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(nicePrint(s) + " is " + (decreasing(s) ? "" : "not ") + "decreasing");
        }
    }

    private void demoWeaklyIncreasing_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(nicePrint(s) + " is " + (weaklyIncreasing(s) ? "" : "not ") + "weakly increasing");
        }
    }

    private void demoWeaklyDecreasing_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(nicePrint(s) + " is " + (weaklyDecreasing(s) ? "" : "not ") + "weakly decreasing");
        }
    }

    private void demoZigzagging_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(nicePrint(s) + " is " + (zigzagging(s) ? "" : "not ") + "zigzagging");
        }
    }

    private void demoIncreasing_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).strings(),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": " + nicePrint(p.a) +
                    " is " + (increasing(p.b) ? "" : "not ") + "increasing");
        }
    }

    private void demoDecreasing_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).strings(),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": " + nicePrint(p.a) +
                    " is " + (decreasing(p.b) ? "" : "not ") + "decreasing");
        }
    }

    private void demoWeaklyIncreasing_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).strings(),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": " + nicePrint(p.a) +
                    " is " + (weaklyIncreasing(p.b) ? "" : "not ") + "weakly increasing");
        }
    }

    private void demoWeaklyDecreasing_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).strings(),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": " + nicePrint(p.a) +
                    " is " + (weaklyDecreasing(p.b) ? "" : "not ") + "weakly decreasing");
        }
    }

    private void demoZigzagging_Comparator_String() {
        Iterable<Pair<String, String>> ps = P.dependentPairs(
                P.withScale(4).strings(),
                s -> map(IterableUtils::charsToString, P.permutationsFinite(toList(nub(s))))
        );
        for (Pair<String, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = (x, y) -> {
                Integer xIndex = p.b.indexOf(x);
                if (xIndex == -1) {
                    throw new IllegalArgumentException();
                }
                Integer yIndex = p.b.indexOf(y);
                if (yIndex == -1) {
                    throw new IllegalArgumentException();
                }
                return Integer.compare(xIndex, yIndex);
            };
            System.out.println(intercalate(" < ", map(Testing::nicePrint, fromString(p.b))) + ": " + nicePrint(p.a) +
                    " is " + (zigzagging(p.b) ? "" : "not ") + "zigzagging");
        }
    }

    private void demoReadOrderingStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrderingStrict(" + nicePrint(s) + ") = " + readStrict(s));
        }
    }

    private void demoReadOrderingStrict_targeted() {
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("readOrderingStrict(" + s + ") = " + readStrict(s));
        }
    }

    private void demoToString() {
        for (Ordering o : take(LIMIT, P.orderings())) {
            System.out.println(o);
        }
    }
}
