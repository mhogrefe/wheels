package mho.haskellesque.structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuadrupleTest {
    @Test
    public void testConstructor() {
        aeq(new Quadruple<>("hi", 3, true, 'a').a, "hi");
        aeq(new Quadruple<>("hi", 3, true, 'a').b, 3);
        aeq(new Quadruple<>("hi", 3, true, 'a').c, true);
        aeq(new Quadruple<>("hi", 3, true, 'a').d, 'a');
        aeq(new Quadruple<>("hi", 3, true, null).a, "hi");
        aeq(new Quadruple<>("hi", 3, true, null).b, 3);
        aeq(new Quadruple<>("hi", 3, true, null).c, true);
        assertNull(new Quadruple<>("hi", 3, true, null).d);
        assertNull(new Quadruple<>(null, 3, true, 'a').a);
        aeq(new Quadruple<>(null, 3, true, 'a').b, 3);
        aeq(new Quadruple<>(null, 3, true, 'a').c, true);
        aeq(new Quadruple<>(null, 3, true, 'a').d, 'a');
        assertNull(new Quadruple<>(null, null, null, null).a);
        assertNull(new Quadruple<>(null, null, null, null).b);
        assertNull(new Quadruple<>(null, null, null, null).c);
        assertNull(new Quadruple<>(null, null, null, null).d);
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
    public void testHashCode() {
        aeq(new Quadruple<>("hi", 3, true, 'a').hashCode(), 99215380);
        aeq(new Quadruple<>("hi", 4, true, 'a').hashCode(), 99216341);
        aeq(new Quadruple<>("bye", 3, true, 'a').hashCode(), -1374514425);
        aeq(new Quadruple<>("bye", 4, true, 'a').hashCode(), -1374513464);
        aeq(new Quadruple<>("hi", 3, true, null).hashCode(), 99215283);
        aeq(new Quadruple<>(null, 3, true, 'a').hashCode(), 41141);
        aeq(new Quadruple<>(null, null, null, null).hashCode(), 0);
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
        Quadruple.QuadrupleComparator<String, Integer, Boolean, Character> pc = new Quadruple.QuadrupleComparator<>();
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
