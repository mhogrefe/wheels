package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.testing.Testing.*;

public class QuadrupleTest {
    private static <A, B, C, D> void constructor_helper(A a, B b, C c, D d, @NotNull String output) {
        aeq(new Quadruple<>(a, b, c, d), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', "(hi, 3, true, a)");
        constructor_helper("hi", 3, true, null, "(hi, 3, true, null)");
        constructor_helper(null, 3, true, 'a', "(null, 3, true, a)");
        constructor_helper(null, null, null, null, "(null, null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, T d, @NotNull String output) {
        aeq(Quadruple.toList(new Quadruple<>(a, b, c, d)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, 4, "[1, 2, 3, 4]");
        toList_helper("hi", "bye", "hey", "yo", "[hi, bye, hey, yo]");
        toList_helper(1, null, null, null, "[1, null, null, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Quadruple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()),
                output
        );
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Quadruple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2, 3, 4]", "(1, 2, 3, 4)");
        fromList_helper("[1, null, null, null]", "(1, null, null, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1, 2, 3]");
        fromList_fail_helper("[1, 2, 3, 4, 5]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            boolean pc,
            char pd,
            @NotNull String qa,
            int qb,
            boolean qc,
            char qd,
            @NotNull String output
    ) {
        aeq(Quadruple.compare(new Quadruple<>(pa, pb, pc, pd), new Quadruple<>(qa, qb, qc, qd)), output);
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, true, 'a', "hi", 3, true, 'a', "=");
        compare_helper("hi", 3, true, 'a', "hi", 4, true, 'a', "<");
        compare_helper("hi", 3, true, 'a', "bye", 3, true, 'a', ">");
        compare_helper("hi", 3, true, 'a', "bye", 4, true, 'a', ">");
        compare_helper("hi", 4, true, 'a', "hi", 3, true, 'a', ">");
        compare_helper("hi", 4, true, 'a', "hi", 4, true, 'a', "=");
        compare_helper("hi", 4, true, 'a', "bye", 3, true, 'a', ">");
        compare_helper("hi", 4, true, 'a', "bye", 4, true, 'a', ">");
        compare_helper("bye", 3, true, 'a', "hi", 3, true, 'a', "<");
        compare_helper("bye", 3, true, 'a', "hi", 4, true, 'a', "<");
        compare_helper("bye", 3, true, 'a', "bye", 3, true, 'a', "=");
        compare_helper("bye", 3, true, 'a', "bye", 4, true, 'a', "<");
        compare_helper("bye", 4, true, 'a', "hi", 3, true, 'a', "<");
        compare_helper("bye", 4, true, 'a', "hi", 4, true, 'a', "<");
        compare_helper("bye", 4, true, 'a', "bye", 3, true, 'a', ">");
        compare_helper("bye", 4, true, 'a', "bye", 4, true, 'a', "=");
    }

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            boolean output
    ) {
        aeq(new Quadruple<>(pa, pb, pc, pd).equals(new Quadruple<>(qa, qb, qc, qd)), output);
    }

    private static void equals_helper(String pa, Integer pb, Boolean pc, Character pd, Object x, boolean output) {
        aeq(new Quadruple<>(pa, pb, pc, pd).equals(x), output);
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, true, 'a', "hi", 3, true, 'a', true);
        equals_helper("hi", 3, true, 'a', "hi", 4, true, 'a', false);
        equals_helper("hi", 3, true, 'a', "bye", 3, true, 'a', false);
        equals_helper("hi", 3, true, 'a', "hi", 3, true, null, false);
        equals_helper("hi", 3, true, 'a', null, 3, true, 'a', false);
        equals_helper("hi", 3, true, 'a', null, null, null, null, false);
        equals_helper("hi", 3, true, null, "hi", 3, true, 'a', false);
        equals_helper("hi", 3, true, null, "hi", 3, true, null, true);
        equals_helper("hi", 3, true, null, "bye", 3, true, null, false);
        equals_helper("hi", 3, true, null, null, 3, true, 'a', false);
        equals_helper("hi", 3, true, null, null, null, null, null, false);
        equals_helper(null, 3, true, 'a', "hi", 3, true, 'a', false);
        equals_helper(null, 3, true, 'a', "hi", 3, true, null, false);
        equals_helper(null, 3, true, 'a', null, 3, true, 'a', true);
        equals_helper(null, 3, true, 'a', null, 4, true, 'a', false);
        equals_helper(null, 3, true, 'a', null, null, null, null, false);
        equals_helper(null, null, null, null, "hi", 3, true, 'a', false);
        equals_helper(null, null, null, null, "hi", 3, true, null, false);
        equals_helper(null, null, null, null, null, 3, true, 'a', false);
        equals_helper(null, null, null, null, null, null, null, null, true);

        equals_helper("hi", 3, true, 'a', null, false);
        equals_helper("hi", 3, true, 'a', 0.5, false);
        equals_helper("hi", 3, true, null, null, false);
        equals_helper("hi", 3, true, null, 0.5, false);
        equals_helper(null, 3, true, 'a', null, false);
        equals_helper(null, 3, true, 'a', 0.5, false);
        equals_helper(null, null, null, null, null, false);
        equals_helper(null, null, null, null, 0.5, false);
    }

    @Test
    public void testToString() {
        aeq(new Quadruple<>("hi", 3, true, 'a'), "(hi, 3, true, a)");
        aeq(new Quadruple<>("hi", 3, true, null), "(hi, 3, true, null)");
        aeq(new Quadruple<>(null, 3, true, 'a'), "(null, 3, true, a)");
        aeq(new Quadruple<>(null, null, null, null), "(null, null, null, null)");
    }

    @Test
    public void testQuadrupleComparator_compare() {
        Quadruple.QuadrupleComparator<String, Integer, Boolean, Character> pc = new Quadruple.QuadrupleComparator<>(
                Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Character>naturalOrder())
        );
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 3, true, null)), 0);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 3, true, null)), 0);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, null, null, null)), 0);
    }
}
