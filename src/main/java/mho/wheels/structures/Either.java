package mho.wheels.structures;

import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Either<A, B> {
    public enum Part {
        LEFT, RIGHT
    }

    private final @NotNull NullableOptional<A> left;
    private final @NotNull NullableOptional<B> right;

    private Either(@NotNull NullableOptional<A> left, @NotNull NullableOptional<B> right) {
        this.left = left;
        this.right = right;
    }

    public static @NotNull <A, B> Either<A, B> leftOf(@Nullable A left) {
        return new Either<>(NullableOptional.of(left), NullableOptional.empty());
    }

    public static @NotNull <A, B> Either<A, B> rightOf(@Nullable B right) {
        return new Either<>(NullableOptional.empty(), NullableOptional.of(right));
    }

    public @NotNull Part whichPart() {
        return left.isPresent() ? Part.LEFT : Part.RIGHT;
    }

    public A left() {
        return left.get();
    }

    public B right() {
        return right.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Either<?, ?> either = (Either<?, ?>) o;
        return left.equals(either.left) && right.equals(either.right);
    }

    @Override
    public int hashCode() {
        return 31 * left.hashCode() + right.hashCode();
    }

    public @NotNull String toString() {
        return "[" + (left.isPresent() ? left.get() : right.get()) + "]";
    }

    public void validate() {
        Testing.assertNotEquals(this, left.isPresent(), right.isPresent());
    }
}
