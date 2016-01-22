package mho.wheels.numberUtils;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.NoRemoveIterator;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.ceilingLog;
import static mho.wheels.numberUtils.IntegerUtils.*;
import static mho.wheels.numberUtils.IntegerUtils.demux;
import static mho.wheels.numberUtils.IntegerUtils.mux;
import static mho.wheels.ordering.Ordering.ge;
import static mho.wheels.ordering.Ordering.lt;
import static mho.wheels.testing.Testing.*;

public class IntegerUtilsProperties extends TestProperties {
    public IntegerUtilsProperties() {
        super("IntegerUtils");
    }

    @Override
    protected void testBothModes() {
        propertiesIsPowerOfTwo_int();
        compareImplementationsIsPowerOfTwo_int();
        propertiesIsPowerOfTwo_long();
        compareImplementationsIsPowerOfTwo_long();
        propertiesIsPowerOfTwo_BigInteger();
        compareImplementationsIsPowerOfTwo_BigInteger();
        propertiesCeilingLog2_int();
        compareImplementationsCeilingLog2_int();
        propertiesCeilingLog2_long();
        compareImplementationsCeilingLog2_long();
        propertiesCeilingLog2_BigInteger();
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
        compareImplementationsDemux();
    }

    private static boolean isPowerOfTwo_int_simplest(int n) {
        return isPowerOfTwo(BigInteger.valueOf(n));
    }

    private static boolean isPowerOfTwo_int_alt(int n) {
        if (n < 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return Integer.lowestOneBit(n) == Integer.highestOneBit(n);
    }

    private void propertiesIsPowerOfTwo_int() {
        initialize("isPowerOfTwo(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            boolean isPowerOfTwo = isPowerOfTwo(i);
            assertEquals(i, isPowerOfTwo, isPowerOfTwo_int_alt(i));
            assertEquals(i, isPowerOfTwo, 1 << ceilingLog2(i) == i);
        }

        for (int i : take(LIMIT, P.withElement(0, P.negativeIntegers()))) {
            try {
                isPowerOfTwo(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsIsPowerOfTwo_int() {
        Map<String, Function<Integer, Boolean>> functions = new LinkedHashMap<>();
        functions.put("simplest", IntegerUtilsProperties::isPowerOfTwo_int_simplest);
        functions.put("alt", IntegerUtilsProperties::isPowerOfTwo_int_alt);
        functions.put("standard", IntegerUtils::isPowerOfTwo);
        compareImplementations("isPowerOfTwo(int)", take(LIMIT, P.positiveIntegers()), functions);
    }

    private static boolean isPowerOfTwo_long_simplest(long n) {
        return isPowerOfTwo(BigInteger.valueOf(n));
    }

    private static boolean isPowerOfTwo_long_alt(long n) {
        if (n < 1L) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return Long.lowestOneBit(n) == Long.highestOneBit(n);
    }

    private void propertiesIsPowerOfTwo_long() {
        initialize("isPowerOfTwo(long)");
        for (long l : take(LIMIT, P.positiveLongs())) {
            boolean isPowerOfTwo = isPowerOfTwo(l);
            assertEquals(l, isPowerOfTwo, isPowerOfTwo_long_alt(l));
            assertEquals(l, isPowerOfTwo, 1L << ceilingLog2(l) == l);
        }

        for (long l : take(LIMIT, P.withElement(0L, P.negativeLongs()))) {
            try {
                isPowerOfTwo(l);
                fail(l);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsIsPowerOfTwo_long() {
        Map<String, Function<Long, Boolean>> functions = new LinkedHashMap<>();
        functions.put("simplest", IntegerUtilsProperties::isPowerOfTwo_long_simplest);
        functions.put("alt", IntegerUtilsProperties::isPowerOfTwo_long_alt);
        functions.put("standard", IntegerUtils::isPowerOfTwo);
        compareImplementations("isPowerOfTwo(long)", take(LIMIT, P.positiveLongs()), functions);
    }

    private static boolean isPowerOfTwo_BigInteger_alt(@NotNull BigInteger n) {
        if (n.signum() != 1) {
            throw new ArithmeticException("n must be positive. Invalid n: " + n);
        }
        return n.and(n.subtract(BigInteger.ONE)).equals(BigInteger.ZERO);
    }

    private void propertiesIsPowerOfTwo_BigInteger() {
        initialize("isPowerOfTwo(BigInteger)");
        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            boolean isPowerOfTwo = isPowerOfTwo(i);
            assertEquals(i, isPowerOfTwo, isPowerOfTwo_BigInteger_alt(i));
            assertEquals(i, isPowerOfTwo, BigInteger.ONE.shiftLeft(ceilingLog2(i)).equals(i));
        }

        for (BigInteger i : take(LIMIT, P.withElement(BigInteger.ZERO, P.negativeBigIntegers()))) {
            try {
                isPowerOfTwo(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsIsPowerOfTwo_BigInteger() {
        Map<String, Function<BigInteger, Boolean>> functions = new LinkedHashMap<>();
        functions.put("alt", IntegerUtilsProperties::isPowerOfTwo_BigInteger_alt);
        functions.put("standard", IntegerUtils::isPowerOfTwo);
        compareImplementations("isPowerOfTwo(BigInteger)", take(LIMIT, P.positiveBigIntegers()), functions);
    }

    private static int ceilingLog2_int_simplest(int n) {
        return ceilingLog2(BigInteger.valueOf(n));
    }

    private void propertiesCeilingLog2_int() {
        initialize("ceilingLog2(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            int ceilingLog2 = ceilingLog2(i);
            assertTrue(i, ceilingLog2 == 31 || 1 << ceilingLog2 >= i);
            assertTrue(i, 1 << (ceilingLog2 - 1) < i);
        }

        for (int i : take(LIMIT, P.withElement(0, P.negativeIntegers()))) {
            try {
                ceilingLog2(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsCeilingLog2_int() {
        Map<String, Function<Integer, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", IntegerUtilsProperties::ceilingLog2_int_simplest);
        functions.put("standard", IntegerUtils::ceilingLog2);
        compareImplementations("ceilingLog2(int)", take(LIMIT, P.positiveIntegers()), functions);
    }

    private static int ceilingLog2_long_simplest(long n) {
        return ceilingLog2(BigInteger.valueOf(n));
    }

    private void propertiesCeilingLog2_long() {
        initialize("ceilingLog2(long)");
        for (long l : take(LIMIT, P.positiveLongs())) {
            int ceilingLog2 = ceilingLog2(l);
            assertTrue(l, ceilingLog2 == 63 || 1L << ceilingLog2 >= l);
            assertTrue(l, 1L << (ceilingLog2 - 1) < l);
        }

        for (long l : take(LIMIT, P.withElement(0L, P.negativeLongs()))) {
            try {
                ceilingLog2(l);
                fail(l);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsCeilingLog2_long() {
        Map<String, Function<Long, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", IntegerUtilsProperties::ceilingLog2_long_simplest);
        functions.put("standard", IntegerUtils::ceilingLog2);
        compareImplementations("ceilingLog2(long)", take(LIMIT, P.positiveLongs()), functions);
    }

    private void propertiesCeilingLog2_BigInteger() {
        initialize("ceilingLog2(BigInteger)");
        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            int ceilingLog2 = ceilingLog2(i);
            assertTrue(i, ge(BigInteger.ONE.shiftLeft(ceilingLog2), i));
            assertTrue(i, lt(BigInteger.ONE.shiftLeft(ceilingLog2 - 1), i));
        }

        for (BigInteger i : take(LIMIT, P.withElement(BigInteger.ZERO, P.negativeBigIntegers()))) {
            try {
                ceilingLog2(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Boolean> bits_int_simplest(int n) {
        return bits(BigInteger.valueOf(n));
    }

    private void propertiesBits_int() {
        initialize("bits(int)");
        for (int i : take(LIMIT, P.naturalIntegers())) {
            Iterable<Boolean> bitsIterable = bits(i);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(i, bits, bits_int_simplest(i));
            aeqit(i, bits, reverse(bigEndianBits(i)));
            assertTrue(i, all(b -> b != null, bits));
            assertEquals(i, fromBits(bits).intValueExact(), i);
            assertEquals(i, bits.size(), BigInteger.valueOf(i).bitLength());
            testNoRemove(bitsIterable);
            testHasNext(bitsIterable);
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(i, bits.isEmpty());
            assertEquals(i, last(bits), true);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                bits(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBits_int() {
        Map<String, Function<Integer, List<Boolean>>> functions = new LinkedHashMap<>();
        functions.put("simplest", i -> toList(bits_int_simplest(i)));
        functions.put("standard", i -> toList(bits(i)));
        compareImplementations("bits(int)", take(LIMIT, P.naturalIntegers()), functions);
    }

    private static @NotNull Iterable<Boolean> bits_BigInteger_alt(@NotNull BigInteger n) {
        return () -> new NoRemoveIterator<Boolean>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public Boolean next() {
                if (remaining.equals(BigInteger.ZERO)) {
                    throw new NoSuchElementException();
                }
                boolean bit = remaining.testBit(0);
                remaining = remaining.shiftRight(1);
                return bit;
            }
        };
    }

    private void propertiesBits_BigInteger() {
        initialize("bits(BigInteger)");
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            Iterable<Boolean> bitsIterable = bits(i);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(i, bits, bits_BigInteger_alt(i));
            aeqit(i, bits, reverse(bigEndianBits(i)));
            assertTrue(i, all(b -> b != null, bits));
            assertEquals(i, fromBits(bits), i);
            assertEquals(i, bits.size(), i.bitLength());
            testNoRemove(bitsIterable);
            testHasNext(bitsIterable);
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = toList(bits(i));
            assertFalse(i, bits.isEmpty());
            assertEquals(i, last(bits), true);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                bits(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBits_BigInteger() {
        Map<String, Function<BigInteger, List<Boolean>>> functions = new LinkedHashMap<>();
        functions.put("alt", i -> toList(bits_BigInteger_alt(i)));
        functions.put("standard", i -> toList(bits(i)));
        compareImplementations("bits(BigInteger)", take(LIMIT, P.naturalBigIntegers()), functions);
    }

    private static @NotNull Iterable<Boolean> bitsPadded_int_int_simplest(int length, int n) {
        return bitsPadded(length, BigInteger.valueOf(n));
    }

    private void propertiesBitsPadded_int_int() {
        initialize("");
        System.out.println("\t\ttesting bitsPadded(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalIntegers(),
                P.withScale(20).naturalIntegersGeometric()
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            Iterable<Boolean> bitsIterable = bitsPadded(p.b, p.a);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(p, bits, bitsPadded_int_int_simplest(p.b, p.a));
            aeqit(p, bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p, all(b -> b != null, bits));
            assertEquals(p, bits.size(), p.b.intValue());
            try {
                bitsIterable.iterator().remove();
                fail(p);
            } catch (UnsupportedOperationException ignored) {}
        }

        ps = map(
                p -> new Pair<>(p.a, BigInteger.valueOf(p.a).bitLength() + p.b),
                P.pairs(P.naturalIntegers(), P.naturalIntegersGeometric())
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p, fromBits(bits).intValueExact(), p.a.intValue());
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {
            }
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBitsPadded_int_int() {
        initialize("");
        System.out.println("\t\tcomparing bitsPadded(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalIntegers(),
                P.withScale(20).naturalIntegersGeometric()
        );
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

    private void propertiesBitsPadded_int_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting bitsPadded(int, BigInteger) properties...");

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            Iterable<Boolean> bitsIterable = bitsPadded(p.b, p.a);
            List<Boolean> bits = toList(bitsIterable);
            aeqit(p, bits, reverse(bigEndianBitsPadded(p.b, p.a)));
            assertTrue(p, all(b -> b != null, bits));
            assertEquals(p, bits.size(), p.b.intValue());
            try {
                bitsIterable.iterator().remove();
                fail(p);
            } catch (UnsupportedOperationException ignored) {}
        }

        ps = map(
                p -> new Pair<>(p.a, p.a.bitLength() + p.b),
                P.pairs(P.naturalBigIntegers(), P.naturalIntegersGeometric())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = toList(bitsPadded(p.b, p.a));
            assertEquals(p.toString(), fromBits(bits), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                bitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull List<Boolean> bigEndianBits_int_simplest(int n) {
        return bigEndianBits(BigInteger.valueOf(n));
    }

    private void propertiesBigEndianBits_int() {
        initialize("");
        System.out.println("\t\ttesting bigEndianBits(int) properties...");

        for (int i : take(LIMIT, P.naturalIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            aeqit(i, bits, bigEndianBits_int_simplest(i));
            aeqit(i, bits, reverse(bits(i)));
            assertTrue(i, all(b -> b != null, bits));
            assertEquals(i, fromBigEndianBits(bits).intValueExact(), i);
            assertEquals(i, bits.size(), BigInteger.valueOf(i).bitLength());
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            assertFalse(i, bits.isEmpty());
            assertEquals(i, head(bits), true);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                bigEndianBits(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBigEndianBits_int() {
        initialize("");
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

    private void propertiesBigEndianBits_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting bigEndianBits(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            aeqit(i, bits, reverse(bits(i)));
            assertTrue(i, all(b -> b != null, bits));
            assertEquals(i, fromBigEndianBits(bits), i);
            assertEquals(i, bits.size(), i.bitLength());
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            List<Boolean> bits = bigEndianBits(i);
            assertFalse(i, bits.isEmpty());
            assertEquals(i, head(bits), true);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                bigEndianBits(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Boolean> bigEndianBitsPadded_int_int_simplest(int length, int n) {
        return bigEndianBitsPadded(length, BigInteger.valueOf(n));
    }

    private void propertiesBigEndianBitsPadded_int_int() {
        initialize("");
        System.out.println("\t\ttesting bigEndianBitsPadded(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            aeqit(p, bits, bigEndianBitsPadded_int_int_simplest(p.b, p.a));
            aeqit(p, bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p, all(b -> b != null, bits));
            assertEquals(p, bits.size(), p.b.intValue());
        }

        ps = map(
                p -> new Pair<>(p.a, BigInteger.valueOf(p.a).bitLength() + p.b),
                P.pairs(P.naturalIntegers(), P.naturalIntegersGeometric())
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            assertEquals(p, fromBigEndianBits(bits).intValueExact(), p.a.intValue());
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBigEndianBitsPadded_int_int() {
        initialize("");
        System.out.println("\t\tcomparing bigEndianBitsPadded(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), P.withScale(20).naturalIntegersGeometric());
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

    private void propertiesBigEndianBitsPadded_int_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting bigEndianBitsPadded(int, BigInteger) properties...");

        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            aeqit(p, bits, reverse(bitsPadded(p.b, p.a)));
            assertTrue(p, all(b -> b != null, bits));
            assertEquals(p, bits.size(), p.b.intValue());
        }

        ps = map(
                p -> new Pair<>(p.a, p.a.bitLength() + p.b),
                P.pairs(P.naturalBigIntegers(), P.naturalIntegersGeometric())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<Boolean> bits = bigEndianBitsPadded(p.b, p.a);
            assertEquals(p, fromBigEndianBits(bits), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                bigEndianBitsPadded(p.b, p.a);
                fail(p);
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

    private void propertiesFromBits() {
        initialize("");
        System.out.println("\t\ttesting fromBits(Iterable<Boolean>) properties...");

        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            BigInteger i = fromBits(bs);
            assertEquals(bs, fromBits_alt(bs), i);
            assertTrue(bs, i.signum() != -1);
            assertEquals(bs, i, fromBigEndianBits(reverse(bs)));
        }

        Iterable<List<Boolean>> bss = map(
                xs -> toList(concat(xs, Collections.singletonList(true))),
                P.lists(P.booleans())
        );
        for (List<Boolean> bs : take(LIMIT, bss)) {
            BigInteger i = fromBits(bs);
            aeqit(bs, bs, bits(i));
        }

        for (List<Boolean> bs : take(LIMIT, P.listsWithElement(null, P.booleans()))) {
            try {
                fromBits(bs);
                fail(bs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void compareImplementationsFromBits() {
        initialize("");
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

    private void propertiesFromBigEndianBits() {
        initialize("");
        System.out.println("\t\ttesting fromBigEndianBits(Iterable<Boolean>) properties...");

        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            BigInteger i = fromBigEndianBits(bs);
            assertTrue(bs, i.signum() != -1);
            assertEquals(bs, i, fromBits(reverse(bs)));
        }

        for (List<Boolean> bs : take(LIMIT, map(xs -> toList(cons(true, xs)), P.lists(P.booleans())))) {
            BigInteger i = fromBigEndianBits(bs);
            aeqit(bs, bs, bigEndianBits(i));
        }

        for (List<Boolean> bs : take(LIMIT, P.listsWithElement(null, P.booleans()))) {
            try {
                fromBigEndianBits(bs);
                fail(bs);
            } catch (NullPointerException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> digits_int_int_simplest(int base, int n) {
        return map(BigInteger::intValue, digits(BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private void propertiesDigits_int_int() {
        initialize("");
        System.out.println("\t\ttesting digits(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> digitsIterable = digits(p.b, p.a);
            List<Integer> digits = toList(digitsIterable);
            aeqit(p, digits, digits_int_int_simplest(p.b, p.a));
            aeqit(p, digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p, all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p, fromDigits(p.b, digits).intValueExact(), p.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(p);
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = toList(digits(p.b, p.a));
            assertFalse(p, digits.isEmpty());
            assertNotEquals(p, last(digits).intValue(), 0);
            int targetDigitCount = ceilingLog(BigInteger.valueOf(p.b), BigInteger.valueOf(p.a)).intValueExact();
            if (BigInteger.valueOf(p.b).pow(targetDigitCount).equals(BigInteger.valueOf(p.a))) {
                targetDigitCount++;
            }
            assertEquals(p, digits.size(), targetDigitCount);
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
            aeqit(i, map(digitsToBits, digits), bits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(i, isEmpty(digits(i, 0)));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.rangeDown(1)))) {
            try {
                digits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.rangeUp(2)))) {
            try {
                digits(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsDigits_int_int() {
        initialize("");
        System.out.println("\t\tcomparing digits(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
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

    private void propertiesDigits_BigInteger_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting digits(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            Iterable<BigInteger> digitsIterable = digits(p.b, p.a);
            List<BigInteger> digits = toList(digitsIterable);
            aeqit(p, digits, reverse(bigEndianDigits(p.b, p.a)));
            assertTrue(p, all(i -> i != null && i.signum() != -1 && lt(i, p.b), digits));
            assertEquals(p, fromDigits(p.b, digits), p.a);
            try {
                digitsIterable.iterator().remove();
                fail(p);
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveBigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.positiveBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            List<BigInteger> digits = toList(digits(p.b, p.a));
            assertFalse(p, digits.isEmpty());
            assertNotEquals(p, last(digits), BigInteger.ZERO);
            int targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
            if (p.b.pow(targetDigitCount).equals(p.a)) {
                targetDigitCount++;
            }
            assertEquals(p, digits.size(), targetDigitCount);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<BigInteger> digits = toList(digits(TWO, i));
            aeqit(i, map(digitsToBits, digits), bits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(TWO))) {
            assertTrue(i, isEmpty(digits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(
                P.naturalBigIntegers(),
                P.rangeDown(NEGATIVE_ONE)
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                digits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairs(P.negativeBigIntegers(), P.rangeUp(TWO));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                digits(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> digitsPadded_int_int_int_simplest(int length, int base, int n) {
        return map(BigInteger::intValue, digitsPadded(length, BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private void propertiesDigitsPadded_int_int_int() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            Iterable<Integer> digitsIterable = digitsPadded(t.a, t.b, t.c);
            List<Integer> digits = toList(digitsIterable);
            aeqit(t, digits, digitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeqit(t, digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t, all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t, digits.size(), t.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(t);
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairs(
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
                    P.dependentPairs(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
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
                                        P.withScale(20).naturalIntegersGeometric()
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            List<Integer> digits = toList(digitsPadded(t.a, t.b, t.c));
            assertEquals(t, fromDigits(t.b, digits).intValueExact(), t.c.intValue());
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
            ps = P.pairs(P.naturalIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = toList(digitsPadded(p.b, 2, p.a));
            aeqit(p, map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(2),
                P.negativeIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(2), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(-1), P.negativeIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsDigitsPadded_int_int_int() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
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

    private void propertiesDigitsPadded_int_BigInteger_BigInteger() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
            ts = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.naturalBigIntegers());
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            Iterable<BigInteger> digitsIterable = digitsPadded(t.a, t.b, t.c);
            List<BigInteger> digits = toList(digitsIterable);
            aeqit(t, digits, reverse(bigEndianDigitsPadded(t.a, t.b, t.c)));
            assertTrue(t, all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t, digits.size(), t.a.intValue());
            try {
                digitsIterable.iterator().remove();
                fail(t);
            } catch (UnsupportedOperationException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairs(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalBigIntegers(), P.rangeUp(TWO)),
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
                    P.dependentPairs(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            P.withScale(20).naturalIntegersGeometric()
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
                                        P.withScale(20).naturalIntegersGeometric()
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            List<BigInteger> digits = toList(digitsPadded(t.a, t.b, t.c));
            assertEquals(t, fromDigits(t.b, digits), t.c);
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
            ps = P.pairs(P.naturalBigIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<BigInteger> digits = toList(digitsPadded(p.b, TWO, p.a));
            aeqit(p, map(digitsToBits, digits), bitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(TWO),
                P.negativeBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(TWO), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(BigInteger.ONE), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                digitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> bigEndianDigits_int_int_simplest(int base, int n) {
        return map(BigInteger::intValue, bigEndianDigits(BigInteger.valueOf(base), BigInteger.valueOf(n)));
    }

    private void propertiesBigEndianDigits_int_int() {
        initialize("");
        System.out.println("\t\ttesting bigEndianDigits(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = bigEndianDigits(p.b, p.a);
            aeqit(p, digits, bigEndianDigits_int_int_simplest(p.b, p.a));
            aeqit(p, digits, reverse(digits(p.b, p.a)));
            assertTrue(p, all(i -> i != null && i >= 0 && i < p.b, digits));
            assertEquals(p, fromBigEndianDigits(p.b, digits).intValueExact(), p.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.positiveIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = bigEndianDigits(p.b, p.a);
            assertFalse(p, digits.isEmpty());
            assertNotEquals(p, head(digits).intValue(), 0);
            int targetDigitCount = ceilingLog(BigInteger.valueOf(p.b), BigInteger.valueOf(p.a)).intValueExact();
            if (BigInteger.valueOf(p.b).pow(targetDigitCount).equals(BigInteger.valueOf(p.a))) {
                targetDigitCount++;
            }
            assertEquals(p, digits.size(), targetDigitCount);
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
            aeqit(i, map(digitsToBits, digits), bigEndianBits(i));
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertTrue(i, isEmpty(bigEndianDigits(i, 0)));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.rangeDown(1)))) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.rangeUp(2)))) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsBigEndianDigits_int_int() {
        initialize("");
        System.out.println("\t\tcomparing bigEndianDigits(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
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

    private void propertiesBigEndianDigits_BigInteger_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting bigEndianDigits(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            List<BigInteger> digits = bigEndianDigits(p.b, p.a);
            aeqit(p, digits, reverse(digits(p.b, p.a)));
            assertTrue(p, all(i -> i != null && i.signum() != -1 && lt(i, p.b), digits));
            assertEquals(p, fromBigEndianDigits(p.b, digits), p.a);
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.positiveBigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.positiveBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            List<BigInteger> digits = bigEndianDigits(p.b, p.a);
            assertFalse(p, digits.isEmpty());
            assertNotEquals(p, head(digits), BigInteger.ZERO);
            int targetDigitCount = ceilingLog(p.b, p.a).intValueExact();
            if (p.b.pow(targetDigitCount).equals(p.a)) {
                targetDigitCount++;
            }
            assertEquals(p, digits.size(), targetDigitCount);
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            List<BigInteger> digits = bigEndianDigits(TWO, i);
            aeqit(i, map(digitsToBits, digits), bigEndianBits(i));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(TWO))) {
            assertTrue(i, isEmpty(bigEndianDigits(i, BigInteger.ZERO)));
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.naturalBigIntegers(), P.rangeDown(BigInteger.ONE));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairs(P.negativeBigIntegers(), P.rangeUp(TWO));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                bigEndianDigits(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull Iterable<Integer> bigEndianDigitsPadded_int_int_int_simplest(int length, int base, int n) {
        return map(
                BigInteger::intValue,
                bigEndianDigitsPadded(length, BigInteger.valueOf(base), BigInteger.valueOf(n))
        );
    }

    private void propertiesBigEndianDigitsPadded_int_int_int() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            List<Integer> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            aeqit(t, digits, bigEndianDigitsPadded_int_int_int_simplest(t.a, t.b, t.c));
            aeqit(t, digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t, all(i -> i != null && i >= 0 && i < t.b, digits));
            assertEquals(t, digits.size(), t.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairs(
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
                    P.dependentPairs(
                            P.pairs(
                                    P.naturalIntegers(),
                                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
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
                                        P.withScale(20).naturalIntegersGeometric()
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            List<Integer> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            assertEquals(t, fromBigEndianDigits(t.b, digits).intValueExact(), t.c.intValue());
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
            ps = P.pairs(P.naturalIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            List<Integer> digits = bigEndianDigitsPadded(p.b, 2, p.a);
            aeqit(p, map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, Integer, Integer>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(2),
                P.negativeIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(2), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(1), P.naturalIntegers());
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsBigEndianDigitsPadded_int_int_int() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
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

    private void propertiesBigEndianDigitsPadded_int_BigInteger_BigInteger() {
        initialize("");
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
            Iterable<Integer> is = P.withScale(20).naturalIntegersGeometric();
            ts = P.triples(is, map(i -> BigInteger.valueOf(i + 2), is), P.naturalBigIntegers());
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            List<BigInteger> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            aeqit(t, digits, reverse(digitsPadded(t.a, t.b, t.c)));
            assertTrue(t, all(i -> i != null && i.signum() != -1 && lt(i, t.b), digits));
            assertEquals(t, digits.size(), t.a.intValue());
        }

        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    q -> new Triple<>(q.b, q.a.b, q.a.a),
                    P.dependentPairs(
                            ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalBigIntegers(), P.rangeUp(TWO)),
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
                    P.dependentPairs(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(
                                            i -> BigInteger.valueOf(i + 2),
                                            P.withScale(20).naturalIntegersGeometric()
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
                                        P.withScale(20).naturalIntegersGeometric()
                                );
                            }
                    )
            );
        }
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            List<BigInteger> digits = bigEndianDigitsPadded(t.a, t.b, t.c);
            assertEquals(t, fromBigEndianDigits(t.b, digits), t.c);
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
            ps = P.pairs(P.naturalBigIntegers(), P.withScale(20).naturalIntegersGeometric());
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<BigInteger> digits = bigEndianDigitsPadded(p.b, TWO, p.a);
            aeqit(p, map(digitsToBits, digits), bigEndianBitsPadded(p.b, p.a));
        }

        Iterable<Triple<Integer, BigInteger, BigInteger>> tsFail = P.triples(
                P.naturalIntegers(),
                P.rangeUp(TWO),
                P.negativeBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (ArithmeticException ignored) {}
        }

        tsFail = P.triples(P.negativeIntegers(), P.rangeUp(TWO), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = P.triples(P.naturalIntegers(), P.rangeDown(BigInteger.ONE), P.naturalBigIntegers());
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, tsFail)) {
            try {
                bigEndianDigitsPadded(t.a, t.b, t.c);
                fail(t);
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

    private void propertiesFromDigits_int_Iterable_Integer() {
        initialize("");
        System.out.println("\t\ttesting fromDigits(int, Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(P.withScale(10).naturalIntegersGeometric()),
                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p, n, fromDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p, n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p, n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || last(p.a) != 0, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            aeqit(p, p.a, map(BigInteger::intValueExact, digits(BigInteger.valueOf(p.b), n)));
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        for (List<Integer> is : take(LIMIT, P.lists(P.range(0, 1)))) {
            assertEquals(is, fromDigits(2, is), fromBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<Integer>, Integer>> unfilteredPsFail = P.pairs(P.lists(P.integers()), P.rangeDown(1));
        Iterable<Pair<List<Integer>, Integer>> psFail = filter(p -> all(i -> i < p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.integers()), P.rangeUp(2));
        psFail = filter(p -> any(i -> i < 0, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), unfilteredPsFail);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsFromDigits_int_Iterable_Integer() {
        initialize("");
        System.out.println("\t\tcomparing fromDigits(int, Iterable<Integer>) implementations...");

        long totalTime = 0;
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(P.withScale(10).naturalIntegersGeometric()),
                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
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

    private void propertiesFromDigits_int_Iterable_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting fromDigits(int, Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    P.rangeUp(TWO)
            );
        } else {
            //noinspection Convert2MethodRef
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), P.withScale(10).naturalIntegersGeometric())),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filterInfinite(p -> all(i -> lt(i, p.b), p.a), unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            assertEquals(p, n, fromBigEndianDigits(p.b, reverse(p.a)));
            assertNotEquals(p, n.signum(), -1);
        }

        ps = filterInfinite(p -> p.a.isEmpty() || !last(p.a).equals(BigInteger.ZERO), ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromDigits(p.b, p.a);
            aeqit(p, p.a, digits(p.b, n));
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        for (List<BigInteger> is : take(LIMIT, P.lists(P.range(BigInteger.ZERO, BigInteger.ONE)))) {
            assertEquals(is, fromDigits(TWO, is), fromBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail = P.pairs(
                P.lists(P.bigIntegers()),
                P.rangeDown(BigInteger.ONE)
        );
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filterInfinite(
                p -> all(i -> lt(i, p.b), p.a),
                unfilteredPsFail
        );
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.bigIntegers()), P.rangeUp(TWO));
        psFail = filterInfinite(p -> any(i -> i.signum() == -1, p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filterInfinite(p -> any(i -> ge(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromDigits(p.b, p.a);
                fail(p);
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

    private void propertiesFromBigEndianDigits_int_Iterable_Integer() {
        initialize("");
        System.out.println("\t\ttesting fromBigEndianDigits(int, Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(P.withScale(10).naturalIntegersGeometric()),
                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p, n, fromBigEndianDigits_int_Iterable_Integer_simplest(p.b, p.a));
            assertEquals(p, n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p, n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || head(p.a) != 0, ps);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeqit(p, p.a, map(BigInteger::intValueExact, bigEndianDigits(BigInteger.valueOf(p.b), n)));
        }

        Function<Integer, Boolean> digitsToBits = i -> {
            switch (i) {
                case 0: return false;
                case 1: return true;
                default: throw new IllegalArgumentException();
            }
        };

        for (List<Integer> is : take(LIMIT, P.lists(P.range(0, 1)))) {
            assertEquals(is, fromBigEndianDigits(2, is), fromBigEndianBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = filter(
                p -> all(i -> i < p.b, p.a),
                P.pairs(P.lists(P.integers()), P.rangeDown(1))
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i < 0, p.a), P.pairs(P.lists(P.integers()), P.rangeUp(2)));
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> i >= p.b, p.a), P.pairs(P.lists(P.integers()), P.rangeUp(2)));
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsFromBigEndianDigits_int_Iterable_Integer() {
        initialize("");
        System.out.println("\t\tcomparing fromBigEndianDigits(int, Iterable<Integer>) implementations...");

        long totalTime = 0;
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(P.withScale(10).naturalIntegersGeometric()),
                    map(i -> i + 2, P.withScale(20).naturalIntegersGeometric())
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

    private void propertiesFromBigEndianDigits_int_Iterable_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting fromBigEndianDigits(int, Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    P.rangeUp(TWO)
            );
        } else {
            //noinspection Convert2MethodRef
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), P.withScale(10).naturalIntegersGeometric())),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            assertEquals(p, n, fromDigits(p.b, reverse(p.a)));
            assertNotEquals(p, n.signum(), -1);
        }

        ps = filter(p -> p.a.isEmpty() || !head(p.a).equals(BigInteger.ZERO), ps);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            BigInteger n = fromBigEndianDigits(p.b, p.a);
            aeqit(p, p.a, bigEndianDigits(p.b, n));
        }

        Function<BigInteger, Boolean> digitsToBits = i -> {
            if (i.equals(BigInteger.ZERO)) return false;
            if (i.equals(BigInteger.ONE)) return true;
            throw new IllegalArgumentException();
        };

        for (List<BigInteger> is : take(LIMIT, P.lists(P.range(BigInteger.ZERO, BigInteger.ONE)))) {
            assertEquals(is, fromBigEndianDigits(TWO, is), fromBigEndianBits(map(digitsToBits, is)));
        }

        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPsFail = P.pairs(
                P.lists(P.bigIntegers()),
                P.rangeDown(BigInteger.ONE)
        );
        Iterable<Pair<List<BigInteger>, BigInteger>> psFail = filter(p -> all(i -> lt(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        unfilteredPsFail = P.pairs(P.lists(P.bigIntegers()), P.rangeUp(TWO));
        psFail = filter(p -> any(i -> i.signum() == -1, p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filter(p -> any(i -> ge(i, p.b), p.a), unfilteredPsFail);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, psFail)) {
            try {
                fromBigEndianDigits(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesToDigit() {
        initialize("");
        System.out.println("\t\ttesting toDigit(int) properties...");

        for (int i : take(LIMIT, P.range(0, 35))) {
            char digit = toDigit(i);
            assertTrue(i, elem(digit, range('0', '9')) || elem(digit, range('A', 'Z')));
            assertEquals(i, i, fromDigit(digit));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                toDigit(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }

        for (int i : take(LIMIT, P.rangeUp(36))) {
            try {
                toDigit(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesFromDigit() {
        initialize("");
        System.out.println("\t\ttesting fromDigit(char) properties...");

        for (char c : take(LIMIT, EP.choose(P.range('0', '9'), P.range('A', 'Z')))) {
            int i = fromDigit(c);
            assertTrue(c, i >= 0 && i < 36);
            assertEquals(c, c, toDigit(i));
        }

        Iterable<Character> csFail = filter(
                d -> !elem(d, range('0', '9')) && !elem(d, range('A', 'Z')),
                P.characters()
        );
        for (char c : take(LIMIT, csFail)) {
            try {
                fromDigit(c);
                fail(c);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull String toStringBase_int_int_simplest(int base, int n) {
        return toStringBase(BigInteger.valueOf(base), BigInteger.valueOf(n));
    }

    private void propertiesToStringBase_int_int() {
        initialize("");
        System.out.println("\t\ttesting toStringBase(int, int) properties...");

        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertEquals(p, toStringBase_int_int_simplest(p.b, p.a), s);
            assertEquals(p, fromStringBase(p.b, s), BigInteger.valueOf(p.a));
        }

        String chars = charsToString(cons('-', concat(range('0', '9'), range('A', 'Z'))));
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.range(2, 36));
        } else {
            ps = P.pairs(P.integers(), P.range(2, 36));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertTrue(p, all(c -> elem(c, chars), s));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(37));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 37, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertEquals(p, head(s), '(');
            assertEquals(p, last(s), ')');
            s = tail(init(s));
            Iterable<Integer> digits = map(Integer::parseInt, Arrays.asList(s.split("\\)\\(")));
            assertFalse(p, isEmpty(digits));
            assertTrue(p, p.a == 0 || head(digits) != 0);
            assertTrue(p, all(d -> d >= 0 && d < p.b, digits));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers(), P.rangeDown(1)))) {
            try {
                toStringBase(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsToStringBase_int_int() {
        initialize("");
        System.out.println("\t\tcomparing toStringBase(int, int) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
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

    private void propertiesToStringBase_BigInteger_BigInteger() {
        initialize("");
        System.out.println("\t\ttesting toStringBase(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertEquals(p, fromStringBase(p.b, s), p.a);
        }

        String chars = charsToString(cons('-', concat(range('0', '9'), range('A', 'Z'))));
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.range(TWO, BigInteger.valueOf(36)));
        } else {
            ps = P.pairs(P.bigIntegers(), P.range(TWO, BigInteger.valueOf(36)));
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            assertTrue(p, all(c -> elem(c, chars), s));
        }

        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(BigInteger.valueOf(37)));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 37), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            String s = toStringBase(p.b, p.a);
            if (head(s) == '-') s = tail(s);
            assertEquals(p, head(s), '(');
            assertEquals(p, last(s), ')');
            s = tail(init(s));
            Iterable<BigInteger> digits = map(BigInteger::new, Arrays.asList(s.split("\\)\\(")));
            assertFalse(p, isEmpty(digits));
            assertTrue(p, p.a.equals(BigInteger.ZERO) || !head(digits).equals(BigInteger.ZERO));
            assertTrue(p, all(d -> d.signum() != -1 && lt(d, p.b), digits));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers(), P.rangeDown(BigInteger.ONE)))) {
            try {
                toStringBase(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull BigInteger fromStringBase_int_String_simplest(int base, @NotNull String s) {
        return fromStringBase(BigInteger.valueOf(base), s);
    }

    private void propertiesFromStringBase_int_String() {
        initialize("");
        System.out.println("\t\ttesting fromString(int, String) properties...");

        Iterable<Pair<Integer, String>> ps = map(
                p -> new Pair<>(p.a, toStringBase(BigInteger.valueOf(p.a), p.b)),
                P.pairs(P.rangeUpGeometric(2), P.bigIntegers())
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p, fromStringBase_int_String_simplest(p.a, p.b), i);
            assertEquals(p, toStringBase(BigInteger.valueOf(p.a), i), p.b);
        }

        for (int i : take(LIMIT, P.rangeUp(2))) {
            assertEquals(i, fromStringBase(i, ""), BigInteger.ZERO);
        }

        for (Pair<Integer, String> p : take(LIMIT, P.pairs(P.rangeDown(1), P.strings()))) {
            try {
                fromStringBase(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        //improper String left untested
    }

    private void compareImplementationsFromStringBase_int_String() {
        initialize("");
        System.out.println("\t\tcomparing fromStringBase(int, String) implementations...");

        long totalTime = 0;
        Iterable<Pair<Integer, String>> ps = map(
                p -> new Pair<>(p.a, toStringBase(BigInteger.valueOf(p.a), p.b)),
                P.pairs(P.rangeUpGeometric(2), P.bigIntegers())
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            fromStringBase_int_String_simplest(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tsimplest: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            fromStringBase(p.a, p.b);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private void propertiesFromStringBase_BigInteger_String() {
        initialize("");
        System.out.println("\t\ttesting fromStringBase(BigInteger, String) properties...");

        Iterable<Pair<BigInteger, String>> ps = map(
                p -> new Pair<>(p.a, toStringBase(p.a, p.b)),
                P.pairs(P.rangeUp(TWO), P.bigIntegers())
        );
        for (Pair<BigInteger, String> p : take(LIMIT, ps)) {
            BigInteger i = fromStringBase(p.a, p.b);
            assertEquals(p, toStringBase(p.a, i), p.b);
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(TWO))) {
            assertEquals(i, fromStringBase(i, ""), BigInteger.ZERO);
        }

        for (Pair<BigInteger, String> p : take(LIMIT, P.pairs(P.rangeDown(BigInteger.ONE), P.strings()))) {
            try {
                fromStringBase(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        //improper String left untested
    }

    private void propertiesLogarithmicMux() {
        initialize("");
        System.out.println("\t\ttesting logarithmicMux(BigInteger, BigInteger) properties...");

        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = P.pairs(P.naturalBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            BigInteger i = logarithmicMux(p.a, p.b);
            assertNotEquals(p, i.signum(), -1);
            assertEquals(p, logarithmicDemux(i), p);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeBigIntegers()))) {
            try {
                logarithmicMux(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalBigIntegers()))) {
            try {
                logarithmicMux(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesLogarithmicDemux() {
        initialize("");
        System.out.println("\t\ttesting logarithmicDemux(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            Pair<BigInteger, BigInteger> p = logarithmicDemux(i);
            assertNotEquals(p, p.a.signum(), -1);
            assertNotEquals(p, p.b.signum(), -1);
            assertEquals(p, logarithmicMux(p.a, p.b), i);
            assertTrue(p, lt(i, logarithmicMux(p.a.add(BigInteger.ONE), p.b)));
            assertTrue(p, lt(i, logarithmicMux(p.a, p.b.add(BigInteger.ONE))));
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                logarithmicDemux(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSquareRootMux() {
        initialize("");
        System.out.println("\t\ttesting squareRootMux(BigInteger, BigInteger) properties...");

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers()))) {
            BigInteger i = squareRootMux(p.a, p.b);
            assertNotEquals(p, i.signum(), -1);
            assertEquals(p, squareRootDemux(i), p);
            assertTrue(p, lt(i, squareRootMux(p.a.add(BigInteger.ONE), p.b)));
            assertTrue(p, lt(i, squareRootMux(p.a, p.b.add(BigInteger.ONE))));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeBigIntegers()))) {
            try {
                squareRootMux(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalBigIntegers()))) {
            try {
                squareRootMux(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSquareRootDemux() {
        initialize("");
        System.out.println("\t\ttesting squareRootDemux(BigInteger) properties...");

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            Pair<BigInteger, BigInteger> p = squareRootDemux(i);
            assertNotEquals(p, p.a.signum(), -1);
            assertNotEquals(p, p.b.signum(), -1);
            assertEquals(p, squareRootMux(p.a, p.b), i);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                squareRootDemux(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesMux() {
        initialize("");
        System.out.println("\t\ttesting mux(List<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, P.lists(P.naturalBigIntegers()))) {
            BigInteger i = IntegerUtils.mux(is);
            assertNotEquals(is, i.signum(), -1);
            assertEquals(is, demux(is.size(), i), is);
            for (int j = 0; j < is.size(); j++) {
                assertTrue(is, lt(i, mux(toList(set(is, j, is.get(j).add(BigInteger.ONE))))));
            }
        }

        for (List<BigInteger> is : take(LIMIT, P.listsWithElement(null, P.naturalBigIntegers()))) {
            try {
                IntegerUtils.mux(is);
                fail(is);
            } catch (NullPointerException | IllegalArgumentException ignored) {}
        }

        Iterable<List<BigInteger>> isFail = filterInfinite(
                is -> any(i -> i.signum() == -1, is),
                P.lists(P.bigIntegers())
        );
        for (List<BigInteger> is : take(LIMIT, isFail)) {
            try {
                IntegerUtils.mux(is);
                fail(is);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull List<BigInteger> demux_alt(int size, @NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(size, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(IntegerUtils::fromBits, IterableUtils.demux(size, bits(n))));
    }

    private static @NotNull List<BigInteger> demux_alt2(int size, @NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(size, BigInteger.ZERO));
        }
        int length = n.bitLength();
        int resultLength = (length / 8 + 1) / size + 2;
        byte[][] demuxedBytes = new byte[size][resultLength];
        int ri = resultLength - 1;
        int rj = 1;
        int rk = size - 1;
        for (int i = 0; i < length; i++) {
            if (n.testBit(i)) {
                demuxedBytes[rk][ri] |= rj;
            }
            if (rk == 0) {
                rk = size - 1;
                rj <<= 1;
                if (rj == 256) {
                    rj = 1;
                    ri--;
                }
            } else {
                rk--;
            }
        }
        List<BigInteger> demuxed = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            demuxed.add(new BigInteger(demuxedBytes[i]));
        }
        return demuxed;
    }

    private void propertiesDemux() {
        initialize("");
        System.out.println("\t\ttesting demux(int size, BigInteger n) properties...");

        Iterable<Pair<BigInteger, Integer>> ps = P.withElement(
                new Pair<>(BigInteger.ZERO, 0),
                P.pairs(P.naturalBigIntegers(), P.withScale(20).positiveIntegersGeometric())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            List<BigInteger> xs = demux(p.b, p.a);
            assertEquals(p, xs, demux_alt(p.b, p.a));
            assertEquals(p, xs, demux_alt2(p.b, p.a));
            assertTrue(p, all(x -> x != null && x.signum() != -1, xs));
            assertEquals(p, IntegerUtils.mux(xs), p.a);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                demux(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.positiveIntegers()))) {
            try {
                demux(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            try {
                demux(0, i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsDemux() {
        initialize("");
        System.out.println("\t\tcomparing demux(int size, BigInteger n) implementations...");

        Iterable<Pair<BigInteger, Integer>> ps = P.withElement(
                new Pair<>(BigInteger.ZERO, 0),
                P.pairs(P.naturalBigIntegers(), P.withScale(20).positiveIntegersGeometric())
        );
        long totalTime = 0;
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            demux_alt(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\talternative: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            demux_alt2(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\talternative 2: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            long time = System.nanoTime();
            demux(p.b, p.a);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }
}
