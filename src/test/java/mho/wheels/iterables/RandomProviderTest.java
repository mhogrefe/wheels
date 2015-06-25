package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
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
                "{1=923, 3=869, 5=475, 7=451, 1 << 1=439, 1 >> 2=419, 3 >> 1=395, 1 << 2=391, 3 << 2=376, 3 >> 2=359}",
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
                " -3 >> 2=359}",
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
                "{-3=474, -1=461, 3=460, 1=433, 5=237, -5=228, -7=223, 7=221, -3 << 2=220, -1 >> 1=211}",
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
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(1, "11", "1");
        nextFromRange_BinaryFraction_BinaryFraction_fail_helper(1, "-1", "-11");
    }

    @Test
    @Ignore
    public void testPositiveOrdinaryFloats() {
        aeqit(take(TINY_LIMIT, P.positiveOrdinaryFloats()),
                "[1.89613015E10, 1.1960635E-14, 3.3527607E-4, 5.655431E-37, 3.614718E-15, 2.0566479E-25," +
                " 2.9515041E16, 4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, 2.6460455E-35," +
                " 66049.98, 8.7866956E17, 5.9178722E-21, 5.2186357E-27, 5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testNegativeOrdinaryFloats() {
        aeqit(take(TINY_LIMIT, P.negativeOrdinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, -5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " -2.9515041E16, -4.02697717E15, -29027.99, -12970.511, -4.78944453E14, -6.62682E-27," +
                " -2.6460455E-35, -66049.98, -8.7866956E17, -5.9178722E-21, -5.2186357E-27, -5.710558E33," +
                " -1.7919747E36, -5.174596E-35]");
    }

    @Test
    @Ignore
    public void testOrdinaryFloats() {
        aeqit(take(TINY_LIMIT, P.ordinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testFloats() {
        aeqit(take(TINY_LIMIT, P.floats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    @Ignore
    public void testPositiveOrdinaryDoubles() {
        aeqit(take(TINY_LIMIT, P.positiveOrdinaryDoubles()),
                "[1.0846552561567438E80, 2.38197354700265E-114, 5.149568405861E-293, 2.4985843477357602E-200," +
                " 4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, 2.8252505782194046E36," +
                " 8.566220677325717E-165, 7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, 1.4224921830272466E88, 1.4069651251380964E77," +
                " 2.1879373410803317E-265, 3.027783021197556E-242, 1.1079692399020062E285, 4.408373100689709E-147]");
    }

    @Test
    @Ignore
    public void testNegativeOrdinaryDoubles() {
        aeqit(take(TINY_LIMIT, P.negativeOrdinaryDoubles()),
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
        aeqit(take(TINY_LIMIT, P.ordinaryDoubles()),
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
        aeqit(take(TINY_LIMIT, P.doubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, 5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, -1.4224921830272466E88, 1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " 4.408373100689709E-147]");
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

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }
}
// @formatter:on
