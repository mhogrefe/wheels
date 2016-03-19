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
            @NotNull String topSampleCount,
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
                "[0.005, 7E-7, 0, 6, 0.7, 0.2, 6, 0, 0, 2.5, 0.01, 0, 0, 0.001, 0, 1, 0, 0.1, 0, 0.01, ...]",
                "{0=285193, 0.1=106913, 1=95281, 0.01=53792, 3=31973, 2=31755, 0.3=26965, 0.001=26868, 0.2=26768," +
                " 0.02=13696}",
                1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0",
                "[0.000013, 2.5387, 1, 0, 2, 0.4, 0.0002, 0.1, 0.14, 0.0008, 4.565, 2E-14, 6, 0, 0.00001, 0.9, 3722," +
                " 0.0001, 0.3, 0.0002, ...]",
                "{0=79582, 0.1=36196, 1=33195, 0.01=27112, 0.001=20186, 0.0001=15189, 0.3=14428, 0.2=14306, 2=13893," +
                " 3=13687}",
                1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0",
                "[4.04936997654063E-17, 2416.5, 0.000012564, 0.18, 24260304162.8, 1.4851833763E-8, 2352046.8," +
                " 6.9E-27, 8.01E-28, 0.0003541, 1E-8, 1.60161406, 7, 784415066, 703.32, 20.85970393," +
                " 0.005375094279919512, 3, 0.00148719799, 0.00075, ...]",
                "{0=6735, 1=3263, 0.1=3052, 0.01=2803, 0.001=2390, 0.0001=2137, 0.00001=1906, 0.000001=1717, 2=1594," +
                " 3=1532}",
                7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0.0",
                "[0.005, 7E-7, 0, 6, 0.7, 0.2, 6, 0, 0, 2.5, 0.01, 0, 0, 0.001, 0, 1, 0, 0.1, 0, 0.01, ...]",
                "{0=285193, 0.1=106913, 1=95281, 0.01=53792, 3=31973, 2=31755, 0.3=26965, 0.001=26868, 0.2=26768," +
                " 0.02=13696}",
                1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[0.000013, 2.5387, 1, 0, 2, 0.4, 0.0002, 0.1, 0.14, 0.0008, 4.565, 2E-14, 6, 0, 0.00001, 0.9, 3722," +
                " 0.0001, 0.3, 0.0002, ...]",
                "{0=79582, 0.1=36196, 1=33195, 0.01=27112, 0.001=20186, 0.0001=15189, 0.3=14428, 0.2=14306, 2=13893," +
                " 3=13687}",
                1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[4.04936997654063E-17, 2416.5, 0.000012564, 0.18, 24260304162.8, 1.4851833763E-8, 2352046.8," +
                " 6.9E-27, 8.01E-28, 0.0003541, 1E-8, 1.60161406, 7, 784415066, 703.32, 20.85970393," +
                " 0.005375094279919512, 3, 0.00148719799, 0.00075, ...]",
                "{0=6735, 1=3263, 0.1=3052, 0.01=2803, 0.001=2390, 0.0001=2137, 0.00001=1906, 0.000001=1717, 2=1594," +
                " 3=1532}",
                7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "[1.005, 1.0000007, 1, 7, 1.7, 1.2, 7, 1, 1, 3.5, 1.01, 1, 1, 1.001, 1, 2, 1, 1.1, 1, 1.01, ...]",
                "{1=285193, 1.1=106913, 2=95281, 1.01=53792, 4=31973, 3=31755, 1.3=26965, 1.001=26868, 1.2=26768," +
                " 1.02=13696}",
                1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "[1.000013, 3.5387, 2, 1, 3, 1.4, 1.0002, 1.1, 1.14, 1.0008, 5.565, 1.00000000000002, 7, 1, 1.00001," +
                " 1.9, 3723, 1.0001, 1.3, 1.0002, ...]",
                "{1=79582, 1.1=36196, 2=33195, 1.01=27112, 1.001=20186, 1.0001=15189, 1.3=14428, 1.2=14306, 3=13893," +
                " 4=13687}",
                1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "[1.0000000000000000404936997654063, 2417.5, 1.000012564, 1.18, 24260304163.8, 1.000000014851833763," +
                " 2352047.8, 1.0000000000000000000000000069, 1.000000000000000000000000000801, 1.0003541," +
                " 1.00000001, 2.60161406, 8, 784415067, 704.32, 21.85970393, 1.005375094279919512, 4, 1.00148719799," +
                " 1.00075, ...]",
                "{1=6735, 2=3263, 1.1=3052, 1.01=2803, 1.001=2390, 1.0001=2137, 1.00001=1906, 1.000001=1717, 3=1594," +
                " 4=1532}",
                7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1",
                "[-0.995, -0.9999993, -1, 5, -0.3, -0.8, 5, -1, -1, 1.5, -0.99, -1, -1, -0.999, -1, 0, -1, -0.9, -1," +
                " -0.99, ...]",
                "{-1=285193, -0.9=106913, 0=95281, -0.99=53792, 2=31973, 1=31755, -0.7=26965, -0.999=26868," +
                " -0.8=26768, -0.98=13696}",
                1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1",
                "[-0.999987, 1.5387, 0, -1, 1, -0.6, -0.9998, -0.9, -0.86, -0.9992, 3.565, -0.99999999999998, 5, -1," +
                " -0.99999, -0.1, 3721, -0.9999, -0.7, -0.9998, ...]",
                "{-1=79582, -0.9=36196, 0=33195, -0.99=27112, -0.999=20186, -0.9999=15189, -0.7=14428, -0.8=14306," +
                " 1=13893, 2=13687}",
                1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1",
                "[-0.9999999999999999595063002345937, 2415.5, -0.999987436, -0.82, 24260304161.8," +
                " -0.999999985148166237, 2352045.8, -0.9999999999999999999999999931," +
                " -0.999999999999999999999999999199, -0.9996459, -0.99999999, 0.60161406, 6, 784415065, 702.32," +
                " 19.85970393, -0.994624905720080488, 2, -0.99851280201, -0.99925, ...]",
                "{-1=6735, 0=3263, -0.9=3052, -0.99=2803, -0.999=2390, -0.9999=2137, -0.99999=1906, -0.999999=1717," +
                " 1=1594, 2=1532}",
                7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[1.005, 1.0000007, 1, 7, 1.7, 1.2, 7, 1, 1, 3.5, 1.01, 1, 1, 1.001, 1, 2, 1, 1.1, 1, 1.01, ...]",
                "{1=285193, 1.1=106913, 2=95281, 1.01=53792, 4=31973, 3=31755, 1.3=26965, 1.001=26868, 1.2=26768," +
                " 1.02=13696}",
                1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[1.000013, 3.5387, 2, 1, 3, 1.4, 1.0002, 1.1, 1.14, 1.0008, 5.565, 1.00000000000002, 7, 1, 1.00001," +
                " 1.9, 3723, 1.0001, 1.3, 1.0002, ...]",
                "{1=79582, 1.1=36196, 2=33195, 1.01=27112, 1.001=20186, 1.0001=15189, 1.3=14428, 1.2=14306, 3=13893," +
                " 4=13687}",
                1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[1.0000000000000000404936997654063, 2417.5, 1.000012564, 1.18, 24260304163.8, 1.000000014851833763," +
                " 2352047.8, 1.0000000000000000000000000069, 1.000000000000000000000000000801, 1.0003541," +
                " 1.00000001, 2.60161406, 8, 784415067, 704.32, 21.85970393, 1.005375094279919512, 4, 1.00148719799," +
                " 1.00075, ...]",
                "{1=6735, 2=3263, 1.1=3052, 1.01=2803, 1.001=2390, 1.0001=2137, 1.00001=1906, 1.000001=1717, 3=1594," +
                " 4=1532}",
                7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-0.995, -0.9999993, -1, 5, -0.3, -0.8, 5, -1, -1, 1.5, -0.99, -1, -1, -0.999, -1, 0, -1, -0.9, -1," +
                " -0.99, ...]",
                "{-1=285193, -0.9=106913, 0=95281, -0.99=53792, 2=31973, 1=31755, -0.7=26965, -0.999=26868," +
                " -0.8=26768, -0.98=13696}",
                1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-0.999987, 1.5387, 0, -1, 1, -0.6, -0.9998, -0.9, -0.86, -0.9992, 3.565, -0.99999999999998, 5, -1," +
                " -0.99999, -0.1, 3721, -0.9999, -0.7, -0.9998, ...]",
                "{-1=79582, -0.9=36196, 0=33195, -0.99=27112, -0.999=20186, -0.9999=15189, -0.7=14428, -0.8=14306," +
                " 1=13893, 2=13687}",
                1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-0.9999999999999999595063002345937, 2415.5, -0.999987436, -0.82, 24260304161.8," +
                " -0.999999985148166237, 2352045.8, -0.9999999999999999999999999931," +
                " -0.999999999999999999999999999199, -0.9996459, -0.99999999, 0.60161406, 6, 784415065, 702.32," +
                " 19.85970393, -0.994624905720080488, 2, -0.99851280201, -0.99925, ...]",
                "{-1=6735, 0=3263, -0.9=3052, -0.99=2803, -0.999=2390, -0.9999=2137, -0.99999=1906, -0.999999=1717," +
                " 1=1594, 2=1532}",
                7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "[9.005, 9.0000007, 9, 15, 9.7, 9.2, 15, 9, 9, 11.5, 9.01, 9, 9, 9.001, 9, 10, 9, 9.1, 9, 9.01, ...]",
                "{9=285193, 9.1=106913, 10=95281, 9.01=53792, 12=31973, 11=31755, 9.3=26965, 9.001=26868, 9.2=26768," +
                " 9.02=13696}",
                1739.4926563856254,
                6.9322569999429255,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "[9.000013, 11.5387, 10, 9, 11, 9.4, 9.0002, 9.1, 9.14, 9.0008, 13.565, 9.00000000000002, 15, 9," +
                " 9.00001, 9.9, 3731, 9.0001, 9.3, 9.0002, ...]",
                "{9=79582, 9.1=36196, 10=33195, 9.01=27112, 9.001=20186, 9.0001=15189, 9.3=14428, 9.2=14306," +
                " 11=13893, 12=13687}",
                1.7454453319279503E14,
                14.305499999987385,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "[9.0000000000000000404936997654063, 2425.5, 9.000012564, 9.18, 24260304171.8, 9.000000014851833763," +
                " 2352055.8, 9.0000000000000000000000000069, 9.000000000000000000000000000801, 9.0003541," +
                " 9.00000001, 10.60161406, 16, 784415075, 712.32, 29.85970393, 9.005375094279919512, 12," +
                " 9.00148719799, 9.00075, ...]",
                "{9=6735, 10=3263, 9.1=3052, 9.01=2803, 9.001=2390, 9.0001=2137, 9.00001=1906, 9.000001=1717," +
                " 11=1594, 12=1532}",
                7.165990190806363E111,
                46.02150500002807,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-8.995, -8.9999993, -9, -3, -8.3, -8.8, -3, -9, -9, -6.5, -8.99, -9, -9, -8.999, -9, -8, -9, -8.9," +
                " -9, -8.99, ...]",
                "{-9=285193, -8.9=106913, -8=95281, -8.99=53792, -6=31973, -7=31755, -8.7=26965, -8.999=26868," +
                " -8.8=26768, -8.98=13696}",
                1721.4926563710835,
                6.656802999978999,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-8.999987, -6.4613, -8, -9, -7, -8.6, -8.9998, -8.9, -8.86, -8.9992, -4.435, -8.99999999999998," +
                " -3, -9, -8.99999, -8.1, 3713, -8.9999, -8.7, -8.9998, ...]",
                "{-9=79582, -8.9=36196, -8=33195, -8.99=27112, -8.999=20186, -8.9999=15189, -8.7=14428, -8.8=14306," +
                " -7=13893, -6=13687}",
                1.7454453319279066E14,
                14.00533799998072,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-8.9999999999999999595063002345937, 2407.5, -8.999987436, -8.82, 24260304153.8," +
                " -8.999999985148166237, 2352037.8, -8.9999999999999999999999999931," +
                " -8.999999999999999999999999999199, -8.9996459, -8.99999999, -7.39838594, -2, 784415057, 694.32," +
                " 11.85970393, -8.994624905720080488, -6, -8.99851280201, -8.99925, ...]",
                "{-9=6735, -8=3263, -8.9=3052, -8.99=2803, -8.999=2390, -8.9999=2137, -8.99999=1906, -8.999999=1717," +
                " -7=1594, -6=1532}",
                7.165990190806363E111,
                45.869602000022596,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "[10.005, 10.0000007, 10, 16, 10.7, 10.2, 16, 10, 10, 12.5, 10.01, 10, 10, 10.001, 10, 11, 10, 10.1," +
                " 10, 10.01, ...]",
                "{10=285193, 10.1=106913, 11=95281, 10.01=53792, 13=31973, 12=31755, 10.3=26965, 10.001=26868," +
                " 10.2=26768, 10.02=13696}",
                1740.4926563798701,
                6.950839999942956,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "[10.000013, 12.5387, 11, 10, 12, 10.4, 10.0002, 10.1, 10.14, 10.0008, 14.565, 10.00000000000002," +
                " 16, 10, 10.00001, 10.9, 3732, 10.0001, 10.3, 10.0002, ...]",
                "{10=79582, 10.1=36196, 11=33195, 10.01=27112, 10.001=20186, 10.0001=15189, 10.3=14428, 10.2=14306," +
                " 12=13893, 13=13687}",
                1.7454453319279528E14,
                14.348097999995753,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "[10.0000000000000000404936997654063, 2426.5, 10.000012564, 10.18, 24260304172.8," +
                " 10.000000014851833763, 2352056.8, 10.0000000000000000000000000069," +
                " 10.000000000000000000000000000801, 10.0003541, 10.00000001, 11.60161406, 17, 784415076, 713.32," +
                " 30.85970393, 10.005375094279919512, 13, 10.00148719799, 10.00075, ...]",
                "{10=6735, 11=3263, 10.1=3052, 10.01=2803, 10.001=2390, 10.0001=2137, 10.00001=1906, 10.000001=1717," +
                " 12=1594, 13=1532}",
                7.165990190806363E111,
                46.08150400001817,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-9.995, -9.9999993, -10, -4, -9.3, -9.8, -4, -10, -10, -7.5, -9.99, -10, -10, -9.999, -10, -9," +
                " -10, -9.9, -10, -9.99, ...]",
                "{-10=285193, -9.9=106913, -9=95281, -9.99=53792, -7=31973, -8=31755, -9.7=26965, -9.999=26868," +
                " -9.8=26768, -9.98=13696}",
                1720.4926563824695,
                6.695553999971137,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-9.999987, -7.4613, -9, -10, -8, -9.6, -9.9998, -9.9, -9.86, -9.9992, -5.435, -9.99999999999998," +
                " -4, -10, -9.99999, -9.1, 3712, -9.9999, -9.7, -9.9998, ...]",
                "{-10=79582, -9.9=36196, -9=33195, -9.99=27112, -9.999=20186, -9.9999=15189, -9.7=14428, -9.8=14306," +
                " -8=13893, -7=13687}",
                1.7454453319279047E14,
                14.037556999983535,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-9.9999999999999999595063002345937, 2406.5, -9.999987436, -9.82, 24260304152.8," +
                " -9.999999985148166237, 2352036.8, -9.9999999999999999999999999931," +
                " -9.999999999999999999999999999199, -9.9996459, -9.99999999, -8.39838594, -3, 784415056, 693.32," +
                " 10.85970393, -9.994624905720080488, -7, -9.99851280201, -9.99925, ...]",
                "{-10=6735, -9=3263, -9.9=3052, -9.99=2803, -9.999=2390, -9.9999=2137, -9.99999=1906," +
                " -9.999999=1717, -8=1594, -7=1532}",
                7.165990190806363E111,
                45.92659800001058,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "[101.005, 101.0000007, 101, 107, 101.7, 101.2, 107, 101, 101, 103.5, 101.01, 101, 101, 101.001," +
                " 101, 102, 101, 101.1, 101, 101.01, ...]",
                "{101=285193, 101.1=106913, 102=95281, 101.01=53792, 104=31973, 103=31755, 101.3=26965," +
                " 101.001=26868, 101.2=26768, 101.02=13696}",
                1831.4926563830088,
                9.922831000003992,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "[101.000013, 103.5387, 102, 101, 103, 101.4, 101.0002, 101.1, 101.14, 101.0008, 105.565," +
                " 101.00000000000002, 107, 101, 101.00001, 101.9, 3823, 101.0001, 101.3, 101.0002, ...]",
                "{101=79582, 101.1=36196, 102=33195, 101.01=27112, 101.001=20186, 101.0001=15189, 101.3=14428," +
                " 101.2=14306, 103=13893, 104=13687}",
                1.7454453319283166E14,
                17.17193899996032,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "[101.0000000000000000404936997654063, 2517.5, 101.000012564, 101.18, 24260304263.8," +
                " 101.000000014851833763, 2352147.8, 101.0000000000000000000000000069," +
                " 101.000000000000000000000000000801, 101.0003541, 101.00000001, 102.60161406, 108, 784415167," +
                " 804.32, 121.85970393, 101.005375094279919512, 104, 101.00148719799, 101.00075, ...]",
                "{101=6735, 102=3263, 101.1=3052, 101.01=2803, 101.001=2390, 101.0001=2137, 101.00001=1906," +
                " 101.000001=1717, 103=1594, 104=1532}",
                7.165990190806363E111,
                47.83554400002552,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-100.995, -100.9999993, -101, -95, -100.3, -100.8, -95, -101, -101, -98.5, -100.99, -101, -101," +
                " -100.999, -101, -100, -101, -100.9, -101, -100.99, ...]",
                "{-101=285193, -100.9=106913, -100=95281, -100.99=53792, -98=31973, -99=31755, -100.7=26965," +
                " -100.999=26868, -100.8=26768, -100.98=13696}",
                1629.4926563728989,
                9.829158000003313,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-100.999987, -98.4613, -100, -101, -99, -100.6, -100.9998, -100.9, -100.86, -100.9992, -96.435," +
                " -100.99999999999998, -95, -101, -100.99999, -100.1, 3621, -100.9999, -100.7, -100.9998, ...]",
                "{-101=79582, -100.9=36196, -100=33195, -100.99=27112, -100.999=20186, -100.9999=15189," +
                " -100.7=14428, -100.8=14306, -99=13893, -98=13687}",
                1.7454453319275428E14,
                16.960544999936484,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-100.9999999999999999595063002345937, 2315.5, -100.999987436, -100.82, 24260304061.8," +
                " -100.999999985148166237, 2351945.8, -100.9999999999999999999999999931," +
                " -100.999999999999999999999999999199, -100.9996459, -100.99999999, -99.39838594, -94, 784414965," +
                " 602.32, -80.14029607, -100.994624905720080488, -98, -100.99851280201, -100.99925, ...]",
                "{-101=6735, -100=3263, -100.9=3052, -100.99=2803, -100.999=2390, -100.9999=2137, -100.99999=1906," +
                " -100.999999=1717, -99=1594, -98=1532}",
                7.165990190806363E111,
                47.68714400002377,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234567.005, 1234567.0000007, 1234567, 1234573, 1234567.7, 1234567.2, 1234573, 1234567, 1234567," +
                " 1234569.5, 1234567.01, 1234567, 1234567, 1234567.001, 1234567, 1234568, 1234567, 1234567.1," +
                " 1234567, 1234567.01, ...]",
                "{1234567=285193, 1234567.1=106913, 1234568=95281, 1234567.01=53792, 1234570=31973, 1234569=31755," +
                " 1234567.3=26965, 1234567.001=26868, 1234567.2=26768, 1234567.02=13696}",
                1236297.4926485345,
                23.699083000022462,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234567.000013, 1234569.5387, 1234568, 1234567, 1234569, 1234567.4, 1234567.0002, 1234567.1," +
                " 1234567.14, 1234567.0008, 1234571.565, 1234567.00000000000002, 1234573, 1234567, 1234567.00001," +
                " 1234567.9, 1238289, 1234567.0001, 1234567.3, 1234567.0002, ...]",
                "{1234567=79582, 1234567.1=36196, 1234568=33195, 1234567.01=27112, 1234567.001=20186," +
                " 1234567.0001=15189, 1234567.3=14428, 1234567.2=14306, 1234569=13893, 1234570=13687}",
                1.7454453443670638E14,
                30.376576999862372,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1234567.0000000000000000404936997654063, 1236983.5, 1234567.000012564, 1234567.18, 24261538729.8," +
                " 1234567.000000014851833763, 3586613.8, 1234567.0000000000000000000000000069," +
                " 1234567.000000000000000000000000000801, 1234567.0003541, 1234567.00000001, 1234568.60161406," +
                " 1234574, 785649633, 1235270.32, 1234587.85970393, 1234567.005375094279919512, 1234570," +
                " 1234567.00148719799, 1234567.00075, ...]",
                "{1234567=6735, 1234568=3263, 1234567.1=3052, 1234567.01=2803, 1234567.001=2390, 1234567.0001=2137," +
                " 1234567.00001=1906, 1234567.000001=1717, 1234569=1594, 1234570=1532}",
                7.165990190806363E111,
                56.49539900007108,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234566.995, -1234566.9999993, -1234567, -1234561, -1234566.3, -1234566.8, -1234561, -1234567," +
                " -1234567, -1234564.5, -1234566.99, -1234567, -1234567, -1234566.999, -1234567, -1234566, -1234567," +
                " -1234566.9, -1234567, -1234566.99, ...]",
                "{-1234567=285193, -1234566.9=106913, -1234566=95281, -1234566.99=53792, -1234564=31973," +
                " -1234565=31755, -1234566.7=26965, -1234566.999=26868, -1234566.8=26768, -1234566.98=13696}",
                -1232836.5073433,
                23.698749000022378,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234566.999987, -1234564.4613, -1234566, -1234567, -1234565, -1234566.6, -1234566.9998," +
                " -1234566.9, -1234566.86, -1234566.9992, -1234562.435, -1234566.99999999999998, -1234561, -1234567," +
                " -1234566.99999, -1234566.1, -1230845, -1234566.9999, -1234566.7, -1234566.9998, ...]",
                "{-1234567=79582, -1234566.9=36196, -1234566=33195, -1234566.99=27112, -1234566.999=20186," +
                " -1234566.9999=15189, -1234566.7=14428, -1234566.8=14306, -1234565=13893, -1234564=13687}",
                1.745445319498745E14,
                30.364093999859953,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1234566.9999999999999999595063002345937, -1232150.5, -1234566.999987436, -1234566.82," +
                " 24259069595.8, -1234566.999999985148166237, 1117479.8, -1234566.9999999999999999999999999931," +
                " -1234566.999999999999999999999999999199, -1234566.9996459, -1234566.99999999, -1234565.39838594," +
                " -1234560, 783180499, -1233863.68, -1234546.14029607, -1234566.994624905720080488, -1234564," +
                " -1234566.99851280201, -1234566.99925, ...]",
                "{-1234567=6735, -1234566=3263, -1234566.9=3052, -1234566.99=2803, -1234566.999=2390," +
                " -1234566.9999=2137, -1234566.99999=1906, -1234566.999999=1717, -1234565=1594, -1234564=1532}",
                7.165990190806363E111,
                56.40500900006992,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.095, 0.0900007, 0.09, 6.09, 0.79, 0.29, 6.09, 0.09, 0.09, 2.59, 0.1, 0.09, 0.09, 0.091, 0.09," +
                " 1.09, 0.09, 0.19, 0.09, 0.1, ...]",
                "{0.09=285193, 0.19=106913, 1.09=95281, 0.1=53792, 3.09=31973, 2.09=31755, 0.39=26965, 0.091=26868," +
                " 0.29=26768, 0.11=13696}",
                1730.5826563736073,
                6.4465309999639375,
                2.158562999998543
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.090013, 2.6287, 1.09, 0.09, 2.09, 0.49, 0.0902, 0.19, 0.23, 0.0908, 4.655, 0.09000000000002," +
                " 6.09, 0.09, 0.09001, 0.99, 3722.09, 0.0901, 0.39, 0.0902, ...]",
                "{0.09=79582, 0.19=36196, 1.09=33195, 0.1=27112, 0.091=20186, 0.0901=15189, 0.39=14428, 0.29=14306," +
                " 2.09=13893, 3.09=13687}",
                1.7454453319279266E14,
                12.175352999986236,
                3.583485999959521
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[0.0900000000000000404936997654063, 2416.59, 0.090012564, 0.27, 24260304162.89," +
                " 0.090000014851833763, 2352046.89, 0.0900000000000000000000000069," +
                " 0.090000000000000000000000000801, 0.0903541, 0.09000001, 1.69161406, 7.09, 784415066.09, 703.41," +
                " 20.94970393, 0.095375094279919512, 3.09, 0.09148719799, 0.09075, ...]",
                "{0.09=6735, 1.09=3263, 0.19=3052, 0.1=2803, 0.091=2390, 0.0901=2137, 0.09001=1906, 0.090001=1717," +
                " 2.09=1594, 3.09=1532}",
                7.165990190806363E111,
                44.08624700002398,
                8.288373000030616
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.085, -0.0899993, -0.09, 5.91, 0.61, 0.11, 5.91, -0.09, -0.09, 2.41, -0.08, -0.09, -0.09," +
                " -0.089, -0.09, 0.91, -0.09, 0.01, -0.09, -0.08, ...]",
                "{-0.09=285193, 0.01=106913, 0.91=95281, -0.08=53792, 2.91=31973, 1.91=31755, 0.21=26965," +
                " -0.089=26868, 0.11=26768, -0.07=13696}",
                1730.4026563818722,
                6.02339199998733,
                2.2115529999964987
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.089987, 2.4487, 0.91, -0.09, 1.91, 0.31, -0.0898, 0.01, 0.05, -0.0892, 4.475," +
                " -0.08999999999998, 5.91, -0.09, -0.08999, 0.81, 3721.91, -0.0899, 0.21, -0.0898, ...]",
                "{-0.09=79582, 0.01=36196, 0.91=33195, -0.08=27112, -0.089=20186, -0.0899=15189, 0.21=14428," +
                " 0.11=14306, 1.91=13893, 2.91=13687}",
                1.745445331927926E14,
                11.870971999989273,
                3.60882599995618
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-0.0899999999999999595063002345937, 2416.41, -0.089987436, 0.09, 24260304162.71," +
                " -0.089999985148166237, 2352046.71, -0.0899999999999999999999999931," +
                " -0.089999999999999999999999999199, -0.0896459, -0.08999999, 1.51161406, 6.91, 784415065.91," +
                " 703.23, 20.76970393, -0.084624905720080488, 2.91, -0.08851280201, -0.08925, ...]",
                "{-0.09=6735, 0.91=3263, 0.01=3052, -0.08=2803, -0.089=2390, -0.0899=2137, -0.08999=1906," +
                " -0.089999=1717, 1.91=1594, 2.91=1532}",
                7.165990190806363E111,
                43.95665900001538,
                8.290905000030733
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[0.005000000001, 7.00001E-7, 1E-12, 6.000000000001, 0.700000000001, 0.200000000001, 6.000000000001," +
                " 1E-12, 1E-12, 2.500000000001, 0.010000000001, 1E-12, 1E-12, 0.001000000001, 1E-12, 1.000000000001," +
                " 1E-12, 0.100000000001, 1E-12, 0.010000000001, ...]",
                "{1E-12=285193, 0.100000000001=106913, 1.000000000001=95281, 0.010000000001=53792," +
                " 3.000000000001=31973, 2.000000000001=31755, 0.300000000001=26965, 0.001000000001=26868," +
                " 0.200000000001=26768, 0.020000000001=13696}",
                1730.4926563837735,
                27.411878999985376,
                12.000161999910091
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[0.000013000001, 2.538700000001, 1.000000000001, 1E-12, 2.000000000001, 0.400000000001," +
                " 0.000200000001, 0.100000000001, 0.140000000001, 0.000800000001, 4.565000000001, 1.02E-12," +
                " 6.000000000001, 1E-12, 0.000010000001, 0.900000000001, 3722.000000000001, 0.000100000001," +
                " 0.300000000001, 0.000200000001, ...]",
                "{1E-12=79582, 0.100000000001=36196, 1.000000000001=33195, 0.010000000001=27112," +
                " 0.001000000001=20186, 0.000100000001=15189, 0.300000000001=14428, 0.200000000001=14306," +
                " 2.000000000001=13893, 3.000000000001=13687}",
                1.7454453319279266E14,
                32.42130799998842,
                12.09146899991106
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[1.0000404936997654063E-12, 2416.500000000001, 0.000012564001, 0.180000000001," +
                " 24260304162.800000000001, 1.4852833763E-8, 2352046.800000000001, 1.0000000000000069E-12," +
                " 1.000000000000000801E-12, 0.000354100001, 1.0001E-8, 1.601614060001, 7.000000000001," +
                " 784415066.000000000001, 703.320000000001, 20.859703930001, 0.005375094280919512, 3.000000000001," +
                " 0.001487197991, 0.000750000001, ...]",
                "{1E-12=6735, 1.000000000001=3263, 0.100000000001=3052, 0.010000000001=2803, 0.001000000001=2390," +
                " 0.000100000001=2137, 0.000010000001=1906, 0.000001000001=1717, 2.000000000001=1594," +
                " 3.000000000001=1532}",
                7.165990190806363E111,
                54.557798000017726,
                13.941664999922093
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "[0.004999999999, 6.99999E-7, -1E-12, 5.999999999999, 0.699999999999, 0.199999999999," +
                " 5.999999999999, -1E-12, -1E-12, 2.499999999999, 0.009999999999, -1E-12, -1E-12, 0.000999999999," +
                " -1E-12, 0.999999999999, -1E-12, 0.099999999999, -1E-12, 0.009999999999, ...]",
                "{-1E-12=285193, 0.099999999999=106913, 0.999999999999=95281, 0.009999999999=53792," +
                " 2.999999999999=31973, 1.999999999999=31755, 0.299999999999=26965, 0.000999999999=26868," +
                " 0.199999999999=26768, 0.019999999999=13696}",
                1730.4926563837735,
                27.41171399998538,
                11.999559999910096
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "[0.000012999999, 2.538699999999, 0.999999999999, -1E-12, 1.999999999999, 0.399999999999," +
                " 0.000199999999, 0.099999999999, 0.139999999999, 0.000799999999, 4.564999999999, -9.8E-13," +
                " 5.999999999999, -1E-12, 0.000009999999, 0.899999999999, 3721.999999999999, 0.000099999999," +
                " 0.299999999999, 0.000199999999, ...]",
                "{-1E-12=79582, 0.099999999999=36196, 0.999999999999=33195, 0.009999999999=27112," +
                " 0.000999999999=20186, 0.000099999999=15189, 0.299999999999=14428, 0.199999999999=14306," +
                " 1.999999999999=13893, 2.999999999999=13687}",
                1.7454453319279266E14,
                32.408068999987364,
                12.073045999911198
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "[-9.999595063002345937E-13, 2416.499999999999, 0.000012563999, 0.179999999999," +
                " 24260304162.799999999999, 1.4850833763E-8, 2352046.799999999999, -9.999999999999931E-13," +
                " -9.99999999999999199E-13, 0.000354099999, 9.999E-9, 1.601614059999, 6.999999999999," +
                " 784415065.999999999999, 703.319999999999, 20.859703929999, 0.005375094278919512, 2.999999999999," +
                " 0.001487197989, 0.000749999999, ...]",
                "{-1E-12=6735, 0.999999999999=3263, 0.099999999999=3052, 0.009999999999=2803, 0.000999999999=2390," +
                " 0.000099999999=2137, 0.000009999999=1906, 9.99999E-7=1717, 1.999999999999=1594," +
                " 2.999999999999=1532}",
                7.165990190806363E111,
                54.520999000017284,
                13.931557999922264
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[1000000000000.005, 1000000000000.0000007, 1000000000000, 1000000000006, 1000000000000.7," +
                " 1000000000000.2, 1000000000006, 1000000000000, 1000000000000, 1000000000002.5, 1000000000000.01," +
                " 1000000000000, 1000000000000, 1000000000000.001, 1000000000000, 1000000000001, 1000000000000," +
                " 1000000000000.1, 1000000000000, 1000000000000.01, ...]",
                "{1000000000000=285193, 1000000000000.1=106913, 1000000000001=95281, 1000000000000.01=53792," +
                " 1000000000003=31973, 1000000000002=31755, 1000000000000.3=26965, 1000000000000.001=26868," +
                " 1000000000000.2=26768, 1000000000000.02=13696}",
                1.0000000017292806E12,
                43.066519999641805,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[1000000000000.000013, 1000000000002.5387, 1000000000001, 1000000000000, 1000000000002," +
                " 1000000000000.4, 1000000000000.0002, 1000000000000.1, 1000000000000.14, 1000000000000.0008," +
                " 1000000000004.565, 1000000000000.00000000000002, 1000000000006, 1000000000000," +
                " 1000000000000.00001, 1000000000000.9, 1000000003722, 1000000000000.0001, 1000000000000.3," +
                " 1000000000000.0002, ...]",
                "{1000000000000=79582, 1000000000000.1=36196, 1000000000001=33195, 1000000000000.01=27112," +
                " 1000000000000.001=20186, 1000000000000.0001=15189, 1000000000000.3=14428, 1000000000000.2=14306," +
                " 1000000000002=13893, 1000000000003=13687}",
                1.7554453319279253E14,
                49.885632999960094,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[1000000000000.0000000000000000404936997654063, 1000000002416.5, 1000000000000.000012564," +
                " 1000000000000.18, 1024260304162.8, 1000000000000.000000014851833763, 1000002352046.8," +
                " 1000000000000.0000000000000000000000000069, 1000000000000.000000000000000000000000000801," +
                " 1000000000000.0003541, 1000000000000.00000001, 1000000000001.60161406, 1000000000007," +
                " 1000784415066, 1000000000703.32, 1000000000020.85970393, 1000000000000.005375094279919512," +
                " 1000000000003, 1000000000000.00148719799, 1000000000000.00075, ...]",
                "{1000000000000=6735, 1000000000001=3263, 1000000000000.1=3052, 1000000000000.01=2803," +
                " 1000000000000.001=2390, 1000000000000.0001=2137, 1000000000000.00001=1906," +
                " 1000000000000.000001=1717, 1000000000002=1594, 1000000000003=1532}",
                7.165990190806363E111,
                71.84684600000809,
                7.9745270000163195
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "[-999999999999.995, -999999999999.9999993, -1000000000000, -999999999994, -999999999999.3," +
                " -999999999999.8, -999999999994, -1000000000000, -1000000000000, -999999999997.5, -999999999999.99," +
                " -1000000000000, -1000000000000, -999999999999.999, -1000000000000, -999999999999, -1000000000000," +
                " -999999999999.9, -1000000000000, -999999999999.99, ...]",
                "{-1000000000000=285193, -999999999999.9=106913, -999999999999=95281, -999999999999.99=53792," +
                " -999999999997=31973, -999999999998=31755, -999999999999.7=26965, -999999999999.999=26868," +
                " -999999999999.8=26768, -999999999999.98=13696}",
                -9.999999982707194E11,
                43.066519999641805,
                0.8587319999978026
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "[-999999999999.999987, -999999999997.4613, -999999999999, -1000000000000, -999999999998," +
                " -999999999999.6, -999999999999.9998, -999999999999.9, -999999999999.86, -999999999999.9992," +
                " -999999999995.435, -999999999999.99999999999998, -999999999994, -1000000000000," +
                " -999999999999.99999, -999999999999.1, -999999996278, -999999999999.9999, -999999999999.7," +
                " -999999999999.9998, ...]",
                "{-1000000000000=79582, -999999999999.9=36196, -999999999999=33195, -999999999999.99=27112," +
                " -999999999999.999=20186, -999999999999.9999=15189, -999999999999.7=14428, -999999999999.8=14306," +
                " -999999999998=13893, -999999999997=13687}",
                1.7354453319279272E14,
                49.885360999959886,
                2.8802499999917677
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "[-999999999999.9999999999999999595063002345937, -999999997583.5, -999999999999.999987436," +
                " -999999999999.82, -975739695837.2, -999999999999.999999985148166237, -999997647953.2," +
                " -999999999999.9999999999999999999999999931, -999999999999.999999999999999999999999999199," +
                " -999999999999.9996459, -999999999999.99999999, -999999999998.39838594, -999999999993," +
                " -999215584934, -999999999296.68, -999999999979.14029607, -999999999999.994624905720080488," +
                " -999999999997, -999999999999.99851280201, -999999999999.99925, ...]",
                "{-1000000000000=6735, -999999999999=3263, -999999999999.9=3052, -999999999999.99=2803," +
                " -999999999999.999=2390, -999999999999.9999=2137, -999999999999.99999=1906," +
                " -999999999999.999999=1717, -999999999998=1594, -999999999997=1532}",
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
            @NotNull String topSampleCount,
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
                "[-0.005, -7E-7, 0, -6, -0.7, -0.2, -6, 0, 0, -2.5, -0.01, 0, 0, -0.001, 0, -1, 0, -0.1, 0, -0.01," +
                " ...]",
                "{0=285193, -0.1=106913, -1=95281, -0.01=53792, -3=31973, -2=31755, -0.3=26965, -0.001=26868," +
                " -0.2=26768, -0.02=13696}",
                -1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0",
                "[-0.000013, -2.5387, -1, 0, -2, -0.4, -0.0002, -0.1, -0.14, -0.0008, -4.565, -2E-14, -6, 0," +
                " -0.00001, -0.9, -3722, -0.0001, -0.3, -0.0002, ...]",
                "{0=79582, -0.1=36196, -1=33195, -0.01=27112, -0.001=20186, -0.0001=15189, -0.3=14428, -0.2=14306," +
                " -2=13893, -3=13687}",
                -1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0",
                "[-4.04936997654063E-17, -2416.5, -0.000012564, -0.18, -24260304162.8, -1.4851833763E-8, -2352046.8," +
                " -6.9E-27, -8.01E-28, -0.0003541, -1E-8, -1.60161406, -7, -784415066, -703.32, -20.85970393," +
                " -0.005375094279919512, -3, -0.00148719799, -0.00075, ...]",
                "{0=6735, -1=3263, -0.1=3052, -0.01=2803, -0.001=2390, -0.0001=2137, -0.00001=1906, -0.000001=1717," +
                " -2=1594, -3=1532}",
                -7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0.0",
                "[-0.005, -7E-7, 0, -6, -0.7, -0.2, -6, 0, 0, -2.5, -0.01, 0, 0, -0.001, 0, -1, 0, -0.1, 0, -0.01," +
                " ...]",
                "{0=285193, -0.1=106913, -1=95281, -0.01=53792, -3=31973, -2=31755, -0.3=26965, -0.001=26868," +
                " -0.2=26768, -0.02=13696}",
                -1730.4926563837735,
                1.7142599999883954,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[-0.000013, -2.5387, -1, 0, -2, -0.4, -0.0002, -0.1, -0.14, -0.0008, -4.565, -2E-14, -6, 0," +
                " -0.00001, -0.9, -3722, -0.0001, -0.3, -0.0002, ...]",
                "{0=79582, -0.1=36196, -1=33195, -0.01=27112, -0.001=20186, -0.0001=15189, -0.3=14428, -0.2=14306," +
                " -2=13893, -3=13687}",
                -1.7454453319279266E14,
                4.809815000004518,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[-4.04936997654063E-17, -2416.5, -0.000012564, -0.18, -24260304162.8, -1.4851833763E-8, -2352046.8," +
                " -6.9E-27, -8.01E-28, -0.0003541, -1E-8, -1.60161406, -7, -784415066, -703.32, -20.85970393," +
                " -0.005375094279919512, -3, -0.00148719799, -0.00075, ...]",
                "{0=6735, -1=3263, -0.1=3052, -0.01=2803, -0.001=2390, -0.0001=2137, -0.00001=1906, -0.000001=1717," +
                " -2=1594, -3=1532}",
                -7.165990190806363E111,
                31.92421400002456,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "[0.995, 0.9999993, 1, -5, 0.3, 0.8, -5, 1, 1, -1.5, 0.99, 1, 1, 0.999, 1, 0, 1, 0.9, 1, 0.99, ...]",
                "{1=285193, 0.9=106913, 0=95281, 0.99=53792, -2=31973, -1=31755, 0.7=26965, 0.999=26868, 0.8=26768," +
                " 0.98=13696}",
                -1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "[0.999987, -1.5387, 0, 1, -1, 0.6, 0.9998, 0.9, 0.86, 0.9992, -3.565, 0.99999999999998, -5, 1," +
                " 0.99999, 0.1, -3721, 0.9999, 0.7, 0.9998, ...]",
                "{1=79582, 0.9=36196, 0=33195, 0.99=27112, 0.999=20186, 0.9999=15189, 0.7=14428, 0.8=14306," +
                " -1=13893, -2=13687}",
                -1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "[0.9999999999999999595063002345937, -2415.5, 0.999987436, 0.82, -24260304161.8," +
                " 0.999999985148166237, -2352045.8, 0.9999999999999999999999999931," +
                " 0.999999999999999999999999999199, 0.9996459, 0.99999999, -0.60161406, -6, -784415065, -702.32," +
                " -19.85970393, 0.994624905720080488, -2, 0.99851280201, 0.99925, ...]",
                "{1=6735, 0=3263, 0.9=3052, 0.99=2803, 0.999=2390, 0.9999=2137, 0.99999=1906, 0.999999=1717," +
                " -1=1594, -2=1532}",
                -7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1",
                "[-1.005, -1.0000007, -1, -7, -1.7, -1.2, -7, -1, -1, -3.5, -1.01, -1, -1, -1.001, -1, -2, -1, -1.1," +
                " -1, -1.01, ...]",
                "{-1=285193, -1.1=106913, -2=95281, -1.01=53792, -4=31973, -3=31755, -1.3=26965, -1.001=26868," +
                " -1.2=26768, -1.02=13696}",
                -1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1",
                "[-1.000013, -3.5387, -2, -1, -3, -1.4, -1.0002, -1.1, -1.14, -1.0008, -5.565, -1.00000000000002," +
                " -7, -1, -1.00001, -1.9, -3723, -1.0001, -1.3, -1.0002, ...]",
                "{-1=79582, -1.1=36196, -2=33195, -1.01=27112, -1.001=20186, -1.0001=15189, -1.3=14428, -1.2=14306," +
                " -3=13893, -4=13687}",
                -1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1",
                "[-1.0000000000000000404936997654063, -2417.5, -1.000012564, -1.18, -24260304163.8," +
                " -1.000000014851833763, -2352047.8, -1.0000000000000000000000000069," +
                " -1.000000000000000000000000000801, -1.0003541, -1.00000001, -2.60161406, -8, -784415067, -704.32," +
                " -21.85970393, -1.005375094279919512, -4, -1.00148719799, -1.00075, ...]",
                "{-1=6735, -2=3263, -1.1=3052, -1.01=2803, -1.001=2390, -1.0001=2137, -1.00001=1906, -1.000001=1717," +
                " -3=1594, -4=1532}",
                -7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[0.995, 0.9999993, 1, -5, 0.3, 0.8, -5, 1, 1, -1.5, 0.99, 1, 1, 0.999, 1, 0, 1, 0.9, 1, 0.99, ...]",
                "{1=285193, 0.9=106913, 0=95281, 0.99=53792, -2=31973, -1=31755, 0.7=26965, 0.999=26868, 0.8=26768," +
                " 0.98=13696}",
                -1729.4926563696547,
                3.995249000025162,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[0.999987, -1.5387, 0, 1, -1, 0.6, 0.9998, 0.9, 0.86, 0.9992, -3.565, 0.99999999999998, -5, 1," +
                " 0.99999, 0.1, -3721, 0.9999, 0.7, 0.9998, ...]",
                "{1=79582, 0.9=36196, 0=33195, 0.99=27112, 0.999=20186, 0.9999=15189, 0.7=14428, 0.8=14306," +
                " -1=13893, -2=13687}",
                -1.745445331927925E14,
                11.5500519999744,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[0.9999999999999999595063002345937, -2415.5, 0.999987436, 0.82, -24260304161.8," +
                " 0.999999985148166237, -2352045.8, 0.9999999999999999999999999931," +
                " 0.999999999999999999999999999199, 0.9996459, 0.99999999, -0.60161406, -6, -784415065, -702.32," +
                " -19.85970393, 0.994624905720080488, -2, 0.99851280201, 0.99925, ...]",
                "{1=6735, 0=3263, 0.9=3052, 0.99=2803, 0.999=2390, 0.9999=2137, 0.99999=1906, 0.999999=1717," +
                " -1=1594, -2=1532}",
                -7.165990190806363E111,
                44.34990300000263,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-1.005, -1.0000007, -1, -7, -1.7, -1.2, -7, -1, -1, -3.5, -1.01, -1, -1, -1.001, -1, -2, -1, -1.1," +
                " -1, -1.01, ...]",
                "{-1=285193, -1.1=106913, -2=95281, -1.01=53792, -4=31973, -3=31755, -1.3=26965, -1.001=26868," +
                " -1.2=26768, -1.02=13696}",
                -1731.4926563880751,
                4.417796000017163,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-1.000013, -3.5387, -2, -1, -3, -1.4, -1.0002, -1.1, -1.14, -1.0008, -5.565, -1.00000000000002," +
                " -7, -1, -1.00001, -1.9, -3723, -1.0001, -1.3, -1.0002, ...]",
                "{-1=79582, -1.1=36196, -2=33195, -1.01=27112, -1.001=20186, -1.0001=15189, -1.3=14428, -1.2=14306," +
                " -3=13893, -4=13687}",
                -1.745445331927928E14,
                11.923568999984075,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-1.0000000000000000404936997654063, -2417.5, -1.000012564, -1.18, -24260304163.8," +
                " -1.000000014851833763, -2352047.8, -1.0000000000000000000000000069," +
                " -1.000000000000000000000000000801, -1.0003541, -1.00000001, -2.60161406, -8, -784415067, -704.32," +
                " -21.85970393, -1.005375094279919512, -4, -1.00148719799, -1.00075, ...]",
                "{-1=6735, -2=3263, -1.1=3052, -1.01=2803, -1.001=2390, -1.0001=2137, -1.00001=1906, -1.000001=1717," +
                " -3=1594, -4=1532}",
                -7.165990190806363E111,
                44.50005600001114,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "[8.995, 8.9999993, 9, 3, 8.3, 8.8, 3, 9, 9, 6.5, 8.99, 9, 9, 8.999, 9, 8, 9, 8.9, 9, 8.99, ...]",
                "{9=285193, 8.9=106913, 8=95281, 8.99=53792, 6=31973, 7=31755, 8.7=26965, 8.999=26868, 8.8=26768," +
                " 8.98=13696}",
                -1721.4926563710835,
                6.656802999978999,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "[8.999987, 6.4613, 8, 9, 7, 8.6, 8.9998, 8.9, 8.86, 8.9992, 4.435, 8.99999999999998, 3, 9, 8.99999," +
                " 8.1, -3713, 8.9999, 8.7, 8.9998, ...]",
                "{9=79582, 8.9=36196, 8=33195, 8.99=27112, 8.999=20186, 8.9999=15189, 8.7=14428, 8.8=14306, 7=13893," +
                " 6=13687}",
                -1.7454453319279066E14,
                14.00533799998072,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "[8.9999999999999999595063002345937, -2407.5, 8.999987436, 8.82, -24260304153.8," +
                " 8.999999985148166237, -2352037.8, 8.9999999999999999999999999931," +
                " 8.999999999999999999999999999199, 8.9996459, 8.99999999, 7.39838594, 2, -784415057, -694.32," +
                " -11.85970393, 8.994624905720080488, 6, 8.99851280201, 8.99925, ...]",
                "{9=6735, 8=3263, 8.9=3052, 8.99=2803, 8.999=2390, 8.9999=2137, 8.99999=1906, 8.999999=1717, 7=1594," +
                " 6=1532}",
                -7.165990190806363E111,
                45.869602000022596,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-9.005, -9.0000007, -9, -15, -9.7, -9.2, -15, -9, -9, -11.5, -9.01, -9, -9, -9.001, -9, -10, -9," +
                " -9.1, -9, -9.01, ...]",
                "{-9=285193, -9.1=106913, -10=95281, -9.01=53792, -12=31973, -11=31755, -9.3=26965, -9.001=26868," +
                " -9.2=26768, -9.02=13696}",
                -1739.4926563856254,
                6.9322569999429255,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-9.000013, -11.5387, -10, -9, -11, -9.4, -9.0002, -9.1, -9.14, -9.0008, -13.565," +
                " -9.00000000000002, -15, -9, -9.00001, -9.9, -3731, -9.0001, -9.3, -9.0002, ...]",
                "{-9=79582, -9.1=36196, -10=33195, -9.01=27112, -9.001=20186, -9.0001=15189, -9.3=14428, -9.2=14306," +
                " -11=13893, -12=13687}",
                -1.7454453319279503E14,
                14.305499999987385,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-9.0000000000000000404936997654063, -2425.5, -9.000012564, -9.18, -24260304171.8," +
                " -9.000000014851833763, -2352055.8, -9.0000000000000000000000000069," +
                " -9.000000000000000000000000000801, -9.0003541, -9.00000001, -10.60161406, -16, -784415075," +
                " -712.32, -29.85970393, -9.005375094279919512, -12, -9.00148719799, -9.00075, ...]",
                "{-9=6735, -10=3263, -9.1=3052, -9.01=2803, -9.001=2390, -9.0001=2137, -9.00001=1906," +
                " -9.000001=1717, -11=1594, -12=1532}",
                -7.165990190806363E111,
                46.02150500002807,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "[9.995, 9.9999993, 10, 4, 9.3, 9.8, 4, 10, 10, 7.5, 9.99, 10, 10, 9.999, 10, 9, 10, 9.9, 10, 9.99," +
                " ...]",
                "{10=285193, 9.9=106913, 9=95281, 9.99=53792, 7=31973, 8=31755, 9.7=26965, 9.999=26868, 9.8=26768," +
                " 9.98=13696}",
                -1720.4926563824695,
                6.695553999971137,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "[9.999987, 7.4613, 9, 10, 8, 9.6, 9.9998, 9.9, 9.86, 9.9992, 5.435, 9.99999999999998, 4, 10," +
                " 9.99999, 9.1, -3712, 9.9999, 9.7, 9.9998, ...]",
                "{10=79582, 9.9=36196, 9=33195, 9.99=27112, 9.999=20186, 9.9999=15189, 9.7=14428, 9.8=14306," +
                " 8=13893, 7=13687}",
                -1.7454453319279047E14,
                14.037556999983535,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "[9.9999999999999999595063002345937, -2406.5, 9.999987436, 9.82, -24260304152.8," +
                " 9.999999985148166237, -2352036.8, 9.9999999999999999999999999931," +
                " 9.999999999999999999999999999199, 9.9996459, 9.99999999, 8.39838594, 3, -784415056, -693.32," +
                " -10.85970393, 9.994624905720080488, 7, 9.99851280201, 9.99925, ...]",
                "{10=6735, 9=3263, 9.9=3052, 9.99=2803, 9.999=2390, 9.9999=2137, 9.99999=1906, 9.999999=1717," +
                " 8=1594, 7=1532}",
                -7.165990190806363E111,
                45.92659800001058,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-10.005, -10.0000007, -10, -16, -10.7, -10.2, -16, -10, -10, -12.5, -10.01, -10, -10, -10.001," +
                " -10, -11, -10, -10.1, -10, -10.01, ...]",
                "{-10=285193, -10.1=106913, -11=95281, -10.01=53792, -13=31973, -12=31755, -10.3=26965," +
                " -10.001=26868, -10.2=26768, -10.02=13696}",
                -1740.4926563798701,
                6.950839999942956,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-10.000013, -12.5387, -11, -10, -12, -10.4, -10.0002, -10.1, -10.14, -10.0008, -14.565," +
                " -10.00000000000002, -16, -10, -10.00001, -10.9, -3732, -10.0001, -10.3, -10.0002, ...]",
                "{-10=79582, -10.1=36196, -11=33195, -10.01=27112, -10.001=20186, -10.0001=15189, -10.3=14428," +
                " -10.2=14306, -12=13893, -13=13687}",
                -1.7454453319279528E14,
                14.348097999995753,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-10.0000000000000000404936997654063, -2426.5, -10.000012564, -10.18, -24260304172.8," +
                " -10.000000014851833763, -2352056.8, -10.0000000000000000000000000069," +
                " -10.000000000000000000000000000801, -10.0003541, -10.00000001, -11.60161406, -17, -784415076," +
                " -713.32, -30.85970393, -10.005375094279919512, -13, -10.00148719799, -10.00075, ...]",
                "{-10=6735, -11=3263, -10.1=3052, -10.01=2803, -10.001=2390, -10.0001=2137, -10.00001=1906," +
                " -10.000001=1717, -12=1594, -13=1532}",
                -7.165990190806363E111,
                46.08150400001817,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "[100.995, 100.9999993, 101, 95, 100.3, 100.8, 95, 101, 101, 98.5, 100.99, 101, 101, 100.999, 101," +
                " 100, 101, 100.9, 101, 100.99, ...]",
                "{101=285193, 100.9=106913, 100=95281, 100.99=53792, 98=31973, 99=31755, 100.7=26965, 100.999=26868," +
                " 100.8=26768, 100.98=13696}",
                -1629.4926563728989,
                9.829158000003313,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "[100.999987, 98.4613, 100, 101, 99, 100.6, 100.9998, 100.9, 100.86, 100.9992, 96.435," +
                " 100.99999999999998, 95, 101, 100.99999, 100.1, -3621, 100.9999, 100.7, 100.9998, ...]",
                "{101=79582, 100.9=36196, 100=33195, 100.99=27112, 100.999=20186, 100.9999=15189, 100.7=14428," +
                " 100.8=14306, 99=13893, 98=13687}",
                -1.7454453319275428E14,
                16.960544999936484,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "[100.9999999999999999595063002345937, -2315.5, 100.999987436, 100.82, -24260304061.8," +
                " 100.999999985148166237, -2351945.8, 100.9999999999999999999999999931," +
                " 100.999999999999999999999999999199, 100.9996459, 100.99999999, 99.39838594, 94, -784414965," +
                " -602.32, 80.14029607, 100.994624905720080488, 98, 100.99851280201, 100.99925, ...]",
                "{101=6735, 100=3263, 100.9=3052, 100.99=2803, 100.999=2390, 100.9999=2137, 100.99999=1906," +
                " 100.999999=1717, 99=1594, 98=1532}",
                -7.165990190806363E111,
                47.68714400002377,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-101.005, -101.0000007, -101, -107, -101.7, -101.2, -107, -101, -101, -103.5, -101.01, -101, -101," +
                " -101.001, -101, -102, -101, -101.1, -101, -101.01, ...]",
                "{-101=285193, -101.1=106913, -102=95281, -101.01=53792, -104=31973, -103=31755, -101.3=26965," +
                " -101.001=26868, -101.2=26768, -101.02=13696}",
                -1831.4926563830088,
                9.922831000003992,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-101.000013, -103.5387, -102, -101, -103, -101.4, -101.0002, -101.1, -101.14, -101.0008, -105.565," +
                " -101.00000000000002, -107, -101, -101.00001, -101.9, -3823, -101.0001, -101.3, -101.0002, ...]",
                "{-101=79582, -101.1=36196, -102=33195, -101.01=27112, -101.001=20186, -101.0001=15189," +
                " -101.3=14428, -101.2=14306, -103=13893, -104=13687}",
                -1.7454453319283166E14,
                17.17193899996032,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-101.0000000000000000404936997654063, -2517.5, -101.000012564, -101.18, -24260304263.8," +
                " -101.000000014851833763, -2352147.8, -101.0000000000000000000000000069," +
                " -101.000000000000000000000000000801, -101.0003541, -101.00000001, -102.60161406, -108, -784415167," +
                " -804.32, -121.85970393, -101.005375094279919512, -104, -101.00148719799, -101.00075, ...]",
                "{-101=6735, -102=3263, -101.1=3052, -101.01=2803, -101.001=2390, -101.0001=2137, -101.00001=1906," +
                " -101.000001=1717, -103=1594, -104=1532}",
                -7.165990190806363E111,
                47.83554400002552,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234566.995, 1234566.9999993, 1234567, 1234561, 1234566.3, 1234566.8, 1234561, 1234567, 1234567," +
                " 1234564.5, 1234566.99, 1234567, 1234567, 1234566.999, 1234567, 1234566, 1234567, 1234566.9," +
                " 1234567, 1234566.99, ...]",
                "{1234567=285193, 1234566.9=106913, 1234566=95281, 1234566.99=53792, 1234564=31973, 1234565=31755," +
                " 1234566.7=26965, 1234566.999=26868, 1234566.8=26768, 1234566.98=13696}",
                1232836.5073433,
                23.698749000022378,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234566.999987, 1234564.4613, 1234566, 1234567, 1234565, 1234566.6, 1234566.9998, 1234566.9," +
                " 1234566.86, 1234566.9992, 1234562.435, 1234566.99999999999998, 1234561, 1234567, 1234566.99999," +
                " 1234566.1, 1230845, 1234566.9999, 1234566.7, 1234566.9998, ...]",
                "{1234567=79582, 1234566.9=36196, 1234566=33195, 1234566.99=27112, 1234566.999=20186," +
                " 1234566.9999=15189, 1234566.7=14428, 1234566.8=14306, 1234565=13893, 1234564=13687}",
                -1.745445319498745E14,
                30.364093999859953,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1234566.9999999999999999595063002345937, 1232150.5, 1234566.999987436, 1234566.82, -24259069595.8," +
                " 1234566.999999985148166237, -1117479.8, 1234566.9999999999999999999999999931," +
                " 1234566.999999999999999999999999999199, 1234566.9996459, 1234566.99999999, 1234565.39838594," +
                " 1234560, -783180499, 1233863.68, 1234546.14029607, 1234566.994624905720080488, 1234564," +
                " 1234566.99851280201, 1234566.99925, ...]",
                "{1234567=6735, 1234566=3263, 1234566.9=3052, 1234566.99=2803, 1234566.999=2390, 1234566.9999=2137," +
                " 1234566.99999=1906, 1234566.999999=1717, 1234565=1594, 1234564=1532}",
                -7.165990190806363E111,
                56.40500900006992,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234567.005, -1234567.0000007, -1234567, -1234573, -1234567.7, -1234567.2, -1234573, -1234567," +
                " -1234567, -1234569.5, -1234567.01, -1234567, -1234567, -1234567.001, -1234567, -1234568, -1234567," +
                " -1234567.1, -1234567, -1234567.01, ...]",
                "{-1234567=285193, -1234567.1=106913, -1234568=95281, -1234567.01=53792, -1234570=31973," +
                " -1234569=31755, -1234567.3=26965, -1234567.001=26868, -1234567.2=26768, -1234567.02=13696}",
                -1236297.4926485345,
                23.699083000022462,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234567.000013, -1234569.5387, -1234568, -1234567, -1234569, -1234567.4, -1234567.0002," +
                " -1234567.1, -1234567.14, -1234567.0008, -1234571.565, -1234567.00000000000002, -1234573, -1234567," +
                " -1234567.00001, -1234567.9, -1238289, -1234567.0001, -1234567.3, -1234567.0002, ...]",
                "{-1234567=79582, -1234567.1=36196, -1234568=33195, -1234567.01=27112, -1234567.001=20186," +
                " -1234567.0001=15189, -1234567.3=14428, -1234567.2=14306, -1234569=13893, -1234570=13687}",
                -1.7454453443670638E14,
                30.376576999862372,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1234567.0000000000000000404936997654063, -1236983.5, -1234567.000012564, -1234567.18," +
                " -24261538729.8, -1234567.000000014851833763, -3586613.8, -1234567.0000000000000000000000000069," +
                " -1234567.000000000000000000000000000801, -1234567.0003541, -1234567.00000001, -1234568.60161406," +
                " -1234574, -785649633, -1235270.32, -1234587.85970393, -1234567.005375094279919512, -1234570," +
                " -1234567.00148719799, -1234567.00075, ...]",
                "{-1234567=6735, -1234568=3263, -1234567.1=3052, -1234567.01=2803, -1234567.001=2390," +
                " -1234567.0001=2137, -1234567.00001=1906, -1234567.000001=1717, -1234569=1594, -1234570=1532}",
                -7.165990190806363E111,
                56.49539900007108,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.085, 0.0899993, 0.09, -5.91, -0.61, -0.11, -5.91, 0.09, 0.09, -2.41, 0.08, 0.09, 0.09, 0.089," +
                " 0.09, -0.91, 0.09, -0.01, 0.09, 0.08, ...]",
                "{0.09=285193, -0.01=106913, -0.91=95281, 0.08=53792, -2.91=31973, -1.91=31755, -0.21=26965," +
                " 0.089=26868, -0.11=26768, 0.07=13696}",
                -1730.4026563818722,
                6.02339199998733,
                2.2115529999964987
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.089987, -2.4487, -0.91, 0.09, -1.91, -0.31, 0.0898, -0.01, -0.05, 0.0892, -4.475," +
                " 0.08999999999998, -5.91, 0.09, 0.08999, -0.81, -3721.91, 0.0899, -0.21, 0.0898, ...]",
                "{0.09=79582, -0.01=36196, -0.91=33195, 0.08=27112, 0.089=20186, 0.0899=15189, -0.21=14428," +
                " -0.11=14306, -1.91=13893, -2.91=13687}",
                -1.745445331927926E14,
                11.870971999989273,
                3.60882599995618
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[0.0899999999999999595063002345937, -2416.41, 0.089987436, -0.09, -24260304162.71," +
                " 0.089999985148166237, -2352046.71, 0.0899999999999999999999999931," +
                " 0.089999999999999999999999999199, 0.0896459, 0.08999999, -1.51161406, -6.91, -784415065.91," +
                " -703.23, -20.76970393, 0.084624905720080488, -2.91, 0.08851280201, 0.08925, ...]",
                "{0.09=6735, -0.91=3263, -0.01=3052, 0.08=2803, 0.089=2390, 0.0899=2137, 0.08999=1906," +
                " 0.089999=1717, -1.91=1594, -2.91=1532}",
                -7.165990190806363E111,
                43.95665900001538,
                8.290905000030733
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.095, -0.0900007, -0.09, -6.09, -0.79, -0.29, -6.09, -0.09, -0.09, -2.59, -0.1, -0.09, -0.09," +
                " -0.091, -0.09, -1.09, -0.09, -0.19, -0.09, -0.1, ...]",
                "{-0.09=285193, -0.19=106913, -1.09=95281, -0.1=53792, -3.09=31973, -2.09=31755, -0.39=26965," +
                " -0.091=26868, -0.29=26768, -0.11=13696}",
                -1730.5826563736073,
                6.4465309999639375,
                2.158562999998543
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.090013, -2.6287, -1.09, -0.09, -2.09, -0.49, -0.0902, -0.19, -0.23, -0.0908, -4.655," +
                " -0.09000000000002, -6.09, -0.09, -0.09001, -0.99, -3722.09, -0.0901, -0.39, -0.0902, ...]",
                "{-0.09=79582, -0.19=36196, -1.09=33195, -0.1=27112, -0.091=20186, -0.0901=15189, -0.39=14428," +
                " -0.29=14306, -2.09=13893, -3.09=13687}",
                -1.7454453319279266E14,
                12.175352999986236,
                3.583485999959521
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-0.0900000000000000404936997654063, -2416.59, -0.090012564, -0.27, -24260304162.89," +
                " -0.090000014851833763, -2352046.89, -0.0900000000000000000000000069," +
                " -0.090000000000000000000000000801, -0.0903541, -0.09000001, -1.69161406, -7.09, -784415066.09," +
                " -703.41, -20.94970393, -0.095375094279919512, -3.09, -0.09148719799, -0.09075, ...]",
                "{-0.09=6735, -1.09=3263, -0.19=3052, -0.1=2803, -0.091=2390, -0.0901=2137, -0.09001=1906," +
                " -0.090001=1717, -2.09=1594, -3.09=1532}",
                -7.165990190806363E111,
                44.08624700002398,
                8.288373000030616
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[-0.004999999999, -6.99999E-7, 1E-12, -5.999999999999, -0.699999999999, -0.199999999999," +
                " -5.999999999999, 1E-12, 1E-12, -2.499999999999, -0.009999999999, 1E-12, 1E-12, -0.000999999999," +
                " 1E-12, -0.999999999999, 1E-12, -0.099999999999, 1E-12, -0.009999999999, ...]",
                "{1E-12=285193, -0.099999999999=106913, -0.999999999999=95281, -0.009999999999=53792," +
                " -2.999999999999=31973, -1.999999999999=31755, -0.299999999999=26965, -0.000999999999=26868," +
                " -0.199999999999=26768, -0.019999999999=13696}",
                -1730.4926563837735,
                27.41171399998538,
                11.999559999910096
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[-0.000012999999, -2.538699999999, -0.999999999999, 1E-12, -1.999999999999, -0.399999999999," +
                " -0.000199999999, -0.099999999999, -0.139999999999, -0.000799999999, -4.564999999999, 9.8E-13," +
                " -5.999999999999, 1E-12, -0.000009999999, -0.899999999999, -3721.999999999999, -0.000099999999," +
                " -0.299999999999, -0.000199999999, ...]",
                "{1E-12=79582, -0.099999999999=36196, -0.999999999999=33195, -0.009999999999=27112," +
                " -0.000999999999=20186, -0.000099999999=15189, -0.299999999999=14428, -0.199999999999=14306," +
                " -1.999999999999=13893, -2.999999999999=13687}",
                -1.7454453319279266E14,
                32.408068999987364,
                12.073045999911198
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[9.999595063002345937E-13, -2416.499999999999, -0.000012563999, -0.179999999999," +
                " -24260304162.799999999999, -1.4850833763E-8, -2352046.799999999999, 9.999999999999931E-13," +
                " 9.99999999999999199E-13, -0.000354099999, -9.999E-9, -1.601614059999, -6.999999999999," +
                " -784415065.999999999999, -703.319999999999, -20.859703929999, -0.005375094278919512," +
                " -2.999999999999, -0.001487197989, -0.000749999999, ...]",
                "{1E-12=6735, -0.999999999999=3263, -0.099999999999=3052, -0.009999999999=2803," +
                " -0.000999999999=2390, -0.000099999999=2137, -0.000009999999=1906, -9.99999E-7=1717," +
                " -1.999999999999=1594, -2.999999999999=1532}",
                -7.165990190806363E111,
                54.520999000017284,
                13.931557999922264
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "[-0.005000000001, -7.00001E-7, -1E-12, -6.000000000001, -0.700000000001, -0.200000000001," +
                " -6.000000000001, -1E-12, -1E-12, -2.500000000001, -0.010000000001, -1E-12, -1E-12," +
                " -0.001000000001, -1E-12, -1.000000000001, -1E-12, -0.100000000001, -1E-12, -0.010000000001, ...]",
                "{-1E-12=285193, -0.100000000001=106913, -1.000000000001=95281, -0.010000000001=53792," +
                " -3.000000000001=31973, -2.000000000001=31755, -0.300000000001=26965, -0.001000000001=26868," +
                " -0.200000000001=26768, -0.020000000001=13696}",
                -1730.4926563837735,
                27.411878999985376,
                12.000161999910091
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "[-0.000013000001, -2.538700000001, -1.000000000001, -1E-12, -2.000000000001, -0.400000000001," +
                " -0.000200000001, -0.100000000001, -0.140000000001, -0.000800000001, -4.565000000001, -1.02E-12," +
                " -6.000000000001, -1E-12, -0.000010000001, -0.900000000001, -3722.000000000001, -0.000100000001," +
                " -0.300000000001, -0.000200000001, ...]",
                "{-1E-12=79582, -0.100000000001=36196, -1.000000000001=33195, -0.010000000001=27112," +
                " -0.001000000001=20186, -0.000100000001=15189, -0.300000000001=14428, -0.200000000001=14306," +
                " -2.000000000001=13893, -3.000000000001=13687}",
                -1.7454453319279266E14,
                32.42130799998842,
                12.09146899991106
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "[-1.0000404936997654063E-12, -2416.500000000001, -0.000012564001, -0.180000000001," +
                " -24260304162.800000000001, -1.4852833763E-8, -2352046.800000000001, -1.0000000000000069E-12," +
                " -1.000000000000000801E-12, -0.000354100001, -1.0001E-8, -1.601614060001, -7.000000000001," +
                " -784415066.000000000001, -703.320000000001, -20.859703930001, -0.005375094280919512," +
                " -3.000000000001, -0.001487197991, -0.000750000001, ...]",
                "{-1E-12=6735, -1.000000000001=3263, -0.100000000001=3052, -0.010000000001=2803," +
                " -0.001000000001=2390, -0.000100000001=2137, -0.000010000001=1906, -0.000001000001=1717," +
                " -2.000000000001=1594, -3.000000000001=1532}",
                -7.165990190806363E111,
                54.557798000017726,
                13.941664999922093
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[999999999999.995, 999999999999.9999993, 1000000000000, 999999999994, 999999999999.3," +
                " 999999999999.8, 999999999994, 1000000000000, 1000000000000, 999999999997.5, 999999999999.99," +
                " 1000000000000, 1000000000000, 999999999999.999, 1000000000000, 999999999999, 1000000000000," +
                " 999999999999.9, 1000000000000, 999999999999.99, ...]",
                "{1000000000000=285193, 999999999999.9=106913, 999999999999=95281, 999999999999.99=53792," +
                " 999999999997=31973, 999999999998=31755, 999999999999.7=26965, 999999999999.999=26868," +
                " 999999999999.8=26768, 999999999999.98=13696}",
                9.999999982707194E11,
                43.066519999641805,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[999999999999.999987, 999999999997.4613, 999999999999, 1000000000000, 999999999998, 999999999999.6," +
                " 999999999999.9998, 999999999999.9, 999999999999.86, 999999999999.9992, 999999999995.435," +
                " 999999999999.99999999999998, 999999999994, 1000000000000, 999999999999.99999, 999999999999.1," +
                " 999999996278, 999999999999.9999, 999999999999.7, 999999999999.9998, ...]",
                "{1000000000000=79582, 999999999999.9=36196, 999999999999=33195, 999999999999.99=27112," +
                " 999999999999.999=20186, 999999999999.9999=15189, 999999999999.7=14428, 999999999999.8=14306," +
                " 999999999998=13893, 999999999997=13687}",
                -1.7354453319279272E14,
                49.885360999959886,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[999999999999.9999999999999999595063002345937, 999999997583.5, 999999999999.999987436," +
                " 999999999999.82, 975739695837.2, 999999999999.999999985148166237, 999997647953.2," +
                " 999999999999.9999999999999999999999999931, 999999999999.999999999999999999999999999199," +
                " 999999999999.9996459, 999999999999.99999999, 999999999998.39838594, 999999999993, 999215584934," +
                " 999999999296.68, 999999999979.14029607, 999999999999.994624905720080488, 999999999997," +
                " 999999999999.99851280201, 999999999999.99925, ...]",
                "{1000000000000=6735, 999999999999=3263, 999999999999.9=3052, 999999999999.99=2803," +
                " 999999999999.999=2390, 999999999999.9999=2137, 999999999999.99999=1906, 999999999999.999999=1717," +
                " 999999999998=1594, 999999999997=1532}",
                -7.165990190806363E111,
                71.79637100000163,
                7.9745270000163195
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "[-1000000000000.005, -1000000000000.0000007, -1000000000000, -1000000000006, -1000000000000.7," +
                " -1000000000000.2, -1000000000006, -1000000000000, -1000000000000, -1000000000002.5," +
                " -1000000000000.01, -1000000000000, -1000000000000, -1000000000000.001, -1000000000000," +
                " -1000000000001, -1000000000000, -1000000000000.1, -1000000000000, -1000000000000.01, ...]",
                "{-1000000000000=285193, -1000000000000.1=106913, -1000000000001=95281, -1000000000000.01=53792," +
                " -1000000000003=31973, -1000000000002=31755, -1000000000000.3=26965, -1000000000000.001=26868," +
                " -1000000000000.2=26768, -1000000000000.02=13696}",
                -1.0000000017292806E12,
                43.066519999641805,
                0.8587319999978026
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "[-1000000000000.000013, -1000000000002.5387, -1000000000001, -1000000000000, -1000000000002," +
                " -1000000000000.4, -1000000000000.0002, -1000000000000.1, -1000000000000.14, -1000000000000.0008," +
                " -1000000000004.565, -1000000000000.00000000000002, -1000000000006, -1000000000000," +
                " -1000000000000.00001, -1000000000000.9, -1000000003722, -1000000000000.0001, -1000000000000.3," +
                " -1000000000000.0002, ...]",
                "{-1000000000000=79582, -1000000000000.1=36196, -1000000000001=33195, -1000000000000.01=27112," +
                " -1000000000000.001=20186, -1000000000000.0001=15189, -1000000000000.3=14428," +
                " -1000000000000.2=14306, -1000000000002=13893, -1000000000003=13687}",
                -1.7554453319279253E14,
                49.885632999960094,
                2.8802499999917677
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "[-1000000000000.0000000000000000404936997654063, -1000000002416.5, -1000000000000.000012564," +
                " -1000000000000.18, -1024260304162.8, -1000000000000.000000014851833763, -1000002352046.8," +
                " -1000000000000.0000000000000000000000000069, -1000000000000.000000000000000000000000000801," +
                " -1000000000000.0003541, -1000000000000.00000001, -1000000000001.60161406, -1000000000007," +
                " -1000784415066, -1000000000703.32, -1000000000020.85970393, -1000000000000.005375094279919512," +
                " -1000000000003, -1000000000000.00148719799, -1000000000000.00075, ...]",
                "{-1000000000000=6735, -1000000000001=3263, -1000000000000.1=3052, -1000000000000.01=2803," +
                " -1000000000000.001=2390, -1000000000000.0001=2137, -1000000000000.00001=1906," +
                " -1000000000000.000001=1717, -1000000000002=1594, -1000000000003=1532}",
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
            @NotNull String topSampleCount,
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
                "[0.835, 1, 0.98427296, 0.2, 0, 0.6, 0.29, 1, 0.8, 0.3, 1, 0.5, 1, 1, 0, 0.4, 0.6, 0.3, 0.7, 0, ...]",
                "{1=250081, 0=249878, 0.8=27946, 0.4=27923, 0.1=27830, 0.9=27818, 0.3=27816, 0.7=27795, 0.2=27749," +
                " 0.6=27730}",
                0.5003934009792074,
                3.2073840000042164,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1",
                "[0.340254, 0.98427296, 0.86723, 0.08474, 0.64, 0.5712606, 0.83, 1, 1, 0.13194, 0.911719, 0, 0.9," +
                " 0.9, 0.9, 0.6045563, 0.745, 0.5418567, 0.68515816508, 0.4, ...]",
                "{0=124913, 1=124890, 0.6=21083, 0.8=20986, 0.9=20917, 0.2=20871, 0.5=20772, 0.1=20749, 0.7=20692," +
                " 0.3=20557}",
                0.5003081581798811,
                9.489134999999093,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1",
                "[0.7856933403177794690940379029766, 0.20932, 0.22822779, 0.94282, 0.2083329538322841702208107727," +
                " 0.28341763665, 0.16422, 1, 0.00911462089493519, 0.1345241244719, 0.70661929, 0.62772, 0.75, 0.215," +
                " 0.3, 0.6, 0.7782, 0.3357467208, 0.070765, 0.08, ...]",
                "{0=55696, 1=55448, 0.9=11181, 0.2=11113, 0.6=11102, 0.1=10995, 0.3=10963, 0.4=10938, 0.7=10910," +
                " 0.8=10880}",
                0.499968809465756,
                25.829786999967226,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "3",
                "[2, 0, 2.9, 3, 0, 3, 0, 1, 0, 0, 0, 0, 0, 2, 0.3, 2.1, 0, 0, 0, 3, ...]",
                "{0=612673, 2=68007, 3=67983, 1=67810, 0.8=3520, 2.6=3520, 0.2=3483, 2.5=3481, 2.8=3461, 1.1=3450}",
                0.6828048445016695,
                1.690074999998684,
                0.3674749999992351
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "3",
                "[0.8474, 1.3194, 0, 1.23, 0, 1.5762104411, 1.2, 2.8254, 0, 3, 2.11, 0.7, 0, 0.61, 0.3, 0, 0.1, 1," +
                " 0.215, 0, ...]",
                "{0=350149, 2=58686, 1=58588, 3=58117, 2.6=4483, 0.9=4481, 1.9=4465, 0.7=4462, 0.2=4460, 1.5=4458}",
                1.0625945646729795,
                6.920348000009579,
                1.8981119999935625
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "3",
                "[2.0932, 2.2822779, 2.083329538322841702208107727, 2.8341763665, 1.6422, 0.0911462089493519," +
                " 1.345241244719, 2.15, 3, 0.70765, 0.8, 2.13, 3, 0, 2.3, 0.819, 1.54, 1.656238087623776, 2.84," +
                " 2.210077318906600384831704918232, ...]",
                "{0=171467, 3=33879, 2=33742, 1=33378, 1.9=3080, 1.2=3073, 2.8=3063, 2.2=3043, 1.7=3015, 1.3=3015}",
                1.2938927250945065,
                22.428396000032464,
                6.556452000009386
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1E+6",
                "[835000, 1000000, 984272.96, 200000, 0, 600000, 290000, 1000000, 800000, 300000, 1000000, 500000," +
                " 1000000, 1000000, 0, 400000, 600000, 300000, 700000, 0, ...]",
                "{1000000=250081, 0=249878, 800000=27946, 400000=27923, 100000=27830, 900000=27818, 300000=27816," +
                " 700000=27795, 200000=27749, 600000=27730}",
                500393.40098079835,
                14.572748999867288,
                0.015543999999999086
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1E+6",
                "[340254, 984272.96, 867230, 84740, 640000, 571260.6, 830000, 1000000, 1000000, 131940, 911719, 0," +
                " 900000, 900000, 900000, 604556.3, 745000, 541856.7, 685158.16508, 400000, ...]",
                "{0=124913, 1000000=124890, 600000=21083, 800000=20986, 900000=20917, 200000=20871, 500000=20772," +
                " 100000=20749, 700000=20692, 300000=20557}",
                500308.1581803239,
                18.536230999894144,
                0.5365789999993211
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1E+6",
                "[785693.3403177794690940379029766, 209320, 228227.79, 942820, 208332.9538322841702208107727," +
                " 283417.63665, 164220, 1000000, 9114.62089493519, 134524.1244719, 706619.29, 627720, 750000," +
                " 215000, 300000, 600000, 778200, 335746.7208, 70765, 80000, ...]",
                "{0=55696, 1000000=55448, 900000=11181, 200000=11113, 600000=11102, 100000=10995, 300000=10963," +
                " 400000=10938, 700000=10910, 800000=10880}",
                499968.80946587696,
                31.090607999969805,
                3.9439019999964624
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.000001",
                "[8.35E-7, 0.000001, 9.8427296E-7, 2E-7, 0, 6E-7, 2.9E-7, 0.000001, 8E-7, 3E-7, 0.000001, 5E-7," +
                " 0.000001, 0.000001, 0, 4E-7, 6E-7, 3E-7, 7E-7, 0, ...]",
                "{0.000001=250081, 0=249878, 8E-7=27946, 4E-7=27923, 1E-7=27830, 9E-7=27818, 3E-7=27816, 7E-7=27795," +
                " 2E-7=27749, 6E-7=27730}",
                5.003934009778121E-7,
                3.2073840000042164,
                5.500212000006544
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.000001",
                "[3.40254E-7, 9.8427296E-7, 8.6723E-7, 8.474E-8, 6.4E-7, 5.712606E-7, 8.3E-7, 0.000001, 0.000001," +
                " 1.3194E-7, 9.11719E-7, 0, 9E-7, 9E-7, 9E-7, 6.045563E-7, 7.45E-7, 5.418567E-7, 6.8515816508E-7," +
                " 4E-7, ...]",
                "{0=124913, 0.000001=124890, 6E-7=21083, 8E-7=20986, 9E-7=20917, 2E-7=20871, 5E-7=20772, 1E-7=20749," +
                " 7E-7=20692, 3E-7=20557}",
                5.003081581787221E-7,
                9.489134999999093,
                8.257126000016854
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.000001",
                "[7.856933403177794690940379029766E-7, 2.0932E-7, 2.2822779E-7, 9.4282E-7," +
                " 2.083329538322841702208107727E-7, 2.8341763665E-7, 1.6422E-7, 0.000001, 9.11462089493519E-9," +
                " 1.345241244719E-7, 7.0661929E-7, 6.2772E-7, 7.5E-7, 2.15E-7, 3E-7, 6E-7, 7.782E-7, 3.357467208E-7," +
                " 7.0765E-8, 8E-8, ...]",
                "{0=55696, 0.000001=55448, 9E-7=11181, 2E-7=11113, 6E-7=11102, 1E-7=10995, 3E-7=10963, 4E-7=10938," +
                " 7E-7=10910, 8E-7=10880}",
                4.999688094651478E-7,
                25.829786999967226,
                13.663255000001989
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "3",
                "[3, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1.3, 1, 1, 1, 1, 1, 1.96, 1, 2.06, ...]",
                "{1=703259, 2=78016, 3=77976, 1.8=4035, 1.2=3999, 2.1=3950, 2.2=3922, 2.7=3913, 2.8=3907, 1.6=3906}",
                1.3746588393634007,
                2.1531109999821383,
                0.28217299999993095
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "3",
                "[1.8474, 2.3194, 1, 2.23, 1, 2.5762104411, 2.2, 1, 1.7, 1, 1.61, 1.3, 1, 1.1, 2, 1.215, 1, 2.575," +
                " 2.0111768, 1, ...]",
                "{1=447364, 3=74969, 2=74619, 2.6=5747, 1.2=5721, 1.9=5713, 2.9=5680, 2.2=5664, 2.5=5663, 1.4=5656}",
                1.6275508786442359,
                6.670948000040874,
                1.6095569999951769
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "3",
                "[2.6422, 1.0911462089493519, 2.345241244719, 1.70765, 1.8, 1, 1.819, 2.54, 2.656238087623776," +
                " 1.223823, 2.82282, 1.16551102, 2.5, 2.4887302, 1.8227333913, 1, 3, 2.97633654, 1, 3, ...]",
                "{1=236373, 3=46831, 2=46285, 2.9=4270, 2.2=4230, 2.3=4203, 2.7=4180, 1.7=4179, 1.8=4178, 2.5=4170}",
                1.8105920429697409,
                21.450927000054385,
                6.040454000006691
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1E+6",
                "[835001, 984273.96, 200001, 1, 600001, 290001, 800001, 300001, 500001, 1, 400001, 600001, 300001," +
                " 700001, 1, 100001, 1, 1, 1, 1, ...]",
                "{1=333669, 400001=37141, 800001=37101, 100001=37097, 700001=37084, 900001=37045, 600001=36973," +
                " 300001=36971, 500001=36919, 200001=36898}",
                333458.38886693533,
                13.087861999855248,
                0.020790999999999407
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1E+6",
                "[340255, 984273.96, 867231, 84741, 640001, 571261.6, 830001, 131941, 911720, 1, 900001, 900001," +
                " 900001, 604557.3, 745001, 541857.7, 685159.16508, 400001, 577714.9075, 123001, ...]",
                "{1=142481, 600001=24105, 800001=24001, 900001=23863, 200001=23848, 100001=23804, 700001=23744," +
                " 500001=23740, 300001=23453, 400001=23390}",
                428993.13684824604,
                18.472173999913938,
                0.6126289999991997
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1E+6",
                "[785694.3403177794690940379029766, 209321, 228228.79, 942821, 208333.9538322841702208107727," +
                " 283418.63665, 164221, 9115.62089493519, 134525.1244719, 706620.29, 627721, 750001, 215001, 300001," +
                " 600001, 778201, 335747.7208, 70766, 80001, 922961, ...]",
                "{1=58998, 900001=11796, 200001=11738, 600001=11722, 400001=11614, 300001=11609, 100001=11603," +
                " 700001=11577, 500001=11524, 800001=11514}",
                470704.2297839316,
                31.80035300002205,
                4.175350999997326
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1",
                "0",
                "[-0.165, 0, -0.01572704, -0.8, -1, -0.4, -0.71, 0, -0.2, -0.7, 0, -0.5, 0, 0, -1, -0.6, -0.4, -0.7," +
                " -0.3, -1, ...]",
                "{0=250081, -1=249878, -0.2=27946, -0.6=27923, -0.9=27830, -0.1=27818, -0.7=27816, -0.3=27795," +
                " -0.8=27749, -0.4=27730}",
                -0.4996065990175754,
                3.2042220000040937,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1",
                "0",
                "[-0.659746, -0.01572704, -0.13277, -0.91526, -0.36, -0.4287394, -0.17, 0, 0, -0.86806, -0.088281," +
                " -1, -0.1, -0.1, -0.1, -0.3954437, -0.255, -0.4581433, -0.31484183492, -0.6, ...]",
                "{-1=124913, 0=124890, -0.4=21083, -0.2=20986, -0.1=20917, -0.8=20871, -0.5=20772, -0.9=20749," +
                " -0.3=20692, -0.7=20557}",
                -0.49969184181920573,
                9.487877999999462,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1",
                "0",
                "[-0.2143066596822205309059620970234, -0.79068, -0.77177221, -0.05718," +
                " -0.7916670461677158297791892273, -0.71658236335, -0.83578, 0, -0.99088537910506481," +
                " -0.8654758755281, -0.29338071, -0.37228, -0.25, -0.785, -0.7, -0.4, -0.2218, -0.6642532792," +
                " -0.929235, -0.92, ...]",
                "{-1=55696, 0=55448, -0.1=11181, -0.8=11113, -0.4=11102, -0.9=10995, -0.7=10963, -0.6=10938," +
                " -0.3=10910, -0.2=10880}",
                -0.5000311905339897,
                25.82962599996815,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "0",
                "[-1, -3, -0.1, 0, -3, 0, -3, -2, -3, -3, -3, -3, -3, -1, -2.7, -0.9, -3, -3, -3, 0, ...]",
                "{-3=612673, -1=68007, 0=67983, -2=67810, -2.2=3520, -0.4=3520, -2.8=3483, -0.5=3481, -0.2=3461," +
                " -1.9=3450}",
                -2.3171951554812953,
                2.7800909999737797,
                0.3674749999992351
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "0",
                "[-2.1526, -1.6806, -3, -1.77, -3, -1.4237895589, -1.8, -0.1746, -3, 0, -0.89, -2.3, -3, -2.39," +
                " -2.7, -3, -2.9, -2, -2.785, -3, ...]",
                "{-3=350149, -1=58686, -2=58588, 0=58117, -0.4=4483, -2.1=4481, -1.1=4465, -2.3=4462, -2.8=4460," +
                " -1.5=4458}",
                -1.937405435315748,
                7.5025860000388604,
                1.8981119999935625
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "0",
                "[-0.9068, -0.7177221, -0.916670461677158297791892273, -0.1658236335, -1.3578, -2.9088537910506481," +
                " -1.654758755281, -0.85, 0, -2.29235, -2.2, -0.87, 0, -3, -0.7, -2.181, -1.46, -1.343761912376224," +
                " -0.16, -0.789922681093399615168295081768, ...]",
                "{-3=171467, 0=33879, -1=33742, -2=33378, -1.1=3080, -1.8=3073, -0.2=3063, -0.8=3043, -1.3=3015," +
                " -1.7=3015}",
                -1.7061072748996227,
                22.703752999992012,
                6.556452000009386
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "0",
                "[-165000, 0, -15727.04, -800000, -1000000, -400000, -710000, 0, -200000, -700000, 0, -500000, 0, 0," +
                " -1000000, -600000, -400000, -700000, -300000, -1000000, ...]",
                "{0=250081, -1000000=249878, -200000=27946, -600000=27923, -900000=27830, -100000=27818," +
                " -700000=27816, -300000=27795, -800000=27749, -400000=27730}",
                -499606.5990192018,
                14.566150999867714,
                0.015543999999999086
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "0",
                "[-659746, -15727.04, -132770, -915260, -360000, -428739.4, -170000, 0, 0, -868060, -88281," +
                " -1000000, -100000, -100000, -100000, -395443.7, -255000, -458143.3, -314841.83492, -600000, ...]",
                "{-1000000=124913, 0=124890, -400000=21083, -200000=20986, -100000=20917, -800000=20871," +
                " -500000=20772, -900000=20749, -300000=20692, -700000=20557}",
                -499691.841819675,
                18.53413699989557,
                0.5365789999993211
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "0",
                "[-214306.6596822205309059620970234, -790680, -771772.21, -57180, -791667.0461677158297791892273," +
                " -716582.36335, -835780, 0, -990885.37910506481, -865475.8755281, -293380.71, -372280, -250000," +
                " -785000, -700000, -400000, -221800, -664253.2792, -929235, -920000, ...]",
                "{-1000000=55696, 0=55448, -100000=11181, -800000=11113, -400000=11102, -900000=10995," +
                " -700000=10963, -600000=10938, -300000=10910, -200000=10880}",
                -500031.1905341227,
                31.095132999971124,
                3.9439019999964624
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-0.000001",
                "0",
                "[-1.65E-7, 0, -1.572704E-8, -8E-7, -0.000001, -4E-7, -7.1E-7, 0, -2E-7, -7E-7, 0, -5E-7, 0, 0," +
                " -0.000001, -6E-7, -4E-7, -7E-7, -3E-7, -0.000001, ...]",
                "{0=250081, -0.000001=249878, -2E-7=27946, -6E-7=27923, -9E-7=27830, -1E-7=27818, -7E-7=27816," +
                " -3E-7=27795, -8E-7=27749, -4E-7=27730}",
                -4.996065990161529E-7,
                3.2042220000040937,
                5.498994000006578
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-0.000001",
                "0",
                "[-6.59746E-7, -1.572704E-8, -1.3277E-7, -9.1526E-7, -3.6E-7, -4.287394E-7, -1.7E-7, 0, 0," +
                " -8.6806E-7, -8.8281E-8, -0.000001, -1E-7, -1E-7, -1E-7, -3.954437E-7, -2.55E-7, -4.581433E-7," +
                " -3.1484183492E-7, -6E-7, ...]",
                "{-0.000001=124913, 0=124890, -4E-7=21083, -2E-7=20986, -1E-7=20917, -8E-7=20871, -5E-7=20772," +
                " -9E-7=20749, -3E-7=20692, -7E-7=20557}",
                -4.996918418180518E-7,
                9.487877999999462,
                8.257264000016773
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-0.000001",
                "0",
                "[-2.143066596822205309059620970234E-7, -7.9068E-7, -7.7177221E-7, -5.718E-8," +
                " -7.916670461677158297791892273E-7, -7.1658236335E-7, -8.3578E-7, 0, -9.9088537910506481E-7," +
                " -8.654758755281E-7, -2.9338071E-7, -3.7228E-7, -2.5E-7, -7.85E-7, -7E-7, -4E-7, -2.218E-7," +
                " -6.642532792E-7, -9.29235E-7, -9.2E-7, ...]",
                "{-0.000001=55696, 0=55448, -1E-7=11181, -8E-7=11113, -4E-7=11102, -9E-7=10995, -7E-7=10963," +
                " -6E-7=10938, -3E-7=10910, -2E-7=10880}",
                -5.000311905333693E-7,
                25.82962599996815,
                13.66474300000205
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "-1",
                "[-1, -3, -3, -3, -2, -3, -3, -3, -3, -3, -1, -2.7, -3, -3, -3, -3, -3, -2.04, -3, -1.94, ...]",
                "{-3=703259, -2=78016, -1=77976, -2.2=4035, -2.8=3999, -1.9=3950, -1.8=3922, -1.3=3913, -1.2=3907," +
                " -2.4=3906}",
                -2.625341160605808,
                2.778448999970333,
                0.28217299999993095
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "-1",
                "[-2.1526, -1.6806, -3, -1.77, -3, -1.4237895589, -1.8, -3, -2.3, -3, -2.39, -2.7, -3, -2.9, -2," +
                " -2.785, -3, -1.425, -1.9888232, -3, ...]",
                "{-3=447364, -1=74969, -2=74619, -1.4=5747, -2.8=5721, -2.1=5713, -1.1=5680, -1.8=5664, -1.5=5663," +
                " -2.6=5656}",
                -2.372449121331089,
                7.043562000040137,
                1.6095569999951769
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "-1",
                "[-1.3578, -2.9088537910506481, -1.654758755281, -2.29235, -2.2, -3, -2.181, -1.46," +
                " -1.343761912376224, -2.776177, -1.17718, -2.83448898, -1.5, -1.5112698, -2.1772666087, -3, -1," +
                " -1.02366346, -3, -1, ...]",
                "{-3=236373, -1=46831, -2=46285, -1.1=4270, -1.8=4230, -1.7=4203, -1.3=4180, -2.3=4179, -2.2=4178," +
                " -1.5=4170}",
                -2.189407957015854,
                21.64043100000685,
                6.040454000006691
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "-1",
                "[-165000, -15727.04, -800000, -1000000, -400000, -710000, -200000, -700000, -500000, -1000000," +
                " -600000, -400000, -700000, -300000, -1000000, -900000, -1000000, -1000000, -1000000, -1000000, ...]",
                "{-1000000=333669, -600000=37141, -200000=37101, -900000=37097, -300000=37084, -100000=37045," +
                " -400000=36973, -700000=36971, -500000=36919, -800000=36898}",
                -666542.6111339338,
                19.424939999816562,
                0.020790999999999407
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "-1",
                "[-659746, -15727.04, -132770, -915260, -360000, -428739.4, -170000, -868060, -88281, -1000000," +
                " -100000, -100000, -100000, -395443.7, -255000, -458143.3, -314841.83492, -600000, -422286.0925," +
                " -877000, ...]",
                "{-1000000=142481, -400000=24105, -200000=24001, -100000=23863, -800000=23848, -900000=23804," +
                " -300000=23744, -500000=23740, -700000=23453, -600000=23390}",
                -571007.8631520575,
                21.17717899989599,
                0.6126289999991997
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "-1",
                "[-214306.6596822205309059620970234, -790680, -771772.21, -57180, -791667.0461677158297791892273," +
                " -716582.36335, -835780, -990885.37910506481, -865475.8755281, -293380.71, -372280, -250000," +
                " -785000, -700000, -400000, -221800, -664253.2792, -929235, -920000, -77040, ...]",
                "{-1000000=58998, -100000=11796, -800000=11738, -400000=11722, -600000=11614, -700000=11609," +
                " -900000=11603, -300000=11577, -500000=11524, -200000=11514}",
                -529296.7702161488,
                32.91999899996857,
                4.175350999997326
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "100",
                "101",
                "[100.835, 101, 100.98427296, 100.2, 100, 100.6, 100.29, 101, 100.8, 100.3, 101, 100.5, 101, 101," +
                " 100, 100.4, 100.6, 100.3, 100.7, 100, ...]",
                "{101=250081, 100=249878, 100.8=27946, 100.4=27923, 100.1=27830, 100.9=27818, 100.3=27816," +
                " 100.7=27795, 100.2=27749, 100.6=27730}",
                100.50039340172313,
                10.28381199998929,
                0.9994799999976852
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "100",
                "101",
                "[100.340254, 100.98427296, 100.86723, 100.08474, 100.64, 100.5712606, 100.83, 101, 101, 100.13194," +
                " 100.911719, 100, 100.9, 100.9, 100.9, 100.6045563, 100.745, 100.5418567, 100.68515816508, 100.4," +
                " ...]",
                "{100=124913, 101=124890, 100.6=21083, 100.8=20986, 100.9=20917, 100.2=20871, 100.5=20772," +
                " 100.1=20749, 100.7=20692, 100.3=20557}",
                100.50030815855573,
                16.994920999929292,
                3.00660399999138
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "100",
                "101",
                "[100.7856933403177794690940379029766, 100.20932, 100.22822779, 100.94282," +
                " 100.2083329538322841702208107727, 100.28341763665, 100.16422, 101, 100.00911462089493519," +
                " 100.1345241244719, 100.70661929, 100.62772, 100.75, 100.215, 100.3, 100.6, 100.7782," +
                " 100.3357467208, 100.070765, 100.08, ...]",
                "{100=55696, 101=55448, 100.9=11181, 100.2=11113, 100.6=11102, 100.1=10995, 100.3=10963," +
                " 100.4=10938, 100.7=10910, 100.8=10880}",
                100.49996880963798,
                33.63301799986874,
                7.997431000016831
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "2.7183",
                "3.1416",
                "[2.9183, 2.7183, 3.0083, 3.0183, 2.7183, 3.1183, 3.0183, 2.7183, 2.8183, 2.7183, 2.7183, 2.7183," +
                " 2.7183, 3.1183, 3.0673, 2.7183, 3.1183, 2.9183, 2.7483, 2.9283, ...]",
                "{2.7183=535610, 3.1183=59718, 3.0183=59498, 2.9183=59450, 2.8183=59384, 2.7983=3091, 3.0983=3050," +
                " 2.9683=3049, 2.7383=3046, 2.9783=3039}",
                2.8257661906273217,
                15.1930399998104,
                4.053552000024533
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "2.7183",
                "3.1416",
                "[3.058554, 2.80304, 2.85024, 2.7183, 3.1183, 2.8413, 2.7183, 2.87592104411, 2.8383, 3.00084," +
                " 2.7183, 3.0183, 2.9293, 2.7883, 2.7183, 2.7793, 2.7483, 2.7183, 2.7283, 2.8183, ...]",
                "{2.7183=279817, 2.9183=46985, 2.8183=46701, 3.1183=46182, 3.0183=46181, 3.0983=3632, 2.8083=3605," +
                " 3.0783=3600, 2.7383=3598, 3.0083=3588}",
                2.8775768745633243,
                18.06730499998356,
                4.893506999967964
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "2.7183",
                "3.1416",
                "[2.92762, 2.94652779, 2.9266329538322841702208107727, 3.00171763665, 2.88252, 2.72741462089493519," +
                " 2.8528241244719, 2.9333, 3.0183, 3.0540467208, 2.789065, 2.7983, 2.9313, 3.0183, 2.7183, 2.9483," +
                " 2.8002, 2.8723, 2.8839238087623776, 3.0023, ...]",
                "{2.7183=128794, 2.9183=25391, 3.0183=25359, 3.1183=25322, 2.8183=25189, 2.9383=2319, 3.0883=2317," +
                " 2.8383=2314, 3.1283=2296, 2.9083=2296}",
                2.906521168534309,
                31.36289500038055,
                8.862022999959645
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3.1416",
                "2.7183",
                "[-1.1416, -3.1416, -0.2416, -0.1416, 1.8584, -3.1416, 0.8584, -0.1416, -3.1416, -2.1416, -3.1416," +
                " -3.1416, -3.1416, -3.1416, 0.8584, 0.3484, -3.1416, 0.8584, -1.1416, -2.8416, ...]",
                "{-3.1416=467037, 0.8584=51988, -1.1416=51835, -0.1416=51807, -2.1416=51760, 1.8584=51720," +
                " -2.3416=2686, 0.6584=2678, 2.0584=2672, -0.3416=2663}",
                -1.5601724613262047,
                14.495435999905277,
                4.032119000027335
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3.1416",
                "2.7183",
                "[0.26094, -2.2942, 2.571006, -1.8222, -3.1416, 2.276967, 0.8584, 2.635539075, -1.9116, 1.8584," +
                " -3.1416, -1.5653895589, 1.36043, -1.9416, 1.1654, 2.0844, -0.3162, -3.1416, -0.1416, -1.0316, ...]",
                "{-3.1416=223387, -1.1416=37393, -2.1416=37356, 1.8584=37273, -0.1416=36900, 0.8584=36710," +
                " 0.6584=2901, -2.9416=2894, 2.3584=2880, -2.2416=2865}",
                -0.853578161847082,
                16.58768599994705,
                4.740488999975314
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3.1416",
                "2.7183",
                "[-1.0484, -0.8593221, -1.058270461677158297791892273, -0.3074236335, -1.4994, -3.0504537910506481," +
                " -1.796358755281, -0.9916, -0.1416, 0.215867208, -2.43395, -2.3416, -1.0116, -0.1416, -3.1416," +
                " -0.8416, -2.3226, -1.6016, -1.485361912376224, 2.25160786, ...]",
                "{-3.1416=97211, -0.1416=19283, -1.1416=19261, 0.8584=19142, -2.1416=19098, 1.8584=19079," +
                " -0.9416=1755, 0.2584=1750, -1.9416=1747, -1.2416=1746}",
                -0.4913261055680533,
                29.00922700015098,
                8.51985399994396
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0.0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1",
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]",
                "{1=1000000}",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1",
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]",
                "{1=1000000}",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1",
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]",
                "{1=1000000}",
                1.000000000007918,
                1.000000000007918,
                0.0
        );
        rangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
        rangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
        rangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
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
