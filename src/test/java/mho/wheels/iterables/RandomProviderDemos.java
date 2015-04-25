package mho.wheels.iterables;

import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Testing;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class RandomProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static final int SMALL_LIMIT = 1000;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.EXAMPLE;
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
        for (List<Integer> is : take(LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            System.out.println("RandomProvider(" + is + ") = " + new RandomProvider(is));
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
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.alt().naturalIntegers()))) {
            System.out.println("withScale(" + p.a + ", " + p.b + ") = " + p.a.withScale(p.b));
        }
    }

    private static void demoWithSecondaryScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.alt().naturalIntegers()))) {
            System.out.println("withSecondaryScale(" + p.a + ", " + p.b + ") = " + p.a.withSecondaryScale(p.b));
        }
    }

    private static void demoBooleans() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("booleans(" + rp + ") = " + its(rp.booleans()));
        }
    }

    private static void demoIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("integers(" + rp + ") = " + its(rp.integers()));
        }
    }

    private static void demoLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("longs(" + rp + ") = " + its(rp.longs()));
        }
    }

    private static void demoRangeUp_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().bytes()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.alt().shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.alt().integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().longs()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.alt().characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeDown_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().bytes()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.alt().shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.alt().integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().longs()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.alt().characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRange_byte_byte() {
        initialize();
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().bytes(),
                P.alt().alt().bytes()
        );
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_short_short() {
        initialize();
        Iterable<Triple<RandomProvider, Short, Short>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().shorts(),
                P.alt().alt().shorts()
        );
        for (Triple<RandomProvider, Short, Short> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_int_int() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().integers(),
                P.alt().alt().integers()
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_long_long() {
        initialize();
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().longs(),
                P.alt().alt().longs()
        );
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_BigInteger_BigInteger() {
        initialize();
        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().bigIntegers(),
                P.alt().alt().bigIntegers()
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_char_char() {
        initialize();
        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().characters(),
                P.alt().alt().characters()
        );
        for (Triple<RandomProvider, Character, Character> p : take(SMALL_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + cits(p.a.range(p.b, p.c)));
        }
    }

    private static void demoEquals_RandomProvider() {
        initialize();
        for (Pair<RandomProvider, RandomProvider> p : take(LIMIT, P.pairs(P.randomProviders()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private static void demoEquals_null() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            //noinspection ObjectEqualsNull
            System.out.println(r + (r.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private static void demoHashCode() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            System.out.println("hashCode(" + r + ") = " + r.hashCode());
        }
    }

    private static void demoToString() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println(rp);
        }
    }
}
