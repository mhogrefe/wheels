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
 * An ordered Septuple of values. Any combination of values may be null. The {@code Septuple} is immutable iff all of
 * its values are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 * @param <C> the type of the third value
 * @param <D> the type of the fourth value
 * @param <E> the type of the fifth value
 * @param <F> the type of the sixth value
 * @param <G> the type of the seventh value
 */
public final class Septuple<A, B, C, D, E, F, G> {
    /**
     * The first component of the {@code Septuple}
     */
    public final A a;

    /**
     * The second component of the {@code Septuple}
     */
    public final B b;

    /**
     * The third component of the {@code Septuple}
     */
    public final C c;

    /**
     * The fourth component of the {@code Septuple}
     */
    public final D d;

    /**
     * The fifth component of the {@code Septuple}
     */
    public final E e;

    /**
     * The fifth component of the {@code Septuple}
     */
    public final F f;

    /**
     * The sixth component of the {@code Septuple}
     */
    public final G g;

    /**
     * Constructs a {@code Septuple} from seven values.
     *
     * <ul>
     *  <li>{@code a} may be anything.</li>
     *  <li>{@code b} may be anything.</li>
     *  <li>{@code c} may be anything.</li>
     *  <li>{@code d} may be anything.</li>
     *  <li>{@code e} may be anything.</li>
     *  <li>{@code f} may be anything.</li>
     *  <li>{@code g} may be anything.</li>
     *  <li>Any {@code Septuple} may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     * @param f the sixth value
     * @param g the seventh value
     */
    public Septuple(A a, B b, C c, D d, E e, F f, G g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }

    /**
     * Converts a {@code Septuple} of seven values of the same type to a {@code List}.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result has length 7.</li>
     * </ul>
     *
     * @param s a {@code Septuple}
     * @param <T> the type of all values of {@code s}
     * @return a {@code List} containing the values of {@code s}
     */
    public static <T> List<T> toList(Septuple<T, T, T, T, T, T, T> s) {
        return Arrays.asList(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
    }

    /**
     * Converts a {@code List} of seven values to a {@code Septuple}.
     *
     * <ul>
     *  <li>{@code xs} must have length 7.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param xs a {@code List}
     * @param <T> the type of the elements in {@code xs}
     * @return a {@code Septuple} containing the values of {@code xs}
     */
    public static <T> Septuple<T, T, T, T, T, T, T> fromList(@NotNull List<T> xs) {
        if (xs.size() != 7) {
            throw new IllegalArgumentException("xs must have length 7. Invalid xs: " + xs);
        }
        return new Septuple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5), xs.get(6));
    }

    /**
     * Compares two {@code Septuples}s, provided that {@code A}, {@code B}, {@code C}, {@code D}, {@code E}, {@code F},
     * and {@code G} all implement {@code Comparable}.
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
     *  <li>{@code p.g} and {@code q.g} must be comparable by their type's {@code compareTo} method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first {@code Septuple}
     * @param q the second {@code Septuple}
     * @param <A> the type of the first component of {@code p} and {@code q}
     * @param <B> the type of the second component of {@code p} and {@code q}
     * @param <C> the type of the third component of {@code p} and {@code q}
     * @param <D> the type of the fourth component of {@code p} and {@code q}
     * @param <E> the type of the fifth component of {@code p} and {@code q}
     * @param <F> the type of the sixth component of {@code p} and {@code q}
     * @param <G> the type of the seventh component of {@code p} and {@code q}
     * @return how {@code p} and {@code q} are ordered
     */
    public static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>,
            F extends Comparable<F>,
            G extends Comparable<G>
            > Ordering compare(@NotNull Septuple<A, B, C, D, E, F, G> p, @NotNull Septuple<A, B, C, D, E, F, G> q) {
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
        Ordering fOrdering = Ordering.compare(p.f, q.f);
        if (fOrdering != EQ) return fOrdering;
        return Ordering.compare(p.g, q.g);
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Septuple}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Septuple} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Septuple Septuple = (Septuple) that;
        return (a == null ? Septuple.a == null : a.equals(Septuple.a)) &&
                (b == null ? Septuple.b == null : b.equals(Septuple.b)) &&
                (c == null ? Septuple.c == null : c.equals(Septuple.c)) &&
                (d == null ? Septuple.d == null : d.equals(Septuple.d)) &&
                (e == null ? Septuple.e == null : e.equals(Septuple.e)) &&
                (f == null ? Septuple.f == null : f.equals(Septuple.f)) &&
                (g == null ? Septuple.g == null : g.equals(Septuple.g));
    }

    /**
     * Calculates the hash code of {@code this}. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Septuple}.</li>
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
        result = 31 * result + (g != null ? g.hashCode() : 0);
        return result;
    }

    /**
     * Creates a {@code Septuple} from a {@code String}. Valid strings are of the form
     * {@code "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ")"}, where {@code a},
     * {@code b}, {@code c}, {@code d}, {@code e}, {@code f}, and {@code g} are valid {@code String}s for their types.
     * {@code a}, {@code b}, {@code c}, {@code d}, {@code e}, and {@code f} must not contain the {@code String}
     * {@code ", "}, because this will confuse the parser.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result may contain any {@code Septuple}, or be empty.</li>
     * </ul>
     *
     * @param s a string representation of a {@code Septuple}
     * @param readA a function which reads a {@code String} which represents null or a value of type {@code A}
     * @param readB a function which reads a {@code String} which represents null or a value of type {@code B}
     * @param readC a function which reads a {@code String} which represents null or a value of type {@code C}
     * @param readD a function which reads a {@code String} which represents null or a value of type {@code D}
     * @param readE a function which reads a {@code String} which represents null or a value of type {@code E}
     * @param readF a function which reads a {@code String} which represents null or a value of type {@code F}
     * @param readG a function which reads a {@code String} which represents null or a value of type {@code G}
     * @param <A> the type of the {@code Septuple}'s first value
     * @param <B> the type of the {@code Septuple}'s second value
     * @param <C> the type of the {@code Septuple}'s third value
     * @param <D> the type of the {@code Septuple}'s fourth value
     * @param <E> the type of the {@code Septuple}'s fifth value
     * @param <F> the type of the {@code Septuple}'s sixth value
     * @param <G> the type of the {@code Septuple}'s seventh value
     * @return the {@code Septuple} represented by {@code s}, or an empty {@code Optional} if {@code s} is invalid
     */
    public static @NotNull <A, B, C, D, E, F, G> Optional<Septuple<A, B, C, D, E, F, G>> readStrict(
            @NotNull String s,
            @NotNull Function<String, NullableOptional<A>> readA,
            @NotNull Function<String, NullableOptional<B>> readB,
            @NotNull Function<String, NullableOptional<C>> readC,
            @NotNull Function<String, NullableOptional<D>> readD,
            @NotNull Function<String, NullableOptional<E>> readE,
            @NotNull Function<String, NullableOptional<F>> readF,
            @NotNull Function<String, NullableOptional<G>> readG
    ) {
        if (s.length() < 2 || head(s) != '(' || last(s) != ')') return Optional.empty();
        s = middle(s);
        A a = null;
        B b = null;
        C c = null;
        D d = null;
        E e = null;
        F f = null;
        G g = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String token : s.split(", ")) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(token);
            switch (i) {
                case 0:
                    NullableOptional<A> oa = readA.apply(sb.toString());
                    if (oa.isPresent()) {
                        a = oa.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 1:
                    NullableOptional<B> ob = readB.apply(sb.toString());
                    if (ob.isPresent()) {
                        b = ob.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 2:
                    NullableOptional<C> oc = readC.apply(sb.toString());
                    if (oc.isPresent()) {
                        c = oc.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 3:
                    NullableOptional<D> od = readD.apply(sb.toString());
                    if (od.isPresent()) {
                        d = od.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 4:
                    NullableOptional<E> oe = readE.apply(sb.toString());
                    if (oe.isPresent()) {
                        e = oe.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 5:
                    NullableOptional<F> of = readF.apply(sb.toString());
                    if (of.isPresent()) {
                        f = of.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                case 6:
                    NullableOptional<G> og = readG.apply(sb.toString());
                    if (og.isPresent()) {
                        g = og.get();
                        i++;
                        sb = new StringBuilder();
                    }
                    break;
                default:
                    return Optional.empty();
            }
        }

        if (i != 7) return Optional.empty();
        return Optional.of(new Septuple<>(a, b, c, d, e, f, g));
    }

    /**
     * Creates a string representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Septuple}.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  {@code ", "} at least six times.</li>
     * </ul>
     *
     * @return a string representation of {@code this}.
     */
    public @NotNull String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ")";
    }

    /**
     * A comparator which compares two {@code Septuple}s via {@code Comparators} provided for each component.
     *
     * @param <A> the type of the {@code Septuple}s' first components
     * @param <B> the type of the {@code Septuple}s' second components
     * @param <C> the type of the {@code Septuple}s' third components
     * @param <D> the type of the {@code Septuple}s' fourth components
     * @param <E> the type of the {@code Septuple}s' fifth components
     * @param <F> the type of the {@code Septuple}s' sixth components
     * @param <G> the type of the {@code Septuple}s' seventh components
     */
    public static class SeptupleComparator<A, B, C, D, E, F, G> implements Comparator<Septuple<A, B, C, D, E, F, G>> {
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
         * The seventh component's {@code Comparator}
         */
        private final @NotNull Comparator<G> gComparator;

        /**
         * Constructs a {@code SeptupleComparator} from seven {@code Comparator}s.
         *
         * <ul>
         *  <li>{@code aComparator} must be non-null.</li>
         *  <li>{@code bComparator} must be non-null.</li>
         *  <li>{@code cComparator} must be non-null.</li>
         *  <li>{@code dComparator} must be non-null.</li>
         *  <li>{@code eComparator} must be non-null.</li>
         *  <li>{@code fComparator} must be non-null.</li>
         *  <li>{@code gComparator} must be non-null.</li>
         *  <li>Any {@code SeptupleComparator} may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's {@code Comparator}
         * @param bComparator the second component's {@code Comparator}
         * @param cComparator the third component's {@code Comparator}
         * @param dComparator the fourth component's {@code Comparator}
         * @param eComparator the fifth component's {@code Comparator}
         * @param fComparator the sixth component's {@code Comparator}
         * @param gComparator the seventh component's {@code Comparator}
         */
        public SeptupleComparator(
                @NotNull Comparator<A> aComparator,
                @NotNull Comparator<B> bComparator,
                @NotNull Comparator<C> cComparator,
                @NotNull Comparator<D> dComparator,
                @NotNull Comparator<E> eComparator,
                @NotNull Comparator<F> fComparator,
                @NotNull Comparator<G> gComparator
        ) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
            this.cComparator = cComparator;
            this.dComparator = dComparator;
            this.eComparator = eComparator;
            this.fComparator = fComparator;
            this.gComparator = gComparator;
        }

        /**
         * Compares two {@code Septuple}s lexicographically, returning 1, –1, or 0 if the answer is "greater than",
         * "less than", or "equal to", respectively.
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
         *  <li>{@code p.g} and {@code q.g} must be comparable by {@code gComparator}.</li>
         *  <li>The result is –1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first {@code Septuple}
         * @param q the second {@code Septuple}
         * @return {@code this} compared to {@code that}
         */
        @Override
        public int compare(@NotNull Septuple<A, B, C, D, E, F, G> p, @NotNull Septuple<A, B, C, D, E, F, G> q) {
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
            Ordering fOrdering = Ordering.compare(fComparator, p.f, q.f);
            if (fOrdering != EQ) return fOrdering.toInt();
            return Ordering.compare(gComparator, p.g, q.g).toInt();
        }
    }
}
