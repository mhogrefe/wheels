package mho.wheels.ordering.comparators;

import mho.wheels.ordering.Ordering;

import java.util.Comparator;
import java.util.Iterator;

import static mho.wheels.ordering.Ordering.*;

public class LexComparator<T extends Comparable<T>> implements Comparator<Iterable<T>> {
    private final Comparator<T> elementComparator;

    public LexComparator() {
        this.elementComparator = null;
    }

    public LexComparator(Comparator<T> comparator) {
        this.elementComparator = comparator;
    }

    @Override
    public int compare(Iterable<T> xs, Iterable<T> ys) {
        Iterator<T> xsi = xs.iterator();
        Iterator<T> ysi = ys.iterator();
        while (xsi.hasNext()) {
            if (!ysi.hasNext()) return GT.toInt();
            Ordering elementOrdering;
            if (elementComparator == null) {
                elementOrdering = Ordering.compare(xsi.next(), ysi.next());
            } else {
                elementOrdering = Ordering.compare(elementComparator, xsi.next(), ysi.next());
            }
            if (elementOrdering != EQ) return elementOrdering.toInt();
        }
        return (ysi.hasNext() ? LT : EQ).toInt();
    }
}
