package mho.wheels.testing;

import mho.wheels.iterables.IterableUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;

public class Testing {
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
            System.out.println("\t\t\t" + entry.getKey() + ((double) totalTime) / 1e9 + " s");
        }
    }
}
