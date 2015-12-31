package mho.wheels.iterables;

import mho.wheels.math.MathUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public strictfp class IterableUtilsProperties extends TestProperties {
    public IterableUtilsProperties() {
        super("IterableUtils");
    }

    @Override
    protected void testBothModes() {
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
        propertiesSumSignBigInteger();
        compareImplementationsSumSignBigInteger();
        propertiesSumSignBigDecimal();
        compareImplementationsSumSignBigDecimal();
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

    private static @NotNull <T> List<T> unrepeat_alt(@NotNull List<T> xs) {
        if (xs.isEmpty()) return xs;
        for (int i : MathUtils.factors(xs.size())) {
            if (all(IterableUtils::same, demux(i, xs))) {
                return toList(take(i, xs));
            }
        }
        return xs;
    }

    private void propertiesUnrepeat() {
        initialize("");

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

    private void compareImplementationsUnrepeat() {
        initialize("");
        Map<String, Function<List<Integer>, List<Integer>>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::unrepeat_alt);
        functions.put("standard", IterableUtils::unrepeat);
        compareImplementations("unrepeat(List<T>)", take(LIMIT, P.lists(P.withNull(P.integers()))), functions);
    }

    private void propertiesSumByte() {
        initialize("");
        System.out.println("\t\ttesting sumByte(Iterable<Byte>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bytes(),
                (x, y) -> (byte) (x + y),
                IterableUtils::sumByte,
                b -> {},
                true,
                true
        );
    }

    private void propertiesSumShort() {
        initialize("");
        System.out.println("\t\ttesting sumShort(Iterable<Short>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.shorts(),
                (x, y) -> (short) (x + y),
                IterableUtils::sumShort,
                s -> {},
                true,
                true
        );
    }

    private void propertiesSumInteger() {
        initialize("");
        System.out.println("\t\ttesting sumInteger(Iterable<Integer>) properties...");

        propertiesFoldHelper(LIMIT, P, P.integers(), (x, y) -> x + y, IterableUtils::sumInteger, i -> {}, true, true);
    }

    private void propertiesSumLong() {
        initialize("");
        System.out.println("\t\ttesting sumLong(Iterable<Long>) properties...");

        propertiesFoldHelper(LIMIT, P, P.longs(), (x, y) -> x + y, IterableUtils::sumLong, l -> {}, true, true);
    }

    private void propertiesSumFloat() {
        initialize("");
        System.out.println("\t\ttesting sumFloat(Iterable<Float>) properties...");

        propertiesFoldHelper(LIMIT, P, P.floats(), (x, y) -> x + y, IterableUtils::sumFloat, f -> {}, true, false);
        for (List<Float> fs : take(LIMIT, P.listsWithElement(Float.NaN, P.floats()))) {
            aeqf(fs, sumFloat(fs), Float.NaN);
        }
    }

    private void propertiesSumDouble() {
        initialize("");
        System.out.println("\t\ttesting sumDouble(Iterable<Double>) properties...");

        propertiesFoldHelper(LIMIT, P, P.doubles(), (x, y) -> x + y, IterableUtils::sumDouble, d -> {}, true, false);
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

    private void propertiesSumBigInteger() {
        initialize("");
        System.out.println("\t\ttesting sumBigInteger(Iterable<BigInteger>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bigIntegers(),
                BigInteger::add,
                IterableUtils::sumBigInteger,
                i -> {},
                true,
                true
        );
    }

    private void compareImplementationsSumBigInteger() {
        initialize("");
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::sumBigInteger_alt);
        functions.put("standard", IterableUtils::sumBigInteger);
        compareImplementations(
                "sumBigInteger(Iterable<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions
        );
    }

    private void propertiesSumBigDecimal() {
        initialize("");
        System.out.println("\t\ttesting sumBigDecimal(Iterable<BigDecimal>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bigDecimals(),
                BigDecimal::add,
                IterableUtils::sumBigDecimal,
                bd -> {},
                true,
                true
        );
    }

    private void propertiesProductByte() {
        initialize("");
        System.out.println("\t\ttesting productByte(Iterable<Byte>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bytes(),
                (x, y) -> (byte) (x * y),
                IterableUtils::productByte,
                b -> {},
                true,
                true
        );
    }

    private void propertiesProductShort() {
        initialize("");
        System.out.println("\t\ttesting productShort(Iterable<Short>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.shorts(),
                (x, y) -> (short) (x * y),
                IterableUtils::productShort,
                s -> {},
                true,
                true
        );
    }

    private void propertiesProductInteger() {
        initialize("");
        System.out.println("\t\ttesting productInteger(Iterable<Integer>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.integers(),
                (x, y) -> x * y,
                IterableUtils::productInteger,
                i -> {},
                true,
                true
        );
    }

    private void propertiesProductLong() {
        initialize("");
        System.out.println("\t\ttesting productLong(Iterable<Long>) properties...");

        propertiesFoldHelper(LIMIT, P, P.longs(), (x, y) -> x * y, IterableUtils::productLong, l -> {}, true, true);
    }

    private void propertiesProductFloat() {
        initialize("");
        System.out.println("\t\ttesting productFloat(Iterable<Float>) properties...");

        propertiesFoldHelper(LIMIT, P, P.floats(), (x, y) -> x * y, IterableUtils::productFloat, f -> {}, true, false);
        for (List<Float> fs : take(LIMIT, P.listsWithElement(Float.NaN, P.floats()))) {
            aeqf(fs, productFloat(fs), Float.NaN);
        }
    }

    private void propertiesProductDouble() {
        initialize("");
        System.out.println("\t\ttesting productDouble(Iterable<Double>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.doubles(),
                (x, y) -> x * y,
                IterableUtils::productDouble,
                d -> {},
                true,
                false
        );
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

    private void propertiesProductBigInteger() {
        initialize("");
        System.out.println("\t\ttesting productBigInteger(Iterable<BigInteger>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bigIntegers(),
                BigInteger::multiply,
                IterableUtils::productBigInteger,
                i -> {},
                true,
                true
        );
    }

    private void compareImplementationsProductBigInteger() {
        initialize("");
        Map<String, Function<List<BigInteger>, BigInteger>> functions = new LinkedHashMap<>();
        functions.put("alt", IterableUtilsProperties::productBigInteger_alt);
        functions.put("standard", IterableUtils::productBigInteger);
        compareImplementations(
                "productBigInteger(Iterable<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions
        );
    }

    private void propertiesProductBigDecimal() {
        initialize("");
        System.out.println("\t\ttesting productBigDecimal(Iterable<BigDecimal>) properties...");

        propertiesFoldHelper(
                LIMIT,
                P,
                P.bigDecimals(),
                BigDecimal::multiply,
                IterableUtils::productBigDecimal,
                bd -> {},
                true,
                true
        );
    }

    private static int sumSignBigInteger_simplest(@NotNull List<BigInteger> xs) {
        return sumBigInteger(xs).signum();
    }

    private static int sumSignBigInteger_alt(@NotNull List<BigInteger> xs) {
        if (xs.isEmpty()) return 0;
        int mostComplexBitLength = xs.get(0).bitLength();
        int mostComplexIndex = 0;
        for (int i = 1; i < xs.size(); i++) {
            int bitLength = xs.get(i).bitLength();
            if (bitLength > mostComplexBitLength) {
                mostComplexBitLength = bitLength;
                mostComplexIndex = i;
            }
        }
        int j = mostComplexIndex;
        BigInteger sum = sumBigInteger(map(xs::get, filter(i -> i != j, range(0, xs.size() - 1))));
        return Integer.signum(sum.compareTo(xs.get(mostComplexIndex).negate()));
    }

    private void propertiesSumSignBigInteger() {
        initialize("sumSignBigInteger(List<BigInteger>)");
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            int sumSign = sumSignBigInteger(is);
            assertEquals(is, sumSignBigInteger_simplest(is), sumSign);
            assertEquals(is, sumSignBigInteger_alt(is), sumSign);
            assertTrue(is, sumSign == 0 || sumSign == 1 || sumSign == -1);
            assertEquals(is, sumSignBigInteger(toList(map(BigInteger::negate, is))), -sumSign);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i, sumSignBigInteger(Collections.singletonList(i)), i.signum());
        }

        for (List<BigInteger> is : take(LIMIT, P.listsWithElement(null, P.bigIntegers()))) {
            try {
                sumSignBigInteger(is);
                fail(is);
            } catch (NullPointerException ignored) {}
        }
    }

    private void compareImplementationsSumSignBigInteger() {
        Map<String, Function<List<BigInteger>, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", IterableUtilsProperties::sumSignBigInteger_simplest);
        functions.put("alt", IterableUtilsProperties::sumSignBigInteger_alt);
        functions.put("standard", IterableUtils::sumSignBigInteger);
        compareImplementations(
                "sumSignBigInteger(List<BigInteger>)",
                take(LIMIT, P.lists(P.bigIntegers())),
                functions
        );
    }

    private static int sumSignBigDecimal_simplest(@NotNull List<BigDecimal> xs) {
        return sumBigDecimal(xs).signum();
    }

    private static int sumSignBigDecimal_alt(@NotNull List<BigDecimal> xs) {
        if (xs.isEmpty()) return 0;
        int mostComplexBitLength = xs.get(0).unscaledValue().bitLength();
        int mostComplexIndex = 0;
        for (int i = 1; i < xs.size(); i++) {
            int bitLength = xs.get(i).unscaledValue().bitLength();
            if (bitLength > mostComplexBitLength) {
                mostComplexBitLength = bitLength;
                mostComplexIndex = i;
            }
        }
        int j = mostComplexIndex;
        BigDecimal sum = sumBigDecimal(map(xs::get, filter(i -> i != j, range(0, xs.size() - 1))));
        return Integer.signum(sum.compareTo(xs.get(mostComplexIndex).negate()));
    }

    private void propertiesSumSignBigDecimal() {
        initialize("sumSignBigInteger(List<BigDecimal>)");
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            int sumSign = sumSignBigDecimal(bds);
            assertEquals(bds, sumSignBigDecimal_simplest(bds), sumSign);
            assertEquals(bds, sumSignBigDecimal_alt(bds), sumSign);
            assertTrue(bds, sumSign == 0 || sumSign == 1 || sumSign == -1);
            assertEquals(bds, sumSignBigDecimal(toList(map(BigDecimal::negate, bds))), -sumSign);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd, sumSignBigDecimal(Collections.singletonList(bd)), bd.signum());
        }

        for (List<BigDecimal> bds : take(LIMIT, P.listsWithElement(null, P.bigDecimals()))) {
            try {
                sumSignBigDecimal(bds);
                fail(bds);
            } catch (NullPointerException ignored) {}
        }
    }

    private void compareImplementationsSumSignBigDecimal() {
        Map<String, Function<List<BigDecimal>, Integer>> functions = new LinkedHashMap<>();
        functions.put("simplest", IterableUtilsProperties::sumSignBigDecimal_simplest);
        functions.put("alt", IterableUtilsProperties::sumSignBigDecimal_alt);
        functions.put("standard", IterableUtils::sumSignBigDecimal);
        compareImplementations(
                "sumSignBigDecimal(List<BigDecimal>)",
                take(LIMIT, P.lists(P.bigDecimals())),
                functions
        );
    }

    private void propertiesDeltaByte() {
        initialize("");
        System.out.println("\t\ttesting deltaByte(Iterable<Byte>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.bytes(),
                P.bytes(),
                b -> (byte) -b,
                (x, y) -> (byte) (x - y),
                IterableUtils::deltaByte,
                b -> {}
        );
    }

    private void propertiesDeltaShort() {
        initialize("");
        System.out.println("\t\ttesting deltaShort(Iterable<Short>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.shorts(),
                P.shorts(),
                s -> (short) -s,
                (x, y) -> (short) (x - y),
                IterableUtils::deltaShort,
                s -> {}
        );
    }

    private void propertiesDeltaInteger() {
        initialize("");
        System.out.println("\t\ttesting deltaInteger(Iterable<Integer>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.integers(),
                P.integers(),
                i -> -i,
                (x, y) -> x - y,
                IterableUtils::deltaInteger,
                i -> {}
        );
    }

    private void propertiesDeltaLong() {
        initialize("");
        System.out.println("\t\ttesting deltaLong(Iterable<Long>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.longs(),
                P.longs(),
                l -> -l,
                (x, y) -> x - y,
                IterableUtils::deltaLong,
                l -> {}
        );
    }

    private void propertiesDeltaBigInteger() {
        initialize("");
        System.out.println("\t\ttesting deltaBigInteger(Iterable<BigInteger>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.bigIntegers(),
                P.bigIntegers(),
                BigInteger::negate,
                BigInteger::subtract,
                IterableUtils::deltaBigInteger,
                i -> {}
        );
    }

    private void propertiesDeltaBigDecimal() {
        initialize("");
        System.out.println("\t\ttesting deltaBigDecimal(Iterable<BigDecimal>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.bigDecimals(),
                P.bigDecimals(),
                BigDecimal::negate,
                BigDecimal::subtract,
                IterableUtils::deltaBigDecimal,
                bd -> {}
        );
    }

    private void propertiesDeltaFloat() {
        initialize("");
        System.out.println("\t\ttesting deltaFloat(Iterable<Float>) properties...");

        propertiesDeltaHelperClean(
                LIMIT,
                P,
                EP.floats(),
                P.floats(),
                f -> -f,
                (x, y) -> x - y,
                IterableUtils::deltaFloat,
                f -> {},
                FloatingPointUtils::absNegativeZeros
        );
        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            assertTrue(fs, all(f -> !FloatingPointUtils.isNegativeZero(f), deltaFloat(fs)));
        }
    }

    private void propertiesDeltaDouble() {
        initialize("");
        System.out.println("\t\ttesting deltaDouble(Iterable<Double>) properties...");

        propertiesDeltaHelperClean(
                LIMIT,
                P,
                EP.doubles(),
                P.doubles(),
                d -> -d,
                (x, y) -> x - y,
                IterableUtils::deltaDouble,
                d -> {},
                FloatingPointUtils::absNegativeZeros
        );
        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            assertTrue(ds, all(d -> !FloatingPointUtils.isNegativeZero(d), deltaDouble(ds)));
        }
    }

    private void propertiesDeltaCharacter() {
        initialize("");
        System.out.println("\t\ttesting deltaCharacter(Iterable<Character>) properties...");

        propertiesDeltaHelper(
                LIMIT,
                P,
                EP.characters(),
                P.characters(),
                i -> -i,
                (x, y) -> x - y,
                IterableUtils::deltaCharacter,
                c -> {}
        );
    }
}
