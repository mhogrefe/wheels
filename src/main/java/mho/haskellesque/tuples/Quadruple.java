package mho.haskellesque.tuples;

import mho.haskellesque.ordering.NullHandlingComparator;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * An ordered quadruple of values. Any combination of values may be null. If all of the values are immutable, then so
 * is the <tt>Quadruple</tt>.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 * @param <C> the type of the third value
 * @param <D> the type of the fourth value
 */
public final class Quadruple<A, B, C, D> {
    /**
     * The first component of the <tt>Quadruple</tt>
     */
    public final @Nullable A a;

    /**
     * The second component of the <tt>Quadruple</tt>
     */
    public final @Nullable B b;

    /**
     * The third component of the <tt>Quadruple</tt>
     */
    public final @Nullable C c;

    /**
     * The fourth component of the <tt>Quadruple</tt>
     */
    public final @Nullable D d;

    /**
     * Constructs a <tt>Quadruple</tt> from four values.
     *
     * <ul>
     *  <li><tt>a</tt> may be anything</li>
     *  <li><tt>b</tt> may be anything</li>
     *  <li><tt>c</tt> may be anything</li>
     *  <li><tt>d</tt> may be anything</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     */
    public Quadruple(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Compares two <tt>Quadruple</tt>s, provided that <tt>A</tt>, <tt>B</tt>, <tt>C</tt>, and <tt>D</tt> all extend
     * <tt>Comparable</tt>. Any combination of the <tt>Quadruple</tt>s' components may be null.
     *
     * <ul>
     *  <li><tt>p</tt> must be non-null.</li>
     *  <li><tt>q</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first <tt>Quadruple</tt>
     * @param q the second <tt>Quadruple</tt>
     * @param <A> the type of the first component of <tt>p</tt> and <tt>q</tt>
     * @param <B> the type of the second component of <tt>p</tt> and <tt>q</tt>
     * @param <C> the type of the third component of <tt>p</tt> and <tt>q</tt>
     * @param <D> the type of the fourth component of <tt>p</tt> and <tt>q</tt>
     * @return how <tt>p</tt> and <tt>q</tt> are ordered
     */
    private static @NotNull <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>
            > Ordering compare(@NotNull Quadruple<A, B, C, D> p, @NotNull Quadruple<A, B, C, D> q) {
        Ordering aOrdering = Ordering.compare(new NullHandlingComparator<>(), p.a, q.a);
        if (aOrdering != Ordering.EQ) return aOrdering;
        Ordering bOrdering = Ordering.compare(new NullHandlingComparator<>(), p.b, q.b);
        if (bOrdering != Ordering.EQ) return bOrdering;
        Ordering cOrdering = Ordering.compare(new NullHandlingComparator<>(), p.c, q.c);
        if (cOrdering != Ordering.EQ) return cOrdering;
        return Ordering.compare(new NullHandlingComparator<D>(), p.d, q.d);
    }

    /**
     * Determines whether <tt>this</tt> is equal to <tt>that</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Quadruple</tt>.</li>
     *  <li><tt>that</tt> may be any <tt>Object</tt>.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param that The <tt>Quadruple</tt> to be compared with <tt>this</tt>
     * @return <tt>this</tt>=<tt>that</tt>
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Quadruple Quadruple = (Quadruple) that;
        return (a == null ? Quadruple.a == null : a.equals(Quadruple.a)) &&
                (b == null ? Quadruple.b == null : b.equals(Quadruple.b)) &&
                (c == null ? Quadruple.c == null : c.equals(Quadruple.c)) &&
                (d == null ? Quadruple.d == null : d.equals(Quadruple.d));
    }

    /**
     * Calculates the hash code of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Quadruple</tt>.</li>
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
        return result;
    }

    /**
     * Creates a string representation of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Quadruple</tt>.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  <tt>", "</tt> at least three times.</li>
     * </ul>
     *
     * @return a string representation of <tt>this</tt>.
     */
    public String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ")";
    }

    /**
     * A comparator which compares two <tt>Quadruple</tt>s whose values' types <tt>A</tt>, <tt>B</tt>, <tt>C</tt> and
     * <tt>D</tt> all implement <tt>Comparable</tt>.
     *
     * @param <A> the type of the <tt>Quadruple</tt>s' first components
     * @param <B> the type of the <tt>Quadruple</tt>s' second components
     * @param <C> the type of the <tt>Quadruple</tt>s' third components
     * @param <D> the type of the <tt>Quadruple</tt>s' fourth components
     */
    public static class QuadrupleComparator<
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>
            > implements Comparator<Quadruple<A, B, C, D>> {
        /**
         * Compares two <tt>Quadruple</tt>s, returning 1, &#x2212;1, or 0 if the answer is "greater than", "less than",
         * or "equal to", respectively. Any combination of the <tt>Quadruple</tt>s' components may be null.
         *
         * <ul>
         *  <li><tt>p</tt> must be non-null.</li>
         *  <li><tt>q</tt> must be non-null.</li>
         *  <li>The result is &#x2212;1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first <tt>Quadruple</tt>
         * @param q the second <tt>Quadruple</tt>
         * @return <tt>this</tt> compared to <tt>that</tt>
         */
        @Override
        public int compare(@NotNull Quadruple<A, B, C, D> p, @NotNull Quadruple<A, B, C, D> q) {
            return Quadruple.compare(p, q).toInt();
        }
    }
}
