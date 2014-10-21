package mho.haskellesque.structures;

import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static mho.haskellesque.ordering.Ordering.*;

/**
 * An ordered pair of values. Either value, or both, may be null. The <tt>Pair</tt> is immutable iff both of its values
 * are.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 */
public final class Pair<A, B> {
    /**
     * The first component of the <tt>Pair</tt>
     */
    public final @Nullable A a;

    /**
     * The second component of the <tt>Pair</tt>
     */
    public final @Nullable B b;

    /**
     * Constructs a <tt>Pair</tt> from two values.
     *
     * <ul>
     *  <li><tt>a</tt> may be anything.</li>
     *  <li><tt>b</tt> may be anything.</li>
     *  <li>Any <tt>Pair</tt> may be constructed with this constructor.</li>
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
     * Compares two <tt>Pair</tt>s, provided that <tt>A</tt> and <tt>B</tt> both extend <tt>Comparable</tt>.
     *
     * <ul>
     *  <li><tt>p</tt> must be non-null.</li>
     *  <li><tt>q</tt> must be non-null.</li>
     *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by their type's <tt>compareTo</tt> method.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first <tt>Pair</tt>
     * @param q the second <tt>Pair</tt>
     * @param <A> the type of the first component of <tt>p</tt> and <tt>q</tt>
     * @param <B> the type of the second component of <tt>p</tt> and <tt>q</tt>
     * @return how <tt>p</tt> and <tt>q</tt> are ordered
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
     * Determines whether <tt>this</tt> is equal to <tt>that</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Pair</tt>.</li>
     *  <li><tt>that</tt> may be any <tt>Object</tt>.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param that The <tt>Pair</tt> to be compared with <tt>this</tt>
     * @return <tt>this</tt>=<tt>that</tt>
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
     * Calculates the hash code of <tt>this</tt>. The hash code is deterministic iff both values' hash codes are.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Pair</tt>.</li>
     *  <li>(conjecture) The result may be any <tt>int</tt>.</li>
     * </ul>
     *
     * @return <tt>this</tt>'s hash code.
     */
    @Override
    public int hashCode() {
        int result = a == null ? 0 : a.hashCode();
        result = 31 * result + (b == null ? 0 : b.hashCode());
        return result;
    }

    /**
     * Creates a string representation of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Pair</tt>.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  <tt>", "</tt>.</li>
     * </ul>
     *
     * @return a string representation of <tt>this</tt>.
     */
    public String toString() {
        return "(" + a + ", " + b + ")";
    }

    /**
     * A comparator which compares two <tt>Pair</tt>s via <tt>Comparators</tt> provided for each component.
     *
     * @param <A> the type of the <tt>Pair</tt>s' first components
     * @param <B> the type of the <tt>Pair</tt>s' second components
     */
    public static class PairComparator<A, B> implements Comparator<Pair<A, B>> {
        /**
         * The first component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<A> aComparator;

        /**
         * The first component's <tt>Comparator</tt>
         */
        private final @NotNull Comparator<B> bComparator;

        /**
         * Constructs a <tt>PairComparator</tt> from two <tt>Comparator</tt>s.
         *
         * <ul>
         *  <li><tt>aComparator</tt> must be non-null.</li>
         *  <li><tt>bComparator</tt> must be non-null.</li>
         *  <li>Any <tt>PairComparator</tt> may be constructed with this constructor.</li>
         * </ul>
         *
         * @param aComparator the first component's <tt>Comparator</tt>
         * @param bComparator the second component's <tt>Comparator</tt>
         */
        public PairComparator(@NotNull Comparator<A> aComparator, @NotNull Comparator<B> bComparator) {
            this.aComparator = aComparator;
            this.bComparator = bComparator;
        }

        /**
         * Compares two <tt>Pair</tt>s, returning 1, &#x2212;1, or 0 if the answer is "greater than", "less than", or
         * "equal to", respectively. Either, or both, of the <tt>Pair</tt>s' components may be null.
         *
         * <ul>
         *  <li><tt>p</tt> must be non-null.</li>
         *  <li><tt>q</tt> must be non-null.</li>
         *  <li><tt>p.a</tt> and <tt>q.a</tt> must be comparable by <tt>aComparator</tt>.</li>
         *  <li><tt>p.b</tt> and <tt>q.b</tt> must be comparable by <tt>bComparator</tt>.</li>
         *  <li>The result is &#x2212;1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first <tt>Pair</tt>
         * @param q the second <tt>Pair</tt>
         * @return <tt>this</tt> compared to <tt>that</tt>
         */
        @Override
        public int compare(@NotNull Pair<A, B> p, @NotNull Pair<A, B> q) {
            Ordering aOrdering = Ordering.compare(aComparator, p.a, q.a);
            if (aOrdering != EQ) return aOrdering.toInt();
            return Ordering.compare(bComparator, p.b, q.b).toInt();
        }
    }
}