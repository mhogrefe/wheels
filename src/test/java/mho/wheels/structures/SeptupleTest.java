package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import mho.wheels.ordering.comparators.LexComparator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class SeptupleTest {
    private static final List<Integer> x = Arrays.asList(1, 0);

    @Test
    public void testConstructor() {
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).a, "hi");
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).b, 3);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).c, true);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).d, 'a');
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).e, GT);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).f, 0.5);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).g, x);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).a, "hi");
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).b, 3);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).c, true);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).d, 'a');
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).e, GT);
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).f, 0.5);
        assertNull(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).g);
        assertNull(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).a);
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).b, 3);
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).c, true);
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).d, 'a');
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).e, GT);
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).f, 0.5);
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).g, x);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).a);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).b);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).c);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).d);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).e);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).f);
        assertNull(new Septuple<>(null, null, null, null, null, null, null).g);
    }

    @Test
    public void testCompare() {
        aeq(Septuple.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x")
        ), EQ);
        aeq(Septuple.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x")
        ), EQ);
        aeq(Septuple.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x")
        ), EQ);
        aeq(Septuple.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, "x")
        ), LT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, "x")
        ), GT);
        aeq(Septuple.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x"),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, "x")
        ), EQ);
    }

    @Test
    public void testEquals() {
        assertTrue(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>(null, null, null, null, null, null, null)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).equals(null));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x).equals(0.5));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)));
        assertTrue(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
                .equals(new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
                .equals(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
                .equals(new Septuple<>(null, null, null, null, null, null, null)));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).equals(null));
        assertFalse(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null).equals(0.5));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)));
        assertTrue(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>(null, 4, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
                .equals(new Septuple<>(null, null, null, null, null, null, null)));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).equals(null));
        assertFalse(new Septuple<>(null, 3, true, 'a', GT, 0.5, x).equals(0.5));
        assertFalse(new Septuple<>(null, null, null, null, null, null, null)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)));
        assertFalse(new Septuple<>(null, null, null, null, null, null, null)
                .equals(new Septuple<>("hi", 3, true, 'a', GT, null, x)));
        assertFalse(new Septuple<>(null, null, null, null, null, null, null)
                .equals(new Septuple<>(null, 3, true, 'a', GT, 0.5, x)));
        assertTrue(new Septuple<>(null, null, null, null, null, null, null)
                .equals(new Septuple<>(null, null, null, null, null, null, null)));
        assertFalse(new Septuple<>(null, null, null, null, null, null, null).equals(null));
        assertFalse(new Septuple<>(null, null, null, null, null, null, null).equals(0.5));
    }

    @Test
    public void testToString() {
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, x), "(hi, 3, true, a, GT, 0.5, [1, 0])");
        aeq(new Septuple<>("hi", 3, true, 'a', GT, 0.5, null), "(hi, 3, true, a, GT, 0.5, null)");
        aeq(new Septuple<>(null, 3, true, 'a', GT, 0.5, x), "(null, 3, true, a, GT, 0.5, [1, 0])");
        aeq(new Septuple<>(null, null, null, null, null, null, null), "(null, null, null, null, null, null, null)");
    }

    @Test
    public void testSeptupleComparator_compare() {
        Septuple.SeptupleComparator<
                String,
                Integer,
                Boolean,
                Character,
                Ordering,
                Double,
                Iterable<Integer>
                > pc = new Septuple.SeptupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Double>naturalOrder()),
                    Comparator.nullsFirst(new LexComparator<Integer>())
                );
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(new Septuple<>(
                "bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(new Septuple<>(
                "bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), 0);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), 1);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), 0);
        aeq(pc.compare(
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("hi", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("hi", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("bye", 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>("bye", 3, true, 'a', GT, 0.5, null)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>(null, 3, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>(null, 4, true, 'a', GT, 0.5, x)
        ), -1);
        aeq(pc.compare(
                new Septuple<>(null, null, null, null, null, null, null),
                new Septuple<>(null, null, null, null, null, null, null)
        ), 0);
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
