package mho.wheels.misc;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.NullableOptional;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;

public class Readers {
    public @NotNull Optional<Ordering> readOrdering(@NotNull String s) {
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

    public @NotNull Optional<RoundingMode> readRoundingMode(@NotNull String s) {
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

    private static @NotNull <T> Optional<T> genericRead(@NotNull Function<String, T> read, @NotNull String s) {
        try {
            T x = read.apply(s);
            return x.toString().equals(s) ? Optional.of(x) : Optional.<T>empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static @NotNull Optional<Boolean> readBoolean(@NotNull String s) {
        return genericRead(Boolean::parseBoolean, s);
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
            @NotNull Function<String, NullableOptional<T>> read,
            @NotNull String s
    ) {
        if (s.length() < 2 || head(s) != '[' || last(s) != ']') return Optional.empty();
        s = tail(init(s));
        List<T> list = new ArrayList<>();
        for (String token : s.split(", ")) {
            NullableOptional<T> ox = read.apply(token);
            if (!ox.isPresent()) return Optional.empty();
            list.add(ox.get());
        }
        return Optional.of(list);
    }
}
