package mho.wheels.iterables;

import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.wheels.ordering.Ordering.*;

/**
 * Methods for generating and manipulating {@link Iterable}s. The equivalents of every function in Haskell's
 * {@code Data.List} module may be found here (except for {@code permutations} and {@code subsequences}, which are in
 * {@link mho.wheels.math.Combinatorics}).
 */
public final class IterableUtils {
    /**
     * Disallow instantiation
     */
    private IterableUtils() {}

    /**
     * Adds an {@code Iterable}'s elements to a {@link Collection}, in the order that the elements appear in the
     * {@code Iterable}. Only works for finite {@code Iterable}s.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>{@code collection} must be non-null.</li>
     *  <li>{@code collection} must be able to hold every element of {@code xs}.</li>
     * </ul>
     *
     * @param xs the {@code Iterable}
     * @param collection the {@code Collection} to which the {@code Iterable}'s elements are added
     * @param <T> the {@code Iterable}'s element type
     */
    public static <T> void addTo(@NotNull Iterable<T> xs, @NotNull Collection<T> collection) {
        for (T x : xs) {
            collection.add(x);
        }
    }

    /**
     * Adds a {@code String}'s characters to a {@code Collection}, in the order that the characters appear in the
     * {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>{@code collection} must be non-null.</li>
     *  <li>{@code collection} must be able to hold every character of {@code s}.</li>
     * </ul>
     *
     * @param s the string
     * @param collection the collection to which the {@code String}'s characters are added
     */
    public static void addTo(@NotNull String s, @NotNull Collection<Character> collection) {
        for (int i = 0; i < s.length(); i++) {
            collection.add(s.charAt(i));
        }
    }

    /**
     * Converts an {@code Iterable} to a {@link List}. Only works for finite {@code Iterable}s. The resulting list may
     * be modified, but the modifications will not affect the original {@code Iterable}.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param xs the {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return a {@code List} containing the elements of the {@code Iterable} in their original order
     */
    public static @NotNull <T> List<T> toList(@NotNull Iterable<T> xs) {
        List<T> list = new ArrayList<>();
        addTo(xs, list);
        return list;
    }

    /**
     * Converts an {@code Iterable} to a {@code List}. Only works for finite {@code Iterable}s.
     *
     * <ul>
     *  <li>{@code s} may be any {@code String}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the {@code String}
     * @return a {@code List} containing the characters of {@code s} in their original order
     */
    public static @NotNull List<Character> toList(@NotNull String s) {
        List<Character> list = new ArrayList<>();
        addTo(s, list);
        return list;
    }

    /**
     * Creates a {@code String} representation of {@code xs}. Each element is converted to a {@code String} and
     * those {@code String}s are placed in a comma-separated list surrounded by square brackets. Only works for finite
     * {@code Iterable}s.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>The result begins with {@code '['} and ends with {@code ']'}.</li>
     * </ul>
     *
     * @param xs the {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return a {@code String} representation of {@code xs}
     */
    public static @NotNull <T> String toString(@NotNull Iterable<T> xs) {
        return toList(xs).toString();
    }

    /**
     * Creates a {@code String} representation of {@code xs}, displaying at most {@code size} elements. The first
     * {@code size} elements are converted to a {@code String} and those {@code String}s are placed in a
     * comma-separated list surrounded by square brackets. If the {@code Iterable} contains more than {@code size}
     * elements, an ellipsis ({@code ...}) is added at the end of the list.
     *
     * <ul>
     *  <li>{@code size} must be non-negative.</li>
     *  <li>{@code xs} may be any {@code Iterable}.</li>
     *  <li>The result begins with {@code '['} and ends with {@code ']'}.</li>
     * </ul>
     *
     * @param size the maximum number of elements displayed
     * @param xs the {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return a {@code String} representation of {@code xs}
     */
    public static @NotNull <T> String toString(int size, @NotNull Iterable<T> xs) {
        if (size < 0)
            throw new IllegalArgumentException("size cannot be negative");
        if (size == 0) {
            return isEmpty(xs) ? "[]" : "[...]";
        }
        List<T> list = toList(take(size + 1, xs));
        String listString = toList(take(size, list)).toString();
        if (list.size() > size) {
            listString = init(listString) + ", ...]";
        }
        return listString;
    }

    /**
     * Converts a {@code String} to an {@code Iterable} of {@code Character}s. The order of the characters is
     * preserved. Uses O(1) additional memory. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is finite and does not contain any nulls.</li>
     * </ul>
     *
     * @param s the {@code String}
     * @return an {@code Iterable} containing all the {@code String}'s characters in their original order
     */
    public static @NotNull Iterable<Character> fromString(@NotNull String s) {
        return () -> new Iterator<Character>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < s.length();
            }

            @Override
            public Character next() {
                return s.charAt(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Creates a {@code String} from an {@code Iterable} of {@code Character}s. The order of the characters is
     * preserved. Only works for finite {@code Iterable}s.
     *
     * <ul>
     *  <li>{@code cs} must be finite and cannot contain nulls.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param cs the {@code Iterable} of {@code Character}s
     * @return the {@code String} containing all of {@code chars}'s characters in their original order
     */
    public static @NotNull String charsToString(@NotNull Iterable<Character> cs) {
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Generates all {@link Byte}s greater than or equal to {@code a}, in order. Does not wrap around after reaching
     * {@code Byte.MAX_VALUE}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>The result is an {@code Iterable} of consecutive ascending {@code Byte}s ending in 2<sup>7</sup>–1.</li>
     * </ul>
     *
     * Length is 2<sup>7</sup>–{@code a}
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Byte> range(byte a) {
        return range(a, Byte.MAX_VALUE);
    }

    /**
     * Generates all {@link Short}s greater than or equal to {@code a}, in order. Does not wrap around after reaching
     * {@code Short.MAX_VALUE}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>The result is an {@code Iterable} of consecutive ascending {@code Short}s ending in 2<sup>15</sup>–1.</li>
     * </ul>
     *
     * Length is 2<sup>15</sup>–{@code a}
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Short> range(short a) {
        return range(a, Short.MAX_VALUE);
    }

    /**
     * Generates all {@link Integer}s greater than or equal to {@code a}, in order. Does not wrap around after reaching
     * {@code Integer.MAX_VALUE}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is an {@code Iterable} of consecutive ascending {@code Integer}s ending in
     *  2<sup>31</sup>–1.</li>
     * </ul>
     *
     * Length is 2<sup>31</sup>–{@code a}
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Integer> range(int a) {
        return range(a, Integer.MAX_VALUE);
    }

    /**
     * Generates all {@link Long}s greater than or equal to {@code a}, in order. Does not wrap around after reaching
     * {@code Long.MAX_VALUE}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>The result is an {@code Iterable} of consecutive ascending {@code Long}s ending in 2<sup>63</sup>–1.</li>
     * </ul>
     *
     * Length is 2<sup>63</sup>–{@code a}
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Long> range(long a) {
        return range(a, Long.MAX_VALUE);
    }

    /**
     * Generates all {@link BigInteger}s greater than or equal to {@code a}, in order. The {@code Iterable} produced
     * does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} must be non-null.</li>
     *  <li>The result is an infinite {@code Iterable} of consecutive ascending {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<BigInteger> range(@NotNull BigInteger a) {
        return iterate(i -> i.add(BigInteger.ONE), a);
    }

    /**
     * Generates all {@link BigDecimal}s of the form {@code a}+n where n is a non-negative integer, in order. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} must be non-null.</li>
     *  <li>The result is an infinite {@code Iterable} of ascending {@code BigDecimal}s differing by 1.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<BigDecimal> range(@NotNull BigDecimal a) {
        return iterate(i -> i.add(BigDecimal.ONE), a);
    }

    /**
     * Generates all {@link Character}s greater than or equal to {@code a}, in order. Does not wrap around after
     * reaching {@code Character.MAX_VALUE}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>The result is an {@code Iterable} of consecutive ascending {@code Character}s ending in
     *  {@code \uffff}.</li>
     * </ul>
     *
     * Length is 2<sup>16</sup>–{@code a}
     *
     * @param a the starting value of this {@code Character} sequence
     * @return an sequence of consecutive {@code Character}s, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Character> range(char a) {
        return range(a, Character.MAX_VALUE);
    }

    /**
     * Generates all {@link float}s roughly of the form {@code a}+n where n is a non-negative integer, in order.
     * {@code a} is converted to a {@code BigDecimal} internally to minimize rounding errors. Nonetheless, rounding may
     * produce some odd-seeming results: for example, if {@code a} is large, the result might contain runs of identical
     * {@code float}s. If {@code a} is {@code -Infinity}, the result is {@code -Infinity} repeating forever. If
     * {@code a} is {@code +Infinity}, the result is a single {@code +Infinity}. If {@code a} is negative zero, the
     * first element of the result is also negative zero. {@code NaN} is not a legal input. The {@code Iterable}
     * produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is either {@code [+Infinity]}, or an infinite non-descending {@code Iterable} of {@code float}s
     *  roughly differing by 1.</li>
     * </ul>
     *
     * Length is 1 if {@code a} is {@code +Infinity}, infinite otherwise
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Float> range(float a) {
        if (Float.isNaN(a))
            throw new IllegalArgumentException("cannot begin a range with NaN");
        if (Float.isInfinite(a)) {
            return a < 0 ? cycle(Arrays.asList(Float.NEGATIVE_INFINITY)) : Arrays.asList(Float.POSITIVE_INFINITY);
        }
        Iterable<Float> fs = map(BigDecimal::floatValue, range(new BigDecimal(Float.toString(a))));
        return Float.valueOf(a).equals(-0.0f) ? cons(-0.0f, tail(fs)): fs;
    }

    /**
     * Generates all {@link double}s roughly of the form {@code a}+n where n is a non-negative integer, in order.
     * {@code a} is converted to a {@code BigDecimal} internally to minimize rounding errors. Nonetheless, rounding may
     * produce some odd-seeming results: for example, if {@code a} is large, the result might contain runs of identical
     * {@code double}s. If {@code a} is {@code -Infinity}, the result is {@code -Infinity} repeating forever. If
     * {@code a} is {@code +Infinity}, the result is a single {@code +Infinity}. If {@code a} is negative zero, the
     * first element of the result is also negative zero. {@code NaN} is not a legal input. The {@code Iterable}
     * produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is either {@code [+Infinity]}, or an infinite non-descending {@code Iterable} of {@code double}s
     *  roughly differing by 1.</li>
     * </ul>
     *
     * Length is 1 if {@code a} is {@code +Infinity}, infinite otherwise
     *
     * @param a the starting value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive)
     */
    public static @NotNull Iterable<Double> range(double a) {
        if (Double.isNaN(a))
            throw new IllegalArgumentException("cannot begin a range with NaN");
        if (Double.isInfinite(a)) {
            return a < 0 ? cycle(Arrays.asList(Double.NEGATIVE_INFINITY)) : Arrays.asList(Double.POSITIVE_INFINITY);
        }
        Iterable<Double> ds = map(BigDecimal::doubleValue, range(BigDecimal.valueOf(a)));
        return Double.valueOf(a).equals(-0.0) ? cons(-0.0, tail(ds)) : ds;
    }

    /**
     * Generates all {@code Byte}s greater than or equal to {@code a} and less than or equal to {@code b}, in order.
     * If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced does not
     * support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>{@code b} may be any {@code byte}.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code Byte}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code Short}s greater than or equal to {@code a} and less than or equal to {@code b}, in order.
     * If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced does not
     * support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>{@code b} may be any {@code short}.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code Short}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<Short> range(short a, short b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code Integer}s greater than or equal to {@code a} and less than or equal to {@code b}, in order.
     * If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced does not
     * support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>{@code b} may be any {@code int}.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code Integer}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<Integer> range(int a, int b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code Long}s greater than or equal to {@code a} and less than or equal to {@code b}, in order.
     * If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced does not
     * support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>{@code b} may be any {@code long}.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code Long}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<Long> range(long a, long b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code BigInteger}s greater than or equal to {@code a} and less than or equal to {@code b}, in
     * order. If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced
     * does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} must be non-null.</li>
     *  <li>{@code b} must be non-null.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (gt(a, b)) return new ArrayList<>();
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                reachedEnd = x.equals(b);
                BigInteger oldX = x;
                x = x.add(BigInteger.ONE);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@link BigDecimal}s greater than or equal to {@code a} and less than or equal to {@code b} of the
     * form {@code a}+n where n is an integer, in order. If {@code a}{@literal >}{@code b}, an empty {@code Iterable}
     * is returned. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} must be non-null.</li>
     *  <li>{@code b} must be non-null.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive {@code BigDecimal}s differing by 1.</li>
     * </ul>
     *
     * Length is max(⌊{@code b}–{@code a}⌋+1, 0)
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<BigDecimal> range(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        if (gt(a, b)) return new ArrayList<>();
        return () -> new Iterator<BigDecimal>() {
            private BigDecimal x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigDecimal next() {
                reachedEnd = gt(x.add(BigDecimal.ONE), b);
                BigDecimal oldX = x;
                x = x.add(BigDecimal.ONE);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code Character}s greater than or equal to {@code a} and less than or equal to {@code b}, in
     * order. If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. The {@code Iterable} produced
     * does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>{@code b} may be any {@code char}.</li>
     *  <li>The result is a possibly-empty {@code Iterable} of consecutive ascending {@code Character}s.</li>
     * </ul>
     *
     * Length is max({@code b}–{@code a}+1, 0)
     *
     * @param a the starting value of this {@code Character} sequence
     * @param b the ending value of this {@code Character} sequence
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at
     * {@code b} (inclusive)
     */
    public static @NotNull Iterable<Character> range(char a, char b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Generates all {@code float}s greater than or equal to {@code a} and less than or equal to {@code b} roughly of
     * the form {@code a}+n where n is a non-negative integer, in order. {@code a} and {@code b} are converted to
     * {@code BigDecimal}s internally to minimize rounding errors. Nonetheless, rounding may produce some odd-seeming
     * results: for example, if {@code a} is large, the result might contain runs of identical {@code float}s. If
     * {@code a}{@literal >}{@code b}, the result is empty. If {@code a}={@code b}, an {@code Iterable} containing only
     * {@code a} is returned. If {@code a} is {@code -Infinity} and {@code b} is not {@code -Infinity}, the result is
     * {@code -Infinity} repeating forever. If {@code a} is negative zero and {@code b} is nonnegative, the first
     * element of the result is also negative zero. Neither {@code a} nor {@code b} may be {@code NaN}. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>{@code b} cannot be {@code NaN}.</li>
     *  <li>The result is a possibly-empty non-descending {@code Iterable} of {@code float}s roughly differing by
     *  1.</li>
     * </ul>
     *
     * Length is 0 if {@code a}{@literal >}{@code b}, 1 if {@code a}={@code b}, infinite if {@code a} is
     * {@code -Infinity} or {@code b} is {@code Infinity}, and ⌊{@code new BigDecimal(b)}–{@code new BigDecimal(a)}⌋+1
     * otherwise
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at the
     * largest {@code float} an integer away from {@code a} and less than or equal to {@code b}.
     */
    public static @NotNull Iterable<Float> range(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b))
            throw new IllegalArgumentException("cannot begin or end a range with NaN");
        if (a == b) return Arrays.asList(a);
        if (a > b) return new ArrayList<>();
        if (Float.isInfinite(a)) {
            return a < 0 ? cycle(Arrays.asList(Float.NEGATIVE_INFINITY)) : Arrays.asList(Float.POSITIVE_INFINITY);
        }
        if (Float.isInfinite(b)) {
            return range(a);
        }
        Iterable<Float> fs = map(
                BigDecimal::floatValue,
                range(new BigDecimal(Float.toString(a)), new BigDecimal(Float.toString(b)))
        );
        return Float.valueOf(a).equals(-0.0f) ? cons(-0.0f, tail(fs)): fs;
    }

    /**
     * Generates all {@code double}s greater than or equal to {@code a} and less than or equal to {@code b} roughly of
     * the form {@code a}+n where n is a non-negative integer, in order. {@code a} and {@code b} are converted to
     * {@code BigDecimal}s internally to minimize rounding errors. Nonetheless, rounding may produce some odd-seeming
     * results: for example, if {@code a} is large, the result might contain runs of identical {@code double}s. If
     * {@code a}{@literal >}{@code b}, the result is empty. If {@code a}={@code b}, an {@code Iterable} containing only
     * {@code a} is returned. If {@code a} is {@code -Infinity} and {@code b} is not {@code -Infinity}, the result is
     * {@code -Infinity} repeating forever. If {@code a} is negative zero and {@code b} is nonnegative, the first
     * element of the result is also negative zero. Neither {@code a} nor {@code b} may be {@code NaN}. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>{@code b} cannot be {@code NaN}.</li>
     *  <li>The result is a possibly-empty non-descending {@code Iterable} of {@code double}s roughly differing by
     *  1.</li>
     * </ul>
     *
     * Length is 0 if {@code a}{@literal >}{@code b}, 1 if {@code a}={@code b}, infinite if {@code a} is
     * {@code -Infinity} or {@code b} is {@code Infinity}, and ⌊{@code new BigDecimal(b)}–{@code new BigDecimal(a)}⌋+1
     * otherwise
     *
     * @param a the starting value of this arithmetic progression
     * @param b the ending value of this arithmetic progression
     * @return an arithmetic progression with an increment of 1, starting at {@code a} (inclusive) and ending at the
     * largest {@code double} an integer away from {@code a} and less than or equal to {@code b}.
     */
    public static @NotNull Iterable<Double> range(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b))
            throw new IllegalArgumentException("cannot begin or end a range with NaN");
        if (a == b) return Arrays.asList(a);
        if (a > b) return new ArrayList<>();
        if (Double.isInfinite(a)) {
            return a < 0 ? cycle(Arrays.asList(Double.NEGATIVE_INFINITY)) : Arrays.asList(Double.POSITIVE_INFINITY);
        }
        if (Double.isInfinite(b)) {
            return range(a);
        }
        Iterable<Double> ds = map(
                BigDecimal::doubleValue,
                range(new BigDecimal(Double.toString(a)), new BigDecimal(Double.toString(b)))
        );
        return Double.valueOf(a).equals(-0.0) ? cons(-0.0, tail(ds)): ds;
    }

    public static @NotNull Iterable<Byte> rangeBy(byte a, byte i) {
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                byte oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Short> rangeBy(short a, short i) {
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                short oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Integer> rangeBy(int a, int i) {
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                int oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Long> rangeBy(long a, long i) {
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                long oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i) {
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                BigInteger oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? lt(x, a) : gt(x, a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<BigDecimal> rangeBy(@NotNull BigDecimal a, @NotNull BigDecimal i) {
        return () -> new Iterator<BigDecimal>() {
            private BigDecimal x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigDecimal next() {
                BigDecimal oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? lt(x, a) : gt(x, a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Character> rangeBy(char a, int i) {
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                char oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Byte> rangeBy(byte a, byte i, byte b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                byte oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Short> rangeBy(short a, short i, short b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                short oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Integer> rangeBy(int a, int i, int b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                int oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Long> rangeBy(long a, long i, long b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                long oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<BigInteger> rangeBy(BigInteger a, BigInteger i, BigInteger b) {
        if (i.signum() == 1 ? gt(a, b) : gt(b, a)) return new ArrayList<>();
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                BigInteger oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? gt(x, b) : lt(x, b);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<BigDecimal> rangeBy(BigDecimal a, BigDecimal i, BigDecimal b) {
        if (i.signum() == 1 ? gt(a, b) : gt(b, a)) return new ArrayList<>();
        return () -> new Iterator<BigDecimal>() {
            private BigDecimal x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigDecimal next() {
                BigDecimal oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? gt(x, b) : lt(x, b);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Character> rangeBy(char a, int i, char b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                char oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code (:)} list constructor. Creates an {@code Iterable} whose first element is
     * {@code x} and whose remaining elements are given by {@code xs}. {@code xs} may be infinite, in which case the
     * result is also infinite. Uses O(1) additional memory. The {@code Iterable} produced does not support removing
     * elements.
     *
     * <ul>
     *  <li>{@code x} can be anything.</li>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result is a non-empty {@code Iterable}.</li>
     * </ul>
     *
     * Result length is |{@code xs}|+1
     *
     * @param x the first element of the {@code Iterable} to be created
     * @param xs the second-through-last elements of the {@code Iterable} to be created
     * @param <T> the element type of the {@code Iterable} to be created
     * @return the {@code Iterable} to be created
     */
    public static @NotNull <T> Iterable<T> cons(@Nullable T x, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private boolean readHead = false;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return !readHead || xsi.hasNext();
            }

            @Override
            public T next() {
                if (readHead) {
                    return xsi.next();
                } else {
                    readHead = true;
                    return x;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code (:)} list constructor. Creates a {@code String} whose first character is
     * {@code c} and whose remaining characters are given by {@code cs}. Uses O(n) additional memory, where n is the
     * length of cs.
     *
     * <ul>
     *  <li>{@code c} can be anything.</li>
     *  <li>{@code cs} must be non-null.</li>
     *  <li>The result is a non-empty {@code String}.</li>
     * </ul>
     *
     * Result length is |{@code cs}|+1
     *
     * @param c the first character of the {@code String} to be created
     * @param cs the second-through-last characters of the {@code String} to be created
     * @return the {@code String} to be created
     */
    public static @NotNull String cons(char c, @NotNull String cs) {
        return Character.toString(c) + cs;
    }

    /**
     * Equivalent of Haskell's {@code (++)} operator. Creates an {@code Iterable} consisting of {@code xs}'s
     * elements followed by {@code ys}'s elements. {@code xs} may be infinite, in which case the result will be equal
     * to {@code xs}. {@code ys} may be infinite, in which case the result will also be infinite. Uses O(1)
     * additional memory. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>{@code ys} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code xs}|+|{@code ys}|
     *
     * @param xs an {@code Iterable}
     * @param ys another {@code Iterable}
     * @param <T> the element type of the {@code Iterable} to be created
     * @return {@code xs} concatenated with {@code ys}
     */
    public static @NotNull <T> Iterable<T> concat(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private final Iterator<T> ysi = ys.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext() || ysi.hasNext();
            }

            @Override
            public T next() {
                return (xsi.hasNext() ? xsi : ysi).next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code (++)} operator. Creates a {@code String} consisting of {@code s}'s characters
     * followed by {@code t}'s characters. Uses O(n+m) additional memory, where n is the length of {@code s} and m is
     * the length of {@code t}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>{@code t} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code s}|+|{@code t}|
     *
     * @param s a {@code String}
     * @param t a {@code String}
     * @return {@code s} concatenated with {@code t}
     */
    public static @NotNull String concat(@NotNull String s, @NotNull String t) {
        return s + t;
    }

    /**
     * Equivalent of Haskell's {@code head} function. Returns the first element of an {@code Iterable}. Works on
     * infinite {@code Iterable}s. Uses O(1) additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return the {@code Iterable}'s first element
     */
    public static @Nullable <T> T head(@NotNull Iterable<T> xs) {
        return xs.iterator().next();
    }

    /**
     * Equivalent of Haskell's {@code head} function. Returns the first element of a {@code List}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a {@code List}
     * @param <T> the {@code List}'s element type
     * @return the {@code List}'s first element
     */
    public static @Nullable <T> T head(@NotNull List<T> xs) {
        return xs.get(0);
    }

    /**
     * Equivalent of Haskell's {@code head} function. Returns the first element of a {@code SortedSet}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a {@code SortedSet}
     * @param <T> the {@code SortedSet}'s element type
     * @return the {@code SortedSet}'s first element
     */
    public static @Nullable <T> T head(@NotNull SortedSet<T> xs) {
        return xs.first();
    }

    /**
     * Equivalent of Haskell's {@code head} function. Returns the first character of a {@code String}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code s} must be non-empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return the {@code String}'s first character
     */
    public static char head(@NotNull String s) {
        return s.charAt(0);
    }

    /**
     * Equivalent of Haskell's {@code last} function. Returns the last element of an {@code Iterable}. Only works on
     * finite {@code Iterable}s. Uses O(1) additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty and finite.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return the {@code Iterable}'s last element
     */
    public static @Nullable <T> T last(@NotNull Iterable<T> xs) {
        T previous = null;
        boolean empty = true;
        for (T x : xs) {
            empty = false;
            previous = x;
        }
        if (empty)
            throw new NoSuchElementException();
        return previous;
    }

    /**
     * Equivalent of Haskell's {@code last} function. Returns the last element of a {@code List}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a {@code List}
     * @param <T> the {@code List}'s element type
     * @return the {@code List}'s last element
     */
    public static @Nullable <T> T last(@NotNull List<T> xs) {
        return xs.get(xs.size() - 1);
    }

    /**
     * Equivalent of Haskell's {@code last} function. Returns the last element of a {@code SortedSet}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a {@code SortedSet}
     * @param <T> the {@code SortedSet}'s element type
     * @return the {@code SortedSet}'s last element
     */
    public static @Nullable <T> T last(@NotNull SortedSet<T> xs) {
        return xs.last();
    }

    /**
     * Equivalent of Haskell's {@code last} function. Returns the last character of a {@code String}. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li>{@code s} must be non-empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return the {@code String}'s last character
     */
    public static char last(@NotNull String s) {
        return s.charAt(s.length() - 1);
    }

    /**
     * Equivalent of Haskell's {@code tail} function. Returns all elements of an {@code Iterable} but the first.
     * {@code xs} may be infinite, in which the result will also be infinite. Uses O(1) additional memory. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code xs}|–1
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return an {@code Iterable} containing all elements of {@code xs} but the first
     */
    public static @NotNull <T> Iterable<T> tail(@NotNull Iterable<T> xs) {
        if (isEmpty(xs))
            throw new NoSuchElementException();
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                xsi.next();
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code tail} function. Given a {@code String}, returns a {@code String} containing
     * all of its characters but the first. Uses O(n) additional memory, where n is the length of {@code s}.
     *
     * <ul>
     *  <li>{@code s} must be non-empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * Result length is |{@code s}|–1
     *
     * @param s a {@code String}
     * @return a {@code String} containing all characters of {@code s} but the first
     */
    public static @NotNull String tail(@NotNull String s) {
        return s.substring(1);
    }

    /**
     * Equivalent of Haskell's {@code init} function. Returns all elements of an {@code Iterable} but the last.
     * {@code xs} may be infinite, in which the result will be {@code xs}. Uses O(1) additional memory. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code xs} must be non-empty.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code xs}|–1
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return an {@code Iterable} containing all elements of {@code xs} but the last
     */
    public static @NotNull <T> Iterable<T> init(@NotNull Iterable<T> xs) {
        if (isEmpty(xs))
            throw new NoSuchElementException();
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next = xsi.next();

                    @Override
                    public boolean hasNext() {
                        return xsi.hasNext();
                    }

                    @Override
                    public T next() {
                        T oldNext = next;
                        next = xsi.next();
                        return oldNext;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code tail} function. Given a {@code String}, returns a {@code String} containing
     * all of its characters but the last. Uses O(n) additional memory, where n is the length of {@code s}.
     *
     * <ul>
     *  <li>{@code s} must be non-empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * Result length is |{@code s}|–1
     *
     * @param s a {@code String}
     * @return a {@code String} containing all characters of {@code s} but the last
     */
    public static @NotNull String init(@NotNull String s) {
        return s.substring(0, s.length() - 1);
    }

    /**
     * Equivalent of Haskell's {@code null} function. Tests whether an {@code Iterable} contains no elements.
     * {@code xs} may be infinite. Uses O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return whether {@code xs} is empty
     */
    public static <T> boolean isEmpty(@NotNull Iterable<T> xs) {
        return !xs.iterator().hasNext();
    }

    /**
     * Equivalent of Haskell's {@code null} function. Tests whether a {@code Collection} contains no elements. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param xs a {@code Collection}
     * @param <T> the {@code Collection}'s element type
     * @return whether {@code xs} is empty
     */
    public static <T> boolean isEmpty(@NotNull Collection<T> xs) {
        return xs.isEmpty();
    }

    /**
     * Equivalent of Haskell's {@code null} function. Tests whether a {@code String} contains no characters. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return whether {@code s} is empty
     */
    public static boolean isEmpty(@NotNull String s) {
        return s.isEmpty();
    }

    /**
     * Equivalent of Haskell's {@code length} function. Returns the number of elements in an {@code Iterable}. Only
     * works on finite {@code Iterable}s. Uses O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return the {@code Iterable}'s length
     */
    public static <T> int length(@NotNull Iterable<T> xs) {
        int i = 0;
        for (T x : xs) {
            i++;
        }
        return i;
    }

    /**
     * Equivalent of Haskell's {@code length} function. Returns the number of elements in an {@code Iterable}. Only
     * works on finite {@code Iterable}s. Uses O(log(n)) additional space, where n is {@code xs}'s length; but it's
     * effectively constant space.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return the {@code Iterable}'s length
     */
    public static @NotNull <T> BigInteger bigIntegerLength(@NotNull Iterable<T> xs) {
        BigInteger i = BigInteger.ZERO;
        for (T x : xs) {
            i = i.add(BigInteger.ONE);
        }
        return i;
    }

    /**
     * Equivalent of Haskell's {@code length} function. Returns the number of elements in a {@code Collection}. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs a {@code Collection}
     * @param <T> the {@code Collection}'s element type
     * @return the {@code Collection}'s length
     */
    public static <T> int length(@NotNull Collection<T> xs) {
        return xs.size();
    }

    /**
     * Equivalent of Haskell's {@code length} function. Returns the number of characters in a {@code String}. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return the {@code String}'s length
     */
    public static int length(@NotNull String s) {
        return s.length();
    }

    //todo docs
    public static <T> boolean lengthAtLeast(int length, @NotNull Iterable<T> xs) {
        int i = 0;
        for (T x : xs) {
            i++;
            if (i >= length) return true;
        }
        return false;
    }

    public static <T> boolean lengthAtLeast(int length, @NotNull Collection<T> xs) {
        return xs.size() >= length;
    }

    public static <T> boolean lengthAtLeast(int length, @NotNull String s) {
        return s.length() >= length;
    }

    /**
     * Equivalent of Haskell's {@code map} function. Transforms one {@code Iterable} into another by applying a
     * function to each element. {@code xs} may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code f} must be non-null.</li>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>{@code xs} must only contain elements that are valid inputs for {@code f}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code xs}|
     *
     * @param f the function that transforms each element in the {@code Iterable}
     * @param xs the {@code Iterable}
     * @param <A> the type of the original {@code Iterable}'s elements
     * @param <B> the type of the output {@code Iterable}'s elements
     * @return an {@code Iterable} containing the elements of {@code xs} transformed by {@code f}
     */
    public static @NotNull <A, B> Iterable<B> map(@NotNull Function<A, B> f, @NotNull Iterable<A> xs) {
        return () -> new Iterator<B>() {
            private final Iterator<A> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public B next() {
                return f.apply(xsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code map} function. Transforms one {@code String} into another by applying a
     * function to each character. Uses O(n) additional memory, where n is the length of the input string.
     *
     * <ul>
     *  <li>{@code f} must be non-null.</li>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>{@code xs} must only contain characters that are valid inputs for {@code f}.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code s}|
     *
     * @param f the function that transforms each character in the {@code String}
     * @param s the {@code String}
     * @return a {@code String} containing the characters of {@code s} transformed by {@code f}
     */
    public static @NotNull String map(@NotNull Function<Character, Character> f, @NotNull String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(f.apply(s.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * Equivalent of Haskell's {@code reverse} function. Reverses an {@code Iterable}. {@code xs} must be finite.
     * Uses O(n) additional memory, where n is the length of {@code xs}. The resulting list may be modified, but the
     * modifications will not affect the original {@code Iterable}.
     *
     * <ul>
     *  <li>{@code xs} must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code xs}|
     *
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return a {@code List} containing {@code xs}'s elements in reverse order
     */
    public static @NotNull <T> List<T> reverse(@NotNull Iterable<T> xs) {
        List<T> list = toList(xs);
        Collections.reverse(list);
        return list;
    }

    /**
     * Equivalent of Haskell's {@code reverse} function. Reverses a {@code String}. Uses O(n) additional memory,
     * where n is the length of {@code s}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |{@code s}|
     *
     * @param s a {@code String}
     * @return a {@code String} containing {@code s}'s characters in reverse order
     */
    public static @NotNull String reverse(@NotNull String s) {
        char[] reversed = new char[s.length()];
        for (int i = 0; i < s.length() / 2; i++) {
            int j = s.length() - i - 1;
            reversed[i] = s.charAt(j);
            reversed[j] = s.charAt(i);
        }
        if ((s.length() & 1) == 1) {
            int i = s.length() / 2;
            reversed[i] = s.charAt(i);
        }
        return new String(reversed);
    }

    /**
     * Equivalent of Haskell's {@code intersperse} function. Given an {@code Iterable} {@code xs} and a seperator
     * {@code sep}, returns an {@code Iterable} consisting of the elements of {@code xs} with {@code sep} between
     * every adjacent pair. {@code xs} may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code sep} may be anything.</li>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>The result is an {@code Iterable} whose odd-indexed (using 0-based indexing) elements are identical.</li>
     * </ul>
     *
     * Result length is 0 when |{@code xs}|=0, 2|{@code xs}|–1 otherwise
     *
     * @param sep a separator
     * @param xs an {@code Iterable}
     * @param <T> the {@code Iterable}'s element type
     * @return an {@code Iterable} consisting of the elements of {@code xs} interspersed with {@code sep}
     */
    public static @NotNull <T> Iterable<T> intersperse(@Nullable T sep, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private boolean separating = false;

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                if (separating) {
                    separating = false;
                    return sep;
                } else {
                    separating = true;
                    return xsi.next();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code intersperse} function. Given a {@code String} {@code s} and a seperator
     * {@code sep}, returns a {@code String} consisting of the characters of {@code s} with {@code sep} between
     * every adjacent pair. Uses O(n) additional memory, where n is the length of {@code s}.
     *
     * <ul>
     *  <li>{@code sep} may be any {@code char}.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is a {@code String} whose odd-indexed (using 0-based indexing) characters are identical.</li>
     * </ul>
     *
     * Result length is 0 when |{@code s}|=0, 2|{@code s}|–1 otherwise
     *
     * @param sep a separator
     * @param s a {@code String}
     * @return a {@code String} consisting of the characters of {@code s} interspersed with {@code sep}
     */
    public static @NotNull String intersperse(char sep, @NotNull String s) {
        if (s.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            sb.append(sep);
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    /**
     * Equivalent of Haskell's {@code intercalate} function. Inserts an {@code Iterable} between every two adjacent
     * {@code Iterable}s in an {@code Iterable} of {@code Iterable}s, flattening the result. {@code xss}, any
     * element of {@code xss}, or {@code xs} may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The {@code Iterable} produced does not support removing elements.
     *
     * Result length is the sum of the lengths of {@code xs}'s elements and (0 if |{@code xss}|=0,
     * |{@code xss}|(|{@code xs}|–1) otherwise)
     *
     * <ul>
     *  <li>{@code xs} must be non-null.</li>
     *  <li>{@code xss} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param xs the separating {@code Iterable}
     * @param xss the separated {@code Iterable}
     * @param <T> the resulting {@code Iterable}'s element type
     * @return {@code xss} intercalated by {@code xs}
     */
    public static @NotNull <T> Iterable<T> intercalate(@NotNull Iterable<T> xs, @NotNull Iterable<Iterable<T>> xss) {
        return concat(intersperse(xs, xss));
    }

    /**
     * Equivalent of Haskell's {@code intercalate} function. Inserts a {@code String} between every two adjacent
     * {@code String}s in an {@code Iterable} of {@code String}s, flattening the result. Uses O(abc) additional
     * memory, where a is the length of {@code strings}, b is the maximum length of any string in {@code strings},
     * and c is the length of {@code sep}.
     * The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code sep} must be non-null.</li>
     *  <li>{@code strings} must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is the sum of the lengths of {@code xs}'s elements and (0 if |{@code strings}|=0,
     * |{@code strings}|(|{@code sep}|–1) otherwise)
     *
     * @param sep the separating {@code String}
     * @param strings the separated {@code String}s
     * @return {@code strings} intercalated by {@code sep}
     */
    public static @NotNull String intercalate(@NotNull String sep, @NotNull Iterable<String> strings) {
        return concatStrings(intersperse(sep, strings));
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code Iterables}. If the rows have different lengths, then the "overhanging" elements still end up in the
     * result. See test cases for examples. Any element of {@code xss} may be infinite, in which case the result will
     * be infinite. Uses O(nm) additional memory, where n is then length of {@code xss} and m is the largest amount of
     * memory used by any {@code Iterable} in {@code xss}. The {@code Iterable} produced does not support removing
     * elements.
     *
     * <ul>
     *  <li>{@code xss} must be finite.</li>
     *  <li>The lengths of the result's elements are finite, non-increasing, and never 0.</li>
     * </ul>
     *
     * Result length is the maximum length of {@code xss}'s elements
     *
     * @param xss an {@code Iterable} of {@code Iterable}s
     * @param <T> the {@code Iterable}'s elements' element type
     * @return {@code xss}, transposed
     */
    public static @NotNull <T> Iterable<List<T>> transpose(@NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<List<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return any(Iterator::hasNext, iterators);
            }

            @Override
            public List<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    if (iterator.hasNext()) {
                        nextList.add(iterator.next());
                    }
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code String}s. If the rows have different lengths, then the "overhanging" characters still end up in the
     * result. See test cases for examples. Uses O(nm) additional memory, where n is then length of {@code xss} and m
     * is the length of the longest {@code String} in {@code xss}. The {@code Iterable} produced does not support
     * removing elements.
     *
     * <ul>
     *  <li>{@code strings} must be non-null.</li>
     *  <li>The lengths of the result's elements are non-increasing and never 0.</li>
     * </ul>
     *
     * Result length is the maximum length of {@code strings}'s elements
     *
     * @param strings an {@code Iterable} of {@code String}s
     * @return {@code strings}, transposed
     */
    public static @NotNull Iterable<String> transposeStrings(@NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transpose(map(s -> fromString(s), strings))
        );
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code Iterables}. If the rows have different lengths, then the "overhanging" elements will be truncated; the
     * result's rows will all have equal lengths. See test cases for examples. Any element of {@code xss} may be
     * infinite, in which case the result will be infinite. Uses O(nm) additional memory, where n is then length of
     * {@code xss} and m is the largest amount of memory used by any {@code Iterable} in {@code xss}. The
     * {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code xss} must be finite.</li>
     *  <li>The lengths of the result's elements are finite and equal.</li>
     * </ul>
     *
     * Result length is the minimum length of {@code xss}'s elements
     *
     * @param xss an {@code Iterable} of {@code Iterable}s
     * @param <T> the {@code Iterable}'s elements' element type
     * @return {@code xss}, transposed
     */
    public static @NotNull <T> Iterable<List<T>> transposeTruncating(@NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<List<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return !iterators.isEmpty() && all(Iterator::hasNext, iterators);
            }

            @Override
            public List<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    nextList.add(iterator.next());
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code String}s. If the rows have different lengths, then the "overhanging" characters will be truncated. See
     * test cases for examples. Uses O(nm) additional memory, where n is then length of {@code xss} and m is the
     * length of the longest {@code String} in {@code xss}. The {@code Iterable} produced does not support removing
     * elements.
     *
     * <ul>
     *  <li>{@code strings} must be non-null.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the minimum length of {@code strings}'s elements
     *
     * @param strings an {@code Iterable} of {@code String}s
     * @return {@code strings}, transposed
     */
    public static @NotNull Iterable<String> transposeStringsTruncating(@NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transposeTruncating(map(s -> fromString(s), strings))
        );
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code Iterables}. If the rows have different lengths, then the gaps will be padded; the result's rows will all
     * have equal lengths. See test cases for examples. Any element of {@code xss} may be infinite, in which case the
     * result will be infinite. Uses O(nm) additional memory, where n is then length of {@code xss} and m is the
     * largest amount of memory used by any {@code Iterable} in {@code xss}. The {@code Iterable} produced does not
     * support removing elements.
     *
     * <ul>
     *  <li>{@code xss} must be finite.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the maximum length of {@code xss}'s elements
     *
     * @param xss an {@code Iterable} of {@code Iterable}s
     * @param pad the padding
     * @param <T> the {@code Iterable}'s elements' element type
     * @return {@code xss}, transposed
     */
    public static @NotNull <T> Iterable<Iterable<T>> transposePadded(
            @Nullable T pad,
            @NotNull Iterable<Iterable<T>> xss
    ) {
        return () -> new Iterator<Iterable<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return any(Iterator::hasNext, iterators);
            }

            @Override
            public Iterable<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    nextList.add(iterator.hasNext() ? iterator.next() : pad);
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's {@code transpose} function. Swaps rows and columns of an {@code Iterable} of
     * {@code String}s. If the rows have different lengths, then the gaps will be padded; the result's rows will all
     * have equal lengths. Uses O(nm) additional memory, where n is then length of {@code xss} and m is the length of
     * the longest {@code String} in {@code xss}. The {@code Iterable} produced does not support removing elements.
     *
     * <ul>
     *  <li>{@code strings} must be non-null.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the maximum length of {@code strings}'s elements
     *
     * @param strings an {@code Iterable} of {@code String}s
     * @param pad the padding
     * @return {@code strings}, transposed
     */
    public static @NotNull Iterable<String> transposeStringsPadded(char pad, @NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transposePadded(pad, map(s -> fromString(s), strings))
        );
    }

    public static @Nullable <A, B> B foldl(
            @NotNull Function<Pair<B, A>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        B result = z;
        for (A x : xs) {
            result = f.apply(new Pair<B, A>(result, x));
        }
        return result;
    }

    public static @Nullable <A> A foldl1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        A result = null;
        boolean started = false;
        for (A x : xs) {
            if (started) {
                result = f.apply(new Pair<A, A>(result, x));
            } else {
                result = x;
                started = true;
            }
        }
        return result;
    }

    public static @Nullable <A, B> B foldr(
            @NotNull Function<Pair<A, B>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        return foldl(p -> f.apply(new Pair<>(p.b, p.a)), z, reverse(xs));
    }

    public static @Nullable <A> A foldr1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        return foldl1(p -> f.apply(new Pair<>(p.b, p.a)), reverse(xs));
    }

    public static @NotNull <T> Iterable<T> concat(@NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<T>() {
            final Iterator<Iterable<T>> xssi = xss.iterator();
            Iterator<T> xsi = xssi.hasNext() ? xssi.next().iterator() : null;

            @Override
            public boolean hasNext() {
                if (xsi == null) return false;
                while (!xsi.hasNext()) {
                    if (!xssi.hasNext()) return false;
                    xsi = xssi.next().iterator();
                }
                return true;
            }

            @Override
            public T next() {
                hasNext();
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String concatStrings(@NotNull Iterable<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static @NotNull <A, B> Iterable<B> concatMap(@NotNull Function<A, Iterable<B>> f, @NotNull Iterable<A> xs) {
        return concat(map(f, xs));
    }

    public static boolean and(@NotNull Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (!x) return false;
        }
        return true;
    }

    public static boolean or(@NotNull Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (x) return true;
        }
        return false;
    }

    public static <T> boolean any(@NotNull Predicate<T> predicate, @NotNull Iterable<T> xs) {
        for (T x : xs) {
            if (predicate.test(x)) return true;
        }
        return false;
    }

    public static <T> boolean all(@NotNull Predicate<T> predicate, @NotNull Iterable<T> xs) {
        for (T x : xs) {
            if (!predicate.test(x)) return false;
        }
        return true;
    }

    public static byte sumByte(@NotNull Iterable<Byte> xs) {
        return foldl(p -> (byte) (p.a + p.b), (byte) 0, xs);
    }

    public static short sumShort(@NotNull Iterable<Short> xs) {
        return foldl(p -> (short) (p.a + p.b), (short) 0, xs);
    }

    public static int sumInteger(@NotNull Iterable<Integer> xs) {
        return foldl(p -> p.a + p.b, 0, xs);
    }

    public static long sumLong(@NotNull Iterable<Long> xs) {
        return foldl(p -> p.a + p.b, 0L, xs);
    }

    public static float sumFloat(@NotNull Iterable<Float> xs) {
        return foldl(p -> p.a + p.b, 0.0f, xs);
    }

    public static double sumDouble(Iterable<Double> xs) {
        return foldl(p -> p.a + p.b, 0.0, xs);
    }

    public static @NotNull BigInteger sumBigInteger(@NotNull Iterable<BigInteger> xs) {
        return foldl(p -> p.a.add(p.b), BigInteger.ZERO, xs);
    }

    public static @NotNull BigDecimal sumBigDecimal(@NotNull Iterable<BigDecimal> xs) {
        return foldl(p -> p.a.add(p.b), BigDecimal.ZERO, xs);
    }

    public static byte productByte(@NotNull Iterable<Byte> xs) {
        return foldl(p -> (byte) (p.a * p.b), (byte) 1, xs);
    }

    public static short productShort(@NotNull Iterable<Short> xs) {
        return foldl(p -> (short) (p.a * p.b), (short) 1, xs);
    }

    public static int productInteger(@NotNull Iterable<Integer> xs) {
        return foldl(p -> p.a * p.b, 1, xs);
    }

    public static long productLong(@NotNull Iterable<Long> xs) {
        return foldl(p -> p.a * p.b, 1L, xs);
    }

    public static float productFloat(@NotNull Iterable<Float> xs) {
        return foldl(p -> p.a * p.b, 1.0f, xs);
    }

    public static double productDouble(@NotNull Iterable<Double> xs) {
        return foldl(p -> p.a * p.b, 1.0, xs);
    }

    public static @NotNull BigInteger productBigInteger(Iterable<BigInteger> xs) {
        return foldl(p -> p.a.multiply(p.b), BigInteger.ONE, xs);
    }

    public static @NotNull BigDecimal productBigDecimal(@NotNull Iterable<BigDecimal> xs) {
        return foldl(p -> p.a.multiply(p.b), BigDecimal.ONE, xs);
    }

    public static <T extends Comparable<T>> T maximum(@NotNull Iterable<T> xs) {
        return foldl1(p -> max(p.a, p.b), xs);
    }

    public static <T extends Comparable<T>> T minimum(@NotNull Iterable<T> xs) {
        return foldl1(p -> min(p.a, p.b), xs);
    }

    public static @NotNull <A, B> Iterable<B> scanl(
            @NotNull Function<Pair<B, A>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        return () -> new Iterator<B>() {
            private final Iterator<A> xsi = xs.iterator();
            private B result = z;
            private boolean firstTime = true;

            @Override
            public boolean hasNext() {
                return firstTime || xsi.hasNext();
            }

            @Override
            public B next() {
                if (firstTime) {
                    firstTime = false;
                    return result;
                } else {
                    result = f.apply(new Pair<B, A>(result, xsi.next()));
                    return result;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <A> Iterable<A> scanl1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        return scanl(f, head(xs), tail(xs));
    }

    public static @NotNull <A, B> Iterable<B> scanr(
            @NotNull Function<Pair<A, B>, B> f,
            @NotNull B z,
            @NotNull Iterable<A> xs
    ) {
        return scanl(p -> f.apply(new Pair<A, B>(p.b, p.a)), z, reverse(xs));
    }

    public static @NotNull <A> Iterable<A> scanr1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        return scanl1(p -> f.apply(new Pair<A, A>(p.b, p.a)), reverse(xs));
    }

    public static @NotNull <X, Y, ACC> Pair<ACC, List<Y>> mapAccumL(
            @NotNull Function<Pair<ACC, X>, Pair<ACC, Y>> f,
            @Nullable ACC s,
            @NotNull Iterable<X> xs
    ) {
        List<Y> ys = new ArrayList<Y>();
        for (X x : xs) {
            Pair<ACC, Y> p = f.apply(new Pair<ACC, X>(s, x));
            s = p.a;
            ys.add(p.b);
        }
        return new Pair<>(s, ys);
    }

    public static @NotNull <X, Y, ACC> Pair<ACC, List<Y>> mapAccumR(
            @NotNull Function<Pair<ACC, X>, Pair<ACC, Y>> f,
            @Nullable ACC s,
            @NotNull Iterable<X> xs) {
        return mapAccumL(f, s, reverse(xs));
    }

    public static @NotNull <T> Iterable<T> iterate(@NotNull Function<T, T> f, @Nullable T x) {
        return () -> new Iterator<T>() {
            private T current = x;
            private boolean firstTime = true;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (firstTime) {
                    firstTime = false;
                } else {
                    current = f.apply(current);
                }
                return current;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<T> repeat(@Nullable T x) {
        return () -> new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return x;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<T> replicate(int n, @Nullable T x) {
        return () -> new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public T next() {
                i++;
                return x;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<T> replicate(@NotNull BigInteger n, @Nullable T x) {
        return () -> new Iterator<T>() {
            private BigInteger i = BigInteger.ZERO;

            @Override
            public boolean hasNext() {
                return lt(i, n);
            }

            @Override
            public T next() {
                i = i.add(BigInteger.ONE);
                return x;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String replicate(int n, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static @NotNull String replicate(@NotNull BigInteger n, char c) {
        StringBuilder sb = new StringBuilder();
        for (BigInteger i : range(BigInteger.ONE, n)) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static @NotNull <T> Iterable<T> cycle(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return xs;
        return () -> new Iterator<T>() {
            private Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (!xsi.hasNext()) xsi = xs.iterator();
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <A, B> Iterable<A> unfoldr(@NotNull Function<B, Optional<Pair<A, B>>> f, @NotNull B x) {
        return new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return new Iterator<A>() {
                    private boolean hasNext = true;
                    private A next;
                    private B seed = x;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public A next() {
                        A oldNext = next;
                        advance();
                        return oldNext;
                    }

                    private void advance() {
                        Optional<Pair<A, B>> p = f.apply(seed);
                        if (p.isPresent()) {
                            next = p.get().a;
                            seed = p.get().b;
                        } else {
                            hasNext = false;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull <T> Iterable<T> take(int n, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private int i = 0;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return i < n && xsi.hasNext();
            }

            @Override
            public T next() {
                i++;
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<T> take(@NotNull BigInteger n, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private BigInteger i = BigInteger.ZERO;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return lt(i, n) && xsi.hasNext();
            }

            @Override
            public T next() {
                i = i.add(BigInteger.ONE);
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String take(int n, @NotNull String s) {
        return s.substring(0, n);
    }

    public static @NotNull String take(@NotNull BigInteger n, @NotNull String s) {
        return s.substring(0, n.intValueExact());
    }

    public static @NotNull <T> Iterable<T> drop(int n, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                int i = n;
                while (xsi.hasNext()) {
                    if (i <= 0) break;
                    xsi.next();
                    i--;
                }
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<T> drop(@NotNull BigInteger n, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                BigInteger i = n;
                while (xsi.hasNext()) {
                    if (le(i, BigInteger.ZERO)) break;
                    xsi.next();
                    i = i.subtract(BigInteger.ONE);
                }
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String drop(int n, @NotNull String s) {
        return s.substring(n);
    }

    public static @NotNull String drop(@NotNull BigInteger n, @NotNull String s) {
        return s.substring(n.intValueExact());
    }

    public static @NotNull <T> Iterable<T> pad(@NotNull T pad, int length, @NotNull Iterable<T> xs) {
        if (length < 0)
            throw new IllegalArgumentException("cannot pad with a negative length");
        return take(length, concat(xs, repeat(pad)));
    }

    public static @NotNull <T> Iterable<T> pad(@NotNull T pad, @NotNull BigInteger length, @NotNull Iterable<T> xs) {
        if (length.signum() == -1)
            throw new IllegalArgumentException("cannot pad with a negative length");
        return take(length, (Iterable<T>) concat(xs, repeat(pad)));
    }

    public static @NotNull String pad(char pad, int length, @NotNull String s) {
        if (s.length() == length) return s;
        if (s.length() > length) return take(length, s);
        return s + replicate(length - s.length(), pad);
    }

    public static @NotNull String pad(char pad, @NotNull BigInteger length, @NotNull String s) {
        if (s.length() == length.intValueExact()) return s;
        if (s.length() > length.intValueExact()) return take(length, s);
        return s + replicate(length.intValueExact() - s.length(), pad);
    }

    public static @NotNull <T> Pair<Iterable<T>, Iterable<T>> splitAt(int n, @NotNull Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static @NotNull <T> Pair<Iterable<T>, Iterable<T>> splitAt(@NotNull BigInteger n, @NotNull Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static @NotNull Pair<String, String> splitAt(int n, @NotNull String s) {
        return new Pair<>(s.substring(0, n), s.substring(n));
    }

    public static @NotNull Pair<String, String> splitAt(@NotNull BigInteger i, @NotNull String s) {
        return splitAt(i.intValueExact(), s);
    }

    public static @NotNull <T> Iterable<T> takeWhile(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        if (xsi.hasNext()) {
                            next = xsi.next();
                            hasNext = p.test(next);
                        } else {
                            hasNext = false;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull String takeWhile(@NotNull Predicate<Character> p, @NotNull String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!p.test(c)) break;
            sb.append(c);
        }
        return sb.toString();
    }

    public static @NotNull <T> Iterable<T> stopAt(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        if (next != null && p.test(next)) {
                            hasNext = false;
                        } else {
                            hasNext = xsi.hasNext();
                            if (xsi.hasNext()) {
                                next = xsi.next();
                            }
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull String stopAt(@NotNull Predicate<Character> p, @NotNull String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(c);
            if (p.test(c)) break;
        }
        return sb.toString();
    }

    public static @NotNull <T> Iterable<T> dropWhile(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private Iterator<T> xsi = xs.iterator();
            private T x;
            private boolean first = false;
            {
                while (xsi.hasNext()) {
                    x = xsi.next();
                    if (!p.test(x)) {
                        first = true;
                        break;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return first || xsi.hasNext();
            }

            @Override
            public T next() {
                if (first) {
                    first = false;
                    return x;
                } else {
                    return xsi.next();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String dropWhile(@NotNull Predicate<Character> p, @NotNull String s) {
        int startIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            if (p.test(s.charAt(i))) {
                startIndex = i;
                break;
            }
        }
        return startIndex == -1 ? "" : s.substring(startIndex);
    }

    public static @NotNull <T> Iterable<T> dropWhileEnd(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        List<T> list = toList(xs);
        int index = -1;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (!p.test(list.get(i))) {
                index = i;
                break;
            }
        }
        return take(index + 1, list);
    }

    public static @NotNull String dropWhileEnd(@NotNull Predicate<Character> p, @NotNull String s) {
        int index = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!p.test(s.charAt(i))) {
                index = i;
                break;
            }
        }
        return take(index + 1, s);
    }

    public static @NotNull <T> Iterable<List<T>> chunk(int size, @NotNull Iterable<T> xs) {
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public List<T> next() {
                List<T> chunk = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    if (!xsi.hasNext()) break;
                    chunk.add(xsi.next());
                }
                return chunk;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<String> chunk(int size, @NotNull String s) {
        return () -> new Iterator<String>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i != s.length();
            }

            @Override
            public String next() {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < size; j++) {
                    if (i == s.length()) break;
                    sb.append(s.charAt(i++));
                }
                return sb.toString();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<List<T>> chunkPadded(@Nullable T pad, int size, @NotNull Iterable<T> xs) {
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public List<T> next() {
                List<T> chunk = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    chunk.add(xsi.hasNext() ? xsi.next() : pad);
                }
                return chunk;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Pair<Iterable<T>, Iterable<T>> span(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        return new Pair<>(takeWhile(p, xs), dropWhile(p, xs));
    }

    public static @NotNull Pair<String, String> span(@NotNull Predicate<Character> p, @NotNull String s) {
        return new Pair<>(takeWhile(p, s), dropWhile(p, s));
    }

    public static @NotNull <T> Pair<Iterable<T>, Iterable<T>> breakIterable(Predicate<T> p, Iterable<T> xs) {
        return span(p.negate(), xs);
    }

    public static @NotNull Pair<String, String> breakString(@NotNull Predicate<Character> p, @NotNull String s) {
        return span(p.negate(), s);
    }

    public static @NotNull <T> Optional<Iterable<T>> stripPrefix(Iterable<T> prefix, Iterable<T> xs) {
        return isPrefixOf(prefix, xs) ? Optional.of(take(length(prefix), xs)) : Optional.<Iterable<T>>empty();
    }

    public static @NotNull <T> Iterable<Pair<T, Integer>> countAdjacent(@NotNull Iterable<T> xs) {
        return new Iterable<Pair<T, Integer>>() {
            @Override
            public Iterator<Pair<T, Integer>> iterator() {
                return new Iterator<Pair<T, Integer>>() {
                    private Iterator<T> xsi = xs.iterator();
                    private boolean hasNext = xsi.hasNext();
                    private boolean isLast = false;
                    private T nextX = null;
                    private Pair<T, Integer> next = null;

                    {
                        if (hasNext) {
                            nextX = xsi.next();
                        }
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public Pair<T, Integer> next() {
                        if (isLast) {
                            hasNext = false;
                            return next;
                        } else {
                            Pair<T, Integer> oldNext = next;
                            advance();
                            return oldNext;
                        }
                    }

                    private void advance() {
                        T original = nextX;
                        int count = 0;
                        do {
                            count++;
                            if (!xsi.hasNext()) {
                                isLast = true;
                                break;
                            }
                            nextX = xsi.next();
                        } while (Objects.equals(original, nextX));
                        next = new Pair<>(original, count);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static <T> boolean isPrefixOf(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        Iterator<T> xsi = xs.iterator();
        Iterator<T> ysi = ys.iterator();
        while (xsi.hasNext()) {
            if (!ysi.hasNext()) return false;
            T x = xsi.next();
            T y = ysi.next();
            if (!Objects.equals(x, y)) return false;
        }
        return true;
    }

    public static boolean isPrefixOf(@NotNull String s, @NotNull String t) {
        return s.length() <= t.length() && s.substring(0, t.length()).equals(t);
    }

    public static <T> boolean isSuffixOf(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        return isPrefixOf(reverse(xs), reverse(ys));
    }

    public static boolean isSuffixOf(@NotNull String s, @NotNull String t) {
        return s.length() <= t.length() && s.substring(t.length() - s.length()).equals(t);
    }

    public static @NotNull <T> Iterable<List<T>> windows(int size, @NotNull Iterable<T> xs) {
        List<T> firstWindow = toList(take(size, xs));
        if (firstWindow.size() < size) return new ArrayList<>();
        return cons(firstWindow, () -> new Iterator<List<T>>() {
            Iterator<T> xsi = drop(size, xs).iterator();
            List<T> previousWindow = firstWindow;

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public List<T> next() {
                previousWindow = toList(concat(tail(previousWindow), Arrays.asList(xsi.next())));
                return previousWindow;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        });
    }

    public static @NotNull Iterable<String> windows(int size, @NotNull String s) {
        String firstWindow = take(size, s);
        if (firstWindow.length() < size) return new ArrayList<>();
        return cons(firstWindow, () -> new Iterator<String>() {
            Iterator<Character> xsi = fromString(drop(size, s)).iterator();
            String previousWindow = firstWindow;

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public String next() {
                previousWindow = concat(tail(previousWindow), Character.toString(xsi.next()));
                return previousWindow;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        });
    }

    public static <T> boolean isInfixOf(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        return any(zs -> equal(xs, zs), windows(length(xs), ys));
    }

    public static boolean isInfixOf(@NotNull String s, @NotNull String t) {
        return t.contains(s);
    }

    public static @NotNull <T> Iterable<T> mux(@NotNull List<Iterable<T>> xss) {
        return concat(map(list -> list, transpose(xss)));
    }

    public static @NotNull String muxStrings(@NotNull List<String> xss) {
        return concatStrings(transposeStrings(xss));
    }

    public static @NotNull <T> List<Iterable<T>> demux(int lines, @NotNull Iterable<T> xs) {
        List<Iterable<T>> demuxed = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            Iterable<Boolean> mask = concat(
                    replicate(i, false),
                    cycle(cons(true, (Iterable<Boolean>) replicate(lines - 1, false)))
            );
            demuxed.add(select(mask, xs));
        }
        return demuxed;
    }

    public static @NotNull List<String> demux(int lines, @NotNull String s) {
        List<String> demuxed = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            Iterable<Boolean> mask = concat(
                    replicate(i, false),
                    cycle(cons(true, (Iterable<Boolean>) replicate(lines - 1, false)))
            );
            demuxed.add(select(mask, s));
        }
        return demuxed;
    }

    public static @NotNull <T> Optional<T> find(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        for (T x : xs) {
            if (p.test(x)) return Optional.of(x);
        }
        return Optional.empty();
    }

    public static @NotNull Optional<Character> find(@NotNull Predicate<Character> p, @NotNull String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (p.test(c)) return Optional.of(c);
        }
        return Optional.empty();
    }

    public static @NotNull <T> Iterable<T> filter(@NotNull Predicate<T> p, @NotNull Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        while (xsi.hasNext()) {
                            next = xsi.next();
                            if (p.test(next)) {
                                hasNext = true;
                                return;
                            }
                        }
                        hasNext = false;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull String filter(@NotNull Predicate<Character> p, @NotNull String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (p.test(c)) sb.append(c);
        }
        return sb.toString();
    }

    public static @NotNull <T> Pair<Iterable<T>, Iterable<T>> partition(
            @NotNull Predicate<T> p,
            @NotNull Iterable<T> xs) {
        return new Pair<>(filter(p, xs), filter(x -> !p.test(x), xs));
    }

    public static @NotNull Pair<String, String> partition(@NotNull Predicate<Character> p, @NotNull String s) {
        StringBuilder sba = new StringBuilder();
        StringBuilder sbb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            (p.test(c) ? sba : sbb).append(c);
        }
        return new Pair<>(sba.toString(), sbb.toString());
    }

    public static <T> T get(Iterable<T> xs, int i) {
        if (i < 0)
            throw new IndexOutOfBoundsException();
        Iterator<T> xsi = xs.iterator();
        T element = null;
        for (int j = 0; j <= i; j++) {
            if (!xsi.hasNext())
                throw new IndexOutOfBoundsException();
            element = xsi.next();
        }
        return element;
    }

    public static <T> T get(Iterable<T> xs, BigInteger i) {
        if (lt(i, BigInteger.ZERO))
            throw new IndexOutOfBoundsException();
        Iterator<T> xsi = xs.iterator();
        T element = null;
        for (BigInteger j : range(BigInteger.ONE, i)) {
            if (!xsi.hasNext())
                throw new IndexOutOfBoundsException();
            element = xsi.next();
        }
        return element;
    }

    public static <T> T get(List<T> xs, int i) {
        return xs.get(i);
    }

    public static char get(String s, int i) {
        return s.charAt(i);
    }

    public static <T> Iterable<T> select(Iterable<Boolean> bs, Iterable<T> xs) {
        return map(p -> p.b, filter(p -> p.a, (Iterable<Pair<Boolean, T>>) zip(bs, xs)));
    }

    public static <T> String select(Iterable<Boolean> bs, String s) {
        return charsToString(
                map(p -> p.b, filter(p -> p.a, (Iterable<Pair<Boolean, Character>>) zip(bs, fromString(s))))
        );
    }

    public static <A, B> Iterable<Pair<A, B>> zip(Iterable<A> as, Iterable<B> bs) {
        return () -> new Iterator<Pair<A, B>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext();
            }

            @Override
            public Pair<A, B> next() {
                return new Pair<>(asi.next(), bsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> zip3(Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        return () -> new Iterator<Triple<A, B, C>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext();
            }

            @Override
            public Triple<A, B, C> next() {
                return new Triple<>(asi.next(), bsi.next(), csi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return () -> new Iterator<Quadruple<A, B, C, D>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext() && dsi.hasNext();
            }

            @Override
            public Quadruple<A, B, C, D> next() {
                return new Quadruple<>(asi.next(), bsi.next(), csi.next(), dsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> zip5(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return () -> new Iterator<Quintuple<A, B, C, D, E>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext() && dsi.hasNext() && esi.hasNext();
            }

            @Override
            public Quintuple<A, B, C, D, E> next() {
                return new Quintuple<>(asi.next(), bsi.next(), csi.next(), dsi.next(), esi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> zip6(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return () -> new Iterator<Sextuple<A, B, C, D, E, F>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() &&
                        bsi.hasNext() &&
                        csi.hasNext() &&
                        dsi.hasNext() &&
                        esi.hasNext() &&
                        fsi.hasNext();
            }

            @Override
            public Sextuple<A, B, C, D, E, F> next() {
                return new Sextuple<>(asi.next(), bsi.next(), csi.next(), dsi.next(), esi.next(), fsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> zip7(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return () -> new Iterator<Septuple<A, B, C, D, E, F, G>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();
            private final Iterator<G> gsi = gs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() &&
                        bsi.hasNext() &&
                        csi.hasNext() &&
                        dsi.hasNext() &&
                        esi.hasNext() &&
                        fsi.hasNext() &&
                        gsi.hasNext();
            }

            @Override
            public Septuple<A, B, C, D, E, F, G> next() {
                return new Septuple<>(
                        asi.next(),
                        bsi.next(),
                        csi.next(),
                        dsi.next(),
                        esi.next(),
                        fsi.next(),
                        gsi.next()
                );
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B> Iterable<Pair<A, B>> zipPadded(A aPad, B bPad, Iterable<A> as, Iterable<B> bs) {
        return () -> new Iterator<Pair<A, B>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext();
            }

            @Override
            public Pair<A, B> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                return new Pair<>(a, b);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> zip3Padded(
            A aPad,
            B bPad,
            C cPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return () -> new Iterator<Triple<A, B, C>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext();
            }

            @Override
            public Triple<A, B, C> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                return new Triple<>(a, b, c);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return () -> new Iterator<Quadruple<A, B, C, D>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext() || dsi.hasNext();
            }

            @Override
            public Quadruple<A, B, C, D> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                return new Quadruple<>(a, b, c, d);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> zip5Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es) {
        return () -> new Iterator<Quintuple<A, B, C, D, E>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext() || dsi.hasNext() || esi.hasNext();
            }

            @Override
            public Quintuple<A, B, C, D, E> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                return new Quintuple<>(a, b, c, d, e);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> zip6Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs) {
        return () -> new Iterator<Sextuple<A, B, C, D, E, F>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() ||
                        bsi.hasNext() ||
                        csi.hasNext() ||
                        dsi.hasNext() ||
                        esi.hasNext() ||
                        fsi.hasNext();
            }

            @Override
            public Sextuple<A, B, C, D, E, F> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                F f = fsi.hasNext() ? fsi.next() : fPad;
                return new Sextuple<>(a, b, c, d, e, f);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> zip7Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            G gPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs) {
        return () -> new Iterator<Septuple<A, B, C, D, E, F, G>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();
            private final Iterator<G> gsi = gs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() ||
                        bsi.hasNext() ||
                        csi.hasNext() ||
                        dsi.hasNext() ||
                        esi.hasNext() ||
                        fsi.hasNext() ||
                        gsi.hasNext();
            }

            @Override
            public Septuple<A, B, C, D, E, F, G> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                F f = fsi.hasNext() ? fsi.next() : fPad;
                G g = gsi.hasNext() ? gsi.next() : gPad;
                return new Septuple<>(a, b, c, d, e, f, g);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, O> Iterable<O> zipWith(
            Function<Pair<A, B>, O> f,
            Iterable<A> as,
            Iterable<B> bs
    ) {
        return map(f, zip(as, bs));
    }

    public static <A, B, C, O> Iterable<O> zipWith3(
            Function<Triple<A, B, C>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(f, zip3(as, bs, cs));
    }

    public static <A, B, C, D, O> Iterable<O> zipWith4(
            Function<Quadruple<A, B, C, D>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return map(f, zip4(as, bs, cs, ds));
    }

    public static <A, B, C, D, E, O> Iterable<O> zipWith5(
            Function<Quintuple<A, B, C, D, E>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return map(f, zip5(as, bs, cs, ds, es));
    }

    public static <A, B, C, D, E, F, O> Iterable<O> zipWith6(
            Function<Sextuple<A, B, C, D, E, F>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return map(f, zip6(as, bs, cs, ds, es, fs));
    }

    public static <A, B, C, D, E, F, G, O> Iterable<O> zipWith6(
            Function<Septuple<A, B, C, D, E, F, G>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return map(f, zip7(as, bs, cs, ds, es, fs, gs));
    }

    public static <A, B, O> Iterable<O> zipWithPadded(
            Function<Pair<A, B>, O> f,
            A aPad,
            B bPad,
            Iterable<A> as,
            Iterable<B> bs
    ) {
        return map(f, zipPadded(aPad, bPad, as, bs));
    }

    public static <A, B, C, O> Iterable<O> zipWith3Padded(
            Function<Triple<A, B, C>, O> f,
            A aPad,
            B bPad,
            C cPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(f, zip3Padded(aPad, bPad, cPad, as, bs, cs));
    }

    public static <A, B, C, D, O> Iterable<O> zipWith4Padded(
            Function<Quadruple<A, B, C, D>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return map(f, zip4Padded(aPad, bPad, cPad, dPad, as, bs, cs, ds));
    }

    public static <A, B, C, D, E, O> Iterable<O> zipWith5Padded(
            Function<Quintuple<A, B, C, D, E>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return map(f, zip5Padded(aPad, bPad, cPad, dPad, ePad, as, bs, cs, ds, es));
    }

    public static <A, B, C, D, E, F, O> Iterable<O> zipWith6Padded(
            Function<Sextuple<A, B, C, D, E, F>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return map(f, zip6Padded(aPad, bPad, cPad, dPad, ePad, fPad, as, bs, cs, ds, es, fs));
    }

    public static <A, B, C, D, E, F, G, O> Iterable<O> zipWith7Padded(
            Function<Septuple<A, B, C, D, E, F, G>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            G gPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return map(f, zip7Padded(aPad, bPad, cPad, dPad, ePad, fPad, gPad, as, bs, cs, ds, es, fs, gs));
    }

    public static <A, B> Pair<Iterable<A>, Iterable<B>> unzip(Iterable<Pair<A, B>> ps) {
        return new Pair<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps)
        );
    }

    public static <A, B, C> Triple<Iterable<A>, Iterable<B>, Iterable<C>> unzip3(Iterable<Triple<A, B, C>> ps) {
        return new Triple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps)
        );
    }

    public static <A, B, C, D> Quadruple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>
            > unzip4(Iterable<Quadruple<A, B, C, D>> ps) {
        return new Quadruple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps)
        );
    }

    public static <A, B, C, D, E> Quintuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>
            > unzip5(Iterable<Quintuple<A, B, C, D, E>> ps) {
        return new Quintuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps)
        );
    }

    public static <A, B, C, D, E, F> Sextuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>,
            Iterable<F>
            > unzip6(Iterable<Sextuple<A, B, C, D, E, F>> ps) {
        return new Sextuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps),
                map(p -> p.f, ps)
        );
    }

    public static <A, B, C, D, E, F, G> Septuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>,
            Iterable<F>,
            Iterable<G>
            > unzip7(Iterable<Septuple<A, B, C, D, E, F, G>> ps) {
        return new Septuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps),
                map(p -> p.f, ps),
                map(p -> p.g, ps)
        );
    }

    public static @NotNull <T> Iterable<T> nub(@NotNull Iterable<T> xs) {
        return new Iterable<T>() {
            private Set<T> seen = new HashSet<>();
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;

                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        while (xsi.hasNext()) {
                            next = xsi.next();
                            if (!seen.contains(next)) {
                                seen.add(next);
                                hasNext = true;
                                return;
                            }
                        }
                        hasNext = false;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull String nub(@NotNull String s) {
        Set<Character> seen = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!seen.contains(c)) {
                seen.add(c);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static <T> boolean isSubsetOf(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        HashSet<T> set = new HashSet<>();
        addTo(xs, set);
        for (T y : ys) {
            set.remove(y);
            if (set.isEmpty()) return true;
        }
        return false;
    }

    public static <T> boolean isSubsetOf(@NotNull String s, @NotNull String t) {
        return isSubsetOf(fromString(s), fromString(t));
    }

    public static @NotNull <T extends Comparable<T>> List<T> sort(@NotNull Iterable<T> xss) {
        List<T> list = toList(xss);
        Collections.sort(list);
        return list;
    }

    public static @NotNull String sort(@NotNull String s) {
        List<Character> list = toList(s);
        Collections.sort(list);
        return charsToString(list);
    }

    public static <T> boolean equal(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        Iterator<T> xsi = xs.iterator();
        Iterator<T> ysi = ys.iterator();
        while (xsi.hasNext()) {
            if (!ysi.hasNext()) return false;
            T x = xsi.next();
            T y = ysi.next();
            if (!Objects.equals(x, y)) return false;
        }
        return !ysi.hasNext();
    }
}