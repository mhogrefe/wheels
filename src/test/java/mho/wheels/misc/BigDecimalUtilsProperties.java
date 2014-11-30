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
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("Testing BigDecimalUtils properties " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;

            propertiesSetPrecision();

            System.out.println();
        }
        System.out.println("Done");
        System.out.println();
    }

    private static void propertiesSetPrecision() {
        initialize();
        System.out.println("testing setPrecision(BigDecimal, int) properties...");

        Iterable<Pair<BigDecimal, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigDecimals(), P.positiveIntegers());
        } else {
            ps = P.pairs(P.bigDecimals(), ((RandomProvider) P).positiveIntegersGeometric(20));
        }
        for (Pair<BigDecimal, Integer> p : take(LIMIT, IterableUtils.filter(q -> ne(q.a, BigDecimal.ZERO), ps))) {
            assert p.a != null;
            assert p.b != null;
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
            assert p.a != null;
            assert p.b != null;
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(p.toString(), bd.scale() > -1);
            assertTrue(p.toString(), eq(bd, BigDecimal.ZERO));
        }

        for (Pair<BigDecimal, Integer> p : take(LIMIT, filter(q -> q.b > 1, zeroPs))) {
            assert p.a != null;
            assert p.b != null;
            BigDecimal bd = setPrecision(p.a, p.b);
            assertTrue(
                    p.toString(),
                    bd.toString().equals("0." + replicate(p.b - 1, '0')) || bd.toString().equals("0E-" + (p.b - 1))
            );
        }
    }
}
