package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.ordering.Ordering;
import mho.wheels.ordering.comparators.LexComparator;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class SeptupleTest {
    private static final Septuple.SeptupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering,
                Double,
                Iterable<Integer>
                > PC = new Septuple.SeptupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Double>naturalOrder()),
                    Comparator.nullsFirst(new LexComparator<>())
                );

    private static final @NotNull List<Integer> x = Arrays.asList(1, 0);

    private static <A, B, C, D, E, F, G> void constructor_helper(
            A a,
            B b,
            C c,
            D d,
            E e,
            F f,
            G g,
            @NotNull String output
    ) {
        aeq(new Septuple<>(a, b, c, d, e, f, g), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', GT, 0.5, x, "(hi, 3, true, a, >, 0.5, [1, 0])");
        constructor_helper("hi", 3, true, 'a', GT, 0.5, null, "(hi, 3, true, a, >, 0.5, null)");
        constructor_helper(null, 3, true, 'a', GT, 0.5, x, "(null, 3, true, a, >, 0.5, [1, 0])");
        constructor_helper(null, null, null, null, null, null, null, "(null, null, null, null, null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, T d, T e, T f, T g, @NotNull String output) {
        aeq(Septuple.toList(new Septuple<>(a, b, c, d, e, f, g)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, 4, 5, 6, 7, "[1, 2, 3, 4, 5, 6, 7]");
        toList_helper("hi", "bye", "hey", "yo", "ayy", "hello", "oy", "[hi, bye, hey, yo, ayy, hello, oy]");
        toList_helper(1, null, null, null, null, null, null, "[1, null, null, null, null, null, null]");
    }

    private static void fromList_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Septuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get()),
                output
        );
    }

    private static void fromList_fail_helper(@NotNull String input) {
        try {
            Septuple.fromList(Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(input).get());
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testFromList() {
        fromList_helper("[1, 2, 3, 4, 5, 6, 7]", "(1, 2, 3, 4, 5, 6, 7)");
        fromList_helper("[1, null, null, null, null, null, null]", "(1, null, null, null, null, null, null)");

        fromList_fail_helper("[]");
        fromList_fail_helper("[1, 2, 3, 4, 5, 6]");
        fromList_fail_helper("[1, 2, 3, 4, 5, 6, 7, 8]");
    }

    private static void compare_helper(
            @NotNull String pa,
            int pb,
            boolean pc,
            char pd,
            @NotNull String pe,
            double pf,
            @NotNull String pg,
            @NotNull String qa,
            int qb,
            boolean qc,
            char qd,
            @NotNull String qe,
            double qf,
            @NotNull String qg,
            @NotNull String output
    ) {
        aeq(
                Septuple.compare(
                        new Septuple<>(pa, pb, pc, pd, Readers.readOrderingStrict(pe).get(), pf, pg),
                        new Septuple<>(qa, qb, qc, qd, Readers.readOrderingStrict(qe).get(), qf, qg)
                ),
                output
        );
    }

    @Test
    public void testCompare() {
        compare_helper("hi", 3, true, 'a', ">", 0.5, "x", "hi", 3, true, 'a', ">", 0.5, "x", "=");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "x", "hi", 4, true, 'a', ">", 0.5, "x", "<");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "x", "bye", 3, true, 'a', ">", 0.5, "x", ">");
        compare_helper("hi", 3, true, 'a', ">", 0.5, "x", "bye", 4, true, 'a', ">", 0.5, "x", ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "x", "hi", 3, true, 'a', ">", 0.5, "x", ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "x", "hi", 4, true, 'a', ">", 0.5, "x", "=");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "x", "bye", 3, true, 'a', ">", 0.5, "x", ">");
        compare_helper("hi", 4, true, 'a', ">", 0.5, "x", "bye", 4, true, 'a', ">", 0.5, "x", ">");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "x", "hi", 3, true, 'a', ">", 0.5, "x", "<");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "x", "hi", 4, true, 'a', ">", 0.5, "x", "<");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "x", "bye", 3, true, 'a', ">", 0.5, "x", "=");
        compare_helper("bye", 3, true, 'a', ">", 0.5, "x", "bye", 4, true, 'a', ">", 0.5, "x", "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "x", "hi", 3, true, 'a', ">", 0.5, "x", "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "x", "hi", 4, true, 'a', ">", 0.5, "x", "<");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "x", "bye", 3, true, 'a', ">", 0.5, "x", ">");
        compare_helper("bye", 4, true, 'a', ">", 0.5, "x", "bye", 4, true, 'a', ">", 0.5, "x", "=");
    }

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            Double pf,
            List<Integer> pg,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            @NotNull String qe,
            Double qf,
            List<Integer> qg,
            boolean output
    ) {
        aeq(
                new Septuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get(),
                        pf,
                        pg
                ).equals(
                        new Septuple<>(
                                qa,
                                qb,
                                qc,
                                qd,
                                Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(qe).get(),
                                qf,
                                qg
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
            List<Integer> pg,
            Object x,
            boolean output
    ) {
        aeq(
                new Septuple<>(
                        pa,
                        pb,
                        pc,
                        pd,
                        Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get(),
                        pf,
                        pg
                ).equals(x),
                output
        );
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, true);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, x, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, null, true);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, "bye", 3, true, 'a', ">", 0.5, null, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, null, 3, true, 'a', ">", 0.5, x, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, null, null, null, null, "null", null, null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, true);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, false);
        equals_helper(null, null, null, null, "null", null, null, "hi", 3, true, 'a', ">", 0.5, x, false);
        equals_helper(null, null, null, null, "null", null, null, "hi", 3, true, 'a', ">", null, x, false);
        equals_helper(null, null, null, null, "null", null, null, null, 3, true, 'a', ">", 0.5, x, false);
        equals_helper(null, null, null, null, "null", null, null, null, null, null, null, "null", null, null, true);

        equals_helper("hi", 3, true, 'a', ">", 0.5, x, "null", false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, x, 0.5, false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, "null", false);
        equals_helper("hi", 3, true, 'a', ">", 0.5, null, 0.5, false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, "null", false);
        equals_helper(null, 3, true, 'a', ">", 0.5, x, 0.5, false);
        equals_helper(null, null, null, null, "null", null, null, "null", false);
        equals_helper(null, null, null, null, "null", null, null, 0.5, false);
    }

    private static void SeptupleComparator_compare_helper(
            String pa,
            Integer pb,
            Boolean pc,
            Character pd,
            @NotNull String pe,
            Double pf,
            List<Integer> pg,
            String qa,
            Integer qb,
            Boolean qc,
            Character qd,
            @NotNull String qe,
            Double qf,
            List<Integer> qg,
            int output
    ) {
        aeq(
                PC.compare(
                        new Septuple<>(
                                pa,
                                pb,
                                pc,
                                pd,
                                Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(pe).get(),
                                pf,
                                pg
                        ),
                        new Septuple<>(
                                qa,
                                qb,
                                qc,
                                qd,
                                Readers.readWithNullsStrict(Readers::readOrderingStrict).apply(qe).get(),
                                qf,
                                qg
                        )
                ),
                output
        );
    }

    @Test
    public void testSeptupleComparator_compare() {
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(
                "hi", 3, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 4, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(
                "hi", 4, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, null, 0);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "bye", 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "bye", 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("hi", 3, true, 'a', ">", 0.5, null, null, 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(
                "hi", 3, true, 'a', ">", 0.5, null, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(
                "bye", 3, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper("bye", 4, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, null, 1);
        SeptupleComparator_compare_helper(
                "bye", 4, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "hi", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "bye", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "bye", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, "bye", 3, true, 'a', ">", 0.5, null, 0);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper("bye", 3, true, 'a', ">", 0.5, null, null, 4, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(
                "bye", 3, true, 'a', ">", 0.5, null, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper(null, 3, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(
                null, 3, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "hi", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "hi", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "bye", 4, true, 'a', ">", 0.5, x, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, "bye", 3, true, 'a', ">", 0.5, null, -1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, null, 3, true, 'a', ">", 0.5, x, 1);
        SeptupleComparator_compare_helper(null, 4, true, 'a', ">", 0.5, x, null, 4, true, 'a', ">", 0.5, x, 0);
        SeptupleComparator_compare_helper(
                null, 4, true, 'a', ">", 0.5, x, null, null, null, null, "null", null, null, 1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "hi", 3, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "hi", 4, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "hi", 3, true, 'a', ">", 0.5, null, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "bye", 3, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "bye", 4, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, "bye", 3, true, 'a', ">", 0.5, null, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, null, 3, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, null, 4, true, 'a', ">", 0.5, x, -1
        );
        SeptupleComparator_compare_helper(
                null, null, null, null, "null", null, null, null, null, null, null, "null", null, null, 0
        );
    }
}
