package mho.haskellesque.ordering;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public enum Ordering {
    LT, EQ, GT;

    public @NotNull Ordering invert() {
        switch (this) {
            case LT: return GT;
            case EQ: return EQ;
            case GT: return LT;
        }
        return null;
    }

    public static Ordering fromInt(int i) {
        if (i > 0) return GT;
        if (i < 0) return LT;
        return EQ;
    }

    public int toInt() {
        switch (this) {
            case LT: return -1;
            case EQ: return 0;
            case GT: return 1;
        }
        return 0; //doesn't happen
    }

    public static @NotNull <T extends Comparable<T>> Ordering compare(@NotNull T a, @NotNull T b) {
        return fromInt(a.compareTo(b));
    }

    public static @NotNull <T extends Comparable<T>> Ordering compare(
            @NotNull Comparator<T> comparator,
            @NotNull T a,
            @NotNull T b
    ) {
        return fromInt(comparator.compare(a, b));
    }

    public static @NotNull <T extends Comparable<T>> Ordering compareNullable(@Nullable T a, @Nullable T b) {
        if (a == null && b == null) {
            return EQ;
        } else if (a == null) {
            return LT;
        } else if (b == null) {
            return GT;
        } else {
            return compare(a, b);
        }
    }

    public static @NotNull <T extends Comparable<T>> Ordering compareNullable(
            Comparator<T> comparator,
            @Nullable T a,
            @Nullable T b
    ) {
        if (a == null && b == null) {
            return EQ;
        } else if (a == null) {
            return LT;
        } else if (b == null) {
            return GT;
        } else {
            return compare(comparator, a, b);
        }
    }

    public static <T extends Comparable<T>> boolean eq(T a, T b) {
        return a.compareTo(b) == 0;
    }

    public static <T extends Comparable<T>> boolean equalTo(T a, T b) {
        return eq(a, b);
    }

    public static <T extends Comparable<T>> boolean ne(T a, T b) {
        return a.compareTo(b) != 0;
    }

    public static <T extends Comparable<T>> boolean notEqualTo(T a, T b) {
        return ne(a, b);
    }

    public static <T extends Comparable<T>> boolean lt(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean lessThan(T a, T b) {
        return lt(a, b);
    }

    public static <T extends Comparable<T>> boolean gt(T a, T b) {
        return a.compareTo(b) > 0;
    }

    public static <T extends Comparable<T>> boolean greaterThan(T a, T b) {
        return gt(a, b);
    }

    public static <T extends Comparable<T>> boolean le(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    public static <T extends Comparable<T>> boolean lessThanOrEqualTo(T a, T b) {
        return le(a, b);
    }

    public static <T extends Comparable<T>> boolean ge(T a, T b) {
        return a.compareTo(b) >= 0;
    }

    public static <T extends Comparable<T>> boolean greaterThanOrEqualTo(T a, T b) {
        return ge(a, b);
    }

    public static <T> boolean eq(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) == 0;
    }

    public static <T> boolean equalTo(Comparator<T> comparator, T a, T b) {
        return eq(comparator, a, b);
    }

    public static <T> boolean ne(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) != 0;
    }

    public static <T> boolean notEqualTo(Comparator<T> comparator, T a, T b) {
        return ne(comparator, a, b);
    }

    public static <T> boolean lt(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) < 0;
    }

    public static <T> boolean lessThan(Comparator<T> comparator, T a, T b) {
        return lt(comparator, a, b);
    }

    public static <T> boolean gt(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) > 0;
    }

    public static <T> boolean greaterThan(Comparator<T> comparator, T a, T b) {
        return gt(comparator, a, b);
    }

    public static <T> boolean le(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) <= 0;
    }

    public static <T> boolean lessThanOrEqualTo(Comparator<T> comparator, T a, T b) {
        return le(comparator, a, b);
    }

    public static <T> boolean ge(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b) >= 0;
    }

    public static <T> boolean greaterThanOrEqualTo(Comparator<T> comparator, T a, T b) {
        return ge(comparator, a, b);
    }

    public static <T extends Comparable<T>> T min(T... elements) {
        if (elements.length == 0)
            throw new IllegalArgumentException("element list cannot be empty");
        T smallest = elements[0];
        for (T element : elements) {
            if (lt(element, smallest)) smallest = element;
        }
        return smallest;
    }

    public static <T extends Comparable<T>> T max(T... elements) {
        if (elements.length == 0)
            throw new IllegalArgumentException("element list cannot be empty");
        T largest = elements[0];
        for (T element : elements) {
            if (gt(element, largest)) largest = element;
        }
        return largest;
    }

    public static <T> T min(Comparator<T> comparator, T... elements) {
        if (elements.length == 0)
            throw new IllegalArgumentException("element list cannot be empty");
        T smallest = elements[0];
        for (T element : elements) {
            if (lt(comparator, element, smallest)) smallest = element;
        }
        return smallest;
    }

    public static <T> T max(Comparator<T> comparator, T... elements) {
        if (elements.length == 0)
            throw new IllegalArgumentException("element list cannot be empty");
        T largest = elements[0];
        for (T element : elements) {
            if (gt(comparator, element, largest)) largest = element;
        }
        return largest;
    }
}
