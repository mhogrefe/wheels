package mho.wheels.structures;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static mho.wheels.structures.NullableOptional.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.testEqualsHelper;
import static org.junit.Assert.fail;

public class NullableOptionalTest {
    @Test
    public void testEmpty() {
        aeq(empty(), "NullableOptional.empty");
    }

    private static <T> void of_helper(T input, @NotNull String output) {
        aeq(of(input), output);
    }

    @Test
    public void testOf() {
        of_helper(1, "NullableOptional[1]");
        of_helper("hello", "NullableOptional[hello]");
        of_helper(null, "NullableOptional[null]");
    }

    private static void isPresent_helper(@NotNull String input, boolean output) {
        aeq(readNullableOptionalInteger(input).isPresent(), output);
    }

    @Test
    public void testIsPresent() {
        isPresent_helper("NullableOptional[1]", true);
        isPresent_helper("NullableOptional[-5]", true);
        isPresent_helper("NullableOptional[null]", true);
        isPresent_helper("NullableOptional.empty", false);
    }

    private static void get_helper(@NotNull String input, @NotNull String output) {
        aeq(readNullableOptionalInteger(input).get(), output);
    }

    private static void get_fail_helper(@NotNull String x) {
        NullableOptional<Integer> no = readNullableOptionalInteger(x);
        try {
            no.get();
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    @Test
    public void testGet() {
        get_helper("NullableOptional[1]", "1");
        get_helper("NullableOptional[-5]", "-5");
        get_helper("NullableOptional[null]", "null");

        get_fail_helper("NullableOptional.empty");
    }

    private static void fromOptional_helper(@NotNull String input, @NotNull String output) {
        aeq(fromOptional(readOptionalInteger(input)), output);
    }

    @Test
    public void testFromOptional() {
        fromOptional_helper("Optional[1]", "NullableOptional[1]");
        fromOptional_helper("Optional[-5]", "NullableOptional[-5]");
        fromOptional_helper("Optional.empty", "NullableOptional.empty");
    }

    private static void orElse_helper(@NotNull String no, Integer other, Integer output) {
        aeq(readNullableOptionalInteger(no).orElse(other), output);
    }

    @Test
    public void testOrElse() {
        orElse_helper("NullableOptional.empty", 0, 0);
        orElse_helper("NullableOptional.empty", 5, 5);
        orElse_helper("NullableOptional.empty", null, null);
        orElse_helper("NullableOptional[null]", 0, null);
        orElse_helper("NullableOptional[null]", 5, null);
        orElse_helper("NullableOptional[null]", null, null);
        orElse_helper("NullableOptional[3]", 0, 3);
        orElse_helper("NullableOptional[3]", 5, 3);
        orElse_helper("NullableOptional[3]", null, 3);
    }

    private static void map_helper(@NotNull String no, Integer k, Integer v, @NotNull String output) {
        aeq(
                readNullableOptionalInteger(no)
                        .map(new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(k, v)))),
                output
        );
    }

    private static void map_fail_helper(@NotNull String no, Integer k, Integer v) {
        try {
            readNullableOptionalInteger(no)
                    .map(new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(k, v))));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testMap() {
        map_helper("NullableOptional.empty", 2, 3, "NullableOptional.empty");
        map_helper("NullableOptional.empty", 2, null, "NullableOptional.empty");
        map_helper("NullableOptional.empty", null, 3, "NullableOptional.empty");
        map_helper("NullableOptional.empty", null, null, "NullableOptional.empty");
        map_helper("NullableOptional[2]", 2, 3, "NullableOptional[3]");
        map_helper("NullableOptional[2]", 2, null, "NullableOptional[null]");
        map_helper("NullableOptional[null]", null, 3, "NullableOptional[3]");
        map_helper("NullableOptional[null]", null, null, "NullableOptional[null]");

        map_fail_helper("NullableOptional[2]", 1, 3);
        map_fail_helper("NullableOptional[null]", 1, 3);
    }

    @Test
    public void testEquals() {
        testEqualsHelper(
                readNullableOptionalIntegerList("[NullableOptional[1], NullableOptional[-5], NullableOptional[null]," +
                        " NullableOptional.empty]"),
                readNullableOptionalIntegerList("[NullableOptional[1], NullableOptional[-5], NullableOptional[null]," +
                        " NullableOptional.empty]")
        );
    }

    private static void hashCode_helper(@NotNull String input, int hashCode) {
        aeq(readNullableOptionalInteger(input).hashCode(), hashCode);
    }

    @Test
    public void testHashCode() {
        hashCode_helper("NullableOptional[1]", 32);
        hashCode_helper("NullableOptional[-5]", 26);
        hashCode_helper("NullableOptional[null]", 31);
        hashCode_helper("NullableOptional.empty", 0);
    }

    private static @NotNull Optional<Integer> readOptionalInteger(@NotNull String s) {
        return Readers.readOptionalStrict(Readers::readIntegerStrict).apply(s).get();
    }

    private static @NotNull NullableOptional<Integer> readNullableOptionalInteger(@NotNull String s) {
        return Readers.readNullableOptionalStrict(Readers.readWithNullsStrict(Readers::readIntegerStrict))
                .apply(s).get();
    }

    private static @NotNull List<NullableOptional<Integer>> readNullableOptionalIntegerList(@NotNull String s) {
        return Readers.readListStrict(
                Readers.readNullableOptionalStrict(Readers.readWithNullsStrict(Readers::readIntegerStrict))
        ).apply(s).get();
    }
}
