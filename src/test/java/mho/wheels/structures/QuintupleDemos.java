package mho.wheels.structures;

import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class QuintupleDemos extends Demos {
    public QuintupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Quintuple<Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.quintuples(P.withNull(P.integers())))) {
            System.out.println("new Quintuple<Integer, Integer, Integer, Integer, Integer>(" + q.a + ", " + q.b +
                    ", " + q.c + ", " + q.d + ", " + q.e + ") = " + new Quintuple<>(q.a, q.b, q.c, q.d, q.e));
        }
    }

    private void demoToList() {
        for (Quintuple<Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.quintuples(P.withNull(P.integers())))) {
            System.out.println("toList" + q + " = " + Quintuple.toList(q));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(5, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Quintuple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<
                        Quintuple<Integer, Integer, Integer, Integer, Integer>,
                        Quintuple<Integer, Integer, Integer, Integer, Integer>
                >
        > qs = P.pairs(P.quintuples(P.integers()));
        for (Pair<
                Quintuple<Integer, Integer, Integer, Integer, Integer>,
                Quintuple<Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, qs)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Quintuple.compare(p.a, p.b));
        }
    }
}
