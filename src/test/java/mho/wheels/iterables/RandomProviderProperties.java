package mho.wheels.iterables;

import mho.wheels.ordering.Ordering;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
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
            propertiesBooleans();
            propertiesIntegers();
            propertiesLongs();
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
            propertiesEquals();
            propertiesHashCode();
            propertiesToString();
        }
        System.out.println("Done");
    }

    private static void propertiesConstructor() {
        initialize("RandomProvider()");
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
            assertEquals(is.toString(), rp.getScale(), 32);
            assertEquals(is.toString(), rp.getSecondaryScale(), 8);
        }

        for (List<Integer> is : take(LIMIT, filter(js -> js.size() != IsaacPRNG.SIZE, P.lists(P.integers())))) {
            try {
                new RandomProvider(is);
                fail(is.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesGetScale() {
        initialize("getScale()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int scale = rp.getScale();
            assertEquals(rp.toString(), rp.withScale(scale), rp);
        }
    }

    private static void propertiesGetSecondaryScale() {
        initialize("getSecondaryScale()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int secondaryScale = rp.getSecondaryScale();
            assertEquals(rp.toString(), rp.withSecondaryScale(secondaryScale), rp);
        }
    }

    private static void propertiesGetSeed() {
        initialize("getSeed()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            List<Integer> seed = rp.getSeed();
            assertEquals(rp.toString(), seed.size(), IsaacPRNG.SIZE);
            assertEquals(
                    rp.toString(),
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
            assertEquals(p.toString(), rp.getScale(), p.b.intValue());
            assertEquals(p.toString(), rp.getSecondaryScale(), p.a.getSecondaryScale());
            assertEquals(p.toString(), rp.getSeed(), p.a.getSeed());
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
            assertEquals(p.toString(), rp.getScale(), p.a.getScale());
            assertEquals(p.toString(), rp.getSecondaryScale(), p.b.intValue());
            assertEquals(p.toString(), rp.getSeed(), p.a.getSeed());
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

    private static void propertiesBooleans() {
        initialize("booleans()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Boolean> bs = rp.booleans();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, bs)));
            testNoRemove(TINY_LIMIT, bs);
            for (boolean b : ExhaustiveProvider.INSTANCE.booleans()) {
                assertTrue(rp.toString(), elem(b, bs));
            }
        }
    }

    private static void propertiesIntegers() {
        initialize("integers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.integers();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, is)));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesLongs() {
        initialize("longs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.longs();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, ls)));
            testNoRemove(TINY_LIMIT, ls);
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
            testNoRemove(TINY_LIMIT, is);
            assertEquals(is.toString(), isEmpty(is), p.b.isEmpty());
            assertTrue(is.toString(), all(p.b::contains, take(TINY_LIMIT, is)));
        }
    }

    private static void propertiesUniformSample_String() {
        initialize("uniformSample(String)");
        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.strings()))) {
            Iterable<Character> cs = p.a.uniformSample(p.b);
            testNoRemove(TINY_LIMIT, cs);
            assertEquals(cs.toString(), isEmpty(cs), p.b.isEmpty());
            assertTrue(cs.toString(), all(c -> elem(c, p.b), take(TINY_LIMIT, cs)));
        }
    }

    private static void propertiesOrderings() {
        initialize("orderings()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Ordering> os = rp.orderings();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, os)));
            testNoRemove(TINY_LIMIT, os);
            for (Ordering o : ExhaustiveProvider.INSTANCE.orderings()) {
                assertTrue(rp.toString(), elem(o, os));
            }
        }
    }

    private static void propertiesRoundingModes() {
        initialize("roundingModes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<RoundingMode> rms = rp.roundingModes();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, rms)));
            testNoRemove(TINY_LIMIT, rms);
            for (RoundingMode rm : ExhaustiveProvider.INSTANCE.roundingModes()) {
                assertTrue(rp.toString(), elem(rm, rms));
            }
        }
    }

    private static void propertiesPositiveBytes() {
        initialize("positiveBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.positiveBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b > 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesPositiveShorts() {
        initialize("positiveShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.positiveShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s > 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesPositiveIntegers() {
        initialize("positiveIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.positiveIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i > 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesPositiveLongs() {
        initialize("positiveLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.positiveLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l > 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNegativeBytes() {
        initialize("negativeBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.negativeBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b < 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNegativeShorts() {
        initialize("negativeShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.negativeShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s < 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNegativeIntegers() {
        initialize("negativeIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.negativeIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i < 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNegativeLongs() {
        initialize("negativeLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.negativeLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l < 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNaturalBytes() {
        initialize("naturalBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.naturalBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b >= 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNaturalShorts() {
        initialize("naturalShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.naturalShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s >= 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNaturalIntegers() {
        initialize("naturalIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.naturalIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i >= 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNaturalLongs() {
        initialize("naturalLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.naturalLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l >= 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNonzeroBytes() {
        initialize("nonzeroBytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.nonzeroBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b != 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNonzeroShorts() {
        initialize("nonzeroShorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.nonzeroShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s != 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNonzeroIntegers() {
        initialize("nonzeroIntegers()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.nonzeroIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i != 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNonzeroLongs() {
        initialize("nonzeroLongs()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.nonzeroLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l != 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesBytes() {
        initialize("bytes()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.bytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesShorts() {
        initialize("shorts()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.shorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesAsciiCharacters() {
        initialize("asciiCharacters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.asciiCharacters();
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(rp.toString(), all(c -> c != null, tcs));
            assertTrue(rp.toString(), all(c -> c < 128, tcs));
            testNoRemove(TINY_LIMIT, cs);
        }
    }

    private static void propertiesCharacters() {
        initialize("characters()");
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.characters();
            assertTrue(rp.toString(), all(c -> c != null, take(TINY_LIMIT, cs)));
            testNoRemove(TINY_LIMIT, cs);
        }
    }

    private static void propertiesRangeUp_byte() {
        initialize("testing rangeUp(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeUp(p.b);
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b >= p.b, tbs));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUp(Byte.MAX_VALUE), repeat(Byte.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_short() {
        initialize("rangeUp(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeUp(p.b);
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(s -> s != null, tss));
            testNoRemove(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(s -> s >= p.b, tss));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUp(Short.MAX_VALUE), repeat(Short.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_int() {
        initialize("rangeUp(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeUp(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i >= p.b, tis));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUp(Integer.MAX_VALUE), repeat(Integer.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_long() {
        initialize("rangeUp(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeUp(p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(l -> l != null, tls));
            testNoRemove(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(l -> l >= p.b, tls));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUp(Long.MAX_VALUE), repeat(Long.MAX_VALUE));
        }
    }

    private static void propertiesRangeUp_char() {
        initialize("rangeUp(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeUp(p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b != null, tcs));
            testNoRemove(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b >= p.b, tcs));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUp(Character.MAX_VALUE), repeat(Character.MAX_VALUE));
        }
    }

    private static void propertiesRangeDown_byte() {
        initialize("rangeDown(byte)");
        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            Iterable<Byte> bs = p.a.rangeDown(p.b);
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b <= p.b, tbs));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeDown(Byte.MIN_VALUE), repeat(Byte.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_short() {
        initialize("rangeDown(short)");
        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            Iterable<Short> ss = p.a.rangeDown(p.b);
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(s -> s != null, tss));
            testNoRemove(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(s -> s <= p.b, tss));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeDown(Short.MIN_VALUE), repeat(Short.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_int() {
        initialize("rangeDown(int)");
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            Iterable<Integer> is = p.a.rangeDown(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i <= p.b, tis));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeDown(Integer.MIN_VALUE), repeat(Integer.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_long() {
        initialize("rangeDown(long)");
        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            Iterable<Long> ls = p.a.rangeDown(p.b);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(l -> l != null, tls));
            testNoRemove(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(l -> l <= p.b, tls));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeDown(Long.MIN_VALUE), repeat(Long.MIN_VALUE));
        }
    }

    private static void propertiesRangeDown_char() {
        initialize("rangeDown(char)");
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            Iterable<Character> cs = p.a.rangeDown(p.b);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b != null, tcs));
            testNoRemove(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b <= p.b, tcs));
        }

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeDown(Character.MIN_VALUE), repeat(Character.MIN_VALUE));
        }
    }

    private static void propertiesRange_byte_byte() {
        initialize("range(byte, byte)");
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            Iterable<Byte> bs = p.a.range(p.b, p.c);
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tbs));
            assertEquals(p.toString(), p.b > p.c, isEmpty(bs));
        }

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_short_short() {
        initialize("range(short, short)");
        Iterable<Triple<RandomProvider, Short, Short>> ts = P.triples(
                P.randomProvidersDefault(),
                P.shorts(),
                P.shorts()
        );
        for (Triple<RandomProvider, Short, Short> p : take(LIMIT, ts)) {
            Iterable<Short> ss = p.a.range(p.b, p.c);
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(b -> b != null, tss));
            testNoRemove(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tss));
            assertEquals(p.toString(), p.b > p.c, isEmpty(ss));
        }

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_int_int() {
        initialize("range(int, int)");
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.integers(),
                P.integers()
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(LIMIT, ts)) {
            Iterable<Integer> is = p.a.range(p.b, p.c);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tis));
            assertEquals(p.toString(), p.b > p.c, isEmpty(is));
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_long_long() {
        initialize("range(long, long)");
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            Iterable<Long> ls = p.a.range(p.b, p.c);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(b -> b != null, tls));
            testNoRemove(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tls));
            assertEquals(p.toString(), p.b > p.c, isEmpty(ls));
        }

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        initialize("range(BigInteger, BigInteger)");

        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = P.triples(
                P.randomProvidersDefault(),
                P.bigIntegers(),
                P.bigIntegers()
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(LIMIT, ts)) {
            Iterable<BigInteger> is = p.a.range(p.b, p.c);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> ge(b, p.b) && le(b, p.c), tis));
            assertEquals(p.toString(), gt(p.b, p.c), isEmpty(is));
        }

        Iterable<Pair<RandomProvider, BigInteger>> ps = P.pairs(P.randomProvidersDefault(), P.bigIntegers());
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_char_char() {
        initialize("range(char, char)");
        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.characters(),
                P.characters()
        );
        for (Triple<RandomProvider, Character, Character> p : take(LIMIT, ts)) {
            Iterable<Character> cs = p.a.range(p.b, p.c);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b != null, tcs));
            testNoRemove(TINY_LIMIT, p.a.range(p.b, p.c));
            assertTrue(p.toString(), all(b -> ge(b, p.b) && le(b, p.c), tcs));
            assertEquals(p.toString(), gt(p.b, p.c), isEmpty(cs));
        }

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesPositiveIntegersGeometric() {
        initialize("positiveIntegersGeometric()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.positiveIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i > 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNegativeIntegersGeometric() {
        initialize("negativeIntegersGeometric()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.negativeIntegersGeometric();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i < 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNaturalIntegersGeometric() {
        initialize("naturalIntegersGeometric()");
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.naturalIntegersGeometric();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i >= 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNonzeroIntegersGeometric() {
        initialize("nonzeroIntegersGeometric()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.nonzeroIntegersGeometric();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i != 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesIntegersGeometric() {
        initialize("integersGeometric()");
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.integersGeometric();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesRangeUpGeometric() {
        initialize("rangeUpGeometric(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() > p.b && (p.b >= 1 || p.a.getScale() < Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeUpGeometric(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i >= p.b, tis));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filter(
                p -> p.a.getScale() <= p.b || p.b < 1 && p.a.getScale() >= Integer.MAX_VALUE + p.b,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUpGeometric(p.b);
                fail(p.toString());
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDownGeometric() {
        initialize("rangeDownGeometric(int)");
        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeDownGeometric(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i <= p.b, tis));
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filter(
                p -> p.a.getScale() >= p.b || p.b > -1 && p.a.getScale() <= p.b - Integer.MAX_VALUE,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDownGeometric(p.b);
                fail(p.toString());
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesPositiveBigIntegers() {
        initialize("positiveBigIntegers()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.positiveBigIntegers();
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i.signum() == 1, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNegativeBigIntegers() {
        initialize("negativeBigIntegers()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.negativeBigIntegers();
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i.signum() == -1, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNaturalBigIntegers() {
        initialize("naturalBigIntegers()");
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.naturalBigIntegers();
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i.signum() != -1, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNonzeroBigIntegers() {
        initialize("nonzeroBigIntegers()");
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.nonzeroBigIntegers();
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> !i.equals(BigInteger.ZERO), tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesBigIntegers() {
        initialize("bigIntegers()");
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<BigInteger> is = rp.bigIntegers();
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesEquals() {
        initialize("equals(Object)");
        IterableProvider P2;
        IterableProvider P3;
        if (P instanceof ExhaustiveProvider) {
            P2 = ExhaustiveProvider.INSTANCE;
            P3 = ExhaustiveProvider.INSTANCE;
        } else {
            P2 = ((RandomProvider) P).deepCopy();
            P3 = ((RandomProvider) P).deepCopy();
        }

        Iterable<Triple<RandomProvider, RandomProvider, RandomProvider>> ts = zip3(
                P.randomProviders(),
                P2.randomProviders(),
                P3.randomProviders()
        );
        for (Triple<RandomProvider, RandomProvider, RandomProvider> t : take(LIMIT, ts)) {
            //noinspection ConstantConditions,ObjectEqualsNull
            assertFalse(t.toString(), t.a.equals(null));
            assertEquals(t.toString(), t.a, t.b);
            assertEquals(t.toString(), t.b, t.c);
        }

        P.reset();
        P2.reset();
        P3.reset();
        Iterable<Pair<RandomProvider, RandomProvider>> ps = ExhaustiveProvider.INSTANCE.pairs(
                P.randomProviders(),
                P2.randomProviders()
        );
        for (Pair<RandomProvider, RandomProvider> p : take(LIMIT, ps)) {
            symmetric(RandomProvider::equals, p);
        }

        P.reset();
        P2.reset();
        ts = ExhaustiveProvider.INSTANCE.triples(P.randomProviders(), P2.randomProviders(), P3.randomProviders());
        for (Triple<RandomProvider, RandomProvider, RandomProvider> t : take(LIMIT, ts)) {
            transitive(RandomProvider::equals, t);
        }
    }

    private static void propertiesHashCode() {
        initialize("hashCode()");
        IterableProvider P2;
        if (P instanceof ExhaustiveProvider) {
            P2 = ExhaustiveProvider.INSTANCE;
        } else {
            P2 = ((RandomProvider) P).deepCopy();
        }
        propertiesHashCodeHelper(P.randomProviders(), P2.randomProviders(), LIMIT);
    }

    private static void propertiesToString() {
        initialize("toString()");
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            String s = rp.toString();
            assertTrue(rp.toString(), isSubsetOf(s, RANDOM_PROVIDER_CHARS));
        }
    }
}
