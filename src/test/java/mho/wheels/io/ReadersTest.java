package mho.wheels.io;

import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static org.junit.Assert.*;
import static mho.wheels.testing.Testing.*;

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

    private void readBooleanStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readOrderingStrict_helper(@NotNull String input, @NotNull String output) {
        aeq(readOrderingStrict(input), output);
    }

    @Test
    public void testReadOrderingStrict() {
        readOrderingStrict_helper("LT", "Optional[LT]");
        readOrderingStrict_helper("EQ", "Optional[EQ]");
        readOrderingStrict_helper("GT", "Optional[GT]");

        readOrderingStrict_helper(" LT", "Optional.empty");
        readOrderingStrict_helper("eq", "Optional.empty");
        readOrderingStrict_helper("gt ", "Optional.empty");
        readOrderingStrict_helper("", "Optional.empty");
        readOrderingStrict_helper("dsfsdfgd", "Optional.empty");
    }

    private void readRoundingModeStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readBigIntegerStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readByteStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readShortStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readIntegerStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readLongStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readFloatStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readDoubleStrict_helper(@NotNull String input, @NotNull String output) {
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

    private void readBigDecimalStrict_helper(@NotNull String input, @NotNull String output) {
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

    @Test
    public void testReadCharacterStrict() {
        aeq(readCharacterStrict("a").get(), "a");
        aeq(readCharacterStrict("ø").get(), "ø");
        assertFalse(readCharacterStrict("hi").isPresent());
        assertFalse(readCharacterStrict("").isPresent());
    }

    @Test
    public void testReadStringStrict() {
        aeq(readStringStrict("Hello").get(), "Hello");
        aeq(readStringStrict("ø").get(), "ø");
        aeq(readStringStrict("").get(), "");
    }

    @Test
    public void testReadWithNullsStrict() {
        aeq(readWithNullsStrict(Readers::readIntegerStrict).apply("23").get(), "23");
        aeq(readWithNullsStrict(Readers::readIntegerStrict).apply("-500").get(), "-500");
        assertNull(readWithNullsStrict(Readers::readIntegerStrict).apply("null").get());
        aeq(readWithNullsStrict(Readers::readStringStrict).apply("hello").get(), "hello");
        aeq(readWithNullsStrict(Readers::readStringStrict).apply("bye").get(), "bye");
        aeq(readWithNullsStrict(Readers::readStringStrict).apply("nullification").get(), "nullification");
        aeq(readWithNullsStrict(Readers::readStringStrict).apply("").get(), "");
        assertNull(readWithNullsStrict(Readers::readStringStrict).apply("null").get());
        assertFalse(readWithNullsStrict(Readers::readIntegerStrict).apply("annull").isPresent());
        assertFalse(readWithNullsStrict(Readers::readIntegerStrict).apply("--").isPresent());
        assertFalse(readWithNullsStrict(Readers::readIntegerStrict).apply("").isPresent());
        try {
            readWithNullsStrict(s -> null).apply("hello");
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testReadOptionalStrict() {
        aeq(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[23]").get(), "Optional[23]");
        aeq(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[0]").get(), "Optional[0]");
        aeq(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[-5]").get(), "Optional[-5]");
        aeq(readOptionalStrict(Readers::readIntegerStrict).apply("Optional.empty").get(), "Optional.empty");
        aeq(readOptionalStrict(Readers::readBooleanStrict).apply("Optional[false]").get(), "Optional[false]");
        aeq(readOptionalStrict(Readers::readBooleanStrict).apply("Optional[true]").get(), "Optional[true]");
        aeq(readOptionalStrict(Readers::readBooleanStrict).apply("Optional.empty").get(), "Optional.empty");
        assertFalse(
                readOptionalStrict(Readers::readIntegerStrict).apply("Optional[10000000000000000000]").isPresent()
        );
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[xyz]").isPresent());
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[null]").isPresent());
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("Optional[10").isPresent());
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("Optional").isPresent());
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("xyz").isPresent());
        assertFalse(readOptionalStrict(Readers::readIntegerStrict).apply("").isPresent());
        assertFalse(readOptionalStrict(Readers::readBooleanStrict).apply("Optional[12]").isPresent());
        try {
            readOptionalStrict(s -> null).apply("Optional[hello]");
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadNullableOptionalStrict() {
        Function<String, Optional<NullableOptional<Integer>>> fi = readNullableOptionalStrict(
                readWithNullsStrict(Readers::readIntegerStrict)
        );
        Function<String, Optional<NullableOptional<Boolean>>> fb = readNullableOptionalStrict(
                readWithNullsStrict(Readers::readBooleanStrict)
        );
        aeq(fi.apply("NullableOptional[23]").get(), "NullableOptional[23]");
        aeq(fi.apply("NullableOptional[0]").get(), "NullableOptional[0]");
        aeq(fi.apply("NullableOptional[-5]").get(), "NullableOptional[-5]");
        aeq(fi.apply("NullableOptional[null]").get(), "NullableOptional[null]");
        aeq(fi.apply("NullableOptional.empty").get(), "NullableOptional.empty");
        aeq(fb.apply("NullableOptional[false]").get(), "NullableOptional[false]");
        aeq(fb.apply("NullableOptional[true]").get(), "NullableOptional[true]");
        aeq(fb.apply("NullableOptional[null]").get(), "NullableOptional[null]");
        aeq(fb.apply("NullableOptional.empty").get(), "NullableOptional.empty");
        assertFalse(fi.apply("Optional[23]").isPresent());
        assertFalse(fi.apply("NullableOptional[10000000000000000000]").isPresent());
        assertFalse(fi.apply("NullableOptional[xyz]").isPresent());
        assertFalse(fi.apply("NullableOptional[10").isPresent());
        assertFalse(fi.apply("NullableOptional").isPresent());
        assertFalse(fi.apply("xyz").isPresent());
        assertFalse(fi.apply("").isPresent());
        assertFalse(fb.apply("NullableOptional[12]").isPresent());
        try {
            readNullableOptionalStrict(s -> null).apply("NullableOptional[hello]");
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void testReadListStrict() {
        aeq(readListStrict(Readers::readIntegerStrict).apply("[]").get(), "[]");
        aeq(readListStrict(Readers::readIntegerStrict).apply("[1]").get(), "[1]");
        aeq(readListStrict(Readers::readIntegerStrict).apply("[1, 2, -3]").get(), "[1, 2, -3]");
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("[1000000000000000]").isPresent());
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("[null]").isPresent());
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("[1, 2").isPresent());
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("1, 2").isPresent());
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("[a]").isPresent());
        assertFalse(readListStrict(Readers::readIntegerStrict).apply("[00]").isPresent());
        Optional<List<String>> ss;

        ss = readListStrict(Readers::readStringStrict).apply("[hello]");
        aeq(ss.get(), "[hello]");
        aeq(ss.get().size(), 1);

        ss = readListStrict(Readers::readStringStrict).apply("[hello, bye]");
        aeq(ss.get(), "[hello, bye]");
        aeq(ss.get().size(), 2);

        ss = readListStrict(Readers::readStringStrict).apply("[a, b, c]");
        aeq(ss.get(), "[a, b, c]");
        aeq(ss.get().size(), 3);
    }

    @Test
    public void testReadListWithNulls() {
        //todo
    }
}
