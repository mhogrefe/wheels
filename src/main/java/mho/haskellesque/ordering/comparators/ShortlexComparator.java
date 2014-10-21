package mho.haskellesque.ordering.comparators;

import mho.haskellesque.ordering.Ordering;

import java.util.Comparator;
import java.util.Iterator;

import static mho.haskellesque.ordering.Ordering.EQ;
import static mho.haskellesque.ordering.Ordering.GT;
import static mho.haskellesque.ordering.Ordering.LT;

public class ShortlexComparator<T extends Comparable<T>> implements Comparator<Iterable<T>> {
    private final Comparator<T> elementComparator;

    public ShortlexComparator() {
        this.elementComparator = null;
    }

    public ShortlexComparator(Comparator<T> elementComparator) {
        this.elementComparator = elementComparator;
    }

    @Override
    public int compare(Iterable<T> xs, Iterable<T> ys) {
        Iterator<T> xsi = xs.iterator();
        Iterator<T> ysi = ys.iterator();
        while (xsi.hasNext()) {
            if (!ysi.hasNext()) return GT.toInt();
        }
        if (ysi.hasNext()) return LT.toInt();
        xsi = xs.iterator();
        ysi = ys.iterator();
        while (xsi.hasNext()) {
            Ordering elementOrdering;
            if (elementComparator == null) {
                elementOrdering = Ordering.compare(xsi.next(), ysi.next());
            } else {
                elementOrdering = Ordering.compare(elementComparator, xsi.next(), ysi.next());
            }
            if (elementOrdering != EQ) return elementOrdering.toInt();
        }
        return EQ.toInt();
    }
}
