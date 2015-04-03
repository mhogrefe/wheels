package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.EQ;

/**
 * Compares two {@code String}s via shortlex order. First the lengths of the {@code Iterable}s are compared. If one
 * is shorter, it is smaller than the other in this ordering. If both are equal in length, the {@code Comparator} then
 * looks at their {@code Character}s in parallel, left-to-right. The first pair of {@code Character}s which aren't
 * equal determine the ordering.
 */
public class StringShortlexComparator implements Comparator<String> {
    /**
     * The {@code Comparator} used to compare {@code Character}s. It can be null, in which case the natural ordering of
     * {@code Character}s is used.
     */
    private final @Nullable Comparator<Character> characterComparator;

    /**
     * Constructs a {@code StringShortlexComparator} which uses the natural ordering to compare {@code Character}s.
     */
    public StringShortlexComparator() {
        this.characterComparator = null;
    }

    /**
     * Constructs a {@code StringShortLexComparator} which uses a provided {@code Comparator} to compare
     * {@code Character}s.
     *
     * <ul>
     *  <li>{@code characterComparator} must obey its contract. See {@link java.util.Comparator}.</li>
     * </ul>
     *
     * @param characterComparator the {@code Comparator} used to compare {@code Character}s
     */
    @SuppressWarnings("NullableProblems")
    public StringShortlexComparator(@NotNull Comparator<Character> characterComparator) {
        this.characterComparator = characterComparator;
    }

    /**
     * Compares two {@code String}s via shortlex order.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code ys} cannot be null.</li>
     *  <li>{@code xs} and {@code ys} must contain valid arguments for either {@link java.lang.Character#compareTo} (if
     *  {@code characterComparator} is null) or {@code characterComparator}'s {@code compare} method.</li>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @param s the first {@code String}
     * @param t the second {@code String}
     * @return –1, 0, 1, depending on whether {@code xs} is less than, equal to, or greater than {@code ys},
     * respectively
     */
    @Override
    public int compare(@NotNull String s, @NotNull String t) {
        if (s.length() > t.length()) return 1;
        if (s.length() < t.length()) return -1;
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            Ordering characterOrdering;
            if (characterComparator == null) {
                characterOrdering = Ordering.compare(sc, tc);
            } else {
                characterOrdering = Ordering.compare(characterComparator, sc, tc);
            }
            if (characterOrdering != EQ) return characterOrdering.toInt();
        }
        return 0;
    }
}
