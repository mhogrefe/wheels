package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.testing.Testing.aeq;

public class PairTest {
    private static final Pair.PairComparator<String, Integer> PC = new Pair.PairComparator<>(
            Comparator.nullsFirst(Comparator.<String>naturalOrder()),
            Comparator.nullsFirst(Comparator.<Integer>naturalOrder())
    );

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

    private static void equals_helper(String pa, Integer pb, String qa, Integer qb, boolean output) {
        aeq(new Pair<>(pa, pb).equals(new Pair<>(qa, qb)), output);
    }

    private static void equals_helper(String pa, Integer pb, Object x, boolean output) {
        aeq(new Pair<>(pa, pb).equals(x), output);
    }

    @Test
    public void testEquals() {
        equals_helper("hi", 3, "hi", 3, true);
        equals_helper("hi", 3, "hi", 4, false);
        equals_helper("hi", 3, "bye", 3, false);
        equals_helper("hi", 3, "hi", null, false);
        equals_helper("hi", 3, null, 3, false);
        equals_helper("hi", 3, null, null, false);
        equals_helper("hi", null, "hi", 3, false);
        equals_helper("hi", null, "hi", null, true);
        equals_helper("hi", null, "bye", null, false);
        equals_helper("hi", null, null, 3, false);
        equals_helper("hi", null, null, null, false);
        equals_helper(null, 3, "hi", 3, false);
        equals_helper(null, 3, "hi", null, false);
        equals_helper(null, 3, null, 3, true);
        equals_helper(null, 3, null, 4, false);
        equals_helper(null, 3, null, null, false);
        equals_helper(null, null, "hi", 3, false);
        equals_helper(null, null, "hi", null, false);
        equals_helper(null, null, null, 3, false);
        equals_helper(null, null, null, null, true);

        equals_helper("hi", 3, null, false);
        equals_helper("hi", 3, 0.5, false);
        equals_helper("hi", null, null, false);
        equals_helper("hi", null, 0.5, false);
        equals_helper(null, 3, null, false);
        equals_helper(null, 3, 0.5, false);
        equals_helper(null, null, null, false);
        equals_helper(null, null, 0.5, false);
    }

    private static void hashCode_helper(String a, Integer b, int output) {
        aeq(new Pair<>(a, b).hashCode(), output);
    }

    @Test
    public void testHashCode() {
        hashCode_helper("hi", 3, 103202);
        hashCode_helper("hi", null, 103199);
        hashCode_helper(null, 3, 3);
        hashCode_helper(null, null, 0);
    }

    private static void readStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(
                Pair.readStrict(
                        input,
                        Readers.readWithNullsStrict(Readers::readStringStrict),
                        Readers.readWithNullsStrict(Readers::readIntegerStrict)
                ),
                output
        );
    }

    @Test
    public void testReadStrict() {
        readStrict_helper("(hi, 3)", "Optional[(hi, 3)]");
        readStrict_helper("(hi, null)", "Optional[(hi, null)]");
        readStrict_helper("(null, 3)", "Optional[(null, 3)]");
        readStrict_helper("(null, null)", "Optional[(null, null)]");

        readStrict_helper("hi, 3", "Optional.empty");
        readStrict_helper("(hi, 3.0)", "Optional.empty");
        readStrict_helper("(hi, 3", "Optional.empty");
        readStrict_helper("hi, 3)", "Optional.empty");
        readStrict_helper("(hi,3)", "Optional.empty");
        readStrict_helper("null", "Optional.empty");
    }

    private static void PairComparator_compare_helper(String pa, Integer pb, String qa, Integer qb, int output) {
        aeq(PC.compare(new Pair<>(pa, pb), new Pair<>(qa, qb)), output);
    }

    @Test
    public void testPairComparator_compare() {
        PairComparator_compare_helper("hi", 3, "hi", 3, 0);
        PairComparator_compare_helper("hi", 3, "hi", 4, -1);
        PairComparator_compare_helper("hi", 3, "hi", null, 1);
        PairComparator_compare_helper("hi", 3, "bye", 3, 1);
        PairComparator_compare_helper("hi", 3, "bye", 4, 1);
        PairComparator_compare_helper("hi", 3, "bye", null, 1);
        PairComparator_compare_helper("hi", 3, null, 3, 1);
        PairComparator_compare_helper("hi", 3, null, 4, 1);
        PairComparator_compare_helper("hi", 3, null, null, 1);
        PairComparator_compare_helper("hi", 4, "hi", 3, 1);
        PairComparator_compare_helper("hi", 4, "hi", 4, 0);
        PairComparator_compare_helper("hi", 4, "hi", null, 1);
        PairComparator_compare_helper("hi", 4, "bye", 3, 1);
        PairComparator_compare_helper("hi", 4, "bye", 4, 1);
        PairComparator_compare_helper("hi", 4, "bye", null, 1);
        PairComparator_compare_helper("hi", 4, null, 3, 1);
        PairComparator_compare_helper("hi", 4, null, 4, 1);
        PairComparator_compare_helper("hi", 4, null, null, 1);
        PairComparator_compare_helper("hi", null, "hi", 3, -1);
        PairComparator_compare_helper("hi", null, "hi", 4, -1);
        PairComparator_compare_helper("hi", null, "hi", null, 0);
        PairComparator_compare_helper("hi", null, "bye", 3, 1);
        PairComparator_compare_helper("hi", null, "bye", 4, 1);
        PairComparator_compare_helper("hi", null, "bye", null, 1);
        PairComparator_compare_helper("hi", null, null, 3, 1);
        PairComparator_compare_helper("hi", null, null, 4, 1);
        PairComparator_compare_helper("hi", null, null, null, 1);
        PairComparator_compare_helper("bye", 3, "hi", 3, -1);
        PairComparator_compare_helper("bye", 3, "hi", 4, -1);
        PairComparator_compare_helper("bye", 3, "hi", null, -1);
        PairComparator_compare_helper("bye", 3, "bye", 3, 0);
        PairComparator_compare_helper("bye", 3, "bye", 4, -1);
        PairComparator_compare_helper("bye", 3, "bye", null, 1);
        PairComparator_compare_helper("bye", 3, null, 3, 1);
        PairComparator_compare_helper("bye", 3, null, 4, 1);
        PairComparator_compare_helper("bye", 3, null, null, 1);
        PairComparator_compare_helper("bye", 4, "hi", 3, -1);
        PairComparator_compare_helper("bye", 4, "hi", 4, -1);
        PairComparator_compare_helper("bye", 4, "hi", null, -1);
        PairComparator_compare_helper("bye", 4, "bye", 3, 1);
        PairComparator_compare_helper("bye", 4, "bye", 4, 0);
        PairComparator_compare_helper("bye", 4, "bye", null, 1);
        PairComparator_compare_helper("bye", 4, null, 3, 1);
        PairComparator_compare_helper("bye", 4, null, 4, 1);
        PairComparator_compare_helper("bye", 4, null, null, 1);
        PairComparator_compare_helper("bye", null, "hi", 3, -1);
        PairComparator_compare_helper("bye", null, "hi", 4, -1);
        PairComparator_compare_helper("bye", null, "hi", null, -1);
        PairComparator_compare_helper("bye", null, "bye", 3, -1);
        PairComparator_compare_helper("bye", null, "bye", 4, -1);
        PairComparator_compare_helper("bye", null, "bye", null, 0);
        PairComparator_compare_helper("bye", null, null, 3, 1);
        PairComparator_compare_helper("bye", null, null, 4, 1);
        PairComparator_compare_helper("bye", null, null, null, 1);
        PairComparator_compare_helper(null, 3, "hi", 3, -1);
        PairComparator_compare_helper(null, 3, "hi", 4, -1);
        PairComparator_compare_helper(null, 3, "hi", null, -1);
        PairComparator_compare_helper(null, 3, "bye", 3, -1);
        PairComparator_compare_helper(null, 3, "bye", 4, -1);
        PairComparator_compare_helper(null, 3, "bye", null, -1);
        PairComparator_compare_helper(null, 3, null, 3, 0);
        PairComparator_compare_helper(null, 3, null, 4, -1);
        PairComparator_compare_helper(null, 3, null, null, 1);
        PairComparator_compare_helper(null, 4, "hi", 3, -1);
        PairComparator_compare_helper(null, 4, "hi", 4, -1);
        PairComparator_compare_helper(null, 4, "hi", null, -1);
        PairComparator_compare_helper(null, 4, "bye", 3, -1);
        PairComparator_compare_helper(null, 4, "bye", 4, -1);
        PairComparator_compare_helper(null, 4, "bye", null, -1);
        PairComparator_compare_helper(null, 4, null, 3, 1);
        PairComparator_compare_helper(null, 4, null, 4, 0);
        PairComparator_compare_helper(null, 4, null, null, 1);
        PairComparator_compare_helper(null, null, "hi", 3, -1);
        PairComparator_compare_helper(null, null, "hi", 4, -1);
        PairComparator_compare_helper(null, null, "hi", null, -1);
        PairComparator_compare_helper(null, null, "bye", 3, -1);
        PairComparator_compare_helper(null, null, "bye", 4, -1);
        PairComparator_compare_helper(null, null, "bye", null, -1);
        PairComparator_compare_helper(null, null, null, 3, -1);
        PairComparator_compare_helper(null, null, null, 4, -1);
        PairComparator_compare_helper(null, null, null, null, 0);
    }
}
