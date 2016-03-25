package mho.wheels.concurrency;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.function.Predicate;

public class ResultCache<A, B> {
    private static final int MAX_SIZE = Testing.LARGE_LIMIT;

    private final @NotNull Function<A, B> function;
    private final @NotNull Predicate<A> largeEnough;
    public final @NotNull HashMap<A, B> resultMap;
    public final @NotNull HashMap<A, Pair<Integer, Long>> costMap;
    public final @NotNull PriorityQueue<Pair<Long, A>> costQueue;

    public ResultCache(@NotNull Function<A, B> function, @NotNull Predicate<A> largeEnough) {
        this.function = function;
        this.largeEnough = largeEnough;
        resultMap = new HashMap<>();
        costMap = new HashMap<>();
        costQueue = new PriorityQueue<>((p, q) -> Long.compare(p.a, q.a));
    }

    public @NotNull B get(A input) {
        if (!largeEnough.test(input)) {
            return function.apply(input);
        }
        B result;
        result = resultMap.get(input);
        if (result != null) {
            synchronized (this) {
                Pair<Integer, Long> costPair = costMap.remove(input);
                int seconds = (int) (costPair.b / 1000);
                if (seconds > 0) {
                    System.out.println(">> Cached result reused: " + input + ", " + seconds + " second" +
                            (seconds == 1 ? "" : "s"));
                }
                costMap.put(input, new Pair<>(costPair.a + 1, costPair.b));
                long cost = costPair.b * costPair.a;
                costQueue.remove(new Pair<>(cost, input));
                cost += costPair.b;
                costQueue.add(new Pair<>(cost, input));
            }
            return result;
        } else {
            long startTime = System.currentTimeMillis();
            result = function.apply(input);
            long totalTime = System.currentTimeMillis() - startTime;
            if (totalTime < 10) {
                return result;
            }
            synchronized (this) {
                if (!resultMap.containsKey(input)) {
                    if (costQueue.size() == MAX_SIZE) {
                        dumpCheapestResult();
                    }
                    resultMap.put(input, result);
                    costMap.put(input, new Pair<>(1, totalTime));
                    costQueue.add(new Pair<>(totalTime, input));
                }
                return result;
            }
        }
    }

    private void dumpCheapestResult() {
        Pair<Long, A> cheapest = costQueue.poll();
        resultMap.remove(cheapest.b);
        costMap.remove(cheapest.b);
    }
}
