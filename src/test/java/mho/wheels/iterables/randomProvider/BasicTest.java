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
}
// @formatter:on
