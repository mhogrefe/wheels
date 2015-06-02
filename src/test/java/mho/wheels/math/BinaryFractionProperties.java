package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.misc.BigDecimalUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.BinaryFraction.of;
import static mho.wheels.ordering.Ordering.le;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("ConstantConditions")
public class BinaryFractionProperties {
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize(String name) {
        P.reset();
        System.out.println("\t\ttesting " + name + " properties...");
    }

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        System.out.println("BinaryFraction properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesGetMantissa();
            propertiesGetExponent();
            propertiesOf_BigInteger_int();
            propertiesOf_BigInteger();
            propertiesOf_int();
            propertiesOf_float();
            propertiesOf_double();
            propertiesBigDecimalValue();
            propertiesIsInteger();
        }
        System.out.println("Done");
    }

    private static void propertiesGetMantissa() {
        initialize("getMantissa()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigInteger mantissa = bf.getMantissa();
            assertEquals(bf, of(mantissa, bf.getExponent()), bf);
            assertTrue(bf, mantissa.equals(BigInteger.ZERO) || mantissa.testBit(0));
        }
    }

    private static void propertiesGetExponent() {
        initialize("getExponent()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            bf.getExponent();
        }
    }

    private static void propertiesOf_BigInteger_int() {
        initialize("of(BigInteger, int)");
        Iterable<Pair<BigInteger, Integer>> ps = filter(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BinaryFraction bf = of(p.a, p.b);
            bf.validate();
        }

        Iterable<Pair<BigInteger, Integer>> psFail = P.dependentPairs(
                map(i -> i.shiftLeft(1), P.nonzeroBigIntegers()),
                m -> P.range(Integer.MIN_VALUE - m.getLowestSetBit(), Integer.MAX_VALUE)
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            try {
                of(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesOf_BigInteger() {
        initialize("of(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            BinaryFraction bf = of(i);
            bf.validate();
            assertTrue(i, bf.isInteger());
        }

        for (BigInteger i : take(LIMIT, map(j -> j.shiftLeft(1).add(BigInteger.ONE), P.bigIntegers()))) {
            assertEquals(i.toString(), of(i).getMantissa(), i);
        }
    }

    private static void propertiesOf_int() {
        initialize("of(int)");
        for (int i : take(LIMIT, P.integers())) {
            BinaryFraction bf = of(i);
            bf.validate();
            assertTrue(i, bf.isInteger());
            assertTrue(i, Ordering.ge(bf, of(BigInteger.ONE.shiftLeft(31).negate())));
            assertTrue(i, Ordering.lt(bf, of(BigInteger.ONE.shiftLeft(31))));
        }

        for (int i : take(LIMIT, map(j -> 2 * j + 1, P.integers()))) {
            assertEquals(i, of(i).getMantissa(), BigInteger.valueOf(i));
        }
    }

    private static void propertiesOf_float() {
        initialize("of(float)");
        for (float f : take(LIMIT, P.floats())) {
            Optional<BinaryFraction> obf = of(f);
            assertEquals(f, Float.isFinite(f) && !Float.isNaN(f), obf.isPresent());
        }

        for (float f : take(LIMIT, filter(g -> Float.isFinite(g) && !Float.isNaN(g), P.floats()))) {
            BinaryFraction bf = of(f).get();
            bf.validate();
            assertTrue(f, le(bf.getExponent(), 149));
            assertTrue(f, le(bf.getMantissa(), BigInteger.ONE.shiftLeft(24)));
        }
    }

    private static void propertiesOf_double() {
        initialize("of(double)");
        for (double d : take(LIMIT, P.doubles())) {
            Optional<BinaryFraction> obf = of(d);
            assertEquals(d, Double.isFinite(d) && !Double.isNaN(d), obf.isPresent());
        }

        for (double d : take(LIMIT, filter(e -> Double.isFinite(e) && !Double.isNaN(e), P.doubles()))) {
            BinaryFraction bf = of(d).get();
            bf.validate();
            assertTrue(d, le(bf.getExponent(), 1074));
            assertTrue(d, le(bf.getMantissa(), BigInteger.ONE.shiftLeft(53)));
        }
    }

    private static void propertiesBigDecimalValue() {
        initialize("bigDecimalValue()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigDecimal bd = bf.bigDecimalValue();
            //todo signum
            assertEquals(bf, BigDecimalUtils.shiftRight(bd, bf.getExponent()).toBigIntegerExact(), bf.getMantissa());
        }
    }

    private static void propertiesIsInteger() {
        initialize("isInteger()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            bf.isInteger();
        }
        //todo floor
    }

    private static void propertiesEquals() {
        initialize("equal(Object)");

    }
}
