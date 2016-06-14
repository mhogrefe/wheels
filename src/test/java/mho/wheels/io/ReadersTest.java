package mho.wheels.io;

import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.testing.Testing.aeq;
import static org.junit.Assert.fail;

public class ReadersTest {
    @Test
    public void testConstants() {
        aeq(MAX_POSITIVE_BYTE_LENGTH, 3);
        aeq(MAX_POSITIVE_SHORT_LENGTH, 5);
        aeq(MAX_POSITIVE_INTEGER_LENGTH, 10);
        aeq(MAX_POSITIVE_LONG_LENGTH, 19);
    }

    private static class WordyInteger {
        private final int i;

        public WordyInteger(int i) {
            this.i = i;
        }

        public @NotNull String toString() {
            switch (i) {
                case 1: return "one";
                case 2: return "two";
                case 3: return "three";
                default: return "many";
            }
        }
    }

    private static void genericReadStrict_helper(
            @NotNull Function<String, WordyInteger> f,
            @NotNull String input,
            @NotNull String output
    ) {
        aeq(genericReadStrict(f).apply(input), output);
    }

    @Test
    public void testGenericReadStrict() {
        Function<String, WordyInteger> f = s -> {
            if (s.equals("one")) return new WordyInteger(1);
            if (s.equals("two")) return new WordyInteger(2);
            if (s.equals("three")) return new WordyInteger(3);
            throw new ArithmeticException();
        };
        genericReadStrict_helper(f, "one", "Optional[one]");
        genericReadStrict_helper(f, "two", "Optional[two]");
        genericReadStrict_helper(f, "three", "Optional[three]");

        genericReadStrict_helper(f, "four", "Optional.empty");
        genericReadStrict_helper(f, "", "Optional.empty");
        genericReadStrict_helper(f, " ", "Optional.empty");
        genericReadStrict_helper(f, "null", "Optional.empty");

        f = s -> {
            if (s.equals("one")) return new WordyInteger(1);
            if (s.equals("two")) return new WordyInteger(2);
            if (s.equals("three")) return new WordyInteger(3);
            return new WordyInteger(10);
        };
        genericReadStrict_helper(f, "one", "Optional[one]");
        genericReadStrict_helper(f, "two", "Optional[two]");
        genericReadStrict_helper(f, "three", "Optional[three]");
        genericReadStrict_helper(f, "many", "Optional[many]");

        genericReadStrict_helper(f, "four", "Optional.empty");
        genericReadStrict_helper(f, "", "Optional.empty");
        genericReadStrict_helper(f, " ", "Optional.empty");
        genericReadStrict_helper(f, "null", "Optional.empty");

        genericReadStrict_helper(s -> null, "four", "Optional.empty");
    }

    private static void readBooleanStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readBooleanStrict(input), output);
    }

    @Test
    public void testReadBooleanStrict() {
        readBooleanStrict_helper("false", "Optional[false]");
        readBooleanStrict_helper("true", "Optional[true]");

        readBooleanStrict_helper(" true", "Optional.empty");
        readBooleanStrict_helper("TRUE", "Optional.empty");
        readBooleanStrict_helper("true ", "Optional.empty");
        readBooleanStrict_helper("", "Optional.empty");
        readBooleanStrict_helper("dsfsdfgd", "Optional.empty");
        readBooleanStrict_helper("T", "Optional.empty");
        readBooleanStrict_helper("F", "Optional.empty");
        readBooleanStrict_helper("1", "Optional.empty");
        readBooleanStrict_helper("0", "Optional.empty");
    }

    private static void readOrderingStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readOrderingStrict(input), output);
    }

    @Test
    public void testReadOrderingStrict() {
        readOrderingStrict_helper("<", "Optional[<]");
        readOrderingStrict_helper("=", "Optional[=]");
        readOrderingStrict_helper(">", "Optional[>]");

        readOrderingStrict_helper(" <", "Optional.empty");
        readOrderingStrict_helper("eq", "Optional.empty");
        readOrderingStrict_helper("gt ", "Optional.empty");
        readOrderingStrict_helper("GT ", "Optional.empty");
        readOrderingStrict_helper("", "Optional.empty");
        readOrderingStrict_helper("dsfs<fgd", "Optional.empty");
    }

    private static void readRoundingModeStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readRoundingModeStrict(input), output);
    }

    @Test
    public void testReadRoundingModeStrict() {
        readRoundingModeStrict_helper("UP", "Optional[UP]");
        readRoundingModeStrict_helper("UNNECESSARY", "Optional[UNNECESSARY]");
        readRoundingModeStrict_helper("HALF_EVEN", "Optional[HALF_EVEN]");

        readRoundingModeStrict_helper(" DOWN", "Optional.empty");
        readRoundingModeStrict_helper("HALF-EVEN", "Optional.empty");
        readRoundingModeStrict_helper("FLOOR ", "Optional.empty");
        readRoundingModeStrict_helper("", "Optional.empty");
        readRoundingModeStrict_helper("dsfsdfgd", "Optional.empty");
    }

    private static void readBigIntegerStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readBigIntegerStrict(input), output);
    }

    @Test
    public void testReadBigIntegerStrict() {
        readBigIntegerStrict_helper("0", "Optional[0]");
        readBigIntegerStrict_helper("5", "Optional[5]");
        readBigIntegerStrict_helper("314159265358", "Optional[314159265358]");
        readBigIntegerStrict_helper("-314159265358", "Optional[-314159265358]");

        readBigIntegerStrict_helper(" 1", "Optional.empty");
        readBigIntegerStrict_helper("00", "Optional.empty");
        readBigIntegerStrict_helper("-0", "Optional.empty");
        readBigIntegerStrict_helper("0xff", "Optional.empty");
        readBigIntegerStrict_helper("2 ", "Optional.empty");
        readBigIntegerStrict_helper("--1", "Optional.empty");
        readBigIntegerStrict_helper("1-2", "Optional.empty");
        readBigIntegerStrict_helper("+4", "Optional.empty");
    }

    private static void readByteStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readByteStrict(input), output);
    }

    @Test
    public void testReadByteStrict() {
        readByteStrict_helper("0", "Optional[0]");
        readByteStrict_helper("5", "Optional[5]");
        readByteStrict_helper("-100", "Optional[-100]");
        readByteStrict_helper("127", "Optional[127]");
        readByteStrict_helper("-128", "Optional[-128]");

        readByteStrict_helper("128", "Optional.empty");
        readByteStrict_helper("-129", "Optional.empty");
        readByteStrict_helper(" 1", "Optional.empty");
        readByteStrict_helper("00", "Optional.empty");
        readByteStrict_helper("-0", "Optional.empty");
        readByteStrict_helper("0xff", "Optional.empty");
        readByteStrict_helper("0xff", "Optional.empty");
        readByteStrict_helper("2 ", "Optional.empty");
        readByteStrict_helper("--1", "Optional.empty");
        readByteStrict_helper("1-2", "Optional.empty");
        readByteStrict_helper("+4", "Optional.empty");
    }

    private static void readShortStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readShortStrict(input), output);
    }

    @Test
    public void testReadShortStrict() {
        readShortStrict_helper("0", "Optional[0]");
        readShortStrict_helper("5", "Optional[5]");
        readShortStrict_helper("-100", "Optional[-100]");
        readShortStrict_helper("32767", "Optional[32767]");
        readShortStrict_helper("-32768", "Optional[-32768]");

        readShortStrict_helper("32768", "Optional.empty");
        readShortStrict_helper("-32769", "Optional.empty");
        readShortStrict_helper(" 1", "Optional.empty");
        readShortStrict_helper("00", "Optional.empty");
        readShortStrict_helper("-0", "Optional.empty");
        readShortStrict_helper("0xff", "Optional.empty");
        readShortStrict_helper("0xff", "Optional.empty");
        readShortStrict_helper("2 ", "Optional.empty");
        readShortStrict_helper("--1", "Optional.empty");
        readShortStrict_helper("1-2", "Optional.empty");
        readShortStrict_helper("+4", "Optional.empty");
    }

    private static void readIntegerStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readIntegerStrict(input), output);
    }

    @Test
    public void testReadIntegerStrict() {
        readIntegerStrict_helper("0", "Optional[0]");
        readIntegerStrict_helper("5", "Optional[5]");
        readIntegerStrict_helper("-100", "Optional[-100]");
        readIntegerStrict_helper("2147483647", "Optional[2147483647]");
        readIntegerStrict_helper("-2147483648", "Optional[-2147483648]");

        readIntegerStrict_helper("2147483648", "Optional.empty");
        readIntegerStrict_helper("-2147483649", "Optional.empty");
        readIntegerStrict_helper(" 1", "Optional.empty");
        readIntegerStrict_helper("00", "Optional.empty");
        readIntegerStrict_helper("-0", "Optional.empty");
        readIntegerStrict_helper("0xff", "Optional.empty");
        readIntegerStrict_helper("0xff", "Optional.empty");
        readIntegerStrict_helper("2 ", "Optional.empty");
        readIntegerStrict_helper("--1", "Optional.empty");
        readIntegerStrict_helper("1-2", "Optional.empty");
        readIntegerStrict_helper("+4", "Optional.empty");
    }

    private static void readLongStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readLongStrict(input), output);
    }

    @Test
    public void testReadLongStrict() {
        readLongStrict_helper("0", "Optional[0]");
        readLongStrict_helper("5", "Optional[5]");
        readLongStrict_helper("-100", "Optional[-100]");
        readLongStrict_helper("9223372036854775807", "Optional[9223372036854775807]");
        readLongStrict_helper("-9223372036854775808", "Optional[-9223372036854775808]");

        readLongStrict_helper("9223372036854775808", "Optional.empty");
        readLongStrict_helper("-9223372036854775809", "Optional.empty");
        readLongStrict_helper(" 1", "Optional.empty");
        readLongStrict_helper("00", "Optional.empty");
        readLongStrict_helper("-0", "Optional.empty");
        readLongStrict_helper("0xff", "Optional.empty");
        readLongStrict_helper("0xff", "Optional.empty");
        readLongStrict_helper("2 ", "Optional.empty");
        readLongStrict_helper("--1", "Optional.empty");
        readLongStrict_helper("1-2", "Optional.empty");
        readLongStrict_helper("+4", "Optional.empty");
    }

    private static void readFloatStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readFloatStrict(input), output);
    }

    @Test
    public void testReadFloatStrict() {
        readFloatStrict_helper("0.0", "Optional[0.0]");
        readFloatStrict_helper("-0.0", "Optional[-0.0]");
        readFloatStrict_helper("5.0", "Optional[5.0]");
        readFloatStrict_helper("-100.0", "Optional[-100.0]");
        readFloatStrict_helper("1.0E10", "Optional[1.0E10]");
        readFloatStrict_helper("1.0E-10", "Optional[1.0E-10]");
        readFloatStrict_helper("1.234", "Optional[1.234]");
        readFloatStrict_helper("1.111111", "Optional[1.111111]");
        readFloatStrict_helper("NaN", "Optional[NaN]");
        readFloatStrict_helper("Infinity", "Optional[Infinity]");
        readFloatStrict_helper("-Infinity", "Optional[-Infinity]");

        readFloatStrict_helper("1.1111111", "Optional.empty");
        readFloatStrict_helper("1.0e10", "Optional.empty");
        readFloatStrict_helper("1.0e-10", "Optional.empty");
        readFloatStrict_helper(".", "Optional.empty");
        readFloatStrict_helper("0.", "Optional.empty");
        readFloatStrict_helper(".0", "Optional.empty");
        readFloatStrict_helper(" 1.0", "Optional.empty");
        readFloatStrict_helper("--1.0", "Optional.empty");
    }

    private static void readDoubleStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readDoubleStrict(input), output);
    }

    @Test
    public void testReadDoubleStrict() {
        readDoubleStrict_helper("0.0", "Optional[0.0]");
        readDoubleStrict_helper("-0.0", "Optional[-0.0]");
        readDoubleStrict_helper("5.0", "Optional[5.0]");
        readDoubleStrict_helper("-100.0", "Optional[-100.0]");
        readDoubleStrict_helper("1.0E10", "Optional[1.0E10]");
        readDoubleStrict_helper("1.0E-10", "Optional[1.0E-10]");
        readDoubleStrict_helper("1.234", "Optional[1.234]");
        readDoubleStrict_helper("1.111111111111111", "Optional[1.111111111111111]");
        readDoubleStrict_helper("NaN", "Optional[NaN]");
        readDoubleStrict_helper("Infinity", "Optional[Infinity]");
        readDoubleStrict_helper("-Infinity", "Optional[-Infinity]");

        readDoubleStrict_helper("1.1111111111111111", "Optional.empty");
        readDoubleStrict_helper("1.0e10", "Optional.empty");
        readDoubleStrict_helper("1.0e-10", "Optional.empty");
        readDoubleStrict_helper(".", "Optional.empty");
        readDoubleStrict_helper("0.", "Optional.empty");
        readDoubleStrict_helper(".0", "Optional.empty");
        readDoubleStrict_helper(" 1.0", "Optional.empty");
        readDoubleStrict_helper("--1.0", "Optional.empty");
    }

    private static void readBigDecimalStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readBigDecimalStrict(input), output);
    }

    @Test
    public void testReadBigDecimalStrict() {
        readBigDecimalStrict_helper("0.0", "Optional[0.0]");
        readBigDecimalStrict_helper("5.0", "Optional[5.0]");
        readBigDecimalStrict_helper("-100.0", "Optional[-100.0]");
        readBigDecimalStrict_helper("1.0E+10", "Optional[1.0E+10]");
        readBigDecimalStrict_helper("1.0E-10", "Optional[1.0E-10]");
        readBigDecimalStrict_helper("1.234", "Optional[1.234]");
        readBigDecimalStrict_helper("1.111111111111111", "Optional[1.111111111111111]");

        readBigDecimalStrict_helper("1.0e10", "Optional.empty");
        readBigDecimalStrict_helper("1.0e-10", "Optional.empty");
        readBigDecimalStrict_helper(".", "Optional.empty");
        readBigDecimalStrict_helper("0.", "Optional.empty");
        readBigDecimalStrict_helper(".0", "Optional.empty");
        readBigDecimalStrict_helper(" 1.0", "Optional.empty");
        readBigDecimalStrict_helper("--1.0", "Optional.empty");
        readBigDecimalStrict_helper("-0.0", "Optional.empty");
        readBigDecimalStrict_helper("NaN", "Optional.empty");
        readBigDecimalStrict_helper("Infinity", "Optional.empty");
    }

    private static void readCharacterStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readCharacterStrict(input), output);
    }

    @Test
    public void testReadCharacterStrict() {
        readCharacterStrict_helper("a", "Optional[a]");
        readCharacterStrict_helper("ø", "Optional[ø]");
        readCharacterStrict_helper("hi", "Optional.empty");
        readCharacterStrict_helper("", "Optional.empty");
    }

    private static void readStringStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readStringStrict(input), output);
    }

    @Test
    public void testReadStringStrict() {
        readStringStrict_helper("Hello", "Optional[Hello]");
        readStringStrict_helper("ø", "Optional[ø]");
        readStringStrict_helper("", "Optional[]");
    }

    private static <T> void readWithNullsStrict_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input,
            @NotNull String output
    ) {
        aeq(readWithNullsStrict(read).apply(input), output);
    }

    private static <T> void readWithNullsStrict_fail_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input
    ) {
        try {
            readWithNullsStrict(read).apply(input);
            fail();
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testReadWithNullsStrict() {
        readWithNullsStrict_helper(Readers::readIntegerStrict, "23", "NullableOptional[23]");
        readWithNullsStrict_helper(Readers::readIntegerStrict, "-500", "NullableOptional[-500]");
        readWithNullsStrict_helper(Readers::readIntegerStrict, "null", "NullableOptional[null]");
        readWithNullsStrict_helper(Readers::readStringStrict, "hello", "NullableOptional[hello]");
        readWithNullsStrict_helper(Readers::readStringStrict, "bye", "NullableOptional[bye]");
        readWithNullsStrict_helper(Readers::readStringStrict, "nullification", "NullableOptional[nullification]");
        readWithNullsStrict_helper(Readers::readStringStrict, "", "NullableOptional[]");
        readWithNullsStrict_helper(Readers::readStringStrict, "null", "NullableOptional[null]");
        readWithNullsStrict_helper(Readers::readIntegerStrict, "annull", "NullableOptional.empty");
        readWithNullsStrict_helper(Readers::readIntegerStrict, "--", "NullableOptional.empty");
        readWithNullsStrict_helper(Readers::readIntegerStrict, "", "NullableOptional.empty");

        readWithNullsStrict_fail_helper(s -> null, "hello");
    }

    private static <T> void readOptionalStrict_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input,
            @NotNull String output
    ) {
        aeq(readOptionalStrict(read).apply(input), output);
    }

    private static <T> void readOptionalStrict_fail_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input
    ) {
        try {
            readOptionalStrict(read).apply(input);
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadOptionalStrict() {
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[23]", "Optional[Optional[23]]");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[0]", "Optional[Optional[0]]");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[-5]", "Optional[Optional[-5]]");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional.empty", "Optional[Optional.empty]");
        readOptionalStrict_helper(Readers::readBooleanStrict, "Optional[false]", "Optional[Optional[false]]");
        readOptionalStrict_helper(Readers::readBooleanStrict, "Optional[true]", "Optional[Optional[true]]");

        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[10000000000000000000]", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[xyz]", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[null]", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional[10", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "Optional", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "xyz", "Optional.empty");
        readOptionalStrict_helper(Readers::readIntegerStrict, "", "Optional.empty");
        readOptionalStrict_helper(Readers::readBooleanStrict, "Optional[12]", "Optional.empty");

        readOptionalStrict_fail_helper(s -> null, "Optional[hello]");
    }

    private static <T> void readNullableOptionalStrict_helper(
            @NotNull Function<String, NullableOptional<T>> read,
            @NotNull String input,
            @NotNull String output
    ) {
        aeq(readNullableOptionalStrict(read).apply(input), output);
    }

    private static <T> void readNullableOptionalStrict_fail_helper(
            @NotNull Function<String, NullableOptional<T>> read,
            @NotNull String input
    ) {
        try {
            readNullableOptionalStrict(read).apply(input);
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadNullableOptionalStrict() {
        Function<String, NullableOptional<Integer>> fi = readWithNullsStrict(Readers::readIntegerStrict);
        Function<String, NullableOptional<Boolean>> fb = readWithNullsStrict(Readers::readBooleanStrict);

        readNullableOptionalStrict_helper(fi, "NullableOptional[23]", "Optional[NullableOptional[23]]");
        readNullableOptionalStrict_helper(fi, "NullableOptional[0]", "Optional[NullableOptional[0]]");
        readNullableOptionalStrict_helper(fi, "NullableOptional[-5]", "Optional[NullableOptional[-5]]");
        readNullableOptionalStrict_helper(fi, "NullableOptional[null]", "Optional[NullableOptional[null]]");
        readNullableOptionalStrict_helper(fi, "NullableOptional.empty", "Optional[NullableOptional.empty]");
        readNullableOptionalStrict_helper(fb, "NullableOptional[false]", "Optional[NullableOptional[false]]");
        readNullableOptionalStrict_helper(fb, "NullableOptional[true]", "Optional[NullableOptional[true]]");
        readNullableOptionalStrict_helper(fb, "NullableOptional[null]", "Optional[NullableOptional[null]]");
        readNullableOptionalStrict_helper(fb, "NullableOptional.empty", "Optional[NullableOptional.empty]");

        readNullableOptionalStrict_helper(fi, "Optional[23]", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "NullableOptional[10000000000000000000]", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "NullableOptional[xyz]", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "NullableOptional[10", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "NullableOptional", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "xyz", "Optional.empty");
        readNullableOptionalStrict_helper(fi, "", "Optional.empty");
        readNullableOptionalStrict_helper(fb, "NullableOptional[12]", "Optional.empty");

        readNullableOptionalStrict_fail_helper(s -> null, "NullableOptional[hello]");
    }

    private static <T> void readListStrict_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s,
            @NotNull String output
    ) {
        aeq(readListStrict(read).apply(s), output);
    }

    private static void readListStrict_helper(
            @NotNull Function<String, Optional<String>> read,
            @NotNull String s,
            int length,
            @NotNull String output
    ) {
        Optional<List<String>> result = readListStrict(read).apply(s);
        aeq(result.get().size(), length);
        aeq(result, output);
    }

    private static <T> void readListStrict_fail_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input
    ) {
        try {
            readListStrict(read).apply(input);
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadListStrict() {
        readListStrict_helper(Readers::readIntegerStrict, "[]", "Optional[[]]");
        readListStrict_helper(Readers::readIntegerStrict, "[1]", "Optional[[1]]");
        readListStrict_helper(Readers::readIntegerStrict, "[1, 2, -3]", "Optional[[1, 2, -3]]");
        readListStrict_helper(Readers::readIntegerStrict, "[1000000000000000]", "Optional.empty");
        readListStrict_helper(Readers::readIntegerStrict, "[null]", "Optional.empty");
        readListStrict_helper(Readers::readIntegerStrict, "[1, 2", "Optional.empty");
        readListStrict_helper(Readers::readIntegerStrict, "1, 2", "Optional.empty");
        readListStrict_helper(Readers::readIntegerStrict, "[a]", "Optional.empty");
        readListStrict_helper(Readers::readIntegerStrict, "[00]", "Optional.empty");

        readListStrict_helper(Readers::readStringStrict, "[hello]", 1, "Optional[[hello]]");
        readListStrict_helper(Readers::readStringStrict, "[hello, bye]", 2, "Optional[[hello, bye]]");
        readListStrict_helper(Readers::readStringStrict, "[a, b, c]", 3, "Optional[[a, b, c]]");

        readListStrict_fail_helper(s -> null, "[hello]");
    }

    private static <T> void readListWithNullsStrict_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s,
            @NotNull String output
    ) {
        aeq(readListWithNullsStrict(read).apply(s), output);
    }

    private static void readListWithNullsStrict_helper(
            @NotNull Function<String, Optional<String>> read,
            @NotNull String s,
            int length,
            @NotNull String output
    ) {
        Optional<List<String>> result = readListWithNullsStrict(read).apply(s);
        aeq(result.get().size(), length);
        aeq(result, output);
    }

    private static <T> void readListWithNullsStrict_fail_helper(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String input
    ) {
        try {
            readListWithNullsStrict(read).apply(input);
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadListWithNullsStrict() {
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[]", "Optional[[]]");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[1]", "Optional[[1]]");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[1, 2, -3]", "Optional[[1, 2, -3]]");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[1000000000000000]", "Optional.empty");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[null]", "Optional[[null]]");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[1, 2", "Optional.empty");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "1, 2", "Optional.empty");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[a]", "Optional.empty");
        readListWithNullsStrict_helper(Readers::readIntegerStrict, "[00]", "Optional.empty");

        readListWithNullsStrict_helper(Readers::readStringStrict, "[hello]", 1, "Optional[[hello]]");
        readListWithNullsStrict_helper(Readers::readStringStrict, "[hello, bye]", 2, "Optional[[hello, bye]]");
        readListWithNullsStrict_helper(Readers::readStringStrict, "[a, b, c]", 3, "Optional[[a, b, c]]");

        readListWithNullsStrict_fail_helper(s -> null, "[hello]");
    }
}
