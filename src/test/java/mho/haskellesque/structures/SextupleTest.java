package mho.haskellesque.structures;

import mho.haskellesque.ordering.Ordering;
import org.junit.Test;

import static mho.haskellesque.ordering.Ordering.GT;
import static org.junit.Assert.*;

public class SextupleTest {
    @Test
    public void testConstructor() {
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).a, "hi");
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).b, 3);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).c, true);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).d, 'a');
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).e, GT);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).f, 0.5);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).a, "hi");
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).b, 3);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).c, true);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).d, 'a');
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).e, GT);
        assertNull(new Sextuple<>("hi", 3, true, 'a', GT, null).f);
        assertNull(new Sextuple<>(null, 3, true, 'a', GT, 0.5).a);
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).b, 3);
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).c, true);
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).d, 'a');
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).e, GT);
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).f, 0.5);
        assertNull(new Sextuple<>(null, null, null, null, null, null).a);
        assertNull(new Sextuple<>(null, null, null, null, null, null).b);
        assertNull(new Sextuple<>(null, null, null, null, null, null).c);
        assertNull(new Sextuple<>(null, null, null, null, null, null).d);
        assertNull(new Sextuple<>(null, null, null, null, null, null).e);
        assertNull(new Sextuple<>(null, null, null, null, null, null).f);
    }

    @Test
    public void testEquals() {
        assertTrue(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(new Sextuple<>("hi", 4, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(new Sextuple<>("bye", 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(new Sextuple<>("hi", 3, true, 'a', GT, null)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(new Sextuple<>(null, 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
                .equals(new Sextuple<>(null, null, null, null, null, null)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(null));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).equals(0.5));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null).equals(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)));
        assertTrue(new Sextuple<>("hi", 3, true, 'a', GT, null).equals(new Sextuple<>("hi", 3, true, 'a', GT, null)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null)
                .equals(new Sextuple<>("bye", 3, true, 'a', GT, null)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null).equals(new Sextuple<>(null, 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null)
                .equals(new Sextuple<>(null, null, null, null, null, null)));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null).equals(null));
        assertFalse(new Sextuple<>("hi", 3, true, 'a', GT, null).equals(0.5));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(new Sextuple<>("hi", 3, true, 'a', GT, null)));
        assertTrue(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(new Sextuple<>(null, 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(new Sextuple<>(null, 4, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5)
                .equals(new Sextuple<>(null, null, null, null, null, null)));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(null));
        assertFalse(new Sextuple<>(null, 3, true, 'a', GT, 0.5).equals(0.5));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(new Sextuple<>("hi", 3, true, 'a', GT, null)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(new Sextuple<>(null, 3, true, 'a', GT, 0.5)));
        assertTrue(new Sextuple<>(null, null, null, null, null, null)
                .equals(new Sextuple<>(null, null, null, null, null, null)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(null));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(0.5));
    }

    @Test
    public void testHashCode() {
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5).hashCode(), -339294218);
        aeq(new Sextuple<>("hi", 4, true, 'a', GT, 0.5).hashCode(), -338370697);
        aeq(new Sextuple<>("bye", 3, true, 'a', GT, 0.5).hashCode(), 745570857);
        aeq(new Sextuple<>("bye", 4, true, 'a', GT, 0.5).hashCode(), 746494378);
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null).hashCode(), -1410938890);
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5).hashCode(), -1156457385);
        aeq(new Sextuple<>(null, null, null, null, null, null).hashCode(), 0);
    }

    @Test
    public void testToString() {
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), "(hi, 3, true, a, GT, 0.5)");
        aeq(new Sextuple<>("hi", 3, true, 'a', GT, null), "(hi, 3, true, a, GT, null)");
        aeq(new Sextuple<>(null, 3, true, 'a', GT, 0.5), "(null, 3, true, a, GT, 0.5)");
        aeq(new Sextuple<>(null, null, null, null, null, null), "(null, null, null, null, null, null)");
    }

    @Test
    public void testSextupleComparator_compare() {
        Sextuple.SextupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering,
                Double
                > pc = new Sextuple.SextupleComparator<>();
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, null)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, null)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, null)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, null)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 4, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>("hi", 3, true, 'a', GT, null)), 0);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, null),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("hi", 3, true, 'a', GT, null), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, null),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(
                "bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("bye", 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>("bye", 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("bye", 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>("bye", 4, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, null)), 1);
        aeq(pc.compare(new Sextuple<>("bye", 4, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, null)), 1);
        aeq(pc.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(new Sextuple<>(
                "bye", 3, true, 'a', GT, null),
                new Sextuple<>("hi", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), 0);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, null), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>("bye", 3, true, 'a', GT, null), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, null),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, null)), -1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(
                new Sextuple<>(null, 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(new Sextuple<>(null, 3, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(
                new Sextuple<>(null, 3, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>("hi", 3, true, 'a', GT, null)), -1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 3, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>("bye", 4, true, 'a', GT, 0.5)), -1);
        aeq(pc.compare(
                new Sextuple<>(null, 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>(null, 3, true, 'a', GT, 0.5)), 1);
        aeq(pc.compare(new Sextuple<>(null, 4, true, 'a', GT, 0.5), new Sextuple<>(null, 4, true, 'a', GT, 0.5)), 0);
        aeq(pc.compare(
                new Sextuple<>(null, 4, true, 'a', GT, 0.5),
                new Sextuple<>(null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("hi", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>("bye", 3, true, 'a', GT, null)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>(null, 3, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>(null, 4, true, 'a', GT, 0.5)
        ), -1);
        aeq(pc.compare(
                new Sextuple<>(null, null, null, null, null, null),
                new Sextuple<>(null, null, null, null, null, null)
        ), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
