package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.GT;
import static mho.wheels.testing.Testing.aeq;

public class QuintupleTest {
    private static final Quintuple.QuintupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering
                > PC = new Quintuple.QuintupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder())
                );

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

    private static void hashCode_helper(String a, Integer b, Boolean c, Character d, @NotNull String e, int output) {
        aeq(
                new Quintuple<>(
                        a,
                        b,
                        c,
                        d,
                        Readers.readWithNullsStrict(Readers::readBigDecimalStrict).apply(e).get()
                ).hashCode(),
                output
        );
    }

    @Test
    public void testHashCode() {
        hashCode_helper("hi", 3, true, 'a', "1E+1", -1219290486);
        hashCode_helper("hi", 3, true, 'a', "null", -1219290516);
        hashCode_helper(null, 3, true, 'a', "1E+1", 1275401);
        hashCode_helper(null, null, null, null, "null", 0);
    }

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Quintuple.readStrict(
                        input,
                        Readers.readWithNullsStrict(Readers::readStringStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readBooleanStrict),
                        Readers.readWithNullsStrict(Readers::readCharacterStrict),
                        Readers.readWithNullsStrict(Readers::readOrderingStrict)
                ),
                output
        );
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("(hi, 3, true, a, >)", "Optional[(hi, 3, true, a, >)]");
        readStrict_helper("(hi, 3, true, a, null)", "Optional[(hi, 3, true, a, null)]");
        readStrict_helper("(null, 3, true, a, >)", "Optional[(null, 3, true, a, >)]");
        readStrict_helper("(null, null, null, null, null)", "Optional[(null, null, null, null, null)]");

        readStrict_helper("hi, 3, true, a, >", "Optional.empty");
        readStrict_helper("(hi, 3, true, a, GT)", "Optional.empty");
        readStrict_helper("(hi, 3, true, a, >", "Optional.empty");
        readStrict_helper("hi, 3, true, a, >)", "Optional.empty");
        readStrict_helper("(hi,3,true,a,>)", "Optional.empty");
        readStrict_helper("null", "Optional.empty");
    }

    private static void QuintupleComparator_compare_helper(
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
            int output
    ) {
        aeq(
                PC.compare(
                        new Quintuple<>(
                                pa,
                                pb,
                                pc,
                                pd,
                                Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get()
                        ),
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

    @Test
    public void testQuintupleComparator_compare() {
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "hi", 3, true, 'a', ">", 0);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "hi", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "bye", 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "bye", 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "hi", 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "hi", 4, true, 'a', ">", 0);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "hi", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "bye", 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "bye", 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 4, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "hi", 3, true, 'a', "null", 0);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "bye", 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "bye", 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("hi", 3, true, 'a', "null", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "bye", 3, true, 'a', ">", 0);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "bye", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "bye", 4, true, 'a', ">", 0);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", "bye", 3, true, 'a', "null", 1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 4, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "bye", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "bye", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", "bye", 3, true, 'a', "null", 0);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", null, 4, true, 'a', ">", 1);
        QuintupleComparator_compare_helper("bye", 3, true, 'a', "null", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "bye", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "bye", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", "bye", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", null, 3, true, 'a', ">", 0);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", null, 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 3, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "bye", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "bye", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", "bye", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", null, 3, true, 'a', ">", 1);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", null, 4, true, 'a', ">", 0);
        QuintupleComparator_compare_helper(null, 4, true, 'a', ">", null, null, null, null, "null", 1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "hi", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "hi", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "hi", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "bye", 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "bye", 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", "bye", 3, true, 'a', "null", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", null, 3, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", null, 4, true, 'a', ">", -1);
        QuintupleComparator_compare_helper(null, null, null, null, "null", null, null, null, null, "null", 0);
    }
}
