package mho.haskellesque.iterables;

import mho.haskellesque.math.Combinatorics;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
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
     * A <tt>List</tt> that contains all <tt>RoundingModes</tt>s.
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
     * @return An <tt>Iterable</tt> that contains all <tt>Byte</tt>s in ascending order.
     *
     * Length is 2<sup>8</sup> = 256
     */
    public @NotNull Iterable<Byte> bytesAscending() {
        return range(Byte.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Short> shortsAscending() {
        return range(Short.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    public @NotNull Iterable<Integer> integersAscending() {
        return range(Integer.MIN_VALUE);
    }

    /**
     * @return An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    public @NotNull Iterable<Long> longsAscending() {
        return range(Long.MIN_VALUE);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup>&#x2212;1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return range((byte) 1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup>&#x2212;1 = 32,767
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return range((short) 1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup>&#x2212;1 = 2,147,483,647
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return range(1);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup>&#x2212;1 = 9,223,372,036,854,775,807
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return range(1L);
    }

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return range(BigInteger.ONE);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return rangeBy((byte) -1, (byte) -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return rangeBy((short) -1, (short) -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return rangeBy(-1, -1);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return rangeBy(-1L, -1L);
    }

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return range((byte) 0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Short</tt>s (including 0).
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return range((short) 0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Integer</tt>s (including 0).
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return range(0);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Long</tt>s (including 0).
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return range(0L);
    }

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>BigInteger</tt>s (including 0).
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return range(BigInteger.ZERO);
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s.
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
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s.
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
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s.
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
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return cons(0L, mux(Arrays.asList(positiveLongs(), negativeLongs())));
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>BigInteger</tt>s.
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
     * @return An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in ascending order.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public @NotNull Iterable<Character> asciiCharactersAscending() {
        return range((char) 0, (char) 127);
    }

    /**
     * An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in an order which places "friendly" characters
     * first.
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
     * @return An <tt>Iterable</tt> that contains all <tt>Character</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Character> charactersAscending() {
        return range(Character.MIN_VALUE, Character.MAX_VALUE);
    }

    /**
     * An <tt>Iterable</tt> that contains all <tt>Character</tt>s in an order which places "friendly" characters
     * first.
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
     * order.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> positiveOrdinaryFloatsAscending() {
        return stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative floats in ascending
     * order. Negative zero is not included.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public @NotNull Iterable<Float> negativeOrdinaryFloatsAscending() {
        return stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats in ascending order.
     * Negative zero is not included, but positive zero is.
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
     * here it is placed between negative zero and positive zero.
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
     * integer that, when multiplied by a power of 2, equals the float.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final @NotNull Iterable<Integer> FLOAT_MANTISSAS = rangeBy(1, 2, 1 << 24);

    /**
     * An <tt>Iterable</tt> that contains all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa.
     *
     * Length is 2<sup>8</sup>+23&#x2212;2 = 277
     */
    private static final @NotNull Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 127), rangeBy(-1, -1, -149)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive floats.
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
     * included.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats. Negative zero is not included,
     * but positive zero is.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    @Override
    public @NotNull Iterable<Float> ordinaryFloats() {
        return cons(0.0f, mux(Arrays.asList(positiveOrdinaryFloats(), negativeOrdinaryFloats())));
    }

    /**
     * An <tt>Iterable</tt> that contains all floats. NaN is traditionally unordered, but here it is placed between
     * negative zero and positive zero.
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
     * order.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> positiveOrdinaryDoublesAscending() {
        return stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative doubles in ascending
     * order. Negative zero is not included.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> negativeOrdinaryDoublesAscending() {
        return stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE));
    }

    /**
     * @return An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order.
     * Negative zero is not included, but positive zero is.
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
     * here it is placed between negative zero and positive zero.
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
     * integer that, when multiplied by a power of 2, equals the double.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final @NotNull Iterable<Long> DOUBLE_MANTISSAS = rangeBy(1L, 2, 1L << 53);

    /**
     * An <tt>Iterable</tt> that contains all possible double exponents. A positive double's exponent is the base-2
     * logarithm of the double divided by its mantissa.
     *
     * Length is 2<sup>11</sup>+52&#x2212;2 = 2,098
     */
    private static final @NotNull Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 1023), rangeBy(-1, -1, -1074)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive doubles.
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
     * Negative zero is not included.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order. Negative zero
     * is not included, but positive zero is.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    @Override
    public @NotNull Iterable<Double> ordinaryDoubles() {
        return cons(0.0, mux(Arrays.asList(positiveOrdinaryDoubles(), negativeOrdinaryDoubles())));
    }

    /**
     * An <tt>Iterable</tt> that contains all doubles in ascending order. NaN is traditionally unordered, but here it
     * is placed between negative zero and positive zero.
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

    public @NotNull Iterable<String> strings() {
        return Combinatorics.strings(characters());
    }

    public static <T> Iterable<ArrayList<T>> arrayLists(int size, Iterable<T> xs) {
        return map(list -> (ArrayList<T>) list, lists(size, xs));
    }

    public static <T> Iterable<ArrayList<T>> arrayLists(Iterable<T> xs) {
        return map(list -> (ArrayList<T>) list, lists(xs));
    }

    public static <T> Iterable<LinkedList<T>> linkedLists(int size, Iterable<T> xs) {
        return map(list -> {
            LinkedList<T> linkedList = new LinkedList<>();
            addTo(list, linkedList);
            return linkedList;
        }, lists(size, xs));
    }

    public static <T> Iterable<LinkedList<T>> linkedLists(Iterable<T> xs) {
        return map(list -> {
            LinkedList<T> linkedList = new LinkedList<>();
            addTo(list, linkedList);
            return linkedList;
        }, lists(xs));
    }
}

