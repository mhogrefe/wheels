package mho.wheels.iterables;

import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

/**
 * This class wraps an {@code Iterable} in a way that allows efficient random access. The first time get(n) is called,
 * all values up to n are computed and stored, and thereafter get(k) for any k≤n is a constant-time operation. This
 * class is useful when an {@code Iterable} is costly to iterate through, when a deterministic view into a normally
 * non-deterministic {@code Iterable} is needed, or generally whenever random-access to an {@code Iterable} that isn't
 * a {@code List} is needed.
 *
 * @param <T> the type of the values in the wrapped {@code Iterable}
 */
public class CachedIterator<T> {
    /**
     * An iterator produced by the wrapped {@code Iterable}
     */
    private final @NotNull Iterator<T> iterator;

    /**
     * A list storing some prefix of the elements in the wrapped {@code Iterable}
     */
    private final @NotNull List<T> cache;

    /**
     * The size of the underlying {@code Iterable}. If the end hasn't been reached yet, it is empty. Cannot be negative
     */
    private @NotNull Optional<Integer> size;

    /**
     * Constructs a {@code CachedIterator} from an {@code Iterable}
     *
     * <ul>
     *  <li>{@code iterable} cannot be null.</li>
     *  <li>Any {@code CachedIterator} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param iterable the {@code Iterable} that {@code this} wraps
     */
    public CachedIterator(@NotNull Iterable<T> iterable) {
        iterator = iterable.iterator();
        cache = new ArrayList<>();
        size = !iterator.hasNext() ? Optional.of(0) : Optional.empty();
    }

    /**
     * Advances {@code iterator} by one element.
     *
     * <ul>
     *  <li>{@code iterator.hasNext()} must be true.</li>
     * </ul>
     */
    private void advance() {
        cache.add(iterator.next());
        if (!iterator.hasNext()) {
            size = Optional.of(cache.size());
        }
    }

    /**
     * Gets a zero-indexed element of the underlying {@code Iterable}. If {@code i} is too large, the result is empty.
     *
     * <ul>
     *  <li>{@code this} may be any {@code CachedIterator}.</li>
     *  <li>{@code i} cannot be negative.</li>
     *  <li>The result cannot be (an unwrapped) null.</li>
     * </ul>
     *
     * @param i the index of the element to be retrieved
     * @return the {@code i}th element of the underlying {@code Iterable}, starting from 0.
     */
    public @NotNull NullableOptional<T> get(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("i cannot be negative. Invalid i: " + i);
        }
        for (int j = cache.size(); j <= i; j++) {
            if (!iterator.hasNext()) {
                return NullableOptional.empty();
            }
            advance();
        }
        return NullableOptional.of(cache.get(i));
    }

    /**
     * Gets a zero-indexed element of the underlying {@code Iterable}. If {@code i} is too large (but still small
     * enough to fit in an {@code int}), the result is empty.
     *
     * <ul>
     *  <li>{@code this} may be any {@code CachedIterator}.</li>
     *  <li>{@code i} cannot be negative and must be less than 2<sup>31</sup>.</li>
     *  <li>The result cannot be (an unwrapped) null.</li>
     * </ul>
     *
     * @param i the index of the element to be retrieved
     * @return the {@code i}th element of the underlying {@code Iterable}, starting from 0.
     */
    public @NotNull NullableOptional<T> get(@NotNull BigInteger i) {
        return get(i.intValueExact());
    }

    /**
     * Given an {@code Iterable} of 0-based indices, returns a list of elements from the underlying {@code Iterable}
     * with the specified indices. If any of the indices are too large, the result is empty.
     *
     * <ul>
     *  <li>{@code this} may be any {@code CachedIterator}.</li>
     *  <li>{@code is} must be finite and cannot contain nulls or negative values.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param is a list of indices of elements to be retrieved
     * @return the elements at the specified indices
     */
    public @NotNull Optional<List<T>> get(@NotNull Iterable<Integer> is) {
        List<T> list = new ArrayList<>();
        for (int i : is) {
            NullableOptional<T> element = get(i);
            if (!element.isPresent()) return Optional.empty();
            list.add(element.get());
        }
        return Optional.of(list);
    }

    /**
     * Returns the size of the underlying {@code Iterable} if it has been iterated through already, or empty if not. If
     * the underlying {@code Iterable} is infinite, the result will always be empty.
     *
     * <ul>
     *  <li>{@code this} may be any {@code CachedIterator}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return the size of the underlying {@code Iterable, if known}
     */
    public @NotNull Optional<Integer> knownSize() {
        return size;
    }
}
