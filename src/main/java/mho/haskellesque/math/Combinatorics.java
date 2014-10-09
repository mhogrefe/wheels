package mho.haskellesque.math;

import mho.haskellesque.iterables.CachedIterable;
import mho.haskellesque.iterables.Exhaustive;
import mho.haskellesque.tuples.Pair;
import mho.haskellesque.tuples.Quadruple;
import mho.haskellesque.tuples.Triple;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * Various combinatorial functions and <tt>Iterable</tt>s.
 */
public class Combinatorics {
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
        for (BigInteger i = BigInteger.ONE; le(i, n); i = i.add(BigInteger.ONE)) {
            sf = sf.multiply(i);
            if (i.getLowestSetBit() != 0) {
                sf = sf.add(BigInteger.ONE);
            } else {
                sf = sf.subtract(BigInteger.ONE);
            }
        }
        return sf;
    }

    /**
     * Given two <tt>Iterable</tt>s, returns all ordered pairs of elements from these <tt>Iterable</tt>s in ascending
     * order. Both <tt>Iterable</tt>s must be finite; using long <tt>Iterable</tt>s is possible but discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be non-null.</li>
     *  <li>The result is a sorted list of distinct pairs such that if a<sub>1</sub> appears in the first slot of some
     *  pair and a<sub>2</sub> appears in the second slot of some pair, the pair (a<sub>1</sub>, a<sub>2</sub>) is also
     *  present.</li>
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
     * ascending order. All <tt>Iterable</tt>s must be finite; using long <tt>Iterable</tt>s is possible but
     * discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be non-null.</li>
     *  <li><tt>cs</tt> must be non-null.</li>
     *  <li>The result is a sorted list of distinct triples such that if a<sub>1</sub> appears in the first slot of
     *  some triple, a<sub>2</sub> appears in the second slot of some triple, and a<sub>3</sub> appears in the third
     *  slot of some triple, the triple (a<sub>1</sub>, a<sub>2</sub>, a<sub>3</sub>) is also present.</li>
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
     * ascending order. All <tt>Iterable</tt>s must be finite; using long <tt>Iterable</tt>s is possible but
     * discouraged.
     *
     * <ul>
     *  <li><tt>as</tt> must be non-null.</li>
     *  <li><tt>bs</tt> must be non-null.</li>
     *  <li><tt>cs</tt> must be non-null.</li>
     *  <li><tt>ds</tt> must be non-null.</li>
     *  <li>The result is a sorted list of distinct quadruples such that if a<sub>1</sub> appears in the first slot of
     *  some quadruple, a<sub>2</sub> appears in the second slot of some quadruple, a<sub>3</sub> appears in the third
     *  slot of some quadruple, and a<sub>4</sub> appears in the fourth slot of some quadruple, the quadruple
     *  (a<sub>1</sub>, a<sub>2</sub>, a<sub>3</sub>, a<sub>4</sub>) is also present.</li>
     * </ul>
     *
     * Result length is |<tt>as</tt>||<tt>bs</tt>||<tt>cs</tt>||<tt>ds</tt>
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

    public static <A, B> Iterable<Pair<A, B>> pairsExponentialOrder(Iterable<A> as, Iterable<B> bs) {
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        Function<BigInteger, Optional<Pair<A, B>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = BasicMath.exponentialDemux(bi);
            assert p.a != null;
            Optional<A> optA = aii.get(p.a.intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            Optional<B> optB = bii.get(p.b.intValue());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<A, B>(optA.get(), optB.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<A, B>>::isPresent,
                        (Iterable<Optional<Pair<A, B>>>) map(bi -> f.apply(bi), Exhaustive.BIG_INTEGERS)
                )
        );
    }

    public static <T> Iterable<Pair<T, T>> pairsExponentialOrder(Iterable<T> xs) {
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<Pair<T, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = BasicMath.exponentialDemux(bi);
            assert p.a != null;
            Optional<T> optA = ii.get(p.a.intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            Optional<T> optB = ii.get(p.b.intValue());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<T, T>(optA.get(), optB.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<T, T>>::isPresent,
                        (Iterable<Optional<Pair<T, T>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <A, B> Iterable<Pair<A, B>> pairs(Iterable<A> as, Iterable<B> bs) {
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        Function<BigInteger, Optional<Pair<A, B>>> f = bi -> {
            List<BigInteger> p = BasicMath.demux(2, bi);
            assert p.get(0) != null;
            Optional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            Optional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            return Optional.of(new Pair<A, B>(optA.get(), optB.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<A, B>>::isPresent,
                        (Iterable<Optional<Pair<A, B>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> triples(Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        Function<BigInteger, Optional<Triple<A, B, C>>> f = bi -> {
            List<BigInteger> p = BasicMath.demux(3, bi);
            assert p.get(0) != null;
            Optional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            Optional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            Optional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            return Optional.of(new Triple<A, B, C>(optA.get(), optB.get(), optC.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Triple<A, B, C>>::isPresent,
                        (Iterable<Optional<Triple<A, B, C>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        CachedIterable<D> dii = new CachedIterable<>(ds);
        Function<BigInteger, Optional<Quadruple<A, B, C, D>>> f = bi -> {
            List<BigInteger> p = BasicMath.demux(4, bi);
            assert p.get(0) != null;
            Optional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            Optional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            Optional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            Optional<D> optD = dii.get(p.get(3).intValue());
            if (!optD.isPresent()) return Optional.empty();
            return Optional.of(new Quadruple<A, B, C, D>(optA.get(), optB.get(), optC.get(), optD.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Quadruple<A, B, C, D>>::isPresent,
                        (Iterable<Optional<Quadruple<A, B, C, D>>>)
                                map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <T> Iterable<List<T>> lists(int size, Iterable<T> xs) {
        if (size == 0) {
            return Arrays.asList(new ArrayList<T>());
        }
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi -> ii.get(map(BigInteger::intValue, BasicMath.demux(size, bi)));
        return map(
                Optional::get,
                filter(
                        Optional<List<T>>::isPresent,
                        (Iterable<Optional<List<T>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <T> Iterable<List<T>> lists(Iterable<T> xs) {
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi -> {
            if (bi.equals(BigInteger.ZERO)) {
                return Optional.of(new ArrayList<T>());
            }
            bi = bi.subtract(BigInteger.ONE);
            Pair<BigInteger, BigInteger> sizeIndex = BasicMath.exponentialDemux(bi);
            int size = sizeIndex.b.intValue() + 1;
            return ii.get(map(BigInteger::intValue, BasicMath.demux(size, sizeIndex.a)));
        };
        return map(
                Optional::get,
                filter(
                        Optional<List<T>>::isPresent,
                        (Iterable<Optional<List<T>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
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
