package mho.wheels.iterables.randomProvider;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.ordering.comparators.ListBasedComparator;
import mho.wheels.ordering.comparators.WithNullComparator;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class RandomProviderProperties {
    private static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final String RANDOM_PROVIDER_CHARS = " ,-0123456789@PR[]adeimnorv";
    private static final int SMALL_LIMIT = 1000;
    private static final int TINY_LIMIT = 20;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize(String name) {
        P.reset();
        System.out.println("\t\ttesting " + name + " properties...");
    }

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.example(), 1000, "randomly"));
        System.out.println("RandomProvider properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesConstructor();
            propertiesConstructor_List_Integer();
            propertiesGetScale();
            propertiesGetSecondaryScale();
            propertiesGetSeed();
            propertiesWithScale();
            propertiesWithSecondaryScale();
            propertiesCopy();
            propertiesDeepCopy();
            propertiesReset();
            propertiesGetId();
            propertiesIntegers();
            propertiesLongs();
            propertiesBooleans();
            propertiesUniformSample_Iterable();
            propertiesUniformSample_String();
            propertiesOrderings();
            propertiesRoundingModes();
            propertiesPositiveBytes();
            propertiesPositiveShorts();
            propertiesPositiveIntegers();
            propertiesPositiveLongs();
            propertiesNegativeBytes();
            propertiesNegativeShorts();
            propertiesNegativeIntegers();
            propertiesNegativeLongs();
            propertiesNaturalBytes();
            propertiesNaturalShorts();
            propertiesNaturalIntegers();
            propertiesNaturalLongs();
            propertiesNonzeroBytes();
            propertiesNonzeroShorts();
            propertiesNonzeroIntegers();
            propertiesNonzeroLongs();
            propertiesBytes();
            propertiesShorts();
            propertiesAsciiCharacters();
            propertiesCharacters();
            propertiesRangeUp_byte();
            propertiesRangeUp_short();
            propertiesRangeUp_int();
            propertiesRangeUp_long();
            propertiesRangeUp_char();
            propertiesRangeDown_byte();
            propertiesRangeDown_short();
            propertiesRangeDown_int();
            propertiesRangeDown_long();
            propertiesRangeDown_char();
            propertiesRange_byte_byte();
            propertiesRange_short_short();
            propertiesRange_int_int();
            propertiesRange_long_long();
            propertiesRange_BigInteger_BigInteger();
            propertiesRange_char_char();
            propertiesPositiveIntegersGeometric();
            propertiesNegativeIntegersGeometric();
            propertiesNaturalIntegersGeometric();
            propertiesNonzeroIntegersGeometric();
            propertiesIntegersGeometric();
            propertiesRangeUpGeometric();
            propertiesRangeDownGeometric();
            propertiesPositiveBigIntegers();
            propertiesNegativeBigIntegers();
            propertiesNaturalBigIntegers();
            propertiesNonzeroBigIntegers();
            propertiesBigIntegers();
            propertiesRangeUp_BigInteger();
            propertiesRangeDown_BigInteger();
            propertiesPositiveBinaryFractions();
            propertiesNegativeBinaryFractions();
            propertiesNonzeroBinaryFractions();
            propertiesBinaryFractions();
            propertiesRangeUp_BinaryFraction();
            propertiesRangeDown_BinaryFraction();
            propertiesRange_BinaryFraction_BinaryFraction();
            propertiesPositiveFloats();
            propertiesNegativeFloats();
            propertiesNonzeroFloats();
            propertiesFloats();
            propertiesPositiveDoubles();
            propertiesNegativeDoubles();
            propertiesNonzeroDoubles();
            propertiesDoubles();
            propertiesPositiveFloatsUniform();
            propertiesNegativeFloatsUniform();
            propertiesNonzeroFloatsUniform();
            propertiesFloatsUniform();
            propertiesPositiveDoublesUniform();
            propertiesNegativeDoublesUniform();
            propertiesNonzeroDoublesUniform();
            propertiesDoublesUniform();
            propertiesRangeUp_float();
            propertiesRangeDown_float();
            propertiesRange_float_float();
            propertiesRangeUp_double();
            propertiesRangeDown_double();
            propertiesRange_double_double();
            propertiesRangeUpUniform_float();
            propertiesRangeDownUniform_float();
            propertiesRangeUniform_float_float();
            propertiesRangeUpUniform_double();
            propertiesRangeDownUniform_double();
            propertiesRangeUniform_double_double();
            propertiesPositiveBigDecimals();
            propertiesNegativeBigDecimals();
            propertiesNonzeroBigDecimals();
            propertiesBigDecimals();
            propertiesPositiveCanonicalBigDecimals();
            propertiesNegativeCanonicalBigDecimals();
            propertiesNonzeroCanonicalBigDecimals();
            propertiesCanonicalBigDecimals();
            propertiesRangeUp_BigDecimal();
            propertiesRangeDown_BigDecimal();
            propertiesRange_BigDecimal_BigDecimal();
            propertiesRangeUpCanonical_BigDecimal();
            propertiesRangeDownCanonical_BigDecimal();
            propertiesRangeCanonical_BigDecimal_BigDecimal();
            propertiesWithElement();
            propertiesWithNull();
            propertiesOptionals();
            propertiesNullableOptionals();
            propertiesDependentPairsInfinite();
            propertiesShuffle();
            propertiesPermutationsFinite();
            propertiesStringPermutations();
            propertiesPrefixPermutations();
            propertiesStrings_int_String();
            propertiesStrings_int();
            propertiesLists();
            propertiesStrings_String();
            propertiesStrings();
            propertiesListsAtLeast();
            propertiesStringsAtLeast_int_String();
            propertiesStringsAtLeast_int();
            propertiesDistinctStrings_int_String();
            propertiesDistinctStrings_int();
            propertiesDistinctLists();
            propertiesDistinctStrings_String();
            propertiesDistinctStrings();
            propertiesDistinctListsAtLeast();
            propertiesDistinctStringsAtLeast_int_String();
            propertiesDistinctStringsAtLeast_int();
            propertiesStringBags_int_String();
            propertiesStringBags_int();
            propertiesBags();
            propertiesStringBags_String();
            propertiesStringBags();
            propertiesBagsAtLeast();
            propertiesStringBagsAtLeast_int_String();
            propertiesStringBagsAtLeast_int();
            propertiesStringSubsets_int_String();
            propertiesStringSubsets_int();
            propertiesSubsets();
            propertiesStringSubsets_String();
            propertiesStringSubsets();
            propertiesSubsetsAtLeast();
            propertiesStringSubsetsAtLeast_int_String();
            propertiesStringSubsetsAtLeast_int();
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
            propertiesRandomProvidersFixedScales();
            propertiesRandomProvidersDefault();
            propertiesRandomProvidersDefaultSecondaryScale();
            propertiesRandomProviders();
            propertiesEquals();
            propertiesHashCode();
            propertiesToString();
        }
        System.out.println("Done");
    }

    private static void propertiesConstructor() {
        initialize("RandomProvider()");
        //noinspection unused
        for (Void v : take(LIMIT, repeat((Void) null))) {
            RandomProvider rp = new RandomProvider();
            rp.validate();
        }
    }

    private static void propertiesConstructor_List_Integer() {
        initialize("RandomProvider(List<Integer>)");
        for (List<Integer> is : take(LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            RandomProvider rp = new RandomProvider(is);
            rp.validate();
            assertEquals(is, rp.getScale(), 32);
            assertEquals(is, rp.getSecondaryScale(), 8);
        }

        Iterable<List<Integer>> isFail = filterInfinite(js -> js.size() != IsaacPRNG.SIZE, P.lists(P.integers()));
        for (List<Integer> is : take(LIMIT, isFail)) {
            try {
                new RandomProvider(is);
                fail(is);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesGetScale() {
        initialize("getScale()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int scale = rp.getScale();
            assertEquals(rp, rp.withScale(scale), rp);
        }
    }

    private static void propertiesGetSecondaryScale() {
        initialize("getSecondaryScale()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int secondaryScale = rp.getSecondaryScale();
            assertEquals(rp, rp.withSecondaryScale(secondaryScale), rp);
        }
    }

    private static void propertiesGetSeed() {
        initialize("getSeed()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            List<Integer> seed = rp.getSeed();
            assertEquals(rp, seed.size(), IsaacPRNG.SIZE);
            assertEquals(
                    rp,
                    new RandomProvider(seed).withScale(rp.getScale()).withSecondaryScale(rp.getSecondaryScale()),
                    rp
            );
        }
    }

    private static void propertiesWithScale() {
        initialize("withScale(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            RandomProvider rp = p.a.withScale(p.b);
            rp.validate();
            assertEquals(p, rp.getScale(), p.b);
            assertEquals(p, rp.getSecondaryScale(), p.a.getSecondaryScale());
            assertEquals(p, rp.getSeed(), p.a.getSeed());
            inverses(x -> x.withScale(p.b), (RandomProvider y) -> y.withScale(p.a.getScale()), p.a);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            idempotent(x -> x.withScale(rp.getScale()), rp);
        }
    }

    private static void propertiesWithSecondaryScale() {
        initialize("withSecondaryScale(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            RandomProvider rp = p.a.withSecondaryScale(p.b);
            rp.validate();
            assertEquals(p, rp.getScale(), p.a.getScale());
            assertEquals(p, rp.getSecondaryScale(), p.b);
            assertEquals(p, rp.getSeed(), p.a.getSeed());
            inverses(
                    x -> x.withSecondaryScale(p.b),
                    (RandomProvider y) -> y.withSecondaryScale(p.a.getSecondaryScale()),
                    p.a
            );
        }

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            idempotent(x -> x.withSecondaryScale(rp.getSecondaryScale()), rp);
        }
    }

    private static void propertiesCopy() {
        initialize("copy()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider copy = rp.copy();
            assertEquals(rp, rp, copy);
            head(rp.integers());
            assertEquals(rp, rp, copy);
        }
    }

    private static void propertiesDeepCopy() {
        initialize("deepCopy()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider copy = rp.deepCopy();
            assertEquals(rp, rp, copy);
            head(rp.integers());
            assertNotEquals(rp, rp, copy);
        }
    }

    private static void propertiesReset() {
        initialize("reset()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider rpDependent = rp.withScale(10);
            RandomProvider original = rp.deepCopy();
            RandomProvider dependent = original.withScale(10);
            assertEquals(rp, rpDependent, dependent);
            head(rp.integers());
            assertNotEquals(rp, rp, original);
            assertNotEquals(rp, rpDependent, dependent);
            rp.reset();
            assertEquals(rp, rp, original);
            assertEquals(rp, rpDependent, dependent);
        }
    }

    private static void propertiesGetId() {
        initialize("getId()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            rp.getId();
        }
    }

    private static <T> void simpleTestWithNulls(
            @NotNull RandomProvider rp,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        rp.reset();
        assertTrue(rp, all(predicate, take(TINY_LIMIT, xs)));
        rp.reset();
        testNoRemove(TINY_LIMIT, xs);
    }

    private static <T> void simpleTest(
            @NotNull RandomProvider rp,
            @NotNull Iterable<T> xs,
            @NotNull Predicate<T> predicate
    ) {
        simpleTestWithNulls(rp, xs, x -> x != null && predicate.test(x));
    }

    private static void propertiesIntegers() {
        initialize("integers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.integers();
            simpleTest(rp, is, i -> true);
        }
    }

    private static void propertiesLongs() {
        initialize("longs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.longs();
            simpleTest(rp, ls, l -> true);
        }
    }

    private static void propertiesBooleans() {
        initialize("booleans()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Boolean> bs = rp.booleans();
            simpleTest(rp, bs, b -> true);
            for (boolean b : ExhaustiveProvider.INSTANCE.booleans()) {
                assertTrue(rp, elem(b, bs));
            }
        }
    }

    private static void propertiesUniformSample_Iterable() {
        initialize("uniformSample(Iterable<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.uniformSample(p.b);
            simpleTestWithNulls(p.a, is, p.b::contains);
            assertEquals(is, isEmpty(is), p.b.isEmpty());
        }

        for (RandomProvider rp : take(LIMIT,P.randomProvidersDefault())) {
            try {
                rp.uniformSample(Collections.emptyList());
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.stringsAtLeast(1)))) {
            Iterable<Character> cs = p.a.uniformSample(p.b);
            simpleTest(p.a, cs, c -> elem(c, cs));
            assertEquals(cs, isEmpty(cs), p.b.isEmpty());
        }

        for (RandomProvider rp : take(LIMIT,P.randomProvidersDefault())) {
            try {
                rp.uniformSample("");
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesOrderings() {
        initialize("orderings()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Ordering> os = rp.orderings();
            simpleTest(rp, os, o -> true);
            for (Ordering o : ExhaustiveProvider.INSTANCE.orderings()) {
                assertTrue(rp, elem(o, os));
            }
        }
    }

    private static void propertiesRoundingModes() {
        initialize("roundingModes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<RoundingMode> rms = rp.roundingModes();
            simpleTest(rp, rms, rm -> true);
            for (RoundingMode rm : ExhaustiveProvider.INSTANCE.roundingModes()) {
                assertTrue(rp, elem(rm, rms));
            }
        }
    }

    private static void propertiesPositiveBytes() {
        initialize("positiveBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.positiveBytes();
            simpleTest(rp, bs, b -> b > 0);
        }
    }

    private static void propertiesPositiveShorts() {
        initialize("positiveShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.positiveShorts();
            simpleTest(rp, ss, s -> s > 0);
        }
    }

    private static void propertiesPositiveIntegers() {
        initialize("positiveIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.positiveIntegers();
            simpleTest(rp, is, i -> i > 0);
        }
    }

    private static void propertiesPositiveLongs() {
        initialize("positiveLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.positiveLongs();
            simpleTest(rp, ls, l -> l > 0);
        }
    }

    private static void propertiesNegativeBytes() {
        initialize("negativeBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.negativeBytes();
            simpleTest(rp, bs, b -> b < 0);
        }
    }

    private static void propertiesNegativeShorts() {
        initialize("negativeShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.negativeShorts();
            simpleTest(rp, ss, s -> s < 0);
        }
    }

    private static void propertiesNegativeIntegers() {
        initialize("negativeIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.negativeIntegers();
            simpleTest(rp, is, i -> i < 0);
        }
    }

    private static void propertiesNegativeLongs() {
        initialize("negativeLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.negativeLongs();
            simpleTest(rp, ls, l -> l < 0);
        }
    }

    private static void propertiesNaturalBytes() {
        initialize("naturalBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.naturalBytes();
            simpleTest(rp, bs, b -> b >= 0);
        }
    }

    private static void propertiesNaturalShorts() {
        initialize("naturalShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.naturalShorts();
            simpleTest(rp, ss, s -> s >= 0);
        }
    }

    private static void propertiesNaturalIntegers() {
        initialize("naturalIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.naturalIntegers();
            simpleTest(rp, is, i -> i >= 0);
        }
    }

    private static void propertiesNaturalLongs() {
        initialize("naturalLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.naturalLongs();
            simpleTest(rp, ls, l -> l >= 0);
        }
    }

    private static void propertiesNonzeroBytes() {
        initialize("nonzeroBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.nonzeroBytes();
            simpleTest(rp, bs, b -> b != 0);
        }
    }

    private static void propertiesNonzeroShorts() {
        initialize("nonzeroShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.nonzeroShorts();
            simpleTest(rp, ss, s -> s != 0);
        }
    }

    private static void propertiesNonzeroIntegers() {
        initialize("nonzeroIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.nonzeroIntegers();
            simpleTest(rp, is, i -> i != 0);
        }
    }

    private static void propertiesNonzeroLongs() {
        initialize("nonzeroLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.nonzeroLongs();
            simpleTest(rp, ls, l -> l != 0);
        }
    }

    private static void propertiesBytes() {
        initialize("bytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.bytes();
            simpleTest(rp, bs, b -> true);
        }
    }

    private static void propertiesShorts() {
        initialize("shorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.shorts();
            simpleTest(rp, ss, s -> true);
        }
    }

    private static void propertiesAsciiCharacters() {
        initialize("asciiCharacters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.asciiCharacters();
            simpleTest(rp, cs, c -> c < 128);
        }
    }

    private static void propertiesCharacters() {
        initialize("characters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.characters();
            simpleTest(rp, cs, c -> true);
        }
    }

    private static void propertiesRangeUp_byte() {
        initialize("rangeUp(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeUp(p.b);
            simpleTest(p.a, bs, b -> b >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Byte.MAX_VALUE), repeat(Byte.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_short() {
        initialize("rangeUp(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeUp(p.b);
            simpleTest(p.a, ss, s -> s >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Short.MAX_VALUE), repeat(Short.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_int() {
        initialize("rangeUp(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeUp(p.b);
            simpleTest(p.a, is, i -> i >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Integer.MAX_VALUE), repeat(Integer.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_long() {
        initialize("rangeUp(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeUp(p.b);
            simpleTest(p.a, ls, l -> l >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Long.MAX_VALUE), repeat(Long.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_char() {
        initialize("rangeUp(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeUp(p.b);
            simpleTest(p.a, cs, c -> c >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Character.MAX_VALUE), repeat(Character.MAX_VALUE));
        }
    }

    private static void propertiesRangeDown_byte() {
        initialize("rangeDown(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeDown(p.b);
            simpleTest(p.a, bs, b -> b <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Byte.MIN_VALUE), repeat(Byte.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_short() {
        initialize("rangeDown(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeDown(p.b);
            simpleTest(p.a, ss, s -> s <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Short.MIN_VALUE), repeat(Short.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_int() {
        initialize("rangeDown(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeDown(p.b);
            simpleTest(p.a, is, i -> i <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Integer.MIN_VALUE), repeat(Integer.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_long() {
        initialize("rangeDown(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeDown(p.b);
            simpleTest(p.a, ls, l -> l <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Long.MIN_VALUE), repeat(Long.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_char() {
        initialize("rangeDown(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeDown(p.b);
            simpleTest(p.a, cs, b -> b <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Character.MIN_VALUE), repeat(Character.MIN_VALUE));
        }
    }

    private static void propertiesRange_byte_byte() {
        initialize("range(byte, byte)");
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> t : take(LIMIT, ts)) {
            Iterable<Byte> bs = t.a.range(t.b, t.c);
            simpleTest(t.a, bs, b -> b >= t.b && b <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(bs));
        }

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_short_short() {
        initialize("range(short, short)");
        Iterable<Triple<RandomProvider, Short, Short>> ts = P.triples(
                P.randomProvidersDefault(),
                P.shorts(),
                P.shorts()
        );
        for (Triple<RandomProvider, Short, Short> t : take(LIMIT, ts)) {
            Iterable<Short> ss = t.a.range(t.b, t.c);
            simpleTest(t.a, ss, s -> s >= t.b && s <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(ss));
        }

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_int_int() {
        initialize("range(int, int)");
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.integers(),
                P.integers()
        );
        for (Triple<RandomProvider, Integer, Integer> t : take(LIMIT, ts)) {
            Iterable<Integer> is = t.a.range(t.b, t.c);
            simpleTest(t.a, is, i -> i >= t.b && i <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(is));
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_long_long() {
        initialize("range(long, long)");
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> t : take(LIMIT, ts)) {
            Iterable<Long> ls = t.a.range(t.b, t.c);
            simpleTest(t.a, ls, l -> l >= t.b && l <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(ls));
        }

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        initialize("range(BigInteger, BigInteger)");

        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = P.triples(
                P.randomProvidersDefault(),
                P.bigIntegers(),
                P.bigIntegers()
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            Iterable<BigInteger> is = t.a.range(t.b, t.c);
            simpleTest(t.a, is, i -> ge(i, t.b) && le(i, t.c));
            assertEquals(t, gt(t.b, t.c), isEmpty(is));
        }

        Iterable<Pair<RandomProvider, BigInteger>> ps = P.pairs(P.randomProvidersDefault(), P.bigIntegers());
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_char_char() {
        initialize("range(char, char)");
        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.characters(),
                P.characters()
        );
        for (Triple<RandomProvider, Character, Character> t : take(LIMIT, ts)) {
            Iterable<Character> cs = t.a.range(t.b, t.c);
            simpleTest(t.a, cs, c -> c >= t.b && c <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(cs));
        }

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesPositiveIntegersGeometric() {
        initialize("positiveIntegersGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.positiveIntegersGeometric();
            simpleTest(rp, is, i -> i > 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.positiveIntegersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNegativeIntegersGeometric() {
        initialize("negativeIntegersGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.negativeIntegersGeometric();
            simpleTest(rp, is, i -> i < 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.negativeIntegersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNaturalIntegersGeometric() {
        initialize("naturalIntegersGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.naturalIntegersGeometric();
            simpleTest(rp, is, i -> i >= 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.naturalIntegersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).naturalIntegersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNonzeroIntegersGeometric() {
        initialize("nonzeroIntegersGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.nonzeroIntegersGeometric();
            simpleTest(rp, is, i -> i != 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nonzeroIntegersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesIntegersGeometric() {
        initialize("integersGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.integersGeometric();
            simpleTest(rp, is, i -> true);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.integersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).integersGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeUpGeometric() {
        initialize("rangeUpGeometric(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b >= 1 || p.a.getScale() < Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeUpGeometric(p.b);
            simpleTest(p.a, is, i -> i >= p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() <= p.b || p.b < 1 && p.a.getScale() >= Integer.MAX_VALUE + p.b,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUpGeometric(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDownGeometric() {
        initialize("rangeDownGeometric(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeDownGeometric(p.b);
            simpleTest(p.a, is, i -> i <= p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() >= p.b || p.b > -1 && p.a.getScale() <= p.b - Integer.MAX_VALUE,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDownGeometric(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesPositiveBigIntegers() {
        initialize("positiveBigIntegers()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.positiveBigIntegers();
            simpleTest(rp, is, i -> i.signum() == 1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.positiveBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {
            }
        }
    }

    private static void propertiesNegativeBigIntegers() {
        initialize("negativeBigIntegers()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.negativeBigIntegers();
            simpleTest(rp, is, i -> i.signum() == -1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.negativeBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNaturalBigIntegers() {
        initialize("naturalBigIntegers()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.naturalBigIntegers();
            simpleTest(rp, is, i -> i.signum() != -1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.naturalBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).naturalBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNonzeroBigIntegers() {
        initialize("nonzeroBigIntegers()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.nonzeroBigIntegers();
            simpleTest(rp, is, i -> !i.equals(BigInteger.ZERO));
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nonzeroBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesBigIntegers() {
        initialize("bigIntegers()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.bigIntegers();
            simpleTest(rp, is, i -> true);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.bigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).bigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeUp_BigInteger() {
        initialize("rangeUp(BigInteger)");
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            Iterable<BigInteger> is = p.a.rangeUp(p.b);
            simpleTest(p.a, is, i -> ge(i, p.b));
        }

        Iterable<Pair<RandomProvider, BigInteger>> psFail = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() <= minBitLength || minBitLength != 0 && p.a.getScale() == Integer.MAX_VALUE;
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDown_BigInteger() {
        initialize("rangeDown(BigInteger)");
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            Iterable<BigInteger> is = p.a.rangeDown(p.b);
            simpleTest(p.a, is, i -> le(i, p.b));
        }

        Iterable<Pair<RandomProvider, BigInteger>> psFail = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() <= minBitLength || minBitLength != 0 && p.a.getScale() == Integer.MAX_VALUE;
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesPositiveBinaryFractions() {
        initialize("positiveBinaryFractions()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BinaryFraction> bfs = rp.positiveBinaryFractions();
            rp.reset();
            take(TINY_LIMIT, bfs).forEach(BinaryFraction::validate);
            simpleTest(rp, bfs, bf -> bf.signum() == 1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.positiveBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.positiveBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNegativeBinaryFractions() {
        initialize("negativeBinaryFractions()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BinaryFraction> bfs = rp.negativeBinaryFractions();
            rp.reset();
            take(TINY_LIMIT, bfs).forEach(BinaryFraction::validate);
            simpleTest(rp, bfs, bf -> bf.signum() == -1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.negativeBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.negativeBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNonzeroBinaryFractions() {
        initialize("nonzeroBinaryFractions()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BinaryFraction> bfs = rp.nonzeroBinaryFractions();
            rp.reset();
            take(TINY_LIMIT, bfs).forEach(BinaryFraction::validate);
            simpleTest(rp, bfs, bf -> bf != BinaryFraction.ZERO);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nonzeroBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nonzeroBinaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesBinaryFractions() {
        initialize("binaryFractions()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BinaryFraction> bfs = rp.binaryFractions();
            rp.reset();
            take(TINY_LIMIT, bfs).forEach(BinaryFraction::validate);
            simpleTest(rp, bfs, bf -> true);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 1, P.randomProviders()))) {
            try {
                rp.binaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.binaryFractions();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeUp_BinaryFraction() {
        initialize("rangeUp(BinaryFraction)");
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            Iterable<BinaryFraction> bfs = p.a.rangeUp(p.b);
            simpleTest(p.a, bfs, bf -> ge(bf, p.b));
        }

        Iterable<Pair<RandomProvider, BinaryFraction>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 1, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.binaryFractions());
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDown_BinaryFraction() {
        initialize("rangeDown(BinaryFraction)");
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            Iterable<BinaryFraction> bfs = p.a.rangeDown(p.b);
            simpleTest(p.a, bfs, bf -> le(bf, p.b));
        }

        Iterable<Pair<RandomProvider, BinaryFraction>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 1, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.binaryFractions());
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRange_BinaryFraction_BinaryFraction() {
        initialize("range(BinaryFraction, BinaryFraction)");

        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = P.triples(
                filterInfinite(
                        x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                        P.randomProvidersDefaultSecondaryScale()
                ),
                P.binaryFractions(),
                P.binaryFractions()
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, ts)) {
            Iterable<BinaryFraction> bfs = t.a.range(t.b, t.c);
            simpleTest(t.a, bfs, bf -> ge(bf, t.b) && le(bf, t.c));
            assertEquals(t, gt(t.b, t.c), isEmpty(bfs));
            take(TINY_LIMIT, bfs).forEach(BinaryFraction::validate);
        }

        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(P.randomProvidersDefault(), P.binaryFractions());
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }

        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> tsFail = P.triples(
                filterInfinite(x -> x.getScale() < 1, P.randomProvidersDefaultSecondaryScale()),
                P.binaryFractions(),
                P.binaryFractions()
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, tsFail)) {
            try {
                t.a.range(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = P.triples(P.randomProvidersDefault(), P.binaryFractions(), P.binaryFractions());
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, tsFail)) {
            try {
                t.a.withScale(Integer.MAX_VALUE).range(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesPositiveFloats() {
        initialize("positiveFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.positiveFloats();
            simpleTest(rp, fs, f -> f > 0);
        }
    }

    private static void propertiesNegativeFloats() {
        initialize("negativeFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.negativeFloats();
            simpleTest(rp, fs, f -> f < 0);
        }
    }

    private static void propertiesNonzeroFloats() {
        initialize("nonzeroFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.nonzeroFloats();
            simpleTest(rp, fs, f -> f != 0);
        }
    }

    private static void propertiesFloats() {
        initialize("floats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.floats();
            simpleTest(rp, fs, f -> true);
        }
    }

    private static void propertiesPositiveDoubles() {
        initialize("positiveDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.positiveDoubles();
            simpleTest(rp, ds, d -> d > 0);
        }
    }

    private static void propertiesNegativeDoubles() {
        initialize("negativeDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.negativeDoubles();
            simpleTest(rp, ds, d -> d < 0);
        }
    }

    private static void propertiesNonzeroDoubles() {
        initialize("nonzeroDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.nonzeroDoubles();
            simpleTest(rp, ds, d -> d != 0);
        }
    }

    private static void propertiesDoubles() {
        initialize("doubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.doubles();
            simpleTest(rp, ds, d -> true);
        }
    }

    private static void propertiesPositiveFloatsUniform() {
        initialize("positiveFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.positiveFloatsUniform();
            simpleTest(rp, fs, f -> f > 0 && Float.isFinite(f));
        }
    }

    private static void propertiesNegativeFloatsUniform() {
        initialize("negativeFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.negativeFloatsUniform();
            simpleTest(rp, fs, f -> f < 0 && Float.isFinite(f));
        }
    }

    private static void propertiesNonzeroFloatsUniform() {
        initialize("nonzeroFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.nonzeroFloatsUniform();
            simpleTest(rp, fs, f -> f != 0 && Float.isFinite(f));
        }
    }

    private static void propertiesFloatsUniform() {
        initialize("floatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.floatsUniform();
            simpleTest(rp, fs, f -> Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesPositiveDoublesUniform() {
        initialize("positiveDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.positiveDoublesUniform();
            simpleTest(rp, ds, d -> d > 0 && Double.isFinite(d));
        }
    }

    private static void propertiesNegativeDoublesUniform() {
        initialize("negativeDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.negativeDoublesUniform();
            simpleTest(rp, ds, d -> d < 0 && Double.isFinite(d));
        }
    }

    private static void propertiesNonzeroDoublesUniform() {
        initialize("nonzeroDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.nonzeroDoublesUniform();
            simpleTest(rp, ds, d -> d != 0 && Double.isFinite(d));
        }
    }

    private static void propertiesDoublesUniform() {
        initialize("doublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.doublesUniform();
            simpleTest(rp, ds, d -> Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
        }
    }

    private static void propertiesRangeUp_float() {
        initialize("rangeUp(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            Iterable<Float> fs = p.a.rangeUp(p.b);
            simpleTest(p.a, fs, f -> f >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Float.POSITIVE_INFINITY), repeat(Float.POSITIVE_INFINITY));
        }
    }

    private static void propertiesRangeDown_float() {
        initialize("rangeDown(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            Iterable<Float> fs = p.a.rangeDown(p.b);
            simpleTest(p.a, fs, f -> f <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Float.NEGATIVE_INFINITY), repeat(Float.NEGATIVE_INFINITY));
        }
    }

    private static void propertiesRange_float_float() {
        initialize("range(float, float)");
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats()),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            Iterable<Float> fs = t.a.range(t.b, t.c);
            simpleTest(t.a, fs, f -> f >= t.b && f <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(fs));
        }

        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f) && f != 0.0f, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            try {
                p.a.range(Float.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.range(p.b, Float.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesRangeUp_double() {
        initialize("rangeUp(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            Iterable<Double> ds = p.a.rangeUp(p.b);
            simpleTest(p.a, ds, d -> d >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Double.POSITIVE_INFINITY), repeat(Double.POSITIVE_INFINITY));
        }
    }

    private static void propertiesRangeDown_double() {
        initialize("rangeDown(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            Iterable<Double> ds = p.a.rangeDown(p.b);
            simpleTest(p.a, ds, d -> d <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Double.NEGATIVE_INFINITY), repeat(Double.NEGATIVE_INFINITY));
        }
    }

    private static void propertiesRange_double_double() {
        initialize("range(double, double)");
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles()),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            Iterable<Double> ds = t.a.range(t.b, t.c);
            simpleTest(t.a, ds, f -> f >= t.b && f <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(ds));
        }

        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d) && d != 0.0, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            try {
                p.a.range(Double.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.range(p.b, Double.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesRangeUpUniform_float() {
        initialize("rangeUpUniform(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            Iterable<Float> fs = p.a.rangeUpUniform(p.b);
            simpleTest(p.a, fs, f -> f >= p.b && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesRangeDownUniform_float() {
        initialize("rangeDownUniform(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            Iterable<Float> fs = p.a.rangeDownUniform(p.b);
            simpleTest(p.a, fs, f -> f <= p.b && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesRangeUniform_float_float() {
        initialize("rangeUniform(float, float)");
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats()),
                filter(Float::isFinite, P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            Iterable<Float> fs = t.a.rangeUniform(t.b, t.c);
            simpleTest(
                    t.a,
                    fs,
                    f -> f >= t.b && f <= t.c && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f)
            );
            assertEquals(t, t.b > t.c, isEmpty(fs));
        }

        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.rangeUniform(p.b, p.b), repeat(p.b));
        }

        ps = P.pairs(P.randomProvidersDefault(), filter(Float::isFinite, P.floats()));
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            try {
                p.a.rangeUniform(Float.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(Float.NEGATIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(Float.POSITIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Float.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Float.NEGATIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Float.POSITIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesRangeUpUniform_double() {
        initialize("rangeUpUniform(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            Iterable<Double> ds = p.a.rangeUpUniform(p.b);
            simpleTest(p.a, ds, d -> d >= p.b && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
        }
    }

    private static void propertiesRangeDownUniform_double() {
        initialize("rangeDownUniform(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            Iterable<Double> ds = p.a.rangeDownUniform(p.b);
            simpleTest(p.a, ds, d -> d <= p.b && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
        }
    }

    private static void propertiesRangeUniform_double_double() {
        initialize("rangeUniform(double, double)");
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles()),
                filter(Double::isFinite, P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            Iterable<Double> ds = t.a.rangeUniform(t.b, t.c);
            simpleTest(
                    t.a,
                    ds,
                    d -> d >= t.b && d <= t.c && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d)
            );
            assertEquals(t, t.b > t.c, isEmpty(ds));
        }

        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.rangeUniform(p.b, p.b), repeat(p.b));
        }

        ps = P.pairs(P.randomProvidersDefault(), filter(Double::isFinite, P.doubles()));
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            try {
                p.a.rangeUniform(Double.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(Double.NEGATIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(Double.POSITIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Double.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Double.NEGATIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.rangeUniform(p.b, Double.POSITIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
        }
    }

    private static void propertiesPositiveBigDecimals() {
        initialize("positiveBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.positiveBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> bd.signum() == 1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.positiveBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.positiveBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNegativeBigDecimals() {
        initialize("negativeBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.negativeBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> bd.signum() == -1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.negativeBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.negativeBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNonzeroBigDecimals() {
        initialize("nonzeroBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.nonzeroBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> ne(bd, BigDecimal.ZERO));
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nonzeroBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nonzeroBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesBigDecimals() {
        initialize("bigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.bigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> true);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 1, P.randomProviders()))) {
            try {
                rp.bigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.bigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesPositiveCanonicalBigDecimals() {
        initialize("positiveCanonicalBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.positiveCanonicalBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> bd.signum() == 1 && BigDecimalUtils.isCanonical(bd));
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.positiveCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.positiveCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNegativeCanonicalBigDecimals() {
        initialize("negativeCanonicalBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.negativeCanonicalBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> bd.signum() == -1 && BigDecimalUtils.isCanonical(bd));
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.negativeCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.negativeCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNonzeroCanonicalBigDecimals() {
        initialize("nonzeroCanonicalBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.nonzeroCanonicalBigDecimals();
            rp.reset();
            simpleTest(rp, bds, bd -> ne(bd, BigDecimal.ZERO) && BigDecimalUtils.isCanonical(bd));
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nonzeroCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nonzeroCanonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesCanonicalBigDecimals() {
        initialize("canonicalBigDecimals()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigDecimal> bds = rp.canonicalBigDecimals();
            rp.reset();
            simpleTest(rp, bds, BigDecimalUtils::isCanonical);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.canonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.canonicalBigDecimals();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeUp_BigDecimal() {
        initialize("rangeUp(BigDecimal)");
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            Iterable<BigDecimal> bds = p.a.rangeUp(p.b);
            simpleTest(p.a, bds, bd -> ge(bd, p.b));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.bigDecimals());
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDown_BigDecimal() {
        initialize("rangeDown(BigDecimal)");
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            Iterable<BigDecimal> bds = p.a.rangeDown(p.b);
            simpleTest(p.a, bds, bd -> le(bd, p.b));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.bigDecimals());
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRange_BigDecimal_BigDecimal() {
        initialize("range(BigDecimal, BigDecimal)");

        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = P.triples(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, ts)) {
            Iterable<BigDecimal> bds = t.a.range(t.b, t.c);
            simpleTest(t.a, bds, bd -> ge(bd, t.b) && le(bd, t.c));
            assertEquals(t, gt(t.b, t.c), isEmpty(bds));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            assertTrue(p, all(bd -> eq(bd, p.b), take(TINY_LIMIT, p.a.range(p.b, p.b))));
        }

        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> tsFail = P.triples(
                filterInfinite(x -> x.getScale() < 1, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, tsFail)) {
            try {
                t.a.range(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = P.triples(
                filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, tsFail)) {
            try {
                t.a.range(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeUpCanonical_BigDecimal() {
        initialize("rangeUpCanonical(BigDecimal)");
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            Iterable<BigDecimal> bds = p.a.rangeUpCanonical(p.b);
            simpleTest(p.a, bds, bd -> ge(bd, p.b) && BigDecimalUtils.isCanonical(bd));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUpCanonical(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.bigDecimals());
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUpCanonical(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDownCanonical_BigDecimal() {
        initialize("rangeDownCanonical(BigDecimal)");
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            Iterable<BigDecimal> bds = p.a.rangeDownCanonical(p.b);
            simpleTest(p.a, bds, bd -> le(bd, p.b) && BigDecimalUtils.isCanonical(bd));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDownCanonical(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.bigDecimals());
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDownCanonical(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeCanonical_BigDecimal_BigDecimal() {
        initialize("rangeCanonical(BigDecimal, BigDecimal)");

        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = P.triples(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, ts)) {
            Iterable<BigDecimal> bds = t.a.rangeCanonical(t.b, t.c);
            simpleTest(t.a, bds, bd -> ge(bd, t.b) && le(bd, t.c) && BigDecimalUtils.isCanonical(bd));
            assertEquals(t, gt(t.b, t.c), isEmpty(bds));
        }

        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.rangeCanonical(p.b, p.b), repeat(BigDecimalUtils.canonicalize(p.b)));
        }

        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> tsFail = P.triples(
                filterInfinite(x -> x.getScale() < 1, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, tsFail)) {
            try {
                t.a.rangeCanonical(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = P.triples(
                filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(LIMIT, tsFail)) {
            try {
                t.a.rangeCanonical(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesWithElement() {
        initialize("withElement(T, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            List<Integer> withElement = toList(take(TINY_LIMIT, t.a.withElement(t.b, t.c)));
            testNoRemove(TINY_LIMIT, t.a.withElement(t.b, t.c));
            List<Integer> filteredResult = toList(filter(x -> !Objects.equals(x, t.b), withElement));
            assertEquals(
                    t,
                    filteredResult,
                    toList(take(filteredResult.size(), filterInfinite(x -> !Objects.equals(x, t.b), t.c)))
            );
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> tsFail1 = P.triples(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail1)) {
            try {
                toList(t.a.withElement(t.b, t.c));
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail2 = P.triples(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.withElement(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesWithNull() {
        initialize("withNull(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<Integer> withNull = toList(take(TINY_LIMIT, p.a.withNull(p.b)));
            testNoRemove(TINY_LIMIT, p.a.withNull(p.b));
            List<Integer> filteredResult = toList(filter(x -> x != null, withNull));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail1 = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.integersGeometric())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail1)) {
            try {
                toList(p.a.withNull(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                p.a.withNull(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesOptionals() {
        initialize("optionals(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<Optional<Integer>> os = toList(take(TINY_LIMIT, p.a.optionals(p.b)));
            testNoRemove(TINY_LIMIT, p.a.optionals(p.b));
            List<Integer> filteredResult = toList(optionalFilter(os));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.integersGeometric())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.optionals(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                p.a.optionals(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                toList(p.a.optionals(p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesNullableOptionals() {
        initialize("nullableOptionals(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<NullableOptional<Integer>> os = toList(take(TINY_LIMIT, p.a.nullableOptionals(p.b)));
            testNoRemove(TINY_LIMIT, p.a.nullableOptionals(p.b));
            List<Integer> filteredResult = toList(nullableOptionalFilter(os));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.nullableOptionals(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                p.a.nullableOptionals(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesDependentPairsInfinite() {
        initialize("dependentPairsInfinite(Iterable<A>, Function<A, Iterable<B>>)");
        RandomProvider RP = RandomProvider.example();
        IterableProvider PS = P.withScale(4);
        Function<List<Integer>, Iterable<Map<Integer, List<Integer>>>> f = xs -> filterInfinite(
                m -> !all(p -> isEmpty(p.b), fromMap(m)),
                PS.maps(xs, map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric())))
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
                nub(
                        P.dependentPairsInfinite(
                                nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric()))),
                                f
                        )
                )
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, ps)) {
            Iterable<Pair<Integer, Integer>> pairs = RP.dependentPairsInfinite(p.a, p.b);
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
                toList(RP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }

        f = xs -> {
            if (xs.isEmpty()) {
                return repeat(new HashMap<>());
            } else {
                return filterInfinite(
                        m -> !all(p -> isEmpty(p.b), fromMap(m)),
                        PS.maps(xs, PS.lists(P.integersGeometric()))
                );
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
                nub(P.dependentPairsInfinite(nub(map(IterableUtils::unrepeat, PS.lists(P.integersGeometric()))), f))
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail2)) {
            try {
                toList(RP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>>> psFail3 = map(
                p -> new Pair<>(
                        p.a,
                        new FiniteDomainFunction<>(toMap(map(e -> new Pair<>(e.a, cycle(e.b)), fromMap(p.b))))
                ),
                nub(P.dependentPairsInfinite(PS.listsAtLeast(1, P.integersGeometric()), f))
        );
        for (Pair<List<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(LIMIT, psFail3)) {
            try {
                toList(RP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesShuffle() {
        initialize("shuffle(List<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            List<Integer> shuffled = toList(p.b);
            p.a.shuffle(shuffled);
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            assertEquals(p, sort(comparator, p.b), sort(comparator, shuffled));
        }
    }

    private static void propertiesPermutationsFinite() {
        initialize("permutationsFinite(List<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            List<Integer> sorted = sort(comparator, p.b);
            simpleTest(p.a, p.a.permutationsFinite(p.b), xs -> sort(comparator, xs).equals(sorted));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            List<Integer> xs = Collections.emptyList();
            aeqit(rp, TINY_LIMIT, rp.permutationsFinite(xs), repeat(xs));
        }

        Iterable<Pair<RandomProvider, Integer>> ps2 = P.pairs(
                P.randomProvidersDefault(),
                P.withNull(P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps2)) {
            List<Integer> xs = Collections.singletonList(p.b);
            aeqit(p, TINY_LIMIT, p.a.permutationsFinite(xs), repeat(xs));
        }
    }

    private static void propertiesStringPermutations() {
        initialize("stringPermutations(String)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            Comparator<Character> comparator = new ListBasedComparator<>(toList(p.b));
            String sorted = sort(comparator, p.b);
            simpleTest(p.a, p.a.stringPermutations(p.b), xs -> sort(comparator, xs).equals(sorted));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.stringPermutations(""), repeat(""));
        }

        for (Pair<RandomProvider, Character> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.characters()))) {
            String s = Character.toString(p.b);
            aeqit(p, TINY_LIMIT, p.a.stringPermutations(s), repeat(s));
        }
    }

    private static void propertiesPrefixPermutations() {
        initialize("prefixPermutations(Iterable<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            Comparator<Integer> comparator = new ListBasedComparator<>(p.b);
            List<Integer> sorted = sort(comparator, p.b);
            simpleTest(p.a, p.a.prefixPermutations(p.b), xs -> sort(comparator, toList(xs)).equals(sorted));
        }

        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            List<Integer> xs = Collections.emptyList();
            aeqit(rp, TINY_LIMIT, map(IterableUtils::toList, rp.prefixPermutations(xs)), repeat(xs));
        }

        Iterable<Pair<RandomProvider, Integer>> ps2 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps2)) {
            List<Integer> xs = Collections.singletonList(p.b);
            aeqit(p, TINY_LIMIT, map(IterableUtils::toList, p.a.prefixPermutations(xs)), repeat(xs));
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps3 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps3)) {
            simpleTest(p.a, p.a.prefixPermutations(p.b), xs -> true);
            for (Iterable<Integer> xs : take(TINY_LIMIT, p.a.prefixPermutations(p.b))) {
                simpleTestWithNulls(p.a, xs, x -> true);
            }
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail = P.pairs(
                filterInfinite(rp -> rp.getScale() < 1, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.prefixPermutations(p.b));
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStrings_int_String() {
        initialize("strings(int, String)");
        Iterable<Triple<RandomProvider, String, Integer>> ts = map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                P.pairs(
                        P.randomProvidersDefault(),
                        P.pairsLogarithmicOrder(
                                P.withScale(4).stringsAtLeast(1),
                                P.withScale(4).naturalIntegersGeometric()
                        )
                )
        );
        for (Triple<RandomProvider, String, Integer> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.strings(t.c, t.b), s -> s.length() == t.c && isSubsetOf(s, t.b));
        }

        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.strings(0, p.b), repeat(""));
        }

        Iterable<Triple<RandomProvider, Character, Integer>> ts2 = map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                P.pairs(
                        P.randomProvidersDefault(),
                        P.pairsLogarithmicOrder(P.characters(), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Triple<RandomProvider, Character, Integer> t : take(LIMIT, ts2)) {
            aeqit(t, TINY_LIMIT, t.a.strings(t.c, Character.toString(t.b)), repeat(replicate(t.c, t.b.charValue())));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = P.pairs(P.randomProvidersDefault(), P.positiveIntegers());
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.strings(p.b, "");
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, String, Integer>> tsFail = P.triples(
                P.randomProvidersDefault(),
                P.stringsAtLeast(1),
                P.negativeIntegers()
        );
        for (Triple<RandomProvider, String, Integer> t : take(LIMIT, tsFail)) {
            try {
                t.a.strings(t.c, t.b);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStrings_int() {
        initialize("strings(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.strings(p.b), s -> s.length() == p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = P.pairs(P.randomProvidersDefault(), P.negativeIntegers());
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.strings(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesLists() {
        initialize("lists(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.lists(p.b), is -> true);
        }

        Iterable<Pair<RandomProvider, List<Integer>>> ps2 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).listsAtLeast(1, P.withNull(P.withScale(4).integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            simpleTest(p.a, p.a.lists(p.a.uniformSample(p.b)), is -> isSubsetOf(is, p.b));
        }

        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            try {
                toList(p.a.lists(p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesStrings_String() {
        initialize("strings(String)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.strings(p.b), s -> isSubsetOf(s, p.b));
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.strings("");
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStrings() {
        initialize("strings()");
        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            simpleTest(rp, rp.strings(), s -> true);
        }
    }

    private static void propertiesListsAtLeast() {
        initialize("listsAtLeast(int, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.listsAtLeast(t.b, t.c), is -> is.size() >= t.b);
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> ts2 = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).listsAtLeast(1, P.withNull(P.withScale(4).integersGeometric()))
                )
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, ts2)) {
            simpleTest(t.a, t.a.listsAtLeast(t.b, t.a.uniformSample(t.c)), is -> isSubsetOf(is, t.c));
        }

        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, ts2)) {
            try {
                toList(t.a.listsAtLeast(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.listsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.listsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringsAtLeast_int_String() {
        initialize("stringsAtLeast(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.stringsAtLeast(t.b, t.c), s -> s.length() >= t.b && isSubsetOf(s, t.c));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.stringsAtLeast(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, String>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringsAtLeast_int() {
        initialize("stringsAtLeast(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringsAtLeast(p.b), s -> s.length() >= p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringsAtLeast(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filterInfinite(
                p -> p.a.getScale() <= p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringsAtLeast(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesDistinctStrings_int_String() {
        initialize("distinctStrings(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).naturalIntegersGeometric()
                                )
                        ),
                        p -> filterInfinite(
                                s -> !s.isEmpty() && nub(s).length() >= p.b,
                                P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                        )
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.distinctStrings(t.b, t.c),
                    s -> s.length() == t.b && isSubsetOf(s, t.c) && unique(s)
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.distinctStrings(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Triple<RandomProvider, Integer, String>> tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() > p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).negativeIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b < 0 ? 0 : p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
                try {
                    t.a.distinctStrings(t.b, t.c);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }

    private static void propertiesDistinctStrings_int() {
        initialize("distinctStrings(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.distinctStrings(p.b), s -> s.length() >= p.b && unique(s));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.distinctStrings(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesDistinctLists() {
        initialize("distinctLists(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).repeatingIterables(P.withNull(P.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.distinctLists(p.b), IterableUtils::unique);
        }

        Iterable<Pair<RandomProvider, List<Integer>>> ps2 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).listsAtLeast(1, P.withNull(P.withScale(4).integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            simpleTest(p.a, p.a.distinctLists(p.a.uniformSample(p.b)), is -> isSubsetOf(is, p.b) && unique(is));
        }

        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            try {
                toList(p.a.distinctLists(p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesDistinctStrings_String() {
        initialize("distinctStrings(String)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.distinctStrings(p.b), s -> isSubsetOf(s, p.b) && unique(s));
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(s -> s.getScale() > 0, P.withScale(4).randomProviders());
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.distinctStrings("");
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesDistinctStrings() {
        initialize("distinctStrings()");
        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            simpleTest(rp, rp.distinctStrings(), IterableUtils::unique);
        }
    }

    private static void propertiesDistinctListsAtLeast() {
        initialize("distinctListsAtLeast(int, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.distinctListsAtLeast(t.b, t.c), is -> is.size() >= t.b && unique(is));
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts2 = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).rangeUpGeometric(2)
                                )
                        ),
                        p -> P.withScale(p.a.getScale()).repeatingIterablesDistinctAtLeast(
                                p.b,
                                P.withNull(P.naturalIntegersGeometric())
                        )
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts2)) {
            simpleTest(t.a, t.a.distinctListsAtLeast(t.b, t.c), is -> isSubsetOf(is, t.c) && unique(is));
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).listsAtLeast(1, P.withNull(P.withScale(4).integersGeometric()))
                )
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail)) {
            try {
                toList(t.a.distinctListsAtLeast(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail2 = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.distinctListsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail2 = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.distinctListsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesDistinctStringsAtLeast_int_String() {
        initialize("distinctStringsAtLeast(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).naturalIntegersGeometric()
                                )
                        ),
                        p -> filterInfinite(
                                s -> !s.isEmpty() && nub(s).length() >= p.b,
                                P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                        )
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.distinctStringsAtLeast(t.b, t.c),
                    s -> s.length() >= t.b && isSubsetOf(s, t.c) && unique(s)
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.distinctStringsAtLeast(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Triple<RandomProvider, Integer, String>> tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() > p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).negativeIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b < 0 ? 0 : p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
                try {
                    t.a.distinctStringsAtLeast(t.b, t.c);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() <= p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).naturalIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(SMALL_LIMIT, tsFail)) {
                try {
                    t.a.distinctStringsAtLeast(t.b, t.c);
                    fail(t);
                } catch (IllegalStateException ignored) {}
            }
        }
    }

    private static void propertiesDistinctStringsAtLeast_int() {
        initialize("distinctStringsAtLeast(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.distinctStringsAtLeast(p.b), s -> s.length() >= p.b && unique(s));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.distinctStringsAtLeast(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filterInfinite(
                p -> p.a.getScale() <= p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.distinctStringsAtLeast(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringBags_int_String() {
        initialize("stringBags(int, String)");
        Iterable<Triple<RandomProvider, String, Integer>> ts = map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                P.pairs(
                        P.randomProvidersDefault(),
                        P.pairsLogarithmicOrder(
                                P.withScale(4).stringsAtLeast(1),
                                P.withScale(4).naturalIntegersGeometric()
                        )
                )
        );
        for (Triple<RandomProvider, String, Integer> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.stringBags(t.c, t.b),
                    s -> s.length() == t.c && isSubsetOf(s, t.b) && weaklyIncreasing(toList(s))
            );
        }

        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.stringBags(0, p.b), repeat(""));
        }

        Iterable<Triple<RandomProvider, Character, Integer>> ts2 = map(
                p -> new Triple<>(p.a, p.b.a, p.b.b),
                P.pairs(
                        P.randomProvidersDefault(),
                        P.pairsLogarithmicOrder(P.characters(), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Triple<RandomProvider, Character, Integer> t : take(LIMIT, ts2)) {
            aeqit(
                    t,
                    TINY_LIMIT,
                    t.a.stringBags(t.c, Character.toString(t.b)), repeat(replicate(t.c, t.b.charValue()))
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = P.pairs(P.randomProvidersDefault(), P.positiveIntegers());
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringBags(p.b, "");
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, String, Integer>> tsFail = P.triples(
                P.randomProvidersDefault(),
                P.stringsAtLeast(1),
                P.negativeIntegers()
        );
        for (Triple<RandomProvider, String, Integer> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringBags(t.c, t.b);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringBags_int() {
        initialize("stringBags(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringBags(p.b), s -> s.length() == p.b && weaklyIncreasing(toList(s)));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = P.pairs(P.randomProvidersDefault(), P.negativeIntegers());
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringBags(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesBags() {
        initialize("bags(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.bags(p.b), IterableUtils::weaklyIncreasing);
        }

        Iterable<Pair<RandomProvider, List<Integer>>> ps2 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).listsAtLeast(1, P.withScale(4).integersGeometric())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            simpleTest(p.a, p.a.bags(p.a.uniformSample(p.b)), is -> isSubsetOf(is, p.b) && weaklyIncreasing(is));
        }

        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            try {
                toList(p.a.bags(p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.bags(p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesStringBags_String() {
        initialize("stringBags(String)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringBags(p.b), s -> isSubsetOf(s, p.b) && weaklyIncreasing(toList(s)));
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.stringBags("");
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringBags() {
        initialize("stringBags()");
        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            simpleTest(rp, rp.stringBags(), s -> weaklyIncreasing(toList(s)));
        }
    }

    private static void propertiesBagsAtLeast() {
        initialize("bagsAtLeast(int, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.bagsAtLeast(t.b, t.c), is -> is.size() >= t.b && weaklyIncreasing(is));
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> ts2 = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).listsAtLeast(1, P.withScale(4).integersGeometric())
                )
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, ts2)) {
            simpleTest(t.a, t.a.bagsAtLeast(t.b, t.a.uniformSample(t.c)), is -> isSubsetOf(is, t.c));
        }

        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, ts2)) {
            try {
                toList(t.a.bagsAtLeast(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.bagsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.bagsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(P.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                toList(t.a.bagsAtLeast(t.b, t.c));
                fail(t);
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesStringBagsAtLeast_int_String() {
        initialize("stringBagsAtLeast(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.stringBagsAtLeast(t.b, t.c),
                    s -> s.length() >= t.b && isSubsetOf(s, t.c) && weaklyIncreasing(toList(s))
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.stringBagsAtLeast(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, String>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringBagsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringBagsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringBagsAtLeast_int() {
        initialize("stringBagsAtLeast(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(
                    p.a,
                    p.a.stringBagsAtLeast(p.b),
                    s -> s.length() >= p.b && weaklyIncreasing(toList(s))
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringBagsAtLeast(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filterInfinite(
                p -> p.a.getScale() <= p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringBagsAtLeast(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringSubsets_int_String() {
        initialize("stringSubsets(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).naturalIntegersGeometric()
                                )
                        ),
                        p -> filterInfinite(
                                s -> !s.isEmpty() && nub(s).length() >= p.b,
                                P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                        )
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.stringSubsets(t.b, t.c),
                    s -> s.length() == t.b && isSubsetOf(s, t.c) && increasing(toList(s))
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.stringSubsets(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Triple<RandomProvider, Integer, String>> tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() > p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).negativeIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b < 0 ? 0 : p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
                try {
                    t.a.stringSubsets(t.b, t.c);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }

    private static void propertiesStringSubsets_int() {
        initialize("distinctStrings(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringSubsets(p.b), s -> s.length() >= p.b && increasing(toList(s)));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringSubsets(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesSubsets() {
        initialize("subsets(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).repeatingIterables(P.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.subsets(p.b), IterableUtils::increasing);
        }

        Iterable<Pair<RandomProvider, List<Integer>>> ps2 = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).listsAtLeast(1, P.withScale(4).integersGeometric())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            simpleTest(p.a, p.a.subsets(p.a.uniformSample(p.b)), is -> isSubsetOf(is, p.b) && increasing(is));
        }

        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps2)) {
            try {
                toList(p.a.subsets(p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesStringSubsets_String() {
        initialize("stringSubsets(String)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringSubsets(p.b), s -> isSubsetOf(s, p.b) && increasing(toList(s)));
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(s -> s.getScale() > 0, P.withScale(4).randomProviders());
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.stringSubsets("");
                fail(rp);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringSubsets() {
        initialize("stringSubsets()");
        Iterable<RandomProvider> rpsFail = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            simpleTest(rp, rp.stringSubsets(), s -> increasing(toList(s)));
        }
    }

    private static void propertiesSubsetsAtLeast() {
        initialize("subsetsAtLeast(int, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.subsetsAtLeast(t.b, t.c), is -> is.size() >= t.b && increasing(is));
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts2 = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).rangeUpGeometric(2)
                                )
                        ),
                        p -> P.withScale(p.a.getScale())
                                .repeatingIterablesDistinctAtLeast(p.b, P.naturalIntegersGeometric())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts2)) {
            simpleTest(t.a, t.a.subsetsAtLeast(t.b, t.c), is -> isSubsetOf(is, t.c) && increasing(is));
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> tsFail = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).listsAtLeast(1, P.withScale(4).integersGeometric())
                )
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail)) {
            try {
                toList(t.a.subsetsAtLeast(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail2 = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.subsetsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        tsFail2 = filterInfinite(
                t -> t.a.getScale() <= t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.subsetsAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringSubsetsAtLeast_int_String() {
        initialize("stringSubsetsAtLeast(int, String)");
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                        P.withScale(4).naturalIntegersGeometric()
                                )
                        ),
                        p -> filterInfinite(
                                s -> !s.isEmpty() && nub(s).length() >= p.b,
                                P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                        )
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.stringSubsetsAtLeast(t.b, t.c),
                    s -> s.length() >= t.b && isSubsetOf(s, t.c) && increasing(toList(s))
            );
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.stringSubsetsAtLeast(p.b, ""));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        if (P instanceof ExhaustiveProvider) {
            Iterable<Triple<RandomProvider, Integer, String>> tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() > p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).negativeIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b < 0 ? 0 : p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(LIMIT, tsFail)) {
                try {
                    t.a.stringSubsetsAtLeast(t.b, t.c);
                    fail(t);
                } catch (IllegalArgumentException ignored) {}
            }

            tsFail = map(
                    p -> new Triple<>(p.a.a, p.a.b, p.b),
                    P.dependentPairsInfinite(
                            filterInfinite(
                                    p -> p.a.getScale() <= p.b,
                                    P.pairs(
                                            P.withScale(4).randomProvidersDefaultSecondaryScale(),
                                            P.withScale(4).naturalIntegersGeometric()
                                    )
                            ),
                            p -> filterInfinite(
                                    s -> !s.isEmpty() && nub(s).length() >= p.b,
                                    P.withScale(p.a.getScale()).stringsAtLeast(p.b)
                            )
                    )
            );
            for (Triple<RandomProvider, Integer, String> t : take(SMALL_LIMIT, tsFail)) {
                try {
                    t.a.stringSubsetsAtLeast(t.b, t.c);
                    fail(t);
                } catch (IllegalStateException ignored) {}
            }
        }
    }

    private static void propertiesStringSubsetsAtLeast_int() {
        initialize("stringSubsetsAtLeast(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringSubsetsAtLeast(p.b), s -> s.length() >= p.b && increasing(toList(s)));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).negativeIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringSubsetsAtLeast(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = filterInfinite(
                p -> p.a.getScale() <= p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.stringSubsetsAtLeast(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesCartesianProduct() {
        initialize("cartesianProduct(List<List<T>>)");
        Iterable<Pair<RandomProvider, List<List<Integer>>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())))
        );
        for (Pair<RandomProvider, List<List<Integer>>> p : take(LIMIT, ps)) {
            simpleTest(
                    p.a,
                    p.a.cartesianProduct(p.b),
                    xs -> xs.size() == p.b.size() && and(zipWith(List::contains, p.b, xs))
            );
        }

        Iterable<Pair<RandomProvider, List<List<Integer>>>> psFail = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsWithElement(
                        Collections.emptyList(),
                        P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric()))
                )
        );
        for (Pair<RandomProvider, List<List<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.cartesianProduct(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        psFail = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsWithElement(
                        null,
                        P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric()))
                )
        );
        for (Pair<RandomProvider, List<List<Integer>>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.cartesianProduct(p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesRepeatingIterables() {
        initialize("repeatingIterables(Iterable<T>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            simpleTest(
                    p.a,
                    p.a.repeatingIterables(p.b),
                    ys -> lengthAtLeast(TINY_LIMIT, ys)
            );
        }
    }

    private static void propertiesRepeatingIterablesDistinctAtLeast() {
        initialize("repeatingIterablesDistinctAtLeast(int, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
                p -> new Triple<>(p.a, p.b.b, p.b.a),
                filterInfinite(
                        p -> p.a.getScale() > p.b.b,
                        P.pairs(
                                P.randomProvidersDefaultSecondaryScale(),
                                P.pairsLogarithmicOrder(
                                        P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                                        P.withScale(4).rangeUpGeometric(2)
                                )
                        )
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(TINY_LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.repeatingIterablesDistinctAtLeast(t.b, t.c),
                    ys -> {
                        List<Integer> tys = toList(take(TINY_LIMIT, ys));
                        Set<Integer> distinctElements = new HashSet<>();
                        Iterator<Integer> ysi = ys.iterator();
                        while (distinctElements.size() < t.b) {
                            distinctElements.add(ysi.next());
                        }
                        return tys.size() == TINY_LIMIT;
                    }
            );
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail = map(
                p -> new Triple<>(p.a, p.b.b, p.b.a),
                filterInfinite(
                        p -> p.a.getScale() <= p.b.b,
                        P.pairs(
                                P.randomProvidersDefaultSecondaryScale(),
                                P.pairsLogarithmicOrder(
                                        P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                                        P.withScale(4).rangeUpGeometric(2)
                                )
                        )
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(TINY_LIMIT, tsFail)) {
            try {
                t.a.repeatingIterablesDistinctAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = map(
                p -> new Triple<>(p.a, p.b.b, p.b.a),
                filterInfinite(
                        p -> p.a.getScale() > p.b.b,
                        P.pairs(
                                filterInfinite(rp -> rp.getScale() < 0, P.randomProvidersDefaultSecondaryScale()),
                                P.pairsLogarithmicOrder(
                                        P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                                        P.integers()
                                )
                        )
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(TINY_LIMIT, tsFail)) {
            try {
                t.a.repeatingIterablesDistinctAtLeast(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesSublists() {
        initialize("sublists(List<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.sublists(p.b), xs -> isInfixOf(xs, p.b));
        }
    }

    private static void propertiesSubstrings() {
        initialize("substrings(String<T>)");
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.substrings(p.b), p.b::contains);
        }
    }

    private static void propertiesListsWithElement() {
        initialize("listsWithElement(T, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.listsWithElement(t.b, t.c), xs -> xs.contains(t.b));
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail = P.triples(
                filterInfinite(rp -> rp.getScale() < 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.listsWithElement(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> tsFail2 = P.triples(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.lists(P.withNull(P.withScale(4).integersGeometric()))
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                toList(t.a.listsWithElement(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesStringsWithChar_char_String() {
        initialize("stringsWithChar(char, String)");
        Iterable<Triple<RandomProvider, Character, String>> ts = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() >= 3,
                                P.withScale(4).randomProvidersDefaultSecondaryScale()
                        ),
                        P.characters(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Character, String> t : take(LIMIT, ts)) {
            String combined = cons(t.b, t.c);
            simpleTest(t.a, t.a.stringsWithChar(t.b, t.c), s -> elem(t.b, s) && isSubsetOf(s, combined));
        }

        Iterable<Triple<RandomProvider, Character, String>> tsFail = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() < 3,
                                P.withScale(4).randomProvidersDefaultSecondaryScale()
                        ),
                        P.characters(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Character, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringsWithChar(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        Iterable<Pair<RandomProvider, Character>> psFail = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(LIMIT, psFail)) {
            try {
                p.a.stringsWithChar(p.b, "");
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringsWithChar_char() {
        initialize("stringsWithChar(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            simpleTest(p.a, p.a.stringsWithChar(p.b), s -> elem(p.b, s));
        }
    }

    private static void propertiesSubsetsWithElement() {
        initialize("subsetsWithElement(T, Iterable<T>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.subsetsWithElement(t.b, t.c), xs -> xs.contains(t.b) && increasing(xs));
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail = P.triples(
                filterInfinite(rp -> rp.getScale() < 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.subsetsWithElement(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, List<Integer>>> tsFail2 = P.triples(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.integersGeometric(),
                P.lists(P.withScale(4).integersGeometric())
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                toList(t.a.subsetsWithElement(t.b, t.c));
                fail(t);
            } catch (NoSuchElementException ignored) {}
        }
    }

    private static void propertiesStringSubsetsWithChar_char_String() {
        initialize("stringSubsetsWithChar(char, String)");
        Iterable<Triple<RandomProvider, Character, String>> ts = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() >= 2,
                                P.withScale(4).randomProvidersDefaultSecondaryScale()
                        ),
                        P.characters(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Character, String> t : take(LIMIT, ts)) {
            String combined = cons(t.b, t.c);
            simpleTest(
                    t.a,
                    t.a.stringSubsetsWithChar(t.b, t.c),
                    s -> elem(t.b, s) && isSubsetOf(s, combined) && increasing(toList(s))
            );
        }

        Iterable<Triple<RandomProvider, Character, String>> tsFail = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() < 2,
                                P.withScale(4).randomProvidersDefaultSecondaryScale()
                        ),
                        P.characters(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Character, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringSubsetsWithChar(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        Iterable<Pair<RandomProvider, Character>> psFail = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(LIMIT, psFail)) {
            try {
                p.a.stringSubsetsWithChar(p.b, "");
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesStringSubsetsWithChar_char() {
        initialize("stringSubsetsWithChar(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            simpleTest(p.a, p.a.stringSubsetsWithChar(p.b), s -> elem(p.b, s) && increasing(toList(s)));
        }
    }

    private static void propertiesListsWithSublists() {
        initialize("listsWithSublists(Iterable<List<T>>, Iterable<T>)");
        Iterable<Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.lists(EP.withNull(EP.naturalIntegers()))),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.listsWithSublists(t.b, t.c), xs -> true);
        }

        Iterable<Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>>> tsFail = P.triples(
                filterInfinite(rp -> rp.getScale() <= 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.lists(EP.withNull(EP.naturalIntegers()))),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>> t : take(LIMIT, tsFail)) {
            try {
                t.a.listsWithSublists(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringsWithSubstrings_Iterable_String_String() {
        initialize("stringsWithSubstrings(Iterable<String>, String)");
        Iterable<Triple<RandomProvider, Iterable<String>, String>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Triple<RandomProvider, Iterable<String>, String> t : take(LIMIT, ts)) {
            simpleTest(t.a, t.a.stringsWithSubstrings(t.b, t.c), s -> true);
        }

        Iterable<Triple<RandomProvider, Iterable<String>, String>> tsFail = P.triples(
                filterInfinite(rp -> rp.getScale() <= 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Triple<RandomProvider, Iterable<String>, String> t : take(LIMIT, tsFail)) {
            try {
                t.a.stringsWithSubstrings(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesStringsWithSubstrings_Iterable_String() {
        initialize("stringsWithSubstrings(Iterable<String>)");
        Iterable<Pair<RandomProvider, Iterable<String>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings())
        );
        for (Pair<RandomProvider, Iterable<String>> p : take(LIMIT, ps)) {
            simpleTest(p.a, p.a.stringsWithSubstrings(p.b), s -> true);
        }

        Iterable<Pair<RandomProvider, Iterable<String>>> psFail = P.pairs(
                filterInfinite(rp -> rp.getScale() <= 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings())
        );
        for (Pair<RandomProvider, Iterable<String>> p : take(LIMIT, psFail)) {
            try {
                p.a.stringsWithSubstrings(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesMaps() {
        initialize("maps(List<Integer>, List<Integer>)");
        Comparator<Integer> withNullComparator = new WithNullComparator<>();
        Iterable<Triple<RandomProvider, List<Integer>, Iterable<Integer>>> ts = P.triples(
                P.randomProvidersDefault(),
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, List<Integer>, Iterable<Integer>> t : take(SMALL_LIMIT, ts)) {
            List<Integer> sortedKeys = sort(withNullComparator, t.b);
            simpleTest(t.a, t.a.maps(t.b, t.c), m -> sort(withNullComparator, m.keySet()).equals(sortedKeys));
        }
    }

    private static void propertiesRandomProvidersFixedScales() {
        initialize("randomProvidersFixedScales(int, int)");
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.integersGeometric(),
                P.integersGeometric()
        );
        for (Triple<RandomProvider, Integer, Integer> t : take(LIMIT, ts)) {
            simpleTest(
                    t.a,
                    t.a.randomProvidersFixedScales(t.b, t.c),
                    rp -> rp.getScale() == t.b && rp.getSecondaryScale() == t.c
            );
            for (RandomProvider rp : take(TINY_LIMIT, t.a.randomProvidersFixedScales(t.b, t.c))) {
                rp.validate();
            }
        }
    }

    private static void propertiesRandomProvidersDefault() {
        initialize("randomProvidersDefault()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            simpleTest(rp, rp.randomProvidersDefault(), s -> s.getScale() == 32 && s.getSecondaryScale() == 8);
            for (RandomProvider s : take(TINY_LIMIT, rp.randomProvidersDefault())) {
                s.validate();
            }
        }
    }

    private static void propertiesRandomProvidersDefaultSecondaryScale() {
        initialize("randomProvidersSecondaryScale()");
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            simpleTest(rp, rp.randomProvidersDefaultSecondaryScale(), s -> s.getSecondaryScale() == 8);
            for (RandomProvider s : take(TINY_LIMIT, rp.randomProvidersDefaultSecondaryScale())) {
                s.validate();
            }
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                rp -> rp.getScale() <= 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.randomProvidersDefaultSecondaryScale();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRandomProviders() {
        initialize("randomProviders()");
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            simpleTest(rp, rp.randomProviders(), s -> true);
            for (RandomProvider s : take(TINY_LIMIT, rp.randomProviders())) {
                s.validate();
            }
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                rp -> rp.getScale() <= 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.randomProviders();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, IterableProvider::randomProviders);
    }

    private static void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, IterableProvider::randomProviders);
    }

    private static void propertiesToString() {
        initialize("toString()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            String s = rp.toString();
            assertTrue(rp, isSubsetOf(s, RANDOM_PROVIDER_CHARS));
        }
    }
}
