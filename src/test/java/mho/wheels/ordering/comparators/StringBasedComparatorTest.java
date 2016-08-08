package mho.wheels.ordering.comparators;

import mho.wheels.iterables.IterableUtils;
import mho.wheels.testing.Testing;
import org.junit.Test;

import static org.junit.Assert.fail;

public class StringBasedComparatorTest {
    @Test
    public void testConstructor() {
        StringBasedComparator comparator = new StringBasedComparator("cat");
        Testing.testCompareToHelper(comparator, IterableUtils.toList("cat"));
        try {
            comparator.compare('a', 'z');
            fail();
        } catch (IllegalArgumentException ignored) {}

        StringBasedComparator uselessComparator = new StringBasedComparator("");
        try {
            uselessComparator.compare('a', 'z');
            fail();
        } catch (IllegalArgumentException ignored) {}
    }
}
