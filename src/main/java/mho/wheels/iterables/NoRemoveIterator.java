package mho.wheels.iterables;

import java.util.Iterator;

/**
 * An {@code Iterator} which does not support the {@link Iterator#remove()} method. If removal is attempted, an
 * exception is thrown.
 *
 * @param <T> the type of the elements returned by this {@code NoRemoveIterator}
 */
public abstract class NoRemoveIterator<T> implements Iterator<T> {
    /**
     * Throw an exception if removal is attempted.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException("This Iterator does not support removal.");
    }
}
