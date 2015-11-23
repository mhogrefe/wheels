package mho.wheels.iterables;

import org.junit.Assert;
import org.junit.Test;

public class NoRemoveIteratorTest {
    @Test
    public void testRemove() {
        NoRemoveIterator<Integer> it = new NoRemoveIterator<Integer>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return 0;
            }
        };
        try {
            it.remove();
            Assert.fail();
        } catch (UnsupportedOperationException ignored) {}
    }
}
