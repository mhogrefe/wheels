package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class ListBasedComparatorTest {
    private static void constructor_helper(@NotNull String input) {
        new ListBasedComparator<>(readIntegerListWithNulls(input));
    }

    @Test
    public void testConstructor() {
        constructor_helper("[3, null, 2]");
        constructor_helper("[]");
    }

    private static void compare_helper(@NotNull ListBasedComparator<Integer> comparator, @NotNull String list) {
        Testing.testCompareToHelper(comparator, readIntegerListWithNulls(list));
    }

    private static void compare_fail_helper(@NotNull ListBasedComparator<Integer> comparator, int x, int y) {
        try {
            comparator.compare(x, y);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCompare() {
        ListBasedComparator<Integer> comparator = new ListBasedComparator<>(readIntegerListWithNulls("[3, null, 2]"));
        ListBasedComparator<Integer> uselessComparator = new ListBasedComparator<>(Collections.emptyList());

        compare_helper(comparator, "[3, null, 2]");

        compare_fail_helper(comparator, 1, 2);
        compare_fail_helper(uselessComparator, 1, 2);
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }
}
