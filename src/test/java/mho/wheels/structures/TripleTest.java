package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;

public class TripleTest {
    private static <A, B, C> void constructor_helper(A a, B b, C c, @NotNull String output) {
        aeq(new Triple<>(a, b, c), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, "(hi, 3, true)");
        constructor_helper("hi", 3, null, "(hi, 3, null)");
        constructor_helper(null, 3, true, "(null, 3, true)");
        constructor_helper(null, null, null, "(null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, @NotNull String output) {
        aeq(Triple.toList(new Triple<>(a, b, c)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, "[1, 2, 3]");
        toList_helper("hi", "bye", "hey", "[hi, bye, hey]");
        toList_helper(1, null, null, "[1, null, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(Triple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()), output);
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Triple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2, 3]", "(1, 2, 3)");
        fromList_helper("[1, null, null]", "(1, null, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1, 2]");
        fromList_fail_helper("[1, 2, 3, 4]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            boolean pc,
            @NotNull String qa,
            int qb,
            boolean qc,
            @NotNull String output
    ) {
        aeq(Triple.compare(new Triple<>(pa, pb, pc), new Triple<>(qa, qb, qc)), output);
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, true, "hi", 3, true, "=");
        compare_helper("hi", 3, true, "hi", 4, true, "<");
        compare_helper("hi", 3, true, "bye", 3, true, ">");
        compare_helper("hi", 3, true, "bye", 4, true, ">");
        compare_helper("hi", 4, true, "hi", 3, true, ">");
        compare_helper("hi", 4, true, "hi", 4, true, "=");
        compare_helper("hi", 4, true, "bye", 3, true, ">");
        compare_helper("hi", 4, true, "bye", 4, true, ">");
        compare_helper("bye", 3, true, "hi", 3, true, "<");
        compare_helper("bye", 3, true, "hi", 4, true, "<");
        compare_helper("bye", 3, true, "bye", 3, true, "=");
        compare_helper("bye", 3, true, "bye", 4, true, "<");
        compare_helper("bye", 4, true, "hi", 3, true, "<");
        compare_helper("bye", 4, true, "hi", 4, true, "<");
        compare_helper("bye", 4, true, "bye", 3, true, ">");
        compare_helper("bye", 4, true, "bye", 4, true, "=");
    }

    @Test
    public void testEquals() {
        assertTrue(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 4, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("bye", 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>("hi", 3, true).equals(null));
        assertFalse(new Triple<>("hi", 3, true).equals(0.5));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>("hi", 3, true)));
        assertTrue(new Triple<>("hi", 3, null).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>("bye", 3, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(null));
        assertFalse(new Triple<>("hi", 3, null).equals(0.5));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>("hi", 3, null)));
        assertTrue(new Triple<>(null, 3, true).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>(null, 4, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>(null, 3, true).equals(null));
        assertFalse(new Triple<>(null, 3, true).equals(0.5));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>(null, 3, true)));
        assertTrue(new Triple<>(null, null, null).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>(null, null, null).equals(null));
        assertFalse(new Triple<>(null, null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Triple<>("hi", 3, true), "(hi, 3, true)");
        aeq(new Triple<>("hi", 3, null), "(hi, 3, null)");
        aeq(new Triple<>(null, 3, true), "(null, 3, true)");
        aeq(new Triple<>(null, null, null), "(null, null, null)");
    }

    @Test
    public void testTripleComparator_compare() {
        Triple.TripleComparator<String, Integer, Boolean> pc = new Triple.TripleComparator<>(
                Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Boolean>naturalOrder())
        );
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 3, true)), 0);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 4, true)), 0);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 3, null)), 0);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 3, true)), 0);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 4, true)), 0);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 3, null)), 0);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, 3, true)), 0);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, 4, true)), 0);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, null, null)), 0);
    }
}
