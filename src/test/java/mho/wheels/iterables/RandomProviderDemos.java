package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.ordering.Ordering;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Quadruple;
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
        for (Void v : take(LIMIT, repeat((Void) null))) {
            System.out.println("RandomProvider() = " + new RandomProvider());
        }
    }

    private void demoConstructor_List_Integer() {
        for (List<Integer> is : take(LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            System.out.println("RandomProvider(" + is + ") = " + new RandomProvider(is));
        }
    }

    private void demoGetScale() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getScale(" + rp + ") = " + rp.getScale());
        }
    }

    private void demoGetSecondaryScale() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSecondaryScale(" + rp + ") = " + rp.getSecondaryScale());
        }
    }

    private void demoGetTertiaryScale() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getTertiaryScale(" + rp + ") = " + rp.getTertiaryScale());
        }
    }

    private void demoGetSeed() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSeed(" + rp + ") = " + rp.getSeed());
        }
    }

    private void demoWithScale() {
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withScale(" + p.a + ", " + p.b + ") = " + p.a.withScale(p.b));
        }
    }

    private void demoWithSecondaryScale() {
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withSecondaryScale(" + p.a + ", " + p.b + ") = " + p.a.withSecondaryScale(p.b));
        }
    }

    private void demoWithTertiaryScale() {
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withTertiaryScale(" + p.a + ", " + p.b + ") = " + p.a.withTertiaryScale(p.b));
        }
    }

    private void demoCopy() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("copy(" + rp + ") = " + rp.copy());
        }
    }

    private void demoDeepCopy() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("deepCopy(" + rp + ") = " + rp.deepCopy());
        }
    }

    private void demoReset() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            head(rp.integers());
            RandomProvider beforeReset = rp.deepCopy();
            rp.reset();
            System.out.println("reset(" + beforeReset + ") => " + rp);
        }
    }

    private void demoGetId() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getId(" + rp + ") = " + rp.getId());
        }
    }

    private void demoIntegers() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("integers(" + rp + ") = " + its(rp.integers()));
        }
    }

    private void demoLongs() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("longs(" + rp + ") = " + its(rp.longs()));
        }
    }

    private void demoBooleans() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("booleans(" + rp + ") = " + its(rp.booleans()));
        }
    }

    private void demoUniformSample_Iterable() {
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " + p.b.toString() + ") = " + its(p.a.uniformSample(p.b)));
        }
    }

    private void demoUniformSample_String() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.stringsAtLeast(1));
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " +  nicePrint(p.b) + ") = " +
                    cits(p.a.uniformSample(p.b)));
        }
    }

    private void demoOrderings() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("orderings(" + rp + ") = " + its(rp.orderings()));
        }
    }

    private void demoRoundingModes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("roundingModes(" + rp + ") = " + its(rp.roundingModes()));
        }
    }

    private void demoPositiveBytes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveBytes(" + rp + ") = " + its(rp.positiveBytes()));
        }
    }

    private void demoPositiveShorts() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveShorts(" + rp + ") = " + its(rp.positiveShorts()));
        }
    }

    private void demoPositiveIntegers() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveIntegers(" + rp + ") = " + its(rp.positiveIntegers()));
        }
    }

    private void demoPositiveLongs() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveLongs(" + rp + ") = " + its(rp.positiveLongs()));
        }
    }

    private void demoNegativeBytes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeBytes(" + rp + ") = " + its(rp.negativeBytes()));
        }
    }

    private void demoNegativeShorts() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeShorts(" + rp + ") = " + its(rp.negativeShorts()));
        }
    }

    private void demoNegativeIntegers() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeIntegers(" + rp + ") = " + its(rp.negativeIntegers()));
        }
    }

    private void demoNegativeLongs() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeLongs(" + rp + ") = " + its(rp.negativeLongs()));
        }
    }

    private void demoNaturalBytes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalBytes(" + rp + ") = " + its(rp.naturalBytes()));
        }
    }

    private void demoNaturalShorts() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalShorts(" + rp + ") = " + its(rp.naturalShorts()));
        }
    }

    private void demoNaturalIntegers() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalIntegers(" + rp + ") = " + its(rp.naturalIntegers()));
        }
    }

    private void demoNaturalLongs() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalLongs(" + rp + ") = " + its(rp.naturalLongs()));
        }
    }

    private void demoNonzeroBytes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroBytes(" + rp + ") = " + its(rp.nonzeroBytes()));
        }
    }

    private void demoNonzeroShorts() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroShorts(" + rp + ") = " + its(rp.nonzeroShorts()));
        }
    }

    private void demoNonzeroIntegers() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroIntegers(" + rp + ") = " + its(rp.nonzeroIntegers()));
        }
    }

    private void demoNonzeroLongs() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroLongs(" + rp + ") = " + its(rp.nonzeroLongs()));
        }
    }

    private void demoBytes() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("bytes(" + rp + ") = " + its(rp.bytes()));
        }
    }

    private void demoShorts() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("shorts(" + rp + ") = " + its(rp.shorts()));
        }
    }

    private void demoAsciiCharacters() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("asciiCharacters(" + rp + ") = " + cits(rp.asciiCharacters()));
        }
    }

    private void demoCharacters() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("characters(" + rp + ") = " + cits(rp.characters()));
        }
    }

    private void demoRangeUp_byte() {
        for (Pair<RandomProvider, Byte> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_short() {
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_int() {
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_long() {
        for (Pair<RandomProvider, Long> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeUp_char() {
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_byte() {
        for (Pair<RandomProvider, Byte> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_short() {
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_int() {
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_long() {
        for (Pair<RandomProvider, Long> p : take(MEDIUM_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRangeDown_char() {
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_byte_byte() {
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes())
        );
        for (Triple<RandomProvider, Byte, Byte> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_short_short() {
        Iterable<Triple<RandomProvider, Short, Short>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        P.shorts(),
                        P.shorts()
                )
        );
        for (Triple<RandomProvider, Short, Short> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_int_int() {
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        P.integers(),
                        P.integers()
                )
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_long_long() {
        Iterable<Triple<RandomProvider, Long, Long>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.longs(), P.longs())
        );
        for (Triple<RandomProvider, Long, Long> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_BigInteger_BigInteger() {
        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = filterInfinite(
                t -> Ordering.le(t.b, t.c),
                P.triples(
                        P.randomProvidersDefault(),
                        P.bigIntegers(),
                        P.bigIntegers()
                )
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private void demoRange_char_char() {
        Iterable<Triple<RandomProvider, Character, Character>> ts = filterInfinite(
                t -> t.b <= t.c, P.triples(
                        P.randomProvidersDefault(),
                        P.characters(),
                        P.characters()
                )
        );
        for (Triple<RandomProvider, Character, Character> p : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + cits(p.a.range(p.b, p.c)));
        }
    }

    private void demoPositiveIntegersGeometric() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveIntegersGeometric(" + rp + ") = " + its(rp.positiveIntegersGeometric()));
        }
    }

    private void demoNegativeIntegersGeometric() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeIntegersGeometric(" + rp + ") = " + its(rp.negativeIntegersGeometric()));
        }
    }

    private void demoNaturalIntegersGeometric() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("naturalIntegersGeometric(" + rp + ") = " + its(rp.naturalIntegersGeometric()));
        }
    }

    private void demoNonzeroIntegersGeometric() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroIntegersGeometric(" + rp + ") = " + its(rp.nonzeroIntegersGeometric()));
        }
    }

    private void demoIntegersGeometric() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("integersGeometric(" + rp + ") = " + its(rp.integersGeometric()));
        }
    }

    private void demoRangeUpGeometric() {
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b > 1 || p.a.getScale() >= Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryAndTertiaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpGeometric(p.b)));
        }
    }

    private void demoRangeDownGeometric() {
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryAndTertiaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownGeometric(p.b)));
        }
    }

    private void demoPositiveBigIntegers() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBigIntegers(" + rp + ") = " + its(rp.positiveBigIntegers()));
        }
    }

    private void demoNegativeBigIntegers() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBigIntegers(" + rp + ") = " + its(rp.negativeBigIntegers()));
        }
    }

    private void demoNaturalBigIntegers() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("naturalBigIntegers(" + rp + ") = " + its(rp.naturalBigIntegers()));
        }
    }

    private void demoNonzeroBigIntegers() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBigIntegers(" + rp + ") = " + its(rp.nonzeroBigIntegers()));
        }
    }

    private void demoBigIntegers() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("bigIntegers(" + rp + ") = " + its(rp.bigIntegers()));
        }
    }

    private void demoRangeUp_BigInteger() {
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryAndTertiaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BigInteger() {
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryAndTertiaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoPositiveBinaryFractions() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBinaryFractions(" + rp + ") = " + its(rp.positiveBinaryFractions()));
        }
    }

    private void demoNegativeBinaryFractions() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBinaryFractions(" + rp + ") = " + its(rp.negativeBinaryFractions()));
        }
    }

    private void demoNonzeroBinaryFractions() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBinaryFractions(" + rp + ") = " + its(rp.nonzeroBinaryFractions()));
        }
    }

    private void demoBinaryFractions() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("binaryFractions(" + rp + ") = " + its(rp.binaryFractions()));
        }
    }

    private void demoRangeUp_BinaryFraction() {
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BinaryFraction() {
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_BinaryFraction_BinaryFraction() {
        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = filterInfinite(
                t -> Ordering.le(t.b, t.c),
                P.triples(
                        filterInfinite(
                                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                                P.randomProvidersDefaultSecondaryAndTertiaryScale()
                        ),
                        P.binaryFractions(),
                        P.binaryFractions()
                )
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoPositiveFloats() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloats(" + rp + ") = " + its(rp.positiveFloats()));
        }
    }

    private void demoNegativeFloats() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloats(" + rp + ") = " + its(rp.negativeFloats()));
        }
    }

    private void demoNonzeroFloats() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloats(" + rp + ") = " + its(rp.nonzeroFloats()));
        }
    }

    private void demoFloats() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floats(" + rp + ") = " + its(rp.floats()));
        }
    }

    private void demoPositiveDoubles() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoubles(" + rp + ") = " + its(rp.positiveDoubles()));
        }
    }

    private void demoNegativeDoubles() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoubles(" + rp + ") = " + its(rp.negativeDoubles()));
        }
    }

    private void demoNonzeroDoubles() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoubles(" + rp + ") = " + its(rp.nonzeroDoubles()));
        }
    }

    private void demoDoubles() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doubles(" + rp + ") = " + its(rp.doubles()));
        }
    }

    private void demoPositiveFloatsUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloatsUniform(" + rp + ") = " + its(rp.positiveFloatsUniform()));
        }
    }

    private void demoNegativeFloatsUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloatsUniform(" + rp + ") = " + its(rp.negativeFloatsUniform()));
        }
    }

    private void demoNonzeroFloatsUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloatsUniform(" + rp + ") = " + its(rp.nonzeroFloatsUniform()));
        }
    }

    private void demoFloatsUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floatsUniform(" + rp + ") = " + its(rp.floatsUniform()));
        }
    }

    private void demoPositiveDoublesUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoublesUniform(" + rp + ") = " + its(rp.positiveDoublesUniform()));
        }
    }

    private void demoNegativeDoublesUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoublesUniform(" + rp + ") = " + its(rp.negativeDoublesUniform()));
        }
    }

    private void demoNonzeroDoublesUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoublesUniform(" + rp + ") = " + its(rp.nonzeroDoublesUniform()));
        }
    }

    private void demoDoublesUniform() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doublesUniform(" + rp + ") = " + its(rp.doublesUniform()));
        }
    }

    private void demoRangeUp_float() {
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_float() {
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_float_float() {
        Iterable<Triple<RandomProvider, Float, Float>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(f -> !Float.isNaN(f), P.floats()),
                        filter(f -> !Float.isNaN(f), P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUp_double() {
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_double() {
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_double_double() {
        Iterable<Triple<RandomProvider, Double, Double>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(d -> !Double.isNaN(d), P.doubles()),
                        filter(d -> !Double.isNaN(d), P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUpUniform_float() {
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private void demoRangeDownUniform_float() {
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private void demoRangeUniform_float_float() {
        Iterable<Triple<RandomProvider, Float, Float>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Float::isFinite, P.floats()),
                        filter(Float::isFinite, P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
        }
    }

    private void demoRangeUpUniform_double() {
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private void demoRangeDownUniform_double() {
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private void demoRangeUniform_double_double() {
        Iterable<Triple<RandomProvider, Double, Double>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Double::isFinite, P.doubles()),
                        filter(Double::isFinite, P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
        }
    }

    private void demoPositiveBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveBigDecimals(" + rp + ") = " + its(rp.positiveBigDecimals()));
        }
    }

    private void demoNegativeBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeBigDecimals(" + rp + ") = " + its(rp.negativeBigDecimals()));
        }
    }

    private void demoNonzeroBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroBigDecimals(" + rp + ") = " + its(rp.nonzeroBigDecimals()));
        }
    }

    private void demoBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("bigDecimals(" + rp + ") = " + its(rp.bigDecimals()));
        }
    }

    private void demoPositiveCanonicalBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("positiveCanonicalBigDecimals(" + rp + ") = " + its(rp.positiveCanonicalBigDecimals()));
        }
    }

    private void demoNegativeCanonicalBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("negativeCanonicalBigDecimals(" + rp + ") = " + its(rp.negativeCanonicalBigDecimals()));
        }
    }

    private void demoNonzeroCanonicalBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("nonzeroCanonicalBigDecimals(" + rp + ") = " + its(rp.nonzeroCanonicalBigDecimals()));
        }
    }

    private void demoCanonicalBigDecimals() {
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProvidersDefaultTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("canonicalBigDecimals(" + rp + ") = " + its(rp.canonicalBigDecimals()));
        }
    }

    private void demoRangeUp_BigDecimal() {
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private void demoRangeDown_BigDecimal() {
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private void demoRange_BigDecimal_BigDecimal() {
        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = filterInfinite(
                t -> Ordering.le(t.b, t.c),
                P.triples(
                        filterInfinite(
                                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                                P.withScale(4).randomProvidersDefaultTertiaryScale()
                        ),
                        P.withScale(4).bigDecimals(),
                        P.withScale(4).bigDecimals()
                )
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private void demoRangeUpCanonical_BigDecimal() {
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpCanonical(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpCanonical(p.b)));
        }
    }

    private void demoRangeDownCanonical_BigDecimal() {
        Iterable<Pair<RandomProvider, BigDecimal>> ps = P.pairs(
                filterInfinite(
                        x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                        P.randomProvidersDefaultTertiaryScale()
                ),
                P.bigDecimals()
        );
        for (Pair<RandomProvider, BigDecimal> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownCanonical(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownCanonical(p.b)));
        }
    }

    private void demoRangeCanonical_BigDecimal_BigDecimal() {
        Iterable<Triple<RandomProvider, BigDecimal, BigDecimal>> ts = filterInfinite(
                t -> Ordering.le(t.b, t.c),
                P.triples(
                        filterInfinite(
                                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                                P.randomProvidersDefaultTertiaryScale()
                        ),
                        P.bigDecimals(),
                        P.bigDecimals()
                )
        );
        for (Triple<RandomProvider, BigDecimal, BigDecimal> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("rangeCanonical(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeCanonical(t.b, t.c)));
        }
    }

    private void demoWithElement() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("withElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.withElement(t.b, t.c)));
        }
    }

    private void demoWithNull() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("withNull(" + p.a + ", " + its(p.b) + ") = " + its(p.a.withNull(p.b)));
        }
    }

    private void demoOptionals() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("optionals(" + p.a + ", " + its(p.b) + ") = " + its(p.a.optionals(p.b)));
        }
    }

    private void demoNullableOptionals() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("nullableOptionals(" + p.a + ", " + its(p.b) + ") = " +
                    its(p.a.nullableOptionals(p.b)));
        }
    }

    private void demoDependentPairsInfinite() {
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
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("permutationsFinite(" + p.a + ", " + p.b + ") = " + its(p.a.permutationsFinite(p.b)));
        }
    }

    private void demoStringPermutations() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringPermutations(" + p.a + ", " + p.b + ") = " + its(p.a.stringPermutations(p.b)));
        }
    }

    private void demoPrefixPermutations_finite() {
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.withScale(4).lists(P.withNull(P.naturalIntegersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("prefixPermutations(" + p.a + ", " + p.b + ") = " +
                    its(map(IterableUtils::toList, p.a.prefixPermutations(p.b))));
        }
    }

    private void demoPrefixPermutations_infinite() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("prefixPermutations(" + p.a + ", " + its(p.b) + ") = " +
                    its(map(Testing::its, p.a.prefixPermutations(p.b))));
        }
    }

    private void demoStrings_int_String() {
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
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("strings(" + p.a + ", " + p.b + ") = " + sits(p.a.strings(p.b)));
        }
    }

    private void demoLists() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("lists(" + p.a + ", " + its(p.b) + ") = " + its(p.a.lists(p.b)));
        }
    }

    private void demoStrings_String() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("strings(" + p.a + ", " + nicePrint(p.b) + ") = " + sits(p.a.strings(p.b)));
        }
    }

    private void demoStrings() {
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("strings(" + rp + ") = " + sits(rp.strings()));
        }
    }

    private void demoListsAtLeast() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsAtLeast(" + p.a + ", " + p.b + ") = " + sits(p.a.stringsAtLeast(p.b)));
        }
    }

    private void demoDistinctStrings_int_String() {
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.a + ", " + p.b + ") = " + sits(p.a.distinctStrings(p.b)));
        }
    }

    private void demoDistinctLists() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).repeatingIterables(P.withNull(P.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctLists(" + p.a + ", " + its(p.b) + ") = " + its(p.a.distinctLists(p.b)));
        }
    }

    private void demoDistinctStrings_String() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.distinctStrings(p.b)));
        }
    }

    private void demoDistinctStrings() {
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("distinctStrings(" + rp + ") = " + sits(rp.distinctStrings()));
        }
    }

    private void demoDistinctListsAtLeast() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctStringsAtLeast(" + p.a + ", " + p.b + ") = " +
                    sits(p.a.distinctStringsAtLeast(p.b)));
        }
    }

    private void demoStringBags_int_String() {
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
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBags(" + p.a + ", " + p.b + ") = " + sits(p.a.stringBags(p.b)));
        }
    }

    private void demoBags() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bags(" + p.a + ", " + its(p.b) + ") = " + its(p.a.bags(p.b)));
        }
    }

    private void demoStringBags_String() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBags(" + p.a + ", " + nicePrint(p.b) + ") = " + sits(p.a.stringBags(p.b)));
        }
    }

    private void demoStringBags() {
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("stringBags(" + rp + ") = " + sits(rp.stringBags()));
        }
    }

    private void demoBagsAtLeast() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Triple<RandomProvider, Integer, String>> ts = filterInfinite(
                t -> t.a.getScale() > t.b,
                P.triples(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
                        P.withScale(4).naturalIntegersGeometric()
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBagsAtLeast(" + p.a + ", " + p.b + ") = " + sits(p.a.stringBagsAtLeast(p.b)));
        }
    }

    private void demoStringSubsets_int_String() {
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairsLogarithmicOrder(
                P.randomProvidersDefault(),
                filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.a + ", " + p.b + ") = " + sits(p.a.stringSubsets(p.b)));
        }
    }

    private void demoSubsets() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).repeatingIterables(P.naturalIntegers())
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("subsets(" + p.a + ", " + its(p.b) + ") = " + its(p.a.subsets(p.b)));
        }
    }

    private void demoStringSubsets_String() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 0,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringSubsets(p.b)));
        }
    }

    private void demoStringSubsets() {
        Iterable<RandomProvider> rps = filterInfinite(
                s -> s.getScale() > 0,
                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("stringSubsets(" + rp + ") = " + sits(rp.stringSubsets()));
        }
    }

    private void demoSubsetsAtLeast() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Triple<RandomProvider, Integer, String>> ts = map(
                p -> new Triple<>(p.a.a, p.a.b, p.b),
                P.dependentPairsInfinite(
                        filterInfinite(
                                p -> p.a.getScale() > p.b,
                                P.pairs(
                                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b,
                P.pairs(
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale(),
                        filterInfinite(i -> i <= (1 << 16), P.withScale(4).naturalIntegersGeometric())
                )
        );
        for (Pair<RandomProvider, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsetsAtLeast(" + p.a + ", " + p.b + ") = " +
                    sits(p.a.stringSubsetsAtLeast(p.b)));
        }
    }

    private void demoEithers() {
        Iterable<Triple<RandomProvider, Iterable<Integer>, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.naturalIntegers()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Iterable<Integer>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("eithers(" + t.a + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(t.a.eithers(t.b, t.c)));
        }
    }

    private void demoChoose() {
        Iterable<Triple<RandomProvider, Iterable<Integer>, Iterable<Integer>>> ts = P.triples(
                filterInfinite(rp -> rp.getScale() > 0, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.naturalIntegers()),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Iterable<Integer>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("choose(" + t.a + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(t.a.choose(t.b, t.c)));
        }
    }

    private void demoCartesianProduct() {
        Iterable<Pair<RandomProvider, List<List<Integer>>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).listsAtLeast(1, P.withScale(4).listsAtLeast(1, P.withNull(P.integersGeometric())))
        );
        for (Pair<RandomProvider, List<List<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("cartesianProduct(" + p.a + ", " + p.b + ") = " + its(p.a.cartesianProduct(p.b)));
        }
    }

    private void demoRepeatingIterables() {
        Iterable<Pair<RandomProvider, Iterable<Integer>>> ps = P.pairs(
                filterInfinite(rp -> rp.getScale() > 1, P.randomProvidersDefaultSecondaryAndTertiaryScale()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<RandomProvider, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("repeatingIterables(" + p.a + ", " + its(p.b) + ") = " +
                    its(map(Testing::its, p.a.repeatingIterables(p.b))));
        }
    }

    private void demoRepeatingIterablesDistinctAtLeast() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = map(
                p -> new Triple<>(p.a, p.b.b, p.b.a),
                filterInfinite(
                        p -> p.a.getScale() > p.b.b,
                        P.pairs(
                                P.randomProvidersDefaultSecondaryAndTertiaryScale(),
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
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("sublists(" + p.a + ", " + p.b + ") = " + its(p.a.sublists(p.b)));
        }
    }

    private void demoSubstrings() {
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.withScale(4).strings());
        for (Pair<RandomProvider, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("substrings(" + p.a + ", " + nicePrint(p.b) + ") = " + its(p.a.substrings(p.b)));
        }
    }

    private void demoListsWithElement() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(
                        rp -> rp.getScale() >= 3,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("listsWithElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.listsWithElement(t.b, t.c)));
        }
    }

    private void demoStringsWithChar_char_String() {
        Iterable<Triple<RandomProvider, Character, String>> ts = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() >= 3,
                                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
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
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() >= 3,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsWithChar(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringsWithChar(p.b)));
        }
    }

    private void demoSubsetsWithElement() {
        Iterable<Triple<RandomProvider, Integer, Iterable<Integer>>> ts = P.triples(
                filterInfinite(
                        rp -> rp.getScale() >= 2,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Triple<RandomProvider, Integer, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("subsetsWithElement(" + t.a + ", " + t.b + ", " + its(t.c) + ") = " +
                    its(t.a.subsetsWithElement(t.b, t.c)));
        }
    }

    private void demoStringSubsetsWithChar_char_String() {
        Iterable<Triple<RandomProvider, Character, String>> ts = filterInfinite(
                t -> nub(t.c).length() != 1 || head(t.c) != t.b,
                P.triples(
                        filterInfinite(
                                rp -> rp.getScale() >= 2,
                                P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
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
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() >= 2,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.characters()
        );
        for (Pair<RandomProvider, Character> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringSubsetsWithChar(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    sits(p.a.stringSubsetsWithChar(p.b)));
        }
    }

    private void demoListsWithSublists() {
        Iterable<Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>>> ts = P.triples(
                filterInfinite(
                        rp -> rp.getScale() > 1,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.prefixPermutations(EP.lists(EP.withNull(EP.naturalIntegers()))),
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<RandomProvider, Iterable<List<Integer>>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("listsWithSublists(" + t.a + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(t.a.listsWithSublists(t.b, t.c)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String_String() {
        Iterable<Triple<RandomProvider, Iterable<String>, String>> ts = P.triples(
                filterInfinite(
                        rp -> rp.getScale() > 1,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.prefixPermutations(EP.strings()),
                P.withScale(4).stringsAtLeast(1)
        );
        for (Triple<RandomProvider, Iterable<String>, String> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("stringsWithSubstrings(" + t.a + ", " + sits(t.b) + ", " + nicePrint(t.c) + ") = " +
                    sits(t.a.stringsWithSubstrings(t.b, t.c)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String() {
        Iterable<Pair<RandomProvider, Iterable<String>>> ps = P.pairs(
                filterInfinite(
                        rp -> rp.getScale() > 1,
                        P.withScale(4).randomProvidersDefaultSecondaryAndTertiaryScale()
                ),
                P.prefixPermutations(EP.strings())
        );
        for (Pair<RandomProvider, Iterable<String>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsWithSubstrings(" + p.a + ", " + sits(p.b) + ") = " +
                    sits(p.a.stringsWithSubstrings(p.b)));
        }
    }

    private void demoMaps() {
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
        Iterable<Quadruple<RandomProvider, Integer, Integer, Integer>> qs = P.quadruples(
                P.randomProvidersDefault(),
                P.integersGeometric(),
                P.integersGeometric(),
                P.integersGeometric()
        );
        for (Quadruple<RandomProvider, Integer, Integer, Integer> q : take(SMALL_LIMIT, qs)) {
            System.out.println("randomProvidersFixedScales(" + q.a + ", " + q.b + ", " + q.c + ") = " +
                    its(q.a.randomProvidersFixedScales(q.b, q.c, q.d)));
        }
    }

    private void demoRandomProvidersDefault() {
        for (RandomProvider rp : take(MEDIUM_LIMIT, P.randomProvidersDefault())) {
            System.out.println("randomProvidersDefault(" + rp + ") = " + its(rp.randomProvidersDefault()));
        }
    }

    private void demoRandomProvidersDefaultSecondaryAndTertiaryScale() {
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("randomProvidersDefaultSecondaryAndTertiaryScale(" + rp + ") = " +
                    its(rp.randomProvidersDefaultSecondaryAndTertiaryScale()));
        }
    }

    private void demoRandomProvidersDefaultTertiaryScale() {
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("randomProvidersDefaultTertiaryScale(" + rp + ") = " +
                    its(rp.randomProvidersDefaultTertiaryScale()));
        }
    }

    private void demoRandomProviders() {
        Iterable<RandomProvider> rps = filterInfinite(
                rp -> rp.getScale() > 0,
                P.randomProvidersDefaultSecondaryAndTertiaryScale()
        );
        for (RandomProvider rp : take(MEDIUM_LIMIT, rps)) {
            System.out.println("randomProviders(" + rp + ") = " + its(rp.randomProviders()));
        }
    }

    private void demoEquals_RandomProvider() {
        for (Pair<RandomProvider, RandomProvider> p : take(LIMIT, P.pairs(P.randomProviders()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            //noinspection ObjectEqualsNull
            System.out.println(r + (r.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            System.out.println("hashCode(" + r + ") = " + r.hashCode());
        }
    }

    private void demoToString() {
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println(rp);
        }
    }
}
