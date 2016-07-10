package mho.wheels.structures;

import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class SextupleDemos extends Demos {
    public SextupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.sextuples(P.withNull(P.integers())))) {
            System.out.println("new Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>(" + s.a + ", " +
                    s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ") = " +
                    new Sextuple<>(s.a, s.b, s.c, s.d, s.e, s.f));
        }
    }

    private void demoToList() {
        for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.sextuples(P.withNull(P.integers())))) {
            System.out.println("toList" + s + " = " + Sextuple.toList(s));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(6, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Sextuple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<
                        Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>,
                        Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>
                >
        > ss = P.pairs(P.sextuples(P.integers()));
        for (Pair<
                Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>,
                Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, ss)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Sextuple.compare(p.a, p.b));
        }
    }
}
