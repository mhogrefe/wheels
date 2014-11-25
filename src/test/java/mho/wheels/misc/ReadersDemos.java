package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;

public class ReadersDemos {
    private static final boolean USE_RANDOM = false;
    private static final String BOOLEAN_CHARS = "aeflrstu";
    private static final String ORDERING_CHARS = "EGLQT";
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(new Random(0x6af477d9a7e54fcaL));
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoReadBoolean() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    public static void demoReadBoolean_targeted() {
        initialize();
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BOOLEAN_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BOOLEAN_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private static void demoFindBooleanIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    public static void demoFindBooleanIn_targeted() {
        initialize();
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BOOLEAN_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BOOLEAN_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    private static void demoReadOrdering() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    public static void demoReadOrdering_targeted() {
        initialize();
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ORDERING_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ORDERING_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    private static void demoFindOrderingIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }

    public static void demoFindOrderingIn_targeted() {
        initialize();
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ORDERING_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ORDERING_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }
}
