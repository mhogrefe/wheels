package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class TripleTest {
    private static <A, B, C> void constructor_helper(A a, B b, C c, @NotNull String output) {
        aeq(new Triple<>(a, b, c), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, "(hi, 3, true)");
        constructor_helper("hi", 3, null, "(hi, 3, null)");
        constructor_helper(null, 3, true, "(null, 3, true)");
        constructor_helper(null, null, null, "(null, null, null)");
    }

    @Test
    public void testCompare() {
        aeq(Triple.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 3, true)), EQ);
        aeq(Triple.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 4, true)), LT);
        aeq(Triple.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 3, true)), GT);
        aeq(Triple.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 4, true)), GT);
        aeq(Triple.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 3, true)), GT);
        aeq(Triple.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 4, true)), EQ);
        aeq(Triple.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 3, true)), GT);
        aeq(Triple.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 4, true)), GT);
        aeq(Triple.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 3, true)), LT);
        aeq(Triple.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 4, true)), LT);
        aeq(Triple.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 3, true)), EQ);
        aeq(Triple.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 4, true)), LT);
        aeq(Triple.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 3, true)), LT);
        aeq(Triple.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 4, true)), LT);
        aeq(Triple.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 3, true)), GT);
        aeq(Triple.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 4, true)), EQ);
    }

    @Test
    public void testEquals() {
        assertTrue(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 4, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("bye", 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>("hi", 3, true).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>("hi", 3, true).equals(null));
        assertFalse(new Triple<>("hi", 3, true).equals(0.5));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>("hi", 3, true)));
        assertTrue(new Triple<>("hi", 3, null).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>("bye", 3, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>("hi", 3, null).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>("hi", 3, null).equals(null));
        assertFalse(new Triple<>("hi", 3, null).equals(0.5));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>("hi", 3, null)));
        assertTrue(new Triple<>(null, 3, true).equals(new Triple<>(null, 3, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>(null, 4, true)));
        assertFalse(new Triple<>(null, 3, true).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>(null, 3, true).equals(null));
        assertFalse(new Triple<>(null, 3, true).equals(0.5));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>("hi", 3, true)));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>("hi", 3, null)));
        assertFalse(new Triple<>(null, null, null).equals(new Triple<>(null, 3, true)));
        assertTrue(new Triple<>(null, null, null).equals(new Triple<>(null, null, null)));
        assertFalse(new Triple<>(null, null, null).equals(null));
        assertFalse(new Triple<>(null, null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Triple<>("hi", 3, true), "(hi, 3, true)");
        aeq(new Triple<>("hi", 3, null), "(hi, 3, null)");
        aeq(new Triple<>(null, 3, true), "(null, 3, true)");
        aeq(new Triple<>(null, null, null), "(null, null, null)");
    }

    @Test
    public void testTripleComparator_compare() {
        Triple.TripleComparator<String, Integer, Boolean> pc = new Triple.TripleComparator<>(
                Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Boolean>naturalOrder())
        );
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 3, true)), 0);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("hi", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 4, true)), 0);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("hi", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("hi", 3, null)), 0);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("hi", 3, null), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 3, true)), 0);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 4, true)), 0);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>("bye", 3, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>("bye", 3, null)), 0);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, 4, true)), 1);
        aeq(pc.compare(new Triple<>("bye", 3, null), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, 3, true)), 0);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 3, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, 3, true)), 1);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, 4, true)), 0);
        aeq(pc.compare(new Triple<>(null, 4, true), new Triple<>(null, null, null)), 1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("hi", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>("bye", 3, null)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, 3, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, 4, true)), -1);
        aeq(pc.compare(new Triple<>(null, null, null), new Triple<>(null, null, null)), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
