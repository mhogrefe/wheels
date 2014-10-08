package mho.haskellesque.iterables;

import mho.haskellesque.math.Combinatorics;
import mho.haskellesque.numbers.Numbers;
import mho.haskellesque.ordering.Ordering;

import java.math.BigInteger;
import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.math.Combinatorics.*;

public class Exhaustive {
    public static final List<Boolean> BOOLEANS = Arrays.asList(false, true);

    public static final List<Ordering> ORDERINGS = Arrays.asList(Ordering.EQ, Ordering.LT, Ordering.GT);

    public static final Iterable<Byte> ORDERED_BYTES = take(1 << 8, iterate(b -> (byte) (b + 1), Byte.MIN_VALUE));

    public static final Iterable<Short> ORDERED_SHORTS = take(1 << 16, iterate(s -> (short) (s + 1), Short.MIN_VALUE));

    public static final Iterable<Integer> ORDERED_INTEGERS = take(
            BigInteger.ONE.shiftLeft(32),
            iterate(i -> i + 1, Integer.MIN_VALUE)
    );

    public static final Iterable<Long> ORDERED_LONGS = take(
            BigInteger.ONE.shiftLeft(64),
            iterate(l -> l + 1, Long.MIN_VALUE)
    );

    public static final Iterable<Byte> POSITIVE_BYTES = take((1 << 7) - 1, iterate(b -> (byte) (b + 1), (byte) 1));

    public static final Iterable<Short> POSITIVE_SHORTS = take((1 << 15) - 1, iterate(s -> (short) (s + 1), (short) 1));

    public static final Iterable<Integer> POSITIVE_INTEGERS = take(
            BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE),
            iterate(i -> i + 1, 1)
    );

    public static final Iterable<Long> POSITIVE_LONGS = take(
            BigInteger.ONE.shiftLeft(63).subtract(BigInteger.ONE),
            iterate(l -> l + 1, 1L)
    );

    public static final Iterable<BigInteger> POSITIVE_BIG_INTEGERS = iterate(
            bi -> bi.add(BigInteger.ONE),
            BigInteger.ONE
    );

    public static final Iterable<Byte> NEGATIVE_BYTES = take(1 << 7, iterate(b -> (byte) (b - 1), (byte) -1));

    public static final Iterable<Short> NEGATIVE_SHORTS = take(1 << 15, iterate(s -> (short) (s - 1), (short) 1));

    public static final Iterable<Integer> NEGATIVE_INTEGERS = take(
            BigInteger.ONE.shiftLeft(31),
            iterate(i -> i - 1, -1)
    );

    public static final Iterable<Long> NEGATIVE_LONGS = take(
            BigInteger.ONE.shiftLeft(63),
            iterate(l -> l - 1, -1L)
    );

    public static final Iterable<BigInteger> NEGATIVE_BIG_INTEGERS = iterate(
            bi -> bi.subtract(BigInteger.ONE),
            BigInteger.ONE.negate()
    );

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

