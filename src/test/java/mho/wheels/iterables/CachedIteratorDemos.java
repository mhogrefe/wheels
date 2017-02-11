package mho.wheels.iterables;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class CachedIteratorDemos extends Demos {
    public CachedIteratorDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("new CachedIterator(" + middle(xs.toString()) + ") = " + new CachedIterator<>(xs));
        }
    }

    private void demoConstructor_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(P.withNull(EP.naturalIntegers())))) {
            System.out.println("new CachedIterator(" + middle(its(xs)) + ") = " + new CachedIterator<>(xs));
        }
    }

    private void demoGet_int_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(p.a.toString()) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoGet_int_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(its(p.a)) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoGet_BigInteger_finite() {
        //noinspection Convert2MethodRef
        Iterable<Pair<List<Integer>, BigInteger>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                map(i -> BigInteger.valueOf(i), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<List<Integer>, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(p.a.toString()) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoGet_BigInteger_infinite() {
        //noinspection Convert2MethodRef
        Iterable<Pair<Iterable<Integer>, BigInteger>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                map(i -> BigInteger.valueOf(i), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<Iterable<Integer>, BigInteger> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(its(p.a)) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoGet_Iterable_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).lists(P.naturalIntegersGeometric())
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(p.a.toString()) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoGet_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, List<Integer>>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).lists(P.naturalIntegersGeometric())
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("new CachedIterator(" + middle(its(p.a)) + ").get(" + p.b + ") = " +
                    new CachedIterator<>(p.a).get(p.b));
        }
    }

    private void demoKnownSize_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            CachedIterator<Integer> xs = new CachedIterator<>(p.a);
            xs.get(p.b);
            System.out.println("new CachedIterator(" + middle(p.a.toString()) + ") after advancing " + p.b + " time" +
                    (p.b == 1 ? "" : "s") + ": " + xs.knownSize());
        }
    }

    private void demoKnownSize_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            CachedIterator<Integer> xs = new CachedIterator<>(p.a);
            xs.get(p.b);
            System.out.println("new CachedIterator(" + middle(its(p.a)) + ") after advancing " + p.b + " time" +
                    (p.b == 1 ? "" : "s") + ": " + xs.knownSize());
        }
    }
}
