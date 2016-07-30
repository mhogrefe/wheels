package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.filterInfinite;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;
import static mho.wheels.testing.Testing.propertiesHashCodeHelper;

public class SextupleProperties extends TestProperties {
    private static final @NotNull String NULLABLE_INTEGER_SEXTUPLE_CHARS = " (),-0123456789lnu";

    public SextupleProperties() {
        super("Sextuple");
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
        initialize("Sextuple<A, B, C, D, E, F>(A, B, C, D, E, F)");
        for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.sextuples(P.withNull(P.integers())))) {
            Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> t =
                    new Sextuple<>(s.a, s.b, s.c, s.d, s.e, s.f);
            assertEquals(s, t.a, s.a);
            assertEquals(s, t.b, s.b);
            assertEquals(s, t.c, s.c);
            assertEquals(s, t.d, s.d);
            assertEquals(s, t.e, s.e);
            assertEquals(s, t.f, s.f);
        }
    }

    private void propertiesToList() {
        initialize("toList(Sextuple<A, B, C, D, E, F>)");
        for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.sextuples(P.withNull(P.integers())))) {
            List<Integer> xs = Sextuple.toList(s);
            assertEquals(s, xs.size(), 6);
            assertEquals(s, xs.get(0), s.a);
            assertEquals(s, xs.get(1), s.b);
            assertEquals(s, xs.get(2), s.c);
            assertEquals(s, xs.get(3), s.d);
            assertEquals(s, xs.get(4), s.e);
            assertEquals(s, xs.get(5), s.f);
        }
    }

    private void propertiesFromList() {
        initialize("fromList(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(6, P.withNull(P.integers())))) {
            Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s = Sextuple.fromList(xs);
            assertEquals(xs, s.a, xs.get(0));
            assertEquals(xs, s.b, xs.get(1));
            assertEquals(xs, s.c, xs.get(2));
            assertEquals(xs, s.d, xs.get(3));
            assertEquals(xs, s.e, xs.get(4));
            assertEquals(xs, s.f, xs.get(5));
        }

        for (List<Integer> xs : take(LIMIT, filterInfinite(ys -> ys.size() != 6, P.lists(P.withNull(P.integers()))))) {
            try {
                Sextuple.fromList(xs);
                fail(xs);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesCompare() {
        initialize("compare(Sextuple<A, B, C, D, E, F>, Sextuple<A, B, C, D, E, F>)");
        propertiesCompareToHelper(
                LIMIT,
                P,
                (x, y) -> Sextuple.compare(x, y).toInt(),
                p -> p.sextuples(p.integers())
        );
    }

    private void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, p -> p.sextuples(p.integers()));
    }

    private void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, p -> p.sextuples(p.integers()));
    }

    private void propertiesReadStrict() {
        initialize("readStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                NULLABLE_INTEGER_SEXTUPLE_CHARS,
                P.sextuples(P.withNull(P.integers())),
                s -> Sextuple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                ),
                s -> {},
                false,
                true
        );
    }

    private void propertiesToString() {
        initialize("toString()");
        propertiesToStringHelper(
                LIMIT,
                NULLABLE_INTEGER_SEXTUPLE_CHARS,
                P.sextuples(P.withNull(P.integers())),
                s -> Sextuple.readStrict(
                        s,
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                )
        );
    }
}
