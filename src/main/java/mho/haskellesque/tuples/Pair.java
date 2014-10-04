package mho.haskellesque.tuples;

import mho.haskellesque.ordering.NullHandlingComparator;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public final class Pair<S, T> {
    public final @Nullable S fst;
    public final @Nullable T snd;

    public Pair(@Nullable S fst, @Nullable T snd) {
        this.fst = fst;
        this.snd = snd;
    }

    private static <S extends Comparable<S>, T extends Comparable<T>> int compare(
            @NotNull Pair<S, T> p,
            @NotNull Pair<S, T> q
    ) {
        Ordering fstOrdering = Ordering.compare(new NullHandlingComparator<>(), p.fst, q.fst);
        if (fstOrdering != Ordering.EQ) return fstOrdering.toInt();
        return Ordering.compare(new NullHandlingComparator<>(), p.snd, q.snd).toInt();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return (fst == null ? pair.fst == null : fst.equals(pair.fst)) &&
               (snd == null ? pair.snd == null : snd.equals(pair.snd));
    }

    @Override
    public int hashCode() {
        int result = fst == null ? 0 : fst.hashCode();
        result = 31 * result + (snd == null ? 0 : snd.hashCode());
        return result;
    }

    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }

    public static class PairComparator<S extends Comparable<S>, T extends Comparable<T>>
            implements Comparator<Pair<S, T>> {
        @Override
        public int compare(@NotNull Pair<S, T> p, @NotNull Pair<S, T> q) {
            return Pair.compare(p, q);
        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }
    }
}