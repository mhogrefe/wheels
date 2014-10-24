package mho.haskellesque.iterables;

import mho.haskellesque.math.BasicMath;
import mho.haskellesque.math.Combinatorics;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
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
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) positive floats in ascending
     * order. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> positiveOrdinaryFloatsAscending() {
        return stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) negative floats in ascending
     * order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> negativeOrdinaryFloatsAscending() {
        return stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) floats in ascending order.
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
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) positive floats. Does not support
     * removal.
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
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) negative floats. Negative zero is not
     * included. Does not support removal.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) floats. Negative zero is not included,
     * but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    @Override
    public @NotNull Iterable<Float> ordinaryFloats() {
        return cons(0.0f, mux(Arrays.asList(positiveOrdinaryFloats(), negativeOrdinaryFloats())));
    }

    /**
     * An <tt>Iterable</tt> that contains all floats. Does not support removal.
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
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) positive doubles in ascending
     * order. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> positiveOrdinaryDoublesAscending() {
        return stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) negative doubles in ascending
     * order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> negativeOrdinaryDoublesAscending() {
        return stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) doubles in ascending order.
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
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) positive doubles. Does not support
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
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) negative doubles in ascending order.
     * Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (neither NaN nor infinite) doubles in ascending order. Negative zero
     * is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    @Override
    public @NotNull Iterable<Double> ordinaryDoubles() {
        return cons(0.0, mux(Arrays.asList(positiveOrdinaryDoubles(), negativeOrdinaryDoubles())));
    }

    /**
     * An <tt>Iterable</tt> that contains all doubles. Does not support removal.
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
     * An <tt>Iterable</tt> that contains all positive <tt>BigDecimal</tt>s. Does not support removal.
     *
     * Length is infinite.
     */
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(positiveBigIntegers(), integers()));
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigDecimal</tt>s. Does not support removal.
     *
     * Length is infinite.
     */
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(negativeBigIntegers(), integers()));
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>BigDecimal</tt>s. Does not support removal.
     *
     * Length is infinite.
     */
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(bigIntegers(), integers()));
    }

    /**
     * See <tt>Combinatorics::pairsLogarithmicOrder</tt>
     *
     * @param xs the <tt>Iterable</tt> from which elements are selected
     * @param <T> the type of the given <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>xs</tt> in logarithmic order
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return Combinatorics.pairsLogarithmicOrder(xs);
    }

    /**
     * See <tt>Combinatorics::pairsLogarithmicOrder</tt>
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
        return Combinatorics.pairsLogarithmicOrder(as, bs);
    }

    /**
     * See <tt>Combinatorics::pairs</tt>
     *
     * @param as the <tt>Iterable</tt> from which the first components of the pairs are selected
     * @param bs the <tt>Iterable</tt> from which the second components of the pairs are selected
     * @param <A> the type of the first <tt>Iterable</tt>'s elements
     * @param <B> the type of the second <tt>Iterable</tt>'s elements
     * @return all pairs of elements from <tt>as</tt> and <tt>bs</tt>
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return Combinatorics.pairs(as, bs);
    }

    /**
     * See <tt>Combinatorics::triples</tt>
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
        return Combinatorics.triples(as, bs, cs);
    }

    /**
     * See <tt>Combinatorics::quadruples</tt>
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
        return Combinatorics.quadruples(as, bs, cs, ds);
    }

    /**
     * See <tt>Combinatorics::quintuples</tt>
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
        return Combinatorics.quintuples(as, bs, cs, ds, es);
    }

    /**
     * See <tt>Combinatorics::sextuples</tt>
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
        return Combinatorics.sextuples(as, bs, cs, ds, es, fs);
    }

    /**
     * See <tt>Combinatorics::septuples</tt>
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
        return Combinatorics.septuples(as, bs, cs, ds, es, fs, gs);
    }

    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        return Combinatorics.pairs(xs);
    }

    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        return Combinatorics.triples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        return Combinatorics.quadruples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        return Combinatorics.quintuples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        return Combinatorics.sextuples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
        return Combinatorics.septuples(xs);
    }

    public @NotNull Iterable<String> strings() {
        return Combinatorics.strings(characters());
    }
}
