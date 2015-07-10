package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.misc.BigDecimalUtils;
import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.math.BinaryFraction.of;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.ordering.Ordering.compare;
import static mho.wheels.ordering.Ordering.le;
import static mho.wheels.testing.Testing.*;

public strictfp class BinaryFractionProperties {
    private static int LIMIT;
    private static final @NotNull String BINARY_FRACTION_CHARS = " -0123456789<>";
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
            propertiesFloatRange();
            propertiesDoubleRange();
            propertiesIsInteger();
            propertiesAdd();
            propertiesNegate();
            propertiesAbs();
            propertiesSignum();
            propertiesSubtract();
            compareImplementationsSubtract();
            propertiesMultiply();
            propertiesShiftLeft();
            compareImplementationsShiftLeft();
            propertiesShiftRight();
            compareImplementationsShiftRight();
            propertiesSum();
            propertiesProduct();
            propertiesDelta();
            propertiesFloor();
            propertiesCeiling();
            propertiesEquals();
            propertiesHashCode();
            propertiesCompareTo();
            propertiesRead();
            propertiesFindIn();
            propertiesToString();
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
        Iterable<Pair<BigInteger, Integer>> ps = filterInfinite(
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
            } catch (ArithmeticException ignored) {}
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
            assertEquals(i, of(i).getMantissa(), i);
        }
    }

    private static void propertiesOf_int() {
        initialize("of(int)");
        for (int i : take(LIMIT, P.integers())) {
            BinaryFraction bf = of(i);
            bf.validate();
            assertTrue(i, bf.isInteger());
            assertTrue(i, ge(bf, of(BigInteger.ONE.shiftLeft(31).negate())));
            assertTrue(i, lt(bf, of(BigInteger.ONE.shiftLeft(31))));
        }

        for (int i : take(LIMIT, map(j -> 2 * j + 1, P.integers()))) {
            assertEquals(i, of(i).getMantissa(), BigInteger.valueOf(i));
        }
    }

    private static void propertiesOf_float() {
        initialize("of(float)");
        for (float f : take(LIMIT, P.floats())) {
            Optional<BinaryFraction> obf = of(f);
            assertEquals(f, Float.isFinite(f), obf.isPresent());
        }

        for (float f : take(LIMIT, filter(Float::isFinite, P.floats()))) {
            BinaryFraction bf = of(f).get();
            bf.validate();
            assertTrue(f, le(bf.getExponent(), -FloatingPointUtils.MIN_SUBNORMAL_FLOAT_EXPONENT));
            assertTrue(f, le(bf.getMantissa(), BigInteger.ONE.shiftLeft(24)));
        }
    }

    private static void propertiesOf_double() {
        initialize("of(double)");
        for (double d : take(LIMIT, P.doubles())) {
            Optional<BinaryFraction> obf = of(d);
            assertEquals(d, Double.isFinite(d), obf.isPresent());
        }

        for (double d : take(LIMIT, filter(Double::isFinite, P.doubles()))) {
            BinaryFraction bf = of(d).get();
            bf.validate();
            assertTrue(d, le(bf.getExponent(), -FloatingPointUtils.MIN_SUBNORMAL_DOUBLE_EXPONENT));
            assertTrue(d, le(bf.getMantissa(), BigInteger.ONE.shiftLeft(53)));
        }
    }

    private static void propertiesBigDecimalValue() {
        initialize("bigDecimalValue()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigDecimal bd = bf.bigDecimalValue();
            assertEquals(bf, bd.signum(), bf.signum());
            assertEquals(bf, BigDecimalUtils.shiftRight(bd, bf.getExponent()).toBigIntegerExact(), bf.getMantissa());
        }
    }

    private static void propertiesFloatRange() {
        initialize("floatRange()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Pair<Float, Float> range = bf.floatRange();
            assertNotNull(bf, range.a);
            assertNotNull(bf, range.b);
            assertTrue(bf, range.a.equals(range.b) || FloatingPointUtils.successor(range.a) == range.b);
            assertTrue(bf, range.a == Float.NEGATIVE_INFINITY || ge(bf, of(range.a).get()));
            assertTrue(bf, range.b == Float.POSITIVE_INFINITY || le(bf, of(range.b).get()));
            if (bf != ZERO) {
                assertFalse(bf, FloatingPointUtils.isNegativeZero(range.a));
                assertFalse(bf, FloatingPointUtils.isPositiveZero(range.b));
            }
        }

        Iterable<Float> fs = filter(g -> Float.isFinite(g) && !FloatingPointUtils.isNegativeZero(g), P.floats());
        for (float f : take(LIMIT, fs)) {
            Pair<Float, Float> range = of(f).get().floatRange();
            assertEquals(f, range.a, f);
            assertEquals(f, range.b, f);
        }

        Pair<Float, Float> range = new Pair<>(0.0f, Float.MIN_VALUE);
        Iterable<BinaryFraction> bfs = filter(
                e -> e.signum() == 1 && lt(e, SMALLEST_FLOAT),
                P.range(ZERO, SMALLEST_FLOAT)
        );
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.floatRange(), range);
        }

        range = new Pair<>(-Float.MIN_VALUE, -0.0f);
        bfs = filter(
                e -> e.signum() == -1 && gt(e, SMALLEST_FLOAT.negate()),
                P.range(SMALLEST_FLOAT.negate(), ZERO)
        );
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.floatRange(), range);
        }

        range = new Pair<>(Float.MAX_VALUE, Float.POSITIVE_INFINITY);
        for (BinaryFraction bf : take(LIMIT, filter(e -> gt(e, LARGEST_FLOAT), P.rangeUp(LARGEST_FLOAT)))) {
            assertEquals(bf, bf.floatRange(), range);
        }

        range = new Pair<>(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE);
        bfs = filter(e -> lt(e, LARGEST_FLOAT.negate()), P.rangeDown(LARGEST_FLOAT.negate()));
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.floatRange(), range);
        }
    }

    private static void propertiesDoubleRange() {
        initialize("doubleRange()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Pair<Double, Double> range = bf.doubleRange();
            assertNotNull(bf, range.a);
            assertNotNull(bf, range.b);
            assertTrue(bf, range.a.equals(range.b) || FloatingPointUtils.successor(range.a) == range.b);
            assertTrue(bf, range.a == Double.NEGATIVE_INFINITY || ge(bf, of(range.a).get()));
            assertTrue(bf, range.b == Double.POSITIVE_INFINITY || le(bf, of(range.b).get()));
            if (bf != ZERO) {
                assertFalse(bf, FloatingPointUtils.isNegativeZero(range.a));
                assertFalse(bf, FloatingPointUtils.isPositiveZero(range.b));
            }
        }

        Iterable<Double> ds = filter(e -> Double.isFinite(e) && !FloatingPointUtils.isNegativeZero(e), P.doubles());
        for (double d : take(LIMIT, ds)) {
            Pair<Double, Double> range = of(d).get().doubleRange();
            assertEquals(d, range.a, d);
            assertEquals(d, range.b, d);
        }

        Pair<Double, Double> range = new Pair<>(0.0, Double.MIN_VALUE);
        Iterable<BinaryFraction> bfs = filter(
                e -> e.signum() == 1 && lt(e, SMALLEST_DOUBLE),
                P.range(ZERO, SMALLEST_DOUBLE)
        );
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.doubleRange(), range);
        }

        range = new Pair<>(-Double.MIN_VALUE, -0.0);
        bfs = filter(
                e -> e.signum() == -1 && gt(e, SMALLEST_DOUBLE.negate()),
                P.range(SMALLEST_DOUBLE.negate(), ZERO)
        );
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.doubleRange(), range);
        }

        range = new Pair<>(Double.MAX_VALUE, Double.POSITIVE_INFINITY);
        for (BinaryFraction bf : take(LIMIT, filter(e -> gt(e, LARGEST_DOUBLE), P.rangeUp(LARGEST_DOUBLE)))) {
            assertEquals(bf, bf.doubleRange(), range);
        }

        range = new Pair<>(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE);
        bfs = filter(e -> lt(e, LARGEST_DOUBLE.negate()), P.rangeDown(LARGEST_DOUBLE.negate()));
        for (BinaryFraction bf : take(LIMIT, bfs)) {
            assertEquals(bf, bf.doubleRange(), range);
        }
    }

    private static void propertiesIsInteger() {
        initialize("isInteger()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            assertEquals(bf, bf.isInteger(), of(bf.floor()).equals(bf));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertTrue(i, of(i).isInteger());
        }
    }

    private static void propertiesAdd() {
        initialize("add(BinaryFraction)");
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    try {
                        p.a.add(p.b);
                        return true;
                    } catch (ArithmeticException e) {
                        return false;
                    }
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    bf -> bf.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::add,
                    (x, y) -> x.add(y).stripTrailingZeros(),
                    p
            );
            BinaryFraction sum = p.a.add(p.b);
            sum.validate();
            commutative(BinaryFraction::add, p);
            inverses(bf -> bf.add(p.b), (BinaryFraction bf) -> bf.subtract(p.b), p.a);
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            fixedPoint(ZERO::add, bf);
            fixedPoint(e -> e.add(ZERO), bf);
            assertTrue(bf, bf.add(bf.negate()) == ZERO);
        }

        for (Triple<BinaryFraction, BinaryFraction, BinaryFraction> t : take(LIMIT, P.triples(P.binaryFractions()))) {
            associative(BinaryFraction::add, t);
        }

        //overflow and underflow not tested
    }

    private static void propertiesNegate() {
        initialize("negate()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::negate,
                    BigDecimal::negate,
                    bf
            );
            BinaryFraction negative = bf.negate();
            negative.validate();
            isInvolution(BinaryFraction::negate, bf);
            assertTrue(bf, bf.add(negative) == ZERO);
        }

        for (BinaryFraction bf : take(LIMIT, P.nonzeroBinaryFractions())) {
            assertNotEquals(bf, bf, bf.negate());
        }
    }

    private static void propertiesAbs() {
        initialize("abs()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::abs,
                    BigDecimal::abs,
                    bf
            );
            BinaryFraction abs = bf.abs();
            abs.validate();
            idempotent(BinaryFraction::abs, bf);
            assertNotEquals(bf, abs.signum(), -1);
            assertTrue(bf, ge(abs, ZERO));
        }

        for (BinaryFraction bf : take(LIMIT, P.positiveBinaryFractions())) {
            fixedPoint(BinaryFraction::abs, bf);
        }
    }

    private static void propertiesSignum() {
        initialize("signum()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    Function.identity(),
                    BinaryFraction::signum,
                    BigDecimal::signum,
                    bf
            );
            int signum = bf.signum();
            assertEquals(bf, signum, compare(bf, ZERO).toInt());
            assertTrue(bf, signum == -1 || signum == 0 || signum == 1);
        }
    }

    private static @NotNull BinaryFraction subtract_simplest(@NotNull BinaryFraction a, @NotNull BinaryFraction b) {
        return a.add(b.negate());
    }

    private static void propertiesSubtract() {
        initialize("subtract(BinaryFraction)");
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    try {
                        p.a.subtract(p.b);
                        return true;
                    } catch (ArithmeticException e) {
                        return false;
                    }
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    bf -> bf.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::subtract,
                    (x, y) -> x.subtract(y).stripTrailingZeros(),
                    p
            );
            BinaryFraction difference = p.a.subtract(p.b);
            difference.validate();
            assertEquals(p, difference, subtract_simplest(p.a, p.b));
            antiCommutative(BinaryFraction::subtract, BinaryFraction::negate, p);
            inverses(bf -> bf.add(p.b), (BinaryFraction bf) -> bf.subtract(p.b), p.a);
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            assertEquals(bf, ZERO.subtract(bf), bf.negate());
            fixedPoint(e -> e.subtract(ZERO), bf);
            assertTrue(bf, bf.subtract(bf) == ZERO);
        }

        //overflow and underflow not tested
    }

    private static void compareImplementationsSubtract() {
        Map<String, Function<Pair<BinaryFraction, BinaryFraction>, BinaryFraction>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> subtract_simplest(p.a, p.b));
        functions.put("standard", p -> p.a.subtract(p.b));
        compareImplementations("subtract(BinaryFraction)", take(LIMIT, P.pairs(P.binaryFractions())), functions);
    }

    private static void propertiesMultiply() {
        initialize("multiply(BinaryFraction)");
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    long productExponent = (long) p.a.getExponent() + p.b.getExponent();
                    return productExponent <= Integer.MAX_VALUE && productExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    bf -> bf.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::multiply,
                    (x, y) -> x.multiply(y).stripTrailingZeros(),
                    p
            );
            BinaryFraction product = p.a.multiply(p.b);
            product.validate();
            commutative(BinaryFraction::multiply, p);
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            assertTrue(bf, bf.multiply(ZERO) == ZERO);
            assertTrue(bf, ZERO.multiply(bf) == ZERO);
            fixedPoint(ONE::multiply, bf);
            fixedPoint(e -> e.multiply(ONE), bf);
        }

        //overflow and underflow not tested
    }

    private static @NotNull BinaryFraction shiftLeft_simplest(@NotNull BinaryFraction bf, int bits) {
        return bf.multiply(of(BigInteger.ONE, bits));
    }

    private static void propertiesShiftLeft() {
        initialize("shiftLeft(int)");
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() + p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        for (Pair<BinaryFraction, Integer> p : take(LIMIT, ps)) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    Function.identity(),
                    bf -> bf.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::shiftLeft,
                    (x, bits) -> BigDecimalUtils.shiftLeft(x, bits).stripTrailingZeros(),
                    p
            );
            homomorphic(
                    BinaryFraction::negate,
                    Function.identity(),
                    BinaryFraction::negate,
                    BinaryFraction::shiftLeft,
                    BinaryFraction::shiftLeft,
                    p
            );
            BinaryFraction shifted = p.a.shiftLeft(p.b);
            shifted.validate();
            assertEquals(p, shifted, shiftLeft_simplest(p.a, p.b));
            assertEquals(p, shifted, p.a.shiftRight(-p.b));
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            assertEquals(bf, bf.shiftLeft(0), bf);
        }

        ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() + p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.naturalIntegersGeometric())
        );
        for (Pair<BinaryFraction, Integer> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.shiftLeft(p.b), p.a.multiply(ONE.shiftLeft(p.b)));
        }

        //overflow and underflow not tested
    }

    private static void compareImplementationsShiftLeft() {
        Map<String, Function<Pair<BinaryFraction, Integer>, BinaryFraction>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> shiftLeft_simplest(p.a, p.b));
        functions.put("standard", p -> p.a.shiftLeft(p.b));
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() + p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        compareImplementations("shiftLeft(int)", take(LIMIT, ps), functions);
    }

    private static @NotNull BinaryFraction shiftRight_simplest(@NotNull BinaryFraction bf, int bits) {
        return bf.multiply(of(BigInteger.ONE, -bits));
    }

    private static void propertiesShiftRight() {
        initialize("shiftRight(int)");
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() - p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        for (Pair<BinaryFraction, Integer> p : take(LIMIT, ps)) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    Function.identity(),
                    bf -> bf.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::shiftRight,
                    (x, bits) -> BigDecimalUtils.shiftRight(x, bits).stripTrailingZeros(),
                    p
            );
            homomorphic(
                    BinaryFraction::negate,
                    Function.identity(),
                    BinaryFraction::negate,
                    BinaryFraction::shiftRight,
                    BinaryFraction::shiftRight,
                    p
            );
            BinaryFraction shifted = p.a.shiftRight(p.b);
            shifted.validate();
            assertEquals(p, shifted, shiftRight_simplest(p.a, p.b));
            assertEquals(p, shifted, p.a.shiftLeft(-p.b));
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            assertEquals(bf, bf.shiftRight(0), bf);
        }

        //overflow and underflow not tested
    }

    private static void compareImplementationsShiftRight() {
        Map<String, Function<Pair<BinaryFraction, Integer>, BinaryFraction>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> shiftRight_simplest(p.a, p.b));
        functions.put("standard", p -> p.a.shiftRight(p.b));
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() - p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        compareImplementations("shiftRight(int)", take(LIMIT, ps), functions);
    }

    private static void propertiesSum() {
        initialize("sum(Iterable<BinaryFraction>)");
        propertiesFoldHelper(
                LIMIT,
                P,
                P.binaryFractions(),
                BinaryFraction::add,
                BinaryFraction::sum,
                BinaryFraction::validate,
                true
        );

        for (List<BinaryFraction> bfs : take(LIMIT, P.lists(P.binaryFractions()))) {
            homomorphic(
                    xs -> map(BinaryFraction::bigDecimalValue, xs),
                    x -> x.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::sum,
                    xs -> IterableUtils.sumBigDecimal(xs).stripTrailingZeros(),
                    bfs
            );
        }
    }

    private static void propertiesProduct() {
        initialize("product(Iterable<BinaryFraction>)");
        propertiesFoldHelper(
                LIMIT,
                P,
                P.binaryFractions(),
                BinaryFraction::multiply,
                BinaryFraction::product,
                BinaryFraction::validate,
                true
        );

        for (List<BinaryFraction> bfs : take(LIMIT, P.lists(P.binaryFractions()))) {
            homomorphic(
                    xs -> map(BinaryFraction::bigDecimalValue, xs),
                    x -> x.bigDecimalValue().stripTrailingZeros(),
                    BinaryFraction::product,
                    xs -> IterableUtils.productBigDecimal(xs).stripTrailingZeros(),
                    bfs
            );
        }
    }

    private static void propertiesDelta() {
        initialize("delta(Iterable<BinaryFraction>)");
        propertiesDeltaHelper(
                LIMIT,
                P,
                P.binaryFractions(),
                BinaryFraction::negate,
                BinaryFraction::subtract,
                BinaryFraction::delta,
                BinaryFraction::validate
        );

        for (List<BinaryFraction> bfs : take(LIMIT, P.listsAtLeast(1, P.binaryFractions()))) {
            homomorphic(
                    xs -> map(BinaryFraction::bigDecimalValue, xs),
                    deltas -> toList(map(delta -> delta.bigDecimalValue().stripTrailingZeros(), deltas)),
                    BinaryFraction::delta,
                    bds -> toList(map(BigDecimal::stripTrailingZeros, IterableUtils.deltaBigDecimal(bds))),
                    bfs
            );
        }
    }

    private static void propertiesFloor() {
        initialize("floor()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigInteger floor = bf.floor();
            assertTrue(bf, le(of(floor), bf));
            assertTrue(bf, le(bf.subtract(of(floor)), ONE));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i, of(i).floor(), i);
        }
    }

    private static void propertiesCeiling() {
        initialize("ceiling()");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            BigInteger ceiling = bf.ceiling();
            assertTrue(bf, ge(of(ceiling), bf));
            assertTrue(bf, le(of(ceiling).subtract(bf), ONE));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i, of(i).ceiling(), i);
        }
    }

    private static void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, IterableProvider::binaryFractions);
    }

    private static void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, IterableProvider::binaryFractions);
    }

    private static void propertiesCompareTo() {
        initialize("compareTo(BinaryFraction)");
        propertiesCompareToHelper(LIMIT, P, IterableProvider::binaryFractions);

        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            homomorphic(
                    BinaryFraction::bigDecimalValue,
                    BinaryFraction::bigDecimalValue,
                    Function.identity(),
                    BinaryFraction::compareTo,
                    BigDecimal::compareTo,
                    p
            );
        }
    }

    private static void propertiesRead() {
        initialize("read(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                BINARY_FRACTION_CHARS,
                P.binaryFractions(),
                BinaryFraction::read,
                BinaryFraction::validate,
                true
        );
    }

    private static void propertiesFindIn() {
        initialize("findIn(String)");
        propertiesFindInHelper(
                LIMIT,
                P,
                P.binaryFractions(),
                BinaryFraction::read,
                BinaryFraction::findIn,
                BinaryFraction::validate
        );
    }

    private static void propertiesToString() {
        initialize("toString()");
        propertiesToStringHelper(LIMIT, BINARY_FRACTION_CHARS, P.binaryFractions(), BinaryFraction::read);
    }
}
