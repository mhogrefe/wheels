package mho.haskellesque.iterables;

import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.math.RoundingMode;

public interface IterableProvider {
    public abstract @NotNull Iterable<Boolean> booleans();

    public abstract @NotNull Iterable<Ordering> orderings();

    public abstract @NotNull Iterable<RoundingMode> roundingModes();

    public abstract @NotNull Iterable<Byte> positiveBytes();

    public abstract @NotNull Iterable<Short> positiveShorts();

    public abstract @NotNull Iterable<Integer> positiveIntegers();

    public abstract @NotNull Iterable<Long> positiveLongs();

    public abstract @NotNull Iterable<BigInteger> positiveBigIntegers();

    public abstract @NotNull Iterable<Byte> negativeBytes();

    public abstract @NotNull Iterable<Short> negativeShorts();

    public abstract @NotNull Iterable<Integer> negativeIntegers();

    public abstract @NotNull Iterable<Long> negativeLongs();

    public abstract @NotNull Iterable<BigInteger> negativeBigIntegers();

    public abstract @NotNull Iterable<Byte> naturalBytes();

    public abstract @NotNull Iterable<Short> naturalShorts();

    public abstract @NotNull Iterable<Integer> naturalIntegers();

    public abstract @NotNull Iterable<Long> naturalLongs();

    public abstract @NotNull Iterable<BigInteger> naturalBigIntegers();

    public abstract @NotNull Iterable<Byte> bytes();

    public abstract @NotNull Iterable<Short> shorts();

    public abstract @NotNull Iterable<Integer> integers();

    public abstract @NotNull Iterable<Long> longs();

    public abstract @NotNull Iterable<BigInteger> bigIntegers();

    public abstract @NotNull Iterable<Character> asciiCharacters();

    public abstract @NotNull Iterable<Character> characters();

    public abstract @NotNull Iterable<Float> positiveOrdinaryFloats();

    public abstract @NotNull Iterable<Float> negativeOrdinaryFloats();

    public abstract @NotNull Iterable<Float> ordinaryFloats();

    public abstract @NotNull Iterable<Float> floats();

    public abstract @NotNull Iterable<Double> positiveOrdinaryDoubles();

    public abstract @NotNull Iterable<Double> negativeOrdinaryDoubles();

    public abstract @NotNull Iterable<Double> ordinaryDoubles();

    public abstract @NotNull Iterable<Double> doubles();

    public abstract @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs);

    public abstract @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    );

    public abstract @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs);

    public abstract @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    );

    public abstract @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    );

    public abstract @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    );

    public abstract @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    );

    public abstract @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    );

    public abstract @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs);
}
