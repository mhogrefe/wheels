package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
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
    private static final String RANDOM_PROVIDER_CHARS = " ,-0123456789@PR[]adeimnorv";
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
            assertEquals(p, rp.getScale(), p.b.intValue());
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
            assertEquals(p, rp.getSecondaryScale(), p.b.intValue());
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
                P.lists(P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.uniformSample(p.b);
            simpleTestWithNulls(p.a, is, p.b::contains);
            assertEquals(is, isEmpty(is), p.b.isEmpty());
        }
    }

    private static void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.strings()))) {
            Iterable<Character> cs = p.a.uniformSample(p.b);
            simpleTest(p.a, cs, c -> elem(c, cs));
            assertEquals(cs, isEmpty(cs), p.b.isEmpty());
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
        initialize("withElement(Integer, Iterable<Integer>)");
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integers()),
                P.repeatingIterables(P.withNull(P.integers()))
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
                P.withNull(P.integers()),
                P.lists(P.withNull(P.integers()))
        );
        for (Triple<RandomProvider, Integer, List<Integer>> t : take(LIMIT, tsFail1)) {
            try {
                toList(t.a.withElement(t.b, t.c));
                fail(t);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> tsFail2 = P.triples(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integers()),
                P.repeatingIterables(P.withNull(P.integers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(LIMIT, tsFail2)) {
            try {
                t.a.withElement(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesWithNull() {
        initialize("withNull(Iterable<Integer>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<Integer> withNull = toList(take(TINY_LIMIT, p.a.withNull(p.b)));
            testNoRemove(TINY_LIMIT, p.a.withNull(p.b));
            List<Integer> filteredResult = toList(filter(x -> x != null, withNull));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail1 = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.lists(P.integers())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail1)) {
            try {
                toList(p.a.withNull(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                p.a.withNull(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesOptionals() {
        initialize("optionals(Iterable<Integer>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<Optional<Integer>> os = toList(take(TINY_LIMIT, p.a.optionals(p.b)));
            testNoRemove(TINY_LIMIT, p.a.optionals(p.b));
            List<Integer> filteredResult = toList(optionalMap(Function.identity(), os));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.lists(P.integers())
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.optionals(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.integers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                p.a.optionals(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                map(IterableUtils::cycle, nub(map(IterableUtils::unrepeat, P.listsWithElement(null, P.integers()))))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, psFail2)) {
            try {
                toList(p.a.optionals(p.b));
                fail(p);
            } catch (NullPointerException ignored) {}
        }
    }

    private static void propertiesNullableOptionals() {
        initialize("nullableOptionals(Iterable<Integer>)");
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(LIMIT, ps)) {
            List<NullableOptional<Integer>> os = toList(take(TINY_LIMIT, p.a.nullableOptionals(p.b)));
            testNoRemove(TINY_LIMIT, p.a.nullableOptionals(p.b));
            List<Integer> filteredResult = toList(nullableOptionalMap(Function.identity(), os));
            assertEquals(p, filteredResult, toList(take(filteredResult.size(), p.b)));
        }

        Iterable<Pair<RandomProvider, List<Integer>>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.lists(P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, psFail)) {
            try {
                toList(p.a.nullableOptionals(p.b));
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }

        Iterable<Pair<RandomProvider, Iterable<Integer>>> psFail2 = P.pairs(
                filterInfinite(x -> x.getScale() < 2, P.randomProvidersDefaultSecondaryScale()),
                P.repeatingIterables(P.integers())
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
                toList(RP.dependentPairsInfinite(p.a, p.b));
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
                toList(RP.dependentPairsInfinite(p.a, p.b));
                fail(p);
            } catch (NoSuchElementException ignored) {}
        }

        //todo test uniqueness
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
