package mho.wheels.structures;

import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.assertEquals;
import static mho.wheels.testing.Testing.fail;

public class SeptupleProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_SEPTUPLE_CHARS = " (),-0123456789lnu";

    public SeptupleProperties() {
        super("Septuple");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesToList();
        propertiesFromList();
    }

    private void propertiesConstructor() {
        initialize("Septuple<A, B, C, D, E, F, G>(A, B, C, D, E, F, G)");
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> t =
                    new Septuple<>(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
            assertEquals(s, t.a, s.a);
            assertEquals(s, t.b, s.b);
            assertEquals(s, t.c, s.c);
            assertEquals(s, t.d, s.d);
            assertEquals(s, t.e, s.e);
            assertEquals(s, t.f, s.f);
            assertEquals(s, t.g, s.g);
        }
    }

    private void propertiesToList() {
        initialize("toList(Septuple<A, B, C, D, E, F, G>)");
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            List<Integer> xs = Septuple.toList(s);
            assertEquals(s, xs.size(), 7);
            assertEquals(s, xs.get(0), s.a);
            assertEquals(s, xs.get(1), s.b);
            assertEquals(s, xs.get(2), s.c);
            assertEquals(s, xs.get(3), s.d);
            assertEquals(s, xs.get(4), s.e);
            assertEquals(s, xs.get(5), s.f);
            assertEquals(s, xs.get(6), s.g);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(7, P.withNull(P.integers())))) {
            Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s = Septuple.fromList(xs);
            assertEquals(xs, s.a, xs.get(0));
            assertEquals(xs, s.b, xs.get(1));
            assertEquals(xs, s.c, xs.get(2));
            assertEquals(xs, s.d, xs.get(3));
            assertEquals(xs, s.e, xs.get(4));
            assertEquals(xs, s.f, xs.get(5));
            assertEquals(xs, s.g, xs.get(6));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 7, P.lists(P.withNull(P.integers()))))) {
            try {
                Septuple.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
