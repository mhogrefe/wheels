package mho.wheels.numberUtils;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.math.BigDecimal;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.numberUtils.BigDecimalUtils.*;

@SuppressWarnings("UnusedDeclaration")
public class BigDecimalUtilsDemos extends Demos {
    public BigDecimalUtilsDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoCeilingLog10() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.positiveBigDecimals())) {
            System.out.println("ceilingLog10(" + bd + ") = " + ceilingLog10(bd));
        }
    }

    private void demoSetPrecision() {
        initialize();
        Iterable<Pair<BigDecimal, Integer>> ps = P.pairsSquareRootOrder(
                P.bigDecimals(),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigDecimal, Integer> p : take(LIMIT, ps)) {
            System.out.println("setPrecision(" + p.a + ", " + p.b + ") = " + setPrecision(p.a, p.b));
        }
    }

    private void demoSuccessor() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("successor(" + bd + ") = " + successor(bd));
        }
    }

    private void demoPredecessor() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("predecessor(" + bd + ") = " + predecessor(bd));
        }
    }

    private void demoShiftLeft() {
        initialize();
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            System.out.println(p.a + " << " + p.b + " = " + shiftLeft(p.a, p.b));
        }
    }

    private void demoShiftRight() {
        initialize();
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            System.out.println(p.a + " >> " + p.b + " = " + shiftRight(p.a, p.b));
        }
    }

    private void demoCanonicalize() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("canonicalize(" + bd + ") = " + canonicalize(bd));
        }
    }

    private void demoIsCanonical() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println(bd + " is " + (isCanonical(bd) ? "" : "not ") + "canonical");
        }
    }
}
