package mho.haskellesque.structures;

import mho.haskellesque.ordering.NullHandlingComparator;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * An ordered Septuple of values. Any combination of values may be null. If all of the values are immutable, then so
 * is the <tt>Septuple</tt>.
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
     * The first component of the <tt>Septuple</tt>
     */
    public final @Nullable A a;

    /**
     * The second component of the <tt>Septuple</tt>
     */
    public final @Nullable B b;

    /**
     * The third component of the <tt>Septuple</tt>
     */
    public final @Nullable C c;

    /**
     * The fourth component of the <tt>Septuple</tt>
     */
    public final @Nullable D d;

    /**
     * The fifth component of the <tt>Septuple</tt>
     */
    public final @Nullable E e;

    /**
     * The fifth component of the <tt>Septuple</tt>
     */
    public final @Nullable F f;

    /**
     * The sixth component of the <tt>Septuple</tt>
     */
    public final @Nullable G g;

    /**
     * Constructs a <tt>Septuple</tt> from seven values.
     *
     * <ul>
     *  <li><tt>a</tt> may be anything.</li>
     *  <li><tt>b</tt> may be anything.</li>
     *  <li><tt>c</tt> may be anything.</li>
     *  <li><tt>d</tt> may be anything.</li>
     *  <li><tt>e</tt> may be anything.</li>
     *  <li><tt>f</tt> may be anything.</li>
     *  <li><tt>g</tt> may be anything.</li>
     *  <li>Any <tt>Septuple</tt> may be constructed with this constructor.</li>
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
     * Compares two <tt>Septuple</tt>s, provided that <tt>A</tt>, <tt>B</tt>, <tt>C</tt>, <tt>D</tt>, <tt>E</tt>,
     * <tt>F</tt>, and <tt>G</tt> all extend <tt>Comparable</tt>. Any combination of the <tt>Septuple</tt>s' components
     * may be null.
     *
     * <ul>
     *  <li><tt>p</tt> must be non-null.</li>
     *  <li><tt>q</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param p the first <tt>Septuple</tt>
     * @param q the second <tt>Septuple</tt>
     * @param <A> the type of the first component of <tt>p</tt> and <tt>q</tt>
     * @param <B> the type of the second component of <tt>p</tt> and <tt>q</tt>
     * @param <C> the type of the third component of <tt>p</tt> and <tt>q</tt>
     * @param <D> the type of the fourth component of <tt>p</tt> and <tt>q</tt>
     * @param <E> the type of the fifth component of <tt>p</tt> and <tt>q</tt>
     * @param <F> the type of the sixth component of <tt>p</tt> and <tt>q</tt>
     * @param <G> the type of the seventh component of <tt>p</tt> and <tt>q</tt>
     * @return how <tt>p</tt> and <tt>q</tt> are ordered
     */
    private static @NotNull
    <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>,
            F extends Comparable<F>,
            G extends Comparable<G>
            > Ordering compare(@NotNull Septuple<A, B, C, D, E, F, G> p, @NotNull Septuple<A, B, C, D, E, F, G> q) {
        Ordering aOrdering = Ordering.compare(new NullHandlingComparator<>(), p.a, q.a);
        if (aOrdering != Ordering.EQ) return aOrdering;
        Ordering bOrdering = Ordering.compare(new NullHandlingComparator<>(), p.b, q.b);
        if (bOrdering != Ordering.EQ) return bOrdering;
        Ordering cOrdering = Ordering.compare(new NullHandlingComparator<>(), p.c, q.c);
        if (cOrdering != Ordering.EQ) return cOrdering;
        Ordering dOrdering = Ordering.compare(new NullHandlingComparator<>(), p.d, q.d);
        if (dOrdering != Ordering.EQ) return dOrdering;
        Ordering eOrdering = Ordering.compare(new NullHandlingComparator<>(), p.e, q.e);
        if (eOrdering != Ordering.EQ) return dOrdering;
        Ordering fOrdering = Ordering.compare(new NullHandlingComparator<>(), p.f, q.f);
        if (fOrdering != Ordering.EQ) return dOrdering;
        return Ordering.compare(new NullHandlingComparator<>(), p.g, q.g);
    }

    /**
     * Determines whether <tt>this</tt> is equal to <tt>that</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Septuple</tt>.</li>
     *  <li><tt>that</tt> may be any <tt>Object</tt>.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param that The <tt>Septuple</tt> to be compared with <tt>this</tt>
     * @return <tt>this</tt>=<tt>that</tt>
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
     * Calculates the hash code of <tt>this</tt>. The hash code is deterministic iff all values' hash codes are.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Septuple</tt>.</li>
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
        result = 31 * result + (g != null ? g.hashCode() : 0);
        return result;
    }

    /**
     * Creates a string representation of <tt>this</tt>.
     *
     * <ul>
     *  <li><tt>this</tt> may be any <tt>Septuple</tt>.</li>
     *  <li>The result begins with a left parenthesis, ends with a right parenthesis, and contains the string
     *  <tt>", "</tt> at least six times.</li>
     * </ul>
     *
     * @return a string representation of <tt>this</tt>.
     */
    public String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ")";
    }

    /**
     * A comparator which compares two <tt>Septuple</tt>s whose values' types <tt>A</tt>, <tt>B</tt>, <tt>C</tt>,
     * <tt>D</tt>, <tt>E</tt>, and <tt>F</tt> all implement <tt>Comparable</tt>.
     *
     * @param <A> the type of the <tt>Septuple</tt>s' first components
     * @param <B> the type of the <tt>Septuple</tt>s' second components
     * @param <C> the type of the <tt>Septuple</tt>s' third components
     * @param <D> the type of the <tt>Septuple</tt>s' fourth components
     * @param <E> the type of the <tt>Septuple</tt>s' fifth components
     * @param <F> the type of the <tt>Septuple</tt>s' sixth components
     * @param <G> the type of the <tt>Septuple</tt>s' seventh components
     */
    public static class SeptupleComparator<
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>,
            E extends Comparable<E>,
            F extends Comparable<F>,
            G extends Comparable<G>
            > implements Comparator<Septuple<A, B, C, D, E, F, G>> {
        /**
         * Compares two <tt>Septuple</tt>s, returning 1, &#x2212;1, or 0 if the answer is "greater than", "less than",
         * or "equal to", respectively. Any combination of the <tt>Septuple</tt>s' components may be null.
         *
         * <ul>
         *  <li><tt>p</tt> must be non-null.</li>
         *  <li><tt>q</tt> must be non-null.</li>
         *  <li>The result is &#x2212;1, 0, or 1.</li>
         * </ul>
         *
         * @param p the first <tt>Septuple</tt>
         * @param q the second <tt>Septuple</tt>
         * @return <tt>this</tt> compared to <tt>that</tt>
         */
        @Override
        public int compare(@NotNull Septuple<A, B, C, D, E, F, G> p, @NotNull Septuple<A, B, C, D, E, F, G> q) {
            return Septuple.compare(p, q).toInt();
        }
    }
}
