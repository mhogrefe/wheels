package mho.wheels.math;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.testing.Testing.*;

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

    private void demoReversePermutationSign() {
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("reversePermutationSign(" + i + ") = " + reversePermutationSign(i));
        }
    }
}
