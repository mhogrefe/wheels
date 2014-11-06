package mho.haskellesque.math;

import mho.haskellesque.iterables.ExhaustiveProvider;
import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.ordering.Ordering;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.math.Combinatorics.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CombinatoricsTest {
    private static final @NotNull ExhaustiveProvider P = ExhaustiveProvider.INSTANCE;

    @Test
    public void testFactorial_int() {
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
        } catch (ArithmeticException ignored) {}
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
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testSubfactorial_int() {
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
        } catch (ArithmeticException ignored) {}
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
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testPairsAscending() {
        aeq(pairsAscending(Arrays.asList(1, 2, 3), fromString("abc")),
                "[(1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a), (3, b), (3, c)]");
        aeq(pairsAscending(Arrays.asList(1, null, 3), fromString("abc")),
                "[(1, a), (1, b), (1, c), (null, a), (null, b), (null, c), (3, a), (3, b), (3, c)]");
        aeq(take(20, pairsAscending(P.naturalBigIntegers(), fromString("abc"))),
                "[(0, a), (0, b), (0, c), (1, a), (1, b), (1, c), (2, a), (2, b), (2, c), (3, a)," +
                " (3, b), (3, c), (4, a), (4, b), (4, c), (5, a), (5, b), (5, c), (6, a), (6, b)]");
        aeq(pairsAscending(new ArrayList<Integer>(), fromString("abc")), "[]");
        aeq(pairsAscending(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
    }

    @Test
    public void testTriplesAscending() {
        aeq(triplesAscending(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        aeq(triplesAscending(Arrays.asList(1, null, 3), fromString("abc"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (null, a, false), (null, a, true), (null, b, false), (null, b, true), (null, c, false)," +
                " (null, c, true), (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false)," +
                " (3, c, true)]");
        aeq(take(20, triplesAscending(P.naturalBigIntegers(), fromString("abc"), P.booleans())),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (0, c, false), (0, c, true)," +
                " (1, a, false), (1, a, true), (1, b, false), (1, b, true), (1, c, false), (1, c, true)," +
                " (2, a, false), (2, a, true), (2, b, false), (2, b, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true)]");
        aeq(triplesAscending(new ArrayList<Integer>(), fromString("abc"), P.booleans()), "[]");
        aeq(triplesAscending(new ArrayList<Integer>(), new ArrayList<Character>(), new ArrayList<Boolean>()), "[]");
    }

    @Test
    public void testQuadruplesAscending() {
        aeq(quadruplesAscending(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans(), P.orderings()),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT)," +
                " (1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ)," +
                " (1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT)," +
                " (1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, false, GT), (2, a, true, EQ), (2, a, true, LT), (2, a, true, GT), (2, b, false, EQ)," +
                " (2, b, false, LT), (2, b, false, GT), (2, b, true, EQ), (2, b, true, LT), (2, b, true, GT)," +
                " (2, c, false, EQ), (2, c, false, LT), (2, c, false, GT), (2, c, true, EQ), (2, c, true, LT)," +
                " (2, c, true, GT), (3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ)," +
                " (3, a, true, LT), (3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT)," +
                " (3, b, true, EQ), (3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT)," +
                " (3, c, false, GT), (3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        aeq(quadruplesAscending(Arrays.asList(1, null, 3), fromString("abc"), P.booleans(), P.orderings()),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, false, GT), (1, a, true, EQ), (1, a, true, LT)," +
                " (1, a, true, GT), (1, b, false, EQ), (1, b, false, LT), (1, b, false, GT), (1, b, true, EQ)," +
                " (1, b, true, LT), (1, b, true, GT), (1, c, false, EQ), (1, c, false, LT), (1, c, false, GT)," +
                " (1, c, true, EQ), (1, c, true, LT), (1, c, true, GT), (null, a, false, EQ), (null, a, false, LT)," +
                " (null, a, false, GT), (null, a, true, EQ), (null, a, true, LT), (null, a, true, GT)," +
                " (null, b, false, EQ), (null, b, false, LT), (null, b, false, GT), (null, b, true, EQ)," +
                " (null, b, true, LT), (null, b, true, GT), (null, c, false, EQ), (null, c, false, LT)," +
                " (null, c, false, GT), (null, c, true, EQ), (null, c, true, LT), (null, c, true, GT)," +
                " (3, a, false, EQ), (3, a, false, LT), (3, a, false, GT), (3, a, true, EQ), (3, a, true, LT)," +
                " (3, a, true, GT), (3, b, false, EQ), (3, b, false, LT), (3, b, false, GT), (3, b, true, EQ)," +
                " (3, b, true, LT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT), (3, c, false, GT)," +
                " (3, c, true, EQ), (3, c, true, LT), (3, c, true, GT)]");
        aeq(take(20, quadruplesAscending(P.naturalBigIntegers(), fromString("abc"), P.booleans(), P.orderings())),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, false, GT), (0, a, true, EQ), (0, a, true, LT)," +
                " (0, a, true, GT), (0, b, false, EQ), (0, b, false, LT), (0, b, false, GT), (0, b, true, EQ)," +
                " (0, b, true, LT), (0, b, true, GT), (0, c, false, EQ), (0, c, false, LT), (0, c, false, GT)," +
                " (0, c, true, EQ), (0, c, true, LT), (0, c, true, GT), (1, a, false, EQ), (1, a, false, LT)]");
        aeq(quadruplesAscending(new ArrayList<Integer>(), fromString("abc"), P.booleans(), P.orderings()), "[]");
        aeq(quadruplesAscending(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>()
        ), "[]");
    }

    @Test
    public void testQuintuplesAscending() {
        aeq(quintuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
        ),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, EQ, yes), (1, a, true, EQ, no)," +
                " (1, a, true, LT, yes), (1, a, true, LT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, EQ, yes), (1, b, true, EQ, no)," +
                " (1, b, true, LT, yes), (1, b, true, LT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, EQ, yes), (1, c, true, EQ, no)," +
                " (1, c, true, LT, yes), (1, c, true, LT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, EQ, yes), (2, a, true, EQ, no)," +
                " (2, a, true, LT, yes), (2, a, true, LT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, EQ, yes), (2, b, true, EQ, no)," +
                " (2, b, true, LT, yes), (2, b, true, LT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, EQ, yes), (2, c, true, EQ, no)," +
                " (2, c, true, LT, yes), (2, c, true, LT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, EQ, yes), (3, a, true, EQ, no)," +
                " (3, a, true, LT, yes), (3, a, true, LT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, EQ, yes), (3, b, true, EQ, no)," +
                " (3, b, true, LT, yes), (3, b, true, LT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, EQ, yes), (3, c, true, EQ, no)," +
                " (3, c, true, LT, yes), (3, c, true, LT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]");
        aeq(quintuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, null, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
        ),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, EQ, yes), (1, a, true, EQ, no)," +
                " (1, a, true, LT, yes), (1, a, true, LT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, EQ, yes), (1, b, true, EQ, no)," +
                " (1, b, true, LT, yes), (1, b, true, LT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, EQ, yes), (1, c, true, EQ, no)," +
                " (1, c, true, LT, yes), (1, c, true, LT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (null, a, false, EQ, yes), (null, a, false, EQ, no), (null, a, false, LT, yes)," +
                " (null, a, false, LT, no), (null, a, false, GT, yes), (null, a, false, GT, no)," +
                " (null, a, true, EQ, yes), (null, a, true, EQ, no), (null, a, true, LT, yes)," +
                " (null, a, true, LT, no), (null, a, true, GT, yes), (null, a, true, GT, no)," +
                " (null, b, false, EQ, yes), (null, b, false, EQ, no), (null, b, false, LT, yes)," +
                " (null, b, false, LT, no), (null, b, false, GT, yes), (null, b, false, GT, no)," +
                " (null, b, true, EQ, yes), (null, b, true, EQ, no), (null, b, true, LT, yes)," +
                " (null, b, true, LT, no), (null, b, true, GT, yes), (null, b, true, GT, no)," +
                " (null, c, false, EQ, yes), (null, c, false, EQ, no), (null, c, false, LT, yes)," +
                " (null, c, false, LT, no), (null, c, false, GT, yes), (null, c, false, GT, no)," +
                " (null, c, true, EQ, yes), (null, c, true, EQ, no), (null, c, true, LT, yes)," +
                " (null, c, true, LT, no), (null, c, true, GT, yes), (null, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, EQ, yes), (3, a, true, EQ, no)," +
                " (3, a, true, LT, yes), (3, a, true, LT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, EQ, yes), (3, b, true, EQ, no)," +
                " (3, b, true, LT, yes), (3, b, true, LT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, EQ, yes), (3, c, true, EQ, no)," +
                " (3, c, true, LT, yes), (3, c, true, LT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]");
        aeq(take(20, quintuplesAscending(
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
        )),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, false, GT, yes), (0, a, false, GT, no), (0, a, true, EQ, yes), (0, a, true, EQ, no)," +
                " (0, a, true, LT, yes), (0, a, true, LT, no), (0, a, true, GT, yes), (0, a, true, GT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, false, GT, yes), (0, b, false, GT, no), (0, b, true, EQ, yes), (0, b, true, EQ, no)]");
        aeq(quintuplesAscending(
                new ArrayList<Integer>(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
        ), "[]");
        aeq(quintuplesAscending(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>()
        ), "[]");
    }

    @Test
    public void testSextuplesAscending() {
        aeq(sextuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
        ),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, false, GT, yes, Infinity)," +
                " (1, a, false, GT, yes, NaN), (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN)," +
                " (1, a, true, EQ, yes, Infinity), (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity)," +
                " (1, a, true, EQ, no, NaN), (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN)," +
                " (1, a, true, LT, no, Infinity), (1, a, true, LT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN), (1, b, false, EQ, no, Infinity)," +
                " (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity), (1, b, false, LT, yes, NaN)," +
                " (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN), (1, b, false, GT, yes, Infinity)," +
                " (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity), (1, b, false, GT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (1, b, true, GT, yes, Infinity)," +
                " (1, b, true, GT, yes, NaN), (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, EQ, yes, Infinity), (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity)," +
                " (1, c, true, EQ, no, NaN), (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN)," +
                " (1, c, true, LT, no, Infinity), (1, c, true, LT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (2, a, false, EQ, yes, Infinity), (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity)," +
                " (2, a, false, EQ, no, NaN), (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN)," +
                " (2, a, false, LT, no, Infinity), (2, a, false, LT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN), (2, a, true, EQ, no, Infinity)," +
                " (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity), (2, a, true, LT, yes, NaN)," +
                " (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN), (2, a, true, GT, yes, Infinity)," +
                " (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity), (2, a, true, GT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, false, GT, yes, Infinity)," +
                " (2, b, false, GT, yes, NaN), (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN)," +
                " (2, b, true, EQ, yes, Infinity), (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity)," +
                " (2, b, true, EQ, no, NaN), (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN)," +
                " (2, b, true, LT, no, Infinity), (2, b, true, LT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (2, c, false, EQ, yes, Infinity), (2, c, false, EQ, yes, NaN), (2, c, false, EQ, no, Infinity)," +
                " (2, c, false, EQ, no, NaN), (2, c, false, LT, yes, Infinity), (2, c, false, LT, yes, NaN)," +
                " (2, c, false, LT, no, Infinity), (2, c, false, LT, no, NaN), (2, c, false, GT, yes, Infinity)," +
                " (2, c, false, GT, yes, NaN), (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN)," +
                " (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN), (2, c, true, EQ, no, Infinity)," +
                " (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity), (2, c, true, LT, yes, NaN)," +
                " (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN), (2, c, true, GT, yes, Infinity)," +
                " (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity), (2, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, EQ, yes, Infinity), (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity)," +
                " (3, a, true, EQ, no, NaN), (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN)," +
                " (3, a, true, LT, no, Infinity), (3, a, true, LT, no, NaN), (3, a, true, GT, yes, Infinity)," +
                " (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity), (3, a, true, GT, no, NaN)," +
                " (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN), (3, b, false, EQ, no, Infinity)," +
                " (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity), (3, b, false, LT, yes, NaN)," +
                " (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN), (3, b, false, GT, yes, Infinity)," +
                " (3, b, false, GT, yes, NaN), (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, false, GT, yes, Infinity)," +
                " (3, c, false, GT, yes, NaN), (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN)," +
                " (3, c, true, EQ, yes, Infinity), (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity)," +
                " (3, c, true, EQ, no, NaN), (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN)," +
                " (3, c, true, LT, no, Infinity), (3, c, true, LT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]");
        aeq(sextuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, null, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
        ),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, false, GT, yes, Infinity)," +
                " (1, a, false, GT, yes, NaN), (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN)," +
                " (1, a, true, EQ, yes, Infinity), (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity)," +
                " (1, a, true, EQ, no, NaN), (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN)," +
                " (1, a, true, LT, no, Infinity), (1, a, true, LT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN), (1, b, false, EQ, no, Infinity)," +
                " (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity), (1, b, false, LT, yes, NaN)," +
                " (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN), (1, b, false, GT, yes, Infinity)," +
                " (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity), (1, b, false, GT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (1, b, true, GT, yes, Infinity)," +
                " (1, b, true, GT, yes, NaN), (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, EQ, yes, Infinity), (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity)," +
                " (1, c, true, EQ, no, NaN), (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN)," +
                " (1, c, true, LT, no, Infinity), (1, c, true, LT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (null, a, false, EQ, yes, Infinity), (null, a, false, EQ, yes, NaN)," +
                " (null, a, false, EQ, no, Infinity), (null, a, false, EQ, no, NaN)," +
                " (null, a, false, LT, yes, Infinity), (null, a, false, LT, yes, NaN)," +
                " (null, a, false, LT, no, Infinity), (null, a, false, LT, no, NaN)," +
                " (null, a, false, GT, yes, Infinity), (null, a, false, GT, yes, NaN)," +
                " (null, a, false, GT, no, Infinity), (null, a, false, GT, no, NaN)," +
                " (null, a, true, EQ, yes, Infinity), (null, a, true, EQ, yes, NaN)," +
                " (null, a, true, EQ, no, Infinity), (null, a, true, EQ, no, NaN)," +
                " (null, a, true, LT, yes, Infinity), (null, a, true, LT, yes, NaN)," +
                " (null, a, true, LT, no, Infinity), (null, a, true, LT, no, NaN)," +
                " (null, a, true, GT, yes, Infinity), (null, a, true, GT, yes, NaN)," +
                " (null, a, true, GT, no, Infinity), (null, a, true, GT, no, NaN)," +
                " (null, b, false, EQ, yes, Infinity), (null, b, false, EQ, yes, NaN)," +
                " (null, b, false, EQ, no, Infinity), (null, b, false, EQ, no, NaN)," +
                " (null, b, false, LT, yes, Infinity), (null, b, false, LT, yes, NaN)," +
                " (null, b, false, LT, no, Infinity), (null, b, false, LT, no, NaN)," +
                " (null, b, false, GT, yes, Infinity), (null, b, false, GT, yes, NaN)," +
                " (null, b, false, GT, no, Infinity), (null, b, false, GT, no, NaN)," +
                " (null, b, true, EQ, yes, Infinity), (null, b, true, EQ, yes, NaN)," +
                " (null, b, true, EQ, no, Infinity), (null, b, true, EQ, no, NaN)," +
                " (null, b, true, LT, yes, Infinity), (null, b, true, LT, yes, NaN)," +
                " (null, b, true, LT, no, Infinity), (null, b, true, LT, no, NaN)," +
                " (null, b, true, GT, yes, Infinity), (null, b, true, GT, yes, NaN)," +
                " (null, b, true, GT, no, Infinity), (null, b, true, GT, no, NaN)," +
                " (null, c, false, EQ, yes, Infinity), (null, c, false, EQ, yes, NaN)," +
                " (null, c, false, EQ, no, Infinity), (null, c, false, EQ, no, NaN)," +
                " (null, c, false, LT, yes, Infinity), (null, c, false, LT, yes, NaN)," +
                " (null, c, false, LT, no, Infinity), (null, c, false, LT, no, NaN)," +
                " (null, c, false, GT, yes, Infinity), (null, c, false, GT, yes, NaN)," +
                " (null, c, false, GT, no, Infinity), (null, c, false, GT, no, NaN)," +
                " (null, c, true, EQ, yes, Infinity), (null, c, true, EQ, yes, NaN)," +
                " (null, c, true, EQ, no, Infinity), (null, c, true, EQ, no, NaN)," +
                " (null, c, true, LT, yes, Infinity), (null, c, true, LT, yes, NaN)," +
                " (null, c, true, LT, no, Infinity), (null, c, true, LT, no, NaN)," +
                " (null, c, true, GT, yes, Infinity), (null, c, true, GT, yes, NaN)," +
                " (null, c, true, GT, no, Infinity), (null, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, EQ, yes, Infinity), (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity)," +
                " (3, a, true, EQ, no, NaN), (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN)," +
                " (3, a, true, LT, no, Infinity), (3, a, true, LT, no, NaN), (3, a, true, GT, yes, Infinity)," +
                " (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity), (3, a, true, GT, no, NaN)," +
                " (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN), (3, b, false, EQ, no, Infinity)," +
                " (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity), (3, b, false, LT, yes, NaN)," +
                " (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN), (3, b, false, GT, yes, Infinity)," +
                " (3, b, false, GT, yes, NaN), (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, false, GT, yes, Infinity)," +
                " (3, c, false, GT, yes, NaN), (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN)," +
                " (3, c, true, EQ, yes, Infinity), (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity)," +
                " (3, c, true, EQ, no, NaN), (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN)," +
                " (3, c, true, LT, no, Infinity), (3, c, true, LT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]");
        aeq(take(20, sextuplesAscending(
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                (Iterable<Float>) Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
        )),
                "[(0, a, false, EQ, yes, Infinity), (0, a, false, EQ, yes, NaN), (0, a, false, EQ, no, Infinity)," +
                " (0, a, false, EQ, no, NaN), (0, a, false, LT, yes, Infinity), (0, a, false, LT, yes, NaN)," +
                " (0, a, false, LT, no, Infinity), (0, a, false, LT, no, NaN), (0, a, false, GT, yes, Infinity)," +
                " (0, a, false, GT, yes, NaN), (0, a, false, GT, no, Infinity), (0, a, false, GT, no, NaN)," +
                " (0, a, true, EQ, yes, Infinity), (0, a, true, EQ, yes, NaN), (0, a, true, EQ, no, Infinity)," +
                " (0, a, true, EQ, no, NaN), (0, a, true, LT, yes, Infinity), (0, a, true, LT, yes, NaN)," +
                " (0, a, true, LT, no, Infinity), (0, a, true, LT, no, NaN)]");
        aeq(sextuplesAscending(
                new ArrayList<Integer>(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
        ), "[]");
        aeq(sextuplesAscending(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>(),
                new ArrayList<Float>()
        ), "[]");
    }

    @Test
    public void testSeptuplesAscending() {
        List<Integer> x = Arrays.asList(1, 0);
        List<Integer> y = Arrays.asList(0, 1);
        aeq(septuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                (Iterable<List<Integer>>) Arrays.asList(x, y)
        ),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1])," +
                " (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1])," +
                " (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1])," +
                " (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1])," +
                " (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1])," +
                " (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1])," +
                " (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1])," +
                " (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1])," +
                " (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1])," +
                " (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1])," +
                " (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1])," +
                " (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1])," +
                " (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1])," +
                " (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1])," +
                " (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1])," +
                " (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1])," +
                " (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1])," +
                " (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1])," +
                " (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1])," +
                " (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1])," +
                " (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1])," +
                " (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1])," +
                " (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1])," +
                " (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1])," +
                " (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1])," +
                " (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1])," +
                " (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1])," +
                " (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1])," +
                " (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1])," +
                " (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1])," +
                " (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1])," +
                " (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1])," +
                " (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1])," +
                " (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1])," +
                " (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1])," +
                " (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1])," +
                " (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1])," +
                " (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1])," +
                " (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1])," +
                " (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1])," +
                " (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1])," +
                " (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1])," +
                " (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1])," +
                " (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1])," +
                " (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1])," +
                " (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1])," +
                " (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1])," +
                " (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1])," +
                " (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1])," +
                " (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1])," +
                " (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1])," +
                " (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1])," +
                " (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1])," +
                " (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1])," +
                " (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1])," +
                " (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1])," +
                " (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1])," +
                " (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1])," +
                " (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]");
        aeq(septuplesAscending(
                (Iterable<Integer>) Arrays.asList(1, null, 3),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                (Iterable<List<Integer>>) Arrays.asList(x, y)
        ),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (null, a, false, EQ, yes, Infinity, [1, 0]), (null, a, false, EQ, yes, Infinity, [0, 1])," +
                " (null, a, false, EQ, yes, NaN, [1, 0]), (null, a, false, EQ, yes, NaN, [0, 1])," +
                " (null, a, false, EQ, no, Infinity, [1, 0]), (null, a, false, EQ, no, Infinity, [0, 1])," +
                " (null, a, false, EQ, no, NaN, [1, 0]), (null, a, false, EQ, no, NaN, [0, 1])," +
                " (null, a, false, LT, yes, Infinity, [1, 0]), (null, a, false, LT, yes, Infinity, [0, 1])," +
                " (null, a, false, LT, yes, NaN, [1, 0]), (null, a, false, LT, yes, NaN, [0, 1])," +
                " (null, a, false, LT, no, Infinity, [1, 0]), (null, a, false, LT, no, Infinity, [0, 1])," +
                " (null, a, false, LT, no, NaN, [1, 0]), (null, a, false, LT, no, NaN, [0, 1])," +
                " (null, a, false, GT, yes, Infinity, [1, 0]), (null, a, false, GT, yes, Infinity, [0, 1])," +
                " (null, a, false, GT, yes, NaN, [1, 0]), (null, a, false, GT, yes, NaN, [0, 1])," +
                " (null, a, false, GT, no, Infinity, [1, 0]), (null, a, false, GT, no, Infinity, [0, 1])," +
                " (null, a, false, GT, no, NaN, [1, 0]), (null, a, false, GT, no, NaN, [0, 1])," +
                " (null, a, true, EQ, yes, Infinity, [1, 0]), (null, a, true, EQ, yes, Infinity, [0, 1])," +
                " (null, a, true, EQ, yes, NaN, [1, 0]), (null, a, true, EQ, yes, NaN, [0, 1])," +
                " (null, a, true, EQ, no, Infinity, [1, 0]), (null, a, true, EQ, no, Infinity, [0, 1])," +
                " (null, a, true, EQ, no, NaN, [1, 0]), (null, a, true, EQ, no, NaN, [0, 1])," +
                " (null, a, true, LT, yes, Infinity, [1, 0]), (null, a, true, LT, yes, Infinity, [0, 1])," +
                " (null, a, true, LT, yes, NaN, [1, 0]), (null, a, true, LT, yes, NaN, [0, 1])," +
                " (null, a, true, LT, no, Infinity, [1, 0]), (null, a, true, LT, no, Infinity, [0, 1])," +
                " (null, a, true, LT, no, NaN, [1, 0]), (null, a, true, LT, no, NaN, [0, 1])," +
                " (null, a, true, GT, yes, Infinity, [1, 0]), (null, a, true, GT, yes, Infinity, [0, 1])," +
                " (null, a, true, GT, yes, NaN, [1, 0]), (null, a, true, GT, yes, NaN, [0, 1])," +
                " (null, a, true, GT, no, Infinity, [1, 0]), (null, a, true, GT, no, Infinity, [0, 1])," +
                " (null, a, true, GT, no, NaN, [1, 0]), (null, a, true, GT, no, NaN, [0, 1])," +
                " (null, b, false, EQ, yes, Infinity, [1, 0]), (null, b, false, EQ, yes, Infinity, [0, 1])," +
                " (null, b, false, EQ, yes, NaN, [1, 0]), (null, b, false, EQ, yes, NaN, [0, 1])," +
                " (null, b, false, EQ, no, Infinity, [1, 0]), (null, b, false, EQ, no, Infinity, [0, 1])," +
                " (null, b, false, EQ, no, NaN, [1, 0]), (null, b, false, EQ, no, NaN, [0, 1])," +
                " (null, b, false, LT, yes, Infinity, [1, 0]), (null, b, false, LT, yes, Infinity, [0, 1])," +
                " (null, b, false, LT, yes, NaN, [1, 0]), (null, b, false, LT, yes, NaN, [0, 1])," +
                " (null, b, false, LT, no, Infinity, [1, 0]), (null, b, false, LT, no, Infinity, [0, 1])," +
                " (null, b, false, LT, no, NaN, [1, 0]), (null, b, false, LT, no, NaN, [0, 1])," +
                " (null, b, false, GT, yes, Infinity, [1, 0]), (null, b, false, GT, yes, Infinity, [0, 1])," +
                " (null, b, false, GT, yes, NaN, [1, 0]), (null, b, false, GT, yes, NaN, [0, 1])," +
                " (null, b, false, GT, no, Infinity, [1, 0]), (null, b, false, GT, no, Infinity, [0, 1])," +
                " (null, b, false, GT, no, NaN, [1, 0]), (null, b, false, GT, no, NaN, [0, 1])," +
                " (null, b, true, EQ, yes, Infinity, [1, 0]), (null, b, true, EQ, yes, Infinity, [0, 1])," +
                " (null, b, true, EQ, yes, NaN, [1, 0]), (null, b, true, EQ, yes, NaN, [0, 1])," +
                " (null, b, true, EQ, no, Infinity, [1, 0]), (null, b, true, EQ, no, Infinity, [0, 1])," +
                " (null, b, true, EQ, no, NaN, [1, 0]), (null, b, true, EQ, no, NaN, [0, 1])," +
                " (null, b, true, LT, yes, Infinity, [1, 0]), (null, b, true, LT, yes, Infinity, [0, 1])," +
                " (null, b, true, LT, yes, NaN, [1, 0]), (null, b, true, LT, yes, NaN, [0, 1])," +
                " (null, b, true, LT, no, Infinity, [1, 0]), (null, b, true, LT, no, Infinity, [0, 1])," +
                " (null, b, true, LT, no, NaN, [1, 0]), (null, b, true, LT, no, NaN, [0, 1])," +
                " (null, b, true, GT, yes, Infinity, [1, 0]), (null, b, true, GT, yes, Infinity, [0, 1])," +
                " (null, b, true, GT, yes, NaN, [1, 0]), (null, b, true, GT, yes, NaN, [0, 1])," +
                " (null, b, true, GT, no, Infinity, [1, 0]), (null, b, true, GT, no, Infinity, [0, 1])," +
                " (null, b, true, GT, no, NaN, [1, 0]), (null, b, true, GT, no, NaN, [0, 1])," +
                " (null, c, false, EQ, yes, Infinity, [1, 0]), (null, c, false, EQ, yes, Infinity, [0, 1])," +
                " (null, c, false, EQ, yes, NaN, [1, 0]), (null, c, false, EQ, yes, NaN, [0, 1])," +
                " (null, c, false, EQ, no, Infinity, [1, 0]), (null, c, false, EQ, no, Infinity, [0, 1])," +
                " (null, c, false, EQ, no, NaN, [1, 0]), (null, c, false, EQ, no, NaN, [0, 1])," +
                " (null, c, false, LT, yes, Infinity, [1, 0]), (null, c, false, LT, yes, Infinity, [0, 1])," +
                " (null, c, false, LT, yes, NaN, [1, 0]), (null, c, false, LT, yes, NaN, [0, 1])," +
                " (null, c, false, LT, no, Infinity, [1, 0]), (null, c, false, LT, no, Infinity, [0, 1])," +
                " (null, c, false, LT, no, NaN, [1, 0]), (null, c, false, LT, no, NaN, [0, 1])," +
                " (null, c, false, GT, yes, Infinity, [1, 0]), (null, c, false, GT, yes, Infinity, [0, 1])," +
                " (null, c, false, GT, yes, NaN, [1, 0]), (null, c, false, GT, yes, NaN, [0, 1])," +
                " (null, c, false, GT, no, Infinity, [1, 0]), (null, c, false, GT, no, Infinity, [0, 1])," +
                " (null, c, false, GT, no, NaN, [1, 0]), (null, c, false, GT, no, NaN, [0, 1])," +
                " (null, c, true, EQ, yes, Infinity, [1, 0]), (null, c, true, EQ, yes, Infinity, [0, 1])," +
                " (null, c, true, EQ, yes, NaN, [1, 0]), (null, c, true, EQ, yes, NaN, [0, 1])," +
                " (null, c, true, EQ, no, Infinity, [1, 0]), (null, c, true, EQ, no, Infinity, [0, 1])," +
                " (null, c, true, EQ, no, NaN, [1, 0]), (null, c, true, EQ, no, NaN, [0, 1])," +
                " (null, c, true, LT, yes, Infinity, [1, 0]), (null, c, true, LT, yes, Infinity, [0, 1])," +
                " (null, c, true, LT, yes, NaN, [1, 0]), (null, c, true, LT, yes, NaN, [0, 1])," +
                " (null, c, true, LT, no, Infinity, [1, 0]), (null, c, true, LT, no, Infinity, [0, 1])," +
                " (null, c, true, LT, no, NaN, [1, 0]), (null, c, true, LT, no, NaN, [0, 1])," +
                " (null, c, true, GT, yes, Infinity, [1, 0]), (null, c, true, GT, yes, Infinity, [0, 1])," +
                " (null, c, true, GT, yes, NaN, [1, 0]), (null, c, true, GT, yes, NaN, [0, 1])," +
                " (null, c, true, GT, no, Infinity, [1, 0]), (null, c, true, GT, no, Infinity, [0, 1])," +
                " (null, c, true, GT, no, NaN, [1, 0]), (null, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]");
        aeq(take(20, septuplesAscending(
                P.naturalBigIntegers(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                (Iterable<Float>) Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                (Iterable<List<Integer>>) Arrays.asList(x, y)
        )),
                "[(0, a, false, EQ, yes, Infinity, [1, 0]), (0, a, false, EQ, yes, Infinity, [0, 1])," +
                " (0, a, false, EQ, yes, NaN, [1, 0]), (0, a, false, EQ, yes, NaN, [0, 1])," +
                " (0, a, false, EQ, no, Infinity, [1, 0]), (0, a, false, EQ, no, Infinity, [0, 1])," +
                " (0, a, false, EQ, no, NaN, [1, 0]), (0, a, false, EQ, no, NaN, [0, 1])," +
                " (0, a, false, LT, yes, Infinity, [1, 0]), (0, a, false, LT, yes, Infinity, [0, 1])," +
                " (0, a, false, LT, yes, NaN, [1, 0]), (0, a, false, LT, yes, NaN, [0, 1])," +
                " (0, a, false, LT, no, Infinity, [1, 0]), (0, a, false, LT, no, Infinity, [0, 1])," +
                " (0, a, false, LT, no, NaN, [1, 0]), (0, a, false, LT, no, NaN, [0, 1])," +
                " (0, a, false, GT, yes, Infinity, [1, 0]), (0, a, false, GT, yes, Infinity, [0, 1])," +
                " (0, a, false, GT, yes, NaN, [1, 0]), (0, a, false, GT, yes, NaN, [0, 1])]");
        aeq(septuplesAscending(
                new ArrayList<Integer>(),
                fromString("abc"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                (Iterable<List<Integer>>) Arrays.asList(x, y)
        ), "[]");
        aeq(septuplesAscending(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>(),
                new ArrayList<Float>(),
                new ArrayList<List<Integer>>()
        ), "[]");
    }

    @Test
    public void testListsAscending_int_Iterable() {
        aeq(listsAscending(0, Arrays.asList(1, 2, 3)), "[[]]");
        aeq(listsAscending(1, Arrays.asList(1, 2, 3)), "[[1], [2], [3]]");
        aeq(listsAscending(2, Arrays.asList(1, 2, 3)),
                "[[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]");
        aeq(listsAscending(3, Arrays.asList(1, 2, 3)),
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2]," +
                " [1, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1]," +
                " [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3]," +
                " [3, 3, 1], [3, 3, 2], [3, 3, 3]]");

        aeq(listsAscending(0, Arrays.asList(1, null, 3)), "[[]]");
        aeq(listsAscending(1, Arrays.asList(1, null, 3)), "[[1], [null], [3]]");
        aeq(listsAscending(2, Arrays.asList(1, null, 3)),
                "[[1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1], [3, null], [3, 3]]");
        aeq(listsAscending(3, Arrays.asList(1, null, 3)),
                "[[1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null], [1, null, 3], [1, 3, 1]," +
                " [1, 3, null], [1, 3, 3], [null, 1, 1], [null, 1, null], [null, 1, 3], [null, null, 1]," +
                " [null, null, null], [null, null, 3], [null, 3, 1], [null, 3, null], [null, 3, 3], [3, 1, 1]," +
                " [3, 1, null], [3, 1, 3], [3, null, 1], [3, null, null], [3, null, 3], [3, 3, 1], [3, 3, null]," +
                " [3, 3, 3]]");

        aeq(listsAscending(0, new ArrayList<Integer>()), "[[]]");
        aeq(listsAscending(1, new ArrayList<Integer>()), "[]");
        aeq(listsAscending(2, new ArrayList<Integer>()), "[]");
        aeq(listsAscending(3, new ArrayList<Integer>()), "[]");
        try {
            listsAscending(-1, Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsAscending_BigInteger_Iterable() {
        aeq(listsAscending(BigInteger.ZERO, Arrays.asList(1, 2, 3)), "[[]]");
        aeq(listsAscending(BigInteger.ONE, Arrays.asList(1, 2, 3)), "[[1], [2], [3]]");
        aeq(listsAscending(BigInteger.valueOf(2), Arrays.asList(1, 2, 3)),
                "[[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]");
        aeq(listsAscending(BigInteger.valueOf(3), Arrays.asList(1, 2, 3)),
                "[[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2]," +
                " [1, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1]," +
                " [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3]," +
                " [3, 3, 1], [3, 3, 2], [3, 3, 3]]");

        aeq(listsAscending(0, Arrays.asList(1, null, 3)), "[[]]");
        aeq(listsAscending(1, Arrays.asList(1, null, 3)), "[[1], [null], [3]]");
        aeq(listsAscending(2, Arrays.asList(1, null, 3)),
                "[[1, 1], [1, null], [1, 3], [null, 1], [null, null], [null, 3], [3, 1], [3, null], [3, 3]]");
        aeq(listsAscending(3, Arrays.asList(1, null, 3)),
                "[[1, 1, 1], [1, 1, null], [1, 1, 3], [1, null, 1], [1, null, null], [1, null, 3], [1, 3, 1]," +
                " [1, 3, null], [1, 3, 3], [null, 1, 1], [null, 1, null], [null, 1, 3], [null, null, 1]," +
                " [null, null, null], [null, null, 3], [null, 3, 1], [null, 3, null], [null, 3, 3], [3, 1, 1]," +
                " [3, 1, null], [3, 1, 3], [3, null, 1], [3, null, null], [3, null, 3], [3, 3, 1], [3, 3, null]," +
                " [3, 3, 3]]");

        aeq(listsAscending(BigInteger.ZERO, new ArrayList<Integer>()), "[[]]");
        aeq(listsAscending(BigInteger.ONE, new ArrayList<Integer>()), "[]");
        aeq(listsAscending(BigInteger.valueOf(2), new ArrayList<Integer>()), "[]");
        aeq(listsAscending(BigInteger.valueOf(3), new ArrayList<Integer>()), "[]");
        try {
            listsAscending(BigInteger.valueOf(-1), Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsAscending_int_String() {
        aeq(stringsAscending(0, "abc"), "[]");
        aeq(length(stringsAscending(0, "abc")), 1);
        aeq(stringsAscending(1, "abc"), "[a, b, c]");
        aeq(stringsAscending(2, "abc"), "[aa, ab, ac, ba, bb, bc, ca, cb, cc]");
        aeq(stringsAscending(3, "abc"),
                "[aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, bac, bba," +
                " bbb, bbc, bca, bcb, bcc, caa, cab, cac, cba, cbb, cbc, cca, ccb, ccc]");
        aeq(stringsAscending(0, "a"), "[]");
        aeq(stringsAscending(1, "a"), "[a]");
        aeq(stringsAscending(2, "a"), "[aa]");
        aeq(stringsAscending(3, "a"), "[aaa]");
        aeq(stringsAscending(0, ""), "[]");
        aeq(length(stringsAscending(0, "")), 1);
        aeq(stringsAscending(1, ""), "[]");
        aeq(length(stringsAscending(1, "")), 0);
        aeq(stringsAscending(2, ""), "[]");
        aeq(length(stringsAscending(2, "")), 0);
        aeq(stringsAscending(3, ""), "[]");
        aeq(length(stringsAscending(3, "")), 0);
        try {
            stringsAscending(-1, "");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsAscending_BigInteger_String() {
        aeq(stringsAscending(BigInteger.ZERO, "abc"), "[]");
        aeq(length(stringsAscending(BigInteger.ZERO, "abc")), 1);
        aeq(length(stringsAscending(0, "abc")), 1);
        aeq(stringsAscending(BigInteger.ONE, "abc"), "[a, b, c]");
        aeq(stringsAscending(BigInteger.valueOf(2), "abc"), "[aa, ab, ac, ba, bb, bc, ca, cb, cc]");
        aeq(stringsAscending(BigInteger.valueOf(3), "abc"),
                "[aaa, aab, aac, aba, abb, abc, aca, acb, acc, baa, bab, bac, bba," +
                " bbb, bbc, bca, bcb, bcc, caa, cab, cac, cba, cbb, cbc, cca, ccb, ccc]");
        aeq(stringsAscending(BigInteger.ZERO, "a"), "[]");
        aeq(stringsAscending(BigInteger.ONE, "a"), "[a]");
        aeq(stringsAscending(BigInteger.valueOf(2), "a"), "[aa]");
        aeq(stringsAscending(BigInteger.valueOf(3), "a"), "[aaa]");
        aeq(stringsAscending(BigInteger.ZERO, ""), "[]");
        aeq(length(stringsAscending(BigInteger.ZERO, "")), 1);
        aeq(stringsAscending(BigInteger.ONE, ""), "[]");
        aeq(length(stringsAscending(BigInteger.ONE, "")), 0);
        aeq(stringsAscending(BigInteger.valueOf(2), ""), "[]");
        aeq(length(stringsAscending(BigInteger.valueOf(2), "")), 0);
        aeq(stringsAscending(BigInteger.valueOf(3), ""), "[]");
        aeq(length(stringsAscending(BigInteger.valueOf(3), "")), 0);
        try {
            stringsAscending(BigInteger.valueOf(-1), "");
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testListsShortlex() {
        aeq(take(20, listsShortlex(Arrays.asList(1, 2, 3))),
                "[[], [1], [2], [3], [1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2]," +
                " [3, 3], [1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1]]");
        aeq(listsShortlex(new ArrayList<Integer>()), "[[]]");
    }

    @Test
    public void testStringsShortlex() {
        aeq(take(20, stringsShortlex("abc")),
                "[, a, b, c, aa, ab, ac, ba, bb, bc, ca, cb, cc, aaa, aab, aac, aba, abb, abc, aca]");
        aeq(stringsShortlex(""), "[]");
        aeq(length(stringsShortlex("")), 1);
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable() {
        aeq(pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, 3), (3, 1), (2, 2), (4, 1), (1, 4)," +
                " (3, 2), (2, 3), (4, 2), (3, 3), (2, 4), (4, 3), (3, 4), (4, 4)]");
        aeq(pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (1, null), (null, 1), (2, 2), (4, 1), (1, 4)," +
                " (null, 2), (2, null), (4, 2), (null, null), (2, 4), (4, null), (null, 4), (4, 4)]");
        aeq(pairsLogarithmicOrder(new ArrayList<Integer>()), "[]");
        aeq(take(20, pairsLogarithmicOrder(P.naturalBigIntegers())),
                "[(0, 0), (0, 1), (1, 0), (0, 2), (2, 0), (1, 1), (3, 0), (0, 3), (4, 0), (2, 1)," +
                " (5, 0), (1, 2), (6, 0), (3, 1), (7, 0), (0, 4), (8, 0), (4, 1), (9, 0), (2, 2)]");
        aeq(take(20, pairsLogarithmicOrder((Iterable<BigInteger>) cons(null, P.naturalBigIntegers()))),
                "[(null, null), (null, 0), (0, null), (null, 1), (1, null), (0, 0), (2, null), (null, 2), (3, null)," +
                " (1, 0), (4, null), (0, 1), (5, null), (2, 0), (6, null), (null, 3), (7, null), (3, 0), (8, null)," +
                " (1, 1)]");
    }

    @Test
    public void testPairsLogarithmicOrder_Iterable_Iterable() {
        aeq(pairsLogarithmicOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (3, a), (2, b), (4, a), (1, d)," +
                " (3, b), (2, c), (4, b), (3, c), (2, d), (4, c), (3, d), (4, d)]");
        aeq(pairsLogarithmicOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (1, c), (null, a), (2, b), (4, a), (1, d)," +
                " (null, b), (2, c), (4, b), (null, c), (2, d), (4, c), (null, d), (4, d)]");
        aeq(pairsLogarithmicOrder(new ArrayList<Integer>(), fromString("abcd")), "[]");
        aeq(pairsLogarithmicOrder(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
        aeq(take(20, pairsLogarithmicOrder(P.naturalBigIntegers(), fromString("abcd"))),
                "[(0, a), (0, b), (1, a), (0, c), (2, a), (1, b), (3, a), (0, d), (4, a), (2, b)," +
                " (5, a), (1, c), (6, a), (3, b), (7, a), (8, a), (4, b), (9, a), (2, c), (10, a)]");
        aeq(take(20, pairsLogarithmicOrder(fromString("abcd"), P.naturalBigIntegers())),
                "[(a, 0), (a, 1), (b, 0), (a, 2), (c, 0), (b, 1), (d, 0), (a, 3), (c, 1), (b, 2)," +
                " (d, 1), (a, 4), (c, 2), (b, 3), (d, 2), (a, 5), (c, 3), (b, 4), (d, 3), (a, 6)]");
        aeq(take(20, pairsLogarithmicOrder(P.positiveBigIntegers(), P.negativeBigIntegers())),
                "[(1, -1), (1, -2), (2, -1), (1, -3), (3, -1), (2, -2), (4, -1), (1, -4), (5, -1), (3, -2)," +
                " (6, -1), (2, -3), (7, -1), (4, -2), (8, -1), (1, -5), (9, -1), (5, -2), (10, -1), (3, -3)]");
    }

    @Test
    public void testPairsSquareRootOrder_Iterable() {
        aeq(pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2), (4, 1), (4, 2)," +
                " (1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4), (4, 3), (4, 4)]");
        aeq(pairsSquareRootOrder(Arrays.asList(1, 2, null, 4)),
                "[(1, 1), (1, 2), (2, 1), (2, 2), (null, 1), (null, 2), (4, 1), (4, 2)," +
                " (1, null), (1, 4), (2, null), (2, 4), (null, null), (null, 4), (4, null), (4, 4)]");
        aeq(pairsSquareRootOrder(new ArrayList<Integer>()), "[]");
        aeq(take(20, pairsSquareRootOrder(P.naturalBigIntegers())),
                "[(0, 0), (0, 1), (1, 0), (1, 1), (2, 0), (2, 1), (3, 0), (3, 1), (0, 2), (0, 3)," +
                " (1, 2), (1, 3), (2, 2), (2, 3), (3, 2), (3, 3), (4, 0), (4, 1), (5, 0), (5, 1)]");
        aeq(take(20, pairsSquareRootOrder((Iterable<BigInteger>) cons(null, P.naturalBigIntegers()))),
                "[(null, null), (null, 0), (0, null), (0, 0), (1, null), (1, 0), (2, null), (2, 0), (null, 1)," +
                " (null, 2), (0, 1), (0, 2), (1, 1), (1, 2), (2, 1), (2, 2), (3, null), (3, 0), (4, null), (4, 0)]");
    }

    @Test
    public void testPairsSquareRootOrder_Iterable_Iterable() {
        aeq(pairsSquareRootOrder(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (4, a), (4, b)," +
                " (1, c), (1, d), (2, c), (2, d), (3, c), (3, d), (4, c), (4, d)]");
        aeq(pairsSquareRootOrder(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (null, a), (null, b), (4, a), (4, b)," +
                " (1, c), (1, d), (2, c), (2, d), (null, c), (null, d), (4, c), (4, d)]");
        aeq(pairsSquareRootOrder(new ArrayList<Integer>(), fromString("abcd")), "[]");
        aeq(pairsSquareRootOrder(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
        aeq(take(20, pairsSquareRootOrder(P.naturalBigIntegers(), fromString("abcd"))),
                "[(0, a), (0, b), (1, a), (1, b), (2, a), (2, b), (3, a), (3, b), (0, c), (0, d)," +
                " (1, c), (1, d), (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b)]");
        aeq(take(20, pairsSquareRootOrder(fromString("abcd"), P.naturalBigIntegers())),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (c, 0), (c, 1), (d, 0), (d, 1), (a, 2), (a, 3)," +
                " (b, 2), (b, 3), (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5)]");
        aeq(take(20, pairsSquareRootOrder(P.positiveBigIntegers(), P.negativeBigIntegers())),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (3, -1), (3, -2), (4, -1), (4, -2), (1, -3), (1, -4)," +
                " (2, -3), (2, -4), (3, -3), (3, -4), (4, -3), (4, -4), (5, -1), (5, -2), (6, -1), (6, -2)]");
    }

    @Test
    public void testPairs() {
        aeq(pairs(Arrays.asList(1, 2, 3, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d)," +
                " (3, a), (3, b), (4, a), (4, b), (3, c), (3, d), (4, c), (4, d)]");
        aeq(pairs(Arrays.asList(1, 2, null, 4), fromString("abcd")),
                "[(1, a), (1, b), (2, a), (2, b), (1, c), (1, d), (2, c), (2, d)," +
                " (null, a), (null, b), (4, a), (4, b), (null, c), (null, d), (4, c), (4, d)]");
        aeq(pairs(new ArrayList<Integer>(), fromString("abcd")), "[]");
        aeq(pairs(new ArrayList<Integer>(), new ArrayList<Character>()), "[]");
        aeq(take(20, pairs(P.naturalBigIntegers(), fromString("abcd"))),
                "[(0, a), (0, b), (1, a), (1, b), (0, c), (0, d), (1, c), (1, d), (2, a), (2, b)," +
                " (3, a), (3, b), (2, c), (2, d), (3, c), (3, d), (4, a), (4, b), (5, a), (5, b)]");
        aeq(take(20, pairs(fromString("abcd"), P.naturalBigIntegers())),
                "[(a, 0), (a, 1), (b, 0), (b, 1), (a, 2), (a, 3), (b, 2), (b, 3), (c, 0), (c, 1)," +
                " (d, 0), (d, 1), (c, 2), (c, 3), (d, 2), (d, 3), (a, 4), (a, 5), (b, 4), (b, 5)]");
        aeq(take(20, pairs(P.positiveBigIntegers(), P.negativeBigIntegers())),
                "[(1, -1), (1, -2), (2, -1), (2, -2), (1, -3), (1, -4), (2, -3), (2, -4), (3, -1), (3, -2)," +
                " (4, -1), (4, -2), (3, -3), (3, -4), (4, -3), (4, -4), (1, -5), (1, -6), (2, -5), (2, -6)]");
    }

    @Test
    public void testTriples() {
        aeq(triples(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (2, c, false), (2, c, true)," +
                " (3, a, false), (3, a, true), (3, b, false), (3, b, true), (3, c, false), (3, c, true)]");
        aeq(triples(Arrays.asList(1, 2, null, 4), fromString("abcd"), P.booleans()),
                "[(1, a, false), (1, a, true), (1, b, false), (1, b, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true), (1, c, false), (1, c, true), (1, d, false), (1, d, true)," +
                " (2, c, false), (2, c, true), (2, d, false), (2, d, true), (null, a, false), (null, a, true)," +
                " (null, b, false), (null, b, true), (4, a, false), (4, a, true), (4, b, false), (4, b, true)," +
                " (null, c, false), (null, c, true), (null, d, false), (null, d, true), (4, c, false)," +
                " (4, c, true), (4, d, false), (4, d, true)]");
        aeq(triples(new ArrayList<Integer>(), fromString("abcd"), P.booleans()), "[]");
        aeq(triples(new ArrayList<Integer>(), new ArrayList<Character>(), new ArrayList<Boolean>()), "[]");
        aeq(take(20, triples(P.naturalBigIntegers(), fromString("abcd"), P.booleans())),
                "[(0, a, false), (0, a, true), (0, b, false), (0, b, true), (1, a, false), (1, a, true)," +
                " (1, b, false), (1, b, true), (0, c, false), (0, c, true), (0, d, false), (0, d, true)," +
                " (1, c, false), (1, c, true), (1, d, false), (1, d, true), (2, a, false), (2, a, true)," +
                " (2, b, false), (2, b, true)]");
        aeq(take(20, triples(fromString("abcd"), P.booleans(), P.naturalBigIntegers())),
                "[(a, false, 0), (a, false, 1), (a, true, 0), (a, true, 1), (b, false, 0), (b, false, 1)," +
                " (b, true, 0), (b, true, 1), (a, false, 2), (a, false, 3), (a, true, 2), (a, true, 3)," +
                " (b, false, 2), (b, false, 3), (b, true, 2), (b, true, 3), (c, false, 0), (c, false, 1)," +
                " (c, true, 0), (c, true, 1)]");
        aeq(take(20, triples(P.positiveBigIntegers(), P.negativeBigIntegers(), P.characters())),
                "[(1, -1, a), (1, -1, b), (1, -2, a), (1, -2, b), (2, -1, a), (2, -1, b), (2, -2, a), (2, -2, b)," +
                " (1, -1, c), (1, -1, d), (1, -2, c), (1, -2, d), (2, -1, c), (2, -1, d), (2, -2, c), (2, -2, d)," +
                " (1, -3, a), (1, -3, b), (1, -4, a), (1, -4, b)]");
    }

    @Test
    public void testQuadruples() {
        aeq(quadruples(Arrays.asList(1, 2, 3), fromString("abc"), P.booleans(), P.orderings()),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ)," +
                " (1, b, false, LT), (1, b, true, EQ), (1, b, true, LT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, true, EQ), (2, a, true, LT), (2, b, false, EQ), (2, b, false, LT), (2, b, true, EQ)," +
                " (2, b, true, LT), (1, a, false, GT), (1, a, true, GT), (1, b, false, GT), (1, b, true, GT)," +
                " (2, a, false, GT), (2, a, true, GT), (2, b, false, GT), (2, b, true, GT), (1, c, false, EQ)," +
                " (1, c, false, LT), (1, c, true, EQ), (1, c, true, LT), (2, c, false, EQ), (2, c, false, LT)," +
                " (2, c, true, EQ), (2, c, true, LT), (1, c, false, GT), (1, c, true, GT), (2, c, false, GT)," +
                " (2, c, true, GT), (3, a, false, EQ), (3, a, false, LT), (3, a, true, EQ), (3, a, true, LT)," +
                " (3, b, false, EQ), (3, b, false, LT), (3, b, true, EQ), (3, b, true, LT), (3, a, false, GT)," +
                " (3, a, true, GT), (3, b, false, GT), (3, b, true, GT), (3, c, false, EQ), (3, c, false, LT)," +
                " (3, c, true, EQ), (3, c, true, LT), (3, c, false, GT), (3, c, true, GT)]");
        aeq(quadruples(Arrays.asList(1, 2, null, 4), fromString("abcd"), P.booleans(), P.orderings()),
                "[(1, a, false, EQ), (1, a, false, LT), (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ)," +
                " (1, b, false, LT), (1, b, true, EQ), (1, b, true, LT), (2, a, false, EQ), (2, a, false, LT)," +
                " (2, a, true, EQ), (2, a, true, LT), (2, b, false, EQ), (2, b, false, LT), (2, b, true, EQ)," +
                " (2, b, true, LT), (1, a, false, GT), (1, a, true, GT), (1, b, false, GT), (1, b, true, GT)," +
                " (2, a, false, GT), (2, a, true, GT), (2, b, false, GT), (2, b, true, GT), (1, c, false, EQ)," +
                " (1, c, false, LT), (1, c, true, EQ), (1, c, true, LT), (1, d, false, EQ), (1, d, false, LT)," +
                " (1, d, true, EQ), (1, d, true, LT), (2, c, false, EQ), (2, c, false, LT), (2, c, true, EQ)," +
                " (2, c, true, LT), (2, d, false, EQ), (2, d, false, LT), (2, d, true, EQ), (2, d, true, LT)," +
                " (1, c, false, GT), (1, c, true, GT), (1, d, false, GT), (1, d, true, GT), (2, c, false, GT)," +
                " (2, c, true, GT), (2, d, false, GT), (2, d, true, GT), (null, a, false, EQ), (null, a, false, LT)," +
                " (null, a, true, EQ), (null, a, true, LT), (null, b, false, EQ), (null, b, false, LT)," +
                " (null, b, true, EQ), (null, b, true, LT), (4, a, false, EQ), (4, a, false, LT), (4, a, true, EQ)," +
                " (4, a, true, LT), (4, b, false, EQ), (4, b, false, LT), (4, b, true, EQ), (4, b, true, LT)," +
                " (null, a, false, GT), (null, a, true, GT), (null, b, false, GT), (null, b, true, GT)," +
                " (4, a, false, GT), (4, a, true, GT), (4, b, false, GT), (4, b, true, GT), (null, c, false, EQ)," +
                " (null, c, false, LT), (null, c, true, EQ), (null, c, true, LT), (null, d, false, EQ)," +
                " (null, d, false, LT), (null, d, true, EQ), (null, d, true, LT), (4, c, false, EQ)," +
                " (4, c, false, LT), (4, c, true, EQ), (4, c, true, LT), (4, d, false, EQ), (4, d, false, LT)," +
                " (4, d, true, EQ), (4, d, true, LT), (null, c, false, GT), (null, c, true, GT)," +
                " (null, d, false, GT), (null, d, true, GT), (4, c, false, GT), (4, c, true, GT), (4, d, false, GT)," +
                " (4, d, true, GT)]");
        aeq(quadruples(new ArrayList<Integer>(), fromString("abcd"), P.booleans(), P.orderings()), "[]");
        aeq(quadruples(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>()
        ), "[]");
        aeq(take(20, quadruples(P.naturalBigIntegers(), fromString("abcd"), P.booleans(), P.orderings())),
                "[(0, a, false, EQ), (0, a, false, LT), (0, a, true, EQ), (0, a, true, LT), (0, b, false, EQ)," +
                " (0, b, false, LT), (0, b, true, EQ), (0, b, true, LT), (1, a, false, EQ), (1, a, false, LT)," +
                " (1, a, true, EQ), (1, a, true, LT), (1, b, false, EQ), (1, b, false, LT), (1, b, true, EQ)," +
                " (1, b, true, LT), (0, a, false, GT), (0, a, true, GT), (0, b, false, GT), (0, b, true, GT)]");
        aeq(take(20, quadruples(fromString("abcd"), P.booleans(), P.naturalBigIntegers(), P.orderings())),
                "[(a, false, 0, EQ), (a, false, 0, LT), (a, false, 1, EQ), (a, false, 1, LT), (a, true, 0, EQ)," +
                " (a, true, 0, LT), (a, true, 1, EQ), (a, true, 1, LT), (b, false, 0, EQ), (b, false, 0, LT)," +
                " (b, false, 1, EQ), (b, false, 1, LT), (b, true, 0, EQ), (b, true, 0, LT), (b, true, 1, EQ)," +
                " (b, true, 1, LT), (a, false, 0, GT), (a, false, 1, GT), (a, true, 0, GT), (a, true, 1, GT)]");
        aeq(take(20, quadruples(P.positiveBigIntegers(), P.negativeBigIntegers(), P.characters(), P.strings())),
                "[(1, -1, a, ), (1, -1, a, a), (1, -1, b, ), (1, -1, b, a), (1, -2, a, ), (1, -2, a, a)," +
                " (1, -2, b, ), (1, -2, b, a), (2, -1, a, ), (2, -1, a, a), (2, -1, b, ), (2, -1, b, a)," +
                " (2, -2, a, ), (2, -2, a, a), (2, -2, b, ), (2, -2, b, a), (1, -1, a, aa), (1, -1, a, b)," +
                " (1, -1, b, aa), (1, -1, b, b)]");
    }

    @Test
    public void testQuintuples() {
        aeq(quintuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, true, EQ, yes), (1, a, true, EQ, no), (1, a, true, LT, yes), (1, a, true, LT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, true, EQ, yes), (1, b, true, EQ, no), (1, b, true, LT, yes), (1, b, true, LT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, true, EQ, yes), (2, a, true, EQ, no), (2, a, true, LT, yes), (2, a, true, LT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, true, EQ, yes), (2, b, true, EQ, no), (2, b, true, LT, yes), (2, b, true, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, true, EQ, yes), (1, c, true, EQ, no), (1, c, true, LT, yes), (1, c, true, LT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, true, EQ, yes), (2, c, true, EQ, no), (2, c, true, LT, yes), (2, c, true, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (3, a, false, EQ, yes), (3, a, false, EQ, no), (3, a, false, LT, yes), (3, a, false, LT, no)," +
                " (3, a, true, EQ, yes), (3, a, true, EQ, no), (3, a, true, LT, yes), (3, a, true, LT, no)," +
                " (3, b, false, EQ, yes), (3, b, false, EQ, no), (3, b, false, LT, yes), (3, b, false, LT, no)," +
                " (3, b, true, EQ, yes), (3, b, true, EQ, no), (3, b, true, LT, yes), (3, b, true, LT, no)," +
                " (3, a, false, GT, yes), (3, a, false, GT, no), (3, a, true, GT, yes), (3, a, true, GT, no)," +
                " (3, b, false, GT, yes), (3, b, false, GT, no), (3, b, true, GT, yes), (3, b, true, GT, no)," +
                " (3, c, false, EQ, yes), (3, c, false, EQ, no), (3, c, false, LT, yes), (3, c, false, LT, no)," +
                " (3, c, true, EQ, yes), (3, c, true, EQ, no), (3, c, true, LT, yes), (3, c, true, LT, no)," +
                " (3, c, false, GT, yes), (3, c, false, GT, no), (3, c, true, GT, yes), (3, c, true, GT, no)]");
        aeq(quintuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                ),
                "[(1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)," +
                " (1, a, true, EQ, yes), (1, a, true, EQ, no), (1, a, true, LT, yes), (1, a, true, LT, no)," +
                " (1, b, false, EQ, yes), (1, b, false, EQ, no), (1, b, false, LT, yes), (1, b, false, LT, no)," +
                " (1, b, true, EQ, yes), (1, b, true, EQ, no), (1, b, true, LT, yes), (1, b, true, LT, no)," +
                " (2, a, false, EQ, yes), (2, a, false, EQ, no), (2, a, false, LT, yes), (2, a, false, LT, no)," +
                " (2, a, true, EQ, yes), (2, a, true, EQ, no), (2, a, true, LT, yes), (2, a, true, LT, no)," +
                " (2, b, false, EQ, yes), (2, b, false, EQ, no), (2, b, false, LT, yes), (2, b, false, LT, no)," +
                " (2, b, true, EQ, yes), (2, b, true, EQ, no), (2, b, true, LT, yes), (2, b, true, LT, no)," +
                " (1, a, false, GT, yes), (1, a, false, GT, no), (1, a, true, GT, yes), (1, a, true, GT, no)," +
                " (1, b, false, GT, yes), (1, b, false, GT, no), (1, b, true, GT, yes), (1, b, true, GT, no)," +
                " (2, a, false, GT, yes), (2, a, false, GT, no), (2, a, true, GT, yes), (2, a, true, GT, no)," +
                " (2, b, false, GT, yes), (2, b, false, GT, no), (2, b, true, GT, yes), (2, b, true, GT, no)," +
                " (1, c, false, EQ, yes), (1, c, false, EQ, no), (1, c, false, LT, yes), (1, c, false, LT, no)," +
                " (1, c, true, EQ, yes), (1, c, true, EQ, no), (1, c, true, LT, yes), (1, c, true, LT, no)," +
                " (1, d, false, EQ, yes), (1, d, false, EQ, no), (1, d, false, LT, yes), (1, d, false, LT, no)," +
                " (1, d, true, EQ, yes), (1, d, true, EQ, no), (1, d, true, LT, yes), (1, d, true, LT, no)," +
                " (2, c, false, EQ, yes), (2, c, false, EQ, no), (2, c, false, LT, yes), (2, c, false, LT, no)," +
                " (2, c, true, EQ, yes), (2, c, true, EQ, no), (2, c, true, LT, yes), (2, c, true, LT, no)," +
                " (2, d, false, EQ, yes), (2, d, false, EQ, no), (2, d, false, LT, yes), (2, d, false, LT, no)," +
                " (2, d, true, EQ, yes), (2, d, true, EQ, no), (2, d, true, LT, yes), (2, d, true, LT, no)," +
                " (1, c, false, GT, yes), (1, c, false, GT, no), (1, c, true, GT, yes), (1, c, true, GT, no)," +
                " (1, d, false, GT, yes), (1, d, false, GT, no), (1, d, true, GT, yes), (1, d, true, GT, no)," +
                " (2, c, false, GT, yes), (2, c, false, GT, no), (2, c, true, GT, yes), (2, c, true, GT, no)," +
                " (2, d, false, GT, yes), (2, d, false, GT, no), (2, d, true, GT, yes), (2, d, true, GT, no)," +
                " (null, a, false, EQ, yes), (null, a, false, EQ, no), (null, a, false, LT, yes)," +
                " (null, a, false, LT, no), (null, a, true, EQ, yes), (null, a, true, EQ, no)," +
                " (null, a, true, LT, yes), (null, a, true, LT, no), (null, b, false, EQ, yes)," +
                " (null, b, false, EQ, no), (null, b, false, LT, yes), (null, b, false, LT, no)," +
                " (null, b, true, EQ, yes), (null, b, true, EQ, no), (null, b, true, LT, yes)," +
                " (null, b, true, LT, no), (4, a, false, EQ, yes), (4, a, false, EQ, no), (4, a, false, LT, yes)," +
                " (4, a, false, LT, no), (4, a, true, EQ, yes), (4, a, true, EQ, no), (4, a, true, LT, yes)," +
                " (4, a, true, LT, no), (4, b, false, EQ, yes), (4, b, false, EQ, no), (4, b, false, LT, yes)," +
                " (4, b, false, LT, no), (4, b, true, EQ, yes), (4, b, true, EQ, no), (4, b, true, LT, yes)," +
                " (4, b, true, LT, no), (null, a, false, GT, yes), (null, a, false, GT, no)," +
                " (null, a, true, GT, yes), (null, a, true, GT, no), (null, b, false, GT, yes)," +
                " (null, b, false, GT, no), (null, b, true, GT, yes), (null, b, true, GT, no)," +
                " (4, a, false, GT, yes), (4, a, false, GT, no), (4, a, true, GT, yes), (4, a, true, GT, no)," +
                " (4, b, false, GT, yes), (4, b, false, GT, no), (4, b, true, GT, yes), (4, b, true, GT, no)," +
                " (null, c, false, EQ, yes), (null, c, false, EQ, no), (null, c, false, LT, yes)," +
                " (null, c, false, LT, no), (null, c, true, EQ, yes), (null, c, true, EQ, no)," +
                " (null, c, true, LT, yes), (null, c, true, LT, no), (null, d, false, EQ, yes)," +
                " (null, d, false, EQ, no), (null, d, false, LT, yes), (null, d, false, LT, no)," +
                " (null, d, true, EQ, yes), (null, d, true, EQ, no), (null, d, true, LT, yes)," +
                " (null, d, true, LT, no), (4, c, false, EQ, yes), (4, c, false, EQ, no), (4, c, false, LT, yes)," +
                " (4, c, false, LT, no), (4, c, true, EQ, yes), (4, c, true, EQ, no), (4, c, true, LT, yes)," +
                " (4, c, true, LT, no), (4, d, false, EQ, yes), (4, d, false, EQ, no), (4, d, false, LT, yes)," +
                " (4, d, false, LT, no), (4, d, true, EQ, yes), (4, d, true, EQ, no), (4, d, true, LT, yes)," +
                " (4, d, true, LT, no), (null, c, false, GT, yes), (null, c, false, GT, no)," +
                " (null, c, true, GT, yes), (null, c, true, GT, no), (null, d, false, GT, yes)," +
                " (null, d, false, GT, no), (null, d, true, GT, yes), (null, d, true, GT, no)," +
                " (4, c, false, GT, yes), (4, c, false, GT, no), (4, c, true, GT, yes), (4, c, true, GT, no)," +
                " (4, d, false, GT, yes), (4, d, false, GT, no), (4, d, true, GT, yes), (4, d, true, GT, no)]");
        aeq(quintuples(
                new ArrayList<Integer>(),
                fromString("abcd"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no")
        ), "[]");
        aeq(quintuples(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>()
        ), "[]");
        aeq(take(20, quintuples(
                        P.naturalBigIntegers(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                )),
                "[(0, a, false, EQ, yes), (0, a, false, EQ, no), (0, a, false, LT, yes), (0, a, false, LT, no)," +
                " (0, a, true, EQ, yes), (0, a, true, EQ, no), (0, a, true, LT, yes), (0, a, true, LT, no)," +
                " (0, b, false, EQ, yes), (0, b, false, EQ, no), (0, b, false, LT, yes), (0, b, false, LT, no)," +
                " (0, b, true, EQ, yes), (0, b, true, EQ, no), (0, b, true, LT, yes), (0, b, true, LT, no)," +
                " (1, a, false, EQ, yes), (1, a, false, EQ, no), (1, a, false, LT, yes), (1, a, false, LT, no)]");
        aeq(take(20, quintuples(
                        fromString("abcd"),
                        P.booleans(),
                        P.naturalBigIntegers(),
                        P.orderings(),
                        Arrays.asList("yes", "no")
                )),
                "[(a, false, 0, EQ, yes), (a, false, 0, EQ, no), (a, false, 0, LT, yes), (a, false, 0, LT, no)," +
                " (a, false, 1, EQ, yes), (a, false, 1, EQ, no), (a, false, 1, LT, yes), (a, false, 1, LT, no)," +
                " (a, true, 0, EQ, yes), (a, true, 0, EQ, no), (a, true, 0, LT, yes), (a, true, 0, LT, no)," +
                " (a, true, 1, EQ, yes), (a, true, 1, EQ, no), (a, true, 1, LT, yes), (a, true, 1, LT, no)," +
                " (b, false, 0, EQ, yes), (b, false, 0, EQ, no), (b, false, 0, LT, yes), (b, false, 0, LT, no)]");
        aeq(take(20, quintuples(
                        P.positiveBigIntegers(),
                        P.negativeBigIntegers(),
                        P.characters(),
                        P.strings(),
                        P.floats()
                )),
                "[(1, -1, a, , NaN), (1, -1, a, , Infinity), (1, -1, a, a, NaN), (1, -1, a, a, Infinity)," +
                " (1, -1, b, , NaN), (1, -1, b, , Infinity), (1, -1, b, a, NaN), (1, -1, b, a, Infinity)," +
                " (1, -2, a, , NaN), (1, -2, a, , Infinity), (1, -2, a, a, NaN), (1, -2, a, a, Infinity)," +
                " (1, -2, b, , NaN), (1, -2, b, , Infinity), (1, -2, b, a, NaN), (1, -2, b, a, Infinity)," +
                " (2, -1, a, , NaN), (2, -1, a, , Infinity), (2, -1, a, a, NaN), (2, -1, a, a, Infinity)]");
    }

    @Test
    public void testSextuples() {
        aeq(sextuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, true, EQ, yes, Infinity)," +
                " (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity), (1, a, true, EQ, no, NaN)," +
                " (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN), (1, a, true, LT, no, Infinity)," +
                " (1, a, true, LT, no, NaN), (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN)," +
                " (1, b, false, EQ, no, Infinity), (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity)," +
                " (1, b, false, LT, yes, NaN), (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (2, a, false, EQ, yes, Infinity)," +
                " (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity), (2, a, false, EQ, no, NaN)," +
                " (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN), (2, a, false, LT, no, Infinity)," +
                " (2, a, false, LT, no, NaN), (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN)," +
                " (2, a, true, EQ, no, Infinity), (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity)," +
                " (2, a, true, LT, yes, NaN), (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, true, EQ, yes, Infinity)," +
                " (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity), (2, b, true, EQ, no, NaN)," +
                " (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN), (2, b, true, LT, no, Infinity)," +
                " (2, b, true, LT, no, NaN), (1, a, false, GT, yes, Infinity), (1, a, false, GT, yes, NaN)," +
                " (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, GT, yes, Infinity), (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity)," +
                " (1, b, false, GT, no, NaN), (1, b, true, GT, yes, Infinity), (1, b, true, GT, yes, NaN)," +
                " (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, GT, yes, Infinity), (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity)," +
                " (2, a, true, GT, no, NaN), (2, b, false, GT, yes, Infinity), (2, b, false, GT, yes, NaN)," +
                " (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, true, EQ, yes, Infinity)," +
                " (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity), (1, c, true, EQ, no, NaN)," +
                " (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN), (1, c, true, LT, no, Infinity)," +
                " (1, c, true, LT, no, NaN), (2, c, false, EQ, yes, Infinity), (2, c, false, EQ, yes, NaN)," +
                " (2, c, false, EQ, no, Infinity), (2, c, false, EQ, no, NaN), (2, c, false, LT, yes, Infinity)," +
                " (2, c, false, LT, yes, NaN), (2, c, false, LT, no, Infinity), (2, c, false, LT, no, NaN)," +
                " (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN), (2, c, true, EQ, no, Infinity)," +
                " (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity), (2, c, true, LT, yes, NaN)," +
                " (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN), (1, c, false, GT, yes, Infinity)," +
                " (1, c, false, GT, yes, NaN), (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN)," +
                " (1, c, true, GT, yes, Infinity), (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity)," +
                " (1, c, true, GT, no, NaN), (2, c, false, GT, yes, Infinity), (2, c, false, GT, yes, NaN)," +
                " (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN), (2, c, true, GT, yes, Infinity)," +
                " (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity), (2, c, true, GT, no, NaN)," +
                " (3, a, false, EQ, yes, Infinity), (3, a, false, EQ, yes, NaN), (3, a, false, EQ, no, Infinity)," +
                " (3, a, false, EQ, no, NaN), (3, a, false, LT, yes, Infinity), (3, a, false, LT, yes, NaN)," +
                " (3, a, false, LT, no, Infinity), (3, a, false, LT, no, NaN), (3, a, true, EQ, yes, Infinity)," +
                " (3, a, true, EQ, yes, NaN), (3, a, true, EQ, no, Infinity), (3, a, true, EQ, no, NaN)," +
                " (3, a, true, LT, yes, Infinity), (3, a, true, LT, yes, NaN), (3, a, true, LT, no, Infinity)," +
                " (3, a, true, LT, no, NaN), (3, b, false, EQ, yes, Infinity), (3, b, false, EQ, yes, NaN)," +
                " (3, b, false, EQ, no, Infinity), (3, b, false, EQ, no, NaN), (3, b, false, LT, yes, Infinity)," +
                " (3, b, false, LT, yes, NaN), (3, b, false, LT, no, Infinity), (3, b, false, LT, no, NaN)," +
                " (3, b, true, EQ, yes, Infinity), (3, b, true, EQ, yes, NaN), (3, b, true, EQ, no, Infinity)," +
                " (3, b, true, EQ, no, NaN), (3, b, true, LT, yes, Infinity), (3, b, true, LT, yes, NaN)," +
                " (3, b, true, LT, no, Infinity), (3, b, true, LT, no, NaN), (3, a, false, GT, yes, Infinity)," +
                " (3, a, false, GT, yes, NaN), (3, a, false, GT, no, Infinity), (3, a, false, GT, no, NaN)," +
                " (3, a, true, GT, yes, Infinity), (3, a, true, GT, yes, NaN), (3, a, true, GT, no, Infinity)," +
                " (3, a, true, GT, no, NaN), (3, b, false, GT, yes, Infinity), (3, b, false, GT, yes, NaN)," +
                " (3, b, false, GT, no, Infinity), (3, b, false, GT, no, NaN), (3, b, true, GT, yes, Infinity)," +
                " (3, b, true, GT, yes, NaN), (3, b, true, GT, no, Infinity), (3, b, true, GT, no, NaN)," +
                " (3, c, false, EQ, yes, Infinity), (3, c, false, EQ, yes, NaN), (3, c, false, EQ, no, Infinity)," +
                " (3, c, false, EQ, no, NaN), (3, c, false, LT, yes, Infinity), (3, c, false, LT, yes, NaN)," +
                " (3, c, false, LT, no, Infinity), (3, c, false, LT, no, NaN), (3, c, true, EQ, yes, Infinity)," +
                " (3, c, true, EQ, yes, NaN), (3, c, true, EQ, no, Infinity), (3, c, true, EQ, no, NaN)," +
                " (3, c, true, LT, yes, Infinity), (3, c, true, LT, yes, NaN), (3, c, true, LT, no, Infinity)," +
                " (3, c, true, LT, no, NaN), (3, c, false, GT, yes, Infinity), (3, c, false, GT, yes, NaN)," +
                " (3, c, false, GT, no, Infinity), (3, c, false, GT, no, NaN), (3, c, true, GT, yes, Infinity)," +
                " (3, c, true, GT, yes, NaN), (3, c, true, GT, no, Infinity), (3, c, true, GT, no, NaN)]");
        aeq(sextuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                ),
                "[(1, a, false, EQ, yes, Infinity), (1, a, false, EQ, yes, NaN), (1, a, false, EQ, no, Infinity)," +
                " (1, a, false, EQ, no, NaN), (1, a, false, LT, yes, Infinity), (1, a, false, LT, yes, NaN)," +
                " (1, a, false, LT, no, Infinity), (1, a, false, LT, no, NaN), (1, a, true, EQ, yes, Infinity)," +
                " (1, a, true, EQ, yes, NaN), (1, a, true, EQ, no, Infinity), (1, a, true, EQ, no, NaN)," +
                " (1, a, true, LT, yes, Infinity), (1, a, true, LT, yes, NaN), (1, a, true, LT, no, Infinity)," +
                " (1, a, true, LT, no, NaN), (1, b, false, EQ, yes, Infinity), (1, b, false, EQ, yes, NaN)," +
                " (1, b, false, EQ, no, Infinity), (1, b, false, EQ, no, NaN), (1, b, false, LT, yes, Infinity)," +
                " (1, b, false, LT, yes, NaN), (1, b, false, LT, no, Infinity), (1, b, false, LT, no, NaN)," +
                " (1, b, true, EQ, yes, Infinity), (1, b, true, EQ, yes, NaN), (1, b, true, EQ, no, Infinity)," +
                " (1, b, true, EQ, no, NaN), (1, b, true, LT, yes, Infinity), (1, b, true, LT, yes, NaN)," +
                " (1, b, true, LT, no, Infinity), (1, b, true, LT, no, NaN), (2, a, false, EQ, yes, Infinity)," +
                " (2, a, false, EQ, yes, NaN), (2, a, false, EQ, no, Infinity), (2, a, false, EQ, no, NaN)," +
                " (2, a, false, LT, yes, Infinity), (2, a, false, LT, yes, NaN), (2, a, false, LT, no, Infinity)," +
                " (2, a, false, LT, no, NaN), (2, a, true, EQ, yes, Infinity), (2, a, true, EQ, yes, NaN)," +
                " (2, a, true, EQ, no, Infinity), (2, a, true, EQ, no, NaN), (2, a, true, LT, yes, Infinity)," +
                " (2, a, true, LT, yes, NaN), (2, a, true, LT, no, Infinity), (2, a, true, LT, no, NaN)," +
                " (2, b, false, EQ, yes, Infinity), (2, b, false, EQ, yes, NaN), (2, b, false, EQ, no, Infinity)," +
                " (2, b, false, EQ, no, NaN), (2, b, false, LT, yes, Infinity), (2, b, false, LT, yes, NaN)," +
                " (2, b, false, LT, no, Infinity), (2, b, false, LT, no, NaN), (2, b, true, EQ, yes, Infinity)," +
                " (2, b, true, EQ, yes, NaN), (2, b, true, EQ, no, Infinity), (2, b, true, EQ, no, NaN)," +
                " (2, b, true, LT, yes, Infinity), (2, b, true, LT, yes, NaN), (2, b, true, LT, no, Infinity)," +
                " (2, b, true, LT, no, NaN), (1, a, false, GT, yes, Infinity), (1, a, false, GT, yes, NaN)," +
                " (1, a, false, GT, no, Infinity), (1, a, false, GT, no, NaN), (1, a, true, GT, yes, Infinity)," +
                " (1, a, true, GT, yes, NaN), (1, a, true, GT, no, Infinity), (1, a, true, GT, no, NaN)," +
                " (1, b, false, GT, yes, Infinity), (1, b, false, GT, yes, NaN), (1, b, false, GT, no, Infinity)," +
                " (1, b, false, GT, no, NaN), (1, b, true, GT, yes, Infinity), (1, b, true, GT, yes, NaN)," +
                " (1, b, true, GT, no, Infinity), (1, b, true, GT, no, NaN), (2, a, false, GT, yes, Infinity)," +
                " (2, a, false, GT, yes, NaN), (2, a, false, GT, no, Infinity), (2, a, false, GT, no, NaN)," +
                " (2, a, true, GT, yes, Infinity), (2, a, true, GT, yes, NaN), (2, a, true, GT, no, Infinity)," +
                " (2, a, true, GT, no, NaN), (2, b, false, GT, yes, Infinity), (2, b, false, GT, yes, NaN)," +
                " (2, b, false, GT, no, Infinity), (2, b, false, GT, no, NaN), (2, b, true, GT, yes, Infinity)," +
                " (2, b, true, GT, yes, NaN), (2, b, true, GT, no, Infinity), (2, b, true, GT, no, NaN)," +
                " (1, c, false, EQ, yes, Infinity), (1, c, false, EQ, yes, NaN), (1, c, false, EQ, no, Infinity)," +
                " (1, c, false, EQ, no, NaN), (1, c, false, LT, yes, Infinity), (1, c, false, LT, yes, NaN)," +
                " (1, c, false, LT, no, Infinity), (1, c, false, LT, no, NaN), (1, c, true, EQ, yes, Infinity)," +
                " (1, c, true, EQ, yes, NaN), (1, c, true, EQ, no, Infinity), (1, c, true, EQ, no, NaN)," +
                " (1, c, true, LT, yes, Infinity), (1, c, true, LT, yes, NaN), (1, c, true, LT, no, Infinity)," +
                " (1, c, true, LT, no, NaN), (1, d, false, EQ, yes, Infinity), (1, d, false, EQ, yes, NaN)," +
                " (1, d, false, EQ, no, Infinity), (1, d, false, EQ, no, NaN), (1, d, false, LT, yes, Infinity)," +
                " (1, d, false, LT, yes, NaN), (1, d, false, LT, no, Infinity), (1, d, false, LT, no, NaN)," +
                " (1, d, true, EQ, yes, Infinity), (1, d, true, EQ, yes, NaN), (1, d, true, EQ, no, Infinity)," +
                " (1, d, true, EQ, no, NaN), (1, d, true, LT, yes, Infinity), (1, d, true, LT, yes, NaN)," +
                " (1, d, true, LT, no, Infinity), (1, d, true, LT, no, NaN), (2, c, false, EQ, yes, Infinity)," +
                " (2, c, false, EQ, yes, NaN), (2, c, false, EQ, no, Infinity), (2, c, false, EQ, no, NaN)," +
                " (2, c, false, LT, yes, Infinity), (2, c, false, LT, yes, NaN), (2, c, false, LT, no, Infinity)," +
                " (2, c, false, LT, no, NaN), (2, c, true, EQ, yes, Infinity), (2, c, true, EQ, yes, NaN)," +
                " (2, c, true, EQ, no, Infinity), (2, c, true, EQ, no, NaN), (2, c, true, LT, yes, Infinity)," +
                " (2, c, true, LT, yes, NaN), (2, c, true, LT, no, Infinity), (2, c, true, LT, no, NaN)," +
                " (2, d, false, EQ, yes, Infinity), (2, d, false, EQ, yes, NaN), (2, d, false, EQ, no, Infinity)," +
                " (2, d, false, EQ, no, NaN), (2, d, false, LT, yes, Infinity), (2, d, false, LT, yes, NaN)," +
                " (2, d, false, LT, no, Infinity), (2, d, false, LT, no, NaN), (2, d, true, EQ, yes, Infinity)," +
                " (2, d, true, EQ, yes, NaN), (2, d, true, EQ, no, Infinity), (2, d, true, EQ, no, NaN)," +
                " (2, d, true, LT, yes, Infinity), (2, d, true, LT, yes, NaN), (2, d, true, LT, no, Infinity)," +
                " (2, d, true, LT, no, NaN), (1, c, false, GT, yes, Infinity), (1, c, false, GT, yes, NaN)," +
                " (1, c, false, GT, no, Infinity), (1, c, false, GT, no, NaN), (1, c, true, GT, yes, Infinity)," +
                " (1, c, true, GT, yes, NaN), (1, c, true, GT, no, Infinity), (1, c, true, GT, no, NaN)," +
                " (1, d, false, GT, yes, Infinity), (1, d, false, GT, yes, NaN), (1, d, false, GT, no, Infinity)," +
                " (1, d, false, GT, no, NaN), (1, d, true, GT, yes, Infinity), (1, d, true, GT, yes, NaN)," +
                " (1, d, true, GT, no, Infinity), (1, d, true, GT, no, NaN), (2, c, false, GT, yes, Infinity)," +
                " (2, c, false, GT, yes, NaN), (2, c, false, GT, no, Infinity), (2, c, false, GT, no, NaN)," +
                " (2, c, true, GT, yes, Infinity), (2, c, true, GT, yes, NaN), (2, c, true, GT, no, Infinity)," +
                " (2, c, true, GT, no, NaN), (2, d, false, GT, yes, Infinity), (2, d, false, GT, yes, NaN)," +
                " (2, d, false, GT, no, Infinity), (2, d, false, GT, no, NaN), (2, d, true, GT, yes, Infinity)," +
                " (2, d, true, GT, yes, NaN), (2, d, true, GT, no, Infinity), (2, d, true, GT, no, NaN)," +
                " (null, a, false, EQ, yes, Infinity), (null, a, false, EQ, yes, NaN)," +
                " (null, a, false, EQ, no, Infinity), (null, a, false, EQ, no, NaN)," +
                " (null, a, false, LT, yes, Infinity), (null, a, false, LT, yes, NaN)," +
                " (null, a, false, LT, no, Infinity), (null, a, false, LT, no, NaN)," +
                " (null, a, true, EQ, yes, Infinity), (null, a, true, EQ, yes, NaN)," +
                " (null, a, true, EQ, no, Infinity), (null, a, true, EQ, no, NaN)," +
                " (null, a, true, LT, yes, Infinity), (null, a, true, LT, yes, NaN)," +
                " (null, a, true, LT, no, Infinity), (null, a, true, LT, no, NaN)," +
                " (null, b, false, EQ, yes, Infinity), (null, b, false, EQ, yes, NaN)," +
                " (null, b, false, EQ, no, Infinity), (null, b, false, EQ, no, NaN)," +
                " (null, b, false, LT, yes, Infinity), (null, b, false, LT, yes, NaN)," +
                " (null, b, false, LT, no, Infinity), (null, b, false, LT, no, NaN)," +
                " (null, b, true, EQ, yes, Infinity), (null, b, true, EQ, yes, NaN)," +
                " (null, b, true, EQ, no, Infinity), (null, b, true, EQ, no, NaN)," +
                " (null, b, true, LT, yes, Infinity), (null, b, true, LT, yes, NaN)," +
                " (null, b, true, LT, no, Infinity), (null, b, true, LT, no, NaN), (4, a, false, EQ, yes, Infinity)," +
                " (4, a, false, EQ, yes, NaN), (4, a, false, EQ, no, Infinity), (4, a, false, EQ, no, NaN)," +
                " (4, a, false, LT, yes, Infinity), (4, a, false, LT, yes, NaN), (4, a, false, LT, no, Infinity)," +
                " (4, a, false, LT, no, NaN), (4, a, true, EQ, yes, Infinity), (4, a, true, EQ, yes, NaN)," +
                " (4, a, true, EQ, no, Infinity), (4, a, true, EQ, no, NaN), (4, a, true, LT, yes, Infinity)," +
                " (4, a, true, LT, yes, NaN), (4, a, true, LT, no, Infinity), (4, a, true, LT, no, NaN)," +
                " (4, b, false, EQ, yes, Infinity), (4, b, false, EQ, yes, NaN), (4, b, false, EQ, no, Infinity)," +
                " (4, b, false, EQ, no, NaN), (4, b, false, LT, yes, Infinity), (4, b, false, LT, yes, NaN)," +
                " (4, b, false, LT, no, Infinity), (4, b, false, LT, no, NaN), (4, b, true, EQ, yes, Infinity)," +
                " (4, b, true, EQ, yes, NaN), (4, b, true, EQ, no, Infinity), (4, b, true, EQ, no, NaN)," +
                " (4, b, true, LT, yes, Infinity), (4, b, true, LT, yes, NaN), (4, b, true, LT, no, Infinity)," +
                " (4, b, true, LT, no, NaN), (null, a, false, GT, yes, Infinity), (null, a, false, GT, yes, NaN)," +
                " (null, a, false, GT, no, Infinity), (null, a, false, GT, no, NaN)," +
                " (null, a, true, GT, yes, Infinity), (null, a, true, GT, yes, NaN)," +
                " (null, a, true, GT, no, Infinity), (null, a, true, GT, no, NaN)," +
                " (null, b, false, GT, yes, Infinity), (null, b, false, GT, yes, NaN)," +
                " (null, b, false, GT, no, Infinity), (null, b, false, GT, no, NaN)," +
                " (null, b, true, GT, yes, Infinity), (null, b, true, GT, yes, NaN)," +
                " (null, b, true, GT, no, Infinity), (null, b, true, GT, no, NaN), (4, a, false, GT, yes, Infinity)," +
                " (4, a, false, GT, yes, NaN), (4, a, false, GT, no, Infinity), (4, a, false, GT, no, NaN)," +
                " (4, a, true, GT, yes, Infinity), (4, a, true, GT, yes, NaN), (4, a, true, GT, no, Infinity)," +
                " (4, a, true, GT, no, NaN), (4, b, false, GT, yes, Infinity), (4, b, false, GT, yes, NaN)," +
                " (4, b, false, GT, no, Infinity), (4, b, false, GT, no, NaN), (4, b, true, GT, yes, Infinity)," +
                " (4, b, true, GT, yes, NaN), (4, b, true, GT, no, Infinity), (4, b, true, GT, no, NaN)," +
                " (null, c, false, EQ, yes, Infinity), (null, c, false, EQ, yes, NaN)," +
                " (null, c, false, EQ, no, Infinity), (null, c, false, EQ, no, NaN)," +
                " (null, c, false, LT, yes, Infinity), (null, c, false, LT, yes, NaN)," +
                " (null, c, false, LT, no, Infinity), (null, c, false, LT, no, NaN)," +
                " (null, c, true, EQ, yes, Infinity), (null, c, true, EQ, yes, NaN)," +
                " (null, c, true, EQ, no, Infinity), (null, c, true, EQ, no, NaN)," +
                " (null, c, true, LT, yes, Infinity), (null, c, true, LT, yes, NaN)," +
                " (null, c, true, LT, no, Infinity), (null, c, true, LT, no, NaN)," +
                " (null, d, false, EQ, yes, Infinity), (null, d, false, EQ, yes, NaN)," +
                " (null, d, false, EQ, no, Infinity), (null, d, false, EQ, no, NaN)," +
                " (null, d, false, LT, yes, Infinity), (null, d, false, LT, yes, NaN)," +
                " (null, d, false, LT, no, Infinity), (null, d, false, LT, no, NaN)," +
                " (null, d, true, EQ, yes, Infinity), (null, d, true, EQ, yes, NaN)," +
                " (null, d, true, EQ, no, Infinity), (null, d, true, EQ, no, NaN)," +
                " (null, d, true, LT, yes, Infinity), (null, d, true, LT, yes, NaN)," +
                " (null, d, true, LT, no, Infinity), (null, d, true, LT, no, NaN), (4, c, false, EQ, yes, Infinity)," +
                " (4, c, false, EQ, yes, NaN), (4, c, false, EQ, no, Infinity), (4, c, false, EQ, no, NaN)," +
                " (4, c, false, LT, yes, Infinity), (4, c, false, LT, yes, NaN), (4, c, false, LT, no, Infinity)," +
                " (4, c, false, LT, no, NaN), (4, c, true, EQ, yes, Infinity), (4, c, true, EQ, yes, NaN)," +
                " (4, c, true, EQ, no, Infinity), (4, c, true, EQ, no, NaN), (4, c, true, LT, yes, Infinity)," +
                " (4, c, true, LT, yes, NaN), (4, c, true, LT, no, Infinity), (4, c, true, LT, no, NaN)," +
                " (4, d, false, EQ, yes, Infinity), (4, d, false, EQ, yes, NaN), (4, d, false, EQ, no, Infinity)," +
                " (4, d, false, EQ, no, NaN), (4, d, false, LT, yes, Infinity), (4, d, false, LT, yes, NaN)," +
                " (4, d, false, LT, no, Infinity), (4, d, false, LT, no, NaN), (4, d, true, EQ, yes, Infinity)," +
                " (4, d, true, EQ, yes, NaN), (4, d, true, EQ, no, Infinity), (4, d, true, EQ, no, NaN)," +
                " (4, d, true, LT, yes, Infinity), (4, d, true, LT, yes, NaN), (4, d, true, LT, no, Infinity)," +
                " (4, d, true, LT, no, NaN), (null, c, false, GT, yes, Infinity), (null, c, false, GT, yes, NaN)," +
                " (null, c, false, GT, no, Infinity), (null, c, false, GT, no, NaN)," +
                " (null, c, true, GT, yes, Infinity), (null, c, true, GT, yes, NaN)," +
                " (null, c, true, GT, no, Infinity), (null, c, true, GT, no, NaN)," +
                " (null, d, false, GT, yes, Infinity), (null, d, false, GT, yes, NaN)," +
                " (null, d, false, GT, no, Infinity), (null, d, false, GT, no, NaN)," +
                " (null, d, true, GT, yes, Infinity), (null, d, true, GT, yes, NaN)," +
                " (null, d, true, GT, no, Infinity), (null, d, true, GT, no, NaN), (4, c, false, GT, yes, Infinity)," +
                " (4, c, false, GT, yes, NaN), (4, c, false, GT, no, Infinity), (4, c, false, GT, no, NaN)," +
                " (4, c, true, GT, yes, Infinity), (4, c, true, GT, yes, NaN), (4, c, true, GT, no, Infinity)," +
                " (4, c, true, GT, no, NaN), (4, d, false, GT, yes, Infinity), (4, d, false, GT, yes, NaN)," +
                " (4, d, false, GT, no, Infinity), (4, d, false, GT, no, NaN), (4, d, true, GT, yes, Infinity)," +
                " (4, d, true, GT, yes, NaN), (4, d, true, GT, no, Infinity), (4, d, true, GT, no, NaN)]");
        aeq(sextuples(
                new ArrayList<Integer>(),
                fromString("abcd"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
        ), "[]");
        aeq(sextuples(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>(),
                new ArrayList<Float>()
        ), "[]");
        aeq(take(20, sextuples(
                        P.naturalBigIntegers(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        (List<Float>) Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                )),
                "[(0, a, false, EQ, yes, Infinity), (0, a, false, EQ, yes, NaN), (0, a, false, EQ, no, Infinity)," +
                " (0, a, false, EQ, no, NaN), (0, a, false, LT, yes, Infinity), (0, a, false, LT, yes, NaN)," +
                " (0, a, false, LT, no, Infinity), (0, a, false, LT, no, NaN), (0, a, true, EQ, yes, Infinity)," +
                " (0, a, true, EQ, yes, NaN), (0, a, true, EQ, no, Infinity), (0, a, true, EQ, no, NaN)," +
                " (0, a, true, LT, yes, Infinity), (0, a, true, LT, yes, NaN), (0, a, true, LT, no, Infinity)," +
                " (0, a, true, LT, no, NaN), (0, b, false, EQ, yes, Infinity), (0, b, false, EQ, yes, NaN)," +
                " (0, b, false, EQ, no, Infinity), (0, b, false, EQ, no, NaN)]");
        aeq(take(20, sextuples(
                        fromString("abcd"),
                        P.booleans(),
                        P.naturalBigIntegers(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN)
                )),
                "[(a, false, 0, EQ, yes, Infinity), (a, false, 0, EQ, yes, NaN), (a, false, 0, EQ, no, Infinity)," +
                " (a, false, 0, EQ, no, NaN), (a, false, 0, LT, yes, Infinity), (a, false, 0, LT, yes, NaN)," +
                " (a, false, 0, LT, no, Infinity), (a, false, 0, LT, no, NaN), (a, false, 1, EQ, yes, Infinity)," +
                " (a, false, 1, EQ, yes, NaN), (a, false, 1, EQ, no, Infinity), (a, false, 1, EQ, no, NaN)," +
                " (a, false, 1, LT, yes, Infinity), (a, false, 1, LT, yes, NaN), (a, false, 1, LT, no, Infinity)," +
                " (a, false, 1, LT, no, NaN), (a, true, 0, EQ, yes, Infinity), (a, true, 0, EQ, yes, NaN)," +
                " (a, true, 0, EQ, no, Infinity), (a, true, 0, EQ, no, NaN)]");
        aeq(take(20, sextuples(
                        P.positiveBigIntegers(),
                        P.negativeBigIntegers(),
                        P.characters(),
                        P.strings(),
                        P.floats(),
                        P.lists(P.integers())
                )),
                "[(1, -1, a, , NaN, []), (1, -1, a, , NaN, [0]), (1, -1, a, , Infinity, [])," +
                " (1, -1, a, , Infinity, [0]), (1, -1, a, a, NaN, []), (1, -1, a, a, NaN, [0])," +
                " (1, -1, a, a, Infinity, []), (1, -1, a, a, Infinity, [0]), (1, -1, b, , NaN, [])," +
                " (1, -1, b, , NaN, [0]), (1, -1, b, , Infinity, []), (1, -1, b, , Infinity, [0])," +
                " (1, -1, b, a, NaN, []), (1, -1, b, a, NaN, [0]), (1, -1, b, a, Infinity, [])," +
                " (1, -1, b, a, Infinity, [0]), (1, -2, a, , NaN, []), (1, -2, a, , NaN, [0])," +
                " (1, -2, a, , Infinity, []), (1, -2, a, , Infinity, [0])]");
    }

    @Test
    public void testSeptuples() {
        List<Integer> x = Arrays.asList(1, 0);
        List<Integer> y = Arrays.asList(0, 1);
        aeq(septuples(
                        Arrays.asList(1, 2, 3),
                        fromString("abc"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1])," +
                " (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1])," +
                " (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1])," +
                " (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1])," +
                " (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1])," +
                " (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1])," +
                " (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1])," +
                " (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1])," +
                " (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1])," +
                " (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1])," +
                " (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1])," +
                " (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1])," +
                " (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1])," +
                " (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1])," +
                " (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1])," +
                " (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1])," +
                " (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1])," +
                " (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1])," +
                " (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1])," +
                " (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1])," +
                " (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1])," +
                " (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1])," +
                " (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1])," +
                " (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1])," +
                " (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1])," +
                " (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1])," +
                " (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1])," +
                " (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1])," +
                " (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1])," +
                " (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1])," +
                " (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1])," +
                " (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1])," +
                " (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1])," +
                " (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1])," +
                " (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1])," +
                " (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1])," +
                " (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1])," +
                " (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1])," +
                " (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1])," +
                " (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1])," +
                " (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1])," +
                " (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1])," +
                " (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1])," +
                " (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1])," +
                " (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1])," +
                " (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1])," +
                " (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1])," +
                " (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1])," +
                " (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1])," +
                " (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1])," +
                " (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1])," +
                " (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1])," +
                " (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1])," +
                " (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1])," +
                " (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1])," +
                " (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1])," +
                " (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1])," +
                " (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1])," +
                " (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1])," +
                " (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1])," +
                " (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1])," +
                " (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1])," +
                " (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1])," +
                " (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1])," +
                " (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1])," +
                " (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1])," +
                " (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1])," +
                " (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1])," +
                " (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1])," +
                " (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1])," +
                " (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1])," +
                " (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1])," +
                " (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1])," +
                " (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1])," +
                " (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1])," +
                " (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1])," +
                " (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1])," +
                " (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1])," +
                " (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1])," +
                " (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1])," +
                " (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1])," +
                " (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1])," +
                " (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1])," +
                " (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1])," +
                " (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1])," +
                " (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1])," +
                " (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1])," +
                " (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1])," +
                " (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1])," +
                " (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1])," +
                " (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1])," +
                " (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1])," +
                " (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1])," +
                " (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1])," +
                " (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1])," +
                " (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1])," +
                " (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1])," +
                " (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1])," +
                " (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1])," +
                " (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1])," +
                " (3, a, false, EQ, yes, Infinity, [1, 0]), (3, a, false, EQ, yes, Infinity, [0, 1])," +
                " (3, a, false, EQ, yes, NaN, [1, 0]), (3, a, false, EQ, yes, NaN, [0, 1])," +
                " (3, a, false, EQ, no, Infinity, [1, 0]), (3, a, false, EQ, no, Infinity, [0, 1])," +
                " (3, a, false, EQ, no, NaN, [1, 0]), (3, a, false, EQ, no, NaN, [0, 1])," +
                " (3, a, false, LT, yes, Infinity, [1, 0]), (3, a, false, LT, yes, Infinity, [0, 1])," +
                " (3, a, false, LT, yes, NaN, [1, 0]), (3, a, false, LT, yes, NaN, [0, 1])," +
                " (3, a, false, LT, no, Infinity, [1, 0]), (3, a, false, LT, no, Infinity, [0, 1])," +
                " (3, a, false, LT, no, NaN, [1, 0]), (3, a, false, LT, no, NaN, [0, 1])," +
                " (3, a, true, EQ, yes, Infinity, [1, 0]), (3, a, true, EQ, yes, Infinity, [0, 1])," +
                " (3, a, true, EQ, yes, NaN, [1, 0]), (3, a, true, EQ, yes, NaN, [0, 1])," +
                " (3, a, true, EQ, no, Infinity, [1, 0]), (3, a, true, EQ, no, Infinity, [0, 1])," +
                " (3, a, true, EQ, no, NaN, [1, 0]), (3, a, true, EQ, no, NaN, [0, 1])," +
                " (3, a, true, LT, yes, Infinity, [1, 0]), (3, a, true, LT, yes, Infinity, [0, 1])," +
                " (3, a, true, LT, yes, NaN, [1, 0]), (3, a, true, LT, yes, NaN, [0, 1])," +
                " (3, a, true, LT, no, Infinity, [1, 0]), (3, a, true, LT, no, Infinity, [0, 1])," +
                " (3, a, true, LT, no, NaN, [1, 0]), (3, a, true, LT, no, NaN, [0, 1])," +
                " (3, b, false, EQ, yes, Infinity, [1, 0]), (3, b, false, EQ, yes, Infinity, [0, 1])," +
                " (3, b, false, EQ, yes, NaN, [1, 0]), (3, b, false, EQ, yes, NaN, [0, 1])," +
                " (3, b, false, EQ, no, Infinity, [1, 0]), (3, b, false, EQ, no, Infinity, [0, 1])," +
                " (3, b, false, EQ, no, NaN, [1, 0]), (3, b, false, EQ, no, NaN, [0, 1])," +
                " (3, b, false, LT, yes, Infinity, [1, 0]), (3, b, false, LT, yes, Infinity, [0, 1])," +
                " (3, b, false, LT, yes, NaN, [1, 0]), (3, b, false, LT, yes, NaN, [0, 1])," +
                " (3, b, false, LT, no, Infinity, [1, 0]), (3, b, false, LT, no, Infinity, [0, 1])," +
                " (3, b, false, LT, no, NaN, [1, 0]), (3, b, false, LT, no, NaN, [0, 1])," +
                " (3, b, true, EQ, yes, Infinity, [1, 0]), (3, b, true, EQ, yes, Infinity, [0, 1])," +
                " (3, b, true, EQ, yes, NaN, [1, 0]), (3, b, true, EQ, yes, NaN, [0, 1])," +
                " (3, b, true, EQ, no, Infinity, [1, 0]), (3, b, true, EQ, no, Infinity, [0, 1])," +
                " (3, b, true, EQ, no, NaN, [1, 0]), (3, b, true, EQ, no, NaN, [0, 1])," +
                " (3, b, true, LT, yes, Infinity, [1, 0]), (3, b, true, LT, yes, Infinity, [0, 1])," +
                " (3, b, true, LT, yes, NaN, [1, 0]), (3, b, true, LT, yes, NaN, [0, 1])," +
                " (3, b, true, LT, no, Infinity, [1, 0]), (3, b, true, LT, no, Infinity, [0, 1])," +
                " (3, b, true, LT, no, NaN, [1, 0]), (3, b, true, LT, no, NaN, [0, 1])," +
                " (3, a, false, GT, yes, Infinity, [1, 0]), (3, a, false, GT, yes, Infinity, [0, 1])," +
                " (3, a, false, GT, yes, NaN, [1, 0]), (3, a, false, GT, yes, NaN, [0, 1])," +
                " (3, a, false, GT, no, Infinity, [1, 0]), (3, a, false, GT, no, Infinity, [0, 1])," +
                " (3, a, false, GT, no, NaN, [1, 0]), (3, a, false, GT, no, NaN, [0, 1])," +
                " (3, a, true, GT, yes, Infinity, [1, 0]), (3, a, true, GT, yes, Infinity, [0, 1])," +
                " (3, a, true, GT, yes, NaN, [1, 0]), (3, a, true, GT, yes, NaN, [0, 1])," +
                " (3, a, true, GT, no, Infinity, [1, 0]), (3, a, true, GT, no, Infinity, [0, 1])," +
                " (3, a, true, GT, no, NaN, [1, 0]), (3, a, true, GT, no, NaN, [0, 1])," +
                " (3, b, false, GT, yes, Infinity, [1, 0]), (3, b, false, GT, yes, Infinity, [0, 1])," +
                " (3, b, false, GT, yes, NaN, [1, 0]), (3, b, false, GT, yes, NaN, [0, 1])," +
                " (3, b, false, GT, no, Infinity, [1, 0]), (3, b, false, GT, no, Infinity, [0, 1])," +
                " (3, b, false, GT, no, NaN, [1, 0]), (3, b, false, GT, no, NaN, [0, 1])," +
                " (3, b, true, GT, yes, Infinity, [1, 0]), (3, b, true, GT, yes, Infinity, [0, 1])," +
                " (3, b, true, GT, yes, NaN, [1, 0]), (3, b, true, GT, yes, NaN, [0, 1])," +
                " (3, b, true, GT, no, Infinity, [1, 0]), (3, b, true, GT, no, Infinity, [0, 1])," +
                " (3, b, true, GT, no, NaN, [1, 0]), (3, b, true, GT, no, NaN, [0, 1])," +
                " (3, c, false, EQ, yes, Infinity, [1, 0]), (3, c, false, EQ, yes, Infinity, [0, 1])," +
                " (3, c, false, EQ, yes, NaN, [1, 0]), (3, c, false, EQ, yes, NaN, [0, 1])," +
                " (3, c, false, EQ, no, Infinity, [1, 0]), (3, c, false, EQ, no, Infinity, [0, 1])," +
                " (3, c, false, EQ, no, NaN, [1, 0]), (3, c, false, EQ, no, NaN, [0, 1])," +
                " (3, c, false, LT, yes, Infinity, [1, 0]), (3, c, false, LT, yes, Infinity, [0, 1])," +
                " (3, c, false, LT, yes, NaN, [1, 0]), (3, c, false, LT, yes, NaN, [0, 1])," +
                " (3, c, false, LT, no, Infinity, [1, 0]), (3, c, false, LT, no, Infinity, [0, 1])," +
                " (3, c, false, LT, no, NaN, [1, 0]), (3, c, false, LT, no, NaN, [0, 1])," +
                " (3, c, true, EQ, yes, Infinity, [1, 0]), (3, c, true, EQ, yes, Infinity, [0, 1])," +
                " (3, c, true, EQ, yes, NaN, [1, 0]), (3, c, true, EQ, yes, NaN, [0, 1])," +
                " (3, c, true, EQ, no, Infinity, [1, 0]), (3, c, true, EQ, no, Infinity, [0, 1])," +
                " (3, c, true, EQ, no, NaN, [1, 0]), (3, c, true, EQ, no, NaN, [0, 1])," +
                " (3, c, true, LT, yes, Infinity, [1, 0]), (3, c, true, LT, yes, Infinity, [0, 1])," +
                " (3, c, true, LT, yes, NaN, [1, 0]), (3, c, true, LT, yes, NaN, [0, 1])," +
                " (3, c, true, LT, no, Infinity, [1, 0]), (3, c, true, LT, no, Infinity, [0, 1])," +
                " (3, c, true, LT, no, NaN, [1, 0]), (3, c, true, LT, no, NaN, [0, 1])," +
                " (3, c, false, GT, yes, Infinity, [1, 0]), (3, c, false, GT, yes, Infinity, [0, 1])," +
                " (3, c, false, GT, yes, NaN, [1, 0]), (3, c, false, GT, yes, NaN, [0, 1])," +
                " (3, c, false, GT, no, Infinity, [1, 0]), (3, c, false, GT, no, Infinity, [0, 1])," +
                " (3, c, false, GT, no, NaN, [1, 0]), (3, c, false, GT, no, NaN, [0, 1])," +
                " (3, c, true, GT, yes, Infinity, [1, 0]), (3, c, true, GT, yes, Infinity, [0, 1])," +
                " (3, c, true, GT, yes, NaN, [1, 0]), (3, c, true, GT, yes, NaN, [0, 1])," +
                " (3, c, true, GT, no, Infinity, [1, 0]), (3, c, true, GT, no, Infinity, [0, 1])," +
                " (3, c, true, GT, no, NaN, [1, 0]), (3, c, true, GT, no, NaN, [0, 1])]");
        aeq(septuples(
                        Arrays.asList(1, 2, null, 4),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                ),
                "[(1, a, false, EQ, yes, Infinity, [1, 0]), (1, a, false, EQ, yes, Infinity, [0, 1])," +
                " (1, a, false, EQ, yes, NaN, [1, 0]), (1, a, false, EQ, yes, NaN, [0, 1])," +
                " (1, a, false, EQ, no, Infinity, [1, 0]), (1, a, false, EQ, no, Infinity, [0, 1])," +
                " (1, a, false, EQ, no, NaN, [1, 0]), (1, a, false, EQ, no, NaN, [0, 1])," +
                " (1, a, false, LT, yes, Infinity, [1, 0]), (1, a, false, LT, yes, Infinity, [0, 1])," +
                " (1, a, false, LT, yes, NaN, [1, 0]), (1, a, false, LT, yes, NaN, [0, 1])," +
                " (1, a, false, LT, no, Infinity, [1, 0]), (1, a, false, LT, no, Infinity, [0, 1])," +
                " (1, a, false, LT, no, NaN, [1, 0]), (1, a, false, LT, no, NaN, [0, 1])," +
                " (1, a, true, EQ, yes, Infinity, [1, 0]), (1, a, true, EQ, yes, Infinity, [0, 1])," +
                " (1, a, true, EQ, yes, NaN, [1, 0]), (1, a, true, EQ, yes, NaN, [0, 1])," +
                " (1, a, true, EQ, no, Infinity, [1, 0]), (1, a, true, EQ, no, Infinity, [0, 1])," +
                " (1, a, true, EQ, no, NaN, [1, 0]), (1, a, true, EQ, no, NaN, [0, 1])," +
                " (1, a, true, LT, yes, Infinity, [1, 0]), (1, a, true, LT, yes, Infinity, [0, 1])," +
                " (1, a, true, LT, yes, NaN, [1, 0]), (1, a, true, LT, yes, NaN, [0, 1])," +
                " (1, a, true, LT, no, Infinity, [1, 0]), (1, a, true, LT, no, Infinity, [0, 1])," +
                " (1, a, true, LT, no, NaN, [1, 0]), (1, a, true, LT, no, NaN, [0, 1])," +
                " (1, b, false, EQ, yes, Infinity, [1, 0]), (1, b, false, EQ, yes, Infinity, [0, 1])," +
                " (1, b, false, EQ, yes, NaN, [1, 0]), (1, b, false, EQ, yes, NaN, [0, 1])," +
                " (1, b, false, EQ, no, Infinity, [1, 0]), (1, b, false, EQ, no, Infinity, [0, 1])," +
                " (1, b, false, EQ, no, NaN, [1, 0]), (1, b, false, EQ, no, NaN, [0, 1])," +
                " (1, b, false, LT, yes, Infinity, [1, 0]), (1, b, false, LT, yes, Infinity, [0, 1])," +
                " (1, b, false, LT, yes, NaN, [1, 0]), (1, b, false, LT, yes, NaN, [0, 1])," +
                " (1, b, false, LT, no, Infinity, [1, 0]), (1, b, false, LT, no, Infinity, [0, 1])," +
                " (1, b, false, LT, no, NaN, [1, 0]), (1, b, false, LT, no, NaN, [0, 1])," +
                " (1, b, true, EQ, yes, Infinity, [1, 0]), (1, b, true, EQ, yes, Infinity, [0, 1])," +
                " (1, b, true, EQ, yes, NaN, [1, 0]), (1, b, true, EQ, yes, NaN, [0, 1])," +
                " (1, b, true, EQ, no, Infinity, [1, 0]), (1, b, true, EQ, no, Infinity, [0, 1])," +
                " (1, b, true, EQ, no, NaN, [1, 0]), (1, b, true, EQ, no, NaN, [0, 1])," +
                " (1, b, true, LT, yes, Infinity, [1, 0]), (1, b, true, LT, yes, Infinity, [0, 1])," +
                " (1, b, true, LT, yes, NaN, [1, 0]), (1, b, true, LT, yes, NaN, [0, 1])," +
                " (1, b, true, LT, no, Infinity, [1, 0]), (1, b, true, LT, no, Infinity, [0, 1])," +
                " (1, b, true, LT, no, NaN, [1, 0]), (1, b, true, LT, no, NaN, [0, 1])," +
                " (2, a, false, EQ, yes, Infinity, [1, 0]), (2, a, false, EQ, yes, Infinity, [0, 1])," +
                " (2, a, false, EQ, yes, NaN, [1, 0]), (2, a, false, EQ, yes, NaN, [0, 1])," +
                " (2, a, false, EQ, no, Infinity, [1, 0]), (2, a, false, EQ, no, Infinity, [0, 1])," +
                " (2, a, false, EQ, no, NaN, [1, 0]), (2, a, false, EQ, no, NaN, [0, 1])," +
                " (2, a, false, LT, yes, Infinity, [1, 0]), (2, a, false, LT, yes, Infinity, [0, 1])," +
                " (2, a, false, LT, yes, NaN, [1, 0]), (2, a, false, LT, yes, NaN, [0, 1])," +
                " (2, a, false, LT, no, Infinity, [1, 0]), (2, a, false, LT, no, Infinity, [0, 1])," +
                " (2, a, false, LT, no, NaN, [1, 0]), (2, a, false, LT, no, NaN, [0, 1])," +
                " (2, a, true, EQ, yes, Infinity, [1, 0]), (2, a, true, EQ, yes, Infinity, [0, 1])," +
                " (2, a, true, EQ, yes, NaN, [1, 0]), (2, a, true, EQ, yes, NaN, [0, 1])," +
                " (2, a, true, EQ, no, Infinity, [1, 0]), (2, a, true, EQ, no, Infinity, [0, 1])," +
                " (2, a, true, EQ, no, NaN, [1, 0]), (2, a, true, EQ, no, NaN, [0, 1])," +
                " (2, a, true, LT, yes, Infinity, [1, 0]), (2, a, true, LT, yes, Infinity, [0, 1]), (2, a, true, LT, yes, NaN, [1, 0]), (2, a, true, LT, yes, NaN, [0, 1]), (2, a, true, LT, no, Infinity, [1, 0]), (2, a, true, LT, no, Infinity, [0, 1]), (2, a, true, LT, no, NaN, [1, 0]), (2, a, true, LT, no, NaN, [0, 1]), (2, b, false, EQ, yes, Infinity, [1, 0]), (2, b, false, EQ, yes, Infinity, [0, 1]), (2, b, false, EQ, yes, NaN, [1, 0]), (2, b, false, EQ, yes, NaN, [0, 1]), (2, b, false, EQ, no, Infinity, [1, 0]), (2, b, false, EQ, no, Infinity, [0, 1]), (2, b, false, EQ, no, NaN, [1, 0]), (2, b, false, EQ, no, NaN, [0, 1]), (2, b, false, LT, yes, Infinity, [1, 0]), (2, b, false, LT, yes, Infinity, [0, 1]), (2, b, false, LT, yes, NaN, [1, 0]), (2, b, false, LT, yes, NaN, [0, 1]), (2, b, false, LT, no, Infinity, [1, 0]), (2, b, false, LT, no, Infinity, [0, 1]), (2, b, false, LT, no, NaN, [1, 0]), (2, b, false, LT, no, NaN, [0, 1]), (2, b, true, EQ, yes, Infinity, [1, 0]), (2, b, true, EQ, yes, Infinity, [0, 1]), (2, b, true, EQ, yes, NaN, [1, 0]), (2, b, true, EQ, yes, NaN, [0, 1]), (2, b, true, EQ, no, Infinity, [1, 0]), (2, b, true, EQ, no, Infinity, [0, 1]), (2, b, true, EQ, no, NaN, [1, 0]), (2, b, true, EQ, no, NaN, [0, 1]), (2, b, true, LT, yes, Infinity, [1, 0]), (2, b, true, LT, yes, Infinity, [0, 1]), (2, b, true, LT, yes, NaN, [1, 0]), (2, b, true, LT, yes, NaN, [0, 1]), (2, b, true, LT, no, Infinity, [1, 0]), (2, b, true, LT, no, Infinity, [0, 1]), (2, b, true, LT, no, NaN, [1, 0]), (2, b, true, LT, no, NaN, [0, 1]), (1, a, false, GT, yes, Infinity, [1, 0]), (1, a, false, GT, yes, Infinity, [0, 1]), (1, a, false, GT, yes, NaN, [1, 0]), (1, a, false, GT, yes, NaN, [0, 1]), (1, a, false, GT, no, Infinity, [1, 0]), (1, a, false, GT, no, Infinity, [0, 1]), (1, a, false, GT, no, NaN, [1, 0]), (1, a, false, GT, no, NaN, [0, 1]), (1, a, true, GT, yes, Infinity, [1, 0]), (1, a, true, GT, yes, Infinity, [0, 1]), (1, a, true, GT, yes, NaN, [1, 0]), (1, a, true, GT, yes, NaN, [0, 1]), (1, a, true, GT, no, Infinity, [1, 0]), (1, a, true, GT, no, Infinity, [0, 1]), (1, a, true, GT, no, NaN, [1, 0]), (1, a, true, GT, no, NaN, [0, 1]), (1, b, false, GT, yes, Infinity, [1, 0]), (1, b, false, GT, yes, Infinity, [0, 1]), (1, b, false, GT, yes, NaN, [1, 0]), (1, b, false, GT, yes, NaN, [0, 1]), (1, b, false, GT, no, Infinity, [1, 0]), (1, b, false, GT, no, Infinity, [0, 1]), (1, b, false, GT, no, NaN, [1, 0]), (1, b, false, GT, no, NaN, [0, 1]), (1, b, true, GT, yes, Infinity, [1, 0]), (1, b, true, GT, yes, Infinity, [0, 1]), (1, b, true, GT, yes, NaN, [1, 0]), (1, b, true, GT, yes, NaN, [0, 1]), (1, b, true, GT, no, Infinity, [1, 0]), (1, b, true, GT, no, Infinity, [0, 1]), (1, b, true, GT, no, NaN, [1, 0]), (1, b, true, GT, no, NaN, [0, 1]), (2, a, false, GT, yes, Infinity, [1, 0]), (2, a, false, GT, yes, Infinity, [0, 1]), (2, a, false, GT, yes, NaN, [1, 0]), (2, a, false, GT, yes, NaN, [0, 1]), (2, a, false, GT, no, Infinity, [1, 0]), (2, a, false, GT, no, Infinity, [0, 1]), (2, a, false, GT, no, NaN, [1, 0]), (2, a, false, GT, no, NaN, [0, 1]), (2, a, true, GT, yes, Infinity, [1, 0]), (2, a, true, GT, yes, Infinity, [0, 1]), (2, a, true, GT, yes, NaN, [1, 0]), (2, a, true, GT, yes, NaN, [0, 1]), (2, a, true, GT, no, Infinity, [1, 0]), (2, a, true, GT, no, Infinity, [0, 1]), (2, a, true, GT, no, NaN, [1, 0]), (2, a, true, GT, no, NaN, [0, 1]), (2, b, false, GT, yes, Infinity, [1, 0]), (2, b, false, GT, yes, Infinity, [0, 1]), (2, b, false, GT, yes, NaN, [1, 0]), (2, b, false, GT, yes, NaN, [0, 1]), (2, b, false, GT, no, Infinity, [1, 0]), (2, b, false, GT, no, Infinity, [0, 1]), (2, b, false, GT, no, NaN, [1, 0]), (2, b, false, GT, no, NaN, [0, 1]), (2, b, true, GT, yes, Infinity, [1, 0]), (2, b, true, GT, yes, Infinity, [0, 1]), (2, b, true, GT, yes, NaN, [1, 0]), (2, b, true, GT, yes, NaN, [0, 1]), (2, b, true, GT, no, Infinity, [1, 0]), (2, b, true, GT, no, Infinity, [0, 1]), (2, b, true, GT, no, NaN, [1, 0]), (2, b, true, GT, no, NaN, [0, 1]), (1, c, false, EQ, yes, Infinity, [1, 0]), (1, c, false, EQ, yes, Infinity, [0, 1]), (1, c, false, EQ, yes, NaN, [1, 0]), (1, c, false, EQ, yes, NaN, [0, 1]), (1, c, false, EQ, no, Infinity, [1, 0]), (1, c, false, EQ, no, Infinity, [0, 1]), (1, c, false, EQ, no, NaN, [1, 0]), (1, c, false, EQ, no, NaN, [0, 1]), (1, c, false, LT, yes, Infinity, [1, 0]), (1, c, false, LT, yes, Infinity, [0, 1]), (1, c, false, LT, yes, NaN, [1, 0]), (1, c, false, LT, yes, NaN, [0, 1]), (1, c, false, LT, no, Infinity, [1, 0]), (1, c, false, LT, no, Infinity, [0, 1]), (1, c, false, LT, no, NaN, [1, 0]), (1, c, false, LT, no, NaN, [0, 1]), (1, c, true, EQ, yes, Infinity, [1, 0]), (1, c, true, EQ, yes, Infinity, [0, 1]), (1, c, true, EQ, yes, NaN, [1, 0]), (1, c, true, EQ, yes, NaN, [0, 1]), (1, c, true, EQ, no, Infinity, [1, 0]), (1, c, true, EQ, no, Infinity, [0, 1]), (1, c, true, EQ, no, NaN, [1, 0]), (1, c, true, EQ, no, NaN, [0, 1]), (1, c, true, LT, yes, Infinity, [1, 0]), (1, c, true, LT, yes, Infinity, [0, 1]), (1, c, true, LT, yes, NaN, [1, 0]), (1, c, true, LT, yes, NaN, [0, 1]), (1, c, true, LT, no, Infinity, [1, 0]), (1, c, true, LT, no, Infinity, [0, 1]), (1, c, true, LT, no, NaN, [1, 0]), (1, c, true, LT, no, NaN, [0, 1]), (1, d, false, EQ, yes, Infinity, [1, 0]), (1, d, false, EQ, yes, Infinity, [0, 1]), (1, d, false, EQ, yes, NaN, [1, 0]), (1, d, false, EQ, yes, NaN, [0, 1]), (1, d, false, EQ, no, Infinity, [1, 0]), (1, d, false, EQ, no, Infinity, [0, 1]), (1, d, false, EQ, no, NaN, [1, 0]), (1, d, false, EQ, no, NaN, [0, 1]), (1, d, false, LT, yes, Infinity, [1, 0]), (1, d, false, LT, yes, Infinity, [0, 1]), (1, d, false, LT, yes, NaN, [1, 0]), (1, d, false, LT, yes, NaN, [0, 1]), (1, d, false, LT, no, Infinity, [1, 0]), (1, d, false, LT, no, Infinity, [0, 1]), (1, d, false, LT, no, NaN, [1, 0]), (1, d, false, LT, no, NaN, [0, 1]), (1, d, true, EQ, yes, Infinity, [1, 0]), (1, d, true, EQ, yes, Infinity, [0, 1]), (1, d, true, EQ, yes, NaN, [1, 0]), (1, d, true, EQ, yes, NaN, [0, 1]), (1, d, true, EQ, no, Infinity, [1, 0]), (1, d, true, EQ, no, Infinity, [0, 1]), (1, d, true, EQ, no, NaN, [1, 0]), (1, d, true, EQ, no, NaN, [0, 1]), (1, d, true, LT, yes, Infinity, [1, 0]), (1, d, true, LT, yes, Infinity, [0, 1]), (1, d, true, LT, yes, NaN, [1, 0]), (1, d, true, LT, yes, NaN, [0, 1]), (1, d, true, LT, no, Infinity, [1, 0]), (1, d, true, LT, no, Infinity, [0, 1]), (1, d, true, LT, no, NaN, [1, 0]), (1, d, true, LT, no, NaN, [0, 1]), (2, c, false, EQ, yes, Infinity, [1, 0]), (2, c, false, EQ, yes, Infinity, [0, 1]), (2, c, false, EQ, yes, NaN, [1, 0]), (2, c, false, EQ, yes, NaN, [0, 1]), (2, c, false, EQ, no, Infinity, [1, 0]), (2, c, false, EQ, no, Infinity, [0, 1]), (2, c, false, EQ, no, NaN, [1, 0]), (2, c, false, EQ, no, NaN, [0, 1]), (2, c, false, LT, yes, Infinity, [1, 0]), (2, c, false, LT, yes, Infinity, [0, 1]), (2, c, false, LT, yes, NaN, [1, 0]), (2, c, false, LT, yes, NaN, [0, 1]), (2, c, false, LT, no, Infinity, [1, 0]), (2, c, false, LT, no, Infinity, [0, 1]), (2, c, false, LT, no, NaN, [1, 0]), (2, c, false, LT, no, NaN, [0, 1]), (2, c, true, EQ, yes, Infinity, [1, 0]), (2, c, true, EQ, yes, Infinity, [0, 1]), (2, c, true, EQ, yes, NaN, [1, 0]), (2, c, true, EQ, yes, NaN, [0, 1]), (2, c, true, EQ, no, Infinity, [1, 0]), (2, c, true, EQ, no, Infinity, [0, 1]), (2, c, true, EQ, no, NaN, [1, 0]), (2, c, true, EQ, no, NaN, [0, 1]), (2, c, true, LT, yes, Infinity, [1, 0]), (2, c, true, LT, yes, Infinity, [0, 1]), (2, c, true, LT, yes, NaN, [1, 0]), (2, c, true, LT, yes, NaN, [0, 1]), (2, c, true, LT, no, Infinity, [1, 0]), (2, c, true, LT, no, Infinity, [0, 1]), (2, c, true, LT, no, NaN, [1, 0]), (2, c, true, LT, no, NaN, [0, 1]), (2, d, false, EQ, yes, Infinity, [1, 0]), (2, d, false, EQ, yes, Infinity, [0, 1]), (2, d, false, EQ, yes, NaN, [1, 0]), (2, d, false, EQ, yes, NaN, [0, 1]), (2, d, false, EQ, no, Infinity, [1, 0]), (2, d, false, EQ, no, Infinity, [0, 1]), (2, d, false, EQ, no, NaN, [1, 0]), (2, d, false, EQ, no, NaN, [0, 1]), (2, d, false, LT, yes, Infinity, [1, 0]), (2, d, false, LT, yes, Infinity, [0, 1]), (2, d, false, LT, yes, NaN, [1, 0]), (2, d, false, LT, yes, NaN, [0, 1]), (2, d, false, LT, no, Infinity, [1, 0]), (2, d, false, LT, no, Infinity, [0, 1]), (2, d, false, LT, no, NaN, [1, 0]), (2, d, false, LT, no, NaN, [0, 1]), (2, d, true, EQ, yes, Infinity, [1, 0]), (2, d, true, EQ, yes, Infinity, [0, 1]), (2, d, true, EQ, yes, NaN, [1, 0]), (2, d, true, EQ, yes, NaN, [0, 1]), (2, d, true, EQ, no, Infinity, [1, 0]), (2, d, true, EQ, no, Infinity, [0, 1]), (2, d, true, EQ, no, NaN, [1, 0]), (2, d, true, EQ, no, NaN, [0, 1]), (2, d, true, LT, yes, Infinity, [1, 0]), (2, d, true, LT, yes, Infinity, [0, 1]), (2, d, true, LT, yes, NaN, [1, 0]), (2, d, true, LT, yes, NaN, [0, 1]), (2, d, true, LT, no, Infinity, [1, 0]), (2, d, true, LT, no, Infinity, [0, 1]), (2, d, true, LT, no, NaN, [1, 0]), (2, d, true, LT, no, NaN, [0, 1]), (1, c, false, GT, yes, Infinity, [1, 0]), (1, c, false, GT, yes, Infinity, [0, 1]), (1, c, false, GT, yes, NaN, [1, 0]), (1, c, false, GT, yes, NaN, [0, 1]), (1, c, false, GT, no, Infinity, [1, 0]), (1, c, false, GT, no, Infinity, [0, 1]), (1, c, false, GT, no, NaN, [1, 0]), (1, c, false, GT, no, NaN, [0, 1]), (1, c, true, GT, yes, Infinity, [1, 0]), (1, c, true, GT, yes, Infinity, [0, 1]), (1, c, true, GT, yes, NaN, [1, 0]), (1, c, true, GT, yes, NaN, [0, 1]), (1, c, true, GT, no, Infinity, [1, 0]), (1, c, true, GT, no, Infinity, [0, 1]), (1, c, true, GT, no, NaN, [1, 0]), (1, c, true, GT, no, NaN, [0, 1]), (1, d, false, GT, yes, Infinity, [1, 0]), (1, d, false, GT, yes, Infinity, [0, 1]), (1, d, false, GT, yes, NaN, [1, 0]), (1, d, false, GT, yes, NaN, [0, 1]), (1, d, false, GT, no, Infinity, [1, 0]), (1, d, false, GT, no, Infinity, [0, 1]), (1, d, false, GT, no, NaN, [1, 0]), (1, d, false, GT, no, NaN, [0, 1]), (1, d, true, GT, yes, Infinity, [1, 0]), (1, d, true, GT, yes, Infinity, [0, 1]), (1, d, true, GT, yes, NaN, [1, 0]), (1, d, true, GT, yes, NaN, [0, 1]), (1, d, true, GT, no, Infinity, [1, 0]), (1, d, true, GT, no, Infinity, [0, 1]), (1, d, true, GT, no, NaN, [1, 0]), (1, d, true, GT, no, NaN, [0, 1]), (2, c, false, GT, yes, Infinity, [1, 0]), (2, c, false, GT, yes, Infinity, [0, 1]), (2, c, false, GT, yes, NaN, [1, 0]), (2, c, false, GT, yes, NaN, [0, 1]), (2, c, false, GT, no, Infinity, [1, 0]), (2, c, false, GT, no, Infinity, [0, 1]), (2, c, false, GT, no, NaN, [1, 0]), (2, c, false, GT, no, NaN, [0, 1]), (2, c, true, GT, yes, Infinity, [1, 0]), (2, c, true, GT, yes, Infinity, [0, 1]), (2, c, true, GT, yes, NaN, [1, 0]), (2, c, true, GT, yes, NaN, [0, 1]), (2, c, true, GT, no, Infinity, [1, 0]), (2, c, true, GT, no, Infinity, [0, 1]), (2, c, true, GT, no, NaN, [1, 0]), (2, c, true, GT, no, NaN, [0, 1]), (2, d, false, GT, yes, Infinity, [1, 0]), (2, d, false, GT, yes, Infinity, [0, 1]), (2, d, false, GT, yes, NaN, [1, 0]), (2, d, false, GT, yes, NaN, [0, 1]), (2, d, false, GT, no, Infinity, [1, 0]), (2, d, false, GT, no, Infinity, [0, 1]), (2, d, false, GT, no, NaN, [1, 0]), (2, d, false, GT, no, NaN, [0, 1]), (2, d, true, GT, yes, Infinity, [1, 0]), (2, d, true, GT, yes, Infinity, [0, 1]), (2, d, true, GT, yes, NaN, [1, 0]), (2, d, true, GT, yes, NaN, [0, 1]), (2, d, true, GT, no, Infinity, [1, 0]), (2, d, true, GT, no, Infinity, [0, 1]), (2, d, true, GT, no, NaN, [1, 0]), (2, d, true, GT, no, NaN, [0, 1]), (null, a, false, EQ, yes, Infinity, [1, 0]), (null, a, false, EQ, yes, Infinity, [0, 1]), (null, a, false, EQ, yes, NaN, [1, 0]), (null, a, false, EQ, yes, NaN, [0, 1]), (null, a, false, EQ, no, Infinity, [1, 0]), (null, a, false, EQ, no, Infinity, [0, 1]), (null, a, false, EQ, no, NaN, [1, 0]), (null, a, false, EQ, no, NaN, [0, 1]), (null, a, false, LT, yes, Infinity, [1, 0]), (null, a, false, LT, yes, Infinity, [0, 1]), (null, a, false, LT, yes, NaN, [1, 0]), (null, a, false, LT, yes, NaN, [0, 1]), (null, a, false, LT, no, Infinity, [1, 0]), (null, a, false, LT, no, Infinity, [0, 1]), (null, a, false, LT, no, NaN, [1, 0]), (null, a, false, LT, no, NaN, [0, 1]), (null, a, true, EQ, yes, Infinity, [1, 0]), (null, a, true, EQ, yes, Infinity, [0, 1]), (null, a, true, EQ, yes, NaN, [1, 0]), (null, a, true, EQ, yes, NaN, [0, 1]), (null, a, true, EQ, no, Infinity, [1, 0]), (null, a, true, EQ, no, Infinity, [0, 1]), (null, a, true, EQ, no, NaN, [1, 0]), (null, a, true, EQ, no, NaN, [0, 1]), (null, a, true, LT, yes, Infinity, [1, 0]), (null, a, true, LT, yes, Infinity, [0, 1]), (null, a, true, LT, yes, NaN, [1, 0]), (null, a, true, LT, yes, NaN, [0, 1]), (null, a, true, LT, no, Infinity, [1, 0]), (null, a, true, LT, no, Infinity, [0, 1]), (null, a, true, LT, no, NaN, [1, 0]), (null, a, true, LT, no, NaN, [0, 1]), (null, b, false, EQ, yes, Infinity, [1, 0]), (null, b, false, EQ, yes, Infinity, [0, 1]), (null, b, false, EQ, yes, NaN, [1, 0]), (null, b, false, EQ, yes, NaN, [0, 1]), (null, b, false, EQ, no, Infinity, [1, 0]), (null, b, false, EQ, no, Infinity, [0, 1]), (null, b, false, EQ, no, NaN, [1, 0]), (null, b, false, EQ, no, NaN, [0, 1]), (null, b, false, LT, yes, Infinity, [1, 0]), (null, b, false, LT, yes, Infinity, [0, 1]), (null, b, false, LT, yes, NaN, [1, 0]), (null, b, false, LT, yes, NaN, [0, 1]), (null, b, false, LT, no, Infinity, [1, 0]), (null, b, false, LT, no, Infinity, [0, 1]), (null, b, false, LT, no, NaN, [1, 0]), (null, b, false, LT, no, NaN, [0, 1]), (null, b, true, EQ, yes, Infinity, [1, 0]), (null, b, true, EQ, yes, Infinity, [0, 1]), (null, b, true, EQ, yes, NaN, [1, 0]), (null, b, true, EQ, yes, NaN, [0, 1]), (null, b, true, EQ, no, Infinity, [1, 0]), (null, b, true, EQ, no, Infinity, [0, 1]), (null, b, true, EQ, no, NaN, [1, 0]), (null, b, true, EQ, no, NaN, [0, 1]), (null, b, true, LT, yes, Infinity, [1, 0]), (null, b, true, LT, yes, Infinity, [0, 1]), (null, b, true, LT, yes, NaN, [1, 0]), (null, b, true, LT, yes, NaN, [0, 1]), (null, b, true, LT, no, Infinity, [1, 0]), (null, b, true, LT, no, Infinity, [0, 1]), (null, b, true, LT, no, NaN, [1, 0]), (null, b, true, LT, no, NaN, [0, 1]), (4, a, false, EQ, yes, Infinity, [1, 0]), (4, a, false, EQ, yes, Infinity, [0, 1]), (4, a, false, EQ, yes, NaN, [1, 0]), (4, a, false, EQ, yes, NaN, [0, 1]), (4, a, false, EQ, no, Infinity, [1, 0]), (4, a, false, EQ, no, Infinity, [0, 1]), (4, a, false, EQ, no, NaN, [1, 0]), (4, a, false, EQ, no, NaN, [0, 1]), (4, a, false, LT, yes, Infinity, [1, 0]), (4, a, false, LT, yes, Infinity, [0, 1]), (4, a, false, LT, yes, NaN, [1, 0]), (4, a, false, LT, yes, NaN, [0, 1]), (4, a, false, LT, no, Infinity, [1, 0]), (4, a, false, LT, no, Infinity, [0, 1]), (4, a, false, LT, no, NaN, [1, 0]), (4, a, false, LT, no, NaN, [0, 1]), (4, a, true, EQ, yes, Infinity, [1, 0]), (4, a, true, EQ, yes, Infinity, [0, 1]), (4, a, true, EQ, yes, NaN, [1, 0]), (4, a, true, EQ, yes, NaN, [0, 1]), (4, a, true, EQ, no, Infinity, [1, 0]), (4, a, true, EQ, no, Infinity, [0, 1]), (4, a, true, EQ, no, NaN, [1, 0]), (4, a, true, EQ, no, NaN, [0, 1]), (4, a, true, LT, yes, Infinity, [1, 0]), (4, a, true, LT, yes, Infinity, [0, 1]), (4, a, true, LT, yes, NaN, [1, 0]), (4, a, true, LT, yes, NaN, [0, 1]), (4, a, true, LT, no, Infinity, [1, 0]), (4, a, true, LT, no, Infinity, [0, 1]), (4, a, true, LT, no, NaN, [1, 0]), (4, a, true, LT, no, NaN, [0, 1]), (4, b, false, EQ, yes, Infinity, [1, 0]), (4, b, false, EQ, yes, Infinity, [0, 1]), (4, b, false, EQ, yes, NaN, [1, 0]), (4, b, false, EQ, yes, NaN, [0, 1]), (4, b, false, EQ, no, Infinity, [1, 0]), (4, b, false, EQ, no, Infinity, [0, 1]), (4, b, false, EQ, no, NaN, [1, 0]), (4, b, false, EQ, no, NaN, [0, 1]), (4, b, false, LT, yes, Infinity, [1, 0]), (4, b, false, LT, yes, Infinity, [0, 1]), (4, b, false, LT, yes, NaN, [1, 0]), (4, b, false, LT, yes, NaN, [0, 1]), (4, b, false, LT, no, Infinity, [1, 0]), (4, b, false, LT, no, Infinity, [0, 1]), (4, b, false, LT, no, NaN, [1, 0]), (4, b, false, LT, no, NaN, [0, 1]), (4, b, true, EQ, yes, Infinity, [1, 0]), (4, b, true, EQ, yes, Infinity, [0, 1]), (4, b, true, EQ, yes, NaN, [1, 0]), (4, b, true, EQ, yes, NaN, [0, 1]), (4, b, true, EQ, no, Infinity, [1, 0]), (4, b, true, EQ, no, Infinity, [0, 1]), (4, b, true, EQ, no, NaN, [1, 0]), (4, b, true, EQ, no, NaN, [0, 1]), (4, b, true, LT, yes, Infinity, [1, 0]), (4, b, true, LT, yes, Infinity, [0, 1]), (4, b, true, LT, yes, NaN, [1, 0]), (4, b, true, LT, yes, NaN, [0, 1]), (4, b, true, LT, no, Infinity, [1, 0]), (4, b, true, LT, no, Infinity, [0, 1]), (4, b, true, LT, no, NaN, [1, 0]), (4, b, true, LT, no, NaN, [0, 1]), (null, a, false, GT, yes, Infinity, [1, 0]), (null, a, false, GT, yes, Infinity, [0, 1]), (null, a, false, GT, yes, NaN, [1, 0]), (null, a, false, GT, yes, NaN, [0, 1]), (null, a, false, GT, no, Infinity, [1, 0]), (null, a, false, GT, no, Infinity, [0, 1]), (null, a, false, GT, no, NaN, [1, 0]), (null, a, false, GT, no, NaN, [0, 1]), (null, a, true, GT, yes, Infinity, [1, 0]), (null, a, true, GT, yes, Infinity, [0, 1]), (null, a, true, GT, yes, NaN, [1, 0]), (null, a, true, GT, yes, NaN, [0, 1]), (null, a, true, GT, no, Infinity, [1, 0]), (null, a, true, GT, no, Infinity, [0, 1]), (null, a, true, GT, no, NaN, [1, 0]), (null, a, true, GT, no, NaN, [0, 1]), (null, b, false, GT, yes, Infinity, [1, 0]), (null, b, false, GT, yes, Infinity, [0, 1]), (null, b, false, GT, yes, NaN, [1, 0]), (null, b, false, GT, yes, NaN, [0, 1]), (null, b, false, GT, no, Infinity, [1, 0]), (null, b, false, GT, no, Infinity, [0, 1]), (null, b, false, GT, no, NaN, [1, 0]), (null, b, false, GT, no, NaN, [0, 1]), (null, b, true, GT, yes, Infinity, [1, 0]), (null, b, true, GT, yes, Infinity, [0, 1]), (null, b, true, GT, yes, NaN, [1, 0]), (null, b, true, GT, yes, NaN, [0, 1]), (null, b, true, GT, no, Infinity, [1, 0]), (null, b, true, GT, no, Infinity, [0, 1]), (null, b, true, GT, no, NaN, [1, 0]), (null, b, true, GT, no, NaN, [0, 1]), (4, a, false, GT, yes, Infinity, [1, 0]), (4, a, false, GT, yes, Infinity, [0, 1]), (4, a, false, GT, yes, NaN, [1, 0]), (4, a, false, GT, yes, NaN, [0, 1]), (4, a, false, GT, no, Infinity, [1, 0]), (4, a, false, GT, no, Infinity, [0, 1]), (4, a, false, GT, no, NaN, [1, 0]), (4, a, false, GT, no, NaN, [0, 1]), (4, a, true, GT, yes, Infinity, [1, 0]), (4, a, true, GT, yes, Infinity, [0, 1]), (4, a, true, GT, yes, NaN, [1, 0]), (4, a, true, GT, yes, NaN, [0, 1]), (4, a, true, GT, no, Infinity, [1, 0]), (4, a, true, GT, no, Infinity, [0, 1]), (4, a, true, GT, no, NaN, [1, 0]), (4, a, true, GT, no, NaN, [0, 1]), (4, b, false, GT, yes, Infinity, [1, 0]), (4, b, false, GT, yes, Infinity, [0, 1]), (4, b, false, GT, yes, NaN, [1, 0]), (4, b, false, GT, yes, NaN, [0, 1]), (4, b, false, GT, no, Infinity, [1, 0]), (4, b, false, GT, no, Infinity, [0, 1]), (4, b, false, GT, no, NaN, [1, 0]), (4, b, false, GT, no, NaN, [0, 1]), (4, b, true, GT, yes, Infinity, [1, 0]), (4, b, true, GT, yes, Infinity, [0, 1]), (4, b, true, GT, yes, NaN, [1, 0]), (4, b, true, GT, yes, NaN, [0, 1]), (4, b, true, GT, no, Infinity, [1, 0]), (4, b, true, GT, no, Infinity, [0, 1]), (4, b, true, GT, no, NaN, [1, 0]), (4, b, true, GT, no, NaN, [0, 1]), (null, c, false, EQ, yes, Infinity, [1, 0]), (null, c, false, EQ, yes, Infinity, [0, 1]), (null, c, false, EQ, yes, NaN, [1, 0]), (null, c, false, EQ, yes, NaN, [0, 1]), (null, c, false, EQ, no, Infinity, [1, 0]), (null, c, false, EQ, no, Infinity, [0, 1]), (null, c, false, EQ, no, NaN, [1, 0]), (null, c, false, EQ, no, NaN, [0, 1]), (null, c, false, LT, yes, Infinity, [1, 0]), (null, c, false, LT, yes, Infinity, [0, 1]), (null, c, false, LT, yes, NaN, [1, 0]), (null, c, false, LT, yes, NaN, [0, 1]), (null, c, false, LT, no, Infinity, [1, 0]), (null, c, false, LT, no, Infinity, [0, 1]), (null, c, false, LT, no, NaN, [1, 0]), (null, c, false, LT, no, NaN, [0, 1]), (null, c, true, EQ, yes, Infinity, [1, 0]), (null, c, true, EQ, yes, Infinity, [0, 1]), (null, c, true, EQ, yes, NaN, [1, 0]), (null, c, true, EQ, yes, NaN, [0, 1]), (null, c, true, EQ, no, Infinity, [1, 0]), (null, c, true, EQ, no, Infinity, [0, 1]), (null, c, true, EQ, no, NaN, [1, 0]), (null, c, true, EQ, no, NaN, [0, 1]), (null, c, true, LT, yes, Infinity, [1, 0]), (null, c, true, LT, yes, Infinity, [0, 1]), (null, c, true, LT, yes, NaN, [1, 0]), (null, c, true, LT, yes, NaN, [0, 1]), (null, c, true, LT, no, Infinity, [1, 0]), (null, c, true, LT, no, Infinity, [0, 1]), (null, c, true, LT, no, NaN, [1, 0]), (null, c, true, LT, no, NaN, [0, 1]), (null, d, false, EQ, yes, Infinity, [1, 0]), (null, d, false, EQ, yes, Infinity, [0, 1]), (null, d, false, EQ, yes, NaN, [1, 0]), (null, d, false, EQ, yes, NaN, [0, 1]), (null, d, false, EQ, no, Infinity, [1, 0]), (null, d, false, EQ, no, Infinity, [0, 1]), (null, d, false, EQ, no, NaN, [1, 0]), (null, d, false, EQ, no, NaN, [0, 1]), (null, d, false, LT, yes, Infinity, [1, 0]), (null, d, false, LT, yes, Infinity, [0, 1]), (null, d, false, LT, yes, NaN, [1, 0]), (null, d, false, LT, yes, NaN, [0, 1]), (null, d, false, LT, no, Infinity, [1, 0]), (null, d, false, LT, no, Infinity, [0, 1]), (null, d, false, LT, no, NaN, [1, 0]), (null, d, false, LT, no, NaN, [0, 1]), (null, d, true, EQ, yes, Infinity, [1, 0]), (null, d, true, EQ, yes, Infinity, [0, 1]), (null, d, true, EQ, yes, NaN, [1, 0]), (null, d, true, EQ, yes, NaN, [0, 1]), (null, d, true, EQ, no, Infinity, [1, 0]), (null, d, true, EQ, no, Infinity, [0, 1]), (null, d, true, EQ, no, NaN, [1, 0]), (null, d, true, EQ, no, NaN, [0, 1]), (null, d, true, LT, yes, Infinity, [1, 0]), (null, d, true, LT, yes, Infinity, [0, 1]), (null, d, true, LT, yes, NaN, [1, 0]), (null, d, true, LT, yes, NaN, [0, 1]), (null, d, true, LT, no, Infinity, [1, 0]), (null, d, true, LT, no, Infinity, [0, 1]), (null, d, true, LT, no, NaN, [1, 0]), (null, d, true, LT, no, NaN, [0, 1]), (4, c, false, EQ, yes, Infinity, [1, 0]), (4, c, false, EQ, yes, Infinity, [0, 1]), (4, c, false, EQ, yes, NaN, [1, 0]), (4, c, false, EQ, yes, NaN, [0, 1]), (4, c, false, EQ, no, Infinity, [1, 0]), (4, c, false, EQ, no, Infinity, [0, 1]), (4, c, false, EQ, no, NaN, [1, 0]), (4, c, false, EQ, no, NaN, [0, 1]), (4, c, false, LT, yes, Infinity, [1, 0]), (4, c, false, LT, yes, Infinity, [0, 1]), (4, c, false, LT, yes, NaN, [1, 0]), (4, c, false, LT, yes, NaN, [0, 1]), (4, c, false, LT, no, Infinity, [1, 0]), (4, c, false, LT, no, Infinity, [0, 1]), (4, c, false, LT, no, NaN, [1, 0]), (4, c, false, LT, no, NaN, [0, 1]), (4, c, true, EQ, yes, Infinity, [1, 0]), (4, c, true, EQ, yes, Infinity, [0, 1]), (4, c, true, EQ, yes, NaN, [1, 0]), (4, c, true, EQ, yes, NaN, [0, 1]), (4, c, true, EQ, no, Infinity, [1, 0]), (4, c, true, EQ, no, Infinity, [0, 1]), (4, c, true, EQ, no, NaN, [1, 0]), (4, c, true, EQ, no, NaN, [0, 1]), (4, c, true, LT, yes, Infinity, [1, 0]), (4, c, true, LT, yes, Infinity, [0, 1]), (4, c, true, LT, yes, NaN, [1, 0]), (4, c, true, LT, yes, NaN, [0, 1]), (4, c, true, LT, no, Infinity, [1, 0]), (4, c, true, LT, no, Infinity, [0, 1]), (4, c, true, LT, no, NaN, [1, 0]), (4, c, true, LT, no, NaN, [0, 1]), (4, d, false, EQ, yes, Infinity, [1, 0]), (4, d, false, EQ, yes, Infinity, [0, 1]), (4, d, false, EQ, yes, NaN, [1, 0]), (4, d, false, EQ, yes, NaN, [0, 1]), (4, d, false, EQ, no, Infinity, [1, 0]), (4, d, false, EQ, no, Infinity, [0, 1]), (4, d, false, EQ, no, NaN, [1, 0]), (4, d, false, EQ, no, NaN, [0, 1]), (4, d, false, LT, yes, Infinity, [1, 0]), (4, d, false, LT, yes, Infinity, [0, 1]), (4, d, false, LT, yes, NaN, [1, 0]), (4, d, false, LT, yes, NaN, [0, 1]), (4, d, false, LT, no, Infinity, [1, 0]), (4, d, false, LT, no, Infinity, [0, 1]), (4, d, false, LT, no, NaN, [1, 0]), (4, d, false, LT, no, NaN, [0, 1]), (4, d, true, EQ, yes, Infinity, [1, 0]), (4, d, true, EQ, yes, Infinity, [0, 1]), (4, d, true, EQ, yes, NaN, [1, 0]), (4, d, true, EQ, yes, NaN, [0, 1]), (4, d, true, EQ, no, Infinity, [1, 0]), (4, d, true, EQ, no, Infinity, [0, 1]), (4, d, true, EQ, no, NaN, [1, 0]), (4, d, true, EQ, no, NaN, [0, 1]), (4, d, true, LT, yes, Infinity, [1, 0]), (4, d, true, LT, yes, Infinity, [0, 1]), (4, d, true, LT, yes, NaN, [1, 0]), (4, d, true, LT, yes, NaN, [0, 1]), (4, d, true, LT, no, Infinity, [1, 0]), (4, d, true, LT, no, Infinity, [0, 1]), (4, d, true, LT, no, NaN, [1, 0]), (4, d, true, LT, no, NaN, [0, 1]), (null, c, false, GT, yes, Infinity, [1, 0]), (null, c, false, GT, yes, Infinity, [0, 1]), (null, c, false, GT, yes, NaN, [1, 0]), (null, c, false, GT, yes, NaN, [0, 1]), (null, c, false, GT, no, Infinity, [1, 0]), (null, c, false, GT, no, Infinity, [0, 1]), (null, c, false, GT, no, NaN, [1, 0]), (null, c, false, GT, no, NaN, [0, 1]), (null, c, true, GT, yes, Infinity, [1, 0]), (null, c, true, GT, yes, Infinity, [0, 1]), (null, c, true, GT, yes, NaN, [1, 0]), (null, c, true, GT, yes, NaN, [0, 1]), (null, c, true, GT, no, Infinity, [1, 0]), (null, c, true, GT, no, Infinity, [0, 1]), (null, c, true, GT, no, NaN, [1, 0]), (null, c, true, GT, no, NaN, [0, 1]), (null, d, false, GT, yes, Infinity, [1, 0]), (null, d, false, GT, yes, Infinity, [0, 1]), (null, d, false, GT, yes, NaN, [1, 0]), (null, d, false, GT, yes, NaN, [0, 1]), (null, d, false, GT, no, Infinity, [1, 0]), (null, d, false, GT, no, Infinity, [0, 1]), (null, d, false, GT, no, NaN, [1, 0]), (null, d, false, GT, no, NaN, [0, 1]), (null, d, true, GT, yes, Infinity, [1, 0]), (null, d, true, GT, yes, Infinity, [0, 1]), (null, d, true, GT, yes, NaN, [1, 0]), (null, d, true, GT, yes, NaN, [0, 1]), (null, d, true, GT, no, Infinity, [1, 0]), (null, d, true, GT, no, Infinity, [0, 1]), (null, d, true, GT, no, NaN, [1, 0]), (null, d, true, GT, no, NaN, [0, 1]), (4, c, false, GT, yes, Infinity, [1, 0]), (4, c, false, GT, yes, Infinity, [0, 1]), (4, c, false, GT, yes, NaN, [1, 0]), (4, c, false, GT, yes, NaN, [0, 1]), (4, c, false, GT, no, Infinity, [1, 0]), (4, c, false, GT, no, Infinity, [0, 1]), (4, c, false, GT, no, NaN, [1, 0]), (4, c, false, GT, no, NaN, [0, 1]), (4, c, true, GT, yes, Infinity, [1, 0]), (4, c, true, GT, yes, Infinity, [0, 1]), (4, c, true, GT, yes, NaN, [1, 0]), (4, c, true, GT, yes, NaN, [0, 1]), (4, c, true, GT, no, Infinity, [1, 0]), (4, c, true, GT, no, Infinity, [0, 1]), (4, c, true, GT, no, NaN, [1, 0]), (4, c, true, GT, no, NaN, [0, 1]), (4, d, false, GT, yes, Infinity, [1, 0]), (4, d, false, GT, yes, Infinity, [0, 1]), (4, d, false, GT, yes, NaN, [1, 0]), (4, d, false, GT, yes, NaN, [0, 1]), (4, d, false, GT, no, Infinity, [1, 0]), (4, d, false, GT, no, Infinity, [0, 1]), (4, d, false, GT, no, NaN, [1, 0]), (4, d, false, GT, no, NaN, [0, 1]), (4, d, true, GT, yes, Infinity, [1, 0]), (4, d, true, GT, yes, Infinity, [0, 1]), (4, d, true, GT, yes, NaN, [1, 0]), (4, d, true, GT, yes, NaN, [0, 1]), (4, d, true, GT, no, Infinity, [1, 0]), (4, d, true, GT, no, Infinity, [0, 1]), (4, d, true, GT, no, NaN, [1, 0]), (4, d, true, GT, no, NaN, [0, 1])]");
        aeq(septuples(
                new ArrayList<Integer>(),
                fromString("abcd"),
                P.booleans(),
                P.orderings(),
                Arrays.asList("yes", "no"),
                Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                Arrays.asList(x, y)
        ), "[]");
        aeq(septuples(
                new ArrayList<Integer>(),
                new ArrayList<Character>(),
                new ArrayList<Boolean>(),
                new ArrayList<Ordering>(),
                new ArrayList<String>(),
                new ArrayList<Float>(),
                new ArrayList<List<Integer>>()
        ), "[]");
        aeq(take(20, septuples(
                        P.naturalBigIntegers(),
                        fromString("abcd"),
                        P.booleans(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        (List<Float>) Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                )),
                "[(0, a, false, EQ, yes, Infinity, [1, 0]), (0, a, false, EQ, yes, Infinity, [0, 1])," +
                " (0, a, false, EQ, yes, NaN, [1, 0]), (0, a, false, EQ, yes, NaN, [0, 1])," +
                " (0, a, false, EQ, no, Infinity, [1, 0]), (0, a, false, EQ, no, Infinity, [0, 1])," +
                " (0, a, false, EQ, no, NaN, [1, 0]), (0, a, false, EQ, no, NaN, [0, 1])," +
                " (0, a, false, LT, yes, Infinity, [1, 0]), (0, a, false, LT, yes, Infinity, [0, 1])," +
                " (0, a, false, LT, yes, NaN, [1, 0]), (0, a, false, LT, yes, NaN, [0, 1])," +
                " (0, a, false, LT, no, Infinity, [1, 0]), (0, a, false, LT, no, Infinity, [0, 1])," +
                " (0, a, false, LT, no, NaN, [1, 0]), (0, a, false, LT, no, NaN, [0, 1])," +
                " (0, a, true, EQ, yes, Infinity, [1, 0]), (0, a, true, EQ, yes, Infinity, [0, 1])," +
                " (0, a, true, EQ, yes, NaN, [1, 0]), (0, a, true, EQ, yes, NaN, [0, 1])]");
        aeq(take(20, septuples(
                        fromString("abcd"),
                        P.booleans(),
                        P.naturalBigIntegers(),
                        P.orderings(),
                        Arrays.asList("yes", "no"),
                        Arrays.asList(Float.POSITIVE_INFINITY, Float.NaN),
                        Arrays.asList(x, y)
                )),
                "[(a, false, 0, EQ, yes, Infinity, [1, 0]), (a, false, 0, EQ, yes, Infinity, [0, 1])," +
                " (a, false, 0, EQ, yes, NaN, [1, 0]), (a, false, 0, EQ, yes, NaN, [0, 1])," +
                " (a, false, 0, EQ, no, Infinity, [1, 0]), (a, false, 0, EQ, no, Infinity, [0, 1])," +
                " (a, false, 0, EQ, no, NaN, [1, 0]), (a, false, 0, EQ, no, NaN, [0, 1])," +
                " (a, false, 0, LT, yes, Infinity, [1, 0]), (a, false, 0, LT, yes, Infinity, [0, 1])," +
                " (a, false, 0, LT, yes, NaN, [1, 0]), (a, false, 0, LT, yes, NaN, [0, 1])," +
                " (a, false, 0, LT, no, Infinity, [1, 0]), (a, false, 0, LT, no, Infinity, [0, 1])," +
                " (a, false, 0, LT, no, NaN, [1, 0]), (a, false, 0, LT, no, NaN, [0, 1])," +
                " (a, false, 1, EQ, yes, Infinity, [1, 0]), (a, false, 1, EQ, yes, Infinity, [0, 1])," +
                " (a, false, 1, EQ, yes, NaN, [1, 0]), (a, false, 1, EQ, yes, NaN, [0, 1])]");
        aeq(take(20, septuples(
                        P.positiveBigIntegers(),
                        P.negativeBigIntegers(),
                        P.characters(),
                        P.strings(),
                        P.floats(),
                        P.lists(P.integers()),
                        P.bigDecimals()
                )),
                "[(1, -1, a, , NaN, [], 0), (1, -1, a, , NaN, [], 0.0), (1, -1, a, , NaN, [0], 0)," +
                " (1, -1, a, , NaN, [0], 0.0), (1, -1, a, , Infinity, [], 0), (1, -1, a, , Infinity, [], 0.0)," +
                " (1, -1, a, , Infinity, [0], 0), (1, -1, a, , Infinity, [0], 0.0), (1, -1, a, a, NaN, [], 0)," +
                " (1, -1, a, a, NaN, [], 0.0), (1, -1, a, a, NaN, [0], 0), (1, -1, a, a, NaN, [0], 0.0)," +
                " (1, -1, a, a, Infinity, [], 0), (1, -1, a, a, Infinity, [], 0.0), (1, -1, a, a, Infinity, [0], 0)," +
                " (1, -1, a, a, Infinity, [0], 0.0), (1, -1, b, , NaN, [], 0), (1, -1, b, , NaN, [], 0.0)," +
                " (1, -1, b, , NaN, [0], 0), (1, -1, b, , NaN, [0], 0.0)]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
