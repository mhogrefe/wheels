package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class QuintupleTest {
    private static <A, B, C, D, E> void constructor_helper(A a, B b, C c, D d, E e, @NotNull String output) {
        aeq(new Quintuple<>(a, b, c, d, e), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', GT, "(hi, 3, true, a, GT)");
        constructor_helper("hi", 3, true, 'a', null, "(hi, 3, true, a, null)");
        constructor_helper(null, 3, true, 'a', GT, "(null, 3, true, a, GT)");
        constructor_helper(null, null, null, null, null, "(null, null, null, null, null)");
    }

    @Test
    public void testCompare() {
        aeq(Quintuple.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), EQ);
        aeq(Quintuple.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), EQ);
        aeq(Quintuple.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), EQ);
        aeq(Quintuple.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), LT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), GT);
        aeq(Quintuple.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), EQ);
    }

    @Test
    public void testEquals() {
        assertTrue(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>("hi", 3, true, 'a', GT)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>("hi", 4, true, 'a', GT)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>("bye", 3, true, 'a', GT)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>("hi", 3, true, 'a', null)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>(null, 3, true, 'a', GT)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(new Quintuple<>(null, null, null, null, null)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(null));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', GT).equals(0.5));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(new Quintuple<>("hi", 3, true, 'a', GT)));
        assertTrue(new Quintuple<>("hi", 3, true, 'a', null).equals(new Quintuple<>("hi", 3, true, 'a', null)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(new Quintuple<>("bye", 3, true, 'a', null)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(new Quintuple<>(null, 3, true, 'a', GT)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(new Quintuple<>(null, null, null, null, null)));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(null));
        assertFalse(new Quintuple<>("hi", 3, true, 'a', null).equals(0.5));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(new Quintuple<>("hi", 3, true, 'a', GT)));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(new Quintuple<>("hi", 3, true, 'a', null)));
        assertTrue(new Quintuple<>(null, 3, true, 'a', GT).equals(new Quintuple<>(null, 3, true, 'a', GT)));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(new Quintuple<>(null, 4, true, 'a', GT)));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(new Quintuple<>(null, null, null, null, null)));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(null));
        assertFalse(new Quintuple<>(null, 3, true, 'a', GT).equals(0.5));
        assertFalse(new Quintuple<>(null, null, null, null, null).equals(new Quintuple<>("hi", 3, true, 'a', GT)));
        assertFalse(new Quintuple<>(null, null, null, null, null).equals(new Quintuple<>("hi", 3, true, 'a', null)));
        assertFalse(new Quintuple<>(null, null, null, null, null).equals(new Quintuple<>(null, 3, true, 'a', GT)));
        assertTrue(new Quintuple<>(null, null, null, null, null)
                .equals(new Quintuple<>(null, null, null, null, null)));
        assertFalse(new Quintuple<>(null, null, null, null, null).equals(null));
        assertFalse(new Quintuple<>(null, null, null, null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Quintuple<>("hi", 3, true, 'a', GT), "(hi, 3, true, a, GT)");
        aeq(new Quintuple<>("hi", 3, true, 'a', null), "(hi, 3, true, a, null)");
        aeq(new Quintuple<>(null, 3, true, 'a', GT), "(null, 3, true, a, GT)");
        aeq(new Quintuple<>(null, null, null, null, null), "(null, null, null, null, null)");
    }

    @Test
    public void testQuintupleComparator_compare() {
        Quintuple.QuintupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering
                > pc = new Quintuple.QuintupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder())
                );
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', null)), 0);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("hi", 3, true, 'a', null), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>("bye", 3, true, 'a', null)), 0);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, 4, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>("bye", 3, true, 'a', null), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 3, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, 3, true, 'a', GT)), 1);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, 4, true, 'a', GT)), 0);
        aeq(pc.compare(new Quintuple<>(null, 4, true, 'a', GT), new Quintuple<>(null, null, null, null, null)), 1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("hi", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 4, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>("bye", 3, true, 'a', null)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>(null, 3, true, 'a', GT)), -1);
        aeq(pc.compare(new Quintuple<>(null, null, null, null, null), new Quintuple<>(null, 4, true, 'a', GT)), -1);
        aeq(pc.compare(
                new Quintuple<>(null, null, null, null, null),
                new Quintuple<>(null, null, null, null, null)
        ), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
