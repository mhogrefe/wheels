package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

import static mho.wheels.ordering.Ordering.EQ;

/**
 * Compares two {@code String}s lexicographically (by "dictionary order"). This {@code Comparator} looks at the
 * {@code Character}s of both {@code String}s in parallel, left-to-right. The first pair of {@code Character}s which
 * aren't equal determine the ordering. If one of the {@code Iterator}s ends during this process (that is, the
 * corresponding {@code String} is a prefix of the {@code String} it's being compared to), that {@code String} is
 * considered smaller. If {@code characterComparator} is the usual comparator on {@code Character}s, this comparator is
 * the usual comparator on {@code String}s.
 */
public class StringLexComparator implements Comparator<String> {
    /**
     * The {@code Comparator} used to compare {@code Character}s. It can be null, in which case the natural ordering of
     * {@code Character}s is used.
     */
    private final @NotNull Optional<Comparator<Character>> characterComparator;

    /**
     * Constructs a {@code StringShortlexComparator} which uses the natural ordering to compare {@code Character}s.
     */
    public StringLexComparator() {
        this.characterComparator = Optional.empty();
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
    public StringLexComparator(@NotNull Comparator<Character> characterComparator) {
        this.characterComparator = Optional.of(characterComparator);
    }

    /**
     * Compares two {@code String}s lexicographically.
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
        if (!characterComparator.isPresent()) {
            return s.compareTo(t);
        }
        //noinspection StringEquality
        if (s == t) return 0;
        int sLength = s.length();
        int tLength = t.length();
        int minLength = Math.min(sLength, tLength);
        for (int i = 0; i < minLength; i++) {
            int characterCompare = characterComparator.get().compare(s.charAt(i), t.charAt(i));
            if (characterCompare != 0) {
                return characterCompare;
            }
        }
        return Integer.compare(sLength, tLength);
    }
}
