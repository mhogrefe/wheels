package mho.wheels.ordering;

import mho.wheels.io.Readers;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

public class OrderingTest {
    private static void fromInt_helper(int input, @NotNull String output) {
        aeq(fromInt(input), output);
    }

    @Test
    public void testFromInt() {
        fromInt_helper(0, "=");
        fromInt_helper(1, ">");
        fromInt_helper(2, ">");
        fromInt_helper(-1, "<");
        fromInt_helper(-2, "<");
    }

    private static void toInt_helper(@NotNull String input, int output) {
        aeq(Readers.readOrderingStrict(input).get().toInt(), output);
    }

    @Test
    public void testToInt() {
        toInt_helper("=", 0);
        toInt_helper(">", 1);
        toInt_helper("<", -1);
    }

    private static void invert_helper(@NotNull String input, @NotNull String output) {
        aeq(Readers.readOrderingStrict(input).get().invert(), output);
    }

    @Test
    public void testInvert() {
        invert_helper("=", "=");
        invert_helper(">", "<");
        invert_helper("<", ">");
    }

    private static void compare_helper(int i, int j, @NotNull String output) {
        aeq(compare(i, j), output);
    }

    @Test
    public void testCompare() {
        compare_helper(0, 0, "=");
        compare_helper(1, 2, "<");
        compare_helper(4, 3, ">");
    }
}
