package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.structures.*;
import mho.wheels.testing.Demos;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class ExhaustiveProviderDemos extends Demos {
    public ExhaustiveProviderDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoUniformSample_Iterable() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
            System.out.println("uniformSample(" + middle(is.toString()) + ") = " + its(EP.uniformSample(is)));
        }
    }

    private void demoUniformSample_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("uniformSample(" + nicePrint(s) + ") = " + cits(EP.uniformSample(s)));
        }
    }

    private void demoRangeUpIncreasing_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeUpIncreasing(" + b + ") = " + its(EP.rangeUpIncreasing(b)));
        }
    }

    private void demoRangeUpIncreasing_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeUpIncreasing(" + s + ") = " + its(EP.rangeUpIncreasing(s)));
        }
    }

    private void demoRangeUpIncreasing_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeUpIncreasing(" + i + ") = " + its(EP.rangeUpIncreasing(i)));
        }
    }

    private void demoRangeUpIncreasing_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeUpIncreasing(" + l + ") = " + its(EP.rangeUpIncreasing(l)));
        }
    }

    private void demoRangeUpIncreasing_BigInteger() {
        for (BigInteger i : take(MEDIUM_LIMIT, P.bigIntegers())) {
            System.out.println("rangeUpIncreasing(" + i + ") = " + its(EP.rangeUpIncreasing(i)));
        }
    }

    private void demoRangeUpIncreasing_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("rangeUpIncreasing(" + nicePrint(c) + ") = " + cits(EP.rangeUpIncreasing(c)));
        }
    }

    private void demoRangeUpDecreasing_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeUpDecreasing(" + b + ") = " + its(EP.rangeUpDecreasing(b)));
        }
    }

    private void demoRangeUpDecreasing_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeUpDecreasing(" + s + ") = " + its(EP.rangeUpDecreasing(s)));
        }
    }

    private void demoRangeUpDecreasing_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeUpDecreasing(" + i + ") = " + its(EP.rangeUpDecreasing(i)));
        }
    }

    private void demoRangeUpDecreasing_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeUpDecreasing(" + l + ") = " + its(EP.rangeUpDecreasing(l)));
        }
    }

    private void demoRangeUpDecreasing_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("rangeUpDecreasing(" + nicePrint(c) + ") = " + cits(EP.rangeUpDecreasing(c)));
        }
    }

    private void demoRangeDownIncreasing_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeDownIncreasing(" + b + ") = " + its(EP.rangeDownIncreasing(b)));
        }
    }

    private void demoRangeDownIncreasing_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeDownIncreasing(" + s + ") = " + its(EP.rangeDownIncreasing(s)));
        }
    }

    private void demoRangeDownIncreasing_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeDownIncreasing(" + i + ") = " + its(EP.rangeDownIncreasing(i)));
        }
    }

    private void demoRangeDownIncreasing_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeDownIncreasing(" + l + ") = " + its(EP.rangeDownIncreasing(l)));
        }
    }

    private void demoRangeDownIncreasing_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("rangeDownIncreasing(" + nicePrint(c) + ") = " + cits(EP.rangeDownIncreasing(c)));
        }
    }

    private void demoRangeDownDecreasing_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeDownDecreasing(" + b + ") = " + its(EP.rangeDownDecreasing(b)));
        }
    }

    private void demoRangeDownDecreasing_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeDownDecreasing(" + s + ") = " + its(EP.rangeDownDecreasing(s)));
        }
    }

    private void demoRangeDownDecreasing_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeDownDecreasing(" + i + ") = " + its(EP.rangeDownDecreasing(i)));
        }
    }

    private void demoRangeDownDecreasing_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeDownDecreasing(" + l + ") = " + its(EP.rangeDownDecreasing(l)));
        }
    }

    private void demoRangeDownDecreasing_BigInteger() {
        for (BigInteger i : take(MEDIUM_LIMIT, P.bigIntegers())) {
            System.out.println("rangeDownDecreasing(" + i + ") = " + its(EP.rangeDownDecreasing(i)));
        }
    }

    private void demoRangeDownDecreasing_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("rangeDownDecreasing(" + nicePrint(c) + ") = " + cits(EP.rangeDownDecreasing(c)));
        }
    }

    private void demoRangeIncreasing_byte_byte() {
        for (Pair<Byte, Byte> p : take(MEDIUM_LIMIT, P.bagPairs(P.bytes()))) {
            System.out.println("rangeIncreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeIncreasing_short_short() {
        for (Pair<Short, Short> p : take(MEDIUM_LIMIT, P.bagPairs(P.shorts()))) {
            System.out.println("rangeIncreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeIncreasing_int_int() {
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, P.bagPairs(P.integers()))) {
            System.out.println("rangeIncreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeIncreasing_long_long() {
        for (Pair<Long, Long> p : take(MEDIUM_LIMIT, P.bagPairs(P.longs()))) {
            System.out.println("rangeIncreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeIncreasing_BigInteger_BigInteger() {
        for (Pair<BigInteger, BigInteger> p : take(MEDIUM_LIMIT, P.bagPairs(P.bigIntegers()))) {
            System.out.println("rangeIncreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeIncreasing_char_char() {
        for (Pair<Character, Character> p : take(MEDIUM_LIMIT, P.bagPairs(P.characters()))) {
            System.out.println("rangeIncreasing(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " +
                    cits(EP.rangeIncreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_byte_byte() {
        for (Pair<Byte, Byte> p : take(MEDIUM_LIMIT, P.bagPairs(P.bytes()))) {
            System.out.println("rangeDecreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_short_short() {
        for (Pair<Short, Short> p : take(MEDIUM_LIMIT, P.bagPairs(P.shorts()))) {
            System.out.println("rangeDecreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_int_int() {
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, P.bagPairs(P.integers()))) {
            System.out.println("rangeDecreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_long_long() {
        for (Pair<Long, Long> p : take(MEDIUM_LIMIT, P.bagPairs(P.longs()))) {
            System.out.println("rangeDecreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_BigInteger_BigInteger() {
        for (Pair<BigInteger, BigInteger> p : take(MEDIUM_LIMIT, P.bagPairs(P.bigIntegers()))) {
            System.out.println("rangeDecreasing(" + p.a + ", " + p.b + ") = " + its(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeDecreasing_char_char() {
        for (Pair<Character, Character> p : take(MEDIUM_LIMIT, P.bagPairs(P.characters()))) {
            System.out.println("rangeDecreasing(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " +
                    cits(EP.rangeDecreasing(p.a, p.b)));
        }
    }

    private void demoRangeUp_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeUp(" + b + ") = " + its(EP.rangeUp(b)));
        }
    }

    private void demoRangeUp_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeUp(" + s + ") = " + its(EP.rangeUp(s)));
        }
    }

    private void demoRangeUp_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private void demoRangeUp_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeUp(" + l + ") = " + its(EP.rangeUp(l)));
        }
    }

    private void demoRangeUp_BigInteger() {
        for (BigInteger i : take(MEDIUM_LIMIT, P.bigIntegers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private void demoRangeDown_byte() {
        for (byte b : take(MEDIUM_LIMIT, P.bytes())) {
            System.out.println("rangeDown(" + b + ") = " + its(EP.rangeDown(b)));
        }
    }

    private void demoRangeDown_short() {
        for (short s : take(MEDIUM_LIMIT, P.shorts())) {
            System.out.println("rangeDown(" + s + ") = " + its(EP.rangeDown(s)));
        }
    }

    private void demoRangeDown_int() {
        for (int i : take(MEDIUM_LIMIT, P.integers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private void demoRangeDown_long() {
        for (long l : take(MEDIUM_LIMIT, P.longs())) {
            System.out.println("rangeDown(" + l + ") = " + its(EP.rangeDown(l)));
        }
    }

    private void demoRangeDown_BigInteger() {
        for (BigInteger i : take(MEDIUM_LIMIT, P.bigIntegers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private void demoRange_byte_byte() {
        for (Pair<Byte, Byte> p : take(MEDIUM_LIMIT, P.bagPairs(P.bytes()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRange_short_short() {
        for (Pair<Short, Short> p : take(MEDIUM_LIMIT, P.bagPairs(P.shorts()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRange_int_int() {
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, P.bagPairs(P.integers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRange_long_long() {
        for (Pair<Long, Long> p : take(MEDIUM_LIMIT, P.bagPairs(P.longs()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRange_BigInteger_BigInteger() {
        for (Pair<BigInteger, BigInteger> p : take(MEDIUM_LIMIT, P.bagPairs(P.bigIntegers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRangeUp_BinaryFraction() {
        for (BinaryFraction bf : take(MEDIUM_LIMIT, P.binaryFractions())) {
            System.out.println("rangeUp(" + bf + ") = " + its(EP.rangeUp(bf)));
        }
    }

    private void demoRangeDown_BinaryFraction() {
        for (BinaryFraction bf : take(MEDIUM_LIMIT, P.binaryFractions())) {
            System.out.println("rangeDown(" + bf + ") = " + its(EP.rangeDown(bf)));
        }
    }

    private void demoRange_BinaryFraction_BinaryFraction() {
        for (Pair<BinaryFraction, BinaryFraction> p : take(MEDIUM_LIMIT, P.bagPairs(P.binaryFractions()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRangeUp_float() {
        for (float f : take(MEDIUM_LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("rangeUp(" + f + ") = " + its(EP.rangeUp(f)));
        }
    }

    private void demoRangeDown_float() {
        for (float f : take(MEDIUM_LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("rangeDown(" + f + ") = " + its(EP.rangeDown(f)));
        }
    }

    private void demoRange_float_float() {
        for (Pair<Float, Float> p : take(MEDIUM_LIMIT, P.bagPairs(filter(f -> !Float.isNaN(f), P.floats())))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRangeUp_double() {
        for (double d : take(MEDIUM_LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("rangeUp(" + d + ") = " + its(EP.rangeUp(d)));
        }
    }

    private void demoRangeDown_double() {
        for (double d : take(MEDIUM_LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("rangeDown(" + d + ") = " + its(EP.rangeDown(d)));
        }
    }

    private void demoRange_double_double() {
        for (Pair<Double, Double> p : take(MEDIUM_LIMIT, P.bagPairs(filter(d -> !Double.isNaN(d), P.doubles())))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRangeUp_BigDecimal() {
        for (BigDecimal bd : take(MEDIUM_LIMIT, P.bigDecimals())) {
            System.out.println("rangeUp(" + bd + ") = " + its(EP.rangeUp(bd)));
        }
    }

    private void demoRangeDown_BigDecimal() {
        for (BigDecimal bd : take(MEDIUM_LIMIT, P.bigDecimals())) {
            System.out.println("rangeDown(" + bd + ") = " + its(EP.rangeDown(bd)));
        }
    }

    private void demoRange_BigDecimal_BigDecimal() {
        for (Pair<BigDecimal, BigDecimal> p : take(MEDIUM_LIMIT, P.bagPairs(P.bigDecimals()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private void demoRangeUpCanonical_BigDecimal() {
        for (BigDecimal bd : take(MEDIUM_LIMIT, P.bigDecimals())) {
            System.out.println("rangeUpCanonical(" + bd + ") = " + its(EP.rangeUpCanonical(bd)));
        }
    }

    private void demoRangeDownCanonical_BigDecimal() {
        for (BigDecimal bd : take(MEDIUM_LIMIT, P.bigDecimals())) {
            System.out.println("rangeDownCanonical(" + bd + ") = " + its(EP.rangeDownCanonical(bd)));
        }
    }

    private void demoRangeCanonical_BigDecimal_BigDecimal() {
        for (Pair<BigDecimal, BigDecimal> p : take(MEDIUM_LIMIT, P.bagPairs(P.bigDecimals()))) {
            System.out.println("rangeCanonical(" + p.a + ", " + p.b + ") = " + its(EP.rangeCanonical(p.a, p.b)));
        }
    }

    private void demoWithNull_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("withNull(" + xs + ") = " + its(EP.withNull(xs)));
        }
    }

    private void demoWithNull_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("withNull(" + middle(IterableUtils.toString(TINY_LIMIT, xs)) + ") = " +
                    its(EP.withNull(xs)));
        }
    }

    private void demoNonEmptyOptionals_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("nonEmptyOptionals(" + xs + ") = " + its(EP.nonEmptyOptionals(xs)));
        }
    }

    private void demoNonEmptyOptionals_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("nonEmptyOptionals(" + middle(IterableUtils.toString(TINY_LIMIT, xs)) + ") = " +
                    its(EP.nonEmptyOptionals(xs)));
        }
    }

    private void demoOptionals_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("optionals(" + xs + ") = " + its(EP.optionals(xs)));
        }
    }

    private void demoOptionals_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("optionals(" + middle(IterableUtils.toString(TINY_LIMIT, xs)) + ") = " +
                    its(EP.optionals(xs)));
        }
    }

    private void demoNonEmptyNullableOptionals_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("nonEmptyNullableOptionals(" + xs + ") = " + its(EP.nonEmptyNullableOptionals(xs)));
        }
    }

    private void demoNonEmptyNullableOptionals_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("nonEmptyNullableOptionals(" + middle(IterableUtils.toString(TINY_LIMIT, xs)) + ") = " +
                    its(EP.nonEmptyNullableOptionals(xs)));
        }
    }

    private void demoNullableOptionals_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("nullableOptionals(" + xs + ") = " + its(EP.nullableOptionals(xs)));
        }
    }

    private void demoNullableOptionals_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("nullableOptionals(" + middle(IterableUtils.toString(TINY_LIMIT, xs)) + ") = " +
                    its(EP.nullableOptionals(xs)));
        }
    }

    private void demoDependentPairs_finite() {
        IterableProvider PS = P.withScale(4);
        Iterable<Pair<List<Integer>, Function<Integer, Iterable<Integer>>>> ps = P.dependentPairsInfinite(
                PS.lists(P.integersGeometric()),
                xs -> xs.isEmpty() ?
                        repeat(new FiniteDomainFunction<>(new HashMap<>())) :
                        map(
                                FiniteDomainFunction::new,
                                PS.maps(xs, map(is -> (Iterable<Integer>) is, PS.lists(P.integersGeometric())))
                        )
        );
        if (P instanceof ExhaustiveProvider) {
            ps = nub(ps);
        }
        for (Pair<List<Integer>, Function<Integer, Iterable<Integer>>> p : take(LIMIT, ps)) {
            System.out.println("dependentPairs(" + p.a + ", " + p.b + ") = " + its(EP.dependentPairs(p.a, p.b)));
        }
    }

    private void demoDependentPairs_cyclic() {
        IterableProvider PS = P.withScale(4);
        Function<List<Integer>, Iterable<Map<Integer, List<Integer>>>> f = xs -> {
            if (xs.isEmpty()) {
                return repeat(new HashMap<>());
            } else {
                return filter(m -> !all(p -> isEmpty(p.b), fromMap(m)), PS.maps(xs, PS.lists(P.integersGeometric())));
            }
        };
        Function<
                Pair<List<Integer>, Map<Integer, List<Integer>>>, Pair<Iterable<Integer>,
                Function<Integer, Iterable<Integer>>>
        > g = p -> {
            Iterable<Pair<Integer, List<Integer>>> values = fromMap(p.b);
            Map<Integer, Iterable<Integer>> transformedValues = toMap(
                    map(e -> new Pair<>(e.a, (Iterable<Integer>) e.b), values)
            );
            return new Pair<>(cycle(p.a), new FiniteDomainFunction<>(transformedValues));
        };
        Iterable<Pair<Iterable<Integer>, Function<Integer, Iterable<Integer>>>> ps = map(
                g,
                nub(
                        P.dependentPairsInfinite(
                                nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.positiveIntegersGeometric()))),
                                f
                        )
                )
        );
        for (Pair<Iterable<Integer>, Function<Integer, Iterable<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("dependentPairs(" + its(p.a) + ", " + p.b + ") = " + its(EP.dependentPairs(p.a, p.b)));
        }
    }

    private void demoDependentPairsInfinite() {
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
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfinite(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfinite(p.a, p.b)));
        }
    }

    private static class IntNoHashCode {
        private int i;

        public IntNoHashCode(int i) {
            this.i = i;
        }

        public int intValue() {
            return i;
        }

        public int hashCode() {
            throw new UnsupportedOperationException();
        }

        public @NotNull
        String toString() {
            return Integer.toString(i);
        }
    }

    private void demoDependentPairsInfiniteIdentityHash() {
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
                take(MEDIUM_LIMIT, ps)) {
            String niceFunction = toIdentityMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfiniteIdentityHash(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfiniteIdentityHash(p.a, p.b)));
        }
    }

    private void demoDependentPairsInfiniteLogarithmicOrder() {
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
                        P.dependentPairsInfiniteLogarithmicOrder(
                                nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric()))),
                                f
                        )
                )
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfiniteLogarithmicOrder(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoDependentPairsInfiniteSquareRootOrder() {
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
                        P.dependentPairsInfiniteSquareRootOrder(
                                nub(map(IterableUtils::unrepeat, PS.listsAtLeast(1, P.integersGeometric()))),
                                f
                        )
                )
        );
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(MEDIUM_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfiniteSquareRootOrder(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoPairsLogarithmicOrder_Iterable_Iterable_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsLogarithmicOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.pairsLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoPairsLogarithmicOrder_Iterable_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("pairsLogarithmicOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.pairsLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoPairsLogarithmicOrder_Iterable_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("pairsLogarithmicOrder(" + middle(xs.toString()) + ") = " +
                    its(EP.pairsLogarithmicOrder(xs)));
        }
    }

    private void demoPairsLogarithmicOrder_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("pairsLogarithmicOrder(" + middle(its(xs)) + ") = " +
                    its(EP.pairsLogarithmicOrder(xs)));
        }
    }

    private void demoPairsSquareRootOrder_Iterable_Iterable_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsSquareRootOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.pairsSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoPairsSquareRootOrder_Iterable_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("pairsSquareRootOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.pairsSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoPairsSquareRootOrder_Iterable_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("pairsSquareRootOrder(" + middle(xs.toString()) + ") = " +
                    its(EP.pairsSquareRootOrder(xs)));
        }
    }

    private void demoPairsSquareRootOrder_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("pairsSquareRootOrder(" + middle(its(xs)) + ") = " + its(EP.pairsSquareRootOrder(xs)));
        }
    }

    private void demoPermutationsFinite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("permutationsFinite(" + middle(xs.toString()) + ") = " +
                    its(EP.permutationsFinite(xs)));
        }
    }

    private void demoStringPermutations() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringPermutations(" + nicePrint(s) + ") = " + sits(EP.stringPermutations(s)));
        }
    }

    private void demoPrefixPermutations_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("prefixPermutations(" + middle(xs.toString()) + ") = " +
                    its(map(Testing::its, EP.prefixPermutations(xs))));
        }
    }

    private void demoPrefixPermutations_infinite() {
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("prefixPermutations(" + middle(its(xs)) + ") = " +
                    its(map(Testing::its, EP.prefixPermutations(xs))));
        }
    }

    private void demoListsLex_int_List() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("listsLex(" + p.b + ", " + p.a + ") = " + its(EP.listsLex(p.b, p.a)));
        }
    }

    private void demoPairsLex_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsLex(" + p.a + ", " + p.b + ") = " + its(EP.pairsLex(p.a, p.b)));
        }
    }

    private void demoPairsLex_infinite() {
        Iterable<Pair<Iterable<Integer>, List<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("pairsLex(" + its(p.a) + ", " + p.b + ") = " + its(EP.pairsLex(p.a, p.b)));
        }
    }

    private void demoTriplesLex_finite() {
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("triplesLex(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(EP.triplesLex(t.a, t.b, t.c)));
        }
    }

    private void demoTriplesLex_infinite() {
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Triple<Iterable<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs
        );
        for (Triple<Iterable<Integer>, List<Integer>, List<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("triplesLex(" + its(t.a) + ", " + t.b + ", " + t.c + ") = " +
                    its(EP.triplesLex(t.a, t.b, t.c)));
        }
    }

    private void demoQuadruplesLex_finite() {
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(MEDIUM_LIMIT, qs)) {
            System.out.println("quadruplesLex(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruplesLex(q.a, q.b, q.c, q.d)));
        }
    }

    private void demoQuadruplesLex_infinite() {
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(MEDIUM_LIMIT, qs)) {
            System.out.println("quadruplesLex(" + its(q.a) + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruplesLex(q.a, q.b, q.c, q.d)));
        }
    }

    private void demoQuintuplesLex_finite() {
        Iterable<Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs = P.quintuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(MEDIUM_LIMIT, qs)) {
            System.out.println("quintuplesLex(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private void demoQuintuplesLex_infinite() {
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Quintuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs = P.quintuples(
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
        > q : take(MEDIUM_LIMIT, qs)) {
            System.out.println(
                    "quintuplesLex(" + its(q.a) + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e))
            );
        }
    }

    private void demoSextuplesLex_finite() {
        Iterable<Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.sextuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println(
                    "sextuplesLex(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ") = " +
                    its(EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f))
            );
        }
    }

    private void demoSextuplesLex_infinite() {
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Sextuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.sextuples(
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
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println(
                    "sextuplesLex(" +
                    its(s.a) + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ") = " +
                    its(EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f))
            );
        }
    }

    private void demoSeptuplesLex_finite() {
        Iterable<Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.septuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println(
                    "septuplesLex(" +
                    s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    its(EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g))
            );
        }
    }

    private void demoSeptuplesLex_infinite() {
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Septuple<
                Iterable<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.septuples(
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
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println(
                    "septuplesLex(" +
                    its(s.a) + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    its(EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g))
            );
        }
    }

    private void demoStringsLex_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringsLex(" + p.b + ", " + nicePrint(p.a) + ") = " + sits(EP.stringsLex(p.b, p.a)));
        }
    }

    private void demoListsShortlex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("listsShortlex(" + xs + ") = " + its(EP.listsShortlex(xs)));
        }
    }

    private void demoStringsShortlex() {
        for (String s : take(MEDIUM_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringsShortlex(" + nicePrint(s) + ") = " + sits(EP.stringsShortlex(s)));
        }
    }

    private void demoListsShortlexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.listsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoStringsShortlexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoLists_int_Iterable_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("lists(" + p.b + ", " + p.a + ") = " + its(EP.lists(p.b, p.a)));
        }
    }

    private void demoLists_int_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("lists(" + p.b + ", " + its(p.a) + ") = " + its(EP.lists(p.b, p.a)));
        }
    }

    private void demoPairs_Iterable_Iterable_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairs(" + p.a + ", " + p.b + ") = " + its(EP.pairs(p.a, p.b)));
        }
    }

    private void demoPairs_Iterable_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("pairs(" + its(p.a) + ", " + its(p.b) + ") = " + its(EP.pairs(p.a, p.b)));
        }
    }

    private void demoPairs_Iterable_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("pairs(" + xs + ") = " + its(EP.pairs(xs)));
        }
    }

    private void demoPairs_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("pairs(" + its(xs) + ") = " + its(EP.pairs(xs)));
        }
    }

    private void demoTriples_Iterable_Iterable_Iterable_finite() {
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("triples(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(EP.triples(t.a, t.b, t.c)));
        }
    }

    private void demoTriples_Iterable_Iterable_Iterable_infinite() {
        Iterable<Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> ts = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>> t : take(MEDIUM_LIMIT, ts)) {
            System.out.println("triples(" + its(t.a) + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(EP.triples(t.a, t.b, t.c)));
        }
    }

    private void demoTriples_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("triples(" + xs + ") = " + its(EP.triples(xs)));
        }
    }

    private void demoTriples_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("triples(" + its(xs) + ") = " + its(EP.triples(xs)));
        }
    }

    private void demoQuadruples_Iterable_Iterable_Iterable_Iterable_finite() {
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(MEDIUM_LIMIT, qs)) {
            System.out.println("quadruples(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruples(q.a, q.b, q.c, q.d)));
        }
    }

    private void demoQuadruples_Iterable_Iterable_Iterable_Iterable_infinite() {
        Iterable<Quadruple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> qs =
                P.quadruples(
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                );
        for (Quadruple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > q : take(MEDIUM_LIMIT, qs)) {
            System.out.println(
                    "quadruples(" + its(q.a) + ", " + its(q.b) + ", " + its(q.c) + ", " + its(q.d) + ") = " +
                    its(EP.quadruples(q.a, q.b, q.c, q.d)));
        }
    }

    private void demoQuadruples_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("quadruples(" + xs + ") = " + its(EP.quadruples(xs)));
        }
    }

    private void demoQuadruples_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("quadruples(" + its(xs) + ") = " + its(EP.quadruples(xs)));
        }
    }

    private void demoQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        Iterable<Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> qs = P.quintuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Quintuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > q : take(MEDIUM_LIMIT, qs)) {
            System.out.println("quintuples(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuples(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private void demoQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        Iterable<Quintuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> qs =
                P.quintuples(
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                );
        for (Quintuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > q : take(MEDIUM_LIMIT, qs)) {
            System.out.println(
                    "quintuples(" + its(q.a) + ", " + its(q.b) + ", " + its(q.c) + ", " + its(q.d) + ", " + its(q.e) +
                    ") = " + its(EP.quintuples(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private void demoQuintuples_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("quintuples(" + xs + ") = " + its(EP.quintuples(xs)));
        }
    }

    private void demoQuintuples_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("quintuples(" + its(xs) + ") = " + its(EP.quintuples(xs)));
        }
    }

    private void demoSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        Iterable<Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.sextuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Sextuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println("sextuples(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f +
                    ") = " + its(EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f)));
        }
    }

    private void demoSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        Iterable<Sextuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> ss =
                P.sextuples(
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                );
        for (Sextuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println(
                    "sextuples(" + its(s.a) + ", " + its(s.b) + ", " + its(s.c) + ", " + its(s.d) + ", " + its(s.e) +
                    ", " + its(s.f) + ") = " + its(EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f)));
        }
    }

    private void demoSextuples_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("sextuples(" + xs + ") = " + its(EP.sextuples(xs)));
        }
    }

    private void demoSextuples_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("sextuples(" + its(xs) + ") = " + its(EP.sextuples(xs)));
        }
    }

    private void demoSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        Iterable<Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        >> ss = P.septuples(P.withScale(4).lists(P.withNull(P.integersGeometric())));
        for (Septuple<
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>,
                List<Integer>
        > s : take(MEDIUM_LIMIT, ss)) {
            System.out.println("septuples(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f +
                    ", " + s.g + ") = " + its(EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g)));
        }
    }

    private void demoSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        Iterable<Septuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        >> ss =
                P.septuples(
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                );
        for (Septuple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "septuples(" + its(s.a) + ", " + its(s.b) + ", " + its(s.c) + ", " + its(s.d) + ", " + its(s.e) +
                    ", " + its(s.f) + ", " + its(s.g) + ") = " + its(EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g)));
        }
    }

    private void demoSeptuples_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("septuples(" + xs + ") = " + its(EP.septuples(xs)));
        }
    }

    private void demoSeptuples_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("septuples(" + its(xs) + ") = " + its(EP.septuples(xs)));
        }
    }

    private void demoStrings_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("strings(" + p.b + ", " + nicePrint(p.a) + ") = " + sits(EP.strings(p.b, p.a)));
        }
    }

    private void demoStrings_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("strings(" + i + ") = " + sits(EP.strings(i)));
        }
    }

    private void demoLists_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("lists(" + xs + ") = " + its(EP.lists(xs)));
        }
    }

    private void demoLists_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("lists(" + its(xs) + ") = " + its(EP.lists(xs)));
        }
    }

    private void demoStrings_String() {
        for (String s : take(MEDIUM_LIMIT, P.withScale(4).strings())) {
            System.out.println("strings(" + nicePrint(s) + ") = " + sits(EP.strings(s)));
        }
    }

    private void demoListsAtLeast_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsAtLeast(" + p.b + ", " + p.a + ") = " + its(EP.listsAtLeast(p.b, p.a)));
        }
    }

    private void demoListsAtLeast_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.listsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringsAtLeast_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringsAtLeast_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringsAtLeast(" + i + ") = " + sits(EP.stringsAtLeast(i)));
        }
    }

    private void demoDistinctListsLex_int_List() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctListsLex(" + p.b + ", " + p.a + ") = " + its(EP.distinctListsLex(p.b, p.a)));
        }
    }

    private void demoDistinctPairsLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctPairsLex(" + xs + ") = " + its(EP.distinctPairsLex(xs)));
        }
    }

    private void demoDistinctTriplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctTriplesLex(" + xs + ") = " + its(EP.distinctTriplesLex(xs)));
        }
    }

    private void demoDistinctQuadruplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuadruplesLex(" + xs + ") = " + its(EP.distinctQuadruplesLex(xs)));
        }
    }

    private void demoDistinctQuintuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuintuplesLex(" + xs + ") = " + its(EP.distinctQuintuplesLex(xs)));
        }
    }

    private void demoDistinctSextuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSextuplesLex(" + xs + ") = " + its(EP.distinctSextuplesLex(xs)));
        }
    }

    private void demoDistinctSeptuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSeptuplesLex(" + xs + ") = " + its(EP.distinctSeptuplesLex(xs)));
        }
    }

    private void demoDistinctStringsLex_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.distinctStringsLex(p.b, p.a)));
        }
    }

    private void demoDistinctListsLex_List() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctListsLex(" + xs + ") = " + its(EP.distinctListsLex(xs)));
        }
    }

    private void demoDistinctStringsLex_String() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStringsLex(" + nicePrint(s) + ") = " + sits(EP.distinctStringsLex(s)));
        }
    }

    private void demoDistinctListsLexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctListsLexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsLexAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctStringsLexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsLexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.distinctStringsLexAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctListsShortlex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctListsShortlex(" + xs + ") = " + its(EP.distinctListsShortlex(xs)));
        }
    }

    private void demoDistinctStringsShortlex() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStringsShortlex(" + nicePrint(s) + ") = " +
                    sits(EP.distinctStringsShortlex(s)));
        }
    }

    private void demoDistinctListsShortlexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctListsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctStringsShortlexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.distinctStringsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctLists_int_Iterable_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctLists(" + p.b + ", " + p.a + ") = " + its(EP.distinctLists(p.b, p.a)));
        }
    }

    private void demoDistinctLists_int_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctLists(" + p.b + ", " + its(p.a) + ") = " + its(EP.distinctLists(p.b, p.a)));
        }
    }

    private void demoDistinctPairs_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctPairs(" + xs + ") = " + its(EP.distinctPairs(xs)));
        }
    }

    private void demoDistinctPairs_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctPairs(" + its(xs) + ") = " + its(EP.distinctPairs(xs)));
        }
    }

    private void demoDistinctTriples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctTriples(" + xs + ") = " + its(EP.distinctTriples(xs)));
        }
    }

    private void demoDistinctTriples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctTriples(" + its(xs) + ") = " + its(EP.distinctTriples(xs)));
        }
    }

    private void demoDistinctQuadruples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuadruples(" + xs + ") = " + its(EP.distinctQuadruples(xs)));
        }
    }

    private void demoDistinctQuadruples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctQuadruples(" + its(xs) + ") = " + its(EP.distinctQuadruples(xs)));
        }
    }

    private void demoDistinctQuintuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuintuples(" + xs + ") = " + its(EP.distinctQuintuples(xs)));
        }
    }

    private void demoDistinctQuintuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctQuintuples(" + its(xs) + ") = " + its(EP.distinctQuintuples(xs)));
        }
    }

    private void demoDistinctSextuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSextuples(" + xs + ") = " + its(EP.distinctSextuples(xs)));
        }
    }

    private void demoDistinctSextuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctSextuples(" + its(xs) + ") = " + its(EP.distinctSextuples(xs)));
        }
    }

    private void demoDistinctSeptuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSeptuples(" + xs + ") = " + its(EP.distinctSeptuples(xs)));
        }
    }

    private void demoDistinctSeptuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctSeptuples(" + its(xs) + ") = " + its(EP.distinctSeptuples(xs)));
        }
    }

    private void demoDistinctStrings_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.distinctStrings(p.b, p.a)));
        }
    }

    private void demoDistinctStrings_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("distinctStrings(" + i + ") = " + sits(EP.distinctStrings(i)));
        }
    }

    private void demoDistinctLists_Iterable_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctLists(" + xs + ") = " + its(EP.distinctLists(xs)));
        }
    }

    private void demoDistinctLists_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctLists(" + its(xs) + ") = " + its(EP.distinctLists(xs)));
        }
    }

    private void demoDistinctStrings_String() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStrings(" + nicePrint(s) + ") = " + sits(EP.distinctStrings(s)));
        }
    }

    private void demoDistinctListsAtLeast_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctListsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctListsAtLeast_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("distinctListsAtLeast(" + p.b + ", " + its(p.a) + ") = " +
                    its(EP.distinctListsAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctStringsAtLeast_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.distinctStringsAtLeast(p.b, p.a)));
        }
    }

    private void demoDistinctStringsAtLeast_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("distinctStringsAtLeast(" + i + ") = " + sits(EP.distinctStringsAtLeast(i)));
        }
    }

    private void demoBagsLex_int_List() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("bagsLex(" + p.b + ", " + p.a + ") = " + its(EP.bagsLex(p.b, p.a)));
        }
    }

    private void demoBagPairsLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagPairsLex(" + xs + ") = " + its(EP.bagPairsLex(xs)));
        }
    }

    private void demoBagTriplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagTriplesLex(" + xs + ") = " + its(EP.bagTriplesLex(xs)));
        }
    }

    private void demoBagQuadruplesLex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuadruplesLex(" + xs + ") = " + its(EP.bagQuadruplesLex(xs)));
        }
    }

    private void demoBagQuintuplesLex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuintuplesLex(" + xs + ") = " + its(EP.bagQuintuplesLex(xs)));
        }
    }

    private void demoBagSextuplesLex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSextuplesLex(" + xs + ") = " + its(EP.bagSextuplesLex(xs)));
        }
    }

    private void demoBagSeptuplesLex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSeptuplesLex(" + xs + ") = " + its(EP.bagSeptuplesLex(xs)));
        }
    }

    private void demoStringBagsLex() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringBagsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringBagsLex(p.b, p.a)));
        }
    }

    private void demoBagsShortlex() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagsShortlex(" + xs + ") = " + its(EP.bagsShortlex(xs)));
        }
    }

    private void demoStringBagsShortlex() {
        for (String s : take(MEDIUM_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringBagsShortlex(" + nicePrint(s) + ") = " + sits(EP.stringBagsShortlex(s)));
        }
    }

    private void demoBagsShortlexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bagsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.bagsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoStringBagsShortlexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBagsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringBagsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoBags_int_Iterable_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("bags(" + p.b + ", " + p.a + ") = " + its(EP.bags(p.b, p.a)));
        }
    }

    private void demoBags_int_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bags(" + p.b + ", " + its(p.a) + ") = " + its(EP.bags(p.b, p.a)));
        }
    }

    private void demoBagPairs_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagPairs(" + xs + ") = " + its(EP.bagPairs(xs)));
        }
    }

    private void demoBagPairs_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagPairs(" + its(xs) + ") = " + its(EP.bagPairs(xs)));
        }
    }

    private void demoBagTriples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagTriples(" + xs + ") = " + its(EP.bagTriples(xs)));
        }
    }

    private void demoBagTriples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagTriples(" + its(xs) + ") = " + its(EP.bagTriples(xs)));
        }
    }

    private void demoBagQuadruples_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuadruples(" + xs + ") = " + its(EP.bagQuadruples(xs)));
        }
    }

    private void demoBagQuadruples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagQuadruples(" + its(xs) + ") = " + its(EP.bagQuadruples(xs)));
        }
    }

    private void demoBagQuintuples_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuintuples(" + xs + ") = " + its(EP.bagQuintuples(xs)));
        }
    }

    private void demoBagQuintuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagQuintuples(" + its(xs) + ") = " + its(EP.bagQuintuples(xs)));
        }
    }

    private void demoBagSextuples_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSextuples(" + xs + ") = " + its(EP.bagSextuples(xs)));
        }
    }

    private void demoBagSextuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagSextuples(" + its(xs) + ") = " + its(EP.bagSextuples(xs)));
        }
    }

    private void demoBagSeptuples_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSeptuples(" + xs + ") = " + its(EP.bagSeptuples(xs)));
        }
    }

    private void demoBagSeptuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagSeptuples(" + its(xs) + ") = " + its(EP.bagSeptuples(xs)));
        }
    }

    private void demoStringBags_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringBags(" + p.b + ", " + nicePrint(p.a) + ") = " + sits(EP.stringBags(p.b, p.a)));
        }
    }

    private void demoStringBags_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringBags(" + i + ") = " + sits(EP.stringBags(i)));
        }
    }

    private void demoBags_Iterable_finite() {
        for (List<Integer> xs : take(MEDIUM_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bags(" + xs + ") = " + its(EP.bags(xs)));
        }
    }

    private void demoBags_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bags(" + its(xs) + ") = " + its(EP.bags(xs)));
        }
    }

    private void demoStringBags_String() {
        for (String s : take(MEDIUM_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringBags(" + nicePrint(s) + ") = " + sits(EP.stringBags(s)));
        }
    }

    private void demoBagsAtLeast_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bagsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.bagsAtLeast(p.b, p.a)));
        }
    }

    private void demoBagsAtLeast_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bagsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.bagsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringBagsAtLeast_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("stringBagsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringBagsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringBagsAtLeast_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringBagsAtLeast(" + i + ") = " + sits(EP.stringBagsAtLeast(i)));
        }
    }

    private void demoSubsetsLex_int_List() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsLex(" + p.b + ", " + p.a + ") = " + its(EP.subsetsLex(p.b, p.a)));
        }
    }

    private void demoSubsetPairsLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetPairsLex(" + xs + ") = " + its(EP.subsetPairsLex(xs)));
        }
    }

    private void demoSubsetTriplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetTriplesLex(" + xs + ") = " + its(EP.subsetTriplesLex(xs)));
        }
    }

    private void demoSubsetQuadruplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuadruplesLex(" + xs + ") = " + its(EP.subsetQuadruplesLex(xs)));
        }
    }

    private void demoSubsetQuintuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuintuplesLex(" + xs + ") = " + its(EP.subsetQuintuplesLex(xs)));
        }
    }

    private void demoSubsetSextuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSextuplesLex(" + xs + ") = " + its(EP.subsetSextuplesLex(xs)));
        }
    }

    private void demoSubsetSeptuplesLex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSeptuplesLex(" + xs + ") = " + its(EP.subsetSeptuplesLex(xs)));
        }
    }

    private void demoStringSubsetsLex_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringSubsetsLex(p.b, p.a)));
        }
    }

    private void demoSubsetsLex_List() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetsLex(" + xs + ") = " + its(EP.subsetsLex(xs)));
        }
    }

    private void demoStringSubsetsLex_String() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsetsLex(" + nicePrint(s) + ") = " + sits(EP.stringSubsetsLex(s)));
        }
    }

    private void demoSubsetsLexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsLexAtLeast(" + p.b + ", " + p.a + ") = " + its(EP.subsetsLexAtLeast(p.b, p.a)));
        }
    }

    private void demoStringSubsetsLexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsLexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringSubsetsLexAtLeast(p.b, p.a)));
        }
    }

    private void demoSubsetsShortlex() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetsShortlex(" + xs + ") = " + its(EP.subsetsShortlex(xs)));
        }
    }

    private void demoStringSubsetsShortlex() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsetsShortlex(" + nicePrint(s) + ") = " + sits(EP.stringSubsetsShortlex(s)));
        }
    }

    private void demoSubsetsShortlexAtLeast() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.subsetsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoStringSubsetsShortlexAtLeast() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringSubsetsShortlexAtLeast(p.b, p.a)));
        }
    }

    private void demoSubsets_int_Iterable_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsets(" + p.b + ", " + p.a + ") = " + its(EP.subsets(p.b, p.a)));
        }
    }

    private void demoSubsets_int_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("subsets(" + p.b + ", " + its(p.a) + ") = " + its(EP.subsets(p.b, p.a)));
        }
    }

    private void demoSubsetPairs_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetPairs(" + xs + ") = " + its(EP.subsetPairs(xs)));
        }
    }

    private void demoSubsetPairs_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetPairs(" + its(xs) + ") = " + its(EP.subsetPairs(xs)));
        }
    }

    private void demoSubsetTriples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetTriples(" + xs + ") = " + its(EP.subsetTriples(xs)));
        }
    }

    private void demoSubsetTriples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetTriples(" + its(xs) + ") = " + its(EP.subsetTriples(xs)));
        }
    }

    private void demoSubsetQuadruples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuadruples(" + xs + ") = " + its(EP.subsetQuadruples(xs)));
        }
    }

    private void demoSubsetQuadruples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetQuadruples(" + its(xs) + ") = " + its(EP.subsetQuadruples(xs)));
        }
    }

    private void demoSubsetQuintuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuintuples(" + xs + ") = " + its(EP.subsetQuintuples(xs)));
        }
    }

    private void demoSubsetQuintuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetQuintuples(" + its(xs) + ") = " + its(EP.subsetQuintuples(xs)));
        }
    }

    private void demoSubsetSextuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSextuples(" + xs + ") = " + its(EP.subsetSextuples(xs)));
        }
    }

    private void demoSubsetSextuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetSextuples(" + its(xs) + ") = " + its(EP.subsetSextuples(xs)));
        }
    }

    private void demoSubsetSeptuples_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSeptuples(" + xs + ") = " + its(EP.subsetSeptuples(xs)));
        }
    }

    private void demoSubsetSeptuples_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetSeptuples(" + its(xs) + ") = " + its(EP.subsetSeptuples(xs)));
        }
    }

    private void demoStringSubsets_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringSubsets(p.b, p.a)));
        }
    }

    private void demoStringSubsets_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringSubsets(" + i + ") = " + sits(EP.stringSubsets(i)));
        }
    }

    private void demoSubsets_Iterable_finite() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsets(" + xs + ") = " + its(EP.subsets(xs)));
        }
    }

    private void demoSubsets_Iterable_infinite() {
        for (Iterable<Integer> xs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsets(" + its(xs) + ") = " + its(EP.subsets(xs)));
        }
    }

    private void demoStringSubsets_String() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsets(" + nicePrint(s) + ") = " + sits(EP.stringSubsets(s)));
        }
    }

    private void demoSubsetsAtLeast_finite() {
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.subsetsAtLeast(p.b, p.a)));
        }
    }

    private void demoSubsetsAtLeast_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("subsetsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.subsetsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringSubsetsAtLeast_int_String() {
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    sits(EP.stringSubsetsAtLeast(p.b, p.a)));
        }
    }

    private void demoStringSubsetsAtLeast_int() {
        for (int i : take(SMALL_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringSubsetsAtLeast(" + i + ") = " + sits(EP.stringSubsetsAtLeast(i)));
        }
    }

    private void demoEithersSuccessive_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("eithersSuccessive(" + p.a + ", " + p.b + ") = " + its(EP.eithersSuccessive(p.a, p.b)));
        }
    }

    private void demoEithersSuccessive_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("eithersSuccessive(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.eithersSuccessive(p.a, p.b)));
        }
    }

    private void demoEithersSquareRootOrder_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("eithersSquareRootOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.eithersSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoEithersSquareRootOrder_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("eithersSquareRootOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.eithersSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoEithersLogarithmicOrder_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("eithersLogarithmicOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.eithersLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoEithersLogarithmicOrder_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("eithersLogarithmicOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.eithersLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoEithers_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("eithers(" + p.a + ", " + p.b + ") = " + its(EP.eithers(p.a, p.b)));
        }
    }

    private void demoEithers_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("eithers(" + its(p.a) + ", " + its(p.b) + ") = " + its(EP.eithers(p.a, p.b)));
        }
    }

    private void demoChooseSquareRootOrder_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("chooseSquareRootOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.chooseSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoChooseSquareRootOrder_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("chooseSquareRootOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.chooseSquareRootOrder(p.a, p.b)));
        }
    }

    private void demoChooseLogarithmicOrder_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("chooseLogarithmicOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.chooseLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoChooseLogarithmicOrder_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("chooseLogarithmicOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.chooseLogarithmicOrder(p.a, p.b)));
        }
    }

    private void demoChoose_Iterable_Iterable_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("choose(" + p.a + ", " + p.b + ") = " + its(EP.choose(p.a, p.b)));
        }
    }

    private void demoChoose_Iterable_Iterable_infinite() {
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("choose(" + its(p.a) + ", " + its(p.b) + ") = " + its(EP.choose(p.a, p.b)));
        }
    }

    private void demoChoose_Iterable_finite() {
        Iterable<List<List<Integer>>> xsss = P.withScale(4).lists(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<List<Integer>> xss : take(LIMIT, xsss)) {
            String xsString = middle(xss.toString());
            System.out.println("choose(" + xsString + ") = " + its(EP.choose(toList(map(xs -> xs, xss)))));
        }
    }

    private void demoChoose_Iterable_infinite() {
        Iterable<List<Iterable<Integer>>> xsss = P.withScale(4).listsAtLeast(
                1,
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (List<Iterable<Integer>> xss : take(MEDIUM_LIMIT, xsss)) {
            String xssString = middle(toList(map(Testing::its, xss)).toString());
            System.out.println("choose(" + xssString + ") = " + its(EP.choose(xss)));
        }
    }

    private void demoCartesianProduct() {
        Iterable<List<List<Integer>>> xsss = P.withScale(4).lists(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<List<Integer>> xss : take(LIMIT, xsss)) {
            System.out.println("cartesianProduct(" + middle(xss.toString()) + ") = " + its(EP.cartesianProduct(xss)));
        }
    }

    private void demoRepeatingIterables_finite() {
        Iterable<List<Integer>> xss = filterInfinite(
                ys -> length(nub(ys)) > 1,
                P.withScale(4).listsAtLeast(2, P.withNull(P.integersGeometric()))
        );
        for (List<Integer> xs : take(SMALL_LIMIT, xss)) {
            System.out.println("repeatingIterables(" + middle(xs.toString()) + ") = " +
                    its(map(Testing::its, EP.repeatingIterables(xs))));
        }
    }

    private void demoRepeatingIterables_infinite() {
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(P.withNull(EP.naturalIntegers())))) {
            System.out.println("repeatingIterables(" + middle(its(xs)) + ") = " +
                    its(map(Testing::its, EP.repeatingIterables(xs))));
        }
    }

    private void demoRepeatingIterablesDistinctAtLeast_finite() {
        Iterable<Pair<Integer, List<Integer>>> ps = P.dependentPairsInfiniteLogarithmicOrder(
                P.withScale(3).rangeUpGeometric(2),
                i -> P.withScale(i + 1).distinctListsAtLeast(i, P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("repeatingIterablesDistinctAtLeast(" + p.a + ", " + p.b + ") = " +
                    its(map(Testing::its, EP.repeatingIterablesDistinctAtLeast(p.a, p.b))));
        }
    }

    private void demoRepeatingIterablesDistinctAtLeast_infinite() {
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(P.withNull(EP.naturalIntegers())),
                filterInfinite(i -> i < 10, P.withScale(3).rangeUpGeometric(2))
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("repeatingIterablesDistinctAtLeast(" + p.b + ", " + its(p.a) + ") = " +
                    its(map(Testing::its, EP.repeatingIterablesDistinctAtLeast(p.b, p.a))));
        }
    }

    private void demoSublists() {
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("sublists(" + middle(xs.toString()) + ") = " + its(EP.sublists(xs)));
        }
    }

    private void demoSubstrings() {
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("substrings(" + nicePrint(s) + ") = " + sits(EP.substrings(s)));
        }
    }

    private void demoListsWithElement_finite() {
        Iterable<Pair<Integer, List<Integer>>> ps = P.pairs(
                P.withNull(P.integersGeometric()),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Integer, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsWithElement(" + p.a + ", " + p.b + ") = " + its(EP.listsWithElement(p.a, p.b)));
        }
    }

    private void demoListsWithElement_infinite() {
        Iterable<Pair<Integer, Iterable<Integer>>> ps = P.pairs(
                P.withNull(P.integersGeometric()),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<Integer, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsWithElement(" + p.a + ", " + its(p.b) + ") = " +
                    its(EP.listsWithElement(p.a, p.b)));
        }
    }

    private void demoStringsWithChar_char_String() {
        for (Pair<Character, String> p : take(MEDIUM_LIMIT, P.pairs(P.characters(), P.withScale(4).strings()))) {
            System.out.println("stringsWithChar(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " +
                    sits(EP.stringsWithChar(p.a, p.b)));
        }
    }

    private void demoStringsWithChar_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("stringsWithChar(" + nicePrint(c) + ") = " + sits(EP.stringsWithChar(c)));
        }
    }

    private void demoSubsetsWithElement_finite() {
        Iterable<Pair<Integer, List<Integer>>> ps = P.pairs(
                P.integersGeometric(),
                P.withScale(4).lists(P.integersGeometric())
        );
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("subsetsWithElement(" + p.a + ", " + p.b + ") = " +
                    its(EP.subsetsWithElement(p.a, p.b)));
        }
    }

    private void demoSubsetsWithElement_infinite() {
        Iterable<Pair<Integer, Iterable<Integer>>> ps = P.pairs(
                P.integersGeometric(),
                P.prefixPermutations(EP.naturalIntegers())
        );
        for (Pair<Integer, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("subsetsWithElement(" + p.a + ", " + its(p.b) + ") = " +
                    its(EP.subsetsWithElement(p.a, p.b)));
        }
    }

    private void demoStringSubsetsWithChar_char_String() {
        for (Pair<Character, String> p : take(LIMIT, P.pairs(P.characters(), P.withScale(4).strings()))) {
            System.out.println("stringSubsetsWithChar(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " +
                    sits(EP.stringSubsetsWithChar(p.a, p.b)));
        }
    }

    private void demoStringSubsetsWithChar_char() {
        for (char c : take(MEDIUM_LIMIT, P.characters())) {
            System.out.println("stringSubsetsWithChar(" + nicePrint(c) + ") = " + sits(EP.stringSubsetsWithChar(c)));
        }
    }

    private void demoListsWithSublists_finite() {
        Iterable<List<Integer>> lists = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Pair<List<List<Integer>>, List<Integer>>> ps = P.pairs(P.withScale(4).lists(lists), lists);
        for (Pair<List<List<Integer>>, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsWithSublists(" + p.a + ", " + p.b + ") = " + its(EP.listsWithSublists(p.a, p.b)));
        }
    }

    private void demoListsWithSublists_infinite() {
        Iterable<Pair<List<List<Integer>>, Iterable<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withScale(4).lists(P.withNull(P.integersGeometric()))),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<List<Integer>>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("listsWithSublists(" + p.a + ", " + its(p.b) + ") = " +
                    its(EP.listsWithSublists(p.a, p.b)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String_String() {
        Iterable<String> strings = P.withScale(4).strings();
        for (Pair<List<String>, String> p : take(MEDIUM_LIMIT, P.pairs(P.withScale(4).lists(strings), strings))) {
            System.out.println("stringsWithSubstrings(" + toList(map(Testing::nicePrint, p.a)) + ", " +
                    nicePrint(p.b) + ") = " + sits(EP.stringsWithSubstrings(p.a, p.b)));
        }
    }

    private void demoStringsWithSubstrings_Iterable_String() {
        for (List<String> ss : take(MEDIUM_LIMIT, P.withScale(4).lists(P.withScale(4).strings()))) {
            System.out.println("stringsWithSubstrings(" + toList(map(Testing::nicePrint, ss)) + ") = " +
                    sits(EP.stringsWithSubstrings(ss)));
        }
    }

    private void demoMaps_finite() {
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("maps(" + p.a + ", " + p.b + ") = " + its(EP.maps(p.a, p.b)));
        }
    }

    private void demoMaps_infinite() {
        Iterable<Pair<List<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.withScale(4).distinctLists(P.withNull(P.integersGeometric())),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<Integer>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("maps(" + p.a + ", " + its(p.b) + ") = " + its(EP.maps(p.a, p.b)));
        }
    }

    private void demoIdentityMaps_finite() {
        Iterable<Pair<List<IntNoHashCode>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(map(IntNoHashCode::new, P.integersGeometric()))),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<IntNoHashCode>, List<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("identityMaps(" + p.a + ", " + p.b + ") = " + its(EP.identityMaps(p.a, p.b)));
        }
    }

    private void demoIdentityMaps_infinite() {
        Iterable<Pair<List<IntNoHashCode>, Iterable<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(map(IntNoHashCode::new, P.integersGeometric()))),
                P.prefixPermutations(P.withNull(EP.naturalIntegers()))
        );
        for (Pair<List<IntNoHashCode>, Iterable<Integer>> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("identityMaps(" + p.a + ", " + its(p.b) + ") = " + its(EP.identityMaps(p.a, p.b)));
        }
    }

    private void demoRandomProvidersFixedScales() {
        for (Triple<Integer, Integer, Integer> t : take(SMALL_LIMIT, P.triples(P.integersGeometric()))) {
            System.out.println("randomProvidersFixedScales(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(P.randomProvidersFixedScales(t.a, t.b, t.c)));
        }
    }
}
