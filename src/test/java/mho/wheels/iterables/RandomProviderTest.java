package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.misc.Readers;
import mho.wheels.random.IsaacPRNG;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    private static final int DEFAULT_SAMPLE_SIZE = 1000000;
    private static final int DEFAULT_TOP_COUNT = 10;
    private static final int TINY_LIMIT = 20;

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
    }

    @Test
    public void testConstructor_int() {
        aeq(Q, "RandomProvider[@-7948823947390831374, 32, 8]");
        aeq(R, "RandomProvider[@2449928962525148503, 32, 8]");
        aeq(
                new RandomProvider(toList(IterableUtils.rangeBy(-1, -1, -IsaacPRNG.SIZE))),
                "RandomProvider[@3417306423260907531, 32, 8]"
        );
    }

    @Test
    public void testExample() {
        aeq(RandomProvider.example(), "RandomProvider[@-8800290164235921060, 32, 8]");
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
        aeq(P.withScale(100), "RandomProvider[@-8800290164235921060, 100, 8]");
        aeq(Q.withScale(3), "RandomProvider[@-7948823947390831374, 3, 8]");
        aeq(R.withScale(0), "RandomProvider[@2449928962525148503, 0, 8]");
    }

    @Test
    public void testWithSecondaryScale() {
        aeq(P.withSecondaryScale(100), "RandomProvider[@-8800290164235921060, 32, 100]");
        aeq(Q.withSecondaryScale(3), "RandomProvider[@-7948823947390831374, 32, 3]");
        aeq(R.withSecondaryScale(0), "RandomProvider[@2449928962525148503, 32, 0]");
    }

    @Test
    public void testCopy() {
        RandomProvider copy = P.copy();
        assertEquals(P, copy);
        P.nextInt();
        assertEquals(P, copy);
    }

    @Test
    public void testDeepCopy() {
        RandomProvider copy = P.deepCopy();
        assertEquals(P, copy);
        P.nextInt();
        assertNotEquals(P, copy);
    }

    @Test
    public void testReset() {
        RandomProvider PDependent = P.withScale(10);
        RandomProvider original = P.deepCopy();
        RandomProvider dependent = original.withScale(10);
        assertEquals(PDependent, dependent);
        P.nextInt();
        assertNotEquals(P, original);
        assertNotEquals(PDependent, dependent);
        P.reset();
        assertEquals(P, original);
        assertEquals(PDependent, dependent);
    }

    @Test
    public void testGetId() {
        aeq(P.getId(), -8800290164235921060L);
        P.nextInt();
        aeq(P.getId(), -6220528511995005615L);
        aeq(Q.getId(), -7948823947390831374L);
        aeq(R.getId(), 2449928962525148503L);
    }

    private static <T> void simpleProviderHelper(
            @NotNull Iterable<T> xs,
            @NotNull String output,
            @NotNull String sampleCountOutput
    ) {
        List<T> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeqit(sampleCount(sample).entrySet(), sampleCountOutput);
        P.reset();
    }

    @Test
    public void testNextInt() {
        aeq(P.nextInt(), -1084795351);
        aeq(Q.nextInt(), 405143795);
        aeq(R.nextInt(), 87945096);
    }

    @Test
    public void testIntegers() {
        aeqit(
                take(TINY_LIMIT, P.integers()),
                "[-1084795351, 1143001545, -1986160253, -1177145870, -968883275, -1465892161, -470080200," +
                " -2011352603, -248472835, 1997176995, 293205759, -106693423, -1593537177, -206249451, 565581811," +
                " -195502731, 102870776, -1612587755, -483804495, -831718234]"
        );
    }

    @Test
    public void testNextLong() {
        aeq(P.nextLong(), -4659160554254839351L);
        aeq(Q.nextLong(), 1740079350508374669L);
        aeq(R.nextLong(), 377721315096188309L);
    }

    @Test
    public void testLongs() {
        aeqit(
                take(TINY_LIMIT, P.longs()),
                "[-4659160554254839351, -8530493328132264462, -4161321976937299265, -2018979083213524507," +
                " -1067182698272227165, 1259309150092131537, -6844190056086445547, 2429155385556917621," +
                " 441826621316521237, -2077924480219546458, 404281420475794401, -3799772176394282532," +
                " -3259952746839854786, -1600663848124449857, 7874913887470575742, -6974357164754656982," +
                " 8454731288392606713, 347198304573602423, -601743751419410562, -2127248600113938899]"
        );
    }

    @Test
    public void testNextBoolean() {
        aeq(P.nextBoolean(), true);
        aeq(Q.nextBoolean(), true);
        aeq(R.nextBoolean(), false);
    }

    @Test
    public void testBooleans() {
        simpleProviderHelper(
                P.booleans(),
                "[true, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true," +
                " false, true, true, false]",
                "[true=499965, false=500035]"
        );
    }

    private static void nextUniformSample_Iterable_helper(@NotNull String xs, @NotNull String output) {
        aeq(Objects.toString(P.nextUniformSample(readIntegerListWithNulls(xs))), output);
        P.reset();
    }

    @Test
    public void testNextUniformSample_Iterable() {
        nextUniformSample_Iterable_helper("[3, 1, 4, 1]", "1");
        nextUniformSample_Iterable_helper("[3, 1, null, 1]", "1");
        P.reset();
        try {
            P.nextUniformSample(Collections.emptyList());
        } catch (ArithmeticException ignored) {}
    }

    private static void uniformSample_Iterable_helper(@NotNull String xs, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.uniformSample(readIntegerListWithNulls(xs)), output);
        P.reset();
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper(
                "[3, 1, 4, 1]",
                "[1, 1, 1, 4, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 4, ...]"
        );
        uniformSample_Iterable_helper("[]", "[]");
        uniformSample_Iterable_helper(
                "[3, 1, null, 1]",
                "[1, 1, 1, null, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, null, ...]"
        );
    }

    private static void nextUniformSample_String_helper(@NotNull String s, char output) {
        aeq(P.nextUniformSample(s), output);
        P.reset();
    }

    @Test
    public void testNextUniformSample_String() {
        nextUniformSample_String_helper("hello", 'e');
        try {
            P.nextUniformSample("");
        } catch (ArithmeticException ignored) {}
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        P.reset();
        aeqcs(P.uniformSample(s), output);
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper(
                "hello",
                "eellhlelheeooleollleoololohllollllholhhlhhohllllehelollehllllllleolleolelehelllohoohelllllehllllolo" +
                "llloellhhllloollohohlllohollo"
        );
        uniformSample_String_helper("", "");
    }

    @Test
    public void testNextOrdering() {
        aeq(P.nextOrdering(), "LT");
        aeq(Q.nextOrdering(), "LT");
        aeq(R.nextOrdering(), "EQ");
    }

    @Test
    public void testOrderings() {
        simpleProviderHelper(
                P.orderings(),
                "[LT, LT, GT, LT, EQ, LT, LT, LT, LT, LT, EQ, LT, LT, GT, LT, GT, EQ, GT, LT, LT]",
                "[LT=333313, GT=333615, EQ=333072]"
        );
    }

    @Test
    public void testNextRoundingMode() {
        aeq(P.nextRoundingMode(), "UP");
        aeq(Q.nextRoundingMode(), "CEILING");
        aeq(R.nextRoundingMode(), "UNNECESSARY");
    }

    @Test
    public void testRoundingModes() {
        simpleProviderHelper(
                P.roundingModes(),
                "[UP, UP, CEILING, DOWN, HALF_UP, HALF_EVEN, UNNECESSARY, HALF_UP, HALF_UP, CEILING, HALF_EVEN, UP," +
                " HALF_EVEN, HALF_UP, CEILING, HALF_UP, UNNECESSARY, HALF_UP, UP, HALF_DOWN]",
                "[UP=124820, CEILING=125380, DOWN=124690, HALF_UP=124847, HALF_EVEN=124918, UNNECESSARY=124948," +
                " HALF_DOWN=125283, FLOOR=125114]"
        );
    }

    @Test
    public void testNextPositiveByte() {
        aeq(P.nextPositiveByte(), 41);
        aeq(Q.nextPositiveByte(), 115);
        aeq(R.nextPositiveByte(), 8);
    }

    @Test
    public void testPositiveBytes() {
        aeqit(take(TINY_LIMIT, P.positiveBytes()),
                "[41, 73, 3, 114, 53, 63, 56, 101, 125, 35, 127, 81, 103, 21, 115, 117, 120, 21, 49, 38]");
    }

    @Test
    public void testNextPositiveShort() {
        aeq(P.nextPositiveShort(), 22057);
        aeq(Q.nextPositiveShort(), 243);
        aeq(R.nextPositiveShort(), 28552);
    }

    @Test
    public void testPositiveShorts() {
        aeqit(take(TINY_LIMIT, P.positiveShorts()),
                "[22057, 20937, 6531, 11762, 949, 17087, 9528, 12773, 6909, 163, 30463, 31953, 3431, 25109, 6131," +
                " 23925, 12024, 23829, 15025, 31910]");
    }

    @Test
    public void testNextPositiveInt() {
        aeq(P.nextPositiveInt(), 1143001545);
        aeq(Q.nextPositiveInt(), 405143795);
        aeq(R.nextPositiveInt(), 87945096);
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(TINY_LIMIT, P.positiveIntegers()),
                "[1143001545, 1997176995, 293205759, 565581811, 102870776, 94129103, 1488978913, 1855658460," +
                " 1833521269, 595157118, 1108943146, 1968520527, 80838404, 181782398, 960691757, 442512834," +
                " 474345991, 896325532, 1936225302, 419244611]");
    }

    @Test
    public void testNextPositiveLong() {
        aeq(P.nextPositiveLong(), 1259309150092131537L);
        aeq(Q.nextPositiveLong(), 1740079350508374669L);
        aeq(R.nextPositiveLong(), 377721315096188309L);
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(TINY_LIMIT, P.positiveLongs()),
                "[1259309150092131537, 2429155385556917621, 441826621316521237, 404281420475794401," +
                " 7874913887470575742, 8454731288392606713, 347198304573602423, 1900578154019506034," +
                " 2037300520516627497, 3849688850220341092, 8316024350196968003, 8774587835203863104," +
                " 7027759477968838149, 4582566483620040494, 104407546425062322, 7601919310667137530," +
                " 8935450729811208701, 1568186602409462170, 8008008025538113060, 2525682745804362002]");
    }

    @Test
    public void testNextNegativeByte() {
        aeq(P.nextNegativeByte(), -42);
        aeq(Q.nextNegativeByte(), -116);
        aeq(R.nextNegativeByte(), -9);
    }

    @Test
    public void testNegativeBytes() {
        aeqit(take(TINY_LIMIT, P.negativeBytes()),
                "[-42, -74, -4, -115, -54, -64, -57, -102, -126, -36, -128, -82, -104, -22, -116, -118, -121, -22," +
                " -50, -39]");
    }

    @Test
    public void testNextNegativeShort() {
        aeq(P.nextNegativeShort(), -22058);
        aeq(Q.nextNegativeShort(), -244);
        aeq(R.nextNegativeShort(), -28553);
    }

    @Test
    public void testNegativeShorts() {
        aeqit(take(TINY_LIMIT, P.negativeShorts()),
                "[-22058, -20938, -6532, -11763, -950, -17088, -9529, -12774, -6910, -164, -30464, -31954, -3432," +
                " -25110, -6132, -23926, -12025, -23830, -15026, -31911]");
    }

    @Test
    public void testNextNegativeInt() {
        aeq(P.nextNegativeInt(), -1084795351);
        aeq(Q.nextNegativeInt(), -1333080799);
        aeq(R.nextNegativeInt(), -362359403);
    }

    @Test
    public void testNegativeIntegers() {
        aeqit(take(TINY_LIMIT, P.negativeIntegers()),
                "[-1084795351, -1986160253, -1177145870, -968883275, -1465892161, -470080200, -2011352603," +
                " -248472835, -106693423, -1593537177, -206249451, -195502731, -1612587755, -483804495, -831718234," +
                " -884703402, -759016897, -1408421570, -372683595, -138708033]");
    }

    @Test
    public void testNextNegativeLong() {
        aeq(P.nextNegativeLong(), -4659160554254839351L);
        aeq(Q.nextNegativeLong(), -5476473126251815900L);
        aeq(R.nextNegativeLong(), -4625575076888178893L);
    }

    @Test
    public void testNegativeLongs() {
        aeqit(take(TINY_LIMIT, P.negativeLongs()),
                "[-4659160554254839351, -8530493328132264462, -4161321976937299265, -2018979083213524507," +
                " -1067182698272227165, -6844190056086445547, -2077924480219546458, -3799772176394282532," +
                " -3259952746839854786, -1600663848124449857, -6974357164754656982, -601743751419410562," +
                " -2127248600113938899, -8615999285391660475, -3152269795703421596, -279738421105985993," +
                " -9128636656372363642, -4787870135943121859, -4018571045884316278, -3622924013254235408]");
    }

    @Test
    public void testNextNaturalByte() {
        aeq(P.nextNaturalByte(), 41);
        aeq(Q.nextNaturalByte(), 115);
        aeq(R.nextNaturalByte(), 8);
    }

    @Test
    public void testNaturalBytes() {
        aeqit(take(TINY_LIMIT, P.naturalBytes()),
                "[41, 73, 3, 114, 53, 63, 56, 101, 125, 35, 127, 81, 103, 21, 115, 117, 120, 21, 49, 38]");
    }

    @Test
    public void testNextNaturalShort() {
        aeq(P.nextNaturalShort(), 22057);
        aeq(Q.nextNaturalShort(), 243);
        aeq(R.nextNaturalShort(), 28552);
    }

    @Test
    public void testNaturalShorts() {
        aeqit(take(TINY_LIMIT, P.naturalShorts()),
                "[22057, 20937, 6531, 11762, 949, 17087, 9528, 12773, 6909, 163, 30463, 31953, 3431, 25109, 6131," +
                " 23925, 12024, 23829, 15025, 31910]");
    }

    @Test
    public void testNextNaturalInt() {
        aeq(P.nextNaturalInt(), 1062688297);
        aeq(Q.nextNaturalInt(), 405143795);
        aeq(R.nextNaturalInt(), 87945096);
    }

    @Test
    public void testNaturalIntegers() {
        aeqit(take(TINY_LIMIT, P.naturalIntegers()),
                "[1062688297, 1143001545, 161323395, 970337778, 1178600373, 681591487, 1677403448, 136131045," +
                " 1899010813, 1997176995, 293205759, 2040790225, 553946471, 1941234197, 565581811, 1951980917," +
                " 102870776, 534895893, 1663679153, 1315765414]");
    }

    @Test
    public void testNextNaturalLong() {
        aeq(P.nextNaturalLong(), 4564211482599936457L);
        aeq(Q.nextNaturalLong(), 1740079350508374669L);
        aeq(R.nextNaturalLong(), 377721315096188309L);
    }

    @Test
    public void testNaturalLongs() {
        aeqit(take(TINY_LIMIT, P.naturalLongs()),
                "[4564211482599936457, 692878708722511346, 5062050059917476543, 7204392953641251301," +
                " 8156189338582548643, 1259309150092131537, 2379181980768330261, 2429155385556917621," +
                " 441826621316521237, 7145447556635229350, 404281420475794401, 5423599860460493276," +
                " 5963419290014921022, 7622708188730325951, 7874913887470575742, 2249014872100118826," +
                " 8454731288392606713, 347198304573602423, 8621628285435365246, 7096123436740836909]");
    }

    @Test
    public void testNextNonzeroByte() {
        aeq(P.nextNonzeroByte(), 41);
        aeq(Q.nextNonzeroByte(), -13);
        aeq(R.nextNonzeroByte(), -120);
    }

    @Test
    public void testNonzeroBytes() {
        aeqit(take(TINY_LIMIT, P.nonzeroBytes()),
                "[41, -55, -125, -14, -75, -65, 56, -27, -3, -93, -1, -47, 103, 21, -13, 117, -8, 21, -79, -90]");
    }

    @Test
    public void testNextNonzeroShort() {
        aeq(P.nextNonzeroShort(), 22057);
        aeq(Q.nextNonzeroShort(), 243);
        aeq(R.nextNonzeroShort(), -4216);
    }

    @Test
    public void testNonzeroShorts() {
        aeqit(take(TINY_LIMIT, P.nonzeroShorts()),
                "[22057, -11831, -26237, 11762, 949, 17087, 9528, 12773, -25859, -32605, -2305, -815, -29337, -7659," +
                " 6131, -8843, -20744, -8939, -17743, -858]");
    }

    @Test
    public void testNextNonzeroInt() {
        aeq(P.nextNonzeroInt(), -1084795351);
        aeq(Q.nextNonzeroInt(), 405143795);
        aeq(R.nextNonzeroInt(), 87945096);
    }

    @Test
    public void testNonzeroIntegers() {
        aeqit(take(TINY_LIMIT, P.nonzeroIntegers()),
                "[-1084795351, 1143001545, -1986160253, -1177145870, -968883275, -1465892161, -470080200," +
                " -2011352603, -248472835, 1997176995, 293205759, -106693423, -1593537177, -206249451, 565581811," +
                " -195502731, 102870776, -1612587755, -483804495, -831718234]");
    }

    @Test
    public void testNextNonzeroLong() {
        aeq(P.nextNonzeroLong(), -4659160554254839351L);
        aeq(Q.nextNonzeroLong(), 1740079350508374669L);
        aeq(R.nextNonzeroLong(), 377721315096188309L);
    }

    @Test
    public void testNonzeroLongs() {
        aeqit(take(TINY_LIMIT, P.nonzeroLongs()),
                "[-4659160554254839351, -8530493328132264462, -4161321976937299265, -2018979083213524507," +
                " -1067182698272227165, 1259309150092131537, -6844190056086445547, 2429155385556917621," +
                " 441826621316521237, -2077924480219546458, 404281420475794401, -3799772176394282532," +
                " -3259952746839854786, -1600663848124449857, 7874913887470575742, -6974357164754656982," +
                " 8454731288392606713, 347198304573602423, -601743751419410562, -2127248600113938899]");
    }

    @Test
    public void testNextByte() {
        aeq(P.nextByte(), 41);
        aeq(Q.nextByte(), -13);
        aeq(R.nextByte(), -120);
    }

    @Test
    public void testBytes() {
        aeqit(take(TINY_LIMIT, P.bytes()),
                "[41, -55, -125, -14, -75, -65, 56, -27, -3, -93, -1, -47, 103, 21, -13, 117, -8, 21, -79, -90]");
    }

    @Test
    public void testNextShort() {
        aeq(P.nextShort(), 22057);
        aeq(Q.nextShort(), 243);
        aeq(R.nextShort(), -4216);
    }

    @Test
    public void testShorts() {
        aeqit(take(TINY_LIMIT, P.shorts()),
                "[22057, -11831, -26237, 11762, 949, 17087, 9528, 12773, -25859, -32605, -2305, -815, -29337, -7659," +
                " 6131, -8843, -20744, -8939, -17743, -858]");
    }

    @Test
    public void testNextAsciiChar() {
        aeq(P.nextAsciiChar(), ')');
        aeq(Q.nextAsciiChar(), 's');
        aeq(R.nextAsciiChar(), '\b');
    }

    @Test
    public void testAsciiCharacters() {
        aeqcs(P.asciiCharacters(),
                ")I\3r5?8e}#\177Qg\25sux\u00151&OaV\\?>5?u~\34*Oy\4w?~+-Br\7)\34d\26CLERd%@c7\2\5o.\u001c2S\6z=Vz\30" +
                "}l\nNph\32Xx^$x.\23\22\3oK10)\177u;\u001c2nEZF\17If`5f\23OSS\5\3v\5s\u000b2Y\\oKo;\1|CQ7&");
    }

    @Test
    public void testNextChar() {
        aeq(P.nextChar(), '嘩');
        aeq(Q.nextChar(), 'ó');
        aeq(R.nextChar(), '\uef88');
    }

    @Test
    public void testCharacters() {
        aeqcs(P.characters(),
                "嘩퇉馃\u2df2ε䊿\u2538\u31e5髽肣\uf6ffﳑ赧\ue215\u17f3\udd75껸\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ䲵箿偵恾ᬜK" +
                "㵏ꏹ缄㩷ⴿ읾纫\ufe2d㗂䝲\uf207갩힜坤琖\u2a43퉌\uea45\ue352蕤餥䉀\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ" +
                "\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆" +
                "\ue78f㱉泦㭠瀵컦刓嗏\u3353\ue2d3\ud805ឃᳶ쪅右䦋\u2832ﭙ빜䫯噋\uf36fꌻ躁\ue87c홃祝몷ࢦ");
    }

    private static void nextFromRangeUp_byte_helper(int a, int output) {
        P.reset();
        aeq(P.nextFromRangeUp((byte) a), output);
    }

    @Test
    public void testNextFromRangeUp_byte() {
        nextFromRangeUp_byte_helper(0, 41);
        nextFromRangeUp_byte_helper(1 << 6, 105);
        nextFromRangeUp_byte_helper(-1 << 6, -23);
        nextFromRangeUp_byte_helper(Byte.MAX_VALUE, 127);
        nextFromRangeUp_byte_helper(Byte.MIN_VALUE, -87);
    }

    private static void rangeUp_byte_helper(byte a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
        P.reset();
    }

    @Test
    public void testRangeUp_byte() {
        rangeUp_byte_helper(
                (byte) 0,
                "[41, 73, 3, 114, 53, 63, 56, 101, 125, 35, 127, 81, 103, 21, 115, 117, 120, 21, 49, 38]"
        );
        rangeUp_byte_helper(
                (byte) (1 << 6),
                "[105, 73, 67, 114, 117, 127, 120, 101, 125, 99, 127, 81, 103, 85, 115, 117, 120, 85, 113, 102]"
        );
        rangeUp_byte_helper(
                (byte) (-1 << 6),
                "[-23, 67, 117, 127, -8, 99, 39, -43, 53, -43, 113, 102, 22, -1, -2, 117, 127, 53, 62, -36]"
        );
        rangeUp_byte_helper(
                Byte.MAX_VALUE,
                "[127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127]"
        );
        rangeUp_byte_helper(
                Byte.MIN_VALUE,
                "[-87, 73, 3, 114, 53, 63, -72, 101, 125, 35, 127, 81, -25, -107, 115, -11, 120, -107, 49, 38]"
        );
    }

    private static void nextFromRangeUp_short_helper(int a, int output) {
        P.reset();
        aeq(P.nextFromRangeUp((short) a), output);
    }

    @Test
    public void testNextFromRangeUp_short() {
        nextFromRangeUp_short_helper(0, 22057);
        nextFromRangeUp_short_helper(1 << 14, 22057);
        nextFromRangeUp_short_helper(-1 << 14, 5673);
        nextFromRangeUp_short_helper(Short.MAX_VALUE, 32767);
        nextFromRangeUp_short_helper(Short.MIN_VALUE, -10711);
    }

    private static void rangeUp_short_helper(short a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
        P.reset();
    }

    @Test
    public void testRangeUp_short() {
        rangeUp_short_helper(
                (short) 0,
                "[22057, 20937, 6531, 11762, 949, 17087, 9528, 12773, 6909, 163, 30463, 31953, 3431, 25109, 6131," +
                " 23925, 12024, 23829, 15025, 31910]"
        );
        rangeUp_short_helper(
                (short) (1 << 14),
                "[22057, 20937, 22915, 28146, 17333, 17087, 25912, 29157, 23293, 16547, 30463, 31953, 19815, 25109," +
                " 22515, 23925, 28408, 23829, 31409, 31910]"
        );
        rangeUp_short_helper(
                (short) (-1 << 14),
                "[5673, 22915, -4622, -15435, 703, -6856, -3611, 23293, 16547, 19815, -10253, 28408, 31409, 3023," +
                " -15391, 16214, -9764, 4671, -3778, 3253]"
        );
        rangeUp_short_helper(
                Short.MAX_VALUE,
                "[32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767," +
                " 32767, 32767, 32767, 32767, 32767, 32767]"
        );
        rangeUp_short_helper(
                Short.MIN_VALUE,
                "[-10711, 20937, 6531, -21006, -31819, -15681, -23240, -19995, 6909, 163, 30463, 31953, 3431, 25109," +
                " -26637, 23925, 12024, 23829, 15025, 31910]"
        );
    }

    private static void nextFromRangeUp_int_helper(int a, int output) {
        aeq(P.nextFromRangeUp(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeUp_int() {
        nextFromRangeUp_int_helper(0, 1143001545);
        nextFromRangeUp_int_helper(1 << 30, 1143001545);
        nextFromRangeUp_int_helper(-1 << 30, 69259721);
        nextFromRangeUp_int_helper(Integer.MAX_VALUE, 2147483647);
        nextFromRangeUp_int_helper(Integer.MIN_VALUE, -1004482103);
    }

    private static void rangeUp_int_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
        P.reset();
    }

    @Test
    public void testRangeUp_int() {
        rangeUp_int_helper(
                0,
                "[1143001545, 970337778, 681591487, 136131045, 1997176995, 2040790225, 1941234197, 1951980917," +
                " 534895893, 1315765414, 1488978913, 1855658460, 739062078, 2008775615, 595157118, 1108943146," +
                " 1275438073, 985283191, 181782398, 960691757]"
        );
        rangeUp_int_helper(
                1 << 30,
                "[1143001545, 2044079602, 1755333311, 1209872869, 1997176995, 2040790225, 1941234197, 1951980917," +
                " 1608637717, 1315765414, 1488978913, 1855658460, 1812803902, 2008775615, 1668898942, 1108943146," +
                " 1275438073, 2059025015, 1255524222, 2034433581]"
        );
        rangeUp_int_helper(
                -1 << 30,
                "[69259721, 2044079602, 1755333311, 1209872869, 923435171, 1608637717, 415237089, 781916636," +
                " 1812803902, -478584706, 35201322, 2059025015, -891959426, -113050067, 1109175337, -654497213," +
                " 1765141061, 1055360356, 936002112, 468907575]"
        );
        rangeUp_int_helper(
                Integer.MAX_VALUE,
                "[2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647," +
                " 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647," +
                " 2147483647, 2147483647, 2147483647, 2147483647]"
        );
        rangeUp_int_helper(
                Integer.MIN_VALUE,
                "[-1004482103, 970337778, 681591487, 136131045, -150306653, 2040790225, 1941234197, 1951980917," +
                " 534895893, 1315765414, -658504735, -291825188, 739062078, 2008775615, -1552326530, " +
                "-1038540502, 1275438073, 985283191, -1965701250, -1186791891]"
        );
    }

    private static void nextFromRangeUp_long_helper(long a, long output) {
        aeq(P.nextFromRangeUp(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeUp_long() {
        nextFromRangeUp_long_helper(0L, 2978664684788457540L);
        nextFromRangeUp_long_helper(1L << 62, 7590350703215845444L);
        nextFromRangeUp_long_helper(-1L << 62, 1609966265326126211L);
        nextFromRangeUp_long_helper(Long.MAX_VALUE, 9223372036854775807L);
        nextFromRangeUp_long_helper(Long.MIN_VALUE, -3001719753101261693L);
    }

    private static void rangeUp_long_helper(long a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
        P.reset();
    }

    @Test
    public void testRangeUp_long() {
        rangeUp_long_helper(
                0L,
                "[2978664684788457540, 259411669349684921, 3819968131296829608, 4045916796483607944," +
                " 9050600215542762103, 9220690404532069369, 7461625247526204659, 8293297493653674228," +
                " 8695924240519389599, 3583222511262526670, 5713832101313495128, 6232776051665771374," +
                " 4562923580722056620, 3840666588017310711, 8453337235194935587, 2025272514667682114," +
                " 5709813867763402188, 324207515304377018, 4552478380255597834, 3134077502549279289]"
        );
        rangeUp_long_helper(
                1L << 62,
                "[7590350703215845444, 4871097687777072825, 8431654149724217512, 8657602814910995848," +
                " 9050600215542762103, 9220690404532069369, 7461625247526204659, 8293297493653674228," +
                " 8695924240519389599, 8194908529689914574, 5713832101313495128, 6232776051665771374," +
                " 9174609599149444524, 8452352606444698615, 8453337235194935587, 6636958533095070018," +
                " 5709813867763402188, 4935893533731764922, 9164164398682985738, 7745763520976667193]"
        );
        rangeUp_long_helper(
                -1L << 62,
                "[1609966265326126211, -1308654454609754433, -1874654246358644483, 4614632709429989841," +
                " 5549737756197188595, 8802817253011410639, -4341372912259511332, 1351874002717355189," +
                " 4304305952112864638, -2650756327368211889, 7135333504334759031, -1322097316696094037," +
                " 1669389700406211395, 5037133408195934528, -1504487908198687998, 6789092804704878382," +
                " 3566685953462311704, 5270340593672846712, -1719689906509449096, -3246513607960354030]"
        );
        rangeUp_long_helper(
                Long.MAX_VALUE,
                "[9223372036854775807, 9223372036854775807, 9223372036854775807, 9223372036854775807," +
                " 9223372036854775807, 9223372036854775807, 9223372036854775807, 9223372036854775807," +
                " 9223372036854775807, 9223372036854775807, 9223372036854775807, 9223372036854775807," +
                " 9223372036854775807, 9223372036854775807, 9223372036854775807, 9223372036854775807," +
                " 9223372036854775807, 9223372036854775807, 9223372036854775807, 9223372036854775807]"
        );
        rangeUp_long_helper(
                Long.MIN_VALUE,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, 2946691002601937," +
                " 938051737769800691, 6726395392388302357, 4191131234584022735, -8953058930686899236," +
                " -3259812015710032715, -307380066314523266, -7262442345795599793, 2523647485907371127," +
                " -5933783335123481941, 9097897703523752562, 8234018459023606428, -2942296318021176509," +
                " 5939553317435058514, 425447389768546624, -6116173926626075902, 2177406786277490478]"
        );
    }

    private static void nextFromRangeUp_char_helper(char a, char output) {
        aeq(P.nextFromRangeUp(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeUp_char() {
        nextFromRangeUp_char_helper('\0', '嘩');
        nextFromRangeUp_char_helper('a', '嚊');
        nextFromRangeUp_char_helper('Ш', '婑');
        nextFromRangeUp_char_helper('\uffff', '\uffff');
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        aeqcs(P.rangeUp(a), output);
        P.reset();
    }

    @Test
    public void testRangeUp_char() {
        rangeUp_char_helper(
                '\0',
                "嘩퇉馃\u2df2ε䊿\u2538\u31e5髽肣\uf6ffﳑ赧\ue215\u17f3\udd75껸\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ䲵箿偵" +
                "恾ᬜK㵏ꏹ缄㩷ⴿ읾纫\ufe2d㗂䝲\uf207갩힜坤琖\u2a43퉌\uea45\ue352蕤餥䉀\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖" +
                "䇺ᤘ\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅" +
                "\ue2da䟆\ue78f㱉泦㭠瀵컦刓嗏\u3353\ue2d3\ud805ឃᳶ쪅右䦋\u2832ﭙ빜䫯噋\uf36fꌻ躁\ue87c홃祝몷ࢦ"
        );
        rangeUp_char_helper(
                'a',
                "嚊툪駤\u2e53Ж䌠\u2599\u3246魞脄\uf760ﴲ跈\ue276ᡔ\uddd6꽙\udd76묒ﴇ䰰т羷ᨽ加\u319f䴖簠僖惟\u1b7d\u218b㶰ꑚ罥" +
                "㫘ⶠ쟟缌ﺎ㘣䟓\uf268겊\ud7fd埅瑷\u2aa4튭\ueaa6\ue3b3藅馆䊡\u2bc4\uf698鹣鹦豐辏\uee7d㐓醴컧ᇛ뒞熷䉛\u1979" +
                "\uea5e\u2b4d㗫쨯\uab51凉觻됹䳙\u293f\u2e85\uf8d9\u128fݴ酳\uff64럐\ue62cꠒ﨑岊균\ued56\uab1c痽\ue913뻏뺦" +
                "\ue33b䠧\ue7f0㲪浇㯁炖콇剴嘰\u33b4\ue334\ud866\u17e4ᵗ쫦呔䧬\u2893\ufbba뺽䭐嚬\uf3d0ꎜ転\ue8dd횤者묘इ"
        );
        rangeUp_char_helper(
                'Ш',
                "婑헱鶫\u321aߝ䛧\u2960㘍鼥蓋ﬧ醏\ue63dᰛ\ue19d댠\ue13d뻙俷ࠉ荾Ḅ噧㕦僝翧咝撦ὄ\u2552䅷ꠡ茬㺟ㅧ쮦苓㧪䮚\uf62f끑" +
                "\udbc4完砾\u2e6b홴\uee6d\ue77a覌鵍䙨\u2f8b著ꈪꈭ逗鍖\uf244㟚镻튮ᖢ롥畾䘢ᵀ\uee25\u2f14㦲췶꼘喐跂렀傠ⴆ\u324cﲠᙖ" +
                "\u0b3b锺뮗\ue9f3ꯙ\ufdd8恑놧\uf11d껣秄\uecda슖쉭\ue702䯮\uebb7䁱焎㾈瑝팎嘻姷㝻\ue6fb\udc2d\u1bab\u211e캭堛䶳" +
                "ⱚﾁ슄众婳\uf797ꝣ銩\ueca4\uda6bﹹ뻟\u0cce耚\uf0adᓦၽ"
        );
        rangeUp_char_helper(
                '\uffff',
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff" +
                "\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff\uffff"
        );
    }

    private static void nextFromRangeDown_byte_helper(int a, int output) {
        aeq(P.nextFromRangeDown((byte) a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeDown_byte() {
        nextFromRangeDown_byte_helper(0, -87);
        nextFromRangeDown_byte_helper(1 << 6, -87);
        nextFromRangeDown_byte_helper(-1 << 6, -87);
        nextFromRangeDown_byte_helper(Byte.MAX_VALUE, -87);
        nextFromRangeDown_byte_helper(Byte.MIN_VALUE, -128);
    }

    private static void rangeDown_byte_helper(byte a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
        P.reset();
    }

    @Test
    public void testRangeDown_byte() {
        rangeDown_byte_helper(
                (byte) 0,
                "[-87, -72, -25, -107, -11, -107, -42, -65, -66, -11, -2, -100, -86, -49, -124, -9, -65, -2, -83, -14]"
        );
        rangeDown_byte_helper(
                (byte) (1 << 6),
                "[-87, 3, 53, 63, -72, 35, -25, -107, -11, -107, 49, 38, -42, -65, -66, 53, 63, -11, -2, -100]"
        );
        rangeDown_byte_helper(
                (byte) (-1 << 6),
                "[-87, -125, -75, -65, -72, -93, -107, -107, -79, -90, -65, -66, -75, -65, -100, -86, -124, -65," +
                " -85, -83]"
        );
        rangeDown_byte_helper(
                Byte.MAX_VALUE,
                "[-87, 73, 3, 114, 53, 63, -72, 101, 125, 35, 127, 81, -25, -107, 115, -11, 120, -107, 49, 38]"
        );
        rangeDown_byte_helper(
                Byte.MIN_VALUE,
                "[-128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128, -128," +
                " -128, -128, -128, -128]"
        );
    }

    private static void nextFromRangeDown_short_helper(int a, int output) {
        aeq(P.nextFromRangeDown((short) a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeDown_short() {
        nextFromRangeDown_short_helper(0, -10711);
        nextFromRangeDown_short_helper(1 << 14, -10711);
        nextFromRangeDown_short_helper(-1 << 14, -26237);
        nextFromRangeDown_short_helper(Short.MAX_VALUE, -10711);
        nextFromRangeDown_short_helper(Short.MIN_VALUE, -32768);
    }

    private static void rangeDown_short_helper(short a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
        P.reset();
    }

    @Test
    public void testRangeDown_short() {
        rangeDown_short_helper(
                (short) 0,
                "[-10711, -21006, -31819, -15681, -23240, -19995, -26637, -13361, -31775, -170, -26148, -11713," +
                " -20162, -13131, -1089, -12171, -8066, -25828, -24278, -17073]"
        );
        rangeDown_short_helper(
                (short) (1 << 14),
                "[-10711, 6531, -21006, -31819, -15681, -23240, -19995, 6909, 163, 3431, -26637, 12024, 15025," +
                " -13361, -31775, -170, -26148, -11713, -20162, -13131]"
        );
        rangeDown_short_helper(
                (short) (-1 << 14),
                "[-26237, -21006, -31819, -23240, -19995, -25859, -32605, -29337, -26637, -20744, -17743, -31775," +
                " -26148, -20162, -25828, -24278, -17073, -23559, -17801, -21185]"
        );
        rangeDown_short_helper(
                Short.MAX_VALUE,
                "[-10711, 20937, 6531, -21006, -31819, -15681, -23240, -19995, 6909, 163, 30463, 31953, 3431, 25109," +
                " -26637, 23925, 12024, 23829, 15025, 31910]"
        );
        rangeDown_short_helper(
                Short.MIN_VALUE,
                "[-32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768," +
                " -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768]"
        );
    }

    private static void nextFromRangeDown_int_helper(int a, int output) {
        aeq(P.nextFromRangeDown(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeDown_int() {
        nextFromRangeDown_int_helper(0, -1004482103);
        nextFromRangeDown_int_helper(1 << 30, -1004482103);
        nextFromRangeDown_int_helper(-1 << 30, -1177145870);
        nextFromRangeDown_int_helper(Integer.MAX_VALUE, -1004482103);
        nextFromRangeDown_int_helper(Integer.MIN_VALUE, -2147483648);
    }

    private static void rangeDown_int_helper(int a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
        P.reset();
    }

    @Test
    public void testRangeDown_int() {
        rangeDown_int_helper(
                0,
                "[-1004482103, -150306653, -658504735, -291825188, -1552326530, -1038540502, -1965701250," +
                " -1186791891, -1728239037, -18381468, -137739712, -604834249, -1131859022, -1686158854," +
                " -1782600976, -2111534694, -1846406610, -553610990, -96510935, -2032484754]"
        );
        rangeDown_int_helper(
                1 << 30,
                "[-1004482103, 970337778, 681591487, 136131045, -150306653, 534895893, -658504735, -291825188," +
                " 739062078, -1552326530, -1038540502, 985283191, -1965701250, -1186791891, 35433513, -1728239037," +
                " 691399237, -18381468, -137739712, -604834249]"
        );
        rangeDown_int_helper(
                -1 << 30,
                "[-1177145870, -1465892161, -2011352603, -1612587755, -1408421570, -1552326530, -1162200457," +
                " -1965701250, -1186791891, -2112050135, -1728239037, -1456084411, -1288200699, -1131859022," +
                " -1655648634, -2073512899, -1686158854, -1782600976, -2111534694, -1846406610]"
        );
        rangeDown_int_helper(
                Integer.MAX_VALUE,
                "[-1004482103, 970337778, 681591487, 136131045, -150306653, 2040790225, 1941234197, 1951980917," +
                " 534895893, 1315765414, -658504735, -291825188, 739062078, 2008775615, -1552326530, -1038540502," +
                " 1275438073, 985283191, -1965701250, -1186791891]"
        );
        rangeDown_int_helper(
                Integer.MIN_VALUE,
                "[-2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648," +
                " -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648," +
                " -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648]"
        );
    }

    private static void nextFromRangeDown_long_helper(long a, long output) {
        aeq(P.nextFromRangeDown(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeDown_long() {
        nextFromRangeDown_long_helper(0L, -3001719753101261693L);
        nextFromRangeDown_long_helper(1L << 62, -3001719753101261693L);
        nextFromRangeDown_long_helper(-1L << 62, -6244707352066318268L);
        nextFromRangeDown_long_helper(Long.MAX_VALUE, -3001719753101261693L);
        nextFromRangeDown_long_helper(Long.MIN_VALUE, -9223372036854775808L);
    }

    private static void rangeDown_long_helper(long a, @NotNull String output) {
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
        P.reset();
    }

    @Test
    public void testRangeDown_long() {
        rangeDown_long_helper(
                0L,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, -8953058930686899236," +
                " -3259812015710032715, -307380066314523266, -7262442345795599793, -5933783335123481941," +
                " -2942296318021176509, -6116173926626075902, -1045000064965076200, -6331375924936837000," +
                " -7858199626387741934, -750497281407653010, -4964572946333319706, -3265594823497196973," +
                " -7169158286100765709, -3899242950132782503, -354726065181537090, -8326391862079061231]"
        );
        rangeDown_long_helper(
                1L << 62,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, 2946691002601937," +
                " 938051737769800691, 4191131234584022735, -8953058930686899236, -3259812015710032715," +
                " -307380066314523266, -7262442345795599793, 2523647485907371127, -5933783335123481941," +
                " -2942296318021176509, 425447389768546624, -6116173926626075902, 2177406786277490478," +
                " -1045000064965076200, 658654575245458808, -6331375924936837000, -7858199626387741934]"
        );
        rangeDown_long_helper(
                -1L << 62,
                "[-6244707352066318268, -8963960367505090887, -5403403905557946200, -5177455240371167864," +
                " -5640149525592249138, -4660448456132719188, -5382705448837465097, -7198099522187093694," +
                " -8899164521550398790, -4670893656599177974, -6089294534305496519, -8650775946964755326," +
                " -7145123307227501859, -7605339026464506600, -6513958261454878089, -9034634951682803789," +
                " -7138643007725401796, -7486951269179234622, -7852292981010661281, -8935306705831985167]"
        );
        rangeDown_long_helper(
                Long.MAX_VALUE,
                "[-3001719753101261693, -5920340473037142337, -6486340264786032387, 2946691002601937," +
                " 938051737769800691, 6726395392388302357, 4191131234584022735, -8953058930686899236," +
                " -3259812015710032715, -307380066314523266, -7262442345795599793, 2523647485907371127," +
                " -5933783335123481941, 9097897703523752562, 8234018459023606428, -2942296318021176509," +
                " 5939553317435058514, 425447389768546624, -6116173926626075902, 2177406786277490478]"
        );
        rangeDown_long_helper(
                Long.MIN_VALUE,
                "[-9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808," +
                " -9223372036854775808, -9223372036854775808, -9223372036854775808, -9223372036854775808]"
        );
    }

    private static void nextFromRangeDown_char_helper(char a, char output) {
        aeq(P.nextFromRangeDown(a), output);
        P.reset();
    }

    @Test
    public void testNextFromRangeDown_char() {
        nextFromRangeDown_char_helper('\0', '\0');
        nextFromRangeDown_char_helper('a', ')');
        nextFromRangeDown_char_helper('Ш', 'ǉ');
        nextFromRangeDown_char_helper('\uffff', '嘩');
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
                "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
        );
        rangeDown_char_helper(
                'a',
                ")I\u00035?8#Q\25\u00151&OaV\\?>5?\34*O\4?+-B\7)\34\26CLER%@7\2\5.\u001c2S\6=V\30\nN\32X^$.\23\22\3K" +
                "10);\u001c2EZF\17I`5\23OSS\5\3\5\u000b2Y\\K;\1CQ7&W\5>U7\21(Y\2+'\32\24V<T@)B\2?3+\6\u00129CZ\35BW" +
                "\\FF\13[J"
        );
        rangeDown_char_helper(
                'Ш',
                "ǉƃεʿǥ\u02fd£ȕʱϏϡǜȿľοu~\u031cĪϹɷȇЖɃɌɅ\u0352ĥɀ\u0363ϯβœźŖǺĘǽˬǎ\u02f0ŨƚϘÞxȮĒưʻ²\u02da\u03605ȓ\u0353" +
                "\u02d3\5ʅϳƋ2\u0359\u02ef\u036f\u033b|ɑʷ¦ϲ¾·\21ΨÙɨŔ\u0329ο\u0306\u0092\u0339σŚ\u036bBɗŪŤͽЋɵÊ\u037eʡɪ" +
                "\35\u0366țǆɐʓΤǔȪĢͽ¬ü\u0300\u009bϖɕǆĖƣ,\u02d6ǌ\u02f7\3ɌÄʓϨͺɎ"
        );
        rangeDown_char_helper(
                '\uffff',
                "嘩퇉馃\u2df2ε䊿\u2538\u31e5髽肣\uf6ffﳑ赧\ue215\u17f3\udd75껸\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ䲵箿偵恾ᬜK㵏ꏹ缄㩷" +
                "ⴿ읾纫\ufe2d㗂䝲\uf207갩힜坤琖\u2a43퉌\uea45\ue352蕤餥䉀\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ\ue9fd" +
                "\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆\ue78f" +
                "㱉泦㭠瀵컦刓嗏\u3353\ue2d3\ud805ឃᳶ쪅右䦋\u2832ﭙ빜䫯噋\uf36fꌻ躁\ue87c홃祝몷ࢦ"
        );
    }

    private static void nextFromRange_byte_byte_helper(int a, int b, int output) {
        aeq(P.nextFromRange((byte) a, (byte) b), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_byte_byte() {
        nextFromRange_byte_byte_helper(10, 20, 19);
        nextFromRange_byte_byte_helper(10, 10, 10);
        nextFromRange_byte_byte_helper(-20, -10, -11);
        nextFromRange_byte_byte_helper(-20, -20, -20);
        nextFromRange_byte_byte_helper(0, 0, 0);
        nextFromRange_byte_byte_helper(0, 10, 9);
        nextFromRange_byte_byte_helper(-5, 0, -4);
        nextFromRange_byte_byte_helper(-5, 10, 4);
        nextFromRange_byte_byte_helper(-10, 5, -1);
        try {
            P.nextFromRange((byte) 5, (byte) -10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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

    private static void nextFromRange_short_short_helper(int a, int b, int output) {
        aeq(P.nextFromRange((short) a, (short) b), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_short_short() {
        nextFromRange_short_short_helper(10, 20, 19);
        nextFromRange_short_short_helper(10, 10, 10);
        nextFromRange_short_short_helper(-20, -10, -11);
        nextFromRange_short_short_helper(-20, -20, -20);
        nextFromRange_short_short_helper(0, 0, 0);
        nextFromRange_short_short_helper(0, 10, 9);
        nextFromRange_short_short_helper(-5, 0, -4);
        nextFromRange_short_short_helper(-5, 10, 4);
        nextFromRange_short_short_helper(-10, 5, -1);
        try {
            P.nextFromRange((short) 5, (short) -10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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

    private static void nextFromRange_int_int_helper(int a, int b, int output) {
        aeq(P.nextFromRange(a, b), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_int_int() {
        nextFromRange_int_int_helper(10, 20, 19);
        nextFromRange_int_int_helper(10, 10, 10);
        nextFromRange_int_int_helper(-20, -10, -11);
        nextFromRange_int_int_helper(-20, -20, -20);
        nextFromRange_int_int_helper(0, 0, 0);
        nextFromRange_int_int_helper(0, 10, 9);
        nextFromRange_int_int_helper(-5, 0, -4);
        nextFromRange_int_int_helper(-5, 10, 4);
        nextFromRange_int_int_helper(-10, 5, -1);
        try {
            P.nextFromRange(5, -10);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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

    private static void nextFromRange_long_long_helper(long a, long b, long output) {
        aeq(P.nextFromRange(a, b), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_long_long() {
        nextFromRange_long_long_helper(10L, 20L, 19L);
        nextFromRange_long_long_helper(10L, 10L, 10L);
        nextFromRange_long_long_helper(-20L, -10L, -11L);
        nextFromRange_long_long_helper(-20L, -20L, -20L);
        nextFromRange_long_long_helper(0L, 0L, 0L);
        nextFromRange_long_long_helper(0L, 10L, 9L);
        nextFromRange_long_long_helper(-5L, 0L, -4L);
        nextFromRange_long_long_helper(-5L, 10L, 4L);
        nextFromRange_long_long_helper(-10L, 5L, -1L);
        try {
            P.nextFromRange(5L, -10L);
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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

    private static void nextFromRange_BigInteger_BigInteger_helper(int a, int b, int output) {
        aeq(P.nextFromRange(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_BigInteger_BigInteger() {
        nextFromRange_BigInteger_BigInteger_helper(10, 20, 19);
        nextFromRange_BigInteger_BigInteger_helper(10, 10, 10);
        nextFromRange_BigInteger_BigInteger_helper(-20, -10, -11);
        nextFromRange_BigInteger_BigInteger_helper(-20, -20, -20);
        nextFromRange_BigInteger_BigInteger_helper(0, 0, 0);
        nextFromRange_BigInteger_BigInteger_helper(0, 10, 9);
        nextFromRange_BigInteger_BigInteger_helper(-5, 0, -4);
        nextFromRange_BigInteger_BigInteger_helper(-5, 10, 4);
        nextFromRange_BigInteger_BigInteger_helper(-10, 5, -1);
        P.reset();
        try {
            P.nextFromRange(BigInteger.valueOf(5), BigInteger.valueOf(-10));
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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

    private static void nextFromRange_char_char_helper(char a, char b, char output) {
        aeq(P.nextFromRange(a, b), output);
        P.reset();
    }

    @Test
    public void testNextFromRange_char_char() {
        nextFromRange_char_char_helper('a', 'z', 'j');
        nextFromRange_char_char_helper('a', 'a', 'a');
        nextFromRange_char_char_helper('!', '9', '*');
        P.reset();
        try {
            P.nextFromRange('a', 'A');
            fail();
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
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
                "jjdsvyfdrhvtvyvrgpbwvvkpzexlncshjewdmfsefadxcfpostgwymkoqiyyeyotsdplrqjvsofgpjgavgtpttfdwftlszplpbd" +
                "rxgxsfvxrizclhuiwuagojhcctlgs"
        );
        range_char_char_helper(
                'a',
                'a',
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );
        range_char_char_helper(
                '!',
                '9',
                "**$369&$2(646962'0\"766+0%8,.#3(*%7$-&3%&!$8#&0/34'79-+/1)99%9/43$0,21*63/&'0*'!6'4044&$7&4,30,0\"$" +
                "28'83&682)#,(5)75!'/*(##4,'39$8"
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
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfIntegers(sample), sampleMean);
    }

    private static void nextPositiveIntGeometric_helper(int mean, int output) {
        aeq(P.withScale(mean).nextPositiveIntGeometric(), output);
        P.reset();
    }

    private static void nextPositiveIntGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nextPositiveIntGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextPositiveIntGeometric() {
        nextPositiveIntGeometric_helper(2, 4);
        nextPositiveIntGeometric_helper(3, 5);
        nextPositiveIntGeometric_helper(4, 7);
        nextPositiveIntGeometric_helper(5, 5);
        nextPositiveIntGeometric_helper(10, 36);
        nextPositiveIntGeometric_helper(100, 147);
        nextPositiveIntGeometric_fail_helper(1);
        nextPositiveIntGeometric_fail_helper(0);
        nextPositiveIntGeometric_fail_helper(-1);
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
                "[4, 3, 10, 3, 3, 1, 2, 4, 1, 1, 3, 3, 3, 1, 3, 1, 1, 2, 2, 1]",
                "{1=499125, 2=250897, 3=124849, 4=62518, 5=31407, 6=15634, 7=7825, 8=3926, 9=1896, 10=956}",
                2.0008359999800347
        );
        positiveIntegersGeometric_helper(
                3,
                "[5, 6, 6, 5, 3, 6, 1, 2, 3, 2, 4, 7, 2, 3, 1, 2, 1, 2, 1, 4]",
                "{1=333813, 2=221150, 3=148025, 4=98992, 5=66270, 6=43747, 7=29389, 8=19567, 9=12958, 10=8571}",
                3.002096999989928
        );
        positiveIntegersGeometric_helper(
                4,
                "[7, 10, 7, 7, 4, 10, 1, 3, 3, 2, 7, 8, 2, 3, 1, 2, 1, 2, 1, 8]",
                "{1=250407, 2=187060, 3=139994, 4=105560, 5=79154, 6=58963, 7=44920, 8=33524, 9=25356, 10=18834}",
                4.0033679999901475
        );
        positiveIntegersGeometric_helper(
                5,
                "[5, 4, 18, 8, 3, 1, 2, 1, 2, 6, 7, 18, 6, 3, 8, 15, 1, 9, 2, 5]",
                "{1=200194, 2=160489, 3=127708, 4=101606, 5=82008, 6=65900, 7=52157, 8=41827, 9=33413, 10=26877}",
                5.004360000008482
        );
        positiveIntegersGeometric_helper(
                10,
                "[36, 10, 10, 8, 32, 21, 2, 6, 1, 18, 19, 1, 11, 9, 4, 4, 1, 2, 4, 6]",
                "{1=99758, 2=90376, 3=81079, 4=73085, 5=65513, 6=59012, 7=53321, 8=47369, 9=43229, 10=38339}",
                9.996188000005418
        );
        positiveIntegersGeometric_helper(
                100,
                "[147, 1, 65, 34, 32, 144, 35, 117, 27, 85, 9, 63, 11, 16, 1, 136, 35, 290, 126, 65]",
                "{1=10012, 2=9821, 3=9817, 5=9658, 4=9642, 6=9476, 7=9451, 8=9361, 10=9177, 11=9064}",
                99.9640719999968
        );
        positiveIntegersGeometric_fail_helper(1);
        positiveIntegersGeometric_fail_helper(0);
        positiveIntegersGeometric_fail_helper(-1);
    }

    private static void nextNegativeIntGeometric_helper(int mean, int output) {
        aeq(P.withScale(mean).nextNegativeIntGeometric(), output);
        P.reset();
    }

    private static void nextNegativeIntGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNegativeIntGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNegativeIntGeometric() {
        nextNegativeIntGeometric_helper(2, -4);
        nextNegativeIntGeometric_helper(3, -5);
        nextNegativeIntGeometric_helper(4, -7);
        nextNegativeIntGeometric_helper(5, -5);
        nextNegativeIntGeometric_helper(10, -36);
        nextNegativeIntGeometric_helper(100, -147);
        nextNegativeIntGeometric_fail_helper(1);
        nextNegativeIntGeometric_fail_helper(0);
        nextNegativeIntGeometric_fail_helper(-1);
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
                "[-4, -3, -10, -3, -3, -1, -2, -4, -1, -1, -3, -3, -3, -1, -3, -1, -1, -2, -2, -1]",
                "{-1=499125, -2=250897, -3=124849, -4=62518, -5=31407, -6=15634, -7=7825, -8=3926, -9=1896, -10=956}",
                -2.0008359999800347
        );
        negativeIntegersGeometric_helper(
                3,
                "[-5, -6, -6, -5, -3, -6, -1, -2, -3, -2, -4, -7, -2, -3, -1, -2, -1, -2, -1, -4]",
                "{-1=333813, -2=221150, -3=148025, -4=98992, -5=66270, -6=43747, -7=29389, -8=19567, -9=12958," +
                " -10=8571}",
                -3.002096999989928
        );
        negativeIntegersGeometric_helper(
                4,
                "[-7, -10, -7, -7, -4, -10, -1, -3, -3, -2, -7, -8, -2, -3, -1, -2, -1, -2, -1, -8]",
                "{-1=250407, -2=187060, -3=139994, -4=105560, -5=79154, -6=58963, -7=44920, -8=33524, -9=25356," +
                " -10=18834}",
                -4.0033679999901475
        );
        negativeIntegersGeometric_helper(
                5,
                "[-5, -4, -18, -8, -3, -1, -2, -1, -2, -6, -7, -18, -6, -3, -8, -15, -1, -9, -2, -5]",
                "{-1=200194, -2=160489, -3=127708, -4=101606, -5=82008, -6=65900, -7=52157, -8=41827, -9=33413," +
                " -10=26877}",
                -5.004360000008482
        );
        negativeIntegersGeometric_helper(
                10,
                "[-36, -10, -10, -8, -32, -21, -2, -6, -1, -18, -19, -1, -11, -9, -4, -4, -1, -2, -4, -6]",
                "{-1=99758, -2=90376, -3=81079, -4=73085, -5=65513, -6=59012, -7=53321, -8=47369, -9=43229," +
                " -10=38339}",
                -9.996188000005418
        );
        negativeIntegersGeometric_helper(
                100,
                "[-147, -1, -65, -34, -32, -144, -35, -117, -27, -85, -9, -63, -11, -16, -1, -136, -35, -290, -126," +
                " -65]",
                "{-1=10012, -2=9821, -3=9817, -5=9658, -4=9642, -6=9476, -7=9451, -8=9361, -10=9177, -11=9064}",
                -99.9640719999968
        );
        negativeIntegersGeometric_fail_helper(1);
        negativeIntegersGeometric_fail_helper(0);
        negativeIntegersGeometric_fail_helper(-1);
    }

    private static void nextNaturalIntGeometric_helper(int mean, int output) {
        aeq(P.withScale(mean).nextNaturalIntGeometric(), output);
        P.reset();
    }

    private static void nextNaturalIntGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNaturalIntGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNaturalIntGeometric() {
        nextNaturalIntGeometric_helper(1, 3);
        nextNaturalIntGeometric_helper(2, 4);
        nextNaturalIntGeometric_helper(3, 6);
        nextNaturalIntGeometric_helper(4, 4);
        nextNaturalIntGeometric_helper(5, 5);
        nextNaturalIntGeometric_helper(10, 36);
        nextNaturalIntGeometric_helper(100, 149);
        nextNaturalIntGeometric_fail_helper(0);
        nextNaturalIntGeometric_fail_helper(-1);
        nextNaturalIntGeometric_fail_helper(Integer.MAX_VALUE);
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
                "[3, 2, 9, 2, 2, 0, 1, 3, 0, 0, 2, 2, 2, 0, 2, 0, 0, 1, 1, 0]",
                "{0=499125, 1=250897, 2=124849, 3=62518, 4=31407, 5=15634, 6=7825, 7=3926, 8=1896, 9=956}",
                1.0008359999977228
        );
        naturalIntegersGeometric_helper(
                2,
                "[4, 5, 5, 4, 2, 5, 0, 1, 2, 1, 3, 6, 1, 2, 0, 1, 0, 1, 0, 3]",
                "{0=333813, 1=221150, 2=148025, 3=98992, 4=66270, 5=43747, 6=29389, 7=19567, 8=12958, 9=8571}",
                2.0020969999891216
        );
        naturalIntegersGeometric_helper(
                3,
                "[6, 9, 6, 6, 3, 9, 0, 2, 2, 1, 6, 7, 1, 2, 0, 1, 0, 1, 0, 7]",
                "{0=250407, 1=187060, 2=139994, 3=105560, 4=79154, 5=58963, 6=44920, 7=33524, 8=25356, 9=18834}",
                3.003367999991497
        );
        naturalIntegersGeometric_helper(
                4,
                "[4, 3, 17, 7, 2, 0, 1, 0, 1, 5, 6, 17, 5, 2, 7, 14, 0, 8, 1, 4]",
                "{0=200194, 1=160489, 2=127708, 3=101606, 4=82008, 5=65900, 6=52157, 7=41827, 8=33413, 9=26877}",
                4.004359999991779
        );
        naturalIntegersGeometric_helper(
                5,
                "[5, 7, 23, 9, 3, 0, 1, 0, 1, 5, 8, 22, 5, 2, 7, 18, 0, 10, 1, 5]",
                "{0=166887, 1=139197, 2=115545, 3=96038, 4=80312, 5=66813, 6=55942, 7=46416, 8=38391, 9=32378}",
                5.005650000005037
        );
        naturalIntegersGeometric_helper(
                10,
                "[36, 12, 10, 8, 32, 24, 1, 7, 0, 17, 20, 0, 10, 8, 3, 4, 0, 1, 3, 6]",
                "{0=90519, 1=82798, 2=75630, 3=68355, 4=62062, 5=56573, 6=51318, 7=46453, 8=42422, 9=38243}",
                9.996028000004106
        );
        naturalIntegersGeometric_helper(
                100,
                "[149, 0, 65, 33, 31, 144, 34, 116, 26, 84, 8, 63, 10, 15, 0, 137, 36, 289, 126, 65]",
                "{0=9916, 2=9740, 1=9715, 4=9630, 3=9504, 5=9377, 6=9328, 7=9252, 9=9073, 8=9025}",
                99.96387799999758
        );
        naturalIntegersGeometric_fail_helper(0);
        naturalIntegersGeometric_fail_helper(-1);
        naturalIntegersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void nextNonzeroIntGeometric_helper(int mean, int output) {
        aeq(P.withScale(mean).nextNonzeroIntGeometric(), output);
        P.reset();
    }

    private static void nextNonzeroIntGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNonzeroIntGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNonzeroIntGeometric() {
        nextNonzeroIntGeometric_helper(2, 4);
        nextNonzeroIntGeometric_helper(3, 5);
        nextNonzeroIntGeometric_helper(4, 7);
        nextNonzeroIntGeometric_helper(5, 5);
        nextNonzeroIntGeometric_helper(10, 36);
        nextNonzeroIntGeometric_helper(100, -147);
        nextNonzeroIntGeometric_fail_helper(1);
        nextNonzeroIntGeometric_fail_helper(0);
        nextNonzeroIntGeometric_fail_helper(-1);
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
                "[4, 2, 9, 2, -2, 2, -3, 1, 2, 2, -2, -3, 1, 1, -1, 2, 2, -2, 1, -1]",
                "{1=250216, -1=249853, 2=125042, -2=125020, 3=62554, -3=62266, -4=31431, 4=31320, 5=15667, -5=15448}",
                0.0034480000000000253,
                1.9994539999798795
        );
        nonzeroIntegersGeometric_helper(
                3,
                "[5, 5, 5, -5, 2, -6, 2, 2, 1, -4, 6, -1, -2, -2, -2, 4, -2, 7, 7, 2]",
                "{1=166701, -1=166029, -2=111049, 2=110952, -3=74329, 3=74260, 4=49329, -4=49314, 5=33182, -5=32740}",
                -0.0012719999999999216,
                3.00329999998982
        );
        nonzeroIntegersGeometric_helper(
                4,
                "[7, 9, 6, -6, 3, -9, 3, 2, 1, -6, 7, -1, -2, -2, -2, 8, -4, 8, 14, 5]",
                "{1=125241, -1=124537, -2=93626, 2=93467, -3=70571, 3=70056, 4=53060, -4=52604, -5=39246, 5=39217}",
                -8.369999999999692E-4,
                4.00555699999019
        );
        nonzeroIntegersGeometric_helper(
                5,
                "[5, 4, 18, 7, -3, -2, -2, 6, 6, 18, -5, -3, 8, -14, -1, -8, 1, -4, -7, -1]",
                "{1=100071, -1=99831, -2=80195, 2=79602, -3=64039, 3=63577, 4=51648, -4=50922, 5=41189, -5=40794}",
                0.004184999999999686,
                5.006127000008418
        );
        nonzeroIntegersGeometric_helper(
                10,
                "[36, -9, 9, 7, -31, -20, -1, -6, 1, -18, -19, 1, -10, -8, -3, -4, -2, 4, -5, -12]",
                "{1=49977, -1=49798, -2=45286, 2=45037, 3=40770, -3=40176, 4=36548, -4=36321, 5=33048, -5=32530}",
                0.011239999999999537,
                9.999258000005128
        );
        nonzeroIntegersGeometric_helper(
                100,
                "[-147, 1, -64, -34, 31, -143, -35, -116, 26, 84, 8, 62, -10, -15, -136, 34, 289, -126, 64, -89]",
                "{-1=4973, 3=4970, 2=4962, -2=4930, 1=4908, 4=4872, -4=4839, -3=4837, 6=4820, -5=4817}",
                -0.14496700000000257,
                99.97359300000046
        );
        nonzeroIntegersGeometric_fail_helper(1);
        nonzeroIntegersGeometric_fail_helper(0);
        nonzeroIntegersGeometric_fail_helper(-1);
    }

    private static void nextIntGeometric_helper(int mean, int output) {
        aeq(P.withScale(mean).nextIntGeometric(), output);
        P.reset();
    }

    private static void nextIntGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nextIntGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextIntGeometric() {
        nextIntGeometric_helper(1, 3);
        nextIntGeometric_helper(2, 4);
        nextIntGeometric_helper(3, 6);
        nextIntGeometric_helper(4, 4);
        nextIntGeometric_helper(5, 5);
        nextIntGeometric_helper(10, 36);
        nextIntGeometric_helper(100, -149);
        nextIntGeometric_fail_helper(0);
        nextIntGeometric_fail_helper(-1);
        nextIntGeometric_fail_helper(Integer.MAX_VALUE);
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
                "[3, 1, 8, 1, -1, 1, -2, 0, 1, 1, -1, -2, 0, 0, 0, 1, 1, -1, 0, 0]",
                "{0=500069, 1=125042, -1=125020, 2=62554, -2=62266, -3=31431, 3=31320, 4=15667, -4=15448, -5=7835}",
                0.0025060000000000906,
                1.0003039999976984
        );
        integersGeometric_helper(
                2,
                "[4, 4, 4, -4, 1, -5, 1, 1, 0, -3, 5, 0, -1, -1, -1, 3, -1, 6, 6, 1]",
                "{0=332730, -1=111049, 1=110952, -2=74329, 2=74260, 3=49329, -3=49314, 4=33182, -4=32740, 5=22195}",
                -0.0018900000000000037,
                2.002686999989137
        );
        integersGeometric_helper(
                3,
                "[6, 8, 5, -5, 2, -8, 2, 1, 0, -5, 6, 0, -1, -1, -1, 7, -3, 7, 13, 4]",
                "{0=249778, -1=93626, 1=93467, -2=70571, 2=70056, 3=53060, -3=52604, -4=39246, 4=39217, 5=29959}",
                -0.0014549999999999997,
                3.00388599999155
        );
        integersGeometric_helper(
                4,
                "[4, 3, 17, 6, -2, -1, -1, 5, 5, 17, -4, -2, 7, -13, 0, -7, 0, -3, -6, 0]",
                "{0=199902, -1=80195, 1=79602, -2=64039, 2=63577, 3=51648, -3=50922, 4=41189, -4=40794, 5=33021}",
                0.0036169999999999917,
                3.9981839999915647
        );
        integersGeometric_helper(
                5,
                "[5, 6, 22, 8, -2, -1, -1, 5, 7, 21, -4, -2, 7, -17, 0, -9, 0, -4, -7, 0]",
                "{0=166687, -1=69595, 1=69045, -2=57871, 2=57477, 3=48432, -3=48158, 4=40223, -4=40079, 5=33890}",
                0.003408000000000304,
                4.9993930000049955
        );
        integersGeometric_helper(
                10,
                "[36, -11, 9, 7, -31, -23, 0, -6, 0, -17, -19, 0, -9, -7, -2, -3, -1, 3, -5, -12]",
                "{0=90357, -1=41678, 1=41612, 2=37671, -2=37332, 3=34239, -3=34123, 4=31404, -4=30761, -5=28276}",
                0.010864000000000018,
                9.983633000004563
        );
        integersGeometric_helper(
                100,
                "[-149, 0, -64, -33, 30, -143, -34, -115, 25, 83, 7, 62, -9, -14, -137, 35, 288, -126, 64, -89]",
                "{0=9763, 1=4918, 2=4912, -1=4901, 3=4853, -3=4803, -4=4799, 5=4773, -2=4772, 4=4712}",
                -0.1466660000000059,
                99.86606600000295
        );
        integersGeometric_fail_helper(0);
        integersGeometric_fail_helper(-1);
        integersGeometric_fail_helper(Integer.MAX_VALUE);
    }

    private static void nextIntGeometricFromRangeUp_helper(int mean, int a, int output) {
        aeq(P.withScale(mean).nextIntGeometricFromRangeUp(a), output);
        P.reset();
    }

    private static void nextIntGeometricFromRangeUp_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).nextIntGeometricFromRangeUp(a);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextIntGeometricFromRangeUp() {
        nextIntGeometricFromRangeUp_helper(3, 2, 5);
        nextIntGeometricFromRangeUp_helper(10, 2, 33);
        nextIntGeometricFromRangeUp_helper(11, 10, 13);
        nextIntGeometricFromRangeUp_helper(20, 10, 46);
        nextIntGeometricFromRangeUp_helper(-9, -10, -7);
        nextIntGeometricFromRangeUp_helper(0, -10, 26);
        nextIntGeometricFromRangeUp_helper(Integer.MAX_VALUE, Integer.MAX_VALUE - 1, 2147483646);
        nextIntGeometricFromRangeUp_helper(Integer.MIN_VALUE + 1, Integer.MIN_VALUE, -2147483645);
        nextIntGeometricFromRangeUp_fail_helper(5, 5);
        nextIntGeometricFromRangeUp_fail_helper(4, 5);
        nextIntGeometricFromRangeUp_fail_helper(Integer.MAX_VALUE - 5, -10);
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
                "[5, 4, 11, 4, 4, 2, 3, 5, 2, 2, 4, 4, 4, 2, 4, 2, 2, 3, 3, 2]",
                "{2=499125, 3=250897, 4=124849, 5=62518, 6=31407, 7=15634, 8=7825, 9=3926, 10=1896, 11=956}",
                3.000835999968773
        );
        rangeUpGeometric_helper(
                10,
                2,
                "[33, 11, 11, 7, 31, 20, 3, 7, 2, 19, 15, 2, 12, 8, 5, 5, 2, 3, 4, 7]",
                "{2=110949, 3=98973, 4=87810, 5=78512, 6=69401, 7=61358, 8=54691, 9=48553, 10=43415, 11=38254}",
                9.996049000016834
        );
        rangeUpGeometric_helper(
                11,
                10,
                "[13, 12, 19, 12, 12, 10, 11, 13, 10, 10, 12, 12, 12, 10, 12, 10, 10, 11, 11, 10]",
                "{10=499125, 11=250897, 12=124849, 13=62518, 14=31407, 15=15634, 16=7825, 17=3926, 18=1896, 19=956}",
                11.000835999897873
        );
        rangeUpGeometric_helper(
                20,
                10,
                "[46, 22, 20, 18, 42, 34, 11, 17, 10, 27, 30, 10, 20, 18, 13, 14, 10, 11, 13, 16]",
                "{10=90519, 11=82798, 12=75630, 13=68355, 14=62062, 15=56573, 16=51318, 17=46453, 18=42422, 19=38243}",
                19.99602800000434
        );
        rangeUpGeometric_helper(
                -9,
                -10,
                "[-7, -8, -1, -8, -8, -10, -9, -7, -10, -10, -8, -8, -8, -10, -8, -10, -10, -9, -9, -10]",
                "{-10=499125, -9=250897, -8=124849, -7=62518, -6=31407, -5=15634, -4=7825, -3=3926, -2=1896, -1=956}",
                -8.999163999953181
        );
        rangeUpGeometric_helper(
                0,
                -10,
                "[26, 2, 0, -2, 22, 14, -9, -3, -10, 7, 10, -10, 0, -2, -7, -6, -10, -9, -7, -4]",
                "{-10=90519, -9=82798, -8=75630, -7=68355, -6=62062, -5=56573, -4=51318, -3=46453, -2=42422," +
                " -1=38243}",
                -0.003971999999995211
        );
        rangeUpGeometric_helper(
                Integer.MAX_VALUE,
                Integer.MAX_VALUE - 1,
                "[2147483646, 2147483647, 2147483646, 2147483646, 2147483646, 2147483646, 2147483646, 2147483647," +
                " 2147483647, 2147483646, 2147483647, 2147483646, 2147483646, 2147483647, 2147483646, 2147483647," +
                " 2147483646, 2147483646, 2147483647, 2147483646]",
                "{2147483646=666317, 2147483647=333683}",
                2.1474836463261979E9
        );
        rangeUpGeometric_helper(
                Integer.MIN_VALUE + 1,
                Integer.MIN_VALUE,
                "[-2147483645, -2147483646, -2147483639, -2147483646, -2147483646, -2147483648, -2147483647," +
                " -2147483645, -2147483648, -2147483648, -2147483646, -2147483646, -2147483646, -2147483648," +
                " -2147483646, -2147483648, -2147483648, -2147483647, -2147483647, -2147483648]",
                "{-2147483648=499125, -2147483647=250897, -2147483646=124849, -2147483645=62518, -2147483644=31407," +
                " -2147483643=15634, -2147483642=7825, -2147483641=3926, -2147483640=1896, -2147483639=956}",
                -2.1474836470149984E9
        );
        rangeUpGeometric_fail_helper(5, 5);
        rangeUpGeometric_fail_helper(4, 5);
        rangeUpGeometric_fail_helper(Integer.MAX_VALUE - 5, -10);
    }

    private static void nextIntGeometricFromRangeDown_helper(int mean, int a, int output) {
        aeq(P.withScale(mean).nextIntGeometricFromRangeDown(a), output);
        P.reset();
    }

    private static void nextIntGeometricFromRangeDown_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).nextIntGeometricFromRangeDown(a);
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextIntGeometricFromRangeDown() {
        nextIntGeometricFromRangeDown_helper(0, 2, -2);
        nextIntGeometricFromRangeDown_helper(-5, 2, -4);
        nextIntGeometricFromRangeDown_helper(5, 10, 5);
        nextIntGeometricFromRangeDown_helper(0, 10, -26);
        nextIntGeometricFromRangeDown_helper(-11, -10, -13);
        nextIntGeometricFromRangeDown_helper(-20, -10, -46);
        nextIntGeometricFromRangeDown_helper(Integer.MAX_VALUE - 1, Integer.MAX_VALUE, 2147483644);
        nextIntGeometricFromRangeDown_helper(Integer.MIN_VALUE, Integer.MIN_VALUE + 1, -2147483647);
        nextIntGeometricFromRangeDown_fail_helper(5, 5);
        nextIntGeometricFromRangeDown_fail_helper(6, 5);
        nextIntGeometricFromRangeDown_fail_helper(-5, Integer.MIN_VALUE);
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
                "[-2, -3, -3, -2, 0, -3, 2, 1, 0, 1, -1, -4, 1, 0, 2, 1, 2, 1, 2, -1]",
                "{2=333813, 1=221150, 0=148025, -1=98992, -2=66270, -3=43747, -4=29389, -5=19567, -6=12958, -7=8571}",
                -0.0020969999999987214
        );
        rangeDownGeometric_helper(
                -5,
                2,
                "[-4, -7, -34, -12, -2, 2, 1, 2, 0, -5, -11, -30, -4, -1, -10, -23, 1, -10, 1, -10]",
                "{2=125138, 1=109518, 0=95496, -1=83848, -2=73359, -3=63701, -4=56011, -5=48897, -6=43099, -7=37459}",
                -5.0074679999951055
        );
        rangeDownGeometric_helper(
                5,
                10,
                "[5, 3, -13, 1, 7, 10, 9, 10, 9, 5, 2, -12, 5, 8, 3, -8, 10, 0, 9, 5]",
                "{10=166887, 9=139197, 8=115545, 7=96038, 6=80312, 5=66813, 4=55942, 3=46416, 2=38391, 1=32378}",
                4.994349999991597
        );
        rangeDownGeometric_helper(
                0,
                10,
                "[-26, -2, 0, 2, -22, -14, 9, 3, 10, -7, -10, 10, 0, 2, 7, 6, 10, 9, 7, 4]",
                "{10=90519, 9=82798, 8=75630, 7=68355, 6=62062, 5=56573, 4=51318, 3=46453, 2=42422, 1=38243}",
                0.003971999999995211
        );
        rangeDownGeometric_helper(
                -11,
                -10,
                "[-13, -12, -19, -12, -12, -10, -11, -13, -10, -10, -12, -12, -12, -10, -12, -10, -10, -11, -11, -10]",
                "{-10=499125, -11=250897, -12=124849, -13=62518, -14=31407, -15=15634, -16=7825, -17=3926, -18=1896," +
                " -19=956}",
                -11.000835999897873
        );
        rangeDownGeometric_helper(
                -20,
                -10,
                "[-46, -22, -20, -18, -42, -34, -11, -17, -10, -27, -30, -10, -20, -18, -13, -14, -10, -11, -13, -16]",
                "{-10=90519, -11=82798, -12=75630, -13=68355, -14=62062, -15=56573, -16=51318, -17=46453, -18=42422," +
                " -19=38243}",
                -19.99602800000434
        );
        rangeDownGeometric_helper(
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE,
                "[2147483644, 2147483645, 2147483638, 2147483645, 2147483645, 2147483647, 2147483646, 2147483644," +
                " 2147483647, 2147483647, 2147483645, 2147483645, 2147483645, 2147483647, 2147483645, 2147483647," +
                " 2147483647, 2147483646, 2147483646, 2147483647]",
                "{2147483647=499125, 2147483646=250897, 2147483645=124849, 2147483644=62518, 2147483643=31407," +
                " 2147483642=15634, 2147483641=7825, 2147483640=3926, 2147483639=1896, 2147483638=956}",
                2.147483646013541E9
        );
        rangeDownGeometric_helper(
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                "[-2147483647, -2147483648, -2147483647, -2147483647, -2147483647, -2147483647, -2147483647," +
                " -2147483648, -2147483648, -2147483647, -2147483648, -2147483647, -2147483647, -2147483648," +
                " -2147483647, -2147483648, -2147483647, -2147483647, -2147483648, -2147483647]",
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
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        List<BigInteger> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfBigIntegers(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.abs().bitLength(), sample))), bitSizeMean);
    }

    private static void nextPositiveBigInteger_helper(int mean, @NotNull String output) {
        aeq(P.withScale(mean).nextPositiveBigInteger(), output);
        P.reset();
    }

    private static void nextPositiveBigInteger_fail_helper(int mean) {
        try {
            P.withScale(mean).nextPositiveBigInteger();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextPositiveBigInteger() {
        nextPositiveBigInteger_helper(2, "13");
        nextPositiveBigInteger_helper(3, "21");
        nextPositiveBigInteger_helper(4, "101");
        nextPositiveBigInteger_helper(5, "21");
        nextPositiveBigInteger_helper(10, "47968091191");
        nextPositiveBigInteger_helper(100, "94790976865653102300816908025048767680216168");
        nextPositiveBigInteger_fail_helper(1);
        nextPositiveBigInteger_fail_helper(0);
        nextPositiveBigInteger_fail_helper(-1);
    }

    private static void positiveBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).positiveBigIntegers(),
                output,
                topSampleCount,
                sampleMean,
                bitSizeMean
        );
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
        positiveBigIntegers_helper(
                2,
                "[13, 3, 477, 3, 2, 3, 4, 1, 3, 3, 2, 4, 1, 1, 1, 3, 3, 2, 1, 1]",
                "{1=500069, 3=125042, 2=125020, 7=31426, 4=31262, 5=31128, 6=31004, 12=8028, 14=7919, 11=7903}",
                114.05128999981362,
                1.9994539999798795
        );
        positiveBigIntegers_helper(
                3,
                "[21, 21, 31, 26, 3, 36, 3, 3, 1, 10, 61, 1, 2, 2, 2, 9, 2, 117, 111, 3]",
                "{1=332729, 2=111051, 3=110952, 6=37298, 7=37247, 4=37029, 5=37013, 11=12459, 14=12431, 9=12346}",
                42053.996647257176,
                3.00330199998982
        );
        positiveBigIntegers_helper(
                4,
                "[101, 477, 63, 42, 7, 343, 5, 3, 1, 50, 125, 1, 2, 2, 2, 220, 10, 240, 12106, 19]",
                "{1=249786, 2=93630, 3=93458, 4=35330, 6=35235, 5=35059, 7=34998, 11=13349, 9=13332, 15=13286}",
                3.923720245917525E8,
                4.005570999990192
        );
        positiveBigIntegers_helper(
                5,
                "[21, 13, 207646, 125, 4, 2, 2, 41, 53, 219224, 22, 6, 171, 9881, 1, 192, 1, 12, 70, 1]",
                "{1=199913, 2=80195, 3=79601, 6=32178, 4=31857, 5=31838, 7=31756, 11=12990, 9=12954, 15=12943}",
                8.95688013930559E12,
                5.006042000008429
        );
        positiveBigIntegers_helper(
                10,
                "[47968091191, 209, 348, 117, 1719440537, 956748, 1, 60, 1, 131900, 437219, 1, 566, 245, 6, 8, 2, 13," +
                " 30, 3272]",
                "{1=99896, 2=45185, 3=45008, 5=20574, 7=20224, 6=20173, 4=20083, 13=9246, 9=9174, 14=9159}",
                4.456452305288997E35,
                9.998937000005219
        );
        positiveBigIntegers_helper(
                100,
                "[94790976865653102300816908025048767680216168, 3762255186221726870, 5994570771, 823422155," +
                " 10161754415810092830165715486885560643885805, 1484539043, 53285321364040890158634366042166836," +
                " 7412492, 14380290507177291615829493, 51, 2378861914519634593, 456, 16636," +
                " 53988143125609611862402328554695182710088, 1233903230," +
                " 729330608188823656079318880504880068799735087660742104048519512109274590562188282621450," +
                " 1772557890515702532927646423272573535, 2528945640266242272, 267516009518392367893913935, 5791822]",
                "{1=9825, 3=5010, 2=5000, 5=2554, 7=2419, 4=2398, 6=2371, 12=1269, 10=1255, 13=1232}",
                Double.POSITIVE_INFINITY,
                99.9771549999987
        );
        positiveBigIntegers_fail_helper(1);
        positiveBigIntegers_fail_helper(0);
        positiveBigIntegers_fail_helper(-1);
    }

    private static void nextNegativeBigInteger_helper(int mean, @NotNull String output) {
        aeq(P.withScale(mean).nextNegativeBigInteger(), output);
        P.reset();
    }

    private static void nextNegativeBigInteger_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNegativeBigInteger();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNegativeBigInteger() {
        nextNegativeBigInteger_helper(2, "-13");
        nextNegativeBigInteger_helper(3, "-21");
        nextNegativeBigInteger_helper(4, "-101");
        nextNegativeBigInteger_helper(5, "-21");
        nextNegativeBigInteger_helper(10, "-47968091191");
        nextNegativeBigInteger_helper(100, "-94790976865653102300816908025048767680216168");
        nextNegativeBigInteger_fail_helper(1);
        nextNegativeBigInteger_fail_helper(0);
        nextNegativeBigInteger_fail_helper(-1);
    }

    private static void negativeBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).negativeBigIntegers(),
                output,
                topSampleCount,
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
                "[-13, -3, -477, -3, -2, -3, -4, -1, -3, -3, -2, -4, -1, -1, -1, -3, -3, -2, -1, -1]",
                "{-1=500069, -3=125042, -2=125020, -7=31426, -4=31262, -5=31128, -6=31004, -12=8028, -14=7919," +
                " -11=7903}",
                -114.05128999981362,
                1.9994539999798795
        );
        negativeBigIntegers_helper(
                3,
                "[-21, -21, -31, -26, -3, -36, -3, -3, -1, -10, -61, -1, -2, -2, -2, -9, -2, -117, -111, -3]",
                "{-1=332729, -2=111051, -3=110952, -6=37298, -7=37247, -4=37029, -5=37013, -11=12459, -14=12431," +
                " -9=12346}",
                -42053.996647257176,
                3.00330199998982
        );
        negativeBigIntegers_helper(
                4,
                "[-101, -477, -63, -42, -7, -343, -5, -3, -1, -50, -125, -1, -2, -2, -2, -220, -10, -240, -12106," +
                " -19]",
                "{-1=249786, -2=93630, -3=93458, -4=35330, -6=35235, -5=35059, -7=34998, -11=13349, -9=13332," +
                " -15=13286}",
                -3.923720245917525E8,
                4.005570999990192
        );
        negativeBigIntegers_helper(
                5,
                "[-21, -13, -207646, -125, -4, -2, -2, -41, -53, -219224, -22, -6, -171, -9881, -1, -192, -1, -12," +
                " -70, -1]",
                "{-1=199913, -2=80195, -3=79601, -6=32178, -4=31857, -5=31838, -7=31756, -11=12990, -9=12954," +
                " -15=12943}",
                -8.95688013930559E12,
                5.006042000008429
        );
        negativeBigIntegers_helper(
                10,
                "[-47968091191, -209, -348, -117, -1719440537, -956748, -1, -60, -1, -131900, -437219, -1, -566," +
                " -245, -6, -8, -2, -13, -30, -3272]",
                "{-1=99896, -2=45185, -3=45008, -5=20574, -7=20224, -6=20173, -4=20083, -13=9246, -9=9174, -14=9159}",
                -4.456452305288997E35,
                9.998937000005219
        );
        negativeBigIntegers_helper(
                100,
                "[-94790976865653102300816908025048767680216168, -3762255186221726870, -5994570771, -823422155," +
                " -10161754415810092830165715486885560643885805, -1484539043, -53285321364040890158634366042166836," +
                " -7412492, -14380290507177291615829493, -51, -2378861914519634593, -456, -16636," +
                " -53988143125609611862402328554695182710088, -1233903230," +
                " -729330608188823656079318880504880068799735087660742104048519512109274590562188282621450," +
                " -1772557890515702532927646423272573535, -2528945640266242272, -267516009518392367893913935," +
                " -5791822]",
                "{-1=9825, -3=5010, -2=5000, -5=2554, -7=2419, -4=2398, -6=2371, -12=1269, -10=1255, -13=1232}",
                Double.NEGATIVE_INFINITY,
                99.9771549999987
        );
        negativeBigIntegers_fail_helper(1);
        negativeBigIntegers_fail_helper(0);
        negativeBigIntegers_fail_helper(-1);
    }

    private static void nextNaturalBigInteger_helper(int mean, @NotNull String output) {
        aeq(P.withScale(mean).nextNaturalBigInteger(), output);
        P.reset();
    }

    private static void nextNaturalBigInteger_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNaturalBigInteger();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNaturalBigInteger() {
        nextNaturalBigInteger_helper(1, "5");
        nextNaturalBigInteger_helper(2, "13");
        nextNaturalBigInteger_helper(3, "37");
        nextNaturalBigInteger_helper(4, "13");
        nextNaturalBigInteger_helper(5, "21");
        nextNaturalBigInteger_helper(10, "47968091191");
        nextNaturalBigInteger_helper(100, "630008861630388057697674146568609443823746152");
        nextNaturalBigInteger_fail_helper(0);
        nextNaturalBigInteger_fail_helper(-1);
        nextNaturalBigInteger_fail_helper(Integer.MAX_VALUE);
    }

    private static void naturalBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).naturalBigIntegers(),
                output,
                topSampleCount,
                sampleMean,
                bitSizeMean
        );
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
        naturalBigIntegers_helper(
                1,
                "[5, 1, 221, 1, 1, 1, 2, 0, 3, 1, 1, 2, 0, 1, 0, 0, 1, 1, 1, 0]",
                "{0=499823, 1=250086, 3=62677, 2=62065, 7=15883, 4=15841, 6=15689, 5=15562, 11=3976, 9=3968}",
                7.527205000038622,
                1.00042899999766
        );
        naturalBigIntegers_helper(
                2,
                "[13, 13, 15, 10, 1, 20, 1, 1, 0, 6, 29, 0, 2, 1, 1, 5, 1, 53, 47, 1]",
                "{0=333328, 1=221250, 2=74321, 3=74204, 6=24839, 5=24776, 7=24739, 4=24646, 11=8423, 8=8278}",
                18553.42636205894,
                2.0026819999891274
        );
        naturalBigIntegers_helper(
                3,
                "[37, 221, 31, 26, 3, 215, 3, 1, 0, 50, 61, 0, 2, 1, 1, 105, 6, 117, 8010, 11]",
                "{0=249917, 1=187110, 2=70224, 3=70017, 7=26517, 4=26460, 6=26427, 5=26386, 11=9973, 12=9950}",
                2.0721683567389777E8,
                3.0050639999915902
        );
        naturalBigIntegers_helper(
                4,
                "[13, 5, 76574, 61, 2, 1, 1, 25, 21, 88152, 14, 2, 67, 5785, 0, 192, 0, 12, 38, 0]",
                "{0=199981, 1=159902, 2=64013, 3=63667, 7=25747, 5=25585, 4=25557, 6=25379, 15=10419, 11=10368}",
                6.063391150606273E12,
                4.006382999991641
        );
        naturalBigIntegers_helper(
                5,
                "[21, 53, 2304798, 233, 2, 1, 1, 25, 117, 1661016, 14, 2, 67, 104780, 0, 576, 0, 28, 70, 0]",
                "{0=166661, 1=138858, 2=57786, 3=57583, 4=24131, 7=24083, 5=24044, 6=23950, 9=10179, 15=10166}",
                7.6975225805449024E16,
                5.008064000005
        );
        naturalBigIntegers_helper(
                10,
                "[47968091191, 593, 348, 117, 1719440537, 4626764, 0, 124, 0, 66364, 437219, 0, 566, 88, 2, 4, 1, 5," +
                " 30, 3272]",
                "{0=90425, 1=83151, 3=37762, 2=37465, 5=17197, 6=17184, 7=17024, 4=16953, 9=7894, 11=7839}",
                6.216950515561165E39,
                9.998042000004142
        );
        naturalBigIntegers_helper(
                100,
                "[630008861630388057697674146568609443823746152, 3762255186221726870, 3847087123, 286551243," +
                " 10161754415810092830165715486885560643885805, 947668131, 32516133929901579644512380725286452," +
                " 3218188, 9544587228718774917004789, 19, 2378861914519634593, 200, 8444," +
                " 141100429057369858509026228057227844842824, 2346614327," +
                " 220427863617519248349539572941206305312698531160014400224142519234934133812366899774702," +
                " 3101785886300618405831453483552918111, 2528945640266242272, 267516009518392367893913935, 3694670]",
                "{1=9886, 0=9752, 3=4895, 2=4740, 4=2448, 6=2435, 7=2434, 5=2416, 14=1221, 9=1189}",
                Double.POSITIVE_INFINITY,
                99.9762349999981
        );
        naturalBigIntegers_fail_helper(0);
        naturalBigIntegers_fail_helper(-1);
        naturalBigIntegers_fail_helper(Integer.MAX_VALUE);
    }

    private static void nextNonzeroBigInteger_helper(int mean, @NotNull String output) {
        aeq(P.withScale(mean).nextNonzeroBigInteger(), output);
        P.reset();
    }

    private static void nextNonzeroBigInteger_fail_helper(int mean) {
        try {
            P.withScale(mean).nextNonzeroBigInteger();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNonzeroBigInteger() {
        nextNonzeroBigInteger_helper(2, "13");
        nextNonzeroBigInteger_helper(3, "21");
        nextNonzeroBigInteger_helper(4, "101");
        nextNonzeroBigInteger_helper(5, "21");
        nextNonzeroBigInteger_helper(10, "-47968091191");
        nextNonzeroBigInteger_helper(100, "94790976865653102300816908025048767680216168");
        nextNonzeroBigInteger_fail_helper(1);
        nextNonzeroBigInteger_fail_helper(0);
        nextNonzeroBigInteger_fail_helper(-1);
    }

    private static void nonzeroBigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).nonzeroBigIntegers(),
                output,
                topSampleCount,
                sampleMean,
                bitSizeMean
        );
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
        nonzeroBigIntegers_helper(
                2,
                "[13, 1, 221, 1, 1, 1, -2, 7, 1, 1, -2, -3, -1, 5, -1, 2, -1, -2, -1, -1]",
                "{1=250554, -1=249443, -2=62808, 3=62414, -3=62365, 2=62172, -7=15986, -5=15888, 5=15650, -6=15633}",
                -51.040697000000215,
                1.9999089999798014
        );
        nonzeroBigIntegers_helper(
                3,
                "[21, 13, -15, 10, 3, -36, -1, -1, 10, -61, -4, -1, -1, 5, -2, -53, 47, 3, -89, -2]",
                "{1=166707, -1=166490, 3=55535, 2=55481, -2=55345, -3=55270, -7=18716, 6=18667, -5=18655, -6=18561}",
                -12560.956458999866,
                3.0034329999897373
        );
        nonzeroBigIntegers_helper(
                4,
                "[101, 221, -31, 26, 3, -215, -3, -1, 114, -61, -4, -1, -1, 105, -6, -117, 8010, 11, -856, -8]",
                "{1=124984, -1=124598, 2=46841, 3=46727, -2=46702, -3=46654, -7=17851, 6=17685, 7=17652, 4=17596}",
                6.9148732007788E7,
                4.007541999990144
        );
        nonzeroBigIntegers_helper(
                5,
                "[21, 13, 76574, -125, -2, -1, 2, 25, -53, -219224, -14, -2, -171, -5785, -320, -28, -38, -8797," +
                " -46, 30]",
                "{1=100089, -1=99842, -2=40214, -3=39991, 2=39913, 3=39839, -7=16152, 7=16022, -5=15990, -6=15923}",
                5.064110348766358E12,
                5.006230000008758
        );
        nonzeroBigIntegers_helper(
                10,
                "[-47968091191, -104, 348, -117, -645698713, -956748, -60, -131900, -437219, 1078, 88, 2, -4, 2, -5," +
                " 14, 1224, 81, 4, -79]",
                "{-1=50380, 1=49812, 3=22663, -3=22545, 2=22409, -2=22339, -5=10288, -7=10249, 5=10208, -6=10181}",
                -2.4957396868152156E35,
                9.995175000005379
        );
        nonzeroBigIntegers_helper(
                100,
                "[94790976865653102300816908025048767680216168, 1456412177008032918, -5994570771, 286551243," +
                " 4586568116177437044781785918723470267390701, -947668131, -32516133929901579644512380725286452," +
                " 3218188, 9544587228718774917004789, 19, -1225940409912787617, 200, -8444," +
                " -53988143125609611862402328554695182710088, 697032318," +
                " 480668989983930335001627756431469648749507012262068245328287523662694842055921594854922," +
                " -1107943892623244596475742893132401247, 1376024135659395296, -117101863095185620958878497, 3694670]",
                "{1=5017, -1=5000, -3=2500, 3=2458, 2=2453, -2=2440, 4=1301, -5=1262, 6=1257, -7=1253}",
                Double.NaN,
                99.9676500000014
        );
        nonzeroBigIntegers_fail_helper(1);
        nonzeroBigIntegers_fail_helper(0);
        nonzeroBigIntegers_fail_helper(-1);
    }

    private static void nextBigInteger_helper(int mean, @NotNull String output) {
        aeq(P.withScale(mean).nextBigInteger(), output);
        P.reset();
    }

    private static void nextBigInteger_fail_helper(int mean) {
        try {
            P.withScale(mean).nextBigInteger();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextBigInteger() {
        nextBigInteger_helper(1, "5");
        nextBigInteger_helper(2, "13");
        nextBigInteger_helper(3, "37");
        nextBigInteger_helper(4, "13");
        nextBigInteger_helper(5, "21");
        nextBigInteger_helper(10, "-47968091191");
        nextBigInteger_helper(100, "630008861630388057697674146568609443823746152");
        nextBigInteger_fail_helper(0);
        nextBigInteger_fail_helper(-1);
        nextBigInteger_fail_helper(Integer.MAX_VALUE);
    }

    private static void bigIntegers_helper(
            int meanBitSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).bigIntegers(),
                output,
                topSampleCount,
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
        bigIntegers_helper(
                1,
                "[5, 0, 221, 0, 1, 0, -2, 3, 0, 1, -1, -1, 0, 0, 1, 0, 0, 0, -1, -1]",
                "{0=499795, -1=125124, 1=124571, -3=31728, -2=31436, 3=31363, 2=31091, -7=7886, 5=7885, 4=7862}",
                -21.962919999999734,
                1.0003699999976585
        );
        bigIntegers_helper(
                2,
                "[13, 5, -7, 6, 1, -20, 0, -1, 6, -29, -2, 0, -1, 3, -1, -21, 31, 1, -57, -1]",
                "{0=332694, 1=110999, -1=110797, -3=37312, 2=37226, 3=37138, -2=37133, 7=12528, -5=12466, -4=12334}",
                652.206306000023,
                2.004140999989157
        );
        bigIntegers_helper(
                3,
                "[37, 85, -15, 10, 1, -100, -1, 0, 0, 18, -29, -2, 0, -1, 41, -2, -53, 3914, 7, -344]",
                "{0=249213, 1=93921, -1=93517, -3=35287, 2=35230, 3=35001, -2=34821, 5=13295, -6=13287, -4=13264}",
                3.8147781719932765E7,
                3.008166999991507
        );
        bigIntegers_helper(
                4,
                "[13, 5, 43806, -61, -1, 0, 1, 9, -21, -88152, -6, -1, -67, -3737, -192, -12, -22, -4701, -30, 14]",
                "{0=199552, -1=80147, 1=79812, -3=32188, -2=31996, 3=31903, 2=31772, -5=13020, 7=12904, 5=12799}",
                3.0232412271737646E12,
                4.00732099999149
        );
        bigIntegers_helper(
                5,
                "[21, 21, 1256222, -233, -1, 0, 1, 9, -117, -1661016, -6, -1, -67, -39244, -576, -28, -38, -16989," +
                " -46, 30]",
                "{0=165977, -1=69743, 1=69535, -3=28984, 3=28968, -2=28966, 2=28680, 6=12162, -4=12155, -5=12141}",
                5.6936067849711536E16,
                5.009217000005041
        );
        bigIntegers_helper(
                10,
                "[-47968091191, -337, 220, -117, -645698713, -4626764, -124, -66364, -437219, 566, 56, 1, -2, 1, -3," +
                " 14, 1224, 81, 2, -232]",
                "{0=90999, 1=41389, -1=41275, -3=18996, 3=18823, -2=18748, 2=18717, -5=8699, 6=8690, 5=8545}",
                -3.800274840387793E39,
                9.993747000004165
        );
        bigIntegers_helper(
                100,
                "[630008861630388057697674146568609443823746152, 1456412177008032918, -3847087123, 152333515," +
                " 4586568116177437044781785918723470267390701, -410797219, -11746946495762269130390395408406068," +
                " 1121036, 4708883950260258218180085, 11, -1225940409912787617, 114, -4348," +
                " -141100429057369858509026228057227844842824, 1233903230," +
                " -220427863617519248349539572941206305312698531160014400224142519234934133812366899774702," +
                " -1772557890515702532927646423272573535, 1376024135659395296, -117101863095185620958878497, 1597518]",
                "{0=9917, 1=4883, -1=4852, 2=2533, -3=2503, 3=2411, -2=2405, -6=1237, 7=1219, -4=1202}",
                Double.NaN,
                99.96628299999787
        );
        bigIntegers_fail_helper(0);
        bigIntegers_fail_helper(-1);
        bigIntegers_fail_helper(Integer.MAX_VALUE);
    }

    private static void nextFromRangeUp_BigInteger_helper(int meanBitSize, int a, @NotNull String output) {
        aeq(P.withScale(meanBitSize).nextFromRangeUp(BigInteger.valueOf(a)), output);
        P.reset();
    }

    private static void nextFromRangeUp_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).nextFromRangeUp(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeUp_BigInteger() {
        nextFromRangeUp_BigInteger_helper(5, 8, "117");
        nextFromRangeUp_BigInteger_helper(10, 8, "305");
        nextFromRangeUp_BigInteger_helper(5, 10, "117");
        nextFromRangeUp_BigInteger_helper(10, 10, "305");
        nextFromRangeUp_BigInteger_helper(1, 0, "5");
        nextFromRangeUp_BigInteger_helper(10, 0, "47968091191");
        nextFromRangeUp_BigInteger_helper(5, -8, "21");
        nextFromRangeUp_BigInteger_helper(10, -8, "47968091191");
        nextFromRangeUp_BigInteger_helper(5, -10, "21");
        nextFromRangeUp_BigInteger_helper(10, -10, "47968091191");
        nextFromRangeUp_BigInteger_fail_helper(4, 10);
        nextFromRangeUp_BigInteger_fail_helper(3, 10);
        nextFromRangeUp_BigInteger_fail_helper(Integer.MAX_VALUE, -10);
    }

    private static void rangeUp_BigInteger_helper(
            int meanBitSize,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).rangeUp(BigInteger.valueOf(a)),
                output,
                topSampleCount,
                sampleMean,
                bitSizeMean
        );
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
        rangeUp_BigInteger_helper(
                5,
                8,
                "[117, 21, 3549, 31, 28, 21, 60, 15, 23, 27, 18, 36, 11, 13, 12, 19, 21, 28, 11, 10]",
                "{11=62758, 8=62664, 10=62529, 13=62511, 14=62497, 15=62490, 9=62457, 12=62163, 19=15819, 25=15812}",
                900.8645519990359,
                4.999453999951416
        );
        rangeUp_BigInteger_helper(
                10,
                8,
                "[305, 989, 2870934583, 32233, 104, 24, 46, 348, 13680, 425220133, 191, 38, 5035, 26823934, 10," +
                " 7232, 11, 11449, 3621, 10]",
                "{15=18196, 13=17980, 8=17959, 12=17924, 10=17868, 9=17747, 11=17709, 14=17599, 28=7743, 30=7716}",
                7.070709518052013E21,
                10.008093999993136
        );
        rangeUp_BigInteger_helper(
                5,
                10,
                "[117, 21, 3549, 31, 28, 21, 60, 11, 13, 18, 36, 13, 15, 14, 19, 21, 28, 13, 12, 26]",
                "{10=83635, 15=83453, 12=83407, 13=83388, 11=83086, 14=83073, 25=15868, 19=15768, 22=15712, 29=15707}",
                894.1639779982157,
                4.999280999951436
        );
        rangeUp_BigInteger_helper(
                10,
                10,
                "[305, 989, 2870934583, 32233, 104, 24, 46, 348, 13680, 425220133, 191, 38, 5035, 26823934, 12," +
                " 7232, 13, 11449, 3621, 12]",
                "{15=24036, 14=24011, 10=23917, 11=23879, 12=23741, 13=23572, 28=7748, 27=7733, 30=7713, 23=7670}",
                7.070705170732308E21,
                10.006835999993193
        );
        rangeUp_BigInteger_helper(
                1,
                0,
                "[5, 1, 221, 1, 1, 1, 2, 0, 3, 1, 1, 2, 0, 1, 0, 0, 1, 1, 1, 0]",
                "{0=499823, 1=250086, 3=62677, 2=62065, 7=15883, 4=15841, 6=15689, 5=15562, 11=3976, 9=3968}",
                7.527205000038622,
                1.00042899999766
        );
        rangeUp_BigInteger_helper(
                10,
                0,
                "[47968091191, 593, 348, 117, 1719440537, 4626764, 0, 124, 0, 66364, 437219, 0, 566, 88, 2, 4, 1, 5," +
                " 30, 3272]",
                "{0=90425, 1=83151, 3=37762, 2=37465, 5=17197, 6=17184, 7=17024, 4=16953, 9=7894, 11=7839}",
                6.216950515561165E39,
                9.998042000004142
        );
        rangeUp_BigInteger_helper(
                5,
                -8,
                "[21, 53, 2304798, 233, -2, -1, -1, 25, 117, 1661016, 14, 2, 67, 104780, 0, 576, 0, 28, 70, 0]",
                "{0=166826, -1=69668, 1=69233, 2=29112, 3=28791, -3=28686, -2=28548, -4=12165, 4=12071, -5=12065}",
                7.6976860134970016E16,
                5.007767000005044
        );
        rangeUp_BigInteger_helper(
                10,
                -8,
                "[47968091191, 593, 348, 117, 1719440537, 4626764, 0, 124, 0, 66364, 437219, 0, 566, 88, 2, -4, -1," +
                " 5, 30, 3272]",
                "{0=90514, 1=41621, -1=41465, -3=18990, 2=18857, 3=18796, -2=18647, -6=8643, 5=8630, 4=8548}",
                6.216950511573439E39,
                9.99805800000423
        );
        rangeUp_BigInteger_helper(
                5,
                -10,
                "[21, 53, 2304798, 233, -2, -1, -1, 25, 117, 1661016, 14, 2, 67, 104780, 0, 576, 0, 28, 70, 0]",
                "{0=166764, -1=69685, 1=69236, 2=29122, 3=28824, -3=28712, -2=28582, -4=12143, 7=12053, 4=12046}",
                7.6975187379143424E16,
                5.007546000005042
        );
        rangeUp_BigInteger_helper(
                10,
                -10,
                "[47968091191, 593, 348, 117, 1719440537, 4626764, 0, 124, 0, 66364, 437219, 0, 566, 88, 2, -4, -1," +
                " 5, 30, 3272]",
                "{0=90458, 1=41619, -1=41503, -3=18974, 2=18852, 3=18779, -2=18615, 5=8663, -6=8661, 4=8565}",
                6.216950512238074E39,
                9.998103000004235
        );
        rangeUp_BigInteger_fail_helper(4, 10);
        rangeUp_BigInteger_fail_helper(3, 10);
        rangeUp_BigInteger_fail_helper(Integer.MAX_VALUE, -10);
    }

    private static void nextFromRangeDown_BigInteger_helper(int meanBitSize, int a, @NotNull String output) {
        aeq(P.withScale(meanBitSize).nextFromRangeDown(BigInteger.valueOf(a)), output);
        P.reset();
    }

    private static void nextFromRangeDown_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).nextFromRangeDown(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeDown_BigInteger() {
        nextFromRangeDown_BigInteger_helper(5, 8, "-21");
        nextFromRangeDown_BigInteger_helper(10, 8, "-47968091191");
        nextFromRangeDown_BigInteger_helper(5, 10, "-21");
        nextFromRangeDown_BigInteger_helper(10, 10, "-47968091191");
        nextFromRangeDown_BigInteger_helper(1, 0, "-5");
        nextFromRangeDown_BigInteger_helper(10, 0, "-47968091191");
        nextFromRangeDown_BigInteger_helper(5, -8, "-117");
        nextFromRangeDown_BigInteger_helper(10, -8, "-305");
        nextFromRangeDown_BigInteger_helper(5, -10, "-117");
        nextFromRangeDown_BigInteger_helper(10, -10, "-305");
        nextFromRangeDown_BigInteger_fail_helper(4, -10);
        nextFromRangeDown_BigInteger_fail_helper(3, -10);
        nextFromRangeDown_BigInteger_fail_helper(Integer.MAX_VALUE, 10);
    }

    private static void rangeDown_BigInteger_helper(
            int meanBitSize,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        bigIntegerHelper(
                P.withScale(meanBitSize).rangeDown(BigInteger.valueOf(a)),
                output,
                topSampleCount,
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
                "[-21, -53, -2304798, -233, 2, 1, 1, -25, -117, -1661016, -14, -2, -67, -104780, 0, -576, 0, -28," +
                " -70, 0]",
                "{0=166826, 1=69668, -1=69233, -2=29112, -3=28791, 3=28686, 2=28548, 4=12165, -4=12071, 5=12065}",
                -7.6976860134970016E16,
                5.007767000005044
        );
        rangeDown_BigInteger_helper(
                10,
                8,
                "[-47968091191, -593, -348, -117, -1719440537, -4626764, 0, -124, 0, -66364, -437219, 0, -566, -88," +
                " -2, 4, 1, -5, -30, -3272]",
                "{0=90514, -1=41621, 1=41465, 3=18990, -2=18857, -3=18796, 2=18647, 6=8643, -5=8630, -4=8548}",
                -6.216950511573439E39,
                9.99805800000423
        );
        rangeDown_BigInteger_helper(
                5,
                10,
                "[-21, -53, -2304798, -233, 2, 1, 1, -25, -117, -1661016, -14, -2, -67, -104780, 0, -576, 0, -28," +
                " -70, 0]",
                "{0=166764, 1=69685, -1=69236, -2=29122, -3=28824, 3=28712, 2=28582, 4=12143, -7=12053, -4=12046}",
                -7.6975187379143424E16,
                5.007546000005042
        );
        rangeDown_BigInteger_helper(
                10,
                10,
                "[-47968091191, -593, -348, -117, -1719440537, -4626764, 0, -124, 0, -66364, -437219, 0, -566, -88," +
                " -2, 4, 1, -5, -30, -3272]",
                "{0=90458, -1=41619, 1=41503, 3=18974, -2=18852, -3=18779, 2=18615, -5=8663, 6=8661, -4=8565}",
                -6.216950512238074E39,
                9.998103000004235
        );
        rangeDown_BigInteger_helper(
                1,
                0,
                "[-5, -1, -221, -1, -1, -1, -2, 0, -3, -1, -1, -2, 0, -1, 0, 0, -1, -1, -1, 0]",
                "{0=499823, -1=250086, -3=62677, -2=62065, -7=15883, -4=15841, -6=15689, -5=15562, -11=3976, -9=3968}",
                -7.527205000038622,
                1.00042899999766
        );
        rangeDown_BigInteger_helper(
                10,
                0,
                "[-47968091191, -593, -348, -117, -1719440537, -4626764, 0, -124, 0, -66364, -437219, 0, -566, -88," +
                " -2, -4, -1, -5, -30, -3272]",
                "{0=90425, -1=83151, -3=37762, -2=37465, -5=17197, -6=17184, -7=17024, -4=16953, -9=7894, -11=7839}",
                -6.216950515561165E39,
                9.998042000004142
        );
        rangeDown_BigInteger_helper(
                5,
                -8,
                "[-117, -21, -3549, -31, -28, -21, -60, -15, -23, -27, -18, -36, -11, -13, -12, -19, -21, -28, -11," +
                " -10]",
                "{-11=62758, -8=62664, -10=62529, -13=62511, -14=62497, -15=62490, -9=62457, -12=62163, -19=15819," +
                " -25=15812}",
                -900.8645519990359,
                4.999453999951416
        );
        rangeDown_BigInteger_helper(
                10,
                -8,
                "[-305, -989, -2870934583, -32233, -104, -24, -46, -348, -13680, -425220133, -191, -38, -5035," +
                " -26823934, -10, -7232, -11, -11449, -3621, -10]",
                "{-15=18196, -13=17980, -8=17959, -12=17924, -10=17868, -9=17747, -11=17709, -14=17599, -28=7743," +
                " -30=7716}",
                -7.070709518052013E21,
                10.008093999993136
        );
        rangeDown_BigInteger_helper(
                5,
                -10,
                "[-117, -21, -3549, -31, -28, -21, -60, -11, -13, -18, -36, -13, -15, -14, -19, -21, -28, -13, -12," +
                " -26]",
                "{-10=83635, -15=83453, -12=83407, -13=83388, -11=83086, -14=83073, -25=15868, -19=15768, -22=15712," +
                " -29=15707}",
                -894.1639779982157,
                4.999280999951436
        );
        rangeDown_BigInteger_helper(
                10,
                -10,
                "[-305, -989, -2870934583, -32233, -104, -24, -46, -348, -13680, -425220133, -191, -38, -5035," +
                " -26823934, -12, -7232, -13, -11449, -3621, -12]",
                "{-15=24036, -14=24011, -10=23917, -11=23879, -12=23741, -13=23572, -28=7748, -27=7733, -30=7713," +
                " -23=7670}",
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
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        List<BinaryFraction> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfBinaryFractions(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.getMantissa().abs().bitLength(), sample))), mantissaBitSizeMean);
        aeq(meanOfIntegers(toList(map(x -> Math.abs(x.getExponent()), sample))), exponentMean);
    }

    private static void nextPositiveBinaryFraction_helper(
            int mantissaMeanBitSize,
            int meanExponentSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextPositiveBinaryFraction(),
                output
        );
        P.reset();
    }

    private static void nextPositiveBinaryFraction_fail_helper(int mantissaMeanBitSize, int meanExponentSize) {
        try {
            P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextPositiveBinaryFraction();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextPositiveBinaryFraction() {
        nextPositiveBinaryFraction_helper(2, 1, "13 << 1");
        nextPositiveBinaryFraction_helper(5, 3, "21 << 8");
        nextPositiveBinaryFraction_helper(32, 8, "9899036265412339 >> 7");
        nextPositiveBinaryFraction_helper(100, 10, "94790976865653102300816908025048767680216169 >> 15");
        nextPositiveBinaryFraction_fail_helper(1, 1);
        nextPositiveBinaryFraction_fail_helper(2, 0);
    }

    private static void positiveBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).positiveBinaryFractions(),
                output,
                topSampleCount,
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
                "[13 << 1, 477 << 1, 3 << 1, 5, 3 << 1, 3 >> 2, 1, 1 << 1, 3 >> 1, 1, 3, 1, 1, 1, 3 << 3, 11," +
                " 1 >> 2, 3 << 5, 5, 19 << 2]",
                "{1=250428, 3=124339, 1 << 1=62680, 1 >> 1=62588, 3 << 1=31509, 7=31376, 1 << 2=31370, 1 >> 2=31080," +
                " 5=31066, 3 >> 1=31049}",
                667.4036101180055,
                2.0010259999798934,
                0.9987319999976897
        );
        positiveBinaryFractions_helper(
                5,
                3,
                "[21 << 8, 207647 >> 5, 13, 1 >> 1, 3 << 6, 53 << 13, 345 >> 4, 7 << 11, 9881, 193, 13 >> 4, 7," +
                " 4701, 47, 15 >> 2, 3 >> 5, 1 >> 2, 9 << 1, 3 << 4, 63 << 2]",
                "{1=49801, 3=40309, 1 >> 1=18795, 1 << 1=18688, 7=16065, 5=15921, 3 << 1=15045, 3 >> 1=14935," +
                " 1 >> 2=14052, 1 << 2=13960}",
                5.338187744452398E14,
                5.00820000000873,
                3.0015849999914943
        );
        positiveBinaryFractions_helper(
                32,
                8,
                "[9899036265412339 >> 7, 225462717 >> 28, 165780717137 >> 5, 1 >> 17, 2883801665 >> 10, 445551 >> 1," +
                " 15973 >> 10, 4373 >> 2, 8041 << 3, 11, 6483542254218470125815323736137 << 9, 3 >> 6, 3239 << 3," +
                " 105164088157 >> 19, 6261899171 >> 8, 1 >> 14, 1388001050111 >> 2, 10161243 << 29, 37 << 31, 289]",
                "{3=3446, 1=3367, 5=1674, 7=1641, 1 >> 1=1549, 1 << 1=1527, 3 >> 1=1494, 3 << 1=1452, 3 >> 2=1427," +
                " 1 >> 2=1381}",
                7.67364101496927E126,
                32.00852100002276,
                7.997832000016788
        );
        positiveBinaryFractions_helper(
                100,
                10,
                "[94790976865653102300816908025048767680216169 >> 15, 4630900152605 >> 6, 107302373 >> 12," +
                " 1301 >> 3, 1102076678907027178916829938622163782081261 << 1, 142361763 >> 23," +
                " 27934853437353795793120507739 >> 2, 1121037 << 40, 13078798026233 << 4, 2378861914519634593 >> 5," +
                " 16637 >> 2, 11780491659396320304092245748596897246965 >> 9, 101343 >> 30," +
                " 4281825112654693651459071466596876043334654508762035987794871586861109396215 << 12," +
                " 87894315763404085565781671205347 << 21, 7802322497623 >> 27, 350516300835593753 >> 21," +
                " 752585 << 4, 657125105137779066251367372137060396099924931317 >> 3," +
                " 15713218602914447431492283897880756550610073314504466517671827791587317575567925917482809549 >> 34]",
                "{1=923, 3=869, 5=475, 7=451, 1 << 1=439, 1 >> 2=419, 3 >> 1=395, 1 << 2=391, 3 << 2=376, 3 >> 3=359}",
                7.597222225943522E303,
                99.91674399999913,
                10.005905000005157
        );
        positiveBinaryFractions_fail_helper(1, 1);
        positiveBinaryFractions_fail_helper(2, 0);
    }

    private static void nextNegativeBinaryFraction_helper(
            int mantissaMeanBitSize,
            int meanExponentSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextNegativeBinaryFraction(),
                output
        );
        P.reset();
    }

    private static void nextNegativeBinaryFraction_fail_helper(int mantissaMeanBitSize, int meanExponentSize) {
        try {
            P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextNegativeBinaryFraction();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNegativeBinaryFraction() {
        nextNegativeBinaryFraction_helper(2, 1, "-13 << 1");
        nextNegativeBinaryFraction_helper(5, 3, "-21 << 8");
        nextNegativeBinaryFraction_helper(32, 8, "-9899036265412339 >> 7");
        nextNegativeBinaryFraction_helper(100, 10, "-94790976865653102300816908025048767680216169 >> 15");
        nextNegativeBinaryFraction_fail_helper(1, 1);
        nextNegativeBinaryFraction_fail_helper(2, 0);
    }

    private static void negativeBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).negativeBinaryFractions(),
                output,
                topSampleCount,
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
                "[-13 << 1, -477 << 1, -3 << 1, -5, -3 << 1, -3 >> 2, -1, -1 << 1, -3 >> 1, -1, -3, -1, -1, -1," +
                " -3 << 3, -11, -1 >> 2, -3 << 5, -5, -19 << 2]",
                "{-1=250428, -3=124339, -1 << 1=62680, -1 >> 1=62588, -3 << 1=31509, -7=31376, -1 << 2=31370," +
                " -1 >> 2=31080, -5=31066, -3 >> 1=31049}",
                -667.4036101180055,
                2.0010259999798934,
                0.9987319999976897
        );
        negativeBinaryFractions_helper(
                5,
                3,
                "[-21 << 8, -207647 >> 5, -13, -1 >> 1, -3 << 6, -53 << 13, -345 >> 4, -7 << 11, -9881, -193," +
                " -13 >> 4, -7, -4701, -47, -15 >> 2, -3 >> 5, -1 >> 2, -9 << 1, -3 << 4, -63 << 2]",
                "{-1=49801, -3=40309, -1 >> 1=18795, -1 << 1=18688, -7=16065, -5=15921, -3 << 1=15045," +
                " -3 >> 1=14935, -1 >> 2=14052, -1 << 2=13960}",
                -5.338187744452399E14,
                5.00820000000873,
                3.0015849999914943
        );
        negativeBinaryFractions_helper(
                32,
                8,
                "[-9899036265412339 >> 7, -225462717 >> 28, -165780717137 >> 5, -1 >> 17, -2883801665 >> 10," +
                " -445551 >> 1, -15973 >> 10, -4373 >> 2, -8041 << 3, -11, -6483542254218470125815323736137 << 9," +
                " -3 >> 6, -3239 << 3, -105164088157 >> 19, -6261899171 >> 8, -1 >> 14, -1388001050111 >> 2," +
                " -10161243 << 29, -37 << 31, -289]",
                "{-3=3446, -1=3367, -5=1674, -7=1641, -1 >> 1=1549, -1 << 1=1527, -3 >> 1=1494, -3 << 1=1452," +
                " -3 >> 2=1427, -1 >> 2=1381}",
                -7.673641014969271E126,
                32.00852100002276,
                7.997832000016788
        );
        negativeBinaryFractions_helper(
                100,
                10,
                "[-94790976865653102300816908025048767680216169 >> 15, -4630900152605 >> 6, -107302373 >> 12," +
                " -1301 >> 3, -1102076678907027178916829938622163782081261 << 1, -142361763 >> 23," +
                " -27934853437353795793120507739 >> 2, -1121037 << 40, -13078798026233 << 4," +
                " -2378861914519634593 >> 5, -16637 >> 2, -11780491659396320304092245748596897246965 >> 9," +
                " -101343 >> 30," +
                " -4281825112654693651459071466596876043334654508762035987794871586861109396215 << 12," +
                " -87894315763404085565781671205347 << 21, -7802322497623 >> 27, -350516300835593753 >> 21," +
                " -752585 << 4, -657125105137779066251367372137060396099924931317 >> 3," +
                " -15713218602914447431492283897880756550610073314504466517671827791587317575567925917482809549" +
                " >> 34]",
                "{-1=923, -3=869, -5=475, -7=451, -1 << 1=439, -1 >> 2=419, -3 >> 1=395, -1 << 2=391, -3 << 2=376," +
                " -3 >> 3=359}",
                Double.NEGATIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        negativeBinaryFractions_fail_helper(1, 1);
        negativeBinaryFractions_fail_helper(2, 0);
    }

    private static void nextNonzeroBinaryFraction_helper(
            int mantissaMeanBitSize,
            int meanExponentSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextNonzeroBinaryFraction(),
                output
        );
        P.reset();
    }

    private static void nextNonzeroBinaryFraction_fail_helper(int mantissaMeanBitSize, int meanExponentSize) {
        try {
            P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextNonzeroBinaryFraction();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNonzeroBinaryFraction() {
        nextNonzeroBinaryFraction_helper(2, 1, "5 << 1");
        nextNonzeroBinaryFraction_helper(5, 3, "13 << 8");
        nextNonzeroBinaryFraction_helper(32, 8, "5395436638041843 >> 7");
        nextNonzeroBinaryFraction_helper(100, 10, "50189486468591856017745471479752044668255337 >> 15");
        nextNonzeroBinaryFraction_fail_helper(1, 1);
        nextNonzeroBinaryFraction_fail_helper(2, 0);
    }

    private static void nonzeroBinaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).nonzeroBinaryFractions(),
                output,
                topSampleCount,
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
                "[5 << 1, 221 << 1, 1 << 1, 3, 1 << 1, 1 >> 2, -3, 1 << 1, 1, -1, -3, -1, -1 << 1, 5 >> 3, -3 >> 2," +
                " 1 << 5, 3, 11 << 2, -3 << 2, -5]",
                "{1=125316, -1=124573, -3=62976, 3=62243, 1 << 1=31474, -1 >> 1=31282, -1 << 1=31127, 1 >> 1=31021," +
                " 3 << 1=15828, 7=15784}",
                -1703.4563803661672,
                1.9998409999799591,
                0.9992589999977214
        );
        nonzeroBinaryFractions_helper(
                5,
                3,
                "[13 << 8, 76575 >> 5, 5, -1 >> 1, -3 << 6, 53 << 13, 217 >> 4, -7 << 11, -5785, 125, -13 >> 4, -3," +
                " -4701, -47, -7 >> 2, 3 >> 5, -1 >> 1, -5 << 1, 1 << 4, -63 << 2]",
                "{-1=25068, 1=24968, -3=20121, 3=20093, -1 >> 1=9437, -1 << 1=9388, 1 >> 1=9368, 1 << 1=9287," +
                " 7=8060, -7=7980}",
                5.0363539477125694E14,
                5.002908000008661,
                3.0022489999914326
        );
        nonzeroBinaryFractions_helper(
                32,
                8,
                "[5395436638041843 >> 7, -91244989 >> 28, -97061240401 >> 5, -100875513 >> 13, -46647 >> 5," +
                " 461 >> 1, 7781 >> 10, 2325 >> 2, 3945 << 3, -7, -3948241053762011322821917325385 << 9, -1 >> 6," +
                " 1191 << 3, -36444611421 >> 19, 4114415523 >> 8, -1074751205627367114659 >> 1, -7412493 << 29," +
                " -21 << 31, 161, -15 >> 12]",
                "{-1=1789, 1=1771, -3=1741, 3=1693, 5=831, 7=825, -7=804, -1 << 1=794, -5=790, 1 >> 1=787}",
                -4.3996820983202236E126,
                31.99629500002364,
                7.998090000016753
        );
        nonzeroBinaryFractions_helper(
                100,
                10,
                "[50189486468591856017745471479752044668255337 >> 15, -2431876897053 >> 6, 40193509 >> 12, 789 >> 3," +
                " 405178391452945205743838742601902485019373 << 1, -142361763 >> 23," +
                " 27934853437353795793120507739 >> 2, -596749 << 40, -8680751515129 << 4, 1225940409912787617 >> 5," +
                " 8445 >> 2, 6335973788661304888678252029688605863669 >> 9, -35807 >> 30," +
                " 4281825112654693651459071466596876043334654508762035987794871586861109396215 << 12," +
                " -47329496556100744717887168633315 << 21, -3404275986519 >> 27, -206401112759737881 >> 21," +
                " 490441 << 4, -291749695805053336700446163957989641185941795573 >> 3," +
                " -7565074697576503086418501144243243906404199739840721515127265994169792376514579092749220045 >> 34]",
                "{-3=474, -1=461, 3=460, 1=433, 5=237, -5=228, -7=223, 7=221, -3 << 2=220, 3 << 1=211}",
                Double.NEGATIVE_INFINITY,
                99.89809400000158,
                10.005882000004926
        );
        nonzeroBinaryFractions_fail_helper(1, 1);
        nonzeroBinaryFractions_fail_helper(2, 0);
    }

    private static void nextBinaryFraction_helper(
            int mantissaMeanBitSize,
            int meanExponentSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextBinaryFraction(),
                output
        );
        P.reset();
    }

    private static void nextBinaryFraction_fail_helper(int mantissaMeanBitSize, int meanExponentSize) {
        try {
            P.withScale(mantissaMeanBitSize).withSecondaryScale(meanExponentSize).nextBinaryFraction();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextBinaryFraction() {
        nextBinaryFraction_helper(2, 1, "13 << 1");
        nextBinaryFraction_helper(5, 3, "21");
        nextBinaryFraction_helper(32, 8, "469791");
        nextBinaryFraction_helper(100, 10, "630008861630388057697674146568609443823746153 >> 5");
        nextBinaryFraction_fail_helper(1, 0);
        nextBinaryFraction_fail_helper(0, 1);
    }

    private static void binaryFractions_helper(
            int meanMantissaBitSize,
            int meanExponentSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanMantissaBitSize).withSecondaryScale(meanExponentSize).binaryFractions(),
                output,
                topSampleCount,
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
                "[5, 221 << 3, 0, -1 << 1, 1 >> 3, 0, 0, 0, 1 >> 3, 1 << 2, 0, 0, 0, 0, 0, 0, 1 << 1, -5 << 5, 0," +
                " 21 << 2]",
                "{0=499925, -1=52184, 1=41175, 3=20678, -1 << 1=17498, -1 >> 1=17350, 1 >> 1=13966, 1 << 1=13884," +
                " -3=13110, -1 >> 2=11799}",
                -15141.716243090259,
                0.9597509999985684,
                1.0004139999980264
        );
        binaryFractions_helper(
                5,
                3,
                "[21, 13 >> 2, 731935, -61 >> 4, 25 << 8, -1661015 >> 6, -67 << 1, -5785 >> 2, -11 >> 7, 0," +
                " -69 >> 13, -45 >> 1, 3 >> 3, -9 >> 6, 0, 3 >> 8, 1 << 2, -113 >> 1, 0, 9]",
                "{0=166417, -1=21308, 1=15114, 3=12621, -3=9008, -1 << 1=8453, -1 >> 1=8386, -1 << 2=6611," +
                " -1 >> 2=6505, 1 << 1=6006}",
                1.66610944996342963E18,
                4.954361000006467,
                3.007180999992695
        );
        binaryFractions_helper(
                32,
                8,
                "[469791, 24724324871765 << 11, -1087 >> 10, -25 << 9, -2361 >> 22, 3685, 37997 << 2, -31, -5 << 9," +
                " -30459629547907543097 << 1, -97347 >> 1, -115520513794470430625 >> 1, 4379 >> 3," +
                " 533422527609161 >> 3, -15341255 >> 2, -1283 >> 5, -251 << 8, -3580633563 >> 5, -29945 << 6," +
                " -8861 << 9]",
                "{0=30182, -1=2354, 1=1612, 3=1516, -3=1196, -1 << 1=1054, -1 >> 1=1014, -1 << 2=971, -1 >> 2=923," +
                " -1 >> 3=838}",
                6.447723197358738E125,
                32.013006000028064,
                7.994682000015932
        );
        binaryFractions_helper(
                100,
                10,
                "[630008861630388057697674146568609443823746153 >> 5, 32710707830660605, 1046826469 << 11, 17 << 23," +
                " -256597222505842144348851856454779, -142361763 << 6, -45886509749071363790587482064085 << 9," +
                " 9 << 41, 15017245, 3, -361249281457652385 >> 22, 34371725244293874009975536201589773 << 3," +
                " 9166463," +
                " -33931649963849257541271229886148490275027474611009006183968527899999322432666883949805 >> 4," +
                " -147116069440267308989572019729851081 >> 44, -41860902223040053065371 << 4, 1121 >> 10, 27 >> 11," +
                " 24609989311958439409331914328478960583564339 >> 8," +
                " 706325325640589936310099702184562783564948487506650806918972129922500644033138800134393 << 8]",
                "{0=9913, -1=648, 3=434, 1=423, -3=335, -1 >> 1=320, -1 << 1=291, -1 << 2=268, -1 >> 3=259," +
                " -1 >> 2=257}",
                Double.NEGATIVE_INFINITY,
                99.8870699999952,
                9.98571900000396
        );
        binaryFractions_fail_helper(1, 0);
        binaryFractions_fail_helper(0, 1);
    }

    private static void nextFromRangeUp_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeUp(BinaryFraction.read(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeUp_BinaryFraction_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeUp(BinaryFraction.read(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeUp_BinaryFraction() {
        nextFromRangeUp_BinaryFraction_helper(1, 1, "0", "5");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "0", "21");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "0", "469791");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "1", "3 << 1");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "1", "11 << 1");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "1", "14681 << 5");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "11", "1 << 4");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "11", "1 << 5");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "11", "234901 << 1");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "5 << 20", "5 << 21");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "5 << 20", "13 << 21");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "5 << 20", "117449 << 22");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "5 >> 20", "5 >> 19");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "5 >> 20", "13 >> 19");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "5 >> 20", "117449 >> 18");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "-1", "1 << 2");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "-1", "5 << 2");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "-1", "234895 << 1");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "-11", "-3 << 1");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "-11", "5 << 1");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "-11", "117445 << 2");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "-5 << 20", "0");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "-5 << 20", "1 << 24");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "-5 << 20", "234893 << 21");
        nextFromRangeUp_BinaryFraction_helper(1, 1, "-5 >> 20", "0");
        nextFromRangeUp_BinaryFraction_helper(5, 3, "-5 >> 20", "1 >> 16");
        nextFromRangeUp_BinaryFraction_helper(32, 8, "-5 >> 20", "234893 >> 19");
        nextFromRangeUp_BinaryFraction_fail_helper(1, 0, "0");
        nextFromRangeUp_BinaryFraction_fail_helper(0, 1, "0");
    }

    private static void rangeUp_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(BinaryFraction.read(a).get()),
                output,
                topSampleCount,
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
                "[5, 221 << 3, 0, 1 << 1, 1 >> 3, 0, 0, 0, 1 >> 3, 1 << 2, 0, 0, 0, 0, 0, 0, 1 << 1, 5 << 5, 0," +
                " 21 << 2]",
                "{0=499925, 1=93359, 3=33788, 1 << 1=31382, 1 >> 1=31316, 1 >> 2=21043, 1 << 2=20806, 1 << 3=13962," +
                " 1 >> 3=13774, 3 << 1=11402}",
                20405.38097776243,
                0.9597509999985684,
                1.0004139999980264
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "0",
                "[21, 13 >> 2, 731935, 61 >> 4, 25 << 8, 1661015 >> 6, 67 << 1, 5785 >> 2, 11 >> 7, 0, 69 >> 13," +
                " 45 >> 1, 3 >> 3, 9 >> 6, 0, 3 >> 8, 1 << 2, 113 >> 1, 0, 9]",
                "{0=166417, 1=36422, 3=21629, 1 << 1=14459, 1 >> 1=14312, 1 << 2=11287, 1 >> 2=11201, 5=10374," +
                " 7=8954, 1 >> 3=8734}",
                7.8980066065349693E18,
                4.954361000006467,
                3.007180999992695
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "0",
                "[469791, 24724324871765 << 11, 1087 >> 10, 25 << 9, 2361 >> 22, 3685, 37997 << 2, 31, 5 << 9," +
                " 30459629547907543097 << 1, 97347 >> 1, 115520513794470430625 >> 1, 4379 >> 3," +
                " 533422527609161 >> 3, 15341255 >> 2, 1283 >> 5, 251 << 8, 3580633563 >> 5, 29945 << 6, 8861 << 9]",
                "{0=30182, 1=3966, 3=2712, 1 >> 1=1750, 1 << 1=1750, 1 << 2=1604, 1 >> 2=1565, 5=1459, 1 >> 3=1410," +
                " 1 << 3=1392}",
                6.4477251665470446E125,
                32.013006000028064,
                7.994682000015932
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "1",
                "[3 << 1, 1769, 1, 3, 9 >> 3, 1, 1, 1, 9 >> 3, 5, 1, 1, 1, 1, 1, 1, 3, 161, 1, 85]",
                "{1=499925, 1 << 1=93359, 1 << 2=33788, 3=31382, 3 >> 1=31316, 5 >> 2=21043, 5=20806, 9=13962," +
                " 9 >> 3=13774, 7=11402}",
                20406.380977936788,
                2.2737089999847506,
                0.7358969999983083
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "1",
                "[11 << 1, 17 >> 2, 22873 << 5, 77 >> 4, 6401, 1661079 >> 6, 135, 5789 >> 2, 139 >> 7, 1," +
                " 8261 >> 13, 47 >> 1, 11 >> 3, 73 >> 6, 1, 259 >> 8, 5, 115 >> 1, 1, 5 << 1]",
                "{1=166417, 1 << 1=36422, 1 << 2=21629, 3=14459, 3 >> 1=14312, 5=11287, 5 >> 2=11201, 3 << 1=10374," +
                " 1 << 3=8954, 9 >> 3=8734}",
                7.8980066065349693E18,
                7.159625000020588,
                1.8263119999926671
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "1",
                "[14681 << 5, 50635417337374721, 2111 >> 10, 12801, 4196665 >> 22, 1843 << 1, 151989, 1 << 5, 2561," +
                " 60919259095815086195, 97349 >> 1, 115520513794470430627 >> 1, 4387 >> 3, 533422527609169 >> 3," +
                " 15341259 >> 2, 1315 >> 5, 64257, 3580633595 >> 5, 1916481, 4536833]",
                "{1=30182, 1 << 1=3966, 1 << 2=2712, 3 >> 1=1750, 3=1750, 5=1604, 5 >> 2=1565, 3 << 1=1459," +
                " 9 >> 3=1410, 9=1392}",
                6.4477251665470446E125,
                36.763259000017705,
                4.203809999998741
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "11",
                "[1 << 4, 1779, 11, 13, 89 >> 3, 11, 11, 11, 89 >> 3, 15, 11, 11, 11, 11, 11, 11, 13, 171, 11, 95]",
                "{11=499925, 3 << 2=93359, 7 << 1=33788, 13=31382, 23 >> 1=31316, 45 >> 2=21043, 15=20806, 19=13962," +
                " 89 >> 3=13774, 17=11402}",
                20416.3809777996,
                4.556464999980296,
                0.8102169999972612
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "11",
                "[1 << 5, 57 >> 2, 365973 << 1, 237 >> 4, 6411, 1661719 >> 6, 145, 5829 >> 2, 1419 >> 7, 11," +
                " 90181 >> 13, 67 >> 1, 91 >> 3, 713 >> 6, 11, 2819 >> 8, 15, 135 >> 1, 11, 5 << 2]",
                "{11=166417, 3 << 2=36422, 7 << 1=21629, 13=14459, 23 >> 1=14312, 15=11287, 45 >> 2=11201," +
                " 1 << 4=10374, 9 << 1=8954, 89 >> 3=8734}",
                7.8980066065349693E18,
                8.511026999979812,
                1.8539969999931307
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "11",
                "[234901 << 1, 50635417337374731, 12351 >> 10, 12811, 46139705 >> 22, 231 << 4, 151999, 21 << 1," +
                " 2571, 60919259095815086205, 97369 >> 1, 115520513794470430647 >> 1, 4467 >> 3," +
                " 533422527609249 >> 3, 15341299 >> 2, 1635 >> 5, 64267, 3580633915 >> 5, 1916491, 4536843]",
                "{11=30182, 3 << 2=3966, 7 << 1=2712, 23 >> 1=1750, 13=1750, 15=1604, 45 >> 2=1565, 1 << 4=1459," +
                " 89 >> 3=1410, 19=1392}",
                6.4477251665470446E125,
                37.24737200001865,
                4.206839999998712
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "5 << 20",
                "[5 << 21, 1773 << 20, 5 << 20, 7 << 20, 41 << 17, 5 << 20, 5 << 20, 5 << 20, 41 << 17, 9 << 20," +
                " 5 << 20, 5 << 20, 5 << 20, 5 << 20, 5 << 20, 5 << 20, 7 << 20, 165 << 20, 5 << 20, 89 << 20]",
                "{5 << 20=499925, 3 << 21=93359, 1 << 23=33788, 7 << 20=31382, 11 << 19=31316, 21 << 18=21043," +
                " 9 << 20=20806, 13 << 20=13962, 41 << 17=13774, 11 << 20=11402}",
                2.1401835644499287E10,
                3.7599269999773086,
                19.763109999771977
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "5 << 20",
                "[13 << 21, 33 << 18, 182985 << 22, 141 << 16, 6405 << 20, 1661335 << 14, 139 << 20, 5805 << 18," +
                " 651 << 13, 5 << 20, 41029 << 7, 55 << 19, 43 << 17, 329 << 14, 5 << 20, 1283 << 12, 9 << 20," +
                " 123 << 19, 5 << 20, 7 << 21]",
                "{5 << 20=166417, 3 << 21=36422, 1 << 23=21629, 7 << 20=14459, 11 << 19=14312, 9 << 20=11287," +
                " 21 << 18=11201, 5 << 21=10374, 3 << 22=8954, 41 << 17=8734}",
                8.281660175454012E24,
                8.010092000028635,
                18.854625999852182
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "5 << 20",
                "[117449 << 22, 50635417337374725 << 20, 6207 << 10, 12805 << 20, 20973881 >> 2, 1845 << 21," +
                " 151993 << 20, 9 << 22, 2565 << 20, 60919259095815086199 << 20, 97357 << 19," +
                " 115520513794470430635 << 19, 4419 << 17, 533422527609201 << 17, 15341275 << 18, 1443 << 15," +
                " 64261 << 20, 3580633723 << 15, 1916485 << 20, 4536837 << 20]",
                "{5 << 20=30182, 3 << 21=3966, 1 << 23=2712, 11 << 19=1750, 7 << 20=1750, 9 << 20=1604," +
                " 21 << 18=1565, 5 << 21=1459, 41 << 17=1410, 13 << 20=1392}",
                6.760929864237234E131,
                37.07091300000956,
                17.015772999850565
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "5 >> 20",
                "[5 >> 19, 1773 >> 20, 5 >> 20, 7 >> 20, 41 >> 23, 5 >> 20, 5 >> 20, 5 >> 20, 41 >> 23, 9 >> 20," +
                " 5 >> 20, 5 >> 20, 5 >> 20, 5 >> 20, 5 >> 20, 5 >> 20, 7 >> 20, 165 >> 20, 5 >> 20, 89 >> 20]",
                "{5 >> 20=499925, 3 >> 19=93359, 1 >> 17=33788, 7 >> 20=31382, 11 >> 21=31316, 21 >> 22=21043," +
                " 9 >> 20=20806, 13 >> 20=13962, 41 >> 23=13774, 11 >> 20=11402}",
                0.019464856126887104,
                3.7599269999773086,
                20.237141999770692
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "5 >> 20",
                "[13 >> 19, 33 >> 22, 182985 >> 18, 141 >> 24, 6405 >> 20, 1661335 >> 26, 139 >> 20, 5805 >> 22," +
                " 651 >> 27, 5 >> 20, 41029 >> 33, 55 >> 21, 43 >> 23, 329 >> 26, 5 >> 20, 1283 >> 28, 9 >> 20," +
                " 123 >> 21, 5 >> 20, 7 >> 19]",
                "{5 >> 20=166417, 3 >> 19=36422, 1 >> 17=21629, 7 >> 20=14459, 11 >> 21=14312, 9 >> 20=11287," +
                " 21 >> 22=11201, 5 >> 19=10374, 3 >> 18=8954, 41 >> 23=8734}",
                7.532126051459283E12,
                8.010092000028635,
                21.16858599984985
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "5 >> 20",
                "[117449 >> 18, 50635417337374725 >> 20, 6207 >> 30, 12805 >> 20, 20973881 >> 42, 1845 >> 19," +
                " 151993 >> 20, 9 >> 18, 2565 >> 20, 60919259095815086199 >> 20, 97357 >> 21," +
                " 115520513794470430635 >> 21, 4419 >> 23, 533422527609201 >> 23, 15341275 >> 22, 1443 >> 25," +
                " 64261 >> 20, 3580633723 >> 25, 1916485 >> 20, 4536837 >> 20]",
                "{5 >> 20=30182, 3 >> 19=3966, 1 >> 17=2712, 11 >> 21=1750, 7 >> 20=1750, 9 >> 20=1604," +
                " 21 >> 22=1565, 5 >> 19=1459, 41 >> 23=1410, 13 >> 20=1392}",
                6.149029890582127E119,
                37.07091300000956,
                23.789788999822175
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-1",
                "[1 << 2, 1767, -1, 1, -7 >> 3, -1, -1, -1, -7 >> 3, 3, -1, -1, -1, -1, -1, -1, 1, 159, -1, 83]",
                "{-1=499925, 0=93359, 1 << 1=33788, 1=31382, -1 >> 1=31316, -3 >> 2=21043, 3=20806, 7=13962," +
                " -7 >> 3=13774, 5=11402}",
                20404.38097764382,
                1.8874289999759444,
                0.601121999998552
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-1",
                "[5 << 2, 9 >> 2, 365967 << 1, 45 >> 4, 6399, 1660951 >> 6, 133, 5781 >> 2, -117 >> 7, -1," +
                " -8123 >> 13, 43 >> 1, -5 >> 3, -55 >> 6, -1, -253 >> 8, 3, 111 >> 1, -1, 1 << 3]",
                "{-1=166417, 0=36422, 1 << 1=21629, 1=14459, -1 >> 1=14312, 3=11287, -3 >> 2=11201, 1 << 2=10374," +
                " 3 << 1=8954, -7 >> 3=8734}",
                7.8980066065349683E18,
                6.7572410000217555,
                1.7570869999935141
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-1",
                "[234895 << 1, 50635417337374719, 63 >> 10, 12799, -4191943 >> 22, 921 << 2, 151987, 15 << 1, 2559," +
                " 60919259095815086193, 97345 >> 1, 115520513794470430623 >> 1, 4371 >> 3, 533422527609153 >> 3," +
                " 15341251 >> 2, 1251 >> 5, 64255, 3580633531 >> 5, 1916479, 4536831]",
                "{-1=30182, 0=3966, 1 << 1=2712, -1 >> 1=1750, 1=1750, 3=1604, -3 >> 2=1565, 1 << 2=1459," +
                " -7 >> 3=1410, 7=1392}",
                6.4477251665470446E125,
                36.58280200001777,
                4.194993999998781
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-11",
                "[-3 << 1, 1757, -11, -9, -87 >> 3, -11, -11, -11, -87 >> 3, -7, -11, -11, -11, -11, -11, -11, -9," +
                " 149, -11, 73]",
                "{-11=499925, -5 << 1=93359, -1 << 3=33788, -9=31382, -21 >> 1=31316, -43 >> 2=21043, -7=20806," +
                " -3=13962, -87 >> 3=13774, -5=11402}",
                20394.380977771583,
                4.305438999999481,
                0.7516849999983759
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-11",
                "[5 << 1, -31 >> 2, 182981 << 2, -115 >> 4, 6389, 1660311 >> 6, 123, 5741 >> 2, -1397 >> 7, -11," +
                " -90043 >> 13, 23 >> 1, -85 >> 3, -695 >> 6, -11, -2813 >> 8, -7, 91 >> 1, -11, -1 << 1]",
                "{-11=166417, -5 << 1=36422, -1 << 3=21629, -9=14459, -21 >> 1=14312, -7=11287, -43 >> 2=11201," +
                " -3 << 1=10374, -1 << 2=8954, -87 >> 3=8734}",
                7.8980066065349683E18,
                8.077762999986804,
                1.8205389999923367
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-11",
                "[117445 << 2, 50635417337374709, -10177 >> 10, 12789, -46134983 >> 22, 1837 << 1, 151977, 5 << 2," +
                " 2549, 60919259095815086183, 97325 >> 1, 115520513794470430603 >> 1, 4291 >> 3," +
                " 533422527609073 >> 3, 15341211 >> 2, 931 >> 5, 64245, 3580633211 >> 5, 1916469, 4536821]",
                "{-11=30182, -5 << 1=3966, -1 << 3=2712, -21 >> 1=1750, -9=1750, -7=1604, -43 >> 2=1565," +
                " -3 << 1=1459, -87 >> 3=1410, -3=1392}",
                6.4477251665470446E125,
                37.10622500001412,
                4.201928999998755
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-5 << 20",
                "[0, 1763 << 20, -5 << 20, -3 << 20, -39 << 17, -5 << 20, -5 << 20, -5 << 20, -39 << 17, -1 << 20," +
                " -5 << 20, -5 << 20, -5 << 20, -5 << 20, -5 << 20, -5 << 20, -3 << 20, 155 << 20, -5 << 20," +
                " 79 << 20]",
                "{-5 << 20=499925, -1 << 22=93359, -1 << 21=33788, -3 << 20=31382, -9 << 19=31316, -19 << 18=21043," +
                " -1 << 20=20806, 3 << 20=13962, -39 << 17=13774, 1 << 20=11402}",
                2.1391349883171562E10,
                3.34172299998717,
                19.553109999772616
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-5 << 20",
                "[1 << 24, -7 << 18, 365965 << 21, -19 << 16, 6395 << 20, 1660695 << 14, 129 << 20, 5765 << 18," +
                " -629 << 13, -5 << 20, -40891 << 7, 35 << 19, -37 << 17, -311 << 14, -5 << 20, -1277 << 12," +
                " -1 << 20, 103 << 19, -5 << 20, 1 << 22]",
                "{-5 << 20=166417, -1 << 22=36422, -1 << 21=21629, -3 << 20=14459, -9 << 19=14312, -1 << 20=11287," +
                " -19 << 18=11201, 0=10374, 1 << 21=8954, -39 << 17=8734}",
                8.281660175454011E24,
                7.570154000037757,
                18.615274999850975
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-5 << 20",
                "[234893 << 21, 50635417337374715 << 20, -4033 << 10, 12795 << 20, -20969159 >> 2, 115 << 25," +
                " 151983 << 20, 13 << 21, 2555 << 20, 60919259095815086189 << 20, 97337 << 19," +
                " 115520513794470430615 << 19, 4339 << 17, 533422527609121 << 17, 15341235 << 18, 1123 << 15," +
                " 64251 << 20, 3580633403 << 15, 1916475 << 20, 4536827 << 20]",
                "{-5 << 20=30182, -1 << 22=3966, -1 << 21=2712, -9 << 19=1750, -3 << 20=1750, -1 << 20=1604," +
                " -19 << 18=1565, 0=1459, -39 << 17=1410, 3 << 20=1392}",
                6.760929864237234E131,
                36.9378720000084,
                16.980748999850196
        );
        rangeUp_BinaryFraction_helper(
                1,
                1,
                "-5 >> 20",
                "[0, 1763 >> 20, -5 >> 20, -3 >> 20, -39 >> 23, -5 >> 20, -5 >> 20, -5 >> 20, -39 >> 23, -1 >> 20," +
                " -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -3 >> 20, 155 >> 20, -5 >> 20," +
                " 79 >> 20]",
                "{-5 >> 20=499925, -1 >> 18=93359, -1 >> 19=33788, -3 >> 20=31382, -9 >> 21=31316, -19 >> 22=21043," +
                " -1 >> 20=20806, 3 >> 20=13962, -39 >> 23=13774, 1 >> 20=11402}",
                0.019455319382515483,
                3.34172299998717,
                20.019541999790963
        );
        rangeUp_BinaryFraction_helper(
                5,
                3,
                "-5 >> 20",
                "[1 >> 16, -7 >> 22, 365965 >> 19, -19 >> 24, 6395 >> 20, 1660695 >> 26, 129 >> 20, 5765 >> 22," +
                " -629 >> 27, -5 >> 20, -40891 >> 33, 35 >> 21, -37 >> 23, -311 >> 26, -5 >> 20, -1277 >> 28," +
                " -1 >> 20, 103 >> 21, -5 >> 20, 1 >> 18]",
                "{-5 >> 20=166417, -1 >> 18=36422, -1 >> 19=21629, -3 >> 20=14459, -9 >> 21=14312, -1 >> 20=11287," +
                " -19 >> 22=11201, 0=10374, 1 >> 19=8954, -39 >> 23=8734}",
                7.532126051459282E12,
                7.570154000037757,
                20.992976999858303
        );
        rangeUp_BinaryFraction_helper(
                32,
                8,
                "-5 >> 20",
                "[234893 >> 19, 50635417337374715 >> 20, -4033 >> 30, 12795 >> 20, -20969159 >> 42, 115 >> 15," +
                " 151983 >> 20, 13 >> 19, 2555 >> 20, 60919259095815086189 >> 20, 97337 >> 21," +
                " 115520513794470430615 >> 21, 4339 >> 23, 533422527609121 >> 23, 15341235 >> 22, 1123 >> 25," +
                " 64251 >> 20, 3580633403 >> 25, 1916475 >> 20, 4536827 >> 20]",
                "{-5 >> 20=30182, -1 >> 18=3966, -1 >> 19=2712, -9 >> 21=1750, -3 >> 20=1750, -1 >> 20=1604," +
                " -19 >> 22=1565, 0=1459, -39 >> 23=1410, 3 >> 20=1392}",
                6.149029890582127E119,
                36.9378720000084,
                23.766452999822757
        );
        rangeUp_BinaryFraction_fail_helper(1, 0, "0");
        rangeUp_BinaryFraction_fail_helper(0, 1, "0");
    }
    
    private static void nextFromRangeDown_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeDown(BinaryFraction.read(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeDown_BinaryFraction_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeDown(BinaryFraction.read(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeDown_BinaryFraction() {
        nextFromRangeDown_BinaryFraction_helper(1, 1, "0", "-5");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "0", "-21");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "0", "-469791");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "1", "-1 << 2");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "1", "-5 << 2");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "1", "-234895 << 1");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "11", "3 << 1");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "11", "-5 << 1");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "11", "-117445 << 2");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "5 << 20", "0");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "5 << 20", "-1 << 24");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "5 << 20", "-234893 << 21");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "5 >> 20", "0");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "5 >> 20", "-1 >> 16");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "5 >> 20", "-234893 >> 19");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "-1", "-3 << 1");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "-1", "-11 << 1");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "-1", "-14681 << 5");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "-11", "-1 << 4");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "-11", "-1 << 5");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "-11", "-234901 << 1");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "-5 << 20", "-5 << 21");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "-5 << 20", "-13 << 21");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "-5 << 20", "-117449 << 22");
        nextFromRangeDown_BinaryFraction_helper(1, 1, "-5 >> 20", "-5 >> 19");
        nextFromRangeDown_BinaryFraction_helper(5, 3, "-5 >> 20", "-13 >> 19");
        nextFromRangeDown_BinaryFraction_helper(32, 8, "-5 >> 20", "-117449 >> 18");
        nextFromRangeDown_BinaryFraction_fail_helper(1, 0, "0");
        nextFromRangeDown_BinaryFraction_fail_helper(0, 1, "0");
    }

    private static void rangeDown_BinaryFraction_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(BinaryFraction.read(a).get()),
                output,
                topSampleCount,
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
                "[-5, -221 << 3, 0, -1 << 1, -1 >> 3, 0, 0, 0, -1 >> 3, -1 << 2, 0, 0, 0, 0, 0, 0, -1 << 1, -5 << 5," +
                " 0, -21 << 2]",
                "{0=499925, -1=93359, -3=33788, -1 << 1=31382, -1 >> 1=31316, -1 >> 2=21043, -1 << 2=20806," +
                " -1 << 3=13962, -1 >> 3=13774, -3 << 1=11402}",
                -20405.38097776243,
                0.9597509999985684,
                1.0004139999980264
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "0",
                "[-21, -13 >> 2, -731935, -61 >> 4, -25 << 8, -1661015 >> 6, -67 << 1, -5785 >> 2, -11 >> 7, 0," +
                " -69 >> 13, -45 >> 1, -3 >> 3, -9 >> 6, 0, -3 >> 8, -1 << 2, -113 >> 1, 0, -9]",
                "{0=166417, -1=36422, -3=21629, -1 << 1=14459, -1 >> 1=14312, -1 << 2=11287, -1 >> 2=11201," +
                " -5=10374, -7=8954, -1 >> 3=8734}",
                -7.8980066065349693E18,
                4.954361000006467,
                3.007180999992695
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "0",
                "[-469791, -24724324871765 << 11, -1087 >> 10, -25 << 9, -2361 >> 22, -3685, -37997 << 2, -31," +
                " -5 << 9, -30459629547907543097 << 1, -97347 >> 1, -115520513794470430625 >> 1, -4379 >> 3," +
                " -533422527609161 >> 3, -15341255 >> 2, -1283 >> 5, -251 << 8, -3580633563 >> 5, -29945 << 6," +
                " -8861 << 9]",
                "{0=30182, -1=3966, -3=2712, -1 >> 1=1750, -1 << 1=1750, -1 << 2=1604, -1 >> 2=1565, -5=1459," +
                " -1 >> 3=1410, -1 << 3=1392}",
                -6.447725166547045E125,
                32.013006000028064,
                7.994682000015932
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "1",
                "[-1 << 2, -1767, 1, -1, 7 >> 3, 1, 1, 1, 7 >> 3, -3, 1, 1, 1, 1, 1, 1, -1, -159, 1, -83]",
                "{1=499925, 0=93359, -1 << 1=33788, -1=31382, 1 >> 1=31316, 3 >> 2=21043, -3=20806, -7=13962," +
                " 7 >> 3=13774, -5=11402}",
                -20404.38097764382,
                1.8874289999759444,
                0.601121999998552
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "1",
                "[-5 << 2, -9 >> 2, -365967 << 1, -45 >> 4, -6399, -1660951 >> 6, -133, -5781 >> 2, 117 >> 7, 1," +
                " 8123 >> 13, -43 >> 1, 5 >> 3, 55 >> 6, 1, 253 >> 8, -3, -111 >> 1, 1, -1 << 3]",
                "{1=166417, 0=36422, -1 << 1=21629, -1=14459, 1 >> 1=14312, -3=11287, 3 >> 2=11201, -1 << 2=10374," +
                " -3 << 1=8954, 7 >> 3=8734}",
                -7.8980066065349693E18,
                6.7572410000217555,
                1.7570869999935141
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "1",
                "[-234895 << 1, -50635417337374719, -63 >> 10, -12799, 4191943 >> 22, -921 << 2, -151987, -15 << 1," +
                " -2559, -60919259095815086193, -97345 >> 1, -115520513794470430623 >> 1, -4371 >> 3," +
                " -533422527609153 >> 3, -15341251 >> 2, -1251 >> 5, -64255, -3580633531 >> 5, -1916479, -4536831]",
                "{1=30182, 0=3966, -1 << 1=2712, 1 >> 1=1750, -1=1750, -3=1604, 3 >> 2=1565, -1 << 2=1459," +
                " 7 >> 3=1410, -7=1392}",
                -6.447725166547045E125,
                36.58280200001777,
                4.194993999998781
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "11",
                "[3 << 1, -1757, 11, 9, 87 >> 3, 11, 11, 11, 87 >> 3, 7, 11, 11, 11, 11, 11, 11, 9, -149, 11, -73]",
                "{11=499925, 5 << 1=93359, 1 << 3=33788, 9=31382, 21 >> 1=31316, 43 >> 2=21043, 7=20806, 3=13962," +
                " 87 >> 3=13774, 5=11402}",
                -20394.380977771583,
                4.305438999999481,
                0.7516849999983759
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "11",
                "[-5 << 1, 31 >> 2, -182981 << 2, 115 >> 4, -6389, -1660311 >> 6, -123, -5741 >> 2, 1397 >> 7, 11," +
                " 90043 >> 13, -23 >> 1, 85 >> 3, 695 >> 6, 11, 2813 >> 8, 7, -91 >> 1, 11, 1 << 1]",
                "{11=166417, 5 << 1=36422, 1 << 3=21629, 9=14459, 21 >> 1=14312, 7=11287, 43 >> 2=11201," +
                " 3 << 1=10374, 1 << 2=8954, 87 >> 3=8734}",
                -7.8980066065349693E18,
                8.077762999986804,
                1.8205389999923367
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "11",
                "[-117445 << 2, -50635417337374709, 10177 >> 10, -12789, 46134983 >> 22, -1837 << 1, -151977," +
                " -5 << 2, -2549, -60919259095815086183, -97325 >> 1, -115520513794470430603 >> 1, -4291 >> 3," +
                " -533422527609073 >> 3, -15341211 >> 2, -931 >> 5, -64245, -3580633211 >> 5, -1916469, -4536821]",
                "{11=30182, 5 << 1=3966, 1 << 3=2712, 21 >> 1=1750, 9=1750, 7=1604, 43 >> 2=1565, 3 << 1=1459," +
                " 87 >> 3=1410, 3=1392}",
                -6.447725166547045E125,
                37.10622500001412,
                4.201928999998755
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "5 << 20",
                "[0, -1763 << 20, 5 << 20, 3 << 20, 39 << 17, 5 << 20, 5 << 20, 5 << 20, 39 << 17, 1 << 20, 5 << 20," +
                " 5 << 20, 5 << 20, 5 << 20, 5 << 20, 5 << 20, 3 << 20, -155 << 20, 5 << 20, -79 << 20]",
                "{5 << 20=499925, 1 << 22=93359, 1 << 21=33788, 3 << 20=31382, 9 << 19=31316, 19 << 18=21043," +
                " 1 << 20=20806, -3 << 20=13962, 39 << 17=13774, -1 << 20=11402}",
                -2.1391349883171562E10,
                3.34172299998717,
                19.553109999772616
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "5 << 20",
                "[-1 << 24, 7 << 18, -365965 << 21, 19 << 16, -6395 << 20, -1660695 << 14, -129 << 20, -5765 << 18," +
                " 629 << 13, 5 << 20, 40891 << 7, -35 << 19, 37 << 17, 311 << 14, 5 << 20, 1277 << 12, 1 << 20," +
                " -103 << 19, 5 << 20, -1 << 22]",
                "{5 << 20=166417, 1 << 22=36422, 1 << 21=21629, 3 << 20=14459, 9 << 19=14312, 1 << 20=11287," +
                " 19 << 18=11201, 0=10374, -1 << 21=8954, 39 << 17=8734}",
                -8.281660175454012E24,
                7.570154000037757,
                18.615274999850975
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "5 << 20",
                "[-234893 << 21, -50635417337374715 << 20, 4033 << 10, -12795 << 20, 20969159 >> 2, -115 << 25," +
                " -151983 << 20, -13 << 21, -2555 << 20, -60919259095815086189 << 20, -97337 << 19," +
                " -115520513794470430615 << 19, -4339 << 17, -533422527609121 << 17, -15341235 << 18, -1123 << 15," +
                " -64251 << 20, -3580633403 << 15, -1916475 << 20, -4536827 << 20]",
                "{5 << 20=30182, 1 << 22=3966, 1 << 21=2712, 9 << 19=1750, 3 << 20=1750, 1 << 20=1604," +
                " 19 << 18=1565, 0=1459, 39 << 17=1410, -3 << 20=1392}",
                -6.7609298642372346E131,
                36.9378720000084,
                16.980748999850196
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "5 >> 20",
                "[0, -1763 >> 20, 5 >> 20, 3 >> 20, 39 >> 23, 5 >> 20, 5 >> 20, 5 >> 20, 39 >> 23, 1 >> 20, 5 >> 20," +
                " 5 >> 20, 5 >> 20, 5 >> 20, 5 >> 20, 5 >> 20, 3 >> 20, -155 >> 20, 5 >> 20, -79 >> 20]",
                "{5 >> 20=499925, 1 >> 18=93359, 1 >> 19=33788, 3 >> 20=31382, 9 >> 21=31316, 19 >> 22=21043," +
                " 1 >> 20=20806, -3 >> 20=13962, 39 >> 23=13774, -1 >> 20=11402}",
                -0.019455319382515483,
                3.34172299998717,
                20.019541999790963
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "5 >> 20",
                "[-1 >> 16, 7 >> 22, -365965 >> 19, 19 >> 24, -6395 >> 20, -1660695 >> 26, -129 >> 20, -5765 >> 22," +
                " 629 >> 27, 5 >> 20, 40891 >> 33, -35 >> 21, 37 >> 23, 311 >> 26, 5 >> 20, 1277 >> 28, 1 >> 20," +
                " -103 >> 21, 5 >> 20, -1 >> 18]",
                "{5 >> 20=166417, 1 >> 18=36422, 1 >> 19=21629, 3 >> 20=14459, 9 >> 21=14312, 1 >> 20=11287," +
                " 19 >> 22=11201, 0=10374, -1 >> 19=8954, 39 >> 23=8734}",
                -7.532126051459283E12,
                7.570154000037757,
                20.992976999858303
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "5 >> 20",
                "[-234893 >> 19, -50635417337374715 >> 20, 4033 >> 30, -12795 >> 20, 20969159 >> 42, -115 >> 15," +
                " -151983 >> 20, -13 >> 19, -2555 >> 20, -60919259095815086189 >> 20, -97337 >> 21," +
                " -115520513794470430615 >> 21, -4339 >> 23, -533422527609121 >> 23, -15341235 >> 22, -1123 >> 25," +
                " -64251 >> 20, -3580633403 >> 25, -1916475 >> 20, -4536827 >> 20]",
                "{5 >> 20=30182, 1 >> 18=3966, 1 >> 19=2712, 9 >> 21=1750, 3 >> 20=1750, 1 >> 20=1604," +
                " 19 >> 22=1565, 0=1459, 39 >> 23=1410, -3 >> 20=1392}",
                -6.149029890582128E119,
                36.9378720000084,
                23.766452999822757
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-1",
                "[-3 << 1, -1769, -1, -3, -9 >> 3, -1, -1, -1, -9 >> 3, -5, -1, -1, -1, -1, -1, -1, -3, -161, -1," +
                " -85]",
                "{-1=499925, -1 << 1=93359, -1 << 2=33788, -3=31382, -3 >> 1=31316, -5 >> 2=21043, -5=20806," +
                " -9=13962, -9 >> 3=13774, -7=11402}",
                -20406.380977936788,
                2.2737089999847506,
                0.7358969999983083
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-1",
                "[-11 << 1, -17 >> 2, -22873 << 5, -77 >> 4, -6401, -1661079 >> 6, -135, -5789 >> 2, -139 >> 7, -1," +
                " -8261 >> 13, -47 >> 1, -11 >> 3, -73 >> 6, -1, -259 >> 8, -5, -115 >> 1, -1, -5 << 1]",
                "{-1=166417, -1 << 1=36422, -1 << 2=21629, -3=14459, -3 >> 1=14312, -5=11287, -5 >> 2=11201," +
                " -3 << 1=10374, -1 << 3=8954, -9 >> 3=8734}",
                -7.8980066065349693E18,
                7.159625000020588,
                1.8263119999926671
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-1",
                "[-14681 << 5, -50635417337374721, -2111 >> 10, -12801, -4196665 >> 22, -1843 << 1, -151989," +
                " -1 << 5, -2561, -60919259095815086195, -97349 >> 1, -115520513794470430627 >> 1, -4387 >> 3," +
                " -533422527609169 >> 3, -15341259 >> 2, -1315 >> 5, -64257, -3580633595 >> 5, -1916481, -4536833]",
                "{-1=30182, -1 << 1=3966, -1 << 2=2712, -3 >> 1=1750, -3=1750, -5=1604, -5 >> 2=1565, -3 << 1=1459," +
                " -9 >> 3=1410, -9=1392}",
                -6.447725166547045E125,
                36.763259000017705,
                4.203809999998741
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-11",
                "[-1 << 4, -1779, -11, -13, -89 >> 3, -11, -11, -11, -89 >> 3, -15, -11, -11, -11, -11, -11, -11," +
                " -13, -171, -11, -95]",
                "{-11=499925, -3 << 2=93359, -7 << 1=33788, -13=31382, -23 >> 1=31316, -45 >> 2=21043, -15=20806," +
                " -19=13962, -89 >> 3=13774, -17=11402}",
                -20416.3809777996,
                4.556464999980296,
                0.8102169999972612
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-11",
                "[-1 << 5, -57 >> 2, -365973 << 1, -237 >> 4, -6411, -1661719 >> 6, -145, -5829 >> 2, -1419 >> 7," +
                " -11, -90181 >> 13, -67 >> 1, -91 >> 3, -713 >> 6, -11, -2819 >> 8, -15, -135 >> 1, -11, -5 << 2]",
                "{-11=166417, -3 << 2=36422, -7 << 1=21629, -13=14459, -23 >> 1=14312, -15=11287, -45 >> 2=11201," +
                " -1 << 4=10374, -9 << 1=8954, -89 >> 3=8734}",
                -7.8980066065349693E18,
                8.511026999979812,
                1.8539969999931307
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-11",
                "[-234901 << 1, -50635417337374731, -12351 >> 10, -12811, -46139705 >> 22, -231 << 4, -151999," +
                " -21 << 1, -2571, -60919259095815086205, -97369 >> 1, -115520513794470430647 >> 1, -4467 >> 3," +
                " -533422527609249 >> 3, -15341299 >> 2, -1635 >> 5, -64267, -3580633915 >> 5, -1916491, -4536843]",
                "{-11=30182, -3 << 2=3966, -7 << 1=2712, -23 >> 1=1750, -13=1750, -15=1604, -45 >> 2=1565," +
                " -1 << 4=1459, -89 >> 3=1410, -19=1392}",
                -6.447725166547045E125,
                37.24737200001865,
                4.206839999998712
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-5 << 20",
                "[-5 << 21, -1773 << 20, -5 << 20, -7 << 20, -41 << 17, -5 << 20, -5 << 20, -5 << 20, -41 << 17," +
                " -9 << 20, -5 << 20, -5 << 20, -5 << 20, -5 << 20, -5 << 20, -5 << 20, -7 << 20, -165 << 20," +
                " -5 << 20, -89 << 20]",
                "{-5 << 20=499925, -3 << 21=93359, -1 << 23=33788, -7 << 20=31382, -11 << 19=31316, -21 << 18=21043," +
                " -9 << 20=20806, -13 << 20=13962, -41 << 17=13774, -11 << 20=11402}",
                -2.1401835644499287E10,
                3.7599269999773086,
                19.763109999771977
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-5 << 20",
                "[-13 << 21, -33 << 18, -182985 << 22, -141 << 16, -6405 << 20, -1661335 << 14, -139 << 20," +
                " -5805 << 18, -651 << 13, -5 << 20, -41029 << 7, -55 << 19, -43 << 17, -329 << 14, -5 << 20," +
                " -1283 << 12, -9 << 20, -123 << 19, -5 << 20, -7 << 21]",
                "{-5 << 20=166417, -3 << 21=36422, -1 << 23=21629, -7 << 20=14459, -11 << 19=14312, -9 << 20=11287," +
                " -21 << 18=11201, -5 << 21=10374, -3 << 22=8954, -41 << 17=8734}",
                -8.281660175454012E24,
                8.010092000028635,
                18.854625999852182
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-5 << 20",
                "[-117449 << 22, -50635417337374725 << 20, -6207 << 10, -12805 << 20, -20973881 >> 2, -1845 << 21," +
                " -151993 << 20, -9 << 22, -2565 << 20, -60919259095815086199 << 20, -97357 << 19," +
                " -115520513794470430635 << 19, -4419 << 17, -533422527609201 << 17, -15341275 << 18, -1443 << 15," +
                " -64261 << 20, -3580633723 << 15, -1916485 << 20, -4536837 << 20]",
                "{-5 << 20=30182, -3 << 21=3966, -1 << 23=2712, -11 << 19=1750, -7 << 20=1750, -9 << 20=1604," +
                " -21 << 18=1565, -5 << 21=1459, -41 << 17=1410, -13 << 20=1392}",
                -6.7609298642372346E131,
                37.07091300000956,
                17.015772999850565
        );
        rangeDown_BinaryFraction_helper(
                1,
                1,
                "-5 >> 20",
                "[-5 >> 19, -1773 >> 20, -5 >> 20, -7 >> 20, -41 >> 23, -5 >> 20, -5 >> 20, -5 >> 20, -41 >> 23," +
                " -9 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -5 >> 20, -7 >> 20, -165 >> 20," +
                " -5 >> 20, -89 >> 20]",
                "{-5 >> 20=499925, -3 >> 19=93359, -1 >> 17=33788, -7 >> 20=31382, -11 >> 21=31316, -21 >> 22=21043," +
                " -9 >> 20=20806, -13 >> 20=13962, -41 >> 23=13774, -11 >> 20=11402}",
                -0.019464856126887104,
                3.7599269999773086,
                20.237141999770692
        );
        rangeDown_BinaryFraction_helper(
                5,
                3,
                "-5 >> 20",
                "[-13 >> 19, -33 >> 22, -182985 >> 18, -141 >> 24, -6405 >> 20, -1661335 >> 26, -139 >> 20," +
                " -5805 >> 22, -651 >> 27, -5 >> 20, -41029 >> 33, -55 >> 21, -43 >> 23, -329 >> 26, -5 >> 20," +
                " -1283 >> 28, -9 >> 20, -123 >> 21, -5 >> 20, -7 >> 19]",
                "{-5 >> 20=166417, -3 >> 19=36422, -1 >> 17=21629, -7 >> 20=14459, -11 >> 21=14312, -9 >> 20=11287," +
                " -21 >> 22=11201, -5 >> 19=10374, -3 >> 18=8954, -41 >> 23=8734}",
                -7.532126051459283E12,
                8.010092000028635,
                21.16858599984985
        );
        rangeDown_BinaryFraction_helper(
                32,
                8,
                "-5 >> 20",
                "[-117449 >> 18, -50635417337374725 >> 20, -6207 >> 30, -12805 >> 20, -20973881 >> 42, -1845 >> 19," +
                " -151993 >> 20, -9 >> 18, -2565 >> 20, -60919259095815086199 >> 20, -97357 >> 21," +
                " -115520513794470430635 >> 21, -4419 >> 23, -533422527609201 >> 23, -15341275 >> 22, -1443 >> 25," +
                " -64261 >> 20, -3580633723 >> 25, -1916485 >> 20, -4536837 >> 20]",
                "{-5 >> 20=30182, -3 >> 19=3966, -1 >> 17=2712, -11 >> 21=1750, -7 >> 20=1750, -9 >> 20=1604," +
                " -21 >> 22=1565, -5 >> 19=1459, -41 >> 23=1410, -13 >> 20=1392}",
                -6.149029890582128E119,
                37.07091300000956,
                23.789788999822175
        );
        rangeDown_BinaryFraction_fail_helper(1, 0, "0");
        rangeDown_BinaryFraction_fail_helper(0, 1, "0");
    }

    private static void nextFromRange_BinaryFraction_BinaryFraction_helper(
            int meanDivisionSize,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeq(
                P.withScale(meanDivisionSize)
                        .nextFromRange(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRange_BinaryFraction_BinaryFraction_fail_helper(
            int meanDivisionSize,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(meanDivisionSize).nextFromRange(BinaryFraction.read(a).get(), BinaryFraction.read(b).get());
            fail();
        } catch (IllegalStateException | IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRange_BinaryFraction_BinaryFraction() {
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "0", "1", "3 >> 3");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "0", "1", "1180057081 >> 31");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "0", "1", "415293 >> 19");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "1", "11", "7 >> 2");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "1", "11", "6548766201 >> 30");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "1", "11", "1726013 >> 18");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "11", "11", "11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "11", "11", "11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "11", "11", "11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "-11", "-1", "-41 >> 2");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "-11", "-1", "-6336135687 >> 30");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "-11", "-1", "-1419715 >> 18");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "-11", "-11", "-11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "-11", "-11", "-11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "-11", "-11", "-11");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "0", "0", "0");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "0", "0", "0");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "0", "0", "0");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "0", "11", "67 >> 3");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "0", "11", "16212442617 >> 31");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "0", "11", "4085309 >> 19");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "-1", "11", "1 >> 1");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "-1", "11", "4938153465 >> 29");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "-1", "11", "1332797 >> 17");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "5 >> 20", "1", "8000811 >> 23");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "5 >> 20", "1", "1120933957221881 >> 51");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "5 >> 20", "1", "273665644093 >> 39");
        nextFromRange_BinaryFraction_BinaryFraction_helper(1, "1", "5 << 20", "33166603 >> 3");
        nextFromRange_BinaryFraction_BinaryFraction_helper(8, "1", "5 << 20", "7876324808343033 >> 31");
        nextFromRange_BinaryFraction_BinaryFraction_helper(32, "1", "5 << 20", "1922930988605 >> 19");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(0, "0", "1");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(-1, "0", "1");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(Integer.MAX_VALUE, "0", "1");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(1, "11", "1");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(1, "-1", "-11");
    }

    private static void range_BinaryFraction_BinaryFraction_helper(
            int meanDivisionSize,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double mantissaBitSizeMean,
            double exponentMean
    ) {
        binaryFractionHelper(
                P.withScale(meanDivisionSize).range(BinaryFraction.read(a).get(), BinaryFraction.read(b).get()),
                output,
                topSampleCount,
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
                "[3 >> 3, 1 >> 1, 443 >> 9, 1 >> 1, 1 >> 2, 1 >> 1, 1 >> 3, 1, 1 >> 1, 3 >> 2, 1 >> 1, 1, 1 >> 1, 0," +
                " 1 >> 1, 1 >> 1, 1, 1, 1 >> 1, 1 >> 2]",
                "{1 >> 1=250514, 0=250286, 1=249032, 1 >> 2=62448, 3 >> 2=62363, 1 >> 3=15836, 5 >> 3=15749," +
                " 7 >> 3=15736, 3 >> 3=15454, 1 >> 4=3957}",
                0.4993035895794412,
                1.0832809999980395,
                1.0011419999976825
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "1",
                "[1180057081 >> 31, 209 >> 8, 83 >> 8, 11 >> 5, 217655603 >> 28, 78489 >> 17, 0, 25 >> 5, 1," +
                " 1657 >> 17, 7511 >> 13, 1, 109 >> 9, 17 >> 5, 1 >> 2, 1 >> 3, 1 >> 1, 3 >> 2, 13 >> 4, 401 >> 10]",
                "{1 >> 1=98893, 1=55598, 0=55366, 3 >> 2=44095, 1 >> 2=43860, 3 >> 3=19782, 5 >> 3=19682," +
                " 7 >> 3=19579, 1 >> 3=19222, 7 >> 4=8752}",
                0.500473623506543,
                7.342950000026489,
                7.997435000016672
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "1",
                "[415293 >> 19, 84633021832363 >> 47, 8407883 >> 25, 1, 1657 >> 18, 160365 >> 21, 233 >> 8," +
                " 7577 >> 13, 3275 >> 12, 141529 >> 19, 209 >> 9, 61 >> 6, 43929529225746533347887 >> 77," +
                " 63625 >> 19, 378614980178617274181 >> 69, 25143 >> 16, 6696344589431441 >> 53, 7 >> 4," +
                " 1627181 >> 23, 16903 >> 15]",
                "{1 >> 1=29323, 0=15071, 1=15032, 3 >> 2=14367, 1 >> 2=14018, 1 >> 3=6970, 7 >> 3=6917, 3 >> 3=6876," +
                " 5 >> 3=6851, 3 >> 4=3529}",
                0.50070604284837,
                31.14941800003445,
                32.04546700002099
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "1",
                "11",
                "[7 >> 2, 1211 >> 8, 3, 9, 5 << 1, 3, 7, 3 << 1, 15 >> 1, 11, 9, 1 << 3, 5 << 1, 7, 5, 3 << 1, 11," +
                " 5, 1, 5]",
                "{11=83680, 3=83615, 9=83390, 1=83378, 5=83277, 7=83198, 3 << 1=49988, 1 << 3=49922, 5 << 1=49903," +
                " 1 << 2=49889}",
                6.000429698001397,
                3.0411059999898256,
                0.8980329999976231
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "1",
                "11",
                "[6548766201 >> 30, 177 >> 5, 979 >> 7, 67 >> 3, 116992307 >> 25, 144025 >> 16, 41 >> 4," +
                " 329337 >> 16, 11607 >> 12, 1645 >> 9, 33 >> 4, 5, 37 >> 2, 1 << 2, 13 >> 1, 33 >> 2, 657 >> 8," +
                " 15 >> 2, 7 >> 1, 175 >> 4]",
                "{3 << 1=19780, 1 << 2=19766, 1 << 1=19738, 1 << 3=19627, 5 << 1=19573, 1=18615, 3=18614, 11=18608," +
                " 9=18584, 5=18538}",
                5.999617188270735,
                9.872437000001373,
                7.265178000023123
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "1",
                "11",
                "[1726013 >> 18, 119817393921195 >> 45, 25185099 >> 24, 1574521 >> 18, 4878957 >> 19, 617 >> 7," +
                " 21913 >> 11, 8395 >> 10, 534745 >> 17, 1183 >> 7, 109 >> 4, 383939915992360988733999 >> 75," +
                " 2422921 >> 18, 1411632648306352164677 >> 67, 107063 >> 14, 22458943285228177 >> 51, 5 >> 1," +
                " 20501549 >> 21, 33287 >> 14, 325 >> 5]",
                "{1 << 2=5844, 1 << 1=5802, 3 << 1=5800, 1 << 3=5796, 5 << 1=5788, 11=5196, 1=5116, 7=5095, 5=5073," +
                " 9=5004}",
                6.006316830325357,
                33.93414200002559,
                31.118477000020434
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "11",
                "11",
                "[11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11]",
                "{11=1000000}",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "11",
                "11",
                "[11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11]",
                "{11=1000000}",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "11",
                "11",
                "[11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11]",
                "{11=1000000}",
                11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(1, "11", "1", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(8, "11", "1", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(32, "11", "1", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-11",
                "-1",
                "[-41 >> 2, -1861 >> 8, -9, -3, -1 << 1, -9, -5, -3 << 1, -9 >> 1, -1, -3, -1 << 2, -1 << 1, -5, -7," +
                " -3 << 1, -1, -7, -11, -7]",
                "{-1=83680, -9=83615, -3=83390, -11=83378, -7=83277, -5=83198, -3 << 1=49988, -1 << 2=49922," +
                " -1 << 1=49903, -1 << 3=49889}",
                -5.99957030201959,
                3.041002999989865,
                0.8979999999976223
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-11",
                "-1",
                "[-6336135687 >> 30, -207 >> 5, -557 >> 7, -29 >> 3, -285660877 >> 25, -642407 >> 16, -151 >> 4," +
                " -457095 >> 16, -37545 >> 12, -4499 >> 9, -159 >> 4, -7, -11 >> 2, -1 << 3, -11 >> 1, -15 >> 2," +
                " -2415 >> 8, -33 >> 2, -17 >> 1, -17 >> 4]",
                "{-3 << 1=19780, -1 << 3=19766, -5 << 1=19738, -1 << 2=19627, -1 << 1=19573, -11=18615, -9=18614," +
                " -1=18608, -3=18584, -7=18538}",
                -6.000382811731497,
                9.872120000001026,
                7.265317000023163
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-11",
                "-1",
                "[-1419715 >> 18, -302395071144789 >> 45, -176141493 >> 24, -1571207 >> 18, -1412499 >> 19," +
                " -919 >> 7, -2663 >> 11, -3893 >> 10, -1038119 >> 17, -353 >> 7, -83 >> 4," +
                " -69407266363124951780817 >> 75, -722807 >> 18, -359254782769764790459 >> 67, -89545 >> 14," +
                " -4562654478994799 >> 51, -19 >> 1, -4664275 >> 21, -163321 >> 14, -59 >> 5]",
                "{-1 << 3=5844, -5 << 1=5802, -3 << 1=5800, -1 << 2=5796, -1 << 1=5788, -1=5196, -11=5116, -5=5095," +
                " -7=5073, -3=5004}",
                -5.993683169675392,
                33.930533000025925,
                31.118525000020508
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-11",
                "-11",
                "[-11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11]",
                "{-11=1000000}",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-11",
                "-11",
                "[-11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11]",
                "{-11=1000000}",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-11",
                "-11",
                "[-11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11]",
                "{-11=1000000}",
                -11.000000000125029,
                4.000000000031672,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(1, "-1", "-11", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(8, "-1", "-11", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(32, "-1", "-11", "[]", "{}", 0.0, 0.0, 0.0);
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "0",
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
                "{0=1000000}",
                0.0,
                0.0,
                0.0
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "0",
                "11",
                "[67 >> 3, 955 >> 9, 1, 5, 41 >> 2, 11 >> 2, 7, 9 >> 1, 3, 5, 1 << 2, 7 >> 1, 11 >> 1, 5 >> 1," +
                " 21 >> 1, 21 >> 1, 5 << 1, 0, 5 << 1, 1 << 3]",
                "{3 << 1=42051, 1=41931, 9=41930, 5 << 1=41928, 3=41814, 1 << 3=41812, 7=41697, 5=41627," +
                " 1 << 2=41623, 1 << 1=41543}",
                5.500143323903409,
                3.3425789999888273,
                1.332903999990955
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "0",
                "11",
                "[16212442617 >> 31, 1361 >> 7, 1363 >> 8, 107 >> 4, 1291397427 >> 27, 78489 >> 17, 25 >> 5," +
                " 1312377 >> 17, 7511 >> 13, 9325 >> 10, 241 >> 5, 21 >> 1, 25 >> 3, 9, 27 >> 2, 29 >> 3, 3985 >> 9," +
                " 611 >> 6, 29 >> 2, 159 >> 5]",
                "{1 << 3=9489, 1=9357, 5=9323, 3=9296, 5 << 1=9280, 0=9278, 1 << 2=9275, 9=9272, 7=9261, 11=9219}",
                5.506818198260208,
                10.507393999999584,
                8.069741000021585
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "0",
                "11",
                "[4085309 >> 19, 1351270417030315 >> 47, 8407883 >> 25, 5506681 >> 19, 4354669 >> 20, 2537 >> 8," +
                " 15769 >> 12, 1739 >> 9, 2500825 >> 18, 1055 >> 8, 93 >> 5, 346160984129403827024431 >> 76," +
                " 1636489 >> 19, 1264058695716675751749 >> 68, 254519 >> 15, 33717942353654417 >> 52, 7 >> 3," +
                " 18404397 >> 22, 279047 >> 15, 485 >> 6]",
                "{17 >> 1=2747, 11 >> 1=2712, 3 >> 1=2712, 13 >> 1=2641, 21 >> 1=2640, 7 >> 1=2635, 5=2617," +
                " 1 << 3=2609, 9 >> 1=2605, 19 >> 1=2597}",
                5.496573980658701,
                34.57617900002571,
                32.06209300002209
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "-1",
                "11",
                "[1 >> 1, 827 >> 7, 11, 1, 5, 1 << 3, 5 << 1, 1 << 2, -1, 11, 3, -1, 9, 1 << 3, 9, 9, 3, 7, -1, 7]",
                "{3=125273, 7=125229, 11=124796, -1=124433, 1=83438, 9=83387, 5=83335, 5 << 1=21092, 3 << 1=21031," +
                " 0=20920}",
                5.0041785453242,
                2.6294259999877116,
                0.4159939999985251
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "-1",
                "11",
                "[4938153465 >> 29, 129 >> 4, 275 >> 6, 39 >> 2, 318318899 >> 25, 45721 >> 15, 17 >> 3," +
                " 231033 >> 15, 5463 >> 11, 877 >> 8, 9 >> 3, 7, 15 >> 1, 9, 5 << 1, 19 >> 1, 273 >> 7, 9 >> 1," +
                " 1 << 2, 23 >> 3]",
                "{1=32981, 5=32886, 9=32827, -1=28066, 3=27753, 7=27650, 11=27649, 1 << 1=14801, 3 << 1=14766," +
                " 1 << 2=14649}",
                4.9984930497873075,
                8.580130000010076,
                6.434966000015122
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "-1",
                "11",
                "[1332797 >> 17, 67040835787947 >> 44, 19275 >> 23, 1181305 >> 17, -101779 >> 18, 425 >> 6," +
                " 2457 >> 10, 4811 >> 9, 108761 >> 15, -33 >> 6, 85 >> 3, 25040063294267952493103 >> 74," +
                " -67447 >> 17, 9680098704426241861 >> 66, 82487 >> 13, 1066845055218321 >> 50, 3 << 1," +
                " 578605 >> 20, 8711 >> 13, 85 >> 4]",
                "{5=9758, 1=9716, 9=9585, 11=7714, 3=7685, -1=7566, 7=7565, 3 << 1=4874, 0=4816, 5 << 1=4771}",
                4.999216990832369,
                32.37837700003206,
                30.164890000018275
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "5 >> 20",
                "1",
                "[8000811 >> 23, 170129 >> 19, 28978731 >> 28, 1002401 >> 20, 1585985 >> 21, 694685 >> 21," +
                " 2655353 >> 22, 3978927 >> 22, 753279 >> 20, 298609 >> 21, 1692085 >> 22, 775285 >> 21," +
                " 74107 >> 18, 415303 >> 21, 736377 >> 21, 1694959 >> 21, 233813 >> 20, 659859 >> 20, 1344265 >> 21," +
                " 911723 >> 20]",
                "{67615 >> 19=6, 245407 >> 20=6, 46725 >> 17=6, 321685 >> 20=6, 136403 >> 20=6, 246227 >> 19=6," +
                " 922011 >> 20=6, 622993 >> 20=6, 396785 >> 19=6, 183645 >> 19=6}",
                0.4997954880458359,
                19.500193000030965,
                20.501135000022167
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "5 >> 20",
                "1",
                "[1120933957221881 >> 51, 88380625 >> 27, 263028563 >> 28, 14272139 >> 25, 126071198001459 >> 47," +
                " 7049523865 >> 37, 687833 >> 25, 120865293945 >> 37, 149577047 >> 33, 611845229 >> 30," +
                " 16532209 >> 25, 1428425 >> 21, 7758753 >> 23, 1935753 >> 21, 1612147 >> 22, 1652629 >> 23," +
                " 236615569 >> 29, 39580195 >> 26, 2011845 >> 22, 8704767 >> 25]",
                "{162699 >> 19=4, 1010497 >> 20=4, 1056753 >> 21=4, 282101 >> 20=4, 1758323 >> 21=4," +
                " 1593255 >> 21=4, 76675 >> 17=4, 1781765 >> 21=4, 1301317 >> 22=4, 928403 >> 20=3}",
                0.5002054052455298,
                26.88661500001311,
                27.885368000000607
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "5 >> 20",
                "1",
                "[273665644093 >> 39, 91783445038832540843 >> 67, 721227369291 >> 45, 483461432953 >> 39," +
                " 287422181997 >> 40, 152961513 >> 28, 3378613657 >> 32, 423072971 >> 31, 162120476889 >> 38," +
                " 69637919 >> 28, 5646685 >> 25, 22193201894559684478495650351 >> 96, 446224201865 >> 39," +
                " 93557542190352154768412485 >> 88, 16606585399 >> 35, 4720035811007629357713 >> 72, 14645543 >> 24," +
                " 1246511027245 >> 42, 18572624391 >> 35, 58023717 >> 26]",
                "{387475 >> 20=3, 1941127 >> 21=3, 863791 >> 21=3, 430275 >> 19=3, 70029 >> 20=3, 240523 >> 21=3," +
                " 376961 >> 21=3, 504341 >> 21=2, 368025 >> 20=2, 1920603 >> 22=2}",
                0.5002069628278691,
                51.01174300001681,
                52.01118400001777
        );
        range_BinaryFraction_BinaryFraction_helper(
                1,
                "1",
                "5 << 20",
                "[33166603 >> 3, 2326539, 208571051 >> 6, 5196701, 8299911 >> 1, 1746533 << 1, 11043945 >> 2," +
                " 16561823 >> 2, 2850427, 1041549 >> 1, 2943187 >> 1, 9163885 >> 1, 1232099 << 1, 3667699," +
                " 46023 << 3, 3792103 >> 1, 446033, 4043881, 789633 << 1, 1338061 >> 1]",
                "{3465265=4, 3686377=4, 442321 << 3=4, 3181373=4, 28283 << 6=4, 764759 << 2=4, 1513191 << 1=4," +
                " 2990855=4, 2592627=4, 337151=4}",
                2619927.4664051863,
                21.896850000033783,
                1.5024889999887836
        );
        range_BinaryFraction_BinaryFraction_helper(
                8,
                "1",
                "5 << 20",
                "[7876324808343033 >> 31, 222597841 >> 7, 1001057363 >> 8, 20261627 >> 4, 184502952995123 >> 26," +
                " 501370794649 >> 17, 687705 >> 5, 670620583545 >> 17, 34509282647 >> 13, 1685582957 >> 10," +
                " 32133905 >> 5, 9817025 >> 1, 8171761 >> 3, 10324353 >> 1, 1612131 >> 2, 10041205 >> 3," +
                " 2384097169 >> 9, 97664099 >> 6, 1005915 >> 1, 8704639 >> 5]",
                "{486047=3, 2059069=3, 410591 << 3=3, 7608275 >> 1=3, 2287183=3, 914897 << 2=3, 758759=3," +
                " 184473 << 2=3, 10074523 >> 1=3, 497965 << 1=3}",
                2622974.6827238556,
                29.287213999973005,
                8.10811100002023
        );
        range_BinaryFraction_BinaryFraction_helper(
                32,
                "1",
                "5 << 20",
                "[1922930988605 >> 19, 386930787268231945387 >> 47, 721093151563 >> 25, 2682482591353 >> 19," +
                " 4720514200173 >> 20, 210697961 >> 7, 4183944601 >> 12, 1285273803 >> 10, 400032147673 >> 18," +
                " 34818463 >> 7, 5646557 >> 5, 259877387205897793601833824815 >> 76, 1207265458313 >> 19," +
                " 1331496400884111712256233285 >> 68, 154045407799 >> 15, 14164750762348410303121 >> 52," +
                " 81754343 >> 4, 16605511210029 >> 22, 60825846279 >> 14, 259350053 >> 6]",
                "{7292371 >> 2=3, 8596637 >> 1=3, 16274799 >> 2=2, 4093595 >> 1=2, 3562063=2, 14817975 >> 2=2," +
                " 7205193 >> 1=2, 19042493 >> 2=2, 510483 << 1=2, 4335479 >> 2=2}",
                2621934.787841631,
                53.410762000035774,
                32.06946100002204
        );
        range_BinaryFraction_BinaryFraction_fail_helper(0, "0", "1");
        range_BinaryFraction_BinaryFraction_fail_helper(-1, "0", "1");
        range_BinaryFraction_BinaryFraction_fail_helper(Integer.MAX_VALUE, "0", "1");
    }

    @Test
    public void testNextPositiveFloat() {
        aeq(P.nextPositiveFloat(), 643.2779f);
        aeq(Q.nextPositiveFloat(), 2.1455476E-24f);
        aeq(R.nextPositiveFloat(), 8.930732E-36f);
    }

    @Test
    public void testPositiveFloats() {
        aeqit(take(TINY_LIMIT, P.positiveFloats()),
                "[643.2779, 2.8091642E33, 1.9718748E-28, 1.2339139E-18, 3.040928E-35, 1.4698017E-35, 1.68898314E15," +
                " 2.400072E28, 3.8939846E27, 1.351875E-17, 38.282387, 2.7031316E32, 4.925231E-36, 2.0586195E-32," +
                " 1.859597E-4, 4.636554E-23, 6.5494645E-22, 8.8230604E-7, 1.841686E31, 6.544164E-24]");
    }

    @Test
    public void testNextNegativeFloat() {
        aeq(P.nextNegativeFloat(), -0.84115845f);
        aeq(Q.nextNegativeFloat(), -1.0099123E-9f);
        aeq(R.nextNegativeFloat(), -6.976539E25f);
    }

    @Test
    public void testNegativeFloats() {
        aeqit(take(TINY_LIMIT, P.negativeFloats()),
                "[-0.84115845, -3.7940737E-33, -4.0851493E-4, -12288.927, -1.7792515E-14, -9.2656293E21," +
                " -4.730412E-34, -8.745077E29, -1.0643401E35, -4.4910484E-19, -2.8662077E31, -6.8727283E31," +
                " -9.5656956E-20, -3.1309523E21, -9.9399514E8, -1.2877654E7, -4.17301758E11, -2.006464E-12," +
                " -3.041954E25, -7.6052295E33]");
    }

    @Test
    public void testNextNonzeroFloat() {
        aeq(P.nextNonzeroFloat(), -0.84115845f);
        aeq(Q.nextNonzeroFloat(), 2.1455476E-24f);
        aeq(R.nextNonzeroFloat(), 8.930732E-36f);
    }

    @Test
    public void testNonzeroFloats() {
        aeqit(take(TINY_LIMIT, P.nonzeroFloats()),
                "[-0.84115845, 643.2779, -3.7940737E-33, -4.0851493E-4, -12288.927, -1.7792515E-14, -9.2656293E21," +
                " -4.730412E-34, -8.745077E29, 2.8091642E33, 1.9718748E-28, -1.0643401E35, -4.4910484E-19," +
                " -2.8662077E31, 1.2339139E-18, -6.8727283E31, 3.040928E-35, -9.5656956E-20, -3.1309523E21," +
                " -9.9399514E8]");
    }

    @Test
    public void testNextFloat() {
        aeq(P.nextFloat(), -0.84115845f);
        aeq(Q.nextFloat(), 2.1455476E-24f);
        aeq(R.nextFloat(), 8.930732E-36f);
    }

    @Test
    public void testFloats() {
        aeqit(take(TINY_LIMIT, P.negativeFloats()),
                "[-0.84115845, -3.7940737E-33, -4.0851493E-4, -12288.927, -1.7792515E-14, -9.2656293E21," +
                " -4.730412E-34, -8.745077E29, -1.0643401E35, -4.4910484E-19, -2.8662077E31, -6.8727283E31," +
                " -9.5656956E-20, -3.1309523E21, -9.9399514E8, -1.2877654E7, -4.17301758E11, -2.006464E-12," +
                " -3.041954E25, -7.6052295E33]");
    }

    @Test
    public void testNextPositiveDouble() {
        aeq(P.nextPositiveDouble(), 1.7536743524958916E-224);
        aeq(Q.nextPositiveDouble(), 2.4113969466699316E-192);
        aeq(R.nextPositiveDouble(), 2.0131366863465367E-283);
    }

    @Test
    public void testPositiveDoubles() {
        aeqit(take(TINY_LIMIT, P.positiveDoubles()),
                "[1.7536743524958916E-224, 2.764572212578795E-146, 3.896743264763858E-279, 1.217842565654434E-281," +
                " 2.792496130931957E218, 1.5945589508926632E257, 1.8384296161192212E-285, 1.220775439432479E-181," +
                " 1.774583871298936E-172, 2.409016063284675E-51, 8.548832445406645E247, 3.828636429008211E278," +
                " 6.640742249139187E161, 0.023971318720341354, 1.1041744221277698E-301, 1.5042403999912119E200," +
                " 2.0828213847164863E289, 7.70184820812619E-204, 2.1509695971995357E227, 7.617347059029767E-140]");
    }

    @Test
    public void testNextNegativeDouble() {
        aeq(P.nextNegativeDouble(), -0.0014243510236272453);
        aeq(Q.nextNegativeDouble(), -3.152232323573363E-58);
        aeq(R.nextNegativeDouble(), -0.23950118766162518);
    }

    @Test
    public void testNegativeDoubles() {
        aeqit(take(TINY_LIMIT, P.negativeDoubles()),
                "[-0.0014243510236272453, -2.3500148334353684E-262, -2.537597334289803E30, -4.1961757252226754E173," +
                " -1.6895331934276545E237, -1.2557234924387209E-149, -4.8550659684247044E169," +
                " -3.9265241758078536E54, -4.665139345047659E90, -3.8731262819404067E201, -2.515767423879933E-158," +
                " -2.28825237318607E268, -2.44117139354519E166, -4.515887331197634E-268, -7.214500215448524E97," +
                " -7.350277585808314E289, -2.4159398363557126E-302, -3.4183671817182867E-12, -9.24468111178914E39," +
                " -2.6105127698066958E66]");
    }

    @Test
    public void testNextNonzeroDouble() {
        aeq(P.nextNonzeroDouble(), -0.0014243510236272453);
        aeq(Q.nextNonzeroDouble(), 2.4113969466699316E-192);
        aeq(R.nextNonzeroDouble(), 2.0131366863465367E-283);
    }

    @Test
    public void testNonzeroDoubles() {
        aeqit(take(TINY_LIMIT, P.nonzeroDoubles()),
                "[-0.0014243510236272453, -2.3500148334353684E-262, -2.537597334289803E30, -4.1961757252226754E173," +
                " -1.6895331934276545E237, 1.7536743524958916E-224, -1.2557234924387209E-149," +
                " 2.764572212578795E-146, 3.896743264763858E-279, -4.8550659684247044E169, 1.217842565654434E-281," +
                " -3.9265241758078536E54, -4.665139345047659E90, -3.8731262819404067E201, 2.792496130931957E218," +
                " -2.515767423879933E-158, 1.5945589508926632E257, 1.8384296161192212E-285, -2.28825237318607E268," +
                " -2.44117139354519E166]");
    }

    @Test
    public void testNextDouble() {
        aeq(P.nextDouble(), -0.0014243510236272453);
        aeq(Q.nextDouble(), 2.4113969466699316E-192);
        aeq(R.nextDouble(), 2.0131366863465367E-283);
    }

    @Test
    public void testDoubles() {
        aeqit(take(TINY_LIMIT, P.doubles()),
                "[-0.0014243510236272453, -2.3500148334353684E-262, -2.537597334289803E30, -4.1961757252226754E173," +
                " -1.6895331934276545E237, 1.7536743524958916E-224, -1.2557234924387209E-149," +
                " 2.764572212578795E-146, 3.896743264763858E-279, -4.8550659684247044E169, 1.217842565654434E-281," +
                " -3.9265241758078536E54, -4.665139345047659E90, -3.8731262819404067E201, 2.792496130931957E218," +
                " -2.515767423879933E-158, 1.5945589508926632E257, 1.8384296161192212E-285, -2.28825237318607E268," +
                " -2.44117139354519E166]");
    }

    @Test
    public void testNextPositiveFloatUniform() {
        aeq(P.nextPositiveFloatUniform(), 9.929094E37f);
        aeq(Q.nextPositiveFloatUniform(), 2.0204882E38f);
        aeq(R.nextPositiveFloatUniform(), 9.500816E37f);
    }

    @Test
    public void testPositiveFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.positiveFloatsUniform()),
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]");
    }

    @Test
    public void testNextNegativeFloatUniform() {
        aeq(P.nextNegativeFloatUniform(), -9.929094E37f);
        aeq(Q.nextNegativeFloatUniform(), -2.0204882E38f);
        aeq(R.nextNegativeFloatUniform(), -9.500816E37f);
    }

    @Test
    public void testNegativeFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.negativeFloatsUniform()),
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]");
    }

    @Test
    public void testNextNonzeroFloatUniform() {
        aeq(P.nextNonzeroFloatUniform(), 9.929094E37f);
        aeq(Q.nextNonzeroFloatUniform(), -2.0204882E38f);
        aeq(R.nextNonzeroFloatUniform(), 9.500816E37f);
    }

    @Test
    public void testNonzeroFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.nonzeroFloatsUniform()),
                "[9.929094E37, 1.9126927E38, 2.3923068E38, 2.7261729E38, 3.0669539E38, 2.5483572E38, -2.3865865E38," +
                " 2.584016E38, -1.8050742E38, 1.6911248E38, 3.2878975E37, -2.938879E38, -2.2382738E38, 2.366022E38," +
                " 2.0822496E38, 2.130368E37, -2.2906584E38, 1.7886733E38, 3.0884502E38, -7.3493103E37]");
    }

    @Test
    public void testNextFloatUniform() {
        aeq(P.nextFloatUniform(), 9.929096E37f);
        aeq(Q.nextFloatUniform(), 2.0204884E38f);
        aeq(R.nextFloatUniform(), -2.4527419E38f);
    }

    @Test
    public void testFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.floatsUniform()),
                "[9.929096E37, 3.3988666E38, -1.7763428E38, -4.1404879E37, -3.1679759E38, -1.3943378E38," +
                " -3.2630645E37, 1.2936022E38, 2.6551668E38, 3.3685458E38, -2.420504E38, -3.0740338E38," +
                " 1.6963697E38, -2.7729845E38, -6.354583E37, 8.4991115E37, -3.0125725E38, 1.1304426E38," +
                " 1.9060875E37, -1.9217403E38]");
    }

    @Test
    public void testNextPositiveDoubleUniform() {
        aeq(P.nextPositiveDoubleUniform(), 6.010031716528839E307);
        aeq(Q.nextPositiveDoubleUniform(), 1.3485310997296055E308);
        aeq(R.nextPositiveDoubleUniform(), 4.199972353586778E307);
    }

    @Test
    public void testPositiveDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.positiveDoublesUniform()),
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]");
    }

    @Test
    public void testNextNegativeDoubleUniform() {
        aeq(P.nextNegativeDoubleUniform(), -6.010031716528839E307);
        aeq(Q.nextNegativeDoubleUniform(), -1.3485310997296055E308);
        aeq(R.nextNegativeDoubleUniform(), -4.199972353586778E307);
    }

    @Test
    public void testNegativeDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.negativeDoublesUniform()),
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]");
    }

    @Test
    public void testNextNonzeroDoubleUniform() {
        aeq(P.nextNonzeroDoubleUniform(), -6.010031716528839E307);
        aeq(Q.nextNonzeroDoubleUniform(), -1.3485310997296055E308);
        aeq(R.nextNonzeroDoubleUniform(), -4.199972353586778E307);
    }

    @Test
    public void testNonzeroDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.nonzeroDoublesUniform()),
                "[-6.010031716528839E307, -4.38991984513168E306, 6.045170159795733E307, -1.2213236277546202E308," +
                " 1.093132363674069E308, -2.8350010073869094E307, 5.794375093677068E307, -6.211178609893169E307," +
                " 1.3155340847210589E308, -6.434171608796906E307, -9.114494942217177E307, 9.914564182242407E307," +
                " -8.523326292029085E306, 1.7505475135241652E308, -2.148267505726024E306, -8.64944544675027E307," +
                " -1.0680271527860177E308, 6.700517384784714E307, 1.7054593739868952E308, -1.318586604367549E308]");
    }

    @Test
    public void testNextDoubleUniform() {
        aeq(P.nextDoubleUniform(), -1.1966899632094317E308);
        aeq(Q.nextDoubleUniform(), -4.4916203513271024E307);
        aeq(R.nextDoubleUniform(), -1.3776958995036378E308);
    }

    @Test
    public void testDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.doublesUniform()),
                "[-1.1966899632094317E308, -7.838635388144489E307, -1.207725969607187E308, 1.12613974174393E307," +
                " -1.0183989834060572E308, -1.0860630224900222E308, 1.648669979822754E308, -1.516532278745868E308," +
                " 1.3981609323339557E308, -6.443348708884346E307, -9.594328496644843E307, -5.37699419358574E307," +
                " -4.0928333905911806E307, 1.2875997268467399E308, -1.2292128775815446E308, 8.174351118522929E307," +
                " 2.5109586043466566E307, 1.358911142655542E308, 1.0936400715073092E308, -2.226212499263397E307]");
    }

    private static void floatHelper(@NotNull Iterable<Float> fs, @NotNull String output, float sampleMean) {
        List<Float> sample = toList(take(DEFAULT_SAMPLE_SIZE, fs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfFloats(sample), sampleMean);
    }

    private static void doubleHelper(@NotNull Iterable<Double> ds, @NotNull String output, double sampleMean) {
        List<Double> sample = toList(take(DEFAULT_SAMPLE_SIZE, ds));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfDoubles(sample), sampleMean);
    }

    private static void nextFromRangeUp_float_helper(float a, float output) {
        aeq(P.nextFromRangeUp(a), output);
        P.reset();
    }

    private static void nextFromRangeUp_float_fail_helper(float a) {
        try {
            P.nextFromRangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeUp_float() {
        nextFromRangeUp_float_helper(1.0f, 6.9505213E34f);
        nextFromRangeUp_float_helper(1.0E20f, 3.04433E22f);
        nextFromRangeUp_float_helper(-1.0f, 3.7808473E-36f);
        nextFromRangeUp_float_helper(-1.0E20f, -9.307628E-22f);
        nextFromRangeUp_float_helper((float) Math.PI, 935.52563f);
        nextFromRangeUp_float_helper((float) Math.sqrt(2), 427.6776f);
        nextFromRangeUp_float_helper((float) -Math.PI, 1.2681087E-36f);
        nextFromRangeUp_float_helper((float) -Math.sqrt(2), 2.7718172E-36f);
        nextFromRangeUp_float_helper(0.0f, 643.27783f);
        nextFromRangeUp_float_helper(-0.0f, 643.27783f);
        nextFromRangeUp_float_helper(Float.MIN_VALUE, 643.27795f);
        nextFromRangeUp_float_helper(Float.MIN_NORMAL, 1286.5558f);
        nextFromRangeUp_float_helper(-Float.MIN_VALUE, 643.2778f);
        nextFromRangeUp_float_helper(-Float.MIN_NORMAL, 321.63892f);
        nextFromRangeUp_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY);
        nextFromRangeUp_float_helper(-Float.MAX_VALUE, -0.0034054646f);
        nextFromRangeUp_float_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        nextFromRangeUp_float_helper(Float.NEGATIVE_INFINITY, -0.0034054648f);
        nextFromRangeUp_float_fail_helper(Float.NaN);
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
        rangeUp_float_helper(
                1.0f,
                "[6.9505213E34, 3.0272395E24, 80483.79, 1.6275188E19, 3.4138216E26, 2.3000961E21, 2.4173826E35," +
                " 3502559.5, 3.1639403E34, 19.584063, 1.11343182E15, 6.872559E24, 7.273829E30, 2.8495727E36," +
                " 4.70190961E17, 465.4081, 3.59243305E16, 1.31713057E13, 20.567188, 6.4980443E10]",
                3.9935304E36f
        );
        rangeUp_float_helper(
                1.0E20f,
                "[3.04433E22, 3.4178768E35, 1.5544752E25, 7.6565523E24, 7.462906E33, 2.7189451E35, 7.1734916E31," +
                " 1.702929E32, 4.7802825E28, 7.70691E34, 6.207445E28, 1.8053102E27, 1.8899614E34, 1.2311303E22," +
                " 1.8317792E21, 1.7322734E27, 1.203059E36, 3.1734694E26, 1.5607085E35, 1.3585391E26]",
                8.267385E36f
        );
        rangeUp_float_helper(
                -1.0f,
                "[3.7808473E-36, 2.7802083E35, 1.2108957E25, 321935.12, 1.6510781E-5, 6.5100746E19, 9.92695E-24," +
                " 1.4106354E-10, 1.3655285E27, -4.4543045E-22, 2.2500362E-37, 9.6695294E35, -3.1706085E-7," +
                " -3.5548292E-35, 78.33624, -9.078497E-16, 2.7490233E25, 0.90437907, 4.822225E-5, 8.0389303E-22]",
                3.9935304E36f
        );
        rangeUp_float_helper(
                -1.0E20f,
                "[-9.307628E-22, 2.96792682E15, 124307.64, 3.3268185E-15, 1.7854671E-25, 0.70465326, 7.40886E27," +
                " -3.572859E-34, 1.4644996E-30, 1.4661713E7, -0.043968614, -1.5706407E-20, 2.6133126E26," +
                " 9.9048238E15, -2.96446339E13, -3.2540566E-15, 8.104416E-19, 5.2518837E36, -90269.33, 279434.75]",
                1.14254505E36f
        );
        rangeUp_float_helper(
                (float) Math.PI,
                "[935.52563, 2.0670717E35, 8.814681E24, 235783.0, 4.2910363E33, 1.5385392E35, 4.0239202E31," +
                " 1.0263343E32, 4.926593E19, 1.52713638E9, 2.41148562E15, 3.5306506E28, 1.03607016E27, 1.0753354E34," +
                " 7.1735277E21, 56.54787, 5.4185808E7, 6.816983E35, 1.0409817E7, 9.090077E34]",
                4.026231E36f
        );
        rangeUp_float_helper(
                (float) Math.sqrt(2),
                "[427.6776, 9.034519E34, 4.0287464E24, 107629.69, 1.942262E33, 7.0422764E34, 1.8531663E31," +
                " 4.496496E31, 2.1744522E19, 6.7950342E8, 1.07767306E15, 1.6102532E28, 4.6957504E26, 4.8779746E33," +
                " 3.217043E21, 25.76861, 2.446588E7, 3.1056128E35, 4548152.5, 4.0242281E34]",
                3.9928994E36f
        );
        rangeUp_float_helper(
                (float) -Math.PI,
                "[1.2681087E-36, 9.159051E34, 4.06499E24, 108612.07, 5.7649772E-6, 2.2021039E19, 3.1917953E-24," +
                " 4.7757354E-11, 4.742142E26, -1.3743442E-21, 7.6431964E-38, 3.1305192E35, -9.062986E-7," +
                " -1.0086236E-34, 26.451319, -2.8296374E-15, 8.9480596E24, 0.30949044, 1.5401463E-5, 2.8107568E-22]",
                1.3398904E36f
        );
        rangeUp_float_helper(
                (float) -Math.sqrt(2),
                "[2.7718172E-36, 2.091978E35, 8.887168E24, 237747.77, 1.2724587E-5, 4.9818964E19, 7.185915E-24," +
                " 1.0462903E-10, 1.0453485E27, -6.208567E-22, 1.6758891E-37, 6.916608E35, -4.1581706E-7," +
                " -4.552012E-35, 57.91329, -1.2757452E-15, 1.9478178E25, 0.6972723, 3.5581455E-5, 6.284668E-22]",
                1.3491116E36f
        );
        rangeUp_float_helper(
                0.0f,
                "[643.27783, 4.085149E-4, 1.7792513E-14, 4.7304115E-34, 2.809164E33, 1.06434E35, 2.8662074E31," +
                " 6.872728E31, 9.565695E-20, 9.9399507E8, 1.688983E15, 2.4000717E28, 2.0064638E-12, 7.605229E33," +
                " 1.3518749E-17, 38.282383, 3.5033056E7, 0.0014208097, 2.0586193E-32, 1.8595968E-4]",
                1.9848223E36f
        );
        rangeUp_float_helper(
                -0.0f,
                "[643.27783, 4.085149E-4, 1.7792513E-14, 4.7304115E-34, 2.809164E33, 1.06434E35, 2.8662074E31," +
                " 6.872728E31, 9.565695E-20, 9.9399507E8, 1.688983E15, 2.4000717E28, 2.0064638E-12, 7.605229E33," +
                " 1.3518749E-17, 38.282383, 3.5033056E7, 0.0014208097, 2.0586193E-32, 1.8595968E-4]",
                1.9848223E36f
        );
        rangeUp_float_helper(
                Float.MIN_VALUE,
                "[643.27795, 4.0851496E-4, 1.7792517E-14, 4.7304124E-34, 2.8091645E33, 1.0643402E35, 2.866208E31," +
                " 6.872729E31, 9.565696E-20, 9.939952E8, 1.68898327E15, 2.4000722E28, 2.0064642E-12, 7.60523E33," +
                " 1.3518751E-17, 38.28239, 3.5033064E7, 0.0014208099, 2.0586196E-32, 1.8595971E-4]",
                1.9848228E36f
        );
        rangeUp_float_helper(
                Float.MIN_NORMAL,
                "[1286.5558, 8.1702985E-4, 3.558503E-14, 9.460824E-34, 5.6183284E33, 2.1286803E35, 5.7324153E31," +
                " 1.3745457E32, 1.9131391E-19, 1.98799027E9, 3.37796628E15, 4.800144E28, 4.012928E-12, 1.5210459E34," +
                " 2.70375E-17, 76.56477, 7.006612E7, 0.0028416195, 4.117239E-32, 3.719194E-4]",
                2.0187889E36f
        );
        rangeUp_float_helper(
                -Float.MIN_VALUE,
                "[643.2778, 4.0851487E-4, 1.7792512E-14, 4.730411E-34, 2.8091636E33, 1.0643399E35, 2.8662072E31," +
                " 6.8727273E31, 9.565694E-20, 9.9399501E8, 1.68898287E15, 2.4000715E28, 2.0064636E-12, 7.605228E33," +
                " 1.3518748E-17, 38.28238, 3.5033052E7, 0.0014208095, 2.0586192E-32, 1.8595967E-4]",
                1.9848223E36f
        );
        rangeUp_float_helper(
                -Float.MIN_NORMAL,
                "[321.63892, 1.404582E33, 8.444915E14, 1.2000358E28, 6.7593746E-18, 19.141191, 1.02930965E-32," +
                " 9.297984E-5, 3.2720818E-24, 7.6936063E37, 4.1022953E33, 6.8387656E16, 0.008374141, 1.0557211E-22," +
                " 3.8706986E-26, 6.044153E-38, 1.9096035E-28, 4.6321023E18, 1.2419973E35, 4.1141146E-35]",
                2.0147774E36f
        );
        rangeUp_float_helper(
                Float.MAX_VALUE,
                "[Infinity, 3.4028235E38, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " 3.4028235E38, Infinity, 3.4028235E38, 3.4028235E38, Infinity, 3.4028235E38, 3.4028235E38," +
                " Infinity, Infinity, 3.4028235E38, Infinity]",
                Float.POSITIVE_INFINITY
        );
        rangeUp_float_helper(
                -Float.MAX_VALUE,
                "[-0.0034054646, 8.1702985E-4, 3.558503E-14, 9.460824E-34, -7.387672E-34, 2.1286803E35," +
                " 5.7324153E31, 1.3745457E32, 1.9131391E-19, 1.98799027E9, -1.3321625E-15, -9.028473E-29," +
                " 4.012928E-12, 1.5210459E34, -1.5157211E17, -0.056364857, 7.006612E7, 0.0028416195, -1.0789021E32," +
                " -12096.456]",
                -5.4709615E33f
        );
        rangeUp_float_helper(
                Float.POSITIVE_INFINITY,
                "[Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity]",
                Float.POSITIVE_INFINITY
        );
        rangeUp_float_helper(
                Float.NEGATIVE_INFINITY,
                "[-0.0034054648, 8.170298E-4, 3.5585026E-14, 9.460823E-34, -7.3876725E-34, 2.12868E35, 5.732415E31," +
                " 1.3745456E32, 1.913139E-19, 1.98799014E9, -1.3321626E-15, -9.0284737E-29, 4.0129276E-12," +
                " 1.5210458E34, -1.51572127E17, -0.05636486, 7.0066112E7, 0.0028416193, -1.0789022E32, -12096.457]",
                -5.471121E33f
        );
        rangeUp_float_fail_helper(Float.NaN);
    }

    private static void nextFromRangeDown_float_helper(float a, float output) {
        aeq(P.nextFromRangeDown(a), output);
        P.reset();
    }

    private static void nextFromRangeDown_float_fail_helper(float a) {
        try {
            P.nextFromRangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeDown_float() {
        nextFromRangeDown_float_helper(1.0f, -3.7808473E-36f);
        nextFromRangeDown_float_helper(1.0E20f, 9.307628E-22f);
        nextFromRangeDown_float_helper(-1.0f, -6.9505213E34f);
        nextFromRangeDown_float_helper(-1.0E20f, -3.04433E22f);
        nextFromRangeDown_float_helper((float) Math.PI, -1.2681087E-36f);
        nextFromRangeDown_float_helper((float) Math.sqrt(2), -2.7718172E-36f);
        nextFromRangeDown_float_helper((float) -Math.PI, -935.52563f);
        nextFromRangeDown_float_helper((float) -Math.sqrt(2), -427.6776f);
        nextFromRangeDown_float_helper(0.0f, -643.27783f);
        nextFromRangeDown_float_helper(-0.0f, -643.27783f);
        nextFromRangeDown_float_helper(Float.MIN_VALUE, -643.2778f);
        nextFromRangeDown_float_helper(Float.MIN_NORMAL, -321.63892f);
        nextFromRangeDown_float_helper(-Float.MIN_VALUE, -643.27795f);
        nextFromRangeDown_float_helper(-Float.MIN_NORMAL, -1286.5558f);
        nextFromRangeDown_float_helper(Float.MAX_VALUE, 0.0034054646f);
        nextFromRangeDown_float_helper(-Float.MAX_VALUE, Float.NEGATIVE_INFINITY);
        nextFromRangeDown_float_helper(Float.POSITIVE_INFINITY, 0.0034054648f);
        nextFromRangeDown_float_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        nextFromRangeDown_float_fail_helper(Float.NaN);
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
        rangeDown_float_helper(
                1.0f,
                "[-3.7808473E-36, -2.7802083E35, -1.2108957E25, -321935.12, -1.6510781E-5, -6.5100746E19," +
                " -9.92695E-24, -1.4106354E-10, -1.3655285E27, 4.4543045E-22, -2.2500362E-37, -9.6695294E35," +
                " 3.1706085E-7, 3.5548292E-35, -78.33624, 9.078497E-16, -2.7490233E25, -0.90437907, -4.822225E-5," +
                " -8.0389303E-22]",
                -1.3550214E36f
        );
        rangeDown_float_helper(
                1.0E20f,
                "[9.307628E-22, -2.96792682E15, -124307.64, -3.3268185E-15, -1.7854671E-25, -0.70465326," +
                " -7.40886E27, 3.572859E-34, -1.4644996E-30, -1.4661713E7, 0.043968614, 1.5706407E-20," +
                " -2.6133126E26, -9.9048238E15, 2.96446339E13, 3.2540566E-15, -8.104416E-19, -5.2518837E36," +
                " 90269.33, -279434.75]",
                -1.14254505E36f
        );
        rangeDown_float_helper(
                -1.0f,
                "[-6.9505213E34, -3.0272395E24, -80483.79, -1.6275188E19, -3.4138216E26, -2.3000961E21," +
                " -2.4173826E35, -3502559.5, -3.1639403E34, -19.584063, -1.11343182E15, -6.872559E24, -7.273829E30," +
                " -2.8495727E36, -4.70190961E17, -465.4081, -3.59243305E16, -1.31713057E13, -20.567188," +
                " -6.4980443E10]",
                -1.3550214E36f
        );
        rangeDown_float_helper(
                -1.0E20f,
                "[-3.04433E22, -3.4178768E35, -1.5544752E25, -7.6565523E24, -7.462906E33, -2.7189451E35," +
                " -7.1734916E31, -1.702929E32, -4.7802825E28, -7.70691E34, -6.207445E28, -1.8053102E27," +
                " -1.8899614E34, -1.2311303E22, -1.8317792E21, -1.7322734E27, -1.203059E36, -3.1734694E26," +
                " -1.5607085E35, -1.3585391E26]",
                -8.267385E36f
        );
        rangeDown_float_helper(
                (float) Math.PI,
                "[-1.2681087E-36, -9.159051E34, -4.06499E24, -108612.07, -5.7649772E-6, -2.2021039E19," +
                " -3.1917953E-24, -4.7757354E-11, -4.742142E26, 1.3743442E-21, -7.6431964E-38, -3.1305192E35," +
                " 9.062986E-7, 1.0086236E-34, -26.451319, 2.8296374E-15, -8.9480596E24, -0.30949044, -1.5401463E-5," +
                " -2.8107568E-22]",
                -1.3398904E36f
        );
        rangeDown_float_helper(
                (float) Math.sqrt(2),
                "[-2.7718172E-36, -2.091978E35, -8.887168E24, -237747.77, -1.2724587E-5, -4.9818964E19," +
                " -7.185915E-24, -1.0462903E-10, -1.0453485E27, 6.208567E-22, -1.6758891E-37, -6.916608E35," +
                " 4.1581706E-7, 4.552012E-35, -57.91329, 1.2757452E-15, -1.9478178E25, -0.6972723, -3.5581455E-5," +
                " -6.284668E-22]",
                -1.3491116E36f
        );
        rangeDown_float_helper(
                (float) -Math.PI,
                "[-935.52563, -2.0670717E35, -8.814681E24, -235783.0, -4.2910363E33, -1.5385392E35, -4.0239202E31," +
                " -1.0263343E32, -4.926593E19, -1.52713638E9, -2.41148562E15, -3.5306506E28, -1.03607016E27," +
                " -1.0753354E34, -7.1735277E21, -56.54787, -5.4185808E7, -6.816983E35, -1.0409817E7, -9.090077E34]",
                -4.026231E36f
        );
        rangeDown_float_helper(
                (float) -Math.sqrt(2),
                "[-427.6776, -9.034519E34, -4.0287464E24, -107629.69, -1.942262E33, -7.0422764E34, -1.8531663E31," +
                " -4.496496E31, -2.1744522E19, -6.7950342E8, -1.07767306E15, -1.6102532E28, -4.6957504E26," +
                " -4.8779746E33, -3.217043E21, -25.76861, -2.446588E7, -3.1056128E35, -4548152.5, -4.0242281E34]",
                -3.9928994E36f
        );
        rangeDown_float_helper(
                0.0f,
                "[-643.27783, -4.085149E-4, -1.7792513E-14, -4.7304115E-34, -2.809164E33, -1.06434E35," +
                " -2.8662074E31, -6.872728E31, -9.565695E-20, -9.9399507E8, -1.688983E15, -2.4000717E28," +
                " -2.0064638E-12, -7.605229E33, -1.3518749E-17, -38.282383, -3.5033056E7, -0.0014208097," +
                " -2.0586193E-32, -1.8595968E-4]",
                -1.9848223E36f
        );
        rangeDown_float_helper(
                -0.0f,
                "[-643.27783, -4.085149E-4, -1.7792513E-14, -4.7304115E-34, -2.809164E33, -1.06434E35," +
                " -2.8662074E31, -6.872728E31, -9.565695E-20, -9.9399507E8, -1.688983E15, -2.4000717E28," +
                " -2.0064638E-12, -7.605229E33, -1.3518749E-17, -38.282383, -3.5033056E7, -0.0014208097," +
                " -2.0586193E-32, -1.8595968E-4]",
                -1.9848223E36f
        );
        rangeDown_float_helper(
                Float.MIN_VALUE,
                "[-643.2778, -4.0851487E-4, -1.7792512E-14, -4.730411E-34, -2.8091636E33, -1.0643399E35," +
                " -2.8662072E31, -6.8727273E31, -9.565694E-20, -9.9399501E8, -1.68898287E15, -2.4000715E28," +
                " -2.0064636E-12, -7.605228E33, -1.3518748E-17, -38.28238, -3.5033052E7, -0.0014208095," +
                " -2.0586192E-32, -1.8595967E-4]",
                -1.9848223E36f
        );
        rangeDown_float_helper(
                Float.MIN_NORMAL,
                "[-321.63892, -1.404582E33, -8.444915E14, -1.2000358E28, -6.7593746E-18, -19.141191," +
                " -1.02930965E-32, -9.297984E-5, -3.2720818E-24, -7.6936063E37, -4.1022953E33, -6.8387656E16," +
                " -0.008374141, -1.0557211E-22, -3.8706986E-26, -6.044153E-38, -1.9096035E-28, -4.6321023E18," +
                " -1.2419973E35, -4.1141146E-35]",
                -2.0147774E36f
        );
        rangeDown_float_helper(
                -Float.MIN_VALUE,
                "[-643.27795, -4.0851496E-4, -1.7792517E-14, -4.7304124E-34, -2.8091645E33, -1.0643402E35," +
                " -2.866208E31, -6.872729E31, -9.565696E-20, -9.939952E8, -1.68898327E15, -2.4000722E28," +
                " -2.0064642E-12, -7.60523E33, -1.3518751E-17, -38.28239, -3.5033064E7, -0.0014208099," +
                " -2.0586196E-32, -1.8595971E-4]",
                -1.9848228E36f
        );
        rangeDown_float_helper(
                -Float.MIN_NORMAL,
                "[-1286.5558, -8.1702985E-4, -3.558503E-14, -9.460824E-34, -5.6183284E33, -2.1286803E35," +
                " -5.7324153E31, -1.3745457E32, -1.9131391E-19, -1.98799027E9, -3.37796628E15, -4.800144E28," +
                " -4.012928E-12, -1.5210459E34, -2.70375E-17, -76.56477, -7.006612E7, -0.0028416195, -4.117239E-32," +
                " -3.719194E-4]",
                -2.0187889E36f
        );
        rangeDown_float_helper(
                Float.MAX_VALUE,
                "[0.0034054646, -8.1702985E-4, -3.558503E-14, -9.460824E-34, 7.387672E-34, -2.1286803E35," +
                " -5.7324153E31, -1.3745457E32, -1.9131391E-19, -1.98799027E9, 1.3321625E-15, 9.028473E-29," +
                " -4.012928E-12, -1.5210459E34, 1.5157211E17, 0.056364857, -7.006612E7, -0.0028416195, 1.0789021E32," +
                " 12096.456]",
                5.4709615E33f
        );
        rangeDown_float_helper(
                -Float.MAX_VALUE,
                "[-Infinity, -3.4028235E38, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -3.4028235E38, -Infinity, -3.4028235E38, -3.4028235E38, -Infinity, -3.4028235E38," +
                " -3.4028235E38, -Infinity, -Infinity, -3.4028235E38, -Infinity]",
                Float.NEGATIVE_INFINITY
        );
        rangeDown_float_helper(
                Float.POSITIVE_INFINITY,
                "[0.0034054648, -8.170298E-4, -3.5585026E-14, -9.460823E-34, 7.3876725E-34, -2.12868E35," +
                " -5.732415E31, -1.3745456E32, -1.913139E-19, -1.98799014E9, 1.3321626E-15, 9.0284737E-29," +
                " -4.0129276E-12, -1.5210458E34, 1.51572127E17, 0.05636486, -7.0066112E7, -0.0028416193," +
                " 1.0789022E32, 12096.457]",
                5.471121E33f
        );
        rangeDown_float_helper(
                Float.NEGATIVE_INFINITY,
                "[-Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity]",
                Float.NEGATIVE_INFINITY
        );
        rangeDown_float_fail_helper(Float.NaN);
    }

    private static void nextFromRange_float_float_helper(float a, float b, float output) {
        aeq(P.nextFromRange(a, b), output);
        P.reset();
    }

    private static void nextFromRange_float_float_fail_helper(float a, float b) {
        try {
            P.nextFromRange(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testNextFromRange_float_float() {
        nextFromRange_float_float_helper(1.0f, 2.0f, 1.2564021f);
        nextFromRange_float_float_helper(1.0f, 3.0f, 1.2564021f);
        nextFromRange_float_float_helper(1.0f, 4.0f, 1.2564021f);
        nextFromRange_float_float_helper(1.0f, 257.0f, 13.386217f);
        nextFromRange_float_float_helper(-257.0f, -1.0f, -21.290066f);
        nextFromRange_float_float_helper(1.0f, 1.0E20f, 321.63895f);
        nextFromRange_float_float_helper(-1.0E20f, -1.0f, -3.16722124E17f);
        nextFromRange_float_float_helper((float) Math.sqrt(2), (float) Math.PI, 1.6706157f);
        nextFromRange_float_float_helper((float) Math.PI, FloatingPointUtils.successor((float) Math.PI), 3.141593f);
        nextFromRange_float_float_helper(0.0f, 1.0f, 1.8904236E-36f);
        nextFromRange_float_float_helper(-1.0f, 1.0f, 3.7808473E-36f);
        nextFromRange_float_float_helper(1.0f, 1.0f, 1.0f);
        nextFromRange_float_float_helper(1.0f, Float.POSITIVE_INFINITY, 6.9505213E34f);
        nextFromRange_float_float_helper(Float.NEGATIVE_INFINITY, 1.0f, -0.0034054648f);
        nextFromRange_float_float_helper(Float.MAX_VALUE, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        nextFromRange_float_float_helper(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE, -3.4028235E38f);
        nextFromRange_float_float_helper(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, -0.0034054648f);
        nextFromRange_float_float_helper(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        nextFromRange_float_float_helper(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        nextFromRange_float_float_fail_helper(1.0f, -1.0f);
        nextFromRange_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
        nextFromRange_float_float_fail_helper(Float.POSITIVE_INFINITY, 1.0f);
        nextFromRange_float_float_fail_helper(1.0f, Float.NEGATIVE_INFINITY);
        nextFromRange_float_float_fail_helper(Float.NaN, 1.0f);
        nextFromRange_float_float_fail_helper(Float.NaN, Float.POSITIVE_INFINITY);
        nextFromRange_float_float_fail_helper(Float.NaN, Float.NEGATIVE_INFINITY);
        nextFromRange_float_float_fail_helper(1.0f, Float.NaN);
        nextFromRange_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NaN);
        nextFromRange_float_float_fail_helper(Float.NEGATIVE_INFINITY, Float.NaN);
        nextFromRange_float_float_fail_helper(Float.NaN, Float.NaN);
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
        range_float_float_helper(
                1.0f,
                2.0f,
                "[1.2564021, 1.2280852, 1.0820507, 1.6942583, 1.8514602, 1.1030653, 1.9482572, 1.1963246, 1.0440665," +
                " 1.5233818, 1.4006178, 1.2240039, 1.330792, 1.4212118, 1.4345099, 1.6313026, 1.8180004, 1.994201," +
                " 1.8040158, 1.7906964]",
                1.5001546f
        );
        range_float_float_helper(
                1.0f,
                3.0f,
                "[1.2564021, 2.5040739, 1.2280852, 1.0820507, 2.5623057, 2.826299, 1.6942583, 1.8514602, 2.4234533," +
                " 1.1030653, 2.9294279, 1.9482572, 1.1963246, 1.0440665, 2.9098184, 1.5233818, 1.4006178, 1.2240039," +
                " 1.330792, 1.4212118]",
                1.8340441f
        );
        range_float_float_helper(
                1.0f,
                4.0f,
                "[1.2564021, 2.5040739, 1.2280852, 1.6942583, 1.8514602, 3.0002367, 2.4234533, 1.1030653, 1.1963246," +
                " 1.0440665, 2.9098184, 3.340301, 1.4006178, 1.2240039, 3.9557045, 3.6175165, 2.1437802, 1.8180004," +
                " 1.8040158, 1.2854493]",
                2.2503035f
        );
        range_float_float_helper(
                1.0f,
                257.0f,
                "[13.386217, 2.5040739, 1.2280852, 10.249223, 180.88313, 3.0002367, 124.68846, 19.141193, 46.557095," +
                " 53.444817, 6.0935273, 22.409885, 19.584063, 3.9557045, 5.6848474, 242.96178, 91.80863, 127.62886," +
                " 28.864252, 7.1627855]",
                47.948357f
        );
        range_float_float_helper(
                -257.0f,
                -1.0f,
                "[-21.290066, -112.11964, -227.3051, -27.564054, -1.5907568, -96.242424, -2.111298, -14.460653," +
                " -6.195988, -5.335023, -47.37678, -12.826307, -14.239219, -65.66746, -50.64622, -1.1057674," +
                " -3.1387928, -2.0194106, -9.599124, -38.822716]",
                -47.890907f
        );
        range_float_float_helper(
                1.0f,
                1.0E20f,
                "[321.63895, 80483.79, 1.6275188E19, 4.96997568E8, 8.4449157E14, 19.141193, 1.751653E7, 3502559.5," +
                " 19.584063, 3.83574664E17, 1.11343182E15, 6.838766E16, 4.70190961E17, 465.4081, 3.59243305E16," +
                " 28.864252, 3.07639296E10, 1.31713057E13, 20.567188, 6.4980443E10]",
                2.12897406E18f
        );
        range_float_float_helper(
                -1.0E20f,
                -1.0f,
                "[-3.16722124E17, -1.26907788E15, -6.3627734, -2.06679671E11, -121578.086, -5.3446128E18," +
                " -5.7666579E12, -2.96446318E13, -5.2169644E18, -262.26196, -90269.32, -1492.0846, -220.66562," +
                " -2.21541405E17, -2787.434, -3.57690896E18, -3.3598592E9, -7792382.0, -4.9335977E18, -1.57203635E9]",
                -2.12162781E18f
        );
        range_float_float_helper(
                (float) Math.sqrt(2),
                (float) Math.PI,
                "[1.6706157, 1.6422987, 1.4962642, 2.2169437, 2.5313475, 1.5172788, 2.7249415, 1.6105381, 1.4582801," +
                " 1.9375954, 1.8148314, 1.6382174, 1.7450055, 1.8354254, 1.8487234, 2.9722073, 2.0910323, 2.464428," +
                " 2.816829, 2.4364586]",
                2.1330023f
        );
        range_float_float_helper(
                (float) Math.PI,
                FloatingPointUtils.successor((float) Math.PI),
                "[3.141593, 3.1415927, 3.141593, 3.141593, 3.141593, 3.141593, 3.141593, 3.141593, 3.141593," +
                " 3.1415927, 3.141593, 3.1415927, 3.1415927, 3.141593, 3.1415927, 3.1415927, 3.141593, 3.141593," +
                " 3.1415927, 3.141593]",
                3.1147525f
        );
        range_float_float_helper(
                0.0f,
                1.0f,
                "[1.8904236E-36, 4.085149E-4, 1.7792513E-14, 4.7304115E-34, 8.255391E-6, 3.1278143E-4, 8.4230265E-8," +
                " 2.0197132E-7, 9.565695E-20, 2.921089E-30, 4.963475E-24, 7.053177E-11, 2.0064638E-12, 2.234976E-5," +
                " 1.3518749E-17, 1.1250181E-37, 1.029529E-31, 0.0014208097, 2.0586193E-32, 1.8595968E-4]",
                0.011681142f
        );
        range_float_float_helper(
                -1.0f,
                1.0f,
                "[3.7808473E-36, -1.5969847E-35, -3.6147032E-25, -1.3518638E-5, 1.6510781E-5, 6.2556285E-4," +
                " 1.6846053E-7, 4.0394264E-7, -6.6973376E-20, 5.842178E-30, 9.92695E-24, 1.4106354E-10," +
                " -3.0646636E-27, 4.469952E-5, -4.4543045E-22, 2.2500362E-37, 2.059058E-31, -4.649589E-36," +
                " -3.1706085E-7, -3.5548292E-35]",
                4.273086E-5f
        );
        range_float_float_helper(
                1.0f,
                1.0f,
                "[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]",
                1.0090389f
        );
        range_float_float_helper(
                1.0f,
                Float.POSITIVE_INFINITY,
                "[6.9505213E34, 3.0272395E24, 80483.79, 1.6275188E19, 3.4138216E26, 2.3000961E21, 2.4173826E35," +
                " 3502559.5, 3.1639403E34, 19.584063, 1.11343182E15, 6.872559E24, 7.273829E30, 2.8495727E36," +
                " 4.70190961E17, 465.4081, 3.59243305E16, 1.31713057E13, 20.567188, 6.4980443E10]",
                3.9935304E36f
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                1.0f,
                "[-0.0034054648, 8.170298E-4, 3.5585026E-14, 9.460823E-34, -7.3876725E-34, 1.913139E-19," +
                " -1.3321626E-15, -9.0284737E-29, 4.0129276E-12, -1.51572127E17, -0.05636486, 0.0028416193," +
                " -1.0789022E32, -12096.457, 2.3020953E-37, -3.0892525E23, 8.078653E-14, -1.4002981E-38," +
                " -2.734538E-34, -1.5291396E-17]",
                -1.3425657E36f
        );
        range_float_float_helper(
                Float.MAX_VALUE,
                Float.POSITIVE_INFINITY,
                "[Infinity, 3.4028235E38, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " 3.4028235E38, Infinity, 3.4028235E38, 3.4028235E38, Infinity, 3.4028235E38, 3.4028235E38," +
                " Infinity, Infinity, 3.4028235E38, Infinity]",
                Float.POSITIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                -Float.MAX_VALUE,
                "[-3.4028235E38, -Infinity, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38," +
                " -3.4028235E38, -3.4028235E38, -3.4028235E38, -Infinity, -3.4028235E38, -Infinity, -Infinity," +
                " -3.4028235E38, -Infinity, -Infinity, -3.4028235E38, -3.4028235E38, -Infinity, -3.4028235E38]",
                Float.NEGATIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                "[-0.0034054648, 8.170298E-4, 3.5585026E-14, 9.460823E-34, -7.3876725E-34, 2.12868E35, 5.732415E31," +
                " 1.3745456E32, 1.913139E-19, 1.98799014E9, -1.3321626E-15, -9.0284737E-29, 4.0129276E-12," +
                " 1.5210458E34, -1.51572127E17, -0.05636486, 7.0066112E7, 0.0028416193, -1.0789022E32, -12096.457]",
                -5.471121E33f
        );
        range_float_float_helper(
                Float.POSITIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                "[Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity]",
                Float.POSITIVE_INFINITY
        );
        range_float_float_helper(
                Float.NEGATIVE_INFINITY,
                Float.NEGATIVE_INFINITY,
                "[-Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity]",
                Float.NEGATIVE_INFINITY
        );
        range_float_float_helper(1.0f, -1.0f, "[]", 0.0f);
        range_float_float_helper(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, "[]", 0.0f);
        range_float_float_helper(Float.POSITIVE_INFINITY, 1.0f, "[]", 0.0f);
        range_float_float_helper(1.0f, Float.NEGATIVE_INFINITY, "[]", 0.0f);
        range_float_float_fail_helper(Float.NaN, 1.0f);
        range_float_float_fail_helper(Float.NaN, Float.POSITIVE_INFINITY);
        range_float_float_fail_helper(Float.NaN, Float.NEGATIVE_INFINITY);
        range_float_float_fail_helper(1.0f, Float.NaN);
        range_float_float_fail_helper(Float.POSITIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NEGATIVE_INFINITY, Float.NaN);
        range_float_float_fail_helper(Float.NaN, Float.NaN);
    }

    private static void nextFromRangeUp_double_helper(double a, double output) {
        aeq(P.nextFromRangeUp(a), output);
        P.reset();
    }

    private static void nextFromRangeUp_double_fail_helper(double a) {
        try {
            P.nextFromRangeUp(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeUp_double() {
        nextFromRangeUp_double_helper(1.0, 1.3361160166687895E199);
        nextFromRangeUp_double_helper(1.0E20, 1.236689265426644E219);
        nextFromRangeUp_double_helper(-1.0, 9.695749714968612E-201);
        nextFromRangeUp_double_helper(-1.0E20, 9.995264080399706E-221);
        nextFromRangeUp_double_helper(Math.PI, 3.764522892368842E199);
        nextFromRangeUp_double_helper(Math.sqrt(2), 1.7324409986526723E199);
        nextFromRangeUp_double_helper(-Math.PI, 3.1248378519408627E-201);
        nextFromRangeUp_double_helper(-Math.sqrt(2), 6.99006493837807E-201);
        nextFromRangeUp_double_helper(0.0, 1.4864784103112476E-109);
        nextFromRangeUp_double_helper(-0.0, 1.4864784103112476E-109);
        nextFromRangeUp_double_helper(Double.MIN_VALUE, 1.486478410311248E-109);
        nextFromRangeUp_double_helper(Double.MIN_NORMAL, 2.9729568206224957E-109);
        nextFromRangeUp_double_helper(-Double.MIN_VALUE, 1.4864784103112474E-109);
        nextFromRangeUp_double_helper(-Double.MIN_NORMAL, 4.3574956749855824E107);
        nextFromRangeUp_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY);
        nextFromRangeUp_double_helper(-Double.MAX_VALUE, -2.581476161683265E-108);
        nextFromRangeUp_double_helper(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        nextFromRangeUp_double_helper(Double.NEGATIVE_INFINITY, -2.5814761616832655E-108);
        nextFromRangeUp_double_fail_helper(Double.NaN);
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
        rangeUp_double_helper(
                1.0,
                "[1.3361160166687895E199, 2.3072288694198864E17, 2.2584760398763506E255, 2.9034996317510987E270," +
                " 3.407411369365793E239, 1.0292528640881906E305, 5.403295552765342E256, 2.472466042809842E135," +
                " 4.6952828834636355E21, 2.0334465599448862E304, 3.1314260642373683E209, 1.942795489316547E38," +
                " 8.716553164100655E138, 1.4977836753903746E108, 1.336734354060387E181, 4.1958496470469624E12," +
                " 2.2659943330988978E139, 1.2315990218185135E116, 4.695636083772049E91, 1.8108731649679133E19]",
                2.6552885265154105E305
        );
        rangeUp_double_helper(
                1.0E20,
                "[1.236689265426644E219, 2.0802039030771412E37, 2.1584479716690603E275, 2.696332467719559E290," +
                " 3.074241456659164E210, 1.1848051000545277E266, 9.885917246849498E292, 3.0604408831173115E259," +
                " 4.509119778552049E93, 2.2693412353963272E128, 5.116558819879998E276, 6.085458367839E276," +
                " 2.265572225412841E155, 2.47936609780758E93, 4.6823981447226426E41, 3.057135115446442E229," +
                " 4.287740527297439E30, 1.8795215346865428E58, 7.992473343867941E158, 1.4129812974824141E128]",
                2.880170764012561E305
        );
        rangeUp_double_helper(
                -1.0,
                "[9.695749714968612E-201, -1.748023137790877E-221, -1.18317793185162E-183, 6.617187358035565," +
                " 2.121594297425973E63, 5.8742946490957294E280, -8.580086011885347E-19, 5.331775761432201E-218," +
                " 1.1844330132037358E-20, -8.938482795823259E-132, 2.0565211581170822E169, -1.3850642924441139E-220," +
                " 8.984366145692863E-197, 1.1633051909285701E29, -2.0701848154057248E-208, 1.4798882460290394E146," +
                " 5.6894974968490635E-70, 4.462628677199133E44, -5.062712151576731E-194, -5.740054069458642E-92]",
                2.6552885265154105E305
        );
        rangeUp_double_helper(
                -1.0E20,
                "[9.995264080399706E-221, -1.5798817404797417E-201, -1.1197940581928392E-163, 7.042132306445444E-20," +
                " 2.1565654618360867E43, 6.213265571507549E260, -85.35589443217175, 5.593215107700677E-238," +
                " 1.278957963606487E-40, -8.072635201308954E-112, 2.0603053200554565E149, -1.2540517188697671E-200," +
                " 9.599762377059262E-217, 1.19512282024884E9, -2.037820367922859E-188, 1.524666295515931E126," +
                " 6.3155319052712355E-90, 4.5828980952181496E24, -4.86441857893625E-174, -5.551512423880134E-72]",
                8.851866062997829E304
        );
        rangeUp_double_helper(
                Math.PI,
                "[3.764522892368842E199, 6.754724754207479E17, 6.659583594368998E255, 8.219387886206756E270," +
                " 1.5797237810800915E297, 9.869318362876497E190, 3.680413198673549E246, 3.1459968521161185E273," +
                " 1.0052225117992325E240, 1.465932226901811E74, 7.163488442458895E108, 3.0605727575551044E305," +
                " 1.6457576131745995E257, 1.9083783618789428E257, 7.39416936887628E135, 7.938999583704409E73," +
                " 1.4727417452517908E22, 6.250054558191402E304, 9.703617193821223E209, 1.3103148404337894E11]",
                2.6819125859769203E305
        );
        rangeUp_double_helper(
                Math.sqrt(2),
                "[1.7324409986526723E199, 2.9260432862220326E17, 3.03590486243514E255, 3.7788066706468105E270," +
                " 6.874572397740668E296, 1.6347965850839314E308, 4.376534521737003E190, 1.6572415190565034E246," +
                " 1.4035841425602966E273, 4.373552475805664E239, 6.44435618182571E73, 3.2140029312564035E108," +
                " 1.3928411831531207E305, 7.288349874675049E256, 8.601453618196765E256, 3.2418430174820535E135," +
                " 3.5268473155105324E73, 6.624267527758366E21, 2.781414290034622E304, 4.337029458973856E209]",
                2.659182730566976E305
        );
        rangeUp_double_helper(
                -Math.PI,
                "[3.1248378519408627E-201, -5.0922981473433815E-221, -3.441022587821036E-183, 2.1670010254279894," +
                " 7.069627244259931E62, 1.9010343005720085E280, -2.687484716869768E-18, 1.8192913077360027E-218," +
                " 3.988231886150639E-21, -2.6079057312741725E-131, 6.76110143056415E168, -4.020421646720354E-220," +
                " 2.964989804476424E-197, 3.7583884366317537E28, -6.362728328676735E-208, 4.771613370767263E145," +
                " 2.017717011652103E-70, 1.4985196394765999E44, -1.554718548850846E-193, -1.7692713909302183E-91]",
                8.795811810755379E304
        );
        rangeUp_double_helper(
                -Math.sqrt(2),
                "[6.99006493837807E-201, -2.206383967922193E-221, -1.573108202303946E-183, 4.9603331085431845," +
                " 1.5427542750789997E63, 4.370528606689457E280, -1.2079284597469617E-18, 3.9934426504974453E-218," +
                " 9.037509855800673E-21, -1.1274727245457443E-131, 1.4704077799733424E169, -1.7517529565491666E-220," +
                " 6.767869176709891E-197, 8.351313965754906E28, -2.8765417153200317E-208, 1.066105657914021E146," +
                " 4.489182267745601E-70, 3.276392265501371E44, -6.915593826871018E-194, -7.885507672649443E-92]",
                8.643105716054554E304
        );
        rangeUp_double_helper(
                0.0,
                "[1.4864784103112476E-109, 2.566877321469657E-291, 2.5126379981968673E-53, 3.2302505643973273E-38," +
                " 1.0705597848730719E297, 6.464515643831691E190, 2.590446025507275E246, 2.0373741384115344E273," +
                " 3.7908709815780255E-69, 9.756340335131575E73, 4.589747324434577E108, 0.001145081820838149," +
                " 6.0113658421231894E-52, 1.2227362772017277E257, 2.750709778951463E-173, 5.1828393921634645E73," +
                " 5.2236756011456244E-287, 2.2622843916023813E-4, 3.48382713769133E-99, 9.180665914320277E10]",
                1.2962175921568277E305
        );
        rangeUp_double_helper(
                -0.0,
                "[1.4864784103112476E-109, 2.566877321469657E-291, 2.5126379981968673E-53, 3.2302505643973273E-38," +
                " 1.0705597848730719E297, 6.464515643831691E190, 2.590446025507275E246, 2.0373741384115344E273," +
                " 3.7908709815780255E-69, 9.756340335131575E73, 4.589747324434577E108, 0.001145081820838149," +
                " 6.0113658421231894E-52, 1.2227362772017277E257, 2.750709778951463E-173, 5.1828393921634645E73," +
                " 5.2236756011456244E-287, 2.2622843916023813E-4, 3.48382713769133E-99, 9.180665914320277E10]",
                1.2962175921568277E305
        );
        rangeUp_double_helper(
                Double.MIN_VALUE,
                "[1.486478410311248E-109, 2.5668773214696576E-291, 2.512637998196868E-53, 3.2302505643973283E-38," +
                " 1.0705597848730722E297, 6.464515643831692E190, 2.5904460255072756E246, 2.0373741384115349E273," +
                " 3.7908709815780265E-69, 9.756340335131577E73, 4.589747324434578E108, 0.0011450818208381495," +
                " 6.011365842123191E-52, 1.2227362772017282E257, 2.7507097789514636E-173, 5.182839392163466E73," +
                " 5.2236756011456255E-287, 2.2622843916023818E-4, 3.4838271376913307E-99, 9.18066591432028E10]",
                1.296217592156828E305
        );
        rangeUp_double_helper(
                Double.MIN_NORMAL,
                "[2.9729568206224957E-109, 5.1337546429393145E-291, 5.0252759963937355E-53, 6.460501128794656E-38," +
                " 2.141119569746144E297, 1.2929031287663383E191, 5.1808920510145504E246, 4.0747482768230693E273," +
                " 7.581741963156052E-69, 1.9512680670263152E74, 9.179494648869155E108, 0.0022901636416762985," +
                " 1.202273168424638E-51, 2.445472554403456E257, 5.5014195579029265E-173, 1.036567878432693E74," +
                " 1.044735120229125E-286, 4.524568783204763E-4, 6.967654275382661E-99, 1.8361331828640558E11]",
                1.3130119946507386E305
        );
        rangeUp_double_helper(
                -Double.MIN_VALUE,
                "[1.4864784103112474E-109, 2.5668773214696566E-291, 2.512637998196867E-53, 3.230250564397327E-38," +
                " 1.0705597848730717E297, 6.46451564383169E190, 2.5904460255072744E246, 2.0373741384115342E273," +
                " 3.790870981578025E-69, 9.756340335131573E73, 4.5897473244345765E108, 0.0011450818208381488," +
                " 6.011365842123189E-52, 1.2227362772017274E257, 2.7507097789514625E-173, 5.182839392163464E73," +
                " 5.223675601145624E-287, 2.262284391602381E-4, 3.4838271376913295E-99, 9.180665914320276E10]",
                1.2962175921568277E305
        );
        rangeUp_double_helper(
                -Double.MIN_NORMAL,
                "[4.3574956749855824E107, 3.569958476725043E-88, 5.1501953484257926E-126, 6.551654363761235E-291," +
                " 2.3962241707379914E90, 5.323117741351607E287, 6.978121266704039E-178, 4.5098424446437194E-89," +
                " 4.037783335300366E111, 2.76685359443599E-101, 2.556992647725473E238, 1.1696197496164817E-115," +
                " 1.023603812244475E-217, 1.3031873501899124E258, 2.718713560842341E-24, 9.636644650963007E89," +
                " 1.1665879407592682E-171, 4.3630925649633036E47, 3.672904998095832E284, 5.227500770788364E-249]",
                1.3292108351557686E305
        );
        rangeUp_double_helper(
                Double.MAX_VALUE,
                "[Infinity, Infinity, Infinity, 1.7976931348623157E308, Infinity, Infinity, 1.7976931348623157E308," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " 1.7976931348623157E308, Infinity, Infinity, 1.7976931348623157E308]",
                Double.POSITIVE_INFINITY
        );
        rangeUp_double_helper(
                -Double.MAX_VALUE,
                "[-2.581476161683265E-108, -3.142409194387143E87, -2.12699084541025E125, 3.6809326518023177E-308," +
                " 1.1801760023901214E-245, 9.510403741743703E141, 3.2676848652179108E-28, -1.5424361720094474E290," +
                " -4.476286082796004E-91, -2.0556922191298296E-288, -1.606864915813639E177, 1.1439778670983188E-139," +
                " -2.4899205698697142E88, 3.051391923720765E300, 5.630474286106639E242, -2.7465161338662397E-112," +
                " 2.34993718045188E89, 6.471099924502226E-280, -3.721557030551081E100, 8.232151624378225E-163]",
                -3.959346934339254E303
        );
        rangeUp_double_helper(
                Double.POSITIVE_INFINITY,
                "[Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity]",
                Double.POSITIVE_INFINITY
        );
        rangeUp_double_helper(
                Double.NEGATIVE_INFINITY,
                "[-2.5814761616832655E-108, -3.1424091943871436E87, -2.1269908454102503E125, 3.680932651802317E-308," +
                " 1.1801760023901212E-245, 9.510403741743701E141, 3.2676848652179103E-28, -1.5424361720094475E290," +
                " -4.4762860827960045E-91, -2.05569221912983E-288, -1.6068649158136392E177, 1.1439778670983186E-139," +
                " -2.4899205698697145E88, 3.0513919237207646E300, 5.630474286106638E242, -2.74651613386624E-112," +
                " 2.3499371804518797E89, 6.471099924502225E-280, -3.721557030551082E100, 8.232151624378223E-163]",
                -3.959346934339273E303
        );
        rangeUp_double_fail_helper(Double.NaN);
    }

    private static void nextFromRangeDown_double_helper(double a, double output) {
        aeq(P.nextFromRangeDown(a), output);
        P.reset();
    }

    private static void nextFromRangeDown_double_fail_helper(double a) {
        try {
            P.nextFromRangeDown(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeDown_double() {
        nextFromRangeDown_double_helper(1.0, -9.695749714968612E-201);
        nextFromRangeDown_double_helper(1.0E20, -9.995264080399706E-221);
        nextFromRangeDown_double_helper(-1.0, -1.3361160166687895E199);
        nextFromRangeDown_double_helper(-1.0E20, -1.236689265426644E219);
        nextFromRangeDown_double_helper(Math.PI, -3.1248378519408627E-201);
        nextFromRangeDown_double_helper(Math.sqrt(2), -6.99006493837807E-201);
        nextFromRangeDown_double_helper(-Math.PI, -3.764522892368842E199);
        nextFromRangeDown_double_helper(-Math.sqrt(2), -1.7324409986526723E199);
        nextFromRangeDown_double_helper(0.0, -1.4864784103112476E-109);
        nextFromRangeDown_double_helper(-0.0, -1.4864784103112476E-109);
        nextFromRangeDown_double_helper(Double.MIN_VALUE, -1.4864784103112474E-109);
        nextFromRangeDown_double_helper(Double.MIN_NORMAL, -4.3574956749855824E107);
        nextFromRangeDown_double_helper(-Double.MIN_VALUE, -1.486478410311248E-109);
        nextFromRangeDown_double_helper(-Double.MIN_NORMAL, -2.9729568206224957E-109);
        nextFromRangeDown_double_helper(Double.MAX_VALUE, 2.581476161683265E-108);
        nextFromRangeDown_double_helper(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY);
        nextFromRangeDown_double_helper(Double.POSITIVE_INFINITY, 2.5814761616832655E-108);
        nextFromRangeDown_double_helper(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        nextFromRangeDown_double_fail_helper(Double.NaN);
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
        rangeDown_double_helper(
                1.0,
                "[-9.695749714968612E-201, 1.748023137790877E-221, 1.18317793185162E-183, -6.617187358035565," +
                " -2.121594297425973E63, -5.8742946490957294E280, 8.580086011885347E-19, -5.331775761432201E-218," +
                " -1.1844330132037358E-20, 8.938482795823259E-132, -2.0565211581170822E169, 1.3850642924441139E-220," +
                " -8.984366145692863E-197, -1.1633051909285701E29, 2.0701848154057248E-208, -1.4798882460290394E146," +
                " -5.6894974968490635E-70, -4.462628677199133E44, 5.062712151576731E-194, 5.740054069458642E-92]",
                -8.791807689509865E304
        );
        rangeDown_double_helper(
                1.0E20,
                "[-9.995264080399706E-221, 1.5798817404797417E-201, 1.1197940581928392E-163, -7.042132306445444E-20," +
                " -2.1565654618360867E43, -6.213265571507549E260, 85.35589443217175, -5.593215107700677E-238," +
                " -1.278957963606487E-40, 8.072635201308954E-112, -2.0603053200554565E149, 1.2540517188697671E-200," +
                " -9.599762377059262E-217, -1.19512282024884E9, 2.037820367922859E-188, -1.524666295515931E126," +
                " -6.3155319052712355E-90, -4.5828980952181496E24, 4.86441857893625E-174, 5.551512423880134E-72]",
                -8.851866062997829E304
        );
        rangeDown_double_helper(
                -1.0,
                "[-1.3361160166687895E199, -2.3072288694198864E17, -2.2584760398763506E255, -2.9034996317510987E270," +
                " -3.407411369365793E239, -1.0292528640881906E305, -5.403295552765342E256, -2.472466042809842E135," +
                " -4.6952828834636355E21, -2.0334465599448862E304, -3.1314260642373683E209, -1.942795489316547E38," +
                " -8.716553164100655E138, -1.4977836753903746E108, -1.336734354060387E181, -4.1958496470469624E12," +
                " -2.2659943330988978E139, -1.2315990218185135E116, -4.695636083772049E91, -1.8108731649679133E19]",
                -8.791807689509865E304
        );
        rangeDown_double_helper(
                -1.0E20,
                "[-1.236689265426644E219, -2.0802039030771412E37, -2.1584479716690603E275, -2.696332467719559E290," +
                " -3.074241456659164E210, -1.1848051000545277E266, -9.885917246849498E292, -3.0604408831173115E259," +
                " -4.509119778552049E93, -2.2693412353963272E128, -5.116558819879998E276, -6.085458367839E276," +
                " -2.265572225412841E155, -2.47936609780758E93, -4.6823981447226426E41, -3.057135115446442E229," +
                " -4.287740527297439E30, -1.8795215346865428E58, -7.992473343867941E158, -1.4129812974824141E128]",
                -2.880170764012561E305
        );
        rangeDown_double_helper(
                Math.PI,
                "[-3.1248378519408627E-201, 5.0922981473433815E-221, 3.441022587821036E-183, -2.1670010254279894," +
                " -7.069627244259931E62, -1.9010343005720085E280, 2.687484716869768E-18, -1.8192913077360027E-218," +
                " -3.988231886150639E-21, 2.6079057312741725E-131, -6.76110143056415E168, 4.020421646720354E-220," +
                " -2.964989804476424E-197, -3.7583884366317537E28, 6.362728328676735E-208, -4.771613370767263E145," +
                " -2.017717011652103E-70, -1.4985196394765999E44, 1.554718548850846E-193, 1.7692713909302183E-91]",
                -8.795811810755379E304
        );
        rangeDown_double_helper(
                Math.sqrt(2),
                "[-6.99006493837807E-201, 2.206383967922193E-221, 1.573108202303946E-183, -4.9603331085431845," +
                " -1.5427542750789997E63, -4.370528606689457E280, 1.2079284597469617E-18, -3.9934426504974453E-218," +
                " -9.037509855800673E-21, 1.1274727245457443E-131, -1.4704077799733424E169, 1.7517529565491666E-220," +
                " -6.767869176709891E-197, -8.351313965754906E28, 2.8765417153200317E-208, -1.066105657914021E146," +
                " -4.489182267745601E-70, -3.276392265501371E44, 6.915593826871018E-194, 7.885507672649443E-92]",
                -8.643105716054554E304
        );
        rangeDown_double_helper(
                -Math.PI,
                "[-3.764522892368842E199, -6.754724754207479E17, -6.659583594368998E255, -8.219387886206756E270," +
                " -1.5797237810800915E297, -9.869318362876497E190, -3.680413198673549E246, -3.1459968521161185E273," +
                " -1.0052225117992325E240, -1.465932226901811E74, -7.163488442458895E108, -3.0605727575551044E305," +
                " -1.6457576131745995E257, -1.9083783618789428E257, -7.39416936887628E135, -7.938999583704409E73," +
                " -1.4727417452517908E22, -6.250054558191402E304, -9.703617193821223E209, -1.3103148404337894E11]",
                -2.6819125859769203E305
        );
        rangeDown_double_helper(
                -Math.sqrt(2),
                "[-1.7324409986526723E199, -2.9260432862220326E17, -3.03590486243514E255, -3.7788066706468105E270," +
                " -6.874572397740668E296, -1.6347965850839314E308, -4.376534521737003E190, -1.6572415190565034E246," +
                " -1.4035841425602966E273, -4.373552475805664E239, -6.44435618182571E73, -3.2140029312564035E108," +
                " -1.3928411831531207E305, -7.288349874675049E256, -8.601453618196765E256, -3.2418430174820535E135," +
                " -3.5268473155105324E73, -6.624267527758366E21, -2.781414290034622E304, -4.337029458973856E209]",
                -2.659182730566976E305
        );
        rangeDown_double_helper(
                0.0,
                "[-1.4864784103112476E-109, -2.566877321469657E-291, -2.5126379981968673E-53," +
                " -3.2302505643973273E-38, -1.0705597848730719E297, -6.464515643831691E190, -2.590446025507275E246," +
                " -2.0373741384115344E273, -3.7908709815780255E-69, -9.756340335131575E73, -4.589747324434577E108," +
                " -0.001145081820838149, -6.0113658421231894E-52, -1.2227362772017277E257, -2.750709778951463E-173," +
                " -5.1828393921634645E73, -5.2236756011456244E-287, -2.2622843916023813E-4, -3.48382713769133E-99," +
                " -9.180665914320277E10]",
                -1.2962175921568277E305
        );
        rangeDown_double_helper(
                -0.0,
                "[-1.4864784103112476E-109, -2.566877321469657E-291, -2.5126379981968673E-53," +
                " -3.2302505643973273E-38, -1.0705597848730719E297, -6.464515643831691E190, -2.590446025507275E246," +
                " -2.0373741384115344E273, -3.7908709815780255E-69, -9.756340335131575E73, -4.589747324434577E108," +
                " -0.001145081820838149, -6.0113658421231894E-52, -1.2227362772017277E257, -2.750709778951463E-173," +
                " -5.1828393921634645E73, -5.2236756011456244E-287, -2.2622843916023813E-4, -3.48382713769133E-99," +
                " -9.180665914320277E10]",
                -1.2962175921568277E305
        );
        rangeDown_double_helper(
                Double.MIN_VALUE,
                "[-1.4864784103112474E-109, -2.5668773214696566E-291, -2.512637998196867E-53," +
                " -3.230250564397327E-38, -1.0705597848730717E297, -6.46451564383169E190, -2.5904460255072744E246," +
                " -2.0373741384115342E273, -3.790870981578025E-69, -9.756340335131573E73, -4.5897473244345765E108," +
                " -0.0011450818208381488, -6.011365842123189E-52, -1.2227362772017274E257, -2.7507097789514625E-173," +
                " -5.182839392163464E73, -5.223675601145624E-287, -2.262284391602381E-4, -3.4838271376913295E-99," +
                " -9.180665914320276E10]",
                -1.2962175921568277E305
        );
        rangeDown_double_helper(
                Double.MIN_NORMAL,
                "[-4.3574956749855824E107, -3.569958476725043E-88, -5.1501953484257926E-126," +
                " -6.551654363761235E-291, -2.3962241707379914E90, -5.323117741351607E287, -6.978121266704039E-178," +
                " -4.5098424446437194E-89, -4.037783335300366E111, -2.76685359443599E-101, -2.556992647725473E238," +
                " -1.1696197496164817E-115, -1.023603812244475E-217, -1.3031873501899124E258," +
                " -2.718713560842341E-24, -9.636644650963007E89, -1.1665879407592682E-171, -4.3630925649633036E47," +
                " -3.672904998095832E284, -5.227500770788364E-249]",
                -1.3292108351557686E305
        );
        rangeDown_double_helper(
                -Double.MIN_VALUE,
                "[-1.486478410311248E-109, -2.5668773214696576E-291, -2.512637998196868E-53," +
                " -3.2302505643973283E-38, -1.0705597848730722E297, -6.464515643831692E190, -2.5904460255072756E246," +
                " -2.0373741384115349E273, -3.7908709815780265E-69, -9.756340335131577E73, -4.589747324434578E108," +
                " -0.0011450818208381495, -6.011365842123191E-52, -1.2227362772017282E257, -2.7507097789514636E-173," +
                " -5.182839392163466E73, -5.2236756011456255E-287, -2.2622843916023818E-4, -3.4838271376913307E-99," +
                " -9.18066591432028E10]",
                -1.296217592156828E305
        );
        rangeDown_double_helper(
                -Double.MIN_NORMAL,
                "[-2.9729568206224957E-109, -5.1337546429393145E-291, -5.0252759963937355E-53," +
                " -6.460501128794656E-38, -2.141119569746144E297, -1.2929031287663383E191, -5.1808920510145504E246," +
                " -4.0747482768230693E273, -7.581741963156052E-69, -1.9512680670263152E74, -9.179494648869155E108," +
                " -0.0022901636416762985, -1.202273168424638E-51, -2.445472554403456E257, -5.5014195579029265E-173," +
                " -1.036567878432693E74, -1.044735120229125E-286, -4.524568783204763E-4, -6.967654275382661E-99," +
                " -1.8361331828640558E11]",
                -1.3130119946507386E305
        );
        rangeDown_double_helper(
                Double.MAX_VALUE,
                "[2.581476161683265E-108, 3.142409194387143E87, 2.12699084541025E125, -3.6809326518023177E-308," +
                " -1.1801760023901214E-245, -9.510403741743703E141, -3.2676848652179108E-28, 1.5424361720094474E290," +
                " 4.476286082796004E-91, 2.0556922191298296E-288, 1.606864915813639E177, -1.1439778670983188E-139," +
                " 2.4899205698697142E88, -3.051391923720765E300, -5.630474286106639E242, 2.7465161338662397E-112," +
                " -2.34993718045188E89, -6.471099924502226E-280, 3.721557030551081E100, -8.232151624378225E-163]",
                3.959346934339254E303
        );
        rangeDown_double_helper(
                -Double.MAX_VALUE,
                "[-Infinity, -Infinity, -Infinity, -1.7976931348623157E308, -Infinity, -Infinity," +
                " -1.7976931348623157E308, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity, -Infinity, -1.7976931348623157E308, -Infinity, -Infinity," +
                " -1.7976931348623157E308]",
                Double.NEGATIVE_INFINITY
        );
        rangeDown_double_helper(
                Double.POSITIVE_INFINITY,
                "[2.5814761616832655E-108, 3.1424091943871436E87, 2.1269908454102503E125, -3.680932651802317E-308," +
                " -1.1801760023901212E-245, -9.510403741743701E141, -3.2676848652179103E-28, 1.5424361720094475E290," +
                " 4.4762860827960045E-91, 2.05569221912983E-288, 1.6068649158136392E177, -1.1439778670983186E-139," +
                " 2.4899205698697145E88, -3.0513919237207646E300, -5.630474286106638E242, 2.74651613386624E-112," +
                " -2.3499371804518797E89, -6.471099924502225E-280, 3.721557030551082E100, -8.232151624378223E-163]",
                3.959346934339273E303
        );
        rangeDown_double_helper(
                Double.NEGATIVE_INFINITY,
                "[-Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity]",
                Double.NEGATIVE_INFINITY
        );
        rangeDown_double_fail_helper(Double.NaN);
    }

    private static void nextFromRange_double_double_helper(double a, double b, double output) {
        aeq(P.nextFromRange(a, b), output);
        P.reset();
    }

    private static void nextFromRange_double_double_fail_helper(double a, double b) {
        try {
            P.nextFromRange(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testNextFromRange_double_double() {
        nextFromRange_double_double_helper(1.0, 2.0, 1.5835797778827967);
        nextFromRange_double_double_helper(1.0, 3.0, 1.5835797778827967);
        nextFromRange_double_double_helper(1.0, 4.0, 1.2250037563816156);
        nextFromRange_double_double_helper(1.0, 257.0, 44.685540415870975);
        nextFromRange_double_double_helper(-257.0, -1.0, -6.429932448016128);
        nextFromRange_double_double_helper(1.0, 1.0E20, 2928511.57669452);
        nextFromRange_double_double_helper(-1.0E20, -1.0, -3.4460094358635984E13);
        nextFromRange_double_double_helper(Math.sqrt(2), Math.PI, 1.9977933402558918);
        nextFromRange_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI), 3.1415926535897936);
        nextFromRange_double_double_helper(0.0, 1.0, 1.4864784103112476E-109);
        nextFromRange_double_double_helper(-1.0, 1.0, -8.379780669198026E-200);
        nextFromRange_double_double_helper(1.0, 1.0, 1.0);
        nextFromRange_double_double_helper(1.0, Double.POSITIVE_INFINITY, 1.3361160166687895E199);
        nextFromRange_double_double_helper(Double.NEGATIVE_INFINITY, 1.0, -2.5814761616832655E-108);
        nextFromRange_double_double_helper(Double.MAX_VALUE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        nextFromRange_double_double_helper(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, -1.7976931348623157E308);
        nextFromRange_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                -2.5814761616832655E-108
        );
        nextFromRange_double_double_helper(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
        );
        nextFromRange_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY
        );
        nextFromRange_double_double_fail_helper(1.0, -1.0);
        nextFromRange_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        nextFromRange_double_double_fail_helper(Double.POSITIVE_INFINITY, 1.0);
        nextFromRange_double_double_fail_helper(1.0, Double.NEGATIVE_INFINITY);
        nextFromRange_double_double_fail_helper(Double.NaN, 1.0);
        nextFromRange_double_double_fail_helper(Double.NaN, Double.POSITIVE_INFINITY);
        nextFromRange_double_double_fail_helper(Double.NaN, Double.NEGATIVE_INFINITY);
        nextFromRange_double_double_fail_helper(1.0, Float.NaN);
        nextFromRange_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NaN);
        nextFromRange_double_double_fail_helper(Double.NEGATIVE_INFINITY, Double.NaN);
        nextFromRange_double_double_fail_helper(Double.NaN, Double.NaN);
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
        range_double_double_helper(
                1.0,
                2.0,
                "[1.5835797778827967, 1.2250037563816156, 1.4719282429124805, 1.955959341969205, 1.952474079933553," +
                " 1.2812051939433062, 1.718378909601498, 1.1380311043798883, 1.4966479396923722, 1.8013739894073642," +
                " 1.3500418411605772, 1.19802759669204, 1.1637033262176268, 1.9715807407419437, 1.760310920103927," +
                " 1.9242485088858954, 1.519961437596586, 1.2498568461701323, 1.7434459317250528, 1.3588974805735994]",
                1.499876445210012
        );
        range_double_double_helper(
                1.0,
                3.0,
                "[1.5835797778827967, 1.2250037563816156, 2.626588394843864, 1.4719282429124805, 2.3865778555006436," +
                " 2.2159002099356737, 1.955959341969205, 2.812142937330646, 2.6624944936333725, 2.6642025522415307," +
                " 1.952474079933553, 1.2812051939433062, 1.718378909601498, 1.1380311043798883, 1.4966479396923722," +
                " 2.806839848029425, 1.8013739894073642, 2.3609634965111925, 1.3500418411605772, 1.19802759669204]",
                1.8332389470500066
        );
        range_double_double_helper(
                1.0,
                4.0,
                "[1.2250037563816156, 1.955959341969205, 2.812142937330646, 3.5132877764446104, 1.952474079933553," +
                " 1.2812051939433062, 1.1380311043798883, 1.4966479396923722, 3.605186860203408, 2.806839848029425," +
                " 1.8013739894073642, 2.3609634965111925, 1.1637033262176268, 3.616428387881062, 2.4459543341888903," +
                " 2.8054188041207335, 3.0122098698604303, 1.9242485088858954, 3.0874957849824556, 3.769596927921006]",
                2.252032740476341
        );
        range_double_double_helper(
                1.0,
                257.0,
                "[44.685540415870975, 1.203314540014654, 5.4959865390041, 2.963931008164872, 27.609464705863815," +
                " 250.14988522530933, 37.522041105224474, 28.788722961147837, 32.57365398130994, 3.4016707698203," +
                " 14.666915703916764, 254.53179513426386, 7.413053494402684, 243.84010982189082, 10.68770177001166," +
                " 163.2640701140997, 6.780259012648993, 2.1946968861275895, 139.9816867140988, 3.1257266842702345]",
                47.82222621595425
        );
        range_double_double_helper(
                -257.0,
                -1.0,
                "[-6.429932448016128, -230.47573887812428, -52.1571076879672, -97.4042077387241," +
                " -10.226517647068093, -1.0496102716772708, -7.325369861846941, -9.636888519426082," +
                " -7.943918252336258, -83.3965353657504, -18.72866859216647, -1.0153766005135636," +
                " -36.82057204477853, -1.098905392016478, -26.68709645997668, -1.7284057022335961," +
                " -41.882927898808056, -122.01969964391714, -1.9102993225461031, -92.2267461033525]",
                -47.96215738338841
        );
        range_double_double_helper(
                1.0,
                1.0E20,
                "[2928511.57669452, 2.3072288694198864E17, 5.4959865390041, 8.342724114951985E14, 1928.255942789932," +
                " 2.194764354930176E8, 6.274975011389363E19, 6.863488923324052E16, 4.5903329571601395E10," +
                " 8.467664876943797E13, 1.9084732476775908E15, 4.1958496470469624E12, 1.55954939096077216E17," +
                " 9.426151350551033E9, 3.1257266842702345, 35.52409679123231, 2456.4950584409403, 49318.31812426833," +
                " 4.0552304692838994E14, 1.8108731649679133E19]",
                2.14103887866713216E18
        );
        range_double_double_helper(
                -1.0E20,
                -1.0,
                "[-3.4460094358635984E13, -449.09851696978916, -1.8273861934014382E19, -122767.750694466," +
                " -5.3041244464305416E16, -4.7279710384449133E11, -1.6544173306967305, -1485.057902206319," +
                " -2.188908595448283E9, -1207881.0788519834, -54401.03915338571, -2.428000719319587E7," +
                " -651.8261449875903, -1.0805315424142448E10, -3.3063748052870226E19, -2.8710620640670177E18," +
                " -4.1641803933682392E16, -2.0831142124355958E15, -250943.29987439423, -5.567600384211871]",
                -2.11804239962092954E18
        );
        range_double_double_helper(
                Math.sqrt(2),
                Math.PI,
                "[1.9977933402558918, 1.6392173187547108, 1.8861418052855756, 3.044327334681864, 2.7403458086846," +
                " 2.733375284613296, 1.6954187563164014, 2.2651849439491865, 1.5522446667529834, 1.9108615020654673," +
                " 2.4311751035609186, 1.7642554035336724, 1.612241159065135, 1.577916888590722, 2.7715886062300776," +
                " 2.3490489649540445, 2.676924142517981, 1.9341749999696811, 1.6640704085432274, 2.315318988196296]",
                2.1332465556159024
        );
        range_double_double_helper(
                Math.PI,
                FloatingPointUtils.successor(Math.PI),
                "[3.1415926535897936, 3.1415926535897936, 3.1415926535897936, 3.141592653589793, 3.1415926535897936," +
                " 3.1415926535897936, 3.141592653589793, 3.1415926535897936, 3.1415926535897936, 3.1415926535897936," +
                " 3.1415926535897936, 3.1415926535897936, 3.1415926535897936, 3.1415926535897936," +
                " 3.1415926535897936, 3.1415926535897936, 3.141592653589793, 3.1415926535897936, 3.1415926535897936," +
                " 3.141592653589793]",
                3.1415926535777086
        );
        range_double_double_helper(
                0.0,
                1.0,
                "[1.4864784103112476E-109, 2.566877321469657E-291, 2.5126379981968673E-53, 3.2302505643973273E-38," +
                " 5.955186478225414E-12, 3.5960061917501863E-118, 1.440983433307529E-62, 1.1333269838445345E-35," +
                " 3.7908709815780255E-69, 5.427144458600164E-235, 2.553131697188187E-200, 0.001145081820838149," +
                " 6.0113658421231894E-52, 6.801696315624948E-52, 2.750709778951463E-173, 2.8830501110860696E-235," +
                " 5.2236756011456244E-287, 2.2622843916023813E-4, 3.48382713769133E-99, 5.106914932410541E-298]",
                0.0014616598338681203
        );
        range_double_double_helper(
                -1.0,
                1.0,
                "[-8.379780669198026E-200, -4.85388939564776E-18, -4.7863641226666504E-256," +
                " -3.8472936719446655E-271, 1.1910372956450827E-11, 7.192012383500373E-118, 2.881966866615058E-62," +
                " 2.266653967689069E-35, -3.274793459363971E-240, 1.0854288917200327E-234, 5.106263394376374E-200," +
                " -1.0409422210849897E-305, -1.999171034691222E-257, 1.3603392631249896E-51," +
                " -4.468544986884453E-136, 5.766100222172139E-235, -2.1418717801396104E-22, -5.22562291710748E-305," +
                " -3.33070981438074E-210, 1.0213829864821082E-297]",
                -1.3378778293216968E-5
        );
        range_double_double_helper(
                1.0,
                1.0,
                "[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]",
                1.000000000007918
        );
        range_double_double_helper(
                1.0,
                Double.POSITIVE_INFINITY,
                "[1.3361160166687895E199, 2.3072288694198864E17, 2.2584760398763506E255, 2.9034996317510987E270," +
                " 3.407411369365793E239, 1.0292528640881906E305, 5.403295552765342E256, 2.472466042809842E135," +
                " 4.6952828834636355E21, 2.0334465599448862E304, 3.1314260642373683E209, 1.942795489316547E38," +
                " 8.716553164100655E138, 1.4977836753903746E108, 1.336734354060387E181, 4.1958496470469624E12," +
                " 2.2659943330988978E139, 1.2315990218185135E116, 4.695636083772049E91, 1.8108731649679133E19]",
                2.6552885265154105E305
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                1.0,
                "[-2.5814761616832655E-108, -3.1424091943871436E87, -2.1269908454102503E125, 3.680932651802317E-308," +
                " 1.1801760023901212E-245, 3.2676848652179103E-28, -1.5424361720094475E290, -4.4762860827960045E-91," +
                " -2.05569221912983E-288, -1.6068649158136392E177, 1.1439778670983186E-139, -2.4899205698697145E88," +
                " -2.74651613386624E-112, 6.471099924502225E-280, -3.721557030551082E100, 8.232151624378223E-163," +
                " -3.979841451344905E-239, 2.4824196024650906E-264, -9.101202878673514E114, -1.03188557944043E217]",
                -9.343071840537472E304
        );
        range_double_double_helper(
                Double.MAX_VALUE,
                Double.POSITIVE_INFINITY,
                "[Infinity, Infinity, Infinity, 1.7976931348623157E308, Infinity, Infinity, 1.7976931348623157E308," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " 1.7976931348623157E308, Infinity, Infinity, 1.7976931348623157E308]",
                Double.POSITIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                -Double.MAX_VALUE,
                "[-1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308, -Infinity," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -Infinity, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -Infinity, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -Infinity]",
                Double.NEGATIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                "[-2.5814761616832655E-108, -3.1424091943871436E87, -2.1269908454102503E125, 3.680932651802317E-308," +
                " 1.1801760023901212E-245, 9.510403741743701E141, 3.2676848652179103E-28, -1.5424361720094475E290," +
                " -4.4762860827960045E-91, -2.05569221912983E-288, -1.6068649158136392E177, 1.1439778670983186E-139," +
                " -2.4899205698697145E88, 3.0513919237207646E300, 5.630474286106638E242, -2.74651613386624E-112," +
                " 2.3499371804518797E89, 6.471099924502225E-280, -3.721557030551082E100, 8.232151624378223E-163]",
                -3.959346934339273E303
        );
        range_double_double_helper(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                "[Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity, Infinity," +
                " Infinity, Infinity]",
                Double.POSITIVE_INFINITY
        );
        range_double_double_helper(
                Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                "[-Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity, -Infinity," +
                " -Infinity, -Infinity]",
                Double.NEGATIVE_INFINITY
        );
        range_double_double_helper(1.0, -1.0, "[]", 0.0);
        range_double_double_helper(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, "[]", 0.0);
        range_double_double_helper(Double.POSITIVE_INFINITY, 1.0, "[]", 0.0);
        range_double_double_helper(1.0, Double.NEGATIVE_INFINITY, "[]", 0.0);
        range_double_double_fail_helper(Double.NaN, 1.0);
        range_double_double_fail_helper(Double.NaN, Double.POSITIVE_INFINITY);
        range_double_double_fail_helper(Double.NaN, Double.NEGATIVE_INFINITY);
        range_double_double_fail_helper(1.0, Double.NaN);
        range_double_double_fail_helper(Double.POSITIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NEGATIVE_INFINITY, Double.NaN);
        range_double_double_fail_helper(Double.NaN, Double.NaN);
    }

    private static void nextFromRangeUpUniform_float_helper(float a, float output) {
        aeq(P.nextFromRangeUpUniform(a), output);
        P.reset();
    }

    private static void nextFromRangeUpUniform_float_fail_helper(float a) {
        try {
            P.nextFromRangeUpUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeUpUniform_float() {

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

    }

    private static void nextFromRangeDownUniform_float_helper(float a, float output) {
        aeq(P.nextFromRangeDownUniform(a), output);
        P.reset();
    }

    private static void nextFromRangeDownUniform_float_fail_helper(float a) {
        try {
            P.nextFromRangeDownUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeDownUniform_float() {

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

    }

    private static void nextFromRangeUniform_float_float_helper(float a, float b, float output) {
        aeq(P.nextFromRangeUniform(a, b), output);
        P.reset();
    }

    private static void nextFromRangeUniform_float_float_fail_helper(float a, float b) {
        try {
            P.nextFromRangeUniform(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testNextFromRangeUniform_float_float() {

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

    }

    private static void nextFromRangeUpUniform_double_helper(double a, double output) {
        aeq(P.nextFromRangeUpUniform(a), output);
        P.reset();
    }

    private static void nextFromRangeUpUniform_double_fail_helper(double a) {
        try {
            P.nextFromRangeUpUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeUpUniform_double() {

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

    }

    private static void nextFromRangeDownUniform_double_helper(double a, double output) {
        aeq(P.nextFromRangeDownUniform(a), output);
        P.reset();
    }

    private static void nextFromRangeDownUniform_double_fail_helper(double a) {
        try {
            P.nextFromRangeDownUniform(a);
            fail();
        } catch (ArithmeticException ignored) {}
    }

    @Test
    public void testNextFromRangeDownUniform_double() {

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

    }

    private static void nextFromRangeUniform_double_double_helper(double a, double b, double output) {
        aeq(P.nextFromRangeUniform(a, b), output);
        P.reset();
    }

    private static void nextFromRangeUniform_double_double_fail_helper(double a, double b) {
        try {
            P.nextFromRangeUniform(a, b);
            fail();
        } catch (ArithmeticException | IllegalArgumentException ignored) {}
    }

    @Test
    public void testNextFromRangeUniform_double_double() {

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

    }

    @Test
    public void testEquals() {
        List<RandomProvider> xs = Arrays.asList(
                P,
                Q.withScale(3).withSecondaryScale(0),
                R.withScale(0).withSecondaryScale(10)
        );
        List<RandomProvider> ys = Arrays.asList(
                P,
                Q.withScale(3).withSecondaryScale(0),
                R.withScale(0).withSecondaryScale(10)
        );
        testEqualsHelper(xs, ys);
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(P.equals("hello"));
    }

    @Test
    public void testHashCode() {
        aeq(P.hashCode(), 1620921090);
        aeq(Q.withScale(3).withSecondaryScale(0).hashCode(), 607613738);
        aeq(R.withScale(0).withSecondaryScale(10).hashCode(), 816647836);
    }

    @Test
    public void testToString() {
        aeq(P, "RandomProvider[@-8800290164235921060, 32, 8]");
        aeq(Q.withScale(3).withSecondaryScale(0), "RandomProvider[@-7948823947390831374, 3, 0]");
        aeq(R.withScale(0).withSecondaryScale(10), "RandomProvider[@2449928962525148503, 0, 10]");
    }

    private static double meanOfIntegers(@NotNull List<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfBigIntegers(@NotNull List<BigInteger> xs) {
        return sumDouble(map(i -> i.doubleValue() / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfBinaryFractions(@NotNull List<BinaryFraction> xs) {
        return sumDouble(map(i -> i.doubleRange().a / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static float meanOfFloats(@NotNull List<Float> xs) {
        return sumFloat(map(f -> f / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static double meanOfDoubles(@NotNull List<Double> xs) {
        return sumDouble(map(d -> d / DEFAULT_SAMPLE_SIZE, xs));
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }
}
// @formatter:on
