package mho.wheels.iterables;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class EventuallyKnownSizeIterator<T> extends NoRemoveIterator<T> {
    private @NotNull Optional<BigInteger> outputSize = Optional.empty();
    private @NotNull BigInteger index = BigInteger.ZERO;
    private boolean reachedEnd = false;

    @Override
    public boolean hasNext() {
        return !reachedEnd;
    }

    public boolean outputSizeKnown() {
        return outputSize.isPresent();
    }

    public void setOutputSize(@NotNull BigInteger size) {
        outputSize = Optional.of(size);
        if (Ordering.gt(index, outputSize.get())) {
            throw new IllegalStateException("outputSize too small");
        }
        if (index.equals(outputSize.get())) {
            reachedEnd = true;
        }
    }

    @Override
    public T next() {
        if (reachedEnd) {
            throw new NoSuchElementException();
        }
        T next = advance();
        index = index.add(BigInteger.ONE);
        if (outputSize.isPresent() && index.equals(outputSize.get())) {
            reachedEnd = true;
        }
        return next;
    }

    public abstract T advance();
}
