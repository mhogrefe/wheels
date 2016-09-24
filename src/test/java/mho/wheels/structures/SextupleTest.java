package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.GT;
import static mho.wheels.testing.Testing.aeq;

public class SextupleTest {
    private static final Sextuple.SextupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering,
                Double
                > PC = new Sextuple.SextupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Double>naturalOrder())
                );

    private static <A, B, C, D, E, F> void constructor_helper(A a, B b, C c, D d, E e, F f, @NotNull String output) {
        aeq(new Sextuple<>(a, b, c, d, e, f), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', GT, 0.5, "(hi, 3, true, a, >, 0.5)");
        constructor_helper("hi", 3, true, 'a', GT, null, "(hi, 3, true, a, >, null)");
        constructor_helper(null, 3, true, 'a', GT, 0.5, "(null, 3, true, a, >, 0.5)");
        constructor_helper(null, null, null, null, null, null, "(null, null, null, null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, T d, T e, T f, @NotNull String output) {
        aeq(Sextuple.toList(new Sextuple<>(a, b, c, d, e, f)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, 4, 5, 6, "[1, 2, 3, 4, 5, 6]");
        toList_helper("hi", "bye", "hey", "yo", "ayy", "hello", "[hi, bye, hey, yo, ayy, hello]");
        toList_helper(1, null, null, null, null, null, "[1, null, null, null, null, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Sextuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()),
                output
        );
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Sextuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2, 3, 4, 5, 6]", "(1, 2, 3, 4, 5, 6)");
        fromList_helper("[1, null, null, null, null, null]", "(1, null, null, null, null, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1, 2, 3, 4, 5]");
        fromList_fail_helper("[1, 2, 3, 4, 5, 6, 7]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            boolean pc,
            char pd,
            @NotNull String pe,
            double pf,
            @NotNull String qa,
            int qb,
            boolean qc,
            char qd,
            @NotNull String qe,
            double qf,
            @NotNull String output
    ) {
        aeq(
                Sextuple.compare(
                        new Sextuple<>(pa, pb, pc, pd, Ordering.readStrict(pe).get(), pf),
                        new Sextuple<>(qa, qb, qc, qd, Ordering.readStrict(qe).get(), qf)
                ),
                output
        );
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, "=");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, "<");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, ">");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, "=");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, ">");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, "<");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, "<");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, "=");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, ">");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, "=");
    }

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            Double pf,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            @NotNull String qe,
            Double qf,
            boolean output
    ) {
        aeq(
                new Sextuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Ordering::readStrict).apply(pe).get(),
                        pf
                ).equals(
                        new Sextuple<>(
                                qa,
                                qb,
                                qc,
                                qd,
                                Readers.readWithNullsStrict(Ordering::readStrict).apply(qe).get(),
                                qf
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
            Double pf,
            Object x,
            boolean output
    ) {
        aeq(
                new Sextuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Ordering::readStrict).apply(pe).get(),
                        pf
                ).equals(x),
                output
        );
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, true);
        equals_helper("hi", 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, null, null, null, "null", null, false);
        equals_helper("hi", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", null, true);
        equals_helper("hi", 3, true, 'a', ">", null, "bye", 3, true, 'a', ">", null, false);
        equals_helper("hi", 3, true, 'a', ">", null, null, 3, true, 'a', ">", 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", null, null, null, null, null, "null", null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, true);
        equals_helper(null, 3, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, null, null, null, null, "null", null, false);
        equals_helper(null, null, null, null, "null", null, "hi", 3, true, 'a', ">", 0.5, false);
        equals_helper(null, null, null, null, "null", null, "hi", 3, true, 'a', ">", null, false);
        equals_helper(null, null, null, null, "null", null, null, 3, true, 'a', ">", 0.5, false);
        equals_helper(null, null, null, null, "null", null, null, null, null, null, "null", null, true);

        equals_helper("hi", 3, true, 'a', ">", 0.5, null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", null, null, false);
        equals_helper("hi", 3, true, 'a', ">", null, 0.5, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, 0.5, false);
        equals_helper(null, null, null, null, "null", null, null, false);
        equals_helper(null, null, null, null, "null", null, 0.5, false);
    }

    private static void hashCode_helper(
            String a,
            Integer b,
            Boolean c,
            Character d,
            @NotNull String e,
            Double f,
            int output
    ) {
        aeq(
                new Sextuple<>(
                        a,
                        b,
                        c,
                        d,
                        Readers.readWithNullsStrict(Readers::readBigDecimalStrict).apply(e).get(),
                        f
                ).hashCode(),
                output
        );
    }

    @Test
    public void testHashCode() {
        hashCode_helper("hi", 3, true, 'a', "1E+1", 0.5, 1928345270);
        hashCode_helper("hi", 3, true, 'a', "1E+1", null, 856700598);
        hashCode_helper(null, 3, true, 'a', "1E+1", 0.5, 1111182103);
        hashCode_helper(null, null, null, null, "null", null, 0);
    }

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Sextuple.readStrict(
                        input,
                        Readers.readWithNullsStrict(Readers::readStringStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readBooleanStrict),
                        Readers.readWithNullsStrict(Readers::readCharacterStrict),
                        Readers.readWithNullsStrict(Ordering::readStrict),
                        Readers.readWithNullsStrict(Readers::readDoubleStrict)
                ),
                output
        );
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("(hi, 3, true, a, >, 0.5)", "Optional[(hi, 3, true, a, >, 0.5)]");
        readStrict_helper("(hi, 3, true, a, >, null)", "Optional[(hi, 3, true, a, >, null)]");
        readStrict_helper("(null, 3, true, a, >, 0.5)", "Optional[(null, 3, true, a, >, 0.5)]");
        readStrict_helper("(null, null, null, null, null, null)", "Optional[(null, null, null, null, null, null)]");

        readStrict_helper("hi, 3, true, a, >, 0.5", "Optional.empty");
        readStrict_helper("(hi, 3, true, a, >, 0.50)", "Optional.empty");
        readStrict_helper("(hi, 3, true, a, >, 0.5", "Optional.empty");
        readStrict_helper("hi, 3, true, a, >, 0.5)", "Optional.empty");
        readStrict_helper("(hi,3,true,a,>,0.5)", "Optional.empty");
        readStrict_helper("null", "Optional.empty");
    }

    private static void SextupleComparator_compare_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            Double pf,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            @NotNull String qe,
            Double qf,
            int output
    ) {
        aeq(
                PC.compare(
                        new Sextuple<>(
                                pa,
                                pb,
                                pc,
                                pd,
                                Readers.readWithNullsStrict(Ordering::readStrict).apply(pe).get(),
                                pf
                        ),
                        new Sextuple<>(
                                qa,
                                qb,
                                qc,
                                qd,
                                Readers.readWithNullsStrict(Ordering::readStrict).apply(qe).get(),
                                qf
                        )
                ),
                output
        );
    }

    @Test
    public void testSextupleComparator_compare() {
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", null, 0);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "bye", 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "bye", 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, null, 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("hi", 3, true, 'a', ">", null, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, null, 3, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, null, 4, true, 'a', ">", null, 1);
        SextupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "bye", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "bye", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, "bye", 3, true, 'a', ">", null, 0);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, null, 4, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper("bye", 3, true, 'a', ">", null, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "bye", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, "bye", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, null, 3, true, 'a', ">", 0.5, 1);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, null, 4, true, 'a', ">", 0.5, 0);
        SextupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, null, null, null, null, "null", null, 1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "hi", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "hi", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "hi", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "bye", 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "bye", 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, "bye", 3, true, 'a', ">", null, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, null, 3, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(null, null, null, null, "null", null, null, 4, true, 'a', ">", 0.5, -1);
        SextupleComparator_compare_helper(
                null, null, null, null, "null", null, null, null, null, null, "null", null, 0
        );
    }
}
