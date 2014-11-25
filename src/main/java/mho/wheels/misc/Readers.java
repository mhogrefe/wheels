package mho.wheels.misc;

import mho.wheels.iterables.IterableUtils;
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
public class Readers {
    /**
     * Wraps a function from {@code String} to {@code T} in such a way that if the function throws an exception, this
     * method returns an empty {@code Optional}. An empty {@code Optional} is also returned if calling {@code toString}
     * on the extracted value does not return the original {@code String}. For example, calling
     * {@code genericRead(Integer::parseInt, "0xff")} returns {@code Optional.empty} because {@code "0xff".toString()}
     * is "255", not {@code "0xff"}.
     *
     * <ul>
     *  <li>{@code read} must terminate on every input and never return a null.</li>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param read the original read function
     * @param s the {@code String} to be read
     * @param <T> the type of value read by {@code read}
     * @return the value corresponding to {@code s}, according to the conditions described above
     */
    private static @NotNull <T> Optional<T> genericRead(@NotNull Function<String, T> read, @NotNull String s) {
        try {
            T x = read.apply(s);
            return x.toString().equals(s) ? Optional.of(x) : Optional.<T>empty();
        } catch (Exception e) {
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
     *  <li>{@code xs} cannot contain any nulls, cannot contain any repetitions, and each element must support
     *  {@code T}'s {@code toString} method.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is non-null. If it is non-empty, then neither of the {@code Pair}'s components is null, and the
     *  second component is non-negative.</li>
     * </ul>
     *
     * @param xs an {@code Iterable} of values
     * @param s a {@code String} to be searched in
     * @param <T> the type of the values in {@code xs}
     * @return the first value found in {@code s} and its index.
     */
    private static @NotNull <T> Optional<Pair<T, Integer>> genericFindIn(@NotNull Iterable<T> xs, @NotNull String s) {
        Iterable<Triple<T, String, Integer>> candidates = filter(
                u -> {
                    assert u.c != null;
                    return u.c != -1;
                },
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
            assert x.b != null;
            assert x.c != null;
            assert y.b != null;
            assert y.c != null;
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
     * Finds the first occurrence of a {@code boolean} in a {@code String} and returns the {@code boolean} and the
     * index at which it was found. Returns an empty {@code Optional} if no {@code boolean} is found.
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
     * Finds the first occurrence of an {@code Ordering} in a {@code String} and returns the {@code Ordering} and the
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
     * Finds the first occurrence of a {@code RoundingMode} in a {@code String} and returns the {@code RoundingMode}
     * and the index at which it was found. Returns an empty {@code Optional} if no {@code RoundingMode} is found.
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

    public static @NotNull Optional<Byte> readByte(@NotNull String s) {
        return genericRead(Byte::parseByte, s);
    }

    public static @NotNull Optional<Short> readShort(@NotNull String s) {
        return genericRead(Short::parseShort, s);
    }

    public static @NotNull Optional<Integer> readInteger(@NotNull String s) {
        return genericRead(Integer::parseInt, s);
    }

    public static @NotNull Optional<Long> readLong(@NotNull String s) {
        return genericRead(Long::parseLong, s);
    }

    public static @NotNull Optional<BigInteger> readBigInteger(@NotNull String s) {
        return genericRead(BigInteger::new, s);
    }

    public static @NotNull Optional<BigDecimal> readBigDecimal(@NotNull String s) {
        return genericRead(BigDecimal::new, s);
    }

    public static @NotNull Optional<Float> readFloat(@NotNull String s) {
        return genericRead(Float::parseFloat, s);
    }

    public static @NotNull Optional<Double> readDouble(@NotNull String s) {
        return genericRead(Double::parseDouble, s);
    }

    public static @NotNull Optional<Character> readCharacter(@NotNull String s) {
        return s.length() == 1 ? Optional.of(s.charAt(0)) : Optional.<Character>empty();
    }

    public static @NotNull Optional<String> readString(@NotNull String s) {
        return Optional.of(s);
    }

    public static @NotNull <T> NullableOptional<T> readWithNull(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (s.equals("null")) {
            return NullableOptional.of(null);
        } else {
            return NullableOptional.fromOptional(read.apply(s));
        }
    }

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

    public static @NotNull <T> Optional<List<T>> readList(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        List<T> list = new ArrayList<>();
        for (String token : s.split(", ")) {
            Optional<T> ox = read.apply(token);
            if (!ox.isPresent()) return Optional.empty();
            list.add(ox.get());
        }
        return Optional.of(list);
    }

    public static @NotNull <T> Optional<List<T>> readListWithNulls(
            @NotNull Function<String, Optional<T>> read,
            @NotNull String s
    ) {
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        List<T> list = new ArrayList<>();
        for (String token : s.split(", ")) {
            NullableOptional<T> ox = readWithNull(read, token);
            if (!ox.isPresent()) return Optional.empty();
            list.add(ox.get());
        }
        return Optional.of(list);
    }
}
