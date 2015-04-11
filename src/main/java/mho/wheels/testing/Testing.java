package mho.wheels.testing;

import mho.wheels.iterables.IterableUtils;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.take;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
