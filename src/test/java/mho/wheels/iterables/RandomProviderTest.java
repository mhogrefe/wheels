package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.Either;
import mho.wheels.structures.NullableOptional;
import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class RandomProviderTest {
    private static RandomProvider P;
    private static RandomProvider Q;
    private static RandomProvider R;

    @Before
    public void initialize() {
        P = RandomProvider.example();
        Q = new RandomProvider(toList(replicate(IsaacPRNG.SIZE, 0)));
        R = new RandomProvider(toList(IterableUtils.range(1, IsaacPRNG.SIZE)));
    }

    @Test
    public void testConstructor() {
        RandomProvider provider = new RandomProvider();
        aeq(provider.getScale(), 32);
        aeq(provider.getSecondaryScale(), 8);
        aeq(provider.getTertiaryScale(), 2);
    }

    @Test
    public void testConstructor_int() {
        aeq(Q, "RandomProvider[@-7948823947390831374, 32, 8, 2]");
        aeq(R, "RandomProvider[@2449928962525148503, 32, 8, 2]");
        aeq(
                new RandomProvider(toList(IterableUtils.rangeBy(-1, -1, -IsaacPRNG.SIZE))),
                "RandomProvider[@3417306423260907531, 32, 8, 2]"
        );
    }

    @Test
    public void testExample() {
        aeq(RandomProvider.example(), "RandomProvider[@-8800290164235921060, 32, 8, 2]");
    }

    @Test
    public void testGetScale() {
        aeq(P.getScale(), 32);
        aeq(new RandomProvider().withScale(100).getScale(), 100);
        aeq(new RandomProvider().withScale(3).getScale(), 3);
    }

    @Test
    public void testGetSecondaryScale() {
        aeq(P.getSecondaryScale(), 8);
        aeq(new RandomProvider().withSecondaryScale(100).getSecondaryScale(), 100);
        aeq(new RandomProvider().withSecondaryScale(3).getSecondaryScale(), 3);
    }

    @Test
    public void testGetTertiaryScale() {
        aeq(P.getTertiaryScale(), 2);
        aeq(new RandomProvider().withTertiaryScale(100).getTertiaryScale(), 100);
        aeq(new RandomProvider().withTertiaryScale(3).getTertiaryScale(), 3);
    }

    @Test
    public void testGetSeed() {
        aeq(
                P.getSeed(),
                "[-1740315277, -1661427768, 842676458, -1268128447, -121858045, 1559496322, -581535260, -1819723670," +
                " -334232530, 244755020, -534964695, 301563516, -1795957210, 1451814771, 1299826235, -666749112," +
                " -1729602324, -565031294, 1897952431, 1118663606, -299718943, -1499922009, -837624734, 1439650052," +
                " 312777359, -1140199484, 688524765, 739702138, 1480517762, 1622590976, 835969782, -204259962," +
                " -606452012, -1671898934, 368548728, -333429570, -1477682221, -638975525, -402896626, 1106834480," +
                " -1454735450, 1532680389, 1878326075, 1597781004, 619389131, -898266263, 1900039432, 1228960795," +
                " 1091764975, -1435988581, 1465994011, -241076337, 980038049, -821307198, -25801148, -1278802989," +
                " -290171171, 1063693093, 1718162965, -297113539, -1723402396, 1063795076, 1779331877, 1606303707," +
                " 1342330210, -2115595746, -718013617, 889248973, 1553964562, -2000156621, 1009070370, 998677106," +
                " 309828058, -816607592, 347096084, -565436493, -1836536982, -39909763, -1384351460, 586300570," +
                " -1545743273, -118730601, -1026888351, -643914920, 159473612, -509882909, 2003784095, -1582123439," +
                " 1199200850, -980627072, 589064158, 1351400003, 1083549876, -1039880174, 1634495699, -1583272739," +
                " 1765688283, -316629870, 577895752, -145082312, -645859550, 1496562313, 1970005163, -104842168," +
                " 285710655, 970623004, 375952155, -1114509491, 9760898, 272385973, 1160942220, 79933456, 642681904," +
                " -1291288677, -238849129, 1196057424, -587416967, -2000013062, 953214572, -2003974223, -179005208," +
                " -1599818904, 1963556499, -1494628627, 293535669, -1033907228, 1690848472, 1958730707, 1679864529," +
                " -450182832, -1398178560, 2092043951, 892850383, 662556689, -1954880564, -1297875796, -562200510," +
                " 1753810661, 612072956, -1182875, 294510681, -485063306, 1608426289, 1466734719, 2978810," +
                " -2134449847, 855495682, -1563923271, -306227772, 147934567, 926758908, 1903257258, 1602676310," +
                " -1151393146, 303067731, -1371065668, 1908028886, -425534720, 1241120323, -2101606174, 545122109," +
                " 1781213901, -146337786, -1205949803, -235261172, 1019855899, -193216104, -1286568040, -294909212," +
                " 1086948319, 1903298288, 2119132684, -581936319, -2070422261, 2086926428, -1303966999, -1365365119," +
                " -1891227288, 346044744, 488440551, -790513873, -2045294651, -1270631847, -2126290563, -1816128137," +
                " 1473769929, 784925032, 292983675, -325413283, -2117417065, 1156099828, -1188576148, -1134724577," +
                " 937972245, -924106996, 1553688888, 324720865, 2001615528, 998833644, 137816765, 1901776632," +
                " 2000206935, 942793606, -1742718537, 1909590681, -1332632806, -1355397404, 152253803, -193623640," +
                " 1601921213, -427930872, 1154642563, 1204629137, 581648332, 1921167008, 2054160403, -1709752639," +
                " -402951456, 1597748885, 351809052, -1039041413, 1958075309, 1071372680, 1249922658, -2077011328," +
                " -2088560037, 643876593, -691661336, 2124992669, -534970427, 1061266818, -1731083093, 195764083," +
                " 1773077546, 304479557, 244603812, 834384133, 1684120407, 1493413139, 1731211584, -2062213553," +
                " -270682579, 44310291, 564559440, 957643125, 1374924466, 962420298, 1319979537, 1206138289," +
                " -948832823, -909756549, -664108386, -1355112330, -125435854, -1502071736, -790593389]"
        );
        aeq(
                Q.getSeed(),
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0," +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"
        );
        aeq(
                R.getSeed(),
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27," +
                " 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51," +
                " 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75," +
                " 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99," +
                " 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118," +
                " 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137," +
                " 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156," +
                " 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175," +
                " 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194," +
                " 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213," +
                " 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232," +
                " 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251," +
                " 252, 253, 254, 255, 256]"
        );
        try {
            new RandomProvider(Collections.emptyList());
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            new RandomProvider(Arrays.asList(1, 2, 3));
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testWithScale() {
        aeq(P.withScale(100), "RandomProvider[@-8800290164235921060, 100, 8, 2]");
        aeq(Q.withScale(3), "RandomProvider[@-7948823947390831374, 3, 8, 2]");
        aeq(R.withScale(0), "RandomProvider[@2449928962525148503, 0, 8, 2]");
    }

    @Test
    public void testWithSecondaryScale() {
        aeq(P.withSecondaryScale(100), "RandomProvider[@-8800290164235921060, 32, 100, 2]");
        aeq(Q.withSecondaryScale(3), "RandomProvider[@-7948823947390831374, 32, 3, 2]");
        aeq(R.withSecondaryScale(0), "RandomProvider[@2449928962525148503, 32, 0, 2]");
    }

    @Test
    public void testWithTertiaryScale() {
        aeq(P.withTertiaryScale(100), "RandomProvider[@-8800290164235921060, 32, 8, 100]");
        aeq(Q.withTertiaryScale(3), "RandomProvider[@-7948823947390831374, 32, 8, 3]");
        aeq(R.withTertiaryScale(0), "RandomProvider[@2449928962525148503, 32, 8, 0]");
    }

    @Test
    public void testCopy() {
        RandomProvider copy = P.copy();
        assertEquals(P, copy);
        head(P.integers());
        assertEquals(P, copy);
    }

    @Test
    public void testDeepCopy() {
        RandomProvider copy = P.deepCopy();
        assertEquals(P, copy);
        head(P.integers());
        assertNotEquals(P, copy);
    }

    @Test
    public void testReset() {
        RandomProvider PDependent = P.withScale(10);
        RandomProvider original = P.deepCopy();
        RandomProvider dependent = original.withScale(10);
        assertEquals(PDependent, dependent);
        head(P.integers());
        assertNotEquals(P, original);
        assertNotEquals(PDependent, dependent);
        P.reset();
        assertEquals(P, original);
        assertEquals(PDependent, dependent);
    }

    @Test
    public void testGetId() {
        aeq(P.getId(), -8800290164235921060L);
        head(P.integers());
        aeq(P.getId(), -6220528511995005615L);
        aeq(Q.getId(), -7948823947390831374L);
        aeq(R.getId(), 2449928962525148503L);
    }

    private static <T> void simpleProviderHelper(@NotNull Iterable<T> xs, @NotNull String output) {
        List<T> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(sampleCount(sample), output);
        P.reset();
    }

    @Test
    public void testIntegers() {
        aeqitLimitLog(TINY_LIMIT, P.integers(), "RandomProvider_integers");
    }

    @Test
    public void testLongs() {
        aeqitLimitLog(TINY_LIMIT, P.longs(), "RandomProvider_longs");
    }

    @Test
    public void testBooleans() {
        simpleProviderHelper(P.booleans(), "RandomProvider_booleans");
    }

    private static void uniformSample_Iterable_helper(@NotNull String xs, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.uniformSample(readIntegerListWithNulls(xs)), output);
        P.reset();
    }

    private static void uniformSample_Iterable_fail_helper(@NotNull String xs) {
        try {
            P.uniformSample(readIntegerListWithNulls(xs));
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper("[3, 1, 4, 1]", "RandomProvider_uniformSample_Iterable_i");
        uniformSample_Iterable_helper("[3, 1, null, 1]", "RandomProvider_uniformSample_Iterable_ii");
        uniformSample_Iterable_fail_helper("[]");
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, P.uniformSample(s), output);
        P.reset();
    }

    private static void uniformSample_String_fail_helper(@NotNull String s) {
        try {
            P.uniformSample(s);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper("hello", "RandomProvider_uniformSample_String");
        uniformSample_String_fail_helper("");
    }

    @Test
    public void testOrderings() {
        simpleProviderHelper(P.orderings(), "RandomProvider_orderings");
    }

    @Test
    public void testRoundingModes() {
        simpleProviderHelper(P.roundingModes(), "RandomProvider_roundingModes");
    }

    @Test
    public void testPositiveBytes() {
        aeqitLimitLog(TINY_LIMIT, P.positiveBytes(), "RandomProvider_positiveBytes");
    }

    @Test
    public void testPositiveShorts() {
        aeqitLimitLog(TINY_LIMIT, P.positiveShorts(), "RandomProvider_positiveShorts");
    }

    @Test
    public void testPositiveIntegers() {
        aeqitLimitLog(TINY_LIMIT, P.positiveIntegers(), "RandomProvider_positiveIntegers");
    }

    @Test
    public void testPositiveLongs() {
        aeqitLimitLog(TINY_LIMIT, P.positiveLongs(), "RandomProvider_positiveLongs");
    }

    @Test
    public void testNegativeBytes() {
        aeqitLimitLog(TINY_LIMIT, P.negativeBytes(), "RandomProvider_negativeBytes");
    }

    @Test
    public void testNegativeShorts() {
        aeqitLimitLog(TINY_LIMIT, P.negativeShorts(), "RandomProvider_negativeShorts");
    }

    @Test
    public void testNegativeIntegers() {
        aeqitLimitLog(TINY_LIMIT, P.negativeIntegers(), "RandomProvider_negativeIntegers");
    }

    @Test
    public void testNegativeLongs() {
        aeqitLimitLog(TINY_LIMIT, P.negativeLongs(), "RandomProvider_negativeLongs");
    }

    @Test
    public void testNaturalBytes() {
        aeqitLimitLog(TINY_LIMIT, P.naturalBytes(), "RandomProvider_naturalBytes");
    }

    @Test
    public void testNaturalShorts() {
        aeqitLimitLog(TINY_LIMIT, P.naturalShorts(), "RandomProvider_naturalShorts");
    }

    @Test
    public void testNaturalIntegers() {
        aeqitLimitLog(TINY_LIMIT, P.naturalIntegers(), "RandomProvider_naturalIntegers");
    }

    @Test
    public void testNaturalLongs() {
        aeqitLimitLog(TINY_LIMIT, P.naturalLongs(), "RandomProvider_naturalLongs");
    }

    @Test
    public void testNonzeroBytes() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroBytes(), "RandomProvider_nonzeroBytes");
    }

    @Test
    public void testNonzeroShorts() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroShorts(), "RandomProvider_nonzeroShorts");
    }

    @Test
    public void testNonzeroIntegers() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroIntegers(), "RandomProvider_nonzeroIntegers");
    }

    @Test
    public void testNonzeroLongs() {
        aeqitLimitLog(TINY_LIMIT, P.nonzeroLongs(), "RandomProvider_nonzeroLongs");
    }

    @Test
    public void testBytes() {
        aeqitLimitLog(TINY_LIMIT, P.bytes(), "RandomProvider_bytes");
    }

    @Test
    public void testShorts() {
        aeqitLimitLog(TINY_LIMIT, P.shorts(), "RandomProvider_shorts");
    }

    @Test
    public void testAsciiCharacters() {
        aeqitLimitLog(SMALL_LIMIT, P.asciiCharacters(), "RandomProvider_asciiCharacters");
    }

    @Test
    public void testCharacters() {
        aeqitLimitLog(SMALL_LIMIT, P.characters(), "RandomProvider_characters");
    }

    private static void rangeUp_byte_helper(byte a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_byte() {
        rangeUp_byte_helper((byte) 0, "RandomProvider_rangeUp_byte_i");
        rangeUp_byte_helper((byte) (1 << 6), "RandomProvider_rangeUp_byte_ii");
        rangeUp_byte_helper((byte) (-1 << 6), "RandomProvider_rangeUp_byte_iii");
        rangeUp_byte_helper(Byte.MAX_VALUE, "RandomProvider_rangeUp_byte_iv");
        rangeUp_byte_helper(Byte.MIN_VALUE, "RandomProvider_rangeUp_byte_v");
    }

    private static void rangeUp_short_helper(short a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper((short) 0, "RandomProvider_rangeUp_short_i");
        rangeUp_short_helper((short) (1 << 14), "RandomProvider_rangeUp_short_ii");
        rangeUp_short_helper((short) (-1 << 14), "RandomProvider_rangeUp_short_iii");
        rangeUp_short_helper(Short.MAX_VALUE, "RandomProvider_rangeUp_short_iv");
        rangeUp_short_helper(Short.MIN_VALUE, "RandomProvider_rangeUp_short_v");
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(0, "RandomProvider_rangeUp_int_i");
        rangeUp_int_helper(1 << 30, "RandomProvider_rangeUp_int_ii");
        rangeUp_int_helper(-1 << 30, "RandomProvider_rangeUp_int_iii");
        rangeUp_int_helper(Integer.MAX_VALUE, "RandomProvider_rangeUp_int_iv");
        rangeUp_int_helper(Integer.MIN_VALUE, "RandomProvider_rangeUp_int_v");
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(0L, "RandomProvider_rangeUp_long_i");
        rangeUp_long_helper(1L << 62, "RandomProvider_rangeUp_long_ii");
        rangeUp_long_helper(-1L << 62, "RandomProvider_rangeUp_long_iii");
        rangeUp_long_helper(Long.MAX_VALUE, "RandomProvider_rangeUp_long_iv");
        rangeUp_long_helper(Long.MIN_VALUE, "RandomProvider_rangeUp_long_v");
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_char() {
        rangeUp_char_helper('\0', "RandomProvider_rangeUp_char_i");
        rangeUp_char_helper('a', "RandomProvider_rangeUp_char_ii");
        rangeUp_char_helper('Ш', "RandomProvider_rangeUp_char_iii");
        rangeUp_char_helper('\uffff', "RandomProvider_rangeUp_char_iv");
    }

    private static void rangeDown_byte_helper(byte a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper((byte) 0, "RandomProvider_rangeDown_byte_i");
        rangeDown_byte_helper((byte) (1 << 6), "RandomProvider_rangeDown_byte_ii");
        rangeDown_byte_helper((byte) (-1 << 6), "RandomProvider_rangeDown_byte_iii");
        rangeDown_byte_helper(Byte.MAX_VALUE, "RandomProvider_rangeDown_byte_iv");
        rangeDown_byte_helper(Byte.MIN_VALUE, "RandomProvider_rangeDown_byte_v");
    }

    private static void rangeDown_short_helper(short a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper((short) 0, "RandomProvider_rangeDown_short_i");
        rangeDown_short_helper((short) (1 << 14), "RandomProvider_rangeDown_short_ii");
        rangeDown_short_helper((short) (-1 << 14), "RandomProvider_rangeDown_short_iii");
        rangeDown_short_helper(Short.MAX_VALUE, "RandomProvider_rangeDown_short_iv");
        rangeDown_short_helper(Short.MIN_VALUE, "RandomProvider_rangeDown_short_v");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(0, "RandomProvider_rangeDown_int_i");
        rangeDown_int_helper(1 << 30, "RandomProvider_rangeDown_int_ii");
        rangeDown_int_helper(-1 << 30, "RandomProvider_rangeDown_int_iii");
        rangeDown_int_helper(Integer.MAX_VALUE, "RandomProvider_rangeDown_int_iv");
        rangeDown_int_helper(Integer.MIN_VALUE, "RandomProvider_rangeDown_int_v");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(0L, "RandomProvider_rangeDown_long_i");
        rangeDown_long_helper(1L << 62, "RandomProvider_rangeDown_long_ii");
        rangeDown_long_helper(-1L << 62, "RandomProvider_rangeDown_long_iii");
        rangeDown_long_helper(Long.MAX_VALUE, "RandomProvider_rangeDown_long_iv");
        rangeDown_long_helper(Long.MIN_VALUE, "RandomProvider_rangeDown_long_v");
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_char() {
        rangeDown_char_helper('\0', "RandomProvider_rangeDown_char_i");
        rangeDown_char_helper('a', "RandomProvider_rangeDown_char_ii");
        rangeDown_char_helper('Ш', "RandomProvider_rangeDown_char_iii");
        rangeDown_char_helper('\uffff', "RandomProvider_rangeDown_char_iv");
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.range((byte) a, (byte) b), output);
        P.reset();
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(10, 20, "RandomProvider_range_byte_byte_i");
        range_byte_byte_helper(10, 10, "RandomProvider_range_byte_byte_ii");
        range_byte_byte_helper((byte) 10, (byte) 9, "RandomProvider_range_byte_byte_iii");
        range_byte_byte_helper(-20, -10, "RandomProvider_range_byte_byte_iv");
        range_byte_byte_helper(-20, -20, "RandomProvider_range_byte_byte_v");
        range_byte_byte_helper((byte) -20, (byte) -21, "RandomProvider_range_byte_byte_vi");
        range_byte_byte_helper(0, 0, "RandomProvider_range_byte_byte_vii");
        range_byte_byte_helper(0, 10, "RandomProvider_range_byte_byte_viii");
        range_byte_byte_helper(-5, 0, "RandomProvider_range_byte_byte_ix");
        range_byte_byte_helper(-5, 10, "RandomProvider_range_byte_byte_x");
        range_byte_byte_helper(-10, 5, "RandomProvider_range_byte_byte_xi");
        range_byte_byte_helper(5, -10, "RandomProvider_range_byte_byte_xii");
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.range((short) a, (short) b), output);
        P.reset();
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(10, 20, "RandomProvider_range_short_short_i");
        range_short_short_helper(10, 10, "RandomProvider_range_short_short_ii");
        range_short_short_helper((short) 10, (short) 9, "RandomProvider_range_short_short_iii");
        range_short_short_helper(-20, -10, "RandomProvider_range_short_short_iv");
        range_short_short_helper(-20, -20, "RandomProvider_range_short_short_v");
        range_short_short_helper((short) -20, (short) -21, "RandomProvider_range_short_short_vi");
        range_short_short_helper(0, 0, "RandomProvider_range_short_short_vii");
        range_short_short_helper(0, 10, "RandomProvider_range_short_short_viii");
        range_short_short_helper(-5, 0, "RandomProvider_range_short_short_ix");
        range_short_short_helper(-5, 10, "RandomProvider_range_short_short_x");
        range_short_short_helper(-10, 5, "RandomProvider_range_short_short_xi");
        range_short_short_helper(5, -10, "RandomProvider_range_short_short_xii");
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.range(a, b), output);
        P.reset();
    }

    @Test
    public void testRange_int_int() {
        range_int_int_helper(10, 20, "RandomProvider_range_int_int_i");
        range_int_int_helper(10, 10, "RandomProvider_range_int_int_ii");
        range_int_int_helper(10, 9, "RandomProvider_range_int_int_iii");
        range_int_int_helper(-20, -10, "RandomProvider_range_int_int_iv");
        range_int_int_helper(-20, -20, "RandomProvider_range_int_int_v");
        range_int_int_helper(-20, -21, "RandomProvider_range_int_int_vi");
        range_int_int_helper(0, 0, "RandomProvider_range_int_int_vii");
        range_int_int_helper(0, 10, "RandomProvider_range_int_int_viii");
        range_int_int_helper(-5, 0, "RandomProvider_range_int_int_ix");
        range_int_int_helper(-5, 10, "RandomProvider_range_int_int_x");
        range_int_int_helper(-10, 5, "RandomProvider_range_int_int_xi");
        range_int_int_helper(5, -10, "RandomProvider_range_int_int_xii");
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        P.reset();
        aeqitLimitLog(TINY_LIMIT, P.range(a, b), output);
    }

    @Test
    public void testRange_long_long() {
        range_long_long_helper(10L, 20L, "RandomProvider_range_long_long_i");
        range_long_long_helper(10L, 10L, "RandomProvider_range_long_long_ii");
        range_long_long_helper(10L, 9L, "RandomProvider_range_long_long_iii");
        range_long_long_helper(-20L, -10L, "RandomProvider_range_long_long_iv");
        range_long_long_helper(-20L, -20L, "RandomProvider_range_long_long_v");
        range_long_long_helper(-20L, -21L, "RandomProvider_range_long_long_vi");
        range_long_long_helper(0L, 0L, "RandomProvider_range_long_long_vii");
        range_long_long_helper(0L, 10L, "RandomProvider_range_long_long_viii");
        range_long_long_helper(-5L, 0L, "RandomProvider_range_long_long_ix");
        range_long_long_helper(-5L, 10L, "RandomProvider_range_long_long_x");
        range_long_long_helper(-10L, 5L, "RandomProvider_range_long_long_xii");
        range_long_long_helper(5L, -10L, "RandomProvider_range_long_long_xiii");
    }

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
        P.reset();
    }

    @Test
    public void testRange_BigInteger_BigInteger() {
        range_BigInteger_BigInteger_helper(10, 20, "RandomProvider_range_BigInteger_BigInteger_i");
        range_BigInteger_BigInteger_helper(10, 10, "RandomProvider_range_BigInteger_BigInteger_ii");
        range_BigInteger_BigInteger_helper(10, 9, "RandomProvider_range_BigInteger_BigInteger_iii");
        range_BigInteger_BigInteger_helper(-20, -10, "RandomProvider_range_BigInteger_BigInteger_iv");
        range_BigInteger_BigInteger_helper(-20, -20, "RandomProvider_range_BigInteger_BigInteger_v");
        range_BigInteger_BigInteger_helper(-20, -21, "RandomProvider_range_BigInteger_BigInteger_vi");
        range_BigInteger_BigInteger_helper(0, 0, "RandomProvider_range_BigInteger_BigInteger_vii");
        range_BigInteger_BigInteger_helper(0, 10, "RandomProvider_range_BigInteger_BigInteger_viii");
        range_BigInteger_BigInteger_helper(-5, 0, "RandomProvider_range_BigInteger_BigInteger_ix");
        range_BigInteger_BigInteger_helper(-5, 10, "RandomProvider_range_BigInteger_BigInteger_x");
        range_BigInteger_BigInteger_helper(-10, 5, "RandomProvider_range_BigInteger_BigInteger_xi");
        range_BigInteger_BigInteger_helper(5, -10, "RandomProvider_range_BigInteger_BigInteger_xii");
    }

    private static void range_char_char_helper(char a, char b, @NotNull String output) {
        aeqitLimitLog(SMALL_LIMIT, P.range(a, b), output);
        P.reset();
    }

    @Test
    public void testRange_char_char() {
        range_char_char_helper('a', 'z', "RandomProvider_range_char_char_i");
        range_char_char_helper('a', 'a', "RandomProvider_range_char_char_ii");
        range_char_char_helper('!', '9', "RandomProvider_range_char_char_iii");
        range_char_char_helper('a', 'A', "RandomProvider_range_char_char_iv");
    }

    private static void geometricHelper(
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            double sampleMean
    ) {
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(sample), sampleMean);
    }

    private static void positiveIntegersGeometric_helper(int mean, @NotNull String output, double sampleMean) {
        geometricHelper(P.withScale(mean).positiveIntegersGeometric(), output, sampleMean);
        P.reset();
    }

    private static void positiveIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).positiveIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPositiveIntegersGeometric() {
        positiveIntegersGeometric_helper(2, "RandomProvider_positiveIntegersGeometric_i", 2.0008359999800347);
        positiveIntegersGeometric_helper(3, "RandomProvider_positiveIntegersGeometric_ii", 3.002096999989928);
        positiveIntegersGeometric_helper(4, "RandomProvider_positiveIntegersGeometric_iii", 4.0033679999901475);
        positiveIntegersGeometric_helper(5, "RandomProvider_positiveIntegersGeometric_iv", 5.004360000008482);
        positiveIntegersGeometric_helper(10, "RandomProvider_positiveIntegersGeometric_v", 9.996188000005418);
        positiveIntegersGeometric_helper(100, "RandomProvider_positiveIntegersGeometric_vi", 99.9640719999968);
        positiveIntegersGeometric_fail_helper(1);
        positiveIntegersGeometric_fail_helper(0);
        positiveIntegersGeometric_fail_helper(-1);
    }

    private static void negativeIntegersGeometric_helper(int mean, @NotNull String output, double sampleMean) {
        geometricHelper(P.withScale(mean).negativeIntegersGeometric(), output, sampleMean);
        P.reset();
    }

    private static void negativeIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).negativeIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNegativeIntegersGeometric() {
        negativeIntegersGeometric_helper(2, "RandomProvider_negativeIntegersGeometric_i", -2.0008359999800347);
        negativeIntegersGeometric_helper(3, "RandomProvider_negativeIntegersGeometric_ii", -3.002096999989928);
        negativeIntegersGeometric_helper(4, "RandomProvider_negativeIntegersGeometric_iii", -4.0033679999901475);
        negativeIntegersGeometric_helper(5, "RandomProvider_negativeIntegersGeometric_iv", -5.004360000008482);
        negativeIntegersGeometric_helper(10, "RandomProvider_negativeIntegersGeometric_v", -9.996188000005418);
        negativeIntegersGeometric_helper(100, "RandomProvider_negativeIntegersGeometric_vi", -99.9640719999968);
        negativeIntegersGeometric_fail_helper(1);
        negativeIntegersGeometric_fail_helper(0);
        negativeIntegersGeometric_fail_helper(-1);
    }

    private static void naturalIntegersGeometric_helper(int mean, @NotNull String output, double sampleMean) {
        geometricHelper(P.withScale(mean).naturalIntegersGeometric(), output, sampleMean);
        P.reset();
    }

    private static void naturalIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).naturalIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNaturalIntegersGeometric() {
        naturalIntegersGeometric_helper(1, "RandomProvider_naturalIntegersGeometric_i", 1.0008359999977228);
        naturalIntegersGeometric_helper(2, "RandomProvider_naturalIntegersGeometric_ii", 2.0020969999891216);
        naturalIntegersGeometric_helper(3, "RandomProvider_naturalIntegersGeometric_iii", 3.003367999991497);
        naturalIntegersGeometric_helper(4, "RandomProvider_naturalIntegersGeometric_iv", 4.004359999991779);
        naturalIntegersGeometric_helper(5, "RandomProvider_naturalIntegersGeometric_v", 5.005650000005037);
        naturalIntegersGeometric_helper(10, "RandomProvider_naturalIntegersGeometric_vi", 9.996028000004106);
        naturalIntegersGeometric_helper(100, "RandomProvider_naturalIntegersGeometric_vii", 99.96387799999758);
        naturalIntegersGeometric_fail_helper(0);
        naturalIntegersGeometric_fail_helper(-1);
        naturalIntegersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void nonzeroIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            double sampleMean,
            double sampleAbsMean
    ) {
        List<Integer> xs = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(mean).nonzeroIntegersGeometric()));
        geometricHelper(xs, output, sampleMean);
        aeq(meanOfIntegers(toList(map(Math::abs, xs))), sampleAbsMean);
        P.reset();
    }

    private static void nonzeroIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nonzeroIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNonzeroIntegersGeometric() {
        nonzeroIntegersGeometric_helper(
                2,
                "RandomProvider_nonzeroIntegersGeometric_i",
                0.0034480000000000253,
                1.9994539999798795
        );
        nonzeroIntegersGeometric_helper(
                3,
                "RandomProvider_nonzeroIntegersGeometric_ii",
                -0.0012719999999999216,
                3.00329999998982
        );
        nonzeroIntegersGeometric_helper(
                4,
                "RandomProvider_nonzeroIntegersGeometric_iii",
                -8.369999999999692E-4,
                4.00555699999019
        );
        nonzeroIntegersGeometric_helper(
                5,
                "RandomProvider_nonzeroIntegersGeometric_iv",
                0.004184999999999686,
                5.006127000008418
        );
        nonzeroIntegersGeometric_helper(
                10,
                "RandomProvider_nonzeroIntegersGeometric_v",
                0.011239999999999537,
                9.999258000005128
        );
        nonzeroIntegersGeometric_helper(
                100,
                "RandomProvider_nonzeroIntegersGeometric_vi",
                -0.14496700000000257,
                99.97359300000046
        );
        nonzeroIntegersGeometric_fail_helper(1);
        nonzeroIntegersGeometric_fail_helper(0);
        nonzeroIntegersGeometric_fail_helper(-1);
    }

    private static void integersGeometric_helper(
            int mean,
            @NotNull String output,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = take(DEFAULT_SAMPLE_SIZE, P.withScale(mean).integersGeometric());
        geometricHelper(xs, output, sampleMean);
        aeq(meanOfIntegers(toList(map(Math::abs, xs))), sampleAbsMean);
        P.reset();
    }

    private static void integersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).integersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testIntegersGeometric() {
        integersGeometric_helper(1, "RandomProvider_integersGeometric_i", 0.0025060000000000906, 1.0003039999976984);
        integersGeometric_helper(2, "RandomProvider_integersGeometric_ii", -0.0018900000000000037, 2.002686999989137);
        integersGeometric_helper(3, "RandomProvider_integersGeometric_iii", -0.0014549999999999997, 3.00388599999155);
        integersGeometric_helper(4, "RandomProvider_integersGeometric_iv", 0.0036169999999999917, 3.9981839999915647);
        integersGeometric_helper(5, "RandomProvider_integersGeometric_v", 0.003408000000000304, 4.9993930000049955);
        integersGeometric_helper(10, "RandomProvider_integersGeometric_vi", 0.010864000000000018, 9.983633000004563);
        integersGeometric_helper(100, "RandomProvider_integersGeometric_vii", -0.1466660000000059, 99.86606600000295);
        integersGeometric_fail_helper(0);
        integersGeometric_fail_helper(-1);
        integersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void rangeUpGeometric_helper(int mean, int a, @NotNull String output, double sampleMean) {
        geometricHelper(P.withScale(mean).rangeUpGeometric(a), output, sampleMean);
        P.reset();
    }

    private static void rangeUpGeometric_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).rangeUpGeometric(a);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeUpGeometric() {
        rangeUpGeometric_helper(3, 2, "RandomProvider_rangeUpGeometric_i", 3.000835999968773);
        rangeUpGeometric_helper(10, 2, "RandomProvider_rangeUpGeometric_ii", 9.996049000016834);
        rangeUpGeometric_helper(11, 10, "RandomProvider_rangeUpGeometric_iii", 11.000835999897873);
        rangeUpGeometric_helper(20, 10, "RandomProvider_rangeUpGeometric_iv", 19.99602800000434);
        rangeUpGeometric_helper(-9, -10, "RandomProvider_rangeUpGeometric_v", -8.999163999953181);
        rangeUpGeometric_helper(0, -10, "RandomProvider_rangeUpGeometric_vi", -0.003971999999995211);
        rangeUpGeometric_helper(
                Integer.MAX_VALUE,
                Integer.MAX_VALUE - 1,
                "RandomProvider_rangeUpGeometric_vii",
                2.1474836463261979E9
        );
        rangeUpGeometric_helper(
                Integer.MIN_VALUE + 1,
                Integer.MIN_VALUE,
                "RandomProvider_rangeUpGeometric_viii",
                -2.1474836470149984E9
        );
        rangeUpGeometric_fail_helper(5, 5);
        rangeUpGeometric_fail_helper(4, 5);
        rangeUpGeometric_fail_helper(Integer.MAX_VALUE - 5, -10);
    }

    private static void rangeDownGeometric_helper(int mean, int a, @NotNull String output, double sampleMean) {
        geometricHelper(P.withScale(mean).rangeDownGeometric(a), output, sampleMean);
        P.reset();
    }

    private static void rangeDownGeometric_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).rangeDownGeometric(a);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeDownGeometric() {
        rangeDownGeometric_helper(0, 2, "RandomProvider_rangeDownGeometric_i", -0.0020969999999987214);
        rangeDownGeometric_helper(-5, 2, "RandomProvider_rangeDownGeometric_ii", -5.0074679999951055);
        rangeDownGeometric_helper(5, 10, "RandomProvider_rangeDownGeometric_iii", 4.994349999991597);
        rangeDownGeometric_helper(0, 10, "RandomProvider_rangeDownGeometric_iv", 0.003971999999995211);
        rangeDownGeometric_helper(-11, -10, "RandomProvider_rangeDownGeometric_v", -11.000835999897873);
        rangeDownGeometric_helper(-20, -10, "RandomProvider_rangeDownGeometric_vi", -19.99602800000434);
        rangeDownGeometric_helper(
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE,
                "RandomProvider_rangeDownGeometric_vii",
                2.147483646013541E9
        );
        rangeDownGeometric_helper(
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                "RandomProvider_rangeDownGeometric_viii",
                -2.147483647372161E9
        );
        rangeDownGeometric_fail_helper(5, 5);
        rangeDownGeometric_fail_helper(6, 5);
        rangeDownGeometric_fail_helper(-5, Integer.MIN_VALUE);
    }

    private static void bigIntegerHelper(
            @NotNull Iterable<BigInteger> xs,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        List<BigInteger> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfBigIntegers(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.abs().bitLength(), sample))), bitSizeMean);
    }

    private static void positiveBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(P.withScale(meanBitSize).positiveBigIntegers(), output, sampleMean, bitSizeMean);
        P.reset();
    }

    private static void positiveBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).positiveBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPositiveBigIntegers() {
        positiveBigIntegers_helper(2, "RandomProvider_positiveBigIntegers_i", 114.05128999981362, 1.9994539999798795);
        positiveBigIntegers_helper(3, "RandomProvider_positiveBigIntegers_ii", 42053.996647257176, 3.00330199998982);
        positiveBigIntegers_helper(
                4,
                "RandomProvider_positiveBigIntegers_iii",
                3.923720245917525E8,
                4.005570999990192
        );
        positiveBigIntegers_helper(5, "RandomProvider_positiveBigIntegers_iv", 8.95688013930559E12, 5.006042000008429);
        positiveBigIntegers_helper(
                10,
                "RandomProvider_positiveBigIntegers_v",
                4.456452305288997E35,
                9.998937000005219
        );
        positiveBigIntegers_helper(
                100,
                "RandomProvider_positiveBigIntegers_vi",
                Double.POSITIVE_INFINITY,
                99.9771549999987
        );
        positiveBigIntegers_fail_helper(1);
        positiveBigIntegers_fail_helper(0);
        positiveBigIntegers_fail_helper(-1);
    }

    private static void negativeBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).negativeBigIntegers(),
                output,
                sampleMean,
                bitSizeMean
        );
        P.reset();
    }

    private static void negativeBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).negativeBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNegativeBigIntegers() {
        negativeBigIntegers_helper(
                2,
                "RandomProvider_negativeBigIntegers_i",
                -114.05128999981362,
                1.9994539999798795
        );
        negativeBigIntegers_helper(
                3,
                "RandomProvider_negativeBigIntegers_ii",
                -42053.996647257176,
                3.00330199998982
        );
        negativeBigIntegers_helper(
                4,
                "RandomProvider_negativeBigIntegers_iii",
                -3.923720245917525E8,
                4.005570999990192
        );
        negativeBigIntegers_helper(
                5,
                "RandomProvider_negativeBigIntegers_iv",
                -8.95688013930559E12,
                5.006042000008429
        );
        negativeBigIntegers_helper(
                10,
                "RandomProvider_negativeBigIntegers_v",
                -4.456452305288997E35,
                9.998937000005219
        );
        negativeBigIntegers_helper(
                100,
                "RandomProvider_negativeBigIntegers_vi",
                Double.NEGATIVE_INFINITY,
                99.9771549999987
        );
        negativeBigIntegers_fail_helper(1);
        negativeBigIntegers_fail_helper(0);
        negativeBigIntegers_fail_helper(-1);
    }

    private static void naturalBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(P.withScale(meanBitSize).naturalBigIntegers(), output, sampleMean, bitSizeMean);
        P.reset();
    }

    private static void naturalBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).naturalBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNaturalBigIntegers() {
        naturalBigIntegers_helper(1, "RandomProvider_naturalBigIntegers_i", 7.527205000038622, 1.00042899999766);
        naturalBigIntegers_helper(2, "RandomProvider_naturalBigIntegers_ii", 18553.42636205894, 2.0026819999891274);
        naturalBigIntegers_helper(
                3,
                "RandomProvider_naturalBigIntegers_iii",
                2.0721683567389777E8,
                3.0050639999915902
        );
        naturalBigIntegers_helper(4, "RandomProvider_naturalBigIntegers_iv", 6.063391150606273E12, 4.006382999991641);
        naturalBigIntegers_helper(5, "RandomProvider_naturalBigIntegers_v", 7.6975225805449024E16, 5.008064000005);
        naturalBigIntegers_helper(10, "RandomProvider_naturalBigIntegers_vi", 6.216950515561165E39, 9.998042000004142);
        naturalBigIntegers_helper(
                100,
                "RandomProvider_naturalBigIntegers_100",
                Double.POSITIVE_INFINITY,
                99.9762349999981
        );
        naturalBigIntegers_fail_helper(0);
        naturalBigIntegers_fail_helper(-1);
        naturalBigIntegers_fail_helper(Integer.MAX_VALUE);
    }

    private static void nonzeroBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(P.withScale(meanBitSize).nonzeroBigIntegers(), output, sampleMean, bitSizeMean);
        P.reset();
    }

    private static void nonzeroBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).nonzeroBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNonzeroBigIntegers() {
        nonzeroBigIntegers_helper(2, "RandomProvider_nonzeroBigIntegers_i", -51.040697000000215, 1.9999089999798014);
        nonzeroBigIntegers_helper(3, "RandomProvider_nonzeroBigIntegers_ii", -12560.956458999866, 3.0034329999897373);
        nonzeroBigIntegers_helper(4, "RandomProvider_nonzeroBigIntegers_iii", 6.9148732007788E7, 4.007541999990144);
        nonzeroBigIntegers_helper(5, "RandomProvider_nonzeroBigIntegers_iv", 5.064110348766358E12, 5.006230000008758);
        nonzeroBigIntegers_helper(
                10,
                "RandomProvider_nonzeroBigIntegers_v",
                -2.4957396868152156E35,
                9.995175000005379
        );
        nonzeroBigIntegers_helper(100, "RandomProvider_nonzeroBigIntegers_vi", Double.NaN, 99.9676500000014);
        nonzeroBigIntegers_fail_helper(1);
        nonzeroBigIntegers_fail_helper(0);
        nonzeroBigIntegers_fail_helper(-1);
    }

    private static void bigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).bigIntegers(),
                output,
                sampleMean,
                bitSizeMean
        );
        P.reset();
    }

    private static void bigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).bigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBigIntegers() {
        bigIntegers_helper(1, "RandomProvider_bigIntegers_i", -21.962919999999734, 1.0003699999976585);
        bigIntegers_helper(2, "RandomProvider_bigIntegers_ii", 652.206306000023, 2.004140999989157);
        bigIntegers_helper(3, "RandomProvider_bigIntegers_iii", 3.8147781719932765E7, 3.008166999991507);
        bigIntegers_helper(4, "RandomProvider_bigIntegers_iv", 3.0232412271737646E12, 4.00732099999149);
        bigIntegers_helper(5, "RandomProvider_bigIntegers_v", 5.6936067849711536E16, 5.009217000005041);
        bigIntegers_helper(10, "RandomProvider_bigIntegers_vi", -3.800274840387793E39, 9.993747000004165);
        bigIntegers_helper(100, "RandomProvider_bigIntegers_vii", Double.NaN, 99.96628299999787);
        bigIntegers_fail_helper(0);
        bigIntegers_fail_helper(-1);
        bigIntegers_fail_helper(Integer.MAX_VALUE);
    }

    private static void rangeUp_BigInteger_helper(
            int meanBitSize,
            int a,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(P.withScale(meanBitSize).rangeUp(BigInteger.valueOf(a)), output, sampleMean, bitSizeMean);
        P.reset();
    }

    private static void rangeUp_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).rangeUp(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeUp_BigInteger() {
        rangeUp_BigInteger_helper(5, 8, "RandomProvider_rangeUp_BigInteger_i", 900.8645519990359, 4.999453999951416);
        rangeUp_BigInteger_helper(
                10,
                8,
                "RandomProvider_rangeUp_BigInteger_ii",
                7.070709518052013E21,
                10.008093999993136
        );
        rangeUp_BigInteger_helper(
                5,
                10,
                "RandomProvider_rangeUp_BigInteger_iii",
                894.1639779982157,
                4.999280999951436
        );
        rangeUp_BigInteger_helper(
                10,
                10,
                "RandomProvider_rangeUp_BigInteger_iv",
                7.070705170732308E21,
                10.006835999993193
        );
        rangeUp_BigInteger_helper(
                1,
                0,
                "RandomProvider_rangeUp_BigInteger_v",
                7.527205000038622,
                1.00042899999766
        );
        rangeUp_BigInteger_helper(
                10,
                0,
                "RandomProvider_rangeUp_BigInteger_vi",
                6.216950515561165E39,
                9.998042000004142
        );
        rangeUp_BigInteger_helper(
                5,
                -8,
                "RandomProvider_rangeUp_BigInteger_vii",
                7.6976860134970016E16,
                5.007767000005044
        );
        rangeUp_BigInteger_helper(
                10,
                -8,
                "RandomProvider_rangeUp_BigInteger_viii",
                6.216950511573439E39,
                9.99805800000423
        );
        rangeUp_BigInteger_helper(
                5,
                -10,
                "RandomProvider_rangeUp_BigInteger_ix",
                7.6975187379143424E16,
                5.007546000005042
        );
        rangeUp_BigInteger_helper(
                10,
                -10,
                "RandomProvider_rangeUp_BigInteger_x",
                6.216950512238074E39,
                9.998103000004235
        );
        rangeUp_BigInteger_fail_helper(4, 10);
        rangeUp_BigInteger_fail_helper(3, 10);
        rangeUp_BigInteger_fail_helper(Integer.MAX_VALUE, -10);
    }

    private static void rangeDown_BigInteger_helper(
            int meanBitSize,
            int a,
            @NotNull String output,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).rangeDown(BigInteger.valueOf(a)),
                output,
                sampleMean,
                bitSizeMean
        );
        P.reset();
    }

    private static void rangeDown_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).rangeDown(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testRangeDown_BigInteger() {
        rangeDown_BigInteger_helper(
                5,
                8,
                "RandomProvider_rangeDown_BigInteger_i",
                -7.6976860134970016E16,
                5.007767000005044
        );
        rangeDown_BigInteger_helper(
                10,
                8,
                "RandomProvider_rangeDown_BigInteger_ii",
                -6.216950511573439E39,
                9.99805800000423
        );
        rangeDown_BigInteger_helper(
                5,
                10,
                "RandomProvider_rangeDown_BigInteger_iii",
                -7.6975187379143424E16,
                5.007546000005042
        );
        rangeDown_BigInteger_helper(
                10,
                10,
                "RandomProvider_rangeDown_BigInteger_iv",
                -6.216950512238074E39,
                9.998103000004235
        );
        rangeDown_BigInteger_helper(
                1,
                0,
                "RandomProvider_rangeDown_BigInteger_v",
                -7.527205000038622,
                1.00042899999766
        );
        rangeDown_BigInteger_helper(
                10,
                0,
                "RandomProvider_rangeDown_BigInteger_vi",
                -6.216950515561165E39,
                9.998042000004142
        );
        rangeDown_BigInteger_helper(
                5,
                -8,
                "RandomProvider_rangeDown_BigInteger_vii",
                -900.8645519990359,
                4.999453999951416
        );
        rangeDown_BigInteger_helper(
                10,
                -8,
                "RandomProvider_rangeDown_BigInteger_viii",
                -7.070709518052013E21,
                10.008093999993136
        );
        rangeDown_BigInteger_helper(
                5,
                -10,
                "RandomProvider_rangeDown_BigInteger_ix",
                -894.1639779982157,
                4.999280999951436
        );
        rangeDown_BigInteger_helper(
                10,
                -10,
                "RandomProvider_rangeDown_BigInteger_x",
                -7.070705170732308E21,
                10.006835999993193
        );
        rangeDown_BigInteger_fail_helper(4, -10);
        rangeDown_BigInteger_fail_helper(3, -10);
        rangeDown_BigInteger_fail_helper(Integer.MAX_VALUE, 10);
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

    private static void withElement_helper(
            int scale,
            @NotNull String input,
            @Nullable Integer element,
            @NotNull String output,
            double elementFrequency
    ) {
        Iterable<Integer> xs = P.withScale(scale).withElement(element, cycle(readIntegerListWithNulls(input)));
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfIntegers(toList(map(x -> Objects.equals(x, element) ? 1 : 0, sample))), elementFrequency);
        P.reset();
    }

    @Test
    public void testWithElement() {
        withElement_helper(2, "[1]", null, "RandomProvider_withElement_i", 0.4992549999935604);
        withElement_helper(8, "[1]", null, "RandomProvider_withElement_ii", 0.12480700000010415);
        withElement_helper(32, "[1]", null, "RandomProvider_withElement_iii", 0.031218000000010567);
        withElement_helper(2, "[null, 2, 3]", 10, "RandomProvider_withElement_iv", 0.4992549999935604);
        withElement_helper(8, "[null, 2, 3]", 10, "RandomProvider_withElement_v", 0.12480700000010415);
        withElement_helper(32, "[null, 2, 3]", 10, "RandomProvider_withElement_vi", 0.031218000000010567);

        try {
            toList(P.withElement(null, readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).withElement(null, cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void withNull_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double nullFrequency
    ) {
        Iterable<Integer> xs = P.withScale(scale).withNull(cycle(readIntegerListWithNulls(input)));
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfIntegers(toList(map(x -> x == null ? 1 : 0, sample))), nullFrequency);
        P.reset();
    }

    @Test
    public void testWithNull() {
        withNull_helper(2, "[1]", "RandomProvider_withNull_i", 0.4992549999935604);
        withNull_helper(8, "[1]", "RandomProvider_withNull_ii", 0.12480700000010415);
        withNull_helper(32, "[1]", "RandomProvider_withNull_iii", 0.031218000000010567);
        withNull_helper(2, "[1, 2, 3]", "RandomProvider_withNull_iv", 0.4992549999935604);
        withNull_helper(8, "[1, 2, 3]", "RandomProvider_withNull_v", 0.12480700000010415);
        withNull_helper(32, "[1, 2, 3]", "RandomProvider_withNull_vi", 0.031218000000010567);

        try {
            toList(P.withNull(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).withNull(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void optionalsHelper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double emptyFrequency
    ) {
        Iterable<Optional<Integer>> xs = P.withScale(scale).optionals(cycle(readIntegerListWithNulls(input)));
        List<Optional<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testOptionals() {
        optionalsHelper(2, "[1]", "RandomProvider_optionals_i", 0.4992549999935604);
        optionalsHelper(8, "[1]", "RandomProvider_optionals_ii", 0.12480700000010415);
        optionalsHelper(32, "[1]", "RandomProvider_optionals_iii", 0.031218000000010567);
        optionalsHelper(2, "[1, 2, 3]", "RandomProvider_optionals_iv", 0.4992549999935604);
        optionalsHelper(8, "[1, 2, 3]", "RandomProvider_optionals_v", 0.12480700000010415);
        optionalsHelper(32, "[1, 2, 3]", "RandomProvider_optionals_vi", 0.031218000000010567);

        try {
            toList(P.optionals(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).optionals(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    private static void nullableOptionals_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double emptyFrequency
    ) {
        Iterable<NullableOptional<Integer>> xs = P.withScale(scale)
                .nullableOptionals(cycle(readIntegerListWithNulls(input)));
        List<NullableOptional<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testNullableOptionals() {
        nullableOptionals_helper(2, "[1]", "RandomProvider_nullableOptionals_i", 0.4992549999935604);
        nullableOptionals_helper(8, "[1]", "RandomProvider_nullableOptionals_ii", 0.12480700000010415);
        nullableOptionals_helper(32, "[1]", "RandomProvider_nullableOptionals_iii", 0.031218000000010567);
        nullableOptionals_helper(2, "[null, 2, 3]", "RandomProvider_nullableOptionals_iv", 0.4992549999935604);
        nullableOptionals_helper(8, "[null, 2, 3]", "RandomProvider_nullableOptionals_v", 0.12480700000010415);
        nullableOptionals_helper(32, "[null, 2, 3]", "RandomProvider_nullableOptionals_vi", 0.031218000000010567);

        try {
            toList(P.nullableOptionals(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.withScale(1).nullableOptionals(cycle(readIntegerListWithNulls("[1, 2, 3]")));
            fail();
        } catch (IllegalStateException ignored) {}
    }

    @Test
    public void dependentPairsInfiniteTest() {
        aeqitLimitLog(
                TINY_LIMIT,
                P.dependentPairsInfinite(P.range(1, 5), i -> P.strings(i, charsToString(range('a', 'z')))),
                "RandomProvider_dependentPairsInfinite"
        );
        P.reset();

        try {
            toList(P.dependentPairsInfinite(P.range(1, 5), i -> null));
            fail();
        } catch (NullPointerException ignored) {}

        try {
            toList(
                    P.dependentPairsInfinite(
                            ExhaustiveProvider.INSTANCE.range(1, 5),
                            i -> P.strings(i, charsToString(range('a', 'z')))
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}

        try {
            toList(
                    P.dependentPairsInfinite(
                            P.range(1, 5),
                            i -> ExhaustiveProvider.INSTANCE.range('a', 'z')
                    )
            );
            fail();
        } catch (NoSuchElementException ignored) {}
    }

    private static void shuffle_helper(@NotNull String input, @NotNull String output) {
        shuffle_helper(readIntegerListWithNulls(input), output);
    }

    private static void shuffle_helper(@NotNull List<Integer> input, @NotNull String output) {
        List<Integer> xs = toList(input);
        P.shuffle(xs);
        aeqit(xs, output);
        P.reset();
    }

    @Test
    public void testShuffle() {
        shuffle_helper("[]", "[]");
        shuffle_helper("[5]", "[5]");
        shuffle_helper("[1, 2]", "[2, 1]");
        shuffle_helper("[1, 2, 3]", "[2, 1, 3]");
        shuffle_helper("[1, 2, 3, 4]", "[2, 4, 1, 3]");
        shuffle_helper("[1, 2, 2, 4]", "[2, 4, 1, 2]");
        shuffle_helper("[2, 2, 2, 2]", "[2, 2, 2, 2]");
        shuffle_helper("[3, 1, 4, 1]", "[1, 1, 3, 4]");
        shuffle_helper("[3, 1, null, 1]", "[1, 1, 3, null]");
        shuffle_helper(toList(IterableUtils.range(1, 10)), "[10, 4, 1, 9, 8, 7, 5, 2, 3, 6]");
    }

    private static void permutationsFinite_helper(@NotNull String input, @NotNull String output) {
        permutationsFinite_helper(readIntegerListWithNulls(input), output);
    }

    private static void permutationsFinite_helper(@NotNull List<Integer> input, @NotNull String output) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.permutationsFinite(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    @Test
    public void testPermutationsFinite() {
        permutationsFinite_helper("[]", "RandomProvider_permutationsFinite_i");
        permutationsFinite_helper("[5]", "RandomProvider_permutationsFinite_ii");
        permutationsFinite_helper("[1, 2]", "RandomProvider_permutationsFinite_iii");
        permutationsFinite_helper("[1, 2, 3]", "RandomProvider_permutationsFinite_iv");
        permutationsFinite_helper("[1, 2, 3, 4]", "RandomProvider_permutationsFinite_v");
        permutationsFinite_helper("[1, 2, 2, 4]", "RandomProvider_permutationsFinite_vi");
        permutationsFinite_helper("[2, 2, 2, 2]", "RandomProvider_permutationsFinite_vii");
        permutationsFinite_helper("[3, 1, 4, 1]", "RandomProvider_permutationsFinite_viii");
        permutationsFinite_helper("[3, 1, null, 1]", "RandomProvider_permutationsFinite_ix");
        permutationsFinite_helper(toList(IterableUtils.range(1, 10)), "RandomProvider_permutationsFinite_x");
    }

    private static void stringPermutations_helper(@NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringPermutations(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    @Test
    public void testStringPermutations() {
        stringPermutations_helper("", "RandomProvider_stringPermutations_i");
        stringPermutations_helper("a", "RandomProvider_stringPermutations_ii");
        stringPermutations_helper("abc", "RandomProvider_stringPermutations_iii");
        stringPermutations_helper("foo", "RandomProvider_stringPermutations_iv");
        stringPermutations_helper("hello", "RandomProvider_stringPermutations_v");
        stringPermutations_helper("Mississippi", "RandomProvider_stringPermutations_vi");
    }

    private static void prefixPermutations_helper(int scale, @NotNull String input, @NotNull String output) {
        prefixPermutations_helper(scale, readIntegerListWithNulls(input), output);
    }

    private static void prefixPermutations_helper(int scale, @NotNull List<Integer> input, @NotNull String output) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(IterableUtils::toList, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(Testing::its, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private static void prefixPermutations_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).prefixPermutations(readIntegerListWithNulls(input)));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testPrefixPermutations() {
        prefixPermutations_helper(1, "[]", "RandomProvider_prefixPermutations_i");
        prefixPermutations_helper(2, "[]", "RandomProvider_prefixPermutations_ii");
        prefixPermutations_helper(4, "[]", "RandomProvider_prefixPermutations_iii");
        prefixPermutations_helper(1, "[5]", "RandomProvider_prefixPermutations_iv");
        prefixPermutations_helper(2, "[5]", "RandomProvider_prefixPermutations_v");
        prefixPermutations_helper(4, "[5]", "RandomProvider_prefixPermutations_vi");
        prefixPermutations_helper(1, "[1, 2]", "RandomProvider_prefixPermutations_vii");
        prefixPermutations_helper(2, "[1, 2]", "RandomProvider_prefixPermutations_viii");
        prefixPermutations_helper(4, "[1, 2]", "RandomProvider_prefixPermutations_ix");
        prefixPermutations_helper(1, "[1, 2, 3]", "RandomProvider_prefixPermutations_x");
        prefixPermutations_helper(2, "[1, 2, 3]", "RandomProvider_prefixPermutations_xi");
        prefixPermutations_helper(4, "[1, 2, 3]", "RandomProvider_prefixPermutations_xii");
        prefixPermutations_helper(1, "[1, 2, 3, 4]", "RandomProvider_prefixPermutations_xiii");
        prefixPermutations_helper(2, "[1, 2, 3, 4]", "RandomProvider_prefixPermutations_xiv");
        prefixPermutations_helper(4, "[1, 2, 3, 4]", "RandomProvider_prefixPermutations_xv");
        prefixPermutations_helper(1, "[1, 2, 2, 4]", "RandomProvider_prefixPermutations_xvi");
        prefixPermutations_helper(2, "[1, 2, 2, 4]", "RandomProvider_prefixPermutations_xvii");
        prefixPermutations_helper(4, "[1, 2, 2, 4]", "RandomProvider_prefixPermutations_xviii");
        prefixPermutations_helper(1, "[2, 2, 2, 2]", "RandomProvider_prefixPermutations_xix");
        prefixPermutations_helper(2, "[2, 2, 2, 2]", "RandomProvider_prefixPermutations_xx");
        prefixPermutations_helper(4, "[2, 2, 2, 2]", "RandomProvider_prefixPermutations_xxi");
        prefixPermutations_helper(1, "[3, 1, 4, 1]", "RandomProvider_prefixPermutations_xxii");
        prefixPermutations_helper(2, "[3, 1, 4, 1]", "RandomProvider_prefixPermutations_xxiii");
        prefixPermutations_helper(4, "[3, 1, 4, 1]", "RandomProvider_prefixPermutations_xxiv");
        prefixPermutations_helper(1, "[3, 1, null, 1]", "RandomProvider_prefixPermutations_xxv");
        prefixPermutations_helper(2, "[3, 1, null, 1]", "RandomProvider_prefixPermutations_xxvi");
        prefixPermutations_helper(4, "[3, 1, null, 1]", "RandomProvider_prefixPermutations_xxvii");
        prefixPermutations_helper(1, "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", "RandomProvider_prefixPermutations_xxviii");
        prefixPermutations_helper(2, "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", "RandomProvider_prefixPermutations_xxix");
        prefixPermutations_helper(4, "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", "RandomProvider_prefixPermutations_xxx");
        prefixPermutations_helper(
                1,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "RandomProvider_prefixPermutations_xxxi"
        );
        prefixPermutations_helper(
                2,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "RandomProvider_prefixPermutations_xxxii"
        );
        prefixPermutations_helper(
                4,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "RandomProvider_prefixPermutations_xxxiii"
        );
        prefixPermutations_helper(1, repeat(1), "RandomProvider_prefixPermutations_xxxiv");
        prefixPermutations_helper(2, repeat(1), "RandomProvider_prefixPermutations_xxxv");
        prefixPermutations_helper(4, repeat(1), "RandomProvider_prefixPermutations_xxxvi");
        prefixPermutations_fail_helper(0, "[1, 2, 3]");
        prefixPermutations_fail_helper(-1, "[1, 2, 3]");
    }

    private static void strings_int_Iterable_helper(int size, @NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.strings(size, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void strings_int_Iterable_fail_helper(int size, @NotNull String input) {
        try {
            P.strings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStrings_int_String() {
        strings_int_Iterable_helper(0, "", "RandomProvider_strings_int_String_i");
        strings_int_Iterable_helper(0, "a", "RandomProvider_strings_int_String_ii");
        strings_int_Iterable_helper(1, "a", "RandomProvider_strings_int_String_iii");
        strings_int_Iterable_helper(2, "a", "RandomProvider_strings_int_String_iv");
        strings_int_Iterable_helper(3, "a", "RandomProvider_strings_int_String_v");
        strings_int_Iterable_helper(0, "abc", "RandomProvider_strings_int_String_vi");
        strings_int_Iterable_helper(1, "abc", "RandomProvider_strings_int_String_vii");
        strings_int_Iterable_helper(2, "abc", "RandomProvider_strings_int_String_viii");
        strings_int_Iterable_helper(3, "abc", "RandomProvider_strings_int_String_ix");
        strings_int_Iterable_helper(0, "abbc", "RandomProvider_strings_int_String_x");
        strings_int_Iterable_helper(1, "abbc", "RandomProvider_strings_int_String_xi");
        strings_int_Iterable_helper(2, "abbc", "RandomProvider_strings_int_String_xii");
        strings_int_Iterable_helper(3, "abbc", "RandomProvider_strings_int_String_xiii");
        strings_int_Iterable_helper(0, "Mississippi", "RandomProvider_strings_int_String_xiv");
        strings_int_Iterable_helper(1, "Mississippi", "RandomProvider_strings_int_String_xv");
        strings_int_Iterable_helper(2, "Mississippi", "RandomProvider_strings_int_String_xvi");
        strings_int_Iterable_helper(3, "Mississippi", "RandomProvider_strings_int_String_xvii");

        strings_int_Iterable_fail_helper(1, "");
        strings_int_Iterable_fail_helper(-1, "abc");
    }

    private static void strings_int_helper(int size, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.strings(size)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void strings_int_fail_helper(int size) {
        try {
            P.strings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStrings_int() {
        strings_int_helper(0, "RandomProvider_strings_int_i");
        strings_int_helper(1, "RandomProvider_strings_int_ii");
        strings_int_helper(2, "RandomProvider_strings_int_iii");
        strings_int_helper(3, "RandomProvider_strings_int_iv");

        strings_int_fail_helper(-1);
    }

    private static void lists_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).lists(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void lists_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        lists_Iterable_helper(scale, P.uniformSample(readIntegerListWithNulls(input)), output, meanSize);
    }

    private static void lists_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).lists(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testLists_Iterable() {
        lists_Iterable_helper_uniform(1, "[5]", "RandomProvider_lists_Iterable_i", 1.0008359999977228);
        lists_Iterable_helper_uniform(2, "[5]", "RandomProvider_lists_Iterable_ii", 2.0020969999891216);
        lists_Iterable_helper_uniform(4, "[5]", "RandomProvider_lists_Iterable_iii", 4.004359999991779);
        lists_Iterable_helper_uniform(1, "[1, 2, 3]", "RandomProvider_lists_Iterable_iv", 1.00085799999768);
        lists_Iterable_helper_uniform(2, "[1, 2, 3]", "RandomProvider_lists_Iterable_v", 2.0023509999891522);
        lists_Iterable_helper_uniform(4, "[1, 2, 3]", "RandomProvider_lists_Iterable_vi", 4.00516399999172);
        lists_Iterable_helper_uniform(1, "[1, null, 3]", "RandomProvider_lists_Iterable_vii", 1.00085799999768);
        lists_Iterable_helper_uniform(2, "[1, null, 3]", "RandomProvider_lists_Iterable_viii", 2.0023509999891522);
        lists_Iterable_helper_uniform(4, "[1, null, 3]", "RandomProvider_lists_Iterable_ix", 4.00516399999172);
        lists_Iterable_helper_uniform(1, "[1, 2, 3, 4]", "RandomProvider_lists_Iterable_x", 1.0006389999976706);
        lists_Iterable_helper_uniform(2, "[1, 2, 3, 4]", "RandomProvider_lists_Iterable_xi", 2.0037019999891394);
        lists_Iterable_helper_uniform(4, "[1, 2, 3, 4]", "RandomProvider_lists_Iterable_xii", 4.00571499999147);
        lists_Iterable_helper_uniform(1, "[1, 2, 2, 4]", "RandomProvider_lists_Iterable_xiii", 1.0006389999976706);
        lists_Iterable_helper_uniform(2, "[1, 2, 2, 4]", "RandomProvider_lists_Iterable_xiv", 2.0037019999891394);
        lists_Iterable_helper_uniform(4, "[1, 2, 2, 4]", "RandomProvider_lists_Iterable_xv", 4.00571499999147);
        lists_Iterable_helper_uniform(1, "[2, 2, 2, 2]", "RandomProvider_lists_Iterable_xvi", 1.0006389999976706);
        lists_Iterable_helper_uniform(2, "[2, 2, 2, 2]", "RandomProvider_lists_Iterable_xvii", 2.0037019999891394);
        lists_Iterable_helper_uniform(4, "[2, 2, 2, 2]", "RandomProvider_lists_Iterable_xviii", 4.00571499999147);
        lists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_lists_Iterable_xix",
                0.998919999997707
        );
        lists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_lists_Iterable_xx",
                2.003595999989077
        );
        lists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_lists_Iterable_xxi",
                4.002965999991581
        );
        lists_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_lists_Iterable_xxii",
                1.0012699999976906
        );
        lists_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_lists_Iterable_xxiii",
                2.001994999989098
        );
        lists_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_lists_Iterable_xxiv",
                4.0083209999916205
        );
        lists_Iterable_helper(1, repeat(1), "RandomProvider_lists_Iterable_xxv", 1.0008359999977228);
        lists_Iterable_helper(2, repeat(1), "RandomProvider_lists_Iterable_xxvi", 2.0020969999891216);
        lists_Iterable_helper(4, repeat(1), "RandomProvider_lists_Iterable_xxvii", 4.004359999991779);
        lists_Iterable_fail_helper(1, Collections.emptyList());
        lists_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        lists_Iterable_fail_helper(0, P.integers());
        lists_Iterable_fail_helper(-1, P.integers());
    }

    private static void strings_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).strings(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void strings_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).strings(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStrings_String() {
        strings_String_helper(1, "a", "RandomProvider_strings_String_i", 1.0008359999977228);
        strings_String_helper(2, "a", "RandomProvider_strings_String_ii", 2.0020969999891216);
        strings_String_helper(4, "a", "RandomProvider_strings_String_iii", 4.004359999991779);
        strings_String_helper(1, "abc", "RandomProvider_strings_String_iv", 1.00085799999768);
        strings_String_helper(2, "abc", "RandomProvider_strings_String_v", 2.0023509999891522);
        strings_String_helper(4, "abc", "RandomProvider_strings_String_vi", 4.00516399999172);
        strings_String_helper(1, "abbc", "RandomProvider_strings_String_vii", 1.0006389999976706);
        strings_String_helper(2, "abbc", "RandomProvider_strings_String_viii", 2.0037019999891394);
        strings_String_helper(4, "abbc", "RandomProvider_strings_String_ix", 4.00571499999147);
        strings_String_helper(1, "Mississippi", "RandomProvider_strings_String_x", 0.9996679999977037);
        strings_String_helper(2, "Mississippi", "RandomProvider_strings_String_xi", 2.0026269999890762);
        strings_String_helper(4, "Mississippi", "RandomProvider_strings_String_xii", 4.0051349999917525);
        strings_String_fail_helper(1, "");
        strings_String_fail_helper(0, "abc");
        strings_String_fail_helper(-1, "abc");
    }

    private static void strings_helper(int scale, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).strings()));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void strings_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).strings());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStrings() {
        strings_helper(1, "RandomProvider_strings_i", 1.0006389999976706);
        strings_helper(2, "RandomProvider_strings_ii", 2.0037019999891394);
        strings_helper(4, "RandomProvider_strings_iii", 4.00571499999147);
        strings_fail_helper(0);
        strings_fail_helper(-1);
    }

    private static void listsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsAtLeast(minSize, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        listsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void listsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).listsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testListsAtLeast() {
        listsAtLeast_helper_uniform(2, 1, "[5]", "RandomProvider_listsAtLeast_i", 2.0008359999800347);
        listsAtLeast_helper_uniform(5, 3, "[5]", "RandomProvider_listsAtLeast_ii", 5.002096999996331);
        listsAtLeast_helper_uniform(32, 8, "[5]", "RandomProvider_listsAtLeast_iii", 32.00360900002322);
        listsAtLeast_helper_uniform(2, 1, "[1, 2, 3]", "RandomProvider_listsAtLeast_iv", 1.9993039999798474);
        listsAtLeast_helper_uniform(5, 3, "[1, 2, 3]", "RandomProvider_listsAtLeast_v", 5.003739999996368);
        listsAtLeast_helper_uniform(32, 8, "[1, 2, 3]", "RandomProvider_listsAtLeast_vi", 32.010685000021894);
        listsAtLeast_helper_uniform(2, 1, "[1, null, 3]", "RandomProvider_listsAtLeast_vii", 1.9993039999798474);
        listsAtLeast_helper_uniform(5, 3, "[1, null, 3]", "RandomProvider_listsAtLeast_viii", 5.003739999996368);
        listsAtLeast_helper_uniform(32, 8, "[1, null, 3]", "RandomProvider_listsAtLeast_ix", 32.010685000021894);
        listsAtLeast_helper_uniform(2, 1, "[1, 2, 3, 4]", "RandomProvider_listsAtLeast_x", 1.999585999979838);
        listsAtLeast_helper_uniform(5, 3, "[1, 2, 3, 4]", "RandomProvider_listsAtLeast_xi", 5.00315899999616);
        listsAtLeast_helper_uniform(32, 8, "[1, 2, 3, 4]", "RandomProvider_listsAtLeast_xii", 32.008717000021356);
        listsAtLeast_helper_uniform(2, 1, "[1, 2, 2, 4]", "RandomProvider_listsAtLeast_xiii", 1.999585999979838);
        listsAtLeast_helper_uniform(5, 3, "[1, 2, 2, 4]", "RandomProvider_listsAtLeast_xiv", 5.00315899999616);
        listsAtLeast_helper_uniform(32, 8, "[1, 2, 2, 4]", "RandomProvider_listsAtLeast_xv", 32.008717000021356);
        listsAtLeast_helper_uniform(2, 1, "[2, 2, 2, 2]", "RandomProvider_listsAtLeast_xvi", 1.999585999979838);
        listsAtLeast_helper_uniform(5, 3, "[2, 2, 2, 2]", "RandomProvider_listsAtLeast_xvii", 5.00315899999616);
        listsAtLeast_helper_uniform(32, 8, "[2, 2, 2, 2]", "RandomProvider_listsAtLeast_xviii", 32.008717000021356);
        listsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_listsAtLeast_xix",
                1.9987289999797695
        );
        listsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_listsAtLeast_xx",
                5.002305999996172
        );
        listsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_listsAtLeast_xxi",
                31.997066000022638
        );
        listsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsAtLeast_xxiii",
                2.001126999979881
        );
        listsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsAtLeast_xxiv",
                5.001189999995907
        );
        listsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsAtLeast_xxv",
                32.00730000002313
        );
        listsAtLeast_helper(2, 1, repeat(1), "RandomProvider_listsAtLeast_xxvi", 2.0008359999800347);
        listsAtLeast_helper(5, 3, repeat(1), "RandomProvider_listsAtLeast_xxvii", 5.002096999996331);
        listsAtLeast_helper(32, 8, repeat(1), "RandomProvider_listsAtLeast_xxviii", 32.00360900002322);

        listsAtLeast_fail_helper(5, 3, Collections.emptyList());
        listsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        listsAtLeast_fail_helper(5, 5, P.integers());
        listsAtLeast_fail_helper(4, 5, P.integers());
    }

    private static void stringsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsAtLeast(minSize, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsAtLeast_int_String() {
        stringsAtLeast_int_String_helper(2, 1, "a", "RandomProvider_stringsAtLeast_int_String_i", 2.0008359999800347);
        stringsAtLeast_int_String_helper(5, 3, "a", "RandomProvider_stringsAtLeast_int_String_ii", 5.002096999996331);
        stringsAtLeast_int_String_helper(
                32,
                8,
                "a",
                "RandomProvider_stringsAtLeast_int_String_iii",
                32.00360900002322
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "RandomProvider_stringsAtLeast_int_String_iv",
                1.9993039999798474
        );
        stringsAtLeast_int_String_helper(5, 3, "abc", "RandomProvider_stringsAtLeast_int_String_v", 5.003739999996368);
        stringsAtLeast_int_String_helper(
                32,
                8,
                "abc",
                "RandomProvider_stringsAtLeast_int_String_vi",
                32.010685000021894
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "RandomProvider_stringsAtLeast_int_String_vii",
                1.999585999979838
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "RandomProvider_stringsAtLeast_int_String_viii",
                5.00315899999616
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "abbc",
                "RandomProvider_stringsAtLeast_int_String_ix",
                32.008717000021356
        );
        stringsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "RandomProvider_stringsAtLeast_int_String_x",
                1.9990949999798069
        );
        stringsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "RandomProvider_stringsAtLeast_int_String_xi",
                5.003636999996235
        );
        stringsAtLeast_int_String_helper(
                32,
                8,
                "Mississippi",
                "RandomProvider_stringsAtLeast_int_String_xii",
                32.00263800002314
        );

        stringsAtLeast_int_String_fail_helper(5, 3, "");
        stringsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringsAtLeast_int_helper(int scale, int minSize, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsAtLeast(minSize)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsAtLeast_int() {
        stringsAtLeast_int_helper(2, 1, "RandomProvider_stringsAtLeast_int_i", 1.999585999979838);
        stringsAtLeast_int_helper(5, 3, "RandomProvider_stringsAtLeast_int_ii", 5.00315899999616);
        stringsAtLeast_int_helper(32, 8, "RandomProvider_stringsAtLeast_int_iii", 32.008717000021356);
        stringsAtLeast_int_fail_helper(5, 5);
        stringsAtLeast_int_fail_helper(4, 5);
    }

    private static void distinctStrings_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.distinctStrings(size, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void distinctStrings_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.distinctStrings(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_int_String() {
        distinctStrings_int_String_helper(0, "a", "RandomProvider_distinctStrings_int_String_i");
        distinctStrings_int_String_helper(1, "a", "RandomProvider_distinctStrings_int_String_ii");
        distinctStrings_int_String_helper(0, "abc", "RandomProvider_distinctStrings_int_String_iii");
        distinctStrings_int_String_helper(1, "abc", "RandomProvider_distinctStrings_int_String_iv");
        distinctStrings_int_String_helper(2, "abc", "RandomProvider_distinctStrings_int_String_v");
        distinctStrings_int_String_helper(3, "abc", "RandomProvider_distinctStrings_int_String_vi");
        distinctStrings_int_String_helper(0, "abbc", "RandomProvider_distinctStrings_int_String_vii");
        distinctStrings_int_String_helper(1, "abbc", "RandomProvider_distinctStrings_int_String_viii");
        distinctStrings_int_String_helper(2, "abbc", "RandomProvider_distinctStrings_int_String_ix");
        distinctStrings_int_String_helper(3, "abbc", "RandomProvider_distinctStrings_int_String_x");
        distinctStrings_int_String_helper(0, "Mississippi", "RandomProvider_distinctStrings_int_String_xi");
        distinctStrings_int_String_helper(1, "Mississippi", "RandomProvider_distinctStrings_int_String_xii");
        distinctStrings_int_String_helper(2, "Mississippi", "RandomProvider_distinctStrings_int_String_xiii");
        distinctStrings_int_String_helper(3, "Mississippi", "RandomProvider_distinctStrings_int_String_xiv");

        distinctStrings_int_String_fail_helper(1, "");
        distinctStrings_int_String_fail_helper(-1, "abc");
    }

    private static void distinctStrings_int_helper(int size, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.distinctStrings(size)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void distinctStrings_int_fail_helper(int size) {
        try {
            P.distinctStrings(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_int() {
        distinctStrings_int_helper(0, "RandomProvider_distinctStrings_int_i");
        distinctStrings_int_helper(1, "RandomProvider_distinctStrings_int_ii");
        distinctStrings_int_helper(2, "RandomProvider_distinctStrings_int_iii");
        distinctStrings_int_helper(3, "RandomProvider_distinctStrings_int_iv");

        distinctStrings_int_fail_helper(-1);
    }

    private static void distinctLists_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctLists(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void distinctLists_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        distinctLists_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void distinctLists_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).distinctLists(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctLists_Iterable() {
        distinctLists_Iterable_helper_uniform(1, "[5]", "RandomProvider_distinctLists_Iterable_i", 0.5008749999935656);
        distinctLists_Iterable_helper_uniform(
                2,
                "[5]",
                "RandomProvider_distinctLists_Iterable_ii",
                0.6661869999983192
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[5]",
                "RandomProvider_distinctLists_Iterable_iii",
                0.7998060000021615
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3]",
                "RandomProvider_distinctLists_Iterable_iv",
                0.7507059999970308
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3]",
                "RandomProvider_distinctLists_Iterable_v",
                1.2008789999923022
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3]",
                "RandomProvider_distinctLists_Iterable_vi",
                1.7145229999887661
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, null, 3]",
                "RandomProvider_distinctLists_Iterable_vii",
                0.7507059999970308
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, null, 3]",
                "RandomProvider_distinctLists_Iterable_viii",
                1.2008789999923022
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, null, 3]",
                "RandomProvider_distinctLists_Iterable_ix",
                1.7145229999887661
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4]",
                "RandomProvider_distinctLists_Iterable_x",
                0.8006769999971934
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4]",
                "RandomProvider_distinctLists_Iterable_xi",
                1.334835999990812
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4]",
                "RandomProvider_distinctLists_Iterable_xii",
                2.001787999981212
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 2, 4]",
                "RandomProvider_distinctLists_Iterable_xiii",
                0.7339709999971153
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 2, 4]",
                "RandomProvider_distinctLists_Iterable_xiv",
                1.1676389999927037
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 2, 4]",
                "RandomProvider_distinctLists_Iterable_xv",
                1.667697999989275
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[2, 2, 2, 2]",
                "RandomProvider_distinctLists_Iterable_xvi",
                0.5004429999935531
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[2, 2, 2, 2]",
                "RandomProvider_distinctLists_Iterable_xvii",
                0.6669589999983414
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[2, 2, 2, 2]",
                "RandomProvider_distinctLists_Iterable_xviii",
                0.7999900000021668
        );
        distinctLists_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctLists_Iterable_xix",
                0.9078379999975383
        );
        distinctLists_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctLists_Iterable_xx",
                1.6697689999898184
        );
        distinctLists_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctLists_Iterable_xxi",
                2.8588639999882393
        );
        distinctLists_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctLists_Iterable_xxii",
                0.8811449999975006
        );
        distinctLists_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctLists_Iterable_xxiii",
                1.583489999990105
        );
        distinctLists_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctLists_Iterable_xxiv",
                2.668782999988186
        );
        distinctLists_Iterable_helper(1, repeat(1), "RandomProvider_distinctLists_Iterable_xxv", 0.5008749999935656);
        distinctLists_Iterable_helper(2, repeat(1), "RandomProvider_distinctLists_Iterable_xxvi", 0.6661869999983192);
        distinctLists_Iterable_helper(4, repeat(1), "RandomProvider_distinctLists_Iterable_xxvii", 0.7998060000021615);

        distinctLists_Iterable_fail_helper(1, Collections.emptyList());
        distinctLists_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        distinctLists_Iterable_fail_helper(0, P.integers());
        distinctLists_Iterable_fail_helper(-1, P.integers());
    }

    private static void distinctStrings_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStrings(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStrings_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).distinctStrings(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings_String() {
        distinctStrings_String_helper(1, "a", "RandomProvider_distinctStrings_String_i", 0.5008749999935656);
        distinctStrings_String_helper(2, "a", "RandomProvider_distinctStrings_String_ii", 0.6661869999983192);
        distinctStrings_String_helper(4, "a", "RandomProvider_distinctStrings_String_iii", 0.7998060000021615);
        distinctStrings_String_helper(1, "abc", "RandomProvider_distinctStrings_String_iv", 0.7507059999970308);
        distinctStrings_String_helper(2, "abc", "RandomProvider_distinctStrings_String_v", 1.2008789999923022);
        distinctStrings_String_helper(4, "abc", "RandomProvider_distinctStrings_String_vi", 1.7145229999887661);
        distinctStrings_String_helper(1, "abbc", "RandomProvider_distinctStrings_String_vii", 0.7339709999971153);
        distinctStrings_String_helper(2, "abbc", "RandomProvider_distinctStrings_String_viii", 1.1676389999927037);
        distinctStrings_String_helper(4, "abbc", "RandomProvider_distinctStrings_String_ix", 1.667697999989275);
        distinctStrings_String_helper(1, "Mississippi", "RandomProvider_distinctStrings_String_x", 0.7700039999971866);
        distinctStrings_String_helper(
                2,
                "Mississippi",
                "RandomProvider_distinctStrings_String_xi",
                1.2632049999918284
        );
        distinctStrings_String_helper(
                4,
                "Mississippi",
                "RandomProvider_distinctStrings_String_xii",
                1.8740139999846195
        );

        distinctStrings_String_fail_helper(1, "");
        distinctStrings_String_fail_helper(0, "abc");
        distinctStrings_String_fail_helper(-1, "abc");
    }

    private static void distinctStrings_helper(int scale, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStrings()));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStrings_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).distinctStrings());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStrings() {
        distinctStrings_helper(1, "RandomProvider_distinctStrings_i", 1.0006239999976707);
        distinctStrings_helper(2, "RandomProvider_distinctStrings_ii", 2.0036399999891383);
        distinctStrings_helper(4, "RandomProvider_distinctStrings_iii", 4.005472999991468);

        distinctStrings_fail_helper(0);
        distinctStrings_fail_helper(-1);
    }

    private static void distinctListsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctListsAtLeast(minSize, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void distinctListsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        distinctListsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void distinctListsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).distinctListsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctListsAtLeast() {
        distinctListsAtLeast_helper_uniform(2, 1, "[5]", "RandomProvider_distinctListsAtLeast_i", 1.000000000007918);
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3]",
                "RandomProvider_distinctListsAtLeast_ii",
                1.50008299998526
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3]",
                "RandomProvider_distinctListsAtLeast_iii",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, null, 3]",
                "RandomProvider_distinctListsAtLeast_iv",
                1.50008299998526
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, null, 3]",
                "RandomProvider_distinctListsAtLeast_v",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4]",
                "RandomProvider_distinctListsAtLeast_vi",
                1.5996069999831977
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4]",
                "RandomProvider_distinctListsAtLeast_vii",
                3.3338519999899345
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 2, 4]",
                "RandomProvider_distinctListsAtLeast_viii",
                1.466173999985577
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 2, 4]",
                "RandomProvider_distinctListsAtLeast_ix",
                2.9999999999775233
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[2, 2, 2, 2]",
                "RandomProvider_distinctListsAtLeast_x",
                1.000000000007918
        );
        distinctListsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctListsAtLeast_xi",
                1.8170889999810345
        );
        distinctListsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctListsAtLeast_xii",
                4.168420999985633
        );
        distinctListsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_distinctListsAtLeast_xiii",
                9.41189799992237
        );
        distinctListsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctListsAtLeast_xiv",
                1.7601419999815262
        );
        distinctListsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctListsAtLeast_xv",
                3.989958999983499
        );
        distinctListsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_distinctListsAtLeast_xvi",
                10.378239999978224
        );
        distinctListsAtLeast_helper(2, 1, repeat(1), "RandomProvider_distinctListsAtLeast_xvii", 1.000000000007918);

        distinctListsAtLeast_fail_helper(5, 3, Collections.emptyList());
        distinctListsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        distinctListsAtLeast_fail_helper(5, 5, P.integers());
        distinctListsAtLeast_fail_helper(4, 5, P.integers());
    }

    private static void distinctStringsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStringsAtLeast(minSize, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStringsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).distinctStringsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStringsAtLeast_int_String() {
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "RandomProvider_distinctStringsAtLeast_int_String_i",
                1.000000000007918
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "RandomProvider_distinctStringsAtLeast_int_String_ii",
                1.50008299998526
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "RandomProvider_distinctStringsAtLeast_int_String_iii",
                2.9999999999775233
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "RandomProvider_distinctStringsAtLeast_int_String_iv",
                1.466173999985577
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "RandomProvider_distinctStringsAtLeast_int_String_v",
                2.9999999999775233
        );
        distinctStringsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "RandomProvider_distinctStringsAtLeast_int_String_vi",
                1.5401079999842737
        );
        distinctStringsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "RandomProvider_distinctStringsAtLeast_int_String_vii",
                3.2285219999851744
        );

        distinctStringsAtLeast_int_String_fail_helper(5, 3, "");
        distinctStringsAtLeast_int_String_fail_helper(5, 5, "abc");
        distinctStringsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void distinctStringsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).distinctStringsAtLeast(minSize)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void distinctStringsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).distinctStringsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testDistinctStringsAtLeast_int() {
        distinctStringsAtLeast_int_helper(2, 1, "RandomProvider_distinctStringsAtLeast_int_i", 1.9995569999798375);
        distinctStringsAtLeast_int_helper(5, 3, "RandomProvider_distinctStringsAtLeast_int_ii", 5.00299199999616);
        distinctStringsAtLeast_int_helper(32, 8, "RandomProvider_distinctStringsAtLeast_int_iii", 31.99690200002153);

        distinctStringsAtLeast_int_fail_helper(5, 5);
        distinctStringsAtLeast_int_fail_helper(4, 5);
    }

    private static void stringBags_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringBags(size, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void stringBags_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.stringBags(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringBags_int_String() {
        stringBags_int_String_helper(0, "a", "RandomProvider_stringBags_int_String_i");
        stringBags_int_String_helper(1, "a", "RandomProvider_stringBags_int_String_ii");
        stringBags_int_String_helper(2, "a", "RandomProvider_stringBags_int_String_iii");
        stringBags_int_String_helper(3, "a", "RandomProvider_stringBags_int_String_iv");
        stringBags_int_String_helper(0, "abc", "RandomProvider_stringBags_int_String_v");
        stringBags_int_String_helper(1, "abc", "RandomProvider_stringBags_int_String_vi");
        stringBags_int_String_helper(2, "abc", "RandomProvider_stringBags_int_String_vii");
        stringBags_int_String_helper(3, "abc", "RandomProvider_stringBags_int_String_viii");
        stringBags_int_String_helper(0, "abbc", "RandomProvider_stringBags_int_String_ix");
        stringBags_int_String_helper(1, "abbc", "RandomProvider_stringBags_int_String_x");
        stringBags_int_String_helper(2, "abbc", "RandomProvider_stringBags_int_String_xi");
        stringBags_int_String_helper(3, "abbc", "RandomProvider_stringBags_int_String_xii");
        stringBags_int_String_helper(0, "Mississippi", "RandomProvider_stringBags_int_String_xiii");
        stringBags_int_String_helper(1, "Mississippi", "RandomProvider_stringBags_int_String_xiv");
        stringBags_int_String_helper(2, "Mississippi", "RandomProvider_stringBags_int_String_xv");
        stringBags_int_String_helper(3, "Mississippi", "RandomProvider_stringBags_int_String_xvi");

        stringBags_int_String_fail_helper(1, "");
        stringBags_int_String_fail_helper(-1, "abc");
    }

    private static void stringBags_int_helper(int size, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringBags(size)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void stringBags_int_fail_helper(int size) {
        try {
            P.stringBags(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringBags_int() {
        stringBags_int_helper(0, "RandomProvider_stringBags_int_i");
        stringBags_int_helper(1, "RandomProvider_stringBags_int_ii");
        stringBags_int_helper(2, "RandomProvider_stringBags_int_iii");
        stringBags_int_helper(3, "RandomProvider_stringBags_int_iv");

        stringBags_int_fail_helper(-1);
    }

    private static void bags_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).bags(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void bags_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        bags_Iterable_helper(scale, P.uniformSample(readIntegerListWithNulls(input)), output, meanSize);
    }

    private static void bags_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).bags(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBags_Iterable() {
        bags_Iterable_helper_uniform(1, "[5]", "RandomProvider_bags_Iterable_i", 1.0008359999977228);
        bags_Iterable_helper_uniform(2, "[5]", "RandomProvider_bags_Iterable_ii", 2.0020969999891216);
        bags_Iterable_helper_uniform(4, "[5]", "RandomProvider_bags_Iterable_iii", 4.004359999991779);
        bags_Iterable_helper_uniform(1, "[1, 2, 3]", "RandomProvider_bags_Iterable_iv", 1.00085799999768);
        bags_Iterable_helper_uniform(2, "[1, 2, 3]", "RandomProvider_bags_Iterable_v", 2.0023509999891522);
        bags_Iterable_helper_uniform(4, "[1, 2, 3]", "RandomProvider_bags_Iterable_vi", 4.00516399999172);
        bags_Iterable_helper_uniform(1, "[1, 2, 3, 4]", "RandomProvider_bags_Iterable_vii", 1.0006389999976706);
        bags_Iterable_helper_uniform(2, "[1, 2, 3, 4]", "RandomProvider_bags_Iterable_viii", 2.0037019999891394);
        bags_Iterable_helper_uniform(4, "[1, 2, 3, 4]", "RandomProvider_bags_Iterable_ix", 4.00571499999147);
        bags_Iterable_helper_uniform(1, "[1, 2, 2, 4]", "RandomProvider_bags_Iterable_x", 1.0006389999976706);
        bags_Iterable_helper_uniform(2, "[1, 2, 2, 4]", "RandomProvider_bags_Iterable_xi", 2.0037019999891394);
        bags_Iterable_helper_uniform(4, "[1, 2, 2, 4]", "RandomProvider_bags_Iterable_xii", 4.00571499999147);
        bags_Iterable_helper_uniform(1, "[2, 2, 2, 2]", "RandomProvider_bags_Iterable_xiii", 1.0006389999976706);
        bags_Iterable_helper_uniform(2, "[2, 2, 2, 2]", "RandomProvider_bags_Iterable_xiv", 2.0037019999891394);
        bags_Iterable_helper_uniform(4, "[2, 2, 2, 2]", "RandomProvider_bags_Iterable_xv", 4.00571499999147);
        bags_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bags_Iterable_xvi",
                0.998919999997707
        );
        bags_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bags_Iterable_xvii",
                2.003595999989077
        );
        bags_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bags_Iterable_xviii",
                4.002965999991581
        );
        bags_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bags_Iterable_xix",
                1.0012699999976906
        );
        bags_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bags_Iterable_xx",
                2.001994999989098
        );
        bags_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bags_Iterable_xxi",
                4.0083209999916205
        );
        bags_Iterable_helper(1, repeat(1), "RandomProvider_bags_Iterable_xxii", 1.0008359999977228);
        bags_Iterable_helper(2, repeat(1), "RandomProvider_bags_Iterable_xxiii", 2.0020969999891216);
        bags_Iterable_helper(4, repeat(1), "RandomProvider_bags_Iterable_xxiv", 4.004359999991779);

        bags_Iterable_fail_helper(1, Collections.emptyList());
        bags_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        bags_Iterable_fail_helper(0, P.integers());
        bags_Iterable_fail_helper(-1, P.integers());
        bags_Iterable_fail_helper(1, Arrays.asList(1, null, 3));
        bags_Iterable_fail_helper(1, Collections.singleton(null));
    }

    private static void stringBags_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBags(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBags_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringBags(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBags_String() {
        stringBags_String_helper(1, "a", "RandomProvider_stringBags_String_i", 1.0008359999977228);
        stringBags_String_helper(2, "a", "RandomProvider_stringBags_String_ii", 2.0020969999891216);
        stringBags_String_helper(4, "a", "RandomProvider_stringBags_String_iii", 4.004359999991779);
        stringBags_String_helper(1, "abc", "RandomProvider_stringBags_String_iv", 1.00085799999768);
        stringBags_String_helper(2, "abc", "RandomProvider_stringBags_String_v", 2.0023509999891522);
        stringBags_String_helper(4, "abc", "RandomProvider_stringBags_String_vi", 4.00516399999172);
        stringBags_String_helper(1, "abbc", "RandomProvider_stringBags_String_vii", 1.0006389999976706);
        stringBags_String_helper(2, "abbc", "RandomProvider_stringBags_String_viii", 2.0037019999891394);
        stringBags_String_helper(4, "abbc", "RandomProvider_stringBags_String_ix", 4.00571499999147);
        stringBags_String_helper(1, "Mississippi", "RandomProvider_stringBags_String_x", 0.9996679999977037);
        stringBags_String_helper(2, "Mississippi", "RandomProvider_stringBags_String_xi", 2.0026269999890762);
        stringBags_String_helper(4, "Mississippi", "RandomProvider_stringBags_String_xii", 4.0051349999917525);

        stringBags_String_fail_helper(1, "");
        stringBags_String_fail_helper(0, "abc");
        stringBags_String_fail_helper(-1, "abc");
    }

    private static void stringBags_helper(int scale, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBags()));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBags_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).stringBags());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBags() {
        stringBags_helper(1, "RandomProvider_stringBags_i", 1.0006389999976706);
        stringBags_helper(2, "RandomProvider_stringBags_ii", 2.0037019999891394);
        stringBags_helper(4, "RandomProvider_stringBags_iii", 4.00571499999147);

        stringBags_fail_helper(0);
        stringBags_fail_helper(-1);
    }

    private static void bagsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).bagsAtLeast(minSize, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void bagsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        bagsAtLeast_helper(
                scale,
                minSize,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void bagsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).bagsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testBagsAtLeast() {
        bagsAtLeast_helper_uniform(2, 1, "[5]", "RandomProvider_bagsAtLeast_i", 2.0008359999800347);
        bagsAtLeast_helper_uniform(5, 3, "[5]", "RandomProvider_bagsAtLeast_ii", 5.002096999996331);
        bagsAtLeast_helper_uniform(32, 8, "[5]", "RandomProvider_bagsAtLeast_iii", 32.00360900002322);
        bagsAtLeast_helper_uniform(2, 1, "[1, 2, 3]", "RandomProvider_bagsAtLeast_iv", 1.9993039999798474);
        bagsAtLeast_helper_uniform(5, 3, "[1, 2, 3]", "RandomProvider_bagsAtLeast_v", 5.003739999996368);
        bagsAtLeast_helper_uniform(32, 8, "[1, 2, 3]", "RandomProvider_bagsAtLeast_vi", 32.010685000021894);
        bagsAtLeast_helper_uniform(2, 1, "[1, 2, 3, 4]", "RandomProvider_bagsAtLeast_vii", 1.999585999979838);
        bagsAtLeast_helper_uniform(5, 3, "[1, 2, 3, 4]", "RandomProvider_bagsAtLeast_viii", 5.00315899999616);
        bagsAtLeast_helper_uniform(32, 8, "[1, 2, 3, 4]", "RandomProvider_bagsAtLeast_ix", 32.008717000021356);
        bagsAtLeast_helper_uniform(2, 1, "[1, 2, 2, 4]", "RandomProvider_bagsAtLeast_x", 1.999585999979838);
        bagsAtLeast_helper_uniform(5, 3, "[1, 2, 2, 4]", "RandomProvider_bagsAtLeast_xi", 5.00315899999616);
        bagsAtLeast_helper_uniform(32, 8, "[1, 2, 2, 4]", "RandomProvider_bagsAtLeast_xii", 32.008717000021356);
        bagsAtLeast_helper_uniform(2, 1, "[2, 2, 2, 2]", "RandomProvider_bagsAtLeast_xiii", 1.999585999979838);
        bagsAtLeast_helper_uniform(5, 3, "[2, 2, 2, 2]", "RandomProvider_bagsAtLeast_xiv", 5.00315899999616);
        bagsAtLeast_helper_uniform(32, 8, "[2, 2, 2, 2]", "RandomProvider_bagsAtLeast_xv", 32.008717000021356);
        bagsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bagsAtLeast_xvi",
                1.9987289999797695
        );
        bagsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bagsAtLeast_xvii",
                5.002305999996172
        );
        bagsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_bagsAtLeast_xviii",
                31.997066000022638
        );
        bagsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bagsAtLeast_xix",
                2.001126999979881
        );
        bagsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bagsAtLeast_xx",
                5.001189999995907
        );
        bagsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_bagsAtLeast_xxi",
                32.00730000002313
        );
        bagsAtLeast_helper(2, 1, repeat(1), "RandomProvider_bagsAtLeast_xxii", 2.0008359999800347);
        bagsAtLeast_helper(5, 3, repeat(1), "RandomProvider_bagsAtLeast_xxiii", 5.002096999996331);
        bagsAtLeast_helper(32, 8, repeat(1), "RandomProvider_bagsAtLeast_xxiv", 32.00360900002322);

        bagsAtLeast_fail_helper(5, 3, Collections.emptyList());
        bagsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        bagsAtLeast_fail_helper(5, 5, P.integers());
        bagsAtLeast_fail_helper(4, 5, P.integers());
        bagsAtLeast_fail_helper(2, 1, Collections.singletonList(null));
        bagsAtLeast_fail_helper(2, 1, Arrays.asList(1, null, 3));
    }

    private static void stringBagsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBagsAtLeast(minSize, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBagsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringBagsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBagsAtLeast_int_String() {
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "RandomProvider_stringBagsAtLeast_int_String_i",
                2.0008359999800347
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "a",
                "RandomProvider_stringBagsAtLeast_int_String_ii",
                5.002096999996331
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "a",
                "RandomProvider_stringBagsAtLeast_int_String_iii",
                32.00360900002322
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "RandomProvider_stringBagsAtLeast_int_String_iv",
                1.9993039999798474
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "RandomProvider_stringBagsAtLeast_int_String_v",
                5.003739999996368
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "abc",
                "RandomProvider_stringBagsAtLeast_int_String_vi",
                32.010685000021894
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "RandomProvider_stringBagsAtLeast_int_String_vii",
                1.999585999979838
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "RandomProvider_stringBagsAtLeast_int_String_viii",
                5.00315899999616
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "abbc",
                "RandomProvider_stringBagsAtLeast_int_String_ix",
                32.008717000021356
        );
        stringBagsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "RandomProvider_stringBagsAtLeast_int_String_x",
                1.9990949999798069
        );
        stringBagsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "RandomProvider_stringBagsAtLeast_int_String_xi",
                5.003636999996235
        );
        stringBagsAtLeast_int_String_helper(
                32,
                8,
                "Mississippi",
                "RandomProvider_stringBagsAtLeast_int_String_xii",
                32.00263800002314
        );

        stringBagsAtLeast_int_String_fail_helper(5, 3, "");
        stringBagsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringBagsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringBagsAtLeast_int_helper(int scale, int minSize, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringBagsAtLeast(minSize)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringBagsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringBagsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringBagsAtLeast_int() {
        stringBagsAtLeast_int_helper(2, 1, "RandomProvider_stringBagsAtLeast_int_i", 1.999585999979838);
        stringBagsAtLeast_int_helper(5, 3, "RandomProvider_stringBagsAtLeast_int_ii", 5.00315899999616);
        stringBagsAtLeast_int_helper(32, 8, "RandomProvider_stringBagsAtLeast_int_iii", 32.008717000021356);
        stringBagsAtLeast_int_fail_helper(5, 5);
        stringBagsAtLeast_int_fail_helper(4, 5);
    }

    private static void stringSubsets_int_String_helper(int size, @NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringSubsets(size, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void stringSubsets_int_String_fail_helper(int size, @NotNull String input) {
        try {
            P.stringSubsets(size, input);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_int_String() {
        stringSubsets_int_String_helper(0, "a", "RandomProvider_stringSubsets_int_String_i");
        stringSubsets_int_String_helper(1, "a", "RandomProvider_stringSubsets_int_String_ii");
        stringSubsets_int_String_helper(0, "abc", "RandomProvider_stringSubsets_int_String_iii");
        stringSubsets_int_String_helper(1, "abc", "RandomProvider_stringSubsets_int_String_iv");
        stringSubsets_int_String_helper(2, "abc", "RandomProvider_stringSubsets_int_String_v");
        stringSubsets_int_String_helper(3, "abc", "RandomProvider_stringSubsets_int_String_vi");
        stringSubsets_int_String_helper(0, "abbc", "RandomProvider_stringSubsets_int_String_vii");
        stringSubsets_int_String_helper(1, "abbc", "RandomProvider_stringSubsets_int_String_viii");
        stringSubsets_int_String_helper(2, "abbc", "RandomProvider_stringSubsets_int_String_ix");
        stringSubsets_int_String_helper(3, "abbc", "RandomProvider_stringSubsets_int_String_x");
        stringSubsets_int_String_helper(0, "Mississippi", "RandomProvider_stringSubsets_int_String_xi");
        stringSubsets_int_String_helper(1, "Mississippi", "RandomProvider_stringSubsets_int_String_xii");
        stringSubsets_int_String_helper(2, "Mississippi", "RandomProvider_stringSubsets_int_String_xiii");
        stringSubsets_int_String_helper(3, "Mississippi", "RandomProvider_stringSubsets_int_String_xiv");

        stringSubsets_int_String_fail_helper(1, "");
        stringSubsets_int_String_fail_helper(-1, "abc");
    }

    private static void stringSubsets_int_helper(int size, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringSubsets(size)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private void stringSubsets_int_fail_helper(int size) {
        try {
            P.stringBags(size);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_int() {
        stringSubsets_int_helper(0, "RandomProvider_stringSubsets_int_i");
        stringSubsets_int_helper(1, "RandomProvider_stringSubsets_int_ii");
        stringSubsets_int_helper(2, "RandomProvider_stringSubsets_int_iii");
        stringSubsets_int_helper(3, "RandomProvider_stringSubsets_int_iv");

        stringSubsets_int_fail_helper(-1);
    }

    private static void subsets_Iterable_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsets(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsets_Iterable_helper_uniform(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        subsets_Iterable_helper(
                scale,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void subsets_Iterable_fail_helper(int scale, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).subsets(input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsets_Iterable() {
        subsets_Iterable_helper_uniform(1, "[5]", "RandomProvider_subsets_Iterable_i", 0.5008749999935656);
        subsets_Iterable_helper_uniform(2, "[5]", "RandomProvider_subsets_Iterable_ii", 0.6661869999983192);
        subsets_Iterable_helper_uniform(4, "[5]", "RandomProvider_subsets_Iterable_iii", 0.7998060000021615);
        subsets_Iterable_helper_uniform(1, "[1, 2, 3]", "RandomProvider_subsets_Iterable_iv", 0.7507059999970308);
        subsets_Iterable_helper_uniform(2, "[1, 2, 3]", "RandomProvider_subsets_Iterable_v", 1.2008789999923022);
        subsets_Iterable_helper_uniform(4, "[1, 2, 3]", "RandomProvider_subsets_Iterable_vi", 1.7145229999887661);
        subsets_Iterable_helper_uniform(1, "[1, 2, 3, 4]", "RandomProvider_subsets_Iterable_vii", 0.8006769999971934);
        subsets_Iterable_helper_uniform(2, "[1, 2, 3, 4]", "RandomProvider_subsets_Iterable_viii", 1.334835999990812);
        subsets_Iterable_helper_uniform(4, "[1, 2, 3, 4]", "RandomProvider_subsets_Iterable_ix", 2.001787999981212);
        subsets_Iterable_helper_uniform(1, "[1, 2, 2, 4]", "RandomProvider_subsets_Iterable_x", 0.7339709999971153);
        subsets_Iterable_helper_uniform(2, "[1, 2, 2, 4]", "RandomProvider_subsets_Iterable_xi", 1.1676389999927037);
        subsets_Iterable_helper_uniform(4, "[1, 2, 2, 4]", "RandomProvider_subsets_Iterable_xii", 1.667697999989275);
        subsets_Iterable_helper_uniform(1, "[2, 2, 2, 2]", "RandomProvider_subsets_Iterable_xiii", 0.5004429999935531);
        subsets_Iterable_helper_uniform(2, "[2, 2, 2, 2]", "RandomProvider_subsets_Iterable_xiv", 0.6669589999983414);
        subsets_Iterable_helper_uniform(4, "[2, 2, 2, 2]", "RandomProvider_subsets_Iterable_xv", 0.7999900000021668);
        subsets_Iterable_helper_uniform(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsets_Iterable_xvi",
                0.9078379999975383
        );
        subsets_Iterable_helper_uniform(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsets_Iterable_xvii",
                1.6697689999898184
        );
        subsets_Iterable_helper_uniform(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsets_Iterable_xviii",
                2.8588639999882393
        );
        subsets_Iterable_helper(
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsets_Iterable_xix",
                0.8811449999975006
        );
        subsets_Iterable_helper(
                2,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsets_Iterable_xx",
                1.583489999990105
        );
        subsets_Iterable_helper(
                4,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsets_Iterable_xxi",
                2.668782999988186
        );
        subsets_Iterable_helper(1, repeat(1), "RandomProvider_subsets_Iterable_xxii", 0.5008749999935656);
        subsets_Iterable_helper(2, repeat(1), "RandomProvider_subsets_Iterable_xxiii", 0.6661869999983192);
        subsets_Iterable_helper(4, repeat(1), "RandomProvider_subsets_Iterable_xxiv", 0.7998060000021615);

        subsets_Iterable_fail_helper(1, Collections.emptyList());
        subsets_Iterable_fail_helper(1, Arrays.asList(1, 2, 3));
        subsets_Iterable_fail_helper(0, P.integers());
        subsets_Iterable_fail_helper(-1, P.integers());
        subsets_Iterable_fail_helper(1, Arrays.asList(1, null, 3));
        subsets_Iterable_fail_helper(1, Collections.singleton(null));
    }

    private static void stringSubsets_String_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsets(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsets_String_fail_helper(int scale, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringSubsets(input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsets_String() {
        stringSubsets_String_helper(1, "a", "RandomProvider_stringSubsets_String_i", 0.5008749999935656);
        stringSubsets_String_helper(2, "a", "RandomProvider_stringSubsets_String_ii", 0.6661869999983192);
        stringSubsets_String_helper(4, "a", "RandomProvider_stringSubsets_String_iii", 0.7998060000021615);
        stringSubsets_String_helper(1, "abc", "RandomProvider_stringSubsets_String_iv", 0.7507059999970308);
        stringSubsets_String_helper(2, "abc", "RandomProvider_stringSubsets_String_v", 1.2008789999923022);
        stringSubsets_String_helper(4, "abc", "RandomProvider_stringSubsets_String_vi", 1.7145229999887661);
        stringSubsets_String_helper(1, "abbc", "RandomProvider_stringSubsets_String_vii", 0.7339709999971153);
        stringSubsets_String_helper(2, "abbc", "RandomProvider_stringSubsets_String_viii", 1.1676389999927037);
        stringSubsets_String_helper(4, "abbc", "RandomProvider_stringSubsets_String_ix", 1.667697999989275);
        stringSubsets_String_helper(1, "Mississippi", "RandomProvider_stringSubsets_String_x", 0.7700039999971866);
        stringSubsets_String_helper(2, "Mississippi", "RandomProvider_stringSubsets_String_xi", 1.2632049999918284);
        stringSubsets_String_helper(4, "Mississippi", "RandomProvider_stringSubsets_String_xii", 1.8740139999846195);

        stringSubsets_String_fail_helper(1, "");
        stringSubsets_String_fail_helper(0, "abc");
        stringSubsets_String_fail_helper(-1, "abc");
    }

    private static void stringSubsets_helper(int scale, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsets()));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsets_fail_helper(int scale) {
        try {
            toList(P.withScale(scale).stringSubsets());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsets() {
        stringSubsets_helper(1, "RandomProvider_stringSubsets_i", 1.0006239999976707);
        stringSubsets_helper(2, "RandomProvider_stringSubsets_ii", 2.0036399999891383);
        stringSubsets_helper(4, "RandomProvider_stringSubsets_iii", 4.005472999991468);

        stringSubsets_fail_helper(0);
        stringSubsets_fail_helper(-1);
    }

    private static void subsetsAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsetsAtLeast(minSize, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsetsAtLeast_helper_uniform(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        subsetsAtLeast_helper(scale, minSize, P.uniformSample(readIntegerListWithNulls(input)), output, meanSize);
    }

    private static void subsetsAtLeast_fail_helper(int scale, int minSize, @NotNull Iterable<Integer> input) {
        try {
            toList(P.withScale(scale).subsetsAtLeast(minSize, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsetsAtLeast() {
        subsetsAtLeast_helper_uniform(2, 1, "[5]", "RandomProvider_subsetsAtLeast_i", 1.000000000007918);
        subsetsAtLeast_helper_uniform(2, 1, "[1, 2, 3]", "RandomProvider_subsetsAtLeast_ii", 1.50008299998526);
        subsetsAtLeast_helper_uniform(5, 3, "[1, 2, 3]", "RandomProvider_subsetsAtLeast_iii", 2.9999999999775233);
        subsetsAtLeast_helper_uniform(2, 1, "[1, 2, 3, 4]", "RandomProvider_subsetsAtLeast_iv", 1.5996069999831977);
        subsetsAtLeast_helper_uniform(5, 3, "[1, 2, 3, 4]", "RandomProvider_subsetsAtLeast_v", 3.3338519999899345);
        subsetsAtLeast_helper_uniform(2, 1, "[1, 2, 2, 4]", "RandomProvider_subsetsAtLeast_vi", 1.466173999985577);
        subsetsAtLeast_helper_uniform(5, 3, "[1, 2, 2, 4]", "RandomProvider_subsetsAtLeast_vii", 2.9999999999775233);
        subsetsAtLeast_helper_uniform(2, 1, "[2, 2, 2, 2]", "RandomProvider_subsetsAtLeast_viii", 1.000000000007918);
        subsetsAtLeast_helper_uniform(
                2,
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsetsAtLeast_ix",
                1.8170889999810345
        );
        subsetsAtLeast_helper_uniform(
                5,
                3,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsetsAtLeast_x",
                4.168420999985633
        );
        subsetsAtLeast_helper_uniform(
                32,
                8,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "RandomProvider_subsetsAtLeast_xi",
                9.41189799992237
        );
        subsetsAtLeast_helper(
                2,
                1,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsAtLeast_xii",
                1.7601419999815262
        );
        subsetsAtLeast_helper(
                5,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsAtLeast_xiii",
                3.989958999983499
        );
        subsetsAtLeast_helper(
                32,
                8,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsAtLeast_xiv",
                10.378239999978224
        );
        subsetsAtLeast_helper(2, 1, repeat(1), "RandomProvider_subsetsAtLeast_xv", 1.000000000007918);

        subsetsAtLeast_fail_helper(5, 3, Collections.emptyList());
        subsetsAtLeast_fail_helper(5, 3, Arrays.asList(1, 2, 3));
        subsetsAtLeast_fail_helper(5, 5, P.integers());
        subsetsAtLeast_fail_helper(4, 5, P.integers());
        subsetsAtLeast_fail_helper(2, 1, Collections.singletonList(null));
        subsetsAtLeast_fail_helper(2, 1, Arrays.asList(1, null, 3));
    }

    private static void stringSubsetsAtLeast_int_String_helper(
            int scale,
            int minSize,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsAtLeast(minSize, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsAtLeast_int_String_fail_helper(int scale, int minSize, @NotNull String input) {
        try {
            toList(P.withScale(scale).stringSubsetsAtLeast(minSize, input));
            fail();
        } catch (IllegalArgumentException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsAtLeast_int_String() {
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "a",
                "RandomProvider_stringSubsetsAtLeast_int_String_i",
                1.000000000007918
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "abc",
                "RandomProvider_stringSubsetsAtLeast_int_String_ii",
                1.50008299998526
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "abc",
                "RandomProvider_stringSubsetsAtLeast_int_String_iii",
                2.9999999999775233
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "abbc",
                "RandomProvider_stringSubsetsAtLeast_int_String_iv",
                1.466173999985577
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "abbc",
                "RandomProvider_stringSubsetsAtLeast_int_String_v",
                2.9999999999775233
        );
        stringSubsetsAtLeast_int_String_helper(
                2,
                1,
                "Mississippi",
                "RandomProvider_stringSubsetsAtLeast_int_String_vi",
                1.5401079999842737
        );
        stringSubsetsAtLeast_int_String_helper(
                5,
                3,
                "Mississippi",
                "RandomProvider_stringSubsetsAtLeast_int_String_vii",
                3.2285219999851744
        );

        stringSubsetsAtLeast_int_String_fail_helper(5, 3, "");
        stringSubsetsAtLeast_int_String_fail_helper(5, 5, "abc");
        stringSubsetsAtLeast_int_String_fail_helper(4, 5, "abc");
    }

    private static void stringSubsetsAtLeast_int_helper(
            int scale,
            int minSize,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsAtLeast(minSize)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsAtLeast_int_fail_helper(int scale, int minSize) {
        try {
            toList(P.withScale(scale).stringSubsetsAtLeast(minSize));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsAtLeast_int() {
        stringSubsetsAtLeast_int_helper(2, 1, "RandomProvider_stringSubsetsAtLeast_int_i", 1.9995569999798375);
        stringSubsetsAtLeast_int_helper(5, 3, "RandomProvider_stringSubsetsAtLeast_int_ii", 5.00299199999616);
        stringSubsetsAtLeast_int_helper(32, 8, "RandomProvider_stringSubsetsAtLeast_int_iii", 31.99690200002153);

        stringSubsetsAtLeast_int_fail_helper(5, 5);
        stringSubsetsAtLeast_int_fail_helper(4, 5);
    }

    private static void eithers_helper(int scale, @NotNull String as, @NotNull String bs, @NotNull String output) {
        List<Either<Integer, Integer>> sample = toList(
                take(
                        DEFAULT_SAMPLE_SIZE,
                        P.withScale(scale).eithers(
                                P.uniformSample(readIntegerListWithNulls(as)),
                                P.uniformSample(readIntegerListWithNulls(bs))
                        )
                )
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private static void eithers_fail_helper(int scale, @NotNull Iterable<Integer> as, @NotNull Iterable<Integer> bs) {
        try {
            toList(P.withScale(scale).eithers(as, bs));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testEithers() {
        eithers_helper(1, "[1]", "[2]", "RandomProvider_eithers_i");
        eithers_helper(2, "[1]", "[2]", "RandomProvider_eithers_ii");
        eithers_helper(10, "[1]", "[2]", "RandomProvider_eithers_iii");
        eithers_helper(1, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_eithers_iv");
        eithers_helper(2, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_eithers_v");
        eithers_helper(10, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_eithers_vi");

        eithers_fail_helper(1, Arrays.asList(-1, -2, -3), P.naturalIntegers());
        eithers_fail_helper(1, P.naturalIntegers(), Arrays.asList(-1, -2, -3));
        eithers_fail_helper(1, Arrays.asList(1, 2, 3), Arrays.asList(-1, -2, -3));
        eithers_fail_helper(0, P.naturalIntegers(), P.negativeIntegers());
        eithers_fail_helper(-1, P.naturalIntegers(), P.negativeIntegers());
    }

    private static void choose_helper(int scale, @NotNull String as, @NotNull String bs, @NotNull String output) {
        List<Integer> sample = toList(
                take(
                        DEFAULT_SAMPLE_SIZE,
                        P.withScale(scale).choose(
                                P.uniformSample(readIntegerListWithNulls(as)),
                                P.uniformSample(readIntegerListWithNulls(bs))
                        )
                )
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private static void choose_fail_helper(int scale, @NotNull Iterable<Integer> as, @NotNull Iterable<Integer> bs) {
        try {
            toList(P.withScale(scale).choose(as, bs));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testChoose() {
        choose_helper(1, "[1]", "[2]", "RandomProvider_choose_i");
        choose_helper(2, "[1]", "[2]", "RandomProvider_choose_ii");
        choose_helper(10, "[1]", "[2]", "RandomProvider_choose_iii");
        choose_helper(1, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_choose_iv");
        choose_helper(2, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_choose_v");
        choose_helper(10, "[1, 2, 3]", "[null, -2, -3]", "RandomProvider_choose_vi");

        choose_fail_helper(1, Arrays.asList(-1, -2, -3), P.naturalIntegers());
        choose_fail_helper(1, P.naturalIntegers(), Arrays.asList(-1, -2, -3));
        choose_fail_helper(1, Arrays.asList(1, 2, 3), Arrays.asList(-1, -2, -3));
        choose_fail_helper(0, P.naturalIntegers(), P.negativeIntegers());
        choose_fail_helper(-1, P.naturalIntegers(), P.negativeIntegers());
    }

    private static void cartesianProduct_helper(@NotNull String xss, @NotNull String output) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.cartesianProduct(readIntegerListWithNullsListsWithNulls(xss)))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    private static void cartesianProduct_fail_helper(@NotNull String xss) {
        try {
            toList(P.cartesianProduct(readIntegerListWithNullsListsWithNulls(xss)));
            fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testCartesianProduct() {
        cartesianProduct_helper("[[0]]", "RandomProvider_cartesianProduct_i");
        cartesianProduct_helper("[[null]]", "RandomProvider_cartesianProduct_ii");
        cartesianProduct_helper("[[0, 1]]", "RandomProvider_cartesianProduct_iii");
        cartesianProduct_helper("[[0, 1], [2, 3]]", "RandomProvider_cartesianProduct_iv");
        cartesianProduct_helper("[[1], [1], [1]]", "RandomProvider_cartesianProduct_v");
        cartesianProduct_helper("[[null, null, null]]", "RandomProvider_cartesianProduct_vi");
        cartesianProduct_helper("[[0, 1, 2], [-3, -4], [null, 10]]", "RandomProvider_cartesianProduct_vii");
        cartesianProduct_helper("[[0, 1], [0, 1], [0, 1]]", "RandomProvider_cartesianProduct_viii");

        cartesianProduct_fail_helper("[]");
        cartesianProduct_fail_helper("[[], [1, 2]]");
        cartesianProduct_fail_helper("[[1, 2], null]");
    }

    private static void repeatingIterables_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        aeqitLimitLog(TINY_LIMIT, map(Testing::its, P.withScale(scale).repeatingIterables(input)), output);
        P.reset();
    }

    @Test
    public void testRepeatingIterables() {
        repeatingIterables_helper(2, P.positiveIntegersGeometric(), "RandomProvider_repeatingIterables_i");
        repeatingIterables_helper(4, P.positiveIntegersGeometric(), "RandomProvider_repeatingIterables_ii");
        repeatingIterables_helper(8, P.positiveIntegersGeometric(), "RandomProvider_repeatingIterables_iii");
    }

    private static void repeatingIterablesDistinctAtLeast_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input,
            @NotNull String output
    ) {
        aeqitLimitLog(
                TINY_LIMIT,
                map(Testing::its, P.withScale(scale).repeatingIterablesDistinctAtLeast(minSize, input)),
                output
        );
        P.reset();
    }

    private static void repeatingIterablesDistinctAtLeast_fail_helper(
            int scale,
            int minSize,
            @NotNull Iterable<Integer> input
    ) {
        try {
            P.withScale(scale).repeatingIterablesDistinctAtLeast(minSize, input);
            fail();
        } catch (IllegalStateException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testRepeatingIterablesDistinctAtLeast() {
        repeatingIterablesDistinctAtLeast_helper(
                3,
                2,
                P.positiveIntegersGeometric(),
                "RandomProvider_repeatingIterablesDistinctAtLeast_i"
        );
        repeatingIterablesDistinctAtLeast_helper(
                5,
                3,
                P.positiveIntegersGeometric(),
                "RandomProvider_repeatingIterablesDistinctAtLeast_ii"
        );
        repeatingIterablesDistinctAtLeast_helper(
                32,
                5,
                P.positiveIntegersGeometric(),
                "RandomProvider_repeatingIterablesDistinctAtLeast_iii"
        );
        repeatingIterablesDistinctAtLeast_fail_helper(1, 1, P.positiveIntegers());
        repeatingIterablesDistinctAtLeast_fail_helper(1, -1, P.positiveIntegers());
    }

    private static void sublists_helper(@NotNull String input, @NotNull String output) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.sublists(readIntegerListWithNulls(input))));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    @Test
    public void testSublists() {
        sublists_helper("[]", "RandomProvider_sublists_i");
        sublists_helper("[1, 2, 3, 4]", "RandomProvider_sublists_ii");
        sublists_helper("[1, null, 3, 4]", "RandomProvider_sublists_iii");
        sublists_helper("[3, 1, 4, 1]", "RandomProvider_sublists_iv");
        sublists_helper("[1, 1, 1, 1]", "RandomProvider_sublists_v");
    }

    private static void substrings_helper(@NotNull String input, @NotNull String output) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.substrings(input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        P.reset();
    }

    @Test
    public void testSubstrings() {
        substrings_helper("", "RandomProvider_substrings_i");
        substrings_helper("abcd", "RandomProvider_substrings_ii");
        substrings_helper("aaaa", "RandomProvider_substrings_iii");
        substrings_helper("Mississippi", "RandomProvider_substrings_iv");
    }

    private static void listsWithElement_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsWithElement(x, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsWithElement_helper_uniform(
            int scale,
            @Nullable Integer x,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        listsWithElement_helper(
                scale,
                x,
                P.uniformSample(readIntegerListWithNulls(input)),
                output,
                meanSize
        );
    }

    private static void listsWithElement_fail_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input
    ) {
        try {
            toList(P.withScale(scale).listsWithElement(x, input));
            fail();
        } catch (IllegalStateException | NoSuchElementException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testListsWithElement() {
        listsWithElement_helper_uniform(3, 0, "[1, 2, 3]", "RandomProvider_listsWithElement_i", 3.001751999985313);
        listsWithElement_helper_uniform(5, -5, "[1, 2, 3]", "RandomProvider_listsWithElement_ii", 5.007290999999437);
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, 2, 3]",
                "RandomProvider_listsWithElement_iii",
                31.993837000022335
        );
        listsWithElement_helper_uniform(3, 0, "[1, null, 3]", "RandomProvider_listsWithElement_iv", 3.001751999985313);
        listsWithElement_helper_uniform(5, -5, "[1, null, 3]", "RandomProvider_listsWithElement_v", 5.007290999999437);
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, null, 3]",
                "RandomProvider_listsWithElement_vi",
                32.00126100002188
        );
        listsWithElement_helper_uniform(
                3,
                0,
                "[1, 2, 2, 4]",
                "RandomProvider_listsWithElement_vii",
                3.0022749999853073
        );
        listsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 2, 4]",
                "RandomProvider_listsWithElement_viii",
                5.005771999999611
        );
        listsWithElement_helper_uniform(
                32,
                null,
                "[1, 2, 2, 4]",
                "RandomProvider_listsWithElement_ix",
                32.023569000021205
        );
        listsWithElement_helper(
                3,
                0,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsWithElement_x",
                2.999244999985283
        );
        listsWithElement_helper(
                5,
                -5,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsWithElement_xi",
                5.003905999999416
        );
        listsWithElement_helper(
                32,
                null,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_listsWithElement_xii",
                32.001327000020574
        );
        listsWithElement_helper(3, 0, repeat(1), "RandomProvider_listsWithElement_xiii", 2.999981999985526);
        listsWithElement_helper(5, -5, repeat(1), "RandomProvider_listsWithElement_xiv", 5.0037189999994975);
        listsWithElement_helper(32, null, repeat(1), "RandomProvider_listsWithElement_xv", 31.985562000021005);

        listsWithElement_fail_helper(5, null, Collections.emptyList());
        listsWithElement_fail_helper(5, null, Arrays.asList(1, 2, 3));
        listsWithElement_fail_helper(2, null, P.integers());
    }

    private static void stringsWithChar_char_String_helper(
            int scale,
            char c,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithChar(c, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithChar_char_String_fail_helper(int scale, char c, @NotNull String input) {
        try {
            P.withScale(scale).stringsWithChar(c, input);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsWithChar_char_String() {
        stringsWithChar_char_String_helper(
                3,
                'b',
                "abcd",
                "RandomProvider_stringsWithChar_char_String_i",
                3.0011149999852065
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "abcd",
                "RandomProvider_stringsWithChar_char_String_ii",
                5.005771999999611
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "abcd",
                "RandomProvider_stringsWithChar_char_String_iii",
                32.023569000021205
        );
        stringsWithChar_char_String_helper(
                3,
                'b',
                "aaaa",
                "RandomProvider_stringsWithChar_char_String_iv",
                3.0022749999853073
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "aaaa",
                "RandomProvider_stringsWithChar_char_String_v",
                5.005771999999611
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "aaaa",
                "RandomProvider_stringsWithChar_char_String_vi",
                32.023569000021205
        );
        stringsWithChar_char_String_helper(
                3,
                'b',
                "Mississippi",
                "RandomProvider_stringsWithChar_char_String_vii",
                2.9995019999853154
        );
        stringsWithChar_char_String_helper(
                5,
                '#',
                "Mississippi",
                "RandomProvider_stringsWithChar_char_String_viii",
                5.0064659999996515
        );
        stringsWithChar_char_String_helper(
                32,
                ' ',
                "Mississippi",
                "RandomProvider_stringsWithChar_char_String_ix",
                31.994617000022732
        );

        stringsWithChar_char_String_fail_helper(2, ' ', "abc");
    }

    private static void stringsWithChar_char_helper(int scale, char c, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithChar(c)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithChar_char_fail_helper(int scale, char c) {
        try {
            P.withScale(scale).stringsWithChar(c);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringsWithChar_char() {
        stringsWithChar_char_helper(3, 'b', "RandomProvider_stringsWithChar_char_i", 3.0022749999853757);
        stringsWithChar_char_helper(5, '#', "RandomProvider_stringsWithChar_char_ii", 5.005778999999451);
        stringsWithChar_char_helper(32, ' ', "RandomProvider_stringsWithChar_char_iii", 32.02361800002122);

        stringsWithChar_char_fail_helper(2, ' ');
    }

    private static void subsetsWithElement_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).subsetsWithElement(x, input))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void subsetsWithElement_helper_uniform(
            int scale,
            @Nullable Integer x,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        subsetsWithElement_helper(scale, x, P.uniformSample(readIntegerListWithNulls(input)), output, meanSize);
    }

    private static void subsetsWithElement_fail_helper(
            int scale,
            @Nullable Integer x,
            @NotNull Iterable<Integer> input
    ) {
        try {
            toList(P.withScale(scale).subsetsWithElement(x, input));
            fail();
        } catch (NoSuchElementException | IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testSubsetsWithElement() {
        subsetsWithElement_helper_uniform(
                2,
                0,
                "[1, 2, 3]",
                "RandomProvider_subsetsWithElement_i",
                1.7510349999822503
        );
        subsetsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 3]",
                "RandomProvider_subsetsWithElement_ii",
                2.7138669999868528
        );
        subsetsWithElement_helper_uniform(
                32,
                3,
                "[1, 2, 3]",
                "RandomProvider_subsetsWithElement_iii",
                2.878531999977901
        );
        subsetsWithElement_helper_uniform(
                2,
                0,
                "[1, 2, 2, 4]",
                "RandomProvider_subsetsWithElement_iv",
                1.733663999982879
        );
        subsetsWithElement_helper_uniform(
                5,
                -5,
                "[1, 2, 2, 4]",
                "RandomProvider_subsetsWithElement_v",
                2.6677539999856954
        );
        subsetsWithElement_helper_uniform(
                32,
                3,
                "[1, 2, 2, 4]",
                "RandomProvider_subsetsWithElement_vi",
                3.711202000016531
        );
        subsetsWithElement_helper(
                2,
                0,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsWithElement_vii",
                1.8810509999810079
        );
        subsetsWithElement_helper(
                5,
                -5,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsWithElement_viii",
                3.669920999984385
        );
        subsetsWithElement_helper(
                32,
                3,
                P.withScale(4).positiveIntegersGeometric(),
                "RandomProvider_subsetsWithElement_ix",
                8.632959000000918
        );
        subsetsWithElement_helper(2, 0, repeat(1), "RandomProvider_subsetsWithElement_x", 1.5008749999899669);
        subsetsWithElement_helper(5, -5, repeat(1), "RandomProvider_subsetsWithElement_xi", 1.799806000002514);
        subsetsWithElement_helper(32, 3, repeat(1), "RandomProvider_subsetsWithElement_xii", 1.9688010000135663);

        subsetsWithElement_fail_helper(5, 0, Collections.emptyList());
        subsetsWithElement_fail_helper(5, 0, Arrays.asList(1, 2, 3));
        subsetsWithElement_fail_helper(1, 0, P.integers());
    }

    private static void stringSubsetsWithChar_char_String_helper(
            int scale,
            char c,
            @NotNull String input,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsWithChar(c, input)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsWithChar_char_String_fail_helper(int scale, char c, @NotNull String input) {
        try {
            P.withScale(scale).stringSubsetsWithChar(c, input);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsWithChar_char_String() {
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "abcd",
                "RandomProvider_subsetsWithChar_char_String_i",
                1.7505739999822374
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "abcd",
                "RandomProvider_subsetsWithChar_char_String_ii",
                3.0014489999821183
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "abcd",
                "RandomProvider_subsetsWithChar_char_String_iii",
                4.543944999926795
        );
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "aaaa",
                "RandomProvider_subsetsWithChar_char_String_iv",
                1.500442999989859
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "aaaa",
                "RandomProvider_subsetsWithChar_char_String_v",
                1.7999900000025462
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "aaaa",
                "RandomProvider_subsetsWithChar_char_String_vi",
                1.9686830000135644
        );
        stringSubsetsWithChar_char_String_helper(
                2,
                'b',
                "Mississippi",
                "RandomProvider_subsetsWithChar_char_String_vii",
                1.7701949999823314
        );
        stringSubsetsWithChar_char_String_helper(
                5,
                '#',
                "Mississippi",
                "RandomProvider_subsetsWithChar_char_String_viii",
                2.874729999983963
        );
        stringSubsetsWithChar_char_String_helper(
                32,
                ' ',
                "Mississippi",
                "RandomProvider_subsetsWithChar_char_String_ix",
                4.425015999937323
        );

        stringSubsetsWithChar_char_String_fail_helper(1, ' ', "abc");
    }

    private static void stringSubsetsWithChar_char_helper(int scale, char c, @NotNull String output, double meanSize) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringSubsetsWithChar(c)));
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringSubsetsWithChar_char_fail_helper(int scale, char c) {
        try {
            P.withScale(scale).stringSubsetsWithChar(c);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testStringSubsetsWithChar_char() {
        stringSubsetsWithChar_char_helper(2, 'b', "RandomProvider_subsetsWithChar_char_i", 2.0006209999799105);
        stringSubsetsWithChar_char_helper(5, '#', "RandomProvider_subsetsWithChar_char_ii", 5.005519000008595);
        stringSubsetsWithChar_char_helper(32, ' ', "RandomProvider_subsetsWithChar_char_iii", 32.01744900002343);

        stringSubsetsWithChar_char_fail_helper(1, ' ');
    }

    private static void listsWithSublists_helper(
            int scale,
            @NotNull Iterable<List<Integer>> sublists,
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            double meanSize
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).listsWithSublists(sublists, xs))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(List::size, sample))), meanSize);
        P.reset();
    }

    private static void listsWithSublists_fail_helper(
            int scale,
            @NotNull Iterable<List<Integer>> sublists,
            @NotNull Iterable<Integer> input
    ) {
        try {
            toList(P.withScale(scale).listsWithSublists(sublists, input));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testListsWithSublists() {
        listsWithSublists_helper(
                2,
                P.uniformSample(Collections.singletonList(Collections.emptyList())),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "RandomProvider_listsWithSublists_i",
                2.0017519999875453
        );
        listsWithSublists_helper(
                16,
                P.uniformSample(Collections.singletonList(Collections.emptyList())),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "RandomProvider_listsWithSublists_ii",
                15.983893999996807
        );
        listsWithSublists_helper(
                2,
                P.uniformSample(Collections.singletonList(Arrays.asList(1, 2, 3))),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "RandomProvider_listsWithSublists_iii",
                5.001751999985147
        );
        listsWithSublists_helper(
                16,
                P.uniformSample(Collections.singletonList(Arrays.asList(1, 2, 3))),
                P.uniformSample(Arrays.asList(4, 5, 6)),
                "RandomProvider_listsWithSublists_iv",
                18.983894000012278
        );
        listsWithSublists_helper(
                2,
                map(i -> Arrays.asList(i, i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric(),
                "RandomProvider_listsWithSublists_v",
                4.001453999968074
        );
        listsWithSublists_helper(
                16,
                map(i -> Arrays.asList(i, i), P.naturalIntegersGeometric()),
                P.naturalIntegersGeometric(),
                "RandomProvider_listsWithSublists_vi",
                17.99236900000833
        );

        listsWithSublists_fail_helper(
                1,
                P.uniformSample(Collections.singletonList(Arrays.asList(1, 2, 3))),
                P.uniformSample(Arrays.asList(4, 5, 6))
        );
    }

    private static void stringsWithSubstrings_Iterable_String_String_helper(
            int scale,
            @NotNull Iterable<String> substrings,
            @NotNull String s,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithSubstrings(substrings, s))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithSubstrings_Iterable_String_String_fail_helper(
            int scale,
            @NotNull Iterable<String> substrings,
            @NotNull String s
    ) {
        try {
            P.withScale(scale).stringsWithSubstrings(substrings, s);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void stringsWithSubstrings_Iterable_String_String() {
        stringsWithSubstrings_Iterable_String_String_helper(
                2,
                P.uniformSample(Collections.singletonList("")),
                charsToString(range('a', 'z')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_i",
                2.0001649999875575
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                16,
                P.uniformSample(Collections.singletonList("")),
                charsToString(range('a', 'z')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_ii",
                15.980978999996648
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                2,
                P.uniformSample(Collections.singletonList("cat")),
                charsToString(range('a', 'z')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_iii",
                5.000164999985253
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                16,
                P.uniformSample(Collections.singletonList("cat")),
                charsToString(range('a', 'z')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_iv",
                18.980979000012418
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                2,
                map(f -> Float.toString(f), P.floats()),
                charsToString(range('0', '9')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_v",
                14.31768299994003
        );
        stringsWithSubstrings_Iterable_String_String_helper(
                16,
                map(f -> Float.toString(f), P.floats()),
                charsToString(range('0', '9')),
                "RandomProvider_stringsWithSubstrings_Iterable_String_String_vi",
                28.306307000011344
        );

        stringsWithSubstrings_Iterable_String_String_fail_helper(
                1,
                P.uniformSample(Collections.singletonList("cat")),
                charsToString(range('a', 'z'))
        );
    }

    private static void stringsWithSubstrings_Iterable_String_helper(
            int scale,
            @NotNull Iterable<String> substrings,
            @NotNull String output,
            double meanSize
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.withScale(scale).stringsWithSubstrings(substrings))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        aeqMapLog(topSampleCount(DEFAULT_TOP_COUNT, sample), output);
        aeq(meanOfIntegers(toList(map(String::length, sample))), meanSize);
        P.reset();
    }

    private static void stringsWithSubstrings_Iterable_String_fail_helper(
            int scale,
            @NotNull Iterable<String> substrings
    ) {
        try {
            P.withScale(scale).stringsWithSubstrings(substrings);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void stringsWithSubstrings_Iterable_String() {
        stringsWithSubstrings_Iterable_String_helper(
                2,
                P.uniformSample(Collections.singletonList("")),
                "RandomProvider_stringsWithSubstrings_Iterable_String_i",
                2.002274999987558
        );
        stringsWithSubstrings_Iterable_String_helper(
                16,
                P.uniformSample(Collections.singletonList("")),
                "RandomProvider_stringsWithSubstrings_Iterable_String_ii",
                15.98473899999724
        );
        stringsWithSubstrings_Iterable_String_helper(
                2,
                P.uniformSample(Collections.singletonList("cat")),
                "RandomProvider_stringsWithSubstrings_Iterable_String_iii",
                5.002274999985167
        );
        stringsWithSubstrings_Iterable_String_helper(
                16,
                P.uniformSample(Collections.singletonList("cat")),
                "RandomProvider_stringsWithSubstrings_Iterable_String_iv",
                18.984739000011526
        );
        stringsWithSubstrings_Iterable_String_helper(
                2,
                map(f -> Float.toString(f), P.floats()),
                "RandomProvider_stringsWithSubstrings_Iterable_String_v",
                14.31644599993982
        );
        stringsWithSubstrings_Iterable_String_helper(
                16,
                map(f -> Float.toString(f), P.floats()),
                "RandomProvider_stringsWithSubstrings_Iterable_String_vi",
                28.30002600001155
        );
        stringsWithSubstrings_Iterable_String_fail_helper(1, P.uniformSample(Collections.singletonList("cat")));
    }

    private static void maps_helper(@NotNull String keys, @NotNull Iterable<Integer> values, @NotNull String output) {
        List<Map<Integer, Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, P.maps(readIntegerListWithNulls(keys), values))
        );
        aeqitLimitLog(TINY_LIMIT, sample, output);
        P.reset();
    }

    @Test
    public void testMaps() {
        maps_helper("[5]", P.naturalIntegersGeometric(), "RandomProvider_maps_i");
        maps_helper("[1, 2, 3]", P.naturalIntegersGeometric(), "RandomProvider_maps_ii");
        maps_helper("[1, null, 3]", P.naturalIntegersGeometric(), "RandomProvider_maps_iii");
    }

    private static void randomProvidersFixedScales_helper(
            int scale,
            int secondaryScale,
            int tertiaryScale,
            @NotNull String output
    ) {
        aeqitLimitLog(TINY_LIMIT, P.randomProvidersFixedScales(scale, secondaryScale, tertiaryScale), output);
        P.reset();
    }

    @Test
    public void testRandomProvidersFixedScales() {
        randomProvidersFixedScales_helper(8, 32, 2, "RandomProvider_randomProvidersFixedScales_i");
        randomProvidersFixedScales_helper(0, 0, 0, "RandomProvider_randomProvidersFixedScales_ii");
        randomProvidersFixedScales_helper(-5, -10, -1, "RandomProvider_randomProvidersFixedScales_iii");
    }

    @Test
    public void testRandomProvidersDefault() {
        aeqitLimitLog(TINY_LIMIT, P.randomProvidersDefault(), "RandomProvider_randomProvidersDefault");
    }

    private static void randomProvidersDefaultSecondaryAndTertiaryScale_helper(int scale, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.withScale(scale).randomProvidersDefaultSecondaryAndTertiaryScale(), output);
    }

    private static void randomProvidersDefaultSecondaryAndTertiaryScale_fail_helper(int scale) {
        try {
            P.withScale(scale).randomProvidersDefaultSecondaryAndTertiaryScale();
            fail();
        } catch (IllegalStateException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testRandomProvidersDefaultSecondaryAndTertiaryScale() {
        randomProvidersDefaultSecondaryAndTertiaryScale_helper(1,
                "RandomProvider_randomProvidersDefaultSecondaryAndTertiaryScale_i");
        randomProvidersDefaultSecondaryAndTertiaryScale_helper(8,
                "RandomProvider_randomProvidersDefaultSecondaryAndTertiaryScale_ii");
        randomProvidersDefaultSecondaryAndTertiaryScale_fail_helper(0);
        randomProvidersDefaultSecondaryAndTertiaryScale_fail_helper(-1);
    }

    private static void randomProvidersDefaultTertiaryScale_helper(
            int scale,
            int secondaryScale,
            @NotNull String output
    ) {
        aeqitLimitLog(
                TINY_LIMIT,
                P.withScale(scale).withSecondaryScale(secondaryScale).randomProvidersDefaultTertiaryScale(),
                output
        );
    }

    private static void randomProvidersDefaultTertiaryScale_fail_helper(int scale, int secondaryScale) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).randomProvidersDefaultTertiaryScale();
            fail();
        } catch (IllegalStateException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testRandomProvidersDefaultTertiaryScale() {
        randomProvidersDefaultTertiaryScale_helper(1, 10, "RandomProvider_randomProvidersDefaultTertiaryScale_i");
        randomProvidersDefaultTertiaryScale_helper(8, 5, "RandomProvider_randomProvidersDefaultTertiaryScale_ii");
        randomProvidersDefaultTertiaryScale_fail_helper(0, -5);
        randomProvidersDefaultTertiaryScale_fail_helper(-1, 5);
    }

    private static void randomProviders_helper(int scale, @NotNull String output) {
        aeqitLimitLog(TINY_LIMIT, P.withScale(scale).randomProviders(), output);
    }

    private static void randomProviders_fail_helper(int scale) {
        try {
            P.withScale(scale).randomProviders();
            fail();
        } catch (IllegalStateException ignored) {}
        finally{
            P.reset();
        }
    }

    @Test
    public void testRandomProviders() {
        randomProviders_helper(1, "RandomProvider_randomProviders_i");
        randomProviders_helper(8, "RandomProvider_randomProviders_ii");
        randomProviders_fail_helper(0);
        randomProviders_fail_helper(-1);
    }

    @Test
    public void testEquals() {
        List<RandomProvider> xs = Arrays.asList(
                RandomProvider.example(),
                Q.withScale(3).withSecondaryScale(0).withTertiaryScale(-1),
                R.withScale(0).withSecondaryScale(10).withTertiaryScale(5)
        );
        List<RandomProvider> ys = Arrays.asList(
                RandomProvider.example(),
                Q.withScale(3).withSecondaryScale(0).withTertiaryScale(-1),
                R.withScale(0).withSecondaryScale(10).withTertiaryScale(5)
        );
        testEqualsHelper(xs, ys);
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(P.equals("hello"));
    }

    @Test
    public void testHashCode() {
        aeq(P.hashCode(), -1291053760);
        aeq(Q.withScale(3).withSecondaryScale(0).withTertiaryScale(-1).hashCode(), 1656156693);
        aeq(R.withScale(0).withSecondaryScale(10).withTertiaryScale(5).hashCode(), -453720855);
    }

    @Test
    public void testToString() {
        aeq(P, "RandomProvider[@-8800290164235921060, 32, 8, 2]");
        aeq(
                Q.withScale(3).withSecondaryScale(0).withTertiaryScale(-1),
                "RandomProvider[@-7948823947390831374, 3, 0, -1]"
        );
        aeq(
                R.withScale(0).withSecondaryScale(10).withTertiaryScale(5),
                "RandomProvider[@2449928962525148503, 0, 10, 5]"
        );
    }

    private static double meanOfIntegers(@NotNull List<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfBigIntegers(@NotNull List<BigInteger> xs) {
        return sumDouble(map(i -> i.doubleValue() / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<List<Integer>> readIntegerListWithNullsListsWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers.readListWithNulls(Readers::readInteger)).apply(s).get();
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
