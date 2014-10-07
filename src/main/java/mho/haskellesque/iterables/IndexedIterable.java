package mho.haskellesque.iterables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class IndexedIterable<T> {
    private Iterator<T> iterator;
    private List<T> cache;

    public IndexedIterable(Iterable<T> iterable) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
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

    public Iterable<T> get(Iterable<Integer> is) {
        final Iterator<Integer> isi = is.iterator();
        return () -> new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return isi.hasNext();
            }

            @Override
            public T next() {
                return get(isi.next()).get();
            }
        };
    }

    public void clearCache() {
        cache.clear();
    }

    public int cacheSize() {
        return cache.size();
    }
}
