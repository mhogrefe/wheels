package mho.wheels.iterables;

import java.util.Iterator;

public abstract class NoRemoveIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
        throw new UnsupportedOperationException("This Iterator does not support removal.");
    }
}
