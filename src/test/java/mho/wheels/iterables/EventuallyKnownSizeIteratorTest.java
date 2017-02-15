package mho.wheels.iterables;

import mho.wheels.numberUtils.IntegerUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static mho.wheels.testing.Testing.*;

public class EventuallyKnownSizeIteratorTest {
    private static @NotNull EventuallyKnownSizeIterator<Integer> exampleIterator() {
        return new EventuallyKnownSizeIterator<Integer>() {
            private int i = 0;

            @Override
            public Integer advance() {
                return i++;
            }
        };
    }

    @Test
    public void testEventuallyKnownSizeIterator() {
        EventuallyKnownSizeIterator<Integer> it = exampleIterator();

        aeq(it.outputSizeKnown(), false);
        aeq(it.hasNext(), true);
        aeq(it.next(), 0);
        aeq(it.hasNext(), true);
        aeq(it.next(), 1);
        aeq(it.hasNext(), true);
        aeq(it.next(), 2);
        aeq(it.hasNext(), true);
        aeq(it.outputSizeKnown(), false);
        it.setOutputSize(BigInteger.valueOf(5));
        aeq(it.outputSizeKnown(), true);
        aeq(it.next(), 3);
        aeq(it.hasNext(), true);
        aeq(it.next(), 4);
        aeq(it.hasNext(), false);

        it = exampleIterator();
        try {
            it.setOutputSize(IntegerUtils.NEGATIVE_ONE);
            Assert.fail();
        } catch (IllegalStateException ignored) {}

        it = exampleIterator();
        it.next();
        it.next();
        try {
            it.setOutputSize(BigInteger.ONE);
            Assert.fail();
        } catch (IllegalStateException ignored) {}

        it = exampleIterator();
        it.setOutputSize(BigInteger.ZERO);
        aeq(it.hasNext(), false);
    }
}
