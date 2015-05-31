package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public strictfp class IterableUtilsProperties {
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
        System.out.println("IterableUtils properties");
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("\ttesting " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;
            propertiesUnrepeat();
            compareImplementationsUnrepeat();
            propertiesSumByte();
            propertiesSumShort();
            propertiesSumInteger();
            propertiesSumLong();
            propertiesSumFloat();
            propertiesSumDouble();
            propertiesSumBigInteger();
            compareImplementationsSumBigInteger();
            propertiesSumBigDecimal();
            propertiesProductByte();
            propertiesProductShort();
            propertiesProductInteger();
            propertiesProductLong();
            propertiesProductFloat();
            propertiesProductDouble();
            propertiesProductBigInteger();
            compareImplementationsProductBigInteger();
            propertiesProductBigDecimal();
            propertiesDeltaByte();
            propertiesDeltaShort();
            propertiesDeltaInteger();
            propertiesDeltaLong();
            propertiesDeltaBigInteger();
            propertiesDeltaBigDecimal();
            propertiesDeltaFloat();
            propertiesDeltaDouble();
            propertiesDeltaCharacter();
        }
        System.out.println("Done");
    }

    private static @NotNull <T> List<T> unrepeat_alt(@NotNull List<T> xs) {
        if (xs.isEmpty()) return xs;
        for (int i : MathUtils.factors(xs.size())) {
            if (all(IterableUtils::same, demux(i, xs))) {
                return toList(take(i, xs));
            }
        }
        return xs;
    }

    private static void propertiesUnrepeat() {
        initialize();

        System.out.println("\t\ttesting unrepeat(List<T>) properties...");

        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            List<Integer> unrepeated = unrepeat(is);
            assertEquals(is.toString(), unrepeated, unrepeat_alt(is));
            assertEquals(is.toString(), unrepeat(unrepeated), unrepeated);
        }

        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.withNull(P.integers())))) {
            List<Integer> unrepeated = unrepeat(is);
            assertTrue(is.toString(), MathUtils.factors(is.size()).contains(unrepeated.size()));
            aeqit(is.toString(), concat(replicate(is.size() / unrepeated.size(), unrepeated)), is);
        }
    }

    private static void compareImplementationsUnrepeat() {
        initialize();
        Map<String, Function<List<Integer>, List<Integer>>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::unrepeat_alt);
        functions.put("standard", IterableUtils::unrepeat);
        compareImplementations("unrepeat(List<T>)", take(LIMIT, P.lists(P.withNull(P.integers()))), functions);
    }

    private static void propertiesSumByte() {
        initialize();
        System.out.println("\t\ttesting sumByte(Iterable<Byte>) properties...");

        foldProperties(LIMIT, P, P.bytes(), (x, y) -> (byte) (x + y), IterableUtils::sumByte, true);
    }

    private static void propertiesSumShort() {
        initialize();
        System.out.println("\t\ttesting sumShort(Iterable<Short>) properties...");

        foldProperties(LIMIT, P, P.shorts(), (x, y) -> (short) (x + y), IterableUtils::sumShort, true);
    }

    private static void propertiesSumInteger() {
        initialize();
        System.out.println("\t\ttesting sumInteger(Iterable<Integer>) properties...");

        foldProperties(LIMIT, P, P.integers(), (x, y) -> x + y, IterableUtils::sumInteger, true);
    }

    private static void propertiesSumLong() {
        initialize();
        System.out.println("\t\ttesting sumLong(Iterable<Long>) properties...");

        foldProperties(LIMIT, P, P.longs(), (x, y) -> x + y, IterableUtils::sumLong, true);
    }

    private static void propertiesSumFloat() {
        initialize();
        System.out.println("\t\ttesting sumFloat(Iterable<Float>) properties...");

        foldProperties(LIMIT, P, P.floats(), (x, y) -> x + y, IterableUtils::sumFloat, false);
        for (List<Float> fs : take(LIMIT, P.listsWithElement(Float.NaN, P.floats()))) {
            aeqf(fs, sumFloat(fs), Float.NaN);
        }
    }

    private static void propertiesSumDouble() {
        initialize();
        System.out.println("\t\ttesting sumDouble(Iterable<Double>) properties...");

        foldProperties(LIMIT, P, P.doubles(), (x, y) -> x + y, IterableUtils::sumDouble, false);
        for (List<Double> ds : take(LIMIT, P.listsWithElement(Double.NaN, P.doubles()))) {
            aeqd(ds, sumDouble(ds), Double.NaN);
        }
    }

    private static @NotNull BigInteger sumBigInteger_alt(@NotNull Iterable<BigInteger> xs) {
        return foldl(
                BigInteger::add,
                BigInteger.ZERO,
                sort(
                        (x, y) -> {
                            Ordering o = compare(x.abs(), y.abs());
                            if (o == EQ) {
                                int sx = x.signum();
                                int sy = x.signum();
                                if (sx > sy) {
                                    o = GT;
                                } else if (sx < sy) {
                                    o = LT;
                                }
                            }
                            return o.toInt();
                        },
                        xs
                )
        );
    }

    private static void propertiesSumBigInteger() {
        initialize();
        System.out.println("\t\ttesting sumBigInteger(Iterable<BigInteger>) properties...");

        foldProperties(LIMIT, P, P.bigIntegers(), BigInteger::add, IterableUtils::sumBigInteger, true);
    }

    private static void compareImplementationsSumBigInteger() {
        initialize();
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::sumBigInteger_alt);
        functions.put("standard", IterableUtils::sumBigInteger);
        compareImplementations(
                "sumBigInteger(Iterable<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions
        );
    }

    private static void propertiesSumBigDecimal() {
        initialize();
        System.out.println("\t\ttesting sumBigDecimal(Iterable<BigDecimal>) properties...");

        foldProperties(LIMIT, P, P.bigDecimals(), BigDecimal::add, IterableUtils::sumBigDecimal, true);
    }

    private static void propertiesProductByte() {
        initialize();
        System.out.println("\t\ttesting productByte(Iterable<Byte>) properties...");

        foldProperties(LIMIT, P, P.bytes(), (x, y) -> (byte) (x * y), IterableUtils::productByte, true);
    }

    private static void propertiesProductShort() {
        initialize();
        System.out.println("\t\ttesting productShort(Iterable<Short>) properties...");

        foldProperties(LIMIT, P, P.shorts(), (x, y) -> (short) (x * y), IterableUtils::productShort, true);
    }

    private static void propertiesProductInteger() {
        initialize();
        System.out.println("\t\ttesting productInteger(Iterable<Integer>) properties...");

        foldProperties(LIMIT, P, P.integers(), (x, y) -> x * y, IterableUtils::productInteger, true);
    }

    private static void propertiesProductLong() {
        initialize();
        System.out.println("\t\ttesting productLong(Iterable<Long>) properties...");

        foldProperties(LIMIT, P, P.longs(), (x, y) -> x * y, IterableUtils::productLong, true);
    }

    private static void propertiesProductFloat() {
        initialize();
        System.out.println("\t\ttesting productFloat(Iterable<Float>) properties...");

        foldProperties(LIMIT, P, P.floats(), (x, y) -> x * y, IterableUtils::productFloat, false);
        for (List<Float> fs : take(LIMIT, P.listsWithElement(Float.NaN, P.floats()))) {
            aeqf(fs, productFloat(fs), Float.NaN);
        }
    }

    private static void propertiesProductDouble() {
        initialize();
        System.out.println("\t\ttesting productDouble(Iterable<Double>) properties...");

        foldProperties(LIMIT, P, P.doubles(), (x, y) -> x * y, IterableUtils::productDouble, false);
        for (List<Double> ds : take(LIMIT, P.listsWithElement(Double.NaN, P.doubles()))) {
            aeqd(ds, productDouble(ds), Double.NaN);
        }
    }

    private static @NotNull BigInteger productBigInteger_alt(@NotNull Iterable<BigInteger> xs) {
        return foldl(
                BigInteger::multiply,
                BigInteger.ONE,
                sort(
                        (x, y) -> {
                            Ordering o = compare(x.abs(), y.abs());
                            if (o == EQ) {
                                int sx = x.signum();
                                int sy = x.signum();
                                if (sx > sy) {
                                    o = GT;
                                } else if (sx < sy) {
                                    o = LT;
                                }
                            }
                            return o.toInt();
                        },
                        xs
                )
        );
    }

    private static void propertiesProductBigInteger() {
        initialize();
        System.out.println("\t\ttesting productBigInteger(Iterable<BigInteger>) properties...");

        foldProperties(LIMIT, P, P.bigIntegers(), BigInteger::multiply, IterableUtils::productBigInteger, true);
    }

    private static void compareImplementationsProductBigInteger() {
        initialize();
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::productBigInteger_alt);
        functions.put("standard", IterableUtils::productBigInteger);
        compareImplementations(
                "productBigInteger(Iterable<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions
        );
    }

    private static void propertiesProductBigDecimal() {
        initialize();
        System.out.println("\t\ttesting productBigDecimal(Iterable<BigDecimal>) properties...");

        foldProperties(LIMIT, P, P.bigDecimals(), BigDecimal::multiply, IterableUtils::productBigDecimal, true);
    }
    
    private static void propertiesDeltaByte() {
        initialize();
        System.out.println("\t\ttesting deltaByte(Iterable<Byte>) properties...");

        deltaProperties(LIMIT, P, P.bytes(), b -> (byte) -b, (x, y) -> (byte) (x - y), IterableUtils::deltaByte);
    }

    private static void propertiesDeltaShort() {
        initialize();
        System.out.println("\t\ttesting deltaShort(Iterable<Short>) properties...");

        deltaProperties(LIMIT, P, P.shorts(), s -> (short) -s, (x, y) -> (short) (x - y), IterableUtils::deltaShort);
    }

    private static void propertiesDeltaInteger() {
        initialize();
        System.out.println("\t\ttesting deltaInteger(Iterable<Integer>) properties...");

        deltaProperties(LIMIT, P, P.integers(), i -> -i, (x, y) -> x - y, IterableUtils::deltaInteger);
    }

    private static void propertiesDeltaLong() {
        initialize();
        System.out.println("\t\ttesting deltaLong(Iterable<Long>) properties...");

        deltaProperties(LIMIT, P, P.longs(), l -> -l, (x, y) -> x - y, IterableUtils::deltaLong);
    }

    private static void propertiesDeltaBigInteger() {
        initialize();
        System.out.println("\t\ttesting deltaBigInteger(Iterable<BigInteger>) properties...");

        deltaProperties(
                LIMIT,
                P,
                P.bigIntegers(),
                BigInteger::negate,
                BigInteger::subtract,
                IterableUtils::deltaBigInteger
        );
    }

    private static void propertiesDeltaBigDecimal() {
        initialize();
        System.out.println("\t\ttesting deltaBigDecimal(Iterable<BigDecimal>) properties...");

        deltaProperties(
                LIMIT,
                P,
                P.bigDecimals(),
                BigDecimal::negate,
                BigDecimal::subtract,
                IterableUtils::deltaBigDecimal
        );
    }

    private static void propertiesDeltaFloat() {
        initialize();
        System.out.println("\t\ttesting deltaFloat(Iterable<Float>) properties...");

        deltaPropertiesClean(
                LIMIT,
                P,
                P.floats(),
                f -> -f,
                (x, y) -> x - y,
                IterableUtils::deltaFloat,
                FloatingPointUtils::absNegativeZeros
        );
        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            assertTrue(fs, all(f -> !FloatingPointUtils.isNegativeZero(f), deltaFloat(fs)));
        }
    }

    private static void propertiesDeltaDouble() {
        initialize();
        System.out.println("\t\ttesting deltaDouble(Iterable<Double>) properties...");

        deltaPropertiesClean(
                LIMIT,
                P,
                P.doubles(),
                d -> -d,
                (x, y) -> x - y,
                IterableUtils::deltaDouble,
                FloatingPointUtils::absNegativeZeros
        );
        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            assertTrue(ds, all(d -> !FloatingPointUtils.isNegativeZero(d), deltaDouble(ds)));
        }
    }

    private static void propertiesDeltaCharacter() {
        initialize();
        System.out.println("\t\ttesting deltaCharacter(Iterable<Character>) properties...");

        deltaProperties(LIMIT, P, P.characters(), i -> -i, (x, y) -> x - y, IterableUtils::deltaCharacter);
    }
}
