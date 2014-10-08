package mho.haskellesque.iterables;

import org.junit.Test;

import java.util.List;

import static mho.haskellesque.iterables.Exhaustive.*;
import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;

public class ExhaustiveTest {
    @Test
    public void testBooleans() {
        aeq(BOOLEANS, "[false, true]");
    }

    @Test
    public void testOrderedOrderings() {
        aeq(ORDERED_ORDERINGS, "[LT, EQ, GT]");
    }

    @Test
    public void testOrderings() {
        aeq(ORDERINGS, "[EQ, LT, GT]");
    }

    @Test
    public void testOrderedBytes() {
        assertEquals(length(ORDERED_BYTES), 256);
        aeq(take(20, ORDERED_BYTES),
                "[-128, -127, -126, -125, -124, -123, -122, -121, -120, -119," +
                " -118, -117, -116, -115, -114, -113, -112, -111, -110, -109]");
    }

    @Test
    public void testOrderedShorts() {
        assertEquals(length(ORDERED_SHORTS), 65536);
        aeq(take(20, ORDERED_SHORTS),
                "[-32768, -32767, -32766, -32765, -32764, -32763, -32762, -32761, -32760, -32759," +
                " -32758, -32757, -32756, -32755, -32754, -32753, -32752, -32751, -32750, -32749]");
    }

    @Test
    public void testOrderedIntegers() {
        aeq(take(20, ORDERED_INTEGERS),
                "[-2147483648, -2147483647, -2147483646, -2147483645, -2147483644," +
                " -2147483643, -2147483642, -2147483641, -2147483640, -2147483639," +
                " -2147483638, -2147483637, -2147483636, -2147483635, -2147483634," +
                " -2147483633, -2147483632, -2147483631, -2147483630, -2147483629]");
    }

    @Test
    public void testOrderedLongs() {
        aeq(take(20, ORDERED_LONGS),
                "[-9223372036854775808, -9223372036854775807, -9223372036854775806, -9223372036854775805," +
                " -9223372036854775804, -9223372036854775803, -9223372036854775802, -9223372036854775801," +
                " -9223372036854775800, -9223372036854775799, -9223372036854775798, -9223372036854775797," +
                " -9223372036854775796, -9223372036854775795, -9223372036854775794, -9223372036854775793," +
                " -9223372036854775792, -9223372036854775791, -9223372036854775790, -9223372036854775789]");
    }

    @Test
    public void testPositiveBytes() {
        assertEquals(length(POSITIVE_BYTES), 127);
        aeq(take(20, POSITIVE_BYTES), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveShorts() {
        assertEquals(length(POSITIVE_SHORTS), 32767);
        aeq(take(20, POSITIVE_SHORTS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveIntegers() {
        aeq(take(20, POSITIVE_INTEGERS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveLongs() {
        aeq(take(20, POSITIVE_LONGS), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testPositiveBigIntegers() {
        aeq(take(20, POSITIVE_BIG_INTEGERS),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }

    @Test
    public void testNegativeBytes() {
        assertEquals(length(NEGATIVE_BYTES), 128);
        aeq(take(20, NEGATIVE_BYTES),
                "[-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
