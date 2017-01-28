package mho.wheels.ordering.comparators;

import mho.wheels.io.Readers;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.fail;

public class StringShortlexComparatorTest {
    private static final @NotNull Comparator<Character> VOWELS_BEFORE_CONSONANTS =
            new StringBasedComparator("aeiouybcdfghjklmnpqrstvwxz");

    @Test
    public void testConstructor() {
        new StringShortlexComparator();
    }

    @Test
    public void testConstructor_Comparator() {
        new StringShortlexComparator(VOWELS_BEFORE_CONSONANTS);
    }

    private static void compare_helper(@NotNull StringShortlexComparator comparator, @NotNull String list) {
        Testing.testCompareToHelper(comparator, readStringList(list));
    }

    private static void compare_fail_helper(
            @NotNull StringShortlexComparator comparator,
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
        compare_helper(new StringShortlexComparator(), "[cat, owl, mouse, octopus, elephant, caterpillar]");
        compare_helper(
                new StringShortlexComparator(VOWELS_BEFORE_CONSONANTS),
                "[owl, cat, mouse, octopus, elephant, caterpillar]"
        );

        compare_fail_helper(new StringShortlexComparator(VOWELS_BEFORE_CONSONANTS), "cat", "123");
        compare_fail_helper(new StringShortlexComparator(new StringBasedComparator("")), "cat", "dog");
    }

    private static @NotNull List<String> readStringList(@NotNull String s) {
        return Readers.readListStrict(Readers::readStringStrict).apply(s).get();
    }
}
