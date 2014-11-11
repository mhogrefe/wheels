package mho.wheels.iterables;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IterableProvider {
    public abstract @NotNull Iterable<Boolean> booleans();

    public abstract @NotNull Iterable<Ordering> orderings();

    public abstract @NotNull Iterable<RoundingMode> roundingModes();

    public abstract @NotNull Iterable<Byte> range(byte a);

    public abstract @NotNull Iterable<Short> range(short a);

    public abstract @NotNull Iterable<Integer> range(int a);

    public abstract @NotNull Iterable<Long> range(long a);

    public abstract @NotNull Iterable<BigInteger> range(@NotNull BigInteger a);

    public abstract @NotNull Iterable<Character> range(char a);

    public abstract @NotNull Iterable<Byte> range(byte a, byte b);

    public abstract @NotNull Iterable<Short> range(short a, short b);

    public abstract @NotNull Iterable<Integer> range(int a, int b);

    public abstract @NotNull Iterable<Long> range(long a, long b);

    public abstract @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b);

    public abstract @NotNull Iterable<Character> range(char a, char b);

    public abstract @NotNull Iterable<Byte> rangeBy(byte a, byte i);

    public abstract @NotNull Iterable<Short> rangeBy(short a, short i);

    public abstract @NotNull Iterable<Integer> rangeBy(int a, int i);

    public abstract @NotNull Iterable<Long> rangeBy(long a, long i);

    public abstract @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i);

    public abstract @NotNull Iterable<Character> rangeBy(char a, int i);

    public abstract @NotNull Iterable<Byte> rangeBy(byte a, byte i, byte b);

    public abstract @NotNull Iterable<Short> rangeBy(short a, short i, short b);

    public abstract @NotNull Iterable<Integer> rangeBy(int a, int i, int b);

    public abstract @NotNull Iterable<Long> rangeBy(long a, long i, long b);

    public abstract @NotNull Iterable<BigInteger> rangeBy(
            @NotNull BigInteger a,
            @NotNull BigInteger i,
            @NotNull BigInteger b
    );

    public abstract @NotNull Iterable<Character> rangeBy(char a, int i, char b);

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

    public abstract @NotNull Iterable<BigDecimal> positiveBigDecimals();

    public abstract @NotNull Iterable<BigDecimal> negativeBigDecimals();

    public abstract @NotNull Iterable<BigDecimal> bigDecimals();

    public abstract @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs);

    public abstract @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
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

    public abstract @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs);

    public abstract @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs);

    public abstract @NotNull Iterable<String> strings(int size, @NotNull Iterable<Character> cs);

    public abstract @NotNull Iterable<String> strings(int size);

    public abstract @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs);

    public abstract @NotNull Iterable<String> strings();
}
