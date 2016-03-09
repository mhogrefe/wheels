package mho.wheels.iterables.randomProvider;

import mho.wheels.io.Readers;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.random.IsaacPRNG;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class BasicTest {
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
        aeqitLimit(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper((byte) 0,
                "[-87, -72, -25, -107, -11, -107, -42, -65, -66, -11, -2, -100, -86, -49, -124, -9, -65, -2, -83," +
                " -14, ...]");
        rangeDown_byte_helper((byte) (1 << 6),
                "[-87, 3, 53, 63, -72, 35, -25, -107, -11, -107, 49, 38, -42, -65, -66, 53, 63, -11, -2, -100, ...]");
        rangeDown_byte_helper((byte) (-1 << 6),
                "[-87, -125, -75, -65, -72, -93, -107, -107, -79, -90, -65, -66, -75, -65, -100, -86, -124, -65," +
                " -85, -83, ...]");
        rangeDown_byte_helper(Byte.MAX_VALUE,
                "[-87, 73, 3, 114, 53, 63, -72, 101, 125, 35, 127, 81, -25, -107, 115, -11, 120, -107, 49, 38, ...]");
        rangeDown_byte_helper(
                Byte.MIN_VALUE,
                "[-128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128," +
                " -128, -128, -128, -128, ...]");
    }

    private static void rangeDown_short_helper(short a, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper((short) 0,
                "[-10711, -21006, -31819, -15681, -23240, -19995, -26637, -13361, -31775, -170, -26148, -11713," +
                " -20162, -13131, -1089, -12171, -8066, -25828, -24278, -17073, ...]");
        rangeDown_short_helper((short) (1 << 14),
                "[-10711, 6531, -21006, -31819, -15681, -23240, -19995, 6909, 163, 3431, -26637, 12024, 15025," +
                " -13361, -31775, -170, -26148, -11713, -20162, -13131, ...]");
        rangeDown_short_helper((short) (-1 << 14),
                "[-26237, -21006, -31819, -23240, -19995, -25859, -32605, -29337, -26637, -20744, -17743, -31775," +
                " -26148, -20162, -25828, -24278, -17073, -23559, -17801, -21185, ...]");
        rangeDown_short_helper(Short.MAX_VALUE,
                "[-10711, 20937, 6531, -21006, -31819, -15681, -23240, -19995, 6909, 163, 30463, 31953, 3431, 25109," +
                " -26637, 23925, 12024, 23829, 15025, 31910, ...]");
        rangeDown_short_helper(Short.MIN_VALUE,
                "[-32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768," +
                " -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, ...]");
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(0,
                "[-1004482103, -150306653, -658504735, -291825188, -1552326530, -1038540502, -1965701250," +
                " -1186791891, -1728239037, -18381468, -137739712, -604834249, -1131859022, -1686158854," +
                " -1782600976, -2111534694, -1846406610, -553610990, -96510935, -2032484754, ...]");
        rangeDown_int_helper(1 << 30,
                "[-1004482103, 970337778, 681591487, 136131045, -150306653, 534895893, -658504735, -291825188," +
                " 739062078, -1552326530, -1038540502, 985283191, -1965701250, -1186791891, 35433513, -1728239037," +
                " 691399237, -18381468, -137739712, -604834249, ...]");
        rangeDown_int_helper(-1 << 30,
                "[-1177145870, -1465892161, -2011352603, -1612587755, -1408421570, -1552326530, -1162200457," +
                " -1965701250, -1186791891, -2112050135, -1728239037, -1456084411, -1288200699, -1131859022," +
                " -1655648634, -2073512899, -1686158854, -1782600976, -2111534694, -1846406610, ...]");
        rangeDown_int_helper(Integer.MAX_VALUE,
                "[-1004482103, 970337778, 681591487, 136131045, -150306653, 2040790225, 1941234197, 1951980917," +
                " 534895893, 1315765414, -658504735, -291825188, 739062078, 2008775615, -1552326530, -1038540502," +
                " 1275438073, 985283191, -1965701250, -1186791891, ...]");
        rangeDown_int_helper(Integer.MIN_VALUE,
                "[-2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648," +
                " -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648," +
                " -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, ...]");
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(0L,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, -8953058930686899236," +
                " -3259812015710032715, -307380066314523266, -7262442345795599793, -5933783335123481941," +
                " -2942296318021176509, -6116173926626075902, -1045000064965076200, -6331375924936837000," +
                " -7858199626387741934, -750497281407653010, -4964572946333319706, -3265594823497196973," +
                " -7169158286100765709, -3899242950132782503, -354726065181537090, -8326391862079061231, ...]");
        rangeDown_long_helper(1L << 62,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, 2946691002601937," +
                " 938051737769800691, 4191131234584022735, -8953058930686899236, -3259812015710032715," +
                " -307380066314523266, -7262442345795599793, 2523647485907371127, -5933783335123481941," +
                " -2942296318021176509, 425447389768546624, -6116173926626075902, 2177406786277490478," +
                " -1045000064965076200, 658654575245458808, -6331375924936837000, -7858199626387741934, ...]");
        rangeDown_long_helper(-1L << 62,
                "[-6244707352066318268, -8963960367505090887, -5403403905557946200, -5177455240371167864," +
                " -5640149525592249138, -4660448456132719188, -5382705448837465097, -7198099522187093694," +
                " -8899164521550398790, -4670893656599177974, -6089294534305496519, -8650775946964755326," +
                " -7145123307227501859, -7605339026464506600, -6513958261454878089, -9034634951682803789," +
                " -7138643007725401796, -7486951269179234622, -7852292981010661281, -8935306705831985167, ...]");
        rangeDown_long_helper(Long.MAX_VALUE,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, 2946691002601937," +
                " 938051737769800691, 6726395392388302357, 4191131234584022735, -8953058930686899236," +
                " -3259812015710032715, -307380066314523266, -7262442345795599793, 2523647485907371127," +
                " -5933783335123481941, 9097897703523752562, 8234018459023606428, -2942296318021176509," +
                " 5939553317435058514, 425447389768546624, -6116173926626075902, 2177406786277490478, ...]");
        rangeDown_long_helper(Long.MIN_VALUE,
                "[-9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808, ...]");
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        aeqcs(P.rangeDown(a), output);
        P.reset();
    }

    @Test
    public void testRangeDown_char() {
        rangeDown_char_helper(
                '\0',
                "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0" +
                "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0" +
                "\0\0"
        );
        rangeDown_char_helper(
                'a',
                ")I\u00035?8#Q\25\u00151&OaV\\?>5?\34*O\4?+-B\7)\34\26CLER%@7\2\5.\u001c2S\6=V\30\nN\32X^$.\23\22\3K" +
                "10);\u001c2EZF\17I`5\23OSS\5\3\5\u000b2Y\\K;\1CQ7&W\5>U7\21(Y\2"
        );
        rangeDown_char_helper(
                'Ш',
                "ǉƃεʿǥ\u02fd£ȕʱϏϡǜȿľοu~\u031cĪϹɷȇЖɃɌɅ\u0352ĥɀ\u0363ϯβœźŖǺĘǽˬǎ\u02f0ŨƚϘÞxȮĒưʻ²\u02da\u03605ȓ\u0353" +
                "\u02d3\5ʅϳƋ2\u0359\u02ef\u036f\u033b|ɑʷ¦ϲ¾·\21ΨÙɨŔ\u0329ο\u0306\u0092\u0339σŚ\u036bBɗŪŤͽЋɵÊ\u037eʡɪ" +
                "\35\u0366ț"
        );
        rangeDown_char_helper(
                '\uffff',
                "嘩퇉馃\u2df2ε䊿\u2538\u31e5髽肣\uf6ffﳑ赧\ue215\u17f3\udd75껸\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ䲵箿偵恾ᬜK㵏ꏹ缄㩷" +
                "ⴿ읾纫\ufe2d㗂䝲\uf207갩힜坤琖\u2a43퉌\uea45\ue352蕤餥䉀\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd" +
                "\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f"
        );
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.range((byte) a, (byte) b), output);
        P.reset();
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(
                10,
                20,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_byte_byte_helper(
                10,
                10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_byte_byte_helper((byte) 10, (byte) 9, "[]");
        range_byte_byte_helper(
                -20,
                -10,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_byte_byte_helper(
                -20,
                -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_byte_byte_helper((byte) -20, (byte) -21, "[]");
        range_byte_byte_helper(0, 0, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]");
        range_byte_byte_helper(0, 10, "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]");
        range_byte_byte_helper(-5, 0, "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]");
        range_byte_byte_helper(-5, 10, "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]");
        range_byte_byte_helper(
                -10,
                5,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_byte_byte_helper(5, -10, "[]");
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.range((short) a, (short) b), output);
        P.reset();
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(
                10,
                20,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_short_short_helper(
                10,
                10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_short_short_helper((short) 10, (short) 9, "[]");
        range_short_short_helper(
                -20,
                -10,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_short_short_helper(
                -20,
                -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_short_short_helper((short) -20, (short) -21, "[]");
        range_short_short_helper(0, 0, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]");
        range_short_short_helper(0, 10, "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]");
        range_short_short_helper(
                -5,
                0,
                "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]"
        );
        range_short_short_helper(-5, 10, "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]");
        range_short_short_helper(
                -10,
                5,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_short_short_helper(5, -10, "[]");
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.range(a, b), output);
        P.reset();
    }

    @Test
    public void testRange_int_int() {
        range_int_int_helper(
                10,
                20,
                "[19, 12, 15, 13, 11, 15, 15, 15, 16, 11, 20, 19, 17, 12, 19, 14, 13, 15, 14, 10, ...]"
        );
        range_int_int_helper(
                10,
                10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_int_int_helper(10, 9, "[]");
        range_int_int_helper(
                -20,
                -10,
                "[-11, -18, -15, -17, -19, -15, -15, -15, -14, -19, -10, -11, -13, -18, -11, -16, -17, -15, -16," +
                " -20, ...]"
        );
        range_int_int_helper(
                -20,
                -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_int_int_helper(-20, -21, "[]");
        range_int_int_helper(0, 0, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]");
        range_int_int_helper(0, 10, "[9, 2, 5, 3, 1, 5, 5, 5, 6, 1, 10, 9, 7, 2, 9, 4, 3, 5, 4, 0, ...]");
        range_int_int_helper(-5, 0, "[-4, -3, 0, -2, -4, 0, 0, 0, -4, -1, -3, -4, 0, -3, -4, -1, -2, 0, -1, -5, ...]");
        range_int_int_helper(-5, 10, "[4, -3, 10, 0, -2, -4, 0, 0, 0, 1, -4, 7, 9, 10, 9, 5, 4, 2, 9, 8, ...]");
        range_int_int_helper(-10, 5, "[-1, -8, 5, -5, -7, -9, -5, -5, -5, -4, -9, 2, 4, 5, 4, 0, -1, -3, 4, 3, ...]");
        range_int_int_helper(5, -10, "[]");
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        P.reset();
        aeqitLimit(TINY_LIMIT, P.range(a, b), output);
    }

    @Test
    public void testRange_long_long() {
        range_long_long_helper(
                10L,
                20L,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_long_long_helper(
                10L,
                10L,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_long_long_helper(10L, 9L, "[]");
        range_long_long_helper(
                -20L,
                -10L,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_long_long_helper(
                -20L,
                -20L,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_long_long_helper(-20L, -21L, "[]");
        range_long_long_helper(0L, 0L, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]");
        range_long_long_helper(0L, 10L, "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]");
        range_long_long_helper(
                -5L,
                0L,
                "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]"
        );
        range_long_long_helper(-5L, 10L, "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]");
        range_long_long_helper(
                -10L,
                5L,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_long_long_helper(5L, -10L, "[]");
    }

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
        P.reset();
    }

    @Test
    public void testRange_BigInteger_BigInteger() {
        range_BigInteger_BigInteger_helper(
                10,
                20,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_BigInteger_BigInteger_helper(
                10,
                10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_BigInteger_BigInteger_helper(10, 9, "[]");
        range_BigInteger_BigInteger_helper(
                -20,
                -10,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_BigInteger_BigInteger_helper(
                -20,
                -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_BigInteger_BigInteger_helper(-20, -21, "[]");
        range_BigInteger_BigInteger_helper(0, 0, "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]");
        range_BigInteger_BigInteger_helper(0, 10, "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]");
        range_BigInteger_BigInteger_helper(
                -5,
                0,
                "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]"
        );
        range_BigInteger_BigInteger_helper(
                -5,
                10,
                "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]"
        );
        range_BigInteger_BigInteger_helper(
                -10,
                5,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_BigInteger_BigInteger_helper(5, -10, "[]");
    }

    private static void range_char_char_helper(char a, char b, @NotNull String output) {
        aeqcs(P.range(a, b), output);
        P.reset();
    }

    @Test
    public void testRange_char_char() {
        range_char_char_helper(
                'a',
                'z',
                "jjdsvyfdrhvtvyvrgpbwvvkpzexlncshjewdmfsefadxcfpostgwymkoqiyyeyotsdplrqjvsofgpjgavgtpttfdwftlszplpbdr"
        );
        range_char_char_helper(
                'a',
                'a',
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );
        range_char_char_helper(
                '!',
                '9',
                "**$369&$2(646962'0\"766+0%8,.#3(*%7$-&3%&!$8#&0/34'79-+/1)99%9/43$0,21*63/&'0*'!6'4044&$7&4,30,0\"$" +
                "28'"
        );
        range_char_char_helper('a', 'A', "");
    }

    private static void geometricHelper(
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(sample), sampleMean);
    }

    private static void positiveIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).positiveIntegersGeometric(), output, topSampleCount, sampleMean);
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
        positiveIntegersGeometric_helper(
                2,
                "[4, 3, 10, 3, 3, 1, 2, 4, 1, 1, 3, 3, 3, 1, 3, 1, 1, 2, 2, 1, ...]",
                "{1=499125, 2=250897, 3=124849, 4=62518, 5=31407, 6=15634, 7=7825, 8=3926, 9=1896, 10=956}",
                2.0008359999800347
        );
        positiveIntegersGeometric_helper(
                3,
                "[5, 6, 6, 5, 3, 6, 1, 2, 3, 2, 4, 7, 2, 3, 1, 2, 1, 2, 1, 4, ...]",
                "{1=333813, 2=221150, 3=148025, 4=98992, 5=66270, 6=43747, 7=29389, 8=19567, 9=12958, 10=8571}",
                3.002096999989928
        );
        positiveIntegersGeometric_helper(
                4,
                "[7, 10, 7, 7, 4, 10, 1, 3, 3, 2, 7, 8, 2, 3, 1, 2, 1, 2, 1, 8, ...]",
                "{1=250407, 2=187060, 3=139994, 4=105560, 5=79154, 6=58963, 7=44920, 8=33524, 9=25356, 10=18834}",
                4.0033679999901475
        );
        positiveIntegersGeometric_helper(
                5,
                "[5, 4, 18, 8, 3, 1, 2, 1, 2, 6, 7, 18, 6, 3, 8, 15, 1, 9, 2, 5, ...]",
                "{1=200194, 2=160489, 3=127708, 4=101606, 5=82008, 6=65900, 7=52157, 8=41827, 9=33413, 10=26877}",
                5.004360000008482
        );
        positiveIntegersGeometric_helper(
                10,
                "[36, 10, 10, 8, 32, 21, 2, 6, 1, 18, 19, 1, 11, 9, 4, 4, 1, 2, 4, 6, ...]",
                "{1=99758, 2=90376, 3=81079, 4=73085, 5=65513, 6=59012, 7=53321, 8=47369, 9=43229, 10=38339}",
                9.996188000005418
        );
        positiveIntegersGeometric_helper(
                100,
                "[147, 1, 65, 34, 32, 144, 35, 117, 27, 85, 9, 63, 11, 16, 1, 136, 35, 290, 126, 65, ...]",
                "{1=10012, 2=9821, 3=9817, 5=9658, 4=9642, 6=9476, 7=9451, 8=9361, 10=9177, 11=9064}",
                99.9640719999968
        );
        positiveIntegersGeometric_fail_helper(1);
        positiveIntegersGeometric_fail_helper(0);
        positiveIntegersGeometric_fail_helper(-1);
    }

    private static void negativeIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).negativeIntegersGeometric(), output, topSampleCount, sampleMean);
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
        negativeIntegersGeometric_helper(
                2,
                "[-4, -3, -10, -3, -3, -1, -2, -4, -1, -1, -3, -3, -3, -1, -3, -1, -1, -2, -2, -1, ...]",
                "{-1=499125, -2=250897, -3=124849, -4=62518, -5=31407, -6=15634, -7=7825, -8=3926, -9=1896, -10=956}",
                -2.0008359999800347
        );
        negativeIntegersGeometric_helper(
                3,
                "[-5, -6, -6, -5, -3, -6, -1, -2, -3, -2, -4, -7, -2, -3, -1, -2, -1, -2, -1, -4, ...]",
                "{-1=333813, -2=221150, -3=148025, -4=98992, -5=66270, -6=43747, -7=29389, -8=19567, -9=12958," +
                " -10=8571}",
                -3.002096999989928
        );
        negativeIntegersGeometric_helper(
                4,
                "[-7, -10, -7, -7, -4, -10, -1, -3, -3, -2, -7, -8, -2, -3, -1, -2, -1, -2, -1, -8, ...]",
                "{-1=250407, -2=187060, -3=139994, -4=105560, -5=79154, -6=58963, -7=44920, -8=33524, -9=25356," +
                " -10=18834}",
                -4.0033679999901475
        );
        negativeIntegersGeometric_helper(
                5,
                "[-5, -4, -18, -8, -3, -1, -2, -1, -2, -6, -7, -18, -6, -3, -8, -15, -1, -9, -2, -5, ...]",
                "{-1=200194, -2=160489, -3=127708, -4=101606, -5=82008, -6=65900, -7=52157, -8=41827, -9=33413," +
                " -10=26877}",
                -5.004360000008482
        );
        negativeIntegersGeometric_helper(
                10,
                "[-36, -10, -10, -8, -32, -21, -2, -6, -1, -18, -19, -1, -11, -9, -4, -4, -1, -2, -4, -6, ...]",
                "{-1=99758, -2=90376, -3=81079, -4=73085, -5=65513, -6=59012, -7=53321, -8=47369, -9=43229," +
                " -10=38339}",
                -9.996188000005418
        );
        negativeIntegersGeometric_helper(
                100,
                "[-147, -1, -65, -34, -32, -144, -35, -117, -27, -85, -9, -63, -11, -16, -1, -136, -35, -290, -126," +
                " -65, ...]",
                "{-1=10012, -2=9821, -3=9817, -5=9658, -4=9642, -6=9476, -7=9451, -8=9361, -10=9177, -11=9064}",
                -99.9640719999968
        );
        negativeIntegersGeometric_fail_helper(1);
        negativeIntegersGeometric_fail_helper(0);
        negativeIntegersGeometric_fail_helper(-1);
    }

    private static void naturalIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).naturalIntegersGeometric(), output, topSampleCount, sampleMean);
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
        naturalIntegersGeometric_helper(
                1,
                "[3, 2, 9, 2, 2, 0, 1, 3, 0, 0, 2, 2, 2, 0, 2, 0, 0, 1, 1, 0, ...]",
                "{0=499125, 1=250897, 2=124849, 3=62518, 4=31407, 5=15634, 6=7825, 7=3926, 8=1896, 9=956}",
                1.0008359999977228
        );
        naturalIntegersGeometric_helper(
                2,
                "[4, 5, 5, 4, 2, 5, 0, 1, 2, 1, 3, 6, 1, 2, 0, 1, 0, 1, 0, 3, ...]",
                "{0=333813, 1=221150, 2=148025, 3=98992, 4=66270, 5=43747, 6=29389, 7=19567, 8=12958, 9=8571}",
                2.0020969999891216
        );
        naturalIntegersGeometric_helper(
                3,
                "[6, 9, 6, 6, 3, 9, 0, 2, 2, 1, 6, 7, 1, 2, 0, 1, 0, 1, 0, 7, ...]",
                "{0=250407, 1=187060, 2=139994, 3=105560, 4=79154, 5=58963, 6=44920, 7=33524, 8=25356, 9=18834}",
                3.003367999991497
        );
        naturalIntegersGeometric_helper(
                4,
                "[4, 3, 17, 7, 2, 0, 1, 0, 1, 5, 6, 17, 5, 2, 7, 14, 0, 8, 1, 4, ...]",
                "{0=200194, 1=160489, 2=127708, 3=101606, 4=82008, 5=65900, 6=52157, 7=41827, 8=33413, 9=26877}",
                4.004359999991779
        );
        naturalIntegersGeometric_helper(
                5,
                "[5, 7, 23, 9, 3, 0, 1, 0, 1, 5, 8, 22, 5, 2, 7, 18, 0, 10, 1, 5, ...]",
                "{0=166887, 1=139197, 2=115545, 3=96038, 4=80312, 5=66813, 6=55942, 7=46416, 8=38391, 9=32378}",
                5.005650000005037
        );
        naturalIntegersGeometric_helper(
                10,
                "[36, 12, 10, 8, 32, 24, 1, 7, 0, 17, 20, 0, 10, 8, 3, 4, 0, 1, 3, 6, ...]",
                "{0=90519, 1=82798, 2=75630, 3=68355, 4=62062, 5=56573, 6=51318, 7=46453, 8=42422, 9=38243}",
                9.996028000004106
        );
        naturalIntegersGeometric_helper(
                100,
                "[149, 0, 65, 33, 31, 144, 34, 116, 26, 84, 8, 63, 10, 15, 0, 137, 36, 289, 126, 65, ...]",
                "{0=9916, 2=9740, 1=9715, 4=9630, 3=9504, 5=9377, 6=9328, 7=9252, 9=9073, 8=9025}",
                99.96387799999758
        );
        naturalIntegersGeometric_fail_helper(0);
        naturalIntegersGeometric_fail_helper(-1);
        naturalIntegersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void nonzeroIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        List<Integer> xs = toList(take(DEFAULT_SAMPLE_SIZE, P.withScale(mean).nonzeroIntegersGeometric()));
        geometricHelper(xs, output, topSampleCount, sampleMean);
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
                "[4, 2, 9, 2, -2, 2, -3, 1, 2, 2, -2, -3, 1, 1, -1, 2, 2, -2, 1, -1, ...]",
                "{1=250216, -1=249853, 2=125042, -2=125020, 3=62554, -3=62266, -4=31431, 4=31320, 5=15667, -5=15448}",
                0.0034480000000000253,
                1.9994539999798795
        );
        nonzeroIntegersGeometric_helper(
                3,
                "[5, 5, 5, -5, 2, -6, 2, 2, 1, -4, 6, -1, -2, -2, -2, 4, -2, 7, 7, 2, ...]",
                "{1=166701, -1=166029, -2=111049, 2=110952, -3=74329, 3=74260, 4=49329, -4=49314, 5=33182, -5=32740}",
                -0.0012719999999999216,
                3.00329999998982
        );
        nonzeroIntegersGeometric_helper(
                4,
                "[7, 9, 6, -6, 3, -9, 3, 2, 1, -6, 7, -1, -2, -2, -2, 8, -4, 8, 14, 5, ...]",
                "{1=125241, -1=124537, -2=93626, 2=93467, -3=70571, 3=70056, 4=53060, -4=52604, -5=39246, 5=39217}",
                -8.369999999999692E-4,
                4.00555699999019
        );
        nonzeroIntegersGeometric_helper(
                5,
                "[5, 4, 18, 7, -3, -2, -2, 6, 6, 18, -5, -3, 8, -14, -1, -8, 1, -4, -7, -1, ...]",
                "{1=100071, -1=99831, -2=80195, 2=79602, -3=64039, 3=63577, 4=51648, -4=50922, 5=41189, -5=40794}",
                0.004184999999999686,
                5.006127000008418
        );
        nonzeroIntegersGeometric_helper(
                10,
                "[36, -9, 9, 7, -31, -20, -1, -6, 1, -18, -19, 1, -10, -8, -3, -4, -2, 4, -5, -12, ...]",
                "{1=49977, -1=49798, -2=45286, 2=45037, 3=40770, -3=40176, 4=36548, -4=36321, 5=33048, -5=32530}",
                0.011239999999999537,
                9.999258000005128
        );
        nonzeroIntegersGeometric_helper(
                100,
                "[-147, 1, -64, -34, 31, -143, -35, -116, 26, 84, 8, 62, -10, -15, -136, 34, 289, -126, 64, -89, ...]",
                "{-1=4973, 3=4970, 2=4962, -2=4930, 1=4908, 4=4872, -4=4839, -3=4837, 6=4820, -5=4817}",
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
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = take(DEFAULT_SAMPLE_SIZE, P.withScale(mean).integersGeometric());
        geometricHelper(xs, output, topSampleCount, sampleMean);
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
        integersGeometric_helper(
                1,
                "[3, 1, 8, 1, -1, 1, -2, 0, 1, 1, -1, -2, 0, 0, 0, 1, 1, -1, 0, 0, ...]",
                "{0=500069, 1=125042, -1=125020, 2=62554, -2=62266, -3=31431, 3=31320, 4=15667, -4=15448, -5=7835}",
                0.0025060000000000906,
                1.0003039999976984
        );
        integersGeometric_helper(
                2,
                "[4, 4, 4, -4, 1, -5, 1, 1, 0, -3, 5, 0, -1, -1, -1, 3, -1, 6, 6, 1, ...]",
                "{0=332730, -1=111049, 1=110952, -2=74329, 2=74260, 3=49329, -3=49314, 4=33182, -4=32740, 5=22195}",
                -0.0018900000000000037,
                2.002686999989137
        );
        integersGeometric_helper(
                3,
                "[6, 8, 5, -5, 2, -8, 2, 1, 0, -5, 6, 0, -1, -1, -1, 7, -3, 7, 13, 4, ...]",
                "{0=249778, -1=93626, 1=93467, -2=70571, 2=70056, 3=53060, -3=52604, -4=39246, 4=39217, 5=29959}",
                -0.0014549999999999997,
                3.00388599999155
        );
        integersGeometric_helper(
                4,
                "[4, 3, 17, 6, -2, -1, -1, 5, 5, 17, -4, -2, 7, -13, 0, -7, 0, -3, -6, 0, ...]",
                "{0=199902, -1=80195, 1=79602, -2=64039, 2=63577, 3=51648, -3=50922, 4=41189, -4=40794, 5=33021}",
                0.0036169999999999917,
                3.9981839999915647
        );
        integersGeometric_helper(
                5,
                "[5, 6, 22, 8, -2, -1, -1, 5, 7, 21, -4, -2, 7, -17, 0, -9, 0, -4, -7, 0, ...]",
                "{0=166687, -1=69595, 1=69045, -2=57871, 2=57477, 3=48432, -3=48158, 4=40223, -4=40079, 5=33890}",
                0.003408000000000304,
                4.9993930000049955
        );
        integersGeometric_helper(
                10,
                "[36, -11, 9, 7, -31, -23, 0, -6, 0, -17, -19, 0, -9, -7, -2, -3, -1, 3, -5, -12, ...]",
                "{0=90357, -1=41678, 1=41612, 2=37671, -2=37332, 3=34239, -3=34123, 4=31404, -4=30761, -5=28276}",
                0.010864000000000018,
                9.983633000004563
        );
        integersGeometric_helper(
                100,
                "[-149, 0, -64, -33, 30, -143, -34, -115, 25, 83, 7, 62, -9, -14, -137, 35, 288, -126, 64, -89, ...]",
                "{0=9763, 1=4918, 2=4912, -1=4901, 3=4853, -3=4803, -4=4799, 5=4773, -2=4772, 4=4712}",
                -0.1466660000000059,
                99.86606600000295
        );
        integersGeometric_fail_helper(0);
        integersGeometric_fail_helper(-1);
        integersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void rangeUpGeometric_helper(
            int mean,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).rangeUpGeometric(a), output, topSampleCount, sampleMean);
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
        rangeUpGeometric_helper(
                3,
                2,
                "[5, 4, 11, 4, 4, 2, 3, 5, 2, 2, 4, 4, 4, 2, 4, 2, 2, 3, 3, 2, ...]",
                "{2=499125, 3=250897, 4=124849, 5=62518, 6=31407, 7=15634, 8=7825, 9=3926, 10=1896, 11=956}",
                3.000835999968773
        );
        rangeUpGeometric_helper(
                10,
                2,
                "[33, 11, 11, 7, 31, 20, 3, 7, 2, 19, 15, 2, 12, 8, 5, 5, 2, 3, 4, 7, ...]",
                "{2=110949, 3=98973, 4=87810, 5=78512, 6=69401, 7=61358, 8=54691, 9=48553, 10=43415, 11=38254}",
                9.996049000016834
        );
        rangeUpGeometric_helper(
                11,
                10,
                "[13, 12, 19, 12, 12, 10, 11, 13, 10, 10, 12, 12, 12, 10, 12, 10, 10, 11, 11, 10, ...]",
                "{10=499125, 11=250897, 12=124849, 13=62518, 14=31407, 15=15634, 16=7825, 17=3926, 18=1896, 19=956}",
                11.000835999897873
        );
        rangeUpGeometric_helper(
                20,
                10,
                "[46, 22, 20, 18, 42, 34, 11, 17, 10, 27, 30, 10, 20, 18, 13, 14, 10, 11, 13, 16, ...]",
                "{10=90519, 11=82798, 12=75630, 13=68355, 14=62062, 15=56573, 16=51318, 17=46453, 18=42422, 19=38243}",
                19.99602800000434
        );
        rangeUpGeometric_helper(
                -9,
                -10,
                "[-7, -8, -1, -8, -8, -10, -9, -7, -10, -10, -8, -8, -8, -10, -8, -10, -10, -9, -9, -10, ...]",
                "{-10=499125, -9=250897, -8=124849, -7=62518, -6=31407, -5=15634, -4=7825, -3=3926, -2=1896, -1=956}",
                -8.999163999953181
        );
        rangeUpGeometric_helper(
                0,
                -10,
                "[26, 2, 0, -2, 22, 14, -9, -3, -10, 7, 10, -10, 0, -2, -7, -6, -10, -9, -7, -4, ...]",
                "{-10=90519, -9=82798, -8=75630, -7=68355, -6=62062, -5=56573, -4=51318, -3=46453, -2=42422," +
                " -1=38243}",
                -0.003971999999995211
        );
        rangeUpGeometric_helper(
                Integer.MAX_VALUE,
                Integer.MAX_VALUE - 1,
                "[2147483646, 2147483647, 2147483646, 2147483646, 2147483646, 2147483646, 2147483646, 2147483647," +
                " 2147483647, 2147483646, 2147483647, 2147483646, 2147483646, 2147483647, 2147483646, 2147483647," +
                " 2147483646, 2147483646, 2147483647, 2147483646, ...]",
                "{2147483646=666317, 2147483647=333683}",
                2.1474836463261979E9
        );
        rangeUpGeometric_helper(
                Integer.MIN_VALUE + 1,
                Integer.MIN_VALUE,
                "[-2147483645, -2147483646, -2147483639, -2147483646, -2147483646, -2147483648, -2147483647," +
                " -2147483645, -2147483648, -2147483648, -2147483646, -2147483646, -2147483646, -2147483648," +
                " -2147483646, -2147483648, -2147483648, -2147483647, -2147483647, -2147483648, ...]",
                "{-2147483648=499125, -2147483647=250897, -2147483646=124849, -2147483645=62518, -2147483644=31407," +
                " -2147483643=15634, -2147483642=7825, -2147483641=3926, -2147483640=1896, -2147483639=956}",
                -2.1474836470149984E9
        );
        rangeUpGeometric_fail_helper(5, 5);
        rangeUpGeometric_fail_helper(4, 5);
        rangeUpGeometric_fail_helper(Integer.MAX_VALUE - 5, -10);
    }

    private static void rangeDownGeometric_helper(
            int mean,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).rangeDownGeometric(a), output, topSampleCount, sampleMean);
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
        rangeDownGeometric_helper(
                0,
                2,
                "[-2, -3, -3, -2, 0, -3, 2, 1, 0, 1, -1, -4, 1, 0, 2, 1, 2, 1, 2, -1, ...]",
                "{2=333813, 1=221150, 0=148025, -1=98992, -2=66270, -3=43747, -4=29389, -5=19567, -6=12958, -7=8571}",
                -0.0020969999999987214
        );
        rangeDownGeometric_helper(
                -5,
                2,
                "[-4, -7, -34, -12, -2, 2, 1, 2, 0, -5, -11, -30, -4, -1, -10, -23, 1, -10, 1, -10, ...]",
                "{2=125138, 1=109518, 0=95496, -1=83848, -2=73359, -3=63701, -4=56011, -5=48897, -6=43099, -7=37459}",
                -5.0074679999951055
        );
        rangeDownGeometric_helper(
                5,
                10,
                "[5, 3, -13, 1, 7, 10, 9, 10, 9, 5, 2, -12, 5, 8, 3, -8, 10, 0, 9, 5, ...]",
                "{10=166887, 9=139197, 8=115545, 7=96038, 6=80312, 5=66813, 4=55942, 3=46416, 2=38391, 1=32378}",
                4.994349999991597
        );
        rangeDownGeometric_helper(
                0,
                10,
                "[-26, -2, 0, 2, -22, -14, 9, 3, 10, -7, -10, 10, 0, 2, 7, 6, 10, 9, 7, 4, ...]",
                "{10=90519, 9=82798, 8=75630, 7=68355, 6=62062, 5=56573, 4=51318, 3=46453, 2=42422, 1=38243}",
                0.003971999999995211
        );
        rangeDownGeometric_helper(
                -11,
                -10,
                "[-13, -12, -19, -12, -12, -10, -11, -13, -10, -10, -12, -12, -12, -10, -12, -10, -10, -11, -11," +
                " -10, ...]",
                "{-10=499125, -11=250897, -12=124849, -13=62518, -14=31407, -15=15634, -16=7825, -17=3926, -18=1896," +
                " -19=956}",
                -11.000835999897873
        );
        rangeDownGeometric_helper(
                -20,
                -10,
                "[-46, -22, -20, -18, -42, -34, -11, -17, -10, -27, -30, -10, -20, -18, -13, -14, -10, -11, -13," +
                " -16, ...]",
                "{-10=90519, -11=82798, -12=75630, -13=68355, -14=62062, -15=56573, -16=51318, -17=46453, -18=42422," +
                " -19=38243}",
                -19.99602800000434
        );
        rangeDownGeometric_helper(
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE,
                "[2147483644, 2147483645, 2147483638, 2147483645, 2147483645, 2147483647, 2147483646, 2147483644," +
                " 2147483647, 2147483647, 2147483645, 2147483645, 2147483645, 2147483647, 2147483645, 2147483647," +
                " 2147483647, 2147483646, 2147483646, 2147483647, ...]",
                "{2147483647=499125, 2147483646=250897, 2147483645=124849, 2147483644=62518, 2147483643=31407," +
                " 2147483642=15634, 2147483641=7825, 2147483640=3926, 2147483639=1896, 2147483638=956}",
                2.147483646013541E9
        );
        rangeDownGeometric_helper(
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                "[-2147483647, -2147483648, -2147483647, -2147483647, -2147483647, -2147483647, -2147483647," +
                " -2147483648, -2147483648, -2147483647, -2147483648, -2147483647, -2147483647, -2147483648," +
                " -2147483647, -2147483648, -2147483647, -2147483647, -2147483648, -2147483647, ...]",
                "{-2147483647=666317, -2147483648=333683}",
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
        positiveBigIntegers_helper(2, "RandomProvider_positiveBigIntegers_2", 114.05128999981362, 1.9994539999798795);
        positiveBigIntegers_helper(3, "RandomProvider_positiveBigIntegers_3", 42053.996647257176, 3.00330199998982);
        positiveBigIntegers_helper(4, "RandomProvider_positiveBigIntegers_4", 3.923720245917525E8, 4.005570999990192);
        positiveBigIntegers_helper(5, "RandomProvider_positiveBigIntegers_5", 8.95688013930559E12, 5.006042000008429);
        positiveBigIntegers_helper(
                10,
                "RandomProvider_positiveBigIntegers_10",
                4.456452305288997E35,
                9.998937000005219
        );
        positiveBigIntegers_helper(
                100,
                "RandomProvider_positiveBigIntegers_100",
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
                "RandomProvider_negativeBigIntegers_2",
                -114.05128999981362,
                1.9994539999798795
        );
        negativeBigIntegers_helper(
                3,
                "RandomProvider_negativeBigIntegers_3",
                -42053.996647257176,
                3.00330199998982
        );
        negativeBigIntegers_helper(
                4,
                "RandomProvider_negativeBigIntegers_4",
                -3.923720245917525E8,
                4.005570999990192
        );
        negativeBigIntegers_helper(
                5,
                "RandomProvider_negativeBigIntegers_5",
                -8.95688013930559E12,
                5.006042000008429
        );
        negativeBigIntegers_helper(
                10,
                "RandomProvider_negativeBigIntegers_10",
                -4.456452305288997E35,
                9.998937000005219
        );
        negativeBigIntegers_helper(
                100,
                "RandomProvider_negativeBigIntegers_100",
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
        naturalBigIntegers_helper(1, "RandomProvider_naturalBigIntegers_1", 7.527205000038622, 1.00042899999766);
        naturalBigIntegers_helper(2, "RandomProvider_naturalBigIntegers_2", 18553.42636205894, 2.0026819999891274);
        naturalBigIntegers_helper(3, "RandomProvider_naturalBigIntegers_3", 2.0721683567389777E8, 3.0050639999915902);
        naturalBigIntegers_helper(4, "RandomProvider_naturalBigIntegers_4", 6.063391150606273E12, 4.006382999991641);
        naturalBigIntegers_helper(5, "RandomProvider_naturalBigIntegers_5", 7.6975225805449024E16, 5.008064000005);
        naturalBigIntegers_helper(10, "RandomProvider_naturalBigIntegers_10", 6.216950515561165E39, 9.998042000004142);
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
        nonzeroBigIntegers_helper(2, "RandomProvider_nonzeroBigIntegers_2", -51.040697000000215, 1.9999089999798014);
        nonzeroBigIntegers_helper(3, "RandomProvider_nonzeroBigIntegers_3", -12560.956458999866, 3.0034329999897373);
        nonzeroBigIntegers_helper(4, "RandomProvider_nonzeroBigIntegers_4", 6.9148732007788E7, 4.007541999990144);
        nonzeroBigIntegers_helper(5, "RandomProvider_nonzeroBigIntegers_5", 5.064110348766358E12, 5.006230000008758);
        nonzeroBigIntegers_helper(
                10,
                "RandomProvider_nonzeroBigIntegers_10",
                -2.4957396868152156E35,
                9.995175000005379
        );
        nonzeroBigIntegers_helper(100, "RandomProvider_nonzeroBigIntegers_100", Double.NaN, 99.9676500000014);
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
        bigIntegers_helper(1, "RandomProvider_bigIntegers_1", -21.962919999999734, 1.0003699999976585);
        bigIntegers_helper(2, "RandomProvider_bigIntegers_2", 652.206306000023, 2.004140999989157);
        bigIntegers_helper(3, "RandomProvider_bigIntegers_3", 3.8147781719932765E7, 3.008166999991507);
        bigIntegers_helper(4, "RandomProvider_bigIntegers_4", 3.0232412271737646E12, 4.00732099999149);
        bigIntegers_helper(5, "RandomProvider_bigIntegers_5", 5.6936067849711536E16, 5.009217000005041);
        bigIntegers_helper(10, "RandomProvider_bigIntegers_10", -3.800274840387793E39, 9.993747000004165);
        bigIntegers_helper(100, "RandomProvider_bigIntegers_100", Double.NaN, 99.96628299999787);
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
        rangeUp_BigInteger_helper(5, 8, "RandomProvider_rangeUp_BigInteger_5_8", 900.8645519990359, 4.999453999951416);
        rangeUp_BigInteger_helper(
                10,
                8,
                "RandomProvider_rangeUp_BigInteger_10_8",
                7.070709518052013E21,
                10.008093999993136
        );
        rangeUp_BigInteger_helper(
                5,
                10,
                "RandomProvider_rangeUp_BigInteger_5_10",
                894.1639779982157,
                4.999280999951436
        );
        rangeUp_BigInteger_helper(
                10,
                10,
                "RandomProvider_rangeUp_BigInteger_10_10",
                7.070705170732308E21,
                10.006835999993193
        );
        rangeUp_BigInteger_helper(
                1,
                0,
                "RandomProvider_rangeUp_BigInteger_1_0",
                7.527205000038622,
                1.00042899999766
        );
        rangeUp_BigInteger_helper(
                10,
                0,
                "RandomProvider_rangeUp_BigInteger_10_0",
                6.216950515561165E39,
                9.998042000004142
        );
        rangeUp_BigInteger_helper(
                5,
                -8,
                "RandomProvider_rangeUp_BigInteger_5_-8",
                7.6976860134970016E16,
                5.007767000005044
        );
        rangeUp_BigInteger_helper(
                10,
                -8,
                "RandomProvider_rangeUp_BigInteger_10_-8",
                6.216950511573439E39,
                9.99805800000423
        );
        rangeUp_BigInteger_helper(
                5,
                -10,
                "RandomProvider_rangeUp_BigInteger_5_-10",
                7.6975187379143424E16,
                5.007546000005042
        );
        rangeUp_BigInteger_helper(
                10,
                -10,
                "RandomProvider_rangeUp_BigInteger_10_-10",
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
                "RandomProvider_rangeDown_BigInteger_5_8",
                -7.6976860134970016E16,
                5.007767000005044
        );
        rangeDown_BigInteger_helper(
                10,
                8,
                "RandomProvider_rangeDown_BigInteger_10_8",
                -6.216950511573439E39,
                9.99805800000423
        );
        rangeDown_BigInteger_helper(
                5,
                10,
                "RandomProvider_rangeDown_BigInteger_5_10",
                -7.6975187379143424E16,
                5.007546000005042
        );
        rangeDown_BigInteger_helper(
                10,
                10,
                "RandomProvider_rangeDown_BigInteger_10_10",
                -6.216950512238074E39,
                9.998103000004235
        );
        rangeDown_BigInteger_helper(
                1,
                0,
                "RandomProvider_rangeDown_BigInteger_1_0",
                -7.527205000038622,
                1.00042899999766
        );
        rangeDown_BigInteger_helper(
                10,
                0,
                "RandomProvider_rangeDown_BigInteger_10_0",
                -6.216950515561165E39,
                9.998042000004142
        );
        rangeDown_BigInteger_helper(
                5,
                -8,
                "RandomProvider_rangeDown_BigInteger_5_-8",
                -900.8645519990359,
                4.999453999951416
        );
        rangeDown_BigInteger_helper(
                10,
                -8,
                "RandomProvider_rangeDown_BigInteger_10_-8",
                -7.070709518052013E21,
                10.008093999993136
        );
        rangeDown_BigInteger_helper(
                5,
                -10,
                "RandomProvider_rangeDown_BigInteger_5_-10",
                -894.1639779982157,
                4.999280999951436
        );
        rangeDown_BigInteger_helper(
                10,
                -10,
                "RandomProvider_rangeDown_BigInteger_10_-10",
                -7.070705170732308E21,
                10.006835999993193
        );
        rangeDown_BigInteger_fail_helper(4, -10);
        rangeDown_BigInteger_fail_helper(3, -10);
        rangeDown_BigInteger_fail_helper(Integer.MAX_VALUE, 10);
    }

    private static void randomProvidersFixedScales_helper(
            int scale,
            int secondaryScale,
            int tertiaryScale,
            @NotNull String output
    ) {
        aeqitLimit(TINY_LIMIT, P.randomProvidersFixedScales(scale, secondaryScale, tertiaryScale), output);
        P.reset();
    }

    @Test
    public void testRandomProvidersFixedScales() {
        randomProvidersFixedScales_helper(8, 32, 2,
                "[RandomProvider[@6307004437655165633, 8, 32, 2], RandomProvider[@-4519915686575036879, 8, 32, 2]," +
                " RandomProvider[@-7025638243439307050, 8, 32, 2], RandomProvider[@-4126903833619919214, 8, 32, 2]," +
                " RandomProvider[@-5427694677045639344, 8, 32, 2], RandomProvider[@7348552576951528188, 8, 32, 2]," +
                " RandomProvider[@246622893618704701, 8, 32, 2], RandomProvider[@5121732865777267992, 8, 32, 2]," +
                " RandomProvider[@5759987607678616363, 8, 32, 2], RandomProvider[@-2677195354266163888, 8, 32, 2]," +
                " RandomProvider[@-5880388881439538439, 8, 32, 2], RandomProvider[@-6955429462754504210, 8, 32, 2]," +
                " RandomProvider[@-2860702685178448944, 8, 32, 2], RandomProvider[@-6467372047465124781, 8, 32, 2]," +
                " RandomProvider[@7320181434752204060, 8, 32, 2], RandomProvider[@782469688557640613, 8, 32, 2]," +
                " RandomProvider[@2774874340225596175, 8, 32, 2], RandomProvider[@3359862785424331688, 8, 32, 2]," +
                " RandomProvider[@-8433319027656919289, 8, 32, 2], RandomProvider[@1111804850476602072, 8, 32, 2]," +
                " ...]");
        randomProvidersFixedScales_helper(0, 0, 0,
                "[RandomProvider[@6307004437655165633, 0, 0, 0], RandomProvider[@-4519915686575036879, 0, 0, 0]," +
                " RandomProvider[@-7025638243439307050, 0, 0, 0], RandomProvider[@-4126903833619919214, 0, 0, 0]," +
                " RandomProvider[@-5427694677045639344, 0, 0, 0], RandomProvider[@7348552576951528188, 0, 0, 0]," +
                " RandomProvider[@246622893618704701, 0, 0, 0], RandomProvider[@5121732865777267992, 0, 0, 0]," +
                " RandomProvider[@5759987607678616363, 0, 0, 0], RandomProvider[@-2677195354266163888, 0, 0, 0]," +
                " RandomProvider[@-5880388881439538439, 0, 0, 0], RandomProvider[@-6955429462754504210, 0, 0, 0]," +
                " RandomProvider[@-2860702685178448944, 0, 0, 0], RandomProvider[@-6467372047465124781, 0, 0, 0]," +
                " RandomProvider[@7320181434752204060, 0, 0, 0], RandomProvider[@782469688557640613, 0, 0, 0]," +
                " RandomProvider[@2774874340225596175, 0, 0, 0], RandomProvider[@3359862785424331688, 0, 0, 0]," +
                " RandomProvider[@-8433319027656919289, 0, 0, 0], RandomProvider[@1111804850476602072, 0, 0, 0]," +
                " ...]");
        randomProvidersFixedScales_helper(-5, -10, -1,
                "[RandomProvider[@6307004437655165633, -5, -10, -1]," +
                " RandomProvider[@-4519915686575036879, -5, -10, -1]," +
                " RandomProvider[@-7025638243439307050, -5, -10, -1]," +
                " RandomProvider[@-4126903833619919214, -5, -10, -1]," +
                " RandomProvider[@-5427694677045639344, -5, -10, -1]," +
                " RandomProvider[@7348552576951528188, -5, -10, -1]," +
                " RandomProvider[@246622893618704701, -5, -10, -1]," +
                " RandomProvider[@5121732865777267992, -5, -10, -1]," +
                " RandomProvider[@5759987607678616363, -5, -10, -1]," +
                " RandomProvider[@-2677195354266163888, -5, -10, -1]," +
                " RandomProvider[@-5880388881439538439, -5, -10, -1]," +
                " RandomProvider[@-6955429462754504210, -5, -10, -1]," +
                " RandomProvider[@-2860702685178448944, -5, -10, -1]," +
                " RandomProvider[@-6467372047465124781, -5, -10, -1]," +
                " RandomProvider[@7320181434752204060, -5, -10, -1]," +
                " RandomProvider[@782469688557640613, -5, -10, -1]," +
                " RandomProvider[@2774874340225596175, -5, -10, -1]," +
                " RandomProvider[@3359862785424331688, -5, -10, -1]," +
                " RandomProvider[@-8433319027656919289, -5, -10, -1]," +
                " RandomProvider[@1111804850476602072, -5, -10, -1], ...]");
    }

    @Test
    public void testRandomProvidersDefault() {
        aeqitLimit(TINY_LIMIT, P.randomProvidersDefault(),
                "[RandomProvider[@6307004437655165633, 32, 8, 2], RandomProvider[@-4519915686575036879, 32, 8, 2]," +
                " RandomProvider[@-7025638243439307050, 32, 8, 2], RandomProvider[@-4126903833619919214, 32, 8, 2]," +
                " RandomProvider[@-5427694677045639344, 32, 8, 2], RandomProvider[@7348552576951528188, 32, 8, 2]," +
                " RandomProvider[@246622893618704701, 32, 8, 2], RandomProvider[@5121732865777267992, 32, 8, 2]," +
                " RandomProvider[@5759987607678616363, 32, 8, 2], RandomProvider[@-2677195354266163888, 32, 8, 2]," +
                " RandomProvider[@-5880388881439538439, 32, 8, 2], RandomProvider[@-6955429462754504210, 32, 8, 2]," +
                " RandomProvider[@-2860702685178448944, 32, 8, 2], RandomProvider[@-6467372047465124781, 32, 8, 2]," +
                " RandomProvider[@7320181434752204060, 32, 8, 2], RandomProvider[@782469688557640613, 32, 8, 2]," +
                " RandomProvider[@2774874340225596175, 32, 8, 2], RandomProvider[@3359862785424331688, 32, 8, 2]," +
                " RandomProvider[@-8433319027656919289, 32, 8, 2], RandomProvider[@1111804850476602072, 32, 8, 2]," +
                " ...]");
    }

    private static void randomProvidersDefaultSecondaryAndTertiaryScale_helper(int scale, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.withScale(scale).randomProvidersDefaultSecondaryAndTertiaryScale(), output);
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
                "[RandomProvider[@6307004437655165633, -5, 8, 2], RandomProvider[@-7770852755974855344, 0, 8, 2]," +
                " RandomProvider[@6832674836478317052, 0, 8, 2], RandomProvider[@2348979093764503707, 0, 8, 2]," +
                " RandomProvider[@-3311771976522419646, 0, 8, 2], RandomProvider[@-7634280196942541620, 1, 8, 2]," +
                " RandomProvider[@-7767705787531644917, 0, 8, 2], RandomProvider[@81421145801651884, 0, 8, 2]," +
                " RandomProvider[@-6552540366894552383, 0, 8, 2], RandomProvider[@7587196352192942386, 0, 8, 2]," +
                " RandomProvider[@-8246943450391907254, -3, 8, 2], RandomProvider[@-820566476981084482, 1, 8, 2]," +
                " RandomProvider[@2186418457431307206, 0, 8, 2], RandomProvider[@-5224324213007012299, 4, 8, 2]," +
                " RandomProvider[@-4563547075322936850, -1, 8, 2], RandomProvider[@-6789389732339265485, 0, 8, 2]," +
                " RandomProvider[@2363691026006694815, -2, 8, 2], RandomProvider[@3709336108842122753, 2, 8, 2]," +
                " RandomProvider[@3790936233798178647, 0, 8, 2], RandomProvider[@-3688180373504387923, 0, 8, 2]," +
                " ...]");
        randomProvidersDefaultSecondaryAndTertiaryScale_helper(8,
                "[RandomProvider[@3887373508347741101, 0, 8, 2], RandomProvider[@7489240012074539148, 0, 8, 2]," +
                " RandomProvider[@6700805681412473352, -1, 8, 2], RandomProvider[@-1374926170863284819, -8, 8, 2]," +
                " RandomProvider[@-3848183273460469121, 37, 8, 2], RandomProvider[@-49450811760821044, 0, 8, 2]," +
                " RandomProvider[@4430970725363059248, -2, 8, 2], RandomProvider[@-5482300470053986377, 0, 8, 2]," +
                " RandomProvider[@1538407982862159616, -23, 8, 2], RandomProvider[@7336982826946736739, 0, 8, 2]," +
                " RandomProvider[@-2938230294926391142, -6, 8, 2], RandomProvider[@-3485615571225768952, 7, 8, 2]," +
                " RandomProvider[@996423837391949024, 1, 8, 2], RandomProvider[@1456970389750881923, 8, 8, 2]," +
                " RandomProvider[@-6637239735594790750, -8, 8, 2], RandomProvider[@-2747177645903728981, 2, 8, 2]," +
                " RandomProvider[@740372212332383945, 7, 8, 2], RandomProvider[@-9195285235564984364, -18, 8, 2]," +
                " RandomProvider[@-527513867224610970, -10, 8, 2], RandomProvider[@-7292629687871019766, -14, 8, 2]," +
                " ...]");
        randomProvidersDefaultSecondaryAndTertiaryScale_fail_helper(0);
        randomProvidersDefaultSecondaryAndTertiaryScale_fail_helper(-1);
    }

    private static void randomProvidersDefaultTertiaryScale_helper(
            int scale,
            int secondaryScale,
            @NotNull String output
    ) {
        aeqitLimit(
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
        randomProvidersDefaultTertiaryScale_helper(1, 10,
                "[RandomProvider[@6307004437655165633, -5, 0, 2], RandomProvider[@-6171431263397241906, 0, 0, 2]," +
                " RandomProvider[@-1848836459362430551, -1, 1, 2], RandomProvider[@4117319870858322367, 1, 0, 2]," +
                " RandomProvider[@-1980397111602854804, 0, 0, 2], RandomProvider[@9043088029279530162, 0, 0, 2]," +
                " RandomProvider[@3798757115854213964, 0, -1, 2], RandomProvider[@-4388439033826358752, -1, -1, 2]," +
                " RandomProvider[@-6385331079771071403, -2, -6, 2], RandomProvider[@-8783571164295275862, -3, 0, 2]," +
                " RandomProvider[@-6017058958409844511, 0, 0, 2], RandomProvider[@-8489446225902946620, 0, 1, 2]," +
                " RandomProvider[@2332760258481133325, 2, 0, 2], RandomProvider[@8234428250670004417, 0, 0, 2]," +
                " RandomProvider[@2791533226707083522, -1, 0, 2], RandomProvider[@-3075859140743035913, -2, 0, 2]," +
                " RandomProvider[@-5529583516561931219, 1, 0, 2], RandomProvider[@-3450294338751092713, 0, 0, 2]," +
                " RandomProvider[@8375083899504250850, 1, 2, 2], RandomProvider[@1358604122880332696, 2, 0, 2], ...]");
        randomProvidersDefaultTertiaryScale_helper(8, 5,
                "[RandomProvider[@-7930238353239242110, -2, 2, 2], RandomProvider[@8121741449231013813, -6, 0, 2]," +
                " RandomProvider[@3075629249571270827, 0, -6, 2], RandomProvider[@7250712286939599965, 8, -2, 2]," +
                " RandomProvider[@-1625758914952969695, 7, -4, 2], RandomProvider[@-2252948204333303243, 2, -11, 2]," +
                " RandomProvider[@-6560316366330489585, -9, 12, 2], RandomProvider[@1732114681838405558, 1, 10, 2]," +
                " RandomProvider[@8481290765385111525, -6, -8, 2], RandomProvider[@-2851664326566691393, -2, 53, 2]," +
                " RandomProvider[@-8448663629993551207, 4, 10, 2], RandomProvider[@7917521134228965409, -11, 1, 2]," +
                " RandomProvider[@4266384421149219084, 15, -11, 2]," +
                " RandomProvider[@-3356991513602379740, 6, -12, 2], RandomProvider[@-6164819607534227449, 9, 1, 2]," +
                " RandomProvider[@1451132675867871265, 6, 17, 2], RandomProvider[@8575705307132439150, 11, 0, 2]," +
                " RandomProvider[@9124218868602319196, 1, -1, 2], RandomProvider[@8680823873053448635, 1, -18, 2]," +
                " RandomProvider[@-511767027727822272, -1, -5, 2], ...]");
        randomProvidersDefaultTertiaryScale_fail_helper(0, -5);
        randomProvidersDefaultTertiaryScale_fail_helper(-1, 5);
    }

    private static void randomProviders_helper(int scale, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.withScale(scale).randomProviders(), output);
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
        randomProviders_helper(1,
                "[RandomProvider[@6307004437655165633, -5, 0, -1], RandomProvider[@-2164058141773875473, -2, -1, 0]," +
                " RandomProvider[@-8673382113360763284, 1, 0, 0], RandomProvider[@2340806036351914368, 0, -1, 0]," +
                " RandomProvider[@-1405990713230511685, 0, 0, 0], RandomProvider[@3583690730334262517, 0, 0, 0]," +
                " RandomProvider[@-1765586312930096474, 0, 0, 1], RandomProvider[@-5049557932898541532, 1, 2, 0]," +
                " RandomProvider[@-1886753891110093763, -2, -3, 3], RandomProvider[@-2240132514146400831, 0, 3, 0]," +
                " RandomProvider[@-66506097629859838, 1, -2, 0], RandomProvider[@-600402736355833231, 1, -2, 0]," +
                " RandomProvider[@1665673473376977262, 0, 0, -2], RandomProvider[@8485604864171982830, 2, 0, -5]," +
                " RandomProvider[@6970041540771143295, 1, 0, -1], RandomProvider[@-5197820436891692579, -1, 0, -3]," +
                " RandomProvider[@4497665493626278145, 1, 0, 0], RandomProvider[@-9090554980022590090, 1, 0, 0]," +
                " RandomProvider[@-9166376787865155184, 0, 1, 1], RandomProvider[@1162418687312963091, 0, -1, 0]," +
                " ...]");
        randomProviders_helper(8,
                "[RandomProvider[@1407012230448997866, -5, 0, 6], RandomProvider[@4398829686418147516, 0, 8, -12]," +
                " RandomProvider[@-3714734985478712389, 0, 0, 24], RandomProvider[@4812290183660938574, 0, -4, 14]," +
                " RandomProvider[@1465093423371142335, -2, 0, 0], RandomProvider[@72183019512228281, 17, 2, -2]," +
                " RandomProvider[@5604138525455037432, -7, 3, 6]," +
                " RandomProvider[@1538407982862159616, -23, -10, -5]," +
                " RandomProvider[@-3573607485612529784, -2, -1, -5]," +
                " RandomProvider[@-1957751325601548703, 18, -13, 0]," +
                " RandomProvider[@-6013738702941258732, 0, 2, -19], RandomProvider[@-4494889013192701264, 4, 4, 6]," +
                " RandomProvider[@561508256824797692, 1, -17, 0], RandomProvider[@6209673873373544388, 0, 7, 0]," +
                " RandomProvider[@6275790472149024274, -10, -1, 0]," +
                " RandomProvider[@4014080165189533624, -11, -36, 6]," +
                " RandomProvider[@3800236844360757756, -4, -17, 3], RandomProvider[@-4720459891865792453, 5, -4, 6]," +
                " RandomProvider[@1210364901413161385, -6, 5, -1], RandomProvider[@6939589116879036549, -14, 9, -4]," +
                " ...]");
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
}
// @formatter:on
