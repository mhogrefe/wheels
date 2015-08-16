package mho.wheels.io;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.take;

@SuppressWarnings("UnusedDeclaration")
public class ReadersDemos {
    private static final boolean USE_RANDOM = false;
    private static final @NotNull String BOOLEAN_CHARS = "aeflrstu";
    private static final @NotNull String ORDERING_CHARS = "EGLQT";
    private static final @NotNull String ROUNDING_MODE_CHARS = "ACDEFGHILNOPRSUVWY_";
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";
    private static final @NotNull String FLOATING_POINT_CHARS = "-.0123456789EINafinty";
    private static final @NotNull String BIG_DECIMAL_CHARS = "+-.0123456789E";
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoGenericRead() {
        initialize();
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            System.out.println("genericRead(" + p.a + ").apply(" + p.b + ") = " + genericRead(p.a).apply(p.b));
        }
    }

    private static void demoGenericFindIn_List_T() {
        initialize();
        //todo use distinct lists instead
        Iterable<Pair<List<Integer>, String>> ps = P.pairs(P.subsets(P.integers()), P.strings(INTEGRAL_CHARS));
        for (Pair<List<Integer>, String> p : take(LIMIT, ps)) {
            String listString = tail(init(p.a.toString()));
            System.out.println("genericFindIn(" + listString + ").apply(" + p.b + ") = " +
                    genericFindIn(p.a).apply(p.b));
        }
    }

    private static void demoReadBoolean() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private static void demoReadBoolean_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private static void demoFindBooleanIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    private static void demoFindBooleanIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("findBooleanIn(" + s + ") = " + findBooleanIn(s));
        }
    }

    private static void demoReadOrdering() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    private static void demoReadOrdering_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("readOrdering(" + s + ") = " + readOrdering(s));
        }
    }

    private static void demoFindOrderingIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }

    private static void demoFindOrderingIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("findOrderingIn(" + s + ") = " + findOrderingIn(s));
        }
    }

    private static void demoReadRoundingMode() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readRoundingMode(" + s + ") = " + readRoundingMode(s));
        }
    }

    private static void demoReadRoundingMode_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("readRoundingMode(" + s + ") = " + readRoundingMode(s));
        }
    }

    private static void demoFindRoundingModeIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findRoundingModeIn(" + s + ") = " + findRoundingModeIn(s));
        }
    }

    private static void demoFindRoundingModeIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("findRoundingModeIn(" + s + ") = " + findRoundingModeIn(s));
        }
    }

    private static void demoReadBigInteger() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigInteger(" + s + ") = " + readBigInteger(s));
        }
    }

    private static void demoReadBigInteger_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readBigInteger(" + s + ") = " + readBigInteger(s));
        }
    }

    private static void demoFindBigIntegerIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBigIntegerIn(" + s + ") = " + findBigIntegerIn(s));
        }
    }

    private static void demoFindBigIntegerIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findBigIntegerIn(" + s + ") = " + findBigIntegerIn(s));
        }
    }

    private static void demoReadByte() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readByte(" + s + ") = " + readByte(s));
        }
    }

    private static void demoReadByte_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readByte(" + s + ") = " + readByte(s));
        }
    }

    private static void demoFindByteIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findByteIn(" + s + ") = " + findByteIn(s));
        }
    }

    private static void demoFindByteIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findByteIn(" + s + ") = " + findByteIn(s));
        }
    }

    private static void demoReadShort() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readShort(" + s + ") = " + readShort(s));
        }
    }

    private static void demoReadShort_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readShort(" + s + ") = " + readShort(s));
        }
    }

    private static void demoFindShortIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findShortIn(" + s + ") = " + findShortIn(s));
        }
    }

    private static void demoFindShortIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findShortIn(" + s + ") = " + findShortIn(s));
        }
    }

    private static void demoReadInteger() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readInteger(" + s + ") = " + readByte(s));
        }
    }

    private static void demoReadInteger_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readInteger(" + s + ") = " + readInteger(s));
        }
    }

    private static void demoFindIntegerIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findIntegerIn(" + s + ") = " + findIntegerIn(s));
        }
    }

    private static void demoFindIntegerIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findIntegerIn(" + s + ") = " + findIntegerIn(s));
        }
    }

    private static void demoReadLong() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readLong(" + s + ") = " + readLong(s));
        }
    }

    private static void demoReadLong_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readLong(" + s + ") = " + readLong(s));
        }
    }

    private static void demoFindLongIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findLongIn(" + s + ") = " + findLongIn(s));
        }
    }

    private static void demoFindLongIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("findLongIn(" + s + ") = " + findLongIn(s));
        }
    }

    private static void demoReadFloat() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readFloat(" + s + ") = " + readFloat(s));
        }
    }

    private static void demoReadFloat_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readFloat(" + s + ") = " + readFloat(s));
        }
    }

    private static void demoFindFloatIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findFloatIn(" + s + ") = " + findFloatIn(s));
        }
    }

    private static void demoFindFloatIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("findFloatIn(" + s + ") = " + findFloatIn(s));
        }
    }

    private static void demoReadDouble() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readDouble(" + s + ") = " + readDouble(s));
        }
    }

    private static void demoReadDouble_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readDouble(" + s + ") = " + readDouble(s));
        }
    }

    private static void demoFindDoubleIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findDoubleIn(" + s + ") = " + findDoubleIn(s));
        }
    }

    private static void demoFindDoubleIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("findDoubleIn(" + s + ") = " + findDoubleIn(s));
        }
    }

    private static void demoReadBigDecimal() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigDecimal(" + s + ") = " + readBigDecimal(s));
        }
    }

    private static void demoReadBigDecimal_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("readBigDecimal(" + s + ") = " + readBigDecimal(s));
        }
    }

    private static void demoFindBigDecimalIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findBigDecimalIn(" + s + ") = " + findBigDecimalIn(s));
        }
    }

    private static void demoFindBigDecimalIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("findBigDecimalIn(" + s + ") = " + findBigDecimalIn(s));
        }
    }

    private static void demoReadCharacter() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readCharacter(" + s + ") = " + readCharacter(s));
        }
    }

    private static void demoFindCharacterIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findCharacterIn(" + s + ") = " + findCharacterIn(s));
        }
    }

    private static void demoReadString() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readString(" + s + ") = " + readString(s));
        }
    }
}
