package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class QuadrupleTest {
    private static <A, B, C, D> void constructor_helper(A a, B b, C c, D d, @NotNull String output) {
        aeq(new Quadruple<>(a, b, c, d), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', "(hi, 3, true, a)");
        constructor_helper("hi", 3, true, null, "(hi, 3, true, null)");
        constructor_helper(null, 3, true, 'a', "(null, 3, true, a)");
        constructor_helper(null, null, null, null, "(null, null, null, null)");
    }

    @Test
    public void testCompare() {
        aeq(Quadruple.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), EQ);
        aeq(Quadruple.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), EQ);
        aeq(Quadruple.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), EQ);
        aeq(Quadruple.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), LT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), GT);
        aeq(Quadruple.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), EQ);
    }

    @Test
    public void testEquals() {
        assertTrue(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>("hi", 3, true, 'a')));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>("hi", 4, true, 'a')));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>("bye", 3, true, 'a')));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>("hi", 3, true, null)));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>(null, 3, true, 'a')));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(new Quadruple<>(null, null, null, null)));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(null));
        assertFalse(new Quadruple<>("hi", 3, true, 'a').equals(0.5));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(new Quadruple<>("hi", 3, true, 'a')));
        assertTrue(new Quadruple<>("hi", 3, true, null).equals(new Quadruple<>("hi", 3, true, null)));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(new Quadruple<>("bye", 3, true, null)));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(new Quadruple<>(null, 3, true, 'a')));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(new Quadruple<>(null, null, null, null)));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(null));
        assertFalse(new Quadruple<>("hi", 3, true, null).equals(0.5));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(new Quadruple<>("hi", 3, true, 'a')));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(new Quadruple<>("hi", 3, true, null)));
        assertTrue(new Quadruple<>(null, 3, true, 'a').equals(new Quadruple<>(null, 3, true, 'a')));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(new Quadruple<>(null, 4, true, 'a')));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(new Quadruple<>(null, null, null, null)));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(null));
        assertFalse(new Quadruple<>(null, 3, true, 'a').equals(0.5));
        assertFalse(new Quadruple<>(null, null, null, null).equals(new Quadruple<>("hi", 3, true, 'a')));
        assertFalse(new Quadruple<>(null, null, null, null).equals(new Quadruple<>("hi", 3, true, null)));
        assertFalse(new Quadruple<>(null, null, null, null).equals(new Quadruple<>(null, 3, true, 'a')));
        assertTrue(new Quadruple<>(null, null, null, null).equals(new Quadruple<>(null, null, null, null)));
        assertFalse(new Quadruple<>(null, null, null, null).equals(null));
        assertFalse(new Quadruple<>(null, null, null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Quadruple<>("hi", 3, true, 'a'), "(hi, 3, true, a)");
        aeq(new Quadruple<>("hi", 3, true, null), "(hi, 3, true, null)");
        aeq(new Quadruple<>(null, 3, true, 'a'), "(null, 3, true, a)");
        aeq(new Quadruple<>(null, null, null, null), "(null, null, null, null)");
    }

    @Test
    public void testQuadrupleComparator_compare() {
        Quadruple.QuadrupleComparator<String, Integer, Boolean, Character> pc = new Quadruple.QuadrupleComparator<>(
                Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                Comparator.nullsFirst(Comparator.<Character>naturalOrder())
        );
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("hi", 3, true, null)), 0);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("hi", 3, true, null), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>("bye", 3, true, null)), 0);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, 4, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>("bye", 3, true, null), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 3, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, 3, true, 'a')), 1);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, 4, true, 'a')), 0);
        aeq(pc.compare(new Quadruple<>(null, 4, true, 'a'), new Quadruple<>(null, null, null, null)), 1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("hi", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>("bye", 3, true, null)), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, 3, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, 4, true, 'a')), -1);
        aeq(pc.compare(new Quadruple<>(null, null, null, null), new Quadruple<>(null, null, null, null)), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
