package mho.wheels.iterables;

import mho.wheels.structures.Pair;

import static mho.wheels.iterables.IterableUtils.*;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class RandomProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(0x6af477d9a7e54fcaL);
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoConstructor() {
        initialize();
        for (Void v : take(LIMIT, repeat((Void) null))) {
            System.out.println("RandomProvider() = " + new RandomProvider());
        }
    }

    private static void demoConstructor_int() {
        initialize();
        for (long l : take(LIMIT, P.longs())) {
            System.out.println("RandomProvider(" + l + ") = " + new RandomProvider(l));
        }
    }

    private static void demoGetScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getScale(" + rp + ") = " + rp.getScale());
        }
    }

    private static void demoGetSecondaryScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSecondaryScale(" + rp + ") = " + rp.getSecondaryScale());
        }
    }

    private static void demoGetSeed() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSeed(" + rp + ") = " + rp.getSeed());
        }
    }

    private static void demoAlt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("alt(" + rp + ") = " + rp.alt());
        }
    }

    private static void demoWithScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withScale(" + p.a + ", " + p.b + ") = " + p.a.withScale(p.b));
        }
    }

    private static void demoWithSecondaryScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withSecondaryScale(" + p.a + ", " + p.b + ") = " + p.a.withSecondaryScale(p.b));
        }
    }

    private static void demoToString() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println(rp);
        }
    }
}