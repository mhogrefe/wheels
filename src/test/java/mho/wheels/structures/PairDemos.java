package mho.wheels.structures;

import mho.wheels.testing.Demos;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class PairDemos extends Demos {
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
}
