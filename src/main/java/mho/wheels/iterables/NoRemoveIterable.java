package mho.wheels.iterables;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * A wrapper around {@code Iterable} which disallows calling the {@link Iterator#remove()} method on any of its
 * iterators. If removal is attempted, an exception is thrown.
 *
 * @param <T> the type of the elements contained in this {@code NoRemoveIterable}
 */
public class NoRemoveIterable<T> implements Iterable<T> {
    private final @NotNull Iterable<T> inner;

    public NoRemoveIterable(@NotNull Iterable<T> inner) {
        this.inner = inner;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return new NoRemoveIterator<T>() {
            private final @NotNull Iterator<T> it = inner.iterator();

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
