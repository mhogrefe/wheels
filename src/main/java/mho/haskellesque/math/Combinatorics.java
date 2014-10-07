package mho.haskellesque.math;

import mho.haskellesque.iterables.Exhaustive;
import mho.haskellesque.iterables.IndexedIterable;
import mho.haskellesque.tuples.Pair;
import mho.haskellesque.tuples.Quadruple;
import mho.haskellesque.tuples.Triple;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

public class Combinatorics {
    public static BigInteger factorial(int n) {
        return productBigInteger(range(BigInteger.ONE, BigInteger.valueOf(n).add(BigInteger.ONE)));
    }

    public static BigInteger factorial(BigInteger n) {
        return productBigInteger(range(BigInteger.ONE, n.add(BigInteger.ONE)));
    }

    public static BigInteger subfactorial(int n) {
        if (n == 0) return BigInteger.ONE;
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        BigInteger c = b;
        for (int i = 1; i < n; i++) {
            c = BigInteger.valueOf(i).multiply(a.add(b));
            a = b;
            b = c;
        }
        return c;
    }

    public static BigInteger subfactorial(BigInteger n) {
        if (n.equals(BigInteger.ZERO)) return BigInteger.ONE;
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        BigInteger c = b;
        for (BigInteger i = BigInteger.ONE; lt(i, n); i = i.add(BigInteger.ONE)) {
            c = i.multiply(a.add(b));
            a = b;
            b = c;
        }
        return c;
    }

    public static <S, T> Iterable<Pair<S, T>> cartesianPairs(Iterable<S> fsts, Iterable<T> snds) {
        return concatMap(p -> zip(repeat(p.fst), p.snd), zip(fsts, repeat(snds)));
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> cartesianTriples(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(
                p -> new Triple<>(p.fst, p.snd.fst, p.snd.snd),
                cartesianPairs(as, (Iterable<Pair<B, C>>) cartesianPairs(bs, cs))
        );
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> cartesianQuadruples(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return map(
                p -> new Quadruple<>(p.fst.fst, p.fst.snd, p.snd.fst, p.snd.snd),
                cartesianPairs(
                        (Iterable<Pair<A, B>>)cartesianPairs(as, bs),
                        (Iterable<Pair<C, D>>) cartesianPairs(cs, ds)
                )
        );
    }

    public static <S, T> Iterable<Pair<S, T>> exponentialPairs(Iterable<S> fsts, Iterable<T> snds) {
        IndexedIterable<S> fstii = new IndexedIterable<>(fsts);
        IndexedIterable<T> sndii = new IndexedIterable<>(snds);
        Function<BigInteger, Optional<Pair<S, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = BasicMath.exponentialDemux(bi);
            assert p.fst != null;
            Optional<S> optFst = fstii.get(p.fst.intValue());
            if (!optFst.isPresent()) return Optional.empty();
            assert p.snd != null;
            Optional<T> optSnd = sndii.get(p.snd.intValue());
            if (!optSnd.isPresent()) return Optional.empty();
            return Optional.of(new Pair<S, T>(optFst.get(), optSnd.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<S, T>>::isPresent,
                        (Iterable<Optional<Pair<S, T>>>) map(bi -> f.apply(bi), Exhaustive.BIG_INTEGERS)
                )
        );
    }

    public static <T> Iterable<Pair<T, T>> exponentialPairs(Iterable<T> xs) {
        IndexedIterable<T> ii = new IndexedIterable<>(xs);
        Function<BigInteger, Optional<Pair<T, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = BasicMath.exponentialDemux(bi);
            assert p.fst != null;
            Optional<T> optFst = ii.get(p.fst.intValue());
            if (!optFst.isPresent()) return Optional.empty();
            assert p.snd != null;
            Optional<T> optSnd = ii.get(p.snd.intValue());
            if (!optSnd.isPresent()) return Optional.empty();
            return Optional.of(new Pair<T, T>(optFst.get(), optSnd.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<T, T>>::isPresent,
                        (Iterable<Optional<Pair<T, T>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <S, T> Iterable<Pair<S, T>> pairs(Iterable<S> fsts, Iterable<T> snds) {
        IndexedIterable<S> fstii = new IndexedIterable<>(fsts);
        IndexedIterable<T> sndii = new IndexedIterable<>(snds);
        Function<BigInteger, Optional<Pair<S, T>>> f = bi -> {
            List<BigInteger> p = BasicMath.demux(2, bi);
            assert p.get(0) != null;
            Optional<S> optFst = fstii.get(p.get(0).intValue());
            if (!optFst.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            Optional<T> optSnd = sndii.get(p.get(1).intValue());
            if (!optSnd.isPresent()) return Optional.empty();
            return Optional.of(new Pair<S, T>(optFst.get(), optSnd.get()));
        };
        return map(
                Optional::get,
                filter(
                        Optional<Pair<S, T>>::isPresent,
                        (Iterable<Optional<Pair<S, T>>>) map(bi -> f.apply(bi), Exhaustive.NATURAL_BIG_INTEGERS)
                )
        );
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> triples(Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        IndexedIterable<A> aii = new IndexedIterable<>(as);
        IndexedIterable<B> bii = new IndexedIterable<>(bs);
        IndexedIterable<C> cii = new IndexedIterable<>(cs);
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
        IndexedIterable<A> aii = new IndexedIterable<>(as);
        IndexedIterable<B> bii = new IndexedIterable<>(bs);
        IndexedIterable<C> cii = new IndexedIterable<>(cs);
        IndexedIterable<D> dii = new IndexedIterable<>(ds);
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
}
