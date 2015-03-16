package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.EQ;

/**
 * An ordered Quintuple of values. Any combination of values may be null. The {@code Quintuple} is immutable iff all of
 * its values are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 * @param <C> the type of the third value
 * @param <D> the type of the fourth value
 * @param <E> the type of the fifth value
 */
public final class Quintuple<A, B, C, D, E> {
    /**
     * The first component of the {@code Quintuple}
     */
    public final @Nullable A a;

    /**
     * The second component of the {@code Quintuple}
     */
    public final @Nullable B b;

    /**
     * The third component of the {@code Quintuple}
     */
    public final @Nullable C c;

    /**
     * The fourth component of the {@code Quintuple}
     */
    public final @Nullable D d;

    /**
     * The fifth component of the {@code Quintuple}
     */
    public final @Nullable E e;

    /**
     * Constructs a {@code Quintuple} from five values.
     *
     * <ul>
     *  <li>{@code a} may be anything.</li>
     *  <li>{@code b} may be anything.</li>
     *  <li>{@code c} may be anything.</li>
     *  <li>{@code d} may be anything.</li>
     *  <li>{@code e} may be anything.</li>
     *  <li>Any {@code Quintuple} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     */
    public Quintuple(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public static <T> List<T> toList(Quintuple<T, T, T, T, T> q) {
        return Arrays.asList(q.a, q.b, q.c, q.d, q.e);
    }

    /**
     * Compares two {@code Quintuples}s, provided that {@code A}, {@code B}, {@code C}, {@code D}, and {@code E} all
     * implement {@code Comparable}.
     *
     * <ul>
     *  <li>{@code p} must be non-null.</li>
     *  <li>{@code q} must be non-null.</li>
     *  <li>{@code p.a} and {@code q.a} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.b} and {@code q.b} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.c} and {@code q.c} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.d} and {@code q.d} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.e} and {@code q.e} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first {@code Quintuple}
     * @param q the second {@code Quintuple}
     * @param <A> the type of the first component of {@code p} and {@code q}
     * @param <B> the type of the second component of {@code p} and {@code q}
     * @param <C> the type of the third component of {@code p} and {@code q}
     * @param <D> the type of the fourth component of {@code p} and {@code q}
     * @param <E> the type of the fifth component of {@code p} and {@code q}
     * @return how {@code p} and {@code q} are ordered
     */
    public static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>
            > Ordering compare(
            @NotNull Quintuple<A, B, C, D, E> p,
            @NotNull Quintuple<A, B, C, D, E> q
    ) {
        Ordering aOrdering = Ordering.compare(p.a, q.a);
        if (aOrdering != EQ) return aOrdering;
        Ordering bOrdering = Ordering.compare(p.b, q.b);
        if (bOrdering != EQ) return bOrdering;
        Ordering cOrdering = Ordering.compare(p.c, q.c);
        if (cOrdering != EQ) return cOrdering;
        Ordering dOrdering = Ordering.compare(p.d, q.d);
        if (dOrdering != EQ) return dOrdering;
        return Ordering.compare(p.e, q.e);
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Quintuple}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Quintuple} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Quintuple Quintuple = (Quintuple) that;
        return (a == null ? Quintuple.a == null : a.equals(Quintuple.a)) &&
                (b == null ? Quintuple.b == null : b.equals(Quintuple.b)) &&
                (c == null ? Quintuple.c == null : c.equals(Quintuple.c)) &&
                (d == null ? Quintuple.d == null : d.equals(Quintuple.d)) &&
                (e == null ? Quintuple.e == null : e.equals(Quintuple.e));
    }

    /**
     * Calculates the hash code of {@code this}. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Quintuple}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code
     */
    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        result = 31 * result + (e != null ? e.hashCode() : 0);
        return result;
    }

    /**
     * Creates a {@code Quintuple} from a {@code String}. Valid strings are of the form
     * {@code "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ")"}, where {@code a}, {@code b}, {@code c},
     * {@code d}, and {@code e} are valid {@code String}s for their types. {@code a}, {@code b}, {@code c}, and
     * {@code d} must not contain the {@code String} {@code ", "}, because this will confuse the parser. If the
     * {@code String} is invalid, the method returns {@code Optional.empty()} without throwing an exception; this aids
     * composability.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result may contain any {@code Quintuple}, or be empty.</li>
     * </ul>
     *
     * @param s a string representation of a {@code Quintuple}
     * @param readA a function which reads a {@code String} which represents null or a value of type {@code A}
     * @param readB a function which reads a {@code String} which represents null or a value of type {@code B}
     * @param readC a function which reads a {@code String} which represents null or a value of type {@code C}
     * @param readD a function which reads a {@code String} which represents null or a value of type {@code D}
     * @param readE a function which reads a {@code String} which represents null or a value of type {@code E}
     * @param <A> the type of the {@code Quintuple}'s first value
     * @param <B> the type of the {@code Quintuple}'s second value
     * @param <C> the type of the {@code Quintuple}'s third value
     * @param <D> the type of the {@code Quintuple}'s fourth value
     * @param <E> the type of the {@code Quintuple}'s fifth value
     * @return the {@code Quintuple} represented by {@code s}, or an empty {@code Optional} if {@code s} is invalid
     */
    public static @NotNull <A, B, C, D, E> Optional<Quintuple<A, B, C, D, E>> read(
            @NotNull String s,
            @NotNull Function<String, NullableOptional<A>> readA,
            @NotNull Function<String, NullableOptional<B>> readB,
            @NotNull Function<String, NullableOptional<C>> readC,
            @NotNull Function<String, NullableOptional<D>> readD,
            @NotNull Function<String, NullableOptional<E>> readE
    ) {
        if (s.length() < 2 || head(s) != '(' || last(s) != ')') return Optional.empty();
        s = tail(init(s));
        String[] tokens = s.split(", ");
        if (tokens.length != 5) return Optional.empty();
        NullableOptional<A> oa = readA.apply(tokens[0]);
        if (!oa.isPresent()) return Optional.empty();
        NullableOptional<B> ob = readB.apply(tokens[1]);
        if (!ob.isPresent()) return Optional.empty();
        NullableOptional<C> oc = readC.apply(tokens[2]);
        if (!oc.isPresent()) return Optional.empty();
        NullableOptional<D> od = readD.apply(tokens[3]);
        if (!od.isPresent()) return Optional.empty();
        NullableOptional<E> oe = readE.apply(tokens[4]);
        if (!oe.isPresent()) return Optional.empty();
        return Optional.of(new Quintuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get()));
    }

    /**
     * Creates a string representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Quintuple}.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  {@code ", "} at least four times.</li>
     * </ul>
     *
     * @return a string representation of {@code this}.
     */
    public @NotNull String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ")";
    }

    /**
     * A comparator which compares two {@code Quintuple}s via {@code Comparators} provided for each component.
     *
     * @param <A> the type of the {@code Quintuple}s' first components
     * @param <B> the type of the {@code Quintuple}s' second components
     * @param <C> the type of the {@code Quintuple}s' third components
     * @param <D> the type of the {@code Quintuple}s' fourth components
     * @param <E> the type of the {@code Quintuple}s' fifth components
     */
    public static class QuintupleComparator<A, B, C, D, E> implements Comparator<Quintuple<A, B, C, D, E>> {
        /**
         * The first component's {@code Comparator}
         */
        private final @NotNull Comparator<A> aComparator;

        /**
         * The second component's {@code Comparator}
         */
        private final @NotNull Comparator<B> bComparator;

        /**
         * The third component's {@code Comparator}
         */
        private final @NotNull Comparator<C> cComparator;

        /**
         * The fourth component's {@code Comparator}
         */
        private final @NotNull Comparator<D> dComparator;

        /**
         * The fifth component's {@code Comparator}
         */
        private final @NotNull Comparator<E> eComparator;

        /**
         * Constructs a {@code QuintupleComparator} from five {@code Comparator}s.
         *
         * <ul>
         *  <li>{@code aComparator} must be non-null.</li>
         *  <li>{@code bComparator} must be non-null.</li>
         *  <li>{@code cComparator} must be non-null.</li>
         *  <li>{@code dComparator} must be non-null.</li>
         *  <li>{@code eComparator} must be non-null.</li>
         *  <li>Any {@code QuintupleComparator} may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's {@code Comparator}
         * @param bComparator the second component's {@code Comparator}
         * @param cComparator the third component's {@code Comparator}
         * @param dComparator the fourth component's {@code Comparator}
         * @param eComparator the fifth component's {@code Comparator}
         */
        public QuintupleComparator(
                @NotNull Comparator<A> aComparator,
                @NotNull Comparator<B> bComparator,
                @NotNull Comparator<C> cComparator,
                @NotNull Comparator<D> dComparator,
                @NotNull Comparator<E> eComparator
        ) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
            this.cComparator = cComparator;
            this.dComparator = dComparator;
            this.eComparator = eComparator;
        }

        /**
         * Compares two {@code Quintuple}s, returning 1, –1, or 0 if the answer is "greater than", "less than", or
         * "equal to", respectively.
         *
         * <ul>
         *  <li>{@code p} must be non-null.</li>
         *  <li>{@code q} must be non-null.</li>
         *  <li>{@code p.a} and {@code q.a} must be comparable by {@code aComparator}.</li>
         *  <li>{@code p.b} and {@code q.b} must be comparable by {@code bComparator}.</li>
         *  <li>{@code p.c} and {@code q.c} must be comparable by {@code cComparator}.</li>
         *  <li>{@code p.d} and {@code q.d} must be comparable by {@code dComparator}.</li>
         *  <li>{@code p.e} and {@code q.e} must be comparable by {@code eComparator}.</li>
         *  <li>The result is –1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first {@code Quintuple}
         * @param q the second {@code Quintuple}
         * @return {@code this} compared to {@code that}
         */
        @Override
        public int compare(@NotNull Quintuple<A, B, C, D, E> p, @NotNull Quintuple<A, B, C, D, E> q) {
            Ordering aOrdering = Ordering.compare(aComparator, p.a, q.a);
            if (aOrdering != EQ) return aOrdering.toInt();
            Ordering bOrdering = Ordering.compare(bComparator, p.b, q.b);
            if (bOrdering != EQ) return bOrdering.toInt();
            Ordering cOrdering = Ordering.compare(cComparator, p.c, q.c);
            if (cOrdering != EQ) return cOrdering.toInt();
            Ordering dOrdering = Ordering.compare(dComparator, p.d, q.d);
            if (dOrdering != EQ) return dOrdering.toInt();
            return Ordering.compare(eComparator, p.e, q.e).toInt();
        }
    }
}
