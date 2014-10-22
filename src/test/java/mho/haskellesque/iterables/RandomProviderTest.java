package mho.haskellesque.iterables;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.assertEquals;

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
    public void testPositiveBigIntegers() {
        aeq(take(20, P.positiveBigIntegers()),
                "[48652618031, 19, 1604523, 47342936008929411, 1, 5781660610793558447160696017748849152415," +
                " 8670351559683419543527211, 71846825, 259," +
                " 7532600913987301393787776023776380057180403400673862364081, 25237825, 1350400639," +
                " 520713022518431, 2440457, 21776818515, 1980643, 2193505253511091, 900745, 15115, 32517]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
