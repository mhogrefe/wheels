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
    public @NotNull Iterable<Boolean> booleans();

    public @NotNull Iterable<Ordering> orderings();

    public @NotNull Iterable<RoundingMode> roundingModes();

    public @NotNull Iterable<Byte> range(byte a);

    public @NotNull Iterable<Short> range(short a);

    public @NotNull Iterable<Integer> range(int a);

    public @NotNull Iterable<Long> range(long a);

    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a);

    public @NotNull Iterable<Character> range(char a);

    public @NotNull Iterable<Byte> range(byte a, byte b);

    public @NotNull Iterable<Short> range(short a, short b);

    public @NotNull Iterable<Integer> range(int a, int b);

    public @NotNull Iterable<Long> range(long a, long b);

    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b);

    public @NotNull Iterable<Character> range(char a, char b);

    public @NotNull Iterable<Byte> rangeBy(byte a, byte i);

    public @NotNull Iterable<Short> rangeBy(short a, short i);

    public @NotNull Iterable<Integer> rangeBy(int a, int i);

    public @NotNull Iterable<Long> rangeBy(long a, long i);

    public @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i);

    public @NotNull Iterable<Character> rangeBy(char a, int i);

    public @NotNull Iterable<Byte> rangeBy(byte a, byte i, byte b);

    public @NotNull Iterable<Short> rangeBy(short a, short i, short b);

    public @NotNull Iterable<Integer> rangeBy(int a, int i, int b);

    public @NotNull Iterable<Long> rangeBy(long a, long i, long b);

    public @NotNull Iterable<BigInteger> rangeBy(
            @NotNull BigInteger a,
            @NotNull BigInteger i,
            @NotNull BigInteger b
    );

    public @NotNull Iterable<Character> rangeBy(char a, int i, char b);

    public @NotNull Iterable<Byte> positiveBytes();

    public @NotNull Iterable<Short> positiveShorts();

    public @NotNull Iterable<Integer> positiveIntegers();

    public @NotNull Iterable<Long> positiveLongs();

    public @NotNull Iterable<BigInteger> positiveBigIntegers();

    public @NotNull Iterable<Byte> negativeBytes();

    public @NotNull Iterable<Short> negativeShorts();

    public @NotNull Iterable<Integer> negativeIntegers();

    public @NotNull Iterable<Long> negativeLongs();

    public @NotNull Iterable<BigInteger> negativeBigIntegers();

    public @NotNull Iterable<Byte> naturalBytes();

    public @NotNull Iterable<Short> naturalShorts();

    public @NotNull Iterable<Integer> naturalIntegers();

    public @NotNull Iterable<Long> naturalLongs();

    public @NotNull Iterable<BigInteger> naturalBigIntegers();

    public @NotNull Iterable<Byte> bytes();

    public @NotNull Iterable<Short> shorts();

    public @NotNull Iterable<Integer> integers();

    public @NotNull Iterable<Long> longs();

    public @NotNull Iterable<BigInteger> bigIntegers();

    public @NotNull Iterable<Character> asciiCharacters();

    public @NotNull Iterable<Character> characters();

    public @NotNull Iterable<Float> positiveOrdinaryFloats();

    public @NotNull Iterable<Float> negativeOrdinaryFloats();

    public @NotNull Iterable<Float> ordinaryFloats();

    public @NotNull Iterable<Float> floats();

    public @NotNull Iterable<Double> positiveOrdinaryDoubles();

    public @NotNull Iterable<Double> negativeOrdinaryDoubles();

    public @NotNull Iterable<Double> ordinaryDoubles();

    public @NotNull Iterable<Double> doubles();

    public @NotNull Iterable<BigDecimal> positiveBigDecimals();

    public @NotNull Iterable<BigDecimal> negativeBigDecimals();

    public @NotNull Iterable<BigDecimal> bigDecimals();

    public @NotNull <T> Iterable<T> withNull(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs);

    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    );

    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsLogarithmic(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    );

    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs);

    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    );

    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    );

    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    );

    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    );

    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    );

    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs);

    public @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs);

    public @NotNull Iterable<String> strings(int size, @NotNull Iterable<Character> cs);

    public @NotNull Iterable<String> strings(int size);

    public @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs);

    public @NotNull Iterable<String> strings();
}
