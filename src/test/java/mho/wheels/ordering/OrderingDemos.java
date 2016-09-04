package mho.wheels.ordering;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Demos;

import java.util.Comparator;
import java.util.Objects;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

@SuppressWarnings("UnusedDeclaration")
public class OrderingDemos extends Demos {
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
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + compare(p.a, p.b));
        }
    }

    private void demoCompare_Comparator_T_T() {
        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> (t.c == 0) == Objects.equals(t.a, t.b),
                P.triples(
                        P.withNull(P.integers()),
                        P.withNull(P.integers()),
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

    private void demoToString() {
        for (Ordering o : take(LIMIT, P.orderings())) {
            System.out.println(o);
        }
    }
}
