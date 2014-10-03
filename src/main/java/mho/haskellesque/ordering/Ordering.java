package mho.haskellesque.ordering;

public enum Ordering {
    LT, EQ, GT;

    public Ordering reverse() {
        switch (this) {
            case LT: return GT;
            case EQ: return EQ;
            case GT: return LT;
        }
        return null;
    }

    public static <T extends Comparable<T>> Ordering compare(T a, T b) {
        int c = a.compareTo(b);
        if (c < 0) return LT;
        if (c > 0) return GT;
        return EQ;
    }

    public static <T extends Comparable<T>> boolean eq(T a, T b) {
        return a.compareTo(b) == 0;
    }

    public static <T extends Comparable<T>> boolean equalTo(T a, T b) {
        return a.compareTo(b) == 0;
    }

    public static <T extends Comparable<T>> boolean ne(T a, T b) {
        return a.compareTo(b) != 0;
    }

    public static <T extends Comparable<T>> boolean notEqualTo(T a, T b) {
        return a.compareTo(b) != 0;
    }

    public static <T extends Comparable<T>> boolean lt(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean lessThan(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean gt(T a, T b) {
        return a.compareTo(b) > 0;
    }

    public static <T extends Comparable<T>> boolean greaterThan(T a, T b) {
        return a.compareTo(b) > 0;
    }

    public static <T extends Comparable<T>> boolean le(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    public static <T extends Comparable<T>> boolean lessThanOrEqualTo(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    public static <T extends Comparable<T>> boolean ge(T a, T b) {
        return a.compareTo(b) >= 0;
    }

    public static <T extends Comparable<T>> boolean greaterThanOrEqualTo(T a, T b) {
        return a.compareTo(b) >= 0;
    }
}
