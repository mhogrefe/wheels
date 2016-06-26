package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

public class PairTest {
    private static <A, B> void constructor_helper(A a, B b, @NotNull String output) {
        aeq(new Pair<>(a, b), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, "(hi, 3)");
        constructor_helper("hi", null, "(hi, null)");
        constructor_helper(null, 3, "(null, 3)");
        constructor_helper(null, null, "(null, null)");
    }

    private static <T> void toList_helper(T a, T b, @NotNull String output) {
        aeq(Pair.toList(new Pair<>(a, b)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, "[1, 2]");
        toList_helper("hi", "bye", "[hi, bye]");
        toList_helper(1, null, "[1, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(Pair.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()), output);
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Pair.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2]", "(1, 2)");
        fromList_helper("[1, null]", "(1, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1]");
        fromList_fail_helper("[1, 2, 3]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            @NotNull String qa,
            int qb,
            @NotNull String output
    ) {
        aeq(Pair.compare(new Pair<>(pa, pb), new Pair<>(qa, qb)), output);
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, "hi", 3, "=");
        compare_helper("hi", 3, "hi", 4, "<");
        compare_helper("hi", 3, "bye", 3, ">");
        compare_helper("hi", 3, "bye", 4, ">");
        compare_helper("hi", 4, "hi", 3, ">");
        compare_helper("hi", 4, "hi", 4, "=");
        compare_helper("hi", 4, "bye", 3, ">");
        compare_helper("hi", 4, "bye", 4, ">");
        compare_helper("bye", 3, "hi", 3, "<");
        compare_helper("bye", 3, "hi", 4, "<");
        compare_helper("bye", 3, "bye", 3, "=");
        compare_helper("bye", 3, "bye", 4, "<");
        compare_helper("bye", 4, "hi", 3, "<");
        compare_helper("bye", 4, "hi", 4, "<");
        compare_helper("bye", 4, "bye", 3, ">");
        compare_helper("bye", 4, "bye", 4, "=");
    }

    @Test
    public void testEquals() {
        assertTrue(new Pair<>("hi", 3).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("hi", 4)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("bye", 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>("hi", 3).equals(null));
        assertFalse(new Pair<>("hi", 3).equals(0.5));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>("hi", 3)));
        assertTrue(new Pair<>("hi", null).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>("bye", null)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>("hi", null).equals(null));
        assertFalse(new Pair<>("hi", null).equals(0.5));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>("hi", null)));
        assertTrue(new Pair<>(null, 3).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>(null, 4)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>(null, 3).equals(null));
        assertFalse(new Pair<>(null, 3).equals(0.5));
        assertFalse(new Pair<>(null, null).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>(null, null).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>(null, null).equals(new Pair<>(null, 3)));
        assertTrue(new Pair<>(null, null).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>(null, null).equals(null));
        assertFalse(new Pair<>(null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Pair<>("hi", 3), "(hi, 3)");
        aeq(new Pair<>("hi", null), "(hi, null)");
        aeq(new Pair<>(null, 3), "(null, 3)");
        aeq(new Pair<>(null, null), "(null, null)");
    }

    @Test
    public void testPairComparator_compare() {
        Pair.PairComparator<String, Integer> pc = new Pair.PairComparator<>(
                Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Integer>naturalOrder())
        );
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", 3)), 0);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", 4)), 0);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", null)), 0);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", 3)), 0);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", 4)), 0);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", null)), 0);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, 3)), 0);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, 4)), 0);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, null)), 0);
    }
}
