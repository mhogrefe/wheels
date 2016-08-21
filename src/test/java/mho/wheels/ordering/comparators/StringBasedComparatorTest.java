package mho.wheels.ordering.comparators;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.fail;

public class StringBasedComparatorTest {
    private static void constructor_helper(@NotNull String input) {
        new StringBasedComparator(input);
    }

    @Test
    public void testConstructor() {
        constructor_helper("cat");
        constructor_helper("");
    }

    private static void compare_helper(@NotNull StringBasedComparator comparator, @NotNull String s) {
        Testing.testCompareToHelper(comparator, IterableUtils.toList(s));
    }

    private static void compare_fail_helper(@NotNull StringBasedComparator comparator, char x, char y) {
        try {
            comparator.compare(x, y);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCompare() {
        StringBasedComparator comparator = new StringBasedComparator("cat");
        StringBasedComparator uselessComparator = new StringBasedComparator("");

        compare_helper(comparator, "cat");

        compare_fail_helper(comparator, 'a', 'z');
        compare_fail_helper(uselessComparator, 'a', 'z');
    }
}
