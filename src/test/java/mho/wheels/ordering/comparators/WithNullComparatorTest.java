package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class WithNullComparatorTest {
    private static final @NotNull Comparator<Integer> ODDS_BEFORE_EVENS = (i, j) -> {
        boolean iEven = (i & 1) == 0;
        boolean jEven = (j & 1) == 0;
        if (iEven && !jEven) return 1;
        if (!iEven && jEven) return -1;
        return Integer.compare(i, j);
    };

    @Test
    public void testConstructor() {
        new WithNullComparator<Integer>();
    }

    @Test
    public void testConstructor_Comparator() {
        new WithNullComparator<>(ODDS_BEFORE_EVENS);
    }

    private static void compare_helper(@NotNull WithNullComparator<Integer> comparator, @NotNull String list) {
        Testing.testCompareToHelper(comparator, readIntegerListWithNulls(list));
    }

    @Test
    public void testCompare() {
        compare_helper(new WithNullComparator<>(), "[null, -3, -2, -1, 0, 1, 2, 3]");
        compare_helper(new WithNullComparator<>(ODDS_BEFORE_EVENS), "[null, -3, -1, 1, 3, -2, 0, 2]");
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }
}
