package mho.wheels.ordering.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBasedComparator<T> implements Comparator<T> {
    private final @NotNull Map<T, Integer> indexMap;

    public ListBasedComparator(@NotNull List<T> list) {
        indexMap = new HashMap<>();
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            T x = list.get(i);
            if (!indexMap.containsKey(x)) {
                indexMap.put(x, j);
                j++;
            }
        }
    }

    @Override
    public int compare(T x, T y) {
        return x == y ? 0 : Integer.compare(indexMap.get(x), indexMap.get(y));
    }
}
