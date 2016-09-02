package mho.wheels.ordering;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

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
        aeq(Readers.readOrderingStrict(input).get().toInt(), output);
    }

    @Test
    public void testToInt() {
        toInt_helper("=", 0);
        toInt_helper(">", 1);
        toInt_helper("<", -1);
    }

    private static void invert_helper(@NotNull String input, @NotNull String output) {
        aeq(Readers.readOrderingStrict(input).get().invert(), output);
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
}
