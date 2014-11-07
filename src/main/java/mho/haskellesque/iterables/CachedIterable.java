package mho.haskellesque.iterables;

import mho.haskellesque.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    public @NotNull Optional<Boolean> isLast(@Nullable T x) {
        if (iterator.hasNext()) return Optional.empty();
        if (cache.isEmpty()) return Optional.of(false);
        T last = IterableUtils.last(cache);
        return Optional.of(x == null ? last == null : x.equals(last));
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
