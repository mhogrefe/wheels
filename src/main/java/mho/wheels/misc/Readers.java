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
     * Wraps a function from {@code String} to {@code T} in such a way that if the function throws an exception, this
     * method returns an empty {@code Optional}. An empty {@code Optional} is also returned if calling {@code toString}
     * on the extracted value does not return the original {@code String}. For example, calling
     * {@code genericRead(Integer::parseInt, "0xff")} returns {@code Optional.empty} because {@code "0xff".toString()}
     * is {@code "255"}, not {@code "0xff"}.
     *
     * <ul>
     *  <li>{@code read} must terminate on {@code s} (possibly with an exception) and not return a null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param read the original read function
     * @param s the {@code String} to be read
     * @param <T> the type of value read by {@code read}
     * @return the value corresponding to {@code s}, according to the conditions described above
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull <T> Optional<T> genericRead(@NotNull Function<String, T> read, @NotNull String s) {
        boolean nullResult = false;
        try {
            T x = read.apply(s);
            if (x == null) nullResult = true;
            return x.toString().equals(s) ? Optional.of(x) : Optional.<T>empty();
        } catch (Exception e) {
            if (nullResult)
                throw new IllegalArgumentException("read function cannot return null on " + s);
            return Optional.empty();
        }
    }

    /**
     * Given a small {@code Iterable} of values and a {@code String}, finds the first occurrence of a value in the
     * {@code String} as determined by the values' {@code toString} method. If two different values occur at the same
     * index (meaning that one is a prefix of the other), the longer value is preferred. The value is returned along
     * with the index at which it is found. If no value is found, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code xs} cannot contain any nulls.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>{@code T}'s {@code toString} method must terminate on each of {@code xs} without throwing an exception or
     *  returning a null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of values
     * @param s a {@code String} to be searched in
     * @param <T> the type of the values in {@code xs}
     * @return the first value found in {@code s} and its index.
     */
    public static @NotNull <T> Optional<Pair<T, Integer>> genericFindIn(@NotNull Iterable<T> xs, @NotNull String s) {
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
    }

    /**
     * Given a function {@code read} to convert a {@code String} to a value, a {@code String} {@code usedChars}
     * containing all characters needed to represent any value of that type as a {@code String}, and a {@code String}
     * {@code s} to find values in, finds the first occurrence of a value. If two different values occur at the same
     * index (meaning that one is a prefix of the other), the longer value is preferred. The value is returned along
     * with the index at which it is found. If no value is found, an empty {@code Optional} is returned.
     *
     * <ul>
     *  <li>{@code read} must always terminate and never return a null.</li>
     *  <li>{@code usedChars} cannot be null.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param read a function which converts a {@code String} to a value.
     * @param usedChars all the characters needed to represent a value of type {@code T} as a {@code String}.
     * @param s a {@code String} to be searched in
     * @param <T> the type of the values in {@code xs}
     * @return the first value found in {@code s} and its index.
     */
    public static @NotNull <T> Optional<Pair<T, Integer>> genericFindIn(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String usedChars,
            @NotNull String s
    ) {
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
        return genericRead(Boolean::parseBoolean, s);
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
        return genericFindIn(Arrays.asList(false, true), s);
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
        return genericFindIn(Arrays.asList(Ordering.values()), s);
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
        return genericFindIn(Arrays.asList(RoundingMode.values()), s);
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
        return genericRead(BigInteger::new, s);
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
        return genericRead(Byte::parseByte, s);
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
        return genericRead(Short::parseShort, s);
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
        return genericRead(Integer::parseInt, s);
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
        return genericRead(Long::parseLong, s);
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
        return genericRead(Float::parseFloat, s);
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
        return genericFindIn(Readers::readFloat, "-.0123456789EINafinty", s);
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
        return genericRead(Double::parseDouble, s);
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
        return genericFindIn(Readers::readDouble, "-.0123456789EINafinty", s);
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
        return genericRead(BigDecimal::new, s);
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
        return genericFindIn(Readers::readBigDecimal, "+-.0123456789E", s);
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
     * Given a read function and a {@code String}, reads either null or the value given by the function.
     *
     * <ul>
     *  <li>{@code read} must terminate on {@code s} and not return a null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param read a function which takes a {@code String} and returns an {@code Optional{@literal<T>}}.
     * @param s the input {@code String}
     * @param <T> the type of the value to be read
     * @return the value of {@code T} represented by {@code s}, or a wrapped null, or an empty {@code NullableOptional}
     * if {@code s} does not represent any value of {@code T} or null
     */
    public static @NotNull <T> NullableOptional<T> readWithNulls(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (s.equals("null")) {
            return NullableOptional.of(null);
        } else {
            return NullableOptional.fromOptional(read.apply(s));
        }
    }

    /**
     * Given a find-in function and a {@code String} {@code s}, return the value and index of the first "null" or
     * value-string found in {@code s}.
     *
     * <ul>
     *  <li>{@code findIn}, when applied to {@code s}, must not return null, and, if the result is non-empty, its
     *  elements are both non-null and the second element is non-negative.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param s the input {@code String}
     * @param <T> the type of value to be read
     * @return the index and value of the first "null" or value-string, or an empty {@code Optional} if nothing is
     * found
     */
    public static @NotNull <T> Optional<Pair<T, Integer>> findInWithNulls(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull String s
    ) {
        Optional<Pair<T, Integer>> nonNullResult = findIn.apply(s);
        int nullIndex = s.indexOf("null");
        if (nullIndex == -1) return nonNullResult;
        if (!nonNullResult.isPresent()) return Optional.of(new Pair<>(null, nullIndex));
        Pair<T, Integer> unwrapped = nonNullResult.get();
        if (nullIndex > unwrapped.b || (nullIndex == unwrapped.b && unwrapped.a.toString().length() >= 4)) {
            return nonNullResult;
        } else {
            return Optional.of(new Pair<>(null, nullIndex));
        }
    }

    /**
     * Reads an {@link java.util.Optional} from a {@code String}. Only {@code String}s which could have been emitted
     * by {@link java.util.Optional#toString} are recognized.
     *
     * <ul>
     *  <li>{@code read} must terminate on {@code s} and not return a null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param read a function which reads {@code s} into a value of type {@code T}
     * @param s the input {@code String}
     * @param <T> the type of the {@code Optional}'s value
     * @return the {@code Optional} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent an {@code Optional}
     */
    public static @NotNull <T> Optional<Optional<T>> readOptional(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (!s.startsWith("Optional")) return Optional.empty();
        s = s.substring("Optional".length());
        if (s.equals(".empty")) return Optional.of(Optional.<T>empty());
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        Optional<T> ot = read.apply(s);
        return ot.isPresent() ? Optional.of(ot) : Optional.<Optional<T>>empty();
    }

    /**
     * Finds the first occurrence of an {@code Optional} of a given type in a {@code String}. Takes the type's
     * {@code findIn} function. Returns the {@code Optional} and the index at which it was found. Returns an empty
     * outer {@code Optional} if no {@code Optional} is found. Only {@code Optional}s which could have been emitted by
     * {@link java.util.Optional#toString} are recognized. The longest possible {@code Optional} is parsed.
     *
     * <ul>
     *  <li>{@code findIn}, when applied to {@code s}, must not return null, and, if the result is non-empty, its
     *  elements are both non-null and the second element is non-negative.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param s the input {@code String}
     * @param <T> the type of the {@code Optional}'s value
     * @return the first {@code Optional<T>} found in {@code s}, and the index at which it was found
     */
    public static @NotNull <T> Optional<Pair<Optional<T>, Integer>> findOptionalIn(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull String s
    ) {
        while (true) {
            int optionalIndex = s.indexOf("Optional");
            if (optionalIndex == -1) return Optional.empty();
            s = s.substring(optionalIndex + "Optional".length());
            if (s.startsWith(".empty")) return Optional.of(new Pair<>(Optional.<T>empty(), optionalIndex));
            if (s.startsWith("[")) {
                Optional<Pair<T, Integer>> found = findIn.apply(s);
                if (found.isPresent() && found.get().b == 1 && head(s) == '[') {
                    s = s.substring(found.get().a.toString().length() + 1);
                    if (s.startsWith("]")) {
                        return Optional.of(new Pair<>(Optional.of(found.get().a), optionalIndex));
                    }
                }
            }
        }
    }

    /**
     * Reads a {@link mho.wheels.structures.NullableOptional} from a {@code String}. Only {@code String}s which could
     * have been emitted by {@link mho.wheels.structures.NullableOptional#toString} are recognized.
     *
     * <ul>
     *  <li>{@code read} must terminate on {@code s} and not return a null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param read a function which reads {@code s} into a value of type {@code T}
     * @param s the input {@code String}
     * @param <T> the type of the {@code NullableOptional}'s value
     * @return the {@code NullableOptional} represented by {@code s}, or {@code Optional.empty} if {@code s} does not
     * represent a {@code NullableOptional}
     */
    public static @NotNull <T> Optional<NullableOptional<T>> readNullableOptional(
            @NotNull Function<String, NullableOptional<T>> read,
            @NotNull String s
    ) {
        if (!s.startsWith("NullableOptional")) return Optional.empty();
        s = s.substring("NullableOptional".length());
        if (s.equals(".empty")) return Optional.of(NullableOptional.<T>empty());
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        NullableOptional<T> ot = read.apply(s);
        return ot.isPresent() ? Optional.of(ot) : Optional.<NullableOptional<T>>empty();
    }

    /**
     * Finds the first occurrence of a {@code NullableOptional} of a given type in a {@code String}. Takes the type's
     * {@code findIn} function. Returns the {@code NullableOptional} and the index at which it was found. Returns an
     * empty outer {@code Optional} if no {@code NullableOptional} is found. Only {@code NullableOptional}s which could
     * have been emitted by {@link mho.wheels.structures.NullableOptional#toString} are recognized. The longest
     * possible {@code NullableOptional} is parsed.
     *
     * <ul>
     *  <li>{@code findIn}, when applied to {@code s}, must not return null, and, if the result is non-empty, its
     *  second element is non-negative. The first element may be null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param s the input {@code String}
     * @param <T> the type of the {@code Optional}'s value
     * @return the first {@code Optional<T>} found in {@code s}, and the index at which it was found
     */
    public static @NotNull <T> Optional<Pair<NullableOptional<T>, Integer>> findNullableOptionalIn(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull String s
    ) {
        while (true) {
            int optionalIndex = s.indexOf("NullableOptional");
            if (optionalIndex == -1) return Optional.empty();
            s = s.substring(optionalIndex + "NullableOptional".length());
            if (s.startsWith(".empty")) return Optional.of(new Pair<>(NullableOptional.<T>empty(), optionalIndex));
            if (s.startsWith("[")) {
                Optional<Pair<T, Integer>> found = findIn.apply(s);
                if (found.isPresent() && found.get().b == 1 && head(s) == '[') {
                    s = s.substring(found.get().a.toString().length() + 1);
                    if (s.startsWith("]")) {
                        return Optional.of(new Pair<>(NullableOptional.of(found.get().a), optionalIndex));
                    }
                }
            }
        }
    }

    /**
     * Reads a {@link java.util.List} from a {@code String}. Only {@code String}s which could have been emitted by
     * {@code java.util.List#toString} are recognized. In some cases there may be ambiguity; for example, when reading
     * {@code "[a, b, c]"} as a list of {@code String}s, both {@code ["a", "b", "c"]} and {@code ["a, b, c"]} are
     * valid interpretations. This method stops reading each list element as soon as possible, so the first option
     * would be returned.
     *
     * <ul>
     *  <li>{@code findIn}, when applied to {@code s}, must not return null, and, if the result is non-empty, its
     *  elements are both non-null and the second element is non-negative.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param s the input {@code String}
     * @param <T> the type of the {@code List}'s values
     * @return the {@code List} represented by {@code s}, or {@code Optional.empty} if {@code s} does not represent a
     * {@code List}
     */
    public static @NotNull <T> Optional<List<T>> readList(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull String s
    ) {
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        List<T> list = new ArrayList<>();
        while (!s.isEmpty()) {
            Optional<Pair<T, Integer>> next = findIn.apply(s);
            if (!next.isPresent()) return Optional.empty();
            Pair<T, Integer> unwrapped = next.get();
            if (unwrapped.b != 0) return Optional.empty();
            T element = unwrapped.a;
            list.add(element);
            s = s.substring(Objects.toString(element).length());
            if (!s.isEmpty()) {
                if (!s.startsWith(", ")) return Optional.empty();
                s = s.substring(2);
            }
        }
        return Optional.of(list);
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
     * @param findIn a function which takes a {@code String} and returns the index and value of the first value-string
     *               found.
     * @param s the input {@code String}
     * @param <T> the type of the {@code List}'s values
     * @return the first {@code List<T>} found in {@code s}, and the index at which it was found
     */
    public static @NotNull <T> Optional<Pair<List<T>, Integer>> findListIn(
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull String s
    ) {
        List<Integer> leftIndices = toList(elemIndices('[', s));
        List<Integer> rightIndices = reverse(elemIndices(']', s));
        for (int leftIndex : leftIndices) {
            for (int rightIndex : rightIndices) {
                if (rightIndex < leftIndex) break;
                String stripped = s.substring(leftIndex, rightIndex + 1);
                Optional<List<T>> candidate = readList(findIn, stripped);
                if (candidate.isPresent()) return Optional.of(new Pair<>(candidate.get(), leftIndex));
            }
        }
        return Optional.empty();
    }
}
