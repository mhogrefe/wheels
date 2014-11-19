package mho.wheels.iterables;

import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.*;

public class CachedIterable<T> {
    private final @NotNull Iterator<T> iterator;
    private final @NotNull List<T> cache;
    private @Nullable Integer size;

    public CachedIterable(@NotNull Iterable<T> iterable) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
        size = null;
    }

    public @NotNull NullableOptional<T> get(int i) {
        for (int j = cache.size(); j <= i; j++) {
            if (!iterator.hasNext()) {
                return NullableOptional.empty();
            }
            cache.add(iterator.next());
        }
        return NullableOptional.of(cache.get(i));
    }

    public @NotNull NullableOptional<T> get(@NotNull BigInteger i) {
        return get(i.intValueExact());
    }

    public @NotNull Optional<List<T>> get(@NotNull Iterable<Integer> is) {
        List<T> list = new ArrayList<>();
        for (int i : is) {
            NullableOptional<T> element = get(i);
            if (!element.isPresent()) return Optional.empty();
            list.add(element.get());
        }
        return Optional.of(list);
    }

    public @NotNull Optional<List<T>> getBigInteger(@NotNull Iterable<BigInteger> is) {
        return get(IterableUtils.map(BigInteger::intValueExact, is));
    }

    public @NotNull Optional<List<T>> select(@NotNull Iterable<Boolean> bs) {
        List<T> elements = new ArrayList<>();
        int i = 0;
        for (boolean b : bs) {
            if (b) {
                NullableOptional<T> element = get(i);
                if (!element.isPresent()) return Optional.empty();
                elements.add(element.get());
            }
            i++;
        }
        return Optional.of(elements);
    }

    public int size() {
        if (size != null) return size;
        while (iterator.hasNext()) {
            cache.add(iterator.next());
        }
        size = cache.size();
        return size;
    }

    public @NotNull NullableOptional<T> getLast() {
        if (iterator.hasNext() || cache.isEmpty()) return NullableOptional.empty();
        return NullableOptional.of(IterableUtils.last(cache));
    }

    public @NotNull Optional<Boolean> isLast(@Nullable T x) {
        NullableOptional<T> last = getLast();
        if (!last.isPresent()) return Optional.empty();
        return Optional.of(Objects.equals(last.get(), x));
    }

    public @Nullable T lastSoFar() {
        return IterableUtils.last(cache);
    }

    public void clearCache() {
        cache.clear();
    }

    public int cacheSize() {
        return cache.size();
    }
}
