package mho.wheels.ordering.comparators;

import java.util.Comparator;

public class WithNullComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T x, T y) {
        if (x == null && y == null) return 0;
        if (x == null) return -1;
        if (y == null) return 1;
        return x.compareTo(y);
    }
}
