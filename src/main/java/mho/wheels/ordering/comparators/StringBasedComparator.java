package mho.wheels.ordering.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StringBasedComparator implements Comparator<Character> {
    private final @NotNull Map<Character, Integer> indexMap;

    public StringBasedComparator(@NotNull String s) {
        indexMap = new HashMap<>();
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!indexMap.containsKey(c)) {
                indexMap.put(c, j);
                j++;
            }
        }
    }

    @Override
    public int compare(Character x, Character y) {
        return x == y ? 0 : Integer.compare(indexMap.get(x), indexMap.get(y));
    }
}
