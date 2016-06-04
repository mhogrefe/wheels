package mho.wheels.structures;

import mho.wheels.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Comparator;

import static mho.wheels.ordering.Ordering.*;
import static org.junit.Assert.*;

public class SextupleTest {
    private static <A, B, C, D, E, F> void constructor_helper(A a, B b, C c, D d, E e, F f, @NotNull String output) {
        aeq(new Sextuple<>(a, b, c, d, e, f), output);
    }

    @Test
    public void testConstructor() {
        constructor_helper("hi", 3, true, 'a', GT, 0.5, "(hi, 3, true, a, GT, 0.5)");
        constructor_helper("hi", 3, true, 'a', GT, null, "(hi, 3, true, a, GT, null)");
        constructor_helper(null, 3, true, 'a', GT, 0.5, "(null, 3, true, a, GT, 0.5)");
        constructor_helper(null, null, null, null, null, null, "(null, null, null, null, null, null)");
    }

    private static <T> void toList_helper(T a, T b, T c, T d, T e, T f, @NotNull String output) {
        aeq(Sextuple.toList(new Sextuple<>(a, b, c, d, e, f)), output);
    }

    @Test
    public void testToList() {
        toList_helper(1, 2, 3, 4, 5, 6, "[1, 2, 3, 4, 5, 6]");
        toList_helper("hi", "bye", "hey", "yo", "ayy", "hello", "[hi, bye, hey, yo, ayy, hello]");
        toList_helper(1, null, null, null, null, null, "[1, null, null, null, null, null]");
    }

    @Test
    public void testCompare() {
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), EQ);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), EQ);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), EQ);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 3, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("hi", 4, true, 'a', GT, 0.5)
        ), LT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 3, true, 'a', GT, 0.5)
        ), GT);
        aeq(Sextuple.compare(
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5),
                new Sextuple<>("bye", 4, true, 'a', GT, 0.5)
        ), EQ);
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
        assertFalse(new Sextuple<>(null, null, null, null, null, null)
                .equals(new Sextuple<>("hi", 3, true, 'a', GT, 0.5)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null)
                .equals(new Sextuple<>("hi", 3, true, 'a', GT, null)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null)
                .equals(new Sextuple<>(null, 3, true, 'a', GT, 0.5)));
        assertTrue(new Sextuple<>(null, null, null, null, null, null)
                .equals(new Sextuple<>(null, null, null, null, null, null)));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(null));
        assertFalse(new Sextuple<>(null, null, null, null, null, null).equals(0.5));
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
                > pc = new Sextuple.SextupleComparator<>(
                    Comparator.nullsFirst(Comparator.<String>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Integer>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Boolean>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Character>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Ordering>naturalOrder()),
                    Comparator.nullsFirst(Comparator.<Double>naturalOrder())
                );
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
