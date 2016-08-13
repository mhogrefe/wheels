package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static org.junit.Assert.fail;

public class LexComparatorTest {
    private static final @NotNull Comparator<Integer> ODDS_BEFORE_EVENS = (i, j) -> {
        boolean iEven = (i & 1) == 0;
        boolean jEven = (j & 1) == 0;
        if (iEven && !jEven) return 1;
        if (!iEven && jEven) return -1;
        return Integer.compare(i, j);
    };

    @Test
    public void testConstructor() {
        new LexComparator<Integer>();
    }

    @Test
    public void testConstructor_Comparator() {
        new LexComparator<>(ODDS_BEFORE_EVENS);
    }

    private static void compare_helper(
            @NotNull LexComparator<Integer> comparator,
            @NotNull List<Iterable<Integer>> list
    ) {
        Testing.testCompareToHelper(comparator, list);
    }

    private static void compare_helper(@NotNull LexComparator<Integer> comparator, @NotNull String list) {
        compare_helper(comparator, readIntegerListWithNullsIterable(list));
    }

    private static void compare_fail_helper(
            @NotNull LexComparator<Integer> comparator,
            @NotNull String x,
            @NotNull String y
    ) {
        try {
            comparator.compare(readIntegerListWithNulls(x), readIntegerListWithNulls(y));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testCompare() {
        compare_helper(new LexComparator<>(), "[[], [1, 2, 3], [3], [3, 1, 4]]");
        compare_helper(
                new LexComparator<>(),
                Arrays.asList(
                        Collections.emptyList(),
                        Arrays.asList(2, 4, 6),
                        map(i -> i << 1, ExhaustiveProvider.INSTANCE.rangeUpIncreasing(1)),
                        Arrays.asList(3, 6, null)
                )
        );
        compare_helper(
                new LexComparator<>(ODDS_BEFORE_EVENS),
                Arrays.asList(
                        Collections.emptyList(),
                        Arrays.asList(3, 6, null),
                        Arrays.asList(2, 4, 6),
                        map(i -> i << 1, ExhaustiveProvider.INSTANCE.rangeUpIncreasing(1))
                )
        );

        compare_fail_helper(new LexComparator<>(), "[null]", "[1, 2, 3]");
        compare_fail_helper(
                new LexComparator<>(new ListBasedComparator<>(readIntegerListWithNulls("[1, 2, 3]"))),
                "[4]",
                "[1, 2, 3]"
        );
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull List<Iterable<Integer>> readIntegerListWithNullsIterable(@NotNull String s) {
        return toList(
                map(
                        is -> (Iterable<Integer>) is,
                        Readers.readListStrict(Readers.readListWithNullsStrict(Readers::readIntegerStrict))
                                .apply(s).get()
                )
        );
    }
}
