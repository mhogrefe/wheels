package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.structures.NullableOptional.fromOptional;
import static mho.wheels.structures.NullableOptional.of;
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
        propertiesEquals();
        propertiesHashCode_Integer();
        propertiesToString_Integer();
    }

    private void propertiesOf_Integer() {
        initialize("of(NullableOptional<Integer>)");
        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            NullableOptional<Integer> noi = of(i);
            assertTrue(i, noi.isPresent());
            //noinspection RedundantTypeArguments
            inverses(NullableOptional::of, NullableOptional<Integer>::get, i);
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
            inverses(NullableOptional<Integer>::get, NullableOptional::of, noi);
        }
    }

    private void propertiesFromOptional_Integer() {
        initialize("fromOptional(Optional<Integer>)");
        for (Optional<Integer> oi : take(LIMIT, P.optionals(P.integers()))) {
            NullableOptional<Integer> noi = fromOptional(oi);
            assertEquals(oi, oi.isPresent(), noi.isPresent());
            //noinspection Convert2MethodRef
            inverses(
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
                Readers.readNullableOptional(Readers.readWithNulls(Readers::readInteger))
        );
    }
}
