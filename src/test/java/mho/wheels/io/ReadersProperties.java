package mho.wheels.io;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public strictfp class ReadersProperties extends TestProperties {
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
        for (String s : take(LIMIT, P.strings())) {
            readBooleanStrict(s);
        }

        for (boolean b : take(LIMIT, P.booleans())) {
            Optional<Boolean> ob = readBooleanStrict(Boolean.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private void propertiesReadOrderingStrict() {
        initialize("readOrderingStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readOrderingStrict(s);
        }

        for (Ordering o : take(LIMIT, P.orderings())) {
            Optional<Ordering> oo = readOrderingStrict(o.toString());
            assertEquals(o, oo.get(), o);
        }
    }

    private void propertiesReadRoundingModeStrict() {
        initialize("readRoundingModeStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readRoundingModeStrict(s);
        }

        for (RoundingMode rm : take(LIMIT, P.roundingModes())) {
            Optional<RoundingMode> orm = readRoundingModeStrict(rm.toString());
            assertEquals(rm, orm.get(), rm);
        }
    }

    private void propertiesReadBigIntegerStrict() {
        initialize("readBigIntegerStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigIntegerStrict(s);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Optional<BigInteger> oi = readBigIntegerStrict(i.toString());
            assertEquals(i, oi.get(), i);
        }
    }

    private void propertiesReadByteStrict() {
        initialize("readByteStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readByteStrict(s);
        }

        for (byte b : take(LIMIT, P.bytes())) {
            Optional<Byte> ob = readByteStrict(Byte.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private void propertiesReadShortStrict() {
        initialize("readShortStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readShortStrict(s);
        }

        for (short s : take(LIMIT, P.shorts())) {
            Optional<Short> os = readShortStrict(Short.toString(s));
            assertEquals(s, os.get(), s);
        }
    }

    private void propertiesReadIntegerStrict() {
        initialize("readIntegerStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readIntegerStrict(s);
        }

        for (int i : take(LIMIT, P.integers())) {
            Optional<Integer> oi = readIntegerStrict(Integer.toString(i));
            assertEquals(i, oi.get(), i);
        }
    }

    private void propertiesReadLongStrict() {
        initialize("readLongStrict(String)");
        for (String s : take(LIMIT, P.strings())) {
            readLongStrict(s);
        }

        for (long l : take(LIMIT, P.longs())) {
            Optional<Long> ol = readLongStrict(Long.toString(l));
            assertEquals(l, ol.get(), l);
        }
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
