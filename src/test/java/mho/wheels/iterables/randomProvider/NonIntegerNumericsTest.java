package mho.wheels.iterables.randomProvider;

import mho.wheels.io.Readers;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class NonIntegerNumericsTest {
    private static RandomProvider P;

    @Before
    public void initialize() {
        P = RandomProvider.example();
    }

    private static void binaryFractionHelper(
            @NotNull Iterable<BinaryFraction> xs,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        List<BinaryFraction> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfBinaryFractions(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.getMantissa().abs().bitLength(), sample))), mantissaBitSizeMean);
        aeq(meanOfIntegers(toList(map(x -> Math.abs(x.getExponent()), sample))), exponentMean);
    }

    private static void positiveBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).positiveBinaryFractions(),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void positiveBinaryFractions_fail_helper(int meanMantissaBitSize, int meanExponentSize) {
        try {
            P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).positiveBinaryFractions();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPositiveBinaryFractions() {
        positiveBinaryFractions_helper(
                2,
                1,
                "RandomProvider_positiveBinaryFractions_i",
                667.4036101180055,
                2.0010259999798934,
                0.9987319999976897
        );
        positiveBinaryFractions_helper(
                5,
                3,
                "RandomProvider_positiveBinaryFractions_ii",
                5.338187744452398E14,
                5.00820000000873,
                3.0015849999914943
        );
        positiveBinaryFractions_helper(
                32,
                8,
                "RandomProvider_positiveBinaryFractions_iii",
                7.67364101496927E126,
                32.00852100002276,
                7.997832000016788
        );
        positiveBinaryFractions_helper(
                100,
                10,
                "RandomProvider_positiveBinaryFractions_iv",
                7.597222225943522E303,
                99.91674399999913,
                10.005905000005157
        );
        positiveBinaryFractions_fail_helper(1, 1);
        positiveBinaryFractions_fail_helper(2, 0);
    }

    private static void negativeBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).negativeBinaryFractions(),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void negativeBinaryFractions_fail_helper(int meanMantissaBitSize, int meanExponentSize) {
        try {
            P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).negativeBinaryFractions();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNegativeBinaryFractions() {
        negativeBinaryFractions_helper(
                2,
                1,
                "RandomProvider_negativeBinaryFractions_i",
                -667.4036101180055,
                2.0010259999798934,
                0.9987319999976897
        );
        negativeBinaryFractions_helper(
                5,
                3,
                "RandomProvider_negativeBinaryFractions_ii",
                -5.338187744452399E14,
                5.00820000000873,
                3.0015849999914943
        );
        negativeBinaryFractions_helper(
                32,
                8,
                "RandomProvider_negativeBinaryFractions_iii",
                -7.673641014969271E126,
                32.00852100002276,
                7.997832000016788
        );
        negativeBinaryFractions_helper(
                100,
                10,
                "RandomProvider_negativeBinaryFractions_iv",
                Double.NEGATIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        negativeBinaryFractions_fail_helper(1, 1);
        negativeBinaryFractions_fail_helper(2, 0);
    }

    private static void nonzeroBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).nonzeroBinaryFractions(),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void nonzeroBinaryFractions_fail_helper(int meanMantissaBitSize, int meanExponentSize) {
        try {
            P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).nonzeroBinaryFractions();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNonzeroBinaryFractions() {
        nonzeroBinaryFractions_helper(
                2,
                1,
                "RandomProvider_nonzeroBinaryFractions_i",
                -1658.0414945915677,
                1.999841999979959,
                0.9992589999977214
        );
        nonzeroBinaryFractions_helper(
                5,
                3,
                "RandomProvider_nonzeroBinaryFractions_ii",
                5.037488944944938E14,
                5.002909000008661,
                3.0022489999914326
        );
        nonzeroBinaryFractions_helper(
                32,
                8,
                "RandomProvider_nonzeroBinaryFractions_iii",
                -4.399722772552127E126,
                31.996296000023637,
                7.998090000016753
        );
        nonzeroBinaryFractions_helper(
                100,
                10,
                "RandomProvider_nonzeroBinaryFractions_iv",
                Double.NEGATIVE_INFINITY,
                99.89809500000158,
                10.005882000004926
        );
        nonzeroBinaryFractions_fail_helper(1, 1);
        nonzeroBinaryFractions_fail_helper(2, 0);
    }

    private static void binaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).binaryFractions(),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void binaryFractions_fail_helper(int meanMantissaBitSize, int meanExponentSize) {
        try {
            P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).binaryFractions();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBinaryFractions() {
        binaryFractions_helper(
                1,
                1,
                "RandomProvider_binaryFractions_i",
                -15141.716243090259,
                0.9597509999985684,
                1.0004139999980264
        );
        binaryFractions_helper(
                5,
                3,
                "RandomProvider_binaryFractions_ii",
                1.66610944996342963E18,
                4.954361000006467,
                3.007180999992695
        );
        binaryFractions_helper(
                32,
                8,
                "RandomProvider_binaryFractions_iii",
                6.447723197358738E125,
                32.013006000028064,
                7.994682000015932
        );
        binaryFractions_helper(
                100,
                10,
                "RandomProvider_binaryFractions_iv",
                Double.NEGATIVE_INFINITY,
                99.8870699999952,
                9.98571900000396
        );
        binaryFractions_fail_helper(1, 0);
        binaryFractions_fail_helper(0, 1);
    }

    private static void rangeUp_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(BinaryFraction.read(a).get()),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void rangeUp_BinaryFraction_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(BinaryFraction.read(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeUp_BinaryFraction() {
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "0",
                "RandomProvider_rangeUp_BinaryFraction_i",
                20405.38097776243,
                0.9597509999985684,
                1.0004139999980264
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeUp_BinaryFraction_ii",
                7.8980066065349693E18,
                4.954361000006467,
                3.007180999992695
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeUp_BinaryFraction_iii",
                6.4477251665470446E125,
                32.013006000028064,
                7.994682000015932
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "1",
                "RandomProvider_rangeUp_BinaryFraction_iv",
                20406.380977936788,
                2.2737089999847506,
                0.7358969999983083
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeUp_BinaryFraction_v",
                7.8980066065349693E18,
                7.159625000020588,
                1.8263119999926671
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeUp_BinaryFraction_vi",
                6.4477251665470446E125,
                36.763259000017705,
                4.203809999998741
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "11",
                "RandomProvider_rangeUp_BinaryFraction_vii",
                20416.3809777996,
                4.556464999980296,
                0.8102169999972612
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "11",
                "RandomProvider_rangeUp_BinaryFraction_viii",
                7.8980066065349693E18,
                8.511026999979812,
                1.8539969999931307
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "11",
                "RandomProvider_rangeUp_BinaryFraction_ix",
                6.4477251665470446E125,
                37.24737200001865,
                4.206839999998712
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_x",
                2.1401835644499287E10,
                3.7599269999773086,
                19.763109999771977
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_xi",
                8.281660175454012E24,
                8.010092000028635,
                18.854625999852182
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_xii",
                6.760929864237234E131,
                37.07091300000956,
                17.015772999850565
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xiii",
                0.019464856126887104,
                3.7599269999773086,
                20.237141999770692
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xiv",
                7.532126051459283E12,
                8.010092000028635,
                21.16858599984985
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xv",
                6.149029890582127E119,
                37.07091300000956,
                23.789788999822175
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-1",
                "RandomProvider_rangeUp_BinaryFraction_xvi",
                20404.38097764382,
                1.8874289999759444,
                0.601121999998552
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeUp_BinaryFraction_xvii",
                7.8980066065349683E18,
                6.7572410000217555,
                1.7570869999935141
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeUp_BinaryFraction_xviii",
                6.4477251665470446E125,
                36.58280200001777,
                4.194993999998781
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-11",
                "RandomProvider_rangeUp_BinaryFraction_xix",
                20394.380977771583,
                4.305438999999481,
                0.7516849999983759
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-11",
                "RandomProvider_rangeUp_BinaryFraction_xx",
                7.8980066065349683E18,
                8.077762999986804,
                1.8205389999923367
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-11",
                "RandomProvider_rangeUp_BinaryFraction_xxi",
                6.4477251665470446E125,
                37.10622500001412,
                4.201928999998755
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_xxii",
                2.1391349883171562E10,
                3.34172299998717,
                19.553109999772616
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_xxiii",
                8.281660175454011E24,
                7.570154000037757,
                18.615274999850975
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-5 << 20",
                "RandomProvider_rangeUp_BinaryFraction_xxiv",
                6.760929864237234E131,
                36.9378720000084,
                16.980748999850196
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xxv",
                0.019455319382515483,
                3.34172299998717,
                20.019541999790963
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xxvi",
                7.532126051459282E12,
                7.570154000037757,
                20.992976999858303
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-5 >> 20",
                "RandomProvider_rangeUp_BinaryFraction_xxvii",
                6.149029890582127E119,
                36.9378720000084,
                23.766452999822757
        );
        rangeUp_BinaryFraction_fail_helper(1, 0, "0");
        rangeUp_BinaryFraction_fail_helper(0, 1, "0");
    }

    private static void rangeDown_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(BinaryFraction.read(a).get()),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void rangeDown_BinaryFraction_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(BinaryFraction.read(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeDown_BinaryFraction() {
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "0",
                "RandomProvider_rangeDown_BinaryFraction_i",
                -20405.38097776243,
                0.9597509999985684,
                1.0004139999980264
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeDown_BinaryFraction_ii",
                -7.8980066065349693E18,
                4.954361000006467,
                3.007180999992695
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeDown_BinaryFraction_iii",
                -6.447725166547045E125,
                32.013006000028064,
                7.994682000015932
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "1",
                "RandomProvider_rangeDown_BinaryFraction_iv",
                -20404.38097764382,
                1.8874289999759444,
                0.601121999998552
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeDown_BinaryFraction_v",
                -7.8980066065349693E18,
                6.7572410000217555,
                1.7570869999935141
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeDown_BinaryFraction_vi",
                -6.447725166547045E125,
                36.58280200001777,
                4.194993999998781
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "11",
                "RandomProvider_rangeDown_BinaryFraction_vii",
                -20394.380977771583,
                4.305438999999481,
                0.7516849999983759
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "11",
                "RandomProvider_rangeDown_BinaryFraction_viii",
                -7.8980066065349693E18,
                8.077762999986804,
                1.8205389999923367
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "11",
                "RandomProvider_rangeDown_BinaryFraction_ix",
                -6.447725166547045E125,
                37.10622500001412,
                4.201928999998755
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_x",
                -2.1391349883171562E10,
                3.34172299998717,
                19.553109999772616
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_xi",
                -8.281660175454012E24,
                7.570154000037757,
                18.615274999850975
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_xii",
                -6.7609298642372346E131,
                36.9378720000084,
                16.980748999850196
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xiii",
                -0.019455319382515483,
                3.34172299998717,
                20.019541999790963
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xiv",
                -7.532126051459283E12,
                7.570154000037757,
                20.992976999858303
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xv",
                -6.149029890582128E119,
                36.9378720000084,
                23.766452999822757
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-1",
                "RandomProvider_rangeDown_BinaryFraction_xvi",
                -20406.380977936788,
                2.2737089999847506,
                0.7358969999983083
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeDown_BinaryFraction_xvii",
                -7.8980066065349693E18,
                7.159625000020588,
                1.8263119999926671
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeDown_BinaryFraction_xviii",
                -6.447725166547045E125,
                36.763259000017705,
                4.203809999998741
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-11",
                "RandomProvider_rangeDown_BinaryFraction_xix",
                -20416.3809777996,
                4.556464999980296,
                0.8102169999972612
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-11",
                "RandomProvider_rangeDown_BinaryFraction_xx",
                -7.8980066065349693E18,
                8.511026999979812,
                1.8539969999931307
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-11",
                "RandomProvider_rangeDown_BinaryFraction_xxi",
                -6.447725166547045E125,
                37.24737200001865,
                4.206839999998712
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_xxii",
                -2.1401835644499287E10,
                3.7599269999773086,
                19.763109999771977
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_xxiii",
                -8.281660175454012E24,
                8.010092000028635,
                18.854625999852182
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-5 << 20",
                "RandomProvider_rangeDown_BinaryFraction_xxiv",
                -6.7609298642372346E131,
                37.07091300000956,
                17.015772999850565
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xxv",
                -0.019464856126887104,
                3.7599269999773086,
                20.237141999770692
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xxvi",
                -7.532126051459283E12,
                8.010092000028635,
                21.16858599984985
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-5 >> 20",
                "RandomProvider_rangeDown_BinaryFraction_xxvii",
                -6.149029890582128E119,
                37.07091300000956,
                23.789788999822175
        );
        rangeDown_BinaryFraction_fail_helper(1, 0, "0");
        rangeDown_BinaryFraction_fail_helper(0, 1, "0");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            int meanDivisionSize,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanDivisionSize).range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void range_BinaryFraction_BinaryFraction_fail_helper(
            int meanDivisionSize,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(meanDivisionSize).range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRange_BinaryFraction_BinaryFraction() {
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "0",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_i",
                0.4993035895794412,
                1.0832809999980395,
                1.0011419999976825
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_ii",
                0.500473623506543,
                7.342950000026489,
                7.997435000016672
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_iii",
                0.50070604284837,
                31.14941800003445,
                32.04546700002099
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_iv",
                6.000429698001397,
                3.0411059999898256,
                0.8980329999976231
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_v",
                5.999617188270735,
                9.872437000001373,
                7.265178000023123
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_vi",
                6.006316830325357,
                33.93414200002559,
                31.118477000020434
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "11",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_vii",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "11",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_viii",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "11",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_ix",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "11",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_x",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "11",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xi",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "11",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xii",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-11",
                "-1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xiii",
                -5.99957030201959,
                3.041002999989865,
                0.8979999999976223
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-11",
                "-1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xiv",
                -6.000382811731497,
                9.872120000001026,
                7.265317000023163
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-11",
                "-1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xv",
                -5.993683169675392,
                33.930533000025925,
                31.118525000020508
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-11",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xvi",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-11",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xvii",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-11",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xviii",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-1",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xix",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-1",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xx",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-1",
                "-11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxi",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "0",
                "0",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxii",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "0",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxiii",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "0",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxiv",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "0",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxv",
                5.500143323903409,
                3.3425789999888273,
                1.332903999990955
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxvi",
                5.506818198260208,
                10.507393999999584,
                8.069741000021585
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxvii",
                5.496573980658701,
                34.57617900002571,
                32.06209300002209
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxviii",
                5.0041785453242,
                2.6294259999877116,
                0.4159939999985251
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxix",
                4.9984930497873075,
                8.580130000010076,
                6.434966000015122
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-1",
                "11",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxx",
                4.999216990832369,
                32.37837700003206,
                30.164890000018275
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "5 >> 20",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxi",
                0.4997954880458359,
                19.500193000030965,
                20.501135000022167
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "5 >> 20",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxii",
                0.5002054052455298,
                26.88661500001311,
                27.885368000000607
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "5 >> 20",
                "1",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxiii",
                0.5002069628278691,
                51.01174300001681,
                52.01118400001777
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "1",
                "5 << 20",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxiv",
                2619927.4664051863,
                21.896850000033783,
                1.5024889999887836
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "1",
                "5 << 20",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxv",
                2622974.6827238556,
                29.287213999973005,
                8.10811100002023
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "1",
                "5 << 20",
                "RandomProvider_range_BinaryFraction_BinaryFraction_xxxvi",
                2621934.787841631,
                53.410762000035774,
                32.06946100002204
        );
        range_BinaryFraction_BinaryFraction_fail_helper(0, "0", "1");
        range_BinaryFraction_BinaryFraction_fail_helper(-1, "0", "1");
        range_BinaryFraction_BinaryFraction_fail_helper(Integer.MAX_VALUE, "0", "1");
    }

    @Test
    public void testPositiveFloats() {
        aeqitLimitLog(TINY_LIMIT, P.positiveFloats(), "RandomProvider_positiveFloats");
    }

    @Test
    public void testNegativeFloats() {
        aeqitLimitLog(TINY_LIMIT, P.negativeFloats(), "RandomProvider_negativeFloats");
    }

    @Test
    public void testNonzeroFloats() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroFloats(), "RandomProvider_nonzeroFloats");
    }

    @Test
    public void testFloats() {
        aeqitLimitLog(TINY_LIMIT, P.negativeFloats(), "RandomProvider_floats");
    }

    @Test
    public void testPositiveDoubles() {
        aeqitLimitLog(TINY_LIMIT, P.positiveDoubles(), "RandomProvider_positiveDoubles");
    }

    @Test
    public void testNegativeDoubles() {
        aeqitLimitLog(TINY_LIMIT, P.negativeDoubles(), "RandomProvider_negativeDoubles");
    }

    @Test
    public void testNonzeroDoubles() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroDoubles(), "RandomProvider_nonzeroDoubles");
    }

    @Test
    public void testDoubles() {
        aeqitLimitLog(TINY_LIMIT, P.negativeDoubles(), "RandomProvider_doubles");
    }

    @Test
    public void testPositiveFloatsUniform() {
        aeqitLimitLog(TINY_LIMIT, P.positiveFloatsUniform(), "RandomProvider_positiveFloatsUniform");
    }

    @Test
    public void testNegativeFloatsUniform() {
        aeqitLimitLog(TINY_LIMIT, P.negativeFloatsUniform(), "RandomProvider_negativeFloatsUniform");
    }

    @Test
    public void testNonzeroFloatsUniform() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroFloatsUniform(), "RandomProvider_nonzeroFloatsUniform");
    }

    @Test
    public void testFloatsUniform() {
        aeqitLimitLog(TINY_LIMIT, P.floatsUniform(), "RandomProvider_floatsUniform");
    }

    @Test
    public void testPositiveDoublesUniform() {
        aeqitLimitLog(TINY_LIMIT, P.positiveDoublesUniform(), "RandomProvider_positiveDoublesUniform");
    }

    @Test
    public void testNegativeDoublesUniform() {
        aeqitLimitLog(TINY_LIMIT, P.negativeDoublesUniform(), "RandomProvider_negativeDoublesUniform");
    }

    @Test
    public void testNonzeroDoublesUniform() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroDoublesUniform(), "RandomProvider_nonzeroDoublesUniform");
    }

    @Test
    public void testDoublesUniform() {
        aeqitLimitLog(TINY_LIMIT, P.doublesUniform(), "RandomProvider_doublesUniform");
    }

    private static void floatHelper(@NotNull Iterable<Float> fs, @NotNull String output, float sampleMean) {
        List<Float> sample = toList(take(DEFAULT_SAMPLE_SIZE, fs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfFloats(sample), sampleMean);
    }

    private static void doubleHelper(@NotNull Iterable<Double> ds, @NotNull String output, double sampleMean) {
        List<Double> sample = toList(take(DEFAULT_SAMPLE_SIZE, ds));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfDoubles(sample), sampleMean);
    }

    private static void rangeUp_float_helper(float a, @NotNull String output, float sampleMean) {
        floatHelper(P.rangeUp(a), output, sampleMean);
        P.reset();
    }

    private static void rangeUp_float_fail_helper(float a) {
        try {
            P.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_float() {
        rangeUp_float_helper(1.0f, "RandomProvider_rangeUp_float_i", 3.9935304E36f);
        rangeUp_float_helper(1.0E20f, "RandomProvider_rangeUp_float_ii", 8.267385E36f);
        rangeUp_float_helper(-1.0f, "RandomProvider_rangeUp_float_iii", 1.3550214E36f);
        rangeUp_float_helper(-1.0E20f, "RandomProvider_rangeUp_float_iv", 1.14254505E36f);
        rangeUp_float_helper((float) Math.PI, "RandomProvider_rangeUp_float_v", 4.026231E36f);
        rangeUp_float_helper((float) Math.sqrt(2), "RandomProvider_rangeUp_float_vi", 3.9928994E36f);
        rangeUp_float_helper((float) -Math.PI, "RandomProvider_rangeUp_float_vii", 1.3398904E36f);
        rangeUp_float_helper((float) -Math.sqrt(2), "RandomProvider_rangeUp_float_viii", 1.3491116E36f);
        rangeUp_float_helper(0.0f, "RandomProvider_rangeUp_float_ix", 1.9848223E36f);
        rangeUp_float_helper(-0.0f, "RandomProvider_rangeUp_float_x", 1.9848223E36f);
        rangeUp_float_helper(Float.MIN_VALUE, "RandomProvider_rangeUp_float_xi", 1.9848228E36f);
        rangeUp_float_helper(Float.MIN_NORMAL, "RandomProvider_rangeUp_float_xii", 2.0187889E36f);
        rangeUp_float_helper(-Float.MIN_VALUE, "RandomProvider_rangeUp_float_xiii", 1.9848223E36f);
        rangeUp_float_helper(-Float.MIN_NORMAL, "RandomProvider_rangeUp_float_xiv", 2.0147774E36f);
        rangeUp_float_helper(Float.MAX_VALUE, "RandomProvider_rangeUp_float_xv", Float.POSITIVE_INFINITY);
        rangeUp_float_helper(-Float.MAX_VALUE, "RandomProvider_rangeUp_float_xvi", -5.4709615E33f);
        rangeUp_float_helper(Float.POSITIVE_INFINITY, "RandomProvider_rangeUp_float_xvii", Float.POSITIVE_INFINITY);
        rangeUp_float_helper(Float.NEGATIVE_INFINITY, "RandomProvider_rangeUp_float_xviii", -5.471121E33f);
        rangeUp_float_fail_helper(Float.NaN);
    }

    private static void rangeDown_float_helper(float a, @NotNull String output, float sampleMean) {
        floatHelper(P.rangeDown(a), output, sampleMean);
        P.reset();
    }

    private static void rangeDown_float_fail_helper(float a) {
        try {
            P.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_float() {
        rangeDown_float_helper(1.0f, "RandomProvider_rangeDown_float_i", -1.3550214E36f);
        rangeDown_float_helper(1.0E20f, "RandomProvider_rangeDown_float_ii", -1.14254505E36f);
        rangeDown_float_helper(-1.0f, "RandomProvider_rangeDown_float_iii", -3.9935304E36f);
        rangeDown_float_helper(-1.0E20f, "RandomProvider_rangeDown_float_iv", -8.267385E36f);
        rangeDown_float_helper((float) Math.PI, "RandomProvider_rangeDown_float_v", -1.3398904E36f);
        rangeDown_float_helper((float) Math.sqrt(2), "RandomProvider_rangeDown_float_vi", -1.3491116E36f);
        rangeDown_float_helper((float) -Math.PI, "RandomProvider_rangeDown_float_vii", -4.026231E36f);
        rangeDown_float_helper((float) -Math.sqrt(2), "RandomProvider_rangeDown_float_viii", -3.9928994E36f);
        rangeDown_float_helper(0.0f, "RandomProvider_rangeDown_float_ix", -1.9848223E36f);
        rangeDown_float_helper(-0.0f, "RandomProvider_rangeDown_float_x", -1.9848223E36f);
        rangeDown_float_helper(Float.MIN_VALUE, "RandomProvider_rangeDown_float_xi", -1.9848223E36f);
        rangeDown_float_helper(Float.MIN_NORMAL, "RandomProvider_rangeDown_float_xii", -2.0147774E36f);
        rangeDown_float_helper(-Float.MIN_VALUE, "RandomProvider_rangeDown_float_xiii", -1.9848228E36f);
        rangeDown_float_helper(-Float.MIN_NORMAL, "RandomProvider_rangeDown_float_xiv", -2.0187889E36f);
        rangeDown_float_helper(Float.MAX_VALUE, "RandomProvider_rangeDown_float_xv", 5.4709615E33f);
        rangeDown_float_helper(-Float.MAX_VALUE, "RandomProvider_rangeDown_float_xvi", Float.NEGATIVE_INFINITY);
        rangeDown_float_helper(Float.POSITIVE_INFINITY, "RandomProvider_rangeDown_float_xvii", 5.471121E33f);
        rangeDown_float_helper(
                Float.NEGATIVE_INFINITY,
                "RandomProvider_rangeDown_float_xviii",
                Float.NEGATIVE_INFINITY
        );
        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void range_float_float_helper(float a, float b, @NotNull String output, float sampleMean) {
        floatHelper(P.range(a, b), output, sampleMean);
        P.reset();
    }

    private static void range_float_float_fail_helper(float a, float b) {
        try {
            P.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_float_float() {
        range_float_float_helper(1.0f, 2.0f, "RandomProvider_range_float_float_i", 1.5001546f);
        range_float_float_helper(1.0f, 3.0f, "RandomProvider_range_float_float_ii", 1.8340441f);
        range_float_float_helper(1.0f, 4.0f, "RandomProvider_range_float_float_iii", 2.2503035f);
        range_float_float_helper(1.0f, 257.0f, "RandomProvider_range_float_float_iv", 47.948357f);
        range_float_float_helper(-257.0f, -1.0f, "RandomProvider_range_float_float_v", -47.890907f);
        range_float_float_helper(1.0f, 1.0E20f, "RandomProvider_range_float_float_vi", 2.12897406E18f);
        range_float_float_helper(-1.0E20f, -1.0f, "RandomProvider_range_float_float_vii", -2.12162781E18f);
        range_float_float_helper(
                (float) Math.sqrt(2),
                (float) Math.PI,
                "RandomProvider_range_float_float_viii",
                2.1330023f
        );
        range_float_float_helper(
                (float) Math.PI,
                FloatingPointUtils.successor((float) Math.PI),
                "RandomProvider_range_float_float_ix",
                3.1147525f
        );
        range_float_float_helper(0.0f, 1.0f, "RandomProvider_range_float_float_x", 0.011681142f);
        range_float_float_helper(-1.0f, 1.0f, "RandomProvider_range_float_float_xi", 4.273086E-5f);
        range_float_float_helper(1.0f, 1.0f, "RandomProvider_range_float_float_xii", 1.0090389f);
        range_float_float_helper(
                1.0f,
                Float.POSITIVE_INFINITY,
                "RandomProvider_range_float_float_xiii",
                3.9935304E36f
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                1.0f,
                "RandomProvider_range_float_float_xiv",
                -1.3425657E36f
        );
        range_float_float_helper(
                Float.MAX_VALUE,
                Float.POSITIVE_INFINITY,
                "RandomProvider_range_float_float_xv",
                Float.POSITIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                -Float.MAX_VALUE,
                "RandomProvider_range_float_float_xvi",
                Float.NEGATIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                "RandomProvider_range_float_float_xvii",
                -5.471121E33f
        );
        range_float_float_helper(
                Float.POSITIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                "RandomProvider_range_float_float_xviii",
                Float.POSITIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                Float.NEGATIVE_INFINITY,
                "RandomProvider_range_float_float_xix",
                Float.NEGATIVE_INFINITY
        );
        range_float_float_helper(1.0f, -1.0f, "RandomProvider_range_float_float_xx", 0.0f);
        range_float_float_helper(
                Float.POSITIVE_INFINITY,
                Float.NEGATIVE_INFINITY,
                "RandomProvider_range_float_float_xxi",
                0.0f
        );
        range_float_float_helper(Float.POSITIVE_INFINITY, 1.0f, "RandomProvider_range_float_float_xxii", 0.0f);
        range_float_float_helper(1.0f, Float.NEGATIVE_INFINITY, "RandomProvider_range_float_float_xxiii", 0.0f);
        range_float_float_fail_helper(Float.NaN, 1.0f);
        range_float_float_fail_helper(Float.NaN, Float.POSITIVE_INFINITY);
        range_float_float_fail_helper(Float.NaN, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(1.0f, Float.NaN);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NEGATIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NaN, Float.NaN);
    }

    private static void rangeUp_double_helper(double a, @NotNull String output, double sampleMean) {
        doubleHelper(P.rangeUp(a), output, sampleMean);
        P.reset();
    }

    private static void rangeUp_double_fail_helper(double a) {
        try {
            P.rangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUp_double() {
        rangeUp_double_helper(1.0, "RandomProvider_rangeUp_double_i", 2.6552885265154105E305);
        rangeUp_double_helper(1.0E20, "RandomProvider_rangeUp_double_ii", 2.880170764012561E305);
        rangeUp_double_helper(-1.0, "RandomProvider_rangeUp_double_iii", 8.791807689509865E304);
        rangeUp_double_helper(-1.0E20, "RandomProvider_rangeUp_double_iv", 8.851866062997829E304);
        rangeUp_double_helper(Math.PI, "RandomProvider_rangeUp_double_v", 2.6819125859769203E305);
        rangeUp_double_helper(Math.sqrt(2), "RandomProvider_rangeUp_double_vi", 2.659182730566976E305);
        rangeUp_double_helper(-Math.PI, "RandomProvider_rangeUp_double_vii", 8.795811810755379E304);
        rangeUp_double_helper(-Math.sqrt(2), "RandomProvider_rangeUp_double_viii", 8.643105716054554E304);
        rangeUp_double_helper(0.0, "RandomProvider_rangeUp_double_ix", 1.2962175921568277E305);
        rangeUp_double_helper(-0.0, "RandomProvider_rangeUp_double_x", 1.2962175921568277E305);
        rangeUp_double_helper(Double.MIN_VALUE, "RandomProvider_rangeUp_double_xi", 1.296217592156828E305);
        rangeUp_double_helper(Double.MIN_NORMAL, "RandomProvider_rangeUp_double_xii", 1.3130119946507386E305);
        rangeUp_double_helper(-Double.MIN_VALUE, "RandomProvider_rangeUp_double_xiii", 1.2962175921568277E305);
        rangeUp_double_helper(-Double.MIN_NORMAL, "RandomProvider_rangeUp_double_xiv", 1.3292108351557686E305);
        rangeUp_double_helper(Double.MAX_VALUE, "RandomProvider_rangeUp_double_xv", Double.POSITIVE_INFINITY);
        rangeUp_double_helper(-Double.MAX_VALUE, "RandomProvider_rangeUp_double_xvi", -3.959346934339254E303);
        rangeUp_double_helper(
                Double.POSITIVE_INFINITY,
                "RandomProvider_rangeUp_double_xvii",
                Double.POSITIVE_INFINITY
        );
        rangeUp_double_helper(
                Double.NEGATIVE_INFINITY,
                "RandomProvider_rangeUp_double_xviii",
                -3.959346934339273E303
        );
        rangeUp_double_fail_helper(Double.NaN);
    }

    private static void rangeDown_double_helper(double a, @NotNull String output, double sampleMean) {
        doubleHelper(P.rangeDown(a), output, sampleMean);
        P.reset();
    }

    private static void rangeDown_double_fail_helper(double a) {
        try {
            P.rangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDown_double() {
        rangeDown_double_helper(1.0, "RandomProvider_rangeDown_double_i", -8.791807689509865E304);
        rangeDown_double_helper(1.0E20, "RandomProvider_rangeDown_double_ii", -8.851866062997829E304);
        rangeDown_double_helper(-1.0, "RandomProvider_rangeDown_double_iii", -2.6552885265154105E305);
        rangeDown_double_helper(-1.0E20, "RandomProvider_rangeDown_double_iv", -2.880170764012561E305);
        rangeDown_double_helper(Math.PI, "RandomProvider_rangeDown_double_v", -8.795811810755379E304);
        rangeDown_double_helper(Math.sqrt(2), "RandomProvider_rangeDown_double_vi", -8.643105716054554E304);
        rangeDown_double_helper(-Math.PI, "RandomProvider_rangeDown_double_vii", -2.6819125859769203E305);
        rangeDown_double_helper(-Math.sqrt(2), "RandomProvider_rangeDown_double_viii", -2.659182730566976E305);
        rangeDown_double_helper(0.0, "RandomProvider_rangeDown_double_ix", -1.2962175921568277E305);
        rangeDown_double_helper(-0.0, "RandomProvider_rangeDown_double_x", -1.2962175921568277E305);
        rangeDown_double_helper(Double.MIN_VALUE, "RandomProvider_rangeDown_double_xi", -1.2962175921568277E305);
        rangeDown_double_helper(Double.MIN_NORMAL, "RandomProvider_rangeDown_double_xii", -1.3292108351557686E305);
        rangeDown_double_helper(-Double.MIN_VALUE, "RandomProvider_rangeDown_double_xiii", -1.296217592156828E305);
        rangeDown_double_helper(-Double.MIN_NORMAL, "RandomProvider_rangeDown_double_xiv", -1.3130119946507386E305);
        rangeDown_double_helper(Double.MAX_VALUE, "RandomProvider_rangeDown_double_xv", 3.959346934339254E303);
        rangeDown_double_helper(-Double.MAX_VALUE, "RandomProvider_rangeDown_double_xvi", Double.NEGATIVE_INFINITY);
        rangeDown_double_helper(
                Double.POSITIVE_INFINITY,
                "RandomProvider_rangeDown_double_xvii",
                3.959346934339273E303
        );
        rangeDown_double_helper(
                Double.NEGATIVE_INFINITY,
                "RandomProvider_rangeDown_double_xviii",
                Double.NEGATIVE_INFINITY
        );
        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void range_double_double_helper(double a, double b, @NotNull String output, double sampleMean) {
        doubleHelper(P.range(a, b), output, sampleMean);
        P.reset();
    }

    private static void range_double_double_fail_helper(double a, double b) {
        try {
            P.range(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRange_double_double() {
        range_double_double_helper(1.0, 2.0, "RandomProvider_range_double_double_i", 1.499876445210012);
        range_double_double_helper(1.0, 3.0, "RandomProvider_range_double_double_ii", 1.8332389470500066);
        range_double_double_helper(1.0, 4.0, "RandomProvider_range_double_double_iii", 2.252032740476341);
        range_double_double_helper(1.0, 257.0, "RandomProvider_range_double_double_iv", 47.82222621595425);
        range_double_double_helper(-257.0, -1.0, "RandomProvider_range_double_double_v", -47.96215738338841);
        range_double_double_helper(1.0, 1.0E20, "RandomProvider_range_double_double_vi", 2.14103887866713216E18);
        range_double_double_helper(-1.0E20, -1.0, "RandomProvider_range_double_double_vii", -2.11804239962092954E18);
        range_double_double_helper(
                Math.sqrt(2),
                Math.PI,
                "RandomProvider_range_double_double_viii",
                2.1332465556159024
        );
        range_double_double_helper(
                Math.PI,
                FloatingPointUtils.successor(Math.PI),
                "RandomProvider_range_double_double_ix",
                3.1415926535777086
        );
        range_double_double_helper(0.0, 1.0, "RandomProvider_range_double_double_x", 0.0014616598338681203);
        range_double_double_helper(-1.0, 1.0, "RandomProvider_range_double_double_xi", -1.3378778293216968E-5);
        range_double_double_helper(1.0, 1.0, "RandomProvider_range_double_double_xii", 1.000000000007918);
        range_double_double_helper(
                1.0,
                Double.POSITIVE_INFINITY,
                "RandomProvider_range_double_double_xiii",
                2.6552885265154105E305
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                1.0,
                "RandomProvider_range_double_double_xiv",
                -9.343071840537472E304
        );
        range_double_double_helper(
                Double.MAX_VALUE,
                Double.POSITIVE_INFINITY,
                "RandomProvider_range_double_double_xv",
                Double.POSITIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                -Double.MAX_VALUE,
                "RandomProvider_range_double_double_xvi",
                Double.NEGATIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                "RandomProvider_range_double_double_xvii",
                -3.959346934339273E303
        );
        range_double_double_helper(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                "RandomProvider_range_double_double_xviii",
                Double.POSITIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                "RandomProvider_range_double_double_xix",
                Double.NEGATIVE_INFINITY
        );
        range_double_double_helper(1.0, -1.0, "RandomProvider_range_double_double_xx", 0.0);
        range_double_double_helper(
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                "RandomProvider_range_double_double_xxi",
                0.0
        );
        range_double_double_helper(Double.POSITIVE_INFINITY, 1.0, "RandomProvider_range_double_double_xxii", 0.0);
        range_double_double_helper(1.0, Double.NEGATIVE_INFINITY, "RandomProvider_range_double_double_xxiii", 0.0);
        range_double_double_fail_helper(Double.NaN, 1.0);
        range_double_double_fail_helper(Double.NaN, Double.POSITIVE_INFINITY);
        range_double_double_fail_helper(Double.NaN, Double.NEGATIVE_INFINITY);
        range_double_double_fail_helper(1.0, Double.NaN);
        range_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NEGATIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NaN, Double.NaN);
    }

    private static void rangeUpUniform_float_helper(float a, @NotNull String output, float sampleMean) {
        floatHelper(P.rangeUpUniform(a), output, sampleMean);
        P.reset();
    }

    private static void rangeUpUniform_float_fail_helper(float a) {
        try {
            P.rangeUpUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUpUniform_float() {
        rangeUpUniform_float_helper(1.0f, "RandomProvider_rangeUpUniform_float_i", 1.6998872E38f);
        rangeUpUniform_float_helper(1.0E20f, "RandomProvider_rangeUpUniform_float_ii", 1.6998872E38f);
        rangeUpUniform_float_helper(-1.0f, "RandomProvider_rangeUpUniform_float_iii", 1.6998872E38f);
        rangeUpUniform_float_helper(-1.0E20f, "RandomProvider_rangeUpUniform_float_iv", 1.6998872E38f);
        rangeUpUniform_float_helper((float) Math.PI, "RandomProvider_rangeUpUniform_float_v", 1.6998872E38f);
        rangeUpUniform_float_helper((float) Math.sqrt(2), "RandomProvider_rangeUpUniform_float_vi", 1.6998872E38f);
        rangeUpUniform_float_helper((float) -Math.PI, "RandomProvider_rangeUpUniform_float_vii", 1.6998872E38f);
        rangeUpUniform_float_helper((float) -Math.sqrt(2), "RandomProvider_rangeUpUniform_float_viii", 1.6998872E38f);
        rangeUpUniform_float_helper(0.0f, "RandomProvider_rangeUpUniform_float_ix", 1.6998872E38f);
        rangeUpUniform_float_helper(-0.0f, "RandomProvider_rangeUpUniform_float_x", 1.6998872E38f);
        rangeUpUniform_float_helper(Float.MIN_VALUE, "RandomProvider_rangeUpUniform_float_xi", 1.6998872E38f);
        rangeUpUniform_float_helper(Float.MIN_NORMAL, "RandomProvider_rangeUpUniform_float_xii", 1.6998872E38f);
        rangeUpUniform_float_helper(-Float.MIN_VALUE, "RandomProvider_rangeUpUniform_float_xiii", 1.6998872E38f);
        rangeUpUniform_float_helper(-Float.MIN_NORMAL, "RandomProvider_rangeUpUniform_float_xiv", 1.6998872E38f);
        rangeUpUniform_float_helper(
                Float.MAX_VALUE,
                "RandomProvider_rangeUpUniform_float_xv",
                Float.POSITIVE_INFINITY
        );
        rangeUpUniform_float_helper(-Float.MAX_VALUE, "RandomProvider_rangeUpUniform_float_xvi", -1.1742675E35f);

        rangeUpUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        rangeUpUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        rangeUpUniform_float_fail_helper(Float.NaN);
    }

    private static void rangeDownUniform_float_helper(float a, @NotNull String output, float sampleMean) {
        floatHelper(P.rangeDownUniform(a), output, sampleMean);
        P.reset();
    }

    private static void rangeDownUniform_float_fail_helper(float a) {
        try {
            P.rangeDownUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDownUniform_float() {
        rangeDownUniform_float_helper(1.0f, "RandomProvider_rangeDownUniform_float_i", -1.6998872E38f);
        rangeDownUniform_float_helper(1.0E20f, "RandomProvider_rangeDownUniform_float_ii", -1.6998872E38f);
        rangeDownUniform_float_helper(-1.0f, "RandomProvider_rangeDownUniform_float_iii", -1.6998872E38f);
        rangeDownUniform_float_helper(-1.0E20f, "RandomProvider_rangeDownUniform_float_iv", -1.6998872E38f);
        rangeDownUniform_float_helper((float) Math.PI, "RandomProvider_rangeDownUniform_float_v", -1.6998872E38f);
        rangeDownUniform_float_helper(
                (float) Math.sqrt(2),
                "RandomProvider_rangeDownUniform_float_vi",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper((float) -Math.PI, "RandomProvider_rangeDownUniform_float_vii", -1.6998872E38f);
        rangeDownUniform_float_helper(
                (float) -Math.sqrt(2),
                "RandomProvider_rangeDownUniform_float_viii",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(0.0f, "RandomProvider_rangeDownUniform_float_ix", -1.6998872E38f);
        rangeDownUniform_float_helper(-0.0f, "RandomProvider_rangeDownUniform_float_x", -1.6998872E38f);
        rangeDownUniform_float_helper(Float.MIN_VALUE, "RandomProvider_rangeDownUniform_float_xi", -1.6998872E38f);
        rangeDownUniform_float_helper(Float.MIN_NORMAL, "RandomProvider_rangeDownUniform_float_xii", -1.6998872E38f);
        rangeDownUniform_float_helper(-Float.MIN_VALUE, "RandomProvider_rangeDownUniform_float_xiii", -1.6998872E38f);
        rangeDownUniform_float_helper(-Float.MIN_NORMAL, "RandomProvider_rangeDownUniform_float_xiv", -1.6998872E38f);
        rangeDownUniform_float_helper(Float.MAX_VALUE, "RandomProvider_rangeDownUniform_float_xv", 1.1742675E35f);
        rangeDownUniform_float_helper(
                -Float.MAX_VALUE,
                "RandomProvider_rangeDownUniform_float_xvi",
                Float.NEGATIVE_INFINITY
        );

        rangeDownUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        rangeDownUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        rangeDownUniform_float_fail_helper(Float.NaN);
    }

    private static void rangeUniform_float_float_helper(float a, float b, @NotNull String output, float sampleMean) {
        floatHelper(P.rangeUniform(a, b), output, sampleMean);
        P.reset();
    }

    private static void rangeUniform_float_float_fail_helper(float a, float b) {
        try {
            P.rangeUniform(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUniform_float_float() {
        rangeUniform_float_float_helper(1.0f, 2.0f, "RandomProvider_rangeUniform_float_float_i", 1.5004492f);
        rangeUniform_float_float_helper(1.0f, 3.0f, "RandomProvider_rangeUniform_float_float_ii", 1.9997782f);
        rangeUniform_float_float_helper(1.0f, 4.0f, "RandomProvider_rangeUniform_float_float_iii", 2.4997394f);
        rangeUniform_float_float_helper(1.0f, 257.0f, "RandomProvider_rangeUniform_float_float_iv", 129.06558f);
        rangeUniform_float_float_helper(-257.0f, -1.0f, "RandomProvider_rangeUniform_float_float_v", -128.92534f);
        rangeUniform_float_float_helper(1.0f, 1.0E20f, "RandomProvider_rangeUniform_float_float_vi", 5.001496E19f);
        rangeUniform_float_float_helper(-1.0E20f, -1.0f, "RandomProvider_rangeUniform_float_float_vii", -4.997858E19f);
        rangeUniform_float_float_helper(
                (float) Math.sqrt(2),
                (float) Math.PI,
                "RandomProvider_rangeUniform_float_float_viii",
                2.2780147f
        );
        rangeUniform_float_float_helper(
                (float) Math.PI,
                FloatingPointUtils.successor((float) Math.PI),
                "RandomProvider_rangeUniform_float_float_ix",
                3.1147525f
        );
        rangeUniform_float_float_helper(0.0f, 1.0f, "RandomProvider_rangeUniform_float_float_x", 0.50023586f);
        rangeUniform_float_float_helper(-1.0f, 1.0f, "RandomProvider_rangeUniform_float_float_xi", -3.822554E-4f);
        rangeUniform_float_float_helper(1.0f, 1.0f, "RandomProvider_rangeUniform_float_float_xii", 1.0090389f);
        rangeUniform_float_float_helper(1.0f, -1.0f, "RandomProvider_rangeUniform_float_float_xiii", 0.0f);

        rangeUniform_float_float_fail_helper(Float.POSITIVE_INFINITY, 1.0f);
        rangeUniform_float_float_fail_helper(Float.NEGATIVE_INFINITY, 1.0f);
        rangeUniform_float_float_fail_helper(Float.NaN, 1.0f);
        rangeUniform_float_float_fail_helper(1.0f, Float.POSITIVE_INFINITY);
        rangeUniform_float_float_fail_helper(1.0f, Float.NEGATIVE_INFINITY);
        rangeUniform_float_float_fail_helper(1.0f, Float.NaN);
    }

    private static void rangeUpUniform_double_helper(double a, @NotNull String output, double sampleMean) {
        doubleHelper(P.rangeUpUniform(a), output, sampleMean);
        P.reset();
    }

    private static void rangeUpUniform_double_fail_helper(double a) {
        try {
            P.rangeUpUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUpUniform_double() {
        rangeUpUniform_double_helper(1.0, "RandomProvider_rangeUpUniform_double_i", 8.995051139308303E307);
        rangeUpUniform_double_helper(1.0E20, "RandomProvider_rangeUpUniform_double_ii", 8.995051139308303E307);
        rangeUpUniform_double_helper(-1.0, "RandomProvider_rangeUpUniform_double_iii", 8.995051139308303E307);
        rangeUpUniform_double_helper(-1.0E20, "RandomProvider_rangeUpUniform_double_iv", 8.995051139308303E307);
        rangeUpUniform_double_helper(Math.PI, "RandomProvider_rangeUpUniform_double_v", 8.995051139308303E307);
        rangeUpUniform_double_helper(Math.sqrt(2), "RandomProvider_rangeUpUniform_double_vi", 8.995051139308303E307);
        rangeUpUniform_double_helper(-Math.PI, "RandomProvider_rangeUpUniform_double_vii", 8.995051139308303E307);
        rangeUpUniform_double_helper(
                -Math.sqrt(2),
                "RandomProvider_rangeUpUniform_double_viii",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(0.0, "RandomProvider_rangeUpUniform_double_ix", 8.995051139308303E307);
        rangeUpUniform_double_helper(-0.0, "RandomProvider_rangeUpUniform_double_x", 8.995051139308303E307);
        rangeUpUniform_double_helper(
                Double.MIN_VALUE,
                "RandomProvider_rangeUpUniform_double_xi",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Double.MIN_NORMAL,
                "RandomProvider_rangeUpUniform_double_xii",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Double.MIN_VALUE,
                "RandomProvider_rangeUpUniform_double_xiii",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Double.MIN_NORMAL,
                "RandomProvider_rangeUpUniform_double_xiv",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Double.MAX_VALUE,
                "RandomProvider_rangeUpUniform_double_xv",
                Double.POSITIVE_INFINITY
        );
        rangeUpUniform_double_helper(
                -Double.MAX_VALUE,
                "RandomProvider_rangeUpUniform_double_xvi",
                5.32707980219756E304
        );

        rangeUpUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        rangeUpUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        rangeUpUniform_double_fail_helper(Double.NaN);
    }

    private static void rangeDownUniform_double_helper(double a, @NotNull String output, double sampleMean) {
        doubleHelper(P.rangeDownUniform(a), output, sampleMean);
        P.reset();
    }

    private static void rangeDownUniform_double_fail_helper(double a) {
        try {
            P.rangeDownUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeDownUniform_double() {
        rangeDownUniform_double_helper(1.0, "RandomProvider_rangeDownUniform_double_i", -8.995051139308303E307);
        rangeDownUniform_double_helper(1.0E20, "RandomProvider_rangeDownUniform_double_ii", -8.995051139308303E307);
        rangeDownUniform_double_helper(-1.0, "RandomProvider_rangeDownUniform_double_iii", -8.995051139308303E307);
        rangeDownUniform_double_helper(-1.0E20, "RandomProvider_rangeDownUniform_double_iv", -8.995051139308303E307);
        rangeDownUniform_double_helper(Math.PI, "RandomProvider_rangeDownUniform_double_v", -8.995051139308303E307);
        rangeDownUniform_double_helper(
                Math.sqrt(2),
                "RandomProvider_rangeDownUniform_double_vi",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(-Math.PI, "RandomProvider_rangeDownUniform_double_vii", -8.995051139308303E307);
        rangeDownUniform_double_helper(
                -Math.sqrt(2),
                "RandomProvider_rangeDownUniform_double_viii",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(0.0, "RandomProvider_rangeDownUniform_double_ix", -8.995051139308303E307);
        rangeDownUniform_double_helper(-0.0, "RandomProvider_rangeDownUniform_double_x", -8.995051139308303E307);
        rangeDownUniform_double_helper(
                Double.MIN_VALUE,
                "RandomProvider_rangeDownUniform_double_xi",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Double.MIN_NORMAL,
                "RandomProvider_rangeDownUniform_double_xii",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Double.MIN_VALUE,
                "RandomProvider_rangeDownUniform_double_xiii",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Double.MIN_NORMAL,
                "RandomProvider_rangeDownUniform_double_xiv",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Double.MAX_VALUE,
                "RandomProvider_rangeDownUniform_double_xv",
                -5.32707980219756E304
        );
        rangeDownUniform_double_helper(
                -Double.MAX_VALUE,
                "RandomProvider_rangeDownUniform_double_xvi",
                Double.NEGATIVE_INFINITY
        );
        rangeDownUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        rangeDownUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        rangeDownUniform_double_fail_helper(Double.NaN);
    }

    private static void rangeUniform_double_double_helper(
            double a,
            double b,
            @NotNull String output,
            double sampleMean
    ) {
        doubleHelper(P.rangeUniform(a, b), output, sampleMean);
        P.reset();
    }

    private static void rangeUniform_double_double_fail_helper(double a, double b) {
        try {
            P.rangeUniform(a, b);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testRangeUniform_double_double() {
        rangeUniform_double_double_helper(1.0, 2.0, "RandomProvider_rangeUniform_double_double_i", 1.4998662259843438);
        rangeUniform_double_double_helper(1.0, 3.0, "RandomProvider_rangeUniform_double_double_ii", 2.00069067968375);
        rangeUniform_double_double_helper(
                1.0,
                4.0,
                "RandomProvider_rangeUniform_double_double_iii",
                2.5002819717191107
        );
        rangeUniform_double_double_helper(
                1.0,
                257.0,
                "RandomProvider_rangeUniform_double_double_iv",
                128.96575385200788
        );
        rangeUniform_double_double_helper(
                -257.0,
                -1.0,
                "RandomProvider_rangeUniform_double_double_v",
                -129.03424614799135
        );
        rangeUniform_double_double_helper(
                1.0,
                1.0E20,
                "RandomProvider_rangeUniform_double_double_vi",
                5.002316023213162E19
        );
        rangeUniform_double_double_helper(
                -1.0E20,
                -1.0,
                "RandomProvider_rangeUniform_double_double_vii",
                -4.997683976786839E19
        );
        rangeUniform_double_double_helper(
                Math.sqrt(2),
                Math.PI,
                "RandomProvider_rangeUniform_double_double_viii",
                2.2773915219111456
        );
        rangeUniform_double_double_helper(
                Math.PI,
                FloatingPointUtils.successor(Math.PI),
                "RandomProvider_rangeUniform_double_double_ix",
                3.1415926535777086
        );
        rangeUniform_double_double_helper(
                0.0,
                1.0,
                "RandomProvider_rangeUniform_double_double_x",
                0.4998662259843977
        );
        rangeUniform_double_double_helper(
                -1.0,
                1.0,
                "RandomProvider_rangeUniform_double_double_xi",
                6.906796837564503E-4
        );
        rangeUniform_double_double_helper(
                1.0,
                1.0,
                "RandomProvider_rangeUniform_double_double_xii",
                1.000000000007918
        );
        rangeUniform_double_double_helper(1.0, -1.0, "RandomProvider_rangeUniform_double_double_xiii", 0.0);

        rangeUniform_double_double_fail_helper(Double.POSITIVE_INFINITY, 1.0);
        rangeUniform_double_double_fail_helper(Double.NEGATIVE_INFINITY, 1.0);
        rangeUniform_double_double_fail_helper(Double.NaN, 1.0);
        rangeUniform_double_double_fail_helper(1.0, Double.POSITIVE_INFINITY);
        rangeUniform_double_double_fail_helper(1.0, Double.NEGATIVE_INFINITY);
        rangeUniform_double_double_fail_helper(1.0, Double.NaN);
    }

    private static void bigDecimalHelper(
            @NotNull Iterable<BigDecimal> xs,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        List<BigDecimal> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfBigDecimals(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.unscaledValue().abs().bitLength(), sample))), unscaledBitSizeMean);
        aeq(meanOfIntegers(toList(map(x -> Math.abs(x.scale()), sample))), scaleMean);
    }

    private static void positiveBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void positiveBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPositiveBigDecimals() {
        positiveBigDecimals_helper(
                2,
                1,
                "RandomProvider_positiveBigDecimals_i",
                1.0009534254522994E16,
                2.0010259999798934,
                0.9987319999976897
        );
        positiveBigDecimals_helper(
                5,
                3,
                "RandomProvider_positiveBigDecimals_ii",
                1.6850709563045524E40,
                5.00820000000873,
                3.0015849999914943
        );
        positiveBigDecimals_helper(
                32,
                8,
                "RandomProvider_positiveBigDecimals_iii",
                1.2572425193762146E145,
                32.00852100002276,
                7.997832000016788
        );
        positiveBigDecimals_helper(
                100,
                10,
                "RandomProvider_positiveBigDecimals_iv",
                Double.POSITIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        positiveBigDecimals_fail_helper(1, 1);
        positiveBigDecimals_fail_helper(2, 0);
    }

    private static void negativeBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void negativeBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNegativeBigDecimals() {
        negativeBigDecimals_helper(
                2,
                1,
                "RandomProvider_negativeBigDecimals_i",
                -1.0009534254522994E16,
                2.0010259999798934,
                0.9987319999976897
        );
        negativeBigDecimals_helper(
                5,
                3,
                "RandomProvider_negativeBigDecimals_ii",
                -1.6850709563045524E40,
                5.00820000000873,
                3.0015849999914943
        );
        negativeBigDecimals_helper(
                32,
                8,
                "RandomProvider_negativeBigDecimals_iii",
                -1.2572425193762146E145,
                32.00852100002276,
                7.997832000016788
        );
        negativeBigDecimals_helper(
                100,
                10,
                "RandomProvider_negativeBigDecimals_iv",
                Double.NEGATIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        negativeBigDecimals_fail_helper(1, 1);
        negativeBigDecimals_fail_helper(2, 0);
    }

    private static void nonzeroBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void nonzeroBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNonzeroBigDecimals() {
        nonzeroBigDecimals_helper(
                2,
                1,
                "RandomProvider_nonzeroBigDecimals_i",
                -3.0002088848452557E17,
                1.99926999997992,
                0.9998329999977443
        );
        nonzeroBigDecimals_helper(
                5,
                3,
                "RandomProvider_nonzeroBigDecimals_ii",
                -3.2241232349714534E39,
                5.007370000008868,
                2.996820999991485
        );
        nonzeroBigDecimals_helper(
                32,
                8,
                "RandomProvider_nonzeroBigDecimals_iii",
                1.2572425193762147E144,
                32.00133300002266,
                8.001691000016947
        );
        nonzeroBigDecimals_helper(
                100,
                10,
                "RandomProvider_nonzeroBigDecimals_iv",
                Double.NaN,
                99.9163349999983,
                10.004244000004174
        );
        nonzeroBigDecimals_fail_helper(1, 1);
        nonzeroBigDecimals_fail_helper(2, 0);
    }

    private static void bigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).bigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void bigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).bigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBigDecimals() {
        bigDecimals_helper(
                2,
                1,
                "RandomProvider_bigDecimals_i",
                -6.179780535515134E14,
                2.0001599999890485,
                1.0009719999977271
        );
        bigDecimals_helper(
                5,
                3,
                "RandomProvider_bigDecimals_ii",
                -1.606745484001922E40,
                5.010166000005216,
                2.995944999991484
        );
        bigDecimals_helper(
                32,
                8,
                "RandomProvider_bigDecimals_iii",
                -1.0670887202897772E136,
                32.02927300002175,
                8.000781000016627
        );
        bigDecimals_helper(
                100,
                10,
                "RandomProvider_bigDecimals_iv",
                Double.NaN,
                99.91854699999466,
                10.00440500000418
        );
        bigDecimals_fail_helper(0, 1);
        bigDecimals_fail_helper(2, 0);
    }

    private static void positiveCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveCanonicalBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void positiveCanonicalBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveCanonicalBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPositiveCanonicalBigDecimals() {
        positiveCanonicalBigDecimals_helper(
                2,
                1,
                "RandomProvider_positiveCanonicalBigDecimals_i",
                6.995086292023604,
                1.9993349999798273,
                1.0016879999976924
        );
        positiveCanonicalBigDecimals_helper(
                5,
                3,
                "RandomProvider_positiveCanonicalBigDecimals_ii",
                5.0163406598608475E10,
                5.010096000008702,
                2.9996349999914838
        );
        positiveCanonicalBigDecimals_helper(
                32,
                8,
                "RandomProvider_positiveCanonicalBigDecimals_iii",
                2.1875960862631944E131,
                32.00952900002345,
                7.992489000016789
        );
        positiveCanonicalBigDecimals_helper(
                100,
                10,
                "RandomProvider_positiveCanonicalBigDecimals_iv",
                Double.POSITIVE_INFINITY,
                99.93904899999788,
                10.005566000005233
        );
        positiveCanonicalBigDecimals_fail_helper(1, 1);
        positiveCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void negativeCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeCanonicalBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void negativeCanonicalBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeCanonicalBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNegativeCanonicalBigDecimals() {
        negativeCanonicalBigDecimals_helper(
                2,
                1,
                "RandomProvider_negativeCanonicalBigDecimals_i",
                -6.995086292023604,
                1.9993349999798273,
                1.0016879999976924
        );
        negativeCanonicalBigDecimals_helper(
                5,
                3,
                "RandomProvider_negativeCanonicalBigDecimals_ii",
                -5.0163406598608475E10,
                5.010096000008702,
                2.9996349999914838
        );
        negativeCanonicalBigDecimals_helper(
                32,
                8,
                "RandomProvider_negativeCanonicalBigDecimals_iii",
                -2.1875960862631944E131,
                32.00952900002345,
                7.992489000016789
        );
        negativeCanonicalBigDecimals_helper(
                100,
                10,
                "RandomProvider_negativeCanonicalBigDecimals_iv",
                Double.NEGATIVE_INFINITY,
                99.93904899999788,
                10.005566000005233
        );
        negativeCanonicalBigDecimals_fail_helper(1, 1);
        negativeCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void nonzeroCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroCanonicalBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void nonzeroCanonicalBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroCanonicalBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNonzeroCanonicalBigDecimals() {
        nonzeroCanonicalBigDecimals_helper(
                2,
                1,
                "RandomProvider_nonzeroCanonicalBigDecimals_i",
                2.6687974633912868,
                2.0000579999799557,
                1.0015309999976933
        );
        nonzeroCanonicalBigDecimals_helper(
                5,
                3,
                "RandomProvider_nonzeroCanonicalBigDecimals_ii",
                -7.081594725123083E13,
                5.008832000008888,
                2.9996669999914487
        );
        nonzeroCanonicalBigDecimals_helper(
                32,
                8,
                "RandomProvider_nonzeroCanonicalBigDecimals_iii",
                2.1875960862631944E131,
                32.006803000023154,
                7.998565000016982
        );
        nonzeroCanonicalBigDecimals_helper(
                100,
                10,
                "RandomProvider_nonzeroCanonicalBigDecimals_iv",
                Double.NaN,
                99.93122599999683,
                10.008305000004126
        );
        nonzeroCanonicalBigDecimals_fail_helper(1, 1);
        nonzeroCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void canonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).canonicalBigDecimals(),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void canonicalBigDecimals_fail_helper(int meanUnscaledBitSize, int meanScaleSize) {
        try {
            P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).canonicalBigDecimals();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testCanonicalBigDecimals() {
        canonicalBigDecimals_helper(
                2,
                1,
                "RandomProvider_canonicalBigDecimals_i",
                -763.4558908243097,
                2.0009919999845023,
                1.0021219999976965
        );
        canonicalBigDecimals_helper(
                5,
                3,
                "RandomProvider_canonicalBigDecimals_ii",
                1.0252835941140206E14,
                5.008561000007895,
                3.000018999991451
        );
        canonicalBigDecimals_helper(
                32,
                8,
                "RandomProvider_canonicalBigDecimals_iii",
                -6.239390326218754E113,
                32.01280800002317,
                8.001077000016947
        );
        canonicalBigDecimals_helper(
                100,
                10,
                "RandomProvider_canonicalBigDecimals_iv",
                Double.NaN,
                99.93086299999682,
                10.008105000004125
        );
        canonicalBigDecimals_fail_helper(1, 1);
        canonicalBigDecimals_fail_helper(2, 0);
    }

    private static void rangeUp_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(Readers.readBigDecimal(a).get()),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void rangeUp_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeUp_BigDecimal() {
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0",
                "RandomProvider_rangeUp_BigDecimal_i",
                4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeUp_BigDecimal_ii",
                1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeUp_BigDecimal_iii",
                4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0.0",
                "RandomProvider_rangeUp_BigDecimal_iv",
                4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0.0",
                "RandomProvider_rangeUp_BigDecimal_v",
                1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0.0",
                "RandomProvider_rangeUp_BigDecimal_vi",
                4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1",
                "RandomProvider_rangeUp_BigDecimal_vii",
                1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeUp_BigDecimal_viii",
                1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeUp_BigDecimal_ix",
                4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1",
                "RandomProvider_rangeUp_BigDecimal_x",
                3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeUp_BigDecimal_xi",
                1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeUp_BigDecimal_xii",
                4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1.0",
                "RandomProvider_rangeUp_BigDecimal_xiii",
                1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1.0",
                "RandomProvider_rangeUp_BigDecimal_xiv",
                1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1.0",
                "RandomProvider_rangeUp_BigDecimal_xv",
                4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "RandomProvider_rangeUp_BigDecimal_xvi",
                3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "RandomProvider_rangeUp_BigDecimal_xvii",
                1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "RandomProvider_rangeUp_BigDecimal_xviii",
                4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "9",
                "RandomProvider_rangeUp_BigDecimal_xix",
                1393.5639559349913,
                9.880917999979038,
                1.8592359999871946
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "9",
                "RandomProvider_rangeUp_BigDecimal_xx",
                1.3255156090701044E14,
                24.11267099993038,
                5.856708000007621
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "9",
                "RandomProvider_rangeUp_BigDecimal_xxi",
                4.18021886093211E113,
                72.54750000002102,
                15.956679999995899
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-9",
                "RandomProvider_rangeUp_BigDecimal_xxii",
                1378.9562815626282,
                9.876909999983395,
                1.8597449999883424
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-9",
                "RandomProvider_rangeUp_BigDecimal_xxiii",
                1.3255156101085684E14,
                23.87646199989173,
                5.874416000007179
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-9",
                "RandomProvider_rangeUp_BigDecimal_xxiv",
                4.18021886093211E113,
                72.39022300002145,
                15.95933799999627
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "10",
                "RandomProvider_rangeUp_BigDecimal_xxv",
                1394.5639559317256,
                9.344789999982071,
                1.8601429999848516
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "10",
                "RandomProvider_rangeUp_BigDecimal_xxvi",
                1.3255156090701067E14,
                24.027310999942575,
                5.833906000008166
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "10",
                "RandomProvider_rangeUp_BigDecimal_xxvii",
                4.18021886093211E113,
                72.6158040000279,
                15.954095999995749
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-10",
                "RandomProvider_rangeUp_BigDecimal_xxviii",
                1371.5161272222995,
                9.041028999984173,
                1.8600609999848983
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-10",
                "RandomProvider_rangeUp_BigDecimal_xxix",
                1.3255156078209173E14,
                23.679469999889513,
                5.835174000008125
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-10",
                "RandomProvider_rangeUp_BigDecimal_xxx",
                4.18021886093211E113,
                72.45249300002985,
                15.954258999995814
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "101",
                "RandomProvider_rangeUp_BigDecimal_xxxi",
                1485.5639559341907,
                13.261434999963267,
                1.8598879999882962
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "101",
                "RandomProvider_rangeUp_BigDecimal_xxxii",
                1.3255156090703184E14,
                27.119858999869805,
                5.873467000007205
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "101",
                "RandomProvider_rangeUp_BigDecimal_xxxiii",
                4.18021886093211E113,
                74.39138300003185,
                15.959073999996285
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-101",
                "RandomProvider_rangeUp_BigDecimal_xxxiv",
                1283.5659129612752,
                12.548057999941495,
                1.9062969999885824
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-101",
                "RandomProvider_rangeUp_BigDecimal_xxxv",
                1.3255156090699792E14,
                26.704327999833442,
                5.8527580000076265
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-101",
                "RandomProvider_rangeUp_BigDecimal_xxxvi",
                4.18021886093211E113,
                74.22560500003182,
                15.954912999995985
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1234567",
                "RandomProvider_rangeUp_BigDecimal_xxxvii",
                1235951.5639480965,
                26.786103999874832,
                1.8600099999879223
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1234567",
                "RandomProvider_rangeUp_BigDecimal_xxxviii",
                1.3255156214142816E14,
                40.21804799994806,
                5.866811000007397
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1234567",
                "RandomProvider_rangeUp_BigDecimal_xxxix",
                4.18021886093211E113,
                83.01889800010139,
                15.958300999996053
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "RandomProvider_rangeUp_BigDecimal_xl",
                -1233182.4360437375,
                26.87187099987704,
                1.8600259999881925
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "RandomProvider_rangeUp_BigDecimal_xli",
                1.3255155967258822E14,
                40.239422999948026,
                5.871547000007296
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "RandomProvider_rangeUp_BigDecimal_xlii",
                4.18021886093211E113,
                82.92891300009914,
                15.958750999996031
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0.09",
                "RandomProvider_rangeUp_BigDecimal_xliii",
                1384.6539559255805,
                9.721092999988024,
                3.1611809999692686
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0.09",
                "RandomProvider_rangeUp_BigDecimal_xliv",
                1.3255156090700805E14,
                22.11084499998398,
                6.582647000006659
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0.09",
                "RandomProvider_rangeUp_BigDecimal_xlv",
                4.18021886093211E113,
                70.64934000002235,
                16.281369000005466
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "RandomProvider_rangeUp_BigDecimal_xlvi",
                1384.4640551254167,
                9.249282999984306,
                3.213727999966871
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "RandomProvider_rangeUp_BigDecimal_xlvii",
                1.3255156086335206E14,
                21.758886999950676,
                6.607647000006336
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "RandomProvider_rangeUp_BigDecimal_xlviii",
                4.18021886093211E113,
                70.50093900002139,
                16.283410000005688
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "RandomProvider_rangeUp_BigDecimal_xlix",
                1384.5639559361018,
                30.66109999998521,
                13.00223999988918
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "RandomProvider_rangeUp_BigDecimal_l",
                1.3255156090700803E14,
                42.33828099999371,
                15.086760999914567
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "RandomProvider_rangeUp_BigDecimal_li",
                4.18021886093211E113,
                81.12436300001744,
                21.93101600001485
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "RandomProvider_rangeUp_BigDecimal_lii",
                1384.5634216923115,
                30.66058399998519,
                13.001653999889186
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "RandomProvider_rangeUp_BigDecimal_liii",
                1.3255156106591658E14,
                42.30981799999284,
                15.068190999914787
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "RandomProvider_rangeUp_BigDecimal_liv",
                4.18021886093211E113,
                81.06456700001691,
                21.920668000014597
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "RandomProvider_rangeUp_BigDecimal_lv",
                1.0000000013833472E12,
                35.175007999926635,
                4.719209999999073
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "RandomProvider_rangeUp_BigDecimal_lvi",
                1.3355156090700798E14,
                56.715418000037005,
                6.368124000006292
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "RandomProvider_rangeUp_BigDecimal_lvii",
                4.18021886093211E113,
                98.11771199995576,
                15.959104999996125
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "RandomProvider_rangeUp_BigDecimal_lviii",
                -9.999999986166528E11,
                35.175007999926635,
                4.719209999999073
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "RandomProvider_rangeUp_BigDecimal_lix",
                1.3155156090700802E14,
                56.71513600003691,
                6.368124000006292
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "RandomProvider_rangeUp_BigDecimal_lx",
                4.18021886093211E113,
                98.06712599995508,
                15.959104999996125
        );
        rangeUp_BigDecimal_fail_helper(1, 1, "0");
        rangeUp_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void rangeDown_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(Readers.readBigDecimal(a).get()),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void rangeDown_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeDown_BigDecimal() {
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0",
                "RandomProvider_rangeDown_BigDecimal_i",
                -4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeDown_BigDecimal_ii",
                -1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeDown_BigDecimal_iii",
                -4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0.0",
                "RandomProvider_rangeDown_BigDecimal_iv",
                -4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0.0",
                "RandomProvider_rangeDown_BigDecimal_v",
                -1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0.0",
                "RandomProvider_rangeDown_BigDecimal_vi",
                -4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1",
                "RandomProvider_rangeDown_BigDecimal_vii",
                -3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeDown_BigDecimal_viii",
                -1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeDown_BigDecimal_ix",
                -4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1",
                "RandomProvider_rangeDown_BigDecimal_x",
                -1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeDown_BigDecimal_xi",
                -1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeDown_BigDecimal_xii",
                -4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1.0",
                "RandomProvider_rangeDown_BigDecimal_xiii",
                -3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1.0",
                "RandomProvider_rangeDown_BigDecimal_xiv",
                -1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1.0",
                "RandomProvider_rangeDown_BigDecimal_xv",
                -4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "RandomProvider_rangeDown_BigDecimal_xvi",
                -1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "RandomProvider_rangeDown_BigDecimal_xvii",
                -1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "RandomProvider_rangeDown_BigDecimal_xviii",
                -4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "9",
                "RandomProvider_rangeDown_BigDecimal_xix",
                -1378.9562815626282,
                9.876909999983395,
                1.8597449999883424
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "9",
                "RandomProvider_rangeDown_BigDecimal_xx",
                -1.3255156101085684E14,
                23.87646199989173,
                5.874416000007179
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "9",
                "RandomProvider_rangeDown_BigDecimal_xxi",
                -4.18021886093211E113,
                72.39022300002145,
                15.95933799999627
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-9",
                "RandomProvider_rangeDown_BigDecimal_xxii",
                -1393.5639559349913,
                9.880917999979038,
                1.8592359999871946
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-9",
                "RandomProvider_rangeDown_BigDecimal_xxiii",
                -1.3255156090701044E14,
                24.11267099993038,
                5.856708000007621
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-9",
                "RandomProvider_rangeDown_BigDecimal_xxiv",
                -4.18021886093211E113,
                72.54750000002102,
                15.956679999995899
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "10",
                "RandomProvider_rangeDown_BigDecimal_xxv",
                -1371.5161272222995,
                9.041028999984173,
                1.8600609999848983
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "10",
                "RandomProvider_rangeDown_BigDecimal_xxvi",
                -1.3255156078209173E14,
                23.679469999889513,
                5.835174000008125
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "10",
                "RandomProvider_rangeDown_BigDecimal_xxvii",
                -4.18021886093211E113,
                72.45249300002985,
                15.954258999995814
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-10",
                "RandomProvider_rangeDown_BigDecimal_xxviii",
                -1394.5639559317256,
                9.344789999982071,
                1.8601429999848516
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-10",
                "RandomProvider_rangeDown_BigDecimal_xxix",
                -1.3255156090701067E14,
                24.027310999942575,
                5.833906000008166
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-10",
                "RandomProvider_rangeDown_BigDecimal_xxx",
                -4.18021886093211E113,
                72.6158040000279,
                15.954095999995749
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "101",
                "RandomProvider_rangeDown_BigDecimal_xxxi",
                -1283.5659129612752,
                12.548057999941495,
                1.9062969999885824
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "101",
                "RandomProvider_rangeDown_BigDecimal_xxxii",
                -1.3255156090699792E14,
                26.704327999833442,
                5.8527580000076265
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "101",
                "RandomProvider_rangeDown_BigDecimal_xxxiii",
                -4.18021886093211E113,
                74.22560500003182,
                15.954912999995985
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-101",
                "RandomProvider_rangeDown_BigDecimal_xxxiv",
                -1485.5639559341907,
                13.261434999963267,
                1.8598879999882962
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-101",
                "RandomProvider_rangeDown_BigDecimal_xxxv",
                -1.3255156090703184E14,
                27.119858999869805,
                5.873467000007205
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-101",
                "RandomProvider_rangeDown_BigDecimal_xxxvi",
                -4.18021886093211E113,
                74.39138300003185,
                15.959073999996285
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1234567",
                "RandomProvider_rangeDown_BigDecimal_xxxvii",
                1233182.4360437375,
                26.87187099987704,
                1.8600259999881925
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1234567",
                "RandomProvider_rangeDown_BigDecimal_xxxviii",
                -1.3255155967258822E14,
                40.239422999948026,
                5.871547000007296
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1234567",
                "RandomProvider_rangeDown_BigDecimal_xxxix",
                -4.18021886093211E113,
                82.92891300009914,
                15.958750999996031
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "RandomProvider_rangeDown_BigDecimal_xl",
                -1235951.5639480965,
                26.786103999874832,
                1.8600099999879223
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "RandomProvider_rangeDown_BigDecimal_xli",
                -1.3255156214142816E14,
                40.21804799994806,
                5.866811000007397
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "RandomProvider_rangeDown_BigDecimal_xlii",
                -4.18021886093211E113,
                83.01889800010139,
                15.958300999996053
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0.09",
                "RandomProvider_rangeDown_BigDecimal_xliii",
                -1384.4640551254167,
                9.249282999984306,
                3.213727999966871
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0.09",
                "RandomProvider_rangeDown_BigDecimal_xliv",
                -1.3255156086335206E14,
                21.758886999950676,
                6.607647000006336
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0.09",
                "RandomProvider_rangeDown_BigDecimal_xlv",
                -4.18021886093211E113,
                70.50093900002139,
                16.283410000005688
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "RandomProvider_rangeDown_BigDecimal_xlvi",
                -1384.6539559255805,
                9.721092999988024,
                3.1611809999692686
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "RandomProvider_rangeDown_BigDecimal_xlvii",
                -1.3255156090700805E14,
                22.11084499998398,
                6.582647000006659
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "RandomProvider_rangeDown_BigDecimal_xlviii",
                -4.18021886093211E113,
                70.64934000002235,
                16.281369000005466
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "RandomProvider_rangeDown_BigDecimal_xlix",
                -1384.5634216923115,
                30.66058399998519,
                13.001653999889186
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "RandomProvider_rangeDown_BigDecimal_l",
                -1.3255156106591658E14,
                42.30981799999284,
                15.068190999914787
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "RandomProvider_rangeDown_BigDecimal_li",
                -4.18021886093211E113,
                81.06456700001691,
                21.920668000014597
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "RandomProvider_rangeDown_BigDecimal_lii",
                -1384.5639559361018,
                30.66109999998521,
                13.00223999988918
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "RandomProvider_rangeDown_BigDecimal_liii",
                -1.3255156090700803E14,
                42.33828099999371,
                15.086760999914567
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "RandomProvider_rangeDown_BigDecimal_liv",
                -4.18021886093211E113,
                81.12436300001744,
                21.93101600001485
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "RandomProvider_rangeDown_BigDecimal_lv",
                9.999999986166528E11,
                35.175007999926635,
                4.719209999999073
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "RandomProvider_rangeDown_BigDecimal_lvi",
                -1.3155156090700802E14,
                56.71513600003691,
                6.368124000006292
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "RandomProvider_rangeDown_BigDecimal_lvii",
                -4.18021886093211E113,
                98.06712599995508,
                15.959104999996125
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "RandomProvider_rangeDown_BigDecimal_lviii",
                -1.0000000013833472E12,
                35.175007999926635,
                4.719209999999073
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "RandomProvider_rangeDown_BigDecimal_lix",
                -1.3355156090700798E14,
                56.715418000037005,
                6.368124000006292
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "RandomProvider_rangeDown_BigDecimal_lx",
                -4.18021886093211E113,
                98.11771199995576,
                15.959104999996125
        );
        rangeDown_BigDecimal_fail_helper(1, 1, "0");
        rangeDown_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void range_BigDecimal_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .range(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void range_BigDecimal_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .range(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRange_BigDecimal_BigDecimal() {
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_i",
                0.49974181014590885,
                5.59269400000979,
                1.9986289999875098
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_ii",
                0.49994173214022963,
                18.1187839999679,
                6.007439000007458
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_iii",
                0.500371540682368,
                50.84739499999407,
                15.985023999996871
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_iv",
                0.6835993574261239,
                2.9379489999931696,
                1.3677679999925896
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_v",
                1.062113959074216,
                13.332859999998846,
                4.897865000002074
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_vi",
                1.2962726292409759,
                44.45600600002838,
                14.560672999996417
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_vii",
                499741.81014750147,
                5.59269400000979,
                3.1484789999806573
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_viii",
                499941.7321406794,
                18.1187839999679,
                3.6066369999861645
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_ix",
                500371.54068251094,
                50.84739499999407,
                11.149427999999784
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.000001",
                "RandomProvider_range_BigDecimal_BigDecimal_x",
                4.997418101445085E-7,
                5.59269400000979,
                6.496949000021773
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.000001",
                "RandomProvider_range_BigDecimal_BigDecimal_xi",
                4.999417321390705E-7,
                18.1187839999679,
                11.257294999992666
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.000001",
                "RandomProvider_range_BigDecimal_BigDecimal_xii",
                5.003715406817602E-7,
                50.84739499999407,
                21.652756000007802
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_xiii",
                1.3749136388746568,
                5.285133000014813,
                1.2841969999935254
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_xiv",
                1.6272101053585095,
                16.43551499998298,
                4.613846999999211
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_xv",
                1.8119114824740903,
                47.84639700000742,
                14.026318999996679
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_xvi",
                333040.3334871308,
                16.332860999928386,
                1.0213529999973245
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_xvii",
                428881.9766954399,
                28.3818839999616,
                3.61354699999148
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1E+6",
                "RandomProvider_range_BigDecimal_BigDecimal_xviii",
                470615.7681893622,
                58.28940599998806,
                12.153703999997873
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xix",
                -0.5003224338184838,
                5.6016160000096455,
                2.000491999987506
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xx",
                -0.49979485012240055,
                18.1147109999674,
                6.007397000007255
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxi",
                -0.4997172674593811,
                50.82451099999267,
                15.983411999996747
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxii",
                -2.316612149630881,
                5.819886999996417,
                1.3681939999925739
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxiii",
                -1.938844730320883,
                16.847756000016705,
                4.902132000002184
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxiv",
                -1.7039183609959918,
                48.36847900004126,
                14.560999999996431
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxv",
                -500322.43382010004,
                5.6016160000096455,
                3.1490699999805405
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxvi",
                -499794.8501228811,
                18.1147109999674,
                3.608310999986109
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxvii",
                -499717.26745949313,
                50.82451099999267,
                11.151406000000007
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-0.000001",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxviii",
                -5.003224338171066E-7,
                5.6016160000096455,
                6.5015840000220155
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-0.000001",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxix",
                -4.99794850121267E-7,
                18.1147109999674,
                11.25785899999279
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-0.000001",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_xxx",
                -4.997172674587391E-7,
                50.82451099999267,
                21.648954000007993
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxi",
                -2.6250863610944726,
                6.044352999999744,
                1.2841969999935254
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxii",
                -2.372789894616791,
                16.967438000029702,
                4.613846999999211
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxiii",
                -2.1880885175115052,
                48.136073000057394,
                14.026318999996679
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxiv",
                -666960.6665137309,
                7.471091000009681,
                3.863571999971566
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxv",
                -571119.023304855,
                20.706134999956284,
                3.6956279999854234
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "-1",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxvi",
                -529385.2318107232,
                53.81033699999196,
                11.33299799999915
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "100",
                "101",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxvii",
                100.49956052399139,
                12.080524999929484,
                2.125093999988825
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "100",
                "101",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxviii",
                100.50017215152612,
                26.214576999832683,
                5.9297770000088414
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "100",
                "101",
                "RandomProvider_range_BigDecimal_BigDecimal_xxxix",
                100.50033153589463,
                59.864782000072,
                15.909656999996354
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "2.7183",
                "3.1416",
                "RandomProvider_range_BigDecimal_BigDecimal_xl",
                2.825842387956162,
                18.745504000095565,
                5.0517519999509375
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "2.7183",
                "3.1416",
                "RandomProvider_range_BigDecimal_BigDecimal_xli",
                2.877667574471862,
                28.275335000400293,
                7.899351999956035
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "2.7183",
                "3.1416",
                "RandomProvider_range_BigDecimal_BigDecimal_li",
                2.9066625149504075,
                58.079359000018094,
                16.867250000012355
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3.1416",
                "2.7183",
                "RandomProvider_range_BigDecimal_BigDecimal_lii",
                -1.5598804219080757,
                17.89980300004773,
                5.028742999950965
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3.1416",
                "2.7183",
                "RandomProvider_range_BigDecimal_BigDecimal_liii",
                -0.8501281134081722,
                26.622813000158892,
                7.745819999954518
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3.1416",
                "2.7183",
                "RandomProvider_range_BigDecimal_BigDecimal_liv",
                -0.4897316229434775,
                55.57982800002947,
                16.511090000011794
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lv",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lvi",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lvii",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lviii",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lix",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lx",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxi",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxii",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxiii",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxiv",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxv",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0.0",
                "RandomProvider_range_BigDecimal_BigDecimal_lxvi",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_lxvii",
                1.000000000007918,
                4.073980000046605,
                1.0008359999977228
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_lxviii",
                1.000000000007918,
                10.558456999961003,
                3.003367999991497
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1",
                "RandomProvider_range_BigDecimal_BigDecimal_lxix",
                1.000000000007918,
                27.0739779999554,
                7.996049000016875
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "5",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_lxx",
                0.0,
                0.0,
                0.0
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "5",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_lxxi",
                0.0,
                0.0,
                0.0
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "5",
                "3",
                "RandomProvider_range_BigDecimal_BigDecimal_lxxii",
                0.0,
                0.0,
                0.0
        );
        range_BigDecimal_BigDecimal_fail_helper(0, 1, "0", "1");
        range_BigDecimal_BigDecimal_fail_helper(1, 0, "0", "1");
    }

    private static void rangeUpCanonical_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .rangeUpCanonical(Readers.readBigDecimal(a).get()),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void rangeUpCanonical_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeUpCanonical(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeUpCanonical_BigDecimal() {
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0",
                "RandomProvider_rangeUpCanonical_BigDecimal_i",
                1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeUpCanonical_BigDecimal_ii",
                1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeUpCanonical_BigDecimal_iii",
                7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_iv",
                1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_v",
                1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_vi",
                7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "RandomProvider_rangeUpCanonical_BigDecimal_vii",
                1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeUpCanonical_BigDecimal_viii",
                1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeUpCanonical_BigDecimal_ix",
                7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1",
                "RandomProvider_rangeUpCanonical_BigDecimal_x",
                1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeUpCanonical_BigDecimal_xi",
                1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeUpCanonical_BigDecimal_xii",
                7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xiii",
                1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xiv",
                1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xv",
                7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xvi",
                1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xvii",
                1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "RandomProvider_rangeUpCanonical_BigDecimal_xviii",
                7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xix",
                1739.4926563856254,
                6.9322569999429255,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xx",
                1.7454453319279503E14,
                14.305499999987385,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxi",
                7.165990190806363E111,
                46.02150500002807,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxii",
                1721.4926563710835,
                6.656802999978999,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxiii",
                1.7454453319279066E14,
                14.00533799998072,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxiv",
                7.165990190806363E111,
                45.869602000022596,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxv",
                1740.4926563798701,
                6.950839999942956,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxvi",
                1.7454453319279528E14,
                14.348097999995753,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxvii",
                7.165990190806363E111,
                46.08150400001817,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxviii",
                1720.4926563824695,
                6.695553999971137,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxix",
                1.7454453319279047E14,
                14.037556999983535,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxx",
                7.165990190806363E111,
                45.92659800001058,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxi",
                1831.4926563830088,
                9.922831000003992,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxii",
                1.7454453319283166E14,
                17.17193899996032,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxiii",
                7.165990190806363E111,
                47.83554400002552,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxiv",
                1629.4926563728989,
                9.829158000003313,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxv",
                1.7454453319275428E14,
                16.960544999936484,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxvi",
                7.165990190806363E111,
                47.68714400002377,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxvii",
                1236297.4926485345,
                23.699083000022462,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxviii",
                1.7454453443670638E14,
                30.376576999862372,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xxxix",
                7.165990190806363E111,
                56.49539900007108,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xl",
                -1232836.5073433,
                23.698749000022378,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xli",
                1.745445319498745E14,
                30.364093999859953,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlii",
                7.165990190806363E111,
                56.40500900006992,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xliii",
                1730.5826563736073,
                6.4465309999639375,
                2.158562999998543
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xliv",
                1.7454453319279266E14,
                12.175352999986236,
                3.583485999959521
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlv",
                7.165990190806363E111,
                44.08624700002398,
                8.288373000030616
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlvi",
                1730.4026563818722,
                6.02339199998733,
                2.2115529999964987
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlvii",
                1.745445331927926E14,
                11.870971999989273,
                3.60882599995618
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlviii",
                7.165990190806363E111,
                43.95665900001538,
                8.290905000030733
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_xlix",
                1730.4926563837735,
                27.411878999985376,
                12.000161999910091
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_l",
                1.7454453319279266E14,
                32.42130799998842,
                12.09146899991106
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_li",
                7.165990190806363E111,
                54.557798000017726,
                13.941664999922093
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lii",
                1730.4926563837735,
                27.41171399998538,
                11.999559999910096
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_liii",
                1.7454453319279266E14,
                32.408068999987364,
                12.073045999911198
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "RandomProvider_rangeUpCanonical_BigDecimal_liv",
                7.165990190806363E111,
                54.520999000017284,
                13.931557999922264
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lv",
                1.0000000017292806E12,
                43.066519999641805,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lvi",
                1.7554453319279253E14,
                49.885632999960094,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lvii",
                7.165990190806363E111,
                71.84684600000809,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lviii",
                -9.999999982707194E11,
                43.066519999641805,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lix",
                1.7354453319279272E14,
                49.885360999959886,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "RandomProvider_rangeUpCanonical_BigDecimal_lx",
                7.165990190806363E111,
                71.79637100000163,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_fail_helper(1, 1, "0");
        rangeUpCanonical_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void rangeDownCanonical_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .rangeDownCanonical(Readers.readBigDecimal(a).get()),
                output,
                sampleMean,
                unscaledBitSizeMean,
                scaleMean
        );
        P.reset();
    }

    private static void rangeDownCanonical_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).rangeDownCanonical(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeDownCanonical_BigDecimal() {
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0",
                "RandomProvider_rangeDownCanonical_BigDecimal_i",
                -1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0",
                "RandomProvider_rangeDownCanonical_BigDecimal_ii",
                -1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0",
                "RandomProvider_rangeDownCanonical_BigDecimal_iii",
                -7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_iv",
                -1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_v",
                -1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_vi",
                -7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "RandomProvider_rangeDownCanonical_BigDecimal_vii",
                -1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "RandomProvider_rangeDownCanonical_BigDecimal_viii",
                -1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "RandomProvider_rangeDownCanonical_BigDecimal_ix",
                -7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1",
                "RandomProvider_rangeDownCanonical_BigDecimal_x",
                -1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1",
                "RandomProvider_rangeDownCanonical_BigDecimal_xi",
                -1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1",
                "RandomProvider_rangeDownCanonical_BigDecimal_xii",
                -7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xiii",
                -1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xiv",
                -1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xv",
                -7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xvi",
                -1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xvii",
                -1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "RandomProvider_rangeDownCanonical_BigDecimal_xviii",
                -7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xix",
                -1721.4926563710835,
                6.656802999978999,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xx",
                -1.7454453319279066E14,
                14.00533799998072,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxi",
                -7.165990190806363E111,
                45.869602000022596,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxii",
                -1739.4926563856254,
                6.9322569999429255,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxiii",
                -1.7454453319279503E14,
                14.305499999987385,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxiv",
                -7.165990190806363E111,
                46.02150500002807,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxv",
                -1720.4926563824695,
                6.695553999971137,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxvi",
                -1.7454453319279047E14,
                14.037556999983535,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxvii",
                -7.165990190806363E111,
                45.92659800001058,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxviii",
                -1740.4926563798701,
                6.950839999942956,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxix",
                -1.7454453319279528E14,
                14.348097999995753,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxx",
                -7.165990190806363E111,
                46.08150400001817,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxi",
                -1629.4926563728989,
                9.829158000003313,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxii",
                -1.7454453319275428E14,
                16.960544999936484,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxiii",
                -7.165990190806363E111,
                47.68714400002377,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxiv",
                -1831.4926563830088,
                9.922831000003992,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxv",
                -1.7454453319283166E14,
                17.17193899996032,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxvi",
                -7.165990190806363E111,
                47.83554400002552,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxvii",
                1232836.5073433,
                23.698749000022378,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxviii",
                -1.745445319498745E14,
                30.364093999859953,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xxxix",
                -7.165990190806363E111,
                56.40500900006992,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xl",
                -1236297.4926485345,
                23.699083000022462,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xli",
                -1.7454453443670638E14,
                30.376576999862372,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlii",
                -7.165990190806363E111,
                56.49539900007108,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xliii",
                -1730.4026563818722,
                6.02339199998733,
                2.2115529999964987
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xliv",
                -1.745445331927926E14,
                11.870971999989273,
                3.60882599995618
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlv",
                -7.165990190806363E111,
                43.95665900001538,
                8.290905000030733
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlvi",
                -1730.5826563736073,
                6.4465309999639375,
                2.158562999998543
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlvii",
                -1.7454453319279266E14,
                12.175352999986236,
                3.583485999959521
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlviii",
                -7.165990190806363E111,
                44.08624700002398,
                8.288373000030616
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_xlix",
                -1730.4926563837735,
                27.41171399998538,
                11.999559999910096
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_l",
                -1.7454453319279266E14,
                32.408068999987364,
                12.073045999911198
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_li",
                -7.165990190806363E111,
                54.520999000017284,
                13.931557999922264
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lii",
                -1730.4926563837735,
                27.411878999985376,
                12.000161999910091
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_liii",
                -1.7454453319279266E14,
                32.42130799998842,
                12.09146899991106
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "RandomProvider_rangeDownCanonical_BigDecimal_liv",
                -7.165990190806363E111,
                54.557798000017726,
                13.941664999922093
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lv",
                9.999999982707194E11,
                43.066519999641805,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lvi",
                -1.7354453319279272E14,
                49.885360999959886,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lvii",
                -7.165990190806363E111,
                71.79637100000163,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lviii",
                -1.0000000017292806E12,
                43.066519999641805,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lix",
                -1.7554453319279253E14,
                49.885632999960094,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "RandomProvider_rangeDownCanonical_BigDecimal_lx",
                -7.165990190806363E111,
                71.84684600000809,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_fail_helper(1, 1, "0");
        rangeDownCanonical_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void rangeCanonical_BigDecimal_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        bigDecimalHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .rangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output,
                sampleMean,
                mantissaBitSizeMean,
                exponentMean
        );
        P.reset();
    }

    private static void rangeCanonical_BigDecimal_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .rangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeCanonical_BigDecimal_BigDecimal() {
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_i",
                0.5003934009792074,
                3.2073840000042164,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_ii",
                0.5003081581798811,
                9.489134999999093,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_iii",
                0.499968809465756,
                25.829786999967226,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_iv",
                0.6828048445016695,
                1.690074999998684,
                0.3674749999992351
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_v",
                1.0625945646729795,
                6.920348000009579,
                1.8981119999935625
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_vi",
                1.2938927250945065,
                22.428396000032464,
                6.556452000009386
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_vii",
                500393.40098079835,
                14.572748999867288,
                0.015543999999999086
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_viii",
                500308.1581803239,
                18.536230999894144,
                0.5365789999993211
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_ix",
                499968.80946587696,
                31.090607999969805,
                3.9439019999964624
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.000001",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_x",
                5.003934009778121E-7,
                3.2073840000042164,
                5.500212000006544
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.000001",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xi",
                5.003081581787221E-7,
                9.489134999999093,
                8.257126000016854
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.000001",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xii",
                4.999688094651478E-7,
                25.829786999967226,
                13.663255000001989
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xiii",
                1.3746588393634007,
                2.1531109999821383,
                0.28217299999993095
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xiv",
                1.6275508786442359,
                6.670948000040874,
                1.6095569999951769
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xv",
                1.8105920429697409,
                21.450927000054385,
                6.040454000006691
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xvi",
                333458.38886693533,
                13.087861999855248,
                0.020790999999999407
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xvii",
                428993.13684824604,
                18.472173999913938,
                0.6126289999991997
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1E+6",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xviii",
                470704.2297839316,
                31.80035300002205,
                4.175350999997326
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xix",
                -0.4996065990175754,
                3.2042220000040937,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xx",
                -0.49969184181920573,
                9.487877999999462,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxi",
                -0.5000311905339897,
                25.82962599996815,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxii",
                -2.3171951554812953,
                2.7800909999737797,
                0.3674749999992351
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxiii",
                -1.937405435315748,
                7.5025860000388604,
                1.8981119999935625
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxiv",
                -1.7061072748996227,
                22.703752999992012,
                6.556452000009386
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxv",
                -499606.5990192018,
                14.566150999867714,
                0.015543999999999086
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxvi",
                -499691.841819675,
                18.53413699989557,
                0.5365789999993211
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxvii",
                -500031.1905341227,
                31.095132999971124,
                3.9439019999964624
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-0.000001",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxviii",
                -4.996065990161529E-7,
                3.2042220000040937,
                5.498994000006578
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-0.000001",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxix",
                -4.996918418180518E-7,
                9.487877999999462,
                8.257264000016773
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-0.000001",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxx",
                -5.000311905333693E-7,
                25.82962599996815,
                13.66474300000205
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxi",
                -2.625341160605808,
                2.778448999970333,
                0.28217299999993095
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxii",
                -2.372449121331089,
                7.043562000040137,
                1.6095569999951769
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxiii",
                -2.189407957015854,
                21.64043100000685,
                6.040454000006691
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxiv",
                -666542.6111339338,
                19.424939999816562,
                0.020790999999999407
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxv",
                -571007.8631520575,
                21.17717899989599,
                0.6126289999991997
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "-1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxvi",
                -529296.7702161488,
                32.91999899996857,
                4.175350999997326
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "100",
                "101",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxvii",
                100.50039340172313,
                10.28381199998929,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "100",
                "101",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxviii",
                100.50030815855573,
                16.994920999929292,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "100",
                "101",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xxxix",
                100.49996880963798,
                33.63301799986874,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "2.7183",
                "3.1416",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xl",
                2.8257661906273217,
                15.1930399998104,
                4.053552000024533
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "2.7183",
                "3.1416",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xli",
                2.8775768745633243,
                18.06730499998356,
                4.893506999967964
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "2.7183",
                "3.1416",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlii",
                2.906521168534309,
                31.36289500038055,
                8.862022999959645
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3.1416",
                "2.7183",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xliii",
                -1.5601724613262047,
                14.495435999905277,
                4.032119000027335
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3.1416",
                "2.7183",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xliv",
                -0.853578161847082,
                16.58768599994705,
                4.740488999975314
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3.1416",
                "2.7183",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlv",
                -0.4913261055680533,
                29.00922700015098,
                8.51985399994396
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlvi",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlvii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlviii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_xlix",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_l",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_li",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_liii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_liv",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lv",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lvi",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0.0",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lvii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lviii",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lix",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lx",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "5",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lxi",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "5",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lxii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "5",
                "3",
                "RandomProvider_rangeCanonical_BigDecimal_BigDecimal_lxiii",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_fail_helper(0, 1, "0", "1");
        rangeCanonical_BigDecimal_BigDecimal_fail_helper(1, 0, "0", "1");
    }

    private static double meanOfIntegers(@NotNull List<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfBinaryFractions(@NotNull List<BinaryFraction> xs) {
        return sumDouble(map(bf -> bf.doubleRange().a / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static float meanOfFloats(@NotNull List<Float> xs) {
        return sumFloat(map(f -> f / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfDoubles(@NotNull List<Double> xs) {
        return sumDouble(map(d -> d / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfBigDecimals(@NotNull List<BigDecimal> xs) {
        return sumDouble(map(bd -> bd.doubleValue() / DEFAULT_SAMPLE_SIZE, xs));
    }
}
// @formatter:on
