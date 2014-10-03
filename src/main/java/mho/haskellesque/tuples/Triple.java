package mho.haskellesque.tuples;

import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public final class Triple<A, B, C> {
    public final @Nullable A a;
    public final @Nullable B b;
    public final @Nullable C c;

    private Triple(@Nullable A a, @Nullable B b, @Nullable C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private static <
                A extends Comparable<A>,
                B extends Comparable<B>,
                C extends Comparable<C>
            > int compare(@NotNull Triple<A, B, C> p, @NotNull Triple<A, B, C> q) {
        Ordering aOrdering = Ordering.compareNullable(p.a, q.a);
        if (aOrdering != Ordering.EQ) return aOrdering.toInt();
        Ordering bOrdering = Ordering.compareNullable(p.b, q.b);
        if (bOrdering != Ordering.EQ) return bOrdering.toInt();
        return Ordering.compareNullable(p.c, q.c).toInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple triple = (Triple) o;
        if (a != null ? !a.equals(triple.a) : triple.a != null) return false;
        if (b != null ? !b.equals(triple.b) : triple.b != null) return false;
        if (c != null ? !c.equals(triple.c) : triple.c != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "(" + a + ", " + b + ", " + c + ")";
    }

    public static class TripleComparator<
                A extends Comparable<A>,
                B extends Comparable<B>,
                C extends Comparable<C>
            > implements Comparator<Triple<A, B, C>> {
        @Override
        public int compare(@NotNull Triple<A, B, C> p, @NotNull Triple<A, B, C> q) {
            return Triple.compare(p, q);
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
