package mho.haskellesque.iterables;

import org.junit.Test;

import java.util.Random;

import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;

public class RandomProviderTest {
    private static final RandomProvider P = new RandomProvider(new Random(-1781048559808000493L));

    @Test
    public void testBooleans() {
        aeq(take(20, P.booleans()), "[true, true, true, true, false, true, true, false, true, false," +
                                    " false, false, false, true, true, true, true, false, true, false]");
    }

    @Test
    public void testOrderings() {
        aeq(take(20, P.orderings()),
                "[GT, LT, LT, LT, GT, LT, EQ, LT, LT, LT, EQ, EQ, EQ, EQ, LT, GT, EQ, GT, GT, LT]");
    }

    @Test
    public void testRoundingModes() {
        aeq(take(20, P.roundingModes()),
                "[UNNECESSARY, CEILING, UP, FLOOR, UP, CEILING, HALF_UP, DOWN, HALF_EVEN, HALF_EVEN, UNNECESSARY," +
                " UNNECESSARY, HALF_UP, HALF_DOWN, HALF_DOWN, CEILING, HALF_UP, HALF_UP, HALF_DOWN, DOWN]");
    }

    @Test
    public void testPositiveBytes() {
        aeq(take(20, P.positiveBytes()),
                "[30, 18, 126, 85, 98, 106, 104, 57, 99, 72, 21, 10, 108, 13, 41, 112, 48, 28, 88, 65]");
    }

    @Test
    public void testPositiveShorts() {
        aeq(take(20, P.positiveShorts()),
                "[26232, 22446, 819, 25605, 30890, 14278, 25772, 9299, 13921, 12781," +
                " 19320, 27743, 3123, 10565, 8214, 14234, 3634, 25164, 24321, 26878]");
    }

    @Test
    public void testPositiveIntegers() {
        aeq(take(20, P.positiveIntegers()),
                "[1862984163, 815791165, 1540725447, 648843781, 1619213527, 9372337, 2004903603, 239712816," +
                " 1089616606, 1400853891, 648189510, 1080423068, 1005171783, 1235207709, 541109400, 495449479," +
                " 143140885, 1024190606, 1523489560, 1031333220]");
    }

    @Test
    public void testPositiveLongs() {
        aeq(take(20, P.positiveLongs()),
                "[6694779733869837341, 171095588617588254, 4345311666977507873, 8946000396800402890," +
                " 545386723573518150, 5871724929163162481, 2120885861964924932, 6863539866563558879," +
                " 3561665915256856874, 5895805929385677630, 7539556068943214110, 418967922702867146," +
                " 3381380479067629929, 5947433037806119687, 219141394985415818, 1332043550178265449," +
                " 2728334591496020257, 4587580059396856868, 8563056830601339847, 7734208068585898642]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
