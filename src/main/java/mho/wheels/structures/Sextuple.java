package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.EQ;

/**
 * An ordered Sextuple of values. Any combination of values may be null. The <tt>Sextuple</tt> is immutable iff all of
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
     * The first component of the <tt>Sextuple</tt>
     */
    public final @Nullable A a;

    /**
     * The second component of the <tt>Sextuple</tt>
     */
    public final @Nullable B b;

    /**
     * The third component of the <tt>Sextuple</tt>
     */
    public final @Nullable C c;

    /**
     * The fourth component of the <tt>Sextuple</tt>
     */
    public final @Nullable D d;

    /**
     * The fifth component of the <tt>Sextuple</tt>
     */
    public final @Nullable E e;

    /**
     * The fifth component of the <tt>Sextuple</tt>
     */
    public final @Nullable F f;

    /**
     * Constructs a <tt>Sextuple</tt> from six values.
     *
     * <ul>
     *  <li><tt>a</tt> may be anything.</li>
     *  <li><tt>b</tt> may be anything.</li>
     *  <li><tt>c</tt> may be anything.</li>
     *  <li><tt>d</tt> may be anything.</li>
     *  <li><tt>e</tt> may be anything.</li>
     *  <li><tt>f</tt> may be anything.</li>
     *  <li>Any <tt>Sextuple</tt> may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     * @param f the sixth value
     */
    public Sextuple(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    /**
     * Compares two <tt>Sextuples</tt>s, provided that <tt>A</tt>, <tt>B</tt>, <tt>C</tt>, <tt>D</tt>, <tt>E</tt>, and
     * <tt>F</tt> all implement <tt>Comparable</tt>.
     *
     * <ul>
     *  <li><tt>p</tt> must be non-null.</li>
     *  <li><tt>q</tt> must be non-null.</li>
     *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.c</tt> and <tt>q.c</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.d</tt> and <tt>q.d</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.e</tt> and <tt>q.e</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.f</tt> and <tt>q.f</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first <tt>Sextuple</tt>
     * @param q the second <tt>Sextuple</tt>
     * @param <A> the type of the first component of <tt>p</tt> and <tt>q</tt>
     * @param <B> the type of the second component of <tt>p</tt> and <tt>q</tt>
     * @param <C> the type of the third component of <tt>p</tt> and <tt>q</tt>
     * @param <D> the type of the fourth component of <tt>p</tt> and <tt>q</tt>
     * @param <E> the type of the fifth component of <tt>p</tt> and <tt>q</tt>
     * @param <F> the type of the sixth component of <tt>p</tt> and <tt>q</tt>
     * @return how <tt>p</tt> and <tt>q</tt> are ordered
     */
    public static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>,
            F extends Comparable<F>
            > Ordering compare(
            @NotNull Sextuple<A, B, C, D, E, F> p,
            @NotNull Sextuple<A, B, C, D, E, F> q
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
        return Ordering.compare(p.f, q.f);
    }

    /**
     * Determines whether <tt>this</tt> is equal to <tt>that</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Sextuple</tt>.</li>
     *  <li><tt>that</tt> may be any <tt>Object</tt>.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param that The <tt>Sextuple</tt> to be compared with <tt>this</tt>
     * @return <tt>this</tt>=<tt>that</tt>
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
     * Calculates the hash code of <tt>this</tt>. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Sextuple</tt>.</li>
     *  <li>(conjecture) The result may be any <tt>int</tt>.</li>
     * </ul>
     *
     * @return <tt>this</tt>'s hash code.
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
     * Creates a string representation of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Sextuple</tt>.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  <tt>", "</tt> at least five times.</li>
     * </ul>
     *
     * @return a string representation of <tt>this</tt>.
     */
    public String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ")";
    }

    /**
     * A comparator which compares two <tt>Sextuple</tt>s via <tt>Comparators</tt> provided for each component.
     *
     * @param <A> the type of the <tt>Sextuple</tt>s' first components
     * @param <B> the type of the <tt>Sextuple</tt>s' second components
     * @param <C> the type of the <tt>Sextuple</tt>s' third components
     * @param <D> the type of the <tt>Sextuple</tt>s' fourth components
     * @param <E> the type of the <tt>Sextuple</tt>s' fifth components
     * @param <F> the type of the <tt>Sextuple</tt>s' sixth components
     */
    public static class SextupleComparator<A, B, C, D, E, F> implements Comparator<Sextuple<A, B, C, D, E, F>> {
        /**
         * The first component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<A> aComparator;

        /**
         * The second component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<B> bComparator;

        /**
         * The third component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<C> cComparator;

        /**
         * The fourth component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<D> dComparator;

        /**
         * The fifth component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<E> eComparator;

        /**
         * The sixth component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<F> fComparator;

        /**
         * Constructs a <tt>SextupleComparator</tt> from six <tt>Comparator</tt>s.
         *
         * <ul>
         *  <li><tt>aComparator</tt> must be non-null.</li>
         *  <li><tt>bComparator</tt> must be non-null.</li>
         *  <li><tt>cComparator</tt> must be non-null.</li>
         *  <li><tt>dComparator</tt> must be non-null.</li>
         *  <li><tt>eComparator</tt> must be non-null.</li>
         *  <li><tt>fComparator</tt> must be non-null.</li>
         *  <li>Any <tt>SextupleComparator</tt> may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's <tt>Comparator</tt>
         * @param bComparator the second component's <tt>Comparator</tt>
         * @param cComparator the third component's <tt>Comparator</tt>
         * @param dComparator the fourth component's <tt>Comparator</tt>
         * @param eComparator the fifth component's <tt>Comparator</tt>
         * @param fComparator the sixth component's <tt>Comparator</tt>
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
         * Compares two <tt>Sextuple</tt>s, returning 1, &#x2212;1, or 0 if the answer is "greater than", "less than",
         * or "equal to", respectively.
         *
         * <ul>
         *  <li><tt>p</tt> must be non-null.</li>
         *  <li><tt>q</tt> must be non-null.</li>
         *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by <tt>aComparator</tt>.</li>
         *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by <tt>bComparator</tt>.</li>
         *  <li><tt>p.c</tt> and <tt>q.c</tt> must be comparable by <tt>cComparator</tt>.</li>
         *  <li><tt>p.d</tt> and <tt>q.d</tt> must be comparable by <tt>dComparator</tt>.</li>
         *  <li><tt>p.e</tt> and <tt>q.e</tt> must be comparable by <tt>eComparator</tt>.</li>
         *  <li><tt>p.f</tt> and <tt>q.f</tt> must be comparable by <tt>fComparator</tt>.</li>
         *  <li>The result is &#x2212;1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first <tt>Sextuple</tt>
         * @param q the second <tt>Sextuple</tt>
         * @return <tt>this</tt> compared to <tt>that</tt>
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
