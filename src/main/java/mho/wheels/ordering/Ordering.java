package mho.wheels.ordering;

import mho.wheels.structures.Pair;
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

    public char toChar() {
        switch (this) {
            case LT: return '<';
            case EQ: return '=';
            case GT: return '>';
        }
        return 0; //doesn't happen
    }

    public static @NotNull <T extends Comparable<T>> Ordering compare(@NotNull T a, @NotNull T b) {
        return fromInt(a.compareTo(b));
    }

    public static @NotNull <T> Ordering compare(@NotNull Comparator<T> comparator, @Nullable T a, @Nullable T b) {
        return fromInt(comparator.compare(a, b));
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

    public static <T extends Comparable<T>> T min(T a, T b) {
        return lt(a, b) ? a : b;
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        return gt(a, b) ? a : b;
    }

    public static <T extends Comparable<T>> Pair<T, T> minMax(T a, T b) {
        return lt(a, b) ? new Pair<>(a, b) : new Pair<>(b, a);
    }

    public static <T> T min(Comparator<T> comparator, T a, T b) {
        return lt(comparator, a, b) ? a : b;
    }

    public static <T> T max(Comparator<T> comparator, T a, T b) {
        return gt(comparator, a, b) ? a : b;
    }

    public static <T> Pair<T, T> minMax(Comparator<T> comparator, T a, T b) {
        return lt(comparator, a, b) ? new Pair<>(a, b) : new Pair<>(b, a);
    }
}
