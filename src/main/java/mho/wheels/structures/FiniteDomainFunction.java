package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class FiniteDomainFunction<A, B> implements Function<A, B> {
    private @NotNull Map<A, B> map;

    public FiniteDomainFunction(@NotNull Map<A, B> map) {
        this.map = map;
    }

    @Override
    public @Nullable B apply(@Nullable A a) {
        if (!map.containsKey(a))
            throw new IllegalArgumentException(Objects.toString(a));
        return map.get(a);
    }

    public int domainSize() {
        return map.size();
    }

    //here range means image, not codomain
    public int rangeSize() {
        Set<B> values = new HashSet<>();
        values.addAll(map.values());
        return values.size();
    }

    public boolean injective() {
        return domainSize() == rangeSize();
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && map.equals(((FiniteDomainFunction) o).map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    public @NotNull String toString() {
        return map.toString();
    }
}
