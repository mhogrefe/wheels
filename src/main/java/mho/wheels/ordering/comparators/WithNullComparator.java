package mho.wheels.ordering.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;

/**
 * Compares two possibly-null values. Null is considered to be less than any other value; for comparing non-null
 * values, a provided comparator or the natural ordering is used.
 *
 * @param <T> the type of element to be compared
 */
public class WithNullComparator<T extends Comparable<T>> implements Comparator<T> {
    /**
     * The {@code Comparator} used to compare non-null values of type {@code T}. It can be null, in which case the
     * natural ordering of {@code T} is used.
     */
    private final @NotNull Optional<Comparator<T>> nonNullComparator;

    /**
     * Constructs a {@code WithNullComparator} which uses the natural ordering to compare elements.
     */
    public WithNullComparator() {
        this.nonNullComparator = Optional.empty();
    }

    /**
     * Constructs a {@code WithNullComparator} which uses a provided {@code Comparator} to compare non-null elements.
     *
     * <ul>
     *  <li>{@code nonNullComparator} must obey its contract. See {@link java.util.Comparator}.</li>
     * </ul>
     *
     * @param nonNullComparator the {@code Comparator} used to compare non-null values of type {@code T}
     */
    @SuppressWarnings("NullableProblems")
    public WithNullComparator(@NotNull Comparator<T> nonNullComparator) {
        this.nonNullComparator = Optional.of(nonNullComparator);
    }

    /**
     * Compares {@code x} and {@code y}. If they are both null, they are equal; if one is null, it is smaller; if
     * neither is null and a {@code nonNullComparator} is provided, it is used to compare them; otherwise, they are
     * compared by their natural ordering.
     *
     * <ul>
     *  <ul>{@code x} may be any value.</ul>
     *  <ul>{@code y} may be any value.</ul>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param x the first value
     * @param y the second value
     * @return –1, 0, 1, depending on whether {@code x} is less than, equal to, or greater than {@code y}, respectively
     */
    @Override
    public int compare(T x, T y) {
        if (x == null && y == null) return 0;
        if (x == null) return -1;
        if (y == null) return 1;
        return nonNullComparator.isPresent() ? nonNullComparator.get().compare(x, y) : x.compareTo(y);
    }
}
