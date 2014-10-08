package mho.haskellesque.iterables;

import org.junit.Test;

import static mho.haskellesque.iterables.Exhaustive.*;
import static org.junit.Assert.assertEquals;

public class ExhaustiveTest {
    @Test
    public void testBooleans() {
        aeq(BOOLEANS, "[false, true]");
    }

    @Test
    public void testOrderings() {
        aeq(ORDERINGS, "[EQ, LT, GT]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
