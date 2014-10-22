package mho.haskellesque.math;

import mho.haskellesque.iterables.CachedIterable;
import mho.haskellesque.iterables.ExhaustiveProvider;
import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * Various combinatorial functions and <tt>Iterable</tt>s.
 */
public final class Combinatorics {
    /**
     * A provider of <tt>Iterable</tt>s containing every value of some type.
     */
    private static final @NotNull ExhaustiveProvider P = new ExhaustiveProvider();

    /**
     * Disallow instantiation
     */
    private Combinatorics() {}

    /**
     * The factorial function <tt>n</tt>!
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a factorial.</li>
     * </ul>
     *
     * @param n the argument
     * @return <tt>n</tt>!
     */
    public static @NotNull BigInteger factorial(int n) {
        if (n < 0)
            throw new ArithmeticException("cannot take factorial of " + n);
        return productBigInteger(range(BigInteger.ONE, BigInteger.valueOf(n)));
    }

    /**
     * The factorial function <tt>n</tt>!
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a factorial.</li>
     * </ul>
     *
     * @param n the argument
     * @return <tt>n</tt>!
     */
    public static @NotNull BigInteger factorial(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot take factorial of " + n);
        return productBigInteger(range(BigInteger.ONE, n));
    }

    /**
     * The subfactorial function !<tt>n</tt>
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a subfactorial (rencontres number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !<tt>n</tt>
     */
    public static @NotNull BigInteger subfactorial(int n) {
        if (n < 0)
            throw new ArithmeticException("cannot take subfactorial of " + n);
        BigInteger sf = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            sf = sf.multiply(BigInteger.valueOf(i));
            if ((i & 1) == 0) {
                sf = sf.add(BigInteger.ONE);
            } else {
                sf = sf.subtract(BigInteger.ONE);
            }
        }
        return sf;
    }

    /**
     * The subfactorial function !<tt>n</tt>
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a subfactorial (rencontres number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !<tt>n</tt>
     */
    public static @NotNull BigInteger subfactorial(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot take subfactorial of " + n);
        BigInteger sf = BigInteger.ONE;
        for (BigInteger bi : range(BigInteger.ONE, n)) {
            sf = sf.multiply(bi);
            if (bi.getLowestSetBit() != 0) {
                sf = sf.add(BigInteger.ONE);
            } else {
                sf = sf.subtract(BigInteger.ONE);
            }
        }
        return sf;
    }

    /**
     * Given two <tt>Iterable</tt>s, returns all ordered pairs of elements from these <tt>Iterable</tt>s in ascending
     * order. The second <tt>Iterable</tt> must be finite; using a long second <tt>Iterable</tt> is possible but
     * discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of two finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>|
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @return all ordered pairs of elements from <tt>as</tt> and <tt>bs</tt>
     */
    public static @NotNull <A, B> Iterable<Pair<A, B>> pairsAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return concatMap(p -> zip(repeat(p.a), p.b), zip(as, repeat(bs)));
    }

    /**
     * Given three <tt>Iterable</tt>s, returns all ordered triples of elements from these <tt>Iterable</tt>s in
     * ascending order. All <tt>Iterable</tt>s but the first must be finite; using long <tt>Iterable</tt>s in any
     * position but the first is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li><tt>cs</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of three finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>|
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param cs the third <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @return all ordered triples of elements from <tt>as</tt>, <tt>bs</tt>, and <tt>cs</tt>
     */
    public static @NotNull <A, B, C> Iterable<Triple<A, B, C>> triplesAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        return map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                pairsAscending(as, (Iterable<Pair<B, C>>) pairsAscending(bs, cs))
        );
    }

    /**
     * Given four <tt>Iterable</tt>s, returns all ordered quadruples of elements from these <tt>Iterable</tt>s in
     * ascending order. All <tt>Iterable</tt>s but the first must be finite; using long <tt>Iterable</tt>s in any
     * position but the first is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li><tt>cs</tt> must be finite.</li>
     *  <li><tt>ds</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of four finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>|
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param cs the third <tt>Iterable</tt>
     * @param ds the fourth <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @return all ordered quadruples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, and <tt>ds</tt>
     */
    public static @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruplesAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    ) {
        return map(
                p -> new Quadruple<>(p.a.a, p.a.b, p.b.a, p.b.b),
                pairsAscending(
                        (Iterable<Pair<A, B>>) pairsAscending(as, bs),
                        (Iterable<Pair<C, D>>) pairsAscending(cs, ds)
                )
        );
    }

    /**
     * Given five <tt>Iterable</tt>s, returns all ordered quintuples of elements from these <tt>Iterable</tt>s in
     * ascending order. All <tt>Iterable</tt>s but the first must be finite; using long <tt>Iterable</tt>s in any
     * position but the first is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li><tt>cs</tt> must be finite.</li>
     *  <li><tt>ds</tt> must be finite.</li>
     *  <li><tt>es</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of five finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>|
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param cs the third <tt>Iterable</tt>
     * @param ds the fourth <tt>Iterable</tt>
     * @param es the fifth <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @return all ordered quintuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, and
     * <tt>es</tt>
     */
    public static @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuplesAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    ) {
        return map(
                p -> new Quintuple<>(p.a.a, p.a.b, p.b.a, p.b.b, p.b.c),
                pairsAscending(
                        (Iterable<Pair<A, B>>) pairsAscending(as, bs),
                        (Iterable<Triple<C, D, E>>) triplesAscending(cs, ds, es)
                )
        );
    }

    /**
     * Given six <tt>Iterable</tt>s, returns all ordered sextuples of elements from these <tt>Iterable</tt>s in
     * ascending order. All <tt>Iterable</tt>s but the first must be finite; using long <tt>Iterable</tt>s in any
     * position but the first is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li><tt>cs</tt> must be finite.</li>
     *  <li><tt>ds</tt> must be finite.</li>
     *  <li><tt>es</tt> must be finite.</li>
     *  <li><tt>fs</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of six finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>||<tt>fs</tt>|
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param cs the third <tt>Iterable</tt>
     * @param ds the fourth <tt>Iterable</tt>
     * @param es the fifth <tt>Iterable</tt>
     * @param fs the sixth <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @param <F> the type of the sixth <tt>Iterable</tt>'s elements
     * @return all ordered sextuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, <tt>es</tt>,
     * and <tt>fs</tt>
     */
    public static @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuplesAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    ) {
        return map(
                p -> new Sextuple<>(p.a.a, p.a.b, p.a.c, p.b.a, p.b.b, p.b.c),
                pairsAscending(
                        (Iterable<Triple<A, B, C>>) triplesAscending(as, bs, cs),
                        (Iterable<Triple<D, E, F>>) triplesAscending(ds, es, fs)
                )
        );
    }

    /**
     * Given seven <tt>Iterable</tt>s, returns all ordered septuples of elements from these <tt>Iterable</tt>s in
     * ascending order. All <tt>Iterable</tt>s but the first must be finite; using long <tt>Iterable</tt>s in any
     * position but the first is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be finite.</li>
     *  <li><tt>cs</tt> must be finite.</li>
     *  <li><tt>ds</tt> must be finite.</li>
     *  <li><tt>es</tt> must be finite.</li>
     *  <li><tt>fs</tt> must be finite.</li>
     *  <li><tt>gs</tt> must be finite.</li>
     *  <li>The result is the Cartesian product of seven finite <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>||<tt>fs</tt>||<tt>gs</tt>
     *
     * @param as the first <tt>Iterable</tt>
     * @param bs the second <tt>Iterable</tt>
     * @param cs the third <tt>Iterable</tt>
     * @param ds the fourth <tt>Iterable</tt>
     * @param es the fifth <tt>Iterable</tt>
     * @param fs the sixth <tt>Iterable</tt>
     * @param gs the seventh <tt>Iterable</tt>
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @param <F> the type of the sixth <tt>Iterable</tt>'s elements
     * @param <G> the type of the seventh <tt>Iterable</tt>'s elements
     * @return all ordered septuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, <tt>es</tt>,
     * <tt>fs</tt>, and <tt>gs</tt>
     */
    public static @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuplesAscending(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    ) {
        return map(
                p -> new Septuple<>(p.a.a, p.a.b, p.a.c, p.b.a, p.b.b, p.b.c, p.b.d),
                pairsAscending(
                        (Iterable<Triple<A, B, C>>) triplesAscending(as, bs, cs),
                        (Iterable<Quadruple<D, E, F, G>>) quadruplesAscending(ds, es, fs, gs)
                )
        );
    }

    /**
     * Returns an <tt>Iterable</tt> containing all lists of a given length with elements from a given
     * <tt>Iterable</tt>. The lists are ordered lexicographically, matching the order given by the original
     * <tt>Iterable</tt>. The <tt>Iterable</tt> must be finite; using a long <tt>Iterable</tt> is possible but
     * discouraged.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is finite. All of its elements have the same length. None are empty, unless the result consists
     *  entirely of one empty element.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|<sup><tt>length</tt></sup>
     *
     * @param length the length of the result lists
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all lists of a given length created from <tt>xs</tt>
     */
    public static @NotNull <T> Iterable<List<T>> listsAscending(int length, @NotNull Iterable<T> xs) {
        if (length < 0)
            throw new IllegalArgumentException("lists must have a non-negative length");
        if (length == 0) {
            return Arrays.asList(new ArrayList<T>());
        }
        Function<T, List<T>> makeSingleton = x -> {
            List<T> list = new ArrayList<>();
            list.add(x);
            return list;
        };
        List<Iterable<List<T>>> intermediates = new ArrayList<>();
        intermediates.add(map(makeSingleton, xs));
        for (int i = 1; i < length; i++) {
            Iterable<List<T>> lists = last(intermediates);
            intermediates.add(concatMap(x -> map(list -> toList(cons(x, list)), lists), xs));
        }
        return last(intermediates);
    }

    /**
     * Returns an <tt>Iterable</tt> containing all lists of a given length with elements from a given
     * <tt>Iterable</tt>. The lists are ordered lexicographically, matching the order given by the original
     * <tt>Iterable</tt>. The <tt>Iterable</tt> must be finite; using a long <tt>Iterable</tt> is possible but
     * discouraged.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is finite. All of its elements have the same length. None are empty, unless the result consists
     *  entirely of one empty element.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|<sup><tt>length</tt></sup>
     *
     * @param length the length of the result lists
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all lists of a given length created from <tt>xs</tt>
     */
    public static @NotNull <T> Iterable<List<T>> listsAscending(@NotNull BigInteger length, @NotNull Iterable<T> xs) {
        if (lt(length, BigInteger.ZERO))
            throw new IllegalArgumentException("lists must have a non-negative length");
        if (eq(length, BigInteger.ZERO)) {
            return Arrays.asList(new ArrayList<T>());
        }
        Function<T, List<T>> makeSingleton = x -> {
            List<T> list = new ArrayList<>();
            list.add(x);
            return list;
        };
        List<Iterable<List<T>>> intermediates = new ArrayList<>();
        intermediates.add(map(makeSingleton, xs));
        for (BigInteger bi : range(BigInteger.ONE, length.subtract(BigInteger.ONE))) {
            Iterable<List<T>> lists = last(intermediates);
            intermediates.add(concatMap(x -> map(list -> toList((Iterable<T>) cons(x, list)), lists), xs));
        }
        return last(intermediates);
    }

    /**
     * Returns an <tt>Iterable</tt> containing all <tt>String</tt>s of a given length with characters from a given
     * <tt>Iterable</tt>. The <tt>String</tt>s are ordered lexicographically, matching the order given by the original
     * <tt>Iterable</tt>. Using long <tt>String</tt> is possible but discouraged.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>s</tt> is non-null.</li>
     *  <li>The result is finite. All of its <tt>String</tt>s have the same length. None are empty, unless the result
     *  consists entirely of one empty <tt>String</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|<sup><tt>length</tt></sup>
     *
     * @param length the length of the result <tt>String</tt>
     * @param s the <tt>String</tt> from which characters are selected
     * @return all Strings of a given length created from <tt>s</tt>
     */
    public static @NotNull Iterable<String> stringsAscending(int length, @NotNull String s) {
        if (length < 0)
            throw new IllegalArgumentException("strings must have a non-negative length");
        BigInteger totalLength = BigInteger.valueOf(s.length()).pow(length);
        Function<BigInteger, String> f = bi -> charsToString(
                map(
                        i -> s.charAt(i.intValue()),
                        BasicMath.bigEndianDigitsPadded(BigInteger.valueOf(length), BigInteger.valueOf(s.length()), bi)
                )
        );
        return map(f, range(BigInteger.ZERO, totalLength.subtract(BigInteger.ONE)));
    }

    /**
     * Returns an <tt>Iterable</tt> containing all <tt>String</tt>s of a given length with characters from a given
     * <tt>Iterable</tt>. The <tt>String</tt>s are ordered lexicographically, matching the order given by the original
     * <tt>Iterable</tt>. Using long <tt>String</tt> is possible but discouraged.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>s</tt> is non-null.</li>
     *  <li>The result is finite. All of its <tt>String</tt>s have the same length. None are empty, unless the result
     *  consists entirely of one empty <tt>String</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|<sup><tt>length</tt></sup>
     *
     * @param length the length of the result <tt>String</tt>
     * @param s the <tt>String</tt> from which characters are selected
     * @return all Strings of a given length created from <tt>s</tt>
     */
    public static @NotNull Iterable<String> stringsAscending(@NotNull BigInteger length, @NotNull String s) {
        if (lt(length, BigInteger.ZERO))
            throw new IllegalArgumentException("strings must have a non-negative length");
        BigInteger totalLength = BigInteger.valueOf(s.length()).pow(length.intValue());
        Function<BigInteger, String> f = bi -> charsToString(
                map(
                        i -> s.charAt(i.intValue()),
                        BasicMath.bigEndianDigitsPadded(
                                BigInteger.valueOf(length.intValue()),
                                BigInteger.valueOf(s.length()),
                                bi
                        )
                )
        );
        return map(f, range(BigInteger.ZERO, totalLength.subtract(BigInteger.ONE)));
    }

    /**
     * Returns an <tt>Iterable</tt> containing all lists with elements from a given <tt>Iterable</tt>. The lists are in
     * shortlex order; that is, shorter lists precede longer lists, and lists of the same length are ordered
     * lexicographically, matching the order given by the original <tt>Iterable</tt>. The <tt>Iterable</tt> must be
     * finite; using a long <tt>Iterable</tt> is possible but discouraged.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result either consists of a single empty list, or is infinite. It is in shortlex order (according to
     *  some ordering of its elements) and contains every list of elements drawn from some sequence.</li>
     * </ul>
     *
     * Result length is 1 if <tt>xs</tt> is empty, infinite otherwise
     *
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all lists created from <tt>xs</tt>
     */
    public static @NotNull <T> Iterable<List<T>> listsShortlex(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Arrays.asList(new ArrayList<T>());
        return concatMap(i -> listsAscending(i, xs), P.naturalBigIntegers());
    }

    /**
     * Returns an <tt>Iterable</tt> containing all <tt>String</tt>s with characters from a given <tt>String</tt>. The
     * <tt>String</tt>s are in shortlex order; that is, shorter <tt>String</tt>s precede longer <tt>String</tt>s, and
     * <tt>String</tt>s of the same length are ordered lexicographically, matching the order given by the original
     * <tt>String</tt>. Using a long <tt>String</tt> is possible but discouraged.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li>The result either consists of a single empty <tt>String</tt>, or is infinite. It is in shortlex order
     *  (according to some ordering of its characters) and contains every <tt>String</tt> with characters drawn from
     *  some sequence.</li>
     * </ul>
     *
     * Result length is 1 if <tt>s</tt> is empty, infinite otherwise
     *
     * @param s the <tt>String</tt> from which characters are selected
     * @return all <tt>String</tt>s created from <tt>s</tt>
     */
    public static @NotNull Iterable<String> stringsShortlex(@NotNull String s) {
        if (isEmpty(s)) return Arrays.asList("");
        return concatMap(i -> stringsAscending(i, s), P.naturalBigIntegers());
    }

    public static <T> Iterable<List<T>> lists(Iterable<T> xs) {
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi -> {
            if (bi.equals(BigInteger.ZERO)) {
                return Optional.of(new ArrayList<T>());
            }
            bi = bi.subtract(BigInteger.ONE);
            Pair<BigInteger, BigInteger> sizeIndex = BasicMath.logarithmicDemux(bi);
            int size = sizeIndex.b.intValue() + 1;
            return ii.get(map(BigInteger::intValue, BasicMath.demux(size, sizeIndex.a)));
        };
        return map(
                Optional::get,
                filter(
                        Optional<List<T>>::isPresent,
                        (Iterable<Optional<List<T>>>) map(bi -> f.apply(bi), P.naturalBigIntegers())
                )
        );
    }

    public static Iterable<String> strings(Iterable<Character> cs) {
        return map(IterableUtils::charsToString, lists(cs));
    }

    public static Iterable<String> strings(String s) {
        return map(IterableUtils::charsToString, lists(fromString(s)));
    }

    public static <T> Iterable<List<T>> orderedSubsequences(Iterable<T> xs) {
        if (isEmpty(xs))
            return Arrays.asList(new ArrayList<T>());
        final CachedIterable<T> ii = new CachedIterable(xs);
        return () -> new Iterator<List<T>>() {
            private List<Integer> indices = new ArrayList<>();

            @Override
            public boolean hasNext() {
                return indices != null;
            }

            @Override
            public List<T> next() {
                List<T> subsequence = ii.get(indices).get();
                if (indices.isEmpty()) {
                    indices.add(0);
                } else {
                    int lastIndex = last(indices);
                    if (lastIndex < ii.size() - 1) {
                        indices.add(lastIndex + 1);
                    } else if (indices.size() == 1) {
                        indices = null;
                    } else {
                        indices.remove(indices.size() - 1);
                        indices.set(indices.size() - 1, last(indices) + 1);
                    }
                }
                return subsequence;
            }
        };
    }
}
