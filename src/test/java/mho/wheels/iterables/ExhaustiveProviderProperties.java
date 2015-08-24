package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class ExhaustiveProviderProperties {
    private static final ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final int LARGE_LIMIT = 10000;
    private static final int TINY_LIMIT = 20;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initializeConstant(String name) {
        System.out.println("\ttesting " + name + " properties...");
    }

    private static void initialize(String name) {
        P.reset();
        System.out.println("\t\ttesting " + name + " properties...");
    }

    @Test
    public void testAllProperties() {
        System.out.println("ExhaustiveProvider properties");
        propertiesBooleans();
        propertiesOrderingsIncreasing();
        propertiesOrderings();
        propertiesRoundingModes();
        propertiesBytesIncreasing();
        propertiesShortsIncreasing();
        propertiesIntegersIncreasing();
        propertiesLongsIncreasing();
        propertiesPositiveBytes();
        propertiesPositiveShorts();
        propertiesPositiveIntegers();
        propertiesPositiveLongs();
        propertiesPositiveBigIntegers();
        propertiesNegativeBytes();
        propertiesNegativeShorts();
        propertiesNegativeIntegers();
        propertiesNegativeLongs();
        propertiesNegativeBigIntegers();
        propertiesNonzeroBytes();
        propertiesNonzeroShorts();
        propertiesNonzeroIntegers();
        propertiesNonzeroLongs();
        propertiesNonzeroBigIntegers();
        propertiesNaturalBytes();
        propertiesNaturalShorts();
        propertiesNaturalIntegers();
        propertiesNaturalLongs();
        propertiesNaturalBigIntegers();
        propertiesBytes();
        propertiesShorts();
        propertiesIntegers();
        propertiesLongs();
        propertiesBigIntegers();
        propertiesAsciiCharactersIncreasing();
        propertiesAsciiCharacters();
        propertiesCharactersIncreasing();
        propertiesCharacters();
        propertiesPositiveBinaryFractions();
        propertiesNegativeBinaryFractions();
        propertiesNonzeroBinaryFractions();
        propertiesBinaryFractions();
        propertiesPositiveFloats();
        propertiesNegativeFloats();
        propertiesNonzeroFloats();
        propertiesFloats();
        propertiesPositiveDoubles();
        propertiesNegativeDoubles();
        propertiesNonzeroDoubles();
        propertiesDoubles();
        propertiesPositiveBigDecimals();
        propertiesNegativeBigDecimals();
        propertiesNonzeroBigDecimals();
        propertiesBigDecimals();
        propertiesPositiveCanonicalBigDecimals();
        propertiesNegativeCanonicalBigDecimals();
        propertiesNonzeroCanonicalBigDecimals();
        propertiesCanonicalBigDecimals();
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesUniformSample_Iterable();
            propertiesUniformSample_String();
            propertiesRangeUp_byte();
            propertiesRangeUp_short();
            propertiesRangeUp_int();
            propertiesRangeUp_long();
            propertiesRangeUp_BigInteger();
            propertiesRangeUp_char();
            propertiesRangeDown_byte();
            propertiesRangeDown_short();
            propertiesRangeDown_int();
            propertiesRangeDown_long();
            propertiesRangeDown_BigInteger();
            propertiesRangeDown_char();
            propertiesRange_byte_byte();
            propertiesRange_short_short();
            propertiesRange_int_int();
            propertiesRange_long_long();
            propertiesRange_BigInteger_BigInteger();
            propertiesRange_char_char();
            propertiesRangeUp_BinaryFraction();
            propertiesRangeDown_BinaryFraction();
            propertiesRange_BinaryFraction_BinaryFraction();
            propertiesRangeUp_float();
            propertiesRangeDown_float();
            propertiesRange_float_float();
            propertiesRangeUp_double();
            propertiesRangeDown_double();
            propertiesRange_double_double();
            propertiesRangeUp_BigDecimal();
            propertiesRangeDown_BigDecimal();
            propertiesRange_BigDecimal_BigDecimal();
            propertiesRangeUpCanonical_BigDecimal();
            propertiesRangeDownCanonical_BigDecimal();
            propertiesRangeCanonical_BigDecimal_BigDecimal();
            propertiesWithNull();
            propertiesNonEmptyOptionals();
            propertiesOptionals();
            propertiesNonEmptyNullableOptionals();
            propertiesNullableOptionals();
            propertiesDependentPairs();
            propertiesDependentPairsInfinite();
        }
        System.out.println("Done");
    }

    private static <T> void test_helper(
            int limit,
            @NotNull Object message,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        Iterable<T> txs = take(limit, xs);
        assertTrue(message, all(x -> x != null && predicate.test(x), txs));
        testNoRemove(limit, txs);
        assertTrue(message, unique(txs));
    }

    private static <T> void simpleTest(
            @NotNull Object message,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        test_helper(TINY_LIMIT, message, xs, predicate);
    }

    private static <T> void biggerTest(
            @NotNull Object message,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        test_helper(LARGE_LIMIT, message, xs, predicate);
    }

    private static void propertiesBooleans() {
        initializeConstant("booleans()");
        biggerTest(EP, EP.booleans(), b -> true);
        testHasNext(EP.booleans());
    }

    private static void propertiesOrderingsIncreasing() {
        initializeConstant("orderingsIncreasing()");
        biggerTest(EP, EP.orderingsIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.orderingsIncreasing()));
        testHasNext(EP.orderingsIncreasing());
    }

    private static void propertiesOrderings() {
        initializeConstant("orderings()");
        biggerTest(EP, EP.orderings(), b -> true);
        testHasNext(EP.orderings());
    }

    private static void propertiesRoundingModes() {
        initializeConstant("roundingModes()");
        biggerTest(EP, EP.roundingModes(), b -> true);
        testHasNext(EP.roundingModes());
    }

    private static void propertiesUniformSample_Iterable() {
        initialize("uniformSample(Iterable<T>)");
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            Iterable<Integer> js = EP.uniformSample(is);
            aeqit(is, is, js);
            testNoRemove(js);
            testHasNext(js);
        }
    }

    private static void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (String s : take(LIMIT, P.strings())) {
            Iterable<Character> cs = EP.uniformSample(s);
            assertEquals(s, s, charsToString(cs));
            testNoRemove(cs);
            testHasNext(cs);
        }
    }

    private static void propertiesBytesIncreasing() {
        initializeConstant("bytesIncreasing()");
        biggerTest(EP, EP.bytesIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.bytesIncreasing()));
        testHasNext(EP.bytesIncreasing());
    }

    private static void propertiesShortsIncreasing() {
        initializeConstant("shortsIncreasing()");
        biggerTest(EP, EP.shortsIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.shortsIncreasing()));
        testHasNext(EP.shortsIncreasing());
    }

    private static void propertiesIntegersIncreasing() {
        initializeConstant("integersIncreasing()");
        biggerTest(EP, EP.integersIncreasing(), b -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.integersIncreasing())));
    }

    private static void propertiesLongsIncreasing() {
        initializeConstant("longsIncreasing()");
        biggerTest(EP, EP.longsIncreasing(), b -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.longsIncreasing())));
    }

    private static void propertiesPositiveBytes() {
        initializeConstant("positiveBytes()");
        biggerTest(EP, EP.positiveBytes(), b -> b > 0);
        assertTrue(EP, increasing(EP.positiveBytes()));
        testHasNext(EP.positiveBytes());
    }

    private static void propertiesPositiveShorts() {
        initializeConstant("positiveShorts()");
        biggerTest(EP, EP.positiveShorts(), s -> s > 0);
        assertTrue(EP, increasing(EP.positiveShorts()));
        testHasNext(EP.positiveShorts());
    }

    private static void propertiesPositiveIntegers() {
        initializeConstant("positiveIntegers()");
        biggerTest(EP, EP.positiveIntegers(), i -> i > 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveIntegers())));
    }

    private static void propertiesPositiveLongs() {
        initializeConstant("positiveLongs()");
        biggerTest(EP, EP.positiveLongs(), l -> l > 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveLongs())));
    }

    private static void propertiesPositiveBigIntegers() {
        initializeConstant("positiveBigIntegers()");
        biggerTest(EP, EP.positiveBigIntegers(), i -> i.signum() == 1);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveBigIntegers())));
    }

    private static void propertiesNegativeBytes() {
        initializeConstant("negativeBytes()");
        biggerTest(EP, EP.negativeBytes(), b -> b < 0);
        assertTrue(EP, decreasing(EP.negativeBytes()));
        testHasNext(EP.negativeBytes());
    }

    private static void propertiesNegativeShorts() {
        initializeConstant("negativeShorts()");
        biggerTest(EP, EP.negativeShorts(), s -> s < 0);
        assertTrue(EP, decreasing(EP.negativeShorts()));
        testHasNext(EP.negativeShorts());
    }

    private static void propertiesNegativeIntegers() {
        initializeConstant("negativeIntegers()");
        biggerTest(EP, EP.negativeIntegers(), i -> i < 0);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeIntegers())));
    }

    private static void propertiesNegativeLongs() {
        initializeConstant("negativeLongs()");
        biggerTest(EP, EP.negativeLongs(), l -> l < 0);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeLongs())));
    }

    private static void propertiesNegativeBigIntegers() {
        initializeConstant("negativeBigIntegers()");
        biggerTest(EP, EP.negativeBigIntegers(), i -> i.signum() == -1);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeBigIntegers())));
    }

    private static void propertiesNonzeroBytes() {
        initializeConstant("nonzeroBytes()");
        biggerTest(EP, EP.nonzeroBytes(), b -> b != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.nonzeroBytes())));
        testHasNext(EP.nonzeroBytes());
    }

    private static void propertiesNonzeroShorts() {
        initializeConstant("nonzeroShorts()");
        biggerTest(EP, EP.nonzeroShorts(), s -> s != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.nonzeroShorts())));
        testHasNext(EP.nonzeroShorts());
    }

    private static void propertiesNonzeroIntegers() {
        initializeConstant("nonzeroIntegers()");
        biggerTest(EP, EP.nonzeroIntegers(), i -> i != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(LARGE_LIMIT, EP.nonzeroIntegers()))));
    }

    private static void propertiesNonzeroLongs() {
        initializeConstant("nonzeroLongs()");
        biggerTest(EP, EP.nonzeroLongs(), l -> l != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(LARGE_LIMIT, EP.nonzeroLongs()))));
    }

    private static void propertiesNonzeroBigIntegers() {
        initializeConstant("nonzeroBigIntegers()");
        biggerTest(EP, EP.nonzeroBigIntegers(), i -> !i.equals(BigInteger.ZERO));
        assertTrue(EP, weaklyIncreasing(map(BigInteger::abs, take(LARGE_LIMIT, EP.nonzeroBigIntegers()))));
    }

    private static void propertiesNaturalBytes() {
        initializeConstant("naturalBytes()");
        biggerTest(EP, EP.naturalBytes(), b -> b >= 0);
        assertTrue(EP, increasing(EP.naturalBytes()));
        testHasNext(EP.naturalBytes());
    }

    private static void propertiesNaturalShorts() {
        initializeConstant("naturalShorts()");
        biggerTest(EP, EP.naturalShorts(), s -> s >= 0);
        assertTrue(EP, increasing(EP.naturalShorts()));
        testHasNext(EP.naturalShorts());
    }

    private static void propertiesNaturalIntegers() {
        initializeConstant("naturalIntegers()");
        biggerTest(EP, EP.naturalIntegers(), i -> i >= 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalIntegers())));
    }

    private static void propertiesNaturalLongs() {
        initializeConstant("naturalLongs()");
        biggerTest(EP, EP.naturalLongs(), l -> l >= 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalLongs())));
    }

    private static void propertiesNaturalBigIntegers() {
        initializeConstant("naturalBigIntegers()");
        biggerTest(EP, EP.naturalBigIntegers(), i -> i.signum() != -1);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalBigIntegers())));
    }

    private static void propertiesBytes() {
        initializeConstant("bytes()");
        biggerTest(EP, EP.bytes(), b -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.bytes())));
        testHasNext(EP.bytes());
    }

    private static void propertiesShorts() {
        initializeConstant("shorts()");
        biggerTest(EP, EP.shorts(), s -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.shorts())));
        testHasNext(EP.shorts());
    }

    private static void propertiesIntegers() {
        initializeConstant("integers()");
        biggerTest(EP, EP.integers(), i -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(LARGE_LIMIT, EP.integers()))));
    }

    private static void propertiesLongs() {
        initializeConstant("longs()");
        biggerTest(EP, EP.longs(), l -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(LARGE_LIMIT, EP.longs()))));
    }

    private static void propertiesBigIntegers() {
        initializeConstant("bigIntegers()");
        biggerTest(EP, EP.bigIntegers(), i -> true);
        assertTrue(EP, weaklyIncreasing(map(BigInteger::abs, take(LARGE_LIMIT, EP.bigIntegers()))));
    }

    private static void propertiesAsciiCharactersIncreasing() {
        initializeConstant("asciiCharactersIncreasing()");
        biggerTest(EP, EP.asciiCharactersIncreasing(), i -> i < 128);
        assertTrue(EP, increasing(EP.asciiCharactersIncreasing()));
        testHasNext(EP.asciiCharactersIncreasing());
    }

    private static void propertiesAsciiCharacters() {
        initializeConstant("asciiCharacters()");
        biggerTest(EP, EP.asciiCharacters(), i -> i < 128);
        testHasNext(EP.asciiCharacters());
    }

    private static void propertiesCharactersIncreasing() {
        initializeConstant("charactersIncreasing()");
        biggerTest(EP, EP.charactersIncreasing(), i -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.charactersIncreasing())));
        testHasNext(EP.charactersIncreasing());
    }

    private static void propertiesCharacters() {
        initializeConstant("characters()");
        biggerTest(EP, EP.characters(), i -> true);
        testHasNext(EP.characters());
    }

    private static void propertiesRangeUp_byte() {
        initialize("rangeUp(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeUp(b);
            assertTrue(b, all(c -> c != null, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), (1 << 7) - b);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c >= b, bs));
            //noinspection RedundantCast
            assertTrue(b, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
        }
    }

    private static void propertiesRangeUp_short() {
        initialize("rangeUp(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeUp(s);
            simpleTest(s, ss, t -> t >= s);
            //noinspection RedundantCast
            assertTrue(s, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
            if (Short.MAX_VALUE - s < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private static void propertiesRangeUp_int() {
        initialize("rangeUp(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeUp(i);
            simpleTest(i, is, j -> j >= i);
            //noinspection RedundantCast
            assertTrue(i, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, is))));
            if ((long) Integer.MAX_VALUE - i < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private static void propertiesRangeUp_long() {
        initialize("rangeUp(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeUp(l);
            simpleTest(l, ls, m -> m >= l);
            //noinspection RedundantCast
            assertTrue(l, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(TINY_LIMIT, ls))));
            if (lt(BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(l)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private static void propertiesRangeUp_BigInteger() {
        initialize("rangeUp(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeUp(i);
            simpleTest(i, is, j -> ge(j, i));
            assertTrue(i, weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private static void propertiesRangeUp_char() {
        initialize("rangeUp(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeUp(c);
            simpleTest(nicePrint(c), cs, d -> d >= c);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
            if (Character.MAX_VALUE - c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private static void propertiesRangeDown_byte() {
        initialize("rangeDown(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeDown(b);
            assertTrue(b, all(c -> c != null, bs));
            testNoRemove(bs);
            assertEquals(b, length(bs), b + (1 << 7) + 1);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c <= b, bs));
            //noinspection RedundantCast
            assertTrue(b, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            testHasNext(bs);
        }
    }

    private static void propertiesRangeDown_short() {
        initialize("rangeDown(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeDown(s);
            simpleTest(s, ss, t -> t <= s);
            //noinspection RedundantCast
            assertTrue(s, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
            if (s - Short.MIN_VALUE < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private static void propertiesRangeDown_int() {
        initialize("rangeDown(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeDown(i);
            simpleTest(i, is, j -> j <= i);
            //noinspection RedundantCast
            assertTrue(i, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, is))));
            if ((long) i - Integer.MIN_VALUE < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private static void propertiesRangeDown_long() {
        initialize("rangeDown(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeDown(l);
            simpleTest(l, ls, m -> m <= l);
            //noinspection RedundantCast
            assertTrue(l, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(TINY_LIMIT, ls))));
            if (lt(BigInteger.valueOf(l).subtract(BigInteger.valueOf(Long.MIN_VALUE)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private static void propertiesRangeDown_BigInteger() {
        initialize("rangeDown(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeDown(i);
            simpleTest(i, is, j -> le(j, i));
            assertTrue(i, weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private static void propertiesRangeDown_char() {
        initialize("rangeDown(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeDown(c);
            simpleTest(c, cs, d -> d <= c);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
            if (c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private static void propertiesRange_byte_byte() {
        initialize("range(byte, byte)");
        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            Iterable<Byte> bs = EP.range(p.a, p.b);
            assertTrue(p, all(b -> b != null, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(p, length(bs), p.a > p.b ? 0 : p.b - p.a + 1);
            assertTrue(p, unique(bs));
            assertTrue(p, all(b -> b >= p.a && b <= p.b, bs));
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            assertEquals(p, p.a > p.b, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(b, EP.range(b, b), Collections.singletonList(b));
            aeqit(b, TINY_LIMIT, EP.range(b, Byte.MAX_VALUE), EP.rangeUp(b));
            aeqit(b, TINY_LIMIT, EP.range(Byte.MIN_VALUE, b), EP.rangeDown(b));
        }
    }

    private static void propertiesRange_short_short() {
        initialize("range(short, short)");
        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            Iterable<Short> ss = EP.range(p.a, p.b);
            simpleTest(p, ss, s -> s >= p.a && s <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
            assertEquals(p, p.a > p.b, isEmpty(ss));
            if (p.b - p.a < 1 << 8) {
                testHasNext(ss);
            }
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(s, EP.range(s, s), Collections.singletonList(s));
            aeqit(s, TINY_LIMIT, EP.range(s, Short.MAX_VALUE), EP.rangeUp(s));
            aeqit(s, TINY_LIMIT, EP.range(Short.MIN_VALUE, s), EP.rangeDown(s));
        }
    }

    private static void propertiesRange_int_int() {
        initialize("range(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            Iterable<Integer> is = EP.range(p.a, p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            simpleTest(p, is, i -> i >= p.a && i <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, tis)));
            assertEquals(p, p.a > p.b, isEmpty(is));
            if ((long) p.b - p.a < 1L << 8) {
                testHasNext(is);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(i, EP.range(i, i), Collections.singletonList(i));
            aeqit(i, TINY_LIMIT, EP.range(i, Integer.MAX_VALUE), EP.rangeUp(i));
            aeqit(i, TINY_LIMIT, EP.range(Integer.MIN_VALUE, i), EP.rangeDown(i));
        }
    }

    private static void propertiesRange_long_long() {
        initialize("range(long, long)");
        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            Iterable<Long> ls = EP.range(p.a, p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            simpleTest(p, ls, l -> l >= p.a && l <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Long>) map(Math::abs, tls)));
            assertEquals(p, p.a > p.b, isEmpty(ls));
            if (lt(BigInteger.valueOf(p.b).subtract(BigInteger.valueOf(p.a)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(l, EP.range(l, l), Collections.singletonList(l));
            aeqit(l, TINY_LIMIT, EP.range(l, Long.MAX_VALUE), EP.rangeUp(l));
            aeqit(l, TINY_LIMIT, EP.range(Long.MIN_VALUE, l), EP.rangeDown(l));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        initialize("range(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = EP.range(p.a, p.b);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            simpleTest(p, is, i -> ge(i, p.a) && le(i, p.b));
            assertTrue(p, weaklyIncreasing(map(BigInteger::abs, tis)));
            assertEquals(p, gt(p.a, p.b), isEmpty(is));
            if (lt(p.b.subtract(p.a), BigInteger.valueOf(1 << 8))) {
                testHasNext(is);
            }
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i, EP.range(i, i), Collections.singletonList(i));
        }
    }

    private static void propertiesRange_char_char() {
        initialize("range(char, char)");
        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            Iterable<Character> cs = EP.range(p.a, p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            simpleTest(p, cs, c -> c >= p.a && c <= p.b);
            assertTrue(p, increasing(tcs));
            assertEquals(p, p.a > p.b, isEmpty(cs));
            if (p.b - p.a < 1 << 8) {
                testHasNext(cs);
            }
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.range(c, c), Collections.singletonList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range(c, Character.MAX_VALUE), EP.rangeUp(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range('\0', c), EP.rangeDown(c));
        }
    }

    private static void propertiesPositiveBinaryFractions() {
        initializeConstant("positiveBinaryFractions()");
        biggerTest(EP, EP.positiveBinaryFractions(), bf -> bf.signum() == 1);
        take(TINY_LIMIT, EP.positiveBinaryFractions()).forEach(BinaryFraction::validate);
    }

    private static void propertiesNegativeBinaryFractions() {
        initializeConstant("negativeBinaryFractions()");
        biggerTest(EP, EP.negativeBinaryFractions(), bf -> bf.signum() == -1);
        take(TINY_LIMIT, EP.negativeBinaryFractions()).forEach(BinaryFraction::validate);
    }

    private static void propertiesNonzeroBinaryFractions() {
        initializeConstant("nonzeroBinaryFractions()");
        biggerTest(EP, EP.nonzeroBinaryFractions(), bf -> bf != BinaryFraction.ZERO);
        take(TINY_LIMIT, EP.nonzeroBinaryFractions()).forEach(BinaryFraction::validate);
    }

    private static void propertiesBinaryFractions() {
        initializeConstant("binaryFractions()");
        biggerTest(EP, EP.binaryFractions(), bf -> true);
        take(TINY_LIMIT, EP.binaryFractions()).forEach(BinaryFraction::validate);
    }

    private static void propertiesRangeUp_BinaryFraction() {
        initialize("rangeUp(BinaryFraction)");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Iterable<BinaryFraction> bfs = EP.rangeUp(bf);
            simpleTest(bf, bfs, c -> ge(c, bf));
            take(TINY_LIMIT, EP.rangeUp(bf)).forEach(BinaryFraction::validate);
        }
    }

    private static void propertiesRangeDown_BinaryFraction() {
        initialize("rangeDown(BinaryFraction)");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Iterable<BinaryFraction> bfs = EP.rangeDown(bf);
            simpleTest(bf, bfs, c -> le(c, bf));
            take(TINY_LIMIT, EP.rangeDown(bf)).forEach(BinaryFraction::validate);
        }
    }

    private static void propertiesRange_BinaryFraction_BinaryFraction() {
        initialize("range(BinaryFraction, BinaryFraction)");
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            Iterable<BinaryFraction> bfs = EP.range(p.a, p.b);
            simpleTest(p, bfs, bf -> ge(bf, p.a) && le(bf, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bfs));
            take(TINY_LIMIT, EP.range(p.a, p.b)).forEach(BinaryFraction::validate);
            if (ge(p.a, p.b)) {
                testHasNext(bfs);
            }
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            aeqit(bf, EP.range(bf, bf), Collections.singletonList(bf));
        }
    }

    private static void propertiesPositiveFloats() {
        initializeConstant("positiveFloats()");
        biggerTest(EP, EP.positiveFloats(), f -> f > 0);
    }

    private static void propertiesNegativeFloats() {
        initializeConstant("negativeFloats()");
        biggerTest(EP, EP.negativeFloats(), f -> f < 0);
    }

    private static void propertiesNonzeroFloats() {
        initializeConstant("nonzeroFloats()");
        biggerTest(EP, EP.nonzeroFloats(), f -> f != 0);
    }

    private static void propertiesFloats() {
        initializeConstant("floats()");
        biggerTest(EP, EP.floats(), f -> true);
    }

    private static void propertiesPositiveDoubles() {
        initializeConstant("positiveDoubles()");
        biggerTest(EP, EP.positiveDoubles(), d -> d > 0);
    }

    private static void propertiesNegativeDoubles() {
        initializeConstant("negativeDoubles()");
        biggerTest(EP, EP.negativeDoubles(), d -> d < 0);
    }

    private static void propertiesNonzeroDoubles() {
        initializeConstant("nonzeroDoubles()");
        biggerTest(EP, EP.nonzeroDoubles(), d -> d != 0);
    }

    private static void propertiesDoubles() {
        initializeConstant("doubles()");
        biggerTest(EP, EP.doubles(), d -> true);
    }

    private static void propertiesRangeUp_float() {
        initialize("rangeUp(float)");
        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            Iterable<Float> fs = EP.rangeUp(f);
            assertEquals(f, head(fs), Float.POSITIVE_INFINITY);
            simpleTest(
                    f,
                    fs,
                    g -> !Float.isNaN(g) &&
                            ge(FloatingPointUtils.absNegativeZeros(g), FloatingPointUtils.absNegativeZeros(f))
            );
            if ((long) FloatingPointUtils.toOrderedRepresentation(Float.POSITIVE_INFINITY) -
                    FloatingPointUtils.toOrderedRepresentation(f) < 1L << 8) {
                testHasNext(fs);
            }
        }
    }

    private static void propertiesRangeDown_float() {
        initialize("rangeDown(float)");
        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            Iterable<Float> fs = EP.rangeDown(f);
            assertEquals(f, head(fs), Float.NEGATIVE_INFINITY);
            simpleTest(
                    f,
                    fs,
                    g -> !Float.isNaN(g) &&
                            le(FloatingPointUtils.absNegativeZeros(g), FloatingPointUtils.absNegativeZeros(f))
            );
            if ((long) FloatingPointUtils.toOrderedRepresentation(f) -
                    FloatingPointUtils.toOrderedRepresentation(Float.NEGATIVE_INFINITY) < 1L << 8) {
                testHasNext(fs);
            }
        }
    }

    private static void propertiesRange_float_float() {
        initialize("range(float, float)");
        for (Pair<Float, Float> p : take(LIMIT, P.pairs(filter(g -> !Float.isNaN(g), P.floats())))) {
            Iterable<Float> fs = EP.range(p.a, p.b);
            Pair<Float, Float> q = new Pair<>(
                    FloatingPointUtils.absNegativeZeros(p.a),
                    FloatingPointUtils.absNegativeZeros(p.b)
            );
            simpleTest(
                    p,
                    fs,
                    f -> ge(FloatingPointUtils.absNegativeZeros(f), q.a) &&
                         le(FloatingPointUtils.absNegativeZeros(f), q.b) ///
            );
            assertEquals(p, gt(q.a, q.b), isEmpty(fs));
            if ((long) FloatingPointUtils.toOrderedRepresentation(p.b) -
                    FloatingPointUtils.toOrderedRepresentation(p.a) < 1L << 8) {
                testHasNext(fs);
            }
        }

        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g) && g != 0.0f, P.floats()))) {
            aeqit(f, EP.range(f, f), Collections.singletonList(f));
        }

        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g) && g != Float.NEGATIVE_INFINITY, P.floats()))) {
            aeqit(f, TINY_LIMIT, EP.range(f, Float.POSITIVE_INFINITY), EP.rangeUp(f));
        }

        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g) && g != Float.POSITIVE_INFINITY, P.floats()))) {
            aeqit(f, TINY_LIMIT, EP.range(Float.NEGATIVE_INFINITY, f), EP.rangeDown(f));
        }
    }

    private static void propertiesRangeUp_double() {
        initialize("rangeUp(double)");
        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            Iterable<Double> ds = EP.rangeUp(d);
            assertEquals(d, head(ds), Double.POSITIVE_INFINITY);
            simpleTest(
                    d,
                    ds,
                    e -> !Double.isNaN(e) &&
                            ge(FloatingPointUtils.absNegativeZeros(e), FloatingPointUtils.absNegativeZeros(d))
            );
            boolean condition = lt(
                    BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(Double.POSITIVE_INFINITY))
                            .subtract(BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(d))),
                    BigInteger.valueOf(1 << 8)
            );
            if (condition) {
                testHasNext(ds);
            }
        }
    }

    private static void propertiesRangeDown_double() {
        initialize("rangeDown(double)");
        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            Iterable<Double> ds = EP.rangeDown(d);
            assertEquals(d, head(ds), Double.NEGATIVE_INFINITY);
            simpleTest(
                    d,
                    ds,
                    e -> !Double.isNaN(e) &&
                            le(FloatingPointUtils.absNegativeZeros(e), FloatingPointUtils.absNegativeZeros(d))
            );
            boolean condition = lt(
                    BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(d))
                            .subtract(
                                    BigInteger.valueOf(
                                            FloatingPointUtils.toOrderedRepresentation(Double.NEGATIVE_INFINITY)
                                    )
                            ),
                    BigInteger.valueOf(1 << 8)
            );
            if (condition) {
                testHasNext(ds);
            }
        }
    }

    private static void propertiesRange_double_double() {
        initialize("range(double, double)");
        for (Pair<Double, Double> p : take(LIMIT, P.pairs(filter(g -> !Double.isNaN(g), P.doubles())))) {
            Iterable<Double> ds = EP.range(p.a, p.b);
            Pair<Double, Double> q = new Pair<>(
                    FloatingPointUtils.absNegativeZeros(p.a),
                    FloatingPointUtils.absNegativeZeros(p.b)
            );
            simpleTest(
                    p,
                    ds,
                    f -> ge(FloatingPointUtils.absNegativeZeros(f), q.a) &&
                         le(FloatingPointUtils.absNegativeZeros(f), q.b) ///
            );
            assertEquals(p, gt(q.a, q.b), isEmpty(ds));
            boolean condition = lt(
                    BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(p.b))
                            .subtract(BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(p.a))),
                    BigInteger.valueOf(1 << 8)
            );
            if (condition) {
                testHasNext(ds);
            }
        }

        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e) && e != 0.0, P.floats()))) {
            aeqit(d, EP.range(d, d), Collections.singletonList(d));
        }

        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e) && e != Double.NEGATIVE_INFINITY, P.doubles()))) {
            aeqit(d, TINY_LIMIT, EP.range(d, Double.POSITIVE_INFINITY), EP.rangeUp(d));
        }

        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e) && e != Double.POSITIVE_INFINITY, P.doubles()))) {
            aeqit(d, TINY_LIMIT, EP.range(Double.NEGATIVE_INFINITY, d), EP.rangeDown(d));
        }
    }

    private static void propertiesPositiveBigDecimals() {
        initializeConstant("positiveBigDecimals()");
        biggerTest(EP, EP.positiveBigDecimals(), bd -> bd.signum() == 1);
    }

    private static void propertiesNegativeBigDecimals() {
        initializeConstant("negativeBigDecimals()");
        biggerTest(EP, EP.negativeBigDecimals(), bd -> bd.signum() == -1);
    }

    private static void propertiesNonzeroBigDecimals() {
        initializeConstant("nonzeroBigDecimals()");
        biggerTest(EP, EP.nonzeroBigDecimals(), bd -> !bd.equals(BigDecimal.ZERO));
    }

    private static void propertiesBigDecimals() {
        initializeConstant("bigDecimals()");
        biggerTest(EP, EP.bigDecimals(), bd -> true);
    }

    private static void propertiesPositiveCanonicalBigDecimals() {
        initializeConstant("positiveCanonicalBigDecimals()");
        biggerTest(EP, EP.positiveCanonicalBigDecimals(), bd -> BigDecimalUtils.isCanonical(bd) && bd.signum() == 1);
    }

    private static void propertiesNegativeCanonicalBigDecimals() {
        initializeConstant("negativeCanonicalBigDecimals()");
        biggerTest(EP, EP.negativeCanonicalBigDecimals(), bd -> BigDecimalUtils.isCanonical(bd) && bd.signum() == -1);
    }

    private static void propertiesNonzeroCanonicalBigDecimals() {
        initializeConstant("nonzeroCanonicalBigDecimals()");
        biggerTest(
                EP,
                EP.nonzeroCanonicalBigDecimals(),
                bd -> BigDecimalUtils.isCanonical(bd) && !bd.equals(BigDecimal.ZERO)
        );
    }

    private static void propertiesCanonicalBigDecimals() {
        initializeConstant("canonicalBigDecimals()");
        biggerTest(EP, EP.canonicalBigDecimals(), BigDecimalUtils::isCanonical);
    }

    private static void propertiesRangeUp_BigDecimal() {
        initialize("rangeUp(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeUp(bd);
            simpleTest(bd, bds, c -> ge(c, bd));
        }
    }

    private static void propertiesRangeDown_BigDecimal() {
        initialize("rangeDown(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeDown(bd);
            simpleTest(bd, bds, c -> le(c, bd));
        }
    }

    //todo test hasNext
    private static void propertiesRange_BigDecimal_BigDecimal() {
        initialize("range(BigDecimal, BigDecimal)");
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            Iterable<BigDecimal> bds = EP.range(p.a, p.b);
            simpleTest(p, bds, bd -> ge(bd, p.a) && le(bd, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bds));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            all(c -> eq(c, bd), take(TINY_LIMIT, EP.range(bd, bd)));
        }
    }

    private static void propertiesRangeUpCanonical_BigDecimal() {
        initialize("rangeUpCanonical(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeUpCanonical(bd);
            simpleTest(bd, bds, c -> BigDecimalUtils.isCanonical(c) && ge(c, bd));
        }
    }

    private static void propertiesRangeDownCanonical_BigDecimal() {
        initialize("rangeDownCanonical(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeDownCanonical(bd);
            simpleTest(bd, bds, c -> BigDecimalUtils.isCanonical(c) && le(c, bd));
        }
    }

    private static void propertiesRangeCanonical_BigDecimal_BigDecimal() {
        initialize("rangeCanonical(BigDecimal, BigDecimal)");
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            Iterable<BigDecimal> bds = EP.rangeCanonical(p.a, p.b);
            simpleTest(p, bds, bd -> BigDecimalUtils.isCanonical(bd) && ge(bd, p.a) && le(bd, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bds));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            aeqit(bd, EP.rangeCanonical(bd, bd), Collections.singletonList(BigDecimalUtils.canonicalize(bd)));
        }
    }

    private static void propertiesWithNull() {
        initialize("withNull(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integers()))) {
            Iterable<Integer> withNull = EP.withNull(xs);
            testNoRemove(withNull);
            assertFalse(xs, isEmpty(withNull));
            assertNull(xs, head(withNull));
            assertTrue(xs, all(i -> i != null, tail(withNull)));
            inverses(EP::withNull, (Iterable<Integer> wn) -> toList(tail(wn)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.repeatingIterables(P.integers()))) {
            Iterable<Integer> withNull = EP.withNull(xs);
            testNoRemove(TINY_LIMIT, withNull);
            assertFalse(xs, isEmpty(withNull));
            assertNull(xs, head(withNull));
            assertTrue(xs, all(i -> i != null, take(TINY_LIMIT, tail(withNull))));
            aeqit(xs, TINY_LIMIT, tail(withNull), xs);
        }
    }

    private static void propertiesNonEmptyOptionals() {
        initialize("nonEmptyOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integers()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(neos);
            assertEquals(xs, xs.size(), length(neos));
            inverses(EP::nonEmptyOptionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, ys)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.repeatingIterables(P.integers()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(TINY_LIMIT, neos);
            aeqit(xs, TINY_LIMIT, map(Optional::get, neos), xs);
        }
    }

    private static void propertiesOptionals() {
        initialize("optionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.integers()))) {
            Iterable<Optional<Integer>> os = EP.optionals(xs);
            testNoRemove(os);
            assertFalse(xs, isEmpty(os));
            assertFalse(xs, head(os).isPresent());
            assertTrue(xs, all(Optional::isPresent, tail(os)));
            inverses(EP::optionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, tail(ys))), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.repeatingIterables(P.integers()))) {
            Iterable<Optional<Integer>> os = EP.optionals(xs);
            testNoRemove(TINY_LIMIT, os);
            assertFalse(xs, isEmpty(os));
            assertFalse(xs, head(os).isPresent());
            assertTrue(xs, all(Optional::isPresent, take(TINY_LIMIT, tail(os))));
            aeqit(xs, TINY_LIMIT, map(Optional::get, tail(os)), xs);
        }
    }

    private static void propertiesNonEmptyNullableOptionals() {
        initialize("nonEmptyNullableOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            Iterable<NullableOptional<Integer>> nenos = EP.nonEmptyNullableOptionals(xs);
            testNoRemove(nenos);
            assertEquals(xs, xs.size(), length(nenos));
            inverses(
                    EP::nonEmptyNullableOptionals,
                    (Iterable<NullableOptional<Integer>> ys) -> toList(map(NullableOptional::get, ys)),
                    xs
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.repeatingIterables(P.withNull(P.integers())))) {
            Iterable<NullableOptional<Integer>> nenos = EP.nonEmptyNullableOptionals(xs);
            testNoRemove(TINY_LIMIT, nenos);
            aeqit(xs, TINY_LIMIT, map(NullableOptional::get, nenos), xs);
        }
    }

    private static void propertiesNullableOptionals() {
        initialize("nullableOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            Iterable<NullableOptional<Integer>> nos = EP.nullableOptionals(xs);
            testNoRemove(nos);
            assertFalse(xs, isEmpty(nos));
            assertFalse(xs, head(nos).isPresent());
            assertTrue(xs, all(NullableOptional::isPresent, tail(nos)));
            inverses(
                    EP::nullableOptionals,
                    (Iterable<NullableOptional<Integer>> ys) -> toList(map(NullableOptional::get, tail(ys))),
                    xs
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.repeatingIterables(P.withNull(P.integers())))) {
            Iterable<NullableOptional<Integer>> nos = EP.nullableOptionals(xs);
            testNoRemove(TINY_LIMIT, nos);
            assertFalse(xs, isEmpty(nos));
            assertFalse(xs, head(nos).isPresent());
            assertTrue(xs, all(NullableOptional::isPresent, take(TINY_LIMIT, tail(nos))));
            aeqit(xs, TINY_LIMIT, map(NullableOptional::get, tail(nos)), xs);
        }
    }

    private static void propertiesDependentPairs() {
        initialize("dependentPairs(Iterable<A>, Function<A, Iterable<B>>)");
        IterableProvider PS = P.withScale(4);
        Iterable<Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> ps = P.dependentPairsInfinite(
                PS.lists(P.integers()),
                xs -> xs.isEmpty() ?
                        repeat(new FiniteDomainFunction<>(new HashMap<>())) :
                        map(
                                FiniteDomainFunction::new,
                                PS.maps(xs, map(is -> (Iterable<Integer>) is, PS.lists(P.integers())))
                        )
        );
        if (P instanceof ExhaustiveProvider) {
            ps = nub(ps);
        }
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.dependentPairs(p.a, p.b);
            testNoRemove(pairs);
            assertTrue(p, all(q -> q != null, pairs));
            assertTrue(p, isSubsetOf(map(q -> q.a, pairs), p.a));
            assertTrue(p, isSubsetOf(map(q -> q.b, pairs), concat(p.b.range())));
            assertEquals(p, length(pairs), sumInteger(map(i -> length(p.b.apply(i)), p.a)));
        }

        Function<List<Integer>, Iterable<Map<Integer, List<Integer>>>> f = xs -> {
            if (xs.isEmpty()) {
                return repeat(new HashMap<>());
            } else {
                return filter(m -> !all(p -> isEmpty(p.b), fromMap(m)), PS.maps(xs, PS.lists(P.integers())));
            }
        };
        Function<
                Pair<List<Integer>, Map<Integer, List<Integer>>>,
                Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>
        > g = p -> {
            Iterable<Pair<Integer, List<Integer>>> values = fromMap(p.b);
            Map<Integer, Iterable<Integer>> transformedValues = toMap(
                    map(e -> new Pair<>(e.a, (Iterable<Integer>) e.b), values)
            );
            return new Pair<>(cycle(p.a), new FiniteDomainFunction<>(transformedValues));
        };
        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> ps2 = map(
                g,
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.lists(P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, ps2)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.dependentPairs(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, all(q -> q != null, take(TINY_LIMIT, pairs)));
        }

        Iterable<Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail = nub(
                map(
                        p -> p.b,
                        P.dependentPairs(
                                filterInfinite(r -> r.b.domainSize() != 0, ps),
                                q -> map(k -> new Pair<>(q.a, q.b.set(k, null)), P.uniformSample(toList(q.b.domain())))
                        )
                )
        );
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairs(p.a, p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        //todo test uniqueness
    }

    private static void propertiesDependentPairsInfinite() {
        initialize("dependentPairsInfinite(Iterable<A>, Function<A, Iterable<B>>)");
        IterableProvider PS = P.withScale(4);
        Function<List<Integer>, Iterable<Map<Integer, List<Integer>>>> f = xs -> filterInfinite(
                m -> !all(p -> isEmpty(p.b), fromMap(m)),
                PS.maps(xs, map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers())))
        );
        Function<
                Pair<List<Integer>, Map<Integer, List<Integer>>>,
                Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>
        > g = p -> {
            Iterable<Pair<Integer, List<Integer>>> values = fromMap(p.b);
            Map<Integer, Iterable<Integer>> transformedValues = toMap(
                    map(e -> new Pair<>(e.a, cycle(e.b)), values)
            );
            return new Pair<>(cycle(p.a), new FiniteDomainFunction<>(transformedValues));
        };
        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> ps = map(
                g,
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.dependentPairsInfinite(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, all(q -> q != null, take(TINY_LIMIT, pairs)));
        }

        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail = map(
                p -> p.b,
                P.dependentPairs(
                        filterInfinite(r -> r.b.domainSize() != 0, ps),
                        q -> map(k -> new Pair<>(q.a, q.b.set(k, null)), P.uniformSample(toList(q.b.domain())))
                )
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }

        f = xs -> {
            if (xs.isEmpty()) {
                return repeat(new HashMap<>());
            } else {
                return filter(m -> !all(p -> isEmpty(p.b), fromMap(m)), PS.maps(xs, PS.lists(P.integers())));
            }
        };
        g = p -> {
            Iterable<Pair<Integer, List<Integer>>> values = fromMap(p.b);
            Map<Integer, Iterable<Integer>> transformedValues = toMap(
                    map(e -> new Pair<>(e.a, (Iterable<Integer>) e.b), values)
            );
            return new Pair<>(cycle(p.a), new FiniteDomainFunction<>(transformedValues));
        };
        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail2 = map(
                g,
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.lists(P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail3 = map(
                p -> new Pair<>(
                        p.a,
                        new FiniteDomainFunction<>(toMap(map(e -> new Pair<>(e.a, cycle(e.b)), fromMap(p.b))))
                ),
                nub(P.dependentPairsInfinite(PS.listsAtLeast(1, P.integers()), f))
        );
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail3)) {
            try {
                toList(EP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        //todo test uniqueness
    }
}
