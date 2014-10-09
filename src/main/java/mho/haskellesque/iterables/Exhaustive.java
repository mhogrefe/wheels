package mho.haskellesque.iterables;

import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.iterables.IterableUtils.cons;
import static mho.haskellesque.math.Combinatorics.*;

/**
 * <tt>Iterable</tt>s that contain all (or some important subset) of a type's values. These are useful for exhaustive
 * testing. Nulls are not included by default, but may easily be added via cons. The <tt>Iterable</tt>'s elements are
 * typically in order of increasing complexity, unless otherwise specified. See the test class for examples.
 */
public class Exhaustive {
    /**
     * An <tt>Iterable</tt> that contains both <tt>Boolean</tt>s.
     */
    public static final @NotNull List<Boolean> BOOLEANS = Arrays.asList(false, true);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Ordering</tt>s in ascending order.
     */
    public static final @NotNull List<Ordering> ORDERINGS_IN_ORDER = Arrays.asList(
            Ordering.LT,
            Ordering.EQ,
            Ordering.GT
    );

    /**
     * An <tt>Iterable</tt> that contains all <tt>Ordering</tt>s.
     */
    public static final @NotNull List<Ordering> ORDERINGS = Arrays.asList(Ordering.EQ, Ordering.LT, Ordering.GT);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s in ascending order.
     */
    public static final @NotNull Iterable<Byte> BYTES_IN_ORDER = range(Byte.MIN_VALUE, Byte.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order.
     */
    public static final @NotNull Iterable<Short> SHORTS_IN_ORDER = range(Short.MIN_VALUE, Short.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order.
     */
    public static final @NotNull Iterable<Integer> INTEGERS_IN_ORDER = range(Integer.MIN_VALUE, Integer.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order.
     */
    public static final @NotNull Iterable<Long> LONGS_IN_ORDER = range(Long.MIN_VALUE, Long.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s.
     */
    public static final @NotNull Iterable<Byte> POSITIVE_BYTES = range((byte) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s.
     */
    public static final @NotNull Iterable<Short> POSITIVE_SHORTS = range((short) 1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s.
     */
    public static final @NotNull Iterable<Integer> POSITIVE_INTEGERS = range(1);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s.
     */
    public static final @NotNull Iterable<Long> POSITIVE_LONGS = range(1L);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>BigInteger</tt>s.
     */
    public static final @NotNull Iterable<BigInteger> POSITIVE_BIG_INTEGERS = range(BigInteger.ONE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Byte</tt>s.
     */
    public static final @NotNull Iterable<Byte> NEGATIVE_BYTES = rangeBy((byte) -1, (byte) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s.
     */
    public static final @NotNull Iterable<Short> NEGATIVE_SHORTS = rangeBy((short) -1, (short) -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s.
     */
    public static final @NotNull Iterable<Integer> NEGATIVE_INTEGERS = rangeBy(-1, -1);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s.
     */
    public static final @NotNull Iterable<Long> NEGATIVE_LONGS = rangeBy(-1L, -1L);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigInteger</tt>s.
     */
    public static final @NotNull Iterable<BigInteger> NEGATIVE_BIG_INTEGERS =
            rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Byte</tt>s.
     */
    public static final @NotNull Iterable<Byte> NATURAL_BYTES = range((byte) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Short</tt>s.
     */
    public static final @NotNull Iterable<Short> NATURAL_SHORTS = range((short) 0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Integer</tt>s.
     */
    public static final @NotNull Iterable<Integer> NATURAL_INTEGERS = range(0);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>Long</tt>s.
     */
    public static final @NotNull Iterable<Long> NATURAL_LONGS = range(0L);

    /**
     * An <tt>Iterable</tt> that contains all natural <tt>BigInteger</tt>s.
     */
    public static final @NotNull Iterable<BigInteger> NATURAL_BIG_INTEGERS = range(BigInteger.ZERO);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Byte</tt>s.
     */
    public static final @NotNull Iterable<Byte> BYTES = cons((byte) 0, mux(Arrays.asList(POSITIVE_BYTES, NEGATIVE_BYTES)));

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s.
     */
    public static final @NotNull Iterable<Short> SHORTS = cons((short) 0, mux(Arrays.asList(POSITIVE_SHORTS, NEGATIVE_SHORTS)));

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s.
     */
    public static final @NotNull Iterable<Integer> INTEGERS = cons(0, mux(Arrays.asList(POSITIVE_INTEGERS, NEGATIVE_INTEGERS)));

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s.
     */
    public static final @NotNull Iterable<Long> LONGS = cons(0L, mux(Arrays.asList(POSITIVE_LONGS, NEGATIVE_LONGS)));

    /**
     * An <tt>Iterable</tt> that contains all <tt>BigInteger</tt>s.
     */
    public static final @NotNull Iterable<BigInteger> BIG_INTEGERS = cons(
            BigInteger.ZERO, mux(Arrays.asList(POSITIVE_BIG_INTEGERS, NEGATIVE_BIG_INTEGERS))
    );

    public static final Iterable<Float> FLOATS_IN_ORDER = () -> new Iterator<Float>() {
        private Float next = Float.NEGATIVE_INFINITY;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Float next() {
            Float current = next;
            if (next > 0 && Float.isInfinite(next)) {
                next = null;
            } else if (next.equals(Float.valueOf(-0.0f))) {
                next = Float.NaN;
            } else if (Float.isNaN(next)) {
                next = 0f;
            } else {
                next = Numbers.successor(next);
            }
            return current;
        }
    };

    public static final Iterable<Double> DOUBLES_IN_ORDER = () -> new Iterator<Double>() {
        private Double next = Double.NEGATIVE_INFINITY;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Double next() {
            Double current = next;
            if (next > 0 && Double.isInfinite(next)) {
                next = null;
            } else if (next.equals(Double.valueOf(-0.0))) {
                next = Double.NaN;
            } else if (Double.isNaN(next)) {
                next = 0.0;
            } else {
                next = Numbers.successor(next);
            }
            return current;
        }
    };

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

