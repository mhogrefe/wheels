package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.structures.NullableOptional.*;
import static mho.wheels.testing.Testing.*;

public class NullableOptionalProperties extends TestProperties {
    private static final @NotNull String NULLABLE_OPTIONAL_INTEGER_CHARS = "-.0123456789NO[]abeilmnoptuy";

    public NullableOptionalProperties() {
        super("NullableOptional");
    }

    @Override
    protected void testBothModes() {
        propertiesOf_Integer();
        propertiesIsPresent_Integer();
        propertiesGet_Integer();
        propertiesFromOptional_Integer();
        propertiesOrElse();
        propertiesMap();
        propertiesEquals();
        propertiesHashCode_Integer();
        propertiesToString_Integer();
    }

    private void propertiesOf_Integer() {
        initialize("of(NullableOptional<T>)");
        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            NullableOptional<Integer> noi = of(i);
            assertTrue(i, noi.isPresent());
            //noinspection RedundantTypeArguments
            inverse(NullableOptional::of, NullableOptional<Integer>::get, i);
            assertEquals(i, noi.toString(), "NullableOptional[" + i + "]");
        }
    }

    private void propertiesIsPresent_Integer() {
        initialize("isPresent()");
        for (NullableOptional<Integer> noi : take(LIMIT, P.nullableOptionals(P.withNull(P.integers())))) {
            noi.isPresent();
        }
    }

    private void propertiesGet_Integer() {
        initialize("get()");
        for (NullableOptional<Integer> noi : take(LIMIT, P.nonEmptyNullableOptionals(P.withNull(P.integers())))) {
            noi.get();
            //noinspection RedundantTypeArguments
            inverse(NullableOptional<Integer>::get, NullableOptional::of, noi);
        }
    }

    private void propertiesFromOptional_Integer() {
        initialize("fromOptional(Optional<T>)");
        for (Optional<Integer> oi : take(LIMIT, P.optionals(P.integers()))) {
            NullableOptional<Integer> noi = fromOptional(oi);
            assertEquals(oi, oi.isPresent(), noi.isPresent());
            //noinspection Convert2MethodRef
            inverse(
                    (Optional<Integer> n) -> fromOptional(n),
                    (NullableOptional<Integer> m) -> m.isPresent() ? Optional.of(m.get()) : Optional.empty(),
                    oi
            );
            assertEquals(oi, oi.toString(), noi.toString().substring("Nullable".length()));
        }

        for (Optional<Integer> oi : take(LIMIT, P.nonEmptyOptionals(P.integers()))) {
            NullableOptional<Integer> noi = fromOptional(oi);
            assertNotNull(oi, noi.get());
            assertEquals(oi, oi.get(), noi.get());
        }
    }

    private void propertiesOrElse() {
        initialize("orElse(T)");
        Iterable<Pair<NullableOptional<Integer>, Integer>> ps = P.pairs(
                P.nullableOptionals(P.integers()),
                P.integers()
        );
        for (Pair<NullableOptional<Integer>, Integer> p : take(LIMIT, ps)) {
            p.a.orElse(p.b);
        }

        ps = P.pairs(P.nonEmptyNullableOptionals(P.integers()), P.integers());
        for (Pair<NullableOptional<Integer>, Integer> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.orElse(p.b), p.a.get());
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(i, empty().orElse(i), i);
        }
    }

    private void propertiesMap() {
        initialize("map(Function<T, V>)");
        Iterable<Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>>> ps = P.withElement(
                new Pair<>(empty(), new FiniteDomainFunction<>(Collections.emptyMap())),
                map(
                        p -> new Pair<>(of(p.a), new FiniteDomainFunction<>(Collections.singletonList(p))),
                        P.pairs(P.integers())
                )
        );
        for (Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>> p : take(LIMIT, ps)) {
            p.a.map(p.b);
        }

        ps = map(
                p -> new Pair<>(of(p.a), new FiniteDomainFunction<>(Collections.singletonList(p))),
                P.pairs(P.integers())
        );
        for (Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.map(p.b).get(), p.b.apply(p.a.get()));
        }

        Iterable<Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>>> psFail = map(
                t -> new Pair<>(of(t.a), new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(t.b, t.c)))),
                filter(t -> !t.a.equals(t.b), P.triples(P.integers()))
        );
        for (Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>> p : take(LIMIT, psFail)) {
            try {
                p.a.map(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, p -> p.nullableOptionals(p.withNull(p.integers())));
    }

    private void propertiesHashCode_Integer() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, p -> p.nullableOptionals(p.withNull(p.integers())));
    }

    private void propertiesToString_Integer() {
        initialize("toString()");
        propertiesToStringHelper(
                LIMIT,
                NULLABLE_OPTIONAL_INTEGER_CHARS,
                P.nullableOptionals(P.withNull(P.integers())),
                Readers.readNullableOptionalStrict(Readers.readWithNullsStrict(Readers::readIntegerStrict))
        );
    }
}
