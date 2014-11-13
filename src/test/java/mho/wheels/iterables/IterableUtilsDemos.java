package mho.wheels.iterables;

import mho.wheels.structures.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.range;

public class IterableUtilsDemos {
    private static final boolean USE_RANDOM = false;
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

    public static void demoAddTo_Collection_Iterable() {
        initialize();
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(P.withNull(P.integers()))))) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    public static void demoAddTo_Collection_String() {
        initialize();
        Iterable<Pair<String, List<Character>>> ps = P.pairs(P.strings(), P.lists(P.withNull(P.characters())));
        for (Pair<String, List<Character>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Character> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    public static void demoToList_Iterable() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("toList(" + listString + ") = " + toList(is));
        }
    }

    public static void demoToList_String() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("toList(" + s + ") = " + toList(s));
        }
    }

    public static void demoToString_Iterable() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("toString(" + listString + ") = " + IterableUtils.toString(is));
        }
    }

    public static void demoToString_int_finite_Iterable() {
        initialize();
        Iterable<Pair<Integer, List<Integer>>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = map(
                    p -> new Pair<>(p.b, p.a),
                    ((ExhaustiveProvider) P).pairsSquareRootOrder(
                            P.lists(P.withNull(P.integers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            ps = P.pairs(((RandomProvider) P).naturalIntegersGeometric(20), P.lists(P.integers()));
        }
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("toString(" + p.a + ", " + p.b + ") = " + IterableUtils.toString(p.a, p.b));
        }
    }

    public static void demoToString_int_infinite_Iterable() {
        initialize();
        Iterable<Pair<Integer, Iterable<Integer>>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = map(
                    p -> {
                        assert p.a != null;
                        return new Pair<>(p.b, cycle(p.a));
                    },
                    ((ExhaustiveProvider) P).pairsSquareRootOrder(
                            P.lists(P.withNull(P.integers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            ps = P.pairs(
                    ((RandomProvider) P).naturalIntegersGeometric(20),
                    map(IterableUtils::cycle, P.lists(P.integers()))
            );
        }
        for (Pair<Integer, Iterable<Integer>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("toString(" + p.a + ", " + IterableUtils.toString(10, p.b) + ") = " + IterableUtils.toString(p.a, p.b));
        }
    }

    public static void demoFromString() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("fromString(" + s + ") = " + toList(fromString(s)));
        }
    }

    public static void demoCharsToString() {
        initialize();
        for (List<Character> cs : take(LIMIT, P.lists(P.characters()))) {
            String listString = tail(init(cs.toString()));
            System.out.println("charsToString(" + listString + ") = " + charsToString(cs));
        }
    }

    public static void demoRange_byte() {
        initialize();
        for (byte b : take(LIMIT, P.bytes())) {
            System.out.println("range(" + b + ") = " + toList(range(b)));
        }
    }

    public static void demoRange_short() {
        initialize();
        for (short s : take(LIMIT, P.shorts())) {
            System.out.println("range(" + s + ") = " + IterableUtils.toString(20, range(s)));
        }
    }

    public static void demoRange_int() {
        initialize();
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(20, range(i)));
        }
    }

    public static void demoRange_long() {
        initialize();
        for (long l : take(LIMIT, P.longs())) {
            System.out.println("range(" + l + ") = " + IterableUtils.toString(20, range(l)));
        }
    }

    public static void demoRange_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(20, range(i)));
        }
    }

    public static void demoRange_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("range(" + bd + ") = " + IterableUtils.toString(20, range(bd)));
        }
    }

    public static void demoRange_char() {
        initialize();
        for (char c : take(LIMIT, P.characters())) {
            System.out.println("range(" + c + ") = " + IterableUtils.toString(20, range(c)));
        }
    }
}
