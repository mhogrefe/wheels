package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.EQ;

/**
 * An ordered triple of values. Any combination of values may be null. The <tt>Triple</tt> is immutable iff all of its
 * values are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 * @param <C> the type of the third value
 */
public final class Triple<A, B, C> {
    /**
     * The first component of the <tt>Triple</tt>
     */
    public final @Nullable A a;

    /**
     * The second component of the <tt>Triple</tt>
     */
    public final @Nullable B b;

    /**
     * The third component of the <tt>Triple</tt>
     */
    public final @Nullable C c;

    /**
     * Constructs a <tt>Triple</tt> from three values.
     *
     * <ul>
     *  <li><tt>a</tt> may be anything.</li>
     *  <li><tt>b</tt> may be anything.</li>
     *  <li><tt>c</tt> may be anything.</li>
     *  <li>Any <tt>Triple</tt> may be constructed with this constructor.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     */
    public Triple(@Nullable A a, @Nullable B b, @Nullable C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Compares two <tt>Triple</tt>s, provided that <tt>A</tt>, <tt>B</tt>, and <tt>C</tt> all implement
     * <tt>Comparable</tt>.
     *
     * <ul>
     *  <li><tt>p</tt> must be non-null.</li>
     *  <li><tt>q</tt> must be non-null.</li>
     *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.c</tt> and <tt>q.c</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first <tt>Triple</tt>
     * @param q the second <tt>Triple</tt>
     * @param <A> the type of the first component of <tt>p</tt> and <tt>q</tt>
     * @param <B> the type of the second component of <tt>p</tt> and <tt>q</tt>
     * @param <C> the type of the third component of <tt>p</tt> and <tt>q</tt>
     * @return how <tt>p</tt> and <tt>q</tt> are ordered
     */
    public static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>
            > Ordering compare(
            @NotNull Triple<A, B, C> p,
            @NotNull Triple<A, B, C> q
    ) {
        Ordering aOrdering = Ordering.compare(p.a, q.a);
        if (aOrdering != EQ) return aOrdering;
        Ordering bOrdering = Ordering.compare(p.b, q.b);
        if (bOrdering != EQ) return bOrdering;
        return Ordering.compare(p.c, q.c);
    }

    /**
     * Determines whether <tt>this</tt> is equal to <tt>that</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Triple</tt>.</li>
     *  <li><tt>that</tt> may be any <tt>Object</tt>.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param that The <tt>Triple</tt> to be compared with <tt>this</tt>
     * @return <tt>this</tt>=<tt>that</tt>
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Triple triple = (Triple) that;
        return (a == null ? triple.a == null : a.equals(triple.a)) &&
               (b == null ? triple.b == null : b.equals(triple.b)) &&
               (c == null ? triple.c == null : c.equals(triple.c));
    }

    /**
     * Calculates the hash code of <tt>this</tt>. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Triple</tt>.</li>
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
        return result;
    }

    /**
     * Creates a string representation of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Triple</tt>.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  <tt>", "</tt> at least twice.</li>
     * </ul>
     *
     * @return a string representation of <tt>this</tt>.
     */
    public @NotNull String toString() {
        return "(" + a + ", " + b + ", " + c + ")";
    }

    /**
     * A comparator which compares two <tt>Triple</tt>s via <tt>Comparators</tt> provided for each component.
     *
     * @param <A> the type of the <tt>Triple</tt>s' first components
     * @param <B> the type of the <tt>Triple</tt>s' second components
     * @param <C> the type of the <tt>Triple</tt>s' third components
     */
    public static class TripleComparator<A, B, C> implements Comparator<Triple<A, B, C>> {
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
         * Constructs a <tt>TripleComparator</tt> from three <tt>Comparator</tt>s.
         *
         * <ul>
         *  <li><tt>aComparator</tt> must be non-null.</li>
         *  <li><tt>bComparator</tt> must be non-null.</li>
         *  <li><tt>cComparator</tt> must be non-null.</li>
         *  <li>Any <tt>TripleComparator</tt> may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's <tt>Comparator</tt>
         * @param bComparator the second component's <tt>Comparator</tt>
         * @param cComparator the third component's <tt>Comparator</tt>
         */
        public TripleComparator(
                @NotNull Comparator<A> aComparator,
                @NotNull Comparator<B> bComparator,
                @NotNull Comparator<C> cComparator
        ) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
            this.cComparator = cComparator;
        }

        /**
         * Compares two <tt>Triple</tt>s, returning 1, &#x2212;1, or 0 if the answer is "greater than", "less than", or
         * "equal to", respectively.
         *
         * <ul>
         *  <li><tt>p</tt> must be non-null.</li>
         *  <li><tt>q</tt> must be non-null.</li>
         *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by <tt>aComparator</tt>.</li>
         *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by <tt>bComparator</tt>.</li>
         *  <li><tt>p.c</tt> and <tt>q.c</tt> must be comparable by <tt>cComparator</tt>.</li>
         *  <li>The result is &#x2212;1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first <tt>Triple</tt>
         * @param q the second <tt>Triple</tt>
         * @return <tt>this</tt> compared to <tt>that</tt>
         */
        @Override
        public int compare(@NotNull Triple<A, B, C> p, @NotNull Triple<A, B, C> q) {
            Ordering aOrdering = Ordering.compare(aComparator, p.a, q.a);
            if (aOrdering != EQ) return aOrdering.toInt();
            Ordering bOrdering = Ordering.compare(bComparator, p.b, q.b);
            if (bOrdering != EQ) return bOrdering.toInt();
            return Ordering.compare(cComparator, p.c, q.c).toInt();
        }
    }
}
