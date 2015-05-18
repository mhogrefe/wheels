package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;

public class NullableOptional<T> {
    private boolean present;
    private @Nullable T x;

    public static @NotNull <T> NullableOptional<T> of(@Nullable T x) {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.x = x;
        optional.present = true;
        return optional;
    }

    public static @NotNull <T> NullableOptional<T> empty() {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.present = false;
        return optional;
    }

    public boolean isPresent() {
        return present;
    }

    public @Nullable T get() {
        if (!present)
            throw new NoSuchElementException("no value present");
        return x;
    }

    public static @NotNull <T> NullableOptional<T> fromOptional(@NotNull Optional<T> ot) {
        return ot.isPresent() ? of(ot.get()) : empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NullableOptional that = (NullableOptional) o;
        return present == that.present && (x != null ? x.equals(that.x) : that.x == null);
    }

    @Override
    public int hashCode() {
        return  31 * (present ? 1 : 0) + (x != null ? x.hashCode() : 0);
    }

    @Override
    public @NotNull String toString() {
        return present ? String.format("NullableOptional[%s]", x) : "NullableOptional.empty";
    }
}
