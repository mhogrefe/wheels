package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.map;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.math.BinaryFraction.of;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class BinaryFractionBenchmarks {
    private static final boolean USE_RANDOM = false;
    private static final @NotNull
    String BINARY_FRACTION_CHARS = " -0123456789<>";
    private static int LIMIT;
    private static final int SMALL_LIMIT = 1000;
    private static final int TINY_LIMIT = 20;
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

    private static void benchmarkGetMantissa() {
        initialize();
        benchmark(
                1000,
                "getMantissa() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getMantissa,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkGetExponent() {
        initialize();
        benchmark(
                1000,
                "getExponent() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getExponent,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkOf_BigInteger_int() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps = filterInfinite(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        benchmark(
                1000,
                "of(BigInteger, int) by BigInteger bit length and absolute int",
                take(LIMIT, ps),
                p -> of(p.a, p.b),
                p -> Arrays.asList(p.a.bitLength(), Math.abs(p.b))
        );
    }

    private static void benchmarkOf_BigInteger() {
        initialize();
        benchmark(
                1000,
                "of(BigInteger) by bit length",
                take(LIMIT, P.bigIntegers()),
                BinaryFraction::of,
                i -> Collections.singletonList(i.bitLength())
        );
    }

    private static void benchmarkOf_int() {
        initialize();
        benchmark(
                1000,
                "of(int) by bit length",
                take(LIMIT, P.integers()),
                BinaryFraction::of,
                i -> Collections.singletonList(BigInteger.valueOf(i).bitLength())
        );
    }

    private static void benchmarkOf_float() {
        initialize();
        benchmark(
                1000,
                "of(float) by String length",
                take(LIMIT, P.floats()),
                BinaryFraction::of,
                f -> Collections.singletonList(Float.toString(f).length())
        );
    }

    private static void benchmarkOf_double() {
        initialize();
        benchmark(
                1000,
                "of(double) by String length",
                take(LIMIT, P.doubles()),
                BinaryFraction::of,
                d -> Collections.singletonList(Double.toString(d).length())
        );
    }

    private static void benchmarkBigDecimalValue() {
        initialize();
        benchmark(
                1000,
                "bigDecimalValue() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::bigDecimalValue,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkFloatRange() {
        initialize();
        benchmark(
                1000,
                "floatRange() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::floatRange,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkDoubleRange() {
        initialize();
        benchmark(
                1000,
                "doubleRange() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::doubleRange,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkBigIntegerValueExact() {
        initialize();
        benchmark(
                1000,
                "bigIntegerValueExact() by mantissa bit length and absolute exponent",
                take(LIMIT, map(BinaryFraction::of, P.bigIntegers())),
                BinaryFraction::bigIntegerValueExact,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkIsInteger() {
        initialize();
        benchmark(
                1000,
                "isInteger() by mantissa bit length and absolute exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::isInteger,
                bf -> Arrays.asList(bf.getMantissa().bitLength(), Math.abs(bf.getExponent()))
        );
    }

    private static void benchmarkAdd() {
        initialize();
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
        benchmark(
                1000,
                "add(BinaryFraction) by sum of mantissa bit lengths and sum of absolute exponents",
                take(LIMIT, ps),
                p -> p.a.add(p.b),
                p -> Arrays.asList(
                        p.a.getMantissa().bitLength() + p.b.getMantissa().bitLength(),
                        Math.abs(p.a.getExponent()) + Math.abs(p.b.getExponent())
                )
        );
    }
}
