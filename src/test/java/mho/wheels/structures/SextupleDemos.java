package mho.wheels.structures;

import mho.wheels.testing.Demos;

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
}
