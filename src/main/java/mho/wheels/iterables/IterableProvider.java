package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.ordering.Ordering;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;

/**
 * This class provides {@code Iterables} for testing.
 */
public abstract strictfp class IterableProvider {
    /**
     * Returns a shallow copy of {@code this}.
     */
    public @NotNull IterableProvider copy() {
        return this;
    }

    /**
     * Returns a deep copy of {@code this}.
     */
    public @NotNull IterableProvider deepCopy() {
        return this;
    }

    /**
     * Resets {@code this} to its original state (if {@code this} has any state).
     */
    public void reset() {}

    /**
     * Returns a shallow copy of {@code this} with a given primary scale.
     *
     * @param scale the primary scale.
     */
    public @NotNull IterableProvider withScale(int scale) {
        return this;
    }

    /**
     * Returns a shallow copy of {@code this} with a given secondary scale.
     *
     * @param secondaryScale the secondary scale.
     */
    public @NotNull IterableProvider withSecondaryScale(int secondaryScale) {
        return this;
    }

    /**
     * Generates {@code Boolean}s.
     */
    public abstract @NotNull Iterable<Boolean> booleans();

    /**
     * Generates {@link Ordering}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Ordering> orderingsIncreasing() {
        return orderings();
    }

    /**
     * Generates {@link Ordering}s.
     */
    public abstract @NotNull Iterable<Ordering> orderings();

    /**
     * Generates {@code RoundingMode}s.
     */
    public abstract @NotNull Iterable<RoundingMode> roundingModes();

    /**
     * Generates values from a list, sampled uniformly if applicable.
     *
     * @param xs the source list
     */
    public abstract @NotNull <T> Iterable<T> uniformSample(@NotNull List<T> xs);

    /**
     * Generates {@code Character}s from a {@code String}, sampled uniformly if applicable.
     *
     * @param s the source {@code String}
     */
    public abstract @NotNull Iterable<Character> uniformSample(@NotNull String s);

    /**
     * Generates {@code Byte}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Byte> bytesIncreasing() {
        return bytes();
    }

    /**
     * Generates {@code Short}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Short> shortsIncreasing() {
        return shorts();
    }

    /**
     * Generates {@code Integer}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Integer> integersIncreasing() {
        return integers();
    }

    /**
     * Generates {@code Long}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Long> longsIncreasing() {
        return longs();
    }

    /**
     * Generates positive {@code Byte}s.
     */
    public abstract @NotNull Iterable<Byte> positiveBytes();

    /**
     * Generates positive {@code Short}s.
     */
    public abstract @NotNull Iterable<Short> positiveShorts();

    /**
     * Generates positive {@code Integer}s.
     */
    public abstract @NotNull Iterable<Integer> positiveIntegers();

    /**
     * Generates positive {@code Long}s.
     */
    public abstract @NotNull Iterable<Long> positiveLongs();

    /**
     * Generates positive {@code BigInteger}s.
     */
    public abstract @NotNull Iterable<BigInteger> positiveBigIntegers();

    /**
     * Generates negative {@code Byte}s.
     */
    public abstract @NotNull Iterable<Byte> negativeBytes();

    /**
     * Generates negative {@code Short}s.
     */
    public abstract @NotNull Iterable<Short> negativeShorts();

    /**
     * Generates negative {@code Integer}s.
     */
    public abstract @NotNull Iterable<Integer> negativeIntegers();

    /**
     * Generates negative {@code Long}s.
     */
    public abstract @NotNull Iterable<Long> negativeLongs();

    /**
     * Generates negative {@code BigInteger}s.
     */
    public abstract @NotNull Iterable<BigInteger> negativeBigIntegers();

    /**
     * Generates nonzero {@code Byte}s.
     */
    public @NotNull Iterable<Byte> nonzeroBytes() {
        return filter(b -> b != 0, bytes());
    }

    /**
     * Generates nonzero {@code Short}s.
     */
    public @NotNull Iterable<Short> nonzeroShorts() {
        return filter(s -> s != 0, shorts());
    }

    /**
     * Generates nonzero {@code Integer}s.
     */
    public @NotNull Iterable<Integer> nonzeroIntegers() {
        return filter(i -> i != 0, integers());
    }

    /**
     * Generates nonzero {@code Long}s.
     */
    public @NotNull Iterable<Long> nonzeroLongs() {
        return filter(l -> l != 0, longs());
    }

    /**
     * Generates nonzero {@code BigInteger}s.
     */
    public abstract @NotNull Iterable<BigInteger> nonzeroBigIntegers();

    /**
     * Generates natural {@code Byte}s (including 0).
     */
    public abstract @NotNull Iterable<Byte> naturalBytes();

    /**
     * Generates natural {@code Short}s (including 0).
     */
    public abstract @NotNull Iterable<Short> naturalShorts();

    /**
     * Generates natural {@code Integer}s (including 0).
     */
    public abstract @NotNull Iterable<Integer> naturalIntegers();

    /**
     * Generates natural {@code Long}s (including 0).
     */
    public abstract @NotNull Iterable<Long> naturalLongs();

    /**
     * Generates natural {@code BigInteger}s (including 0).
     */
    public abstract @NotNull Iterable<BigInteger> naturalBigIntegers();

    /**
     * Generates {@code Byte}s.
     */
    public abstract @NotNull Iterable<Byte> bytes();

    /**
     * Generates {@code Short}s.
     */
    public abstract @NotNull Iterable<Short> shorts();

    /**
     * Generates {@code Integer}s.
     */
    public abstract @NotNull Iterable<Integer> integers();

    /**
     * Generates {@code Long}s.
     */
    public abstract @NotNull Iterable<Long> longs();

    /**
     * Generates {@code BigInteger}s.
     */
    public abstract @NotNull Iterable<BigInteger> bigIntegers();

    /**
     * Generates ASCII {@code Character}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Character> asciiCharactersIncreasing() {
        return asciiCharacters();
    }

    /**
     * Generates ASCII {@code Character}s.
     */
    public abstract @NotNull Iterable<Character> asciiCharacters();

    /**
     * Generates {@code Character}s, in increasing order if applicable.
     */
    public @NotNull Iterable<Character> charactersIncreasing() {
        return characters();
    }

    /**
     * Generates {@code Character}s.
     */
    public abstract @NotNull Iterable<Character> characters();

    /**
     * Generates positive {@code Integer}s from a geometric distribution (if applicable).
     */
    public @NotNull Iterable<Integer> positiveIntegersGeometric() {
        return positiveIntegers();
    }

    /**
     * Generates negative {@code Integer}s from a geometric distribution (if applicable).
     */
    public @NotNull Iterable<Integer> negativeIntegersGeometric() {
        return negativeIntegers();
    }

    /**
     * Generates nonzero {@code Integer}s from a geometric distribution (if applicable).
     */
    public @NotNull Iterable<Integer> nonzeroIntegersGeometric() {
        return nonzeroIntegers();
    }

    /**
     * Generates natural {@code Integer}s (including 0) from a geometric distribution (if applicable).
     */
    public @NotNull Iterable<Integer> naturalIntegersGeometric() {
        return naturalIntegers();
    }

    /**
     * Generates {@code Integer}s from a geometric distribution (if applicable).
     */
    public @NotNull Iterable<Integer> integersGeometric() {
        return integers();
    }

    /**
     * Generates positive {@code Integer}s greater than or equal to a given value from a geometric distribution (if
     * applicable).
     *
     * @param a the inclusive lower bound of the generated {@code Integer}s
     */
    public @NotNull Iterable<Integer> rangeUpGeometric(int a) {
        return rangeUp(a);
    }

    /**
     * Generates positive {@code Integer}s less than or equal to a given value from a geometric distribution (if
     * applicable).
     *
     * @param a the inclusive upper bound of the generated {@code Integer}s
     */
    public @NotNull Iterable<Integer> rangeDownGeometric(int a) {
        return rangeDown(a);
    }

    /**
     * Generates {@code Byte}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code Byte}s
     */
    public abstract @NotNull Iterable<Byte> rangeUp(byte a);

    /**
     * Generates {@code Short}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code Short}s
     */
    public abstract @NotNull Iterable<Short> rangeUp(short a);

    /**
     * Generates {@code Integer}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code Integer}s
     */
    public abstract @NotNull Iterable<Integer> rangeUp(int a);

    /**
     * Generates {@code Long}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code Long}s
     */
    public abstract @NotNull Iterable<Long> rangeUp(long a);

    /**
     * Generates {@code BigInteger}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code BigInteger}s
     */
    public abstract @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a);

    /**
     * Generates {@code Character}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code Character}s
     */
    public abstract @NotNull Iterable<Character> rangeUp(char a);

    /**
     * Generates {@code Byte}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code Byte}s
     */
    public abstract @NotNull Iterable<Byte> rangeDown(byte a);

    /**
     * Generates {@code Short}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code Short}s
     */
    public abstract @NotNull Iterable<Short> rangeDown(short a);

    /**
     * Generates {@code Integer}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code Integer}s
     */
    public abstract @NotNull Iterable<Integer> rangeDown(int a);

    /**
     * Generates {@code Long}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code Long}s
     */
    public abstract @NotNull Iterable<Long> rangeDown(long a);

    /**
     * Generates {@code BigInteger}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code BigInteger}s
     */
    public abstract @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a);

    /**
     * Generates {@code Character}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code Character}s
     */
    public abstract @NotNull Iterable<Character> rangeDown(char a);

    /**
     * Generates {@code Byte}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code Byte}s
     * @param b the inclusive upper bound of the generated {@code Byte}s
     */
    public abstract @NotNull Iterable<Byte> range(byte a, byte b);

    /**
     * Generates {@code Short}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code Short}s
     * @param b the inclusive upper bound of the generated {@code Short}s
     */
    public abstract @NotNull Iterable<Short> range(short a, short b);

    /**
     * Generates {@code Integer}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code Integer}s
     * @param b the inclusive upper bound of the generated {@code Integer}s
     */
    public abstract @NotNull Iterable<Integer> range(int a, int b);

    /**
     * Generates {@code Long}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code Long}s
     * @param b the inclusive upper bound of the generated {@code Long}s
     */
    public abstract @NotNull Iterable<Long> range(long a, long b);

    /**
     * Generates {@code BigInteger}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code BigInteger}s
     * @param b the inclusive upper bound of the generated {@code BigInteger}s
     */
    public abstract @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b);

    /**
     * Generates {@code Character}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code Character}s
     * @param b the inclusive upper bound of the generated {@code Character}s
     */
    public abstract @NotNull Iterable<Character> range(char a, char b);

    /**
     * Generates positive {@link BinaryFraction}s.
     */
    public abstract @NotNull Iterable<BinaryFraction> positiveBinaryFractions();

    /**
     * Generates negative {@link BinaryFraction}s.
     */
    public abstract @NotNull Iterable<BinaryFraction> negativeBinaryFractions();

    /**
     * Generates nonzero {@link BinaryFraction}s.
     */
    public abstract @NotNull Iterable<BinaryFraction> nonzeroBinaryFractions();

    /**
     * Generates {@link BinaryFraction}s.
     */
    public abstract @NotNull Iterable<BinaryFraction> binaryFractions();

    /**
     * Generates {@code BinaryFraction}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code BinaryFraction}s
     */
    public abstract @NotNull Iterable<BinaryFraction> rangeUp(@NotNull BinaryFraction a);

    /**
     * Generates {@code BinaryFraction}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code BinaryFraction}s
     */
    public abstract @NotNull Iterable<BinaryFraction> rangeDown(@NotNull BinaryFraction a);

    /**
     * Generates {@link BinaryFraction}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code BinaryFraction}s
     * @param b the inclusive upper bound of the generated {@code BinaryFraction}s
     */
    public abstract @NotNull Iterable<BinaryFraction> range(@NotNull BinaryFraction a, @NotNull BinaryFraction b);

    /**
     * Generates positive {@code Float}s, including {@code Infinity} but not positive zero.
     */
    public abstract @NotNull Iterable<Float> positiveFloats();

    /**
     * Generates negative {@code Float}s, including {@code -Infinity} but not negative zero.
     */
    public abstract @NotNull Iterable<Float> negativeFloats();

    /**
     * Generates nonzero {@code Float}s, including {@code NaN}, positive and negative zeros, {@code Infinity}, and
     * {@code -Infinity}.
     */
    public abstract @NotNull Iterable<Float> nonzeroFloats();

    /**
     * Generates {@code Float}s.
     */
    public abstract @NotNull Iterable<Float> floats();

    /**
     * Generates positive {@code Double}s, including {@code Infinity} but not positive zero.
     */
    public abstract @NotNull Iterable<Double> positiveDoubles();

    /**
     * Generates negative {@code Double}s, including {@code -Infinity} but not negative zero.
     */
    public abstract @NotNull Iterable<Double> negativeDoubles();

    /**
     * Generates nonzero {@code Double}s, including {@code NaN}, positive and negative zeros, {@code Infinity}, and
     * {@code -Infinity}.
     */
    public abstract @NotNull Iterable<Double> nonzeroDoubles();

    /**
     * Generates {@code Double}s, including {@code NaN}, positive and negative zeros, {@code Infinity}, and
     * {@code -Infinity}.
     */
    public abstract @NotNull Iterable<Double> doubles();

    /**
     * Generates positive {@code Float}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Float> positiveFloatsUniform() {
        return positiveFloats();
    }

    /**
     * Generates negative {@code Float}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Float> negativeFloatsUniform() {
        return negativeFloats();
    }

    /**
     * Generates nonzero {@code Float}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Float> nonzeroFloatsUniform() {
        return nonzeroFloats();
    }

    /**
     * Generates {@code Float}s selected from a distribution which approximates a uniform distribution over the reals
     * (if applicable).
     */
    public @NotNull Iterable<Float> floatsUniform() {
        return floats();
    }

    /**
     * Generates positive {@code Double}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Double> positiveDoublesUniform() {
        return positiveDoubles();
    }

    /**
     * Generates negative {@code Double}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Double> negativeDoublesUniform() {
        return negativeDoubles();
    }

    /**
     * Generates nonzero {@code Double}s selected from a distribution which approximates a uniform distribution over
     * the reals (if applicable).
     */
    public @NotNull Iterable<Double> nonzeroDoublesUniform() {
        return nonzeroDoubles();
    }

    /**
     * Generates {@code Double}s selected from a distribution which approximates a uniform distribution over the reals
     * (if applicable).
     */
    public @NotNull Iterable<Double> doublesUniform() {
        return doubles();
    }

    /**
     * Generates {@code float}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code float}s
     */
    public abstract @NotNull Iterable<Float> rangeUp(float a);

    /**
     * Generates {@code float}s less than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code float}s
     */
    public abstract @NotNull Iterable<Float> rangeDown(float a);

    /**
     * Generates {@code float}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code float}s
     * @param b the inclusive upper bound of the generated {@code float}s
     */
    public abstract @NotNull Iterable<Float> range(float a, float b);

    /**
     * Generates {@code double}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code double}s
     */
    public abstract @NotNull Iterable<Double> rangeUp(double a);

    /**
     * Generates {@code double}s less than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code double}s
     */
    public abstract @NotNull Iterable<Double> rangeDown(double a);

    /**
     * Generates {@code double}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code double}s
     * @param b the inclusive upper bound of the generated {@code double}s
     */
    public abstract @NotNull Iterable<Double> range(double a, double b);

    /**
     * Generates positive {@code BigDecimal}s.
     */
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(positiveBigIntegers(), integers()));
    }

    /**
     * Generates negative {@code BigDecimal}s.
     */
    public Iterable<BigDecimal> negativeBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(negativeBigIntegers(), integers()));
    }

    /**
     * Generates nonzero {@code BigDecimal}s.
     */
    public @NotNull Iterable<BigDecimal> nonzeroBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(nonzeroBigIntegers(), integers()));
    }

    /**
     * Generates {@code BigDecimal}s.
     */
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(bigIntegers(), integers()));
    }

    /**
     * Generates positive {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     */
    public abstract @NotNull Iterable<BigDecimal> positiveCanonicalBigDecimals();

    /**
     * Generates negative {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     */
    public abstract @NotNull Iterable<BigDecimal> negativeCanonicalBigDecimals();

    /**
     * Generates nonzero {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     */
    public abstract @NotNull Iterable<BigDecimal> nonzeroCanonicalBigDecimals();

    /**
     * Generates {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     */
    public abstract @NotNull Iterable<BigDecimal> canonicalBigDecimals();

    /**
     * Generates {@code BigDecimal}s greater than or equal to a given value.
     *
     * @param a the inclusive lower bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> rangeUp(@NotNull BigDecimal a);

    /**
     * Generates {@code BigDecimal}s less than or equal to a given value.
     *
     * @param a the inclusive upper bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> rangeDown(@NotNull BigDecimal a);

    /**
     * Generates {@code BigDecimal}s between {@code a} and {@code b}, inclusive.
     *
     * @param a the inclusive lower bound of the generated {@code BigDecimal}s
     * @param b the inclusive upper bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> range(@NotNull BigDecimal a, @NotNull BigDecimal b);

    /**
     * Generates {@code BigDecimal}s greater than or equal to a given value, in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     *
     * @param a the inclusive lower bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> rangeUpCanonical(@NotNull BigDecimal a);

    /**
     * Generates {@code BigDecimal}s less than or equal to a given value, in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     *
     * @param a the inclusive upper bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> rangeDownCanonical(@NotNull BigDecimal a);

    /**
     * Generates {@code BigDecimal}s between {@code a} and {@code b}, inclusive, in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}).
     *
     * @param a the inclusive lower bound of the generated {@code BigDecimal}s
     * @param b the inclusive upper bound of the generated {@code BigDecimal}s
     */
    public abstract @NotNull Iterable<BigDecimal> rangeCanonical(@NotNull BigDecimal a, @NotNull BigDecimal b);

    /**
     * Generates all the elements in a given {@code Iterable}, along with an extra element.
     *
     * @param x an extra element
     * @param xs an {@code Iterable}
     * @param <T> the type of element contained in {@code xs}
     */
    public abstract @NotNull <T> Iterable<T> withElement(@Nullable T x, @NotNull Iterable<T> xs);

    /**
     * Generates all the elements in a given {@code Iterable}, along with null.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of element contained in {@code xs}
     * @return an {@code Iterable} including null and every element in {@code xs}
     */
    public @NotNull <T> Iterable<T> withNull(@NotNull Iterable<T> xs) {
        return withElement(null, xs);
    }

    /**
     * Generates nonempty {@link Optional}s; the result contains wrapped values of {@code xs}.
     *
     * @param xs an {@code Iterable}.
     * @param <T> the type of element contained in {@code xs}
     */
    public @NotNull <T> Iterable<Optional<T>> nonEmptyOptionals(@NotNull Iterable<T> xs) {
        return map(Optional::of, xs);
    }

    /**
     * Generates {@link Optional}s; the result contains wrapped values of {@code xs}, along with the empty
     * {@code Optional}.
     *
     * @param xs an {@code Iterable}.
     * @param <T> the type of element contained in {@code xs}
     */
    public @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs) {
        return withElement(Optional.<T>empty(), map(Optional::of, xs));
    }

    /**
     * Generates nonempty {@link NullableOptional}s; the result contains wrapped values of {@code xs}.
     *
     * @param xs an {@code Iterable}.
     * @param <T> the type of element contained in {@code xs}
     */
    public @NotNull <T> Iterable<NullableOptional<T>> nonEmptyNullableOptionals(@NotNull Iterable<T> xs) {
        return map(NullableOptional::of, xs);
    }

    /**
     * Generates {@link NullableOptional}s; the result contains wrapped values of {@code xs}, along with the empty
     * {@code NullableOptional}.
     *
     * @param xs an {@code Iterable}.
     * @param <T> the type of element contained in {@code xs}
     */
    public @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs) {
        return withElement(NullableOptional.<T>empty(), map(NullableOptional::of, xs));
    }

    /**
     * Generates pairs of values where the second value depends on the first.
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(xs, f);
    }

    /**
     * Generates pairs of values where the second value depends on the first. There must be an infinite number of
     * possible first values, and every first value must be associated with an infinite number of possible second
     * values.
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an infinite {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public abstract @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfinite(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    );

    /**
     * Generates pairs of values where the second value depends on the first and the second value grows linearly, but
     * the first grows logarithmically (if applicable). There must be an infinite number of possible first values, and
     * every first value must be associated with an infinite number of possible second values.
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an infinite {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfiniteLogarithmicOrder(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(xs, f);
    }

    /**
     * Generates pairs of values where the second value depends on the first and the second value grows as
     * O(n<sup>2/3</sup>), but the first grows as O(n<sup>1/3</sup>) (if applicable). There must be an infinite number
     * of possible first values, and every first value must be associated with an infinite number of possible second
     * values.
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an infinite {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfiniteSquareRootOrder(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(xs, f);
    }

    /**
     * Generates pairs of elements where the first component grows linearly but the second grows logarithmically (if
     * applicable).
     *
     * @param as the source of values in the first slot
     * @param bs the source of values in the second slot
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairs(as, bs);
    }

    /**
     * Generates pairs of elements where the first component grows linearly but the second grows logarithmically (if
     * applicable).
     *
     * @param xs the source of values
     * @param <T> the type of values in the both slots of the result pairs
     */
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return pairs(xs);
    }

    /**
     * Generates pairs of elements where the first component grows as O(n<sup>2/3</sup>) but the second grows as
     * O(n<sup>1/3</sup>) (if applicable).
     *
     * @param as the source of values in the first slot
     * @param bs the source of values in the second slot
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsSquareRootOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairs(as, bs);
    }

    /**
     * Generates pairs of elements where the first component grows as O(n<sup>2/3</sup>) but the second grows as
     * O(n<sup>1/3</sup>) (if applicable).
     *
     * @param xs the source of values
     * @param <T> the type of values in the both slots of the result pairs
     */
    public @NotNull <T> Iterable<Pair<T, T>> pairsSquareRootOrder(@NotNull Iterable<T> xs) {
        return pairs(xs);
    }

    /**
     * Generates all permutations of a {@code List}.
     *
     * @param xs a list of elements
     * @param <T> the type of values in the permutations
     */
    public abstract @NotNull <T> Iterable<List<T>> permutationsFinite(@NotNull List<T> xs);

    /**
     * Generates all permutations of a {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringPermutations(@NotNull String s) {
        return map(IterableUtils::charsToString, permutationsFinite(toList(s)));
    }

    /**
     * Generates permutations of an {@code Iterable}. If the {@code Iterable} is finite, all permutations are
     * generated; if it is infinite, then only permutations that are equal to the identity except in a finite prefix
     * are generated.
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the permutations
     */
    public abstract @NotNull <T> Iterable<Iterable<T>> prefixPermutations(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code List}s of a given size containing elements from a given {@code List}. The {@code List}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> listsLex(int size, @NotNull List<T> xs) {
        return lists(size, uniformSample(xs));
    }

    /**
     * Given two {@code Iterable}s, returns all {@code Pair}s of elements from these {@code Iterable}s. The
     * {@code Pair}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     */
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLex(@NotNull Iterable<A> as, @NotNull List<B> bs) {
        return pairs(as, uniformSample(bs));
    }

    /**
     * Given three {@code Iterable}s, returns all {@code Triple}s of elements from these {@code Iterable}s. The
     * {@code Triple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     */
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs
    ) {
        return triples(as, uniformSample(bs), uniformSample(cs));
    }

    /**
     * Given four {@code Iterable}s, returns all {@code Quadruple}s of elements from these {@code Iterable}s. The
     * {@code Quadruple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     */
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds
    ) {
        return quadruples(as, uniformSample(bs), uniformSample(cs), uniformSample(ds));
    }

    /**
     * Given five {@code Iterable}s, returns all {@code Quintuple}s of elements from these {@code Iterable}s. The
     * {@code Quintuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     */
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es
    ) {
        return quintuples(as, uniformSample(bs), uniformSample(cs), uniformSample(ds), uniformSample(es));
    }

    /**
     * Given six {@code Iterable}s, returns all {@code Sextuple}s of elements from these {@code Iterable}s. The
     * {@code Sextuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     */
    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es,
            @NotNull List<F> fs
    ) {
        return sextuples(
                as,
                uniformSample(bs),
                uniformSample(cs),
                uniformSample(ds),
                uniformSample(es),
                uniformSample(fs)
        );
    }

    /**
     * Given seven {@code Iterable}s, returns all {@code Septuple}s of elements from these {@code Iterable}s. The
     * {@code Septuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param gs the seventh {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @param <G> the type of the seventh {@code Iterable}'s elements
     */
    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es,
            @NotNull List<F> fs,
            @NotNull List<G> gs
    ) {
        return septuples(
                as,
                uniformSample(bs),
                uniformSample(cs),
                uniformSample(ds),
                uniformSample(es),
                uniformSample(fs),
                uniformSample(gs)
        );
    }

    /**
     * Generates all {@code String}s of a given size containing characters from a given {@code String}. The
     * {@code String}s are ordered lexicographically, matching the order given by the original {@code String}.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringsLex(int size, @NotNull String s) {
        return strings(size, s);
    }

    /**
     * Generates all {@code List}s containing elements from a given {@code Iterable}. The {@code List}s are ordered in
     * shortlex order (by length, then lexicographically), matching the order given by the original {@code Iterable}.
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public <T> Iterable<List<T>> listsShortlex(@NotNull List<T> xs) {
        return lists(uniformSample(xs));
    }

    /**
     * Generates all {@code String}s containing characters from a given {@code String}. The {@code String}s are ordered
     * in shortlex order (by length, then lexicographically), matching the order given by the original {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringsShortlex(@NotNull String s) {
        return strings(s);
    }

    /**
     * Generates all {@code List}s with a minimum size containing elements from a given {@code Iterable}. The
     * {@code List}s are ordered in shortlex order (by length, then lexicographically), matching the order given by the
     * original {@code Iterable}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> listsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        return listsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all {@code String}s with a minimum size containing characters from a given {@code String}. The
     * {@code String}s are ordered in shortlex order (by length, then lexicographically), matching the order given by
     * the original {@code String}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringsShortlexAtLeast(int minSize, @NotNull String s) {
        return stringsAtLeast(minSize, s);
    }

    /**
     * Generates all {@code List}s of a given size containing elements from a given {@code Iterable}.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs);

    /**
     * Given two {@code Iterable}s, generates all {@code Pair}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs);

    /**
     * Generates all {@code Pair}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs);

    /**
     * Given three {@code Iterable}s, generates all {@code Triple}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    );

    /**
     * Generates all {@code Triple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs);

    /**
     * Given four {@code Iterable}s, generates all {@code Quadruple}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    );

    /**
     * Generates all {@code Quadruple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs);

    /**
     * Given five {@code Iterable}s, generates all {@code Quintuple}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    );

    /**
     * Generates all {@code Quintuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs);

    /**
     * Given six {@code Iterable}s, generates all {@code Sextuple}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    );

    /**
     * Generates all {@code Sextuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs);

    /**
     * Given seven {@code Iterable}s, generates all {@code Septuple}s of elements from these {@code Iterable}s.
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param gs the seventh {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @param <G> the type of the seventh {@code Iterable}'s elements
     */
    public abstract @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    );

    /**
     * Generates all {@code Septuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s of a given size containing characters from a given {@code String}.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> strings(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, lists(size, uniformSample(s)));
    }

    /**
     * Generates all {@code String}s of a given size.
     *
     * @param size the length of each of the generated {@code String}s
     */
    public @NotNull Iterable<String> strings(int size) {
        return map(IterableUtils::charsToString, lists(size, characters()));
    }

    /**
     * Generates all {@code List}s of a containing elements from a given {@code Iterable}.
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s of a containing characters from a given {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> strings(@NotNull String s) {
        return map(IterableUtils::charsToString, lists(uniformSample(s)));
    }

    /**
     * Generates all {@code String}s.
     */
    public @NotNull Iterable<String> strings() {
        return map(IterableUtils::charsToString, lists(characters()));
    }

    /**
     * Generates all {@code List}s with a minimum size containing elements from a given {@code Iterable}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s with a minimum size containing characters from a given {@code String}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringsAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, listsAtLeast(minSize, uniformSample(s)));
    }

    /**
     * Generates all {@code String}s with a minimum size.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     */
    public @NotNull Iterable<String> stringsAtLeast(int minSize) {
        return map(IterableUtils::charsToString, listsAtLeast(minSize, characters()));
    }

    /**
     * Generates all {@code List}s of a given size containing elements from a given {@code List} with no repetitions.
     * The {@code List}s are ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> distinctListsLex(int size, @NotNull List<T> xs) {
        return distinctLists(size, uniformSample(xs));
    }

    /**
     * Generates all {@code Pair}s of elements from a {@code List} with no repetitions. The {@code Pair}s are ordered
     * lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Pair<T, T>> distinctPairsLex(@NotNull List<T> xs) {
        return distinctPairs(uniformSample(xs));
    }

    /**
     * Generates all {@code Triple}s of elements from a {@code List} with no repetitions. The {@code Triple}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Triple<T, T, T>> distinctTriplesLex(@NotNull List<T> xs) {
        return distinctTriples(uniformSample(xs));
    }

    /**
     * Generates all {@code Quadruple}s of elements from a {@code List} with no repetitions. The {@code Quadruple}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> distinctQuadruplesLex(@NotNull List<T> xs) {
        return distinctQuadruples(uniformSample(xs));
    }

    /**
     * Generates all {@code Quintuple}s of elements from a {@code List} with no repetitions. The {@code Quintuple}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> distinctQuintuplesLex(@NotNull List<T> xs) {
        return distinctQuintuples(uniformSample(xs));
    }

    /**
     * Generates all {@code Sextuple}s of elements from a {@code List} with no repetitions. The {@code Sextuple}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> distinctSextuplesLex(@NotNull List<T> xs) {
        return distinctSextuples(uniformSample(xs));
    }

    /**
     * Generates all {@code Septuple}s of elements from a {@code List} with no repetitions. The {@code Septuple}s are
     * ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> distinctSeptuplesLex(@NotNull List<T> xs) {
        return distinctSeptuples(uniformSample(xs));
    }

    /**
     * Generates all {@code String}s of a given size containing characters from a given {@code String} with no
     * repetitions. The {@code String}s are ordered lexicographically, matching the order given by the original
     * {@code String}.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsLex(int size, @NotNull String s) {
        return distinctStrings(size, s);
    }

    /**
     * Generates all {@code List}s containing elements from a given {@code List} with no repetitions. The {@code List}s
     * are ordered lexicographically, matching the order given by the original {@code List}.
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> distinctListsLex(@NotNull List<T> xs) {
        return distinctLists(uniformSample(xs));
    }

    /**
     * Generates all {@code String}s containing characters from a given {@code String} with no repetitions. The
     * {@code String}s are ordered lexicographically, matching the order given by the original {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsLex(@NotNull String s) {
        return distinctStrings(s);
    }

    /**
     * Generates all {@code List}s with a minimum size containing elements from a given {@code List} with no
     * repetitions. The {@code List}s are ordered lexicographically, matching the order given by the original
     * {@code List}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> distinctListsLexAtLeast(int minSize, @NotNull List<T> xs) {
        return distinctListsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all {@code String}s with a minimum size containing characters from a given {@code String} with no
     * repetitions. The {@code String}s are ordered lexicographically, matching the order given by the original
     * {@code String}.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsLexAtLeast(int minSize, @NotNull String s) {
        return distinctStringsAtLeast(minSize, s);
    }

    /**
     * Generates all {@code List}s containing elements from a given {@code Iterable} with no repetitions. The
     * {@code List}s are ordered in shortlex order (by length, then lexicographically), matching the order given by the
     * original {@code Iterable}.
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> distinctListsShortlex(@NotNull List<T> xs) {
        return distinctLists(uniformSample(xs));
    }

    /**
     * Generates all {@code String}s containing characters from a given {@code String} with no repetitions. The
     * {@code String}s are ordered in shortlex order (by length, then lexicographically), matching the order given by
     * the original {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsShortlex(@NotNull String s) {
        return distinctStrings(s);
    }

    /**
     * Generates all {@code List}s with a minimum size containing elements from a given {@code Iterable} with no
     * repetitions. The {@code List}s are ordered in shortlex order (by length, then lexicographically), matching the
     * order given by the original {@code Iterable}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T> Iterable<List<T>> distinctListsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        return distinctListsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all {@code String}s with a minimum size containing characters from a given {@code String} with no
     * repetitions. The {@code String}s are ordered in shortlex order (by length, then lexicographically), matching the
     * order given by the original {@code String}.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsShortlexAtLeast(int minSize, @NotNull String s) {
        return distinctStringsAtLeast(minSize, s);
    }

    /**
     * Generates all {@code List}s of a given size containing elements from a given {@code Iterable} with no
     * repetitions.
     *
     * @param xs an {@code Iterable} of elements
     * @param size the length of each of the generated {@code List}s
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> distinctLists(int size, @NotNull Iterable<T> xs);

    /**
     * Generates all {@code Pair}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Pair<T, T>> distinctPairs(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code Triple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Triple<T, T, T>> distinctTriples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code Quadruple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Quadruple<T, T, T, T>> distinctQuadruples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code Quintuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> distinctQuintuples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code Sextuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> distinctSextuples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code Septuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> distinctSeptuples(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s of a given size containing characters from a given {@code String} with no
     * repetitions.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStrings(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, distinctLists(size, uniformSample(s)));
    }

    /**
     * Generates all {@code String}s of a given size containing characters from a given {@code String}.
     *
     * @param size the length of each of the generated {@code String}s
     */
    public @NotNull Iterable<String> distinctStrings(int size) {
        return map(IterableUtils::charsToString, distinctLists(size, characters()));
    }

    /**
     * Generates all {@code List}s containing elements from a given {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> distinctLists(@NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s containing characters from a given {@code String} with no repetitions.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStrings(@NotNull String s) {
        return map(IterableUtils::charsToString, distinctLists(uniformSample(s)));
    }

    /**
     * Generates all {@code String}s containing characters from a given {@code String}.
     */
    public Iterable<String> distinctStrings() {
        return map(IterableUtils::charsToString, distinctLists(characters()));
    }

    /**
     * Generates all {@code List}s with a minimum size containing elements from a given {@code Iterable} with no
     * repetitions.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> distinctListsAtLeast(int minSize, @NotNull Iterable<T> xs);

    /**
     * Generates all {@code String}s with a minimum size containing characters from a given {@code String} with no
     * repetitions.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> distinctStringsAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsAtLeast(minSize, uniformSample(s)));
    }

    /**
     * Generates all {@code String}s with a minimum size with no repetitions.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     */
    public @NotNull Iterable<String> distinctStringsAtLeast(int minSize) {
        return map(IterableUtils::charsToString, distinctListsAtLeast(minSize, characters()));
    }

    /**
     * Generates all unordered {@code List}s of a given size containing elements from a given {@code List}. The
     * {@code List}s are ordered lexicographically.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsLex(int size, @NotNull List<T> xs) {
        return bags(size, uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Pair}s of elements from a {@code List}. The {@code Pair}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> bagPairsLex(@NotNull List<T> xs) {
        return bagPairs(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Triple}s of elements from a {@code List}. The {@code Triple}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> bagTriplesLex(@NotNull List<T> xs) {
        return bagTriples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Quadruple}s of elements from a {@code List}. The {@code Quadruple}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> bagQuadruplesLex(@NotNull List<T> xs) {
        return bagQuadruples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Quintuple}s of elements from a {@code List}. The {@code Quintuple}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> bagQuintuplesLex(
            @NotNull List<T> xs
    ) {
        return bagQuintuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Sextuple}s of elements from a {@code List}. The {@code Sextuple}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> bagSextuplesLex(
            @NotNull List<T> xs
    ) {
        return bagSextuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Septuple}s of elements from a {@code List}. The {@code Septuple}s are ordered
     * lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> bagSeptuplesLex(
            @NotNull List<T> xs
    ) {
        return bagSeptuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String}. The {@code String}s
     * are ordered lexicographically.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringBagsLex(int size, @NotNull String s) {
        return stringBags(size, s);
    }

    /**
     * Generates all unordered {@code List}s containing elements from a given {@code Iterable}. The {@code List}s are
     * ordered in shortlex order (by length, then lexicographically).
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsShortlex(@NotNull List<T> xs) {
        return bags(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String}. The {@code String}s
     * are ordered in shortlex order (by length, then lexicographically).
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringBagsShortlex(@NotNull String s) {
        return stringBags(s);
    }

    /**
     * Generates all unordered {@code List}s with a minimum size containing elements from a given {@code Iterable}. The
     * {@code List}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        return bagsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s with a minimum size containing characters from a given {@code String}.
     * The {@code String}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringBagsShortlexAtLeast(int minSize, @NotNull String s) {
        return stringBagsAtLeast(minSize, s);
    }

    /**
     * Generates all unordered {@code List}s of a given size containing elements from a given {@code Iterable}.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> bags(int size, @NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code Pair}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> bagPairs(@NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code Triple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> bagTriples(@NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code Quadruple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> bagQuadruples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Quintuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> bagQuintuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Sextuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> bagSextuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Septuple}s of elements from an {@code Iterable}.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> bagSeptuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code String}s of a given size containing characters from a given {@code String}.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringBags(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, bags(size, uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s of a given size.
     *
     * @param size the length of each of the generated {@code String}s
     */
    public @NotNull Iterable<String> stringBags(int size) {
        return map(IterableUtils::charsToString, bags(size, characters()));
    }

    /**
     * Generates all unordered {@code List}s containing elements from a given {@code Iterable}.
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> bags(@NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String}.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringBags(@NotNull String s) {
        return map(IterableUtils::charsToString, bags(uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s.
     */
    public @NotNull Iterable<String> stringBags() {
        return map(IterableUtils::charsToString, bags(characters()));
    }

    /**
     * Generates all unordered {@code List}s with a minimum size containing elements from a given {@code Iterable}.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsAtLeast(
            int minSize,
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code String}s with a minimum size containing characters from a given {@code String}.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s an {@code String}
     */
    public @NotNull Iterable<String> stringBagsAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, bagsAtLeast(minSize, uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s with a minimum size.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     */
    public @NotNull Iterable<String> stringBagsAtLeast(int minSize) {
        return map(IterableUtils::charsToString, bagsAtLeast(minSize, characters()));
    }

    /**
     * Generates all unordered {@code List}s of a given size containing elements from a given {@code List} with no
     * repetitions. The {@code List}s are ordered lexicographically.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLex(int size, @NotNull List<T> xs) {
        return subsets(size, uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Pair}s of elements from a {@code List} with no repetitions. The {@code Pair}s are
     * ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> subsetPairsLex(@NotNull List<T> xs) {
        return subsetPairs(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Triple}s of elements from a {@code List} with no repetitions. The {@code Triple}s
     * are ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> subsetTriplesLex(@NotNull List<T> xs) {
        return subsetTriples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Quadruple}s of elements from a {@code List} with no repetitions. The
     * {@code Quadruple}s are ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> subsetQuadruplesLex(
            @NotNull List<T> xs
    ) {
        return subsetQuadruples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Quintuple}s of elements from a {@code List} with no repetitions. The
     * {@code Quintuple}s are ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> subsetQuintuplesLex(
            @NotNull List<T> xs
    ) {
        return subsetQuintuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Sextuple}s of elements from a {@code List} with no repetitions. The
     * {@code Sextuple}s are ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> subsetSextuplesLex(
            @NotNull List<T> xs
    ) {
        return subsetSextuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code Septuple}s of elements from a {@code List} with no repetitions. The
     * {@code Septuple}s are ordered lexicographically.
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     */
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> subsetSeptuplesLex(
            @NotNull List<T> xs
    ) {
        return subsetSeptuples(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String} with no repetitions.
     * The {@code String}s are ordered lexicographically.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsLex(int size, @NotNull String s) {
        return stringSubsets(size, s);
    }

    /**
     * Generates all unordered {@code List}s containing elements from a given {@code List} with no repetitions. The
     * {@code List}s are ordered lexicographically.
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLex(@NotNull List<T> xs) {
        return subsets(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String} with no repetitions.
     * The {@code String}s are ordered lexicographically.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsLex(@NotNull String s) {
        return stringSubsets(s);
    }

    /**
     * Generates all unordered {@code List}s with a minimum size containing elements from a given {@code List} with no
     * repetitions. The {@code List}s are ordered lexicographically.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLexAtLeast(int minSize, @NotNull List<T> xs) {
        return subsetsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s with a minimum size containing characters from a given {@code String}
     * with no repetitions. The {@code String}s are ordered lexicographically.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsLexAtLeast(int minSize, @NotNull String s) {
        return stringSubsetsAtLeast(minSize, s);
    }

    /**
     * Generates all unordered {@code List}s containing elements from a given {@code Iterable} with no repetitions. The
     * {@code List}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsShortlex(@NotNull List<T> xs) {
        return subsets(uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String} with no repetitions.
     * The {@code String}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsShortlex(@NotNull String s) {
        return stringSubsets(s);
    }

    /**
     * Generates all unordered {@code List}s with a minimum size containing elements from a given {@code Iterable} with
     * no repetitions. The {@code List}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs a {@code List} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsShortlexAtLeast(
            int minSize,
            @NotNull List<T> xs
    ) {
        return subsetsAtLeast(minSize, uniformSample(xs));
    }

    /**
     * Generates all unordered {@code String}s with a minimum size containing characters from a given {@code String}
     * with no repetitions. The {@code String}s are ordered in shortlex order (by length, then lexicographically).
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsShortlexAtLeast(int minSize, @NotNull String s) {
        return stringSubsetsAtLeast(minSize, s);
    }

    /**
     * Generates all unordered {@code List}s of a given size containing elements from a given {@code Iterable} with no
     * repetitions.
     *
     * @param size the length of each of the generated {@code List}s
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> subsets(int size, @NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code Pair}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> subsetPairs(@NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code Triple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> subsetTriples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Quadruple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> subsetQuadruples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Quintuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> subsetQuintuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Sextuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> subsetSextuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code Septuple}s of elements from an {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> subsetSeptuples(
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code String}s of a given size containing characters from a given {@code String} with
     * no repetitions.
     *
     * @param size the length of each of the generated {@code String}s
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsets(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, subsets(size, uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s of a given size with no repetitions.
     *
     * @param size the length of each of the generated {@code String}s
     */
    public @NotNull Iterable<String> stringSubsets(int size) {
        return map(IterableUtils::charsToString, subsets(size, characters()));
    }

    /**
     * Generates all unordered {@code List}s containing elements from a given {@code Iterable} with no repetitions.
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> subsets(@NotNull Iterable<T> xs);

    /**
     * Generates all unordered {@code String}s containing characters from a given {@code String} with no repetitions.
     *
     * @param s a {@code String}
     */
    public @NotNull Iterable<String> stringSubsets(@NotNull String s) {
        return map(IterableUtils::charsToString, subsets(uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s with no repetitions.
     */
    public @NotNull Iterable<String> stringSubsets() {
        return map(IterableUtils::charsToString, subsets(characters()));
    }

    /**
     * Generates all unordered {@code List}s with a minimum size containing elements from a given {@code Iterable} with
     * no repetitions.
     *
     * @param minSize the minimum size of the resulting {@code List}s
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsAtLeast(
            int minSize,
            @NotNull Iterable<T> xs
    );

    /**
     * Generates all unordered {@code String}s with a minimum size containing characters from a given {@code String}
     * with no repetitions.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     * @param s an {@code String}
     */
    public @NotNull Iterable<String> stringSubsetsAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, subsetsAtLeast(minSize, uniformSample(s)));
    }

    /**
     * Generates all unordered {@code String}s with a minimum size with no repetitions.
     *
     * @param minSize the minimum size of the resulting {@code String}s
     */
    public @NotNull Iterable<String> stringSubsetsAtLeast(int minSize) {
        return map(IterableUtils::charsToString, subsetsAtLeast(minSize, characters()));
    }

    /**
     * Generates the Cartesian product of a {@code List} of {@code List}s, that is, all possible {@code List}s such
     * that the ith element of the {@code List} comes from the ith input {@code List}.
     *
     * @param xss a {@code List} of {@code List}s
     * @param <T> the type of values in the {@code List}s
     */
    public abstract @NotNull <T> Iterable<List<T>> cartesianProduct(@NotNull List<List<T>> xss);

    /**
     * Generates repeating infinite {@code Iterables} whose elements are chosen from a given {@code List}.
     *
     * @param xs the source of elements for the generated {@code Iterable}s
     * @param <T> the type of elements in the {@code Iterable}
     */
    public @NotNull <T> Iterable<Iterable<T>> repeatingIterables(@NotNull Iterable<T> xs) {
        return repeatingIterablesAtLeast(1, xs);
    }

    /**
     * Generates repeating infinite {@code Iterables} whose elements are chosen from a given {@code List}. The
     * repeating part has a specified minimum length.
     *
     * @param minSize the minimum length of the repeating part in the generated {@code Iterable}s
     * @param xs the source of elements for the {@code Iterable}
     * @param <T> the type of elements in the {@code Iterable}
     */
    public @NotNull <T> Iterable<Iterable<T>> repeatingIterablesAtLeast(int minSize, @NotNull Iterable<T> xs) {
        return map(IterableUtils::cycle, filterInfinite(ys -> unrepeat(ys).equals(ys), listsAtLeast(minSize, xs)));
    }

    public @NotNull Iterable<String> stringsWithChar(char c, @NotNull String s) {
        return map(p -> insert(p.a, p.b, c), dependentPairs(strings(s), t -> range(0, t.length())));
    }

    public @NotNull Iterable<String> stringsWithChar(char c) {
        return map(p -> insert(p.a, p.b, c), dependentPairs(strings(), t -> range(0, t.length())));
    }

    public @NotNull Iterable<String> stringsWithSubstrings(@NotNull Iterable<String> substrings, @NotNull String s) {
        return map(
                p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                pairsSquareRootOrder(dependentPairs(strings(s), t -> range(0, t.length())), substrings)
        );
    }

    public @NotNull Iterable<String> stringsWithSubstrings(@NotNull Iterable<String> substrings) {
        return map(
                p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                pairsSquareRootOrder(dependentPairs(strings(), t -> range(0, t.length())), substrings)
        );
    }

    public @NotNull <T> Iterable<List<T>> listsWithElement(@Nullable T element, Iterable<T> xs) {
        return map(p -> toList(insert(p.a, p.b, element)), dependentPairs(lists(xs), list -> range(0, list.size())));
    }

    public @NotNull <T> Iterable<List<T>> listsWithSubsequence(
            @NotNull Iterable<Iterable<T>> subsequences,
            @NotNull Iterable<T> xs
    ) {
        return map(
                p -> toList(concat(Arrays.asList(take(p.a.b, p.a.a), p.b, drop(p.a.b, p.a.a)))),
                pairsSquareRootOrder(dependentPairs(lists(xs), list -> range(0, list.size())), subsequences)
        );
    }

    public @NotNull <K, V> Iterable<Map<K, V>> maps(@NotNull List<K> ks, Iterable<V> vs) {
        return map(xs -> toMap(zip(ks, xs)), lists(ks.size(), vs));
    }

    public @NotNull Iterable<String> substrings(@NotNull String s) {
        return map(
                q -> s.substring(q.a, q.b),
                filter(p -> p.a <= p.b, pairs(range(0, s.length() - 1), range(0, s.length())))
        );
    }

    public @NotNull Iterable<RandomProvider> randomProvidersFixedScales(int scale, int secondaryScale) {
        return map(
                is -> new RandomProvider(is).withScale(scale).withSecondaryScale(secondaryScale),
                lists(IsaacPRNG.SIZE, integers())
        );
    }

    public @NotNull Iterable<RandomProvider> randomProviders() {
        return map(
                p -> new RandomProvider(p.a).withScale(p.b.a).withSecondaryScale(p.b.b),
                pairs(lists(IsaacPRNG.SIZE, integers()), pairs(integersGeometric()))
        );
    }

    public @NotNull Iterable<RandomProvider> randomProvidersDefault() {
        return map(RandomProvider::new, lists(IsaacPRNG.SIZE, integers()));
    }

    public @NotNull Iterable<RandomProvider> randomProvidersDefaultSecondaryScale() {
        return map(
                p -> new RandomProvider(p.a).withScale(p.b),
                pairs(lists(IsaacPRNG.SIZE, integers()), integersGeometric())
        );
    }
}
