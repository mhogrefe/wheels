package mho.wheels.numberUtils;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.numberUtils.BigDecimalUtils.*;
import static mho.wheels.ordering.Ordering.eq;
import static mho.wheels.ordering.Ordering.ne;
import static mho.wheels.testing.Testing.*;

public class BigDecimalUtilsProperties extends TestProperties {
    public BigDecimalUtilsProperties() {
        super("BigDecimalUtils");
    }

    @Override
    protected void testBothModes() {
        propertiesCeilingLog10();
        propertiesSetPrecision();
        propertiesSuccessor();
        propertiesPredecessor();
        propertiesShiftLeft();
        propertiesShiftRight();
        propertiesCanonicalize();
        propertiesIsCanonical();
    }

    private void propertiesCeilingLog10() {
        initialize("ceilingLog10(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.positiveBigDecimals())) {
            int log = ceilingLog10(bd);
            assertTrue(bd, Ordering.le(bd, BigDecimal.ONE.movePointRight(log)));
            assertTrue(bd, Ordering.gt(bd, BigDecimal.ONE.movePointRight(log - 1)));
            homomorphic(
                    BigDecimalUtils::ceilingLog10,
                    BigDecimalUtils::ceilingLog10,
                    c -> c.movePointLeft(1),
                    i -> i - 1,
                    bd
            );
            homomorphic(
                    BigDecimalUtils::ceilingLog10,
                    BigDecimalUtils::ceilingLog10,
                    c -> c.movePointRight(1),
                    i -> i + 1,
                    bd
            );
        }

        for (BigDecimal bd : take(LIMIT, P.withElement(BigDecimal.ZERO, P.negativeBigDecimals()))) {
            try {
                ceilingLog10(bd);
                fail(bd);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSetPrecision() {
        initialize("setPrecision(BigDecimal, int)");
        Iterable<Pair<BigDecimal, Integer>> ps = filterInfinite(
                q -> ne(q.a, BigDecimal.ZERO),
                P.pairsSquareRootOrder(P.bigDecimals(), P.positiveIntegersGeometric())
        );
        for (Pair<BigDecimal, Integer> p : take(LIMIT, ps)) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertEquals(p, bd.precision(), p.b);
            assertTrue(p, ne(bd, BigDecimal.ZERO));
        }

        ps = P.pairsSquareRootOrder(
                map(i -> new BigDecimal(BigInteger.ZERO, i), P.integersGeometric()),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigDecimal, Integer> p : take(LIMIT, ps)) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(p, bd.scale() > -1);
            assertTrue(p, eq(bd, BigDecimal.ZERO));
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, filterInfinite(q -> q.b > 1, ps))) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(
                    p,
                    bd.toString().equals("0." + replicate(p.b - 1, '0')) || bd.toString().equals("0E-" + (p.b - 1))
            );
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.rangeDown(0)))) {
            try {
                setPrecision(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSuccessor() {
        initialize("successor(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            BigDecimal successor = successor(bd);
            assertEquals(bd, bd.scale(), successor.scale());
            assertEquals(bd, predecessor(successor), bd);
            assertEquals(bd, successor(bd.negate()), predecessor(bd).negate());
        }
    }

    private void propertiesPredecessor() {
        initialize("predecessor(BigDecimal)");

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            BigDecimal predecessor = predecessor(bd);
            assertEquals(bd, bd.scale(), predecessor.scale());
            assertEquals(bd, successor(predecessor), bd);
            assertEquals(bd, predecessor(bd.negate()), successor(bd).negate());
        }
    }

    private void propertiesShiftLeft() {
        initialize("shiftLeft(BigDecimal, int)");
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            homomorphic(
                    BigDecimal::negate,
                    Function.identity(),
                    BigDecimal::negate,
                    BigDecimalUtils::shiftLeft,
                    BigDecimalUtils::shiftLeft,
                    p
            );
            BigDecimal shifted = shiftLeft(p.a, p.b);
            assertEquals(p, shifted, shiftRight(p.a, -p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd, shiftLeft(bd, 0), bd);
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.naturalIntegersGeometric()))) {
            assertEquals(p, shiftLeft(p.a, p.b), p.a.multiply(TWO.pow(p.b)));
        }
    }

    private void propertiesShiftRight() {
        initialize("shiftRight(BigDecimal, int)");
        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.integersGeometric()))) {
            homomorphic(
                    BigDecimal::negate,
                    Function.identity(),
                    BigDecimal::negate,
                    BigDecimalUtils::shiftRight,
                    BigDecimalUtils::shiftRight,
                    p
            );
            BigDecimal shifted = shiftRight(p.a, p.b);
            assertEquals(p, shifted, shiftLeft(p.a, -p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd, shiftRight(bd, 0), bd);
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.naturalIntegersGeometric()))) {
            //noinspection BigDecimalMethodWithoutRoundingCalled
            assertEquals(p, shiftRight(p.a, p.b), p.a.divide(TWO.pow(p.b)));
        }
    }

    private void propertiesCanonicalize() {
        initialize("canonicalize(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            homomorphic(
                    BigDecimal::negate,
                    BigDecimal::negate,
                    BigDecimalUtils::canonicalize,
                    BigDecimalUtils::canonicalize,
                    bd
            );
            BigDecimal canonicalized = canonicalize(bd);
            assertTrue(bd, isCanonical(canonicalized));
            assertTrue(bd, eq(bd, canonicalized));
            assertFalse(bd, canonicalized.scale() < 0);
            assertTrue(bd, canonicalized.scale() == 0 ||
                    !canonicalized.unscaledValue().mod(BigInteger.TEN).equals(BigInteger.ZERO));
        }

        for (BigDecimal bd : take(LIMIT, map(i -> new BigDecimal(BigInteger.ZERO, i), P.integersGeometric()))) {
            assertEquals(bd, canonicalize(bd).scale(), 0);
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assertEquals(p, eq(p.a, p.b), canonicalize(p.a).equals(canonicalize(p.b)));
        }
    }

    private void propertiesIsCanonical() {
        initialize("isCanonical(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd, isCanonical(bd), bd.equals(canonicalize(bd)));
        }
    }
}
