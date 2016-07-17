package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.testing.Testing.*;

public class TripleTest {
    private static final Triple.TripleComparator<String, Integer, Boolean> PC = new Triple.TripleComparator<>(
            Comparator.nullsFirst(Comparator.<String>naturalOrder()),
            Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
            Comparator.nullsFirst(Comparator.<Boolean>naturalOrder())
    );

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

    private static void equals_helper(
            String pa,
            Integer pb,
            Boolean pc,
            String qa,
            Integer qb,
            Boolean qc,
            boolean output
    ) {
        aeq(new Triple<>(pa, pb, pc).equals(new Triple<>(qa, qb, qc)), output);
    }

    private static void equals_helper(String pa, Integer pb, Boolean pc, Object x, boolean output) {
        aeq(new Triple<>(pa, pb, pc).equals(x), output);
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, true, "hi", 3, true, true);
        equals_helper("hi", 3, true, "hi", 4, true, false);
        equals_helper("hi", 3, true, "bye", 3, true, false);
        equals_helper("hi", 3, true, "hi", 3, null, false);
        equals_helper("hi", 3, true, null, 3, true, false);
        equals_helper("hi", 3, true, null, null, null, false);
        equals_helper("hi", 3, null, "hi", 3, true, false);
        equals_helper("hi", 3, null, "hi", 3, null, true);
        equals_helper("hi", 3, null, "bye", 3, null, false);
        equals_helper("hi", 3, null, null, 3, true, false);
        equals_helper("hi", 3, null, null, null, null, false);
        equals_helper(null, 3, true, "hi", 3, true, false);
        equals_helper(null, 3, true, "hi", 3, null, false);
        equals_helper(null, 3, true, null, 3, true, true);
        equals_helper(null, 3, true, null, 4, true, false);
        equals_helper(null, 3, true, null, null, null, false);
        equals_helper(null, null, null, "hi", 3, true, false);
        equals_helper(null, null, null, "hi", 3, null, false);
        equals_helper(null, null, null, null, 3, true, false);
        equals_helper(null, null, null, null, null, null, true);

        equals_helper("hi", 3, true, null, false);
        equals_helper("hi", 3, true, 0.5, false);
        equals_helper("hi", 3, null, null, false);
        equals_helper("hi", 3, null, 0.5, false);
        equals_helper(null, 3, true, null, false);
        equals_helper(null, 3, true, 0.5, false);
        equals_helper(null, null, null, null, false);
        equals_helper(null, null, null, 0.5, false);
    }

    private static void hashCode_helper(String a, Integer b, Boolean c, int output) {
        aeq(new Triple<>(a, b, c).hashCode(), output);
    }

    @Test
    public void testHashCode() {
        hashCode_helper("hi", 3, true, 3200493);
        hashCode_helper("hi", 3, null, 3199262);
        hashCode_helper(null, 3, true, 1324);
        hashCode_helper(null, null, null, 0);
    }

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Triple.readStrict(
                        input,
                        Readers.readWithNullsStrict(Readers::readStringStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict),
                        Readers.readWithNullsStrict(Readers::readBooleanStrict)
                ),
                output
        );
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("(hi, 3, true)", "Optional[(hi, 3, true)]");
        readStrict_helper("(hi, 3, null)", "Optional[(hi, 3, null)]");
        readStrict_helper("(null, 3, true)", "Optional[(null, 3, true)]");
        readStrict_helper("(null, null, null)", "Optional[(null, null, null)]");

        readStrict_helper("hi, 3, true", "Optional.empty");
        readStrict_helper("(hi, 3, True)", "Optional.empty");
        readStrict_helper("(hi, 3, true", "Optional.empty");
        readStrict_helper("hi, 3, true)", "Optional.empty");
        readStrict_helper("(hi,3,true)", "Optional.empty");
        readStrict_helper("null", "Optional.empty");
    }

    private static void TripleComparator_compare_helper(
            String pa,
            Integer pb,
            Boolean pc,
            String qa,
            Integer qb,
            Boolean qc,
            int output
    ) {
        aeq(PC.compare(new Triple<>(pa, pb, pc), new Triple<>(qa, qb, qc)), output);
    }

    @Test
    public void testTripleComparator_compare() {
        TripleComparator_compare_helper("hi", 3, true, "hi", 3, true, 0);
        TripleComparator_compare_helper("hi", 3, true, "hi", 4, true, -1);
        TripleComparator_compare_helper("hi", 3, true, "hi", 3, null, 1);
        TripleComparator_compare_helper("hi", 3, true, "bye", 3, true, 1);
        TripleComparator_compare_helper("hi", 3, true, "bye", 4, true, 1);
        TripleComparator_compare_helper("hi", 3, true, "bye", 3, null, 1);
        TripleComparator_compare_helper("hi", 3, true, null, 3, true, 1);
        TripleComparator_compare_helper("hi", 3, true, null, 4, true, 1);
        TripleComparator_compare_helper("hi", 3, true, null, null, null, 1);
        TripleComparator_compare_helper("hi", 4, true, "hi", 3, true, 1);
        TripleComparator_compare_helper("hi", 4, true, "hi", 4, true, 0);
        TripleComparator_compare_helper("hi", 4, true, "hi", 3, null, 1);
        TripleComparator_compare_helper("hi", 4, true, "bye", 3, true, 1);
        TripleComparator_compare_helper("hi", 4, true, "bye", 4, true, 1);
        TripleComparator_compare_helper("hi", 4, true, "bye", 3, null, 1);
        TripleComparator_compare_helper("hi", 4, true, null, 3, true, 1);
        TripleComparator_compare_helper("hi", 4, true, null, 4, true, 1);
        TripleComparator_compare_helper("hi", 4, true, null, null, null, 1);
        TripleComparator_compare_helper("hi", 3, null, "hi", 3, true, -1);
        TripleComparator_compare_helper("hi", 3, null, "hi", 4, true, -1);
        TripleComparator_compare_helper("hi", 3, null, "hi", 3, null, 0);
        TripleComparator_compare_helper("hi", 3, null, "bye", 3, true, 1);
        TripleComparator_compare_helper("hi", 3, null, "bye", 4, true, 1);
        TripleComparator_compare_helper("hi", 3, null, "bye", 3, null, 1);
        TripleComparator_compare_helper("hi", 3, null, null, 3, true, 1);
        TripleComparator_compare_helper("hi", 3, null, null, 4, true, 1);
        TripleComparator_compare_helper("hi", 3, null, null, null, null, 1);
        TripleComparator_compare_helper("bye", 3, true, "hi", 3, true, -1);
        TripleComparator_compare_helper("bye", 3, true, "hi", 4, true, -1);
        TripleComparator_compare_helper("bye", 3, true, "hi", 3, null, -1);
        TripleComparator_compare_helper("bye", 3, true, "bye", 3, true, 0);
        TripleComparator_compare_helper("bye", 3, true, "bye", 4, true, -1);
        TripleComparator_compare_helper("bye", 3, true, "bye", 3, null, 1);
        TripleComparator_compare_helper("bye", 3, true, null, 3, true, 1);
        TripleComparator_compare_helper("bye", 3, true, null, 4, true, 1);
        TripleComparator_compare_helper("bye", 3, true, null, null, null, 1);
        TripleComparator_compare_helper("bye", 4, true, "hi", 3, true, -1);
        TripleComparator_compare_helper("bye", 4, true, "hi", 4, true, -1);
        TripleComparator_compare_helper("bye", 4, true, "hi", 3, null, -1);
        TripleComparator_compare_helper("bye", 4, true, "bye", 3, null, 1);
        TripleComparator_compare_helper("bye", 4, true, "bye", 4, true, 0);
        TripleComparator_compare_helper("bye", 4, true, "bye", 3, null, 1);
        TripleComparator_compare_helper("bye", 4, true, null, 3, true, 1);
        TripleComparator_compare_helper("bye", 4, true, null, 4, true, 1);
        TripleComparator_compare_helper("bye", 4, true, null, null, null, 1);
        TripleComparator_compare_helper("bye", 3, null, "hi", 3, true, -1);
        TripleComparator_compare_helper("bye", 3, null, "hi", 4, true, -1);
        TripleComparator_compare_helper("bye", 3, null, "hi", 3, null, -1);
        TripleComparator_compare_helper("bye", 3, null, "bye", 3, true, -1);
        TripleComparator_compare_helper("bye", 3, null, "bye", 4, true, -1);
        TripleComparator_compare_helper("bye", 3, null, "bye", 3, null, 0);
        TripleComparator_compare_helper("bye", 3, null, null, 3, true, 1);
        TripleComparator_compare_helper("bye", 3, null, null, 4, true, 1);
        TripleComparator_compare_helper("bye", 3, null, null, null, null, 1);
        TripleComparator_compare_helper(null, 3, true, "hi", 3, true, -1);
        TripleComparator_compare_helper(null, 3, true, "hi", 4, true, -1);
        TripleComparator_compare_helper(null, 3, true, "hi", 3, null, -1);
        TripleComparator_compare_helper(null, 3, true, "bye", 3, true, -1);
        TripleComparator_compare_helper(null, 3, true, "bye", 4, true, -1);
        TripleComparator_compare_helper(null, 3, true, "bye", 3, null, -1);
        TripleComparator_compare_helper(null, 3, true, null, 3, true, 0);
        TripleComparator_compare_helper(null, 3, true, null, 4, true, -1);
        TripleComparator_compare_helper(null, 3, true, null, null, null, 1);
        TripleComparator_compare_helper(null, 4, true, "hi", 3, true, -1);
        TripleComparator_compare_helper(null, 4, true, "hi", 4, true, -1);
        TripleComparator_compare_helper(null, 4, true, "hi", 3, null, -1);
        TripleComparator_compare_helper(null, 4, true, "bye", 3, true, -1);
        TripleComparator_compare_helper(null, 4, true, "bye", 4, true, -1);
        TripleComparator_compare_helper(null, 4, true, "bye", 3, null, -1);
        TripleComparator_compare_helper(null, 4, true, null, 3, true, 1);
        TripleComparator_compare_helper(null, 4, true, null, 4, true, 0);
        TripleComparator_compare_helper(null, 4, true, null, null, null, 1);
        TripleComparator_compare_helper(null, null, null, "hi", 3, true, -1);
        TripleComparator_compare_helper(null, null, null, "hi", 4, true, -1);
        TripleComparator_compare_helper(null, null, null, "hi", 3, null, -1);
        TripleComparator_compare_helper(null, null, null, "bye", 3, true, -1);
        TripleComparator_compare_helper(null, null, null, "bye", 4, true, -1);
        TripleComparator_compare_helper(null, null, null, "bye", 3, null, -1);
        TripleComparator_compare_helper(null, null, null, null, 3, true, -1);
        TripleComparator_compare_helper(null, null, null, null, 4, true, -1);
        TripleComparator_compare_helper(null, null, null, null, null, null, 0);
    }
}
