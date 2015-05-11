package mho.wheels.iterables;

import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class RandomProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static final int SMALL_LIMIT = 1000;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoConstructor() {
        initialize();
        for (Void v : take(LIMIT, repeat((Void) null))) {
            System.out.println("RandomProvider() = " + new RandomProvider());
        }
    }

    private static void demoConstructor_List_Integer() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            System.out.println("RandomProvider(" + is + ") = " + new RandomProvider(is));
        }
    }

    private static void demoGetScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getScale(" + rp + ") = " + rp.getScale());
        }
    }

    private static void demoGetSecondaryScale() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSecondaryScale(" + rp + ") = " + rp.getSecondaryScale());
        }
    }

    private static void demoGetSeed() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getSeed(" + rp + ") = " + rp.getSeed());
        }
    }

    private static void demoWithScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withScale(" + p.a + ", " + p.b + ") = " + p.a.withScale(p.b));
        }
    }

    private static void demoWithSecondaryScale() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            System.out.println("withSecondaryScale(" + p.a + ", " + p.b + ") = " + p.a.withSecondaryScale(p.b));
        }
    }

    private static void demoCopy() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("copy(" + rp + ") = " + rp.copy());
        }
    }

    private static void demoDeepCopy() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("deepCopy(" + rp + ") = " + rp.deepCopy());
        }
    }

    private static void demoReset() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            rp.nextInt();
            RandomProvider beforeReset = rp.deepCopy();
            rp.reset();
            System.out.println("reset(" + beforeReset + ") = " + rp);
        }
    }

    private static void demoGetId() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println("getId(" + rp + ") = " + rp.getId());
        }
    }

    private static void demoNextInt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextInt(" + rp + ") = " + rp.nextInt());
        }
    }

    private static void demoIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("integers(" + rp + ") = " + its(rp.integers()));
        }
    }

    private static void demoNextLong() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextLong(" + rp + ") = " + rp.nextLong());
        }
    }

    private static void demoLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("longs(" + rp + ") = " + its(rp.longs()));
        }
    }

    private static void demoNextBoolean() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextBoolean(" + rp + ") = " + rp.nextBoolean());
        }
    }

    private static void demoBooleans() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("booleans(" + rp + ") = " + its(rp.booleans()));
        }
    }

    private static void demoNextUniformSample_Iterable() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.listsAtLeast(1, P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextUniformSample(" + p.a + ", " + p.b.toString() + ") = " +
                    p.a.nextUniformSample(p.b));
        }
    }

    private static void demoUniformSample_Iterable() {
        initialize();
        Iterable<Pair<RandomProvider, List<Integer>>> ps = P.pairs(
                P.randomProvidersDefault(),
                P.lists(P.withNull(P.integers()))
        );
        for (Pair<RandomProvider, List<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " + p.b.toString() + ") = " + its(p.a.uniformSample(p.b)));
        }
    }

    private static void demoNextUniformSample_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.stringsAtLeast(1));
        for (Pair<RandomProvider, String> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextUniformSample(" + p.a + ", " +  nicePrint(p.b) + ") = " +
                    nicePrint(p.a.nextUniformSample(p.b)));
        }
    }

    private static void demoUniformSample_String() {
        initialize();
        Iterable<Pair<RandomProvider, String>> ps = P.pairs(P.randomProvidersDefault(), P.strings());
        for (Pair<RandomProvider, String> p : take(SMALL_LIMIT, ps)) {
            System.out.println("uniformSample(" + p.a + ", " +  nicePrint(p.b) + ") = " +
                    cits(p.a.uniformSample(p.b)));
        }
    }

    private static void demoNextOrdering() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextOrdering(" + rp + ") = " + rp.nextOrdering());
        }
    }

    private static void demoOrderings() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("orderings(" + rp + ") = " + its(rp.orderings()));
        }
    }

    private static void demoNextRoundingMode() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextRoundingMode(" + rp + ") = " + rp.nextRoundingMode());
        }
    }

    private static void demoRoundingModes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("roundingModes(" + rp + ") = " + its(rp.roundingModes()));
        }
    }

    private static void demoNextPositiveByte() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveByte(" + rp + ") = " + rp.nextPositiveByte());
        }
    }

    private static void demoPositiveBytes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveBytes(" + rp + ") = " + its(rp.positiveBytes()));
        }
    }

    private static void demoNextPositiveShort() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveShort(" + rp + ") = " + rp.nextPositiveShort());
        }
    }

    private static void demoPositiveShorts() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveShorts(" + rp + ") = " + its(rp.positiveShorts()));
        }
    }

    private static void demoNextPositiveInt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveInt(" + rp + ") = " + rp.nextPositiveInt());
        }
    }

    private static void demoPositiveIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveIntegers(" + rp + ") = " + its(rp.positiveIntegers()));
        }
    }

    private static void demoNextPositiveLong() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveLong(" + rp + ") = " + rp.nextPositiveLong());
        }
    }

    private static void demoPositiveLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveLongs(" + rp + ") = " + its(rp.positiveLongs()));
        }
    }

    private static void demoNextNegativeByte() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeByte(" + rp + ") = " + rp.nextNegativeByte());
        }
    }

    private static void demoNegativeBytes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeBytes(" + rp + ") = " + its(rp.negativeBytes()));
        }
    }

    private static void demoNextNegativeShort() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeShort(" + rp + ") = " + rp.nextNegativeShort());
        }
    }

    private static void demoNegativeShorts() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeShorts(" + rp + ") = " + its(rp.negativeShorts()));
        }
    }

    private static void demoNextNegativeInt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeInt(" + rp + ") = " + rp.nextNegativeInt());
        }
    }

    private static void demoNegativeIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeIntegers(" + rp + ") = " + its(rp.negativeIntegers()));
        }
    }

    private static void demoNextNegativeLong() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeLong(" + rp + ") = " + rp.nextNegativeLong());
        }
    }

    private static void demoNegativeLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeLongs(" + rp + ") = " + its(rp.negativeLongs()));
        }
    }

    private static void demoNextNaturalByte() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNaturalByte(" + rp + ") = " + rp.nextNaturalByte());
        }
    }

    private static void demoNaturalBytes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalBytes(" + rp + ") = " + its(rp.naturalBytes()));
        }
    }

    private static void demoNextNaturalShort() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNaturalShort(" + rp + ") = " + rp.nextNaturalShort());
        }
    }

    private static void demoNaturalShorts() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalShorts(" + rp + ") = " + its(rp.naturalShorts()));
        }
    }

    private static void demoNextNaturalInt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNaturalInt(" + rp + ") = " + rp.nextNaturalInt());
        }
    }

    private static void demoNaturalIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalIntegers(" + rp + ") = " + its(rp.naturalIntegers()));
        }
    }

    private static void demoNextNaturalLong() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNaturalLong(" + rp + ") = " + rp.nextNaturalLong());
        }
    }

    private static void demoNaturalLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("naturalLongs(" + rp + ") = " + its(rp.naturalLongs()));
        }
    }

    private static void demoNextNonzeroByte() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroByte(" + rp + ") = " + rp.nextNonzeroByte());
        }
    }

    private static void demoNonzeroBytes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroBytes(" + rp + ") = " + its(rp.nonzeroBytes()));
        }
    }

    private static void demoNextNonzeroShort() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroShort(" + rp + ") = " + rp.nextNonzeroShort());
        }
    }

    private static void demoNonzeroShorts() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroShorts(" + rp + ") = " + its(rp.nonzeroShorts()));
        }
    }

    private static void demoNextNonzeroInt() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroInt(" + rp + ") = " + rp.nextNonzeroInt());
        }
    }

    private static void demoNonzeroIntegers() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroIntegers(" + rp + ") = " + its(rp.nonzeroIntegers()));
        }
    }

    private static void demoNextNonzeroLong() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroLong(" + rp + ") = " + rp.nextNonzeroLong());
        }
    }

    private static void demoNonzeroLongs() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroLongs(" + rp + ") = " + its(rp.nonzeroLongs()));
        }
    }

    private static void demoNextByte() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextByte(" + rp + ") = " + rp.nextByte());
        }
    }

    private static void demoBytes() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("bytes(" + rp + ") = " + its(rp.bytes()));
        }
    }

    private static void demoNextShort() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextShort(" + rp + ") = " + rp.nextShort());
        }
    }

    private static void demoShorts() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("shorts(" + rp + ") = " + its(rp.shorts()));
        }
    }

    private static void demoAsciiCharacters() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("asciiCharacters(" + rp + ") = " + cits(rp.asciiCharacters()));
        }
    }

    private static void demoCharacters() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("characters(" + rp + ") = " + cits(rp.characters()));
        }
    }

    private static void demoRangeUp_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeUp_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeUp(p.b)));
        }
    }

    private static void demoRangeDown_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRangeDown_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeDown(p.b)));
        }
    }

    private static void demoRange_byte_byte() {
        initialize();
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_short_short() {
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

    private static void demoRange_int_int() {
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

    private static void demoRange_long_long() {
        initialize();
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoRange_BigInteger_BigInteger() {
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

    private static void demoRange_char_char() {
        initialize();
        Iterable<Triple<RandomProvider, Character, Character>> ts = P.triples(
                P.randomProvidersDefault(),
                P.characters(),
                P.characters()
        );
        for (Triple<RandomProvider, Character, Character> p : take(SMALL_LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + cits(p.a.range(p.b, p.c)));
        }
    }

    private static void demoPositiveIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("positiveIntegersGeometric(" + rp + ") = " + its(rp.positiveIntegersGeometric()));
        }
    }

    private static void demoNegativeIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("negativeIntegersGeometric(" + rp + ") = " + its(rp.negativeIntegersGeometric()));
        }
    }

    private static void demoNaturalIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("naturalIntegersGeometric(" + rp + ") = " + its(rp.naturalIntegersGeometric()));
        }
    }

    private static void demoNonzeroIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nonzeroIntegersGeometric(" + rp + ") = " + its(rp.nonzeroIntegersGeometric()));
        }
    }

    private static void demoIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filter(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("integersGeometric(" + rp + ") = " + its(rp.integersGeometric()));
        }
    }

    private static void demoRangeUpGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() > p.b && (p.b > 1 || p.a.getScale() >= Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpGeometric(p.b)));
        }
    }

    private static void demoRangeDownGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filter(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownGeometric(p.b)));
        }
    }

    private static void demoPositiveBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("positiveBigIntegers(" + rp + ") = " + its(rp.positiveBigIntegers()));
        }
    }

    private static void demoNegativeBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("negativeBigIntegers(" + rp + ") = " + its(rp.negativeBigIntegers()));
        }
    }

    private static void demoNaturalBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() > 0, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("naturalBigIntegers(" + rp + ") = " + its(rp.naturalBigIntegers()));
        }
    }

    private static void demoNonzeroBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() >= 2, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nonzeroBigIntegers(" + rp + ") = " + its(rp.nonzeroBigIntegers()));
        }
    }

    private static void demoBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filter(x -> x.getScale() > 0, P.randomProvidersDefaultSecondaryScale());
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("bigIntegers(" + rp + ") = " + its(rp.bigIntegers()));
        }
    }

    private static void demoEquals_RandomProvider() {
        initialize();
        for (Pair<RandomProvider, RandomProvider> p : take(LIMIT, P.pairs(P.randomProviders()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private static void demoEquals_null() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            //noinspection ObjectEqualsNull
            System.out.println(r + (r.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private static void demoHashCode() {
        initialize();
        for (RandomProvider r : take(LIMIT, P.randomProviders())) {
            System.out.println("hashCode(" + r + ") = " + r.hashCode());
        }
    }

    private static void demoToString() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            System.out.println(rp);
        }
    }
}
