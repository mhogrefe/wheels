package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
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
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.range;

/**
 * This class provides {@code Iterables} for testing. Subclasses should be immutable.
 */
public abstract strictfp class IterableProvider {
    public void reset() {}
    public @NotNull IterableProvider withScale(int scale) {
        return this;
    }
    public @NotNull IterableProvider withSecondaryScale(int secondaryScale) {
        return this;
    }

    public abstract @NotNull Iterable<Boolean> booleans();
    public abstract @NotNull Iterable<Ordering> orderings();
    public abstract @NotNull Iterable<RoundingMode> roundingModes();

    public abstract @NotNull <T> Iterable<T> uniformSample(@NotNull List<T> xs);
    public abstract @NotNull Iterable<Character> uniformSample(@NotNull String s);

    public @NotNull Iterable<Integer> naturalIntegersGeometric() {
        return naturalIntegers();
    }
    public @NotNull Iterable<Integer> positiveIntegersGeometric() {
        return positiveIntegers();
    }
    public @NotNull Iterable<Integer> negativeIntegersGeometric() {
        return negativeIntegers();
    }
    public @NotNull Iterable<Integer> nonzeroIntegersGeometric() {
        return integers();
    }
    public @NotNull Iterable<Integer> integersGeometric() {
        return integers();
    }
    public @NotNull Iterable<Integer> rangeUpGeometric(int a) {
        return rangeUp(a);
    }
    public @NotNull Iterable<Integer> rangeDownGeometric(int a) {
        return rangeDown(a);
    }

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
    public @NotNull Iterable<Byte> nonzeroBytes() {
        return filter(b -> b != 0, bytes());
    }
    public @NotNull Iterable<Short> nonzeroShorts() {
        return filter(s -> s != 0, shorts());
    }
    public @NotNull Iterable<Integer> nonzeroIntegers() {
        return filter(i -> i != 0, integers());
    }
    public @NotNull Iterable<Long> nonzeroLongs() {
        return filter(l -> l != 0, longs());
    }
    public abstract @NotNull Iterable<BigInteger> nonzeroBigIntegers();
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

    public abstract @NotNull Iterable<Byte> rangeUp(byte a);
    public abstract @NotNull Iterable<Short> rangeUp(short a);
    public abstract @NotNull Iterable<Integer> rangeUp(int a);
    public abstract @NotNull Iterable<Long> rangeUp(long a);
    public abstract @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a);
    public abstract @NotNull Iterable<Character> rangeUp(char a);
    public abstract @NotNull Iterable<Byte> rangeDown(byte a);
    public abstract @NotNull Iterable<Short> rangeDown(short a);
    public abstract @NotNull Iterable<Integer> rangeDown(int a);
    public abstract @NotNull Iterable<Long> rangeDown(long a);
    public abstract @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a);
    public abstract @NotNull Iterable<Character> rangeDown(char a);
    public abstract @NotNull Iterable<Byte> range(byte a, byte b);
    public abstract @NotNull Iterable<Short> range(short a, short b);
    public abstract @NotNull Iterable<Integer> range(int a, int b);
    public abstract @NotNull Iterable<Long> range(long a, long b);
    public abstract @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b);
    public abstract @NotNull Iterable<Character> range(char a, char b);

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

    public @NotNull Iterable<BinaryFraction> binaryFractions() {
        return map(p -> BinaryFraction.of(p.a, p.b), pairsLogarithmicOrder(bigIntegers(), integersGeometric()));
    }

    public abstract @NotNull <T> Iterable<T> withNull(@NotNull Iterable<T> xs);
    public abstract @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs);
    public abstract @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs);
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return pairs(xs);
    }
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairs(as, bs);
    }
    public @NotNull <T> Iterable<Pair<T, T>> pairsSquareRootOrder(@NotNull Iterable<T> xs) {
        return pairs(xs);
    }
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsSquareRootOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairs(as, bs);
    }
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
    public abstract @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs);
    public abstract @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs);
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

    public abstract @NotNull Iterable<String> strings(int size, @NotNull Iterable<Character> cs);
    public abstract @NotNull Iterable<String> stringsAtLeast(int minSize, @NotNull Iterable<Character> cs);
    public abstract @NotNull Iterable<String> strings(int size);
    public abstract @NotNull Iterable<String> stringsAtLeast(int size);
    public abstract @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs);
    public abstract @NotNull Iterable<String> strings();
    public @NotNull Iterable<String> stringsWithChar(char c, Iterable<Character> cs) {
        return map(p -> insert(p.a, p.b, c), dependentPairs(strings(cs), s -> range(0, s.length())));
    }
    public @NotNull Iterable<String> stringsWithChar(char c) {
        return stringsWithChar(c, characters());
    }
    public @NotNull Iterable<String> stringsWithSubstrings(
            @NotNull Iterable<String> substrings,
            @NotNull Iterable<Character> cs
    ) {
        return map(
                p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                pairsSquareRootOrder(dependentPairs(strings(cs), s -> range(0, s.length())), substrings)
        );
    }
    public @NotNull Iterable<String> stringsWithSubstrings(@NotNull Iterable<String> substrings) {
        return stringsWithSubstrings(substrings, characters());
    }

    public abstract @NotNull <T> Iterable<List<T>> permutations(@NotNull List<T> xs);
    public @NotNull Iterable<String> permutations(@NotNull String s) {
        return map(IterableUtils::charsToString, permutations(toList(fromString(s))));
    }

    public @NotNull Iterable<RandomProvider> randomProvidersFixedScales(int scale, int secondaryScale) {
        return map(
                is -> new RandomProvider(is).withScale(scale).withSecondaryScale(secondaryScale),
                lists(IsaacPRNG.SIZE, integers())
        );
    }

    @SuppressWarnings("ConstantConditions")
    public @NotNull Iterable<RandomProvider> randomProviders() {
        return map(
                p -> new RandomProvider(p.a).withScale(p.b.a).withSecondaryScale(p.b.b),
                pairs(lists(IsaacPRNG.SIZE, integers()), pairs(integersGeometric()))
        );
    }

    public @NotNull Iterable<RandomProvider> randomProvidersDefault() {
        return map(RandomProvider::new, lists(IsaacPRNG.SIZE, integers()));
    }

    @SuppressWarnings("ConstantConditions")
    public @NotNull Iterable<RandomProvider> randomProvidersDefaultSecondaryScale() {
        return map(
                p -> new RandomProvider(p.a).withScale(p.b),
                pairs(lists(IsaacPRNG.SIZE, integers()), integersGeometric())
        );
    }
}
