package mho.wheels.structures;

import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class QuadrupleDemos extends Demos {
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
}
