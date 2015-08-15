package mho.wheels.io;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.map;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;

public strictfp class ReadersProperties {
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize(String name) {
        P.reset();
        System.out.println("\t\ttesting " + name + " properties...");
    }

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        System.out.println("Readers properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesGenericRead();
            propertiesReadBoolean();
            propertiesFindBooleanIn();
            propertiesReadOrdering();
            propertiesFindOrderingIn();
            propertiesReadRoundingMode();
            propertiesFindRoundingModeIn();
            propertiesReadBigInteger();
            propertiesFindBigIntegerIn();
            propertiesReadByte();
            propertiesFindByteIn();
            propertiesReadShort();
            propertiesFindShortIn();
            propertiesReadInteger();
            propertiesFindIntegerIn();
            propertiesReadLong();
            propertiesFindLongIn();
            propertiesReadFloat();
            propertiesFindFloatIn();
            propertiesReadDouble();
            propertiesFindDoubleIn();
            propertiesReadBigDecimal();
            propertiesFindBigDecimalIn();
            propertiesReadCharacter();
            propertiesFindCharacterIn();
            propertiesReadString();
        }
        System.out.println("Done");
    }

    private static void propertiesGenericRead() {
        initialize("genericRead(Function<String, T>)");
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            genericRead(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            String s = Integer.toString(i);
            Function<String, Integer> f = new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(s, i)));
            assertEquals(i, genericRead(f).apply(s).get(), i);
        }
    }

    private static void propertiesReadBoolean() {
        initialize("readBoolean(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBoolean(s);
        }

        for (boolean b : take(LIMIT, P.booleans())) {
            Optional<Boolean> ob = readBoolean(Boolean.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private static void propertiesFindBooleanIn() {
        initialize("findBooleanIn(String)");
        propertiesFindInHelper(LIMIT, P, P.booleans(), Readers::readBoolean, Readers::findBooleanIn, b -> {});
    }

    private static void propertiesReadOrdering() {
        initialize("readOrdering(String)");
        for (String s : take(LIMIT, P.strings())) {
            readOrdering(s);
        }

        for (Ordering o : take(LIMIT, P.orderings())) {
            Optional<Ordering> oo = readOrdering(o.toString());
            assertEquals(o, oo.get(), o);
        }
    }

    private static void propertiesFindOrderingIn() {
        initialize("findOrderingIn(String)");
        propertiesFindInHelper(LIMIT, P, P.orderings(), Readers::readOrdering, Readers::findOrderingIn, o -> {});
    }

    private static void propertiesReadRoundingMode() {
        initialize("readRoundingMode(String)");
        for (String s : take(LIMIT, P.strings())) {
            readRoundingMode(s);
        }

        for (RoundingMode rm : take(LIMIT, P.roundingModes())) {
            Optional<RoundingMode> orm = readRoundingMode(rm.toString());
            assertEquals(rm, orm.get(), rm);
        }
    }

    private static void propertiesFindRoundingModeIn() {
        initialize("findRoundingModeIn(String)");
        propertiesFindInHelper(
                LIMIT,
                P,
                P.roundingModes(),
                Readers::readRoundingMode,
                Readers::findRoundingModeIn,
                rm -> {}
        );
    }

    private static void propertiesReadBigInteger() {
        initialize("readBigInteger(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigInteger(s);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Optional<BigInteger> oi = readBigInteger(i.toString());
            assertEquals(i, oi.get(), i);
        }
    }

    private static void propertiesFindBigIntegerIn() {
        initialize("findBigIntegerIn(String)");
        propertiesFindInHelper(LIMIT, P, P.bigIntegers(), Readers::readBigInteger, Readers::findBigIntegerIn, i -> {});
    }

    private static void propertiesReadByte() {
        initialize("readByte(String)");
        for (String s : take(LIMIT, P.strings())) {
            readByte(s);
        }

        for (byte b : take(LIMIT, P.bytes())) {
            Optional<Byte> ob = readByte(Byte.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private static void propertiesFindByteIn() {
        initialize("findByteIn(String)");
        propertiesFindInHelper(LIMIT, P, P.bytes(), Readers::readByte, Readers::findByteIn, b -> {});
    }

    private static void propertiesReadShort() {
        initialize("readShort(String)");
        for (String s : take(LIMIT, P.strings())) {
            readShort(s);
        }

        for (short s : take(LIMIT, P.shorts())) {
            Optional<Short> os = readShort(Short.toString(s));
            assertEquals(s, os.get(), s);
        }
    }

    private static void propertiesFindShortIn() {
        initialize("findShortIn(String)");
        propertiesFindInHelper(LIMIT, P, P.shorts(), Readers::readShort, Readers::findShortIn, s -> {});
    }

    private static void propertiesReadInteger() {
        initialize("readInteger(String)");
        for (String s : take(LIMIT, P.strings())) {
            readInteger(s);
        }

        for (int i : take(LIMIT, P.integers())) {
            Optional<Integer> oi = readInteger(Integer.toString(i));
            assertEquals(i, oi.get().intValue(), i);
        }
    }

    private static void propertiesFindIntegerIn() {
        initialize("findIntegerIn(String)");
        propertiesFindInHelper(LIMIT, P, P.integers(), Readers::readInteger, Readers::findIntegerIn, i -> {});
    }

    private static void propertiesReadLong() {
        initialize("readLong(String)");
        for (String s : take(LIMIT, P.strings())) {
            readLong(s);
        }

        for (long l : take(LIMIT, P.longs())) {
            Optional<Long> ol = readLong(Long.toString(l));
            assertEquals(l, ol.get(), l);
        }
    }

    private static void propertiesFindLongIn() {
        initialize("findLongIn(String)");
        propertiesFindInHelper(LIMIT, P, P.longs(), Readers::readLong, Readers::findLongIn, l -> {});
    }

    private static void propertiesReadFloat() {
        initialize("readFloat(String)");
        for (String s : take(LIMIT, P.strings())) {
            readFloat(s);
        }

        for (float f : take(LIMIT, P.floats())) {
            Optional<Float> of = readFloat(Float.toString(f));
            assertEquals(f, of.get(), f);
        }
    }

    private static void propertiesFindFloatIn() {
        initialize("findFloatIn(String)");
        propertiesFindInHelper(LIMIT, P, P.floats(), Readers::readFloat, Readers::findFloatIn, f -> {});
    }

    private static void propertiesReadDouble() {
        initialize("readDouble(String)");
        for (String s : take(LIMIT, P.strings())) {
            readDouble(s);
        }

        for (double d : take(LIMIT, P.doubles())) {
            Optional<Double> od = readDouble(Double.toString(d));
            assertEquals(d, od.get(), d);
        }
    }

    private static void propertiesFindDoubleIn() {
        initialize("findDoubleIn(String)");
        propertiesFindInHelper(LIMIT, P, P.doubles(), Readers::readDouble, Readers::findDoubleIn, d -> {});
    }

    private static void propertiesReadBigDecimal() {
        initialize("readBigDecimal(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigDecimal(s);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Optional<BigDecimal> obd = readBigDecimal(bd.toString());
            assertEquals(bd, obd.get(), bd);
        }
    }

    private static void propertiesFindBigDecimalIn() {
        initialize("findBigDecimalIn(String)");
        propertiesFindInHelper(
                LIMIT,
                P,
                P.bigDecimals(),
                Readers::readBigDecimal,
                Readers::findBigDecimalIn,
                bd -> {}
        );
    }

    private static void propertiesReadCharacter() {
        initialize("readCharacter(String)");
        for (String s : take(LIMIT, P.strings())) {
            readCharacter(s);
        }

        for (char c : take(LIMIT, P.characters())) {
            Optional<Character> oc = readCharacter(Character.toString(c));
            assertEquals(c, oc.get(), c);
        }
    }

    private static void propertiesFindCharacterIn() {
        initialize("findCharacterIn(String)");
        propertiesFindInHelper(LIMIT, P, P.characters(), Readers::readCharacter, Readers::findCharacterIn, c -> {});
    }

    private static void propertiesReadString() {
        initialize("readString(String)");
        for (String s : take(LIMIT, P.strings())) {
            Optional<String> os = readString(s);
            assertEquals(s, os.get(), s);
        }
    }
}
