package mho.wheels.iterables.randomProvider;

import mho.wheels.io.Readers;
import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.NullableOptional;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class CompoundTest {
    private static RandomProvider P;
    private static final int DEFAULT_SAMPLE_SIZE = 1000000;
    private static final int DEFAULT_TOP_COUNT = 10;
    private static final int TINY_LIMIT = 20;

    @Before
    public void initialize() {
        P = RandomProvider.example();
    }

    private static void withElement_helper(
            int scale,
            @NotNull String input,
            @Nullable Integer element,
            @NotNull String output,
            double elementFrequency
    ) {
        Iterable<Integer> xs = P.withScale(scale).withElement(element, cycle(readIntegerListWithNulls(input)));
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> Objects.equals(x, element) ? 1 : 0, sample))), elementFrequency);
        P.reset();
    }

    @Test
    public void testWithElement() {
        withElement_helper(
                2,
                "[1]",
                null,
                "[1, null, 1, 1, 1, 1, 1, 1, 1, null, 1, null, null, 1, null, null, 1, 1, null, 1]",
                0.4992549999935604
        );
        withElement_helper(
                8,
                "[1]",
                null,
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.12480700000010415
        );
        withElement_helper(
                32,
                "[1]",
                null,
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.031218000000010567
        );
        withElement_helper(
                2,
                "[null, 2, 3]",
                10,
                "[null, 10, 2, 3, null, 2, 3, null, 2, 10, 3, 10, 10, null, 10, 10, 2, 3, 10, null]",
                0.4992549999935604
        );
        withElement_helper(
                8,
                "[null, 2, 3]",
                10,
                "[null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2]",
                0.12480700000010415
        );
        withElement_helper(
                32,
                "[null, 2, 3]",
                10,
                "[null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2]",
                0.031218000000010567
        );
        try {
            toList(P.withElement(null, readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).withElement(null, cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void withNull_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double nullFrequency
    ) {
        Iterable<Integer> xs = P.withScale(scale).withNull(cycle(readIntegerListWithNulls(input)));
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x == null ? 1 : 0, sample))), nullFrequency);
        P.reset();
    }

    @Test
    public void testWithNull() {
        withNull_helper(
                2,
                "[1]",
                "[1, null, 1, 1, 1, 1, 1, 1, 1, null, 1, null, null, 1, null, null, 1, 1, null, 1]",
                0.4992549999935604
        );
        withNull_helper(8, "[1]", "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]", 0.12480700000010415);
        withNull_helper(
                32,
                "[1]",
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.031218000000010567
        );
        withNull_helper(
                2,
                "[1, 2, 3]",
                "[1, null, 2, 3, 1, 2, 3, 1, 2, null, 3, null, null, 1, null, null, 2, 3, null, 1]",
                0.4992549999935604
        );
        withNull_helper(
                8,
                "[1, 2, 3]",
                "[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]",
                0.12480700000010415
        );
        withNull_helper(
                32,
                "[1, 2, 3]",
                "[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]",
                0.031218000000010567
        );
        try {
            toList(P.withNull(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).withNull(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void optionalsHelper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double emptyFrequency
    ) {
        Iterable<Optional<Integer>> xs = P.withScale(scale).optionals(cycle(readIntegerListWithNulls(input)));
        List<Optional<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testOptionals() {
        optionalsHelper(
                2,
                "[1]",
                "[Optional[1], Optional.empty, Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional.empty, Optional[1], Optional.empty, Optional.empty," +
                " Optional[1], Optional.empty, Optional.empty, Optional[1], Optional[1], Optional.empty, Optional[1]]",
                0.4992549999935604
        );
        optionalsHelper(
                8,
                "[1]",
                "[Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]]",
                0.12480700000010415
        );
        optionalsHelper(
                32,
                "[1]",
                "[Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]]",
                0.031218000000010567
        );
        optionalsHelper(
                2,
                "[1, 2, 3]",
                "[Optional[1], Optional.empty, Optional[2], Optional[3], Optional[1], Optional[2], Optional[3]," +
                " Optional[1], Optional[2], Optional.empty, Optional[3], Optional.empty, Optional.empty," +
                " Optional[1], Optional.empty, Optional.empty, Optional[2], Optional[3], Optional.empty, Optional[1]]",
                0.4992549999935604
        );
        optionalsHelper(
                8,
                "[1, 2, 3]",
                "[Optional[1], Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1]," +
                " Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]," +
                " Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]]",
                0.12480700000010415
        );
        optionalsHelper(
                32,
                "[1, 2, 3]",
                "[Optional[1], Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1]," +
                " Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]," +
                " Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]]",
                0.031218000000010567
        );
        try {
            toList(P.optionals(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).optionals(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void nullableOptionals_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double emptyFrequency
    ) {
        Iterable<NullableOptional<Integer>> xs = P.withScale(scale)
                .nullableOptionals(cycle(readIntegerListWithNulls(input)));
        List<NullableOptional<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testNullableOptionals() {
        nullableOptionals_helper(
                2,
                "[1]",
                "[NullableOptional[1], NullableOptional.empty, NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional.empty, NullableOptional[1], NullableOptional.empty," +
                " NullableOptional.empty, NullableOptional[1], NullableOptional.empty, NullableOptional.empty," +
                " NullableOptional[1], NullableOptional[1], NullableOptional.empty, NullableOptional[1]]",
                0.4992549999935604
        );
        nullableOptionals_helper(
                8,
                "[1]",
                "[NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]]",
                0.12480700000010415
        );
        nullableOptionals_helper(
                32,
                "[1]",
                "[NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]]",
                0.031218000000010567
        );
        nullableOptionals_helper(
                2,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional.empty, NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional.empty, NullableOptional[3], NullableOptional.empty," +
                " NullableOptional.empty, NullableOptional[null], NullableOptional.empty, NullableOptional.empty," +
                " NullableOptional[2], NullableOptional[3], NullableOptional.empty, NullableOptional[null]]",
                0.4992549999935604
        );
        nullableOptionals_helper(
                8,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]," +
                " NullableOptional[3], NullableOptional[null], NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]]",
                0.12480700000010415
        );
        nullableOptionals_helper(
                32,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]," +
                " NullableOptional[3], NullableOptional[null], NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]]",
                0.031218000000010567
        );
        try {
            toList(P.nullableOptionals(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).nullableOptionals(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    @Test
    public void dependentPairsInfiniteTest() {
        aeqitLimit(
                TINY_LIMIT,
                P.dependentPairsInfinite(P.range(1, 5), i -> P.strings(i, charsToString(range('a', 'z')))),
                "[(2, ds), (4, rhvt), (1, v), (2, wv), (5, kpzex), (3, hje), (4, mfse), (1, d), (3, fpo), (3, tgw)," +
                " (1, m), (1, y), (1, o), (3, dpl), (1, j), (5, sofgp), (4, pttf), (4, lszp), (2, dr), (3, fvx), ...]"
        );
        P.reset();

        try {
            toList(P.dependentPairsInfinite(P.range(1, 5), i -> null));
            fail();
        } catch (NullPointerException ignored) {}

        try {
            toList(
                    P.dependentPairsInfinite(
                            ExhaustiveProvider.INSTANCE.range(1, 5),
                            i -> P.strings(i, charsToString(range('a', 'z')))
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    P.dependentPairsInfinite(
                            P.range(1, 5),
                            i -> ExhaustiveProvider.INSTANCE.range('a', 'z')
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    private static void shuffle_helper(@NotNull String input, @NotNull String output) {
        shuffle_helper(readIntegerListWithNulls(input), output);
    }

    private static void shuffle_helper(@NotNull List<Integer> input, @NotNull String output) {
        List<Integer> xs = toList(input);
        P.shuffle(xs);
        aeqit(xs, output);
        P.reset();
    }

    @Test
    public void testShuffle() {
        shuffle_helper("[]", "[]");
        shuffle_helper("[5]", "[5]");
        shuffle_helper("[1, 2]", "[2, 1]");
        shuffle_helper("[1, 2, 3]", "[2, 1, 3]");
        shuffle_helper("[1, 2, 3, 4]", "[2, 4, 1, 3]");
        shuffle_helper("[1, 2, 2, 4]", "[2, 4, 1, 2]");
        shuffle_helper("[2, 2, 2, 2]", "[2, 2, 2, 2]");
        shuffle_helper("[3, 1, 4, 1]", "[1, 1, 3, 4]");
        shuffle_helper("[3, 1, null, 1]", "[1, 1, 3, null]");
        shuffle_helper(toList(IterableUtils.range(1, 10)), "[10, 4, 1, 9, 8, 7, 5, 2, 3, 6]");
    }

    private static void permutationsFinite_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        permutationsFinite_helper(readIntegerListWithNulls(input), output, topSampleCount);
    }

    private static void permutationsFinite_helper(
            @NotNull List<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.permutationsFinite(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testPermutationsFinite() {
        permutationsFinite_helper(
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}");
        permutationsFinite_helper(
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}");
        permutationsFinite_helper(
                "[1, 2]",
                "[[2, 1], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [1, 2]," +
                " [1, 2], [2, 1], [1, 2], [1, 2], [2, 1], [2, 1], [1, 2], [2, 1], ...]",
                "{[2, 1]=500745, [1, 2]=499255}");
        permutationsFinite_helper(
                "[1, 2, 3]",
                "[[2, 1, 3], [2, 3, 1], [2, 3, 1], [2, 3, 1], [3, 1, 2], [1, 2, 3], [3, 2, 1], [2, 3, 1], [3, 1, 2]," +
                " [3, 1, 2], [1, 3, 2], [2, 1, 3], [1, 3, 2], [2, 1, 3], [3, 2, 1], [2, 1, 3], [2, 1, 3], [1, 2, 3]," +
                " [1, 2, 3], [3, 2, 1], ...]",
                "{[2, 3, 1]=167387, [3, 2, 1]=167243, [1, 3, 2]=166538, [1, 2, 3]=166496, [3, 1, 2]=166232," +
                " [2, 1, 3]=166104}");
        permutationsFinite_helper(
                "[1, 2, 3, 4]",
                "[[2, 4, 1, 3], [2, 3, 4, 1], [2, 3, 1, 4], [2, 1, 3, 4], [4, 1, 3, 2], [2, 4, 1, 3], [3, 1, 2, 4]," +
                " [4, 3, 2, 1], [1, 3, 2, 4], [3, 4, 2, 1], [3, 1, 2, 4], [1, 4, 3, 2], [1, 4, 3, 2], [4, 3, 1, 2]," +
                " [2, 1, 3, 4], [3, 4, 2, 1], [4, 3, 2, 1], [4, 1, 3, 2], [4, 2, 1, 3], [3, 4, 1, 2], ...]",
                "{[4, 2, 3, 1]=42026, [2, 3, 1, 4]=42012, [4, 1, 3, 2]=41883, [1, 4, 3, 2]=41846," +
                " [3, 2, 4, 1]=41820, [4, 3, 1, 2]=41782, [3, 1, 4, 2]=41776, [3, 4, 1, 2]=41771," +
                " [2, 1, 3, 4]=41764, [4, 3, 2, 1]=41745}");
        permutationsFinite_helper(
                "[1, 2, 2, 4]",
                "[[2, 4, 1, 2], [2, 2, 4, 1], [2, 2, 1, 4], [2, 1, 2, 4], [4, 1, 2, 2], [2, 4, 1, 2], [2, 1, 2, 4]," +
                " [4, 2, 2, 1], [1, 2, 2, 4], [2, 4, 2, 1], [2, 1, 2, 4], [1, 4, 2, 2], [1, 4, 2, 2], [4, 2, 1, 2]," +
                " [2, 1, 2, 4], [2, 4, 2, 1], [4, 2, 2, 1], [4, 1, 2, 2], [4, 2, 1, 2], [2, 4, 1, 2], ...]",
                "{[4, 2, 2, 1]=83771, [2, 2, 1, 4]=83554, [2, 2, 4, 1]=83502, [2, 4, 1, 2]=83498," +
                " [4, 1, 2, 2]=83476, [1, 4, 2, 2]=83417, [2, 1, 4, 2]=83341, [4, 2, 1, 2]=83271," +
                " [2, 4, 2, 1]=83193, [2, 1, 2, 4]=83115}");
        permutationsFinite_helper(
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}");
        permutationsFinite_helper(
                "[3, 1, 4, 1]",
                "[[1, 1, 3, 4], [1, 4, 1, 3], [1, 4, 3, 1], [1, 3, 4, 1], [1, 3, 4, 1], [1, 1, 3, 4], [4, 3, 1, 1]," +
                " [1, 4, 1, 3], [3, 4, 1, 1], [4, 1, 1, 3], [4, 3, 1, 1], [3, 1, 4, 1], [3, 1, 4, 1], [1, 4, 3, 1]," +
                " [1, 3, 4, 1], [4, 1, 1, 3], [1, 4, 1, 3], [1, 3, 4, 1], [1, 1, 3, 4], [4, 1, 3, 1], ...]",
                "{[1, 4, 3, 1]=83794, [1, 1, 4, 3]=83659, [1, 3, 4, 1]=83647, [1, 4, 1, 3]=83427," +
                " [4, 1, 1, 3]=83380, [3, 1, 4, 1]=83325, [4, 1, 3, 1]=83313, [1, 1, 3, 4]=83216," +
                " [1, 3, 1, 4]=83158, [4, 3, 1, 1]=83127}");
        permutationsFinite_helper(
                "[3, 1, null, 1]",
                "[[1, 1, 3, null], [1, null, 1, 3], [1, null, 3, 1], [1, 3, null, 1], [1, 3, null, 1]," +
                " [1, 1, 3, null], [null, 3, 1, 1], [1, null, 1, 3], [3, null, 1, 1], [null, 1, 1, 3]," +
                " [null, 3, 1, 1], [3, 1, null, 1], [3, 1, null, 1], [1, null, 3, 1], [1, 3, null, 1]," +
                " [null, 1, 1, 3], [1, null, 1, 3], [1, 3, null, 1], [1, 1, 3, null], [null, 1, 3, 1], ...]",
                "{[1, null, 3, 1]=83794, [1, 1, null, 3]=83659, [1, 3, null, 1]=83647, [1, null, 1, 3]=83427," +
                " [null, 1, 1, 3]=83380, [3, 1, null, 1]=83325, [null, 1, 3, 1]=83313, [1, 1, 3, null]=83216," +
                " [1, 3, 1, null]=83158, [null, 3, 1, 1]=83127}");
        permutationsFinite_helper(
                toList(IterableUtils.range(1, 10)),
                "[[10, 4, 1, 9, 8, 7, 5, 2, 3, 6], [7, 3, 1, 10, 2, 5, 4, 6, 8, 9], [3, 6, 2, 9, 4, 1, 10, 5, 8, 7]," +
                " [3, 8, 2, 6, 10, 1, 7, 5, 9, 4], [5, 4, 10, 1, 6, 3, 9, 2, 8, 7], [7, 1, 6, 2, 10, 9, 3, 8, 5, 4]," +
                " [2, 8, 5, 10, 3, 1, 4, 6, 9, 7], [5, 8, 4, 6, 2, 1, 7, 10, 3, 9], [3, 9, 2, 10, 4, 1, 6, 8, 7, 5]," +
                " [7, 2, 3, 1, 8, 10, 6, 5, 9, 4], [4, 8, 9, 7, 5, 2, 3, 6, 1, 10], [9, 2, 1, 5, 3, 7, 6, 4, 10, 8]," +
                " [3, 4, 9, 5, 10, 7, 6, 8, 2, 1], [9, 6, 4, 10, 5, 2, 3, 8, 1, 7], [4, 2, 9, 1, 6, 5, 3, 7, 10, 8]," +
                " [3, 1, 7, 5, 8, 9, 4, 6, 2, 10], [9, 8, 2, 6, 4, 5, 10, 7, 3, 1], [9, 2, 7, 3, 5, 10, 1, 6, 4, 8]," +
                " [10, 3, 2, 1, 6, 7, 8, 4, 9, 5], [3, 6, 2, 1, 10, 8, 9, 5, 7, 4], ...]",
                "{[7, 4, 2, 6, 9, 3, 1, 5, 10, 8]=6, [10, 9, 5, 3, 8, 1, 7, 2, 6, 4]=5," +
                " [5, 8, 1, 10, 6, 3, 9, 4, 7, 2]=5, [3, 9, 6, 4, 1, 10, 5, 7, 8, 2]=5," +
                " [4, 1, 6, 5, 8, 10, 3, 7, 2, 9]=5, [8, 5, 6, 2, 7, 9, 4, 3, 1, 10]=5," +
                " [1, 3, 4, 6, 2, 5, 9, 10, 8, 7]=5, [4, 5, 6, 7, 1, 10, 3, 8, 2, 9]=5," +
                " [5, 3, 6, 2, 8, 10, 1, 9, 7, 4]=5, [3, 10, 4, 7, 8, 9, 1, 5, 6, 2]=5}");
    }

    private static void stringPermutations_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringPermutations(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testStringPermutations() {
        stringPermutations_helper("", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        stringPermutations_helper(
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}");
        stringPermutations_helper(
                "abc",
                "[bac, bca, bca, bca, cab, abc, cba, bca, cab, cab, acb, bac, acb, bac, cba, bac, bac, abc, abc," +
                " cba, ...]",
                "{bca=167387, cba=167243, acb=166538, abc=166496, cab=166232, bac=166104}");
        stringPermutations_helper(
                "foo",
                "[ofo, oof, oof, oof, ofo, foo, oof, oof, ofo, ofo, foo, ofo, foo, ofo, oof, ofo, ofo, foo, foo," +
                " oof, ...]",
                "{oof=334630, foo=333034, ofo=332336}");
        stringPermutations_helper(
                "hello",
                "[elhol, elloh, eholl, lhoel, lheol, oellh, lleho, leolh, olhle, ellho, loehl, lohle, lhloe, lehlo," +
                " llheo, olleh, elohl, loehl, lhleo, helol, ...]",
                "{elolh=16971, lehlo=16937, lhloe=16931, llhoe=16917, leohl=16876, lleoh=16866, ollhe=16835," +
                " olhel=16828, lleho=16806, leolh=16802}");
        stringPermutations_helper(
                "Mississippi",
                "[psissisiipM, iMpssissipi, Mpipsiiisss, ipsisiipssM, iiissMpspsi, iiipsMispss, psiisiMspsi," +
                " sisMipiissp, siisspipiMs, piiMsssisip, ssMiipisspi, piisiiMssps, Mispspsiisi, iisssMisppi," +
                " sspsspMiiii, sipssiMspii, sipipissiMs, iissipisMps, isipiMsssip, siMipiipsss, ...]",
                "{iipssMiissp=54, iisMpissips=52, iisMsspiips=52, ssispiiMpis=51, spMsisiipsi=51, iMspiipssis=51," +
                " ssisMppiiis=50, sMsipssiipi=50, spisiiiMpss=50, sipMspsiisi=50}");
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        prefixPermutations_helper(scale, readIntegerListWithNulls(input), output, topSampleCount);
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull List<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(IterableUtils::toList, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(Testing::its, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private static void prefixPermutations_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).prefixPermutations(readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPrefixPermutations() {
        prefixPermutations_helper(
                1,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2]",
                "[[2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [2, 1], [1, 2], [1, 2], [1, 2]," +
                " [2, 1], [1, 2], [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [2, 1], ...]",
                "{[1, 2]=500128, [2, 1]=499872}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2]",
                "[[2, 1], [1, 2], [1, 2], [1, 2], [1, 2], [1, 2], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1]," +
                " [1, 2], [2, 1], [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [2, 1], ...]",
                "{[2, 1]=500320, [1, 2]=499680}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2]",
                "[[1, 2], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [1, 2]," +
                " [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [1, 2], [1, 2], [2, 1], ...]",
                "{[1, 2]=500657, [2, 1]=499343}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3]",
                "[[2, 3, 1], [3, 1, 2], [1, 3, 2], [2, 1, 3], [3, 1, 2], [2, 3, 1], [3, 2, 1], [3, 2, 1], [1, 2, 3]," +
                " [1, 3, 2], [2, 3, 1], [1, 3, 2], [1, 2, 3], [2, 3, 1], [3, 1, 2], [3, 1, 2], [2, 1, 3], [1, 3, 2]," +
                " [3, 1, 2], [1, 2, 3], ...]",
                "{[1, 3, 2]=166994, [3, 1, 2]=166913, [2, 3, 1]=166781, [3, 2, 1]=166581, [1, 2, 3]=166397" +
                ", [2, 1, 3]=166334}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3]",
                "[[3, 2, 1], [3, 1, 2], [3, 2, 1], [3, 2, 1], [1, 3, 2], [2, 3, 1], [1, 2, 3], [3, 1, 2], [3, 1, 2]," +
                " [2, 3, 1], [3, 1, 2], [1, 3, 2], [2, 1, 3], [2, 3, 1], [1, 2, 3], [2, 1, 3], [2, 1, 3], [2, 3, 1]," +
                " [1, 2, 3], [2, 1, 3], ...]",
                "{[1, 2, 3]=167294, [2, 1, 3]=166661, [3, 2, 1]=166629, [2, 3, 1]=166619, [3, 1, 2]=166593" +
                ", [1, 3, 2]=166204}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3]",
                "[[2, 3, 1], [3, 1, 2], [2, 3, 1], [1, 2, 3], [3, 1, 2], [2, 3, 1], [2, 3, 1], [1, 2, 3], [1, 3, 2]," +
                " [3, 2, 1], [2, 1, 3], [1, 3, 2], [1, 3, 2], [1, 2, 3], [2, 1, 3], [3, 2, 1], [1, 2, 3], [2, 3, 1]," +
                " [2, 3, 1], [2, 3, 1], ...]",
                "{[3, 1, 2]=167085, [1, 3, 2]=167081, [2, 3, 1]=166636, [1, 2, 3]=166544, [2, 1, 3]=166445" +
                ", [3, 2, 1]=166209}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3, 4]",
                "[[2, 3, 1, 4], [4, 1, 2, 3], [1, 4, 3, 2], [4, 1, 2, 3], [2, 4, 3, 1], [2, 4, 3, 1], [1, 3, 2, 4]," +
                " [3, 1, 2, 4], [1, 3, 4, 2], [3, 1, 4, 2], [2, 1, 4, 3], [4, 3, 1, 2], [1, 4, 2, 3], [1, 3, 4, 2]," +
                " [1, 2, 4, 3], [2, 1, 4, 3], [3, 4, 1, 2], [1, 3, 4, 2], [2, 1, 3, 4], [1, 3, 2, 4], ...]",
                "{[3, 1, 2, 4]=42143, [3, 4, 1, 2]=41973, [2, 1, 4, 3]=41966, [1, 3, 4, 2]=41863," +
                " [4, 3, 2, 1]=41814, [2, 3, 1, 4]=41774, [2, 1, 3, 4]=41765, [3, 1, 4, 2]=41743," +
                " [4, 3, 1, 2]=41704, [4, 1, 2, 3]=41663}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3, 4]",
                "[[2, 1, 4, 3], [4, 2, 1, 3], [3, 4, 1, 2], [3, 1, 2, 4], [3, 2, 4, 1], [2, 3, 1, 4], [3, 4, 1, 2]," +
                " [1, 3, 2, 4], [3, 1, 4, 2], [1, 2, 4, 3], [2, 3, 4, 1], [3, 2, 4, 1], [4, 1, 2, 3], [4, 1, 3, 2]," +
                " [1, 3, 2, 4], [4, 3, 2, 1], [2, 1, 4, 3], [1, 2, 3, 4], [3, 2, 1, 4], [3, 4, 1, 2], ...]",
                "{[3, 2, 1, 4]=42069, [4, 2, 1, 3]=42028, [3, 4, 2, 1]=42017, [4, 1, 2, 3]=41899," +
                " [2, 1, 4, 3]=41847, [1, 3, 4, 2]=41824, [2, 4, 1, 3]=41808, [2, 4, 3, 1]=41726," +
                " [1, 2, 3, 4]=41711, [3, 4, 1, 2]=41707}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3, 4]",
                "[[2, 1, 4, 3], [3, 4, 1, 2], [4, 2, 1, 3], [1, 3, 2, 4], [1, 3, 4, 2], [3, 4, 1, 2], [4, 3, 1, 2]," +
                " [1, 4, 2, 3], [4, 2, 3, 1], [2, 3, 4, 1], [2, 4, 3, 1], [2, 3, 4, 1], [4, 2, 1, 3], [2, 3, 4, 1]," +
                " [3, 2, 1, 4], [2, 3, 1, 4], [1, 2, 4, 3], [4, 1, 2, 3], [3, 2, 4, 1], [4, 3, 1, 2], ...]",
                "{[2, 1, 4, 3]=41909, [1, 2, 3, 4]=41878, [1, 3, 2, 4]=41859, [3, 1, 4, 2]=41836," +
                " [2, 4, 1, 3]=41825, [1, 2, 4, 3]=41813, [3, 2, 1, 4]=41781, [2, 3, 4, 1]=41776," +
                " [4, 1, 2, 3]=41766, [4, 3, 1, 2]=41710}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 2, 4]",
                "[[2, 2, 1, 4], [4, 1, 2, 2], [1, 4, 2, 2], [4, 1, 2, 2], [2, 4, 2, 1], [2, 4, 2, 1], [1, 2, 2, 4]," +
                " [2, 1, 2, 4], [1, 2, 4, 2], [2, 1, 4, 2], [2, 1, 4, 2], [4, 2, 1, 2], [1, 4, 2, 2], [1, 2, 4, 2]," +
                " [1, 2, 4, 2], [2, 1, 4, 2], [2, 4, 1, 2], [1, 2, 4, 2], [2, 1, 2, 4], [1, 2, 2, 4], ...]",
                "{[2, 1, 2, 4]=83908, [2, 1, 4, 2]=83709, [2, 4, 1, 2]=83591, [1, 2, 4, 2]=83469," +
                " [4, 2, 2, 1]=83413, [2, 2, 1, 4]=83293, [4, 2, 1, 2]=83185, [1, 2, 2, 4]=83168," +
                " [2, 2, 4, 1]=83139, [2, 4, 2, 1]=83052}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 2, 4]",
                "[[2, 1, 4, 2], [4, 2, 1, 2], [2, 4, 1, 2], [2, 1, 2, 4], [2, 2, 4, 1], [2, 2, 1, 4], [2, 4, 1, 2]," +
                " [1, 2, 2, 4], [2, 1, 4, 2], [1, 2, 4, 2], [2, 2, 4, 1], [2, 2, 4, 1], [4, 1, 2, 2], [4, 1, 2, 2]," +
                " [1, 2, 2, 4], [4, 2, 2, 1], [2, 1, 4, 2], [1, 2, 2, 4], [2, 2, 1, 4], [2, 4, 1, 2], ...]",
                "{[2, 4, 2, 1]=83743, [2, 2, 1, 4]=83582, [4, 2, 1, 2]=83535, [2, 4, 1, 2]=83515," +
                " [4, 1, 2, 2]=83348, [2, 1, 4, 2]=83342, [2, 1, 2, 4]=83257, [1, 2, 2, 4]=83237," +
                " [1, 2, 4, 2]=83215, [1, 4, 2, 2]=83101}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 2, 4]",
                "[[2, 1, 4, 2], [2, 4, 1, 2], [4, 2, 1, 2], [1, 2, 2, 4], [1, 2, 4, 2], [2, 4, 1, 2], [4, 2, 1, 2]," +
                " [1, 4, 2, 2], [4, 2, 2, 1], [2, 2, 4, 1], [2, 4, 2, 1], [2, 2, 4, 1], [4, 2, 1, 2], [2, 2, 4, 1]," +
                " [2, 2, 1, 4], [2, 2, 1, 4], [1, 2, 4, 2], [4, 1, 2, 2], [2, 2, 4, 1], [4, 2, 1, 2], ...]",
                "{[2, 1, 4, 2]=83745, [1, 2, 2, 4]=83737, [1, 2, 4, 2]=83458, [2, 2, 4, 1]=83446," +
                " [2, 2, 1, 4]=83442, [4, 2, 2, 1]=83360, [2, 4, 1, 2]=83245, [4, 2, 1, 2]=83230," +
                " [2, 4, 2, 1]=83181, [1, 4, 2, 2]=83133}"
        );
        prefixPermutations_helper(
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[3, 1, 4, 1]",
                "[[1, 4, 3, 1], [1, 3, 1, 4], [3, 1, 4, 1], [1, 3, 1, 4], [1, 1, 4, 3], [1, 1, 4, 3], [3, 4, 1, 1]," +
                " [4, 3, 1, 1], [3, 4, 1, 1], [4, 3, 1, 1], [1, 3, 1, 4], [1, 4, 3, 1], [3, 1, 1, 4], [3, 4, 1, 1]," +
                " [3, 1, 1, 4], [1, 3, 1, 4], [4, 1, 3, 1], [3, 4, 1, 1], [1, 3, 4, 1], [3, 4, 1, 1], ...]",
                "{[4, 3, 1, 1]=83886, [1, 3, 1, 4]=83629, [3, 4, 1, 1]=83508, [4, 1, 3, 1]=83492," +
                " [1, 4, 3, 1]=83478, [1, 4, 1, 3]=83387, [1, 3, 4, 1]=83135, [4, 1, 1, 3]=83122," +
                " [3, 1, 1, 4]=83104, [1, 1, 3, 4]=83099}"
        );
        prefixPermutations_helper(
                2,
                "[3, 1, 4, 1]",
                "[[1, 3, 1, 4], [1, 1, 3, 4], [4, 1, 3, 1], [4, 3, 1, 1], [4, 1, 1, 3], [1, 4, 3, 1], [4, 1, 3, 1]," +
                " [3, 4, 1, 1], [4, 3, 1, 1], [3, 1, 1, 4], [1, 4, 1, 3], [4, 1, 1, 3], [1, 3, 1, 4], [1, 3, 4, 1]," +
                " [3, 4, 1, 1], [1, 4, 1, 3], [1, 3, 1, 4], [3, 1, 4, 1], [4, 1, 3, 1], [4, 1, 3, 1], ...]",
                "{[1, 1, 3, 4]=83836, [4, 1, 3, 1]=83776, [1, 3, 1, 4]=83746, [4, 1, 1, 3]=83601," +
                " [3, 4, 1, 1]=83350, [3, 1, 4, 1]=83286, [1, 1, 4, 3]=83153, [4, 3, 1, 1]=83129," +
                " [1, 4, 1, 3]=83114, [1, 3, 4, 1]=83072}"
        );
        prefixPermutations_helper(
                4,
                "[3, 1, 4, 1]",
                "[[1, 3, 1, 4], [4, 1, 3, 1], [1, 1, 3, 4], [3, 4, 1, 1], [3, 4, 1, 1], [4, 1, 3, 1], [1, 4, 3, 1]," +
                " [3, 1, 1, 4], [1, 1, 4, 3], [1, 4, 1, 3], [1, 1, 4, 3], [1, 4, 1, 3], [1, 1, 3, 4], [1, 4, 1, 3]," +
                " [4, 1, 3, 1], [1, 4, 3, 1], [3, 1, 1, 4], [1, 3, 1, 4], [4, 1, 1, 3], [1, 4, 3, 1], ...]",
                "{[1, 3, 1, 4]=83675, [3, 4, 1, 1]=83504, [1, 4, 1, 3]=83477, [3, 1, 1, 4]=83475," +
                " [4, 3, 1, 1]=83395, [1, 4, 3, 1]=83371, [3, 1, 4, 1]=83349, [1, 1, 3, 4]=83345," +
                " [4, 1, 1, 3]=83297, [1, 1, 4, 3]=83213}"
        );
        prefixPermutations_helper(
                1,
                "[3, 1, null, 1]",
                "[[1, null, 3, 1], [1, 3, 1, null], [3, 1, null, 1], [1, 3, 1, null], [1, 1, null, 3]," +
                " [1, 1, null, 3], [3, null, 1, 1], [null, 3, 1, 1], [3, null, 1, 1], [null, 3, 1, 1]," +
                " [1, 3, 1, null], [1, null, 3, 1], [3, 1, 1, null], [3, null, 1, 1], [3, 1, 1, null]," +
                " [1, 3, 1, null], [null, 1, 3, 1], [3, null, 1, 1], [1, 3, null, 1], [3, null, 1, 1], ...]",
                "{[null, 3, 1, 1]=83886, [1, 3, 1, null]=83629, [3, null, 1, 1]=83508, [null, 1, 3, 1]=83492," +
                " [1, null, 3, 1]=83478, [1, null, 1, 3]=83387, [1, 3, null, 1]=83135, [null, 1, 1, 3]=83122," +
                " [3, 1, 1, null]=83104, [1, 1, 3, null]=83099}"
        );
        prefixPermutations_helper(
                2,
                "[3, 1, null, 1]",
                "[[1, 3, 1, null], [1, 1, 3, null], [null, 1, 3, 1], [null, 3, 1, 1], [null, 1, 1, 3]," +
                " [1, null, 3, 1], [null, 1, 3, 1], [3, null, 1, 1], [null, 3, 1, 1], [3, 1, 1, null]," +
                " [1, null, 1, 3], [null, 1, 1, 3], [1, 3, 1, null], [1, 3, null, 1], [3, null, 1, 1]," +
                " [1, null, 1, 3], [1, 3, 1, null], [3, 1, null, 1], [null, 1, 3, 1], [null, 1, 3, 1], ...]",
                "{[1, 1, 3, null]=83836, [null, 1, 3, 1]=83776, [1, 3, 1, null]=83746, [null, 1, 1, 3]=83601," +
                " [3, null, 1, 1]=83350, [3, 1, null, 1]=83286, [1, 1, null, 3]=83153, [null, 3, 1, 1]=83129," +
                " [1, null, 1, 3]=83114, [1, 3, null, 1]=83072}"
        );
        prefixPermutations_helper(
                4,
                "[3, 1, null, 1]",
                "[[1, 3, 1, null], [null, 1, 3, 1], [1, 1, 3, null], [3, null, 1, 1], [3, null, 1, 1]," +
                " [null, 1, 3, 1], [1, null, 3, 1], [3, 1, 1, null], [1, 1, null, 3], [1, null, 1, 3]," +
                " [1, 1, null, 3], [1, null, 1, 3], [1, 1, 3, null], [1, null, 1, 3], [null, 1, 3, 1]," +
                " [1, null, 3, 1], [3, 1, 1, null], [1, 3, 1, null], [null, 1, 1, 3], [1, null, 3, 1], ...]",
                "{[1, 3, 1, null]=83675, [3, null, 1, 1]=83504, [1, null, 1, 3]=83477, [3, 1, 1, null]=83475," +
                " [null, 3, 1, 1]=83395, [1, null, 3, 1]=83371, [3, 1, null, 1]=83349, [1, 1, 3, null]=83345," +
                " [null, 1, 1, 3]=83297, [1, 1, null, 3]=83213}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 3, 1, 4, 5, 6, 7, 8, 9, 10], [2, 6, 1, 4, 3, 5, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=3553, [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]=298," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10]=65, [2, 3, 1, 4, 5, 6, 7, 8, 9, 10]=62," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10]=47, [3, 2, 1, 4, 5, 6, 7, 8, 9, 10]=41," +
                " [1, 4, 2, 3, 5, 6, 7, 8, 9, 10]=9, [3, 4, 1, 2, 5, 6, 7, 8, 9, 10]=9," +
                " [4, 3, 2, 1, 5, 6, 7, 8, 9, 10]=9, [4, 1, 3, 2, 5, 6, 7, 8, 9, 10]=9}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10], [3, 1, 2, 4, 5, 6, 7, 8, 9, 10], [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [5, 4, 2, 1, 3, 6, 7, 8, 9, 10], [3, 2, 1, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [3, 1, 2, 4, 5, 6, 7, 8, 9, 10], [3, 1, 4, 2, 5, 6, 7, 8, 9, 10]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [3, 6, 7, 2, 4, 5, 1, 8, 9, 10]," +
                " [3, 4, 2, 1, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=28, [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]=6," +
                " [7, 5, 2, 10, 9, 8, 6, 3, 4, 1]=6, [1, 10, 4, 2, 6, 8, 7, 9, 3, 5]=6," +
                " [2, 5, 3, 6, 4, 7, 1, 9, 10, 8]=6, [10, 3, 1, 2, 6, 9, 7, 8, 4, 5]=6," +
                " [4, 5, 3, 10, 9, 8, 7, 6, 2, 1]=6, [8, 1, 7, 2, 10, 6, 9, 3, 4, 5]=6," +
                " [9, 7, 2, 10, 4, 6, 5, 8, 1, 3]=5, [9, 7, 6, 5, 2, 3, 10, 1, 8, 4]=5}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10], [7, 8, 5, 4, 10, 3, 1, 6, 9, 2], [10, 9, 1, 2, 8, 7, 6, 5, 4, 3]," +
                " [7, 4, 2, 10, 1, 6, 3, 5, 8, 9], [10, 8, 2, 7, 9, 5, 4, 1, 6, 3], [5, 9, 4, 8, 7, 6, 2, 1, 10, 3]," +
                " [5, 10, 9, 1, 6, 7, 8, 3, 2, 4], [2, 10, 7, 8, 6, 3, 5, 4, 1, 9], [1, 2, 9, 5, 4, 7, 10, 6, 3, 8]," +
                " [3, 6, 10, 8, 2, 9, 7, 5, 1, 4], [9, 4, 3, 2, 6, 7, 10, 1, 8, 5], [1, 2, 9, 3, 4, 5, 6, 8, 7, 10]," +
                " [8, 4, 1, 10, 3, 9, 7, 2, 5, 6], [5, 9, 1, 3, 4, 2, 6, 8, 10, 7], [6, 1, 5, 9, 8, 3, 4, 2, 10, 7]," +
                " [9, 2, 1, 10, 6, 8, 7, 5, 4, 3], [4, 3, 8, 2, 10, 9, 6, 1, 5, 7], [10, 2, 6, 9, 8, 3, 4, 1, 7, 5]," +
                " [3, 8, 10, 1, 5, 6, 4, 7, 9, 2], [10, 7, 2, 3, 4, 8, 5, 6, 1, 9], ...]",
                "{[2, 4, 5, 7, 8, 3, 10, 9, 6, 1]=6, [4, 10, 2, 9, 7, 3, 1, 5, 6, 8]=5," +
                " [10, 8, 3, 2, 4, 6, 9, 7, 5, 1]=5, [9, 4, 8, 10, 5, 1, 6, 7, 3, 2]=5," +
                " [9, 7, 5, 2, 10, 8, 1, 6, 4, 3]=5, [2, 3, 10, 1, 9, 6, 8, 7, 4, 5]=5," +
                " [9, 2, 7, 10, 4, 8, 5, 6, 1, 3]=5, [8, 9, 2, 7, 5, 10, 1, 4, 6, 3]=5," +
                " [2, 4, 7, 1, 8, 6, 10, 9, 5, 3]=5, [3, 5, 2, 9, 7, 8, 1, 6, 4, 10]=5}"
        );
        prefixPermutations_helper(
                1,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 6, 1, 4, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=824351," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=74171," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11916," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11875," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11874," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11862," +
                " [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1485," +
                " [4, 3, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1481," +
                " [1, 4, 3, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1474" +
                ", [3, 2, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1469}"
        );
        prefixPermutations_helper(
                2,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [5, 4, 2, 1, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 6, 7, 2, 4, 5, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 4, 2, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=648981," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=93409," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19809," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19717," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19627," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19442," +
                " [3, 1, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3322," +
                " [4, 2, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3273," +
                " [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3264" +
                ", [2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3260}"
        );
        prefixPermutations_helper(
                4,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [8, 7, 5, 10, 15, 16, 2, 6, 13, 1, 9, 11, 14, 3, 12, 4, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 9, 7, 10, 2, 12, 16, 4, 1, 13, 8, 6, 15, 5, 14, 11, 17, 18, 19, 20, ...]," +
                " [1, 5, 6, 8, 9, 10, 3, 7, 2, 4, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 4, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [5, 8, 1, 3, 6, 7, 4, 2, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 2, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 5, 3, 4, 2, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 2, 1, 5, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 9, 6, 2, 8, 3, 5, 4, 7, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 6, 2, 4, 5, 3, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=445737," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=83952," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21276," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21147," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21128," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21069," +
                " [2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4172," +
                " [4, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4121," +
                " [3, 2, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4098" +
                ", [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4060}"
        );
        prefixPermutations_helper(
                1,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_helper(
                2,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_helper(
                4,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_fail_helper(0, "[1, 2, 3]");
        prefixPermutations_fail_helper(-1, "[1, 2, 3]");
    }

    private static void strings_int_Iterable_helper(
            int size,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.strings(size, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void strings_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            P.strings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStrings_int_String() {
        strings_int_Iterable_helper(0, "", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(0, "a", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}"
        );
        strings_int_Iterable_helper(
                2,
                "a",
                "[aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, ...]",
                "{aa=1000000}"
        );
        strings_int_Iterable_helper(
                3,
                "a",
                "[aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa," +
                " aaa, ...]",
                "{aaa=1000000}"
        );
        strings_int_Iterable_helper(0, "abc", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "abc",
                "[b, b, c, b, a, b, b, b, b, b, a, b, b, c, b, c, a, c, b, b, ...]",
                "{c=333615, b=333313, a=333072}"
        );
        strings_int_Iterable_helper(
                2,
                "abc",
                "[bb, cb, ab, bb, bb, ab, bc, bc, ac, bb, ca, cb, ac, bc, cb, aa, ca, bc, ab, ac, ...]",
                "{cb=111390, bc=111322, ca=111219, aa=111121, ba=111088, cc=111028, ab=111012, bb=110943, ac=110877}"
        );
        strings_int_Iterable_helper(
                3,
                "abc",
                "[bbc, bab, bbb, bab, bcb, cac, bbc, acb, acb, ccb, aac, abc, aba, cbc, acc, cbc, cab, acc, aac," +
                " aac, ...]",
                "{aaa=37441, bbb=37355, cac=37327, bcb=37306, cbc=37273, cba=37236, cca=37231, ccc=37214, bca=37158," +
                " aab=37136}"
        );
        strings_int_Iterable_helper(0, "abbc", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "abbc",
                "[b, b, c, b, b, c, a, b, b, c, c, b, c, b, c, b, a, b, b, b, ...]",
                "{b=499640, c=250298, a=250062}"
        );
        strings_int_Iterable_helper(
                2,
                "abbc",
                "[bb, cb, bc, ab, bc, cb, cb, cb, ab, bb, cb, ba, cb, bc, bb, ab, cb, ac, cb, cb, ...]",
                "{bb=250376, bc=124904, cb=124818, ab=124686, ba=124685, ac=62739, cc=62694, aa=62656, ca=62442}"
        );
        strings_int_Iterable_helper(
                3,
                "abbc",
                "[bbc, bbc, abb, ccb, cbc, bab, bbc, bba, cbb, cbb, abc, bac, cbc, bbb, cba, abc, abb, aba, ccb," +
                " bcb, ...]",
                "{bbb=125202, cbb=62727, bba=62625, bbc=62606, abb=62481, bab=62386, bcb=62173, acb=31470," +
                " bcc=31464, cbc=31441}"
        );
        strings_int_Iterable_helper(0, "Mississippi", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "Mississippi",
                "[p, p, s, s, s, p, s, s, i, i, s, s, s, p, s, i, s, i, s, s, ...]",
                "{s=363979, i=363703, p=181581, M=90737}"
        );
        strings_int_Iterable_helper(
                2,
                "Mississippi",
                "[pp, ss, sp, ss, ii, ss, sp, si, si, ss, si, pi, is, si, pi, ss, ss, is, Ms, is, ...]",
                "{ss=132606, si=132473, is=132392, ii=131960, ps=66316, sp=66221, ip=66050, pi=65612, Mi=33235," +
                " pp=33071}"
        );
        strings_int_Iterable_helper(
                3,
                "Mississippi",
                "[pps, ssp, ssi, iss, sps, isi, sss, ipi, iss, ipi, sss, sis, Msi, sss, ssi, sip, iMp, ipp, ips," +
                " ssi, ...]",
                "{sss=48687, sis=48297, ssi=48283, iis=48220, sii=48107, iii=48048, iss=47940, isi=47797, psi=24274," +
                " pss=24260}"
        );
        strings_int_Iterable_fail_helper(1, "");
        strings_int_Iterable_fail_helper(-1, "abc");
    }

    private static void strings_int_helper(int size, @NotNull String output, @NotNull String topSampleCount) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.strings(size)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void strings_int_fail_helper(int size) {
        try {
            P.strings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStrings_int() {
        strings_int_helper(
                0,
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        strings_int_helper(
                1,
                "[嘩, 퇉, 馃, \u2df2, ε, 䊿, \u2538, \u31e5, 髽, 肣, \uf6ff, ﳑ, 赧, \ue215, \u17f3, \udd75, 껸, \udd15," +
                " 몱, ﲦ, ...]",
                "{\uf1b2=36, 撢=35, આ=34, 퉃=34, \27=33, 韖=32, 㖒=32, 膗=31, 㗞=31, 䕦=31}"
        );
        strings_int_helper(
                2,
                "[嘩퇉, 馃\u2df2, ε䊿, \u2538\u31e5, 髽肣, \uf6ffﳑ, 赧\ue215, \u17f3\udd75, 껸\udd15, 몱ﲦ, 䯏ϡ," +
                " 罖\u19dc, 刿ㄾ, 䲵箿, 偵恾, ᬜK, 㵏ꏹ, 缄㩷, ⴿ읾, 纫\ufe2d, ...]",
                "{\uf310틺=2, 緑\ue709=2, 㑰\uf5be=2, \ue429菧=2, \uf480\u23c0=2, Ⳉ고=2, 㜛땹=2, \ue283捿=2," +
                " \ua8edⰄ=2, 楮譂=2}"
        );
        strings_int_helper(
                3,
                "[嘩퇉馃, \u2df2ε䊿, \u2538\u31e5髽, 肣\uf6ffﳑ, 赧\ue215\u17f3, \udd75껸\udd15, 몱ﲦ䯏, ϡ罖\u19dc, 刿ㄾ䲵," +
                " 箿偵恾, ᬜK㵏, ꏹ缄㩷, ⴿ읾纫, \ufe2d㗂䝲, \uf207갩힜, 坤琖\u2a43, 퉌\uea45\ue352, 蕤餥䉀, \u2b63\uf637鸂," +
                " 鸅误輮, ...]",
                "{嘩퇉馃=1, \u2df2ε䊿=1, \u2538\u31e5髽=1, 肣\uf6ffﳑ=1, 赧\ue215\u17f3=1, \udd75껸\udd15=1, 몱ﲦ䯏=1," +
                " ϡ罖\u19dc=1, 刿ㄾ䲵=1, 箿偵恾=1}"
        );
        strings_int_fail_helper(-1);
    }

    private static void lists_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).lists(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void lists_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        lists_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void lists_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).lists(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testLists_Iterable() {
        lists_Iterable_helper_uniform(
                1,
                "[5]",
                "[[5, 5, 5], [5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5], [5, 5], [], [5], [5, 5, 5], [], []," +
                " [5, 5], [5, 5], [5, 5], [], [5, 5], [], [], [5], [5], [], ...]",
                "{[]=499125, [5]=250897, [5, 5]=124849, [5, 5, 5]=62518, [5, 5, 5, 5]=31407, [5, 5, 5, 5, 5]=15634," +
                " [5, 5, 5, 5, 5, 5]=7825, [5, 5, 5, 5, 5, 5, 5]=3926, [5, 5, 5, 5, 5, 5, 5, 5]=1896" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5]=956}",
                1.0008359999977228
        );
        lists_Iterable_helper_uniform(
                2,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5], [5, 5], [5, 5, 5, 5, 5], [], [5]," +
                " [5, 5], [5], [5, 5, 5], [5, 5, 5, 5, 5, 5], [5], [5, 5], [], [5], [], [5], [], [5, 5, 5], ...]",
                "{[]=333813, [5]=221150, [5, 5]=148025, [5, 5, 5]=98992, [5, 5, 5, 5]=66270, [5, 5, 5, 5, 5]=43747," +
                " [5, 5, 5, 5, 5, 5]=29389, [5, 5, 5, 5, 5, 5, 5]=19567, [5, 5, 5, 5, 5, 5, 5, 5]=12958" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5]=8571}",
                2.0020969999891216
        );
        lists_Iterable_helper_uniform(
                4,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5], [], [5], [], [5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [], [5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5], [5, 5, 5, 5], ...]",
                "{[]=200194, [5]=160489, [5, 5]=127708, [5, 5, 5]=101606, [5, 5, 5, 5]=82008, [5, 5, 5, 5, 5]=65900," +
                " [5, 5, 5, 5, 5, 5]=52157, [5, 5, 5, 5, 5, 5, 5]=41827, [5, 5, 5, 5, 5, 5, 5, 5]=33413" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5]=26877}",
                4.004359999991779
        );
        lists_Iterable_helper_uniform(
                1,
                "[1, 2, 3]",
                "[[2, 1, 2], [2, 2, 3, 2, 3, 1, 3, 2], [1, 3], [3, 2], [], [], [1, 3], [2], [], [], [3], [1, 3]," +
                " [3], [3], [], [3], [], [], [], [], ...]",
                "{[]=499504, [2]=83540, [3]=83439, [1]=83275, [2, 3]=13999, [2, 2]=13964, [1, 1]=13961," +
                " [3, 2]=13913, [2, 1]=13882, [1, 2]=13879}",
                1.00085799999768
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, 2, 3]",
                "[[2, 2, 2, 2], [2], [3, 2, 2, 3], [], [3, 2], [1, 3, 1], [2, 1], [3, 3, 3], [2, 1, 3], [1], [1]," +
                " [1], [2, 2, 1], [2, 3, 2, 3, 2, 3, 2], [], [2], [2, 3, 3, 1, 1, 3], [], [], [], ...]",
                "{[]=333247, [2]=74291, [3]=74037, [1]=73733, [3, 1]=16580, [1, 2]=16560, [3, 3]=16556," +
                " [2, 1]=16422, [1, 3]=16411, [3, 2]=16379}",
                2.0023509999891522
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, 2, 3]",
                "[[2, 2, 2, 2], [2], [3, 2, 3, 1, 3, 3, 3, 2, 3, 3, 1, 2, 1, 3, 3, 1, 1], [1], [3], [2, 2, 1, 3, 3]," +
                " [2, 3], [2, 3, 3, 1, 1, 3, 1, 1, 1, 3, 3, 2, 3, 3, 3, 3, 2], []," +
                " [3, 1, 3, 2, 1, 1, 3, 3, 2, 1, 1, 1, 1, 3], [1, 3, 3], [3, 1, 3, 3, 3]," +
                " [3, 1, 3, 3, 2, 3, 2, 2, 2, 2, 3], [], [], [3, 3, 1, 1], [1], [3, 1, 1, 1], [], [1, 3, 1], ...]",
                "{[]=199912, [2]=53484, [1]=53481, [3]=53282, [2, 3]=14347, [1, 1]=14328, [1, 2]=14321," +
                " [3, 1]=14304, [2, 2]=14257, [3, 3]=14250}",
                4.00516399999172
        );
        lists_Iterable_helper_uniform(
                1,
                "[1, null, 3]",
                "[[null, 1, null], [null, null, 3, null, 3, 1, 3, null], [1, 3], [3, null], [], [], [1, 3], [null]," +
                " [], [], [3], [1, 3], [3], [3], [], [3], [], [], [], [], ...]",
                "{[]=499504, [null]=83540, [3]=83439, [1]=83275, [null, 3]=13999, [null, null]=13964, [1, 1]=13961," +
                " [3, null]=13913, [null, 1]=13882, [1, null]=13879}",
                1.00085799999768
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, null, 3]",
                "[[null, null, null, null], [null], [3, null, null, 3], [], [3, null], [1, 3, 1], [null, 1]," +
                " [3, 3, 3], [null, 1, 3], [1], [1], [1], [null, null, 1], [null, 3, null, 3, null, 3, null], []," +
                " [null], [null, 3, 3, 1, 1, 3], [], [], [], ...]",
                "{[]=333247, [null]=74291, [3]=74037, [1]=73733, [3, 1]=16580, [1, null]=16560, [3, 3]=16556," +
                " [null, 1]=16422, [1, 3]=16411, [3, null]=16379}",
                2.0023509999891522
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, null, 3]",
                "[[null, null, null, null], [null], [3, null, 3, 1, 3, 3, 3, null, 3, 3, 1, null, 1, 3, 3, 1, 1]," +
                " [1], [3], [null, null, 1, 3, 3], [null, 3]," +
                " [null, 3, 3, 1, 1, 3, 1, 1, 1, 3, 3, null, 3, 3, 3, 3, null], []," +
                " [3, 1, 3, null, 1, 1, 3, 3, null, 1, 1, 1, 1, 3], [1, 3, 3], [3, 1, 3, 3, 3]," +
                " [3, 1, 3, 3, null, 3, null, null, null, null, 3], [], [], [3, 3, 1, 1], [1], [3, 1, 1, 1], []," +
                " [1, 3, 1], ...]",
                "{[]=199912, [null]=53484, [1]=53481, [3]=53282, [null, 3]=14347, [1, 1]=14328, [1, null]=14321," +
                " [3, 1]=14304, [null, null]=14257, [3, 3]=14250}",
                4.00516399999172
        );
        lists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4]",
                "[[2, 4, 1], [2, 2, 3, 4, 2, 3, 1, 4, 3], [1, 3, 4], [4], [4], [3], [1, 3], [2], [], [], [4], [2]," +
                " [1], [], [3], [3], [], [3], [], [], ...]",
                "{[]=499557, [3]=62855, [1]=62711, [2]=62536, [4]=62330, [2, 2]=8015, [4, 3]=7980, [1, 4]=7963," +
                " [2, 3]=7893, [4, 2]=7892}",
                1.0006389999976706
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4]",
                "[[2, 2, 4, 4], [2, 2, 3], [4, 3], [3, 4, 2], [], [1, 3, 4, 1, 2], [2], [], [3, 4, 3], [2, 1, 3, 3]," +
                " [], [], [1], [1], [2, 4, 2], [], [2, 3, 4, 4, 4, 4, 2], [4, 4, 4, 4], [4], [2, 3, 4, 4, 3, 1, 1]" +
                ", ...]",
                "{[]=333041, [1]=55655, [2]=55628, [4]=55498, [3]=55246, [3, 3]=9433, [2, 2]=9373, [2, 4]=9320," +
                " [3, 4]=9312, [2, 1]=9295}",
                2.0037019999891394
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4]",
                "[[2, 2, 4, 4], [2, 2], [4, 4, 3, 2, 4, 3, 1, 3, 4, 3, 3, 2, 3, 3, 1, 2], [1, 3], [], [], [3]," +
                " [2, 4, 2, 4, 1], [2, 3, 4], [2, 3, 4, 4, 3, 1, 1, 3, 1, 1, 1, 3, 3, 2, 4, 3], [4, 3, 2, 4, 4]," +
                " [3, 1, 3, 4, 2, 4, 1, 1, 3, 4, 3], [1], [], [1, 3, 4, 3], [3, 1, 3, 3, 3]," +
                " [3, 1, 3, 3, 2, 3, 4, 2, 2, 2, 4], [3], [], [3, 4, 3, 1], ...]",
                "{[]=200010, [2]=40047, [4]=39960, [3]=39955, [1]=39861, [4, 1]=8182, [3, 2]=8116, [4, 3]=8107," +
                " [1, 1]=8029, [4, 2]=8025}",
                4.00571499999147
        );
        lists_Iterable_helper_uniform(
                1,
                "[1, 2, 2, 4]",
                "[[2, 4, 1], [2, 2, 2, 4, 2, 2, 1, 4, 2], [1, 2, 4], [4], [4], [2], [1, 2], [2], [], [], [4], [2]," +
                " [1], [], [2], [2], [], [2], [], [], ...]",
                "{[]=499557, [2]=125391, [1]=62711, [4]=62330, [2, 2]=31328, [4, 2]=15872, [2, 1]=15491," +
                " [1, 2]=15489, [2, 4]=15329, [1, 4]=7963}",
                1.0006389999976706
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4], [2, 2, 2], [4, 2], [2, 4, 2], [], [1, 2, 4, 1, 2], [2], [], [2, 4, 2], [2, 1, 2, 2]," +
                " [], [], [1], [1], [2, 4, 2], [], [2, 2, 4, 4, 4, 4, 2], [4, 4, 4, 4], [4], [2, 2, 4, 4, 2, 1, 1]" +
                ", ...]",
                "{[]=333041, [2]=110874, [1]=55655, [4]=55498, [2, 2]=37235, [2, 4]=18632, [1, 2]=18512," +
                " [2, 1]=18480, [4, 2]=18414, [2, 2, 2]=12455}",
                2.0037019999891394
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4], [2, 2], [4, 4, 2, 2, 4, 2, 1, 2, 4, 2, 2, 2, 2, 2, 1, 2], [1, 2], [], [], [2]," +
                " [2, 4, 2, 4, 1], [2, 2, 4], [2, 2, 4, 4, 2, 1, 1, 2, 1, 1, 1, 2, 2, 2, 4, 2], [4, 2, 2, 4, 4]," +
                " [2, 1, 2, 4, 2, 4, 1, 1, 2, 4, 2], [1], [], [1, 2, 4, 2], [2, 1, 2, 2, 2]," +
                " [2, 1, 2, 2, 2, 2, 4, 2, 2, 2, 4], [2], [], [2, 4, 2, 1], ...]",
                "{[]=200010, [2]=80002, [4]=39960, [1]=39861, [2, 2]=32002, [4, 2]=16132, [2, 4]=16010," +
                " [2, 1]=15945, [1, 2]=15898, [2, 2, 2]=12985}",
                4.00571499999147
        );
        lists_Iterable_helper_uniform(
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2], [2], [2], [2], [2, 2], [2], [], [], [2], [2]," +
                " [2], [], [2], [2], [], [2], [], [], ...]",
                "{[]=499557, [2]=250432, [2, 2]=124756, [2, 2, 2]=62825, [2, 2, 2, 2]=31144, [2, 2, 2, 2, 2]=15656," +
                " [2, 2, 2, 2, 2, 2]=7784, [2, 2, 2, 2, 2, 2, 2]=3987, [2, 2, 2, 2, 2, 2, 2, 2]=1945" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=945}",
                1.0006389999976706
        );
        lists_Iterable_helper_uniform(
                2,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2], [2, 2], [2, 2, 2], [], [2, 2, 2, 2, 2], [2], [], [2, 2, 2], [2, 2, 2, 2]," +
                " [], [], [2], [2], [2, 2, 2], [], [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2], [2, 2, 2, 2, 2, 2, 2]" +
                ", ...]",
                "{[]=333041, [2]=222027, [2, 2]=148088, [2, 2, 2]=98825, [2, 2, 2, 2]=65746, [2, 2, 2, 2, 2]=44116," +
                " [2, 2, 2, 2, 2, 2]=29303, [2, 2, 2, 2, 2, 2, 2]=19671, [2, 2, 2, 2, 2, 2, 2, 2]=13059" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=8625}",
                2.0037019999891394
        );
        lists_Iterable_helper_uniform(
                4,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2], [], [], [2]," +
                " [2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2], [], [2, 2, 2, 2], [2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2], [], [2, 2, 2, 2], ...]",
                "{[]=200010, [2]=159823, [2, 2]=128026, [2, 2, 2]=102068, [2, 2, 2, 2]=82001, [2, 2, 2, 2, 2]=65507," +
                " [2, 2, 2, 2, 2, 2]=52528, [2, 2, 2, 2, 2, 2, 2]=41779, [2, 2, 2, 2, 2, 2, 2, 2]=33653" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=26990}",
                4.00571499999147
        );
        lists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 9, 6], [6, 2, 7, 2, 7, 6, 6, 10], [], [3, 3], [5, 7], [6], [], [], [4], [6], [3], [7], [], []," +
                " [1], [], [], [], [], [], ...]",
                "{[]=500030, [10]=25344, [1]=25129, [4]=25128, [7]=25106, [2]=25093, [6]=25064, [8]=24996," +
                " [3]=24945, [5]=24916}",
                0.998919999997707
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8], [6, 2], [6, 6, 10], [], [5, 7, 4, 6, 3], [], [4], [3, 4, 7], [1, 9, 9, 9], [9]," +
                " [10, 6, 3], [6, 7, 4, 4, 4, 6], [2, 4, 2, 8], [10, 3, 8, 5, 9, 7], [], [], []," +
                " [4, 8, 3, 8, 7, 7, 5, 6], [2, 7], [], ...]",
                "{[]=333018, [7]=22348, [10]=22330, [6]=22322, [2]=22312, [4]=22212, [1]=22163, [3]=22146," +
                " [5]=22122, [8]=22019}",
                2.003595999989077
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8], [6], [4, 8, 3, 6, 3, 4, 7, 7, 9, 1, 9, 9, 9, 5, 9, 4, 3], [10, 6, 3], [6, 7]," +
                " [10, 3, 8, 5, 9, 7, 5, 1, 7, 10, 8, 3, 3, 4, 7, 3, 10], []," +
                " [7, 1, 4, 5, 5, 3, 8, 1, 1, 7, 4, 7, 6, 7], [3, 8], [7, 1, 10, 3, 10], [3, 5, 2, 3, 2, 10], [1]," +
                " [3, 9, 5, 6], [5], [3, 1, 9, 8], [], [], [1, 4, 1], [6, 7], [4, 9, 6, 4, 2], ...]",
                "{[]=200177, [8]=16184, [10]=16147, [6]=16096, [1]=16095, [9]=16049, [5]=16043, [7]=15982," +
                " [2]=15958, [3]=15950}",
                4.002965999991581
        );
        lists_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 10, 7], [5], [], [10, 1], [], [3], [7], [], [5], [3], [], [], [], [], [], [], [], [], [5]," +
                " [9, 15, 6, 12], ...]",
                "{[]=499603, [1]=62583, [2]=46821, [3]=35045, [4]=26163, [5]=20032, [6]=14805, [7]=11067, [8]=8386" +
                ", [1, 1]=7678}",
                1.0012699999976906
        );
        lists_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 7, 4], [1, 3, 3, 2, 7], [2, 3, 1, 2, 1, 2], [], [5, 9, 15], [12], [1, 2, 1], []," +
                " [9, 4, 7, 6, 2, 5, 1, 6], [], [], [], [3, 1, 5, 1, 1], [2], [1, 4, 5, 4], [12], [2], [], [4, 1]," +
                " [1], ...]",
                "{[]=333149, [1]=55129, [2]=41612, [3]=31155, [4]=23246, [5]=17762, [6]=13192, [7]=9863," +
                " [1, 1]=9292, [8]=7549}",
                2.001994999989098
        );
        lists_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 7, 4], [7, 8, 2, 3, 1, 2, 1, 2, 1, 8], [15, 6, 12, 6, 1, 2], [13]," +
                " [2, 5, 1, 6, 1, 1, 1, 10, 3, 1, 5, 1, 1, 4], [], [2, 12, 2, 2, 1, 4, 4, 1, 3, 1, 1, 6, 2], [], []," +
                " [], [1, 2, 3], [5, 4], [9], [4, 1, 6, 7, 5], [], [1, 1, 2, 1, 3, 2, 3], [13, 7, 10], [4, 19]," +
                " [2, 4, 1, 22, 17, 2, 1, 2, 1, 3, 4, 3], [], ...]",
                "{[]=199867, [1]=39597, [2]=30067, [3]=22409, [4]=16944, [5]=12477, [6]=9434, [1, 1]=8183, [7]=7055" +
                ", [2, 1]=6035}",
                4.0083209999916205
        );
        lists_Iterable_helper(
                1,
                repeat(1),
                "[[1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1], [1, 1], [], [1], [1, 1, 1], [], []," +
                " [1, 1], [1, 1], [1, 1], [], [1, 1], [], [], [1], [1], [], ...]",
                "{[]=499125, [1]=250897, [1, 1]=124849, [1, 1, 1]=62518, [1, 1, 1, 1]=31407, [1, 1, 1, 1, 1]=15634," +
                " [1, 1, 1, 1, 1, 1]=7825, [1, 1, 1, 1, 1, 1, 1]=3926, [1, 1, 1, 1, 1, 1, 1, 1]=1896" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=956}",
                1.0008359999977228
        );
        lists_Iterable_helper(
                2,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [], [1]," +
                " [1, 1], [1], [1, 1, 1], [1, 1, 1, 1, 1, 1], [1], [1, 1], [], [1], [], [1], [], [1, 1, 1], ...]",
                "{[]=333813, [1]=221150, [1, 1]=148025, [1, 1, 1]=98992, [1, 1, 1, 1]=66270, [1, 1, 1, 1, 1]=43747," +
                " [1, 1, 1, 1, 1, 1]=29389, [1, 1, 1, 1, 1, 1, 1]=19567, [1, 1, 1, 1, 1, 1, 1, 1]=12958" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=8571}",
                2.0020969999891216
        );
        lists_Iterable_helper(
                4,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1], [], [1], [], [1], [1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [], [1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1], [1, 1, 1, 1], ...]",
                "{[]=200194, [1]=160489, [1, 1]=127708, [1, 1, 1]=101606, [1, 1, 1, 1]=82008, [1, 1, 1, 1, 1]=65900," +
                " [1, 1, 1, 1, 1, 1]=52157, [1, 1, 1, 1, 1, 1, 1]=41827, [1, 1, 1, 1, 1, 1, 1, 1]=33413" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=26877}",
                4.004359999991779
        );
        lists_Iterable_fail_helper(1, Collections.emptyList());
        lists_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        lists_Iterable_fail_helper(0, P.integers());
        lists_Iterable_fail_helper(-1, P.integers());
    }

    private static void strings_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).strings(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void strings_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).strings(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStrings_String() {
        strings_String_helper(
                1,
                "a",
                "[aaa, aa, aaaaaaaaa, aa, aa, , a, aaa, , , aa, aa, aa, , aa, , , a, a, , ...]",
                "{=499125, a=250897, aa=124849, aaa=62518, aaaa=31407, aaaaa=15634, aaaaaa=7825, aaaaaaa=3926," +
                " aaaaaaaa=1896, aaaaaaaaa=956}",
                1.0008359999977228
        );
        strings_String_helper(
                2,
                "a",
                "[aaaa, aaaaa, aaaaa, aaaa, aa, aaaaa, , a, aa, a, aaa, aaaaaa, a, aa, , a, , a, , aaa, ...]",
                "{=333813, a=221150, aa=148025, aaa=98992, aaaa=66270, aaaaa=43747, aaaaaa=29389, aaaaaaa=19567," +
                " aaaaaaaa=12958, aaaaaaaaa=8571}",
                2.0020969999891216
        );
        strings_String_helper(
                4,
                "a",
                "[aaaa, aaa, aaaaaaaaaaaaaaaaa, aaaaaaa, aa, , a, , a, aaaaa, aaaaaa, aaaaaaaaaaaaaaaaa, aaaaa, aa," +
                " aaaaaaa, aaaaaaaaaaaaaa, , aaaaaaaa, a, aaaa, ...]",
                "{=200194, a=160489, aa=127708, aaa=101606, aaaa=82008, aaaaa=65900, aaaaaa=52157, aaaaaaa=41827," +
                " aaaaaaaa=33413, aaaaaaaaa=26877}",
                4.004359999991779
        );
        strings_String_helper(
                1,
                "abc",
                "[bab, bbcbcacb, ac, cb, , , ac, b, , , c, ac, c, c, , c, , , , , ...]",
                "{=499504, b=83540, c=83439, a=83275, bc=13999, bb=13964, aa=13961, cb=13913, ba=13882, ab=13879}",
                1.00085799999768
        );
        strings_String_helper(
                2,
                "abc",
                "[bbbb, b, cbbc, , cb, aca, ba, ccc, bac, a, a, a, bba, bcbcbcb, , b, bccaac, , , , ...]",
                "{=333247, b=74291, c=74037, a=73733, ca=16580, ab=16560, cc=16556, ba=16422, ac=16411, cb=16379}",
                2.0023509999891522
        );
        strings_String_helper(
                4,
                "abc",
                "[bbbb, b, cbcacccbccabaccaa, a, c, bbacc, bc, bccaacaaaccbccccb, , cacbaaccbaaaac, acc, caccc," +
                " caccbcbbbbc, , , ccaa, a, caaa, , aca, ...]",
                "{=199912, b=53484, a=53481, c=53282, bc=14347, aa=14328, ab=14321, ca=14304, bb=14257, cc=14250}",
                4.00516399999172
        );
        strings_String_helper(
                1,
                "abbc",
                "[bca, bbbcbbacb, abc, c, c, b, ab, b, , , c, b, a, , b, b, , b, , , ...]",
                "{=499557, b=125391, a=62711, c=62330, bb=31328, cb=15872, ba=15491, ab=15489, bc=15329, ac=7963}",
                1.0006389999976706
        );
        strings_String_helper(
                2,
                "abbc",
                "[bbcc, bbb, cb, bcb, , abcab, b, , bcb, babb, , , a, a, bcb, , bbccccb, cccc, c, bbccbaa, ...]",
                "{=333041, b=110874, a=55655, c=55498, bb=37235, bc=18632, ab=18512, ba=18480, cb=18414, bbb=12455}",
                2.0037019999891394
        );
        strings_String_helper(
                4,
                "abbc",
                "[bbcc, bb, ccbbcbabcbbbbbab, ab, , , b, bcbca, bbc, bbccbaabaaabbbcb, cbbcc, babcbcaabcb, a, ," +
                " abcb, babbb, babbbbcbbbc, b, , bcba, ...]",
                "{=200010, b=80002, c=39960, a=39861, bb=32002, cb=16132, bc=16010, ba=15945, ab=15898, bbb=12985}",
                4.00571499999147
        );
        strings_String_helper(
                1,
                "Mississippi",
                "[sps, sisisssi, is, , is, s, , , s, s, s, i, i, , i, , , , , , ...]",
                "{=499907, s=91141, i=91078, p=45481, M=22586, si=16547, is=16526, ss=16500, ii=16475, ip=8402}",
                0.9996679999977037
        );
        strings_String_helper(
                2,
                "Mississippi",
                "[ssii, si, ssi, i, issss, , s, sss, iMpi, , , p, pss, ssssss, isii, psiiip, i, , siisisis, , ...]",
                "{=333528, s=80737, i=80484, p=40277, M=20228, ss=19746, is=19600, si=19560, ii=19556, ip=9890}",
                2.0026269999890762
        );
        strings_String_helper(
                4,
                "Mississippi",
                "[ssii, s, sisssssisipiMpipp, s, psss, ss, psiiipsiMspisssss, s, sMisiiisiMMss, si, sMiip," +
                " siisipipsM, , spis, i, sMpi, , , MsM, ss, ...]",
                "{=199852, s=58261, i=58255, p=29278, si=17043, is=16951, ss=16925, ii=16678, M=14538, ip=8503}",
                4.0051349999917525
        );
        strings_String_fail_helper(1, "");
        strings_String_fail_helper(0, "abc");
        strings_String_fail_helper(-1, "abc");
    }

    private static void strings_helper(
            int scale,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).strings()));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void strings_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).strings());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStrings() {
        strings_helper(
                1,
                "[ε䊿\u2538, \udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ, ᬜK㵏, 㩷, 纫, 䝲, 坤琖, \uea45, , , \u2b63, 鸅, \uee1c, , ᅺ, 䇺," +
                " , 㖊, , , ...]",
                "{=499557, \uf59a=15, 僵=14, \u12c7=14, 瘍=14, \ue0de=14, \ua838=13, 䃢=13, 喽=13, 瓫=13}",
                1.0006389999976706
        );
        strings_helper(
                2,
                "[\u31e5髽肣\uf6ff, \udd15몱ﲦ, 刿ㄾ, K㵏ꏹ, , 坤琖\u2a43퉌\uea45, 餥, , \u33b2酓캆, \ue9fd\u2aec㖊짎, , ," +
                " 䱸, \uf878, 尩굿\uecf5, , 瀵컦刓嗏\u3353\ue2d3\ud805, 䫯噋\uf36fꌻ, 홃, 壙\udd82픫鼧\u061a\u2e94穨, ...]",
                "{=333041, 趤=15, 挗=13, \u2fae=13, 阤=12, \u0978=12, \ue2fe=12, \uab10=12, 䖸=12, \ue973=12}",
                2.0037019999891394
        );
        strings_helper(
                4,
                "[\u31e5髽肣\uf6ff, \udd15몱, \u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd, 全覚, , , ሮ," +
                " 尩굿\uecf5ꪻ疜, 瀵컦刓, 壙\udd82픫鼧\u061a\u2e94穨㽖ﶼ䥔핀糦嗮\uf329ﻧ\udd42, ꯃ慚총\u0e77\uf36b," +
                " 駆퉐庺\u2293\ued0d䴻ꎤ槔横靯ढ, 䃼, , 만ᑒ拷\ue68e, \u2506囀ͺ\u124eꪪ, 嶂췴䔾턞\uead1猂唯湑ﮍ蹙甧, \uab6e, ," +
                " 滞\ue89bᖒ㿘, ...]",
                "{=200010, \ued08=11, 듏=11, \ua495=11, 幱=10, 㚼=10, Ꙛ=10, 홣=10, ﺆ=10, \ua494=10}",
                4.00571499999147
        );
        strings_fail_helper(0);
        strings_fail_helper(-1);
    }

    private static void listsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsAtLeast(minSize, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        listsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void listsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).listsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testListsAtLeast() {
        listsAtLeast_helper_uniform(
                2,
                1,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5], [5, 5, 5], [5], [5, 5]," +
                " [5, 5, 5, 5], [5], [5], [5, 5, 5], [5, 5, 5], [5, 5, 5], [5], [5, 5, 5], [5], [5], [5, 5], [5, 5]," +
                " [5], ...]",
                "{[5]=499125, [5, 5]=250897, [5, 5, 5]=124849, [5, 5, 5, 5]=62518, [5, 5, 5, 5, 5]=31407," +
                " [5, 5, 5, 5, 5, 5]=15634, [5, 5, 5, 5, 5, 5, 5]=7825, [5, 5, 5, 5, 5, 5, 5, 5]=3926," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5]=1896, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=956}",
                2.0008359999800347
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[5]",
                "[[5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5]," +
                " [5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5], ...]",
                "{[5, 5, 5]=333813, [5, 5, 5, 5]=221150, [5, 5, 5, 5, 5]=148025, [5, 5, 5, 5, 5, 5]=98992," +
                " [5, 5, 5, 5, 5, 5, 5]=66270, [5, 5, 5, 5, 5, 5, 5, 5]=43747, [5, 5, 5, 5, 5, 5, 5, 5, 5]=29389," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=19567, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=12958" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=8571}",
                5.002096999996331
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[5]",
                "[[5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5," +
                " 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]",
                "{[5, 5, 5, 5, 5, 5, 5, 5]=39940, [5, 5, 5, 5, 5, 5, 5, 5, 5]=38196," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=36988, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=35334," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=33831, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=32551," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=31521," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=30149," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=28763" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=27543}",
                32.00360900002322
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3]",
                "[[2, 1, 2, 2], [2, 2, 3, 2, 3, 1, 3, 2], [1, 3, 2], [3], [3, 2, 1], [3], [2, 3], [2], [3]," +
                " [1, 3, 3], [2], [3], [2], [3], [1], [3], [1], [1], [3], [2, 1], ...]",
                "{[2]=167156, [3]=166869, [1]=166251, [2, 3]=28319, [1, 3]=27831, [2, 2]=27782, [3, 1]=27773," +
                " [1, 1]=27761, [1, 2]=27704, [3, 2]=27683}",
                1.9993039999798474
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3]",
                "[[2, 2, 2, 2, 2, 1, 2], [3, 2, 2, 3, 1, 3, 2], [3, 2, 3], [1, 3, 1, 2, 3], [2, 1, 3]," +
                " [3, 3, 3, 2, 3], [2, 1, 3, 3], [1, 3, 1], [3, 1, 1], [2, 2, 1, 3, 3, 2], [2, 3, 2, 3, 2, 3, 2]," +
                " [2, 1, 2], [2, 3, 3, 1, 1, 3, 1, 1, 1], [3, 2, 3, 1, 3, 3, 3, 1, 2, 2, 3], [2, 3, 2, 3]," +
                " [3, 1, 3], [1, 3, 3, 2], [1, 1, 1], [3, 3, 1, 1, 3, 3, 1, 1], [2, 1, 3], ...]",
                "{[2, 3, 3]=12598, [2, 3, 1]=12517, [3, 3, 1]=12472, [2, 1, 1]=12457, [3, 2, 3]=12442," +
                " [1, 3, 3]=12437, [3, 3, 2]=12420, [1, 1, 1]=12412, [3, 1, 2]=12392, [3, 1, 3]=12372}",
                5.003739999996368
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3]",
                "[[3, 2, 3, 1, 3, 3, 3, 2, 3, 3, 1, 2, 1, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 3, 2, 1, 2, 2, 1, 3, 3," +
                " 2, 3, 3, 2, 3, 1, 2, 3, 2, 3, 2, 3, 2, 1, 2, 1]," +
                " [3, 3, 2, 3, 3, 3, 3, 2, 1, 3, 2, 3, 1, 3, 3, 3, 1, 2, 2, 3, 3, 1, 2, 3, 2, 3]," +
                " [3, 1, 3, 2, 1, 1, 3, 3], [1, 1, 3, 3, 2, 3, 3, 1, 3, 3]," +
                " [3, 3, 3, 2, 1, 1, 3, 2, 2, 1, 3, 2, 3, 2, 1, 3, 2, 3, 1, 3, 1]," +
                " [3, 1, 2, 1, 1, 2, 2, 1, 3, 3, 1, 1, 2, 1, 1, 1]," +
                " [3, 1, 1, 3, 2, 3, 1, 2, 3, 3, 2, 1, 3, 3, 1, 3, 3, 1, 2, 2, 2, 2, 2, 1, 2]," +
                " [1, 1, 3, 2, 2, 3, 1, 2, 2, 2, 1, 1, 2], [1, 1, 3, 1, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 2, 3]," +
                " [2, 2, 2, 3, 2, 2, 2, 3, 3, 3, 2, 3, 3, 1, 2, 1, 1, 2, 1, 1, 3, 3, 1, 3, 2, 1, 3, 2, 1, 3, 1, 3," +
                " 3, 2, 2, 1, 1, 1, 2, 2, 1, 3, 2, 1, 1, 3, 2, 1, 1, 1, 3, 2, 2, 1, 2, 2, 1, 3, 1, 1, 1, 2, 3, 2, 2," +
                " 3, 3, 2, 2, 3, 3, 3, 2, 2, 1, 1, 1, 1, 3, 2, 2, 1, 2, 2, 1, 3, 3, 3, 3]," +
                " [2, 2, 2, 3, 2, 1, 3, 3, 2, 1, 3, 1, 2, 3, 3, 2, 1, 2, 3, 1, 2, 2, 1, 3, 3, 2, 2, 2, 3, 3, 1, 1," +
                " 3, 3, 2, 1, 3, 1, 2, 3]," +
                " [3, 2, 3, 1, 3, 2, 1, 1, 3, 2, 3, 3, 1, 3, 2, 1, 1, 2, 3, 2, 1, 3, 2, 2, 3, 2, 2, 1, 2, 3, 2, 3," +
                " 2]," +
                " [2, 2, 1, 1, 1, 1, 2, 3, 2, 3, 1, 2, 3, 2, 2, 3, 3, 2, 3, 2, 1, 3, 2, 1, 2, 1, 2, 1, 3, 2, 3, 3," +
                " 3, 1, 2, 3, 1, 3, 2, 1, 2, 1, 2, 1, 3, 3, 1, 2, 1, 2, 1, 3, 2, 1, 1]," +
                " [1, 1, 3, 1, 3, 3, 2, 3, 2, 1, 3, 2, 1, 1, 3, 1, 2, 1, 1, 1, 2, 1, 3, 2, 3, 2, 2, 1, 2, 1, 1]," +
                " [1, 3, 1, 1, 2, 2, 1, 1, 3, 1, 2, 3, 2, 1, 3, 3, 2, 2, 3, 1, 2, 3]," +
                " [1, 1, 3, 1, 2, 1, 2, 2, 1, 3, 3, 2, 3, 2, 3, 3, 2, 2, 1, 2, 2, 1, 3, 3, 3, 3, 2, 1, 2, 3, 1, 1," +
                " 3, 3, 2, 2, 3, 1, 1, 1, 2, 2, 2, 3]," +
                " [3, 3, 1, 2, 2, 3, 3, 2, 1, 3, 3, 2, 1, 2, 1, 1, 1, 3, 2, 3, 2, 3, 1, 1, 1, 2, 2, 3, 1, 2, 3, 3," +
                " 1, 1, 3, 3], [2, 3, 2, 3, 3, 3, 2, 2, 2]," +
                " [1, 1, 3, 3, 1, 3, 3, 2, 2, 3, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 2, 3, 3, 2, 1, 2, 2, 1, 1, 2, 1]," +
                " [2, 2, 2, 1, 3, 1, 3, 1, 2], ...]",
                "{[3, 3, 1, 2, 2, 3, 1, 1]=16, [1, 3, 3, 3, 3, 1, 1, 1]=15, [1, 1, 1, 3, 2, 3, 1, 1]=15," +
                " [3, 2, 3, 3, 3, 2, 2, 3]=15, [1, 3, 1, 2, 2, 1, 2, 2]=15, [2, 1, 3, 1, 1, 1, 2, 3]=15," +
                " [1, 2, 2, 2, 2, 3, 2, 2]=14, [1, 1, 2, 2, 1, 2, 1, 2]=14, [1, 2, 3, 2, 3, 1, 2, 1]=14" +
                ", [3, 2, 1, 3, 1, 3, 3, 2]=14}",
                32.010685000021894
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, null, 3]",
                "[[null, 1, null, null], [null, null, 3, null, 3, 1, 3, null], [1, 3, null], [3], [3, null, 1], [3]," +
                " [null, 3], [null], [3], [1, 3, 3], [null], [3], [null], [3], [1], [3], [1], [1], [3], [null, 1]" +
                ", ...]",
                "{[null]=167156, [3]=166869, [1]=166251, [null, 3]=28319, [1, 3]=27831, [null, null]=27782," +
                " [3, 1]=27773, [1, 1]=27761, [1, null]=27704, [3, null]=27683}",
                1.9993039999798474
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, null, 3]",
                "[[null, null, null, null, null, 1, null], [3, null, null, 3, 1, 3, null], [3, null, 3]," +
                " [1, 3, 1, null, 3], [null, 1, 3], [3, 3, 3, null, 3], [null, 1, 3, 3], [1, 3, 1], [3, 1, 1]," +
                " [null, null, 1, 3, 3, null], [null, 3, null, 3, null, 3, null], [null, 1, null]," +
                " [null, 3, 3, 1, 1, 3, 1, 1, 1], [3, null, 3, 1, 3, 3, 3, 1, null, null, 3], [null, 3, null, 3]," +
                " [3, 1, 3], [1, 3, 3, null], [1, 1, 1], [3, 3, 1, 1, 3, 3, 1, 1], [null, 1, 3], ...]",
                "{[null, 3, 3]=12598, [null, 3, 1]=12517, [3, 3, 1]=12472, [null, 1, 1]=12457, [3, null, 3]=12442," +
                " [1, 3, 3]=12437, [3, 3, null]=12420, [1, 1, 1]=12412, [3, 1, null]=12392, [3, 1, 3]=12372}",
                5.003739999996368
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, null, 3]",
                "[[3, null, 3, 1, 3, 3, 3, null, 3, 3, 1, null, 1, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 3, null, 1," +
                " null, null, 1, 3, 3, null, 3, 3, null, 3, 1, null, 3, null, 3, null, 3, null, 1, null, 1]," +
                " [3, 3, null, 3, 3, 3, 3, null, 1, 3, null, 3, 1, 3, 3, 3, 1, null, null, 3, 3, 1, null, 3, null," +
                " 3], [3, 1, 3, null, 1, 1, 3, 3], [1, 1, 3, 3, null, 3, 3, 1, 3, 3]," +
                " [3, 3, 3, null, 1, 1, 3, null, null, 1, 3, null, 3, null, 1, 3, null, 3, 1, 3, 1]," +
                " [3, 1, null, 1, 1, null, null, 1, 3, 3, 1, 1, null, 1, 1, 1]," +
                " [3, 1, 1, 3, null, 3, 1, null, 3, 3, null, 1, 3, 3, 1, 3, 3, 1, null, null, null, null, null, 1," +
                " null], [1, 1, 3, null, null, 3, 1, null, null, null, 1, 1, null]," +
                " [1, 1, 3, 1, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, null, 3]," +
                " [null, null, null, 3, null, null, null, 3, 3, 3, null, 3, 3, 1, null, 1, 1, null, 1, 1, 3, 3, 1," +
                " 3, null, 1, 3, null, 1, 3, 1, 3, 3, null, null, 1, 1, 1, null, null, 1, 3, null, 1, 1, 3, null, 1," +
                " 1, 1, 3, null, null, 1, null, null, 1, 3, 1, 1, 1, null, 3, null, null, 3, 3, null, null, 3, 3, 3," +
                " null, null, 1, 1, 1, 1, 3, null, null, 1, null, null, 1, 3, 3, 3, 3]," +
                " [null, null, null, 3, null, 1, 3, 3, null, 1, 3, 1, null, 3, 3, null, 1, null, 3, 1, null, null," +
                " 1, 3, 3, null, null, null, 3, 3, 1, 1, 3, 3, null, 1, 3, 1, null, 3]," +
                " [3, null, 3, 1, 3, null, 1, 1, 3, null, 3, 3, 1, 3, null, 1, 1, null, 3, null, 1, 3, null, null," +
                " 3, null, null, 1, null, 3, null, 3, null]," +
                " [null, null, 1, 1, 1, 1, null, 3, null, 3, 1, null, 3, null, null, 3, 3, null, 3, null, 1, 3," +
                " null, 1, null, 1, null, 1, 3, null, 3, 3, 3, 1, null, 3, 1, 3, null, 1, null, 1, null, 1, 3, 3," +
                " 1, null, 1, null, 1, 3, null, 1, 1]," +
                " [1, 1, 3, 1, 3, 3, null, 3, null, 1, 3, null, 1, 1, 3, 1, null, 1, 1, 1, null, 1, 3, null, 3," +
                " null, null, 1, null, 1, 1]," +
                " [1, 3, 1, 1, null, null, 1, 1, 3, 1, null, 3, null, 1, 3, 3, null, null, 3, 1, null, 3]," +
                " [1, 1, 3, 1, null, 1, null, null, 1, 3, 3, null, 3, null, 3, 3, null, null, 1, null, null, 1, 3," +
                " 3, 3, 3, null, 1, null, 3, 1, 1, 3, 3, null, null, 3, 1, 1, 1, null, null, null, 3]," +
                " [3, 3, 1, null, null, 3, 3, null, 1, 3, 3, null, 1, null, 1, 1, 1, 3, null, 3, null, 3, 1, 1, 1," +
                " null, null, 3, 1, null, 3, 3, 1, 1, 3, 3], [null, 3, null, 3, 3, 3, null, null, null]," +
                " [1, 1, 3, 3, 1, 3, 3, null, null, 3, null, 1, null, 3, 1, null, 1, 1, null, null, null, 3, 3," +
                " null, 1, null, null, 1, 1, null, 1], [null, null, null, 1, 3, 1, 3, 1, null], ...]",
                "{[3, 3, 1, null, null, 3, 1, 1]=16, [1, 3, 3, 3, 3, 1, 1, 1]=15, [1, 1, 1, 3, null, 3, 1, 1]=15," +
                " [3, null, 3, 3, 3, null, null, 3]=15, [1, 3, 1, null, null, 1, null, null]=15," +
                " [null, 1, 3, 1, 1, 1, null, 3]=15, [1, null, null, null, null, 3, null, null]=14," +
                " [1, 1, null, null, 1, null, 1, null]=14, [1, null, 3, null, 3, 1, null, 1]=14," +
                " [3, null, 1, 3, 1, 3, 3, null]=14}",
                32.010685000021894
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4]",
                "[[2, 4, 1, 2], [2, 2, 3, 4, 2, 3, 1, 4, 3], [1, 3, 4, 2], [4], [4, 2], [3], [1, 3, 4], [2], [1]," +
                " [4, 4], [2], [1, 3], [3, 2], [3], [2], [3], [1], [3], [1], [1], ...]",
                "{[4]=125444, [2]=125303, [1]=125036, [3]=124486, [2, 3]=15820, [3, 3]=15793, [1, 1]=15774," +
                " [2, 2]=15720, [2, 4]=15688, [3, 4]=15683}",
                1.999585999979838
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4]",
                "[[2, 2, 4, 4, 2, 4, 2], [2, 2, 3, 4], [4, 3, 2, 4, 2], [3, 4, 2, 1], [1, 3, 4, 1, 2, 3, 1, 2]," +
                " [4, 4, 3], [3, 4, 3, 3, 2], [2, 1, 3, 3, 1], [3, 1, 1], [1, 3, 4, 3], [2, 4, 2, 4], [3, 3, 2]," +
                " [2, 3, 4, 4, 4, 4, 2], [4, 4, 4, 4, 2, 1, 4], [2, 3, 4, 4, 3, 1, 1, 3, 1, 1], [3, 3, 2]," +
                " [4, 3, 2, 4, 4, 3, 4, 4], [3, 3, 3], [2, 4, 4], [2, 3, 2, 3, 4, 1], ...]",
                "{[1, 2, 4]=5450, [1, 2, 1]=5359, [1, 4, 4]=5355, [3, 4, 4]=5307, [3, 3, 4]=5307, [2, 2, 4]=5280," +
                " [1, 3, 3]=5279, [4, 1, 4]=5273, [4, 3, 3]=5271, [2, 3, 2]=5269}",
                5.00315899999616
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4]",
                "[[4, 4, 3, 2, 4, 3, 1, 3, 4, 3, 3, 2, 3, 3, 1, 2, 1, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 4, 3, 4, 4," +
                " 4, 2, 1, 2, 4, 2, 4, 1, 3, 3, 2, 3, 3, 4, 2, 3], [2, 3, 4, 4, 4, 4, 2, 4]," +
                " [3, 3, 2, 4, 3, 3, 4, 4, 4, 3, 3, 2, 1, 4, 3, 2, 4, 4, 3, 4, 4, 1, 3, 3, 3, 1, 2, 4, 4, 2, 3, 3," +
                " 1, 2, 3], [3, 1, 3, 4, 2, 4, 1, 1, 3], [1, 1, 4, 3, 4, 4, 3, 2, 3, 3, 4, 1]," +
                " [3, 3, 3, 2, 1, 1, 3, 2, 2, 1, 3, 2, 3, 2, 1, 3, 2, 3, 1, 3, 1, 3, 3]," +
                " [3, 1, 2, 1, 1, 2, 4, 2, 1, 3, 4, 3, 1, 1, 4, 2], [1, 2, 2, 3, 3, 3, 1, 3]," +
                " [3, 1, 4, 4, 1, 3, 2, 3, 4, 4, 1, 2, 3, 3, 2, 1, 4, 3, 3], [3, 4, 3, 1, 4, 2, 2, 2]," +
                " [1, 4, 4, 4, 4, 4, 1, 4, 3, 2, 2, 4, 3, 1, 2, 2, 4, 2, 1]," +
                " [4, 1, 1, 4, 3, 1, 4, 1, 3, 3, 1, 3, 4, 3, 1, 3, 3, 3, 2]," +
                " [2, 4, 2, 2, 3, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 3, 1, 2, 1, 1, 2, 1, 1, 3, 3, 1, 3, 2, 4, 1, 3, 2," +
                " 1, 3, 1, 3, 3, 2, 4, 4, 2, 1, 1, 1, 2, 2, 1, 3, 2, 4, 1, 4, 1, 4, 3, 2, 1, 1, 1, 3, 2, 2, 1, 2, 2," +
                " 1, 3, 1, 1, 1, 2, 4, 4, 3, 4, 4, 4, 2, 2, 3, 3, 2, 2, 3, 3, 3, 2, 2, 1, 1]," +
                " [2, 4, 2, 2, 3, 4, 2, 1, 3, 3, 2, 4, 1, 3, 1, 2, 3, 4, 4, 3, 2, 1, 2, 3, 1, 2, 2, 4, 1, 3, 3, 4," +
                " 2, 4, 2, 4, 2, 3, 3, 4, 4, 1, 1, 3, 3, 2, 1, 3, 4, 1, 2, 4, 3, 4, 4, 4]," +
                " [3, 2, 3, 1, 3, 2, 1, 1, 3, 2, 3, 3, 1, 3, 2, 1, 4, 1, 2, 3, 2, 1, 3, 2, 4, 4, 2, 3, 2, 2, 4]," +
                " [2, 4, 3, 2, 3, 4, 2, 1]," +
                " [2, 2, 4, 1, 1, 4, 4, 1, 1, 4, 2, 3, 2, 3, 1, 4, 4, 2, 3, 2, 4, 2, 3, 3, 4, 2, 4, 3, 2, 1, 3, 2," +
                " 1, 4, 2, 1, 2, 1, 3, 2, 3, 3, 3, 1, 4, 2, 3, 1, 3, 2, 1, 4, 2, 4]," +
                " [4, 2, 1, 2, 1, 4, 3, 2, 1, 4, 1, 2]," +
                " [1, 1, 3, 1, 3, 4, 3, 2, 3, 2, 1, 3, 4, 2, 1, 4, 1, 4, 3, 4, 1, 2, 1, 1, 1, 2, 1, 3, 2, 3]," +
                " [3, 2, 4, 3, 4, 1, 3, 3, 3, 3, 2, 4, 4, 4], ...]",
                "{[3, 3, 1, 4, 4, 4, 3, 4]=6, [4, 1, 3, 1, 1, 3, 3, 3]=6, [1, 4, 2, 4, 3, 1, 3, 3]=6," +
                " [1, 4, 3, 1, 4, 2, 3, 4]=6, [2, 1, 2, 3, 1, 3, 1, 3]=5, [4, 4, 2, 3, 3, 2, 3, 2]=5," +
                " [2, 3, 4, 4, 4, 3, 1, 4]=5, [3, 1, 1, 2, 1, 4, 3, 3]=5, [2, 4, 3, 4, 2, 3, 1, 4]=5," +
                " [4, 2, 3, 1, 4, 4, 3, 1]=5}",
                32.008717000021356
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 2, 4]",
                "[[2, 4, 1, 2], [2, 2, 2, 4, 2, 2, 1, 4, 2], [1, 2, 4, 2], [4], [4, 2], [2], [1, 2, 4], [2], [1]," +
                " [4, 4], [2], [1, 2], [2, 2], [2], [2], [2], [1], [2], [1], [1], ...]",
                "{[2]=249789, [4]=125444, [1]=125036, [2, 2]=62887, [2, 4]=31371, [4, 2]=31153, [1, 2]=31119," +
                " [2, 1]=30818, [1, 1]=15774, [1, 4]=15648}",
                1.999585999979838
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4, 2, 4, 2], [2, 2, 2, 4], [4, 2, 2, 4, 2], [2, 4, 2, 1], [1, 2, 4, 1, 2, 2, 1, 2]," +
                " [4, 4, 2], [2, 4, 2, 2, 2], [2, 1, 2, 2, 1], [2, 1, 1], [1, 2, 4, 2], [2, 4, 2, 4], [2, 2, 2]," +
                " [2, 2, 4, 4, 4, 4, 2], [4, 4, 4, 4, 2, 1, 4], [2, 2, 4, 4, 2, 1, 1, 2, 1, 1], [2, 2, 2]," +
                " [4, 2, 2, 4, 4, 2, 4, 4], [2, 2, 2], [2, 4, 4], [2, 2, 2, 2, 4, 1], ...]",
                "{[2, 2, 2]=41560, [2, 2, 4]=20918, [1, 2, 2]=20798, [2, 4, 2]=20791, [2, 1, 2]=20753," +
                " [4, 2, 2]=20552, [2, 2, 1]=20519, [2, 2, 2, 2]=14169, [1, 2, 4]=10717, [1, 2, 1]=10620}",
                5.00315899999616
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 2, 4]",
                "[[4, 4, 2, 2, 4, 2, 1, 2, 4, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 4, 2, 4, 4," +
                " 4, 2, 1, 2, 4, 2, 4, 1, 2, 2, 2, 2, 2, 4, 2, 2], [2, 2, 4, 4, 4, 4, 2, 4]," +
                " [2, 2, 2, 4, 2, 2, 4, 4, 4, 2, 2, 2, 1, 4, 2, 2, 4, 4, 2, 4, 4, 1, 2, 2, 2, 1, 2, 4, 4, 2, 2, 2," +
                " 1, 2, 2], [2, 1, 2, 4, 2, 4, 1, 1, 2], [1, 1, 4, 2, 4, 4, 2, 2, 2, 2, 4, 1]," +
                " [2, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2]," +
                " [2, 1, 2, 1, 1, 2, 4, 2, 1, 2, 4, 2, 1, 1, 4, 2], [1, 2, 2, 2, 2, 2, 1, 2]," +
                " [2, 1, 4, 4, 1, 2, 2, 2, 4, 4, 1, 2, 2, 2, 2, 1, 4, 2, 2], [2, 4, 2, 1, 4, 2, 2, 2]," +
                " [1, 4, 4, 4, 4, 4, 1, 4, 2, 2, 2, 4, 2, 1, 2, 2, 4, 2, 1]," +
                " [4, 1, 1, 4, 2, 1, 4, 1, 2, 2, 1, 2, 4, 2, 1, 2, 2, 2, 2]," +
                " [2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 4, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 4, 1, 2, 2," +
                " 1, 2, 1, 2, 2, 2, 4, 4, 2, 1, 1, 1, 2, 2, 1, 2, 2, 4, 1, 4, 1, 4, 2, 2, 1, 1, 1, 2, 2, 2, 1, 2, 2," +
                " 1, 2, 1, 1, 1, 2, 4, 4, 2, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1]," +
                " [2, 4, 2, 2, 2, 4, 2, 1, 2, 2, 2, 4, 1, 2, 1, 2, 2, 4, 4, 2, 2, 1, 2, 2, 1, 2, 2, 4, 1, 2, 2, 4," +
                " 2, 4, 2, 4, 2, 2, 2, 4, 4, 1, 1, 2, 2, 2, 1, 2, 4, 1, 2, 4, 2, 4, 4, 4]," +
                " [2, 2, 2, 1, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 2, 1, 4, 1, 2, 2, 2, 1, 2, 2, 4, 4, 2, 2, 2, 2, 4]," +
                " [2, 4, 2, 2, 2, 4, 2, 1]," +
                " [2, 2, 4, 1, 1, 4, 4, 1, 1, 4, 2, 2, 2, 2, 1, 4, 4, 2, 2, 2, 4, 2, 2, 2, 4, 2, 4, 2, 2, 1, 2, 2," +
                " 1, 4, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 4, 2, 2, 1, 2, 2, 1, 4, 2, 4]," +
                " [4, 2, 1, 2, 1, 4, 2, 2, 1, 4, 1, 2]," +
                " [1, 1, 2, 1, 2, 4, 2, 2, 2, 2, 1, 2, 4, 2, 1, 4, 1, 4, 2, 4, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2]," +
                " [2, 2, 4, 2, 4, 1, 2, 2, 2, 2, 2, 4, 4, 4], ...]",
                "{[2, 2, 2, 2, 2, 2, 2, 2]=155, [2, 2, 2, 2, 2, 2, 2, 1]=97, [2, 2, 2, 2, 2, 4, 2, 2]=93," +
                " [2, 1, 2, 2, 2, 2, 2, 2]=87, [2, 2, 2, 2, 2, 2, 2, 2, 2]=83, [2, 2, 4, 2, 2, 2, 2, 2]=80," +
                " [2, 2, 2, 4, 2, 2, 2, 2]=79, [2, 4, 2, 2, 2, 2, 2, 2]=78, [2, 2, 1, 2, 2, 2, 2, 2]=77" +
                ", [2, 2, 2, 2, 2, 2, 4, 2]=73}",
                32.008717000021356
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2], [2, 2], [2], [2, 2, 2], [2], [2]," +
                " [2, 2], [2], [2, 2], [2, 2], [2], [2], [2], [2], [2], [2], [2], ...]",
                "{[2]=500269, [2, 2]=249809, [2, 2, 2]=124830, [2, 2, 2, 2]=62682, [2, 2, 2, 2, 2]=31195," +
                " [2, 2, 2, 2, 2, 2]=15549, [2, 2, 2, 2, 2, 2, 2]=7831, [2, 2, 2, 2, 2, 2, 2, 2]=3938," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2]=2013, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=951}",
                1.999585999979838
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2], [2, 2, 2, 2, 2, 2], ...]",
                "{[2, 2, 2]=332475, [2, 2, 2, 2]=222950, [2, 2, 2, 2, 2]=148435, [2, 2, 2, 2, 2, 2]=98386," +
                " [2, 2, 2, 2, 2, 2, 2]=65648, [2, 2, 2, 2, 2, 2, 2, 2]=43847, [2, 2, 2, 2, 2, 2, 2, 2, 2]=29430," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=19567, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=13014" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=8771}",
                5.00315899999616
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2, 2, 2, 2, 2]=40181, [2, 2, 2, 2, 2, 2, 2, 2, 2]=38543," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=37070, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=35343," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=33943, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=32305," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=31206," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=29856," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=28774" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=27718}",
                32.008717000021356
        );
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 9, 6, 4], [6, 2, 7, 2, 7, 6, 6], [10], [8], [3, 3], [5, 7, 4], [6], [5], [4, 8], [6], [3, 4]," +
                " [7], [9], [1, 9], [9], [5], [4], [4], [10, 6, 3, 6], [7], ...]",
                "{[6]=50479, [5]=50284, [2]=50162, [4]=50062, [3]=50049, [10]=50015, [9]=49998, [1]=49982," +
                " [8]=49890, [7]=49879}",
                1.9987289999797695
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8, 6, 4, 6], [6, 2, 7], [6, 6, 10, 5, 8], [5, 7, 4, 6, 3, 5, 6, 1], [3, 4, 7, 7, 9, 1]," +
                " [9, 9, 5], [4, 3, 4], [10, 6, 3, 6], [6, 7, 4, 4, 4, 6, 4], [2, 4, 2, 8, 7, 8, 3]," +
                " [10, 3, 8, 5, 9, 7, 5], [7, 10, 8], [4, 8, 3, 8, 7, 7, 5, 6], [2, 7, 1, 7, 1], [5, 3, 8, 1, 1]," +
                " [7, 7, 9, 3, 8, 4, 5, 4], [7, 1, 10, 3], [3, 6, 10, 3, 9], [2, 3, 2, 10], [1, 2, 5, 5, 2], ...]",
                "{[7, 3, 8]=397, [2, 8, 9]=390, [10, 1, 2]=389, [6, 9, 3]=383, [5, 2, 3]=383, [7, 9, 8]=382," +
                " [4, 7, 3]=378, [9, 10, 7]=377, [4, 10, 3]=376, [6, 6, 1]=376}",
                5.002305999996172
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[4, 8, 3, 6, 3, 4, 7, 7, 9, 1, 9, 9, 9, 5, 9, 4, 3, 4, 2, 1, 10, 6, 3, 6, 7, 10, 7, 1, 6, 7, 4, 4," +
                " 4, 6, 4, 7, 6, 4, 3, 10, 2, 4, 2, 8, 7, 8, 3, 6]," +
                " [7, 10, 8, 3, 3, 4, 7, 3, 10, 9, 4, 8, 3, 8, 7, 7, 5, 6, 2], [7, 1, 4, 5, 5, 3, 8, 1, 1, 7]," +
                " [10, 3, 10, 2, 3, 6, 10, 3, 9, 3, 5, 2, 3, 2, 10, 8, 10, 7, 1, 1, 2, 5, 5, 2, 6, 9, 3, 9, 5, 6," +
                " 1], [4, 1, 10, 3, 8, 1, 6, 7, 6, 3, 3, 1, 4, 9, 6, 4, 2, 4, 10, 4, 9, 2, 6, 1, 5, 8]," +
                " [2, 10, 8, 2, 1, 9, 8, 10, 3, 8, 2, 5, 9, 1, 5, 4, 7, 1], [3, 1, 3, 6, 7, 10, 6, 8, 2, 4, 1]," +
                " [2, 2, 10, 3, 6, 10, 3, 3, 4, 2, 3, 8, 7, 1, 2, 9, 1, 9, 5, 7, 7, 5, 8, 1, 7, 10, 5, 7, 9, 2, 8," +
                " 1, 1, 5, 2, 10, 5, 2, 1, 4, 5, 4, 6, 1, 9, 9, 3, 6, 2, 9, 2, 6, 9, 10, 8, 7, 4, 4, 4, 10, 6, 3, 7," +
                " 6, 3, 7, 2, 10, 1, 9, 4, 5, 3, 8, 2, 4, 9, 10, 2, 5, 8, 3, 7]," +
                " [4, 2, 2, 4, 6, 5, 7, 7, 10, 8, 1, 1, 2, 2, 5, 10, 3, 5, 10, 9, 3, 3, 2, 4, 2, 6, 7, 8, 5, 1, 9," +
                " 4, 8, 8, 9, 4, 9, 2, 7, 10]," +
                " [3, 5, 10, 1, 9, 7, 6, 7, 4, 5, 10, 3, 6, 1, 10, 6, 6, 2, 1, 2, 8, 10, 3, 4, 10]," +
                " [6, 6, 8, 5, 9, 8, 4, 5, 4, 2, 3, 10, 9, 8, 6, 3, 6, 4, 10, 3, 6, 2, 6, 5, 2, 10, 9, 7, 6, 7, 3," +
                " 4, 6, 5, 6, 1, 6, 8, 6, 7, 3, 1, 4, 1, 6, 5, 3, 6, 1, 8, 9, 3, 7, 10, 8]," +
                " [5, 6, 10, 7, 2, 5, 4, 9, 5, 1, 2, 9, 3, 2, 5, 6, 5, 1, 3, 8, 5, 3, 3, 4]," +
                " [1, 3, 8, 1, 5, 4, 2, 6, 4, 1, 5, 8, 7]," +
                " [9, 9, 10, 10, 1, 7, 10, 3, 2, 3, 2, 6, 1, 8, 4, 9, 3, 3, 8, 10, 4, 9, 2, 1, 5, 7, 8, 6, 1, 9, 2," +
                " 2, 2, 4, 4, 6, 5, 6, 3, 5, 3, 3, 10, 10, 5, 4, 5, 5, 9, 6, 7, 6, 7, 2]," +
                " [3, 7, 1, 6, 6, 10, 8, 4, 7, 10, 5, 4]," +
                " [10, 8, 6, 3, 4, 7, 7, 10, 10, 5, 6, 9, 2, 10, 8, 8, 5, 5, 6, 8, 8, 7, 4, 7, 5, 10, 5, 1, 5, 8]," +
                " [2, 9, 8, 2, 10, 7, 2, 8, 9, 10, 9, 9, 4, 2, 8, 1, 2, 2, 8, 4, 9, 5, 1, 2, 9]," +
                " [8, 3, 10, 1, 8, 2, 3, 7, 2, 7, 1, 8, 1, 6, 8, 2, 3, 10, 9, 6, 4, 9, 5, 7, 2, 5, 3, 9, 9, 9, 2, 1," +
                " 1, 4, 6, 5, 2, 8, 8, 7, 2, 6, 9, 1, 6, 1, 7, 7, 10, 2, 9, 7, 9, 9, 9, 3, 6, 7, 2, 4, 7, 9, 2, 10," +
                " 6, 5, 10, 5, 3, 2, 3, 8, 4, 9, 7, 5, 2, 3, 3, 10, 7, 4, 3, 7, 7, 2, 1, 7, 3, 6, 4, 1, 2, 1, 6, 3," +
                " 1, 2, 4, 7], [1, 1, 4, 5, 8, 9, 9, 9, 8, 5, 10, 8, 8, 5, 3, 4, 1, 4]," +
                " [5, 6, 1, 7, 5, 8, 4, 2, 6, 9, 2, 10, 8, 6, 5, 7, 1, 9, 1, 4, 9, 1, 9, 3, 2, 9, 3, 2], ...]",
                "{[6, 9, 1, 7, 2, 5, 10, 2]=2, [1, 4, 4, 8, 9, 2, 1, 10]=2, [6, 1, 3, 1, 8, 8, 3, 7]=2," +
                " [7, 9, 1, 3, 4, 4, 4, 7]=2, [3, 4, 5, 9, 3, 3, 3, 8]=2, [3, 5, 2, 10, 9, 7, 6, 10, 6]=2," +
                " [4, 8, 3, 6, 3, 4, 7, 7, 9, 1, 9, 9, 9, 5, 9, 4, 3, 4, 2, 1, 10, 6, 3, 6, 7, 10, 7, 1, 6, 7, 4, 4," +
                " 4, 6, 4, 7, 6, 4, 3, 10, 2, 4, 2, 8, 7, 8, 3, 6]=1," +
                " [7, 10, 8, 3, 3, 4, 7, 3, 10, 9, 4, 8, 3, 8, 7, 7, 5, 6, 2]=1, [7, 1, 4, 5, 5, 3, 8, 1, 1, 7]=1," +
                " [10, 3, 10, 2, 3, 6, 10, 3, 9, 3, 5, 2, 3, 2, 10, 8, 10, 7, 1, 1, 2, 5, 5, 2, 6, 9, 3, 9, 5, 6," +
                " 1]=1}",
                31.997066000022638
        );
        listsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 10, 7, 7], [3], [7, 1, 3], [1, 2], [4, 8, 2], [2], [2], [2], [8], [9, 15, 6, 12, 6], [2], [1]," +
                " [12], [7, 4], [2, 6, 2, 5, 1], [5], [1], [10], [2], [5], ...]",
                "{[1]=124902, [2]=94480, [3]=69971, [4]=52478, [5]=39569, [6]=29407, [7]=22369, [8]=16718," +
                " [1, 1]=15589, [9]=12621}",
                2.001126999979881
        );
        listsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 7, 4, 10, 1, 3], [2, 7, 8, 2, 3], [2, 1, 2], [8, 5, 9], [6, 12, 6, 1, 2, 1, 1, 13, 9, 4]," +
                " [6, 2, 5, 1, 6, 1, 1], [10, 3, 1], [1, 1, 4, 2, 5], [4, 5, 4], [12, 2, 2, 1], [4, 1, 3, 1, 1]," +
                " [2, 1, 3, 1, 5, 1, 2, 3], [5, 4, 4, 9, 7, 4], [6, 7, 5], [10, 1, 1], [2, 1, 3], [3, 4, 13]," +
                " [10, 3, 1, 4, 19, 5, 7, 6, 2], [1, 22, 17], [1, 2, 1, 3], ...]",
                "{[1, 1, 1]=5214, [1, 1, 2]=3940, [2, 1, 1]=3856, [1, 2, 1]=3855, [1, 1, 3]=2974, [3, 1, 1]=2942," +
                " [1, 2, 2]=2859, [2, 1, 2]=2823, [2, 2, 1]=2821, [1, 3, 1]=2789}",
                5.001189999995907
        );
        listsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "[[7, 8, 2, 3, 1, 2, 1, 2, 1, 8, 5, 9, 15, 6, 12, 6, 1, 2, 1, 1, 13, 9, 4, 7, 6, 2, 5, 1, 6, 1, 1," +
                " 1, 10, 3, 1, 5, 1, 1, 4, 2, 5, 1, 4, 5, 4, 2, 12, 2]," +
                " [1, 6, 2, 1, 3, 1, 5, 1, 2, 3, 6, 5, 4, 4, 9, 7, 4, 1, 6]," +
                " [5, 1, 10, 1, 1, 1, 2, 1, 3, 2, 3, 4, 13]," +
                " [17, 2, 1, 2, 1, 3, 4, 3, 2, 7, 1, 1, 3, 4, 2, 4, 1, 1, 4, 3, 2, 1, 1, 19, 1, 1, 2, 7, 3, 14, 2," +
                " 20, 4, 1, 2, 5, 2, 8, 5, 2, 7, 3, 4, 13, 1, 4, 3, 7, 2, 7, 1, 1, 6, 5, 2, 4, 4, 3, 1, 5, 3, 2, 4," +
                " 10, 8, 1, 2, 6, 2, 5, 2, 5, 1, 10, 8, 3, 2, 2, 2, 2, 2, 1, 2]," +
                " [5, 2, 3, 3, 2, 4, 2, 6, 11, 2, 18, 1, 1, 2, 7, 4, 2, 4, 2, 1, 1, 2, 7, 2, 1, 6, 9, 3, 1, 3, 1, 6," +
                " 1, 4, 5, 6, 6, 1, 1, 3, 2, 10, 2, 7, 1, 6, 1, 2, 7, 1, 3, 1]," +
                " [13, 6, 8, 4, 1, 8, 1, 1, 10, 4, 1, 5, 5, 1]," +
                " [3, 8, 4, 3, 3, 1, 6, 2, 1, 5, 5, 2, 6, 15, 2, 4, 2, 3, 2, 10]," +
                " [1, 4, 7, 7, 3, 2, 1, 9, 3, 1, 4, 2]," +
                " [3, 4, 9, 3, 3, 8, 6, 1, 4, 4, 2, 2, 1, 4, 1, 5, 17, 1, 2, 9, 2, 1, 2, 1, 2, 9, 5, 3, 8, 3, 15, 8," +
                " 2, 5, 12, 4, 2, 3, 1, 2, 1, 2, 3, 1, 3, 1, 2, 5, 6, 2, 8, 2, 4, 1, 4, 8, 2, 3, 2, 3, 5, 3, 4, 2," +
                " 3, 3, 2, 1, 1, 4, 3, 10, 1, 14, 4, 3, 10, 1, 4, 1, 5, 8, 2, 5, 7, 2, 2, 2, 10, 13, 6, 5, 3, 3, 4," +
                " 1, 2, 2, 3, 2, 5, 4, 5, 15, 3, 2, 2, 1, 6, 1, 2, 1], [2, 2, 8, 1, 8, 1, 1, 7, 4, 2, 1]," +
                " [1, 7, 11, 6, 4, 3, 1, 2, 4, 3, 1, 8, 7, 3, 3, 1, 6, 2, 10]," +
                " [2, 2, 8, 7, 4, 1, 3, 4, 5, 1, 2, 1, 4, 7, 2, 4, 2, 1, 3, 1, 3, 5]," +
                " [2, 5, 5, 3, 2, 1, 2, 1, 1, 6, 2, 5, 8, 1, 1, 3, 6, 4, 13, 3, 8]," +
                " [6, 5, 9, 1, 8, 1, 5, 7, 3, 12, 6, 2, 2, 2, 2, 3, 16, 8, 1, 6, 6, 9, 6, 2, 10, 3, 2, 4, 3, 9, 2," +
                " 2, 3]," +
                " [2, 4, 5, 3, 1, 6, 1, 3, 14, 4, 1, 1, 6, 1, 9, 1, 3, 1, 3, 15, 6, 1, 1, 1, 5, 4, 10, 5, 8, 17, 5," +
                " 1, 11, 1, 2, 4, 3, 1, 5, 7, 1, 5, 4, 2, 6, 6, 6, 2, 2, 4, 4, 2, 1, 4, 1, 6, 4, 1, 5, 3, 1, 10, 3," +
                " 1, 1, 1, 1, 2, 1, 6, 8, 3, 1, 1, 5, 3, 3, 4, 2, 1]," +
                " [4, 7, 4, 5, 4, 2, 5, 1, 2, 8, 2, 2, 8, 11, 4, 8, 4, 9, 6]," +
                " [5, 12, 4, 2, 1, 2, 9, 6, 8, 2, 2, 3, 8, 1, 9, 4, 10, 2, 3], [6, 5, 6, 1, 2, 3, 11, 6, 4]," +
                " [16, 2, 1, 1, 5, 5, 1, 1, 1, 4, 6, 2, 1]," +
                " [17, 1, 7, 2, 3, 7, 1, 3, 1, 6, 1, 3, 1, 3, 1, 3, 3, 1, 11, 1, 2, 5, 5, 1, 3, 3, 2, 2, 8, 1, 3, 5," +
                " 15, 4, 1, 2, 4, 1, 10, 2, 1, 1, 2, 1, 7, 3, 2, 3], ...]",
                "{[1, 2, 3, 1, 1, 4, 4, 1]=3, [1, 1, 1, 1, 1, 2, 4, 6]=3, [4, 6, 1, 2, 1, 1, 3, 1]=3," +
                " [1, 7, 3, 2, 6, 1, 3, 1]=2, [3, 1, 6, 2, 9, 3, 1, 5, 2]=2, [3, 1, 1, 5, 3, 3, 2, 1]=2," +
                " [4, 2, 1, 6, 1, 1, 2, 2]=2, [3, 2, 3, 1, 1, 2, 2, 2, 2]=2, [1, 1, 1, 1, 3, 1, 3, 7, 12]=2," +
                " [3, 1, 6, 3, 1, 3, 3, 1]=2}",
                32.00730000002313
        );
        listsAtLeast_helper(
                2,
                1,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1], [1], [1, 1]," +
                " [1, 1, 1, 1], [1], [1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1], [1, 1, 1], [1], [1], [1, 1], [1, 1]," +
                " [1], ...]",
                "{[1]=499125, [1, 1]=250897, [1, 1, 1]=124849, [1, 1, 1, 1]=62518, [1, 1, 1, 1, 1]=31407," +
                " [1, 1, 1, 1, 1, 1]=15634, [1, 1, 1, 1, 1, 1, 1]=7825, [1, 1, 1, 1, 1, 1, 1, 1]=3926," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1]=1896, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=956}",
                2.0008359999800347
        );
        listsAtLeast_helper(
                5,
                3,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1], ...]",
                "{[1, 1, 1]=333813, [1, 1, 1, 1]=221150, [1, 1, 1, 1, 1]=148025, [1, 1, 1, 1, 1, 1]=98992," +
                " [1, 1, 1, 1, 1, 1, 1]=66270, [1, 1, 1, 1, 1, 1, 1, 1]=43747, [1, 1, 1, 1, 1, 1, 1, 1, 1]=29389," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=19567, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=12958" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=8571}",
                5.002096999996331
        );
        listsAtLeast_helper(
                32,
                8,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1]=39940, [1, 1, 1, 1, 1, 1, 1, 1, 1]=38196," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=36988, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=35334," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=33831, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=32551," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=31521," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=30149," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=28763," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=27543}",
                32.00360900002322
        );
        listsAtLeast_fail_helper(5, 3, Collections.emptyList());
        listsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        listsAtLeast_fail_helper(5, 5, P.integers());
        listsAtLeast_fail_helper(4, 5, P.integers());
    }

    private static void stringsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsAtLeast(minSize, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsAtLeast_int_String() {
        stringsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "[aaaa, aaa, aaaaaaaaaa, aaa, aaa, a, aa, aaaa, a, a, aaa, aaa, aaa, a, aaa, a, a, aa, aa, a, ...]",
                "{a=499125, aa=250897, aaa=124849, aaaa=62518, aaaaa=31407, aaaaaa=15634, aaaaaaa=7825," +
                " aaaaaaaa=3926, aaaaaaaaa=1896, aaaaaaaaaa=956}",
                2.0008359999800347
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "a",
                "[aaaaaaa, aaaaaaaa, aaaaaaaa, aaaaaaa, aaaaa, aaaaaaaa, aaa, aaaa, aaaaa, aaaa, aaaaaa, aaaaaaaaa," +
                " aaaa, aaaaa, aaa, aaaa, aaa, aaaa, aaa, aaaaaa, ...]",
                "{aaa=333813, aaaa=221150, aaaaa=148025, aaaaaa=98992, aaaaaaa=66270, aaaaaaaa=43747," +
                " aaaaaaaaa=29389, aaaaaaaaaa=19567, aaaaaaaaaaa=12958, aaaaaaaaaaaa=8571}",
                5.002096999996331
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "a",
                "[aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaa, aaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaa, ...]",
                "{aaaaaaaa=39940, aaaaaaaaa=38196, aaaaaaaaaa=36988, aaaaaaaaaaa=35334, aaaaaaaaaaaa=33831," +
                " aaaaaaaaaaaaa=32551, aaaaaaaaaaaaaa=31521, aaaaaaaaaaaaaaa=30149, aaaaaaaaaaaaaaaa=28763" +
                ", aaaaaaaaaaaaaaaaa=27543}",
                32.00360900002322
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "[babb, bbcbcacb, acb, c, cba, c, bc, b, c, acc, b, c, b, c, a, c, a, a, c, ba, ...]",
                "{b=167156, c=166869, a=166251, bc=28319, ac=27831, bb=27782, ca=27773, aa=27761, ab=27704, cb=27683}",
                1.9993039999798474
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "[bbbbbab, cbbcacb, cbc, acabc, bac, cccbc, bacc, aca, caa, bbaccb, bcbcbcb, bab, bccaacaaa," +
                " cbcacccabbc, bcbc, cac, accb, aaa, ccaaccaa, bac, ...]",
                "{bcc=12598, bca=12517, cca=12472, baa=12457, cbc=12442, acc=12437, ccb=12420, aaa=12412, cab=12392" +
                ", cac=12372}",
                5.003739999996368
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "abc",
                "[cbcacccbccabaccaacaacaaccbabbaccbccbcabcbcbcbaba, ccbccccbacbcacccabbccabcbc, cacbaacc," +
                " aaccbccacc, cccbaacbbacbcbacbcaca, cabaabbaccaabaaa, caacbcabccbaccaccabbbbbab, aacbbcabbbaab," +
                " aacaaccaccacccbc," +
                " bbbcbbbcccbccabaabaaccacbacbacaccbbaaabbacbaacbaaacbbabbacaaabcbbccbbcccbbaaaacbbabbacccc," +
                " bbbcbaccbacabccbabcabbaccbbbccaaccbacabc, cbcacbaacbccacbaabcbacbbcbbabcbcb," +
                " bbaaaabcbcabcbbccbcbacbababacbcccabcacbababaccababacbaa, aacaccbcbacbaacabaaabacbcbbabaa," +
                " acaabbaacabcbaccbbcabc, aacababbaccbcbccbbabbaccccbabcaaccbbcaaabbbc," +
                " ccabbccbaccbabaaacbcbcaaabbcabccaacc, bcbcccbbb, aaccaccbbcbabcabaabbbccbabbaaba, bbbacacab, ...]",
                "{ccabbcaa=16, accccaaa=15, aaacbcaa=15, cbcccbbc=15, acabbabb=15, bacaaabc=15, abbbbcbb=14," +
                " aabbabab=14, abcbcaba=14, cbacaccb=14}",
                32.010685000021894
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "[bcab, bbbcbbacb, abcb, c, cb, b, abc, b, a, cc, b, ab, bb, b, b, b, a, b, a, a, ...]",
                "{b=249789, c=125444, a=125036, bb=62887, bc=31371, cb=31153, ab=31119, ba=30818, aa=15774, ac=15648}",
                1.999585999979838
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "[bbccbcb, bbbc, cbbcb, bcba, abcabbab, ccb, bcbbb, babba, baa, abcb, bcbc, bbb, bbccccb, ccccbac," +
                " bbccbaabaa, bbb, cbbccbcc, bbb, bcc, bbbbca, ...]",
                "{bbb=41560, bbc=20918, abb=20798, bcb=20791, bab=20753, cbb=20552, bba=20519, bbbb=14169," +
                " abc=10717, aba=10620}",
                5.00315899999616
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "abbc",
                "[ccbbcbabcbbbbbababbaabaabaabcbcccbabcbcabbbbbcbb, bbccccbc, bbbcbbcccbbbacbbccbccabbbabccbbbabb," +
                " babcbcaab, aacbccbbbbca, bbbbaabbbabbbbabbbababb, babaabcbabcbaacb, abbbbbab, baccabbbccabbbbacbb," +
                " bcbacbbb, acccccacbbbcbabbcba, caacbacabbabcbabbbb," +
                " bcbbbbbbbbbcbbcbabaabaabbabbcabbababbbccbaaabbabbcacacbbaaabbbabbabaaabccbcccbbbbbbbbbbbaa," +
                " bcbbbcbabbbcababbccbbabbabbcabbcbcbcbbbccaabbbabcabcbccc, bbbabbaabbbbabbacabbbabbccbbbbc," +
                " bcbbbcba, bbcaaccaacbbbbaccbbbcbbbcbcbbabbacbababbbbbacbbabbacbc, cbabacbbacab," +
                " aababcbbbbabcbacacbcabaaababbb, bbcbcabbbbbccc, ...]",
                "{bbbbbbbb=155, bbbbbbba=97, bbbbbcbb=93, babbbbbb=87, bbbbbbbbb=83, bbcbbbbb=80, bbbcbbbb=79," +
                " bcbbbbbb=78, bbabbbbb=77, bbbbbbcb=73}",
                32.008717000021356
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "[spss, sisisss, i, iss, iss, s, i, si, s, ss, i, ip, iM, i, p, i, s, s, psss, s, ...]",
                "{i=182168, s=181773, p=91352, M=45380, si=33041, ii=32991, is=32976, ss=32971, sp=16706, pi=16449}",
                1.9990949999798069
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "[ssiisss, sis, ssipi, issssisM, sssisi, iMp, pips, psssi, ssssss, isiisis, psiiips, iMs," +
                " siisisisis, iisMs, isi, isi, MMss, sspsisi, sMii, spis, ...]",
                "{sss=16261, sis=16159, iii=16155, iss=16050, ssi=16043, iis=16002, isi=15947, sii=15643, sip=8121" +
                ", spi=8107}",
                5.003636999996235
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "Mississippi",
                "[sisssssisipiMpippipsssiMpsssispsMssssssssssspisi, spisssssppsiisisisisiiis, sMisiiis, Msssssssss," +
                " iipspisspspsiisipipsM, iiiispspi, ippssspsM, sMpsiMssisssMspssi, iiiipsssiMipiiMp," +
                " issMiisMsiMssispsi," +
                " iipsspsissisisMipMpissiiMspispiiMMiipiiiMsisisMppssipispipisssspssssissipMpsisiisppiii," +
                " siississpiMMiiiipsippssisissiiiMipsiiipspisp, isipMpssissipssMpssiMiiip," +
                " ssiipisisispipisssspissisiippssssssiisMsisssMsMsissMipiss, iiispsiiispiMipsiisiMsiisiis," +
                " MsiMisissMiisp, pipppMspsisiisMispssiipspiMisisMpiiisissississppisii, ssMsspisispissMpsiss," +
                " piisssssppispipiiiiiis, iisiisspispsiiisMip, ...]",
                "{isssssss=23, siiiisss=22, sssiisss=22, sisisiii=21, ssssisii=21, iisssiss=21, ssissisi=20," +
                " isiisisi=20, sissiisi=20, iisiissi=20}",
                32.00263800002314
        );
        stringsAtLeast_int_String_fail_helper(5, 3, "");
        stringsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsAtLeast(minSize)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsAtLeast_int() {
        stringsAtLeast_int_helper(
                2,
                1,
                "[ε䊿\u2538\u31e5, \udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ, ᬜK㵏ꏹ, 㩷, 纫\ufe2d, 䝲, 坤琖\u2a43, \uea45, 蕤," +
                " \u2b63\uf637, 鸅, \uee1c\u33b2, ᅺ됽, 䇺, \ue9fd, 㖊, \uaaf0, 覚, 䱸, \u2e24, ...]",
                "{瓫=24, 簐=22, 듑=22, 瀯=21, \uf3e9=21, 䝞=21, 抷=20, 䒢=20, Ẑ=20, 텁=20}",
                1.999585999979838
        );
        stringsAtLeast_int_helper(
                5,
                3,
                "[\u31e5髽肣\uf6ffﳑ赧\ue215, \udd15몱ﲦ䯏, 刿ㄾ䲵箿偵, K㵏ꏹ缄, 坤琖\u2a43퉌\uea45\ue352蕤餥," +
                " \u2b63\uf637鸂, \u33b2酓캆ᅺ됽, \ue9fd\u2aec㖊짎\uaaf0, 覚돘䱸, \uf878ሮܓ鄒, 尩굿\uecf5ꪻ, \ue8b2빮빅," +
                " 瀵컦刓嗏\u3353\ue2d3\ud805, 䫯噋\uf36fꌻ躁\ue87c홃, 壙\udd82픫鼧\u061a\u2e94穨㽖ﶼ䥔, 糦嗮\uf329," +
                " ꯃ慚총\u0e77\uf36bB㽿\u2a57, \udec6ꅪ\udcc6, \u337d萋\u0d5b, 쪡詪쀝\u1366\uf21bᵠ, ...]",
                "{\u31e5髽肣\uf6ffﳑ赧\ue215=1, \udd15몱ﲦ䯏=1, 刿ㄾ䲵箿偵=1, K㵏ꏹ缄=1, 坤琖\u2a43퉌\uea45\ue352蕤餥=1," +
                " \u2b63\uf637鸂=1, \u33b2酓캆ᅺ됽=1, \ue9fd\u2aec㖊짎\uaaf0=1, 覚돘䱸=1, \uf878ሮܓ鄒=1}",
                5.00315899999616
        );
        stringsAtLeast_int_helper(
                32,
                8,
                "[\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03" +
                "띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f㱉泦, 瀵컦刓嗏\u3353\ue2d3\ud805ឃ," +
                " 糦嗮\uf329ﻧ\udd42䞂鎿鐳鰫묆颒錹睸ꯃ慚총\u0e77\uf36bB㽿\u2a57緜\udec6ꅪ\udcc6\u0964\u337d萋\u0d5b詵\ud8ca" +
                "䍾徜쪡詪, 駆퉐庺\u2293\ued0d䴻ꎤ槔横, 䃼匀낛띆ﱓ㝏ᯖ\uea55\ue9c6儖䦣\u202c," +
                " ͺ\u124eꪪ\u0a49䠬㲜\ue852ډұ\ue28c葒ලȞ蛕䮼ხ叙繲\uefc8嶂췴䔾턞, \uab6e䝀㥑\u2e64년믱젯䁅偘滞\ue89bᖒ㿘燔ꎿ趵," +
                " 㙴ᶙ䁩聂ꃖꐲ\udc58\u26f2, 쳮陜阏顓\u2320쓎狙쟒㕯뚗\u32b0頵\u2606ꎚ궥婜쇻ᅒ댲, 旞\u2613\u19de\u0618戯韕똽\u25ad," +
                " 죴\u0d47㚏帇퀯\uebc7晸犋鈖ꤥ쿕\u0f1b\u2eea\uf800ᓑ濙䢗瞁퓰," +
                " 梏\u2684\ue40c\u2b83葆а팗풬궺쥶ꎠ뗢撻뵪\u21c0羾놂쒞沅," +
                " 헑䈏닁\ue649គ姕\u1069\u2f0d듂狚\ue672団䅁悲枧\u1b56偰摡泈\u1a60㭍\u2af8운\u2026桶뼄ቾᶝ睗㥐厖剹ᥔ㻶\uf3a8춮茞" +
                "\ue531칗ᳯ\u073d飰\ue480\ua6f4\u19b1\u1739箴\ued1a쀁\ua7af탰\u3243\u4df4\u2a33䨺\ud845㼰館㾸侒叵\uf351鳸" +
                "뾁냥ꯈ\u1aba呼\u0b8c䦼鳙柿쥇顆乃\ude93쁳\u0aa9蕕챲閦\ue10d嬵\uecdaꜢஆ挑쑹窀糘," +
                " 甍뮣民皑\u291e秳ʵ솄퍆芦瀉벧\u2e50滎\u25a0爑\u1fce廏놿\u07aa\udde1頔臙ㆲ\uee84㢍䒉藟㗨詂ܒ嘟ᰁ謳䒁\u17cb" +
                "\u0875멖\uecda㙿\ua837稔뇐\uee3aۮ\uf6cd\ue22c\u2fbe톋艸操샣墺貗\u1c47\uf2ff," +
                " 䌚\ufe3d춢후Ꜯ卩鳰阘细\ue9d5\ude3a显鏌㓆갭\uda6cᎳ\ua9f4쉙嘂\uf6a5䜐禎\u0529K쬋壵\ue61e쵕ᶑ\uf04f," +
                " ᬱ뭇昺玉뾂炣虹㨘," +
                " 씅潵겧\u0f24㺸則穣클䜜걓绡缂敉勪\ue498溯7익Ᏺ㥥㖃ど츪퇢㴯ᚅ\u1deb齞杁鱼欎䌕렔횋葑䎌쯹笨\udd06\ude35鲦頒Ϯ懜焣担戎" +
                "몔\ue47a串\u0c00\ue59b聾ﶯ," +
                " \u2153\uf7cdꜰ耕詴茟\u0482쵕ꏠ\ud847\uef98쾭," +
                " 檌裤㻞椼憊ⴋ\u21ba\uec15檮滙\u0cec巶먛㺱\udbf4蠛玌疟ᒚⴓ\u2818\u2b5d\ud85cⲄ뤀\u0361ꚸ璎祍忢," +
                " 좲햽퐗僮眏겴\uf5e2霺ⱊȢ\ue41d\u2f43뜓軷, ...]",
                "{\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03" +
                "띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f㱉泦=1, 瀵컦刓嗏\u3353\ue2d3\ud805ឃ=1," +
                " 糦嗮\uf329ﻧ\udd42䞂鎿鐳鰫묆颒錹睸ꯃ慚총\u0e77\uf36bB㽿\u2a57緜\udec6ꅪ\udcc6\u0964\u337d萋\u0d5b詵\ud8ca" +
                "䍾徜쪡詪=1," +
                " 駆퉐庺\u2293\ued0d䴻ꎤ槔横=1, 䃼匀낛띆ﱓ㝏ᯖ\uea55\ue9c6儖䦣\u202c=1," +
                " ͺ\u124eꪪ\u0a49䠬㲜\ue852ډұ\ue28c葒ලȞ蛕䮼ხ叙繲\uefc8嶂췴䔾턞=1," +
                " \uab6e䝀㥑\u2e64년믱젯䁅偘滞\ue89bᖒ㿘燔ꎿ趵=1, 㙴ᶙ䁩聂ꃖꐲ\udc58\u26f2=1," +
                " 쳮陜阏顓\u2320쓎狙쟒㕯뚗\u32b0頵\u2606ꎚ궥婜쇻ᅒ댲=1, 旞\u2613\u19de\u0618戯韕똽\u25ad=1}",
                32.008717000021356
        );
        stringsAtLeast_int_fail_helper(5, 5);
        stringsAtLeast_int_fail_helper(4, 5);
    }

    private static void distinctStrings_int_String_helper(
            int size,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.distinctStrings(size, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void distinctStrings_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.distinctStrings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_int_String() {
        distinctStrings_int_String_helper(
                0,
                "a",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        distinctStrings_int_String_helper(
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}"
        );
        distinctStrings_int_String_helper(
                0,
                "abc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        distinctStrings_int_String_helper(
                1,
                "abc",
                "[b, b, c, b, a, b, b, b, b, b, a, b, b, c, b, c, a, c, b, b, ...]",
                "{c=333615, b=333313, a=333072}"
        );
        distinctStrings_int_String_helper(
                2,
                "abc",
                "[bc, ba, ba, bc, bc, ac, bc, ac, ba, cb, cb, ac, ab, ca, ba, cb, ca, cb, ca, ba, ...]",
                "{bc=167243, cb=166812, ba=166749, ca=166743, ab=166278, ac=166175}"
        );
        distinctStrings_int_String_helper(
                3,
                "abc",
                "[bca, bac, bca, cba, cba, cba, acb, cab, acb, cab, cab, acb, abc, cba, bca, bac, cba, bca, acb," +
                " cba, ...]",
                "{cab=167288, bca=167242, cba=167051, abc=166598, acb=166062, bac=165759}"
        );
        distinctStrings_int_String_helper(
                0,
                "abbc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        distinctStrings_int_String_helper(
                1,
                "abbc",
                "[b, b, c, b, b, c, a, b, b, c, c, b, c, b, c, b, a, b, b, b, ...]",
                "{b=499640, c=250298, a=250062}"
        );
        distinctStrings_int_String_helper(
                2,
                "abbc",
                "[bc, bc, ab, bc, cb, cb, cb, ab, bc, ba, cb, bc, ba, bc, ba, cb, cb, bc, ba, ab, ...]",
                "{ba=249826, bc=249607, cb=167056, ab=166498, ca=83615, ac=83398}"
        );
        distinctStrings_int_String_helper(
                3,
                "abbc",
                "[bca, bca, bca, cba, bca, cba, abc, abc, cba, bca, bac, bca, bca, bca, bca, cba, cba, bca, abc," +
                " bca, ...]",
                "{bca=250476, bac=249778, cba=166982, abc=166446, cab=83355, acb=82963}"
        );
        distinctStrings_int_String_helper(
                0,
                "Mississippi",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        distinctStrings_int_String_helper(
                1,
                "Mississippi",
                "[p, p, s, s, s, p, s, s, i, i, s, s, s, p, s, i, s, i, s, s, ...]",
                "{s=363979, i=363703, p=181581, M=90737}"
        );
        distinctStrings_int_String_helper(
                2,
                "Mississippi",
                "[ps, sp, si, is, sp, si, si, si, pi, is, si, pi, si, sM, si, si, si, pi, Mp, ip, ...]",
                "{si=208293, is=207849, ip=103895, sp=103888, ps=81111, pi=79988, iM=52287, sM=51836, Mi=36458" +
                ", Ms=36215}"
        );
        distinctStrings_int_String_helper(
                3,
                "Mississippi",
                "[psi, isp, sip, isp, isM, sip, iMp, ips, siM, psi, spM, spi, sip, psi, ips, iMs, pis, spi, isM," +
                " sMi, ...]",
                "{isp=138594, sip=138527, spi=83691, ips=82883, siM=69277, isM=69089, psi=64708, pis=64454," +
                " iMs=35048, sMi=34639}"
        );
        distinctStrings_int_String_fail_helper(1, "");
        distinctStrings_int_String_fail_helper(-1, "abc");
    }

    private static void distinctStrings_int_helper(int size, @NotNull String output, @NotNull String topSampleCount) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.distinctStrings(size)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void distinctStrings_int_fail_helper(int size) {
        try {
            P.distinctStrings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_int() {
        distinctStrings_int_helper(
                0,
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        distinctStrings_int_helper(
                1,
                "[嘩, 퇉, 馃, \u2df2, ε, 䊿, \u2538, \u31e5, 髽, 肣, \uf6ff, ﳑ, 赧, \ue215, \u17f3, \udd75, 껸, \udd15," +
                " 몱, ﲦ, ...]",
                "{\uf1b2=36, 撢=35, આ=34, 퉃=34, \27=33, 韖=32, 㖒=32, 膗=31, 㗞=31, 䕦=31}"
        );
        distinctStrings_int_helper(
                2,
                "[嘩퇉, 馃\u2df2, ε䊿, \u2538\u31e5, 髽肣, \uf6ffﳑ, 赧\ue215, \u17f3\udd75, 껸\udd15, 몱ﲦ, 䯏ϡ," +
                " 罖\u19dc, 刿ㄾ, 䲵箿, 偵恾, ᬜK, 㵏ꏹ, 缄㩷, ⴿ읾, 纫\ufe2d, ...]",
                "{\u2bdbᴴ=2, \uf310틺=2, 㑰\uf5be=2, \uf480\u23c0=2, Ⳬ\ue4b6=2, ꢓ\ue5db=2, 백蝰=2, \ue13d굛=2," +
                " \u2688瘷=2, ύ\u3099=2}"
        );
        distinctStrings_int_helper(
                3,
                "[嘩퇉馃, \u2df2ε䊿, \u2538\u31e5髽, 肣\uf6ffﳑ, 赧\ue215\u17f3, \udd75껸\udd15, 몱ﲦ䯏, ϡ罖\u19dc, 刿ㄾ䲵," +
                " 箿偵恾, ᬜK㵏, ꏹ缄㩷, ⴿ읾纫, \ufe2d㗂䝲, \uf207갩힜, 坤琖\u2a43, 퉌\uea45\ue352, 蕤餥䉀, \u2b63\uf637鸂," +
                " 鸅误輮, ...]",
                "{嘩퇉馃=1, \u2df2ε䊿=1, \u2538\u31e5髽=1, 肣\uf6ffﳑ=1, 赧\ue215\u17f3=1, \udd75껸\udd15=1, 몱ﲦ䯏=1," +
                " ϡ罖\u19dc=1, 刿ㄾ䲵=1, 箿偵恾=1}"
        );
        distinctStrings_int_fail_helper(-1);
    }

    private static void distinctLists_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctLists(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void distinctLists_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        distinctLists_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void distinctLists_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).distinctLists(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctLists_Iterable() {
        distinctLists_Iterable_helper_uniform(
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [], [5], [5], [], [], [5], [5], [5], [], [5], [], [], [5], [5], [], ...]",
                "{[5]=500875, []=499125}",
                0.5008749999935656
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [], [5], [5], [5], [5], [5], [5], [5], [], [5], [], [5], [], [5]," +
                " ...]",
                "{[5]=666187, []=333813}",
                0.6661869999983192
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[5]",
                "[[5], [5], [5], [5], [5], [], [5], [], [5], [5], [5], [5], [5], [5], [5], [5], [], [5], [5], [5]," +
                " ...]",
                "{[5]=799806, []=200194}",
                0.7998060000021615
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3]",
                "[[2, 1], [2, 3, 1], [1, 3], [3, 2], [], [], [1, 3], [2], [], [], [3], [1, 3], [3], [3], [], [3]," +
                " [], [], [], [], ...]",
                "{[]=499504, [2]=100253, [3]=100058, [1]=100023, [3, 2]=25123, [2, 3]=25037, [2, 1]=25028," +
                " [3, 1]=25023, [1, 2]=24962, [1, 3]=24941}",
                0.7507059999970308
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3]",
                "[[2], [2], [3, 2], [], [3, 2], [1, 3], [2, 1], [3], [2, 1, 3], [1], [1], [1], [2, 1], [2, 3], []," +
                " [2], [2, 3, 1], [], [], [], ...]",
                "{[]=333247, [2]=95442, [3]=95325, [1]=94824, [1, 2]=38230, [3, 1]=38133, [3, 2]=38065," +
                " [2, 3]=37941, [1, 3]=37932, [2, 1]=37897}",
                1.2008789999923022
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3]",
                "[[2], [2], [3, 2, 1], [1], [3], [2, 1, 3], [2, 3], [2, 3, 1], [], [3, 1, 2], [1, 3], [3, 1]," +
                " [3, 1, 2], [], [], [3, 1], [1], [3, 1], [], [1, 3], ...]",
                "{[]=199912, [1]=72945, [2]=72868, [3]=72696, [1, 3, 2]=55894, [3, 1, 2]=55497, [2, 3, 1]=55427," +
                " [3, 2, 1]=55389, [2, 1, 3]=55356, [1, 2, 3]=55293}",
                1.7145229999887661
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, null, 3]",
                "[[null, 1], [null, 3, 1], [1, 3], [3, null], [], [], [1, 3], [null], [], [], [3], [1, 3], [3], [3]," +
                " [], [3], [], [], [], [], ...]",
                "{[]=499504, [null]=100253, [3]=100058, [1]=100023, [3, null]=25123, [null, 3]=25037," +
                " [null, 1]=25028, [3, 1]=25023, [1, null]=24962, [1, 3]=24941}",
                0.7507059999970308
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, null, 3]",
                "[[null], [null], [3, null], [], [3, null], [1, 3], [null, 1], [3], [null, 1, 3], [1], [1], [1]," +
                " [null, 1], [null, 3], [], [null], [null, 3, 1], [], [], [], ...]",
                "{[]=333247, [null]=95442, [3]=95325, [1]=94824, [1, null]=38230, [3, 1]=38133, [3, null]=38065," +
                " [null, 3]=37941, [1, 3]=37932, [null, 1]=37897}",
                1.2008789999923022
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, null, 3]",
                "[[null], [null], [3, null, 1], [1], [3], [null, 1, 3], [null, 3], [null, 3, 1], [], [3, 1, null]," +
                " [1, 3], [3, 1], [3, 1, null], [], [], [3, 1], [1], [3, 1], [], [1, 3], ...]",
                "{[]=199912, [1]=72945, [null]=72868, [3]=72696, [1, 3, null]=55894, [3, 1, null]=55497," +
                " [null, 3, 1]=55427, [3, null, 1]=55389, [null, 1, 3]=55356, [1, null, 3]=55293}",
                1.7145229999887661
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4]",
                "[[2, 4, 1], [2, 3, 4, 1], [1, 3, 4], [4], [4], [3], [1, 3], [2], [], [], [4], [2], [1], [], [3]," +
                " [3], [], [3], [], [], ...]",
                "{[]=499557, [3]=71710, [2]=71646, [1]=71506, [4]=71260, [4, 3]=12091, [1, 4]=12031, [2, 3]=12001," +
                " [2, 1]=11981, [4, 2]=11919}",
                0.8006769999971934
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4]",
                "[[2, 4], [2, 3], [4, 3], [3, 4, 2], [], [1, 3, 4, 2], [2], [], [3, 4], [2, 1, 3], [], [], [1], [1]," +
                " [2, 4], [], [2, 3, 4], [4], [4], [2, 3, 4, 1], ...]",
                "{[]=333041, [2]=66872, [1]=66679, [3]=66527, [4]=66467, [3, 2]=16735, [2, 3]=16728, [3, 4]=16715," +
                " [4, 3]=16691, [1, 2]=16689}",
                1.334835999990812
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4]",
                "[[2, 4], [2], [4, 3, 2, 1], [1, 3], [], [], [3], [2, 4, 1], [2, 3, 4], [2, 3, 4, 1], [4, 3, 2]," +
                " [3, 1, 4, 2], [1], [], [1, 3, 4], [3, 1], [3, 1, 2, 4], [3], [], [3, 4, 1], ...]",
                "{[]=200010, [2]=50021, [4]=49847, [3]=49827, [1]=49823, [3, 2]=16860, [4, 1]=16803, [4, 3]=16773," +
                " [2, 1]=16693, [3, 1]=16676}",
                2.001787999981212
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 2, 4]",
                "[[2, 4, 1], [2, 4, 1], [1, 2, 4], [4], [4], [2], [1, 2], [2], [], [], [4], [2], [1], [], [2], [2]," +
                " [], [2], [], [], ...]",
                "{[]=499557, [2]=167198, [1]=71506, [4]=71260, [2, 1]=33256, [2, 4]=32979, [4, 2]=28785," +
                " [1, 2]=28498, [1, 4]=12031, [4, 1]=11881}",
                0.7339709999971153
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 2, 4]",
                "[[2, 4], [2], [4, 2], [2, 4], [], [1, 2, 4], [2], [], [2, 4], [2, 1], [], [], [1], [1], [2, 4], []," +
                " [2, 4], [4], [4], [2, 4, 1], ...]",
                "{[]=333041, [2]=166862, [1]=66679, [4]=66467, [2, 4]=55507, [2, 1]=55502, [4, 2]=44714," +
                " [1, 2]=44328, [2, 1, 4]=27856, [2, 4, 1]=27729}",
                1.1676389999927037
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 2, 4]",
                "[[2, 4], [2], [4, 2, 1], [1, 2], [], [], [2], [2, 4, 1], [2, 4], [2, 4, 1], [4, 2], [2, 1, 4], [1]," +
                " [], [1, 2, 4], [2, 1], [2, 1, 4], [2], [], [2, 4, 1], ...]",
                "{[]=200010, [2]=133338, [2, 4, 1]=66924, [2, 1]=66785, [2, 1, 4]=66587, [2, 4]=66567," +
                " [1, 2, 4]=50238, [4, 2]=50135, [4, 2, 1]=50046, [4]=49847}",
                1.667697999989275
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [2], [2], [2], [2], [], [], [2], [2], [2], [], [2], [2], [], [2], [], [], ...]",
                "{[2]=500443, []=499557}",
                0.5004429999935531
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [], [2], [2], [], [2], [2], [], [], [2], [2], [2], [], [2], [2], [2], [2], ...]",
                "{[2]=666959, []=333041}",
                0.6669589999983414
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [], [], [2], [2], [2], [2], [2], [2], [2], [], [2], [2], [2], [2], [], [2]," +
                " ...]",
                "{[2]=799990, []=200010}",
                0.7999900000021668
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 9], [6, 2, 7, 10], [], [3], [5, 7], [6], [], [], [4], [6], [3], [7], [], [], [1], [], [], []," +
                " [], [], ...]",
                "{[]=500030, [10]=26710, [7]=26432, [4]=26418, [1]=26417, [6]=26409, [2]=26409, [5]=26279," +
                " [8]=26268, [3]=26245}",
                0.9078379999975383
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8], [6, 2], [6, 10], [], [5, 7, 4, 6, 3], [], [4], [3, 4, 7], [1, 9], [9], [10, 6, 3]," +
                " [6, 7, 4], [2, 4, 8], [10, 3, 8, 5, 9, 7], [], [], [], [4, 8, 3, 7, 5, 6], [2, 7], [], ...]",
                "{[]=333018, [6]=23950, [10]=23943, [7]=23883, [2]=23862, [4]=23764, [1]=23753, [3]=23694," +
                " [5]=23667, [8]=23610}",
                1.6697689999898184
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8], [6], [4, 8, 3, 6, 7, 9, 1, 5], [10, 6, 3], [6, 7], [10, 3, 8, 5, 9, 7, 1, 4], []," +
                " [7, 1, 4, 5, 3, 8, 6], [3, 8], [7, 1, 10, 3], [3, 5, 2, 10], [1], [3, 9, 5, 6], [5], [3, 1, 9, 8]," +
                " [], [], [1, 4], [6, 7], [4, 9, 6, 2], ...]",
                "{[]=200177, [10]=17554, [8]=17510, [1]=17479, [6]=17445, [9]=17442, [5]=17440, [7]=17376," +
                " [4]=17340, [2]=17337}",
                2.8588639999882393
        );
        distinctLists_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 10, 7], [5], [], [10, 1], [], [3], [7], [], [5], [3], [], [], [], [], [], [], [], [], [5]," +
                " [9, 15, 6, 12], ...]",
                "{[]=499603, [1]=71387, [2]=51631, [3]=37715, [4]=27574, [5]=20807, [6]=15264, [7]=11330," +
                " [1, 2]=8697, [8]=8526}",
                0.8811449999975006
        );
        distinctLists_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 4], [1, 3, 2, 7], [2, 3, 1], [], [5, 9, 15], [12], [1, 2], [], [9, 4, 7, 6, 2, 5, 1], []," +
                " [], [], [3, 1, 5], [2], [1, 4, 5], [12], [2], [], [4, 1], [1], ...]",
                "{[]=333149, [1]=66271, [2]=47475, [3]=34388, [4]=24997, [5]=18733, [6]=13708, [1, 2]=11823," +
                " [2, 1]=11371, [7]=10160}",
                1.583489999990105
        );
        distinctLists_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 4], [7, 8, 2, 3, 1], [15, 6, 12, 1, 2], [13], [2, 5, 1, 6, 10, 3, 4], []," +
                " [2, 12, 1, 4, 3, 6], [], [], [], [1, 2, 3], [5, 4], [9], [4, 1, 6, 7, 5], [], [1, 2, 3]," +
                " [13, 7, 10], [4, 19], [2, 4, 1, 22, 17, 3], [], ...]",
                "{[]=199867, [1]=49790, [2]=35481, [3]=25159, [4]=18608, [5]=13362, [1, 2]=11519, [2, 1]=10965," +
                " [6]=9910, [1, 3]=8009}",
                2.668782999988186
        );
        distinctLists_Iterable_helper(
                1,
                repeat(1),
                "[[1], [1], [1], [1], [1], [], [1], [1], [], [], [1], [1], [1], [], [1], [], [], [1], [1], [], ...]",
                "{[1]=500875, []=499125}",
                0.5008749999935656
        );
        distinctLists_Iterable_helper(
                2,
                repeat(1),
                "[[1], [1], [1], [1], [1], [1], [], [1], [1], [1], [1], [1], [1], [1], [], [1], [], [1], [], [1]," +
                " ...]",
                "{[1]=666187, []=333813}",
                0.6661869999983192
        );
        distinctLists_Iterable_helper(
                4,
                repeat(1),
                "[[1], [1], [1], [1], [1], [], [1], [], [1], [1], [1], [1], [1], [1], [1], [1], [], [1], [1], [1]" +
                ", ...]",
                "{[1]=799806, []=200194}",
                0.7998060000021615
        );
        distinctLists_Iterable_fail_helper(1, Collections.emptyList());
        distinctLists_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        distinctLists_Iterable_fail_helper(0, P.integers());
        distinctLists_Iterable_fail_helper(-1, P.integers());
    }

    private static void distinctStrings_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStrings(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStrings_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).distinctStrings(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_String() {
        distinctStrings_String_helper(
                1,
                "a",
                "[a, a, a, a, a, , a, a, , , a, a, a, , a, , , a, a, , ...]",
                "{a=500875, =499125}",
                0.5008749999935656
        );
        distinctStrings_String_helper(
                2,
                "a",
                "[a, a, a, a, a, a, , a, a, a, a, a, a, a, , a, , a, , a, ...]",
                "{a=666187, =333813}",
                0.6661869999983192
        );
        distinctStrings_String_helper(
                4,
                "a",
                "[a, a, a, a, a, , a, , a, a, a, a, a, a, a, a, , a, a, a, ...]",
                "{a=799806, =200194}",
                0.7998060000021615
        );
        distinctStrings_String_helper(
                1,
                "abc",
                "[ba, bca, ac, cb, , , ac, b, , , c, ac, c, c, , c, , , , , ...]",
                "{=499504, b=100253, c=100058, a=100023, cb=25123, bc=25037, ba=25028, ca=25023, ab=24962, ac=24941}",
                0.7507059999970308
        );
        distinctStrings_String_helper(
                2,
                "abc",
                "[b, b, cb, , cb, ac, ba, c, bac, a, a, a, ba, bc, , b, bca, , , , ...]",
                "{=333247, b=95442, c=95325, a=94824, ab=38230, ca=38133, cb=38065, bc=37941, ac=37932, ba=37897}",
                1.2008789999923022
        );
        distinctStrings_String_helper(
                4,
                "abc",
                "[b, b, cba, a, c, bac, bc, bca, , cab, ac, ca, cab, , , ca, a, ca, , ac, ...]",
                "{=199912, a=72945, b=72868, c=72696, acb=55894, cab=55497, bca=55427, cba=55389, bac=55356" +
                ", abc=55293}",
                1.7145229999887661
        );
        distinctStrings_String_helper(
                1,
                "abbc",
                "[bca, bca, abc, c, c, b, ab, b, , , c, b, a, , b, b, , b, , , ...]",
                "{=499557, b=167198, a=71506, c=71260, ba=33256, bc=32979, cb=28785, ab=28498, ac=12031, ca=11881}",
                0.7339709999971153
        );
        distinctStrings_String_helper(
                2,
                "abbc",
                "[bc, b, cb, bc, , abc, b, , bc, ba, , , a, a, bc, , bc, c, c, bca, ...]",
                "{=333041, b=166862, a=66679, c=66467, bc=55507, ba=55502, cb=44714, ab=44328, bac=27856, bca=27729}",
                1.1676389999927037
        );
        distinctStrings_String_helper(
                4,
                "abbc",
                "[bc, b, cba, ab, , , b, bca, bc, bca, cb, bac, a, , abc, ba, bac, b, , bca, ...]",
                "{=200010, b=133338, bca=66924, ba=66785, bac=66587, bc=66567, abc=50238, cb=50135, cba=50046" +
                ", c=49847}",
                1.667697999989275
        );
        distinctStrings_String_helper(
                1,
                "Mississippi",
                "[sp, si, is, , is, s, , , s, s, s, i, i, , i, , , , , , ...]",
                "{=499907, s=111371, i=111271, p=49940, si=31957, is=31807, M=23658, ip=13933, sp=13777, ps=12643}",
                0.7700039999971866
        );
        distinctStrings_String_helper(
                2,
                "Mississippi",
                "[si, si, si, i, is, , s, s, iMp, , , p, ps, s, is, psi, i, , si, , ...]",
                "{=333528, s=106872, i=106248, si=50200, is=50121, p=45804, M=21506, sp=20350, ip=20293, pi=17481}",
                1.2632049999918284
        );
        distinctStrings_String_helper(
                4,
                "Mississippi",
                "[si, s, sipM, s, ps, s, psiM, s, sMi, si, sMip, sipM, , spi, i, sMpi, , , Ms, s, ...]",
                "{=199852, s=82176, i=81772, is=57102, si=57080, p=34200, sip=30455, isp=30209, spi=22840, ips=22800}",
                1.8740139999846195
        );
        distinctStrings_String_fail_helper(1, "");
        distinctStrings_String_fail_helper(0, "abc");
        distinctStrings_String_fail_helper(-1, "abc");
    }

    private static void distinctStrings_helper(
            int scale,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStrings()));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStrings_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).distinctStrings());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings() {
        distinctStrings_helper(
                1,
                "[ε䊿\u2538, \udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ, ᬜK㵏, 㩷, 纫, 䝲, 坤琖, \uea45, , , \u2b63, 鸅, \uee1c, , ᅺ, 䇺," +
                " , 㖊, , , ...]",
                "{=499557, \uf59a=15, 僵=14, \u12c7=14, 瘍=14, \ue0de=14, \ua838=13, 䃢=13, 喽=13, 瓫=13}",
                1.0006239999976707
        );
        distinctStrings_helper(
                2,
                "[\u31e5髽肣\uf6ff, \udd15몱ﲦ, 刿ㄾ, K㵏ꏹ, , 坤琖\u2a43퉌\uea45, 餥, , \u33b2酓캆, \ue9fd\u2aec㖊짎, , ," +
                " 䱸, \uf878, 尩굿\uecf5, , 瀵컦刓嗏\u3353\ue2d3\ud805, 䫯噋\uf36fꌻ, 홃, 壙\udd82픫鼧\u061a\u2e94穨, ...]",
                "{=333041, 趤=15, 挗=13, \u2fae=13, 阤=12, \u0978=12, \ue2fe=12, \uab10=12, 䖸=12, \ue973=12}",
                2.0036399999891383
        );
        distinctStrings_helper(
                4,
                "[\u31e5髽肣\uf6ff, \udd15몱, \u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd, 全覚, , , ሮ," +
                " 尩굿\uecf5ꪻ疜, 瀵컦刓, 壙\udd82픫鼧\u061a\u2e94穨㽖ﶼ䥔핀糦嗮\uf329ﻧ\udd42, ꯃ慚총\u0e77\uf36b," +
                " 駆퉐庺\u2293\ued0d䴻ꎤ槔横靯ढ, 䃼, , 만ᑒ拷\ue68e, \u2506囀ͺ\u124eꪪ, 嶂췴䔾턞\uead1猂唯湑ﮍ蹙甧, \uab6e, ," +
                " 滞\ue89bᖒ㿘, ...]",
                "{=200010, \ued08=11, 듏=11, \ua495=11, 幱=10, 㚼=10, Ꙛ=10, 홣=10, ﺆ=10, \ua494=10}",
                4.005472999991468
        );
        distinctStrings_fail_helper(0);
        distinctStrings_fail_helper(-1);
    }

    private static void distinctListsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctListsAtLeast(minSize, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void distinctListsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        distinctListsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void distinctListsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).distinctListsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    private static void distinctStringsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStringsAtLeast(minSize, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStringsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).distinctStringsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctListsAtLeast() {
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}",
                1.000000000007918
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3]",
                "[[2, 1], [2, 3, 1], [1, 3, 2], [3], [3, 2, 1], [3], [2, 3], [2], [3], [1, 3], [2], [3], [2], [3]," +
                " [1], [3], [1], [1], [3], [2, 1], ...]",
                "{[2]=200397, [3]=200001, [1]=199719, [2, 3]=50590, [1, 3]=49968, [3, 2]=49927, [3, 1]=49752," +
                " [2, 1]=49731, [1, 2]=49715, [2, 1, 3]=16871}",
                1.50008299998526
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3]",
                "[[2, 1, 3], [3, 2, 1], [1, 3, 2], [3, 2, 1], [2, 1, 3], [3, 1, 2], [2, 1, 3], [2, 3, 1], [2, 3, 1]," +
                " [1, 3, 2], [3, 2, 1], [2, 3, 1], [1, 3, 2], [1, 3, 2], [3, 1, 2], [3, 2, 1], [3, 2, 1], [3, 2, 1]," +
                " [3, 1, 2], [1, 2, 3], ...]",
                "{[2, 3, 1]=167455, [1, 2, 3]=167096, [1, 3, 2]=166726, [3, 1, 2]=166462, [3, 2, 1]=166272" +
                ", [2, 1, 3]=165989}",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, null, 3]",
                "[[null, 1], [null, 3, 1], [1, 3, null], [3], [3, null, 1], [3], [null, 3], [null], [3], [1, 3]," +
                " [null], [3], [null], [3], [1], [3], [1], [1], [3], [null, 1], ...]",
                "{[null]=200397, [3]=200001, [1]=199719, [null, 3]=50590, [1, 3]=49968, [3, null]=49927," +
                " [3, 1]=49752, [null, 1]=49731, [1, null]=49715, [null, 1, 3]=16871}",
                1.50008299998526
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, null, 3]",
                "[[null, 1, 3], [3, null, 1], [1, 3, null], [3, null, 1], [null, 1, 3], [3, 1, null], [null, 1, 3]," +
                " [null, 3, 1], [null, 3, 1], [1, 3, null], [3, null, 1], [null, 3, 1], [1, 3, null], [1, 3, null]," +
                " [3, 1, null], [3, null, 1], [3, null, 1], [3, null, 1], [3, 1, null], [1, null, 3], ...]",
                "{[null, 3, 1]=167455, [1, null, 3]=167096, [1, 3, null]=166726, [3, 1, null]=166462," +
                " [3, null, 1]=166272, [null, 1, 3]=165989}",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4]",
                "[[2, 4, 1], [2, 3, 4, 1], [1, 3, 4, 2], [4], [4, 2], [3], [1, 3, 4], [2], [1], [4], [2], [1, 3]," +
                " [3, 2], [3], [2], [3], [1], [3], [1], [1], ...]",
                "{[2]=143315, [4]=143102, [1]=143064, [3]=142514, [2, 3]=24022, [4, 2]=23871, [1, 4]=23861," +
                " [3, 4]=23858, [4, 1]=23855, [3, 1]=23805}",
                1.5996069999831977
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4]",
                "[[2, 4, 1, 3], [4, 3, 2], [3, 4, 2, 1], [1, 3, 4, 2], [4, 3, 2], [3, 4, 2], [2, 1, 3], [1, 3, 4]," +
                " [2, 4, 1, 3], [2, 3, 4], [4, 2, 1, 3], [2, 3, 4, 1], [1, 3, 2], [4, 3, 2], [3, 1, 2]," +
                " [2, 3, 4, 1], [1, 3, 4], [1, 4, 3], [3, 1, 4], [4, 2, 1], ...]",
                "{[1, 3, 2]=28020, [3, 2, 4]=27972, [4, 3, 1]=27942, [4, 2, 3]=27939, [2, 3, 4]=27910," +
                " [4, 3, 2]=27893, [2, 1, 3]=27867, [4, 1, 2]=27856, [2, 3, 1]=27844, [3, 2, 1]=27827}",
                3.3338519999899345
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 2, 4]",
                "[[2, 4, 1], [2, 4, 1], [1, 2, 4], [4], [4, 2], [2], [1, 2, 4], [2], [1], [4], [2], [1, 2], [2]," +
                " [2], [2], [2], [1], [2], [1], [1], ...]",
                "{[2]=333476, [4]=143102, [1]=143064, [2, 4]=66615, [2, 1]=66225, [4, 2]=57093, [1, 2]=56893," +
                " [1, 4]=23861, [4, 1]=23855, [2, 4, 1]=16769}",
                1.466173999985577
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 2, 4]",
                "[[2, 4, 1], [4, 2, 1], [4, 2, 1], [2, 1, 4], [2, 4, 1], [1, 2, 4], [2, 4, 1], [2, 4, 1], [2, 4, 1]," +
                " [4, 2, 1], [2, 4, 1], [1, 2, 4], [1, 4, 2], [2, 1, 4], [4, 2, 1], [2, 1, 4], [2, 1, 4], [1, 4, 2]," +
                " [1, 2, 4], [1, 2, 4], ...]",
                "{[2, 1, 4]=249877, [2, 4, 1]=249848, [4, 2, 1]=167153, [1, 2, 4]=167054, [4, 1, 2]=83176" +
                ", [1, 4, 2]=82892}",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2]," +
                " [2], ...]",
                "{[2]=1000000}",
                1.000000000007918
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 9, 4], [6, 2, 7], [10], [8], [3], [5, 7, 4], [6], [5], [4, 8], [6], [3, 4], [7], [9], [1, 9]," +
                " [9], [5], [4], [4], [10, 6, 3], [7], ...]",
                "{[6]=53166, [5]=52878, [2]=52770, [4]=52720, [3]=52634, [1]=52615, [8]=52598, [9]=52572," +
                " [10]=52566, [7]=52513}",
                1.8170889999810345
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 4, 2, 8], [6, 2, 7], [6, 10, 5, 8, 3], [5, 7, 4, 6, 3], [6, 1, 4], [3, 4, 7, 9, 1], [9, 5, 4]," +
                " [10, 6, 3, 7], [6, 7, 4], [2, 4, 8, 7, 3, 6], [10, 3, 8, 5], [7, 5, 1], [4, 8, 3, 7, 5, 6, 2, 1]," +
                " [4, 5, 3, 8], [1, 7, 4], [7, 9, 3, 8, 4, 5], [7, 1, 10, 3], [3, 6, 10, 9], [2, 3, 10, 8]," +
                " [1, 2, 5], ...]",
                "{[2, 4, 5]=654, [4, 10, 3]=647, [4, 8, 5]=645, [10, 7, 4]=641, [7, 1, 6]=637, [9, 1, 5]=635," +
                " [9, 2, 3]=635, [2, 3, 4]=634, [2, 5, 7]=633, [8, 4, 1]=633}",
                4.168420999985633
        );
        distinctListsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[4, 8, 3, 6, 7, 9, 1, 5, 2, 10], [7, 10, 8, 3, 4, 9, 5, 6, 2, 1], [1, 7, 4, 6, 9, 3, 8, 5, 10, 2]," +
                " [1, 2, 5, 6, 9, 3, 10, 7, 8, 4], [4, 9, 6, 2, 10, 1, 5, 8, 7], [5, 4, 7, 1, 8, 3, 6, 10, 2]," +
                " [2, 10, 3, 6, 4, 8, 7, 1, 9, 5], [4, 2, 6, 5, 7, 10, 8, 1, 3, 9], [3, 5, 10, 1, 9, 7, 6, 4]," +
                " [2, 8, 10, 3, 4, 9, 7, 5], [6, 8, 5, 9, 4, 2, 3, 10, 7, 1], [5, 6, 10, 7, 2, 4, 9, 1, 3, 8]," +
                " [9, 10, 1, 7, 3, 2, 6, 8, 4, 5], [10, 8, 6, 3, 4, 7, 5, 9, 2, 1], [2, 9, 8, 10, 7, 4, 1, 5, 3]," +
                " [8, 3, 10, 1, 2, 7, 6, 9, 4, 5], [1, 4, 5, 8, 9, 10, 3, 6, 7], [9, 1, 4, 3, 2, 10, 8, 7, 5, 6]," +
                " [5, 6, 9, 7, 2, 10, 4, 3, 1], [5, 9, 1, 2, 7, 4, 8, 10, 3, 6], ...]",
                "{[3, 5, 9, 6, 1, 10, 2, 8, 7, 4]=5, [7, 9, 3, 4, 2, 10, 8, 1, 6, 5]=4," +
                " [6, 5, 1, 8, 10, 3, 7, 9, 4, 2]=4, [4, 8, 5, 1, 9, 10, 3, 2, 7, 6]=4," +
                " [2, 4, 6, 8, 10, 1, 9, 3, 5, 7]=4, [3, 1, 5, 7, 4, 2, 10, 6, 9, 8]=4," +
                " [2, 10, 7, 6, 5, 9, 8, 3, 4, 1]=4, [7, 2, 10, 8, 9, 5, 4, 1, 6, 3]=4," +
                " [7, 10, 9, 5, 6, 1, 4, 8, 2, 3]=4, [10, 3, 8, 5, 6, 7, 9, 1, 4, 2]=4}",
                9.41189799992237
        );
        distinctListsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 10, 7], [3], [7, 1, 3], [1, 2], [4, 8, 2], [2], [2], [2], [8], [9, 15, 6, 12], [2], [1], [12]," +
                " [7, 4], [2, 6, 5, 1], [5], [1], [10], [2], [5], ...]",
                "{[1]=142762, [2]=104119, [3]=75181, [4]=55392, [5]=41192, [6]=30272, [7]=22836, [1, 2]=17068," +
                " [8]=16987, [2, 1]=16718}",
                1.7601419999815262
        );
        distinctListsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 4, 1, 3], [7, 8, 2, 3], [2, 1, 8], [9, 15, 6, 12], [2, 1, 13], [4, 7, 6, 2, 5, 1]," +
                " [1, 10, 3, 5], [4, 2, 5], [4, 5, 2], [2, 1, 4, 3, 6], [5, 1, 2], [6, 5, 4], [9, 7, 4, 1, 6]," +
                " [5, 1, 10, 2], [3, 2, 4], [7, 10, 3, 1, 4, 19, 5, 6, 2, 22], [2, 1, 3, 4, 7], [2, 1, 19]," +
                " [7, 3, 14], [20, 4, 1, 2], ...]",
                "{[1, 2, 3]=8420, [1, 3, 2]=7714, [2, 1, 3]=7625, [3, 1, 2]=6880, [2, 3, 1]=6446, [1, 2, 4]=6200," +
                " [3, 2, 1]=6165, [2, 1, 4]=5577, [1, 4, 2]=5273, [2, 4, 1]=4541}",
                3.989958999983499
        );
        distinctListsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "[[7, 8, 2, 3, 1, 5, 9, 15, 6, 12, 13, 4, 10], [1, 6, 2, 3, 5, 4, 9, 7]," +
                " [5, 1, 10, 2, 3, 4, 13, 7, 19, 6, 22], [2, 1, 3, 4, 7, 19, 14, 20, 5, 8, 13]," +
                " [4, 3, 1, 5, 2, 10, 8, 6], [4, 1, 3, 6, 15, 2, 5, 11], [1, 2, 7, 4, 6, 9, 3, 5, 10]," +
                " [3, 1, 2, 5, 13, 6, 8, 4, 10], [3, 8, 4, 1, 6, 2, 5, 15, 10, 7], [2, 1, 9, 3, 4, 5, 7, 6, 15]," +
                " [3, 4, 9, 8, 6, 1, 2, 5, 17, 15, 12], [2, 8, 4, 1, 3, 5, 10, 14, 7, 13, 6]," +
                " [2, 3, 5, 4, 15, 1, 6, 8, 7], [1, 2, 4, 3, 8, 7, 6, 10, 11, 5]," +
                " [2, 5, 3, 1, 6, 8, 4, 13, 10, 7, 9], [7, 3, 12, 6, 2, 16, 8, 1, 9]," +
                " [2, 3, 4, 5, 6, 1, 11, 7, 13, 14], [5, 4, 10, 8, 17, 1, 11, 2, 3, 7, 6]," +
                " [1, 5, 3, 4, 2, 16, 7, 8, 11, 9], [5, 12, 4, 2, 1, 9, 6, 8, 3, 10], ...]",
                "{[1, 3, 2, 5, 4, 6, 7, 8]=9, [4, 1, 2, 5, 3, 7, 6, 8]=8, [1, 2, 7, 3, 5, 4, 6, 8]=8," +
                " [1, 5, 2, 4, 3, 6, 7, 9]=8, [1, 3, 4, 2, 8, 6, 7, 5]=8, [1, 2, 4, 3, 5, 7, 6, 9]=8," +
                " [2, 1, 4, 6, 3, 5, 7, 8]=8, [1, 3, 2, 4, 6, 5, 7, 8]=8, [1, 2, 6, 4, 3, 5, 7, 8]=8" +
                ", [1, 3, 2, 4, 6, 5, 9, 7]=7}",
                10.378239999978224
        );
        distinctListsAtLeast_helper(
                2,
                1,
                repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]",
                "{[1]=1000000}",
                1.000000000007918
        );
        distinctListsAtLeast_fail_helper(5, 3, Collections.emptyList());
        distinctListsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        distinctListsAtLeast_fail_helper(5, 5, P.integers());
        distinctListsAtLeast_fail_helper(4, 5, P.integers());
    }

    private static void distinctStringsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStringsAtLeast(minSize)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStringsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).distinctStringsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStringsAtLeast_int_String() {
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}",
                1.000000000007918
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "[ba, bca, acb, c, cba, c, bc, b, c, ac, b, c, b, c, a, c, a, a, c, ba, ...]",
                "{b=200397, c=200001, a=199719, bc=50590, ac=49968, cb=49927, ca=49752, ba=49731, ab=49715," +
                " bac=16871}",
                1.50008299998526
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "[bac, cba, acb, cba, bac, cab, bac, bca, bca, acb, cba, bca, acb, acb, cab, cba, cba, cba, cab," +
                " abc, ...]",
                "{bca=167455, abc=167096, acb=166726, cab=166462, cba=166272, bac=165989}",
                2.9999999999775233
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "[bca, bca, abc, c, cb, b, abc, b, a, c, b, ab, b, b, b, b, a, b, a, a, ...]",
                "{b=333476, c=143102, a=143064, bc=66615, ba=66225, cb=57093, ab=56893, ac=23861, ca=23855," +
                " bca=16769}",
                1.466173999985577
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "[bca, cba, cba, bac, bca, abc, bca, bca, bca, cba, bca, abc, acb, bac, cba, bac, bac, acb, abc," +
                " abc, ...]",
                "{bac=249877, bca=249848, cba=167153, abc=167054, cab=83176, acb=82892}",
                2.9999999999775233
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "[sp, si, i, is, is, s, i, si, s, s, i, ip, iM, i, p, i, s, s, ps, s, ...]",
                "{i=222608, s=222220, p=100226, si=63759, is=63326, M=47546, sp=27944, ip=27659, pi=24950, ps=24913}",
                1.5401079999842737
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "[sip, sip, isM, iMp, psi, spi, sip, psiM, siM, isM, Msp, sip, ips, spi, Misp, psM, ipM, Mspi, sMpi," +
                " isM, ...]",
                "{sip=117359, isp=116982, spi=70509, ips=70019, pis=54744, psi=54548, siM=50854, isM=50725," +
                " sMi=25245, iMs=25152}",
                3.2285219999851744
        );
        distinctStringsAtLeast_int_String_fail_helper(5, 3, "");
        distinctStringsAtLeast_int_String_fail_helper(5, 5, "abc");
        distinctStringsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    @Test
    public void testDistinctStringsAtLeast_int() {
        distinctStringsAtLeast_int_helper(
                2,
                1,
                "[ε䊿\u2538\u31e5, \udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ, ᬜK㵏ꏹ, 㩷, 纫\ufe2d, 䝲, 坤琖\u2a43, \uea45, 蕤," +
                " \u2b63\uf637, 鸅, \uee1c\u33b2, ᅺ됽, 䇺, \ue9fd, 㖊, \uaaf0, 覚, 䱸, \u2e24, ...]",
                "{瓫=24, 簐=22, 듑=22, 瀯=21, \uf3e9=21, 䝞=21, 抷=20, 䒢=20, Ẑ=20, 텁=20}",
                1.9995569999798375
        );
        distinctStringsAtLeast_int_helper(
                5,
                3,
                "[\u31e5髽肣\uf6ffﳑ赧\ue215, \udd15몱ﲦ䯏, 刿ㄾ䲵箿偵, K㵏ꏹ缄, 坤琖\u2a43퉌\uea45\ue352蕤餥," +
                " \u2b63\uf637鸂, \u33b2酓캆ᅺ됽, \ue9fd\u2aec㖊짎\uaaf0, 覚돘䱸, \uf878ሮܓ鄒, 尩굿\uecf5ꪻ, \ue8b2빮빅," +
                " 瀵컦刓嗏\u3353\ue2d3\ud805, 䫯噋\uf36fꌻ躁\ue87c홃, 壙\udd82픫鼧\u061a\u2e94穨㽖ﶼ䥔, 糦嗮\uf329," +
                " ꯃ慚총\u0e77\uf36bB㽿\u2a57, \udec6ꅪ\udcc6, \u337d萋\u0d5b, 쪡詪쀝\u1366\uf21bᵠ, ...]",
                "{\u31e5髽肣\uf6ffﳑ赧\ue215=1, \udd15몱ﲦ䯏=1, 刿ㄾ䲵箿偵=1, K㵏ꏹ缄=1, 坤琖\u2a43퉌\uea45\ue352蕤餥=1," +
                " \u2b63\uf637鸂=1, \u33b2酓캆ᅺ됽=1, \ue9fd\u2aec㖊짎\uaaf0=1, 覚돘䱸=1, \uf878ሮܓ鄒=1}",
                5.00299199999616
        );
        distinctStringsAtLeast_int_helper(
                32,
                8,
                "[\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878" +
                "ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f㱉泦, 瀵컦刓嗏\u3353\ue2d3\ud805ឃ," +
                " 糦嗮\uf329ﻧ\udd42䞂鎿鐳鰫묆颒錹睸ꯃ慚총\u0e77\uf36bB㽿\u2a57緜\udec6ꅪ\udcc6\u0964\u337d萋\u0d5b詵\ud8ca" +
                "䍾徜쪡詪, 駆퉐庺\u2293\ued0d䴻ꎤ槔横, 䃼匀낛띆ﱓ㝏ᯖ\uea55\ue9c6儖䦣\u202c," +
                " ͺ\u124eꪪ\u0a49䠬㲜\ue852ډұ\ue28c葒ලȞ蛕䮼ხ叙繲\uefc8嶂췴䔾턞, \uab6e䝀㥑\u2e64년믱젯䁅偘滞\ue89bᖒ㿘燔ꎿ趵," +
                " 㙴ᶙ䁩聂ꃖꐲ\udc58\u26f2, 쳮陜阏顓\u2320쓎狙쟒㕯뚗\u32b0頵\u2606ꎚ궥婜쇻ᅒ댲, 旞\u2613\u19de\u0618戯韕똽\u25ad," +
                " 죴\u0d47㚏帇퀯\uebc7晸犋鈖ꤥ쿕\u0f1b\u2eea\uf800ᓑ濙䢗瞁퓰," +
                " 梏\u2684\ue40c\u2b83葆а팗풬궺쥶ꎠ뗢撻뵪\u21c0羾놂쒞沅," +
                " 헑䈏닁\ue649គ姕\u1069\u2f0d듂狚\ue672団䅁悲枧\u1b56偰摡泈\u1a60㭍\u2af8운\u2026桶뼄ቾᶝ睗㥐厖剹ᥔ㻶\uf3a8춮茞" +
                "\ue531칗ᳯ\u073d飰\ue480\ua6f4\u19b1\u1739箴\ued1a쀁\ua7af탰\u3243\u4df4\u2a33䨺\ud845㼰館㾸侒叵\uf351鳸" +
                "뾁냥ꯈ\u1aba呼\u0b8c䦼鳙柿쥇顆乃\ude93쁳\u0aa9蕕챲閦\ue10d嬵\uecdaꜢஆ挑쑹窀糘, 甍뮣民皑\u291e秳ʵ솄" +
                "퍆芦瀉벧\u2e50滎\u25a0爑\u1fce廏놿\u07aa\udde1頔臙ㆲ\uee84㢍䒉藟㗨詂ܒ嘟ᰁ謳䒁\u17cb\u0875멖\uecda㙿\ua837" +
                "稔뇐\uee3aۮ\uf6cd\ue22c\u2fbe톋艸操샣墺貗\u1c47\uf2ff, 䌚\ufe3d춢후Ꜯ卩鳰阘细\ue9d5\ude3a显鏌㓆갭\uda6cᎳ" +
                "\ua9f4쉙嘂\uf6a5䜐禎\u0529K쬋壵\ue61e쵕ᶑ\uf04f, ᬱ뭇昺玉뾂炣虹㨘, 씅潵겧\u0f24㺸則穣클䜜걓绡缂敉勪\ue498溯7" +
                "익Ᏺ㥥㖃ど츪퇢㴯ᚅ\u1deb齞杁鱼欎䌕렔횋葑䎌쯹笨\udd06\ude35鲦頒Ϯ懜焣担戎몔\ue47a串\u0c00\ue59b聾ﶯ, \u2153" +
                "\uf7cdꜰ耕詴茟\u0482쵕ꏠ\ud847\uef98쾭, 檌裤㻞椼憊ⴋ\u21ba\uec15檮滙\u0cec巶먛㺱\udbf4蠛玌疟ᒚⴓ\u2818\u2b5d" +
                "\ud85cⲄ뤀\u0361ꚸ璎祍忢, 좲햽퐗僮眏겴\uf5e2霺ⱊȢ\ue41d\u2f43뜓軷, ...]",
                "{\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03" +
                "띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f㱉泦=1, 瀵컦刓嗏\u3353\ue2d3\ud805ឃ=1," +
                " 糦嗮\uf329ﻧ\udd42䞂鎿鐳鰫묆颒錹睸ꯃ慚총\u0e77\uf36bB㽿\u2a57緜\udec6ꅪ\udcc6\u0964\u337d萋\u0d5b詵\ud8ca" +
                "䍾徜쪡詪=1, 駆퉐庺\u2293\ued0d䴻ꎤ槔横=1, 䃼匀낛띆ﱓ㝏ᯖ\uea55\ue9c6儖䦣\u202c=1," +
                " ͺ\u124eꪪ\u0a49䠬㲜\ue852ډұ\ue28c葒ලȞ蛕䮼ხ叙繲\uefc8嶂췴䔾턞=1," +
                " \uab6e䝀㥑\u2e64년믱젯䁅偘滞\ue89bᖒ㿘燔ꎿ趵=1, 㙴ᶙ䁩聂ꃖꐲ\udc58\u26f2=1," +
                " 쳮陜阏顓\u2320쓎狙쟒㕯뚗\u32b0頵\u2606ꎚ궥婜쇻ᅒ댲=1, 旞\u2613\u19de\u0618戯韕똽\u25ad=1}",
                31.99690200002153
        );
        distinctStringsAtLeast_int_fail_helper(5, 5);
        distinctStringsAtLeast_int_fail_helper(4, 5);
    }

    private static void stringBags_int_String_helper(
            int size,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringBags(size, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void stringBags_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.stringBags(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringBags_int_String() {
        stringBags_int_String_helper(
                0,
                "a",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringBags_int_String_helper(
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}"
        );
        stringBags_int_String_helper(
                2,
                "a",
                "[aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, ...]",
                "{aa=1000000}"
        );
        stringBags_int_String_helper(
                3,
                "a",
                "[aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa," +
                " aaa, ...]",
                "{aaa=1000000}"
        );
        stringBags_int_String_helper(
                0,
                "abc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringBags_int_String_helper(
                1,
                "abc",
                "[b, b, c, b, a, b, b, b, b, b, a, b, b, c, b, c, a, c, b, b, ...]",
                "{c=333615, b=333313, a=333072}"
        );
        stringBags_int_String_helper(
                2,
                "abc",
                "[bb, bc, ab, bb, bb, ab, bc, bc, ac, bb, ac, bc, ac, bc, bc, aa, ac, bc, ab, ac, ...]",
                "{bc=222712, ab=222100, ac=222096, aa=111121, cc=111028, bb=110943}"
        );
        stringBags_int_String_helper(
                3,
                "abc",
                "[bbc, abb, bbb, abb, bbc, acc, bbc, abc, abc, bcc, aac, abc, aab, bcc, acc, bcc, abc, acc, aac," +
                " aac, ...]",
                "{abc=222554, acc=111661, bbc=111270, bcc=110898, aab=110745, abb=110451, aac=110411, aaa=37441," +
                " bbb=37355, ccc=37214}"
        );
        stringBags_int_String_helper(
                0,
                "abbc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringBags_int_String_helper(
                1,
                "abbc",
                "[b, b, c, b, b, c, a, b, b, c, c, b, c, b, c, b, a, b, b, b, ...]",
                "{b=499640, c=250298, a=250062}"
        );
        stringBags_int_String_helper(
                2,
                "abbc",
                "[bb, bc, bc, ab, bc, bc, bc, bc, ab, bb, bc, ab, bc, bc, bb, ab, bc, ac, bc, bc, ...]",
                "{bb=250376, bc=249722, ab=249371, ac=125181, cc=62694, aa=62656}"
        );
        stringBags_int_String_helper(
                3,
                "abbc",
                "[bbc, bbc, abb, bcc, bcc, abb, bbc, abb, bbc, bbc, abc, abc, bcc, bbb, abc, abc, abb, aab, bcc," +
                " bbc, ...]",
                "{bbc=187506, abb=187492, abc=187127, bbb=125202, bcc=93885, aab=93511, aac=46960, acc=46889," +
                " ccc=15726, aaa=15702}"
        );
        stringBags_int_String_helper(
                0,
                "Mississippi",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringBags_int_String_helper(
                1,
                "Mississippi",
                "[p, p, s, s, s, p, s, s, i, i, s, s, s, p, s, i, s, i, s, s, ...]",
                "{s=363979, i=363703, p=181581, M=90737}"
        );
        stringBags_int_String_helper(
                2,
                "Mississippi",
                "[pp, ss, ps, ss, ii, ss, ps, is, is, ss, is, ip, is, is, ip, ss, ss, is, Ms, is, ...]",
                "{is=264865, ss=132606, ps=132537, ii=131960, ip=131662, Mi=66206, Ms=65705, pp=33071, Mp=33023" +
                ", MM=8365}"
        );
        stringBags_int_String_helper(
                3,
                "Mississippi",
                "[pps, pss, iss, iss, pss, iis, sss, iip, iss, iip, sss, iss, Mis, sss, iss, ips, Mip, ipp, ips," +
                " iss, ...]",
                "{iss=144520, ips=144458, iis=144124, pss=72346, iip=71867, Mis=71484, sss=48687, iii=48048," +
                " Mii=36461, Mip=36170}"
        );
        stringBags_int_String_fail_helper(1, "");
        stringBags_int_String_fail_helper(-1, "abc");
    }

    private static void stringBags_int_helper(int size, @NotNull String output, @NotNull String topSampleCount) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringBags(size)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void stringBags_int_fail_helper(int size) {
        try {
            P.stringBags(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringBags_int() {
        stringBags_int_helper(
                0,
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringBags_int_helper(
                1,
                "[嘩, 퇉, 馃, \u2df2, ε, 䊿, \u2538, \u31e5, 髽, 肣, \uf6ff, ﳑ, 赧, \ue215, \u17f3, \udd75, 껸, \udd15," +
                " 몱, ﲦ, ...]",
                "{\uf1b2=36, 撢=35, આ=34, 퉃=34, \27=33, 韖=32, 㖒=32, 膗=31, 㗞=31, 䕦=31}"
        );
        stringBags_int_helper(
                2,
                "[嘩퇉, \u2df2馃, ε䊿, \u2538\u31e5, 肣髽, \uf6ffﳑ, 赧\ue215, \u17f3\udd75, 껸\udd15, 몱ﲦ, ϡ䯏," +
                " \u19dc罖, ㄾ刿, 䲵箿, 偵恾, ᬜK, 㵏ꏹ, 㩷缄, ⴿ읾, 纫\ufe2d, ...]",
                "{틺\uf310=2, 緑\ue709=2, 㑰\uf5be=2, 啺횄=2, 菧\ue429=2, 㧣㻜=2, \u23c0\uf480=2, Կ첵=2, 뻀\udf8a=2," +
                " Ⳉ고=2}"
        );
        stringBags_int_helper(
                3,
                "[嘩馃퇉, ε\u2df2䊿, \u2538\u31e5髽, 肣\uf6ffﳑ, \u17f3赧\ue215, 껸\udd15\udd75, 䯏몱ﲦ, ϡ\u19dc罖, ㄾ䲵刿," +
                " 偵恾箿, ᬜK㵏, 㩷缄ꏹ, ⴿ纫읾, 㗂䝲\ufe2d, 갩힜\uf207, \u2a43坤琖, 퉌\ue352\uea45, 䉀蕤餥, \u2b63鸂\uf637," +
                " 误輮鸅, ...]",
                "{嘩馃퇉=1, ε\u2df2䊿=1, \u2538\u31e5髽=1, 肣\uf6ffﳑ=1, \u17f3赧\ue215=1, 껸\udd15\udd75=1, 䯏몱ﲦ=1," +
                " ϡ\u19dc罖=1, ㄾ䲵刿=1, 偵恾箿=1}"
        );
        stringBags_int_fail_helper(-1);
    }

    private static void bags_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).bags(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void bags_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        bags_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void bags_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).bags(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBags_Iterable() {
        bags_Iterable_helper_uniform(
                1,
                "[5]",
                "[[5, 5, 5], [5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5], [5, 5], [], [5], [5, 5, 5], [], []," +
                " [5, 5], [5, 5], [5, 5], [], [5, 5], [], [], [5], [5], [], ...]",
                "{[]=499125, [5]=250897, [5, 5]=124849, [5, 5, 5]=62518, [5, 5, 5, 5]=31407, [5, 5, 5, 5, 5]=15634," +
                " [5, 5, 5, 5, 5, 5]=7825, [5, 5, 5, 5, 5, 5, 5]=3926, [5, 5, 5, 5, 5, 5, 5, 5]=1896," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5]=956}",
                1.0008359999977228
        );
        bags_Iterable_helper_uniform(
                2,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5], [5, 5], [5, 5, 5, 5, 5], [], [5]," +
                " [5, 5], [5], [5, 5, 5], [5, 5, 5, 5, 5, 5], [5], [5, 5], [], [5], [], [5], [], [5, 5, 5], ...]",
                "{[]=333813, [5]=221150, [5, 5]=148025, [5, 5, 5]=98992, [5, 5, 5, 5]=66270, [5, 5, 5, 5, 5]=43747," +
                " [5, 5, 5, 5, 5, 5]=29389, [5, 5, 5, 5, 5, 5, 5]=19567, [5, 5, 5, 5, 5, 5, 5, 5]=12958," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5]=8571}",
                2.0020969999891216
        );
        bags_Iterable_helper_uniform(
                4,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5], [], [5], [], [5], [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [], [5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5], [5, 5, 5, 5], ...]",
                "{[]=200194, [5]=160489, [5, 5]=127708, [5, 5, 5]=101606, [5, 5, 5, 5]=82008, [5, 5, 5, 5, 5]=65900," +
                " [5, 5, 5, 5, 5, 5]=52157, [5, 5, 5, 5, 5, 5, 5]=41827, [5, 5, 5, 5, 5, 5, 5, 5]=33413," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5]=26877}",
                4.004359999991779
        );
        bags_Iterable_helper_uniform(
                1,
                "[1, 2, 3]",
                "[[1, 2, 2], [1, 2, 2, 2, 2, 3, 3, 3], [1, 3], [2, 3], [], [], [1, 3], [2], [], [], [3], [1, 3]," +
                " [3], [3], [], [3], [], [], [], [], ...]",
                "{[]=499504, [2]=83540, [3]=83439, [1]=83275, [2, 3]=27912, [1, 2]=27761, [1, 3]=27505," +
                " [1, 2, 3]=14040, [2, 2]=13964, [1, 1]=13961}",
                1.00085799999768
        );
        bags_Iterable_helper_uniform(
                2,
                "[1, 2, 3]",
                "[[2, 2, 2, 2], [2], [2, 2, 3, 3], [], [2, 3], [1, 1, 3], [1, 2], [3, 3, 3], [1, 2, 3], [1], [1]," +
                " [1], [1, 2, 2], [2, 2, 2, 2, 3, 3, 3], [], [2], [1, 1, 2, 3, 3, 3], [], [], [], ...]",
                "{[]=333247, [2]=74291, [3]=74037, [1]=73733, [1, 3]=32991, [1, 2]=32982, [2, 3]=32718," +
                " [1, 2, 3]=22002, [3, 3]=16556, [2, 2]=16374}",
                2.0023509999891522
        );
        bags_Iterable_helper_uniform(
                4,
                "[1, 2, 3]",
                "[[2, 2, 2, 2], [2], [1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3], [1], [3], [1, 2, 2, 3, 3]," +
                " [2, 3], [1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3], []," +
                " [1, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 3], [1, 3, 3], [1, 3, 3, 3, 3]," +
                " [1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3], [], [], [1, 1, 3, 3], [1], [1, 1, 1, 3], [], [1, 1, 3], ...]",
                "{[]=199912, [2]=53484, [1]=53481, [3]=53282, [2, 3]=28532, [1, 2]=28366, [1, 3]=28346," +
                " [1, 2, 3]=22526, [1, 1]=14328, [2, 2]=14257}",
                4.00516399999172
        );
        bags_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4]",
                "[[1, 2, 4], [1, 2, 2, 2, 3, 3, 3, 4, 4], [1, 3, 4], [4], [4], [3], [1, 3], [2], [], [], [4], [2]," +
                " [1], [], [3], [3], [], [3], [], [], ...]",
                "{[]=499557, [3]=62855, [1]=62711, [2]=62536, [4]=62330, [1, 4]=15745, [3, 4]=15706, [2, 3]=15607," +
                " [1, 2]=15576, [2, 4]=15495}",
                1.0006389999976706
        );
        bags_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4]",
                "[[2, 2, 4, 4], [2, 2, 3], [3, 4], [2, 3, 4], [], [1, 1, 2, 3, 4], [2], [], [3, 3, 4], [1, 2, 3, 3]," +
                " [], [], [1], [1], [2, 2, 4], [], [2, 2, 3, 4, 4, 4, 4], [4, 4, 4, 4], [4], [1, 1, 2, 3, 3, 4, 4]" +
                ", ...]",
                "{[]=333041, [1]=55655, [2]=55628, [4]=55498, [3]=55246, [3, 4]=18572, [1, 2]=18564, [2, 4]=18474," +
                " [1, 4]=18473, [2, 3]=18429}",
                2.0037019999891394
        );
        bags_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4]",
                "[[2, 2, 4, 4], [2, 2], [1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4], [1, 3], [], [], [3]," +
                " [1, 2, 2, 4, 4], [2, 3, 4], [1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4], [2, 3, 4, 4, 4]," +
                " [1, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4], [1], [], [1, 3, 3, 4], [1, 3, 3, 3, 3]," +
                " [1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4], [3], [], [1, 3, 3, 4], ...]",
                "{[]=200010, [2]=40047, [4]=39960, [3]=39955, [1]=39861, [3, 4]=16116, [1, 4]=16111, [2, 3]=16084," +
                " [2, 4]=16026, [1, 2]=15963}",
                4.00571499999147
        );
        bags_Iterable_helper_uniform(
                1,
                "[1, 2, 2, 4]",
                "[[1, 2, 4], [1, 2, 2, 2, 2, 2, 2, 4, 4], [1, 2, 4], [4], [4], [2], [1, 2], [2], [], [], [4], [2]," +
                " [1], [], [2], [2], [], [2], [], [], ...]",
                "{[]=499557, [2]=125391, [1]=62711, [4]=62330, [2, 2]=31328, [2, 4]=31201, [1, 2]=30980," +
                " [1, 4]=15745, [1, 2, 4]=11930, [1, 2, 2]=11853}",
                1.0006389999976706
        );
        bags_Iterable_helper_uniform(
                2,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4], [2, 2, 2], [2, 4], [2, 2, 4], [], [1, 1, 2, 2, 4], [2], [], [2, 2, 4], [1, 2, 2, 2]," +
                " [], [], [1], [1], [2, 2, 4], [], [2, 2, 2, 4, 4, 4, 4], [4, 4, 4, 4], [4], [1, 1, 2, 2, 2, 4, 4]" +
                ", ...]",
                "{[]=333041, [2]=110874, [1]=55655, [4]=55498, [2, 2]=37235, [2, 4]=37046, [1, 2]=36992," +
                " [1, 2, 4]=18649, [2, 2, 4]=18508, [1, 4]=18473}",
                2.0037019999891394
        );
        bags_Iterable_helper_uniform(
                4,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4], [2, 2], [1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4], [1, 2], [], [], [2]," +
                " [1, 2, 2, 4, 4], [2, 2, 4], [1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4], [2, 2, 4, 4, 4]," +
                " [1, 1, 1, 2, 2, 2, 2, 2, 4, 4, 4], [1], [], [1, 2, 2, 4], [1, 2, 2, 2, 2]," +
                " [1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4], [2], [], [1, 2, 2, 4], ...]",
                "{[]=200010, [2]=80002, [4]=39960, [1]=39861, [2, 4]=32142, [2, 2]=32002, [1, 2]=31843," +
                " [1, 2, 4]=19279, [1, 2, 2]=19212, [2, 2, 4]=18778}",
                4.00571499999147
        );
        bags_Iterable_helper_uniform(
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2], [2], [2], [2], [2, 2], [2], [], [], [2], [2]," +
                " [2], [], [2], [2], [], [2], [], [], ...]",
                "{[]=499557, [2]=250432, [2, 2]=124756, [2, 2, 2]=62825, [2, 2, 2, 2]=31144, [2, 2, 2, 2, 2]=15656," +
                " [2, 2, 2, 2, 2, 2]=7784, [2, 2, 2, 2, 2, 2, 2]=3987, [2, 2, 2, 2, 2, 2, 2, 2]=1945" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=945}",
                1.0006389999976706
        );
        bags_Iterable_helper_uniform(
                2,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2], [2, 2], [2, 2, 2], [], [2, 2, 2, 2, 2], [2], [], [2, 2, 2], [2, 2, 2, 2]," +
                " [], [], [2], [2], [2, 2, 2], [], [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2], [2, 2, 2, 2, 2, 2, 2]" +
                ", ...]",
                "{[]=333041, [2]=222027, [2, 2]=148088, [2, 2, 2]=98825, [2, 2, 2, 2]=65746, [2, 2, 2, 2, 2]=44116," +
                " [2, 2, 2, 2, 2, 2]=29303, [2, 2, 2, 2, 2, 2, 2]=19671, [2, 2, 2, 2, 2, 2, 2, 2]=13059" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=8625}",
                2.0037019999891394
        );
        bags_Iterable_helper_uniform(
                4,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2], [], [], [2]," +
                " [2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2], [], [2, 2, 2, 2], [2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2], [], [2, 2, 2, 2], ...]",
                "{[]=200010, [2]=159823, [2, 2]=128026, [2, 2, 2]=102068, [2, 2, 2, 2]=82001, [2, 2, 2, 2, 2]=65507," +
                " [2, 2, 2, 2, 2, 2]=52528, [2, 2, 2, 2, 2, 2, 2]=41779, [2, 2, 2, 2, 2, 2, 2, 2]=33653" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2]=26990}",
                4.00571499999147
        );
        bags_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 6, 9], [2, 2, 6, 6, 6, 7, 7, 10], [], [3, 3], [5, 7], [6], [], [], [4], [6], [3], [7], [], []," +
                " [1], [], [], [], [], [], ...]",
                "{[]=500030, [10]=25344, [1]=25129, [4]=25128, [7]=25106, [2]=25093, [6]=25064, [8]=24996," +
                " [3]=24945, [5]=24916}",
                0.998919999997707
        );
        bags_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 6, 8], [2, 6], [6, 6, 10], [], [3, 4, 5, 6, 7], [], [4], [3, 4, 7], [1, 9, 9, 9], [9]," +
                " [3, 6, 10], [4, 4, 4, 6, 6, 7], [2, 2, 4, 8], [3, 5, 7, 8, 9, 10], [], [], []," +
                " [3, 4, 5, 6, 7, 7, 8, 8], [2, 7], [], ...]",
                "{[]=333018, [7]=22348, [10]=22330, [6]=22322, [2]=22312, [4]=22212, [1]=22163, [3]=22146," +
                " [5]=22122, [8]=22019}",
                2.003595999989077
        );
        bags_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 6, 8], [6], [1, 3, 3, 3, 4, 4, 4, 5, 6, 7, 7, 8, 9, 9, 9, 9, 9], [3, 6, 10], [6, 7]," +
                " [1, 3, 3, 3, 3, 4, 5, 5, 7, 7, 7, 8, 8, 9, 10, 10, 10], []," +
                " [1, 1, 1, 3, 4, 4, 5, 5, 6, 7, 7, 7, 7, 8], [3, 8], [1, 3, 7, 10, 10], [2, 2, 3, 3, 5, 10], [1]," +
                " [3, 5, 6, 9], [5], [1, 3, 8, 9], [], [], [1, 1, 4], [6, 7], [2, 4, 4, 6, 9], ...]",
                "{[]=200177, [8]=16184, [10]=16147, [6]=16096, [1]=16095, [9]=16049, [5]=16043, [7]=15982," +
                " [2]=15958, [3]=15950}",
                4.002965999991581
        );
        bags_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 7, 10], [5], [], [1, 10], [], [3], [7], [], [5], [3], [], [], [], [], [], [], [], [], [5]," +
                " [6, 9, 12, 15], ...]",
                "{[]=499603, [1]=62583, [2]=46821, [3]=35045, [4]=26163, [5]=20032, [6]=14805, [1, 2]=11972," +
                " [7]=11067, [1, 3]=8779}",
                1.0012699999976906
        );
        bags_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "[[4, 7, 7, 10], [1, 2, 3, 3, 7], [1, 1, 2, 2, 2, 3], [], [5, 9, 15], [12], [1, 1, 2], []," +
                " [1, 2, 4, 5, 6, 6, 7, 9], [], [], [], [1, 1, 1, 3, 5], [2], [1, 4, 4, 5], [12], [2], [], [1, 4]," +
                " [1], ...]",
                "{[]=333149, [1]=55129, [2]=41612, [3]=31155, [4]=23246, [5]=17762, [1, 2]=14040, [6]=13192," +
                " [1, 3]=10373, [7]=9863}",
                2.001994999989098
        );
        bags_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "[[4, 7, 7, 10], [1, 1, 1, 2, 2, 2, 3, 7, 8, 8], [1, 2, 6, 6, 12, 15], [13]," +
                " [1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 5, 6, 10], [], [1, 1, 1, 1, 2, 2, 2, 2, 3, 4, 4, 6, 12], [], []," +
                " [], [1, 2, 3], [4, 5], [9], [1, 4, 5, 6, 7], [], [1, 1, 1, 2, 2, 3, 3], [7, 10, 13], [4, 19]," +
                " [1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 17, 22], [], ...]",
                "{[]=199867, [1]=39597, [2]=30067, [3]=22409, [4]=16944, [5]=12477, [1, 2]=12007, [6]=9434," +
                " [1, 3]=8793, [1, 1]=8183}",
                4.0083209999916205
        );
        bags_Iterable_helper(
                1,
                repeat(1),
                "[[1, 1, 1], [1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1], [1, 1], [], [1], [1, 1, 1], [], []," +
                " [1, 1], [1, 1], [1, 1], [], [1, 1], [], [], [1], [1], [], ...]",
                "{[]=499125, [1]=250897, [1, 1]=124849, [1, 1, 1]=62518, [1, 1, 1, 1]=31407, [1, 1, 1, 1, 1]=15634," +
                " [1, 1, 1, 1, 1, 1]=7825, [1, 1, 1, 1, 1, 1, 1]=3926, [1, 1, 1, 1, 1, 1, 1, 1]=1896" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=956}",
                1.0008359999977228
        );
        bags_Iterable_helper(
                2,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1], [1, 1], [1, 1, 1, 1, 1], [], [1]," +
                " [1, 1], [1], [1, 1, 1], [1, 1, 1, 1, 1, 1], [1], [1, 1], [], [1], [], [1], [], [1, 1, 1], ...]",
                "{[]=333813, [1]=221150, [1, 1]=148025, [1, 1, 1]=98992, [1, 1, 1, 1]=66270, [1, 1, 1, 1, 1]=43747," +
                " [1, 1, 1, 1, 1, 1]=29389, [1, 1, 1, 1, 1, 1, 1]=19567, [1, 1, 1, 1, 1, 1, 1, 1]=12958" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=8571}",
                2.0020969999891216
        );
        bags_Iterable_helper(
                4,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1], [], [1], [], [1], [1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [], [1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1], [1, 1, 1, 1], ...]",
                "{[]=200194, [1]=160489, [1, 1]=127708, [1, 1, 1]=101606, [1, 1, 1, 1]=82008, [1, 1, 1, 1, 1]=65900," +
                " [1, 1, 1, 1, 1, 1]=52157, [1, 1, 1, 1, 1, 1, 1]=41827, [1, 1, 1, 1, 1, 1, 1, 1]=33413" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1]=26877}",
                4.004359999991779
        );
        bags_Iterable_fail_helper(1, Collections.emptyList());
        bags_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        bags_Iterable_fail_helper(0, P.integers());
        bags_Iterable_fail_helper(-1, P.integers());
        bags_Iterable_fail_helper(1, Arrays.asList(1, null, 3));
        bags_Iterable_fail_helper(1, Collections.singleton(null));
    }

    private static void stringBags_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBags(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBags_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringBags(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBags_String() {
        stringBags_String_helper(
                1,
                "a",
                "[aaa, aa, aaaaaaaaa, aa, aa, , a, aaa, , , aa, aa, aa, , aa, , , a, a, , ...]",
                "{=499125, a=250897, aa=124849, aaa=62518, aaaa=31407, aaaaa=15634, aaaaaa=7825, aaaaaaa=3926," +
                " aaaaaaaa=1896, aaaaaaaaa=956}",
                1.0008359999977228
        );
        stringBags_String_helper(
                2,
                "a",
                "[aaaa, aaaaa, aaaaa, aaaa, aa, aaaaa, , a, aa, a, aaa, aaaaaa, a, aa, , a, , a, , aaa, ...]",
                "{=333813, a=221150, aa=148025, aaa=98992, aaaa=66270, aaaaa=43747, aaaaaa=29389, aaaaaaa=19567," +
                " aaaaaaaa=12958, aaaaaaaaa=8571}",
                2.0020969999891216
        );
        stringBags_String_helper(
                4,
                "a",
                "[aaaa, aaa, aaaaaaaaaaaaaaaaa, aaaaaaa, aa, , a, , a, aaaaa, aaaaaa, aaaaaaaaaaaaaaaaa, aaaaa, aa," +
                " aaaaaaa, aaaaaaaaaaaaaa, , aaaaaaaa, a, aaaa, ...]",
                "{=200194, a=160489, aa=127708, aaa=101606, aaaa=82008, aaaaa=65900, aaaaaa=52157, aaaaaaa=41827," +
                " aaaaaaaa=33413, aaaaaaaaa=26877}",
                4.004359999991779
        );
        stringBags_String_helper(
                1,
                "abc",
                "[abb, abbbbccc, ac, bc, , , ac, b, , , c, ac, c, c, , c, , , , , ...]",
                "{=499504, b=83540, c=83439, a=83275, bc=27912, ab=27761, ac=27505, abc=14040, bb=13964, aa=13961}",
                1.00085799999768
        );
        stringBags_String_helper(
                2,
                "abc",
                "[bbbb, b, bbcc, , bc, aac, ab, ccc, abc, a, a, a, abb, bbbbccc, , b, aabccc, , , , ...]",
                "{=333247, b=74291, c=74037, a=73733, ac=32991, ab=32982, bc=32718, abc=22002, cc=16556, bb=16374}",
                2.0023509999891522
        );
        stringBags_String_helper(
                4,
                "abc",
                "[bbbb, b, aaaaabbbccccccccc, a, c, abbcc, bc, aaaaabbbccccccccc, , aaaaaaabbccccc, acc, acccc," +
                " abbbbbccccc, , , aacc, a, aaac, , aac, ...]",
                "{=199912, b=53484, a=53481, c=53282, bc=28532, ab=28366, ac=28346, abc=22526, aa=14328, bb=14257}",
                4.00516399999172
        );
        stringBags_String_helper(
                1,
                "abbc",
                "[abc, abbbbbbcc, abc, c, c, b, ab, b, , , c, b, a, , b, b, , b, , , ...]",
                "{=499557, b=125391, a=62711, c=62330, bb=31328, bc=31201, ab=30980, ac=15745, abc=11930, abb=11853}",
                1.0006389999976706
        );
        stringBags_String_helper(
                2,
                "abbc",
                "[bbcc, bbb, bc, bbc, , aabbc, b, , bbc, abbb, , , a, a, bbc, , bbbcccc, cccc, c, aabbbcc, ...]",
                "{=333041, b=110874, a=55655, c=55498, bb=37235, bc=37046, ab=36992, abc=18649, bbc=18508, ac=18473}",
                2.0037019999891394
        );
        stringBags_String_helper(
                4,
                "abbc",
                "[bbcc, bb, aabbbbbbbbbbcccc, ab, , , b, abbcc, bbc, aaaaabbbbbbbbccc, bbccc, aaabbbbbccc, a, ," +
                " abbc, abbbb, abbbbbbbbcc, b, , abbc, ...]",
                "{=200010, b=80002, c=39960, a=39861, bc=32142, bb=32002, ab=31843, abc=19279, abb=19212, bbc=18778}",
                4.00571499999147
        );
        stringBags_String_helper(
                1,
                "Mississippi",
                "[pss, iiisssss, is, , is, s, , , s, s, s, i, i, , i, , , , , , ...]",
                "{=499907, s=91141, i=91078, p=45481, is=33073, M=22586, ip=16660, ss=16500, ii=16475, ps=16363}",
                0.9996679999977037
        );
        stringBags_String_helper(
                2,
                "Mississippi",
                "[iiss, is, iss, i, issss, , s, sss, Miip, , , p, pss, ssssss, iiis, iiipps, i, , iiiissss, , ...]",
                "{=333528, s=80737, i=80484, p=40277, is=39160, M=20228, ss=19746, ip=19599, ii=19556, ps=19483}",
                2.0026269999890762
        );
        stringBags_String_helper(
                4,
                "Mississippi",
                "[iiss, s, Miiiiippppsssssss, s, psss, ss, Miiiiipppssssssss, s, MMMiiiiisssss, is, Miips," +
                " Miiiippsss, , ipss, i, Mips, , , MMs, ss, ...]",
                "{=199852, s=58261, i=58255, is=33994, p=29278, ss=16925, ip=16763, ii=16678, ps=16630, iis=14783}",
                4.0051349999917525
        );
        stringBags_String_fail_helper(1, "");
        stringBags_String_fail_helper(0, "abc");
        stringBags_String_fail_helper(-1, "abc");
    }

    private static void stringBags_helper(
            int scale,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBags()));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBags_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).stringBags());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBags() {
        stringBags_helper(
                1,
                "[ε\u2538䊿, ϡ\u19dcㄾ䯏刿罖몱\udd15ﲦ, ᬜK㵏, 㩷, 纫, 䝲, 坤琖, \uea45, , , \u2b63, 鸅, \uee1c, , ᅺ, 䇺," +
                " , 㖊, , , ...]",
                "{=499557, \uf59a=15, 僵=14, \u12c7=14, 瘍=14, \ue0de=14, \ua838=13, 䃢=13, 喽=13, 瓫=13}",
                1.0006389999976706
        );
        stringBags_helper(
                2,
                "[\u31e5肣髽\uf6ff, 몱\udd15ﲦ, ㄾ刿, K㵏ꏹ, , \u2a43坤琖퉌\uea45, 餥, , \u33b2酓캆, \u2aec㖊짎\ue9fd, , ," +
                " 䱸, \uf878, 尩굿\uecf5, , \u3353刓嗏瀵컦\ud805\ue2d3, 䫯噋ꌻ\uf36f, 홃, \u061a\u2e94壙穨鼧픫\udd82, ...]",
                "{=333041, 趤=15, 挗=13, \u2fae=13, 阤=12, \u0978=12, \ue2fe=12, \uab10=12, 䖸=12, \ue973=12}",
                2.0037019999891394
        );
        stringBags_helper(
                4,
                "[\u31e5肣髽\uf6ff, 몱\udd15, ᅺᤘ\u2b63\u33b2䇺煖误輮酓鸂鸅됽캆\ue9fd\uee1c\uf637, 全覚, , , ሮ," +
                " 尩疜ꪻ굿\uecf5, 刓瀵컦, \u061a\u2e94㽖䥔嗮壙穨糦鼧픫핀\udd42\udd82\uf329ﶼﻧ, \u0e77慚ꯃ총\uf36b," +
                " ढ\u2293䴻庺槔横靯駆ꎤ퉐\ued0d, 䃼, , ᑒ拷만\ue68e, ͺ\u124e\u2506囀ꪪ, 䔾唯嶂湑猂甧蹙췴턞\uead1ﮍ, \uab6e, ," +
                " ᖒ㿘滞\ue89b, ...]",
                "{=200010, \ued08=11, 듏=11, \ua495=11, 幱=10, 㚼=10, Ꙛ=10, 홣=10, ﺆ=10, \ua494=10}",
                4.00571499999147
        );
        stringBags_fail_helper(0);
        stringBags_fail_helper(-1);
    }

    private static void bagsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).bagsAtLeast(minSize, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void bagsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        bagsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void bagsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).bagsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBagsAtLeast() {
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[5]",
                "[[5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5], [5, 5, 5], [5], [5, 5]," +
                " [5, 5, 5, 5], [5], [5], [5, 5, 5], [5, 5, 5], [5, 5, 5], [5], [5, 5, 5], [5], [5], [5, 5], [5, 5]," +
                " [5], ...]",
                "{[5]=499125, [5, 5]=250897, [5, 5, 5]=124849, [5, 5, 5, 5]=62518, [5, 5, 5, 5, 5]=31407," +
                " [5, 5, 5, 5, 5, 5]=15634, [5, 5, 5, 5, 5, 5, 5]=7825, [5, 5, 5, 5, 5, 5, 5, 5]=3926," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5]=1896, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=956}",
                2.0008359999800347
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[5]",
                "[[5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5], [5, 5, 5, 5, 5], [5, 5, 5]," +
                " [5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5], [5, 5, 5], [5, 5, 5, 5, 5, 5], ...]",
                "{[5, 5, 5]=333813, [5, 5, 5, 5]=221150, [5, 5, 5, 5, 5]=148025, [5, 5, 5, 5, 5, 5]=98992," +
                " [5, 5, 5, 5, 5, 5, 5]=66270, [5, 5, 5, 5, 5, 5, 5, 5]=43747, [5, 5, 5, 5, 5, 5, 5, 5, 5]=29389," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=19567, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=12958" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=8571}",
                5.002096999996331
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[5]",
                "[[5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, " +
                "5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5," +
                " 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5], ...]",
                "{[5, 5, 5, 5, 5, 5, 5, 5]=39940, [5, 5, 5, 5, 5, 5, 5, 5, 5]=38196," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=36988, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=35334," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=33831, [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=32551," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=31521," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=30149," +
                " [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=28763" +
                ", [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5]=27543}",
                32.00360900002322
        );
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3]",
                "[[1, 2, 2, 2], [1, 2, 2, 2, 2, 3, 3, 3], [1, 2, 3], [3], [1, 2, 3], [3], [2, 3], [2], [3]," +
                " [1, 3, 3], [2], [3], [2], [3], [1], [3], [1], [1], [3], [1, 2], ...]",
                "{[2]=167156, [3]=166869, [1]=166251, [2, 3]=56002, [1, 3]=55604, [1, 2]=55313, [1, 2, 3]=27815," +
                " [2, 2]=27782, [1, 1]=27761, [3, 3]=27621}",
                1.9993039999798474
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3]",
                "[[1, 2, 2, 2, 2, 2, 2], [1, 2, 2, 2, 3, 3, 3], [2, 3, 3], [1, 1, 2, 3, 3], [1, 2, 3]," +
                " [2, 3, 3, 3, 3], [1, 2, 3, 3], [1, 1, 3], [1, 1, 3], [1, 2, 2, 2, 3, 3], [2, 2, 2, 2, 3, 3, 3]," +
                " [1, 2, 2], [1, 1, 1, 1, 1, 2, 3, 3, 3], [1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3], [2, 2, 3, 3]," +
                " [1, 3, 3], [1, 2, 3, 3], [1, 1, 1], [1, 1, 1, 1, 3, 3, 3, 3], [1, 2, 3], ...]",
                "{[1, 2, 3]=74121, [2, 3, 3]=37460, [1, 3, 3]=37281, [1, 1, 2]=36973, [1, 2, 2]=36882," +
                " [2, 2, 3]=36847, [1, 1, 3]=36676, [1, 2, 2, 3]=32994, [1, 2, 3, 3]=32874, [1, 1, 2, 3]=32719}",
                5.003739999996368
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3]",
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 2, 3, 3, 3, 3], [1, 1, 1, 2, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3], [1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3," +
                " 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, " +
                "3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3], [2, 2, 2, 2, 2, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 2, 2, 2, 2, 3, 3], ...]",
                "{[1, 1, 2, 2, 2, 3, 3, 3]=3539, [1, 1, 1, 2, 2, 3, 3, 3]=3380, [1, 1, 1, 2, 2, 2, 3, 3]=3372," +
                " [1, 1, 1, 2, 2, 2, 3, 3, 3]=3265, [1, 1, 1, 2, 2, 2, 2, 3, 3, 3]=2723," +
                " [1, 1, 1, 1, 2, 2, 2, 3, 3, 3]=2651, [1, 1, 2, 2, 3, 3, 3, 3]=2635," +
                " [1, 1, 1, 2, 2, 2, 3, 3, 3, 3]=2633, [1, 1, 2, 2, 2, 2, 3, 3]=2615, [1, 1, 1, 1, 2, 2, 3, 3]=2536}",
                32.010685000021894
        );
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4]",
                "[[1, 2, 2, 4], [1, 2, 2, 2, 3, 3, 3, 4, 4], [1, 2, 3, 4], [4], [2, 4], [3], [1, 3, 4], [2], [1]," +
                " [4, 4], [2], [1, 3], [2, 3], [3], [2], [3], [1], [3], [1], [1], ...]",
                "{[4]=125444, [2]=125303, [1]=125036, [3]=124486, [2, 3]=31374, [2, 4]=31349, [1, 3]=31275," +
                " [1, 4]=31260, [3, 4]=31175, [1, 2]=30662}",
                1.999585999979838
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4]",
                "[[2, 2, 2, 2, 4, 4, 4], [2, 2, 3, 4], [2, 2, 3, 4, 4], [1, 2, 3, 4], [1, 1, 1, 2, 2, 3, 3, 4]," +
                " [3, 4, 4], [2, 3, 3, 3, 4], [1, 1, 2, 3, 3], [1, 1, 3], [1, 3, 3, 4], [2, 2, 4, 4], [2, 3, 3]," +
                " [2, 2, 3, 4, 4, 4, 4], [1, 2, 4, 4, 4, 4, 4], [1, 1, 1, 1, 2, 3, 3, 3, 4, 4], [2, 3, 3]," +
                " [2, 3, 3, 4, 4, 4, 4, 4], [3, 3, 3], [2, 4, 4], [1, 2, 2, 3, 3, 4], ...]",
                "{[1, 2, 4]=31308, [1, 3, 4]=31190, [1, 2, 3]=30995, [2, 3, 4]=30994, [1, 2, 3, 4]=20848," +
                " [1, 4, 4]=15726, [3, 3, 4]=15702, [2, 4, 4]=15692, [1, 1, 3]=15654, [1, 1, 2]=15643}",
                5.00315899999616
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4]",
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4], [2, 2, 3, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, " +
                "4, 4, 4], [1, 1, 1, 2, 3, 3, 3, 4, 4], [1, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4], [1, 1, 2, 2, 3, 3, 3, 3]," +
                " [1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4], [1, 2, 2, 2, 3, 3, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3," +
                " 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4]," +
                " [1, 2, 2, 2, 3, 3, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 3, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4]," +
                " [1, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4], ...]",
                "{[1, 1, 2, 2, 3, 3, 4, 4]=1551, [1, 1, 2, 2, 3, 3, 3, 4, 4]=1154, [1, 1, 1, 2, 2, 3, 3, 4, 4]=1113," +
                " [1, 1, 2, 2, 3, 3, 4, 4, 4]=1111, [1, 1, 2, 2, 2, 3, 3, 4, 4]=1091, [1, 1, 2, 2, 3, 4, 4, 4]=1078," +
                " [1, 1, 1, 2, 2, 3, 4, 4]=1057, [1, 1, 2, 3, 3, 4, 4, 4]=1056, [1, 1, 2, 2, 2, 3, 3, 4]=1050" +
                ", [1, 1, 2, 2, 2, 3, 4, 4]=1029}",
                32.008717000021356
        );
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 2, 4]",
                "[[1, 2, 2, 4], [1, 2, 2, 2, 2, 2, 2, 4, 4], [1, 2, 2, 4], [4], [2, 4], [2], [1, 2, 4], [2], [1]," +
                " [4, 4], [2], [1, 2], [2, 2], [2], [2], [2], [1], [2], [1], [1], ...]",
                "{[2]=249789, [4]=125444, [1]=125036, [2, 2]=62887, [2, 4]=62524, [1, 2]=61937, [1, 4]=31260," +
                " [1, 2, 4]=23497, [1, 2, 2]=23397, [2, 2, 4]=23350}",
                1.999585999979838
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 2, 4]",
                "[[2, 2, 2, 2, 4, 4, 4], [2, 2, 2, 4], [2, 2, 2, 4, 4], [1, 2, 2, 4], [1, 1, 1, 2, 2, 2, 2, 4]," +
                " [2, 4, 4], [2, 2, 2, 2, 4], [1, 1, 2, 2, 2], [1, 1, 2], [1, 2, 2, 4], [2, 2, 4, 4], [2, 2, 2]," +
                " [2, 2, 2, 4, 4, 4, 4], [1, 2, 4, 4, 4, 4, 4], [1, 1, 1, 1, 2, 2, 2, 2, 4, 4], [2, 2, 2]," +
                " [2, 2, 2, 4, 4, 4, 4, 4], [2, 2, 2], [2, 4, 4], [1, 2, 2, 2, 2, 4], ...]",
                "{[1, 2, 4]=62498, [2, 2, 4]=62261, [1, 2, 2]=62070, [1, 2, 2, 4]=41699, [2, 2, 2]=41560," +
                " [2, 4, 4]=31305, [1, 1, 2]=31297, [2, 2, 2, 4]=28057, [1, 2, 2, 2]=27827, [1, 2, 2, 2, 4]=23400}",
                5.00315899999616
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 2, 4]",
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4], [2, 2, 2, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, " +
                "4, 4, 4], [1, 1, 1, 2, 2, 2, 2, 4, 4], [1, 1, 1, 2, 2, 2, 2, 2, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4], [1, 1, 2, 2, 2, 2, 2, 2]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4], [1, 2, 2, 2, 2, 2, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4]," +
                " [1, 2, 2, 2, 2, 2, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 4, 4, 4]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4]," +
                " [1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4], ...]",
                "{[1, 1, 2, 2, 2, 2, 4, 4]=4084, [1, 1, 2, 2, 2, 2, 2, 4, 4]=3541, [1, 1, 2, 2, 2, 2, 2, 4]=3276," +
                " [1, 2, 2, 2, 2, 2, 4, 4]=3271, [1, 1, 2, 2, 2, 2, 4, 4, 4]=2956," +
                " [1, 1, 2, 2, 2, 2, 2, 2, 4, 4]=2929, [1, 1, 1, 2, 2, 2, 2, 4, 4]=2912," +
                " [1, 1, 1, 2, 2, 2, 2, 2, 4, 4]=2878, [1, 1, 2, 2, 2, 2, 2, 4, 4, 4]=2789" +
                ", [1, 1, 2, 2, 2, 4, 4, 4]=2777}",
                32.008717000021356
        );
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2], [2, 2], [2], [2, 2, 2], [2], [2]," +
                " [2, 2], [2], [2, 2], [2, 2], [2], [2], [2], [2], [2], [2], [2], ...]",
                "{[2]=500269, [2, 2]=249809, [2, 2, 2]=124830, [2, 2, 2, 2]=62682, [2, 2, 2, 2, 2]=31195," +
                " [2, 2, 2, 2, 2, 2]=15549, [2, 2, 2, 2, 2, 2, 2]=7831, [2, 2, 2, 2, 2, 2, 2, 2]=3938," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2]=2013, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=951}",
                1.999585999979838
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2], [2, 2, 2], [2, 2, 2, 2, 2, 2], ...]",
                "{[2, 2, 2]=332475, [2, 2, 2, 2]=222950, [2, 2, 2, 2, 2]=148435, [2, 2, 2, 2, 2, 2]=98386," +
                " [2, 2, 2, 2, 2, 2, 2]=65648, [2, 2, 2, 2, 2, 2, 2, 2]=43847, [2, 2, 2, 2, 2, 2, 2, 2, 2]=29430," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=19567, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=13014" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=8771}",
                5.00315899999616
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2," +
                " 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2, 2, 2, 2, 2]=40181, [2, 2, 2, 2, 2, 2, 2, 2, 2]=38543," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=37070, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=35343," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=33943, [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=32305," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=31206," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=29856," +
                " [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=28774" +
                ", [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2]=27718}",
                32.008717000021356
        );
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[4, 6, 6, 9], [2, 2, 6, 6, 6, 7, 7], [10], [8], [3, 3], [4, 5, 7], [6], [5], [4, 8], [6], [3, 4]," +
                " [7], [9], [1, 9], [9], [5], [4], [4], [3, 6, 6, 10], [7], ...]",
                "{[6]=50479, [5]=50284, [2]=50162, [4]=50062, [3]=50049, [10]=50015, [9]=49998, [1]=49982," +
                " [8]=49890, [7]=49879}",
                1.9987289999797695
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 4, 6, 6, 6, 8], [2, 6, 7], [5, 6, 6, 8, 10], [1, 3, 4, 5, 5, 6, 6, 7], [1, 3, 4, 7, 7, 9]," +
                " [5, 9, 9], [3, 4, 4], [3, 6, 6, 10], [4, 4, 4, 4, 6, 6, 7], [2, 2, 3, 4, 7, 8, 8]," +
                " [3, 5, 5, 7, 8, 9, 10], [7, 8, 10], [3, 4, 5, 6, 7, 7, 8, 8], [1, 1, 2, 7, 7], [1, 1, 3, 5, 8]," +
                " [3, 4, 4, 5, 7, 7, 8, 9], [1, 3, 7, 10], [3, 3, 6, 9, 10], [2, 2, 3, 10], [1, 2, 2, 5, 5], ...]",
                "{[3, 7, 8]=2107, [7, 8, 9]=2091, [2, 8, 9]=2087, [4, 5, 10]=2083, [2, 4, 6]=2073, [2, 3, 9]=2072," +
                " [3, 4, 9]=2069, [7, 9, 10]=2065, [1, 6, 7]=2063, [3, 6, 8]=2063}",
                5.002305999996172
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, " +
                "7, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10]," +
                " [2, 3, 3, 3, 3, 4, 4, 5, 6, 7, 7, 7, 7, 8, 8, 8, 9, 10, 10], [1, 1, 1, 3, 4, 5, 5, 7, 7, 8]," +
                " [1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 6, 6, 6, 7, 8, 9, 9, 9, 10, 10, 10, 10, 10" +
                "], [1, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 7, 8, 8, 9, 9, 10, 10]," +
                " [1, 1, 1, 2, 2, 2, 3, 4, 5, 5, 7, 8, 8, 8, 9, 9, 10, 10], [1, 1, 2, 3, 3, 4, 6, 6, 7, 8, 10]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, " +
                "4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8," +
                " 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10]," +
                " [1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, " +
                "9, 9, 9, 9, 10, 10, 10, 10]," +
                " [1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 6, 6, 6, 7, 7, 8, 9, 10, 10, 10, 10, 10]," +
                " [1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, " +
                "6, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10]," +
                " [1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 7, 8, 9, 9, 10]," +
                " [1, 1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 8, 8]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, " +
                "6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10]," +
                " [1, 3, 4, 4, 5, 6, 6, 7, 7, 8, 10, 10]," +
                " [1, 2, 3, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 9, 10, 10, 10, 10, 10]," +
                " [1, 1, 2, 2, 2, 2, 2, 2, 2, 4, 4, 5, 7, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 10, 10]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7," +
                " 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10" +
                ", 10, 10, 10], [1, 1, 1, 3, 4, 4, 4, 5, 5, 5, 8, 8, 8, 8, 9, 9, 9, 10]," +
                " [1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9, 10], ...]",
                "{[1, 2, 3, 6, 7, 8, 9, 10]=25, [1, 2, 3, 4, 6, 7, 9, 10]=25, [2, 3, 4, 5, 6, 7, 9, 10]=25," +
                " [1, 2, 3, 4, 6, 8, 9, 10]=23, [1, 3, 4, 5, 6, 7, 9, 10]=23, [2, 3, 4, 6, 7, 8, 9, 10]=21," +
                " [1, 3, 4, 5, 7, 8, 9, 10]=21, [1, 2, 4, 5, 6, 7, 8, 9, 10]=20, [1, 2, 3, 4, 5, 6, 8, 9, 10]=20" +
                ", [1, 2, 4, 5, 6, 7, 9, 10]=20}",
                31.997066000022638
        );
        bagsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 7, 7, 10], [3], [1, 3, 7], [1, 2], [2, 4, 8], [2], [2], [2], [8], [6, 6, 9, 12, 15], [2], [1]," +
                " [12], [4, 7], [1, 2, 2, 5, 6], [5], [1], [10], [2], [5], ...]",
                "{[1]=124902, [2]=94480, [3]=69971, [4]=52478, [5]=39569, [6]=29407, [1, 2]=23426, [7]=22369," +
                " [1, 3]=17365, [8]=16718}",
                2.001126999979881
        );
        bagsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "[[1, 3, 4, 7, 7, 10, 10], [2, 2, 3, 7, 8], [1, 2, 2], [5, 8, 9], [1, 1, 1, 2, 4, 6, 6, 9, 12, 13]," +
                " [1, 1, 1, 2, 5, 6, 6], [1, 3, 10], [1, 1, 2, 4, 5], [4, 4, 5], [1, 2, 2, 12], [1, 1, 1, 3, 4]," +
                " [1, 1, 1, 2, 2, 3, 3, 5], [4, 4, 4, 5, 7, 9], [5, 6, 7], [1, 1, 10], [1, 2, 3], [3, 4, 13]," +
                " [1, 2, 3, 4, 5, 6, 7, 10, 19], [1, 17, 22], [1, 1, 2, 3], ...]",
                "{[1, 2, 3]=13093, [1, 1, 2]=11651, [1, 2, 4]=9743, [1, 1, 3]=8705, [1, 2, 2]=8503, [1, 2, 5]=7458," +
                " [1, 3, 4]=7296, [1, 1, 4]=6657, [1, 3, 5]=5535, [1, 2, 6]=5532}",
                5.001189999995907
        );
        bagsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, " +
                "5, 6, 6, 6, 6, 7, 7, 8, 8, 9, 9, 10, 12, 12, 13, 15]," +
                " [1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 9]," +
                " [1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 5, 10, 13]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5," +
                " 5, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 10, 10, 13, 14, 17, 19, 20]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, " +
                "4, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 9, 10, 11, 18]," +
                " [1, 1, 1, 1, 1, 4, 4, 5, 5, 6, 8, 8, 10, 13]," +
                " [1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6, 8, 10, 15]," +
                " [1, 1, 1, 2, 2, 3, 3, 4, 4, 7, 7, 9]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3," +
                " 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7, 8, 8, 8, 8" +
                ", 8, 8, 9, 9, 9, 10, 10, 10, 12, 13, 14, 15, 15, 17], [1, 1, 1, 1, 2, 2, 2, 4, 7, 8, 8]," +
                " [1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 6, 6, 7, 7, 8, 10, 11]," +
                " [1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 7, 7, 8]," +
                " [1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 5, 5, 5, 6, 6, 8, 8, 13]," +
                " [1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 5, 5, 6, 6, 6, 6, 6, 7, 8, 8, 9, 9, 9, 10, 12" +
                ", 16]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, " +
                "2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6," +
                " 6, 6, 6, 6, 6, 7, 8, 8, 9, 10, 10, 11, 14, 15, 17]," +
                " [1, 2, 2, 2, 2, 4, 4, 4, 4, 4, 5, 5, 6, 7, 8, 8, 8, 9, 11]," +
                " [1, 1, 2, 2, 2, 2, 2, 3, 3, 4, 4, 5, 6, 8, 8, 9, 9, 10, 12], [1, 2, 3, 4, 5, 6, 6, 6, 11]," +
                " [1, 1, 1, 1, 1, 1, 2, 2, 4, 5, 5, 6, 16]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, " +
                "3, 3, 4, 4, 5, 5, 5, 6, 7, 7, 7, 8, 10, 11, 15, 17], ...]",
                "{[1, 1, 1, 2, 2, 3, 4, 5]=91, [1, 1, 1, 2, 2, 3, 3, 4]=78, [1, 1, 1, 1, 2, 2, 3, 4]=77," +
                " [1, 1, 1, 2, 2, 2, 3, 4]=74, [1, 1, 2, 2, 2, 3, 4, 5]=69, [1, 1, 2, 2, 3, 3, 4, 5]=68," +
                " [1, 1, 1, 2, 3, 3, 4, 5]=68, [1, 1, 2, 2, 2, 3, 3, 4]=66, [1, 1, 1, 2, 2, 3, 4, 6]=64" +
                ", [1, 1, 1, 2, 2, 3, 4, 4]=64}",
                32.00730000002313
        );
        bagsAtLeast_helper(
                2,
                1,
                repeat(1),
                "[[1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1], [1], [1, 1]," +
                " [1, 1, 1, 1], [1], [1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1], [1, 1, 1], [1], [1], [1, 1], [1, 1]," +
                " [1], ...]",
                "{[1]=499125, [1, 1]=250897, [1, 1, 1]=124849, [1, 1, 1, 1]=62518, [1, 1, 1, 1, 1]=31407," +
                " [1, 1, 1, 1, 1, 1]=15634, [1, 1, 1, 1, 1, 1, 1]=7825, [1, 1, 1, 1, 1, 1, 1, 1]=3926," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1]=1896, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=956}",
                2.0008359999800347
        );
        bagsAtLeast_helper(
                5,
                3,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1], [1, 1, 1], [1, 1, 1, 1, 1, 1], ...]",
                "{[1, 1, 1]=333813, [1, 1, 1, 1]=221150, [1, 1, 1, 1, 1]=148025, [1, 1, 1, 1, 1, 1]=98992," +
                " [1, 1, 1, 1, 1, 1, 1]=66270, [1, 1, 1, 1, 1, 1, 1, 1]=43747, [1, 1, 1, 1, 1, 1, 1, 1, 1]=29389," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=19567, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=12958" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=8571}",
                5.002096999996331
        );
        bagsAtLeast_helper(
                32,
                8,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1]=39940, [1, 1, 1, 1, 1, 1, 1, 1, 1]=38196," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=36988, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=35334," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=33831, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=32551," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=31521," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=30149," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=28763" +
                ", [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]=27543}",
                32.00360900002322
        );
        bagsAtLeast_fail_helper(5, 3, Collections.emptyList());
        bagsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        bagsAtLeast_fail_helper(5, 5, P.integers());
        bagsAtLeast_fail_helper(4, 5, P.integers());
        bagsAtLeast_fail_helper(2, 1, Collections.singletonList(null));
        bagsAtLeast_fail_helper(2, 1, Arrays.asList(1, null, 3));
    }

    private static void stringBagsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBagsAtLeast(minSize, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBagsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringBagsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBagsAtLeast_int_String() {
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "[aaaa, aaa, aaaaaaaaaa, aaa, aaa, a, aa, aaaa, a, a, aaa, aaa, aaa, a, aaa, a, a, aa, aa, a, ...]",
                "{a=499125, aa=250897, aaa=124849, aaaa=62518, aaaaa=31407, aaaaaa=15634, aaaaaaa=7825," +
                " aaaaaaaa=3926, aaaaaaaaa=1896, aaaaaaaaaa=956}",
                2.0008359999800347
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "a",
                "[aaaaaaa, aaaaaaaa, aaaaaaaa, aaaaaaa, aaaaa, aaaaaaaa, aaa, aaaa, aaaaa, aaaa, aaaaaa, aaaaaaaaa," +
                " aaaa, aaaaa, aaa, aaaa, aaa, aaaa, aaa, aaaaaa, ...]",
                "{aaa=333813, aaaa=221150, aaaaa=148025, aaaaaa=98992, aaaaaaa=66270, aaaaaaaa=43747," +
                " aaaaaaaaa=29389, aaaaaaaaaa=19567, aaaaaaaaaaa=12958, aaaaaaaaaaaa=8571}",
                5.002096999996331
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "a",
                "[aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaa, aaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaa, aaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaa, aaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaa, ...]",
                "{aaaaaaaa=39940, aaaaaaaaa=38196, aaaaaaaaaa=36988, aaaaaaaaaaa=35334, aaaaaaaaaaaa=33831," +
                " aaaaaaaaaaaaa=32551, aaaaaaaaaaaaaa=31521, aaaaaaaaaaaaaaa=30149, aaaaaaaaaaaaaaaa=28763" +
                ", aaaaaaaaaaaaaaaaa=27543}",
                32.00360900002322
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "[abbb, abbbbccc, abc, c, abc, c, bc, b, c, acc, b, c, b, c, a, c, a, a, c, ab, ...]",
                "{b=167156, c=166869, a=166251, bc=56002, ac=55604, ab=55313, abc=27815, bb=27782, aa=27761," +
                " cc=27621}",
                1.9993039999798474
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "[abbbbbb, abbbccc, bcc, aabcc, abc, bcccc, abcc, aac, aac, abbbcc, bbbbccc, abb, aaaaabccc," +
                " aabbbcccccc, bbcc, acc, abcc, aaa, aaaacccc, abc, ...]",
                "{abc=74121, bcc=37460, acc=37281, aab=36973, abb=36882, bbc=36847, aac=36676, abbc=32994," +
                " abcc=32874, aabc=32719}",
                5.003739999996368
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "abc",
                "[aaaaaaaaaaaaaabbbbbbbbbbbbbccccccccccccccccccccc, aaaabbbbbbbccccccccccccccc, aaabcccc," +
                " aaabcccccc, aaaaaabbbbbbccccccccc, aaaaaaaaabbbbccc, aaaaaaabbbbbbbbbccccccccc, aaaaabbbbbbcc," +
                " aaaaaabccccccccc," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccc," +
                " aaaaaaaaaabbbbbbbbbbbbbbbccccccccccccccc, aaaaaaaabbbbbbbbbbbbbcccccccccccc," +
                " aaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbcccccccccccccccc, aaaaaaaaaaaaaabbbbbbbbbcccccccc," +
                " aaaaaaaabbbbbbbccccccc, aaaaaaaaaaaaabbbbbbbbbbbbbbbbccccccccccccccc," +
                " aaaaaaaaaaaabbbbbbbbbbcccccccccccccc, bbbbbcccc, aaaaaaaaaaabbbbbbbbbbbbcccccccc, aaabbbbcc, ...]",
                "{aabbbccc=3539, aaabbccc=3380, aaabbbcc=3372, aaabbbccc=3265, aaabbbbccc=2723, aaaabbbccc=2651," +
                " aabbcccc=2635, aaabbbcccc=2633, aabbbbcc=2615, aaaabbcc=2536}",
                32.010685000021894
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "[abbc, abbbbbbcc, abbc, c, bc, b, abc, b, a, cc, b, ab, bb, b, b, b, a, b, a, a, ...]",
                "{b=249789, c=125444, a=125036, bb=62887, bc=62524, ab=61937, ac=31260, abc=23497, abb=23397" +
                ", bbc=23350}",
                1.999585999979838
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "[bbbbccc, bbbc, bbbcc, abbc, aaabbbbc, bcc, bbbbc, aabbb, aab, abbc, bbcc, bbb, bbbcccc, abccccc," +
                " aaaabbbbcc, bbb, bbbccccc, bbb, bcc, abbbbc, ...]",
                "{abc=62498, bbc=62261, abb=62070, abbc=41699, bbb=41560, bcc=31305, aab=31297, bbbc=28057," +
                " abbb=27827, abbbc=23400}",
                5.00315899999616
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "abbc",
                "[aaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccc, bbbccccc, aaaabbbbbbbbbbbbbbbbbbbbccccccccccc," +
                " aaabbbbcc, aaabbbbbcccc, aaaaaabbbbbbbbbbbbbbbbb, aaaaaabbbbbbbccc, aabbbbbb, aaaabbbbbbbbbbccccc," +
                " abbbbbcc, aaaabbbbbbbcccccccc, aaaaaabbbbbbbbbcccc," +
                " aaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccc," +
                " aaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccc, aaaaaaabbbbbbbbbbbbbbbbbbbbcccc," +
                " abbbbbcc, aaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccc, aaaabbbbbccc," +
                " aaaaaaaaaaabbbbbbbbbbbbbbccccc, abbbbbbbbccccc, ...]",
                "{aabbbbcc=4084, aabbbbbcc=3541, aabbbbbc=3276, abbbbbcc=3271, aabbbbccc=2956, aabbbbbbcc=2929," +
                " aaabbbbcc=2912, aaabbbbbcc=2878, aabbbbbccc=2789, aabbbccc=2777}",
                32.008717000021356
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "[psss, iisssss, i, iss, iss, s, i, is, s, ss, i, ip, Mi, i, p, i, s, s, psss, s, ...]",
                "{i=182168, s=181773, p=91352, is=66017, M=45380, ps=33034, ii=32991, ss=32971, ip=32852, iss=18258}",
                1.9990949999798069
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "[iisssss, iss, iipss, Miisssss, iissss, Mip, ipps, ipsss, ssssss, iiiisss, iiippss, Mis," +
                " iiiiisssss, Miiss, iis, iis, MMss, iipssss, Miis, ipss, ...]",
                "{iss=48252, ips=48149, iis=47592, iip=23891, Mis=23872, pss=23837, iips=23232, ipss=23211," +
                " iiss=23121, sss=16261}",
                5.003636999996235
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "Mississippi",
                "[MMMiiiiiiiiiippppppppsssssssssssssssssssssssssss, iiiiiiiiipppssssssssssss, Miiiisss, Msssssssss," +
                " Miiiiiiippppppsssssss, iiiiippss, Mipppssss, MMMiiippssssssssss, MMiiiiiiiipppsss," +
                " MMMiiiiiipssssssss," +
                " MMMMMMMMiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiipppppppppppppppppssssssssssssssssssssssssssssss," +
                " MMMiiiiiiiiiiiiiiiiiiippppppppssssssssssssss, MMMiiiiiiiipppppsssssssss," +
                " MMMMiiiiiiiiiiiiiiiiipppppppsssssssssssssssssssssssssssss, MMiiiiiiiiiiiiiiipppssssssss," +
                " MMMiiiiipsssss, MMMMiiiiiiiiiiiiiiiiiiipppppppppppssssssssssssssssss, MMiiiipppsssssssssss," +
                " iiiiiiiiiipppppsssssss, Miiiiiiiiipppssssss, ...]",
                "{Miiipsss=1772, iiippsss=1715, iiipssss=1663, iiiipsss=1639, iiiipssss=1341, Miiiipsss=1341," +
                " iiiippsss=1340, Miiippsss=1323, Miiiipss=1312, Miiippss=1305}",
                32.00263800002314
        );
        stringBagsAtLeast_int_String_fail_helper(5, 3, "");
        stringBagsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringBagsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringBagsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBagsAtLeast(minSize)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBagsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringBagsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBagsAtLeast_int() {
        stringBagsAtLeast_int_helper(
                2,
                1,
                "[ε\u2538\u31e5䊿, ϡ\u19dcㄾ䯏刿罖몱\udd15ﲦ, ᬜK㵏ꏹ, 㩷, 纫\ufe2d, 䝲, \u2a43坤琖, \uea45, 蕤," +
                " \u2b63\uf637, 鸅, \u33b2\uee1c, ᅺ됽, 䇺, \ue9fd, 㖊, \uaaf0, 覚, 䱸, \u2e24, ...]",
                "{瓫=24, 簐=22, 듑=22, 瀯=21, \uf3e9=21, 䝞=21, 抷=20, 䒢=20, Ẑ=20, 텁=20}",
                1.999585999979838
        );
        stringBagsAtLeast_int_helper(
                5,
                3,
                "[\u31e5肣赧髽\ue215\uf6ffﳑ, 䯏몱\udd15ﲦ, ㄾ䲵偵刿箿, K㵏缄ꏹ, \u2a43坤琖蕤餥퉌\ue352\uea45," +
                " \u2b63鸂\uf637, ᅺ\u33b2酓됽캆, \u2aec㖊\uaaf0짎\ue9fd, 䱸覚돘, ܓሮ鄒\uf878, 尩ꪻ굿\uecf5, 빅빮\ue8b2," +
                " \u3353刓嗏瀵컦\ud805\ue2d3, 䫯噋躁ꌻ홃\ue87c\uf36f, \u061a\u2e94㽖䥔壙穨鼧픫\udd82ﶼ, 嗮糦\uf329," +
                " B\u0e77\u2a57㽿慚ꯃ총\uf36b, ꅪ\udcc6\udec6, \u0d5b\u337d萋, \u1366ᵠ詪쀝쪡\uf21b, ...]",
                "{\u31e5肣赧髽\ue215\uf6ffﳑ=1, 䯏몱\udd15ﲦ=1, ㄾ䲵偵刿箿=1, K㵏缄ꏹ=1, \u2a43坤琖蕤餥퉌\ue352\uea45=1," +
                " \u2b63鸂\uf637=1, ᅺ\u33b2酓됽캆=1, \u2aec㖊\uaaf0짎\ue9fd=1, 䱸覚돘=1, ܓሮ鄒\uf878=1}",
                5.00315899999616
        );
        stringBagsAtLeast_int_helper(
                32,
                8,
                "[ܓᅺሮᤘ\u28de\u2aec\u2b63\u2e24\u33b2㖊㱉䇺䟆䱸全尩泦煖疜覚误輮鄒酓鸂鸅\ua7b1ꪻ\uaaf0굿돘됽띯빅빮짎캆\ue2da" +
                "\ue5cb\ue78f\ue8b2\ue9fd\uecf5\uee1c\uf637\uf878聆\uff03, ឃ\u3353刓嗏瀵컦\ud805\ue2d3," +
                " B\u0964\u0d5b\u0e77\u2a57\u337d㽿䍾䞂嗮徜慚睸糦緜萋詪詵錹鎿鐳颒鰫ꅪꯃ묆쪡총\ud8ca\udcc6\udd42\udec6\uf329" +
                "\uf36bﻧ, \u2293䴻庺槔横駆ꎤ퉐\ued0d," +
                " ᯖ\u202c㝏䃼䦣儖匀낛띆\ue9c6\uea55ﱓ, Ȟͺұډ\u0a49ලხ\u124e㲜䔾䠬䮼叙嶂繲葒蛕ꪪ췴턞\ue28c\ue852\uefc8," +
                " ᖒ\u2e64㥑㿘䁅䝀偘滞燔趵ꎿ\uab6e년믱젯\ue89b, ᶙ\u26f2㙴䁩聂ꃖꐲ\udc58," +
                " ᅒ\u2320\u2606\u32b0㕯婜狙阏陜頵顓ꎚ궥댲뚗쇻쓎쟒쳮, \u0618\u19de\u25ad\u2613戯旞韕똽," +
                " \u0d47\u0f1bᓑ\u2eea㚏䢗帇晸濙犋瞁鈖ꤥ죴쿕퀯퓰\uebc7\uf800," +
                " а\u21c0\u2684\u2b83撻梏沅羾葆ꎠ궺놂뗢뵪쒞쥶팗풬\ue40c," +
                " \u073d\u0aa9ஆ\u0b8c\u1069ቾ\u1739គᥔ\u19b1\u1a60\u1aba\u1b56ᳯᶝ\u2026\u2a33\u2af8\u2f0d\u3243㥐㭍㻶" +
                "㼰㾸䅁䈏䦼䨺\u4df4乃侒偰剹厖叵呼団姕嬵悲挑摡枧柿桶泈狚睗窀箴糘茞蕕閦顆飰館鳙鳸\ua6f4Ꜣ\ua7afꯈ냥닁듂뼄뾁쀁쁳쑹운쥇챲춮" +
                "칗탰헑\ud845\ude93\ue10d\ue480\ue531\ue649\ue672\uecda\ued1a\uf351\uf3a8," +
                " ʵۮܒ\u07aa\u0875\u17cbᰁ\u1c47\u1fce\u25a0\u291e\u2e50\u2fbeㆲ㗨㙿㢍䒁䒉嘟墺廏操民滎瀉爑甍皑秳稔臙艸芦藟詂" +
                "謳貗頔\ua837놿뇐멖뮣벧샣솄톋퍆\udde1\ue22c\uecda\uee3a\uee84\uf2ff\uf6cd," +
                " K\u0529Ꮃᶑ㓆䌚䜐卩嘂壵显禎细鏌阘鳰Ꜯ\ua9f4갭쉙쬋쵕춢후\uda6c\ude3a\ue61e\ue9d5\uf04f\uf6a5\ufe3d," +
                " ᬱ㨘昺炣玉虹뭇뾂," +
                " 7Ϯ\u0c00\u0f24Ᏺᚅ\u1debど㖃㥥㴯㺸䌕䎌䜜則勪懜戎担敉杁欎溯潵焣穣笨绡缂葑頒鱼鲦齞걓겧렔몔씅익쯹츪클퇢횋\udd06" +
                "\ude35\ue47a\ue498\ue59b串聾ﶯ, \u0482\u2153耕茟詴ꏠꜰ쵕쾭\ud847\uef98\uf7cd," +
                " \u0361\u0cecᒚ\u21ba\u2818\u2b5dⲄⴋⴓ㺱㻞巶忢憊椼檌檮滙玌璎疟祍蠛裤ꚸ뤀먛\ud85c\udbf4\uec15," +
                " Ȣⱊ\u2f43僮眏軷霺겴뜓좲퐗햽\ue41d\uf5e2, ...]",
                "{ܓᅺሮᤘ\u28de\u2aec\u2b63\u2e24\u33b2㖊㱉䇺䟆䱸全尩泦煖疜覚误輮鄒酓鸂鸅\ua7b1ꪻ\uaaf0굿돘됽띯빅빮짎캆\ue2da" +
                "\ue5cb\ue78f\ue8b2\ue9fd\uecf5\uee1c\uf637\uf878聆\uff03=1, ឃ\u3353刓嗏瀵컦\ud805\ue2d3=1," +
                " B\u0964\u0d5b\u0e77\u2a57\u337d㽿䍾䞂嗮徜慚睸糦緜萋詪詵錹鎿鐳颒鰫ꅪꯃ묆쪡총\ud8ca\udcc6\udd42\udec6\uf329" +
                "\uf36bﻧ=1, \u2293䴻庺槔横駆ꎤ퉐\ued0d=1, ᯖ\u202c㝏䃼䦣儖匀낛띆\ue9c6\uea55ﱓ=1," +
                " Ȟͺұډ\u0a49ලხ\u124e㲜䔾䠬䮼叙嶂繲葒蛕ꪪ췴턞\ue28c\ue852\uefc8=1," +
                " ᖒ\u2e64㥑㿘䁅䝀偘滞燔趵ꎿ\uab6e년믱젯\ue89b=1, ᶙ\u26f2㙴䁩聂ꃖꐲ\udc58=1," +
                " ᅒ\u2320\u2606\u32b0㕯婜狙阏陜頵顓ꎚ궥댲뚗쇻쓎쟒쳮=1, \u0618\u19de\u25ad\u2613戯旞韕똽=1}",
                32.008717000021356
        );
        stringBagsAtLeast_int_fail_helper(5, 5);
        stringBagsAtLeast_int_fail_helper(4, 5);
    }

    private static void stringSubsets_int_String_helper(
            int size,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringSubsets(size, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void stringSubsets_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.stringSubsets(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_int_String() {
        stringSubsets_int_String_helper(
                0,
                "a",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringSubsets_int_String_helper(
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}"
        );
        stringSubsets_int_String_helper(
                0,
                "abc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringSubsets_int_String_helper(
                1,
                "abc",
                "[b, b, c, b, a, b, b, b, b, b, a, b, b, c, b, c, a, c, b, b, ...]",
                "{c=333615, b=333313, a=333072}"
        );
        stringSubsets_int_String_helper(
                2,
                "abc",
                "[bc, ab, ab, bc, bc, ac, bc, ac, ab, bc, bc, ac, ab, ac, ab, bc, ac, bc, ac, ab, ...]",
                "{bc=334055, ab=333027, ac=332918}"
        );
        stringSubsets_int_String_helper(
                3,
                "abc",
                "[abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc," +
                " abc, ...]",
                "{abc=1000000}"
        );
        stringSubsets_int_String_helper(
                0,
                "abbc",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringSubsets_int_String_helper(
                1,
                "abbc",
                "[b, b, c, b, b, c, a, b, b, c, c, b, c, b, c, b, a, b, b, b, ...]",
                "{b=499640, c=250298, a=250062}"
        );
        stringSubsets_int_String_helper(
                2,
                "abbc",
                "[bc, bc, ab, bc, bc, bc, bc, ab, bc, ab, bc, bc, ab, bc, ab, bc, bc, bc, ab, ab, ...]",
                "{bc=416663, ab=416324, ac=167013}"
        );
        stringSubsets_int_String_helper(
                3,
                "abbc",
                "[abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc," +
                " abc, ...]",
                "{abc=1000000}"
        );
        stringSubsets_int_String_helper(
                0,
                "Mississippi",
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringSubsets_int_String_helper(
                1,
                "Mississippi",
                "[p, p, s, s, s, p, s, s, i, i, s, s, s, p, s, i, s, i, s, s, ...]",
                "{s=363979, i=363703, p=181581, M=90737}"
        );
        stringSubsets_int_String_helper(
                2,
                "Mississippi",
                "[ps, ps, is, is, ps, is, is, is, ip, is, is, ip, is, Ms, is, is, is, ip, Mp, ip, ...]",
                "{is=416142, ps=184999, ip=183883, Mi=88745, Ms=88051, Mp=38180}"
        );
        stringSubsets_int_String_helper(
                3,
                "Mississippi",
                "[ips, ips, ips, ips, Mis, ips, Mip, ips, Mis, ips, Mps, ips, ips, ips, ips, Mis, ips, ips, Mis," +
                " Mis, ...]",
                "{ips=572857, Mis=256219, Mps=85499, Mip=85425}"
        );
        stringSubsets_int_String_fail_helper(1, "");
        stringSubsets_int_String_fail_helper(-1, "abc");
    }

    private static void stringSubsets_int_helper(int size, @NotNull String output, @NotNull String topSampleCount) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringSubsets(size)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private void stringSubsets_int_fail_helper(int size) {
        try {
            P.stringBags(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_int() {
        stringSubsets_int_helper(
                0,
                "[, , , , , , , , , , , , , , , , , , , , ...]",
                "{=1000000}"
        );
        stringSubsets_int_helper(
                1,
                "[嘩, 퇉, 馃, \u2df2, ε, 䊿, \u2538, \u31e5, 髽, 肣, \uf6ff, ﳑ, 赧, \ue215, \u17f3, \udd75, 껸, \udd15," +
                " 몱, ﲦ, ...]",
                "{\uf1b2=36, 撢=35, આ=34, 퉃=34, \27=33, 韖=32, 㖒=32, 膗=31, 㗞=31, 䕦=31}"
        );
        stringSubsets_int_helper(
                2,
                "[嘩퇉, \u2df2馃, ε䊿, \u2538\u31e5, 肣髽, \uf6ffﳑ, 赧\ue215, \u17f3\udd75, 껸\udd15, 몱ﲦ, ϡ䯏," +
                " \u19dc罖, ㄾ刿, 䲵箿, 偵恾, ᬜK, 㵏ꏹ, 㩷缄, ⴿ읾, 纫\ufe2d, ...]",
                "{ᴴ\u2bdb=2, \u223b圉=2, 틺\uf310=2, 㑰\uf5be=2, 啺횄=2, 㧣㻜=2, \u23c0\uf480=2, ᮽ倄=2, Ⳬ\ue4b6=2," +
                " 籯퓁=2}"
        );
        stringSubsets_int_helper(
                3,
                "[嘩馃퇉, ε\u2df2䊿, \u2538\u31e5髽, 肣\uf6ffﳑ, \u17f3赧\ue215, 껸\udd15\udd75, 䯏몱ﲦ, ϡ\u19dc罖, ㄾ䲵刿," +
                " 偵恾箿, ᬜK㵏, 㩷缄ꏹ, ⴿ纫읾, 㗂䝲\ufe2d, 갩힜\uf207, \u2a43坤琖, 퉌\ue352\uea45, 䉀蕤餥, \u2b63鸂\uf637," +
                " 误輮鸅, ...]",
                "{嘩馃퇉=1, ε\u2df2䊿=1, \u2538\u31e5髽=1, 肣\uf6ffﳑ=1, \u17f3赧\ue215=1, 껸\udd15\udd75=1, 䯏몱ﲦ=1," +
                " ϡ\u19dc罖=1, ㄾ䲵刿=1, 偵恾箿=1}"
        );
        stringSubsets_int_fail_helper(-1);
    }

    private static void subsets_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsets(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsets_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        subsets_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void subsets_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).subsets(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsets_Iterable() {
        subsets_Iterable_helper_uniform(
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [], [5], [5], [], [], [5], [5], [5], [], [5], [], [], [5], [5], [], ...]",
                "{[5]=500875, []=499125}",
                0.5008749999935656
        );
        subsets_Iterable_helper_uniform(
                2,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [], [5], [5], [5], [5], [5], [5], [5], [], [5], [], [5], [], [5]," +
                " ...]",
                "{[5]=666187, []=333813}",
                0.6661869999983192
        );
        subsets_Iterable_helper_uniform(
                4,
                "[5]",
                "[[5], [5], [5], [5], [5], [], [5], [], [5], [5], [5], [5], [5], [5], [5], [5], [], [5], [5], [5]," +
                " ...]",
                "{[5]=799806, []=200194}",
                0.7998060000021615
        );
        subsets_Iterable_helper_uniform(
                1,
                "[1, 2, 3]",
                "[[1, 2], [1, 2, 3], [1, 3], [2, 3], [], [], [1, 3], [2], [], [], [3], [1, 3], [3], [3], [], [3]," +
                " [], [], [], [], ...]",
                "{[]=499504, [2]=100253, [3]=100058, [1]=100023, [2, 3]=50160, [1, 2, 3]=50048, [1, 2]=49990" +
                ", [1, 3]=49964}",
                0.7507059999970308
        );
        subsets_Iterable_helper_uniform(
                2,
                "[1, 2, 3]",
                "[[2], [2], [2, 3], [], [2, 3], [1, 3], [1, 2], [3], [1, 2, 3], [1], [1], [1], [1, 2], [2, 3], []," +
                " [2], [1, 2, 3], [], [], [], ...]",
                "{[]=333247, [1, 2, 3]=152964, [2]=95442, [3]=95325, [1]=94824, [1, 2]=76127, [1, 3]=76065" +
                ", [2, 3]=76006}",
                1.2008789999923022
        );
        subsets_Iterable_helper_uniform(
                4,
                "[1, 2, 3]",
                "[[2], [2], [1, 2, 3], [1], [3], [1, 2, 3], [2, 3], [1, 2, 3], [], [1, 2, 3], [1, 3], [1, 3]," +
                " [1, 2, 3], [], [], [1, 3], [1], [1, 3], [], [1, 3], ...]",
                "{[1, 2, 3]=332856, []=199912, [2, 3]=83497, [1, 2]=82734, [1, 3]=82492, [1]=72945, [2]=72868" +
                ", [3]=72696}",
                1.7145229999887661
        );
        subsets_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4]",
                "[[1, 2, 4], [1, 2, 3, 4], [1, 3, 4], [4], [4], [3], [1, 3], [2], [], [], [4], [2], [1], [], [3]," +
                " [3], [], [3], [], [], ...]",
                "{[]=499557, [3]=71710, [2]=71646, [1]=71506, [4]=71260, [3, 4]=23960, [1, 4]=23912, [1, 2]=23865," +
                " [2, 3]=23842, [1, 3]=23669}",
                0.8006769999971934
        );
        subsets_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4]",
                "[[2, 4], [2, 3], [3, 4], [2, 3, 4], [], [1, 2, 3, 4], [2], [], [3, 4], [1, 2, 3], [], [], [1], [1]," +
                " [2, 4], [], [2, 3, 4], [4], [4], [1, 2, 3, 4], ...]",
                "{[]=333041, [2]=66872, [1, 2, 3, 4]=66813, [1]=66679, [3]=66527, [4]=66467, [1, 3, 4]=33691," +
                " [2, 3, 4]=33589, [2, 3]=33463, [3, 4]=33406}",
                1.334835999990812
        );
        subsets_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4]",
                "[[2, 4], [2], [1, 2, 3, 4], [1, 3], [], [], [3], [1, 2, 4], [2, 3, 4], [1, 2, 3, 4], [2, 3, 4]," +
                " [1, 2, 3, 4], [1], [], [1, 3, 4], [1, 3], [1, 2, 3, 4], [3], [], [1, 3, 4], ...]",
                "{[1, 2, 3, 4]=200419, []=200010, [1, 3, 4]=50393, [2, 3, 4]=50288, [2]=50021, [1, 2, 4]=49914," +
                " [1, 2, 3]=49893, [4]=49847, [3]=49827, [1]=49823}",
                2.001787999981212
        );
        subsets_Iterable_helper_uniform(
                1,
                "[1, 2, 2, 4]",
                "[[1, 2, 4], [1, 2, 4], [1, 2, 4], [4], [4], [2], [1, 2], [2], [], [], [4], [2], [1], [], [2], [2]," +
                " [], [2], [], [], ...]",
                "{[]=499557, [2]=167198, [1]=71506, [4]=71260, [2, 4]=61764, [1, 2]=61754, [1, 2, 4]=43049" +
                ", [1, 4]=23912}",
                0.7339709999971153
        );
        subsets_Iterable_helper_uniform(
                2,
                "[1, 2, 2, 4]",
                "[[2, 4], [2], [2, 4], [2, 4], [], [1, 2, 4], [2], [], [2, 4], [1, 2], [], [], [1], [1], [2, 4], []," +
                " [2, 4], [4], [4], [1, 2, 4], ...]",
                "{[]=333041, [2]=166862, [1, 2, 4]=133729, [2, 4]=100221, [1, 2]=99830, [1]=66679, [4]=66467" +
                ", [1, 4]=33171}",
                1.1676389999927037
        );
        subsets_Iterable_helper_uniform(
                4,
                "[1, 2, 2, 4]",
                "[[2, 4], [2], [1, 2, 4], [1, 2], [], [], [2], [1, 2, 4], [2, 4], [1, 2, 4], [2, 4], [1, 2, 4], [1]," +
                " [], [1, 2, 4], [1, 2], [1, 2, 4], [2], [], [1, 2, 4], ...]",
                "{[1, 2, 4]=300726, []=200010, [2]=133338, [2, 4]=116702, [1, 2]=116234, [4]=49847, [1]=49823" +
                ", [1, 4]=33320}",
                1.667697999989275
        );
        subsets_Iterable_helper_uniform(
                1,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [2], [2], [2], [2], [], [], [2], [2], [2], [], [2], [2], [], [2], [], [], ...]",
                "{[2]=500443, []=499557}",
                0.5004429999935531
        );
        subsets_Iterable_helper_uniform(
                2,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [], [2], [2], [], [2], [2], [], [], [2], [2], [2], [], [2], [2], [2], [2], ...]",
                "{[2]=666959, []=333041}",
                0.6669589999983414
        );
        subsets_Iterable_helper_uniform(
                4,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [], [], [2], [2], [2], [2], [2], [2], [2], [], [2], [2], [2], [2], [], [2]," +
                " ...]",
                "{[2]=799990, []=200010}",
                0.7999900000021668
        );
        subsets_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[6, 9], [2, 6, 7, 10], [], [3], [5, 7], [6], [], [], [4], [6], [3], [7], [], [], [1], [], [], []," +
                " [], [], ...]",
                "{[]=500030, [10]=26710, [7]=26432, [4]=26418, [1]=26417, [6]=26409, [2]=26409, [5]=26279," +
                " [8]=26268, [3]=26245}",
                0.9078379999975383
        );
        subsets_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 6, 8], [2, 6], [6, 10], [], [3, 4, 5, 6, 7], [], [4], [3, 4, 7], [1, 9], [9], [3, 6, 10]," +
                " [4, 6, 7], [2, 4, 8], [3, 5, 7, 8, 9, 10], [], [], [], [3, 4, 5, 6, 7, 8], [2, 7], [], ...]",
                "{[]=333018, [6]=23950, [10]=23943, [7]=23883, [2]=23862, [4]=23764, [1]=23753, [3]=23694," +
                " [5]=23667, [8]=23610}",
                1.6697689999898184
        );
        subsets_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 6, 8], [6], [1, 3, 4, 5, 6, 7, 8, 9], [3, 6, 10], [6, 7], [1, 3, 4, 5, 7, 8, 9, 10], []," +
                " [1, 3, 4, 5, 6, 7, 8], [3, 8], [1, 3, 7, 10], [2, 3, 5, 10], [1], [3, 5, 6, 9], [5], [1, 3, 8, 9]," +
                " [], [], [1, 4], [6, 7], [2, 4, 6, 9], ...]",
                "{[]=200177, [10]=17554, [8]=17510, [1]=17479, [6]=17445, [9]=17442, [5]=17440, [7]=17376," +
                " [4]=17340, [2]=17337}",
                2.8588639999882393
        );
        subsets_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 7, 10], [5], [], [1, 10], [], [3], [7], [], [5], [3], [], [], [], [], [], [], [], [], [5]," +
                " [6, 9, 12, 15], ...]",
                "{[]=499603, [1]=71387, [2]=51631, [3]=37715, [4]=27574, [5]=20807, [1, 2]=17158, [6]=15264," +
                " [1, 3]=12126, [7]=11330}",
                0.8811449999975006
        );
        subsets_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "[[4, 7, 10], [1, 2, 3, 7], [1, 2, 3], [], [5, 9, 15], [12], [1, 2], [], [1, 2, 4, 5, 6, 7, 9], []," +
                " [], [], [1, 3, 5], [2], [1, 4, 5], [12], [2], [], [1, 4], [1], ...]",
                "{[]=333149, [1]=66271, [2]=47475, [3]=34388, [4]=24997, [1, 2]=23194, [5]=18733, [1, 3]=16292," +
                " [6]=13708, [1, 4]=11818}",
                1.583489999990105
        );
        subsets_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "[[4, 7, 10], [1, 2, 3, 7, 8], [1, 2, 6, 12, 15], [13], [1, 2, 3, 4, 5, 6, 10], []," +
                " [1, 2, 3, 4, 6, 12], [], [], [], [1, 2, 3], [4, 5], [9], [1, 4, 5, 6, 7], [], [1, 2, 3]," +
                " [7, 10, 13], [4, 19], [1, 2, 3, 4, 17, 22], [], ...]",
                "{[]=199867, [1]=49790, [2]=35481, [3]=25159, [1, 2]=22484, [4]=18608, [1, 3]=15260, [5]=13362," +
                " [1, 2, 3]=13054, [1, 4]=11055}",
                2.668782999988186
        );
        subsets_Iterable_helper(
                1,
                repeat(1),
                "[[1], [1], [1], [1], [1], [], [1], [1], [], [], [1], [1], [1], [], [1], [], [], [1], [1], [], ...]",
                "{[1]=500875, []=499125}",
                0.5008749999935656
        );
        subsets_Iterable_helper(
                2,
                repeat(1),
                "[[1], [1], [1], [1], [1], [1], [], [1], [1], [1], [1], [1], [1], [1], [], [1], [], [1], [], [1]," +
                " ...]",
                "{[1]=666187, []=333813}",
                0.6661869999983192
        );
        subsets_Iterable_helper(
                4,
                repeat(1),
                "[[1], [1], [1], [1], [1], [], [1], [], [1], [1], [1], [1], [1], [1], [1], [1], [], [1], [1], [1]," +
                " ...]",
                "{[1]=799806, []=200194}",
                0.7998060000021615
        );
        subsets_Iterable_fail_helper(1, Collections.emptyList());
        subsets_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        subsets_Iterable_fail_helper(0, P.integers());
        subsets_Iterable_fail_helper(-1, P.integers());
        subsets_Iterable_fail_helper(1, Arrays.asList(1, null, 3));
        subsets_Iterable_fail_helper(1, Collections.singleton(null));
    }

    private static void stringSubsets_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsets(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsets_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringSubsets(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_String() {
        stringSubsets_String_helper(
                1,
                "a",
                "[a, a, a, a, a, , a, a, , , a, a, a, , a, , , a, a, , ...]",
                "{a=500875, =499125}",
                0.5008749999935656
        );
        stringSubsets_String_helper(
                2,
                "a",
                "[a, a, a, a, a, a, , a, a, a, a, a, a, a, , a, , a, , a, ...]",
                "{a=666187, =333813}",
                0.6661869999983192
        );
        stringSubsets_String_helper(
                4,
                "a",
                "[a, a, a, a, a, , a, , a, a, a, a, a, a, a, a, , a, a, a, ...]",
                "{a=799806, =200194}",
                0.7998060000021615
        );
        stringSubsets_String_helper(
                1,
                "abc",
                "[ab, abc, ac, bc, , , ac, b, , , c, ac, c, c, , c, , , , , ...]",
                "{=499504, b=100253, c=100058, a=100023, bc=50160, abc=50048, ab=49990, ac=49964}",
                0.7507059999970308
        );
        stringSubsets_String_helper(
                2,
                "abc",
                "[b, b, bc, , bc, ac, ab, c, abc, a, a, a, ab, bc, , b, abc, , , , ...]",
                "{=333247, abc=152964, b=95442, c=95325, a=94824, ab=76127, ac=76065, bc=76006}",
                1.2008789999923022
        );
        stringSubsets_String_helper(
                4,
                "abc",
                "[b, b, abc, a, c, abc, bc, abc, , abc, ac, ac, abc, , , ac, a, ac, , ac, ...]",
                "{abc=332856, =199912, bc=83497, ab=82734, ac=82492, a=72945, b=72868, c=72696}",
                1.7145229999887661
        );
        stringSubsets_String_helper(
                1,
                "abbc",
                "[abc, abc, abc, c, c, b, ab, b, , , c, b, a, , b, b, , b, , , ...]",
                "{=499557, b=167198, a=71506, c=71260, bc=61764, ab=61754, abc=43049, ac=23912}",
                0.7339709999971153
        );
        stringSubsets_String_helper(
                2,
                "abbc",
                "[bc, b, bc, bc, , abc, b, , bc, ab, , , a, a, bc, , bc, c, c, abc, ...]",
                "{=333041, b=166862, abc=133729, bc=100221, ab=99830, a=66679, c=66467, ac=33171}",
                1.1676389999927037
        );
        stringSubsets_String_helper(
                4,
                "abbc",
                "[bc, b, abc, ab, , , b, abc, bc, abc, bc, abc, a, , abc, ab, abc, b, , abc, ...]",
                "{abc=300726, =200010, b=133338, bc=116702, ab=116234, c=49847, a=49823, ac=33320}",
                1.667697999989275
        );
        stringSubsets_String_helper(
                1,
                "Mississippi",
                "[ps, is, is, , is, s, , , s, s, s, i, i, , i, , , , , , ...]",
                "{=499907, s=111371, i=111271, is=63764, p=49940, ips=27926, ps=26420, ip=26257, M=23658, Mis=12412}",
                0.7700039999971866
        );
        stringSubsets_String_helper(
                2,
                "Mississippi",
                "[is, is, is, i, is, , s, s, Mip, , , p, ps, s, is, ips, i, , is, , ...]",
                "{=333528, s=106872, i=106248, is=100321, ips=77392, p=45804, Mips=40546, ps=37802, ip=37774" +
                ", Mis=31686}",
                1.2632049999918284
        );
        stringSubsets_String_helper(
                4,
                "Mississippi",
                "[is, s, Mips, s, ps, s, Mips, s, Mis, is, Mips, Mips, , ips, i, Mips, , , Ms, s, ...]",
                "{=199852, ips=143500, Mips=131826, is=114182, s=82176, i=81772, Mis=51875, ip=38564, ps=38557" +
                ", p=34200}",
                1.8740139999846195
        );
        stringSubsets_String_fail_helper(1, "");
        stringSubsets_String_fail_helper(0, "abc");
        stringSubsets_String_fail_helper(-1, "abc");
    }

    private static void stringSubsets_helper(
            int scale,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsets()));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsets_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).stringSubsets());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsets() {
        stringSubsets_helper(
                1,
                "[ε\u2538䊿, ϡ\u19dcㄾ䯏刿罖몱\udd15ﲦ, ᬜK㵏, 㩷, 纫, 䝲, 坤琖, \uea45, , , \u2b63, 鸅, \uee1c, , ᅺ, 䇺," +
                " , 㖊, , , ...]",
                "{=499557, \uf59a=15, 僵=14, \u12c7=14, 瘍=14, \ue0de=14, \ua838=13, 䃢=13, 喽=13, 瓫=13}",
                1.0006239999976707
        );
        stringSubsets_helper(
                2,
                "[\u31e5肣髽\uf6ff, 몱\udd15ﲦ, ㄾ刿, K㵏ꏹ, , \u2a43坤琖퉌\uea45, 餥, , \u33b2酓캆, \u2aec㖊짎\ue9fd, , ," +
                " 䱸, \uf878, 尩굿\uecf5, , \u3353刓嗏瀵컦\ud805\ue2d3, 䫯噋ꌻ\uf36f, 홃, \u061a\u2e94壙穨鼧픫\udd82, ...]",
                "{=333041, 趤=15, 挗=13, \u2fae=13, 阤=12, \u0978=12, \ue2fe=12, \uab10=12, 䖸=12, \ue973=12}",
                2.0036399999891383
        );
        stringSubsets_helper(
                4,
                "[\u31e5肣髽\uf6ff, 몱\udd15, ᅺᤘ\u2b63\u33b2䇺煖误輮酓鸂鸅됽캆\ue9fd\uee1c\uf637, 全覚, , , ሮ," +
                " 尩疜ꪻ굿\uecf5, 刓瀵컦, \u061a\u2e94㽖䥔嗮壙穨糦鼧픫핀\udd42\udd82\uf329ﶼﻧ, \u0e77慚ꯃ총\uf36b," +
                " ढ\u2293䴻庺槔横靯駆ꎤ퉐\ued0d, 䃼, , ᑒ拷만\ue68e, ͺ\u124e\u2506囀ꪪ, 䔾唯嶂湑猂甧蹙췴턞\uead1ﮍ, \uab6e, ," +
                " ᖒ㿘滞\ue89b, ...]",
                "{=200010, \ued08=11, 듏=11, \ua495=11, 幱=10, 㚼=10, Ꙛ=10, 홣=10, ﺆ=10, \ua494=10}",
                4.005472999991468
        );
        stringSubsets_fail_helper(0);
        stringSubsets_fail_helper(-1);
    }

    private static void subsetsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsetsAtLeast(minSize, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsetsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        subsetsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void subsetsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).subsetsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsetsAtLeast() {
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}",
                1.000000000007918
        );
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3]",
                "[[1, 2], [1, 2, 3], [1, 2, 3], [3], [1, 2, 3], [3], [2, 3], [2], [3], [1, 3], [2], [3], [2], [3]," +
                " [1], [3], [1], [1], [3], [1, 2], ...]",
                "{[2]=200397, [3]=200001, [1]=199719, [2, 3]=100517, [1, 2, 3]=100200, [1, 3]=99720, [1, 2]=99446}",
                1.50008299998526
        );
        subsetsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3]",
                "[[1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3]," +
                " [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3]," +
                " [1, 2, 3], [1, 2, 3], ...]",
                "{[1, 2, 3]=1000000}",
                2.9999999999775233
        );
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4]",
                "[[1, 2, 4], [1, 2, 3, 4], [1, 2, 3, 4], [4], [2, 4], [3], [1, 3, 4], [2], [1], [4], [2], [1, 3]," +
                " [2, 3], [3], [2], [3], [1], [3], [1], [1], ...]",
                "{[2]=143315, [4]=143102, [1]=143064, [3]=142514, [1, 4]=47716, [2, 3]=47647, [2, 4]=47577," +
                " [1, 3]=47540, [3, 4]=47519, [1, 2]=47049}",
                1.5996069999831977
        );
        subsetsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4]",
                "[[1, 2, 3, 4], [2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4], [2, 3, 4], [2, 3, 4], [1, 2, 3], [1, 3, 4]," +
                " [1, 2, 3, 4], [2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3], [2, 3, 4], [1, 2, 3]," +
                " [1, 2, 3, 4], [1, 3, 4], [1, 3, 4], [1, 3, 4], [1, 2, 4], ...]",
                "{[1, 2, 3, 4]=333852, [2, 3, 4]=167170, [1, 2, 3]=166847, [1, 2, 4]=166090, [1, 3, 4]=166041}",
                3.3338519999899345
        );
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 2, 4]",
                "[[1, 2, 4], [1, 2, 4], [1, 2, 4], [4], [2, 4], [2], [1, 2, 4], [2], [1], [4], [2], [1, 2], [2]," +
                " [2], [2], [2], [1], [2], [1], [1], ...]",
                "{[2]=333476, [4]=143102, [1]=143064, [2, 4]=123708, [1, 2]=123118, [1, 2, 4]=85816, [1, 4]=47716}",
                1.466173999985577
        );
        subsetsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 2, 4]",
                "[[1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4]," +
                " [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4], [1, 2, 4]," +
                " [1, 2, 4], [1, 2, 4], ...]",
                "{[1, 2, 4]=1000000}",
                2.9999999999775233
        );
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[2, 2, 2, 2]",
                "[[2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2], [2]," +
                " [2], ...]",
                "{[2]=1000000}",
                1.000000000007918
        );
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[4, 6, 9], [2, 6, 7], [10], [8], [3], [4, 5, 7], [6], [5], [4, 8], [6], [3, 4], [7], [9], [1, 9]," +
                " [9], [5], [4], [4], [3, 6, 10], [7], ...]",
                "{[6]=53166, [5]=52878, [2]=52770, [4]=52720, [3]=52634, [1]=52615, [8]=52598, [9]=52572," +
                " [10]=52566, [7]=52513}",
                1.8170889999810345
        );
        subsetsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 4, 6, 8], [2, 6, 7], [3, 5, 6, 8, 10], [3, 4, 5, 6, 7], [1, 4, 6], [1, 3, 4, 7, 9], [4, 5, 9]," +
                " [3, 6, 7, 10], [4, 6, 7], [2, 3, 4, 6, 7, 8], [3, 5, 8, 10], [1, 5, 7], [1, 2, 3, 4, 5, 6, 7, 8]," +
                " [3, 4, 5, 8], [1, 4, 7], [3, 4, 5, 7, 8, 9], [1, 3, 7, 10], [3, 6, 9, 10], [2, 3, 8, 10]," +
                " [1, 2, 5], ...]",
                "{[1, 5, 8]=3625, [1, 5, 10]=3580, [1, 2, 10]=3575, [4, 5, 8]=3573, [2, 3, 6]=3570, [4, 7, 10]=3570," +
                " [5, 6, 10]=3570, [4, 5, 10]=3564, [1, 6, 7]=3552, [2, 6, 10]=3551}",
                4.168420999985633
        );
        subsetsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 3, 4, 5, 6, 7, 9, 10]," +
                " [2, 3, 4, 5, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=584407, [2, 3, 4, 5, 6, 7, 8, 9, 10]=24560," +
                " [1, 2, 3, 4, 5, 6, 7, 9, 10]=24496, [1, 2, 3, 4, 5, 7, 8, 9, 10]=24492," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 10]=24318, [1, 3, 4, 5, 6, 7, 8, 9, 10]=24296," +
                " [1, 2, 3, 4, 5, 6, 8, 9, 10]=24254, [1, 2, 4, 5, 6, 7, 8, 9, 10]=24239," +
                " [1, 2, 3, 5, 6, 7, 8, 9, 10]=24234, [1, 2, 3, 4, 6, 7, 8, 9, 10]=24138}",
                9.41189799992237
        );
        subsetsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 7, 10], [3], [1, 3, 7], [1, 2], [2, 4, 8], [2], [2], [2], [8], [6, 9, 12, 15], [2], [1], [12]," +
                " [4, 7], [1, 2, 5, 6], [5], [1], [10], [2], [5], ...]",
                "{[1]=142762, [2]=104119, [3]=75181, [4]=55392, [5]=41192, [1, 2]=33786, [6]=30272, [1, 3]=24074," +
                " [7]=22836, [1, 4]=17437}",
                1.7601419999815262
        );
        subsetsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "[[1, 3, 4, 7, 10], [2, 3, 7, 8], [1, 2, 8], [6, 9, 12, 15], [1, 2, 13], [1, 2, 4, 5, 6, 7]," +
                " [1, 3, 5, 10], [2, 4, 5], [2, 4, 5], [1, 2, 3, 4, 6], [1, 2, 5], [4, 5, 6], [1, 4, 6, 7, 9]," +
                " [1, 2, 5, 10], [2, 3, 4], [1, 2, 3, 4, 5, 6, 7, 10, 19, 22], [1, 2, 3, 4, 7], [1, 2, 19]," +
                " [3, 7, 14], [1, 2, 4, 20], ...]",
                "{[1, 2, 3]=43250, [1, 2, 4]=30077, [1, 2, 5]=21354, [1, 3, 4]=20186, [1, 2, 3, 4]=19240," +
                " [1, 2, 6]=15287, [1, 3, 5]=14238, [1, 2, 3, 5]=13622, [2, 3, 4]=12731, [1, 2, 7]=11260}",
                3.989958999983499
        );
        subsetsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15], [1, 2, 3, 4, 5, 6, 7, 9]," +
                " [1, 2, 3, 4, 5, 6, 7, 10, 13, 19, 22], [1, 2, 3, 4, 5, 7, 8, 13, 14, 19, 20]," +
                " [1, 2, 3, 4, 5, 6, 8, 10], [1, 2, 3, 4, 5, 6, 11, 15], [1, 2, 3, 4, 5, 6, 7, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 8, 10, 13], [1, 2, 3, 4, 5, 6, 7, 8, 10, 15], [1, 2, 3, 4, 5, 6, 7, 9, 15]," +
                " [1, 2, 3, 4, 5, 6, 8, 9, 12, 15, 17], [1, 2, 3, 4, 5, 6, 7, 8, 10, 13, 14]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 15], [1, 2, 3, 4, 5, 6, 7, 8, 10, 11]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13], [1, 2, 3, 6, 7, 8, 9, 12, 16]," +
                " [1, 2, 3, 4, 5, 6, 7, 11, 13, 14], [1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 17]," +
                " [1, 2, 3, 4, 5, 7, 8, 9, 11, 16], [1, 2, 3, 4, 5, 6, 8, 9, 10, 12], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8]=15805, [1, 2, 3, 4, 5, 6, 7, 8, 9]=14490," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=12306, [1, 2, 3, 4, 5, 6, 7, 9]=10542," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 10]=9481, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]=9410," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 11]=8086, [1, 2, 3, 4, 5, 6, 7, 10]=7186," +
                " [1, 2, 3, 4, 5, 6, 8, 9]=6698, [1, 2, 3, 4, 5, 6, 7, 8, 11]=6627}",
                10.378239999978224
        );
        subsetsAtLeast_helper(
                2,
                1,
                repeat(1),
                "[[1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1], [1]," +
                " [1], ...]",
                "{[1]=1000000}",
                1.000000000007918
        );
        subsetsAtLeast_fail_helper(5, 3, Collections.emptyList());
        subsetsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        subsetsAtLeast_fail_helper(5, 5, P.integers());
        subsetsAtLeast_fail_helper(4, 5, P.integers());
        subsetsAtLeast_fail_helper(2, 1, Collections.singletonList(null));
        subsetsAtLeast_fail_helper(2, 1, Arrays.asList(1, null, 3));
    }

    private static void stringSubsetsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsAtLeast(minSize, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringSubsetsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsAtLeast_int_String() {
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}",
                1.000000000007918
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "[ab, abc, abc, c, abc, c, bc, b, c, ac, b, c, b, c, a, c, a, a, c, ab, ...]",
                "{b=200397, c=200001, a=199719, bc=100517, abc=100200, ac=99720, ab=99446}",
                1.50008299998526
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "[abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc," +
                " abc, ...]",
                "{abc=1000000}",
                2.9999999999775233
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "[abc, abc, abc, c, bc, b, abc, b, a, c, b, ab, b, b, b, b, a, b, a, a, ...]",
                "{b=333476, c=143102, a=143064, bc=123708, ab=123118, abc=85816, ac=47716}",
                1.466173999985577
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "[abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc, abc," +
                " abc, ...]",
                "{abc=1000000}",
                2.9999999999775233
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "[ps, is, i, is, is, s, i, is, s, s, i, ip, Mi, i, p, i, s, s, ps, s, ...]",
                "{i=222608, s=222220, is=127085, p=100226, ips=55787, ps=52857, ip=52609, M=47546, Mis=24765" +
                ", Mi=24457}",
                1.5401079999842737
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "[ips, ips, Mis, Mip, ips, ips, ips, Mips, Mis, Mis, Mps, ips, ips, ips, Mips, Mps, Mip, Mips, Mips," +
                " Mis, ...]",
                "{ips=484161, Mips=228522, Mis=187626, Mip=49852, Mps=49839}",
                3.2285219999851744
        );
        stringSubsetsAtLeast_int_String_fail_helper(5, 3, "");
        stringSubsetsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringSubsetsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringSubsetsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsAtLeast(minSize)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringSubsetsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsAtLeast_int() {
        stringSubsetsAtLeast_int_helper(
                2,
                1,
                "[ε\u2538\u31e5䊿, ϡ\u19dcㄾ䯏刿罖몱\udd15ﲦ, ᬜK㵏ꏹ, 㩷, 纫\ufe2d, 䝲, \u2a43坤琖, \uea45, 蕤," +
                " \u2b63\uf637, 鸅, \u33b2\uee1c, ᅺ됽, 䇺, \ue9fd, 㖊, \uaaf0, 覚, 䱸, \u2e24, ...]",
                "{瓫=24, 簐=22, 듑=22, 瀯=21, \uf3e9=21, 䝞=21, 抷=20, 䒢=20, Ẑ=20, 텁=20}",
                1.9995569999798375
        );
        stringSubsetsAtLeast_int_helper(
                5,
                3,
                "[\u31e5肣赧髽\ue215\uf6ffﳑ, 䯏몱\udd15ﲦ, ㄾ䲵偵刿箿, K㵏缄ꏹ, \u2a43坤琖蕤餥퉌\ue352\uea45," +
                " \u2b63鸂\uf637, ᅺ\u33b2酓됽캆, \u2aec㖊\uaaf0짎\ue9fd, 䱸覚돘, ܓሮ鄒\uf878, 尩ꪻ굿\uecf5, 빅빮\ue8b2," +
                " \u3353刓嗏瀵컦\ud805\ue2d3, 䫯噋躁ꌻ홃\ue87c\uf36f, \u061a\u2e94㽖䥔壙穨鼧픫\udd82ﶼ, 嗮糦\uf329," +
                " B\u0e77\u2a57㽿慚ꯃ총\uf36b, ꅪ\udcc6\udec6, \u0d5b\u337d萋, \u1366ᵠ詪쀝쪡\uf21b, ...]",
                "{\u31e5肣赧髽\ue215\uf6ffﳑ=1, 䯏몱\udd15ﲦ=1, ㄾ䲵偵刿箿=1, K㵏缄ꏹ=1, \u2a43坤琖蕤餥퉌\ue352\uea45=1," +
                " \u2b63鸂\uf637=1, ᅺ\u33b2酓됽캆=1, \u2aec㖊\uaaf0짎\ue9fd=1, 䱸覚돘=1, ܓሮ鄒\uf878=1}",
                5.00299199999616
        );
        stringSubsetsAtLeast_int_helper(
                32,
                8,
                "[ܓᅺሮᤘ\u28de\u2aec\u2b63\u2e24\u33b2㖊㱉䇺䟆䱸全尩泦煖疜覚误輮鄒酓鸂鸅\ua7b1ꪻ\uaaf0굿돘됽띯빅빮짎캆\ue2da" +
                "\ue5cb\ue78f\ue8b2\ue9fd\uecf5\uee1c\uf637\uf878聆\uff03, ឃ\u3353刓嗏瀵컦\ud805\ue2d3," +
                " B\u0964\u0d5b\u0e77\u2a57\u337d㽿䍾䞂嗮徜慚睸糦緜萋詪詵錹鎿鐳颒鰫ꅪꯃ묆쪡총\ud8ca\udcc6\udd42\udec6\uf329" +
                "\uf36bﻧ, \u2293䴻庺槔横駆ꎤ퉐\ued0d," +
                " ᯖ\u202c㝏䃼䦣儖匀낛띆\ue9c6\uea55ﱓ, Ȟͺұډ\u0a49ලხ\u124e㲜䔾䠬䮼叙嶂繲葒蛕ꪪ췴턞\ue28c\ue852\uefc8," +
                " ᖒ\u2e64㥑㿘䁅䝀偘滞燔趵ꎿ\uab6e년믱젯\ue89b, ᶙ\u26f2㙴䁩聂ꃖꐲ\udc58," +
                " ᅒ\u2320\u2606\u32b0㕯婜狙阏陜頵顓ꎚ궥댲뚗쇻쓎쟒쳮, \u0618\u19de\u25ad\u2613戯旞韕똽," +
                " \u0d47\u0f1bᓑ\u2eea㚏䢗帇晸濙犋瞁鈖ꤥ죴쿕퀯퓰\uebc7\uf800," +
                " а\u21c0\u2684\u2b83撻梏沅羾葆ꎠ궺놂뗢뵪쒞쥶팗풬\ue40c," +
                " \u073d\u0aa9ஆ\u0b8c\u1069ቾ\u1739គᥔ\u19b1\u1a60\u1aba\u1b56ᳯᶝ\u2026\u2a33\u2af8\u2f0d\u3243㥐㭍㻶㼰" +
                "㾸䅁䈏䦼䨺\u4df4乃侒偰剹厖叵呼団姕嬵悲挑摡枧柿桶泈狚睗窀箴糘茞蕕閦顆飰館鳙鳸\ua6f4Ꜣ\ua7afꯈ냥닁듂뼄뾁쀁쁳쑹운쥇챲춮칗" +
                "탰헑\ud845\ude93\ue10d\ue480\ue531\ue649\ue672\uecda\ued1a\uf351\uf3a8," +
                " ʵۮܒ\u07aa\u0875\u17cbᰁ\u1c47\u1fce\u25a0\u291e\u2e50\u2fbeㆲ㗨㙿㢍䒁䒉嘟墺廏操民滎瀉爑甍皑秳稔臙艸芦藟詂" +
                "謳貗頔\ua837놿뇐멖뮣벧샣솄톋퍆\udde1\ue22c\uecda\uee3a\uee84\uf2ff\uf6cd," +
                " K\u0529Ꮃᶑ㓆䌚䜐卩嘂壵显禎细鏌阘鳰Ꜯ\ua9f4갭쉙쬋쵕춢후\uda6c\ude3a\ue61e\ue9d5\uf04f\uf6a5\ufe3d," +
                " ᬱ㨘昺炣玉虹뭇뾂," +
                " 7Ϯ\u0c00\u0f24Ᏺᚅ\u1debど㖃㥥㴯㺸䌕䎌䜜則勪懜戎担敉杁欎溯潵焣穣笨绡缂葑頒鱼鲦齞걓겧렔몔씅익쯹츪클퇢횋\udd06" +
                "\ude35\ue47a\ue498\ue59b串聾ﶯ, \u0482\u2153耕茟詴ꏠꜰ쵕쾭\ud847\uef98\uf7cd," +
                " \u0361\u0cecᒚ\u21ba\u2818\u2b5dⲄⴋⴓ㺱㻞巶忢憊椼檌檮滙玌璎疟祍蠛裤ꚸ뤀먛\ud85c\udbf4\uec15," +
                " Ȣⱊ\u2f43僮眏軷霺겴뜓좲퐗햽\ue41d\uf5e2, ...]",
                "{ܓᅺሮᤘ\u28de\u2aec\u2b63\u2e24\u33b2㖊㱉䇺䟆䱸全尩泦煖疜覚误輮鄒酓鸂鸅\ua7b1ꪻ\uaaf0굿돘됽띯빅빮짎캆\ue2da" +
                "\ue5cb\ue78f\ue8b2\ue9fd\uecf5\uee1c\uf637\uf878聆\uff03=1, ឃ\u3353刓嗏瀵컦\ud805\ue2d3=1," +
                " B\u0964\u0d5b\u0e77\u2a57\u337d㽿䍾䞂嗮徜慚睸糦緜萋詪詵錹鎿鐳颒鰫ꅪꯃ묆쪡총\ud8ca\udcc6\udd42\udec6\uf329" +
                "\uf36bﻧ=1, \u2293䴻庺槔横駆ꎤ퉐\ued0d=1," +
                " ᯖ\u202c㝏䃼䦣儖匀낛띆\ue9c6\uea55ﱓ=1, Ȟͺұډ\u0a49ලხ\u124e㲜䔾䠬䮼叙嶂繲葒蛕ꪪ췴턞\ue28c\ue852\uefc8=1," +
                " ᖒ\u2e64㥑㿘䁅䝀偘滞燔趵ꎿ\uab6e년믱젯\ue89b=1, ᶙ\u26f2㙴䁩聂ꃖꐲ\udc58=1," +
                " ᅒ\u2320\u2606\u32b0㕯婜狙阏陜頵顓ꎚ궥댲뚗쇻쓎쟒쳮=1, \u0618\u19de\u25ad\u2613戯旞韕똽=1}",
                31.99690200002153
        );
        stringSubsetsAtLeast_int_fail_helper(5, 5);
        stringSubsetsAtLeast_int_fail_helper(4, 5);
    }

    private static void cartesianProduct_helper(
            @NotNull String xss,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.cartesianProduct(readIntegerListWithNullsListsWithNulls(xss)))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private static void cartesianProduct_fail_helper(@NotNull String xss) {
        try {
            toList(P.cartesianProduct(readIntegerListWithNullsListsWithNulls(xss)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testCartesianProduct() {
        cartesianProduct_helper(
                "[[0]]",
                "[[0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0]," +
                " [0], ...]",
                "{[0]=1000000}"
        );
        cartesianProduct_helper(
                "[[null]]",
                "[[null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null]," +
                " [null], [null], [null], [null], [null], [null], [null], [null], ...]",
                "{[null]=1000000}"
        );
        cartesianProduct_helper(
                "[[0, 1]]",
                "[[1], [1], [1], [0], [1], [1], [0], [1], [1], [1], [1], [1], [1], [1], [1], [1], [0], [1], [1]," +
                " [0], ...]",
                "{[0]=500035, [1]=499965}"
        );
        cartesianProduct_helper(
                "[[0, 1], [2, 3]]",
                "[[1, 3], [1, 2], [1, 3], [0, 3], [1, 3], [1, 3], [1, 3], [1, 3], [0, 3], [1, 2], [1, 3], [0, 2]," +
                " [1, 2], [1, 3], [1, 2], [0, 2], [1, 3], [0, 3], [1, 2], [1, 3], ...]",
                "{[0, 3]=250821, [1, 3]=249924, [1, 2]=249737, [0, 2]=249518}"
        );
        cartesianProduct_helper(
                "[[1], [1], [1]]",
                "[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]," +
                " [1, 1, 1], [1, 1, 1], ...]",
                "{[1, 1, 1]=1000000}"
        );
        cartesianProduct_helper(
                "[[null, null, null]]",
                "[[null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null]," +
                " [null], [null], [null], [null], [null], [null], [null], [null], ...]",
                "{[null]=1000000}"
        );
        cartesianProduct_helper(
                "[[0, 1, 2], [-3, -4], [null, 10]]",
                "[[1, -4, 10], [2, -4, 10], [0, -4, 10], [1, -4, 10], [1, -3, 10], [1, -3, 10], [1, -3, null]," +
                " [2, -4, 10], [1, -3, null], [2, -4, 10], [0, -4, 10], [2, -4, 10], [2, -3, 10], [1, -3, null]," +
                " [2, -4, null], [1, -3, null], [1, -3, 10], [2, -4, 10], [2, -3, null], [2, -3, 10], ...]",
                "{[0, -3, 10]=83829, [2, -4, 10]=83612, [2, -4, null]=83541, [1, -4, null]=83393, [1, -3, 10]=83378," +
                " [0, -3, null]=83325, [0, -4, 10]=83211, [0, -4, null]=83194, [1, -4, 10]=83192, [2, -3, 10]=83182}"
        );
        cartesianProduct_helper(
                "[[0, 1], [0, 1], [0, 1]]",
                "[[1, 1, 1], [0, 1, 1], [0, 1, 1], [1, 1, 1], [1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 0], [1, 0, 1]," +
                " [1, 1, 0], [0, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 0], [1, 1, 0], [0, 0, 1], [0, 1, 0], [0, 1, 0]," +
                " [1, 1, 0], [1, 1, 0], ...]",
                "{[1, 1, 0]=125742, [1, 0, 1]=125113, [0, 0, 0]=125087, [1, 0, 0]=124954, [1, 1, 1]=124816," +
                " [0, 1, 1]=124787, [0, 1, 0]=124781, [0, 0, 1]=124720}"
        );
        cartesianProduct_fail_helper("[]");
        cartesianProduct_fail_helper("[[], [1, 2]]");
        cartesianProduct_fail_helper("[[1, 2], null]");
    }

    private static void repeatingIterables_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        aeqitLimit(TINY_LIMIT, map(Testing::its, P.withScale(scale).repeatingIterables(input)), output);
        P.reset();
    }

    @Test
    public void testRepeatingIterables() {
        repeatingIterables_helper(
                2,
                P.positiveIntegersGeometric(),
                "[[44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, ...]," +
                " [38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, ...]," +
                " [14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, ...]," +
                " [1, 25, 1, 25, 1, 25, 1, 25, 1, 25, 1, 25, 1, 25, 1, 25, 1, 25, 1, 25, ...]," +
                " [33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, ...]," +
                " [16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, ...]," +
                " [19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, ...]," +
                " [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, ...]," +
                " [24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, ...]," +
                " [13, 11, 104, 20, 23, 13, 11, 104, 20, 23, 13, 11, 104, 20, 23, 13, 11, 104, 20, 23, ...]," +
                " [46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, ...]," +
                " [63, 10, 70, 32, 66, 63, 10, 70, 32, 66, 63, 10, 70, 32, 66, 63, 10, 70, 32, 66, ...]," +
                " [51, 5, 9, 39, 12, 51, 5, 9, 39, 12, 51, 5, 9, 39, 12, 51, 5, 9, 39, 12, ...]," +
                " [18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, ...]," +
                " [76, 31, 50, 76, 31, 50, 76, 31, 50, 76, 31, 50, 76, 31, 50, 76, 31, 50, 76, 31, ...]," +
                " [3, 43, 22, 3, 43, 22, 3, 43, 22, 3, 43, 22, 3, 43, 22, 3, 43, 22, 3, 43, ...]," +
                " [135, 59, 135, 59, 135, 59, 135, 59, 135, 59, 135, 59, 135, 59, 135, 59, 135, 59, 135, 59, ...]," +
                " [7, 2, 17, 7, 2, 17, 7, 2, 17, 7, 2, 17, 7, 2, 17, 7, 2, 17, 7, 2, ...]," +
                " [6, 53, 6, 53, 6, 53, 6, 53, 6, 53, 6, 53, 6, 53, 6, 53, 6, 53, 6, 53, ...]," +
                " [54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, ...], ...]"
        );
        repeatingIterables_helper(
                4,
                P.positiveIntegersGeometric(),
                "[[44, 26, 15, 2, 27, 33, 16, 20, 5, 24, 14, 19, 11, 44, 26, 15, 2, 27, 33, 16, ...]," +
                " [4, 91, 23, 46, 64, 70, 32, 66, 51, 10, 9, 39, 25, 4, 91, 23, 46, 64, 70, 32, ...]," +
                " [12, 1, 76, 13, 31, 50, 12, 1, 76, 13, 31, 50, 12, 1, 76, 13, 31, 50, 12, 1, ...]," +
                " [3, 38, 22, 135, 7, 59, 17, 6, 3, 38, 22, 135, 7, 59, 17, 6, 3, 38, 22, 135, ...]," +
                " [53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, ...]," +
                " [40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, ...]," +
                " [34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, ...]," +
                " [28, 35, 63, 28, 35, 63, 28, 35, 63, 28, 35, 63, 28, 35, 63, 28, 35, 63, 28, 35, ...]," +
                " [100, 68, 100, 68, 100, 68, 100, 68, 100, 68, 100, 68, 100, 68, 100, 68, 100, 68, 100, 68, ...]," +
                " [81, 36, 75, 21, 29, 81, 36, 75, 21, 29, 81, 36, 75, 21, 29, 81, 36, 75, 21, 29, ...]," +
                " [18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, ...]," +
                " [65, 87, 97, 45, 60, 41, 42, 120, 30, 69, 105, 80, 43, 129, 89, 139, 113, 65, 87, 97, ...]," +
                " [71, 58, 71, 58, 71, 58, 71, 58, 71, 58, 71, 58, 71, 58, 71, 58, 71, 58, 71, 58, ...]," +
                " [88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, 88, ...]," +
                " [141, 72, 57, 54, 77, 61, 140, 90, 141, 72, 57, 54, 77, 61, 140, 90, 141, 72, 57, 54, ...]," +
                " [73, 67, 56, 73, 67, 56, 73, 67, 56, 73, 67, 56, 73, 67, 56, 73, 67, 56, 73, 67, ...]," +
                " [112, 85, 112, 85, 112, 85, 112, 85, 112, 85, 112, 85, 112, 85, 112, 85, 112, 85, 112, 85, ...]," +
                " [168, 47, 86, 168, 47, 86, 168, 47, 86, 168, 47, 86, 168, 47, 86, 168, 47, 86, 168, 47, ...]," +
                " [62, 74, 62, 74, 62, 74, 62, 74, 62, 74, 62, 74, 62, 74, 62, 74, 62, 74, 62, 74, ...]," +
                " [55, 82, 116, 48, 79, 145, 83, 99, 37, 98, 102, 101, 117, 96, 55, 82, 116, 48, 79, 145, ...], ...]"
        );
        repeatingIterables_helper(
                8,
                P.positiveIntegersGeometric(),
                "[[44, 26, 15, 2, 27, 33, 16, 20, 5, 24, 14, 19, 11, 44, 26, 15, 2, 27, 33, 16, ...]," +
                " [4, 91, 23, 46, 64, 70, 32, 66, 51, 10, 9, 39, 25, 4, 91, 23, 46, 64, 70, 32, ...]," +
                " [12, 3, 1, 76, 13, 31, 50, 22, 135, 7, 59, 17, 6, 53, 54, 12, 3, 1, 76, 13, ...]," +
                " [40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, 40, 49, ...]," +
                " [34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, 34, 8, ...]," +
                " [28, 35, 63, 100, 68, 81, 36, 75, 21, 29, 18, 28, 35, 63, 100, 68, 81, 36, 75, 21, ...]," +
                " [65, 87, 97, 45, 60, 41, 42, 120, 30, 69, 105, 80, 43, 129, 89, 139, 113, 65, 87, 97, ...]," +
                " [71, 88, 141, 57, 77, 61, 140, 90, 73, 67, 72, 56, 38, 112, 85, 58, 168, 47, 86, 62, ...]," +
                " [116, 48, 79, 145, 83, 116, 48, 79, 145, 83, 116, 48, 79, 145, 83, 116, 48, 79, 145, 83, ...]," +
                " [99, 37, 98, 102, 101, 117, 96, 94, 134, 104, 127, 99, 37, 98, 102, 101, 117, 96, 94, 134, ...]," +
                " [128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128," +
                " 128, ...]," +
                " [170, 115, 109, 78, 167, 143, 174, 131, 170, 115, 109, 78, 167, 143, 174, 131, 170, 115, 109, 78," +
                " ...]," +
                " [191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191, 191," +
                " 191, ...]," +
                " [84, 124, 103, 144, 92, 151, 125, 84, 124, 103, 144, 92, 151, 125, 84, 124, 103, 144, 92, 151," +
                " ...]," +
                " [110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110," +
                " 110, ...]," +
                " [180, 93, 158, 123, 161, 169, 200, 136, 180, 93, 158, 123, 161, 169, 200, 136, 180, 93, 158, 123," +
                " ...]," +
                " [121, 148, 119, 164, 107, 149, 95, 106, 108, 138, 201, 122, 121, 148, 119, 164, 107, 149, 95," +
                " 106, ...]," +
                " [130, 114, 226, 130, 114, 226, 130, 114, 226, 130, 114, 226, 130, 114, 226, 130, 114, 226, 130," +
                " 114, ...]," +
                " [150, 198, 118, 229, 196, 150, 198, 118, 229, 196, 150, 198, 118, 229, 196, 150, 198, 118, 229," +
                " 196, ...]," +
                " [154, 111, 160, 146, 132, 165, 126, 155, 194, 154, 111, 160, 146, 132, 165, 126, 155, 194, 154," +
                " 111, ...], ...]"
        );
    }

    private static void repeatingIterablesDistinctAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        aeqitLimit(
                TINY_LIMIT,
                map(Testing::its, P.withScale(scale).repeatingIterablesDistinctAtLeast(minSize, input)),
                output
        );
        P.reset();
    }

    private static void repeatingIterablesDistinctAtLeast_fail_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input
    ) {
        try {
            P.withScale(scale).repeatingIterablesDistinctAtLeast(minSize, input);
            fail();
        } catch (IllegalStateException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testRepeatingIterablesDistinctAtLeast() {
        repeatingIterablesDistinctAtLeast_helper(
                3,
                2,
                P.positiveIntegersGeometric(),
                "[[44, 38, 44, 38, 44, 38, 44, 38, 44, 38, 44, 38, 44, 38, 44, 38, 44, 38, 44, 38, ...]," +
                " [15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, ...]," +
                " [27, 32, 27, 32, 27, 32, 27, 32, 27, 32, 27, 32, 27, 32, 27, 32, 27, 32, 27, 32, ...]," +
                " [2, 14, 20, 2, 14, 20, 2, 14, 20, 2, 14, 20, 2, 14, 20, 2, 14, 20, 2, 14, ...]," +
                " [5, 24, 5, 24, 5, 24, 5, 24, 5, 24, 5, 24, 5, 24, 5, 24, 5, 24, 5, 24, ...]," +
                " [19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, ...]," +
                " [104, 23, 46, 64, 70, 66, 104, 23, 46, 64, 70, 66, 104, 23, 46, 64, 70, 66, 104, 23, ...]," +
                " [51, 39, 25, 12, 18, 76, 51, 39, 25, 12, 18, 76, 51, 39, 25, 12, 18, 76, 51, 39, ...]," +
                " [13, 30, 13, 30, 13, 30, 13, 30, 13, 30, 13, 30, 13, 30, 13, 30, 13, 30, 13, 30, ...]," +
                " [50, 22, 50, 22, 50, 22, 50, 22, 50, 22, 50, 22, 50, 22, 50, 22, 50, 22, 50, 22, ...]," +
                " [135, 3, 59, 135, 3, 59, 135, 3, 59, 135, 3, 59, 135, 3, 59, 135, 3, 59, 135, 3, ...]," +
                " [7, 31, 17, 6, 7, 31, 17, 6, 7, 31, 17, 6, 7, 31, 17, 6, 7, 31, 17, 6, ...]," +
                " [33, 54, 40, 33, 54, 40, 33, 54, 40, 33, 54, 40, 33, 54, 40, 33, 54, 40, 33, 54, ...]," +
                " [49, 35, 49, 35, 49, 35, 49, 35, 49, 35, 49, 35, 49, 35, 49, 35, 49, 35, 49, 35, ...]," +
                " [8, 11, 8, 11, 8, 11, 8, 11, 8, 11, 8, 11, 8, 11, 8, 11, 8, 11, 8, 11, ...]," +
                " [34, 63, 100, 68, 34, 63, 100, 68, 34, 63, 100, 68, 34, 63, 100, 68, 34, 63, 100, 68, ...]," +
                " [81, 36, 81, 36, 81, 36, 81, 36, 81, 36, 81, 36, 81, 36, 81, 36, 81, 36, 81, 36, ...]," +
                " [75, 16, 28, 29, 65, 41, 75, 16, 28, 29, 65, 41, 75, 16, 28, 29, 65, 41, 75, 16, ...]," +
                " [87, 96, 87, 96, 87, 96, 87, 96, 87, 96, 87, 96, 87, 96, 87, 96, 87, 96, 87, 96, ...]," +
                " [45, 60, 42, 120, 69, 45, 60, 42, 120, 69, 45, 60, 42, 120, 69, 45, 60, 42, 120, 69, ...], ...]"
        );
        repeatingIterablesDistinctAtLeast_helper(
                5,
                3,
                P.positiveIntegersGeometric(),
                "[[44, 26, 15, 2, 27, 33, 16, 20, 5, 24, 14, 44, 26, 15, 2, 27, 33, 16, 20, 5, ...]," +
                " [19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, 4, 19, 9, ...]," +
                " [104, 3, 23, 46, 64, 70, 32, 66, 51, 10, 39, 25, 12, 18, 1, 76, 104, 3, 23, 46, ...]," +
                " [13, 50, 22, 135, 7, 59, 31, 17, 6, 53, 54, 40, 13, 50, 22, 135, 7, 59, 31, 17, ...]," +
                " [49, 30, 35, 8, 28, 11, 49, 30, 35, 8, 28, 11, 49, 30, 35, 8, 28, 11, 49, 30, ...]," +
                " [34, 29, 63, 100, 68, 81, 36, 34, 29, 63, 100, 68, 81, 36, 34, 29, 63, 100, 68, 81, ...]," +
                " [75, 65, 41, 87, 75, 65, 41, 87, 75, 65, 41, 87, 75, 65, 41, 87, 75, 65, 41, 87, ...]," +
                " [97, 99, 45, 97, 99, 45, 97, 99, 45, 97, 99, 45, 97, 99, 45, 97, 99, 45, 97, 99, ...]," +
                " [60, 42, 120, 69, 60, 42, 120, 69, 60, 42, 120, 69, 60, 42, 120, 69, 60, 42, 120, 69, ...]," +
                " [105, 80, 43, 105, 80, 43, 105, 80, 43, 105, 80, 43, 105, 80, 43, 105, 80, 43, 105, 80, ...]," +
                " [129, 89, 139, 113, 129, 89, 139, 113, 129, 89, 139, 113, 129, 89, 139, 113, 129, 89, 139, 113," +
                " ...], [71, 58, 88, 71, 58, 88, 71, 58, 88, 71, 58, 88, 71, 58, 88, 71, 58, 88, 71, 58, ...]," +
                " [141, 72, 21, 57, 77, 61, 140, 141, 72, 21, 57, 77, 61, 140, 141, 72, 21, 57, 77, 61, ...]," +
                " [90, 91, 73, 67, 90, 91, 73, 67, 90, 91, 73, 67, 90, 91, 73, 67, 90, 91, 73, 67, ...]," +
                " [56, 38, 112, 85, 168, 56, 38, 112, 85, 168, 56, 38, 112, 85, 168, 56, 38, 112, 85, 168, ...]," +
                " [47, 86, 62, 47, 86, 62, 47, 86, 62, 47, 86, 62, 47, 86, 62, 47, 86, 62, 47, 86, ...]," +
                " [74, 55, 52, 82, 116, 48, 79, 74, 55, 52, 82, 116, 48, 79, 74, 55, 52, 82, 116, 48, ...]," +
                " [145, 83, 37, 98, 102, 145, 83, 37, 98, 102, 145, 83, 37, 98, 102, 145, 83, 37, 98, 102, ...]," +
                " [101, 117, 96, 94, 134, 101, 117, 96, 94, 134, 101, 117, 96, 94, 134, 101, 117, 96, 94, 134, ...]," +
                " [127, 126, 170, 127, 126, 170, 127, 126, 170, 127, 126, 170, 127, 126, 170, 127, 126, 170, 127," +
                " 126, ...], ...]"
        );
        repeatingIterablesDistinctAtLeast_helper(
                32,
                5,
                P.positiveIntegersGeometric(),
                "[[44, 15, 2, 27, 33, 16, 20, 5, 24, 14, 19, 11, 4, 104, 23, 46, 64, 70, 32, 66, ...]," +
                " [7, 59, 17, 6, 53, 54, 40, 49, 34, 35, 8, 28, 7, 59, 17, 6, 53, 54, 40, 49, ...]," +
                " [63, 100, 68, 81, 36, 75, 21, 29, 65, 41, 87, 97, 45, 60, 42, 120, 30, 69, 105, 80, ...]," +
                " [102, 101, 117, 96, 94, 134, 127, 128, 170, 115, 109, 78, 167, 143, 174, 131, 191, 84, 124, 103, " +
                "...]," +
                " [130, 114, 226, 150, 198, 118, 130, 114, 226, 150, 198, 118, 130, 114, 226, 150, 198, 118, 130," +
                " 114, ...]," +
                " [229, 196, 154, 111, 160, 146, 132, 165, 126, 155, 194, 159, 224, 179, 133, 153, 192, 267, 188," +
                " 142, ...]," +
                " [203, 204, 173, 213, 209, 242, 172, 189, 178, 193, 218, 182, 176, 163, 171, 166, 202, 227, 252," +
                " 214, ...]," +
                " [195, 237, 308, 187, 162, 294, 206, 304, 256, 190, 197, 205, 222, 243, 183, 186, 241, 281, 211," +
                " 208, ...]," +
                " [185, 264, 400, 232, 236, 215, 295, 262, 235, 276, 223, 233, 219, 266, 210, 240, 185, 264, 400," +
                " 232, ...]," +
                " [277, 254, 263, 359, 247, 293, 238, 250, 216, 319, 366, 314, 271, 277, 254, 263, 359, 247, 293," +
                " 238, ...]," +
                " [225, 287, 318, 248, 255, 246, 274, 270, 231, 341, 217, 289, 257, 239, 249, 260, 283, 284, 273," +
                " 269, ...]," +
                " [336, 282, 251, 258, 356, 261, 323, 230, 244, 253, 301, 296, 290, 298, 265, 285, 309, 305, 291," +
                " 280, ...]," +
                " [300, 297, 317, 286, 325, 272, 360, 345, 313, 324, 275, 306, 300, 297, 317, 286, 325, 272, 360," +
                " 345, ...]," +
                " [379, 299, 322, 343, 303, 302, 335, 316, 327, 331, 461, 315, 404, 394, 379, 299, 322, 343, 303," +
                " 302, ...]," +
                " [344, 425, 350, 279, 312, 333, 353, 344, 425, 350, 279, 312, 333, 353, 344, 425, 350, 279, 312," +
                " 333, ...]," +
                " [292, 330, 328, 364, 320, 362, 326, 307, 311, 332, 292, 330, 328, 364, 320, 362, 326, 307, 311," +
                " 332, ...]," +
                " [321, 386, 367, 334, 524, 347, 376, 389, 339, 383, 346, 357, 375, 369, 455, 337, 352, 321, 386," +
                " 367, ...]," +
                " [373, 354, 419, 338, 358, 387, 349, 407, 355, 377, 373, 354, 419, 338, 358, 387, 349, 407, 355," +
                " 377, ...]," +
                " [351, 372, 363, 412, 441, 340, 429, 351, 372, 363, 412, 441, 340, 429, 351, 372, 363, 412, 441," +
                " 340, ...]," +
                " [348, 365, 388, 506, 378, 392, 380, 381, 348, 365, 388, 506, 378, 392, 380, 381, 348, 365, 388," +
                " 506, ...], ...]"
        );
        repeatingIterablesDistinctAtLeast_fail_helper(1, 1, P.positiveIntegers());
        repeatingIterablesDistinctAtLeast_fail_helper(1, -1, P.positiveIntegers());
    }

    private static void sublists_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.sublists(readIntegerListWithNulls(input))));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testSublists() {
        sublists_helper(
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        sublists_helper(
                "[1, 2, 3, 4]",
                "[[2], [2, 3, 4], [], [], [], [4], [2], [1, 2], [], [2], [1, 2], [3], [3, 4], [1, 2, 3], [4], []," +
                " [], [3, 4], [1], [2, 3, 4], ...]",
                "{[]=332665, [4]=67092, [2, 3]=67014, [3, 4]=66993, [1, 2]=66874, [3]=66850, [1, 2, 3, 4]=66738," +
                " [2]=66555, [1]=66531, [2, 3, 4]=66518}"
        );
        sublists_helper(
                "[1, null, 3, 4]",
                "[[null], [null, 3, 4], [], [], [], [4], [null], [1, null], [], [null], [1, null], [3], [3, 4]," +
                " [1, null, 3], [4], [], [], [3, 4], [1], [null, 3, 4], ...]",
                "{[]=332665, [4]=67092, [null, 3]=67014, [3, 4]=66993, [1, null]=66874, [3]=66850," +
                " [1, null, 3, 4]=66738, [null]=66555, [1]=66531, [null, 3, 4]=66518}"
        );
        sublists_helper(
                "[3, 1, 4, 1]",
                "[[1], [1, 4, 1], [], [], [], [1], [1], [3, 1], [], [1], [3, 1], [4], [4, 1], [3, 1, 4], [1], []," +
                " [], [4, 1], [3], [1, 4, 1], ...]",
                "{[]=332665, [1]=133647, [1, 4]=67014, [4, 1]=66993, [3, 1]=66874, [4]=66850, [3, 1, 4, 1]=66738," +
                " [3]=66531, [1, 4, 1]=66518, [3, 1, 4]=66170}"
        );
        sublists_helper(
                "[1, 1, 1, 1]",
                "[[1], [1, 1, 1], [], [], [], [1], [1], [1, 1], [], [1], [1, 1], [1], [1, 1], [1, 1, 1], [1], []," +
                " [], [1, 1], [1], [1, 1, 1], ...]",
                "{[]=332665, [1]=267028, [1, 1]=200881, [1, 1, 1]=132688, [1, 1, 1, 1]=66738}"
        );
    }

    private static void substrings_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.substrings(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testSubstrings() {
        substrings_helper("", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        substrings_helper(
                "abcd",
                "[b, bcd, , , , d, b, ab, , b, ab, c, cd, abc, d, , , cd, a, bcd, ...]",
                "{=332665, d=67092, bc=67014, cd=66993, ab=66874, c=66850, abcd=66738, b=66555, a=66531, bcd=66518}"
        );
        substrings_helper(
                "aaaa",
                "[a, aaa, , , , a, a, aa, , a, aa, a, aa, aaa, a, , , aa, a, aaa, ...]",
                "{=332665, a=267028, aa=200881, aaa=132688, aaaa=66738}"
        );
        substrings_helper(
                "Mississippi",
                "[issi, , si, ssis, , Mississipp, si, ssissippi, i, ssiss, is, is, pp, sippi, , Mis, ippi, sis, si," +
                " ss, ...]",
                "{=153654, s=51343, i=50882, ss=26001, is=25767, p=25756, si=25753, ssi=25718, iss=25591, issi=25454}"
        );
    }

    private static void listsWithElement_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsWithElement(x, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsWithElement_helper_uniform(
            int scale,
            @Nullable Integer x,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        listsWithElement_helper(
                scale,
                x,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void listsWithElement_fail_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input
    ) {
        try {
            toList(P.withScale(scale).listsWithElement(x, input));
            fail();
        } catch (IllegalStateException | NoSuchElementException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testListsWithElement() {
        listsWithElement_helper_uniform(
                3,
                0,
                "[1, 2, 3]",
                "[[2, 1, 2, 0, 2, 2, 3, 2, 3, 1, 3, 2], [1, 3, 0, 3, 2], [0], [1, 3, 0, 2], [0], [3, 0, 1, 3]," +
                " [3, 0, 3], [0, 3], [0], [0], [0], [0], [0], [2, 0], [3, 3, 2, 3, 0], [1, 2, 0]," +
                " [2, 3, 2, 1, 2, 1, 0, 3, 2, 3], [2, 3, 3, 0], [0], [0], ...]",
                "{[0]=249571, [0, 2]=41857, [3, 0]=41798, [2, 0]=41752, [0, 3]=41627, [0, 1]=41496, [1, 0]=41418," +
                " [2, 2, 0]=7145, [1, 2, 0]=7142, [2, 0, 2]=7079}",
                3.001751999985313
        );
        listsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 3]",
                "[[2, 2, 2, 2, -5, 2], [3, 2, 2, 3, -5], [3, 2, -5, 1, 3, 1], [2, 1, -5, 3, 3, 3], [2, 1, 3, -5, 1]," +
                " [1, -5, 1], [2, 2, 1, -5, 2, 3, 2, 3, 2, 3, 2], [-5, 2], [2, 3, 3, 1, 1, 3, -5], [-5]," +
                " [3, 2, 3, 1, 3, 3, 3, 1, -5, 2, 3, 2, 3], [-5, 3], [1, -5, 1, 1, 1], [3, 3, 1, 1, 3, -5, 1]," +
                " [-5, 3], [-5, 1, 3, 2, 2], [-5, 3, 2, 3, 1], [3, -5, 3, 1, 2, 1, 1, 2, 2, 1], [1, 2, -5], [-5]," +
                " ...]",
                "{[-5]=111111, [-5, 2]=24723, [-5, 3]=24630, [3, -5]=24513, [-5, 1]=24494, [1, -5]=24481," +
                " [2, -5]=24476, [1, -5, 3]=5614, [-5, 3, 2]=5606, [-5, 2, 2]=5589}",
                5.007290999999437
        );
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, 2, 3]",
                "[[3, 2, 3, 1, 3, 3, 3, 2, 3, 3, 1, 2, 1, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 3, 2, 1, 2, 2, 1, 3, 3, " +
                "2, 3, 3, 2, 3, 1, 2, 3, 2, 3, 2, 3, 2, 1, 2, 1, 2, 3, 3, 2, 3, null, 3, 3, 2, 3, 3]," +
                " [3, 1, 3, 2, 1, 1, 3, 3, 2, 1, 1, 1, 1, 3, 3, 2, 3, 3, 1, 3, 3, 1, 1, 3, 3, 1, 1, 1, 2, 1, 3, 1, " +
                "null, 3, 1, 2, 1, 1, 2, 2, 1, 3, 3, 1, 1, 2, 1, 1], [1, 1, 1, 3, 2, 3, 1, 1, null, 3, 2]," +
                " [2, 3, 3, null, 3]," +
                " [1, 1, 3, 2, 2, 3, 1, 2, 2, 2, 1, 1, 2, 3, 2, 3, 3, 1, 1, 1, 1, 1, null, 3, 3, 1, 3, 3]," +
                " [3, 3, 2, 3, 3, 2, 1, 3, 2, 2, null, 2, 2, 2, 3, 2, 2, 2, 3, 3, 3, 2, 3, 3, 1, 2, 1, 1, 2, 1, 1, " +
                "3, 3, 1, 3, 2, 1, 3, 2, 1, 3, 1, 3, 3, 2, 2, 1, 1, 1, 2, 2, 1, 3, 2, 1, 1, 3, 2]," +
                " [null, 1, 1, 1, 3, 2, 2, 1, 2, 2, 1, 3, 3, 3]," +
                " [3, 2, 2, 3, 2, 3, 3, 3, 2, 2, 3, null, 2, 2, 2, 3, 2, 1, 3, 3, 2], [3, null]," +
                " [3, 3, 2, 1, 3, 1, 2, 3, 1, 1, 2, 3, 2, 3, 2, 3, 1, 1, 1, 3, 2, 1, 2, 3, 3, 2, 1, null, 3]," +
                " [1, 3, 2, 3, 3, null, 2, 3, 2, 3, 2, 1, 1, 3, 1, 2, 3, 1]," +
                " [2, 1, 2, 1, 1, 3, 1, 2, 2, 1, 1, 1, 1, 2, 3, 2, 3, 1, 2, 3, 2, 2, 3, 3, 2, 3, 2, 1, 3, 2, 1, 2, " +
                "1, 2, 1, 3, 2, 3, 3, 3, 1, null, 2, 1, 2, 1], [2, 1, null, 1]," +
                " [1, 1, 3, 1, 3, 3, 2, 3, 2, 1, 3, 2, 1, 1, 3, 1, 2, 1, 1, 1, 2, 1, 3, 2, 3, 2, 2, 1, 2, 1, 1, 3, " +
                "2, 3, 1, 3, null, 1, 3, 1, 1, 2]," +
                " [1, 3, 1, null, 1, 1, 3, 1, 2, 1, 2, 2, 1, 3, 3, 2, 3, 2, 3, 3, 2, 2, 1, 2, 2, 1, 3, 3, 3, 3, 2, " +
                "1, 2, 3]," +
                " [null, 3, 3, 1, 2, 2, 3, 3, 2, 1, 3, 3, 2, 1, 2, 1, 1, 1, 3, 2, 3, 2, 3, 1, 1, 1, 2, 2, 3]," +
                " [2, 3, 2, 3, 3, 3, 2, 2, 2, 1, 2, 1, 2, null, 1, 1, 3, 3, 1, 3, 3, 2, 2, 3, 2]," +
                " [2, 1, 1, null, 2, 2, 2, 1, 3, 1, 3, 1, 2]," +
                " [3, 1, 3, 2, 1, 2, 1, 1, 2, 2, 2, 2, 3, 1, 3, 3, 3, 1, 2, 3, 3, 2, 2, 2, 3, 2, 2, 2, 3, 2, null, " +
                "3, 1, 2, 2, 3, 1, 2, 3, 2, 3, 2, 3, 3, 1, 1, 2, 2, 1, 3, 2, 3, 2, 1, 2, 3, 3, 3, 1]," +
                " [1, 2, 3, 1, 2, 2, 3, 2, 3, 3, 2, 3, 2, 2, 1, 1, 2, null, 2, 1, 2, 3, 3, 2, 1, 2, 3, 3, 3, 3, 3, " +
                "1, 2, 3, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1], ...]",
                "{[null]=3618, [null, 3]=1196, [2, null]=1193, [null, 1]=1172, [3, null]=1168, [1, null]=1135," +
                " [null, 2]=1089, [null, 1, 1]=412, [null, 2, 2]=391, [null, 3, 1]=390}",
                31.993837000022335
        );
        listsWithElement_helper_uniform(
                3,
                0,
                "[1, null, 3]",
                "[[null, 1, null, 0, null, null, 3, null, 3, 1, 3, null], [1, 3, 0, 3, null], [0], [1, 3, 0, null]," +
                " [0], [3, 0, 1, 3], [3, 0, 3], [0, 3], [0], [0], [0], [0], [0], [null, 0], [3, 3, null, 3, 0]," +
                " [1, null, 0], [null, 3, null, 1, null, 1, 0, 3, null, 3], [null, 3, 3, 0], [0], [0], ...]",
                "{[0]=249571, [0, null]=41857, [3, 0]=41798, [null, 0]=41752, [0, 3]=41627, [0, 1]=41496," +
                " [1, 0]=41418, [null, null, 0]=7145, [1, null, 0]=7142, [null, 0, null]=7079}",
                3.001751999985313
        );
        listsWithElement_helper_uniform(
                5,
                -5,
                "[1, null, 3]",
                "[[null, null, null, null, -5, null], [3, null, null, 3, -5], [3, null, -5, 1, 3, 1]," +
                " [null, 1, -5, 3, 3, 3], [null, 1, 3, -5, 1], [1, -5, 1]," +
                " [null, null, 1, -5, null, 3, null, 3, null, 3, null], [-5, null], [null, 3, 3, 1, 1, 3, -5], [-5]," +
                " [3, null, 3, 1, 3, 3, 3, 1, -5, null, 3, null, 3], [-5, 3], [1, -5, 1, 1, 1]," +
                " [3, 3, 1, 1, 3, -5, 1], [-5, 3], [-5, 1, 3, null, null], [-5, 3, null, 3, 1]," +
                " [3, -5, 3, 1, null, 1, 1, null, null, 1], [1, null, -5], [-5], ...]",
                "{[-5]=111111, [-5, null]=24723, [-5, 3]=24630, [3, -5]=24513, [-5, 1]=24494, [1, -5]=24481," +
                " [null, -5]=24476, [1, -5, 3]=5614, [-5, 3, null]=5606, [-5, null, null]=5589}",
                5.007290999999437
        );
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, null, 3]",
                "[[3, 3, 1, 3, 3, 3, 3, 3, 1, 1, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 3, 1, 1, 3, 3, 3, 3, 3, 1, 3, 3, " +
                "3, 1, 1, 3, 3, 3, 1, 3, 3, 1, 1, 3, 1, 1, 1, 3, 3, 3, 3, 3, 3, null, 3, 1, 3, null, 1, 1, 3, 3, nu" +
                "ll, 1, 1, 1], [null, 3, 3, 3, null, 1, 1, 3, null, null, 1, 3, null, 3, null, 1]," +
                " [3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 3, 3, 3, 1, 3, null, 3, 1, 1, 3, null, 3, 1, null]," +
                " [3, 3, 1, 1, 1, 3, 1, null, null, null, null, 1, 1, null, 3, null], [1, 1, 3, 1, 1, 3, null]," +
                " [3, 3, 3, null, null, null, null, 3, null, null, null, 3, 3, 3, null, 3, 3, 1, null, 1, 1, null, " +
                "1, 1, 3, 3, 1, 3, null, 1, 3, null, 1, 3, 1, 3, 3, null, null, 1, 1, 1, null, null, 1, 3, null, 1," +
                " 1, 3, null, 1, 1, 1, 3, null, null, 1, null, null, 1]," +
                " [1, 1, 1, 3, 1, 1, 3, 3, 3, 3, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, null, null, null, null, 3, nul" +
                "l, 1, 3, 3, null], [3, null]," +
                " [3, 3, 1, 3, 1, 3, 1, 1, 3, 3, 3, 1, 1, 1, 3, 1, 3, 3, 1, 1, 3, 3, 1, 3, 3, 1, 3, null, null, 3, " +
                "null, 3, null, 1, 1, 3, 1, null, 3, 1, 1, null, 3]," +
                " [1, 1, 1, 3, 1, 1, 1, 1, 1, 3, 3, 1, 3, 3, 3, 3, 1, 3, 1, 1, 1, 3, 3, 3, 3, 1, 3, 1, 3, 1, 1, 1, " +
                "3, 3, 1, 1, 1, null, 1]," +
                " [1, 1, 3, 1, 3, 3, 3, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 3, 3, 3, 3, 1, 3, 1, " +
                "1, 3, 1, 1, null, 1, 1, 3, 1, null, 1, null, null, 1, 3, 3, null, 3, null, 3, 3, null, null, 1, nu" +
                "ll, null, 1, 3, 3, 3, 3, null, 1, null, 3, 1, 1, 3, 3, null, null]," +
                " [1, null, 3, 3, 1, null, null, 3, 3, null, 1, 3, 3, null, 1, null, 1, 1, 1, 3, null, 3, null, 3]," +
                " [3, 3, 3, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 3, 3, 1, 1, 1, 3, 1, 1, null, null, 1, 1, null, null, nul" +
                "l, 3, 3, null, 1]," +
                " [1, 3, 1, 3, 1, 1, 3, 3, 1, null, 3, 1, null, null, 3, 1, null, 3, null, 3, null, 3, 3, 1, 1, nul" +
                "l, null, 1, 3, null, 3, null, 1, null, 3, 3, 3, 1, 1, 3, null, 1, 3, 3, 1, 1, 3, 1, 1, null, 3, 1," +
                " 1, null, 3, 1, null, null, 3, null, 3, 3, null, 3, null, null, 1, 1, null, 1, 3, 3]," +
                " [3, 3, 3, 1, 1, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 3, 3, " +
                "1, 3, 1, 3, 3, 3, 1, 1, 1, 1, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 1, 3, 3, 1, 1, null, null, null, 1]," +
                " [1, 1, null, 1, null, null, 1, 1, null, 3, null, 1, null, null, 3, 1, 1, null, null, 3, 1, null, " +
                "null], [1, 1, 1, null, 1, null, 3, 1, 1, null, 3, null, 1, null, 3, 3, 1, 3, 3, null, 1, 3, 3]," +
                " [3, 1, 3, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, null, 3, null, 1]," +
                " [1, 3, 3, 3, 1, 3, 1, 1, 1, null, 1, 3, 3, null, null]," +
                " [1, 1, 3, 1, 3, 1, 1, 3, 3, 3, 1, 3, 1, 1, 3, 1, 1, 3, 3, 3, 3, 1, 1, 3, null, 3, 1, 3, 1, null, " +
                "null, null, 3, 1, null, null, 1, null, null, 1, 1], ...]",
                "{[null]=3619, [1, null]=1781, [3, null]=1730, [null, null]=1195, [null, 1]=1123, [null, 3]=1122," +
                " [3, 3, null]=844, [1, 1, null]=837, [1, 3, null]=804, [3, 1, null]=782}",
                32.00126100002188
        );
        listsWithElement_helper_uniform(
                3,
                0,
                "[1, 2, 2, 4]",
                "[[2, 4, 1, 0, 2, 2, 2, 4, 2, 2, 1, 4, 2], [1, 2, 4, 0, 4], [4, 0, 2], [1, 2, 0, 2], [0], [4, 0, 2]," +
                " [1, 0], [2, 0, 2], [0, 2], [0], [0], [0], [0], [0], [4, 0, 2, 4, 2], [2, 0], [2, 0, 1, 2]," +
                " [0, 2, 4, 4, 2, 2, 1], [4, 2, 4, 2, 4, 0], [2, 0, 2, 2], ...]",
                "{[0]=249337, [2, 0]=62653, [0, 2]=62593, [1, 0]=31457, [0, 4]=31319, [0, 1]=31317, [4, 0]=31097," +
                " [2, 0, 2]=15872, [0, 2, 2]=15686, [2, 2, 0]=15367}",
                3.0022749999853073
        );
        listsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 2, 4]",
                "[[2, 2, 4, 4, -5, 2, 2, 2], [4, 2, -5, 2, 4, 2], [-5, 1, 2, 4, 1, 2], [2, -5]," +
                " [2, 4, 2, -5, 2, 1, 2, 2], [-5], [1, -5, 1], [2, 4, 2, -5], [2, 2, 4, 4, 4, 4, 2, -5, 4, 4, 4, 4]," +
                " [4, -5, 2, 2, 4, 4, 2, 1, 1], [1, -5], [4, 2, 2, 4, 4, 2, 4, 4, -5], [2, 4, 4, -5, 2, 2, 2]," +
                " [2, -5], [1, 2, -5, 1, 1], [-5, 2, 2, 1, 1, 2], [1, -5], [2, -5], [1, 2, 2, 2, -5]," +
                " [2, 2, 2, 1, -5, 2], ...]",
                "{[-5]=111075, [-5, 2]=36857, [2, -5]=36751, [-5, 4]=18603, [1, -5]=18543, [4, -5]=18329," +
                " [-5, 1]=18312, [-5, 2, 2]=12568, [2, 2, -5]=12426, [2, -5, 2]=12267}",
                5.005771999999611
        );
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, 2, 2, 4]",
                "[[4, 4, 2, 2, 4, 2, 1, 2, 4, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 4, 2, 4, 4, " +
                "4, 2, 1, 2, 4, 2, 4, 1, 2, 2, 2, 2, 2, 4, 2, 2, 1, 2, 2, 4, 4, null, 2, 2, 2, 4, 2, 2, 4, 4, 4, 2," +
                " 2, 2, 1, 4, 2, 2]," +
                " [2, 1, 2, 4, 2, 4, 1, 1, 2, 4, 2, 4, 2, 1, 1, 1, 1, 4, 2, 4, 4, 2, null, 2, 2, 2, 2, 1, 1, 2, 2, " +
                "2, 1, 2, 2, 2]," +
                " [2, 1, 2, 1, 1, 2, 4, 2, 1, 2, 4, 2, 1, 1, 4, 2, 1, 1, 1, null, 2, 1, 4, 4, 1, 2, 2, 2, 4, 4]," +
                " [null, 2, 4]," +
                " [1, 4, 4, 4, 4, 4, 1, 4, 2, 2, 2, 4, 2, 1, 2, 2, 4, 2, 1, 1, 4, null, 4, 1, 1, 4, 2, 1, 4]," +
                " [2, 4, 2, null]," +
                " [2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, null, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 4, 2, 1, 2, " +
                "1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 4, 1, 2, 2, 1, 2, 1, 2, 2, 2, 4, 4, 2, 1, 1, 1, 2, 2]," +
                " [4, 1, 4, 2, null, 1, 1, 4, 1, 2, 4, 2, 2, 4, 4, 1, 2, 2, 1, 4]," +
                " [2, 2, 2, 2, 2, 2, 4, 2, 2, 4, 2, 4, 2, 2, null, 2, 4, 2, 2, 2, 4, 2, 1, 2]," +
                " [2, 1, 2, null, 2, 2, 2, 1, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 2, 1, 4, 1, 2, 2, 2, 1, 2, 2, 4, 4, 2, " +
                "2, 2, 2, 4]," +
                " [null, 2, 2, 4, 1, 1, 4, 4, 1, 1, 4, 2, 2, 2, 2, 1, 4, 4, 2, 2, 2, 4, 2, 2, 2, 4, 2, 4, 2, 2, 1, " +
                "2, 2, 1, 4, 2, 1], [4, 2, 4, 4, 1, 2, 1, 2, 2, 1, 4, 2, 1, 2, null, 4]," +
                " [1, 1, 2, 1, 2, 4, 2, 2, 2, 2, 1, 2, 4, 2, 1, 4, 1, 4, 2, 4, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2, 4, " +
                "2, 1, 2, 1, 1, null, 1, 2, 4, 1, 1, 4, 2, 4]," +
                " [1, 4, null, 4, 4, 1, 1, 2, 1, 2, 1, 2, 2, 4, 4, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 2, 4, 1, 4, 4, " +
                "2, 2, 4]," +
                " [1, 2, 2, 2, 4, 4, 2, 2, 1, 1, 1, 2, null, 2, 2, 1, 2, 2, 2, 2, 2, 4, 4, 1, 2, 2, 2, 1, 2, 4, 1, " +
                "4, 4], [null, 2, 4, 2, 2, 2, 4, 4, 4, 2, 2, 2, 2]," +
                " [1, 4, 4, 4, 1, 4, 4, 2, 4, 2, 4, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1, 1, 4, 2, 2, 2, 2, 2, 2, " +
                "4, null, 4, 2, 4, 4, 2, 2]," +
                " [2, 1, 4, 2, 2, 1, null, 4, 2, 1, 2, 2, 2, 1, 4, 2, 2, 2, 2, 2, 2, 2, 1, 4, 4, 1, 2, 2, 1, 4, 2, " +
                "4, 2, 4, 2, 2, 1, 2, 2, 2, 2, 4, 1, 1, 2, 2, 4, 1, 2, 4, 2, 1, 4, 1, 2, 1, 1, 2, 2, 4, 1, 1, 4, 2," +
                " 2, 4, 1, 2, 2]," +
                " [2, 1, 2, 2, 2, 4, 2, 2, 4, 2, 1, 2, 1, 1, 2, null, 2, 1, 2, 2, 2, 2, 1, 2, 4, 2, 4, 2, 4, 2, 2, " +
                "4, 2, 4, 1, 4, 2, 2, 1, 4, 1, 2, 4], [4, null], ...]",
                "{[null]=3631, [null, 2]=1732, [2, null]=1688, [2, null, 2]=873, [4, null]=852, [null, 1]=849," +
                " [1, null]=843, [null, 4]=824, [2, 2, null]=805, [null, 2, 2]=764}",
                32.023569000021205
        );
        listsWithElement_helper(
                3,
                0,
                P.withScale(4).positiveIntegersGeometric(),
                "[[3, 10, 7, 0, 5], [0, 10, 1], [0, 3], [7, 0], [5, 0, 3], [0], [0], [0], [0], [5, 0, 9, 15, 6, 12]," +
                " [4, 0], [0], [0], [0], [8, 9, 0], [0], [0, 2, 6, 2, 5], [0], [3, 0], [0], ...]",
                "{[0]=250126, [1, 0]=31309, [0, 1]=31288, [2, 0]=23468, [0, 2]=23259, [0, 3]=17630, [3, 0]=17611," +
                " [4, 0]=13170, [0, 4]=13054, [5, 0]=9983}",
                2.999244999985283
        );
        listsWithElement_helper(
                5,
                -5,
                P.withScale(4).positiveIntegersGeometric(),
                "[[10, 7, 7, 4, -5, 1, 3, 3, 2, 7], [2, 3, 1, 2, 1, 2, -5], [5, 9, 15, -5, 12], [1, 2, 1, -5]," +
                " [9, 4, 7, 6, 2, 5, 1, 6, -5], [-5], [3, 1, 5, 1, 1, -5, 2], [1, 4, 5, 4, -5, 12], [2, -5]," +
                " [4, 1, -5, 1], [-5, 2, 1, 3, 1, 5], [-5, 3], [5, 4, 4, -5, 7, 4, 1, 6, 7], [1, 10, 1, -5], [-5]," +
                " [-5, 2], [4, 13, -5, 10, 3, 1, 4, 19, 5], [6, 2, 4, 1, 22, 17, -5, 1], [1, -5, 4, 3], [7, -5], ...]",
                "{[-5]=110706, [1, -5]=18421, [-5, 1]=18318, [-5, 2]=13979, [2, -5]=13741, [-5, 3]=10447," +
                " [3, -5]=10342, [4, -5]=7772, [-5, 4]=7707, [5, -5]=5977}",
                5.003905999999416
        );
        listsWithElement_helper(
                32,
                null,
                P.withScale(4).positiveIntegersGeometric(),
                "[[7, 8, 2, 3, 1, 2, 1, 2, 1, 8, 5, 9, 15, 6, 12, 6, 1, 2, 1, 1, 13, 9, 4, 7, 6, 2, 5, 1, 6, 1, 1, " +
                "1, 10, 3, 1, 5, 1, 1, 4, 2, 5, 1, 4, 5, 4, 2, 12, 2, 2, 1, 4, 4, 1, null]," +
                " [1, 3, 1, 5, 1, 2, 3, 6, null, 4, 9], [1, 6, 7, 5, 1, 10, 1, 1, 1, 2, null, 4, 13, 7, 10, 3]," +
                " [17, 2, 1, 2, 1, 3, 4, 3, 2, 7, 1, 1, 3, 4, 2, 4, 1, 1, 4, 3, 2, 1, 1, 19, 1, 1, 2, 7, 3, 14, 2, " +
                "20, 4, 1, 2, 5, 2, 8, 5, 2, 7, 3, 4, 13, 1, 4, 3, 7, 2, 7, 1, 1, 6, 5, 2, 4, 4, 3, 1, 5, 3, 2, 4, " +
                "10, 8, 1, 2, 6, 2, 5, null, 4, 1, 3, 1, 6, 15, 3, 3, 2, 6, 4, 3, 5, 2, 3, 3, 2, 4, 2, 6, 11, 2, 18" +
                ", 1]," +
                " [2, 7, 2, 1, 6, 9, 3, 1, 3, 1, 6, 1, 4, 5, 6, 6, 1, 1, 3, 2, 10, 2, 7, null, 3, 1, 2, 2, 5, 13, 6" +
                ", 8, 4], [1, 1, 10, 4, 1, 5, 5, 1, null, 3, 8, 4, 3, 3, 1, 6, 2, 1]," +
                " [15, 2, 4, 2, 3, 2, 10, 3, 2, 2, 1, 1, 4, 7, 7, 3, 2, null, 9, 2, 2, 2, 4, 2, 3, 1, 1]," +
                " [4, 4, 2, 1, 7, 6, 15, 2, 2, 4, 4, 4, 1, 1, 7, 4, 6, 3, null, 3, 4, 9, 3, 3, 8]," +
                " [1, 5, 17, 1, 2, 9, 2, 1, 2, 1, 2, 9, 5, 3, 8, 3, 15, 8, 2, 5, 12, 4, 2, null, 2, 1]," +
                " [2, 8, 2, 4, 1, 4, 8, 2, 3, 2, 3, 5, 3, 4, 2, 3, 3, 2, 1, 1, 4, 3, null, 1, 4, 1, 5, 8, 2, 5, 7, " +
                "2, 2, 2, 10, 13, 6, 5, 3, 3, 4]," +
                " [2, 3, null, 1, 1, 2, 1, 2, 2, 8, 1, 8, 1, 1, 7, 4, 2, 1, 3, 2, 8, 1, 7, 11, 6, 4, 3, 1]," +
                " [3, 1, 8, 7, 3, null, 6]," +
                " [1, 3, 11, 1, 2, 4, 2, 2, 8, 7, 4, null, 4, 2, 1, 3, 1, 3, 5, 1, 4, 2, 5, 3, 2, 5, 5, 3, 2, 1, 2]," +
                " [8, 1, 1, 3, 6, 4, 13, 3, 8, 10, 3, 1, 2, 1, null, 6, 5, 9, 1, 8, 1]," +
                " [7, 3, 12, 6, null, 1, 6, 6, 9, 6, 2, 10, 3, 2, 4, 3, 9, 2, 2, 3]," +
                " [11, 2, 4, 7, 6, 1, 6, 3, 2, 2, 1, 2, 2, 1, 2, 1, 13, 2, 4, 5, 3, 1, 6, 1, 3, 14, 4, 1, null, 5, " +
                "4, 10, 5, 8, 17, 5, 1, 11, 1, 2, 4, 3, 1, 5, 7, 1], [4, 2, 6, 6, null, 2, 4, 4]," +
                " [1, 6, 4, 1, 5, 3, null, 1, 5, 3, 3, 4, 2, 1, 16, 1, 4, 7, 4, 5, 4, 2, 5, 1, 2, 8, 2, 2, 8]," +
                " [6, 8, 3, 2, 5, 12, 4, 2, 1, 2, 9, 6, 8, 2, 2, 3, 8, 1, 9, 4, 10, 2, 3, 1, 1, 6, 5, 6, 1, 2, 3, 1" +
                "1, 6, 4, 5, null], [1, 1, 5, 5, 1, 1, 1, 4, 6, 2, 1, 2, 8, 3, 9, 8, 8, null, 17, 1, 7, 2, 3, 7, 1]" +
                ", ...]",
                "{[null]=3674, [1, null]=868, [null, 1]=845, [2, null]=682, [null, 2]=642, [3, null]=486," +
                " [null, 3]=478, [4, null]=386, [null, 4]=364, [5, null]=295}",
                32.001327000020574
        );
        listsWithElement_helper(
                3,
                0,
                repeat(1),
                "[[1, 1, 1, 0, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1], [1, 1, 0], [1, 0, 1, 1, 1], [0]," +
                " [1, 1, 0, 1, 1], [1, 1, 0], [1, 1, 0], [0, 1], [1, 0], [1, 0, 1, 1], [1, 1, 0], [0, 1], [0, 1]," +
                " [0], [1, 0], [0], [0], [0], [0], ...]",
                "{[0]=250050, [1, 0]=125653, [0, 1]=124331, [1, 1, 0]=62471, [1, 0, 1]=62445, [0, 1, 1]=62377," +
                " [0, 1, 1, 1]=31539, [1, 1, 0, 1]=31263, [1, 0, 1, 1]=31206, [1, 1, 1, 0]=31114}",
                2.999981999985526
        );
        listsWithElement_helper(
                5,
                -5,
                repeat(1),
                "[[1, 1, 1, 1, -5, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, -5, 1, 1, 1, 1], [1, 1, -5, 1, 1, 1, 1, 1]," +
                " [-5, 1], [1, 1, -5, 1], [1, 1, 1, -5, 1, 1, 1, 1, 1, 1], [1, -5, 1, 1], [-5, 1], [-5, 1]," +
                " [-5, 1, 1, 1], [1, 1, -5, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, -5, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, -5, 1, 1, 1], [-5, 1], [-5], [1, 1, 1, 1, 1, 1, 1, 1, -5, 1, 1, 1]," +
                " [1, 1, 1, -5, 1, 1, 1, 1], [1, 1, 1, 1, -5, 1], [1, 1, -5], [1, 1, 1, -5], ...]",
                "{[-5]=111514, [-5, 1]=74118, [1, -5]=73884, [-5, 1, 1]=49434, [1, 1, -5]=49139, [1, -5, 1]=49064," +
                " [1, 1, 1, -5]=32992, [1, -5, 1, 1]=32987, [1, 1, -5, 1]=32911, [-5, 1, 1, 1]=32654}",
                5.0037189999994975
        );
        listsWithElement_helper(
                32,
                null,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1," +
                " 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, null, 1, 1, 1, 1, 1, 1], [1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "null, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, null, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, " +
                "1], [1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, null, 1]," +
                " [1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, " +
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, 1, 1, 1], ...]",
                "{[null]=3569, [1, null]=3438, [null, 1]=3407, [1, null, 1]=3282, [null, 1, 1]=3212," +
                " [1, 1, null]=3188, [1, null, 1, 1]=3044, [1, 1, 1, null]=3024, [null, 1, 1, 1]=2998" +
                ", [1, 1, null, 1]=2972}",
                31.985562000021005
        );
        listsWithElement_fail_helper(5, null, Collections.emptyList());
        listsWithElement_fail_helper(5, null, Arrays.asList(1, 2, 3));
        listsWithElement_fail_helper(2, null, P.integers());
    }

    private static void stringsWithChar_char_String_helper(
            int scale,
            char c,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithChar(c, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithChar_char_String_fail_helper(int scale, char c, @NotNull String input) {
        try {
            P.withScale(scale).stringsWithChar(c, input);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsWithChar_char_String() {
        stringsWithChar_char_String_helper(
                3,
                'b',
                "abcd",
                "[dadbbbcdbc, bb, acbdd, bcd, ab, cb, dbb, ab, cbc, bc, b, b, b, b, b, dbbdb, cb, cbab, bbddcba," +
                " ddcdcbb, ...]",
                "{b=249549, db=42139, cb=41763, ab=41712, ba=31351, bb=31320, bc=31249, bd=30856, cab=7056, acb=7016}",
                3.0011149999852065
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "abcd",
                "[bbdd#bbc, dc#cdb, #acdab, b#, cdc#bacc, #, a#a, bdb#, bcddddb#dddd, d#bcddcaa, a#, dcbddcdd#," +
                " bdd#bcb, c#, ac#aa, #ccaac, a#, c#, acbb#, cbca#c, ...]",
                "{#=111075, #d=18603, a#=18543, #b=18469, b#=18429, #c=18388, d#=18329, c#=18322, #a=18312, #bb=3248}",
                5.005771999999611
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "abcd",
                "[ddcbdcacdccbccabaccaacaacaacdcdddbabdbdaccbccdbcabcdd ccbdccdddccbadcb," +
                " cacdbdaacdcdbaaaadcddc cccbaacbbacbc, cabaabdbacdcaadbaaa caddacbcdd,  cd," +
                " adddddadcbbdcabbdbaad daadcad, cdc , ccbccbacbbcb bdbbcbbbcccdbcdcabaabaaccacbdacbacaccbddbaaabb," +
                " dadc aadacdbbddabbad, cbbcbcdccdbdbc bdbbcdbac, cab cbcacbaacbccacbadabcbacbddbcbbd," +
                "  bbdaaddaadbcbcaddbcbdbccdbdcbacbadba, dbddabaccadbab d," +
                " aacacdcbcbacdbadadcdabaaabacbcbdbabaa acdaadbd, ad ddaacababbddaccbcbccbddbdaddbbd," +
                " accbddbcaaab ccabbccbddaccbabdadd,  bdcbcdddccbb, adddaddcdcdaccbbcbabcabaadbbbccbd dbddbb," +
                " badcca dcabbcadbcbcbccaddabbadcdbdcbabcccdaacbdacdcadacaabcdaadbcdabb," +
                " baccbdbbdcacaac babccbabdcdcdccdcdadbcadabd, d , ...]",
                "{ =3631,  b=880, b =858, d =852,  c=852,  a=849, a =843, c =830,  d=824,  dd=229}",
                32.023569000021205
        );
        stringsWithChar_char_String_helper(
                3,
                'b',
                "aaaa",
                "[aaabaaaaaaaaa, aaaba, aba, aaba, b, aba, ab, aba, ba, b, b, b, b, b, abaaa, ab, abaa, baaaaaa," +
                " aaaaab, abaa, ...]",
                "{b=249337, ba=125229, ab=125207, baa=62633, aba=62599, aab=62095, aaab=31559, baaa=31254," +
                " aaba=31075, abaa=31033}",
                3.0022749999853073
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "aaaa",
                "[aaaa#aaa, aa#aaa, #aaaaa, a#, aaa#aaaa, #, a#a, aaa#, aaaaaaa#aaaa, a#aaaaaaa, a#, aaaaaaaa#," +
                " aaa#aaa, a#, aa#aa, #aaaaa, a#, a#, aaaa#, aaaa#a, ...]",
                "{#=111075, #a=73772, a#=73623, aa#=49634, #aa=49416, a#a=49351, a#aa=32949, #aaa=32912, aaa#=32899" +
                ", aa#a=32804}",
                5.005771999999611
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "aaaa",
                "[aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaa aaaaaaaaaa,  aa," +
                " aaaaaaaaaaaaaaaaaaaaa aaaaaaa, aaa , aaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaa aaaaaaaaaaaaaaa, aaaaaaaaaaaaaa aaaaaaaaa, aaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                "  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaaaaaaaaaaaaa a," +
                " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaa, aa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaa,  aaaaaaaaaaaa, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaa," +
                " aaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa," +
                " aaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaa, a , ...]",
                "{ =3631,  a=3405, a =3383, a a=3275, aa =3201,  aa=3143, aaa =3107, a aa=3030, aa a=3018," +
                " aaa a=2965}",
                32.023569000021205
        );
        stringsWithChar_char_String_helper(
                3,
                'b',
                "Mississippi",
                "[spsbsisisssi, isb, isbs, b, sbs, sbi, ib, ib, b, b, b, b, bs, pssb, sbMs, bssspis, isbs, psbip, b," +
                " b, ...]",
                "{b=249684, bs=45808, sb=45691, ib=45560, bi=45265, pb=22818, bp=22593, Mb=11495, bM=11394, bii=8360}",
                2.9995019999853154
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "Mississippi",
                "[ssii#si, ssi#i, issss#, s#sss, iMpi#, #p, pss#ssssss, isii#psiiip, i#, siisisis#, iisM#i, i#MMs," +
                " ssps#i, s#, spis#psp, i#Miiiis, #is, #, #sMpip, #MsM, ...]",
                "{#=111441, #i=26925, s#=26908, #s=26892, i#=26543, p#=13406, #p=13247, #M=6776, M#=6711, #is=6630}",
                5.0064659999996515
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "Mississippi",
                "[sisssssisipiMpippipsssiMpsssispsMssssssssssspisiisiss spiss," +
                " sMisiiisiMMssssssssspsisispsMiip MiiiispspisM, pipMisss s,  spssi, iiiipsssiMip issMiisMsiM," +
                " sssspispsssi iipsspsissisisMipMpissiiMspispiiMMiipiiiMsis, pp psisiisppiii," +
                " pssiiispsssiss siississp, M isipMpssissipssMpssiMiiipssppps," +
                " spspsMssiipisisispipisssspissisiippssssssiisMsis s, s i," +
                " iiispsiiispiMipsiisiMsiisiisssipiiMMs pipppMspsisiisMispssiipspiMisisMpiiisi," +
                " ssMsspisispissMpsisspipiisisiipip ," +
                " iisiisspispsiiisMipiipsiiippppsiiMiiispiMipss ispiMiissisMiMsiiisppsispisiiispippiiMMssiiiisispMs" +
                "Msspipisppps, ssssiMiMssiMiiiissspispiiMMMsiiipippiipi isspp, isMisiisispipisisMpMs ," +
                " iispsissspiiisssii , ispsissiipspisipspisssipspsiispp sM," +
                " iisMisiMpMipMiisMsisiisipsssipMipspi Mssiipsip, psipssMsspippspssMss ips, ...]",
                "{ =3670,  i=1285, s =1285,  s=1282, i =1232, p =596,  p=588, s i=458, si =455, i s=453}",
                31.994617000022732
        );
        stringsWithChar_char_String_fail_helper(2, ' ', "abc");
    }

    private static void stringsWithChar_char_helper(
            int scale,
            char c,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithChar(c)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithChar_char_fail_helper(int scale, char c) {
        try {
            P.withScale(scale).stringsWithChar(c);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsWithChar_char() {
        stringsWithChar_char_helper(
                3,
                'b',
                "[ε䊿\u2538b\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ, ᬜK㵏b㩷, 纫b䝲, 坤琖b\uea45, b, \u2b63b鸅, \uee1cb, ᅺb䇺, b㖊, b," +
                " b, b, b, b, \uff03b尩굿\uecf5, \ue8b2b, 䟆b㭠瀵, b쪅右䦋\u2832ﭙ빜, 홃祝몷ࢦ\ufdd7b, \u0c55b壙\udd82, ...]",
                "{b=248975, 鍻b=10, 㵃b=10, bఘ=10, b腨=10, 喽b=10, 趍b=10, 圍b=10, b㭐=10, b\u18af=9}",
                3.0022749999853757
        );
        stringsWithChar_char_helper(
                5,
                '#',
                "[\u31e5髽肣\uf6ff#\udd15몱ﲦ, 刿ㄾ#K㵏ꏹ, #坤琖\u2a43퉌\uea45, 餥#, \u33b2酓캆#\ue9fd\u2aec㖊짎, #," +
                " 䱸#\uf878, 尩굿\uecf5#, 瀵컦刓嗏\u3353\ue2d3\ud805#䫯噋\uf36fꌻ, 홃#壙\udd82픫鼧\u061a\u2e94穨, 䥔#," +
                " ꯃ慚총\u0e77\uf36bB㽿\u2a57#, \u337d萋\u0d5b#쪡詪쀝, 駆#, 槔横#渀䃼, #ᗶ\u1ad6仈만ᑒ, 퉌#, \u2506#," +
                " 㲜\ue852ډұ#, ხ叙繲\uefc8#䔾, ...]",
                "{#=111130, #\u0978=7, #ᵻ=7, 齉#=7, 䯟#=7, \u02d9#=7, #ȡ=7, #\u0817=7, #ｫ=7, 壼#=7}",
                5.005778999999451
        );
        stringsWithChar_char_helper(
                32,
                ' ',
                "[\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03" +
                "띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f㱉泦㭠瀵컦刓嗏 糦嗮\uf329ﻧ\udd42䞂鎿鐳鰫묆颒錹睸ꯃ慚총," +
                " 駆퉐庺\u2293\ued0d䴻ꎤ槔横靯ढ\u0dd7\u0b7d袬渀䃼匀낛띆ﱓ㝏ᯖ ͺ\u124eꪪ\u0a49䠬㲜\ue852ډұ\ue28c葒ලȞ," +
                " \uab6e䝀㥑\u2e64년믱젯䁅偘滞\ue89bᖒ㿘燔ꎿ趵ꑼ딀㙴 쳮陜阏顓\u2320쓎狙쟒㕯뚗,  旞\u2613," +
                " 죴\u0d47㚏帇퀯\uebc7晸犋鈖ꤥ쿕\u0f1b\u2eea\uf800ᓑ濙䢗瞁퓰诨갇 梏\u2684\ue40c\u2b83葆а팗, 뗢撻뵪 ," +
                " 濆엶䩵ᣞ\ud992\u2f79滔菆\ufe0d砩\udad6昍 헑䈏닁\ue649គ姕\u1069\u2f0d듂狚\ue672団䅁悲枧\u1b56偰摡泈\u1a60㭍" +
                "\u2af8운\u2026桶뼄ቾᶝ睗㥐厖剹ᥔ㻶\uf3a8춮茞\ue531칗ᳯ\u073d飰\ue480\ua6f4\u19b1\u1739," +
                " \u3243\u4df4\u2a33䨺 糘ﱜ\u22a3䐄굲ﱷ\u3291\uf28d즓\uf27f䝨雩\uecb1ᡄ\ude17," +
                " 訾ꉹ\uaa4d藆\ue34d\uf5a2됇\ud9aa撊홳價\u2673휅\udee2 甍뮣民皑\u291e秳ʵ솄퍆," +
                " 滎\u25a0爑 䌚\ufe3d춢후Ꜯ卩鳰阘细\ue9d5\ude3a显鏌㓆갭\uda6cᎳ\ua9f4쉙嘂\uf6a5䜐禎\u0529K쬋壵\ue61e쵕ᶑ" +
                "\uf04f,  씅潵겧\u0f24㺸則穣클䜜걓绡缂敉勪\ue498溯7익Ᏺ㥥㖃ど츪퇢㴯ᚅ\u1deb齞杁鱼欎䌕렔횋葑䎌," +
                " \ue59b聾ﶯ\uddd7ぜＵნ\ue266耒뎀\u2153\uf7cdꜰ耕 \ud847," +
                " 檌裤㻞椼憊ⴋ\u21ba\uec15檮滙\u0cec巶먛㺱\udbf4蠛玌疟ᒚⴓ\u2818\u2b5d\ud85cⲄ뤀\u0361ꚸ璎祍忢\u17cd\uddcf溑鲴" +
                "ᇥ\u3314ﮀ ﰀ\u2572\ueea7䳰뼴Ⴃ跁涫," +
                " 솔\u2fb7 嗏\ue7db榜ᖨ鯊\u2ac8鸭\ue55c\ufe49豙\u302bᨋ奀ꂎ惶\u2669喒谁\u1712\u08ca\uf1d1坏餻䌕ꋋ礰똗銳鼭" +
                "\ue60dዿ," +
                " \ufaf4\ua9e6톞\uf7bd쩷줋飅\u10ce錰ﻈ벬\1 鹢ᐦ盠摅䣵曎똮梩\ue387\u2513墼ᒊ\u3016\udae9識ねꨋ\uf684洓䘃," +
                "  诉氷\ue85a\u21a5퇢頣\uec9b쬋赶砖攝棩, 酔ꓷ䅣杷ﶤ憐㛳紮暓넮腛\u3278㭚滎ᠥ䕉\uf022泱覔밁꾲ꇀ\uea21騘纼\u32c7" +
                "\u0c81\uf05d쿙\uef96挺\ue221愗 铟䙡潛셿항삁, 祱뉈\u07bf探㷖轨 誧\ueb82颌\uf239젝滺\ueb60첇深沢䑍썦ᒡ壞椶" +
                "\ue840\ue357\u1a5b\ue7e0ℵ偍\u009c쐯胚锗뢡㭯澒驙氘䟥蓪촞\ue63e\uf8e3魘䡄鱆䦑쨯شၚ瑫鋒헼\ufb0fᴈ仪漨함䘁\ua9ca" +
                "쫻\ue3b0鸐铓\uf155\udf3e\u2bdbᴴ\ueae1䍽, 냥퇐\u2236펶ߙ톯窱\u1fed离僾냸\u0a7a\ue95cᑌ捖 矱땰叕壟浪띭타嫱뗏먊몿" +
                "蟪礟艮Ū\u1063氶滣㸸\ue367\udfedꔲ鵈ꚇ죬䬍ꦟ, 잋 , ...]",
                "{ =3631, 啲 =3, 招 =3,  쟿=3,  쥧=3, 塬 =2, 謸 =2, 뇃 =2, ߏ =2,  \u2f1e=2}",
                32.02361800002122
        );
        stringsWithChar_char_fail_helper(2, ' ');
    }

    private static void subsetsWithElement_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsetsWithElement(x, input))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsetsWithElement_helper_uniform(
            int scale,
            @Nullable Integer x,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        subsetsWithElement_helper(
                scale,
                x,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                topSampleCount,
                meanSize
        );
    }

    private static void subsetsWithElement_fail_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input
    ) {
        try {
            toList(P.withScale(scale).subsetsWithElement(x, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsetsWithElement() {
        subsetsWithElement_helper_uniform(
                2,
                0,
                "[1, 2, 3]",
                "[[0, 2], [0, 1, 2, 3], [0], [0], [0], [0, 2, 3], [0], [0], [0, 1, 2], [0, 3], [0], [0], [0, 2]," +
                " [0, 1, 3], [0, 3], [0, 3], [0], [0, 3], [0], [0], ...]",
                "{[0]=499507, [0, 3]=100200, [0, 1]=100121, [0, 2]=99865, [0, 2, 3]=50269, [0, 1, 2, 3]=50235," +
                " [0, 1, 2]=50160, [0, 1, 3]=49643}",
                1.7510349999822503
        );
        subsetsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 3]",
                "[[-5, 2], [-5, 2], [-5, 1, 2, 3], [-5, 1], [-5, 1], [-5, 1, 2, 3], [-5, 2, 3], [-5, 1, 2, 3], [-5]," +
                " [-5, 1, 2, 3], [-5, 1, 3], [-5, 1, 3], [-5, 1, 2, 3], [-5], [-5], [-5, 1, 3], [-5, 1], [-5, 1, 3]," +
                " [-5], [-5, 1, 3], ...]",
                "{[-5, 1, 2, 3]=332308, [-5]=199912, [-5, 2, 3]=83429, [-5, 1, 3]=82869, [-5, 1, 2]=82865," +
                " [-5, 3]=72904, [-5, 1]=72875, [-5, 2]=72838}",
                2.7138669999868528
        );
        subsetsWithElement_helper_uniform(
                32,
                3,
                "[1, 2, 3]",
                "[[1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [3], [1, 2, 3]," +
                " [1, 2, 3], [2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3]," +
                " [1, 2, 3], [1, 2, 3], ...]",
                "{[1, 2, 3]=909921, [3]=31389, [1, 3]=29542, [2, 3]=29148}",
                2.878531999977901
        );
        subsetsWithElement_helper_uniform(
                2,
                0,
                "[1, 2, 2, 4]",
                "[[0, 2], [0], [0, 1, 2, 4], [0, 1, 2], [0, 4], [0, 4], [0, 4], [0, 1, 2], [0, 2], [0], [0], [0, 2]," +
                " [0, 4], [0, 2], [0], [0, 1], [0, 2], [0], [0, 2], [0], ...]",
                "{[0]=499557, [0, 2]=167319, [0, 4]=71464, [0, 1]=71430, [0, 1, 2]=61773, [0, 2, 4]=61661," +
                " [0, 1, 2, 4]=42991, [0, 1, 4]=23805}",
                1.733663999982879
        );
        subsetsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 2, 4]",
                "[[-5, 2], [-5, 2, 4], [-5, 1, 2, 4], [-5, 1, 2], [-5], [-5], [-5, 2], [-5, 2, 4], [-5, 1, 2]," +
                " [-5, 1, 2, 4], [-5, 2, 4], [-5, 1, 2, 4], [-5, 2], [-5], [-5, 1, 2, 4], [-5, 1, 2], [-5, 1, 2, 4]," +
                " [-5, 4], [-5], [-5, 2, 4], ...]",
                "{[-5, 1, 2, 4]=300718, [-5]=200010, [-5, 2]=133219, [-5, 2, 4]=116740, [-5, 1, 2]=116152," +
                " [-5, 1]=49925, [-5, 4]=49800, [-5, 1, 4]=33436}",
                2.6677539999856954
        );
        subsetsWithElement_helper_uniform(
                32,
                3,
                "[1, 2, 2, 4]",
                "[[1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3], [1, 2, 3, 4], [3], [1, 2, 3, 4], [3]," +
                " [1, 2, 3, 4], [1, 2, 3, 4], [2, 3], [2, 3], [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4]," +
                " [1, 2, 3, 4], [1, 2, 3, 4], [1, 2, 3, 4], [2, 3], [1, 2, 3, 4], ...]",
                "{[1, 2, 3, 4]=823110, [1, 2, 3]=43609, [2, 3, 4]=43361, [3]=31317, [2, 3]=29307, [1, 3]=10032," +
                " [3, 4]=9935, [1, 3, 4]=9329}",
                3.711202000016531
        );
        subsetsWithElement_helper(
                2,
                0,
                P.withScale(4).positiveIntegersGeometric(),
                "[[0, 1, 2, 3, 4, 7, 10], [0], [0, 7], [0, 5], [0], [0], [0], [0], [0], [0], [0], [0], [0, 3]," +
                " [0, 5, 6, 9, 15], [0, 12], [0], [0], [0], [0], [0], ...]",
                "{[0]=499603, [0, 1]=71342, [0, 2]=51658, [0, 3]=37660, [0, 4]=27749, [0, 5]=20802, [0, 1, 2]=16906," +
                " [0, 6]=15214, [0, 1, 3]=12259, [0, 7]=11373}",
                1.8810509999810079
        );
        subsetsWithElement_helper(
                5,
                -5,
                P.withScale(4).positiveIntegersGeometric(),
                "[[-5, 7], [-5, 1, 2, 3, 4, 7, 8], [-5, 1, 6, 8, 12, 15], [-5, 2], [-5, 1, 2, 3, 5, 6, 10, 13]," +
                " [-5], [-5, 1, 2, 3, 4, 6, 12], [-5], [-5], [-5], [-5, 1, 2], [-5, 3, 5], [-5, 4]," +
                " [-5, 1, 4, 6, 7, 9], [-5], [-5, 1, 2, 3, 5], [-5, 3, 7, 13], [-5, 4, 10]," +
                " [-5, 1, 2, 3, 4, 17, 19, 22], [-5], ...]",
                "{[-5]=199867, [-5, 1]=49980, [-5, 2]=35180, [-5, 3]=25191, [-5, 1, 2]=22543, [-5, 4]=18409," +
                " [-5, 1, 3]=15351, [-5, 5]=13445, [-5, 1, 2, 3]=13147, [-5, 1, 4]=11025}",
                3.669920999984385
        );
        subsetsWithElement_helper(
                32,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15], [1, 2, 3, 4, 5, 6, 7, 9], [1, 2, 3, 4, 5, 7, 10, 13]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 10, 13, 14, 17, 19, 20], [1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 18]," +
                " [1, 2, 3, 4, 5, 8, 10, 13], [1, 2, 3, 4, 7, 9, 10, 15]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 17], [1, 2, 3, 4, 6, 7, 11], [1, 2, 3, 8]," +
                " [1, 2, 3, 6, 7, 10, 11], [1, 2, 3, 7, 8], [1, 2, 3, 4, 5, 6, 8, 10, 13]," +
                " [1, 2, 3, 5, 6, 7, 8, 9, 12, 16], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 10, 16, 17], [1, 2, 3, 4, 5, 8, 11]," +
                " [1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12], [1, 2, 3, 4, 16], [1, 2, 3, 4, 5, 6], ...]",
                "{[3]=31419, [1, 3]=12349, [1, 2, 3]=9638, [2, 3]=8232, [1, 2, 3, 4, 5, 6, 7, 8, 9]=7495," +
                " [1, 2, 3, 4]=7409, [1, 2, 3, 4, 5, 6, 7, 8]=7163, [1, 2, 3, 4, 5, 6, 7]=6981," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=6925, [1, 2, 3, 4, 5]=6673}",
                8.632959000000918
        );
        subsetsWithElement_helper(
                2,
                0,
                repeat(1),
                "[[0, 1], [0, 1], [0, 1], [0, 1], [0, 1], [0], [0, 1], [0, 1], [0], [0], [0, 1], [0, 1], [0, 1]," +
                " [0], [0, 1], [0], [0], [0, 1], [0, 1], [0], ...]",
                "{[0, 1]=500875, [0]=499125}",
                1.5008749999899669
        );
        subsetsWithElement_helper(
                5,
                -5,
                repeat(1),
                "[[-5, 1], [-5, 1], [-5, 1], [-5, 1], [-5, 1], [-5], [-5, 1], [-5], [-5, 1], [-5, 1], [-5, 1]," +
                " [-5, 1], [-5, 1], [-5, 1], [-5, 1], [-5, 1], [-5], [-5, 1], [-5, 1], [-5, 1], ...]",
                "{[-5, 1]=799806, [-5]=200194}",
                1.799806000002514
        );
        subsetsWithElement_helper(
                32,
                3,
                repeat(1),
                "[[1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3]," +
                " [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], [1, 3], ...]",
                "{[1, 3]=968801, [3]=31199}",
                1.9688010000135663
        );
        subsetsWithElement_fail_helper(5, 0, Collections.emptyList());
        subsetsWithElement_fail_helper(5, 0, Arrays.asList(1, 2, 3));
        subsetsWithElement_fail_helper(1, 0, P.integers());
    }

    private static void stringSubsetsWithChar_char_String_helper(
            int scale,
            char c,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsWithChar(c, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsWithChar_char_String_fail_helper(int scale, char c, @NotNull String input) {
        try {
            P.withScale(scale).stringSubsetsWithChar(c, input);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsWithChar_char_String() {
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "abcd",
                "[b, bd, abcd, b, abcd, bd, bd, bd, abc, bc, b, bc, bd, b, b, b, bd, bc, b, bc, ...]",
                "{b=499234, bc=100943, ab=100118, bd=99936, abcd=50039, abc=49991, abd=49945, bcd=49794}",
                1.7505739999822374
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "abcd",
                "[#b, #bd, #abcd, #ab, #, #, #c, #bcd, #abc, #abcd, #bcd, #abcd, #c, #, #acd, #ac, #abcd, #d, #," +
                " #cd, ...]",
                "{#abcd=200356, #=200010, #acd=50436, #bcd=50299, #c=50105, #abd=49926, #a=49925, #b=49902," +
                " #abc=49828, #d=49800}",
                3.0014489999821183
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "abcd",
                "[ abcd,  abcd,  abcd,  abc,  abcd,  ,  abcd,  ,  abcd,  acd,  c,  c,  abcd,  abcd,  abcd,  abcd," +
                "  abcd,  abcd,  bc,  abcd, ...]",
                "{ abcd=773484,  =31317,  abc=25051,  abd=24894,  bcd=24763,  acd=24732,  a=10032,  b=9969,  d=9935" +
                ",  c=9893}",
                4.543944999926795
        );
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "aaaa",
                "[ab, b, ab, ab, ab, ab, ab, ab, ab, b, b, ab, ab, ab, b, ab, ab, b, ab, b, ...]",
                "{ab=500443, b=499557}",
                1.500442999989859
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "aaaa",
                "[#a, #a, #a, #a, #, #, #a, #a, #a, #a, #a, #a, #a, #, #a, #a, #a, #a, #, #a, ...]",
                "{#a=799990, #=200010}",
                1.7999900000025462
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "aaaa",
                "[ a,  a,  a,  a,  a,  ,  a,  ,  a,  a,  a,  a,  a,  a,  a,  a,  a,  a,  a,  a, ...]",
                "{ a=968683,  =31317}",
                1.9686830000135644
        );
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "Mississippi",
                "[bps, bips, b, bps, bis, bs, b, b, bs, bs, bs, bs, bi, b, bi, b, b, b, b, b, ...]",
                "{b=499907, bs=111412, bi=111205, bis=63811, bp=49643, bips=28080, bps=26542, bip=26154, Mb=24053" +
                ", Mbis=12372}",
                1.7701949999823314
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "Mississippi",
                "[#ps, #i, #Mips, #p, #ps, #s, #Mips, #s, #Mis, #s, #Mis, #ips, #, #Mips, #s, #Mips, #, #, #Mis," +
                " #Ms, ...]",
                "{#=199852, #ips=143859, #Mips=132111, #is=113744, #s=82185, #i=81806, #Mis=51927, #ps=38829," +
                " #ip=38579, #p=33990}",
                2.874729999983963
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "Mississippi",
                "[ Mips,  ips,  Mis,  ips,  ,  s,  Mips,  Mips,  ips,  i,  Mips,  is,  Mis,  Mips,  Mips,  Mips," +
                "  ips,  is,  s,  ips, ...]",
                "{ Mips=679742,  ips=126854,  is=40560,  Mis=32736,  =31294,  s=16993,  i=16988,  ip=11342," +
                "  ps=11221,  p=6667}",
                4.425015999937323
        );
        stringSubsetsWithChar_char_String_fail_helper(1, ' ', "abc");
    }

    private static void stringSubsetsWithChar_char_helper(
            int scale,
            char c,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsWithChar(c)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsWithChar_char_fail_helper(int scale, char c) {
        try {
            P.withScale(scale).stringSubsetsWithChar(c);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsWithChar_char() {
        stringSubsetsWithChar_char_helper(
                2,
                'b',
                "[bε嘩, b, bϡ\u19dc䊿䯏刿罖몱\udd15ﲦ, bᬜKㄾ, b㵏, b㩷, b纫, b䝲坤, b琖, b, b, b\uea45, b\u2b63, b鸅, b," +
                " b\uee1c, bᅺ, b, b䇺, b, ...]",
                "{b=499556, b\uf59a=15, bཛ=14, b釁=14, b嚷=14, b匔=14, b\u20b5=13, b僵=13, b縖=13, b㓬=13}",
                2.0006209999799105
        );
        stringSubsetsWithChar_char_helper(
                5,
                '#',
                "[#\u31e5嘩髽, #肣\udd15, #ᅺᤘ\u2b63\u33b2䇺煖误輮酓鸂鸅됽몱캆\uee1c\uf637, #全\ue9fd, #, #, #覚," +
                " #ሮ尩ꪻ굿\uecf5, #瀵疜컦, #\u061a\u2e94㽖䥔刓嗮壙穨糦鼧픫핀\udd82\uf329ﶼﻧ, #\u0e77慚ꯃ총\udd42," +
                " #\u2293䴻庺槔横靯駆ꎤ퉐\ued0d\uf36b, #ढ, #, #ᑒ䃼拷만, #ͺ\u124e\u2506囀\ue68e, #䔾唯嶂湑猂蹙ꪪ췴턞\uead1ﮍ," +
                " #甧, #, #ᖒ滞\uab6e\ue89b, ...]",
                "{#=200002, #棦=12, #\u2043=11, #农=11, #\ue373=10, #\u32a0=10, #돧=10, #돹=10, #㽚=10, #켓=10}",
                5.005519000008595
        );
        stringSubsetsWithChar_char_helper(
                32,
                ' ',
                "[ ܓᅺሮᤘ\u28de\u2aec\u2b63\u2e24\u33b2㖊㭠㱉䇺䟆䱸全嘩尩泦瀵煖疜覚误輮鄒酓鸂鸅\ua7b1ꪻ\uaaf0굿돘됽띯빅빮짎캆컦" +
                "\ue2da\ue5cb\ue78f\ue8b2\ue9fd\uecf5\uee1c\uf637\uf878聆\uff03," +
                "  B\u0964\u0d5b\u0e77\u1366ᵠ\u2a57\u337d㽿䍾䞂刓嗮徜慚睸糦緜萋詪詵錹鎿鐳颒鰫ꅪꯃ묆쀝쪡총\ud8ca\udcc6\udd42" +
                "\udec6\uf21b\uf329\uf36bﻧ," +
                "  ᯖ\u202c㝏䃼䦣儖匀駆낛띆\ue9c6\uea55ﱓ,  Ȟͺұډ\u0a49ල\u124eᗶ㲜䠬葒蛕ꪪ\ue28c\ue852," +
                "  ᖒ\u2e64㥑㿘䁅䝀䮼偘滞燔趵ꎿ\uab6e년믱젯\ue89b,  ,  ᅒ\u2320\u2606\u32b0㕯婜狙阏陜頵顓ꎚꑼ궥뚗쇻쓎쟒쳮,  ," +
                "  \u0d47\u0f1bᓑ\u2eea㚏䢗帇晸濙犋瞁诨鈖ꤥ갇댲죴쿕퀯퓰\uebc7\uede9\uf800,  а\u2684\u2b83梏葆ꒂ팗풬\ue40c,  궺," +
                "  羾뗢," +
                "  \u073d\u0aa9ஆ\u0b8c\u1069ቾ\u1739គᥔ\u19b1\u1a60\u1aba\u1b56ᳯᶝ\u2026\u22a3\u2a33\u2af8\u2f0d" +
                "\u3243\u3291㥐㭍㻶㼰㾸䅁䈏䐄䝨䦼䨺\u4df4乃侒偰剹厖叵呼団姕嬵悲挑摡枧柿桶泈狚睗窀箴糘茞蕕閦顆飰館鳙鳸\ua6f4Ꜣ" +
                "\ua7afꯈ굲냥놂닁듂뼄뾁쀁쁳쑹운쥇즓챲춮칗탰헑\ud845\ude93\ue10d\ue480\ue531\ue649\ue672\uecda\ued1a\uf27f\uf28d" +
                "\uf351\uf3a8ﱜﱷ,  ʵۮܒ\u07aa\u0875\u17cbᰁ\u1fce\u25a0\u291e\u2e50\u2fbeㆲ㗨㙿㢍䒁䒉嘟廏民滎瀉爑甍皑秳稔臙" +
                "艸芦藟詂謳雩頔\ua837놿뇐멖뮣벧솄톋퍆\udde1\ue22c\uecda\uee3a\uee84\uf6cd," +
                "  K\u0529Ꮃᶑ㓆䌚䜐卩嘂壵操显禎细鏌阘鳰Ꜯ\ua9f4갭넀쉙쬋쵕춢후\uda6c\ude3a\ue61e\ue9d5\uf04f\uf6a5\ufe3d," +
                "  7Ϯ\u0c00\u0f24ნᏲᚅᬱ\u1deb\u2153ぜど㖃㥥㴯㺸䌕䎌䜜則勪懜戎担敉杁欎溯潵焣穣笨绡缂耒葑頒鱼鲦齞걓겧뎀렔몔씅익쯹츪클" +
                "퇢횋\udd06\uddd7\ude35\ue266\ue47a\ue498\ue59b\uf7cd串聾ﶯＵ,  伺ꜰ쾭\ud847\uef98," +
                "  \u0361\u0cecᒚ\u17cd\u21ba\u2818\u2b5dⲄⴋⴓ㺱㻞巶忢憊椼檌檮滙玌璎疟祍蠛裤贲ꚸ뤀먛\ud85c\udbf4\uddcf\uec15," +
                "  溑좲햽,  Ⴃ\u2572\u2fb7䳰嬐涫跁뼴솔턕퐗\ue103\ueea7ﰀ, ...]",
                "{ =31331,  ᴅ=6,  \u29fb=5,  뙺=5,  དྷ=5,  ᝰ=5,  \ue3dd=4,  筞=4,  \ufff9=4,  勡=4}",
                32.01744900002343
        );
        stringSubsetsWithChar_char_fail_helper(1, ' ');
    }

    private static void listsWithSublists_helper(
            int scale,
            @NotNull Iterable<List<Integer>> sublists,
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            @NotNull String topSampleCount,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsWithSublists(sublists, xs))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsWithSublists_fail_helper(
            int scale,
            @NotNull Iterable<List<Integer>> sublists,
            @NotNull Iterable<Integer> input
    ) {
//        try {
            toList(P.withScale(scale).listsWithSublists(sublists, input));
//            fail();
//        } catch (NoSuchElementException | IllegalStateException ignored) {}
//        finally {
//            P.reset();
//        }
    }

    @Test
    public void testListsWithSublists() {
        listsWithSublists_helper(
                5,
                P.uniformSample(Collections.singletonList(Arrays.asList(1, 2, 3))),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "[[5, 4, 5, 1, 2, 3, 5, 5, 6, 5, 6, 4, 6, 5], [4, 6, 1, 2, 3, 6, 5], [1, 2, 3], [4, 6, 1, 2, 3, 5]," +
                " [1, 2, 3], [6, 1, 2, 3, 4, 6], [6, 1, 2, 3, 6], [1, 2, 3, 6], [1, 2, 3], [1, 2, 3], [1, 2, 3]," +
                " [1, 2, 3], [1, 2, 3], [5, 1, 2, 3], [6, 6, 5, 6, 1, 2, 3], [4, 5, 1, 2, 3]," +
                " [5, 6, 5, 4, 5, 4, 1, 2, 3, 6, 5, 6], [5, 6, 6, 1, 2, 3], [1, 2, 3], [1, 2, 3], ...]",
                "{[1, 2, 3]=249571, [1, 2, 3, 5]=41857, [6, 1, 2, 3]=41798, [5, 1, 2, 3]=41752, [1, 2, 3, 6]=41627," +
                " [1, 2, 3, 4]=41496, [4, 1, 2, 3]=41418, [5, 5, 1, 2, 3]=7145, [4, 5, 1, 2, 3]=7142," +
                " [5, 1, 2, 3, 5]=7079}",
                5.001751999985147
        );
        listsWithSublists_helper(
                16,
                P.uniformSample(Collections.singletonList(Arrays.asList(1, 2, 3))),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "[[5, 5, 5, 5, 5, 1, 2, 3]," +
                " [6, 5, 6, 4, 6, 6, 6, 5, 6, 6, 4, 5, 4, 6, 6, 4, 4, 6, 4, 4, 6, 4, 4, 6, 6, 5, 4, 5, 5, 1, 2, 3," +
                " 5, 6, 5, 6, 5, 6, 5, 4, 5, 4], [5, 6, 6, 4, 4, 6, 4, 4, 1, 2, 3]," +
                " [6, 5, 6, 4, 6, 6, 6, 4, 5, 5, 1, 2, 3, 6, 4, 6, 5, 4, 4, 6, 6]," +
                " [4, 4, 1, 2, 3, 4, 6, 6, 4, 4, 4, 5, 4, 6, 4, 6, 6]," +
                " [6, 4, 6, 6, 5, 6, 5, 5, 5, 5, 6, 4, 6, 4, 5, 4, 1, 2, 3, 6, 6, 4, 4]," +
                " [4, 5, 1, 2, 3, 6, 4, 4, 4], [1, 2, 3, 4, 6, 4, 4], [5, 6, 6, 1, 2, 3, 6, 6, 4, 5, 5]," +
                " [5, 6, 5, 4, 4, 4, 6, 5, 5, 6, 4, 1, 2, 3, 4, 5, 6, 5], [4, 4, 4, 4, 1, 2, 3, 4, 6]," +
                " [6, 1, 2, 3, 6, 6], [6, 6, 5, 6, 6, 5, 4, 6, 5, 1, 2, 3, 6, 6, 4, 5, 5, 6, 6, 6, 6, 5, 6]," +
                " [6, 4, 4, 4, 5, 5, 5, 6, 5, 5, 6, 6, 6, 5, 5, 5, 6, 5, 4, 5, 5, 5, 6, 5, 5, 5, 1, 2, 3, 5, 4, 4," +
                " 5, 4, 4, 6, 6], [6, 5, 4, 1, 2, 3, 6], [4, 4, 5, 1, 2, 3, 4, 6, 5, 4, 4], [1, 2, 3, 5, 5, 4]," +
                " [4, 4, 4, 6, 5, 5, 4, 5, 5, 4, 6, 6, 6, 6, 5, 6, 5, 5, 5, 5, 1, 2, 3, 6, 5]," +
                " [4, 5, 5, 4, 5, 4, 5, 5, 5, 6, 5, 4, 6, 6, 5, 4, 6, 4, 5, 6, 6, 1, 2, 3, 6, 6, 5, 5, 5, 6, 6, 4]," +
                " [1, 2, 3, 5, 6, 4, 4, 5, 6], ...]",
                "{[1, 2, 3]=17715, [1, 2, 3, 5]=5212, [1, 2, 3, 4]=5206, [1, 2, 3, 6]=5149, [6, 1, 2, 3]=5077," +
                " [5, 1, 2, 3]=5061, [4, 1, 2, 3]=5038, [1, 2, 3, 6, 5]=1592, [1, 2, 3, 4, 5]=1560," +
                " [1, 2, 3, 5, 5]=1542}",
                16.013374999997783
        );
        listsWithSublists_helper(
                4,
                map(i -> Arrays.asList(i, i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric(),
                "[[46, 25, 19, 19], [17, 0, 0], [8, 21, 21], [13, 13], [11, 11, 17], [5, 10, 10], [77, 77, 19, 70]," +
                " [52, 6, 17, 17, 22, 16], [6, 6, 8, 0], [6, 15, 41, 41], [20, 20], [22, 9, 23, 23, 83]," +
                " [27, 32, 32, 2], [41, 41, 1], [20, 12, 12], [33, 45, 45, 25, 14, 12, 2]," +
                " [37, 37, 2, 15, 15, 24, 24, 118, 49, 46], [54, 54, 15, 15, 0, 90], [31, 31], [18, 18, 28, 19], ...]",
                "{[0, 0]=7600, [1, 1]=7316, [2, 2]=7114, [3, 3]=6968, [4, 4]=6683, [5, 5]=6601, [6, 6]=6233," +
                " [7, 7]=6065, [8, 8]=5837, [9, 9]=5775}",
                4.001450999968003
        );
        listsWithSublists_helper(
                16,
                map(i -> Arrays.asList(i, i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric(),
                "[[40, 25, 0, 19, 21, 9, 13, 12, 20, 10, 7, 77, 19, 70, 19, 19, 15, 53, 6, 24, 16, 6]," +
                " [9, 9, 41, 9], [16, 23, 24, 9, 84, 32, 28, 2, 15, 15, 38, 3, 12, 21, 45, 34, 28, 14, 12, 2, 24]," +
                " [35, 40, 40, 15, 15, 118, 49, 46, 54, 19, 15, 0, 90]," +
                " [12, 31, 19, 36, 44, 43, 168, 18, 7, 3, 31, 22, 13, 33, 49, 31, 31, 60, 50, 14]," +
                " [7, 121, 23, 122, 19, 0, 0, 74]," +
                " [6, 14, 14, 9, 51, 10, 7, 31, 9, 18, 12, 17, 1, 73, 9, 29, 21, 3, 55, 16], [35, 35, 8, 64]," +
                " [12, 12, 45, 24, 170, 60, 21, 32, 22], [34, 34, 26, 91, 34]," +
                " [10, 100, 5, 126, 0, 11, 16, 0, 29, 7, 6, 13, 22, 120, 2, 30, 36, 11, 31, 10, 0, 2, 33, 4, 10, 14," +
                " 18, 36, 33, 61, 21, 21, 26, 43], [37, 25, 43, 89, 20, 30, 41, 6, 24, 4, 4, 14]," +
                " [8, 5, 14, 21, 106, 0, 62, 94, 16, 8, 1, 5, 45, 4, 17, 7, 7, 25, 31, 25, 27, 80, 53, 2]," +
                " [21, 40, 44, 10, 11, 10, 47, 28, 33, 48, 21, 21], [67, 6, 6, 60]," +
                " [19, 19, 67, 21, 47, 7, 51, 4, 35, 35, 35, 20, 17, 12, 19]," +
                " [38, 0, 2, 2, 35, 1, 18, 33, 24, 33, 17, 6]," +
                " [5, 11, 31, 40, 42, 35, 0, 14, 27, 4, 6, 5, 5, 30, 28, 34, 2, 50, 40]," +
                " [28, 11, 37, 18, 31, 28, 4, 4, 76, 44, 8, 30, 57, 19, 27, 25, 23, 12, 4, 7, 60, 76, 3, 39]," +
                " [23, 23, 11, 26, 33, 15, 20, 2, 7], ...]",
                "{[0, 0]=496, [1, 1]=473, [3, 3]=440, [4, 4]=406, [2, 2]=406, [5, 5]=399, [7, 7]=383, [6, 6]=368," +
                " [9, 9]=362, [12, 12]=354}",
                16.005091000001197
        );
        //todo failure
    }

    private static double meanOfIntegers(@NotNull List<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsListsWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers.readListWithNulls(Readers::readInteger)).apply(s).get();
    }
}
// @formatter:on
