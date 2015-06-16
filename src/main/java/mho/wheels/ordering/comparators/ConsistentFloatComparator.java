package mho.wheels.ordering.comparators;

import mho.wheels.misc.FloatingPointUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class ConsistentFloatComparator implements Comparator<Float> {
    public static final @NotNull ConsistentFloatComparator INSTANCE = new ConsistentFloatComparator();

    @Override
    public int compare(@NotNull Float x, @NotNull Float y) {
        //noinspection NumberEquality
        if (x == y) return 0;
        int xType = type(x);
        int yType = type(y);
        if (xType > yType) return 1;
        if (xType < yType) return -1;
        return xType == 2 || xType == -2 ? Float.compare(x, y) : 0;
    }

    private static int type(float f) {
        int type = 0;
        if (f > 0.0f) {
            type = 2;
        } else if (f < 0.0f) {
            type = -2;
        } else if (FloatingPointUtils.isPositiveZero(f)) {
            type = 1;
        } else if (FloatingPointUtils.isNegativeZero(f)) {
            type = -1;
        }
        return type;
    }
}
