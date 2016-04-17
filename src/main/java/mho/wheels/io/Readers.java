package mho.wheels.io;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;

/**
 * Methods for reading values from a {@code String}. Unlike Java's standard parsing methods, these never throw an
 * exception. Instead, they return empty {@code Optional}s.
 */
public class Readers {
    /**
     * The length of the longest {@code String} representation of any positive {@code Byte}.
     */
    public static final int MAX_POSITIVE_BYTE_LENGTH = Byte.toString(Byte.MAX_VALUE).length();

    /**
     * The length of the longest {@code String} representation of any positive {@code Short}.
     */
    public static final int MAX_POSITIVE_SHORT_LENGTH = Short.toString(Short.MAX_VALUE).length();

    /**
     * The length of the longest {@code String} representation of any positive {@code Integer}.
     */
    public static final int MAX_POSITIVE_INTEGER_LENGTH = Integer.toString(Integer.MAX_VALUE).length();

    /**
     * The length of the longest {@code String} representation of any positive {@code Long}.
     */
    public static final int MAX_POSITIVE_LONG_LENGTH = Long.toString(Long.MAX_VALUE).length();

    /**
     * Disallow instantiation
     */
    private Readers() {}

    /**
     * Turns a function {@code read} from {@code String} to {@code T} into a function from {@code String} to
     * {@code Optional<T>} such that the new function returns an empty {@code Optional} whenever {@code read} would
     * throw an exception, return null, or whenever the {@code T} value produced by {@code read} has a different
     * {@code String} representation than the original {@code String}. Consider calling
     * {@code genericRead(Integer::parseInt).apply("0xff")}. This returns an empty {@code Optional}, since although
     * {@code "0xff"} can be read as 127, it isn't the string returned by {@code Integer.toString(127)}.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>The result must be called on {@code String}s {@code s} such that {@code read.apply(s)} terminates (possibly
     *  by throwing an exception).</li>
     * </ul>
     *
     * @param read the original read function
     * @param <T> the type of value read by {@code read}
     * @return a function which behaves like {@code read} but doesn't throw exceptions and only accepts {@code String}s
     * that can be returned by {@code T}'s {@code toString} method
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T> Function<String, Optional<T>> genericReadStrict(@NotNull Function<String, T> read) {
        return s -> {
            T x;
            try {
                x = read.apply(s);
            } catch (Exception e) {
                return Optional.empty();
            }
            if (x == null) return Optional.empty();
            return x.toString().equals(s) ? Optional.of(x) : Optional.<T>empty();
        };
    }

    /**
     * Reads a {@code boolean} from a {@code String}. Only accepts the {@code String}s "true" and "false".
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code boolean} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent
     * a {@code boolean}
     */
    public static @NotNull Optional<Boolean> readBooleanStrict(@NotNull String s) {
        return genericReadStrict(Boolean::parseBoolean).apply(s);
    }

    /**
     * Reads a {@code boolean} from a {@code String}. Accepts the {@code String}s "true", "false", "t", "f", "1", and
     * "0". Is case insensitive and allows leading and trailing spaces.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code boolean} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent
     * a {@code boolean}
     */
    public static @NotNull Optional<Boolean> readBoolean(@NotNull String s) {
        switch (s.trim().toLowerCase()) {
            case "true":
            case "t":
            case "1":
                return Optional.of(true);
            case "false":
            case "f":
            case "0":
                return Optional.of(false);
            default:
                return Optional.empty();
        }
    }

    /**
     * Reads an {@link Ordering} from a {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Ordering} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent
     * an {@code Ordering}
     */
    public static @NotNull Optional<Ordering> readOrderingStrict(@NotNull String s) {
        switch (s) {
            case "LT":
                return Optional.of(Ordering.LT);
            case "EQ":
                return Optional.of(Ordering.EQ);
            case "GT":
                return Optional.of(Ordering.GT);
            default:
                return Optional.empty();
        }
    }

    /**
     * Reads a {@link java.math.RoundingMode} from a {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code RoundingMode} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent a {@code RoundingMode}
     */
    public static @NotNull Optional<RoundingMode> readRoundingModeStrict(@NotNull String s) {
        switch (s) {
            case "UP":
                return Optional.of(RoundingMode.UP);
            case "DOWN":
                return Optional.of(RoundingMode.DOWN);
            case "CEILING":
                return Optional.of(RoundingMode.CEILING);
            case "FLOOR":
                return Optional.of(RoundingMode.FLOOR);
            case "HALF_UP":
                return Optional.of(RoundingMode.HALF_UP);
            case "HALF_DOWN":
                return Optional.of(RoundingMode.HALF_DOWN);
            case "HALF_EVEN":
                return Optional.of(RoundingMode.HALF_EVEN);
            case "UNNECESSARY":
                return Optional.of(RoundingMode.UNNECESSARY);
            default:
                return Optional.empty();
        }
    }

    /**
     * Reads a {@link java.math.BigInteger} from a {@code String}. Leading zeros, octal, and hexadecimal are not
     * allowed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code BigInteger} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent a {@code BigInteger}
     */
    public static @NotNull Optional<BigInteger> readBigIntegerStrict(@NotNull String s) {
        return genericReadStrict(BigInteger::new).apply(s);
    }

    /**
     * Reads a {@link java.lang.Byte} from a {@code String}. Leading zeros, octal, and hexadecimal are not allowed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Byte} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code Byte}
     */
    public static @NotNull Optional<Byte> readByteStrict(@NotNull String s) {
        return genericReadStrict(Byte::parseByte).apply(s);
    }

    /**
     * Reads a {@link java.lang.Short} from a {@code String}. Leading zeros, octal, and hexadecimal are not allowed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Short} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code Short}
     */
    public static @NotNull Optional<Short> readShortStrict(@NotNull String s) {
        return genericReadStrict(Short::parseShort).apply(s);
    }

    /**
     * Reads an {@link java.lang.Integer} from a {@code String}. Leading zeros, octal, and hexadecimal are not allowed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Integer} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent
     * an {@code Integer}
     */
    public static @NotNull Optional<Integer> readIntegerStrict(@NotNull String s) {
        return genericReadStrict(Integer::parseInt).apply(s);
    }

    /**
     * Reads a {@link java.lang.Long} from a {@code String}. Leading zeros, octal, and hexadecimal are not allowed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Long} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code Long}
     */
    public static @NotNull Optional<Long> readLongStrict(@NotNull String s) {
        return genericReadStrict(Long::parseLong).apply(s);
    }

    /**
     * Reads a {@link java.lang.Float} from a {@code String}. Only {@code String}s which could have been emitted by
     * {@link java.lang.Float#toString} are recognized.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Float} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code Float}
     */
    public static @NotNull Optional<Float> readFloatStrict(@NotNull String s) {
        return genericReadStrict(Float::parseFloat).apply(s);
    }

    /**
     * Reads a {@link java.lang.Double} from a {@code String}. Only {@code String}s which could have been emitted by
     * {@link java.lang.Double#toString} are recognized.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Double} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code Double}
     */
    public static @NotNull Optional<Double> readDoubleStrict(@NotNull String s) {
        return genericReadStrict(Double::parseDouble).apply(s);
    }

    /**
     * Reads a {@link java.math.BigDecimal} from a {@code String}. Only {@code String}s which could have been emitted
     * by {@link java.math.BigDecimal#toString} are recognized.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code BigDecimal} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent a {@code BigDecimal}
     */
    public static @NotNull Optional<BigDecimal> readBigDecimalStrict(@NotNull String s) {
        return genericReadStrict(BigDecimal::new).apply(s);
    }

    /**
     * Reads a {@link java.lang.Character} from a {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code Character} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent a {@code Character}
     */
    public static @NotNull Optional<Character> readCharacter(@NotNull String s) {
        return s.length() == 1 ? Optional.of(s.charAt(0)) : Optional.<Character>empty();
    }

    /**
     * Reads a {@link java.lang.String} from a {@code String}.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-empty.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the {@code String} represented by {@code s}
     */
    public static @NotNull Optional<String> readString(@NotNull String s) {
        return Optional.of(s);
    }

    /**
     * Transforms a read function into a read function that can also read nulls if necessary.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>The result must be called on {@code String}s that {@code read} terminates and doesn't return null on.</li>
     * </ul>
     *
     * @param read a function which takes a {@code String} and returns an {@code Optional{@literal<T>}}.
     * @param <T> the type of the value to be read
     * @return a null-handling version of {@code read}
     */
    public static @NotNull <T> Function<String, NullableOptional<T>> readWithNullsStrict(
            @NotNull Function<String, Optional<T>> read
    ) {
        return s -> {
            if (s.equals("null")) {
                return NullableOptional.of(null);
            } else {
                return NullableOptional.fromOptional(read.apply(s));
            }
        };
    }

    /**
     * Transform a function which reads a {@code String} into a value to a function which reads a {@code String} into
     * an {@code Optional} value. That function only returns {@code String}s which could have been returned by
     * {@link Optional#toString}, and returns an empty {@code Optional<Optional<T>>} if the {@code String} does not
     * represent an {@code Optional<T>}.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is must only be called on {@code String}s {@code s} such that if {@code s} is of the form
     *  {@code "Optional[" + t + "]"}, {@code read} terminates and does not return a null on {@code t}.</li>
     * </ul>
     *
     * @param read a function which reads {@code s} into a value of type {@code T}
     * @param <T> the type of the {@code Optional}'s value
     * @return a function which reads an {@code Optional} value
     */
    public static @NotNull <T> Function<String, Optional<Optional<T>>> readOptionalStrict(
            @NotNull Function<String, Optional<T>> read
    ) {
        return s -> {
            if (!s.startsWith("Optional")) return Optional.empty();
            s = s.substring("Optional".length());
            if (s.equals(".empty")) return Optional.of(Optional.<T>empty());
            if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
            s = tail(init(s));
            return read.apply(s).map(Optional::of);
        };
    }

    /**
     * Transform a function which reads a {@code String} into a possibly-null value to a function which reads a
     * {@code String} into a {@code NullableOptional} value. That function only returns {@code String}s which could
     * have been returned by {@link NullableOptional#toString}, and returns an empty
     * {@code Optional<NullableOptional<T>>} if the {@code String} does not represent a {@code NullableOptional<T>}.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is must only be called on {@code String}s {@code s} such that if {@code s} is of the form
     *  {@code "NullableOptional[" + t + "]"}, {@code read} terminates and does not return a null on {@code t}.</li>
     * </ul>
     *
     * @param read a function which reads {@code s} into a value of type {@code T}
     * @param <T> the type of the {@code NullableOptional}'s value
     * @return a function which reads a {@code NullableOptional} value
     */
    public static @NotNull <T> Function<String, Optional<NullableOptional<T>>> readNullableOptionalStrict(
            @NotNull Function<String, NullableOptional<T>> read
    ) {
        return s -> {
            if (!s.startsWith("NullableOptional")) return Optional.empty();
            s = s.substring("NullableOptional".length());
            if (s.equals(".empty")) return Optional.of(NullableOptional.<T>empty());
            if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
            s = tail(init(s));
            NullableOptional<T> ot = read.apply(s);
            return ot.isPresent() ? Optional.of(ot) : Optional.<NullableOptional<T>>empty();
        };
    }

    private static @NotNull <T> Function<String, Optional<List<T>>> genericReadListStrict(
            @NotNull BiFunction<String, List<T>, Boolean> genericRead
    ) {
        return s -> {
            if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
            s = tail(init(s));
            if (s.isEmpty()) return Optional.of(new ArrayList<>());
            String[] tokens = s.split(", ");
            List<T> result = new ArrayList<>();
            int i;
            for (i = 0; i < tokens.length; i++) {
                StringBuilder sb = new StringBuilder();
                String prefix = "";
                for (; i < tokens.length; i++) {
                    sb.append(prefix);
                    sb.append(tokens[i]);
                    prefix = ", ";
                    if (genericRead.apply(sb.toString(), result)) break;
                    if (i == tokens.length - 1) {
                        return Optional.empty();
                    }
                }
            }
            return Optional.of(result);
        };
    }

    /**
     * Returns a function which reads a {@link java.util.List} from a {@code String}. Only {@code String}s which could
     * have been emitted by {@code java.util.List#toString} are recognized. In some cases there may be ambiguity; for
     * example, when reading {@code "[a, b, c]"} as a list of {@code String}s, both {@code ["a", "b", "c"]} and
     * {@code ["a, b, c"]} are valid interpretations. This method stops reading each list element as soon as possible,
     * so the first option would be returned.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>The result must be applied to {@code String}s {@code s} such that {@code read}, when applied to any
     *  substring of {@code s}, must terminate and not return null. (This precondition is not checked for every
     *  substring.)</li>
     * </ul>
     *
     * @param read a function which reads a {@code String} into a value of type {@code T}
     * @param <T> the type of the {@code List}'s values
     * @return a function which reads a {@code List}
     */
    public static @NotNull <T> Function<String, Optional<List<T>>> readListStrict(
            @NotNull Function<String, Optional<T>> read
    ) {
        return genericReadListStrict(
                (s, result) -> {
                    Optional<T> candidate = read.apply(s);
                    if (candidate.isPresent()) {
                        result.add(candidate.get());
                        return true;
                    }
                    return false;
                }
        );
    }

    public static @NotNull <T> Function<String, Optional<List<T>>> readListWithNullsStrict(
            @NotNull Function<String, Optional<T>> read
    ) {
        return genericReadListStrict(
                (s, result) -> {
                    NullableOptional<T> candidate = readWithNullsStrict(read).apply(s);
                    if (candidate.isPresent()) {
                        result.add(candidate.get());
                        return true;
                    }
                    return false;
                }
        );
    }
}
