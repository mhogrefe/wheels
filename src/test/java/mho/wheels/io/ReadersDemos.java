package mho.wheels.io;

import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;

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
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            System.out.println("genericReadStrict(" + p.a + ").apply(" + p.b + ") = " +
                    genericReadStrict(p.a).apply(p.b));
        }
    }

    private void demoReadBooleanStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBooleanStrict(" + s + ") = " + readBooleanStrict(s));
        }
    }

    private void demoReadBooleanStrict_targeted() {
        for (String s : take(LIMIT, P.strings(BOOLEAN_CHARS))) {
            System.out.println("readBooleanStrict(" + s + ") = " + readBooleanStrict(s));
        }
    }

    private void demoReadOrderingStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readOrderingStrict(" + s + ") = " + readOrderingStrict(s));
        }
    }

    private void demoReadOrderingStrict_targeted() {
        for (String s : take(LIMIT, P.strings(ORDERING_CHARS))) {
            System.out.println("readOrderingStrict(" + s + ") = " + readOrderingStrict(s));
        }
    }

    private void demoReadRoundingModeStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readRoundingModeStrict(" + s + ") = " + readRoundingModeStrict(s));
        }
    }

    private void demoReadRoundingModeStrict_targeted() {
        for (String s : take(LIMIT, P.strings(ROUNDING_MODE_CHARS))) {
            System.out.println("readRoundingModeStrict(" + s + ") = " + readRoundingModeStrict(s));
        }
    }

    private void demoReadBigIntegerStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigIntegerStrict(" + s + ") = " + readBigIntegerStrict(s));
        }
    }

    private void demoReadBigIntegerStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readBigIntegerStrict(" + s + ") = " + readBigIntegerStrict(s));
        }
    }

    private void demoReadByteStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readByteStrict(" + s + ") = " + readByteStrict(s));
        }
    }

    private void demoReadByteStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readByteStrict(" + s + ") = " + readByteStrict(s));
        }
    }

    private void demoReadShortStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readShortStrict(" + s + ") = " + readShortStrict(s));
        }
    }

    private void demoReadShortStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readShortStrict(" + s + ") = " + readShortStrict(s));
        }
    }

    private void demoReadIntegerStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readIntegerStrict(" + s + ") = " + readByteStrict(s));
        }
    }

    private void demoReadIntegerStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readIntegerStrict(" + s + ") = " + readIntegerStrict(s));
        }
    }

    private void demoReadLongStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readLongStrict(" + s + ") = " + readLongStrict(s));
        }
    }

    private void demoReadLongStrict_targeted() {
        for (String s : take(LIMIT, P.strings(INTEGRAL_CHARS))) {
            System.out.println("readLongStrict(" + s + ") = " + readLongStrict(s));
        }
    }

    private void demoReadFloatStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readFloatStrict(" + s + ") = " + readFloatStrict(s));
        }
    }

    private void demoReadFloatStrict_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readFloatStrict(" + s + ") = " + readFloatStrict(s));
        }
    }

    private void demoReadDoubleStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readDoubleStrict(" + s + ") = " + readDoubleStrict(s));
        }
    }

    private void demoReadDoubleStrict_targeted() {
        for (String s : take(LIMIT, P.strings(FLOATING_POINT_CHARS))) {
            System.out.println("readDoubleStrict(" + s + ") = " + readDoubleStrict(s));
        }
    }

    private void demoReadBigDecimalStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBigDecimalStrict(" + s + ") = " + readBigDecimalStrict(s));
        }
    }

    private void demoReadBigDecimalStrict_targeted() {
        for (String s : take(LIMIT, P.strings(BIG_DECIMAL_CHARS))) {
            System.out.println("readBigDecimalStrict(" + s + ") = " + readBigDecimalStrict(s));
        }
    }

    private void demoReadCharacter() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readCharacter(" + s + ") = " + readCharacter(s));
        }
    }

    private void demoReadString() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readString(" + s + ") = " + readString(s));
        }
    }
}
