package mho.wheels.structures;

import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Either<A, B> {
    public enum Slot {
        A, B
    }

    private final @NotNull Slot slot;
    private final @Nullable A a;
    private final @Nullable B b;

    private Either(@NotNull Slot slot, @Nullable A a, @Nullable B b) {
        this.slot = slot;
        this.a = a;
        this.b = b;
    }

    public static @NotNull <A, B> Either<A, B> ofA(@Nullable A a) {
        return new Either<>(Slot.A, a, null);
    }

    public static @NotNull <A, B> Either<A, B> ofB(@Nullable B b) {
        return new Either<>(Slot.B, null, b);
    }

    public @NotNull Slot whichSlot() {
        return slot;
    }

    public A a() {
        switch (slot) {
            case A: return a;
            case B: throw new IllegalStateException();
            default: throw new IllegalStateException("unreachable");
        }
    }

    public B b() {
        switch (slot) {
            case A: throw new IllegalStateException();
            case B: return b;
            default: throw new IllegalStateException("unreachable");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Either<?, ?> either = (Either<?, ?>) o;
        return (a == null ? either.a == null : a.equals(either.a))
            && (b == null ? either.b == null : b.equals(either.b));
    }

    @Override
    public int hashCode() {
        return 31 * (a != null ? a.hashCode() : 0) + (b != null ? b.hashCode() : 0);
    }

    public @NotNull String toString() {
        switch (slot) {
            case A: return "[" + a + ", ]";
            case B: return "[, " + b + "]";
            default: throw new IllegalStateException("unreachable");
        }
    }

    public void validate() {
        switch (slot) {
            case A:
                Testing.assertNull(this, b);
                return;
            case B:
                Testing.assertNull(this, a);
                return;
            default: throw new IllegalStateException("unreachable");
        }
    }
}
