package mho.wheels.iterables;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NoRemoveIterableTest {
    @Test
    public void testRemove() {
        List<Integer> xs = new ArrayList<>();
        xs.add(1);
        xs.add(2);
        xs.add(3);
        NoRemoveIterable<Integer> nrxs = new NoRemoveIterable<>(xs);
        try {
            nrxs.iterator().remove();
            Assert.fail();
        } catch (UnsupportedOperationException ignored) {}
    }
}
