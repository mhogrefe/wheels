package mho.wheels.math;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
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
        propertiesGcd_List_BigInteger();
        compareImplementationsGcd_List_BigInteger();
        propertiesLcm_List_BigInteger();
        compareImplementationsLcm_List_BigInteger();
        propertiesFactorial_int();
        compareImplementationsFactorial_int();
        propertiesFactorial_BigInteger();
        compareImplementationsFactorial_BigInteger();
        propertiesSubfactorial_int();
        compareImplementationsSubfactorial_int();
        propertiesSubfactorial_BigInteger();
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
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            int gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_int_int_simplest(p.a, p.b));
            assertEquals(p, gcd, gcd_int_int_explicit(p.a, p.b));
            assertEquals(p, gcd, gcd(p.b, p.a));
            if (p.a != 0) {
                assertEquals(p, p.a % gcd, 0);
            }
            if (p.b != 0) {
                assertEquals(p, p.b % gcd, 0);
            }
            assertTrue(p, gcd >= 0);
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            if (p.a != 0 || p.b != 0) {
                for (int i : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                    assertFalse(p, p.a % i == 0 && p.b % i == 0);
                }
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            idempotent(j -> gcd(i, j), 1);
            assertEquals(i, gcd(i, i), Math.abs(i));
            assertEquals(i, gcd(i, 0), Math.abs(i));
        }

        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.integers()))) {
            associative(MathUtils::gcd, t);
        }
    }

    private void compareImplementationsGcd_int_int() {
        Map<String, Function<Pair<Integer, Integer>, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> gcd_int_int_simplest(p.a, p.b));
        functions.put("explicit", p -> gcd_int_int_explicit(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        compareImplementations("gcd(int, int)", take(LIMIT, P.pairs(P.integers())), functions);
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
        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            long gcd = gcd(p.a, p.b);
            assertEquals(p, gcd, gcd_long_long_simplest(p.a, p.b));
            if (Math.abs(p.a) <= Integer.MAX_VALUE && Math.abs(p.b) <= Integer.MAX_VALUE) {
                assertEquals(p, gcd, gcd_long_long_explicit(p.a, p.b));
            }
            assertEquals(p, gcd, gcd(p.b, p.a));
            assertTrue(p, gcd >= 0);
            if (p.a != 0L) {
                assertEquals(p, p.a % gcd, 0L);
            }
            if (p.b != 0L) {
                assertEquals(p, p.b % gcd, 0L);
            }
            assertEquals(p, gcd, gcd(Math.abs(p.a), Math.abs(p.b)));
            if (p.a != 0L || p.b != 0L) {
                for (long l : take(TINY_LIMIT, P.rangeUp(gcd + 1))) {
                    assertFalse(p, p.a % l == 0 && p.b % l == 0);
                }
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            idempotent(m -> gcd(l, m), 1L);
            assertEquals(l, gcd(l, l), Math.abs(l));
            assertEquals(l, gcd(l, 0L), Math.abs(l));
        }

        for (Triple<Long, Long, Long> t : take(LIMIT, P.triples(P.longs()))) {
            associative(MathUtils::gcd, t);
        }
    }

    private void compareImplementationsGcd_long_long1() {
        Map<String, Function<Pair<Long, Long>, Long>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> gcd_long_long_simplest(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        compareImplementations("gcd(long, long)", take(LIMIT, P.pairs(P.longs())), functions);
    }

    private void compareImplementationsGcd_long_long2() {
        Map<String, Function<Pair<Long, Long>, Long>> functions = new LinkedHashMap<>();
        functions.put("explicit", p -> gcd_long_long_explicit(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        compareImplementations("gcd(long, long)", take(LIMIT, P.pairs(map(i -> (long) i, P.integers()))), functions);
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

    private static @NotNull BigInteger gcd_List_BigInteger_alt(@NotNull List<BigInteger> xs) {
        return foldl(BigInteger::gcd, BigInteger.ZERO, sort(xs));
    }

    private void propertiesGcd_List_BigInteger() {
        initialize("gcd(List<BigInteger>)");
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            BigInteger gcd = gcd(is);
            assertEquals(is, gcd_List_BigInteger_alt(is), gcd);
            assertNotEquals(is, gcd.signum(), -1);
            assertEquals(is, gcd, gcd(toList(map(BigInteger::abs, is))));
        }

        propertiesFoldHelper(LIMIT, P, P.naturalBigIntegers(), BigInteger::gcd, MathUtils::gcd, i -> {}, true, true);

        for (List<BigInteger> is : take(LIMIT, P.listsWithElement(null, P.bigIntegers()))) {
            try {
                gcd(is);
                fail(is);
            } catch (NullPointerException ignored) {}
        }
    }

    private void compareImplementationsGcd_List_BigInteger() {
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::gcd_List_BigInteger_alt);
        functions.put("standard", MathUtils::gcd);
        compareImplementations("gcd(List<BigInteger>)", take(LIMIT, P.lists(P.bigIntegers())), functions);
    }

    private static @NotNull BigInteger lcm_List_BigInteger_alt(@NotNull List<BigInteger> xs) {
        if (xs.isEmpty()) {
            throw new ArithmeticException("xs cannot be empty.");
        }
        if (any(x -> x.signum() != 1, xs)) {
            throw new ArithmeticException("Every element of xs must be positive. Invalid xs: " + xs);
        }
        return foldl1(MathUtils::lcm, xs);
    }

    private void propertiesLcm_List_BigInteger() {
        initialize("lcm(List<BigInteger>)");
        for (List<BigInteger> is : take(LIMIT, P.listsAtLeast(1, P.positiveBigIntegers()))) {
            BigInteger lcm = lcm(is);
            assertEquals(is, lcm_List_BigInteger_alt(is), lcm);
            assertEquals(is, lcm.signum(), 1);
        }

        propertiesFoldHelper(LIMIT, P, P.positiveBigIntegers(), BigInteger::gcd, MathUtils::gcd, i -> {}, false, true);

        Iterable<List<BigInteger>> issFail = filterInfinite(
                is -> any(j -> j.signum() == -1, is),
                P.listsAtLeast(1, P.bigIntegers())
        );
        for (List<BigInteger> is : take(LIMIT, issFail)) {
            try {
                lcm(is);
                fail(is);
            } catch (ArithmeticException ignored) {}
        }

        issFail = filterInfinite(is -> !is.isEmpty(), P.listsWithElement(null, P.positiveBigIntegers()));
        for (List<BigInteger> is : take(LIMIT, issFail)) {
            try {
                lcm(is);
                fail(is);
            } catch (NullPointerException ignored) {}
        }
    }

    private void compareImplementationsLcm_List_BigInteger() {
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::lcm_List_BigInteger_alt);
        functions.put("standard", MathUtils::lcm);
        Iterable<List<BigInteger>> iss = P.listsAtLeast(1, P.positiveBigIntegers());
        compareImplementations("lcm(List<BigInteger>)", take(LIMIT, iss), functions);
    }

    private static @NotNull BigInteger factorial_int_simplest(int n) {
        return factorial(BigInteger.valueOf(n));
    }

    private static @NotNull BigInteger factorial_int_alt(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return productBigInteger(range(BigInteger.ONE, BigInteger.valueOf(n)));
    }

    private void propertiesFactorial_int() {
        initialize("factorial(int)");
        for (int i : take(MEDIUM_LIMIT, P.naturalIntegersGeometric())) {
            BigInteger factorial = factorial(i);
            assertEquals(i, factorial, factorial_int_simplest(i));
            assertEquals(i, factorial, factorial_int_alt(i));
            assertEquals(i, factorial.signum(), 1);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                factorial(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsFactorial_int() {
        Map<String, Function<Integer, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("simplest", MathUtilsProperties::factorial_int_simplest);
        functions.put("alt", MathUtilsProperties::factorial_int_alt);
        functions.put("standard", MathUtils::factorial);
        compareImplementations("factorial(int)", take(MEDIUM_LIMIT, P.naturalIntegersGeometric()), functions);
    }

    private static @NotNull BigInteger factorial_BigInteger_alt(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        return productBigInteger(range(BigInteger.ONE, n));
    }

    private void propertiesFactorial_BigInteger() {
        initialize("factorial(BigInteger)");
        //noinspection Convert2MethodRef
        for (BigInteger i : take(MEDIUM_LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            BigInteger factorial = factorial(i);
            assertEquals(i, factorial, factorial_BigInteger_alt(i));
            assertEquals(i, factorial.signum(), 1);
        }

        for (BigInteger i : take(LIMIT, P.negativeBigIntegers())) {
            try {
                factorial(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsFactorial_BigInteger() {
        Map<String, Function<BigInteger, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::factorial_BigInteger_alt);
        functions.put("standard", MathUtils::factorial);
        //noinspection Convert2MethodRef
        Iterable<BigInteger> is = map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric());
        compareImplementations("factorial(BigInteger)", take(MEDIUM_LIMIT, is), functions);
    }

    private static @NotNull BigInteger subfactorial_int_simplest(int n) {
        return subfactorial(BigInteger.valueOf(n));
    }

    private void propertiesSubfactorial_int() {
        initialize("subfactorial(int)");
        for (int i : take(MEDIUM_LIMIT, P.naturalIntegersGeometric())) {
            BigInteger subfactorial = subfactorial(i);
            assertEquals(i, subfactorial, subfactorial_int_simplest(i));
            assertNotEquals(i, subfactorial.signum(), -1);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                subfactorial(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsSubfactorial_int() {
        Map<String, Function<Integer, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("simplest", MathUtilsProperties::subfactorial_int_simplest);
        functions.put("standard", MathUtils::subfactorial);
        compareImplementations("subfactorial(int)", take(MEDIUM_LIMIT, P.naturalIntegersGeometric()), functions);
    }

    private void propertiesSubfactorial_BigInteger() {
        initialize("subfactorial(BigInteger)");
        //noinspection Convert2MethodRef
        for (BigInteger i : take(MEDIUM_LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            BigInteger subfactorial = subfactorial(i);
            assertNotEquals(i, subfactorial.signum(), -1);
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                subfactorial(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
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
