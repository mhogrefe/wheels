package mho.haskellesque.structures;

import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.ordering.comparators.NullHandlingComparator;
import org.junit.Test;

import java.util.Comparator;

import static mho.haskellesque.ordering.Ordering.*;
import static org.junit.Assert.*;

public class PairTest {
    @Test
    public void testConstructor() {
        aeq(new Pair<>("hi", 3).a, "hi");
        aeq(new Pair<>("hi", 3).b, 3);
        aeq(new Pair<>("hi", null).a, "hi");
        assertNull(new Pair<>("hi", null).b);
        assertNull(new Pair<>(null, 3).a);
        aeq(new Pair<>(null, 3).b, 3);
        assertNull(new Pair<>(null, null).a);
        assertNull(new Pair<>(null, null).b);
    }

    @Test
    public void testCompare() {
        aeq(Pair.compare(new Pair<>("hi", 3), new Pair<>("hi", 3)), EQ);
        aeq(Pair.compare(new Pair<>("hi", 3), new Pair<>("hi", 4)), LT);
        aeq(Pair.compare(new Pair<>("hi", 3), new Pair<>("bye", 3)), GT);
        aeq(Pair.compare(new Pair<>("hi", 3), new Pair<>("bye", 4)), GT);
        aeq(Pair.compare(new Pair<>("hi", 4), new Pair<>("hi", 3)), GT);
        aeq(Pair.compare(new Pair<>("hi", 4), new Pair<>("hi", 4)), EQ);
        aeq(Pair.compare(new Pair<>("hi", 4), new Pair<>("bye", 3)), GT);
        aeq(Pair.compare(new Pair<>("hi", 4), new Pair<>("bye", 4)), GT);
        aeq(Pair.compare(new Pair<>("bye", 3), new Pair<>("hi", 3)), LT);
        aeq(Pair.compare(new Pair<>("bye", 3), new Pair<>("hi", 4)), LT);
        aeq(Pair.compare(new Pair<>("bye", 3), new Pair<>("bye", 3)), EQ);
        aeq(Pair.compare(new Pair<>("bye", 3), new Pair<>("bye", 4)), LT);
        aeq(Pair.compare(new Pair<>("bye", 4), new Pair<>("hi", 3)), LT);
        aeq(Pair.compare(new Pair<>("bye", 4), new Pair<>("hi", 4)), LT);
        aeq(Pair.compare(new Pair<>("bye", 4), new Pair<>("bye", 3)), GT);
        aeq(Pair.compare(new Pair<>("bye", 4), new Pair<>("bye", 4)), EQ);
    }

    @Test
    public void testEquals() {
        assertTrue(new Pair<>("hi", 3).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("hi", 4)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("bye", 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>("hi", 3).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>("hi", 3).equals(null));
        assertFalse(new Pair<>("hi", 3).equals(0.5));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>("hi", 3)));
        assertTrue(new Pair<>("hi", null).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>("bye", null)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>("hi", null).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>("hi", null).equals(null));
        assertFalse(new Pair<>("hi", null).equals(0.5));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>("hi", null)));
        assertTrue(new Pair<>(null, 3).equals(new Pair<>(null, 3)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>(null, 4)));
        assertFalse(new Pair<>(null, 3).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>(null, 3).equals(null));
        assertFalse(new Pair<>(null, 3).equals(0.5));
        assertFalse(new Pair<>(null, null).equals(new Pair<>("hi", 3)));
        assertFalse(new Pair<>(null, null).equals(new Pair<>("hi", null)));
        assertFalse(new Pair<>(null, null).equals(new Pair<>(null, 3)));
        assertTrue(new Pair<>(null, null).equals(new Pair<>(null, null)));
        assertFalse(new Pair<>(null, null).equals(null));
        assertFalse(new Pair<>(null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Pair<>("hi", 3), "(hi, 3)");
        aeq(new Pair<>("hi", null), "(hi, null)");
        aeq(new Pair<>(null, 3), "(null, 3)");
        aeq(new Pair<>(null, null), "(null, null)");
    }

    @Test
    public void testPairComparator_compare() {
        Pair.PairComparator<String, Integer> pc = new Pair.PairComparator<>(
                NullHandlingComparator.of(Comparator.<String>naturalOrder()),
                NullHandlingComparator.of(Comparator.<Integer>naturalOrder())
        );
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", 3)), 0);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("hi", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", 4)), 0);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("hi", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("hi", null)), 0);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", 4)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("hi", null), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", 3)), 0);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", 4)), 0);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>("bye", null)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>("bye", null)), 0);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, 4)), 1);
        aeq(pc.compare(new Pair<>("bye", null), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, 3)), 0);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, 4)), -1);
        aeq(pc.compare(new Pair<>(null, 3), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, 3)), 1);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, 4)), 0);
        aeq(pc.compare(new Pair<>(null, 4), new Pair<>(null, null)), 1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("hi", null)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>("bye", null)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, 3)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, 4)), -1);
        aeq(pc.compare(new Pair<>(null, null), new Pair<>(null, null)), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
