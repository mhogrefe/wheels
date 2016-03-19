package mho.wheels.math;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.*;

public class MathUtilsProperties extends TestProperties {
    public MathUtilsProperties() {
        super("MathUtils");
    }

    @Override
    protected void testBothModes() {
        propertiesGcd_int_int();
        compareImplementationsGcd_int_int();
        propertiesGcd_long_long();
        compareImplementationsGcd_long_long1();
        compareImplementationsGcd_long_long2();
        propertiesLcm_BigInteger_BigInteger();
        compareImplementationsLcm_BigInteger_BigInteger();
        propertiesReversePermutationSign();
        compareImplementationsReversePermutationSign();
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

    private void propertiesGcd_int_int() {
        initialize("gcd(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, filter(q -> q.a != 0 || q.b != 0, P.pairs(P.integers())))) {
            int gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p, gcd, gcd_int_int_explicit(p.a, p.b));
            assertEquals(p, gcd, gcd(p.b, p.a));
            assertEquals(p, p.a % gcd, 0);
            assertEquals(p, p.b % gcd, 0);
            assertTrue(p, gcd >= 0);
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (int i : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p, p.a % i == 0 && p.b % i == 0);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            idempotent(j -> gcd(i, j), 1);
        }

        for (int i : take(LIMIT, P.nonzeroIntegers())) {
            assertEquals(i, gcd(i, i), Math.abs(i));
            assertEquals(i, gcd(i, 0), Math.abs(i));
        }

        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                u -> (u.a != 0 || u.b != 0) && (u.b != 0 || u.c != 0),
                P.triples(P.integers())
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            associative(MathUtils::gcd, t);
        }
    }

    private void compareImplementationsGcd_int_int() {
        Map<String, Function<Pair<Integer, Integer>, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> gcd_int_int_simplest(p.a, p.b));
        functions.put("explicit", p -> gcd_int_int_explicit(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        Iterable<Pair<Integer, Integer>> ps = filter(q -> q.a != 0 || q.b != 0, P.pairs(P.integers()));
        compareImplementations("gcd(int, int)", take(LIMIT, ps), functions);
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

    private void propertiesGcd_long_long() {
        initialize("gcd(long, long)");
        for (Pair<Long, Long> p : take(LIMIT, filter(q -> q.a != 0 || q.b != 0, P.pairs(P.longs())))) {
            long gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p, gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertEquals(p, gcd, gcd(p.b, p.a));
            assertTrue(p, gcd >= 0);
            assertEquals(p, p.a % gcd, 0L);
            assertEquals(p, p.b % gcd, 0L);
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            for (long l : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                assertFalse(p, p.a % l == 0 && p.b % l == 0);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            idempotent(m -> gcd(l, m), 1L);
        }

        for (long l : take(LIMIT, P.nonzeroLongs())) {
            assertEquals(l, gcd(l, l), Math.abs(l));
            assertEquals(l, gcd(l, 0L), Math.abs(l));
        }

        Iterable<Triple<Long, Long, Long>> ts = filter(
                u -> (u.a != 0L || u.b != 0L) && (u.b != 0L || u.c != 0L),
                P.triples(P.longs())
        );
        for (Triple<Long, Long, Long> t : take(LIMIT, ts)) {
            associative(MathUtils::gcd, t);
        }
    }

    private void compareImplementationsGcd_long_long1() {
        Map<String, Function<Pair<Long, Long>, Long>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> gcd_long_long_simplest(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        Iterable<Pair<Long, Long>> ps = filter(q -> q.a != 0 || q.b != 0, P.pairs(P.longs()));
        compareImplementations("gcd(long, long)", take(LIMIT, ps), functions);
    }

    private void compareImplementationsGcd_long_long2() {
        Map<String, Function<Pair<Long, Long>, Long>> functions = new LinkedHashMap<>();
        functions.put("explicit", p -> gcd_long_long_explicit(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        Iterable<Pair<Long, Long>> ps = filter(q -> q.a != 0 || q.b != 0, P.pairs(map(i -> (long) i, P.integers())));
        compareImplementations("gcd(long, long)", take(LIMIT, ps), functions);
    }

    private static @NotNull BigInteger lcm_explicit(@NotNull BigInteger x, @NotNull BigInteger y) {
        return head(orderedIntersection(rangeBy(x, x), rangeBy(y, y)));
    }

    private void propertiesLcm_BigInteger_BigInteger() {
        initialize("lcm(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p, lcm, lcm(p.b, p.a));
            assertEquals(p, lcm.signum(), 1);
            assertEquals(p, lcm.mod(p.a), BigInteger.ZERO);
            assertEquals(p, lcm.mod(p.b), BigInteger.ZERO);
            for (BigInteger i : take(TINY_LIMIT, P.range(BigInteger.ONE, lcm.subtract(BigInteger.ONE)))) {
                assertFalse(p, i.mod(p.a).equals(BigInteger.ZERO) && i.mod(p.b).equals(BigInteger.ZERO));
            }
        }

        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(map(s -> BigInteger.valueOf(s), P.positiveShorts()));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p, lcm, lcm_explicit(p.a, p.b));
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            idempotent(j -> lcm(i, j), BigInteger.ONE);
            idempotent(j -> lcm(j, j), i);
        }

        for (Triple<BigInteger, BigInteger, BigInteger> t : take(LIMIT, P.triples(P.positiveBigIntegers()))) {
            associative(MathUtils::lcm, t);
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(
                P.withElement(BigInteger.ZERO, P.negativeBigIntegers()),
                P.positiveBigIntegers()
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                lcm(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                lcm(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsLcm_BigInteger_BigInteger() {
        Map<String, Function<Pair<BigInteger, BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("explicit", p -> lcm_explicit(p.a, p.b));
        functions.put("standard", p -> lcm(p.a, p.b));
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(map(s -> BigInteger.valueOf(s), P.positiveShorts()));
        compareImplementations("lcm(BigInteger, BigInteger)", take(LIMIT, ps), functions);
    }

    private static boolean reversePermutationSign_alt(int i) {
        return BigInteger.valueOf(i).multiply(BigInteger.valueOf(i - 1)).shiftRight(1).and(BigInteger.ONE)
                .equals(BigInteger.ZERO);
    }

    private void propertiesReversePermutationSign() {
        initialize("reversePermutationSign()");
        for (int i : take(LIMIT, P.integers())) {
            boolean reversePermutationSign = reversePermutationSign(i);
            assertEquals(i, reversePermutationSign, reversePermutationSign_alt(i));
        }
    }

    private void compareImplementationsReversePermutationSign() {
        Map<String, Function<Integer, Boolean>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::reversePermutationSign_alt);
        functions.put("standard", MathUtils::reversePermutationSign);
        compareImplementations("reversePermutationSign()", take(LIMIT, P.integers()), functions);
    }
}
