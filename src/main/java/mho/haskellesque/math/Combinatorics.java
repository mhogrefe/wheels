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
    private static final @NotNull ExhaustiveProvider P = ExhaustiveProvider.INSTANCE;

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
                        i -> s.charAt(i.intValueExact()),
                        MathUtils.bigEndianDigitsPadded(BigInteger.valueOf(length), BigInteger.valueOf(s.length()), bi)
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
        BigInteger totalLength = BigInteger.valueOf(s.length()).pow(length.intValueExact());
        Function<BigInteger, String> f = bi -> charsToString(
                map(
                        i -> s.charAt(i.intValueExact()),
                        MathUtils.bigEndianDigitsPadded(
                                BigInteger.valueOf(length.intValueExact()),
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

    //todo
    private static @NotNull <A, B> Iterable<Pair<A, B>> pairsByFunction(
            @NotNull Function<BigInteger, Pair<BigInteger, BigInteger>> unpairingFunction,
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        if (isEmpty(as) || isEmpty(bs)) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        Function<BigInteger, Optional<Pair<A, B>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = unpairingFunction.apply(bi);
            assert p.a != null;
            NullableOptional<A> optA = aii.get(p.a.intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            NullableOptional<B> optB = bii.get(p.b.intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<A, B>(optA.get(), optB.get()));
        };
        Predicate<Optional<Pair<A, B>>> lastPair = o -> {
            if (!o.isPresent()) return false;
            Pair<A, B> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            return lastA.isPresent() && lastB.isPresent() && lastA.get() && lastB.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<A, B>>::isPresent,
                        stopAt(
                                lastPair,
                                (Iterable<Optional<Pair<A, B>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all pairs of elements taken from one <tt>Iterable</tt>s in such a way that the first component grows
     * linearly but the second grows logarithmically (hence the name).
     *
     * <ul>
     *  <li><tt>xs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all pairs of elements taken from some <tt>Iterable</tt>.
     *  The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  <tt>BasicMath.logarithmicDemux</tt> and interpreting the resulting pairs as indices into the original
     *  <tt>Iterable</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|<sup>2</sup>
     *
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>xs</tt> in logarithmic order
     */
    public static @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return new ArrayList<>();
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<Pair<T, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = MathUtils.logarithmicDemux(bi);
            assert p.a != null;
            NullableOptional<T> optA = ii.get(p.a.intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            NullableOptional<T> optB = ii.get(p.b.intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<T, T>(optA.get(), optB.get()));
        };
        Predicate<Optional<Pair<T, T>>> lastPair = o -> {
            if (!o.isPresent()) return false;
            Pair<T, T> p = o.get();
            Optional<Boolean> lastA = ii.isLast(p.a);
            Optional<Boolean> lastB = ii.isLast(p.b);
            return lastA.isPresent() && lastB.isPresent() && lastA.get() && lastB.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<T, T>>::isPresent,
                        stopAt(
                                lastPair,
                                (Iterable<Optional<Pair<T, T>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all pairs of elements taken from two <tt>Iterable</tt>s in such a way that the first component grows
     * linearly but the second grows logarithmically.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all pairs of elements taken from two <tt>Iterable</tt>s.
     *  The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  <tt>BasicMath.logarithmicDemux</tt> and interpreting the resulting pairs as indices into the original
     *  <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the pairs are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the pairs are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>as</tt> and <tt>bs</tt> in logarithmic order
     */
    public static @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairsByFunction(MathUtils::logarithmicDemux, as, bs);
    }

    /**
     * Returns all pairs of elements taken from one <tt>Iterable</tt>s in such a way that the first component grows
     * as O(n<sup>2/3</sup>) but the second grows as O(n<sup>1/3</sup>).
     *
     * <ul>
     *  <li><tt>xs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all pairs of elements taken from some <tt>Iterable</tt>.
     *  The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  <tt>BasicMath.squareRootDemux</tt> and interpreting the resulting pairs as indices into the original
     *  <tt>Iterable</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|<sup>2</sup>
     *
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>xs</tt> in square-root order
     */
    public static @NotNull <T> Iterable<Pair<T, T>> pairsSquareRootOrder(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return new ArrayList<>();
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<Pair<T, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = MathUtils.squareRootDemux(bi);
            assert p.a != null;
            NullableOptional<T> optA = ii.get(p.a.intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            NullableOptional<T> optB = ii.get(p.b.intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<T, T>(optA.get(), optB.get()));
        };
        Predicate<Optional<Pair<T, T>>> lastPair = o -> {
            if (!o.isPresent()) return false;
            Pair<T, T> p = o.get();
            Optional<Boolean> lastA = ii.isLast(p.a);
            Optional<Boolean> lastB = ii.isLast(p.b);
            return lastA.isPresent() && lastB.isPresent() && lastA.get() && lastB.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<T, T>>::isPresent,
                        stopAt(
                                lastPair,
                                (Iterable<Optional<Pair<T, T>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all pairs of elements taken from two <tt>Iterable</tt>s in such a way that the first component grows
     * as O(n<sup>2/3</sup>) but the second grows as O(n<sup>1/3</sup>).
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all pairs of elements taken from two <tt>Iterable</tt>s.
     *  The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  <tt>BasicMath.squareRootDemux</tt> and interpreting the resulting pairs as indices into the original
     *  <tt>Iterable</tt>s.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the pairs are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the pairs are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>as</tt> and <tt>bs</tt> in square-root order
     */
    public static @NotNull <A, B> Iterable<Pair<A, B>> pairsSquareRootOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairsByFunction(MathUtils::squareRootDemux, as, bs);
    }

    /**
     * Returns all pairs of elements taken from two <tt>Iterable</tt>s in such a way that both components grow as the
     * square root of the number of pairs iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all pairs of elements taken from two <tt>Iterable</tt>s.
     *  The elements are ordered by following a Z-curve through the pair space. The curve is computed by
     *  un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the pairs are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the pairs are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>as</tt> and <tt>bs</tt>
     */
    public static @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return pairsByFunction(
                bi -> {
                    List<BigInteger> list = MathUtils.demux(2, bi);
                    return new Pair<>(list.get(0), list.get(1));
                },
                as,
                bs
        );
    }

    /**
     * Returns all triples of elements taken from three <tt>Iterable</tt>s in such a way that all components grow as
     * the cube root of the number of triples iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li><tt>cs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all triples of elements taken from three <tt>Iterable</tt>s.
     *  The elements are ordered by following a Z-curve through the triple space. The curve is computed by
     *  un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the triples are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the triples are selected
     * @param cs the <tt>Iterable</tt> from which the third components of the triples are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @return all triples of elements from <tt>as</tt>, <tt>bs</tt>, and <tt>cs</tt>
     */
    public static @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs)) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        Function<BigInteger, Optional<Triple<A, B, C>>> f = bi -> {
            List<BigInteger> p = MathUtils.demux(3, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValueExact());
            if (!optC.isPresent()) return Optional.empty();
            return Optional.of(new Triple<A, B, C>(optA.get(), optB.get(), optC.get()));
        };
        Predicate<Optional<Triple<A, B, C>>> lastTriple = o -> {
            if (!o.isPresent()) return false;
            Triple<A, B, C> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            Optional<Boolean> lastC = cii.isLast(p.c);
            return lastA.isPresent() &&
                    lastB.isPresent() &&
                    lastC.isPresent() &&
                    lastA.get() &&
                    lastB.get() &&
                    lastC.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Triple<A, B, C>>::isPresent,
                        stopAt(
                                lastTriple,
                                (Iterable<Optional<Triple<A, B, C>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all quadruples of elements taken from four <tt>Iterable</tt>s in such a way that all components grow as
     * the fourth root of the number of quadruples iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li><tt>cs</tt> is non-null.</li>
     *  <li><tt>ds</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all quadruples of elements taken from four
     *  <tt>Iterable</tt>s. The elements are ordered by following a Z-curve through the quadruple space. The curve is
     *  computed by un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the quadruples are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the quadruples are selected
     * @param cs the <tt>Iterable</tt> from which the third components of the quadruples are selected
     * @param ds the <tt>Iterable</tt> from which the fourth components of the quadruples are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @return all quadruples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, and <tt>ds</tt>
     */
    public static @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds)) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        CachedIterable<D> dii = new CachedIterable<>(ds);
        Function<BigInteger, Optional<Quadruple<A, B, C, D>>> f = bi -> {
            List<BigInteger> p = MathUtils.demux(4, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValueExact());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValueExact());
            if (!optD.isPresent()) return Optional.empty();
            return Optional.of(new Quadruple<A, B, C, D>(optA.get(), optB.get(), optC.get(), optD.get()));
        };
        Predicate<Optional<Quadruple<A, B, C, D>>> lastQuadruple = o -> {
            if (!o.isPresent()) return false;
            Quadruple<A, B, C, D> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            Optional<Boolean> lastC = cii.isLast(p.c);
            Optional<Boolean> lastD = dii.isLast(p.d);
            return lastA.isPresent() &&
                    lastB.isPresent() &&
                    lastC.isPresent() &&
                    lastD.isPresent() &&
                    lastA.get() &&
                    lastB.get() &&
                    lastC.get() &&
                    lastD.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Quadruple<A, B, C, D>>::isPresent,
                        stopAt(
                                lastQuadruple,
                                (Iterable<Optional<Quadruple<A, B, C, D>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all quintuples of elements taken from five <tt>Iterable</tt>s in such a way that all components grow as
     * the fifth root of the number of quintuples iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li><tt>cs</tt> is non-null.</li>
     *  <li><tt>ds</tt> is non-null.</li>
     *  <li><tt>es</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all quintuples of elements taken from five
     *  <tt>Iterable</tt>s. The elements are ordered by following a Z-curve through the quintuple space. The curve is
     *  computed by un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the quintuples are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the quintuples are selected
     * @param cs the <tt>Iterable</tt> from which the third components of the quintuples are selected
     * @param ds the <tt>Iterable</tt> from which the fourth components of the quintuples are selected
     * @param es the <tt>Iterable</tt> from which the fifth components of the quintuples are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @return all quintuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, and <tt>es</tt>
     */
    public static @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds) || isEmpty(es)) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        CachedIterable<D> dii = new CachedIterable<>(ds);
        CachedIterable<E> eii = new CachedIterable<>(es);
        Function<BigInteger, Optional<Quintuple<A, B, C, D, E>>> f = bi -> {
            List<BigInteger> p = MathUtils.demux(5, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValueExact());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValueExact());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValueExact());
            if (!optE.isPresent()) return Optional.empty();
            return Optional.of(new Quintuple<A, B, C, D, E>(
                    optA.get(),
                    optB.get(),
                    optC.get(),
                    optD.get(),
                    optE.get()
            ));
        };
        Predicate<Optional<Quintuple<A, B, C, D, E>>> lastQuintuple = o -> {
            if (!o.isPresent()) return false;
            Quintuple<A, B, C, D, E> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            Optional<Boolean> lastC = cii.isLast(p.c);
            Optional<Boolean> lastD = dii.isLast(p.d);
            Optional<Boolean> lastE = eii.isLast(p.e);
            return lastA.isPresent() &&
                    lastB.isPresent() &&
                    lastC.isPresent() &&
                    lastD.isPresent() &&
                    lastE.isPresent() &&
                    lastA.get() &&
                    lastB.get() &&
                    lastC.get() &&
                    lastD.get() &&
                    lastE.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Quintuple<A, B, C, D, E>>::isPresent,
                        stopAt(
                                lastQuintuple,
                                (Iterable<Optional<Quintuple<A, B, C, D, E>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all sextuples of elements taken from six <tt>Iterable</tt>s in such a way that all components grow as
     * the sixth root of the number of sextuples iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li><tt>cs</tt> is non-null.</li>
     *  <li><tt>ds</tt> is non-null.</li>
     *  <li><tt>es</tt> is non-null.</li>
     *  <li><tt>fs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all sextuples of elements taken from six <tt>Iterable</tt>s.
     *  The elements are ordered by following a Z-curve through the sextuple space. The curve is computed by
     *  un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>||<tt>fs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the sextuples are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the sextuples are selected
     * @param cs the <tt>Iterable</tt> from which the third components of the sextuples are selected
     * @param ds the <tt>Iterable</tt> from which the fourth components of the sextuples are selected
     * @param es the <tt>Iterable</tt> from which the fifth components of the sextuples are selected
     * @param fs the <tt>Iterable</tt> from which the sixth components of the sextuples are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @param <F> the type of the sixth <tt>Iterable</tt>'s elements
     * @return all sextuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, <tt>es</tt>, and
     * <tt>fs</tt>
     */
    public static @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    ) {
        if (
                isEmpty(as) ||
                        isEmpty(bs) ||
                        isEmpty(cs) ||
                        isEmpty(ds) ||
                        isEmpty(es) ||
                        isEmpty(fs)
                ) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        CachedIterable<D> dii = new CachedIterable<>(ds);
        CachedIterable<E> eii = new CachedIterable<>(es);
        CachedIterable<F> fii = new CachedIterable<>(fs);
        Function<BigInteger, Optional<Sextuple<A, B, C, D, E, F>>> f = bi -> {
            List<BigInteger> p = MathUtils.demux(6, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValueExact());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValueExact());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValueExact());
            if (!optE.isPresent()) return Optional.empty();
            assert p.get(5) != null;
            NullableOptional<F> optF = fii.get(p.get(5).intValueExact());
            if (!optF.isPresent()) return Optional.empty();
            return Optional.of(new Sextuple<A, B, C, D, E, F>(
                    optA.get(),
                    optB.get(),
                    optC.get(),
                    optD.get(),
                    optE.get(),
                    optF.get()
            ));
        };
        Predicate<Optional<Sextuple<A, B, C, D, E, F>>> lastSextuple = o -> {
            if (!o.isPresent()) return false;
            Sextuple<A, B, C, D, E, F> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            Optional<Boolean> lastC = cii.isLast(p.c);
            Optional<Boolean> lastD = dii.isLast(p.d);
            Optional<Boolean> lastE = eii.isLast(p.e);
            Optional<Boolean> lastF = fii.isLast(p.f);
            return lastA.isPresent() &&
                    lastB.isPresent() &&
                    lastC.isPresent() &&
                    lastD.isPresent() &&
                    lastE.isPresent() &&
                    lastF.isPresent() &&
                    lastA.get() &&
                    lastB.get() &&
                    lastC.get() &&
                    lastD.get() &&
                    lastE.get() &&
                    lastF.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Sextuple<A, B, C, D, E, F>>::isPresent,
                        stopAt(
                                lastSextuple,
                                (Iterable<Optional<Sextuple<A, B, C, D, E, F>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    /**
     * Returns all septuples of elements taken from seven <tt>Iterable</tt>s in such a way that all components grow as
     * the seventh root of the number of septuples iterated.
     *
     * <ul>
     *  <li><tt>as</tt> is non-null.</li>
     *  <li><tt>bs</tt> is non-null.</li>
     *  <li><tt>cs</tt> is non-null.</li>
     *  <li><tt>ds</tt> is non-null.</li>
     *  <li><tt>es</tt> is non-null.</li>
     *  <li><tt>fs</tt> is non-null.</li>
     *  <li><tt>gs</tt> is non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> containing all septuples of elements taken from seven
     *  <tt>Iterable</tt>s. The elements are ordered by following a Z-curve through the septuple space. The curve is
     *  computed by un-interleaving bits of successive integers.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>||<tt>es</tt>||<tt>fs</tt>||<tt>gs</tt>|
     *
     * @param as the <tt>Iterable</tt> from which the first components of the septuples are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the septuples are selected
     * @param cs the <tt>Iterable</tt> from which the third components of the septuples are selected
     * @param ds the <tt>Iterable</tt> from which the fourth components of the septuples are selected
     * @param es the <tt>Iterable</tt> from which the fifth components of the septuples are selected
     * @param fs the <tt>Iterable</tt> from which the sixth components of the septuples are selected
     * @param gs the <tt>Iterable</tt> from which the seventh components of the septuples are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @param <C> the type of the third <tt>Iterable</tt>'s elements
     * @param <D> the type of the fourth <tt>Iterable</tt>'s elements
     * @param <E> the type of the fifth <tt>Iterable</tt>'s elements
     * @param <F> the type of the sixth <tt>Iterable</tt>'s elements
     * @param <G> the type of the seventh <tt>Iterable</tt>'s elements
     * @return all septuples of elements from <tt>as</tt>, <tt>bs</tt>, <tt>cs</tt>, <tt>ds</tt>, <tt>es</tt>,
     * <tt>fs</tt>, and <tt>gs</tt>
     */
    public static @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    ) {
        if (
                isEmpty(as) ||
                        isEmpty(bs) ||
                        isEmpty(cs) ||
                        isEmpty(ds) ||
                        isEmpty(es) ||
                        isEmpty(fs) ||
                        isEmpty(gs)
                ) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        CachedIterable<D> dii = new CachedIterable<>(ds);
        CachedIterable<E> eii = new CachedIterable<>(es);
        CachedIterable<F> fii = new CachedIterable<>(fs);
        CachedIterable<G> gii = new CachedIterable<>(gs);
        Function<BigInteger, Optional<Septuple<A, B, C, D, E, F, G>>> f = bi -> {
            List<BigInteger> p = MathUtils.demux(7, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValueExact());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValueExact());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValueExact());
            if (!optE.isPresent()) return Optional.empty();
            assert p.get(5) != null;
            NullableOptional<F> optF = fii.get(p.get(5).intValueExact());
            if (!optF.isPresent()) return Optional.empty();
            assert p.get(6) != null;
            NullableOptional<G> optG = gii.get(p.get(6).intValueExact());
            if (!optG.isPresent()) return Optional.empty();
            return Optional.of(new Septuple<A, B, C, D, E, F, G>(
                    optA.get(),
                    optB.get(),
                    optC.get(),
                    optD.get(),
                    optE.get(),
                    optF.get(),
                    optG.get()
            ));
        };
        Predicate<Optional<Septuple<A, B, C, D, E, F, G>>> lastSeptuple = o -> {
            if (!o.isPresent()) return false;
            Septuple<A, B, C, D, E, F, G> p = o.get();
            Optional<Boolean> lastA = aii.isLast(p.a);
            Optional<Boolean> lastB = bii.isLast(p.b);
            Optional<Boolean> lastC = cii.isLast(p.c);
            Optional<Boolean> lastD = dii.isLast(p.d);
            Optional<Boolean> lastE = eii.isLast(p.e);
            Optional<Boolean> lastF = fii.isLast(p.f);
            Optional<Boolean> lastG = gii.isLast(p.g);
            return lastA.isPresent() &&
                    lastB.isPresent() &&
                    lastC.isPresent() &&
                    lastD.isPresent() &&
                    lastE.isPresent() &&
                    lastF.isPresent() &&
                    lastG.isPresent() &&
                    lastA.get() &&
                    lastB.get() &&
                    lastC.get() &&
                    lastD.get() &&
                    lastE.get() &&
                    lastF.get() &&
                    lastG.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Septuple<A, B, C, D, E, F, G>>::isPresent,
                        stopAt(
                                lastSeptuple,
                                (Iterable<Optional<Septuple<A, B, C, D, E, F, G>>>)
                                        map(bi -> f.apply(bi), P.naturalBigIntegers())
                        )
                )
        );
    }

    public static @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        CachedIterable<A> as = new CachedIterable<>(xs);
        CachedIterable<CachedIterable<B>> possibleBs = new CachedIterable<>(
                (Iterable<CachedIterable<B>>) map(x -> new CachedIterable<B>(f.apply(x)), xs)
        );
        Function<Pair<BigInteger, BigInteger>, Optional<Pair<A, B>>> p2p = p -> {
            assert p.a != null;
            assert p.b != null;
            NullableOptional<A> optA = as.get(p.a.intValueExact());
            if (!optA.isPresent()) return Optional.empty();
            NullableOptional<CachedIterable<B>> optBs = possibleBs.get(p.a.intValueExact());
            if (!optBs.isPresent()) return Optional.empty();
            CachedIterable<B> bs = optBs.get();
            NullableOptional<B> optB = bs.get(p.b.intValueExact());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<A, B>(optA.get(), optB.get()));
        };
        Predicate<Optional<Pair<A, B>>> lastPair = o -> {
            if (!o.isPresent()) return false;
            Pair<A, B> p = o.get();
            Optional<Boolean> lastA = as.isLast(p.a);
            if (!lastA.isPresent() || !lastA.get()) return false;
            if (possibleBs.size() == 0) return false;
            Optional<Boolean> lastB = possibleBs.lastSoFar().isLast(p.b);
            return lastB.isPresent() && lastB.get();
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<A, B>>::isPresent,
                        stopAt(lastPair, map(p2p, P.pairs(P.naturalBigIntegers())))
                )
        );
    }

    public static <T> Iterable<List<T>> lists(int size, Iterable<T> xs) {
        if (size == 0) {
            return Arrays.asList(new ArrayList<T>());
        }
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi ->
                ii.get(map(BigInteger::intValueExact, MathUtils.demux(size, bi)));
        return map(
                Optional::get,
                filter(
                        Optional<List<T>>::isPresent,
                        (Iterable<Optional<List<T>>>) map(bi -> f.apply(bi), P.naturalBigIntegers())
                )
        );
    }

    public static @NotNull <T> Iterable<List<T>> controlledListsAscending(@NotNull List<Iterable<T>> xss) {
        if (xss.size() == 0) return Arrays.asList(new ArrayList<T>());
        if (xss.size() == 1) return map(x -> Arrays.asList(x), xss.get(0));
        if (xss.size() == 2) return map(p -> Arrays.<T>asList(p.a, p.b), pairsAscending(xss.get(0), xss.get(1)));
        List<Iterable<T>> leftList = new ArrayList<>();
        List<Iterable<T>> rightList = new ArrayList<>();
        for (int i = 0; i < xss.size() / 2; i++) {
            leftList.add(xss.get(i));
        }
        for (int i = xss.size() / 2; i < xss.size(); i++) {
            rightList.add(xss.get(i));
        }
        Iterable<List<T>> leftLists = controlledListsAscending(leftList);
        Iterable<List<T>> rightLists = controlledListsAscending(rightList);
        return map(p -> toList(concat(p.a, p.b)), pairsAscending(leftLists, rightLists));
    }

    public static Iterable<String> strings(int size, Iterable<Character> cs) {
        return map(IterableUtils::charsToString, lists(size, cs));
    }

    public static Iterable<String> strings(int size, String s) {
        return map(IterableUtils::charsToString, lists(size, fromString(s)));
    }

    public Iterable<String> strings(int size) {
        return strings(size, P.characters());
    }

    public static @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), lists(2, xs));
    }

    public static @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), lists(3, xs));
    }

    public static @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), lists(4, xs));
    }

    public static @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                lists(5, xs)
        );
    }

    public static @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                lists(6, xs)
        );
    }

    public static @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                lists(7, xs)
        );
    }

    public static <T> Iterable<List<T>> lists(Iterable<T> xs) {
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi -> {
            if (bi.equals(BigInteger.ZERO)) {
                return Optional.of(new ArrayList<T>());
            }
            bi = bi.subtract(BigInteger.ONE);
            Pair<BigInteger, BigInteger> sizeIndex = MathUtils.logarithmicDemux(bi);
            int size = sizeIndex.b.intValueExact() + 1;
            return ii.get(map(BigInteger::intValueExact, MathUtils.demux(size, sizeIndex.a)));
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }
}
