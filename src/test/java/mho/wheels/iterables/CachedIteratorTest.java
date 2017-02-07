package mho.wheels.iterables;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mho.wheels.testing.Testing.*;

public class CachedIteratorTest {
    private static void constructor_helper(@NotNull Iterable<Integer> input) {
        new CachedIterator<>(input);
    }

    @Test
    public void testConstructor() {
        constructor_helper(Collections.emptyList());
        constructor_helper(Collections.singletonList(1));
        constructor_helper(Collections.singletonList(null));
        constructor_helper(Arrays.asList(1, 2, 3));
        constructor_helper(Arrays.asList(1, null, 3));
        constructor_helper(ExhaustiveProvider.INSTANCE.rangeUp(1));
        constructor_helper(IterableUtils.repeat(null));
    }

    private static void get_int_helper(@NotNull Iterable<Integer> xs, int i, @NotNull String output) {
        aeq(new CachedIterator<>(xs).get(i), output);
    }

    private static void get_int_fail_helper(@NotNull Iterable<Integer> xs, int i) {
        try {
            new CachedIterator<>(xs).get(i);
            Assert.fail();
        } catch (IndexOutOfBoundsException ignored) {}
    }

    @Test
    public void testGet_int() {
        get_int_helper(Collections.emptyList(), 0, "NullableOptional.empty");
        get_int_helper(Collections.emptyList(), 1, "NullableOptional.empty");
        get_int_helper(Collections.singletonList(1), 0, "NullableOptional[1]");
        get_int_helper(Collections.singletonList(1), 1, "NullableOptional.empty");
        get_int_helper(Collections.singletonList(null), 0, "NullableOptional[null]");
        get_int_helper(Collections.singletonList(null), 1, "NullableOptional.empty");
        get_int_helper(Arrays.asList(1, 2, 3), 0, "NullableOptional[1]");
        get_int_helper(Arrays.asList(1, 2, 3), 1, "NullableOptional[2]");
        get_int_helper(Arrays.asList(1, 2, 3), 2, "NullableOptional[3]");
        get_int_helper(Arrays.asList(1, null, 3), 0, "NullableOptional[1]");
        get_int_helper(Arrays.asList(1, null, 3), 1, "NullableOptional[null]");
        get_int_helper(Arrays.asList(1, null, 3), 2, "NullableOptional[3]");
        get_int_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), 0, "NullableOptional[1]");
        get_int_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), 10, "NullableOptional[11]");
        get_int_helper(IterableUtils.repeat(null), 0, "NullableOptional[null]");
        get_int_helper(IterableUtils.repeat(null), 10, "NullableOptional[null]");

        get_int_fail_helper(Collections.emptyList(), -1);
        get_int_fail_helper(Collections.singletonList(1), -1);
        get_int_fail_helper(Collections.singletonList(null), -1);
        get_int_fail_helper(Arrays.asList(1, 2, 3), -1);
        get_int_fail_helper(Arrays.asList(1, null, 3), -1);
        get_int_fail_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), -1);
        get_int_fail_helper(IterableUtils.repeat(null), -1);
    }

    private static void get_BigInteger_helper(
            @NotNull Iterable<Integer> xs,
            @NotNull String i,
            @NotNull String output
    ) {
        aeq(new CachedIterator<>(xs).get(Readers.readBigIntegerStrict(i).get()), output);
    }

    private static void get_BigInteger_fail_helper(@NotNull Iterable<Integer> xs, @NotNull String i) {
        try {
            new CachedIterator<>(xs).get(Readers.readBigIntegerStrict(i).get());
            Assert.fail();
        } catch (IndexOutOfBoundsException ignored) {}
    }

    @Test
    public void testGet_BigInteger() {
        get_BigInteger_helper(Collections.emptyList(), "0", "NullableOptional.empty");
        get_BigInteger_helper(Collections.emptyList(), "1", "NullableOptional.empty");
        get_BigInteger_helper(Collections.singletonList(1), "0", "NullableOptional[1]");
        get_BigInteger_helper(Collections.singletonList(1), "1", "NullableOptional.empty");
        get_BigInteger_helper(Collections.singletonList(null), "0", "NullableOptional[null]");
        get_BigInteger_helper(Collections.singletonList(null), "1", "NullableOptional.empty");
        get_BigInteger_helper(Arrays.asList(1, 2, 3), "0", "NullableOptional[1]");
        get_BigInteger_helper(Arrays.asList(1, 2, 3), "1", "NullableOptional[2]");
        get_BigInteger_helper(Arrays.asList(1, 2, 3), "2", "NullableOptional[3]");
        get_BigInteger_helper(Arrays.asList(1, null, 3), "0", "NullableOptional[1]");
        get_BigInteger_helper(Arrays.asList(1, null, 3), "1", "NullableOptional[null]");
        get_BigInteger_helper(Arrays.asList(1, null, 3), "2", "NullableOptional[3]");
        get_BigInteger_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "0", "NullableOptional[1]");
        get_BigInteger_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "10", "NullableOptional[11]");
        get_BigInteger_helper(IterableUtils.repeat(null), "0", "NullableOptional[null]");
        get_BigInteger_helper(IterableUtils.repeat(null), "10", "NullableOptional[null]");

        get_BigInteger_fail_helper(Collections.emptyList(), "-1");
        get_BigInteger_fail_helper(Collections.singletonList(1), "-1");
        get_BigInteger_fail_helper(Collections.singletonList(null), "-1");
        get_BigInteger_fail_helper(Arrays.asList(1, 2, 3), "-1");
        get_BigInteger_fail_helper(Arrays.asList(1, null, 3), "-1");
        get_BigInteger_fail_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "-1");
        get_BigInteger_fail_helper(IterableUtils.repeat(null), "-1");
    }

    private static void get_Iterable_helper(
            @NotNull Iterable<Integer> xs,
            @NotNull String is,
            @NotNull String output
    ) {
        aeq(new CachedIterator<>(xs).get(readIntegerList(is)), output);
    }

    private static void get_Iterable_fail_helper(@NotNull Iterable<Integer> xs, @NotNull String is) {
        try {
            new CachedIterator<>(xs).get(readIntegerListWithNulls(is));
            Assert.fail();
        } catch (IndexOutOfBoundsException | NullPointerException ignored) {}
    }

    @Test
    public void testGet_Iterable() {
        get_Iterable_helper(Collections.emptyList(), "[]", "Optional[[]]");
        get_Iterable_helper(Collections.emptyList(), "[1, 2, 3]", "Optional.empty");
        get_Iterable_helper(Collections.singletonList(1), "[]", "Optional[[]]");
        get_Iterable_helper(Collections.singletonList(1), "[0]", "Optional[[1]]");
        get_Iterable_helper(Collections.singletonList(1), "[0, 1, 2]", "Optional.empty");
        get_Iterable_helper(Collections.singletonList(null), "[]", "Optional[[]]");
        get_Iterable_helper(Collections.singletonList(null), "[0]", "Optional[[null]]");
        get_Iterable_helper(Collections.singletonList(null), "[0, 1, 2]", "Optional.empty");
        get_Iterable_helper(Arrays.asList(1, 2, 3), "[]", "Optional[[]]");
        get_Iterable_helper(Arrays.asList(1, 2, 3), "[0]", "Optional[[1]]");
        get_Iterable_helper(Arrays.asList(1, 2, 3), "[0, 1, 2]", "Optional[[1, 2, 3]]");
        get_Iterable_helper(Arrays.asList(1, 2, 3), "[2, 0, 1, 0, 2, 2]", "Optional[[3, 1, 2, 1, 3, 3]]");
        get_Iterable_helper(Arrays.asList(1, null, 3), "[]", "Optional[[]]");
        get_Iterable_helper(Arrays.asList(1, null, 3), "[0]", "Optional[[1]]");
        get_Iterable_helper(Arrays.asList(1, null, 3), "[0, 1, 2]", "Optional[[1, null, 3]]");
        get_Iterable_helper(Arrays.asList(1, null, 3), "[2, 0, 1, 0, 2, 2]", "Optional[[3, 1, null, 1, 3, 3]]");
        get_Iterable_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "[]", "Optional[[]]");
        get_Iterable_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "[3, 1, 4, 1]", "Optional[[4, 2, 5, 2]]");
        get_Iterable_helper(IterableUtils.repeat(null), "[]", "Optional[[]]");
        get_Iterable_helper(IterableUtils.repeat(null), "[3, 2, 1]", "Optional[[null, null, null]]");

        get_Iterable_fail_helper(Collections.emptyList(), "[-1, 2, 3]");
        get_Iterable_fail_helper(Collections.singletonList(1), "[-1, 2, 3]");
        get_Iterable_fail_helper(Collections.singletonList(null), "[-1, 2, 3]");
        get_Iterable_fail_helper(Arrays.asList(1, 2, 3), "[-1, 2, 3]");
        get_Iterable_fail_helper(Arrays.asList(1, null, 3), "[-1, 2, 3]");
        get_Iterable_fail_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "[-1, 2, 3]");
        get_Iterable_fail_helper(IterableUtils.repeat(null), "[-1, 2, 3]");
        get_Iterable_fail_helper(Collections.emptyList(), "[null, 2, 3]");
        get_Iterable_fail_helper(Collections.singletonList(1), "[null, 2, 3]");
        get_Iterable_fail_helper(Collections.singletonList(null), "[null, 2, 3]");
        get_Iterable_fail_helper(Arrays.asList(1, 2, 3), "[null, 2, 3]");
        get_Iterable_fail_helper(Arrays.asList(1, null, 3), "[null, 2, 3]");
        get_Iterable_fail_helper(ExhaustiveProvider.INSTANCE.rangeUp(1), "[null, 2, 3]");
        get_Iterable_fail_helper(IterableUtils.repeat(null), "[null, 2, 3]");
    }

    @Test
    public void testKnownSize() {
        CachedIterator<Integer> xs;
        xs = new CachedIterator<>(Collections.emptyList());
        aeq(xs.knownSize(), "Optional[0]");

        xs = new CachedIterator<>(Collections.singletonList(1));
        aeq(xs.knownSize(), "Optional.empty");
        xs.get(0);
        aeq(xs.knownSize(), "Optional[1]");

        xs = new CachedIterator<>(Arrays.asList(1, 2, 3));
        aeq(xs.knownSize(), "Optional.empty");
        xs.get(1);
        aeq(xs.knownSize(), "Optional.empty");
        xs.get(2);
        aeq(xs.knownSize(), "Optional[3]");

        xs = new CachedIterator<>(ExhaustiveProvider.INSTANCE.rangeUp(1));
        aeq(xs.knownSize(), "Optional.empty");
        xs.get(100);
        aeq(xs.knownSize(), "Optional.empty");
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readListStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNullsStrict(Readers::readIntegerStrict).apply(s).get();
    }
}
