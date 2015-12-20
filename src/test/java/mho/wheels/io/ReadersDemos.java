package mho.wheels.io;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;

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

    private void demoGenericRead() {
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            System.out.println("genericRead(" + p.a + ").apply(" + p.b + ") = " + genericRead(p.a).apply(p.b));
        }
    }

    private void demoGenericFindIn_List_T() {
        Iterable<Pair<List<Integer>, String>> ps = P.pairs(P.distinctLists(P.integers()), P.strings(INTEGRAL_CHARS));
        for (Pair<List<Integer>, String> p : take(LIMIT, ps)) {
            String listString = tail(init(p.a.toString()));
            System.out.println("genericFindIn(" + listString + ").apply(" + p.b + ") = " +
                    genericFindIn(p.a).apply(p.b));
        }
    }

    private void demoGenericFindIn_Function_String_Optional_T() {
        Iterable<Pair<Function<String, Optional<Integer>>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<String, Optional<Integer>>(p.b), p.a),
                P.dependentPairsInfinite(
                        P.withScale(4).stringsAtLeast(1, INTEGRAL_CHARS),
                        s -> P.maps(
                                toList(filter(t -> !t.isEmpty(), ExhaustiveProvider.INSTANCE.substrings(s))),
                                P.optionals(P.integersGeometric())
                        )
                )
        );
        for (Pair<Function<String, Optional<Integer>>, String> p : take(MEDIUM_LIMIT, ps)) {
            String listString = tail(init(p.a.toString()));
            System.out.println("genericFindIn(" + listString + ").apply(" + p.b + ") = " +
                    genericFindIn(p.a).apply(p.b));
        }
    }

    private void demoReadBoolean() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private void demoReadBoolean_targeted() {
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private void demoFindBooleanIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    private void demoFindBooleanIn_targeted() {
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    private void demoReadOrdering() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    private void demoReadOrdering_targeted() {
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    private void demoFindOrderingIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }

    private void demoFindOrderingIn_targeted() {
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }

    private void demoReadRoundingMode() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readRoundingMode(" + s + ") = " + readRoundingMode(s));
        }
    }

    private void demoReadRoundingMode_targeted() {
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("readRoundingMode(" + s + ") = " + readRoundingMode(s));
        }
    }

    private void demoFindRoundingModeIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findRoundingModeIn(" + s + ") = " + findRoundingModeIn(s));
        }
    }

    private void demoFindRoundingModeIn_targeted() {
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("findRoundingModeIn(" + s + ") = " + findRoundingModeIn(s));
        }
    }

    private void demoReadBigInteger() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigInteger(" + s + ") = " + readBigInteger(s));
        }
    }

    private void demoReadBigInteger_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readBigInteger(" + s + ") = " + readBigInteger(s));
        }
    }

    private void demoFindBigIntegerIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBigIntegerIn(" + s + ") = " + findBigIntegerIn(s));
        }
    }

    private void demoFindBigIntegerIn_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findBigIntegerIn(" + s + ") = " + findBigIntegerIn(s));
        }
    }

    private void demoReadByte() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readByte(" + s + ") = " + readByte(s));
        }
    }

    private void demoReadByte_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readByte(" + s + ") = " + readByte(s));
        }
    }

    private void demoFindByteIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findByteIn(" + s + ") = " + findByteIn(s));
        }
    }

    private void demoFindByteIn_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findByteIn(" + s + ") = " + findByteIn(s));
        }
    }

    private void demoReadShort() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readShort(" + s + ") = " + readShort(s));
        }
    }

    private void demoReadShort_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readShort(" + s + ") = " + readShort(s));
        }
    }

    private void demoFindShortIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findShortIn(" + s + ") = " + findShortIn(s));
        }
    }

    private void demoFindShortIn_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findShortIn(" + s + ") = " + findShortIn(s));
        }
    }

    private void demoReadInteger() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readInteger(" + s + ") = " + readByte(s));
        }
    }

    private void demoReadInteger_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readInteger(" + s + ") = " + readInteger(s));
        }
    }

    private void demoFindIntegerIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findIntegerIn(" + s + ") = " + findIntegerIn(s));
        }
    }

    private void demoFindIntegerIn_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findIntegerIn(" + s + ") = " + findIntegerIn(s));
        }
    }

    private void demoReadLong() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readLong(" + s + ") = " + readLong(s));
        }
    }

    private void demoReadLong_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readLong(" + s + ") = " + readLong(s));
        }
    }

    private void demoFindLongIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findLongIn(" + s + ") = " + findLongIn(s));
        }
    }

    private void demoFindLongIn_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findLongIn(" + s + ") = " + findLongIn(s));
        }
    }

    private void demoReadFloat() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readFloat(" + s + ") = " + readFloat(s));
        }
    }

    private void demoReadFloat_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readFloat(" + s + ") = " + readFloat(s));
        }
    }

    private void demoFindFloatIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findFloatIn(" + s + ") = " + findFloatIn(s));
        }
    }

    private void demoFindFloatIn_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("findFloatIn(" + s + ") = " + findFloatIn(s));
        }
    }

    private void demoReadDouble() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readDouble(" + s + ") = " + readDouble(s));
        }
    }

    private void demoReadDouble_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readDouble(" + s + ") = " + readDouble(s));
        }
    }

    private void demoFindDoubleIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findDoubleIn(" + s + ") = " + findDoubleIn(s));
        }
    }

    private void demoFindDoubleIn_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("findDoubleIn(" + s + ") = " + findDoubleIn(s));
        }
    }

    private void demoReadBigDecimal() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigDecimal(" + s + ") = " + readBigDecimal(s));
        }
    }

    private void demoReadBigDecimal_targeted() {
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("readBigDecimal(" + s + ") = " + readBigDecimal(s));
        }
    }

    private void demoFindBigDecimalIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBigDecimalIn(" + s + ") = " + findBigDecimalIn(s));
        }
    }

    private void demoFindBigDecimalIn_targeted() {
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("findBigDecimalIn(" + s + ") = " + findBigDecimalIn(s));
        }
    }

    private void demoReadCharacter() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readCharacter(" + s + ") = " + readCharacter(s));
        }
    }

    private void demoFindCharacterIn() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findCharacterIn(" + s + ") = " + findCharacterIn(s));
        }
    }

    private void demoReadString() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readString(" + s + ") = " + readString(s));
        }
    }
}
