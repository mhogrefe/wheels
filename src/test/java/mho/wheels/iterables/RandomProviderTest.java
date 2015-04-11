package mho.wheels.iterables;

import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Test;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

public class RandomProviderTest {
    private static final RandomProvider P = new RandomProvider(0x6af477d9a7e54fcaL);
    private static final int DEFAULT_SAMPLE_SIZE = 1000000;
    private static final int DEFAULT_TOP_COUNT = 10;

    @Test
    public void testConstructor() {

    }

    private static <T> void simpleProviderHelper(
            @NotNull Iterable<T> xs,
            @NotNull String output,
            @NotNull String sampleCountOutput
    ) {
        aeqit(take(20, xs), output);
        aeqit(sampleCount(DEFAULT_SAMPLE_SIZE, xs).entrySet(), sampleCountOutput);
    }

    @Test
    public void testBooleans() {
        simpleProviderHelper(
                P.booleans(),
                "[false, false, false, true, false, true, true, false, true, true, false, false, false, true, true," +
                " false, false, true, true, false]",
                "[false=500122, true=499878]"
        );
    }

    @Test
    public void testOrderings() {
        simpleProviderHelper(
                P.orderings(),
                "[EQ, GT, EQ, EQ, EQ, GT, LT, GT, LT, EQ, EQ, LT, LT, GT, EQ, LT, LT, LT, GT, EQ]",
                "[EQ=333219, GT=333296, LT=333485]"
        );
    }

    @Test
    public void testRoundingModes() {
        simpleProviderHelper(
                P.roundingModes(),
                "[HALF_UP, HALF_UP, HALF_UP, DOWN, HALF_DOWN, UP, DOWN, CEILING, UNNECESSARY, HALF_DOWN, HALF_DOWN," +
                " CEILING, FLOOR, HALF_DOWN, UNNECESSARY, FLOOR, FLOOR, HALF_EVEN, UP, HALF_DOWN]",
                "[HALF_UP=125006, DOWN=125075, HALF_DOWN=124925, UP=124892, CEILING=125057, UNNECESSARY=125002," +
                " FLOOR=125061, HALF_EVEN=124982]"
        );
    }

    private static void geometricHelper(
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        aeqit(take(20, xs), output);
        aeq(topSampleCount(DEFAULT_SAMPLE_SIZE, DEFAULT_TOP_COUNT, xs), topSampleCount);
        aeq(meanOfIntegers(xs), sampleMean);
    }

    private static void naturalIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.naturalIntegersGeometric(mean), output, topSampleCount, sampleMean);
    }

    @Test
    @Ignore
    public void testNaturalIntegersGeometric() {
        naturalIntegersGeometric_helper(0,
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
                "{0=1000000}",
                0.0
        );
        naturalIntegersGeometric_helper(
                1,
                "[0, 0, 0, 2, 0, 1, 2, 0, 2, 0, 0, 3, 0, 0, 0, 0, 4, 0, 1, 0]",
                "{0=498731, 1=250297, 2=125336, 3=62592, 4=31500, 5=15746, 6=8002, 7=3890, 8=1922, 9=981}",
                1.003892999997649
        );
        naturalIntegersGeometric_helper(
                2,
                "[2, 2, 0, 1, 3, 2, 0, 5, 1, 0, 7, 0, 0, 5, 0, 0, 0, 2, 0, 4]",
                "{0=333030, 1=222156, 2=148589, 3=98029, 4=66010, 5=44029, 6=29428, 7=19470, 8=13078, 9=8797}",
                2.003356999989073
        );
        naturalIntegersGeometric_helper(
                3,
                "[5, 0, 1, 3, 2, 0, 7, 0, 8, 7, 1, 2, 0, 7, 5, 5, 1, 1, 3, 0]",
                "{0=249593, 1=187157, 2=140530, 3=105260, 4=79490, 5=59545, 6=44447, 7=33455, 8=24992, 9=18829}",
                3.007273999991544
        );
        naturalIntegersGeometric_helper(
                4,
                "[5, 0, 1, 3, 3, 7, 17, 1, 2, 0, 7, 5, 5, 1, 5, 0, 1, 5, 0, 1]",
                "{0=199675, 1=160080, 2=127838, 3=102046, 4=82544, 5=65842, 6=52302, 7=42109, 8=33370, 9=26704}",
                4.002147999991753
        );
        naturalIntegersGeometric_helper(
                5,
                "[5, 0, 1, 3, 3, 7, 17, 4, 0, 7, 5, 5, 7, 2, 5, 0, 1, 0, 0, 3]",
                "{0=166772, 1=138747, 2=115323, 3=96516, 4=80590, 5=67087, 6=55738, 7=46574, 8=38599, 9=32319}",
                5.004303000005186
        );
        naturalIntegersGeometric_helper(
                10,
                "[5, 0, 1, 7, 7, 23, 7, 5, 5, 7, 8, 0, 1, 1, 3, 7, 19, 14, 6, 2]",
                "{0=91337, 1=82350, 2=75328, 3=68014, 4=62162, 5=56226, 6=51001, 7=46587, 8=42624, 9=38253}",
                10.004593000004135
        );
        naturalIntegersGeometric_helper(
                100,
                "[5, 56, 5, 25, 52, 151, 183, 38, 88, 111, 16, 41, 95, 65, 21, 85, 32, 27, 23, 15]",
                "{0=10074, 2=9937, 1=9815, 3=9545, 4=9543, 5=9429, 9=9164, 6=9119, 7=9099, 10=9058}",
                99.98710199999887
        );
        naturalIntegersGeometric_helper(
                -4,
                "[5, 56, 5, 25, 52, 151, 183, 38, 88, 111, 16, 41, 95, 65, 21, 85, 32, 27, 23, 15]",
                "{0=10074, 2=9937, 1=9815, 3=9545, 4=9543, 5=9429, 9=9164, 6=9119, 7=9099, 10=9058}",
                99.98710199999887
        );
        try {
            P.naturalIntegersGeometric(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    private static void positiveIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.positiveIntegersGeometric(mean), output, topSampleCount, sampleMean);
    }


    private void positiveIntegersGeometric_fail(int meanSize) {
        try {
            P.positiveIntegersGeometric(meanSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPositiveIntegersGeometric() {
        positiveIntegersGeometric_helper(
                1,
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                "{1=1000000}",
                1.000000000007918 //rounding error
        );
        positiveIntegersGeometric_helper(
                2,
                "[1, 1, 1, 3, 1, 2, 3, 1, 3, 1, 1, 4, 1, 1, 1, 1, 5, 1, 2, 1]",
                "{1=498731, 2=250297, 3=125336, 4=62592, 5=31500, 6=15746, 7=8002, 8=3890, 9=1922, 10=981}",
                2.00389299998003
        );
        positiveIntegersGeometric_helper(
                3,
                "[3, 3, 1, 2, 4, 3, 1, 6, 2, 1, 8, 1, 1, 6, 1, 1, 1, 3, 1, 5]",
                "{1=333030, 2=222156, 3=148589, 4=98029, 5=66010, 6=44029, 7=29428, 8=19470, 9=13078, 10=8797}",
                3.003356999989909
        );
        positiveIntegersGeometric_helper(
                4,
                "[6, 1, 2, 4, 3, 1, 8, 1, 9, 8, 2, 3, 1, 8, 6, 6, 2, 2, 4, 1]",
                "{1=249593, 2=187157, 3=140530, 4=105260, 5=79490, 6=59545, 7=44447, 8=33455, 9=24992, 10=18829}",
                4.007273999990233);
        positiveIntegersGeometric_helper(
                5,
                "[6, 1, 2, 4, 4, 8, 18, 2, 3, 1, 8, 6, 6, 2, 6, 1, 2, 6, 1, 2]",
                "{1=199675, 2=160080, 3=127838, 4=102046, 5=82544, 6=65842, 7=52302, 8=42109, 9=33370, 10=26704}",
                5.002148000008636
        );
        positiveIntegersGeometric_helper(
                10,
                "[6, 1, 2, 8, 8, 24, 8, 6, 6, 8, 9, 1, 2, 2, 4, 8, 20, 15, 7, 3]",
                "{1=100343, 2=89755, 3=81299, 4=72632, 5=65775, 6=58756, 7=52964, 8=47987, 9=42962, 10=38405}",
                10.002680000005894
        );
        positiveIntegersGeometric_helper(
                100,
                "[6, 57, 6, 26, 53, 152, 184, 39, 89, 112, 17, 42, 96, 66, 22, 86, 33, 28, 24, 16]",
                "{1=10197, 3=9995, 2=9909, 4=9645, 5=9638, 6=9529, 10=9264, 7=9236, 8=9183, 11=9132}",
                100.00996300000024
        );
        positiveIntegersGeometric_fail(0);
        positiveIntegersGeometric_fail(-5);
    }

    private static void negativeIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.negativeIntegersGeometric(mean), output, topSampleCount, sampleMean);
    }


    private void negativeIntegersGeometric_fail(int meanSize) {
        try {
            P.negativeIntegersGeometric(meanSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testNegativeIntegersGeometric() {
        negativeIntegersGeometric_helper(
                -1,
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]",
                "{-1=1000000}",
                -1.000000000007918 //rounding error
        );
        negativeIntegersGeometric_helper(
                -2,
                "[-1, -1, -1, -3, -1, -2, -3, -1, -3, -1, -1, -4, -1, -1, -1, -1, -5, -1, -2, -1]",
                "{-1=498731, -2=250297, -3=125336, -4=62592, -5=31500, -6=15746, -7=8002, -8=3890, -9=1922, -10=981}",
                -2.00389299998003
        );
        negativeIntegersGeometric_helper(
                -3,
                "[-3, -3, -1, -2, -4, -3, -1, -6, -2, -1, -8, -1, -1, -6, -1, -1, -1, -3, -1, -5]",
                "{-1=333030, -2=222156, -3=148589, -4=98029, -5=66010, -6=44029, -7=29428, -8=19470, -9=13078," +
                " -10=8797}",
                -3.003356999989909
        );
        negativeIntegersGeometric_helper(
                -4,
                "[-6, -1, -2, -4, -3, -1, -8, -1, -9, -8, -2, -3, -1, -8, -6, -6, -2, -2, -4, -1]",
                "{-1=249593, -2=187157, -3=140530, -4=105260, -5=79490, -6=59545, -7=44447, -8=33455, -9=24992," +
                " -10=18829}",
                -4.007273999990233);
        negativeIntegersGeometric_helper(
                -5,
                "[-6, -1, -2, -4, -4, -8, -18, -2, -3, -1, -8, -6, -6, -2, -6, -1, -2, -6, -1, -2]",
                "{-1=199675, -2=160080, -3=127838, -4=102046, -5=82544, -6=65842, -7=52302, -8=42109, -9=33370," +
                " -10=26704}",
                -5.002148000008636
        );
        negativeIntegersGeometric_helper(
                -10,
                "[-6, -1, -2, -8, -8, -24, -8, -6, -6, -8, -9, -1, -2, -2, -4, -8, -20, -15, -7, -3]",
                "{-1=100343, -2=89755, -3=81299, -4=72632, -5=65775, -6=58756, -7=52964, -8=47987, -9=42962," +
                " -10=38405}",
                -10.002680000005894
        );
        negativeIntegersGeometric_helper(
                -100,
                "[-6, -57, -6, -26, -53, -152, -184, -39, -89, -112, -17, -42, -96, -66, -22, -86, -33, -28, -24," +
                        " -16]",
                "{-1=10197, -3=9995, -2=9909, -4=9645, -5=9638, -6=9529, -10=9264, -7=9236, -8=9183, -11=9132}",
                -100.00996300000024
        );
        negativeIntegersGeometric_fail(0);
        negativeIntegersGeometric_fail(5);
    }

    private static void nonzeroIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = P.nonzeroIntegersGeometric(mean);
        geometricHelper(xs, output, topSampleCount, sampleMean);
        aeq(meanOfIntegers(map(Math::abs, xs)), sampleAbsMean);
    }


    private void nonzeroIntegersGeometric_fail(int meanSize) {
        try {
            P.nonzeroIntegersGeometric(meanSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testNonzeroIntegersGeometric() {
        nonzeroIntegersGeometric_helper(
                1,
                "[-1, -1, -1, 1, -1, 1, 1, -1, 1, 1, -1, -1, -1, 1, 1, -1, -1, 1, 1, -1]",
                "{-1=500122, 1=499878}",
                -2.439999999999994E-4,
                1.000000000007918 //rounding error
        );
        nonzeroIntegersGeometric_helper(
                2,
                "[-1, -1, -1, 3, -1, 2, 3, -1, 3, 1, -1, -4, -1, 1, 1, -1, -5, 1, 2, -1]",
                "{1=249397, -1=249334, -2=125532, 2=124765, -3=62734, 3=62602, 4=31581, -4=31011, 5=15775, -5=15725}",
                4.610000000000048E-4,
                2.00389299998003
        );
        nonzeroIntegersGeometric_helper(
                3,
                "[-3, -3, -1, 2, -4, 3, 1, -6, 2, 1, -8, -1, -1, 6, 1, -1, -1, 3, 1, -5]",
                "{1=166856, -1=166174, -2=111318, 2=110838, 3=74330, -3=74259, -4=49170, 4=48859, 5=33025, -5=32985}",
                -0.003314999999999991,
                3.003356999989909
        );
        nonzeroIntegersGeometric_helper(
                4,
                "[-6, -1, -2, 4, -3, 1, 8, -1, 9, 8, -2, -3, -1, 8, 6, -6, -2, 2, 4, -1]",
                "{-1=124963, 1=124630, -2=93780, 2=93377, 3=70541, -3=69989, -4=52748, 4=52512, -5=39799, 5=39691}",
                0.0041719999999999275,
                4.007273999990233
        );
        nonzeroIntegersGeometric_helper(
                5,
                "[-6, -1, -2, 4, -4, 8, 18, -2, 3, 1, -8, -6, -6, 2, 6, -1, -2, 6, 1, -2]",
                "{-1=99902, 1=99773, 2=80317, -2=79763, 3=63969, -3=63869, 4=51064, -4=50982, -5=41291, 5=41253}",
                -0.007923999999999843,
                5.002148000008636
        );
        nonzeroIntegersGeometric_helper(
                10,
                "[-6, -1, -2, 8, -8, 24, 8, -6, 6, 8, -9, -1, -2, 2, 4, -8, -20, 15, 7, -3]",
                "{1=50233, -1=50110, 2=44897, -2=44858, -3=40707, 3=40592, -4=36359, 4=36273, 5=33078, -5=32697}",
                -0.011589999999999732,
                10.002680000005894
        );
        nonzeroIntegersGeometric_helper(
                100,
                "[-6, -57, -6, 26, -53, 152, 184, -39, 89, 112, -17, -42, -96, 66, 22, -86, -33, 28, 24, -16]",
                "{1=5124, -1=5073, -3=5027, -2=4989, 3=4968, 2=4920, -5=4832, -4=4831, 4=4814, 5=4806}",
                0.09591699999999705,
                100.00996300000024
        );
        nonzeroIntegersGeometric_fail(0);
        nonzeroIntegersGeometric_fail(-5);
    }

    private static void integersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = P.integersGeometric(mean);
        geometricHelper(xs, output, topSampleCount, sampleMean);
        aeq(meanOfIntegers(map(Math::abs, xs)), sampleAbsMean);
    }


    private void integersGeometric_fail(int meanSize) {
        try {
            P.integersGeometric(meanSize);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    @Ignore
    public void testIntegersGeometric() {
        integersGeometric_helper(
                0,
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
                "{0=1000000}",
                0.0,
                0.0
        );
        integersGeometric_helper(
                1,
                "[-1, -1, -1, 0, -1, 0, 0, -1, 2, 0, -1, -1, -1, 1, 2, -1, -1, 0, 2, -1]",
                "{-1=1000000}",
                -1.000000000007918, //rounding error
                0.0
        );
        integersGeometric_helper(
                2,
                "[-1, -1, -1, -3, -1, -2, -3, -1, -3, -1, -1, -4, -1, -1, -1, -1, -5, -1, -2, -1]",
                "{-1=498731, -2=250297, -3=125336, -4=62592, -5=31500, -6=15746, -7=8002, -8=3890, -9=1922, -10=981}",
                -2.00389299998003,
                0.0
        );
        integersGeometric_helper(
                3,
                "[-3, -3, -1, -2, -4, -3, -1, -6, -2, -1, -8, -1, -1, -6, -1, -1, -1, -3, -1, -5]",
                "{-1=333030, -2=222156, -3=148589, -4=98029, -5=66010, -6=44029, -7=29428, -8=19470, -9=13078," +
                        " -10=8797}",
                -3.003356999989909,
                0.0
        );
        integersGeometric_helper(
                4,
                "[-6, -1, -2, -4, -3, -1, -8, -1, -9, -8, -2, -3, -1, -8, -6, -6, -2, -2, -4, -1]",
                "{-1=249593, -2=187157, -3=140530, -4=105260, -5=79490, -6=59545, -7=44447, -8=33455, -9=24992," +
                        " -10=18829}",
                -4.007273999990233,
                0.0
        );
        integersGeometric_helper(
                5,
                "[-6, -1, -2, -4, -4, -8, -18, -2, -3, -1, -8, -6, -6, -2, -6, -1, -2, -6, -1, -2]",
                "{-1=199675, -2=160080, -3=127838, -4=102046, -5=82544, -6=65842, -7=52302, -8=42109, -9=33370," +
                        " -10=26704}",
                -5.002148000008636,
                0.0
        );
        integersGeometric_helper(
                10,
                "[-6, -1, -2, -8, -8, -24, -8, -6, -6, -8, -9, -1, -2, -2, -4, -8, -20, -15, -7, -3]",
                "{-1=100343, -2=89755, -3=81299, -4=72632, -5=65775, -6=58756, -7=52964, -8=47987, -9=42962," +
                        " -10=38405}",
                -10.002680000005894,
                0.0
        );
        integersGeometric_helper(
                100,
                "[-6, -57, -6, -26, -53, -152, -184, -39, -89, -112, -17, -42, -96, -66, -22, -86, -33, -28, -24," +
                        " -16]",
                "{-1=10197, -3=9995, -2=9909, -4=9645, -5=9638, -6=9529, -10=9264, -7=9236, -8=9183, -11=9132}",
                -100.00996300000024,
                0.0
        );
        integersGeometric_fail(0);
        integersGeometric_fail(5);
    }

    @Test
    public void testPositiveBytes() {
        aeqit(take(20, P.positiveBytes()),
                "[18, 88, 104, 6, 29, 29, 13, 13, 52, 56, 113, 52, 57, 71, 91, 59, 64, 66, 1, 26]");
    }

    @Test
    @Ignore
    public void testPositiveShorts() {
        aeqit(take(20, P.positiveShorts()),
                "[29559, 24497, 25521, 7601, 15393, 17782, 25443, 13977, 19844, 10977," +
                " 6993, 15895, 13568, 24091, 18433, 27279, 26356, 29039, 23271, 17273]");
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(20, P.positiveIntegers()),
                "[838088639, 328782387, 1023352007, 1288498826, 596019284, 1251925389, 1626530542, 872910510," +
                " 1604720617, 1168045472, 1535924, 405543310, 124070167, 1385792448, 1255589909, 742314043," +
                " 149049931, 1532599744, 1364960949, 740140532]");
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(20, P.positiveLongs()),
                "[7199126587670897765, 8790526798708989202, 5119766654749621017, 4474953112883356325," +
                " 4662298945794479298, 13193478919435035, 1065754609235199870, 7661308888129625996," +
                " 1280329148412410751, 6721818803979990042, 4947586878887127432, 7082175163031428867," +
                " 2686229925919009542, 602148044581217176, 2626034444874753984, 4428434850933104875," +
                " 926106523787202568, 7729238571771020305, 7971237095602901340, 2602655955979596862]");
    }

    private void positiveBigIntegers_int_helper(int meanBitSize, @NotNull String output) {
        aeqit(take(20, P.withScale(meanBitSize).positiveBigIntegers()), output);
    }

    @Test
    @Ignore
    public void testPositiveBigIntegers_Int() {
        positiveBigIntegers_int_helper(3, "[15, 1, 7, 3, 1, 2, 8, 1, 13, 5, 20, 2, 1, 1, 1, 1, 1, 1, 3, 1]");
        positiveBigIntegers_int_helper(4, "[1, 1, 1, 6, 4, 94, 59, 4, 1, 1, 1, 43, 15, 1, 3, 1, 2, 103103, 393, 12]");
        positiveBigIntegers_int_helper(5, "[1, 2, 2821, 1, 13, 1, 273, 1, 3, 3, 1, 3, 15, 2, 6, 14, 5, 7, 1, 1]");
        positiveBigIntegers_int_helper(10,
                "[418, 1, 886, 15, 2, 1023538995542242, 2527383, 11, 2, 3411, 10, 4891, 8, 2, 25, 3, 10, 349," +
                        " 110732294, 3877]");
        positiveBigIntegers_int_helper(100,
                "[631847851262602872164, 62178362933629457256170097449498832870026795417, 547758176," +
                        " 2346149950119691144404, 311, 4742738, 67302549518065217887062796935441749979, 53471, 4223," +
                        " 17312403, 316463874199, 6, 447122575, 1176, 704610823827," +
                        " 31430331193008341986440693101333088795173295035345951291600655076040609838446721240723225651953502" +
                        "51261283498014102904063, 7517586777550828054626795662503, 741109, 101419744017795180979313623318," +
                        " 25612091393]");
        try {
            P.withScale(2).positiveBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(0).positiveBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(-4).positiveBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    @Ignore
    public void testPositiveBigIntegers() {
        aeqit(take(20, P.positiveBigIntegers()),
                "[65649474733, 50, 1752003, 108680047959250986, 2, 169829217569110637456607575012447814909456," +
                " 8046132249267142822265255, 78549137, 3080," +
                " 6955247343603701669934693326084685760295830262297267296665, 547758176, 2133810949," +
                " 547945394950967, 4742738, 27183283269, 1631119, 1811559053982367, 595931, 13367, 20607]");
    }

    @Test
    @Ignore
    public void testNegativeBytes() {
        aeqit(take(20, P.negativeBytes()),
                "[-105, -128, -85, -93, -2, -84, -76, -46, -109, -36," +
                " -36, -44, -11, -68, -100, -111, -79, -10, -124, -62]");
    }

    @Test
    @Ignore
    public void testNegativeShorts() {
        aeqit(take(20, P.negativeShorts()),
                "[-26695, -32710, -21548, -23768, -417, -21442, -19264, -11625, -27827, -9074," +
                " -8998, -11245, -2562, -17159, -25537, -28322, -20208, -2536, -31687, -15831]");
    }

    @Test
    @Ignore
    public void testNegativeIntegers() {
        aeqit(take(20, P.negativeIntegers()),
                "[-796047920, -7618489, -1470662991, -1179662338, -1484637212, -1770087994, -647698373, -2045988837," +
                " -947846915, -582804843, -1646292442, -141769040, -1282994601, -213515457, -1321491010, -767780260," +
                " -1996736455, -2015128232, -1916151903, -81932857]");
    }

    @Test
    @Ignore
    public void testNegativeLongs() {
        aeqit(take(20, P.negativeLongs()),
                "[-3418999782456442809, -6316449450962204674, -7602470043748550772, -2781843328518141957," +
                " -4070971502122296683, -7070772197709661375, -608893388310409322, -3297591106051422579," +
                " -8575917774971103912, -8229809756225242051, -351898943428221388, -5035479974893608156," +
                " -2087360320830562347, -4864443654894485421, -3626293116983244765, -6128126907599458534," +
                " -4181052272311759209, -1935017808723883567, -3861844328646811360, -196660781681668032]");
    }

    private void negativeBigIntegers_intHelper(int meanBitSize, @NotNull String output) {
        aeqit(take(20, P.withScale(meanBitSize).negativeBigIntegers()), output);
    }

    @Test
    @Ignore
    public void testNegativeBigIntegers_Int() {
        negativeBigIntegers_intHelper(3,
                "[-15, -1, -7, -3, -1, -2, -8, -1, -13, -5, -20, -2, -1, -1, -1, -1, -1, -1, -3, -1]");
        negativeBigIntegers_intHelper(4,
                "[-1, -1, -1, -6, -4, -94, -59, -4, -1, -1, -1, -43, -15, -1, -3, -1, -2, -103103, -393, -12]");
        negativeBigIntegers_intHelper(5,
                "[-1, -2, -2821, -1, -13, -1, -273, -1, -3, -3, -1, -3, -15, -2, -6, -14, -5, -7, -1, -1]");
        negativeBigIntegers_intHelper(10,
                "[-418, -1, -886, -15, -2, -1023538995542242, -2527383, -11, -2, -3411, -10, -4891, -8, -2, -25, -3," +
                        " -10, -349, -110732294, -3877]");
        negativeBigIntegers_intHelper(100,
                "[-631847851262602872164, -62178362933629457256170097449498832870026795417, -547758176," +
                " -2346149950119691144404, -311, -4742738, -67302549518065217887062796935441749979, -53471, -4223," +
                " -17312403, -316463874199, -6, -447122575, -1176, -704610823827," +
                " -3143033119300834198644069310133308879517329503534595129160065507604060983844672124072322565195350" +
                "251261283498014102904063, -7517586777550828054626795662503, -741109," +
                " -101419744017795180979313623318, -25612091393]");
        try {
            P.withScale(2).negativeBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(0).negativeBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(-4).negativeBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    @Ignore
    public void testNegativeBigIntegers() {
        aeqit(take(20, P.negativeBigIntegers()),
                "[-65649474733, -50, -1752003, -108680047959250986, -2, -169829217569110637456607575012447814909456," +
                " -8046132249267142822265255, -78549137, -3080," +
                " -6955247343603701669934693326084685760295830262297267296665, -547758176, -2133810949," +
                " -547945394950967, -4742738, -27183283269, -1631119, -1811559053982367, -595931, -13367, -20607]");
    }

    @Test
    @Ignore
    public void testNaturalBytes() {
        aeqit(take(20, P.naturalBytes()),
                "[80, 71, 49, 126, 65, 100, 70, 12, 59, 123, 11, 121, 120, 27, 125, 21, 38, 65, 48, 22]");
    }

    @Test
    @Ignore
    public void testNaturalShorts() {
        aeqit(take(20, P.naturalShorts()),
                "[17872, 16455, 30385, 18430, 29121, 15332, 6598, 14220, 26683, 18427," +
                " 10763, 19577, 16888, 12315, 253, 6805, 4646, 15169, 18096, 3990]");
    }

    @Test
    @Ignore
    public void testNaturalIntegers() {
        aeqit(take(20, P.naturalIntegers()),
                "[1351435728, 2139865159, 676820657, 967821310, 54555073, 662846436, 377395654, 1523693452," +
                " 1499785275, 1189267451, 1179298315, 1473891449, 335757816, 101494811, 1199636733, 1564678805," +
                " 501191206, 332315457, 2005714608, 2074906518]");
    }

    @Test
    @Ignore
    public void testNaturalLongs() {
        aeqit(take(20, P.naturalLongs()),
                "[5804372254398332999, 2906922585892571134, 234312252881255396, 1620901993106225036," +
                " 6441528708336633851, 5065047696626797689, 1442068837050396699, 5152400534732479125," +
                " 2152599839145114433, 8614478648544366486, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, 5925780930803353229, 5761072552197714005," +
                " 647454261883671896, 993562280629533757, 8871473093426554420, 2417358956864889798]");
    }

    private void naturalBigIntegers_intHelper(int meanBitSize, @NotNull String output) {
        aeqit(take(20, P.withScale(meanBitSize).naturalBigIntegers()), output);
    }

    @Test
    @Ignore
    public void testNaturalBigIntegers_Int() {
        naturalBigIntegers_intHelper(3, "[7, 0, 3, 1, 0, 0, 0, 0, 5, 1, 4, 0, 0, 0, 0, 0, 0, 0, 1, 0]");
        naturalBigIntegers_intHelper(4, "[0, 0, 0, 2, 0, 30, 27, 0, 0, 0, 0, 11, 7, 0, 1, 0, 0, 37567, 137, 4]");
        naturalBigIntegers_intHelper(5, "[0, 0, 773, 0, 5, 0, 17, 0, 1, 1, 0, 1, 7, 0, 2, 6, 1, 3, 0, 0]");
        naturalBigIntegers_intHelper(10,
                "[162, 0, 374, 7, 0, 460589042120930, 430231, 3, 0, 1363, 2, 795, 0, 0, 9, 1, 2, 93, 43623430, 1829]");
        naturalBigIntegers_intHelper(100,
                "[41552040903897220452, 16506436767038741062304946427114988505778903449, 10887264," +
                " 1165558329402279840980, 55, 548434, 24767253652947909954140971006470723547, 20703, 127, 535187," +
                " 41585967255, 2, 178687119, 152, 154855009939," +
                " 56078324121392560898815013813029700518762371070537161632940615106341336182783092944267721191521241" +
                "9825380326041355410687, 2446984376637910448639982840999, 216821, 22191581503530843385769672982," +
                " 8432222209]");
        try {
            P.withScale(2).naturalBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(0).naturalBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(-4).naturalBigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    @Ignore
    public void testNaturalBigIntegers() {
        aeqit(take(20, P.naturalBigIntegers()),
                "[31289736365, 18, 703427, 36622453921323050, 0, 82716931637350390809983675509915152776720," +
                " 3210428970808626123440551, 11440273, 1032," +
                " 678145608217020906098903902877019344193474817833232783769, 10887264, 1060069125, 266470418240311," +
                " 548434, 10003414085, 582543, 685659147139743, 71643, 5175, 4223]");
    }

    @Test
    @Ignore
    public void testBytes() {
        aeqit(take(20, P.bytes()),
                "[-48, 71, -79, -2, -63, -28, -58, -116, 59, -5, 11, 121, -8, 27, -3, -107, 38, 65, -80, -106]");
    }

    @Test
    @Ignore
    public void testShorts() {
        aeqit(take(20, P.shorts()),
                "[17872, -16313, 30385, -14338, 29121, 15332, -26170, -18548, -6085, -14341," +
                " -22005, -13191, 16888, -20453, 253, 6805, -28122, -17599, -14672, -28778]");
    }

    @Test
    @Ignore
    public void testIntegers() {
        aeqit(take(20, P.integers()),
                "[-796047920, -7618489, -1470662991, -1179662338, 54555073, -1484637212, -1770087994, 1523693452," +
                " -647698373, 1189267451, 1179298315, 1473891449, 335757816, -2045988837, -947846915, -582804843," +
                " -1646292442, 332315457, -141769040, 2074906518]");
    }

    @Test
    @Ignore
    public void testLongs() {
        aeqit(take(20, P.longs()),
                "[-3418999782456442809, -6316449450962204674, 234312252881255396, -7602470043748550772," +
                " -2781843328518141957, 5065047696626797689, 1442068837050396699, -4070971502122296683," +
                " -7070772197709661375, -608893388310409322, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, -3297591106051422579, 5761072552197714005," +
                " -8575917774971103912, -8229809756225242051, -351898943428221388, 2417358956864889798]");
    }

    private void bigIntegers_intHelper(int meanBitSize, @NotNull String output) {
        aeqit(take(20, P.withScale(meanBitSize).bigIntegers()), output);
    }

    @Test
    @Ignore
    public void testBigIntegers_Int() {
        bigIntegers_intHelper(3, "[7, -1, 1, -1, 0, 0, -1, -6, -1, 6, -1, 0, -1, 0, -1, 1, 1, 1, 0, -1]");
        bigIntegers_intHelper(4, "[1, 1, -7, 4, -2, 0, 10, -1, 0, 0, -2, -8, -6, 3, 0, -1, 0, 0, -2, 4]");
        bigIntegers_intHelper(5, "[1, 773, 2, 0, 24, -10, -1, -2, -1, 1, -3, 3, -2, 7, 10, 2, 3271, 120, 11, 0]");
        bigIntegers_intHelper(10, "[-2, 11, -454, -19342463128, -3412, 13, -1, -55, 0, -4," +
                " 0, 3, 35, -1, -43623431, -8, 0, 19579, -29, 4]");
        bigIntegers_intHelper(100,
                "[-88557569903630551599799955827784349169626451040329715964314, 202, 60318599134," +
                " 1640702634687943479, -61191085979970053457695, 4254037577138942334193887, 12821954296221206544535," +
                " -1638087117977, 3, 582, 230, 16168191, 26, 51481126197039749041591204, -71523839508501956928333," +
                " 1325372505506602807026564, 3757547800543576, 4364599426705721714," +
                " 113847612089673464000064561451248807, -400979282943760427063214761070268927754993666]");
        try {
            P.withScale(2).bigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(0).bigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(-4).bigIntegers();
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    @Ignore
    public void testBigIntegers() {
        aeqit(take(20, P.bigIntegers()),
                "[31289736365, 1332686935725045463947306, -49775, -12910780249752364756422," +
                " -23944809563965594065693683811078439336, 0, 320784164," +
                " -88557569903630551599799955827784349169626451040329715964314, 202, 60318599134," +
                " 1640702634687943479, 30595542989985026728847, -1, -1063509394284735583548472, 1," +
                " 12821954296221206544535, 819043558988, -1, 3, 582]");
    }

    @Test
    @Ignore
    public void testAsciiCharacters() {
        aeq(charsToString(take(100, P.asciiCharacters())),
                "PG1~AdF\f;{\13yx\33}\25&A0\26zW\3?\n~\24>\\\rvU9X!=G4*FV:3ByK$$YU\32cE" +
                        "S\37#\f\32\34\27\16Q\13 \20@p:PN\t\30YDC\f\30Wp,.d72\6}K$*#`4RUYh>IE}");
    }

    @Test
    @Ignore
    public void testCharacters() {
        aeq(charsToString(take(100, P.characters())),
                "\u45d0\uc047\u76b1\uc7fe\u71c1\u3be4\u99c6\ub78c\ue83b\uc7fb\uaa0b\ucc79\u41f8\ub01b\u00fd\u1a95" +
                "\u9226\ubb41\uc6b0\u8f96\u907a\u0e57\ufc03\u033f\ud08a\u32fe\u7414\ua5be\u9a5c\u468d\u70f6\u7055" +
                "\u3839\u9558\ud7a1\u7c3d\ucdc7\u2634\u2f2a\u63c6\u0c56\ufa3a\udf33\u11c2\u5879\u04cb\u61a4\ua324" +
                "\u34d9\u1fd5\u5b9a\u3ee3\u0645\u8053\ud19f\u0823\u850c\u6f1a\ueb1c\u8e97\u6f8e\u49d1\uf90b\u6120" +
                "\u5210\u1040\ub3f0\ua6ba\u77d0\u01ce\ue509\ue198\u5959\u0f44\ueec3\u408c\u1c18\u24d7\ue870\u22ac" +
                "\uf72e\u9064\u4137\ub132\u9186\u07fd\u1e4b\ub3a4\uad2a\u14a3\ue860\u0234\ub3d2\ue555\ubc59\u3a68" +
                "\uf8be\uce49\ub345\u99fd");
    }

    @Test
    @Ignore
    public void testPositiveOrdinaryFloats() {
        aeqit(take(20, P.positiveOrdinaryFloats()),
                "[1.89613015E10, 1.1960635E-14, 3.3527607E-4, 5.655431E-37, 3.614718E-15, 2.0566479E-25," +
                " 2.9515041E16, 4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, 2.6460455E-35," +
                " 66049.98, 8.7866956E17, 5.9178722E-21, 5.2186357E-27, 5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testNegativeOrdinaryFloats() {
        aeqit(take(20, P.negativeOrdinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, -5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " -2.9515041E16, -4.02697717E15, -29027.99, -12970.511, -4.78944453E14, -6.62682E-27," +
                " -2.6460455E-35, -66049.98, -8.7866956E17, -5.9178722E-21, -5.2186357E-27, -5.710558E33," +
                " -1.7919747E36, -5.174596E-35]");
    }

    @Test
    @Ignore
    public void testOrdinaryFloats() {
        aeqit(take(20, P.ordinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testFloats() {
        aeqit(take(20, P.floats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testPositiveOrdinaryDoubles() {
        aeqit(take(20, P.positiveOrdinaryDoubles()),
                "[1.0846552561567438E80, 2.38197354700265E-114, 5.149568405861E-293, 2.4985843477357602E-200," +
                " 4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, 2.8252505782194046E36," +
                " 8.566220677325717E-165, 7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, 1.4224921830272466E88, 1.4069651251380964E77," +
                " 2.1879373410803317E-265, 3.027783021197556E-242, 1.1079692399020062E285, 4.408373100689709E-147]");
    }

    @Test
    @Ignore
    public void testNegativeOrdinaryDoubles() {
        aeqit(take(20, P.negativeOrdinaryDoubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, -5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, -4.225116780176157E30, -2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, -3.60536199254296E-277, -1.2019415773498463E252," +
                " -4.813417096972919E-51, -1.3135493453396428E-126, -1.4224921830272466E88, -1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " -4.408373100689709E-147]");
    }

    @Test
    @Ignore
    public void testOrdinaryDoubles() {
        aeqit(take(20, P.ordinaryDoubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, 5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, -1.4224921830272466E88, 1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " 4.408373100689709E-147]");
    }

    @Test
    @Ignore
    public void testDoubles() {
        aeqit(take(20, P.doubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, 5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, -1.4224921830272466E88, 1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " 4.408373100689709E-147]");
    }

    private static double meanOfIntegers(@NotNull Iterable<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, take(DEFAULT_SAMPLE_SIZE, xs)));
    }
}
