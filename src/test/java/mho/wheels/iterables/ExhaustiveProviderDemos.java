package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.structures.*;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class ExhaustiveProviderDemos {
    private static final boolean USE_RANDOM = false;
    private static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final int SMALL_LIMIT = 1000;
    private static final int TINY_LIMIT = 100;
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

    private static void demoUniformSample_Iterable() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(is.toString()));
            System.out.println("uniformSample(" + listString + ") = " + its(EP.uniformSample(is)));
        }
    }

    private static void demoUniformSample_String() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("uniformSample(" + nicePrint(s) + ") = " + cits(EP.uniformSample(s)));
        }
    }

    private static void demoRangeUp_byte() {
        initialize();
        for (byte b : take(SMALL_LIMIT, P.bytes())) {
            System.out.println("rangeUp(" + b + ") = " + its(EP.rangeUp(b)));
        }
    }

    private static void demoRangeUp_short() {
        initialize();
        for (short s : take(SMALL_LIMIT, P.shorts())) {
            System.out.println("rangeUp(" + s + ") = " + its(EP.rangeUp(s)));
        }
    }

    private static void demoRangeUp_int() {
        initialize();
        for (int i : take(SMALL_LIMIT, P.integers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private static void demoRangeUp_long() {
        initialize();
        for (long l : take(SMALL_LIMIT, P.longs())) {
            System.out.println("rangeUp(" + l + ") = " + its(EP.rangeUp(l)));
        }
    }

    private static void demoRangeUp_BigInteger() {
        initialize();
        for (BigInteger i : take(SMALL_LIMIT, P.bigIntegers())) {
            System.out.println("rangeUp(" + i + ") = " + its(EP.rangeUp(i)));
        }
    }

    private static void demoRangeUp_char() {
        initialize();
        for (char c : take(SMALL_LIMIT, P.characters())) {
            System.out.println("rangeUp(" + nicePrint(c) + ") = " + cits(EP.rangeUp(c)));
        }
    }

    private static void demoRangeDown_byte() {
        initialize();
        for (byte b : take(SMALL_LIMIT, P.bytes())) {
            System.out.println("rangeDown(" + b + ") = " + its(EP.rangeDown(b)));
        }
    }

    private static void demoRangeDown_short() {
        initialize();
        for (short s : take(SMALL_LIMIT, P.shorts())) {
            System.out.println("rangeDown(" + s + ") = " + its(EP.rangeDown(s)));
        }
    }

    private static void demoRangeDown_int() {
        initialize();
        for (int i : take(SMALL_LIMIT, P.integers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private static void demoRangeDown_long() {
        initialize();
        for (long l : take(SMALL_LIMIT, P.longs())) {
            System.out.println("rangeDown(" + l + ") = " + its(EP.rangeDown(l)));
        }
    }

    private static void demoRangeDown_BigInteger() {
        initialize();
        for (BigInteger i : take(SMALL_LIMIT, P.bigIntegers())) {
            System.out.println("rangeDown(" + i + ") = " + its(EP.rangeDown(i)));
        }
    }

    private static void demoRangeDown_char() {
        initialize();
        for (char c : take(SMALL_LIMIT, P.characters())) {
            System.out.println("rangeDown(" + nicePrint(c) + ") = " + cits(EP.rangeDown(c)));
        }
    }

    private static void demoRange_byte_byte() {
        initialize();
        for (Pair<Byte, Byte> p : take(SMALL_LIMIT, P.pairs(P.bytes()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_short_short() {
        initialize();
        for (Pair<Short, Short> p : take(SMALL_LIMIT, P.pairs(P.shorts()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_int_int() {
        initialize();
        for (Pair<Integer, Integer> p : take(SMALL_LIMIT, P.pairs(P.integers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_long_long() {
        initialize();
        for (Pair<Long, Long> p : take(SMALL_LIMIT, P.pairs(P.longs()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_BigInteger_BigInteger() {
        initialize();
        for (Pair<BigInteger, BigInteger> p : take(SMALL_LIMIT, P.pairs(P.bigIntegers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRange_char_char() {
        initialize();
        for (Pair<Character, Character> p : take(SMALL_LIMIT, P.pairs(P.characters()))) {
            System.out.println("range(" + nicePrint(p.a) + ", " + nicePrint(p.b) + ") = " + cits(EP.range(p.a, p.b)));
        }
    }

    private static void demoRangeUp_BinaryFraction() {
        initialize();
        for (BinaryFraction bf : take(SMALL_LIMIT, P.binaryFractions())) {
            System.out.println("rangeUp(" + bf + ") = " + its(EP.rangeUp(bf)));
        }
    }

    private static void demoRangeDown_BinaryFraction() {
        initialize();
        for (BinaryFraction bf : take(SMALL_LIMIT, P.binaryFractions())) {
            System.out.println("rangeDown(" + bf + ") = " + its(EP.rangeDown(bf)));
        }
    }

    private static void demoRange_BinaryFraction_BinaryFraction() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(SMALL_LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRangeUp_float() {
        initialize();
        for (float f : take(SMALL_LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("rangeUp(" + f + ") = " + its(EP.rangeUp(f)));
        }
    }

    private static void demoRangeDown_float() {
        initialize();
        for (float f : take(SMALL_LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("rangeDown(" + f + ") = " + its(EP.rangeDown(f)));
        }
    }

    private static void demoRange_float_float() {
        initialize();
        for (Pair<Float, Float> p : take(SMALL_LIMIT, P.pairs(filter(f -> !Float.isNaN(f), P.floats())))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRangeUp_double() {
        initialize();
        for (double d : take(SMALL_LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("rangeUp(" + d + ") = " + its(EP.rangeUp(d)));
        }
    }

    private static void demoRangeDown_double() {
        initialize();
        for (double d : take(SMALL_LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("rangeDown(" + d + ") = " + its(EP.rangeDown(d)));
        }
    }

    private static void demoRange_double_double() {
        initialize();
        for (Pair<Double, Double> p : take(SMALL_LIMIT, P.pairs(filter(d -> !Double.isNaN(d), P.doubles())))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRangeUp_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(SMALL_LIMIT, P.bigDecimals())) {
            System.out.println("rangeUp(" + bd + ") = " + its(EP.rangeUp(bd)));
        }
    }

    private static void demoRangeDown_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(SMALL_LIMIT, P.bigDecimals())) {
            System.out.println("rangeDown(" + bd + ") = " + its(EP.rangeDown(bd)));
        }
    }

    private static void demoRange_BigDecimal_BigDecimal() {
        initialize();
        for (Pair<BigDecimal, BigDecimal> p : take(SMALL_LIMIT, P.pairs(P.bigDecimals()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " + its(EP.range(p.a, p.b)));
        }
    }

    private static void demoRangeUpCanonical_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(SMALL_LIMIT, P.bigDecimals())) {
            System.out.println("rangeUpCanonical(" + bd + ") = " + its(EP.rangeUpCanonical(bd)));
        }
    }

    private static void demoRangeDownCanonical_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(SMALL_LIMIT, P.bigDecimals())) {
            System.out.println("rangeDownCanonical(" + bd + ") = " + its(EP.rangeDownCanonical(bd)));
        }
    }

    private static void demoRangeCanonical_BigDecimal_BigDecimal() {
        initialize();
        for (Pair<BigDecimal, BigDecimal> p : take(SMALL_LIMIT, P.pairs(P.bigDecimals()))) {
            System.out.println("rangeCanonical(" + p.a + ", " + p.b + ") = " + its(EP.rangeCanonical(p.a, p.b)));
        }
    }

    private static void demoWithNull_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("withNull(" + xs + ") = " + its(EP.withNull(xs)));
        }
    }

    private static void demoWithNull_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            String xsString = tail(init(its(xs)));
            System.out.println("withNull(" + xsString + ") = " + its(EP.withNull(xs)));
        }
    }

    private static void demoNonEmptyOptionals_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("nonEmptyOptionals(" + xs + ") = " + its(EP.nonEmptyOptionals(xs)));
        }
    }

    private static void demoNonEmptyOptionals_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            String xsString = tail(init(its(xs)));
            System.out.println("nonEmptyOptionals(" + xsString + ") = " + its(EP.nonEmptyOptionals(xs)));
        }
    }

    private static void demoOptionals_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("optionals(" + xs + ") = " + its(EP.optionals(xs)));
        }
    }

    private static void demoOptionals_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            String xsString = tail(init(its(xs)));
            System.out.println("optionals(" + xsString + ") = " + its(EP.optionals(xs)));
        }
    }

    private static void demoNonEmptyNullableOptionals_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("nonEmptyNullableOptionals(" + xs + ") = " + its(EP.nonEmptyNullableOptionals(xs)));
        }
    }

    private static void demoNonEmptyNullableOptionals_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            String xsString = tail(init(its(xs)));
            System.out.println("nonEmptyNullableOptionals(" + xsString + ") = " +
                    its(EP.nonEmptyNullableOptionals(xs)));
        }
    }

    private static void demoNullableOptionals_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("nullableOptionals(" + xs + ") = " + its(EP.nullableOptionals(xs)));
        }
    }

    private static void demoNullableOptionals_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            String xsString = tail(init(its(xs)));
            System.out.println("nullableOptionals(" + xsString + ") = " + its(EP.nullableOptionals(xs)));
        }
    }

    private static void demoDependentPairs_finite() {
        initialize();
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

    private static void demoDependentPairs_cyclic() {
        initialize();
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
        for (Pair<Iterable<Integer>, Function<Integer, Iterable<Integer>>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("dependentPairs(" + its(p.a) + ", " + p.b + ") = " + its(EP.dependentPairs(p.a, p.b)));
        }
    }

    private static void demoDependentPairsInfinite() {
        initialize();
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
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(SMALL_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfinite(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfinite(p.a, p.b)));
        }
    }

    private static void demoDependentPairsInfiniteLogarithmicOrder() {
        initialize();
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
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(SMALL_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfiniteLogarithmicOrder(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfiniteLogarithmicOrder(p.a, p.b)));
        }
    }

    private static void demoDependentPairsInfiniteSquareRootOrder() {
        initialize();
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
        for (Pair<Iterable<Integer>, FiniteDomainFunction<Integer, Iterable<Integer>>> p : take(SMALL_LIMIT, ps)) {
            String niceFunction = toMap(map(q -> new Pair<>(q.a, its(q.b)), fromMap(p.b.asMap()))).toString();
            System.out.println("dependentPairsInfiniteSquareRootOrder(" + its(p.a) + ", " + niceFunction + ") = " +
                    its(EP.dependentPairsInfiniteSquareRootOrder(p.a, p.b)));
        }
    }

    private static void demoPairsLogarithmicOrder_Iterable_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsLogarithmicOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.pairsLogarithmicOrder(p.a, p.b)));
        }
    }

    private static void demoPairsLogarithmicOrder_Iterable_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("pairsLogarithmicOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.pairsLogarithmicOrder(p.a, p.b)));
        }
    }

    private static void demoPairsLogarithmicOrder_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(xs.toString()));
            System.out.println("pairsLogarithmicOrder(" + listString + ") = " + its(EP.pairsLogarithmicOrder(xs)));
        }
    }

    private static void demoPairsLogarithmicOrder_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            String listString = tail(init(its(xs)));
            System.out.println("pairsLogarithmicOrder(" + listString + ") = " + its(EP.pairsLogarithmicOrder(xs)));
        }
    }

    private static void demoPairsSquareRootOrder_Iterable_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsSquareRootOrder(" + p.a + ", " + p.b + ") = " +
                    its(EP.pairsSquareRootOrder(p.a, p.b)));
        }
    }

    private static void demoPairsSquareRootOrder_Iterable_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("pairsSquareRootOrder(" + its(p.a) + ", " + its(p.b) + ") = " +
                    its(EP.pairsSquareRootOrder(p.a, p.b)));
        }
    }

    private static void demoPairsSquareRootOrder_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(xs.toString()));
            System.out.println("pairsSquareRootOrder(" + listString + ") = " + its(EP.pairsSquareRootOrder(xs)));
        }
    }

    private static void demoPairsSquareRootOrder_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            String listString = tail(init(its(xs)));
            System.out.println("pairsSquareRootOrder(" + listString + ") = " + its(EP.pairsSquareRootOrder(xs)));
        }
    }

    private static void demoPermutationsFinite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(xs.toString()));
            System.out.println("permutationsFinite(" + listString + ") = " + its(EP.permutationsFinite(xs)));
        }
    }

    private static void demoStringPermutations() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringPermutations(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringPermutations(s))));
        }
    }

    private static void demoPrefixPermutations_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            String listString = tail(init(xs.toString()));
            System.out.println("prefixPermutations(" + listString + ") = " +
                    its(map(Testing::its, EP.prefixPermutations(xs))));
        }
    }

    private static void demoPrefixPermutations_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(TINY_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            String listString = tail(init(its(xs)));
            System.out.println("prefixPermutations(" + listString + ") = " +
                    its(map(Testing::its, EP.prefixPermutations(xs))));
        }
    }

    private static void demoListsLex_int_List() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("listsLex(" + p.b + ", " + p.a + ") = " + its(EP.listsLex(p.b, p.a)));
        }
    }

    private static void demoPairsLex_finite() {
        initialize();
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairsLex(" + p.a + ", " + p.b + ") = " + its(EP.pairsLex(p.a, p.b)));
        }
    }

    private static void demoPairsLex_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, List<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<Iterable<Integer>, List<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("pairsLex(" + its(p.a) + ", " + p.b + ") = " + its(EP.pairsLex(p.a, p.b)));
        }
    }

    private static void demoTriplesLex_finite() {
        initialize();
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(SMALL_LIMIT, ts)) {
            System.out.println("triplesLex(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    its(EP.triplesLex(t.a, t.b, t.c)));
        }
    }

    private static void demoTriplesLex_infinite() {
        initialize();
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Triple<Iterable<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs
        );
        for (Triple<Iterable<Integer>, List<Integer>, List<Integer>> t : take(SMALL_LIMIT, ts)) {
            System.out.println("triplesLex(" + its(t.a) + ", " + t.b + ", " + t.c + ") = " +
                    its(EP.triplesLex(t.a, t.b, t.c)));
        }
    }

    private static void demoQuadruplesLex_finite() {
        initialize();
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(SMALL_LIMIT, qs)) {
            System.out.println("quadruplesLex(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruplesLex(q.a, q.b, q.c, q.d)));
        }
    }

    private static void demoQuadruplesLex_infinite() {
        initialize();
        Iterable<List<Integer>> finiteArgs = P.withScale(4).lists(P.withNull(P.integersGeometric()));
        Iterable<Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                finiteArgs,
                finiteArgs,
                finiteArgs
        );
        for (Quadruple<Iterable<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(SMALL_LIMIT, qs)) {
            System.out.println("quadruplesLex(" + its(q.a) + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruplesLex(q.a, q.b, q.c, q.d)));
        }
    }

    private static void demoQuintuplesLex_finite() {
        initialize();
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
        > q : take(SMALL_LIMIT, qs)) {
            System.out.println("quintuplesLex(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private static void demoQuintuplesLex_infinite() {
        initialize();
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
        > q : take(SMALL_LIMIT, qs)) {
            System.out.println(
                    "quintuplesLex(" + its(q.a) + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuplesLex(q.a, q.b, q.c, q.d, q.e))
            );
        }
    }

    private static void demoSextuplesLex_finite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "sextuplesLex(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ") = " +
                    its(EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f))
            );
        }
    }

    private static void demoSextuplesLex_infinite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "sextuplesLex(" +
                    its(s.a) + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ") = " +
                    its(EP.sextuplesLex(s.a, s.b, s.c, s.d, s.e, s.f))
            );
        }
    }

    private static void demoSeptuplesLex_finite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "septuplesLex(" +
                    s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    its(EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g))
            );
        }
    }

    private static void demoSeptuplesLex_infinite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "septuplesLex(" +
                    its(s.a) + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    its(EP.septuplesLex(s.a, s.b, s.c, s.d, s.e, s.f, s.g))
            );
        }
    }

    private static void demoStringsLex_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringsLex(p.b, p.a))));
        }
    }

    private static void demoListsShortlex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("listsShortlex(" + xs + ") = " + its(EP.listsShortlex(xs)));
        }
    }

    private static void demoStringsShortlex() {
        initialize();
        for (String s : take(SMALL_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringsShortlex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringsShortlex(s))));
        }
    }

    private static void demoListsShortlexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("listsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.listsShortlexAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringsShortlexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("stringsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringsShortlexAtLeast(p.b, p.a))));
        }
    }

    private static void demoLists_int_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("lists(" + p.b + ", " + p.a + ") = " + its(EP.lists(p.b, p.a)));
        }
    }

    private static void demoLists_int_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("lists(" + p.b + ", " + its(p.a) + ") = " + its(EP.lists(p.b, p.a)));
        }
    }

    private static void demoPairs_Iterable_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, List<Integer>>> ps = P.pairs(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("pairs(" + p.a + ", " + p.b + ") = " + its(EP.pairs(p.a, p.b)));
        }
    }

    private static void demoPairs_Iterable_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Iterable<Integer>>> ps = P.pairs(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Pair<Iterable<Integer>, Iterable<Integer>> p : take(SMALL_LIMIT, ps)) {
            System.out.println("pairs(" + its(p.a) + ", " + its(p.b) + ") = " + its(EP.pairs(p.a, p.b)));
        }
    }

    private static void demoPairs_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("pairs(" + xs + ") = " + its(EP.pairs(xs)));
        }
    }

    private static void demoPairs_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("pairs(" + its(xs) + ") = " + its(EP.pairs(xs)));
        }
    }

    private static void demoTriples_Iterable_Iterable_Iterable_finite() {
        initialize();
        Iterable<Triple<List<Integer>, List<Integer>, List<Integer>>> ts = P.triples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Triple<List<Integer>, List<Integer>, List<Integer>> t : take(SMALL_LIMIT, ts)) {
            System.out.println("triples(" + t.a + ", " + t.b + ", " + t.c + ") = " + its(EP.triples(t.a, t.b, t.c)));
        }
    }

    private static void demoTriples_Iterable_Iterable_Iterable_infinite() {
        initialize();
        Iterable<Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> ts = P.triples(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
        );
        for (Triple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>> t : take(SMALL_LIMIT, ts)) {
            System.out.println("triples(" + its(t.a) + ", " + its(t.b) + ", " + its(t.c) + ") = " +
                    its(EP.triples(t.a, t.b, t.c)));
        }
    }

    private static void demoTriples_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("triples(" + xs + ") = " + its(EP.triples(xs)));
        }
    }

    private static void demoTriples_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("triples(" + its(xs) + ") = " + its(EP.triples(xs)));
        }
    }

    private static void demoQuadruples_Iterable_Iterable_Iterable_Iterable_finite() {
        initialize();
        Iterable<Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>>> qs = P.quadruples(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (Quadruple<List<Integer>, List<Integer>, List<Integer>, List<Integer>> q : take(SMALL_LIMIT, qs)) {
            System.out.println("quadruples(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ") = " +
                    its(EP.quadruples(q.a, q.b, q.c, q.d)));
        }
    }

    private static void demoQuadruples_Iterable_Iterable_Iterable_Iterable_infinite() {
        initialize();
        Iterable<Quadruple<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> qs =
                P.quadruples(
                        P.prefixPermutations(EP.withNull(EP.naturalIntegers()))
                );
        for (Quadruple<
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>,
                Iterable<Integer>
        > q : take(SMALL_LIMIT, qs)) {
            System.out.println(
                    "quadruples(" + its(q.a) + ", " + its(q.b) + ", " + its(q.c) + ", " + its(q.d) + ") = " +
                    its(EP.quadruples(q.a, q.b, q.c, q.d)));
        }
    }

    private static void demoQuadruples_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("quadruples(" + xs + ") = " + its(EP.quadruples(xs)));
        }
    }

    private static void demoQuadruples_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("quadruples(" + its(xs) + ") = " + its(EP.quadruples(xs)));
        }
    }

    private static void demoQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        initialize();
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
        > q : take(SMALL_LIMIT, qs)) {
            System.out.println("quintuples(" + q.a + ", " + q.b + ", " + q.c + ", " + q.d + ", " + q.e + ") = " +
                    its(EP.quintuples(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private static void demoQuintuples_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        initialize();
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
        > q : take(SMALL_LIMIT, qs)) {
            System.out.println(
                    "quintuples(" + its(q.a) + ", " + its(q.b) + ", " + its(q.c) + ", " + its(q.d) + ", " + its(q.e) +
                    ") = " + its(EP.quintuples(q.a, q.b, q.c, q.d, q.e)));
        }
    }

    private static void demoQuintuples_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("quintuples(" + xs + ") = " + its(EP.quintuples(xs)));
        }
    }

    private static void demoQuintuples_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("quintuples(" + its(xs) + ") = " + its(EP.quintuples(xs)));
        }
    }

    private static void demoSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println("sextuples(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f +
                    ") = " + its(EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f)));
        }
    }

    private static void demoSextuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println(
                    "sextuples(" + its(s.a) + ", " + its(s.b) + ", " + its(s.c) + ", " + its(s.d) + ", " + its(s.e) +
                    ", " + its(s.f) + ") = " + its(EP.sextuples(s.a, s.b, s.c, s.d, s.e, s.f)));
        }
    }

    private static void demoSextuples_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("sextuples(" + xs + ") = " + its(EP.sextuples(xs)));
        }
    }

    private static void demoSextuples_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("sextuples(" + its(xs) + ") = " + its(EP.sextuples(xs)));
        }
    }

    private static void demoSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_finite() {
        initialize();
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
        > s : take(SMALL_LIMIT, ss)) {
            System.out.println("septuples(" + s.a + ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f +
                    ", " + s.g + ") = " + its(EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g)));
        }
    }

    private static void demoSeptuples_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_Iterable_infinite() {
        initialize();
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
        > s : take(TINY_LIMIT, ss)) {
            System.out.println(
                    "septuples(" + its(s.a) + ", " + its(s.b) + ", " + its(s.c) + ", " + its(s.d) + ", " + its(s.e) +
                    ", " + its(s.f) + ", " + its(s.g) + ") = " + its(EP.septuples(s.a, s.b, s.c, s.d, s.e, s.f, s.g)));
        }
    }

    private static void demoSeptuples_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("septuples(" + xs + ") = " + its(EP.septuples(xs)));
        }
    }

    private static void demoSeptuples_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("septuples(" + its(xs) + ") = " + its(EP.septuples(xs)));
        }
    }

    private static void demoStrings_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("strings(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.strings(p.b, p.a))));
        }
    }

    private static void demoStrings_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("strings(" + i + ") = " + its(map(Testing::nicePrint, EP.strings(i))));
        }
    }

    private static void demoLists_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("lists(" + xs + ") = " + its(EP.lists(xs)));
        }
    }

    private static void demoLists_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("lists(" + its(xs) + ") = " + its(EP.lists(xs)));
        }
    }

    private static void demoStrings_String() {
        initialize();
        for (String s : take(SMALL_LIMIT, P.withScale(4).strings())) {
            System.out.println("strings(" + nicePrint(s) + ") = " + its(map(Testing::nicePrint, EP.strings(s))));
        }
    }

    private static void demoListsAtLeast_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("listsAtLeast(" + p.b + ", " + p.a + ") = " + its(EP.listsAtLeast(p.b, p.a)));
        }
    }

    private static void demoListsAtLeast_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("listsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.listsAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringsAtLeast_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("stringsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringsAtLeast(p.b, p.a))));
        }
    }

    private static void demoStringsAtLeast_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringsAtLeast(" + i + ") = " + its(map(Testing::nicePrint, EP.stringsAtLeast(i))));
        }
    }

    private static void demoDistinctListsLex_int_List() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctListsLex(" + p.b + ", " + p.a + ") = " + its(EP.distinctListsLex(p.b, p.a)));
        }
    }

    private static void demoDistinctPairsLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctPairsLex(" + xs + ") = " + its(EP.distinctPairsLex(xs)));
        }
    }

    private static void demoDistinctTriplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctTriplesLex(" + xs + ") = " + its(EP.distinctTriplesLex(xs)));
        }
    }

    private static void demoDistinctQuadruplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuadruplesLex(" + xs + ") = " + its(EP.distinctQuadruplesLex(xs)));
        }
    }

    private static void demoDistinctQuintuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuintuplesLex(" + xs + ") = " + its(EP.distinctQuintuplesLex(xs)));
        }
    }

    private static void demoDistinctSextuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSextuplesLex(" + xs + ") = " + its(EP.distinctSextuplesLex(xs)));
        }
    }

    private static void demoDistinctSeptuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSeptuplesLex(" + xs + ") = " + its(EP.distinctSeptuplesLex(xs)));
        }
    }

    private static void demoDistinctStringsLex_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsLex(p.b, p.a))));
        }
    }

    private static void demoDistinctListsLex_List() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctListsLex(" + xs + ") = " + its(EP.distinctListsLex(xs)));
        }
    }

    private static void demoDistinctStringsLex_String() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStringsLex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsLex(s))));
        }
    }

    private static void demoDistinctListsLexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("distinctListsLexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsLexAtLeast(p.b, p.a)));
        }
    }

    private static void demoDistinctStringsLexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsLexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsLexAtLeast(p.b, p.a))));
        }
    }

    private static void demoDistinctListsShortlex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctListsShortlex(" + xs + ") = " + its(EP.distinctListsShortlex(xs)));
        }
    }

    private static void demoDistinctStringsShortlex() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStringsShortlex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsShortlex(s))));
        }
    }

    private static void demoDistinctListsShortlexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("distinctListsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsShortlexAtLeast(p.b, p.a)));
        }
    }

    private static void demoDistinctStringsShortlexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsShortlexAtLeast(p.b, p.a))));
        }
    }

    private static void demoDistinctLists_int_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctLists(" + p.b + ", " + p.a + ") = " + its(EP.distinctLists(p.b, p.a)));
        }
    }

    private static void demoDistinctLists_int_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("distinctLists(" + p.b + ", " + its(p.a) + ") = " + its(EP.distinctLists(p.b, p.a)));
        }
    }

    private static void demoDistinctPairs_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctPairs(" + xs + ") = " + its(EP.distinctPairs(xs)));
        }
    }

    private static void demoDistinctPairs_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctPairs(" + its(xs) + ") = " + its(EP.distinctPairs(xs)));
        }
    }

    private static void demoDistinctTriples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctTriples(" + xs + ") = " + its(EP.distinctTriples(xs)));
        }
    }

    private static void demoDistinctTriples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctTriples(" + its(xs) + ") = " + its(EP.distinctTriples(xs)));
        }
    }

    private static void demoDistinctQuadruples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuadruples(" + xs + ") = " + its(EP.distinctQuadruples(xs)));
        }
    }

    private static void demoDistinctQuadruples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctQuadruples(" + its(xs) + ") = " + its(EP.distinctQuadruples(xs)));
        }
    }

    private static void demoDistinctQuintuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctQuintuples(" + xs + ") = " + its(EP.distinctQuintuples(xs)));
        }
    }

    private static void demoDistinctQuintuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctQuintuples(" + its(xs) + ") = " + its(EP.distinctQuintuples(xs)));
        }
    }

    private static void demoDistinctSextuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSextuples(" + xs + ") = " + its(EP.distinctSextuples(xs)));
        }
    }

    private static void demoDistinctSextuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctSextuples(" + its(xs) + ") = " + its(EP.distinctSextuples(xs)));
        }
    }

    private static void demoDistinctSeptuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctSeptuples(" + xs + ") = " + its(EP.distinctSeptuples(xs)));
        }
    }

    private static void demoDistinctSeptuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctSeptuples(" + its(xs) + ") = " + its(EP.distinctSeptuples(xs)));
        }
    }

    private static void demoDistinctStrings_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStrings(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStrings(p.b, p.a))));
        }
    }

    private static void demoDistinctStrings_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("distinctStrings(" + i + ") = " + its(map(Testing::nicePrint, EP.distinctStrings(i))));
        }
    }

    private static void demoDistinctLists_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.withNull(P.integersGeometric())))) {
            System.out.println("distinctLists(" + xs + ") = " + its(EP.distinctLists(xs)));
        }
    }

    private static void demoDistinctLists_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.withNull(EP.naturalIntegers())))) {
            System.out.println("distinctLists(" + its(xs) + ") = " + its(EP.distinctLists(xs)));
        }
    }

    private static void demoDistinctStrings_String() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("distinctStrings(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStrings(s))));
        }
    }

    private static void demoDistinctListsAtLeast_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.withNull(P.integersGeometric())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctListsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.distinctListsAtLeast(p.b, p.a)));
        }
    }

    private static void demoDistinctListsAtLeast_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.withNull(EP.naturalIntegers())),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("distinctListsAtLeast(" + p.b + ", " + its(p.a) + ") = " +
                    its(EP.distinctListsAtLeast(p.b, p.a)));
        }
    }

    private static void demoDistinctStringsAtLeast_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("distinctStringsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsAtLeast(p.b, p.a))));
        }
    }

    private static void demoDistinctStringsAtLeast_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("distinctStringsAtLeast(" + i + ") = " +
                    its(map(Testing::nicePrint, EP.distinctStringsAtLeast(i))));
        }
    }

    private static void demoBagsLex_int_List() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("bagsLex(" + p.b + ", " + p.a + ") = " + its(EP.bagsLex(p.b, p.a)));
        }
    }

    private static void demoBagPairsLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagPairsLex(" + xs + ") = " + its(EP.bagPairsLex(xs)));
        }
    }

    private static void demoBagTriplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagTriplesLex(" + xs + ") = " + its(EP.bagTriplesLex(xs)));
        }
    }

    private static void demoBagQuadruplesLex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuadruplesLex(" + xs + ") = " + its(EP.bagQuadruplesLex(xs)));
        }
    }

    private static void demoBagQuintuplesLex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuintuplesLex(" + xs + ") = " + its(EP.bagQuintuplesLex(xs)));
        }
    }

    private static void demoBagSextuplesLex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSextuplesLex(" + xs + ") = " + its(EP.bagSextuplesLex(xs)));
        }
    }

    private static void demoBagSeptuplesLex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSeptuplesLex(" + xs + ") = " + its(EP.bagSeptuplesLex(xs)));
        }
    }

    private static void demoStringBagsLex() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringBagsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringBagsLex(p.b, p.a))));
        }
    }

    private static void demoBagsShortlex() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagsShortlex(" + xs + ") = " + its(EP.bagsShortlex(xs)));
        }
    }

    private static void demoStringBagsShortlex() {
        initialize();
        for (String s : take(SMALL_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringBagsShortlex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringBagsShortlex(s))));
        }
    }

    private static void demoBagsShortlexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("bagsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.bagsShortlexAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringBagsShortlexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("stringBagsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringBagsShortlexAtLeast(p.b, p.a))));
        }
    }

    private static void demoBags_int_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("bags(" + p.b + ", " + p.a + ") = " + its(EP.bags(p.b, p.a)));
        }
    }

    private static void demoBags_int_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("bags(" + p.b + ", " + its(p.a) + ") = " + its(EP.bags(p.b, p.a)));
        }
    }

    private static void demoBagPairs_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagPairs(" + xs + ") = " + its(EP.bagPairs(xs)));
        }
    }

    private static void demoBagPairs_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagPairs(" + its(xs) + ") = " + its(EP.bagPairs(xs)));
        }
    }

    private static void demoBagTriples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagTriples(" + xs + ") = " + its(EP.bagTriples(xs)));
        }
    }

    private static void demoBagTriples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagTriples(" + its(xs) + ") = " + its(EP.bagTriples(xs)));
        }
    }

    private static void demoBagQuadruples_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuadruples(" + xs + ") = " + its(EP.bagQuadruples(xs)));
        }
    }

    private static void demoBagQuadruples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagQuadruples(" + its(xs) + ") = " + its(EP.bagQuadruples(xs)));
        }
    }

    private static void demoBagQuintuples_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagQuintuples(" + xs + ") = " + its(EP.bagQuintuples(xs)));
        }
    }

    private static void demoBagQuintuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagQuintuples(" + its(xs) + ") = " + its(EP.bagQuintuples(xs)));
        }
    }

    private static void demoBagSextuples_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSextuples(" + xs + ") = " + its(EP.bagSextuples(xs)));
        }
    }

    private static void demoBagSextuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagSextuples(" + its(xs) + ") = " + its(EP.bagSextuples(xs)));
        }
    }

    private static void demoBagSeptuples_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bagSeptuples(" + xs + ") = " + its(EP.bagSeptuples(xs)));
        }
    }

    private static void demoBagSeptuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bagSeptuples(" + its(xs) + ") = " + its(EP.bagSeptuples(xs)));
        }
    }

    private static void demoStringBags_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringBags(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringBags(p.b, p.a))));
        }
    }

    private static void demoStringBags_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringBags(" + i + ") = " + its(map(Testing::nicePrint, EP.stringBags(i))));
        }
    }

    private static void demoBags_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(SMALL_LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("bags(" + xs + ") = " + its(EP.bags(xs)));
        }
    }

    private static void demoBags_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("bags(" + its(xs) + ") = " + its(EP.bags(xs)));
        }
    }

    private static void demoStringBags_String() {
        initialize();
        for (String s : take(SMALL_LIMIT, P.withScale(4).strings())) {
            System.out.println("stringBags(" + nicePrint(s) + ") = " + its(map(Testing::nicePrint, EP.stringBags(s))));
        }
    }

    private static void demoBagsAtLeast_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("bagsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.bagsAtLeast(p.b, p.a)));
        }
    }

    private static void demoBagsAtLeast_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("bagsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.bagsAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringBagsAtLeast_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("stringBagsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringBagsAtLeast(p.b, p.a))));
        }
    }

    private static void demoStringBagsAtLeast_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringBagsAtLeast(" + i + ") = " +
                    its(map(Testing::nicePrint, EP.stringBagsAtLeast(i))));
        }
    }

    private static void demoSubsetsLex_int_List() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsLex(" + p.b + ", " + p.a + ") = " + its(EP.subsetsLex(p.b, p.a)));
        }
    }

    private static void demoSubsetPairsLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetPairsLex(" + xs + ") = " + its(EP.subsetPairsLex(xs)));
        }
    }

    private static void demoSubsetTriplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetTriplesLex(" + xs + ") = " + its(EP.subsetTriplesLex(xs)));
        }
    }

    private static void demoSubsetQuadruplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuadruplesLex(" + xs + ") = " + its(EP.subsetQuadruplesLex(xs)));
        }
    }

    private static void demoSubsetQuintuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuintuplesLex(" + xs + ") = " + its(EP.subsetQuintuplesLex(xs)));
        }
    }

    private static void demoSubsetSextuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSextuplesLex(" + xs + ") = " + its(EP.subsetSextuplesLex(xs)));
        }
    }

    private static void demoSubsetSeptuplesLex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSeptuplesLex(" + xs + ") = " + its(EP.subsetSeptuplesLex(xs)));
        }
    }

    private static void demoStringSubsetsLex_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsLex(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsLex(p.b, p.a))));
        }
    }

    private static void demoSubsetsLex_List() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetsLex(" + xs + ") = " + its(EP.subsetsLex(xs)));
        }
    }

    private static void demoStringSubsetsLex_String() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsetsLex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsLex(s))));
        }
    }

    private static void demoSubsetsLexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsLexAtLeast(" + p.b + ", " + p.a + ") = " + its(EP.subsetsLexAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringSubsetsLexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsLexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsLexAtLeast(p.b, p.a))));
        }
    }

    private static void demoSubsetsShortlex() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetsShortlex(" + xs + ") = " + its(EP.subsetsShortlex(xs)));
        }
    }

    private static void demoStringSubsetsShortlex() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsetsShortlex(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsShortlex(s))));
        }
    }

    private static void demoSubsetsShortlexAtLeast() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsShortlexAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.subsetsShortlexAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringSubsetsShortlexAtLeast() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsShortlexAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsShortlexAtLeast(p.b, p.a))));
        }
    }

    private static void demoSubsets_int_Iterable_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsets(" + p.b + ", " + p.a + ") = " + its(EP.subsets(p.b, p.a)));
        }
    }

    private static void demoSubsets_int_Iterable_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("subsets(" + p.b + ", " + its(p.a) + ") = " + its(EP.subsets(p.b, p.a)));
        }
    }

    private static void demoSubsetPairs_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetPairs(" + xs + ") = " + its(EP.subsetPairs(xs)));
        }
    }

    private static void demoSubsetPairs_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetPairs(" + its(xs) + ") = " + its(EP.subsetPairs(xs)));
        }
    }

    private static void demoSubsetTriples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetTriples(" + xs + ") = " + its(EP.subsetTriples(xs)));
        }
    }

    private static void demoSubsetTriples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetTriples(" + its(xs) + ") = " + its(EP.subsetTriples(xs)));
        }
    }

    private static void demoSubsetQuadruples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuadruples(" + xs + ") = " + its(EP.subsetQuadruples(xs)));
        }
    }

    private static void demoSubsetQuadruples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetQuadruples(" + its(xs) + ") = " + its(EP.subsetQuadruples(xs)));
        }
    }

    private static void demoSubsetQuintuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetQuintuples(" + xs + ") = " + its(EP.subsetQuintuples(xs)));
        }
    }

    private static void demoSubsetQuintuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetQuintuples(" + its(xs) + ") = " + its(EP.subsetQuintuples(xs)));
        }
    }

    private static void demoSubsetSextuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSextuples(" + xs + ") = " + its(EP.subsetSextuples(xs)));
        }
    }

    private static void demoSubsetSextuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetSextuples(" + its(xs) + ") = " + its(EP.subsetSextuples(xs)));
        }
    }

    private static void demoSubsetSeptuples_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsetSeptuples(" + xs + ") = " + its(EP.subsetSeptuples(xs)));
        }
    }

    private static void demoSubsetSeptuples_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsetSeptuples(" + its(xs) + ") = " + its(EP.subsetSeptuples(xs)));
        }
    }

    private static void demoStringSubsets_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsets(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsets(p.b, p.a))));
        }
    }

    private static void demoStringSubsets_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringSubsets(" + i + ") = " + its(map(Testing::nicePrint, EP.stringSubsets(i))));
        }
    }

    private static void demoSubsets_Iterable_finite() {
        initialize();
        for (List<Integer> xs : take(LIMIT, P.withScale(4).lists(P.integersGeometric()))) {
            System.out.println("subsets(" + xs + ") = " + its(EP.subsets(xs)));
        }
    }

    private static void demoSubsets_Iterable_infinite() {
        initialize();
        for (Iterable<Integer> xs : take(SMALL_LIMIT, P.prefixPermutations(EP.naturalIntegers()))) {
            System.out.println("subsets(" + its(xs) + ") = " + its(EP.subsets(xs)));
        }
    }

    private static void demoStringSubsets_String() {
        initialize();
        for (String s : take(LIMIT, P.withScale(4).strings())) {
            System.out.println("stringSubsets(" + nicePrint(s) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsets(s))));
        }
    }

    private static void demoSubsetsAtLeast_finite() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).lists(P.integersGeometric()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("subsetsAtLeast(" + p.b + ", " + p.a + ") = " +
                    its(EP.subsetsAtLeast(p.b, p.a)));
        }
    }

    private static void demoSubsetsAtLeast_infinite() {
        initialize();
        Iterable<Pair<Iterable<Integer>, Integer>> ps = P.pairsLogarithmicOrder(
                P.prefixPermutations(EP.naturalIntegers()),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<Iterable<Integer>, Integer> p : take(SMALL_LIMIT, ps)) {
            System.out.println("subsetsAtLeast(" + p.b + ", " + its(p.a) + ") = " + its(EP.subsetsAtLeast(p.b, p.a)));
        }
    }

    private static void demoStringSubsetsAtLeast_int_String() {
        initialize();
        Iterable<Pair<String, Integer>> ps = P.pairsLogarithmicOrder(
                P.withScale(4).strings(),
                P.withScale(4).naturalIntegersGeometric()
        );
        for (Pair<String, Integer> p : take(LIMIT, ps)) {
            System.out.println("stringSubsetsAtLeast(" + p.b + ", " + nicePrint(p.a) + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsAtLeast(p.b, p.a))));
        }
    }

    private static void demoStringSubsetsAtLeast_int() {
        initialize();
        for (int i : take(TINY_LIMIT, P.withScale(4).naturalIntegersGeometric())) {
            System.out.println("stringSubsetsAtLeast(" + i + ") = " +
                    its(map(Testing::nicePrint, EP.stringSubsetsAtLeast(i))));
        }
    }

    private static void demoCartesianProduct() {
        initialize();
        Iterable<List<List<Integer>>> xsss = P.withScale(4).lists(
                P.withScale(4).lists(P.withNull(P.integersGeometric()))
        );
        for (List<List<Integer>> xss : take(LIMIT, xsss)) {
            String listString = tail(init(xss.toString()));
            System.out.println("cartesianProduct(" + listString + ") = " + its(EP.cartesianProduct(xss)));
        }
    }

    private static void demoRepeatingIterables_finite() {
        initialize();
        Iterable<List<Integer>> xss = filter(
                ys -> length(nub(ys)) > 1,
                P.withScale(4).listsAtLeast(2, P.withNull(P.integersGeometric()))
        );
        for (List<Integer> xs : take(TINY_LIMIT, xss)) {
            String listString = tail(init(xs.toString()));
            System.out.println(xs);
            System.out.println("repeatingIterables(" + listString + ") = " +
                    its(map(Testing::its, EP.repeatingIterables(xs))));
        }
    }
}
