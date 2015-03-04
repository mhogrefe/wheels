package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class FiniteDomainFunction<A, B> implements Function<A, B> {
    private @NotNull Map<A, B> map;

    public FiniteDomainFunction(@NotNull Map<A, B> map) {
        this.map = map;
    }

    public FiniteDomainFunction(@NotNull Iterable<Pair<A, B>> pairs) {
        map = new HashMap<>();
        Set<A> keys = new HashSet<>();
        for (Pair<A, B> pair : pairs) {
            if (keys.contains(pair.a))
                throw new IllegalArgumentException("key " + pair.a + " appears more than once");
            keys.add(pair.a);
            map.put(pair.a, pair.b);
        }
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
    public boolean equals(@Nullable Object o) {
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