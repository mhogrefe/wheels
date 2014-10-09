package mho.haskellesque.math;

import mho.haskellesque.iterables.CachedIterable;
import mho.haskellesque.iterables.Exhaustive;
import mho.haskellesque.tuples.Pair;
import mho.haskellesque.tuples.Quadruple;
import mho.haskellesque.tuples.Triple;

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
    public static BigInteger factorial(int n) {
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
    public static BigInteger factorial(BigInteger n) {
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
    public static BigInteger subfactorial(int n) {
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
    public static BigInteger subfactorial(BigInteger n) {
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

    public static <A, B> Iterable<Pair<A, B>> pairsAscending(Iterable<A> as, Iterable<B> bs) {
        return concatMap(p -> zip(repeat(p.a), p.b), zip(as, repeat(bs)));
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> triplesAscending(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                pairsAscending(as, (Iterable<Pair<B, C>>) pairsAscending(bs, cs))
        );
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruplesAscending(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
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

    public static <T> Iterable<List<T>> listsAscending(int size, Iterable<T> xs) {
        return null;
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
