package mho.wheels.misc;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.NullableOptional;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;

/**
 * Methods for reading values from a {@code String}. Unlike Java's standard parsing methods, these never throw an
 * exception. Instead, they return empty {@code Optional}s.
 */
@SuppressWarnings("ConstantConditions")
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
     * Turns a function {@code read} from {@code String} to {@code T} into a function from {@code String} to
     * {@code Optional<T>} such that the new function returns an empty {@code Optional} whenever {@code read} would
     * throw an exception, or whenever the {@code T} value produced by {@code read} has a different {@code String}
     * representation than the original {@code String}. Consider calling
     * {@code genericRead(Integer::parseInt).apply("0xff")}. This returns an empty {@code Optional}, since although
     * {@code "0xff"} can be read as 127, it isn't the string returned by {@code Integer.toString(127)}.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>The result must be called on {@code String}s {@code s} such that {@code read.apply(s)} terminates (possibly
     *  by throwing an exception) and does not return null.</li>
     * </ul>
     *
     * @param read the original read function
     * @param <T> the type of value read by {@code read}
     * @return a function which behaves like {@code read} but doesn't throw exceptions
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T> Function<String, Optional<T>> genericRead(@NotNull Function<String, T> read) {
        return s -> {
            T x;
            try {
                x = read.apply(s);
            } catch (Exception e) {
                return Optional.empty();
            }
            if (x == null)
                throw new IllegalArgumentException("read function cannot return null on " + s);
            return x.toString().equals(s) ? Optional.of(x) : Optional.<T>empty();
        };
    }

    /**
     * Given a small {@code Iterable} of values, returns a function which takes a {@code String} and returns the first
     * occurrence of one of those values in the {@code String} as determined by the values' {@code toString} method. If
     * two different values occur at the same index (meaning that one is a prefix of the other), the longer value is
     * preferred. The value is returned along with the index at which it is found. If no value is found, an empty
     * {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code xs} cannot contain any nulls.</li>
     *  <li>{@code T}'s {@code toString} method must terminate on each of {@code xs} without returning a null.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of values
     * @param <T> the type of the values in {@code xs}
     * @return a function which takes a {@code String} and returns the first occurrence of a value
     */
    public static @NotNull <T> Function<String, Optional<Pair<T, Integer>>> genericFindIn(@NotNull Iterable<T> xs) {
        return s -> {
            Iterable<Triple<T, String, Integer>> candidates = filter(
                    u -> u.c != -1,
                    map(
                            x -> {
                                String t = x.toString();
                                return new Triple<>(x, t, s.indexOf(t));
                            },
                            xs
                    )
            );
            if (isEmpty(candidates)) return Optional.empty();
            Comparator<Triple<T, String, Integer>> comparator = (x, y) -> {
                if (x.c < y.c) return -1;
                if (x.c > y.c) return 1;
                if (x.b.length() > y.b.length()) return -1;
                if (x.b.length() < y.b.length()) return 1;
                return 0;
            };
            Triple<T, String, Integer> bestResult = minimum(comparator, candidates);
            return Optional.of(new Pair<>(bestResult.a, bestResult.c));
        };
    }

    /**
     * Given a function {@code read} to convert a {@code String} to a value, and a {@code String} {@code usedChars}
     * containing all characters needed to represent any value of that type as a {@code String}, returns a function
     * which takes a {@code String} and finds the first occurrence of a value. If two different values occur at the
     * same index (meaning that one is a prefix of the other), the longer value is preferred. The value is returned
     * along with the index at which it is found. If no value is found, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code read} must be non-null.</li>
     *  <li>{@code usedChars} must contain all characters that the result of {@code T}'s {@code toString} method could
     *  possibly contain. (This precondition is not checked.)</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result must be called on {@code String}s {@code s} such that {@code read} always terminates and never
     *  returns a null on any substring of {@code s}. (This precondition is not checked for every substring.)</li>
     * </ul>
     *
     * @param read a function which converts a {@code String} to a value.
     * @param usedChars all the characters needed to represent a value of type {@code T} as a {@code String}.
     * @param <T> the type of the values in {@code xs}
     * @return a function which takes a {@code String} and returns the first occurrence of a value
     */
    public static @NotNull <T> Function<String, Optional<Pair<T, Integer>>> genericFindIn(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String usedChars
    ) {
        return s -> {
            if (isEmpty(s)) return Optional.empty();
            Iterable<String> grouped = group(p -> elem(p.a, usedChars) == elem(p.b, usedChars), s);
            Iterable<Integer> indices = scanl(p -> p.a + p.b, 0, map(String::length, grouped));
            Iterable<Boolean> mask;
            if (elem(head(head(grouped)), usedChars)) {
                mask = cycle(Arrays.asList(true, false));
            } else {
                mask = cycle(Arrays.asList(false, true));
            }
            for (Pair<String, Integer> p : select(mask, zip(grouped, indices))) {
                int offset = p.b;
                String substring = p.a;
                for (int i = 0; i < substring.length(); i++) {
                    for (int j = substring.length(); j > i; j--) {
                        Optional<T> ox = read.apply(substring.substring(i, j));
                        if (ox.isPresent()) {
                            return Optional.of(new Pair<>(ox.get(), offset + i));
                        }
                    }
                }
            }
            return Optional.empty();
        };
    }

    /**
     * Reads a {@code boolean} from a {@code String}.
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
        return genericRead(Boolean::parseBoolean).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code boolean} in a {@code String}. Returns the {@code boolean} and the index
     * at which it was found. Returns an empty {@code Optional} if no {@code boolean} is found.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code boolean} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Boolean, Integer>> findBooleanIn(@NotNull String s) {
        return genericFindIn(Arrays.asList(false, true)).apply(s);
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
    public static @NotNull Optional<Ordering> readOrdering(@NotNull String s) {
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
     * Finds the first occurrence of an {@code Ordering} in a {@code String}. Returns the {@code Ordering} and the
     * index at which it was found. Returns an empty {@code Optional} if no {@code Ordering} is found.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Ordering} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Ordering, Integer>> findOrderingIn(@NotNull String s) {
        return genericFindIn(Arrays.asList(Ordering.values())).apply(s);
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
    public static @NotNull Optional<RoundingMode> readRoundingMode(@NotNull String s) {
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
     * Finds the first occurrence of a {@code RoundingMode} in a {@code String}. Returns the {@code RoundingMode} and
     * the index at which it was found. Returns an empty {@code Optional} if no {@code RoundingMode} is found.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code RoundingMode} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<RoundingMode, Integer>> findRoundingModeIn(@NotNull String s) {
        return genericFindIn(Arrays.asList(RoundingMode.values())).apply(s);
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
    public static @NotNull Optional<BigInteger> readBigInteger(@NotNull String s) {
        return genericRead(BigInteger::new).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code BigInteger} in a {@code String}. Returns the {@code BigInteger} and the
     * index at which it was found. Returns an empty {@code Optional} if no {@code BigInteger} is found. Leading zeros,
     * octal, and hexadecimal are not allowed. The longest possible {@code BigInteger} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code BigInteger} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<BigInteger, Integer>> findBigIntegerIn(@NotNull String s) {
        int zeroIndex = s.indexOf('0');
        int nonzeroDigitIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '1' && c <= '9') {
                nonzeroDigitIndex = i;
                break;
            }
        }
        if (zeroIndex == -1 && nonzeroDigitIndex == -1) {
            return Optional.empty();
        } else if (zeroIndex != -1 && (nonzeroDigitIndex == -1 || zeroIndex < nonzeroDigitIndex)) {
            return Optional.of(new Pair<>(BigInteger.ZERO, zeroIndex));
        } else {
            int endIndex = s.length() - 1;
            for (int i = nonzeroDigitIndex + 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c < '0' || c > '9') {
                    endIndex = i - 1;
                    break;
                }
            }
            if (nonzeroDigitIndex != 0 && s.charAt(nonzeroDigitIndex - 1) == '-') {
                nonzeroDigitIndex--;
            }
            BigInteger i = new BigInteger(s.substring(nonzeroDigitIndex, endIndex + 1));
            return Optional.of(new Pair<>(i, nonzeroDigitIndex));
        }
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
    public static @NotNull Optional<Byte> readByte(@NotNull String s) {
        return genericRead(Byte::parseByte).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code Byte} in a {@code String}. Returns the {@code Byte} and the index at
     * which it was found. Returns an empty {@code Optional} if no {@code Byte} is found. Leading zeros, octal, and
     * hexadecimal are not allowed. The longest possible {@code Byte} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Byte} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Byte, Integer>> findByteIn(@NotNull String s) {
        Optional<Pair<BigInteger, Integer>> op = findBigIntegerIn(s);
        if (!op.isPresent()) return Optional.empty();
        Pair<BigInteger, Integer> p = op.get();
        String bis = p.a.toString();
        boolean isNegative = head(bis) == '-';
        int trimSize = MAX_POSITIVE_BYTE_LENGTH;
        if (isNegative) trimSize++;
        String bs = take(trimSize, bis);
        Optional<Byte> ob = readByte(bs);
        if (!ob.isPresent()) {
            bs = take(trimSize - 1, bis);
            ob = readByte(bs);
        }
        return Optional.of(new Pair<>(ob.get(), p.b));
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
    public static @NotNull Optional<Short> readShort(@NotNull String s) {
        return genericRead(Short::parseShort).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code Short} in a {@code String}. Returns the {@code Short} and the index at
     * which it was found. Returns an empty {@code Optional} if no {@code Short} is found. Leading zeros, octal, and
     * hexadecimal are not allowed. The longest possible {@code Short} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Short} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Short, Integer>> findShortIn(@NotNull String s) {
        Optional<Pair<BigInteger, Integer>> op = findBigIntegerIn(s);
        if (!op.isPresent()) return Optional.empty();
        Pair<BigInteger, Integer> p = op.get();
        String bis = p.a.toString();
        boolean isNegative = head(bis) == '-';
        int trimSize = MAX_POSITIVE_SHORT_LENGTH;
        if (isNegative) trimSize++;
        String ss = take(trimSize, bis);
        Optional<Short> os = readShort(ss);
        if (!os.isPresent()) {
            ss = take(trimSize - 1, bis);
            os = readShort(ss);
        }
        return Optional.of(new Pair<>(os.get(), p.b));
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
    public static @NotNull Optional<Integer> readInteger(@NotNull String s) {
        return genericRead(Integer::parseInt).apply(s);
    }

    /**
     * Finds the first occurrence of an {@code Integer} in a {@code String}. Returns the {@code Integer} and the index
     * at which it was found. Returns an empty {@code Optional} if no {@code Integer} is found. Leading zeros, octal,
     * and hexadecimal are not allowed. The longest possible {@code Integer} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Integer} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Integer, Integer>> findIntegerIn(@NotNull String s) {
        Optional<Pair<BigInteger, Integer>> op = findBigIntegerIn(s);
        if (!op.isPresent()) return Optional.empty();
        Pair<BigInteger, Integer> p = op.get();
        String bis = p.a.toString();
        boolean isNegative = head(bis) == '-';
        int trimSize = MAX_POSITIVE_INTEGER_LENGTH;
        if (isNegative) trimSize++;
        String is = take(trimSize, bis);
        Optional<Integer> oi = readInteger(is);
        if (!oi.isPresent()) {
            is = take(trimSize - 1, bis);
            oi = readInteger(is);
        }
        return Optional.of(new Pair<>(oi.get(), p.b));
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
    public static @NotNull Optional<Long> readLong(@NotNull String s) {
        return genericRead(Long::parseLong).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code Long} in a {@code String}. Returns the {@code Long} and the index at
     * which it was found. Returns an empty {@code Optional} if no {@code Long} is found. Leading zeros, octal, and
     * hexadecimal are not allowed. The longest possible {@code Long} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Long} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Long, Integer>> findLongIn(@NotNull String s) {
        Optional<Pair<BigInteger, Integer>> op = findBigIntegerIn(s);
        if (!op.isPresent()) return Optional.empty();
        Pair<BigInteger, Integer> p = op.get();
        String bis = p.a.toString();
        boolean isNegative = head(bis) == '-';
        int trimSize = MAX_POSITIVE_LONG_LENGTH;
        if (isNegative) trimSize++;
        String ls = take(trimSize, bis);
        Optional<Long> ol = readLong(ls);
        if (!ol.isPresent()) {
            ls = take(trimSize - 1, bis);
            ol = readLong(ls);
        }
        return Optional.of(new Pair<>(ol.get(), p.b));
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
    public static @NotNull Optional<Float> readFloat(@NotNull String s) {
        return genericRead(Float::parseFloat).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code Float} in a {@code String}. Returns the {@code Float} and the index at
     * which it was found. Returns an empty {@code Optional} if no {@code Float} is found. Only {@code String}s which
     * could have been emitted by {@link java.lang.Float#toString} are recognized. The longest possible {@code Float}
     * is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Float} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Float, Integer>> findFloatIn(@NotNull String s) {
        return genericFindIn(Readers::readFloat, "-.0123456789EINafinty").apply(s);
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
    public static @NotNull Optional<Double> readDouble(@NotNull String s) {
        return genericRead(Double::parseDouble).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code Double} in a {@code String}. Returns the {@code Double} and the index
     * at which it was found. Returns an empty {@code Optional} if no {@code Double} is found. Only {@code String}s
     * which could have been emitted by {@link java.lang.Double#toString} are recognized. The longest possible
     * {@code Double} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Double} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Double, Integer>> findDoubleIn(@NotNull String s) {
        return genericFindIn(Readers::readDouble, "-.0123456789EINafinty").apply(s);
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
    public static @NotNull Optional<BigDecimal> readBigDecimal(@NotNull String s) {
        return genericRead(BigDecimal::new).apply(s);
    }

    /**
     * Finds the first occurrence of a {@code BigDecimal} in a {@code String}. Returns the {@code BigDecimal} and the
     * index at which it was found. Returns an empty {@code Optional} if no {@code BigDecimal} is found. Only
     * {@code String}s which could have been emitted by {@link java.math.BigDecimal#toString} are recognized. The
     * longest possible {@code BigDecimal} is parsed.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code BigDecimal} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<BigDecimal, Integer>> findBigDecimalIn(@NotNull String s) {
        return genericFindIn(Readers::readBigDecimal, "+-.0123456789E").apply(s);
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
     * Finds the first occurrence of a {@link java.lang.Character} in a {@code String}. Returns the {@code Character}
     * and the index at which it was found. Returns an empty {@code Optional} if no {@code Character} is found.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is 0.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return the first {@code Character} found in {@code s}, and the index at which it was found
     */
    public static @NotNull Optional<Pair<Character, Integer>> findCharacterIn(@NotNull String s) {
        return s.isEmpty() ? Optional.<Pair<Character,Integer>>empty() : Optional.of(new Pair<>(s.charAt(0), 0));
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
     * Finds the first occurrence of a {@link java.lang.String} in a {@code String} (which will be the {@code String}
     * itself). Returns the {@code String} and the index at which it was found (which will be 0).
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is a {@code Pair} whose first element is non-null and whose second element is 0.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @return ({@code s}, 0)
     */
    public static @NotNull Optional<Pair<String, Integer>> findStringIn(@NotNull String s) {
        return Optional.of(new Pair<>(s, 0));
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
    public static @NotNull <T> Function<String, NullableOptional<T>> readWithNulls(
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
     * Transforms a find-in function into a find-in function that can also read nulls if necessary.
     *
     * <ul>
     *  <li>{@code findIn} must be non-null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result must be called on {@code String}s {@code s} such that {@code findIn.apply(s)} returns a pair
     *  with neither element null and the second element non-negative.</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param <T> the type of value to be read
     * @return a null-handling version of {@code findIn}
     */
    public static @NotNull <T> Function<String, Optional<Pair<T, Integer>>> findInWithNulls(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn
    ) {
        return s -> {
            Optional<Pair<T, Integer>> nonNullResult = findIn.apply(s);
            if (nonNullResult.isPresent()) {
                Pair<T, Integer> result = nonNullResult.get();
                if (result.a == null || result.b == null)
                    throw new NullPointerException();
                if (result.b < 0)
                    throw new IllegalArgumentException("findIn should not return indices less than 0");
            }
            int nullIndex = s.indexOf("null");
            if (nullIndex == -1) return nonNullResult;
            if (!nonNullResult.isPresent()) return Optional.of(new Pair<>(null, nullIndex));
            Pair<T, Integer> unwrapped = nonNullResult.get();
            if (nullIndex > unwrapped.b || (nullIndex == unwrapped.b && unwrapped.a.toString().length() >= 4)) {
                return nonNullResult;
            } else {
                return Optional.of(new Pair<>(null, nullIndex));
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
    public static @NotNull <T> Function<String, Optional<Optional<T>>> readOptional(
            @NotNull Function<String, Optional<T>> read
    ) {
        return s -> {
            if (!s.startsWith("Optional")) return Optional.empty();
            s = s.substring("Optional".length());
            if (s.equals(".empty")) return Optional.of(Optional.<T>empty());
            if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
            s = tail(init(s));
            Optional<T> ot = read.apply(s);
            return ot.isPresent() ? Optional.of(ot) : Optional.<Optional<T>>empty();
        };
    }

    /**
     * Returns a function which Finds the first occurrence of an {@code Optional} of a given type in a {@code String}.
     * Takes the type's {@code findIn} function. The result returns the {@code Optional} and the index at which it was
     * found and returns an empty outer {@code Optional} if no {@code Optional} is found. Only {@code Optional}s which
     * could have been emitted by {@link java.util.Optional#toString} are recognized. The longest possible
     * {@code Optional} is parsed.
     *
     * <ul>
     *  <li>{@code findIn} must be non-null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result must only be called on {@code String}s {@code s} such that {@code findIn}, when applied to any
     *  suffix of {@code s}, must not return null, and, if the result is non-empty, its elements are both non-null and
     *  the second element is non-negative. (This precondition is not checked for every suffix of {@code s}.)</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param <T> the type of the {@code Optional}'s value
     * @return a function which finds an {@code Optional} value
     */
    public static @NotNull <T> Function<String, Optional<Pair<Optional<T>, Integer>>> findOptionalIn(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn
    ) {
        return s -> {
            while (true) {
                int optionalIndex = s.indexOf("Optional");
                if (optionalIndex == -1) return Optional.empty();
                s = s.substring(optionalIndex + "Optional".length());
                if (s.startsWith(".empty")) return Optional.of(new Pair<>(Optional.<T>empty(), optionalIndex));
                if (s.startsWith("[")) {
                    Optional<Pair<T, Integer>> found = findIn.apply(s);
                    if (found.isPresent()) {
                        Pair<T, Integer> presentFound = found.get();
                        if (presentFound.a == null || presentFound.b == null)
                            throw new NullPointerException();
                        if (presentFound.b < 0)
                            throw new IllegalArgumentException("findIn should not return indices less than 0");
                        if (found.get().b == 1 && head(s) == '[') {
                            s = s.substring(found.get().a.toString().length() + 1);
                            if (s.startsWith("]")) {
                                return Optional.of(new Pair<>(Optional.of(found.get().a), optionalIndex));
                            }
                        }
                    }
                }
            }
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
    public static @NotNull <T> Function<String, Optional<NullableOptional<T>>> readNullableOptional(
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

    /**
     * Returns a function which finds the first occurrence of a {@code NullableOptional} of a given type in a
     * {@code String}. Takes the type's {@code findIn} function. The result returns the {@code NullableOptional} and
     * the index at which it was found and returns an empty outer {@code Optional} if no {@code NullableOptional} is
     * found. Only {@code NullableOptional}s which could have been emitted by
     * {@link mho.wheels.structures.NullableOptional#toString} are recognized. The longest possible
     * {@code NullableOptional} is parsed.
     *
     * <ul>
     *  <li>{@code findIn} must be non-null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result must only be called on {@code String}s {@code s} such that {@code findIn}, when applied to any
     *  suffix of {@code s}, must not return null, and, if the result is non-empty, its second element is non-negative.
     *  (This precondition is not checked for every suffix of {@code s}.)</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param <T> the type of the {@code NullableOptional}'s value
     * @return a function which finds a {@code NullableOptional} value
     */
    public static @NotNull <T> Function<String, Optional<Pair<NullableOptional<T>, Integer>>> findNullableOptionalIn(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn
    ) {
        return s -> {
            while (true) {
                int optionalIndex = s.indexOf("NullableOptional");
                if (optionalIndex == -1) return Optional.empty();
                s = s.substring(optionalIndex + "NullableOptional".length());
                if (s.startsWith(".empty")) return Optional.of(new Pair<>(NullableOptional.<T>empty(), optionalIndex));
                if (s.startsWith("[")) {
                    Optional<Pair<T, Integer>> found = findIn.apply(s);
                    if (found.isPresent()) {
                        Pair<T, Integer> presentFound = found.get();
                        if (presentFound.b == null)
                            throw new NullPointerException();
                        if (presentFound.b < 0)
                            throw new IllegalArgumentException("findIn should not return indices less than 0");
                        if (found.get().b == 1 && head(s) == '[') {
                            s = s.substring(Objects.toString(found.get().a).length() + 1);
                            if (s.startsWith("]")) {
                                return Optional.of(new Pair<>(NullableOptional.of(found.get().a), optionalIndex));
                            }
                        }
                    }
                }
            }
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
    public static @NotNull <T> Function<String, Optional<List<T>>> readList(
            @NotNull Function<String, Optional<T>> read
    ) {
        return s -> {
            if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
            s = tail(init(s));
            if (s.isEmpty()) return Optional.of(new ArrayList<T>());
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
                    Optional<T> candidate = read.apply(sb.toString());
                    if (candidate.isPresent()) {
                        result.add(candidate.get());
                        break;
                    }
                    if (i == tokens.length - 1) {
                        return Optional.empty();
                    }
                }
            }
            return Optional.of(result);
        };
    }

    public static @NotNull <T> Optional<List<T>> readListWithNulls(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        if (s.isEmpty()) return Optional.of(new ArrayList<T>());
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
                NullableOptional<T> candidate = readWithNulls(read).apply(sb.toString());
                if (candidate.isPresent()) {
                    result.add(candidate.get());
                    break;
                }
                if (i == tokens.length - 1) {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(result);
    }

    /**
     * Finds the first occurrence of a {@code List} of a given type in a {@code String}. Takes the type's
     * {@code findIn} function. Returns the {@code List} and the index at which it was found. Returns an empty
     * {@code Optional} if no {@code List} is found. Only {@code List}s which could have been emitted by
     * {@code java.util.List#toString} are recognized. The longest possible {@code List} is parsed. In some cases there
     * may be ambiguity; for example, when finding a list of {@code String}s in {@code "[a, b, c]"}, both
     * {@code ["a", "b", "c"]} and {@code ["a, b, c"]} are valid interpretations. This method stops reading each list
     * element as soon as possible, so the first option would be returned. However, if some interpretation results in
     * finding a longer overall list, that interpretation is preferred. For example, when finding a list of
     * {@code String}s in {@code "[a], b]"}, the preferred representation is {@code ["a]", "b"]}, not {@code ["a"]},
     * because in the first case more of the original {@code String} is read.
     *
     * <ul>
     *  <li>{@code findIn}, when applied to {@code s}, must not return null, and, if the result is non-empty, its
     *  elements are both non-null and the second element is non-negative.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param s the input {@code String}
     * @param <T> the type of the {@code List}'s values
     * @return the first {@code List<T>} found in {@code s}, and the index at which it was found
     */
    public static @NotNull <T> Optional<Pair<List<T>, Integer>> findListIn(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String usedChars,
            @NotNull String s
    ) {
        return genericFindIn(readList(read), nub(sort(usedChars + "[, ]"))).apply(s);
    }

    public static @NotNull <T> Optional<Pair<List<T>, Integer>> findListWithNullsIn(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String usedChars,
            @NotNull String s
    ) {
        return genericFindIn(t -> readListWithNulls(read, t), nub(sort(usedChars + "[null, ]"))).apply(s);
    }
}
