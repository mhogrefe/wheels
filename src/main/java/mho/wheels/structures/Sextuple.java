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
 * An ordered Sextuple of values. Any combination of values may be null. The {@code Sextuple} is immutable iff all of
 * its values are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 * @param <C> the type of the third value
 * @param <D> the type of the fourth value
 * @param <E> the type of the fifth value
 * @param <F> the type of the sixth value
 */
public final class Sextuple<A, B, C, D, E, F> {
    /**
     * The first component of the {@code Sextuple}
     */
    public final A a;

    /**
     * The second component of the {@code Sextuple}
     */
    public final B b;

    /**
     * The third component of the {@code Sextuple}
     */
    public final C c;

    /**
     * The fourth component of the {@code Sextuple}
     */
    public final D d;

    /**
     * The fifth component of the {@code Sextuple}
     */
    public final E e;

    /**
     * The fifth component of the {@code Sextuple}
     */
    public final F f;

    /**
     * Constructs a {@code Sextuple} from six values.
     *
     * <ul>
     *  <li>{@code a} may be anything.</li>
     *  <li>{@code b} may be anything.</li>
     *  <li>{@code c} may be anything.</li>
     *  <li>{@code d} may be anything.</li>
     *  <li>{@code e} may be anything.</li>
     *  <li>{@code f} may be anything.</li>
     *  <li>Any {@code Sextuple} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     * @param f the sixth value
     */
    public Sextuple(A a, B b, C c, D d, E e, F f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public static <T> List<T> toList(Sextuple<T, T, T, T, T, T> s) {
        return Arrays.asList(s.a, s.b, s.c, s.d, s.e, s.f);
    }

    public static <T> Sextuple<T, T, T, T, T, T> fromList(@NotNull List<T> xs) {
        if (xs.size() != 6) {
            throw new IllegalArgumentException();
        }
        return new Sextuple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5));
    }

    /**
     * Compares two {@code Sextuples}s, provided that {@code A}, {@code B}, {@code C}, {@code D}, {@code E}, and
     * {@code F} all implement {@code Comparable}.
     *
     * <ul>
     *  <li>{@code p} must be non-null.</li>
     *  <li>{@code q} must be non-null.</li>
     *  <li>{@code p.a} and {@code q.a} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.b} and {@code q.b} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.c} and {@code q.c} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.d} and {@code q.d} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.e} and {@code q.e} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>{@code p.f} and {@code q.f} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first {@code Sextuple}
     * @param q the second {@code Sextuple}
     * @param <A> the type of the first component of {@code p} and {@code q}
     * @param <B> the type of the second component of {@code p} and {@code q}
     * @param <C> the type of the third component of {@code p} and {@code q}
     * @param <D> the type of the fourth component of {@code p} and {@code q}
     * @param <E> the type of the fifth component of {@code p} and {@code q}
     * @param <F> the type of the sixth component of {@code p} and {@code q}
     * @return how {@code p} and {@code q} are ordered
     */
    public static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>,
            F extends Comparable<F>
            > Ordering compare(@NotNull Sextuple<A, B, C, D, E, F> p, @NotNull Sextuple<A, B, C, D, E, F> q) {
        Ordering aOrdering = Ordering.compare(p.a, q.a);
        if (aOrdering != EQ) return aOrdering;
        Ordering bOrdering = Ordering.compare(p.b, q.b);
        if (bOrdering != EQ) return bOrdering;
        Ordering cOrdering = Ordering.compare(p.c, q.c);
        if (cOrdering != EQ) return cOrdering;
        Ordering dOrdering = Ordering.compare(p.d, q.d);
        if (dOrdering != EQ) return dOrdering;
        Ordering eOrdering = Ordering.compare(p.e, q.e);
        if (eOrdering != EQ) return eOrdering;
        return Ordering.compare(p.f, q.f);
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Sextuple}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Sextuple} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Sextuple Sextuple = (Sextuple) that;
        return (a == null ? Sextuple.a == null : a.equals(Sextuple.a)) &&
                (b == null ? Sextuple.b == null : b.equals(Sextuple.b)) &&
                (c == null ? Sextuple.c == null : c.equals(Sextuple.c)) &&
                (d == null ? Sextuple.d == null : d.equals(Sextuple.d)) &&
                (e == null ? Sextuple.e == null : e.equals(Sextuple.e)) &&
                (f == null ? Sextuple.f == null : f.equals(Sextuple.f));
    }

    /**
     * Calculates the hash code of {@code this}. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Sextuple}.</li>
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
        result = 31 * result + (f != null ? f.hashCode() : 0);
        return result;
    }

    /**
     * Creates a {@code Sextuple} from a {@code String}. Valid strings are of the form
     * {@code "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ")"}, where {@code a}, {@code b},
     * {@code c}, {@code d}, {@code e}, and {@code f} are valid {@code String}s for their types. {@code a}, {@code b},
     * {@code c}, {@code d}, and {@code e} must not contain the {@code String} {@code ", "}, because this will confuse
     * the parser.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result may contain any {@code Sextuple}, or be empty.</li>
     * </ul>
     *
     * @param s a string representation of a {@code Sextuple}
     * @param readA a function which reads a {@code String} which represents null or a value of type {@code A}
     * @param readB a function which reads a {@code String} which represents null or a value of type {@code B}
     * @param readC a function which reads a {@code String} which represents null or a value of type {@code C}
     * @param readD a function which reads a {@code String} which represents null or a value of type {@code D}
     * @param readE a function which reads a {@code String} which represents null or a value of type {@code E}
     * @param readF a function which reads a {@code String} which represents null or a value of type {@code F}
     * @param <A> the type of the {@code Sextuple}'s first value
     * @param <B> the type of the {@code Sextuple}'s second value
     * @param <C> the type of the {@code Sextuple}'s third value
     * @param <D> the type of the {@code Sextuple}'s fourth value
     * @param <E> the type of the {@code Sextuple}'s fifth value
     * @param <F> the type of the {@code Sextuple}'s sixth value
     * @return the {@code Sextuple} represented by {@code s}, or an empty {@code Optional} if {@code s} is invalid
     */
    public static @NotNull <A, B, C, D, E, F> Optional<Sextuple<A, B, C, D, E, F>> readStrict(
            @NotNull String s,
            @NotNull Function<String, NullableOptional<A>> readA,
            @NotNull Function<String, NullableOptional<B>> readB,
            @NotNull Function<String, NullableOptional<C>> readC,
            @NotNull Function<String, NullableOptional<D>> readD,
            @NotNull Function<String, NullableOptional<E>> readE,
            @NotNull Function<String, NullableOptional<F>> readF
    ) {
        if (s.length() < 2 || head(s) != '(' || last(s) != ')') return Optional.empty();
        s = tail(init(s));
        String[] tokens = s.split(", ");
        if (tokens.length != 6) return Optional.empty();
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
        NullableOptional<F> of = readF.apply(tokens[5]);
        if (!of.isPresent()) return Optional.empty();
        return Optional.of(new Sextuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get(), of.get()));
    }

    /**
     * Creates a string representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Sextuple}.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  {@code ", "} at least five times.</li>
     * </ul>
     *
     * @return a string representation of {@code this}.
     */
    public @NotNull String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ")";
    }

    /**
     * A comparator which compares two {@code Sextuple}s via {@code Comparators} provided for each component.
     *
     * @param <A> the type of the {@code Sextuple}s' first components
     * @param <B> the type of the {@code Sextuple}s' second components
     * @param <C> the type of the {@code Sextuple}s' third components
     * @param <D> the type of the {@code Sextuple}s' fourth components
     * @param <E> the type of the {@code Sextuple}s' fifth components
     * @param <F> the type of the {@code Sextuple}s' sixth components
     */
    public static class SextupleComparator<A, B, C, D, E, F> implements Comparator<Sextuple<A, B, C, D, E, F>> {
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
         * The sixth component's {@code Comparator}
         */
        private final @NotNull Comparator<F> fComparator;

        /**
         * Constructs a {@code SextupleComparator} from six {@code Comparator}s.
         *
         * <ul>
         *  <li>{@code aComparator} must be non-null.</li>
         *  <li>{@code bComparator} must be non-null.</li>
         *  <li>{@code cComparator} must be non-null.</li>
         *  <li>{@code dComparator} must be non-null.</li>
         *  <li>{@code eComparator} must be non-null.</li>
         *  <li>{@code fComparator} must be non-null.</li>
         *  <li>Any {@code SextupleComparator} may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's {@code Comparator}
         * @param bComparator the second component's {@code Comparator}
         * @param cComparator the third component's {@code Comparator}
         * @param dComparator the fourth component's {@code Comparator}
         * @param eComparator the fifth component's {@code Comparator}
         * @param fComparator the sixth component's {@code Comparator}
         */
        public SextupleComparator(
                @NotNull Comparator<A> aComparator,
                @NotNull Comparator<B> bComparator,
                @NotNull Comparator<C> cComparator,
                @NotNull Comparator<D> dComparator,
                @NotNull Comparator<E> eComparator,
                @NotNull Comparator<F> fComparator
        ) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
            this.cComparator = cComparator;
            this.dComparator = dComparator;
            this.eComparator = eComparator;
            this.fComparator = fComparator;
        }

        /**
         * Compares two {@code Sextuple}s, returning 1, –1, or 0 if the answer is "greater than", "less than", or
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
         *  <li>{@code p.f} and {@code q.f} must be comparable by {@code fComparator}.</li>
         *  <li>The result is –1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first {@code Sextuple}
         * @param q the second {@code Sextuple}
         * @return {@code this} compared to {@code that}
         */
        @Override
        public int compare(@NotNull Sextuple<A, B, C, D, E, F> p, @NotNull Sextuple<A, B, C, D, E, F> q) {
            Ordering aOrdering = Ordering.compare(aComparator, p.a, q.a);
            if (aOrdering != EQ) return aOrdering.toInt();
            Ordering bOrdering = Ordering.compare(bComparator, p.b, q.b);
            if (bOrdering != EQ) return bOrdering.toInt();
            Ordering cOrdering = Ordering.compare(cComparator, p.c, q.c);
            if (cOrdering != EQ) return cOrdering.toInt();
            Ordering dOrdering = Ordering.compare(dComparator, p.d, q.d);
            if (dOrdering != EQ) return dOrdering.toInt();
            Ordering eOrdering = Ordering.compare(eComparator, p.e, q.e);
            if (eOrdering != EQ) return eOrdering.toInt();
            return Ordering.compare(fComparator, p.f, q.f).toInt();
        }
    }
}
