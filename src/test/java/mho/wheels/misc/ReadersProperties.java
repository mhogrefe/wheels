package mho.wheels.misc;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.junit.Test;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersProperties {
    private static boolean USE_RANDOM;
    private static int LIMIT;

    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(new Random(0x6af477d9a7e54fcaL));
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
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.booleans())
            );
        } else {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    P.pairs(ps, P.booleans())
            );
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
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.orderings())
            );
        } else {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    P.pairs(ps, P.orderings())
            );
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
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(ps, P.roundingModes())
            );
        } else {
            ss = map(
                    p -> {
                        assert p.a != null;
                        assert p.a.a != null;
                        assert p.a.b != null;
                        return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                    },
                    P.pairs(ps, P.roundingModes())
            );
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
            Optional<BigInteger> orm = readBigInteger(i.toString());
            assertEquals(i.toString(), orm.get(), i);
        }
    }

    private static void propertiesFindBigIntegerIn() {
        initialize();
        System.out.println("\t\ttesting findBigIntegerIn(String) properties...");

        for (String s : take(LIMIT, P.strings())) {
            findBigIntegerIn(s);
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(
                p -> {
                    assert p.a != null;
                    assert p.a.a != null;
                    assert p.a.b != null;
                    return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                },
                P.pairs(ps, P.bigIntegers())
        );
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
        Iterable<String> ss = map(
                p -> {
                    assert p.a != null;
                    assert p.a.a != null;
                    assert p.a.b != null;
                    return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                },
                P.pairs(ps, P.bytes())
        );
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
        Iterable<String> ss = map(
                p -> {
                    assert p.a != null;
                    assert p.a.a != null;
                    assert p.a.b != null;
                    return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                },
                P.pairs(ps, P.shorts())
        );
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
            assertEquals(Integer.toString(i), oi.get(), Integer.valueOf(i));
        }
    }

    private static void propertiesFindIntegerIn() {
        initialize();
        System.out.println("\t\ttesting findIntegerIn(String) properties...");

        for (int i : take(LIMIT, P.integers())) {
            findByteIn(Integer.toString(i));
        }

        Iterable<Pair<String, Integer>> ps = P.dependentPairsLogarithmic(P.strings(), s -> range(0, s.length()));
        Iterable<String> ss = map(
                p -> {
                    assert p.a != null;
                    assert p.a.a != null;
                    assert p.a.b != null;
                    return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                },
                P.pairs(ps, P.integers())
        );
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
        Iterable<String> ss = map(
                p -> {
                    assert p.a != null;
                    assert p.a.a != null;
                    assert p.a.b != null;
                    return take(p.a.b, p.a.a) + p.b + drop(p.a.b, p.a.a);
                },
                P.pairs(ps, P.longs())
        );
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
}
