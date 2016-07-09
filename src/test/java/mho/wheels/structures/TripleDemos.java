package mho.wheels.structures;

import mho.wheels.testing.Demos;

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
}
