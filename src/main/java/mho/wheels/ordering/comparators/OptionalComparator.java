package mho.wheels.ordering.comparators;

import java.util.Comparator;
import java.util.Optional;

public class OptionalComparator<T extends Comparable<T>> implements Comparator<Optional<T>> {
    @Override
    public int compare(Optional<T> ox, Optional<T> oy) {
        if (!ox.isPresent() && !oy.isPresent()) return 0;
        if (!ox.isPresent()) return -1;
        if (!oy.isPresent()) return 1;
        return ox.get().compareTo(oy.get());
    }
}
