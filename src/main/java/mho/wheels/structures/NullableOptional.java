package mho.wheels.structures;

import java.util.NoSuchElementException;

public class NullableOptional<T> {
    private boolean present;
    private T x;

    public static <T> NullableOptional<T> of(T x) {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.x = x;
        optional.present = true;
        return optional;
    }

    public static <T> NullableOptional<T> empty() {
        NullableOptional<T> optional = new NullableOptional<>();
        optional.present = false;
        return optional;
    }

    public boolean isPresent() {
        return present;
    }

    public T get() {
        if (!present)
            throw new NoSuchElementException("no value present");
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NullableOptional that = (NullableOptional) o;
        return present == that.present && x.equals(that.x);
    }

    @Override
    public int hashCode() {
        int result = (present ? 1 : 0);
        result = 31 * result + x.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return present ? String.format("Optional[%s]", x) : "Optional.empty";
    }
}
