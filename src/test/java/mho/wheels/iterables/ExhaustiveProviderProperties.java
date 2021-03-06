package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.math.MathUtils;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.ordering.comparators.*;
import mho.wheels.structures.*;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class ExhaustiveProviderProperties extends TestProperties {
    public ExhaustiveProviderProperties() {
        super("ExhaustiveProvider");
    }

    @Override
    protected void testConstant() {
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
        propertiesDistinctStrings();
        propertiesStringBags();
        propertiesStringSubsets();
        propertiesRandomProvidersDefault();
        propertiesRandomProvidersDefaultSecondaryAndTertiaryScale();
        propertiesRandomProvidersDefaultTertiaryScale();
        propertiesRandomProviders();
    }

    @Override
    protected void testBothModes() {
        propertiesRangeUpIncreasing_byte();
        propertiesRangeUpIncreasing_short();
        propertiesRangeUpIncreasing_int();
        propertiesRangeUpIncreasing_long();
        propertiesRangeUpIncreasing_BigInteger();
        propertiesRangeUpIncreasing_char();
        propertiesRangeUpDecreasing_byte();
        propertiesRangeUpDecreasing_short();
        propertiesRangeUpDecreasing_int();
        propertiesRangeUpDecreasing_long();
        propertiesRangeUpDecreasing_char();
        propertiesRangeDownIncreasing_byte();
        propertiesRangeDownIncreasing_short();
        propertiesRangeDownIncreasing_int();
        propertiesRangeDownIncreasing_long();
        propertiesRangeDownIncreasing_char();
        propertiesRangeDownDecreasing_byte();
        propertiesRangeDownDecreasing_short();
        propertiesRangeDownDecreasing_int();
        propertiesRangeDownDecreasing_long();
        propertiesRangeDownDecreasing_BigInteger();
        propertiesRangeDownDecreasing_char();
        propertiesRangeIncreasing_byte_byte();
        propertiesRangeIncreasing_short_short();
        propertiesRangeIncreasing_int_int();
        propertiesRangeIncreasing_long_long();
        propertiesRangeIncreasing_BigInteger_BigInteger();
        propertiesRangeIncreasing_char_char();
        propertiesRangeDecreasing_byte_byte();
        propertiesRangeDecreasing_short_short();
        propertiesRangeDecreasing_int_int();
        propertiesRangeDecreasing_long_long();
        propertiesRangeDecreasing_BigInteger_BigInteger();
        propertiesRangeDecreasing_char_char();
        propertiesUniformSample_Iterable();
        propertiesUniformSample_String();
        propertiesRangeUp_byte();
        propertiesRangeUp_short();
        propertiesRangeUp_int();
        propertiesRangeUp_long();
        propertiesRangeUp_BigInteger();
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
        propertiesDependentPairsInfiniteIdentityHash();
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
        propertiesLists_Iterable();
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
        propertiesDistinctListsLexAtLeast();
        propertiesDistinctStringsLexAtLeast();
        propertiesDistinctListsShortlex();
        propertiesDistinctStringsShortlex();
        propertiesDistinctListsShortlexAtLeast();
        propertiesDistinctStringsShortlexAtLeast();
        propertiesDistinctLists_int_Iterable();
        propertiesDistinctPairs();
        propertiesDistinctTriples();
        propertiesDistinctQuadruples();
        propertiesDistinctQuintuples();
        propertiesDistinctSextuples();
        propertiesDistinctSeptuples();
        propertiesDistinctStrings_int_String();
        propertiesDistinctStrings_int();
        propertiesDistinctLists_Iterable();
        propertiesDistinctStrings_String();
        propertiesDistinctListsAtLeast();
        propertiesDistinctStringsAtLeast_int_String();
        propertiesDistinctStringsAtLeast_int();
        propertiesBagsLex_int_List();
        propertiesBagPairsLex();
        propertiesBagTriplesLex();
        propertiesBagQuadruplesLex();
        propertiesBagQuintuplesLex();
        propertiesBagSextuplesLex();
        propertiesBagSeptuplesLex();
        propertiesStringBagsLex();
        propertiesBagsShortlex();
        propertiesStringBagsShortlex();
        propertiesBagsShortlexAtLeast();
        propertiesStringBagsShortlexAtLeast();
        propertiesBags_int_Iterable();
        propertiesBagPairs();
        propertiesBagTriples();
        propertiesBagQuadruples();
        propertiesBagQuintuples();
        propertiesBagSextuples();
        propertiesBagSeptuples();
        propertiesStringBags_int_String();
        propertiesStringBags_int();
        propertiesBags_Iterable();
        propertiesStringBags_String();
        propertiesBagsAtLeast();
        propertiesStringBagsAtLeast_int_String();
        propertiesStringBagsAtLeast_int();
        propertiesSubsetsLex_int_List();
        propertiesSubsetPairsLex();
        propertiesSubsetTriplesLex();
        propertiesSubsetQuadruplesLex();
        propertiesSubsetQuintuplesLex();
        propertiesSubsetSextuplesLex();
        propertiesSubsetSeptuplesLex();
        propertiesStringSubsetsLex_int_String();
        propertiesSubsetsLex_List();
        propertiesStringSubsetsLex_String();
        propertiesSubsetsLexAtLeast();
        propertiesStringSubsetsLexAtLeast();
        propertiesSubsetsShortlex();
        propertiesStringSubsetsShortlex();
        propertiesSubsetsShortlexAtLeast();
        propertiesStringSubsetsShortlexAtLeast();
        propertiesSubsets_int_Iterable();
        propertiesSubsetPairs();
        propertiesSubsetTriples();
        propertiesSubsetQuadruples();
        propertiesSubsetQuintuples();
        propertiesSubsetSextuples();
        propertiesSubsetSeptuples();
        propertiesStringSubsets_int_String();
        propertiesStringSubsets_int();
        propertiesSubsets_Iterable();
        propertiesStringSubsets_String();
        propertiesSubsetsAtLeast();
        propertiesStringSubsetsAtLeast_int_String();
        propertiesStringSubsetsAtLeast_int();
        propertiesEithersSuccessive();
        propertiesEithersSquareRootOrder();
        propertiesEithersLogarithmicOrder();
        propertiesEithers();
        propertiesChooseSquareRootOrder();
        propertiesChooseLogarithmicOrder();
        propertiesChoose_Iterable_Iterable();
        propertiesChoose_Iterable();
        propertiesCartesianProduct();
        propertiesRepeatingIterables();
        propertiesRepeatingIterablesDistinctAtLeast();
        propertiesSublists();
        propertiesSubstrings();
        propertiesListsWithElement();
        propertiesStringsWithChar_char_String();
        propertiesStringsWithChar_char();
        propertiesSubsetsWithElement();
        propertiesStringSubsetsWithChar_char_String();
        propertiesStringSubsetsWithChar_char();
        propertiesListsWithSublists();
        propertiesStringsWithSubstrings_Iterable_String_String();
        propertiesStringsWithSubstrings_Iterable_String();
        propertiesMaps();
        propertiesIdentityMaps();
        propertiesRandomProvidersFixedScales();
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

    private void propertiesRangeUpIncreasing_byte() {
        initialize("rangeUpIncreasing(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeUpIncreasing(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), (1 << 7) - b);
            assertEquals(b, head(bs), b);
            assertEquals(b, last(bs), Byte.MAX_VALUE);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c >= b, bs));
            assertTrue(b, increasing(bs));
        }
    }

    private void propertiesRangeUpIncreasing_short() {
        initialize("rangeUpIncreasing(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeUpIncreasing(s);
            simpleTest(s, ss, t -> t >= s);
            assertEquals(s, head(ss), s);
            assertTrue(s, increasing(take(TINY_LIMIT, ss)));
            if (Short.MAX_VALUE - s < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private void propertiesRangeUpIncreasing_int() {
        initialize("rangeUpIncreasing(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeUpIncreasing(i);
            simpleTest(i, is, j -> j >= i);
            assertEquals(i, head(is), i);
            assertTrue(i, increasing(take(TINY_LIMIT, is)));
            if ((long) Integer.MAX_VALUE - i < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private void propertiesRangeUpIncreasing_long() {
        initialize("rangeUpIncreasing(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeUpIncreasing(l);
            simpleTest(l, ls, m -> m >= l);
            assertEquals(l, head(ls), l);
            assertTrue(l, increasing(take(TINY_LIMIT, ls)));
            if (lt(BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(l)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private void propertiesRangeUpIncreasing_BigInteger() {
        initialize("rangeUpIncreasing(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeUpIncreasing(i);
            simpleTest(i, is, j -> ge(j, i));
            assertEquals(i, head(is), i);
            assertTrue(i, increasing(take(TINY_LIMIT, is)));
        }
    }

    private void propertiesRangeUpIncreasing_char() {
        initialize("rangeUpIncreasing(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeUpIncreasing(c);
            simpleTest(nicePrint(c), cs, d -> d >= c);
            assertEquals(c, head(cs), c);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
            if (Character.MAX_VALUE - c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private void propertiesRangeUpDecreasing_byte() {
        initialize("rangeUpDecreasing(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeUpDecreasing(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), (1 << 7) - b);
            assertEquals(b, head(bs), Byte.MAX_VALUE);
            assertEquals(b, last(bs), b);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c >= b, bs));
            assertTrue(b, decreasing(bs));
        }
    }

    private void propertiesRangeUpDecreasing_short() {
        initialize("rangeUpDecreasing(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeUpDecreasing(s);
            simpleTest(s, ss, t -> t >= s);
            assertEquals(s, head(ss), Short.MAX_VALUE);
            assertTrue(s, decreasing(take(TINY_LIMIT, ss)));
            if (Short.MAX_VALUE - s < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private void propertiesRangeUpDecreasing_int() {
        initialize("rangeUpDecreasing(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeUpDecreasing(i);
            simpleTest(i, is, j -> j >= i);
            assertEquals(i, head(is), Integer.MAX_VALUE);
            assertTrue(i, decreasing(take(TINY_LIMIT, is)));
            if ((long) Integer.MAX_VALUE - i < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private void propertiesRangeUpDecreasing_long() {
        initialize("rangeUpDecreasing(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeUpDecreasing(l);
            simpleTest(l, ls, m -> m >= l);
            assertEquals(l, head(ls), Long.MAX_VALUE);
            assertTrue(l, decreasing(take(TINY_LIMIT, ls)));
            if (lt(BigInteger.valueOf(Long.MAX_VALUE).subtract(BigInteger.valueOf(l)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private void propertiesRangeUpDecreasing_char() {
        initialize("rangeUpDecreasing(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeUpDecreasing(c);
            simpleTest(nicePrint(c), cs, d -> d >= c);
            assertEquals(c, head(cs), Character.MAX_VALUE);
            assertTrue(nicePrint(c), decreasing(take(TINY_LIMIT, cs)));
            if (Character.MAX_VALUE - c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private void propertiesRangeDownIncreasing_byte() {
        initialize("rangeDownIncreasing(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeDownIncreasing(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), b + (1 << 7) + 1);
            assertEquals(b, head(bs), Byte.MIN_VALUE);
            assertEquals(b, last(bs), b);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c <= b, bs));
            assertTrue(b, increasing(bs));
        }
    }

    private void propertiesRangeDownIncreasing_short() {
        initialize("rangeDownIncreasing(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeDownIncreasing(s);
            simpleTest(s, ss, t -> t <= s);
            assertEquals(s, head(ss), Short.MIN_VALUE);
            assertTrue(s, increasing(take(TINY_LIMIT, ss)));
            if (s - Short.MIN_VALUE < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private void propertiesRangeDownIncreasing_int() {
        initialize("rangeDownIncreasing(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeDownIncreasing(i);
            simpleTest(i, is, j -> j <= i);
            assertEquals(i, head(is), Integer.MIN_VALUE);
            assertTrue(i, increasing(take(TINY_LIMIT, is)));
            if ((long) i - Integer.MIN_VALUE < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private void propertiesRangeDownIncreasing_long() {
        initialize("rangeDownIncreasing(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeDownIncreasing(l);
            simpleTest(l, ls, m -> m <= l);
            assertEquals(l, head(ls), Long.MIN_VALUE);
            assertTrue(l, increasing(take(TINY_LIMIT, ls)));
            if (lt(BigInteger.valueOf(l).subtract(BigInteger.valueOf(Long.MIN_VALUE)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private void propertiesRangeDownIncreasing_char() {
        initialize("rangeDownIncreasing(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeDownIncreasing(c);
            simpleTest(nicePrint(c), cs, d -> d <= c);
            assertEquals(c, head(cs), Character.MIN_VALUE);
            assertTrue(nicePrint(c), increasing(take(TINY_LIMIT, cs)));
            if (c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private void propertiesRangeDownDecreasing_byte() {
        initialize("rangeDownDecreasing(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeDownDecreasing(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), b + (1 << 7) + 1);
            assertEquals(b, head(bs), b);
            assertEquals(b, last(bs), Byte.MIN_VALUE);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c <= b, bs));
            assertTrue(b, decreasing(bs));
        }
    }

    private void propertiesRangeDownDecreasing_short() {
        initialize("rangeDownDecreasing(short)");
        for (short s : take(LIMIT, P.shorts())) {
            Iterable<Short> ss = EP.rangeDownDecreasing(s);
            simpleTest(s, ss, t -> t <= s);
            assertEquals(s, head(ss), s);
            assertTrue(s, decreasing(take(TINY_LIMIT, ss)));
            if (s - Short.MIN_VALUE < 1 << 8) {
                testHasNext(ss);
            }
        }
    }

    private void propertiesRangeDownDecreasing_int() {
        initialize("rangeDownDecreasing(int)");
        for (int i : take(LIMIT, P.integers())) {
            Iterable<Integer> is = EP.rangeDownDecreasing(i);
            simpleTest(i, is, j -> j <= i);
            assertEquals(i, head(is), i);
            assertTrue(i, decreasing(take(TINY_LIMIT, is)));
            if ((long) i - Integer.MIN_VALUE < 1L << 8) {
                testHasNext(is);
            }
        }
    }

    private void propertiesRangeDownDecreasing_long() {
        initialize("rangeDownDecreasing(long)");
        for (long l : take(LIMIT, P.longs())) {
            Iterable<Long> ls = EP.rangeDownDecreasing(l);
            simpleTest(l, ls, m -> m <= l);
            assertEquals(l, head(ls), l);
            assertTrue(l, decreasing(take(TINY_LIMIT, ls)));
            if (lt(BigInteger.valueOf(l).subtract(BigInteger.valueOf(Long.MIN_VALUE)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }
    }

    private void propertiesRangeDownDecreasing_BigInteger() {
        initialize("rangeDownDecreasing(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeDownDecreasing(i);
            simpleTest(i, is, j -> le(j, i));
            assertEquals(i, head(is), i);
            assertTrue(i, decreasing(take(TINY_LIMIT, is)));
        }
    }

    private void propertiesRangeDownDecreasing_char() {
        initialize("rangeDownDecreasing(char)");
        for (char c : take(LIMIT, P.characters())) {
            Iterable<Character> cs = EP.rangeDownDecreasing(c);
            simpleTest(nicePrint(c), cs, d -> d <= c);
            assertEquals(c, head(cs), c);
            assertTrue(nicePrint(c), decreasing(take(TINY_LIMIT, cs)));
            if (c < 1 << 8) {
                testHasNext(cs);
            }
        }
    }

    private void propertiesRangeIncreasing_byte_byte() {
        initialize("rangeIncreasing(byte, byte)");
        for (Pair<Byte, Byte> p : take(LIMIT, P.bagPairs(P.bytes()))) {
            Iterable<Byte> bs = EP.rangeIncreasing(p.a, p.b);
            assertTrue(p, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(p, length(bs), p.b - p.a + 1);
            assertTrue(p, unique(bs));
            assertEquals(p, head(bs), p.a);
            assertEquals(p, last(bs), p.b);
            assertTrue(p, all(b -> b >= p.a && b <= p.b, bs));
            assertTrue(p, increasing(bs));
            assertFalse(p, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(b, EP.rangeIncreasing(b, b), Collections.singletonList(b));
            aeqit(b, TINY_LIMIT, EP.rangeIncreasing(b, Byte.MAX_VALUE), EP.rangeUpIncreasing(b));
            aeqit(b, TINY_LIMIT, EP.rangeIncreasing(Byte.MIN_VALUE, b), EP.rangeDownIncreasing(b));
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.subsetPairs(P.bytes()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeIncreasing_short_short() {
        initialize("rangeIncreasing(short, short)");
        for (Pair<Short, Short> p : take(LIMIT, P.bagPairs(P.shorts()))) {
            Iterable<Short> ss = EP.rangeIncreasing(p.a, p.b);
            assertEquals(p, head(ss), p.a);
            simpleTest(p, ss, s -> s >= p.a && s <= p.b);
            assertTrue(p, increasing(take(TINY_LIMIT, ss)));
            assertFalse(p, isEmpty(ss));
            if (p.b - p.a < 1 << 8) {
                testHasNext(ss);
            }
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(s, EP.rangeIncreasing(s, s), Collections.singletonList(s));
            aeqit(s, TINY_LIMIT, EP.rangeIncreasing(s, Short.MAX_VALUE), EP.rangeUpIncreasing(s));
            aeqit(s, TINY_LIMIT, EP.rangeIncreasing(Short.MIN_VALUE, s), EP.rangeDownIncreasing(s));
        }

        for (Pair<Short, Short> p : take(LIMIT, P.subsetPairs(P.shorts()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeIncreasing_int_int() {
        initialize("rangeIncreasing(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.bagPairs(P.integers()))) {
            Iterable<Integer> is = EP.rangeIncreasing(p.a, p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertEquals(p, head(is), p.a);
            simpleTest(p, is, i -> i >= p.a && i <= p.b);
            assertTrue(p, increasing(take(TINY_LIMIT, tis)));
            assertFalse(p, isEmpty(is));
            if ((long) p.b - p.a < 1L << 8) {
                testHasNext(is);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(i, EP.rangeIncreasing(i, i), Collections.singletonList(i));
            aeqit(i, TINY_LIMIT, EP.rangeIncreasing(i, Integer.MAX_VALUE), EP.rangeUpIncreasing(i));
            aeqit(i, TINY_LIMIT, EP.rangeIncreasing(Integer.MIN_VALUE, i), EP.rangeDownIncreasing(i));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.integers()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeIncreasing_long_long() {
        initialize("rangeIncreasing(long, long)");
        for (Pair<Long, Long> p : take(LIMIT, P.bagPairs(P.longs()))) {
            Iterable<Long> ls = EP.rangeIncreasing(p.a, p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertEquals(p, head(ls), p.a);
            simpleTest(p, ls, l -> l >= p.a && l <= p.b);
            assertTrue(p, increasing(take(TINY_LIMIT, tls)));
            assertFalse(p, isEmpty(ls));
            if (lt(BigInteger.valueOf(p.b).subtract(BigInteger.valueOf(p.a)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(l, EP.rangeIncreasing(l, l), Collections.singletonList(l));
            aeqit(l, TINY_LIMIT, EP.rangeIncreasing(l, Long.MAX_VALUE), EP.rangeUpIncreasing(l));
            aeqit(l, TINY_LIMIT, EP.rangeIncreasing(Long.MIN_VALUE, l), EP.rangeDownIncreasing(l));
        }

        for (Pair<Long, Long> p : take(LIMIT, P.subsetPairs(P.longs()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeIncreasing_BigInteger_BigInteger() {
        initialize("rangeIncreasing(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.bagPairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = EP.rangeIncreasing(p.a, p.b);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertEquals(p, head(is), p.a);
            simpleTest(p, is, i -> ge(i, p.a) && le(i, p.b));
            assertTrue(p, increasing(take(TINY_LIMIT, tis)));
            assertFalse(p, isEmpty(is));
            if (lt(p.b.subtract(p.a), BigInteger.valueOf(1 << 8))) {
                testHasNext(is);
            }
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i, EP.rangeIncreasing(i, i), Collections.singletonList(i));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.subsetPairs(P.bigIntegers()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeIncreasing_char_char() {
        initialize("rangeIncreasing(char, char)");
        for (Pair<Character, Character> p : take(LIMIT, P.bagPairs(P.characters()))) {
            Iterable<Character> cs = EP.rangeIncreasing(p.a, p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertEquals(p, head(cs), p.a);
            simpleTest(p, cs, c -> c >= p.a && c <= p.b);
            assertTrue(p, increasing(tcs));
            assertFalse(p, isEmpty(cs));
            if (p.b - p.a < 1 << 8) {
                testHasNext(cs);
            }
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.rangeIncreasing(c, c), Collections.singletonList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.rangeIncreasing(c, Character.MAX_VALUE), EP.rangeUpIncreasing(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.rangeIncreasing('\0', c), EP.rangeDownIncreasing(c));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.subsetPairs(P.characters()))) {
            try {
                EP.rangeIncreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_byte_byte() {
        initialize("rangeDecreasing(byte, byte)");
        for (Pair<Byte, Byte> p : take(LIMIT, P.bagPairs(P.bytes()))) {
            Iterable<Byte> bs = EP.rangeDecreasing(p.a, p.b);
            assertTrue(p, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(p, length(bs), p.b - p.a + 1);
            assertTrue(p, unique(bs));
            assertEquals(p, head(bs), p.b);
            assertEquals(p, last(bs), p.a);
            assertTrue(p, all(b -> b >= p.a && b <= p.b, bs));
            assertTrue(p, decreasing(bs));
            assertFalse(p, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(b, EP.rangeDecreasing(b, b), Collections.singletonList(b));
            aeqit(b, TINY_LIMIT, EP.rangeDecreasing(b, Byte.MAX_VALUE), EP.rangeUpDecreasing(b));
            aeqit(b, TINY_LIMIT, EP.rangeDecreasing(Byte.MIN_VALUE, b), EP.rangeDownDecreasing(b));
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.subsetPairs(P.bytes()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_short_short() {
        initialize("rangeDecreasing(short, short)");
        for (Pair<Short, Short> p : take(LIMIT, P.bagPairs(P.shorts()))) {
            Iterable<Short> ss = EP.rangeDecreasing(p.a, p.b);
            assertEquals(p, head(ss), p.b);
            simpleTest(p, ss, s -> s >= p.a && s <= p.b);
            assertTrue(p, decreasing(take(TINY_LIMIT, ss)));
            assertFalse(p, isEmpty(ss));
            if (p.b - p.a < 1 << 8) {
                testHasNext(ss);
            }
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(s, EP.rangeDecreasing(s, s), Collections.singletonList(s));
            aeqit(s, TINY_LIMIT, EP.rangeDecreasing(s, Short.MAX_VALUE), EP.rangeUpDecreasing(s));
            aeqit(s, TINY_LIMIT, EP.rangeDecreasing(Short.MIN_VALUE, s), EP.rangeDownDecreasing(s));
        }

        for (Pair<Short, Short> p : take(LIMIT, P.subsetPairs(P.shorts()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_int_int() {
        initialize("rangeDecreasing(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.bagPairs(P.integers()))) {
            Iterable<Integer> is = EP.rangeDecreasing(p.a, p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertEquals(p, head(is), p.b);
            simpleTest(p, is, i -> i >= p.a && i <= p.b);
            assertTrue(p, decreasing(take(TINY_LIMIT, tis)));
            assertFalse(p, isEmpty(is));
            if ((long) p.b - p.a < 1L << 8) {
                testHasNext(is);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(i, EP.rangeDecreasing(i, i), Collections.singletonList(i));
            aeqit(i, TINY_LIMIT, EP.rangeDecreasing(i, Integer.MAX_VALUE), EP.rangeUpDecreasing(i));
            aeqit(i, TINY_LIMIT, EP.rangeDecreasing(Integer.MIN_VALUE, i), EP.rangeDownDecreasing(i));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.integers()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_long_long() {
        initialize("rangeDecreasing(long, long)");
        for (Pair<Long, Long> p : take(LIMIT, P.bagPairs(P.longs()))) {
            Iterable<Long> ls = EP.rangeDecreasing(p.a, p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertEquals(p, head(ls), p.b);
            simpleTest(p, ls, l -> l >= p.a && l <= p.b);
            assertTrue(p, decreasing(take(TINY_LIMIT, tls)));
            assertFalse(p, isEmpty(ls));
            if (lt(BigInteger.valueOf(p.b).subtract(BigInteger.valueOf(p.a)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(l, EP.rangeDecreasing(l, l), Collections.singletonList(l));
            aeqit(l, TINY_LIMIT, EP.rangeDecreasing(l, Long.MAX_VALUE), EP.rangeUpDecreasing(l));
            aeqit(l, TINY_LIMIT, EP.rangeDecreasing(Long.MIN_VALUE, l), EP.rangeDownDecreasing(l));
        }

        for (Pair<Long, Long> p : take(LIMIT, P.subsetPairs(P.longs()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_BigInteger_BigInteger() {
        initialize("rangeDecreasing(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.bagPairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = EP.rangeDecreasing(p.a, p.b);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertEquals(p, head(is), p.b);
            simpleTest(p, is, i -> ge(i, p.a) && le(i, p.b));
            assertTrue(p, decreasing(take(TINY_LIMIT, tis)));
            assertFalse(p, isEmpty(is));
            if (lt(p.b.subtract(p.a), BigInteger.valueOf(1 << 8))) {
                testHasNext(is);
            }
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i, EP.rangeDecreasing(i, i), Collections.singletonList(i));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.subsetPairs(P.bigIntegers()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeDecreasing_char_char() {
        initialize("rangeDecreasing(char, char)");
        for (Pair<Character, Character> p : take(LIMIT, P.bagPairs(P.characters()))) {
            Iterable<Character> cs = EP.rangeDecreasing(p.a, p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertEquals(p, head(cs), p.b);
            simpleTest(p, cs, c -> c >= p.a && c <= p.b);
            assertTrue(p, decreasing(tcs));
            assertFalse(p, isEmpty(cs));
            if (p.b - p.a < 1 << 8) {
                testHasNext(cs);
            }
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.rangeDecreasing(c, c), Collections.singletonList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.rangeDecreasing(c, Character.MAX_VALUE), EP.rangeUpDecreasing(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.rangeDecreasing('\0', c), EP.rangeDownDecreasing(c));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.subsetPairs(P.characters()))) {
            try {
                EP.rangeDecreasing(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesBooleans() {
        initializeConstant("booleans()");
        biggerTest(EP, EP.booleans(), b -> true);
        testHasNext(EP.booleans());
    }

    private void propertiesOrderingsIncreasing() {
        initializeConstant("orderingsIncreasing()");
        biggerTest(EP, EP.orderingsIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.orderingsIncreasing()));
        testHasNext(EP.orderingsIncreasing());
    }

    private void propertiesOrderings() {
        initializeConstant("orderings()");
        biggerTest(EP, EP.orderings(), b -> true);
        testHasNext(EP.orderings());
    }

    private void propertiesRoundingModes() {
        initializeConstant("roundingModes()");
        biggerTest(EP, EP.roundingModes(), b -> true);
        testHasNext(EP.roundingModes());
    }

    private void propertiesUniformSample_Iterable() {
        initialize("uniformSample(Iterable<T>)");
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
            Iterable<Integer> js = EP.uniformSample(is);
            aeqit(is, is, js);
            testNoRemove(js);
            testHasNext(js);
        }
    }

    private void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (String s : take(LIMIT, P.strings())) {
            Iterable<Character> cs = EP.uniformSample(s);
            assertEquals(s, s, charsToString(cs));
            testNoRemove(cs);
            testHasNext(cs);
        }
    }

    private void propertiesBytesIncreasing() {
        initializeConstant("bytesIncreasing()");
        biggerTest(EP, EP.bytesIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.bytesIncreasing()));
        testHasNext(EP.bytesIncreasing());
    }

    private void propertiesShortsIncreasing() {
        initializeConstant("shortsIncreasing()");
        biggerTest(EP, EP.shortsIncreasing(), b -> true);
        assertTrue(EP, increasing(EP.shortsIncreasing()));
        testHasNext(EP.shortsIncreasing());
    }

    private void propertiesIntegersIncreasing() {
        initializeConstant("integersIncreasing()");
        biggerTest(EP, EP.integersIncreasing(), b -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.integersIncreasing())));
    }

    private void propertiesLongsIncreasing() {
        initializeConstant("longsIncreasing()");
        biggerTest(EP, EP.longsIncreasing(), b -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.longsIncreasing())));
    }

    private void propertiesPositiveBytes() {
        initializeConstant("positiveBytes()");
        biggerTest(EP, EP.positiveBytes(), b -> b > 0);
        assertTrue(EP, increasing(EP.positiveBytes()));
        testHasNext(EP.positiveBytes());
    }

    private void propertiesPositiveShorts() {
        initializeConstant("positiveShorts()");
        biggerTest(EP, EP.positiveShorts(), s -> s > 0);
        assertTrue(EP, increasing(EP.positiveShorts()));
        testHasNext(EP.positiveShorts());
    }

    private void propertiesPositiveIntegers() {
        initializeConstant("positiveIntegers()");
        biggerTest(EP, EP.positiveIntegers(), i -> i > 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveIntegers())));
    }

    private void propertiesPositiveLongs() {
        initializeConstant("positiveLongs()");
        biggerTest(EP, EP.positiveLongs(), l -> l > 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveLongs())));
    }

    private void propertiesPositiveBigIntegers() {
        initializeConstant("positiveBigIntegers()");
        biggerTest(EP, EP.positiveBigIntegers(), i -> i.signum() == 1);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.positiveBigIntegers())));
    }

    private void propertiesNegativeBytes() {
        initializeConstant("negativeBytes()");
        biggerTest(EP, EP.negativeBytes(), b -> b < 0);
        assertTrue(EP, decreasing(EP.negativeBytes()));
        testHasNext(EP.negativeBytes());
    }

    private void propertiesNegativeShorts() {
        initializeConstant("negativeShorts()");
        biggerTest(EP, EP.negativeShorts(), s -> s < 0);
        assertTrue(EP, decreasing(EP.negativeShorts()));
        testHasNext(EP.negativeShorts());
    }

    private void propertiesNegativeIntegers() {
        initializeConstant("negativeIntegers()");
        biggerTest(EP, EP.negativeIntegers(), i -> i < 0);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeIntegers())));
    }

    private void propertiesNegativeLongs() {
        initializeConstant("negativeLongs()");
        biggerTest(EP, EP.negativeLongs(), l -> l < 0);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeLongs())));
    }

    private void propertiesNegativeBigIntegers() {
        initializeConstant("negativeBigIntegers()");
        biggerTest(EP, EP.negativeBigIntegers(), i -> i.signum() == -1);
        assertTrue(EP, decreasing(take(LARGE_LIMIT, EP.negativeBigIntegers())));
    }

    private void propertiesNonzeroBytes() {
        initializeConstant("nonzeroBytes()");
        biggerTest(EP, EP.nonzeroBytes(), b -> b != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.nonzeroBytes())));
        testHasNext(EP.nonzeroBytes());
    }

    private void propertiesNonzeroShorts() {
        initializeConstant("nonzeroShorts()");
        biggerTest(EP, EP.nonzeroShorts(), s -> s != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.nonzeroShorts())));
        testHasNext(EP.nonzeroShorts());
    }

    private void propertiesNonzeroIntegers() {
        initializeConstant("nonzeroIntegers()");
        biggerTest(EP, EP.nonzeroIntegers(), i -> i != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(LARGE_LIMIT, EP.nonzeroIntegers()))));
    }

    private void propertiesNonzeroLongs() {
        initializeConstant("nonzeroLongs()");
        biggerTest(EP, EP.nonzeroLongs(), l -> l != 0);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(LARGE_LIMIT, EP.nonzeroLongs()))));
    }

    private void propertiesNonzeroBigIntegers() {
        initializeConstant("nonzeroBigIntegers()");
        biggerTest(EP, EP.nonzeroBigIntegers(), i -> !i.equals(BigInteger.ZERO));
        assertTrue(EP, weaklyIncreasing(map(BigInteger::abs, take(LARGE_LIMIT, EP.nonzeroBigIntegers()))));
    }

    private void propertiesNaturalBytes() {
        initializeConstant("naturalBytes()");
        biggerTest(EP, EP.naturalBytes(), b -> b >= 0);
        assertTrue(EP, increasing(EP.naturalBytes()));
        testHasNext(EP.naturalBytes());
    }

    private void propertiesNaturalShorts() {
        initializeConstant("naturalShorts()");
        biggerTest(EP, EP.naturalShorts(), s -> s >= 0);
        assertTrue(EP, increasing(EP.naturalShorts()));
        testHasNext(EP.naturalShorts());
    }

    private void propertiesNaturalIntegers() {
        initializeConstant("naturalIntegers()");
        biggerTest(EP, EP.naturalIntegers(), i -> i >= 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalIntegers())));
    }

    private void propertiesNaturalLongs() {
        initializeConstant("naturalLongs()");
        biggerTest(EP, EP.naturalLongs(), l -> l >= 0);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalLongs())));
    }

    private void propertiesNaturalBigIntegers() {
        initializeConstant("naturalBigIntegers()");
        biggerTest(EP, EP.naturalBigIntegers(), i -> i.signum() != -1);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.naturalBigIntegers())));
    }

    private void propertiesBytes() {
        initializeConstant("bytes()");
        biggerTest(EP, EP.bytes(), b -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.bytes())));
        testHasNext(EP.bytes());
    }

    private void propertiesShorts() {
        initializeConstant("shorts()");
        biggerTest(EP, EP.shorts(), s -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, EP.shorts())));
        testHasNext(EP.shorts());
    }

    private void propertiesIntegers() {
        initializeConstant("integers()");
        biggerTest(EP, EP.integers(), i -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(LARGE_LIMIT, EP.integers()))));
    }

    private void propertiesLongs() {
        initializeConstant("longs()");
        biggerTest(EP, EP.longs(), l -> true);
        //noinspection RedundantCast
        assertTrue(EP, weaklyIncreasing((Iterable<Long>) map(Math::abs, take(LARGE_LIMIT, EP.longs()))));
    }

    private void propertiesBigIntegers() {
        initializeConstant("bigIntegers()");
        biggerTest(EP, EP.bigIntegers(), i -> true);
        assertTrue(EP, weaklyIncreasing(map(BigInteger::abs, take(LARGE_LIMIT, EP.bigIntegers()))));
    }

    private void propertiesAsciiCharactersIncreasing() {
        initializeConstant("asciiCharactersIncreasing()");
        biggerTest(EP, EP.asciiCharactersIncreasing(), i -> i < 128);
        assertTrue(EP, increasing(EP.asciiCharactersIncreasing()));
        testHasNext(EP.asciiCharactersIncreasing());
    }

    private void propertiesAsciiCharacters() {
        initializeConstant("asciiCharacters()");
        biggerTest(EP, EP.asciiCharacters(), i -> i < 128);
        testHasNext(EP.asciiCharacters());
    }

    private void propertiesCharactersIncreasing() {
        initializeConstant("charactersIncreasing()");
        biggerTest(EP, EP.charactersIncreasing(), i -> true);
        assertTrue(EP, increasing(take(LARGE_LIMIT, EP.charactersIncreasing())));
        testHasNext(EP.charactersIncreasing());
    }

    private void propertiesCharacters() {
        initializeConstant("characters()");
        biggerTest(EP, EP.characters(), i -> true);
        testHasNext(EP.characters());
    }

    private void propertiesRangeUp_byte() {
        initialize("rangeUp(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeUp(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(b, length(bs), (1 << 7) - b);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c >= b, bs));
            //noinspection RedundantCast
            assertTrue(b, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
        }
    }

    private void propertiesRangeUp_short() {
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

    private void propertiesRangeUp_int() {
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

    private void propertiesRangeUp_long() {
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

    private void propertiesRangeUp_BigInteger() {
        initialize("rangeUp(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeUp(i);
            simpleTest(i, is, j -> ge(j, i));
            assertTrue(i, weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private void propertiesRangeDown_byte() {
        initialize("rangeDown(byte)");
        for (byte b : take(LIMIT, P.bytes())) {
            Iterable<Byte> bs = EP.rangeDown(b);
            assertTrue(b, all(Objects::nonNull, bs));
            testNoRemove(bs);
            assertEquals(b, length(bs), b + (1 << 7) + 1);
            assertTrue(b, unique(bs));
            assertTrue(b, all(c -> c <= b, bs));
            //noinspection RedundantCast
            assertTrue(b, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            testHasNext(bs);
        }
    }

    private void propertiesRangeDown_short() {
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

    private void propertiesRangeDown_int() {
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

    private void propertiesRangeDown_long() {
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

    private void propertiesRangeDown_BigInteger() {
        initialize("rangeDown(BigInteger)");
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Iterable<BigInteger> is = EP.rangeDown(i);
            simpleTest(i, is, j -> le(j, i));
            assertTrue(i, weaklyIncreasing(map(BigInteger::abs, take(TINY_LIMIT, is))));
        }
    }

    private void propertiesRangeDown_char() {
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

    private void propertiesRange_byte_byte() {
        initialize("range(byte, byte)");
        for (Pair<Byte, Byte> p : take(LIMIT, P.bagPairs(P.bytes()))) {
            Iterable<Byte> bs = EP.range(p.a, p.b);
            assertTrue(p, all(Objects::nonNull, bs));
            testNoRemove(bs);
            testHasNext(bs);
            assertEquals(p, length(bs), p.b - p.a + 1);
            assertTrue(p, unique(bs));
            assertTrue(p, all(b -> b >= p.a && b <= p.b, bs));
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, bs)));
            assertFalse(p, isEmpty(bs));
        }

        for (byte b : take(LIMIT, P.bytes())) {
            aeqit(b, EP.range(b, b), Collections.singletonList(b));
            aeqit(b, TINY_LIMIT, EP.range(b, Byte.MAX_VALUE), EP.rangeUp(b));
            aeqit(b, TINY_LIMIT, EP.range(Byte.MIN_VALUE, b), EP.rangeDown(b));
        }

        for (Pair<Byte, Byte> p : take(LIMIT, P.subsetPairs(P.bytes()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRange_short_short() {
        initialize("range(short, short)");
        for (Pair<Short, Short> p : take(LIMIT, P.bagPairs(P.shorts()))) {
            Iterable<Short> ss = EP.range(p.a, p.b);
            simpleTest(p, ss, s -> s >= p.a && s <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, take(TINY_LIMIT, ss))));
            assertFalse(p, isEmpty(ss));
            if (p.b - p.a < 1 << 8) {
                testHasNext(ss);
            }
        }

        for (short s : take(LIMIT, P.shorts())) {
            aeqit(s, EP.range(s, s), Collections.singletonList(s));
            aeqit(s, TINY_LIMIT, EP.range(s, Short.MAX_VALUE), EP.rangeUp(s));
            aeqit(s, TINY_LIMIT, EP.range(Short.MIN_VALUE, s), EP.rangeDown(s));
        }

        for (Pair<Short, Short> p : take(LIMIT, P.subsetPairs(P.shorts()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRange_int_int() {
        initialize("range(int, int)");
        for (Pair<Integer, Integer> p : take(LIMIT, P.bagPairs(P.integers()))) {
            Iterable<Integer> is = EP.range(p.a, p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            simpleTest(p, is, i -> i >= p.a && i <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Integer>) map(Math::abs, tis)));
            assertFalse(p, isEmpty(is));
            if ((long) p.b - p.a < 1L << 8) {
                testHasNext(is);
            }
        }

        for (int i : take(LIMIT, P.integers())) {
            aeqit(i, EP.range(i, i), Collections.singletonList(i));
            aeqit(i, TINY_LIMIT, EP.range(i, Integer.MAX_VALUE), EP.rangeUp(i));
            aeqit(i, TINY_LIMIT, EP.range(Integer.MIN_VALUE, i), EP.rangeDown(i));
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.subsetPairs(P.integers()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRange_long_long() {
        initialize("range(long, long)");
        for (Pair<Long, Long> p : take(LIMIT, P.bagPairs(P.longs()))) {
            Iterable<Long> ls = EP.range(p.a, p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            simpleTest(p, ls, l -> l >= p.a && l <= p.b);
            //noinspection RedundantCast
            assertTrue(p, weaklyIncreasing((Iterable<Long>) map(Math::abs, tls)));
            assertFalse(p, isEmpty(ls));
            if (lt(BigInteger.valueOf(p.b).subtract(BigInteger.valueOf(p.a)), BigInteger.valueOf(1 << 8))) {
                testHasNext(ls);
            }
        }

        for (long l : take(LIMIT, P.longs())) {
            aeqit(l, EP.range(l, l), Collections.singletonList(l));
            aeqit(l, TINY_LIMIT, EP.range(l, Long.MAX_VALUE), EP.rangeUp(l));
            aeqit(l, TINY_LIMIT, EP.range(Long.MIN_VALUE, l), EP.rangeDown(l));
        }

        for (Pair<Long, Long> p : take(LIMIT, P.subsetPairs(P.longs()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRange_BigInteger_BigInteger() {
        initialize("range(BigInteger, BigInteger)");
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.bagPairs(P.bigIntegers()))) {
            Iterable<BigInteger> is = EP.range(p.a, p.b);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            simpleTest(p, is, i -> ge(i, p.a) && le(i, p.b));
            assertTrue(p, weaklyIncreasing(map(BigInteger::abs, tis)));
            assertFalse(p, isEmpty(is));
            if (lt(p.b.subtract(p.a), BigInteger.valueOf(1 << 8))) {
                testHasNext(is);
            }
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            aeqit(i, EP.range(i, i), Collections.singletonList(i));
        }

        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.subsetPairs(P.bigIntegers()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRange_char_char() {
        initialize("range(char, char)");
        for (Pair<Character, Character> p : take(LIMIT, P.bagPairs(P.characters()))) {
            Iterable<Character> cs = EP.range(p.a, p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            simpleTest(p, cs, c -> c >= p.a && c <= p.b);
            assertTrue(p, increasing(tcs));
            assertFalse(p, isEmpty(cs));
            if (p.b - p.a < 1 << 8) {
                testHasNext(cs);
            }
        }

        for (char c : take(LIMIT, P.characters())) {
            aeqit(nicePrint(c), EP.range(c, c), Collections.singletonList(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range(c, Character.MAX_VALUE), EP.rangeUp(c));
            aeqit(nicePrint(c), TINY_LIMIT, EP.range('\0', c), EP.rangeDown(c));
        }

        for (Pair<Character, Character> p : take(LIMIT, P.subsetPairs(P.characters()))) {
            try {
                EP.range(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesPositiveBinaryFractions() {
        initializeConstant("positiveBinaryFractions()");
        biggerTest(EP, EP.positiveBinaryFractions(), bf -> bf.signum() == 1);
    }

    private void propertiesNegativeBinaryFractions() {
        initializeConstant("negativeBinaryFractions()");
        biggerTest(EP, EP.negativeBinaryFractions(), bf -> bf.signum() == -1);
    }

    private void propertiesNonzeroBinaryFractions() {
        initializeConstant("nonzeroBinaryFractions()");
        biggerTest(EP, EP.nonzeroBinaryFractions(), bf -> bf != BinaryFraction.ZERO);
    }

    private void propertiesBinaryFractions() {
        initializeConstant("binaryFractions()");
        biggerTest(EP, EP.binaryFractions(), bf -> true);
    }

    private void propertiesRangeUp_BinaryFraction() {
        initialize("rangeUp(BinaryFraction)");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Iterable<BinaryFraction> bfs = EP.rangeUp(bf);
            simpleTest(bf, bfs, c -> ge(c, bf));
        }
    }

    private void propertiesRangeDown_BinaryFraction() {
        initialize("rangeDown(BinaryFraction)");
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            Iterable<BinaryFraction> bfs = EP.rangeDown(bf);
            simpleTest(bf, bfs, c -> le(c, bf));
        }
    }

    private void propertiesRange_BinaryFraction_BinaryFraction() {
        initialize("range(BinaryFraction, BinaryFraction)");
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.bagPairs(P.binaryFractions()))) {
            Iterable<BinaryFraction> bfs = EP.range(p.a, p.b);
            simpleTest(p, bfs, bf -> ge(bf, p.a) && le(bf, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bfs));
            if (ge(p.a, p.b)) {
                testHasNext(bfs);
            }
        }

        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            aeqit(bf, EP.range(bf, bf), Collections.singletonList(bf));
        }

        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.subsetPairs(P.binaryFractions()))) {
            try {
                EP.range(p.b, p.a);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesPositiveFloats() {
        initializeConstant("positiveFloats()");
        biggerTest(EP, EP.positiveFloats(), f -> f > 0);
    }

    private void propertiesNegativeFloats() {
        initializeConstant("negativeFloats()");
        biggerTest(EP, EP.negativeFloats(), f -> f < 0);
    }

    private void propertiesNonzeroFloats() {
        initializeConstant("nonzeroFloats()");
        biggerTest(EP, EP.nonzeroFloats(), f -> f != 0);
    }

    private void propertiesFloats() {
        initializeConstant("floats()");
        biggerTest(EP, EP.floats(), f -> true);
    }

    private void propertiesPositiveDoubles() {
        initializeConstant("positiveDoubles()");
        biggerTest(EP, EP.positiveDoubles(), d -> d > 0);
    }

    private void propertiesNegativeDoubles() {
        initializeConstant("negativeDoubles()");
        biggerTest(EP, EP.negativeDoubles(), d -> d < 0);
    }

    private void propertiesNonzeroDoubles() {
        initializeConstant("nonzeroDoubles()");
        biggerTest(EP, EP.nonzeroDoubles(), d -> d != 0);
    }

    private void propertiesDoubles() {
        initializeConstant("doubles()");
        biggerTest(EP, EP.doubles(), d -> true);
    }

    private void propertiesRangeUp_float() {
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

    private void propertiesRangeDown_float() {
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

    private void propertiesRange_float_float() {
        initialize("range(float, float)");
        for (Pair<Float, Float> p : take(LIMIT, P.subsetPairs(filter(g -> !Float.isNaN(g), P.floats())))) {
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

        for (Pair<Float, Float> p : take(LIMIT, P.subsetPairs(filter(g -> !Float.isNaN(g), P.floats())))) {
            try {
                EP.range(p.b, p.a);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeUp_double() {
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

    private void propertiesRangeDown_double() {
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

    private void propertiesRange_double_double() {
        initialize("range(double, double)");
        for (Pair<Double, Double> p : take(LIMIT, P.bagPairs(filter(g -> !Double.isNaN(g), P.doubles())))) {
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

        for (Pair<Double, Double> p : take(LIMIT, P.subsetPairs(filter(e -> !Double.isNaN(e), P.doubles())))) {
            try {
                EP.range(p.b, p.a);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesPositiveBigDecimals() {
        initializeConstant("positiveBigDecimals()");
        biggerTest(EP, EP.positiveBigDecimals(), bd -> bd.signum() == 1);
    }

    private void propertiesNegativeBigDecimals() {
        initializeConstant("negativeBigDecimals()");
        biggerTest(EP, EP.negativeBigDecimals(), bd -> bd.signum() == -1);
    }

    private void propertiesNonzeroBigDecimals() {
        initializeConstant("nonzeroBigDecimals()");
        biggerTest(EP, EP.nonzeroBigDecimals(), bd -> !bd.equals(BigDecimal.ZERO));
    }

    private void propertiesBigDecimals() {
        initializeConstant("bigDecimals()");
        biggerTest(EP, EP.bigDecimals(), bd -> true);
    }

    private void propertiesPositiveCanonicalBigDecimals() {
        initializeConstant("positiveCanonicalBigDecimals()");
        biggerTest(EP, EP.positiveCanonicalBigDecimals(), bd -> BigDecimalUtils.isCanonical(bd) && bd.signum() == 1);
    }

    private void propertiesNegativeCanonicalBigDecimals() {
        initializeConstant("negativeCanonicalBigDecimals()");
        biggerTest(EP, EP.negativeCanonicalBigDecimals(), bd -> BigDecimalUtils.isCanonical(bd) && bd.signum() == -1);
    }

    private void propertiesNonzeroCanonicalBigDecimals() {
        initializeConstant("nonzeroCanonicalBigDecimals()");
        biggerTest(
                EP,
                EP.nonzeroCanonicalBigDecimals(),
                bd -> BigDecimalUtils.isCanonical(bd) && !bd.equals(BigDecimal.ZERO)
        );
    }

    private void propertiesCanonicalBigDecimals() {
        initializeConstant("canonicalBigDecimals()");
        biggerTest(EP, EP.canonicalBigDecimals(), BigDecimalUtils::isCanonical);
    }

    private void propertiesRangeUp_BigDecimal() {
        initialize("rangeUp(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeUp(bd);
            simpleTest(bd, bds, c -> ge(c, bd));
        }
    }

    private void propertiesRangeDown_BigDecimal() {
        initialize("rangeDown(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeDown(bd);
            simpleTest(bd, bds, c -> le(c, bd));
        }
    }

    private void propertiesRange_BigDecimal_BigDecimal() {
        initialize("range(BigDecimal, BigDecimal)");
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.bagPairs(P.bigDecimals()))) {
            Iterable<BigDecimal> bds = EP.range(p.a, p.b);
            simpleTest(p, bds, bd -> ge(bd, p.a) && le(bd, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bds));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            all(c -> eq(c, bd), take(TINY_LIMIT, EP.range(bd, bd)));
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.subsetPairs(P.bigDecimals()))) {
            try {
                EP.range(p.b, p.a);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRangeUpCanonical_BigDecimal() {
        initialize("rangeUpCanonical(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeUpCanonical(bd);
            simpleTest(bd, bds, c -> BigDecimalUtils.isCanonical(c) && ge(c, bd));
        }
    }

    private void propertiesRangeDownCanonical_BigDecimal() {
        initialize("rangeDownCanonical(BigDecimal)");
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Iterable<BigDecimal> bds = EP.rangeDownCanonical(bd);
            simpleTest(bd, bds, c -> BigDecimalUtils.isCanonical(c) && le(c, bd));
        }
    }

    private void propertiesRangeCanonical_BigDecimal_BigDecimal() {
        initialize("rangeCanonical(BigDecimal, BigDecimal)");
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.bagPairs(P.bigDecimals()))) {
            Iterable<BigDecimal> bds = EP.rangeCanonical(p.a, p.b);
            simpleTest(p, bds, bd -> BigDecimalUtils.isCanonical(bd) && ge(bd, p.a) && le(bd, p.b));
            assertEquals(p, gt(p.a, p.b), isEmpty(bds));
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            aeqit(bd, EP.rangeCanonical(bd, bd), Collections.singletonList(BigDecimalUtils.canonicalize(bd)));
        }

        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.subsetPairs(P.bigDecimals()))) {
            try {
                EP.rangeCanonical(p.b, p.a);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesWithNull() {
        initialize("withNull(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Integer> withNull = EP.withNull(xs);
            testNoRemove(withNull);
            testHasNext(withNull);
            assertFalse(xs, isEmpty(withNull));
            assertNull(xs, head(withNull));
            assertTrue(xs, all(Objects::nonNull, tail(withNull)));
            inverse(EP::withNull, (Iterable<Integer> wn) -> toList(tail(wn)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Integer> withNull = EP.withNull(xs);
            testNoRemove(TINY_LIMIT, withNull);
            assertFalse(xs, isEmpty(withNull));
            assertNull(xs, head(withNull));
            assertTrue(xs, all(Objects::nonNull, take(TINY_LIMIT, tail(withNull))));
            aeqit(xs, TINY_LIMIT, tail(withNull), xs);
        }
    }

    private void propertiesNonEmptyOptionals() {
        initialize("nonEmptyOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(neos);
            testHasNext(neos);
            assertEquals(xs, xs.size(), length(neos));
            inverse(EP::nonEmptyOptionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, ys)), xs);
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Optional<Integer>> neos = EP.nonEmptyOptionals(xs);
            testNoRemove(TINY_LIMIT, neos);
            aeqit(xs, TINY_LIMIT, map(Optional::get, neos), xs);
        }
    }

    private void propertiesOptionals() {
        initialize("optionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Optional<Integer>> os = EP.optionals(xs);
            testNoRemove(os);
            testHasNext(os);
            assertFalse(xs, isEmpty(os));
            assertFalse(xs, head(os).isPresent());
            assertTrue(xs, all(Optional::isPresent, tail(os)));
            inverse(EP::optionals, (Iterable<Optional<Integer>> ys) -> toList(map(Optional::get, tail(ys))), xs);
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

    private void propertiesNonEmptyNullableOptionals() {
        initialize("nonEmptyNullableOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<NullableOptional<Integer>> nenos = EP.nonEmptyNullableOptionals(xs);
            testNoRemove(nenos);
            testHasNext(nenos);
            assertEquals(xs, xs.size(), length(nenos));
            inverse(
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

    private void propertiesNullableOptionals() {
        initialize("nullableOptionals(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<NullableOptional<Integer>> nos = EP.nullableOptionals(xs);
            testNoRemove(nos);
            testHasNext(nos);
            assertFalse(xs, isEmpty(nos));
            assertFalse(xs, head(nos).isPresent());
            assertTrue(xs, all(NullableOptional::isPresent, tail(nos)));
            inverse(
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

    private void propertiesDependentPairs() {
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
            assertTrue(p, all(Objects::nonNull, pairs));
            assertTrue(p, isSubsetOf(map(q -> q.a, pairs), p.a));
            assertTrue(p, isSubsetOf(map(q -> q.b, pairs), concat(p.b.range())));
            assertEquals(p, length(pairs), sumInteger(toList(map(i -> length(p.b.apply(i)), p.a))));
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
            assertTrue(p, all(Objects::nonNull, take(TINY_LIMIT, pairs)));
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

    private void propertiesDependentPairsInfinite() {
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
            assertTrue(p, all(Objects::nonNull, take(TINY_LIMIT, pairs)));
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
        psFail = map(
                g,
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integers()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail2 = map(
                p -> new Pair<>(
                        p.a,
                        new FiniteDomainFunction<>(toMap(map(e -> new Pair<>(e.a, cycle(e.b)), fromMap(p.b))))
                ),
                nub(P.dependentPairsInfinite(PS.listsAtLeast(1, P.integers()), f))
        );
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static class IntNoHashCode {
        private int i;

        public IntNoHashCode(int i) {
            this.i = i;
        }

        public int hashCode() {
            throw new UnsupportedOperationException();
        }

        public @NotNull String toString() {
            return Integer.toString(i);
        }
    }

    private void propertiesDependentPairsInfiniteIdentityHash() {
        initialize("dependentPairsInfiniteIdentityHash(Iterable<A>, Function<A, Iterable<B>>)");
        IterableProvider PS = P.withScale(4);
        Function<List<IntNoHashCode>, Iterable<IdentityHashMap<IntNoHashCode, List<Integer>>>> f = xs ->
                filterInfinite(
                        m -> !all(p -> isEmpty(p.b), fromMap(m)),
                        PS.identityMaps(xs, map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric())))
                );
        Function<
                Pair<List<IntNoHashCode>, IdentityHashMap<IntNoHashCode, List<Integer>>>,
                Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>>
        > g = p -> {
            Iterable<Pair<IntNoHashCode, List<Integer>>> values = fromMap(p.b);
            IdentityHashMap<IntNoHashCode, Iterable<Integer>> transformedValues = toIdentityMap(
                    map(e -> new Pair<>(e.a, cycle(e.b)), values)
            );
            return new Pair<>(cycle(p.a), new IdentityFiniteDomainFunction<>(transformedValues));
        };
        Iterable<Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>>> ps =
                map(
                        g,
                        P.dependentPairsInfiniteIdentityHash(
                                map(
                                        IterableUtils::unrepeat,
                                        PS.listsAtLeast(1, map(IntNoHashCode::new, P.integersGeometric()))
                                ),
                                f
                        )
                );
        for (Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>> p :
                take(LIMIT, ps)) {
            Iterable<Pair<IntNoHashCode, Integer>> pairs = EP.dependentPairsInfiniteIdentityHash(p.a, p.b);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(p, all(Objects::nonNull, take(TINY_LIMIT, pairs)));
        }

        Iterable<
                Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>>
        > psFail = map(
                p -> p.b,
                P.dependentPairs(
                        filterInfinite(r -> r.b.domainSize() != 0, ps),
                        q -> map(k -> new Pair<>(q.a, q.b.set(k, null)), P.uniformSample(q.b.domain()))
                )
        );
        for (Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>> p :
                take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairsInfiniteIdentityHash(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }

        f = xs -> {
            if (xs.isEmpty()) {
                return repeat(new IdentityHashMap<>());
            } else {
                return filter(m -> !all(p -> isEmpty(p.b), fromMap(m)), PS.identityMaps(xs, PS.lists(P.integers())));
            }
        };
        g = p -> {
            Iterable<Pair<IntNoHashCode, List<Integer>>> values = fromMap(p.b);
            IdentityHashMap<IntNoHashCode, Iterable<Integer>> transformedValues = toIdentityMap(
                    map(e -> new Pair<>(e.a, (Iterable<Integer>) e.b), values)
            );
            return new Pair<>(cycle(p.a), new IdentityFiniteDomainFunction<>(transformedValues));
        };
        psFail = map(
                g,
                P.dependentPairsInfiniteIdentityHash(
                        map(IterableUtils::unrepeat, PS.listsAtLeast(1, map(IntNoHashCode::new, P.integers()))),
                        f
                )
        );
        for (Pair<Iterable<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>> p :
                take(LIMIT, psFail)) {
            try {
                toList(EP.dependentPairsInfiniteIdentityHash(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Pair<List<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>>> psFail2 =
                map(
                        p -> new Pair<>(
                                p.a,
                                new IdentityFiniteDomainFunction<>(
                                        toIdentityMap(map(e -> new Pair<>(e.a, cycle(e.b)), fromMap(p.b)))
                                )
                        ),
                        P.dependentPairsInfiniteIdentityHash(
                                PS.listsAtLeast(1, map(IntNoHashCode::new, P.integers())),
                                f
                        )
                );
        for (Pair<List<IntNoHashCode>, IdentityFiniteDomainFunction<IntNoHashCode, Iterable<Integer>>> p :
                take(LIMIT, psFail2)) {
            try {
                toList(EP.dependentPairsInfiniteIdentityHash(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private void propertiesDependentPairsInfiniteLogarithmicOrder() {
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
            assertTrue(p, all(Objects::nonNull, take(TINY_LIMIT, pairs)));
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

    private void propertiesDependentPairsInfiniteSquareRootOrder() {
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
            assertTrue(p, all(Objects::nonNull, take(TINY_LIMIT, pairs)));
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

    private void propertiesPairsLogarithmicOrder_Iterable_Iterable() {
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

    private void propertiesPairsLogarithmicOrder_Iterable() {
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

    private void compareImplementationsPairsLogarithmicOrder_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairsLogarithmicOrder_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairsLogarithmicOrder(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> xs.size() < TINY_LIMIT,
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("pairsLogarithmicOrder(Iterable<T>)", take(LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesPairsSquareRootOrder_Iterable_Iterable() {
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

    private void propertiesPairsSquareRootOrder_Iterable() {
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
                        p.b.a < MathUtils.ceilingRoot(3, BigInteger.valueOf(p.a).pow(2)).intValueExact() * 2 + 1
                );
                assertTrue(p, p.b.b < MathUtils.ceilingRoot(3, BigInteger.valueOf(p.a)).intValueExact() * 2 + 1);
            }
        }
    }

    private void compareImplementationsPairsSquareRootOrder_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairsSquareRootOrder_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairsSquareRootOrder(xs)));
        Iterable<List<Integer>> iss = P.withScale(4).lists(P.integersGeometric());
        compareImplementations("pairsSquareRootOrder(Iterable<T>)", take(LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesPermutationsFinite() {
        initialize("permutationsFinite(List<T>)");
        Comparator<Integer> comparator = Comparator.nullsFirst(Comparator.naturalOrder());
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

    private void propertiesStringPermutations() {
        initialize("stringPermutations(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> permutations = EP.stringPermutations(s);
            testNoRemove(TINY_LIMIT, permutations);
            if (s.length() < 10) {
                Comparator<Character> cComparator = new ListBasedComparator<>(toList(s));
                testHasNext(permutations);
                List<String> permutationsList = toList(permutations);
                assertEquals(s, head(permutationsList), sort(cComparator, s));
                assertEquals(s, last(permutationsList), reverse(sort(cComparator, s)));
                assertEquals(s, permutationsList.size(), MathUtils.permutationCount(toList(s)).intValueExact());
                String sorted = sort(s);
                assertTrue(s, all(p -> sort(p).equals(sorted), permutationsList));
                assertTrue(s, unique(permutationsList));
                assertTrue(s, increasing(new StringLexComparator(cComparator), permutationsList));
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

    private void propertiesPrefixPermutations() {
        initialize("prefixPermutations(Iterable<T>)");
        Comparator<Integer> comparator = Comparator.nullsFirst(Comparator.naturalOrder());
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
                    toList(map(IterableUtils::toList, EP.prefixPermutations(Collections.singletonList(i)))),
                    Collections.singletonList(Collections.singletonList(i))
            );
        }

        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.withNull(P.integersGeometric())))) {
            assertEquals(
                    p,
                    toList(map(IterableUtils::toList, EP.prefixPermutations(Arrays.asList(p.a, p.b)))),
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
                                EP.rangeIncreasing(BigInteger.ZERO, base.pow(size).subtract(BigInteger.ONE))
                        )
                );
        }
    }

    private void propertiesListsLex_int_List() {
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

    private void compareImplementationsListsLex_int_List() {
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
        compareImplementations("listsLex(int, List<T>)", take(LIMIT, ps), functions, v -> P.reset());
    }

    private void propertiesPairsLex() {
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

    private void propertiesTriplesLex() {
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

    private void propertiesQuadruplesLex() {
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

    private void propertiesQuintuplesLex() {
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

    private void propertiesSextuplesLex() {
        initialize("sextuplesLex(Iterable<A>, List<B>, List<C>, List<D>, List<E>, List<F>)");
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

    private void propertiesSeptuplesLex() {
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
                return Collections.singletonList(replicateString(size, s.charAt(0)));
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
                                EP.rangeIncreasing(BigInteger.ZERO, base.pow(size).subtract(BigInteger.ONE))
                        )
                );
        }
    }

    private void propertiesStringsLex_int_String() {
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
                    assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
                    assertEquals(p, last(stringsList), replicateString(p.b, last(p.a)));
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
                assertTrue(p, increasing(new StringLexComparator(new StringBasedComparator(p.a)), stringsList));
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

    private void compareImplementationsStringsLex_int_String() {
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
        compareImplementations("stringsLex(int, String)", take(LIMIT, ps), functions, v -> P.reset());
    }

    private void propertiesListsShortlex() {
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

    private void propertiesStringsShortlex() {
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

    private void propertiesListsShortlexAtLeast() {
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

    private void propertiesStringsShortlexAtLeast() {
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
                assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
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

    private void propertiesLists_int_Iterable() {
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
            assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
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

    private void propertiesPairs_Iterable_Iterable() {
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

    private void propertiesPairs_Iterable() {
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
                int root = MathUtils.ceilingRoot(2, BigInteger.valueOf(p.a)).intValueExact() * 2 + 1;
                assertTrue(p, p.b.a < root);
                assertTrue(p, p.b.b < root);
            }
        }
    }

    private void compareImplementationsPairs_Iterable() {
        Map<String, Function<List<Integer>, List<Pair<Integer, Integer>>>> functions = new LinkedHashMap<>();
        functions.put("simplest", ExhaustiveProviderProperties::pairs_Iterable_simplest);
        functions.put("standard", xs -> toList(EP.pairs(xs)));
        Iterable<List<Integer>> iss = filterInfinite(
                xs -> lt(BigInteger.valueOf(xs.size()).pow(2), BigInteger.valueOf(LIMIT)),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        compareImplementations("pairs(Iterable<T>)", take(LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesTriples_Iterable_Iterable_Iterable() {
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

    private void propertiesTriples_Iterable() {
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
                int root = MathUtils.ceilingRoot(3, BigInteger.valueOf(t.a)).intValueExact() * 2 + 1;
                assertTrue(t, t.b.a < root);
                assertTrue(t, t.b.b < root);
                assertTrue(t, t.b.c < root);
            }
        }
    }

    private void compareImplementationsTriples_Iterable() {
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
        compareImplementations("triples(Iterable<T>)", take(LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesQuadruples_Iterable_Iterable_Iterable_Iterable() {
        initialize("quadruples(Iterable<A>, Iterable<B>, Iterable<C>, Iterable<D>)");
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(MEDIUM_LIMIT, qs)) {
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
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(MEDIUM_LIMIT, qs)) {
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

    private void propertiesQuadruples_Iterable() {
        initialize("quadruples(Iterable<T>)");
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
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

        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
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
                int root = MathUtils.ceilingRoot(4, BigInteger.valueOf(q.a)).intValueExact() * 2 + 1;
                assertTrue(q, q.b.a < root);
                assertTrue(q, q.b.b < root);
                assertTrue(q, q.b.c < root);
                assertTrue(q, q.b.d < root);
            }
        }
    }

    private void compareImplementationsQuadruples_Iterable() {
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
        compareImplementations("quadruples(Iterable<T>)", take(MEDIUM_LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable() {
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
        > q : take(MEDIUM_LIMIT, qs)) {
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
        > q : take(MEDIUM_LIMIT, qs)) {
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

    private void propertiesQuintuples_Iterable() {
        initialize("quintuples(Iterable<T>)");
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
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

        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
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
                int root = MathUtils.ceilingRoot(5, BigInteger.valueOf(q.a)).intValueExact() * 2 + 1;
                assertTrue(q, q.b.a < root);
                assertTrue(q, q.b.b < root);
                assertTrue(q, q.b.c < root);
                assertTrue(q, q.b.d < root);
                assertTrue(q, q.b.e < root);
            }
        }
    }

    private void compareImplementationsQuintuples_Iterable() {
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
        compareImplementations("quintuples(Iterable<T>)", take(MEDIUM_LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
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
        > s : take(MEDIUM_LIMIT, ss)) {
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
        > s : take(MEDIUM_LIMIT, ss)) {
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

    private void propertiesSextuples_Iterable() {
        initialize("sextuples(Iterable<T>)");
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
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

        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
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
                int root = MathUtils.ceilingRoot(6, BigInteger.valueOf(s.a)).intValueExact() * 2 + 1;
                assertTrue(s, s.b.a < root);
                assertTrue(s, s.b.b < root);
                assertTrue(s, s.b.c < root);
                assertTrue(s, s.b.d < root);
                assertTrue(s, s.b.e < root);
                assertTrue(s, s.b.f < root);
            }
        }
    }

    private void compareImplementationsSextuples_Iterable() {
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
        compareImplementations("sextuples(Iterable<T>)", take(MEDIUM_LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable() {
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
        > s : take(MEDIUM_LIMIT, ss)) {
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
        > s : take(MEDIUM_LIMIT, ss)) {
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

    private void propertiesSeptuples_Iterable() {
        initialize("septuples(Iterable<T>)");
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
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

        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
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
                int root = MathUtils.ceilingRoot(7, BigInteger.valueOf(s.a)).intValueExact() * 2 + 1;
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

    private void compareImplementationsSeptuples_Iterable() {
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
        compareImplementations("septuples(Iterable<T>)", take(MEDIUM_LIMIT, iss), functions, v -> P.reset());
    }

    private void propertiesStrings_int_String() {
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
                    assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
                    assertEquals(p, last(stringsList), replicateString(p.b, last(p.a)));
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

    private void propertiesStrings_int() {
        initialize("strings(int)");
        for (int i : take(MEDIUM_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
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

    private void propertiesLists_Iterable() {
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

        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<List<Integer>> lists = EP.lists(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            assertTrue(xs, unique(listsList));
        }
    }

    private void propertiesStrings_String() {
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

    private void propertiesStrings() {
        initializeConstant("strings()");
        biggerTest(EP, EP.strings(), s -> true);
    }

    private void propertiesListsAtLeast() {
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
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.listsAtLeast(p.b, p.a)));
            assertTrue(p, unique(listsList));
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.listsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
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

    private void propertiesStringsAtLeast_int_String() {
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
                assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
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

    private void propertiesStringsAtLeast_int() {
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

    private void propertiesDistinctListsLex_int_List() {
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

    private void propertiesDistinctPairsLex() {
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
                            IterableUtils.equal(filter(p -> unique(Pair.toList(p)), EP.pairsLex(xs, xs)), pairsList)
                    );
                }
            }
        }
    }

    private void propertiesDistinctTriplesLex() {
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
                                    filter(t -> unique(Triple.toList(t)), EP.triplesLex(xs, xs, xs)),
                                    triplesList
                            )
                    );
                }
            }
        }
    }

    private void propertiesDistinctQuadruplesLex() {
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
                                    filter(q -> unique(Quadruple.toList(q)), EP.quadruplesLex(xs, xs, xs, xs)),
                                    quadruplesList
                            )
                    );
                }
            }
        }
    }

    private void propertiesDistinctQuintuplesLex() {
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
                                    filter(q -> unique(Quintuple.toList(q)), EP.quintuplesLex(xs, xs, xs, xs, xs)),
                                    quintuplesList
                            )
                    );
                }
            }
        }
    }

    private void propertiesDistinctSextuplesLex() {
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
                                    filter(s -> unique(Sextuple.toList(s)), EP.sextuplesLex(xs, xs, xs, xs, xs, xs)),
                                    sextuplesList
                            )
                    );
                }
            }
        }
    }

    private void propertiesDistinctSeptuplesLex() {
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
                                            s -> unique(Septuple.toList(s)),
                                            EP.septuplesLex(xs, xs, xs, xs, xs, xs, xs)
                                    ),
                                    septuplesList
                            )
                    );
                }
            }
        }
    }

    private void propertiesDistinctStringsLex_int_String() {
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
                assertTrue(p, increasing(new StringLexComparator(new StringBasedComparator(p.a)), stringsList));
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

    private void propertiesDistinctListsLex_List() {
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

    private void propertiesDistinctStringsLex_String() {
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
                assertTrue(s, increasing(new StringLexComparator(new StringBasedComparator(s)), stringsList));
            }
        }
    }

    private void propertiesDistinctListsLexAtLeast() {
        initialize("distinctListsLexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.distinctListsLexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), toList(take(p.b, p.a)));
                    assertEquals(p, last(listsList), reverse(p.a));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctListsLexAtLeast(p.b, p.a));
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
            Iterable<List<Integer>> xss = EP.distinctListsLexAtLeast(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctListsLexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctStringsLexAtLeast() {
        initialize("distinctStringsLexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.distinctStringsLexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), take(p.b, p.a));
                    assertEquals(p, last(stringsList), reverse(p.a));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(xs -> xs.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.distinctStringsLexAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, increasing(new StringLexComparator(new StringBasedComparator(p.a)), stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.distinctStringsLexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctStringsLexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctListsShortlex() {
        initialize("distinctListsShortlex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> lists = EP.distinctListsShortlex(xs);
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
                List<List<Integer>> listsList = toList(EP.distinctListsShortlex(xs));
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
    }

    private void propertiesDistinctStringsShortlex() {
        initialize("distinctStringsShortlex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.distinctStringsShortlex(s);
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
                List<String> stringsList = toList(EP.distinctStringsShortlex(s));
                assertTrue(s, unique(stringsList));
                assertTrue(
                        s,
                        increasing(
                                new ShortlexComparator<>(new ListBasedComparator<>(toList(s))),
                                map(IterableUtils::toList, stringsList)
                        )
                );
            }
        }
    }

    private void propertiesDistinctListsShortlexAtLeast() {
        initialize("distinctListsShortlexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.distinctListsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), toList(take(p.b, p.a)));
                    assertEquals(p, last(listsList), reverse(p.a));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctListsShortlexAtLeast(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(
                        p,
                        increasing(
                                new ShortlexComparator<>(new ListBasedComparator<>(p.a)),
                                map(ys -> ((Iterable<Integer>) ys), listsList)
                        )
                );
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.distinctListsShortlexAtLeast(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctListsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctStringsShortlexAtLeast() {
        initialize("distinctStringsShortlexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.distinctStringsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), take(p.b, p.a));
                    assertEquals(p, last(stringsList), reverse(p.a));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(xs -> xs.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.distinctStringsShortlexAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(
                        p,
                        increasing(
                                new ShortlexComparator<>(new ListBasedComparator<>(toList(p.a))),
                                map(IterableUtils::toList, stringsList)
                        )
                );
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.distinctStringsShortlexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctStringsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctLists_int_Iterable() {
        initialize("distinctLists(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.distinctLists(p.b, p.a);
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
                List<List<Integer>> listsList = toList(EP.distinctLists(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, all(IterableUtils::unique, listsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.distinctLists(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), toList(take(p.b, p.a)));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            assertTrue(p, all(IterableUtils::unique, listsList));
            assertTrue(p, unique(listsList));
        }

        ps = filter(
                p -> p.a.size() < p.b,
                P.pairsLogarithmicOrder(
                        P.withScale(4).lists(P.withNull(P.integersGeometric())),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> xss = EP.distinctLists(p.b, p.a);
            testHasNext(xss);
            assertEquals(p, toList(xss), Collections.emptyList());
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> xss = EP.distinctLists(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctLists(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctPairs() {
        initialize("distinctPairs(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.distinctPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.distinctPairs(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(IterableUtils::unique, map(Pair::toList, pairsList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.distinctPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(xs, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            for (Pair<Integer, Integer> p : pairsList) {
                assertTrue(xs, elem(p.a, xs));
                assertTrue(xs, elem(p.b, xs));
            }
        }
    }

    private void propertiesDistinctTriples() {
        initialize("distinctTriples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.distinctTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.distinctTriples(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Triple::toList, triplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.distinctTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            assertTrue(xs, unique(take(TINY_LIMIT, triples)));
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            for (Triple<Integer, Integer, Integer> t : triplesList) {
                assertTrue(xs, elem(t.a, xs));
                assertTrue(xs, elem(t.b, xs));
                assertTrue(xs, elem(t.c, xs));
            }
        }
    }

    private void propertiesDistinctQuadruples() {
        initialize("distinctQuadruples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.distinctQuadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(EP.distinctQuadruples(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Quadruple::toList, quadruplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.distinctQuadruples(xs);
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
    }

    private void propertiesDistinctQuintuples() {
        initialize("distinctQuintuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.distinctQuintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.distinctQuintuples(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Quintuple::toList, quintuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.distinctQuintuples(xs);
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
    }

    private void propertiesDistinctSextuples() {
        initialize("distinctSextuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.distinctSextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.distinctSextuples(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Sextuple::toList, sextuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.distinctSextuples(xs);
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
    }

    private void propertiesDistinctSeptuples() {
        initialize("distinctSeptuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.distinctSeptuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.distinctSeptuples(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(IterableUtils::unique, map(Septuple::toList, septuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.distinctSeptuples(xs);
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
    }

    private void propertiesDistinctStrings_int_String() {
        initialize("distinctStrings(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.distinctStrings(p.b, p.a);
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
                List<String> stringsList = toList(EP.distinctStrings(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, all(IterableUtils::unique, stringsList));
            }
        }

        ps = filter(
                p -> p.a.length() < p.b,
                P.pairsLogarithmicOrder(P.withScale(4).strings(), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> ss = EP.distinctStrings(p.b, p.a);
            testHasNext(ss);
            assertEquals(p, toList(ss), Collections.emptyList());
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.distinctStrings(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctStrings(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctStrings_int() {
        initialize("distinctStrings(int)");
        for (int i : take(MEDIUM_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.distinctStrings(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(take(i, EP.characters())));
            assertTrue(i, all(s -> s.length() == i, stringsList));
            assertTrue(i, all(IterableUtils::unique, stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(MEDIUM_LIMIT, P.rangeUp((1 << 16) + 1))) {
            assertEquals(i, toList(EP.distinctStrings(i)), Collections.emptyList());
        }

        for (int i : take(LIMIT, P.withScale(4).negativeIntegersGeometric())) {
            try {
                EP.distinctStrings(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctLists_Iterable() {
        initialize("distinctLists(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            Iterable<List<Integer>> lists = EP.distinctLists(xs);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(xs.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctLists(xs));
                assertTrue(xs, unique(listsList));
                assertTrue(xs, all(IterableUtils::unique, listsList));
            }
        }

        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            Iterable<List<Integer>> lists = EP.distinctLists(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            assertTrue(xs, all(IterableUtils::unique, listsList));
            assertTrue(xs, unique(listsList));
        }
    }

    private void propertiesDistinctStrings_String() {
        initialize("distinctStrings(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.distinctStrings(s);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(s.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
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
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(s.length());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(EP.distinctStrings(s));
                assertTrue(s, unique(stringsList));
                assertTrue(s, all(IterableUtils::unique, stringsList));
            }
        }
    }

    private void propertiesDistinctStrings() {
        initializeConstant("distinctStrings()");
        biggerTest(EP, EP.distinctStrings(), IterableUtils::unique);
    }

    private void propertiesDistinctListsAtLeast() {
        initialize("distinctListsAtLeast(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.distinctListsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), toList(take(p.b, p.a)));
                    assertEquals(p, last(listsList), reverse(p.a));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.distinctListsAtLeast(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, all(IterableUtils::unique, listsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.distinctListsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), toList(take(p.b, p.a)));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            assertTrue(p, all(IterableUtils::unique, listsList));
            assertTrue(p, unique(listsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.distinctListsAtLeast(i, Collections.emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctListsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctStringsAtLeast_int_String() {
        initialize("distinctStringsAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.distinctStringsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), take(p.b, p.a));
                    assertEquals(p, last(stringsList), reverse(p.a));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
                assertTrue(p, all(s -> s.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.numberOfArrangementsOfASet(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(EP.distinctStringsAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, all(IterableUtils::unique, stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.distinctStringsAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.distinctStringsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesDistinctStringsAtLeast_int() {
        initialize("distinctStringsAtLeast(int)");
        Iterable<Integer> is = filterInfinite(j -> j < (1 << 16) + 1, P.withScale(4).naturalIntegersGeometric());
        for (int i : take(TINY_LIMIT, is)) {
            Iterable<String> strings = EP.distinctStringsAtLeast(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(take(i, EP.characters())));
            assertTrue(i, all(s -> s.length() >= i, stringsList));
            assertTrue(i, all(IterableUtils::unique, stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(MEDIUM_LIMIT, P.rangeUp((1 << 16) + 1))) {
            assertEquals(i, toList(EP.distinctStringsAtLeast(i)), Collections.emptyList());
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                EP.distinctStringsAtLeast(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesBagsLex_int_List() {
        initialize("bagsLex(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.bagsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(listsList), toList(replicate(p.b, minimum(p.a))));
                    assertEquals(p, last(listsList), toList(replicate(p.b, maximum(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.bagsLex(p.b, p.a));
                assertTrue(p, increasing(new LexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
                BigInteger limit = BigInteger.valueOf(p.a.size()).pow(p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(
                                    filter(Ordering::weaklyIncreasing, EP.listsLex(p.b, sort(p.a))),
                                    listsList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> xss = EP.bagsLex(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.bagsLex(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.bagsLex(p.b, p.a);
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagPairsLex() {
        initialize("bagPairsLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.bagPairsLex(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!pairsList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(xs, head(pairsList), new Pair<>(minimum, minimum));
                    assertEquals(xs, last(pairsList), new Pair<>(maximum, maximum));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(p -> elem(p.a, xs), pairsList));
                assertTrue(xs, all(p -> elem(p.b, xs), pairsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger pairsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.bagPairsLex(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
                assertTrue(
                        xs,
                        increasing(
                                new Pair.PairComparator<Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                pairsList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(2);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(p -> weaklyIncreasing(Pair.toList(p)), EP.pairsLex(sorted, sorted)),
                                    pairsList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagPairsLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagTriplesLex() {
        initialize("bagTriplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.bagTriplesLex(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!triplesList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(xs, head(triplesList), new Triple<>(minimum, minimum, minimum));
                    assertEquals(xs, last(triplesList), new Triple<>(maximum, maximum, maximum));
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(t -> elem(t.a, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.b, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.c, xs), triplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger triplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.bagTriplesLex(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Triple.TripleComparator<Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                triplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(3);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            t -> weaklyIncreasing(Triple.toList(t)),
                                            EP.triplesLex(sorted, sorted, sorted)
                                    ),
                                    triplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagTriplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagQuadruplesLex() {
        initialize("bagQuadruplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.bagQuadruplesLex(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!quadruplesList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(xs, head(quadruplesList), new Quadruple<>(minimum, minimum, minimum, minimum));
                    assertEquals(xs, last(quadruplesList), new Quadruple<>(maximum, maximum, maximum, maximum));
                }
                assertEquals(xs, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(xs, all(q -> elem(q.a, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quadruplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quadruplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(EP.bagQuadruplesLex(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quadruple.QuadrupleComparator<Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                quadruplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(4);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            q -> weaklyIncreasing(Quadruple.toList(q)),
                                            EP.quadruplesLex(sorted, sorted, sorted, sorted)
                                    ),
                                    quadruplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagQuadruplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagQuintuplesLex() {
        initialize("bagQuintuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.bagQuintuplesLex(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!quintuplesList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(
                            xs,
                            head(quintuplesList),
                            new Quintuple<>(minimum, minimum, minimum, minimum, minimum)
                    );
                    assertEquals(
                            xs,
                            last(quintuplesList),
                            new Quintuple<>(maximum, maximum, maximum, maximum, maximum)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quintuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.bagQuintuplesLex(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quintuple.QuintupleComparator<Integer, Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                quintuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(5);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            q -> weaklyIncreasing(Quintuple.toList(q)),
                                            EP.quintuplesLex(sorted, sorted, sorted, sorted, sorted)
                                    ),
                                    quintuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagQuintuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagSextuplesLex() {
        initialize("bagSextuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.bagSextuplesLex(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (!sextuplesList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(
                            xs,
                            head(sextuplesList),
                            new Sextuple<>(minimum, minimum, minimum, minimum, minimum, minimum)
                    );
                    assertEquals(
                            xs,
                            last(sextuplesList),
                            new Sextuple<>(maximum, maximum, maximum, maximum, maximum, maximum)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger sextuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.bagSextuplesLex(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Sextuple.SextupleComparator<Integer, Integer, Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                sextuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(6);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            s -> weaklyIncreasing(Sextuple.toList(s)),
                                            EP.sextuplesLex(sorted, sorted, sorted, sorted, sorted, sorted)
                                    ),
                                    sextuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagSextuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagSeptuplesLex() {
        initialize("bagSeptuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.bagSeptuplesLex(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (!septuplesList.isEmpty()) {
                    int minimum = minimum(xs);
                    int maximum = maximum(xs);
                    assertEquals(
                            xs,
                            head(septuplesList),
                            new Septuple<>(minimum, minimum, minimum, minimum, minimum, minimum, minimum)
                    );
                    assertEquals(
                            xs,
                            last(septuplesList),
                            new Septuple<>(maximum, maximum, maximum, maximum, maximum, maximum, maximum)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger septuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.bagSeptuplesLex(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Septuple.SeptupleComparator<
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer
                                >(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                septuplesList
                        )
                );
                BigInteger limit = BigInteger.valueOf(xs.size()).pow(7);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            s -> weaklyIncreasing(Septuple.toList(s)),
                                            EP.septuplesLex(sorted, sorted, sorted, sorted, sorted, sorted, sorted)
                                    ),
                                    septuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.bagSeptuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBagsLex() {
        initialize("stringBagsLex(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringBagsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(stringsList), replicateString(p.b, minimum(p.a)));
                    assertEquals(p, last(stringsList), replicateString(p.b, maximum(p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(fromString(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringBagsLex(p.b, p.a));
                assertTrue(p, increasing(stringsList));
                BigInteger limit = BigInteger.valueOf(p.a.length()).pow(p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(
                                    filter(s -> weaklyIncreasing(fromString(s)), EP.stringsLex(p.b, sort(p.a))),
                                    stringsList
                            )
                    );
                }
            }
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.stringBagsLex(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringBagsLex(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesBagsShortlex() {
        initialize("bagsShortlex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> lists = EP.bagsShortlex(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.bagsShortlex(xs)));
            assertTrue(xs, unique(listsList));
            assertTrue(xs, all(Ordering::weaklyIncreasing, listsList));
            assertTrue(xs, increasing(new ShortlexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagsShortlex(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBagsShortlex() {
        initialize("stringBagsShortlex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringBagsShortlex(s);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(s, head(stringsList), "");
            assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringBagsShortlex(s)));
            assertTrue(s, unique(stringsList));
            assertTrue(s, all(t -> weaklyIncreasing(toList(t)), stringsList));
            assertTrue(s, increasing(new StringShortlexComparator(), stringsList));
        }
    }

    private void propertiesBagsShortlexAtLeast() {
        initialize("bagsShortlexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.bagsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(listsList), toList(replicate(p.b, minimum(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.bagsShortlexAtLeast(p.b, p.a)));
            assertTrue(p, unique(listsList));
            assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
            assertTrue(p, increasing(new ShortlexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.bagsShortlexAtLeast(i, Collections.<Integer>emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.bagsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(EP.bagsShortlexAtLeast(p.b, p.a));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBagsShortlexAtLeast() {
        initialize("stringBagsShortlexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringBagsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(stringsList), replicateString(p.b, minimum(p.a)));
            }
            assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
            assertTrue(p, all(s -> s.length() >= p.b, stringsList));
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringBagsShortlexAtLeast(p.b, p.a)));
            assertTrue(p, unique(stringsList));
            assertTrue(p, all(t -> weaklyIncreasing(toList(t)), stringsList));
            assertTrue(p, increasing(new StringShortlexComparator(), stringsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringBagsShortlexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringBagsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesBags_int_Iterable() {
        initialize("bags(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.bags(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
                    assertEquals(p, last(listsList), toList(replicate(p.b, last(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.bags(p.b, p.a));
                assertTrue(p, unique(listsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.bags(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
            assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            assertTrue(p, unique(listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> xss = EP.bags(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.bags(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<List<Integer>, Integer>> psFail2 = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).positiveIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.bags(p.b, p.a));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagPairs() {
        initialize("bagPairs(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.bagPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!pairsList.isEmpty()) {
                    assertEquals(xs, head(pairsList), new Pair<>(head(xs), head(xs)));
                    assertEquals(xs, last(pairsList), new Pair<>(last(xs), last(xs)));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(p -> elem(p.a, xs), pairsList));
                assertTrue(xs, all(p -> elem(p.b, xs), pairsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger pairsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.bagPairs(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.bagPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(xs, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
            for (Pair<Integer, Integer> p : pairsList) {
                assertTrue(xs, elem(p.a, xs));
                assertTrue(xs, elem(p.b, xs));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagPairs(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagTriples() {
        initialize("bagTriples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.bagTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!triplesList.isEmpty()) {
                    assertEquals(xs, head(triplesList), new Triple<>(head(xs), head(xs), head(xs)));
                    assertEquals(xs, last(triplesList), new Triple<>(last(xs), last(xs), last(xs)));
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(t -> elem(t.a, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.b, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.c, xs), triplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger triplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.bagTriples(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.bagTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            assertTrue(xs, unique(take(TINY_LIMIT, triples)));
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
            for (Triple<Integer, Integer, Integer> t : triplesList) {
                assertTrue(xs, elem(t.a, xs));
                assertTrue(xs, elem(t.b, xs));
                assertTrue(xs, elem(t.c, xs));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagTriples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagQuadruples() {
        initialize("bagQuadruples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.bagQuadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!quadruplesList.isEmpty()) {
                    assertEquals(xs, head(quadruplesList), new Quadruple<>(head(xs), head(xs), head(xs), head(xs)));
                    assertEquals(xs, last(quadruplesList), new Quadruple<>(last(xs), last(xs), last(xs), last(xs)));
                }
                assertEquals(xs, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(xs, all(q -> elem(q.a, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quadruplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quadruplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(EP.bagQuadruples(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.bagQuadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            assertTrue(xs, unique(take(TINY_LIMIT, quadruples)));
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(take(TINY_LIMIT, quadruples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
            for (Quadruple<Integer, Integer, Integer, Integer> q : quadruplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagQuadruples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagQuintuples() {
        initialize("bagQuintuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.bagQuintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!quintuplesList.isEmpty()) {
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
                assertTrue(xs, all(q -> elem(q.a, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quintuplesList));
                assertTrue(xs, all(q -> elem(q.e, xs), quintuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quintuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.bagQuintuples(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.bagQuintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            assertTrue(xs, unique(take(TINY_LIMIT, quintuples)));
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                    toList(take(TINY_LIMIT, quintuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
            for (Quintuple<Integer, Integer, Integer, Integer, Integer> q : quintuplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
                assertTrue(xs, elem(q.e, xs));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagQuintuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagSextuples() {
        initialize("bagSextuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples = EP.bagSextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 6);
                if (lt(sextuplesLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (!sextuplesList.isEmpty()) {
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
                assertTrue(xs, all(s -> elem(s.a, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.b, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.c, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.d, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.e, xs), sextuplesList));
                assertTrue(xs, all(s -> elem(s.f, xs), sextuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger sextuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.bagSextuples(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples = EP.bagSextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            assertTrue(xs, unique(take(TINY_LIMIT, sextuples)));
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                    toList(take(TINY_LIMIT, sextuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
            for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s : sextuplesList) {
                assertTrue(xs, elem(s.a, xs));
                assertTrue(xs, elem(s.b, xs));
                assertTrue(xs, elem(s.c, xs));
                assertTrue(xs, elem(s.d, xs));
                assertTrue(xs, elem(s.e, xs));
                assertTrue(xs, elem(s.f, xs));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagSextuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesBagSeptuples() {
        initialize("bagSeptuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.bagSeptuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (!septuplesList.isEmpty()) {
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
                assertTrue(xs, all(s -> elem(s.a, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.b, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.c, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.d, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.e, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.f, xs), septuplesList));
                assertTrue(xs, all(s -> elem(s.g, xs), septuplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger septuplesLength = MathUtils.multisetCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.bagSeptuples(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.bagSeptuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            assertTrue(xs, unique(take(TINY_LIMIT, septuples)));
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                    toList(take(TINY_LIMIT, septuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bagSeptuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBags_int_String() {
        initialize("stringBags(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringBags(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!p.a.isEmpty()) {
                    assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
                    assertEquals(p, last(stringsList), replicateString(p.b, last(p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(toList(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.multisetCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<String> stringsList = toList(EP.stringBags(p.b, p.a));
                assertTrue(p, unique(stringsList));
            }
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.stringBags(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringBags(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringBags_int() {
        initialize("stringBags(int)");
        for (int i : take(MEDIUM_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.stringBags(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), sort(charsToString(replicate(i, head(EP.characters())))));
            assertTrue(i, all(s -> s.length() == i, stringsList));
            assertTrue(i, all(s -> weaklyIncreasing(toList(s)), stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(LIMIT, P.withScale(4).negativeIntegersGeometric())) {
            try {
                EP.stringBags(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesBags_Iterable() {
        initialize("bags(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> lists = EP.bags(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.bags(xs)));
            assertTrue(xs, unique(listsList));
            assertTrue(xs, all(Ordering::weaklyIncreasing, listsList));
        }

        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<List<Integer>> lists = EP.bags(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            assertTrue(xs, unique(listsList));
            assertTrue(xs, all(Ordering::weaklyIncreasing, listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.bags(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBags_String() {
        initialize("stringBags(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringBags(s);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(s, head(stringsList), "");
            assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
            assertTrue(s, all(t -> weaklyIncreasing(toList(t)), stringsList));
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringBags(s)));
            assertTrue(s, unique(stringsList));
            assertTrue(s, all(t -> weaklyIncreasing(toList(t)), stringsList));
        }
    }

    private void propertiesStringBags() {
        initializeConstant("stringBags()");
        biggerTest(EP, EP.stringBags(), s -> weaklyIncreasing(toList(s)));
    }

    private void propertiesBagsAtLeast() {
        initialize("bagsAtLeast(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.bagsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            }
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, EP.bagsAtLeast(p.b, p.a)));
            assertTrue(p, unique(listsList));
            assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.bagsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), toList(replicate(p.b, head(p.a))));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            assertTrue(p, unique(listsList));
            assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.bagsAtLeast(i, Collections.<Integer>emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.bagsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(EP.bagsAtLeast(p.b, p.a));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringBagsAtLeast_int_String() {
        initialize("stringBagsAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringBagsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            if (!p.a.isEmpty()) {
                assertEquals(p, head(stringsList), replicateString(p.b, head(p.a)));
            }
            assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
            assertTrue(p, all(s -> s.length() >= p.b, stringsList));
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            List<String> stringsList = toList(take(TINY_LIMIT, EP.stringBagsAtLeast(p.b, p.a)));
            assertTrue(p, unique(stringsList));
            assertTrue(p, all(t -> weaklyIncreasing(toList(t)), stringsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringBagsAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringBagsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringBagsAtLeast_int() {
        initialize("stringBagsAtLeast(int)");
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.stringBagsAtLeast(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(replicate(i, head(EP.characters()))));
            assertTrue(i, all(s -> s.length() >= i, stringsList));
            assertTrue(i, unique(stringsList));
            assertTrue(i, all(t -> weaklyIncreasing(toList(t)), stringsList));
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                EP.stringBagsAtLeast(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesSubsetsLex_int_List() {
        initialize("subsetsLex(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.subsetsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!listsList.isEmpty()) {
                    assertEquals(p, head(listsList), toList(take(p.b, sort(p.a))));
                    assertEquals(p, last(listsList), reverse(take(p.b, reverse(sort(p.a)))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsLex(p.b, p.a));
                assertTrue(p, all(IterableUtils::unique, listsList));
                assertTrue(p, increasing(new LexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.size()), p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(
                                    filter(Ordering::weaklyIncreasing, EP.distinctListsLex(p.b, sort(p.a))),
                                    listsList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> xss = EP.subsetsLex(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsLex(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsLex(p.b, p.a);
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetPairsLex() {
        initialize("subsetPairsLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.subsetPairsLex(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!pairsList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(xs, head(pairsList), new Pair<>(ys.get(0), ys.get(1)));
                    assertEquals(xs, last(pairsList), new Pair<>(ys.get(ys.size() - 2), ys.get(ys.size() - 1)));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(p -> elem(p.a, xs), pairsList));
                assertTrue(xs, all(p -> elem(p.b, xs), pairsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger pairsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.subsetPairsLex(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
                assertTrue(xs, all(IterableUtils::unique, map(Pair::toList, pairsList)));
                assertTrue(
                        xs,
                        increasing(
                                new Pair.PairComparator<Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                pairsList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 2);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(p -> weaklyIncreasing(Pair.toList(p)), EP.distinctPairsLex(sorted)),
                                    pairsList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetPairsLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetTriplesLex() {
        initialize("subsetTriplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.subsetTriplesLex(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!triplesList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(xs, head(triplesList), new Triple<>(ys.get(0), ys.get(1), ys.get(2)));
                    assertEquals(
                            xs,
                            last(triplesList),
                            new Triple<>(ys.get(ys.size() - 3), ys.get(ys.size() - 2), ys.get(ys.size() - 1))
                    );
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(t -> elem(t.a, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.b, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.c, xs), triplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger triplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.subsetTriplesLex(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
                assertTrue(xs, all(IterableUtils::unique, map(Triple::toList, triplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Triple.TripleComparator<Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                triplesList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 3);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(p -> weaklyIncreasing(Triple.toList(p)), EP.distinctTriplesLex(sorted)),
                                    triplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetTriplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetQuadruplesLex() {
        initialize("subsetQuadruplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.subsetQuadruplesLex(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!quadruplesList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(
                            xs,
                            head(quadruplesList),
                            new Quadruple<>(ys.get(0), ys.get(1), ys.get(2), ys.get(3))
                    );
                    assertEquals(
                            xs,
                            last(quadruplesList),
                            new Quadruple<>(
                                    ys.get(ys.size() - 4),
                                    ys.get(ys.size() - 3),
                                    ys.get(ys.size() - 2),
                                    ys.get(ys.size() - 1)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quadruplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList =
                        toList(EP.subsetQuadruplesLex(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
                assertTrue(xs, all(IterableUtils::unique, map(Quadruple::toList, quadruplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quadruple.QuadrupleComparator<Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                quadruplesList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 4);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            p -> weaklyIncreasing(Quadruple.toList(p)),
                                            EP.distinctQuadruplesLex(sorted)
                                    ),
                                    quadruplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetQuadruplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetQuintuplesLex() {
        initialize("subsetQuintuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.subsetQuintuplesLex(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!quintuplesList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(
                            xs,
                            head(quintuplesList),
                            new Quintuple<>(ys.get(0), ys.get(1), ys.get(2), ys.get(3), ys.get(4))
                    );
                    assertEquals(
                            xs,
                            last(quintuplesList),
                            new Quintuple<>(
                                    ys.get(ys.size() - 5),
                                    ys.get(ys.size() - 4),
                                    ys.get(ys.size() - 3),
                                    ys.get(ys.size() - 2),
                                    ys.get(ys.size() - 1)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quintuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.subsetQuintuplesLex(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
                assertTrue(xs, all(IterableUtils::unique, map(Quintuple::toList, quintuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Quintuple.QuintupleComparator<Integer, Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                quintuplesList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 5);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            p -> weaklyIncreasing(Quintuple.toList(p)),
                                            EP.distinctQuintuplesLex(sorted)
                                    ),
                                    quintuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetQuintuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetSextuplesLex() {
        initialize("subsetSextuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.subsetSextuplesLex(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (!sextuplesList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(
                            xs,
                            head(sextuplesList),
                            new Sextuple<>(ys.get(0), ys.get(1), ys.get(2), ys.get(3), ys.get(4), ys.get(5))
                    );
                    assertEquals(
                            xs,
                            last(sextuplesList),
                            new Sextuple<>(
                                    ys.get(ys.size() - 6),
                                    ys.get(ys.size() - 5),
                                    ys.get(ys.size() - 4),
                                    ys.get(ys.size() - 3),
                                    ys.get(ys.size() - 2),
                                    ys.get(ys.size() - 1)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger sextuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.subsetSextuplesLex(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
                assertTrue(xs, all(IterableUtils::unique, map(Sextuple::toList, sextuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Sextuple.SextupleComparator<Integer, Integer, Integer, Integer, Integer, Integer>(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                sextuplesList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 6);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            p -> weaklyIncreasing(Sextuple.toList(p)),
                                            EP.distinctSextuplesLex(sorted)
                                    ),
                                    sextuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetSextuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetSeptuplesLex() {
        initialize("subsetSeptuplesLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.subsetSeptuplesLex(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (!septuplesList.isEmpty()) {
                    List<Integer> ys = sort(xs);
                    assertEquals(
                            xs,
                            head(septuplesList),
                            new Septuple<>(ys.get(0), ys.get(1), ys.get(2), ys.get(3), ys.get(4), ys.get(5), ys.get(6))
                    );
                    assertEquals(
                            xs,
                            last(septuplesList),
                            new Septuple<>(
                                    ys.get(ys.size() - 7),
                                    ys.get(ys.size() - 6),
                                    ys.get(ys.size() - 5),
                                    ys.get(ys.size() - 4),
                                    ys.get(ys.size() - 3),
                                    ys.get(ys.size() - 2),
                                    ys.get(ys.size() - 1)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger septuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.subsetSeptuplesLex(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
                assertTrue(xs, all(IterableUtils::unique, map(Septuple::toList, septuplesList)));
                assertTrue(
                        xs,
                        increasing(
                                new Septuple.SeptupleComparator<
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer,
                                        Integer
                                >(
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder(),
                                        Comparator.naturalOrder()
                                ),
                                septuplesList
                        )
                );
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(xs.size()), 7);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    List<Integer> sorted = sort(xs);
                    assertTrue(
                            xs,
                            IterableUtils.equal(
                                    filter(
                                            p -> weaklyIncreasing(Septuple.toList(p)),
                                            EP.distinctSeptuplesLex(sorted)
                                    ),
                                    septuplesList
                            )
                    );
                }
            }
        }

        for (List<Integer> xs : take(LIMIT, P.listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetSeptuplesLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsetsLex_int_String() {
        initialize("stringSubsetsLex(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringSubsetsLex(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!stringsList.isEmpty()) {
                    assertEquals(p, head(stringsList), take(p.b, sort(p.a)));
                    assertEquals(p, last(stringsList), reverse(take(p.b, reverse(sort(p.a)))));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(fromString(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsLex(p.b, p.a));
                assertTrue(p, increasing(stringsList));
                assertTrue(p, all(IterableUtils::unique, stringsList));
                BigInteger limit = MathUtils.fallingFactorial(BigInteger.valueOf(p.a.length()), p.b);
                if (lt(limit, BigInteger.valueOf(LIMIT))) {
                    assertTrue(
                            p,
                            IterableUtils.equal(
                                    filter(
                                            s -> weaklyIncreasing(fromString(s)),
                                            EP.distinctStringsLex(p.b, sort(p.a))
                                    ),
                                    stringsList
                            )
                    );
                }
            }
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.stringSubsetsLex(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringSubsetsLex(p.b, p.a);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private void propertiesSubsetsLex_List() {
        initialize("subsetsLex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> lists = EP.subsetsLex(xs);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!xs.isEmpty()) {
                    assertEquals(xs, head(listsList), Collections.emptyList());
                    assertEquals(xs, last(listsList), Collections.singletonList(maximum(xs)));
                }
                assertEquals(xs, listsList.size(), listsLength.intValueExact());
                assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, listsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsLex(xs));
                assertTrue(xs, unique(listsList));
                assertTrue(xs, increasing(new LexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                EP.subsetsLex(xs);
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsetsLex_String() {
        initialize("stringSubsetsLex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringSubsetsLex(s);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!s.isEmpty()) {
                    assertEquals(s, head(stringsList), "");
                    assertEquals(s, last(stringsList), Character.toString(maximum(s)));
                }
                assertEquals(s, stringsList.size(), stringsLength.intValueExact());
                assertTrue(s, all(ys -> isSubsetOf(ys, s), stringsList));
                assertTrue(s, all(t -> weaklyIncreasing(toList(t)), stringsList));
            }
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            BigInteger stringsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsLex(s));
                assertTrue(s, unique(stringsList));
                assertTrue(s, increasing(stringsList));
            }
        }
    }

    private void propertiesSubsetsLexAtLeast() {
        initialize("subsetsLexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.subsetsLexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), toList(take(p.b, sort(p.a))));
                    assertEquals(p, last(listsList), reverse(take(max(p.b, 1), reverse(sort(p.a)))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsLexAtLeast(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, increasing(new LexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.subsetsLexAtLeast(i, Collections.<Integer>emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsLexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairsLogarithmicOrder(
                P.withScale(4).listsWithElement(null, P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsLexAtLeast(p.b, p.a);
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsetsLexAtLeast() {
        initialize("stringSubsetsLexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringSubsetsLexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), take(p.b, sort(p.a)));
                    assertEquals(p, last(stringsList), reverse(take(max(p.b, 1), reverse(sort(p.a)))));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(toList(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsLexAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, increasing(stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringSubsetsLexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringSubsetsLexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesSubsetsShortlex() {
        initialize("subsetsShortlex(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> lists = EP.subsetsShortlex(xs);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!xs.isEmpty()) {
                    assertEquals(xs, head(listsList), Collections.emptyList());
                    assertEquals(xs, last(listsList), sort(xs));
                }
                assertEquals(xs, listsList.size(), listsLength.intValueExact());
                assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, listsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsShortlex(xs));
                assertTrue(xs, unique(listsList));
                assertTrue(xs, increasing(new ShortlexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.subsetsShortlex(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsetsShortlex() {
        initialize("stringSubsetsShortlex(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringSubsetsShortlex(s);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!s.isEmpty()) {
                    assertEquals(s, head(stringsList), "");
                    assertEquals(s, last(stringsList), sort(s));
                }
                assertEquals(s, stringsList.size(), stringsLength.intValueExact());
                assertTrue(s, all(ys -> isSubsetOf(ys, s), stringsList));
                assertTrue(s, all(t -> weaklyIncreasing(toList(t)), stringsList));
            }
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            BigInteger stringsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsShortlex(s));
                assertTrue(s, unique(stringsList));
                assertTrue(s, increasing(new StringShortlexComparator(), stringsList));
            }
        }
    }

    private void propertiesSubsetsShortlexAtLeast() {
        initialize("subsetsShortlexAtLeast(int, List<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.subsetsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), toList(take(p.b, sort(p.a))));
                    assertEquals(p, last(listsList), sort(p.a));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsShortlexAtLeast(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, increasing(new ShortlexComparator<>(), map(ys -> ((Iterable<Integer>) ys), listsList)));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.subsetsShortlexAtLeast(i, Collections.<Integer>emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringSubsetsShortlexAtLeast() {
        initialize("stringSubsetsShortlexAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringSubsetsShortlexAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), take(p.b, sort(p.a)));
                    assertEquals(p, last(stringsList), sort(p.a));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(toList(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsShortlexAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, increasing(new StringShortlexComparator(), stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringSubsetsShortlexAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringSubsetsShortlexAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesSubsets_int_Iterable() {
        initialize("subsets(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.subsets(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!listsList.isEmpty()) {
                    assertEquals(p, head(listsList), sort(take(p.b, p.a)));
                    assertEquals(p, last(listsList), sort(take(p.b, reverse(p.a))));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
                assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.size()), p.b);
            if (lt(listsLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsets(p.b, p.a));
                assertTrue(p, all(IterableUtils::unique, listsList));
                assertTrue(p, unique(listsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.subsets(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), sort(take(p.b, p.a)));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(Ordering::weaklyIncreasing, listsList));
            assertTrue(p, all(xs -> xs.size() == p.b, listsList));
            assertTrue(p, unique(listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> xss = EP.subsets(0, xs);
            testHasNext(xss);
            assertEquals(xs, toList(xss), Collections.singletonList(Collections.emptyList()));
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsets(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<List<Integer>, Integer>> psFail2 = filterInfinite(
                p -> p.a.size() >= p.b,
                P.pairsLogarithmicOrder(
                        P.withScale(4).listsWithElement(null, P.integersGeometric()),
                        P.withScale(4).positiveIntegersGeometric()
                )
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail2)) {
            try {
                toList(EP.subsets(p.b, p.a));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetPairs() {
        initialize("subsetPairs(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.subsetPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            BigInteger pairsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(pairs);
                List<Pair<Integer, Integer>> pairsList = toList(pairs);
                if (!pairsList.isEmpty()) {
                    List<Integer> start = sort(Arrays.asList(xs.get(0), xs.get(1)));
                    assertEquals(xs, head(pairsList), new Pair<>(start.get(0), start.get(1)));
                    List<Integer> end = sort(Arrays.asList(xs.get(xs.size() - 2), xs.get(xs.size() - 1)));
                    assertEquals(xs, last(pairsList), new Pair<>(end.get(0), end.get(1)));
                }
                assertEquals(xs, pairsList.size(), pairsLength.intValueExact());
                assertTrue(xs, all(p -> elem(p.a, xs), pairsList));
                assertTrue(xs, all(p -> elem(p.b, xs), pairsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger pairsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 2);
            if (lt(pairsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Pair<Integer, Integer>> pairsList = toList(EP.subsetPairs(xs));
                assertTrue(xs, unique(pairsList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Pair<Integer, Integer>> pairs = EP.subsetPairs(xs);
            testNoRemove(TINY_LIMIT, pairs);
            assertTrue(xs, unique(take(TINY_LIMIT, pairs)));
            List<Pair<Integer, Integer>> pairsList = toList(take(TINY_LIMIT, pairs));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Pair::toList, pairsList)));
            assertTrue(xs, all(IterableUtils::unique, map(Pair::toList, pairsList)));
            for (Pair<Integer, Integer> p : pairsList) {
                assertTrue(xs, elem(p.a, xs));
                assertTrue(xs, elem(p.b, xs));
            }
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 2,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetPairs(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetTriples() {
        initialize("subsetTriples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.subsetTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            BigInteger triplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(triples);
                List<Triple<Integer, Integer, Integer>> triplesList = toList(triples);
                if (!triplesList.isEmpty()) {
                    List<Integer> start = sort(Arrays.asList(xs.get(0), xs.get(1), xs.get(2)));
                    assertEquals(xs, head(triplesList), new Triple<>(start.get(0), start.get(1), start.get(2)));
                    List<Integer> end = sort(
                            Arrays.asList(xs.get(xs.size() - 3), xs.get(xs.size() - 2), xs.get(xs.size() - 1))
                    );
                    assertEquals(xs, last(triplesList), new Triple<>(end.get(0), end.get(1), end.get(2)));
                }
                assertEquals(xs, triplesList.size(), triplesLength.intValueExact());
                assertTrue(xs, all(t -> elem(t.a, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.b, xs), triplesList));
                assertTrue(xs, all(t -> elem(t.c, xs), triplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger triplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 3);
            if (lt(triplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Triple<Integer, Integer, Integer>> triplesList = toList(EP.subsetTriples(xs));
                assertTrue(xs, unique(triplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Triple<Integer, Integer, Integer>> triples = EP.subsetTriples(xs);
            testNoRemove(TINY_LIMIT, triples);
            assertTrue(xs, unique(take(TINY_LIMIT, triples)));
            List<Triple<Integer, Integer, Integer>> triplesList = toList(take(TINY_LIMIT, triples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Triple::toList, triplesList)));
            assertTrue(xs, all(IterableUtils::unique, map(Triple::toList, triplesList)));
            for (Triple<Integer, Integer, Integer> t : triplesList) {
                assertTrue(xs, elem(t.a, xs));
                assertTrue(xs, elem(t.b, xs));
                assertTrue(xs, elem(t.c, xs));
            }
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 3,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetTriples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetQuadruples() {
        initialize("subsetQuadruples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.subsetQuadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            BigInteger quadruplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(quadruples);
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(quadruples);
                if (!quadruplesList.isEmpty()) {
                    List<Integer> start = sort(Arrays.asList(xs.get(0), xs.get(1), xs.get(2), xs.get(3)));
                    assertEquals(
                            xs,
                            head(quadruplesList),
                            new Quadruple<>(start.get(0), start.get(1), start.get(2), start.get(3))
                    );
                    List<Integer> end = sort(
                            Arrays.asList(
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 1)
                            )
                    );
                    assertEquals(
                            xs,
                            last(quadruplesList),
                            new Quadruple<>(end.get(0), end.get(1), end.get(2), end.get(3))
                    );
                }
                assertEquals(xs, quadruplesList.size(), quadruplesLength.intValueExact());
                assertTrue(xs, all(q -> elem(q.a, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.b, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.c, xs), quadruplesList));
                assertTrue(xs, all(q -> elem(q.d, xs), quadruplesList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quadruplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 4);
            if (lt(quadruplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(EP.subsetQuadruples(xs));
                assertTrue(xs, unique(quadruplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Quadruple<Integer, Integer, Integer, Integer>> quadruples = EP.subsetQuadruples(xs);
            testNoRemove(TINY_LIMIT, quadruples);
            assertTrue(xs, unique(take(TINY_LIMIT, quadruples)));
            List<Quadruple<Integer, Integer, Integer, Integer>> quadruplesList = toList(take(TINY_LIMIT, quadruples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quadruple::toList, quadruplesList)));
            assertTrue(xs, all(IterableUtils::unique, map(Quadruple::toList, quadruplesList)));
            for (Quadruple<Integer, Integer, Integer, Integer> q : quadruplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
            }
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 4,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetQuadruples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetQuintuples() {
        initialize("subsetQuintuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.subsetQuintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            BigInteger quintuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(quintuples);
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList = toList(quintuples);
                if (!quintuplesList.isEmpty()) {
                    List<Integer> start = sort(Arrays.asList(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4)));
                    assertEquals(
                            xs,
                            head(quintuplesList),
                            new Quintuple<>(start.get(0), start.get(1), start.get(2), start.get(3), start.get(4))
                    );
                    List<Integer> end = sort(
                            Arrays.asList(
                                    xs.get(xs.size() - 5),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 1)
                            )
                    );
                    assertEquals(
                            xs,
                            last(quintuplesList),
                            new Quintuple<>(end.get(0), end.get(1), end.get(2), end.get(3), end.get(4))
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger quintuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 5);
            if (lt(quintuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                        toList(EP.subsetQuintuples(xs));
                assertTrue(xs, unique(quintuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuples = EP.subsetQuintuples(xs);
            testNoRemove(TINY_LIMIT, quintuples);
            assertTrue(xs, unique(take(TINY_LIMIT, quintuples)));
            List<Quintuple<Integer, Integer, Integer, Integer, Integer>> quintuplesList =
                    toList(take(TINY_LIMIT, quintuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Quintuple::toList, quintuplesList)));
            assertTrue(xs, all(IterableUtils::unique, map(Quintuple::toList, quintuplesList)));
            for (Quintuple<Integer, Integer, Integer, Integer, Integer> q : quintuplesList) {
                assertTrue(xs, elem(q.a, xs));
                assertTrue(xs, elem(q.b, xs));
                assertTrue(xs, elem(q.c, xs));
                assertTrue(xs, elem(q.d, xs));
                assertTrue(xs, elem(q.e, xs));
            }
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 5,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetQuintuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetSextuples() {
        initialize("subsetSextuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.subsetSextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            BigInteger sextuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(sextuples);
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList = toList(sextuples);
                if (!sextuplesList.isEmpty()) {
                    List<Integer> start = sort(
                            Arrays.asList(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5))
                    );
                    assertEquals(
                            xs,
                            head(sextuplesList),
                            new Sextuple<>(
                                    start.get(0),
                                    start.get(1),
                                    start.get(2),
                                    start.get(3),
                                    start.get(4),
                                    start.get(5)
                            )
                    );
                    List<Integer> end = sort(
                            Arrays.asList(
                                    xs.get(xs.size() - 6),
                                    xs.get(xs.size() - 5),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 1)
                            )
                    );
                    assertEquals(
                            xs,
                            last(sextuplesList),
                            new Sextuple<>(end.get(0), end.get(1), end.get(2), end.get(3), end.get(4), end.get(5))
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger sextuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 6);
            if (lt(sextuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                        toList(EP.subsetSextuples(xs));
                assertTrue(xs, unique(sextuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuples =
                    EP.subsetSextuples(xs);
            testNoRemove(TINY_LIMIT, sextuples);
            assertTrue(xs, unique(take(TINY_LIMIT, sextuples)));
            List<Sextuple<Integer, Integer, Integer, Integer, Integer, Integer>> sextuplesList =
                    toList(take(TINY_LIMIT, sextuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Sextuple::toList, sextuplesList)));
            assertTrue(xs, all(IterableUtils::unique, map(Sextuple::toList, sextuplesList)));
            for (Sextuple<Integer, Integer, Integer, Integer, Integer, Integer> s : sextuplesList) {
                assertTrue(xs, elem(s.a, xs));
                assertTrue(xs, elem(s.b, xs));
                assertTrue(xs, elem(s.c, xs));
                assertTrue(xs, elem(s.d, xs));
                assertTrue(xs, elem(s.e, xs));
                assertTrue(xs, elem(s.f, xs));
            }
        }

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 6,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetSextuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesSubsetSeptuples() {
        initialize("subsetSeptuples(Iterable<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.subsetSeptuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            BigInteger septuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(septuples);
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(septuples);
                if (!septuplesList.isEmpty()) {
                    List<Integer> start = sort(
                            Arrays.asList(xs.get(0), xs.get(1), xs.get(2), xs.get(3), xs.get(4), xs.get(5), xs.get(6))
                    );
                    assertEquals(
                            xs,
                            head(septuplesList),
                            new Septuple<>(
                                    start.get(0),
                                    start.get(1),
                                    start.get(2),
                                    start.get(3),
                                    start.get(4),
                                    start.get(5),
                                    start.get(6)
                            )
                    );
                    List<Integer> end = sort(
                            Arrays.asList(
                                    xs.get(xs.size() - 7),
                                    xs.get(xs.size() - 6),
                                    xs.get(xs.size() - 5),
                                    xs.get(xs.size() - 4),
                                    xs.get(xs.size() - 3),
                                    xs.get(xs.size() - 2),
                                    xs.get(xs.size() - 1)
                            )
                    );
                    assertEquals(
                            xs,
                            last(septuplesList),
                            new Septuple<>(
                                    end.get(0),
                                    end.get(1),
                                    end.get(2),
                                    end.get(3),
                                    end.get(4),
                                    end.get(5),
                                    end.get(6)
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

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger septuplesLength = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size()), 7);
            if (lt(septuplesLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                        toList(EP.subsetSeptuples(xs));
                assertTrue(xs, unique(septuplesList));
                assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
            }
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuples =
                    EP.subsetSeptuples(xs);
            testNoRemove(TINY_LIMIT, septuples);
            assertTrue(xs, unique(take(TINY_LIMIT, septuples)));
            List<Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> septuplesList =
                    toList(take(TINY_LIMIT, septuples));
            assertTrue(xs, all(Ordering::weaklyIncreasing, map(Septuple::toList, septuplesList)));
            assertTrue(xs, all(IterableUtils::unique, map(Septuple::toList, septuplesList)));
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

        Iterable<List<Integer>> xsFail = filterInfinite(
                ys -> ys.size() >= 7,
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (List<Integer> xs : take(LIMIT, xsFail)) {
            try {
                toList(EP.subsetSeptuples(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsets_int_String() {
        initialize("stringSubsets(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringSubsets(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(TINY_LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!stringsList.isEmpty()) {
                    assertEquals(p, head(stringsList), sort(take(p.b, p.a)));
                    assertEquals(p, last(stringsList), sort(take(p.b, reverse(p.a))));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), stringsList));
                assertTrue(p, all(s -> weaklyIncreasing(toList(s)), stringsList));
                assertTrue(p, all(xs -> xs.length() == p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.binomialCoefficient(BigInteger.valueOf(p.a.length()), p.b);
            if (lt(stringsLength, BigInteger.valueOf(TINY_LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsets(p.b, p.a));
                assertTrue(p, all(IterableUtils::unique, stringsList));
                assertTrue(p, unique(stringsList));
            }
        }

        ps = filter(
                p -> p.a.length() < p.b,
                P.pairsLogarithmicOrder(P.withScale(4).strings(), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> ss = EP.stringSubsets(p.b, p.a);
            testHasNext(ss);
            assertEquals(p, toList(ss), Collections.emptyList());
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> ss = EP.stringSubsets(0, s);
            testHasNext(ss);
            assertEquals(s, toList(ss), Collections.singletonList(""));
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringSubsets(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringSubsets_int() {
        initialize("stringSubsets(int)");
        for (int i : take(MEDIUM_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            Iterable<String> strings = EP.stringSubsets(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), sort(charsToString(take(i, EP.characters()))));
            assertTrue(i, all(s -> s.length() == i, stringsList));
            assertTrue(i, all(s -> weaklyIncreasing(toList(s)), stringsList));
            assertTrue(i, all(IterableUtils::unique, stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(MEDIUM_LIMIT, P.rangeUp((1 << 16) + 1))) {
            assertEquals(i, toList(EP.stringSubsets(i)), Collections.emptyList());
        }

        for (int i : take(LIMIT, P.withScale(4).negativeIntegersGeometric())) {
            try {
                EP.stringSubsets(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesSubsets_Iterable() {
        initialize("subsets(Iterable<T>)");

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            Iterable<List<Integer>> lists = EP.subsets(xs);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(lists);
                List<List<Integer>> listsList = toList(lists);
                if (!listsList.isEmpty()) {
                    assertEquals(xs, head(listsList), Collections.emptyList());
                }
                assertEquals(xs, listsList.size(), listsLength.intValueExact());
                assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            }
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.integersGeometric()))) {
            BigInteger listsLength = BigInteger.ONE.shiftLeft(xs.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsets(xs));
                assertTrue(xs, unique(listsList));
                assertTrue(xs, all(Ordering::increasing, listsList));
            }
        }

        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            Iterable<List<Integer>> lists = EP.subsets(xs);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(xs, head(listsList), Collections.emptyList());
            assertTrue(xs, all(ys -> isSubsetOf(ys, xs), listsList));
            assertTrue(xs, unique(listsList));
            assertTrue(xs, all(Ordering::increasing, listsList));
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.subsets(xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsets_String() {
        initialize("stringSubsets(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            Iterable<String> strings = EP.stringSubsets(s);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                testHasNext(strings);
                List<String> stringsList = toList(strings);
                if (!stringsList.isEmpty()) {
                    assertEquals(s, head(stringsList), "");
                }
                assertEquals(s, stringsList.size(), stringsLength.intValueExact());
                assertTrue(s, all(t -> isSubsetOf(t, s), stringsList));
            }
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            BigInteger listsLength = BigInteger.ONE.shiftLeft(s.length());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsets(s));
                assertTrue(s, unique(stringsList));
                assertTrue(s, all(t -> increasing(toList(t)), stringsList));
            }
        }
    }

    private void propertiesStringSubsets() {
        initializeConstant("stringSubsets()");
        biggerTest(EP, EP.stringSubsets(), s -> increasing(toList(s)));
    }

    private void propertiesSubsetsAtLeast() {
        initialize("subsetsAtLeast(int, Iterable<T>)");
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            Iterable<List<Integer>> lists = EP.subsetsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                if (!listsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(listsList), sort(take(p.b, p.a)));
                }
                assertEquals(p, listsList.size(), listsLength.intValueExact());
                assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
                assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            }
        }

        ps = P.pairsLogarithmicOrder(
                P.withScale(4).distinctLists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            BigInteger listsLength = MathUtils.subsetCount(p.b, p.a.size());
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(EP.subsetsAtLeast(p.b, p.a));
                assertTrue(p, unique(listsList));
                assertTrue(p, all(IterableUtils::unique, listsList));
            }
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps2)) {
            Iterable<List<Integer>> lists = EP.subsetsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, lists);
            List<List<Integer>> listsList = toList(take(TINY_LIMIT, lists));
            assertEquals(p, head(listsList), sort(take(p.b, p.a)));
            assertTrue(p, all(xs -> isSubsetOf(xs, p.a), listsList));
            assertTrue(p, all(xs -> xs.size() >= p.b, listsList));
            assertTrue(p, all(Ordering::increasing, listsList));
            assertTrue(p, unique(listsList));
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<List<Integer>> xss = EP.subsetsAtLeast(i, Collections.<Integer>emptyList());
            testHasNext(xss);
            assertEquals(i, toList(xss), Collections.emptyList());
        }

        Iterable<Pair<List<Integer>, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.subsetsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringSubsetsAtLeast_int_String() {
        initialize("stringSubsetsAtLeast(int, String)");
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            Iterable<String> strings = EP.stringSubsetsAtLeast(p.b, p.a);
            testNoRemove(TINY_LIMIT, strings);
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(strings);
                if (!stringsLength.equals(BigInteger.ZERO)) {
                    assertEquals(p, head(stringsList), sort(take(p.b, p.a)));
                }
                assertEquals(p, stringsList.size(), stringsLength.intValueExact());
                assertTrue(p, all(s -> isSubsetOf(s, p.a), stringsList));
                assertTrue(p, all(s -> s.length() >= p.b, stringsList));
            }
        }

        ps = P.pairsLogarithmicOrder(P.withScale(4).distinctStrings(), P.withScale(4).naturalIntegersGeometric());
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            BigInteger stringsLength = MathUtils.subsetCount(p.b, p.a.length());
            if (lt(stringsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<String> stringsList = toList(EP.stringSubsetsAtLeast(p.b, p.a));
                assertTrue(p, unique(stringsList));
                assertTrue(p, all(s -> increasing(toList(s)), stringsList));
            }
        }

        for (int i : take(LIMIT, P.positiveIntegersGeometric())) {
            Iterable<String> ss = EP.stringSubsetsAtLeast(i, "");
            testHasNext(ss);
            assertEquals(i, toList(ss), Collections.emptyList());
        }

        Iterable<Pair<String, Integer>> psFail = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).negativeIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, psFail)) {
            try {
                EP.stringSubsetsAtLeast(p.b, p.a);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesStringSubsetsAtLeast_int() {
        initialize("stringSubsetsAtLeast(int)");
        Iterable<Integer> is = filterInfinite(j -> j < (1 << 16) + 1, P.withScale(4).naturalIntegersGeometric());
        for (int i : take(TINY_LIMIT, is)) {
            Iterable<String> strings = EP.stringSubsetsAtLeast(i);
            testNoRemove(TINY_LIMIT, strings);
            List<String> stringsList = toList(take(TINY_LIMIT, strings));
            assertEquals(i, head(stringsList), charsToString(take(i, EP.characters())));
            assertTrue(i, all(s -> s.length() >= i, stringsList));
            assertTrue(i, all(s -> increasing(toList(s)), stringsList));
            assertTrue(i, unique(stringsList));
        }

        for (int i : take(MEDIUM_LIMIT, P.rangeUp((1 << 16) + 1))) {
            assertEquals(i, toList(EP.stringSubsetsAtLeast(i)), Collections.emptyList());
        }

        for (int i : take(LIMIT, P.negativeIntegers())) {
            try {
                EP.stringSubsetsAtLeast(i);
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesEithersSuccessive() {
        initialize("eithersSuccessive(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersSuccessive(p.a, p.b);
            testNoRemove(eithers);
            testHasNext(eithers);
            List<Either<Integer, Integer>> eithersList = toList(eithers);
            if (!p.a.isEmpty()) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertEquals(p, eithersList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.eithersSuccessive(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersSuccessive(p.a, p.b);
            testNoRemove(TINY_LIMIT, eithers);
            List<Either<Integer, Integer>> eithersList = toList(take(TINY_LIMIT, eithers));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
            assertTrue(p, unique(eithersList));
        }
    }

    private void propertiesEithersSquareRootOrder() {
        initialize("eithersSquareRootOrder(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersSquareRootOrder(p.a, p.b);
            testNoRemove(eithers);
            testHasNext(eithers);
            List<Either<Integer, Integer>> eithersList = toList(eithers);
            if (!p.b.isEmpty()) {
                assertEquals(p, head(eithersList), Either.ofB(head(p.b)));
            }
            assertEquals(p, eithersList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.eithersSquareRootOrder(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersSquareRootOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, eithers);
            List<Either<Integer, Integer>> eithersList = toList(take(TINY_LIMIT, eithers));
            if (!isEmpty(p.b)) {
                assertEquals(p, head(eithersList), Either.ofB(head(p.b)));
            }
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
            assertTrue(p, unique(eithersList));
        }
    }

    private void propertiesEithersLogarithmicOrder() {
        initialize("eithersLogarithmicOrder(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersLogarithmicOrder(p.a, p.b);
            testNoRemove(eithers);
            testHasNext(eithers);
            List<Either<Integer, Integer>> eithersList = toList(eithers);
            if (!p.a.isEmpty()) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertEquals(p, eithersList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.eithersLogarithmicOrder(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithersLogarithmicOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, eithers);
            List<Either<Integer, Integer>> eithersList = toList(take(TINY_LIMIT, eithers));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
            assertTrue(p, unique(eithersList));
        }
    }

    private void propertiesEithers() {
        initialize("eithers(Iterable<A>, Iterable<B>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithers(p.a, p.b);
            testNoRemove(eithers);
            testHasNext(eithers);
            List<Either<Integer, Integer>> eithersList = toList(eithers);
            if (!p.a.isEmpty()) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertEquals(p, eithersList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
        }

        ps = P.pairs(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(EP.eithers(p.a, p.b)));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Either<Integer, Integer>> eithers = EP.eithers(p.a, p.b);
            testNoRemove(TINY_LIMIT, eithers);
            List<Either<Integer, Integer>> eithersList = toList(take(TINY_LIMIT, eithers));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(eithersList), Either.ofA(head(p.a)));
            }
            assertTrue(p, all(e -> e.whichSlot() == Either.Slot.A ? elem(e.a(), p.a) : elem(e.b(), p.b), eithersList));
            assertTrue(p, unique(eithersList));
        }
    }

    private void propertiesChooseSquareRootOrder() {
        initialize("chooseSquareRootOrder(Iterable<T>, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Integer> chosen = EP.chooseSquareRootOrder(p.a, p.b);
            testNoRemove(chosen);
            testHasNext(chosen);
            List<Integer> chosenList = toList(chosen);
            if (!p.b.isEmpty()) {
                assertEquals(p, head(chosenList), head(p.b));
            }
            assertEquals(p, chosenList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Integer> chosen = EP.chooseSquareRootOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, chosen);
            List<Integer> chosenList = toList(take(TINY_LIMIT, chosen));
            if (!isEmpty(p.b)) {
                assertEquals(p, head(chosenList), head(p.b));
            }
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }
    }

    private void propertiesChooseLogarithmicOrder() {
        initialize("chooseLogarithmicOrder(Iterable<T>, Iterable<T>)");
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Integer> chosen = EP.chooseLogarithmicOrder(p.a, p.b);
            testNoRemove(chosen);
            testHasNext(chosen);
            List<Integer> chosenList = toList(chosen);
            if (!p.a.isEmpty()) {
                assertEquals(p, head(chosenList), head(p.a));
            }
            assertEquals(p, chosenList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Integer> chosen = EP.chooseLogarithmicOrder(p.a, p.b);
            testNoRemove(TINY_LIMIT, chosen);
            List<Integer> chosenList = toList(take(TINY_LIMIT, chosen));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(chosenList), head(p.a));
            }
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }
    }

    private void propertiesChoose_Iterable_Iterable() {
        initialize("choose(Iterable<T>, Iterable<T>)");
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(P.withNull(P.integersGeometric()))))) {
            Iterable<Integer> chosen = EP.choose(p.a, p.b);
            testNoRemove(chosen);
            testHasNext(chosen);
            List<Integer> chosenList = toList(chosen);
            if (!p.a.isEmpty()) {
                assertEquals(p, head(chosenList), head(p.a));
            }
            assertEquals(p, chosenList.size(), p.a.size() + p.b.size());
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }

        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Integer> chosen = EP.choose(p.a, p.b);
            testNoRemove(TINY_LIMIT, chosen);
            List<Integer> chosenList = toList(take(TINY_LIMIT, chosen));
            if (!isEmpty(p.a)) {
                assertEquals(p, head(chosenList), head(p.a));
            }
            assertTrue(p, all(i -> elem(i, p.a) || elem(i, p.b), chosenList));
        }
    }

    private void propertiesChoose_Iterable() {
        initialize("choose(<List<Iterable<T>>)");
        for (List<List<Integer>> xss : take(LIMIT, P.lists(P.lists(P.withNull(P.integersGeometric()))))) {
            Iterable<Integer> chosen = EP.choose(toList(map(xs -> xs, xss)));
            testNoRemove(chosen);
            testHasNext(chosen);
            List<Integer> chosenList = toList(chosen);
            if (!xss.isEmpty() && !head(xss).isEmpty()) {
                assertEquals(xss, head(chosenList), head(head(xss)));
            }
            assertEquals(xss, chosenList.size(), sumInteger(toList(map(List::size, xss))));
            assertTrue(xss, all(i -> any(xs -> elem(i, xs), xss), chosenList));
        }

        Iterable<List<Iterable<Integer>>> xsss = P.withScale(4).listsAtLeast(
                1,
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (List<Iterable<Integer>> xss : take(LIMIT, xsss)) {
            Iterable<Integer> chosen = EP.choose(xss);
            testNoRemove(TINY_LIMIT, chosen);
            List<Integer> chosenList = toList(take(TINY_LIMIT, chosen));
            if (!isEmpty(head(xsss))) {
                assertEquals(xss, head(chosenList), head(head(xss)));
            }
            assertTrue(xss, all(i -> any(xs -> elem(i, xs), xss), chosenList));
        }
    }

    private void propertiesCartesianProduct() {
        initialize("cartesianProduct(List<List<T>>)");
        Iterable<List<List<Integer>>> xsss = P.withScale(4).lists(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<List<Integer>> xss : take(LIMIT, xsss)) {
            Iterable<List<Integer>> lists = EP.cartesianProduct(xss);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = productBigInteger(toList(map(xs -> BigInteger.valueOf(xs.size()), xss)));
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                List<List<Integer>> listsList = toList(lists);
                assertEquals(xss, listsList.size(), listsLength.intValueExact());
                assertTrue(xss, all(xs -> xs.size() == xss.size(), listsList));
                assertTrue(xss, all(xs -> and(zipWith(List::contains, xss, xs)), listsList));
                if (!listsList.isEmpty()) {
                    assertEquals(xss, head(listsList), toList(map(IterableUtils::head, xss)));
                    assertEquals(xss, last(listsList), toList(map(IterableUtils::last, xss)));
                }
            }
        }

        xsss = P.withScale(4).lists(P.withScale(4).distinctLists(P.withNull(P.integersGeometric())));
        for (List<List<Integer>> xss : take(LIMIT, xsss)) {
            Iterable<List<Integer>> lists = EP.cartesianProduct(xss);
            testNoRemove(TINY_LIMIT, lists);
            BigInteger listsLength = productBigInteger(toList(map(xs -> BigInteger.valueOf(xs.size()), xss)));
            if (lt(listsLength, BigInteger.valueOf(SMALL_LIMIT))) {
                assertTrue(xss, unique(lists));
            }
        }

        Iterable<List<List<Integer>>> xsssFail = P.withScale(4).listsWithElement(
                null,
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<List<Integer>> xss : take(LIMIT, xsssFail)) {
            try {
                EP.cartesianProduct(xss);
                fail(xss);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesRepeatingIterables() {
        initialize("repeatingIterables(Iterable<T>)");
        Iterable<List<Integer>> xss = filterInfinite(
                ys -> length(nub(ys)) > 1,
                P.withScale(4).listsAtLeast(2, P.withNull(P.integersGeometric()))
        );
        for (List<Integer> xs : take(TINY_LIMIT, xss)) {
            simpleTest(
                    xs,
                    EP.repeatingIterables(xs),
                    ys -> {
                        List<Integer> tys = toList(take(TINY_LIMIT, ys));
                        return tys.size() == TINY_LIMIT && all(xs::contains, tys);
                    }
            );
        }

        for (Iterable<Integer> xs : take(TINY_LIMIT, P.prefixPermutations(P.withNull(EP.naturalIntegers())))) {
            simpleTest(
                    xs,
                    EP.repeatingIterables(xs),
                    ys -> lengthAtLeast(TINY_LIMIT, ys)
            );
        }

        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            try {
                EP.repeatingIterables(Collections.singletonList(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesRepeatingIterablesDistinctAtLeast() {
        initialize("repeatingIterablesDistinctAtLeast(int, Iterable<T>)");
        Iterable<Pair<Integer, List<Integer>>> ps = P.dependentPairsInfiniteLogarithmicOrder(
                P.withScale(3).rangeUpGeometric(2),
                i -> P.withScale(i + 1).distinctListsAtLeast(i, P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(TINY_LIMIT, ps)) {
            simpleTest(
                    p,
                    EP.repeatingIterablesDistinctAtLeast(p.a, p.b),
                    ys -> {
                        List<Integer> tys = toList(take(TINY_LIMIT, ys));
                        Set<Integer> distinctElements = new HashSet<>();
                        Iterator<Integer> ysi = ys.iterator();
                        while (distinctElements.size() < p.a) {
                            distinctElements.add(ysi.next());
                        }
                        return tys.size() == TINY_LIMIT && all(p.b::contains, tys);
                    }
            );
        }

        Iterable<Pair<Iterable<Integer>, Integer>> ps2 = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                filterInfinite(i -> i < 10, P.withScale(3).rangeUpGeometric(2))
        );
        for (Pair<Iterable<Integer>, Integer> p : take(TINY_LIMIT, ps2)) {
            simpleTest(
                    p,
                    EP.repeatingIterablesDistinctAtLeast(p.b, p.a),
                    ys -> {
                        Set<Integer> distinctElements = new HashSet<>();
                        Iterator<Integer> ysi = ys.iterator();
                        while (distinctElements.size() < p.b) {
                            distinctElements.add(ysi.next());
                        }
                        return lengthAtLeast(TINY_LIMIT, ys);
                    }
            );
        }

        Iterable<Pair<Integer, List<Integer>>> psFail = P.dependentPairsInfinite(
                P.negativeIntegers(),
                i -> P.distinctListsAtLeast(2, P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(TINY_LIMIT, psFail)) {
            try {
                EP.repeatingIterablesDistinctAtLeast(p.a, p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            try {
                EP.repeatingIterablesDistinctAtLeast(0, Collections.singletonList(i));
                fail(i);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesSublists() {
        initialize("sublists(List<T>)");
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            List<List<Integer>> sublists = toList(EP.sublists(xs));
            simpleTest(xs, sublists, ys -> isInfixOf(ys, xs));
            assertTrue(xs, unique(sublists));
            int sublistSize = sublists.size();
            int minSize = xs.size() + 1;
            int maxSize = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size() + 1), 2).intValueExact() + 1;
            assertTrue(xs, sublistSize >= minSize && sublistSize <= maxSize);
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).distinctLists(P.withNull(P.integersGeometric())))) {
            int sublistSize = length(EP.sublists(xs));
            int maxSize = MathUtils.binomialCoefficient(BigInteger.valueOf(xs.size() + 1), 2).intValueExact() + 1;
            assertTrue(xs, sublistSize == maxSize);
        }
    }

    private void propertiesSubstrings() {
        initialize("substrings(String)");
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            List<String> substrings = toList(EP.substrings(s));
            simpleTest(s, substrings, s::contains);
            assertTrue(s, unique(substrings));
            int sublistSize = substrings.size();
            int minSize = s.length() + 1;
            int maxSize = MathUtils.binomialCoefficient(BigInteger.valueOf(s.length() + 1), 2).intValueExact() + 1;
            assertTrue(s, sublistSize >= minSize && sublistSize <= maxSize);
        }

        for (String s : take(LIMIT, P.withScale(4).distinctStrings())) {
            int sublistSize = length(EP.substrings(s));
            int maxSize = MathUtils.binomialCoefficient(BigInteger.valueOf(s.length() + 1), 2).intValueExact() + 1;
            assertTrue(s, sublistSize == maxSize);
        }
    }

    private void propertiesListsWithElement() {
        initialize("listsWithElement(T, Iterable<T>)");
        Iterable<Pair<Integer, List<Integer>>> ps = P.pairs(
                P.withNull(P.integersGeometric()),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            List<Integer> combined = toList(cons(p.a, p.b));
            assertTrue(
                    p,
                    all(
                            xs -> xs.contains(p.a) && isSubsetOf(xs, combined),
                            take(TINY_LIMIT, EP.listsWithElement(p.a, p.b))
                    )
            );
        }

        ps = P.pairs(
                P.withNull(P.integersGeometric()),
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(take(TINY_LIMIT, EP.listsWithElement(p.a, p.b))));
        }

        for (Integer i : take(LIMIT, P.withNull(P.integersGeometric()))) {
            assertEquals(
                    i,
                    toList(EP.listsWithElement(i, Collections.emptyList())),
                    Collections.singletonList(Collections.singletonList(i))
            );
        }

        Iterable<Pair<Integer, Iterable<Integer>>> ps2 = P.pairs(
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Integer, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps2)) {
            simpleTest(p, EP.listsWithElement(p.a, p.b), xs -> xs.contains(p.a));
        }
    }

    private void propertiesStringsWithChar_char_String() {
        initialize("stringsWithChar(char, String)");
        for (Pair<Character, String> p : take(LIMIT, P.pairs(P.characters(), P.withScale(4).strings()))) {
            String combined = cons(p.a, p.b);
            assertTrue(
                    p,
                    all(s -> elem(p.a, s) && isSubsetOf(s, combined), take(TINY_LIMIT, EP.stringsWithChar(p.a, p.b)))
            );
        }

        for (Pair<Character, String> p : take(LIMIT, P.pairs(P.characters(), P.withScale(4).distinctStrings()))) {
            assertTrue(p, unique(take(TINY_LIMIT, EP.stringsWithChar(p.a, p.b))));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertEquals(c, toList(EP.stringsWithChar(c, "")), Collections.singletonList(Character.toString(c)));
        }
    }

    private void propertiesStringsWithChar_char() {
        initialize("stringsWithChar(char, String)");
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            simpleTest(c, EP.stringsWithChar(c), s -> elem(c, s));
        }
    }

    private void propertiesSubsetsWithElement() {
        initialize("subsetsWithElement(T, Iterable<T>)");
        Iterable<Pair<Integer, List<Integer>>> ps = P.pairs(
                P.integersGeometric(),
                P.withScale(4).lists(P.integersGeometric())
        );
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            List<Integer> combined = toList(cons(p.a, p.b));
            assertTrue(
                    p,
                    all(
                            xs -> xs.contains(p.a) && isSubsetOf(xs, combined) && weaklyIncreasing(xs),
                            take(TINY_LIMIT, EP.subsetsWithElement(p.a, p.b))
                    )
            );
        }

        ps = P.pairs(P.integersGeometric(), P.withScale(4).distinctLists(P.integersGeometric()));
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, unique(take(TINY_LIMIT, EP.subsetsWithElement(p.a, p.b))));
        }

        for (Integer i : take(LIMIT, P.integersGeometric())) {
            assertEquals(
                    i,
                    toList(EP.subsetsWithElement(i, Collections.emptyList())),
                    Collections.singletonList(Collections.singletonList(i))
            );
        }

        Iterable<Pair<Integer, Iterable<Integer>>> ps2 = P.pairs(
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<Integer, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps2)) {
            simpleTest(p, EP.subsetsWithElement(p.a, p.b), xs -> xs.contains(p.a));
        }

        Iterable<Pair<Integer, List<Integer>>> psFail = P.pairs(
                P.integersGeometric(),
                P.withScale(4).listsWithElement(null, P.integersGeometric())
        );
        for (Pair<Integer, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.subsetsWithElement(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).listsWithElement(null, P.integersGeometric()))) {
            try {
                toList(EP.subsetsWithElement(null, xs));
                fail(xs);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringSubsetsWithChar_char_String() {
        initialize("stringSubsetsWithChar(char, String)");
        for (Pair<Character, String> p : take(LIMIT, P.pairs(P.characters(), P.withScale(4).strings()))) {
            String combined = cons(p.a, p.b);
            assertTrue(
                    p,
                    all(
                            s -> elem(p.a, s) && isSubsetOf(s, combined) && weaklyIncreasing(toList(s)),
                            take(TINY_LIMIT, EP.stringSubsetsWithChar(p.a, p.b))
                    )
            );
        }

        for (Pair<Character, String> p : take(LIMIT, P.pairs(P.characters(), P.withScale(4).distinctStrings()))) {
            assertTrue(p, unique(take(TINY_LIMIT, EP.stringSubsetsWithChar(p.a, p.b))));
        }

        for (char c : take(LIMIT, P.characters())) {
            assertEquals(c, toList(EP.stringSubsetsWithChar(c, "")), Collections.singletonList(Character.toString(c)));
        }
    }

    private void propertiesStringSubsetsWithChar_char() {
        initialize("stringSubsetsWithChar(char)");
        for (char c : take(LIMIT, P.characters())) {
            simpleTest(c, EP.stringSubsetsWithChar(c), s -> elem(c, s) && weaklyIncreasing(toList(s)));
        }
    }

    private void propertiesListsWithSublists() {
        initialize("listsWithSublists(Iterable<List<T>>, Iterable<T>)");
        Iterable<List<Integer>> lists = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        for (Pair<List<List<Integer>>, List<Integer>> p : take(LIMIT, P.pairs(P.withScale(4).lists(lists), lists))) {
            List<Integer> combined = toList(nub(concat(map(xs -> (Iterable<Integer>) xs, cons(p.b, p.a)))));
            simpleTest(
                    p,
                    EP.listsWithSublists(p.a, p.b),
                    xs -> any(ys -> isInfixOf(ys, xs), p.a) && isSubsetOf(xs, combined)
            );
        }

        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            assertTrue(xs, isEmpty(EP.listsWithSublists(Collections.emptyList(), xs)));
        }

        Iterable<Pair<List<List<Integer>>, Iterable<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withScale(4).lists(P.withNull(P.integersGeometric()))),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<List<Integer>>, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p, EP.listsWithSublists(p.a, p.b), xs -> any(ys -> isInfixOf(ys, xs), p.a));
        }

        Iterable<Pair<List<List<Integer>>, List<Integer>>> psFail = P.pairs(
                P.withScale(4).listsWithElement(null, lists),
                lists
        );
        for (Pair<List<List<Integer>>, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(EP.listsWithSublists(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringsWithSubstrings_Iterable_String_String() {
        initialize("stringsWithSubstrings(Iterable<String>, String)");
        Iterable<String> strings = P.withScale(4).strings();
        for (Pair<List<String>, String> p : take(LIMIT, P.pairs(P.withScale(4).lists(strings), strings))) {
            String combined = concatStrings(cons(p.b, p.a));
            simpleTest(
                    p,
                    EP.stringsWithSubstrings(p.a, p.b),
                    s -> any(t -> isInfixOf(t, s), p.a) && isSubsetOf(s, combined)
            );
        }

        for (String s : take(LIMIT, P.withScale(4).strings())) {
            assertTrue(s, isEmpty(EP.stringsWithSubstrings(Collections.emptyList(), s)));
        }

        Iterable<Pair<List<String>, String>> psFail = P.pairs(P.withScale(4).listsWithElement(null, strings), strings);
        for (Pair<List<String>, String> p : take(LIMIT, psFail)) {
            try {
                toList(EP.stringsWithSubstrings(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private void propertiesStringsWithSubstrings_Iterable_String() {
        initialize("stringsWithSubstrings(Iterable<String>)");
        for (List<String> ss : take(LIMIT, P.withScale(4).lists(P.withScale(4).strings()))) {
            simpleTest(ss, EP.stringsWithSubstrings(ss), s -> any(t -> isInfixOf(t, s), ss));
        }
    }

    private void propertiesMaps() {
        initialize("maps(List<K>, Iterable<V>)");
        Comparator<Integer> withNullComparator = Comparator.nullsFirst(Comparator.naturalOrder());
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.distinctLists(P.withNull(P.integersGeometric())),
                P.lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Map<Integer, Integer>> maps = EP.maps(p.a, p.b);
            testNoRemove(TINY_LIMIT, maps);
            BigInteger mapsLength = BigInteger.valueOf(p.b.size()).pow(p.a.size());
            if (lt(mapsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(maps);
                List<Map<Integer, Integer>> mapsList = toList(maps);
                assertEquals(p, mapsList.size(), mapsLength.intValueExact());
                List<Integer> sortedKeys = sort(withNullComparator, p.a);
                assertTrue(p, all(m -> sort(withNullComparator, m.keySet()).equals(sortedKeys), mapsList));
                assertTrue(p, all(m -> isSubsetOf(m.values(), p.b), mapsList));
            }
        }

        ps = P.pairs(P.distinctLists(P.withNull(P.integersGeometric())));
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            BigInteger mapsLength = BigInteger.valueOf(p.b.size()).pow(p.a.size());
            if (lt(mapsLength, BigInteger.valueOf(LIMIT))) {
                List<Map<Integer, Integer>> mapsList = toList(EP.maps(p.a, p.b));
                assertTrue(p, unique(mapsList));
            }
        }

        Iterable<Pair<List<Integer>, Iterable<Integer>>> ps2 = P.pairs(
                P.distinctLists(P.withNull(P.integersGeometric())),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<Integer>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<Map<Integer, Integer>> maps = EP.maps(p.a, p.b);
            testNoRemove(TINY_LIMIT, maps);
            List<Map<Integer, Integer>> mapsList = toList(take(TINY_LIMIT, maps));
            List<Integer> sortedKeys = sort(withNullComparator, p.a);
            assertTrue(p, all(m -> sort(withNullComparator, m.keySet()).equals(sortedKeys), mapsList));
            assertTrue(p, all(m -> isSubsetOf(m.values(), p.b), mapsList));
        }

        for (List<Integer> xs : take(LIMIT, P.distinctLists(P.withNull(P.integersGeometric())))) {
            assertEquals(
                    xs,
                    toList(EP.maps(Collections.emptyList(), xs)),
                    Collections.singletonList(Collections.emptyMap())
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            assertEquals(
                    xs,
                    toList(EP.maps(Collections.emptyList(), xs)),
                    Collections.singletonList(Collections.emptyMap())
            );
        }

        for (List<Integer> xs : take(LIMIT, P.distinctListsAtLeast(1, P.withNull(P.integersGeometric())))) {
            assertTrue(xs, isEmpty(EP.maps(xs, Collections.emptyList())));
        }
    }

    private void propertiesIdentityMaps() {
        initialize("identityMaps(List<K>, Iterable<V>)");
        Iterable<Pair<List<IntNoHashCode>, List<Integer>>> ps = P.pairs(
                P.lists(P.withNull(map(IntNoHashCode::new, P.integersGeometric()))),
                P.lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<IntNoHashCode>, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<IdentityHashMap<IntNoHashCode, Integer>> maps = EP.identityMaps(p.a, p.b);
            testNoRemove(TINY_LIMIT, maps);
            BigInteger mapsLength = BigInteger.valueOf(p.b.size()).pow(p.a.size());
            if (lt(mapsLength, BigInteger.valueOf(LIMIT))) {
                testHasNext(maps);
                List<IdentityHashMap<IntNoHashCode, Integer>> mapsList = toList(maps);
                assertEquals(p, mapsList.size(), mapsLength.intValueExact());
                assertTrue(p, all(m -> isSubsetOf(m.values(), p.b), mapsList));
            }
        }

        ps = P.pairs(
                P.lists(map(IntNoHashCode::new, P.integersGeometric())),
                P.lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<IntNoHashCode>, List<Integer>> p : take(LIMIT, ps)) {
            BigInteger mapsLength = BigInteger.valueOf(p.b.size()).pow(p.a.size());
            if (lt(mapsLength, BigInteger.valueOf(LIMIT))) {
                toList(EP.identityMaps(p.a, p.b));
            }
        }

        Iterable<Pair<List<IntNoHashCode>, Iterable<Integer>>> ps2 = P.pairs(
                P.lists(P.withNull(map(IntNoHashCode::new, P.integersGeometric()))),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<IntNoHashCode>, Iterable<Integer>> p : take(LIMIT, ps2)) {
            Iterable<IdentityHashMap<IntNoHashCode, Integer>> maps = EP.identityMaps(p.a, p.b);
            testNoRemove(TINY_LIMIT, maps);
            List<IdentityHashMap<IntNoHashCode, Integer>> mapsList = toList(take(TINY_LIMIT, maps));
            assertTrue(p, all(m -> isSubsetOf(m.values(), p.b), mapsList));
        }

        for (List<Integer> xs : take(LIMIT, P.distinctLists(P.withNull(P.integersGeometric())))) {
            assertEquals(
                    xs,
                    toList(EP.identityMaps(Collections.emptyList(), xs)),
                    Collections.singletonList(new IdentityHashMap<>())
            );
        }

        for (Iterable<Integer> xs : take(LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            assertEquals(
                    xs,
                    toList(EP.identityMaps(Collections.emptyList(), xs)),
                    Collections.singletonList(new IdentityHashMap<>())
            );
        }

        Iterable<List<IntNoHashCode>> xss = P.listsAtLeast(
                1,
                P.withNull(map(IntNoHashCode::new, P.integersGeometric()))
        );
        for (List<IntNoHashCode> xs : take(LIMIT, xss)) {
            assertTrue(xs, isEmpty(EP.identityMaps(xs, Collections.emptyList())));
        }
    }

    private void propertiesRandomProvidersFixedScales() {
        initialize("randomProvidersFixedScales(int, int, int)");
        for (Triple<Integer, Integer, Integer> t : take(MEDIUM_LIMIT, P.triples(P.integersGeometric()))) {
            Iterable<RandomProvider> rps = EP.randomProvidersFixedScales(t.a, t.b, t.c);
            testNoRemove(TINY_LIMIT, rps);
            List<RandomProvider> rpsList = toList(take(TINY_LIMIT, rps));
            for (RandomProvider rp : rpsList) {
                rp.validate();
            }
            take(TINY_LIMIT, rpsList).forEach(RandomProvider::validate);
            assertTrue(
                    t,
                    all(
                            rp -> rp.getScale() == t.a && rp.getSecondaryScale() == t.b &&
                                    rp.getTertiaryScale() == t.c,
                            rpsList
                    )
            );
            assertTrue(t, unique(rpsList));
        }
    }

    private void propertiesRandomProvidersDefault() {
        initializeConstant("randomProvidersDefault()");
        biggerTest(
                EP,
                EP.randomProvidersDefault(),
                rp -> rp.getScale() == 32 && rp.getSecondaryScale() == 8 && rp.getTertiaryScale() == 2
        );
        take(LARGE_LIMIT, EP.randomProvidersDefault()).forEach(RandomProvider::validate);
    }

    private void propertiesRandomProvidersDefaultSecondaryAndTertiaryScale() {
        initializeConstant("randomProvidersDefaulSecondaryAndTertiaryScale()");
        biggerTest(
                EP,
                EP.randomProvidersDefaultSecondaryAndTertiaryScale(),
                rp -> rp.getSecondaryScale() == 8 && rp.getTertiaryScale() == 2
        );
        take(LARGE_LIMIT, EP.randomProvidersDefaultSecondaryAndTertiaryScale()).forEach(RandomProvider::validate);
    }

    private void propertiesRandomProvidersDefaultTertiaryScale() {
        initializeConstant("randomProvidersDefaulTertiaryScale()");
        biggerTest(EP, EP.randomProvidersDefaultTertiaryScale(), rp -> rp.getTertiaryScale() == 2);
        take(LARGE_LIMIT, EP.randomProvidersDefaultTertiaryScale()).forEach(RandomProvider::validate);
    }

    private void propertiesRandomProviders() {
        initializeConstant("randomProviders()");
        biggerTest(EP, EP.randomProviders(), rp -> true);
        take(LARGE_LIMIT, EP.randomProviders()).forEach(RandomProvider::validate);
    }
}
