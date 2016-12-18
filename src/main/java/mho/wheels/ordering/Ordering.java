package mho.wheels.ordering;

import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;

/**
 * An enumeration consisting of the elements {@code LT} (less than), {@code EQ} (equal to), and {@code GT} (greater
 * than). Ordering-related utilities are also provided.
 */
public enum Ordering {
    LT, // <
    EQ, // ==
    GT; // >

    /**
     * Converts an integer to an ordering, based on comparing the integer to zero. This is useful because C-style
     * ordering-related methods often use negative integers to mean "less than", zero to mean "equal to", and positive
     * integers to mean "greater than".
     *
     * <ul>
     *  <li>{@code i} may be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param i an {@code int}
     * @return whether {@code i} is greater than, less than, or equal to zero
     */
    public static @NotNull Ordering fromInt(int i) {
        if (i > 0) return GT;
        if (i < 0) return LT;
        return EQ;
    }

    /**
     * Converts {@code this} to an {@code int}. {@code EQ} becomes 0, {@code LT} becomes –1, and {@code GT} becomes 1.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Ordering}.</li>
     *  <li>The result may be –1, 0, or 1.</li>
     * </ul>
     *
     * @return an {@code int} equivalent to {@code this}
     */
    public int toInt() {
        switch (this) {
            case LT: return -1;
            case EQ: return 0;
            case GT: return 1;
        }
        throw new IllegalStateException("unreachable");
    }

    /**
     * Returns the opposite ordering: {@code EQ} stays {@code EQ}, and {@code LT} and {@code GT} switch places.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Ordering}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return the opposite or inverse ordering
     */
    public @NotNull Ordering invert() {
        switch (this) {
            case LT: return GT;
            case EQ: return EQ;
            case GT: return LT;
        }
        throw new IllegalStateException("unreachable");
    }

    /**
     * Compares two values using their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return whether {@code a} is less than, equal to, or greater than {@code b}
     */
    public static @NotNull <T extends Comparable<T>> Ordering compare(@NotNull T a, @NotNull T b) {
        return fromInt(a.compareTo(b));
    }

    /**
     * Compares two values using a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return whether {@code a} is less than, equal to, or greater than {@code b}
     */
    public static @NotNull <T> Ordering compare(@NotNull Comparator<T> comparator, @Nullable T a, @Nullable T b) {
        return fromInt(comparator.compare(a, b));
    }

    /**
     * Whether {@code a} and {@code b} are equal with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a=b
     */
    public static <T extends Comparable<T>> boolean eq(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) == 0;
    }

    /**
     * Whether {@code a} and {@code b} are unequal with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≠b
     */
    public static <T extends Comparable<T>> boolean ne(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) != 0;
    }

    /**
     * Whether {@code a} is less than b {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a{@literal <}b
     */
    public static <T extends Comparable<T>> boolean lt(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) < 0;
    }

    /**
     * Whether {@code a} is greater than b {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a{@literal >}b
     */
    public static <T extends Comparable<T>> boolean gt(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) > 0;
    }

    /**
     * Whether {@code a} is less than or equal to b {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≤b
     */
    public static <T extends Comparable<T>> boolean le(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) <= 0;
    }

    /**
     * Whether {@code a} is greater than or equal to b {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≥b
     */
    public static <T extends Comparable<T>> boolean ge(@NotNull T a, @NotNull T b) {
        return a.compareTo(b) >= 0;
    }

    /**
     * Whether {@code a} and {@code b} are equal with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a=<sub>{@code comparator}</sub>b
     */
    public static <T> boolean eq(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) == 0;
    }

    /**
     * Whether {@code a} and {@code b} are unequal with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≠<sub>{@code comparator}</sub>b
     */
    public static <T> boolean ne(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) != 0;
    }

    /**
     * Whether {@code a} is less than {@code b} with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a{@literal <}<sub>{@code comparator}</sub>b
     */
    public static <T> boolean lt(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) < 0;
    }

    /**
     * Whether {@code a} is greater than {@code b} with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a{@literal >}<sub>{@code comparator}</sub>b
     */
    public static <T> boolean gt(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) > 0;
    }

    /**
     * Whether {@code a} is less than or equal to {@code b} with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≤<sub>{@code comparator}</sub>b
     */
    public static <T> boolean le(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) <= 0;
    }

    /**
     * Whether {@code a} is less than or equal to {@code b} with respect to a provided {@code Comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return a≥<sub>{@code comparator}</sub>b
     */
    public static <T> boolean ge(@NotNull Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) >= 0;
    }

    /**
     * Returns the smaller of {@code a} and {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return min({@code a}, {@code b})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> T min(@NotNull T a, @NotNull T b) {
        return lt(a, b) ? a : b;
    }

    /**
     * Returns the larger of {@code a} and {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return max({@code a}, {@code b})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> T max(@NotNull T a, @NotNull T b) {
        return gt(a, b) ? a : b;
    }

    /**
     * Returns the smaller and the larger of {@code a} and {@code b} with respect to their default {@code Comparator}.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>{@code a} and {@code b} must be comparable by their default comparator.</li>
     *  <li>The result is not null, and neither of its elements is null.</li>
     * </ul>
     *
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return (min({@code a}, {@code b}), max({@code a}, {@code b}))
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> Pair<T, T> minMax(@NotNull T a, @NotNull T b) {
        return lt(a, b) ? new Pair<>(a, b) : new Pair<>(b, a);
    }

    /**
     * Returns the smaller of {@code a} and {@code b} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} may be any {@code T}.</li>
     *  <li>{@code b} may be any {@code T}.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code T}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return min<sub>{@code comparator}</sub>({@code a}, {@code b})
     */
    @SuppressWarnings("JavaDoc")
    public static <T> T min(@NotNull Comparator<T> comparator, T a, T b) {
        return lt(comparator, a, b) ? a : b;
    }

    /**
     * Returns the larger of {@code a} and {@code b} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} may be any {@code T}.</li>
     *  <li>{@code b} may be any {@code T}.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code T}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return max<sub>{@code comparator}</sub>({@code a}, {@code b})
     */
    @SuppressWarnings("JavaDoc")
    public static <T> T max(@NotNull Comparator<T> comparator, T a, T b) {
        return gt(comparator, a, b) ? a : b;
    }

    /**
     * Returns the smaller and the larger of {@code a} and {@code b} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code a} may be any {@code T}.</li>
     *  <li>{@code b} may be any {@code T}.</li>
     *  <li>{@code a} and {@code b} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code T}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare {@code a} and {@code b}
     * @param a the first value
     * @param b the second value
     * @param <T> the type of {@code a} and {@code b}
     * @return (min<sub>{@code comparator}</sub>({@code a}, {@code b}),
     * max<sub>{@code comparator}</sub>({@code a}, {@code b}))
     */
    @SuppressWarnings("JavaDoc")
    public static <T> Pair<T, T> minMax(@NotNull Comparator<T> comparator, T a, T b) {
        return lt(comparator, a, b) ? new Pair<>(a, b) : new Pair<>(b, a);
    }

    /**
     * Returns the smallest element of {@code xs} with respect to the default {@code Comparator} of type {@code T}.
     *
     * <ul>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by their default comparator.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return min({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> T minimum(@NotNull Iterable<T> xs) {
        T min = null;
        boolean first = true;
        for (T x : xs) {
            if (first) {
                min = x;
                first = false;
            } else {
                if (lt(x, min)) {
                    min = x;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("xs cannot be empty.");
        }
        if (min == null) {
            throw new NullPointerException();
        }
        return min;
    }

    /**
     * Returns the largest element of {@code xs} with respect to the default {@code Comparator} of type {@code T}.
     *
     * <ul>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by their default comparator.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return max({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> T maximum(@NotNull Iterable<T> xs) {
        T max = null;
        boolean first = true;
        for (T x : xs) {
            if (first) {
                max = x;
                first = false;
            } else {
                if (gt(x, max)) {
                    max = x;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("xs cannot be empty.");
        }
        if (max == null) {
            throw new NullPointerException();
        }
        return max;
    }

    /**
     * Returns the smallest and largest elements of {@code xs} with respect to the default {@code Comparator} of type
     * {@code T}.
     *
     * <ul>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by their default comparator.</li>
     *  <li>The result is not null and neither of its elements is null.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return (min({@code xs}), max({@code xs}))
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T extends Comparable<T>> Pair<T, T> minimumMaximum(@NotNull Iterable<T> xs) {
        T min = null;
        T max = null;
        boolean first = true;
        for (T x : xs) {
            if (first) {
                min = x;
                max = x;
                first = false;
            } else {
                if (lt(x, min)) {
                    min = x;
                } else if (gt(x, max)) {
                    max = x;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("xs cannot be empty.");
        }
        if (min == null) {
            throw new NullPointerException();
        }
        return new Pair<>(min, max);
    }

    /**
     * Returns the smallest element of {@code xs} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code T}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the elements of {@code xs}
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return min<sub>{@code comparator}</sub>({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static <T> T minimum(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return foldl1((x, y) -> min(comparator, x, y), xs);
    }

    /**
     * Returns the largest element of {@code xs} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code T}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the elements of {@code xs}
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return max<sub>{@code comparator}</sub>({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static <T> T maximum(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return foldl1((x, y) -> max(comparator, x, y), xs);
    }

    /**
     * Returns the smallest and largest elements of {@code xs} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code xs} cannot be empty or infinite and cannot contain nulls.</li>
     *  <li>Every pair of elements in {@code xs} must be comparable by {@code comparator}.</li>
     *  <li>The result is not null and neither of its elements is null.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the elements of {@code xs}
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of elements in {@code xs}
     * @return (min<sub>{@code comparator}</sub>({@code xs}), max<sub>{@code comparator}</sub>({@code xs}))
     */
    @SuppressWarnings("JavaDoc")
    public static <T> Pair<T, T> minimumMaximum(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        T min = null;
        T max = null;
        boolean first = true;
        for (T x : xs) {
            if (first) {
                min = x;
                max = x;
                first = false;
            } else {
                if (lt(comparator, x, min)) {
                    min = x;
                } else if (gt(comparator, x, max)) {
                    max = x;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("xs cannot be empty.");
        }
        return new Pair<>(min, max);
    }

    /**
     * Returns the smallest {@code char} of {@code s} with respect to the default {@code char} ordering.
     *
     * <ul>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return min({@code s})
     */
    @SuppressWarnings("JavaDoc")
    public static char minimum(@NotNull String s) {
        return foldl1(Ordering::min, fromString(s));
    }

    /**
     * Returns the largest {@code char} of {@code s} with respect to the default {@code char} ordering.
     *
     * <ul>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return max({@code s})
     */
    @SuppressWarnings("JavaDoc")
    public static char maximum(@NotNull String s) {
        return foldl1(Ordering::max, fromString(s));
    }

    /**
     * Returns the smallest and largest {@code char}s of {@code s} with respect to the default {@code char} ordering.
     *
     * <ul>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>The result is not null and neither of its elements is null.</li>
     * </ul>
     *
     * @param s a {@code String}
     * @return (min({@code xs}), max({@code xs}))
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull Pair<Character, Character> minimumMaximum(@NotNull String s) {
        char min = '\0';
        char max = '\0';
        boolean first = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (first) {
                min = c;
                max = c;
                first = false;
            } else {
                if (lt(c, min)) {
                    min = c;
                } else if (gt(c, max)) {
                    max = c;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("s cannot be empty.");
        }
        return new Pair<>(min, max);
    }

    /**
     * Returns the smallest {@code char} of {@code s} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>Every pair of {@code char}s in {@code s} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the {@code char}s of {@code s}
     * @param s a {@code String}
     * @return min<sub>{@code comparator}</sub>({@code s})
     */
    @SuppressWarnings("JavaDoc")
    public static char minimum(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return foldl1((x, y) -> min(comparator, x, y), fromString(s));
    }

    /**
     * Returns the largest {@code char} of {@code s} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>Every pair of {@code char}s in {@code s} must be comparable by {@code comparator}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the {@code char}s of {@code s}
     * @param s a {@code String}
     * @return max<sub>{@code comparator}</sub>({@code s})
     */
    public static char maximum(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return foldl1((x, y) -> max(comparator, x, y), fromString(s));
    }

    /**
     * Returns the smallest and largest {@code char}s of {@code s} with respect to {@code comparator}.
     *
     * <ul>
     *  <li>{@code comparator} cannot be null.</li>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>Every pair of {@code char}s in {@code s} must be comparable by {@code comparator}.</li>
     *  <li>The result is not null and neither of its elements is null.</li>
     * </ul>
     *
     * @param comparator the {@code Comparator} used to compare the {@code char}s of {@code s}
     * @param s a {@code String}
     * @return (min<sub>{@code comparator}</sub>({@code xs}), max<sub>{@code comparator}</sub>({@code xs}))
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull Pair<Character, Character> minimumMaximum(
            @NotNull Comparator<Character> comparator,
            @NotNull String s
    ) {
        char min = '\0';
        char max = '\0';
        boolean first = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (first) {
                min = c;
                max = c;
                first = false;
            } else {
                if (lt(comparator, c, min)) {
                    min = c;
                } else if (gt(comparator, c, max)) {
                    max = c;
                }
            }
        }
        if (first) {
            throw new IllegalArgumentException("s cannot be empty.");
        }
        return new Pair<>(min, max);
    }

    public static <T extends Comparable<T>> boolean increasing(@NotNull Iterable<T> xs) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((x, y) -> lt(x, y), xs));
    }

    public static <T extends Comparable<T>> boolean decreasing(@NotNull Iterable<T> xs) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((x, y) -> gt(x, y), xs));
    }

    public static <T extends Comparable<T>> boolean weaklyIncreasing(@NotNull Iterable<T> xs) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((x, y) -> le(x, y), xs));
    }

    public static <T extends Comparable<T>> boolean weaklyDecreasing(@NotNull Iterable<T> xs) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((x, y) -> ge(x, y), xs));
    }

    public static <T extends Comparable<T>> boolean zigzagging(@NotNull Iterable<T> xs) {
        Iterable<Pair<Ordering, Ordering>> compares = adjacentPairsWith(
                Pair::new,
                adjacentPairsWith(Ordering::compare, xs)
        );
        return all(p -> p.a != EQ && p.a == p.b.invert(), compares);
    }

    public static <T> boolean increasing(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return and(adjacentPairsWith((x, y) -> lt(comparator, x, y), xs));
    }

    public static <T> boolean decreasing(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return and(adjacentPairsWith((x, y) -> gt(comparator, x, y), xs));
    }

    public static <T> boolean weaklyIncreasing(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return and(adjacentPairsWith((x, y) -> le(comparator, x, y), xs));
    }

    public static <T> boolean weaklyDecreasing(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        return and(adjacentPairsWith((x, y) -> ge(comparator, x, y), xs));
    }

    public static <T> boolean zigzagging(@NotNull Comparator<T> comparator, @NotNull Iterable<T> xs) {
        Iterable<Pair<Ordering, Ordering>> compares = adjacentPairsWith(
                Pair::new,
                adjacentPairsWith((x, y) -> compare(comparator, x, y), xs)
        );
        return all(p -> p.a != EQ && p.a == p.b.invert(), compares);
    }

    public static boolean increasing(@NotNull String s) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((a, b) -> lt(a, b), fromString(s)));
    }

    public static boolean decreasing(@NotNull String s) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((a, b) -> gt(a, b), fromString(s)));
    }

    public static boolean weaklyIncreasing(@NotNull String s) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((a, b) -> le(a, b), fromString(s)));
    }

    public static boolean weaklyDecreasing(@NotNull String s) {
        //noinspection Convert2MethodRef
        return and(adjacentPairsWith((a, b) -> ge(a, b), fromString(s)));
    }

    public static boolean zigzagging(@NotNull String s) {
        Iterable<Pair<Ordering, Ordering>> compares = adjacentPairsWith(
                Pair::new,
                adjacentPairsWith(Ordering::compare, fromString(s))
        );
        return all(p -> p.a != EQ && p.a == p.b.invert(), compares);
    }

    public static boolean increasing(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return and(adjacentPairsWith((x, y) -> lt(comparator, x, y), fromString(s)));
    }

    public static boolean decreasing(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return and(adjacentPairsWith((x, y) -> gt(comparator, x, y), fromString(s)));
    }

    public static boolean weaklyIncreasing(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return and(adjacentPairsWith((x, y) -> le(comparator, x, y), fromString(s)));
    }

    public static boolean weaklyDecreasing(@NotNull Comparator<Character> comparator, @NotNull String s) {
        return and(adjacentPairsWith((x, y) -> ge(comparator, x, y), fromString(s)));
    }

    public static boolean zigzagging(@NotNull Comparator<Character> comparator, @NotNull String s) {
        Iterable<Pair<Ordering, Ordering>> compares = adjacentPairsWith(
                Pair::new,
                adjacentPairsWith((x, y) -> compare(comparator, x, y), fromString(s))
        );
        return all(p -> p.a != EQ && p.a == p.b.invert(), compares);
    }

    /**
     * Reads an {@link Ordering} from a {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Ordering} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent
     * an {@code Ordering}
     */
    public static @NotNull Optional<Ordering> readStrict(@NotNull String s) {
        switch (s) {
            case "<":
                return Optional.of(Ordering.LT);
            case "=":
                return Optional.of(Ordering.EQ);
            case ">":
                return Optional.of(Ordering.GT);
            default:
                return Optional.empty();
        }
    }

    /**
     * Converts {@code this} to a single-character {@code String}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code Ordering}.</li>
     *  <li>The result may be "<", "=", or ">".</li>
     * </ul>
     *
     * @return the {@code String} representing {@code this}
     */
    public @NotNull String toString() {
        switch (this) {
            case LT: return "<";
            case EQ: return "=";
            case GT: return ">";
        }
        throw new IllegalStateException("unreachable");
    }
}
