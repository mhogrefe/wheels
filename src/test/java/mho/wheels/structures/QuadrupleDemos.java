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
}
