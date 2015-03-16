package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.BigDecimalUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class BigDecimalUtilsProperties {
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
        System.out.println("BigDecimalUtils properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
            propertiesSetPrecision();
            propertiesSuccessor();
            propertiesPredecessor();
        }
        System.out.println("Done");
    }

    private static void propertiesSetPrecision() {
        initialize();
        System.out.println("\t\ttesting setPrecision(BigDecimal, int) properties...");

        Iterable<Pair<BigDecimal, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigDecimals(), P.positiveIntegers());
        } else {
            ps = P.pairs(P.bigDecimals(), ((RandomProvider) P).positiveIntegersGeometric(20));
        }
        for (Pair<BigDecimal, Integer> p : take(LIMIT, IterableUtils.filter(q -> ne(q.a, BigDecimal.ZERO), ps))) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertEquals(p.toString(), bd.precision(), (int) p.b);
            assertTrue(p.toString(), ne(bd, BigDecimal.ZERO));
        }

        Iterable<Pair<BigDecimal, Integer>> zeroPs;
        if (P instanceof ExhaustiveProvider) {
            Iterable<BigDecimal> zeroes = map(i -> new BigDecimal(BigInteger.ZERO, i), P.integers());
            zeroPs = ((ExhaustiveProvider) P).pairsSquareRootOrder(zeroes, P.positiveIntegers());
        } else {
            Iterable<BigDecimal> zeroes = map(
                    i -> new BigDecimal(BigInteger.ZERO, i),
                    ((RandomProvider) P).integersGeometric(20)
            );
            zeroPs = P.pairs(zeroes, ((RandomProvider) P).positiveIntegersGeometric(20));
        }
        for (Pair<BigDecimal, Integer> p : take(LIMIT, zeroPs)) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(p.toString(), bd.scale() > -1);
            assertTrue(p.toString(), eq(bd, BigDecimal.ZERO));
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, filter(q -> q.b > 1, zeroPs))) {
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(
                    p.toString(),
                    bd.toString().equals("0." + replicate(p.b - 1, '0')) || bd.toString().equals("0E-" + (p.b - 1))
            );
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, P.pairs(P.bigDecimals(), P.rangeDown(0)))) {
            try {
                setPrecision(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesSuccessor() {
        initialize();
        System.out.println("\t\ttesting successor(BigDecimal) properties...");

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            BigDecimal successor = successor(bd);
            assertEquals(bd.toString(), bd.scale(), successor.scale());
            assertEquals(bd.toString(), predecessor(successor), bd);
            assertEquals(bd.toString(), successor(bd.negate()), predecessor(bd).negate());
        }
    }

    private static void propertiesPredecessor() {
        initialize();
        System.out.println("\t\ttesting predecessor(BigDecimal) properties...");

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            BigDecimal predecessor = predecessor(bd);
            assertEquals(bd.toString(), bd.scale(), predecessor.scale());
            assertEquals(bd.toString(), successor(predecessor), bd);
            assertEquals(bd.toString(), predecessor(bd.negate()), successor(bd).negate());
        }
    }
}
