package mho.wheels.io;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;

import static mho.wheels.iterables.IterableUtils.fromString;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.io.Readers.*;

@SuppressWarnings("UnusedDeclaration")
public class ReadersDemos {
    private static final boolean USE_RANDOM = false;
    private static final String BOOLEAN_CHARS = "aeflrstu";
    private static final String ORDERING_CHARS = "EGLQT";
    private static final String ROUNDING_MODE_CHARS = "ACDEFGHILNOPRSUVWY_";
    private static final String INTEGRAL_CHARS = "-0123456789";
    private static final String FLOATING_POINT_CHARS = "-.0123456789EINafinty";
    private static final String BIG_DECIMAL_CHARS = "+-.0123456789E";
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

    private static void demoReadBoolean() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("readBoolean(" + s + ") = " + readBoolean(s));
        }
    }

    private static void demoReadBoolean_targeted() {
        initialize();
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BOOLEAN_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BOOLEAN_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BOOLEAN_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BOOLEAN_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ORDERING_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ORDERING_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ORDERING_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ORDERING_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ROUNDING_MODE_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ROUNDING_MODE_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(ROUNDING_MODE_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(ROUNDING_MODE_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(INTEGRAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(INTEGRAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(FLOATING_POINT_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(FLOATING_POINT_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(FLOATING_POINT_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(FLOATING_POINT_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(FLOATING_POINT_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(FLOATING_POINT_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(FLOATING_POINT_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(FLOATING_POINT_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BIG_DECIMAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BIG_DECIMAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
        Iterable<Character> cs;
        if (P instanceof ExhaustiveProvider) {
            cs = fromString(BIG_DECIMAL_CHARS);
        } else {
            cs = ((RandomProvider) P).uniformSample(BIG_DECIMAL_CHARS);
        }
        for (String s : take(LIMIT, P.strings(cs))) {
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
