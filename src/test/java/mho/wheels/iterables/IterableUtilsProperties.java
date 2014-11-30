package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static org.junit.Assert.*;

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
        for (boolean useRandom : Arrays.asList(false, true)) {
            System.out.println("Testing IterableUtils properties " + (useRandom ? "randomly" : "exhaustively"));
            USE_RANDOM = useRandom;

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

            System.out.println();
        }
        System.out.println("Done");
        System.out.println();
    }

    private static void propertiesSumByte() {
        initialize();
        System.out.println("testing sumByte(Iterable<Byte>) properties...");

        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            sumByte(bs);
        }

        Iterable<Pair<List<Byte>, List<Byte>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bytes()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Byte>, List<Byte>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumByte(p.a), sumByte(p.b));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertEquals(Byte.toString(b), sumByte(Arrays.asList(b)), b);
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumByte(Arrays.asList(p.a, p.b)), (byte) (p.a + p.b));
        }
    }

    private static void propertiesSumShort() {
        initialize();
        System.out.println("testing sumShort(Iterable<Short>) properties...");

        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            sumShort(ss);
        }

        Iterable<Pair<List<Short>, List<Short>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.shorts()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Short>, List<Short>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumShort(p.a), sumShort(p.b));
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertEquals(Short.toString(s), sumShort(Arrays.asList(s)), s);
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumShort(Arrays.asList(p.a, p.b)), (short) (p.a + p.b));
        }
    }

    private static void propertiesSumInteger() {
        initialize();
        System.out.println("testing sumInteger(Iterable<Integer>) properties...");

        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            sumInteger(is);
        }

        Iterable<Pair<List<Integer>, List<Integer>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.integers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumInteger(p.a), sumInteger(p.b));
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(Integer.toString(i), sumInteger(Arrays.asList(i)), i);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumInteger(Arrays.asList(p.a, p.b)), p.a + p.b);
        }
    }

    private static void propertiesSumLong() {
        initialize();
        System.out.println("testing sumLong(Iterable<Long>) properties...");

        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            sumLong(ls);
        }

        Iterable<Pair<List<Long>, List<Long>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.longs()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Long>, List<Long>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumLong(p.a), sumLong(p.b));
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(Long.toString(l), sumLong(Arrays.asList(l)), l);
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumLong(Arrays.asList(p.a, p.b)), p.a + p.b);
        }
    }

    private static void propertiesSumFloat() {
        initialize();
        System.out.println("testing sumFloat(Iterable<Float>) properties...");

        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            sumFloat(fs);
        }

        Iterable<List<Float>> fss = map(p -> {
            assert p.a != null;
            assert p.b != null;
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
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), sumFloat(Arrays.asList(p.a, p.b)), p.a + p.b);
        }
    }

    private static void propertiesSumDouble() {
        initialize();
        System.out.println("testing sumDouble(Iterable<Double>) properties...");

        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            sumDouble(ds);
        }

        Iterable<List<Double>> dss = map(p -> {
            assert p.a != null;
            assert p.b != null;
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
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), sumDouble(Arrays.asList(p.a, p.b)), p.a + p.b);
        }
    }

    private static void propertiesSumBigInteger() {
        initialize();
        System.out.println("testing sumBigInteger(Iterable<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            sumBigInteger(is);
        }

        Iterable<Pair<List<BigInteger>, List<BigInteger>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bigIntegers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigInteger>, List<BigInteger>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumBigInteger(p.a), sumBigInteger(p.b));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i.toString(), sumBigInteger(Arrays.asList(i)), i);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumBigInteger(Arrays.asList(p.a, p.b)), p.a.add(p.b));
        }
    }

    private static void propertiesSumBigDecimal() {
        initialize();
        System.out.println("testing sumBigDecimal(Iterable<BigDecimal>) properties...");

        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            sumBigDecimal(bds);
        }

        Iterable<Pair<List<BigDecimal>, List<BigDecimal>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bigDecimals()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigDecimal>, List<BigDecimal>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumBigDecimal(p.a), sumBigDecimal(p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd.toString(), sumBigDecimal(Arrays.asList(bd)), bd);
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), sumBigDecimal(Arrays.asList(p.a, p.b)), p.a.add(p.b));
        }
    }

    private static void propertiesProductByte() {
        initialize();
        System.out.println("testing productByte(Iterable<Byte>) properties...");

        Iterable<Pair<List<Byte>, List<Byte>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bytes()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Byte>, List<Byte>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productByte(p.a), productByte(p.b));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertEquals(Byte.toString(b), productByte(Arrays.asList(b)), b);
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productByte(Arrays.asList(p.a, p.b)), (byte) (p.a * p.b));
        }
    }

    private static void propertiesProductShort() {
        initialize();
        System.out.println("testing productShort(Iterable<Short>) properties...");

        Iterable<Pair<List<Short>, List<Short>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.shorts()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Short>, List<Short>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productShort(p.a), productShort(p.b));
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertEquals(Short.toString(s), productShort(Arrays.asList(s)), s);
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productShort(Arrays.asList(p.a, p.b)), (short) (p.a * p.b));
        }
    }

    private static void propertiesProductInteger() {
        initialize();
        System.out.println("testing productInteger(Iterable<Integer>) properties...");

        Iterable<Pair<List<Integer>, List<Integer>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.integers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productInteger(p.a), productInteger(p.b));
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(Integer.toString(i), productInteger(Arrays.asList(i)), i);
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productInteger(Arrays.asList(p.a, p.b)), p.a * p.b);
        }
    }

    private static void propertiesProductLong() {
        initialize();
        System.out.println("testing productLong(Iterable<Long>) properties...");

        Iterable<Pair<List<Long>, List<Long>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.longs()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<Long>, List<Long>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productLong(p.a), productLong(p.b));
        }

        for (long l : take(LIMIT, P.longs())) {
            assertEquals(Long.toString(l), productLong(Arrays.asList(l)), l);
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productLong(Arrays.asList(p.a, p.b)), p.a * p.b);
        }
    }

    private static void propertiesProductFloat() {
        initialize();
        System.out.println("testing productFloat(Iterable<Float>) properties...");

        Iterable<List<Float>> fss = map(p -> {
            assert p.a != null;
            assert p.b != null;
            return toList(insert(p.a, p.b, Float.NaN));
        }, (Iterable<Pair<List<Float>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.floats()),
                fs -> range(0, fs.size())
        ));

        for (List<Float> fs : take(LIMIT, fss)) {
            aeq(fs.toString(), productFloat(fs), Float.NaN);
        }

        for (float f : take(LIMIT, P.floats())) {
            aeq(Float.toString(f), productFloat(Arrays.asList(f)), f);
        }

        for (Pair<Float, Float> p : take(LIMIT, P.pairs(P.floats()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), productFloat(Arrays.asList(p.a, p.b)), p.a * p.b);
        }
    }

    private static void propertiesProductDouble() {
        initialize();
        System.out.println("testing productDouble(Iterable<Double>) properties...");

        Iterable<List<Double>> dss = map(p -> {
            assert p.a != null;
            assert p.b != null;
            return toList(insert(p.a, p.b, Double.NaN));
        }, (Iterable<Pair<List<Double>, Integer>>) P.dependentPairsLogarithmic(
                P.lists(P.doubles()),
                ds -> range(0, ds.size())
        ));

        for (List<Double> ds : take(LIMIT, dss)) {
            aeq(ds.toString(), productDouble(ds), Double.NaN);
        }

        for (double d : take(LIMIT, P.doubles())) {
            aeq(Double.toString(d), productDouble(Arrays.asList(d)), d);
        }

        for (Pair<Double, Double> p : take(LIMIT, P.pairs(P.doubles()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), productDouble(Arrays.asList(p.a, p.b)), p.a * p.b);
        }
    }

    private static void propertiesProductBigInteger() {
        initialize();
        System.out.println("testing productBigInteger(Iterable<BigInteger>) properties...");

        Iterable<Pair<List<BigInteger>, List<BigInteger>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bigIntegers()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigInteger>, List<BigInteger>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productBigInteger(p.a), productBigInteger(p.b));
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertEquals(i.toString(), productBigInteger(Arrays.asList(i)), i);
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productBigInteger(Arrays.asList(p.a, p.b)), p.a.multiply(p.b));
        }
    }

    private static void propertiesProductBigDecimal() {
        initialize();
        System.out.println("testing productBigDecimal(Iterable<BigDecimal>) properties...");

        Iterable<Pair<List<BigDecimal>, List<BigDecimal>>> ps = filter(
                q -> {
                    assert q.a != null;
                    assert q.b != null;
                    return !q.a.equals(q.b);
                },
                P.dependentPairsLogarithmic(P.lists(P.bigDecimals()), Combinatorics::permutationsIncreasing)
        );

        for (Pair<List<BigDecimal>, List<BigDecimal>> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productBigDecimal(p.a), productBigDecimal(p.b));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertEquals(bd.toString(), productBigDecimal(Arrays.asList(bd)), bd);
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assert p.a != null;
            assert p.b != null;
            assertEquals(p.toString(), productBigDecimal(Arrays.asList(p.a, p.b)), p.a.multiply(p.b));
        }
    }
    
    private static void propertiesDeltaByte() {
        initialize();
        System.out.println("testing deltaByte(Iterable<Byte>) properties...");

        for (List<Byte> bs : take(LIMIT, filter(cs -> !cs.isEmpty(), P.lists(P.bytes())))) {
            Iterable<Byte> reversed = reverse(map(b -> (byte) -b, deltaByte(reverse(bs))));
            aeq(bs.toString(), deltaByte(bs), reversed);
        }

        for (byte b : take(LIMIT, P.bytes())) {
            assertTrue(Byte.toString(b), isEmpty(deltaByte(Arrays.asList(b))));
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaByte(Arrays.asList(p.a, p.b)), Arrays.asList((byte) (p.b - p.a)));
        }
    }

    private static void propertiesDeltaShort() {
        initialize();
        System.out.println("testing deltaShort(Iterable<Short>) properties...");

        for (List<Short> ss : take(LIMIT, filter(ts -> !ts.isEmpty(), P.lists(P.shorts())))) {
            Iterable<Short> reversed = reverse(map(s -> (short) -s, deltaShort(reverse(ss))));
            aeq(ss.toString(), deltaShort(ss), reversed);
        }

        for (short s : take(LIMIT, P.shorts())) {
            assertTrue(Short.toString(s), isEmpty(deltaShort(Arrays.asList(s))));
        }

        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaShort(Arrays.asList(p.a, p.b)), Arrays.asList((short) (p.b - p.a)));
        }
    }

    private static void propertiesDeltaInteger() {
        initialize();
        System.out.println("testing deltaInteger(Iterable<Integer>) properties...");

        for (List<Integer> is : take(LIMIT, filter(js -> !js.isEmpty(), P.lists(P.integers())))) {
            Iterable<Integer> reversed = reverse(map(i -> -i, deltaInteger(reverse(is))));
            aeq(is.toString(), deltaInteger(is), reversed);
        }

        for (int i : take(LIMIT, P.integers())) {
            assertTrue(Integer.toString(i), isEmpty(deltaInteger(Arrays.asList(i))));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaInteger(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }
    }

    private static void propertiesDeltaLong() {
        initialize();
        System.out.println("testing deltaLong(Iterable<Long>) properties...");

        for (List<Long> ls : take(LIMIT, filter(ms -> !ms.isEmpty(), P.lists(P.longs())))) {
            Iterable<Long> reversed = reverse(map(l -> -l, deltaLong(reverse(ls))));
            aeq(ls.toString(), deltaLong(ls), reversed);
        }

        for (long l : take(LIMIT, P.longs())) {
            assertTrue(Long.toString(l), isEmpty(deltaLong(Arrays.asList(l))));
        }

        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaLong(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }
    }

    private static void propertiesDeltaBigInteger() {
        initialize();
        System.out.println("testing deltaBigInteger(Iterable<BigInteger>) properties...");

        for (List<BigInteger> is : take(LIMIT, filter(js -> !js.isEmpty(), P.lists(P.bigIntegers())))) {
            Iterable<BigInteger> reversed = reverse(map(BigInteger::negate, deltaBigInteger(reverse(is))));
            aeq(is.toString(), deltaBigInteger(is), reversed);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            assertTrue(i.toString(), isEmpty(deltaBigInteger(Arrays.asList(i))));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaBigInteger(Arrays.asList(p.a, p.b)), Arrays.asList(p.b.subtract(p.a)));
        }
    }

    private static void propertiesDeltaBigDecimal() {
        initialize();
        System.out.println("testing deltaBigDecimal(Iterable<BigDecimal>) properties...");

        for (List<BigDecimal> bds : take(LIMIT, filter(bes -> !bes.isEmpty(), P.lists(P.bigDecimals())))) {
            Iterable<BigDecimal> reversed = reverse(map(BigDecimal::negate, deltaBigDecimal(reverse(bds))));
            aeq(bds.toString(), deltaBigDecimal(bds), reversed);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            assertTrue(bd.toString(), isEmpty(deltaBigDecimal(Arrays.asList(bd))));
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaBigDecimal(Arrays.asList(p.a, p.b)), Arrays.asList(p.b.subtract(p.a)));
        }
    }

    private static void propertiesDeltaFloat() {
        initialize();
        System.out.println("testing deltaFloat(Iterable<Float>) properties...");

        for (List<Float> fs : take(LIMIT, filter(gs -> !gs.isEmpty(), P.lists(P.floats())))) {
            Iterable<Float> reversed = reverse(map(f -> -f, deltaFloat(reverse(fs))));
            aeq(fs.toString(), absFloatNegativeZeros(deltaFloat(fs)), absFloatNegativeZeros(reversed));
        }

        for (float f : take(LIMIT, P.floats())) {
            assertTrue(Float.toString(f), isEmpty(deltaFloat(Arrays.asList(f))));
        }

        for (Pair<Float, Float> p : take(LIMIT, P.pairs(P.floats()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaFloat(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
        }
    }

    private static void propertiesDeltaDouble() {
        initialize();
        System.out.println("testing deltaDouble(Iterable<Double>) properties...");

        for (List<Double> ds : take(LIMIT, filter(es -> !es.isEmpty(), P.lists(P.doubles())))) {
            Iterable<Double> reversed = reverse(map(d -> -d, deltaDouble(reverse(ds))));
            aeq(ds.toString(), absDoubleNegativeZeros(deltaDouble(ds)), absDoubleNegativeZeros(reversed));
        }

        for (double d : take(LIMIT, P.doubles())) {
            assertTrue(Double.toString(d), isEmpty(deltaDouble(Arrays.asList(d))));
        }

        for (Pair<Double, Double> p : take(LIMIT, P.pairs(P.doubles()))) {
            assert p.a != null;
            assert p.b != null;
            aeq(p.toString(), deltaDouble(Arrays.asList(p.a, p.b)), Arrays.asList(p.b - p.a));
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

    private static void aeq(String message, float f1, float f2) {
        assertEquals(message, Float.toString(f1), Float.toString(f2));
    }

    private static void aeq(String message, double d1, double d2) {
        assertEquals(message, Double.toString(d1), Double.toString(d2));
    }
}
