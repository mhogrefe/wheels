package mho.wheels.math;

import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Quadruple;
import mho.wheels.testing.Demos;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;
import static mho.wheels.testing.Testing.SMALL_LIMIT;

@SuppressWarnings("UnusedDeclaration")
public class MathUtilsDemos extends Demos {
    public MathUtilsDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoGcd_int_int() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private void demoGcd_long_long() {
        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private void demoLcm_BigInteger_BigInteger() {
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.positiveBigIntegers()))) {
            System.out.println("lcm(" + p.a + ", " + p.b + ") = " + lcm(p.a, p.b));
        }
    }

    private void demoGcd_List_BigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.withScale(4).lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("gcd(" + listString + ") = " + gcd(is));
        }
    }

    private void demoLcm_List_BigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.withScale(4).listsAtLeast(1, P.positiveBigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("lcm(" + listString + ") = " + lcm(is));
        }
    }

    private void demoFactorial_int() {
        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            System.out.println(i + "! = " + factorial(i));
        }
    }

    private void demoFactorial_BigInteger() {
        //noinspection Convert2MethodRef
        for (BigInteger i : take(SMALL_LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            System.out.println(i + "! = " + factorial(i));
        }
    }

    private void demoSubfactorial_int() {
        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            System.out.println("!" + i + " = " + subfactorial(i));
        }
    }

    private void demoSubfactorial_BigInteger() {
        //noinspection Convert2MethodRef
        for (BigInteger i : take(SMALL_LIMIT, map(j -> BigInteger.valueOf(j), P.naturalIntegersGeometric()))) {
            System.out.println("!" + i + " = " + subfactorial(i));
        }
    }

    private void demoFallingFactorial() {
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("fallingFactorial(" + p.a + ", " + p.b + ") = " + fallingFactorial(p.a, p.b));
        }
    }

    private void demoNumberOfArrangementsOfASet_int() {
        for (int i : take(SMALL_LIMIT, P.naturalIntegersGeometric())) {
            System.out.println("numberOfArrangementsOfASet(" + i + ") = " + numberOfArrangementsOfASet(i));
        }
    }

    private void demoNumberOfArrangementsOfASet_int_int() {
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, P.pairs(P.naturalIntegersGeometric()))) {
            System.out.println("numberOfArrangementsOfASet(" + p.a + ", " + p.b + ") = " +
                    numberOfArrangementsOfASet(p.a, p.b));
        }
    }

    private void demoBinomialCoefficient() {
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("binomialCoefficient(" + p.a + ", " + p.b + ") = " + binomialCoefficient(p.a, p.b));
        }
    }

    private void demoMultisetCoefficient() {
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, Integer>> ps = P.pairs(
                map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("multisetCoefficient(" + p.a + ", " + p.b + ") = " + multisetCoefficient(p.a, p.b));
        }
    }

    private void demoSubsetCount() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.naturalIntegersGeometric()))) {
            System.out.println("subsetCount(" + p.a + ", " + p.b + ") = " + subsetCount(p.a, p.b));
        }
    }

    private void demoPermutationCount() {
        for (List<Integer> xs : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(xs.toString()));
            System.out.println("permutationCount(" + listString + ") = " + permutationCount(xs));
        }
    }

    private void demoReversePermutationSign() {
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("reversePermutationSign(" + i + ") = " + reversePermutationSign(i));
        }
    }

    private void demoFastGrowingCeilingInverse() {
        Iterable<Pair<Integer, Integer>> ranges = P.bagPairs(P.withScale(2).integersGeometric());
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
            System.out.println("fastGrowingCeilingInverse(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    fastGrowingCeilingInverse(q.a, q.b, q.c, q.d));
        }
    }

    private void demoCeilingLog() {
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairs(P.rangeUp(IntegerUtils.TWO), P.positiveBigIntegers());
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("ceilingLog(" + p.a + ", " + p.b + ") = " + ceilingLog(p.a, p.b));
        }
    }

    private void demoCeilingInverse() {
        //noinspection Convert2MethodRef,RedundantCast
        Iterable<Pair<BigInteger, BigInteger>> ranges = P.bagPairs(
                (Iterable<BigInteger>) map(i -> BigInteger.valueOf(i), P.withScale(2).integersGeometric())
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
            System.out.println("ceilingInverse(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    ceilingInverse(q.a, q.b, q.c, q.d));
        }
    }

    private void demoCeilingRoot() {
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsLogarithmicOrder(
                P.naturalBigIntegers(),
                P.positiveIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("ceilingRoot(" + p.b + ", " + p.a + ") = " + ceilingRoot(p.b, p.a));
        }
    }

    private void demoSmallestPrimeFactor_int() {
        for (int i : take(LIMIT, P.rangeUp(2))) {
            System.out.println("smallestPrimeFactor(" + i + ") = " + smallestPrimeFactor(i));
        }
    }

    private void demoSmallestPrimeFactor_BigInteger() {
        for (BigInteger i : take(LIMIT, P.withScale(16).rangeUp(IntegerUtils.TWO))) {
            System.out.println("smallestPrimeFactor(" + i + ") = " + smallestPrimeFactor(i));
        }
    }

    private void demoIsPrime_int() {
        for (int i : take(LIMIT, P.positiveIntegers())) {
            System.out.println(i + " is " + (isPrime(i) ? "" : "not ") + "prime");
        }
    }

    private void demoIsPrime_BigInteger() {
        for (BigInteger i : take(LIMIT, P.withScale(12).positiveBigIntegers())) {
            System.out.println(i + " is " + (isPrime(i) ? "" : "not ") + "prime");
        }
    }
}
