package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.propertiesHashCodeHelper;

public class QuintupleProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_QUINTUPLE_CHARS = " (),-0123456789lnu";

    public QuintupleProperties() {
        super("Quintuple");
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
        initialize("Quintuple<A, B, C, D, E>(A, B, C, D, E)");
        for (Quintuple<Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.quintuples(P.withNull(P.integers())))) {
            Quintuple<Integer, Integer, Integer, Integer, Integer> r = new Quintuple<>(q.a, q.b, q.c, q.d, q.e);
            assertEquals(q, r.a, q.a);
            assertEquals(q, r.b, q.b);
            assertEquals(q, r.c, q.c);
            assertEquals(q, r.d, q.d);
            assertEquals(q, r.e, q.e);
        }
    }

    private void propertiesToList() {
        initialize("toList(Quintuple<A, B, C, D, E>)");
        for (Quintuple<Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.quintuples(P.withNull(P.integers())))) {
            List<Integer> xs = Quintuple.toList(q);
            assertEquals(q, xs.size(), 5);
            assertEquals(q, xs.get(0), q.a);
            assertEquals(q, xs.get(1), q.b);
            assertEquals(q, xs.get(2), q.c);
            assertEquals(q, xs.get(3), q.d);
            assertEquals(q, xs.get(4), q.e);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(5, P.withNull(P.integers())))) {
            Quintuple<Integer, Integer, Integer, Integer, Integer> q = Quintuple.fromList(xs);
            assertEquals(xs, q.a, xs.get(0));
            assertEquals(xs, q.b, xs.get(1));
            assertEquals(xs, q.c, xs.get(2));
            assertEquals(xs, q.d, xs.get(3));
            assertEquals(xs, q.e, xs.get(4));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 5, P.lists(P.withNull(P.integers()))))) {
            try {
                Quintuple.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesCompare() {
        initialize("compare(Quintuple<A, B, C, D, E>, Quintuple<A, B, C, D, E>)");
        propertiesCompareToHelper(
                LIMIT,
                P,
                (x, y) -> Quintuple.compare(x, y).toInt(),
                p -> p.quintuples(p.integers())
        );
    }

    private void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, p -> p.quintuples(p.integers()));
    }

    private void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, p -> p.quintuples(p.integers()));
    }

    private void propertiesReadStrict() {
        initialize("readStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                NULLABLE_INTEGER_QUINTUPLE_CHARS,
                P.quintuples(P.withNull(P.integers())),
                s -> Quintuple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
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
                NULLABLE_INTEGER_QUINTUPLE_CHARS,
                P.quintuples(P.withNull(P.integers())),
                s -> Quintuple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                )
        );
    }
}
