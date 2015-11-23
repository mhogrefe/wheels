package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class is just like {@link Optional}, but it can also contain nulls.
 *
 * @param <T> The type of element that {@code this} contains.
 */
public class NullableOptional<T> {
    /**
     * Whether {@code this} contains an element
     */
    private boolean present;

    /**
     * If {@code this} contains an element, that element's value; otherwise, null.
     */
    private @Nullable T x;

    /**
     * Create an empty {@code NullableOptional}.
     *
     * @param <T> the type of element the {@code NullableOptional} would hold if it were not empty.
     * @return an empty {@code NullableOptional}
     */
    public static @NotNull <T> NullableOptional<T> empty() {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.x = null;
        optional.present = false;
        return optional;
    }

    /**
     * Create a non-empty {@code NullableOptional}.
     *
     * <ul>
     *  <li>{@code x} may be any value of type {@code T}, or null.</li>
     *  <li>The result is non-empty.</li>
     * </ul>
     *
     * @param x the value to be contain in the {@code NullableOptional}
     * @param <T> the type of the element contained in the {@code NullableOptional}
     * @return the {@code NullableOptional} containing {@code x}
     */
    public static @NotNull <T> NullableOptional<T> of(@Nullable T x) {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.x = x;
        optional.present = true;
        return optional;
    }

    /**
     * Whether {@code this} contains a value.
     *
     * <ul>
     *  <li>{@code this} cannot be null.</li>
     *  <li>The result may be either boolean.</li>
     * </ul>
     *
     * @return whether {@code this} contains a value
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Returns the value contained in {@code this}.
     *
     * <ul>
     *  <li>{@code this} cannot be empty.</li>
     *  <li>The result may be any value of type {@code T}, or null.</li>
     * </ul>
     *
     * @return the value contained in {@code this}
     */
    public T get() {
        if (!present) {
            throw new NoSuchElementException("this cannot be empty.");
        }
        return x;
    }

    /**
     * Turns an {@code Optional} into a {@code NullableOptional}, preserving the value inside (if it exists).
     *
     * <ul>
     *  <li>{@code ot} cannot be null.</li>
     *  <li>The result does not contain null.</li>
     * </ul>
     *
     * @param ot an {@code Optional}
     * @param <T> the type of element contained in {@code ot}
     * @return a {@code NullableOptional} equivalent to {@code ot}
     */
    public static @NotNull <T> NullableOptional<T> fromOptional(@NotNull Optional<T> ot) {
        return ot.isPresent() ? of(ot.get()) : empty();
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code NullableOptional}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code Object} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        NullableOptional no = (NullableOptional) that;
        return present == no.present && (x != null ? x.equals(no.x) : no.x == null);
    }

    /**
     * Calculates the hash code of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code NullableOptional}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code.
     */
    @Override
    public int hashCode() {
        return  31 * (present ? 1 : 0) + (x != null ? x.hashCode() : 0);
    }

    /**
     * Creates a {@code String} representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code NullableOptional}.</li>
     *  <li>The result is either {@code "NullableOptional.empty"}, or begins with {@code "NullableOptional["} and ends
     *  with {@code "]"}.</li>
     * </ul>
     *
     * @return a {@code String} representation of {@code this}
     */
    @Override
    public @NotNull String toString() {
        return "NullableOptional" + (present ? "[" + x + "]" : ".empty");
    }
}
