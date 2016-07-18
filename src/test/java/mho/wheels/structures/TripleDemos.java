package mho.wheels.structures;

import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class TripleDemos extends Demos {
    public TripleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            System.out.println("new Triple<Integer, Integer, Integer>(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    new Triple<>(t.a, t.b, t.c));
        }
    }

    private void demoToList() {
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            System.out.println("toList" + t + " = " + Triple.toList(t));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(3, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Triple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<
                        Triple<Integer, Integer, Integer>,
                        Triple<Integer, Integer, Integer>
                >
        > ps = P.pairs(P.triples(P.integers()));
        for (Pair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>> p : take(LIMIT, ps)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Triple.compare(p.a, p.b));
        }
    }

    private void demoEquals_Triple() {
        Iterable<Pair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>>> ps =
                P.pairs(P.triples(P.withNull(P.integers())));
        for (Pair<Triple<Integer, Integer, Integer>, Triple<Integer, Integer, Integer>> p : take(LIMIT, ps)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(t + (t.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            System.out.println("hashCode" + t + " = " + t.hashCode());
        }
    }
}
