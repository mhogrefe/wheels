package mho.wheels.concurrency;

import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;

public class ConcurrencyUtils {
    public static @NotNull <A, B> Pair<String, B> evaluateFastest(
            @NotNull Map<String, Function<A, B>> functions,
            A input
    ) {
        ExecutorService service = Executors.newFixedThreadPool(functions.size());
        List<Callable<Pair<String, B>>> tasks = toList(
                map(p -> (() -> new Pair<>(p.a, p.b.apply(input))), fromMap(functions))
        );
        try {
            return service.invokeAny(tasks);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {
            throw new IllegalArgumentException();
        }
    }
}
