package mho.wheels.iterables;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * An {@code Iterator} whose size may be specified after iteration has begun; the {@link Iterator#hasNext()} method
 * will then respect this size.
 *
 * @param <T> the type of elements returned by the iterator
 */
public abstract class EventuallyKnownSizeIterator<T> extends NoRemoveIterator<T> {
    /**
     * The number of elements to be produced by this iterator, or empty if the number is unknown
     */
    private @NotNull Optional<BigInteger> outputSize = Optional.empty();

    /**
     * The zero-based index of the next element to be returned
     */
    private @NotNull BigInteger index = BigInteger.ZERO;

    /**
     * Whether there are any more elements to produce
     */
    private boolean reachedEnd = false;

    /**
     * Whether we're done iterating
     *
     * <ul>
     *  <li>{@code this} may be any {@code EventuallyKnownSizeIterator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @return true if there are more elements, false if not
     */
    @Override
    public boolean hasNext() {
        return !reachedEnd;
    }

    /**
     * Whether the number of elements to be produced is known at this point in time
     *
     * <ul>
     *  <li>{@code this} may be any {@code EventuallyKnownSizeIterator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @return whether the size of the output is known
     */
    public boolean outputSizeKnown() {
        return outputSize.isPresent();
    }

    /**
     * Sets the number of elements to be produced by this iterator.
     *
     * <ul>
     *  <li>{@code this} may be any {@code EventuallyKnownSizeIterator}.</li>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code size} must be at least the number of elements already produced.</li>
     * </ul>
     *
     * @param size the size of the output
     */
    public void setOutputSize(@NotNull BigInteger size) {
        outputSize = Optional.of(size);
        if (Ordering.gt(index, outputSize.get())) {
            throw new IllegalStateException("size must be at least the number of elements already produced. Invalid" +
                    " size: " + size);
        }
        if (index.equals(size)) {
            reachedEnd = true;
        }
    }

    /**
     * Returns the next element.
     *
     * <ul>
     *  <li>If the output size is known, the number of elements produced so far must be less than that size when this
     *  method is called.</li>
     *  <li>The result may be null or any value of type {@code T}.</li>
     * </ul>
     *
     * @return the next element
     */
    @Override
    public T next() {
        if (reachedEnd) {
            throw new NoSuchElementException("The number of elements produced so far must be less than the output " +
                    "size when this method is called.");
        }
        T next = advance();
        index = index.add(BigInteger.ONE);
        if (outputSize.isPresent() && index.equals(outputSize.get())) {
            reachedEnd = true;
        }
        return next;
    }

    /**
     * Returns the next element.
     *
     * <ul>
     *  <li>If the output size is known, the number of elements produced so far must be less than that size when this
     *  method is called.</li>
     *  <li>The result may be null or any value of type {@code T}.</li>
     * </ul>
     *
     * @return the next element
     */
    public abstract T advance();
}
