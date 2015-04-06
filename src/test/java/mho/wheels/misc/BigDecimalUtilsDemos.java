package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;

import java.math.BigDecimal;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.misc.BigDecimalUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class BigDecimalUtilsDemos {
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

    private static void demoSetPrecision() {
        initialize();
        Iterable<Pair<BigDecimal, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigDecimals(), P.positiveIntegers());
        } else {
            ps = P.pairs(P.bigDecimals(), ((RandomProvider) P).positiveIntegersGeometric(20));
        }
        for (Pair<BigDecimal, Integer> p : take(LIMIT, ps)) {
            System.out.println("setPrecision(" + p.a + ", " + p.b + ") = " + setPrecision(p.a, p.b));
        }
    }

    private static void demoSuccessor() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("successor(" + bd + ") = " + successor(bd));
        }
    }

    private static void demoPredecessor() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("predecessor(" + bd + ") = " + predecessor(bd));
        }
    }
}
