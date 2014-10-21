package mho.haskellesque.ordering.comparators;

import java.util.Comparator;

import static mho.haskellesque.ordering.Ordering.*;

public class NullHandlingComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T a, T b) {
        if (a == null && b == null) return EQ.toInt();
        if (a == null) return LT.toInt();
        if (b == null) return GT.toInt();
        return a.compareTo(b);
    }

    public static <T> Comparator<T> of(Comparator<T> comparator) {
        return (a, b) -> {
            if (a == null && b == null) return EQ.toInt();
            if (a == null) return LT.toInt();
            if (b == null) return GT.toInt();
            return comparator.compare(a, b);
        };
    }
}
