package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class RandomProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;
    private static final int SMALL_LIMIT = 1000;
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

    private static void demoNextAsciiChar() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextAsciiChar(" + rp + ") = " + nicePrint(rp.nextAsciiChar()));
        }
    }

    private static void demoAsciiCharacters() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("asciiCharacters(" + rp + ") = " + cits(rp.asciiCharacters()));
        }
    }

    private static void demoNextChar() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextChar(" + rp + ") = " + nicePrint(rp.nextChar()));
        }
    }

    private static void demoCharacters() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("characters(" + rp + ") = " + cits(rp.characters()));
        }
    }

    private static void demoNextFromRangeUp_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeUp_short() {
        initialize();
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeUp_int() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeUp_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeUp_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    nicePrint(p.a.nextFromRangeUp(p.b)));
        }
    }

    private static void demoRangeUp_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeDown_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_byte() {
        initialize();
        for (Pair<RandomProvider, Byte> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.bytes()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRangeDown_short() {
        initialize();
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.shorts()))) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_short() {
        initialize();
        Iterable<Pair<RandomProvider, Short>> ps = P.pairs(P.randomProvidersDefault(), P.shorts());
        for (Pair<RandomProvider, Short> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRangeDown_int() {
        initialize();
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.integers()))) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_int() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = P.pairs(P.randomProvidersDefault(), P.integers());
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRangeDown_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_long() {
        initialize();
        for (Pair<RandomProvider, Long> p : take(SMALL_LIMIT, P.pairs(P.randomProvidersDefault(), P.longs()))) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRangeDown_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " +
                    nicePrint(p.a.nextFromRangeDown(p.b)));
        }
    }

    private static void demoRangeDown_char() {
        initialize();
        Iterable<Pair<RandomProvider, Character>> ps = P.pairs(P.randomProvidersDefault(), P.characters());
        for (Pair<RandomProvider, Character> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + nicePrint(p.b) + ") = " + cits(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRange_byte_byte() {
        initialize();
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes())
        );
        for (Triple<RandomProvider, Byte, Byte> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + p.b + ", " + p.c + ") = " +
                    p.a.nextFromRange(p.b, p.c));
        }
    }

    private static void demoRange_byte_byte() {
        initialize();
        Iterable<Triple<RandomProvider, Byte, Byte>> ts = P.triples(P.randomProvidersDefault(), P.bytes(), P.bytes());
        for (Triple<RandomProvider, Byte, Byte> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoNextFromRange_short_short() {
        initialize();
        Iterable<Triple<RandomProvider, Short, Short>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.shorts(), P.shorts())
        );
        for (Triple<RandomProvider, Short, Short> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + p.b + ", " + p.c + ") = " +
                    p.a.nextFromRange(p.b, p.c));
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

    private static void demoNextFromRange_int_int() {
        initialize();
        Iterable<Triple<RandomProvider, Integer, Integer>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.integers(), P.integers())
        );
        for (Triple<RandomProvider, Integer, Integer> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + p.b + ", " + p.c + ") = " +
                    p.a.nextFromRange(p.b, p.c));
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

    private static void demoNextFromRange_long_long() {
        initialize();
        Iterable<Triple<RandomProvider, Long, Long>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.longs(), P.longs())
        );
        for (Triple<RandomProvider, Long, Long> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + p.b + ", " + p.c + ") = " +
                    p.a.nextFromRange(p.b, p.c));
        }
    }

    private static void demoRange_long_long() {
        initialize();
        Iterable<Triple<RandomProvider, Long, Long>> ts = P.triples(P.randomProvidersDefault(), P.longs(), P.longs());
        for (Triple<RandomProvider, Long, Long> p : take(LIMIT, ts)) {
            System.out.println("range(" + p.a + ", " + p.b + ", " + p.c + ") = " + its(p.a.range(p.b, p.c)));
        }
    }

    private static void demoNextFromRange_BigInteger_BigInteger() {
        initialize();
        Iterable<Triple<RandomProvider, BigInteger, BigInteger>> ts = filterInfinite(
                t -> le(t.b, t.c),
                P.triples(P.randomProvidersDefault(), P.bigIntegers(), P.bigIntegers())
        );
        for (Triple<RandomProvider, BigInteger, BigInteger> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + p.b + ", " + p.c + ") = " +
                    p.a.nextFromRange(p.b, p.c));
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

    private static void demoNextFromRange_char_char() {
        initialize();
        Iterable<Triple<RandomProvider, Character, Character>> ts = filterInfinite(
                t -> t.b <= t.c,
                P.triples(P.randomProvidersDefault(), P.characters(), P.characters())
        );
        for (Triple<RandomProvider, Character, Character> p : take(SMALL_LIMIT, ts)) {
            System.out.println("nextFromRange(" + p.a + ", " + nicePrint(p.b) + ", " + nicePrint(p.c) + ") = " +
                    nicePrint(p.a.nextFromRange(p.b, p.c)));
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

    private static void demoNextPositiveIntGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextPositiveIntGeometric(" + rp + ") = " + rp.nextPositiveIntGeometric());
        }
    }

    private static void demoPositiveIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("positiveIntegersGeometric(" + rp + ") = " + its(rp.positiveIntegersGeometric()));
        }
    }

    private static void demoNextNegativeIntGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNegativeIntGeometric(" + rp + ") = " + rp.nextNegativeIntGeometric());
        }
    }

    private static void demoNegativeIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("negativeIntegersGeometric(" + rp + ") = " + its(rp.negativeIntegersGeometric()));
        }
    }

    private static void demoNextNaturalIntGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNaturalIntGeometric(" + rp + ") = " + rp.nextNaturalIntGeometric());
        }
    }

    private static void demoNaturalIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("naturalIntegersGeometric(" + rp + ") = " + its(rp.naturalIntegersGeometric()));
        }
    }

    private static void demoNextNonzeroIntGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNonzeroIntGeometric(" + rp + ") = " + rp.nextNonzeroIntGeometric());
        }
    }

    private static void demoNonzeroIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nonzeroIntegersGeometric(" + rp + ") = " + its(rp.nonzeroIntegersGeometric()));
        }
    }

    private static void demoNextIntGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextIntGeometric(" + rp + ") = " + rp.nextIntGeometric());
        }
    }

    private static void demoIntegersGeometric() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("integersGeometric(" + rp + ") = " + its(rp.integersGeometric()));
        }
    }

    private static void demoNextIntGeometricFromRangeUp() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b > 1 || p.a.getScale() >= Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextIntGeometricFromRangeUp(" + p.a + ", " + p.b + ") = " +
                    p.a.nextIntGeometricFromRangeUp(p.b));
        }
    }

    private static void demoRangeUpGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() > p.b && (p.b > 1 || p.a.getScale() >= Integer.MAX_VALUE + p.b),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpGeometric(p.b)));
        }
    }

    private static void demoNextIntGeometricFromRangeDown() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextIntGeometricFromRangeDown(" + p.a + ", " + p.b + ") = " +
                    p.a.nextIntGeometricFromRangeDown(p.b));
        }
    }

    private static void demoRangeDownGeometric() {
        initialize();
        Iterable<Pair<RandomProvider, Integer>> ps = filterInfinite(
                p -> p.a.getScale() < p.b && (p.b <= -1 || p.a.getScale() > p.b - Integer.MAX_VALUE),
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.integersGeometric())
        );
        for (Pair<RandomProvider, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownGeometric(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownGeometric(p.b)));
        }
    }

    private static void demoNextPositiveBigInteger() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextPositiveBigInteger(" + rp + ") = " + rp.nextPositiveBigInteger());
        }
    }

    private static void demoPositiveBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("positiveBigIntegers(" + rp + ") = " + its(rp.positiveBigIntegers()));
        }
    }

    private static void demoNextNegativeBigInteger() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNegativeBigInteger(" + rp + ") = " + rp.nextNegativeBigInteger());
        }
    }

    private static void demoNegativeBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("negativeBigIntegers(" + rp + ") = " + its(rp.negativeBigIntegers()));
        }
    }

    private static void demoNextNaturalBigInteger() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNaturalBigInteger(" + rp + ") = " + rp.nextNaturalBigInteger());
        }
    }

    private static void demoNaturalBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("naturalBigIntegers(" + rp + ") = " + its(rp.naturalBigIntegers()));
        }
    }

    private static void demoNextNonzeroBigInteger() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextNonzeroBigInteger(" + rp + ") = " + rp.nextNonzeroBigInteger());
        }
    }

    private static void demoNonzeroBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nonzeroBigIntegers(" + rp + ") = " + its(rp.nonzeroBigIntegers()));
        }
    }

    private static void demoNextBigInteger() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nextBigInteger(" + rp + ") = " + rp.nextBigInteger());
        }
    }

    private static void demoBigIntegers() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                P.randomProvidersDefaultSecondaryScale()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("bigIntegers(" + rp + ") = " + its(rp.bigIntegers()));
        }
    }

    private static void demoNextFromRangeUp_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == -1 ? 0 : p.b.bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeDown_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(SMALL_LIMIT, ps)) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_BigInteger() {
        initialize();
        Iterable<Pair<RandomProvider, BigInteger>> ps = filterInfinite(
                p -> {
                    int minBitLength = p.b.signum() == 1 ? 0 : p.b.negate().bitLength();
                    return p.a.getScale() > minBitLength && (minBitLength == 0 || p.a.getScale() != Integer.MAX_VALUE);
                },
                P.pairs(P.randomProvidersDefaultSecondaryScale(), P.bigIntegers())
        );
        for (Pair<RandomProvider, BigInteger> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextPositiveBinaryFraction() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            System.out.println("nextPositiveBinaryFraction(" + rp + ") = " + rp.nextPositiveBinaryFraction());
        }
    }

    private static void demoPositiveBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("positiveBinaryFractions(" + rp + ") = " + its(rp.positiveBinaryFractions()));
        }
    }

    private static void demoNextNegativeBinaryFraction() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            System.out.println("nextNegativeBinaryFraction(" + rp + ") = " + rp.nextNegativeBinaryFraction());
        }
    }

    private static void demoNegativeBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("negativeBinaryFractions(" + rp + ") = " + its(rp.negativeBinaryFractions()));
        }
    }

    private static void demoNextNonzeroBinaryFraction() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            System.out.println("nextNonzeroBinaryFraction(" + rp + ") = " + rp.nextNonzeroBinaryFraction());
        }
    }

    private static void demoNonzeroBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() >= 2 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("nonzeroBinaryFractions(" + rp + ") = " + its(rp.nonzeroBinaryFractions()));
        }
    }

    private static void demoNextBinaryFraction() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(LIMIT, rps)) {
            System.out.println("nextBinaryFraction(" + rp + ") = " + rp.nextBinaryFraction());
        }
    }

    private static void demoBinaryFractions() {
        initialize();
        Iterable<RandomProvider> rps = filterInfinite(
                x -> x.getScale() > 0 && x.getSecondaryScale() > 0,
                P.randomProviders()
        );
        for (RandomProvider rp : take(SMALL_LIMIT, rps)) {
            System.out.println("binaryFractions(" + rp + ") = " + its(rp.binaryFractions()));
        }
    }

    private static void demoNextFromRangeUp_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeDown_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_BinaryFraction() {
        initialize();
        Iterable<Pair<RandomProvider, BinaryFraction>> ps = P.pairs(
                filterInfinite(x -> x.getScale() > 0 && x.getSecondaryScale() > 0, P.randomProviders()),
                P.binaryFractions()
        );
        for (Pair<RandomProvider, BinaryFraction> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRange_BinaryFraction_BinaryFraction() {
        initialize();
        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = filterInfinite(
                t -> lt(t.b, t.c),
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
            System.out.println("nextFromRange(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    t.a.nextFromRange(t.b, t.c));
        }
    }

    private static void demoRange_BinaryFraction_BinaryFraction() {
        initialize();
        Iterable<Triple<RandomProvider, BinaryFraction, BinaryFraction>> ts = P.triples(
                filterInfinite(
                        x -> x.getScale() > 0 && x.getScale() != Integer.MAX_VALUE,
                        P.randomProvidersDefaultSecondaryScale()
                ),
                P.binaryFractions(),
                P.binaryFractions()
        );
        for (Triple<RandomProvider, BinaryFraction, BinaryFraction> t : take(SMALL_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private static void demoNextPositiveFloat() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveFloat(" + rp + ") = " + rp.nextPositiveFloat());
        }
    }

    private static void demoPositiveFloats() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloats(" + rp + ") = " + its(rp.positiveFloats()));
        }
    }

    private static void demoNextNegativeFloat() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeFloat(" + rp + ") = " + rp.nextNegativeFloat());
        }
    }

    private static void demoNegativeFloats() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloats(" + rp + ") = " + its(rp.negativeFloats()));
        }
    }

    private static void demoNextNonzeroFloat() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroFloat(" + rp + ") = " + rp.nextNonzeroFloat());
        }
    }

    private static void demoNonzeroFloats() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloats(" + rp + ") = " + its(rp.nonzeroFloats()));
        }
    }

    private static void demoNextFloat() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextFloat(" + rp + ") = " + rp.nextFloat());
        }
    }

    private static void demoFloats() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floats(" + rp + ") = " + its(rp.floats()));
        }
    }

    private static void demoNextPositiveDouble() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveDouble(" + rp + ") = " + rp.nextPositiveDouble());
        }
    }

    private static void demoPositiveDoubles() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoubles(" + rp + ") = " + its(rp.positiveDoubles()));
        }
    }

    private static void demoNextNegativeDouble() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeDouble(" + rp + ") = " + rp.nextNegativeDouble());
        }
    }

    private static void demoNegativeDoubles() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoubles(" + rp + ") = " + its(rp.negativeDoubles()));
        }
    }

    private static void demoNextNonzeroDouble() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroDouble(" + rp + ") = " + rp.nextNonzeroDouble());
        }
    }

    private static void demoNonzeroDoubles() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoubles(" + rp + ") = " + its(rp.nonzeroDoubles()));
        }
    }

    private static void demoNextDouble() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextDouble(" + rp + ") = " + rp.nextDouble());
        }
    }

    private static void demoDoubles() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doubles(" + rp + ") = " + its(rp.doubles()));
        }
    }

    private static void demoNextPositiveFloatUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveFloatUniform(" + rp + ") = " + rp.nextPositiveFloatUniform());
        }
    }

    private static void demoPositiveFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveFloatsUniform(" + rp + ") = " + its(rp.positiveFloatsUniform()));
        }
    }

    private static void demoNextNegativeFloatUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeFloatUniform(" + rp + ") = " + rp.nextNegativeFloatUniform());
        }
    }

    private static void demoNegativeFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeFloatsUniform(" + rp + ") = " + its(rp.negativeFloatsUniform()));
        }
    }

    private static void demoNextNonzeroFloatUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroFloatUniform(" + rp + ") = " + rp.nextNonzeroFloatUniform());
        }
    }

    private static void demoNonzeroFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroFloatsUniform(" + rp + ") = " + its(rp.nonzeroFloatsUniform()));
        }
    }

    private static void demoNextFloatUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextFloatUniform(" + rp + ") = " + rp.nextFloatUniform());
        }
    }

    private static void demoFloatsUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("floatsUniform(" + rp + ") = " + its(rp.floatsUniform()));
        }
    }

    private static void demoNextPositiveDoubleUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextPositiveDoubleUniform(" + rp + ") = " + rp.nextPositiveDoubleUniform());
        }
    }

    private static void demoPositiveDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("positiveDoublesUniform(" + rp + ") = " + its(rp.positiveDoublesUniform()));
        }
    }

    private static void demoNextNegativeDoubleUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNegativeDoubleUniform(" + rp + ") = " + rp.nextNegativeDoubleUniform());
        }
    }

    private static void demoNegativeDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("negativeDoublesUniform(" + rp + ") = " + its(rp.negativeDoublesUniform()));
        }
    }

    private static void demoNextNonzeroDoubleUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextNonzeroDoubleUniform(" + rp + ") = " + rp.nextNonzeroDoubleUniform());
        }
    }

    private static void demoNonzeroDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("nonzeroDoublesUniform(" + rp + ") = " + its(rp.nonzeroDoublesUniform()));
        }
    }

    private static void demoNextDoubleUniform() {
        initialize();
        for (RandomProvider rp : take(LIMIT, P.randomProvidersDefault())) {
            System.out.println("nextDoubleUniform(" + rp + ") = " + rp.nextDoubleUniform());
        }
    }

    private static void demoDoublesUniform() {
        initialize();
        for (RandomProvider rp : take(SMALL_LIMIT, P.randomProvidersDefault())) {
            System.out.println("doublesUniform(" + rp + ") = " + its(rp.doublesUniform()));
        }
    }

    private static void demoNextFromRangeUp_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeDown_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRange_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = filter(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(f -> !Float.isNaN(f), P.floats()),
                        filter(f -> !Float.isNaN(f), P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            System.out.println("nextFromRange(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    t.a.nextFromRange(t.b, t.c));
        }
    }

    private static void demoRange_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(f -> !Float.isNaN(f), P.floats()),
                filter(f -> !Float.isNaN(f), P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(SMALL_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private static void demoNextFromRangeUp_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeUp(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeUp(p.b));
        }
    }

    private static void demoRangeUp_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUp(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUp(p.b)));
        }
    }

    private static void demoNextFromRangeDown_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeDown(" + p.a + ", " + p.b + ") = " + p.a.nextFromRangeDown(p.b));
        }
    }

    private static void demoRangeDown_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDown(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDown(p.b)));
        }
    }

    private static void demoNextFromRange_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = filter(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(d -> !Double.isNaN(d), P.doubles()),
                        filter(d -> !Double.isNaN(d), P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            System.out.println("nextFromRange(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    t.a.nextFromRange(t.b, t.c));
        }
    }

    private static void demoRange_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(d -> !Double.isNaN(d), P.doubles()),
                filter(d -> !Double.isNaN(d), P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(SMALL_LIMIT, ts)) {
            System.out.println("range(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(t.a.range(t.b, t.c)));
        }
    }

    private static void demoNextFromRangeUpUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeUpUniform(" + p.a + ", " + p.b + ") = " +
                    p.a.nextFromRangeUpUniform(p.b));
        }
    }

    private static void demoRangeUpUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private static void demoNextFromRangeDownUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeDownUniform(" + p.a + ", " + p.b + ") = " +
                    p.a.nextFromRangeDownUniform(p.b));
        }
    }

    private static void demoRangeDownUniform_float() {
        initialize();
        Iterable<Pair<RandomProvider, Float>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats())
        );
        for (Pair<RandomProvider, Float> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private static void demoNextFromRangeUniform_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = filter(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Float::isFinite, P.floats()),
                        filter(Float::isFinite, P.floats())
                )
        );
        for (Triple<RandomProvider, Float, Float> t : take(LIMIT, ts)) {
            System.out.println("nextFromRangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    t.a.nextFromRangeUniform(t.b, t.c));
        }
    }

    private static void demoRangeUniform_float_float() {
        initialize();
        Iterable<Triple<RandomProvider, Float, Float>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Float::isFinite, P.floats()),
                filter(Float::isFinite, P.floats())
        );
        for (Triple<RandomProvider, Float, Float> t : take(SMALL_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
        }
    }

    private static void demoNextFromRangeUpUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeUpUniform(" + p.a + ", " + p.b + ") = " +
                    p.a.nextFromRangeUpUniform(p.b));
        }
    }

    private static void demoRangeUpUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeUpUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeUpUniform(p.b)));
        }
    }

    private static void demoNextFromRangeDownUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(LIMIT, ps)) {
            System.out.println("nextFromRangeDownUniform(" + p.a + ", " + p.b + ") = " +
                    p.a.nextFromRangeDownUniform(p.b));
        }
    }

    private static void demoRangeDownUniform_double() {
        initialize();
        Iterable<Pair<RandomProvider, Double>> ps = P.pairs(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles())
        );
        for (Pair<RandomProvider, Double> p : take(SMALL_LIMIT, ps)) {
            System.out.println("rangeDownUniform(" + p.a + ", " + p.b + ") = " + its(p.a.rangeDownUniform(p.b)));
        }
    }

    private static void demoNextFromRangeUniform_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = filter(
                t -> t.b <= t.c,
                P.triples(
                        P.randomProvidersDefault(),
                        filter(Double::isFinite, P.doubles()),
                        filter(Double::isFinite, P.doubles())
                )
        );
        for (Triple<RandomProvider, Double, Double> t : take(LIMIT, ts)) {
            System.out.println("nextFromRangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    t.a.nextFromRangeUniform(t.b, t.c));
        }
    }

    private static void demoRangeUniform_double_double() {
        initialize();
        Iterable<Triple<RandomProvider, Double, Double>> ts = P.triples(
                P.randomProvidersDefault(),
                filter(Double::isFinite, P.doubles()),
                filter(Double::isFinite, P.doubles())
        );
        for (Triple<RandomProvider, Double, Double> t : take(SMALL_LIMIT, ts)) {
            System.out.println("rangeUniform(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(t.a.rangeUniform(t.b, t.c)));
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
