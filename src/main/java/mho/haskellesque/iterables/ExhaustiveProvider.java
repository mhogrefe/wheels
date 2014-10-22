package mho.haskellesque.iterables;

import mho.haskellesque.math.BasicMath;
import mho.haskellesque.math.Combinatorics;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.iterables.IterableUtils.map;
import static mho.haskellesque.math.Combinatorics.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * <tt>Iterable</tt>s that contain all (or some important subset) of a type's values. These are useful for exhaustive
 * testing. Nulls are not included by default, but may easily be added via cons. The <tt>Iterable</tt>'s elements are
 * typically in order of increasing complexity, unless otherwise specified. See the test class for examples.
 */
public class ExhaustiveProvider implements IterableProvider {
    /**
     * A <tt>List</tt> that contains both <tt>Boolean</tt>s.
     *
     * Length is 2
     */
    @Override
    public @NotNull List<Boolean> booleans() {
        return Arrays.asList(false, true);
    }

    /**
     * @return A <tt>List</tt> that contains all <tt>Ordering</tt>s in ascending order.
     *
     * Length is 3
     */
    public @NotNull List<Ordering> orderingsAscending() {
        return Arrays.asList(LT, EQ, GT);
    }

    /**
     * A <tt>List</tt> that contains all <tt>Ordering</tt>s.
     *
     * Length is 3
     */
    @Override
    public @NotNull List<Ordering> orderings() {
        return Arrays.asList(EQ, LT, GT);
    }

    /**
     * A <tt>List</tt> that contains all <tt>RoundingMode</tt>s.
     *
     * Length is 8
     */
    @Override
    public @NotNull List<RoundingMode> roundingModes() {
        return Arrays.asList(
                RoundingMode.UNNECESSARY,
                RoundingMode.UP,
                RoundingMode.DOWN,
                RoundingMode.CEILING,
                RoundingMode.FLOOR,
                RoundingMode.HALF_UP,
                RoundingMode.HALF_DOWN,
                RoundingMode.HALF_EVEN
        );
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Byte</tt>s in ascending order. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 256
     */
    public @NotNull Iterable<Byte> bytesAscending() {
        return range(Byte.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Short> shortsAscending() {
        return range(Short.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    public @NotNull Iterable<Integer> integersAscending() {
        return range(Integer.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    public @NotNull Iterable<Long> longsAscending() {
        return range(Long.MIN_VALUE);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s. Does not support removal.
     *
     * Length is 2<sup>7</sup>&#x2212;1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return range((byte) 1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s. Does not support removal.
     *
     * Length is 2<sup>15</sup>&#x2212;1 = 32,767
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return range((short) 1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;1 = 2,147,483,647
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return range(1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;1 = 9,223,372,036,854,775,807
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return range(1L);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>BigInteger</tt>s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return range(BigInteger.ONE);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Byte</tt>s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return rangeBy((byte) -1, (byte) -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s. Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return rangeBy((short) -1, (short) -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s. Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return rangeBy(-1, -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s. Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return rangeBy(-1L, -1L);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigInteger</tt>s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Byte</tt>s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return range((byte) 0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Short</tt>s (including 0). Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return range((short) 0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Integer</tt>s (including 0). Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return range(0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Long</tt>s (including 0). Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return range(0L);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>BigInteger</tt>s (including 0). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return range(BigInteger.ZERO);
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return cons(
                (byte) 0,
                mux(Arrays.asList(positiveBytes(), negativeBytes()))
        );
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return cons(
                (short) 0,
                mux(Arrays.asList(positiveShorts(), negativeShorts()))
        );
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return cons(
                0,
                mux(Arrays.asList(positiveIntegers(), negativeIntegers()))
        );
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return cons(0L, mux(Arrays.asList(positiveLongs(), negativeLongs())));
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>BigInteger</tt>s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        return cons(
                BigInteger.ZERO, mux(Arrays.asList(positiveBigIntegers(), negativeBigIntegers()))
        );
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in ascending order. Does not support
     * removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public @NotNull Iterable<Character> asciiCharactersAscending() {
        return range((char) 0, (char) 127);
    }

    /**
     * An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in an order which places "friendly" characters
     * first. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Character> asciiCharacters() {
        return concat(Arrays.asList(
                range('a', 'z'),
                range('A', 'Z'),
                range('0', '9'),
                range('!', '/'),            // printable non-alphanumeric ASCII...
                range(':', '@'),            // ...
                range('[', '`'),            // ...
                range('{', '~'),            // ...
                range((char) 0, (char) 32), // non-printable and whitespace ASCII
                Arrays.asList((char) 127)   // DEL
        ));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Character</tt>s in ascending order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Character> charactersAscending() {
        return range(Character.MIN_VALUE, Character.MAX_VALUE);
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Character</tt>s in an order which places "friendly" characters
     * first. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Character> characters() {
        return concat(Arrays.asList(
                range('a', 'z'),
                range('A', 'Z'),
                range('0', '9'),
                range('!', '/'),            // printable non-alphanumeric ASCII...
                range(':', '@'),            // ...
                range('[', '`'),            // ...
                range('{', '~'),            // ...
                range((char) 0, (char) 32), // non-printable and whitespace ASCII
                range((char) 127)           // DEL and non-ASCII
        ));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive floats in ascending
     * order. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> positiveOrdinaryFloatsAscending() {
        return stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative floats in ascending
     * order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> negativeOrdinaryFloatsAscending() {
        return stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats in ascending order.
     * Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    public @NotNull Iterable<Float> ordinaryFloatsAscending() {
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE)),
                Arrays.asList(0.0f),
                stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all floats in ascending order. NaN is traditionally unordered, but
     * here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>+3 = 4,278,190,083
     */
    public @NotNull Iterable<Float> floatsAscending() {
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, Float.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0f, Float.NaN, 0.0f),
                stopAt(f -> f == Float.POSITIVE_INFINITY, iterate(Numbers::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * An <tt>Iterable</tt> that contains all possible positive float mantissas. A float's mantissa is the unique odd
     * integer that, when multiplied by a power of 2, equals the float. Does not support removal.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final @NotNull Iterable<Integer> FLOAT_MANTISSAS = rangeBy(1, 2, 1 << 24);

    /**
     * An <tt>Iterable</tt> that contains all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>8</sup>+23&#x2212;2 = 277
     */
    private static final @NotNull Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 127), rangeBy(-1, -1, -149)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive floats. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> positiveOrdinaryFloats() {
        return map(
                Optional::get,
                filter(
                        Optional::isPresent,
                        (Iterable<Optional<Float>>) map(
                                p -> Numbers.floatFromPair(p.a, p.b),
                                pairs(FLOAT_MANTISSAS, FLOAT_EXPONENTS)
                        )
                )
        );
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative floats. Negative zero is not
     * included. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats. Negative zero is not included,
     * but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    @Override
    public @NotNull Iterable<Float> ordinaryFloats() {
        return cons(0.0f, mux(Arrays.asList(positiveOrdinaryFloats(), negativeOrdinaryFloats())));
    }

    /**
     * An <tt>Iterable</tt> that contains all floats. NaN is traditionally unordered, but here it is placed between
     * negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>+3 = 4,278,190,083
     */
    @Override
    public @NotNull Iterable<Float> floats() {
        return concat(
                Arrays.asList(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f),
                (Iterable<Float>) tail(ordinaryFloats())
        );
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive doubles in ascending
     * order. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> positiveOrdinaryDoublesAscending() {
        return stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative doubles in ascending
     * order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> negativeOrdinaryDoublesAscending() {
        return stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order.
     * Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    public @NotNull Iterable<Double> ordinaryDoublesAscending() {
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE)),
                Arrays.asList(0.0),
                stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all doubles in ascending order. NaN is traditionally unordered, but
     * here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    public @NotNull Iterable<Double> doublesAscending() {
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, Double.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0, Double.NaN, 0.0),
                stopAt(d -> d == Double.POSITIVE_INFINITY, iterate(Numbers::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * An <tt>Iterable</tt> that contains all possible positive double mantissas. A double's mantissa is the unique odd
     * integer that, when multiplied by a power of 2, equals the double. Does not support removal.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final @NotNull Iterable<Long> DOUBLE_MANTISSAS = rangeBy(1L, 2, 1L << 53);

    /**
     * An <tt>Iterable</tt> that contains all possible double exponents. A positive double's exponent is the base-2
     * logarithm of the double divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>11</sup>+52&#x2212;2 = 2,098
     */
    private static final @NotNull Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 1023), rangeBy(-1, -1, -1074)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive doubles. Does not support
     * removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> positiveOrdinaryDoubles() {
        return map(
                Optional::get,
                filter(
                        Optional::isPresent,
                        (Iterable<Optional<Double>>) map(
                                p -> Numbers.doubleFromPair(p.a, p.b),
                                pairs(DOUBLE_MANTISSAS, DOUBLE_EXPONENTS)
                        )
                )
        );
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative doubles in ascending order.
     * Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order. Negative zero
     * is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    @Override
    public @NotNull Iterable<Double> ordinaryDoubles() {
        return cons(0.0, mux(Arrays.asList(positiveOrdinaryDoubles(), negativeOrdinaryDoubles())));
    }

    /**
     * An <tt>Iterable</tt> that contains all doubles in ascending order. NaN is traditionally unordered, but here it
     * is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    @Override
    public @NotNull Iterable<Double> doubles() {
        return concat(
                Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0),
                (Iterable<Double>) tail(ordinaryDoubles())
        );
    }

    /**
     * Returns all pairs of elements taken from two <tt>Iterable</tt>s in such a way that the first component grows
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
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return new ArrayList<>();
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<Pair<T, T>>> f = bi -> {
            Pair<BigInteger, BigInteger> p = BasicMath.logarithmicDemux(bi);
            assert p.a != null;
            NullableOptional<T> optA = ii.get(p.a.intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            NullableOptional<T> optB = ii.get(p.b.intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
                        )
                )
        );
    }

    //todo
    private @NotNull <A, B> Iterable<Pair<A, B>> pairsByFunction(
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
            NullableOptional<A> optA = aii.get(p.a.intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.b != null;
            NullableOptional<B> optB = bii.get(p.b.intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
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
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return pairsByFunction(BasicMath::logarithmicDemux, as, bs);
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
    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return pairsByFunction(
                bi -> {
                    List<BigInteger> list = BasicMath.demux(2, bi);
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
    @Override
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs)) return new ArrayList<>();
        CachedIterable<A> aii = new CachedIterable<>(as);
        CachedIterable<B> bii = new CachedIterable<>(bs);
        CachedIterable<C> cii = new CachedIterable<>(cs);
        Function<BigInteger, Optional<Triple<A, B, C>>> f = bi -> {
            List<BigInteger> p = BasicMath.demux(3, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
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
    @Override
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
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
            List<BigInteger> p = BasicMath.demux(4, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
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
    @Override
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
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
            List<BigInteger> p = BasicMath.demux(5, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValue());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
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
    @Override
    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
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
            List<BigInteger> p = BasicMath.demux(6, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValue());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValue());
            if (!optE.isPresent()) return Optional.empty();
            assert p.get(5) != null;
            NullableOptional<F> optF = fii.get(p.get(5).intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
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
    @Override
    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
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
            List<BigInteger> p = BasicMath.demux(7, bi);
            assert p.get(0) != null;
            NullableOptional<A> optA = aii.get(p.get(0).intValue());
            if (!optA.isPresent()) return Optional.empty();
            assert p.get(1) != null;
            NullableOptional<B> optB = bii.get(p.get(1).intValue());
            if (!optB.isPresent()) return Optional.empty();
            assert p.get(2) != null;
            NullableOptional<C> optC = cii.get(p.get(2).intValue());
            if (!optC.isPresent()) return Optional.empty();
            assert p.get(3) != null;
            NullableOptional<D> optD = dii.get(p.get(3).intValue());
            if (!optD.isPresent()) return Optional.empty();
            assert p.get(4) != null;
            NullableOptional<E> optE = eii.get(p.get(4).intValue());
            if (!optE.isPresent()) return Optional.empty();
            assert p.get(5) != null;
            NullableOptional<F> optF = fii.get(p.get(5).intValue());
            if (!optF.isPresent()) return Optional.empty();
            assert p.get(6) != null;
            NullableOptional<G> optG = gii.get(p.get(6).intValue());
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
                                        map(bi -> f.apply(bi), naturalBigIntegers())
                        )
                )
        );
    }

    public <T> Iterable<List<T>> lists(int size, Iterable<T> xs) {
        if (size == 0) {
            return Arrays.asList(new ArrayList<T>());
        }
        CachedIterable<T> ii = new CachedIterable<>(xs);
        Function<BigInteger, Optional<List<T>>> f = bi -> ii.get(map(BigInteger::intValue, BasicMath.demux(size, bi)));
        return map(
                Optional::get,
                filter(
                        Optional<List<T>>::isPresent,
                        (Iterable<Optional<List<T>>>) map(bi -> f.apply(bi), naturalBigIntegers())
                )
        );
    }

    public Iterable<String> strings(int size, Iterable<Character> cs) {
        return map(IterableUtils::charsToString, lists(size, cs));
    }

    public Iterable<String> strings(int size, String s) {
        return map(IterableUtils::charsToString, lists(size, fromString(s)));
    }

    public Iterable<String> strings(int size) {
        return strings(size, characters());
    }

    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), lists(2, xs));
    }

    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), lists(3, xs));
    }

    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), lists(4, xs));
    }

    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                lists(5, xs)
        );
    }

    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                lists(6, xs)
        );
    }

    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
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

    public @NotNull Iterable<String> strings() {
        return Combinatorics.strings(characters());
    }
}

