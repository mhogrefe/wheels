package mho.wheels.iterables;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class NoRemoveIterable<T> implements Iterable<T> {
    private final @NotNull Iterable<T> inner;

    public NoRemoveIterable(@NotNull Iterable<T> inner) {
        this.inner = inner;
    }

    @Override
    public Iterator<T> iterator() {
        return new NoRemoveIterator<T>() {
            private Iterator<T> it = inner.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next();
            }
        };
    }
}
