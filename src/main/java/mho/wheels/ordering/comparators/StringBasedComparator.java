package mho.wheels.ordering.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Compares two {@code Character}s by an ordering specified by a {@code String}.
 */
public class StringBasedComparator implements Comparator<Character> {
    /**
     * A map from a {@code Character} to its position in the ordering {@code String}.
     */
    private final @NotNull Map<Character, Integer> indexMap;

    /**
     * Creates a comparator which orders {@code Character}s based on the ordering given in a {@code String}; so the
     * {@code String} "bca" defines the {@code Character} ordering 'b' < 'c' < 'a', and all other {@code Character}s
     * are not comparable. The {@code String} can have repetitions, in which case only the first occurrence of a
     * {@code Character} is considered.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>Any {@code StringBasedComparator} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param s a {@code String}
     */
    public StringBasedComparator(@NotNull String s) {
        indexMap = new HashMap<>();
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!indexMap.containsKey(c)) {
                indexMap.put(c, j);
                j++;
            }
        }
    }

    /**
     * Compares two {@code Character}s based on which occurs first in a {@code String}. If either {@code Character} is
     * not in the {@code String}, an exception is thrown.
     *
     * <ul>
     *  <ul>{@code x} must be a key of {@code indexMap}.</ul>
     *  <ul>{@code y} must be a key of {@code indexMap}.</ul>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param x the first {@code Character}
     * @param y the second {@code Character}
     * @return –1, 0, 1, depending on whether {@code x} is less than, equal to, or greater than {@code y}, respectively
     */
    @Override
    public int compare(Character x, Character y) {
        Integer xi = indexMap.get(x);
        if (xi == null) {
            throw new IllegalArgumentException("This comparator cannot compare the character " + x + ".");
        }
        Integer yi = indexMap.get(y);
        if (yi == null) {
            throw new IllegalArgumentException("This comparator cannot compare the character " + y + ".");
        }
        return x == y ? 0 : Integer.compare(xi, yi);
    }
}
