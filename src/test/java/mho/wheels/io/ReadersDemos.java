package mho.wheels.io;

import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.nicePrint;

@SuppressWarnings("UnusedDeclaration")
public class ReadersDemos extends Demos {
    private static final @NotNull String BOOLEAN_CHARS = "aeflrstu";
    private static final @NotNull String ORDERING_CHARS = "EGLQT";
    private static final @NotNull String ROUNDING_MODE_CHARS = "ACDEFGHILNOPRSUVWY_";
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";
    private static final @NotNull String FLOATING_POINT_CHARS = "-.0123456789EINafinty";
    private static final @NotNull String BIG_DECIMAL_CHARS = "+-.0123456789E";

    public ReadersDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoGenericReadStrict() {
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            System.out.println("genericReadStrict(" + p.a + ").apply(" + nicePrint(p.b) + ") = " +
                    genericReadStrict(p.a).apply(p.b));
        }
    }

    private void demoGenericReadStrict_targeted() {
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            System.out.println("genericReadStrict(" + p.a + ").apply(" + p.b + ") = " +
                    genericReadStrict(p.a).apply(p.b));
        }
    }

    private void demoReadBooleanStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBooleanStrict(" + nicePrint(s) + ") = " + readBooleanStrict(s));
        }
    }

    private void demoReadBooleanStrict_targeted() {
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("readBooleanStrict(" + s + ") = " + readBooleanStrict(s));
        }
    }

    private void demoReadOrderingStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrderingStrict(" + nicePrint(s) + ") = " + readOrderingStrict(s));
        }
    }

    private void demoReadOrderingStrict_targeted() {
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("readOrderingStrict(" + s + ") = " + readOrderingStrict(s));
        }
    }

    private void demoReadRoundingModeStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readRoundingModeStrict(" + nicePrint(s) + ") = " + readRoundingModeStrict(s));
        }
    }

    private void demoReadRoundingModeStrict_targeted() {
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("readRoundingModeStrict(" + s + ") = " + readRoundingModeStrict(s));
        }
    }

    private void demoReadBigIntegerStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigIntegerStrict(" + nicePrint(s) + ") = " + readBigIntegerStrict(s));
        }
    }

    private void demoReadBigIntegerStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readBigIntegerStrict(" + s + ") = " + readBigIntegerStrict(s));
        }
    }

    private void demoReadByteStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readByteStrict(" + nicePrint(s) + ") = " + readByteStrict(s));
        }
    }

    private void demoReadByteStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readByteStrict(" + s + ") = " + readByteStrict(s));
        }
    }

    private void demoReadShortStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readShortStrict(" + nicePrint(s) + ") = " + readShortStrict(s));
        }
    }

    private void demoReadShortStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readShortStrict(" + s + ") = " + readShortStrict(s));
        }
    }

    private void demoReadIntegerStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readIntegerStrict(" + nicePrint(s) + ") = " + readByteStrict(s));
        }
    }

    private void demoReadIntegerStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readIntegerStrict(" + s + ") = " + readIntegerStrict(s));
        }
    }

    private void demoReadLongStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readLongStrict(" + nicePrint(s) + ") = " + readLongStrict(s));
        }
    }

    private void demoReadLongStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readLongStrict(" + s + ") = " + readLongStrict(s));
        }
    }

    private void demoReadFloatStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readFloatStrict(" + nicePrint(s) + ") = " + readFloatStrict(s));
        }
    }

    private void demoReadFloatStrict_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readFloatStrict(" + s + ") = " + readFloatStrict(s));
        }
    }

    private void demoReadDoubleStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readDoubleStrict(" + nicePrint(s) + ") = " + readDoubleStrict(s));
        }
    }

    private void demoReadDoubleStrict_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readDoubleStrict(" + s + ") = " + readDoubleStrict(s));
        }
    }

    private void demoReadBigDecimalStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigDecimalStrict(" + nicePrint(s) + ") = " + readBigDecimalStrict(s));
        }
    }

    private void demoReadBigDecimalStrict_targeted() {
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("readBigDecimalStrict(" + s + ") = " + readBigDecimalStrict(s));
        }
    }

    private void demoReadCharacterStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readCharacterStrict(" + nicePrint(s) + ") = " + readCharacterStrict(s));
        }
    }

    private void demoReadStringStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readStringStrict(" + nicePrint(s) + ") = " + readStringStrict(s));
        }
    }

    private void demoReadWithNullsStrict() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readWithNullsStrict(" + nicePrint(p.a.toString()) + ").apply(" + nicePrint(p.b) + ") = " +
                    readWithNullsStrict(p.a).apply(p.b));
        }
    }

    private void demoReadWithNullsStrict_targeted() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(sort(nub(INTEGRAL_CHARS + "null"))), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readWithNullsStrict(" + p.a + ").apply(" + p.b + ") = " +
                    readWithNullsStrict(p.a).apply(p.b));
        }
    }

    private void demoReadOptionalStrict() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readOptionalStrict(" + nicePrint(p.a.toString()) + ").apply(" + nicePrint(p.b) + ") = " +
                    readOptionalStrict(p.a).apply(p.b));
        }
    }

    private void demoReadOptionalStrict_targeted() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(sort(nub(INTEGRAL_CHARS + "Optional[]"))), P.optionals(P.integers()))
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readOptionalStrict(" + p.a + ").apply(" + nicePrint(p.b) + ") = " +
                    readOptionalStrict(p.a).apply(p.b));
        }
    }

    private void demoReadNullableOptionalStrict() {
        Iterable<Pair<Function<String, NullableOptional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(), P.nullableOptionals(P.withNull(P.integers())))
        );
        for (Pair<Function<String, NullableOptional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readNullableOptionalStrict(" + nicePrint(p.a.toString()) + ").apply(" +
                    nicePrint(p.b) + ") = " + readNullableOptionalStrict(p.a).apply(p.b));
        }
    }

    private void demoReadNullableOptionalStrict_targeted() {
        Iterable<Pair<Function<String, NullableOptional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(
                        P.strings(sort(nub(INTEGRAL_CHARS + "NullableOptional[]"))),
                        P.nullableOptionals(P.withNull(P.integers()))
                )
        );
        for (Pair<Function<String, NullableOptional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readNullableOptionalStrict(" + p.a + ").apply(" + nicePrint(p.b) + ") = " +
                    readNullableOptionalStrict(p.a).apply(p.b));
        }
    }

    private void demoReadListStrict() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                q -> new Pair<>((Function<String, Optional<Integer>>) q.b, q.b.domain().toString()),
                P.dependentPairsInfinite(
                        P.withScale(4).subsetsAtLeast(1, P.withScale(4).strings()),
                        ss -> map(
                                m -> new FiniteDomainFunction<>(m),
                                P.maps(ss, P.nonEmptyOptionals(P.integers()))
                        )
                )
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(LIMIT, ps)) {
            System.out.println("readListStrict(" + nicePrint(p.a.toString()) + ").apply(" + nicePrint(p.b) + ") = " +
                    readListStrict(p.a).apply(p.b));
        }
    }
}
