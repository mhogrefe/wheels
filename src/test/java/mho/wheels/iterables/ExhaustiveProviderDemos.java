package mho.wheels.iterables;

import mho.wheels.structures.Pair;

import java.math.BigInteger;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class ExhaustiveProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static final ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final int SMALL_LIMIT = 1000;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(0x6af477d9a7e54fcaL);
        } else {
            P = ExhaustiveProvider.INSTANCE;
        }
    }

    private static void demoRangeUp_byte() {
        initialize();
        for (byte b : take(SMALL_LIMIT, P.bytes())) {
            System.out.println("rangeUp(" + b + ") = " + its(EP.rangeUp(b)));
        }
    }

    private static void demoRangeUp_short() {
        initialize();
        for (short s : take(SMALL_LIMIT, P.shorts())) {
            System.out.println("rangeUp(" + s + ") = " + its(EP.rangeUp(s)));
        }
    }

    private static void demoRangeUp_int() {
        initialize();
        for (int i : take(SMALL_LIMIT, P.integers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private static void demoRangeUp_long() {
        initialize();
        for (long l : take(SMALL_LIMIT, P.longs())) {
            System.out.println("rangeUp(" + l + ") = " + its(EP.rangeUp(l)));
        }
    }

    private static void demoRangeUp_BigInteger() {
        initialize();
        for (BigInteger i : take(SMALL_LIMIT, P.bigIntegers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private static void demoRangeUp_char() {
        initialize();
        for (char c : take(SMALL_LIMIT, P.characters())) {
            System.out.println("rangeUp(" + nicePrint(c) + ") = " + cits(EP.rangeUp(c)));
        }
    }

    private static void demoRangeDown_byte() {
        initialize();
        for (byte b : take(SMALL_LIMIT, P.bytes())) {
            System.out.println("rangeDown(" + b + ") = " + its(EP.rangeDown(b)));
        }
    }

    private static void demoRangeDown_short() {
        initialize();
        for (short s : take(SMALL_LIMIT, P.shorts())) {
            System.out.println("rangeDown(" + s + ") = " + its(EP.rangeDown(s)));
        }
    }

    private static void demoRangeDown_int() {
        initialize();
        for (int i : take(SMALL_LIMIT, P.integers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private static void demoRangeDown_long() {
        initialize();
        for (long l : take(SMALL_LIMIT, P.longs())) {
            System.out.println("rangeDown(" + l + ") = " + its(EP.rangeDown(l)));
        }
    }

    private static void demoRangeDown_BigInteger() {
        initialize();
        for (BigInteger i : take(SMALL_LIMIT, P.bigIntegers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private static void demoRangeDown_char() {
        initialize();
        for (char c : take(SMALL_LIMIT, P.characters())) {
            System.out.println("rangeDown(" + nicePrint(c) + ") = " + cits(EP.rangeDown(c)));
        }
    }

    private static void demoRange_byte_byte() {
        initialize();
        for (Pair<Byte, Byte> p : take(SMALL_LIMIT, P.pairs(P.bytes()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_short_short() {
        initialize();
        for (Pair<Short, Short> p : take(SMALL_LIMIT, P.pairs(P.shorts()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_int_int() {
        initialize();
        for (Pair<Integer, Integer> p : take(SMALL_LIMIT, P.pairs(P.integers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_long_long() {
        initialize();
        for (Pair<Long, Long> p : take(SMALL_LIMIT, P.pairs(P.longs()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_BigInteger_BigInteger() {
        initialize();
        for (Pair<BigInteger, BigInteger> p : take(SMALL_LIMIT, P.pairs(P.bigIntegers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_char_char() {
        initialize();
        for (Pair<Character, Character> p : take(SMALL_LIMIT, P.pairs(P.characters()))) {
            System.out.println("range(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " + cits(EP.range(p.a, p.b)));
        }
    }
}
