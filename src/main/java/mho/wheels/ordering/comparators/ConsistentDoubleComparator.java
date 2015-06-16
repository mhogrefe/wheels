package mho.wheels.ordering.comparators;

import mho.wheels.misc.FloatingPointUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class ConsistentDoubleComparator implements Comparator<Double> {
    public static final @NotNull ConsistentDoubleComparator INSTANCE = new ConsistentDoubleComparator();

    @Override
    public int compare(@NotNull Double x, @NotNull Double y) {
        //noinspection NumberEquality
        if (x == y) return 0;
        int xType = type(x);
        int yType = type(y);
        if (xType > yType) return 1;
        if (xType < yType) return -1;
        return xType == 2 || xType == -2 ? Double.compare(x, y) : 0;
    }

    private static int type(double d) {
        int type = 0;
        if (d > 0.0) {
            type = 2;
        } else if (d < 0.0) {
            type = -2;
        } else if (FloatingPointUtils.isPositiveZero(d)) {
            type = 1;
        } else if (FloatingPointUtils.isNegativeZero(d)) {
            type = -1;
        }
        return type;
    }
}
