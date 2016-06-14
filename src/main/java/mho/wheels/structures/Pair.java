package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.EQ;

/**
 * An ordered pair of values. Either value, or both, may be null. The {@code Pair} is immutable iff both of its values
 * are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 */
public final class Pair<A, B> {
    /**
     * The first component of the {@code Pair}
     */
    public final A a;

    /**
     * The second component of the {@code Pair}
     */
    public final B b;

    /**
     * Constructs a {@code Pair} from two values.
     *
     * <ul>
     *  <li>{@code a} may be anything.</li>
     *  <li>{@code b} may be anything.</li>
     *  <li>Any {@code Pair} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Converts a {@code Pair} of two values of the same type to a {@code List}.
     *
     * <ul>
     *  <li>{@code p} cannot be null.</li>
     *  <li>The result has length 2.</li>
     * </ul>
     *
     * @param p a {@code Pair}
     * @param <T> the type of both values of {@code p}
     * @return a {@code List} containing the values of {@code p}
     */
    public static <T> List<T> toList(Pair<T, T> p) {
        return Arrays.asList(p.a, p.b);
    }

    /**
     * Converts a {@code List} of two values to a {@code Pair}.
     *
     * <ul>
     *  <li>{@code xs} must have length 2.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param xs a {@code List}
     * @param <T> the type of the elements in {@code xs}
     * @return a {@code Pair} containing the values of {@code xs}
     */
    public static <T> Pair<T, T> fromList(@NotNull List<T> xs) {
        if (xs.size() != 2) {
            throw new IllegalArgumentException("xs must have length 2. Invalid xs: " + xs);
        }
        return new Pair<>(xs.get(0), xs.get(1));
    }

    /**
     * Compares two {@code Pair}s, provided that {@code A} and {@code B} both implement {@link java.lang.Comparable}.
     *
     * <ul>
     *  <li>{@code p} must be non-null.</li>
     *  <li>{@code q} must be non-null.</li>
     *  <li>{@code p.a} and {@code q.a} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.b} and {@code q.b} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first {@code Pair}
     * @param q the second {@code Pair}
     * @param <A> the type of the first component of {@code p} and {@code q}
     * @param <B> the type of the second component of {@code p} and {@code q}
     * @return how {@code p} and {@code q} are ordered
     */
    public static @NotNull <A extends Comparable<A>, B extends Comparable<B>> Ordering compare(
            @NotNull Pair<A, B> p,
            @NotNull Pair<A, B> q
    ) {
        Ordering aOrdering = Ordering.compare(p.a, q.a);
        if (aOrdering != EQ) return aOrdering;
        return Ordering.compare(p.b, q.b);
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Pair}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Pair} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Pair pair = (Pair) that;
        return (a == null ? pair.a == null : a.equals(pair.a)) &&
               (b == null ? pair.b == null : b.equals(pair.b)); ///
    }

    /**
     * Calculates the hash code of {@code this}. The hash code is deterministic iff both values' hash codes are.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Pair}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code
     */
    @Override
    public int hashCode() {
        int result = a == null ? 0 : a.hashCode();
        result = 31 * result + (b == null ? 0 : b.hashCode());
        return result;
    }

    /**
     * Creates a {@code Pair} from a {@code String}. Valid strings are of the form {@code "(" + a + ", " + b + ")"},
     * where {@code a} and {@code b} are valid {@code String}s for their types. {@code a} must not contain the
     * {@code String} {@code ", "}, because this will confuse the parser.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result may contain any {@code Pair}, or be empty.</li>
     * </ul>
     *
     * @param s a string representation of a {@code Pair}
     * @param readA a function which reads a {@code String} which represents null or a value of type {@code A}
     * @param readB a function which reads a {@code String} which represents null or a value of type {@code B}
     * @param <A> the type of the {@code Pair}'s first value
     * @param <B> the type of the {@code Pair}'s second value
     * @return the {@code Pair} represented by {@code s}, or an empty {@code Optional} if {@code s} is invalid.
     */
    public static @NotNull <A, B> Optional<Pair<A, B>> readStrict(
            @NotNull String s,
            @NotNull Function<String, NullableOptional<A>> readA,
            @NotNull Function<String, NullableOptional<B>> readB
    ) {
        if (s.length() < 2 || head(s) != '(' || last(s) != ')') return Optional.empty();
        s = middle(s);
        String[] tokens = s.split(", ");
        if (tokens.length != 2) return Optional.empty();
        NullableOptional<A> oa = readA.apply(tokens[0]);
        if (!oa.isPresent()) return Optional.empty();
        NullableOptional<B> ob = readB.apply(tokens[1]);
        if (!ob.isPresent()) return Optional.empty();
        return Optional.of(new Pair<>(oa.get(), ob.get()));
    }

    /**
     * Creates a string representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Pair}.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  {@code ", "}.</li>
     * </ul>
     *
     * @return a string representation of {@code this}
     */
    public @NotNull String toString() {
        return "(" + a + ", " + b + ")";
    }

    /**
     * A comparator which compares two {@code Pair}s via {@link java.util.Comparator}s provided for each component.
     *
     * @param <A> the type of the {@code Pair}s' first components
     * @param <B> the type of the {@code Pair}s' second components
     */
    public static class PairComparator<A, B> implements Comparator<Pair<A, B>> {
        /**
         * The first component's {@code Comparator}
         */
        private final @NotNull Comparator<A> aComparator;

        /**
         * The second component's {@code Comparator}
         */
        private final @NotNull Comparator<B> bComparator;

        /**
         * Constructs a {@code PairComparator} from two {@code Comparator}s.
         *
         * <ul>
         *  <li>{@code aComparator} must be non-null.</li>
         *  <li>{@code bComparator} must be non-null.</li>
         *  <li>Any {@code PairComparator} may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's {@code Comparator}
         * @param bComparator the second component's {@code Comparator}
         */
        public PairComparator(@NotNull Comparator<A> aComparator, @NotNull Comparator<B> bComparator) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
        }

        /**
         * Compares two {@code Pair}s lexicographically, returning 1, –1, or 0 if the answer is "greater than",
         * "less than", or "equal to", respectively.
         *
         * <ul>
         *  <li>{@code p} must be non-null.</li>
         *  <li>{@code q} must be non-null.</li>
         *  <li>{@code p.a} and {@code q.a} must be comparable by {@code aComparator}.</li>
         *  <li>{@code p.b} and {@code q.b} must be comparable by {@code bComparator}.</li>
         *  <li>The result is –1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first {@code Pair}
         * @param q the second {@code Pair}
         * @return {@code this} compared to {@code that}
         */
        @Override
        public int compare(@NotNull Pair<A, B> p, @NotNull Pair<A, B> q) {
            Ordering aOrdering = Ordering.compare(aComparator, p.a, q.a);
            if (aOrdering != EQ) return aOrdering.toInt();
            return Ordering.compare(bComparator, p.b, q.b).toInt();
        }
    }
}
