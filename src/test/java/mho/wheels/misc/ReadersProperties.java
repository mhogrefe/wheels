package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersProperties {
    private static boolean USE_RANDOM;
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

    @Test
    public void testAllProperties() {
        System.out.println("Readers properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
            propertiesReadBoolean();
            propertiesFindBooleanIn();
            propertiesReadOrdering();
            propertiesFindOrderingIn();
        }
        System.out.println("Done");
    }

    private static void propertiesReadBoolean() {
        initialize();
        System.out.println("testing readBoolean(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readBoolean(s);
        }

        for (boolean b : take(LIMIT, P.booleans())) {
            Optional<Boolean> ob = readBoolean(Boolean.toString(b));
            assertEquals(Boolean.toString(b), ob.get(), b);
        }
    }

    private static void propertiesFindBooleanIn() {
        initialize();
        System.out.println("testing findBooleanIn(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            findBooleanIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss;
        if (P instanceof ExhaustiveProvider) {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.booleans())
            );
        } else {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    P.pairs(ps, P.booleans())
            );
        }
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Boolean, Integer>> op = findBooleanIn(s);
            Pair<Boolean, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            assertTrue(s, s.substring(p.b).startsWith(p.a.toString()));
        }
    }

    private static void propertiesReadOrdering() {
        initialize();
        System.out.println("testing readOrdering(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readOrdering(s);
        }

        for (Ordering o : take(LIMIT, P.orderings())) {
            Optional<Ordering> oo = readOrdering(o.toString());
            assertEquals(o.toString(), oo.get(), o);
        }
    }

    private static void propertiesFindOrderingIn() {
        initialize();
        System.out.println("testing findOrderingIn(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            findOrderingIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss;
        if (P instanceof ExhaustiveProvider) {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.orderings())
            );
        } else {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    P.pairs(ps, P.orderings())
            );
        }
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Ordering, Integer>> op = findOrderingIn(s);
            Pair<Ordering, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            assertTrue(s, s.substring(p.b).startsWith(p.a.toString()));
        }
    }
}
