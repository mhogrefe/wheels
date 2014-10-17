package mho.haskellesque.structures;

import mho.haskellesque.ordering.Ordering;
import org.junit.Test;

import static mho.haskellesque.ordering.Ordering.*;
import static org.junit.Assert.*;

public class QuintupleTest {
    @Test
    public void testConstructor() {
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).a, "hi");
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).b, 3);
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).c, true);
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).d, 'a');
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).e, GT);
        aeq(new Quintuple<>("hi", 3, true, 'a', null).a, "hi");
        aeq(new Quintuple<>("hi", 3, true, 'a', null).b, 3);
        aeq(new Quintuple<>("hi", 3, true, 'a', null).c, true);
        aeq(new Quintuple<>("hi", 3, true, 'a', null).d, 'a');
        assertNull(new Quintuple<>("hi", 3, true, 'a', null).e);
        assertNull(new Quintuple<>(null, 3, true, 'a', GT).a);
        aeq(new Quintuple<>(null, 3, true, 'a', GT).b, 3);
        aeq(new Quintuple<>(null, 3, true, 'a', GT).c, true);
        aeq(new Quintuple<>(null, 3, true, 'a', GT).d, 'a');
        aeq(new Quintuple<>(null, 3, true, 'a', GT).e, GT);
        assertNull(new Quintuple<>(null, null, null, null, null).a);
        assertNull(new Quintuple<>(null, null, null, null, null).b);
        assertNull(new Quintuple<>(null, null, null, null, null).c);
        assertNull(new Quintuple<>(null, null, null, null, null).d);
        assertNull(new Quintuple<>(null, null, null, null, null).e);
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
    public void testHashCode() {
        aeq(new Quintuple<>("hi", 3, true, 'a', GT).hashCode(), -503146706);
        aeq(new Quintuple<>("hi", 4, true, 'a', GT).hashCode(), -503116915);
        aeq(new Quintuple<>("bye", 3, true, 'a', GT).hashCode(), 1055869595);
        aeq(new Quintuple<>("bye", 4, true, 'a', GT).hashCode(), 1055899386);
        aeq(new Quintuple<>("hi", 3, true, 'a', null).hashCode(), -1219290516);
        aeq(new Quintuple<>(null, 3, true, 'a', GT).hashCode(), 717419181);
        aeq(new Quintuple<>(null, null, null, null, null).hashCode(), 0);
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
                > pc = new Quintuple.QuintupleComparator<>();
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
