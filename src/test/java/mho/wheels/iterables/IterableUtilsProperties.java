package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class IterableUtilsProperties {
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
            propertiesSumBigDecimal();
            propertiesProductByte();
            propertiesProductShort();
            propertiesProductInteger();
            propertiesProductLong();
            propertiesProductFloat();
            propertiesProductDouble();
            propertiesProductBigInteger();
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
            aeq(is.toString(), concat(replicate(is.size() / unrepeated.size(), unrepeated)), is);
        }
    }

    private static void compareImplementationsUnrepeat() {
        initialize();
        System.out.println("\t\tcomparing unrepeat(List<T>) implementations...");

        long totalTime = 0;
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            long time = System.nanoTime();
            unrepeat_alt(is);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\talt: " + ((double) totalTime) / 1e9 + " s");

        totalTime = 0;
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            long time = System.nanoTime();
            unrepeat(is);
            totalTime += (System.nanoTime() - time);
        }
        System.out.println("\t\t\tstandard: " + ((double) totalTime) / 1e9 + " s");
    }

    private static void propertiesSumByte() {
        initialize();
        System.out.println("\t\ttesting sumByte(Iterable<Byte>) properties...");

        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            sumByte(bs);
        }

        Iterable<Pair<List<Byte>, List<Byte>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.bytes()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Byte>, List<Byte>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumByte(p.a), sumByte(p.b));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertEquals(Byte.toString(b), sumByte(Arrays.asList(b)), b);
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            assertEquals(p.toString(), sumByte(Arrays.asList(p.a, p.b)), (byte) (p.a + p.b));
        }

        Iterable<List<Byte>> failBss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Byte>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bytes()),
                        bs -> range(0, bs.size())
                )
        );
        for (List<Byte> bs : take(LIMIT, failBss)) {
            try {
                sumByte(bs);
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumShort() {
        initialize();
        System.out.println("\t\ttesting sumShort(Iterable<Short>) properties...");

        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            sumShort(ss);
        }

        Iterable<Pair<List<Short>, List<Short>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.shorts()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Short>, List<Short>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumShort(p.a), sumShort(p.b));
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertEquals(Short.toString(s), sumShort(Arrays.asList(s)), s);
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            assertEquals(p.toString(), sumShort(Arrays.asList(p.a, p.b)), (short) (p.a + p.b));
        }

        Iterable<List<Short>> failSss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Short>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.shorts()),
                        ss -> range(0, ss.size())
                )
        );
        for (List<Short> ss : take(LIMIT, failSss)) {
            try {
                sumShort(ss);
                fail(ss.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumInteger() {
        initialize();
        System.out.println("\t\ttesting sumInteger(Iterable<Integer>) properties...");

        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            sumInteger(is);
        }

        Iterable<Pair<List<Integer>, List<Integer>>> ps = filter(
                q -> {
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.integers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumInteger(p.a), sumInteger(p.b));
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(Integer.toString(i), sumInteger(Arrays.asList(i)), i);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            assertEquals(p.toString(), sumInteger(Arrays.asList(p.a, p.b)), p.a + p.b);
        }

        Iterable<List<Integer>> failIss = map(p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Integer>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.integers()),
                        is -> range(0, is.size())
                )
        );
        for (List<Integer> is : take(LIMIT, failIss)) {
            try {
                sumInteger(is);
                fail(is.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumLong() {
        initialize();
        System.out.println("\t\ttesting sumLong(Iterable<Long>) properties...");

        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            sumLong(ls);
        }

        Iterable<Pair<List<Long>, List<Long>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.longs()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Long>, List<Long>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumLong(p.a), sumLong(p.b));
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(Long.toString(l), sumLong(Arrays.asList(l)), l);
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            assertEquals(p.toString(), sumLong(Arrays.asList(p.a, p.b)), p.a + p.b);
        }

        Iterable<List<Long>> failLss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Long>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.longs()),
                        ls -> range(0, ls.size())
                )
        );
        for (List<Long> ls : take(LIMIT, failLss)) {
            try {
                sumLong(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumFloat() {
        initialize();
        System.out.println("\t\ttesting sumFloat(Iterable<Float>) properties...");

        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            sumFloat(fs);
        }

        Iterable<List<Float>> fss = map(p -> {
            return toList(insert(p.a, p.b, Float.NaN));
        }, (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.floats()),
                fs -> range(0, fs.size())
        ));

        for (List<Float> fs : take(LIMIT, fss)) {
            aeq(fs.toString(), sumFloat(fs), Float.NaN);
        }

        for (float f : take(LIMIT, P.floats())) {
            aeq(Float.toString(f), sumFloat(Arrays.asList(f)), f);
        }

        for (Pair<Float, Float> p : take(LIMIT, P.pairs(P.floats()))) {
            aeq(p.toString(), sumFloat(Arrays.asList(p.a, p.b)), p.a + p.b);
        }

        Iterable<List<Float>> failFss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.floats()),
                        fs -> range(0, fs.size())
                )
        );
        for (List<Float> fs : take(LIMIT, failFss)) {
            try {
                sumFloat(fs);
                fail(fs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumDouble() {
        initialize();
        System.out.println("\t\ttesting sumDouble(Iterable<Double>) properties...");

        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            sumDouble(ds);
        }

        Iterable<List<Double>> dss = map(p -> {
            return toList(insert(p.a, p.b, Double.NaN));
        }, (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.doubles()),
                ds -> range(0, ds.size())
        ));

        for (List<Double> ds : take(LIMIT, dss)) {
            aeq(ds.toString(), sumDouble(ds), Double.NaN);
        }

        for (double d : take(LIMIT, P.doubles())) {
            aeq(Double.toString(d), sumDouble(Arrays.asList(d)), d);
        }

        for (Pair<Double, Double> p : take(LIMIT, P.pairs(P.doubles()))) {
            aeq(p.toString(), sumDouble(Arrays.asList(p.a, p.b)), p.a + p.b);
        }

        Iterable<List<Double>> failDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.doubles()),
                        ds -> range(0, ds.size())
                )
        );
        for (List<Double> ls : take(LIMIT, failDss)) {
            try {
                sumDouble(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumBigInteger() {
        initialize();
        System.out.println("\t\ttesting sumBigInteger(Iterable<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            sumBigInteger(is);
        }

        Iterable<Pair<List<BigInteger>, List<BigInteger>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.bigIntegers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigInteger>, List<BigInteger>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumBigInteger(p.a), sumBigInteger(p.b));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i.toString(), sumBigInteger(Arrays.asList(i)), i);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            assertEquals(p.toString(), sumBigInteger(Arrays.asList(p.a, p.b)), p.a.add(p.b));
        }

        Iterable<List<BigInteger>> failIss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigInteger>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigIntegers()),
                        is -> range(0, is.size())
                )
        );
        for (List<BigInteger> ls : take(LIMIT, failIss)) {
            try {
                sumBigInteger(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesSumBigDecimal() {
        initialize();
        System.out.println("\t\ttesting sumBigDecimal(Iterable<BigDecimal>) properties...");

        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            sumBigDecimal(bds);
        }

        Iterable<Pair<List<BigDecimal>, List<BigDecimal>>> ps = filter(
                q -> {
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bigDecimals()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigDecimal>, List<BigDecimal>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), sumBigDecimal(p.a), sumBigDecimal(p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd.toString(), sumBigDecimal(Arrays.asList(bd)), bd);
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assertEquals(p.toString(), sumBigDecimal(Arrays.asList(p.a, p.b)), p.a.add(p.b));
        }

        Iterable<List<BigDecimal>> failBDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigDecimal>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigDecimals()),
                        bds -> range(0, bds.size())
                )
        );
        for (List<BigDecimal> bds : take(LIMIT, failBDss)) {
            try {
                sumBigDecimal(bds);
                fail(bds.toString());
            } catch (AssertionError | NullPointerException ignored) {}
        }
    }

    private static void propertiesProductByte() {
        initialize();
        System.out.println("\t\ttesting productByte(Iterable<Byte>) properties...");

        Iterable<Pair<List<Byte>, List<Byte>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.bytes()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Byte>, List<Byte>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productByte(p.a), productByte(p.b));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertEquals(Byte.toString(b), productByte(Arrays.asList(b)), b);
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            assertEquals(p.toString(), productByte(Arrays.asList(p.a, p.b)), (byte) (p.a * p.b));
        }

        Iterable<List<Byte>> failBss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Byte>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bytes()),
                        bs -> range(0, bs.size())
                )
        );
        for (List<Byte> bs : take(LIMIT, failBss)) {
            try {
                productByte(bs);
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductShort() {
        initialize();
        System.out.println("\t\ttesting productShort(Iterable<Short>) properties...");

        Iterable<Pair<List<Short>, List<Short>>> ps = filter(
                q -> {
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.shorts()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Short>, List<Short>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productShort(p.a), productShort(p.b));
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertEquals(Short.toString(s), productShort(Arrays.asList(s)), s);
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            assertEquals(p.toString(), productShort(Arrays.asList(p.a, p.b)), (short) (p.a * p.b));
        }

        Iterable<List<Short>> failSss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Short>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.shorts()),
                        ss -> range(0, ss.size())
                )
        );
        for (List<Short> ss : take(LIMIT, failSss)) {
            try {
                productShort(ss);
                fail(ss.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductInteger() {
        initialize();
        System.out.println("\t\ttesting productInteger(Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, List<Integer>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.integers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productInteger(p.a), productInteger(p.b));
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(Integer.toString(i), productInteger(Arrays.asList(i)), i);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            assertEquals(p.toString(), productInteger(Arrays.asList(p.a, p.b)), p.a * p.b);
        }

        Iterable<List<Integer>> failIss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Integer>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.integers()),
                        is -> range(0, is.size())
                )
        );
        for (List<Integer> is : take(LIMIT, failIss)) {
            try {
                productInteger(is);
                fail(is.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductLong() {
        initialize();
        System.out.println("\t\ttesting productLong(Iterable<Long>) properties...");

        Iterable<Pair<List<Long>, List<Long>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.longs()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Long>, List<Long>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productLong(p.a), productLong(p.b));
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(Long.toString(l), productLong(Arrays.asList(l)), l);
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            assertEquals(p.toString(), productLong(Arrays.asList(p.a, p.b)), p.a * p.b);
        }

        Iterable<List<Long>> failLss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Long>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.longs()),
                        ls -> range(0, ls.size())
                )
        );
        for (List<Long> ls : take(LIMIT, failLss)) {
            try {
                productLong(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductFloat() {
        initialize();
        System.out.println("\t\ttesting productFloat(Iterable<Float>) properties...");

        Iterable<List<Float>> fss = map(
                p -> toList(insert(p.a, p.b, Float.NaN)),
                (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.floats()),
                        fs -> range(0, fs.size())
                )
        );

        for (List<Float> fs : take(LIMIT, fss)) {
            aeq(fs.toString(), productFloat(fs), Float.NaN);
        }

        for (float f : take(LIMIT, P.floats())) {
            aeq(Float.toString(f), productFloat(Arrays.asList(f)), f);
        }

        for (Pair<Float, Float> p : take(LIMIT, P.pairs(P.floats()))) {
            aeq(p.toString(), productFloat(Arrays.asList(p.a, p.b)), p.a * p.b);
        }

        Iterable<List<Float>> failFss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.floats()),
                        fs -> range(0, fs.size())
                )
        );
        for (List<Float> fs : take(LIMIT, failFss)) {
            try {
                productFloat(fs);
                fail(fs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductDouble() {
        initialize();
        System.out.println("\t\ttesting productDouble(Iterable<Double>) properties...");

        Iterable<List<Double>> dss = map(
                p -> toList(insert(p.a, p.b, Double.NaN)),
                (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.doubles()),
                        ds -> range(0, ds.size())
                )
        );

        for (List<Double> ds : take(LIMIT, dss)) {
            aeq(ds.toString(), productDouble(ds), Double.NaN);
        }

        for (double d : take(LIMIT, P.doubles())) {
            aeq(Double.toString(d), productDouble(Arrays.asList(d)), d);
        }

        for (Pair<Double, Double> p : take(LIMIT, P.pairs(P.doubles()))) {
            aeq(p.toString(), productDouble(Arrays.asList(p.a, p.b)), p.a * p.b);
        }

        Iterable<List<Double>> failDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.doubles()),
                        ds -> range(0, ds.size())
                )
        );
        for (List<Double> ls : take(LIMIT, failDss)) {
            try {
                productDouble(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductBigInteger() {
        initialize();
        System.out.println("\t\ttesting productBigInteger(Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, List<BigInteger>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.bigIntegers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigInteger>, List<BigInteger>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productBigInteger(p.a), productBigInteger(p.b));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i.toString(), productBigInteger(Arrays.asList(i)), i);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            assertEquals(p.toString(), productBigInteger(Arrays.asList(p.a, p.b)), p.a.multiply(p.b));
        }

        Iterable<List<BigInteger>> failIss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigInteger>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigIntegers()),
                        is -> range(0, is.size())
                )
        );
        for (List<BigInteger> ls : take(LIMIT, failIss)) {
            try {
                productBigInteger(ls);
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesProductBigDecimal() {
        initialize();
        System.out.println("\t\ttesting productBigDecimal(Iterable<BigDecimal>) properties...");

        Iterable<Pair<List<BigDecimal>, List<BigDecimal>>> ps = filter(
                q -> !q.a.equals(q.b),
                P.dependentPairsLogarithmic(P.lists(P.bigDecimals()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigDecimal>, List<BigDecimal>> p : take(LIMIT, ps)) {
            assertEquals(p.toString(), productBigDecimal(p.a), productBigDecimal(p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd.toString(), productBigDecimal(Arrays.asList(bd)), bd);
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assertEquals(p.toString(), productBigDecimal(Arrays.asList(p.a, p.b)), p.a.multiply(p.b));
        }

        Iterable<List<BigDecimal>> failBDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigDecimal>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigDecimals()),
                        bds -> range(0, bds.size())
                )
        );
        for (List<BigDecimal> bds : take(LIMIT, failBDss)) {
            try {
                productBigDecimal(bds);
                fail(bds.toString());
            } catch (AssertionError | NullPointerException ignored) {}
        }
    }
    
    private static void propertiesDeltaByte() {
        initialize();
        System.out.println("\t\ttesting deltaByte(Iterable<Byte>) properties...");

        for (List<Byte> bs : take(LIMIT, P.listsAtLeast(1, P.bytes()))) {
            Iterable<Byte> deltas = deltaByte(bs);
            aeq(bs.toString(), length(deltas), length(bs) - 1);
            Iterable<Byte> reversed = reverse(map(b -> (byte) -b, deltaByte(reverse(bs))));
            aeq(bs.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertTrue(Byte.toString(b), isEmpty(deltaByte(Arrays.asList(b))));
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            aeq(p.toString(), deltaByte(Arrays.asList(p.a, p.b)), Arrays.asList((byte) (p.b - p.a)));
        }

        Iterable<List<Byte>> failBss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Byte>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bytes()),
                        bs -> range(0, bs.size())
                )
        );
        for (List<Byte> bs : take(LIMIT, failBss)) {
            try {
                toList(deltaByte(bs));
                fail(bs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaShort() {
        initialize();
        System.out.println("\t\ttesting deltaShort(Iterable<Short>) properties...");

        for (List<Short> ss : take(LIMIT, P.listsAtLeast(1, P.shorts()))) {
            Iterable<Short> deltas = deltaShort(ss);
            aeq(ss.toString(), length(deltas), length(ss) - 1);
            Iterable<Short> reversed = reverse(map(s -> (short) -s, deltaShort(reverse(ss))));
            aeq(ss.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertTrue(Short.toString(s), isEmpty(deltaShort(Arrays.asList(s))));
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            aeq(p.toString(), deltaShort(Arrays.asList(p.a, p.b)), Arrays.asList((short) (p.b - p.a)));
        }

        Iterable<List<Short>> failSss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Short>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.shorts()),
                        ss -> range(0, ss.size())
                )
        );
        for (List<Short> ss : take(LIMIT, failSss)) {
            try {
                toList(deltaShort(ss));
                fail(ss.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaInteger() {
        initialize();
        System.out.println("\t\ttesting deltaInteger(Iterable<Integer>) properties...");

        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.integers()))) {
            Iterable<Integer> deltas = deltaInteger(is);
            aeq(is.toString(), length(deltas), length(is) - 1);
            Iterable<Integer> reversed = reverse(map(i -> -i, deltaInteger(reverse(is))));
            aeq(is.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (int i : take(LIMIT, P.integers())) {
            assertTrue(Integer.toString(i), isEmpty(deltaInteger(Arrays.asList(i))));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            aeq(p.toString(), deltaInteger(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }

        Iterable<List<Integer>> failIss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Integer>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.integers()),
                        is -> range(0, is.size())
                )
        );
        for (List<Integer> is : take(LIMIT, failIss)) {
            try {
                toList(deltaInteger(is));
                fail(is.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaLong() {
        initialize();
        System.out.println("\t\ttesting deltaLong(Iterable<Long>) properties...");

        for (List<Long> ls : take(LIMIT, P.listsAtLeast(1, P.longs()))) {
            Iterable<Long> deltas = deltaLong(ls);
            aeq(ls.toString(), length(deltas), length(ls) - 1);
            Iterable<Long> reversed = reverse(map(l -> -l, deltaLong(reverse(ls))));
            aeq(ls.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (long l : take(LIMIT, P.longs())) {
            assertTrue(Long.toString(l), isEmpty(deltaLong(Arrays.asList(l))));
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            aeq(p.toString(), deltaLong(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }

        Iterable<List<Long>> failLss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Long>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.longs()),
                        ls -> range(0, ls.size())
                )
        );
        for (List<Long> ls : take(LIMIT, failLss)) {
            try {
                toList(deltaLong(ls));
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaBigInteger() {
        initialize();
        System.out.println("\t\ttesting deltaBigInteger(Iterable<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, P.listsAtLeast(1, P.bigIntegers()))) {
            Iterable<BigInteger> deltas = deltaBigInteger(is);
            aeq(is.toString(), length(deltas), length(is) - 1);
            Iterable<BigInteger> reversed = reverse(map(BigInteger::negate, deltaBigInteger(reverse(is))));
            aeq(is.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertTrue(i.toString(), isEmpty(deltaBigInteger(Arrays.asList(i))));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            aeq(p.toString(), deltaBigInteger(Arrays.asList(p.a, p.b)), Arrays.asList(p.b.subtract(p.a)));
        }

        Iterable<List<BigInteger>> failIss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigInteger>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigIntegers()),
                        is -> range(0, is.size())
                )
        );
        for (List<BigInteger> ls : take(LIMIT, failIss)) {
            try {
                toList(deltaBigInteger(ls));
                fail(ls.toString());
            } catch (AssertionError | NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaBigDecimal() {
        initialize();
        System.out.println("\t\ttesting deltaBigDecimal(Iterable<BigDecimal>) properties...");

        for (List<BigDecimal> bds : take(LIMIT, P.listsAtLeast(1, P.bigDecimals()))) {
            Iterable<BigDecimal> deltas = deltaBigDecimal(bds);
            aeq(bds.toString(), length(deltas), length(bds) - 1);
            Iterable<BigDecimal> reversed = reverse(map(BigDecimal::negate, deltaBigDecimal(reverse(bds))));
            aeq(bds.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertTrue(bd.toString(), isEmpty(deltaBigDecimal(Arrays.asList(bd))));
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            aeq(p.toString(), deltaBigDecimal(Arrays.asList(p.a, p.b)), Arrays.asList(p.b.subtract(p.a)));
        }

        Iterable<List<BigDecimal>> failBDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<BigDecimal>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.bigDecimals()),
                        bds -> range(0, bds.size())
                )
        );
        for (List<BigDecimal> bds : take(LIMIT, failBDss)) {
            try {
                toList(deltaBigDecimal(bds));
                fail(bds.toString());
            } catch (AssertionError | NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaFloat() {
        initialize();
        System.out.println("\t\ttesting deltaFloat(Iterable<Float>) properties...");

        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            Iterable<Float> deltas = deltaFloat(fs);
            aeq(fs.toString(), length(deltas), length(fs) - 1);
            Iterable<Float> reversed = reverse(map(f -> -f, deltaFloat(reverse(fs))));
            aeq(fs.toString(), absFloatNegativeZeros(deltas), absFloatNegativeZeros(reversed));
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (float f : take(LIMIT, P.floats())) {
            assertTrue(Float.toString(f), isEmpty(deltaFloat(Arrays.asList(f))));
        }

        for (Pair<Float, Float> p : take(LIMIT, P.pairs(P.floats()))) {
            aeq(p.toString(), deltaFloat(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }

        Iterable<List<Float>> failFss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.floats()),
                        fs -> range(0, fs.size())
                )
        );
        for (List<Float> fs : take(LIMIT, failFss)) {
            try {
                toList(deltaFloat(fs));
                fail(fs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaDouble() {
        initialize();
        System.out.println("\t\ttesting deltaDouble(Iterable<Double>) properties...");

        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            Iterable<Double> deltas = deltaDouble(ds);
            aeq(ds.toString(), length(deltas), length(ds) - 1);
            Iterable<Double> reversed = reverse(map(d -> -d, deltaDouble(reverse(ds))));
            aeq(ds.toString(), absDoubleNegativeZeros(deltas), absDoubleNegativeZeros(reversed));
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (double d : take(LIMIT, P.doubles())) {
            assertTrue(Double.toString(d), isEmpty(deltaDouble(Arrays.asList(d))));
        }

        for (Pair<Double, Double> p : take(LIMIT, P.pairs(P.doubles()))) {
            aeq(p.toString(), deltaDouble(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }

        Iterable<List<Double>> failDss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.doubles()),
                        ds -> range(0, ds.size())
                )
        );
        for (List<Double> ls : take(LIMIT, failDss)) {
            try {
                toList(deltaDouble(ls));
                fail(ls.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesDeltaCharacter() {
        initialize();
        System.out.println("\t\ttesting deltaCharacter(Iterable<Character>) properties...");

        for (List<Character> cs : take(LIMIT, P.listsAtLeast(1, P.characters()))) {
            Iterable<Integer> deltas = deltaCharacter(cs);
            aeq(cs.toString(), length(deltas), length(cs) - 1);
            Iterable<Integer> reversed = reverse(map(i -> -i, deltaCharacter(reverse(cs))));
            aeq(cs.toString(), deltas, reversed);
            try {
                deltas.iterator().remove();
            } catch (UnsupportedOperationException ignored) {}
        }

        for (char c : take(LIMIT, P.characters())) {
            assertTrue(Character.toString(c), isEmpty(deltaCharacter(Arrays.asList(c))));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            aeq(p.toString(), deltaCharacter(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }

        Iterable<List<Character>> failCss = map(
                p -> toList(insert(p.a, p.b, null)),
                (Iterable<Pair<List<Character>, Integer>>) P.dependentPairsLogarithmic(
                        P.lists(P.characters()),
                        cs -> range(0, cs.size())
                )
        );
        for (List<Character> cs : take(LIMIT, failCss)) {
            try {
                toList(deltaCharacter(cs));
                fail(cs.toString());
            } catch (NullPointerException ignored) {}
        }
    }

    private static @NotNull Iterable<Float> absFloatNegativeZeros(@NotNull Iterable<Float> fs) {
        return map(f -> f == 0.0f ? 0.0f : f, fs);
    }

    private static @NotNull Iterable<Double> absDoubleNegativeZeros(@NotNull Iterable<Double> ds) {
        return map(d -> d == 0.0 ? 0.0 : d, ds);
    }

    private static <T> void aeq(String message, Iterable<T> xs, Iterable<T> ys) {
        assertTrue(message, equal(xs, ys));
    }

    private static void aeq(String message, int i, int j) {
        assertEquals(message, i, j);
    }

    private static void aeq(String message, long i, long j) {
        assertEquals(message, i, j);
    }

    private static void aeq(String message, float f1, float f2) {
        assertEquals(message, Float.toString(f1), Float.toString(f2));
    }

    private static void aeq(String message, double d1, double d2) {
        assertEquals(message, Double.toString(d1), Double.toString(d2));
    }
}
