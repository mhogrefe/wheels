package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public strictfp class ReadersProperties {
    private static boolean USE_RANDOM;
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

    @Test
    public void testAllProperties() {
        System.out.println("Readers properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
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

    private static void propertiesReadBoolean() {
        initialize();
        System.out.println("\t\ttesting readBoolean(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readBoolean(s);
        }

        for (boolean b : take(LIMIT, P.booleans())) {
            Optional<Boolean> ob = readBoolean(Boolean.toString(b));
            assertEquals(Boolean.toString(b), ob.get(), b);
        }
    }

    private static void propertiesFindBooleanIn() {
        initialize();
        System.out.println("\t\ttesting findBooleanIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.booleans(), Readers::readBoolean, Readers::findBooleanIn);
    }

    private static void propertiesReadOrdering() {
        initialize();
        System.out.println("\t\ttesting readOrdering(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readOrdering(s);
        }

        for (Ordering o : take(LIMIT, P.orderings())) {
            Optional<Ordering> oo = readOrdering(o.toString());
            assertEquals(o.toString(), oo.get(), o);
        }
    }

    private static void propertiesFindOrderingIn() {
        initialize();
        System.out.println("\t\ttesting findOrderingIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.orderings(), Readers::readOrdering, Readers::findOrderingIn);
    }

    private static void propertiesReadRoundingMode() {
        initialize();
        System.out.println("\t\ttesting readRoundingMode(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readRoundingMode(s);
        }

        for (RoundingMode rm : take(LIMIT, P.roundingModes())) {
            Optional<RoundingMode> orm = readRoundingMode(rm.toString());
            assertEquals(rm.toString(), orm.get(), rm);
        }
    }

    private static void propertiesFindRoundingModeIn() {
        initialize();
        System.out.println("\t\ttesting findRoundingModeIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.roundingModes(), Readers::readRoundingMode, Readers::findRoundingModeIn);
    }

    private static void propertiesReadBigInteger() {
        initialize();
        System.out.println("\t\ttesting readBigInteger(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readBigInteger(s);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Optional<BigInteger> oi = readBigInteger(i.toString());
            assertEquals(i.toString(), oi.get(), i);
        }
    }

    private static void propertiesFindBigIntegerIn() {
        initialize();
        System.out.println("\t\ttesting findBigIntegerIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.bigIntegers(), Readers::readBigInteger, Readers::findBigIntegerIn);
    }

    private static void propertiesReadByte() {
        initialize();
        System.out.println("\t\ttesting readByte(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readByte(s);
        }

        for (byte b : take(LIMIT, P.bytes())) {
            Optional<Byte> ob = readByte(Byte.toString(b));
            assertEquals(Byte.toString(b), ob.get(), Byte.valueOf(b));
        }
    }

    private static void propertiesFindByteIn() {
        initialize();
        System.out.println("\t\ttesting findByteIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.bytes(), Readers::readByte, Readers::findByteIn);
    }

    private static void propertiesReadShort() {
        initialize();
        System.out.println("\t\ttesting readShort(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readShort(s);
        }

        for (short s : take(LIMIT, P.shorts())) {
            Optional<Short> os = readShort(Short.toString(s));
            assertEquals(Short.toString(s), os.get(), Short.valueOf(s));
        }
    }

    private static void propertiesFindShortIn() {
        initialize();
        System.out.println("\t\ttesting findShortIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.shorts(), Readers::readShort, Readers::findShortIn);
    }

    private static void propertiesReadInteger() {
        initialize();
        System.out.println("\t\ttesting readInteger(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readInteger(s);
        }

        for (int i : take(LIMIT, P.integers())) {
            Optional<Integer> oi = readInteger(Integer.toString(i));
            assertEquals(Integer.toString(i), oi.get().intValue(), i);
        }
    }

    private static void propertiesFindIntegerIn() {
        initialize();
        System.out.println("\t\ttesting findIntegerIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.integers(), Readers::readInteger, Readers::findIntegerIn);
    }

    private static void propertiesReadLong() {
        initialize();
        System.out.println("\t\ttesting readLong(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readLong(s);
        }

        for (long l : take(LIMIT, P.longs())) {
            Optional<Long> ol = readLong(Long.toString(l));
            assertEquals(Long.toString(l), ol.get(), Long.valueOf(l));
        }
    }

    private static void propertiesFindLongIn() {
        initialize();
        System.out.println("\t\ttesting findLongIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.longs(), Readers::readLong, Readers::findLongIn);
    }

    private static void propertiesReadFloat() {
        initialize();
        System.out.println("\t\ttesting readFloat(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readFloat(s);
        }

        for (float f : take(LIMIT, P.floats())) {
            Optional<Float> of = readFloat(Float.toString(f));
            assertEquals(Float.toString(f), of.get(), Float.valueOf(f));
        }
    }

    private static void propertiesFindFloatIn() {
        initialize();
        System.out.println("\t\ttesting findFloatIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.floats(), Readers::readFloat, Readers::findFloatIn);
    }

    private static void propertiesReadDouble() {
        initialize();
        System.out.println("\t\ttesting readDouble(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readDouble(s);
        }

        for (double d : take(LIMIT, P.doubles())) {
            Optional<Double> od = readDouble(Double.toString(d));
            assertEquals(Double.toString(d), od.get(), Double.valueOf(d));
        }
    }

    private static void propertiesFindDoubleIn() {
        initialize();
        System.out.println("\t\ttesting findDoubleIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.doubles(), Readers::readDouble, Readers::findDoubleIn);
    }

    private static void propertiesReadBigDecimal() {
        initialize();
        System.out.println("\t\ttesting readBigDecimal(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readBigDecimal(s);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Optional<BigDecimal> obd = readBigDecimal(bd.toString());
            assertEquals(bd.toString(), obd.get(), bd);
        }
    }

    private static void propertiesFindBigDecimalIn() {
        initialize();
        System.out.println("\t\ttesting findBigDecimalIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.bigDecimals(), Readers::readBigDecimal, Readers::findBigDecimalIn);
    }

    private static void propertiesReadCharacter() {
        initialize();
        System.out.println("\t\ttesting readCharacter(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            readCharacter(s);
        }

        for (char c : take(LIMIT, P.characters())) {
            Optional<Character> oc = readCharacter(Character.toString(c));
            assertEquals(Character.toString(c), oc.get(), Character.valueOf(c));
        }
    }

    private static void propertiesFindCharacterIn() {
        initialize();
        System.out.println("\t\ttesting findCharacterIn(String) properties...");

        propertiesFindInHelper(LIMIT, P, P.characters(), Readers::readCharacter, Readers::findCharacterIn);
    }

    private static void propertiesReadString() {
        initialize();
        System.out.println("\t\ttesting readString(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            Optional<String> os = readString(s);
            assertEquals(s, os.get(), s);
        }
    }
}
