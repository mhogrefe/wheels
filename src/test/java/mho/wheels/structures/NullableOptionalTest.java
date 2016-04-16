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
import static org.junit.Assert.*;

public class NullableOptionalTest {
    @Test
    public void testEmpty() {
        aeq(empty(), "NullableOptional.empty");
    }

    @Test
    public void testOf() {
        aeq(of(1), "NullableOptional[1]");
        aeq(of("hello"), "NullableOptional[hello]");
        aeq(of(null), "NullableOptional[null]");
    }

    private static void isPresent_true_helper(@NotNull String s) {
        assertTrue(readNullableOptionalInteger(s).isPresent());
    }

    private static void isPresent_false_helper(@NotNull String s) {
        assertFalse(readNullableOptionalInteger(s).isPresent());
    }

    @Test
    public void testIsPresent() {
        isPresent_true_helper("NullableOptional[1]");
        isPresent_true_helper("NullableOptional[-5]");
        isPresent_true_helper("NullableOptional[null]");
        isPresent_false_helper("NullableOptional.empty");
    }

    private static void get_helper(@NotNull String x, @NotNull String output) {
        aeq(readNullableOptionalInteger(x).get(), output);
    }

    private static void get_null_helper(@NotNull String x) {
        assertNull(readNullableOptionalInteger(x).get());
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
        get_null_helper("NullableOptional[null]");
        get_fail_helper("NullableOptional.empty");
    }

    private static void fromOptional_helper(@NotNull String x, @NotNull String output) {
        aeq(fromOptional(readOptionalInteger(x)), output);
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
