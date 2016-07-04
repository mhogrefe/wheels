package mho.wheels.structures;

import mho.wheels.testing.Demos;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class SeptupleDemos extends Demos {
    public SeptupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("new Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>(" + s.a +
                    ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    new Septuple<>(s.a, s.b, s.c, s.d, s.e, s.f, s.g));
        }
    }
}
