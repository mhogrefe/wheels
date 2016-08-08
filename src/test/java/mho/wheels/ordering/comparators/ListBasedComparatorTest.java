package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class ListBasedComparatorTest {
    @Test
    public void testConstructor() {
        ListBasedComparator<Integer> comparator = new ListBasedComparator<>(readIntegerListWithNulls("[3, null, 2]"));
        Testing.testCompareToHelper(comparator, readIntegerListWithNulls("[3, null, 2]"));
        try {
            comparator.compare(1, 2);
            fail();
        } catch (IllegalArgumentException ignored) {}

        ListBasedComparator<Integer> uselessComparator = new ListBasedComparator<>(Collections.emptyList());
        try {
            uselessComparator.compare(1, 2);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }
}
