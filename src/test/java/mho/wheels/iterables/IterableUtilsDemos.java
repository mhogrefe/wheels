package mho.wheels.iterables;

import mho.wheels.structures.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;

public class IterableUtilsDemos {
    private static final boolean USE_RANDOM = true;
    private static int LIMIT;

    private static IterableProvider P;

    public static void main(String[] args) {
        demoToList_String();
    }

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
        for (List<Integer> list : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(list.toString()));
            System.out.println("toList(" + listString + ") = " + toList(list));
        }
    }

    public static void demoToList_String() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("toList(" + s + ") = " + toList(s));
        }
    }
}
