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

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(RandomProvider.EXAMPLE, 1000, "randomly"));
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
            propertiesAlt();
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
            propertiesEquals();
            propertiesHashCode();
            propertiesToString();
        }
        System.out.println("Done");
    }

    private static void propertiesConstructor() {
        System.out.println("\t\ttesting RandomProvider() properties...");

        for (Void v : take(LIMIT, repeat((Void) null))) {
            RandomProvider rp = new RandomProvider();
            rp.validate();
        }
    }

    private static void propertiesConstructor_List_Integer() {
        System.out.println("\t\ttesting RandomProvider(List<Integer>) properties...");

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
        System.out.println("\t\ttesting getScale() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int scale = rp.getScale();
            assertEquals(rp.toString(), rp.withScale(scale), rp);
        }
    }

    private static void propertiesGetSecondaryScale() {
        System.out.println("\t\ttesting getSecondaryScale() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int secondaryScale = rp.getSecondaryScale();
            assertEquals(rp.toString(), rp.withSecondaryScale(secondaryScale), rp);
        }
    }

    private static void propertiesGetSeed() {
        System.out.println("\t\ttesting getSeed() properties...");

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

    private static void propertiesAlt() {
        System.out.println("\t\ttesting alt() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider alt = rp.alt();
            alt.validate();
            assertEquals(rp.toString(), alt.getScale(), rp.getScale());
            assertEquals(rp.toString(), alt.getSecondaryScale(), rp.getSecondaryScale());
        }
    }

    private static void propertiesWithScale() {
        System.out.println("\t\ttesting withScale(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.alt().naturalIntegers()))) {
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
        System.out.println("\t\ttesting withSecondaryScale(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.alt().naturalIntegers()))) {
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
        System.out.println("\t\ttesting booleans() properties...");

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
        System.out.println("\t\ttesting integers() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.integers();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, is)));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesLongs() {
        System.out.println("\t\ttesting longs() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.longs();
            assertTrue(rp.toString(), all(i -> i != null, take(TINY_LIMIT, ls)));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesUniformSample_Iterable() {
        System.out.println("\t\ttesting uniformSample(Iterable<T>) properties...");

        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.alt().lists(P.alt().withNull(P.alt().integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.uniformSample(p.b);
            testNoRemove(TINY_LIMIT, is);
            assertEquals(is.toString(), isEmpty(is), p.b.isEmpty());
            assertTrue(is.toString(), all(p.b::contains, take(TINY_LIMIT, is)));
        }
    }

    private static void propertiesUniformSample_String() {
        System.out.println("\t\ttesting uniformSample(String) properties...");

        for (Pair<RandomProvider, String> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().strings()))) {
            Iterable<Character> cs = p.a.uniformSample(p.b);
            testNoRemove(TINY_LIMIT, cs);
            assertEquals(cs.toString(), isEmpty(cs), p.b.isEmpty());
            assertTrue(cs.toString(), all(c -> elem(c, p.b), take(TINY_LIMIT, cs)));
        }
    }

    private static void propertiesOrderings() {
        System.out.println("\t\ttesting orderings() properties...");

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
        System.out.println("\t\ttesting roundingModes() properties...");

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
        System.out.println("\t\ttesting positiveBytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.positiveBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b > 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesPositiveShorts() {
        System.out.println("\t\ttesting positiveShorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.positiveShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s > 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesPositiveIntegers() {
        System.out.println("\t\ttesting positiveIntegers() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.positiveIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i > 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesPositiveLongs() {
        System.out.println("\t\ttesting positiveLongs() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.positiveLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l > 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNegativeBytes() {
        System.out.println("\t\ttesting negativeBytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.negativeBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b < 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNegativeShorts() {
        System.out.println("\t\ttesting negativeShorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.negativeShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s < 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNegativeIntegers() {
        System.out.println("\t\ttesting negativeIntegers() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.negativeIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i < 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNegativeLongs() {
        System.out.println("\t\ttesting negativeLongs() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.negativeLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l < 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNaturalBytes() {
        System.out.println("\t\ttesting naturalBytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.naturalBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b >= 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNaturalShorts() {
        System.out.println("\t\ttesting naturalShorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.naturalShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s >= 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNaturalIntegers() {
        System.out.println("\t\ttesting naturalIntegers() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.naturalIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i >= 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNaturalLongs() {
        System.out.println("\t\ttesting naturalLongs() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.naturalLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l >= 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesNonzeroBytes() {
        System.out.println("\t\ttesting nonzeroBytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.nonzeroBytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            assertTrue(rp.toString(), all(b -> b != 0, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesNonzeroShorts() {
        System.out.println("\t\ttesting nonzeroShorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.nonzeroShorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            assertTrue(rp.toString(), all(s -> s != 0, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesNonzeroIntegers() {
        System.out.println("\t\ttesting nonzeroIntegers() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Integer> is = rp.nonzeroIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i != 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNonzeroLongs() {
        System.out.println("\t\ttesting nonzeroLongs() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Long> ls = rp.nonzeroLongs();
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(rp.toString(), all(l -> l != null, tls));
            assertTrue(rp.toString(), all(l -> l != 0, tls));
            testNoRemove(TINY_LIMIT, ls);
        }
    }

    private static void propertiesBytes() {
        System.out.println("\t\ttesting bytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Byte> bs = rp.bytes();
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(rp.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
        }
    }

    private static void propertiesShorts() {
        System.out.println("\t\ttesting shorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Short> ss = rp.shorts();
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(rp.toString(), all(s -> s != null, tss));
            testNoRemove(TINY_LIMIT, ss);
        }
    }

    private static void propertiesAsciiCharacters() {
        System.out.println("\t\ttesting nonzeroBytes() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.asciiCharacters();
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(rp.toString(), all(c -> c != null, tcs));
            assertTrue(rp.toString(), all(c -> c < 128, tcs));
            testNoRemove(TINY_LIMIT, cs);
        }
    }

    private static void propertiesCharacters() {
        System.out.println("\t\ttesting nonzeroShorts() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            Iterable<Character> cs = rp.characters();
            assertTrue(rp.toString(), all(c -> c != null, take(TINY_LIMIT, cs)));
            testNoRemove(TINY_LIMIT, cs);
        }
    }

    private static void propertiesRangeUp_byte() {
        System.out.println("\t\ttesting rangeUp(byte) properties...");

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().bytes()))) {
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
        System.out.println("\t\ttesting rangeUp(short) properties...");

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().shorts()))) {
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
        System.out.println("\t\ttesting rangeUp(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().integers()))) {
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
        System.out.println("\t\ttesting rangeUp(long) properties...");

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().longs()))) {
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
        System.out.println("\t\ttesting rangeUp(char) properties...");

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.alt().characters());
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
        System.out.println("\t\ttesting rangeDown(byte) properties...");

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().bytes()))) {
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
        System.out.println("\t\ttesting rangeDown(short) properties...");

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().shorts()))) {
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
        System.out.println("\t\ttesting rangeDown(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().integers()))) {
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
        System.out.println("\t\ttesting rangeDown(long) properties...");

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().longs()))) {
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
        System.out.println("\t\ttesting rangeDown(char) properties...");

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.alt().characters());
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
        System.out.println("\t\ttesting range(byte, byte) properties...");

        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().bytes(),
                P.alt().alt().bytes()
        );
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            Iterable<Byte> bs = p.a.range(p.b, p.c);
            Iterable<Byte> tbs = take(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b != null, tbs));
            testNoRemove(TINY_LIMIT, bs);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tbs));
            assertEquals(p.toString(), p.b > p.c, isEmpty(bs));
        }

        for (Pair<RandomProvider, Byte> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().bytes()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, Byte.MAX_VALUE), p.a.rangeUp(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(Byte.MIN_VALUE, p.b), p.a.rangeDown(p.b));
        }
    }

    private static void propertiesRange_short_short() {
        System.out.println("\t\ttesting range(short, short) properties...");

        Iterable<Triple<RandomProvider, Short, Short>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().shorts(),
                P.alt().alt().shorts()
        );
        for (Triple<RandomProvider, Short, Short> p : take(LIMIT, ts)) {
            Iterable<Short> ss = p.a.range(p.b, p.c);
            Iterable<Short> tss = take(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(b -> b != null, tss));
            testNoRemove(TINY_LIMIT, ss);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tss));
            assertEquals(p.toString(), p.b > p.c, isEmpty(ss));
        }

        for (Pair<RandomProvider, Short> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().shorts()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, Short.MAX_VALUE), p.a.rangeUp(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(Short.MIN_VALUE, p.b), p.a.rangeDown(p.b));
        }
    }

    private static void propertiesRange_int_int() {
        System.out.println("\t\ttesting range(int, int) properties...");

        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().integers(),
                P.alt().alt().integers()
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(LIMIT, ts)) {
            Iterable<Integer> is = p.a.range(p.b, p.c);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tis));
            assertEquals(p.toString(), p.b > p.c, isEmpty(is));
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().integers()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, Integer.MAX_VALUE), p.a.rangeUp(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(Integer.MIN_VALUE, p.b), p.a.rangeDown(p.b));
        }
    }

    private static void propertiesRange_long_long() {
        System.out.println("\t\ttesting range(long, long) properties...");

        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().longs(),
                P.alt().alt().longs()
        );
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            Iterable<Long> ls = p.a.range(p.b, p.c);
            Iterable<Long> tls = take(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(b -> b != null, tls));
            testNoRemove(TINY_LIMIT, ls);
            assertTrue(p.toString(), all(b -> b >= p.b && b <= p.c, tls));
            assertEquals(p.toString(), p.b > p.c, isEmpty(ls));
        }

        for (Pair<RandomProvider, Long> p : take(LIMIT, P.pairs(P.randomProvidersDefault(), P.alt().longs()))) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, Long.MAX_VALUE), p.a.rangeUp(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(Long.MIN_VALUE, p.b), p.a.rangeDown(p.b));
        }
    }

    private static void propertiesRange_BigInteger_BigInteger() {
        System.out.println("\t\ttesting range(BigInteger, BigInteger) properties...");

        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().bigIntegers(),
                P.alt().alt().bigIntegers()
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(LIMIT, ts)) {
            Iterable<BigInteger> is = p.a.range(p.b, p.c);
            Iterable<BigInteger> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> b != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(b -> ge(b, p.b) && le(b, p.c), tis));
            assertEquals(p.toString(), gt(p.b, p.c), isEmpty(is));
        }

        Iterable<Pair<RandomProvider, BigInteger>> ps = P.pairs(P.randomProvidersDefault(), P.alt().bigIntegers());
        for (Pair<RandomProvider, BigInteger> p : take(LIMIT, ps)) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
        }
    }

    private static void propertiesRange_char_char() {
        System.out.println("\t\ttesting range(char, char) properties...");

        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.alt().characters(),
                P.alt().alt().characters()
        );
        for (Triple<RandomProvider, Character, Character> p : take(LIMIT, ts)) {
            Iterable<Character> cs = p.a.range(p.b, p.c);
            Iterable<Character> tcs = take(TINY_LIMIT, cs);
            assertTrue(p.toString(), all(b -> b != null, tcs));
            testNoRemove(TINY_LIMIT, p.a.range(p.b, p.c));
            assertTrue(p.toString(), all(b -> ge(b, p.b) && le(b, p.c), tcs));
            assertEquals(p.toString(), gt(p.b, p.c), isEmpty(cs));
        }

        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.alt().characters());
        for (Pair<RandomProvider, Character> p : take(LIMIT, ps)) {
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, p.b), repeat(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range(p.b, Character.MAX_VALUE), p.a.rangeUp(p.b));
            aeqit(p.toString(), TINY_LIMIT, p.a.range('\0', p.b), p.a.rangeDown(p.b));
        }
    }

    private static void propertiesPositiveIntegersGeometric() {
        System.out.println("\t\ttesting positiveIntegersGeometric() properties...");

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
        System.out.println("\t\ttesting negativeIntegersGeometric() properties...");

        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.negativeIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i < 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNaturalIntegersGeometric() {
        System.out.println("\t\ttesting naturalIntegersGeometric() properties...");

        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.naturalIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i >= 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesNonzeroIntegersGeometric() {
        System.out.println("\t\ttesting nonzeroIntegersGeometric() properties...");

        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.nonzeroIntegers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            assertTrue(rp.toString(), all(i -> i != 0, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesIntegersGeometric() {
        System.out.println("\t\ttesting integersGeometric() properties...");

        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            Iterable<Integer> is = rp.integers();
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(rp.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
        }
    }

    private static void propertiesRangeUpGeometric() {
        System.out.println("\t\ttesting rangeUpGeometric(int) properties...");

        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() > p.b && (p.b >= 1 || p.a.getScale() < Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.alt().alt().integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeUpGeometric(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i >= p.b, tis));
        }

        Iterable<RandomProvider> rps = filter(x -> x.getScale() > 1, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUpGeometric(1), rp.positiveIntegersGeometric());
        }

        rps = filter(x -> x.getScale() > 0, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUpGeometric(0), rp.naturalIntegersGeometric());
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filter(
                p -> p.a.getScale() <= p.b || p.b < 1 && p.a.getScale() >= Integer.MAX_VALUE + p.b,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.alt().alt().integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeUpGeometric(p.b);
                fail(p.toString());
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesRangeDownGeometric() {
        System.out.println("\t\ttesting rangeDownGeometric(int) properties...");

        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.alt().alt().integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, ps)) {
            Iterable<Integer> is = p.a.rangeDownGeometric(p.b);
            Iterable<Integer> tis = take(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i != null, tis));
            testNoRemove(TINY_LIMIT, is);
            assertTrue(p.toString(), all(i -> i <= p.b, tis));
        }

        Iterable<RandomProvider> rps = filter(x -> x.getScale() > 1, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUpGeometric(1), rp.positiveIntegersGeometric());
        }

        rps = filter(x -> x.getScale() > 0, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(LIMIT, rps)) {
            aeqit(rp.toString(), TINY_LIMIT, rp.rangeUpGeometric(0), rp.naturalIntegersGeometric());
        }

        Iterable<Pair<RandomProvider, Integer>> psFail = filter(
                p -> p.a.getScale() >= p.b || p.b > -1 && p.a.getScale() <= p.b - Integer.MAX_VALUE,
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.alt().alt().integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(LIMIT, psFail)) {
            try {
                p.a.rangeDownGeometric(p.b);
                fail(p.toString());
            } catch (IllegalStateException ignored) {}
        }
    }

    private static void propertiesEquals() {
        System.out.println("\t\ttesting equals(Object) properties...");

        propertiesEqualsHelper(P, P.randomProviders(), RandomProvider::equals, LIMIT);
    }

    private static void propertiesHashCode() {
        System.out.println("\t\ttesting hashCode() properties...");

        propertiesHashCodeHelper(P.randomProviders(), LIMIT);
    }

    private static void propertiesToString() {
        System.out.println("\t\ttesting toString() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            String s = rp.toString();
            assertTrue(rp.toString(), isSubsetOf(s, RANDOM_PROVIDER_CHARS));
        }
    }
}
