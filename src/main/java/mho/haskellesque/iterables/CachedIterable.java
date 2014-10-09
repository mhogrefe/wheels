package mho.haskellesque.iterables;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CachedIterable<T> {
    private Iterator<T> iterator;
    private List<T> cache;
    private Integer size;

    public CachedIterable(Iterable<T> iterable) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
        size = null;
    }

    public Optional<T> get(int i) {
        for (int j = cache.size(); j <= i; j++) {
            if (!iterator.hasNext()) {
                return Optional.empty();
            }
            cache.add(iterator.next());
        }
        return Optional.of(cache.get(i));
    }

    public Optional<T> get(BigInteger i) {
        return get(i.intValue());
    }

    public Optional<List<T>> get(Iterable<Integer> is) {
        List<T> list = new ArrayList<>();
        for (int i : is) {
            Optional<T> element = get(i);
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
                Optional<T> element = get(i);
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

    public void clearCache() {
        cache.clear();
    }

    public int cacheSize() {
        return cache.size();
    }
}
