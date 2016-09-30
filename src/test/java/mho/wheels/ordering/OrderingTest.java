package mho.wheels.ordering;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

public class OrderingTest {
    private static final @NotNull Comparator<Integer> ODDS_BEFORE_EVENS = (i, j) -> {
        boolean iEven = (i & 1) == 0;
        boolean jEven = (j & 1) == 0;
        if (iEven && !jEven) return 1;
        if (!iEven && jEven) return -1;
        return Integer.compare(i, j);
    };

    private static void fromInt_helper(int input, @NotNull String output) {
        aeq(fromInt(input), output);
    }

    @Test
    public void testFromInt() {
        fromInt_helper(0, "=");
        fromInt_helper(1, ">");
        fromInt_helper(2, ">");
        fromInt_helper(-1, "<");
        fromInt_helper(-2, "<");
    }

    private static void toInt_helper(@NotNull String input, int output) {
        aeq(Ordering.readStrict(input).get().toInt(), output);
    }

    @Test
    public void testToInt() {
        toInt_helper("=", 0);
        toInt_helper(">", 1);
        toInt_helper("<", -1);
    }

    private static void invert_helper(@NotNull String input, @NotNull String output) {
        aeq(Ordering.readStrict(input).get().invert(), output);
    }

    @Test
    public void testInvert() {
        invert_helper("=", "=");
        invert_helper(">", "<");
        invert_helper("<", ">");
    }

    private static void compare_T_T_helper(int i, int j, @NotNull String output) {
        aeq(compare(i, j), output);
    }

    @Test
    public void testCompare_T_T() {
        compare_T_T_helper(0, 0, "=");
        compare_T_T_helper(1, 2, "<");
        compare_T_T_helper(4, 3, ">");
    }

    private static void compare_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            @NotNull String output
    ) {
        aeq(compare(comparator, i, j), output);
    }

    @Test
    public void testCompare_Comparator_T_T() {
        compare_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, "=");
        compare_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, "<");
        compare_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, ">");
    }

    private static void eq_T_T_helper(int i, int j, boolean output) {
        aeq(eq(i, j), output);
    }

    @Test
    public void testEq_T_T() {
        eq_T_T_helper(0, 0, true);
        eq_T_T_helper(1, 2, false);
        eq_T_T_helper(4, 3, false);
    }

    private static void ne_T_T_helper(int i, int j, boolean output) {
        aeq(ne(i, j), output);
    }

    @Test
    public void testNe_T_T() {
        ne_T_T_helper(0, 0, false);
        ne_T_T_helper(1, 2, true);
        ne_T_T_helper(4, 3, true);
    }

    private static void lt_T_T_helper(int i, int j, boolean output) {
        aeq(lt(i, j), output);
    }

    @Test
    public void testLt_T_T() {
        lt_T_T_helper(0, 0, false);
        lt_T_T_helper(1, 2, true);
        lt_T_T_helper(4, 3, false);
    }

    private static void gt_T_T_helper(int i, int j, boolean output) {
        aeq(gt(i, j), output);
    }

    @Test
    public void testGt_T_T() {
        gt_T_T_helper(0, 0, false);
        gt_T_T_helper(1, 2, false);
        gt_T_T_helper(4, 3, true);
    }

    private static void le_T_T_helper(int i, int j, boolean output) {
        aeq(le(i, j), output);
    }

    @Test
    public void testLe_T_T() {
        le_T_T_helper(0, 0, true);
        le_T_T_helper(1, 2, true);
        le_T_T_helper(4, 3, false);
    }

    private static void ge_T_T_helper(int i, int j, boolean output) {
        aeq(ge(i, j), output);
    }

    @Test
    public void testGe_T_T() {
        ge_T_T_helper(0, 0, true);
        ge_T_T_helper(1, 2, false);
        ge_T_T_helper(4, 3, true);
    }

    private static void eq_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(eq(comparator, i, j), output);
    }

    @Test
    public void testEq_Comparator_T_T() {
        eq_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, true);
        eq_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, false);
        eq_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, false);
    }

    private static void ne_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(ne(comparator, i, j), output);
    }

    @Test
    public void testNe_Comparator_T_T() {
        ne_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, false);
        ne_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, true);
        ne_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, true);
    }

    private static void lt_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(lt(comparator, i, j), output);
    }

    @Test
    public void testLt_Comparator_T_T() {
        lt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, false);
        lt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, true);
        lt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, false);
    }

    private static void gt_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(gt(comparator, i, j), output);
    }

    @Test
    public void testGt_Comparator_T_T() {
        gt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, false);
        gt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, false);
        gt_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, true);
    }

    private static void le_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(le(comparator, i, j), output);
    }

    @Test
    public void testLe_Comparator_T_T() {
        le_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, true);
        le_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, true);
        le_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, false);
    }

    private static void ge_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            boolean output
    ) {
        aeq(ge(comparator, i, j), output);
    }

    @Test
    public void testGe_Comparator_T_T() {
        ge_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, true);
        ge_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, false);
        ge_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, true);
    }

    private static void min_T_T_helper(int i, int j, int output) {
        aeq(min(i, j), output);
    }

    @Test
    public void testMin_T_T() {
        min_T_T_helper(0, 0, 0);
        min_T_T_helper(1, 2, 1);
        min_T_T_helper(4, 3, 3);
    }

    private static void max_T_T_helper(int i, int j, int output) {
        aeq(max(i, j), output);
    }

    @Test
    public void testMax_T_T() {
        max_T_T_helper(0, 0, 0);
        max_T_T_helper(1, 2, 2);
        max_T_T_helper(4, 3, 4);
    }

    private static void minMax_T_T_helper(int i, int j, @NotNull String output) {
        aeq(minMax(i, j), output);
    }

    @Test
    public void testMinMax_T_T() {
        minMax_T_T_helper(0, 0, "(0, 0)");
        minMax_T_T_helper(1, 2, "(1, 2)");
        minMax_T_T_helper(4, 3, "(3, 4)");
    }

    private static void min_Comparator_T_T_helper(@NotNull Comparator<Integer> comparator, int i, int j, int output) {
        aeq(min(comparator, i, j), output);
    }

    @Test
    public void testMin_Comparator_T_T() {
        min_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, 0);
        min_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, 5);
        min_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, 11);
    }

    private static void max_Comparator_T_T_helper(@NotNull Comparator<Integer> comparator, int i, int j, int output) {
        aeq(max(comparator, i, j), output);
    }

    @Test
    public void testMax_Comparator_T_T() {
        max_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, 0);
        max_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, 2);
        max_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, 4);
    }

    private static void minMax_Comparator_T_T_helper(
            @NotNull Comparator<Integer> comparator,
            int i,
            int j,
            @NotNull String output
    ) {
        aeq(minMax(comparator, i, j), output);
    }

    @Test
    public void testMinMax_Comparator_T_T() {
        minMax_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 0, 0, "(0, 0)");
        minMax_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 5, 2, "(5, 2)");
        minMax_Comparator_T_T_helper(ODDS_BEFORE_EVENS, 4, 11, "(11, 4)");
    }

    private static void minimum_Iterable_T_helper(@NotNull String xs, int output) {
        aeq(minimum(readIntegerList(xs)), output);
    }

    private static void minimum_Iterable_T_fail_helper(@NotNull String xs) {
        try {
            minimum(readIntegerListWithNulls(xs));
            fail();
        } catch (IllegalArgumentException | NullPointerException | IllegalStateException ignored) {}
    }

    @Test
    public void testMinimum_Iterable_T() {
        minimum_Iterable_T_helper("[1]", 1);
        minimum_Iterable_T_helper("[5, 2, 11, 4]", 2);

        minimum_Iterable_T_fail_helper("[]");
        minimum_Iterable_T_fail_helper("[null]");
        minimum_Iterable_T_fail_helper("[5, 2, 11, null]");
    }

    private static void maximum_Iterable_T_helper(@NotNull String xs, int output) {
        aeq(maximum(readIntegerList(xs)), output);
    }

    private static void maximum_Iterable_T_fail_helper(@NotNull String xs) {
        try {
            maximum(readIntegerListWithNulls(xs));
            fail();
        } catch (IllegalArgumentException | NullPointerException | IllegalStateException ignored) {}
    }

    @Test
    public void testMaximum_Iterable_T() {
        maximum_Iterable_T_helper("[1]", 1);
        maximum_Iterable_T_helper("[5, 2, 11, 4]", 11);

        maximum_Iterable_T_fail_helper("[]");
        maximum_Iterable_T_fail_helper("[null]");
        maximum_Iterable_T_fail_helper("[5, 2, 11, null]");
    }

    private static void minimumMaximum_Iterable_T_helper(@NotNull String xs, @NotNull String output) {
        aeq(minimumMaximum(readIntegerList(xs)), output);
    }

    private static void minimumMaximum_Iterable_T_fail_helper(@NotNull String xs) {
        try {
            minimumMaximum(readIntegerListWithNulls(xs));
            fail();
        } catch (IllegalArgumentException | NullPointerException | IllegalStateException ignored) {}
    }

    @Test
    public void testMinimumMaximum_Iterable_T() {
        minimumMaximum_Iterable_T_helper("[1]", "(1, 1)");
        minimumMaximum_Iterable_T_helper("[5, 2, 11, 4]", "(2, 11)");

        minimumMaximum_Iterable_T_fail_helper("[]");
        minimumMaximum_Iterable_T_fail_helper("[null]");
        minimumMaximum_Iterable_T_fail_helper("[5, 2, 11, null]");
    }

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readStrict(input), output);
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("<", "Optional[<]");
        readStrict_helper("=", "Optional[=]");
        readStrict_helper(">", "Optional[>]");

        readStrict_helper(" <", "Optional.empty");
        readStrict_helper("eq", "Optional.empty");
        readStrict_helper("gt ", "Optional.empty");
        readStrict_helper("GT ", "Optional.empty");
        readStrict_helper("", "Optional.empty");
        readStrict_helper("dsfs<fgd", "Optional.empty");
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readListStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }
}
