package mho.haskellesque.iterables;

import mho.haskellesque.math.BasicMath;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
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
public class Exhaustive {
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
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s in ascending order.
     *
     * Length is 2<sup>8</sup>
     */
    public static final @NotNull Iterable<Byte> BYTES_ASCENDING = range(Byte.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup>
     */
    public static final @NotNull Iterable<Short> SHORTS_ASCENDING = range(Short.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order.
     *
     * Length is 2<sup>32</sup>
     */
    public static final @NotNull Iterable<Integer> INTEGERS_ASCENDING = range(Integer.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order.
     *
     * Length is 2<sup>64</sup>
     */
    public static final @NotNull Iterable<Long> LONGS_ASCENDING = range(Long.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s.
     *
     * Length is 2<sup>7</sup>&#x2212;1
     */
    public static final @NotNull Iterable<Byte> POSITIVE_BYTES = range((byte) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup>&#x2212;1
     */
    public static final @NotNull Iterable<Short> POSITIVE_SHORTS = range((short) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup>&#x2212;1
     */
    public static final @NotNull Iterable<Integer> POSITIVE_INTEGERS = range(1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup>&#x2212;1
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
     * Length is 2<sup>7</sup>
     */
    public static final @NotNull Iterable<Byte> NEGATIVE_BYTES = rangeBy((byte) -1, (byte) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup>
     */
    public static final @NotNull Iterable<Short> NEGATIVE_SHORTS = rangeBy((short) -1, (short) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup>
     */
    public static final @NotNull Iterable<Integer> NEGATIVE_INTEGERS = rangeBy(-1, -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup>
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
     * Length is 2<sup>7</sup>
     */
    public static final @NotNull Iterable<Byte> NATURAL_BYTES = range((byte) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Short</tt>s.
     *
     * Length is 2<sup>15</sup>
     */
    public static final @NotNull Iterable<Short> NATURAL_SHORTS = range((short) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Integer</tt>s.
     *
     * Length is 2<sup>31</sup>
     */
    public static final @NotNull Iterable<Integer> NATURAL_INTEGERS = range(0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Long</tt>s.
     *
     * Length is 2<sup>63</sup>
     */
    public static final @NotNull Iterable<Long> NATURAL_LONGS = range(0L);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>BigInteger</tt>s.
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> NATURAL_BIG_INTEGERS = range(BigInteger.ZERO);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s.
     *
     * Length is 2<sup>8</sup>
     */
    public static final @NotNull Iterable<Byte> BYTES = cons(
            (byte) 0,
            mux(Arrays.asList(POSITIVE_BYTES, NEGATIVE_BYTES))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s.
     *
     * Length is 2<sup>16</sup>
     */
    public static final @NotNull Iterable<Short> SHORTS = cons(
            (short) 0,
            mux(Arrays.asList(POSITIVE_SHORTS, NEGATIVE_SHORTS))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s.
     *
     * Length is 2<sup>32</sup>
     */
    public static final @NotNull Iterable<Integer> INTEGERS = cons(
            0,
            mux(Arrays.asList(POSITIVE_INTEGERS, NEGATIVE_INTEGERS))
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s.
     *
     * Length is 2<sup>64</sup>
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
     * An <tt>Iterable</tt> that contains all ASCII <tt>Characters</tt>s in ascending order.
     *
     * Length is 2<sup>7</sup>
     */
    public static final @NotNull Iterable<Character> ASCII_CHARACTERS_ASCENDING = range((char) 0, (char) 127);

    /**
     * An <tt>Iterable</tt> that contains all ASCII <tt>Characters</tt>s in an order which places "friendly" characters
     * first.
     *
     * Length is 2<sup>7</sup>
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
     * An <tt>Iterable</tt> that contains all <tt>Characters</tt>s in ascending order.
     *
     * Length is 2<sup>16</sup>
     */
    public static final @NotNull Iterable<Character> CHARACTERS_ASCENDING =
            range(Character.MIN_VALUE, Character.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Characters</tt>s in an order which places "friendly" characters
     * first.
     *
     * Length is 2<sup>16</sup>
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

    //2^31-2^23-1
    public static final Iterable<Float> POSITIVE_ORDINARY_FLOATS_ASCENDING =
            stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));

    //2^31-2^23-1
    public static final Iterable<Float> NEGATIVE_ORDINARY_FLOATS_ASCENDING =
            stopAt(f -> f == -Float.MAX_VALUE, iterate(Numbers::predecessor, -Float.MIN_VALUE));

    //2^32-2^24-1
    public static final Iterable<Float> ORDINARY_FLOATS_ASCENDING =
            concat((Iterable<Iterable<Float>>) Arrays.asList(
                    stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE)),
                    Arrays.asList(0.0f),
                    stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE))
            ));

    //2^32-2^24+3
    public static final Iterable<Float> FLOATS_ASCENDING =
            concat((Iterable<Iterable<Float>>) Arrays.asList(
                    stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, Float.NEGATIVE_INFINITY)),
                    Arrays.asList(-0.0f, Float.NaN, 0.0f),
                    stopAt(f -> f == Float.POSITIVE_INFINITY, iterate(Numbers::successor, Float.MIN_VALUE))
            ));

    //2^63-2^52-1
    public static final Iterable<Double> POSITIVE_ORDINARY_DOUBLES_ASCENDING =
            stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));

    //2^63-2^52-1
    public static final Iterable<Double> NEGATIVE_ORDINARY_DOUBLES_ASCENDING =
            stopAt(d -> d == -Double.MAX_VALUE, iterate(Numbers::predecessor, -Double.MIN_VALUE));

    //2^64-2^53-1
    public static final Iterable<Double> ORDINARY_DOUBLES_ASCENDING =
            concat((Iterable<Iterable<Double>>) Arrays.asList(
                    stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE)),
                    Arrays.asList(0.0),
                    stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE))
            ));

    //2^64-2^53+3
    public static final Iterable<Double> DOUBLES_ASCENDING =
            concat((Iterable<Iterable<Double>>) Arrays.asList(
                    stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, Double.NEGATIVE_INFINITY)),
                    Arrays.asList(-0.0, Double.NaN, 0.0),
                    stopAt(d -> d == Double.POSITIVE_INFINITY, iterate(Numbers::successor, Double.MIN_VALUE))
            ));

    private static final Iterable<Integer> FLOAT_MANTISSAS = rangeBy(1, 2, 1 << 24);

    private static final Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 127), rangeBy(-1, -1, -149)))
    );

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

    public static final Iterable<Float> NEGATIVE_ORDINARY_FLOATS = map(f -> -f, POSITIVE_ORDINARY_FLOATS);

    private static final Iterable<Long> DOUBLE_MANTISSAS = rangeBy(1L, 2, 1L << 53);

    private static final Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(range(1, 1023), rangeBy(-1, -1, -1074)))
    );

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

    public static final Iterable<Double> NEGATIVE_ORDINARY_DOUBLES = map(d -> -d, POSITIVE_ORDINARY_DOUBLES);

    public static void main(String[] args) {
        for (float i : POSITIVE_ORDINARY_FLOATS) {
            System.out.println(i);
        }
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

