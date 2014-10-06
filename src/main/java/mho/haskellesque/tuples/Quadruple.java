package mho.haskellesque.tuples;

import mho.haskellesque.ordering.NullHandlingComparator;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public final class Quadruple<A, B, C, D> {
    public final @Nullable A a;
    public final @Nullable B b;
    public final @Nullable C c;
    public final @Nullable D d;

    public Quadruple(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    private static <
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>
            > int compare(@NotNull Quadruple<A, B, C, D> p, @NotNull Quadruple<A, B, C, D> q) {
        Ordering aOrdering = Ordering.compare(new NullHandlingComparator<>(), p.a, q.a);
        if (aOrdering != Ordering.EQ) return aOrdering.toInt();
        Ordering bOrdering = Ordering.compare(new NullHandlingComparator<>(), p.b, q.b);
        if (bOrdering != Ordering.EQ) return bOrdering.toInt();
        Ordering cOrdering = Ordering.compare(new NullHandlingComparator<>(), p.c, q.c);
        if (cOrdering != Ordering.EQ) return cOrdering.toInt();
        return Ordering.compare(new NullHandlingComparator<>(), p.d, q.d).toInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quadruple Quadruple = (Quadruple) o;
        return (a == null ? Quadruple.a == null : a.equals(Quadruple.a)) &&
                (b == null ? Quadruple.b == null : b.equals(Quadruple.b)) &&
                (c == null ? Quadruple.c == null : c.equals(Quadruple.c)) &&
                (d == null ? Quadruple.d == null : d.equals(Quadruple.d));
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ")";
    }

    public static class QuadrupleComparator<
            A extends Comparable<A>,
            B extends Comparable<B>,
            C extends Comparable<C>,
            D extends Comparable<D>
            > implements Comparator<Quadruple<A, B, C, D>> {
        @Override
        public int compare(@NotNull Quadruple<A, B, C, D> p, @NotNull Quadruple<A, B, C, D> q) {
            return Quadruple.compare(p, q);
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
