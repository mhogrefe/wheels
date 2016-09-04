package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableUtils;
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
    protected void testConstant() {
        propertiesIntPrimes();
        propertiesPrimes();
    }

    @Override
    protected void testBothModes() {
        propertiesPow();
        compareImplementationsPow();
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
        propertiesCompactPrimeFactors_int();
        propertiesCompactPrimeFactors_BigInteger();
        propertiesFactors_int();
        propertiesFactors_BigInteger();
        propertiesLargestPerfectPowerFactor_int_int();
        propertiesLargestPerfectPowerFactor_int_BigInteger();
        propertiesExpressAsPower();
        propertiesTotient_int();
        compareImplementationsTotient_int();
        propertiesTotient_BigInteger();
        propertiesInverseTotient();
    }

    private static int pow_simplest(int n, int p) {
        return BigInteger.valueOf(n).pow(p).intValueExact();
    }

    private static int pow_alt(int n, int p) {
        if (p < 0) {
            throw new ArithmeticException("p cannot be negative. Invalid p: " + p);
        }
        if (p == 0) return 1;
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) {
            if (p > 30) {
                throw new ArithmeticException("n^p must be less than 2^31. n: " + n + ", p: " + p);
            }
            return 1 << p;
        }
        if (n == -2) {
            if (p == 31) return Integer.MIN_VALUE;
            if (p > 31) {
                throw new ArithmeticException("n^p must be greater than or equal to -2^31. n: " + n + ", p: " + p);
            }
            int result = 1 << p;
            return (p & 1) == 0 ? result : -result;
        }
        int result = 1;
        for (boolean b : IntegerUtils.bigEndianBits(p)) {
            result = Math.multiplyExact(result, result);
            if (b) result = Math.multiplyExact(result, n);
        }
        return result;
    }

    private void propertiesPow() {
        initialize("pow(int, int)");
        BigInteger lowerLimit = BigInteger.valueOf(Integer.MIN_VALUE);
        BigInteger upperLimit = BigInteger.valueOf(Integer.MAX_VALUE);
        Iterable<Pair<Integer, Integer>> ps = filterInfinite(
                p -> {
                    BigInteger result = BigInteger.valueOf(p.a).pow(p.b);
                    return ge(result, lowerLimit) && le(result, upperLimit);
                },
                P.pairsLogarithmicOrder(P.integersGeometric(), P.naturalIntegersGeometric())
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            int power = pow(p.a, p.b);
            assertEquals(p, pow_simplest(p.a, p.b), power);
            assertEquals(p, pow_alt(p.a, p.b), power);
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(i,  pow(i, 0), 1);
            assertEquals(i,  pow(i, 1), i);
        }

        for (int i : take(LIMIT, P.naturalIntegers())) {
            assertEquals(i,  pow(1, i), 1);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers(), P.negativeIntegers()))) {
            try {
                pow(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        Iterable<Pair<Integer, Integer>> psFail = filterInfinite(
                p -> {
                    BigInteger result = BigInteger.valueOf(p.a).pow(p.b);
                    return lt(result, lowerLimit) || gt(result, upperLimit);
                },
                P.pairsLogarithmicOrder(P.integersGeometric(), P.naturalIntegersGeometric())
        );
        for (Pair<Integer, Integer> p : take(LIMIT, psFail)) {
            try {
                pow(p.a, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void compareImplementationsPow() {
        Map<String, Function<Pair<Integer, Integer>, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", p -> pow_simplest(p.a, p.b));
        functions.put("alt", p -> pow_alt(p.a, p.b));
        functions.put("standard", p -> pow(p.a, p.b));
        BigInteger lowerLimit = BigInteger.valueOf(Integer.MIN_VALUE);
        BigInteger upperLimit = BigInteger.valueOf(Integer.MAX_VALUE);
        Iterable<Pair<Integer, Integer>> ps = filterInfinite(
                p -> {
                    BigInteger result = BigInteger.valueOf(p.a).pow(p.b);
                    return ge(result, lowerLimit) && le(result, upperLimit);
                },
                P.pairsLogarithmicOrder(P.integersGeometric(), P.naturalIntegersGeometric())
        );
        compareImplementations("pow(int, int)", take(LIMIT, ps), functions, v -> P.reset());
    }

    private static int gcd_int_int_simplest(int x, int y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).intValueExact();
    }

    private static int gcd_int_int_explicit(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x < 0 || y < 0) {
            throw new ArithmeticException();
        }
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(x), factors(y)));
    }

    private void propertiesGcd_int_int() {
        initialize("gcd(int, int)");
        Iterable<Pair<Integer, Integer>> ps = filter(
                q -> !(q.a == Integer.MIN_VALUE && q.b == Integer.MIN_VALUE) &&
                        !(q.a == Integer.MIN_VALUE && q.b == 0) &&
                        !(q.a == 0 && q.b == Integer.MIN_VALUE),
                P.pairs(P.integers())
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
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
        }

        for (int i : take(LIMIT, filter(j -> j != Integer.MIN_VALUE, P.integers()))) {
            assertEquals(i, gcd(i, i), Math.abs(i));
            assertEquals(i, gcd(0, i), Math.abs(i));
            assertEquals(i, gcd(i, 0), Math.abs(i));
        }

        Iterable<Triple<Integer, Integer, Integer>> ts = filter(
                t -> {
                    int minValueCount = 0;
                    if (t.a == Integer.MIN_VALUE) minValueCount++;
                    if (t.b == Integer.MIN_VALUE) minValueCount++;
                    if (t.c == Integer.MIN_VALUE) minValueCount++;
                    return minValueCount < 2 &&
                            (minValueCount == 0 ||
                                    !(t.a == Integer.MIN_VALUE && t.b == 0) &&
                                    !(t.b == Integer.MIN_VALUE && t.a == 0) &&
                                    !(t.b == Integer.MIN_VALUE && t.c == 0) &&
                                    !(t.c == Integer.MIN_VALUE && t.b == 0)
                            );
                },
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
        compareImplementations("gcd(int, int)", take(LIMIT, P.pairs(P.integers())), functions, v -> P.reset());
    }

    private static long gcd_long_long_simplest(long x, long y) {
        return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).longValueExact();
    }

    private static long gcd_long_long_explicit(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x < 0 || y < 0) {
            throw new ArithmeticException();
        }
        if (x == 0) return y;
        if (y == 0) return x;
        return maximum(intersect(factors(BigInteger.valueOf(x)), factors(BigInteger.valueOf(y)))).longValue();
    }

    private void propertiesGcd_long_long() {
        initialize("gcd(long, long)");
        Iterable<Pair<Long, Long>> ps = filter(
                q -> !(q.a == Long.MIN_VALUE && q.b == Long.MIN_VALUE) &&
                        !(q.a == Long.MIN_VALUE && q.b == 0) &&
                        !(q.a == 0 && q.b == Long.MIN_VALUE),
                P.pairs(P.longs())
        );
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
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
        }

        for (long l : take(LIMIT, filter(j -> j != Integer.MIN_VALUE, P.longs()))) {
            assertEquals(l, gcd(l, l), Math.abs(l));
            assertEquals(l, gcd(0, l), Math.abs(l));
            assertEquals(l, gcd(l, 0), Math.abs(l));
        }

        Iterable<Triple<Long, Long, Long>> ts = filter(
                t -> {
                    int minValueCount = 0;
                    if (t.a == Long.MIN_VALUE) minValueCount++;
                    if (t.b == Long.MIN_VALUE) minValueCount++;
                    if (t.c == Long.MIN_VALUE) minValueCount++;
                    return minValueCount < 2 &&
                            (minValueCount == 0 ||
                                    !(t.a == Long.MIN_VALUE && t.b == 0) &&
                                    !(t.b == Long.MIN_VALUE && t.a == 0) &&
                                    !(t.b == Long.MIN_VALUE && t.c == 0) &&
                                    !(t.c == Long.MIN_VALUE && t.b == 0)
                            );
                },
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
        compareImplementations("gcd(long, long)", take(LIMIT, P.pairs(P.longs())), functions, v -> P.reset());
    }

    private void compareImplementationsGcd_long_long2() {
        Map<String, Function<Pair<Long, Long>, Long>> functions = new LinkedHashMap<>();
        functions.put("explicit", p -> gcd_long_long_explicit(p.a, p.b));
        functions.put("standard", p -> gcd(p.a, p.b));
        compareImplementations(
                "gcd(long, long)",
                take(LIMIT, P.pairs(map(i -> (long) i, P.integers()))),
                functions,
                v -> P.reset()
        );
    }

    private static @NotNull BigInteger lcm_explicit(@NotNull BigInteger x, @NotNull BigInteger y) {
        return head(orderedIntersection(iterate(n -> n.add(x), x), iterate(n -> n.add(y), y)));
    }

    private void propertiesLcm_BigInteger_BigInteger() {
        initialize("lcm(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            BigInteger lcm = lcm(p.a, p.b);
            assertEquals(p, lcm, lcm(p.b, p.a));
            assertEquals(p, lcm.signum(), 1);
            assertEquals(p, lcm.mod(p.a), BigInteger.ZERO);
            assertEquals(p, lcm.mod(p.b), BigInteger.ZERO);
            if (!lcm.equals(BigInteger.ONE)) {
                for (BigInteger i : take(TINY_LIMIT, P.range(BigInteger.ONE, lcm.subtract(BigInteger.ONE)))) {
                    assertFalse(p, i.mod(p.a).equals(BigInteger.ZERO) && i.mod(p.b).equals(BigInteger.ZERO));
                }
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
        compareImplementations("lcm(BigInteger, BigInteger)", take(LIMIT, ps), functions, v -> P.reset());
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
        compareImplementations(
                "gcd(List<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions,
                v -> P.reset()
        );
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
        compareImplementations("lcm(List<BigInteger>)", take(LIMIT, iss), functions, v -> P.reset());
    }

    private static @NotNull BigInteger factorial_int_simplest(int n) {
        return factorial(BigInteger.valueOf(n));
    }

    private static @NotNull BigInteger factorial_int_alt(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (n == 0) {
            return BigInteger.ONE;
        }
        return productBigInteger(
                toList(ExhaustiveProvider.INSTANCE.rangeIncreasing(BigInteger.ONE, BigInteger.valueOf(n)))
        );
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
        compareImplementations(
                "factorial(int)",
                take(MEDIUM_LIMIT, P.naturalIntegersGeometric()),
                functions,
                v -> P.reset()
        );
    }

    private static @NotNull BigInteger factorial_BigInteger_alt(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        return productBigInteger(toList(ExhaustiveProvider.INSTANCE.rangeIncreasing(BigInteger.ONE, n)));
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
        compareImplementations("factorial(BigInteger)", take(MEDIUM_LIMIT, is), functions, v -> P.reset());
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
        compareImplementations(
                "subfactorial(int)",
                take(MEDIUM_LIMIT, P.naturalIntegersGeometric()),
                functions,
                v -> P.reset()
        );
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
        return sumBigInteger(
                toList(map(k -> fallingFactorial(bigN, k), ExhaustiveProvider.INSTANCE.rangeIncreasing(0, n)))
        );
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
        compareImplementations("numberOfArrangementsOfASet(int)", take(SMALL_LIMIT, is), functions, v -> P.reset());
    }

    private static @NotNull BigInteger numberOfArrangementsOfASet_int_int_alt(int minSize, int n) {
        if (minSize < 0) {
            throw new ArithmeticException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (minSize > n) {
            return BigInteger.ZERO;
        }
        BigInteger bigN = BigInteger.valueOf(n);
        return sumBigInteger(
                toList(map(k -> fallingFactorial(bigN, k), ExhaustiveProvider.INSTANCE.rangeIncreasing(minSize, n)))
        );
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
        compareImplementations("numberOfArrangementsOfASet(int, int)", take(LIMIT, ps), functions, v -> P.reset());
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
        compareImplementations(
                "reversePermutationSign()",
                take(LIMIT, P.naturalIntegers()),
                functions,
                v -> P.reset()
        );
    }

    private void propertiesFastGrowingCeilingInverse() {
        initialize("fastGrowingCeilingInverse(Function<Integer, BigInteger>, int, int, BigInteger)");
        Iterable<Pair<Integer, Integer>> ranges = P.bagPairs(P.integersGeometric());
        //noinspection Convert2MethodRef,RedundantCast
        Function<Pair<Integer, Integer>, Iterable<Function<Integer, BigInteger>>> fGenerator = range ->
                map(
                        is -> new FiniteDomainFunction<>(
                                zip(ExhaustiveProvider.INSTANCE.rangeIncreasing(range.a, range.b), is)
                        ),
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
        compareImplementations("ceilingLog(BigInteger, BigInteger)", take(LIMIT, ps), functions, v -> P.reset());
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
                        is -> new FiniteDomainFunction<>(
                                zip(ExhaustiveProvider.INSTANCE.rangeIncreasing(range.a, range.b), is)
                        ),
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
        compareImplementations("ceilingRoot(int, BigInteger)", take(LIMIT, ps), functions, v -> P.reset());
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
        compareImplementations("smallestPrimeFactor(int)", take(LIMIT, is), functions, v -> P.reset());
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
        compareImplementations(
                "isPrime(int)",
                take(LIMIT, P.withScale(65536).positiveIntegersGeometric()),
                functions,
                v -> P.reset()
        );
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
            inverse(is -> toList(primeFactors(is)), IterableUtils::productInteger, i);
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
            inverse(is -> toList(primeFactors(is)), IterableUtils::productBigInteger, i);
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

    private void propertiesCompactPrimeFactors_int() {
        initialize("compactPrimeFactors(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Pair<Integer, Integer>> primeFactors = toList(compactPrimeFactors(i));
            assertEquals(
                    i,
                    toList(map(p -> new Pair<>(p.a.intValueExact(), p.b), compactPrimeFactors(BigInteger.valueOf(i)))),
                    primeFactors
            );
            inverse(
                    MathUtils::compactPrimeFactors,
                    (Iterable<Pair<Integer, Integer>> fs) -> productInteger(toList(map(p -> pow(p.a, p.b), fs))),
                    i
            );
            //noinspection RedundantCast
            assertTrue(i, weaklyIncreasing((Iterable<Integer>) map(p -> p.a, primeFactors)));
            assertTrue(i, all(MathUtils::isPrime, map(p -> p.a, primeFactors)));
            assertTrue(i, all(e -> e > 0, map(p -> p.b, primeFactors)));
        }

        for (int i : take(LIMIT, filterInfinite(MathUtils::isPrime, P.rangeUp(2)))) {
            assertEquals(i, length(compactPrimeFactors(i)), 1);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                toList(compactPrimeFactors(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesCompactPrimeFactors_BigInteger() {
        initialize("compactPrimeFactors(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(12).positiveBigIntegers())) {
            List<Pair<BigInteger, Integer>> primeFactors = toList(compactPrimeFactors(i));
            inverse(
                    MathUtils::compactPrimeFactors,
                    (Iterable<Pair<BigInteger, Integer>> fs) -> productBigInteger(toList(map(p -> p.a.pow(p.b), fs))),
                    i
            );
            //noinspection RedundantCast
            assertTrue(i, weaklyIncreasing((Iterable<BigInteger>) map(p -> p.a, primeFactors)));
            assertTrue(i, all(MathUtils::isPrime, map(p -> p.a, primeFactors)));
            assertTrue(i, all(e -> e > 0, map(p -> p.b, primeFactors)));
        }

        Iterable<BigInteger> is = filterInfinite(MathUtils::isPrime, P.withScale(8).rangeUp(IntegerUtils.TWO));
        for (BigInteger i : take(LIMIT, is)) {
            assertEquals(i, length(compactPrimeFactors(i)), 1);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                toList(compactPrimeFactors(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesFactors_int() {
        initialize("factors(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            List<Integer> factors = factors(i);
            assertEquals(i, toList(map(BigInteger::intValueExact, factors(BigInteger.valueOf(i)))), factors);
            assertTrue(i, increasing(factors));
            assertEquals(i, head(factors), 1);
            assertEquals(i, last(factors), i);
            assertTrue(i, all(f -> f > 0 && i % f == 0, factors));
        }

        for (int i : take(LIMIT, filterInfinite(MathUtils::isPrime, P.rangeUp(2)))) {
            assertEquals(i, length(factors(i)), 2);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                factors(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesFactors_BigInteger() {
        initialize("factors(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(12).positiveBigIntegers())) {
            List<BigInteger> factors = factors(i);
            assertTrue(i, increasing(factors));
            assertEquals(i, head(factors), BigInteger.ONE);
            assertEquals(i, last(factors), i);
            assertTrue(i, all(f -> f.signum() == 1 && i.mod(f).equals(BigInteger.ZERO), factors));
        }

        Iterable<BigInteger> is = filterInfinite(MathUtils::isPrime, P.withScale(8).rangeUp(IntegerUtils.TWO));
        for (BigInteger i : take(LIMIT, is)) {
            assertEquals(i, length(factors(i)), 2);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                factors(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesIntPrimes() {
        initializeConstant("intPrimes()");
        for (int p : take(LARGE_LIMIT, intPrimes())) {
            assertTrue(p, isPrime(p));
        }
    }

    private void propertiesPrimes() {
        initializeConstant("primes()");
        for (BigInteger p : take(LARGE_LIMIT, primes())) {
            assertTrue(p, isPrime(p));
        }
    }

    private void propertiesLargestPerfectPowerFactor_int_int() {
        initialize("largestPerfectPowerFactor(int, int)");
        Iterable<Pair<Integer, Integer>> ps = P.pairsLogarithmicOrder(
                P.positiveIntegers(),
                P.positiveIntegersGeometric()
        );
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            int lppf = largestPerfectPowerFactor(p.b, p.a);
            assertTrue(p, lppf > 0);
            assertEquals(p, p.a % pow(lppf, p.b), 0);
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            assertEquals(i, largestPerfectPowerFactor(1, i), i);
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            assertEquals(i, largestPerfectPowerFactor(i, 1), 1);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.rangeDown(0), P.positiveIntegers()))) {
            try {
                largestPerfectPowerFactor(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.positiveIntegers(), P.rangeDown(0)))) {
            try {
                largestPerfectPowerFactor(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesLargestPerfectPowerFactor_int_BigInteger() {
        initialize("largestPerfectPowerFactor(int, BigInteger)");
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(8).positiveBigIntegers(),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            BigInteger lppf = largestPerfectPowerFactor(p.b, p.a);
            assertEquals(p, lppf.signum(), 1);
            assertEquals(p, p.a.mod(lppf.pow(p.b)), BigInteger.ZERO);
        }

        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            assertEquals(i, largestPerfectPowerFactor(1, i), i);
        }

        for (int i : take(LIMIT, P.positiveIntegers())) {
            assertEquals(i, largestPerfectPowerFactor(i, BigInteger.ONE), BigInteger.ONE);
        }

        for (Pair<Integer, BigInteger> p : take(LIMIT, P.pairs(P.rangeDown(0), P.positiveBigIntegers()))) {
            try {
                largestPerfectPowerFactor(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        for (Pair<Integer, BigInteger> p : take(LIMIT, P.pairs(P.positiveIntegers(), P.rangeDown(BigInteger.ZERO)))) {
            try {
                largestPerfectPowerFactor(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesExpressAsPower() {
        initialize("expressAsPower(BigInteger)");
        for (BigInteger i : take(LIMIT, P.rangeUp(IntegerUtils.TWO))) {
            Pair<BigInteger, Integer> p = expressAsPower(i);
            assertTrue(i, ge(p.a, IntegerUtils.TWO));
            assertTrue(i, p.b > 0);
            inverse(MathUtils::expressAsPower, q -> q.a.pow(q.b), i);
            Pair<BigInteger, Integer> r = expressAsPower(p.a);
            assertEquals(i, r.a, p.a);
            assertEquals(i, r.b, 1);
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ONE))) {
            try {
                expressAsPower(i);
                fail(i);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static int totient_int_simplest(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        int totient = 1;
        for (int i = 2; i <= n; i++) {
            if (gcd(i, n) == 1) totient++;
        }
        return totient;
    }

    private void propertiesTotient_int() {
        initialize("totient(int)");
        for (int i : take(LIMIT, P.positiveIntegers())) {
            int totient = totient(i);
            assertTrue(i, totient > 0);
            assertTrue(i, totient <= i);
            assertEquals(i, totient, totient(BigInteger.valueOf(i)).intValueExact());
            assertTrue(i, inverseTotient(BigInteger.valueOf(totient)).contains(BigInteger.valueOf(i)));
        }

        for (int i : take(LIMIT, P.withScale(65536).rangeUpGeometric(2))) {
            int totient = totient(i);
            assertEquals(i, totient, totient_int_simplest(i));
        }

        for (int i : take(LIMIT, filterInfinite(MathUtils::isPrime, P.rangeUp(2)))) {
            assertEquals(i, totient(i), i - 1);
        }

        for (int i : take(LIMIT, P.rangeDown(0))) {
            try {
                totient(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void compareImplementationsTotient_int() {
        smallestPrimeFactor(3); // force sieve initialization
        Map<String, Function<Integer, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", MathUtilsProperties::totient_int_simplest);
        functions.put("standard", MathUtils::totient);
        Iterable<Integer> is = P.withScale(65536).rangeUpGeometric(2);
        compareImplementations("totient(int)", take(LIMIT, is), functions, v -> P.reset());
    }

    private void propertiesTotient_BigInteger() {
        initialize("totient(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(12).positiveBigIntegers())) {
            BigInteger totient = totient(i);
            assertEquals(i, totient.signum(), 1);
            assertTrue(i, le(totient, i));
        }

        for (BigInteger i : take(LIMIT, P.withScale(8).positiveBigIntegers())) {
            assertTrue(i, inverseTotient(totient(i)).contains(i));
        }

        Iterable<BigInteger> is = filterInfinite(MathUtils::isPrime, P.withScale(8).rangeUp(IntegerUtils.TWO));
        for (BigInteger i : take(LIMIT, is)) {
            assertEquals(i, totient(i), i.subtract(BigInteger.ONE));
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                totient(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesInverseTotient() {
        initialize("inverseTotient(BigInteger)");
        for (BigInteger i : take(LIMIT, P.withScale(8).positiveBigIntegers())) {
            List<BigInteger> inverseTotient = inverseTotient(i);
            assertTrue(i, increasing(inverseTotient));
            assertTrue(i, inverseTotient.isEmpty() || head(inverseTotient).signum() == 1);
            assertTrue(i, all(it -> totient(it).equals(i), inverseTotient));
            assertTrue(i, inverseTotient(i.shiftLeft(1).add(BigInteger.ONE)).isEmpty());
        }

        for (BigInteger i : take(LIMIT, P.rangeDown(BigInteger.ZERO))) {
            try {
                inverseTotient(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
