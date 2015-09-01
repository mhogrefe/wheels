package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

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
                "getMantissa() by mantissa bit length",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getMantissa,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "getMantissa() by exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getMantissa,
                bf -> Math.abs(bf.getExponent())
        );
    }

    private static void benchmarkGetExponent() {
        initialize();
        benchmark(
                "getExponent() by mantissa bit length",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getExponent,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "getExponent() by exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::getExponent,
                bf -> Math.abs(bf.getExponent())
        );
    }

    private static void benchmarkOf_BigInteger_int() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps = filterInfinite(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        benchmark(
                "of(BigInteger, int) by BigInteger bit length",
                take(LIMIT, ps),
                p -> of(p.a, p.b),
                p -> p.a.bitLength()
        );
        System.out.println();
        benchmark(
                "of(BigInteger, int) by int",
                take(LIMIT, ps),
                p -> of(p.a, p.b),
                p -> Math.abs(p.b)
        );
    }

    private static void benchmarkOf_BigInteger() {
        initialize();
        benchmark(
                "of(BigInteger) by bit length",
                take(LIMIT, P.bigIntegers()),
                BinaryFraction::of,
                BigInteger::bitLength
        );
    }

    private static void benchmarkOf_int() {
        initialize();
        benchmark(
                "of(int) by bit length",
                take(LIMIT, P.integers()),
                BinaryFraction::of,
                i -> BigInteger.valueOf(i).bitLength()
        );
    }

    private static void benchmarkOf_float() {
        initialize();
        benchmark(
                "of(float) by String length",
                take(LIMIT, P.floats()),
                BinaryFraction::of,
                f -> Float.toString(f).length()
        );
    }

    private static void benchmarkOf_double() {
        initialize();
        benchmark(
                "of(double) by String length",
                take(LIMIT, P.doubles()),
                BinaryFraction::of,
                d -> Double.toString(d).length()
        );
    }

    private static void benchmarkBigDecimalValue() {
        initialize();
        benchmark(
                "bigDecimalValue() by mantissa bit length",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::bigDecimalValue,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "bigDecimalValue() by exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::bigDecimalValue,
                bf -> Math.abs(bf.getExponent())
        );
    }

    private static void benchmarkFloatRange() {
        initialize();
        benchmark(
                "floatRange() by mantissa bit length",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::floatRange,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "floatRange() by exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::floatRange,
                bf -> Math.abs(bf.getExponent())
        );
    }

    private static void benchmarkDoubleRange() {
        initialize();
        benchmark(
                "doubleRange() by mantissa bit length",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::doubleRange,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "doubleRange() by exponent",
                take(LIMIT, P.binaryFractions()),
                BinaryFraction::doubleRange,
                bf -> Math.abs(bf.getExponent())
        );
    }

    private static void benchmarkBigIntegerValueExact() {
        initialize();
        benchmark(
                "bigIntegerValueExact() by mantissa bit length",
                take(LIMIT, map(BinaryFraction::of, P.bigIntegers())),
                BinaryFraction::bigIntegerValueExact,
                bf -> bf.getMantissa().bitLength()
        );
        System.out.println();
        benchmark(
                "bigIntegerValueExact() by exponent",
                take(LIMIT, map(BinaryFraction::of, P.bigIntegers())),
                BinaryFraction::bigIntegerValueExact,
                bf -> Math.abs(bf.getExponent())
        );
    }
}
