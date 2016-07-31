package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

import static mho.wheels.ordering.Ordering.EQ;

/**
 * Compares two {@code Iterable}s lexicographically (by "dictionary order"). This {@code Comparator} looks at the
 * elements of both {@code Iterable}s in parallel, left-to-right. The first pair of elements which aren't equal
 * determine the ordering. If one of the {@code Iterator}s ends during this process (that is, the corresponding
 * {@code Iterable} is a prefix of the {@code Iterable} it's being compared to), that {@code Iterable} is considered
 * smaller.
 *
 * @param <T> the type of the {@code Iterable}s' values
 */
public final class LexComparator<T extends Comparable<T>> implements Comparator<Iterable<T>> {
    /**
     * The {@code Comparator} used to compare values of type {@code T}. It can be null, in which case the natural
     * ordering of {@code T} is used.
     */
    private final @NotNull Optional<Comparator<T>> elementComparator;

    /**
     * Constructs a {@code LexComparator} which uses the natural ordering to compare elements.
     */
    public LexComparator() {
        this.elementComparator = Optional.empty();
    }

    /**
     * Constructs a {@code LexComparator} which uses a provided {@code Comparator} to compare elements.
     *
     * <ul>
     *  <li>{@code comparator} must obey its contract. See {@link java.util.Comparator}.</li>
     * </ul>
     *
     * @param elementComparator the {@code Comparator} used to compare values of type {@code T}
     */
    @SuppressWarnings("NullableProblems")
    public LexComparator(@NotNull Comparator<T> elementComparator) {
        this.elementComparator = Optional.of(elementComparator);
    }

    /**
     * Compares two {@code Iterable}s lexicographically. If the {@code Iterable}s are equal and infinite, this method
     * will hang.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code ys} cannot be null.</li>
     *  <li>{@code xs} and {@code ys} must contain valid arguments for either {@code T}'s {@code compareTo} method (if
     *  {@code elementComparator} is null) or {@code elementComparator}'s {@code compare} method.</li>
     *  <li>{@code xs} and {@code ys} cannot be equal and infinite.</li>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param xs the first {@code Iterable}
     * @param ys the second {@code Iterable}
     * @return –1, 0, 1, depending on whether {@code xs} is less than, equal to, or greater than {@code ys},
     * respectively
     */
    @Override
    public int compare(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        if (xs == ys) return 0;
        Iterator<T> xsi = xs.iterator();
        Iterator<T> ysi = ys.iterator();
        while (xsi.hasNext()) {
            if (!ysi.hasNext()) return 1;
            int elementCompare;
            if (elementComparator.isPresent()) {
                elementCompare = elementComparator.get().compare(xsi.next(), ysi.next());
            } else {
                elementCompare = xsi.next().compareTo(ysi.next());
            }
            if (elementCompare != 0) {
                return elementCompare;
            }
        }
        return ysi.hasNext() ? -1 : 0;
    }
}
