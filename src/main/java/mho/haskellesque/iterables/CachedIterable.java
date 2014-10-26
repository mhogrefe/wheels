package mho.haskellesque.iterables;

import mho.haskellesque.structures.NullableOptional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CachedIterable<T> {
    private final Iterator<T> iterator;
    private final List<T> cache;
    private Integer size;

    public CachedIterable(Iterable<T> iterable) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
        size = null;
    }

    public NullableOptional<T> get(int i) {
        for (int j = cache.size(); j <= i; j++) {
            if (!iterator.hasNext()) {
                return NullableOptional.empty();
            }
            cache.add(iterator.next());
        }
        return NullableOptional.of(cache.get(i));
    }

    public NullableOptional<T> get(BigInteger i) {
        return get(i.intValue());
    }

    public Optional<List<T>> get(Iterable<Integer> is) {
        List<T> list = new ArrayList<>();
        for (int i : is) {
            NullableOptional<T> element = get(i);
            if (!element.isPresent()) return Optional.empty();
            list.add(element.get());
        }
        return Optional.of(list);
    }

    public Optional<List<T>> getBigInteger(Iterable<BigInteger> is) {
        return get(IterableUtils.map(BigInteger::intValue, is));
    }

    public Optional<List<T>> select(Iterable<Boolean> bs) {
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

    public Optional<Boolean> isLast(T x) {
        if (iterator.hasNext()) return Optional.empty();
        if (cache.isEmpty()) return Optional.of(false);
        T last = IterableUtils.last(cache);
        return Optional.of(x == null ? last == null : x.equals(last));
    }

    public T lastSoFar() {
        return IterableUtils.last(cache);
    }

    public void clearCache() {
        cache.clear();
    }

    public int cacheSize() {
        return cache.size();
    }
}
