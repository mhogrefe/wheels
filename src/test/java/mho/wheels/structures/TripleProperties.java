package mho.wheels.structures;

import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.assertEquals;
import static mho.wheels.testing.Testing.fail;

public class TripleProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_TRIPLE_CHARS = " (),-0123456789lnu";

    public TripleProperties() {
        super("Triple");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesToList();
        propertiesFromList();
    }

    private void propertiesConstructor() {
        initialize("Triple<A, B, C>(A, B, C)");
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            Triple<Integer, Integer, Integer> s = new Triple<>(t.a, t.b, t.c);
            assertEquals(t, s.a, t.a);
            assertEquals(t, s.b, t.b);
            assertEquals(t, s.c, t.c);
        }
    }

    private void propertiesToList() {
        initialize("toList(Triple<A, B, C>)");
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, P.triples(P.withNull(P.integers())))) {
            List<Integer> xs = Triple.toList(t);
            assertEquals(t, xs.size(), 3);
            assertEquals(t, xs.get(0), t.a);
            assertEquals(t, xs.get(1), t.b);
            assertEquals(t, xs.get(2), t.c);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(3, P.withNull(P.integers())))) {
            Triple<Integer, Integer, Integer> p = Triple.fromList(xs);
            assertEquals(xs, p.a, xs.get(0));
            assertEquals(xs, p.b, xs.get(1));
            assertEquals(xs, p.c, xs.get(2));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 3, P.lists(P.withNull(P.integers()))))) {
            try {
                Triple.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
