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
    public B apply(@Nullable A a) {
        if (!map.containsKey(a)) {
            throw new IllegalArgumentException(Objects.toString(a));
        }
        return map.get(a);
    }

    public int domainSize() {
        return map.size();
    }

    public @NotNull Set<A> domain() {
        Set<A> domain = new HashSet<>();
        domain.addAll(map.keySet());
        return domain;
    }

    //here range means image, not codomain
    public @NotNull Set<B> range() {
        Set<B> range = new HashSet<>();
        range.addAll(map.values());
        return range;
    }

    public int rangeSize() {
        return range().size();
    }

    public boolean injective() {
        return domainSize() == rangeSize();
    }

    public @NotNull Map<A, B> asMap() {
        Map<A, B> copy = new HashMap<>();
        copy.putAll(map);
        return copy;
    }

    public @NotNull FiniteDomainFunction<A, B> set(A key, B value) {
        Map<A, B> copy = new HashMap<>();
        copy.putAll(map);
        copy.put(key, value);
        return new FiniteDomainFunction<>(copy);
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
