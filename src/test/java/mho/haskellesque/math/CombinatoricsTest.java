package mho.haskellesque.math;

import mho.haskellesque.iterables.IterableUtils;
import org.junit.Test;

import java.math.BigInteger;

import static mho.haskellesque.math.Combinatorics.*;
import static org.junit.Assert.assertEquals;

public class CombinatoricsTest {
    @Test
    public void testFactorial_Int() {
        aeq(factorial(0), 1);
        aeq(factorial(1), 1);
        aeq(factorial(2), 2);
        aeq(factorial(3), 6);
        aeq(factorial(4), 24);
        aeq(factorial(5), 120);
        aeq(factorial(6), 720);
        aeq(factorial(10), 3628800);
        aeq(factorial(100), "9332621544394415268169923885626670049071596826438162146859296389521759999322991" +
                            "5608941463976156518286253697920827223758251185210916864000000000000000000000000");
    }

    @Test
    public void testFactorial_BigInteger() {
        aeq(factorial(BigInteger.ZERO), 1);
        aeq(factorial(BigInteger.ONE), 1);
        aeq(factorial(BigInteger.valueOf(2)), 2);
        aeq(factorial(BigInteger.valueOf(3)), 6);
        aeq(factorial(BigInteger.valueOf(4)), 24);
        aeq(factorial(BigInteger.valueOf(5)), 120);
        aeq(factorial(BigInteger.valueOf(6)), 720);
        aeq(factorial(BigInteger.TEN), 3628800);
        aeq(factorial(BigInteger.valueOf(100)),
                "9332621544394415268169923885626670049071596826438162146859296389521759999322991" +
                "5608941463976156518286253697920827223758251185210916864000000000000000000000000");
    }

    @Test
    public void testSubfactorial_Int() {
        aeq(subfactorial(0), 1);
        aeq(subfactorial(1), 0);
        aeq(subfactorial(2), 1);
        aeq(subfactorial(3), 2);
        aeq(subfactorial(4), 9);
        aeq(subfactorial(5), 44);
        aeq(subfactorial(6), 265);
        aeq(subfactorial(10), 1334961);
        aeq(subfactorial(100), "3433279598416380476519597752677614203236578380537578498354340028268518079332763" +
                               "2432791396429850988990237345920155783984828001486412574060553756854137069878601");
    }

    @Test
    public void testSubfactorial_BigInteger() {
        aeq(subfactorial(BigInteger.ZERO), 1);
        aeq(subfactorial(BigInteger.ONE), 0);
        aeq(subfactorial(BigInteger.valueOf(2)), 1);
        aeq(subfactorial(BigInteger.valueOf(3)), 2);
        aeq(subfactorial(BigInteger.valueOf(4)), 9);
        aeq(subfactorial(BigInteger.valueOf(5)), 44);
        aeq(subfactorial(BigInteger.valueOf(6)), 265);
        aeq(subfactorial(BigInteger.TEN), 1334961);
        aeq(subfactorial(BigInteger.valueOf(100)),
                "3433279598416380476519597752677614203236578380537578498354340028268518079332763" +
                "2432791396429850988990237345920155783984828001486412574060553756854137069878601");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
