package mho.haskellesque.math;

import mho.haskellesque.iterables.Exhaustive;
import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.ordering.Ordering;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.math.Combinatorics.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        try {
            factorial(-1);
            fail();
        } catch (ArithmeticException e) {}
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
        try {
            factorial(BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException e) {}
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
        try {
            subfactorial(-1);
            fail();
        } catch (ArithmeticException e) {}
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
        try {
            subfactorial(BigInteger.valueOf(-1));
            fail();
        } catch (ArithmeticException e) {}
    }

    @Test
    public void testPairsAscending() {
        aeq(pairsAscending(Arrays.asList(1, 2, 3), fromString("abc")),
                "[(1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)]");
        aeq(pairsAscending(Arrays.asList(1, null, 3), fromString("abc")),
                "[(1, a), (1, b), (1, c), (null, a), (null, b), (null, c), (3, a), (3, b), (3, c)]");
        aeq(pairsAscending(new ArrayList<Integer>(), fromString("abc")), "[]");
        aeq(pairsAscending(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
    }

    @Test
    public void testTriplesAscending() {
        aeq(triplesAscending(Arrays.asList(1, 2, 3), fromString("abc"), Exhaustive.BOOLEANS),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        aeq(triplesAscending(Arrays.asList(1, null, 3), fromString("abc"), Exhaustive.BOOLEANS),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (null, a, false), (null, a, true), (null, b, false), (null, b, true), (null, c, false)," +
                " (null, c, true), (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false)," +
                " (3, c, true)]");
        aeq(triplesAscending(new ArrayList<Integer>(), fromString("abc"), Exhaustive.BOOLEANS), "[]");
        aeq(triplesAscending(new ArrayList<Integer>(), new ArrayList<Character>(), new ArrayList<Boolean>()), "[]");
    }

    @Test
    public void testQuadruplesAscending() {
        aeq(quadruplesAscending(
                Arrays.asList(1, 2, 3),
                fromString("abc"),
                Exhaustive.BOOLEANS,
                Exhaustive.ORDERINGS
        ),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT), " +
                "(1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ), " +
                "(1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT), " +
                "(1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (2, a, false, EQ), (2, a, false, LT), " +
                "(2, a, false, GT), (2, a, true, EQ), (2, a, true, LT), (2, a, true, GT), (2, b, false, EQ), " +
                "(2, b, false, LT), (2, b, false, GT), (2, b, true, EQ), (2, b, true, LT), (2, b, true, GT), " +
                "(2, c, false, EQ), (2, c, false, LT), (2, c, false, GT), (2, c, true, EQ), (2, c, true, LT), " +
                "(2, c, true, GT), (3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ), " +
                "(3, a, true, LT), (3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT), " +
                "(3, b, true, EQ), (3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT), " +
                "(3, c, false, GT), (3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        aeq(quadruplesAscending(
                Arrays.asList(1, null, 3),
                fromString("abc"),
                Exhaustive.BOOLEANS,
                Exhaustive.ORDERINGS
        ),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT), " +
                "(1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ), " +
                "(1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT), " +
                "(1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (null, a, false, EQ), (null, a, false, LT), " +
                "(null, a, false, GT), (null, a, true, EQ), (null, a, true, LT), (null, a, true, GT), " +
                "(null, b, false, EQ), (null, b, false, LT), (null, b, false, GT), (null, b, true, EQ), " +
                "(null, b, true, LT), (null, b, true, GT), (null, c, false, EQ), (null, c, false, LT), " +
                "(null, c, false, GT), (null, c, true, EQ), (null, c, true, LT), (null, c, true, GT), " +
                "(3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ), (3, a, true, LT), " +
                "(3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT), (3, b, true, EQ), " +
                "(3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT), (3, c, false, GT), " +
                "(3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        aeq(quadruplesAscending(
                new ArrayList<Integer>(),
                fromString("abc"),
                Exhaustive.BOOLEANS,
                Exhaustive.ORDERINGS
        ), "[]");
        aeq(quadruplesAscending(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>()
        ), "[]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
