package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
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
    public final @Nullable A a;

    /**
     * The second component of the {@code Septuple}
     */
    public final @Nullable B b;

    /**
     * The third component of the {@code Septuple}
     */
    public final @Nullable C c;

    /**
     * The fourth component of the {@code Septuple}
     */
    public final @Nullable D d;

    /**
     * The fifth component of the {@code Septuple}
     */
    public final @Nullable E e;

    /**
     * The fifth component of the {@code Septuple}
     */
    public final @Nullable F f;

    /**
     * The sixth component of the {@code Septuple}
     */
    public final @Nullable G g;

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
    public Septuple(
            @Nullable A a,
            @Nullable B b,
            @Nullable C c,
            @Nullable D d,
            @Nullable E e,
            @Nullable F f,
            @Nullable G g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
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
            > Ordering compare(
            @NotNull Septuple<A, B, C, D, E, F, G> p,
            @NotNull Septuple<A, B, C, D, E, F, G> q
    ) {
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
     * If the {@code String} is invalid, the method returns Optional.empty() without throwing an exception; this aids
     * composability.
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
    public static @NotNull <A, B, C, D, E, F, G> Optional<Septuple<A, B, C, D, E, F, G>> read(
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
        s = tail(init(s));
        String[] tokens = s.split(", ");
        if (tokens.length != 7) return Optional.empty();
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
        NullableOptional<G> og = readG.apply(tokens[6]);
        if (!og.isPresent()) return Optional.empty();
        return Optional.of(new Septuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get(), of.get(), og.get()));
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
         * Compares two {@code Septuple}s, returning 1, –1, or 0 if the answer is "greater than", "less than", or
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
