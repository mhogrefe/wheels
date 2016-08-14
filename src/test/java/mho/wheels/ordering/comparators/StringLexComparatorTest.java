package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.fail;

public class StringLexComparatorTest {
    private static final @NotNull Comparator<Character> VOWELS_BEFORE_CONSONANTS =
            new StringBasedComparator("aeiouybcdfghjklmnpqrstvwxz");

    @Test
    public void testConstructor() {
        new StringLexComparator();
    }

    @Test
    public void testConstructor_Comparator() {
        new StringLexComparator(VOWELS_BEFORE_CONSONANTS);
    }

    private static void compare_helper(@NotNull StringLexComparator comparator, @NotNull String list) {
        Testing.testCompareToHelper(comparator, readStringList(list));
    }

    private static void compare_fail_helper(
            @NotNull StringLexComparator comparator,
            @NotNull String s,
            @NotNull String t
    ) {
        try {
            comparator.compare(s, t);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCompare() {
        compare_helper(new StringLexComparator(), "[cat, caterpillar, dog, elephant, mouse, octopus]");
        compare_helper(
                new StringLexComparator(VOWELS_BEFORE_CONSONANTS),
                "[elephant, octopus, cat, caterpillar, dog, mouse]"
        );

        compare_fail_helper(new StringLexComparator(VOWELS_BEFORE_CONSONANTS), "cat", "123");
        compare_fail_helper(new StringLexComparator(new StringBasedComparator("")), "cat", "dog");
    }

    private static @NotNull List<String> readStringList(@NotNull String s) {
        return Readers.readListStrict(Readers::readStringStrict).apply(s).get();
    }
}
