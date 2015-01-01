package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.tail;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class MathUtilsProperties {
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
        System.out.println("MathUtils properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
            propertiesGcd_int_int();
            compareImplementationsGcd_int_int();
            propertiesGcd_long_long();
            compareImplementationsGcd_long_long();
            propertiesBits_int();
            compareImplementationsBits_int();
            propertiesBits_BigInteger();
            compareImplementationsBits_BigInteger();
            propertiesBitsPadded_int_int();
            compareImplementationsBitsPadded_int_int();
            propertiesBitsPadded_int_BigInteger();
            propertiesBigEndianBits_int();
            compareImplementationsBigEndianBits_int();
            propertiesBigEndianBits_BigInteger();
            propertiesBigEndianBitsPadded_int_int();
            compareImplementationsBigEndianBitsPadded_int_int();
            propertiesBigEndianBitsPadded_int_BigInteger();
            propertiesFromBigEndianBits();
            propertiesFromBits();
            propertiesDigits_int_int();
            compareImplementationsDigits_int_int();
            propertiesDigits_BigInteger_BigInteger();
            propertiesDigitsPadded_int_int_int();
            compareImplementationsDigitsPadded_int_int_int();
            propertiesDigitsPadded_int_BigInteger_BigInteger();
            propertiesBigEndianDigits_int_int();
            compareImplementationsBigEndianDigits_int_int();
            propertiesBigEndianDigits_BigInteger_BigInteger();
            propertiesBigEndianDigitsPadded_int_int_int();
            compareImplementationsBigEndianDigitsPadded_int_int_int();
            propertiesBigEndianDigitsPadded_int_BigInteger_BigInteger();
            propertiesFromDigits_int_Iterable_Integer();
            compareImplementationsFromDigits_int_Iterable_Integer();
            propertiesFromDigits_int_Iterable_BigInteger();
            propertiesFromBigEndianDigits_int_Iterable_Integer();
            compareImplementationsFromBigEndianDigits_int_Iterable_Integer();
            propertiesFromBigEndianDigits_int_Iterable_BigInteger();
            propertiesToDigit();
            propertiesFromDigit();
            propertiesToStringBase_int_int();
            compareImplementationsToStringBase_int_int();
            propertiesToStringBase_BigInteger_BigInteger();
            propertiesFromStringBase_int_String();
            compareImplementationsFromStringBase_int_String();
            propertiesFromStringBase_BigInteger_String();
            propertiesLogarithmicMux();
            propertiesLogarithmicDemux();
        }
        System.out.println("Done");
    }

    private static int gcd_int_int_simplest(int x, int y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).intValue();
    }

    private static int gcd_int_int_explicit(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(x), factors(y)));
    }

    private static void propertiesGcd_int_int() {
        initialize();
        System.out.println("\t\ttesting gcd(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            int gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p.toString(), gcd, gcd_int_int_explicit(p.a, p.b));
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
        }
    }

    private static void compareImplementationsGcd_int_int() {
        initialize();
        System.out.println("\t\tcomparing gcd(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_int_int_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_int_int_explicit(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static long gcd_long_long_simplest(long x, long y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).longValue();
    }

    private static long gcd_long_long_explicit(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(BigInteger.valueOf(x)), factors(BigInteger.valueOf(y)))).longValue();
    }

    private static void propertiesGcd_long_long() {
        initialize();
        System.out.println("\t\ttesting gcd(long, long) properties...");

        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p.toString(), gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
        }
    }

    private static void compareImplementationsGcd_long_long() {
        initialize();
        System.out.println("\t\tcomparing gcd(long, long) implementations...");

        long totalTime = 0;
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd_long_long_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        if (P instanceof ExhaustiveProvider) {
            for (Pair<Long, Long> p : take(LIMIT, ps)) {
                long time = System.nanoTime();
                assert p.a != null;
                assert p.b != null;
                gcd_long_long_explicit(p.a, p.b);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");
        } else {
            System.out.println("\t\t\texplicit: too long");
        }

        totalTime = 0;
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull Iterable<Boolean> bits_int_simplest(int n) {
        return bits(BigInteger.valueOf(n));
    }

    private static void propertiesBits_int() {
        initialize();
        System.out.println("\t\ttesting bits(int) properties...");

        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Boolean> bits = toList(bits(i));
            aeq(Integer.toString(i), bits, bits_int_simplest(i));
            aeq(Integer.toString(i), bits, reverse(bigEndianBits(i)));
            assertTrue(Integer.toString(i), all(b -> b != null, bits));
            assertEquals(Integer.toString(i), fromBits(bits).intValueExact(), i);
            assertEquals(Integer.toString(i), bits.size(), BigInteger.valueOf(i).bitLength());
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(Integer.toString(i), bits.isEmpty());
            assertEquals(Integer.toString(i), last(bits), true);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                bits(i);
                fail(Integer.toString(i));
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBits_int() {
        initialize();
        System.out.println("\t\tcomparing bits(int) implementations...");

        long totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bits_int_simplest(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bits(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull Iterable<Boolean> bits_BigInteger_alt(@NotNull BigInteger n) {
        return () -> new Iterator<Boolean>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public Boolean next() {
                boolean bit = remaining.testBit(0);
                remaining = remaining.shiftRight(1);
                return bit;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    private static void propertiesBits_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bits(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<Boolean> bits = toList(bits(i));
            aeq(i.toString(), bits, bits_BigInteger_alt(i));
            aeq(i.toString(), bits, reverse(bigEndianBits(i)));
            assertTrue(i.toString(), all(b -> b != null, bits));
            assertEquals(i.toString(), fromBits(bits), i);
            assertEquals(i.toString(), bits.size(), i.bitLength());
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(i.toString(), bits.isEmpty());
            assertEquals(i.toString(), last(bits), true);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                bits(i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBits_BigInteger() {
        initialize();
        System.out.println("\t\tcomparing bits(BigInteger) implementations...");

        long totalTime = 0;
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            long time = System.nanoTime();
            toList(bits_BigInteger_alt(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\talt: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            long time = System.nanoTime();
            toList(bits(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull Iterable<Boolean> bitsPadded_int_int_simplest(int length, int n) {
        return bitsPadded(length, BigInteger.valueOf(n));
    }

    private static void propertiesBitsPadded_int_int() {
        initialize();
        System.out.println("\t\ttesting bitsPadded(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            aeq(p.toString(), bits, bitsPadded_int_int_simplest(p.b, p.a));
            aeq(p.toString(), bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), Integer.valueOf(bits.size()), p.b);
        }

        ps = P.dependentPairsLogarithmic(P.naturalIntegers(), i -> range(BigInteger.valueOf(i).bitLength()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p.toString(), Integer.valueOf(fromBits(bits).intValueExact()), p.a);
        }

        Iterable<Pair<Integer, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.negativeIntegers());
        } else {
            psFail = P.pairs(P.naturalIntegers(), ((RandomProvider) P).negativeIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeIntegers(), P.naturalIntegers());
        } else {
            psFail = P.pairs(P.negativeIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBitsPadded_int_int() {
        initialize();
        System.out.println("\t\tcomparing bitsPadded(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toList(bitsPadded_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toList(bitsPadded(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBitsPadded_int_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bitsPadded(int, BigInteger) properties...");

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            aeq(p.toString(), bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), Integer.valueOf(bits.size()), p.b);
        }

        ps = P.dependentPairsLogarithmic(P.naturalBigIntegers(), i -> range(i.bitLength()));
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p.toString(), fromBits(bits), p.a);
        }

        Iterable<Pair<BigInteger, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.negativeIntegers());
        } else {
            psFail = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).negativeIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeBigIntegers(), P.naturalIntegers());
        } else {
            psFail = P.pairs(P.negativeBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Boolean> bigEndianBits_int_simplest(int n) {
        return bigEndianBits(BigInteger.valueOf(n));
    }

    private static void propertiesBigEndianBits_int() {
        initialize();
        System.out.println("\t\ttesting bigEndianBits(int) properties...");

        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Boolean> bits = toList(bigEndianBits(i));
            aeq(Integer.toString(i), bits, bigEndianBits_int_simplest(i));
            aeq(Integer.toString(i), bits, reverse(bits(i)));
            assertTrue(Integer.toString(i), all(b -> b != null, bits));
            assertEquals(Integer.toString(i), fromBigEndianBits(bits).intValueExact(), i);
            assertEquals(Integer.toString(i), bits.size(), BigInteger.valueOf(i).bitLength());
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = toList(bigEndianBits(i));
            assertFalse(Integer.toString(i), bits.isEmpty());
            assertEquals(Integer.toString(i), head(bits), true);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                bigEndianBits(i);
                fail(Integer.toString(i));
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBigEndianBits_int() {
        initialize();
        System.out.println("\t\tcomparing bigEndianBits(int) implementations...");

        long totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bigEndianBits_int_simplest(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            toList(bigEndianBits(i));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBigEndianBits_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bigEndianBits(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<Boolean> bits = toList(bigEndianBits(i));
            aeq(i.toString(), bits, reverse(bits(i)));
            assertTrue(i.toString(), all(b -> b != null, bits));
            assertEquals(i.toString(), fromBigEndianBits(bits), i);
            assertEquals(i.toString(), bits.size(), i.bitLength());
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = toList(bigEndianBits(i));
            assertFalse(i.toString(), bits.isEmpty());
            assertEquals(i.toString(), head(bits), true);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                bigEndianBits(i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Boolean> bigEndianBitsPadded_int_int_simplest(int length, int n) {
        return bigEndianBitsPadded(length, BigInteger.valueOf(n));
    }

    private static void propertiesBigEndianBitsPadded_int_int() {
        initialize();
        System.out.println("\t\ttesting bigEndianBitsPadded(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bigEndianBitsPadded(p.b, p.a));
            aeq(p.toString(), bits, bigEndianBitsPadded_int_int_simplest(p.b, p.a));
            aeq(p.toString(), bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), Integer.valueOf(bits.size()), p.b);
        }

        ps = P.dependentPairsLogarithmic(P.naturalIntegers(), i -> range(BigInteger.valueOf(i).bitLength()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bigEndianBitsPadded(p.b, p.a));
            assertEquals(p.toString(), Integer.valueOf(fromBigEndianBits(bits).intValueExact()), p.a);
        }

        Iterable<Pair<Integer, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.negativeIntegers());
        } else {
            psFail = P.pairs(P.naturalIntegers(), ((RandomProvider) P).negativeIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeIntegers(), P.naturalIntegers());
        } else {
            psFail = P.pairs(P.negativeIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBigEndianBitsPadded_int_int() {
        initialize();
        System.out.println("\t\tcomparing bigEndianBitsPadded(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toList(bigEndianBitsPadded_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toList(bigEndianBitsPadded(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBigEndianBitsPadded_int_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bigEndianBitsPadded(int, BigInteger) properties...");

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bigEndianBitsPadded(p.b, p.a));
            aeq(p.toString(), bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), Integer.valueOf(bits.size()), p.b);
        }

        ps = P.dependentPairsLogarithmic(P.naturalBigIntegers(), i -> range(i.bitLength()));
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Boolean> bits = toList(bigEndianBitsPadded(p.b, p.a));
            assertEquals(p.toString(), fromBigEndianBits(bits), p.a);
        }

        Iterable<Pair<BigInteger, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.negativeIntegers());
        } else {
            psFail = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).negativeIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeBigIntegers(), P.naturalIntegers());
        } else {
            psFail = P.pairs(P.negativeBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesFromBits() {
        initialize();
        System.out.println("\t\ttesting fromBits(Iterable<Boolean>) properties...");

        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            BigInteger i = fromBits(bs);
            assertTrue(bs.toString(), i.signum() != -1);
            assertEquals(bs.toString(), i, fromBigEndianBits(reverse(bs)));
        }

        Iterable<List<Boolean>> bss = map(xs -> toList(concat(xs, Arrays.asList(true))), P.lists(P.booleans()));
        for (List<Boolean> bs : take(LIMIT, bss)) {
            BigInteger i = fromBits(bs);
            aeq(bs.toString(), bs, bits(i));
        }

        Iterable<List<Boolean>> failBss = map(p -> {
            assert p.a != null;
            assert p.b != null;
            return toList(insert(p.a, p.b, null));
        }, (Iterable<Pair<List<Boolean>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.booleans()),
                bs -> range(0, bs.size())
        ));
        for (List<Boolean> bs : take(LIMIT, failBss)) {
            try {
                fromBits(bs);
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesFromBigEndianBits() {
        initialize();
        System.out.println("\t\ttesting fromBigEndianBits(Iterable<Boolean>) properties...");

        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            BigInteger i = fromBigEndianBits(bs);
            assertTrue(bs.toString(), i.signum() != -1);
            assertEquals(bs.toString(), i, fromBits(reverse(bs)));
        }

        for (List<Boolean> bs : take(LIMIT, map(xs -> toList(cons(true, xs)), P.lists(P.booleans())))) {
            BigInteger i = fromBigEndianBits(bs);
            aeq(bs.toString(), bs, bigEndianBits(i));
        }

        Iterable<List<Boolean>> failBss = map(p -> {
            assert p.a != null;
            assert p.b != null;
            return toList(insert(p.a, p.b, null));
        }, (Iterable<Pair<List<Boolean>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.booleans()),
                bs -> range(0, bs.size())
        ));
        for (List<Boolean> bs : take(LIMIT, failBss)) {
            try {
                fromBigEndianBits(bs);
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> digits_int_int_simplest(int base, int n) {
        return map(BigInteger::intValue, digits(BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private static void propertiesDigits_int_int() {
        initialize();
        System.out.println("\t\ttesting digits(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(digits(p.b, p.a));
            aeq(p.toString(), digits, digits_int_int_simplest(p.b, p.a));
            aeq(p.toString(), digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p.toString(), Integer.valueOf(fromDigits(p.b, digits).intValueExact()), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(digits(p.b, p.a));
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), last(digits), Integer.valueOf(0));
            int targetDigitCount = ceilingLog(BigInteger.valueOf(p.b), BigInteger.valueOf(p.a)).intValueExact();
            if (BigInteger.valueOf(p.b).pow(targetDigitCount).equals(BigInteger.valueOf(p.a))) {
                targetDigitCount++;
            }
            assertEquals(p.toString(), digits.size(), targetDigitCount);
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };
        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Integer> digits = toList(digits(2, i));
            aeq(Integer.toString(i), map(digitsToBits, digits), bits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(Integer.toString(i), isEmpty(digits(i, 0)));
        }

        Iterable<Pair<Integer, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeDown(1));
        } else {
            psFail = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeIntegers(), P.rangeUp(2));
        } else {
            psFail = P.pairs(P.negativeIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsDigits_int_int() {
        initialize();
        System.out.println("\t\tcomparing digits(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long time = System.nanoTime();
            toList(digits_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long time = System.nanoTime();
            toList(digits(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesDigits_BigInteger_BigInteger() {
        initialize();
        System.out.println("\t\ttesting digits(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.naturalBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(digits(p.b, p.a));
            aeq(p.toString(), digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i.signum() != -1 && lt(i, p.b), digits));
            assertEquals(p.toString(), fromDigits(p.b, digits), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.positiveBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            ps = P.pairs(
                    P.positiveBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(digits(p.b, p.a));
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), last(digits), BigInteger.ZERO);
            int targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
            if (p.b.pow(targetDigitCount).equals(p.a)) {
                targetDigitCount++;
            }
            assertEquals(p.toString(), digits.size(), targetDigitCount);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<BigInteger> digits = toList(digits(BigInteger.valueOf(2), i));
            aeq(i.toString(), map(digitsToBits, digits), bits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(BigInteger.valueOf(2)))) {
            assertTrue(i.toString(), isEmpty(digits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.naturalBigIntegers(),
                    P.rangeDown(BigInteger.ONE)
            );
        } else {
            psFail = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.negativeBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            psFail = P.pairs(
                    P.negativeBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> digitsPadded_int_int_int_simplest(int length, int base, int n) {
        return map(BigInteger::intValue, digitsPadded(length, BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private static void propertiesDigitsPadded_int_int_int() {
        initialize();
        System.out.println("\t\ttesting digitsPadded(int, int, int) properties...");

        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<Integer> digits = toList(digitsPadded(t.a, t.b, t.c));
            aeq(t.toString(), digits, digitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeq(t.toString(), digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t.toString(), Integer.valueOf(digits.size()), t.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<Integer, Integer>>) ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalIntegers(),
                                    P.rangeUp(2)
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a > 0) {
                                    targetDigitCount = ceilingLog(
                                            BigInteger.valueOf(p.b),
                                            BigInteger.valueOf(p.a)
                                    ).intValueExact();
                                    BigInteger x = BigInteger.valueOf(p.b).pow(targetDigitCount);
                                    BigInteger y = BigInteger.valueOf(p.a);
                                    //noinspection SuspiciousNameCombination
                                    if (x.equals(y)) {
                                        targetDigitCount++;
                                    }
                                }
                                return P.rangeUp(targetDigitCount);
                            }
                    )
            );
        } else {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<Integer, Integer>>) P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a > 0) {
                                    targetDigitCount = ceilingLog(
                                            BigInteger.valueOf(p.b),
                                            BigInteger.valueOf(p.a)
                                    ).intValueExact();
                                    BigInteger x = BigInteger.valueOf(p.b).pow(targetDigitCount);
                                    BigInteger y = BigInteger.valueOf(p.a);
                                    //noinspection SuspiciousNameCombination
                                    if (x.equals(y)) {
                                        targetDigitCount++;
                                    }
                                }
                                final int c = targetDigitCount;
                                return (Iterable<Integer>) map(
                                        i -> i + c,
                                        ((RandomProvider) P).naturalIntegersGeometric(20)
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<Integer> digits = toList(digitsPadded(t.a, t.b, t.c));
            assertEquals(t.toString(), Integer.valueOf(fromDigits(t.b, digits).intValueExact()), t.c);
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(digitsPadded(p.b, 2, p.a));
            aeq(p.toString(), map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail;
        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.negativeIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            tsFail = P.triples(is, map(i -> i + 2, is), P.negativeIntegers());
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.negativeIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).negativeIntegersGeometric(20),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)),
                    P.naturalIntegers()
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.negativeIntegers())),
                            P.negativeIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).naturalIntegersGeometric(20),
                    map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20)),
                    P.negativeIntegers()
            );
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsDigitsPadded_int_int_int() {
        initialize();
        System.out.println("\t\tcomparing digitsPadded(int, int, int) implementations...");

        long totalTime = 0;
        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            toList(digitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            toList(digitsPadded(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesDigitsPadded_int_BigInteger_BigInteger() {
        initialize();
        System.out.println("\t\ttesting digitsPadded(int, BigInteger, BigInteger) properties...");

        Iterable<Triple<Integer, BigInteger, BigInteger>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> BigInteger.valueOf(i + 2), P.naturalIntegers())),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.naturalBigIntegers());
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<BigInteger> digits = toList(digitsPadded(t.a, t.b, t.c));
            aeq(t.toString(), digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t.toString(), Integer.valueOf(digits.size()), t.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<BigInteger, BigInteger>>) ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalBigIntegers(),
                                    P.rangeUp(BigInteger.valueOf(2))
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a.signum() == 1) {
                                    targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
                                    //noinspection SuspiciousNameCombination
                                    if (p.b.pow(targetDigitCount).equals(p.a)) {
                                        targetDigitCount++;
                                    }
                                }
                                return P.rangeUp(targetDigitCount);
                            }
                    )
            );
        } else {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<BigInteger, BigInteger>>) P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            ((RandomProvider) P).naturalIntegersGeometric(20)
                                    )
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a.signum() == 1) {
                                    targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
                                    //noinspection SuspiciousNameCombination
                                    if (p.b.pow(targetDigitCount).equals(p.a)) {
                                        targetDigitCount++;
                                    }
                                }
                                final int c = targetDigitCount;
                                return (Iterable<Integer>) map(
                                        i -> i + c,
                                        ((RandomProvider) P).naturalIntegersGeometric(20)
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<BigInteger> digits = toList(digitsPadded(t.a, t.b, t.c));
            assertEquals(t.toString(), fromDigits(t.b, digits), t.c);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(digitsPadded(p.b, BigInteger.valueOf(2), p.a));
            aeq(p.toString(), map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail;
        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.negativeBigIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            tsFail = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.negativeBigIntegers());
        }

        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.negativeIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).negativeIntegersGeometric(20),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20)),
                    P.naturalBigIntegers()
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> BigInteger.valueOf(i + 2), P.negativeIntegers())),
                            P.negativeBigIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).naturalIntegersGeometric(20),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20)),
                    P.negativeBigIntegers()
            );
        }

        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> bigEndianDigits_int_int_simplest(int base, int n) {
        return map(BigInteger::intValue, bigEndianDigits(BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private static void propertiesBigEndianDigits_int_int() {
        initialize();
        System.out.println("\t\ttesting bigEndianDigits(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(bigEndianDigits(p.b, p.a));
            aeq(p.toString(), digits, bigEndianDigits_int_int_simplest(p.b, p.a));
            aeq(p.toString(), digits, reverse(digits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p.toString(), Integer.valueOf(fromBigEndianDigits(p.b, digits).intValueExact()), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(bigEndianDigits(p.b, p.a));
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), head(digits), Integer.valueOf(0));
            int targetDigitCount = ceilingLog(BigInteger.valueOf(p.b), BigInteger.valueOf(p.a)).intValueExact();
            if (BigInteger.valueOf(p.b).pow(targetDigitCount).equals(BigInteger.valueOf(p.a))) {
                targetDigitCount++;
            }
            assertEquals(p.toString(), digits.size(), targetDigitCount);
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };
        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Integer> digits = toList(bigEndianDigits(2, i));
            aeq(Integer.toString(i), map(digitsToBits, digits), bigEndianBits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(Integer.toString(i), isEmpty(bigEndianDigits(i, 0)));
        }

        Iterable<Pair<Integer, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeDown(1));
        } else {
            psFail = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.negativeIntegers(), P.rangeUp(2));
        } else {
            psFail = P.pairs(P.negativeIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsBigEndianDigits_int_int() {
        initialize();
        System.out.println("\t\tcomparing bigEndianDigits(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long time = System.nanoTime();
            toList(bigEndianDigits_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            long time = System.nanoTime();
            toList(bigEndianDigits(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBigEndianDigits_BigInteger_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bigEndianDigits(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.naturalBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(bigEndianDigits(p.b, p.a));
            aeq(p.toString(), digits, reverse(digits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i.signum() != -1 && lt(i, p.b), digits));
            assertEquals(p.toString(), fromBigEndianDigits(p.b, digits), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.positiveBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            ps = P.pairs(
                    P.positiveBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(bigEndianDigits(p.b, p.a));
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), head(digits), BigInteger.ZERO);
            int targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
            if (p.b.pow(targetDigitCount).equals(p.a)) {
                targetDigitCount++;
            }
            assertEquals(p.toString(), digits.size(), targetDigitCount);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<BigInteger> digits = toList(bigEndianDigits(BigInteger.valueOf(2), i));
            aeq(i.toString(), map(digitsToBits, digits), bigEndianBits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(BigInteger.valueOf(2)))) {
            assertTrue(i.toString(), isEmpty(bigEndianDigits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.naturalBigIntegers(),
                    P.rangeDown(BigInteger.ONE)
            );
        } else {
            psFail = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.negativeBigIntegers(),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            psFail = P.pairs(
                    P.negativeBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> bigEndianDigitsPadded_int_int_int_simplest(int length, int base, int n) {
        return map(
                BigInteger::intValue,
                bigEndianDigitsPadded(length, BigInteger.valueOf(base), BigInteger.valueOf(n))
        );
    }

    private static void propertiesBigEndianDigitsPadded_int_int_int() {
        initialize();
        System.out.println("\t\ttesting bigEndianDigitsPadded(int, int, int) properties...");

        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<Integer> digits = toList(bigEndianDigitsPadded(t.a, t.b, t.c));
            aeq(t.toString(), digits, bigEndianDigitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeq(t.toString(), digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t.toString(), Integer.valueOf(digits.size()), t.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<Integer, Integer>>) ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalIntegers(),
                                    P.rangeUp(2)
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a > 0) {
                                    targetDigitCount = ceilingLog(
                                            BigInteger.valueOf(p.b),
                                            BigInteger.valueOf(p.a)
                                    ).intValueExact();
                                    BigInteger x = BigInteger.valueOf(p.b).pow(targetDigitCount);
                                    BigInteger y = BigInteger.valueOf(p.a);
                                    //noinspection SuspiciousNameCombination
                                    if (x.equals(y)) {
                                        targetDigitCount++;
                                    }
                                }
                                return P.rangeUp(targetDigitCount);
                            }
                    )
            );
        } else {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<Integer, Integer>>) P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a > 0) {
                                    targetDigitCount = ceilingLog(
                                            BigInteger.valueOf(p.b),
                                            BigInteger.valueOf(p.a)
                                    ).intValueExact();
                                    BigInteger x = BigInteger.valueOf(p.b).pow(targetDigitCount);
                                    BigInteger y = BigInteger.valueOf(p.a);
                                    //noinspection SuspiciousNameCombination
                                    if (x.equals(y)) {
                                        targetDigitCount++;
                                    }
                                }
                                final int c = targetDigitCount;
                                return (Iterable<Integer>) map(
                                        i -> i + c,
                                        ((RandomProvider) P).naturalIntegersGeometric(20)
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<Integer> digits = toList(bigEndianDigitsPadded(t.a, t.b, t.c));
            assertEquals(t.toString(), Integer.valueOf(fromBigEndianDigits(t.b, digits).intValueExact()), t.c);
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<Integer> digits = toList(bigEndianDigitsPadded(p.b, 2, p.a));
            aeq(p.toString(), map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail;
        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.negativeIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            tsFail = P.triples(is, map(i -> i + 2, is), P.negativeIntegers());
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.negativeIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).negativeIntegersGeometric(20),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)),
                    P.naturalIntegers()
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.negativeIntegers())),
                            P.negativeIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).naturalIntegersGeometric(20),
                    map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20)),
                    P.negativeIntegers()
            );
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsBigEndianDigitsPadded_int_int_int() {
        initialize();
        System.out.println("\t\tcomparing bigEndianDigitsPadded(int, int, int) implementations...");

        long totalTime = 0;
        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            toList(bigEndianDigitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            toList(bigEndianDigitsPadded(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBigEndianDigitsPadded_int_BigInteger_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bigEndianDigitsPadded(int, BigInteger, BigInteger) properties...");

        Iterable<Triple<Integer, BigInteger, BigInteger>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> BigInteger.valueOf(i + 2), P.naturalIntegers())),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.naturalBigIntegers());
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<BigInteger> digits = toList(bigEndianDigitsPadded(t.a, t.b, t.c));
            aeq(t.toString(), digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t.toString(), Integer.valueOf(digits.size()), t.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<BigInteger, BigInteger>>) ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalBigIntegers(),
                                    P.rangeUp(BigInteger.valueOf(2))
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a.signum() == 1) {
                                    targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
                                    //noinspection SuspiciousNameCombination
                                    if (p.b.pow(targetDigitCount).equals(p.a)) {
                                        targetDigitCount++;
                                    }
                                }
                                return P.rangeUp(targetDigitCount);
                            }
                    )
            );
        } else {
            ts = map(
                    q -> {
                        assert q.a != null;
                        return new Triple<>(q.b, q.a.b, q.a.a);
                    },
                    P.dependentPairsLogarithmic(
                            (Iterable<Pair<BigInteger, BigInteger>>) P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            ((RandomProvider) P).naturalIntegersGeometric(20)
                                    )
                            ),
                            p -> {
                                assert p.a != null;
                                assert p.b != null;
                                int targetDigitCount = 0;
                                if (p.a.signum() == 1) {
                                    targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
                                    //noinspection SuspiciousNameCombination
                                    if (p.b.pow(targetDigitCount).equals(p.a)) {
                                        targetDigitCount++;
                                    }
                                }
                                final int c = targetDigitCount;
                                return (Iterable<Integer>) map(
                                        i -> i + c,
                                        ((RandomProvider) P).naturalIntegersGeometric(20)
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            List<BigInteger> digits = toList(bigEndianDigitsPadded(t.a, t.b, t.c));
            assertEquals(t.toString(), fromBigEndianDigits(t.b, digits), t.c);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            List<BigInteger> digits = toList(bigEndianDigitsPadded(p.b, BigInteger.valueOf(2), p.a));
            aeq(p.toString(), map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail;
        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.negativeBigIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            tsFail = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.negativeBigIntegers());
        }

        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.negativeIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).negativeIntegersGeometric(20),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20)),
                    P.naturalBigIntegers()
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            tsFail = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> BigInteger.valueOf(i + 2), P.negativeIntegers())),
                            P.negativeBigIntegers()
                    )
            );
        } else {
            tsFail = P.triples(
                    ((RandomProvider) P).naturalIntegersGeometric(20),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20)),
                    P.negativeBigIntegers()
            );
        }

        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull BigInteger fromDigits_int_Iterable_Integer_simplest(
            int base,
            @NotNull Iterable<Integer> digits
    ) {
        //noinspection Convert2MethodRef
        return fromDigits(BigInteger.valueOf(base), map(i -> BigInteger.valueOf(i), digits));
    }

    private static void propertiesFromDigits_int_Iterable_Integer() {
        initialize();
        System.out.println("\t\ttesting fromDigits(int, Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> {
            assert p.a != null;
            return all(i -> i < p.b, p.a);
        }, unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p.toString(), n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> {
            assert p.a != null;
            return p.a.isEmpty() || last(p.a) != 0;
        }, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromDigits(p.b, p.a);
            aeq(p.toString(), p.a, map(BigInteger::intValueExact, digits(BigInteger.valueOf(p.b), n)));
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        for (List<Integer> is : take(LIMIT, P.lists(P.range(0, 1)))) {
            assertEquals(is.toString(), fromDigits(2, is), fromBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<Integer>, Integer>> unfilteredPsFail;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.integers()),
                    P.rangeDown(1)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(((RandomProvider) P).integersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> psFail = filter(p -> {
            assert p.a != null;
            return all(i -> i < p.b, p.a);
        }, unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.integers()),
                    P.rangeUp(2)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(((RandomProvider) P).integersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> i < 0, p.a);
        }, unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsFromDigits_int_Iterable_Integer() {
        initialize();
        System.out.println("\t\tcomparing fromDigits(int, Iterable<Integer>) implementations...");

        long totalTime = 0;
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromDigits_int_Iterable_Integer_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromDigits(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesFromDigits_int_Iterable_BigInteger() {
        initialize();
        System.out.println("\t\ttesting fromDigits(int, Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> {
            assert p.a != null;
            return all(i -> lt(i, p.b), p.a);
        }, unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> {
            assert p.a != null;
            return p.a.isEmpty() || !last(p.a).equals(BigInteger.ZERO);
        }, ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromDigits(p.b, p.a);
            aeq(p.toString(), p.a, digits(p.b, n));
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        for (List<BigInteger> is : take(LIMIT, P.lists(P.range(BigInteger.ZERO, BigInteger.ONE)))) {
            assertEquals(is.toString(), fromDigits(BigInteger.valueOf(2), is), fromBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.bigIntegers()),
                    P.rangeDown(BigInteger.ONE)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).integersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filter(p -> {
            assert p.a != null;
            return all(i -> lt(i, p.b), p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.bigIntegers()),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).integersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> i.signum() == -1, p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> ge(i, p.b), p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull BigInteger fromBigEndianDigits_int_Iterable_Integer_simplest(
            int base,
            @NotNull Iterable<Integer> digits
    ) {
        //noinspection Convert2MethodRef
        return fromBigEndianDigits(BigInteger.valueOf(base), map(i -> BigInteger.valueOf(i), digits));
    }

    private static void propertiesFromBigEndianDigits_int_Iterable_Integer() {
        initialize();
        System.out.println("\t\ttesting fromBigEndianDigits(int, Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> {
            assert p.a != null;
            return all(i -> i < p.b, p.a);
        }, unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromBigEndianDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p.toString(), n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> {
            assert p.a != null;
            return p.a.isEmpty() || head(p.a) != 0;
        }, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeq(p.toString(), p.a, map(BigInteger::intValueExact, bigEndianDigits(BigInteger.valueOf(p.b), n)));
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        for (List<Integer> is : take(LIMIT, P.lists(P.range(0, 1)))) {
            assertEquals(is.toString(), fromBigEndianDigits(2, is), fromBigEndianBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<Integer>, Integer>> unfilteredPsFail;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.integers()),
                    P.rangeDown(1)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(((RandomProvider) P).integersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> psFail = filter(p -> {
            assert p.a != null;
            return all(i -> i < p.b, p.a);
        }, unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.integers()),
                    P.rangeUp(2)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(((RandomProvider) P).integersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> i < 0, p.a);
        }, unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsFromBigEndianDigits_int_Iterable_Integer() {
        initialize();
        System.out.println("\t\tcomparing fromBigEndianDigits(int, Iterable<Integer>) implementations...");

        long totalTime = 0;
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromBigEndianDigits_int_Iterable_Integer_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromBigEndianDigits(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesFromBigEndianDigits_int_Iterable_BigInteger() {
        initialize();
        System.out.println("\t\ttesting fromBigEndianDigits(int, Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> {
            assert p.a != null;
            return all(i -> lt(i, p.b), p.a);
        }, unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> {
            assert p.a != null;
            return p.a.isEmpty() || !head(p.a).equals(BigInteger.ZERO);
        }, ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeq(p.toString(), p.a, bigEndianDigits(p.b, n));
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        for (List<BigInteger> is : take(LIMIT, P.lists(P.range(BigInteger.ZERO, BigInteger.ONE)))) {
            assertEquals(
                    is.toString(),
                    fromBigEndianDigits(BigInteger.valueOf(2), is), fromBigEndianBits(map(digitsToBits, is))
            );
        }

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.bigIntegers()),
                    P.rangeDown(BigInteger.ONE)
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).integersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filter(p -> {
            assert p.a != null;
            return all(i -> lt(i, p.b), p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            unfilteredPsFail = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.bigIntegers()),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            unfilteredPsFail = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).integersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> i.signum() == -1, p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> {
            assert p.a != null;
            return any(i -> ge(i, p.b), p.a);
        }, unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesToDigit() {
        initialize();
        System.out.println("\t\ttesting toDigit(int) properties...");

        for (int i : take(LIMIT, P.range(0, 35))) {
            char digit = toDigit(i);
            assertTrue(Integer.toString(i), elem(digit, range('0', '9')) || elem(digit, range('A', 'Z')));
            assertEquals(Integer.toString(i), i, fromDigit(digit));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                toDigit(i);
                fail(Integer.toString(i));
            } catch (IllegalArgumentException ignored) {}
        }

        for (int i : take(LIMIT, P.rangeUp(36))) {
            try {
                toDigit(i);
                fail(Integer.toString(i));
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesFromDigit() {
        initialize();
        System.out.println("\t\ttesting fromDigit(char) properties...");

        for (char c : take(LIMIT, IterableUtils.mux(Arrays.asList(P.range('0', '9'), P.range('A', 'Z'))))) {
            int i = fromDigit(c);
            assertTrue(Character.toString(c), i >= 0 && i < 36);
            assertEquals(Character.toString(c), c, toDigit(i));
        }

        Iterable<Character> csFail = filter(
                d -> !elem(d, range('0', '9')) && !elem(d, range('A', 'Z')),
                P.characters()
        );
        for (char c : take(LIMIT, csFail)) {
            try {
                fromDigit(c);
                fail(Character.toString(c));
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull String toStringBase_int_int_simplest(int base, int n) {
        return toStringBase(BigInteger.valueOf(base), BigInteger.valueOf(n));
    }

    private static void propertiesToStringBase_int_int() {
        initialize();
        System.out.println("\t\ttesting toStringBase(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            assertEquals(p.toString(), toStringBase_int_int_simplest(p.b, p.a), s);
            assertEquals(p.toString(), fromStringBase(p.b, s), BigInteger.valueOf(p.a));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.range(2, 36));
        } else {
            ps = P.pairs(P.range(2, 36));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertTrue(p.toString(), all(c -> elem(c, charsToString(concat(range('0', '9'), range('A', 'Z')))), s));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(37));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 37, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertEquals(p.toString(), head(s), '(');
            assertEquals(p.toString(), last(s), ')');
            s = tail(init(s));
            Iterable<Integer> digits = map(Integer::parseInt, Arrays.asList(s.split("\\)\\(")));
            assertFalse(p.toString(), isEmpty(digits));
            assertTrue(p.toString(), p.a == 0 || head(digits) != 0);
            assertTrue(p.toString(), all(d -> d >= 0 && d < p.b, digits));
        }

        Iterable<Pair<Integer, Integer>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeDown(1));
        } else {
            psFail = P.pairs(P.integers(), map(i -> i + 2, ((RandomProvider) P).negativeIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                toStringBase(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsToStringBase_int_int() {
        initialize();
        System.out.println("\t\tcomparing toStringBase(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toStringBase_int_int_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            toStringBase(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesToStringBase_BigInteger_BigInteger() {
        initialize();
        System.out.println("\t\ttesting toStringBase(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            assertEquals(p.toString(), fromStringBase(p.b, s), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.bigIntegers(),
                    P.range(BigInteger.valueOf(2), BigInteger.valueOf(36))
            );
        } else {
            ps = P.pairs(P.range(BigInteger.valueOf(2), BigInteger.valueOf(36)));
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertTrue(p.toString(), all(c -> elem(c, charsToString(concat(range('0', '9'), range('A', 'Z')))), s));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(BigInteger.valueOf(37)));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 37), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertEquals(p.toString(), head(s), '(');
            assertEquals(p.toString(), last(s), ')');
            s = tail(init(s));
            Iterable<BigInteger> digits = map(BigInteger::new, Arrays.asList(s.split("\\)\\(")));
            assertFalse(p.toString(), isEmpty(digits));
            assertTrue(p.toString(), p.a.equals(BigInteger.ZERO) || !head(digits).equals(BigInteger.ZERO));
            assertTrue(p.toString(), all(d -> d.signum() != -1 && lt(d, p.b), digits));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeDown(BigInteger.ONE));
        } else {
            psFail = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                toStringBase(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull BigInteger fromStringBase_int_String_simplest(int base, @NotNull String s) {
        return fromStringBase(BigInteger.valueOf(base), s);
    }

    private static void propertiesFromStringBase_int_String() {
        initialize();
        System.out.println("\t\ttesting fromString(int, String) properties...");

        Iterable<Pair<Integer, String>> ps = P.dependentPairs(
                P.rangeUp(2),
                b -> {
                    Iterable<String> positiveStrings;
                    if (b <= 36) {
                        positiveStrings = P.strings(map(MathUtils::toDigit, P.range(0, b - 1)));
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                P.lists(P.range(0, b - 1))
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), fromStringBase_int_String_simplest(p.a, p.b), i);
        }

        ps = P.dependentPairs(
                P.rangeUp(2),
                b -> {
                    Iterable<String> positiveStrings;
                    if (b <= 36) {
                        positiveStrings = filter(
                                s -> !s.isEmpty() && head(s) != '0',
                                P.strings(map(MathUtils::toDigit, P.range(0, b - 1)))
                        );
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                filter(
                                        is -> !is.isEmpty() && head(is) != 0,
                                        (Iterable<List<Integer>>) P.lists(P.range(0, b - 1))
                                )
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), toStringBase(BigInteger.valueOf(p.a), i), p.b);
        }

        for (Pair<Integer, String> p : take(LIMIT, P.pairs(P.rangeDown(1), P.strings()))) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromStringBase(p.a, p.b);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        //improper String left untested
    }

    private static void compareImplementationsFromStringBase_int_String() {
        initialize();
        System.out.println("\t\tcomparing fromStringBase(int, String) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, String>> ps = P.dependentPairs(
                P.rangeUp(2),
                b -> {
                    Iterable<String> positiveStrings;
                    if (b <= 36) {
                        positiveStrings = P.strings(map(MathUtils::toDigit, P.range(0, b - 1)));
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                P.lists(P.range(0, b - 1))
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromStringBase_int_String_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            assert p.a != null;
            assert p.b != null;
            fromStringBase(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesFromStringBase_BigInteger_String() {
        initialize();
        System.out.println("\t\ttesting fromStringBase(BigInteger, String) properties...");

        Iterable<Pair<BigInteger, String>> ps = P.dependentPairs(
                P.rangeUp(BigInteger.valueOf(2)),
                b -> {
                    Iterable<String> positiveStrings;
                    if (le(b, BigInteger.valueOf(36))) {
                        positiveStrings = P.strings(map(MathUtils::toDigit, P.range(0, b.intValueExact() - 1)));
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                P.lists(P.range(BigInteger.ZERO, b.subtract(BigInteger.ONE)))
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<BigInteger, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            fromStringBase(p.a, p.b);
        }

        ps = P.dependentPairs(
                P.rangeUp(BigInteger.valueOf(2)),
                b -> {
                    Iterable<String> positiveStrings;
                    if (le(b, BigInteger.valueOf(36))) {
                        positiveStrings = filter(
                                s -> !s.isEmpty() && head(s) != '0',
                                P.strings(map(MathUtils::toDigit, P.range(0, b.intValueExact() - 1)))
                        );
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                filter(
                                        is -> !is.isEmpty() && !head(is).equals(BigInteger.ZERO),
                                        (Iterable<List<BigInteger>>) P.lists(
                                                P.range(BigInteger.ZERO, b.subtract(BigInteger.ONE))
                                        )
                                )
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<BigInteger, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), toStringBase(p.a, i), p.b);
        }

        for (Pair<BigInteger, String> p : take(LIMIT, P.pairs(P.rangeDown(BigInteger.ONE), P.strings()))) {
            assert p.a != null;
            assert p.b != null;
            try {
                fromStringBase(p.a, p.b);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        //improper String left untested
    }

    private static void propertiesLogarithmicMux() {
        initialize();
        System.out.println("\t\ttesting logarithmicMux(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = P.pairs(P.naturalBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            BigInteger i = logarithmicMux(p.a, p.b);
            assertNotEquals(p.toString(), i.signum(), -1);
            assertEquals(p.toString(), logarithmicDemux(i), p);
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail;
        if (P instanceof ExhaustiveProvider) {
            psFail = P.pairs(P.naturalBigIntegers(), P.negativeBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            psFail = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i), ((RandomProvider) P).negativeIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                logarithmicMux(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            psFail = P.pairs(P.negativeBigIntegers(), P.naturalBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            psFail = P.pairs(
                    P.negativeBigIntegers(),
                    map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            assert p.a != null;
            assert p.b != null;
            try {
                logarithmicMux(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesLogarithmicDemux() {
        initialize();
        System.out.println("\t\ttesting logarithmicDemux(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            Pair<BigInteger, BigInteger> p = logarithmicDemux(i);
            assert p.a != null;
            assert p.b != null;
            assertNotEquals(p.toString(), p.a.signum(), -1);
            assertNotEquals(p.toString(), p.b.signum(), -1);
            assertEquals(p.toString(), logarithmicMux(p.a, p.b), i);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                logarithmicDemux(i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static <T> void aeq(String message, Iterable<T> xs, Iterable<T> ys) {
        assertTrue(message, equal(xs, ys));
    }
}
