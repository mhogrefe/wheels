package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;

public class ReadersDemos {
    private static final boolean USE_RANDOM = true;
    private static final String BOOLEAN_CHARS = "aeflrstu";
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

    public static void main(String[] args) {
        demoFindBooleanIn_targeted();
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
}
