package mho.wheels.ordering.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compares two values by an ordering specified by a list.
 *
 * @param <T> the type of element to be compared
 */
public class ListBasedComparator<T> implements Comparator<T> {
    /**
     * A map from a value to its position in the ordering list.
     */
    private final @NotNull Map<T, Integer> indexMap;

    /**
     * Creates a comparator which orders values based on the ordering given in a list; so the list ['b', 'c', 'a']
     * defines the {@code Character} ordering 'b' < 'c' < 'a', and all other {@code Character}s are not comparable. The
     * list can have repetitions, in which case only the first occurrence of a value is considered. The list may also
     * contain nulls.
     *
     * <ul>
     *  <li>{@code list} cannot be null.</li>
     *  <li>Any {@code ListBasedComparator} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param list a list of values
     */
    public ListBasedComparator(@NotNull List<T> list) {
        indexMap = new HashMap<>();
        int j = 0;
        for (T x : list) {
            if (!indexMap.containsKey(x)) {
                indexMap.put(x, j);
                j++;
            }
        }
    }

    /**
     * Compares two values based on which occurs first in a list. If either value is not in the list, an exception is
     * thrown.
     *
     * <ul>
     *  <ul>{@code x} must be a key of {@code indexMap}.</ul>
     *  <ul>{@code y} must be a key of {@code indexMap}.</ul>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param x the first value
     * @param y the second value
     * @return –1, 0, 1, depending on whether {@code x} is less than, equal to, or greater than {@code y}, respectively
     */
    @Override
    public int compare(T x, T y) {
        Integer xi = indexMap.get(x);
        if (xi == null) {
            throw new IllegalArgumentException("This comparator cannot compare the value " + x + ".");
        }
        Integer yi = indexMap.get(y);
        if (yi == null) {
            throw new IllegalArgumentException("This comparator cannot compare the value " + y + ".");
        }
        return x == y ? 0 : Integer.compare(xi, yi);
    }
}
