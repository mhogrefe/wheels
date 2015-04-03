package mho.wheels.testing;

import mho.wheels.iterables.IterableUtils;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Testing {
    public static void aeq(String message, int i, int j) {
        assertEquals(message, i, j);
    }

    public static void aeq(String message, long i, long j) {
        assertEquals(message, i, j);
    }

    public static void aeq(String message, float f1, float f2) {
        assertEquals(message, Float.toString(f1), Float.toString(f2));
    }

    public static void aeq(String message, double d1, double d2) {
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
}
