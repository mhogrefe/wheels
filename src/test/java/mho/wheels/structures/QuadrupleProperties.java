package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.propertiesHashCodeHelper;

public class QuadrupleProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_QUADRUPLE_CHARS = " (),-0123456789lnu";

    public QuadrupleProperties() {
        super("Quadruple");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesToList();
        propertiesFromList();
        propertiesCompare();
        propertiesEquals();
        propertiesHashCode();
        propertiesReadStrict();
        propertiesToString();
    }

    private void propertiesConstructor() {
        initialize("Quadruple<A, B, C, D>(A, B, C, D)");
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            Quadruple<Integer, Integer, Integer, Integer> r = new Quadruple<>(q.a, q.b, q.c, q.d);
            assertEquals(q, r.a, q.a);
            assertEquals(q, r.b, q.b);
            assertEquals(q, r.c, q.c);
            assertEquals(q, r.d, q.d);
        }
    }

    private void propertiesToList() {
        initialize("toList(Quadruple<A, B, C, D>)");
        for (Quadruple<Integer, Integer, Integer, Integer> q : take(LIMIT, P.quadruples(P.withNull(P.integers())))) {
            List<Integer> xs = Quadruple.toList(q);
            assertEquals(q, xs.size(), 4);
            assertEquals(q, xs.get(0), q.a);
            assertEquals(q, xs.get(1), q.b);
            assertEquals(q, xs.get(2), q.c);
            assertEquals(q, xs.get(3), q.d);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(4, P.withNull(P.integers())))) {
            Quadruple<Integer, Integer, Integer, Integer> q = Quadruple.fromList(xs);
            assertEquals(xs, q.a, xs.get(0));
            assertEquals(xs, q.b, xs.get(1));
            assertEquals(xs, q.c, xs.get(2));
            assertEquals(xs, q.d, xs.get(3));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 4, P.lists(P.withNull(P.integers()))))) {
            try {
                Quadruple.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesCompare() {
        initialize("compare(Quadruple<A, B, C, D>, Quadruple<A, B, C, D>)");
        propertiesCompareToHelper(
                LIMIT,
                P,
                (x, y) -> Quadruple.compare(x, y).toInt(),
                p -> p.quadruples(p.integers())
        );
    }

    private void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, p -> p.quadruples(p.integers()));
    }

    private void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, p -> p.quadruples(p.integers()));
    }

    private void propertiesReadStrict() {
        initialize("readStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                NULLABLE_INTEGER_QUADRUPLE_CHARS,
                P.quadruples(P.withNull(P.integers())),
                s -> Quadruple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                ),
                q -> {},
                false,
                true
        );
    }

    private void propertiesToString() {
        initialize("toString()");
        propertiesToStringHelper(
                LIMIT,
                NULLABLE_INTEGER_QUADRUPLE_CHARS,
                P.quadruples(P.withNull(P.integers())),
                s -> Quadruple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                )
        );
    }
}
