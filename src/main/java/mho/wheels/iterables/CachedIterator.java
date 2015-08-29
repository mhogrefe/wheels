package mho.wheels.iterables;

import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.*;

public class CachedIterator<T> {
    private final @NotNull Iterator<T> iterator;
    private final @NotNull List<T> cache;
    private @NotNull Optional<Integer> size;
    private boolean checkUniqueness;
    private @NotNull Set<T> set;

    public CachedIterator(@NotNull Iterable<T> iterable, boolean checkUniqueness) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
        size = Optional.empty();
        this.checkUniqueness = checkUniqueness;
        set = new HashSet<>();
    }

    public CachedIterator(@NotNull Iterable<T> iterable) {
        this(iterable, false);
    }

    public @NotNull NullableOptional<T> get(int i) {
        for (int j = cache.size(); j <= i; j++) {
            if (!iterator.hasNext()) {
                return NullableOptional.empty();
            }
            advance();
        }
        return NullableOptional.of(cache.get(i));
    }

    private void advance() {
        T next = iterator.next();
        if (checkUniqueness && !set.add(next)) {
            throw new IllegalStateException("iterator cannot contain duplicates. Duplicate: " + next);
        }
        cache.add(next);
        if (!iterator.hasNext()) {
            size = Optional.of(cache.size());
        }
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

    public @NotNull Optional<Integer> knownSize() {
        return size;
    }

    public int size() {
        if (size.isPresent()) return size.get();
        while (iterator.hasNext()) {
            advance();
        }
        size = Optional.of(cache.size());
        return size.get();
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
