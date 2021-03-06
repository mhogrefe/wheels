package mho.wheels.io;

import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public strictfp class ReadersProperties extends TestProperties {
    private static final @NotNull String BOOLEAN_CHARS = "aeflrstu";
    private static final @NotNull String ROUNDING_MODE_CHARS = "ACDEFGHILNOPRSUVWY_";
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";
    private static final @NotNull String FLOATING_POINT_CHARS = "-.0123456789EINafinty";
    private static final @NotNull String BIG_DECIMAL_CHARS = "+-.0123456789E";
    private static final @NotNull String ALL_CHARS = charsToString(EP.characters());

    public ReadersProperties() {
        super("Readers");
    }

    @Override
    protected void testBothModes() {
        propertiesGenericReadStrict();
        propertiesReadBooleanStrict();
        propertiesReadRoundingModeStrict();
        propertiesReadBigIntegerStrict();
        propertiesReadByteStrict();
        propertiesReadShortStrict();
        propertiesReadIntegerStrict();
        propertiesReadLongStrict();
        propertiesReadFloatStrict();
        propertiesReadDoubleStrict();
        propertiesReadBigDecimalStrict();
        propertiesReadCharacterStrict();
        propertiesReadStringStrict();
        propertiesReadWithNullsStrict();
        propertiesReadOptionalStrict();
        propertiesReadNullableOptionalStrict();
        propertiesReadListStrict();
        propertiesReadListWithNullsStrict();
    }

    private void propertiesGenericReadStrict() {
        initialize("genericReadStrict(Function<String, T>)");
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            genericReadStrict(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            String s = Integer.toString(i);
            Function<String, Integer> f = new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(s, i)));
            assertEquals(i, genericReadStrict(f).apply(s).get(), i);
        }
    }

    private void propertiesReadBooleanStrict() {
        initialize("readBooleanStrict(String)");
        propertiesReadHelper(LIMIT, P, BOOLEAN_CHARS, P.booleans(), Readers::readBooleanStrict, b -> {}, false, true);
    }

    private void propertiesReadRoundingModeStrict() {
        initialize("readRoundingModeStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                ROUNDING_MODE_CHARS,
                P.roundingModes(),
                Readers::readRoundingModeStrict,
                rm -> {},
                false,
                true
        );
    }

    private void propertiesReadBigIntegerStrict() {
        initialize("readBigIntegerStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                INTEGRAL_CHARS,
                P.bigIntegers(),
                Readers::readBigIntegerStrict,
                i -> {},
                false,
                true
        );
    }

    private void propertiesReadByteStrict() {
        initialize("readByteStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.bytes(), Readers::readByteStrict, b -> {}, false, true);
    }

    private void propertiesReadShortStrict() {
        initialize("readShortStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.shorts(), Readers::readShortStrict, s -> {}, false, true);
    }

    private void propertiesReadIntegerStrict() {
        initialize("readIntegerStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.integers(), Readers::readIntegerStrict, i -> {}, false, true);
    }

    private void propertiesReadLongStrict() {
        initialize("readLongStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.longs(), Readers::readLongStrict, l -> {}, false, true);
    }

    private void propertiesReadFloatStrict() {
        initialize("readFloatStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                FLOATING_POINT_CHARS,
                P.floats(),
                Readers::readFloatStrict,
                f -> {},
                false,
                true
        );
    }

    private void propertiesReadDoubleStrict() {
        initialize("readDoubleStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                FLOATING_POINT_CHARS,
                P.doubles(),
                Readers::readDoubleStrict,
                d -> {},
                false,
                true
        );
    }

    private void propertiesReadBigDecimalStrict() {
        initialize("readBigDecimalStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                BIG_DECIMAL_CHARS,
                P.bigDecimals(),
                Readers::readBigDecimalStrict,
                bd -> {},
                false,
                true
        );
    }

    private void propertiesReadCharacterStrict() {
        initialize("readCharacterStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                ALL_CHARS,
                P.characters(),
                Readers::readCharacterStrict,
                c -> {},
                false,
                true
        );
    }

    private void propertiesReadStringStrict() {
        initialize("readStringStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                ALL_CHARS,
                P.strings(),
                Readers::readStringStrict,
                c -> {},
                true,
                true
        );
    }

    private void propertiesReadWithNullsStrict() {
        initialize("readWithNullsStrict(Function<String, Optional<T>>)");
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            readWithNullsStrict(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            String s = Integer.toString(i);
            Function<String, Optional<Integer>> f = new FiniteDomainFunction<>(
                    Collections.singletonList(new Pair<>(s, Optional.of(i)))
            );
            assertEquals(i, readWithNullsStrict(f).apply(s).get(), i);
            assertNull(i, readWithNullsStrict(f).apply("null").get());
        }
    }

    private void propertiesReadOptionalStrict() {
        initialize("readOptionalStrict(Function<String, Optional<T>>)");
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            readOptionalStrict(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            String s = Integer.toString(i);
            Function<String, Optional<Integer>> f = new FiniteDomainFunction<>(
                    Collections.singletonList(new Pair<>(s, Optional.of(i)))
            );
            assertEquals(i, readOptionalStrict(f).apply("Optional[" + s + "]").get().get(), i);
            assertFalse(i, readOptionalStrict(f).apply("Optional.empty").get().isPresent());
        }
    }

    private void propertiesReadNullableOptionalStrict() {
        initialize("readNullableOptionalStrict(Function<String, NullableOptional<T>>)");
        Iterable<Pair<Function<String, NullableOptional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.nullableOptionals(P.withNull(P.integers())))
        );
        for (Pair<Function<String, NullableOptional<Integer>>, String> p : take(LIMIT, ps)) {
            readNullableOptionalStrict(p.a).apply(p.b);
        }

        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            String s = i == null ? "null" : Integer.toString(i);
            Function<String, NullableOptional<Integer>> f = new FiniteDomainFunction<>(
                    Collections.singletonList(new Pair<>(s, NullableOptional.of(i)))
            );
            assertEquals(i, readNullableOptionalStrict(f).apply("NullableOptional[" + s + "]").get().get(), i);
            assertFalse(i, readNullableOptionalStrict(f).apply("NullableOptional.empty").get().isPresent());
        }
    }

    private void propertiesReadListStrict() {
        initialize("readListStrict(Function<String, Optional<T>>)");
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                q -> new Pair<>((Function<String, Optional<Integer>>) q.b, q.b.domain().toString()),
                P.dependentPairsInfinite(
                        P.withScale(4).subsetsAtLeast(1, P.withScale(4).strings()),
                        ss -> map(FiniteDomainFunction::new, P.maps(ss, P.nonEmptyOptionals(P.integers()))
)
                )
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            readListStrict(p.a).apply(p.b);
        }

        for (List<Integer> xs : take(LIMIT, P.lists(P.integers()))) {
            assertEquals(xs, readListStrict(Readers::readIntegerStrict).apply(xs.toString()).get(), xs);
        }
    }

    private void propertiesReadListWithNullsStrict() {
        initialize("readListWithNullsStrict(Function<String, Optional<T>>)");
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                q -> new Pair<>((Function<String, Optional<Integer>>) q.b, q.b.domain().toString()),
                P.dependentPairsInfinite(
                        P.withScale(4).subsetsAtLeast(1, P.withScale(4).strings()),
                        ss -> map(FiniteDomainFunction::new, P.maps(ss, P.nonEmptyOptionals(P.integers())))
                )
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            readListWithNullsStrict(p.a).apply(p.b);
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integers()))) {
            assertEquals(xs, readListWithNullsStrict(Readers::readIntegerStrict).apply(xs.toString()).get(), xs);
        }
    }
}
