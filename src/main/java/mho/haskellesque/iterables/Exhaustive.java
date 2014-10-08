package mho.haskellesque.iterables;

import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.math.Combinatorics.*;

/**
 * <tt>Iterable</tt>s that contain all (or some important subset) of a type's values. These are useful for exhaustive
 * testing. Nulls are not included by default, but may easily be added via cons.
 */
public class Exhaustive {
    /**
     * An <tt>Iterable</tt> that contains both <tt>Boolean</tt>s.
     */
    public static final @NotNull List<Boolean> BOOLEANS = Arrays.asList(false, true);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Ordering</tt>s in ascending order.
     */
    public static final @NotNull List<Ordering> ORDERED_ORDERINGS = Arrays.asList(
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
    public static final Iterable<Byte> ORDERED_BYTES = range(Byte.MIN_VALUE, Byte.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Short</tt>s in ascending order.
     */
    public static final Iterable<Short> ORDERED_SHORTS = range(Short.MIN_VALUE, Short.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Integer</tt>s in ascending order.
     */
    public static final Iterable<Integer> ORDERED_INTEGERS = range(Integer.MIN_VALUE, Integer.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all <tt>Long</tt>s in ascending order.
     */
    public static final Iterable<Long> ORDERED_LONGS = range(Long.MIN_VALUE, Long.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Byte</tt>s.
     */
    public static final Iterable<Byte> POSITIVE_BYTES = range((byte) 1, Byte.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Short</tt>s.
     */
    public static final Iterable<Short> POSITIVE_SHORTS = range((short) 1, Short.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Integer</tt>s.
     */
    public static final Iterable<Integer> POSITIVE_INTEGERS = range(1, Integer.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>Long</tt>s.
     */
    public static final Iterable<Long> POSITIVE_LONGS = range(1L, Long.MAX_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all positive <tt>BigInteger</tt>s.
     */
    public static final Iterable<BigInteger> POSITIVE_BIG_INTEGERS = range(BigInteger.ONE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Byte</tt>s.
     */
    public static final Iterable<Byte> NEGATIVE_BYTES = rangeBy((byte) -1, (byte) -1, Byte.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Short</tt>s.
     */
    public static final Iterable<Short> NEGATIVE_SHORTS = rangeBy((short) -1, (short) -1, Short.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Integer</tt>s.
     */
    public static final Iterable<Integer> NEGATIVE_INTEGERS = rangeBy(-1, -1, Integer.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>Long</tt>s.
     */
    public static final Iterable<Long> NEGATIVE_LONGS = rangeBy(-1L, -1L, Long.MIN_VALUE);

    /**
     * An <tt>Iterable</tt> that contains all negative <tt>BigInteger</tt>s.
     */
    public static final Iterable<BigInteger> NEGATIVE_BIG_INTEGERS =
            rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));

    public static final Iterable<Byte> NATURAL_BYTES = take(1 << 7, iterate(b -> (byte) (b + 1), (byte) 0));

    public static final Iterable<Short> NATURAL_SHORTS = take(1 << 15, iterate(s -> (short) (s + 1), (short) 0));

    public static final Iterable<Integer> NATURAL_INTEGERS = take(
            BigInteger.ONE.shiftLeft(31),
            iterate(i -> i + 1, 0)
    );

    public static final Iterable<Long> NATURAL_LONGS = take(
            BigInteger.ONE.shiftLeft(63),
            iterate(l -> l + 1, 0L)
    );

    public static final Iterable<BigInteger> NATURAL_BIG_INTEGERS = iterate(
            bi -> bi.add(BigInteger.ONE),
            BigInteger.ZERO
    );

    public static final Iterable<Byte> BYTES = concat(
            cons(
                    (byte) 0,
                    concatMap(b -> Arrays.asList(b, (byte) -b), POSITIVE_BYTES)
            ),
            Arrays.asList(Byte.MIN_VALUE)
    );

    public static final Iterable<Short> SHORTS = concat(
            cons(
                    (short) 0,
                    concatMap(s -> Arrays.asList(s, (short) -s), POSITIVE_SHORTS)
            ),
            Arrays.asList(Short.MIN_VALUE)
    );

    public static final Iterable<Integer> INTEGERS = concat(
            cons(
                    0,
                    concatMap(s -> Arrays.asList(s, -s), POSITIVE_INTEGERS)
            ),
            Arrays.asList(Integer.MIN_VALUE)
    );

    public static final Iterable<Long> LONGS = concat(
            cons(
                    0L,
                    concatMap(s -> Arrays.asList(s, -s), POSITIVE_LONGS)
            ),
            Arrays.asList(Long.MIN_VALUE)
    );

    public static final Iterable<BigInteger> BIG_INTEGERS = cons(
            BigInteger.ZERO,
            concatMap(bi -> Arrays.asList(bi, bi.negate()), POSITIVE_BIG_INTEGERS)
    );

    public static final Iterable<Float> ORDERED_FLOATS = () -> new Iterator<Float>() {
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

    public static final Iterable<Double> ORDERED_DOUBLES = () -> new Iterator<Double>() {
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

