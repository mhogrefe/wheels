package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;

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
    public final @Nullable A a;

    /**
     * The second component of the {@code Pair}
     */
    public final @Nullable B b;

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
    public Pair(@Nullable A a, @Nullable B b) {
        this.a = a;
        this.b = b;
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
    public boolean equals(@Nullable Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Pair pair = (Pair) that;
        return (a == null ? pair.a == null : a.equals(pair.a)) &&
               (b == null ? pair.b == null : b.equals(pair.b));
    }

    /**
     * Calculates the hash code of {@code this}. The hash code is deterministic iff both values' hash codes are.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Pair}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code.
     */
    @Override
    public int hashCode() {
        int result = a == null ? 0 : a.hashCode();
        result = 31 * result + (b == null ? 0 : b.hashCode());
        return result;
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
     * @return a string representation of {@code this}.
     */
    public String toString() {
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
         * Compares two {@code Pair}s, returning 1, –1, or 0 if the answer is "greater than", "less than", or
         * "equal to", respectively.
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