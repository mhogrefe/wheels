package mho.haskellesque.iterables;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RandomProviderTest {
    private @NotNull RandomProvider P;

    @Before
    public void initialize() {
        P = new RandomProvider(new Random(-1781048559808000493L));
    }

    @Test
    public void testBooleans() {
        aeq(take(20, P.booleans()), "[true, true, true, true, false, true, true, false, true, false," +
                                    " false, false, false, true, true, true, true, false, true, false]");
    }

    @Test
    public void testOrderings() {
        aeq(take(20, P.orderings()),
                "[EQ, EQ, LT, LT, LT, EQ, LT, GT, GT, GT, LT, GT, LT, LT, GT, LT, LT, EQ, EQ, LT]");
    }

    @Test
    public void testRoundingModes() {
        aeq(take(20, P.roundingModes()),
                "[HALF_DOWN, HALF_EVEN, HALF_UP, HALF_UP, UNNECESSARY, HALF_UP, FLOOR, DOWN, HALF_DOWN, DOWN," +
                " DOWN, DOWN, UNNECESSARY, FLOOR, HALF_DOWN, HALF_DOWN, FLOOR, UNNECESSARY, HALF_EVEN, CEILING]");
    }

    @Test
    public void testPositiveBytes() {
        aeq(take(20, P.positiveBytes()),
                "[18, 2, 37, 20, 96, 86, 89, 16, 60, 41, 96, 31, 22, 115, 38, 103, 97, 73, 31, 39]");
    }

    @Test
    public void testPositiveShorts() {
        aeq(take(20, P.positiveShorts()),
                "[29559, 24497, 25521, 7601, 15393, 17782, 25443, 13977, 19844, 10977," +
                " 6993, 15895, 13568, 24091, 18433, 27279, 26356, 29039, 23271, 17273]");
    }

    @Test
    public void testPositiveIntegers() {
        aeq(take(20, P.positiveIntegers()),
                "[1749459689, 2143674404, 1412152153, 1557652480, 27277537, 1405165043, 1262439652, 761846727," +
                " 1823634462, 594633726, 589649158, 736945725, 167878909, 1124489230, 1673560191, 1856081227," +
                " 1324337428, 166157729, 2076599129, 1037453260]");
    }

    @Test
    public void testPositiveLongs() {
        aeq(take(20, P.positiveLongs()),
                "[3418999782456442809, 6316449450962204674, 234312252881255396, 7602470043748550772," +
                " 2781843328518141957, 5065047696626797689, 1442068837050396699, 4070971502122296683," +
                " 7070772197709661375, 608893388310409322, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, 3297591106051422579, 5761072552197714005," +
                " 8575917774971103912, 8229809756225242051, 351898943428221388, 2417358956864889798]");
    }

    @Test
    public void testPositiveBigIntegers_Int() {
        aeq(take(20, P.positiveBigIntegers(3)), "[15, 1, 7, 3, 1, 2, 8, 1, 13, 5, 20, 2, 1, 1, 1, 1, 1, 1, 3, 1]");
        aeq(take(20, P.positiveBigIntegers(4)),
                "[1, 1, 1, 6, 4, 94, 59, 4, 1, 1, 1, 43, 15, 1, 3, 1, 2, 103103, 393, 12]");
        aeq(take(20, P.positiveBigIntegers(5)),
                "[1, 2, 2821, 1, 13, 1, 273, 1, 3, 3, 1, 3, 15, 2, 6, 14, 5, 7, 1, 1]");
        aeq(take(20, P.positiveBigIntegers(10)),
                "[418, 1, 886, 15, 2, 1023538995542242, 2527383, 11, 2, 3411," +
                " 10, 4891, 8, 2, 25, 3, 10, 349, 110732294, 3877]");
        aeq(take(20, P.positiveBigIntegers(100)),
                "[631847851262602872164, 62178362933629457256170097449498832870026795417, 547758176," +
                " 2346149950119691144404, 311, 4742738, 67302549518065217887062796935441749979, 53471, 4223," +
                " 17312403, 316463874199, 6, 447122575, 1176, 704610823827," +
                " 31430331193008341986440693101333088795173295035345951291600655076040609838446721240723225651953502" +
                "51261283498014102904063, 7517586777550828054626795662503, 741109, 101419744017795180979313623318," +
                " 25612091393]");
        try {
            P.positiveBigIntegers(2);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            P.positiveBigIntegers(0);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            P.positiveBigIntegers(-4);
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testPositiveBigIntegers() {
        aeq(take(20, P.positiveBigIntegers()),
                "[65649474733, 50, 1752003, 108680047959250986, 2, 169829217569110637456607575012447814909456," +
                " 8046132249267142822265255, 78549137, 3080," +
                " 6955247343603701669934693326084685760295830262297267296665, 547758176, 2133810949," +
                " 547945394950967, 4742738, 27183283269, 1631119, 1811559053982367, 595931, 13367, 20607]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
