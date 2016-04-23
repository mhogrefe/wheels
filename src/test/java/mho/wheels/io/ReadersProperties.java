package mho.wheels.io;

import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public strictfp class ReadersProperties extends TestProperties {
    private static final @NotNull String BOOLEAN_CHARS = "aeflrstu";
    private static final @NotNull String ORDERING_CHARS = "EGLQT";
    private static final @NotNull String ROUNDING_MODE_CHARS = "ACDEFGHILNOPRSUVWY_";
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";

    public ReadersProperties() {
        super("Readers");
    }

    @Override
    protected void testBothModes() {
        propertiesGenericReadStrict();
        propertiesReadBooleanStrict();
        propertiesReadOrderingStrict();
        propertiesReadRoundingModeStrict();
        propertiesReadBigIntegerStrict();
        propertiesReadByteStrict();
        propertiesReadShortStrict();
        propertiesReadIntegerStrict();
        propertiesReadLongStrict();
        propertiesReadFloatStrict();
        propertiesReadDoubleStrict();
        propertiesReadBigDecimalStrict();
        propertiesReadCharacter();
        propertiesReadString();
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

    private void propertiesReadOrderingStrict() {
        initialize("readOrderingStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                ORDERING_CHARS,
                P.orderings(),
                Readers::readOrderingStrict,
                o -> {},
                false,
                true
        );
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
                rm -> {},
                false,
                true
        );
    }

    private void propertiesReadByteStrict() {
        initialize("readByteStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.bytes(), Readers::readByteStrict, rm -> {}, false, true);
    }

    private void propertiesReadShortStrict() {
        initialize("readShortStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.shorts(), Readers::readShortStrict, rm -> {}, false, true);
    }

    private void propertiesReadIntegerStrict() {
        initialize("readIntegerStrict(String)");
        propertiesReadHelper(
                LIMIT,
                P,
                INTEGRAL_CHARS,
                P.integers(),
                Readers::readIntegerStrict,
                rm -> {},
                false,
                true
        );
    }

    private void propertiesReadLongStrict() {
        initialize("readLongStrict(String)");
        propertiesReadHelper(LIMIT, P, INTEGRAL_CHARS, P.longs(), Readers::readLongStrict, rm -> {}, false, true);
    }

    private void propertiesReadFloatStrict() {
        initialize("readFloatStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readFloatStrict(s);
        }

        for (float f : take(LIMIT, P.floats())) {
            Optional<Float> of = readFloatStrict(Float.toString(f));
            assertEquals(f, of.get(), f);
        }
    }

    private void propertiesReadDoubleStrict() {
        initialize("readDoubleStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readDoubleStrict(s);
        }

        for (double d : take(LIMIT, P.doubles())) {
            Optional<Double> od = readDoubleStrict(Double.toString(d));
            assertEquals(d, od.get(), d);
        }
    }

    private void propertiesReadBigDecimalStrict() {
        initialize("readBigDecimalStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigDecimalStrict(s);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Optional<BigDecimal> obd = readBigDecimalStrict(bd.toString());
            assertEquals(bd, obd.get(), bd);
        }
    }

    private void propertiesReadCharacter() {
        initialize("readCharacter(String)");
        for (String s : take(LIMIT, P.strings())) {
            readCharacter(s);
        }

        for (char c : take(LIMIT, P.characters())) {
            Optional<Character> oc = readCharacter(Character.toString(c));
            assertEquals(c, oc.get(), c);
        }
    }

    private void propertiesReadString() {
        initialize("readString(String)");
        for (String s : take(LIMIT, P.strings())) {
            Optional<String> os = readString(s);
            assertEquals(s, os.get(), s);
        }
    }
}
