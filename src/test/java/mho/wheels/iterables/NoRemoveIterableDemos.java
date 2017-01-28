package mho.wheels.iterables;

import mho.wheels.testing.Demos;

import java.util.Iterator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.EP;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;
import static mho.wheels.testing.Testing.its;

@SuppressWarnings("UnusedDeclaration")
public class NoRemoveIterableDemos extends Demos {
    public NoRemoveIterableDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            NoRemoveIterable<Integer> nrxs = new NoRemoveIterable<>(xs);
            System.out.println("NoRemoveIterable from " + xs + ": " + its(nrxs));
        }
    }
}
