package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.math.MathUtils;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.ordering.comparators.*;
import mho.wheels.structures.*;
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
    private static final int SMALL_LIMIT = 100;
    private static final int TINY_LIMIT = 20;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initializeConstant(String name) {
        System.out.println("\ttesting " + name + " properties...");
    }

    private static void initialize(String name) {
        P.reset();
        System.out.print('\t');
        initializeConstant(name);
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
        propertiesStrings();
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
            propertiesDependentPairsInfiniteLogarithmicOrder();
            propertiesDependentPairsInfiniteSquareRootOrder();
            propertiesPairsLogarithmicOrder_Iterable_Iterable();
            propertiesPairsLogarithmicOrder_Iterable();
            compareImplementationsPairsLogarithmicOrder_Iterable();
            propertiesPairsSquareRootOrder_Iterable_Iterable();
            propertiesPairsSquareRootOrder_Iterable();
            compareImplementationsPairsSquareRootOrder_Iterable();
            propertiesPermutationsFinite();
            propertiesStringPermutations();
            propertiesPrefixPermutations();
            propertiesListsLex_int_List();
            compareImplementationsListsLex_int_List();
            propertiesPairsLex();
            propertiesTriplesLex();
            propertiesQuadruplesLex();
            propertiesQuintuplesLex();
            propertiesSextuplesLex();
            propertiesSeptuplesLex();
            propertiesStringsLex_int_String();
            compareImplementationsStringsLex_int_String();
            propertiesListsShortlex();
            propertiesStringsShortlex();
            propertiesListsShortlexAtLeast();
            propertiesStringsShortlexAtLeast();
            propertiesLists_int_Iterable();
            propertiesPairs_Iterable_Iterable();
            propertiesPairs_Iterable();
            compareImplementationsPairs_Iterable();
            propertiesTriples_Iterable_Iterable_Iterable();
            propertiesTriples_Iterable();
            compareImplementationsTriples_Iterable();
            propertiesQuadruples_Iterable_Iterable_Iterable_Iterable();
            propertiesQuadruples_Iterable();
            compareImplementationsQuadruples_Iterable();
            propertiesQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable();
            propertiesQuintuples_Iterable();
            compareImplementationsQuintuples_Iterable();
            propertiesSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable();
            propertiesSextuples_Iterable();
            compareImplementationsSextuples_Iterable();
            propertiesSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable();
            propertiesSeptuples_Iterable();
            compareImplementationsSeptuples_Iterable();
            propertiesStrings_int_String();
            propertiesStrings_int();
            propertiesLists();
            propertiesStrings_String();
            propertiesListsAtLeast();
            propertiesStringsAtLeast_int_String();
            propertiesStringsAtLeast_int();
            propertiesDistinctListsLex_int_List();
            propertiesDistinctPairsLex();
            propertiesDistinctTriplesLex();
            propertiesDistinctQuadruplesLex();
            propertiesDistinctQuintuplesLex();
            propertiesDistinctSextuplesLex();
            propertiesDistinctSeptuplesLex();
            propertiesDistinctStringsLex_int_String();
            propertiesDistinctListsLex_List();
            propertiesDistinctStringsLex_String();
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
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
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
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Integer> withNull = EP.withNull(xs);
            testNoRemove(withNull);
            testHasNext(withNull);
            assertFalse(xs, isEmpty(withNull));
            assertNull(xs, head(withNull));
            assertTrue(xs, all(i -> i != null, tail(withNull)));
            inverses(EP::withNull, (Iterable<Integer> wn) -> toList(tail(wn)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
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
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(neos);
            testHasNext(neos);
            assertEquals(xs, xs.size(), length(neos));
            inverses(EP::nonEmptyOptionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, ys)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(TINY_LIMIT, neos);
            aeqit(xs, TINY_LIMIT, map(Optional::get, neos), xs);
        }
    }

    private static void propertiesOptionals() {
        initialize("optionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Optional<Integer>> os = EP.optionals(xs);
            testNoRemove(os);
            testHasNext(os);
            assertFalse(xs, isEmpty(os));
            assertFalse(xs, head(os).isPresent());
            assertTrue(xs, all(Optional::isPresent, tail(os)));
            inverses(EP::optionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, tail(ys))), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
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
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<NullableOptional<Integer>> nenos = EP.nonEmptyNullableOptionals(xs);
            testNoRemove(nenos);
            testHasNext(nenos);
            assertEquals(xs, xs.size(), length(nenos));
            inverses(
                    EP::nonEmptyNullableOptionals,
                    (Iterable<NullableOptional<Integer>> ys) -> toList(map(NullableOptional::get, ys)),
                    xs
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<NullableOptional<Integer>> nenos = EP.nonEmptyNullableOptionals(xs);
            testNoRemove(TINY_LIMIT, nenos);
            aeqit(xs, TINY_LIMIT, map(NullableOptional::get, nenos), xs);
        }
    }

    private static void propertiesNullableOptionals() {
        initialize("nullableOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<NullableOptional<Integer>> nos = EP.nullableOptionals(xs);
            testNoRemove(nos);
            testHasNext(nos);
            assertFalse(xs, isEmpty(nos));
            assertFalse(xs, head(nos).isPresent());
            assertTrue(xs, all(NullableOptional::isPresent, tail(nos)));
            inverses(
                    EP::nullableOptionals,
                    (Iterable<NullableOptional<Integer>> ys) -> toList(map(NullableOptional::get, tail(ys))),
                    xs
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
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
            testHasNext(pairs);
            assertTrue(p, all(q -> q != null, pairs));
            assertTrue(p, isSubsetOf(map(q -> q.a, pairs), p.a));
            assertTrue(p, isSubsetOf(map(q -> q.b, pairs), concat(p.b.range())));
            assertEquals(p, length(pairs), sumInteger(map(i -> length(p.b.apply(i)), p.a)));
        }

        ps = P.dependentPairsInfinite(
                PS.distinctLists(P.integers()),
                xs -> xs.isEmpty() ?
                        repeat(new FiniteDomainFunction<>(new HashMap<>())) :
                        map(
                                FiniteDomainFunction::new,
                                PS.maps(xs, map(is -> (Iterable<Integer>) is, PS.distinctLists(P.integers())))
                        )
        );
        if (P instanceof ExhaustiveProvider) {
            ps = nub(ps);
        }
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.dependentPairs(p.a, p.b)));
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
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
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
            } catch (NullPointerException | IllegalArgumentException ignored) {}
        }
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

        if (P instanceof ExhaustiveProvider) {
            aeqit(
                    "",
                    LIMIT,
                    EP.pairs(EP.naturalBigIntegers()),
                    EP.dependentPairsInfinite(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers())
            );
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
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
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
    }

    private static void propertiesDependentPairsInfiniteLogarithmicOrder() {
        initialize("dependentPairsInfiniteLogarithmicOrder(Iterable<A>, Function<A, Iterable<B>>)");
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
            Iterable<Pair<Integer, Integer>> pairs = EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, all(q -> q != null, take(TINY_LIMIT, pairs)));
        }

        if (P instanceof ExhaustiveProvider) {
            aeqit(
                    "",
                    LIMIT,
                    map(p -> new Pair<>(p.b, p.a), EP.pairsLogarithmicOrder(EP.naturalBigIntegers())),
                    EP.dependentPairsInfiniteLogarithmicOrder(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers())
            );
        }

        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psSmaller = map(
                g,
                nub(
                        P.dependentPairsInfinite(
                                nub(
                                        map(
                                                IterableUtils::unrepeat,
                                                filterInfinite(xs -> xs.size() < 5, P.listsAtLeast(1, P.integers()))
                                        )
                                ),
                                f
                        )
                )
        );
        Iterable<Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail = map(
                p -> p.b,
                P.dependentPairs(
                        filterInfinite(r -> r.b.domainSize() != 0, psSmaller),
                        q -> map(k -> new Pair<>(q.a, q.b.set(k, null)), P.uniformSample(toList(q.b.domain())))
                )
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b));
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
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b));
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
                toList(EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesDependentPairsInfiniteSquareRootOrder() {
        initialize("dependentPairsInfiniteSquareRootOrder(Iterable<A>, Function<A, Iterable<B>>)");
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
            Iterable<Pair<Integer, Integer>> pairs = EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, all(q -> q != null, take(TINY_LIMIT, pairs)));
        }

        if (P instanceof ExhaustiveProvider) {
            aeqit(
                    "",
                    LIMIT,
                    map(p -> new Pair<>(p.b, p.a), EP.pairsSquareRootOrder(EP.naturalBigIntegers())),
                    EP.dependentPairsInfiniteSquareRootOrder(EP.naturalBigIntegers(), i -> EP.naturalBigIntegers())
            );
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
                toList(EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b));
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
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b));
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
                toList(EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesPairsLogarithmicOrder_Iterable_Iterable() {
        initialize("pairsLogarithmicOrder(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.integersGeometric()),
                filterInfinite(xs -> xs.size() < TINY_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLogarithmicOrder(p.a, p.b);
            testNoRemove(pairs);
            testHasNext(pairs);
            List<Pair<Integer, Integer>> pairList = toList(pairs);
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(p, p.a.contains(p2.a));
                assertTrue(p, p.b.contains(p2.b));
            }
            assertEquals(p, length(pairList), p.a.size() * p.b.size());
            if (pairList.size() != 0) {
                assertEquals(p, last(pairList), new Pair<>(last(p.a), last(p.b)));
            }
        }

        ps = P.pairs(
                P.withScale(4).distinctLists(P.integersGeometric()),
                filterInfinite(
                        xs -> xs.size() < TINY_LIMIT,
                        P.withScale(4).distinctLists(P.withNull(P.integersGeometric()))
                )
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.pairsLogarithmicOrder(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLogarithmicOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(p, elem(p2.a, p.a));
                assertTrue(p, elem(p2.b, p.b));
            }
        }
    }

    private static @NotNull List<Pair<Integer, Integer>> pairsLogarithmicOrder_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.pairsLogarithmicOrder(xs, xs));
    }

    private static void propertiesPairsLogarithmicOrder_Iterable() {
        initialize("pairsLogarithmicOrder(Iterable<T>)");
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> xs.size() < TINY_LIMIT,
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<Integer> is : take(LIMIT, iss)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLogarithmicOrder(is);
            testNoRemove(pairs);
            testHasNext(pairs);
            List<Pair<Integer, Integer>> pairList = toList(pairs);
            assertEquals(is, pairList, pairsLogarithmicOrder_Iterable_simplest(is));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(is, is.contains(p2.a));
                assertTrue(is, is.contains(p2.b));
            }
            assertEquals(is, length(pairList), is.size() * is.size());
            if (pairList.size() != 0) {
                assertEquals(is, last(pairList), new Pair<>(last(is), last(is)));
            }
        }

        iss = filterInfinite(
                xs -> xs.size() < TINY_LIMIT,
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric()))
        );
        for (List<Integer> is : take(LIMIT, iss)) {
            assertTrue(is, unique(EP.pairsLogarithmicOrder(is)));
        }

        for (Iterable<Integer> is : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLogarithmicOrder(is);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(is, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(is, elem(p2.a, is));
                assertTrue(is, elem(p2.b, is));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Pair<Integer, Integer>>> ps3 = zip(
                    P.naturalIntegers(),
                    EP.pairsLogarithmicOrder(P.naturalIntegers())
            );
            for (Pair<Integer, Pair<Integer, Integer>> p : take(LIMIT, ps3)) {
                assertTrue(p, p.b.a < p.a / 2 + 1);
                assertTrue(p, p.b.b < IntegerUtils.ceilingLog2(p.a + 2));
            }
        }
    }

    private static void compareImplementationsPairsLogarithmicOrder_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairsLogarithmicOrder_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairsLogarithmicOrder(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> xs.size() < TINY_LIMIT,
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("pairsLogarithmicOrder(Iterable<T>)", take(LIMIT, iss), functions);
    }

    private static void propertiesPairsSquareRootOrder_Iterable_Iterable() {
        initialize("pairsSquareRootOrder(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsSquareRootOrder(p.a, p.b);
            testNoRemove(pairs);
            testHasNext(pairs);
            List<Pair<Integer, Integer>> pairList = toList(pairs);
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(p, p.a.contains(p2.a));
                assertTrue(p, p.b.contains(p2.b));
            }
            assertEquals(p, length(pairList), p.a.size() * p.b.size());
            if (pairList.size() != 0) {
                assertEquals(p, last(pairList), new Pair<>(last(p.a), last(p.b)));
            }
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.pairsSquareRootOrder(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsSquareRootOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(p, elem(p2.a, p.a));
                assertTrue(p, elem(p2.b, p.b));
            }
        }
    }

    private static @NotNull List<Pair<Integer, Integer>> pairsSquareRootOrder_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.pairsSquareRootOrder(xs, xs));
    }

    private static void propertiesPairsSquareRootOrder_Iterable() {
        initialize("pairsSquareRootOrder(Iterable<T>)");
        for (List<Integer> is : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsSquareRootOrder(is);
            testNoRemove(pairs);
            testHasNext(pairs);
            List<Pair<Integer, Integer>> pairList = toList(pairs);
            assertEquals(is, pairList, pairsSquareRootOrder_Iterable_simplest(is));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(is, is.contains(p2.a));
                assertTrue(is, is.contains(p2.b));
            }
            assertEquals(is, length(pairList), is.size() * is.size());
            if (pairList.size() != 0) {
                assertEquals(is, last(pairList), new Pair<>(last(is), last(is)));
            }
        }

        for (List<Integer> is : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            assertTrue(is, unique(EP.pairsSquareRootOrder(is)));
        }

        for (Iterable<Integer> is : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsSquareRootOrder(is);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(is, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p2 : pairList) {
                assertTrue(is, elem(p2.a, is));
                assertTrue(is, elem(p2.b, is));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Pair<Integer, Integer>>> ps3 = zip(
                    P.naturalIntegers(),
                    EP.pairsSquareRootOrder(P.naturalIntegers())
            );
            for (Pair<Integer, Pair<Integer, Integer>> p : take(LIMIT, ps3)) {
                assertTrue(
                        p,
                        p.b.a < MathUtils.ceilingRoot(BigInteger.valueOf(3), BigInteger.valueOf(p.a).pow(2))
                                .intValueExact() * 2 + 1
                );
                assertTrue(
                        p,
                        p.b.b < MathUtils.ceilingRoot(BigInteger.valueOf(3), BigInteger.valueOf(p.a))
                                .intValueExact() * 2 + 1
                );
            }
        }
    }

    private static void compareImplementationsPairsSquareRootOrder_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairsSquareRootOrder_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairsSquareRootOrder(xs)));
        Iterable<List<Integer>> iss = P.withScale(4).lists(P.integersGeometric());
        compareImplementations("pairsSquareRootOrder(Iterable<T>)", take(LIMIT, iss), functions);
    }

    private static void propertiesPermutationsFinite() {
        initialize("permutationsFinite(List<T>)");
        Comparator<Integer> comparator = new WithNullComparator<>();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> permutations = EP.permutationsFinite(xs);
            testNoRemove(TINY_LIMIT, permutations);
            if (xs.size() < 10) {
                Comparator<Integer> xsComparator = new ListBasedComparator<>(xs);
                testHasNext(permutations);
                List<List<Integer>> permutationsList = toList(permutations);
                assertEquals(xs, head(permutationsList), sort(xsComparator, xs));
                assertEquals(xs, last(permutationsList), reverse(sort(xsComparator, xs)));
                assertEquals(xs, permutationsList.size(), MathUtils.permutationCount(xs).intValueExact());
                List<Integer> sorted = sort(comparator, xs);
                assertTrue(xs, all(p -> sort(comparator, p).equals(sorted), permutationsList));
                assertTrue(xs, unique(permutationsList));
                assertTrue(
                        xs,
                        increasing(
                                new LexComparator<>(xsComparator),
                                map(ys -> ((Iterable<Integer>) ys), permutationsList)
                        )
                );
            }
        }

        for (Integer i : take(LIMIT, P.withNull(P.integersGeometric()))) {
            assertEquals(
                    i,
                    toList(EP.permutationsFinite(Collections.singletonList(i))),
                    Collections.singletonList(Collections.singletonList(i))
            );
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.distinctPairs(P.withNull(P.integersGeometric())))) {
            assertEquals(
                    p,
                    toList(EP.permutationsFinite(Arrays.asList(p.a, p.b))),
                    Arrays.asList(Arrays.asList(p.a, p.b), Arrays.asList(p.b, p.a))
            );
        }
    }

    private static void propertiesStringPermutations() {
        initialize("stringPermutations(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> permutations = EP.stringPermutations(s);
            testNoRemove(TINY_LIMIT, permutations);
            if (s.length() < 10) {
                Comparator<Character> sComparator = new ListBasedComparator<>(toList(s));
                testHasNext(permutations);
                List<String> permutationsList = toList(permutations);
                assertEquals(s, head(permutationsList), sort(sComparator, s));
                assertEquals(s, last(permutationsList), reverse(sort(sComparator, s)));
                assertEquals(s, permutationsList.size(), MathUtils.permutationCount(toList(s)).intValueExact());
                String sorted = sort(s);
                assertTrue(s, all(p -> sort(p).equals(sorted), permutationsList));
                assertTrue(s, unique(permutationsList));
                assertTrue(
                        s,
                        increasing(new LexComparator<>(sComparator), map(IterableUtils::fromString, permutationsList))
                );
            }
        }

        for (char c : take(LIMIT, P.characters())) {
            String s = Character.toString(c);
            assertEquals(c, toList(EP.stringPermutations(s)), Collections.singletonList(s));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.distinctPairs(P.characters()))) {
            String s = charsToString(Arrays.asList(p.a, p.b));
            String t = charsToString(Arrays.asList(p.b, p.a));
            assertEquals(p, toList(EP.stringPermutations(s)), Arrays.asList(s, t));
        }
    }

    private static void propertiesPrefixPermutations() {
        initialize("prefixPermutations(Iterable<T>)");
        Comparator<Integer> comparator = new WithNullComparator<>();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Iterable<Integer>> permutations = EP.prefixPermutations(xs);
            testNoRemove(TINY_LIMIT, permutations);
            if (xs.size() < 10) {
                testHasNext(permutations);
                List<Iterable<Integer>> permutationsList = toList(permutations);
                assertEquals(xs, toList(head(permutationsList)), xs);
                assertEquals(xs, toList(last(permutationsList)), reverse(xs));
                assertEquals(xs, permutationsList.size(), MathUtils.factorial(xs.size()).intValueExact());
                List<Integer> sorted = sort(comparator, xs);
                assertTrue(xs, all(p -> sort(comparator, p).equals(sorted), permutationsList));
                for (Iterable<Integer> is : take(TINY_LIMIT, permutationsList)) {
                    testNoRemove(is);
                    testHasNext(is);
                }
            }
        }

        for (Integer i : take(LIMIT, P.withNull(P.integersGeometric()))) {
            assertEquals(
                    i,
                    toList(
                            (Iterable<List<Integer>>) map(
                                    IterableUtils::toList,
                                    EP.prefixPermutations(Collections.singletonList(i))
                            )
                    ),
                    Collections.singletonList(Collections.singletonList(i))
            );
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integersGeometric())))) {
            assertEquals(
                    p,
                    toList(
                            (Iterable<List<Integer>>) map(
                                    IterableUtils::toList,
                                    EP.prefixPermutations(Arrays.asList(p.a, p.b))
                            )
                    ),
                    Arrays.asList(Arrays.asList(p.a, p.b), Arrays.asList(p.b, p.a))
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Iterable<Integer>> permutations = EP.prefixPermutations(xs);
            List<Integer> smallXs = toList(take(TINY_LIMIT, xs));
            testNoRemove(TINY_LIMIT, permutations);
            List<Iterable<Integer>> permutationsList = toList(take(TINY_LIMIT, permutations));
            assertEquals(its(xs), toList(take(TINY_LIMIT, head(permutationsList))), smallXs);
            assertEquals(its(xs), permutationsList.size(), TINY_LIMIT);
            for (Iterable<Integer> is : permutationsList) {
                testNoRemove(TINY_LIMIT, is);
                assertTrue(its(is), unique(take(TINY_LIMIT, is)));
            }
        }
    }

    private static @NotNull List<List<Integer>> listsLex_int_List_alt(
            int size, @NotNull List<Integer> xs
    ) {
        int xsSize = xs.size();
        switch (xsSize) {
            case 0:
                return size == 0 ? Collections.singletonList(Collections.emptyList()) : Collections.emptyList();
            case 1:
                return Collections.singletonList(toList(replicate(size, xs.get(0))));
            default:
                BigInteger base = BigInteger.valueOf(xsSize);
                return toList(
                        map(
                                i -> toList(
                                        map(
                                                d -> xs.get(d.intValueExact()),
                                                IntegerUtils.bigEndianDigitsPadded(size, base, i)
                                        )
                                ),
                                range(BigInteger.ZERO, base.pow(size).subtract(BigInteger.ONE))
                        )
                );
        }
    }

    private static void propertiesListsLex_int_List() {
        initialize("listsLex(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.listsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = BigInteger.valueOf(p.a.size()).pow(p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                assertEquals(p, listsLex_int_List_alt(p.b, p.a), listsList);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
                    assertEquals(p, last(listsList), toList(replicate(p.b, last(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = BigInteger.valueOf(p.a.size()).pow(p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.listsLex(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(
                        p,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(p.a)),
                                map(ys -> ((Iterable<Integer>) ys), listsList)
                        )
                );
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.listsLex(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> xss = EP.listsLex(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.listsLex(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsListsLex_int_List() {
        Map<String, Function<Pair<List<Integer>, Integer>, List<List<Integer>>>> functions = new LinkedHashMap<>();
        functions.put("alt", p -> listsLex_int_List_alt(p.b, p.a));
        functions.put("standard", p -> toList(EP.listsLex(p.b, p.a)));
        Iterable<Pair<List<Integer>, Integer>> ps = filterInfinite(
                p -> lt(BigInteger.valueOf(p.a.size()).pow(p.b), BigInteger.valueOf(LIMIT)),
                P.pairsLogarithmicOrder(
                        P.withScale(4).lists(P.withNull(P.integersGeometric())),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        compareImplementations("listsLex(int, List<T>)", take(LIMIT, ps), functions);
    }

    private static void propertiesPairsLex() {
        initialize("pairsLex(Iterable<A>, List<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLex(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = BigInteger.valueOf(p.a.size()).multiply(BigInteger.valueOf(p.b.size()));
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!p.a.isEmpty() && !p.b.isEmpty()) {
                    assertEquals(p, head(pairsList), new Pair<>(head(p.a), head(p.b)));
                    assertEquals(p, last(pairsList), new Pair<>(last(p.a), last(p.b)));
                }
                assertEquals(p, pairsList.size(), pairsLength.intValueExact());
                assertTrue(p, all(i -> elem(i, p.a), map(q -> q.a, pairsList)));
                assertTrue(p, all(i -> elem(i, p.b), map(q -> q.b, pairsList)));
            }
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            BigInteger pairsLength = BigInteger.valueOf(p.a.size()).multiply(BigInteger.valueOf(p.b.size()));
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.pairsLex(p.a, p.b));
                assertTrue(p, unique(pairsList));
                Comparator<Pair<Integer, Integer>> comparator = new Pair.PairComparator<>(
                        new ListBasedComparator<>(p.a),
                        new ListBasedComparator<>(p.b)
                );
                assertTrue(p, increasing(comparator, pairsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, List<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairsLex(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            if (!isEmpty(p.a) && !p.b.isEmpty()) {
                assertEquals(p, head(pairsList), new Pair<>(head(p.a), head(p.b)));
            }
            assertTrue(p, all(i -> elem(i, p.a), map(q -> q.a, pairsList)));
            assertTrue(p, all(i -> elem(i, p.b), map(q -> q.b, pairsList)));
        }

        ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.distinctLists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(LIMIT, ps2)) {
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, EP.pairsLex(p.a, p.b)));
            assertTrue(p, unique(pairsList));
        }
    }

    private static void propertiesTriplesLex() {
        initialize("triplesLex(Iterable<A>, List<B>, List<C>)");
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts)) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triplesLex(t.a, t.b, t.c);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = BigInteger.valueOf(t.a.size())
                    .multiply(BigInteger.valueOf(t.b.size()))
                    .multiply(BigInteger.valueOf(t.c.size()));
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!t.a.isEmpty() && !t.b.isEmpty() && !t.c.isEmpty()) {
                    assertEquals(t, head(triplesList), new Triple<>(head(t.a), head(t.b), head(t.c)));
                    assertEquals(t, last(triplesList), new Triple<>(last(t.a), last(t.b), last(t.c)));
                }
                assertEquals(t, triplesList.size(), triplesLength.intValueExact());
                assertTrue(t, all(i -> elem(i, t.a), map(u -> u.a, triplesList)));
                assertTrue(t, all(i -> elem(i, t.b), map(u -> u.b, triplesList)));
                assertTrue(t, all(i -> elem(i, t.c), map(u -> u.c, triplesList)));
            }
        }

        ts = P.triples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts)) {
            BigInteger triplesLength = BigInteger.valueOf(t.a.size())
                    .multiply(BigInteger.valueOf(t.b.size()))
                    .multiply(BigInteger.valueOf(t.c.size()));
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.triplesLex(t.a, t.b, t.c));
                assertTrue(t, unique(triplesList));
                Comparator<Triple<Integer, Integer, Integer>> comparator = new Triple.TripleComparator<>(
                        new ListBasedComparator<>(t.a),
                        new ListBasedComparator<>(t.b),
                        new ListBasedComparator<>(t.c)
                );
                assertTrue(t, increasing(comparator, triplesList));
            }
        }

        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Triple<Iterable<Integer>, List<Integer>, List<Integer>>> ts2 = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs
        );
        for (Triple<Iterable<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts2)) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triplesLex(t.a, t.b, t.c);
            testNoRemove(TINY_LIMIT, triples);
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            if (!isEmpty(t.a) && !t.b.isEmpty() && !t.c.isEmpty()) {
                assertEquals(t, head(triplesList), new Triple<>(head(t.a), head(t.b), head(t.c)));
            }
            assertTrue(t, all(i -> elem(i, t.a), map(u -> u.a, triplesList)));
            assertTrue(t, all(i -> elem(i, t.b), map(u -> u.b, triplesList)));
            assertTrue(t, all(i -> elem(i, t.c), map(u -> u.c, triplesList)));
        }

        finiteArgs = P.distinctLists(P.withNull(P.integersGeometric()));
        ts2 = P.triples(P.prefixPermutations(EP.withNull(EP.naturalIntegers())), finiteArgs, finiteArgs);
        for (Triple<Iterable<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts2)) {
            List<Triple<Integer, Integer, Integer>> triplesList = toList(
                    take(TINY_LIMIT, EP.triplesLex(t.a, t.b, t.c))
            );
            assertTrue(t, unique(triplesList));
        }
    }

    private static void propertiesQuadruplesLex() {
        initialize("quadruplesLex(Iterable<A>, List<B>, List<C>, List<D>)");
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(LIMIT, qs)) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruplesLex(q.a, q.b, q.c, q.d);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()));
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!q.a.isEmpty() && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty()) {
                    assertEquals(q, head(quadruplesList), new Quadruple<>(head(q.a), head(q.b), head(q.c), head(q.d)));
                    assertEquals(q, last(quadruplesList), new Quadruple<>(last(q.a), last(q.b), last(q.c), last(q.d)));
                }
                assertEquals(q, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quadruplesList)));
            }
        }

        qs = P.quadruples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(LIMIT, qs)) {
            BigInteger quadruplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()));
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList =
                        toList(EP.quadruplesLex(q.a, q.b, q.c, q.d));
                assertTrue(q, unique(quadruplesList));
                Comparator<Quadruple<Integer, Integer, Integer, Integer>> comparator =
                        new Quadruple.QuadrupleComparator<>(
                                new ListBasedComparator<>(q.a),
                                new ListBasedComparator<>(q.b),
                                new ListBasedComparator<>(q.c),
                                new ListBasedComparator<>(q.d)
                        );
                assertTrue(q, increasing(comparator, quadruplesList));
            }
        }

        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs2 = P.quadruples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(LIMIT, qs2)) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruplesLex(q.a, q.b, q.c, q.d);
            testNoRemove(TINY_LIMIT, quadruples);
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(take(TINY_LIMIT, quadruples));
            if (!isEmpty(q.a) && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty()) {
                assertEquals(q, head(quadruplesList), new Quadruple<>(head(q.a), head(q.b), head(q.c), head(q.d)));
            }
            assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quadruplesList)));
        }

        finiteArgs = P.distinctLists(P.withNull(P.integersGeometric()));
        qs2 = P.quadruples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(LIMIT, qs2)) {
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(
                    take(TINY_LIMIT, EP.quadruplesLex(q.a, q.b, q.c, q.d))
            );
            assertTrue(q, unique(quadruplesList));
        }
    }

    private static void propertiesQuintuplesLex() {
        initialize("quintuplesLex(Iterable<A>, List<B>, List<C>, List<D>, List<E>)");
        Iterable<Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs = P.quintuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(LIMIT, qs)) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples =
                    EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()))
                    .multiply(BigInteger.valueOf(q.e.size()));
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!q.a.isEmpty() && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty() && !q.e.isEmpty()) {
                    assertEquals(
                            q,
                            head(quintuplesList),
                            new Quintuple<>(head(q.a), head(q.b), head(q.c), head(q.d), head(q.e))
                    );
                    assertEquals(
                            q,
                            last(quintuplesList),
                            new Quintuple<>(last(q.a), last(q.b), last(q.c), last(q.d), last(q.e))
                    );
                }
                assertEquals(q, quintuplesList.size(), quintuplesLength.intValueExact());
                assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.e), map(r -> r.e, quintuplesList)));
            }
        }

        qs = P.quintuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(LIMIT, qs)) {
            BigInteger quintuplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()))
                    .multiply(BigInteger.valueOf(q.e.size()));
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e));
                assertTrue(q, unique(quintuplesList));
                Comparator<Quintuple<Integer, Integer, Integer, Integer, Integer>> comparator =
                        new Quintuple.QuintupleComparator<>(
                                new ListBasedComparator<>(q.a),
                                new ListBasedComparator<>(q.b),
                                new ListBasedComparator<>(q.c),
                                new ListBasedComparator<>(q.d),
                                new ListBasedComparator<>(q.e)
                        );
                assertTrue(q, increasing(comparator, quintuplesList));
            }
        }

        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Quintuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs2 = P.quintuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quintuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(LIMIT, qs2)) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples =
                    EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e);
            testNoRemove(TINY_LIMIT, quintuples);
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                    toList(take(TINY_LIMIT, quintuples));
            if (!isEmpty(q.a) && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty() && !q.e.isEmpty()) {
                assertEquals(
                        q,
                        head(quintuplesList),
                        new Quintuple<>(head(q.a), head(q.b), head(q.c), head(q.d), head(q.e))
                );
            }
            assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.e), map(r -> r.e, quintuplesList)));
        }

        finiteArgs = P.distinctLists(P.withNull(P.integersGeometric()));
        qs2 = P.quintuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quintuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(LIMIT, qs2)) {
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(
                    take(TINY_LIMIT, EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e))
            );
            assertTrue(q, unique(quintuplesList));
        }
    }

    private static void propertiesSextuplesLex() {
        initialize("sexuplesLex(Iterable<A>, List<B>, List<C>, List<D>, List<E>, List<F>)");
        Iterable<Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.sextuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss)) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()));
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (
                        !s.a.isEmpty() &&
                        !s.b.isEmpty() &&
                        !s.c.isEmpty() &&
                        !s.d.isEmpty() &&
                        !s.e.isEmpty() &&
                        !s.f.isEmpty()
                ) {
                    assertEquals(
                            s,
                            head(sextuplesList),
                            new Sextuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f))
                    );
                    assertEquals(
                            s,
                            last(sextuplesList),
                            new Sextuple<>(last(s.a), last(s.b), last(s.c), last(s.d), last(s.e), last(s.f))
                    );
                }
                assertEquals(s, sextuplesList.size(), sextuplesLength.intValueExact());
                assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, sextuplesList)));
            }
        }

        ss = P.sextuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss)) {
            BigInteger sextuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()));
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f));
                assertTrue(s, unique(sextuplesList));
                Comparator<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> comparator =
                        new Sextuple.SextupleComparator<>(
                                new ListBasedComparator<>(s.a),
                                new ListBasedComparator<>(s.b),
                                new ListBasedComparator<>(s.c),
                                new ListBasedComparator<>(s.d),
                                new ListBasedComparator<>(s.e),
                                new ListBasedComparator<>(s.f)
                        );
                assertTrue(s, increasing(comparator, sextuplesList));
            }
        }

        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Sextuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss2 = P.sextuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Sextuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss2)) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f);
            testNoRemove(TINY_LIMIT, sextuples);
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                    toList(take(TINY_LIMIT, sextuples));
            if (
                    !isEmpty(s.a) &&
                    !s.b.isEmpty() &&
                    !s.c.isEmpty() &&
                    !s.d.isEmpty() &&
                    !s.e.isEmpty() &&
                    !s.f.isEmpty()
            ) {
                assertEquals(
                        s,
                        head(sextuplesList),
                        new Sextuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f))
                );
            }
            assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, sextuplesList)));
        }

        finiteArgs = P.distinctLists(P.withNull(P.integersGeometric()));
        ss2 = P.sextuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Sextuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss2)) {
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(
                    take(TINY_LIMIT, EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f))
            );
            assertTrue(s, unique(sextuplesList));
        }
    }

    private static void propertiesSeptuplesLex() {
        initialize("septuplesLex(Iterable<A>, List<B>, List<C>, List<D>, List<E>, List<F>, List<G>)");
        Iterable<Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.septuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss)) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()))
                    .multiply(BigInteger.valueOf(s.g.size()));
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (
                        !s.a.isEmpty() &&
                        !s.b.isEmpty() &&
                        !s.c.isEmpty() &&
                        !s.d.isEmpty() &&
                        !s.e.isEmpty() &&
                        !s.f.isEmpty() &&
                        !s.g.isEmpty()
                ) {
                    assertEquals(
                            s,
                            head(septuplesList),
                            new Septuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f), head(s.g))
                    );
                    assertEquals(
                            s,
                            last(septuplesList),
                            new Septuple<>(last(s.a), last(s.b), last(s.c), last(s.d), last(s.e), last(s.f), last(s.g))
                    );
                }
                assertEquals(s, septuplesList.size(), septuplesLength.intValueExact());
                assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, septuplesList)));
            }
        }

        ss = P.septuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss)) {
            BigInteger septuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()))
                    .multiply(BigInteger.valueOf(s.g.size()));
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g));
                assertTrue(s, unique(septuplesList));
                Comparator<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> comparator =
                        new Septuple.SeptupleComparator<>(
                                new ListBasedComparator<>(s.a),
                                new ListBasedComparator<>(s.b),
                                new ListBasedComparator<>(s.c),
                                new ListBasedComparator<>(s.d),
                                new ListBasedComparator<>(s.e),
                                new ListBasedComparator<>(s.f),
                                new ListBasedComparator<>(s.g)
                        );
                assertTrue(s, increasing(comparator, septuplesList));
            }
        }

        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Septuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss2 = P.septuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Septuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss2)) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
            testNoRemove(TINY_LIMIT, septuples);
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                    toList(take(TINY_LIMIT, septuples));
            if (
                    !isEmpty(s.a) &&
                    !s.b.isEmpty() &&
                    !s.c.isEmpty() &&
                    !s.d.isEmpty() &&
                    !s.e.isEmpty() &&
                    !s.f.isEmpty() &&
                    !s.g.isEmpty()
            ) {
                assertEquals(
                        s,
                        head(septuplesList),
                        new Septuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f), head(s.g))
                );
            }
            assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.g), map(t -> t.g, septuplesList)));
        }

        finiteArgs = P.distinctLists(P.withNull(P.integersGeometric()));
        ss2 = P.septuples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Septuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(LIMIT, ss2)) {
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList = toList(
                    take(TINY_LIMIT, EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g))
            );
            assertTrue(s, unique(septuplesList));
        }
    }

    private static @NotNull List<String> stringsLex_int_String_alt(
            int size, @NotNull String s
    ) {
        int sLength = s.length();
        switch (sLength) {
            case 0:
                return size == 0 ? Collections.singletonList("") : Collections.emptyList();
            case 1:
                return Collections.singletonList(replicate(size, s.charAt(0)));
            default:
                BigInteger base = BigInteger.valueOf(sLength);
                return toList(
                        map(
                                i -> charsToString(
                                        map(
                                                d -> s.charAt(d.intValueExact()),
                                                IntegerUtils.bigEndianDigitsPadded(size, base, i)
                                        )
                                ),
                                range(BigInteger.ZERO, base.pow(size).subtract(BigInteger.ONE))
                        )
                );
        }
    }

    private static void propertiesStringsLex_int_String() {
        initialize("stringsLex(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = BigInteger.valueOf(p.a.length()).pow(p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                assertEquals(p, stringsLex_int_String_alt(p.b, p.a), stringsList);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(stringsList), replicate(p.b, head(p.a)));
                    assertEquals(p, last(stringsList), replicate(p.b, last(p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> s.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctStrings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = BigInteger.valueOf(p.a.length()).pow(p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringsLex(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(
                        p,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(toList(p.a))),
                                map(IterableUtils::toList, stringsList)
                        )
                );
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringsLex(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.stringsLex(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringsLex(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void compareImplementationsStringsLex_int_String() {
        Map<String, Function<Pair<String, Integer>, List<String>>> functions = new LinkedHashMap<>();
        functions.put("alt", p -> stringsLex_int_String_alt(p.b, p.a));
        functions.put("standard", p -> toList(EP.stringsLex(p.b, p.a)));
        Iterable<Pair<String, Integer>> ps = filterInfinite(
                p -> lt(BigInteger.valueOf(p.a.length()).pow(p.b), BigInteger.valueOf(LIMIT)),
                P.pairsLogarithmicOrder(
                        P.withScale(4).strings(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        compareImplementations("stringsLex(int, String)", take(LIMIT, ps), functions);
    }

    private static void propertiesListsShortlex() {
        initialize("listsShortlex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> lists = EP.listsShortlex(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.listsShortlex(xs)));
            assertTrue(xs, unique(listsList));
            assertTrue(
                    xs,
                    increasing(
                            new ShortlexComparator<>(new ListBasedComparator<>(xs)),
                            map(ys -> ((Iterable<Integer>) ys), listsList)
                    )
            );
        }
    }

    private static void propertiesStringsShortlex() {
        initialize("stringsShortlex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringsShortlex(s);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(s, head(stringsList), "");
            assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringsShortlex(s)));
            assertTrue(s, unique(stringsList));
            assertTrue(s, increasing(new StringShortlexComparator(new ListBasedComparator<>(toList(s))), stringsList));
        }
    }

    private static void propertiesListsShortlexAtLeast() {
        initialize("listsShortlexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.listsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.listsShortlexAtLeast(p.b, p.a)));
            assertTrue(p, unique(listsList));
            assertTrue(
                    p,
                    increasing(
                            new ShortlexComparator<>(new ListBasedComparator<>(p.a)),
                            map(ys -> ((Iterable<Integer>) ys), listsList)
                    )
            );
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.listsShortlexAtLeast(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.listsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringsShortlexAtLeast() {
        initialize("stringsShortlexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(stringsList), replicate(p.b, head(p.a)));
            }
            assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
            assertTrue(p, all(s -> s.length() >= p.b, stringsList));
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringsShortlexAtLeast(p.b, p.a)));
            assertTrue(p, unique(stringsList));
            assertTrue(
                    p,
                    increasing(new StringShortlexComparator(new ListBasedComparator<>(toList(p.a))), stringsList)
            );
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringsShortlexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesLists_int_Iterable() {
        initialize("lists(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.lists(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = BigInteger.valueOf(p.a.size()).pow(p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
                    assertEquals(p, last(listsList), toList(replicate(p.b, last(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = BigInteger.valueOf(p.a.size()).pow(p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(p, unique(toList(EP.lists(p.b, p.a))));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.lists(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            assertTrue(p, unique(listsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.lists(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> xss = EP.lists(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.lists(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesPairs_Iterable_Iterable() {
        initialize("pairs(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairs(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = BigInteger.valueOf(p.a.size()).multiply(BigInteger.valueOf(p.b.size()));
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!p.a.isEmpty() && !p.b.isEmpty()) {
                    assertEquals(p, head(pairsList), new Pair<>(head(p.a), head(p.b)));
                    assertEquals(p, last(pairsList), new Pair<>(last(p.a), last(p.b)));
                }
                assertEquals(p, pairsList.size(), pairsLength.intValueExact());
                assertTrue(p, all(i -> elem(i, p.a), map(q -> q.a, pairsList)));
                assertTrue(p, all(i -> elem(i, p.b), map(q -> q.b, pairsList)));
            }
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            BigInteger pairsLength = BigInteger.valueOf(p.a.size()).multiply(BigInteger.valueOf(p.b.size()));
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.pairs(p.a, p.b));
                assertTrue(p, unique(pairsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairs(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            if (!isEmpty(p.a) && !isEmpty(p.b)) {
                assertEquals(p, head(pairsList), new Pair<>(head(p.a), head(p.b)));
            }
            assertTrue(p, all(i -> elem(i, p.a), map(q -> q.a, pairsList)));
            assertTrue(p, all(i -> elem(i, p.b), map(q -> q.b, pairsList)));
            assertTrue(p, unique(pairsList));
        }
    }

    private static @NotNull List<Pair<Integer, Integer>> pairs_Iterable_simplest(@NotNull List<Integer> xs) {
        return toList(EP.pairs(xs, xs));
    }

    private static void propertiesPairs_Iterable() {
        initialize("pairs(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = BigInteger.valueOf(xs.size()).pow(2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                assertEquals(xs, pairsList, pairs_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(xs, head(pairsList), new Pair<>(head(xs), head(xs)));
                    assertEquals(xs, last(pairsList), new Pair<>(last(xs), last(xs)));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(p -> p.a, pairsList)));
                assertTrue(xs, all(i -> elem(i, xs), map(p -> p.b, pairsList)));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger pairsLength = BigInteger.valueOf(xs.size()).pow(2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.pairs(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.pairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(xs, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p : pairsList) {
                assertTrue(xs, elem(p.a, xs));
                assertTrue(xs, elem(p.b, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Pair<Integer, Integer>>> ps3 = zip(
                    P.naturalIntegers(),
                    EP.pairs(P.naturalIntegers())
            );
            for (Pair<Integer, Pair<Integer, Integer>> p : take(LIMIT, ps3)) {
                int root = MathUtils.ceilingRoot(IntegerUtils.TWO, BigInteger.valueOf(p.a)).intValueExact() * 2 + 1;
                assertTrue(p, p.b.a < root);
                assertTrue(p, p.b.b < root);
            }
        }
    }

    private static void compareImplementationsPairs_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairs_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairs(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(2), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("pairs(Iterable<T>)", take(LIMIT, iss), functions);
    }

    private static void propertiesTriples_Iterable_Iterable_Iterable() {
        initialize("triples(Iterable<A>, Iterable<B>, Iterable<C>)");
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts)) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triples(t.a, t.b, t.c);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = BigInteger.valueOf(t.a.size())
                    .multiply(BigInteger.valueOf(t.b.size()))
                    .multiply(BigInteger.valueOf(t.c.size()));
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!t.a.isEmpty() && !t.b.isEmpty() && !t.c.isEmpty()) {
                    assertEquals(t, head(triplesList), new Triple<>(head(t.a), head(t.b), head(t.c)));
                    assertEquals(t, last(triplesList), new Triple<>(last(t.a), last(t.b), last(t.c)));
                }
                assertEquals(t, triplesList.size(), triplesLength.intValueExact());
                assertTrue(t, all(i -> elem(i, t.a), map(u -> u.a, triplesList)));
                assertTrue(t, all(i -> elem(i, t.b), map(u -> u.b, triplesList)));
                assertTrue(t, all(i -> elem(i, t.c), map(u -> u.c, triplesList)));
            }
        }

        ts = P.triples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(LIMIT, ts)) {
            BigInteger triplesLength = BigInteger.valueOf(t.a.size())
                    .multiply(BigInteger.valueOf(t.b.size()))
                    .multiply(BigInteger.valueOf(t.c.size()));
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.triples(t.a, t.b, t.c));
                assertTrue(t, unique(triplesList));
            }
        }

        Iterable<Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> ts2 = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>> t : take(LIMIT, ts2)) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triples(t.a, t.b, t.c);
            testNoRemove(TINY_LIMIT, triples);
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            if (!isEmpty(t.a) && !isEmpty(t.b) && !isEmpty(t.c)) {
                assertEquals(t, head(triplesList), new Triple<>(head(t.a), head(t.b), head(t.c)));
            }
            assertTrue(t, all(i -> elem(i, t.a), map(u -> u.a, triplesList)));
            assertTrue(t, all(i -> elem(i, t.b), map(u -> u.b, triplesList)));
            assertTrue(t, all(i -> elem(i, t.c), map(u -> u.c, triplesList)));
            assertTrue(t, unique(triplesList));
        }
    }

    private static @NotNull List<Triple<Integer, Integer, Integer>> triples_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.triples(xs, xs, xs));
    }

    private static void propertiesTriples_Iterable() {
        initialize("triples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triples(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = BigInteger.valueOf(xs.size()).pow(3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                assertEquals(xs, triplesList, triples_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(xs, head(triplesList), new Triple<>(head(xs), head(xs), head(xs)));
                    assertEquals(xs, last(triplesList), new Triple<>(last(xs), last(xs), last(xs)));
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(t -> t.a, triplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(t -> t.b, triplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(t -> t.c, triplesList)));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger triplesLength = BigInteger.valueOf(xs.size()).pow(3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.triples(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.triples(xs);
            testNoRemove(TINY_LIMIT, triples);
            assertTrue(xs, unique(take(TINY_LIMIT, triples)));
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            for (Triple<Integer, Integer, Integer> t : triplesList) {
                assertTrue(xs, elem(t.a, xs));
                assertTrue(xs, elem(t.b, xs));
                assertTrue(xs, elem(t.c, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Triple<Integer, Integer, Integer>>> ts = zip(
                    P.naturalIntegers(),
                    EP.triples(P.naturalIntegers())
            );
            for (Pair<Integer, Triple<Integer, Integer, Integer>> t : take(LIMIT, ts)) {
                int root = MathUtils.ceilingRoot(BigInteger.valueOf(3), BigInteger.valueOf(t.a))
                        .intValueExact() * 2 + 1;
                assertTrue(t, t.b.a < root);
                assertTrue(t, t.b.b < root);
                assertTrue(t, t.b.c < root);
            }
        }
    }

    private static void compareImplementationsTriples_Iterable() {
        Map<
                String,
                Function<List<Integer>, List<Triple<Integer, Integer, Integer>>>
        > functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::triples_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.triples(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(3), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("triples(Iterable<T>)", take(LIMIT, iss), functions);
    }

    private static void propertiesQuadruples_Iterable_Iterable_Iterable_Iterable() {
        initialize("quadruples(Iterable<A>, Iterable<B>, Iterable<C>, Iterable<D>)");
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(SMALL_LIMIT, qs)) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruples(q.a, q.b, q.c, q.d);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()));
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!q.a.isEmpty() && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty()) {
                    assertEquals(q, head(quadruplesList), new Quadruple<>(head(q.a), head(q.b), head(q.c), head(q.d)));
                    assertEquals(q, last(quadruplesList), new Quadruple<>(last(q.a), last(q.b), last(q.c), last(q.d)));
                }
                assertEquals(q, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quadruplesList)));
                assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quadruplesList)));
            }
        }

        qs = P.quadruples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(SMALL_LIMIT, qs)) {
            BigInteger quadruplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()));
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList =
                        toList(EP.quadruples(q.a, q.b, q.c, q.d));
                assertTrue(q, unique(quadruplesList));
            }
        }

        Iterable<Quadruple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> qs2 =
                P.quadruples(P.prefixPermutations(EP.withNull(EP.naturalIntegers())));
        for (Quadruple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > q : take(LIMIT, qs2)) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruples(q.a, q.b, q.c, q.d);
            testNoRemove(TINY_LIMIT, quadruples);
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(take(TINY_LIMIT, quadruples));
            if (!isEmpty(q.a) && !isEmpty(q.b) && !isEmpty(q.c) && !isEmpty(q.d)) {
                assertEquals(q, head(quadruplesList), new Quadruple<>(head(q.a), head(q.b), head(q.c), head(q.d)));
            }
            assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quadruplesList)));
            assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quadruplesList)));
            assertTrue(q, unique(quadruplesList));
        }
    }

    private static @NotNull List<Quadruple<Integer, Integer, Integer, Integer>> quadruples_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.quadruples(xs, xs, xs, xs));
    }

    private static void propertiesQuadruples_Iterable() {
        initialize("quadruples(Iterable<T>)");
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = BigInteger.valueOf(xs.size()).pow(4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                assertEquals(xs, quadruplesList, quadruples_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(xs, head(quadruplesList), new Quadruple<>(head(xs), head(xs), head(xs), head(xs)));
                    assertEquals(xs, last(quadruplesList), new Quadruple<>(last(xs), last(xs), last(xs), last(xs)));
                }
                assertEquals(xs, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.a, quadruplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.b, quadruplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.c, quadruplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.d, quadruplesList)));
            }
        }

        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger quadruplesLength = BigInteger.valueOf(xs.size()).pow(4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.quadruples(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.quadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            assertTrue(xs, unique(take(TINY_LIMIT, quadruples)));
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(take(TINY_LIMIT, quadruples));
            for (Quadruple<Integer, Integer, Integer, Integer> q : quadruplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Quadruple<Integer, Integer, Integer, Integer>>> qs = zip(
                    P.naturalIntegers(),
                    EP.quadruples(P.naturalIntegers())
            );
            for (Pair<Integer, Quadruple<Integer, Integer, Integer, Integer>> q : take(LIMIT, qs)) {
                int root = MathUtils.ceilingRoot(BigInteger.valueOf(4), BigInteger.valueOf(q.a))
                        .intValueExact() * 2 + 1;
                assertTrue(q, q.b.a < root);
                assertTrue(q, q.b.b < root);
                assertTrue(q, q.b.c < root);
                assertTrue(q, q.b.d < root);
            }
        }
    }

    private static void compareImplementationsQuadruples_Iterable() {
        Map<
                String,
                Function<List<Integer>, List<Quadruple<Integer, Integer, Integer, Integer>>>
        > functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::quadruples_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.quadruples(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(4), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("quadruples(Iterable<T>)", take(SMALL_LIMIT, iss), functions);
    }

    private static void propertiesQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable() {
        initialize("quintuples(Iterable<A>, Iterable<B>, Iterable<C>, Iterable<D>, Iterable<E>)");
        Iterable<Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs = P.quintuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(SMALL_LIMIT, qs)) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples =
                    EP.quintuples(q.a, q.b, q.c, q.d, q.e);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()))
                    .multiply(BigInteger.valueOf(q.e.size()));
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!q.a.isEmpty() && !q.b.isEmpty() && !q.c.isEmpty() && !q.d.isEmpty() && !q.e.isEmpty()) {
                    assertEquals(
                            q,
                            head(quintuplesList),
                            new Quintuple<>(head(q.a), head(q.b), head(q.c), head(q.d), head(q.e))
                    );
                    assertEquals(
                            q,
                            last(quintuplesList),
                            new Quintuple<>(last(q.a), last(q.b), last(q.c), last(q.d), last(q.e))
                    );
                }
                assertEquals(q, quintuplesList.size(), quintuplesLength.intValueExact());
                assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quintuplesList)));
                assertTrue(q, all(i -> elem(i, q.e), map(r -> r.e, quintuplesList)));
            }
        }

        qs = P.quintuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(SMALL_LIMIT, qs)) {
            BigInteger quintuplesLength = BigInteger.valueOf(q.a.size())
                    .multiply(BigInteger.valueOf(q.b.size()))
                    .multiply(BigInteger.valueOf(q.c.size()))
                    .multiply(BigInteger.valueOf(q.d.size()))
                    .multiply(BigInteger.valueOf(q.e.size()));
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.quintuples(q.a, q.b, q.c, q.d, q.e));
                assertTrue(q, unique(quintuplesList));
            }
        }

        Iterable<Quintuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> qs2 = P.quintuples(P.prefixPermutations(EP.withNull(EP.naturalIntegers())));
        for (Quintuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > q : take(LIMIT, qs2)) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples =
                    EP.quintuples(q.a, q.b, q.c, q.d, q.e);
            testNoRemove(TINY_LIMIT, quintuples);
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                    toList(take(TINY_LIMIT, quintuples));
            if (!isEmpty(q.a) && !isEmpty(q.b) && !isEmpty(q.c) && !isEmpty(q.d) && !isEmpty(q.e)) {
                assertEquals(
                        q,
                        head(quintuplesList),
                        new Quintuple<>(head(q.a), head(q.b), head(q.c), head(q.d), head(q.e))
                );
            }
            assertTrue(q, all(i -> elem(i, q.a), map(r -> r.a, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.b), map(r -> r.b, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.c), map(r -> r.c, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.d), map(r -> r.d, quintuplesList)));
            assertTrue(q, all(i -> elem(i, q.e), map(r -> r.e, quintuplesList)));
            assertTrue(q, unique(quintuplesList));
        }
    }

    private static @NotNull List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.quintuples(xs, xs, xs, xs, xs));
    }

    private static void propertiesQuintuples_Iterable() {
        initialize("quintuples(Iterable<T>)");
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.quintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = BigInteger.valueOf(xs.size()).pow(5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                assertEquals(xs, quintuplesList, quintuples_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(
                            xs,
                            head(quintuplesList),
                            new Quintuple<>(head(xs), head(xs), head(xs), head(xs), head(xs))
                    );
                    assertEquals(
                            xs,
                            last(quintuplesList),
                            new Quintuple<>(last(xs), last(xs), last(xs), last(xs), last(xs))
                    );
                }
                assertEquals(xs, quintuplesList.size(), quintuplesLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.a, quintuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.b, quintuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.c, quintuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.d, quintuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.e, quintuplesList)));
            }
        }

        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger quintuplesLength = BigInteger.valueOf(xs.size()).pow(5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.quintuples(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.quintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            assertTrue(xs, unique(take(TINY_LIMIT, quintuples)));
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                    toList(take(TINY_LIMIT, quintuples));
            for (Quintuple<Integer, Integer, Integer, Integer, Integer> q : quintuplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
                assertTrue(xs, elem(q.e, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Quintuple<Integer, Integer, Integer, Integer, Integer>>> qs = zip(
                    P.naturalIntegers(),
                    EP.quintuples(P.naturalIntegers())
            );
            for (Pair<Integer, Quintuple<Integer, Integer, Integer, Integer, Integer>> q : take(LIMIT, qs)) {
                int root = MathUtils.ceilingRoot(BigInteger.valueOf(5), BigInteger.valueOf(q.a))
                        .intValueExact() * 2 + 1;
                assertTrue(q, q.b.a < root);
                assertTrue(q, q.b.b < root);
                assertTrue(q, q.b.c < root);
                assertTrue(q, q.b.d < root);
                assertTrue(q, q.b.e < root);
            }
        }
    }

    private static void compareImplementationsQuintuples_Iterable() {
        Map<
                String,
                Function<List<Integer>, List<Quintuple<Integer, Integer, Integer, Integer, Integer>>>
        > functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::quintuples_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.quintuples(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(5), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("quintuples(Iterable<T>)", take(SMALL_LIMIT, iss), functions);
    }

    private static void propertiesSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        initialize("sextuples(Iterable<A>, Iterable<B>, Iterable<C>, Iterable<D>, Iterable<E>, Iterable<F>)");
        Iterable<Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.sextuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(SMALL_LIMIT, ss)) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()));
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (
                        !s.a.isEmpty() &&
                        !s.b.isEmpty() &&
                        !s.c.isEmpty() &&
                        !s.d.isEmpty() &&
                        !s.e.isEmpty() &&
                        !s.f.isEmpty()
                ) {
                    assertEquals(
                            s,
                            head(sextuplesList),
                            new Sextuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f))
                    );
                    assertEquals(
                            s,
                            last(sextuplesList),
                            new Sextuple<>(last(s.a), last(s.b), last(s.c), last(s.d), last(s.e), last(s.f))
                    );
                }
                assertEquals(s, sextuplesList.size(), sextuplesLength.intValueExact());
                assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, sextuplesList)));
                assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, sextuplesList)));
            }
        }

        ss = P.sextuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(SMALL_LIMIT, ss)) {
            BigInteger sextuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()));
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f));
                assertTrue(s, unique(sextuplesList));
            }
        }

        Iterable<Sextuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> ss2 = P.sextuples(P.prefixPermutations(EP.withNull(EP.naturalIntegers())));
        for (Sextuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > s : take(LIMIT, ss2)) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f);
            testNoRemove(TINY_LIMIT, sextuples);
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                    toList(take(TINY_LIMIT, sextuples));
            if (!isEmpty(s.a) && !isEmpty(s.b) && !isEmpty(s.c) && !isEmpty(s.d) && !isEmpty(s.e) && !isEmpty(s.f)) {
                assertEquals(
                        s,
                        head(sextuplesList),
                        new Sextuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f))
                );
            }
            assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, sextuplesList)));
            assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, sextuplesList)));
            assertTrue(s, unique(sextuplesList));
        }
    }

    private static @NotNull List<Sextuple<
            Integer,
            Integer,
            Integer,
            Integer,
            Integer,
            Integer
    >> sextuples_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.sextuples(xs, xs, xs, xs, xs, xs));
    }

    private static void propertiesSextuples_Iterable() {
        initialize("sextuples(Iterable<T>)");
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples = EP.sextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = BigInteger.valueOf(xs.size()).pow(6);
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                assertEquals(xs, sextuplesList, sextuples_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(
                            xs,
                            head(sextuplesList),
                            new Sextuple<>(head(xs), head(xs), head(xs), head(xs), head(xs), head(xs))
                    );
                    assertEquals(
                            xs,
                            last(sextuplesList),
                            new Sextuple<>(last(xs), last(xs), last(xs), last(xs), last(xs), last(xs))
                    );
                }
                assertEquals(xs, sextuplesList.size(), sextuplesLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.a, sextuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.b, sextuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.c, sextuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.d, sextuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.e, sextuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.f, sextuplesList)));
            }
        }

        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger sextuplesLength = BigInteger.valueOf(xs.size()).pow(6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.sextuples(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples = EP.sextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            assertTrue(xs, unique(take(TINY_LIMIT, sextuples)));
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                    toList(take(TINY_LIMIT, sextuples));
            for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s : sextuplesList) {
                assertTrue(xs, elem(s.a, xs));
                assertTrue(xs, elem(s.b, xs));
                assertTrue(xs, elem(s.c, xs));
                assertTrue(xs, elem(s.d, xs));
                assertTrue(xs, elem(s.e, xs));
                assertTrue(xs, elem(s.f, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>>> ss = zip(
                    P.naturalIntegers(),
                    EP.sextuples(P.naturalIntegers())
            );
            for (Pair<Integer, Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> s : take(LIMIT, ss)) {
                int root = MathUtils.ceilingRoot(BigInteger.valueOf(6), BigInteger.valueOf(s.a))
                        .intValueExact() * 2 + 1;
                assertTrue(s, s.b.a < root);
                assertTrue(s, s.b.b < root);
                assertTrue(s, s.b.c < root);
                assertTrue(s, s.b.d < root);
                assertTrue(s, s.b.e < root);
                assertTrue(s, s.b.f < root);
            }
        }
    }

    private static void compareImplementationsSextuples_Iterable() {
        Map<
                String,
                Function<List<Integer>, List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>>>
        > functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::sextuples_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.sextuples(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(6), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("sextuples(Iterable<T>)", take(SMALL_LIMIT, iss), functions);
    }

    private static void propertiesSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
        initialize(
                "septuples(Iterable<A>, Iterable<B>, Iterable<C>, Iterable<D>, Iterable<E>, Iterable<F>, Iterable<G>)"
        );
        Iterable<Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.septuples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(SMALL_LIMIT, ss)) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()))
                    .multiply(BigInteger.valueOf(s.g.size()));
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (
                        !s.a.isEmpty() &&
                        !s.b.isEmpty() &&
                        !s.c.isEmpty() &&
                        !s.d.isEmpty() &&
                        !s.e.isEmpty() &&
                        !s.f.isEmpty() &&
                        !s.g.isEmpty()
                ) {
                    assertEquals(
                            s,
                            head(septuplesList),
                            new Septuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f), head(s.g))
                    );
                    assertEquals(
                            s,
                            last(septuplesList),
                            new Septuple<>(last(s.a), last(s.b), last(s.c), last(s.d), last(s.e), last(s.f), last(s.g))
                    );
                }
                assertEquals(s, septuplesList.size(), septuplesLength.intValueExact());
                assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, septuplesList)));
                assertTrue(s, all(i -> elem(i, s.g), map(t -> t.g, septuplesList)));
            }
        }

        ss = P.septuples(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(SMALL_LIMIT, ss)) {
            BigInteger septuplesLength = BigInteger.valueOf(s.a.size())
                    .multiply(BigInteger.valueOf(s.b.size()))
                    .multiply(BigInteger.valueOf(s.c.size()))
                    .multiply(BigInteger.valueOf(s.d.size()))
                    .multiply(BigInteger.valueOf(s.e.size()))
                    .multiply(BigInteger.valueOf(s.f.size()))
                    .multiply(BigInteger.valueOf(s.g.size()));
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g));
                assertTrue(s, unique(septuplesList));
            }
        }

        Iterable<Septuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> ss2 = P.septuples(P.prefixPermutations(EP.withNull(EP.naturalIntegers())));
        for (Septuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > s : take(LIMIT, ss2)) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g);
            testNoRemove(TINY_LIMIT, septuples);
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                    toList(take(TINY_LIMIT, septuples));
            if (
                    !isEmpty(s.a) &&
                    !isEmpty(s.b) &&
                    !isEmpty(s.c) &&
                    !isEmpty(s.d) &&
                    !isEmpty(s.e) &&
                    !isEmpty(s.f) &&
                    !isEmpty(s.g)
            ) {
                assertEquals(
                        s,
                        head(septuplesList),
                        new Septuple<>(head(s.a), head(s.b), head(s.c), head(s.d), head(s.e), head(s.f), head(s.g))
                );
            }
            assertTrue(s, all(i -> elem(i, s.a), map(t -> t.a, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.b), map(t -> t.b, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.c), map(t -> t.c, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.d), map(t -> t.d, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.e), map(t -> t.e, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.f), map(t -> t.f, septuplesList)));
            assertTrue(s, all(i -> elem(i, s.f), map(t -> t.g, septuplesList)));
            assertTrue(s, unique(septuplesList));
        }
    }

    private static @NotNull List<Septuple<
            Integer,
            Integer,
            Integer,
            Integer,
            Integer,
            Integer,
            Integer
    >> septuples_Iterable_simplest(
            @NotNull List<Integer> xs
    ) {
        return toList(EP.septuples(xs, xs, xs, xs, xs, xs, xs));
    }

    private static void propertiesSeptuples_Iterable() {
        initialize("septuples(Iterable<T>)");
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = BigInteger.valueOf(xs.size()).pow(7);
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                assertEquals(xs, septuplesList, septuples_Iterable_simplest(xs));
                if (!xs.isEmpty()) {
                    assertEquals(
                            xs,
                            head(septuplesList),
                            new Septuple<>(head(xs), head(xs), head(xs), head(xs), head(xs), head(xs), head(xs))
                    );
                    assertEquals(
                            xs,
                            last(septuplesList),
                            new Septuple<>(last(xs), last(xs), last(xs), last(xs), last(xs), last(xs), last(xs))
                    );
                }
                assertEquals(xs, septuplesList.size(), septuplesLength.intValueExact());
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.a, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.b, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.c, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.d, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.e, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.f, septuplesList)));
                assertTrue(xs, all(i -> elem(i, xs), map(q -> q.g, septuplesList)));
            }
        }

        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger septuplesLength = BigInteger.valueOf(xs.size()).pow(7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                assertTrue(xs, unique(EP.septuples(xs)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.septuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            assertTrue(xs, unique(take(TINY_LIMIT, septuples)));
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                    toList(take(TINY_LIMIT, septuples));
            for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s : septuplesList) {
                assertTrue(xs, elem(s.a, xs));
                assertTrue(xs, elem(s.b, xs));
                assertTrue(xs, elem(s.c, xs));
                assertTrue(xs, elem(s.d, xs));
                assertTrue(xs, elem(s.e, xs));
                assertTrue(xs, elem(s.f, xs));
                assertTrue(xs, elem(s.g, xs));
            }
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Pair<Integer, Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>> ss = zip(
                    P.naturalIntegers(),
                    EP.septuples(P.naturalIntegers())
            );
            for (Pair<
                    Integer,
                    Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>
            s : take(LIMIT, ss)) {
                int root = MathUtils.ceilingRoot(BigInteger.valueOf(7), BigInteger.valueOf(s.a))
                        .intValueExact() * 2 + 1;
                assertTrue(s, s.b.a < root);
                assertTrue(s, s.b.b < root);
                assertTrue(s, s.b.c < root);
                assertTrue(s, s.b.d < root);
                assertTrue(s, s.b.e < root);
                assertTrue(s, s.b.f < root);
                assertTrue(s, s.b.g < root);
            }
        }
    }

    private static void compareImplementationsSeptuples_Iterable() {
        Map<
                String,
                Function<List<Integer>, List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>>
        > functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::septuples_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.septuples(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(7), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("septuples(Iterable<T>)", take(SMALL_LIMIT, iss), functions);
    }

    private static void propertiesStrings_int_String() {
        initialize("strings(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.strings(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = BigInteger.valueOf(p.a.length()).pow(p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(stringsList), replicate(p.b, head(p.a)));
                    assertEquals(p, last(stringsList), replicate(p.b, last(p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> s.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctStrings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = BigInteger.valueOf(p.a.length()).pow(p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.strings(p.b, p.a));
                assertTrue(p, unique(stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.strings(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.strings(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.strings(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStrings_int() {
        initialize("strings(int)");
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.strings(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(replicate(i, head(EP.characters()))));
            assertTrue(i, all(s -> s.length() == i, stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(LIMIT, P.withScale(4).negativeIntegersGeometric())) {
            try {
                EP.strings(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesLists() {
        initialize("lists(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> lists = EP.lists(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.lists(xs)));
            assertTrue(xs, unique(listsList));
        }

        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<List<Integer>> lists = EP.lists(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            assertTrue(xs, unique(listsList));
        }
    }

    private static void propertiesStrings_String() {
        initialize("strings(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.strings(s);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(s, head(stringsList), "");
            assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.strings(s)));
            assertTrue(s, unique(stringsList));
        }
    }

    private static void propertiesStrings() {
        initializeConstant("strings()");
        biggerTest(EP, EP.strings(), s -> true);
    }

    private static void propertiesListsAtLeast() {
        initialize("listsAtLeast(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.listsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.listsShortlexAtLeast(p.b, p.a)));
            assertTrue(p, unique(listsList));
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.listsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            assertTrue(p, unique(listsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.listsAtLeast(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.listsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringsAtLeast_int_String() {
        initialize("stringsAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(stringsList), replicate(p.b, head(p.a)));
            }
            assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
            assertTrue(p, all(s -> s.length() >= p.b, stringsList));
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringsAtLeast(p.b, p.a)));
            assertTrue(p, unique(stringsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringsAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringsAtLeast_int() {
        initialize("stringsAtLeast(int)");
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.stringsAtLeast(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(replicate(i, head(EP.characters()))));
            assertTrue(i, all(s -> s.length() >= i, stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                EP.stringsAtLeast(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesDistinctListsLex_int_List() {
        initialize("distinctListsLex(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.distinctListsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!listsList.isEmpty()) {
                    assertEquals(p, head(listsList), toList(take(p.b, p.a)));
                    assertEquals(p, last(listsList), toList(take(p.b, reverse(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctListsLex(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, all(IterableUtils::unique, listsList));
                assertTrue(
                        p,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(p.a)),
                                map(ys -> ((Iterable<Integer>) ys), listsList)
                        )
                );
                BigInteger limit = BigInteger.valueOf(p.a.size()).pow(p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(filter(IterableUtils::unique, EP.listsLex(p.b, p.a)), listsList)
                    );
                }
            }
        }

        ps = filter(
                p -> p.a.size() < p.b,
                P.pairsLogarithmicOrder(
                        P.withScale(4).lists(P.withNull(P.integersGeometric())),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> xss = EP.distinctListsLex(p.b, p.a);
            testHasNext(xss);
            assertEquals(p, toList(xss), Collections.emptyList());
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> xss = EP.distinctListsLex(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger listsLength = MathUtils.factorial(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                Iterable<List<Integer>> xss = EP.distinctListsLex(xs.size(), xs);
                assertTrue(xs, IterableUtils.equal(xss, EP.permutationsFinite(xs)));
            }
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctListsLex(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesDistinctPairsLex() {
        initialize("distinctPairsLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.distinctPairsLex(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!pairsList.isEmpty()) {
                    assertEquals(xs, head(pairsList), new Pair<>(xs.get(0), xs.get(1)));
                    assertEquals(xs, last(pairsList), new Pair<>(xs.get(xs.size() - 1), xs.get(xs.size() - 2)));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(p -> elem(p.a, xs), pairsList));
                assertTrue(xs, all(p -> elem(p.b, xs), pairsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger pairsLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.distinctPairsLex(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(IterableUtils::unique, map(Pair::toList, pairsList)));
                assertTrue(
                        xs,
                        increasing(
                                new Pair.PairComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                pairsList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(2);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(p -> IterableUtils.unique(Pair.toList(p)), EP.pairsLex(xs, xs)),
                                    pairsList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctTriplesLex() {
        initialize("distinctTriplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.distinctTriplesLex(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!triplesList.isEmpty()) {
                    assertEquals(xs, head(triplesList), new Triple<>(xs.get(0), xs.get(1), xs.get(2)));
                    assertEquals(
                            xs,
                            last(triplesList),
                            new Triple<>(xs.get(xs.size() - 1), xs.get(xs.size() - 2), xs.get(xs.size() - 3))
                    );
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(t -> elem(t.a, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.b, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.c, xs), triplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger triplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.distinctTriplesLex(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Triple::toList, triplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Triple.TripleComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                triplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(3);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(t -> IterableUtils.unique(Triple.toList(t)), EP.triplesLex(xs, xs, xs)),
                                    triplesList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctQuadruplesLex() {
        initialize("distinctQuadruplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.distinctQuadruplesLex(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!quadruplesList.isEmpty()) {
                    assertEquals(
                            xs,
                            head(quadruplesList),
                            new Quadruple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3))
                    );
                    assertEquals(
                            xs,
                            last(quadruplesList),
                            new Quadruple<>(
                                    xs.get(xs.size() - 1),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 4)
                            )
                    );
                }
                assertEquals(xs, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(xs, all(q -> elem(q.a, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quadruplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger quadruplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList =
                        toList(EP.distinctQuadruplesLex(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Quadruple::toList, quadruplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quadruple.QuadrupleComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                quadruplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(4);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            q -> IterableUtils.unique(Quadruple.toList(q)),
                                            EP.quadruplesLex(xs, xs, xs, xs)
                                    ),
                                    quadruplesList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctQuintuplesLex() {
        initialize("distinctQuintuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.distinctQuintuplesLex(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!quintuplesList.isEmpty()) {
                    assertEquals(
                            xs,
                            head(quintuplesList),
                            new Quintuple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4))
                    );
                    assertEquals(
                            xs,
                            last(quintuplesList),
                            new Quintuple<>(
                                    xs.get(xs.size() - 1),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 5)
                            )
                    );
                }
                assertEquals(xs, quintuplesList.size(), quintuplesLength.intValueExact());
                assertTrue(xs, all(q -> elem(q.a, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.e, xs), quintuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger quintuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.distinctQuintuplesLex(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Quintuple::toList, quintuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quintuple.QuintupleComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                quintuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(5);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            q -> IterableUtils.unique(Quintuple.toList(q)),
                                            EP.quintuplesLex(xs, xs, xs, xs, xs)
                                    ),
                                    quintuplesList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctSextuplesLex() {
        initialize("distinctSextuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.distinctSextuplesLex(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (!sextuplesList.isEmpty()) {
                    assertEquals(
                            xs,
                            head(sextuplesList),
                            new Sextuple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5))
                    );
                    assertEquals(
                            xs,
                            last(sextuplesList),
                            new Sextuple<>(
                                    xs.get(xs.size() - 1),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 5),
                                    xs.get(xs.size() - 6)
                            )
                    );
                }
                assertEquals(xs, sextuplesList.size(), sextuplesLength.intValueExact());
                assertTrue(xs, all(s -> elem(s.a, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.b, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.c, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.d, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.e, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.f, xs), sextuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger sextuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.distinctSextuplesLex(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Sextuple::toList, sextuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Sextuple.SextupleComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                sextuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(6);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            s -> IterableUtils.unique(Sextuple.toList(s)),
                                            EP.sextuplesLex(xs, xs, xs, xs, xs, xs)
                                    ),
                                    sextuplesList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctSeptuplesLex() {
        initialize("distinctSeptuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.distinctSeptuplesLex(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (!septuplesList.isEmpty()) {
                    assertEquals(
                            xs,
                            head(septuplesList),
                            new Septuple<>(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5), xs.get(6))
                    );
                    assertEquals(
                            xs,
                            last(septuplesList),
                            new Septuple<>(
                                    xs.get(xs.size() - 1),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 5),
                                    xs.get(xs.size() - 6),
                                    xs.get(xs.size() - 7)
                            )
                    );
                }
                assertEquals(xs, septuplesList.size(), septuplesLength.intValueExact());
                assertTrue(xs, all(s -> elem(s.a, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.b, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.c, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.d, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.e, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.f, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.g, xs), septuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger septuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.distinctSeptuplesLex(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Septuple::toList, septuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Septuple.SeptupleComparator<>(
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs),
                                        new ListBasedComparator<>(xs)
                                ),
                                septuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(7);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            s -> IterableUtils.unique(Septuple.toList(s)),
                                            EP.septuplesLex(xs, xs, xs, xs, xs, xs, xs)
                                    ),
                                    septuplesList
                            )
                    );
                }
            }
        }
    }

    private static void propertiesDistinctStringsLex_int_String() {
        initialize("distinctStringsLex(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.distinctStringsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!stringsList.isEmpty()) {
                    assertEquals(p, head(stringsList), take(p.b, p.a));
                    assertEquals(p, last(stringsList), take(p.b, reverse(p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(xs -> xs.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.distinctStringsLex(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, all(IterableUtils::unique, stringsList));
                assertTrue(
                        p,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(toList(p.a))),
                                map(IterableUtils::toList, stringsList)
                        )
                );
                BigInteger limit = BigInteger.valueOf(p.a.length()).pow(p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(filter(IterableUtils::unique, EP.stringsLex(p.b, p.a)), stringsList)
                    );
                }
            }
        }

        ps = filter(
                p -> p.a.length() < p.b,
                P.pairsLogarithmicOrder(P.withScale(4).strings(), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> ss = EP.distinctStringsLex(p.b, p.a);
            testHasNext(ss);
            assertEquals(p, toList(ss), Collections.emptyList());
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.distinctStringsLex(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            BigInteger stringsLength = MathUtils.factorial(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                Iterable<String> ss = EP.distinctStringsLex(s.length(), s);
                assertTrue(s, IterableUtils.equal(ss, EP.stringPermutations(s)));
            }
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctStringsLex(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesDistinctListsLex_List() {
        initialize("distinctListsLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> lists = EP.distinctListsLex(xs);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!listsList.isEmpty()) {
                    assertEquals(xs, head(listsList), Collections.emptyList());
                    assertEquals(xs, last(listsList), reverse(xs));
                }
                assertEquals(xs, listsList.size(), listsLength.intValueExact());
                assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctListsLex(xs));
                assertTrue(xs, unique(listsList));
                assertTrue(
                        xs,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(xs)),
                                map(ys -> ((Iterable<Integer>) ys), listsList)
                        )
                );
            }
        }
    }

    private static void propertiesDistinctStringsLex_String() {
        initialize("distinctStringsLex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.distinctStringsLex(s);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!stringsList.isEmpty()) {
                    assertEquals(s, head(stringsList), "");
                    assertEquals(s, last(stringsList), reverse(s));
                }
                assertEquals(s, stringsList.size(), stringsLength.intValueExact());
                assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
            }
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.distinctStringsLex(s));
                assertTrue(s, unique(stringsList));
                assertTrue(
                        s,
                        increasing(
                                new LexComparator<>(new ListBasedComparator<>(toList(s))),
                                map(IterableUtils::toList, stringsList)
                        )
                );
            }
        }
    }
}
