package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class QuintupleTest {
    private static <A, B, C, D, E> void constructor_helper(A a, B b, C c, D d, E e, @NotNull String output) {
        aeq(new Quintuple<>(a, b, c, d, e), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', GT, "(hi, 3, true, a, >)");
        constructor_helper("hi", 3, true, 'a', null, "(hi, 3, true, a, null)");
        constructor_helper(null, 3, true, 'a', GT, "(null, 3, true, a, >)");
        constructor_helper(null, null, null, null, null, "(null, null, null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, T d, T e, @NotNull String output) {
        aeq(Quintuple.toList(new Quintuple<>(a, b, c, d, e)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, 4, 5, "[1, 2, 3, 4, 5]");
        toList_helper("hi", "bye", "hey", "yo", "ayy", "[hi, bye, hey, yo, ayy]");
        toList_helper(1, null, null, null, null, "[1, null, null, null, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Quintuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()),
                output
        );
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Quintuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2, 3, 4, 5]", "(1, 2, 3, 4, 5)");
        fromList_helper("[1, null, null, null, null]", "(1, null, null, null, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1, 2, 3, 4]");
        fromList_fail_helper("[1, 2, 3, 4, 5, 6]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            boolean pc,
            char pd,
            @NotNull String pe,
            @NotNull String qa,
            int qb,
            boolean qc,
            char qd,
            @NotNull String qe,
            @NotNull String output
    ) {
        aeq(
                Quintuple.compare(
                        new Quintuple<>(pa, pb, pc, pd, Readers.readOrderingStrict(pe).get()),
                        new Quintuple<>(qa, qb, qc, qd, Readers.readOrderingStrict(qe).get())
                ),
                output
        );
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, true, 'a', ">", "hi", 3, true, 'a', ">", "=");
        compare_helper("hi", 3, true, 'a', ">", "hi", 4, true, 'a', ">", "<");
        compare_helper("hi", 3, true, 'a', ">", "bye", 3, true, 'a', ">", ">");
        compare_helper("hi", 3, true, 'a', ">", "bye", 4, true, 'a', ">", ">");
        compare_helper("hi", 4, true, 'a', ">", "hi", 3, true, 'a', ">", ">");
        compare_helper("hi", 4, true, 'a', ">", "hi", 4, true, 'a', ">", "=");
        compare_helper("hi", 4, true, 'a', ">", "bye", 3, true, 'a', ">", ">");
        compare_helper("hi", 4, true, 'a', ">", "bye", 4, true, 'a', ">", ">");
        compare_helper("bye", 3, true, 'a', ">", "hi", 3, true, 'a', ">", "<");
        compare_helper("bye", 3, true, 'a', ">", "hi", 4, true, 'a', ">", "<");
        compare_helper("bye", 3, true, 'a', ">", "bye", 3, true, 'a', ">", "=");
        compare_helper("bye", 3, true, 'a', ">", "bye", 4, true, 'a', ">", "<");
        compare_helper("bye", 4, true, 'a', ">", "hi", 3, true, 'a', ">", "<");
        compare_helper("bye", 4, true, 'a', ">", "hi", 4, true, 'a', ">", "<");
        compare_helper("bye", 4, true, 'a', ">", "bye", 3, true, 'a', ">", ">");
        compare_helper("bye", 4, true, 'a', ">", "bye", 4, true, 'a', ">", "=");
    }

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            @NotNull String qe,
            boolean output
    ) {
        aeq(
                new Quintuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get()
                ).equals(
                        new Quintuple<>(
                                qa,
                                qb,
                                qc,
                                qd,
                                Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(qe).get()
                        )
                ),
                output
        );
    }

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            Object x,
            boolean output
    ) {
        aeq(
                new Quintuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get()
                ).equals(x),
                output
        );
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, true, 'a', ">", "hi", 3, true, 'a', ">", true);
        equals_helper("hi", 3, true, 'a', ">", "hi", 4, true, 'a', ">", false);
        equals_helper("hi", 3, true, 'a', ">", "bye", 3, true, 'a', ">", false);
        equals_helper("hi", 3, true, 'a', ">", "hi", 3, true, 'a', "null", false);
        equals_helper("hi", 3, true, 'a', ">", null, 3, true, 'a', ">", false);
        equals_helper("hi", 3, true, 'a', ">", null, null, null, null, "null", false);
        equals_helper("hi", 3, true, 'a', "null", "hi", 3, true, 'a', ">", false);
        equals_helper("hi", 3, true, 'a', "null", "hi", 3, true, 'a', "null", true);
        equals_helper("hi", 3, true, 'a', "null", "bye", 3, true, 'a', "null", false);
        equals_helper("hi", 3, true, 'a', "null", null, 3, true, 'a', ">", false);
        equals_helper("hi", 3, true, 'a', "null", null, null, null, null, "null", false);
        equals_helper(null, 3, true, 'a', ">", "hi", 3, true, 'a', ">", false);
        equals_helper(null, 3, true, 'a', ">", "hi", 3, true, 'a', "null", false);
        equals_helper(null, 3, true, 'a', ">", null, 3, true, 'a', ">", true);
        equals_helper(null, 3, true, 'a', ">", null, 4, true, 'a', ">", false);
        equals_helper(null, 3, true, 'a', ">", null, null, null, null, "null", false);
        equals_helper(null, null, null, null, "null", "hi", 3, true, 'a', ">", false);
        equals_helper(null, null, null, null, "null", "hi", 3, true, 'a', "null", false);
        equals_helper(null, null, null, null, "null", null, 3, true, 'a', ">", false);
        equals_helper(null, null, null, null, "null", null, null, null, null, "null", true);

        equals_helper(null, null, null, null, "null", null, false);
        equals_helper(null, null, null, null, "null", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', "null", null, false);
        equals_helper("hi", 3, true, 'a', "null", 0.5, false);
        equals_helper(null, 3, true, 'a', ">", null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, false);
    }

    @Test
    public void testToString() {
        aeq(new Quintuple<>("hi", 3, true, 'a', GT), "(hi, 3, true, a, >)");
        aeq(new Quintuple<>("hi", 3, true, 'a', null), "(hi, 3, true, a, null)");
        aeq(new Quintuple<>(null, 3, true, 'a', GT), "(null, 3, true, a, >)");
        aeq(new Quintuple<>(null, null, null, null, null), "(null, null, null, null, null)");
    }

    @Test
    public void testQuintupleComparator_compare() {
        Quintuple.QuintupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering
                > pc = new Quintuple.QuintupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder())
                );
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', null)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', null)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>(null, 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>(null, 4, true, 'a', GT)), -1);
        aeq(pc.compare(
                new Quintuple<>(null, null, null, null, null),
                new Quintuple<>(null, null, null, null, null)
        ), 0);
    }
}
