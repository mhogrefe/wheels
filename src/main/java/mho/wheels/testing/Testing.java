package mho.wheels.testing;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.iterables.IterableUtils.zip;
import static org.junit.Assert.*;

public class Testing {
    /**
     * Disallow instantiation
     */
    private Testing() {}

    public static void aeq(String message, int i, int j) {
        assertEquals(message, i, j);
    }

    public static void aeq(String message, long i, long j) {
        assertEquals(message, i, j);
    }

    public static void aeqf(float f, float g) {
        assertEquals(Float.toString(f) + " != " + Float.toString(g), Float.toString(f), Float.toString(g));
    }

    public static void aeqd(double d, double e) {
        assertEquals(Double.toString(d) + " != " + Double.toString(e), Double.toString(d), Double.toString(e));
    }

    public static void aeqf(String message, float f1, float f2) {
        assertEquals(message, Float.toString(f1), Float.toString(f2));
    }

    public static void aeqd(String message, double d1, double d2) {
        assertEquals(message, Double.toString(d1), Double.toString(d2));
    }

    public static void aeq(String message, BigDecimal x, BigDecimal y) {
        assertEquals(message, x.stripTrailingZeros(), y.stripTrailingZeros());
    }

    public static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }

    public static void aeqit(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    public static <T> void aeqit(@NotNull String message, @NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        assertTrue(message, IterableUtils.equal(xs, ys));
    }

    public static <A, B> void compareImplementations(
            @NotNull String method,
            @NotNull Iterable<A> inputs,
            @NotNull Map<String, Function<A, B>> functions
    ) {
        System.out.println("\t\tcomparing " + method + " implementations...");
        for (Map.Entry<String, Function<A, B>> entry : functions.entrySet()) {
            Function<A, B> function = entry.getValue();
            long totalTime = 0;
            for (A input : inputs) {
                long time = System.nanoTime();
                function.apply(input);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\t" + entry.getKey() + ": " + ((double) totalTime) / 1e9 + " s");
        }
    }

    public static <T> Map<T, Integer> sampleCount(int sampleSize, @NotNull Iterable<T> xs) {
        Map<T, Integer> counts = new LinkedHashMap<>();
        for (T value : take(sampleSize, xs)) {
            Integer count = counts.get(value);
            if (count == null) count = 0;
            counts.put(value, count + 1);
        }
        return counts;
    }

    public static <T> Map<T, Integer> topSampleCount(int sampleSize, int topCount, @NotNull Iterable<T> xs) {
        SortedMap<Integer, List<T>> frequencyMap = new TreeMap<>();
        for (Map.Entry<T, Integer> entry : sampleCount(sampleSize, xs).entrySet()) {
            int frequency = entry.getValue();
            List<T> elements = frequencyMap.get(frequency);
            if (elements == null) {
                elements = new ArrayList<>();
                frequencyMap.put(-frequency, elements);
            }
            elements.add(entry.getKey());
        }
        Map<T, Integer> filteredCounts = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<Integer, List<T>> entry : frequencyMap.entrySet()) {
            for (T x : entry.getValue()) {
                if (i == topCount) return filteredCounts;
                filteredCounts.put(x, -entry.getKey());
                i++;
            }
        }
        return filteredCounts;
    }

    public static <T> void symmetric(@NotNull BiPredicate<T, T> relation, @NotNull Pair<T, T> p) {
        assertEquals(p.toString(), relation.test(p.a, p.b), relation.test(p.b, p.a));
    }

    public static <T> void antiSymmetric(@NotNull BiPredicate<T, T> relation, @NotNull Pair<T, T> p) {
        assertNotEquals(p.toString(), relation.test(p.a, p.b), relation.test(p.b, p.a));
    }

    public static <T> void transitive(@NotNull BiPredicate<T, T> relation, @NotNull Triple<T, T, T> t) {
        if (relation.test(t.a, t.b) && relation.test(t.b, t.c)) {
            assertTrue(t.toString(), relation.test(t.a, t.c));
        }
    }

    public static <T> void fixedPoint(@NotNull Function<T, T> f, @NotNull T x) {
        assertEquals(x.toString(), f.apply(x), x);
    }

    public static <T> void idempotent(@NotNull Function<T, T> f, @NotNull T x) {
        T y = f.apply(x);
        assertEquals(x.toString(), f.apply(y), y);
    }

    public static <T> void isInvolution(@NotNull Function<T, T> f, @NotNull T x) {
        assertEquals(x.toString(), f.apply(f.apply(x)), x);
    }

    public static <A, B> void inverses(@NotNull Function<A, B> f, @NotNull Function<B, A> g, @NotNull A x) {
        assertEquals(x.toString(), g.apply(f.apply(x)), x);
    }

    public static <A, B> void commutative(@NotNull BiFunction<A, A, B> f, @NotNull Pair<A, A> p) {
        assertEquals(p.toString(), f.apply(p.a, p.b), f.apply(p.b, p.a));
    }

    public static <A, B> void anticommutative(
            @NotNull BiFunction<A, A, B> f,
            @NotNull Function<B, B> negate,
            @NotNull Pair<A, A> p) {
        assertEquals(p.toString(), f.apply(p.a, p.b), negate.apply(f.apply(p.b, p.a)));
    }

    public static <T> void associative(@NotNull BiFunction<T, T, T> f, @NotNull Triple<T, T, T> t) {
        assertEquals(t.toString(), f.apply(f.apply(t.a, t.b), t.c), f.apply(t.a, f.apply(t.b, t.c)));
    }

    public static <T> void testEqualsHelper(@NotNull List<T> xs, @NotNull List<T> ys) {
        for (T x : xs) {
            //noinspection ObjectEqualsNull
            assertFalse(x.toString(), x.equals(null));
        }
        for (int i = 0; i < xs.size(); i++) {
            for (int j = 0; j < xs.size(); j++) {
                T x = xs.get(i);
                T y = ys.get(j);
                assertEquals(new Pair<>(x, y).toString(), i == j, x.equals(y));
            }
        }
    }

    public static <T> void propertiesEqualsHelper(
            @NotNull IterableProvider P,
            @NotNull Iterable<T> xs,
            @NotNull BiPredicate<T, T> equals,
            int limit
    ) {
        for (T x : take(limit, xs)) {
            //noinspection EqualsWithItself
            assertTrue(x.toString(), x.equals(x));
            //noinspection ObjectEqualsNull
            assertFalse(x.toString(), x.equals(null));
        }

        for (Pair<T, T> p : take(limit, zip(xs, xs))) {
            //noinspection ConstantConditions
            assertTrue(p.toString(), p.a.equals(p.b));
        }

        for (Pair<T, T> p : take(limit, P.pairs(xs, xs))) {
            symmetric(equals, p);
        }

        Iterable<Triple<T, T, T>> ts = P.triples(xs, xs, xs);
        for (Triple<T, T, T> t : take(limit, ts)) {
            transitive(equals, t);
        }
    }

    public static <T> void propertiesHashCodeHelper(@NotNull Iterable<T> xs, int limit) {
        for (Pair<T, T> p : take(limit, zip(xs, xs))) {
            assertEquals(p.toString(), xs.hashCode(), xs.hashCode());
        }
    }

    public static <T> void testNoRemove(@NotNull Iterable<T> xs) {
        Iterator<T> it = xs.iterator();
        while (it.hasNext()) {
            it.next();
            try {
                it.remove();
                fail(IterableUtils.toString(20, xs));
            } catch (UnsupportedOperationException ignored) {}
        }
    }
}
