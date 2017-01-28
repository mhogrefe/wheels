package mho.wheels.iterables;

import mho.wheels.testing.TestProperties;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.iterables.IterableUtils.toList;
import static mho.wheels.testing.Testing.*;

public class NoRemoveIterableProperties extends TestProperties {
    public NoRemoveIterableProperties() {
        super("NoRemoveIterable");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesRemove();
    }

    private void propertiesConstructor() {
        initialize("NoRemoveIterable(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            NoRemoveIterable<Integer> nrxs = new NoRemoveIterable<>(xs);
            assertEquals(xs, toList(nrxs), xs);
        }
    }

    private void propertiesRemove() {
        initialize("remove()");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            NoRemoveIterable<Integer> nrxs = new NoRemoveIterable<>(xs);
            testNoRemove(TINY_LIMIT, nrxs);
        }
    }
}
