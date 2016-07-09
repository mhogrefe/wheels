package mho.wheels.structures;

import mho.wheels.testing.Demos;

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
}
