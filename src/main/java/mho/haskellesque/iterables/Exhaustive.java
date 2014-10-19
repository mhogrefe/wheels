package mho.haskellesque.iterables;

import mho.haskellesque.math.BasicMath;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.iterables.IterableUtils.cons;
import static mho.haskellesque.math.Combinatorics.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * <tt>Iterable</tt>s that contain all (or some important subset) of a type's values. These are useful for exhaustive
 * testing. Nulls are not included by default, but may easily be added via cons. The <tt>Iterable</tt>'s elements are
 * typically in order of increasing complexity, unless otherwise specified. See the test class for examples.
 */
public final class Exhaustive {
    /**
     * Disallow instantiation
     */
    private Exhaustive() {}

    /**
     * An <tt>Iterable</tt> that contains both <tt>Boolean</tt>s.
     *
     * Length is 2
     */
    public static final @NotNull List<Boolean> BOOLEANS = Arrays.asList(false, true);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Ordering</tt>s in ascending order.
     *
     * Length is 3
     */
    public static final @NotNull List<Ordering> ORDERINGS_ASCENDING = Arrays.asList(
            LT,
            EQ,
            GT
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Ordering</tt>s.
     *
     * Length is 3
     */
    public static final @NotNull List<Ordering> ORDERINGS = Arrays.asList(EQ, LT, GT);

    /**
     * An <tt>Iterable</tt> that contains all <tt>RoundingModes</tt>s.
     *
     * Length is 8
     */
    public static final @NotNull List<RoundingMode> ROUNDING_MODES = Arrays.asList(
            RoundingMode.UNNECESSARY,
            RoundingMode.UP,
            RoundingMode.DOWN,
            RoundingMode.CEILING,
            RoundingMode.FLOOR,
            RoundingMode.HALF_UP,
            RoundingMode.HALF_DOWN,
            RoundingMode.HALF_EVEN
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s in ascending order.
     *
     * Length is 2<sup>8</sup> = 256
     */
    public static final @NotNull Iterable<Byte> BYTES_ASCENDING = range(Byte.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public static final @NotNull Iterable<Short> SHORTS_ASCENDING = range(Short.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    public static final @NotNull Iterable<Integer> INTEGERS_ASCENDING = range(Integer.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    public static final @NotNull Iterable<Long> LONGS_ASCENDING = range(Long.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup>&#x2212;1 = 127
     */
    public static final @NotNull Iterable<Byte> POSITIVE_BYTES = range((byte) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup>&#x2212;1 = 32,767
     */
    public static final @NotNull Iterable<Short> POSITIVE_SHORTS = range((short) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup>&#x2212;1 = 2,147,483,647
     */
    public static final @NotNull Iterable<Integer> POSITIVE_INTEGERS = range(1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup>&#x2212;1 = 9,223,372,036,854,775,807
     */
    public static final @NotNull Iterable<Long> POSITIVE_LONGS = range(1L);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> POSITIVE_BIG_INTEGERS = range(BigInteger.ONE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public static final @NotNull Iterable<Byte> NEGATIVE_BYTES = rangeBy((byte) -1, (byte) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    public static final @NotNull Iterable<Short> NEGATIVE_SHORTS = rangeBy((short) -1, (short) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    public static final @NotNull Iterable<Integer> NEGATIVE_INTEGERS = rangeBy(-1, -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    public static final @NotNull Iterable<Long> NEGATIVE_LONGS = rangeBy(-1L, -1L);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> NEGATIVE_BIG_INTEGERS =
            rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public static final @NotNull Iterable<Byte> NATURAL_BYTES = range((byte) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Short</tt>s (including 0).
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    public static final @NotNull Iterable<Short> NATURAL_SHORTS = range((short) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Integer</tt>s (including 0).
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    public static final @NotNull Iterable<Integer> NATURAL_INTEGERS = range(0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Long</tt>s (including 0).
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    public static final @NotNull Iterable<Long> NATURAL_LONGS = range(0L);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>BigInteger</tt>s (including 0).
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> NATURAL_BIG_INTEGERS = range(BigInteger.ZERO);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s.
     *
     * Length is 2<sup>8</sup> = 128
     */
    public static final @NotNull Iterable<Byte> BYTES = cons(
            (byte) 0,
            mux(Arrays.asList(POSITIVE_BYTES, NEGATIVE_BYTES))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public static final @NotNull Iterable<Short> SHORTS = cons(
            (short) 0,
            mux(Arrays.asList(POSITIVE_SHORTS, NEGATIVE_SHORTS))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    public static final @NotNull Iterable<Integer> INTEGERS = cons(
            0,
            mux(Arrays.asList(POSITIVE_INTEGERS, NEGATIVE_INTEGERS))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    public static final @NotNull Iterable<Long> LONGS = cons(0L, mux(Arrays.asList(POSITIVE_LONGS, NEGATIVE_LONGS)));

    /**
     * An <tt>Iterable</tt> that contains all <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> BIG_INTEGERS = cons(
            BigInteger.ZERO, mux(Arrays.asList(POSITIVE_BIG_INTEGERS, NEGATIVE_BIG_INTEGERS))
    );

    /**
     * An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in ascending order.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public static final @NotNull Iterable<Character> ASCII_CHARACTERS_ASCENDING = range((char) 0, (char) 127);

    /**
     * An <tt>Iterable</tt> that contains all ASCII <tt>Character</tt>s in an order which places "friendly" characters
     * first.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public static final @NotNull Iterable<Character> ASCII_CHARACTERS = concat(Arrays.asList(
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

    /**
     * An <tt>Iterable</tt> that contains all <tt>Character</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public static final @NotNull Iterable<Character> CHARACTERS_ASCENDING =
            range(Character.MIN_VALUE, Character.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Character</tt>s in an order which places "friendly" characters
     * first.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public static final @NotNull Iterable<Character> CHARACTERS = concat(Arrays.asList(
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

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive floats in ascending order.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public static final Iterable<Float> POSITIVE_ORDINARY_FLOATS_ASCENDING =
            stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative floats in ascending order.
     * Negative zero is not included.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public static final Iterable<Float> NEGATIVE_ORDINARY_FLOATS_ASCENDING =
            stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE));

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats in ascending order. Negative zero
     * is not included, but positive zero is.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    public static final Iterable<Float> ORDINARY_FLOATS_ASCENDING =
            concat((Iterable<Iterable<Float>>) Arrays.asList(
                    stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE)),
                    Arrays.asList(0.0f),
                    stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE))
            ));

    /**
     * An <tt>Iterable</tt> that contains all floats in ascending order. NaN is traditionally unordered, but here it is
     * placed between negative zero and positive zero.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>+3 = 4,278,190,083
     */
    public static final Iterable<Float> FLOATS_ASCENDING =
            concat((Iterable<Iterable<Float>>) Arrays.asList(
                    stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, Float.NEGATIVE_INFINITY)),
                    Arrays.asList(-0.0f, Float.NaN, 0.0f),
                    stopAt(f -> f == Float.POSITIVE_INFINITY, iterate(Numbers::successor, Float.MIN_VALUE))
            ));

    /**
     * An <tt>Iterable</tt> that contains all possible positive float mantissas. A float's mantissa is the unique odd
     * integer that, when multiplied by a power of 2, equals the float.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final Iterable<Integer> FLOAT_MANTISSAS = rangeBy(1, 2, 1 << 24);

    /**
     * An <tt>Iterable</tt> that contains all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa.
     *
     * Length is 2<sup>8</sup>+23&#x2212;2 = 277
     */
    private static final Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 127), rangeBy(-1, -1, -149)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive floats.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public static final Iterable<Float> POSITIVE_ORDINARY_FLOATS = map(
            Optional::get,
            filter(
                    Optional::isPresent,
                    (Iterable<Optional<Float>>) map(
                            p -> Numbers.floatFromPair(p.a, p.b),
                            pairs(FLOAT_MANTISSAS, FLOAT_EXPONENTS)
                    )
            )
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative floats. Negative zero is not
     * included.
     *
     * Length is 2<sup>31</sup>&#x2212;2<sup>23</sup>&#x2212;1 = 2,139,095,039
     */
    public static final Iterable<Float> NEGATIVE_ORDINARY_FLOATS = map(f -> -f, POSITIVE_ORDINARY_FLOATS);

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) floats. Negative zero is not included,
     * but positive zero is.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>&#x2212;1 = 4,278,190,079
     */
    public static final Iterable<Float> ORDINARY_FLOATS = cons(0.0f, mux(Arrays.asList(
            POSITIVE_ORDINARY_FLOATS,
            NEGATIVE_ORDINARY_FLOATS
    )));

    /**
     * An <tt>Iterable</tt> that contains all floats. NaN is traditionally unordered, but here it is placed between
     * negative zero and positive zero.
     *
     * Length is 2<sup>32</sup>&#x2212;2<sup>24</sup>+3 = 4,278,190,083
     */
    public static final Iterable<Float> FLOATS = concat(
            Arrays.asList(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f),
            (Iterable<Float>) tail(ORDINARY_FLOATS)
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive doubles in ascending order.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public static final Iterable<Double> POSITIVE_ORDINARY_DOUBLES_ASCENDING =
            stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative doubles in ascending order.
     * Negative zero is not included.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public static final Iterable<Double> NEGATIVE_ORDINARY_DOUBLES_ASCENDING =
            stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE));

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order. Negative zero
     * is not included, but positive zero is.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    public static final Iterable<Double> ORDINARY_DOUBLES_ASCENDING =
            concat((Iterable<Iterable<Double>>) Arrays.asList(
                    stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE)),
                    Arrays.asList(0.0),
                    stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE))
            ));

    /**
     * An <tt>Iterable</tt> that contains all doubles in ascending order. NaN is traditionally unordered, but here it
     * is placed between negative zero and positive zero.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    public static final Iterable<Double> DOUBLES_ASCENDING =
            concat((Iterable<Iterable<Double>>) Arrays.asList(
                    stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, Double.NEGATIVE_INFINITY)),
                    Arrays.asList(-0.0, Double.NaN, 0.0),
                    stopAt(d -> d == Double.POSITIVE_INFINITY, iterate(Numbers::successor, Double.MIN_VALUE))
            ));

    /**
     * An <tt>Iterable</tt> that contains all possible positive double mantissas. A double's mantissa is the unique odd
     * integer that, when multiplied by a power of 2, equals the double.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final Iterable<Long> DOUBLE_MANTISSAS = rangeBy(1L, 2, 1L << 53);

    /**
     * An <tt>Iterable</tt> that contains all possible double exponents. A positive double's exponent is the base-2
     * logarithm of the double divided by its mantissa.
     *
     * Length is 2<sup>11</sup>+52&#x2212;2 = 2,098
     */
    private static final Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 1023), rangeBy(-1, -1, -1074)))
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) positive doubles.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public static final Iterable<Double> POSITIVE_ORDINARY_DOUBLES = map(
            Optional::get,
            filter(
                    Optional::isPresent,
                    (Iterable<Optional<Double>>) map(
                            p -> Numbers.doubleFromPair(p.a, p.b),
                            pairs(DOUBLE_MANTISSAS, DOUBLE_EXPONENTS)
                    )
            )
    );

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) negative doubles in ascending order.
     * Negative zero is not included.
     *
     * Length is 2<sup>63</sup>&#x2212;2<sup>52</sup>&#x2212;1 = 9,218,868,437,227,405,311
     */
    public static final Iterable<Double> NEGATIVE_ORDINARY_DOUBLES = map(d -> -d, POSITIVE_ORDINARY_DOUBLES);

    /**
     * An <tt>Iterable</tt> that contains all ordinary (not NaN or infinite) doubles in ascending order. Negative zero
     * is not included, but positive zero is.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>&#x2212;1 = 18,437,736,874,454,810,623
     */
    public static final Iterable<Double> ORDINARY_DOUBLES = cons(0.0, mux(Arrays.asList(
            POSITIVE_ORDINARY_DOUBLES,
            NEGATIVE_ORDINARY_DOUBLES
    )));

    /**
     * An <tt>Iterable</tt> that contains all doubles in ascending order. NaN is traditionally unordered, but here it
     * is placed between negative zero and positive zero.
     *
     * Length is 2<sup>64</sup>&#x2212;2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    public static final Iterable<Double> DOUBLES = concat(
            Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0),
            (Iterable<Double>) tail(ORDINARY_DOUBLES)
    );

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

