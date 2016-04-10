package mho.wheels.math;

import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Quadruple;
import mho.wheels.structures.Triple;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.ordering.Ordering.*;
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
        propertiesFallingFactorial();
        propertiesNumberOfArrangementsOfASet_int();
        compareImplementationsNumberOfArrangementsOfASet_int();
        propertiesNumberOfArrangementsOfASet_int_int();
        compareImplementationsNumberOfArrangementsOfASet_int_int();
        propertiesBinomialCoefficient();
        propertiesMultisetCoefficient();
        propertiesSubsetCount();
        propertiesPermutationCount();
        propertiesReversePermutationSign();
        compareImplementationsReversePermutationSign();
        propertiesFastGrowingCeilingInverse();
        propertiesCeilingLog();
        compareImplementationsCeilingLog();
        propertiesCeilingInverse();
        propertiesCeilingRoot();
        compareImplementationsCeilingRoot();
        propertiesSmallestPrimeFactor_int();
        compareImplementationsSmallestPrimeFactor_int();
        propertiesSmallestPrimeFactor_BigInteger();
        propertiesIsPrime_int();
        compareImplementationsIsPrime_int();
        propertiesIsPrime_BigInteger();
        propertiesPrimeFactors_int();
        propertiesPrimeFactors_BigInteger();
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

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.rangeDown(BigInteger.ZERO), P.positiveBigIntegers());
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

        for (int i : take(MEDIUM_LIMIT, P.positiveIntegersGeometric())) {
            assertTrue(i, lt(factorial(i), factorial(i + 1)));
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

        //noinspection Convert2MethodRef
        for (BigInteger i : take(MEDIUM_LIMIT, map(j -> BigInteger.valueOf(j), P.positiveIntegersGeometric()))) {
            assertTrue(i, lt(factorial(i), factorial(i.add(BigInteger.ONE))));
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

        for (int i : take(MEDIUM_LIMIT, P.positiveIntegersGeometric())) {
            assertTrue(i, lt(subfactorial(i), subfactorial(i + 1)));
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

        //noinspection Convert2MethodRef
        for (BigInteger i : take(MEDIUM_LIMIT, map(j -> BigInteger.valueOf(j), P.positiveIntegersGeometric()))) {
            assertTrue(i, lt(subfactorial(i), subfactorial(i.add(BigInteger.ONE))));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                subfactorial(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesFallingFactorial() {
        initialize("fallingFactorial(BigInteger, int)");
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BigInteger fallingFactorial = fallingFactorial(p.a, p.b);
            assertNotEquals(p, fallingFactorial.signum(), -1);
            assertTrue(p, ge(fallingFactorial(p.a.add(BigInteger.ONE), p.b), fallingFactorial));
        }

        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            assertEquals(i, fallingFactorial(BigInteger.valueOf(i), i), factorial(i));
        }

        //noinspection Convert2MethodRef
        for (BigInteger i : take(LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            assertEquals(i, fallingFactorial(i, 0), BigInteger.ONE);
            assertEquals(i, fallingFactorial(i, 1), i);
        }

        ps = map(p -> new Pair<>(BigInteger.valueOf(p.a), p.b), P.subsetPairs(P.naturalIntegersGeometric()));
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assertEquals(p, fallingFactorial(p.a, p.b), BigInteger.ZERO);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                fallingFactorial(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                fallingFactorial(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static @NotNull BigInteger numberOfArrangementsOfASet_int_alt(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger bigN = BigInteger.valueOf(n);
        return sumBigInteger(map(k -> fallingFactorial(bigN, k), range(0, n)));
    }

    private void propertiesNumberOfArrangementsOfASet_int() {
        initialize("numberOfArrangementsOfASet(int)");
        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            BigInteger n = numberOfArrangementsOfASet(i);
            assertEquals(i, numberOfArrangementsOfASet_int_alt(i), n);
            assertEquals(i, n.signum(), 1);
            assertTrue(i, lt(n, numberOfArrangementsOfASet(i + 1)));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                numberOfArrangementsOfASet(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsNumberOfArrangementsOfASet_int() {
        Map<String, Function<Integer, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::numberOfArrangementsOfASet_int_alt);
        functions.put("standard", MathUtils::numberOfArrangementsOfASet);
        Iterable<Integer> is = P.naturalIntegersGeometric();
        compareImplementations("numberOfArrangementsOfASet(int)", take(SMALL_LIMIT, is), functions);
    }

    private static @NotNull BigInteger numberOfArrangementsOfASet_int_int_alt(int minSize, int n) {
        if (minSize < 0) {
            throw new ArithmeticException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger bigN = BigInteger.valueOf(n);
        return sumBigInteger(map(k -> fallingFactorial(bigN, k), range(minSize, n)));
    }

    private void propertiesNumberOfArrangementsOfASet_int_int() {
        initialize("numberOfArrangementsOfASet(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegersGeometric()))) {
            BigInteger n = numberOfArrangementsOfASet(p.a, p.b);
            assertEquals(p, numberOfArrangementsOfASet_int_int_alt(p.a, p.b), n);
            assertTrue(p, ge(numberOfArrangementsOfASet(p.a, p.b + 1), n));
        }

        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            assertEquals(i, numberOfArrangementsOfASet(0, i), numberOfArrangementsOfASet(i));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.naturalIntegersGeometric()))) {
            assertEquals(p, numberOfArrangementsOfASet(p.b, p.a), BigInteger.ZERO);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
            try {
                numberOfArrangementsOfASet(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                numberOfArrangementsOfASet(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsNumberOfArrangementsOfASet_int_int() {
        Map<String, Function<Pair<Integer, Integer>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", p -> numberOfArrangementsOfASet_int_int_alt(p.a, p.b));
        functions.put("standard", p -> numberOfArrangementsOfASet(p.a, p.b));
        Iterable<Pair<Integer, Integer>> ps = P.pairs(P.naturalIntegersGeometric());
        compareImplementations("numberOfArrangementsOfASet(int, int)", take(LIMIT, ps), functions);
    }

    private void propertiesBinomialCoefficient() {
        initialize("binomialCoefficient(BigInteger, int)");
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BigInteger binomialCoefficient = binomialCoefficient(p.a, p.b);
            assertNotEquals(p, binomialCoefficient.signum(), -1);
            assertTrue(p, ge(binomialCoefficient(p.a.add(BigInteger.ONE), p.b), binomialCoefficient));
        }

        //noinspection Convert2MethodRef
        for (BigInteger i : take(LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            assertEquals(i, binomialCoefficient(i, 0), BigInteger.ONE);
            assertEquals(i, binomialCoefficient(i, 1), i);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.naturalIntegersGeometric()))) {
            assertEquals(p, binomialCoefficient(BigInteger.valueOf(p.a), p.b), BigInteger.ZERO);
            BigInteger binomialCoefficient = binomialCoefficient(BigInteger.valueOf(p.b), p.a);
            assertEquals(p, binomialCoefficient, binomialCoefficient(BigInteger.valueOf(p.b), p.b - p.a));
            assertEquals(p, binomialCoefficient, factorial(p.b).divide(factorial(p.a).multiply(factorial(p.b - p.a))));
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                binomialCoefficient(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                binomialCoefficient(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesMultisetCoefficient() {
        initialize("multisetCoefficient(BigInteger, int)");
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BigInteger multisetCoefficient = multisetCoefficient(p.a, p.b);
            assertNotEquals(p, multisetCoefficient.signum(), -1);
            assertTrue(p, ge(multisetCoefficient(p.a.add(BigInteger.ONE), p.b), multisetCoefficient));
        }

        //noinspection Convert2MethodRef
        for (BigInteger i : take(LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            assertEquals(i, multisetCoefficient(i, 0), BigInteger.ONE);
            assertEquals(i, multisetCoefficient(i, 1), i);
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.negativeBigIntegers(), P.naturalIntegers()))) {
            try {
                multisetCoefficient(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<BigInteger, Integer> p : take(LIMIT, P.pairs(P.naturalBigIntegers(), P.negativeIntegers()))) {
            try {
                multisetCoefficient(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSubsetCount() {
        initialize("subsetCount(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegersGeometric()))) {
            BigInteger subsetCount = subsetCount(p.a, p.b);
            assertNotEquals(p, subsetCount.signum(), -1);
            assertTrue(p, ge(subsetCount(p.a, p.b + 1), subsetCount));
        }

        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            assertEquals(i, subsetCount(0, i), BigInteger.ONE.shiftLeft(i));
            assertEquals(i, subsetCount(i, i), BigInteger.ONE);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.naturalIntegersGeometric()))) {
            assertEquals(p, subsetCount(p.b, p.a), BigInteger.ZERO);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.negativeIntegers(), P.naturalIntegers()))) {
            try {
                subsetCount(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegers(), P.negativeIntegers()))) {
            try {
                subsetCount(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesPermutationCount() {
        initialize("permutationCount(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
            BigInteger permutationCount = permutationCount(xs);
            assertEquals(xs, permutationCount.signum(), 1);
            for (List<Integer> ys : take(TINY_LIMIT, P.permutationsFinite(xs))) {
                assertEquals(xs, permutationCount(ys), permutationCount);
            }
        }

        for (List<Integer> xs : take(LIMIT, P.distinctLists(P.withNull(P.integersGeometric())))) {
            assertEquals(xs, permutationCount(xs), factorial(xs.size()));
        }
    }

    private static boolean reversePermutationSign_alt(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("i cannot be negative. Invalid i: " + i);
        }
        return BigInteger.valueOf(i).multiply(BigInteger.valueOf(i - 1)).shiftRight(1).and(BigInteger.ONE)
                .equals(BigInteger.ZERO);
    }

    private void propertiesReversePermutationSign() {
        initialize("reversePermutationSign()");
        for (int i : take(LIMIT, P.naturalIntegers())) {
            boolean reversePermutationSign = reversePermutationSign(i);
            assertEquals(i, reversePermutationSign, reversePermutationSign_alt(i));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                reversePermutationSign(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsReversePermutationSign() {
        Map<String, Function<Integer, Boolean>> functions = new LinkedHashMap<>();
        functions.put("alt", MathUtilsProperties::reversePermutationSign_alt);
        functions.put("standard", MathUtils::reversePermutationSign);
        compareImplementations("reversePermutationSign()", take(LIMIT, P.naturalIntegers()), functions);
    }

    private void propertiesFastGrowingCeilingInverse() {
        initialize("fastGrowingCeilingInverse(Function<Integer, BigInteger>, int, int, BigInteger)");
        Iterable<Pair<Integer, Integer>> ranges = P.bagPairs(P.integersGeometric());
        //noinspection Convert2MethodRef,RedundantCast
        Function<Pair<Integer, Integer>, Iterable<Function<Integer, BigInteger>>> fGenerator = range ->
                map(
                        is -> new FiniteDomainFunction<>(zip(range(range.a, range.b), is)),
                        P.bags(
                                range.b - range.a + 1,
                                (Iterable<BigInteger>) map(i -> BigInteger.valueOf(i), P.integersGeometric())
                        )
                );
        Iterable<Quadruple<Function<Integer, BigInteger>, Integer, Integer, BigInteger>> qs = map(
                q -> new Quadruple<>(q.a.b, q.a.a.a, q.a.a.b, q.b),
                P.dependentPairsInfinite(
                        P.dependentPairsInfinite(ranges, fGenerator),
                        p -> map(i -> p.b.apply(p.a.b).subtract(BigInteger.valueOf(i)), P.naturalIntegersGeometric())
                )
        );
        for (Quadruple<Function<Integer, BigInteger>, Integer, Integer, BigInteger> q : take(LIMIT, qs)) {
            int x = fastGrowingCeilingInverse(q.a, q.b, q.c, q.d);
            assertTrue(q, ge(q.a.apply(x), q.d));
            assertTrue(q, x == q.b || le(q.a.apply(x - 1), q.d));
        }

        Iterable<Quadruple<Function<Integer, BigInteger>, Integer, Integer, BigInteger>> qsFail = map(
                p -> new Quadruple<>(
                        new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(0, p.a))),
                        p.b.b,
                        p.b.a,
                        p.a
                ),
                P.pairs(P.bigIntegers(), P.subsetPairs(P.integers()))
        );
        for (Quadruple<Function<Integer, BigInteger>, Integer, Integer, BigInteger> q : take(LIMIT, qsFail)) {
            try {
                fastGrowingCeilingInverse(q.a, q.b, q.c, q.d);
                fail(q);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static int ceilingLog_alt(@NotNull BigInteger base, @NotNull BigInteger x) {
        //noinspection SuspiciousNameCombination
        return ceilingInverse(i -> base.pow(i.intValueExact()), BigInteger.ZERO, BigInteger.valueOf(x.bitLength()), x)
                .intValueExact();
    }

    private void propertiesCeilingLog() {
        initialize("ceilingLog(BigInteger, BigInteger)");
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(P.rangeUp(IntegerUtils.TWO), P.positiveBigIntegers());
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            int ceilingLog = ceilingLog(p.a, p.b);
            assertEquals(ps, ceilingLog, ceilingLog_alt(p.a, p.b));
            assertTrue(p, ceilingLog >= 0);
            assertTrue(p, ge(p.a.pow(ceilingLog), p.b));
            assertTrue(p, ceilingLog == 0 || lt(p.a.pow(ceilingLog - 1), p.b));
        }

        for (BigInteger i : take(LIMIT, P.rangeUp(IntegerUtils.TWO))) {
            assertEquals(i, ceilingLog(i, BigInteger.ONE), 0);
            assertEquals(i, ceilingLog(i, i), 1);
        }

        Iterable<Pair<BigInteger, BigInteger>> psFail = P.pairs(P.rangeDown(BigInteger.ONE), P.positiveBigIntegers());
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                ceilingLog(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        psFail = P.pairs(P.rangeUp(IntegerUtils.TWO), P.rangeDown(BigInteger.ZERO));
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, psFail)) {
            try {
                ceilingLog(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsCeilingLog() {
        Map<String, Function<Pair<BigInteger, BigInteger>, Integer>> functions = new LinkedHashMap<>();
        functions.put("alt", p -> ceilingLog_alt(p.a, p.b));
        functions.put("standard", p -> ceilingLog(p.a, p.b));
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(P.rangeUp(IntegerUtils.TWO), P.positiveBigIntegers());
        compareImplementations("ceilingLog(BigInteger, BigInteger)", take(LIMIT, ps), functions);
    }

    private void propertiesCeilingInverse() {
        initialize("ceilingInverse(Function<BigInteger, BigInteger>, BigInteger, BigInteger, BigInteger)");
        //noinspection Convert2MethodRef,RedundantCast
        Iterable<Pair<BigInteger, BigInteger>> ranges = P.bagPairs(
                (Iterable<BigInteger>) map(i -> BigInteger.valueOf(i), P.integersGeometric())
        );
        //noinspection Convert2MethodRef,RedundantCast
        Function<Pair<BigInteger, BigInteger>, Iterable<Function<BigInteger, BigInteger>>> fGenerator = range ->
                map(
                        is -> new FiniteDomainFunction<>(zip(range(range.a, range.b), is)),
                        P.bags(
                                range.b.intValueExact() - range.a.intValueExact() + 1,
                                (Iterable<BigInteger>) map(i -> BigInteger.valueOf(i), P.integersGeometric())
                        )
                );
        Iterable<Quadruple<Function<BigInteger, BigInteger>, BigInteger, BigInteger, BigInteger>> qs = map(
                q -> new Quadruple<>(q.a.b, q.a.a.a, q.a.a.b, q.b),
                P.dependentPairsInfinite(
                        P.dependentPairsInfinite(ranges, fGenerator),
                        p -> map(i -> p.b.apply(p.a.b).subtract(BigInteger.valueOf(i)), P.naturalIntegersGeometric())
                )
        );
        for (Quadruple<Function<BigInteger, BigInteger>, BigInteger, BigInteger, BigInteger> q : take(LIMIT, qs)) {
            BigInteger x = ceilingInverse(q.a, q.b, q.c, q.d);
            assertTrue(q, ge(q.a.apply(x), q.d));
            assertTrue(q, x.equals(q.b) || le(q.a.apply(x.subtract(BigInteger.ONE)), q.d));
        }

        Iterable<Quadruple<Function<BigInteger, BigInteger>, BigInteger, BigInteger, BigInteger>> qsFail = map(
                p -> new Quadruple<>(
                        new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(BigInteger.ZERO, p.a))),
                        p.b.b,
                        p.b.a,
                        p.a
                ),
                P.pairs(P.bigIntegers(), P.subsetPairs(P.bigIntegers()))
        );
        for (Quadruple<Function<BigInteger, BigInteger>, BigInteger, BigInteger, BigInteger> q : take(LIMIT, qsFail)) {
            try {
                ceilingInverse(q.a, q.b, q.c, q.d);
                fail(q);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static @NotNull BigInteger ceilingRoot_alt(int r, @NotNull BigInteger x) {
        if (r < 1) {
            throw new ArithmeticException("r must be positive. Invalid r: " + r);
        }
        if (x.signum() == -1) {
            throw new ArithmeticException("x cannot be negative. Invalid x: " + x);
        }
        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) return x;
        if (r == 1) return x;
        //noinspection SuspiciousNameCombination
        return ceilingInverse(i -> i.pow(r), BigInteger.ZERO, x, x);
    }

    private void propertiesCeilingRoot() {
        initialize("ceilingRoot(int, BigInteger)");
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsLogarithmicOrder(
                P.naturalBigIntegers(),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BigInteger ceilingRoot = ceilingRoot(p.b, p.a);
            assertEquals(ps, ceilingRoot, ceilingRoot_alt(p.b, p.a));
            assertNotEquals(p, ceilingRoot.signum(), -1);
            assertTrue(p, ge(ceilingRoot.pow(p.b), p.a));
            assertTrue(
                    p,
                    ceilingRoot.equals(BigInteger.ZERO) || lt(ceilingRoot.subtract(BigInteger.ONE).pow(p.b), p.a)
            );
        }

        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            assertEquals(i, ceilingRoot(1, i), i);
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            fixedPoint(j -> ceilingRoot(i, j), BigInteger.ZERO);
            fixedPoint(j -> ceilingRoot(i, j), BigInteger.ONE);
        }

        Iterable<Pair<BigInteger, Integer>> psFail = P.pairsLogarithmicOrder(
                P.negativeBigIntegers(),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            try {
                ceilingRoot(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(P.naturalBigIntegers(), P.rangeDown(0));
        for (Pair<BigInteger, Integer> p : take(LIMIT, psFail)) {
            try {
                ceilingRoot(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsCeilingRoot() {
        Map<String, Function<Pair<BigInteger, Integer>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", p -> ceilingRoot_alt(p.b, p.a));
        functions.put("standard", p -> ceilingRoot(p.b, p.a));
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsLogarithmicOrder(
                P.naturalBigIntegers(),
                P.positiveIntegersGeometric()
        );
        compareImplementations("ceilingRoot(int, BigInteger)", take(LIMIT, ps), functions);
    }

    private static int smallestPrimeFactor_int_simplest(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be at least 2. Invalid n: " + n);
        }
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) return i;
        }
        throw new IllegalStateException("unreachable");
    }

    private static int smallestPrimeFactor_int_alt1(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be at least 2. Invalid n: " + n);
        }
        int limit = Math.min(n - 1, ceilingRoot(2, BigInteger.valueOf(n)).intValueExact());
        for (int i = 2; i <= limit; i++) {
            if (n % i == 0) return i;
        }
        return n;
    }

    private static int smallestPrimeFactor_int_alt2(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be at least 2. Invalid n: " + n);
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return i;
        }
        return n;
    }

    private void propertiesSmallestPrimeFactor_int() {
        initialize("smallestPrimeFactor(int)");
        for (int i : take(LIMIT, P.rangeUp(2))) {
            int spf = smallestPrimeFactor(i);
            assertTrue(i, isPrime(spf));
            assertTrue(i, i % spf == 0);
        }

        for (int i : take(LIMIT, P.withScale(65536).rangeUpGeometric(2))) {
            int spf = smallestPrimeFactor(i);
            assertEquals(i, smallestPrimeFactor_int_simplest(i), spf);
            assertEquals(i, smallestPrimeFactor_int_alt1(i), spf);
            assertEquals(i, smallestPrimeFactor_int_alt2(i), spf);
        }

        for (int i : take(LIMIT, filterInfinite(MathUtils::isPrime, P.rangeUp(2)))) {
            assertEquals(i, smallestPrimeFactor(i), i);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                smallestPrimeFactor(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsSmallestPrimeFactor_int() {
        smallestPrimeFactor(3); // force sieve initialization
        Map<String, Function<Integer, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", MathUtilsProperties::smallestPrimeFactor_int_simplest);
        functions.put("alt1", MathUtilsProperties::smallestPrimeFactor_int_alt1);
        functions.put("alt2", MathUtilsProperties::smallestPrimeFactor_int_alt2);
        functions.put("standard", MathUtils::smallestPrimeFactor);
        Iterable<Integer> is = P.withScale(65536).rangeUpGeometric(2);
        compareImplementations("smallestPrimeFactor(int)", take(LIMIT, is), functions);
    }

    private void propertiesSmallestPrimeFactor_BigInteger() {
        initialize("smallestPrimeFactor(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(16).rangeUp(IntegerUtils.TWO))) {
            BigInteger spf = smallestPrimeFactor(i);
            assertTrue(i, isPrime(spf));
            assertTrue(i, i.mod(spf).equals(BigInteger.ZERO));
        }

        Iterable<BigInteger> is = filterInfinite(MathUtils::isPrime, P.withScale(8).rangeUp(IntegerUtils.TWO));
        for (BigInteger i : take(LIMIT, is)) {
            assertEquals(i, smallestPrimeFactor(i), i);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                smallestPrimeFactor(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static boolean isPrime_int_simplest(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be positive. Invalid n: " + n);
        }
        if (n == 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static boolean isPrime_int_alt1(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be positive. Invalid n: " + n);
        }
        if (n == 1) return false;
        int limit = Math.min(n - 1, ceilingRoot(2, BigInteger.valueOf(n)).intValueExact());
        for (int i = 2; i <= limit; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static boolean isPrime_int_alt2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be positive. Invalid n: " + n);
        }
        if (n == 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private void propertiesIsPrime_int() {
        initialize("isPrime(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            isPrime(i);
        }

        for (int i : take(LIMIT, P.withScale(65536).rangeUpGeometric(2))) {
            boolean isPrime = isPrime(i);
            assertEquals(i, isPrime_int_simplest(i), isPrime);
            assertEquals(i, isPrime_int_alt1(i), isPrime);
            assertEquals(i, isPrime_int_alt2(i), isPrime);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                isPrime(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsIsPrime_int() {
        smallestPrimeFactor(3); // force sieve initialization
        Map<String, Function<Integer, Boolean>> functions = new LinkedHashMap<>();
        functions.put("simplest", MathUtilsProperties::isPrime_int_simplest);
        functions.put("alt1", MathUtilsProperties::isPrime_int_alt1);
        functions.put("alt2", MathUtilsProperties::isPrime_int_alt2);
        functions.put("standard", MathUtils::isPrime);
        compareImplementations("isPrime(int)", take(LIMIT, P.withScale(65536).positiveIntegersGeometric()), functions);
    }

    private void propertiesIsPrime_BigInteger() {
        initialize("isPrime(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(16).rangeUp(IntegerUtils.TWO))) {
            isPrime(i);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                isPrime(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesPrimeFactors_int() {
        initialize("primeFactors(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Integer> primeFactors = toList(primeFactors(i));
            assertEquals(i, toList(map(BigInteger::intValueExact, primeFactors(BigInteger.valueOf(i)))), primeFactors);
            assertTrue(i, weaklyIncreasing(primeFactors));
            assertTrue(i, all(MathUtils::isPrime, primeFactors));
        }

        for (int i : take(LIMIT, filterInfinite(MathUtils::isPrime, P.rangeUp(2)))) {
            assertEquals(i, length(primeFactors(i)), 1);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                toList(primeFactors(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesPrimeFactors_BigInteger() {
        initialize("primeFactors(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(12).positiveBigIntegers())) {
            List<BigInteger> primeFactors = toList(primeFactors(i));
            assertTrue(i, weaklyIncreasing(primeFactors));
            assertTrue(i, all(MathUtils::isPrime, primeFactors));
        }

        Iterable<BigInteger> is = filterInfinite(MathUtils::isPrime, P.withScale(8).rangeUp(IntegerUtils.TWO));
        for (BigInteger i : take(LIMIT, is)) {
            assertEquals(i, length(primeFactors(i)), 1);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                toList(primeFactors(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
