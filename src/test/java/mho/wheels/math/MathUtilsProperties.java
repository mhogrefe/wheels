package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.math.MathUtils.demux;
import static mho.wheels.math.MathUtils.mux;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class MathUtilsProperties {
    private static boolean USE_RANDOM;
    private static final int SMALL_LIMIT = 3000;
    private static final int TINY_LIMIT = 10;
    private static int LIMIT;

    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(0x6af477d9a7e54fcaL);
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
            propertiesLcm();
            compareImplementationsLcm();
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
            compareImplementationsFromBits();
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
            propertiesSquareRootMux();
            propertiesSquareRootDemux();
            propertiesMux();
            propertiesDemux();
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
            int gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p.toString(), gcd, gcd_int_int_explicit(p.a, p.b));
            assertEquals(p.toString(), gcd, gcd(p.b, p.a));
            assertEquals(p.toString(), p.a % gcd, 0);
            assertEquals(p.toString(), p.b % gcd, 0);
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (int i : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p.toString(), p.a % i == 0 && p.b % i == 0);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(gcd(i, 1), 1);
        }

        for (int i : take(LIMIT, filter(j -> j != 0, P.integers()))) {
            assertEquals(gcd(i, i), Math.abs(i));
            assertEquals(gcd(i, 0), Math.abs(i));
        }

        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                u -> (u.a != 0 || u.b != 0) && (u.b != 0 || u.c != 0),
                P.triples(P.integers())
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            int gcd1 = gcd(gcd(t.a, t.b), t.c);
            int gcd2 = gcd(t.a, gcd(t.b, t.c));
            assertEquals(t.toString(), gcd1, gcd2);
        }
    }

    private static void compareImplementationsGcd_int_int() {
        initialize();
        System.out.println("\t\tcomparing gcd(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_int_int_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_int_int_explicit(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            long gcd = gcd(p.a, p.b);
            assertEquals(p.toString(), gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p.toString(), gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertEquals(p.toString(), gcd, gcd(p.b, p.a));
            assertTrue(p.toString(), gcd >= 0);
            assertEquals(p.toString(), p.a % gcd, 0);
            assertEquals(p.toString(), p.b % gcd, 0);
            assertEquals(p.toString(), gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (long l : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p.toString(), p.a % l == 0 && p.b % l == 0);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(gcd(l, 1L), 1);
        }

        for (long l : take(LIMIT, filter(m -> m != 0L, P.longs()))) {
            assertEquals(gcd(l, l), Math.abs(l));
            assertEquals(gcd(l, 0L), Math.abs(l));
        }

        Iterable<Triple<Long, Long, Long>> ts = filter(
                u -> (u.a != 0L || u.b != 0L) && (u.b != 0L || u.c != 0L),
                P.triples(P.longs())
        );
        for (Triple<Long, Long, Long> t : take(LIMIT, ts)) {
            long gcd1 = gcd(gcd(t.a, t.b), t.c);
            long gcd2 = gcd(t.a, gcd(t.b, t.c));
            assertEquals(t.toString(), gcd1, gcd2);
        }
    }

    private static void compareImplementationsGcd_long_long() {
        initialize();
        System.out.println("\t\tcomparing gcd(long, long) implementations...");

        long totalTime = 0;
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            gcd_long_long_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        if (P instanceof ExhaustiveProvider) {
            totalTime = 0;
            for (Pair<Long, Long> p : take(LIMIT, ps)) {
                long time = System.nanoTime();
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
            gcd(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static @NotNull BigInteger lcm_explicit(@NotNull BigInteger x, @NotNull BigInteger y) {
        return head(orderedIntersection(rangeBy(x, x), rangeBy(y, y)));
    }

    private static void propertiesLcm() {
        initialize();
        System.out.println("\t\ttesting lcm(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p.toString(), lcm, lcm(p.b, p.a));
            assertEquals(p.toString(), lcm.signum(), 1);
            assertEquals(p.toString(), lcm.mod(p.a), BigInteger.ZERO);
            assertEquals(p.toString(), lcm.mod(p.b), BigInteger.ZERO);
            for (BigInteger i : take(TINY_LIMIT, P.range(BigInteger.ONE, lcm.subtract(BigInteger.ONE)))) {
                assertFalse(p.toString(), i.mod(p.a).equals(BigInteger.ZERO) && i.mod(p.b).equals(BigInteger.ZERO));
            }
        }

        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(
                filter(i -> le(i, BigInteger.valueOf(1000000)), P.withScale(10).positiveBigIntegers())
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p.toString(), lcm, lcm_explicit(p.a, p.b));
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            assertEquals(lcm(i, BigInteger.ONE), i);
            assertEquals(lcm(i, i), i);
        }

        for (Triple<BigInteger, BigInteger, BigInteger> t : take(LIMIT, P.triples(P.positiveBigIntegers()))) {
            BigInteger lcm1 = lcm(lcm(t.a, t.b), t.c);
            BigInteger lcm2 = lcm(t.a, lcm(t.b, t.c));
            assertEquals(t.toString(), lcm1, lcm2);
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.rangeDown(BigInteger.ZERO), P.positiveBigIntegers());
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                lcm(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
            try {
                lcm(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void compareImplementationsLcm() {
        initialize();
        System.out.println("\t\tcomparing lcm(BigInteger, BigInteger) implementations...");

        long totalTime = 0;

        if (P instanceof ExhaustiveProvider) {
            for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
                long time = System.nanoTime();
                lcm_explicit(p.a, p.b);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\texplicit: " + ((double) totalTime) / 1e9 + " s");
        } else {
            System.out.println("\t\t\texplicit: too long");
        }

        totalTime = 0;
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            long time = System.nanoTime();
            lcm(p.a, p.b);
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
            Iterable<Boolean> bitsIterable = bits(i);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(Integer.toString(i), bits, bits_int_simplest(i));
            aeqit(Integer.toString(i), bits, reverse(bigEndianBits(i)));
            assertTrue(Integer.toString(i), all(b -> b != null, bits));
            assertEquals(Integer.toString(i), fromBits(bits).intValueExact(), i);
            assertEquals(Integer.toString(i), bits.size(), BigInteger.valueOf(i).bitLength());
            try {
                bitsIterable.iterator().remove();
                fail(Integer.toString(i));
            } catch (UnsupportedOperationException ignored) {}
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
            Iterable<Boolean> bitsIterable = bits(i);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(i.toString(), bits, bits_BigInteger_alt(i));
            aeqit(i.toString(), bits, reverse(bigEndianBits(i)));
            assertTrue(i.toString(), all(b -> b != null, bits));
            assertEquals(i.toString(), fromBits(bits), i);
            assertEquals(i.toString(), bits.size(), i.bitLength());
            try {
                bitsIterable.iterator().remove();
                fail(i.toString());
            } catch (UnsupportedOperationException ignored) {}
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
            Iterable<Boolean> bitsIterable = bitsPadded(p.b, p.a);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(p.toString(), bits, bitsPadded_int_int_simplest(p.b, p.a));
            aeqit(p.toString(), bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), bits.size(), p.b.intValue());
            try {
                bitsIterable.iterator().remove();
                fail(p.toString());
            } catch (UnsupportedOperationException ignored) {}
        }

        ps = P.dependentPairsLogarithmic(P.naturalIntegers(), i -> rangeUp(BigInteger.valueOf(i).bitLength()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p.toString(), fromBits(bits).intValueExact(), p.a.intValue());
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {
            }
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
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
            toList(bitsPadded_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            Iterable<Boolean> bitsIterable = bitsPadded(p.b, p.a);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(p.toString(), bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), bits.size(), p.b.intValue());
            try {
                bitsIterable.iterator().remove();
                fail(p.toString());
            } catch (UnsupportedOperationException ignored) {}
        }

        ps = P.dependentPairsLogarithmic(P.naturalBigIntegers(), i -> rangeUp(i.bitLength()));
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p.toString(), fromBits(bits), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull List<Boolean> bigEndianBits_int_simplest(int n) {
        return bigEndianBits(BigInteger.valueOf(n));
    }

    private static void propertiesBigEndianBits_int() {
        initialize();
        System.out.println("\t\ttesting bigEndianBits(int) properties...");

        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            aeqit(Integer.toString(i), bits, bigEndianBits_int_simplest(i));
            aeqit(Integer.toString(i), bits, reverse(bits(i)));
            assertTrue(Integer.toString(i), all(b -> b != null, bits));
            assertEquals(Integer.toString(i), fromBigEndianBits(bits).intValueExact(), i);
            assertEquals(Integer.toString(i), bits.size(), BigInteger.valueOf(i).bitLength());
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
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
            bigEndianBits_int_simplest(i);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (int i : take(LIMIT, P.naturalIntegers())) {
            long time = System.nanoTime();
            bigEndianBits(i);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesBigEndianBits_BigInteger() {
        initialize();
        System.out.println("\t\ttesting bigEndianBits(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            aeqit(i.toString(), bits, reverse(bits(i)));
            assertTrue(i.toString(), all(b -> b != null, bits));
            assertEquals(i.toString(), fromBigEndianBits(bits), i);
            assertEquals(i.toString(), bits.size(), i.bitLength());
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
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
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            aeqit(p.toString(), bits, bigEndianBitsPadded_int_int_simplest(p.b, p.a));
            aeqit(p.toString(), bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), bits.size(), p.b.intValue());
        }

        ps = P.dependentPairsLogarithmic(P.naturalIntegers(), i -> rangeUp(BigInteger.valueOf(i).bitLength()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            assertEquals(p.toString(), fromBigEndianBits(bits).intValueExact(), p.a.intValue());
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
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
            toList(bigEndianBitsPadded_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            bigEndianBitsPadded(p.b, p.a);
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
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            aeqit(p.toString(), bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p.toString(), all(b -> b != null, bits));
            assertEquals(p.toString(), bits.size(), p.b.intValue());
        }

        ps = P.dependentPairsLogarithmic(P.naturalBigIntegers(), i -> rangeUp(i.bitLength()));
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            assertEquals(p.toString(), fromBigEndianBits(bits), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull BigInteger fromBits_alt(@NotNull Iterable<Boolean> xs) {
        List<Boolean> bits = toList(xs);
        byte[] bytes = new byte[bits.size() / 8 + 1]; // if bits.size() is a multiple of 8, we get an extra zero to the
        int byteIndex = bytes.length;                 // left which ensures a positive sign
        for (int i = 0; i < bits.size(); i++) {
            int j = i % 8;
            if (j == 0) byteIndex--;
            if (bits.get(i)) {
                bytes[byteIndex] |= 1 << j;
            }
        }
        return new BigInteger(bytes);
    }

    private static void propertiesFromBits() {
        initialize();
        System.out.println("\t\ttesting fromBits(Iterable<Boolean>) properties...");

        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            BigInteger i = fromBits(bs);
            assertEquals(bs.toString(), fromBits_alt(bs), i);
            assertTrue(bs.toString(), i.signum() != -1);
            assertEquals(bs.toString(), i, fromBigEndianBits(reverse(bs)));
        }

        Iterable<List<Boolean>> bss = map(xs -> toList(concat(xs, Arrays.asList(true))), P.lists(P.booleans()));
        for (List<Boolean> bs : take(LIMIT, bss)) {
            BigInteger i = fromBits(bs);
            aeqit(bs.toString(), bs, bits(i));
        }

        Iterable<List<Boolean>> failBss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Boolean>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.booleans()),
                        bs -> range(0, bs.size())
                )
        );
        for (List<Boolean> bs : take(LIMIT, failBss)) {
            try {
                fromBits(bs);
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void compareImplementationsFromBits() {
        initialize();
        System.out.println("\t\tcomparing fromBits(Iterable<Boolean>) implementations...");

        long totalTime = 0;
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            long time = System.nanoTime();
            fromBits_alt(bs);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\talt: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            long time = System.nanoTime();
            fromBits(bs);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
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
            aeqit(bs.toString(), bs, bigEndianBits(i));
        }

        Iterable<List<Boolean>> failBss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Boolean>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.booleans()),
                        bs -> range(0, bs.size())
                )
        );
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
            Iterable<Integer> digitsIterable = digits(p.b, p.a);
            List<Integer> digits = toList(digitsIterable);
            aeqit(p.toString(), digits, digits_int_int_simplest(p.b, p.a));
            aeqit(p.toString(), digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p.toString(), fromDigits(p.b, digits).intValueExact(), p.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(p.toString());
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = toList(digits(p.b, p.a));
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), last(digits).intValue(), 0);
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
            aeqit(Integer.toString(i), map(digitsToBits, digits), bits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(Integer.toString(i), isEmpty(digits(i, 0)));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.rangeDown(1)))) {
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.rangeUp(2)))) {
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
            long time = System.nanoTime();
            toList(digits_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
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
            Iterable<BigInteger> digitsIterable = digits(p.b, p.a);
            List<BigInteger> digits = toList(digitsIterable);
            aeqit(p.toString(), digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i.signum() != -1 && lt(i, p.b), digits));
            assertEquals(p.toString(), fromDigits(p.b, digits), p.a);
            try {
                digitsIterable.iterator().remove();
                fail(p.toString());
            } catch (UnsupportedOperationException ignored) {}
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
            aeqit(i.toString(), map(digitsToBits, digits), bits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(BigInteger.valueOf(2)))) {
            assertTrue(i.toString(), isEmpty(digits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(
                P.naturalBigIntegers(),
                P.rangeDown(BigInteger.valueOf(-1))
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                digits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairs(P.negativeBigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            Iterable<Integer> digitsIterable = digitsPadded(t.a, t.b, t.c);
            List<Integer> digits = toList(digitsIterable);
            aeqit(t.toString(), digits, digitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeqit(t.toString(), digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t.toString(), digits.size(), t.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(t.toString());
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalIntegers(), P.rangeUp(2)),
                            p -> {
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
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
                            ),
                            p -> {
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
            List<Integer> digits = toList(digitsPadded(t.a, t.b, t.c));
            assertEquals(t.toString(), fromDigits(t.b, digits).intValueExact(), t.c.intValue());
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
            List<Integer> digits = toList(digitsPadded(p.b, 2, p.a));
            aeqit(p.toString(), map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(2),
                P.negativeIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(2), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(-1), P.negativeIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            toList(digitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            Iterable<BigInteger> digitsIterable = digitsPadded(t.a, t.b, t.c);
            List<BigInteger> digits = toList(digitsIterable);
            aeqit(t.toString(), digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t.toString(), digits.size(), t.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(t.toString());
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalBigIntegers(),
                                    P.rangeUp(BigInteger.valueOf(2))
                            ),
                            p -> {
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
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            ((RandomProvider) P).naturalIntegersGeometric(20)
                                    )
                            ),
                            p -> {
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
            List<BigInteger> digits = toList(digitsPadded(p.b, BigInteger.valueOf(2), p.a));
            aeqit(p.toString(), map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(BigInteger.valueOf(2)),
                P.negativeBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(BigInteger.valueOf(2)), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(BigInteger.ONE), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
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
            List<Integer> digits = bigEndianDigits(p.b, p.a);
            aeqit(p.toString(), digits, bigEndianDigits_int_int_simplest(p.b, p.a));
            aeqit(p.toString(), digits, reverse(digits(p.b, p.a)));
            assertTrue(p.toString(), all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p.toString(), fromBigEndianDigits(p.b, digits).intValueExact(), p.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = bigEndianDigits(p.b, p.a);
            assertFalse(p.toString(), digits.isEmpty());
            assertNotEquals(p.toString(), head(digits).intValue(), 0);
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
            List<Integer> digits = bigEndianDigits(2, i);
            aeqit(Integer.toString(i), map(digitsToBits, digits), bigEndianBits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(Integer.toString(i), isEmpty(bigEndianDigits(i, 0)));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.rangeDown(1)))) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.rangeUp(2)))) {
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
            long time = System.nanoTime();
            toList(bigEndianDigits_int_int_simplest(p.b, p.a));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            bigEndianDigits(p.b, p.a);
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
            List<BigInteger> digits = bigEndianDigits(p.b, p.a);
            aeqit(p.toString(), digits, reverse(digits(p.b, p.a)));
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
            List<BigInteger> digits = bigEndianDigits(p.b, p.a);
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
            List<BigInteger> digits = bigEndianDigits(BigInteger.valueOf(2), i);
            aeqit(i.toString(), map(digitsToBits, digits), bigEndianBits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(BigInteger.valueOf(2)))) {
            assertTrue(i.toString(), isEmpty(bigEndianDigits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.naturalBigIntegers(), P.rangeDown(BigInteger.ONE));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairs(P.negativeBigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            List<Integer> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            aeqit(t.toString(), digits, bigEndianDigitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeqit(t.toString(), digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t.toString(), digits.size(), t.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalIntegers(), P.rangeUp(2)),
                            p -> {
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
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
                            ),
                            p -> {
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
            List<Integer> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            assertEquals(t.toString(), fromBigEndianDigits(t.b, digits).intValueExact(), t.c.intValue());
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
            List<Integer> digits = bigEndianDigitsPadded(p.b, 2, p.a);
            aeqit(p.toString(), map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(2),
                P.negativeIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(2), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(1), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            toList(bigEndianDigitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            long time = System.nanoTime();
            bigEndianDigitsPadded(t.a, t.b, t.c);
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
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
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
            List<BigInteger> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            aeqit(t.toString(), digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t.toString(), all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t.toString(), digits.size(), t.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                                    P.naturalBigIntegers(),
                                    P.rangeUp(BigInteger.valueOf(2))
                            ),
                            p -> {
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
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairsLogarithmic(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            ((RandomProvider) P).naturalIntegersGeometric(20)
                                    )
                            ),
                            p -> {
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
            List<BigInteger> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
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
            List<BigInteger> digits = bigEndianDigitsPadded(p.b, BigInteger.valueOf(2), p.a);
            aeqit(p.toString(), map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(BigInteger.valueOf(2)),
                P.negativeBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(BigInteger.valueOf(2)), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(BigInteger.ONE), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
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
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p.toString(), n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || last(p.a) != 0, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            aeqit(p.toString(), p.a, map(BigInteger::intValueExact, digits(BigInteger.valueOf(p.b), n)));
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

        Iterable<Pair<List<Integer>, Integer>> unfilteredPsFail = P.pairs(P.lists(P.integers()), P.rangeDown(1));
        Iterable<Pair<List<Integer>, Integer>> psFail = filter(p -> all(i -> i < p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.integers()), P.rangeUp(2));
        psFail = filter(p -> any(i -> i < 0, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
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
            fromDigits_int_Iterable_Integer_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            //noinspection Convert2MethodRef
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || !last(p.a).equals(BigInteger.ZERO), ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            aeqit(p.toString(), p.a, digits(p.b, n));
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        for (List<BigInteger> is : take(LIMIT, P.lists(P.range(BigInteger.ZERO, BigInteger.ONE)))) {
            assertEquals(is.toString(), fromDigits(BigInteger.valueOf(2), is), fromBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail = P.pairs(
                P.lists(P.bigIntegers()),
                P.rangeDown(BigInteger.ONE)
        );
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.bigIntegers()), P.rangeUp(BigInteger.valueOf(2)));
        psFail = filter(p -> any(i -> i.signum() == -1, p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> ge(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
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
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromBigEndianDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p.toString(), n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || head(p.a) != 0, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeqit(p.toString(), p.a, map(BigInteger::intValueExact, bigEndianDigits(BigInteger.valueOf(p.b), n)));
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

        Iterable<Pair<List<Integer>, Integer>> psFail = filter(
                p -> all(i -> i < p.b, p.a),
                P.pairs(P.lists(P.integers()), P.rangeDown(1))
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i < 0, p.a), P.pairs(P.lists(P.integers()), P.rangeUp(2)));
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), P.pairs(P.lists(P.integers()), P.rangeUp(2)));
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
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
            fromBigEndianDigits_int_Iterable_Integer_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            //noinspection Convert2MethodRef
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p.toString(), n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p.toString(), n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || !head(p.a).equals(BigInteger.ZERO), ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeqit(p.toString(), p.a, bigEndianDigits(p.b, n));
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

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail = P.pairs(
                P.lists(P.bigIntegers()),
                P.rangeDown(BigInteger.ONE)
        );
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.bigIntegers()), P.rangeUp(BigInteger.valueOf(2)));
        psFail = filter(p -> any(i -> i.signum() == -1, p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> ge(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
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
            String s = toStringBase(p.b, p.a);
            assertEquals(p.toString(), toStringBase_int_int_simplest(p.b, p.a), s);
            assertEquals(p.toString(), fromStringBase(p.b, s), BigInteger.valueOf(p.a));
        }

        String chars = charsToString(cons('-', concat(range('0', '9'), range('A', 'Z'))));
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.range(2, 36));
        } else {
            ps = P.pairs(P.integers(), P.range(2, 36));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertTrue(p.toString(), all(c -> elem(c, chars), s));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(37));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 37, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
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

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers(), P.rangeDown(1)))) {
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
            toStringBase_int_int_simplest(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
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
            String s = toStringBase(p.b, p.a);
            assertEquals(p.toString(), fromStringBase(p.b, s), p.a);
        }

        String chars = charsToString(cons('-', concat(range('0', '9'), range('A', 'Z'))));
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(
                    P.bigIntegers(),
                    P.range(BigInteger.valueOf(2), BigInteger.valueOf(36))
            );
        } else {
            ps = P.pairs(P.bigIntegers(), P.range(BigInteger.valueOf(2), BigInteger.valueOf(36)));
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertTrue(p.toString(), all(c -> elem(c, chars), s));
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

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers(), P.rangeDown(BigInteger.ONE)))) {
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

        Iterable<Integer> bases;
        if (P instanceof ExhaustiveProvider) {
            bases = P.rangeUp(2);
        } else {
            bases = map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        Iterable<Pair<Integer, String>> ps = P.dependentPairs(
                bases,
                b -> {
                    String chars = "-";
                    if (b < 36) {
                        chars += charsToString(range('0', MathUtils.toDigit(b - 1)));
                    } else {
                        chars += "()0123456789";
                    }
                    Iterable<Character> unfiltered;
                    if (P instanceof ExhaustiveProvider) {
                        unfiltered = fromString(chars);
                    } else {
                        unfiltered = ((RandomProvider) P).uniformSample(chars);
                    }
                    return filter(
                            s -> {
                                try {
                                    fromStringBase(b, s);
                                    return true;
                                } catch (IllegalArgumentException e) {
                                    return false;
                                }
                            },
                            P.strings(unfiltered)
                    );
                }
        );
        for (Pair<Integer, String> p : take(SMALL_LIMIT, ps)) {
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), fromStringBase_int_String_simplest(p.a, p.b), i);
        }

        ps = filter(
                p -> !p.b.isEmpty() && !p.b.startsWith("0") && !p.b.startsWith("-0") && !p.b.startsWith("(0)") &&
                        !p.b.startsWith("-(0)"),
                ps
        );
        for (Pair<Integer, String> p : take(SMALL_LIMIT, ps)) {
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), toStringBase(BigInteger.valueOf(p.a), i), p.b);
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertEquals(Integer.toString(i), fromStringBase(i, ""), BigInteger.ZERO);
        }

        for (Pair<Integer, String> p : take(SMALL_LIMIT, P.pairs(P.rangeDown(1), P.strings()))) {
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
                    return IterableUtils.mux(
                            Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<Integer, String> p : take(SMALL_LIMIT, ps)) {
            long time = System.nanoTime();
            fromStringBase_int_String_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, String> p : take(SMALL_LIMIT, ps)) {
            long time = System.nanoTime();
            fromStringBase(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesFromStringBase_BigInteger_String() {
        initialize();
        System.out.println("\t\ttesting fromStringBase(BigInteger, String) properties...");

        Iterable<BigInteger> bases;
        if (P instanceof ExhaustiveProvider) {
            bases = P.rangeUp(BigInteger.valueOf(2));
        } else {
            bases = map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        Iterable<Pair<BigInteger, String>> ps = P.dependentPairs(
                bases,
                b -> {
                    String chars = "-";
                    if (Ordering.le(b, BigInteger.valueOf(36))) {
                        chars += charsToString(range('0', MathUtils.toDigit(b.intValueExact() - 1)));
                    } else {
                        chars += "()0123456789";
                    }
                    Iterable<Character> unfiltered;
                    if (P instanceof ExhaustiveProvider) {
                        unfiltered = fromString(chars);
                    } else {
                        unfiltered = ((RandomProvider) P).uniformSample(chars);
                    }
                    return filter(
                            s -> {
                                try {
                                    fromStringBase(b, s);
                                    return true;
                                } catch (IllegalArgumentException e) {
                                    return false;
                                }
                            },
                            P.strings(unfiltered)
                    );
                }
        );
        for (Pair<BigInteger, String> p : take(SMALL_LIMIT, ps)) {
            fromStringBase(p.a, p.b);
        }

        ps = filter(
                p -> !p.b.isEmpty() && head(p.b) != '0' && !p.b.startsWith("-0") && !p.b.startsWith("(0)") &&
                        !p.b.startsWith("-(0)"),
                ps
        );
        for (Pair<BigInteger, String> p : take(SMALL_LIMIT, ps)) {
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p.toString(), toStringBase(p.a, i), p.b);
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(BigInteger.valueOf(2)))) {
            assertEquals(i.toString(), fromStringBase(i, ""), BigInteger.ZERO);
        }

        for (Pair<BigInteger, String> p : take(SMALL_LIMIT, P.pairs(P.rangeDown(BigInteger.ONE), P.strings()))) {
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
            BigInteger i = logarithmicMux(p.a, p.b);
            assertNotEquals(p.toString(), i.signum(), -1);
            assertEquals(p.toString(), logarithmicDemux(i), p);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeBigIntegers()))) {
            try {
                logarithmicMux(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalBigIntegers()))) {
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

    private static void propertiesSquareRootMux() {
        initialize();
        System.out.println("\t\ttesting squareRootMux(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers()))) {
            BigInteger i = squareRootMux(p.a, p.b);
            assertNotEquals(p.toString(), i.signum(), -1);
            assertEquals(p.toString(), squareRootDemux(i), p);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeBigIntegers()))) {
            try {
                squareRootMux(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalBigIntegers()))) {
            try {
                squareRootMux(p.a, p.b);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesSquareRootDemux() {
        initialize();
        System.out.println("\t\ttesting squareRootDemux(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            Pair<BigInteger, BigInteger> p = squareRootDemux(i);
            assertNotEquals(p.toString(), p.a.signum(), -1);
            assertNotEquals(p.toString(), p.b.signum(), -1);
            assertEquals(p.toString(), squareRootMux(p.a, p.b), i);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                squareRootDemux(i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesMux() {
        initialize();
        System.out.println("\t\ttesting mux(List<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, P.lists(P.naturalBigIntegers()))) {
            BigInteger i = mux(is);
            assertNotEquals(is.toString(), i.signum(), -1);
            assertEquals(is.toString(), demux(is.size(), i), is);
        }

        Iterable<List<BigInteger>>  isFail = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigInteger>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.naturalBigIntegers()),
                        bs -> range(0, bs.size())
                )
        );
        for (List<BigInteger> is : take(LIMIT, isFail)) {
            try {
                mux(is);
                fail(is.toString());
            } catch (IllegalArgumentException ignored) {}
        }

        isFail = filter(is -> any(i -> i.signum() == -1, is), P.lists(P.bigIntegers()));
        for (List<BigInteger> is : take(LIMIT, isFail)) {
            try {
                mux(is);
                fail(is.toString());
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesDemux() {
        initialize();
        System.out.println("\t\ttesting demux(int size, BigInteger n) properties...");

        Iterable<Pair<BigInteger, Integer>> ps;
        Pair<BigInteger, Integer> zeroPair = new Pair<>(BigInteger.ZERO, 0);
        if (P instanceof ExhaustiveProvider) {
            ps = cons(
                    zeroPair,
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalBigIntegers(), P.positiveIntegers())
            );
        } else {
            ps = ((RandomProvider) P).addSpecialElement(
                    zeroPair,
                    P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).positiveIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<BigInteger> xs = demux(p.b, p.a);
            assertTrue(p.toString(), all(x -> x != null && x.signum() != -1, xs));
            assertEquals(p.toString(), mux(xs), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                demux(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.positiveIntegers()))) {
            try {
                demux(p.b, p.a);
                fail(p.toString());
            } catch (ArithmeticException ignored) {}
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            try {
                demux(0, i);
                fail(i.toString());
            } catch (ArithmeticException ignored) {}
        }
    }
}
