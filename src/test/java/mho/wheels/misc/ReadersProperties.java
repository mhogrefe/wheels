package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class ReadersProperties {
    private static boolean USE_RANDOM;
    private static int LIMIT;

    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(0x6af477d9a7e54fcaL);
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

        for (String s : take(LIMIT, P.strings())) {
            findBooleanIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss;
        if (P instanceof ExhaustiveProvider) {
            ss = map(
                    p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.booleans())
            );
        } else {
            ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.booleans()));
        }
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Boolean, Integer>> op = findBooleanIn(s);
            Pair<Boolean, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findBooleanIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readBoolean(during + head(after)).isPresent());
        }
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

        for (String s : take(LIMIT, P.strings())) {
            findOrderingIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss;
        if (P instanceof ExhaustiveProvider) {
            ss = map(
                    p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.orderings())
            );
        } else {
            ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.orderings()));
        }
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Ordering, Integer>> op = findOrderingIn(s);
            Pair<Ordering, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findOrderingIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readOrdering(during + head(after)).isPresent());
        }
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

        for (String s : take(LIMIT, P.strings())) {
            findRoundingModeIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss;
        if (P instanceof ExhaustiveProvider) {
            ss = map(
                    p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a),
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.roundingModes())
            );
        } else {
            ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.roundingModes()));
        }
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<RoundingMode, Integer>> op = findRoundingModeIn(s);
            Pair<RoundingMode, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findRoundingModeIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readRoundingMode(during + head(after)).isPresent());
        }
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

        for (String s : take(LIMIT, P.strings())) {
            findBigIntegerIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.bigIntegers()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<BigInteger, Integer>> op = findBigIntegerIn(s);
            Pair<BigInteger, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findBigIntegerIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readBigInteger(during + head(after)).isPresent());
        }
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

        for (byte b : take(LIMIT, P.bytes())) {
            findByteIn(Byte.toString(b));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.bytes()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Byte, Integer>> op = findByteIn(s);
            Pair<Byte, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findByteIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readByte(during + head(after)).isPresent());
        }
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

        for (short s : take(LIMIT, P.shorts())) {
            findByteIn(Short.toString(s));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.shorts()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Short, Integer>> op = findShortIn(s);
            Pair<Short, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findShortIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readShort(during + head(after)).isPresent());
        }
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

        for (int i : take(LIMIT, P.integers())) {
            findByteIn(Integer.toString(i));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.integers()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Integer, Integer>> op = findIntegerIn(s);
            Pair<Integer, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findIntegerIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readInteger(during + head(after)).isPresent());
        }
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

        for (long l : take(LIMIT, P.bytes())) {
            findByteIn(Long.toString(l));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.longs()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Long, Integer>> op = findLongIn(s);
            Pair<Long, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findLongIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readLong(during + head(after)).isPresent());
        }
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

        for (float f : take(LIMIT, P.floats())) {
            findFloatIn(Float.toString(f));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.floats()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Float, Integer>> op = findFloatIn(s);
            Pair<Float, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findFloatIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readFloat(during + head(after)).isPresent());
        }
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

        for (double d : take(LIMIT, P.doubles())) {
            findDoubleIn(Double.toString(d));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.doubles()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<Double, Integer>> op = findDoubleIn(s);
            Pair<Double, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findDoubleIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readDouble(during + head(after)).isPresent());
        }
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

        for (String s : take(LIMIT, P.strings())) {
            findBigDecimalIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(p -> take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a), P.pairs(ps, P.bigDecimals()));
        for (String s : take(LIMIT, ss)) {
            Optional<Pair<BigDecimal, Integer>> op = findBigDecimalIn(s);
            Pair<BigDecimal, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findBigDecimalIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readBigDecimal(during + head(after)).isPresent());
        }
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

        for (char c : take(LIMIT, P.characters())) {
            findCharacterIn(Character.toString(c));
        }

        for (String s : take(LIMIT, filter(t -> !t.isEmpty(), P.strings()))) {
            Optional<Pair<Character, Integer>> op = findCharacterIn(s);
            Pair<Character, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findCharacterIn(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !readCharacter(during + head(after)).isPresent());
        }
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
