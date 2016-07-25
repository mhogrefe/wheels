package mho.wheels.structures;

import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.assertEquals;
import static mho.wheels.testing.Testing.fail;

public class PairProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_PAIR_CHARS = " (),-0123456789lnu";

    public PairProperties() {
        super("Pair");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesToList();
        propertiesFromList();
    }

    private void propertiesConstructor() {
        initialize("Pair<A, B>(A, B)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            Pair<Integer, Integer> q = new Pair<>(p.a, p.b);
            assertEquals(p, q.a, p.a);
            assertEquals(p, q.b, p.b);
        }
    }

    private void propertiesToList() {
        initialize("toList(Pair<A, B>)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integers())))) {
            List<Integer> xs = Pair.toList(p);
            assertEquals(p, xs.size(), 2);
            assertEquals(p, xs.get(0), p.a);
            assertEquals(p, xs.get(1), p.b);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(2, P.withNull(P.integers())))) {
            Pair<Integer, Integer> p = Pair.fromList(xs);
            assertEquals(xs, p.a, xs.get(0));
            assertEquals(xs, p.b, xs.get(1));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 2, P.lists(P.withNull(P.integers()))))) {
            try {
                Pair.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
