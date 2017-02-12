package mho.wheels.iterables;

import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public class CachedIteratorProperties extends TestProperties {
    public CachedIteratorProperties() {
        super("CachedIterator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesGet_int();
        propertiesGet_BigInteger();
        propertiesGet_Iterable();
        propertiesKnownSize();
    }

    private void propertiesConstructor() {
        initialize("new CachedIterator(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            CachedIterator<Integer> cxs = new CachedIterator<>(xs);
            List<Integer> ys = new ArrayList<>();
            for (int i = 0; ; i++) {
                NullableOptional<Integer> ox = cxs.get(i);
                if (!ox.isPresent()) break;
                ys.add(ox.get());
            }
            assertEquals(xs, xs, ys);
        }

        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(P.withNull(EP.naturalIntegers())))) {
            new CachedIterator<>(xs);
        }
    }

    private void propertiesGet_int() {
        initialize("get(int)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            CachedIterator<Integer> cxs = new CachedIterator<>(p.a);
            NullableOptional<Integer> ox = cxs.get(p.b);
            assertEquals(p, ox, cxs.get(BigInteger.valueOf(p.b)));
            assertEquals(p, ox.isPresent(), p.b < p.a.size());
            if (ox.isPresent()) {
                assertEquals(p, p.a.get(p.b), ox.get());
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            CachedIterator<Integer> cxs = new CachedIterator<>(p.a);
            NullableOptional<Integer> ox = cxs.get(p.b);
            assertEquals(p, ox, cxs.get(BigInteger.valueOf(p.b)));
            assertTrue(p, ox.isPresent());
        }
    }

    private void propertiesGet_BigInteger() {
        initialize("get(BigInteger)");
        //noinspection Convert2MethodRef
        Iterable<Pair<List<Integer>, BigInteger>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                map(i -> BigInteger.valueOf(i), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<List<Integer>, BigInteger> p : take(LIMIT, ps)) {
            CachedIterator<Integer> cxs = new CachedIterator<>(p.a);
            NullableOptional<Integer> ox = cxs.get(p.b);
            assertEquals(p, ox.isPresent(), p.b.intValueExact() < p.a.size());
            if (ox.isPresent()) {
                assertEquals(p, p.a.get(p.b.intValueExact()), ox.get());
            }
        }

        //noinspection Convert2MethodRef
        Iterable<Pair<Iterable<Integer>, BigInteger>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                map(i -> BigInteger.valueOf(i), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<Iterable<Integer>, BigInteger> p : take(LIMIT, ps2)) {
            CachedIterator<Integer> cxs = new CachedIterator<>(p.a);
            assertTrue(p, cxs.get(p.b).isPresent());
        }
    }

    private void propertiesGet_Iterable() {
        initialize("get(Iterable<Integer>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).lists(P.naturalIntegersGeometric())
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Optional<List<Integer>> oxs = new CachedIterator<>(p.a).get(p.b);
            assertEquals(p, oxs.isPresent(), all(i -> i < p.a.size(), p.b));
            if (oxs.isPresent()) {
                List<Integer> xs = oxs.get();
                assertTrue(p, and(zipWith((i, x) -> Objects.equals(p.a.get(i), x), p.b, xs)));
            }
        }

        Iterable<Pair<Iterable<Integer>, List<Integer>>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).lists(P.naturalIntegersGeometric())
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(LIMIT, ps2)) {
            assertTrue(p, new CachedIterator<>(p.a).get(p.b).isPresent());
        }
    }

    private void propertiesKnownSize() {
        initialize("knownSize()");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            CachedIterator<Integer> xs = new CachedIterator<>(p.a);
            xs.get(p.b);
            Optional<Integer> oi = xs.knownSize();
            assertEquals(p, oi.isPresent(), p.b >= p.a.size() - 1);
            oi.ifPresent(i -> assertEquals(p, i, p.a.size()));
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            CachedIterator<Integer> xs = new CachedIterator<>(p.a);
            xs.get(p.b);
            assertFalse(p, xs.knownSize().isPresent());
        }
    }
}
