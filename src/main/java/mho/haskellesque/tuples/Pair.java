package mho.haskellesque.tuples;

import mho.haskellesque.ordering.NullHandlingComparator;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public final class Pair<A, B> {
    public final @Nullable A a;
    public final @Nullable B b;

    public Pair(@Nullable A a, @Nullable B b) {
        this.a = a;
        this.b = b;
    }

    private static <A extends Comparable<A>, B extends Comparable<B>> int compare(
            @NotNull Pair<A, B> p,
            @NotNull Pair<A, B> q
    ) {
        Ordering aOrdering = Ordering.compare(new NullHandlingComparator<>(), p.a, q.a);
        if (aOrdering != Ordering.EQ) return aOrdering.toInt();
        return Ordering.compare(new NullHandlingComparator<>(), p.b, q.b).toInt();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return (a == null ? pair.a == null : a.equals(pair.a)) &&
               (b == null ? pair.b == null : b.equals(pair.b));
    }

    @Override
    public int hashCode() {
        int result = a == null ? 0 : a.hashCode();
        result = 31 * result + (b == null ? 0 : b.hashCode());
        return result;
    }

    public String toString() {
        return "(" + a + ", " + b + ")";
    }

    public static class PairComparator<A extends Comparable<A>, B extends Comparable<B>>
            implements Comparator<Pair<A, B>> {
        @Override
        public int compare(@NotNull Pair<A, B> p, @NotNull Pair<A, B> q) {
            return Pair.compare(p, q);
        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }
    }
}