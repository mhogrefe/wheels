package mho.wheels.numberUtils;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;

import java.math.BigDecimal;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.numberUtils.BigDecimalUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class BigDecimalUtilsDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoSetPrecision() {
        initialize();
        Iterable<Pair<BigDecimal, Integer>> ps = P.pairsSquareRootOrder(
                P.bigDecimals(),
                P.positiveIntegersGeometric()
        );
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

    private static void demoShiftLeft() {
        initialize();
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            System.out.println(p.a + " << " + p.b + " = " + shiftLeft(p.a, p.b));
        }
    }

    private static void demoShiftRight() {
        initialize();
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            System.out.println(p.a + " >> " + p.b + " = " + shiftRight(p.a, p.b));
        }
    }
}
