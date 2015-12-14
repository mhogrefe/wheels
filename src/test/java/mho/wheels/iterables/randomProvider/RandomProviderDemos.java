package mho.wheels.iterables.randomProvider;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.math.BinaryFraction;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Demos;
import mho.wheels.testing.Testing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class RandomProviderDemos extends Demos {
    public RandomProviderDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        initialize();
        for (Void v : take(LIMIT, repeat((Void) null))) {
            System.out.println("RandomProvider() = " + new RandomProvider());
        }
    }

    private void demoConstructor_List_Integer() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            System.out.println("RandomProvider(" + is + ") = " + new RandomProvider(is));
        }
    }

    private void demoGetScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getScale(" + rp + ") = " + rp.getScale());
        }
    }

    private void demoGetSecondaryScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSecondaryScale(" + rp + ") = " + rp.getSecondaryScale());
        }
    }

    private void demoGetSeed() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSeed(" + rp + ") = " + rp.getSeed());
        }
    }

    private void demoWithScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withScale(" + p.a + ", " + p.b + ") = " + p.a.withScale(p.b));
        }
    }

    private void demoWithSecondaryScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withSecondaryScale(" + p.a + ", " + p.b + ") = " + p.a.withSecondaryScale(p.b));
        }
    }

    private void demoCopy() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("copy(" + rp + ") = " + rp.copy());
        }
    }

    private void demoDeepCopy() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("deepCopy(" + rp + ") = " + rp.deepCopy());
        }
    }

    private void demoReset() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            head(rp.integers());
            RandomProvider beforeReset = rp.deepCopy();
            rp.reset();
            System.out.println("reset(" + beforeReset + ") => " + rp);
        }
    }

    private void demoGetId() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getId(" + rp + ") = " + rp.getId());
        }
    }

    private void demoIntegers() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("integers(" + rp + ") = " + its(rp.integers()));
        }
    }

    private void demoLongs() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("longs(" + rp + ") = " + its(rp.longs()));
        }
    }

    private void demoBooleans() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("booleans(" + rp + ") = " + its(rp.booleans()));
        }
    }

    private void demoUniformSample_Iterable() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " + p.b.toString() + ") = " + its(p.a.uniformSample(p.b)));
        }
    }

    private void demoUniformSample_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.stringsAtLeast(1));
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " +  nicePrint(p.b) + ") = " +
                    cits(p.a.uniformSample(p.b)));
        }
    }

    private void demoOrderings() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("orderings(" + rp + ") = " + its(rp.orderings()));
        }
    }

    private void demoRoundingModes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("roundingModes(" + rp + ") = " + its(rp.roundingModes()));
        }
    }

    private void demoPositiveBytes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveBytes(" + rp + ") = " + its(rp.positiveBytes()));
        }
    }

    private void demoPositiveShorts() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveShorts(" + rp + ") = " + its(rp.positiveShorts()));
        }
    }

    private void demoPositiveIntegers() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveIntegers(" + rp + ") = " + its(rp.positiveIntegers()));
        }
    }

    private void demoPositiveLongs() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveLongs(" + rp + ") = " + its(rp.positiveLongs()));
        }
    }

    private void demoNegativeBytes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeBytes(" + rp + ") = " + its(rp.negativeBytes()));
        }
    }

    private void demoNegativeShorts() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeShorts(" + rp + ") = " + its(rp.negativeShorts()));
        }
    }

    private void demoNegativeIntegers() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeIntegers(" + rp + ") = " + its(rp.negativeIntegers()));
        }
    }

    private void demoNegativeLongs() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeLongs(" + rp + ") = " + its(rp.negativeLongs()));
        }
    }

    private void demoNaturalBytes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalBytes(" + rp + ") = " + its(rp.naturalBytes()));
        }
    }

    private void demoNaturalShorts() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalShorts(" + rp + ") = " + its(rp.naturalShorts()));
        }
    }

    private void demoNaturalIntegers() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalIntegers(" + rp + ") = " + its(rp.naturalIntegers()));
        }
    }

    private void demoNaturalLongs() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalLongs(" + rp + ") = " + its(rp.naturalLongs()));
        }
    }

    private void demoNonzeroBytes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroBytes(" + rp + ") = " + its(rp.nonzeroBytes()));
        }
    }

    private void demoNonzeroShorts() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroShorts(" + rp + ") = " + its(rp.nonzeroShorts()));
        }
    }

    private void demoNonzeroIntegers() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroIntegers(" + rp + ") = " + its(rp.nonzeroIntegers()));
        }
    }

    private void demoNonzeroLongs() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroLongs(" + rp + ") = " + its(rp.nonzeroLongs()));
        }
    }

    private void demoBytes() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("bytes(" + rp + ") = " + its(rp.bytes()));
        }
    }

    private void demoShorts() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("shorts(" + rp + ") = " + its(rp.shorts()));
        }
    }

    private void demoAsciiCharacters() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("asciiCharacters(" + rp + ") = " + cits(rp.asciiCharacters()));
        }
    }

    private void demoCharacters() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("characters(" + rp + ") = " + cits(rp.characters()));
        }
    }

    private void demoRangeUp_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_byte_byte() {
        initialize();
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_short_short() {
        initialize();
        Iterable<Triple<RandomProvider, Short, Short>> ts = P.triples(
                P.randomProvidersDefault(),
                P.shorts(),
                P.shorts()
        );
        for (Triple<RandomProvider, Short, Short> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_int_int() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.integers(),
                P.integers()
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_long_long() {
        initialize();
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_BigInteger_BigInteger() {
        initialize();
        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = P.triples(
                P.randomProvidersDefault(),
                P.bigIntegers(),
                P.bigIntegers()
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_char_char() {
        initialize();
        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.characters(),
                P.characters()
        );
        for (Triple<RandomProvider, Character, Character> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + cits(p.a.range(p.b, p.c)));
        }
    }

    private void demoPositiveIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveIntegersGeometric(" + rp + ") = " + its(rp.positiveIntegersGeometric()));
        }
    }

    private void demoNegativeIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeIntegersGeometric(" + rp + ") = " + its(rp.negativeIntegersGeometric()));
        }
    }

    private void demoNaturalIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("naturalIntegersGeometric(" + rp + ") = " + its(rp.naturalIntegersGeometric()));
        }
    }

    private void demoNonzeroIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroIntegersGeometric(" + rp + ") = " + its(rp.nonzeroIntegersGeometric()));
        }
    }

    private void demoIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("integersGeometric(" + rp + ") = " + its(rp.integersGeometric()));
        }
    }

    private void demoRangeUpGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b > 1 || p.a.getScale() >= Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpGeometric(p.b)));
        }
    }

    private void demoRangeDownGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownGeometric(p.b)));
        }
    }

    private void demoPositiveBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBigIntegers(" + rp + ") = " + its(rp.positiveBigIntegers()));
        }
    }

    private void demoNegativeBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBigIntegers(" + rp + ") = " + its(rp.negativeBigIntegers()));
        }
    }

    private void demoNaturalBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("naturalBigIntegers(" + rp + ") = " + its(rp.naturalBigIntegers()));
        }
    }

    private void demoNonzeroBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBigIntegers(" + rp + ") = " + its(rp.nonzeroBigIntegers()));
        }
    }

    private void demoBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("bigIntegers(" + rp + ") = " + its(rp.bigIntegers()));
        }
    }

    private void demoRangeUp_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoPositiveBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBinaryFractions(" + rp + ") = " + its(rp.positiveBinaryFractions()));
        }
    }

    private void demoNegativeBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBinaryFractions(" + rp + ") = " + its(rp.negativeBinaryFractions()));
        }
    }

    private void demoNonzeroBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBinaryFractions(" + rp + ") = " + its(rp.nonzeroBinaryFractions()));
        }
    }

    private void demoBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("binaryFractions(" + rp + ") = " + its(rp.binaryFractions()));
        }
    }

    private void demoRangeUp_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_BinaryFraction_BinaryFraction() {
        initialize();
        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = P.triples(
                filterInfinite(
                        x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                        P.randomProvidersDefaultSecondaryScale()
                ),
                P.binaryFractions(),
                P.binaryFractions()
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoPositiveFloats() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloats(" + rp + ") = " + its(rp.positiveFloats()));
        }
    }

    private void demoNegativeFloats() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloats(" + rp + ") = " + its(rp.negativeFloats()));
        }
    }

    private void demoNonzeroFloats() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloats(" + rp + ") = " + its(rp.nonzeroFloats()));
        }
    }

    private void demoFloats() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floats(" + rp + ") = " + its(rp.floats()));
        }
    }

    private void demoPositiveDoubles() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoubles(" + rp + ") = " + its(rp.positiveDoubles()));
        }
    }

    private void demoNegativeDoubles() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoubles(" + rp + ") = " + its(rp.negativeDoubles()));
        }
    }

    private void demoNonzeroDoubles() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoubles(" + rp + ") = " + its(rp.nonzeroDoubles()));
        }
    }

    private void demoDoubles() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doubles(" + rp + ") = " + its(rp.doubles()));
        }
    }

    private void demoPositiveFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloatsUniform(" + rp + ") = " + its(rp.positiveFloatsUniform()));
        }
    }

    private void demoNegativeFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloatsUniform(" + rp + ") = " + its(rp.negativeFloatsUniform()));
        }
    }

    private void demoNonzeroFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloatsUniform(" + rp + ") = " + its(rp.nonzeroFloatsUniform()));
        }
    }

    private void demoFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floatsUniform(" + rp + ") = " + its(rp.floatsUniform()));
        }
    }

    private void demoPositiveDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoublesUniform(" + rp + ") = " + its(rp.positiveDoublesUniform()));
        }
    }

    private void demoNegativeDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoublesUniform(" + rp + ") = " + its(rp.negativeDoublesUniform()));
        }
    }

    private void demoNonzeroDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoublesUniform(" + rp + ") = " + its(rp.nonzeroDoublesUniform()));
        }
    }

    private void demoDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doublesUniform(" + rp + ") = " + its(rp.doublesUniform()));
        }
    }

    private void demoRangeUp_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats()),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUp_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles()),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUpUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private void demoRangeDownUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private void demoRangeUniform_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats()),
                filter(Float::isFinite, P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
        }
    }

    private void demoRangeUpUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private void demoRangeDownUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private void demoRangeUniform_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles()),
                filter(Double::isFinite, P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
        }
    }

    private void demoPositiveBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBigDecimals(" + rp + ") = " + its(rp.positiveBigDecimals()));
        }
    }

    private void demoNegativeBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBigDecimals(" + rp + ") = " + its(rp.negativeBigDecimals()));
        }
    }

    private void demoNonzeroBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBigDecimals(" + rp + ") = " + its(rp.nonzeroBigDecimals()));
        }
    }

    private void demoBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("bigDecimals(" + rp + ") = " + its(rp.bigDecimals()));
        }
    }

    private void demoPositiveCanonicalBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveCanonicalBigDecimals(" + rp + ") = " + its(rp.positiveCanonicalBigDecimals()));
        }
    }

    private void demoNegativeCanonicalBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeCanonicalBigDecimals(" + rp + ") = " + its(rp.negativeCanonicalBigDecimals()));
        }
    }

    private void demoNonzeroCanonicalBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroCanonicalBigDecimals(" + rp + ") = " + its(rp.nonzeroCanonicalBigDecimals()));
        }
    }

    private void demoCanonicalBigDecimals() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("canonicalBigDecimals(" + rp + ") = " + its(rp.canonicalBigDecimals()));
        }
    }

    private void demoRangeUp_BigDecimal() {
        initialize();
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BigDecimal() {
        initialize();
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_BigDecimal_BigDecimal() {
        initialize();
        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = P.triples(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUpCanonical_BigDecimal() {
        initialize();
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpCanonical(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpCanonical(p.b)));
        }
    }

    private void demoRangeDownCanonical_BigDecimal() {
        initialize();
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownCanonical(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownCanonical(p.b)));
        }
    }

    private void demoRangeCanonical_BigDecimal_BigDecimal() {
        initialize();
        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = P.triples(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.bigDecimals(),
                P.bigDecimals()
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeCanonical(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeCanonical(t.b, t.c)));
        }
    }

    private void demoWithElement() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("withElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.withElement(t.b, t.c)));
        }
    }

    private void demoWithNull() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("withNull(" + p.a + ", " + its(p.b) + ") = " + its(p.a.withNull(p.b)));
        }
    }

    private void demoOptionals() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("optionals(" + p.a + ", " + its(p.b) + ") = " + its(p.a.optionals(p.b)));
        }
    }

    private void demoNullableOptionals() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("nullableOptionals(" + p.a + ", " + its(p.b) + ") = " +
                    its(p.a.nullableOptionals(p.b)));
        }
    }

    private void demoDependentPairsInfinite() {
        initialize();
        RandomProvider RP = RandomProvider.example();
        IterableProvider PS = P.withScale(4);
        Function<List<Integer>, Iterable<Map<Integer, List<Integer>>>> f = xs -> filterInfinite(
                m -> !all(p -> isEmpty(p.b), fromMap(m)),
                PS.maps(xs, IterableUtils.map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric())))
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
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfinite(" + RandomProvider.example() + ", " + its(p.a) + ", " +
                    niceFunction + ") = " + its(RP.dependentPairsInfinite(p.a, p.b)));
        }
    }

    private void demoShuffle() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(LIMIT, ps)) {
            List<Integer> shuffled = toList(p.b);
            p.a.shuffle(shuffled);
            System.out.println("shuffle(" + p.a + ", " + p.b + ") => " + shuffled);
        }
    }

    private void demoPermutationsFinite() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("permutationsFinite(" + p.a + ", " + p.b + ") = " + its(p.a.permutationsFinite(p.b)));
        }
    }

    private void demoStringPermutations() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringPermutations(" + p.a + ", " + p.b + ") = " + its(p.a.stringPermutations(p.b)));
        }
    }

    private void demoPrefixPermutations_finite() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryScale()),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("prefixPermutations(" + p.a + ", " + p.b + ") = " +
                    its(map(IterableUtils::toList, p.a.prefixPermutations(p.b))));
        }
    }

    private void demoPrefixPermutations_infinite() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("prefixPermutations(" + p.a + ", " + its(p.b) + ") = " +
                    its(map(Testing::its, p.a.prefixPermutations(p.b))));
        }
    }

    private void demoStrings_int_String() {
        initialize();
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
        for (Triple<RandomProvider, String, Integer> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("strings(" + t.a + ", " + t.c + ", " + nicePrint(t.b) + ") = " +
                    sits(t.a.strings(t.c, t.b)));
        }
    }

    private void demoStrings_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("strings(" + p.a + ", " + p.b + ") = " + sits(p.a.strings(p.b)));
        }
    }

    private void demoLists() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("lists(" + p.a + ", " + its(p.b) + ") = " + its(p.a.lists(p.b)));
        }
    }

    private void demoStrings_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("strings(" + p.a + ", " + nicePrint(p.b) + ") = " + sits(p.a.strings(p.b)));
        }
    }

    private void demoStrings() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("strings(" + rp + ") = " + sits(rp.strings()));
        }
    }

    private void demoListsAtLeast() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("listsAtLeast(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.listsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringsAtLeast_int_String() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringsAtLeast(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringsAtLeast_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsAtLeast(" + p.a + ", " + p.b + ") = " + sits(p.a.stringsAtLeast(p.b)));
        }
    }

    private void demoDistinctStrings_int_String() {
        initialize();
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
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("distinctStrings(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.distinctStrings(t.b, t.c)));
        }
    }

    private void demoDistinctStrings_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.a + ", " + p.b + ") = " + sits(p.a.distinctStrings(p.b)));
        }
    }

    private void demoDistinctLists() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).repeatingIterables(P.withNull(P.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctLists(" + p.a + ", " + its(p.b) + ") = " + its(p.a.distinctLists(p.b)));
        }
    }

    private void demoDistinctStrings_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.distinctStrings(p.b)));
        }
    }

    private void demoDistinctStrings() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("distinctStrings(" + rp + ") = " + sits(rp.distinctStrings()));
        }
    }

    private void demoDistinctListsAtLeast() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
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
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("distinctListsAtLeast(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.distinctListsAtLeast(t.b, t.c)));
        }
    }

    private void demoDistinctStringsAtLeast_int_String() {
        initialize();
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
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("distinctStringsAtLeast(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.distinctStringsAtLeast(t.b, t.c)));
        }
    }

    private void demoDistinctStringsAtLeast_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStringsAtLeast(" + p.a + ", " + p.b + ") = " +
                    sits(p.a.distinctStringsAtLeast(p.b)));
        }
    }

    private void demoStringBags_int_String() {
        initialize();
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
        for (Triple<RandomProvider, String, Integer> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringBags(" + t.a + ", " + t.c + ", " + nicePrint(t.b) + ") = " +
                    sits(t.a.stringBags(t.c, t.b)));
        }
    }

    private void demoStringBags_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBags(" + p.a + ", " + p.b + ") = " + sits(p.a.stringBags(p.b)));
        }
    }

    private void demoBags() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bags(" + p.a + ", " + its(p.b) + ") = " + its(p.a.bags(p.b)));
        }
    }

    private void demoStringBags_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBags(" + p.a + ", " + nicePrint(p.b) + ") = " + sits(p.a.stringBags(p.b)));
        }
    }

    private void demoStringBags() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("stringBags(" + rp + ") = " + sits(rp.stringBags()));
        }
    }

    private void demoBagsAtLeast() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.prefixPermutations(EP.naturalIntegers())
                )
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("bagsAtLeast(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.bagsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringBagsAtLeast_int_String() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric(),
                        P.withScale(4).stringsAtLeast(1)
                )
        );
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringBagsAtLeast(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringBagsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringBagsAtLeast_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBagsAtLeast(" + p.a + ", " + p.b + ") = " + sits(p.a.stringBagsAtLeast(p.b)));
        }
    }

    private void demoStringSubsets_int_String() {
        initialize();
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
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringSubsets(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringSubsets(t.b, t.c)));
        }
    }

    private void demoStringSubsets_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.a + ", " + p.b + ") = " + sits(p.a.stringSubsets(p.b)));
        }
    }

    private void demoSubsets() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).repeatingIterables(P.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("subsets(" + p.a + ", " + its(p.b) + ") = " + its(p.a.subsets(p.b)));
        }
    }

    private void demoStringSubsets_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringSubsets(p.b)));
        }
    }

    private void demoStringSubsets() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("stringSubsets(" + rp + ") = " + sits(rp.stringSubsets()));
        }
    }

    private void demoSubsetsAtLeast() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
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
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("subsetsAtLeast(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.subsetsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringSubsetsAtLeast_int_String() {
        initialize();
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
        for (Triple<RandomProvider, Integer, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringSubsetsAtLeast(" + t.a + ", " + t.b + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringSubsetsAtLeast(t.b, t.c)));
        }
    }

    private void demoStringSubsetsAtLeast_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsetsAtLeast(" + p.a + ", " + p.b + ") = " +
                    sits(p.a.stringSubsetsAtLeast(p.b)));
        }
    }

    private void demoCartesianProduct() {
        initialize();
        Iterable<Pair<RandomProvider, List<List<Integer>>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())))
        );
        for (Pair<RandomProvider, List<List<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("cartesianProduct(" + p.a + ", " + p.b + ") = " + its(p.a.cartesianProduct(p.b)));
        }
    }

    private void demoRepeatingIterables() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("repeatingIterables(" + p.a + ", " + its(p.b) + ") = " +
                    its(map(Testing::its, p.a.repeatingIterables(p.b))));
        }
    }

    private void demoRepeatingIterablesDistinctAtLeast() {
        initialize();
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
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(SMALL_LIMIT, ts)) {
            System.out.println("repeatingIterablesDistinctAtLeast(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(map(Testing::its, t.a.repeatingIterablesDistinctAtLeast(t.b, t.c))));
        }
    }

    private void demoSublists() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("sublists(" + p.a + ", " + p.b + ") = " + its(p.a.sublists(p.b)));
        }
    }

    private void demoSubstrings() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("substrings(" + p.a + ", " + nicePrint(p.b) + ") = " + its(p.a.substrings(p.b)));
        }
    }

    private void demoListsWithElement() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("listsWithElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.listsWithElement(t.b, t.c)));
        }
    }

    private void demoStringsWithChar_char_String() {
        initialize();
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
        for (Triple<RandomProvider, Character, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringsWithChar(" + t.a + ", " + nicePrint(t.b) + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringsWithChar(t.b, t.c)));
        }
    }

    private void demoStringsWithChar_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 3, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsWithChar(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringsWithChar(p.b)));
        }
    }

    private void demoSubsetsWithElement() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("subsetsWithElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.subsetsWithElement(t.b, t.c)));
        }
    }

    private void demoStringSubsetsWithChar_char_String() {
        initialize();
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
        for (Triple<RandomProvider, Character, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringSubsetsWithChar(" + t.a + ", " + nicePrint(t.b) + ", " + nicePrint(t.c) +
                    ") = " + sits(t.a.stringSubsetsWithChar(t.b, t.c)));
        }
    }

    private void demoStringSubsetsWithChar_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() >= 2, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsetsWithChar(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringSubsetsWithChar(p.b)));
        }
    }

    private void demoListsWithSublists() {
        initialize();
        Iterable<Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 1, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.lists(EP.withNull(EP.naturalIntegers()))),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("listsWithSublists(" + t.a + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(t.a.listsWithSublists(t.b, t.c)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String_String() {
        initialize();
        Iterable<Triple<RandomProvider, Iterable<String>, String>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 1, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Triple<RandomProvider, Iterable<String>, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringsWithSubstrings(" + t.a + ", " + sits(t.b) + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringsWithSubstrings(t.b, t.c)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String() {
        initialize();
        Iterable<Pair<RandomProvider, Iterable<String>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 1, P.withScale(4).randomProvidersDefaultSecondaryScale()),
                P.prefixPermutations(EP.strings())
        );
        for (Pair<RandomProvider, Iterable<String>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsWithSubstrings(" + p.a + ", " + sits(p.b) + ") = " +
                    sits(p.a.stringsWithSubstrings(p.b)));
        }
    }

    private void demoMaps() {
        initialize();
        Iterable<Triple<RandomProvider, List<Integer>, Iterable<Integer>>> ts = P.triples(
                P.randomProvidersDefault(),
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, List<Integer>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("maps(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " + its(t.a.maps(t.b, t.c)));
        }
    }

    private void demoRandomProvidersFixedScales() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = P.triples(
                P.randomProvidersDefault(),
                P.integersGeometric(),
                P.integersGeometric()
        );
        for (Triple<RandomProvider, Integer, Integer> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("randomProvidersFixedScales(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.randomProvidersFixedScales(t.b, t.c)));
        }
    }

    private void demoRandomProvidersDefault() {
        initialize();
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("randomProvidersDefault(" + rp + ") = " + its(rp.randomProvidersDefault()));
        }
    }

    private void demoRandomProvidersDefaultSecondaryScale() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("randomProvidersDefaultSecondaryScale(" + rp + ") = " +
                    its(rp.randomProvidersDefaultSecondaryScale()));
        }
    }

    private void demoRandomProviders() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("randomProviders(" + rp + ") = " + its(rp.randomProviders()));
        }
    }

    private void demoEquals_RandomProvider() {
        initialize();
        for (Pair<RandomProvider, RandomProvider> p : take(LIMIT, P.pairs(P.randomProviders()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            //noinspection ObjectEqualsNull
            System.out.println(r + (r.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            System.out.println("hashCode(" + r + ") = " + r.hashCode());
        }
    }

    private void demoToString() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println(rp);
        }
    }
}
