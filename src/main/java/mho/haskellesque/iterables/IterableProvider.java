package mho.haskellesque.iterables;

import mho.haskellesque.ordering.Ordering;
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
}
