package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
            propertiesNextInt();
            propertiesIntegers();
            propertiesNextLong();
            propertiesLongs();
            propertiesNextBoolean();
            propertiesBooleans();
            propertiesNextUniformSample_Iterable();
            propertiesUniformSample_Iterable();
            propertiesNextUniformSample_String();
            propertiesUniformSample_String();
            propertiesNextOrdering();
            propertiesOrderings();
            propertiesNextRoundingMode();
            propertiesRoundingModes();
            propertiesNextPositiveByte();
            propertiesPositiveBytes();
            propertiesNextPositiveShort();
            propertiesPositiveShorts();
            propertiesNextPositiveInt();
            propertiesPositiveIntegers();
            propertiesNextPositiveLong();
            propertiesPositiveLongs();
            propertiesNextNegativeByte();
            propertiesNegativeBytes();
            propertiesNextNegativeShort();
            propertiesNegativeShorts();
            propertiesNextNegativeInt();
            propertiesNegativeIntegers();
            propertiesNextNegativeLong();
            propertiesNegativeLongs();
            propertiesNextNaturalByte();
            propertiesNaturalBytes();
            propertiesNextNaturalShort();
            propertiesNaturalShorts();
            propertiesNextNaturalInt();
            propertiesNaturalIntegers();
            propertiesNextNaturalLong();
            propertiesNaturalLongs();
            propertiesNextNonzeroByte();
            propertiesNonzeroBytes();
            propertiesNextNonzeroShort();
            propertiesNonzeroShorts();
            propertiesNextNonzeroInt();
            propertiesNonzeroIntegers();
            propertiesNextNonzeroLong();
            propertiesNonzeroLongs();
            propertiesNextByte();
            propertiesBytes();
            propertiesNextShort();
            propertiesShorts();
            propertiesNextAsciiChar();
            propertiesAsciiCharacters();
            propertiesNextChar();
            propertiesCharacters();
            propertiesNextFromRangeUp_byte();
            propertiesRangeUp_byte();
            propertiesNextFromRangeUp_short();
            propertiesRangeUp_short();
            propertiesNextFromRangeUp_int();
            propertiesRangeUp_int();
            propertiesNextFromRangeUp_long();
            propertiesRangeUp_long();
            propertiesNextFromRangeUp_char();
            propertiesRangeUp_char();
            propertiesNextFromRangeDown_byte();
            propertiesRangeDown_byte();
            propertiesNextFromRangeDown_short();
            propertiesRangeDown_short();
            propertiesNextFromRangeDown_int();
            propertiesRangeDown_int();
            propertiesNextFromRangeDown_long();
            propertiesRangeDown_long();
            propertiesNextFromRangeDown_char();
            propertiesRangeDown_char();
            propertiesNextFromRange_byte_byte();
            propertiesRange_byte_byte();
            propertiesNextFromRange_short_short();
            propertiesRange_short_short();
            propertiesNextFromRange_int_int();
            propertiesRange_int_int();
            propertiesNextFromRange_long_long();
            propertiesRange_long_long();
            propertiesNextFromRange_BigInteger_BigInteger();
            propertiesRange_BigInteger_BigInteger();
            propertiesNextFromRange_char_char();
            propertiesRange_char_char();
            propertiesNextPositiveIntGeometric();
            propertiesPositiveIntegersGeometric();
            propertiesNextNegativeIntGeometric();
            propertiesNegativeIntegersGeometric();
            propertiesNextNaturalIntGeometric();
            propertiesNaturalIntegersGeometric();
            propertiesNextNonzeroIntGeometric();
            propertiesNonzeroIntegersGeometric();
            propertiesNextIntGeometric();
            propertiesIntegersGeometric();
            propertiesNextIntGeometricFromRangeUp();
            propertiesRangeUpGeometric();
            propertiesNextIntGeometricFromRangeDown();
            propertiesRangeDownGeometric();
            propertiesNextPositiveBigInteger();
            propertiesPositiveBigIntegers();
            propertiesNextNegativeBigInteger();
            propertiesNegativeBigIntegers();
            propertiesNextNaturalBigInteger();
            propertiesNaturalBigIntegers();
            propertiesNextNonzeroBigInteger();
            propertiesNonzeroBigIntegers();
            propertiesNextBigInteger();
            propertiesBigIntegers();
            propertiesNextFromRangeUp_BigInteger();
            propertiesRangeUp_BigInteger();
            propertiesNextFromRangeDown_BigInteger();
            propertiesRangeDown_BigInteger();
            propertiesNextPositiveBinaryFraction();
            propertiesPositiveBinaryFractions();
            propertiesNextNegativeBinaryFraction();
            propertiesNegativeBinaryFractions();
            propertiesNextNonzeroBinaryFraction();
            propertiesNonzeroBinaryFractions();
            propertiesNextBinaryFraction();
            propertiesBinaryFractions();
            propertiesNextFromRangeUp_BinaryFraction();
            propertiesRangeUp_BinaryFraction();
            propertiesNextFromRangeDown_BinaryFraction();
            propertiesRangeDown_BinaryFraction();
            propertiesNextFromRange_BinaryFraction_BinaryFraction();
            propertiesRange_BinaryFraction_BinaryFraction();
            propertiesNextPositiveFloat();
            propertiesPositiveFloats();
            propertiesNextNegativeFloat();
            propertiesNegativeFloats();
            propertiesNextNonzeroFloat();
            propertiesNonzeroFloats();
            propertiesNextFloat();
            propertiesFloats();
            propertiesNextPositiveDouble();
            propertiesPositiveDoubles();
            propertiesNextNegativeDouble();
            propertiesNegativeDoubles();
            propertiesNextNonzeroDouble();
            propertiesNonzeroDoubles();
            propertiesNextDouble();
            propertiesDoubles();
            propertiesNextPositiveFloatUniform();
            propertiesPositiveFloatsUniform();
            propertiesNextNegativeFloatUniform();
            propertiesNegativeFloatsUniform();
            propertiesNextNonzeroFloatUniform();
            propertiesNonzeroFloatsUniform();
            propertiesNextFloatUniform();
            propertiesFloatsUniform();
            propertiesNextPositiveDoubleUniform();
            propertiesPositiveDoublesUniform();
            propertiesNextNegativeDoubleUniform();
            propertiesNegativeDoublesUniform();
            propertiesNextNonzeroDoubleUniform();
            propertiesNonzeroDoublesUniform();
            propertiesNextDoubleUniform();
            propertiesDoublesUniform();
            propertiesNextFromRangeUp_float();
            propertiesRangeUp_float();
            propertiesNextFromRangeDown_float();
            propertiesRangeDown_float();
            propertiesNextFromRange_float_float();
            propertiesRange_float_float();
            propertiesNextFromRangeUp_double();
            propertiesRangeUp_double();
            propertiesNextFromRangeDown_double();
            propertiesRangeDown_double();
            propertiesNextFromRange_double_double();
            propertiesRange_double_double();
            propertiesNextFromRangeUpUniform_float();
            propertiesRangeUpUniform_float();
            propertiesNextFromRangeDownUniform_float();
            propertiesRangeDownUniform_float();
            propertiesNextFromRangeUniform_float_float();
            propertiesRangeUniform_float_float();
            propertiesNextFromRangeUpUniform_double();
            propertiesRangeUpUniform_double();
            propertiesNextFromRangeDownUniform_double();
            propertiesRangeDownUniform_double();
            propertiesNextFromRangeUniform_double_double();
            propertiesRangeUniform_double_double();
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
            rp.nextInt();
            assertEquals(rp, rp, copy);
        }
    }

    private static void propertiesDeepCopy() {
        initialize("deepCopy()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider copy = rp.deepCopy();
            assertEquals(rp, rp, copy);
            rp.nextInt();
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
            rp.nextInt();
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

    private static <T> void supplierEquivalence(
            @NotNull RandomProvider rp,
            @NotNull Iterable<T> xs,
            @NotNull Supplier<T> s
    ) {
        rp.reset();
        List<T> iterableSample = toList(take(TINY_LIMIT, xs));
        rp.reset();
        List<T> supplierSample = toList(take(TINY_LIMIT, fromSupplier(s)));
        aeqit(rp, iterableSample, supplierSample);
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

    private static void propertiesNextInt() {
        initialize("nextInt()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextInt();
        }
    }

    private static void propertiesIntegers() {
        initialize("integers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.integers();
            simpleTest(rp, is, i -> true);
            supplierEquivalence(rp, is, rp::nextInt);
        }
    }

    private static void propertiesNextLong() {
        initialize("nextLong()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextLong();
        }
    }

    private static void propertiesLongs() {
        initialize("longs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.longs();
            simpleTest(rp, ls, l -> true);
            supplierEquivalence(rp, ls, rp::nextLong);
        }
    }

    private static void propertiesNextBoolean() {
        initialize("nextBoolean()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextBoolean();
        }
    }

    private static void propertiesBooleans() {
        initialize("booleans()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Boolean> bs = rp.booleans();
            simpleTest(rp, bs, b -> true);
            supplierEquivalence(rp, bs, rp::nextBoolean);
            for (boolean b : ExhaustiveProvider.INSTANCE.booleans()) {
                assertTrue(rp, elem(b, bs));
            }
        }
    }

    private static void propertiesNextUniformSample_Iterable() {
        initialize("nextUniformSample(Iterable<T>)");
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.listsAtLeast(1, P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            assertTrue(p, p.b.contains(p.a.nextUniformSample(p.b)));
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
            if (!p.b.isEmpty()) {
                supplierEquivalence(p.a, is, () -> p.a.nextUniformSample(p.b));
            }
            assertEquals(is, isEmpty(is), p.b.isEmpty());
        }
    }

    private static void propertiesNextUniformSample_String() {
        initialize("nextUniformSample(String)");
        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.stringsAtLeast(1)))) {
            assertTrue(p, elem(p.a.nextUniformSample(p.b), p.b));
        }
    }

    private static void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.strings()))) {
            Iterable<Character> cs = p.a.uniformSample(p.b);
            simpleTest(p.a, cs, c -> elem(c, cs));
            if (!p.b.isEmpty()) {
                supplierEquivalence(p.a, cs, () -> p.a.nextUniformSample(p.b));
            }
            assertEquals(cs, isEmpty(cs), p.b.isEmpty());
        }
    }

    private static void propertiesNextOrdering() {
        initialize("nextOrdering()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextOrdering();
        }
    }

    private static void propertiesOrderings() {
        initialize("orderings()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Ordering> os = rp.orderings();
            simpleTest(rp, os, o -> true);
            supplierEquivalence(rp, os, rp::nextOrdering);
            for (Ordering o : ExhaustiveProvider.INSTANCE.orderings()) {
                assertTrue(rp, elem(o, os));
            }
        }
    }

    private static void propertiesNextRoundingMode() {
        initialize("nextRoundingMode()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextRoundingMode();
        }
    }

    private static void propertiesRoundingModes() {
        initialize("roundingModes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<RoundingMode> rms = rp.roundingModes();
            simpleTest(rp, rms, rm -> true);
            supplierEquivalence(rp, rms, rp::nextRoundingMode);
            for (RoundingMode rm : ExhaustiveProvider.INSTANCE.roundingModes()) {
                assertTrue(rp, elem(rm, rms));
            }
        }
    }

    private static void propertiesNextPositiveByte() {
        initialize("nextPositiveByte()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            byte b = rp.nextPositiveByte();
            assertTrue(rp, b > 0);
        }
    }

    private static void propertiesPositiveBytes() {
        initialize("positiveBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.positiveBytes();
            simpleTest(rp, bs, b -> b > 0);
            supplierEquivalence(rp, bs, rp::nextPositiveByte);
        }
    }

    private static void propertiesNextPositiveShort() {
        initialize("nextPositiveShort()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            short s = rp.nextPositiveShort();
            assertTrue(rp, s > 0);
        }
    }

    private static void propertiesPositiveShorts() {
        initialize("positiveShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.positiveShorts();
            simpleTest(rp, ss, s -> s > 0);
            supplierEquivalence(rp, ss, rp::nextPositiveShort);
        }
    }

    private static void propertiesNextPositiveInt() {
        initialize("nextPositiveInt()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            int i = rp.nextPositiveInt();
            assertTrue(rp, i > 0);
        }
    }

    private static void propertiesPositiveIntegers() {
        initialize("positiveIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.positiveIntegers();
            simpleTest(rp, is, i -> i > 0);
            supplierEquivalence(rp, is, rp::nextPositiveInt);
        }
    }

    private static void propertiesNextPositiveLong() {
        initialize("nextPositiveLong()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            long l = rp.nextPositiveLong();
            assertTrue(rp, l > 0);
        }
    }

    private static void propertiesPositiveLongs() {
        initialize("positiveLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.positiveLongs();
            simpleTest(rp, ls, l -> l > 0);
            supplierEquivalence(rp, ls, rp::nextPositiveLong);
        }
    }

    private static void propertiesNextNegativeByte() {
        initialize("nextNegativeByte()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            byte b = rp.nextNegativeByte();
            assertTrue(rp, b < 0);
        }
    }

    private static void propertiesNegativeBytes() {
        initialize("negativeBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.negativeBytes();
            simpleTest(rp, bs, b -> b < 0);
            supplierEquivalence(rp, bs, rp::nextNegativeByte);
        }
    }

    private static void propertiesNextNegativeShort() {
        initialize("nextNegativeShort()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            short s = rp.nextNegativeShort();
            assertTrue(rp, s < 0);
        }
    }

    private static void propertiesNegativeShorts() {
        initialize("negativeShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.negativeShorts();
            simpleTest(rp, ss, s -> s < 0);
            supplierEquivalence(rp, ss, rp::nextNegativeShort);
        }
    }

    private static void propertiesNextNegativeInt() {
        initialize("nextNegativeInt()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            int i = rp.nextNegativeInt();
            assertTrue(rp, i < 0);
        }
    }

    private static void propertiesNegativeIntegers() {
        initialize("negativeIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.negativeIntegers();
            simpleTest(rp, is, i -> i < 0);
            supplierEquivalence(rp, is, rp::nextNegativeInt);
        }
    }

    private static void propertiesNextNegativeLong() {
        initialize("nextNegativeLong()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            long l = rp.nextNegativeLong();
            assertTrue(rp, l < 0);
        }
    }

    private static void propertiesNegativeLongs() {
        initialize("negativeLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.negativeLongs();
            simpleTest(rp, ls, l -> l < 0);
            supplierEquivalence(rp, ls, rp::nextNegativeLong);
        }
    }

    private static void propertiesNextNaturalByte() {
        initialize("nextNaturalByte()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            byte b = rp.nextNaturalByte();
            assertTrue(rp, b >= 0);
        }
    }

    private static void propertiesNaturalBytes() {
        initialize("naturalBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.naturalBytes();
            simpleTest(rp, bs, b -> b >= 0);
            supplierEquivalence(rp, bs, rp::nextNaturalByte);
        }
    }

    private static void propertiesNextNaturalShort() {
        initialize("nextNaturalShort()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            short s = rp.nextNaturalShort();
            assertTrue(rp, s >= 0);
        }
    }

    private static void propertiesNaturalShorts() {
        initialize("naturalShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.naturalShorts();
            simpleTest(rp, ss, s -> s >= 0);
            supplierEquivalence(rp, ss, rp::nextNaturalShort);
        }
    }

    private static void propertiesNextNaturalInt() {
        initialize("nextNaturalInt()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            int i = rp.nextNaturalInt();
            assertTrue(rp, i >= 0);
        }
    }

    private static void propertiesNaturalIntegers() {
        initialize("naturalIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.naturalIntegers();
            simpleTest(rp, is, i -> i >= 0);
            supplierEquivalence(rp, is, rp::nextNaturalInt);
        }
    }

    private static void propertiesNextNaturalLong() {
        initialize("nextNaturalLong()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            long l = rp.nextNaturalLong();
            assertTrue(rp, l >= 0);
        }
    }

    private static void propertiesNaturalLongs() {
        initialize("naturalLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.naturalLongs();
            simpleTest(rp, ls, l -> l >= 0);
            supplierEquivalence(rp, ls, rp::nextNaturalLong);
        }
    }

    private static void propertiesNextNonzeroByte() {
        initialize("nextNonzeroByte()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            byte b = rp.nextNonzeroByte();
            assertTrue(rp, b != 0);
        }
    }

    private static void propertiesNonzeroBytes() {
        initialize("nonzeroBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.nonzeroBytes();
            simpleTest(rp, bs, b -> b != 0);
            supplierEquivalence(rp, bs, rp::nextNonzeroByte);
        }
    }

    private static void propertiesNextNonzeroShort() {
        initialize("nextNonzeroShort()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            short s = rp.nextNonzeroShort();
            assertTrue(rp, s != 0);
        }
    }

    private static void propertiesNonzeroShorts() {
        initialize("nonzeroShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.nonzeroShorts();
            simpleTest(rp, ss, s -> s != 0);
            supplierEquivalence(rp, ss, rp::nextNonzeroShort);
        }
    }

    private static void propertiesNextNonzeroInt() {
        initialize("nextNonzeroInt()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            int i = rp.nextNonzeroInt();
            assertTrue(rp, i != 0);
        }
    }

    private static void propertiesNonzeroIntegers() {
        initialize("nonzeroIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.nonzeroIntegers();
            simpleTest(rp, is, i -> i != 0);
            supplierEquivalence(rp, is, rp::nextNonzeroInt);
        }
    }

    private static void propertiesNextNonzeroLong() {
        initialize("nextNonzeroLong()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            long l = rp.nextNonzeroLong();
            assertTrue(rp, l != 0);
        }
    }

    private static void propertiesNonzeroLongs() {
        initialize("nonzeroLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.nonzeroLongs();
            simpleTest(rp, ls, l -> l != 0);
            supplierEquivalence(rp, ls, rp::nextNonzeroLong);
        }
    }

    private static void propertiesNextByte() {
        initialize("nextByte()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextByte();
        }
    }

    private static void propertiesBytes() {
        initialize("bytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.bytes();
            simpleTest(rp, bs, b -> true);
            supplierEquivalence(rp, bs, rp::nextByte);
        }
    }

    private static void propertiesNextShort() {
        initialize("nextShort()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextShort();
        }
    }

    private static void propertiesShorts() {
        initialize("shorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.shorts();
            simpleTest(rp, ss, s -> true);
            supplierEquivalence(rp, ss, rp::nextShort);
        }
    }

    private static void propertiesNextAsciiChar() {
        initialize("nextAsciiChar()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            char c = rp.nextAsciiChar();
            assertTrue(rp, c < 128);
        }
    }

    private static void propertiesAsciiCharacters() {
        initialize("asciiCharacters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.asciiCharacters();
            simpleTest(rp, cs, c -> c < 128);
            supplierEquivalence(rp, cs, rp::nextAsciiChar);
        }
    }

    private static void propertiesNextChar() {
        initialize("nextChar()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextChar();
        }
    }

    private static void propertiesCharacters() {
        initialize("characters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.characters();
            simpleTest(rp, cs, c -> true);
            supplierEquivalence(rp, cs, rp::nextChar);
        }
    }

    private static void propertiesNextFromRangeUp_byte() {
        initialize("nextFromRangeUp(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            byte b = p.a.nextFromRangeUp(p.b);
            assertTrue(p, b >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Byte.MAX_VALUE), Byte.MAX_VALUE);
        }
    }

    private static void propertiesRangeUp_byte() {
        initialize("rangeUp(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeUp(p.b);
            simpleTest(p.a, bs, b -> b >= p.b);
            supplierEquivalence(p.a, bs, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Byte.MAX_VALUE), repeat(Byte.MAX_VALUE));
        }
    }

    private static void propertiesNextFromRangeUp_short() {
        initialize("nextFromRangeUp(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            short s = p.a.nextFromRangeUp(p.b);
            assertTrue(p, s >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Short.MAX_VALUE), Short.MAX_VALUE);
        }
    }

    private static void propertiesRangeUp_short() {
        initialize("rangeUp(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeUp(p.b);
            simpleTest(p.a, ss, s -> s >= p.b);
            supplierEquivalence(p.a, ss, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Short.MAX_VALUE), repeat(Short.MAX_VALUE));
        }
    }

    private static void propertiesNextFromRangeUp_int() {
        initialize("nextFromRangeUp(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            int i = p.a.nextFromRangeUp(p.b);
            assertTrue(p, i >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Integer.MAX_VALUE), Integer.MAX_VALUE);
        }
    }

    private static void propertiesRangeUp_int() {
        initialize("rangeUp(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeUp(p.b);
            simpleTest(p.a, is, i -> i >= p.b);
            supplierEquivalence(p.a, is, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Integer.MAX_VALUE), repeat(Integer.MAX_VALUE));
        }
    }

    private static void propertiesNextFromRangeUp_long() {
        initialize("nextFromRangeUp(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            long l = p.a.nextFromRangeUp(p.b);
            assertTrue(p, l >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Long.MAX_VALUE), Long.MAX_VALUE);
        }
    }

    private static void propertiesRangeUp_long() {
        initialize("rangeUp(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeUp(p.b);
            simpleTest(p.a, ls, l -> l >= p.b);
            supplierEquivalence(p.a, ls, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Long.MAX_VALUE), repeat(Long.MAX_VALUE));
        }
    }

    private static void propertiesNextFromRangeUp_char() {
        initialize("nextFromRangeUp(char)");
        for (Pair<RandomProvider, Character> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.characters()))) {
            char c = p.a.nextFromRangeUp(p.b);
            assertTrue(p, c >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Character.MAX_VALUE), Character.MAX_VALUE);
        }
    }

    private static void propertiesRangeUp_char() {
        initialize("rangeUp(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeUp(p.b);
            simpleTest(p.a, cs, c -> c >= p.b);
            supplierEquivalence(p.a, cs, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Character.MAX_VALUE), repeat(Character.MAX_VALUE));
        }
    }

    private static void propertiesNextFromRangeDown_byte() {
        initialize("nextFromRangeDown(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            byte b = p.a.nextFromRangeDown(p.b);
            assertTrue(p, b <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Byte.MIN_VALUE), Byte.MIN_VALUE);
        }
    }

    private static void propertiesRangeDown_byte() {
        initialize("rangeDown(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeDown(p.b);
            simpleTest(p.a, bs, b -> b <= p.b);
            supplierEquivalence(p.a, bs, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Byte.MIN_VALUE), repeat(Byte.MIN_VALUE));
        }
    }

    private static void propertiesNextFromRangeDown_short() {
        initialize("nextFromRangeDown(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            short s = p.a.nextFromRangeDown(p.b);
            assertTrue(p, s <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Short.MIN_VALUE), Short.MIN_VALUE);
        }
    }

    private static void propertiesRangeDown_short() {
        initialize("rangeDown(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeDown(p.b);
            simpleTest(p.a, ss, s -> s <= p.b);
            supplierEquivalence(p.a, ss, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Short.MIN_VALUE), repeat(Short.MIN_VALUE));
        }
    }

    private static void propertiesNextFromRangeDown_int() {
        initialize("nextFromRangeDown(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            int i = p.a.nextFromRangeDown(p.b);
            assertTrue(p, i <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Integer.MIN_VALUE), Integer.MIN_VALUE);
        }
    }

    private static void propertiesRangeDown_int() {
        initialize("rangeDown(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeDown(p.b);
            simpleTest(p.a, is, i -> i <= p.b);
            supplierEquivalence(p.a, is, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Integer.MIN_VALUE), repeat(Integer.MIN_VALUE));
        }
    }

    private static void propertiesNextFromRangeDown_long() {
        initialize("nextFromRangeDown(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            long l = p.a.nextFromRangeDown(p.b);
            assertTrue(p, l <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Long.MIN_VALUE), Long.MIN_VALUE);
        }
    }

    private static void propertiesRangeDown_long() {
        initialize("rangeDown(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeDown(p.b);
            simpleTest(p.a, ls, l -> l <= p.b);
            supplierEquivalence(p.a, ls, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Long.MIN_VALUE), repeat(Long.MIN_VALUE));
        }
    }

    private static void propertiesNextFromRangeDown_char() {
        initialize("nextFromRangeDown(char)");
        for (Pair<RandomProvider, Character> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.characters()))) {
            char c = p.a.nextFromRangeDown(p.b);
            assertTrue(p, c <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown('\0'), '\0');
        }
    }

    private static void propertiesRangeDown_char() {
        initialize("rangeDown(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeDown(p.b);
            simpleTest(p.a, cs, b -> b <= p.b);
            supplierEquivalence(p.a, cs, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Character.MIN_VALUE), repeat(Character.MIN_VALUE));
        }
    }

    private static void propertiesNextFromRange_byte_byte() {
        initialize("nextFromRange(byte, byte)");
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes())
        );
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            byte b = p.a.nextFromRange(p.b, p.c);
            assertTrue(p, b >= p.b);
            assertTrue(p, b <= p.c);
        }

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b.byteValue());
        }
    }

    private static void propertiesRange_byte_byte() {
        initialize("range(byte, byte)");
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> t : take(LIMIT, ts)) {
            Iterable<Byte> bs = t.a.range(t.b, t.c);
            simpleTest(t.a, bs, b -> b >= t.b && b <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(bs));
            if (t.b <= t.c) {
                supplierEquivalence(t.a, bs, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }
    
    private static void propertiesNextFromRange_short_short() {
        initialize("nextFromRange(short, short)");
        Iterable<Triple<RandomProvider, Short, Short>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.shorts(), P.shorts())
        );
        for (Triple<RandomProvider, Short, Short> t : take(LIMIT, ts)) {
            short s = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, s >= t.b);
            assertTrue(t, s <= t.c);
        }

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b.shortValue());
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, ss, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }
    
    private static void propertiesNextFromRange_int_int() {
        initialize("nextFromRange(int, int)");
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.integers(), P.integers())
        );
        for (Triple<RandomProvider, Integer, Integer> t : take(LIMIT, ts)) {
            int i = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, i >= t.b);
            assertTrue(t, i <= t.c);
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b.intValue());
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, is, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }
    
    private static void propertiesNextFromRange_long_long() {
        initialize("nextFromRange(long, long)");
        Iterable<Triple<RandomProvider, Long, Long>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.longs(), P.longs())
        );
        for (Triple<RandomProvider, Long, Long> t : take(LIMIT, ts)) {
            long l = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, l >= t.b);
            assertTrue(t, l <= t.c);
        }

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b.longValue());
        }
    }

    private static void propertiesRange_long_long() {
        initialize("range(long, long)");
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> t : take(LIMIT, ts)) {
            Iterable<Long> ls = t.a.range(t.b, t.c);
            simpleTest(t.a, ls, l -> l >= t.b && l <= t.c);
            assertEquals(t, t.b > t.c, isEmpty(ls));
            if (t.b <= t.c) {
                supplierEquivalence(t.a, ls, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }
    
    private static void propertiesNextFromRange_BigInteger_BigInteger() {
        initialize("nextFromRange(BigInteger, BigInteger)");
        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = filterInfinite(
                t -> le(t.b, t.c),
                P.triples(P.randomProvidersDefault(), P.bigIntegers(), P.bigIntegers())
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            BigInteger i = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, ge(i, t.b));
            assertTrue(t, le(i, t.c));
        }

        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bigIntegers()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b);
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
            if (le(t.b, t.c)) {
                supplierEquivalence(t.a, is, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        Iterable<Pair<RandomProvider, BigInteger>> ps = P.pairs(P.randomProvidersDefault(), P.bigIntegers());
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }
    
    private static void propertiesNextFromRange_char_char() {
        initialize("nextFromRange(char, char)");
        Iterable<Triple<RandomProvider, Character, Character>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.characters(), P.characters())
        );
        for (Triple<RandomProvider, Character, Character> t : take(LIMIT, ts)) {
            char c = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, c >= t.b);
            assertTrue(t, c <= t.c);
        }

        for (Pair<RandomProvider, Character> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.characters()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b.charValue());
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, cs, () -> t.a.nextFromRange(t.b, t.c));
            }
        }

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            aeqit(p, TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesNextPositiveIntGeometric() {
        initialize("nextPositiveIntGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            int i = rp.nextPositiveIntGeometric();
            assertTrue(rp, i > 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextPositiveIntGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
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
            supplierEquivalence(rp, is, rp::nextPositiveIntGeometric);
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

    private static void propertiesNextNegativeIntGeometric() {
        initialize("nextNegativeIntGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            int i = rp.nextNegativeIntGeometric();
            assertTrue(rp, i < 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNegativeIntGeometric();
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
            supplierEquivalence(rp, is, rp::nextNegativeIntGeometric);
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

    private static void propertiesNextNaturalIntGeometric() {
        initialize("nextNaturalIntGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            int i = rp.nextNaturalIntGeometric();
            assertTrue(rp, i >= 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNaturalIntGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).nextNaturalIntGeometric();
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
            supplierEquivalence(rp, is, rp::nextNaturalIntGeometric);
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

    private static void propertiesNextNonzeroIntGeometric() {
        initialize("nextNonzeroIntGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            int i = rp.nextNonzeroIntGeometric();
            assertTrue(rp, i != 0);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNonzeroIntGeometric();
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
            supplierEquivalence(rp, is, rp::nextNonzeroIntGeometric);
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

    private static void propertiesNextIntGeometric() {
        initialize("nextPositiveIntGeometric()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            rp.nextIntGeometric();
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextIntGeometric();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).nextIntGeometric();
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
            supplierEquivalence(rp, is, rp::nextIntGeometric);
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

    private static void propertiesNextIntGeometricFromRangeUp() {
        initialize("nextIntGeometricFromRangeUp(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b >= 1 || p.a.getScale() < Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            int i = p.a.nextIntGeometricFromRangeUp(p.b);
            assertTrue(p, i >= p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() <= p.b || p.b < 1 && p.a.getScale() >= Integer.MAX_VALUE + p.b,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.nextIntGeometricFromRangeUp(p.b);
                fail(p);
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
            supplierEquivalence(p.a, is, () -> p.a.nextIntGeometricFromRangeUp(p.b));
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

    private static void propertiesNextIntGeometricFromRangeDown() {
        initialize("nextIntGeometricFromRangeDown(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            int i = p.a.nextIntGeometricFromRangeDown(p.b);
            assertTrue(p, i <= p.b);
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filterInfinite(
                p -> p.a.getScale() >= p.b || p.b > -1 && p.a.getScale() <= p.b - Integer.MAX_VALUE,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.nextIntGeometricFromRangeDown(p.b);
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
            supplierEquivalence(p.a, is, () -> p.a.nextIntGeometricFromRangeDown(p.b));
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

    private static void propertiesNextPositiveBigInteger() {
        initialize("nextPositiveBigInteger()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BigInteger i = rp.nextPositiveBigInteger();
            assertEquals(rp, i.signum(), 1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextPositiveBigInteger();
                fail(rp);
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
            supplierEquivalence(rp, is, rp::nextPositiveBigInteger);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.positiveBigIntegers();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesNextNegativeBigInteger() {
        initialize("nextNegativeBigInteger()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BigInteger i = rp.nextNegativeBigInteger();
            assertEquals(rp, i.signum(), -1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNegativeBigInteger();
                fail(rp);
            } catch (IllegalStateException ignored) {}
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
            supplierEquivalence(rp, is, rp::nextNegativeBigInteger);
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

    private static void propertiesNextNaturalBigInteger() {
        initialize("nextNaturalBigInteger()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BigInteger i = rp.nextNaturalBigInteger();
            assertNotEquals(rp, i.signum(), -1);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNaturalBigInteger();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).nextNaturalBigInteger();
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
            supplierEquivalence(rp, is, rp::nextNaturalBigInteger);
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

    private static void propertiesNextNonzeroBigInteger() {
        initialize("nextNonzeroBigInteger()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BigInteger i = rp.nextNonzeroBigInteger();
            assertNotEquals(rp, i, BigInteger.ZERO);
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextNonzeroBigInteger();
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
            supplierEquivalence(rp, is, rp::nextNonzeroBigInteger);
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

    private static void propertiesNextBigInteger() {
        initialize("nextBigInteger()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            rp.nextBigInteger();
        }

        Iterable<RandomProvider> rpsFail = filterInfinite(
                x -> x.getScale() < 1,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rpsFail)) {
            try {
                rp.nextBigInteger();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            try {
                rp.withScale(Integer.MAX_VALUE).nextBigInteger();
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
            supplierEquivalence(rp, is, rp::nextBigInteger);
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

    private static void propertiesNextFromRangeUp_BigInteger() {
        initialize("nextFromRangeUp(BigInteger)");
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            BigInteger i = p.a.nextFromRangeUp(p.b);
            assertTrue(p, ge(i, p.b));
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
                p.a.nextFromRangeUp(p.b);
                fail(p);
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
            supplierEquivalence(p.a, is, () -> p.a.nextFromRangeUp(p.b));
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

    private static void propertiesNextFromRangeDown_BigInteger() {
        initialize("nextFromRangeDown(BigInteger)");
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            BigInteger i = p.a.nextFromRangeDown(p.b);
            assertTrue(p, le(i, p.b));
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
                p.a.nextFromRangeDown(p.b);
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
            supplierEquivalence(p.a, is, () -> p.a.nextFromRangeDown(p.b));
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

    private static void propertiesNextPositiveBinaryFraction() {
        initialize("nextPositiveBinaryFraction()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BinaryFraction bf = rp.nextPositiveBinaryFraction();
            bf.validate();
            assertEquals(rp, bf.signum(), 1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nextPositiveBinaryFraction();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nextPositiveBinaryFraction();
                fail(rp);
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
            supplierEquivalence(rp, bfs, rp::nextPositiveBinaryFraction);
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

    private static void propertiesNextNegativeBinaryFraction() {
        initialize("nextNegativeBinaryFraction()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BinaryFraction bf = rp.nextNegativeBinaryFraction();
            bf.validate();
            assertEquals(rp, bf.signum(), -1);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nextNegativeBinaryFraction();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nextNegativeBinaryFraction();
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
            supplierEquivalence(rp, bfs, rp::nextNegativeBinaryFraction);
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

    private static void propertiesNextNonzeroBinaryFraction() {
        initialize("nextNonzeroBinaryFraction()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BinaryFraction bf = rp.nextNonzeroBinaryFraction();
            bf.validate();
            assertTrue(rp, bf != BinaryFraction.ZERO);
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 2, P.randomProviders()))) {
            try {
                rp.nextNonzeroBinaryFraction();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nextNonzeroBinaryFraction();
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
            supplierEquivalence(rp, bfs, rp::nextNonzeroBinaryFraction);
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

    private static void propertiesNextBinaryFraction() {
        initialize("nextBinaryFraction()");
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            BinaryFraction bf = rp.nextBinaryFraction();
            bf.validate();
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getScale() < 1, P.randomProviders()))) {
            try {
                rp.nextBinaryFraction();
                fail(rp);
            } catch (IllegalStateException ignored) {}
        }

        for (RandomProvider rp : take(LIMIT, filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()))) {
            try {
                rp.nextBinaryFraction();
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
            supplierEquivalence(rp, bfs, rp::nextBinaryFraction);
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

    private static void propertiesNextFromRangeUp_BinaryFraction() {
        initialize("nextFromRangeUp(BinaryFraction)");
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            BinaryFraction bf = p.a.nextFromRangeUp(p.b);
            assertTrue(p, ge(bf, p.b));
        }

        Iterable<Pair<RandomProvider, BinaryFraction>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 1,
                P.randomProviders()), P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.nextFromRangeUp(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.binaryFractions());
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.nextFromRangeUp(p.b);
                fail(p);
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
            simpleTest(p.a, bfs, i -> ge(i, p.b));
            supplierEquivalence(p.a, bfs, () -> p.a.nextFromRangeUp(p.b));
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

    private static void propertiesNextFromRangeDown_BinaryFraction() {
        initialize("nextFromRangeDown(BinaryFraction)");
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            BinaryFraction bf = p.a.nextFromRangeDown(p.b);
            assertTrue(p, le(bf, p.b));
        }

        Iterable<Pair<RandomProvider, BinaryFraction>> psFail = P.pairs(
                filterInfinite(x -> x.getScale() < 1,
                P.randomProviders()), P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.nextFromRangeDown(p.b);
                fail(p);
            } catch (IllegalStateException ignored) {}
        }

        psFail = P.pairs(filterInfinite(x -> x.getSecondaryScale() < 1, P.randomProviders()), P.binaryFractions());
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, psFail)) {
            try {
                p.a.nextFromRangeDown(p.b);
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
            simpleTest(p.a, bfs, i -> le(i, p.b));
            supplierEquivalence(p.a, bfs, () -> p.a.nextFromRangeDown(p.b));
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

    private static void propertiesNextFromRange_BinaryFraction_BinaryFraction() {
        initialize("nextFromRange(BinaryFraction, BinaryFraction)");
        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = filterInfinite(
                t -> le(t.b, t.c),
                P.triples(
                        filterInfinite(
                                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                                P.randomProvidersDefaultSecondaryScale()
                        ),
                        P.binaryFractions(),
                        P.binaryFractions()
                )
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, ts)) {
            BinaryFraction bf = t.a.nextFromRange(t.b, t.c);
            bf.validate();
            assertTrue(t, ge(bf, t.b));
            assertTrue(t, le(bf, t.c));
        }

        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bigIntegers()))) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b);
        }

        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> tsFail = filterInfinite(
                t -> le(t.b, t.c),
                P.triples(
                        filterInfinite(x -> x.getScale() < 1, P.randomProvidersDefaultSecondaryScale()),
                        P.binaryFractions(),
                        P.binaryFractions()
                )
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, tsFail)) {
            try {
                t.a.nextFromRange(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = filterInfinite(
                t -> le(t.b, t.c),
                P.triples(P.randomProvidersDefault(), P.binaryFractions(), P.binaryFractions())
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, tsFail)) {
            try {
                t.a.withScale(Integer.MAX_VALUE).nextFromRange(t.b, t.c);
                fail(t);
            } catch (IllegalStateException ignored) {}
        }

        tsFail = filterInfinite(
                t -> gt(t.b, t.c),
                P.triples(
                        filterInfinite(
                                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                                P.randomProvidersDefaultSecondaryScale()
                        ),
                        P.binaryFractions(),
                        P.binaryFractions()
                )
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(LIMIT, tsFail)) {
            try {
                t.a.nextFromRange(t.b, t.c);
                fail(t);
            } catch (IllegalArgumentException ignored) {}
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
            if (le(t.b, t.c)) {
                supplierEquivalence(t.a, bfs, () -> t.a.nextFromRange(t.b, t.c));
            }
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

    private static void propertiesNextPositiveFloat() {
        initialize("nextPositiveFloat()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextPositiveFloat();
            assertTrue(rp, f > 0);
        }
    }

    private static void propertiesPositiveFloats() {
        initialize("positiveFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.positiveFloats();
            simpleTest(rp, fs, f -> f > 0);
            supplierEquivalence(rp, fs, rp::nextPositiveFloat);
        }
    }

    private static void propertiesNextNegativeFloat() {
        initialize("nextNegativeFloat()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextNegativeFloat();
            assertTrue(rp, f < 0);
        }
    }

    private static void propertiesNegativeFloats() {
        initialize("negativeFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.negativeFloats();
            simpleTest(rp, fs, f -> f < 0);
            supplierEquivalence(rp, fs, rp::nextNegativeFloat);
        }
    }

    private static void propertiesNextNonzeroFloat() {
        initialize("nextNonzeroFloat()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextNonzeroFloat();
            assertTrue(rp, f != 0);
        }
    }

    private static void propertiesNonzeroFloats() {
        initialize("nonzeroFloats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.nonzeroFloats();
            simpleTest(rp, fs, f -> f != 0);
            supplierEquivalence(rp, fs, rp::nextNonzeroFloat);
        }
    }

    private static void propertiesNextFloat() {
        initialize("nextFloat()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextFloat();
        }
    }

    private static void propertiesFloats() {
        initialize("floats()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.floats();
            simpleTest(rp, fs, f -> true);
            supplierEquivalence(rp, fs, rp::nextFloat);
        }
    }

    private static void propertiesNextPositiveDouble() {
        initialize("nextPositiveDouble()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextPositiveDouble();
            assertTrue(rp, d > 0);
        }
    }

    private static void propertiesPositiveDoubles() {
        initialize("positiveDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.positiveDoubles();
            simpleTest(rp, ds, d -> d > 0);
            supplierEquivalence(rp, ds, rp::nextPositiveDouble);
        }
    }

    private static void propertiesNextNegativeDouble() {
        initialize("nextNegativeDouble()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextNegativeDouble();
            assertTrue(rp, d < 0);
        }
    }

    private static void propertiesNegativeDoubles() {
        initialize("negativeDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.negativeDoubles();
            simpleTest(rp, ds, d -> d < 0);
            supplierEquivalence(rp, ds, rp::nextNegativeDouble);
        }
    }

    private static void propertiesNextNonzeroDouble() {
        initialize("nextNonzeroDouble()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextNonzeroDouble();
            assertTrue(rp, d != 0);
        }
    }

    private static void propertiesNonzeroDoubles() {
        initialize("nonzeroDoubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.nonzeroDoubles();
            simpleTest(rp, ds, d -> d != 0);
            supplierEquivalence(rp, ds, rp::nextNonzeroDouble);
        }
    }

    private static void propertiesNextDouble() {
        initialize("nextDouble()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            rp.nextDouble();
        }
    }

    private static void propertiesDoubles() {
        initialize("doubles()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.doubles();
            simpleTest(rp, ds, d -> true);
            supplierEquivalence(rp, ds, rp::nextDouble);
        }
    }

    private static void propertiesNextPositiveFloatUniform() {
        initialize("nextPositiveFloatUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextPositiveFloatUniform();
            assertTrue(rp, f > 0 && Float.isFinite(f));
        }
    }

    private static void propertiesPositiveFloatsUniform() {
        initialize("positiveFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.positiveFloatsUniform();
            simpleTest(rp, fs, f -> f > 0 && Float.isFinite(f));
            supplierEquivalence(rp, fs, rp::nextPositiveFloatUniform);
        }
    }

    private static void propertiesNextNegativeFloatUniform() {
        initialize("nextNegativeFloatUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextNegativeFloatUniform();
            assertTrue(rp, f < 0 && Float.isFinite(f));
        }
    }

    private static void propertiesNegativeFloatsUniform() {
        initialize("negativeFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.negativeFloatsUniform();
            simpleTest(rp, fs, f -> f < 0 && Float.isFinite(f));
            supplierEquivalence(rp, fs, rp::nextNegativeFloatUniform);
        }
    }

    private static void propertiesNextNonzeroFloatUniform() {
        initialize("nextNonzeroFloatUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextNonzeroFloatUniform();
            assertTrue(rp, f != 0 && Float.isFinite(f));
        }
    }

    private static void propertiesNonzeroFloatsUniform() {
        initialize("nonzeroFloatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.nonzeroFloatsUniform();
            simpleTest(rp, fs, f -> f != 0 && Float.isFinite(f));
            supplierEquivalence(rp, fs, rp::nextNonzeroFloatUniform);
        }
    }

    private static void propertiesNextFloatUniform() {
        initialize("nextFloatUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            float f = rp.nextFloatUniform();
            assertTrue(rp, Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
        }
    }

    private static void propertiesFloatsUniform() {
        initialize("floatsUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Float> fs = rp.floatsUniform();
            simpleTest(rp, fs, f -> Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
            supplierEquivalence(rp, fs, rp::nextFloatUniform);
        }
    }

    private static void propertiesNextPositiveDoubleUniform() {
        initialize("nextPositiveDoubleUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextPositiveDoubleUniform();
            assertTrue(rp, d > 0 && Double.isFinite(d));
        }
    }

    private static void propertiesPositiveDoublesUniform() {
        initialize("positiveDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.positiveDoublesUniform();
            simpleTest(rp, ds, d -> d > 0 && Double.isFinite(d));
            supplierEquivalence(rp, ds, rp::nextPositiveDoubleUniform);
        }
    }

    private static void propertiesNextNegativeDoubleUniform() {
        initialize("nextNegativeDoubleUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextNegativeDoubleUniform();
            assertTrue(rp, d < 0 && Double.isFinite(d));
        }
    }

    private static void propertiesNegativeDoublesUniform() {
        initialize("negativeDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.negativeDoublesUniform();
            simpleTest(rp, ds, d -> d < 0 && Double.isFinite(d));
            supplierEquivalence(rp, ds, rp::nextNegativeDoubleUniform);
        }
    }

    private static void propertiesNextNonzeroDoubleUniform() {
        initialize("nextNonzeroDoubleUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextNonzeroDoubleUniform();
            assertTrue(rp, d != 0 && Double.isFinite(d));
        }
    }

    private static void propertiesNonzeroDoublesUniform() {
        initialize("nonzeroDoublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.nonzeroDoublesUniform();
            simpleTest(rp, ds, d -> d != 0 && Double.isFinite(d));
            supplierEquivalence(rp, ds, rp::nextNonzeroDoubleUniform);
        }
    }

    private static void propertiesNextDoubleUniform() {
        initialize("nextDoubleUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            double d = rp.nextDoubleUniform();
            assertTrue(rp, Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
        }
    }

    private static void propertiesDoublesUniform() {
        initialize("doublesUniform()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Double> ds = rp.doublesUniform();
            simpleTest(rp, ds, d -> Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
            supplierEquivalence(rp, ds, rp::nextDoubleUniform);
        }
    }

    private static void propertiesNextFromRangeUp_float() {
        initialize("nextFromRangeUp(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            float f = p.a.nextFromRangeUp(p.b);
            assertTrue(p, f >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Float.POSITIVE_INFINITY), Float.POSITIVE_INFINITY);
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
            supplierEquivalence(p.a, fs, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Float.POSITIVE_INFINITY), repeat(Float.POSITIVE_INFINITY));
        }
    }

    private static void propertiesNextFromRangeDown_float() {
        initialize("nextFromRangeDown(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            float f = p.a.nextFromRangeDown(p.b);
            assertTrue(p, f <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Float.NEGATIVE_INFINITY), Float.NEGATIVE_INFINITY);
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
            supplierEquivalence(p.a, fs, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Float.NEGATIVE_INFINITY), repeat(Float.NEGATIVE_INFINITY));
        }
    }

    private static void propertiesNextFromRange_float_float() {
        initialize("nextFromRange(float, float)");
        Iterable<Triple<RandomProvider, Float, Float>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(f -> !Float.isNaN(f), P.floats()),
                        filter(f -> !Float.isNaN(f), P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            float f = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, f >= t.b && f <= t.c);
        }

        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f) && f != 0.0f, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b);
            try {
                p.a.nextFromRange(Float.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRange(p.b, Float.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, fs, () -> t.a.nextFromRange(t.b, t.c));
            }
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

    private static void propertiesNextFromRangeUp_double() {
        initialize("nextFromRangeUp(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            double d = p.a.nextFromRangeUp(p.b);
            assertTrue(p, d >= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeUp(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
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
            supplierEquivalence(p.a, ds, () -> p.a.nextFromRangeUp(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeUp(Double.POSITIVE_INFINITY), repeat(Double.POSITIVE_INFINITY));
        }
    }

    private static void propertiesNextFromRangeDown_double() {
        initialize("nextFromRangeDown(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Double.isNaN(f), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            double d = p.a.nextFromRangeDown(p.b);
            assertTrue(p, d <= p.b);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            assertEquals(rp, rp.nextFromRangeDown(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
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
            supplierEquivalence(p.a, ds, () -> p.a.nextFromRangeDown(p.b));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp, TINY_LIMIT, rp.rangeDown(Double.NEGATIVE_INFINITY), repeat(Double.NEGATIVE_INFINITY));
        }
    }

    private static void propertiesNextFromRange_double_double() {
        initialize("nextFromRange(double, double)");
        Iterable<Triple<RandomProvider, Double, Double>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(d -> !Double.isNaN(d), P.doubles()),
                        filter(d -> !Double.isNaN(d), P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            double d = t.a.nextFromRange(t.b, t.c);
            assertTrue(t, d >= t.b && d <= t.c);
        }

        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d) && d != 0.0, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.nextFromRange(p.b, p.b), p.b);
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, ds, () -> t.a.nextFromRange(t.b, t.c));
            }
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

    private static void propertiesNextFromRangeUpUniform_float() {
        initialize("nextFromRangeUpUniform(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            float f = p.a.nextFromRangeUpUniform(p.b);
            assertTrue(p, f >= p.b && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
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
            supplierEquivalence(p.a, fs, () -> p.a.nextFromRangeUpUniform(p.b));
        }
    }

    private static void propertiesNextFromRangeDownUniform_float() {
        initialize("nextFromRangeDownUniform(float)");
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            float f = p.a.nextFromRangeDownUniform(p.b);
            assertTrue(p, f <= p.b && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
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
            supplierEquivalence(p.a, fs, () -> p.a.nextFromRangeDownUniform(p.b));
        }
    }

    private static void propertiesNextFromRangeUniform_float_float() {
        initialize("nextFromRangeUniform(float, float)");
        Iterable<Triple<RandomProvider, Float, Float>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Float::isFinite, P.floats()),
                        filter(Float::isFinite, P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            float f = t.a.nextFromRangeUniform(t.b, t.c);
            assertTrue(t, f >= t.b && f <= t.c && Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f));
        }

        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> Float.isFinite(f) && !FloatingPointUtils.isNegativeZero(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.nextFromRangeUniform(p.b, p.b), p.b);
        }

        ps = P.pairs(P.randomProvidersDefault(), filter(Float::isFinite, P.floats()));
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            try {
                p.a.nextFromRangeUniform(Float.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(Float.NEGATIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(Float.POSITIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Float.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Float.NEGATIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Float.POSITIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, fs, () -> t.a.nextFromRangeUniform(t.b, t.c));
            }
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

    private static void propertiesNextFromRangeUpUniform_double() {
        initialize("nextFromRangeUpUniform(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            double d = p.a.nextFromRangeUpUniform(p.b);
            assertTrue(p, d >= p.b && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
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
            supplierEquivalence(p.a, ds, () -> p.a.nextFromRangeUpUniform(p.b));
        }
    }

    private static void propertiesNextFromRangeDownUniform_double() {
        initialize("nextFromRangeDownUniform(double)");
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            double d = p.a.nextFromRangeDownUniform(p.b);
            assertTrue(p, d <= p.b && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
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
            supplierEquivalence(p.a, ds, () -> p.a.nextFromRangeDownUniform(p.b));
        }
    }

    private static void propertiesNextFromRangeUniform_double_double() {
        initialize("nextFromRangeUniform(double, double)");
        Iterable<Triple<RandomProvider, Double, Double>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Double::isFinite, P.doubles()),
                        filter(Double::isFinite, P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            double d = t.a.nextFromRangeUniform(t.b, t.c);
            assertTrue(t, d >= t.b && d <= t.c && Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d));
        }

        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> Double.isFinite(d) && !FloatingPointUtils.isNegativeZero(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            assertEquals(p, p.a.nextFromRangeUniform(p.b, p.b), p.b);
        }

        ps = P.pairs(P.randomProvidersDefault(), filter(Double::isFinite, P.doubles()));
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            try {
                p.a.nextFromRangeUniform(Double.NaN, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(Double.NEGATIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(Double.POSITIVE_INFINITY, p.b);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Double.NaN);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Double.NEGATIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
            try {
                p.a.nextFromRangeUniform(p.b, Double.POSITIVE_INFINITY);
                fail(p);
            } catch (ArithmeticException ignored) {}
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
            if (t.b <= t.c) {
                supplierEquivalence(t.a, ds, () -> t.a.nextFromRangeUniform(t.b, t.c));
            }
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
